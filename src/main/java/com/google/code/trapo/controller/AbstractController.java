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
package com.google.code.trapo.controller;

import static org.springframework.util.ReflectionUtils.getField;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

/**
 * @author Bamboozle Who
 *
 * @since 27/10/2009
 */
public abstract class AbstractController<Type> {

	public abstract Validator validator();
	
	protected boolean exists(Type bean) {
		return getField(field("id", bean), bean) != null;
	}

	@SuppressWarnings("unchecked")
	private Field field(String name, Type bean) {
		try {
			Class c = bean.getClass();
			Field field = c.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	protected String decode(String string) {
		try {
			return URLDecoder.decode(string, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return string;
		}
	}
	
	protected boolean hasErrors(Type bean, BindingResult errors) {
		validator().validate(bean, errors);
		return errors.hasErrors();
	}
	
}
