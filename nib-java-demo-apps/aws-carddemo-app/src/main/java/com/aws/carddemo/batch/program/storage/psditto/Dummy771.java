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
public class Dummy771 extends NAsmField {

  private NAsmField dummy771001 = new NAsmField(this, "DUMMY771", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY771001, Len.DUMMY771001);
  private NAsmField dummy771002 = new NAsmField(this, "DUMMY771", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY771002, Len.DUMMY771002);
  private NAsmField dummy771003 = new NAsmField(this, "DUMMY771", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY771003, Len.DUMMY771003);

  /**
   * @return the dummy771001
   */
  public NAsmField getDummy771001() {
    return dummy771001;
  }

  /**
   * @return the dummy771002
   */
  public NAsmField getDummy771002() {
    return dummy771002;
  }

  /**
   * @return the dummy771003
   */
  public NAsmField getDummy771003() {
    return dummy771003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy771(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY771);
    dummy771001.set("DBC");
    dummy771002.set(_LOW(Len.DUMMY771002));
    dummy771003.set(190);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY771001 = 3;
    public static final int DUMMY771002 = 1;
    public static final int DUMMY771003 = 4;

    public static final int DUMMY771 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY771001 = 0;
    public static final int DUMMY771002 = Pos.DUMMY771001 + Len.DUMMY771001;
    public static final int DUMMY771003 = Pos.DUMMY771002 + Len.DUMMY771002;

  }

}
