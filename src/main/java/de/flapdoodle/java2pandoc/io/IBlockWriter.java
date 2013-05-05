package de.flapdoodle.java2pandoc.io;

import de.flapdoodle.java2pandoc.block.Block;


public interface IBlockWriter {
	void write(Block block);
}
