package com.nib.asm.storage;

import java.util.HashMap;
import java.util.Iterator;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.utils.NAsmEnvironment;

public class NAsmRegister extends NAsmRegisterOptimized {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public NAsmRegister(String name) throws NAsmException {
    super(name);
  }

  public NAsmRegister(String name, int length) throws NAsmException {
    super(name, length);
  }
  
  /**
   * Use this one for converting LR commands:
   * 
   * i.e.
   * 
   * LR    R1,R6
   * 
   */
  public void setRegister(NAsmRegister register) throws NAsmException {
    setRegister(register, 0);
  }

  public void setRegister(NAsmRegister register, int offset) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "setRegister");
    byte[] value = register.getValue(0, 4);
    if (NAsmEnvironment.isValidAddress(value)) {
      NAsmAddress pointedAddress = NAsmEnvironment.getAddress(value);
      NAsmValue realValue = NAsmValue.resolveValue(pointedAddress.getAddressedField(), this);
      if (realValue != null && realValue.getBaseAddress() != null) {
        pointedAddress = realValue.getBaseAddress().getClone(realValue.getBaseAddressOffset());
        value = NAsmEnvironment.registerAddress(pointedAddress);
      } else {
        pointedAddress = pointedAddress.getClone();
        value = NAsmEnvironment.registerAddress(pointedAddress);
      }
    }
    //housekeeping - not required: already performed by the setByteArray
    //this.dismissAddress(true);
    this.setByteArray(value);
    NAsmEnvironment.statsEndMethod(this.getClass(), "setRegister");
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#clone()
   */
  @Override
  public NAsmRegister clone() {
    NAsmRegister r = null;
    try {
      r = new NAsmRegister(this.getName());
      NAsmAddress NAsmAddress = this.getPointedAddress();
      if (NAsmAddress != null) {
        NAsmAddress NAsmAddressCloned = NAsmAddress.getClone(NAsmAddress.getOffset());
        r.setPointedAddress(NAsmAddressCloned);
      } else {
        r.setValue(this.getValue(), 0);
      }
      if (this.unsingRegisteredFields != null) {
        r.unsingRegisteredFields = new HashMap<>();
        Iterator<String> it = this.unsingRegisteredFields.keySet().iterator();
        while (it.hasNext()) {
          String programName = it.next();
          NAsmField f = this.unsingRegisteredFields.get(programName);
          r.unsingRegisteredFields.put(programName, f);
        }
      }
    } catch (Exception e) {
      NAsmEnvironment.console(e);
    }
    return r;
  }
  
}
