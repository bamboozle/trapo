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

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Bamboozle Who
 *
 * @since 31/10/2009
 */
@RunWith(MockitoJUnitRunner.class) 
public class FluentHibernateTemplateTests {

	@Mock private SessionFactory sessionFactory;
	private FluentHibernateTemplate template;
	
	@Before
	public void createTemplate() {
		this.template = new FluentHibernateTemplate(sessionFactory);
	}
	
	@Test
	public void should_set_cached_queries_true() {
		assertThat(template.usingCachedQueries().isCacheQueries(), is(true));
	}
	
	@Test
	public void should_not_use_cached_queries_by_default() {
		assertThat(template.isCacheQueries(), is(false));
	}
	
	@Test
	public void should_set_the_query_fecth_size() {
		assertEquals(template.withFetchSize(10).getFetchSize(), 10);
	}
	
	@Test
	public void should_set_the_cache_region() {
		assertEquals(template.withCacheRegion("Test.region").getQueryCacheRegion(), "Test.region");
	}
	
	@Test
	public void should_set_max_results() {
		assertEquals(template.withMaxResults(10).getMaxResults(), 10);
	}
}
