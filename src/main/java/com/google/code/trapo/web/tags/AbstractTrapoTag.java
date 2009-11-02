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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * @author Bamboozle Who
 *
 * @since 02/11/2009
 */
@SuppressWarnings("serial")
public abstract class AbstractTrapoTag extends TagSupport {

	private String url;
	private String value;
	private String extension;
	private String encoding = "utf-8";
	
	
	public abstract String transformValue() throws JspException;
	
	@Override
	public int doStartTag() throws JspException {
		try {
			write(completeUrl());
			return BodyTag.SKIP_BODY;
		} catch (IOException ex) {
			throw new JspException(ex.getMessage(), ex);
		}
	}
	
	private void write(String value) throws IOException {
		this.pageContext.getOut().write(value);
	}
	
	private String completeUrl() throws JspException {
		StringBuilder completeUrl = contextUrl().append(url);
		if(!completeUrl.toString().endsWith("/")) {
			completeUrl.append("/");
		}
		
		completeUrl.append(transformValue());
		if(StringUtils.isNotEmpty(extension)) {
			completeUrl.append(".").append(extension);
		}
		return completeUrl.toString();
	}
	
	private StringBuilder contextUrl() {
		return new StringBuilder(pageContext.getServletContext().getContextPath());
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
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
