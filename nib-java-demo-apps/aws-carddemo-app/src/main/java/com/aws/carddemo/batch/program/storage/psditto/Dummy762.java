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
public class Dummy762 extends NAsmField {

  private NAsmField dummy762001 = new NAsmField(this, "DUMMY762", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY762001, Len.DUMMY762001);
  private NAsmField dummy762002 = new NAsmField(this, "DUMMY762", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY762002, Len.DUMMY762002);
  private NAsmField dummy762003 = new NAsmField(this, "DUMMY762", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY762003, Len.DUMMY762003);

  /**
   * @return the dummy762001
   */
  public NAsmField getDummy762001() {
    return dummy762001;
  }

  /**
   * @return the dummy762002
   */
  public NAsmField getDummy762002() {
    return dummy762002;
  }

  /**
   * @return the dummy762003
   */
  public NAsmField getDummy762003() {
    return dummy762003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy762(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY762);
    dummy762001.set("561");
    dummy762002.set(_LOW(Len.DUMMY762002));
    dummy762003.set(184);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY762001 = 3;
    public static final int DUMMY762002 = 1;
    public static final int DUMMY762003 = 4;

    public static final int DUMMY762 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY762001 = 0;
    public static final int DUMMY762002 = Pos.DUMMY762001 + Len.DUMMY762001;
    public static final int DUMMY762003 = Pos.DUMMY762002 + Len.DUMMY762002;

  }

}
