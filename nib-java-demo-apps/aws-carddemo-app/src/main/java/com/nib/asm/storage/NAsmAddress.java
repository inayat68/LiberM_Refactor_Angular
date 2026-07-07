package com.nib.asm.storage;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.exceptions.NAsmNotImplementedException;
import com.nib.asm.utils.NAsmEnvironment;
import com.nib.asm.utils.NAsmStrings;

public class NAsmAddress implements INAsmClonable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private NAsmField addressedField;
  private int offset;
  private NAsmField sourceField = null;
  private int sourceOffset = 0;
  private int memoryId = 0;
  private String addrKey = null;
  private byte[] addrKeyBytes = null;

  public NAsmAddress(NAsmField field, int offset) throws NAsmException {
    this.addressedField = field;
    this.offset = offset;
    if (!field.hasValidId()) {
      field.setId(NAsmEnvironment.getNextFieldId());
      NAsmEnvironment.addressedFieldList.add(field.getId(), new NAsmAddressesCollection());
    }
  }

  public boolean isNull() {
    return this.addressedField == null;
  }

  public boolean isZero() {
    return offset == 0;
  }

  public void increment(int value) throws NAsmException {
    change(this.offset + value);
  }

  public void decrement(int value) throws NAsmException {
    change(this.offset - value);
  }

  protected void change(int offset) throws NAsmException {
    this.offset = offset;
  }

  public boolean greaterEquals(NAsmAddress address) throws NAsmException {
    throw new NAsmNotImplementedException();
  }

  public boolean greater(NAsmAddress address) throws NAsmException {
    throw new NAsmNotImplementedException();
  }

  public boolean less(NAsmAddress address) throws NAsmException {
    throw new NAsmNotImplementedException();
  }

  public boolean lessEquals(NAsmAddress address) throws NAsmException {
    throw new NAsmNotImplementedException();
  }

  public boolean equals(NAsmAddress address) {
    if (addressedField == null) {
      return address == null;
    } else {
      //return addressedField.equals(address.getAddressedField()) && offset == address.getOffset();
      return addressedField.getName().equals(address.getAddressedField().getName()) //
          && addressedField.getType().equals(address.getAddressedField().getType()) // 
          && addressedField.getLength() == address.getAddressedField().getLength() // 
          && offset == address.getOffset();
    }
  }

  public NAsmAddress getClone() throws NAsmException {
    NAsmAddress rc = new NAsmAddress(this.addressedField, this.offset);
    return rc;
  }

  public NAsmAddress getClone(int offset) throws NAsmException {
    NAsmAddress rc = new NAsmAddress(this.addressedField, offset);
    return rc;
  }

  public byte[] getValue() throws NAsmException {
    return getValue(null);
  }

  /**
   * @return the value
   * @throws NAsmException
   */
  public byte[] getValue(Integer length) throws NAsmException {
    int len = addressedField.getValue().length;
    if (length == null) {
      len = addressedField.getValue().length;
    } else {
      len = length;
    }
    byte[] value;
    if (this.offset == 0) {
      value = addressedField.getValue(0, len);
    } else {
      if (length == null) {
        value = new byte[len - offset];
        System.arraycopy(addressedField.getValue(0, len), offset, value, 0, value.length);
      } else {
        value = new byte[len];
        System.arraycopy(addressedField.getValue(offset, len), 0, value, 0, value.length);
      }
    }
    return value;
  }

  /**
   * @param value the value to set
   * @throws NAsmException
   */
  public void setValue(byte[] value) throws NAsmException {
    addressedField.setValue(value, 0);
  }

  /**
   * @return the offset
   */
  public int getOffset() {
    return offset;
  }

  /**
   * @param offset the offset to set
   */
  public void setOffset(int offset) {
    this.offset = offset;
  }

  /**
   * @return the addressedField
   */
  public NAsmField getAddressedField() {
    return addressedField;
  }

  /**
   * @return the sourceField
   */
  public NAsmField getSourceField() {
    return sourceField;
  }

  /**
   * @param sourceField the sourceField to set
   */
  public void setSourceField(NAsmField sourceField) {
    this.sourceField = sourceField;
  }

  /**
   * @return the sourceOffset
   */
  public int getSourceOffset() {
    return sourceOffset;
  }

  /**
   * @param sourceOffset the sourceOffset to set
   */
  public void setSourceOffset(int sourceOffset) {
    this.sourceOffset = sourceOffset;
  }

  /**
   * @return the memoryId
   */
  public int getMemoryId() {
    return memoryId;
  }

  /**
   * @param memoryId the memoryId to set
   */
  public void setMemoryId(int memoryId) {
    this.memoryId = memoryId;
    this.addrKey = null;
    this.addrKeyBytes = null;
    this.sourceField = null;
  }

  /**
   * 
   * @return
   */
  public boolean hasValidMemoryId() {
    return this.memoryId > 0;
  }

  /**
   * 
   */
  public void freeMemoryId() {
    this.memoryId = 0;
    this.addrKey = null;
    this.addrKeyBytes = null;
    this.sourceField = null;
  }
  
  /**
   * 
   * @return
   * @throws NAsmException
   */
  public String getAddrKey() throws NAsmException {
    if (!hasValidMemoryId()) {
      return null;
    }
    if (this.addrKey == null) {
      byte[] seq = this.getAddrKeyBytes();
      this.addrKey = new String(new byte[] { seq[0], seq[1], seq[2], seq[3] }, NAsmEnvironment.ENV_CHARSET());
    }
    return this.addrKey;
  }

  /**
   * 
   * @return
   * @throws NAsmException
   */
  public byte[] getAddrKeyBytes() throws NAsmException {
    if (!hasValidMemoryId()) {
      return null;
    }
    if (this.addrKeyBytes == null) {
      this.addrKeyBytes = NAsmStrings.valueToBinaryBigEndian(this.memoryId, 4);
      int seqVal = this.addrKeyBytes[0] | NAsmEnvironment.ADDRESS_MASK;
      this.addrKeyBytes[0] = (byte) seqVal;
    }
    return this.addrKeyBytes;
  }
  
}
