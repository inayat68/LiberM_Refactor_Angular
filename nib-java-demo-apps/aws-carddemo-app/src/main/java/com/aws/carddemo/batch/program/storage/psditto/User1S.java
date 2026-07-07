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
public class User1S extends NAsmField {

  private NAsmField dummy666 = new NAsmField(this, "DUMMY666", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY666, Len.DUMMY666);
  private NAsmField dummy667 = new NAsmField(this, "DUMMY667", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY667, Len.DUMMY667);
  private NAsmField dummy669 = new NAsmField(this, "DUMMY669", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY669, Len.DUMMY669);
  private NAsmField dummy670 = new NAsmField(this, "DUMMY670", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY670, Len.DUMMY670);

  /**
   * @return the dummy666
   */
  public NAsmField getDummy666() {
    return dummy666;
  }

  /**
   * @return the dummy667
   */
  public NAsmField getDummy667() {
    return dummy667;
  }

  /**
   * @return the dummy669
   */
  public NAsmField getDummy669() {
    return dummy669;
  }

  /**
   * @return the dummy670
   */
  public NAsmField getDummy670() {
    return dummy670;
  }

  /**
   *
   * @throws NAsmException
   */
  public User1S(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.USER1S);
    dummy666.set(_PAD("PSDITTO - PARM ERROR.  VERIFY FORMAT AS FOLLOWS.", Len.DUMMY666));
    dummy667.set(_PAD("NUMRECS=XXXXX,PS HEADER=X", Len.DUMMY667));
    dummy669.set(_PAD("WHERE NUMRECS FLD IS NUMERIC OR ALL, AND PS HEADER FLD IS Y OR N.", Len.DUMMY669));
    dummy670.set(_PAD("ABEND 3001.    MODE=", Len.DUMMY670));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY666 = 75;
    public static final int DUMMY667 = 75;
    public static final int DUMMY669 = 75;
    public static final int DUMMY670 = 30;

    public static final int USER1S = Len.DUMMY666
                                + Len.DUMMY667
                                + Len.DUMMY669
                                + Len.DUMMY670;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY666 = 0;
    public static final int DUMMY667 = Pos.DUMMY666 + Len.DUMMY666;
    public static final int DUMMY669 = Pos.DUMMY667 + Len.DUMMY667;
    public static final int DUMMY670 = Pos.DUMMY669 + Len.DUMMY669;

  }

}
