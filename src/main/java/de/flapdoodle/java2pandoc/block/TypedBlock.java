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

import javax.annotation.concurrent.Immutable;

@Immutable
public class TypedBlock {

	private final Type _type;
	private final Block _block;

	public static enum Type {
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

	@Override
	public String toString() {
		return "{"+_type+":"+_block+"}";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_block == null)
				? 0
				: _block.hashCode());
		result = prime * result + ((_type == null)
				? 0
				: _type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypedBlock other = (TypedBlock) obj;
		if (_block == null) {
			if (other._block != null)
				return false;
		} else if (!_block.equals(other._block))
			return false;
		if (_type != other._type)
			return false;
		return true;
	}
	
	
}
