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
public class Dummy767 extends NAsmField {

  private NAsmField dummy767001 = new NAsmField(this, "DUMMY767", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY767001, Len.DUMMY767001);
  private NAsmField dummy767002 = new NAsmField(this, "DUMMY767", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY767002, Len.DUMMY767002);
  private NAsmField dummy767003 = new NAsmField(this, "DUMMY767", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY767003, Len.DUMMY767003);

  /**
   * @return the dummy767001
   */
  public NAsmField getDummy767001() {
    return dummy767001;
  }

  /**
   * @return the dummy767002
   */
  public NAsmField getDummy767002() {
    return dummy767002;
  }

  /**
   * @return the dummy767003
   */
  public NAsmField getDummy767003() {
    return dummy767003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy767(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY767);
    dummy767001.set("PSG");
    dummy767002.set(_LOW(Len.DUMMY767002));
    dummy767003.set(272);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY767001 = 3;
    public static final int DUMMY767002 = 1;
    public static final int DUMMY767003 = 4;

    public static final int DUMMY767 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY767001 = 0;
    public static final int DUMMY767002 = Pos.DUMMY767001 + Len.DUMMY767001;
    public static final int DUMMY767003 = Pos.DUMMY767002 + Len.DUMMY767002;

  }

}
