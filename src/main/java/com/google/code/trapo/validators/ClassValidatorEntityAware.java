//$Id: ClassValidatorEntityAware.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package com.google.code.trapo.validators;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.AssertionFailure;
import org.hibernate.Hibernate;
import org.hibernate.MappingException;
import org.hibernate.annotations.common.reflection.Filter;
import org.hibernate.annotations.common.reflection.ReflectionManager;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XMember;
import org.hibernate.annotations.common.reflection.XMethod;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.java.JavaReflectionManager;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.util.IdentitySet;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.hibernate.validator.PersistentClassConstraint;
import org.hibernate.validator.PropertyConstraint;
import org.hibernate.validator.Valid;
import org.hibernate.validator.Validator;
import org.hibernate.validator.ValidatorClass;
import org.hibernate.validator.Version;
import org.hibernate.validator.interpolator.DefaultMessageInterpolatorAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Engine that take a bean and check every expressed annotation restrictions
 *
 * @author Gavin King
 * @author Emmanuel Bernard
 */
@SuppressWarnings({"serial", "unchecked"})
public class ClassValidatorEntityAware<T> implements Serializable {

	private static final Logger log = LoggerFactory.getLogger( ClassValidatorEntityAware.class );
	private static final InvalidValue[] EMPTY_INVALID_VALUE_ARRAY = new InvalidValue[]{};
	private static final Set<Class> INDEXABLE_CLASS = new HashSet<Class>();

	static {
		INDEXABLE_CLASS.add( Integer.class );
		INDEXABLE_CLASS.add( Long.class );
		INDEXABLE_CLASS.add( String.class );
	}

	static {
		Version.touch(); //touch version
	}

	private final Class<T> beanClass;
	private transient ReflectionManager reflectionManager;
	
	private final transient Map<XClass, ClassValidatorEntityAware> childClassValidatorEntityAwares;
	private transient List<Validator> beanValidators;
	private transient List<Validator> memberValidators;
	private transient List<XMember> memberGetters;
	private transient List<XMember> childGetters;
	private transient DefaultMessageInterpolatorAggregator defaultInterpolator;

	private static final Filter GET_ALL_FILTER = new Filter() {
		public boolean returnStatic() {
		return true;
		}

		public boolean returnTransient() {
		return true;
		}
	};

	/**
	 * create the validator engine for a particular bean class, using a custom message interpolator
	 * for message rendering on violation
	 */
	public ClassValidatorEntityAware(Class<T> beanClass) {
		this( beanClass, new HashMap<XClass, ClassValidatorEntityAware>(), null );
	}

    /**
     * Not a public API
     */
	public ClassValidatorEntityAware(
			Class<T> beanClass,
			Map<XClass, ClassValidatorEntityAware> childClassValidatorEntityAwares,
			ReflectionManager reflectionManager) {
        this.reflectionManager = reflectionManager != null ? reflectionManager : new JavaReflectionManager();
        XClass beanXClass = this.reflectionManager.toXClass( beanClass );
		this.beanClass = beanClass;
		this.childClassValidatorEntityAwares = childClassValidatorEntityAwares != null ?
                childClassValidatorEntityAwares :
                new HashMap<XClass, ClassValidatorEntityAware>();
		initValidator( beanXClass, this.childClassValidatorEntityAwares );
	}

	protected ClassValidatorEntityAware(
			XClass beanXClass,
			Map<XClass, ClassValidatorEntityAware> childClassValidatorEntityAwares,
			ReflectionManager reflectionManager) {
		this.reflectionManager = reflectionManager;
		this.beanClass = reflectionManager.toClass( beanXClass );
		this.childClassValidatorEntityAwares = childClassValidatorEntityAwares;
		initValidator( beanXClass, childClassValidatorEntityAwares );
	}

