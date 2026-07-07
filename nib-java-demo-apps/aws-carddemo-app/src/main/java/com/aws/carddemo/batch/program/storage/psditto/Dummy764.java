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
public class Dummy764 extends NAsmField {

  private NAsmField dummy764001 = new NAsmField(this, "DUMMY764", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY764001, Len.DUMMY764001);
  private NAsmField dummy764002 = new NAsmField(this, "DUMMY764", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY764002, Len.DUMMY764002);
  private NAsmField dummy764003 = new NAsmField(this, "DUMMY764", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY764003, Len.DUMMY764003);

  /**
   * @return the dummy764001
   */
  public NAsmField getDummy764001() {
    return dummy764001;
  }

  /**
   * @return the dummy764002
   */
  public NAsmField getDummy764002() {
    return dummy764002;
  }

  /**
   * @return the dummy764003
   */
  public NAsmField getDummy764003() {
    return dummy764003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy764(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY764);
    dummy764001.set("FRE");
    dummy764002.set(_LOW(Len.DUMMY764002));
    dummy764003.set(188);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY764001 = 3;
    public static final int DUMMY764002 = 1;
    public static final int DUMMY764003 = 4;

    public static final int DUMMY764 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY764001 = 0;
    public static final int DUMMY764002 = Pos.DUMMY764001 + Len.DUMMY764001;
    public static final int DUMMY764003 = Pos.DUMMY764002 + Len.DUMMY764002;

  }

}
