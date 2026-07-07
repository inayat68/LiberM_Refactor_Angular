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
public class Curtimef extends NAsmField {

  // . DATA FIELD ATTRIBUTE
  private NAsmField curtimea = new NAsmField(this, "CURTIMEA", NAsmFieldType.ALPHANUMERIC, Pos.CURTIMEA, Len.CURTIMEA);
  // . COLOUR ATTRIBUTE
  private NAsmField curtimec = new NAsmField(this, "CURTIMEC", NAsmFieldType.ALPHANUMERIC, Pos.CURTIMEC, Len.CURTIMEC);
  // . PS ATTRIBUTE
  private NAsmField curtimep = new NAsmField(this, "CURTIMEP", NAsmFieldType.ALPHANUMERIC, Pos.CURTIMEP, Len.CURTIMEP);
  // . HIGHLIGHTING ATTRIBUTE
  private NAsmField curtimeh = new NAsmField(this, "CURTIMEH", NAsmFieldType.ALPHANUMERIC, Pos.CURTIMEH, Len.CURTIMEH);
  // . VALIDATION ATTRIBUTE
  private NAsmField curtimev = new NAsmField(this, "CURTIMEV", NAsmFieldType.ALPHANUMERIC, Pos.CURTIMEV, Len.CURTIMEV);

  /**
   * @return the curtimea
   */
  public NAsmField getCurtimea() {
    return curtimea;
  }

  /**
   * @return the curtimec
   */
  public NAsmField getCurtimec() {
    return curtimec;
  }

  /**
   * @return the curtimep
   */
  public NAsmField getCurtimep() {
    return curtimep;
  }

  /**
   * @return the curtimeh
   */
  public NAsmField getCurtimeh() {
    return curtimeh;
  }

  /**
   * @return the curtimev
   */
  public NAsmField getCurtimev() {
    return curtimev;
  }

  /**
   *
   * @throws NAsmException
   */
  public Curtimef(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.CURTIMEF);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int CURTIMEA = 1;
    public static final int CURTIMEC = 1;
    public static final int CURTIMEP = 1;
    public static final int CURTIMEH = 1;
    public static final int CURTIMEV = 1;

    public static final int CURTIMEF_SLOT = 1;
    public static final int CURTIMEF = Len.CURTIMEA
                                + Len.CURTIMEC
                                + Len.CURTIMEP
                                + Len.CURTIMEH
                                + Len.CURTIMEV;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int CURTIMEA = 0;
    public static final int CURTIMEC = Pos.CURTIMEA + Len.CURTIMEA;
    public static final int CURTIMEP = Pos.CURTIMEC + Len.CURTIMEC;
    public static final int CURTIMEH = Pos.CURTIMEP + Len.CURTIMEP;
    public static final int CURTIMEV = Pos.CURTIMEH + Len.CURTIMEH;

  }

}
