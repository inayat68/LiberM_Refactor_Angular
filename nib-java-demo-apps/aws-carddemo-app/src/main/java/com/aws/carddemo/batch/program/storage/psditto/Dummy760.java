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
public class Dummy760 extends NAsmField {

  // RTN. SELECT SEGS WILL HAVE C'Y'
  private NAsmField dummy760001 = new NAsmField(this, "DUMMY760", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY760001, Len.DUMMY760001);
  private NAsmField dummy760002 = new NAsmField(this, "DUMMY760", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY760002, Len.DUMMY760002);
  private NAsmField dummy760003 = new NAsmField(this, "DUMMY760", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY760003, Len.DUMMY760003);

  /**
   * @return the dummy760001
   */
  public NAsmField getDummy760001() {
    return dummy760001;
  }

  /**
   * @return the dummy760002
   */
  public NAsmField getDummy760002() {
    return dummy760002;
  }

  /**
   * @return the dummy760003
   */
  public NAsmField getDummy760003() {
    return dummy760003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy760(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY760);
    dummy760001.set("SRC");
    dummy760002.set(_LOW(Len.DUMMY760002));
    dummy760003.set(180);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY760001 = 3;
    public static final int DUMMY760002 = 1;
    public static final int DUMMY760003 = 4;

    public static final int DUMMY760 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY760001 = 0;
    public static final int DUMMY760002 = Pos.DUMMY760001 + Len.DUMMY760001;
    public static final int DUMMY760003 = Pos.DUMMY760002 + Len.DUMMY760002;

  }

}
