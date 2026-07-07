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
public class Dummy773 extends NAsmField {

  private NAsmField dummy773001 = new NAsmField(this, "DUMMY773", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY773001, Len.DUMMY773001);
  private NAsmField dummy773002 = new NAsmField(this, "DUMMY773", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY773002, Len.DUMMY773002);
  private NAsmField dummy773003 = new NAsmField(this, "DUMMY773", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY773003, Len.DUMMY773003);

  /**
   * @return the dummy773001
   */
  public NAsmField getDummy773001() {
    return dummy773001;
  }

  /**
   * @return the dummy773002
   */
  public NAsmField getDummy773002() {
    return dummy773002;
  }

  /**
   * @return the dummy773003
   */
  public NAsmField getDummy773003() {
    return dummy773003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy773(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY773);
    dummy773001.set("AF2");
    dummy773002.set(_LOW(Len.DUMMY773002));
    dummy773003.set(260);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY773001 = 3;
    public static final int DUMMY773002 = 1;
    public static final int DUMMY773003 = 4;

    public static final int DUMMY773 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY773001 = 0;
    public static final int DUMMY773002 = Pos.DUMMY773001 + Len.DUMMY773001;
    public static final int DUMMY773003 = Pos.DUMMY773002 + Len.DUMMY773002;

  }

}
