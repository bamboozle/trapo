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

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.google.code.trapo.domain.Forum;

/**
 * @author Bamboozle Who
 *
 * @since 20/08/2009
 */
@Repository
public class ForumRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Forum save(Forum forum) {
		template().save(forum);
		return forum;
	}
	
	public Forum update(Forum forum) {
		template().update(forum);
		return forum;
	}
	
	public void delete(Forum forum) {
		template().delete(forum);
	}
	
	public Forum get(String id) {
		return template().get(Forum.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public Forum byName(String name) {
		
		HibernateTemplate template = template();
		template.setMaxResults(1);
		template.setCacheQueries(true);
		template.setQueryCacheRegion("forums.byName");
		
		List<Forum> forums = template.findByNamedParam("from Forum f where f.name = :name", "name", name);
		
		if(forums.isEmpty()) {
			return null;
		}
		
		return forums.iterator().next();
		
	}

	private HibernateTemplate template() {
		HibernateTemplate template = new HibernateTemplate(sessionFactory);
		return template;
	}

	public List<Forum> listAll() {
		
		HibernateTemplate template = template();
		template.setCacheQueries(true);
		template.setQueryCacheRegion("forums.listAll");
		
		return template.loadAll(Forum.class);
	}
	
}
