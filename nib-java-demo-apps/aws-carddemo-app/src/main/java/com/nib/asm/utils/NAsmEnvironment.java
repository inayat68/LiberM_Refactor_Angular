package com.nib.asm.utils;

import com.nib.asm.INAsmLogger;
import com.nib.asm.INAsmProgramWrapper;
import com.nib.asm.NAsmOnlineProgram;
import com.nib.asm.NAsmBatchProgram;
import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.storage.INAsmStandardIO.NAsmMessageType;
import com.nib.asm.storage.*;
import com.nib.asm.storage.NAsmField.NAsmFieldType;
import com.nib.commons.AbstractProgram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * Define and handle all he runtime NAsmRuntime variables.
 * 
 * @author nib-labs.io
 */
public class NAsmEnvironment {

  private static final Logger logger = LoggerFactory.getLogger(NAsmEnvironment.class);

  public static final PrintStream DFT_OUT = System.out;

  /**
   * Environment variable for setting up the framework debugging feature, the
   * accepted values are:<br>
   * <br>
   * <ul>
   * <li>TRUE
   * <li>ON
   * <li>YES
   * </ul>
   * <br>
   * All the debug messages emitted by the framework will invoke
   * {@link NAsmStandardIO#displaySTDOUT
   * NAsmStandardIO#displaySTDOUT method} to be printed out or redirected to a
   * customized <br>
   * <br>
   * i.e. to set tracing up (the following example is for a Linux KSH shell):<br>
   * <br>
   * export NIB2JAVA_DEBUG=TRUE <br>
   * <br>
   */
  public static final String ENV_VAR_NIB_DEBUG = "NIB2JAVA_DEBUG";

  /**
   * Environment variable for setting up the framework tracing feature, the
   * accepted values are:<br>
   * <br>
   * <ul>
   * <li>TRUE
   * <li>ON
   * <li>YES
   * </ul>
   * <br>
   * All the trace messages emitted by the framework will invoke
   * {@link NAsmStandardIO#displaySTDOUT
   * NAsmStandardIO#displaySTDOUT method} to be printed out or redirected to a
   * customized <br>
   * <br>
   * i.e. to set tracing up (the following example is for a Linux KSH shell):<br>
   * <br>
   * export NIB2JAVA_TRACE=TRUE <br>
   * <br>
   */
  public static final String ENV_VAR_NIB_TRACE = "NIB2JAVA_TRACE";

  /**
   * Environment variable for setting up the framework statistics feature, the
   * accepted values are:<br>
   * <br>
   * <ul>
   * <li>TRUE
   * <li>ON
   * <li>YES
   * </ul>
   * <br>
   * i.e. to set tracing up (the following example is for a Linux KSH shell):<br>
   * <br>
   * export NIB2JAVA_STATS=TRUE <br>
   * <br>
   */
  public static final String ENV_VAR_NIB_STATS = "NIB2JAVA_STATS";

  /**
   * Environment variable for setting up the prefix for the environment variables
   * used to define the program file properties, the default value is
   * <b>NIB2JAVA_</b><br>
   * <br>
   * i.e. to setup file properties for file FILEIN (the following example is for a
   * Linux KSH shell):<br>
   * <br>
   * export
   * <b>NIB2JAVA_</b>FILEIN_FULLPATH=$HOME/nib_prj/tests_data/XYZ.FILDATA.PIFAT01
   * <br>
   */
  public static final String ENV_VAR_NIB_FILE_PREFIX = "NIB2JAVA_FILE_PREFIX";
  private static final String _NIB_FILE_PREFIX_DFLT = "NIB2JAVA_";

  /**
   * Environment variable for setting up the charset to be used for the Java I/O
   * operations, the default value is ISO-8859-1<br>
   * <br>
   * i.e. to setup a charset UTF-8 (the following example is for a Linux KSH
   * shell):<br>
   * <br>
   * export ENV_VAR_NIB_CHARSET=UTF-8 <br>
   */
  public static final String ENV_VAR_NIB_CHARSET = "NIB2JAVA_CHARSET";
  public static final String _NIB_ENV_DFT_CHARSET = "ISO-8859-1";

  /**
   * Environment variable for setting up the EBCDIC codepage to be used to convert
   * Hexadecimal program embedded values at runtime, the default value is
   * Cp1047<br>
   * <br>
   * i.e. to setup an EBCDIC codepage CP1047 (the following example is for a Linux
   * KSH shell):<br>
   * <br>
   * export NIB2JAVA_EBCDIC_CODEPAGE=CP1047 <br>
   */
  public static final String ENV_VAR_NIB_EBCDIC_CODEPAGE = "NIB2JAVA_EBCDIC_CODEPAGE";
  public static final String _NIB_EBCDIC_DFT_CODEPAGE = "Cp1047";

  /**
   * Environment variable for setting up the maximum files record length, the
   * default value is 32767<br>
   * <br>
   * i.e. to setup a maximum record length of 1000 bytes (the following example is
   * for a Linux KSH shell):<br>
   * <br>
   * export NIB2JAVA_FILE_MAX_RECLEN=1000 <br>
   * 
   */
  public static final String ENV_VAR_NIB_FILE_MAX_RECLEN = "NIB2JAVA_FILE_MAX_RECLEN";
  public static final int _NIB_FILE_MAX_RECLEN = 32767;

  /**
   * Environment variable for setting up array out of range runtime error
   * behavior, by default the runtime execution will stop with an exception,
   * setting up this variable using one of the following value:<br>
   * <br>
   * <ul>
   * <li>TRUE
   * <li>ON
   * <li>YES
   * </ul>
   * <br>
   * the execution will continue sending a warning message, note, the array cell
   * content will be unpredictable<br>
   * <br>
   * i.e. to setup the variable to emit a warning message (the following example
   * is for a Linux KSH shell):<br>
   * <br>
   * export NIB2JAVA_ALLOW_OUT_OF_RANGE=TRUE <br>
   * 
   */
  public static final String ENV_VAR_NIB_ALLOW_OUT_OF_RANGE = "NIB2JAVA_ALLOW_OUT_OF_RANGE";

  /**
   * Environment variable for setting up numeric values interpretation, by default
   * the runtime execution will stop with an exception any usage of numeric
   * variables containing malformed values, setting up this variable using one of
   * the following value:<br>
   * <br>
   * <ul>
   * <li>TRUE
   * <li>ON
   * <li>YES
   * </ul>
   * <br>
   * the execution will continue sending a warning message, note, the numeric
   * values will be setup to zero<br>
   * <br>
   * i.e. to setup the variable to emit a warning message (the following example
   * is for a Linux KSH shell):<br>
   * <br>
   * export NIB2JAVA_ALLOW_MALFORMED_NUMERIC_VALUES=TRUE <br>
   * 
   */
  public static final String ENV_VAR_NIB_ALLOW_MALFORMED_NUMERIC_VALUES = "NIB2JAVA_ALLOW_MALFORMED_NUMERIC_VALUES";

  /**
   * Environment variable for printing file statistics after a JOB execution, by
   * default the runtime will print statistics, setting up this variable using one
   * of the following value:<br>
   * <br>
   * <ul>
   * <li>TRUE
   * <li>ON
   * <li>YES
   * </ul>
   * <br>
   * file statistics will be printed at the end of a JOB execution<br>
   * <br>
   * i.e. to setup the variable to disable statistics printing (the following
   * example is for a Linux KSH shell):<br>
   * <br>
   * export NIB2JAVA_PRINT_JOB_STATISTICS=FALSE <br>
   * 
   */
  public static final String ENV_VAR_NIB_PRINT_RUN_STATISTICS = "NIB2JAVA_PRINT_JOB_STATISTICS";

  /**
   * Environment variable for handling data in EBCDIC, by
   * default the runtime will print statistics, setting up this variable using one
   * of the following value:<br>
   * <br>
   * <ul>
   * <li>TRUE
   * <li>FALSE
   * </ul>
   * 
   */
  public static final String ENV_VAR_NIB_RUN_EBCDIC = "NIB2JAVA_RUN_EBCDIC";

  /**
   * Environment variable for handling SYSOUT in a readable format:<br>
   * <br>
   * <ul>
   * <li>TRUE
   * <li>FALSE
   * </ul>
   * 
   */
  public static final String ENV_VAR_NIB_WRITE_LS_SYSOUT = "NIB2JAVA_WRITE_LS_SYSOUT";

  /**
   * Environment variable for setting up the SQL url value, it's an optional value
   * that needs to be setup in case the converted Runtime program contains SQL
   * statements. <br>
   * <br>
   * i.e. sample of SQL url usage (the following example is for a Linux KSH
   * shell):<br>
   * <br>
   * export NIB2JAVA_SQL_URL="jdbc:mysql://xbeprodserver:3306/nibconverted" <br>
   * 
   */
  public static final String ENV_VAR_NIB_SQL_URL = "NIB2JAVA_SQL_URL";

  /**
   * Environment variable for setting up the SQL driver value, it's an optional
   * value that needs to be setup in case the converted Runtime program
   * contains SQL statements. <br>
   * <br>
   * i.e. sample of SQL driver usage (the following example is for a Linux KSH
   * shell):<br>
   * <br>
   * export NIB2JAVA_SQL_DRIVER="com.mysql.jdbc.Driver" <br>
   * 
   */
  public static final String ENV_VAR_NIB_SQL_DRIVER = "NIB2JAVA_SQL_DRIVER";

  /**
   * Environment variable for setting up the SQL user value, it's an optional
   * value that needs to be setup in case the converted Runtime program
   * contains SQL statements. <br>
   * <br>
   * i.e. sample of SQL user usage (the following example is for a Linux KSH
   * shell):<br>
   * <br>
   * export NIB2JAVA_SQL_USER="nibrodusr" <br>
   * 
   */
  public static final String ENV_VAR_NIB_SQL_USER = "NIB2JAVA_SQL_USER";

  /**
   * Environment variable for setting up the SQL password value, it's an optional
   * value that needs to be setup in case the converted Runtime program
   * contains SQL statements. <br>
   * <br>
   * i.e. sample of SQL password usage (the following example is for a Linux KSH
   * shell):<br>
   * <br>
   * export NIB2JAVA_SQL_PASSWORD="mypassword" <br>
   * 
   */
  public static final String ENV_VAR_NIB_SQL_PASSWORD = "NIB2JAVA_SQL_PASSWORD";

  /**
   * Environment variable for setting up the default name of disk volumes,
   * the default value is <b>DFTVOL</b><br>
   * <br>
   */
  public static final String ENV_VAR_NIB_DFT_VOLUME = "NIB2JAVA_DFT_VOLUME";
  private static final String _NIB_DFT_VOLUME_DFLT = "DFTVOL";

  /**
   * Environment variable for setting up the memory limit,
   * values must be numeric, the allowed range is 1-100, 
   * the default value is <b>10</b><br>
   * <br>
   */
  public static final String ENV_VAR_NIB_MEM_LIMIT = "NIB2JAVA_MEM_LIMIT";
  public static final int _NIB_DFLT_MEM_LIMIT = 10;

  /**
   * Environment variable for setting up the number of fields allocation slot,
   * values must be numeric, the allowed range is 100-100000, 
   * the default value is <b>10000</b><br>
   * <br>
   */
  public static final String ENV_VAR_NIB_FIELDS_ALLOC_SLOT = "NIB2JAVA_FIELDS_ALLOC_SLOT";
  public static final int _NIB_DFLT_FIELDS_ALLOC_SLOT = 10000;

