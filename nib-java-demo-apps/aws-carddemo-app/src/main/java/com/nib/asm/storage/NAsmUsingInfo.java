package com.nib.asm.storage;

import com.nib.asm.utils.NAsmEnvironment;

public class NAsmUsingInfo {
  private int deltaOffset = 0;
  private NAsmRegister usingRegister;
  private NAsmField usingField;

  /**
   * 
   * @param field
   * @return
   */
  public static NAsmUsingInfo extractUsingInfo(NAsmField field) {
    NAsmRegister usingRegister = null;
    NAsmField usingField = null;
    int deltaOffset = 0;
    if (field.getUsingRegister() == null) {
      NAsmField parent = field.getParent();
      while (parent != null && parent.getUsingRegister() == null) {
        parent = parent.getParent();
      }
      if (parent != null && parent.getUsingRegister() != null) {
        usingRegister = parent.getUsingRegister();
        usingField = parent;
        deltaOffset = field.getAbsoluteOffset() - parent.getAbsoluteOffset();
      }
    } else {
      usingRegister = field.getUsingRegister();
    }
    if (usingRegister == null) {
      return null;
    } else {
      return new NAsmUsingInfo(usingRegister, usingField, deltaOffset);
    }
  }

  /**
   * 
   * @param usingRegister
   * @param deltaOffset
   */
  public NAsmUsingInfo(NAsmRegister usingRegister, NAsmField usingField, int deltaOffset) {
    this.usingRegister = usingRegister;
    this.usingField = usingField;
    this.deltaOffset = deltaOffset;
  }

  /**
   * @return the deltaOffset
   */
  public int getDeltaOffset() {
    return deltaOffset;
  }

  /**
   * @return the usingRegister
   */
  public NAsmRegister getUsingRegister() {
  	//SQ - register number is enough
  	//return usingRegister;
  	
  	if (usingRegister == null) {
  		return null;
  	}
  	
  	int val = 0;
  	try {
  		val = Integer.parseInt(usingRegister.getName());
  	}catch(Exception e) {
  		return usingRegister; //ex: PARMS case
  	}
  	return NAsmEnvironment.registers[val];
  	
  }

  /**
   * @return the usingField
   */
  public NAsmField getUsingField() {
    return usingField;
  }
}
