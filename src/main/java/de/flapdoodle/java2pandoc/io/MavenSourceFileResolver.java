package de.flapdoodle.java2pandoc.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.reference.FileReference;

public class MavenSourceFileResolver implements IReferenceResolver<FileReference> {

	private final Path _baseDirectory;
	private final List<Path> _searchPaths = Lists.newArrayList(Paths.get("src", "main", "java"),
			Paths.get("src", "main", "resources"), Paths.get("src", "test", "java"), Paths.get("src", "test", "resources"));

	public MavenSourceFileResolver(Path baseDirectory) {
		_baseDirectory = baseDirectory;
	}

	@Override
	public Optional<Block> resolve(FileReference reference) {
		Optional<Block> ret = Optional.absent();

		for (Path searchPath : _searchPaths) {
			Path javaSourcePaths = _baseDirectory.resolve(searchPath).resolve(reference.path());
			if (Files.exists(javaSourcePaths)) {
				if (ret.isPresent()) {
					throw new IllegalArgumentException("Found " + reference + " in multiple paths(" + _searchPaths + ")");
				}
				ret = read(javaSourcePaths);
			}
		}

		return ret;
	}

	private Optional<Block> read(Path javaSourcePaths) {
		try {
			return Optional.of(new Block(Files.readAllLines(javaSourcePaths, Charsets.UTF_8)));
		} catch (IOException e) {
			return Optional.absent();
		}
	}
}
