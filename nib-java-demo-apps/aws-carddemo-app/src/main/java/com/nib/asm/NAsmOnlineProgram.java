package com.nib.asm;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.exceptions.NAsmExitProgramException;
import com.nib.asm.sql.NAsmSql;
import com.nib.asm.storage.*;
import com.nib.asm.utils.NAsmEnvironment;
import com.nib.asm.utils.NAsmExecutionInfo;
import com.nib.asm.utils.NAsmRuntime;
import com.nib.asm.utils.NAsmStrings;
import com.nib.commons.AbstractProgram;
import com.nib.commons.Context;
//import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nib.supernaut.AbstractOnlineProgram;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;

/**
 * Class used to replicate the structure of a ASM370 program<br>
 * <br>
 * each ASM370 program contains an optional environment definition
 * section, an optional library definition section, and one or more activity
 * sections.<br>
 * 
 * @author nib-labs.io
 */
public abstract class NAsmOnlineProgram extends AbstractOnlineProgram implements INAsmClonable {

  public NAsmOnlineProgram(Context rt) throws NAsmException {
    super(rt);
  }

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  protected static final Logger logger = LoggerFactory.getLogger(NAsmOnlineProgram.class);

  /**
   * Method implemented by NAsmRuntime converted programs invoked to start the
   * program execution.
   * 
   * @throws NAsmException if there is an NAsmRuntime specific issue
   * 
   */
  //protected abstract int run(NAsmField... parameters) throws NAsmException;

  private NAsmField[] parameters = null;

  /**
   * Get program storage instance
   * 
   * @return
   */
  //public abstract NAsmField getStorage();

  private String name;
  private INAsmDDcardProvider ddCardProvider = null;
  private IVsamHandler vsamHandler = null;
  private IRecordHandler recordHandler = null;
  protected NAsmField _RETURN_CODE = new NAsmField(NAsmField.SYSTEM_RETURN_CODE, NAsmField.NAsmFieldType.NUMERIC_BINARY, 0, 4);
  protected NAsmField _COND_CODE = NAsmEnvironment.CODE_CODE();
  protected NAsmField _SQLCODE = new NAsmField(NAsmField.SYSTEM_SQLCODE, NAsmField.NAsmFieldType.NUMERIC_BINARY, 0, 4);
  protected NAsmField _SPACE = new NAsmField("SPACE", NAsmField.NAsmFieldType.ALPHANUMERIC, 0, NAsmEnvironment.FILE_MAX_RECLEN());
  protected NAsmField _ZERO = new NAsmField("ZERO", NAsmField.NAsmFieldType.NUMERIC_BINARY, 0, 1);
  protected NAsmField _NOTZERO = new NAsmField("NOTZERO", NAsmField.NAsmFieldType.NUMERIC_BINARY, 0, 1);
  protected NAsmField localRegisterArea = new NAsmField("REGISTER_AREA", NAsmField.NAsmFieldType.ALPHANUMERIC, 0, 1);
  protected NAsmField dummy = new NAsmField("DUMMY", NAsmField.NAsmFieldType.ALPHANUMERIC, 0, 32000);
  
  protected int ZERO = 0;
  protected static int RC_OK = 0;
  protected static int RC_NOK = 1;

  protected NAsmSql _SQL = null;
  //protected BasicDataSource _CTUDataSource = null;
  protected static NAsmStandardIO _STDOUT = new NAsmStandardIO();
  protected static NAsmOnlineProgram _CURRENT_INSTANCE = null;
  protected static int _statsPageNum = 0;

  public static int REG_0 = 0;
  public static int REG_1 = 1;
  public static int REG_2 = 2;
  public static int REG_3 = 3;
  public static int REG_4 = 4;
  public static int REG_5 = 5;
  public static int REG_6 = 6;
  public static int REG_7 = 7;
  public static int REG_8 = 8;
  public static int REG_9 = 9;
  public static int REG_10 = 10;
  public static int REG_11 = 11;
  public static int REG_12 = 12;
  public static int REG_13 = 13;
  public static int REG_14 = 14;
  public static int REG_15 = 15;

