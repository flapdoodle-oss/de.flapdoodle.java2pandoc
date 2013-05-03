package de.flapdoodle.java2pandoc.exceptions;


public class BlockProcessingException extends RuntimeException {

	public BlockProcessingException(String message, Throwable cause) {
		super(message, cause);
	}

	public BlockProcessingException(String message) {
		super(message);
	}

	public BlockProcessingException(Throwable cause) {
		super(cause);
	}

}
