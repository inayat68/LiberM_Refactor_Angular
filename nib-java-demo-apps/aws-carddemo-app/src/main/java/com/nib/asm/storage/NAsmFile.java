package com.nib.asm.storage;

import com.nib.commons.AbstractProgram;
import com.nib.asm.INAsmProgramWrapper;
import com.nib.asm.NAsmOnlineProgram;
import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.exceptions.NAsmFileNotDefinedException;
import com.nib.asm.storage.IVsamHandler.VSAM_STATUS_CODE;
import com.nib.asm.utils.NAsmEnvironment;
import com.nib.asm.utils.NAsmStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 * Abstraction of a basic NAsmRuntime file definition.<br>
 * 
 * @author nib-labs.io
 */
public class NAsmFile extends NAsmField implements INAsmFile {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static final Logger logger = LoggerFactory.getLogger(NAsmFile.class);

  private File physicalFile = null;

  /**
   * Self-explanatory values for NAsmRuntime file types.
   */
  public static enum NAsmFileType {
    FLAT,
    VIRTUAL,
    DLI,
    IDMS,
    VSAM,
    SQL,
    HOSTDISK;
  }

  /**
   * Self-explanatory values for NAsmRuntime comms types.
   */
  public static enum NAsmDeviceType {
    CARD,
    PUNCH,
    PRINTER,
    DISK,
    TAPE;
  }

  /**
   * Values for NAsmRuntime record formats.
   */
  public static enum NAsmRecordFormat {
    /**
     * Undefined format, an exception is raised if the file is used at
     * runtime.
     */
    UNKNOWN,
    /**
     * Fixed record length, records are placed one after the other with
     * exactly the same number of bytes without any delimiter (new line or
     * something else).
     */
    F,
    /**
     * Same as F.
     */
    FB,
    /**
     * Same as F, ASA version.
     */
    FBA,
    /**
     * NAsmVariable record length, LINUX format. It requires the 2 bytes length
     * prefix for each record.
     */
    U,
    /**
     * NAsmVariable record length, this is exactly like the RDW file format on
     * the mainframe. It requires the 4 bytes length prefix for each record.
     */
    VB,
    /**
     * Same as VB.
     */
    V,
    /**
     * Same as VB.
     */
    VBS,
    /**
     * Line Sequential, records are delimited by DOS new line characters
     * 0x0D0A. Not all files can be used with this format. If the records
     * contain binary fields (COMP, COMP-3 etc) data in these fields may
     * contain 0x0D or 0x0A values which will cause the record to be
     * truncated when being read.
     */
    LS_DOS,
    /**
     * Line Sequential, records are delimited by UNIX new line characters
     * 0x0A. Not all files can be used with this format. If the records
     * contain binary fields (COMP, COMP-3 etc) data in these fields may
     * contain 0x0A values which will cause the record to be truncated when
     * being read.
     */
    LS_UNIX,
    /**
     * Fixed record length, records are placed one after the other with
     * exactly the same number of bytes. Last two bytes of the record must
     * be \r\n otherwise an exception is raised. Last two bytes of the
     * record are not part of the data buffer sent to the program and are
     * not part of the record length.
     */
    LSFB_DOS,
    /**
     * Fixed record length, records are placed one after the other with
     * exactly the same number of bytes. Last byte of the record must be \n
     * otherwise an exception is raised. Last byte of the record is not part
     * of the data buffer sent to the program and are not part of the record
     * length.
     */
    LSFB_UNIX,
    /**
     * VSAM variable length.
     */
    VSAM,
    /**
     * VSAM fixed length.
     */
    VSAM_FB;
  }

  //Bulk insert support
  private List<NAsmField> rows = new ArrayList<>();

  private NAsmFileType fileType;

  private NAsmDeviceType deviceType;

  private NAsmRecordFormat recordFormat = NAsmRecordFormat.UNKNOWN;

  private boolean inStream = false;
  private Vector<String> inStreamData = new Vector<>();

  private int recordLength = 0;
  private int maxRecordLength = 0;
  private int keyStart = 0;
  private int keyLength = 0;
  private String table = "";
  private boolean ascii = false;
  private String fullPath = "";
  private int statsReadCount = 0;
  private int statsWriteCount = 0;
  private int statsRecordCount = 0;
  private boolean input = false;
  private int dynamicEofMethod = 0;

  // Runtime properties
  private byte[] currentRecord = null;
  private byte[] prevRecord = null;

  //ASM DCB values
  private static int DBC_OF_ERR = 0x00;
  private static int DBC_OF_OPN = 0x10;

  //SQ TODO review - 148 Vs 176
  private static int RDJFCB_LEN = 176;

  /**
   * Public instance of NRECORD_LENGTH_CLASS.
   */
  public NRECORD_LENGTH_CLASS RECORD_LENGTH;

  /**
   * Public instance of NRECORD_COUNT_CLASS.
   */
  public NRECORD_COUNT_CLASS RECORD_COUNT;

  private int blockSize = 0;

  private boolean fullTrk = false;

  private String dsnName = "";

  private boolean truncate = true;

  // I-O variables
  private transient Object fs = null;
  private transient InputStream ifs = null;
  private transient BufferedReader sr = null;
  private boolean _extRecordHandler = false;
  private boolean dummy = false;
  private boolean sysout = false;
  private boolean temp = false;
  private boolean inCatalog = false;
  private String dcb = null;
  private AbstractProgram openProgram = null;
  private Integer bulkInsertLimit = null;
  private boolean _isOpened = false;
  private boolean _eof;

  // VSAM key
  private NAsmField key = null;