  public static HashMap<String, NAsmFile> externalFilesInfo = null;
  public static HashMap<String, NAsmDDcard> runtimeFilesInfo = null;
  private static HashMap<String, NAsmFile> wFiles = null;
  private static Vector<NAsmFile> vFiles = null;
  public static HashMap<String, NAsmOnlineProgram> programInstances = null;
  public static HashMap<String, NAsmOnlineProgram> programInstancesCache = null;
  public static INAsmProgramWrapper iProgramWrapper = null;
  public static String sysDateLongFormat = null;
  public static String sysDateFormat = null;
  private static AbstractProgram currentInstance = null;
  private static NAsmOnlineProgram mainInstance = null;
  private static NAsmStandardIO _STDOUT = null;
  private static Boolean debug = null;
  private static Boolean trace = null;
  private static Boolean stats = null;
  private static String filePrefix = null;
  private static String dftVolume = null;
  private static Integer memoryLimit = null;
  private static Integer fieldsAllocSlot = null;
  private static Charset charset = null;
  private static String ebcdicCodepage = null;
  private static Charset ebcdicCharset = null;
  private static Integer file_max_reclen = null;
  private static Boolean allowOutOfRange = null;
  private static Boolean allowNumericMalformed = null;
  private static Boolean printExecutionStatistics = null;
  private static Boolean runEBCDIC = null;
  private static Boolean sysoutLSFormat = null;

  private static String sqlURL = null;
  private static String sqlDriver = null;
  private static String sqlUser = null;
  private static String sqlPassword = null;

  private static String filesBasepath = null;
  private static String sysoutBasepath = null;
  private static String jobName = null;
  private static String jobXmlName = null;
  private static String stepName = null;
  private static String jobProjectName = null;
  private static long jobNumber = 0;
  public static int fieldIdThreshold = 1;
  public static ArrayList<NAsmAddressesCollection> addressedFieldList = null;
  public static int memoryIdThreshold = 1;
  public static int[] addressesMapList = null;
  public static HashMap<Integer, String> addressesMapRecycled = null;
  private static INAsmLogger userLogger = null;
  private static boolean storeAddresses = false;
  private static List<NAsmAddress> storedAddresses = null;

  //Changed to use 7 bits from B0 00 00 00 to BF FF FF FF
  public static final int ADDRESS_MASK = 0xB0;
  public static final int MEMORY_RANGE_MIN = 0xB0000001; //To keep consistent with ADDRESS_MASK
  public static final int MEMORY_RANGE_MAX = 0xBFFFFFFF; //To keep consistent with ADDRESS_MASK
  public static final int MEMORY_MAX_ALLOCATION = 0xBFFFFFFF - 0xB0000000;
  public static int FIELD_ALLOCATION_SLOT;
  public static int MEMORY_ALLOCATION_LIMIT;

  public static NAsmRegister debugRegister;
  public static NAsmRegister wrkRegister;
  private static NAsmField _COND_CODE;
  private static INAsmExtRecordHandler extRecordHandler = null;
  public static NAsmField debugField; // SQ
  private static NAsmField systemSaveArea; // SQ - 4096 bytes area to be used as "random buffer" (in case of
											// non-addressed registers/pointers)
  private static NAsmField systemDummyArea; // SQ - 4096 bytes area to be used as "random buffer" (in case of
											// non-addressed registers/pointers)

  static {
    init();
  }

  //Registers
  public static NAsmRegister[] registers;

