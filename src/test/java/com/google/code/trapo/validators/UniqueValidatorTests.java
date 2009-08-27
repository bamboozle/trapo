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

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Value;
import org.junit.Test;

import com.google.code.trapo.domain.Forum;

/**
 * @author Bamboozle Who
 *
 * @since 27/08/2009
 */
public class UniqueValidatorTests {

	@Test
	public void should_return_false_when_the_value_is_null() {
		
		UniqueValidator validator = new UniqueValidator();
		validator.setEntityClass(Forum.class);
		validator.setPropertyName("name");
		
		assertThat(validator.isValid(null), is(false));
		
	}
	
	@Test
	public void should_return_valid_when_the_value_do_not_exists_yet() {
		
		List result = new ArrayList();
		
		UniqueValidator validator = uniqueValidator(result);
		
		assertThat(validator.isValid("trapo"), is(true));
		
	}
	
	@Test
	public void should_return_true_when_the_invalid_when_the_value_alread_exists() {
		
		List result = asList("trapo");
		UniqueValidator validator = uniqueValidator(result);
		
		assertThat(validator.isValid("trapo"), is(false));
	}
	
	@Test
	public void should_apply_unique_constraint_to_property_annotated_with_unique_constraint() {

		Column column = new Column();
		Property property = propertyMock(column);
		
		UniqueValidator validator = new UniqueValidator();
		validator.apply(property);
		
		assertThat(column.isUnique(), is(true));
		
	}

	private Property propertyMock(Column column) {
		Value value = mock(Value.class);
		PersistentClass persistentClass = mock(PersistentClass.class);

		Iterator<Column> iterator = mock(Iterator.class);
		when(iterator.next()).thenReturn(column);
		when(iterator.hasNext()).thenReturn(true)
								.thenReturn(false);
		
		Property property = mock(Property.class);
		when(property.getValue()).thenReturn(value);
		when(property.getPersistentClass()).thenReturn(persistentClass);
		when(property.isComposite()).thenReturn(false);
		when(property.getColumnIterator()).thenReturn(iterator);
		return property;
	}

	private UniqueValidator uniqueValidator(List result) {
		
		Query query = mock(Query.class);
		when(query.setParameter("value", "trapo")).thenReturn(query);
		when(query.list()).thenReturn(result);
		
		Session session = mock(Session.class);
		when(session.createQuery(query())).thenReturn(query);
		
		SessionFactory sessionFactory = mock(SessionFactory.class);
		when(sessionFactory.openSession()).thenReturn(session);
		
		UniqueValidator validator = new UniqueValidator();
		validator.setEntityClass(Forum.class);
		validator.setPropertyName("name");
		validator.setSessionFactory(sessionFactory);
		
		return validator;
	}
	
	private String query() {
		String query = "select name from com.google.code.trapo.domain.Forum as bean  where bean.name = :value";
		return query;
	}
	
}
