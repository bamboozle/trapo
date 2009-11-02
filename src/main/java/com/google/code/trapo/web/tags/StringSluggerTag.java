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

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Bamboozle Who
 * 
 * @since 01/11/2009
 */
@SuppressWarnings("serial")
public class StringSluggerTag extends TagSupport {

	private String value;
	private String encoding = "utf-8";
	
	private static final String ACCENTED_CHARS = "";
	private static final String NOT_ACCENTED_CHARS = "";
	
	@Override
	public int doStartTag() throws JspException {
		try {
			write(slug(value));
			return BodyTag.SKIP_BODY;
		} catch (IOException ex) {
			throw new JspException(ex.getMessage(), ex);
		}
	}
	
	private String slug(String string) {
		if(string == null) {
			return "";
		}
		String temp = string.toLowerCase(Locale.getDefault());
		return temp.replaceAll("\\s+", "-");
	}
	
	private void write(String value) throws IOException {
		this.pageContext.getOut().write(value);
	}

	public String getValue() {
		return value;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
