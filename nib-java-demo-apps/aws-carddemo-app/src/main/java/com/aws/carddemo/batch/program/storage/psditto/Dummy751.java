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
public class Dummy751 extends NAsmField {

  // ROUTINE. SEGMENTS SELECTED WILL HAVE
  private NAsmField dummy751001 = new NAsmField(this, "DUMMY751", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY751001, Len.DUMMY751001);
  private NAsmField dummy751002 = new NAsmField(this, "DUMMY751", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY751002, Len.DUMMY751002);

  /**
   * @return the dummy751001
   */
  public NAsmField getDummy751001() {
    return dummy751001;
  }

  /**
   * @return the dummy751002
   */
  public NAsmField getDummy751002() {
    return dummy751002;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy751(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY751);
    dummy751001.set("FRE");
    dummy751002.set(_LOW(Len.DUMMY751002));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY751001 = 3;
    public static final int DUMMY751002 = 1;

    public static final int DUMMY751 = 4;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY751001 = 0;
    public static final int DUMMY751002 = Pos.DUMMY751001 + Len.DUMMY751001;

  }

}
