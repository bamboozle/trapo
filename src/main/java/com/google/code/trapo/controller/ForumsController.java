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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.google.code.trapo.domain.Forum;
import com.google.code.trapo.persistence.ForumRepository;

/**
 * @author Bamboozle Who
 *
 * @since 31/08/2009
 */
public class ForumsController {

	@Autowired
	private ForumRepository forumRepository;
	
	public String save(Forum forum, Model model) {
		forumRepository.save(forum);
		model.addAttribute("forum", forum);
		return "forums/show";
	}
	
	protected void setForumRepository(ForumRepository forumRepository) {
		this.forumRepository = forumRepository;
	}
}
