package com.nib.asm.storage;

import com.nib.asm.exceptions.NAsmException;

public abstract class NRECORD_PROPERTIES_CLASS implements INAsmClonable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private NAsmFile file;

  protected abstract int get() throws NAsmException;

  protected abstract void set(NRECORD_PROPERTIES_CLASS value) throws NAsmException;

  protected abstract void set(int value) throws NAsmException;

  protected NRECORD_PROPERTIES_CLASS(NAsmFile file) {
    this.file = file;
  }

  /**
   * @return the file
   */
  public NAsmFile getFile() {
    return file;
  }

  /**
   * @return numeric value
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public int getNumeric() throws NAsmException {
    return get();
  }

  /**
   * 
   * @return Integer value
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public Integer getInteger() throws NAsmException {
    return get();
  }

  /**
   * 
   * @return String value
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public String getString() throws NAsmException {
    return "" + get();
  }

  /**
   * 
   * @param value
   *            the value to be compared.
   * @return <b>true</b> if the field value is equal to the value passed
   *         as parameter <br>
   *         <b>false</b> if the field value is not equal to the value
   *         passed as parameter
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean equals(int value) throws NAsmException {
    return this.get() == value;
  }

  /**
   * 
   * @param value
   *            the value to be compared.
   * @return <b>true</b> if the field value is greater to the value passed
   *         as parameter <br>
   *         <b>false</b> if the field value is not greater to the value
   *         passed as parameter
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean greater(int value) throws NAsmException {
    return this.get() > value;
  }

  /**
   * 
   * @param value
   *            the value to be compared.
   * @return <b>true</b> if the field value is greater or equal to the
   *         value passed as parameter <br>
   *         <b>false</b> if the field value is not greater or equal to
   *         the value passed as parameter
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean greaterEquals(int value) throws NAsmException {
    return this.get() >= value;
  }

  /**
   * 
   * @param value
   *            the value to be compared.
   * @return <b>true</b> if the field value is less to the value passed as
   *         parameter <br>
   *         <b>false</b> if the field value is not less to the value
   *         passed as parameter
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean less(int value) throws NAsmException {
    return this.get() < value;
  }

  /**
   * 
   * @param value
   *            the value to be compared.
   * @return <b>true</b> if the field value is less or equal to the value
   *         passed as parameter <br>
   *         <b>false</b> if the field value is not less or equal to the
   *         value passed as parameter
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean lessEquals(int value) throws NAsmException {
    return this.get() <= value;
  }

}