  /**
   * 
   * @param name
   *            file name
   * @param fileType
   *            file type a value of NAsmFile.NAsmFileType
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public NAsmFile(String name, NAsmFileType fileType) throws NAsmException {
    super(name, NAsmFieldType.ALPHANUMERIC, 0, NAsmEnvironment.FILE_MAX_RECLEN());
    this.fileType = fileType;
    this.RECORD_LENGTH = new NRECORD_LENGTH_CLASS(this);
    this.RECORD_COUNT = new NRECORD_COUNT_CLASS(this);
  }

  @Override
  public NAsmField read() throws NAsmException {
    if (this.isDummy()) {
      return this;
    }
    if (this.next()) {
      return this;
    } else {
      return null;
    }
  }

  private void writeFile() throws NAsmException {
    writeFile(false);
  }

  private void writeFile(boolean skipRecordHandler) throws NAsmException {
    this.input = false;
    if (!this._isOpened) {
      boolean isRegistered = this.registerFile();
      BufferedOutputStream existingStream = null;
      if (isRegistered) {
        NAsmFile registeredFile = NAsmEnvironment.getFile(this.getName());
        if (registeredFile.isOpened()) {
          existingStream = registeredFile.getOutputStream();
        }
      }
      this.loadFileProperties();
      // Determine the real length if not specified
      if (this.getRecordLength() == 0) {
        int maxLength = 0;
        for (int idx = 0; idx < getChildrenFields().size(); idx++) {
          int len = getChildrenFields().get(idx).getAbsoluteOffset() + getChildrenFields().get(idx).getLength() - 1;
          if (len > maxLength) {
            maxLength = len;
          }
        }
        this.setLength(maxLength);
        this.setRecordLength(maxLength);
      }
      this.openForWrite(existingStream);
    }
    int writeLen = this.getRecordLength();
    byte[] record;

    if (this.getRecordFormat().equals(NAsmRecordFormat.U)) {
      //SQ TODO implementation
      byte[] bl = ByteBuffer.allocate(4).putInt(writeLen + 4).array();
      byte[] rdw = new byte[] { 0x00, 0x00 };
      rdw[0] = bl[3];
      rdw[1] = bl[2];
      this.writeRecord(rdw, rdw.length, skipRecordHandler);
    } else if (isVariableMainframe()) {
      //Extract the length from the first two bytes of the buffer
      byte[] rdw = new byte[] { 0x00, 0x00, 0x00, 0x00 };
      rdw[2] = this.getRecordValue()[0];
      rdw[3] = this.getRecordValue()[1];
      int len = ByteBuffer.wrap(rdw).getInt();
      //  Don't write more than the allowed LRECL
      //SQ writing RDW included... we'd miss last 4 
      //writeLen = Math.min(len, this.getMaxRecordLength() - 4);
      writeLen = Math.min(len, this.getMaxRecordLength());
    }
    record = this.getRecordValue();
    this.writeRecord(record, writeLen, skipRecordHandler);
    if (this.getRecordFormat().equals(NAsmRecordFormat.LS_DOS) || this.getRecordFormat().equals(NAsmRecordFormat.LSFB_DOS)) {
      record = NAsmEnvironment.NL_DOS().getBytes();
      this.writeRecord(record, record.length, true, skipRecordHandler);
    } else if (this.getRecordFormat().equals(NAsmRecordFormat.LS_UNIX) || this.getRecordFormat().equals(NAsmRecordFormat.LSFB_UNIX)) {
      record = NAsmEnvironment.NL_UNIX().getBytes();
      this.writeRecord(record, record.length, true, skipRecordHandler);
    }
    this.statsWriteCount++;
    this.statsRecordCount++;
  }

  @Override
  public boolean next() throws NAsmException {
    return next(false, NAsmOnlineProgram.getReg1());
  }

  public boolean next(boolean skipRecordHandler) throws NAsmException {
    return next(skipRecordHandler, NAsmOnlineProgram.getReg1());
  }

  public boolean next(NAsmRegister readRegister) throws NAsmException {
    return next(false, readRegister);
  }

  public boolean next(boolean skipRecordHandler, NAsmRegister readRegister) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "next");
    this.input = true;
    if (this.isDummy()) {
      if (!this._eof) {
        String v = NAsmStrings.repeatCharacter((char) 0x00, this.getLengthBytes());
        this.setLength(this.getLengthBytes());
        this.setValue(v.getBytes(), 0);
        this._eof = true;
      }
      //NAsmOnlineProgram.getReg1().set(0);
      NAsmOnlineProgram.getReg1().load(this.getAddress());
      NAsmEnvironment.statsEndMethod(this.getClass(), "next");
      return false;
    }
    if (!this._isOpened) {
      this.registerFile();
      this.loadFileProperties();
      this.openForRead();
    }
    if (this.isEOF()) {
      //NAsmOnlineProgram.getReg1().set(0);
      NAsmOnlineProgram.getReg1().load(this.getAddress());
      NAsmEnvironment.statsEndMethod(this.getClass(), "next");
      return false;
    }

    this.prevRecord = this.currentRecord;
    this.currentRecord = this.readNextRecord(skipRecordHandler);

    if (currentRecord == null) {
      readRegister.load(this.getAddress());
      //Set Register 15 to no-zero value
      NAsmOnlineProgram.getReg15().set(1);
      this.setEOF(true);
      NAsmEnvironment.statsEndMethod(this.getClass(), "next");
      return false;
    }
    //Set Register 1 to data area
    readRegister.load(this.getAddress());
    //Set Register 15 to zero value
    NAsmOnlineProgram.getReg15().set(0);

    if (this.getRecordFormat().equals(NAsmRecordFormat.V) //
        || this.getRecordFormat().equals(NAsmRecordFormat.VB) //
        || this.getRecordFormat().equals(NAsmRecordFormat.VBS)) {
      this.RECORD_LENGTH.set(currentRecord.length);
      this.setRecordLength(currentRecord.length);
      this.setLength(currentRecord.length);
    }

    this.statsReadCount++;
    this.statsRecordCount++;
    //    if (NAsmEnvironment.DEBUG() && currentRecord != null) {
    //      NAsmEnvironment.debug("Dump record (" + this.getStatsReadCount() + "), length (" + currentRecord.length + ")");
    //      NAsmStrings.formatHexDump(currentRecord, true);
    //    }

    //If the user program will read more than the length of the actual buffer the framework will fill 
    //the rest of the buffer with 0x00
    if (this.getLengthBytes() > currentRecord.length) {
      String v = NAsmStrings.repeatCharacter((char) 0x00, this.getLengthBytes());
      this.setLength(this.getLengthBytes());
      this.setValue(v.getBytes(), 0);
    }

    // Change the record length according to the record length
    this.setLength(currentRecord.length);
    this.setRecordValue(currentRecord);
    NAsmEnvironment.statsEndMethod(this.getClass(), "next");
    return true;
  }

  /**
   * Close the file and force the statistics reset
   */
  public void closeWithReset() {
    this.close();
    this.resetStats();
  }

  @Override
  public void close() {
    if (this.isDummy()) {
      _isOpened = false;
      return;
    }
    if (useExtFH()) {
      if (NAsmEnvironment.getExtRecordHandler().close(this)) {
        _isOpened = false;
      }
      return;
    }
    if (fs != null) {
      try {
        if (fs instanceof BufferedOutputStream) {
          if (this._extRecordHandler) {
            //NAsmEnvironment.memoryStats("[NAsmFile/close] before close");
            BufferedOutputStream extOutStream = NAsmEnvironment.getMainInstance().getRecordHandler().close(this, (BufferedOutputStream) fs);
            //NAsmEnvironment.memoryStats("[NAsmFile/close] after close");
            if (extOutStream != null) {
              try {
                extOutStream.flush();
                extOutStream.close();
              } catch (Exception e) {
              }
            } else {
              BufferedOutputStream ofs = (BufferedOutputStream) fs;
              ofs.flush();
              ofs.close();
            }
          } else {
            BufferedOutputStream ofs = (BufferedOutputStream) fs;
            ofs.flush();
            //NAsmEnvironment.memoryStats("[NAsmFile/close] after flush");
            ofs.close();
            //NAsmEnvironment.memoryStats("[NAsmFile/close] after real close");
          }
        } else if (fs instanceof InputStream) {
          if (this._extRecordHandler) {
            InputStream extInStream = NAsmEnvironment.getMainInstance().getRecordHandler().close(this, (InputStream) fs);
            if (extInStream != null) {
              try {
                extInStream.close();
              } catch (Exception e) {
              }
            } else {
              InputStream ifs = (InputStream) fs;
              ifs.close();
            }
          } else {
            InputStream ifs = (InputStream) fs;
            ifs.close();
          }
        }
      } catch (Exception e) {

      }
    }
    if (sr != null) {
      try {
        if (this._extRecordHandler) {
          BufferedReader extInStream = NAsmEnvironment.getMainInstance().getRecordHandler().close(this, sr);
          if (extInStream != null) {
            extInStream.close();
          } else {
            sr.close();
          }
        } else {
          sr.close();
        }
      } catch (Exception e) {

      }
    }
    if (this.getRecordFormat().equals(NAsmRecordFormat.VSAM) //
        || this.getRecordFormat().equals(NAsmRecordFormat.VSAM_FB)) {
      try {
        // LL write a "tail" of records accumulated in rows array  
        if (this.getBulkInsertLimit() != null && rows.size() > 0) {
          // case Spanner
          boolean executeRecordHandler = (NAsmEnvironment.getMainInstance().getRecordHandler() != null);
          VSAM_STATUS_CODE vsc = bulkWrite(rows, false, executeRecordHandler);
          NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(vsc.getValue());
          rows.clear();
        } else {
          NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(0);
        }
        NAsmEnvironment.getMainInstance().getVsamHandler().close(this);
      } catch (NAsmException e) {
      }
    }
    _isOpened = false;
  }

  public void openInput() throws NAsmException {
    //Force a close first
    if (this._isOpened) {
      this.close();
    }

    this.forceOpen(true);

  }

  public void openOutput() throws NAsmException {
    if (this.getName().equals("STATS")) {
      //logger.debug("");
    }
    //Force a close first
    if (this._isOpened) {
      this.close();
    }
    this.forceOpen(false);
  }

  @Override
  public void write(NAsmField field) throws NAsmException {
    if (this.isDummy()) {
      //System.out.println("[DEBUG] write - dd=" + this.getName() + " - isDummy=true - no writes/OK!");
      return;
    }
    write(field, 0);
  }

  public void write(NAsmField field, boolean skipRecordHandler) throws NAsmException {
    if (this.isDummy()) {
      //System.out.println("[DEBUG] write - dd=" + this.getName() + " - isDummy=true - no writes/OK!");
      return;
    }
    write(field, 0, skipRecordHandler);
  }

  /**
   * Write the data of the passed field
   * 
   * @throws NAsmException
   */
  public void write(NAsmField field, int offset) throws NAsmException {
    write(field, offset, false);
  }

  public void write(NAsmField field, int offset, boolean skipRecordHandler) throws NAsmException {
    //    if (this.getPhysicalFile() != null && (this.getPhysicalFile().getName().contains("DEFPARMS"))) {
    //      logger.debug("");
    //      if (field.getString(0, 133).toString().contains("xxx")) {
    //        logger.debug("");
    //      }
    //    }
    if (this.isDummy()) {
      return;
    }
    int len = this.getMaxRecordLength();

    //SQ TODO review the following!
    int rdwLen = 0;
    if (isVariableMainframe()) {
      rdwLen = 4;
      this.setByteString(field.getByteString(offset, rdwLen));
      byte[] rdw = new byte[] { 0x00, 0x00, 0x00, 0x00 };
      rdw[2] = this.getRecordValue()[0];
      rdw[3] = this.getRecordValue()[1];
      len = ByteBuffer.wrap(rdw).getInt();
      len = Math.min(len, this.getMaxRecordLength());
    } else if (isVariableLUW()) {
      rdwLen = 2;
      this.setByteString(field.getByteString(offset, rdwLen));
      byte[] rdw = new byte[] { 0x00, 0x00, 0x00, 0x00 };
      rdw[2] = this.getRecordValue()[0];
      rdw[3] = this.getRecordValue()[1];
      len = ByteBuffer.wrap(rdw).getInt();
      len = Math.min(len, this.getMaxRecordLength());
    }
    this.setByteString(field.getByteString(offset, len));
    //this.setByteString(field.getByteString(offset + rdwLen, len), rdwLen);
    this.writeFile(skipRecordHandler);
  }

