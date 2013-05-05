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
package de.flapdoodle.java2pandoc.parser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.block.TypedBlock;


public class BlockToTypedBlockConverter implements IBlockToTypedBlockConverter {

	static Pattern LINE_COMMENT_PATTERN = Pattern.compile("(^\\s*//)");
	static Pattern BLOCK_COMMENT_PATTERN = Pattern.compile("(^\\s*\\*\\s)");
	
	@Override
	public TypedBlock convert(Block block) {
		return guessTypedBlock(block);
	}

	@Nonnull
	@VisibleForTesting
	protected static TypedBlock guessTypedBlock(Block block) {
		Optional<Block> unpackedComment=unpackComment(block);
		if (unpackedComment.isPresent()) {
			return new TypedBlock(TypedBlock.Type.Text,unpackedComment.get());
		}
		return new TypedBlock(TypedBlock.Type.Code,block);
	}

	@Nonnull
	@VisibleForTesting
	protected static Optional<Block> unpackComment(Block block) {
		Optional<Block> blockWithLineCommentStripped=removePattern(block, LINE_COMMENT_PATTERN);
		if (blockWithLineCommentStripped.isPresent()) {
			return blockWithLineCommentStripped;
		}
		return removePattern(block, BLOCK_COMMENT_PATTERN);
	}

	@Nonnull
	@VisibleForTesting
	protected static Optional<Block> removePattern(Block block, Pattern pattern) {
		List<String> processedLines=Lists.newArrayList();
		List<String> removedParts=Lists.newArrayList();
		
		for (String line : block.lines()) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				removedParts.add(line.substring(0,matcher.end()));
				processedLines.add(line.substring(matcher.end()));
			} else {
				return Optional.absent();
			}
		}
		if (processedLines.size()==block.lines().size() && (sameParts(removedParts))) {
			return Optional.of(new Block(processedLines));
		}
		return Optional.absent();
	}

	private static boolean sameParts(List<String> removedParts) {
		return Sets.newHashSet(removedParts).size()==1;
	}
}
