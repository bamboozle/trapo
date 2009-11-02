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

import static com.google.code.trapo.web.Message.error;
import static com.google.code.trapo.web.Message.information;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.trapo.domain.Forum;
import com.google.code.trapo.domain.Topic;
import com.google.code.trapo.persistence.ForumRepository;
import com.google.code.trapo.persistence.TopicRepository;

/**
 * @author Bamboozle Who
 * 
 * @since 23/10/2009
 */
@Controller
@Transactional
public class TopicsController extends AbstractController<Topic> {

	@Autowired private ForumRepository forumRepository;
	@Autowired private TopicRepository topicRepository;
	@Autowired private Validator validator;
	
	@RequestMapping("/topic/create")
	public String create(String id, Model model) {
		Forum forum = forum(id);
		if (!forum.isOpen()) {
			model.addAttribute("message", error("You can't post in this forum. It is not open or it does not exists."));
			return "redirect:/view/forums/list";
		}
		model.addAttribute("forum", forum);
		model.addAttribute("topic", new Topic());
		return "topics/create";
	}
	
	@RequestMapping(value = { "/topic/save", "/topic/update" }, method = POST)
	public String save(@ModelAttribute Topic topic, BindingResult errors, Model model) {
		if(this.hasErrors(topic, errors)) {
			model.addAttribute("forum", forum(topic.getForum().getId()));
			model.addAttribute("message", error("Oops, there are some errors in form"));
			return "topics/create";
		}
		if(exists(topic)) {
			topicRepository.update(topic);
			model.addAttribute("message", information("Topic was updated"));
		} else {
			topicRepository.add(topic);
			model.addAttribute("message", information("New Topic was created"));
		}
		model.addAttribute("topic", topic);
		return "topics/show";
	}
	
	protected void setForumRepository(ForumRepository forumRepository) {
		this.forumRepository = forumRepository;
	}
	
	protected void setTopicRepository(TopicRepository topicRepository) {
		this.topicRepository = topicRepository;
	}
	
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	
	private Forum forum(String id) {
		Forum forum = this.forumRepository.get(id);
		return forum != null ? forum : new Forum().close();
	}

	@Override
	public Validator validator() {
		return validator;
	}

}
