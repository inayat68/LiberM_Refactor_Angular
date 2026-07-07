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
public class Dummy750 extends NAsmField {

  private NAsmField dummy750001 = new NAsmField(this, "DUMMY750", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY750001, Len.DUMMY750001);
  private NAsmField dummy750002 = new NAsmField(this, "DUMMY750", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY750002, Len.DUMMY750002);

  /**
   * @return the dummy750001
   */
  public NAsmField getDummy750001() {
    return dummy750001;
  }

  /**
   * @return the dummy750002
   */
  public NAsmField getDummy750002() {
    return dummy750002;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy750(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY750);
    dummy750001.set("DBC");
    dummy750002.set(_LOW(Len.DUMMY750002));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY750001 = 3;
    public static final int DUMMY750002 = 1;

    public static final int DUMMY750 = 4;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY750001 = 0;
    public static final int DUMMY750002 = Pos.DUMMY750001 + Len.DUMMY750001;

  }

}