  /**
   * Write the data contained into the NAsmFile buffer
   * 
   * @throws NAsmException
   */
  public void write() throws NAsmException {
    if (this.isDummy()) {
      return;
    }
    this.writeFile();
  }

  /**
   * VSAM data bulk insert 
   * 
   * @param rows
   * @throws NAsmException
   */
  public VSAM_STATUS_CODE bulkWrite(List<NAsmField> rows, boolean replace, boolean executeRecordHandler) throws NAsmException {
    if (this.isDummy()) {
      return VSAM_STATUS_CODE.OPERATION_COMPLETED_SUCCESSFULLY;
    }
    if (this.getRecordFormat() != NAsmRecordFormat.VSAM //
        && this.getRecordFormat() != NAsmRecordFormat.VSAM_FB) {
      throw new NAsmException("File '" + this.getName() + "' is not defined as VSAM, operation not permitted.");
    }
    //NAsmEnvironment.memoryStats("[NAsmFile/bulkWrite] before bulkWrite"); //SQtmp PERF
    VSAM_STATUS_CODE vsc = NAsmEnvironment.getMainInstance().getVsamHandler().bulkWrite(this, rows, replace, executeRecordHandler);
    //NAsmEnvironment.memoryStats("[NAsmFile/bulkWrite] after bulkWrite"); //SQtmp PERF
    return vsc;
  }

  /**
   * VSAM data bulk read 
   * 
   * @param rows
   * @throws NAsmException
   */
  public List<NAsmField> bulkRead(boolean executeRecordHandler) throws NAsmException {
    return bulkRead(null, null, executeRecordHandler);
  }

  /**
   * VSAM data bulk read - var2fix varsion
   * 
   * @param rows
   * @throws NAsmException
   */
  public List<NAsmField> bulkRead(boolean executeRecordHandler, boolean var2fix) throws NAsmException {
    return bulkRead(null, null, executeRecordHandler, var2fix);
  }

  /**
   * VSAM data bulk read 
   * 
   * @param rows
   * @throws NAsmException
   */
  public List<NAsmField> bulkRead(Integer limit, Integer offset, boolean executeRecordHandler) throws NAsmException {
    return bulkRead(limit, offset, executeRecordHandler, false);
  }

  /**
   * VSAM data bulk read - var2fix version
   * 
   * @param limit
   * @param executeRecordHandler
   * @param var2fix - false is the default
   * @throws NAsmException
   */
  public List<NAsmField> bulkRead(Integer limit, Integer offset, boolean executeRecordHandler, boolean var2fix) throws NAsmException {
    List<NAsmField> rows;
    if (this.isDummy()) {
      return new ArrayList<>();
    }
    if (this.getRecordFormat() != NAsmRecordFormat.VSAM //
        && this.getRecordFormat() != NAsmRecordFormat.VSAM_FB) {
      throw new NAsmException("File '" + this.getName() + "' is not defined as VSAM, operation not permitted.");
    }
    rows = NAsmEnvironment.getMainInstance().getVsamHandler().bulkRead(this, limit, offset, executeRecordHandler, var2fix); //SQtmp
    return rows;
  }

  /**
   * 
   * @param key
   * @return
   * @throws NAsmException
   */
  public NAsmField start(NAsmField key) throws NAsmException {
    return start(key.getValue());
  }

  @Override
  public NAsmField start(byte[] key) throws NAsmException {
    if (this.isDummy()) {
      return this;
    }
    if (this.getRecordFormat() != NAsmRecordFormat.VSAM //
        && this.getRecordFormat() != NAsmRecordFormat.VSAM_FB) {
      throw new NAsmException("File '" + this.getName() + "' is not defined as VSAM, operation not permitted.");
    }
    byte[] buff = NAsmEnvironment.getMainInstance().getVsamHandler().start(this, key);
    NAsmField rc = this;
    if (buff != null) {
      //this.setLength(buff.length);
      //this.setRecordValue(buff);
      //NAsmOnlineProgram.getReg1().load(this.getAddress());
    } else {
      rc = null;
    }
    NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(NAsmEnvironment.getMainInstance().getVsamHandler().getStatusCode().getValue());
    return rc;
  }

  public NAsmField read(NAsmField key) throws NAsmException {
    return read(key.getValue(), false);
  }

  public NAsmField read(NAsmField key, boolean exactMatch) throws NAsmException {
    return read(key.getValue(), exactMatch);
  }

  @Override
  public NAsmField read(byte[] key) throws NAsmException {
    return read(key, false);
  }

  @Override
  public NAsmField read(byte[] key, boolean exactMatch) throws NAsmException {
    if (this.isDummy()) {
      return this;
    }
    if (this.getRecordFormat() != NAsmRecordFormat.VSAM //
        && this.getRecordFormat() != NAsmRecordFormat.VSAM_FB) {
      throw new NAsmException("File '" + this.getName() + "' is not defined as VSAM, operation not permitted.");
    }
    byte[] buff = NAsmEnvironment.getMainInstance().getVsamHandler().read(this, key, exactMatch);
    NAsmField rc = this;
    if (buff != null) {
      //Check if there is an external record handler to invoke
      if (this._extRecordHandler) {
        byte[] rhBuff = NAsmEnvironment.getMainInstance().getRecordHandler().decrypt(this, buff);
        if (rhBuff != null) {
          buff = rhBuff;
        }
      }
      this.setLength(buff.length);
      this.setRecordValue(buff);
      NAsmOnlineProgram.getReg1().load(this.getAddress()); //SQ TODO double check this
    } else {
      this.setEOF(true); //SQ TODO double check this and check all the other read methods
      rc = null;
    }
    NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(NAsmEnvironment.getMainInstance().getVsamHandler().getStatusCode().getValue());
    return rc;
  }

  @Override
  public void write(String area) throws NAsmException {
    if (this.isDummy()) {
      return;
    }
    this.set(area);
    this.writeFile();
  }

  @Override
  public int delete(byte[] key) throws NAsmException {
    if (this.isDummy()) {
      return 0;
    }
    if (this.getRecordFormat() != NAsmRecordFormat.VSAM //
        && this.getRecordFormat() != NAsmRecordFormat.VSAM_FB) {
      throw new NAsmException("File '" + this.getName() + "' is not defined as VSAM, operation not permitted.");
    }
    int rc = NAsmEnvironment.getMainInstance().getVsamHandler().delete(this, key);
    NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(NAsmEnvironment.getMainInstance().getVsamHandler().getStatusCode().getValue());
    return rc;
  }

  public int update(NAsmField field) throws NAsmException {
    return update(field.getValue());
  }

  @Override
  public int update(byte[] data) throws NAsmException {
    if (this.getRecordFormat() != NAsmRecordFormat.VSAM //
        && this.getRecordFormat() != NAsmRecordFormat.VSAM_FB) {
      throw new NAsmException("File '" + this.getName() + "' is not defined as VSAM, operation not permitted.");
    }
    //Extract the length from the first two bytes of the buffer
    byte[] rdw = new byte[] { 0x00, 0x00, 0x00, 0x00 };
    rdw[2] = data[0];
    rdw[3] = data[1];
    int len = ByteBuffer.wrap(rdw).getInt();
    //  Don't write more than the allowed LRECL
    int writeLen = Math.min(len, this.getMaxRecordLength() - 4);
    //Check if there is an external record handler to invoke
    byte[] encryptedData = null;
    if (writeLen > 0 && this._extRecordHandler) {
      encryptedData = NAsmEnvironment.getMainInstance().getRecordHandler().encrypt(this, data, writeLen);
      writeLen = encryptedData.length;
    }
    int rc = NAsmEnvironment.getMainInstance().getVsamHandler().update(this, data, encryptedData, writeLen);
    NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(NAsmEnvironment.getMainInstance().getVsamHandler().getStatusCode().getValue());
    return rc;
  }

  public boolean isLineSequential() {
    return getRecordFormat().equals(NAsmRecordFormat.LS_DOS) //
        || getRecordFormat().equals(NAsmRecordFormat.LS_UNIX) //
        || getRecordFormat().equals(NAsmRecordFormat.LSFB_DOS) //
        || getRecordFormat().equals(NAsmRecordFormat.LSFB_UNIX);
  }

