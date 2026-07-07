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
public class User5S extends NAsmField {

  private NAsmField dummy694 = new NAsmField(this, "DUMMY694", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY694, Len.DUMMY694);
  private NAsmField dummy696 = new NAsmField(this, "DUMMY696", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY696, Len.DUMMY696);
  private NAsmField dummy698 = new NAsmField(this, "DUMMY698", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY698, Len.DUMMY698);
  private NAsmField dummy699 = new NAsmField(this, "DUMMY699", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY699, Len.DUMMY699);

  /**
   * @return the dummy694
   */
  public NAsmField getDummy694() {
    return dummy694;
  }

  /**
   * @return the dummy696
   */
  public NAsmField getDummy696() {
    return dummy696;
  }

  /**
   * @return the dummy698
   */
  public NAsmField getDummy698() {
    return dummy698;
  }

  /**
   * @return the dummy699
   */
  public NAsmField getDummy699() {
    return dummy699;
  }

  /**
   *
   * @throws NAsmException
   */
  public User5S(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.USER5S);
    dummy694.set(_PAD("PSDITTO - SIZE ERROR.", Len.DUMMY694));
    dummy696.set(_PAD("500 IS MAXIMUM NUMBER OF FIELDS ALLOWED FOR SPECIAL LCR DITTO.", Len.DUMMY696));
    dummy698.set(_PAD("CONTACT SYSTEM SUPPORT IF YOU WISH TO DITTO MORE THAN THE MAXIMUM.", Len.DUMMY698));
    dummy699.set(_PAD("ABEND 3005.    MODE=", Len.DUMMY699));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY694 = 75;
    public static final int DUMMY696 = 75;
    public static final int DUMMY698 = 75;
    public static final int DUMMY699 = 30;

    public static final int USER5S = Len.DUMMY694
                                + Len.DUMMY696
                                + Len.DUMMY698
                                + Len.DUMMY699;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY694 = 0;
    public static final int DUMMY696 = Pos.DUMMY694 + Len.DUMMY694;
    public static final int DUMMY698 = Pos.DUMMY696 + Len.DUMMY696;
    public static final int DUMMY699 = Pos.DUMMY698 + Len.DUMMY698;

  }

}
