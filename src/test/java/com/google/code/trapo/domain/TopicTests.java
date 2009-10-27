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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.joda.time.DateTime;
import org.junit.Test;

/**
 * @author Bamboozle Who
 *
 * @since 23/10/2009
 */
public class TopicTests {

	@Test
	public void should_compare_two_topics_using_the_data_when_they_were_created() {

		DateTime now = new DateTime();
		
		Topic topic1 = new Topic();
		topic1.setCreatedAt(now);
		
		Topic topic2 = new Topic();
		topic2.setCreatedAt(now.plusDays(1));
		
		assertThat(topic1.compareTo(topic2), equalTo(-1));
		assertThat(topic2.compareTo(topic1), equalTo(1));
		assertThat(topic1.compareTo(topic1), equalTo(0));
	}
	
	@Test
	public void should_create_a_topic_with_the_given_title_and_text() {
		String title = "the title";
		String text = "the text";
		Topic topic = new Topic(title, text);
		assertThat(topic.getTitle(), CoreMatchers.equalTo(title));
		assertThat(topic.getText(), CoreMatchers.equalTo(text));
	}
	
	@Test
	public final void should_set_value_in_properties() {

		DateTime now = new DateTime();
		
		Topic topic = new Topic();
		topic.setCreatedAt(now);
		topic.setId("1234");
		topic.setText("text");
		topic.setTitle("title");
		
		assertThat(topic.getCreatedAt(), equalTo(now));
		assertThat(topic.getId(), equalTo("1234"));
		assertThat(topic.getText(), equalTo("text"));
		assertThat(topic.getTitle(), equalTo("title"));
	}
}