  public boolean isVariable() {
    return isVariableLUW() || isVariableMainframe();
  }

  //SQ
  public boolean isFixed() {
    return getRecordFormat().equals(NAsmRecordFormat.F) //
        || this.getRecordFormat().equals(NAsmRecordFormat.FB) //
        || this.getRecordFormat().equals(NAsmRecordFormat.FBA);
  }

  public boolean isVariableLUW() {
    return getRecordFormat().equals(NAsmRecordFormat.U);
  }

  public boolean isVariableMainframe() {
    return getRecordFormat().equals(NAsmRecordFormat.V) || getRecordFormat().equals(NAsmRecordFormat.VB) //
        || getRecordFormat().equals(NAsmRecordFormat.VBS) //
        || getRecordFormat().equals(NAsmRecordFormat.VSAM);
  }

  private boolean registerFile() {
    return NAsmEnvironment.registerFile(this);
  }

  protected void forceOpen(boolean read) throws NAsmException {
    if (!this._isOpened) {
      boolean isRegistered = this.registerFile();
      try {
        this.loadFileProperties();
        if (NAsmEnvironment.TRACE()) {
          try {
            //Note: utilities like SORT do not have a currentInstance
            logger.info("[ I/O ][" + (NAsmEnvironment.getCurrentInstance() == null ? " N/A " : NAsmEnvironment.getCurrentInstance().getProgramId()
            ) + "] Opening for " + (read ? "input: " : "output: ")
                + this.getName() + " [" + this.recordFormat + "/" + this.maxRecordLength + "][" + (this.getTable() != null ? this.getTable() : this.getPhysicalFile()) + "]");
          } catch (Exception e) {
          }
        }
        if (this.isDummy()) {
          //System.out.println("[DEBUG] forceOpen - dd=" + this.getName() + " - isDummy=" + dummy);
          this._isOpened = true;
          this.orAt(48, DBC_OF_OPN);
          //if (this.getFileType().equals(NAsmFileType.VSAM)) {
          NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(0x00);
          //}
        } else {
          if (read) {
            this.openForRead();
          } else {
            BufferedOutputStream existingStream = null;
            if (isRegistered) {
              NAsmFile registeredFile = NAsmEnvironment.getFile(this.getName());
              if (registeredFile.isOpened()) {
                existingStream = registeredFile.getOutputStream();
                //Additional check, if the output is not to SYSOUT=*,
                //and the same file has been opened by another program will treat the output as dummy
                if (false || !this.isSysout()) { //TODO let's use an external variable - They transform SYSOUTS to DNS anyway ...
                  if (registeredFile.getOpenProgram() != null //
                      && NAsmEnvironment.getCurrentInstance() != null //
                      && !registeredFile.getOpenProgram().getProgramId().equals(NAsmEnvironment.getCurrentInstance().getProgramId())) {
                    logger.info("[ I/O ][" + NAsmEnvironment.getCurrentInstance().getProgramId() + "] " //
                        + this.getName() + " is already opened by [" + registeredFile.getOpenProgram().getProgramId() + "]" //
                        + " output will be treated as dummy");
                    this.setDummy(true);
                  }
                }
              }
            }
            if (existingStream != null) {
              //logger.debug("");
            }
            this.openForWrite(existingStream);
          }
          //Set the ASM switch for file open
          this.orAt(48, DBC_OF_OPN);
          //if (this.getFileType().equals(NAsmFileType.VSAM)) {
          NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(0x00);
          //}
        }
      } catch (NAsmFileNotDefinedException e) {
        logger.warn(e.getMessage());
        //DD not defined
        this.orAt(48, DBC_OF_ERR);
        this._isOpened = false;
        NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(0xff);
      } catch (NAsmException e) {
        throw e;
      }
    }
  }

  private void openForRead() throws NAsmException {
    try {
      if (useExtFH()) {
        if (!NAsmEnvironment.getExtRecordHandler().openInput(this)) {
          throw new NAsmException("Cannot open file " + this.getName() + " using the external file handler");
        }
      } else {
        if (this.getRecordFormat().equals(NAsmRecordFormat.VSAM) //
            || this.getRecordFormat().equals(NAsmRecordFormat.VSAM_FB)) {
          if (NAsmEnvironment.getMainInstance().getVsamHandler() == null) {
            throw new NAsmException(String.format("Unable to open VSAM file %1$s: please setup a valid VSAM handler using env variable " + INAsmProgramWrapper.BATCH_VSAM_HANDLER, this.physicalFile));
          }
          NAsmEnvironment.getMainInstance().getVsamHandler().open(this);
          NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(0);
          if (NAsmEnvironment.getMainInstance().getRecordHandler() != null //
              && NAsmEnvironment.getMainInstance().getRecordHandler().isHandled(this)) {
            _extRecordHandler = true;
          }
        } else {
          if (this.getRecordFormat().equals(NAsmRecordFormat.LS_DOS) || this.getRecordFormat().equals(NAsmRecordFormat.LS_UNIX)) {
            //Check if there is an external record handler to invoke
            if (NAsmEnvironment.getMainInstance().getRecordHandler() != null //
                && NAsmEnvironment.getMainInstance().getRecordHandler().isHandled(this)) {
              sr = NAsmEnvironment.getMainInstance().getRecordHandler().openInputLS(this);
              _extRecordHandler = true;
            } else {
              sr = new BufferedReader(new InputStreamReader(new FileInputStream(this.physicalFile), NAsmEnvironment.ENV_CHARSET()));
            }
          } else {
            if (NAsmEnvironment.getMainInstance().getRecordHandler() != null //
                && NAsmEnvironment.getMainInstance().getRecordHandler().isHandled(this)) {
              fs = NAsmEnvironment.getMainInstance().getRecordHandler().openInput(this);
              _extRecordHandler = true;
            } else {
              fs = new BufferedInputStream(new FileInputStream(this.physicalFile));
            }
            ifs = null;
          }
        }
      }
      _isOpened = true;
      openProgram = NAsmEnvironment.getCurrentInstance();
      input = true;
      statsRecordCount = 0;
    } catch (NAsmException ex) {
      throw ex;
    } catch (Exception ex) {
      NAsmException eex = new NAsmException(String.format("Unable to open file '%1$s' as %2$s.  Error ==> %3$s", this.physicalFile, getRecordFormat(), ex.getMessage()));
      eex.setJavaStack(ex);
      throw eex;
    }
  }

  private boolean openForWrite(BufferedOutputStream existingStream) throws NAsmException {
    try {
      if (useExtFH()) {
        if (!NAsmEnvironment.getExtRecordHandler().openOutput(this, existingStream)) {
          throw new NAsmException("Cannot open file " + this.getName() + " using the external file handler");
        }
      } else {
        if (this.getRecordFormat().equals(NAsmRecordFormat.VSAM) //
            || this.getRecordFormat().equals(NAsmRecordFormat.VSAM_FB)) {
          if (NAsmEnvironment.getMainInstance().getVsamHandler() == null) {
            throw new NAsmException(String.format("Unable to open VSAM file %1$s: please setup a valid VSAM handler using env variable " + INAsmProgramWrapper.BATCH_VSAM_HANDLER, this.physicalFile));
          }
          NAsmEnvironment.getMainInstance().getVsamHandler().open(this);
          NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(0);
          if (NAsmEnvironment.getMainInstance().getRecordHandler() != null //
              && NAsmEnvironment.getMainInstance().getRecordHandler().isHandled(this)) {
            _extRecordHandler = true;
          }
        } else {
          if (existingStream != null) {
            fs = existingStream;
          } else {
            if (NAsmEnvironment.getMainInstance().getRecordHandler() != null //
                && NAsmEnvironment.getMainInstance().getRecordHandler().isHandled(this)) {
              fs = NAsmEnvironment.getMainInstance().getRecordHandler().openOutput(this, isAppend());
              _extRecordHandler = true;
            } else {
              fs = new BufferedOutputStream(new FileOutputStream(this.physicalFile, isAppend()));
            }
          }
        }
      }
      _isOpened = true;
      openProgram = NAsmEnvironment.getCurrentInstance();
      input = false;
      return true;
    } catch (NAsmException ex) {
      throw ex;
    } catch (Exception ex) {
      NAsmException eex = new NAsmException(String.format("Unable to open file %1$s as write for create. Truncate = %2$s. Error ==> %3$s", this.physicalFile, truncate, ex.getMessage()));
      eex.setJavaStack(ex);
      throw eex;
    }
  }

