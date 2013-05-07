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

import de.flapdoodle.java2pandoc.parser.TestBlockToBlockListConverter;
import de.flapdoodle.java2pandoc.sample.basics.Headers;
import de.flapdoodle.java2pandoc.sample.basics.JavaDocTags;

/**
 * -->
 * % JavaSource to Pandoc Converter Documentation
 * % Michael Mosmann
 * 
 * Intro
 * =====
 * 
 * This is an experiment. We want to generate a documentation from source code, so that in the best case the code in the
 * documentation is fresh and tested. Maybe this can simplify the process of code documentation, because you will have your documentation and
 * your code in the same source file.
 * 
 * The only thing you have to do is to mark snippets in your source file which should be rendered to the final documentation. It does not
 * matter if you mark a comment or a code. We will be able to detect the difference.
 * 
 * To mark your snippet, you have to write something like this in your JavaDoc comment to mark the start:
 * <pre>
 * 	* -->
 * </pre>
 * and 
 * <pre>
 * 	* <--
 * </pre>
 * to mark the end.
 * 
 * It is not much different for your code or line comments. This for the start:
 * <pre>
 * 	// -->
 * </pre>
 * and this
 * <pre>
 * 	// <--
 * </pre>
 * for the end.
 * 
 * {@link JavaDocTags}
 * 
 * {@link Headers}
 * 
 * {@link TestBlockToBlockListConverter}
 * 
 * <--
 * 
 * @author mosmann
 * 
 */
public class Start {

	// line comments
	//can start as soon as possible

	/**
	 * block comments not
	 */
}
