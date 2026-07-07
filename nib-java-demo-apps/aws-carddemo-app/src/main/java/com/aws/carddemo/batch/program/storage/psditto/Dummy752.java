package com.aws.carddemo.batch.program.storage.psditto;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.storage.NAsmField;

/**
 * 
 * <b>RP-Asm2Java - ASM370 to JAVA.</b> <br/>
 * <br/>
 * 
 * <br/>
 * 
 * @version 2.2.8
 * 
 * @author RP - Modern Systems
 */
public class Dummy752 extends NAsmField {

  // A 'Y' PLACED IN THIS BYTE.hexzeros
  private NAsmField dummy752001 = new NAsmField(this, "DUMMY752", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY752001, Len.DUMMY752001);
  private NAsmField dummy752002 = new NAsmField(this, "DUMMY752", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY752002, Len.DUMMY752002);

  /**
   * @return the dummy752001
   */
  public NAsmField getDummy752001() {
    return dummy752001;
  }

  /**
   * @return the dummy752002
   */
  public NAsmField getDummy752002() {
    return dummy752002;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy752(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY752);
    dummy752001.set("LCR");
    dummy752002.set(_LOW(Len.DUMMY752002));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY752001 = 3;
    public static final int DUMMY752002 = 1;

    public static final int DUMMY752 = 4;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY752001 = 0;
    public static final int DUMMY752002 = Pos.DUMMY752001 + Len.DUMMY752001;

  }

}
