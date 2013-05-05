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
package de.flapdoodle.java2pandoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.block.TypedBlock;
import de.flapdoodle.java2pandoc.io.IBlockWriter;
import de.flapdoodle.java2pandoc.io.IReferenceResolver;
import de.flapdoodle.java2pandoc.parser.IBlockToTypedBlockListConverter;
import de.flapdoodle.java2pandoc.reference.FileReference;
import de.flapdoodle.java2pandoc.reference.JavaReference;


public class JavaSourceToPandocProcessor {
	
	static final Pattern INCLUDE_PATTERN=Pattern.compile("^\\{\\@link\\s+(?<include>.*)\\}$");
	static final Pattern IMPORT_LINE_PATTERN=Pattern.compile("^import (?<import>.*);$");
	static final Pattern PACKAGE_LINE_PATTERN=Pattern.compile("^package (?<package>.*);$");
	
	private final IBlockToTypedBlockListConverter _blockToTypedBlockListConverter;
	private final IReferenceResolver<FileReference> _referenceResolver;
	
	public JavaSourceToPandocProcessor(IBlockToTypedBlockListConverter blockToTypedBlockListConverter,IReferenceResolver<FileReference> referenceResolver) {
		_blockToTypedBlockListConverter = blockToTypedBlockListConverter;
		_referenceResolver = referenceResolver;
	}
	
	public void process(JavaReference startPoint,IBlockWriter writer) {
		if (startPoint.method()!=null) {
			throw new IllegalArgumentException("JavaReference with method is not supported at this point");
		}
		
		Optional<Block> file = _referenceResolver.resolve(startPoint.asFileReference());
		if (file.isPresent()) {
			process(file, writer);
		} else {
			throw new IllegalArgumentException("Could not find "+startPoint);
		}
	}

	private void process(Optional<Block> file, IBlockWriter writer) {
		Set<String> imports = imports(file.get());
		
		List<TypedBlock> typedBlocks = _blockToTypedBlockListConverter.convert(file.get());
		for (TypedBlock t : typedBlocks) {
			processIncludes(t,imports,writer);
		}
	}

	private void processIncludes(TypedBlock typedBlock, Set<String> imports, IBlockWriter writer) {
		switch (typedBlock.type()) {
			case Text:
				processIncludes(typedBlock.block(),imports,writer);
				break;
			case Code:
				writer.write(decorateCode(typedBlock.block()));
				break;
		}
	}

	private void processIncludes(Block block, Set<String> imports, IBlockWriter writer) {
		List<String> output=Lists.newArrayList();
		for (String line : block.lines()) {
			Optional<String> include = parseInclude(line);
			if (include.isPresent()) {
				writer.write(new Block(output));
				output=Lists.newArrayList();
				
//				System.out.println("Include: "+include.get());
				Optional<Block> includeFile = resolve(include.get(),imports,writer);
				if (includeFile.isPresent()) {
					process(includeFile, writer);
				} else {
					throw new IllegalArgumentException("Could not resolve "+include.get()+" with "+imports);
				}
			} else {
				output.add(line);
			}
		}
		if (!output.isEmpty()) {
			writer.write(new Block(output));
		}
	}
	
	private Optional<Block> resolve(String include, Set<String> imports, IBlockWriter writer) {
//		System.out.println("Include '"+include+"' with "+imports);
		
		List<String> searchEntries=Lists.newArrayList();
		if (include.contains(".")) {
			searchEntries.add(include);
		} else {
			Optional<String> fullPath = exactMatch(include, imports);
			if (fullPath.isPresent()) {
				searchEntries.add(fullPath.get());
			} else {
				for (String i : imports) {
					int idx=i.indexOf("*");
					if (idx!=-1) {
						searchEntries.add(i.substring(0,idx)+include);
					}
				}
			}
		}
		return resolve(searchEntries,writer);
	}

	private Optional<Block> resolve(List<String> searchEntries, IBlockWriter writer) {
//		System.out.println("Include "+searchEntries);
		for (String e : searchEntries) {
			Optional<JavaReference> javaRef = JavaReference.parse(e);
			if (javaRef.isPresent()) {
				Optional<Block> file = _referenceResolver.resolve(javaRef.get().asFileReference());
				if (file.isPresent()) {
					return file;
				}
			}
		}
		return Optional.absent();
	}
	
	private Optional<String> exactMatch(String include, Set<String> imports) {
		Optional<String> fullPath=Optional.absent();
		for (String i : imports) {
			if (i.endsWith(include)) {
				fullPath=Optional.of(i);
			}
		}
		return fullPath;
	}
	
	private static Optional<String> parseInclude(String line) {
		Matcher matcher = INCLUDE_PATTERN.matcher(line);
		if (matcher.matches()) {
			return Optional.of(matcher.group("include"));
		}
		return Optional.absent();
	}

	private static Block decorateCode(Block block) {
		List<String> lines=Lists.newArrayList();
		lines.add("");
		lines.add("~~~{.java}");
		lines.addAll(block.lines());
		lines.add("~~~");
		lines.add("");
		return new Block(lines);
	}

	private static Set<String> imports(Block file) {
		Set<String> ret = Sets.newHashSet();
		for (String line : file.lines()) {
			Matcher matcher = IMPORT_LINE_PATTERN.matcher(line);
			if (matcher.matches()) {
				ret.add(matcher.group("import"));
			} else {
				matcher = PACKAGE_LINE_PATTERN.matcher(line);
				if (matcher.matches()) {
					ret.add(matcher.group("package")+".*");
				}
			}
		}
		return ret;
	}
}
