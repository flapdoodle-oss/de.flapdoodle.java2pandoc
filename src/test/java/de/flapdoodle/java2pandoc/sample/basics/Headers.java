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
package de.flapdoodle.java2pandoc.sample.basics;

/**
 * -->
 * ## Headers
 * 
 * There are two kinds of headers, Setext and atx.
 * 
 * ### Setext-style headers
 * 
 * A setext-style header is a line of text “underlined” with a row of = signs (for a level one header)
 * of - signs (for a level two header):
 * 
 * <pre>
 * A level-one header
 * ==================
 * </pre>
 * 
 * <pre>
 * A level-two header
 * ------------------
 * </pre>
 * 
 * The header text can contain inline formatting, such as emphasis (see Inline formatting, below).
 * 
 * ### Atx-style headers
 * 
 * An Atx-style header consists of one to six # signs and a line of text, optionally followed by any number of # signs.
 * The number of # signs at the beginning of the line is the header level:
 * 
 * <pre>
 * ## A level-two header
 * </pre>
 * 
 * <pre>
 * ### A level-three header ###
 * </pre>
 * 
 * As with setext-style headers, the header text can contain formatting:
 * 
 * <pre>
 * # A level-one header with a [link](/url) and *emphasis*
 * </pre>
 * 
 * Standard markdown syntax does not require a blank line before a header. Pandoc does require this (except, of course,
 * at the beginning of the document).
 * The reason for the requirement is that it is all too easy for a # to end up at the beginning of a line by accident
 * (perhaps through line wrapping). Consider, for example:
 * 
 * <pre>
 * I like several of their flavors of ice cream:
 * #22, for example, and #5.
 * </pre>
 * 
 * <--
 * 
 * @author mosmann
 * 
 */
public class Headers {

}
