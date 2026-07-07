package com.nib.asm.exceptions;

/**
 * Handle specific NAsmRuntime exceptions.
 * 
 * @author nib-labs.io
 */
public class NAsmFileNotDefinedException extends NAsmException {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the getMessage() method.
	 */
	public NAsmFileNotDefinedException(String message) {
		super(message);
	}

}
