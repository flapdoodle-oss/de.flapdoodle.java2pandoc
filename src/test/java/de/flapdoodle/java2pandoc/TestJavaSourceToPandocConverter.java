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

import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Optional;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.block.TypedBlock;
import de.flapdoodle.java2pandoc.io.MavenSourceFileResolver;
import de.flapdoodle.java2pandoc.line.matcher.JavaSourceLineMatcher;
import de.flapdoodle.java2pandoc.parser.BlockToBlockListConverter;
import de.flapdoodle.java2pandoc.parser.BlockToTypedBlockConverter;
import de.flapdoodle.java2pandoc.parser.BlockToTypedBlockListConverter;
import de.flapdoodle.java2pandoc.parser.IBlockToBlockListConverter;
import de.flapdoodle.java2pandoc.parser.IBlockToTypedBlockConverter;
import de.flapdoodle.java2pandoc.parser.IBlockToTypedBlockListConverter;
import de.flapdoodle.java2pandoc.reference.JavaReference;
import de.flapdoodle.java2pandoc.sample.Book;

public class TestJavaSourceToPandocConverter {

	@Test
	public void testSample() {
		MavenSourceFileResolver mavenSourceResolver = new MavenSourceFileResolver(Paths.get(""));
		Optional<Block> file = mavenSourceResolver.resolve(new JavaReference(Book.class.getName()).asFileReference());
		assertTrue(file.isPresent());

		IBlockToBlockListConverter blockToBlockList=new BlockToBlockListConverter(JavaSourceLineMatcher.startMatcher(),
				JavaSourceLineMatcher.endMatcher());
		IBlockToTypedBlockConverter blockToTypedBlock=new BlockToTypedBlockConverter();
		IBlockToTypedBlockListConverter toTypedBlocks=new BlockToTypedBlockListConverter(blockToBlockList, blockToTypedBlock);
		
		List<TypedBlock> typedBlocks = toTypedBlocks.convert(file.get());
		for (TypedBlock b : typedBlocks) {
			print(b);
		}
	}

	private static void print(TypedBlock typedBlock) {
		switch (typedBlock.type()) {
			case Code:
				printCode(typedBlock.block());
				break;
			case Text:
				print(typedBlock.block());
				break;
		}
	}

	private static void printCode(Block block) {
		System.out.println("");
		System.out.println("~~~{.java}");
		print(block);
		System.out.println("~~~");
		System.out.println("");
	}

	public static void print(Block block) {
		for (String line : block.lines()) {
			System.out.println(line);
		}
	}

}
