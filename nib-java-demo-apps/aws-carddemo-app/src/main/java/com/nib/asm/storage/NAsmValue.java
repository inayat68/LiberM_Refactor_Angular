package com.nib.asm.storage;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.utils.NAsmEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NAsmValue {
  private static final Logger logger = LoggerFactory.getLogger(NAsmValue.class);
  
  private byte[] value;
  private int absoluteOffset;
  private NAsmAddress baseAddress;
  private int baseAddressOffset;

  /**
   * 
   * @param value
   * @param offset
   */
  public NAsmValue(byte[] value, int absoluteOffset, NAsmAddress baseAddress, int baseAddressOffset) {
    this.value = value;
    this.absoluteOffset = absoluteOffset;
    this.baseAddress = baseAddress;
    this.baseAddressOffset = baseAddressOffset;
  }

  /**
   * 
   * @param field
   * @return
   * @throws NAsmException
   */
  public static NAsmValue resolveValue(NAsmField field) throws NAsmException {
    return resolveValue(field, null);
  }

  public static NAsmValue resolveValue(NAsmField field, NAsmRegister register) throws NAsmException {
    byte[] value = null;
    int absoluteOffset = 0;
    int deltaOffset = 0;
    NAsmUsingInfo usingInfo = NAsmUsingInfo.extractUsingInfo(field);
    NAsmAddress pa = null;
    if (usingInfo != null // 
        && (register == null || // 
            (register != null && //
                !register.getName().equals(usingInfo.getUsingRegister().getName())))) {
      //The delta offset is calculated always on the original fields,
      //we assume sibling USING on different structures respect the 
      //structure similarity
      deltaOffset = usingInfo.getDeltaOffset();
      pa = usingInfo.getUsingRegister().getPointedAddress();
      //The structure is setup with a USING on register
      //but the register doesn't contain a valid address
      //point to a all 0x00 area
      if (pa == null && usingInfo.getUsingRegister() != null) {
        if (NAsmEnvironment.TRACE() || NAsmEnvironment.DEBUG()) {
          logger.warn("DSECT " + usingInfo.getUsingField().getName() + " not addressed -> R" + usingInfo.getUsingRegister().getName() + ", used field " + field.getName());
        }
        value = new byte[32000];
      }else {
        //Check is also the referenced field has a USING setup
        if (pa != null) {
          NAsmUsingInfo nestedUsingInfo = NAsmUsingInfo.extractUsingInfo(pa.getAddressedField());
          if (nestedUsingInfo != null) {
            pa = nestedUsingInfo.getUsingRegister().getPointedAddress();
            deltaOffset += nestedUsingInfo.getDeltaOffset();
          }
        }
        if (pa != null) {
          absoluteOffset = pa.getOffset() + deltaOffset //
              + pa.getAddressedField().getAbsoluteOffset();
          if (pa.getAddressedField() instanceof NAsmProgramInstance) {
            NAsmProgramInstance program = (NAsmProgramInstance) pa.getAddressedField();
            //value = program.getInstance().getStorage().getValueInstance();
          }else {
            value = pa.getAddressedField().getValueInstance();
          }
        }
      }
    }

    if (value == null) {
      NAsmField parent = field.getParent();
      while (parent != null //
          && value == null) {
        pa = null;
        if (parent.getUsingRegister() != null
            && (register == null || // 
                (register != null && //
                    !register.getName().equals(parent.getUsingRegister().getName())))) {
          pa = parent.getUsingRegister().getPointedAddress();
          //Avoid cyclic reference to the same register
          if (pa != null // 
              && (pa.getAddressedField() == parent.getUsingRegister() //
                  || (pa.getAddressedField() instanceof NAsmRegister //
                      && parent.getUsingRegister() instanceof NAsmRegister //
                      && parent.getUsingRegister().getName().equals(pa.getAddressedField().getName())))) {
            pa = null;
          }
        }else if (parent.isStoragePointer()) {
          //For possible future implementations
        }
        if (pa != null) {
          absoluteOffset = pa.getOffset();
          //Calculate the offset respect to the parent
          deltaOffset = field.getAbsoluteOffset() - parent.getAbsoluteOffset();
          absoluteOffset += deltaOffset;
          value = pa.getAddressedField().getValueInstance();
        }
        parent = parent.getParent();
      }
    }
    if (value != null) {
      return new NAsmValue(value, absoluteOffset, pa, deltaOffset);
    }
    return null;
  }

  /**
   * @return the value
   */
  public byte[] getValue() {
    return value;
  }

  /**
   * @return the absoluteOffset
   */
  public int getAbsoluteOffset() {
    return absoluteOffset;
  }

  /**
   * @return the baseAddress
   */
  public NAsmAddress getBaseAddress() {
    return baseAddress;
  }

  /**
   * @return the baseAddressOffset
   */
  public int getBaseAddressOffset() {
    return baseAddressOffset;
  }

}
