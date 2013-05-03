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
