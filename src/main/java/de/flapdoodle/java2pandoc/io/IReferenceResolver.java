package de.flapdoodle.java2pandoc.io;

import com.google.common.base.Optional;

import de.flapdoodle.java2pandoc.block.Block;


public interface IReferenceResolver<T> {
	Optional<Block> resolve(T reference);
}
