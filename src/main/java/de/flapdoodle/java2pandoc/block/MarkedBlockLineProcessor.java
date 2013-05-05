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
package de.flapdoodle.java2pandoc.block;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import de.flapdoodle.java2pandoc.exceptions.BlockProcessingException;
import de.flapdoodle.java2pandoc.line.ILineProcessor;
import de.flapdoodle.java2pandoc.line.matcher.ILineMatcher;
import de.flapdoodle.java2pandoc.line.matcher.LineMatch;

@Deprecated
public class MarkedBlockLineProcessor implements ILineProcessor {

	private final IBlockProcessor _blockProcessor;
	private final ILineMatcher _startMark;
	private final ILineMatcher _endMark;

	boolean _collectLines = false;
	List<String> _collectedLines = Lists.newArrayList();
	Deque<BlockIndent> _indents = new ArrayDeque<BlockIndent>();
	int _indent = 0;

	public MarkedBlockLineProcessor(IBlockProcessor blockProcessor, ILineMatcher startMark, ILineMatcher endMark) {
		_blockProcessor = blockProcessor;
		_startMark = startMark;
		_endMark = endMark;
	}

	@Override
	public void process(String line) {
		Optional<LineMatch> startMatch = _startMark.match(line);
		if (startMatch.isPresent()) {
			if (_collectLines) {
				throw new BlockProcessingException("block allready started: " + line);
			}
			_collectLines = true;
			_indent = startMatch.get().indent();
		} else {
			Optional<LineMatch> endMatch = _endMark.match(line);
			if (endMatch.isPresent()) {

				if (!_collectLines) {
					throw new BlockProcessingException("block allready closed: " + line);
				}
				_collectLines = false;

				int indentBlock = baseIndentation(_indents, _indent);

				BlockIndent currentBlockIndent = new BlockIndent(_indent, endMatch.get().indent());
				if (currentBlockIndent.indentationLeft()) {
					_indents.push(currentBlockIndent);
				} else {
					if (currentBlockIndent.indentationReduced()) {
						BlockIndent removed = _indents.pop();
						if (!removed.inverse(currentBlockIndent)) {
							throw new BlockProcessingException("Indentation does not match " + removed + " ? " + currentBlockIndent);
						}
					}
				}

				if (_indents.isEmpty()) {
					Block block = new Block(_collectedLines).shiftLeft(indentBlock);
					_collectedLines = Lists.newArrayList();

					_blockProcessor.process(block);
				}
			} else {
				if (_collectLines)
					_collectedLines.add(line);
			}
		}
	}

	private static int baseIndentation(Deque<BlockIndent> indents, int indent) {
		BlockIndent topMost = indents.peekLast();
		if (topMost != null) {
			return topMost.start();
		}
		return indent;
	}

	static class BlockIndent {

		final int _start;
		final int _end;

		public BlockIndent(int start, int end) {
			_start = start;
			_end = end;
		}

		public boolean inverse(BlockIndent other) {
			return _end == other._start && _start == other._end;
		}

		public boolean indentationReduced() {
			return _end < _start;
		}

		public boolean indentationLeft() {
			return _end > _start;
		}

		public int start() {
			return _start;
		}

		public int end() {
			return _end;
		}
	}
}
