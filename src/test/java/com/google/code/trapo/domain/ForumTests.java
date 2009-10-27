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
package com.google.code.trapo.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;

/**
 * @author Bamboozle Who
 *
 * @since 20/08/2009
 */
public class ForumTests {
	
	@Test
	public final void should_set_value_in_properties() {
		
		Date createdAt = new Date();
		
		Forum forum = new Forum();
		forum.setCreatedAt(createdAt);
		forum.setDescription("my description");
		forum.setId("1234");
		forum.setName("my new forum");
		forum.setOpen(true);
		
		assertThat(forum.getCreatedAt(), equalTo(createdAt));
		assertThat(forum.getDescription(), equalTo("my description"));
		assertThat(forum.getId(), equalTo("1234"));
		assertThat(forum.getName(), equalTo("my new forum"));
		assertThat(forum.isOpen(), is(true));
	}

	@Test
	public final void should_compare_two_forums_lexicographically() {
		
		Forum forum1 = new Forum("A Forum");
		Forum forum2 = new Forum("B Forum");
		
		assertThat(forum1.compareTo(forum2) < 0, is(true));
		assertThat(forum2.compareTo(forum1) < 0, is(false));
		
	}
	
	@Test
	public final void should_return_zero_when_the_to_compare_forum_is_null() {
		assertThat(new Forum("A Forum").compareTo(null), equalTo(0));
	}
	
	@Test
	public final void should_return_zero_when_comparing_forums_with_the_same_name() {
		
		Forum forum1 = new Forum("A Forum");
		Forum forum2 = new Forum("A Forum");
		
		assertThat(forum1.compareTo(forum2), equalTo(0));
	}
	
	@Test
	public final void should_not_consider_case_when_comparing_two_forums() {
		
		Forum forum1 = new Forum("A Forum");
		Forum forum2 = new Forum("a forum");
		
		assertThat(forum1.compareTo(forum2), equalTo(0));
	}
	
	@Test
	public final void should_open_a_forum_when_calling_open() {
		
		Forum forum = new Forum("A Forum");
		forum.open();

		assertThat(forum.isOpen(), is(true));
	}
	
	@Test
	public final void should_close_a_forum_when_calling_close() {
		
		Forum forum = new Forum().open();
		
		forum.close();
		assertThat(forum.isOpen(), is(false));
	}
	
	@Test
	public final void should_create_a_forum_with_a_name_and_open_it() {
		
		Forum forum = new Forum().withName("A Forum")
								 .open();
		
		assertThat(forum.getName(), equalTo("A Forum"));
		assertThat(forum.isOpen(), is(true));
		
	}
	
	@Test
	public void should_open_topics_in_a_forum() {
		
		Forum forum = new Forum().withName("A Forum").open();
		
		Topic topic = forum.openTopic("title", "text");

		assertThat(topic.getTitle(), equalTo("title"));
		assertThat(topic.getText(), equalTo("text"));
		assertThat(topic.getCreatedAt(), notNullValue());
		assertThat(topic.getForum(), equalTo(forum));
	}

}
