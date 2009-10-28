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

import static com.google.code.trapo.web.Message.information;
import static com.google.code.trapo.web.Message.warning;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.trapo.domain.Forum;
import com.google.code.trapo.domain.Topic;
import com.google.code.trapo.persistence.ForumRepository;
import com.google.code.trapo.persistence.TopicRepository;

/**
 * @author Bamboozle Who
 *
 * @since 31/08/2009
 */
@Controller
@Transactional
public class ForumsController extends AbstractController<Forum> {

	@Autowired private ForumRepository forumRepository;
	@Autowired private TopicRepository topicRepository;
	@Autowired private Validator validator;
	
	@RequestMapping(value = { "/forum/save", "/forum/update" }, method = POST)
	public String save(@ModelAttribute Forum forum, BindingResult errors, Model model) {
		
		if(this.hasErrors(forum, errors)) {
			model.addAttribute("forum", forum);
			return "forums/create";
		}
		
		if(exists(forum)) {
			forumRepository.update(forum);
			model.addAttribute("message", information("Forum was updated"));
		} else {
			forumRepository.add(forum.open());
			model.addAttribute("message", information("New forum was created"));
		}
		model.addAttribute("forum", forum);
		return "forums/show";
	}

	@RequestMapping(value = "/forum/delete", method = POST)
	public String delete(String id, Model model) {
		Forum forum = this.forumRepository.get(id);
		if(forum == null) {
			return redirectsToList("Forum to delete was not found.", model);
		}
		forumRepository.remove(forum);
		model.addAttribute("message", information("Forum was successfull deleted"));
		return list(model);
	}
	
	@RequestMapping( { "/forums", "/forums/list" })
	public String list(Model model) {
		List<Forum> forums = forumRepository.listAll();
		model.addAttribute("forums", forums);
		return "forums/list";
	}
	
	@RequestMapping({"/forum/{name}"})
	public String show(@PathVariable String name, Model model) {
		Forum forum = forum(name);
		if(forum == null) {
			return redirectsToList("Forum with name "+ name + " not found", model);
		}
		model.addAttribute("topics", topics(forum));
		model.addAttribute("forum", forum);
		return "forums/show";
	}
	
	@RequestMapping("/forum/create")
	public String create(Model model) {
		model.addAttribute("forum", new Forum());
		return "forums/create";
	}
	
	@RequestMapping(value = "/forum/edit", method = POST)
	public String edit(String id, Model model) {
		Forum forum = this.forumRepository.get(id);
		if(forum == null) {
			return redirectsToList("Forum to edit was not found.", model);
		}
		model.addAttribute("forum", forum);
		return "forums/create";
	}
	
	@Override
	public Validator validator() {
		return validator;
	}
	
	protected void setForumRepository(ForumRepository forumRepository) {
		this.forumRepository = forumRepository;
	}
	
	protected void setValidator(Validator validator) {
		this.validator = validator;
	}
	
	private String redirectsToList(String message, Model model) {
		model.addAttribute("message", warning(message));
		return this.list(model);
	}
	
	private Forum forum(String name) {
		Forum forum = forumRepository.byName(decode(name));
		return forum;
	}

	private List<Topic> topics(Forum forum) {
		return this.topicRepository.topicsFor(forum);
	}
	
}
