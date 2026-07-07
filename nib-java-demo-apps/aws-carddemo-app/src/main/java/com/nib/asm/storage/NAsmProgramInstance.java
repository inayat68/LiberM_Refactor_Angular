package com.nib.asm.storage;

import com.nib.asm.NAsmOnlineProgram;
import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.utils.NAsmEnvironment;

public class NAsmProgramInstance extends NAsmField {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private NAsmOnlineProgram instance = null;

  public NAsmProgramInstance(String programName) throws NAsmException {
    super(programName, NAsmFieldType.NUMERIC_BINARY, 0, 4);
    instance = NAsmEnvironment.getInstance(programName);
  }

  public NAsmProgramInstance(NAsmOnlineProgram instance) throws NAsmException {
    super(instance.getName(), NAsmFieldType.NUMERIC_BINARY, 0, 4);
    this.instance = instance;
  }
  
  /**
   * @return the instance
   */
  public NAsmOnlineProgram getInstance() {
    return instance;
  }

}
