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
public class Dummy755 extends NAsmField {

  private NAsmField dummy755001 = new NAsmField(this, "DUMMY755", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY755001, Len.DUMMY755001);
  private NAsmField dummy755002 = new NAsmField(this, "DUMMY755", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY755002, Len.DUMMY755002);

  /**
   * @return the dummy755001
   */
  public NAsmField getDummy755001() {
    return dummy755001;
  }

  /**
   * @return the dummy755002
   */
  public NAsmField getDummy755002() {
    return dummy755002;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy755(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY755);
    dummy755001.set("SRC");
    dummy755002.set(_LOW(Len.DUMMY755002));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY755001 = 3;
    public static final int DUMMY755002 = 1;

    public static final int DUMMY755 = 4;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY755001 = 0;
    public static final int DUMMY755002 = Pos.DUMMY755001 + Len.DUMMY755001;

  }

}
