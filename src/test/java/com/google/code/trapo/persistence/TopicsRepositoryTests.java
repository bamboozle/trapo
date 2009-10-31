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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.trapo.domain.Forum;
import com.google.code.trapo.domain.Topic;

/**
 * @author Bamboozle Who
 *
 * @since 31/10/2009
 */
public class TopicsRepositoryTests extends AbstractRepositoryTest<Topic, String>{

	@Autowired private TopicRepository topicRepository;
	
	@Override
	public String[] entities() {
		return new String[] { "topics" };
	}

	@Override
	public AbstractRepository<Topic, String> repository() {
		return topicRepository;
	}

	@Test
	public void should_find_all_topics_for_a_forum() {
		Forum forum = new Forum();
		forum.setId("1234");
		
		List<Topic> topics = this.topicRepository.topicsFor(forum);
		assertThat(topics.isEmpty(), is(false));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void should_throw_an_exception_when_trying_to_find_topics_for_a_null_forum() {
		this.topicRepository.topicsFor(null);
	}
	
	@Test
	public void should_return_an_empty_collection_for_a_non_existent_forum() {
		Forum forum = new Forum();
		forum.setId("non existent");
		List<Topic> topics = this.topicRepository.topicsFor(forum);
		assertThat(topics.isEmpty(), is(true));
	}
	
	@After
	public void unloadTopicsData() throws Exception {
		runDBUnitOperation(DatabaseOperation.DELETE_ALL, entities());
	}
	
}
