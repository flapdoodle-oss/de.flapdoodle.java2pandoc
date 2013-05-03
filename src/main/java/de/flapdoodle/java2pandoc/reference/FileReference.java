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

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.concurrent.Immutable;

@Immutable
public class FileReference implements IReference<FileReference> {

	private final Path _path;

	public FileReference(Path path) {
		_path = path;
	}

	public Path path() {
		return _path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_path == null)
				? 0
				: _path.hashCode());
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
		FileReference other = (FileReference) obj;
		if (_path == null) {
			if (other._path != null)
				return false;
		} else if (!_path.equals(other._path))
			return false;
		return true;
	}

	public static FileReference fromString(String pathAsString, char separator) {
		String[] split = pathAsString.split("\\" + separator);
		Path path = Paths.get(split[0]);
		for (int i = 1; i < split.length; i++) {
			path = path.resolve(split[i]);
		}
		return new FileReference(path);
	}
}
