package de.flapdoodle.java2pandoc.parser;

import java.util.List;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.block.TypedBlock;


public interface IBlockToTypedBlockListConverter {
	List<TypedBlock> convert(Block block);
}