  /**
   * Method implemented by NAsmRuntime converted programs invoked before
   * starting the program execution.
   */
  public int execute(NAsmField... parameters) throws NAsmException {
    _NOTZERO.set(1);
    if (parameters != null && parameters.length >= 0) {
      this.parameters = new NAsmField[parameters.length];
      for (int idx = 0; idx < parameters.length; idx++) {
        this.parameters[idx] = parameters[idx];
      }
    }
    //Store the address allocated for this program execution
    //NAsmEnvironment.recordAddressesMap.clear();
    AbstractProgram callerInstance = NAsmEnvironment.getCurrentInstance();
    NAsmExecutionInfo.addToStack(this, callerInstance);
    NAsmField saveRegistersForUsing = new NAsmField("SAVE_REGISTERS", NAsmField.NAsmFieldType.ALPHANUMERIC, 0, 1);
    NAsmEnvironment.saveRegisters(REG_0, REG_15, saveRegistersForUsing, this);
    
    //Register the current program
    NAsmEnvironment.setCurrentInstance(this);
    int rc = 0;
    try {
    	
    	//TRACING programs chain
      if (NAsmEnvironment.TRACE()) {
      	if (NAsmExecutionInfo.tracePrevPgm.contentEquals(this.name)) {
      		 ++NAsmExecutionInfo.traceCount;
      		 if (NAsmExecutionInfo.traceCount <= NAsmExecutionInfo.TRACE_LIMIT) {
      		   logger.trace(String.format("%-" + (8 + 3 * (NAsmExecutionInfo.getStackLevel()+1)) +"s", " ") + String.format("[%-8s]", this.name ));
      		 }else {
      			 //no more trace for same program 
      		 }
      	}else {
      		if (NAsmExecutionInfo.traceCount > NAsmExecutionInfo.TRACE_LIMIT) {
      		  logger.trace(String.format("%-" + (8 + 3 * (NAsmExecutionInfo.getStackLevel()+1)) +"s", " ") + "... trace suppressed for '" + NAsmExecutionInfo.tracePrevPgm + "' (" + "Executed " + NAsmExecutionInfo.traceCount + " times)");
      		}
      		NAsmExecutionInfo.traceCount = 0;
      		logger.trace(String.format("%-" + (8 + 3 * (NAsmExecutionInfo.getStackLevel()+1)) +"s", " ") + String.format("[%-8s]", this.name ));
	        NAsmExecutionInfo.tracePrevPgm = this.name;
      	}
      }
      
      //rc = this.run(parameters);
    } catch (NAsmExitProgramException e) {
      if (e.getReturnCode() == 0) {
        rc = 0;
      } else {
        throw e;
      }
    }
    //Restore USING fields
    if (callerInstance != null) {
      for (int idx = 0; idx <= 15; idx++) {
        NAsmField f = saveRegistersForUsing.getSavedRegisters(this.getName())[idx].getUnsingRegisteredField(callerInstance.getProgramId());
        if (f != null) {
          f.setUsing(NAsmEnvironment.registers[idx], false);
        }
        byte[] value = saveRegistersForUsing.getSavedRegisters(this.getName())[idx].getValue(0, 4);
        if (NAsmEnvironment.isValidAddress(value)) {
          NAsmEnvironment.dismissAddress(value);
        }
      }
    }
    NAsmExecutionInfo.removeFromStack(this);
    NAsmEnvironment.setCurrentInstance(callerInstance);
//    if (NAsmEnvironment.recordAddressesMap.size() > 0) {
//      Iterator<String> it = NAsmEnvironment.recordAddressesMap.keySet().iterator();
//      Vector<String> keys = new Vector<>();   
//      while (it.hasNext()) {
//        String address = it.next();
//        keys.add(address);
//        if (address.getBytes(NAsmEnvironment.ENV_CHARSET())[2] == 0x03 &&
//            address.getBytes(NAsmEnvironment.ENV_CHARSET())[3] == 0xe2) {
//        }
//      }
//      //logger.info("Remove cached addresses: " + keys.size());
//      for (int idx=0;idx < keys.size();idx++) {
//        NAsmEnvironment.dismissAddress(keys.get(idx).getBytes(NAsmEnvironment.ENV_CHARSET()));
//      }
//      logger.info("[DEBUG][NAsmProgramCall:registerAddress] after call alloc=" + NAsmEnvironment.recordAddressesMap.size());
//    }
    return rc;
  }

  /**
   * @return the parameters
   */
  public NAsmField[] getParameters() {
    return parameters;
  }

