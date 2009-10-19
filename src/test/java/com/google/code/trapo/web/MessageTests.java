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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author Bamboozle Who
 * 
 * @since 27/09/2009
 */
public class MessageTests {
	
	@Test
	public void should_create_warning_messages() {
		Message message = Message.warning("my warning message");
		assertThat(message.isWarning(), is(true));
	}
	
	@Test
	public void should_create_warning_messages_with_the_given_messageg() {
		Message message = Message.warning("my message");
		assertThat(message.getText(), equalTo("my message"));
	}
	
	@Test
	public void should_create_information_messages() {
		Message message = Message.information("my message");
		assertThat(message.isInformation(), is(true));
	}
	
	@Test
	public void should_create_information_messages_with_the_given_text() {
		Message message = Message.information("my message");
		assertThat(message.getText(), equalTo("my message"));
	}
	
	@Test
	public void should_create_error_messages() {
		Message message = Message.error("my error message");
		assertThat(message.isError(), is(true));
	}
	
	@Test
	public void should_create_error_messages_with_the_given_text() {
		Message message = Message.error("my error message");
		assertThat(message.getText(), equalTo("my error message"));
	}
	
	@Test
	public void assert_that_warning_messages_are_not_information_messages() {
		Message message = Message.warning("my message");
		assertThat(message.isInformation(), is(false));
	}
	
	@Test
	public void assert_that_warning_messages_are_not_error_messages() {
		Message message = Message.warning("my message");
		assertThat(message.isError(), is(false));
	}
	
	@Test
	public void assert_that_information_messages_are_not_warning_messages() {
		Message message = Message.information("my message");
		assertThat(message.isWarning(), is(false));
	}
	
	@Test
	public void assert_that_information_messages_are_not_error_messages() {
		Message message = Message.information("my message");
		assertThat(message.isError(), is(false));
	}
	
	@Test
	public void assert_that_error_messages_are_not_warning_messages() {
		Message message = Message.error("my message");
		assertThat(message.isWarning(), is(false));
	}
	
	@Test
	public void assert_that_error_messages_are_not_information_messages() {
		Message message = Message.error("my message");
		assertThat(message.isInformation(), is(false));
	}
	
	@Test
	public void message_type_must_match_with_is_methods() {
		Message message = Message.error("error");
		boolean matchError = message.getType() == Message.Type.ERROR && message.isError();
		assertThat(matchError, is(true));
		
		message = Message.information("information");
		matchError = message.getType() == Message.Type.INFORMATION && message.isInformation();
		assertThat(matchError, is(true));
		
		message = Message.warning("warning");
		matchError = message.getType() == Message.Type.WARNING && message.isWarning();
		assertThat(matchError, is(true));
	}
	
	@Test
	public void message_to_string_must_print_the_text_message() {
		Message message = Message.error("error message");
		assertThat(message.toString(), equalTo("error message"));
	}
}
