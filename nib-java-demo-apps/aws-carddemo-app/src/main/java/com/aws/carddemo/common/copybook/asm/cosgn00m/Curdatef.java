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
public class Curdatef extends NAsmField {

  // . DATA FIELD ATTRIBUTE
  private NAsmField curdatea = new NAsmField(this, "CURDATEA", NAsmFieldType.ALPHANUMERIC, Pos.CURDATEA, Len.CURDATEA);
  // . COLOUR ATTRIBUTE
  private NAsmField curdatec = new NAsmField(this, "CURDATEC", NAsmFieldType.ALPHANUMERIC, Pos.CURDATEC, Len.CURDATEC);
  // . PS ATTRIBUTE
  private NAsmField curdatep = new NAsmField(this, "CURDATEP", NAsmFieldType.ALPHANUMERIC, Pos.CURDATEP, Len.CURDATEP);
  // . HIGHLIGHTING ATTRIBUTE
  private NAsmField curdateh = new NAsmField(this, "CURDATEH", NAsmFieldType.ALPHANUMERIC, Pos.CURDATEH, Len.CURDATEH);
  // . VALIDATION ATTRIBUTE
  private NAsmField curdatev = new NAsmField(this, "CURDATEV", NAsmFieldType.ALPHANUMERIC, Pos.CURDATEV, Len.CURDATEV);

  /**
   * @return the curdatea
   */
  public NAsmField getCurdatea() {
    return curdatea;
  }

  /**
   * @return the curdatec
   */
  public NAsmField getCurdatec() {
    return curdatec;
  }

  /**
   * @return the curdatep
   */
  public NAsmField getCurdatep() {
    return curdatep;
  }

  /**
   * @return the curdateh
   */
  public NAsmField getCurdateh() {
    return curdateh;
  }

  /**
   * @return the curdatev
   */
  public NAsmField getCurdatev() {
    return curdatev;
  }

  /**
   *
   * @throws NAsmException
   */
  public Curdatef(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.CURDATEF);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int CURDATEA = 1;
    public static final int CURDATEC = 1;
    public static final int CURDATEP = 1;
    public static final int CURDATEH = 1;
    public static final int CURDATEV = 1;

    public static final int CURDATEF_SLOT = 1;
    public static final int CURDATEF = Len.CURDATEA
                                + Len.CURDATEC
                                + Len.CURDATEP
                                + Len.CURDATEH
                                + Len.CURDATEV;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int CURDATEA = 0;
    public static final int CURDATEC = Pos.CURDATEA + Len.CURDATEA;
    public static final int CURDATEP = Pos.CURDATEC + Len.CURDATEC;
    public static final int CURDATEH = Pos.CURDATEP + Len.CURDATEP;
    public static final int CURDATEV = Pos.CURDATEH + Len.CURDATEH;

  }

}
