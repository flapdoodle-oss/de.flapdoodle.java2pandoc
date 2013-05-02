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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Optional;


public class RegexLineMatcher implements ILineMatcher {
	private Pattern _pattern;

	public RegexLineMatcher(String pattern) {
		_pattern = Pattern.compile(pattern);
	}
	
	@Override
	public Optional<LineMatch> match(String line) {
		Matcher matcher = _pattern.matcher(line);
		if (matcher.matches()) {
			return Optional.of(new LineMatch(matcher.end(1)));
		}
		return Optional.absent();
	}

}
