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
public class Exlst extends NAsmField {

  private NAsmField dummy831 = new NAsmField(this, "DUMMY831", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY831, Len.DUMMY831);
  private NAsmField dummy832 = new NAsmField(this, "DUMMY832", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY832, Len.DUMMY832);

  /**
   * @return the dummy831
   */
  public NAsmField getDummy831() {
    return dummy831;
  }

  /**
   * @return the dummy832
   */
  public NAsmField getDummy832() {
    return dummy832;
  }

  /**
   *
   * @throws NAsmException
   */
  public Exlst(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.EXLST);
    dummy831.setHexValue("0x07");
    dummy832.set(2272);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY831 = 1;
    public static final int DUMMY832 = 3;

    public static final int EXLST_SLOT = 0;
    public static final int EXLST = Len.DUMMY831
                                + Len.DUMMY832;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY831 = 0;
    public static final int DUMMY832 = Pos.DUMMY831 + Len.DUMMY831;

  }

}
