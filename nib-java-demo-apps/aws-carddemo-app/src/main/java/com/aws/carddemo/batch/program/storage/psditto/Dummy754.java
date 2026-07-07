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
public class Dummy754 extends NAsmField {

  private NAsmField dummy754001 = new NAsmField(this, "DUMMY754", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY754001, Len.DUMMY754001);
  private NAsmField dummy754002 = new NAsmField(this, "DUMMY754", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY754002, Len.DUMMY754002);

  /**
   * @return the dummy754001
   */
  public NAsmField getDummy754001() {
    return dummy754001;
  }

  /**
   * @return the dummy754002
   */
  public NAsmField getDummy754002() {
    return dummy754002;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy754(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY754);
    dummy754001.set("P/S");
    dummy754002.set(_LOW(Len.DUMMY754002));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY754001 = 3;
    public static final int DUMMY754002 = 1;

    public static final int DUMMY754 = 4;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY754001 = 0;
    public static final int DUMMY754002 = Pos.DUMMY754001 + Len.DUMMY754001;

  }

}
