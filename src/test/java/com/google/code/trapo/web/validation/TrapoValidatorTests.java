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

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

/**
 * @author Bamboozle Who
 * 
 * @since 18/10/2009
 */
public class TrapoValidatorTests {

	private TrapoValidator trapoValidator;
	
	@Before
	public void initValidator() {
		ValidatorFactory validatorFactory = buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		
		this.trapoValidator = new TrapoValidator(validator);
	}
	
	@Test
	public void validator_should_find_validation_errors() {
		Errors errors = trapoValidator.validate(new Foo(null));
		assertThat(errors.getErrorCount(), equalTo(1));
	}

	
	@Test
	public void should_return_a_empty_errors_when_object_is_valid() {
		Errors errors = trapoValidator.validate(new Foo("value"));
		assertThat(errors.getErrorCount(), equalTo(0));
	}
	
	@Test
	public void should_return_an_error_for_specific_field() {
		Errors errors = trapoValidator.validate(new Foo(null));
		assertThat(errors.getFieldError("bar"), notNullValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void should_throws_a_illegal_argument_exception_when_trying_to_validate_a_null_object() {
		trapoValidator.validate(null);
	}
	
	public static class Foo {
		
		@NotEmpty String bar;

		public Foo(String bar) {
			this.bar = bar;
		}
		
	}
}
