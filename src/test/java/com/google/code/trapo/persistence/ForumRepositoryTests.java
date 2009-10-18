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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.code.trapo.domain.Forum;

/**
 * @author Bamboozle Who
 * 
 * @since 22/08/2009
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
	"classpath:/trapo-servlet.xml" 
})
public class ForumRepositoryTests {

	@Autowired
	private ForumRepository forumRepository;
	
	@Before
	public void addForumsToDatabase() {
		Forum forum = forum("A Mock forum");
		forum.setDescription("My new java forum");
		forum.setCreatedAt(new Date());
		forumRepository.save(forum.open());
	}
	
	@Test
	public void should_find_a_forum_by_name() {
		Forum forum = forumRepository.byName("A Mock forum");
		assertThat(forum.getName(), equalTo("A Mock forum"));
	}
	
	@Test
	public void should_return_null_when_there_is_no_forum_with_the_given_name() {
		Forum forum = forumRepository.byName("there is no forum with this name");
		assertThat(forum, nullValue());
	}
	
	@Test
	public final void should_save_a_forum_in_database() {

		Forum forum = forum("Trapo");
		forumRepository.save(forum);
		
		assertThat(forum.getId(), notNullValue());
		
	}
	
	@Test
	public final void should_update_a_forum_to_the_database() {
		
		Forum forum = forum();
		forumRepository.save(forum);
		
		forum.withName("A New Name");
		forumRepository.update(forum);
		
		Forum updated = forumRepository.get(forum.getId());
		assertThat(updated.getName(), equalTo("A New Name"));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void should_raise_a_exception_when_trying_to_reading_with_null_id() {
		forumRepository.get(null);
	}
	
	@Test
	public final void should_return_null_when_there_is_no_forum_for_the_given_id() {
		assertThat(forumRepository.get("no-existent-id"), nullValue());
	}
	
	@Test
	public final void should_delete_a_forum() {
	
		Forum forum = forum();
		this.forumRepository.save(forum);
		
		assertThat(forumRepository.get(forum.getId()), notNullValue());
		
		this.forumRepository.delete(forum);
		
		assertThat(forumRepository.get(forum.getId()), nullValue());
		
	}

	private Forum forum() {
		return forum("A New Forum");
	}
	
	private Forum forum(String name) {
		Forum forum = new Forum().withName(name).open();
		forum.setDescription("some description");
		return forum;
	}
}
