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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hibernate.validator.InvalidValue;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.Validator;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Bamboozle Who
 *
 * @since 27/08/2009
 */
public class ClassValidatorEntityAwareTests {

	@Test
	public void should_set_bean_class_and_property_name_in_a_entity_aware_validator() {
		
		ClassValidatorEntityAware<EntityUnique> classValidator = validator(EntityUnique.class);
		List<Validator> beansValidators = classValidator.getMemberValidators();
		
		assertThat(beansValidators.size(), equalTo(1));
		
		UniqueValidator uniqueValidator = (UniqueValidator) beansValidators.get(0);
		
		assertThat(uniqueValidator.getPropertyName(), equalTo("p2"));
	}

	private ClassValidatorEntityAware validator(Class c) {
		return new ClassValidatorEntityAware(c);
	}
	
	@Test
	public void should_find_invalid_values_for_non_conform_objects() {
		
		ClassValidatorEntityAware<EntityNotEmpty> classValidator = validator(EntityNotEmpty.class);
		
		EntityNotEmpty entity = new EntityNotEmpty();
		InvalidValue[] invalidValues = classValidator.getInvalidValues(entity);
		
		assertThat(invalidValues, notNullValue());
		assertThat(invalidValues.length, equalTo(1));
		
	}
	
	public static class EntityUnique {
		@Unique private String p2;

		public String getP2() {
			return p2;
		}

		public void setP2(String p2) {
			this.p2 = p2;
		}
	}
	
	public static class EntityNotEmpty {
		@NotEmpty private String p2;

		public String getP2() {
			return p2;
		}

		public void setP2(String p2) {
			this.p2 = p2;
		}
		
	}
}
