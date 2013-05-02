package de.flapdoodle.java2pandoc.line.matcher;

import static org.junit.Assert.*;

import org.junit.Test;

import de.flapdoodle.java2pandoc.line.matcher.RegexLineMatcher;


public class TestRegexLineMatcher {

	@Test
	public void patternShouldMatch() {
		RegexLineMatcher matcher = new RegexLineMatcher("(^\\s*)//\\s*-->\\s*$");
		assertTrue(matcher.match("  // -->").isPresent());
		assertTrue(matcher.match("  //-->").isPresent());
		assertTrue(matcher.match("// -->").isPresent());
		assertTrue(matcher.match("//-->").isPresent());
	}
	@Test
	public void patternShouldNotMatch() {
		RegexLineMatcher matcher = new RegexLineMatcher("(^\\s*)//\\s*-->\\s*$");
		assertFalse(matcher.match("a  // --> ").isPresent());
		assertFalse(matcher.match("  * --> a").isPresent());
	}
}
