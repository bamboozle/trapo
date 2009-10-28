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
package com.google.code.trapo.persistence;

import static java.lang.String.valueOf;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author Bamboozle Who
 * 
 * @since 28/10/2009
 */
public abstract class AbstractRepository<Type, KeyType extends Serializable> {
	
	@Autowired private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	abstract Class entityClass();
	
	public Type add(Type entity) {
		template().save(entity);
		return entity;
	}

	public Type update(Type entity) {
		template().update(entity);
		return entity;
	}
	
	public void remove(Type entity) {
		template().delete(entity);
	}
	
	@SuppressWarnings("unchecked")
	public List<Type> listAll() {
		HibernateTemplate template = template();
		template.setCacheQueries(true);
		template.setQueryCacheRegion(queryCacheRegion());
		return template.loadAll(entityClass());
	}
	
	@SuppressWarnings("unchecked")
	public List<Type> listAll(final int page) {
		HibernateTemplate template = template();
		template.setQueryCacheRegion(queryCacheRegion(page));
		template.setCacheQueries(true);
		List<Type> results = template.executeFind(new HibernateCallback<List<Type>>() {
			public List<Type> doInHibernate(Session session) {
				Query query = session.createQuery("from ".concat(entityName()));
				query.setMaxResults(20);
				query.setFirstResult(page * 20);
				return query.list();
			}
		});
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public Type get(KeyType id) {
		HibernateTemplate template = template();
		template.setCacheQueries(true);
		template.setQueryCacheRegion(entityName().concat(valueOf(id)));
		return (Type) template.get(entityClass(), id);
	}
	
	protected HibernateTemplate template() {
		HibernateTemplate template = new HibernateTemplate(sessionFactory);
		return template;
	}
	
	private String entityName() {
		return entityClass().getSimpleName();
	}
	
	private String queryCacheRegion() {
		return entityName().concat(".listAll");
	}
	
	private String queryCacheRegion(int page) {
		return entityName().concat(".listAll.").concat(valueOf(page));
	}
}
