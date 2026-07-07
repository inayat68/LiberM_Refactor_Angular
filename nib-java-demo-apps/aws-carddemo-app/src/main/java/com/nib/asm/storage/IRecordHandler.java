package com.nib.asm.storage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.nib.asm.exceptions.NAsmException;

public interface IRecordHandler extends INAsmClonable {
  
  public enum Disposition {
    OLD, //
    SHR, //
    NEW //
  }

  /**
   * Returns TRUE is the passed file is handled externally  
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean isHandled(NAsmFile file);

  /**
   * Override the file open, if nothing is done it should return null,
   * if a not null value is returned the reader will be used to access the file 
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public BufferedInputStream openInput(NAsmFile file) throws NAsmException;

  /**
   * Override the file open, if nothing is done it should return null,
   * if a not null value is returned the reader will be used to access the file 
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public BufferedReader openInputLS(NAsmFile file) throws NAsmException;

  /**
   * Override the file open, if nothing is done it should return null,
   * if a not null value is returned the writer will be used to access the file 
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public BufferedOutputStream openOutput(NAsmFile file, boolean append) throws NAsmException;

  /**
   * Override the file close, if nothing is done it should return the passed
   * BufferedReader instance, if a not null value is returned the file will be closed 
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public BufferedReader close(NAsmFile file, BufferedReader bufferedReader) throws NAsmException;

  /**
   * Override the file close, if nothing is done it should return the passed
   * InputStream instance, if a not null value is returned the file will be closed 
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public InputStream close(NAsmFile file, InputStream inputStream) throws NAsmException;

  /**
   * Override the file close, if nothing is done it should return the passed
   * BufferedOutputStream instance, if a not null value is returned the file will be closed 
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public BufferedOutputStream close(NAsmFile file, BufferedOutputStream bufferedOutputStream) throws NAsmException;

  /**
   * Get a file input stream of the requested file
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public FileInputStream getFileInputStream(NAsmFile file) throws NAsmException;

  /**
   * Delete a file
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean delete(NAsmFile file) throws NAsmException;

  /**
   * Rename a file
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean rename(NAsmFile sourceFile, NAsmFile targetFile) throws NAsmException;
  
  /**
   * Decrypt the buffer data
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public byte[] decrypt(NAsmFile file, byte[] data) throws NAsmException;

  /**
   * Encrypt the buffer data
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public byte[] encrypt(NAsmFile file, byte[] data, int length) throws NAsmException;
  
  /**
   * Bulk decrypt the buffer data
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public List<byte[]> bulkDecrypt(NAsmFile file, List<byte[]> rowsData) throws NAsmException;

  /**
   * Bulk encrypt the buffer data
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public List<byte[]> bulkEncrypt(NAsmFile file, List<byte[]> values) throws NAsmException;
  
  /**
   * Handle file disposition
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean handleDisposition(NAsmFile file, Disposition disposition, boolean createMissingFiles, Object fileExtraInfo) throws NAsmException;
    
}
