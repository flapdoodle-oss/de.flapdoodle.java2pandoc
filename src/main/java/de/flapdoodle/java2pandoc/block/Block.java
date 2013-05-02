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
