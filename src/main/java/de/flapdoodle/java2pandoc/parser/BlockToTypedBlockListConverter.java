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

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.block.TypedBlock;


public class BlockToTypedBlockListConverter implements IBlockToTypedBlockListConverter {

	
	private final IBlockToBlockListConverter _blockToBlockList;
	private final IBlockToTypedBlockConverter _blockToTypedBlock;

	public BlockToTypedBlockListConverter(IBlockToBlockListConverter blockToBlockList,IBlockToTypedBlockConverter blockToTypedBlock) {
		_blockToBlockList = blockToBlockList;
		_blockToTypedBlock = blockToTypedBlock;
	}
	
	@Override
	public List<TypedBlock> convert(Block block) {
		List<Block> blockList = _blockToBlockList.convert(block);
		return Lists.transform(blockList, new Function<Block, TypedBlock>() {
			@Override
			public TypedBlock apply(Block input) {
				return _blockToTypedBlock.convert(input);
			}
		});
	}

}