	private void initValidator(
			XClass xClass, Map<XClass, ClassValidatorEntityAware> childClassValidatorEntityAwares
	) {
		beanValidators = new ArrayList<Validator>();
		memberValidators = new ArrayList<Validator>();
		memberGetters = new ArrayList<XMember>();
		childGetters = new ArrayList<XMember>();
		defaultInterpolator = new DefaultMessageInterpolatorAggregator();

		//build the class hierarchy to look for members in
		childClassValidatorEntityAwares.put( xClass, this );
		Collection<XClass> classes = new HashSet<XClass>();
		addSuperClassesAndInterfaces( xClass, classes );
		for ( XClass currentClass : classes ) {
			Annotation[] classAnnotations = currentClass.getAnnotations();
			for ( int i = 0; i < classAnnotations.length ; i++ ) {
				Annotation classAnnotation = classAnnotations[i];
				Validator beanValidator = createValidator( classAnnotation );
				addBeanValidator(beanValidator);
				handleAggregateAnnotations(classAnnotation, null);
			}
		}

		//Check on all selected classes
		for ( XClass currClass : classes ) {
			List<XMethod> methods = currClass.getDeclaredMethods();
			for ( XMethod method : methods ) {
				createMemberValidator( method );
				createChildValidator( method );
			}

			List<XProperty> fields = currClass.getDeclaredProperties(
					"field", GET_ALL_FILTER
			);
			for ( XProperty field : fields ) {
				createMemberValidator( field );
				createChildValidator( field );
			}
		}
	}

	private void addBeanValidator(Validator beanValidator) {
		if ( beanValidator != null ) {
			beanValidators.add( beanValidator );
		}
		if (beanValidator instanceof EntityAwareValidator) {
			EntityAwareValidator eav = (EntityAwareValidator) beanValidator;
			eav.setEntityClass(beanClass);
		}
	}

	private void addSuperClassesAndInterfaces(XClass clazz, Collection<XClass> classes) {
		for ( XClass currClass = clazz; currClass != null ; currClass = currClass.getSuperclass() ) {
			if ( ! classes.add( currClass ) ) return;
			XClass[] interfaces = currClass.getInterfaces();
			for ( XClass interf : interfaces ) {
				addSuperClassesAndInterfaces( interf, classes );
			}
		}
	}

	private boolean handleAggregateAnnotations(Annotation annotation, XMember member) {
		Object[] values;
		try {
			Method valueMethod = annotation.getClass().getMethod( "value" );
			if ( valueMethod.getReturnType().isArray() ) {
				values = (Object[]) valueMethod.invoke( annotation );
			}
			else {
				return false;
			}
		}
		catch (NoSuchMethodException e) {
			return false;
		}
		catch (Exception e) {
			throw new IllegalStateException( e );
		}

		boolean validatorPresent = false;
		for ( Object value : values ) {
			if ( value instanceof Annotation ) {
				annotation = (Annotation) value;
				Validator validator = createValidator( annotation );
				validatorPresent = 	addMemberValidator(member, validator);
			}
		}
		return validatorPresent;
	}

	private boolean addMemberValidator(XMember member, Validator validator) {
		
		boolean validatorPresent = false;
		
		if ( validator != null ) {
			if (validator instanceof EntityAwareValidator) {
				EntityAwareValidator eav = (EntityAwareValidator) validator;
				eav.setEntityClass(beanClass);
				eav.setPropertyName(member.getName());
			}
			if ( member != null ) {
				memberValidators.add( validator );
				setAccessible( member );
				memberGetters.add( member );
			}
			else {
				addBeanValidator(validator);
			}
			validatorPresent = true;
		}
		return validatorPresent;
	}

	private void createChildValidator( XMember member) {
		if ( member.isAnnotationPresent( Valid.class ) ) {
			setAccessible( member );
			childGetters.add( member );
			XClass clazz;
			if ( member.isCollection() || member.isArray() ) {
				clazz = member.getElementClass();
			}
			else {
				clazz = member.getType();
			}
			if ( !childClassValidatorEntityAwares.containsKey( clazz ) ) {
				//ClassValidatorEntityAware added by side effect (added to childClassValidatorEntityAwares during CV construction)
				new ClassValidatorEntityAware( clazz, childClassValidatorEntityAwares, reflectionManager );
			}
		}
	}

