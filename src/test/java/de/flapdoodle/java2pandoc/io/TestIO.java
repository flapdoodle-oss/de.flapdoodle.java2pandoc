package de.flapdoodle.java2pandoc.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.google.common.io.Files;

import de.flapdoodle.java2pandoc.sample.Book;


public class TestIO {

	@Test
	public void currentDir() throws IOException {
		System.out.println(getClass().getResource(Book.class.getName()+".java"));
		
		Path p=Paths.get("fuh");
		System.out.println("Path:"+p.toFile().getAbsolutePath());
	}
}
