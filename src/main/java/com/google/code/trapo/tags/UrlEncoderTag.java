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
package com.google.code.trapo.tags;

import static java.net.URLEncoder.encode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Bamboozle Who
 *
 * @since 31/08/2009
 */
@SuppressWarnings("serial")
public class UrlEncoderTag extends TagSupport {

	private String value;
	private String encoding = "utf-8";
	
	@Override
	public final int doStartTag() throws JspException {
		try {
			
			if(this.value == null) {
				this.value = "";
			}
			
			write(encode(this.value, this.encoding));
			return BodyTag.SKIP_BODY;
			
		} catch (UnsupportedEncodingException ex) {
			throw new JspException(ex);
		} catch (IOException ex) {
			throw new JspException(ex);
		}
	}
	
	private void write(String value) throws IOException {
		this.pageContext.getOut().write(value);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
}
