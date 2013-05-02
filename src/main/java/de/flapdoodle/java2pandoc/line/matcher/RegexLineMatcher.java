package de.flapdoodle.java2pandoc.line.matcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Optional;


public class RegexLineMatcher implements ILineMatcher {
	private Pattern _pattern;

	public RegexLineMatcher(String pattern) {
		_pattern = Pattern.compile(pattern);
	}
	
	@Override
	public Optional<LineMatch> match(String line) {
		Matcher matcher = _pattern.matcher(line);
		if (matcher.matches()) {
			return Optional.of(new LineMatch(matcher.end(1)));
		}
		return Optional.absent();
	}

}
