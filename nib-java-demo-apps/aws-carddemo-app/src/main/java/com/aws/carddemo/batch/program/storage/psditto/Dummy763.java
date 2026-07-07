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
public class Dummy763 extends NAsmField {

  private NAsmField dummy763001 = new NAsmField(this, "DUMMY763", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY763001, Len.DUMMY763001);
  private NAsmField dummy763002 = new NAsmField(this, "DUMMY763", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY763002, Len.DUMMY763002);
  private NAsmField dummy763003 = new NAsmField(this, "DUMMY763", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY763003, Len.DUMMY763003);

  /**
   * @return the dummy763001
   */
  public NAsmField getDummy763001() {
    return dummy763001;
  }

  /**
   * @return the dummy763002
   */
  public NAsmField getDummy763002() {
    return dummy763002;
  }

  /**
   * @return the dummy763003
   */
  public NAsmField getDummy763003() {
    return dummy763003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy763(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY763);
    dummy763001.set("LCR");
    dummy763002.set(_LOW(Len.DUMMY763002));
    dummy763003.set(186);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY763001 = 3;
    public static final int DUMMY763002 = 1;
    public static final int DUMMY763003 = 4;

    public static final int DUMMY763 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY763001 = 0;
    public static final int DUMMY763002 = Pos.DUMMY763001 + Len.DUMMY763001;
    public static final int DUMMY763003 = Pos.DUMMY763002 + Len.DUMMY763002;

  }

}
