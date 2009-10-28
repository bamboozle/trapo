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

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

/**
 * @author Bamboozle Who
 *
 * @since 23/10/2009
 */
@Entity
@Table(name = "topics")
@Cache(region = "com.google.code.trapo.domain.Topic", usage = READ_WRITE)
public class Topic implements Comparable<Topic> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	@NotEmpty 
	private String title;
	@NotEmpty @Type(type = "text")
	private String text;

	private DateTime createdAt = new DateTime();
	
	@ManyToOne
	private Forum forum = new Forum();
	
	public Topic() {
		// hibernate is happy
	}
	
	public Topic(String title, String text) {
		this.title = title;
		this.text = text;
	}
	
	public int compareTo(Topic topic) {
		return this.createdAt.compareTo(topic.createdAt);
	}

	public String getId() {
		return id;
	}
	
	public Forum getForum() {
		return forum;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}

}
