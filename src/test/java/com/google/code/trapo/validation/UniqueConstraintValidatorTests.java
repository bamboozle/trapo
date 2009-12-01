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
package com.google.code.trapo.validation;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import net.vidageek.mirror.dsl.Mirror;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.CriteriaImpl;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Bamboozle Who
 *
 * @since 04/11/2009
 */
public class UniqueConstraintValidatorTests {

	@Test
	public void should_initialize_whit_values_coming_from_annotation() {
		UniqueConstraintValidator validator = new UniqueConstraintValidator();
		validator.initialize(unique(String.class, "value"));
		assertThat(entity(validator), equalTo(String.class));
		assertThat(field(validator), equalTo("value"));
	}
	
	@Test
	public void should_not_be_valid_if_string_is_empty() {
		UniqueConstraintValidator validator = new UniqueConstraintValidator();
		assertThat(validator.isValid("", null), is(false));
		assertThat(validator.isValid(null, null), is(false));
	}
	
	@Test
	public void should_not_be_valid_if_value_is_already_in_use() {
		
		SessionImplementor session = session(1);
		UniqueConstraintValidator validator = validator(session);
		assertThat(validator.isValid("trapo", null), is(false));
	}

	@Test
	public void should_be_valid_when_there_is_no_repeated_value_in_database() {
		
		SessionImplementor session = session(0);
		UniqueConstraintValidator validator = validator(session);
		assertThat(validator.isValid("trapo", null), is(true));
	}
	
	private UniqueConstraintValidator validator(SessionImplementor session) {
		SessionFactory sessionFactory = mock(SessionFactory.class);
		when(sessionFactory.getCurrentSession()).thenAnswer(answer(session));
		when(sessionFactory.openSession()).thenAnswer(answer(session));
		
		UniqueConstraintValidator validator = new UniqueConstraintValidator();
		validator.initialize(unique(String.class, "value"));
		validator.setSessionFactory(sessionFactory);
		return validator;
	}

	private SessionImplementor session(int count) {
		SessionMock mock = mock(SessionMock.class);
		when(mock.list(Mockito.any(CriteriaImpl.class))).thenReturn(asList(count));
		return mock;
	}

	private Answer<SessionImplementor> answer(final SessionImplementor session) {
		return new Answer<SessionImplementor>() {
			public SessionImplementor answer(InvocationOnMock invocation) throws Throwable {
				return session;
			}
		};
	}

	@SuppressWarnings("unchecked")
	private Unique unique(Class entity, String field) {
		Unique unique = mock(Unique.class);
		when(unique.entity()).thenReturn(entity);
		when(unique.field()).thenReturn(field);
		return unique;
	}	
	
	private String field(UniqueConstraintValidator validator) {
		return (String)new Mirror().on(validator).get().field("field");
	}
	
	@SuppressWarnings("unchecked")
	private Class<String> entity(Object target) {
		return (Class<String>) new Mirror().on(target).get().field("entity");
	}
	
	static interface SessionMock extends Session, SessionImplementor {}
	
}
