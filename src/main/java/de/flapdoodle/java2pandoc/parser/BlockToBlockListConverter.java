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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.exceptions.BlockProcessingException;
import de.flapdoodle.java2pandoc.line.matcher.ILineMatcher;
import de.flapdoodle.java2pandoc.line.matcher.LineMatch;

public class BlockToBlockListConverter implements IBlockToBlockListConverter {

	private final ILineMatcher _startMark;
	private final ILineMatcher _endMark;

	public BlockToBlockListConverter(ILineMatcher startMark, ILineMatcher endMark) {
		_startMark = startMark;
		_endMark = endMark;
	}

	@Override
	public List<Block> convert(Block bblock) {
		List<Block> ret = Lists.newArrayList();

		boolean collectLines = false;
		List<String> collectedLines = Lists.newArrayList();
		Deque<BlockIndent> indents = new ArrayDeque<BlockIndent>();
		int indent = 0;
		
		for (String line : bblock.lines()) {
			Optional<LineMatch> startMatch = _startMark.match(line);
			if (startMatch.isPresent()) {
				if (collectLines) {
					throw new BlockProcessingException("block allready started: " + line);
				}
				collectLines = true;
				indent = startMatch.get().indent();
			} else {
				Optional<LineMatch> endMatch = _endMark.match(line);
				if (endMatch.isPresent()) {

					if (!collectLines) {
						throw new BlockProcessingException("block allready closed: " + line);
					}
					collectLines = false;

					int indentBlock = baseIndentation(indents, indent);

					BlockIndent currentBlockIndent = new BlockIndent(indent, endMatch.get().indent());
					if (currentBlockIndent.indentationLeft()) {
						indents.push(currentBlockIndent);
					} else {
						if (currentBlockIndent.indentationReduced()) {
							BlockIndent removed = indents.pop();
							if (!removed.inverse(currentBlockIndent)) {
								throw new BlockProcessingException("Indentation does not match " + removed + " ? " + currentBlockIndent);
							}
						}
					}

					if (indents.isEmpty()) {
						Block block = new Block(collectedLines).shiftLeft(indentBlock);
						collectedLines = Lists.newArrayList();

						ret.add(block);
					}
				} else {
					if (collectLines)
						collectedLines.add(line);
				}
			}
		}
		
		return ImmutableList.copyOf(ret);
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
