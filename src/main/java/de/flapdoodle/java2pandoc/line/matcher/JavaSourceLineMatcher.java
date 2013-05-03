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


public final class JavaSourceLineMatcher {
	public static ILineMatcher startMatcher() {
		return new RegexLineMatcher("(^\\s*)(//|\\*)\\s*-->\\s*$");
	}
	public static ILineMatcher endMatcher() {
		return new RegexLineMatcher("(^\\s*)(//|\\*)\\s*<--\\s*$");
	}
}