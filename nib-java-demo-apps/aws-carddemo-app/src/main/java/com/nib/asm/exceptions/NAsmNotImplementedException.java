package com.nib.asm.exceptions;

/**
 * Handle specific NAsmRuntime exceptions.
 * 
 * @author nib-labs.io
 */
public class NAsmNotImplementedException extends NAsmException {

  /**
   * Serial version
   */
  private static final long serialVersionUID = 1L;

	public NAsmNotImplementedException() {
		super("Not implemented");
	}
}
