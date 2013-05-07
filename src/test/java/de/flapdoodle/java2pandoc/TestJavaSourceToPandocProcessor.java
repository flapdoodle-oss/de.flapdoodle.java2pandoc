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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.io.Files;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.block.TypedBlock;
import de.flapdoodle.java2pandoc.io.IBlockWriter;
import de.flapdoodle.java2pandoc.io.MavenSourceFileResolver;
import de.flapdoodle.java2pandoc.javadoc.JavaDocFormatingToPandocConverter;
import de.flapdoodle.java2pandoc.line.matcher.JavaSourceLineMatcher;
import de.flapdoodle.java2pandoc.parser.BlockToBlockListConverter;
import de.flapdoodle.java2pandoc.parser.BlockToTypedBlockConverter;
import de.flapdoodle.java2pandoc.parser.BlockToTypedBlockListConverter;
import de.flapdoodle.java2pandoc.parser.IBlockToBlockListConverter;
import de.flapdoodle.java2pandoc.parser.IBlockToTypedBlockConverter;
import de.flapdoodle.java2pandoc.parser.IBlockToTypedBlockListConverter;
import de.flapdoodle.java2pandoc.parser.TypedBlockListToTypedBlockListConverter;
import de.flapdoodle.java2pandoc.reference.JavaReference;
import de.flapdoodle.java2pandoc.sample.Book;
import de.flapdoodle.java2pandoc.sample.Start;

public class TestJavaSourceToPandocProcessor {

	@Test
	public void testSample() {
		MavenSourceFileResolver mavenSourceResolver = new MavenSourceFileResolver(Paths.get(""));
		Optional<Block> file = mavenSourceResolver.resolve(new JavaReference(Book.class.getName()).asFileReference());
		assertTrue(file.isPresent());

		IBlockToBlockListConverter blockToBlockList = new BlockToBlockListConverter(JavaSourceLineMatcher.startMatcher(),
				JavaSourceLineMatcher.endMatcher());
		IBlockToTypedBlockConverter blockToTypedBlock = new BlockToTypedBlockConverter();
		IBlockToTypedBlockListConverter toTypedBlocks = new BlockToTypedBlockListConverter(blockToBlockList,
				blockToTypedBlock);
		
		IBlockToTypedBlockListConverter rootConverter = TypedBlockListToTypedBlockListConverter.postProcess(toTypedBlocks, new JavaDocFormatingToPandocConverter(2));

		JavaSourceToPandocProcessor javaSourceToPandocProcessor = new JavaSourceToPandocProcessor(rootConverter,
				mavenSourceResolver);

		IBlockWriter writer = new SplitWriter(new SystemOutBlockWriter(),new FileOutBlockWriter("test.md"));
		javaSourceToPandocProcessor.process(new JavaReference(Start.class.getName()), writer);
	}

	static class SplitWriter implements IBlockWriter {

		private final IBlockWriter[] _destinations;

		public SplitWriter(IBlockWriter ... destinations) {
			_destinations = destinations;
		}
		
		@Override
		public void write(Block block) {
			for (IBlockWriter d : _destinations) {
				d.write(block);
			}
		}

		@Override
		public void close() {
			for (IBlockWriter d : _destinations) {
				d.close();
			}
		}
		
	}
	static class SystemOutBlockWriter implements IBlockWriter {

		@Override
		public void write(Block block) {
			for (String line : block.lines()) {
				System.out.println(line);
			}
		}

		@Override
		public void close() {

		}

	}

	static class FileOutBlockWriter implements IBlockWriter {

		private final String _filename;
		StringBuilder sb=new StringBuilder();

		public FileOutBlockWriter(String filename) {
			_filename = filename;
		}

		@Override
		public void write(Block block) {
			for (String line : block.lines()) {
				sb.append(line);
				sb.append("\n");
			}
		}

		@Override
		public void close() {
			try {
				Files.write(sb, new File(_filename), Charsets.UTF_8);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}
}
