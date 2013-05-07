package de.flapdoodle.java2pandoc.javadoc;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import de.flapdoodle.java2pandoc.block.Block;
import de.flapdoodle.java2pandoc.block.TypedBlock;
import de.flapdoodle.java2pandoc.block.TypedBlock.Type;
import de.flapdoodle.java2pandoc.exceptions.BlockProcessingException;
import de.flapdoodle.java2pandoc.parser.ITypedBlockToTypedBlockConverter;

public class JavaDocFormatingToPandocConverter implements ITypedBlockToTypedBlockConverter {

	static final Pattern CODE_START = Pattern.compile("^<code>\\s*$");
	static final Pattern CODE_END = Pattern.compile("^</code>\\s*$");

	static final Pattern PRE_START = Pattern.compile("^<pre>\\s*$");
	static final Pattern PRE_END = Pattern.compile("^</pre>\\s*$");
	
	static final Pattern SPACES_ON_THE_LEFT=Pattern.compile("^(?<spaces>\\s*)");
	
	private final int _tabHasSpaces;
	private final String _tabAsSpaces;
	
	public JavaDocFormatingToPandocConverter(int tabHasSpaces) {
		_tabHasSpaces = tabHasSpaces;
		_tabAsSpaces = Strings.repeat(" ", _tabHasSpaces);
	}
	
	@Override
	public List<TypedBlock> convert(TypedBlock block) {
		if (block.type() == Type.Text) {
			return codeToMarkdown(preToMarkdown(_tabAsSpaces, block));
		}
		return Lists.newArrayList(block);
	}

	protected static List<String> shiftLeftMinTwoTabs(String tabAsSpaces, List<String> lines) {
		List<String> ret=lines;
		
		int minIndent=Integer.MAX_VALUE;
		
		for (String line : lines) {
			Matcher matcher = SPACES_ON_THE_LEFT.matcher(line);
			if (matcher.find()) {
				String spaces=matcher.group("spaces");
				spaces=spaces.replace("\t", tabAsSpaces);
				minIndent=Math.min(minIndent, spaces.length());
			}
		}
		
		int addInFront=8-minIndent;
		if (addInFront>0) {
			String pre=Strings.repeat(" ", addInFront);
			ret=Lists.newArrayList();
			for (String line : lines) {
				ret.add(pre+line.replace("\t", tabAsSpaces));
			}			
		}
		
		return ret;
	}
	
	protected static TypedBlock preToMarkdown(String tabAsSpaces, TypedBlock block) {
		List<String> lines=Lists.newArrayList();
		List<String> indentedLines=Lists.newArrayList();
		
		boolean indent=false;
		for (String line : block.block().lines()) {
			boolean skip = false;
			if (!indent && PRE_START.matcher(line).matches()) {
				indent=true;
				skip=true;
			} else {
				if (indent && PRE_END.matcher(line).matches()) {
					indent=false;
					skip=true;
					
					lines.addAll(shiftLeftMinTwoTabs(tabAsSpaces, indentedLines));
					indentedLines=Lists.newArrayList();
				}
			}
			if (!skip) {
				if (indent) {
					indentedLines.add(line);
				} else {
					lines.add(line);
				}
			} else {
				lines.add("");
			}
		}
		
		if (indent) throw new BlockProcessingException("no closing </pre> found");
		
		return new TypedBlock(Type.Text, new Block(lines));
	}

	@VisibleForTesting
	protected static List<TypedBlock> codeToMarkdown(TypedBlock block) {
		List<TypedBlock> ret = Lists.newArrayList();

		boolean isText = true;
		List<String> text = Lists.newArrayList();
		List<String> code = Lists.newArrayList();

		for (String line : block.block().lines()) {
			boolean skip = false;

			if (isText && CODE_START.matcher(line).matches()) {
				ret.add(new TypedBlock(Type.Text, new Block(text)));
				text = Lists.newArrayList();
				isText = false;
				skip = true;
			} else {
				if (!isText && CODE_END.matcher(line).matches()) {
					ret.add(new TypedBlock(Type.Code, new Block(code)));
					code = Lists.newArrayList();
					isText = true;
					skip = true;
				}
			}

			if (!skip) {
				if (isText) {
					text.add(line);
				} else {
					code.add(line);
				}
			}
		}
		
		if (!isText) throw new BlockProcessingException("no closing </code> found");
		if (!text.isEmpty()) ret.add(new TypedBlock(Type.Text, new Block(text)));
		
		return ret;
	}
}
