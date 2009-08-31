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

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.NotEmpty;

/**
 * @author Bamboozle Who
 * 
 * @since 20/08/2009
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "forums")
public class Forum implements Serializable, Comparable<Forum> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String description;
	private Date createdAt;
	private boolean open;

	public Forum() {
		// now hibernate is happy. :-)
	}
	
	public Forum(String name) {
		this.name = name;
		this.createdAt = new Date();
	}
	
	public Forum withName(String name) {
		this.setName(name);
		return this;
	}

	public Forum open() {
		this.open = true;
		return this;
	}

	public int compareTo(Forum forum) {
		if (forum == null) {
			return 0;
		}
		return name.compareToIgnoreCase(forum.name);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public boolean isOpen() {
		return open;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

}
