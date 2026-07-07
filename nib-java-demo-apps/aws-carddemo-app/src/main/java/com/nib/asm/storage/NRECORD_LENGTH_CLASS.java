package com.nib.asm.storage;

import com.nib.asm.exceptions.NAsmException;

public class NRECORD_LENGTH_CLASS extends NRECORD_PROPERTIES_CLASS implements INAsmClonable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Make available RECORD_LENGTH NAsmRuntime property.
   * 
   * @param file
   *            for which the record length is inquired
   */
  public NRECORD_LENGTH_CLASS(NAsmFile file) {
    super(file);
  }

  @Override
  public void set(NRECORD_PROPERTIES_CLASS recordLength) throws NAsmException {
    this.getFile().setRecordLength(recordLength.get());
  }

  @Override
  public void set(int recordLength) throws NAsmException {
    this.getFile().forceOpen(false);
    this.getFile().setRecordLength(recordLength);
    this.getFile().setLength(recordLength);
  }

  @Override
  public int get() throws NAsmException {
    return this.getFile().getRecordLength();
  }

}
