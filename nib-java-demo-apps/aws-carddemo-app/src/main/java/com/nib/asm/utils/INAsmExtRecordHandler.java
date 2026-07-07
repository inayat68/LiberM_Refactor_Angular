package com.nib.asm.utils;

import java.io.BufferedOutputStream;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.storage.NAsmFile;

public interface INAsmExtRecordHandler {

	  /**
	   * Returns TRUE if the passed file is handled externally  
	   * 
	   */
	  public boolean isHandled(NAsmFile file);

	  /**
	   * Returns the latest file status  
	   * 
	   * @throws NAsmException
	   *             if there is an EasyRuntime specific issue
	   */
	  public int getFileStatus(NAsmFile file) throws NAsmException;

    /**
     * Returns true if the file is ended  
     * 
     */
    public boolean isStatusEndOfFile(NAsmFile nibFile);

    /**
     * Returns true if the latest access couldn't find a record for the passed key  
     * 
     */
    public boolean isStatusKeyNotFound(NAsmFile nibFile);
	  
	  /**
	   * Returns TRUE if the data is returned in EBCDIC format  
	   * 
	   */
	  public boolean isEbcdic();

	  /**
	   * Override the file open for input
	   * 
	   * @throws NAsmException
	   *             if there is an EasyRuntime specific issue
	   */
	  public boolean openInput(NAsmFile file) throws NAsmException;

	  /**
	   * Override the file open for output 
	   * 
	   * @throws NAsmException
	   *             if there is an EasyRuntime specific issue
	   */
	  public boolean openOutput(NAsmFile file, BufferedOutputStream existingStream) throws NAsmException;

	  /**
	   * Override the file close 
	   * 
	   */
	  public boolean close(NAsmFile file);

	  /**
	   * Read a byte array from the requested file
	   * 
	   * @throws NAsmException
	   *             if there is an EasyRuntime specific issue
	   */
	  public byte[] read(NAsmFile file) throws NAsmException;

	  /**
	   * Read a byte array from the requested file using a key (VSAM only)
	   * 
	   * @throws NAsmException
	   *             if there is an EasyRuntime specific issue
	   */
	  public byte[] read(NAsmFile file, byte[] key) throws NAsmException;

	  /**
	   * Write a byte array to the requested file
	   * 
	   * @throws NAsmException
	   *             if there is an EasyRuntime specific issue
	   */
	  public void write(NAsmFile file, byte[] buffer, int length) throws NAsmException;

	  /**
	   * Write a byte array to the requested file using a key (VSAM only)
	   * 
	   * @throws NAsmException
	   *             if there is an EasyRuntime specific issue
	   */
	  public void write(NAsmFile file, byte[] buffer, byte[] key) throws NAsmException;

	  /**
	   * Update using a byte array the requested file using a key (VSAM only)
	   * 
	   * @throws NAsmException
	   *             if there is an EasyRuntime specific issue
	   */
	  public void rewrite(NAsmFile file, byte[] buffer, byte[] key) throws NAsmException;
	  
	}