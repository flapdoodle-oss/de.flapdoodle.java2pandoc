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
