package com.nib.asm.exceptions;

public class NAsmAbendException extends NAsmException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int abendCode;
	
	public NAsmAbendException(int abendCode) {
		super("ABEND" + abendCode);
		this.abendCode = abendCode;
	}

	/**
	 * @return the abendCode
	 */
	public int getAbendCode() {
		return abendCode;
	}

}
