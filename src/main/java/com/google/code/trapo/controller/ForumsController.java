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

import static java.lang.String.format;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

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

	@Autowired
	private ForumRepository forumRepository;
	
	@RequestMapping(value = "/forum/save", method = POST)
	public String save(Forum forum, Model model) {
		forumRepository.save(forum);
		model.addAttribute("forum", forum);
		return "forums/show";
	}
	
	@RequestMapping({"/forums/", "/forums", "/forums/list"})
	public String list(Model model) {
		List<Forum> forums = forumRepository.listAll();
		model.addAttribute("forums", forums);
		return "forums/list";
	}
	
	@RequestMapping({"/forum/{name}"})
	public String show(@PathVariable String name, Model model) {
		Forum forum = forumRepository.byName(decode(name));
		model.addAttribute("forum", forum);
		return "forums/show";
	}
	
	@RequestMapping("/forum/create")
	public String create() {
		return "forums/create";
	}
	
	@RequestMapping("/forum/edit/{name}")
	public String edit(@PathVariable String name, Model model) {
		Forum forum = forumRepository.byName(name);
		if(forum == null) {
			return redirectsToList(name, model);
		}
		model.addAttribute("forum", forum);
		return "forums/edit";
	}
	
	@RequestMapping(value = "/forum/update", method = POST)
	public String update(Forum forum, Model model) {
		forumRepository.update(forum);
		model.addAttribute("forum", forum);
		return "forums/show";
	}
	
	@RequestMapping("/forum/close/{name}")
	public String close(@PathVariable String name, Model model) {
		
		Forum forum = forumRepository.byName(name);
		if(forum == null) {
			return redirectsToList(name, model);
		}
		
		forum.close();
		model.addAttribute("forum", forum);
		
		return "forums/show";
	}
	
	protected void setForumRepository(ForumRepository forumRepository) {
		this.forumRepository = forumRepository;
	}
	
	private String redirectsToList(String name, Model model) {
		model.addAttribute("message", format("Forum %s was not found.", name));
		return this.list(model);
	}
	
	private String decode(String string) {
		try {
			return URLDecoder.decode(string, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return string;
		}
	}

}
