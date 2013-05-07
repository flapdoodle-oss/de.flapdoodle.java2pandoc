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
package de.flapdoodle.java2pandoc.javadoc;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.block.TypedBlock;
import de.flapdoodle.java2pandoc.block.TypedBlock.Type;


public class TestJavaDocFormatingToPandocConverter {

	@Test
	public void nothingToChange() {
		JavaDocFormatingToPandocConverter converter = new JavaDocFormatingToPandocConverter(2);
		TypedBlock source = new TypedBlock(Type.Code, new Block("Fun"));
		TypedBlock match = new TypedBlock(Type.Code, new Block("Fun"));
		List<TypedBlock> ret = converter.convert(source);
		assertEquals(1,ret.size());
		assertEquals(match,ret.get(0));
	}

	@Test
	public void codeTagToJavaCode() {
		JavaDocFormatingToPandocConverter converter = new JavaDocFormatingToPandocConverter(2);
		TypedBlock source = new TypedBlock(Type.Text, new Block("Start","<code>","Without tab","</code>","End"));
		
		TypedBlock text= new TypedBlock(Type.Text, new Block("Start"));
		TypedBlock code = new TypedBlock(Type.Code, new Block("Without tab"));
		TypedBlock text2 = new TypedBlock(Type.Text, new Block("End"));
		
		List<TypedBlock> ret = converter.convert(source);
		assertEquals(3,ret.size());
		assertEquals(text,ret.get(0));
		assertEquals(code,ret.get(1));
		assertEquals(text2,ret.get(2));
	}

	@Test
	public void preTagToIndent() {
		JavaDocFormatingToPandocConverter converter = new JavaDocFormatingToPandocConverter(2);
		TypedBlock source = new TypedBlock(Type.Text, new Block("Start","<pre>","Without tab","</pre>","End"));
		
		TypedBlock text= new TypedBlock(Type.Text, new Block("Start","","        Without tab","","End"));
		
		List<TypedBlock> ret = converter.convert(source);
		assertEquals(1,ret.size());
		assertEquals(text,ret.get(0));
	}
}