  /**
   * Replicate the structure of a ASM370 Program.
   * 
   * @param name
   *            name of the original Runtime program
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
//  public NAsmOnlineProgram(String name) throws NAsmException {
//    this(name, null);
//  }

  /**
   * Replicate the structure of a ASM370 Program, using this method
   * users can override the STDIO default class used by the framework (see the
   * {@link NAsmStandardIO STDIO default class}). <br>
   * <br>
   * <b>Usage example:</b><br>
   * <br>
   * 
   * <b>A snippet of code from an Runtime converted program:</b><br>
   * 
   * <pre>
   * public ASMPGM01(NAsmStandardIO stdIO) throws NAsmException {
   * 	super("ASMPGM01", stdIO);
   * 	initiate();
   * }
   * </pre>
   * 
   * <b>An example of Java wrapper used to instantiate the Runtime converted
   * program:</b><br>
   * 
   * <pre>
   * NAsmOnlineProgram rpPgm = new ASMPGM01(new NAsmStandardIO() {
   *    &#64;Override
   *    public void display(String message) {
   *      logger.info(message);
   *    }
   *
   *    &#64;Override
   *    public void displaySTDOUT(String message) {
   *      logger.info(message);
   *    }
   *
   *    &#64;Override
   *    public void displayEXCEPTION(Exception e) {
   *      e.printStackTrace();
   *    }
   *  });
   *  rpPgm.execute();
   * }
   * </pre>
   * 
   * @param name
   *            name of the original Runtime program
   * @param stdIO
   *            STDIO class to redirect user logging and messaging
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
//  public NAsmOnlineProgram(String name, NAsmStandardIO stdIO) throws NAsmException {
//    if (stdIO != null) {
//			_STDOUT = stdIO;
//		}
//    NAsmEnvironment.setSTDIO(_STDOUT);
    // No license check
    // Commercial.checkLicence();
    //    if (NAsmEnvironment.getMainInstance() == null) {
    //			NAsmEnvironment.console(NAsmMessageType.INFO, "Version: " + NAsmRuntime.getPluginVersion());
    //		}
//    this.name = name;
//    _CURRENT_INSTANCE = this;
//  }

  /**
   * @return the program name
   */
  public String getName() {
    return name;
  }

  /**
   * Force a program execution stop.
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void stopExecution() throws NAsmException {
    NAsmEnvironment.closeFiles();
    if (this.getRETURN_CODE().getInt() == 0) {
      throw new NAsmException("STOP_EXECUTION");
    } else {
      throw new NAsmException("STOP_EXECUTION");
    }
  }

  /**
   * 
   * @return the SQL instance for this program
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public NAsmSql getSQL() throws NAsmException {
    if (this._SQL == null) {
      this.setSQL(new NAsmSql());
    }
    return this._SQL;
  }

  private void setSQL(NAsmSql _SQL) {
    this._SQL = _SQL;
  }

  /**
   * 
   * @return the latest generated SQLCODE
   */
  public NAsmField getSQLCODE() {
    return _SQLCODE;
  }

  /**
   * @return the latest RETURN_CODE setup for the program
   */
  public NAsmField getRETURN_CODE() {
    return _RETURN_CODE;
  }

  /**
   * @return the latest COND_CODE setup for the program
   */
  public NAsmField getCOND_CODE() {
    return _COND_CODE;
  }

  /**
   * 
   * @return the latest generated VSAM STATUS CODE
   */
  public IVsamHandler.VSAM_STATUS_CODE getVSAM_STATUS_CODE() {
    return vsamHandler.getStatusCode();
  }
  
  /**
   * Just a dummy method to avoid check syntax issues.
   */
  public static void _DUMMY() {
    // DO ABSOLUTELY NOTHING
  }

  /**
   * Set a return code for this program.
   * 
   * @param returnCode
   *            return code to be set
   * @throws NAsmException
   */
  protected void setRETURN_CODE(NAsmField returnCode) throws NAsmException {
    this._RETURN_CODE.set(returnCode.getInt());
  }

  /**
   * Set a cond code for this program.
   * 
   * @param condCode
   *            cond code to be set
   * @throws NAsmException
   */
  protected void setCOND_CODE(NAsmField condCode) throws NAsmException {
    this._COND_CODE.set(condCode.getInt());
  }

  /**
   * @param file
   *            file to be checked
   * @return <b>true</b> if the end of file condition is satisfied for this
   *         file
   */
  protected boolean eof(NAsmFile file) {
    return file.isEOF();
  }