  private void loadFileProperties() throws NAsmFileNotDefinedException, NAsmException {
    // Assign the file to the physical file
    //if (this.physicalFile == null) {
    if (this.getName().contains("SYSIN")) {
      logger.debug("");
    }
    // Search for an environment declaration
    NAsmFile f = NAsmEnvironment.externalFilesInfo.get(this.getName());
    // Try to read it from the runtime or the DD provider
    if (f == null) {
      // Runtime table
      NAsmDDcard dd = NAsmEnvironment.runtimeFilesInfo.get(this.getName());
      // DD provider
      if (dd == null) {
        dd = NAsmEnvironment.getMainInstance().getDDCardProvider().getDDcard(this.getName());
      }
      if (dd != null) {
        f = NAsmEnvironment.getMainInstance().addExternalFileInfo(dd.getDdName(), dd.getPath(), dd.getFormat(), dd.getLrecl(), dd.getDsnName(), dd.getAppend(), dd.isDummy(), dd.isSysout(), dd.isTemp(),
            dd.getTable(), dd.getKeyStart(), dd.getKeyLength());
        f.setKeyStart(dd.getKeyStart());
        f.setKeyLength(dd.getKeyLength());
        f.setTable(dd.getTable());
        f.setAscii(dd.isAscii());
        f.setBulkInsertLimit(dd.getBulkInsertLimit());
        if (f.getRecordFormat().equals(NAsmRecordFormat.VSAM) //
            || f.getRecordFormat().equals(NAsmRecordFormat.VSAM_FB)) {
          f.setFullPath("SQL:" + dd.getTable());
        }
      }
    }

    if (f != null) {
      if (f.getFullPath() != null && f.getFullPath().contains("STATO1")) {
        //logger.debug("");
      }
      this.setRecordFormat(f.getRecordFormat());
      this.setRecordLength(f.getRecordLength());
      this.setMaxRecordLength(f.getMaxRecordLength());
      this.setDsnName(f.getDsnName());
      this.setTruncate(f.isTruncate());
      this.setKeyStart(f.getKeyStart());
      this.setKeyLength(f.getKeyLength());
      this.setTable(f.getTable());
      this.setAscii(f.isAscii());
      this.setDummy(f.isDummy());
      this.setSysout(f.isSysout());
      this.setTemp(f.isTemp());
      this.setInCatalog(f.isInCatalog());
      this.setDcb(f.getDcb());
      this.setBulkInsertLimit(f.getBulkInsertLimit());
      if (!useExtFH()) { //
        this.physicalFile = new File(f.getFullPath());
      }
      //Force the buffer to fit the size of the file to improve performances
      if (f.isFixed()) {
        this.setLength(f.getMaxRecordLength());
      }
    } else {
      String file_recfmt = "";
      String file_reclen = "";
      String file_path = "";
      String append = "";
      file_recfmt = NAsmEnvironment.getEnvVar(NAsmEnvironment.FILE_PREFIX() + this.getName() + "_RECFMT");
      file_reclen = NAsmEnvironment.getEnvVar(NAsmEnvironment.FILE_PREFIX() + this.getName() + "_RECLEN");
      file_path = NAsmEnvironment.getEnvVar(NAsmEnvironment.FILE_PREFIX() + this.getName() + "_FULLPATH");
      append = NAsmEnvironment.getEnvVar(NAsmEnvironment.FILE_PREFIX() + this.getName() + "_APPEND");
      if (file_recfmt != null && file_recfmt.trim().length() > 0) {
        file_recfmt = file_recfmt.trim().toUpperCase(Locale.US);
        if (file_recfmt.equals(NAsmRecordFormat.F.toString())) {
          this.setRecordFormat(NAsmRecordFormat.F);
        } else if (file_recfmt.equals(NAsmRecordFormat.FB.toString())) {
          this.setRecordFormat(NAsmRecordFormat.FB);
        } else if (file_recfmt.equals(NAsmRecordFormat.FBA.toString())) {
          this.setRecordFormat(NAsmRecordFormat.FBA);
        } else if (file_recfmt.equals(NAsmRecordFormat.V.toString())) {
          this.setRecordFormat(NAsmRecordFormat.V);
        } else if (file_recfmt.equals(NAsmRecordFormat.VB.toString())) {
          this.setRecordFormat(NAsmRecordFormat.VB);
        } else if (file_recfmt.equals(NAsmRecordFormat.VBS.toString())) {
          this.setRecordFormat(NAsmRecordFormat.VBS);
        } else if (file_recfmt.equals(NAsmRecordFormat.U.toString())) {
          this.setRecordFormat(NAsmRecordFormat.U);
        } else if (file_recfmt.equals(NAsmRecordFormat.LS_DOS.toString())) {
          this.setRecordFormat(NAsmRecordFormat.LS_DOS);
        } else if (file_recfmt.equals(NAsmRecordFormat.LS_UNIX.toString())) {
          this.setRecordFormat(NAsmRecordFormat.LS_UNIX);
        } else if (file_recfmt.equals(NAsmRecordFormat.LSFB_DOS.toString())) {
          this.setRecordFormat(NAsmRecordFormat.LSFB_DOS);
          this.setRecordLength(0);
        } else if (file_recfmt.equals(NAsmRecordFormat.LSFB_UNIX.toString())) {
          this.setRecordFormat(NAsmRecordFormat.LSFB_UNIX);
          this.setRecordLength(0);
        } else if (file_recfmt.equals(NAsmRecordFormat.VSAM.toString())) {
          this.setRecordFormat(NAsmRecordFormat.VSAM);
        } else if (file_recfmt.equals(NAsmRecordFormat.VSAM_FB.toString())) {
          this.setRecordFormat(NAsmRecordFormat.VSAM_FB);
        } else {
          throw new NAsmFileNotDefinedException("File '" + this.getName() + "' invalid record format '" + file_recfmt + "'");
        }
      }
      if (this.getRecordFormat().equals(NAsmRecordFormat.UNKNOWN)) {
        throw new NAsmFileNotDefinedException("File '" + this.getName() + "' record format not defined");
      }

      if (file_reclen != null && file_reclen.trim().length() > 0) {
        try {
          this.setRecordLength(Integer.parseInt(file_reclen));
        } catch (Exception ex) {

        }
      }

      if ((this.getRecordFormat().equals(NAsmRecordFormat.F) //
          || this.getRecordFormat().equals(NAsmRecordFormat.FB) //
          || this.getRecordFormat().equals(NAsmRecordFormat.FBA) //
          || this.getRecordFormat().equals(NAsmRecordFormat.LSFB_DOS) //
          || this.getRecordFormat().equals(NAsmRecordFormat.LSFB_UNIX) //
      ) //
          && this.getRecordLength() == 0) {
        throw new NAsmFileNotDefinedException("File '" + this.getName() + "' record format '" + this.getRecordFormat() + "', record length not specified.");
      }

      //Force the buffer to fit the size of the file
      //to improve performances
      if (this.getRecordFormat().equals(NAsmRecordFormat.F) //
          || this.getRecordFormat().equals(NAsmRecordFormat.FB) // 
          || this.getRecordFormat().equals(NAsmRecordFormat.FBA) // 
      ) {
        this.setLength(this.getRecordLength());
      }

      if (this.getRecordFormat().equals(NAsmRecordFormat.LSFB_DOS) //
          || this.getRecordFormat().equals(NAsmRecordFormat.LSFB_UNIX) //
      ) {
        this.setLength(this.getRecordLength());
      }

      if (file_path == null || file_path.trim().length() == 0) {
        if (this.getFileType().equals(NAsmFileType.VIRTUAL)) {
          //Create a temporary file
          file_path = NAsmEnvironment.getTempDir() + File.separator + "NAsm" + this.getName() + "_" + NAsmStrings.getTimestamp().replace(":", "").replace(".", "_").replace("-", "").replace(" ", "_") + ".tmp";

          //Create it empty
          File fTemp = new File(file_path);
          try {
            fTemp.createNewFile();
          } catch (IOException e) {
            throw new NAsmFileNotDefinedException("Cannot create file '" + file_path + "'.");
          }
        } else {
          throw new NAsmFileNotDefinedException("File '" + this.getName() + "' not defined.");
        }
      }
      this.setFullPath(file_path);

      this.setTruncate(true);
      if (append != null && (append.toUpperCase(Locale.US).equals("YES") || append.toUpperCase(Locale.US).equals("TRUE"))) {
        this.setTruncate(false);
      }
      this.physicalFile = new File(file_path);
    }
    //}
    if (!this.getFileType().equals(NAsmFileType.VIRTUAL)) {
      this.resetStats();
    }
  }

  private void resetStats() {
    this.statsReadCount = 0;
    this.statsWriteCount = 0;
    this.setEOF(false);
    this.currentRecord = null;
    this.prevRecord = null;
    this.input = false;
  }

