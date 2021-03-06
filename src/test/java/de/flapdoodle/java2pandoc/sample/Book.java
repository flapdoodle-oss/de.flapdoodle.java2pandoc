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
package de.flapdoodle.java2pandoc.sample;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

/*
 * -->
 * % Headline
 * 
 * Why should i care?
 * <--
 */
public class Book {

	/**
	 * 
	 * @param d
	 *          a not so big value
	 */
	// -->
	public void methodContent(int d) {
		// <--
		int a = 2;
		int b = a * 3;
		// -->
	}

	// <--

	/*
	 * I can write comments, where ever i like
	 * without including it
	 */

	/*
	 * -->
	 * ... or with including it:
	 * <--
	 */

	// -->
	public int other(int b) {
		int c = 2;
		String s = "huiu";

		// <--

		/*
		 * Indentation could lead to trouble
		 */

		// -->
		return -1;
	}

	// <--

	// -->
	/**
	 * a javadoc
	 * 
	 * @param list
	 *          list
	 * @param dummy
	 *          does not mean anything
	 * @return useless stuff
	 */
	public List<String> sort(List<String> list, Optional<String> dummy) {
		return Lists.newArrayList();
	}
	// <--
}
