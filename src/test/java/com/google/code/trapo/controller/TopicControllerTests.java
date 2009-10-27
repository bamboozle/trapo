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
package com.google.code.trapo.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.google.code.trapo.domain.Forum;
import com.google.code.trapo.persistence.ForumRepository;
import com.google.code.trapo.web.Message;

/**
 * @author Bamboozle Who
 *
 * @since 23/10/2009
 */
public class TopicControllerTests {
	
	@Test
	public void should_navigate_to_create_a_new_topic_in_a_forum() {
		
		TopicsController controller = new TopicsController();
		controller.setForumRepository(forumRepository(forum()));

		String result = controller.create("1234", model());
		
		assertThat(result, equalTo("topics/create"));
	}
	
	@Test
	public void cannot_create_a_new_topic_in_a_closed_forum() {
		
		Forum forum = forum().close();
		
		TopicsController controller = new TopicsController();
		controller.setForumRepository(forumRepository(forum));
		
		String result = controller.create("1234", model());
		assertThat(result, equalTo("redirect:/forums/list"));
		
	}
	
	@Test
	public void should_redirect_to_list_with_a_error_message_when_trying_to_post_in_a_non_existent_forum() {
		
		Model model = model();
		
		TopicsController controller = new TopicsController();
		controller.setForumRepository(forumRepository(null));
		
		String result = controller.create("1234", model);
		Message message = (Message) model.asMap().get("message");
		
		assertThat(result, equalTo("redirect:/forums/list"));
		assertThat(message, notNullValue());
		assertThat(message.isError(), is(true));
	}
	
	@Test
	public void when_trying_to_create_a_topic_in_closed_forum_should_put_a_message_in_model() {
		
		Forum forum = forum().close();
		Model model = model();
		
		TopicsController controller = new TopicsController();
		controller.setForumRepository(forumRepository(forum));
		
		controller.create("1234", model);
		assertThat(model.containsAttribute("message"), is(true));
	}
	
	private ForumRepository forumRepository(Forum forum) {
		ForumRepository forumRepository = mock(ForumRepository.class);
		when(forumRepository.get("1234")).thenReturn(forum);
		return forumRepository;
	}
	
	private Model model() {
		return new ExtendedModelMap();
	}

	private Forum forum() {
		return new Forum().withName("my forum").open();
	}
}
