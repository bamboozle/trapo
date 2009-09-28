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
package com.google.code.trapo.web;

/**
 * @author Bamboozle Who
 * 
 * @since 27/09/2009
 */
public class Message {

	private final String text;
	private final Type type;

	enum Type {
		WARNING, INFORMATION, ERROR;
	}

	public Message(String text, Type type) {
		this.text = text;
		this.type = type;
	}

	public static Message warning(String text) {
		return new Message(text, Type.WARNING);
	}

	public static Message information(String text) {
		return new Message(text, Type.INFORMATION);
	}
	
	public static Message error(String text) {
		return new Message(text, Type.ERROR);
	}

	public boolean isWarning() {
		return type == Type.WARNING;
	}

	public boolean isInformation() {
		return type == Type.INFORMATION;
	}
	
	public boolean isError() {
		return type == Type.ERROR;
	}

	public String getText() {
		return text;
	}
	
	public Type getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return text;
	}

}
