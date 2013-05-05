package de.flapdoodle.java2pandoc.parser;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.block.TypedBlock;


public interface IBlockToTypedBlockConverter {
	TypedBlock convert(Block block);
}