	private void createMemberValidator(XMember member) {
		boolean validatorPresent = false;
		Annotation[] memberAnnotations = member.getAnnotations();
		for ( Annotation methodAnnotation : memberAnnotations ) {
			Validator propertyValidator = createValidator( methodAnnotation );
			if ( propertyValidator != null ) {
				addMemberValidator(member, propertyValidator);
				setAccessible( member );
				memberGetters.add( member );
				validatorPresent = true;
			}
			boolean agrValidPresent = handleAggregateAnnotations( methodAnnotation, member );
			validatorPresent = validatorPresent || agrValidPresent;
		}
		if ( validatorPresent && !member.isTypeResolved() ) {
			log.warn( "Original type of property {} is unbound and has been approximated.", member );
		}
	}

	private static void setAccessible(XMember member) {
		if ( !Modifier.isPublic( member.getModifiers() ) ) {
			member.setAccessible( true );
		}
	}

	private Validator createValidator(Annotation annotation) {
		try {
			ValidatorClass validatorClass = annotation.annotationType().getAnnotation( ValidatorClass.class );
			if ( validatorClass == null ) {
				return null;
			}
			Validator beanValidator = validatorClass.value().newInstance();
			beanValidator.initialize( annotation );
			defaultInterpolator.addInterpolator( annotation, beanValidator );
			return beanValidator;
		}
		catch (Exception e) {
			throw new IllegalArgumentException( "could not instantiate ClassValidatorEntityAware", e );
		}
	}

	public boolean hasValidationRules() {
		return beanValidators.size() != 0 || memberValidators.size() != 0;
	}

	/**
	 * apply constraints on a bean instance and return all the failures.
	 * if <code>bean</code> is null, an empty array is returned
	 */
	public InvalidValue[] getInvalidValues(T bean) {
		return this.getInvalidValues( bean, new IdentitySet() );
	}

	/**
	 * apply constraints on a bean instance and return all the failures.
	 * if <code>bean</code> is null, an empty array is returned
	 */
	protected InvalidValue[] getInvalidValues(T bean, Set<Object> circularityState) {
		if ( bean == null || circularityState.contains( bean ) ) {
			return EMPTY_INVALID_VALUE_ARRAY; //Avoid circularity
		}
		else {
			circularityState.add( bean );
		}

		if ( !beanClass.isInstance( bean ) ) {
			throw new IllegalArgumentException( "not an instance of: " + bean.getClass() );
		}

		List<InvalidValue> results = new ArrayList<InvalidValue>();

		runBeanValidators(bean, results);
		runMemberValidators(bean, results);
		runChildValidators(bean, circularityState, results);

		return results.toArray( new InvalidValue[results.size()] );
	}

	private void runChildValidators(T bean, Set<Object> circularityState,
			List<InvalidValue> results) {
		for ( int i = 0; i < childGetters.size() ; i++ ) {
			XMember getter = childGetters.get( i );
			if ( Hibernate.isPropertyInitialized( bean, getPropertyName( getter ) ) ) {
				Object value = getMemberValue( bean, getter );
				if ( value != null && Hibernate.isInitialized( value ) ) {
					String propertyName = getPropertyName( getter );
					if ( getter.isCollection() ) {
						int index = 0;
						boolean isIterable = value instanceof Iterable;
						Map map = ! isIterable ? (Map) value : null;
						Iterable elements = isIterable ?
								(Iterable) value :
								map.keySet();
						for ( Object element : elements ) {
							Object actualElement = isIterable ? element : map.get( element );
							if ( actualElement == null ) {
								index++;
								continue;
							}
							InvalidValue[] invalidValues = getClassValidatorEntityAware( actualElement )
									.getInvalidValues( actualElement, circularityState );

							String indexedPropName = MessageFormat.format(
									"{0}[{1}]",
									propertyName,
									INDEXABLE_CLASS.contains( element.getClass() ) ?
											( "'" + element + "'" ) :
											index
							);
							index++;

							for ( InvalidValue invalidValue : invalidValues ) {
								invalidValue.addParentBean( bean, indexedPropName );
								results.add( invalidValue );
							}
						}
					}
					if ( getter.isArray() ) {
						int index = 0;
						for ( Object element : (Object[]) value ) {
							if ( element == null ) {
								index++;
								continue;
							}
							InvalidValue[] invalidValues = getClassValidatorEntityAware( element )
									.getInvalidValues( element, circularityState );

							String indexedPropName = MessageFormat.format(
									"{0}[{1}]",
									propertyName,
									index
							);
							index++;

							for ( InvalidValue invalidValue : invalidValues ) {
								invalidValue.addParentBean( bean, indexedPropName );
								results.add( invalidValue );
							}
						}
					}
					else {
						InvalidValue[] invalidValues = getClassValidatorEntityAware( value )
								.getInvalidValues( value, circularityState );
						for ( InvalidValue invalidValue : invalidValues ) {
							invalidValue.addParentBean( bean, propertyName );
							results.add( invalidValue );
						}
					}
				}
			}
		}
	}

