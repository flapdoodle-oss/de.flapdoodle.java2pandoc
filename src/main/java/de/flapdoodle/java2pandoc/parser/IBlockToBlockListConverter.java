package de.flapdoodle.java2pandoc.parser;

import java.util.List;

import de.flapdoodle.java2pandoc.block.Block;

public interface IBlockToBlockListConverter {

	List<Block> convert(Block block);
}
