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
public class Dummy766 extends NAsmField {

  private NAsmField dummy766001 = new NAsmField(this, "DUMMY766", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY766001, Len.DUMMY766001);
  private NAsmField dummy766002 = new NAsmField(this, "DUMMY766", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY766002, Len.DUMMY766002);
  private NAsmField dummy766003 = new NAsmField(this, "DUMMY766", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY766003, Len.DUMMY766003);

  /**
   * @return the dummy766001
   */
  public NAsmField getDummy766001() {
    return dummy766001;
  }

  /**
   * @return the dummy766002
   */
  public NAsmField getDummy766002() {
    return dummy766002;
  }

  /**
   * @return the dummy766003
   */
  public NAsmField getDummy766003() {
    return dummy766003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy766(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY766);
    dummy766001.set("MDL");
    dummy766002.set(_LOW(Len.DUMMY766002));
    dummy766003.set(270);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY766001 = 3;
    public static final int DUMMY766002 = 1;
    public static final int DUMMY766003 = 4;

    public static final int DUMMY766 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY766001 = 0;
    public static final int DUMMY766002 = Pos.DUMMY766001 + Len.DUMMY766001;
    public static final int DUMMY766003 = Pos.DUMMY766002 + Len.DUMMY766002;

  }

}
