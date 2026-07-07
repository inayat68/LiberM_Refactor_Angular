package com.nib.asm.storage;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.utils.NAsmEnvironment;
import com.nib.asm.utils.NAsmStrings;
//import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NAsmRegisterOptimized extends NAsmField {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static final Logger logger = LoggerFactory.getLogger(NAsmRegisterOptimized.class);
  
	private static int dumpIdx = 0;

  protected HashMap<String, NAsmField> unsingRegisteredFields = null;
  
  //Santix: performance tuning, store most common operations and values
  private Integer cachedInt = null;
  private NAsmAddress cachedAddress = null;
  
  public NAsmRegisterOptimized(String name) throws NAsmException {
    super(name, NAsmFieldType.NUMERIC_BINARY, 0, 4);
  }

  public NAsmRegisterOptimized(String name, int length) throws NAsmException {
    super(name, NAsmFieldType.NUMERIC_BINARY, 0, length);
  }

  /**
   * 
   * @param offset
   * @throws NAsmException
   */
  public void setAddress(String literal, int offset) throws NAsmException {
    NAsmField field = this.getConstant(literal);
    setAddress(field, 0);
  }
  
  /**
   * setAddress - version with byte array literal
   * @param literal [byte array]
   * @param offset
   * @throws NAsmException
   */
  public void setAddress(byte literal[], int offset) throws NAsmException {
  	NAsmField field = null;
    field = this.getConstant(literal);
    setAddress(field, 0);
  }
  
  /**
   * setAddress - version with byte array literal
   * @param literal [byte array]
   * @throws NAsmException
   */
  public void setAddress(byte literal[]) throws NAsmException {
  	setAddress(literal, 0);
  }

  @Override
  public void setAddress(NAsmField field) throws NAsmException {
    setAddress(field, 0);
  }

  @Override
  public void setAddress(NAsmField field, int offset) throws NAsmException {
    if (NAsmEnvironment.DEBUG()) {
      //SQtmp - security check:
      if (field instanceof NAsmRegisterOptimized && field.getPointedAddress() == null) {
        logger.warn("Register " + field.getName() + " not addressed!");
      }
    }
    setAddress(field, offset, 0);
  }

  public void setAddress(NAsmRegisterOptimized registerLeft, NAsmRegisterOptimized registerRight) throws NAsmException {
    setAddress(registerLeft, registerRight, 0);
  }
  
  /**
   * setAddress version to be used in case of "LA R1,0(Rx,Ry)"
   * It checks if registers are "addressed"; normal sum otherwise
   * 
   * @param registerLeft
   * @param registerRight
   * 
   * @throws NAsmException
   */
  public void setAddress(NAsmRegisterOptimized registerLeft, NAsmRegisterOptimized registerRight, int offset) throws NAsmException {
    if (registerLeft.getPointedAddress() == null && registerRight.getPointedAddress() != null) {
      setAddress(registerRight, registerLeft.getInt() + offset, 0);
    } else if (registerLeft.getPointedAddress() != null && registerRight.getPointedAddress() == null) {
      setAddress(registerLeft, registerRight.getInt() + offset, 0);
    } else if (registerLeft.getPointedAddress() == null && registerRight.getPointedAddress() == null) {
      //Just a sum
    	//logger.info("[DEBUG][setAddress] both registers #" + registerLeft.getName() + " and #" + registerRight.getName() + " not addressed. Simple sum into #" + this.getName() );
      set(registerRight.getInt() + registerLeft.getInt() + offset);
    } else {
      logger.warn("[WARN ][setAddress] both registers #" + registerLeft.getName() + " and #" + registerRight.getName() + " apparently addressed. Instruction to review.");
    	set(registerRight.getInt() + registerLeft.getInt() + offset); //default
    }
  }

  /**
   * 
   * @param address
   * @param offset
   * @throws NAsmException
   */
  public void setAddress(NAsmAddress address, Integer... offsets) throws NAsmException {
    setAddress(address.getAddressedField(), true, offsets);
  }

  /**
   * 
   * @param field
   * @param offsets
   * @throws NAsmException
   */
  public void setAddress(NAsmField field, Integer... offsets) throws NAsmException {
    setAddress(field, false, offsets);
  }

  /**
   * 
   * @param field
   * @param offset
   * @throws NAsmException
   */
  private void setAddress(NAsmField field, boolean ignorePointedAddress, Integer... offsets) throws NAsmException {
    this.dismissCache();
    NAsmEnvironment.statsStartMethod(this.getClass(), "setAddress");
    int offset = 0;
    for (int idx = 0; idx < offsets.length; idx++) {
      offset += offsets[idx];
    }
    //Setting up on itself
    if (this.getName().equals(field.getName()) //
        && this.getLengthBytes() == field.getLengthBytes() //
    ) {
      if (offset > 0) {
        this.increment(offset);
      } else if (offset < 0) {
        this.decrement(offset * -1);
      }
      NAsmEnvironment.statsEndMethod(this.getClass(), "setAddress");
      return;
    }
    NAsmAddress fa = field.getPointedAddress();
    if (field instanceof NAsmRegisterOptimized && fa != null) {
      this.dismissAddress(true);
      this.setPointedAddress(fa.getClone(fa.getOffset() + offset));
    } else if (field instanceof NAsmField && fa != null && !ignorePointedAddress) {
      this.dismissAddress(true);
      this.setPointedAddress(fa.getClone(fa.getOffset() + offset));
    } else if (field instanceof NAsmProgramInstance) {
      this.dismissAddress(true);
      NAsmProgramInstance program = (NAsmProgramInstance) field;
      //this.setAddress(program.getInstance().getStorage());
    } else {
      NAsmUsingInfo usingInfo = NAsmUsingInfo.extractUsingInfo(field);
      if (usingInfo != null) {
        NAsmAddress pa = usingInfo.getUsingRegister().getPointedAddress();
        int off = offset + usingInfo.getDeltaOffset() + pa.getOffset();
        this.dismissAddress(true);
        this.setPointedAddress(new NAsmAddress(pa.getAddressedField(), off));
      } else {
        //Another register which doesn't contain any address
        if (field instanceof NAsmRegisterOptimized) {
					int value = field.getBinaryAtRange(0, 4) + offset; //SQ
          this.set(value);
        } else {
          this.dismissAddress(true);
          this.setPointedAddress(new NAsmAddress(field, offset));
        }
      }
    }
    NAsmEnvironment.statsEndMethod(this.getClass(), "setAddress");
  }

  /**
   * 
   * @param field
   * @throws NAsmException
   */
  public void addAddress(NAsmField field) throws NAsmException {
    this.dismissCache();
    int offset = this.getInt();
    NAsmAddress pa = field.getPointedAddress();
    if (pa != null) {
      //Is it the register itself it is trying to restore?
      if (pa.getAddressedField() instanceof NAsmRegisterOptimized //
          && pa.getAddressedField().getName().equals(this.getName())) {
        //this.setPointedAddress(null);
        this.set(pa.getAddressedField().getInt());
      } else if (pa.getAddressedField() instanceof NAsmProgramInstance) {
        NAsmProgramInstance program = (NAsmProgramInstance) pa.getAddressedField();
        //this.setAddress(program.getInstance().getStorage(), offset);
      } else {
        this.setPointedAddress(pa.getClone(pa.getOffset() + offset));
      }
    } else {
      this.setPointedAddress(new NAsmAddress(field, offset));
    }
  }

  /**
   * 
   * @param value
   * @throws NAsmException
   */
  public void addAddress(int value) throws NAsmException {
    this.dismissCache();
    //Changed to use 7 bits
    if (value >= NAsmEnvironment.MEMORY_RANGE_MIN && value <= NAsmEnvironment.MEMORY_RANGE_MAX) {
      byte[] binaryValue = NAsmStrings.valueToBinaryBigEndian(value, 4);
      if (NAsmEnvironment.isValidAddress(binaryValue)) {
        NAsmField addr = new NAsmField("ADDR", NAsmFieldType.NUMERIC_BINARY, 0, 4);
        addr.setByteArray(binaryValue);
        this.addAddress(addr);
        return;
      }
    }
    this.increment(value);
  }

  /**
   * 
   * @param field
   * @throws NAsmException
   */
  public void subtractAddress(NAsmField field) throws NAsmException {
    this.dismissCache();
    Integer offsetAddr = null;
    NAsmAddress ta = this.getPointedAddress();
    NAsmAddress fa = field.getPointedAddress();
    if (ta != null) {
      offsetAddr = ta.getOffset();
    }
    if (fa != null) {
      if (offsetAddr != null) {
        this.set(offsetAddr - fa.getOffset());
      } else {
        //Shouldn't be a real case
				int offset = this.getInt();
        this.set(offset - fa.getOffset());
      }
    } else {
      if (ta != null) {
        int value = (ta.getAddressedField().getAbsoluteOffset() + ta.getOffset()) - field.getAbsoluteOffset();
        this.set(value);
      } else {
        subtractAddress(field.getInt());
      }
    }
  }

  /**
   * 
   * @param value
   * @throws NAsmException
   */
  public void subtractAddress(int value) throws NAsmException {
    this.decrement(value);
  }

  /**
   * Use this one for converting ST commands:
   * 
   * i.e.
   * 
   * ST    R8,4(R9)
   * 
   */
  @Override
  public void set(NAsmField field) throws NAsmException {
    this.set(field, 0);
  }

  /**
   * Use this one for converting ST commands:
   * 
   * i.e.
   * 
   * ST    R8,4(R9)
   * 
   */
  @Override
  public void set(NAsmField field, int offset) throws NAsmException {
    this.dismissCache();
    NAsmAddress ta = this.getPointedAddress();
    NAsmAddress fa = field.getPointedAddress();
    if (ta == null) {
      throw new NAsmException("Register " + this.getName() + " doesn't contain a valid address.");
    }
    if (fa != null) {
      NAsmAddress pointedAddress = fa.getClone();
      byte[] address = NAsmEnvironment.registerAddress(pointedAddress);
      ta.getAddressedField().setByteArray(address, ta.getOffset() + offset, address.length);
    } else {
      if (ta.getOffset() != 0) {
        //logger.debug(""); //SQtmp debug catcher
      }
      ta.getAddressedField().setByteString(field.getByteString(0, 4), ta.getOffset() + offset);
    }
  }

  @Override
  public void set(NAsmField field, int offset, boolean skipDismiss) throws NAsmException {
    this.dismissCache();
    NAsmAddress ta = this.getPointedAddress();
    if (ta == null) {
      throw new NAsmException("Register " + this.getName() + " doesn't contain a valid address.");
    }
    ta.getAddressedField().set(field, ta.getOffset() + offset, skipDismiss);
  }
  
  
  /**
   * Use this one for converting L commands:
   * 
   * i.e.
   * 
   * L     R2,CNTMEM04
   * 
   */
  public void load(NAsmField field) throws NAsmException {
    this.load(field, 0);
  }

  /**
   * Use this one for converting L commands:
   * 
   * i.e.
   * 
   * L     R2,CNTMEM04
   * 
   */
  public void load(NAsmAddress address) throws NAsmException {
    if (this.cachedAddress != null && cachedAddress.equals(address)) {
      return;
    }
    NAsmValue realValue = NAsmValue.resolveValue(address.getAddressedField());
    byte[] value;
    NAsmAddress registerAddress;
    if (realValue != null && realValue.getBaseAddress() != null) {
      registerAddress = realValue.getBaseAddress().getClone(realValue.getBaseAddressOffset());
    } else {
      registerAddress = address.getClone();
    }
    value = NAsmEnvironment.registerAddress(registerAddress);
    this.setByteArray(value);
    this.cachedAddress = address;
  }

  /**
   * Use this one for converting L commands:
   * 
   * i.e.
   * 
   * L     R2,CNTMEM04
   * 
   */
  public void load(NAsmField field, int offset) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "load");
    //byte[] value = field.getByteString(offset, 4).getBytes(NAsmEnvironment.ENV_CHARSET());
    byte[] value = field.getByteArray(offset, 4);
    if (NAsmEnvironment.isValidAddress(value)) {
      if (NAsmEnvironment.getAddress(value).getAddressedField() instanceof NAsmProgramInstance) {
        NAsmProgramInstance program = (NAsmProgramInstance) NAsmEnvironment.getAddress(value).getAddressedField();
        //this.setAddress(program.getInstance().getStorage());
        value = null;
      } else {
        NAsmAddress pointedAddress = NAsmEnvironment.getAddress(value);
        NAsmValue realValue = NAsmValue.resolveValue(pointedAddress.getAddressedField());
        if (realValue != null && realValue.getBaseAddress() != null) {
          pointedAddress = realValue.getBaseAddress().getClone(realValue.getBaseAddressOffset());
          value = NAsmEnvironment.registerAddress(pointedAddress);
        } else {
          pointedAddress = pointedAddress.getClone();
          value = NAsmEnvironment.registerAddress(pointedAddress);
        }
      }
    }
    if (value != null) {
      this.setByteArray(value);
    }
    NAsmEnvironment.statsEndMethod(this.getClass(), "load");
  }
  
  //SQ TODO review/rename - Replacement for LH
  public void loadHalf(NAsmField field) throws NAsmException {
    loadHalf(field, 0);
  }
  
  public void loadHalf(NAsmField field, int offset) throws NAsmException {
    //NAsmEnvironment.statsStartMethod(this.getClass(), "loadHalf");
    byte[] value2bytes = field.getByteString(offset, 2).getBytes(NAsmEnvironment.ENV_CHARSET());
    byte[] value = new byte[4]; 
    value[0] = 0; value[1] = 0;
    value[2] = value2bytes[0]; value[3] = value2bytes[1];
    setByteArray(value);
    //NAsmEnvironment.statsEndMethod(this.getClass(), "loadHalf");
  }

  // ICM   R15,B'1111',=X'40000000'
  public void insertCharUnderMask(int binaryMask, int hexMask) throws NAsmException {
    String hexString = Integer.toHexString(hexMask);
    String hexValue = NAsmStrings.fromHexToString(hexString);
    this.insertCharUnderMask(binaryMask, hexValue);
  }

  public void insertCharUnderMask(int binaryMask, String hexValue) throws NAsmException {
    insertCharUnderMask(binaryMask, hexValue, true, true);
  }

  public void insertCharUnderMask(int binaryMask, String hexValue, boolean isNative) throws NAsmException {
    insertCharUnderMask(binaryMask, hexValue, isNative, true);
  }
  
  public void insertCharUnderMask(int binaryMask, String hexValue, boolean isNative, boolean dismissPointedAddress) throws NAsmException {
    this.dismissCache();
    String bits = String.format("%4s", Integer.toBinaryString(binaryMask)).replaceAll(" ", "0"); // 4-bit Integer
    byte[] value = super.getValue();
    if (dismissPointedAddress) {
      this.dismissAddress(true);
    }
    //Move length
    int len = 0;
    for (int idx = 0; idx < bits.length(); idx++) {
      if (bits.substring(idx, idx + 1).equals("1")) {
        len++;
      }
    }
    if (len == 0) {
      return;
    }
    byte[] buff = new byte[4];
    byte[] hexBytes;
    if (isNative) {
      hexBytes = hexValue.getBytes(NAsmEnvironment.ENV_CHARSET());
    } else {
      if (NAsmEnvironment.RUN_EBCDIC()) {
      	//SQ was convertBytesToASCII
        hexBytes = NAsmStrings.convertBytesToEBCDIC(hexValue.getBytes(NAsmEnvironment.ENV_CHARSET()));
      } else {
        hexBytes = hexValue.getBytes(NAsmEnvironment.ENV_CHARSET());
      }
    }
    len = 0;
    for (int idx = 0; idx < bits.length(); idx++) {
      if (bits.substring(idx, idx + 1).equals("1")) {
        buff[idx] = hexBytes[len];
        len++;
      } else {
        buff[idx] = value[idx];
      }
    }
    //SQtmp no actions at all - just reg-value modification
    //this.setByteArray(buff, 0, buff.length);
    System.arraycopy(buff, 0, getValueInstance(), 0, 4);
  }

  // CLM   R15,B'1111',=X'40000000'
  public int compareLogicalMask(int binaryMask, int hexMask) throws NAsmException {
    String hexString = Integer.toHexString(hexMask);
    String hexValue = NAsmStrings.fromHexToString(hexString);
    return this.compareLogicalMask(binaryMask, hexValue);
  }

  public int compareLogicalMask(int binaryMask, String hexValue) throws NAsmException {
    return compareLogicalMask(binaryMask, hexValue, true);
  }

  public int compareLogicalMask(int binaryMask, String hexValue, boolean isNative) throws NAsmException {
    String bits = String.format("%4s", Integer.toBinaryString(binaryMask)).replaceAll(" ", "0"); // 4-bit Integer
    byte[] value = super.getValue();
    //Move length
    int len = 0;
    for (int idx = 0; idx < bits.length(); idx++) {
      if (bits.substring(idx, idx + 1).equals("1")) {
        len++;
      }
    }
    if (len == 0) {
      return 0; //SQ check this scenario
    }
    byte[] buff = new byte[4];
    byte[] hexBytes;
    if (isNative) {
      hexBytes = hexValue.getBytes(NAsmEnvironment.ENV_CHARSET());
    } else {
      if (NAsmEnvironment.RUN_EBCDIC()) {
        hexBytes = NAsmStrings.convertBytesToEBCDIC(hexValue.getBytes(NAsmEnvironment.ENV_CHARSET()));
      } else {
        hexBytes = hexValue.getBytes(NAsmEnvironment.ENV_CHARSET());
      }
    }

    len = 0;
    for (int idx = 0; idx < bits.length(); idx++) {
      if (bits.substring(idx, idx + 1).equals("1")) {
        buff[idx] = hexBytes[len];
        len++;
      } else {
        buff[idx] = value[idx];
      }
    }
    return this.getByteString(0, 4).compareTo(new String(buff, NAsmEnvironment.ENV_CHARSET()));
  }

  @Override
  public double getNumeric() throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      return pa.getAddressedField().getNumeric();
    } else {
      return super.getNumeric();
    }
  }

  @Override
  public int getInt() throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "getInt");
    NAsmAddress pa = this.getPointedAddress();
    int rc;
    if (pa != null) {
      //Extract the first 4 bytes from the pointed field
      int value = 0;
      if (pa.getAddressedField().getLengthBytes() == 4) {
        value = pa.getAddressedField().getInt();
      } else {
        byte[] buff = extractPointedAddress(pa);
        if (buff != null) {
          value = NAsmStrings.binaryBigEndianToValue(buff, this.getLength());
        }
      }
      rc = value;
    } else {
      rc = super.getInt();
    }
    NAsmEnvironment.statsEndMethod(this.getClass(), "getInt");
    return rc;
  }

  /**
   * 
   * @param checkPointedAddress
   * @return
   * @throws NAsmException
   */
  public int getInt(boolean checkPointedAddress) throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
    if (checkPointedAddress && pa != null) {
      //Extract the first 4 bytes from the pointed field
      int value = 0;
      if (pa.getAddressedField().getLengthBytes() == 4) {
        value = pa.getAddressedField().getInt();
      } else {
        byte[] buff = extractPointedAddress(pa);
        if (buff != null) {
          value = NAsmStrings.binaryBigEndianToValue(buff, this.getLength());
        }
      }
      return value;
    } else {
      return super.getInt();
    }
  }

  /**
   * 
   * @return
   * @throws NAsmException
   */
  public long getLong() throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      //Extract the first 4 bytes from the pointed field
      long value = 0;
      if (pa.getAddressedField().getLengthBytes() == 4) {
        value = pa.getAddressedField().getInt();
      } else {
        byte[] buff = extractPointedAddress(pa);
        if (buff != null) {
          value = NAsmStrings.binaryBigEndianToLongValue(buff, this.getLength());
        }
      }
      return value;
    } else {
      return NAsmStrings.binaryBigEndianToLongValue(super.getValue(0, this.getLengthBytes(), true), this.getLength());
    }
  }

  @Override
  public boolean translateAndTest(NAsmField translateTable) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      return pa.getAddressedField().translateAndTest(translateTable, pa.getOffset(), pa.getAddressedField().getLengthBytes());
    } else {
      return super.translateAndTest(translateTable);
    }
  }

  @Override
  public boolean translateAndTest(NAsmField translateTable, int offset, Integer length) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      return pa.getAddressedField().translateAndTest(translateTable, pa.getOffset() + offset, length);
    } else {
      return super.translateAndTest(translateTable, offset, this.getLengthBytes());
    }
  }

  @Override
  public void set(String value) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      if (NAsmEnvironment.RUN_EBCDIC()) {
        byte[] byteBufferEbcdic = NAsmStrings.convertBytesToEBCDIC(value.getBytes(NAsmEnvironment.ENV_CHARSET()));
        value = new String(byteBufferEbcdic, NAsmEnvironment.ENV_CHARSET());
      }
      pa.getAddressedField().setByteString(value, pa.getOffset());
    } else {
      if (value.length() > 4) {
        throw new NAsmException("Register " + this.getName() + " doesn't contain a valid address.");
      }
      this.dismissAddress();
      NAsmField fld = new NAsmField(_CONSTANT, NAsmFieldType.NUMERIC_BINARY, 0, 4);
      fld.set(value);
      super.set(fld, 0);
    }
  }

  @Override
  public void set(String value, int offset) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      if (NAsmEnvironment.RUN_EBCDIC()) {
        byte[] byteBufferAscii;
        byteBufferAscii = NAsmStrings.convertBytesToEBCDIC(value.getBytes(NAsmEnvironment.ENV_CHARSET()));
        value = new String(byteBufferAscii, NAsmEnvironment.ENV_CHARSET());
      }
      pa.getAddressedField().setByteString(value, pa.getOffset() + offset);
    } else {
      if (value.length() > 4 || (value.length() + offset) > 4) {
        throw new NAsmException("Register " + this.getName() + " doesn't contain a valid address.");
      }
      this.dismissAddress();
      NAsmField fld = new NAsmField(_CONSTANT, NAsmFieldType.NUMERIC_BINARY, 0, 4);
      fld.set(value);
      super.set(fld, offset);
    }
  }

  @Override
  public void set(int value) throws NAsmException {
    if (this.cachedInt != null && this.cachedInt == value) {
      return;
    }
    this.dismissCache();
    //Santix: performance tuning
    NAsmEnvironment.statsStartMethod(this.getClass(), "set");
    //this.dismissAddress();
    //NAsmField fld = new NAsmField(NAsmField._CONSTANT, NAsmFieldType.NUMERIC_BINARY, 0, 4);
    //fld.set(value);
    //super.set(fld, 0);
    super.setByteArray(NAsmStrings.valueToBinaryBigEndian(value, 4));
    cachedInt = value;
    NAsmEnvironment.statsEndMethod(this.getClass(), "set");
  }

	/**
	 * set
	 * 
	 * @param value: character to be stored as integer value (depending on RUN_EBCDIC)
	 * @throws NAsmException
	 */
	public void set(char value) throws NAsmException {
		set(NAsmEnvironment.RUN_EBCDIC() ? NAsmStrings.getEbcdicSequence(value) : (int) value);
	}

  @Override
  public void set(double value) throws NAsmException {
    this.dismissCache();
    this.dismissAddress();
    //    NAsmField fld = new NAsmField(NAsmField._CONSTANT, NAsmFieldType.NUMERIC_BINARY, 0, 4);
    //    fld.set(value);
    //    super.set(fld, 0);
    super.setByteArray(NAsmStrings.valueToBinaryBigEndian(value, 4));
  }

  @Override
	public void set(long value) throws NAsmException {
    this.dismissCache();
    this.dismissAddress();
    //    NAsmField fld = new NAsmField(NAsmField._CONSTANT, NAsmFieldType.NUMERIC_BINARY, 0, this.getLengthBytes());
    //    fld.set(value);
    //    super.set(fld, 0);
    super.setByteArray(NAsmStrings.valueToBinaryBigEndian(value, 4));
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final int PREVIEW_MAX_LEN = 2048; //SQtmp 1024;
    try {
      NAsmAddress pa = this.getPointedAddress();
      if (pa != null) {
        String preview;
        byte[] previewH = new byte[0];
        try {
          previewH = pa.getValue();
        } catch (Exception e) {
          try {
            previewH = pa.getValue(PREVIEW_MAX_LEN);
          } catch (Exception e2) {
            //e2.printStackTrace();
          }
        }

        if (previewH.length > PREVIEW_MAX_LEN) {
          previewH = Arrays.copyOfRange(previewH, 0, PREVIEW_MAX_LEN);
        }
        if (NAsmEnvironment.RUN_EBCDIC()) {
          byte[] byteBufferAscii;
          byteBufferAscii = NAsmStrings.convertBytesToASCII(previewH);
          preview = new String(byteBufferAscii, NAsmEnvironment.ENV_CHARSET());
        } else {
          preview = new String(previewH, NAsmEnvironment.ENV_CHARSET());
        }
        if (preview.length() > PREVIEW_MAX_LEN) {
          preview = preview.substring(0, PREVIEW_MAX_LEN);
        }
        String hexPreview = NAsmStrings.formatHexDump(previewH, NAsmEnvironment.RUN_EBCDIC());
        return "Pointing to : " + pa.getAddressedField().getName() + "\n" //
            + "Offset      : " + pa.getOffset() + "\n" //
            + "Length      : " + preview.length() + (preview.length() > PREVIEW_MAX_LEN ? " [dumped: " + PREVIEW_MAX_LEN + "]" : "") + "\n" //
            //+ "Data (0-" + PREVIEW_MAX_LEN + "): " + preview + "\n" //
            + "Data Hex    : \n" + "        _______________________________________________\n" + "        00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F\n" + "        -----------------------------------------------\n" + hexPreview
            + "        _______________________________________________\n" + "        00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F\n"

        ;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    //if (this._value == null)
    if (this.getValueInstance() == null) {
      return null;
    }
    try {
      return this.getString(false).toString();
    } catch (NAsmException e) {
      e.printStackTrace();
    }
    if (NAsmEnvironment.RUN_EBCDIC()) {
      byte[] byteBufferAscii = NAsmStrings.convertBytesToASCII(this.getValueInstance());
      return new String(byteBufferAscii, NAsmEnvironment.ENV_CHARSET());
    } else {
      return new String(this.getValueInstance(), NAsmEnvironment.ENV_CHARSET());
    }
  }

  /**
   * increment 
   * @param value to increment
   * @throws NAsmException
   */
  public void increment(int value) throws NAsmException {
  	increment(value, true);
  }
  
  /**
   * increment
   * @param value to increment
   * @param checkPointedAddress - false: ignore "pointed address"
   * @throws NAsmException
   */
  public void increment(int value, boolean checkPointedAddress) throws NAsmException {
    this.dismissCache();
    if (!checkPointedAddress) { //SQ
    	int v = this.getInt(false) + value;
      this.setValue(NAsmStrings.valueToBinaryBigEndian(v, 4), 0);
    }else {
      NAsmAddress ta = this.getPointedAddress();
	    if (ta != null) { 
	      ta.increment(value);
	    } else {
        int v = this.getInt() + value;
        //	      NAsmField fld = new NAsmField(NAsmField._CONSTANT, NAsmFieldType.NUMERIC_BINARY, 0, 4);
        //	      fld.set(v);
        //	      super.set(fld, 0);
        super.setByteArray(NAsmStrings.valueToBinaryBigEndian(v, 4));
	    }
    }
  }
  
  /**
   * 
   * @param registers
   * @throws NAsmException
   */
  public void increment(NAsmRegisterOptimized register) throws NAsmException {
    this.dismissCache();
    NAsmAddress ta = this.getPointedAddress();
    NAsmAddress pa = null;
    int v;
    if (ta != null) {
      v = ta.getOffset();
    } else {
      v = this.getInt();
    }
    NAsmAddress ra = register.getPointedAddress();
    if (ra != null) {
      //Set the address on the field pointed by the first register
      pa = ra;
      if (ta == null || ta.getAddressedField().getName().equals(pa.getAddressedField().getName())) {
        v += pa.getOffset();
      } else {
        v = (ta.getAddressedField().getAbsoluteOffset() + ta.getOffset()) // 
            + (pa.getAddressedField().getAbsoluteOffset() + pa.getOffset());
      }
    } else {
      v += register.getInt();
    }
    //If the register is set up on a field amend the pointer value
    if (ta != null) {
    	if (pa == null) {
    	  ta.change(v);
      } else {
        this.set(v);
      }
    } else {
      if (pa == null) {
        this.set(v);
      } else {
        this.setPointedAddress(pa.getClone(v));
      }
    }
  }

  /**
   * 
   * @param value
   * @throws NAsmException
   */
  public void decrement(int value) throws NAsmException {
    this.dismissCache();
    int v;
    NAsmAddress ta = this.getPointedAddress();
    if (ta != null) {
      ta.decrement(value);
      v = ta.getOffset();
    } else {
      v = this.getInt() - value;
      //      NAsmField fld = new NAsmField(NAsmField._CONSTANT, NAsmFieldType.NUMERIC_BINARY, 0, 4);
      //      fld.set(v);
      //      super.set(fld, 0);
      super.setByteArray(NAsmStrings.valueToBinaryBigEndian(v, 4));
    }
  }

  /**
   * 
   * @param registers
   * @throws NAsmException
   */
  public void decrement(NAsmRegisterOptimized register) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = null;
    int v;
    NAsmAddress ta = this.getPointedAddress();
    if (ta != null) {
      v = ta.getOffset();
    } else {
      v = this.getInt();
    }
    NAsmAddress ra = register.getPointedAddress();
    if (ra != null) {
      //Set the address on the field pointed by the first register
      pa = ra;
      if (ta == null || ta.getAddressedField().getName().equals(pa.getAddressedField().getName())) {
        v -= pa.getOffset();
      } else {
        v = (ta.getAddressedField().getAbsoluteOffset() + ta.getOffset()) // 
            - (pa.getAddressedField().getAbsoluteOffset() + pa.getOffset());
      }
    } else {
      v -= register.getInt();
    }
    //If the register is set up on a field amend the pointer value
    if (ta != null) {
      if (pa == null) {
        ta.change(v);
      } else {
        this.set(v);
      }
    } else {
      if (pa == null) {
        this.set(v);
      } else {
        this.setPointedAddress(pa.getClone(v));
      }
    }
  }

  /**
   * 
   * @return
   * @throws NAsmException
   */
  public boolean isEmpty() throws NAsmException {
    if (this.getPointedAddress() == null) {
      if (this.getNumeric() == 0) {
        return true;
      }
      return false;
    } else {
      return false;
    }
  }

  @Override
  public String getByteString() throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      return pa.getAddressedField().getByteString(pa.getOffset(), pa.getAddressedField().getLengthBytes() - pa.getOffset());
    } else {
      return super.getByteString();
    }
  }
  
  /**
   * SQ
   * @param resolvePointer : false to get the actual content (address) instead of the "resolvedPointedAddress" 
   * @return
   * @throws NAsmException
   */
  public String getByteString(boolean resolvePointer) throws NAsmException {
    if (resolvePointer) {
    	return getByteString(); //standard/original method
    } else {
      return super.getByteString(); //NAsmField's method (no pointed value)
    }
  }

  @Override
  public String getByteString(int offset, int length) throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      return pa.getAddressedField().getByteString(pa.getOffset() + offset, length);
    } else {
      return super.getByteString(offset, length);
    }
  }

  public byte[] getByteArray() throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      return pa.getValue(null);
    } else {
      return super.getValue();
    }
  }

  public byte[] getByteArray(Integer length) throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      return pa.getValue(length);
    } else {
      return super.getValue(0, length);
    }
  }

	/**
	 * 
	 * @param offset
	 * @param length
	 * @return
	 * @throws NAsmException
	 */
	@Override
	public byte[] getByteArray(int offset, int length) throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
		if (pa != null) {
			return pa.getAddressedField().getValue(pa.getOffset() + offset, length);
		} else {
			return super.getValue(offset, length);
		}
	}

  @Override
  public NAsmString getString() throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "getString");
    NAsmString rc = null;
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      if (NAsmEnvironment.RUN_EBCDIC()) {
        byte[] byteBufferAscii = NAsmStrings.convertBytesToASCII(pa.getValue());
        rc = new NAsmString(byteBufferAscii, NAsmEnvironment.ENV_CHARSET());
      } else {
        rc = new NAsmString(pa.getValue(), NAsmEnvironment.ENV_CHARSET());
      }
    } else {
      rc = super.getString();
    }
    NAsmEnvironment.statsEndMethod(this.getClass(), "getString");
    return rc;
  }

  @Override
  public NAsmString getString(int offset, int length) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "getStringOL");
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      NAsmField addressedField = pa.getAddressedField();
      //SQ security check
      if (addressedField.getLength() < offset ) { //it's another object actually!
      	if (! (addressedField.getType().equals(NAsmFieldType.ALPHANUMERIC) || addressedField.getType().equals(NAsmFieldType.ALPHANUMERIC_DBCS) || addressedField.getType().equals(NAsmFieldType.ALPHANUMERIC_MIXED))) {
      		//SQ probably going to execute incorrect getString
      		//throw new NAsmException("SQtmp - getString to check!");
      	  NAsmString rc = addressedField.getChars(pa.getOffset() + offset, length);
          NAsmEnvironment.statsEndMethod(this.getClass(), "getStringOL");
      		return rc;
      	}
      }
      NAsmString rc = pa.getAddressedField().getString(pa.getOffset() + offset, length);
      NAsmEnvironment.statsEndMethod(this.getClass(), "getStringOL");
      return rc;
    } else {
      byte[] byteBuffer = super.getValue(offset, length, true);
      NAsmString rc;
      if (NAsmEnvironment.RUN_EBCDIC()) {
        byte[] byteBufferAscii = NAsmStrings.convertBytesToASCII(byteBuffer);
        rc = new NAsmString(byteBufferAscii, NAsmEnvironment.ENV_CHARSET());
      } else {
        rc = new NAsmString(byteBuffer, NAsmEnvironment.ENV_CHARSET());
      }
      NAsmEnvironment.statsEndMethod(this.getClass(), "getStringOL");
      return rc;
    }
  }

  @Override
  public double getNumericAtRange(int offsetStart, int offsetEnd) throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      return pa.getAddressedField().getNumericAtRange(pa.getOffset() + offsetStart, pa.getOffset() + offsetEnd);
    } else {
      return super.getNumericAtRange(offsetStart, offsetEnd); //SQ TODO check if exception should be raised instead
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
  @Override
	public double getNumericZoned(int offsetStart, int offsetEnd) throws NAsmException {
  	NAsmAddress pa = getPointedAddress();
    if (pa != null) {
    	//SQ TODO check this change:
      //NAsmAddress paNew = NAsmAddress.resolvePointedAddress(pa);
      //return paNew.getAddressedField().getNumericZoned(pa.getOffset() + offsetStart, pa.getOffset() + offsetEnd);
    	return pa.getAddressedField().getNumericZoned(pa.getOffset() + offsetStart, pa.getOffset() + offsetEnd);
    } else {
    	throw new NAsmException("Register " + getName() + " not addressed.");
    }
	}

  @Override
  public int getBinaryAtRange(int offsetStart, int offsetEnd) throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      return pa.getAddressedField().getBinaryAtRange(pa.getOffset() + offsetStart, pa.getOffset() + offsetEnd);
    } else {
      return super.getBinaryAtRange(offsetStart, offsetEnd); //SQ TODO check if exception should be raised instead
    }
  }

  @Override
  public double getPacked() throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      return pa.getAddressedField().getPacked();
    } else {
      return super.getPacked(); //SQ TODO check if exception should be raised instead
    }
  }

  @Override
  public double getPackedAtRange(int offsetStart, int offsetEnd) throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      return pa.getAddressedField().getPackedAtRange(pa.getOffset() + offsetStart, pa.getOffset() + offsetEnd);
    } else {
      return super.getPackedAtRange(offsetStart, offsetEnd); //SQ TODO check if exception should be raised instead
    }
  }

  @Override
  public void setEdited(double value, Integer offset, Integer length) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      pa.getAddressedField().setEdited(value, pa.getOffset() + offset, length);
    } else {
      super.setEdited(value, offset, length);
    }
  }

  public int getMemoryOffset() throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      return pa.getAddressedField().getAbsoluteOffset() + pa.getOffset();
    } else {
      return super.getAbsoluteOffset();
    }
  }

  @Override
  public int compareTo(NAsmField field) throws NAsmException {
    return compareTo(field, 0, 4, false);
  }

  public int compareTo(NAsmField field, boolean byContent) throws NAsmException {
    return compareTo(field, 0, 4, byContent);
  }

  public int compareTo(NAsmField field, int offset, int length) throws NAsmException {
    return compareTo(field, offset, length, false);
  }

  public int compareTo(NAsmField field, int offset, int length, boolean byContent) throws NAsmException {
    NAsmEnvironment.statsStartMethod(this.getClass(), "compareTo");
    int rc = 0;
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      int posReg = pa.getAddressedField().getAbsoluteOffset() + pa.getOffset();
      int posField = 0;
      NAsmAddress fa = field.getPointedAddress();
      if (fa != null) {
        if (byContent) {
          int off = fa.getOffset() + offset;
          posField = fa.getAddressedField().getBinaryAtRange(off, off + length);
        } else {
          posField = fa.getAddressedField().getAbsoluteOffset() + fa.getOffset();
        }
      } else {
        posField = pa.getAddressedField().getBinaryAtRange(offset, offset + length);
      }
      rc = posReg - posField;
    } else {
      if (length == 4) {
        rc = super.getBinaryAtRange(offset, offset + length) - field.getInt();
      } else {
        rc = super.getBinaryAtRange(offset, offset + length) - field.getBinaryAtRange(0, length);
      }
    }
    NAsmEnvironment.statsStartMethod(this.getClass(), "compareTo");
    return rc;
  }

  @Override
  public boolean equals(NAsmField field) throws NAsmException {
    return equals(field, 0, 4, false);
  }

  public boolean equals(NAsmField field, boolean byContent) throws NAsmException {
    return equals(field, 0, 4, byContent);
  }

  @Override
  public boolean equals(int value) throws NAsmException {
    return equals(this.getConstant(value), 0, 4, false);
  }

  public boolean equals(int value, boolean byContent) throws NAsmException {
    return equals(this.getConstant(value), 0, 4, byContent);
  }

  public boolean equals(int value, int offset, int length) throws NAsmException {
    if (offset == 2 && length == 2) {
      //logger.debug(""); //just a debug catcher
    }
    return equals(value, offset, length, false);
  }

  public boolean equals(int value, int offset, int length, boolean byContent) throws NAsmException {
    if (offset == 2 && length == 2) {
      //logger.debug(""); //just a debug catcher
    }
    return equals(this.getConstant(value, length), offset, length, byContent);
  }

  public boolean equals(NAsmField field, int offset, int length) throws NAsmException {
    return equals(field, offset, length, false);
  }

  public boolean equals(NAsmField field, int offset, int length, boolean byContent) throws NAsmException {
    //SQtmp
    if (offset == 2 && length == 2) {
      //logger.debug(""); //just a debug catcher
    }
    int rc = this.compareTo(field, offset, length, byContent);
    return rc == 0;
  }

  @Override
  public boolean less(NAsmField field) throws NAsmException {
    return less(field, 0, 4, false);
  }

  public boolean less(NAsmField field, boolean byContent) throws NAsmException {
    return less(field, 0, 4, byContent);
  }

  @Override
  public boolean less(int value) throws NAsmException {
    return less(this.getConstant(value), 0, 4, false);
  }

  public boolean less(int value, boolean byContent) throws NAsmException {
    return less(this.getConstant(value), 0, 4, byContent);
  }

  public boolean less(int value, int offset, int length) throws NAsmException {
    //SQtmp
    if (offset == 2 && length == 2) {
      //logger.debug(""); //just a debug catcher
    }
    return less(this.getConstant(value, length), offset, length, false);
  }

  public boolean less(int value, int offset, int length, boolean byContent) throws NAsmException {
    return less(this.getConstant(value, length), offset, length, byContent);
  }

  public boolean less(NAsmField field, int offset, int length) throws NAsmException {
    return less(field, offset, length, false);
  }

  public boolean less(NAsmField field, int offset, int length, boolean byContent) throws NAsmException {
    //SQtmp
    if (offset == 2 && length == 2) {
      //logger.debug(""); //just a debug catcher
    }
    int rc = this.compareTo(field, offset, length, byContent);
    return rc < 0;
  }

  @Override
  public boolean greater(NAsmField field) throws NAsmException {
    return greater(field, 0, 4, false);
  }

  public boolean greater(NAsmField field, boolean byContent) throws NAsmException {
    return greater(field, 0, 4, byContent);
  }

  @Override
  public boolean greater(int value) throws NAsmException {
    return greater(this.getConstant(value), 0, 4, false);
  }

  public boolean greater(int value, boolean byContent) throws NAsmException {
    return greater(this.getConstant(value), 0, 4, byContent);
  }

  public boolean greater(int value, int offset, int length) throws NAsmException {
    return greater(this.getConstant(value, length), offset, length, false);
  }

  public boolean greater(int value, int offset, int length, boolean byContent) throws NAsmException {
    return greater(this.getConstant(value, length), offset, length, byContent);
  }

  public boolean greater(NAsmField field, int offset, int length, boolean byContent) throws NAsmException {
    //SQtmp
    if (offset == 2 && length == 2) {
      //logger.debug(""); //just a debug catcher
    }
    int rc = this.compareTo(field, offset, length, byContent);
    return rc > 0;
  }

  @Override
  public void setByteStringWithMask(String value, int mask) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      pa.getAddressedField().setByteStringWithMask(value, mask);
    } else {
      super.setByteStringWithMask(value, mask);
    }
  }

  @Override
  public void setByteStringWithMask(String value, int mask, int offset) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      pa.getAddressedField().setByteStringWithMask(value, mask, offset);
    } else {
      super.setByteStringWithMask(value, mask, offset);
    }
  }

  @Override
  public void setAsPacked(double value) throws NAsmException {
    this.setAsPacked(value, null);
  }

  @Override
  public void setAsPacked(double value, Integer length) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      pa.getAddressedField().setAsPacked(value, pa.getOffset(), length);
    } else {
      super.setAsPacked(value, length);
    }
  }
  
   
  //SQtmp - clone of correspondent "setAsPacked" to manage signed Vs unsigned packed instructions
  public void setAsPackedUnsigned(double value, Integer length) throws NAsmException {
  	NAsmStrings.isUnsignedPacked = true;
  	setAsPacked(value, length);
  	NAsmStrings.isUnsignedPacked = false;
  }

  @Override
  public void setAsPacked(double value, Integer offset, Integer length) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      pa.getAddressedField().setAsPacked(value, pa.getOffset() + offset, length);
    } else {
      super.setAsPacked(value, offset, length);
    }
  }

  @Override
  public void moveWithOffset(NAsmField field, int fromLength, int toLength) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      pa.getAddressedField().moveWithOffset(field, 0, fromLength, pa.getOffset(), toLength);
    } else {
      super.moveWithOffset(field, toLength, fromLength);
    }
  }

  @Override
  public void convertToZoned(String packedSequence, int offset, int length) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      pa.getAddressedField().convertToZoned(packedSequence, pa.getOffset() + offset, length);
    } else {
      super.convertToZoned(packedSequence, offset, length);
    }
  }

  @Override
  public int testUnderMask(int mask) throws NAsmException {
    return testUnderMaskAt(0, mask);
  }

  @Override
  public int testUnderMaskAt(int offset, int mask) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      return pa.getAddressedField().testUnderMaskAt(pa.getOffset() + offset, mask);
    } else {
      return super.testUnderMaskAt(offset, mask);
    }
  }

	/**
	 * Replacement for NR
	 * 
	 * @param mask
	 * @throws NAsmException
	 */
	public void and(NAsmRegisterOptimized mask) throws NAsmException {
    this.dismissCache();
		//Works on the register data area only
		super.andLogic(mask.getValueInstance(), 0, 4);
	}

	/**
	 * Replacement for N
	 * 
	 * @param mask
	 * @throws NAsmException
	 */
  @Override
  public void and(Integer mask) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
			throw new NAsmException("Security check - Register " + this.getName() + " addressed.");
			//      pa.getAddressedField().andAt(pa.getOffset(), mask);
    } else {
			NAsmField fMask = new NAsmField(_CONSTANT, NAsmFieldType.NUMERIC_BINARY, 0, 4);
			fMask.set(mask);
			//super.andAt(0, fMask);
			super.andLogic(fMask.getByteArray(0, 4), 0, 4);
		}
  }

  @Override
  public void andAt(Integer offset, int mask) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      pa.getAddressedField().andAt(pa.getOffset() + offset, mask);
    } else {
			//SQ
			//      NAsmField fMask = new NAsmField(NAsmField._CONSTANT, NAsmFieldType.NUMERIC_BINARY, 0, 4 - offset);
			//      fMask.set(mask);
			//      super.andAt(offset, fMask);
			throw new NAsmException("Register " + this.getName() + " not addressed.");
    }
  }

  //SQtmp - mask must be the HEX literal (i.e. "00FF")
  @Override
  public void andAt(int offset, int length, String mask) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      pa.getAddressedField().andAt(pa.getOffset() + offset, length, mask);
    } else {
      throw new NAsmException("Register " + this.getName() + " not addressed.");
    }
  }

  @Override
	/**
	 * Replacement for O instruction
	 */
  public void or(Integer mask) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
			throw new NAsmException("Security check - Register " + this.getName() + " addressed.");
			//      pa.getAddressedField().orAt(pa.getOffset(), mask);
    } else {
			NAsmField fMask = new NAsmField(_CONSTANT, NAsmFieldType.NUMERIC_BINARY, 0, 4);
			fMask.set(mask);
			//super.orAt(0, fMask);
			super.orLogic(fMask.getByteArray(0, 4), 0);
    }
  }

  @Override
  public void orAt(Integer offset, int mask) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      pa.getAddressedField().orAt(pa.getOffset() + offset, mask);
    } else {
			//      NAsmField fMask = new NAsmField(NAsmField._CONSTANT, NAsmFieldType.NUMERIC_BINARY, 0, 4 - offset);
			//      fMask.set(mask);
			//      super.orAt(offset, fMask);
			throw new NAsmException("Register " + this.getName() + " not addressed.");
    }
  }

	@Override
	//SQ Version for OC and OI - it must not use the register content
	public void orAt(int offset, byte[] maskBuffer) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
		if (pa != null) {
			pa.getAddressedField().orAt(pa.getOffset() + offset, maskBuffer);
		} else {
			throw new NAsmException("Register not addressed.");
		}
	}

	/**
	 * OR replacement [OR R1,R2]
	 * 
	 * @param mask
	 * @throws NAsmException
	 */
	public void or(NAsmRegisterOptimized mask) throws NAsmException {
    this.dismissCache();
		//Works on the register data area only
		//SQ super.or(mask); with this it'd go back and forth among NAsmField and NAsmRegister versions...
		super.orLogic(mask.getValueInstance(), 0);
	}

	/**
	 * O replacement [O R1,D2(X2,B2)]
	 * 
	 * @param mask
	 * @throws NAsmException
	 */
  @Override
  public void or(NAsmField mask) throws NAsmException {
		super.orLogic(mask.getByteArray(0, 4), 0);
  }

  @Override
  public void or(NAsmField mask, int length) throws NAsmException {
    this.dismissCache();
    //Works on the register data area only
    super.or(mask, length);
  }

	/**
	 * Replacement for XR
	 * 
	 * @param mask
	 * @throws NAsmException
	 */
	public void xor(NAsmRegisterOptimized mask) throws NAsmException {
    this.dismissCache();
		//Works on the register data area only
		super.xorLogic(mask.getValueInstance(), 0, 4);
	}

  public void shiftBitsRight(int value) throws NAsmException {
    this.dismissCache();
    NAsmAddress ta = this.getPointedAddress();
  	if (ta != null) {
  	  //logger.debug(""); //SQtmp debug catcher
  	}
  	//long rc = this.getLong() / (long) Math.pow(2, value);
  	long rc = NAsmStrings.binaryBigEndianToLongValue(super.getValue(0, this.getLengthBytes(), true), this.getLength());
  	rc = rc / (long) Math.pow(2, value);
    this.set(rc);
  }

  public void shiftBitsLeft(int value) throws NAsmException {
    int rc = this.getInt() * (int) Math.pow(2, value);
    this.set(rc);
  }

  @Override
  public void setHighBits(NAsmField field) throws NAsmException {
    this.setHighBits(field, 0);
  }

  @Override
  public void setHighBits(NAsmString value) throws NAsmException {
    this.setHighBits(value.toString());
  }

  @Override
  public void setHighBits(String value) throws NAsmException {
    NAsmField fld = this.getConstant(value);
    this.setHighBits(fld, 0);
  }

  @Override
  public void setHighBits(int value, int offset) throws NAsmException {
    NAsmField fld = this.getConstant(value);
    setHighBits(fld, offset);
  }

  @Override
	protected void setHighBits(NAsmField table, int offset) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      pa.getAddressedField().setHighBits(table, offset + pa.getOffset());
    } else {
      super.setHighBits(table, offset);
    }
  }

  @Override
  public void setLowBits(NAsmField field) throws NAsmException {
    this.setLowBits(field, 0);
  }

  @Override
  public void setLowBits(NAsmString value) throws NAsmException {
    this.setLowBits(value.toString());
  }

  @Override
  public void setLowBits(String value) throws NAsmException {
    NAsmField fld = this.getConstant(value);
    setLowBits(fld, 0);
  }

  @Override
  public void setLowBits(NAsmField table, int offset) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      pa.getAddressedField().setLowBits(table, offset + pa.getOffset());
    } else {
      super.setLowBits(table, offset);
    }
  }

  @Override
  public void translate(NAsmField table, int length) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      pa.getAddressedField().translate(table, length, pa.getOffset());
    } else {
      super.translate(table, length);
    }
  }

  @Override
  public void translate(NAsmField table, int length, int offset) throws NAsmException {
    this.dismissCache();
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
      pa.getAddressedField().translate(table, length, offset + pa.getOffset());
    } else {
      super.translate(table, length, offset);
    }
  }

  public void translate(String string, int length) throws NAsmException {
    if (string.length() < 256) {
			string = string + _LOW(256 - string.length());
		}
    translate(getConstantS(string), length);
  }
  

  @Override
  public void setByteString(String value) throws NAsmException {
    this.setByteString(value, true, 0);
  }

  @Override
  public void setByteString(String value, int offset) throws NAsmException {
    this.setByteString(value, true, offset);
  }

  public void setByteString(String value, boolean resetPointer) throws NAsmException {
    this.setByteString(value, resetPointer, 0);
  }

  public void setByteString(String value, boolean resetPointer, int offset) throws NAsmException {
    this.dismissCache();
    if (resetPointer) {
      //      if (this.getInt() > 255 && value.length() != 4) {
      //        logger.info("[WARNING] check register usage");
      //      }
      this.dismissAddress();
      super.setByteString(value, offset);
    } else {
      NAsmAddress pa = this.getPointedAddress();
      if (pa != null) {
        pa.getAddressedField().setByteString(value, pa.getOffset() + offset);
      } else {
        super.setByteString(value, offset);
      }
    }
  }

  @Override
  public void setByteArray(byte[] value) throws NAsmException {
    this.setByteArray(value, 0, true, value.length);
  }

  @Override
  public void setByteArray(byte[] value, int offset, Integer length) throws NAsmException {
    this.setByteArray(value, offset, true, length);
  }

  public void setByteArray(byte[] value, boolean resetPointer) throws NAsmException {
    setByteArray(value, 0, resetPointer, value.length);
  }

  public void setByteArray(byte[] value, int offset, boolean resetPointer, Integer length) throws NAsmException {
    this.dismissCache();
    if (resetPointer) {
      this.dismissAddress(true);
      super.setByteArray(value, offset, length);
    } else {
      NAsmAddress pa = this.getPointedAddress();
      if (pa != null) {
        pa.getAddressedField().setByteArray(value, pa.getOffset() + offset, length);
      } else {
      	super.setByteArray(value, offset, length);
      }
    }
  }

  public int setLong(NAsmRegisterOptimized register) throws NAsmException {
    this.dismissCache();
    NAsmEnvironment.statsStartMethod(this.getClass(), "setLong");
    int targetReg = Integer.parseInt(this.getName()); //target == left == baseReg
    int sourceReg = Integer.parseInt(register.getName()); //source == right == workingReg
    targetReg++;
    if (targetReg > 15) {
      targetReg = 0;
    }
    sourceReg++;
    if (sourceReg > 15) {
      sourceReg = 0;
    }

    int targetLen = NAsmEnvironment.registers[targetReg].getInt() & 0x00FFFFFF; //SQ actual len to work with
    int sourceLen = NAsmEnvironment.registers[sourceReg].getInt() & 0x00FFFFFF; //SQ actual len to work with
    char repChar = NAsmEnvironment.registers[sourceReg].getByteString().charAt(0); //SQ pad char

    if (NAsmEnvironment.registers[sourceReg].getByteString().substring(1, 4).equals(_LOW(3))) { //special case:
      this.setByteArray(NAsmStrings.repeatCharacter(repChar, targetLen).getBytes(NAsmEnvironment.ENV_CHARSET()), false);
    } else {
      int len = (targetLen <= sourceLen) ? targetLen : sourceLen;
      byte[] buff = new byte[len];
      System.arraycopy(register.getByteArray(len), 0, buff, 0, len);
      this.setByteArray(buff, false);
      if (targetLen > sourceLen) {
        this.setByteArray(NAsmStrings.repeatCharacter(repChar, targetLen - sourceLen).getBytes(NAsmEnvironment.ENV_CHARSET()), len, false, targetLen - sourceLen);
      }
    }
    this.getPointedAddress().increment(targetLen);
    NAsmAddress ra = register.getPointedAddress();
    if (ra == null) {
      register.set(sourceLen);
    } else {
      ra.increment(sourceLen);
    }
    NAsmEnvironment.registers[targetReg].set(0);
    NAsmEnvironment.registers[sourceReg].set(0);

    NAsmEnvironment.statsEndMethod(this.getClass(), "setLong");
    //SQ TODO set the actual session's _COND_CODE
    return targetLen == sourceLen ? 0 : (targetLen > sourceLen ? 2 : 3);
  }

  public int compareLong(NAsmField register) throws NAsmException {
    int baseReg = Integer.parseInt(this.getName());
    int workingReg = Integer.parseInt(register.getName());
    NAsmAddress ra1 = NAsmEnvironment.registers[baseReg].getPointedAddress();
    NAsmAddress ra2 = NAsmEnvironment.registers[workingReg].getPointedAddress();
    if (ra1 == null) {
      throw new NAsmException("Register " + baseReg + " doesn't contain a valid address.");
    }
    if (ra2 == null) {
      throw new NAsmException("Register " + workingReg + " doesn't contain a valid address.");
    }
    NAsmField f1 = ra1.getAddressedField();
    int off1 = ra1.getOffset();
    NAsmField f2 = ra2.getAddressedField();
    int off2 = ra2.getOffset();
    baseReg++;
    if (baseReg > 15) {
      baseReg = 0;
    }
    workingReg++;
    if (workingReg > 15) {
      workingReg = 0;
    }
    int baseLen = NAsmEnvironment.registers[baseReg].getInt();
    //int workingLen = NAsmEnvironment.registers[workingReg].getInt();
    String s1 = f1.getByteString(off1, baseLen);
    String s2 = f2.getByteString(off2, baseLen);
    return s1.compareTo(s2);
  }

  public void divide_OLD(NAsmRegisterOptimized register) throws NAsmException {
    int baseReg = Integer.parseInt(this.getName());
    int workingReg = Integer.parseInt(register.getName());
    baseReg++;
    if (baseReg > 15) {
      baseReg = 0;
    }
    int dividend = NAsmEnvironment.registers[baseReg].getInt();
    int divisor = NAsmEnvironment.registers[workingReg].getInt();
    int quotient = dividend / divisor;
    int remainder = dividend % divisor;
    NAsmEnvironment.registers[baseReg].set(quotient);
    this.set(remainder);
  }
  
  //SQtmp
  public void divide(NAsmRegisterOptimized register) throws NAsmException {
  	long dividend = this.getPair();
  	int divisor = register.getInt();
    int quotient = (int) dividend / divisor;
    int remainder = (int) dividend % divisor;
    //even-reg:
    this.set(remainder);
    //odd-reg:
    int oddReg = Integer.parseInt(this.getName()) + 1;
    NAsmEnvironment.registers[oddReg].set(quotient);
  }

  public void divideDoubleLogical(int value) throws NAsmException {
    NAsmRegisterOptimized dr = new NAsmRegisterOptimized("DOUBLE_REG", 8);
    int baseReg = Integer.parseInt(this.getName());
    int remainderReg = baseReg + 1;
    if (remainderReg > 15) {
      remainderReg = 0;
    }
    dr.setByteArray(NAsmEnvironment.registers[baseReg].getByteArray(4), 0, 4);
    dr.setByteArray(NAsmEnvironment.registers[remainderReg].getByteArray(4), 4, 4);
    long dividend = dr.getLong() / (long) Math.pow(2, value);
    byte[] bLong = NAsmStrings.valueToBinaryBigEndian(dividend, dr.getLengthBytes());
    dr.setByteArray(bLong);
    NAsmEnvironment.registers[baseReg].setByteArray(dr.getValue(0, 4));
    NAsmEnvironment.registers[remainderReg].setByteArray(dr.getValue(4, 4));
  }

  public void multiply_OLD(NAsmRegisterOptimized register) throws NAsmException {
    int baseReg = Integer.parseInt(this.getName());
    int workingReg = Integer.parseInt(register.getName());
    int baseRegPlusOne = baseReg + 1;
    if (baseRegPlusOne > 15) {
      baseRegPlusOne = 0;
    }
    NAsmField basePlusOne = new NAsmField("BIG", NAsmFieldType.NUMERIC_BINARY, 0, 8);
    basePlusOne.setByteString(NAsmEnvironment.registers[baseReg].getByteString(0, 4), 0);
    basePlusOne.setByteString(NAsmEnvironment.registers[baseRegPlusOne].getByteString(0, 4), 4);
    basePlusOne.set(basePlusOne.getInt() * NAsmEnvironment.registers[workingReg].getInt());
    NAsmEnvironment.registers[baseReg].setByteString(basePlusOne.getByteString(0, 4), 0);
    NAsmEnvironment.registers[baseRegPlusOne].setByteString(basePlusOne.getByteString(4, 4), 0);
  }
  
  public void multiply(NAsmRegisterOptimized register) throws NAsmException {
    int baseReg = Integer.parseInt(this.getName());
    int baseRegPlusOne = baseReg + 1;
    if (baseRegPlusOne > 15) {
      baseRegPlusOne = 0;
    }
    this.setPair(NAsmEnvironment.registers[baseRegPlusOne].getNumeric() * register.getInt());
  }
  
  /**
   * special version of "set" to set a pair of registers 
   * (to be used in instructions like "M" working with pairs of registers)
   * 
   * @param value (double) to be stored in pairs of registers
   *
   */
  public void setPair(double value) throws NAsmException {
    int baseReg = Integer.parseInt(this.getName());
    int baseRegPlusOne = baseReg + 1;
    if (baseRegPlusOne > 15) {
      baseRegPlusOne = 0;
    }
    NAsmField basePlusOne = new NAsmField("BIG", NAsmFieldType.NUMERIC_BINARY, 0, 8);
    basePlusOne.set(value);
    NAsmEnvironment.registers[baseReg].setByteString(basePlusOne.getByteString(0, 4), 0);
    NAsmEnvironment.registers[baseRegPlusOne].setByteString(basePlusOne.getByteString(4, 4), 0);
  }
  
  /**
   * "getter" to work with even/odd pair of registers   
   * (f.i. to be used in instructions like "DR")
   *
   */
  public long getPair() throws NAsmException {
  	int evenReg = Integer.parseInt(this.getName());
    int oddReg = evenReg + 1;
    //NAsmField pair = new NAsmField("PAIR", NAsmFieldType.NUMERIC_BINARY, 0, 8);
    NAsmRegisterOptimized pairReg = new NAsmRegisterOptimized("PAIR", 8);
    pairReg.setByteArray(NAsmEnvironment.registers[evenReg].getValue(0,4), 0, 4); // SQ replaced getByteArray
    pairReg.setByteArray(NAsmEnvironment.registers[oddReg].getValue(0,4), 4, 4);  // SQ
    return pairReg.getLong();
  }


  /**
   * @return the unsingRegisteredField
   */
  public NAsmField getUnsingRegisteredField() {
    return getUnsingRegisteredField(null);
  }

  public NAsmField getUnsingRegisteredField(String program) {
    if (unsingRegisteredFields == null) {
      return null;
    }
    if (program == null) {
      return unsingRegisteredFields.get(NAsmEnvironment.getCurrentInstance().getProgramId());
    } else {
      return unsingRegisteredFields.get(program);
    }
  }

  public HashMap<String, NAsmField> getUnsingRegisteredFields() {
    return this.unsingRegisteredFields;
  }

  /**
   * 
   * @param field
   */
  public void addUsingField(NAsmField field) {
    if (this.unsingRegisteredFields == null) {
      this.unsingRegisteredFields = new HashMap<>();
    }
    this.unsingRegisteredFields.put(NAsmEnvironment.getCurrentInstance().getProgramId(), field);
  }

  /**
   * 
   */
  public void drop() {
    if (this.unsingRegisteredFields == null) {
      return;
    }
    NAsmField f = this.unsingRegisteredFields.get(NAsmEnvironment.getCurrentInstance().getProgramId());
    if (f != null) {
      f.dropUsing();
      this.unsingRegisteredFields.remove(NAsmEnvironment.getCurrentInstance().getProgramId());
    }
  }

  /**
   * 
   * @param pa
   * @return
   * @throws NAsmException
   */
  public static byte[] extractPointedAddress(NAsmAddress pa) throws NAsmException {
    byte[] buff = new byte[4];
    //Out of bound, assembler doesn't mind, just a chunk of memory
    if (pa.getOffset() + 4 <= pa.getAddressedField().getLengthBytes()) {
      System.arraycopy(pa.getAddressedField().getValue(), pa.getOffset(), buff, 0, buff.length);
      return buff;
    } else {
      //Point to the main area
      NAsmField parent = pa.getAddressedField().getParent();
      while (parent != null && parent.getParent() != null) {
        parent = parent.getParent();
      }
      if (parent != null) {
        int off = pa.getOffset() + pa.getAddressedField().getAbsoluteOffset();
        if (off + 4 <= parent.getLengthBytes()) {
          System.arraycopy(parent.getValue(), off, buff, 0, buff.length);
          return buff;
        }
      }
    }
    return null;
  }

  private void dismissAddress() throws NAsmException {
    dismissAddress(false);
  }

  /**
   * 
   * @throws NAsmException
   */
  private void dismissAddress(boolean skipValueErase) throws NAsmException {
    byte[] value = this.getValue(0, 4, true);
    if (NAsmEnvironment.isValidAddress(value)) {
      NAsmEnvironment.dismissAddress(value);
      if (!skipValueErase) {
        super.setByteString(_LOW(4), 0);
      }
    }
  }

  /**
   * Dump into file whatever area addressed by the register (offset and number of bytes to be provided)  
   * 
   * @param offset
   * @param lenght
   */
  public void dumpArea(int offset, int lenght) {
		String fileName = "dump_Reg" + this.getName() + "_#" + (++dumpIdx) + ".dump";
    try {
      byte[] dump = this.getByteArray(lenght);
      Files.write(Paths.get(fileName), dump);
    } catch (Exception e) {
      logger.info("[dumpArea][E] " + e.getMessage());
    }
  }

	/**
   * This method implements ED logic when the first parameter is a register
   * and a second parameter is a field:<br/>
   * <b>ED    3(6,R7),PACK4+5</b><br/>
   * 
   * Usage example:<br/>
   * <b>getReg7().setEdited(storage.getPack4(), 3, 6, 5);</b><br/>
   * 
   * @param NAsmRegister r_packed
   * @param int mask_offset
   * @param int mask_len
   * @param int packed_offset
   * @return void
   * @throws NAsmException
   */
	@Override
	public void setEdited_NEW(NAsmField f_packed, int mask_offset, int mask_len, int packed_offset) throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
    if (pa != null) {
			pa.getAddressedField().setEdited_NEW(f_packed, mask_offset + pa.getOffset(), mask_len, packed_offset);
    } else {
			//SQ
			//super.setEdited_NEW(f_packed, mask_offset, mask_len, packed_offset);
			throw new NAsmException("Register '" + this.getName() + "' not addressed.");
    }
	}

  @Override
  public void setEditedMK(NAsmField f_packed, int mask_offset, int mask_len) throws NAsmException {
    setEditedMK(f_packed, mask_offset, mask_len, 0);
  }
  
	/**
   * This method implements EDMK logic when the first parameter is a register
   * and a second parameter is a field:<br/>
   * <b>EDMK    3(6,R7),PACK4+5</b><br/>
   * 
   * Usage example:<br/>
   * <b>getReg7().setEdited(storage.getPack4(), 3, 6, 5);</b><br/>
   * 
   * @param NAsmRegister r_packed
   * @param int mask_offset
   * @param int mask_len
   * @param int packed_offset
   * @return void
   * @throws NAsmException
   */
	@Override
	public void setEditedMK(NAsmField f_packed, int mask_offset, int mask_len, int packed_offset) throws NAsmException {
    NAsmAddress pa = this.getPointedAddress();
		if (pa != null) {
			pa.getAddressedField().setEditedMK(f_packed, mask_offset + pa.getOffset(), mask_len, packed_offset);
		} else {
			throw new NAsmException("Register '" + this.getName() + "' not addressed.");
		}
	}
  
	private void dismissCache() {
	  this.cachedInt = null;
	  this.cachedAddress = null;
	}
	
	//SQ
	public int getByte(int pos) {
		return getValueInstance()[pos-1];
	}
  
}
