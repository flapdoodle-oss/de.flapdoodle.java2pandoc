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

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.reference.FileReference;
import de.flapdoodle.java2pandoc.reference.JavaReference;


public class JavaSourceReferenceResolver implements IReferenceResolver<JavaReference> {
	
	private final ImmutableList<IReferenceResolver<FileReference>> _fileResolvers;

	public JavaSourceReferenceResolver(List<IReferenceResolver<FileReference>> fileResolvers) {
		_fileResolvers = ImmutableList.copyOf(fileResolvers);
	}
	
	@Override
	public Optional<Block> resolve(JavaReference reference) {
		FileReference javaSoureFileReference = reference.asFileReference();
		
		for (IReferenceResolver<FileReference> resolver : _fileResolvers) {
			Optional<Block> file = resolver.resolve(javaSoureFileReference);
			if (file.isPresent()) {
				return resolve(reference, file.get());
			}
		}
		
		return Optional.absent();
	}

	private Optional<Block> resolve(JavaReference reference, Block block) {
		if (reference.method()!=null) {
			throw new IllegalArgumentException("method ref not supported");
		}
		return Optional.of(block);
	}

}
