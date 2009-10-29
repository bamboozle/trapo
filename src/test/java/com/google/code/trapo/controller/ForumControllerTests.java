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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import com.google.code.trapo.domain.Forum;
import com.google.code.trapo.persistence.ForumRepository;
import com.google.code.trapo.persistence.TopicRepository;
import com.google.code.trapo.web.Message;

/**
 * @author Bamboozle Who
 *
 * @since 31/08/2009
 */
public class ForumControllerTests {

	@Test
	public void should_update_a_existent_forum() {
		
		Forum forum = forum();
		forum.setId("1234"); // already on database
		
		Validator validator = mock(Validator.class);
		
		ForumRepository repository = this.repository();
		ForumsController controller = controllerWith(repository, validator);
		
		String result = controller.save(forum, errors(), model());
		assertThat(result, equalTo("forums/show"));
		
		verify(repository).update(forum);
	}
	
	@Test
	public void should_not_update_forum_when_there_are_invalid_field_values() {
		
		Validator validator = mock(Validator.class);
		
		ForumRepository repository = this.repository();
		ForumsController controller = controllerWith(repository, validator);
		
		BindingResult errors = errors();
		BDDMockito.given(errors.hasErrors()).willReturn(true);
		
		String result = controller.save(forum(), errors, model());
		assertThat(result, equalTo("forums/create"));
	}

	@Test
	public void verify_that_update_method_add_a_information_message() {
		
		Forum forum = forum();
		forum.setId("1234");
		
		Model model = this.model();
		
		ForumRepository repository = repository();
		Validator validator = mock(Validator.class);
		ForumsController controller = controllerWith(repository, validator);
		controller.save(forum, errors(), model);
		
		Message message = (Message)model.asMap().get("message");
		assertThat(message, notNullValue());
		verify(repository).update(forum);
		
	}
	
	@Test
	public void should_redirect_to_show_page_when_saving_a_new_forum() {
		
		Forum forum = forum();
		Validator validator = mock(Validator.class);
		ForumsController controller = controllerWith(repository(), validator);
		String result = controller.save(forum, errors(), model());
		
		assertThat(result, equalTo("forums/show"));
	}
	
	@Test
	public void should_save_a_forum_when_all_properties_are_ok() {
		
		Forum forum = forum();
		BindingResult errors = errors();
		
		given(errors.hasErrors()).willReturn(false);
		
		Validator validator = mock(Validator.class);
		ForumsController controller = controllerWith(repositoryFor(forum), validator);
		controller.save(forum, errors, model());
		
		assertThat(forum.getId(), notNullValue());
	}
	
	@Test
	public void should_open_forums_when_saving() {
		Forum forum = forum();
		Validator validator = mock(Validator.class);
		ForumsController controller = controllerWith(repositoryFor(forum), validator);
		controller.save(forum, errors(), model());
		assertThat(forum.isOpen(), is(true));
	}
	
	@Test
	public void should_add_forum_attribute_to_model_when_saving_with_success() {
		
		Model model = model();
		Forum forum = forum();
		Validator validator = mock(Validator.class);
		ForumsController controller = controllerWith(repository(), validator);
		controller.save(forum, errors(), model);
		
		assertThat(model.containsAttribute("forum"), is(true));
	}
	
	@Test @SuppressWarnings("unchecked")
	public void should_return_all_forums_when_listing() {
		
		Model model = this.model();
		ForumRepository repository = mock(ForumRepository.class);
		when(repository.listAll()).thenReturn(asList(forum(), forum(), forum()));
		
		ForumsController controller = controllerWith(repository);
		
		controller.list(model);
		
		List<Forum> forums = (List<Forum>) model.asMap().get("forums");
		
		assertThat(forums.size(), CoreMatchers.equalTo(3));
		assertThat(model.containsAttribute("forums"), is(true));
	}
	
	@Test
	public void should_redirect_to_list_page_when_listing_all_forums() {
		
		ForumsController controller = controllerWith(repository());
		
		String result = controller.list(model());
		assertThat(result, equalTo("forums/list"));
	}
	
	@Test
	public void should_redirect_to_create_page_when_asking_to_create_a_new_forum() {
		
		ForumsController controller = controllerWith(repository());
		
		String result = controller.create(model());
		assertThat(result, equalTo("forums/create"));
	}
	
