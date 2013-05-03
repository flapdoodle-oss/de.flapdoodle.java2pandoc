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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Optional;

/**
 * a reference which to a file and some anchors
 * a.java.class.in.a.package.JavaClass#method(int)
 * 
 * @author mosmann
 */
@Immutable
public class JavaReference implements IReference<JavaReference> {

	private static final String JAVA_NAME = "[a-zA-Z]+[a-zA-Z0-9\\$]*";
	private static final String JAVA_CLASSNAME = JAVA_NAME + "(\\." + JAVA_NAME + ")*";
	private static final String METHOD_PARAMS = "(" + JAVA_CLASSNAME + "(," + JAVA_CLASSNAME + ")*)*";

	private static final Pattern PATTERN = Pattern.compile("^(?<class>" + JAVA_CLASSNAME + ")(#(?<method>" + JAVA_NAME
			+ ")\\((?<params>" + METHOD_PARAMS + ")\\))?");

	private final String _clazz;
	private final MethodReference _method;

	public JavaReference(String clazz) {
		this(clazz, null);
	}

	public JavaReference(String clazz, MethodReference method) {
		_clazz = clazz;
		_method = method;
	}

	public String classname() {
		return _clazz;
	}

	public MethodReference method() {
		return _method;
	}

	public FileReference asFileReference() {
		return FileReference.fromString(_clazz.replace('.', '/') + ".java", '/');
	}

	@Override
	public String toString() {
		return "(" + _clazz + (_method != null
				? "#" + _method
				: "") + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_clazz == null)
				? 0
				: _clazz.hashCode());
		result = prime * result + ((_method == null)
				? 0
				: _method.hashCode());
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
		JavaReference other = (JavaReference) obj;
		if (_clazz == null) {
			if (other._clazz != null)
				return false;
		} else if (!_clazz.equals(other._clazz))
			return false;
		if (_method == null) {
			if (other._method != null)
				return false;
		} else if (!_method.equals(other._method))
			return false;
		return true;
	}

	/**
	 * Huii {@link Book#other(int)}
	 */
	public static Optional<JavaReference> parse(String refAsString) {
		Matcher matcher = PATTERN.matcher(refAsString);
		if (matcher.matches()) {
			String methodName = matcher.group("method");
			String params = matcher.group("params");
			String classname = matcher.group("class");

			if (methodName != null) {
				return Optional.of(new JavaReference(classname, new MethodReference(methodName, params)));
			}

			if (params != null) {
				throw new IllegalArgumentException("not method name but params? whats wrong??: " + refAsString);
			}

			return Optional.of(new JavaReference(classname, null));
		}

		return Optional.absent();
	}
}
