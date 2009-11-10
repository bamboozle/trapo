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

import org.springframework.stereotype.Repository;

import com.google.code.trapo.domain.Forum;
import com.google.code.trapo.domain.Topic;

/**
 * @author Bamboozle Who
 * 
 * @since 28/10/2009
 */
@Repository
public class TopicRepository extends AbstractRepository<Topic, String> {

	@Override
	@SuppressWarnings("unchecked")
	public Class entityClass() {
		return Topic.class;
	}

	@SuppressWarnings("unchecked")
	public List<Topic> topicsFor(Forum forum) {

		if (forum == null) {
			throw new IllegalArgumentException("Forum cannot be null.");
		}

		return template().usingCachedQueries().withCacheRegion(
				"Topic.topicsFor").findByNamedQuery("Topic.topicsFor",
				forum.getId());
	}

	@SuppressWarnings("unchecked")
	public Topic byTitle(String title) {
		List<Topic> topics = template()
							.usingCachedQueries()
							.withCacheRegion("Topic.byTitle")
							.withMaxResults(1)
							.findByNamedQuery("Topic.byTitle", title);

		if (topics.isEmpty()) {
			return null;
		}

		return topics.iterator().next();
	}
}
