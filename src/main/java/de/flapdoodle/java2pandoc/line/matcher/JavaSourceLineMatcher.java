package de.flapdoodle.java2pandoc.line.matcher;


public final class JavaSourceLineMatcher {
	public static ILineMatcher startMatcher() {
		return new RegexLineMatcher("(^\\s*)(//|\\*)\\s*-->\\s*$");
	}
	public static ILineMatcher endMatcher() {
		return new RegexLineMatcher("(^\\s*)(//|\\*)\\s*<--\\s*$");
	}
}
