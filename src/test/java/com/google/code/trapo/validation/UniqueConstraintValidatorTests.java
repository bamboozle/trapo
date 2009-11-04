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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import net.vidageek.mirror.dsl.Mirror;

import org.junit.Test;

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
	
}
