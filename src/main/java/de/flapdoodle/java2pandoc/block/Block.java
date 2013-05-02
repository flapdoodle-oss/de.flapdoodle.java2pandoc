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

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@Immutable
public class Block {
	
	private ImmutableList<String> lines;

	public Block(Collection<String> lines) {
		this.lines=ImmutableList.copyOf(lines);
	}
	
	@Nonnull
	public ImmutableList<String> lines() {
		return lines;
	}

	public Block shiftLeft(int positions) throws ParseException {
		List<String> ret=Lists.newArrayList();
		for (String line : lines) {
			if (line.length()>positions) {
				String removed=line.substring(0,positions);
				if (removed.trim().isEmpty()) throw new ParseException("Could not shift line "+positions+" to the left: '"+line+"'", 0);
				ret.add(line.substring(positions));
			} else {
				if (!line.trim().isEmpty()) throw new ParseException("Could not shift line "+positions+" to the left: '"+line+"'", 0);
				ret.add("");
			}
		}
		return new Block(ret);
	}
}
