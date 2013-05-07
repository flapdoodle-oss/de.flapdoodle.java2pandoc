package de.flapdoodle.java2pandoc.parser;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.block.TypedBlock;

public class TypedBlockListToTypedBlockListConverter {
	
	private final ITypedBlockToTypedBlockConverter[] _converter;

	public TypedBlockListToTypedBlockListConverter(ITypedBlockToTypedBlockConverter ... converter) {
		_converter = converter;
	}

	public List<TypedBlock> convert(List<TypedBlock> blockList) {
		List<TypedBlock> ret=blockList;
		
		for (ITypedBlockToTypedBlockConverter c : _converter) {
			List<TypedBlock> collectAll=Lists.newArrayList();
			for (TypedBlock t : ret) {
				collectAll.addAll(c.convert(t));
			}
			ret=ImmutableList.copyOf(collectAll);
		}
		
		return ret;
	}
	
	public static IBlockToTypedBlockListConverter postProcess(IBlockToTypedBlockListConverter converter,ITypedBlockToTypedBlockConverter ... postProcessors) {
		return new PostProcessor(converter,postProcessors);
	}
	
	static class PostProcessor implements IBlockToTypedBlockListConverter {

		private final IBlockToTypedBlockListConverter _converter;
		private final TypedBlockListToTypedBlockListConverter _postProcessor;

		public PostProcessor(IBlockToTypedBlockListConverter converter, ITypedBlockToTypedBlockConverter[] postProcessors) {
			_converter = converter;
			_postProcessor = new TypedBlockListToTypedBlockListConverter(postProcessors);
		}

		@Override
		public List<TypedBlock> convert(Block block) {
			return _postProcessor.convert(_converter.convert(block));
		}
		
	}
}
