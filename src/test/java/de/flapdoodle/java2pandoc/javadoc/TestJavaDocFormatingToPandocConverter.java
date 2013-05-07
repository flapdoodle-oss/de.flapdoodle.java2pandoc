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
