package com.nib.asm.storage;

import com.nib.asm.exceptions.NAsmException;

public class NRECORD_COUNT_CLASS extends NRECORD_PROPERTIES_CLASS implements INAsmClonable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Make available RECORD_COUNT NAsmRuntime property.
   * 
   * @param file
   *            for which the record count is inquired
   */
  public NRECORD_COUNT_CLASS(NAsmFile file) {
    super(file);
  }

  @Override
  public void set(NRECORD_PROPERTIES_CLASS recordLength) throws NAsmException {
    throw new NAsmException("RECORD-COUNT is ready only");
  }

  @Override
  public void set(int value) throws NAsmException {
    throw new NAsmException("RECORD-COUNT is ready only");
  }

  @Override
  public int get() {
    return this.getFile().getStatsRecordCount();
  }

}
