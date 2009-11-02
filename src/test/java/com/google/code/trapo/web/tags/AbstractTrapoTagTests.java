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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Bamboozle Who
 * 
 * @since 02/11/2009
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractTrapoTagTests {

	@Mock protected JspWriter writer;
	@Mock protected PageContext pageContext;
	@Mock protected ServletContext servletContext;

	protected void runAgainstTag(AbstractTrapoTag tag, String expected) throws Exception {

		setupMocks();

		tag.setPageContext(pageContext);
		tag.setUrl("view/");
		tag.doStartTag();

		verify(writer).write(expected);
	}

	protected void setupMocks() {
		when(pageContext.getOut()).thenReturn(writer);
		when(pageContext.getServletContext()).thenReturn(servletContext);
		when(servletContext.getContextPath()).thenReturn("trapo/");
	}
}
