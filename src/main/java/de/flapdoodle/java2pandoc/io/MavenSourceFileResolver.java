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
