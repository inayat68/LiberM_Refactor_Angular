package com.nib.asm.exceptions;

import com.nib.commons.exceptions.ContextException;

/**
 * Handle specific NAsmRuntime exceptions.
 * 
 * @author nib-labs.io
 */
public class NAsmException extends ContextException {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;
	
	private Throwable javaStack = null;

	/**
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the getMessage() method.
	 */
	public NAsmException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            getCause() method). (A null value is permitted, and indicates
	 *            that the cause is nonexistent or unknown.)
	 */
	//public NAsmException(Throwable cause) {
	//	super(cause);
	//}

	/**
	 * @param message
	 *            custom message
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            getCause() method). (A null value is permitted, and indicates
	 *            that the cause is nonexistent or unknown.)
	 */
	//public NAsmException(String message, Throwable cause) {
	//	super(message, cause);
	//}

	/**
	 * @param message
	 *            the detail message (which is saved for later retrieval by the
	 *            getMessage() method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            getCause() method). (A null value is permitted, and indicates
	 *            that the cause is nonexistent or unknown.)
	 * @param enableSuppression
	 *            whether or not suppression is enabled or disabled
	 * @param writableStackTrace
	 *            whether or not the stack trace should be writable
	 */
	//public NAsmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	//	super(message, cause, enableSuppression, writableStackTrace);
	//}

	/**
	 * @return the original javaStack
	 */
	public Throwable getJavaStack() {
		return javaStack;
	}

	/**
	 * @param javaStack
	 *            the original javaStack to set
	 */
	public void setJavaStack(Throwable javaStack) {
		this.javaStack = javaStack;
	}

}
