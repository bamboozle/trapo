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

import static java.net.URLEncoder.encode;

import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.JspException;

/**
 * @author Bamboozle Who
 *
 * @since 31/08/2009
 */
@SuppressWarnings("serial")
public class UrlEncoderTag extends AbstractTrapoTag {

	@Override
	public String transformValue() throws JspException {
		if(getValue() == null) {
			return "";
		}
		try {
			return encode(getValue(), getEncoding());
		} catch (UnsupportedEncodingException ex) {
			throw new JspException(ex.getMessage(), ex);
		}
	}
	
}
