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

import java.util.List;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;


public class TestBlockToTypedBlockProcessor {

	@Test
	public void stripCommentLines() {
		CollectingTypedBlockProcessor typedBlockProcessor = new CollectingTypedBlockProcessor();
		BlockToTypedBlockProcessor blockProcessor = new BlockToTypedBlockProcessor(typedBlockProcessor);
		
		blockProcessor.process(new Block(Lists.newArrayList(" // Hu"," // Ha"," // He")));
		
		List<TypedBlock> typedBlocks = typedBlockProcessor.typedBlocks();
		assertEquals(1,typedBlocks.size());
		assertEquals(TypedBlock.Type.Text,typedBlocks.get(0).type());
	}

	@Test
	public void codeLines() {
		CollectingTypedBlockProcessor typedBlockProcessor = new CollectingTypedBlockProcessor();
		BlockToTypedBlockProcessor blockProcessor = new BlockToTypedBlockProcessor(typedBlockProcessor);
		
		blockProcessor.process(new Block(Lists.newArrayList(" // a should be 2"," a=2;"," // right")));
		
		List<TypedBlock> typedBlocks = typedBlockProcessor.typedBlocks();
		assertEquals(1,typedBlocks.size());
		assertEquals(TypedBlock.Type.Code,typedBlocks.get(0).type());
	}

	static class CollectingTypedBlockProcessor implements ITypedBlockProcessor {

		List<TypedBlock> typedBlocks=Lists.newArrayList();
		
		@Override
		public void process(TypedBlock typedBlock) {
			typedBlocks.add(typedBlock);
		}
		
		public List<TypedBlock> typedBlocks() {
			return typedBlocks;
		}
	}
}
