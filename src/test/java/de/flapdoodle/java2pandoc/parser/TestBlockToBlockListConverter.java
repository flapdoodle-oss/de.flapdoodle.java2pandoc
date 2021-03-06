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
package de.flapdoodle.java2pandoc.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.line.matcher.ILineMatcher;
import de.flapdoodle.java2pandoc.line.matcher.JavaSourceLineMatcher;

/**
 * -->
 * ## Sample of Test Code in Documentation
 * 
 * You can set the mark to the beginning of the code block which should show up in your documentation.
 * You need the second mark on the end of the block.
 * 
 * <code>
 * int a=2;
 * // -->
 * String s="This is the code which will show up";
 * // <--
 * s="and this not";
 * </code>
 * 
 * <--
 * 
 * @author mosmann
 * 
 */
public class TestBlockToBlockListConverter {

	/**
	 * -->
	 * ### Test Code for a simple Comment
	 * <--
	 * 
	 * @throws ParseException
	 */
	@Test
	public void simpleComment() throws ParseException {
		// -->
		ILineMatcher startMatcher = JavaSourceLineMatcher.startMatcher();
		ILineMatcher endMatcher = JavaSourceLineMatcher.endMatcher();
		BlockToBlockListConverter converter = new BlockToBlockListConverter(startMatcher, endMatcher);

		List<String> lines = Lists.newArrayList();

		lines.add(" // -->");
		lines.add(" // shift one left");
		lines.add(" // this line too");
		lines.add(" // <--");

		List<Block> blocks = converter.convert(new Block(lines));

		assertEquals(1, blocks.size());
		Block block = blocks.get(0);
		assertNotNull(block);
		assertEquals("// shift one left", block.lines().get(0));
		assertEquals("// this line too", block.lines().get(1));
		// <--
	}

	/**
	 * -->
	 * ### Test Code for an embedded Marker
	 * <--
	 * 
	 * @throws ParseException
	 */
	@Test
	public void embeddedComment() throws ParseException {
		// -->
		ILineMatcher startMatcher = JavaSourceLineMatcher.startMatcher();
		ILineMatcher endMatcher = JavaSourceLineMatcher.endMatcher();
		BlockToBlockListConverter converter = new BlockToBlockListConverter(startMatcher, endMatcher);

		List<String> lines = Lists.newArrayList();

		lines.add(" // -->");
		lines.add(" // shift one left");
		lines.add(" 	// <--");
		lines.add(" 	// -->");
		lines.add(" 	// shift one left (embedded)");
		lines.add(" 	// this line too (embedded)");
		lines.add(" 	// <--");
		lines.add(" 	// -->");
		lines.add(" // this line too");
		lines.add(" // <--");

		List<Block> blocks = converter.convert(new Block(lines));

		assertEquals(1, blocks.size());
		Block block = blocks.get(0);
		assertNotNull(block);
		assertEquals("// shift one left", block.lines().get(0));
		assertEquals("	// shift one left (embedded)", block.lines().get(1));
		assertEquals("	// this line too (embedded)", block.lines().get(2));
		assertEquals("// this line too", block.lines().get(3));
		// <--
	}
}