  /**
   * Convert an HEX value to its EBCDIC value representation.
   * 
   * @param value
   *            value to be converted
   * @return EBCDIC representation
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  protected String convertEBCDIC(String value) throws NAsmException {
    try {
      return NAsmStrings.convertEBCDIC(value);
    } catch (Exception ex) {
      NAsmException eex = new NAsmException(String.format("EBCDIC to ASCII unsupported encoding, message:" + ex.getMessage()));
      eex.setJavaStack(ex);
      throw eex;
    }
  }

  /**
   * @param times
   *            how many New Line sequences
   * @return a sequence of New Line characters depending on "times" variable.
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  protected String newLine(int times) throws NAsmException {
    try {
      return NAsmEnvironment.NL(times);
    } catch (Exception ex) {
      NAsmException eex = new NAsmException(String.format("Get system NewLine sequence, message:" + ex.getMessage()));
      eex.setJavaStack(ex);
      throw eex;
    }
  }

  /**
   * 
   * Use this method to setup the file properties to be used at runtime.
   * 
   * @param ddName
   *            logical file name
   * @param fileFullPath
   *            physical file full path
   * @param recordFormat
   *            type of file a valid value of
   *            {@link NAsmFile.NAsmRecordFormat NAsmRecordFormat}
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void addExternalFileInfo(String ddName, String fileFullPath, NAsmFile.NAsmRecordFormat recordFormat) throws NAsmException {
    this.addExternalFileInfo(ddName, fileFullPath, recordFormat, 0, "", false, false, false, false, "", 0, 0);
  }

  /**
   * Use this method to setup the file properties to be used at runtime.
   * 
   * @param ddName
   *            logical file name
   * @param fileName
   *            physical file name
   * @param fileFormat
   *            file format, valid values are:<br>
   *            <ul>
   *            <li><b>LS_UNIX</b> Linux/Unix format
   *            <li><b>LS_DOS</b> Dos/Windows format
   *            </ul>
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void addDDStatement(String ddName, String fileName, String fileFormat) throws NAsmException {
    NAsmFile.NAsmRecordFormat recordFormat = NAsmFile.NAsmRecordFormat.UNKNOWN;
    switch (fileFormat) {
    case "LS_UNIX":
      recordFormat = NAsmFile.NAsmRecordFormat.LS_UNIX;
      break;
    case "LS_DOS":
      recordFormat = NAsmFile.NAsmRecordFormat.LS_DOS;
      break;
    }
    this.addExternalFileInfo(ddName, fileName, recordFormat);
  }

  /**
   * 
   * Use this method to setup the file properties to be used at runtime.
   * 
   * @param ddName
   *            logical file name
   * @param fileFullPath
   *            physical file full path
   * @param recordFormat
   *            type of file a valid value of
   *            {@link NAsmFile.NAsmRecordFormat NAsmRecordFormat}
   * @param append
   *            when TRUE the data will be appended to a previous file if
   *            present
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void addExternalFileInfo(String ddName, String fileFullPath, NAsmFile.NAsmRecordFormat recordFormat, boolean append, boolean dummy, boolean sysout, boolean temp, String table, int keyStart, int keyLength) throws NAsmException {
    this.addExternalFileInfo(ddName, fileFullPath, recordFormat, 0, "", append, dummy, sysout, temp, table, keyStart, keyLength);
  }

  /**
   * Use this method to setup the file properties to be used at runtime.
   * 
   * @param ddName
   *            logical file name
   * @param fileFullPath
   *            physical file full path
   * @param recordFormat
   *            type of file a valid value of
   *            {@link NAsmFile.NAsmRecordFormat NAsmRecordFormat}
   * @param recordLength
   *            record length
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void addExternalFileInfo(String ddName, String fileFullPath, NAsmFile.NAsmRecordFormat recordFormat, int recordLength) throws NAsmException {
    this.addExternalFileInfo(ddName, fileFullPath, recordFormat, recordLength, "", false, false, false, false, "", 0, 0);
  }

  /**
   * Use this method to setup the file properties to be used at runtime.
   * 
   * @param ddName
   *            logical file name
   * @param fileFullPath
   *            physical file full path
   * @param recordFormat
   *            type of file a valid value of
   *            {@link NAsmFile.NAsmRecordFormat NAsmRecordFormat}
   * @param recordLength
   *            record length
   * @param append
   *            when TRUE the data will be appended to a previous file if
   *            present
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public NAsmFile addExternalFileInfo(String ddName, String fileFullPath, NAsmFile.NAsmRecordFormat recordFormat, int recordLength, String dsnName, boolean append, boolean dummy, boolean sysout, boolean temp,
                                      String table, int keyStart, int keyLength) throws NAsmException {
    NAsmFile f = new NAsmFile(ddName, NAsmFile.NAsmFileType.FLAT);
    f.setRecordFormat(recordFormat);
    f.setRecordLength(recordLength);
    f.setMaxRecordLength(recordLength);
    f.setFullPath(fileFullPath);
    f.setTruncate(!append);
    f.setDsnName(dsnName);
    f.setDummy(dummy);
    f.setSysout(sysout);
    f.setTemp(temp);
    f.setTable(table);
    f.setKeyStart(keyStart);
    f.setKeyLength(keyLength);
    NAsmEnvironment.externalFilesInfo.put(ddName, f);
    return f;
  }

  /**
   * @param value
   *            when <b>true</b> enables debug tracing features <br>
   *            <b>false</b> disables debug tracing features
   */
  public void setDEBUG(boolean value) {
    NAsmEnvironment.setDEBUG(value);
  }

