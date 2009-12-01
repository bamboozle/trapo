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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

/**
 * @author Bamboozle Who
 * 
 * @since 01/12/2009
 */
@Entity
@Table(name = "replies")
public class Reply implements Comparable<Reply> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@NotNull @NotEmpty
	private String title;
	@NotNull @NotEmpty
	private String text;
	
	private DateTime repliedAt = new DateTime();
	private DateTime updatedAt = new DateTime();
	
	@ManyToOne @NotNull
	private Topic topic;
	
	public Reply(String title, String text) {
		this.title = title;
		this.text = text;
	}

	public void inReplyTo(Topic topic) {
		this.topic = topic;
		this.repliedAt = new DateTime();
		this.updatedAt = new DateTime();
	}

	public int compareTo(Reply reply) {
		if (reply == null) {
			return 0;
		}
		return this.repliedAt.compareTo(reply.repliedAt);
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}
	
	public Topic getTopic() {
		return topic;
	}

	public DateTime getRepliedAt() {
		return repliedAt;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public void setRepliedAt(DateTime repliedAt) {
		this.repliedAt = repliedAt;
	}

	public void setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