  private byte[] readNextRecord(boolean skipRecordHandler) throws NAsmException {
    if (useExtFH()) {
      byte[] bytes;
      if (this.getFileType().equals(NAsmFileType.VSAM) && this.key != null) {
        bytes = NAsmEnvironment.getExtRecordHandler().read(this, this.key.getValue());
      } else {
        bytes = NAsmEnvironment.getExtRecordHandler().read(this);
      }
      if (bytes == null) {
        if (NAsmEnvironment.getExtRecordHandler().isStatusEndOfFile(this)) {
          this.setEOF(true);
          return null;
        } else if (NAsmEnvironment.getExtRecordHandler().isStatusKeyNotFound(this)) {
          return null;
        }
        throw new NAsmException("Cannot read file " + this.getName() + " using the external file handler");
      }
      return bytes;
    }
    byte[] buff;
    try {
      // for fixed length just read num bytes
      if (this.getRecordFormat().equals(NAsmRecordFormat.F) //
          || this.getRecordFormat().equals(NAsmRecordFormat.FB) //
          || this.getRecordFormat().equals(NAsmRecordFormat.FBA) //
      ) {
        buff = this.readBytes(this.getRecordLength());
        // for line sequential ReadLine method on stream reader object returns line up to CR/LF
      } else if (this.getRecordFormat().equals(NAsmRecordFormat.LSFB_DOS) || this.getRecordFormat().equals(NAsmRecordFormat.LSFB_UNIX)) {
        //Check if there is an external record handler to invoke
        int recTerm = 1;
        if (this.getRecordFormat().equals(NAsmRecordFormat.LSFB_DOS)) {
          recTerm = 2;
        }
        int realLen = this.getRecordLength() + recTerm;
        byte[] tempBytes = this.readBytes(realLen);
        if (tempBytes == null) {
          return null;
        }
        byte[] termBytes = new byte[0];
        String expTerm;
        if (this.getRecordFormat().equals(NAsmRecordFormat.LSFB_DOS)) {
          expTerm = "0x0D0A";
          try {
            termBytes = new byte[] { tempBytes[realLen - 2], tempBytes[realLen - 1] };
          } catch (Exception exp) {

          }
        } else {
          expTerm = "0x0A";
          try {
            termBytes = new byte[] { tempBytes[realLen - 1] };
          } catch (Exception exp) {

          }
        }
        boolean sendExcp = false;
        if (termBytes.length != recTerm) {
          sendExcp = true;
        } else {
          if (recTerm == 1) {
            if (termBytes[0] != 10) {
              sendExcp = true;
            }
          } else {
            if (termBytes[0] != 13 || termBytes[1] != 10) {
              sendExcp = true;
            }
          }
        }
        if (sendExcp) {
          String buffString = new String(tempBytes, NAsmEnvironment.ENV_CHARSET());
          String dump = dumpRecord(tempBytes, buffString, realLen, realLen - recTerm, recTerm);
          String message = "Omitted expected record terminator " + expTerm //
              + ", rec_len: " + this.getRecordLength() //
              + ", offset: " + (this.getRecordLength() - recTerm + 1) //
              + ", hex value: 0x" + NAsmStrings.toHex(termBytes) + dump;
          throw new NAsmException(message);
        }
        buff = new byte[realLen - recTerm];
        System.arraycopy(tempBytes, 0, buff, 0, realLen - recTerm);
      } else if (this.getRecordFormat().equals(NAsmRecordFormat.LS_DOS) || this.getRecordFormat().equals(NAsmRecordFormat.LS_UNIX)) {
        String s = sr.readLine();
        if (s == null) {
          return null;
        }
        if (s.length() < 80) {
          s = NAsmStrings.formatSpaces(s, 80);
        }
        if (NAsmEnvironment.RUN_EBCDIC()) {
          //The expected format is ASCII, keep it internally as EBCDIC
          //to let the runtime functionalities to continue to work properly
          byte[] byteBufferEBCDIC = NAsmStrings.convertBytesToEBCDIC(s.getBytes());
          buff = byteBufferEBCDIC;
        } else {
          buff = s.getBytes(NAsmEnvironment.ENV_CHARSET());
        }
        // NAsmVariable length Linux format, 2 binary bytes for the length little endian
      } else if (this.getRecordFormat().equals(NAsmRecordFormat.U)) {
        buff = new byte[] { 0x00, 0x00, 0x00, 0x00 };
        byte[] rbytes = this.readBytes(2);
        // logger.info("VB=>[" + new String(rbytes) + "]");
        if (rbytes != null) {
          buff[2] = rbytes[0];
          buff[3] = rbytes[1];
          int len = ByteBuffer.wrap(buff).getInt() - 2;
          buff = this.readBytes(len);
          // String s = new String(bytes);
          // logger.info("VB=>[" + s + "]");
        } else {
          buff = null;
        }
        // NAsmVariable length Mainframe format, 4 binary bytes for the length (llrr) big
        // endian
      } else if (this.getRecordFormat().equals(NAsmRecordFormat.V) || this.getRecordFormat().equals(NAsmRecordFormat.VB) //
          || this.getRecordFormat().equals(NAsmRecordFormat.VBS)) {
        byte[] bytesLen = new byte[] { 0x00, 0x00, 0x00, 0x00 };
        byte[] rbytes = this.readBytes(4);
        // logger.info("VB=>[" + new String(rbytes) + "]");
        if (rbytes != null) {
          bytesLen[2] = rbytes[0];
          bytesLen[3] = rbytes[1];
          int len = ByteBuffer.wrap(bytesLen).getInt() - 4;
          byte[] bytesData = this.readBytes(len);
          buff = new byte[len + 4];
          System.arraycopy(rbytes, 0, buff, 0, 4);
          System.arraycopy(bytesData, 0, buff, 4, len);
          // String s = new String(bytes);
          // logger.info("VB=>[" + s + "]");
        } else {
          buff = null;
        }
      } else if (this.getRecordFormat().equals(NAsmRecordFormat.VSAM) //
          || this.getRecordFormat().equals(NAsmRecordFormat.VSAM_FB)) {
        buff = NAsmEnvironment.getMainInstance().getVsamHandler().read(this);
        NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(NAsmEnvironment.getMainInstance().getVsamHandler().getStatusCode().getValue());
      } else {
        throw new NAsmException(String.format("Unknown record format %1$s for file %2$s: ", this.getRecordFormat(), this.getName()));
      }
      //Check if there is an external record handler to invoke
      if (this._extRecordHandler && !skipRecordHandler) {
        buff = NAsmEnvironment.getMainInstance().getRecordHandler().decrypt(this, buff);
      }
      return buff;
    } catch (Exception ex) {
      NAsmException eex = new NAsmException(String.format("Error reading file %1$s with file organization %2$s  Error==>%3$s", this.physicalFile, this.getRecordFormat(), ex.getMessage()));
      eex.setJavaStack(ex);
      throw eex;
    }
  }

  public byte[] readBytes(int howMany) throws Exception {
    // read HowMany bytes from the local FileStream object
    byte[] bytes = new byte[howMany];
    //char[] chars = new char[howMany];
    int numBytesToRead = howMany;
    int numBytesRead = 0;
    try {
      while (numBytesToRead > 0) {
        if (ifs == null) {
          ifs = (InputStream) fs;
        }
        int n = ifs.read(bytes, numBytesRead, numBytesToRead);
        if (n <= 0) {
          break;
        }
        numBytesRead += n;
        numBytesToRead -= n;
      }
    } catch (Exception ex) {
      throw ex;
    }

    if (numBytesRead != bytes.length) {
      bytes = null;
    }
    return bytes;
  }

  private boolean writeRecord(byte[] data, int length, boolean skipRecordHandler) throws NAsmException {
    return writeRecord(data, length, false, false);
  }

