package de.flapdoodle.java2pandoc.parser;

import java.util.List;

import de.flapdoodle.java2pandoc.block.TypedBlock;


public interface ITypedBlockToTypedBlockConverter {
	List<TypedBlock> convert(TypedBlock block);
}
