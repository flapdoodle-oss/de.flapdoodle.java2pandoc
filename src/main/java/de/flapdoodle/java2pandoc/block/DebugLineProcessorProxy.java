package de.flapdoodle.java2pandoc.block;

import de.flapdoodle.java2pandoc.line.ILineProcessor;

public class DebugLineProcessorProxy implements ILineProcessor {

	private final ILineProcessor _parent;

	public DebugLineProcessorProxy(ILineProcessor parent) {
		_parent = parent;
	}

	@Override
	public void process(String line) {
		System.out.println(">" + line);
		_parent.process(line);
	}

}