  /**
   *
   * @param value
   *            file prefix
   */
  public void setFILE_PREFIX(String value) {
    NAsmEnvironment.setFILE_PREFIX(value);
  }

  /**
   *
   * @param value
   *            a supported Java charset
   */
  public void setDEFAULT_CHARSET(String value) {
    NAsmEnvironment.setENV_CHARSET(value);
  }

  /**
   *
   * @param value
   *            the EBCDIC codepage
   */
  public void setEBCDIC_CODEPAGE(String value) {
    NAsmEnvironment.setEBCDIC_CODEPAGE(value);
  }

  /**
   *
   * @param value
   *            the default maximum record size
   */
  public void setFILE_MAX_RECLEN(Integer value) {
    NAsmEnvironment.setFILE_MAX_RECLEN(value);
  }

  /**
   *
   * @param value
   *            when <b>true</b> emit a warning message in case an array out
   *            of range error occurs <br>
   *            <b>false</b> generates a JAVA exception in case an array out
   *            of range error occurs
   */
  public void setAllowOutOfRange(boolean value) {
    NAsmEnvironment.setAllowOutOfRange(value);
  }

  /**
   *
   * @param value
   *            when <b>true</b> emit a warning message in case a malformed
   *            numeric value is used <br>
   *            <b>false</b> generates a JAVA exception in case a malformed
   *            numeric value is used
   */
  public void setAllowMalformedNumericValues(boolean value) {
    NAsmEnvironment.setAllowMalformedNumeric(value);
  }

  /**
   *
   * @param value
   *            when <b>true</b> prints file statistics at the end of a JOB
   *            execution <br>
   *            <b>false</b> doesn't print file statistics at the end of a JOB
   *            execution
   */
  public void setPrintJobStatistics(boolean value) {
    NAsmEnvironment.setPrintJobStatistics(value);
  }

  /**
   * Set up a database connection
   * 
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void setSqlConnection(Connection sqlConnection) throws NAsmException {
    this.setSQL(new NAsmSql(sqlConnection));
  }

  /**
   * Register the database CTU data source
   * 
   * @param databaseCTUDataSource
   *            a valid database CTU data source
   */
//  public void setDatabaseCTUDataSource(BasicDataSource databaseCTUDataSource) {
//    this._CTUDataSource = databaseCTUDataSource;
//  }

  /**
   * @param value
   *            a valid SQL url value
   */
  public void setSqlUrl(String value) {
    NAsmEnvironment.setSQL_URL(value);
  }

  /**
   * @param value
   *            a valid SQL driver value
   */
  public void setDatabaseDriver(String value) {
    NAsmEnvironment.setSQL_DRIVER(value);
  }

  /**
   * @param value
   *            a valid SQL user value
   */
  public void setDatabaseUserName(String value) {
    NAsmEnvironment.setSQL_USER(value);
  }

  /**
   * @param value
   *            a valid SQL password value
   */
  public void setDatabasePassword(String value) {
    NAsmEnvironment.setSQL_PASSWORD(value);
  }

  /**
   * @return the program in execution
   */
  public static NAsmOnlineProgram getInstance() {
    return _CURRENT_INSTANCE;
  }

  /**
   * Print files statistics.
   * 
   * @param sourceName
   *            caller module
   */
  public static void printFilesStats(String sourceName) {
    printFilesStats(sourceName, null);
  }

