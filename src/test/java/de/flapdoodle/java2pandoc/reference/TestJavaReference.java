/**
 * Copyright (C) 2013
 *   Michael Mosmann <michael@mosmann.de>
 *
 * with contributions from
 * 	-
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
