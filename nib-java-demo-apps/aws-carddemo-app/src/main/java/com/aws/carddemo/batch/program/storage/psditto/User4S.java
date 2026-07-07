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
public class User4S extends NAsmField {

  private NAsmField dummy688 = new NAsmField(this, "DUMMY688", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY688, Len.DUMMY688);
  private NAsmField dummy689 = new NAsmField(this, "DUMMY689", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY689, Len.DUMMY689);
  private NAsmField dummy690 = new NAsmField(this, "DUMMY690", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY690, Len.DUMMY690);
  private NAsmField dummy691 = new NAsmField(this, "DUMMY691", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY691, Len.DUMMY691);

  /**
   * @return the dummy688
   */
  public NAsmField getDummy688() {
    return dummy688;
  }

  /**
   * @return the dummy689
   */
  public NAsmField getDummy689() {
    return dummy689;
  }

  /**
   * @return the dummy690
   */
  public NAsmField getDummy690() {
    return dummy690;
  }

  /**
   * @return the dummy691
   */
  public NAsmField getDummy691() {
    return dummy691;
  }

  /**
   *
   * @throws NAsmException
   */
  public User4S(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.USER4S);
    dummy688.set(_PAD("PSDITTO - USER ERROR.", Len.DUMMY688));
    dummy689.set(_PAD("USER MUST SPECIFY SELECT OPTION FOR LCR SEGMENT,", Len.DUMMY689));
    dummy690.set(_PAD("IF LCR AUDIT DITTO IS TO BE DONE FOR OSCR RECORDS.", Len.DUMMY690));
    dummy691.set(_PAD("ABEND 3004.    MODE=", Len.DUMMY691));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY688 = 75;
    public static final int DUMMY689 = 75;
    public static final int DUMMY690 = 75;
    public static final int DUMMY691 = 30;

    public static final int USER4S = Len.DUMMY688
                                + Len.DUMMY689
                                + Len.DUMMY690
                                + Len.DUMMY691;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY688 = 0;
    public static final int DUMMY689 = Pos.DUMMY688 + Len.DUMMY688;
    public static final int DUMMY690 = Pos.DUMMY689 + Len.DUMMY689;
    public static final int DUMMY691 = Pos.DUMMY690 + Len.DUMMY690;

  }

}
