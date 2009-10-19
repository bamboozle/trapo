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

import static java.lang.String.valueOf;
import static java.util.Locale.getDefault;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * @author Bamboozle Who
 *
 * @since 18/10/2009
 */
@Component
public class TrapoValidator {

	private Validator validator;
	
	@Autowired
	public TrapoValidator(Validator validator) {
		this.validator = validator;
	}
	
	public Errors validate(Object bean) {
		
		String name = name(bean);
		
		BindException exception = new BindException(bean, name);
		
		Set<ConstraintViolation<Object>> violations = validator.validate(bean);
		for (ConstraintViolation<Object> violation : violations) {
			exception.addError(asError(violation, name));
		}
		
		return exception;
	}

	private ObjectError asError(ConstraintViolation<Object> violation, String name) {
		
		Path property = violation.getPropertyPath();
		Object invalidValue = violation.getInvalidValue();
		String message = violation.getMessage();
		
		return new FieldError(
				name, 
				valueOf(property), 
				violation.getInvalidValue(), 
				true, 
				null, 
				new Object[] {invalidValue}, 
				message);
	}

	private String name(Object bean) {
		if(bean != null) {
			return bean.getClass().getName().toLowerCase(getDefault());
		}
		return "";
	}

}
