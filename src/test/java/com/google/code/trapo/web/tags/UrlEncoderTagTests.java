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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.junit.Test;

/**
 * @author Bamboozle Who
 *
 * @since 31/08/2009
 */
public class UrlEncoderTagTests {

	@Test
	public void should_encode_a_url_using_default_encoding() throws Exception {
		
		UrlEncoderTag tag = new UrlEncoderTag();
		tag.setEncoding("utf-8");
		tag.setValue("trapo is a cool forum");
		this.runAgainstTag(tag);
		
	}
	
	@Test
	public void should_encode_a_url_when_no_encoding_is_informed() throws Exception {
		
		UrlEncoderTag tag = new UrlEncoderTag();
		tag.setValue("trapo is a cool forum");
		this.runAgainstTag(tag);
		
	}
	
	@Test
	public void should_render_empty_when_the_value_is_null() throws Exception {
		
		JspWriter writer = mock(JspWriter.class);

		PageContext context = mock(PageContext.class);
		when(context.getOut()).thenReturn(writer);
		
		UrlEncoderTag tag = new UrlEncoderTag();
		tag.setValue(null);
		tag.setPageContext(context);
		tag.doStartTag();
		
		verify(writer).write("");
		
	}
	
	@Test(expected = JspException.class)
	public void should_throws_a_exception_when_informed_encoding_is_not_supported() throws Exception {
		
		UrlEncoderTag tag = new UrlEncoderTag();
		tag.setEncoding("non-existent-encoding");
		tag.setValue("trapo is a cool forum");
		
		this.runAgainstTag(tag);
	}
	
	@Test(expected = JspException.class)
	public void should_throws_a_exception_when_a_io_exception_happens() throws Exception {
		
		UrlEncoderTag tag = new UrlEncoderTag();
		
		JspWriter writer = mock(JspWriter.class);
		doThrow(new IOException()).when(writer).write("trapo+is+a+cool+forum");
		
		PageContext context = mock(PageContext.class);
		when(context.getOut()).thenReturn(writer);

		tag.setPageContext(context);
		tag.setValue("trapo is a cool forum");
		tag.doStartTag();
		
	}
	
	private void runAgainstTag(UrlEncoderTag tag) throws Exception {
		
		JspWriter writer = mock(JspWriter.class);

		PageContext context = mock(PageContext.class);
		when(context.getOut()).thenReturn(writer);
		
		tag.setPageContext(context);
		tag.doStartTag();
		
		verify(writer).write("trapo+is+a+cool+forum");
		
	}
}
