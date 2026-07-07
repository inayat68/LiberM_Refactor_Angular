package com.aws.carddemo.common.copybook.asm.cosgn00m;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.storage.NAsmField;

/**
 * 
 * <b>Divergeek - ASM370 to JAVA.</b> <br/>
 * <br/>
 * 
 * <br/>
 * 
 * @version 2.2.8
 * 
 * @author divergeek
 */
public class Trnnamef extends NAsmField {

  // . DATA FIELD ATTRIBUTE
  private NAsmField trnnamea = new NAsmField(this, "TRNNAMEA", NAsmFieldType.ALPHANUMERIC, Pos.TRNNAMEA, Len.TRNNAMEA);
  // . COLOUR ATTRIBUTE
  private NAsmField trnnamec = new NAsmField(this, "TRNNAMEC", NAsmFieldType.ALPHANUMERIC, Pos.TRNNAMEC, Len.TRNNAMEC);
  // . PS ATTRIBUTE
  private NAsmField trnnamep = new NAsmField(this, "TRNNAMEP", NAsmFieldType.ALPHANUMERIC, Pos.TRNNAMEP, Len.TRNNAMEP);
  // . HIGHLIGHTING ATTRIBUTE
  private NAsmField trnnameh = new NAsmField(this, "TRNNAMEH", NAsmFieldType.ALPHANUMERIC, Pos.TRNNAMEH, Len.TRNNAMEH);
  // . VALIDATION ATTRIBUTE
  private NAsmField trnnamev = new NAsmField(this, "TRNNAMEV", NAsmFieldType.ALPHANUMERIC, Pos.TRNNAMEV, Len.TRNNAMEV);

  /**
   * @return the trnnamea
   */
  public NAsmField getTrnnamea() {
    return trnnamea;
  }

  /**
   * @return the trnnamec
   */
  public NAsmField getTrnnamec() {
    return trnnamec;
  }

  /**
   * @return the trnnamep
   */
  public NAsmField getTrnnamep() {
    return trnnamep;
  }

  /**
   * @return the trnnameh
   */
  public NAsmField getTrnnameh() {
    return trnnameh;
  }

  /**
   * @return the trnnamev
   */
  public NAsmField getTrnnamev() {
    return trnnamev;
  }

  /**
   *
   * @throws NAsmException
   */
  public Trnnamef(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.TRNNAMEF);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int TRNNAMEA = 1;
    public static final int TRNNAMEC = 1;
    public static final int TRNNAMEP = 1;
    public static final int TRNNAMEH = 1;
    public static final int TRNNAMEV = 1;

    public static final int TRNNAMEF_SLOT = 1;
    public static final int TRNNAMEF = Len.TRNNAMEA
                                + Len.TRNNAMEC
                                + Len.TRNNAMEP
                                + Len.TRNNAMEH
                                + Len.TRNNAMEV;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int TRNNAMEA = 0;
    public static final int TRNNAMEC = Pos.TRNNAMEA + Len.TRNNAMEA;
    public static final int TRNNAMEP = Pos.TRNNAMEC + Len.TRNNAMEC;
    public static final int TRNNAMEH = Pos.TRNNAMEP + Len.TRNNAMEP;
    public static final int TRNNAMEV = Pos.TRNNAMEH + Len.TRNNAMEH;

  }

}
