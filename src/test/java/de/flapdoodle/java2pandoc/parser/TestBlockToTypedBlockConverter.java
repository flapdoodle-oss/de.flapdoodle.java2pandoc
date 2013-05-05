package de.flapdoodle.java2pandoc.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.block.TypedBlock;

public class TestBlockToTypedBlockConverter {

	@Test
	public void stripCommentLines() {
		BlockToTypedBlockConverter blockConverter = new BlockToTypedBlockConverter();
		TypedBlock typedBlock = blockConverter.convert(new Block(Lists.newArrayList(" // Hu", " // Ha", " // He")));

		assertNotNull(typedBlock);
		assertEquals(TypedBlock.Type.Text, typedBlock.type());
	}

	@Test
	public void codeLines() {
		BlockToTypedBlockConverter blockConverter = new BlockToTypedBlockConverter();

		TypedBlock typedBlock = blockConverter.convert(new Block(Lists.newArrayList(" // a should be 2", " a=2;",
				" // right")));

		assertNotNull(typedBlock);
		assertEquals(TypedBlock.Type.Code, typedBlock.type());
	}

	@Test
	public void removeLineComments() {
		Optional<Block> unpackedComment = BlockToTypedBlockConverter.unpackComment(new Block(Lists.newArrayList(" // Hu",
				" // Ha", " // He")));
		assertTrue(unpackedComment.isPresent());
		assertEquals("[ Hu,  Ha,  He]", unpackedComment.get().lines().toString());
	}

	@Test
	public void removeBlockComments() {
		Optional<Block> unpackedComment = BlockToTypedBlockConverter.unpackComment(new Block(Lists.newArrayList("   * Hu",
				"   * Ha", "   * He")));
		assertTrue(unpackedComment.isPresent());
		assertEquals("[ Hu,  Ha,  He]", unpackedComment.get().lines().toString());
	}

	@Test
	public void notAValidBlockComments() {
		Optional<Block> unpackedComment = BlockToTypedBlockConverter.unpackComment(new Block(Lists.newArrayList("    * Hu",
				"   * Ha", "   * He")));
		assertFalse(unpackedComment.isPresent());
	}

	@Test
	public void notAValidLineComments() {
		Optional<Block> unpackedComment = BlockToTypedBlockConverter.unpackComment(new Block(Lists.newArrayList(
				"    // Hu", "   // Ha", "   // He")));
		assertFalse(unpackedComment.isPresent());
	}

}