  private boolean writeRecord(byte[] data, int length, boolean skipConversion, boolean skipRecordHandler) throws NAsmException {
    try {
      //Check if there is an external record handler to invoke
      byte[] encryptedData = null;
      if (length > 0 && data != null && this._extRecordHandler && !skipRecordHandler) {
        encryptedData = NAsmEnvironment.getMainInstance().getRecordHandler().encrypt(this, data, length);
        length = encryptedData.length;
      }
      if (this.getRecordFormat().equals(NAsmRecordFormat.VSAM) // 
          || this.getRecordFormat().equals(NAsmRecordFormat.VSAM_FB)) {
        if (this.getBulkInsertLimit() != null) {
          // LL bulk write
          NAsmField field = new NAsmField("VSAM_ROW", NAsmFieldType.ALPHANUMERIC, 0, length);
          if (encryptedData != null) {
            field.setByteArray(encryptedData, 0, length);
          } else {
            field.setByteArray(data, 0, length);
          }
          rows.add(field);
          if (rows.size() < this.getBulkInsertLimit()) {
            NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(VSAM_STATUS_CODE.OPERATION_COMPLETED_SUCCESSFULLY.getValue());
          } else {
            boolean executeRecordHandler = (NAsmEnvironment.getMainInstance().getRecordHandler() != null);
            VSAM_STATUS_CODE vsc = bulkWrite(rows, false, executeRecordHandler);
            NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(vsc.getValue());
            rows.clear();
          }
        } else {
          // LL normal write	
          NAsmEnvironment.getMainInstance().getVsamHandler().write(this, data, encryptedData, length);
          NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(NAsmEnvironment.getMainInstance().getVsamHandler().getStatusCode().getValue());
        }
      } else {
        BufferedOutputStream ofs = (BufferedOutputStream) fs;
        if (NAsmEnvironment.RUN_EBCDIC() && !skipConversion) {
          if (this.isSysout()) {
            //logger.debug("");
          }
          if (this.getRecordFormat().equals(NAsmRecordFormat.LS_DOS) // 
              || this.getRecordFormat().equals(NAsmRecordFormat.LS_UNIX) //
              || this.getRecordFormat().equals(NAsmRecordFormat.LSFB_DOS) //
              || this.getRecordFormat().equals(NAsmRecordFormat.LSFB_UNIX) //
              || this.isAscii()) {
            //The expected format is EBCDIC for facility converted to ASCII
            data = NAsmStrings.convertBytesToASCII(data);
            if (isLineSequential()) {
              //Trim right space
              for (int idx = length - 1; idx >= 0; idx--) {
                if (data[idx] == 0x20) {
                  length--;
                } else {
                  idx = 0;
                }
              }
              if (length == 0) {
                return true;
              }
            }
          }
        }
        ofs.write(data, 0, length);
        if (this.statsWriteCount > 0 && this.statsWriteCount % 1000 == 0) {
          ofs.flush();
        }
      }
      return true;
    } catch (Exception ex) {
      NAsmException eex = new NAsmException(String.format("Error writing file %1$s with file organization %2$s  Error==>%3$s", this.physicalFile, this.getRecordFormat(), ex.getMessage()));
      eex.setJavaStack(ex);
      throw eex;
    }
  }

  /**
   * @return the recordValue
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public byte[] getRecordValue() throws NAsmException {
    return this.getValue();
  }

  /**
   * @param recordValue
   *            the recordValue to set
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  private void setRecordValue(byte[] recordValue) throws NAsmException {
    this.setValue(recordValue, 0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#clone()
   */
  @Override
  protected NAsmFile clone() {
    NAsmFile f = null;
    try {
      f = new NAsmFile(this.getName(), this.fileType);
      for (int idx = 0; idx < this.getChildrenFields().size(); idx++) {
        f.addField(this.getChildrenFields().get(idx).clone());
      }
      f.setRecordValue(this.getRecordValue());
    } catch (NAsmException e) {
      NAsmEnvironment.console(e);
    }
    return f;
  }

  /**
   * @return the deviceType
   */
  public NAsmDeviceType getDeviceType() {
    return deviceType;
  }

  /**
   * @param deviceType
   *            the deviceType to set
   */
  public void setDeviceType(NAsmDeviceType deviceType) {
    this.deviceType = deviceType;
  }

  /**
   * @return the recordFormat
   */
  public NAsmRecordFormat getRecordFormat() {
    return recordFormat;
  }

  /**
   * @param recordFormat
   *            the recordFormat to set
   */
  public void setRecordFormat(NAsmRecordFormat recordFormat) {
    this.recordFormat = recordFormat;
  }

  /**
   * @return the recordLength
   */
  public int getRecordLength() {
    return recordLength;
  }

  /**
   * @param recordLength
   *            the recordLength to set
   */
  public void setRecordLength(int recordLength) {
    this.recordLength = recordLength;
  }

  /**
   * @return the maxRecordLength
   */
  public int getMaxRecordLength() {
    return maxRecordLength;
  }

  /**
   * @param recordLength
   *            the recordLength to set
   */
  public void setMaxRecordLength(int recordLength) {
    this.maxRecordLength = recordLength;
  }

  /**
   * @return the blockSize
   */
  public int getBlockSize() {
    return blockSize;
  }

  /**
   * @param blockSize
   *            the blockSize to set
   */
  public void setBlockSize(int blockSize) {
    this.blockSize = blockSize;
  }

  /**
   * @return the fullTrk
   */
  public boolean isFullTrk() {
    return fullTrk;
  }

  /**
   * @param fullTrk
   *            the fullTrk to set
   */
  public void setFullTrk(boolean fullTrk) {
    this.fullTrk = fullTrk;
  }

  /**
   * @return the fileType
   */
  public NAsmFileType getFileType() {
    return fileType;
  }

  /**
   * @return <b>true</b> if end of file is reached
   */
  public boolean isEOF() {
    return this._eof;
  }

  /**
   * Set the end of file status.
   * 
   * @param value
   *            to set
   */
  public void setEOF(boolean value) {
    this._eof = value;
  }

  /**
   * @return the statsReadCount
   */
  public int getStatsReadCount() {
    return statsReadCount;
  }

  /**
   * @return the statsWriteCount
   */
  public int getStatsWriteCount() {
    return statsWriteCount;
  }

  /**
   * @return the statsRecordCount
   */
  public int getStatsRecordCount() {
    return statsRecordCount;
  }

  /**
   * @param statsReadCount
   *            the statsReadCount to set
   */
  public void setStatsReadCount(int statsReadCount) {
    this.statsReadCount = statsReadCount;
  }

  /**
   * @param statsWriteCount
   *            the statsWriteCount to set
   */
  public void setStatsWriteCount(int statsWriteCount) {
    this.statsWriteCount = statsWriteCount;
  }

  /**
   * @param statsRecordCount
   *            the statsRecordCount to set
   */
  public void setStatsRecordCount(int statsRecordCount) {
    this.statsRecordCount = statsRecordCount;
  }

  /**
   * @return <b>true</b> if the file is open <br>
   *         <b>false</b> if the file is not open
   */
  public boolean isOpened() {
    return this._isOpened;
  }

  /**
   * File status not handled - to be implemented 
   * 
   * @return false [to be implemented]
   */
  public boolean isCheckFileMethod(int mask) throws NAsmException {
    byte[] b = this.getValue(48, 1);
    int value = b[0] & 0xff;
    if (this.isOpened() != ((value & mask) != 0)) {
      //logger.debug(""); //SQtmp debug catcher check
    }
    return (value & mask) != 0;
  }

  /**
   * @return the file full path
   */
  public String getFullPath() {
    return this.fullPath;
  }

  /**
   * @param fullPath
   *            the file full path
   */
  public void setFullPath(String fullPath) {
    this.fullPath = fullPath;
  }

  /**
   * @return the truncate
   */
  public boolean isTruncate() {
    return this.truncate;
  }

  /**
   * @return the "append" (not truncate)
   */
  public boolean isAppend() {
    return !this.truncate;
  }

  /**
   * @param truncate
   *            the truncate to set
   */
  public void setTruncate(boolean truncate) {
    this.truncate = truncate;
  }

  /**
   * @return the file size in bytes
   */
  public long getFileSize() {
    return this.physicalFile.length();
  }

  /**
   * @return <b>true</b> if file has an INSTREAM clause
   */
  public boolean isInStream() {
    return inStream;
  }

  /**
   * @param inStream
   *            the inStream to set
   */
  public void setInStream(boolean inStream) {
    this.inStream = inStream;
  }

  /**
   * 
   * @param value
   *            add a new value to the instream data
   */
  public void addInStreamValue(String value) {
    this.inStreamData.add(this.inStreamData.size(), value);
  }

  /**
   * @return the inStreamData
   */
  public Vector<String> getInStreamData() {
    return inStreamData;
  }

  /**
   * @return the input
   */
  public boolean isInput() {
    return input;
  }

  /**
   * @param input
   *            the input to set
   */
  public void setInput(boolean input) {
    this.input = input;
  }

  /**
   * @return the prevRecord
   */
  public byte[] getPrevRecord() {
    return prevRecord;
  }

