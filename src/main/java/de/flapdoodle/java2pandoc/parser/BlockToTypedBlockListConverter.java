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
