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

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author Bamboozle Who
 *
 * @since 28/10/2009
 */
public class FluentHibernateTemplate extends HibernateTemplate {

	public FluentHibernateTemplate(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public FluentHibernateTemplate usingCachedQueries() {
		super.setCacheQueries(true);
		return this;
	}
	
	public FluentHibernateTemplate withFetchSize(int fetchSize) {
		super.setFetchSize(fetchSize);
		return this;
	}

	public FluentHibernateTemplate withMaxResults(int maxResults) {
		super.setMaxResults(maxResults);
		return this;
	}

	public FluentHibernateTemplate withCacheRegion(String queryCacheRegion) {
		super.setQueryCacheRegion(queryCacheRegion);
		return this;
	}
	
}
