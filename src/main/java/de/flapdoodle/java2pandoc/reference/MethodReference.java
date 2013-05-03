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
package de.flapdoodle.java2pandoc.reference;

import java.util.Arrays;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

@Immutable
public class MethodReference {

	private final String _name;
	private final ImmutableList<String> _params;

	public MethodReference(String name, String params) {
		Preconditions.checkNotNull(name, "name is null");
		_name = name;
		_params = params != null
				? ImmutableList.copyOf(Arrays.asList(params.split("[\\s,]+")))
				: ImmutableList.<String>of();
	}

	public String name() {
		return _name;
	}

	public ImmutableList<String> params() {
		return _params;
	}

	@Override
	public String toString() {
		return _name + (_params != null
				? "(" + _params + ")"
				: "");
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
