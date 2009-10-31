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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.trapo.domain.Forum;

/**
 * @author Bamboozle Who
 * 
 * @since 22/08/2009
 */
public class ForumRepositoryTests extends AbstractRepositoryTest<Forum, String> {

	@Autowired private ForumRepository forumRepository;
	
	@Override @SuppressWarnings("unchecked") 
	public AbstractRepository repository() {
		return forumRepository;
	}
	
	@Override
	public String[] entities() {
		return new String[]{ "forums" };
	}
	
	@Test
	public void should_list_all_forums() {
		List<Forum> forums = this.forumRepository.listAll();
		assertThat(forums.isEmpty(), is(false));
	}
	
	@Test
	public void should_list_just_the_first_forums_page() {
		List<Forum> forums = this.forumRepository.listAll(0);
		assertThat(forums.get(0).getId(), equalTo("12300"));
		assertThat(forums.size(), equalTo(20));
	}
	
	@Test
	public void should_find_a_forum_by_name() {
		Forum forum = forumRepository.byName("A Forum 12300");
		assertThat(forum.getDescription(), equalTo("The 12300 forum description"));
	}
	
	@Test
	public void should_return_null_when_there_is_no_forum_with_the_given_name() {
		Forum forum = forumRepository.byName("there is no forum with this name");
		assertThat(forum, nullValue());
	}
	
	@Test
	public final void should_save_a_forum_in_database() {

		Forum forum = forum("Trapo");
		forumRepository.add(forum);
		
		assertThat(forum.getId(), notNullValue());
		
	}
	
	@Test
	public final void should_update_a_forum_to_the_database() {
		
		Forum forum = forumRepository.get("12300");
		
		forum.withName("A New Name");
		forumRepository.update(forum);
		
		Forum updated = forumRepository.get("12300");
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
		forum.setId("12300");
		
		this.forumRepository.remove(forum);
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