	private void runMemberValidators(T bean, List<InvalidValue> results) {
		for ( int i = 0; i < memberValidators.size() ; i++ ) {
			XMember getter = memberGetters.get( i );
			if ( Hibernate.isPropertyInitialized( bean, getPropertyName( getter ) ) ) {
				Object value = getMemberValue( bean, getter );
				Validator validator = memberValidators.get( i );
				if ( !validator.isValid( value ) ) {
					String propertyName = getPropertyName( getter );
					results.add( new InvalidValue( "", beanClass, propertyName, value, bean ) );
				}
			}
		}
	}

	private void runBeanValidators(T bean, List<InvalidValue> results) {
		for ( int i = 0; i < beanValidators.size() ; i++ ) {
			Validator validator = beanValidators.get( i );
			if ( !validator.isValid( bean ) ) {
				results.add( new InvalidValue( "", beanClass, null, bean, bean ) );
			}
		}
	}

	private ClassValidatorEntityAware getClassValidatorEntityAware(Object value) {
		Class clazz = value.getClass();
		ClassValidatorEntityAware validator = childClassValidatorEntityAwares.get( reflectionManager.toXClass( clazz ) );
		if ( validator == null ) { //handles polymorphism 
			//TODO cache this thing. in a second queue (reflectionManager being sealed)? beware of concurrency
			validator = new ClassValidatorEntityAware( clazz );
		}
		return validator;
	}

	/**
	 * Apply constraints of a particular property on a bean instance and return all the failures.
	 * Note this is not recursive.
	 */
	//TODO should it be recursive?
	public InvalidValue[] getInvalidValues(T bean, String propertyName) {
		List<InvalidValue> results = new ArrayList<InvalidValue>();

		for ( int i = 0; i < memberValidators.size() ; i++ ) {
			XMember getter = memberGetters.get( i );
			if ( getPropertyName( getter ).equals( propertyName ) ) {
				Object value = getMemberValue( bean, getter );
				Validator validator = memberValidators.get( i );
				if ( !validator.isValid( value ) ) {
					results.add( new InvalidValue( "", beanClass, propertyName, value, bean ) );
				}
			}
		}

		return results.toArray( new InvalidValue[results.size()] );
	}

	/**
	 * Apply constraints of a particular property value of a bean type and return all the failures.
	 * The InvalidValue objects returns return null for InvalidValue#getBean() and InvalidValue#getRootBean()
	 * Note this is not recursive.
	 */
	//TODO should it be recursive?
	public InvalidValue[] getPotentialInvalidValues(String propertyName, Object value) {
		List<InvalidValue> results = new ArrayList<InvalidValue>();

		for ( int i = 0; i < memberValidators.size() ; i++ ) {
			XMember getter = memberGetters.get( i );
			if ( getPropertyName( getter ).equals( propertyName ) ) {
				Validator validator = memberValidators.get( i );
				if ( !validator.isValid( value ) ) {
					results.add( new InvalidValue( "", beanClass, propertyName, value, null ) );
				}
			}
		}

		return results.toArray( new InvalidValue[results.size()] );
	}

	private Object getMemberValue(T bean, XMember getter) {
		Object value;
		try {
			value = getter.invoke( bean );
		}
		catch (Exception e) {
			throw new IllegalStateException( "Could not get property value", e );
		}
		return value;
	}

