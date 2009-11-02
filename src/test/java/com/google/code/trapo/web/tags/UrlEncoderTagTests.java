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

import javax.servlet.ServletContext;
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

	private UrlEncoderTag tag = new UrlEncoderTag();
	
	@Test
	public void should_encode_a_url_using_default_encoding() throws Exception {
		tag.setEncoding("utf-8");
		tag.setValue("trapo is a cool forum");
		this.runAgainstTag(tag);
	}
	
	@Test
	public void should_encode_a_url_when_no_encoding_is_informed() throws Exception {
		
		tag.setValue("trapo is a cool forum");
		this.runAgainstTag(tag);
		
	}
	
	@Test
	public void should_render_empty_when_the_value_is_null() throws Exception {
		
		JspWriter writer = mock(JspWriter.class);

		PageContext context = mock(PageContext.class);
		ServletContext servletContext = mock(ServletContext.class);
		
		when(context.getOut()).thenReturn(writer);
		when(context.getServletContext()).thenReturn(servletContext);
		when(servletContext.getContextPath()).thenReturn("trapo/");
		
		tag.setValue(null);
		tag.setUrl("view/");
		tag.setPageContext(context);
		tag.doStartTag();
		
		verify(writer).write("trapo/view/");
		
	}
	
	@Test(expected = JspException.class)
	public void should_throws_a_exception_when_informed_encoding_is_not_supported() throws Exception {
		
		tag.setEncoding("non-existent-encoding");
		tag.setValue("trapo is a cool forum");
		
		this.runAgainstTag(tag);
	}
	
	@Test(expected = JspException.class)
	public void should_throws_a_exception_when_a_io_exception_happens() throws Exception {
		
		JspWriter writer = mock(JspWriter.class);
		PageContext context = mock(PageContext.class);
		ServletContext servletContext = mock(ServletContext.class);
		
		doThrow(new IOException()).when(writer).write("trapo/view/trapo+is+a+cool+forum");
		when(context.getOut()).thenReturn(writer);
		when(context.getServletContext()).thenReturn(servletContext);
		when(servletContext.getContextPath()).thenReturn("trapo/");

		tag.setPageContext(context);
		tag.setUrl("view/");
		tag.setValue("trapo is a cool forum");
		tag.doStartTag();
		
	}
	
	private void runAgainstTag(UrlEncoderTag tag) throws Exception {
		
		JspWriter writer = mock(JspWriter.class);
		PageContext context = mock(PageContext.class);
		ServletContext servletContext = mock(ServletContext.class);
		
		when(context.getOut()).thenReturn(writer);
		when(context.getServletContext()).thenReturn(servletContext);
		when(servletContext.getContextPath()).thenReturn("trapo/");
		
		tag.setPageContext(context);
		tag.setUrl("view/");
		tag.doStartTag();
		
		verify(writer).write("trapo/view/trapo+is+a+cool+forum");
		
	}
}
