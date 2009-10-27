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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.code.trapo.domain.Forum;
import com.google.code.trapo.persistence.ForumRepository;

/**
 * @author Bamboozle Who
 * 
 * @since 23/10/2009
 */
@Controller
public class TopicsController {

	@Autowired private ForumRepository forumRepository;
	
	@RequestMapping(value = "/topic/create")
	public String create(@RequestParam("id") String id, Model model) {
		if (!forum(id).isOpen()) {
			model.addAttribute("message", error("You can't post in this forum. It is not open or it does not exists."));
			return "redirect:/view/forums/list";
		}
		return "topics/create";
	}
	
	protected void setForumRepository(ForumRepository forumRepository) {
		this.forumRepository = forumRepository;
	}
	
	private Forum forum(String id) {
		Forum forum = this.forumRepository.get(id);
		return forum != null ? forum : new Forum().close();
	}

}
