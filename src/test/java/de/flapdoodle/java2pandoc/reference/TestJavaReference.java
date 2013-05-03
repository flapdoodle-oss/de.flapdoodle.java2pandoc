package de.flapdoodle.java2pandoc.reference;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.base.Optional;


public class TestJavaReference {

	@Test
	public void validReferenceWithoutMethod() {
		Optional<JavaReference> result = JavaReference.parse("de.flapdoodle.Class");

		assertTrue(result.isPresent());
		
		JavaReference match = new JavaReference("de.flapdoodle.Class");
		
		assertEquals(match,result.get());
	}
	
	@Test
	public void validReferenceWithMethod() {
		Optional<JavaReference> result = JavaReference.parse("de.flapdoodle.Class#fuh(int)");

		assertTrue(result.isPresent());
		
		JavaReference match = new JavaReference("de.flapdoodle.Class",new MethodReference("fuh","int"));
		
		assertEquals(match,result.get());
	}
}
