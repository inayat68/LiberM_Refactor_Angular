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
public class Dummy770 extends NAsmField {

  private NAsmField dummy770001 = new NAsmField(this, "DUMMY770", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY770001, Len.DUMMY770001);
  private NAsmField dummy770002 = new NAsmField(this, "DUMMY770", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY770002, Len.DUMMY770002);
  private NAsmField dummy770003 = new NAsmField(this, "DUMMY770", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY770003, Len.DUMMY770003);

  /**
   * @return the dummy770001
   */
  public NAsmField getDummy770001() {
    return dummy770001;
  }

  /**
   * @return the dummy770002
   */
  public NAsmField getDummy770002() {
    return dummy770002;
  }

  /**
   * @return the dummy770003
   */
  public NAsmField getDummy770003() {
    return dummy770003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy770(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY770);
    dummy770001.set("AFS");
    dummy770002.set(_LOW(Len.DUMMY770002));
    dummy770003.set(278);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY770001 = 3;
    public static final int DUMMY770002 = 1;
    public static final int DUMMY770003 = 4;

    public static final int DUMMY770 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY770001 = 0;
    public static final int DUMMY770002 = Pos.DUMMY770001 + Len.DUMMY770001;
    public static final int DUMMY770003 = Pos.DUMMY770002 + Len.DUMMY770002;

  }

}