  public static void init() {
    NAsmExecutionInfo.init();
    MEMORY_ALLOCATION_LIMIT = MEMORY_MAX_ALLOCATION / 100 * MEMORY_LIMIT();
    FIELD_ALLOCATION_SLOT = FIELDS_ALLOC_SLOT();
    externalFilesInfo = new HashMap<>();
    runtimeFilesInfo = new HashMap<>();
    wFiles = new HashMap<>();
    vFiles = new Vector<>();
    programInstances = new HashMap<>();
    programInstancesCache = new HashMap<>();
    NAsmExecStats.initStep();
    iProgramWrapper = null;
    currentInstance = null;
    mainInstance = null;
    addressedFieldList = new ArrayList<>();
    addressedFieldList.ensureCapacity(FIELD_ALLOCATION_SLOT);
    initializeArray(addressedFieldList, 0, FIELD_ALLOCATION_SLOT);
    addressesMapList = new int[MEMORY_ALLOCATION_LIMIT];
    fieldIdThreshold = 1;
    memoryIdThreshold = 1;
    addressesMapRecycled = new HashMap<>();
    dftVolume = null;
    userLogger = null;
    try {
      debugRegister = new NAsmRegister("DEBUG_REGISTER");
      wrkRegister = new NAsmRegister("WRK_REGISTER");
      _COND_CODE = new NAsmField(NAsmField.SYSTEM_RETURN_CODE, NAsmFieldType.NUMERIC_BINARY, 0, 4);
			debugField = new NAsmField("DEBUG_FIELD", NAsmFieldType.ALPHANUMERIC, 0, 256); //SQ
			systemSaveArea = new NAsmField("SYSTEM_SAVE_AREA", NAsmFieldType.ALPHANUMERIC, 0, 144); //72 for 32-bit; 144 covers also 64-bit
			systemDummyArea = new NAsmField("SYSTEM_DUMMY_AREA", NAsmFieldType.ALPHANUMERIC, 0, 4096);

      registers = new NAsmRegister[] { //
          new NAsmRegister("" + NAsmOnlineProgram.REG_0), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_1), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_2), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_3), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_4), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_5), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_6), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_7), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_8), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_9), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_10), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_11), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_12), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_13), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_14), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_15), //
      };

			//SQ Mainframe behavior:
			//R13 -> SAVE AREA
			registers[NAsmOnlineProgram.REG_13].setAddress(systemSaveArea);
			//Rn -> DUMMY AREA to avoid abends in "dirty programs" with access to non-addressed registers
			for (int i = 2; i < NAsmOnlineProgram.REG_13; i++) {
				registers[i].setAddress(systemDummyArea);
			}
      
    } catch (NAsmException e) {
			System.err.println("[NAsmEnvironment] Problems initializing NAsmEnvironment: " + e.getMessage()); //SQtmp
			//NAsmEnvironment.console(e); //SQ this is wrong: NAsmEnvironment not yet loaded when we fall here!
    }
  }
  
  @SuppressWarnings("unchecked")
  private static void initializeArray(@SuppressWarnings("rawtypes") List array, int startIndex, int slot) {
    for (int idx = startIndex; idx < startIndex + slot; idx++) {
      array.add(idx, null);
    }
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_DEBUG}.
   * 
   * @return the default debug value
   */
  public static boolean DEBUG() {
    if (debug == null) {
      debug = false;
      String v = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_DEBUG);
      if (v != null && (v.toUpperCase(Locale.US).startsWith("Y") //
          || v.toUpperCase(Locale.US).startsWith("O") //
          || v.toUpperCase(Locale.US).startsWith("T"))) {
        debug = true;
      }
    }
    return debug;
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_TRACE}.
   * 
   * @return the default trace value
   */
  public static boolean TRACE() {
    if (trace == null) {
      trace = false;
      String v = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_TRACE);
      if (v != null && (v.toUpperCase(Locale.US).startsWith("Y") //
          || v.toUpperCase(Locale.US).startsWith("O") //
          || v.toUpperCase(Locale.US).startsWith("T"))) {
        trace = true;
      }
    }
    return trace;
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_STATS}.
   * 
   * @return the default stats value
   */
  public static boolean STATS() {
    if (stats == null) {
      stats = false;
      String v = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_STATS);
      if (v != null && (v.toUpperCase(Locale.US).startsWith("Y") //
          || v.toUpperCase(Locale.US).startsWith("O") //
          || v.toUpperCase(Locale.US).startsWith("T"))) {
        stats = true;
      }
    }
    return stats;
  }

  /**
   * Same effect as setting up the environment variable
   * {@link NAsmEnvironment#ENV_VAR_NIB_DEBUG}.
   * 
   * @param value when <b>true</b> enables debug tracing features <br>
   *              <b>false</b> disables debug tracing features
   */
  public static void setDEBUG(boolean value) {
    debug = value;
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_FILE_PREFIX}.
   * 
   * @return the default file prefix value
   * 
   */
  public static String FILE_PREFIX() {
    if (filePrefix == null) {
      try {
        filePrefix = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_FILE_PREFIX);
      } catch (Exception ex) {

      }
      if (filePrefix == null || filePrefix.trim().length() == 0) {
        filePrefix = _NIB_FILE_PREFIX_DFLT;
      }
    }
    return filePrefix;
  }

  /**
   * Same effect as setting up the environment variable
   * {@link NAsmEnvironment#ENV_VAR_NIB_FILE_PREFIX}.
   * 
   * @param value file prefix
   */
  public static void setFILE_PREFIX(String value) {
    NAsmEnvironment.filePrefix = value;
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_CHARSET}.
   * 
   * @return the environment defaulr charset
   */
  public static Charset ENV_CHARSET() {
    if (charset == null) {
      String newCharset = "";
      try {
        newCharset = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_CHARSET);
        charset = Charset.forName(newCharset);
        NAsmEnvironment.console(NAsmMessageType.INFO, "User charset " + charset + " loaded.");
      } catch (Exception ex) {
        if (newCharset != null && newCharset.trim().length() > 0) {
          NAsmEnvironment.console(NAsmMessageType.SEVERE, "Unexpected error while loading charset: '" + newCharset + "'. Default charset " + _NIB_ENV_DFT_CHARSET + " will be used.");
        }
      }
      if (charset == null) {
        charset = Charset.forName(_NIB_ENV_DFT_CHARSET);
        NAsmEnvironment.console(NAsmMessageType.INFO, "Default charset " + _NIB_ENV_DFT_CHARSET + " loaded.");
      }
    }
    return charset;
  }

  /**
   * Same effect as setting up the environment variable
   * {@link NAsmEnvironment#ENV_VAR_NIB_CHARSET}.
   * 
   * @param value a supported Java charset
   */
  public static void setENV_CHARSET(String value) {
    NAsmEnvironment.charset = Charset.forName(value);
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_EBCDIC_CODEPAGE}.
   * 
   * @return the EBCDIC codepage
   */
  public static String EBCDIC_CODEPAGE() {
    if (ebcdicCodepage == null) {
      String newCodepage = "";
      try {
        newCodepage = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_EBCDIC_CODEPAGE);
        ebcdicCodepage = newCodepage;
      } catch (Exception ex) {
        NAsmEnvironment.console(NAsmMessageType.SEVERE, "Unexpected error while loading ebcdic codepage: '" + newCodepage + "'. Default codepage " + _NIB_EBCDIC_DFT_CODEPAGE + " will be used.");

      }
      if (ebcdicCodepage == null) {
        ebcdicCodepage = _NIB_EBCDIC_DFT_CODEPAGE;
      }
    }
    return ebcdicCodepage;
  }

  public static Charset EBCDIC_CHARSET() {
    if (ebcdicCharset == null) {
      ebcdicCharset = Charset.forName(NAsmEnvironment.EBCDIC_CODEPAGE());
    }
    return ebcdicCharset;
  }

  /**
   * Same effect as setting up the environment variable
   * {@link NAsmEnvironment#ENV_VAR_NIB_EBCDIC_CODEPAGE}.
   * 
   * @param value the EBCDIC codepage
   */
  public static void setEBCDIC_CODEPAGE(String value) {
    NAsmEnvironment.ebcdicCodepage = value;
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_FILE_MAX_RECLEN}.
   * 
   * @return the default maximum record size
   */
  public static int FILE_MAX_RECLEN() {
    if (file_max_reclen == null) {
      String newMaxLen = null;
      try {
        newMaxLen = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_FILE_MAX_RECLEN);
      } catch (Exception ex) {

      }
      if (newMaxLen == null || newMaxLen.trim().length() == 0) {
        // Default
        file_max_reclen = _NIB_FILE_MAX_RECLEN;
      } else {
        try {
          file_max_reclen = Integer.parseInt(newMaxLen);
        } catch (Exception ex) {

        }
        if (file_max_reclen == null || file_max_reclen <= 0) {
          file_max_reclen = _NIB_FILE_MAX_RECLEN;
          NAsmEnvironment.console(NAsmMessageType.SEVERE,
              "Invalid value '" + newMaxLen + "' for environment variable '" + ENV_VAR_NIB_FILE_MAX_RECLEN + "'. Default value " + _NIB_FILE_MAX_RECLEN + " will be used.");
        }
      }
    }
    return file_max_reclen;
  }

  /**
   * Same effect as setting up the environment variable
   * {@link NAsmEnvironment#ENV_VAR_NIB_FILE_MAX_RECLEN}.
   * 
   * @param value the default maximum record size
   */
  public static void setFILE_MAX_RECLEN(Integer value) {
    NAsmEnvironment.file_max_reclen = value;
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_ALLOW_OUT_OF_RANGE}.
   * 
   * @return out of range behavior variable value
   */
  public static boolean ALLOW_OUT_OF_RANGE() {
    if (allowOutOfRange == null) {
      allowOutOfRange = false;
      try {
        String val = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_ALLOW_OUT_OF_RANGE);
        if (val != null) {
          allowOutOfRange = true;
        }
      } catch (Exception ex) {

      }
    }
    return allowOutOfRange;
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_ALLOW_MALFORMED_NUMERIC_VALUES}.
   * 
   * @return malformed numeric values behavior variable value
   */
  public static boolean ALLOW_NUMERIC_MALFORMED() {
    if (allowNumericMalformed == null) {
      allowNumericMalformed = false;
      try {
        String val = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_ALLOW_MALFORMED_NUMERIC_VALUES);
        if (val != null) {
          allowNumericMalformed = true;
        }
      } catch (Exception ex) {

      }
    }
    return allowNumericMalformed;
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_PRINT_RUN_STATISTICS}.
   * 
   * @return print job statistics behavior variable value
   */
  public static boolean PRINT_RUN_STATISTICS() {
    if (printExecutionStatistics == null) {
      printExecutionStatistics = Boolean.FALSE;
      try {
        String val = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_PRINT_RUN_STATISTICS);
        if (val != null) {
          printExecutionStatistics = Boolean.TRUE;
        }
      } catch (Exception ex) {

      }
    }
    return printExecutionStatistics;
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_RUN_EBCDIC}.
   * 
   * @return print job statistics behavior variable value
   */
  public static boolean RUN_EBCDIC() {
    if (runEBCDIC == null) {
      runEBCDIC = Boolean.FALSE;
      try {
        String val = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_RUN_EBCDIC);
        if (val != null) {
          runEBCDIC = Boolean.TRUE;
        }
      } catch (Exception ex) {

      }
    }
    return runEBCDIC;
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_WRITE_LS_SYSOUT}.
   * 
   * @return print SYSOUT in readable format
   */
  public static boolean WRITE_LS_SYSOUT() {
    if (sysoutLSFormat == null) {
      sysoutLSFormat = Boolean.FALSE;
      try {
        String val = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_WRITE_LS_SYSOUT);
        if (val != null) {
          sysoutLSFormat = Boolean.TRUE;
        }
      } catch (Exception ex) {

      }
    }
    return sysoutLSFormat;
  }

  /**
   * Same effect as setting up the environment variable
   * {@link NAsmEnvironment#ENV_VAR_NIB_ALLOW_OUT_OF_RANGE}.
   * 
   * @param value when <b>true</b> emit a warning message in case an array out of
   *              range error occurs <br>
   *              <b>false</b> generates a JAVA exception in case an array out of
   *              range error occurs
   */
  public static void setAllowOutOfRange(boolean value) {
    NAsmEnvironment.allowOutOfRange = value;
  }

  /**
   * Same effect as setting up the environment variable
   * {@link NAsmEnvironment#ENV_VAR_NIB_ALLOW_MALFORMED_NUMERIC_VALUES}.
   * 
   * @param value when <b>true</b> emit a warning message in case an array out of
   *              range error occurs <br>
   *              <b>false</b> generates a JAVA exception in case an array out of
   *              range error occurs
   */
  public static void setAllowMalformedNumeric(boolean value) {
    NAsmEnvironment.allowNumericMalformed = value;
  }

  /**
   * Same effect as setting up the environment variable
   * {@link NAsmEnvironment#ENV_VAR_NIB_PRINT_RUN_STATISTICS}.
   * 
   * @param value when <b>true</b> prints file statistics at the end of a JOB
   *              execution <br>
   *              <b>false</b> doesn't print file statistics at the end of a JOB
   *              execution
   */
  public static void setPrintJobStatistics(boolean value) {
    NAsmEnvironment.printExecutionStatistics = value;
  }

  /**
   * Same effect as setting up the environment variable
   * {@link NAsmEnvironment#ENV_VAR_NIB_RUN_EBCDIC}.
   * 
   * @param value when <b>true</b> read/write data in EBCDIC <br>
   *              <b>false</b> doesn't read/write data in EBCDIC <br>
   */
  public static void setRunEBCDIC(boolean value) {
    NAsmEnvironment.runEBCDIC = value;
  }

  /**
   * Same effect as setting up the environment variable
   * {@link NAsmEnvironment#ENV_VAR_NIB_WRITE_LS_SYSOUT}.
   * 
   * @param value when <b>true</b> write SYSAOUT in a readble format<br>
   *              <b>false</b> default environment settings <br>
   */
  public static void setSysoutLSFormat(boolean value) {
    NAsmEnvironment.sysoutLSFormat = value;
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_SQL_URL}.
   * 
   * @return the SQL url value
   */
  public static String SQL_URL() {
    if (sqlURL == null) {
      try {
        sqlURL = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_SQL_URL);
      } catch (Exception ex) {
        sqlURL = null;
      }
      if (sqlURL == null || sqlURL.trim().length() == 0) {
        sqlURL = "";
      }
    }
    return sqlURL;
  }

  /**
   * Same effect as setting up the environment variable
   * {@link NAsmEnvironment#ENV_VAR_NIB_SQL_URL}.
   * 
   * @param value a valid SQL url value
   */
  public static void setSQL_URL(String value) {
    NAsmEnvironment.sqlURL = value;
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_SQL_DRIVER}.
   * 
   * @return the SQL driver value
   */
  public static String SQL_DRIVER() {
    if (sqlDriver == null) {
      try {
        sqlDriver = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_SQL_DRIVER);
      } catch (Exception ex) {
        sqlDriver = null;
      }
      if (sqlDriver == null || sqlDriver.trim().length() == 0) {
        sqlDriver = "";
      }
    }
    return sqlDriver;
  }

  /**
   * Same effect as setting up the environment variable
   * {@link NAsmEnvironment#ENV_VAR_NIB_SQL_DRIVER}.
   * 
   * @param value a valid SQL driver value
   */
  public static void setSQL_DRIVER(String value) {
    NAsmEnvironment.sqlDriver = value;
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_SQL_USER}.
   * 
   * @return the SQL user value
   */
  public static String SQL_USER() {
    if (sqlUser == null) {
      try {
        sqlUser = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_SQL_USER);
      } catch (Exception ex) {
        sqlUser = null;
      }
      if (sqlUser == null || sqlUser.trim().length() == 0) {
        sqlUser = "";
      }
    }
    return sqlUser;
  }

  /**
   * Same effect as setting up the environment variable
   * {@link NAsmEnvironment#ENV_VAR_NIB_SQL_USER}.
   * 
   * @param value a valid SQL user value
   */
  public static void setSQL_USER(String value) {
    NAsmEnvironment.sqlUser = value;
  }

  /**
   * see
   * {@link NAsmEnvironment#ENV_VAR_NIB_SQL_PASSWORD}.
   * 
   * @return the SQL user value
   */
  public static String SQL_PASSWORD() {
    if (sqlPassword == null) {
      try {
        sqlPassword = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_SQL_PASSWORD);
      } catch (Exception ex) {
        sqlPassword = null;
      }
    }
    return sqlPassword;
  }

  /**
   * Same effect as setting up the environment variable
   * {@link NAsmEnvironment#ENV_VAR_NIB_SQL_PASSWORD}.
   * 
   * @param value a valid SQL password value
   */
  public static void setSQL_PASSWORD(String value) {
    NAsmEnvironment.sqlPassword = value;
  }

  /**
   * Get a sequence of New Line characters.
   * 
   * @param howMany number of New Line
   * @return a sequence of New Line characters
   */
  public static String NL(int howMany) {
    String rc = "";
    for (int idx = 0; idx < howMany; idx++) {
      rc += NL();
    }
    return rc;
  }

  /**
   * @return a New Line character platform dependent
   * 
   */
  public static String NL() {
    return System.lineSeparator();
  }

  /**
   * @return a New Line character in DOS format
   * 
   */
  public static String NL_DOS() {
    return "\r\n";
  }

  /**
   * @return a New Line character in Unix format
   * 
   */
  public static String NL_UNIX() {
    return "\n";
  }

  /**
   * @return Environment decimal sign
   * 
   */
  private static String DECIMAL_SIGN_SEPARATOR;

  /**
   * 
   */
  private static int debugInt;

  /**
   * 
   */
  private static int debugString;

  public static String DECIMAL_SIGN() {
    if (DECIMAL_SIGN_SEPARATOR == null) {
      DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
      DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
      char sep = symbols.getDecimalSeparator();
      DECIMAL_SIGN_SEPARATOR = Character.toString(sep);
    }

    return DECIMAL_SIGN_SEPARATOR;
  }

  /**
   * Get the PAGE country string.
   * 
   * @return the PAGE string for a specific country.
   */
  public static String getPAGE_STRING() {
    String page = "PAGE";
    switch (Locale.getDefault().getLanguage()) {
    case "sv":
      page = "SIDA";
      break;
    case "it":
      page = "PAG.";
      break;
    }
    return page;
  }

  /**
   * Read an environment variable.
   * 
   * @param varName variable to be read
   * @return an environment variable value
   */
  public static String getEnvVar(String varName) {
    return System.getenv(varName);
  }

  /**
   * Set an environment variable up.
   * 
   * @param varName  variable to be set
   * @param varValue value to be set
   * @throws Exception if a problem occurs.
   */
  public static void setEnvVar(String varName, Object varValue) throws Exception {
    if (varValue instanceof Integer) {
      setEnvVar(varName, ((Integer) varValue).toString());
    } else if (varValue instanceof String) {
      setEnvVar(varName, (String) varValue);
    } else {
      throw new Exception("Environment variable '" + varName + "' cannot be setup.");
    }
  }

  /**
   * Set an environment variable up.
   * 
   * @param       <K> variable name to be set
   * @param key   variable name to be set
   * @param       <V> variable value to be set
   * @param value variable value to be set
   */
  @SuppressWarnings({ "unchecked", "deprecation" })
  public static <K, V> void setEnvVar(final String key, final String value) {
    try {
      // / we obtain the actual environment
      final Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
      final Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
      final boolean environmentAccessibility = theEnvironmentField.isAccessible();
      theEnvironmentField.setAccessible(true);

      final Map<K, V> env = (Map<K, V>) theEnvironmentField.get(null);

      // if (SystemUtils.IS_OS_WINDOWS) {
      if (File.separator.equals("\\")) {
        // This is all that is needed on windows running java jdk
        // 1.8.0_92
        if (value == null) {
          env.remove(key);
        } else {
          env.put((K) key, (V) value);
        }
      } else {
        // This is triggered to work on openjdk 1.8.0_91
        // The ProcessEnvironment$NAsmVariable is the key of the map
        final Class<K> variableClass = (Class<K>) Class.forName("java.lang.ProcessEnvironment$NAsmVariable");
        final Method convertToVariable = variableClass.getMethod("valueOf", String.class);
        final boolean conversionVariableAccessibility = convertToVariable.isAccessible();
        convertToVariable.setAccessible(true);

        // The ProcessEnvironment$Value is the value fo the map
        final Class<V> valueClass = (Class<V>) Class.forName("java.lang.ProcessEnvironment$Value");
        final Method convertToValue = valueClass.getMethod("valueOf", String.class);
        final boolean conversionValueAccessibility = convertToValue.isAccessible();
        convertToValue.setAccessible(true);

        if (value == null) {
          env.remove(convertToVariable.invoke(null, key));
        } else {
          // we place the new value inside the map after conversion so
          // as to
          // avoid class cast exceptions when rerunning this code
          env.put((K) convertToVariable.invoke(null, key), (V) convertToValue.invoke(null, value));

          // reset accessibility to what they were
          convertToValue.setAccessible(conversionValueAccessibility);
          convertToVariable.setAccessible(conversionVariableAccessibility);
        }
      }
      // reset environment accessibility
      theEnvironmentField.setAccessible(environmentAccessibility);

      // we apply the same to the case insensitive environment
      final Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
      final boolean insensitiveAccessibility = theCaseInsensitiveEnvironmentField.isAccessible();
      theCaseInsensitiveEnvironmentField.setAccessible(true);
      // Not entirely sure if this needs to be casted to
      // ProcessEnvironment$NAsmVariable and $Value as well
      final Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
      if (value == null) {
        // remove if null
        cienv.remove(key);
      } else {
        cienv.put(key, value);
      }
      theCaseInsensitiveEnvironmentField.setAccessible(insensitiveAccessibility);
    } catch (final ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new IllegalStateException("Failed setting environment variable <" + key + "> to <" + value + ">", e);
    } catch (final NoSuchFieldException e) {
      // we could not find theEnvironment
      final Map<String, String> env = System.getenv();
      Stream.of(Collections.class.getDeclaredClasses())
          // obtain the declared classes of type $UnmodifiableMap
          .filter(c1 -> "java.util.Collections$UnmodifiableMap".equals(c1.getName())).map(c1 -> {
            try {
              return c1.getDeclaredField("m");
            } catch (final NoSuchFieldException e1) {
              throw new IllegalStateException("Failed setting environment variable <" + key + "> to <" + value + "> when locating in-class memory map of environment", e1);
            }
          }).forEach(field -> {
            try {
              final boolean fieldAccessibility = field.isAccessible();
              field.setAccessible(true);
              // we obtain the environment
              final Map<String, String> map = (Map<String, String>) field.get(env);
              if (value == null) {
                // remove if null
                map.remove(key);
              } else {
                map.put(key, value);
              }
              // reset accessibility
              field.setAccessible(fieldAccessibility);
            } catch (final ConcurrentModificationException e1) {
              // This may happen if we keep backups of the
              // environment before calling this method
              // as the map that we kept as a backup may
              // be picked up inside this block.
              // So we simply skip this attempt and
              // continue adjusting the other maps
              // To avoid this one should always keep
              // individual keys/value backups not the
              // entire map
              // LOGGER.info("Attempted to modify source map:
              // "+field.getDeclaringClass()+"#"+field.getName(),
              // e1);
            } catch (final IllegalAccessException e1) {
              throw new IllegalStateException("Failed setting environment variable <" + key + "> to <" + value + ">. Unable to access field!", e1);
            }
          });
    }
  }

  /**
   * Get the system temporary directory.
   * 
   * @return the system temporary directory
   */
  public static String getTempDir() {
    String property = "java.storage.tmpdir";
    String tempDir = System.getProperty(property);
    if (tempDir.endsWith(File.separator)) {
      tempDir = tempDir.substring(0, tempDir.length() - 1);
    }
    return tempDir;
  }

  /**
   * Get the system home directory.
   * 
   * @return the system home directory
   */
  public static String getHomeDir() {
    String property = "user.home";
    String homeDir = System.getProperty(property);
    if (homeDir.endsWith(File.separator)) {
      homeDir = homeDir.substring(0, homeDir.length() - 1);
    }
    return homeDir;
  }

  /**
   * Get the system date with yyyy/MM/dd format.
   * 
   * @return the system date with yyyy/MM/dd format.
   */
  public static String getSYSDATE_LONG() {
    Date date = new Date();
    DateFormat df;
    if (sysDateLongFormat == null) {
      df = new SimpleDateFormat("yyyy/MM/dd");
    } else {
      df = new SimpleDateFormat(sysDateLongFormat);
    }
    String s = df.format(date);
    return s;
  }

  /**
   * Get the system date with yy/MM/dd format.
   * 
   * @return the system date with yy/MM/dd format.
   */
  public static String getSYSDATE() {
    Date date = new Date();
    DateFormat df;
    if (sysDateFormat == null) {
      df = new SimpleDateFormat("yy/MM/dd");
    } else {
      df = new SimpleDateFormat(sysDateFormat);
    }
    String s = df.format(date);
    return s;
  }

  /**
   * Get the system time with HH:mm:ss format.
   * 
   * @return the system time with HH:mm:ss format.
   */
  public static String getSYSTIME() {
    Date date = new Date();
    DateFormat df = new SimpleDateFormat("HH:mm:ss");
    String s = df.format(date);
    return s;
  }

  /**
   * Print a message to the defined console.
   * 
   * @param type    message type
   * @param message content
   */
  public static void console(NAsmMessageType type, String message) {
    String outMessage;
    if (type.equals(NAsmMessageType.SEVERE)) {
      String ref = "";
      if (message.contains("->")) {
        ref = "[" + message.substring(0, message.indexOf("->")).trim() + "]";
        message = message.substring(message.indexOf("->") + 2).trim();
      }
      outMessage = ref + " " + message;
      if (_STDOUT == null) {
        logger.error(outMessage);
      } else {
        _STDOUT.flush();
        _STDOUT.displaySTDOUT(type, outMessage);
        _STDOUT.flush();
      }
    } else if (type.equals(NAsmMessageType.WARNING)) {
      String ref = "";
      if (message.contains("->")) {
        ref = "[" + message.substring(0, message.indexOf("->")).trim() + "]";
        message = message.substring(message.indexOf("->") + 2).trim();
      }
      outMessage = ref + " " + message;
      if (_STDOUT == null) {
        logger.warn(outMessage);
      } else {
        _STDOUT.flush();
        _STDOUT.displaySTDOUT(type, outMessage);
        _STDOUT.flush();
      }
    } else {
      outMessage = message;
      if (_STDOUT == null) {
        logger.info(outMessage);
      } else {
        _STDOUT.flush();
        _STDOUT.displaySTDOUT(type, outMessage);
        _STDOUT.flush();
      }
    }

  }

  /**
   * Print a debug message to the defined console.
   * 
   * @param message content
   */
  public static void debug(String message) {
    if (NAsmEnvironment.DEBUG()) {
      _STDOUT.displaySTDOUT(NAsmMessageType.TRACE, NAsmRuntime.getPluginName() + "[DBG]> " + message);
      _STDOUT.flush();
    }
  }

  /**
   * Print an exception to the defined console.
   * 
   * @param e exception to be printed
   */
  public static void console(Exception e) {
    _STDOUT.displayEXCEPTION(e);
    _STDOUT.flush();
  }

  /**
   * Override the standard out.
   * 
   * @param stdout instance of standard output to be used
   */
  public static void setSTDIO(NAsmStandardIO stdout) {
    _STDOUT = stdout;
  }

  /**
   * Printer is setup to ASA
   * 
   * @return ASA printer
   */
  public static boolean isPrinterASA() {
    return true;
  }

  public static String getParm(NAsmField field) throws NAsmException {
    String parm = getParm(field.getString().trim());
    if (parm == null || parm.length() == 0) {
      NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(0);
    } else {
      NAsmEnvironment.registers[NAsmOnlineProgram.REG_15].set(parm.length());
    }
    return parm;
  }

  public static String getParm(String parmName) throws NAsmException {
	return iProgramWrapper.getParm(parmName);
  }

  public static void saveRegisters(int startReg, int endReg, NAsmField area, AbstractProgram program) throws NAsmException {
    saveRegisters(startReg, endReg, area, 0, program);
  }

  public static void saveRegisters(int startReg, int endReg, NAsmField area, int areaOffset) throws NAsmException {
    saveRegisters(startReg, endReg, area, areaOffset, NAsmEnvironment.getCurrentInstance());
  }

  private static void saveRegisters(int startReg, int endReg, NAsmField area, int areaOffset, AbstractProgram program) throws NAsmException {
    if (area instanceof NAsmRegister) {
      if (program instanceof NAsmBatchProgram) {
        area = ((NAsmBatchProgram) program).getLocalRegisterArea();
      } else {
        area = ((NAsmOnlineProgram) program).getLocalRegisterArea();
      }
      if (area.getSavedRegisters(program.getProgramId()) != null) {
        //throw new NAsmException("Nested \"saveRegistersPosition\" to fix in program '" + program.getName() + "'"); //SQtmp debug catcher
        //logger.debug("");//SQtmp debug catcher
      }
    } else {
      if (!area.getName().toUpperCase(Locale.US).contains("13F") && !program.getProgramId().contentEquals("LBQE") && !program.getProgramId().contentEquals("LFNM") && !program.getProgramId().contentEquals("LFAD")
          && !area.getName().contentEquals("SAVE_REGISTERS") && !area.getName().toUpperCase(Locale.US).contains("SAV") && !area.getName().toUpperCase(Locale.US).contains("REG")) {
        //logger.debug(""); //SQtmp debug catcher
      }
    }

    NAsmRegister[] savedRegisters = new NAsmRegister[] { //
        new NAsmRegister("" + NAsmOnlineProgram.REG_0), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_1), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_2), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_3), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_4), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_5), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_6), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_7), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_8), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_9), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_10), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_11), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_12), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_13), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_14), //
        new NAsmRegister("" + NAsmOnlineProgram.REG_15), //
    };
    if (endReg < startReg) {
      //for (int idx = endReg; idx <= NAsmOnlineProgram.REG_15; idx++) { //SQ from start to R15 ; from R0 to end
      for (int idx = startReg; idx <= NAsmOnlineProgram.REG_15; idx++) { //SQ from start to R15 ; from R0 to end
        savedRegisters[idx] = registers[idx].clone();
      }
      //SQ endReg = startReg;
      startReg = 0;
    }
    for (int idx = startReg; idx <= endReg; idx++) {
      savedRegisters[idx] = registers[idx].clone();
    }
    area.setSavedRegisters(program.getProgramId(), savedRegisters);
  }

  public static void multipleStore(int startReg, int endReg, NAsmField area) throws NAsmException {
    multipleStore(startReg, endReg, area, 0);
  }
  
  public static void multipleStore(int startReg, int endReg, NAsmField area, int areaOffset) throws NAsmException {
    int pos = 0;
    if (endReg < startReg) {
      pos = executeMultipleStore(startReg, NAsmOnlineProgram.REG_15, area, areaOffset, pos);
      startReg = 0;
    }
    executeMultipleStore(startReg, endReg, area, areaOffset, pos);
  }
  
  private static int executeMultipleStore(int startReg, int endReg, NAsmField area, int areaOffset, int pos) throws NAsmException {
    for (int idx = startReg; idx <= endReg; idx++) {
      int offset = areaOffset + pos * 4;
//      byte[] addr = area.getByteArray(offset, 4);
//      Integer memoryId = getAddressMemoryId(addr);
//      if (memoryId != null && memoryId == 213) {
//        logger.debug("");
//      }
//      if (isValidAddressIndex(memoryId)) {
//        area.storeRegister(offset, idx);
//      }
      area.set(registers[idx], offset, true);
      pos++;
    }
    return pos;
  }

  //SQtmp - this kind of method could be useful - TODO the below implementation cannot be used as is because of the offset ... 
  public static void setRegistersPosition(int startReg, int endReg, NAsmField area, int areaOffset) throws NAsmException {
    AbstractProgram program = NAsmEnvironment.getCurrentInstance();
    if (area instanceof NAsmRegister) {
      if (NAsmEnvironment.getCurrentInstance() instanceof NAsmBatchProgram) {
        area = ((NAsmBatchProgram) NAsmEnvironment.getCurrentInstance()).getLocalRegisterArea();
      } else {
        area = ((NAsmOnlineProgram) NAsmEnvironment.getCurrentInstance()).getLocalRegisterArea();
      }

    }
    NAsmRegister[] savedRegisters = area.getSavedRegisters(program.getProgramId());
    //SQ check to review
    if (savedRegisters == null) {
			logger.debug("[" + program.getProgramId() + "] No registers saved in '" + area.getName() + "'");
      savedRegisters = new NAsmRegister[] { //
          new NAsmRegister("" + NAsmOnlineProgram.REG_0), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_1), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_2), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_3), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_4), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_5), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_6), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_7), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_8), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_9), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_10), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_11), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_12), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_13), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_14), //
          new NAsmRegister("" + NAsmOnlineProgram.REG_15), //
      };
    }

    if (endReg < startReg) {
      for (int idx = startReg; idx <= NAsmOnlineProgram.REG_15; idx++) { //SQ from start to R15 ; from R0 to end
        savedRegisters[idx] = registers[idx].clone();
      }
      startReg = 0;
    }
    for (int idx = startReg; idx <= endReg; idx++) {
      savedRegisters[idx] = registers[idx].clone();
    }
    area.setSavedRegisters(program.getProgramId(), savedRegisters);
  }

  public static void restoreRegistersPosition(int startReg, int endReg, NAsmField area, int areaOffset) throws NAsmException {
    restoreRegistersPosition(startReg, endReg, area, areaOffset, NAsmEnvironment.getCurrentInstance());
  }

  private static void restoreRegistersPosition(int startReg, int endReg, NAsmField area, int areaOffset, AbstractProgram program) throws NAsmException {
    if (area instanceof NAsmRegister) {
      if (program instanceof NAsmBatchProgram) {
        area = ((NAsmBatchProgram) program).getLocalRegisterArea();
      } else {
        area = ((NAsmOnlineProgram) program).getLocalRegisterArea();
      }
    }
    NAsmRegister[] savedRegisters = area.getSavedRegisters(program.getProgramId());
    if (savedRegisters == null) {
      //SQ - it can be a "simple" multiple load:
      if (NAsmEnvironment.DEBUG()) {
				logger.debug("[" + program.getProgramId() + "] No registers saved in '" + area.getName() + "'");
      }
      multipleLoad(startReg, endReg, area, areaOffset);
      return;
    }
    area.removeSavedRegisters(program.getProgramId());
    HashMap<Integer, NAsmRegister> savedRegistersMap = new HashMap<>();
    for (int idx = 0; idx < savedRegisters.length; idx++) {
      savedRegistersMap.put(idx, savedRegisters[idx]);
    }

    if (endReg < startReg) {
      //for (int idx = endReg; idx <= NAsmOnlineProgram.REG_15; idx++) { //SQ from start to R15 ; from R0 to end
      for (int idx = startReg; idx <= NAsmOnlineProgram.REG_15; idx++) {
        if (savedRegisters != null && savedRegisters[idx] != null) {
          savedRegistersMap.remove(idx);
          byte[] value = registers[idx].getValue(0, 4);
          if (NAsmEnvironment.isValidAddress(value)) {
            NAsmEnvironment.dismissAddress(value);
          }
          registers[idx] = savedRegisters[idx];
          //Moved also to the run() exit to inherit the right program instance 
          NAsmField f = registers[idx].getUnsingRegisteredField();
          if (f != null) {
            f.setUsing(registers[idx], false);
          }
        }
      }
      startReg = 0;
    }
    for (int idx = startReg; idx <= endReg; idx++) {
      if (savedRegisters != null && savedRegisters[idx] != null) {
        savedRegistersMap.remove(idx);
        byte[] value = registers[idx].getValue(0, 4);
        if (NAsmEnvironment.isValidAddress(value)) {
          NAsmEnvironment.dismissAddress(value);
        }
        registers[idx] = savedRegisters[idx];
        //Moved also to the run() exit to inherit the right program instance 
        NAsmField f = registers[idx].getUnsingRegisteredField();
        if (f != null) {
          f.setUsing(registers[idx], false);
        }
      }
    }
    //Registers cloned but not used
    Iterator<Integer> it = savedRegistersMap.keySet().iterator();
    while (it.hasNext()) {
      int idx = it.next();
      byte[] value = savedRegisters[idx].getValue(0, 4);
      if (NAsmEnvironment.isValidAddress(value)) {
        NAsmEnvironment.dismissAddress(value);
      }
    }
  }

  /**
   * multipleLoad - "simple LM"
   * 
   */
  public static void multipleLoad(int startReg, int endReg, NAsmField area) throws NAsmException {
    multipleLoad(startReg, endReg, area, 0);
  }

  /**
   * multipleLoad - "simple LM"
   * 
   */
  public static void multipleLoad(int startReg, int endReg, NAsmField area, int areaOffset) throws NAsmException {
    int pos = 0;
    if (endReg < startReg) {
      pos = executeMultipleLoad(startReg, NAsmOnlineProgram.REG_15, area, areaOffset, pos);
      startReg = 0;
    }
    executeMultipleLoad(startReg, endReg, area, areaOffset, pos);
  }

  private static int executeMultipleLoad(int startReg, int endReg, NAsmField area, int areaOffset, int pos) throws NAsmException {
    for (int idx = startReg; idx <= endReg; idx++, pos++) {
      int offset = areaOffset + pos * 4;
      byte[] addr = area.getByteArray(offset, 4);
      Integer memoryId = getAddressMemoryId(addr);
      if (isValidAddressIndex(memoryId)) {
        //        if (area.isStoreRegister(offset, idx)) {
        //          registers[idx].setByteArray(addr, 0, true, 4);
        //          area.removeStoredRegister(offset);
        //        }else {
        registers[idx].load(area, offset);
        //        }
      }else {
        registers[idx].setByteArray(addr, 0, true, 4);
      }
      //TODO double-check the below: 
      //      NAsmField f = registers[idx].getUnsingRegisteredField();
      //      if (f != null) {
      //        f.setUsing(registers[idx], false);
      //      }
    }
    return pos;
  }
  
  /**
   * 
   * @param value
   * @return
   * @throws NAsmException
   */
  public static boolean isValidAddress(byte[] value) throws NAsmException {
    Integer memoryId = getAddressMemoryId(value);
    if (memoryId != null) {
        if (memoryId < 1 || memoryId > addressesMapList.length) {
          return false;
        }
        //There is a valid field registered
        return addressesMapList[memoryId] > 0;
    }
    return false;
  }

  /**
   * 
   * @param value
   * @return
   * @throws NAsmException
   */
  public static boolean isValidAddressIndex(Integer memoryId) throws NAsmException {
    if (memoryId != null) {
        if (memoryId < 1 || memoryId > addressesMapList.length) {
          return false;
        }
        //There is a valid field registered
        return addressesMapList[memoryId] > 0;
    }
    return false;
  }

  public static Integer getAddressMemoryId(byte[] value) throws NAsmException {
    if (value != null && value.length == 4 && isValidAddressKey(value[0])) {
        return NAsmStrings.binaryBigEndianToValue(new byte[] { (byte) (value[0] & 0x0F), value[1], value[2], value[3] }, 4);
    }
    return null;
  }
  
  /**
   * 
   * @param value
   * @return
   */
  private static boolean isValidAddressKey(byte value) {
    return (value & 0xF0) == ADDRESS_MASK;
  }

  /**
   * 
   * @param pointedAddress
   * @return
   * @throws NAsmException
   */
  public static synchronized byte[] registerAddress(NAsmAddress pointedAddress) throws NAsmException {
    byte[] addrKeyBytes = null;
    if (pointedAddress.hasValidMemoryId()) {
      addrKeyBytes = pointedAddress.getAddrKeyBytes();
      if (addrKeyBytes != null) {
        return addrKeyBytes;
      }
    }
    if (addressesMapRecycled.size() > 0) {
      Integer recycledKey = addressesMapRecycled.keySet().iterator().next();
      pointedAddress.setMemoryId(recycledKey);
      addressesMapRecycled.remove(recycledKey);
    } else {
      pointedAddress.setMemoryId(getNextMemoryId());
    }
    int fieldId = pointedAddress.getAddressedField().getId();
    NAsmAddressesCollection collection = addressedFieldList.get(fieldId);
    collection.addAddress(pointedAddress);
    if (fieldId < 1 || fieldId >= addressedFieldList.size()) {
      throw new NAsmException("Memory handler fault, invalid field ID");
    }
    addressesMapList[pointedAddress.getMemoryId()] = fieldId;
    return pointedAddress.getAddrKeyBytes();
  }

  /**
   * @return the addressesMapRecycled
   */
  public static HashMap<Integer, String> getAddressesMapRecycled() {
    return addressesMapRecycled;
  }

  public static synchronized void dismissAddress(byte[] value) throws NAsmException {
    String addrKey = new String(value, NAsmEnvironment.ENV_CHARSET());
    dismissAddress(addrKey, value);
  }
  
  static int pos = 0; 
  public static synchronized void dismissAddress(String addrKey, byte[] value) throws NAsmException {
    Integer intValue = NAsmStrings.binaryBigEndianToValue(new byte[] { (byte) (value[0] & 0x0F), value[1], value[2], value[3] }, 4);
    //    if (intValue == 0x02a2) { //DEBUG
    //      logger.debug("> " + ++pos); //DEBUG
    //    } //DEBUG
    int fieldId = addressesMapList[intValue];
    addressesMapList[intValue] = 0;
    NAsmAddressesCollection collection = addressedFieldList.get(fieldId);
    collection.removeAddress(intValue);
    if (!addressesMapRecycled.containsKey(intValue)) {
      addressesMapRecycled.put(intValue, null);
    }
  }

  public static synchronized NAsmAddress getAddress(byte[] value) throws NAsmException {
    Integer memoryId = getAddressMemoryId(value);
    if (memoryId == null || memoryId <= 0 || memoryId >= addressesMapList.length) {
      return null;
    }
    int fieldId = addressesMapList[memoryId];
    //Recycled
    if (fieldId == 0) {
      return null;
    }
    NAsmAddressesCollection collection = addressedFieldList.get(fieldId);
    return collection.getAddress(memoryId);
  }

  /**
   * @return the programInstance
   */
  public static AbstractProgram getCurrentInstance() {
    return currentInstance;
  }

  /**
   * @param programInstance the programInstance to set
   */
  public static void setCurrentInstance(AbstractProgram programInstance) {
    NAsmEnvironment.currentInstance = programInstance;
  }

  /**
   * @return the mainInstance
   */
  public static NAsmOnlineProgram getMainInstance() {
    return mainInstance;
  }

  /**
   * @param mainInstance the mainInstance to set
   */
  public static void setMainInstance(NAsmOnlineProgram mainInstance) {
    NAsmEnvironment.mainInstance = mainInstance;
  }

  /**
   * @deprecated  replaced by {@link #loadProgram(Class program)}
   */
  @Deprecated
  public static NAsmOnlineProgram getInstance(@SuppressWarnings("rawtypes") Class program) throws Exception {
    return loadProgram(program);
  }

  public static NAsmOnlineProgram loadProgramShared(@SuppressWarnings("rawtypes") Class program) throws Exception {
    return loadProgram(program, "SHARED");
  }

  /**
   * Load an instance of a program, updates register zero
   * 
   * @param program
   * @return
   * @throws NAsmException
   */
  public static NAsmOnlineProgram loadProgram(@SuppressWarnings("rawtypes") Class programName) throws Exception {
    return loadProgram(programName, getCallerClassName());
  }

  private static NAsmOnlineProgram loadProgram(@SuppressWarnings("rawtypes") Class program, String callerClass) throws Exception {
    NAsmEnvironment.statsStartMethod(NAsmEnvironment.class, "loadProgram");
    String programName = program.getSimpleName();
    String caller = callerClass;
    if (caller == null) {
      caller = getCallerClassName();
    }
    if (programInstances.containsKey(programName.toUpperCase(Locale.US) + "_" + caller)) {
      NAsmOnlineProgram instance = programInstances.get(programName.toUpperCase(Locale.US) + "_" + caller);
      NAsmProgramInstance pInst = new NAsmProgramInstance(instance);
      registers[0].setAddress(pInst);
      NAsmEnvironment.statsEndMethod(NAsmEnvironment.class, "loadProgram");
      return instance;
    } else {
      //Class<?> clazz;
      NAsmOnlineProgram instance;
      try {
        //clazz = Class.forName(program.getName());
        //instance = (NAsmOnlineProgram) clazz.getConstructor(NAsmStandardIO.class).newInstance(_STDOUT);
        instance = getProgramInstance(program.getName());
        if (NAsmEnvironment.TRACE()) {
          int level = NAsmExecutionInfo.getStackLevel() < 0 ? 1 : NAsmExecutionInfo.getStackLevel() + 1;
          logger.trace("[Loaded]" + String.format("%-" + (3 * level) + "s", " ") + String.format("(%-8s)", programName.toUpperCase(Locale.US)));
        }
      } catch (ClassNotFoundException e) {
        throw new NAsmException("Class not found: " + program.getName());
      } catch (Exception e) {
        if (userLogger != null) {
          userLogger.error(e);
        } else {
          logger.error(e.getMessage(), e);
        }
        throw e;
      }
      programInstances.put(programName.toUpperCase(Locale.US) + "_" + caller, instance);
      NAsmProgramInstance pInst = new NAsmProgramInstance(instance);
      registers[0].setAddress(pInst);
      NAsmEnvironment.statsEndMethod(NAsmEnvironment.class, "loadProgram");
      return instance;
    }
  }

  //SQtmp loadProgram version with: NAsmString programName and no "caller" limitations (always new instance)
  public static NAsmOnlineProgram loadProgram(NAsmString programName) throws NAsmException {
    return loadProgram(programName.trim());
  }

  //SQtmp loadProgram version with: String programName and no "caller" limitations (always new instance)
  public static NAsmOnlineProgram loadProgram(String programName) throws NAsmException {
    programName = programName.trim();
    Class<?> clazz;
    NAsmOnlineProgram instance;
    try {
      String mainPackage = getMainPackage(); 
      //System.out.println("[DEBUG] loadProgram: package=" + mainPackage + "." + programName.toLowerCase() + "." + NAsmStrings.capitalizeOnlyFirstLetter(programName.toLowerCase()));
      clazz = Class.forName(mainPackage + "." + programName.toLowerCase() + "." + NAsmStrings.capitalizeOnlyFirstLetter(programName.toLowerCase()));
      instance = (NAsmOnlineProgram) clazz.getConstructor(NAsmStandardIO.class).newInstance(_STDOUT);
      if (NAsmEnvironment.TRACE()) {
        int level = NAsmExecutionInfo.getStackLevel() < 0 ? 1 : NAsmExecutionInfo.getStackLevel() + 1;
        logger.trace("[Loaded]" + String.format("%-" + (3 * level) + "s", " ") + String.format("(%-8s)", programName));
      }
    } catch (ClassNotFoundException e) {
      throw new NAsmException("Class not found: " + programName);
    } catch (Exception e) {
      e.printStackTrace();
      if (userLogger != null) {
        userLogger.error(e);
      } else {
        logger.error(e.getMessage(), e);
      }
      throw new NAsmException("Cannot load class: " + programName);
    }
    //programInstances.put(programName.toUpperCase(Locale.US) + "_" + caller, instance);
    NAsmProgramInstance pInst = new NAsmProgramInstance(instance);
    registers[0].setAddress(pInst);
    return instance;
  }

  /**
   * DELETE EP=program macro
   * 
   * @param programName
   * @throws NAsmException
   */
  public static void deleteProgram(@SuppressWarnings("rawtypes") Class program) throws NAsmException {
    deleteProgram(program.getSimpleName().toUpperCase(Locale.US));
  }

  /**
   * DELETE EP=program macro
   * 
   * @param programName
   * @throws NAsmException
   */
  public static void deleteProgram(String programName) throws NAsmException {
    deleteProgram(programName, getCallerClassName());
  }

  private static void deleteProgram(String programName, String callerClass) throws NAsmException {
    try {
      String caller = callerClass;
      if (caller == null) {
        caller = getCallerClassName();
      }
      programName = programName.trim();
      if (programInstances.containsKey(programName.toUpperCase(Locale.US) + "_" + caller)) {
        //NAsmOnlineProgram instance = programInstances.get(programName.toUpperCase(Locale.US));
        //NAsmProgramInstance pInst = new NAsmProgramInstance(instance);
        //instance = null; //+ gc if needed
        programInstances.remove(programName.toUpperCase(Locale.US) + "_" + caller);
        if (NAsmEnvironment.TRACE()) {
          logger.trace("[Delete] " + String.format("(%-8s)", programName));
        }
      }
    } catch (Exception e) {
      logger.warn("Problems deleting program '" + programName + "'");
    }

  }

  /**
   * getProgramInstance usage: 
   * 
   *   L     R12,=V(BLUTO)
   *   getReg12().setAddress(getProgramInstance(Class.bluto));
   * 
   * @param program Class
   * @return program instance to use as input for "setAddress" 
   * @throws NAsmException
   */
  public static NAsmProgramInstance getProgramInstance(@SuppressWarnings("rawtypes") Class program) throws NAsmException {
    NAsmOnlineProgram instance = NAsmEnvironment.getInstanceSQ(program, getCallerClassName());
    return new NAsmProgramInstance(instance);
  }

  /**
   * execute usage: 
   * 
   *   L     R15,=V(BLUTO) 
   *         BASR  R14,R15
   *   NAsmEnvironment.execute(Class.bluto);
   * 
   * @param program Class 
   * @return 
   * @throws NAsmException
   */
  public static void execute(@SuppressWarnings("rawtypes") Class program, NAsmField... parameters) throws NAsmException {
    NAsmEnvironment.getInstanceSQ(program, getCallerClassName()).execute(parameters);
  }

  /**
   * execute usage: 
   * 
   *   L     R15,=V(BLUTO) 
   *         BASR  R14,R15
   *   NAsmEnvironment.execute(Class.bluto);
   * 
   * @param program Class 
   * @return 
   * @throws NAsmException
   */
  public static void execute(@SuppressWarnings("rawtypes") Class program) throws NAsmException {
    NAsmEnvironment.statsStartMethod(NAsmEnvironment.class, "execute");
    NAsmEnvironment.getInstanceSQ(program, getCallerClassName()).execute();
    NAsmEnvironment.statsEndMethod(NAsmEnvironment.class, "execute");
  }
  
  /**
   * execute usage: 
   * 
   *   LINK  EP=BLUTO) 
   *         
   *   NAsmEnvironment.executeLink(Class.bluto);
   * 
   * @param program Class 
   * @return 
   * @throws NAsmException
   */
  public static void executeLink(@SuppressWarnings("rawtypes") Class program) throws NAsmException {
    NAsmEnvironment.getInstanceSQ(program, "SHARED").execute();
  }

  /**
   * execute usage: 
   * 
   *   L     R15,=V(BLUTO) 
   *         BASR  R14,R15
   *   LOAD  EP=PSOUTDAT       CALL PSOUTDAT
   *   LR    R15,R0
   *   BALR  R14,R15
   *   NAsmEnvironment.execute(getReg15());
   * 
   * @param register instance 
   * @return 
   * @throws NAsmException
   */
  public static void execute(NAsmRegister register) throws NAsmException {
    if (register.getPointedAddress() == null) {
      throw new NAsmException("Register is not loaded to valid address");
    }
    if (register.getPointedAddress().getAddressedField().getProgramInstance() == null) {
      throw new NAsmException("Register is not loaded to valid program instance");
    }
    register.getPointedAddress().getAddressedField().getProgramInstance().execute();
  }

  /**
   * execute usage: 
   * 
   *   L     R15,PSPARSE_ADR    PARSE APT#
   *   BASR  R14,R15
   *   NAsmEnvironment.execute(storage.getCaParseAdr());
   * 
   * @param register instance 
   * @return 
   * @throws NAsmException
   */
  public static void execute(NAsmField address) throws NAsmException {
    byte[] value = address.getByteString(0, 4).getBytes(NAsmEnvironment.ENV_CHARSET());
    if (NAsmEnvironment.isValidAddress(value)) {
      if (NAsmEnvironment.getAddress(value).getAddressedField() instanceof NAsmProgramInstance) {
        NAsmProgramInstance instance = (NAsmProgramInstance) NAsmEnvironment.getAddress(value).getAddressedField();
        instance.getProgramInstance().execute();
      } else if (NAsmEnvironment.getAddress(value).getAddressedField().isStorage() //
          && NAsmEnvironment.getAddress(value).getAddressedField().getProgramInstance() != null) {
        NAsmEnvironment.getAddress(value).getAddressedField().getProgramInstance().execute();
      } else {
        throw new NAsmException("Address is not a valid program instance");
      }
    } else {
      throw new NAsmException("Field is not a valid address");
    }
  }

  public static NAsmOnlineProgram getInstanceShared(@SuppressWarnings("rawtypes") Class program) throws NAsmException {
    return getInstanceSQ(program, "SHARED");
  }

  public static NAsmOnlineProgram getInstanceSQ(@SuppressWarnings("rawtypes") Class program) throws NAsmException {
    return getInstanceSQ(program, getCallerClassName());
  }

  /**
   * getInstanceSQ: SQtmp - getInstance version without REG0 side-effect  
   * 
   * 
   * @param program 
   * @return program instance to use as input for "setAddress" 
   * @throws NAsmException
   */
  private static NAsmOnlineProgram getInstanceSQ(@SuppressWarnings("rawtypes") Class program, String callerClass) throws NAsmException {
    NAsmEnvironment.statsStartMethod(NAsmEnvironment.class, "getInstanceSQ");
    String programName = program.getSimpleName();
    String caller = callerClass;
    if (caller == null) {
      caller = getCallerClassName();
    }
    if (programInstances.containsKey(programName.toUpperCase(Locale.US) + "_" + caller)) {
      NAsmOnlineProgram instance = programInstances.get(programName.toUpperCase(Locale.US) + "_" + caller);
      NAsmEnvironment.statsEndMethod(NAsmEnvironment.class, "getInstanceSQ");
      return instance;
    } else {
      //Class<?> clazz;
      NAsmOnlineProgram instance;
      try {
        //clazz = Class.forName(program.getName());
        //instance = (NAsmOnlineProgram) clazz.getConstructor(NAsmStandardIO.class).newInstance(_STDOUT);
        instance = getProgramInstance(program.getName());
        if (NAsmEnvironment.TRACE()) {
          int level = NAsmExecutionInfo.getStackLevel() < 0 ? 1 : NAsmExecutionInfo.getStackLevel() + 1;
          logger.trace("[Loaded]" + String.format("%-" + (3 * level) + "s", " ") + String.format("(%-8s)", programName.toUpperCase(Locale.US)));
        }
      } catch (ClassNotFoundException e) {
        throw new NAsmException("Class not found: " + program.getName());
      } catch (Exception e) {
        if (userLogger != null) {
          userLogger.error(e);
        } else {
          logger.error(e.getMessage(), e);
        }
        throw new NAsmException("Cannot load class: " + programName);
      }
      programInstances.put(programName.toUpperCase(Locale.US) + "_" + caller, instance);
      NAsmEnvironment.statsEndMethod(NAsmEnvironment.class, "getInstanceSQ");
      return instance;
    }
  }

  private static NAsmOnlineProgram getProgramInstance(String programName) throws Exception {
    NAsmEnvironment.statsStartMethod(NAsmEnvironment.class, "getProgramInstance");
    Class<?> clazz = Class.forName(programName);
    NAsmOnlineProgram instance = (NAsmOnlineProgram) clazz.getConstructor(NAsmStandardIO.class).newInstance(_STDOUT);
    NAsmEnvironment.statsEndMethod(NAsmEnvironment.class, "getProgramInstance");
    return instance;
  }

  public static NAsmOnlineProgram getInstance(NAsmString programName) throws NAsmException {
    return getInstance(programName.toString(), getCallerClassName());
  }

  public static NAsmOnlineProgram getInstance(String programName) throws NAsmException {
    return getInstance(programName, getCallerClassName());
  }

  private static NAsmOnlineProgram getInstance(String programName, String callerClass) throws NAsmException {
    programName = programName.trim();
    String caller = callerClass;
    if (caller == null) {
      caller = getCallerClassName();
    }
    if (programInstances.containsKey(programName.toUpperCase(Locale.US) + "_" + caller)) {
      NAsmOnlineProgram instance = programInstances.get(programName.toUpperCase(Locale.US) + "_" + caller);
      NAsmProgramInstance pInst = new NAsmProgramInstance(instance);
      registers[0].setAddress(pInst);
      return instance;
    } else {
      Class<?> clazz;
      NAsmOnlineProgram instance;
      try {
        String mainPackage = NAsmEnvironment.getMainInstance().getClass().getPackage().toString().replace("package ", "");
        mainPackage = mainPackage.substring(0, mainPackage.lastIndexOf("."));
        clazz = Class.forName(mainPackage + "." + programName.toLowerCase() + "." + NAsmStrings.capitalizeOnlyFirstLetter(programName.toLowerCase()));
        instance = (NAsmOnlineProgram) clazz.getConstructor(NAsmStandardIO.class).newInstance(_STDOUT);
        if (NAsmEnvironment.TRACE()) {
          int level = NAsmExecutionInfo.getStackLevel() < 0 ? 1 : NAsmExecutionInfo.getStackLevel() + 1;
          logger.trace("[Loaded]" + String.format("%-" + (3 * level) + "s", " ") + String.format("(%-8s)", programName));
        }
      } catch (ClassNotFoundException e) {
        throw new NAsmException("Class not found: " + programName);
      } catch (Exception e) {
        if (userLogger != null) {
          userLogger.error(e);
        } else {
          logger.error(e.getMessage(), e);
        }
        throw new NAsmException("Cannot load class: " + programName);
      }
      programInstances.put(programName.toUpperCase(Locale.US) + "_" + caller, instance);
      NAsmProgramInstance pInst = new NAsmProgramInstance(instance);
      registers[0].setAddress(pInst);
      return instance;
    }
  }

  public static void execTime(NAsmField time, NAsmField date) throws NAsmException {
    Date currDate = Calendar.getInstance().getTime();
    String forcedDate = getEnvVar("EXEC_TIMESTAMP");
    if (forcedDate != null && !forcedDate.isEmpty()) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH);
      try {
        currDate = sdf.parse(forcedDate);
      } catch (Exception e) {
        throw new NAsmException("Wrong env variable format EXEC_TIMESTAMP=" + forcedDate + ", exepected format yyyymmdd_hhmmss.");
      }
    }
    SimpleDateFormat jdeSdf = new SimpleDateFormat("01yyDDD");
    int julNum = Integer.parseInt(jdeSdf.format(currDate));
    byte[] julianPacked = NAsmStrings.packData(julNum, 7, 0, false);
    date.setByteArray(julianPacked);
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(currDate);
    String timeString = timeStamp.substring(9, 17);
    byte[] timeBinary = new byte[4];
    for (int idx = 0; idx < timeString.length(); idx += 2) {
      timeBinary[idx / 2] = (byte) ((Character.digit(timeString.charAt(idx), 16) << 4) + Character.digit(timeString.charAt(idx + 1), 16));
    }
    time.setByteArray(timeBinary);
  }

  /**
   * 
   * @return the main program storage instance
   */
  //public static NAsmField getStorage() {
  //  return mainInstance.getStorage();
  //}

  /**
   * 
   * @param dsnName
   * @return
   */
  public static NAsmDDcard getFileInfo(String dsnName) {
    return NAsmEnvironment.getMainInstance().getDDCardProvider().getFileInfo(dsnName);
  }

  /**
   * @return the filesBasepath
   */
  public static String getFilesBasepath() {
    return filesBasepath;
  }

  /**
   * @param filesBasepath the filesBasepath to set
   */
  public static void setFilesBasepath(String filesBasepath) {
    NAsmEnvironment.filesBasepath = filesBasepath;
  }

  /**
   * @return the sysoutBasepath
   */
  public static String getSysoutBasepath() {
    return sysoutBasepath;
  }

  /**
   * @param sysoutBasepath the sysoutBasepath to set
   */
  public static void setSysoutBasepath(String sysoutBasepath) {
    NAsmEnvironment.sysoutBasepath = sysoutBasepath;
  }

  /**
   * @return the jobName
   */
  public static String getJobName() {
    return String.format("%-8s", jobName);
  }

  /**
   * @param jobName the jobName to set
   */
  public static void setJobName(String jobName) {
    NAsmEnvironment.jobName = jobName;
  }

  /**
   * @return the jobXmlName
   */
  public static String getJobXmlName() {
    return jobXmlName;
  }

  /**
   * @param jobXmlName the jobXmlName to set
   */
  public static void setJobXmlName(String jobXmlName) {
    NAsmEnvironment.jobXmlName = jobXmlName;
  }

  /**
   * @return the jobProjectName
   */
  public static String getJobProjectName() {
    return String.format("%-7s", jobProjectName);
  }

  /**
   * @param jobProjectName the jobProjectName to set
   */
  public static void setJobProjectName(String jobProjectName) {
    NAsmEnvironment.jobProjectName = jobProjectName;
  }

  /**
   * @return the stepName
   */
  public static String getStepName() {
    return stepName;
  }

  /**
   * @param stepName the stepName to set
   */
  public static void setStepName(String stepName) {
    NAsmEnvironment.stepName = stepName;
  }

  /**
   * @return the jobNumber
   */
  public static long getJobNumber() {
    return jobNumber;
  }

  /**
   * @param jobNumber the jobNumber to set
   */
  public static void setJobNumber(long jobNumber) {
    NAsmEnvironment.jobNumber = jobNumber;
  }

  /**
   * @return the next field id
   * @throws NAsmException
   */
  public static synchronized int getNextFieldId() throws NAsmException {
    int rc = fieldIdThreshold++;
    if (rc > addressedFieldList.size()) {
      addressedFieldList.ensureCapacity(addressedFieldList.size() + FIELD_ALLOCATION_SLOT);
      initializeArray(addressedFieldList, addressedFieldList.size(), FIELD_ALLOCATION_SLOT);
    }
    return rc;
  }

  /**
   * @return the next memory id
   * @throws NAsmException
   */
  public static synchronized int getNextMemoryId() throws NAsmException {
    int rc = memoryIdThreshold++;
    if (rc + 1 >= MEMORY_ALLOCATION_LIMIT) {
      throw new NAsmException("The memory allocation limit has been reached, please setup a proper value using " + ENV_VAR_NIB_MEM_LIMIT + " env variable");
    }
    if (rc + 1 >= MEMORY_MAX_ALLOCATION) {
      throw new NAsmException("Maximum memory allocation reached");
    }
    return rc;
  }
  
  /**
   * 
   * @return caller program reference
   */
  public static String getCallerReference() {
    try {
      StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
      for (int i = 1; i < stElements.length; i++) {
        StackTraceElement ste = stElements[i];
        if (ste.getClassName().equals(NAsmEnvironment.getCurrentInstance().getClass().getName())) {
          return ste.getClassName() + ", line: " + ste.getLineNumber();
        }
      }
    } catch (Exception e) {

    }
    return "";
  }

  /**
   * 
   * @return caller program name
   */
  public static String getCallerClassName() {
    try {
      StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
      for (int i = 1; i < stElements.length; i++) {
        StackTraceElement ste = stElements[i];
        if (ste.getClassName().equals(NAsmEnvironment.getCurrentInstance().getClass().getName())) {
          return ste.getClassName();
        }
      }
    } catch (Exception e) {

    }
    return "";
  }

  /**
   * @return the debugInt
   */
  public static int getDebugInt() {
    return debugInt;
  }

  /**
   * @param debugInt the debugInt to set
   */
  public static void setDebugInt(int debugInt) {
    NAsmEnvironment.debugInt = debugInt;
  }

  /**
   * @return the debugString
   */
  public static int getDebugString() {
    return debugString;
  }

  /**
   * @param debugString the debugString to set
   */
  public static void setDebugString(int debugString) {
    NAsmEnvironment.debugString = debugString;
  }

  /**
   * Register not input files read and/or written in the JOBs, the same file
   * can be written from a JOB and read or continued to written from a next
   * JOB, we need to store the files used during the whole program execution
   * in order to open them in append when needed.
   * 
   * @param file
   *            to be registered
   * @return <b>true</b> if for the file is already registered
   */
  public static boolean registerFile(NAsmFile file) {
    if (!wFiles.containsKey(file.getName())) {
      wFiles.put(file.getName(), file);
      addFile(file);
      return false;
    }
    return true;
  }

  /**
   * 
   * Close all the files common among the JOBs.
   * 
   */
  public static void closeCommonFiles() {
    if (wFiles.size() > 0) {
      Iterator<String> keys = wFiles.keySet().iterator();
      while (keys.hasNext()) {
        String fName = keys.next();
        if (wFiles.get(fName).isOpened()) {
          wFiles.get(fName).close();
        }
      }
    }
  }

  /**
   * @return a vector of all the files defined for this session
   */
  public static Vector<NAsmFile> getFiles() {
    return vFiles;
  }

  /**
   * @return an instance of a registerd file
   */
  public static NAsmFile getFile(String name) {
    return wFiles.get(name);
  }

  /**
   * 
   * Close all the files of the program.
   * 
   */
  public static void closeFiles() {
    for (int idx = 0; idx < vFiles.size(); idx++) {
      if (vFiles.get(idx).isOpened()) {
        try {
          vFiles.get(idx).close();
        } catch (Exception e) {
          //Do nothing
          if (userLogger != null) {
            userLogger.error(e);
          } else {
            logger.error(e.getMessage(), e);
          }
        }
      }
    }
  }

  /**
   * @param file
   *            the file to be added
   * @return the file added
   */
  private static NAsmFile addFile(NAsmFile file) {
    vFiles.add(vFiles.size(), file);
    return file;
  }

  //SQtmp TODO - to extend to manage other CVT attributes
  public static byte[] getCVT_JulianDate() {
    SimpleDateFormat jdeSdf = new SimpleDateFormat("01yyDDD");
    int julNum = Integer.parseInt(jdeSdf.format(Calendar.getInstance().getTime()));
    return NAsmStrings.packData(julNum, 7, 0, false);
  }

  /**
   * 
   * @return the default name for disks volume
   */
  public static String DFT_VOLUME() {
    if (dftVolume == null) {
      try {
        dftVolume = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_DFT_VOLUME);
      } catch (Exception ex) {

      }
      if (dftVolume == null || dftVolume.trim().length() == 0) {
        dftVolume = _NIB_DFT_VOLUME_DFLT;
      } else {
        dftVolume = NAsmStrings.formatSpaces(dftVolume, 6);
      }
    }
    return dftVolume;
  }

  /**
   * 
   * @return the default memory limit
   */
  public static int MEMORY_LIMIT() {
    if (memoryLimit == null) {
      try {
        String val = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_MEM_LIMIT);
        if (val != null && !val.isEmpty()) {
          memoryLimit = Integer.parseInt(val);
        }
      } catch (Exception ex) {
        logger.error("Invalid value for " + ENV_VAR_NIB_MEM_LIMIT + ", allowed values are from 1 to 100");
      }
      if (memoryLimit == null || memoryLimit < 1 || memoryLimit > 100) {
        if (memoryLimit != null) {
          logger.warn("Invalid value for " + ENV_VAR_NIB_MEM_LIMIT + ", allowed values are from 1 to 100, the default value is used");
        }
        memoryLimit = _NIB_DFLT_MEM_LIMIT;
      }
    }
    return memoryLimit;
  }

  /**
   * 
   * @return the default fields allocation slot
   */
  public static int FIELDS_ALLOC_SLOT() {
    if (fieldsAllocSlot == null) {
      try {
        String val = NAsmEnvironment.getEnvVar(ENV_VAR_NIB_FIELDS_ALLOC_SLOT);
        if (val != null && !val.isEmpty()) {
          fieldsAllocSlot = Integer.parseInt(val);
        }
      } catch (Exception ex) {
        logger.error("Invalid value for " + ENV_VAR_NIB_FIELDS_ALLOC_SLOT + ", allowed values are from 100 to 100000");
      }
      if (fieldsAllocSlot == null || fieldsAllocSlot < 100 || fieldsAllocSlot > 100000) {
        if (fieldsAllocSlot != null) {
          logger.warn("Invalid value for " + ENV_VAR_NIB_FIELDS_ALLOC_SLOT + ", allowed values are from 100 to 100000, the default value is used");
        }
        fieldsAllocSlot = _NIB_DFLT_FIELDS_ALLOC_SLOT;
      }
    }
    return fieldsAllocSlot;
  }

  /**
   * 
   * @return the default fields allocation slot
   */
  public static NAsmField CODE_CODE() {
    return _COND_CODE;
  }

	public static void setCOND_CODE(int condCode) throws NAsmException {
		_COND_CODE.set(condCode);
	}

	public static int getCOND_CODE() throws NAsmException {
		return _COND_CODE.getInt();
	}

	public static boolean HIGH_CONDCODE() throws NAsmException {
		return getCOND_CODE() == 2;
	}

	public static boolean LOW_CONDCODE() throws NAsmException {
		return getCOND_CODE() == 4;
	}

  /**
   * Capture runtime execution start time
   * 
   * @param class_
   * @param methodName
   */
  public static void statsStartMethod(Class<?> class_, String methodName) {
    if (!STATS()) {
      return;
    }
    NAsmExecStats.statsStartMethod(class_, methodName);
  }

  /**
   * Capture runtime execution end time
   * 
   * @param class_
   * @param methodName
   */
  public static void statsEndMethod(Class<?> class_, String methodName) {
    if (!STATS()) {
      return;
    }
    NAsmExecStats.statsEndMethod(class_, methodName);
  }

  /**
   * @return the userLogger
   */
  public static INAsmLogger getUserLogger() {
    return userLogger;
  }

  /**
   * @param userLogger the userLogger to set
   */
  public static void setUserLogger(INAsmLogger userLogger) {
    NAsmEnvironment.userLogger = userLogger;
  }

  /**
   * Is a Windows OS?
   */
  public static boolean isOsWindows() {
    return File.separator.equals("\\");
  }

	//SQtmp
	public static void memoryStats(String text) {
		final int mb = 1024 * 1024;
		// get Runtime instance
		Runtime instance = Runtime.getRuntime();
		logger.info("\n[PERF ][" + text + "] Heap Memory used [MB]: " + (instance.totalMemory() - instance.freeMemory()) / mb + 
				"; Total is: " + instance.totalMemory() / mb
				+ "; Max is: " + instance.maxMemory() / mb + "\n");
	}

  public static int getMemorySize() {
    int total = 0;
    HashMap<Integer, Integer> counted = new HashMap<>();
    for (int idx = 0; idx < addressesMapList.length; idx++) {
      int fieldId = addressesMapList[idx];
      if (fieldId > -1) {
        NAsmAddressesCollection coll = addressedFieldList.get(fieldId);
        if (coll != null && coll.getAddressesList().size() > 0 && !counted.containsKey(fieldId)) {
          counted.put(fieldId, fieldId);
          total += coll.getAddressesList().size();
        }
      }
    }
    return total;
  }

  public static void printMemoryStats() {
    printMemoryStats(false);
  }
  
  public static void printMemoryStats(boolean includeFieldsDetail) {
    HashMap<Integer, Integer> printed = new HashMap<>();
    for (int idx = 0; idx < memoryIdThreshold; idx++) {
      int fieldId = addressesMapList[idx];
      if (fieldId == -1) {
      } else {
        if (includeFieldsDetail) {
          NAsmAddressesCollection coll = addressedFieldList.get(fieldId);
          if (coll != null && coll.getAddressesList().size() > 1000 && !printed.containsKey(fieldId)) {
            printed.put(fieldId, fieldId);
            Integer key = coll.getAddressesList().keySet().iterator().next();
            String line = NAsmStrings.formatSpaces(coll.getAddressesList().get(key).getAddressedField().getName(), 30) //
                + " -> " + coll.getAddressesList().size() + " Id(" + fieldId + ")";
            logger.info(line);
          }
        }
      }
    }
    logger.info("-");
    logger.info("Total memory : " + memoryIdThreshold);
    logger.info("Used   slots : " + (memoryIdThreshold - addressesMapRecycled.size()));
    logger.info("Empty  slots : " + addressesMapRecycled.size());
    logger.info("Fields slots : " + fieldIdThreshold);
  }
  
  /**
   * @return the storeAddresses
   */
  public static boolean isStoreAddresses() {
    return NAsmEnvironment.storeAddresses;
  }

  /**
   * @param storeAddresses the storeAddresses to set
   */
  public static void setStoreAddresses(boolean value) {
    NAsmEnvironment.storeAddresses = value;
    NAsmEnvironment.storedAddresses = new ArrayList<>();
  }

  /**
   * @return the storedAddresses
   */
  public static List<NAsmAddress> getStoredAddresses() {
    return NAsmEnvironment.storedAddresses;
  }

  /**
   * @return the storedAddresses
   */
  public static void addStoredAddress(NAsmAddress address) {
    NAsmEnvironment.storedAddresses.add(address);
  }

	/**
	 * @return the systemDummyArea
	 */
	public static NAsmField getSystemDummyArea() {
		return systemDummyArea;
	}

  /**
   * @return the memoryLimit
   */
  public static Integer getMemoryLimit() {
    return memoryLimit;
  }

  /**
   * @param memoryLimit the memoryLimit to set
   */
  public static void setMemoryLimit(Integer memoryLimit) {
    if (memoryLimit == null || memoryLimit < 1 || memoryLimit > 100) {
      memoryLimit = _NIB_DFLT_MEM_LIMIT;
      logger.warn("Invalid value for memory limit, allowed value are from 1 to 100");
    }
    NAsmEnvironment.memoryLimit = memoryLimit;
  }

  /**
   * @return the fieldsAllocSlot
   */
  public static Integer getFieldsAllocSlot() {
    return fieldsAllocSlot;
  }

  /**
   * @param fieldsAllocSlot the fieldsAllocSlot to set
   */
  public static void setFieldsAllocSlot(Integer fieldsAllocSlot) {
    if (fieldsAllocSlot == null || fieldsAllocSlot < 100 || fieldsAllocSlot > 100000) {
      fieldsAllocSlot = _NIB_DFLT_FIELDS_ALLOC_SLOT;
      logger.error("Invalid value for fields allocation slot, allowed value are from 100 to 100000");
    }
    NAsmEnvironment.fieldsAllocSlot = fieldsAllocSlot;
  }

	/**
	 * SQ
	 * 
	 * @return program package
	 */
	public static String getMainPackage() {
		String mainPackage = null;

		// From higher-level program:
		if (NAsmEnvironment.getMainInstance() != null && !NAsmEnvironment.getMainInstance().getName().equals("ZOSMAIN")) {
			mainPackage = NAsmEnvironment.getMainInstance().getClass().getPackage().toString().replace("package ", "");
			mainPackage = mainPackage.substring(0, mainPackage.lastIndexOf("."));
		} else {
			// DEFAULT:
			if (mainPackage == null) {
				mainPackage = iProgramWrapper.getProgramsPackage();
			}
		}
		return mainPackage;
	}

	/**
	 * @return the external recordHandler
	 */
	public static INAsmExtRecordHandler getExtRecordHandler() {
		return extRecordHandler;
	}

	/**
	 * @param recordHandler the external recordHandler to set
	 */
	public static void setExtRecordHandler(INAsmExtRecordHandler extRecordHandler) {
		NAsmEnvironment.extRecordHandler = extRecordHandler;
	}

}
