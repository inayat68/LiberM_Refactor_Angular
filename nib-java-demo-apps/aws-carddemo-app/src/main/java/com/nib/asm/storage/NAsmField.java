package com.nib.asm.storage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import com.nib.asm.NAsmOnlineProgram;
import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.utils.NAsmEnvironment;
import com.nib.asm.utils.NAsmStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * Abstraction of a basic NAsmRuntime field definition.<br>
 * The basic field definition consists of the DEFINE keyword, the field name,
 * its location, and its attributes. <br>
 * The sample definition below uses the field name FIELDA, has a location
 * starting in position 100 in the record, and has the attribute 5 for length
 * and A for alphabetic.<br>
 * 
 * @author nib-labs.io
 */
public class NAsmField implements INAsmBasicBuffer, INAsmClonable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static final Logger logger = LoggerFactory.getLogger(NAsmField.class);

  public final static String SYSTEM_RETURN_CODE = "RETURN-CODE";
  public final static String SYSTEM_SQLCODE = "SQLCODE";
  public final static String SYSTEM_SYSDATE_LONG = "SYSDATE-LONG";
  public final static String SYSTEM_SYSDATE = "SYSDATE";
  public final static String SYSTEM_SYSTIME = "SYSTIME";
  public final static String _STORAGE = "STORAGE";
  public final static String _STORAGE_RP = "_STORAGE_";
  public final static String _GETMAIN_STORAGE = "MAIN_STG_";
  public final static String _CONSTANT = "CONST";
  
  private int id = 0;
  private String name;
  private NAsmField parent;
  private byte[] _value;
  private boolean storagePointer = false;
  private NAsmAddress cachedAddress = null;
  protected NAsmRegister _usingRegister = null;
  private Boolean dsect = null;
  //private HashMap<Integer, Integer> storedRegisters = new HashMap<>();
  
  /**
   * Self-explanatory values for NAsmRuntime field types.
   */
  public static enum NAsmFieldType {
    ALPHANUMERIC, //
    ALPHANUMERIC_DBCS, //
    ALPHANUMERIC_MIXED, //
    //    NUMERIC_ZONED, //
    NUMERIC_PACKED, //
    NUMERIC_PACKED_UNSIGNED, //
    NUMERIC_BINARY,
  }

  /**
   * Self-explanatory values for conditions.
   */
  public static enum Condition {
    EQUAL, //
    LESS, //
    LESS_EQUAL, //
    GREATER, //
    GREATER_EQUAL, //
  }

  private NAsmFieldType type;
  private int length = 0;
  private Integer array = null;
  private int offset;
  private Integer absoluteOffset = null;
  private int decimals = 0;

  private Vector<NAsmField> vFields = new Vector<>();
  private HashMap<String, NAsmField> hFields = new HashMap<>();
  private HashMap<String, NAsmRegister[]> savedRegisters = null;
  private NAsmOnlineProgram programInstance = null;

  /**
   * 
   * @param name
   *            field name.
   * @param type
   *            field type a value from NAsmField.NAsmFieldType.
   * @param offset
   *            field offset.
   * @param length
   *            field length.
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public NAsmField(String name, NAsmFieldType type, int offset, int length) throws NAsmException {
    this(null, name, type, offset, length, null);
  }

  public NAsmField(NAsmField parent, String name, NAsmFieldType type, int offset, int length) throws NAsmException {
    this(parent, name, type, offset, length, null);
  }

  /**
   * 
   * @param parent
   *            instance.
   * @param name
   *            field name.
   * @param type
   *            field type a value from NAsmField.NAsmFieldType.
   * @param offset
   *            field offset.
   * @param length
   *            field length.
   * @param array
   *            field array.
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public NAsmField(NAsmField parent, String name, NAsmFieldType type, int offset, int length, Integer array) throws NAsmException {
    this.parent = parent;
    this.name = name;
    this.type = type;
    this.offset = offset;
    this.length = length;
    if (name.equals(_STORAGE)) {
      //Add 32K of buffer for operation after memory
      this.length += 32000;
      //Instance of the program which creating the STORAGE instance
      this.programInstance = NAsmOnlineProgram.getInstance();
      this.name = _STORAGE_RP + this.programInstance.getName();
    }
    this.array = array;
    try {
      if (parent != null) {
        parent.addField(this);
        this._value = parent._value;
      } else {
        int totalLength = this.getLengthBytes();
        if (this.getArray() != null && this.getArray() > 0) {
          totalLength = this.getLengthBytes() * this.getArray();
        }
        this._value = new byte[totalLength];
        this.initialize();
      }
    } catch (NAsmException eex) {
      throw eex;
    } catch (Exception ex) {
      NAsmException eex = new NAsmException("NAsmField instance runtime error: " + ex.getMessage());
      eex.setJavaStack(ex);
      throw eex;
    }
  }

  /**
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void initialize() throws NAsmException {
    if (this.getType().equals(NAsmFieldType.ALPHANUMERIC) //
        || this.getType().equals(NAsmFieldType.ALPHANUMERIC_DBCS) //
        || this.getType().equals(NAsmFieldType.ALPHANUMERIC_MIXED)) {
      String v;
      if (this.getName().equals(SYSTEM_SYSDATE_LONG)) {
        // YYYY/MM/DD
        v = NAsmEnvironment.getSYSDATE_LONG();
      } else if (this.getName().equals(SYSTEM_SYSDATE)) {
        // YY/MM/DD
        v = NAsmEnvironment.getSYSDATE();
      } else if (this.getName().equals(SYSTEM_SYSTIME)) {
        // HH:mm:ss
        v = NAsmEnvironment.getSYSTIME();
      } else {
        //Initialize to LOW-VALUE
        v = null;
      }
      if (v != null) {
        byte[] rc;
        if (NAsmEnvironment.RUN_EBCDIC()) {
          rc = NAsmStrings.convertBytesToEBCDIC(v.getBytes(NAsmEnvironment.ENV_CHARSET()));
        } else {
          rc = v.getBytes(NAsmEnvironment.ENV_CHARSET());
        }
        this.setValue(rc, 0);
      }
    } else if (this.getType().equals(NAsmFieldType.NUMERIC_PACKED)) {
      byte[] packedValue = NAsmStrings.packData(0, this.getLengthDigits(), this.decimals, true);
      this.setValue(packedValue, 0);
    } else if (this.getType().equals(NAsmFieldType.NUMERIC_PACKED_UNSIGNED)) {
      byte[] packedValue = NAsmStrings.packData(0, this.getLengthDigits(), this.decimals, false);
      this.setValue(packedValue, 0);
    } else if (this.getType().equals(NAsmFieldType.NUMERIC_BINARY)) {
      try {
        byte[] binaryValue = NAsmStrings.valueToBinaryBigEndian(0, this.length);
        this.setValue(binaryValue, 0);
      } catch (Exception ex) {
        NAsmException eex = new NAsmException("NAsmField instance failed for " + this.getName() + ", error: " + ex.getMessage());
        eex.setJavaStack(ex);
        throw eex;
      }
    } else {
      throw new NAsmException("NAsmField " + this.getType() + " instance failed");
    }
  }

  /**
   * 
   * @param value
   *            a decimal number to round.
   * @param decimals
   *            a value greater than 0 that specifies the number of decimal
   *            places to round to.
   * @return the rounded value.
   */
  private double round(double value, int decimals) throws Exception {
    if (decimals < 0) {
      throw new IllegalArgumentException();
    }
    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(decimals, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *            the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the type
   */
  public NAsmFieldType getType() {
    return type;
  }

  /**
   * @return the length
   */
  public int getLength() {
    return length;
  }

  /**
   * @return the total area length (considering multiplier)
   */
  public int getTotalLength() {
    return length * (array != null ? array : 1);
  }

  /**
   * @param length
   *            the length to set
   */
  public void setLength(int length) {
    this.length = length;
  }

  /**
   * @return the length in bytes
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public int getLengthBytes() throws NAsmException {
    return this.length;
  }

  /**
   * @return the length in digits
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public int getLengthDigits() throws NAsmException {
    if (this.getType().equals(NAsmFieldType.NUMERIC_PACKED) //
        || this.getType().equals(NAsmFieldType.NUMERIC_PACKED_UNSIGNED)) {
      return (length * 2) - 1;
    } else if (this.getType().equals(NAsmFieldType.NUMERIC_BINARY)) {
      if (length <= 2) {
        return 5;
      } else if (length <= 4) {
        return 10;
      }
      return length;
    } else {
      return length;
    }
  }

  /**
   * @return the offset
   */
  public int getOffset() {
    return offset;
  }

  /**
   * @param offset
   *            the offset to set
   */
  public void setOffset(int offset) {
    this.offset = offset;
  }

  /**
   * @return the decimals
   */
  public int getDecimals() {
    return decimals;
  }

  /**
   * @param decimals
   *            the decimals to set
   */
  public void setDecimals(int decimals) {
    this.decimals = decimals;
  }

  /**
   * @return the parent
   */
  public NAsmField getParent() {
    return parent;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#clone()
   */
  @Override
  protected NAsmField clone() {
    NAsmField f = null;
    try {
      f = new NAsmField(name, type, offset, length);
      f.setDecimals(this.getDecimals());
      f.setArray(array);
    } catch (Exception e) {
      NAsmEnvironment.console(e);
    }
    return f;
  }

  /**
   * 
   * @return a clone of this field
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public NAsmField getClone() throws NAsmException {
    return clone();
  }

  /**
   * Make a boolean test of this NAsmField instance for equal with the value
   * passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean equals(double value) throws NAsmException {
    return this.equals(this.getConstant(value));
  }

  /**
   * Make a boolean test of this NAsmField instance for equal with the value
   * passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean equals(int value) throws NAsmException {
    return this.equals(this.getConstant(value));
  }

  public boolean equals(NAsmString value) throws NAsmException {
    return equals(value.toString());
  }

  public boolean equals(NAsmString value, Integer length) throws NAsmException {
    return equals(value.toString(), length);
  }

  /**
   * Make a boolean test of this NAsmField instance for equal with the value
   * passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean equals(String value) throws NAsmException {
    return this.equals(value, null);
  }

  public boolean equals(String value, Integer length) throws NAsmException {
    return this.equals(this.getConstant(value), length);
  }

  /**
   * Make a boolean test of this NAsmField instance for equal with the value of
   * the field passed.
   * 
   * @param field
   *            field to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean equals(NAsmField field) throws NAsmException {
    return equals(field, 0);
  }

  public boolean equals(NAsmField field, Integer length) throws NAsmException {
    return this.compareFields(this, field, Condition.EQUAL, length).getResult();
  }

  /**
   * Make a boolean test of this NAsmField instance for equal with the range of
   * values passed.
   * 
   * @param value1
   *            lower range value
   * @param value2
   *            higher range value
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean inRange(int value1, int value2) throws NAsmException {
    return (this.getNumeric() >= value1 && this.getNumeric() <= value2);
  }

  /**
   * Make a boolean test of this NAsmField instance for equal with the range of
   * values passed.
   * 
   * @param value1
   *            lower range value
   * @param value2
   *            higher range value
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean inRange(String value1, String value2) throws NAsmException {
    NAsmField const1 = this.getConstant(value1);
    NAsmField const2 = this.getConstant(value2);
    boolean cond1 = this.compareFields(this, const1, Condition.GREATER_EQUAL, null).getResult();
    boolean cond2 = this.compareFields(this, const2, Condition.LESS_EQUAL, null).getResult();
    return (cond1 && cond2);
  }

  /**
   * Make a boolean test of this NAsmField instance for less than with the value
   * passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean less(double value) throws NAsmException {
    return this.less(this.getConstant(value));
  }

  /**
   * Make a boolean test of this NAsmField instance for less than with the value
   * passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean less(int value) throws NAsmException {
    return this.less(this.getConstant(value));
  }

  public boolean less(NAsmString value) throws NAsmException {
    return this.less(value.toString());
  }

  /**
   * Make a boolean test of this NAsmField instance for less than with the value
   * passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean less(String value) throws NAsmException {
    return this.less(this.getConstant(value));
  }

  /**
   * Make a boolean test of this NAsmField instance for less than with the value
   * of the field passed.
   * 
   * @param field
   *            field to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean less(NAsmField field) throws NAsmException {
    return this.compareFields(this, field, Condition.LESS, null).getResult();
  }

  /**
   * Make a boolean test of this NAsmField instance for less or equal than with
   * the value passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean lessEquals(double value) throws NAsmException {
    return this.lessEquals(this.getConstant(value));
  }

  /**
   * Make a boolean test of this NAsmField instance for less or equal than with
   * the value passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean lessEquals(int value) throws NAsmException {
    return this.lessEquals(this.getConstant(value));
  }

  /**
   * Make a boolean test of this NAsmField instance for less or equal than with
   * the value passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean lessEquals(String value) throws NAsmException {
    return this.lessEquals(this.getConstant(value));
  }

  /**
   * Make a boolean test of this NAsmField instance for less or equal than with
   * the value of the field passed.
   * 
   * @param field
   *            field to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean lessEquals(NAsmField field) throws NAsmException {
    return this.compareFields(this, field, Condition.LESS_EQUAL, null).getResult();
  }

  /**
   * Make a boolean test of this NAsmField instance for greater than with the
   * value passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean greater(double value) throws NAsmException {
    return this.greater(this.getConstant(value));
  }

  /**
   * Make a boolean test of this NAsmField instance for greater than with the
   * value passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean greater(int value) throws NAsmException {
    return this.greater(this.getConstant(value));
  }

  /**
   * Make a boolean test of this NAsmField instance for greater than with the
   * value passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean greater(String value) throws NAsmException {
    return this.greater(this.getConstant(value));
  }

  /**
   * Make a boolean test of this NAsmField instance for greater than with the
   * value of the field passed.
   * 
   * @param field
   *            field to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean greater(NAsmField field) throws NAsmException {
    return this.compareFields(this, field, Condition.GREATER, null).getResult();
  }

  /**
   * Make a boolean test of this NAsmField instance for greater or equal than
   * with the value passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean greaterEquals(double value) throws NAsmException {
    return this.greaterEquals(this.getConstant(value));
  }

  /**
   * Make a boolean test of this NAsmField instance for greater or equal than
   * with the value passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean greaterEquals(int value) throws NAsmException {
    return this.greaterEquals(this.getConstant(value));
  }

  /**
   * Make a boolean test of this NAsmField instance for greater or equal than
   * with the value passed.
   * 
   * @param value
   *            value to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean greaterEquals(String value) throws NAsmException {
    return this.greaterEquals(this.getConstant(value));
  }

  /**
   * Make a boolean test of this NAsmField instance for greater or equal than
   * with the value of the field passed.
   * 
   * @param field
   *            field to be tested
   * @return boolean result of the test
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean greaterEquals(NAsmField field) throws NAsmException {
    return this.compareFields(this, field, Condition.GREATER_EQUAL, null).getResult();
  }

  /**
   * 
   * @return<true> if the field is an alphabetic type
   */
  public boolean isAlphabeticType() {
    return (this.getType().equals(NAsmFieldType.ALPHANUMERIC) || this.getType().equals(NAsmFieldType.ALPHANUMERIC_DBCS) || this.getType().equals(NAsmFieldType.ALPHANUMERIC_MIXED));
  }

  /**
   * 
   * @return<true> if the field is a numeric type
   */
  public boolean isNumericType() {
    return (this.getType().equals(NAsmFieldType.NUMERIC_BINARY) //
        || this.getType().equals(NAsmFieldType.NUMERIC_PACKED) //
        || this.getType().equals(NAsmFieldType.NUMERIC_PACKED_UNSIGNED)) //
    //        || this.getType().equals(NAsmFieldType.NUMERIC_ZONED))
    ;
  }

  /**
   * 
   * @param value
   * @return
   * @throws NAsmException
   */
  public int compareTo(String value) throws NAsmException {
    return this.compareTo(getConstant(value));
  }

  /**
   * 
   * @param field
   * @return
   * @throws NAsmException
   */
  public int compareTo(NAsmField field) throws NAsmException {
    NAsmCompareInfo compareFields = this.compareFields(this, field, Condition.EQUAL, null);
    return compareFields.getValue();
  }

  /**
   * 
   * @param field
   * @return
   * @throws NAsmException
   */
  public int compareTo(NAsmField field, int length) throws NAsmException {
    NAsmCompareInfo compareFields = this.compareFields(this, field, Condition.EQUAL, length);
    return compareFields.getValue();
  }

  public NAsmCompareInfo compareFields(NAsmField fld1, NAsmField fld2, Condition condition, Integer length) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "compareFields");
    NAsmCompareInfo rc = null;
    NAsmAddress fa1 = fld1.getPointedAddress();
    NAsmAddress fa2 = fld2.getPointedAddress();
    //Is comparing memory addresses?
    if (fa1 != null //
        && fa2 != null) {
      int val1 = fa1.getOffset();
      int val2 = fa2.getOffset();
      if (condition.equals(Condition.GREATER)) {
        //return (val1 > val2);
        rc = new NAsmCompareInfo(val1, val2, val1 > val2);
      } else if (condition.equals(Condition.GREATER_EQUAL)) {
        //return (val1 >= val2);
        rc = new NAsmCompareInfo(val1, val2, val1 >= val2);
      } else if (condition.equals(Condition.LESS)) {
        //return (val1 < val2);
        rc = new NAsmCompareInfo(val1, val2, val1 < val2);
      } else if (condition.equals(Condition.LESS_EQUAL)) {
        //return (val1 <= val2);
        rc = new NAsmCompareInfo(val1, val2, val1 <= val2);
      } else if (condition.equals(Condition.EQUAL)) {
        //return (val1 == val2);
        rc = new NAsmCompareInfo(val1, val2, val1 == val2);
      }
      if (rc != null) {
        NAsmEnvironment.statsEndMethod(this.getClass(), "compareFields");
        return rc;
      }
    }

    if (((fld1.getType().equals(NAsmFieldType.ALPHANUMERIC) //
        || fld1.getType().equals(NAsmFieldType.ALPHANUMERIC_DBCS) //
        || fld1.getType().equals(NAsmFieldType.ALPHANUMERIC_MIXED) //
    ) //
        && //
        (fld2.getType().equals(NAsmFieldType.ALPHANUMERIC) //
            || fld2.getType().equals(NAsmFieldType.ALPHANUMERIC_DBCS) //
            || fld2.getType().equals(NAsmFieldType.ALPHANUMERIC_MIXED) //
        )) // 
        //
        || //
        (fld1.getType().equals(NAsmFieldType.NUMERIC_BINARY) //
            //
            && //
            fld2.getType().equals(NAsmFieldType.ALPHANUMERIC) //
        ) //
        || //
        (fld1.getType().equals(NAsmFieldType.ALPHANUMERIC) //
            //
            && //
            fld2.getType().equals(NAsmFieldType.NUMERIC_BINARY) //
        ) //
    //
    ) {
      String val1 = fld1.getByteString();
      String val2 = fld2.getByteString();
      if (length == null && val1.length() != val2.length()) {
        //Compare always for the shortest length
        //when a length is not specified, to avoid
        //a wrong compare pass the length as parameter 
        int maxLength;
        if (val2.length() < val1.length()) {
          maxLength = val2.length();
        } else {
          maxLength = val1.length();
        }
        val1 = val1.substring(0, maxLength);
        val2 = val2.substring(0, maxLength);
        //        val1 = StringUtils.rightPad(val1, maxLength, " ");
        //        val2 = StringUtils.rightPad(val2, maxLength, " ");
      }
      if (length != null) {
        if (val1.length() > length) {
          val1 = val1.substring(0, length);
        }
        if (val2.length() > length) {
          val2 = val2.substring(0, length);
        }
      }
      int res = val1.compareTo(val2);
      if (condition.equals(Condition.GREATER)) {
        //return (res > 0);
        rc = new NAsmCompareInfo(res, res > 0);
      } else if (condition.equals(Condition.GREATER_EQUAL)) {
        //return (res >= 0);
        rc = new NAsmCompareInfo(res, res >= 0);
      } else if (condition.equals(Condition.LESS)) {
        //return (res < 0);
        rc = new NAsmCompareInfo(res, res < 0);
      } else if (condition.equals(Condition.LESS_EQUAL)) {
        //return (res <= 0);
        rc = new NAsmCompareInfo(res, res <= 0);
      } else if (condition.equals(Condition.EQUAL)) {
        //return (res == 0);
        rc = new NAsmCompareInfo(res, res == 0);
      }
      if (rc != null) {
        NAsmEnvironment.statsEndMethod(this.getClass(), "compareFields");
        return rc;
      }
    } else if ( //
    (fld1.getType().equals(NAsmFieldType.NUMERIC_PACKED) //
        || fld1.getType().equals(NAsmFieldType.NUMERIC_PACKED_UNSIGNED) //
        || fld1.getType().equals(NAsmFieldType.NUMERIC_BINARY)) //
        && //
        (fld2.getType().equals(NAsmFieldType.NUMERIC_PACKED) //
            || fld2.getType().equals(NAsmFieldType.NUMERIC_PACKED_UNSIGNED) //
            || fld2.getType().equals(NAsmFieldType.NUMERIC_BINARY))) {
      double val1;
      if (fld1.getType().equals(NAsmFieldType.NUMERIC_BINARY)) {
        val1 = fld1.getInt();
      } else {
        val1 = fld1.getNumeric();
      }
      double val2;
      if (fld2.getType().equals(NAsmFieldType.NUMERIC_BINARY)) {
        val2 = fld2.getInt();
      } else {
        val2 = fld2.getNumeric();
      }
      if (condition.equals(Condition.GREATER)) {
        rc = new NAsmCompareInfo(val1, val2, val1 > val2);
      } else if (condition.equals(Condition.GREATER_EQUAL)) {
        rc = new NAsmCompareInfo(val1, val2, val1 >= val2);
      } else if (condition.equals(Condition.LESS)) {
        rc = new NAsmCompareInfo(val1, val2, val1 < val2);
      } else if (condition.equals(Condition.LESS_EQUAL)) {
        rc = new NAsmCompareInfo(val1, val2, val1 <= val2);
      } else if (condition.equals(Condition.EQUAL)) {
        rc = new NAsmCompareInfo(val1, val2, val1 == val2);
      }
      if (rc != null) {
        NAsmEnvironment.statsEndMethod(this.getClass(), "compareFields");
        return rc;
      }
    }
    NAsmEnvironment.statsEndMethod(this.getClass(), "compareFields");
    throw new NAsmException(
        "NAsmField.compareFields, casting not supported: Var1(" + fld1.getName() + ":" + fld1.getType() + ") - Var2(" + fld2.getName() + ":" + fld2.getType() + ") - Condition (" + condition + ")");
  }

  /**
   * 
   * @param value
   *            the value to set
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void set(NAsmString value) throws NAsmException {
    this.set(value.toString());
  }

  /**
   * 
   * @param value
   *            the value to set
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void set(String value) throws NAsmException {
    this.set(value, 0);
  }

  /**
   * 
   * @param value
   *            the value to set
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void set(String value, int offset) throws NAsmException {
    NAsmField fld = this.getConstant(value);
    this.set(fld, offset, value.length());
  }

  /**
   * 
   * @param value
   *            the value to set natively (no casting or data conversion
   *            happening)
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void setByteString(String value) throws NAsmException {
    setByteString(value, 0);
  }

  /**
   * 
   * @param value
   *            the value to set natively (no casting or data conversion
   *            happening)
   * @param offset
   *            target offset
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void setByteString(String value, int offset) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "setByteString");
    if (value == null || value.length() == 0) {
      NAsmEnvironment.statsEndMethod(this.getClass(), "setByteString");
      return;
    }
    int len = value.length();
    byte[] _workingValue = this._value;
    int _workingAbsoluteOffset = this.getAbsoluteOffset();
    NAsmValue realValue = NAsmValue.resolveValue(this);
    if (realValue != null) {
      _workingValue = realValue.getValue();
      _workingAbsoluteOffset = realValue.getAbsoluteOffset();
    }
    System.arraycopy(value.getBytes(NAsmEnvironment.ENV_CHARSET()), 0, _workingValue, _workingAbsoluteOffset + offset, len);
    NAsmEnvironment.statsEndMethod(this.getClass(), "setByteString");
  }

  /**
   * setByteString version to use with a character as offset (EBCDIC Vs ASCII accounted)  
   * 
   * @param value
   *            the value to set natively (no casting or data conversion
   *            happening)
   * @param offset
   *            target offset (char)
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void setByteString(String value, char offset) throws NAsmException {
    setByteString(value, NAsmEnvironment.RUN_EBCDIC() ? NAsmStrings.getEbcdicSequence(offset) : (int) offset);
  }

  public void setByteArray(byte[] value) throws NAsmException {
    setByteArray(value, 0, value.length);
  }

  public void setByteArray(byte[] value, int offset) throws NAsmException {
    setByteArray(value, offset, value.length);
  }

  /**
   * 
   * @param value
   *            the value to set natively (no casting or data conversion
   *            happening)
   * @param offset
   *            target offset
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void setByteArray(byte[] value, int offset, Integer length) throws NAsmException {
    if (value == null || value.length == 0) {
      return;
    }
    int len;
    if (length == null) {
      len = this.getLength();
      if (value.length < len) {
        len = value.length;
      }
    } else {
      len = length;
    }
    byte[] _workingValue = this._value;
    int _workingAbsoluteOffset = this.getAbsoluteOffset();
    NAsmValue realValue = NAsmValue.resolveValue(this);
    if (realValue != null) {
      _workingValue = realValue.getValue();
      _workingAbsoluteOffset = realValue.getAbsoluteOffset();
    }
    System.arraycopy(value, 0, _workingValue, _workingAbsoluteOffset + offset, len);
  }

  /**
   * 
   * @param value to be moved
   * @param mask bit mask, move from the input buffer the bytes corresponding to the ON it
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void setByteStringWithMask(String value, int mask) throws NAsmException {
    setByteStringWithMask(value, mask, 0);
  }

  /**
   * 
   * @param reg
   * @param mask
   * @throws NAsmException
   */
  public void setByteStringWithMask(NAsmRegister reg, int mask) throws NAsmException {
    setByteStringWithMask(reg.getByteString(), mask, 0); //SQ TODO review - refer to "getByteString(false)"
  }

  /**
   * 
   * @param reg
   * @param mask
   * @throws NAsmException
   */
  public void setByteStringWithMask(NAsmRegister reg, int mask, int offset) throws NAsmException {
    setByteStringWithMask(reg.getByteString(), mask, offset);
  }

  /**
   * 
   * @param value to be moved
   * @param mask bit mask, move from the input buffer the bytes corresponding to the ON it
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void setByteStringWithMask(String value, int mask, int offset) throws NAsmException {
    String bits = Integer.toBinaryString(mask);
    if (bits.length() < 4) {
      bits = NAsmStrings.formatZeroes(bits, 4);
    }
    int len = 0;
    for (int idx = 0; idx < bits.length(); idx++) {
      if (bits.charAt(idx) == '1') {
        len++;
      }
    }
    if (len == 0) {
      return;
    }
    byte[] rc = new byte[len];
    int pos = 0;
    for (int idx = 0; idx < bits.length(); idx++) {
      if (bits.charAt(idx) == '1') {
        rc[pos] = value.getBytes(NAsmEnvironment.ENV_CHARSET())[idx];
        pos++;
      }
    }
    setByteArray(rc, offset, len);
  }

  /**
   * Set address
   * 
   * @param field
   *            on which setting the address up
   * @throws NAsmException
   */
  public void setAddress(NAsmField field) throws NAsmException {
    setAddress(field, 0);
  }

  /**
   * Set address
   * 
   * @param field
   *            on which setting the address up
   * @param addressOffset
   *            offset related to the address to set 
   * @throws NAsmException
   */
  public void setAddress(NAsmField field, int addressOffset) throws NAsmException {
    if (field == null) {
      throw new NAsmException("Field cannot be NULL");
    }
    NAsmAddress fa = field.getPointedAddress();
    if (field instanceof NAsmRegister && fa != null) {
      this.setPointedAddress(fa.getClone(fa.getOffset() + addressOffset));
    } else {
      this.setPointedAddress(new NAsmAddress(field, addressOffset));
    }
  }

  /**
   * SQtmp - TODO review names of methods and type of calls for these "setAddress" because NAsmRegister has
   * the same methods but using Integers (not working override)
   * 
   * Set address to use in case of offset in the "destination"
   * 
   * @param field
   *            on which setting the address up
   * @param addressOffset
   *            offset related to the address to set
   * @param destinationOffset
   *            offset related to the receiving field  
   *             
   * @throws NAsmException
   */
  public void setAddressWithOffset(NAsmField field, int addressOffset, int destinationOffset) throws NAsmException {
    if (field == null) {
      throw new NAsmException("Field cannot be NULL");
    }
    NAsmAddress fa = field.getPointedAddress();
    if (field instanceof NAsmRegister && fa != null) {
      this.setPointedAddress(fa.getClone(fa.getOffset() + addressOffset), destinationOffset);
    } else {
      this.setPointedAddress(new NAsmAddress(field, addressOffset), destinationOffset);
    }
  }

  /**
   * SQtmp - Set address for labels
   * 
   * @param int
   *            constant mapping the label
   * @throws NAsmException
   */
  public void setAddress(int mappedLabel) throws NAsmException {
    set(mappedLabel);
  }

  /**
   * Setup USING for this field, its _value
   * will be the one pointed by the register
   * 
   * @param register
   * @throws NAsmException
   */
  public void setUsing(NAsmRegister register) {
    setUsing(register, true);
  }

  public void setUsing(NAsmRegister register, boolean notifyRegister) {
    this._usingRegister = register;
    if (register != null && notifyRegister) {
      register.addUsingField(this);
    }
  }

  /**
   * Remove USING for this field
   * 
   * @throws NAsmException
   */
  protected void dropUsing() {
    this._usingRegister = null;
  }

  /**
   * @return the _usingRegister
   */
  public NAsmRegister getUsingRegister() {
    return this._usingRegister;
  }

  /**
   * 
   * @param value
   *            the value to set
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void set(NRECORD_PROPERTIES_CLASS value) throws NAsmException {
    NAsmField fld = this.getConstant(value.get());
    this.set(fld);
  }

  /**
   * 
   * @param value
   *            the value to set
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void set(Integer value) throws NAsmException {
    if (value == null) {
      this.set(0);
    } else {
      this.set(value.intValue());
    }
  }

  /**
   * 
   * @param value
   *            the value to set
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void set(int value) throws NAsmException {
    NAsmField fld = this.getConstant(value);
    this.set(fld);
  }

  /**
   * 
   * @param value
   *            the value to set
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void set(double value) throws NAsmException {
    if (Double.isNaN(value)) {
      value = 0;
    }
    //Round the value to the number of decimals of the receiver field
    if (this.getDecimals() > 0) {
      int roundDigits = 0;
      roundDigits = (int) Math.pow(10, this.getDecimals());
      value = (double) Math.round(value * roundDigits) / roundDigits;
    } else {
      value = Math.floor(value);
    }
    NAsmField fld = this.getConstant(value);
    this.set(fld);
  }

  public void set(long value) throws NAsmException {
    if (Double.isNaN(value)) {
      value = 0;
    }
    NAsmField fld = this.getConstant(value);
    this.set(fld);
  }

  /**
   * 
   * @param field
   *            the field to set.
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void set(NAsmField field) throws NAsmException {
    if (field instanceof NAsmRegister && field.getLength() > 4) {
      logger.warn("set from a shorter field to verify");
    }
    this.set(field, 0);
  }

  /**
   * 
   * @param field
   *            the field to set.
   * @param offset
   *            the offset to set.
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void set(NAsmField field, int offset) throws NAsmException {
    this.set(field, offset, null);
  }

  /**
   * 
   * @param field
   *            the field to set.
   * @param offset
   *            the offset to set.
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public void set(NAsmField field, int offset, boolean skipDismiss) throws NAsmException {
    this.set(field, offset, null, skipDismiss);
  }

  /**
   * 
   * @param field
   *            the field to set.
   * @param offset
   *            the offset to set.
   * @param length
   *            the length to set.
   * @throws NAsmException
   */
  public void set(NAsmField field, int offset, Integer length) throws NAsmException {
    this.set(field, offset, length, false);
  }

  //SQtmp - clone of correspondent "set" to manage signed Vs unsigned packed instructions
  public void setAsPackedUnsigned(NAsmField field, int offset) throws NAsmException {
    NAsmStrings.isUnsignedPacked = true;
    this.set(field, offset, null);
    NAsmStrings.isUnsignedPacked = false;
  }
  
  /**
   * 
   * @param field
   *            the field to set using like feature.
   * @param forceFromfile
   *            specifies if the field is being set from a file read.
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  private void set(NAsmField field, int offset, Integer length, boolean skipDismiss) throws NAsmException {
    try {
      NAsmEnvironment.statsStartMethod(this.getClass(), "set");
      int totalLength = this.getLengthBytes();
      if (this.getArray() != null && this.getArray() > 0) {
        totalLength = this.getLengthBytes() * this.getArray();
      }
      this.setStoragePointer(false);
      NAsmAddress pa = field.getPointedAddress();
      NAsmAddress oldValue = null;
      if (totalLength == 4 && !skipDismiss) {
        oldValue = this.getPointedAddress(offset, 4);
      }
      if (pa != null) {
        //See if it's the same address, if so, ignore it
        //Santix: removed it doesn't work for cases like this where the address is not cloned: 
        //         ASM:01200          MVC   LCRWORK,LCRADDR         ADDR OF GETMAIN TABLE OF FLDS
        //        if (oldValue == null) {
        //          oldValue = this.getPointedAddress(offset, 4);
        //        }
        //        if (oldValue != null) {
        //          if (pa.getAddressedField().getId() ==  oldValue.getAddressedField().getId()) {
        //            oldValue.setOffset(pa.getOffset());
        //            NAsmEnvironment.statsEndMethod(this.getClass(), "set");
        //            return;
        //          }
        //        }
        if (NAsmEnvironment.isStoreAddresses()) {
          NAsmAddress addr = new NAsmAddress(pa.getAddressedField(), pa.getOffset());
          addr.setSourceField(this);
          addr.setSourceOffset(offset);
          NAsmEnvironment.addStoredAddress(addr);
        }
        
        pa = pa.getClone(pa.getOffset());
        this.setPointedAddress(pa, offset);
        pa.setSourceField(this);
        pa.setSourceOffset(offset);
        this.setStoragePointer(pa.getAddressedField().isStorage() && pa.getAddressedField().getProgramInstance() != null);
        if (oldValue != null && !skipDismiss) {
          if (pa.getSourceField() != null //
              && oldValue.getSourceField() != null //
              && pa.getSourceField().equals(oldValue.getSourceField()) //
              && pa.getAddressedField().getId() == oldValue.getAddressedField().getId()) {
            if (oldValue.getMemoryId() == 85) {
              logger.debug("");
            }
            NAsmEnvironment.dismissAddress(oldValue.getAddrKey(), oldValue.getAddrKeyBytes());
          }
        }
        NAsmEnvironment.statsEndMethod(this.getClass(), "set");
        return;
      }
      if (oldValue != null) {
        if (!this.isDsect() /* && field.isConstant() */) {
          NAsmEnvironment.dismissAddress(oldValue.getAddrKey(), oldValue.getAddrKeyBytes());
        }
      }
      //Optimization, same data type, same length, no casting logic
      if (this.getType().equals(field.getType()) //
          && this.getLength() == field.getLength() //
      ) {
        this.setValue(field.getValue(), offset);
        //SQ important - Other elements of the array must be set. i.e.  "DC    5C' ' | DC 3A(0) | 2PL3'0'" 
        if (totalLength > this.getLengthBytes()) {
          //just a safety check for now:
          if (field.isConstant()) { //SQ TODO review non-constant cases
            if (offset == 0) {
              for (int i = 1; i < this.getArray(); i++) {
                this.setValue(field.getValue(), i * this.getLengthBytes());
              }
            } else {
              //logger.debug(""); //just a debug catcher
            }
          } else {
            //logger.debug(""); //just a debug catcher
          }
        }
      } else if (this.getType().equals(NAsmFieldType.ALPHANUMERIC) //
          || this.getType().equals(NAsmFieldType.ALPHANUMERIC_DBCS) //
          || this.getType().equals(NAsmFieldType.ALPHANUMERIC_MIXED)) {
        byte[] buffer = field.getValue();
        //Take the shorter length between the input field and the receiver
        int maxLen;
        if (length != null) {
          maxLen = length;
          int tmpLen = 0;
          if (totalLength < field.getLengthBytes()) {
            tmpLen = this.getLength();
          } else {
            tmpLen = field.getLength();
          }
          if (tmpLen != maxLen) {
            tmpLen = maxLen;
          }
        } else {
          if (totalLength < field.getLengthBytes()) {
            maxLen = this.getLength();
          } else {
            maxLen = field.getLength();
          }
        }
        if (maxLen == 0) {
          logger.warn("Operation executed using length zero");
          return;
        }
        this.setByteArray(buffer, offset, maxLen); //SQtmp to verify - to verify also maxLen
      } else if (this.getType().equals(NAsmFieldType.NUMERIC_PACKED)) {
        //SQ TODO security check to remove once we verified all the scenarios ...
        double toolValue = field.getNumeric();
        double sqValue = 0;
        try {
          sqValue = field.getPackedAtRange(0, field.getLength());
          if (toolValue != sqValue) {
            throw new NAsmException("\n\n **** set - security check ****\n\n");
          }
        } catch (Exception e) {
        }
        byte[] packedValue = NAsmStrings.packData(field.getNumeric(), this.getLengthDigits(), this.decimals, true);
        this.setValue(packedValue, offset);
      } else if (this.getType().equals(NAsmFieldType.NUMERIC_PACKED_UNSIGNED)) {
        byte[] packedValue = NAsmStrings.packData(field.getNumeric(), this.getLengthDigits(), this.decimals, false);
        this.setValue(packedValue, offset);
      } else if (this.getType().equals(NAsmFieldType.NUMERIC_BINARY)) {
        byte[] binaryValue = NAsmStrings.valueToBinaryBigEndian(field.getNumeric(), this.length);
        this.setValue(binaryValue, offset);
      } else {
        throw new NAsmException("Method not supported NAsmField.set or type: " + this.getType());
      }
      NAsmEnvironment.statsEndMethod(this.getClass(), "set");
    } catch (Exception ex) {
      String value = "null";
      byte[] _workingValue = field._value;
      NAsmValue realValue = NAsmValue.resolveValue(this);
      if (realValue != null) {
        _workingValue = realValue.getValue();
      }
      if (_workingValue != null) {
        value = new String(_workingValue, NAsmEnvironment.ENV_CHARSET());
      }
      if (ex instanceof NAsmException) {
        throw (NAsmException) ex;
      }
      String dump = dumpRecord();
      throw new NAsmException("Invalid value format (" + value + ") for field (" //
          + this.getName() + ") absolute offset (" + this.getAbsoluteOffset() //
          + "), hex value: 0x" + NAsmStrings.toHex(value) + dump);
    }
  }

  /**
   * 
   * @return
   */
  public boolean isConstant() {
    return this.name.equals(_CONSTANT);
  }

  /**
   * Get a numeric representation of the NAsmField value.
   * 
   * @return a numeric representation of the value
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public double getNumeric() throws NAsmException {
    //SQtmp bad behavior in case in case of array;
    //To be safe let's change for now only those cases.
    if (this.getArray() != null && this.getArray() > 0 && this.getLength() > 0) {
      return getNumeric(this.getValue(null, this.getLength()));
    } else {
      return getNumeric(this.getValue());
    }
  }

  //SQ long version - TODO consolidate methods
  public long getNumeric_L() throws NAsmException {
    //SQtmp bad behavior in case in case of array;
    //To be safe let's change for now only those cases.
    if (this.getArray() != null && this.getArray() > 0 && this.getLength() > 0) {
      return getNumeric_L(this.getValue(null, this.getLength()));
    } else {
      return getNumeric_L(this.getValue());
    }
  }

  protected double getNumeric(byte[] byteBuffer) throws NAsmException {
    if (byteBuffer == null) {
      return 0;
    }
    if (this.getType().equals(NAsmFieldType.NUMERIC_PACKED)) {
      double rc = 0;
      try {
        rc = NAsmStrings.unpackData(byteBuffer, this.getDecimals());
      } catch (Exception ex) {
        String dump = dumpRecord();
        String message = "Invalid numeric signed packed value for field: " + this.getName() + " offset: " + this.getAbsoluteOffset() + ", hex value: 0x" + NAsmStrings.toHex(byteBuffer);
        //Emit a warning
        if (NAsmEnvironment.ALLOW_NUMERIC_MALFORMED()) {
          message += ", value will be converted to zero" + dump;
          NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.WARNING, message);
          return 0;
        }
        message += dump;
        // Invalid value
        throw new NAsmException(message);
      }
      return rc;
    } else if (this.getType().equals(NAsmFieldType.NUMERIC_PACKED_UNSIGNED)) {
      double rc = 0;
      try {
        rc = NAsmStrings.unpackData(byteBuffer, this.getDecimals());
      } catch (Exception ex) {
        String dump = dumpRecord();
        String message = "Invalid numeric unsigned packed value for field: " + this.getName() + " offset: " + this.getAbsoluteOffset() + ", hex value: 0x" + NAsmStrings.toHex(byteBuffer);
        //Emit a warning
        if (NAsmEnvironment.ALLOW_NUMERIC_MALFORMED()) {
          message += ", value will be converted to zero" + dump;
          NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.WARNING, message);
          return 0;
        }
        message += dump;
        // Invalid values return zero
        throw new NAsmException(message);
      }
      return rc;
    } else if (this.getType().equals(NAsmFieldType.NUMERIC_BINARY)) {
      double rc = 0;
      try {
        rc = NAsmStrings.binaryBigEndianToValue(byteBuffer, this.getLength());
      } catch (Exception ex) {
        String dump = dumpRecord();
        String message = "Invalid numeric binary value for field: " + this.getName() + " offset: " + this.getAbsoluteOffset() + ", hex value: 0x" + NAsmStrings.toHex(byteBuffer) + dump;
        //Emit a warning
        if (NAsmEnvironment.ALLOW_NUMERIC_MALFORMED()) {
          message += ", value will be converted to zero" + dump;
          NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.WARNING, message);
          return 0;
        }
        message += dump;
        throw new NAsmException(message);
      }
      return rc;
    } else {
      double val = 0;
      try {
        String value;
        if (NAsmEnvironment.RUN_EBCDIC()) {
          //SQtmp - fix sign
          if ((byteBuffer[byteBuffer.length - 1] & 0xF0) == 0xC0) {
            byteBuffer[byteBuffer.length - 1] = (byte) (byteBuffer[byteBuffer.length - 1] | 0xF0);
          }
          byte[] byteBufferAscii = NAsmStrings.convertBytesToASCII(byteBuffer);
          value = new String(byteBufferAscii, NAsmEnvironment.ENV_CHARSET());
        } else {
          value = new String(byteBuffer, NAsmEnvironment.ENV_CHARSET());
        }
        //Blanks in the numeric fields will end up to a value zero
        if (value.trim().isEmpty()) {
          val = 0;
        } else {
          if (this.getDecimals() > 0) {
            int intPart = this.getLength() - this.getDecimals();
            //Despite the Locale has a comma for decimal separator
            //java for doubles uses always a dot
            value = value.substring(0, intPart) + "." + value.substring(intPart);
          }
          val = Double.valueOf(value);
          //          if (this.isNegative()) {
          //            val = val * -1;
          //          }
        }
      } catch (Exception ex) {
        String dump = dumpRecord();
        String message = "Invalid numeric value for field: " + this.getName() + " offset: " + this.getAbsoluteOffset() + ", hex value: 0x" + NAsmStrings.toHex(byteBuffer) + dump;
        //Emit a warning
        if (NAsmEnvironment.ALLOW_NUMERIC_MALFORMED()) {
          message += ", value will be converted to zero" + dump;
          NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.WARNING, message);
          return 0;
        }
        message += dump;
        throw new NAsmException(message);
      }
      return val;
    }
  }

  //SQtmp long version
  protected long getNumeric_L(byte[] byteBuffer) throws NAsmException {
    if (byteBuffer == null) {
      return 0;
    }
    if (this.getType().equals(NAsmFieldType.NUMERIC_PACKED)) {
      long rc = 0; //SQtmp
      try {
        rc = NAsmStrings.unpackData_L(byteBuffer, this.getDecimals()); //SQtmp
      } catch (Exception ex) {
        String dump = dumpRecord();
        String message = "Invalid numeric signed packed value for field: " + this.getName() + " offset: " + this.getAbsoluteOffset() + ", hex value: 0x" + NAsmStrings.toHex(byteBuffer);
        //Emit a warning
        if (NAsmEnvironment.ALLOW_NUMERIC_MALFORMED()) {
          message += ", value will be converted to zero" + dump;
          NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.WARNING, message);
          return 0;
        }
        message += dump;
        // Invalid value
        throw new NAsmException(message);
      }
      return rc;
    } else if (this.getType().equals(NAsmFieldType.NUMERIC_PACKED_UNSIGNED)) {
      long rc = 0; //SQ
      try {
        //rc = NAsmStrings.unpackData(byteBuffer, this.getDecimals(), false);
        rc = NAsmStrings.unpackData_L(byteBuffer, this.getDecimals()); //SQtmp
      } catch (Exception ex) {
        String dump = dumpRecord();
        String message = "Invalid numeric unsigned packed value for field: " + this.getName() + " offset: " + this.getAbsoluteOffset() + ", hex value: 0x" + NAsmStrings.toHex(byteBuffer);
        //Emit a warning
        if (NAsmEnvironment.ALLOW_NUMERIC_MALFORMED()) {
          message += ", value will be converted to zero" + dump;
          NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.WARNING, message);
          return 0;
        }
        message += dump;
        // Invalid values return zero
        throw new NAsmException(message);
      }
      return rc;
    } else if (this.getType().equals(NAsmFieldType.NUMERIC_BINARY)) {
      long rc = 0; //SQ
      try {
        rc = NAsmStrings.binaryBigEndianToValue(byteBuffer, this.getLength());
      } catch (Exception ex) {
        String dump = dumpRecord();
        String message = "Invalid numeric binary value for field: " + this.getName() + " offset: " + this.getAbsoluteOffset() + ", hex value: 0x" + NAsmStrings.toHex(byteBuffer) + dump;
        //Emit a warning
        if (NAsmEnvironment.ALLOW_NUMERIC_MALFORMED()) {
          message += ", value will be converted to zero" + dump;
          NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.WARNING, message);
          return 0;
        }
        message += dump;
        throw new NAsmException(message);
      }
      return rc;
    } else {
      long val = 0; //SQ
      try {
        String value;
        if (NAsmEnvironment.RUN_EBCDIC()) {
          //SQtmp - fix sign
          if ((byteBuffer[byteBuffer.length - 1] & 0xF0) == 0xC0) {
            byteBuffer[byteBuffer.length - 1] = (byte) (byteBuffer[byteBuffer.length - 1] | 0xF0);
          }
          byte[] byteBufferAscii = NAsmStrings.convertBytesToASCII(byteBuffer);
          value = new String(byteBufferAscii, NAsmEnvironment.ENV_CHARSET());
        } else {
          value = new String(byteBuffer, NAsmEnvironment.ENV_CHARSET());
        }
        //Blanks in the numeric fields will end up to a value zero
        if (value.trim().isEmpty()) {
          val = 0;
        } else {
          if (this.getDecimals() > 0) {
            int intPart = this.getLength() - this.getDecimals();
            //Despite the Locale has a comma for decimal separator
            //java for doubles uses always a dot
            value = value.substring(0, intPart) + "." + value.substring(intPart);
          }
          //val = Double.valueOf(value); //SQ this native method corrupts big numbers!
          val = Long.valueOf(value.trim());
        }
      } catch (Exception ex) {
        String dump = dumpRecord();
        String message = "Invalid numeric value for field: " + this.getName() + " offset: " + this.getAbsoluteOffset() + ", hex value: 0x" + NAsmStrings.toHex(byteBuffer) + dump;
        //Emit a warning
        if (NAsmEnvironment.ALLOW_NUMERIC_MALFORMED()) {
          message += ", value will be converted to zero" + dump;
          NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.WARNING, message);
          return 0;
        }
        message += dump;
        throw new NAsmException(message);
      }
      return val;
    }
  }

  /**
   * 
   * @return An int representation of the field value.
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public int getInt() throws NAsmException {
    byte[] byteBuffer = this.getValue();
    if (byteBuffer == null) {
      return 0;
    }
    if (this.getType().equals(NAsmFieldType.NUMERIC_PACKED) //
        || this.getType().equals(NAsmFieldType.NUMERIC_PACKED_UNSIGNED)) {
      double rc = 0;
      try {
        rc = NAsmStrings.unpackData(byteBuffer, this.getDecimals());
      } catch (Exception ex) {
        // Invalid values return zero
        throw new NAsmException("Invalid numeric unsigned packed value for field: " + this.getName() + ", hex value: 0x" + NAsmStrings.toHex(byteBuffer));
      }
      return (int) rc;
      //    } else if (this.getType().equals(NAsmFieldType.NUMERIC_BINARY)) {
      //Anything else is expected binary
    } else {
      double rc = 0;
      try {
        rc = NAsmStrings.binaryBigEndianToValue(byteBuffer, this.getLength());
      } catch (Exception ex) {
        // Invalid values return zero
        throw new NAsmException("Invalid numeric binary value for field: " + this.getName() + ", hex value: 0x" + NAsmStrings.toHex(byteBuffer) + ", error: " + ex.getMessage());
      }
      return (int) rc;
    }
  }

  /**
   * Dump the current record
   * 
   * @return
   */
  private String dumpRecord() {
    try {
      NAsmField parent = this.getParent();
      if (parent == null) {
        parent = this;
      } else {
        while (parent.getParent() != null) {
          parent = parent.getParent();
        }
      }
      byte[] recBuff = parent.getValue();
      String vp = new String(recBuff, NAsmEnvironment.ENV_CHARSET()).substring(0, parent.getLengthBytes());
      int off = this.getAbsoluteOffset();
      int len = this.getLengthBytes();
      if (this.getArray() != null && this.getArray() > 0) {
        len = this.getLengthBytes() * this.getArray();
      }

      byte[] dumpBuffer = recBuff;
      if (vp.length() > 256) {
        int start = off - 10;
        int end = off + len + 10;
        if (start <= 0) {
          start = 0;
          off = 0;
        } else {
          off = 10;
        }
        dumpBuffer = Arrays.copyOfRange(recBuff, start, end);
        vp = new String(dumpBuffer, NAsmEnvironment.ENV_CHARSET());
      }

      return dumpRecord(dumpBuffer, vp, vp.length(), off, len);
    } catch (Exception e) {
      return "";
    }
  }

  protected String dumpRecord(byte[] recBuff, String recBuffString, int recLength, int offField, int lenField) {
    try {
      String outRecS = "";
      String outRecH = "";
      String outRecP = "";
      for (int idx = 0; idx < recLength; idx++) {
        String s = recBuffString.substring(idx, idx + 1);
        char c = s.toCharArray()[0];
        if (Character.isISOControl(c)) {
          s = ".";
        }
        outRecS += " " + s;
        outRecH += NAsmStrings.toHex(new byte[] { recBuff[idx] });
        if (idx >= offField && idx <= (offField + lenField - 1)) {
          outRecP += "^^";
        } else {
          outRecP += "__";
        }
      }
      String rc = NAsmEnvironment.NL() + //
          "Record :" + outRecS + NAsmEnvironment.NL() + //
          "Hex 0x :" + outRecH + NAsmEnvironment.NL() + //
          "Offset :" + outRecP + NAsmEnvironment.NL();
      return rc;
    } catch (Exception e) {
      return "";
    }
  }

  /**
   * 
   * @return An HEX representation of the whole record with an highlight on
   *         the field value.
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public String getRecordHex() throws NAsmException {
    String dump = dumpRecord();
    return dump;
  }

  /**
   * 
   * @return An HEX representation of the field value.
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public String getHexValue() throws NAsmException {
    String str = new String(this.getValue(), NAsmEnvironment.ENV_CHARSET());
    return "0x" + NAsmStrings.toHex(str.getBytes(NAsmEnvironment.ENV_CHARSET()));
  }

  /**
   * 
   * @param index
   * @return
   * @throws NAsmException
   */
  public NAsmField getAtIndex(int index) throws NAsmException {
    int offset = index * this.getLength();
    NAsmField f = new NAsmField(this, this.getName(), this.getType(), offset, this.getLength());
    return f;
  }

  private static DecimalFormat DECIMAL_FORMAT;
  static {
    DECIMAL_FORMAT = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
  }

  /**
   * 
   * @return a value using the EBCDIC charset if it is setup
   * @throws NAsmException
   */
  public String getByteString() throws NAsmException {
    return getByteString(this.getValue(), null);
  }

  /**
   * 
   * @return a value using the EBCDIC charset if it is setup
   * @throws NAsmException
   */
  public String getByteString(byte[] value, Integer length) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "getByteString");
    String rc = null;
    if (value != null) {
      if (length == null) {
        byte[] byteBuffer = getByteArray(value);
        rc = new String(byteBuffer, NAsmEnvironment.ENV_CHARSET());
      } else {
        rc = new String(value, NAsmEnvironment.ENV_CHARSET());
      }
    }
    NAsmEnvironment.statsEndMethod(this.getClass(), "getByteString");
    return rc;
  }

  /**
   * 
   * @param offset
   * @param length
   * @return
   * @throws NAsmException
   */
  public String getByteString(int offset, int length) throws NAsmException {
    return getByteString(this.getValue(offset, length), length);
  }

  /**
   * 
   * @param offset
   * @param length
   * @return
   * @throws NAsmException
   */
  public byte[] getByteArray(int offset, int length) throws NAsmException {
    //SQ TODO we don't want to truncate: test the following:
    //return this.getValue(offset, length);
    return getByteArray(this.getValue(offset, length));
  }

  /**
   * 
   * @param value native buffer value
   * @return a byte array using the EBCDIC charset if it is setup
   * @throws NAsmException
   */
  public byte[] getByteArray(byte[] value) throws NAsmException {
    if (value == null) {
      return null;
    }
    int totalLength;
    //Take the whole buffer if it is a register
    if (this instanceof NAsmRegister) {
      totalLength = value.length;
    } else {
      totalLength = this.getLengthBytes();
      if (this.getArray() != null && this.getArray() > 0) {
        totalLength = this.getLengthBytes() * this.getArray();
      }

    }

    byte[] byteBuffer;
    if (totalLength > 0 && totalLength < value.length) {
      if (false && NAsmEnvironment.DEBUG()) { //SQ TODO review: we are truncating here - refer to comments in getByteArray(int,int)
        logger.debug("\n\n\n[NAsmField] getByteArray truncated value to verify - field: " + this.getName() + "\n\n\n"); //debug catcher
      }
      byteBuffer = Arrays.copyOfRange(value, 0, totalLength);
    } else {
      byteBuffer = value;
    }
    return byteBuffer;
  }

  /**
   * 
   * @return a value using the environment charset
   * @throws NAsmException
   */
  public NAsmString getString() throws NAsmException {
    return getString(false);
  }

  /**
   * 
   * @return An String representation of the field value.
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public NAsmString getString(boolean getNativeCharset) throws NAsmException {
    return getString(this.getValue(), getNativeCharset);
  }

  /**
   * 
   * @param offset
   * @param length
   * @return
   * @throws NAsmException
   */
  public NAsmString getString(int offset, int length) throws NAsmException {
    return getString(this.getValue(offset, length), length, false);
  }

  protected NAsmString getString(byte[] value, boolean getNativeCharset) throws NAsmException {
    return getString(value, null, getNativeCharset);
  }

  /**
   * 
   * @return An String representation of the field value.
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  protected NAsmString getString(byte[] value, Integer length, boolean getNativeCharset) throws NAsmException {
    try {
      int totalLength;
      if (length == null) {
        totalLength = this.getLengthBytes();
        if (this.getArray() != null && this.getArray() > 0) {
          totalLength = this.getLengthBytes() * this.getArray();
        }
      } else {
        totalLength = length;
      }
      if (this.getType().equals(NAsmFieldType.ALPHANUMERIC) || this.getType().equals(NAsmFieldType.ALPHANUMERIC_DBCS) || this.getType().equals(NAsmFieldType.ALPHANUMERIC_MIXED)) {
        if (value == null) {
          return null;
        }
        byte[] byteBuffer;
        if (totalLength > 0 && totalLength < value.length) {
          byteBuffer = Arrays.copyOfRange(value, 0, totalLength);
        } else {
          byteBuffer = value;
        }
        if (getNativeCharset) {
          return new NAsmString(byteBuffer, NAsmEnvironment.ENV_CHARSET());
        } else {
          if (NAsmEnvironment.RUN_EBCDIC()) {
            byte[] byteBufferAscii = NAsmStrings.convertBytesToASCII(byteBuffer);
            return new NAsmString(byteBufferAscii, NAsmEnvironment.ENV_CHARSET());
          } else {
            return new NAsmString(byteBuffer, NAsmEnvironment.ENV_CHARSET());
          }
        }
      } else {
        double numVal = this.getNumeric();
        Double vd = this.round(Math.abs(numVal), this.decimals);
        DECIMAL_FORMAT.setMaximumFractionDigits(this.decimals);
        DECIMAL_FORMAT.setMinimumFractionDigits(this.decimals);
        NAsmString rc = new NAsmString(DECIMAL_FORMAT.format(vd));
        if (this.getType().equals(NAsmFieldType.NUMERIC_PACKED) //
            || this.getType().equals(NAsmFieldType.NUMERIC_PACKED_UNSIGNED)) {
          //Format on a all zeroes mask
          //int maskLen = (this.getLengthDigits() > rc.length() ? this.getLengthDigits() : rc.length());
          //For Assembler it will format the STRING representation
          //using the number of bytes available, if we change this behavior
          //UNPK instructions need to be amended
          int maskLen = (this.getLengthBytes() > rc.length() ? this.getLengthBytes() : rc.length());
          NAsmString mask = new NAsmString("");
          if (numVal < 0) {
            maskLen = maskLen - rc.length();
            if (maskLen > 0) {
              mask = new NAsmString(NAsmStrings.repeatCharacter(' ', maskLen) + rc);
            }
          } else {
            mask = new NAsmString(NAsmStrings.formatZeroes(rc.toString(), maskLen));
          }
          return mask;
        } else {
          return rc;
        }
      }
    } catch (Exception ex) {
      //ex.printStackTrace();
      if (ex instanceof NAsmException) {
        throw (NAsmException) ex;
      }
      NAsmException eex = new NAsmException("Value for field '" + this.getName() + "' cannot be cast to String, error: " + ex.getMessage());
      eex.setJavaStack(ex);
      throw eex;
    }

  }

  /**
   * getStrings version without numeric format (TODO review default getStrings)
   * 
   * @param offset
   * @param length
   * @return
   * @throws NAsmException
   */
  public NAsmString getChars(int offset, int length) throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      return pa.getAddressedField().getChars(pa.getOffset() + offset, length);
    } else {
      byte[] byteBuffer = this.getValue(offset, length);
      if (NAsmEnvironment.RUN_EBCDIC()) {
        byte[] byteBufferAscii = NAsmStrings.convertBytesToASCII(byteBuffer);
        return new NAsmString(byteBufferAscii, NAsmEnvironment.ENV_CHARSET());
      } else {
        return new NAsmString(byteBuffer, NAsmEnvironment.ENV_CHARSET());
      }
    }
  }

  /**
   * 
   * @return <b>true</b> if the field is numeric <br>
   *         <b>false</b> if the field is not numeric
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean isNumeric() throws NAsmException {
    try {
      if (this.getValue() == null) {
        return false;
      }
      if (this.getType().equals(NAsmFieldType.NUMERIC_PACKED) //
          || this.getType().equals(NAsmFieldType.NUMERIC_PACKED_UNSIGNED)) {
        try {
          NAsmStrings.unpackData(this.getValue(), this.getDecimals());
        } catch (Exception ex) {
          return false;
        }
        return true;
      } else if (this.getType().equals(NAsmFieldType.NUMERIC_BINARY)) {
        try {
          NAsmStrings.binaryBigEndianToValue(this.getValue(), this.getLength());
        } catch (Exception ex) {
          return false;
        }
        return true;
      } else {
        return this.isNumeric(this.getString().toString());
      }

    } catch (Exception ex) {
      NAsmException eex = new NAsmException("NAsmField.isNumeric() runtime error: " + ex.getMessage());
      eex.setJavaStack(ex);
      throw eex;
    }
  }

  /**
   * 
   * @return <b>true</b> if the field is all spaces <br>
   *         <b>false</b> if the field is not all spaces
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean isSpace() throws NAsmException {
    try {
      if (this.getValue() == null) {
        return false;
      }
      return this.isSpace(this.getString().toString());
    } catch (Exception ex) {
      NAsmException eex = new NAsmException("NAsmField.isSpace() runtime error: " + ex.getMessage());
      eex.setJavaStack(ex);
      throw eex;
    }
  }

  /**
   * 
   * @return <b>true</b> if the field is all high-values <br>
   *         <b>false</b> if the field is not all high-values
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean isHighValue() throws NAsmException {
    try {
      if (this.getValue() == null) {
        return false;
      }
      return this.isHighValue(this.getByteString());
    } catch (Exception ex) {
      NAsmException eex = new NAsmException("NAsmField.isHighValue() runtime error: " + ex.getMessage());
      eex.setJavaStack(ex);
      throw eex;
    }
  }

  /**
   * 
   * @return <b>true</b> if the field is all low-values <br>
   *         <b>false</b> if the field is not all low-values
   * @throws NAsmException
   *             if there is an NAsmRuntime specific issue
   */
  public boolean isLowValue() throws NAsmException {
    try {
      if (this.getValue() == null) {
        return false;
      }
      return this.isLowValue(this.getByteString());
    } catch (Exception ex) {
      NAsmException eex = new NAsmException("NAsmField.isLowValue() runtime error: " + ex.getMessage());
      eex.setJavaStack(ex);
      throw eex;
    }
  }

  /**
   * @return the absolute offset of the field within its declaration area.
   */
  public int getAbsoluteOffset() {
    if (this.absoluteOffset == null) {
      this.absoluteOffset = Integer.valueOf(0);
      int ofs = this.offset;
      NAsmField parent = this.getParent();
      if (parent != null) {
        ofs += parent.getOffset();
        parent = parent.getParent();
        while (parent != null) {
          ofs += parent.getOffset();
          parent = parent.getParent();
        }
      }
      this.absoluteOffset = ofs;
    }
    return this.absoluteOffset;
  }

  /**
   * @return the vector of fields
   */
  protected Vector<NAsmField> getChildrenFields() {
    return vFields;
  }

  /**
   * 
   * @param field
   *            the field to set
   * @return the field added
   */
  public NAsmField addField(NAsmField field) {
    this.vFields.add(this.vFields.size(), field);
    this.hFields.put(field.getName(), field);
    return field;
  }

  private boolean isNumeric(String value) throws Exception {
    if (value == null) {
      return false;
    }
    String validChars = "0123456789";
    boolean isNumeric = true;
    boolean atLeastADigit = false;
    for (int idx = 0; idx < value.length() && isNumeric; idx++) {
      char c = value.charAt(idx);
      if (validChars.indexOf(c) == -1) {
        if (idx == 0 && (value.charAt(idx) == '+' || value.charAt(idx) == '-')) {

        } else {
          return false;
        }
      } else {
        atLeastADigit = true;
      }
    }
    return atLeastADigit;
  }

  /**
   * 
   * @param value
   *            the value to set
   * @return
   */
  private boolean isSpace(String value) throws Exception {
    if (value == null) {
      return false;
    }
    for (int idx = 0; idx < value.length(); idx++) {
      char c = value.charAt(idx);
      if (c != ' ') {
        return false;
      }
    }
    return true;
  }

  /**
   * 
   * @param value to be tested
   * @return true if the value is all high values
   * @throws Exception
   */
  private boolean isHighValue(String value) throws NAsmException {
    if (value == null) {
      return false;
    }
    for (int idx = 0; idx < value.length(); idx++) {
      int hexValue = value.charAt(idx);
      if (hexValue != 255) {
        return false;
      }
    }
    return true;
  }

  /**
   * 
   * @param value to be tested
   * @return true if the value is all low values
   * @throws Exception
   */
  private boolean isLowValue(String value) throws NAsmException {
    if (value == null) {
      return false;
    }
    for (int idx = 0; idx < value.length(); idx++) {
      int hexValue = value.charAt(idx);
      if (hexValue != 0) {
        return false;
      }
    }
    return true;
  }

  public byte[] getValue() throws NAsmException {
    return getValue(null, null);
  }

  public byte[] getValue(Integer offset, Integer length) throws NAsmException {
    return getValue(offset, length, false);
  }

  /**
   * @return the value
   */
  public byte[] getValue(Integer offset, Integer length, boolean extractNative) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "getValue");
    byte[] _workingValue = this._value;
    int _workingAbsoluteOffset = this.getAbsoluteOffset();
    if (!extractNative) {
      NAsmValue realValue = NAsmValue.resolveValue(this);
      if (realValue != null) {
        _workingValue = realValue.getValue();
        _workingAbsoluteOffset = realValue.getAbsoluteOffset();
      }
    }
    if (offset != null) {
      _workingAbsoluteOffset += offset;
    }
    if (this.getParent() == null) {
      if (length != null && length < this.getLengthBytes()) {
        byte[] b = new byte[length];
        //It's reading out of the boundary memory, on the mainframe
        //running in protected memory it won't abend but will return
        //an all zero binaries chunk of memory
        int bound = _workingAbsoluteOffset + length;
        if (bound > _workingValue.length) {
          String label = NAsmEnvironment.getCallerReference();
          if (label.isEmpty()) {
            label = NAsmEnvironment.getCurrentInstance().getProgramId();
          }
          NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.SEVERE,
              label + " -> attempted access beyond bounds of memory, offset " + _workingAbsoluteOffset + ", length " + length + ",  area length: " + _workingValue.length);
          return b;
        }
        if (length == 0) {
          throw new NAsmException("Field len to check for " + this.getName()); //SQtmp debug catcher
        }
        System.arraycopy(_workingValue, _workingAbsoluteOffset, b, 0, length);
        NAsmEnvironment.statsEndMethod(this.getClass(), "getValue");
        return b;
      } else {
        //return this._value;
        //return _workingValue;
        if (_workingAbsoluteOffset > 0) {
          if (length == null || length == 0) {
            throw new NAsmException("Field len to check for " + this.getName()); //SQtmp debug catcher
          }
          byte[] b = new byte[length];
          System.arraycopy(_workingValue, _workingAbsoluteOffset, b, 0, length);
          NAsmEnvironment.statsEndMethod(this.getClass(), "getValue");
          return b;
        } else {
          if (length == null) {
            length = this.getLengthBytes();
          }
          byte[] b = new byte[length];
          if (length == 0) {
            throw new NAsmException("Field len to check for " + this.getName()); //SQtmp debug catcher
          }
          System.arraycopy(_workingValue, 0, b, 0, length);
          NAsmEnvironment.statsEndMethod(this.getClass(), "getValue");
          return b;
        }
      }
    } else {
      try {
        int totalLength = this.getLengthBytes();
        if (this.getArray() != null && this.getArray() > 0) {
          totalLength = this.getLengthBytes() * this.getArray(); //SQ TODO review here - we must not consider the whole length in many cases! 
        }
        if (length != null) {
          totalLength = length;
        }
        if (totalLength == 0) {
          throw new NAsmException("Field len to check for " + this.getName()); //SQtmp debug catcher
        }
        byte[] b = new byte[totalLength];
        //System.arraycopy(this._value, this.getAbsoluteOffset(), b, 0, totalLength);
        System.arraycopy(_workingValue, _workingAbsoluteOffset, b, 0, totalLength);
        NAsmEnvironment.statsEndMethod(this.getClass(), "getValue");
        return b;
      } catch (Exception ex) {
        NAsmEnvironment.statsEndMethod(this.getClass(), "getValue");
        NAsmException eex = new NAsmException("NAsmField.getValue() runtime error: " + ex.getMessage());
        eex.setJavaStack(ex);
        throw eex;
      }
    }
  }

  public byte[] getRefValue() throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "getRefValue");
    byte[] _workingValue = this._value;
    NAsmValue realValue = NAsmValue.resolveValue(this);
    if (realValue != null) {
      _workingValue = realValue.getValue();
    }
    NAsmEnvironment.statsEndMethod(this.getClass(), "getRefValue");
    return _workingValue;
  }

  public int getRefOffset() throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "getRefOffset");
    int _workingAbsoluteOffset = this.getAbsoluteOffset();
    NAsmValue realValue = NAsmValue.resolveValue(this);
    if (realValue != null) {
      _workingAbsoluteOffset = realValue.getAbsoluteOffset();
    }
    NAsmEnvironment.statsEndMethod(this.getClass(), "getRefOffset");
    return _workingAbsoluteOffset;
  }

  /**
   * @param value
   *            the value to set
   */
  public void setValue(byte[] value, int offset) throws NAsmException {
    setValue(value, offset, null, false);
  }

  public void setValue(byte[] value, int offset, boolean writeNative) throws NAsmException {
    setValue(value, offset, null, writeNative);
  }

  private void setValue(byte[] value, int offset, Integer length, boolean writeNative) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "setValue");
    byte[] _workingValue = this._value;
    int _workingAbsoluteOffset = this.getAbsoluteOffset();
    if (!writeNative) {
      NAsmValue realValue = NAsmValue.resolveValue(this);
      if (realValue != null) {
        _workingValue = realValue.getValue();
        _workingAbsoluteOffset = realValue.getAbsoluteOffset();
      }
    }

    if (this.getParent() == null) {
      byte[] buff = value;
      //byte[] vbuff = this._value;
      byte[] vbuff = _workingValue;
      int totalLength;
      if (length != null) {
        totalLength = length;
      } else {
        totalLength = this.getLengthBytes();
        if (this.getArray() != null && this.getArray() > 0) {
          totalLength = this.getLengthBytes() * this.getArray();
        }
        if (buff.length < totalLength) {
          totalLength = buff.length;
        }
      }
      System.arraycopy(buff, 0, vbuff, offset, totalLength);
    } else {
      try {
        // Take its portion of data
        byte[] buff = value;
        //byte[] vbuff = this._value;
        byte[] vbuff = _workingValue;
        int totalLength;
        if (length != null) {
          totalLength = length;
        } else {
          totalLength = this.getLengthBytes();
          if (this.getArray() != null && this.getArray() > 0) {
            totalLength = this.getLengthBytes() * this.getArray();
          }
          if (buff.length < totalLength) {
            totalLength = buff.length;
          }
        }
        System.arraycopy(buff, 0, vbuff, _workingAbsoluteOffset + offset, totalLength);
      } catch (Exception ex) {
        NAsmException eex = new NAsmException("NAsmField.setValue(Object value) runtime error: " + ex.getMessage());
        eex.setJavaStack(ex);
        throw eex;
      }
    }
    NAsmEnvironment.statsEndMethod(this.getClass(), "setValue");
  }

  protected NAsmField getConstant(String value) throws NAsmException {
    if (value == null || value.length() == 0) {
      value = NAsmStrings.repeatCharacter(' ', this.getLength()); //SQ TODO review are we sure we want it?
    }
    NAsmField fld = new NAsmField(_CONSTANT, NAsmFieldType.ALPHANUMERIC, 0, value.length());
    byte[] constantValue;
    if (NAsmEnvironment.RUN_EBCDIC()) {
      constantValue = NAsmStrings.convertBytesToEBCDIC(value.getBytes(NAsmEnvironment.ENV_CHARSET()));
    } else {
      constantValue = value.getBytes(NAsmEnvironment.ENV_CHARSET());
    }
    fld.setValue(constantValue, 0);
    return fld;
  }

  //SQ
  protected NAsmField getConstant(byte value[]) throws NAsmException {
    NAsmField fld = new NAsmField(_CONSTANT, NAsmFieldType.ALPHANUMERIC, 0, value.length);
    fld.setValue(value, 0);
    return fld;
  }

  //SQtmp TODO check if we can get rid of the above non static version 
  protected static NAsmField getConstantS(String value) throws NAsmException {
    NAsmField fld = new NAsmField(_CONSTANT, NAsmFieldType.ALPHANUMERIC, 0, value.length());
    byte[] constantValue;
    if (NAsmEnvironment.RUN_EBCDIC()) {
      constantValue = NAsmStrings.convertBytesToEBCDIC(value.getBytes(NAsmEnvironment.ENV_CHARSET()));
    } else {
      constantValue = value.getBytes(NAsmEnvironment.ENV_CHARSET());
    }
    fld.setValue(constantValue, 0);
    return fld;
  }

  protected NAsmField getConstant(int value) throws NAsmException {
    return getConstant(value, this.getLengthBytes());
  }

  protected NAsmField getConstant(int value, int length) throws NAsmException {
    NAsmField fld = new NAsmField(_CONSTANT, this.getType(), 0, length);
    if (fld.getType().equals(NAsmFieldType.NUMERIC_PACKED)) {
      byte[] packedValue = NAsmStrings.packData(value, fld.getLengthDigits(), fld.decimals, true);
      fld.setValue(packedValue, 0);
    } else if (fld.getType().equals(NAsmFieldType.NUMERIC_PACKED_UNSIGNED)) {
      byte[] packedValue = NAsmStrings.packData(value, fld.getLengthDigits(), fld.decimals, false);
      fld.setValue(packedValue, 0);
    } else if (fld.getType().equals(NAsmFieldType.NUMERIC_BINARY)) {
      byte[] binaryValue = NAsmStrings.valueToBinaryBigEndian(value, fld.length);
      fld.setValue(binaryValue, 0);
    } else {
      String sValue = String.valueOf(value).trim();
      byte[] constantValue;
      if (NAsmEnvironment.RUN_EBCDIC()) {
        constantValue = NAsmStrings.convertBytesToEBCDIC(sValue.getBytes(NAsmEnvironment.ENV_CHARSET()));
      } else {
        constantValue = sValue.getBytes(NAsmEnvironment.ENV_CHARSET());
      }
      fld.setValue(constantValue, 0);
    }
    return fld;
  }

  protected NAsmField getConstant(double value) throws NAsmException {
    String sValue = String.format("%.0f", value);
    if (sValue.startsWith("+")) {
      sValue = sValue.substring(1);
    } else if (sValue.startsWith("-")) {
      sValue = sValue.substring(1);
    }
    int decp = sValue.indexOf(".");
    int decimals = 0;
    if (decp > -1) {
      String intPart = sValue.substring(0, decp);
      String decPart = sValue.substring(decp + 1);
      if (Integer.parseInt(decPart) == 0) {
        decimals = 0;
        sValue = intPart;
      } else {
        decimals = decPart.length();
        sValue = intPart + decPart;
      }
    }
    NAsmField fld = new NAsmField(_CONSTANT, this.getType(), 0, this.getLengthBytes());
    fld.setDecimals(decimals);
    if (fld.getType().equals(NAsmFieldType.NUMERIC_PACKED)) {
      byte[] packedValue = NAsmStrings.packData(value, fld.getLengthDigits(), fld.decimals, true);
      fld.setValue(packedValue, 0);
    } else if (fld.getType().equals(NAsmFieldType.NUMERIC_PACKED_UNSIGNED)) {
      byte[] packedValue = NAsmStrings.packData(value, fld.getLengthDigits(), fld.decimals, false);
      fld.setValue(packedValue, 0);
    } else if (fld.getType().equals(NAsmFieldType.NUMERIC_BINARY)) {
      byte[] binaryValue = NAsmStrings.valueToBinaryBigEndian(value, fld.length);
      fld.setValue(binaryValue, 0);
    } else {
      byte[] constantValue;
      if (NAsmEnvironment.RUN_EBCDIC()) {
        constantValue = NAsmStrings.convertBytesToEBCDIC(sValue.getBytes(NAsmEnvironment.ENV_CHARSET()));
      } else {
        constantValue = sValue.getBytes(NAsmEnvironment.ENV_CHARSET());
      }
      fld.setValue(constantValue, 0);
    }
    return fld;
  }

  //SQtmp long version
  protected NAsmField getConstant(long value) throws NAsmException {
    int decimals = 0;
    NAsmField fld = new NAsmField(_CONSTANT, this.getType(), 0, this.getLengthBytes());
    fld.setDecimals(decimals);
    if (fld.getType().equals(NAsmFieldType.NUMERIC_PACKED)) {
      byte[] packedValue = NAsmStrings.packData(value, fld.getLengthDigits(), fld.decimals, true);
      fld.setValue(packedValue, 0);
    } else if (fld.getType().equals(NAsmFieldType.NUMERIC_PACKED_UNSIGNED)) {
      byte[] packedValue = NAsmStrings.packData(value, fld.getLengthDigits(), fld.decimals, false);
      fld.setValue(packedValue, 0);
    } else if (fld.getType().equals(NAsmFieldType.NUMERIC_BINARY)) {
      byte[] binaryValue = NAsmStrings.valueToBinaryBigEndian(value, fld.length);
      fld.setValue(binaryValue, 0);
    } else {
      byte[] constantValue;
      String sValue = String.format("%s", value); //SQtmp
      if (NAsmEnvironment.RUN_EBCDIC()) {
        constantValue = NAsmStrings.convertBytesToEBCDIC(sValue.getBytes(NAsmEnvironment.ENV_CHARSET()));
      } else {
        constantValue = sValue.getBytes(NAsmEnvironment.ENV_CHARSET());
      }
      fld.setValue(constantValue, 0);
    }
    return fld;
  }

  /**
   * @return the addressed field
   * @throws NAsmException
   */
  public NAsmAddress getAddress() throws NAsmException {
    if (this.cachedAddress == null) {
      this.cachedAddress = new NAsmAddress(this, 0);
    }
    return this.cachedAddress;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    try {
      byte[] _workingValue = this._value;
      NAsmValue realValue = NAsmValue.resolveValue(this);
      if (realValue != null) {
        _workingValue = realValue.getValue();
      }
      if (_workingValue == null) {
        return null;
      }

      String realValueDisplay = "";
      if (realValue != null && realValue.getBaseAddress() != null) {
        realValueDisplay = "Based name : " + realValue.getBaseAddress().getAddressedField().getName() + "\n" //
            + "Based offs : " + realValue.getBaseAddress().getOffset() + "\n" + "Abs. offs : " + this.getAbsoluteOffset() + String.format(" [hex: %04x]", this.getAbsoluteOffset()) + "\n"; //SQtmp
      }
      if (this.getLengthBytes() == 4) {
        NAsmAddress fPointer = this.getPointedAddress(0, 4);
        if (fPointer != null) {
          NAsmField addrF = fPointer.getAddressedField();
          if (fPointer.getAddressedField() instanceof NAsmProgramInstance) {
            NAsmProgramInstance program = (NAsmProgramInstance) fPointer.getAddressedField();
            //addrF = program.getInstance().getStorage();
          }
          String preview = addrF.getString(fPointer.getOffset(), 16).toString();
          String previewH = addrF.getByteString(fPointer.getOffset(), 256);
          String hexPreview = NAsmStrings.formatHexDump(previewH.getBytes(NAsmEnvironment.ENV_CHARSET()), NAsmEnvironment.RUN_EBCDIC());
          return "Address of : " + addrF.getName() + "\n" //
              + "Offset     : " + fPointer.getOffset() + "\n" //
              + realValueDisplay //
              + "Data (0-16): " + preview + "\n" //
              + "Data Hex   : \n" + hexPreview //
          ;
        }
      }
      //SQ replaced getLengthBytes
      int areaLen = this.getTotalLength(); //getLengthBytes
      String preview = this.getString(0, areaLen).toString();
      String previewH = this.getByteString(0, areaLen);
      String hexPreview = NAsmStrings.formatHexDump(previewH.getBytes(NAsmEnvironment.ENV_CHARSET()), NAsmEnvironment.RUN_EBCDIC());
      return "Data (0-" + areaLen + "): " + preview + "\n" //
          + realValueDisplay //
          + "Data Hex    : \n" + hexPreview //
      ;
    } catch (NAsmException e) {
      e.printStackTrace();
    }
    return new String(this._value, NAsmEnvironment.ENV_CHARSET());
  }

  /**
   * @return the _value
   */
  public byte[] getValueInstance() {
    return _value;
  }

  /**
   * @return the array
   */
  public Integer getArray() {
    return array;
  }

  /**
   * @param array
   *            the array to set
   */
  public void setArray(Integer array) {
    this.array = array;
  }

  /**
   * 
   * @return
   * @throws NAsmException
   */
  public NAsmAddress getPointedAddress() throws NAsmException {
    return getPointedAddress(0, null);
  }

  /**
   * @return the pointedAddress
   * @throws NAsmException
   */
  public NAsmAddress getPointedAddress(int offset, Integer length) throws NAsmException {
    try {
      int fieldLen = this.length;
      if (this.array != null && this.array > 0) {
        fieldLen = this.length * this.array;
      }
      //This field could be an area starting with an address at the beginning
      if (length == null && fieldLen != 4) {
        return null;
      }
      NAsmAddress addr = NAsmEnvironment.getAddress(this.getValue(offset, 4, false));
      return addr;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * @return the pointedAddress
   * @throws NAsmException
   */
  public void setPointedAddress(NAsmAddress pointedAddress) throws NAsmException {
    setPointedAddress(pointedAddress, 0);
  }

  public void setPointedAddress(NAsmAddress pointedAddress, int offset) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "setPointedAddress");
    byte[] address;
    address = NAsmEnvironment.registerAddress(pointedAddress);
    setValue(address, offset, false);
    NAsmEnvironment.statsEndMethod(this.getClass(), "setPointedAddress");
  }

  /**
   * 
   * @param mask
   * @return
   * @throws NAsmException
   */
  public int testUnderMask(int mask) throws NAsmException {
    return testUnderMaskAt(0, mask);
  }

  public int testUnderMaskAt(int offset, int mask) throws NAsmException {
    //Extracting after the field length
    byte[] b = this.getValue(offset, 1);
    int value = b[0] & 0xff;
    return value & mask;
  }

  /**
   * Use this one for OI, will work on a single byte
   * 
   * @param mask
   * @throws NAsmException
   */
  public void or(Integer mask) throws NAsmException {
    orAt(0, mask);
  }

  /**
   * Example: OI CICCIO,C'0'
   * 
   * @param offset
   * @param mask
   * @throws NAsmException
   */
  public void orAt(Integer offset, char mask) throws NAsmException { //SQ
    orAt(offset, NAsmEnvironment.RUN_EBCDIC() ? NAsmStrings.getEbcdicSequence(mask) : (int) mask);
  }

  /**
   * Use this one for OI, will work on a single byte
   * 
   * @param mask
   * @throws NAsmException
   */
  public void orAt(Integer offset, int mask) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "orAt");
    //Preventing usage from registers, OR must be translated passing the other register as parameter
    if (offset == null && this instanceof NAsmRegister) {
      throw new NAsmException("Registers cannot use this method with the passed parameters.");
    }
    byte[] maskBuffer;
    if (mask > 255) {
      int len = 1;
      int q = mask / 255;
      while (q > 0) {
        len++;
        q = q / 255;
      }
      maskBuffer = new byte[len];
      maskBuffer = NAsmStrings.valueToBinaryBigEndian(mask, len);
    } else {
      maskBuffer = new byte[] { (byte) mask };
    }
    orLogic(maskBuffer, offset);
    NAsmEnvironment.statsEndMethod(this.getClass(), "orAt");
  }

	/**
   * SQ
   * Use this one for OC, will work on multiple bytes. 
   * 
   * Example:  OC Q_CURPNTS,=AL2(Q_SSN) 
   *    with:  Q_SSN EQU  X'0040'    
   *    			 storage.getQCurpnts().orAt(0, Len.Q_CURPNTS, PslcmpConstants.Q_SSN);
   * 
   * @param offset
   * @param len
   * @param mask
   * @throws NAsmException
   */
	public void orAt(Integer offset, int len, int mask) throws NAsmException {
		NAsmEnvironment.statsStartMethod(this.getClass(), "orAt");

		//Preventing usage from registers, OR must be translated passing the other register as parameter
		if (offset == null && this instanceof NAsmRegister) { //SQ TODO clarify this check
			throw new NAsmException("Registers cannot use this method with the passed parameters.");
		}

		byte[] maskBuffer = new byte[len];
		maskBuffer = NAsmStrings.valueToBinaryBigEndian(mask, len);

		orLogic(maskBuffer, offset);

		NAsmEnvironment.statsEndMethod(this.getClass(), "orAt");
	}

  /**
   * Generic OR function
   * 
   * @param offset
   * @param length
   * @param mask (right operand byte-string)
   * @throws NAsmException
   */
  public void orAt(int offset, int length, String mask) throws NAsmException {
		if (! Arrays.equals(mask.getBytes(), mask.getBytes(NAsmEnvironment.ENV_CHARSET()))) {
			logger.debug(""); //SQtmp debug catcher
		}
		orLogic(mask.getBytes(NAsmEnvironment.ENV_CHARSET()), offset);
  }

  /**
   * Use this one for OR and OC, will work on a multiple bytes
   * 
   * @param mask
   * @throws NAsmException
   */
  public void or(NAsmField mask) throws NAsmException {
    orAt(0, this.getLengthBytes(), mask);
  }

  public void or(NAsmField mask, int length) throws NAsmException {
    orAt(0, length, mask);
  }

  /**
   * Use this one for OR and OC, will work on a multiple bytes
   * 
   * @param mask
   * @throws NAsmException
   */
  public void orAt(int offset, NAsmField mask) throws NAsmException {
    orAt(0, this.getLengthBytes(), mask);
  }

  public void orAt(int offset, int length, NAsmField mask) throws NAsmException {
    byte[] maskBuffer = mask.getValue(offset, length);
    NAsmAddress ma = mask.getPointedAddress();
    if (ma != null) {
      maskBuffer = ma.getAddressedField().getValue(ma.getOffset() + offset, length);
    } else {
      maskBuffer = mask.getValue(offset, length);
    }
    orAt(offset, maskBuffer);
  }

  public void orAt(int offset, byte[] maskBuffer) throws NAsmException {
    orLogic(maskBuffer, offset);
  }

  /**
   * Logic OR implementation
   * 
   * @param maskBuffer
   * @param offset
   * @throws NAsmException
   */
  protected void orLogic(byte[] maskBuffer, int offset) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "orLogic");
    byte[] b = this.getValue(offset, maskBuffer.length);
    byte[] result = new byte[maskBuffer.length];
    for (int idx = 0; idx < maskBuffer.length; idx++) {
      int value = b[idx] & 0xff;
      int maskValue = maskBuffer[idx] & 0xff;
      result[idx] = (byte) (value | maskValue);
    }
    this.setValue(result, offset);
    NAsmEnvironment.statsEndMethod(this.getClass(), "orLogic");
  }

  /**
   * Use this one for NI, will work on a single byte
   * 
   * @param mask
   * @throws NAsmException
   */
  public void and(Integer mask) throws NAsmException {
    andAt(null, mask);
  }

  /**
   * Use this one for NI, will work on a single byte
   * 
   * @param mask
   * @throws NAsmException
   */
  public void andAt(Integer offset, int mask) throws NAsmException {
    //Preventing usage from registers, NR must be translated passing the other register as parameter
    if (offset == null && this instanceof NAsmRegister) {
      throw new NAsmException("Registers cannot use this method with the passed parameters.");
    }
    if (mask > 255) {
      throw new NAsmException("Mask value invalid for a sigle byte.");
    }
    if (offset == null) {
      offset = 0;
    }
    byte[] maskBuffer = new byte[] { (byte) mask };
    andLogic(maskBuffer, offset);
  }

  /**
   * Use this one for OR and NC, will work on a multiple bytes
   * 
   * @param mask
   * @throws NAsmException
   */
  public void and(NAsmField mask) throws NAsmException {
    andAt(0, this.getLengthBytes(), mask);
  }

  public void and(NAsmField mask, int length) throws NAsmException {
    andAt(0, length, mask);
  }

  /**
   * Use this one for NR and NC, will work on a multiple bytes
   * 
   * @param mask
   * @throws NAsmException
   */
  public void andAt(int offset, NAsmField mask) throws NAsmException {
    andAt(0, this.getLengthBytes(), mask);
  }

  public void andAt(int offset, int length, NAsmField mask) throws NAsmException {
    byte[] maskBuffer;
    NAsmAddress ma = mask.getPointedAddress();
    if (ma != null) {
      maskBuffer = ma.getAddressedField().getValue(ma.getOffset() + offset, length);
    } else {
      maskBuffer = mask.getValue(offset, length);
    }
    andAt(offset, maskBuffer);
  }

  /**
   * SQtmp  
   * @param offset
   * @param length
   * @param mask - HEX literal
   * @throws NAsmException
   */
  public void andAt(int offset, int length, String mask) throws NAsmException {
    andLogic(NAsmStrings.fromHexToString(mask).getBytes(NAsmEnvironment.ENV_CHARSET()), offset, length);
  }

  public void andAt(int offset, byte[] maskBuffer) throws NAsmException {
    andLogic(maskBuffer, offset);
  }

  /**
   * AND logic implementation
   * 
   * @param maskBuffer
   * @param offset
   * @throws NAsmException
   */
  private void andLogic(byte[] maskBuffer, int offset) throws NAsmException {
    andLogic(maskBuffer, offset, maskBuffer.length);
  }

  //SQ required by NAsmRegister
  //private void andLogic(byte[] maskBuffer, int offset, int length) throws NAsmException {
  protected void andLogic(byte[] maskBuffer, int offset, int length) throws NAsmException {
    byte[] b = this.getValue(offset, length);
    byte[] result = new byte[length];
    for (int idx = 0; idx < length; idx++) {
      int value = b[idx] & 0xff;
      int maskValue = maskBuffer[idx] & 0xff;
      result[idx] = (byte) (value & maskValue);
    }
    this.setValue(result, offset);
  }

  /**
   * 
   * @param mask
   * @throws NAsmException
   */
  public void xor(int mask) throws NAsmException {
    byte[] b = this.getValue();
    int value = b[0] & 0xff;
    byte[] result = new byte[1];
    result[0] = (byte) (value ^ mask);
    this.setValue(result, 0);
  }

  /**
   * generic xor function
   * 
   * @param offset
   * @param length
   * @param mask (right operand as NAsmField)
   * @throws NAsmException
   */
  public void xorAt(int offset, int length, NAsmField mask) throws NAsmException {
    if (!Arrays.equals(mask.getByteString(0, length).getBytes(), mask.getByteString(0, length).getBytes(NAsmEnvironment.ENV_CHARSET()))) {
      //logger.debug(""); //SQtmp debug catcher  [mask.getValue(offset, length);]
    }
    xorLogic(mask.getByteString(0, length).getBytes(NAsmEnvironment.ENV_CHARSET()), offset, length);
  }

  /**
   * generic xor function
   * 
   * @param offset
   * @param length
   * @param mask (right operand byte-string)
   * @throws NAsmException
   */
  public void xorAt(int offset, int length, String mask) throws NAsmException {
    xorLogic(mask.getBytes(NAsmEnvironment.ENV_CHARSET()), offset, length);
  }

  protected void xorLogic(byte[] maskBuffer, int offset, int length) throws NAsmException {
    byte[] b = this.getValue(offset, length);
    byte[] result = new byte[length];
    for (int idx = 0; idx < length; idx++) {
      int value = b[idx] & 0xff;
      int maskValue = maskBuffer[idx] & 0xff;
      result[idx] = (byte) (value ^ maskValue);
    }
    this.setValue(result, offset);
  }

  //SQtmp test
  public void pack(int offset, int length, String packString) throws NAsmException {
    pack(packString.getBytes(NAsmEnvironment.ENV_CHARSET()), offset, length);
  }

  private void pack(byte[] zonedBuffer, int offset, int length) throws NAsmException {
    byte[] result = new byte[length];
    for (int idx = 0; idx < length; idx++) {
      int secondDigit = zonedBuffer[idx] & 0x0F;
      result[idx] = (byte) (0xFF ^ secondDigit);
    }
  }

  @SuppressWarnings("unused")
  private void unpack(byte[] packBuffer, int offset, int length) throws NAsmException {
    byte[] b = this.getValue(offset, length);
    byte[] result = new byte[length];
    for (int idx = 0; idx < length; idx++) {
      int packValue = packBuffer[idx] & 0xff;
      int secondDigit = packBuffer[idx] & 0x0F;
      result[idx] = (byte) (0xFF ^ secondDigit);
    }
  }

  /**
   * This method will extract only valid binary big endian values
   * from a string
   * 
   * @param offsetStart
   * @param offsetEnd
   * @return
   * @throws NAsmException
   */
  public double getNumericAtRange(int offsetStart, int offsetEnd) throws NAsmException {
    int length = offsetEnd - offsetStart;
    byte[] value = new byte[length];
    System.arraycopy(this.getValue(offsetStart, length), 0, value, 0, length);
    return getNumeric(value);
  }

  //SQ tmp long version
  public long getNumericAtRange_L(int offsetStart, int offsetEnd) throws NAsmException {
    int length = offsetEnd - offsetStart;
    byte[] value = new byte[length];
    System.arraycopy(this.getValue(offsetStart, length), 0, value, 0, length);
    return getNumeric_L(value);
  }

  public int getBinaryAtRange(int offsetStart, int offsetEnd) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "getBinaryAtRange");
    int length = offsetEnd - offsetStart;
    byte[] value = new byte[length];
    System.arraycopy(this.getValue(offsetStart, length), 0, value, 0, value.length);
    double rc = 0;
    try {
      rc = NAsmStrings.binaryBigEndianToValue(value, value.length);
    } catch (Exception ex) {
      String dump = dumpRecord();
      String message = "Invalid numeric binary value for field: " //
          + this.getName() + " offset: " + this.getAbsoluteOffset() //
          + ", hex value: 0x" + NAsmStrings.toHex(value) + dump;
      message += dump;
      throw new NAsmException(message);
    }
    NAsmEnvironment.statsEndMethod(this.getClass(), "getBinaryAtRange");
    return (int) rc;
  }

  public double getPacked() throws NAsmException {
    return getPackedAtRange(0, this.getLengthBytes());
  }

  public double getPackedAtRange(int offsetStart, int offsetEnd) throws NAsmException {
    int length = offsetEnd - offsetStart;
    byte[] value = new byte[length];
    System.arraycopy(this.getValue(offsetStart, length), 0, value, 0, value.length);
    return NAsmStrings.unpackData(value, this.getDecimals());
  }

  // ------------------------------------------------------------------------------------------------------------
  // --------------------------------- New Implementation -------------------------------------------------------
  // ------------------------------------------------------------------------------------------------------------

  /**
   * This method implements ED logic when the first parameter has no offset nor length
   * and the second parameter is a field without offset:<br/>
   * <b>ED    WORK12,PACK4</b><br/>
   * 
   * Usage example:<br/>
   * <b>storage.getWork12().setEdited(storage.getPack4());</b><br/>
   * 
   * @param NAsmField f_packed
   * @return void
   * @throws NAsmException
   */
  public void setEdited_NEW(NAsmField f_packed) throws NAsmException {
    //SQtmp - check if this.lenght is good enough 
    //setEdited(packed, getValue().length, 0, 0);
    setEdited_NEW(f_packed, 0, getLength(), 0);
  }

  /**
  * This method implements EDMK logic when the first parameter has no offset nor length
  * and the second parameter is a field without offset:<br/>
  * <b>EDMK    WORK12,PACK4</b><br/>
  * 
  * Usage example:<br/>
  * <b>storage.getWork12().setEdited(storage.getPack4());</b><br/>
  * 
  * @param NAsmField f_packed
  * @return void
  * @throws NAsmException
  */
  public void setEditedMK(NAsmField f_packed) throws NAsmException {
    //SQtmp - check if this.lenght is good enough 
    //setEdited(packed, getValue().length, 0, 0);
    setEditedMK(f_packed, 0, getLength(), 0);
  }

  /**
   * This method implements ED logic when the first parameter has no offset nor length
   * and a second parameter is a register without offset:<br/>
   * <b>ED    WORK12,R8</b><br/>
   * 
   * Usage example:<br/>
   * <b>storage.getWork12().setEdited_NEW(getReg8());</b><br/>
   * 
   * @param NAsmRegister r_packed
   * @return void
   * @throws NAsmException
   */
  public void setEdited_NEW(NAsmRegister r_packed) throws NAsmException {
    setEdited_NEW(r_packed, 0);
  }

  /**
  * This method implements EDMK logic when the first parameter has no offset nor length
  * and a second parameter is a register without offset:<br/>
  * <b>EDMK    WORK12,R8</b><br/>
  * 
  * Usage example:<br/>
  * <b>storage.getWork12().setEdited_NEW(getReg8());</b><br/>
  * 
  * @param NAsmRegister r_packed
  * @return void
  * @throws NAsmException
  */
  public void setEditedMK(NAsmRegister r_packed) throws NAsmException {
    setEditedMK(r_packed, 0);
  }

  /**
   * This method implements ED logic when the first parameter has no offset nor length
   * and a second parameter is a register with offset:<br/>
   * <b> ED    WORK12,5(R8)</b><br/>
   * 
   * Usage example:<br/>
   * <b>storage.getWork12().setEdited(getReg8(), 5);</b><br/>
   * 
   * @param NAsmRegister r_packed
   * @param int packed_offset
   * @return void
   * @throws NAsmException
   */
  //	public void setEdited_NEW(NAsmRegister r_packed, int packed_offset) throws NAsmException {
  //		setEdited_NEW(r_packed, 0, getLength(), packed_offset);
  //	}
  //SQtmp - above to be removed
  public void setEdited_NEW(NAsmField r_packed, int packed_offset) throws NAsmException {
    setEdited_NEW(r_packed, 0, getLength(), packed_offset);
  }

  /**
   * This method implements EDMK logic when the first parameter has no offset nor length
   * and a second parameter is a register with offset:<br/>
   * <b> EDMK    WORK12,5(R8)</b><br/>
   * 
   * Usage example:<br/>
   * <b>storage.getWork12().setEdited(getReg8(), 5);</b><br/>
   * 
   * @param NAsmRegister r_packed
   * @param int packed_offset
   * @return void
   * @throws NAsmException
   */
  //	public void setEdited_NEW(NAsmRegister r_packed, int packed_offset) throws NAsmException {
  //		setEdited_NEW(r_packed, 0, getLength(), packed_offset);
  //	}
  //SQtmp - above to be removed
  public void setEditedMK(NAsmField r_packed, int packed_offset) throws NAsmException {
    setEditedMK(r_packed, 0, getLength(), packed_offset);
  }

  /**
   * This method implements ED logic when the first parameter has offset and length
   * and the second parameter is a field without offset:<br/>
   * <b>ED    WORK12+3(8),PACK4</b><br/>
   * 
   * Usage example:<br/>
   * <b>storage.getWork12().setEdited(storage.getPack4(), 3, 8);</b><br/>
   * 
   * @param NAsmField f_packed
   * @param int mask_offset
   * @param int mask_len
   * @return void
   * @throws NAsmException
   */
  public void setEdited_NEW(NAsmField f_packed, int mask_offset, int mask_len) throws NAsmException {
    setEdited_NEW(f_packed, mask_offset, mask_len, 0);
  }

  /**
   * This method implements EDMK logic when the first parameter has offset and length
   * and the second parameter is a field without offset:<br/>
   * <b>EDMK    WORK12+3(8),PACK4</b><br/>
   * 
   * Usage example:<br/>
   * <b>storage.getWork12().setEdited(storage.getPack4(), 3, 8);</b><br/>
   * 
   * @param NAsmField f_packed
   * @param int mask_offset
   * @param int mask_len
   * @return void
   * @throws NAsmException
   */
  public void setEditedMK(NAsmField f_packed, int mask_offset, int mask_len) throws NAsmException {
    setEditedMK(f_packed, mask_offset, mask_len, 0);
  }

  /**
  * This method implements ED logic when the first parameter has offset and length
  * and a second parameter is a register with offset:<br/>
  * <b>ED    WORK12+3(8),5(R8)</b><br/>
  * 
  * Usage example:<br/>
  * <b>storage.getWork12().setEdited(getReg8(), 3, 8, 5);</b><br/>
  * 
  * @param NAsmRegister r_packed
  * @param int mask_offset
  * @param int mask_len
  * @param int packed_offset
  * @return void
  * @throws NAsmException
  */
  //SQtmp TO BE REMOVED 
  public void setEdited_NEW(NAsmRegister r_packed, int mask_offset, int mask_len, int packed_offset) throws NAsmException {
    setEdited_NEW((NAsmField) r_packed, mask_offset, mask_len, packed_offset);
  }

  /**
  * This method implements EDMK logic when the first parameter has offset and length
  * and a second parameter is a register with offset:<br/>
  * <b>EDMK    WORK12+3(8),5(R8)</b><br/>
  * 
  * Usage example:<br/>
  * <b>storage.getWork12().setEdited(getReg8(), 3, 8, 5);</b><br/>
  * 
  * @param NAsmRegister r_packed
  * @param int mask_offset
  * @param int mask_len
  * @param int packed_offset
  * @return void
  * @throws NAsmException
  */
  //SQtmp TO BE REMOVED 
  public void setEditedMK(NAsmRegister r_packed, int mask_offset, int mask_len, int packed_offset) throws NAsmException {
    setEditedMK((NAsmField) r_packed, mask_offset, mask_len, packed_offset);
  }

  /**
   * This method implements ED logic when the first parameter has offset and length
   * and a second parameter is a field with offset:<br/>
   * <b>ED    WORK12+3(8),PACK4+5</b><br/>
   * 
   * Usage example:<br/>
   * <b>storage.getWork12().setEdited(storage.getPack4(), 3, 8, 5);</b><br/>
   * 
   * @param NAsmField f_packed
   * @param int mask_offset
   * @param int mask_len
   * @param int packed_offset
   * @return void
   * @throws NAsmException
   */
  public void setEdited_NEW(NAsmField f_packed, int mask_offset, int mask_len, int packed_offset) throws NAsmException {
    if (mask_len > 256) {
      mask_len = 256;
    }
    //SQ getByteArray truncates!
    //byte[] mask_value = getByteArray(mask_offset, mask_len);
    byte[] mask_value = getValue(mask_offset, mask_len);

    // The length of packed field(s) could not be greater than a half of the length of the mask plus one
    // In a worth case (packed without sign, significance indicator as filler):
    //   mask:    0x21 0x20 0x20 0x20
    //   packed:  0x12 0x34
    // In a worth case (packed with sign, significance indicator as filler):
    //   mask:    0x21 0x20 0x20
    //   packed:  0x12 0x3c

    byte[] packed_value = f_packed.getByteArray(packed_offset, (mask_len / 2) + 1);

    executeED(mask_value, packed_value, mask_offset, mask_len, 0, false); //SQ passing mask_offset instead of 0

    setByteArray(mask_value, mask_offset);
  }

  /**
   * This method implements EDMK logic when the first parameter has offset and length
   * and a second parameter is a field with offset:<br/>
   * <b>EDMK    WORK12+3(8),PACK4+5</b><br/>
   * 
   * Usage example:<br/>
   * <b>storage.getWork12().setEdited(storage.getPack4(), 3, 8, 5);</b><br/>
   * 
   * @param NAsmField f_packed
   * @param int mask_offset
   * @param int mask_len
   * @param int packed_offset
   * @return void
   * @throws NAsmException
   */
  public void setEditedMK(NAsmField f_packed, int mask_offset, int mask_len, int packed_offset) throws NAsmException {
    if (mask_len > 256) {
      mask_len = 256;
    }
    //SQ getByteArray truncates!
    //byte[] mask_value = getByteArray(mask_offset, mask_len);
    byte[] mask_value = getValue(mask_offset, mask_len);

    // The length of packed field(s) could not be greater than a half of the length of the mask plus one
    // In a worth case (packed without sign, significance indicator as filler):
    //   mask:    0x21 0x20 0x20 0x20
    //   packed:  0x12 0x34
    // In a worth case (packed with sign, significance indicator as filler):
    //   mask:    0x21 0x20 0x20
    //   packed:  0x12 0x3c

    //SQ getByteArray truncates!
    byte[] packed_value = f_packed.getByteArray(packed_offset, (mask_len / 2) + 1);
    //byte[] packed_value = f_packed.getValue(packed_offset, (mask_len / 2) + 1);

    executeED(mask_value, packed_value, mask_offset, mask_len, 0, true); //SQ passing mask_offset instead of 0

    setByteArray(mask_value, mask_offset);
  }

  //
  // Wrapper for test cases only, DO NOT USE!!!
  //
  public void executeED(byte[] mask, byte[] packed) throws NAsmException {
    executeED(mask, packed, 0, mask.length, 0, false);
  }

  private void executeED(byte[] mask, byte[] packed, int mask_offset, int mask_len, int packed_offset, boolean isMK) throws NAsmException {
    int i; // mask index
    int j; // next digit number
    int curr; // current half-byte
    int next; // next half-byte
    byte fill; // fill byte
    boolean si = false; // significance indicator
    int edmk = -1;

    int mask_offset_ED = 0; //SQ TODO review usage of this mask_offset_ED (which is different than the argument mask_offset!)   
    fill = mask[mask_offset_ED];

    // Is this right? Why we need to correct the fill byte? How the real assembler 370 does in this case? 
    if (fill == 0x20 || fill == 0x21 || fill == 0x22) {
      fill = 0x40;
    }

    curr = (packed[packed_offset] & 0xff) >> 4;

    for (i = mask_offset_ED, j = 1; i < mask_len && i < mask.length; i++) {
      if (packed_offset + (j / 2) < packed.length) {
        next = ((j & 0x01) == 0) ? ((packed[packed_offset + (j / 2)] & 0xff) >> 4) : (packed[packed_offset + (j / 2)] & 0x0f);
      } else {
        next = 0x00;
      }
      switch (mask[i] & 0xff) {
      case 0x20: // digit selector
        if (!si) {
          if (curr == 0x00) {
            mask[i] = fill;
          } else if (next != 0x0a && next != 0x0c && next != 0x0e && next != 0x0f) {
            mask[i] = (byte) (curr | 0xf0);
            si = true;
            edmk = i;
            if (next == 0x0d) {
              next = 0x00;
            }
          } else {
            mask[i] = (byte) (curr | 0xf0);
            edmk = i;
            next = 0x00;
          }
        } else if (next != 0x0a && next != 0x0c && next != 0x0e && next != 0x0f) {
          mask[i] = (byte) (curr | 0xf0);
          si = true;
          if (next == 0x0d) {
            next = 0x00;
          }
        } else {
          mask[i] = (byte) (curr | 0xf0);
          si = false;
          next = 0x00;
        }
        j++;
        curr = next;
        break;

      case 0x21: // significance starter
        if (!si) {
          if (curr == 0x00) {
            mask[i] = fill;
            if (next != 0x0a && next != 0x0c && next != 0x0e && next != 0x0f) {
              si = true;
            }
          } else if (next != 0x0a && next != 0x0c && next != 0x0e && next != 0x0f) {
            mask[i] = (byte) (curr | 0xf0);
            si = true;
            edmk = i;
            if (next == 0x0d) {
              next = 0x00;
            }
          } else {
            mask[i] = (byte) (curr | 0xf0);
            edmk = i;
            next = 0x00;
          }
        } else if (next != 0x0a && next != 0x0c && next != 0x0e && next != 0x0f) {
          mask[i] = (byte) (curr | 0xf0);
          si = true;
          if (next == 0x0d) {
            next = 0x00;
          }
        } else {
          mask[i] = (byte) (curr | 0xf0);
          si = false;
          next = 0x00;
        }
        j++;
        curr = next;
        // LL ??????????????????????????????????????
        //        if (edmk < 0) {
        //          edmk = i;
        //        }
        // LL ??????????????????????????????????????
        break;

      case 0x22: // field separator
        mask[i] = fill;
        si = false;
        break;

      default:
        if (!si) {
          mask[i] = fill;
        }
        break;
      }
    }

    // SETTING REGISTER 1 if EDMK
    if (isMK && edmk >= 0) {
      NAsmEnvironment.registers[NAsmOnlineProgram.REG_1].setAddress(this, mask_offset + edmk);
    }

  }

  public void setEdited(String value) throws NAsmException {
    throw new NAsmException("TO_BE_IMPLEMENTED");
  }

  public void setEdited(NAsmField field, Integer offset, Integer length) throws NAsmException {
    int value = (int) NAsmStrings.unpackData(field.getValue(), 0);
    setEdited(value, offset, length);
  }

  public void setEdited(double val, Integer offset, Integer length) throws NAsmException {
    int value = (int) val;
    int workingLen = length != null ? workingLen = length : this.getByteString().getBytes().length;
    int workingOffset = offset != null ? offset : 0;
    //SQ - let's work with the actual mask's bytes only
    byte[] mask = this.getByteString(workingOffset, workingLen).getBytes(NAsmEnvironment.ENV_CHARSET());
    //byte[] out = this.getByteString().getBytes(NAsmEnvironment.ENV_CHARSET());

    int maskReplaceValues = 0;
    for (int idx = 0; idx < workingLen; idx++) {
      if (mask[idx] == 0x20 || mask[idx] == 0x21) {
        maskReplaceValues++;
      }
    }
    String strValue = "" + value;
    if (maskReplaceValues < strValue.length()) {
      int diff = strValue.length() - maskReplaceValues;
      strValue = strValue.substring(diff);
    } else if (maskReplaceValues > strValue.length()) {
      strValue = NAsmStrings.formatZeroes(strValue, maskReplaceValues);
    }
    byte[] inputBytes;
    if (NAsmEnvironment.RUN_EBCDIC()) {
      inputBytes = NAsmStrings.convertBytesToEBCDIC(strValue.getBytes(NAsmEnvironment.ENV_CHARSET()));
    } else {
      inputBytes = strValue.getBytes(NAsmEnvironment.ENV_CHARSET());
    }

    boolean is0x21 = false;
    byte maskFiller = mask[0];
    if (maskFiller == 0x20 || maskFiller == 0x21) {
      maskFiller = 0x40;
    }
    int posInput = 0;
    boolean inputMoved = false;
    for (int idx = 0; idx < workingLen; idx++) {
      byte curr = mask[idx];
      if (curr == 0x21) {
        is0x21 = true;
        if ((inputBytes[posInput] & 0xff) == (0xf0 & 0xff)) {
          //Leave 0x21
          if (inputMoved) {
            this.setByteArray(new byte[] { inputBytes[posInput] }, idx + workingOffset, 1);
          } else {
            this.setByteArray(new byte[] { maskFiller }, idx + workingOffset, 1);
          }
        } else {
          this.setByteArray(new byte[] { inputBytes[posInput] }, idx + workingOffset, 1);
          inputMoved = true;
        }
        posInput++;
      } else {
        if (curr == 0x20) {
          if (is0x21) {
            this.setByteArray(new byte[] { inputBytes[posInput] }, idx + workingOffset, 1);
            inputMoved = true;
          } else {
            if ((inputBytes[posInput] & 0xff) == (0xf0 & 0xff) //
                && !inputMoved) {
              this.setByteArray(new byte[] { maskFiller }, idx + workingOffset, 1);
            } else {
              this.setByteArray(new byte[] { inputBytes[posInput] }, idx + workingOffset, 1);
              inputMoved = true;
            }
          }
          posInput++;
        } else {
          //keep what it's there
          if (inputMoved) {
            this.setByteArray(new byte[] { curr }, idx + workingOffset, 1);
          } else {
            this.setByteArray(new byte[] { maskFiller }, idx + workingOffset, 1);
          }
        }
      }
    }
    //this.setByteArray(out);
  }

  public void setAsPacked(NAsmRegister register) throws NAsmException {
    setAsPacked(register.getNumeric());
  }

  public void setAsPacked(double value) throws NAsmException {
    setAsPacked(value, null, null);
  }

  //SQtmp - clone of correspondent "set" to manage signed Vs unsigned packed instructions
  public void setAsPackedUnsigned(double value) throws NAsmException {
    NAsmStrings.isUnsignedPacked = true;
    setAsPacked(value, null, null);
    NAsmStrings.isUnsignedPacked = false;
  }

  public void setAsPacked(double value, Integer length) throws NAsmException {
    setAsPacked(value, null, length);
  }

  public void setAsPacked(double value, Integer offset, Integer length) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "setAsPacked");
    int totalLength;
    if (length != null) {
      totalLength = length;
    } else {
      totalLength = this.getLengthBytes();
      if (this.getArray() != null && this.getArray() > 0) {
        //SQtmp - run-time check - why do we want the total len?
        logger.warn("setAsPacked to verify");
        totalLength = this.getLengthBytes() * this.getArray();
      }
    }

    int digits = totalLength * 2 - 1;
    boolean signed = false;
    if (this.getType().equals(NAsmFieldType.NUMERIC_PACKED)) {
      signed = true;
    }
    byte[] valuePacked = NAsmStrings.packData(value, digits, this.getDecimals(), signed);
    if (offset != null) {
      this.setValue(valuePacked, offset);
    } else {
      this.setValue(valuePacked, 0);
    }
    NAsmEnvironment.statsEndMethod(this.getClass(), "setAsPacked");
  }

  public void setAsString(String value) throws NAsmException {
    int totalLength = this.getLengthBytes();
    if (this.getArray() != null && this.getArray() > 0) {
      totalLength = this.getLengthBytes() * this.getArray();
    }

    if (value == null) {
      return;
    }
    byte[] byteBuffer;
    if (totalLength > 0 && totalLength < value.length()) {
      byteBuffer = Arrays.copyOfRange(value.getBytes(NAsmEnvironment.ENV_CHARSET()), 0, totalLength);
    } else {
      byteBuffer = value.getBytes(NAsmEnvironment.ENV_CHARSET());
    }
    if (NAsmEnvironment.RUN_EBCDIC()) {
      byteBuffer = NAsmStrings.convertBytesToEBCDIC(byteBuffer);
    }
    this.setValue(byteBuffer, 0);
  }

  public void moveWithOffset(NAsmField field, int fromLength, int toLength) throws NAsmException {
    moveWithOffset(field, 0, fromLength, 0, toLength);
  }

  /**
   * 
   * @param field
   * @param fromOffset
   * @param fromLength
   * @param toOffset
   * @param toLength
   * @throws NAsmException
   */
  public void moveWithOffset(NAsmField field, int fromOffset, int fromLength, int toOffset, int toLength) throws NAsmException {
    if (toLength != fromLength || toLength != 1) {
      moveWithOffset_(field, fromOffset, fromLength, toOffset, toLength);
      return;
    }
    NAsmField binFrom = new NAsmField("BINF", NAsmFieldType.NUMERIC_BINARY, 0, fromLength);
    binFrom.set(field.getBinaryAtRange(fromOffset, fromOffset + fromLength));
    binFrom.set(binFrom.getInt() * 16);

    NAsmField binTo = new NAsmField("BINT", NAsmFieldType.NUMERIC_BINARY, 0, toLength);
    binTo.set(this.getBinaryAtRange(toOffset, toOffset + toLength));
    binTo.and(15);
    binTo.or(binFrom.getInt());
    this.setByteString(binTo.getByteString(0, fromLength), toOffset);
  }

  private void moveWithOffset_(NAsmField field, int fromOffset, int fromLength, int toOffset, int toLength) throws NAsmException {
    if (toLength < fromLength) {
      throw new NAsmException("UNSUPPORTED_MVO_LENGTH");
    }
    if (toLength != fromLength && (toOffset > 0 || fromOffset > 0)) {
      throw new NAsmException("UNSUPPORTED_MVO_OFFSET");
    }
    int oneMore = 0;
    if (toLength != fromLength) {
      toOffset = toLength - fromLength;
      oneMore = 1;
    }

    NAsmField source = new NAsmField("SOURCE", NAsmFieldType.ALPHANUMERIC, 0, fromLength + oneMore);
    source.setByteString(field.getByteString(fromOffset, fromLength), oneMore);

    final int shiftMod = 4 % 8;

    byte[] sourceValue = source.getValue();
    byte[] out = new byte[sourceValue.length];

    Byte dstRest = null;
    for (int idx = sourceValue.length - 1; idx >= 0; idx--) {
      byte src = sourceValue[idx];
      //Shift 4 bits left: i.e.  5C -> C0
      byte dst = (byte) (src << shiftMod);
      if (dstRest != null) {
        int val1 = dst & 0xff;
        int val2 = dstRest & 0xff;
        dst = (byte) (val1 | val2);
      }
      out[idx] = dst;
      //Shift 4 bits right: i.e. 5C -> 05
      dstRest = (byte) ((0xff & src) >>> shiftMod);
    }
    byte[] target = this.getValue();
    for (int idx = 0; idx < (toLength - fromLength); idx++) {
      target[idx] = (byte) 0x00;
    }
    int idxOut = 0;
    for (int idx = toOffset - oneMore; idx < out.length + toOffset - oneMore; idx++) {
      //Last always
      if ((idx + 1 == out.length + toOffset - oneMore) //
      ) {
        byte dst = (byte) (target[idx] << shiftMod);
        target[idx] = (byte) ((0xff & dst) >>> shiftMod);
        int val1 = out[idxOut] & 0xff;
        int val2 = target[idx] & 0xff;
        target[idx] = (byte) (val1 | val2);
      } else {
        //First if lengths are different 
        if (oneMore > 0 && idx == (toOffset - oneMore)) {
          byte dst = (byte) (target[idx] << shiftMod);
          target[idx] = dst;
          int val1 = out[idxOut] & 0xff;
          int val2 = target[idx] & 0xff;
          target[idx] = (byte) (val1 | val2);
        } else {
          target[idx] = out[idxOut];
        }
      }
      idxOut++;
    }
    this.setByteArray(target);
  }

  /**
   * Convert the field packed content to its zoned representation
   * 
   * @return a string representing the packed value in a zoned format 
  
   * @throws NAsmException
   */
  public String convertPackedToZoned() throws NAsmException {
    int targetLength = (this.getLengthBytes() * 2) - 1;
    return convertPackedToZoned(0, this.getLengthBytes(), targetLength);
  }

  /**
   * Convert the field packed content to its zoned representation
   * 
   * @return a string representing the packed value in a zoned format 
   *        
   * @throws NAsmException
   */
  public String convertPackedToZoned(int targetLength) throws NAsmException {
    return convertPackedToZoned(0, this.getLengthBytes(), targetLength);
  }

  /**
   * Convert the field packed content to its zoned representation
   * 
   * @param offset
   *        offset within the source field
   * @param length
   *        length within the source field
   * @param targetLength
   *        length of the zoned output
   * @return a string representing the packed value in a zoned format
   *  
   * @throws NAsmException
   */
  public String convertPackedToZoned(int offset, int length, int targetLength) throws NAsmException {
    return NAsmStrings.formatZeroes(this.getPackedAtRange(offset, offset + length), targetLength);
  }

  /**
   * Convert a valid packed sequence to its zoned representation
   * 
   * @param packedSequence
   *        packed sequence to be converted
   * @param offset
   *        offset within the source field
   * @param length
   *        length within the source field
   * @throws NAsmException
   */
  public void convertToZoned(String packedSequence, int offset, int length) throws NAsmException {
    byte[] workingValue = packedSequence.getBytes(NAsmEnvironment.ENV_CHARSET());
    byte[] zonedValue = new byte[length];
    //Initialize the zoned field
    for (int idx = 0; idx < zonedValue.length; idx++) {
      zonedValue[idx] = (byte) 0xf0;
    }
    final int shiftMod = 4 % 8;

    byte src = workingValue[workingValue.length - 1];
    //Shift 4 bits left: i.e.  BC -> C0
    byte dst = (byte) (src << shiftMod);
    //Shift 4 bits right: i.e.  80 -> 08
    byte dst2 = (byte) ((0xff & src) >>> shiftMod);
    zonedValue[zonedValue.length - 1] = (byte) (dst + dst2);

    int outPos = zonedValue.length - 1;
    for (int idx = workingValue.length - 2; idx > -1; idx--) {
      //Shift 4 bits left: i.e.  58 -> 80
      src = workingValue[idx];
      dst = (byte) (src << shiftMod);
      //Shift 4 bits right: i.e.  80 -> 08
      dst = (byte) ((0xff & dst) >>> shiftMod);
      outPos--;
      if (outPos < 0) {
        break;
      }
      zonedValue[outPos] = (byte) (dst + 0xf0);

      src = workingValue[idx];
      //Shift 4 bits right: i.e.  58 -> 05
      dst = (byte) ((0xff & src) >>> shiftMod);
      outPos--;
      if (outPos < 0) {
        break;
      }
      zonedValue[outPos] = (byte) (dst + 0xf0);
    }
    this.setByteArray(zonedValue, offset);
  }

  public void convertToUnsignedPacked(double value) throws NAsmException {
    convertToUnsignedPacked(value, 0, this.getLengthBytes());
  }

  //SQtmp long version
  public void convertToUnsignedPacked(long value) throws NAsmException {
    convertToUnsignedPacked(value, 0, this.getLengthBytes());
  }

  public void convertToUnsignedPacked(double value, int offset, int length) throws NAsmException {
    byte[] packedValue = NAsmStrings.packData(value, length * 2 - 1, 0, false);
    final int shiftMod = 4 % 8;
    byte lastByte = packedValue[length - 1];

    //Shift 4 bits right: i.e.  8C -> 08
    byte dst2 = (byte) ((0xff & lastByte) >>> shiftMod);
    //Shift 4 bits left: i.e.  08 -> 80
    byte dst = (byte) (dst2 << shiftMod);
    //Set last pack value without sign
    packedValue[packedValue.length - 1] = (byte) (dst + 0x0f);
    this.setByteArray(packedValue, offset);
  }

  //SQtmp long version
  public void convertToUnsignedPacked(long value, int offset, int length) throws NAsmException {
    byte[] packedValue = NAsmStrings.packData(value, length * 2 - 1, 0, false);
    final int shiftMod = 4 % 8;
    byte lastByte = packedValue[length - 1];

    //Shift 4 bits right: i.e.  8C -> 08
    byte dst2 = (byte) ((0xff & lastByte) >>> shiftMod);
    //Shift 4 bits left: i.e.  08 -> 80
    byte dst = (byte) (dst2 << shiftMod);
    //Set last pack value without sign
    packedValue[packedValue.length - 1] = (byte) (dst + 0x0f);
    this.setByteArray(packedValue, offset);
  }

  /**
   * Store the given hex sequence 
   * 
   * @param value
   * @throws NAsmException
   */
  public void setHexValue(String value) throws NAsmException {
    if (value == null || value.isEmpty()) {
      return;
    }
    value = value.toUpperCase(Locale.US);
    if (value.startsWith("X'")) {
      value = value.substring(2, value.length() - 1);
    } else if (value.startsWith("0X")) {
      value = value.substring(2);
    }
    byte[] hexString = NAsmStrings.fromHex(value);
    if (array != null && this.array > 0 && hexString.length == this.length) {
      //If this is an initialization all the occurrences must be set
      logger.debug("\n\n\n[NAsmField] Array set to verify: " + this.getName() + "\n\n\n"); //SQtmp debug catcher
    }
    this.setValue(hexString, 0);
  }

  /**
   * Store the given decimal value into a binary format 
   * 
   * @param value
   * @throws NAsmException
   */
  public void setBinaryValue(int value, int length) throws NAsmException {
    byte[] binaryValue = NAsmStrings.valueToBinaryBigEndian(value, length);
    this.setValue(binaryValue, 0);
  }

  //  // ICM   R15,B'1111',=X'40000000'
  //  public void storeCharUnderMask(int binaryMask, int hexMask) throws NAsmException {
  //    String hexString = Integer.toHexString(hexMask);
  //    String hexValue = NAsmStrings.fromHexToString(hexString);
  //    this.storeCharUnderMask(binaryMask, hexValue);
  //  }
  //
  //  public void storeCharUnderMask(int binaryMask, String hexValue) throws NAsmException {
  //    String bits = String.format("%4s", Integer.toBinaryString(binaryMask)).replaceAll(" ", "0"); // 4-bit Integer
  //    //Move length
  //    int len = 0;
  //    for (int idx = 0; idx < bits.length(); idx++) {
  //      if (bits.substring(idx, idx + 1).equals("1"))
  //        len++;
  //    }
  //    if (len == 0)
  //      return;
  //    byte[] buff = new byte[len];
  //    byte[] hexBytes = hexValue.getBytes(NAsmEnvironment.ENV_CHARSET());
  //    len = 0;
  //    for (int idx = 0; idx < bits.length(); idx++) {
  //      if (bits.substring(idx, idx + 1).equals("1")) {
  //        buff[len] = hexBytes[len];
  //        len++;
  //      }
  //    }
  //    this.setByteArray(buff, 0, buff.length);
  //  }

  public void translate(NAsmRegister register) throws NAsmException {
    translate(register.getPointedAddress().getAddressedField(), this.length, 0);
  }

  public void translate(NAsmRegister register, int length) throws NAsmException {
    translate(register.getPointedAddress().getAddressedField(), length, 0);
  }

  public void translate(NAsmField table) throws NAsmException {
    translate(table, this.length, 0);
  }

  public void translate(NAsmField table, int length) throws NAsmException {
    translate(table, length, 0);
  }

  /**
   * 
   * @param table
   * @param length
   * @throws NAsmException
   */
  public void translate(NAsmField table, int length, int offset) throws NAsmException {
    //    byte[] buffer = this.getValue();
    //    if (buffer.length < length) {
    //      length = buffer.length;
    //    }else {
    //      //Needs to read after the field
    //      if (buffer.length < length + offset) {
    //        buffer = this.getValue(0, buffer.length + offset + length);
    //      }
    //    }
    byte[] buffer = this.getValue(offset, length);
    //SQ - to manage fields shorter then 255 (f.i. 0XL256)
    //table.getValue();
    byte[] tableRepl = table.getByteString(0, 256).getBytes(NAsmEnvironment.ENV_CHARSET());
    //for (int idx = offset; idx < (offset + length); idx++) {
    for (int idx = 0; idx < length; idx++) {
      if (buffer[idx] == 0x15) {
        //logger.debug("");
      }
      if (buffer[idx] == 0x25) {
        //logger.debug("");
      }
      int pos = Byte.toUnsignedInt(buffer[idx]);
      int repl = tableRepl[pos];
      buffer[idx] = (byte) repl;
    }
    //this.setValue(buffer, 0);
    this.setValue(buffer, offset, length, false);
  }

  /**
   * 
   * @param translateTable
   * @return
   * @throws NAsmException
   */
  public boolean translateAndTest(NAsmField translateTable) throws NAsmException {
    return translateAndTest(translateTable, 0, this.getLengthBytes());
  }

  /**
   * 
   * @param translateTable
   * @param offset
   * @param length
   * @return
   * @throws NAsmException
   */
  public boolean translateAndTest(NAsmField translateTable, int offset, Integer length) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "translateAndTest");
    if (length == null) {
      length = this.getLengthBytes();
    }
    byte[] workingValue = this.getByteString(offset, length).getBytes(NAsmEnvironment.ENV_CHARSET());
    byte[] tableValue;
    if (translateTable instanceof NAsmRegister) {
      NAsmAddress ra = ((NAsmRegister) translateTable).getPointedAddress();
      tableValue = ra.getAddressedField().getValue(ra.getOffset(), 256);
    } else {
      //SQ - to manage fields shorter then 255 (f.i. 0XL256)
      //tableValue = translateTable.getValue();
      tableValue = translateTable.getByteString(0, 256).getBytes(NAsmEnvironment.ENV_CHARSET());
    }
    for (int idx = 0; idx < workingValue.length; idx++) {
      int value = workingValue[idx] & 0xff;
      int replValue = tableValue[value] & 0xff;
      if (replValue > 0) {
        NAsmEnvironment.registers[NAsmOnlineProgram.REG_2].set(replValue);
        NAsmEnvironment.registers[NAsmOnlineProgram.REG_1].setAddress(this);
        NAsmEnvironment.registers[NAsmOnlineProgram.REG_1].addAddress(idx + offset);
        NAsmEnvironment.statsEndMethod(this.getClass(), "translateAndTest");
        return true;
      }
    }
    NAsmEnvironment.statsEndMethod(this.getClass(), "translateAndTest");
    return false;
  }

  /**
   * 
   * @param field
   * @throws NAsmException
   */
  public void setHighBits(NAsmField field) throws NAsmException {
    setHighBits(field, 0);
  }

  /**
   * 
   * @param value
   * @throws NAsmException
   */
  public void setHighBits(NAsmString value) throws NAsmException {
    setHighBits(value.toString());
  }

  /**
   * 
   * @param value
   * @throws NAsmException
   */
  public void setHighBits(String value) throws NAsmException {
    NAsmField fld = this.getConstant(value);
    setHighBits(fld, 0);
  }

  //  public void setHighBits(int value) throws NAsmException {
  //    setHighBits(value, 0);
  //  }

  public void setHighBits(int value, int offset) throws NAsmException {
    if (value > 255 || value < 0) {
      throw new NAsmException("Values greater than 0xFF not supported.");
    }
    NAsmField fld = new NAsmField("DUMMY", NAsmFieldType.NUMERIC_BINARY, 0, 1);
    fld.set(value);
    setHighBits(fld, offset);
  }

  protected void setHighBits(NAsmField field, int offset) throws NAsmException {
    if (offset > 0) {
      //logger.debug("");
    }
    int length = field.getLength();
    byte[] buffer = this.getValue();
    if (buffer.length < field.length) {
      length = buffer.length;
    }
    byte[] fieldBuffer = field.getValue();
    final int shiftMod = 4 % 8;
    for (int idx = 0; idx < length; idx++) {
      int high_bits = (fieldBuffer[idx] >> 4) * 16;
      //int low_bits = buffer[idx] & 0xf;
      //int value = high_bits;// + low_bits;

      byte src = buffer[idx + offset];
      //Shift 4 bits left: i.e.  BC -> C0
      byte dst = (byte) (src << shiftMod);
      //Shift 4 bits right: i.e.  80 -> 08
      byte dst2 = (byte) ((0xff & dst) >>> shiftMod);

      buffer[idx + offset] = (byte) (high_bits | dst2);
    }
    this.setValue(buffer, 0);
  }

  /**
   * 
   * @param table
   * @param length
   * @throws NAsmException
   */
  public void setLowBits(NAsmField field) throws NAsmException {
    setLowBits(field, 0);
  }

  public void setLowBits(NAsmString value) throws NAsmException {
    setLowBits(value.toString());
  }

  public void setLowBits(String value) throws NAsmException {
    NAsmField fld = this.getConstant(value);
    setLowBits(fld, 0);
  }

  public void setLowBits(NAsmString value, int offset) throws NAsmException {
    setLowBits(value.toString(), offset);
  }

  public void setLowBits(String value, int offset) throws NAsmException {
    NAsmField fld = this.getConstant(value);
    setLowBits(fld, offset);
  }

  public void setLowBits(NAsmField field, int offset) throws NAsmException {
    int length = field.getLength();
    byte[] buffer = this.getValue();
    if (buffer.length < field.length) {
      length = buffer.length;
    }
    byte[] fieldBuffer = field.getValue();
    for (int idx = 0; idx < length; idx++) {
      //int high_bits = (buffer[idx] >> 4) * 16;
      int low_bits = fieldBuffer[idx] & 0xf;
      int value = low_bits; // + high_bits;
      buffer[idx + offset] = (byte) value;
    }
    this.setValue(buffer, 0);
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
    } else {
      return NAsmStrings.repeatCharacter(' ', count);
    }
  }

  protected String _PAD(String value, int count) {
    return NAsmStrings.formatSpaces(value, count);
  }

  protected String _FILL(char value, int count) {
    return NAsmStrings.repeatCharacter(value, count);
  }

  public static int _EBCDIC_OFFSET(char value) {
    return NAsmStrings.getEbcdicSequence(value);
  }

  public static String _INT_TO_BYTES(int value, int length) {
    return _INT_TO_BYTES(value, length, 1);
  }

  public static String _INT_TO_BYTES(int value, int length, int array) {
    String hexString = NAsmStrings.formatZeroes(Integer.toHexString(value), length * 2);
    String hexValue = NAsmStrings.fromHexToString(hexString);
    String rc = hexValue;
    if (array > 1) {
      for (int idx = 0; idx < array; idx++) {
        rc += hexValue;
      }
    }
    return rc;
  }

  /**
   * @return the savedRegisters
   */
  public NAsmRegister[] getSavedRegisters(String programName) {
    if (this.savedRegisters == null) {
      return null;
    }
    return savedRegisters.get(programName);
  }

  /**
   * 
   * @param programName
   * @param savedRegisters
   */
  public void setSavedRegisters(String programName, NAsmRegister[] savedRegisters) {
    if (this.savedRegisters == null) {
      this.savedRegisters = new HashMap<>();
    }
    this.savedRegisters.put(programName, savedRegisters);
  }

  /**
   * 
   * @param programName
   */
  public void removeSavedRegisters(String programName) {
    if (this.savedRegisters != null && this.savedRegisters.containsKey(programName)) {
      this.savedRegisters.remove(programName);
    }
  }

  /**
   * 
   * @return
   */
  public NAsmField getStorage() {
    NAsmField storage = this;
    while (storage.getParent() != null) {
      storage = storage.getParent();
    }
    return storage;
  }

  /**
   * @return the programInstance
   */
  public NAsmOnlineProgram getProgramInstance() {
    return programInstance;
  }

  /**
   * @return the storagePointer
   */
  public boolean isStoragePointer() {
    return storagePointer;
  }

  /**
   * @param storagePointer the storagePointer to set
   */
  public void setStoragePointer(boolean storagePointer) {
    this.storagePointer = storagePointer;
  }

  /**
   * @return the storage
   */
  public boolean isStorage() {
    return this.getName().startsWith(_STORAGE_RP);
  }

  /**
   * @return the storage
   */
  public boolean isGetmainStorage() {
    return this.getName().startsWith(_GETMAIN_STORAGE);
  }

  /**
   * SQ To shift packed decimal fields [i.e. to be used for SRP]
   * 
   * @param positions - number of semibytes to shift
   * @throws NAsmException
   */
  public void shiftPackedLeft(int positions) throws NAsmException {
    if (positions > 0) {
      byte[] b = this.getValue();
      int bytesToShift = (positions + 1) / 2;
      int semibyteToShift = positions % 2;
      int sign = b[b.length - 1] & 0x0F;
      //byte[] result = new byte[field.getLength()];

      //cleaning sign:
      b[b.length - 1] = (byte) (b[b.length - 1] & 0xF0);

      //shifting full bytes
      if (semibyteToShift == 0) {
        int pos = 0;
        for (int idx = bytesToShift; idx < b.length; idx++, pos++) {
          b[pos] = b[idx];
        }
        //filling with zeroes tail:
        for (; pos < b.length; pos++) {
          b[pos] = 0;
        }
      } else {
        //shifting semibytes:

        //Clean-up of first left-semibyte:
        b[0] = (byte) (b[0] & 0x0F);

        int pos = 0;
        for (int idx = bytesToShift - 1; idx < b.length; idx++, pos++) {
          int leftDigit = (b[idx] & 0xF0) >> 4;
          int rightDigit = (b[idx] & 0x0F);
          //moving right semibyte (to left)
          b[pos] = (byte) ((b[pos] & 0x0F) | rightDigit << 4);
          if (pos > 0) {
            //moving left semibyte (to right)
            b[pos - 1] = (byte) ((b[pos - 1] & 0xF0) | leftDigit);
          }
        }

        //filling with zeroes tail:
        for (--pos; pos < b.length; pos++) {
          b[pos] = 0;
        }
      }

      //restoring sign
      b[b.length - 1] = (byte) (b[b.length - 1] | sign);

      if (NAsmEnvironment.DEBUG()) {
        logger.debug("[shiftPackedLeft] Initial value:" + this);
      }

      this.setValue(b, 0);
    }
    if (NAsmEnvironment.DEBUG()) {
      logger.debug("[shiftPackedLeft] Shifted by: " + positions + ": " + this);
    }
  }

  /**
   * SQ To get numeric (double) value of stream of bytes interpreted as numeric
   * zoned [i.e. to be used in PACK]
   * 
   * @param offsetStart
   * @param offsetEnd
   * @throws NAsmException
   */
  public double getNumericZoned(int offsetStart, int offsetEnd) throws NAsmException {
    int length = offsetEnd - offsetStart;
    byte[] byteBuffer = new byte[length];
    System.arraycopy(this.getValue(offsetStart, length), 0, byteBuffer, 0, length);
    return getNumericZoned(byteBuffer);
  }

  /**
   * SQ To get numeric (double) value of stream of bytes interpreted as numeric
   * zoned [i.e. to be used in PACK]
   * 
   * @throws NAsmException
   */
  public double getNumericZoned() throws NAsmException {
    if (this.getArray() != null && this.getArray() > 0 && this.getLength() > 0) {
      return getNumericZoned(this.getValue(null, this.getLength())); //SQ TODO review these cases
    } else {
      return getNumericZoned(this.getValue());
    }
  }

  protected double getNumericZoned(byte[] byteBuffer) throws NAsmException {
    if (byteBuffer == null) {
      return 0;
    }
    double val = 0;
    try {
      // Getting Sign: 
      int zonedSign = byteBuffer[byteBuffer.length - 1] & 0xF0;

      // Creating a "clean" zoned (Left nibble ignored in ASM)
      for (int idx = 0; idx <= byteBuffer.length - 1; idx++) {
        //byteBuffer[idx] = (byte) (byteBuffer[idx] | 0xF0);
        byteBuffer[idx] = (byte) ((byteBuffer[idx] | 0x30) & 0x3F); //we can avoid the convertBytesToASCII
      }

      String value; //SQ using 0x30 above to avoid convertBytesToASCII
      //if (NAsmEnvironment.RUN_EBCDIC()) {
      //	value = new String(NAsmStrings.convertBytesToASCII(byteBuffer), NAsmEnvironment.ENV_CHARSET());
      //} else {
      value = new String(byteBuffer, NAsmEnvironment.ENV_CHARSET());
      //}
      //Blanks in the numeric fields will end up to a value zero
      if (value.trim().isEmpty()) {
        val = 0;
      } else {
        val = Double.valueOf(value); //SQ Just ignoring decimals

        // Restore negative value - isNegative() cannot be used: it calls again getNumeric!
        if (zonedSign == 0xD0) {
          val = val * -1;
        }
      }
    } catch (Exception ex) {
      String dump = dumpRecord();
      String message = "Invalid numeric value for field: " + this.getName() + " offset: " + this.getAbsoluteOffset() + ", hex value: 0x" + NAsmStrings.toHex(byteBuffer) + dump;
      //Emit a warning
      if (NAsmEnvironment.ALLOW_NUMERIC_MALFORMED()) {
        message += ", value will be converted to zero" + dump;
        NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.WARNING, message);
        return 0;
      }
      message += dump;
      throw new NAsmException(message);
    }
    return val;
  }

  /**
   * 
   * @return
   */
  public boolean hasValidId() {
    return id > 0;
  }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return the dsect
   */
  public Boolean isDsect() {
    if (dsect == null) {
      dsect = false;
      if (this._usingRegister != null) {
        dsect = true;
      } else {
        NAsmField parent = this.getParent();
        while (parent != null && !dsect) {
          if (parent.getUsingRegister() != null) {
            dsect = true;
          }
          parent = parent.getParent();
        }
      }
    }
    return dsect;
  }
  
	/*
	 * Setting condition code
	 */
	public static void compare(double left, int right) throws NAsmException {
		if (left > right) {
			NAsmEnvironment.setCOND_CODE(2);
		} else if (left < right) {
			NAsmEnvironment.setCOND_CODE(4);
		} else {
			NAsmEnvironment.setCOND_CODE(8);
		}
	}

}
