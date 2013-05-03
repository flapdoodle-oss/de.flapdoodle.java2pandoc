package de.flapdoodle.java2pandoc.block;

import de.flapdoodle.java2pandoc.line.ILineProcessor;

public class BlockToLineProcessor implements IBlockProcessor {

	private final ILineProcessor _lineProcessor;

	public BlockToLineProcessor(ILineProcessor lineProcessor) {
		_lineProcessor = lineProcessor;
	}

	@Override
	public void process(Block block) {
		for (String line : block.lines()) {
			_lineProcessor.process(line);
		}
	}
}
