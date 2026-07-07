package com.nib.asm.exceptions;

public class NAsmExitProgramException extends NAsmException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int returnCode;
	
	public NAsmExitProgramException(int returnCode) {
    super("RC=" + returnCode);
		this.returnCode = returnCode;
	}

	/**
	 * @return the returnCode
	 */
	public int getReturnCode() {
		return returnCode;
	}

}
