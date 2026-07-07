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
public class Dummy761 extends NAsmField {

  private NAsmField dummy761001 = new NAsmField(this, "DUMMY761", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY761001, Len.DUMMY761001);
  private NAsmField dummy761002 = new NAsmField(this, "DUMMY761", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY761002, Len.DUMMY761002);
  private NAsmField dummy761003 = new NAsmField(this, "DUMMY761", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY761003, Len.DUMMY761003);

  /**
   * @return the dummy761001
   */
  public NAsmField getDummy761001() {
    return dummy761001;
  }

  /**
   * @return the dummy761002
   */
  public NAsmField getDummy761002() {
    return dummy761002;
  }

  /**
   * @return the dummy761003
   */
  public NAsmField getDummy761003() {
    return dummy761003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy761(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY761);
    dummy761001.set("P/S");
    dummy761002.set(_LOW(Len.DUMMY761002));
    dummy761003.set(182);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY761001 = 3;
    public static final int DUMMY761002 = 1;
    public static final int DUMMY761003 = 4;

    public static final int DUMMY761 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY761001 = 0;
    public static final int DUMMY761002 = Pos.DUMMY761001 + Len.DUMMY761001;
    public static final int DUMMY761003 = Pos.DUMMY761002 + Len.DUMMY761002;

  }

}