  /**
   * Macro RDJFCB emulation
   * https://www.ibm.com/docs/en/zos/2.2.0?topic=information-jfcb-mapping
   * Format:
   * 
   * 0-43     : DSN name
   * 56       : X'FF' no concatenated datasets
   * 100-100  : File format:
   *            "X'C0'" - U - UNDEFINED
   *            "X'80'" - F - FIXED
   *            "X'40'" - V - VARIABLE
   *            "X'20'" - D - VARIABLE (FORMAT D FOR USASI/USASCII)
   *            "X'20'" - T - TRACK OVERFLOW
   *            "X'10'" - B - BLOCKED - MAY NOT OCCUR WITH UNDEFINED
   *            "X'04'" - A - AMERICAN NATIONAL STANDARD (ASA) CONTROL CHARACTER (IOS/ANSI)
   *             
   * 102-103  : BLOCKSIZE binary format
   * 104-105  : RECLEN binary format
   * 118-139  : Volume
   * 140-147  : ** RESERVED-O ** (MDC306) - USED for DD NAME
   * 
   * @param injfcb
   * @throws NAsmException
   * @throws Exception 
   */
  public void setInfoRDJFCB(NAsmField jFcbArea) throws NAsmException {
    //SQ
    //jFcbArea.set(NAsmStrings.formatSpaces(" ", jFcbArea.getLength()), 0);
    int areaLen;
    if (jFcbArea.getLength() > RDJFCB_LEN) {
      areaLen = RDJFCB_LEN;
      if (NAsmEnvironment.DEBUG()) {
        logger.debug("\n[WARNING][setInfoRDJFCB] Object len greater than: " + RDJFCB_LEN + "\n");
      }
    } else {
      areaLen = jFcbArea.getLength();
    }
    jFcbArea.set(NAsmStrings.formatSpaces(" ", areaLen), 0);

    //SQ 44 bytes (probably less?) for actual DSN name (gdg to be resolved)
    //SQ 45/46 bytes (I guess more!) for GDG +/- version
    //Example: "EM.T.GP04005.E.ACRO.COMBCID.G0004V00        +1"
    //SQ TODO - function for this:
    String fileName = this.getDsnName();
    String gdgVersion = "";
    final int FILENAME_SIZE = 46;

    String volume = isDummy() ? "" : NAsmEnvironment.DFT_VOLUME(); //SQ

    //SQ - TODO review override to reset other attributes in case of DUMMY/others

    //DUMMY files:
    if (fileName == null || isDummy()) {
      fileName = "NULLFILE";
    } else {
      //TEMP files:
      if (this.isTemp()) {
        fileName = this.getPhysicalFile().getName();
      } else {
        //GDG files:
        int idxClose = fileName.lastIndexOf(")");
        if (idxClose > 0) { //TODO function "isGDG()" with regExpr
          try {
            int idxOpen = fileName.lastIndexOf("(");
            gdgVersion = fileName.substring(idxOpen + 1, idxClose);
            fileName = this.getPhysicalFile().getName();
          } catch (Exception e) {
          }
        }
      }
    }
    //TODO remove this if after regression tests: we should just use FILENAME_SIZE
    if (gdgVersion != "") {
      jFcbArea.set(NAsmStrings.formatSpaces(fileName, FILENAME_SIZE - gdgVersion.length()) + gdgVersion, 0);
    } else {
      jFcbArea.set(NAsmStrings.formatSpaces(fileName, 44), 0);
    }

    jFcbArea.setByteString(_HIGH(1), 56);
    jFcbArea.set(NAsmStrings.formatSpaces(volume, 22), 118);
    jFcbArea.set(NAsmStrings.formatSpaces(this.getName(), 8), 140);
    byte[] len;
    try {
      len = NAsmStrings.valueToBinaryBigEndian(this.getRecordLength(), 2);
    } catch (Exception e) {
      len = new byte[2];
    }
    jFcbArea.setValue(len, 104);
    blockSize = 0;
    byte[] format = new byte[1];
    switch (this.getRecordFormat()) {
    case F:
      format[0] = (byte) 0x80;
      break;
    case FB:
      format[0] = (byte) 0x90;
      break;
    case LSFB_DOS:
      format[0] = (byte) 0x00;
      break;
    case LSFB_UNIX:
      format[0] = (byte) 0x00;
      break;
    case LS_DOS:
      format[0] = (byte) 0x00;
      break;
    case LS_UNIX:
      format[0] = (byte) 0x00;
      break;
    case U:
    case UNKNOWN:
      format[0] = (byte) 0xc0;
      break;
    case V:
      format[0] = (byte) 0x40;
      break;
    case VB:
    case VBS:
      blockSize = this.getRecordLength() + 4;
      format[0] = (byte) 0x50;
      break;
    default:
      format[0] = (byte) 0x00;
      break;
    }
    byte[] blkSize;
    try {
      blkSize = NAsmStrings.valueToBinaryBigEndian(blockSize, 2);
    } catch (Exception e) {
      blkSize = new byte[2];
    }
    jFcbArea.setValue(blkSize, 102);
    jFcbArea.setValue(format, 100);
  }

  //SQtmp TODO actual implementation
  public void setInfoRDJFCB(int jFcbArea) throws NAsmException {
    return;
  }

  /**
   * @return the dsnName
   */
  public String getDsnName() {
    return dsnName;
  }

  /**
   * @param dsnName the dsnName to set
   */
  public void setDsnName(String dsnName) {
    this.dsnName = dsnName;
  }

  /**
   * @return the physicalFile
   */
  public File getPhysicalFile() {
    return physicalFile;
  }

  /**
   * @return the keyStart
   */
  public int getKeyStart() {
    return keyStart;
  }

  /**
   * @return the keyLength
   */
  public int getKeyLength() {
    return keyLength;
  }

  /**
   * @return the table
   */
  public String getTable() {
    return table;
  }

  /**
   * @param keyStart the keyStart to set
   */
  public void setKeyStart(int keyStart) {
    this.keyStart = keyStart;
  }

  /**
   * @param keyLength the keyLength to set
   */
  public void setKeyLength(int keyLength) {
    this.keyLength = keyLength;
  }

  /**
   * @param table the table to set
   */
  public void setTable(String table) {
    this.table = table;
  }

  /**
   * @return the ascii
   */
  public boolean isAscii() {
    return ascii;
  }

  /**
   * @param ascii the ascii to set
   */
  public void setAscii(boolean ascii) {
    this.ascii = ascii;
  }

  /**
   * @return the dynamicEofMethod
   */
  public int getDynamicEofMethod() {
    return dynamicEofMethod;
  }

  /**
   * @param dynamicEofMethod the dynamicEofMethod to set
   */
  public void setDynamicEofMethod(int dynamicEofMethod) {
    this.dynamicEofMethod = dynamicEofMethod;
  }

  /**
   * @return the dummy
   */
  public boolean isDummy() {
    return dummy;
  }

  /**
   * @param dummy the dummy to set
   */
  public void setDummy(boolean dummy) {
    this.dummy = dummy;
  }

  /**
   * @return the sysout
   */
  public boolean isSysout() {
    return sysout;
  }

  /**
   * @param sysout the sysout to set
   */
  public void setSysout(boolean sysout) {
    this.sysout = sysout;
  }

  /**
   * @return the temp
   */
  public boolean isTemp() {
    return temp;
  }

  /**
   * @param temp the temp to set
   */
  public void setTemp(boolean temp) {
    this.temp = temp;
  }

  /**
   * @return the outputStream
   */
  public BufferedOutputStream getOutputStream() {
    if (fs == null) {
      return null;
    }
    return (BufferedOutputStream) fs;
  }

  /**
   * @return the file reader
   */
  public BufferedReader getReader() {
    return this.sr;
  }

  /**
   * @return the _extRecordHandler
   */
  public boolean isExtRecordHandler() {
    return _extRecordHandler;
  }

  /**
   * Load the file properties if it is not opened
   */
  public void refresh() throws NAsmException {
    if (!this._isOpened) {
      this.loadFileProperties();
    }
  }

  /**
   * @return the inCatalog
   */
  public boolean isInCatalog() {
    return inCatalog;
  }

  /**
   * @param inCatalog the inCatalog to set
   */
  public void setInCatalog(boolean inCatalog) {
    this.inCatalog = inCatalog;
  }

  /**
   * @return the dcb
   */
  public String getDcb() {
    return dcb;
  }

  /**
   * @param dcb the dcb to set
   */
  public void setDcb(String dcb) {
    this.dcb = dcb;
  }

  /**
   * @return the bulkInsertLimit
   */
  public Integer getBulkInsertLimit() {
    return bulkInsertLimit;
  }

  /**
   * @param bulkInsertLimit the bulkInsertLimit to set
   */
  public void setBulkInsertLimit(Integer bulkInsertLimit) {
    this.bulkInsertLimit = bulkInsertLimit;
  }

  /**
   * @return the openProgram
   */
  public AbstractProgram getOpenProgram() {
    return openProgram;
  }

  /**
   * 
   * @return
   */
  private boolean useExtFH() {
    return this.hasExtRecordHandler() && NAsmEnvironment.getExtRecordHandler().isHandled(this);
  }

  /**
   * @return TRUE if an external record handler is setup
   */
  public boolean hasExtRecordHandler() {
    return NAsmEnvironment.getExtRecordHandler() != null;
  }

}
