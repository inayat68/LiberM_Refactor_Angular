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
public class Dummy759 extends NAsmField {

  // BYTE 4 USED BY OPTIONAL SELECT
  private NAsmField dummy759001 = new NAsmField(this, "DUMMY759", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY759001, Len.DUMMY759001);
  private NAsmField dummy759002 = new NAsmField(this, "DUMMY759", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY759002, Len.DUMMY759002);
  private NAsmField dummy759003 = new NAsmField(this, "DUMMY759", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY759003, Len.DUMMY759003);

  /**
   * @return the dummy759001
   */
  public NAsmField getDummy759001() {
    return dummy759001;
  }

  /**
   * @return the dummy759002
   */
  public NAsmField getDummy759002() {
    return dummy759002;
  }

  /**
   * @return the dummy759003
   */
  public NAsmField getDummy759003() {
    return dummy759003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy759(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY759);
    dummy759001.set("WRK");
    dummy759002.set(_LOW(Len.DUMMY759002));
    dummy759003.set(266);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY759001 = 3;
    public static final int DUMMY759002 = 1;
    public static final int DUMMY759003 = 4;

    public static final int DUMMY759 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY759001 = 0;
    public static final int DUMMY759002 = Pos.DUMMY759001 + Len.DUMMY759001;
    public static final int DUMMY759003 = Pos.DUMMY759002 + Len.DUMMY759002;

  }

}
