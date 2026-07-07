package com.nib.asm.storage;

import com.nib.asm.exceptions.NAsmException;

/**
 * NAsmFile main interface (see the {@link NAsmFile
 * NAsmFile class}).
 * 
 * @author nib-labs.io
 */
public interface INAsmFile extends INAsmClonable {

	/**
	 * Read a record.
	 * 
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public NAsmField read() throws NAsmException;

	/**
	 * Write a new record using the string value.
	 * 
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public void write(String buffer) throws NAsmException;

  /**
   * Write a new record using the field value.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void write(NAsmField field) throws NAsmException;

	/**
	 * Check if there is a next record to be read.
	 * 
	 * @return <b>true</b> if there is a next record to be read
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public boolean next() throws NAsmException;

	/**
	 * Close the file.
	 * 
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public void close() throws NAsmException;

  /**
   * Position on a record by key, VSAM only.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public NAsmField start(byte[] key) throws NAsmException;

  /**
   * Read a record by key, VSAM only.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public NAsmField read(byte[] key) throws NAsmException;

  /**
   * Read a record by equal key, VSAM only.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public NAsmField read(byte[] key, boolean exactMatch) throws NAsmException;

  /**
   * Delete a record by key, VSAM only.
   * 
   * @throws NAsmException
   *             if there is an Runtime specific issue
   */
  public int delete(byte[] key) throws NAsmException;


  /**
   * Update a record by key, VSAM only.
   * 
   * @throws NAsmException
   *             if there is an Runtime specific issue
   */
  public int update(byte[] data) throws NAsmException;

}
