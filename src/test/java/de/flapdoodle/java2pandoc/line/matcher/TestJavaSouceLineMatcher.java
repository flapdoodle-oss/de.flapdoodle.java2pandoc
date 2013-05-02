package de.flapdoodle.java2pandoc.line.matcher;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.base.Optional;

import de.flapdoodle.java2pandoc.line.matcher.ILineMatcher;
import de.flapdoodle.java2pandoc.line.matcher.JavaSourceLineMatcher;

public class TestJavaSouceLineMatcher {

	@Test
	public void startShouldMatch() {
		ILineMatcher matcher = JavaSourceLineMatcher.startMatcher();
		assertEquals(Optional.of(new LineMatch(2)),matcher.match("  // -->"));
		assertEquals(Optional.of(new LineMatch(2)),matcher.match("  //-->"));
		assertEquals(Optional.of(new LineMatch(0)),matcher.match("// -->"));
		assertEquals(Optional.of(new LineMatch(0)),matcher.match("//-->"));
		assertEquals(Optional.of(new LineMatch(2)),matcher.match("  * -->"));
		assertEquals(Optional.of(new LineMatch(2)),matcher.match("  *-->"));
		assertEquals(Optional.of(new LineMatch(0)),matcher.match("* -->"));
		assertEquals(Optional.of(new LineMatch(0)),matcher.match("*-->"));
	}

	@Test
	public void endShouldMatch() {
		ILineMatcher matcher = JavaSourceLineMatcher.endMatcher();
		assertEquals(Optional.of(new LineMatch(2)),matcher.match("  // <--"));
		assertEquals(Optional.of(new LineMatch(2)),matcher.match("  //<--"));
		assertEquals(Optional.of(new LineMatch(0)),matcher.match("// <--"));
		assertEquals(Optional.of(new LineMatch(0)),matcher.match("//<--"));
		assertEquals(Optional.of(new LineMatch(2)),matcher.match("  * <--"));
		assertEquals(Optional.of(new LineMatch(2)),matcher.match("  *<--"));
		assertEquals(Optional.of(new LineMatch(0)),matcher.match("* <--"));
		assertEquals(Optional.of(new LineMatch(0)),matcher.match("*<--"));
	}
}
