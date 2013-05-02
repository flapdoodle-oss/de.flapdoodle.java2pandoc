package de.flapdoodle.java2pandoc.line.matcher;

import javax.annotation.concurrent.Immutable;

@Immutable
public class LineMatch {

	private final int _indent;

	public LineMatch(int indent) {
		_indent = indent;
	}

	public int indent() {
		return _indent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _indent;
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
		LineMatch other = (LineMatch) obj;
		if (_indent != other._indent)
			return false;
		return true;
	}
	
	
}
