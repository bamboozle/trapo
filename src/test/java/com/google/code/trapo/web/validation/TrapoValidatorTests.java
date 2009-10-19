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
package com.google.code.trapo.web.validation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;
import org.springframework.validation.Errors;

/**
 * @author Bamboozle Who
 * 
 * @since 18/10/2009
 */
public class TrapoValidatorTests {

	@Test
	public void should_validate_find_validation_errors() {
		TrapoValidator validator = new TrapoValidator();
		Errors errors = validator.validate(new Foo(null));
		assertThat(errors.getErrorCount(), equalTo(1));
	}
	
	@Test
	public void should_return_a_empty_errors_when_object_is_valid() {
		TrapoValidator validator = new TrapoValidator();
		Errors errors = validator.validate(new Foo("value"));
		assertThat(errors.getErrorCount(), equalTo(0));
	}
	
	@Test
	public void should_return_an_error_for_specific_field() {
		TrapoValidator validator = new TrapoValidator();
		Errors errors = validator.validate(new Foo(null));
		assertThat(errors.getFieldError("bar"), notNullValue());
	}
	
	public static class Foo {
		
		@NotEmpty private String bar;

		public Foo(String bar) {
			this.bar = bar;
		}
		
		public String getBar() {
			return bar;
		}
	}
}
