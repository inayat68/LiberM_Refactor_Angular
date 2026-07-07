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
public class Dummy776 extends NAsmField {

  private NAsmField dummy776001 = new NAsmField(this, "DUMMY776", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY776001, Len.DUMMY776001);
  private NAsmField dummy776002 = new NAsmField(this, "DUMMY776", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY776002, Len.DUMMY776002);
  private NAsmField dummy776003 = new NAsmField(this, "DUMMY776", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY776003, Len.DUMMY776003);

  /**
   * @return the dummy776001
   */
  public NAsmField getDummy776001() {
    return dummy776001;
  }

  /**
   * @return the dummy776002
   */
  public NAsmField getDummy776002() {
    return dummy776002;
  }

  /**
   * @return the dummy776003
   */
  public NAsmField getDummy776003() {
    return dummy776003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy776(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY776);
    dummy776001.set("JLH");
    dummy776002.set(_LOW(Len.DUMMY776002));
    dummy776003.set(256);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY776001 = 3;
    public static final int DUMMY776002 = 1;
    public static final int DUMMY776003 = 4;

    public static final int DUMMY776 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY776001 = 0;
    public static final int DUMMY776002 = Pos.DUMMY776001 + Len.DUMMY776001;
    public static final int DUMMY776003 = Pos.DUMMY776002 + Len.DUMMY776002;

  }

}
