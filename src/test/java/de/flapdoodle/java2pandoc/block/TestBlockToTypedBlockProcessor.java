package de.flapdoodle.java2pandoc.block;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;


public class TestBlockToTypedBlockProcessor {

	@Test
	public void stripCommentLines() {
		CollectingTypedBlockProcessor typedBlockProcessor = new CollectingTypedBlockProcessor();
		BlockToTypedBlockProcessor blockProcessor = new BlockToTypedBlockProcessor(typedBlockProcessor);
		
		blockProcessor.process(new Block(Lists.newArrayList(" // Hu"," // Ha"," // He")));
		
		List<TypedBlock> typedBlocks = typedBlockProcessor.typedBlocks();
		assertEquals(1,typedBlocks.size());
		assertEquals(TypedBlock.Type.Text,typedBlocks.get(0).type());
	}

	@Test
	public void codeLines() {
		CollectingTypedBlockProcessor typedBlockProcessor = new CollectingTypedBlockProcessor();
		BlockToTypedBlockProcessor blockProcessor = new BlockToTypedBlockProcessor(typedBlockProcessor);
		
		blockProcessor.process(new Block(Lists.newArrayList(" // a should be 2"," a=2;"," // right")));
		
		List<TypedBlock> typedBlocks = typedBlockProcessor.typedBlocks();
		assertEquals(1,typedBlocks.size());
		assertEquals(TypedBlock.Type.Code,typedBlocks.get(0).type());
	}

	@Test
	public void removeLineComments() {
		Optional<Block> unpackedComment = BlockToTypedBlockProcessor.unpackComment(new Block(Lists.newArrayList(" // Hu"," // Ha"," // He")));
		assertTrue(unpackedComment.isPresent());
		assertEquals("[ Hu,  Ha,  He]",unpackedComment.get().lines().toString());
	}
	
	@Test
	public void removeBlockComments() {
		Optional<Block> unpackedComment = BlockToTypedBlockProcessor.unpackComment(new Block(Lists.newArrayList("   * Hu","   * Ha","   * He")));
		assertTrue(unpackedComment.isPresent());
		assertEquals("[ Hu,  Ha,  He]",unpackedComment.get().lines().toString());
	}

	@Test
	public void notAValidBlockComments() {
		Optional<Block> unpackedComment = BlockToTypedBlockProcessor.unpackComment(new Block(Lists.newArrayList("    * Hu","   * Ha","   * He")));
		assertFalse(unpackedComment.isPresent());
	}

	@Test
	public void notAValidLineComments() {
		Optional<Block> unpackedComment = BlockToTypedBlockProcessor.unpackComment(new Block(Lists.newArrayList("    // Hu","   // Ha","   // He")));
		assertFalse(unpackedComment.isPresent());
	}

	static class CollectingTypedBlockProcessor implements ITypedBlockProcessor {

		List<TypedBlock> typedBlocks=Lists.newArrayList();
		
		@Override
		public void process(TypedBlock typedBlock) {
			typedBlocks.add(typedBlock);
		}
		
		public List<TypedBlock> typedBlocks() {
			return typedBlocks;
		}
	}
}
