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
	static Pattern BLOCK_COMMENT_PATTERN = Pattern.compile("(^\\s*\\*)");
	
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