  /**
   * Print files statistics.
   * 
   * @param sourceName
   *            caller module
   * @param filter
   *            files to be printed
   */
  public static void printFilesStats(String sourceName, HashMap<String, NAsmFile> filter) {
    if (!NAsmEnvironment.PRINT_RUN_STATISTICS()) {
			return;
		}
    _statsPageNum++;
    NAsmStandardIO _STDOUT = new NAsmStandardIO();
    String h = NAsmStrings.getTimestamp() + NAsmStrings.center(NAsmRuntime.getPluginName() + " v" + NAsmRuntime.getPluginVersion(), 60) + NAsmEnvironment.getPAGE_STRING() + " " + String.format("%1$6s", _statsPageNum);
    int headLength = h.length();
    _STDOUT.flush();
    _STDOUT.display("");
    _STDOUT.flush();
    _STDOUT.display(h);
    // TODO - Insert customer name?
    h = NAsmStrings.center(" ", headLength);
    _STDOUT.display(h);
    h = "FILE STATISTICS - " + NAsmRuntime.getPluginName() + " v" + NAsmRuntime.getPluginVersion();
    _STDOUT.display(h);

    // for (int idx = 0; idx < this.vFiles.size(); idx++) {
    // NAsmFile f = this.vFiles.get(idx);
    // String l = this.formatStatsLine(f);
    // _STDOUT.display(l);
    // }
    // Vector<NAsmFile> cf = NAsmOnlineProgram.getProgramFiles();
    for (int idx = 0; idx < NAsmEnvironment.getFiles().size(); idx++) {
      NAsmFile f = NAsmEnvironment.getFiles().get(idx);
      // if (!this.hFiles.containsKey(f.getName())) {
      if (filter == null || filter.containsKey(f.getName())) {
        String l = formatStatsLine(f);
        _STDOUT.display(l);
      }
    }

    _STDOUT.display("");
    _STDOUT.flush();
  }

  private static String formatStatsLine(NAsmFile f) {
    String mask = "000,000";
    mask = mask.substring(7);
    DecimalFormat formatter = new DecimalFormat(mask);
    DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
    char comma = '.';
    char group = ',';
    // char comma = symbols.getDecimalSeparator();
    // char group = symbols.getGroupingSeparator();
    // if (group != '.' && group != ',') {
    // if (comma == ',') {
    // group = '.';
    // } else {
    // group = ',';
    // }
    // }
    // Force US standard to make it similar to real Runtime stats
    symbols.setDecimalSeparator(comma);
    symbols.setGroupingSeparator(group);
    formatter.setDecimalFormatSymbols(symbols);
    int recCount = 0;
    if (f.RECORD_COUNT != null) {
      try {
        recCount = f.RECORD_COUNT.get();
      } catch (Exception e) {
      }
    }
    String recFormat = f.getRecordFormat().toString();
    if (f.getRecordFormat().equals(NAsmFile.NAsmRecordFormat.UNKNOWN)) {
      if (f.isInStream()) {
        recFormat = "INSTREAM";
      }
    }
    String l;
    try {
      l = NAsmStrings.formatSpaces(f.getName(), 17) //
          + String.format("%1$7s", NAsmStrings.padLeadingZeroes(formatter.format(recCount), "" + group)) //
          + "   " //
          + (f.isInput() ? " INPUT" : "OUTPUT") //
          + "  " //
          + NAsmStrings.formatSpaces(f.getFileType().toString(), 10) //
          + " " //
          + NAsmStrings.formatSpaces(recFormat, 15) //
          + " " //
          + String.format("%1$7s", NAsmStrings.padLeadingZeroes(formatter.format(f.RECORD_LENGTH.get()), "" + group))//
      //
      ;
    } catch (NAsmException e) {
      return "???";
    }
    return l;
  }

  /**
   * @return the ddCardProvider
   */
  public INAsmDDcardProvider getDDCardProvider() {
    return ddCardProvider;
  }

  /**
   * @param ddCardProvider
   *            the ddCardProvider to set
   */
  public void setDDCardProvider(INAsmDDcardProvider ddCardProvider) {
    this.ddCardProvider = ddCardProvider;
  }

  public static NAsmRegister getRegDebug() {
    return NAsmEnvironment.debugRegister;
  }

  public static NAsmRegister getReg0() {
    return NAsmEnvironment.registers[REG_0];
  }