	@Test
	public void should_find_a_forum_by_name() {
		
		Forum forum = forum();
		Model model = model();
		
		ForumRepository repository = mock(ForumRepository.class);
		when(repository.byName("my new forum")).thenReturn(forum);
		
		ForumsController controller = controllerWith(repository);
		controller.setTopicsRepository(mock(TopicRepository.class));
		controller.show("my new forum", model);
		
		assertThat(model.containsAttribute("forum"), is(true));
	}
	
	@Test
	public void should_redirect_to_list_when_trying_to_show_a_forum_that_not_exists() {
		
		ForumRepository repository = repository();
		when(repository.byName("non existent forum")).thenReturn(null);
		
		ForumsController controller = controllerWith(repository);
		String result = controller.show("non existent forum", model());
		
		assertThat(result, equalTo("forums/list"));
	}
	
	@Test
	public void should_warning_the_user_when_trying_to_show_a_forum_that_not_exists() {
		
		ForumRepository repository = repository();
		when(repository.byName("non existent forum")).thenReturn(null);
		
		Model model = model();
		
		ForumsController controller = controllerWith(repository);
		controller.show("non existent forum", model);
		
		Message message = (Message) model.asMap().get("message");
		assertThat(message.isWarning(), is(true));
	}
	
	@Test
	public void should_redirect_to_list_when_trying_to_edit_a_forum_that_not_exists() {
		
		ForumsController controller = controllerForNonExistentForum();
		
		String result = controller.edit("to edit", model());
		assertThat(result, equalTo("forums/list"));
	}
	
	@Test
	public void should_put_a_message_in_model_when_trying_edit_a_forum_that_not_exists() {
		
		Model model = model();
		
		ForumsController controller = controllerForNonExistentForum();
		controller.edit("to edit", model);
		
		Message message = (Message)model.asMap().get("message");
		assertThat(message.getText(), equalTo("Forum to edit was not found."));
	}
	
	@Test
	public void should_redirects_to_create_page_when_editing_a_existent_forum() {
		ForumsController controller = controllerToExistentForum(forum());
		assertThat(controller.edit("to edit", model()), equalTo("forums/create"));
	}
	
	@Test
	public void should_put_forum_in_model_when_editing_a_existent_forum() {
		
		Forum forum = forum();
		Model model = model();
		
		ForumsController controller = controllerToExistentForum(forum);
		controller.edit("to edit", model);
		
		assertThat((Forum)model.asMap().get("forum"), equalTo(forum));
	}
	
	@Test
	public void should_delete_a_existent_forum() {
		
		ForumRepository repository = repository();
		when(repository.get("1234")).thenReturn(forum());
		
		ForumsController controller = controllerWith(repository);
		
		Model model = model();
		
		String result = controller.delete("1234", model);
		assertThat(result, equalTo("forums/list"));
		
		Message message = (Message) model.asMap().get("message");
		assertThat(message.isInformation(), is(true));
	}
	
	@Test
	public void should_redirect_to_list_with_message_when_trying_to_delete_a_non_existent_forum() {
		ForumRepository repository = repository();
		when(repository.get("1234")).thenReturn(null);
		
		ForumsController controller = controllerWith(repository);
		
		Model model = model();
		
		String result = controller.delete("1234", model);
		assertThat(result, equalTo("forums/list"));
		
		Message message = (Message) model.asMap().get("message");
		assertThat(message.isWarning(), is(true));
	}
	
	private ForumsController controllerWith(ForumRepository repository, Validator validator) {
		ForumsController controller = controllerWith(repository);
		controller.setValidator(validator);
		return controller;
	}
	
	private ForumsController controllerWith(ForumRepository repository) {
		ForumsController controller = new ForumsController();
		controller.setForumRepository(repository);
		return controller;
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
		when(repository.add(forum)).thenAnswer(new Answer<Forum>() {
			public Forum answer(InvocationOnMock invocation) throws Throwable {
				forum.setId("1234");
				return forum;
			}
		});
		return repository;
	}
	
	private ForumsController controllerToExistentForum(Forum forum) {
		ForumRepository forumRepository = mock(ForumRepository.class);
		when(forumRepository.get("to edit")).thenReturn(forum);
		ForumsController controller = controllerWith(forumRepository);
		return controller;
	}
	
	private ForumsController controllerForNonExistentForum() {
		ForumRepository repository = mock(ForumRepository.class);
		when(repository.byName("to edit")).thenReturn(null);
		ForumsController controller = controllerWith(repository);
		return controller;
	}
	
	private Model model() {
		return new ExtendedModelMap();
	}
	
	private BindingResult errors() {
		return mock(BindingResult.class);
	}
}
