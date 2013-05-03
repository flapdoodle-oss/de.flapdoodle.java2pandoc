package de.flapdoodle.java2pandoc.reference;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;

@Immutable
public class MethodReference {

	private final String _name;
	private final String _params;

	public MethodReference(String name, String params) {
		Preconditions.checkNotNull(name, "name is null");
		_name = name;
		_params = params;
	}

	public String name() {
		return _name;
	}

	public String params() {
		return _params;
	}
	
	@Override
	public String toString() {
		return _name+(_params!=null ? "("+_params+")" : "");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_name == null)
				? 0
				: _name.hashCode());
		result = prime * result + ((_params == null)
				? 0
				: _params.hashCode());
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
		MethodReference other = (MethodReference) obj;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		if (_params == null) {
			if (other._params != null)
				return false;
		} else if (!_params.equals(other._params))
			return false;
		return true;
	}

}