  public static NAsmRegister getReg1() {
    return NAsmEnvironment.registers[REG_1];
  }

  public static NAsmRegister getReg2() {
    return NAsmEnvironment.registers[REG_2];
  }

  public static NAsmRegister getReg3() {
    return NAsmEnvironment.registers[REG_3];
  }

  public static NAsmRegister getReg4() {
    return NAsmEnvironment.registers[REG_4];
  }

  public static NAsmRegister getReg5() {
    return NAsmEnvironment.registers[REG_5];
  }

  public static NAsmRegister getReg6() {
    return NAsmEnvironment.registers[REG_6];
  }

  public static NAsmRegister getReg7() {
    return NAsmEnvironment.registers[REG_7];
  }

  public static NAsmRegister getReg8() {
    return NAsmEnvironment.registers[REG_8];
  }

  public static NAsmRegister getReg9() {
    return NAsmEnvironment.registers[REG_9];
  }

  public static NAsmRegister getReg10() {
    return NAsmEnvironment.registers[REG_10];
  }

  public static NAsmRegister getReg11() {
    return NAsmEnvironment.registers[REG_11];
  }

  public static NAsmRegister getReg12() {
    return NAsmEnvironment.registers[REG_12];
  }

  public static NAsmRegister getReg13() {
    return NAsmEnvironment.registers[REG_13];
  }

  public static NAsmRegister getReg14() {
    return NAsmEnvironment.registers[REG_14];
  }

  public static NAsmRegister getReg15() {
    return NAsmEnvironment.registers[REG_15];
  }

  /**
   * @return the localRegisterArea
   */
  public NAsmField getLocalRegisterArea() {
    return localRegisterArea;
  }

  protected void restoreRegistersPosition(int startReg, int endReg, NAsmField area, int areaOffset) throws NAsmException {
    NAsmEnvironment.restoreRegistersPosition(startReg, endReg, area, areaOffset);
  }

  protected void restoreRegistersPosition(int startReg, int endReg, NAsmField area) throws NAsmException {
    NAsmEnvironment.restoreRegistersPosition(startReg, endReg, area, 0);
  }

  protected void saveRegistersPosition(int startReg, int endReg, NAsmField area, int areaOffset) throws NAsmException {
    NAsmEnvironment.saveRegisters(startReg, endReg, area, areaOffset);
  }

  protected void saveRegistersPosition(int startReg, int endReg, NAsmField area) throws NAsmException {
    NAsmEnvironment.saveRegisters(startReg, endReg, area, 0);
  }

  protected String _LOW(int count) {
    return NAsmStrings.repeatCharacter((char) 0x00, count);
  }

  protected String _HIGH(int count) {
    return NAsmStrings.repeatCharacter((char) 0xff, count);
  }

  protected String _SPACE(int count) {
    return NAsmStrings.repeatCharacter(' ', count);
  }

  protected String _SPACE_NATIVE(int count) {
    if (NAsmEnvironment.RUN_EBCDIC()) {
      return NAsmStrings.repeatCharacter((char) 0x40, count);
    }else {
      return NAsmStrings.repeatCharacter(' ', count);
    }
  }

  protected int _EBCDIC_OFFSET(char value) {
    return NAsmStrings.getEbcdicSequence(value);
  }

  protected NAsmField getStorage(int size) throws NAsmException {
		NAsmField fld = new NAsmField(NAsmField._GETMAIN_STORAGE + this.getName() + "_" + size, NAsmField.NAsmFieldType.ALPHANUMERIC, 0, size);
		//No need, it's already all 0x00
    //fld.set(_LOW(size));
		NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(0); //SQ RC=0 from GETMAIN/SVC 10
    return fld;
  }

  /**
   * @return the dummy
   */
  protected NAsmField getDummy() {
    return dummy;
  }

  /**
   * @return the vsamHandler
   */
  public IVsamHandler getVsamHandler() {
    return vsamHandler;
  }

  /**
   * @param vsamHandler the vsamHandler to set
   */
  public void setVsamHandler(IVsamHandler vsamHandler) {
    this.vsamHandler = vsamHandler;
  }

  /**
   * @return the recordHandler
   */
  public IRecordHandler getRecordHandler() {
    return recordHandler;
  }

  /**
   * @param recordHandler the recordHandler to set
   */
  public void setRecordHandler(IRecordHandler recordHandler) {
    this.recordHandler = recordHandler;
  }

}
