package de.flapdoodle.java2pandoc.block;

import javax.annotation.concurrent.Immutable;

@Immutable
public class TypedBlock {

	private final Type _type;
	private final Block _block;

	static enum Type {
		Code,
		Text
	}

	public TypedBlock(Type type, Block block) {
		_type = type;
		_block = block;
	}

	public Type type() {
		return _type;
	}

	public Block block() {
		return _block;
	}
}
