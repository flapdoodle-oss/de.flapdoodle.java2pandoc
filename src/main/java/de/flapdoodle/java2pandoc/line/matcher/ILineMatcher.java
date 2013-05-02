package de.flapdoodle.java2pandoc.line.matcher;

import com.google.common.base.Optional;


public interface ILineMatcher {
	Optional<LineMatch> match(String line);
}
