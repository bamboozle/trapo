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
package com.google.code.trapo.util;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * @author Bamboozle Who
 *
 * @since 02/11/2009
 */
public class StringSlugger {
	
	private static final String ACCENTED_CHARS = "áàãäâéèêëíìîïóòõôöúùûüñ";
	private static final String NOT_ACCENTED_CHARS = "aaaaaeeeeiiiiooooouuuun";

	public static String slug(String string) {
		return new Slugger(string)
				  .lowercase()
				  .withoutPunctuations()
				  .replaceAccentedChars()
				  .slug();
	}
	
	private static class Slugger {
		
		private final String value;

		public Slugger(String value) {
			this.value = value != null ? value : "";
		}
		
		public Slugger replaceAccentedChars() {
			return new Slugger(StringUtils.replaceChars(value, ACCENTED_CHARS, NOT_ACCENTED_CHARS));
		}

		public Slugger lowercase() {
			return new Slugger(value.toLowerCase(Locale.getDefault()));
		}
		
		public Slugger withoutPunctuations() {
			return new Slugger(value.replaceAll("\\p{Punct}", " "));
		}

		public String slug() {
			return value.trim().replaceAll("\\s+", "-");
		}
		
	}

}
