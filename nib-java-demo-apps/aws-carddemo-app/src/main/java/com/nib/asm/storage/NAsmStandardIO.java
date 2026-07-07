package com.nib.asm.storage;

import com.nib.asm.NAsmOnlineProgram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default class for displaying all the messages emitted by the following
 * entities:<br>
 * <br>
 * <ul>
 * <li>Runtime converted program using a DISPLAY statement
 * <li>Any message emitted by the Runtime framework
 * <li>All the exceptions not handled by the framework
 * </ul>
 * <br>
 * By default all the messages will be redirected to the STDIO, users can
 * override the standard behavior using their own class to redirect all the
 * messages at their convenience using the Runtime program constructor having
 * as parameter
 *
 * @author nib-labs.io
 */
public class NAsmStandardIO implements INAsmStandardIO {

  private static final Logger logger = LoggerFactory.getLogger(NAsmStandardIO.class);

	@Override
	public void display(String message) {
	  logger.info(message);
	}

	@Override
  public void displaySTDOUT(NAsmMessageType type, String message) {
    if (type.equals(NAsmMessageType.SEVERE)) {
      logger.error(message);
    } else if (type.equals(NAsmMessageType.WARNING)) {
      logger.warn(message);
    } else {
      logger.info(message);
    }
  }

	@Override
	public void displayEXCEPTION(Exception e) {
		e.printStackTrace();
	}

	@Override
	public void flush() {
	  
	}

}
