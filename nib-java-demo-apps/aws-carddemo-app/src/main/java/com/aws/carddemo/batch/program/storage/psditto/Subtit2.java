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
public class Subtit2 extends NAsmField {

  private NAsmField dummy878 = new NAsmField(this, "DUMMY878", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY878, Len.DUMMY878);
  private NAsmField dummy879 = new NAsmField(this, "DUMMY879", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY879, Len.DUMMY879);
  private NAsmField dsn = new NAsmField(this, "DSN", NAsmFieldType.ALPHANUMERIC, Pos.DSN, Len.DSN);
  private NAsmField dummy881 = new NAsmField(this, "DUMMY881", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY881, Len.DUMMY881);

  /**
   * @return the dummy878
   */
  public NAsmField getDummy878() {
    return dummy878;
  }

  /**
   * @return the dummy879
   */
  public NAsmField getDummy879() {
    return dummy879;
  }

  /**
   * @return the dsn
   */
  public NAsmField getDsn() {
    return dsn;
  }

  /**
   * @return the dummy881
   */
  public NAsmField getDummy881() {
    return dummy881;
  }

  /**
   *
   * @throws NAsmException
   */
  public Subtit2(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.SUBTIT2);
    dummy878.set(_SPACE(Len.DUMMY878));
    dummy879.set("* * DS NAME: ");
    dummy881.set(_SPACE(Len.DUMMY881));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY878 = 1;
    public static final int DUMMY879 = 13;
    public static final int DSN = 52;
    public static final int DUMMY881 = 67;

    public static final int SUBTIT2_SLOT = 133;
    public static final int SUBTIT2 = Len.DUMMY878
                                + Len.DUMMY879
                                + Len.DSN
                                + Len.DUMMY881;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY878 = 0;
    public static final int DUMMY879 = Pos.DUMMY878 + Len.DUMMY878;
    public static final int DSN = Pos.DUMMY879 + Len.DUMMY879;
    public static final int DUMMY881 = Pos.DSN + Len.DSN;

  }

}
