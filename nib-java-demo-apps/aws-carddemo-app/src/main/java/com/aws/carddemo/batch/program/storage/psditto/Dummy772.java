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
public class Dummy772 extends NAsmField {

  private NAsmField dummy772001 = new NAsmField(this, "DUMMY772", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY772001, Len.DUMMY772001);
  private NAsmField dummy772002 = new NAsmField(this, "DUMMY772", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY772002, Len.DUMMY772002);
  private NAsmField dummy772003 = new NAsmField(this, "DUMMY772", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY772003, Len.DUMMY772003);

  /**
   * @return the dummy772001
   */
  public NAsmField getDummy772001() {
    return dummy772001;
  }

  /**
   * @return the dummy772002
   */
  public NAsmField getDummy772002() {
    return dummy772002;
  }

  /**
   * @return the dummy772003
   */
  public NAsmField getDummy772003() {
    return dummy772003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy772(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY772);
    dummy772001.set("LCM");
    dummy772002.set(_LOW(Len.DUMMY772002));
    dummy772003.set(258);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY772001 = 3;
    public static final int DUMMY772002 = 1;
    public static final int DUMMY772003 = 4;

    public static final int DUMMY772 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY772001 = 0;
    public static final int DUMMY772002 = Pos.DUMMY772001 + Len.DUMMY772001;
    public static final int DUMMY772003 = Pos.DUMMY772002 + Len.DUMMY772002;

  }

}
