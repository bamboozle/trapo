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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.trapo.domain.Forum;
import com.google.code.trapo.persistence.ForumRepository;

/**
 * @author Bamboozle Who
 *
 * @since 31/08/2009
 */
@Controller
@Transactional
public class ForumsController {

	@Autowired private ForumRepository forumRepository;
	
	@RequestMapping(value = { "/forum/save", "/forum/update" }, method = POST)
	public String save(@Valid Forum forum, Model model) {

		if(exists(forum)) {
			forumRepository.update(forum);
			model.addAttribute("message", information("Forum was updated"));
		} else {
			forumRepository.save(forum);
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
		
		forumRepository.delete(forum);
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
	
	protected void setForumRepository(ForumRepository forumRepository) {
		this.forumRepository = forumRepository;
	}
	
	private String redirectsToList(String message, Model model) {
		model.addAttribute("message", warning(message));
		return this.list(model);
	}
	
	private Forum forum(String name) {
		Forum forum = forumRepository.byName(decode(name));
		return forum;
	}
	
	private boolean exists(Forum forum) {
		return forum.getId() != null;
	}

	private String decode(String string) {
		try {
			return URLDecoder.decode(string, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return string;
		}
	}
}
