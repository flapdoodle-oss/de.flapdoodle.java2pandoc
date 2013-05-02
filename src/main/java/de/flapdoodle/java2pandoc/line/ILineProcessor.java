package de.flapdoodle.java2pandoc.line;

import java.text.ParseException;


public interface ILineProcessor {
	void process(String line) throws ParseException;
}
