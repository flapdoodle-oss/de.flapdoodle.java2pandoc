package de.flapdoodle.java2pandoc.sample;

/*
 * -->
 * % Headline
 * 
 * Why should i care?
 * <--
 */
public class Book {

	// -->
	/**
	 * 
	 * @param d
	 *          a not so big value
	 */
	public void methodContent(int d) {
		// -->
		int a = 2;
		int b = a * 3;
		// <--
	}

	// <--

	/*
	 * I can write comments, where ever i like
	 * without including it
	 */

	/*
	 * -->
	 * ... or with including it:
	 * <--
	 */

	// -->
	public int other(int b) {
		int c = 2;
		String s = "huiu";

		// <--

		/*
		 * Indentation could lead to trouble
		 */

		// -->
		return -1;
	}
	// <--
}
