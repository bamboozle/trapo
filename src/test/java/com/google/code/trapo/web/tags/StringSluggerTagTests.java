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
package com.google.code.trapo.web.tags;

import static org.mockito.Mockito.doThrow;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.junit.Test;

/**
 * @author Bamboozle Who
 *
 * @since 01/11/2009
 */
public class StringSluggerTagTests extends AbstractTrapoTagTests {
	
	private StringSluggerTag tag = new StringSluggerTag();

	@Test
	public void should_slug_a_simple_value() throws Exception {
		tag.setEncoding("utf-8");
		tag.setValue("trapo is a cool forum");
		this.runAgainstTag(tag, "trapo/view/trapo-is-a-cool-forum");
	}
	
	@Test
	public void should_render_empty_when_the_value_is_null() throws Exception {
		tag.setValue(null);
		this.runAgainstTag(tag, "trapo/view/");
	}
	
	@Test
	public void should_lower_case_value_when_slugging() throws Exception {
		tag.setValue("Trapo Is A Cool Forum");
		this.runAgainstTag(tag, "trapo/view/trapo-is-a-cool-forum");
	}
	
	@Test
	public void should_replace_more_than_one_space_with_just_one_separator() throws Exception {
		tag.setValue("trapo      is a cool forum");
		this.runAgainstTag(tag, "trapo/view/trapo-is-a-cool-forum");
	}
	
	@Test
	public void should_replace_accented_chars_with_their_counter_parts() throws Exception {
		tag.setValue("trápô is a cöol forum");
		this.runAgainstTag(tag, "trapo/view/trapo-is-a-cool-forum");
	}
	
	@Test(expected = JspException.class)
	public void should_throws_a_exception_when_a_io_exception_happens() throws Exception {
		doThrow(new IOException()).when(writer).write("trapo/view/");
		this.runAgainstTag(tag, "trapo/view/");
	}
	
}
