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
package de.flapdoodle.java2pandoc.block;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import de.flapdoodle.java2pandoc.line.matcher.JavaSourceLineMatcher;

public class TestMarkedBlockLineProcessor {

	@Test
	public void simpleComment() throws ParseException {
		CollectingBlockProcessor blockProcessor = new CollectingBlockProcessor();
		MarkedBlockLineProcessor blockLineProcessor = new MarkedBlockLineProcessor(blockProcessor,
				JavaSourceLineMatcher.startMatcher(), JavaSourceLineMatcher.endMatcher());
		
		blockLineProcessor.process(" // -->");
		blockLineProcessor.process(" // shift one left");
		blockLineProcessor.process(" // this line too");
		blockLineProcessor.process(" // <--");

		Block block = blockProcessor.blocks().get(0);
		assertNotNull(block);
		assertEquals("// shift one left",block.lines().get(0));
		assertEquals("// this line too",block.lines().get(1));
	}

	@Test
	public void embeddedComment() throws ParseException {
		CollectingBlockProcessor blockProcessor = new CollectingBlockProcessor();
		MarkedBlockLineProcessor blockLineProcessor = new MarkedBlockLineProcessor(blockProcessor,
				JavaSourceLineMatcher.startMatcher(), JavaSourceLineMatcher.endMatcher());
		
		blockLineProcessor.process(" // -->");
		blockLineProcessor.process(" // shift one left");
		blockLineProcessor.process(" 	// <--");
		blockLineProcessor.process(" 	// -->");
		blockLineProcessor.process(" 	// shift one left (embedded)");
		blockLineProcessor.process(" 	// this line too (embedded)");
		blockLineProcessor.process(" 	// <--");
		blockLineProcessor.process(" 	// -->");
		blockLineProcessor.process(" // this line too");
		blockLineProcessor.process(" // <--");

		assertEquals(3,blockProcessor.blocks().size());
		Block block1 = blockProcessor.blocks().get(0);
		Block block2 = blockProcessor.blocks().get(1);
		Block block3 = blockProcessor.blocks().get(2);
		assertNotNull(block1);
		assertNotNull(block2);
		assertNotNull(block3);
		assertEquals("// shift one left",block1.lines().get(0));
		assertEquals("	// shift one left (embedded)",block2.lines().get(0));
		assertEquals("	// this line too (embedded)",block2.lines().get(1));
		assertEquals("// this line too",block3.lines().get(0));
	}
	
	static class CollectingBlockProcessor implements IBlockProcessor {

		List<Block> _blocks = Lists.newArrayList();

		@Override
		public void process(Block block) {
			_blocks.add(block);
		}

		public List<Block> blocks() {
			return _blocks;
		};

	}
}
