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
public class Dummy753 extends NAsmField {

  private NAsmField dummy753001 = new NAsmField(this, "DUMMY753", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY753001, Len.DUMMY753001);
  private NAsmField dummy753002 = new NAsmField(this, "DUMMY753", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY753002, Len.DUMMY753002);

  /**
   * @return the dummy753001
   */
  public NAsmField getDummy753001() {
    return dummy753001;
  }

  /**
   * @return the dummy753002
   */
  public NAsmField getDummy753002() {
    return dummy753002;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy753(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY753);
    dummy753001.set("561");
    dummy753002.set(_LOW(Len.DUMMY753002));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY753001 = 3;
    public static final int DUMMY753002 = 1;

    public static final int DUMMY753 = 4;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY753001 = 0;
    public static final int DUMMY753002 = Pos.DUMMY753001 + Len.DUMMY753001;

  }

}
