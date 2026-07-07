package com.nib.asm.storage;

import com.nib.asm.utils.NAsmEnvironment;

/**
 * NAsmStandardIO main interface (see the
 * {@link NAsmStandardIO NAsmStandardIO class}).
 * 
 * @author nib-labs.io
 */
public interface INAsmStandardIO extends INAsmClonable {

	/**
	 * 
	 * Self-explanatory Runtime framework message types.
	 * 
	 */
	public static enum NAsmMessageType {
		CONSOLE, //
		INFO, //
		WARNING, //
		SEVERE, //
		TRACE, //
	}

	/**
	 * 
	 * This method is invoked from the Runtime framework when a converted
	 * Runtime program is issuing a converted DISPLAY statement.<br>
	 * <br>
	 * i.e.:<br>
	 * <br>
	 * Runtime original statement:<br>
	 * <br>
	 * 
	 * <pre>
	 * DISPLAY 'USERCODE  : ' IN-USERCODE
	 * </pre>
	 * 
	 * <br>
	 * Java equivalent converted statement:<br>
	 * <br>
	 * 
	 * <pre>
	 * _STDOUT.display("USERCODE  : " + fileINPUT1.getFldIN_USERCODE().getString());
	 * </pre>
	 * 
	 * @param message
	 *            user message to be displayed
	 */
	public void display(String message);

	/**
	 * This method is invoked from the Runtime framework to display any
	 * message being issued at runtime included tracing messages emitted when
	 * the tracing feature is activated (see the
	 * {@link NAsmEnvironment#ENV_VAR_RP_DEBUG
	 * ENV_VAR_RP_DEBUG} environment variable).
	 * 
	 * <br>
	 * 
	 * @param type
	 *            message type
	 * @param message
	 *            framework message to be displayed
	 */
	public void displaySTDOUT(NAsmMessageType type, String message);

	/**
	 * This method is invoked from the Runtime framework when an exception
	 * not handled is being generated, the method can be used to print the
	 * exception.
	 * 
	 * @param e
	 *            the exception generated
	 */
	public void displayEXCEPTION(Exception e);

	/**
	 * Force a sysout flush.
	 * 
	 */
	public void flush();
}
