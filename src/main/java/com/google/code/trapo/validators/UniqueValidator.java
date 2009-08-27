/**
 * Copyright 2009 Bamboozle Who
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.code.trapo.validators;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.SingleTableSubclass;
import org.hibernate.validator.PropertyConstraint;

/**
 * @author Bamboozle Who
 *
 * @since 27/08/2009
 */
@SuppressWarnings("unchecked")
public class UniqueValidator implements EntityAwareValidator<Unique>, PropertyConstraint {

	private Class entityClass;
	private String propertyName;
	private SessionFactory sessionFactory;

	public void initialize(Unique parameters) {
		// TODO configure message here
	}

	public boolean isValid(Object value) {
		if(value == null) {
			return false;
		}
		return notExists(value);
	}
	
	private boolean notExists(Object value) {
		
		List result = session().createQuery(query())
							   .setParameter("value", value)
							   .list();
		
		return !(result != null && !result.isEmpty());
	}
	
	private String query() {
		StringBuffer query = new StringBuffer();
		query.append("select ")
			 .append(propertyName)
			 .append(" from ").append(entityClass.getName()).append(" as bean ")
			 .append(" where bean.").append(propertyName).append(" = :value");
		
		return query.toString();
	}

	private Session session() {
		return sessionFactory.openSession();
	}

	public void apply(Property property) {
		if (!(property.getPersistentClass() instanceof SingleTableSubclass) && 
			!(property.getValue() instanceof Collection)) {
			
			if ( !property.isComposite() ) {
				
				Iterator<Column> iter = (Iterator<Column>) property.getColumnIterator();
				
				while ( iter.hasNext() ) {
					iter.next().setUnique(true);
				}
				
			}
		}
	}

	public Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
