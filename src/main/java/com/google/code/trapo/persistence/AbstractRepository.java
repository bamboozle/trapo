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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;

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
		return template()
			  .usingCachedQueries()
			  .withCacheRegion(queryCacheRegion())
			  .loadAll(entityClass());
	}
	
	@SuppressWarnings("unchecked")
	public List<Type> listAll(final int page) {
		return template()
			   .usingCachedQueries()
			   .withCacheRegion(queryCacheRegion(page))
			   .executeFind(pagedQuery(page));
	}

	@SuppressWarnings("unchecked")
	private HibernateCallback<List<Type>> pagedQuery(final int page) {
		return new HibernateCallback<List<Type>>() {
			public List<Type> doInHibernate(Session session) {
				return session.createQuery("from ".concat(entityName()))
							  .setFirstResult(page * 20)
							  .setMaxResults(20).list();
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	public Type get(KeyType id) {
		Object entity = template()
					  .usingCachedQueries()
					  .withCacheRegion(entityName().concat(valueOf(id)))
					  .get(entityClass(), id);
		return (Type) entity;
	}
	
	protected FluentHibernateTemplate template() {
		return new FluentHibernateTemplate(sessionFactory);
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
