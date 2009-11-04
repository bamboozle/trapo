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

import static org.hibernate.criterion.DetachedCriteria.forClass;
import static org.hibernate.criterion.Projections.count;
import static org.hibernate.criterion.Restrictions.eq;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author Bamboozle Who
 *
 * @since 02/11/2009
 */
public class UniqueConstraintValidator implements ConstraintValidator<Unique, String> {

	@Autowired private SessionFactory sessionFactory;
	
	private Class<?> entity;
	private String field;
	
	public void initialize(Unique annotation) {
		this.entity = annotation.entity();
		this.field = annotation.field();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isEmpty(value)) {
			return false;
		}
		return query(value).intValue() == 0;
	}
	
	protected void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Number query(String value) {
		HibernateTemplate template = new HibernateTemplate(sessionFactory);
		DetachedCriteria criteria = forClass(entity)
								   .add(eq(field, value))
								   .setProjection(count(field));
		return (Number)template.findByCriteria(criteria).iterator().next();
	}

}