	private String getPropertyName(XMember member) {
		//Do no try to cache the result in a map, it's actually much slower (2.x time)
		String propertyName;
		if ( XProperty.class.isAssignableFrom( member.getClass() ) ) {
			propertyName = member.getName();
		}
		else if ( XMethod.class.isAssignableFrom( member.getClass() ) ) {
			propertyName = member.getName();
			if ( propertyName.startsWith( "is" ) ) {
				propertyName = Introspector.decapitalize( propertyName.substring( 2 ) );
			}
			else if ( propertyName.startsWith( "get" ) ) {
				propertyName = Introspector.decapitalize( propertyName.substring( 3 ) );
			}
			//do nothing for non getter method, in case someone want to validate a PO Method
		}
		else {
			throw new AssertionFailure( "Unexpected member: " + member.getClass().getName() );
		}
		return propertyName;
	}

	/**
	 * apply the registered constraints rules on the hibernate metadata (to be applied on DB schema...)
	 *
	 * @param persistentClass hibernate metadata
	 */
	public void apply(PersistentClass persistentClass) {

		for ( Validator validator : beanValidators ) {
			if ( validator instanceof PersistentClassConstraint ) {
				( (PersistentClassConstraint) validator ).apply( persistentClass );
			}
		}

		Iterator<Validator> validators = memberValidators.iterator();
		Iterator<XMember> getters = memberGetters.iterator();
		while ( validators.hasNext() ) {
			Validator validator = validators.next();
			String propertyName = getPropertyName( getters.next() );
			if ( validator instanceof PropertyConstraint ) {
				try {
					Property property = findPropertyByName(persistentClass, propertyName);
					if (property != null) {
						( (PropertyConstraint) validator ).apply( property );
					}
				}
				catch (MappingException pnfe) {
					//do nothing
				}
			}
		}

	}

	public void assertValid(T bean) {
		InvalidValue[] values = getInvalidValues( bean );
		if ( values.length > 0 ) {
			throw new InvalidStateException( values );
		}
	}
	
	protected List<Validator> getBeansValidators() {
		return this.beanValidators;
	}
	
	protected List<Validator> getMemberValidators() {
		return this.memberValidators;
	}

	/**
	 * Retrieve the property by path in a recursive way, including IndetifierProperty in the loop
	 * If propertyName is null or empty, the IdentifierProperty is returned
	 */
	public static Property findPropertyByName(PersistentClass associatedClass, String propertyName) {
		Property property = null;
		Property idProperty = associatedClass.getIdentifierProperty();
		String idName = idProperty != null ? idProperty.getName() : null;
		try {
			if ( propertyName == null
					|| propertyName.length() == 0
					|| propertyName.equals( idName ) ) {
				//default to id
				property = idProperty;
			}
			else {
				if ( propertyName.indexOf( idName + "." ) == 0 ) {
					property = idProperty;
					propertyName = propertyName.substring( idName.length() + 1 );
				}
				StringTokenizer st = new StringTokenizer( propertyName, ".", false );
				while ( st.hasMoreElements() ) {
					String element = (String) st.nextElement();
					if ( property == null ) {
						property = associatedClass.getProperty( element );
					}
					else {
						if ( ! property.isComposite() ) return null;
						property = ( (Component) property.getValue() ).getProperty( element );
					}
				}
			}
		}
		catch (MappingException e) {
			try {
				//if we do not find it try to check the identifier mapper
				if ( associatedClass.getIdentifierMapper() == null ) return null;
				StringTokenizer st = new StringTokenizer( propertyName, ".", false );
				while ( st.hasMoreElements() ) {
					String element = (String) st.nextElement();
					if ( property == null ) {
						property = associatedClass.getIdentifierMapper().getProperty( element );
					}
					else {
						if ( ! property.isComposite() ) return null;
						property = ( (Component) property.getValue() ).getProperty( element );
					}
				}
			}
			catch (MappingException ee) {
				return null;
			}
		}
		return property;
	}
}
