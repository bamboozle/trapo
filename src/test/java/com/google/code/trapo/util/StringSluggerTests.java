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

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Bamboozle Who
 *
 * @since 02/11/2009
 */
public class StringSluggerTests {

	@Test
	public void should_replace_empty_spaces_with_a_separator() {
		String string = "a simple string to test";
		assertEquals("a-simple-string-to-test", slug(string));
	}
	
	@Test
	public void should_lowercase_all_characters_when_slugging_the_string() {
		String string = "I Have A Lot OF UpperCases";
		assertEquals("i-have-a-lot-of-uppercases", slug(string));
	}
	
	@Test
	public void should_replace_continuous_empty_spaces_with_just_one_separator() {
		String string = "Some             whitespaces between";
		assertEquals("some-whitespaces-between", slug(string));
	}
	
	@Test
	public void should_return_empty_when_trying_to_slug_a_null_string() {
		assertEquals("", slug(null));
	}
	
	@Test
	public void should_replace_punctuations_with_separator() {
		String string = "I have some punctuations. Period. Did you believe that, right?";
		assertEquals("i-have-some-punctuations-period-did-you-believe-that-right", slug(string));
	}
	
	@Test
	public void should_replace_accented_chars_by_their_counter_parts() {
		String string = "áccênted chàrs in this string: áàãäâéèêëíìîïóòõôöúùûüñ";
		assertEquals("accented-chars-in-this-string-aaaaaeeeeiiiiooooouuuun", slug(string));
	}
	
	private String slug(String string) {
		return new StringSlugger().slug(string);
	}
}
