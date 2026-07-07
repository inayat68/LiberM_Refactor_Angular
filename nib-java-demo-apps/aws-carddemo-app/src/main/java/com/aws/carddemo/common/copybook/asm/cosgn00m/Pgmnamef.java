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
public class Pgmnamef extends NAsmField {

  // . DATA FIELD ATTRIBUTE
  private NAsmField pgmnamea = new NAsmField(this, "PGMNAMEA", NAsmFieldType.ALPHANUMERIC, Pos.PGMNAMEA, Len.PGMNAMEA);
  // . COLOUR ATTRIBUTE
  private NAsmField pgmnamec = new NAsmField(this, "PGMNAMEC", NAsmFieldType.ALPHANUMERIC, Pos.PGMNAMEC, Len.PGMNAMEC);
  // . PS ATTRIBUTE
  private NAsmField pgmnamep = new NAsmField(this, "PGMNAMEP", NAsmFieldType.ALPHANUMERIC, Pos.PGMNAMEP, Len.PGMNAMEP);
  // . HIGHLIGHTING ATTRIBUTE
  private NAsmField pgmnameh = new NAsmField(this, "PGMNAMEH", NAsmFieldType.ALPHANUMERIC, Pos.PGMNAMEH, Len.PGMNAMEH);
  // . VALIDATION ATTRIBUTE
  private NAsmField pgmnamev = new NAsmField(this, "PGMNAMEV", NAsmFieldType.ALPHANUMERIC, Pos.PGMNAMEV, Len.PGMNAMEV);

  /**
   * @return the pgmnamea
   */
  public NAsmField getPgmnamea() {
    return pgmnamea;
  }

  /**
   * @return the pgmnamec
   */
  public NAsmField getPgmnamec() {
    return pgmnamec;
  }

  /**
   * @return the pgmnamep
   */
  public NAsmField getPgmnamep() {
    return pgmnamep;
  }

  /**
   * @return the pgmnameh
   */
  public NAsmField getPgmnameh() {
    return pgmnameh;
  }

  /**
   * @return the pgmnamev
   */
  public NAsmField getPgmnamev() {
    return pgmnamev;
  }

  /**
   *
   * @throws NAsmException
   */
  public Pgmnamef(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.PGMNAMEF);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int PGMNAMEA = 1;
    public static final int PGMNAMEC = 1;
    public static final int PGMNAMEP = 1;
    public static final int PGMNAMEH = 1;
    public static final int PGMNAMEV = 1;

    public static final int PGMNAMEF_SLOT = 1;
    public static final int PGMNAMEF = Len.PGMNAMEA
                                + Len.PGMNAMEC
                                + Len.PGMNAMEP
                                + Len.PGMNAMEH
                                + Len.PGMNAMEV;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int PGMNAMEA = 0;
    public static final int PGMNAMEC = Pos.PGMNAMEA + Len.PGMNAMEA;
    public static final int PGMNAMEP = Pos.PGMNAMEC + Len.PGMNAMEC;
    public static final int PGMNAMEH = Pos.PGMNAMEP + Len.PGMNAMEP;
    public static final int PGMNAMEV = Pos.PGMNAMEH + Len.PGMNAMEH;

  }

}
