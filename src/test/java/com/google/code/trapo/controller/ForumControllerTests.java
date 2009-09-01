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

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.google.code.trapo.domain.Forum;
import com.google.code.trapo.persistence.ForumRepository;

/**
 * @author Bamboozle Who
 *
 * @since 31/08/2009
 */
public class ForumControllerTests {

	@Test
	public void should_redirect_to_show_page_when_saving_a_new_forum() {
		
		ForumsController controller = new ForumsController();
		controller.setForumRepository(repository());
		String result = controller.save(forum(), model());
		
		assertThat(result, equalTo("forums/show"));
		
	}
	
	@Test
	public void should_save_a_forum_when_all_properties_are_ok() {
		
		Forum forum = forum();
		
		ForumsController controller = new ForumsController();
		controller.setForumRepository(repositoryFor(forum));
		
		controller.save(forum, model());
		
		assertThat(forum.getId(), notNullValue());
	}
	
	@Test
	public void should_add_forum_attribute_to_model_when_saving_with_success() {
		
		Model model = model();
		
		ForumsController controller = new ForumsController();
		controller.setForumRepository(repository());
		
		controller.save(forum(), model);
		
		assertThat(model.containsAttribute("forum"), is(true));
		
	}
	
	@Test @SuppressWarnings("unchecked")
	public void should_return_all_forums_when_listing() {
		
		Model model = this.model();
		ForumRepository repository = mock(ForumRepository.class);
		when(repository.listAll()).thenReturn(asList(forum(), forum(), forum()));
		
		ForumsController controller = new ForumsController();
		controller.setForumRepository(repository);
		
		controller.list(model);
		
		List<Forum> forums = (List<Forum>) model.asMap().get("forums");
		
		assertThat(forums.size(), CoreMatchers.equalTo(3));
		assertThat(model.containsAttribute("forums"), is(true));
		
	}
	
	@Test
	public void should_redirect_to_list_page_when_listing_all_forums() {
		
		ForumsController controller = new ForumsController();
		controller.setForumRepository(this.repository());
		
		String result = controller.list(model());
		assertThat(result, equalTo("forums/list"));
		
	}
	
	@Test
	public void should_redirect_to_create_page_when_asking_to_create_a_new_forum() {
		
		ForumsController controller = new ForumsController();
		controller.setForumRepository(this.repository());
		
		String result = controller.create();
		assertThat(result, equalTo("forums/create"));
	}
	
	private Forum forum() {
		Forum forum = new Forum();
		forum.setName("my new name");
		forum.setDescription("my description");
		forum.open();
		return forum;
	}
	
	private ForumRepository repository() {
		return Mockito.mock(ForumRepository.class);
	}
	
	private ForumRepository repositoryFor(final Forum forum) {
		ForumRepository repository = Mockito.mock(ForumRepository.class);
		Mockito.when(repository.save(forum)).thenAnswer(new Answer<Forum>() {
			public Forum answer(InvocationOnMock invocation) throws Throwable {
				forum.setId("1234");
				return forum;
			}
		});
		return repository;
	}
	
	private Model model() {
		return new ExtendedModelMap();
	}
}
