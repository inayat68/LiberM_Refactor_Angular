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
public class Dummy774 extends NAsmField {

  private NAsmField dummy774001 = new NAsmField(this, "DUMMY774", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY774001, Len.DUMMY774001);
  private NAsmField dummy774002 = new NAsmField(this, "DUMMY774", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY774002, Len.DUMMY774002);
  private NAsmField dummy774003 = new NAsmField(this, "DUMMY774", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY774003, Len.DUMMY774003);

  /**
   * @return the dummy774001
   */
  public NAsmField getDummy774001() {
    return dummy774001;
  }

  /**
   * @return the dummy774002
   */
  public NAsmField getDummy774002() {
    return dummy774002;
  }

  /**
   * @return the dummy774003
   */
  public NAsmField getDummy774003() {
    return dummy774003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy774(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY774);
    dummy774001.set("AF3");
    dummy774002.set(_LOW(Len.DUMMY774002));
    dummy774003.set(262);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY774001 = 3;
    public static final int DUMMY774002 = 1;
    public static final int DUMMY774003 = 4;

    public static final int DUMMY774 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY774001 = 0;
    public static final int DUMMY774002 = Pos.DUMMY774001 + Len.DUMMY774001;
    public static final int DUMMY774003 = Pos.DUMMY774002 + Len.DUMMY774002;

  }

}
