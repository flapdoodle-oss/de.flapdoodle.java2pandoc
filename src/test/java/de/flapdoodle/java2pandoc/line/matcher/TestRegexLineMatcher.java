/**
 * Copyright (C) 2013
 *   Michael Mosmann <michael@mosmann.de>
 *
 * with contributions from
 * 	-
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.java2pandoc.line.matcher;

import static org.junit.Assert.*;

import org.junit.Test;

import de.flapdoodle.java2pandoc.line.matcher.RegexLineMatcher;


public class TestRegexLineMatcher {

	@Test
	public void patternShouldMatch() {
		RegexLineMatcher matcher = new RegexLineMatcher("(^\\s*)//\\s*-->\\s*$");
		assertTrue(matcher.match("  // -->").isPresent());
		assertTrue(matcher.match("  //-->").isPresent());
		assertTrue(matcher.match("// -->").isPresent());
		assertTrue(matcher.match("//-->").isPresent());
	}
	@Test
	public void patternShouldNotMatch() {
		RegexLineMatcher matcher = new RegexLineMatcher("(^\\s*)//\\s*-->\\s*$");
		assertFalse(matcher.match("a  // --> ").isPresent());
		assertFalse(matcher.match("  * --> a").isPresent());
	}
}
