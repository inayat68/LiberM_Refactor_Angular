package com.nib.asm.storage;

import java.util.List;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.INAsmIdcamsErrors;
import com.nib.asm.INAsmLogger;

public interface IVsamHandler extends INAsmClonable {

  public enum VSAM_STATUS_CODE {
    OPERATION_COMPLETED_SUCCESSFULLY(00),
    NON_UNIQUE_ALTERNATE_INDEX_DUPLICATE_KEY_FOUND(02),
    INVALID_FIXED_LENGTH_RECORD(04),
    OPTIONA_FILE_IS_NOT_PRESENT(05),
    END_OF_FILE_ENCOUNTERED(10),
    READ__OUTSIDE_FILE_BOUNDARY(14),
    INVALID_KEY_FOR_VSAM(20),
    KEY_SEQUENCE_ERROR(21),
    PRIMARY_DUPLICATE_KEY_FOUND(22),
    RECORD_NOT_FOUND(23),
    KEY_OUTSIDE_BOUNDARY(24),
    PERMANENT_IO_ERROR(30),
    RECORD_OUTSIDE_FILE_BOUNDARY(34),
    FILE_IS_NOT_PRESENT(35),
    OPEN_FILE_WITH_WRONG_MODE(37),
    TRIED_TO_OPEN_A_LOCKED_FILE(38),
    CONFLICTING_FILE_ATTRIBUTES(39),
    FILE_IS_ALREADY_OPEN(41),
    FILE_IS_NOT_OPEN(42),
    REWRITE_TRIED_BEFORE_A_READ(43),
    RECORD_HAS_A_DIFFERENT_LENGTH(44),
    READ_BEYOND_END_OF_FILE(46),
    FILE_NOT_OPENED_FOR_INPUT(47),
    FILE_NOT_OPENED_FOR_OUTPUT(48),
    FILE_NOT_OPENED_FOR_I_O(49),
    PASSWORD_OR_AUTHORIZATION_FAILED(91),
    LOGIC_ERROR(92),
    RESOURCES_ARE_NOT_AVAILABLE(93),
    RECORD_UNAVAILABLE_OR_CONCURRENT_OPEN_ERROR(94),
    FILE_INFORMATION_INVALID_OR_INCOMPLETE(95),
    NO_DD_STATEMENT_FOR_THE_FILE(96),
    OPEN_SUCCESSFUL_AND_FILE_INTEGRITY_VERIFIED(97),
    FILE_IS_LOCKED(98),
    RECORD_LOCKED(99),
    UNHANDLED_ERROR(100);

    private final int value;

    VSAM_STATUS_CODE(final int newValue) {
      value = newValue;
    }

    public int getValue() {
      return value;
    }
  }

  /**
   * Return the operation status code.
   */
  public VSAM_STATUS_CODE getStatusCode();

  /**
   * Open file.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void open(NAsmFile file) throws NAsmException;

  /**
   * Close file.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void close(NAsmFile file) throws NAsmException;

  /**
   * Point to the first record which satisfy the key.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public byte[] start(NAsmFile file, byte[] key) throws NAsmException;

  /**
   * Read a record by key.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public byte[] read(NAsmFile file, byte[] key, boolean exactMatch) throws NAsmException;

  /**
   * Read a record in sequential mode.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public byte[] read(NAsmFile file) throws NAsmException;

  /**
   * Read multiple records using a list of rows.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public List<NAsmField> bulkRead(NAsmFile file, Integer limit, boolean executeRecordHandler) throws NAsmException;
  
  /**
   * Read multiple records using a list of rows - var2fix version
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public List<NAsmField> bulkRead(NAsmFile file, Integer limit, Integer offset, boolean executeRecordHandler, boolean var2fix) throws NAsmException;
  
  /**
   * Write multiple records using a list of rows.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public VSAM_STATUS_CODE bulkWrite(NAsmFile file, List<NAsmField> rows, boolean replace, boolean executeRecordHandler) throws NAsmException;

  /**
   * Write a new record using the byte array data.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void write(NAsmFile file, byte[] data, byte[] encryptedData, int length) throws NAsmException;

  /**
   * Delete the VSAM file.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public int delete(NAsmFile file, byte[] key) throws NAsmException;

  /**
   * Update the VSAM file.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public int update(NAsmFile file, byte[] data, byte[] encryptedData, int length) throws NAsmException;

  /**
   * Delete the VSAM cluster.
   * 
   * @throws Exception
   *             if there is an NAsmRuntime specific issue
   */
  public INAsmIdcamsErrors deleteCluster(NAsmDDcard cluster, INAsmLogger log) throws Exception;

  /**
   * Create the VSAM cluster.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public INAsmIdcamsErrors createCluster(NAsmDDcard cluster, INAsmLogger log) throws Exception;


  /**
   * Alter the VSAM cluster name.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public INAsmIdcamsErrors alterCluster(NAsmDDcard cluster, NAsmDDcard targetCluster, INAsmLogger log) throws Exception;
  
  /**
   * Generate create DDL for the VSAM file.
   */
  public String generateCreateDDL(NAsmDDcard vsamCard);

  /**
   * Generate drop DDL for the VSAM file.
   */
  public String generateDropDDL(NAsmDDcard vsamCard);

  /**
   * Copy from or to a VSAM file.
   */
  public INAsmIdcamsErrors copyFile(INAsmLogger logger, Object contextProvider, Object recordAccessProvider, NAsmDDcard inFile, NAsmDDcard outFile, INAsmLogger log, boolean replace) throws Exception;

}
