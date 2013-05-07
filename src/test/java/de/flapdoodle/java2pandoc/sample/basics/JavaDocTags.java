package de.flapdoodle.java2pandoc.sample.basics;

/**
 * -->
 * # JavaDoc Tags and Hints
 * 
 * To make it easy to write markdown style text in your JavaDoc we will you provide with some tipps.
 * 
 * ## Supported Tags
 * 
 * Following JavaDoc Tags are supported:
 * 
 * * pre
 * * code
 * 
 * If you want some generic markdown code block, you can wrap your text with \<pre\>-Tags. To make this a code block like this
 * 
 * <pre>
 * a code block
 * </pre>
 * 
 * you have to write something like this in your JavaDoc comment:
 * 
 * <pre>
 * 	<pre>
 * 		a code block
 * 	</pre>
 * </pre>
 * 
 * ## Escaping
 * 
 * If you want to use Tags in your text you have to escape the tag this way:
 * 
 * <pre>
 * ... this is the \<tag\> i want to used ...
 * </pre>
 * 
 * ## Lists
 * 
 * List are potential target for code format issues. You have to leave a empty line around a list for valid markdown
 * markup. You can write markdown lists, but if you reformat the code they will be gone. List Tags are not supported now.
 * 
 * * Summer
 * * Winter
 * 
 * <--
 * 
 * @author mosmann
 * 
 */
public class JavaDocTags {

}
