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
public class Applidf extends NAsmField {

  // . DATA FIELD ATTRIBUTE
  private NAsmField applida = new NAsmField(this, "APPLIDA", NAsmFieldType.ALPHANUMERIC, Pos.APPLIDA, Len.APPLIDA);
  // . COLOUR ATTRIBUTE
  private NAsmField applidc = new NAsmField(this, "APPLIDC", NAsmFieldType.ALPHANUMERIC, Pos.APPLIDC, Len.APPLIDC);
  // . PS ATTRIBUTE
  private NAsmField applidp = new NAsmField(this, "APPLIDP", NAsmFieldType.ALPHANUMERIC, Pos.APPLIDP, Len.APPLIDP);
  // . HIGHLIGHTING ATTRIBUTE
  private NAsmField applidh = new NAsmField(this, "APPLIDH", NAsmFieldType.ALPHANUMERIC, Pos.APPLIDH, Len.APPLIDH);
  // . VALIDATION ATTRIBUTE
  private NAsmField applidv = new NAsmField(this, "APPLIDV", NAsmFieldType.ALPHANUMERIC, Pos.APPLIDV, Len.APPLIDV);

  /**
   * @return the applida
   */
  public NAsmField getApplida() {
    return applida;
  }

  /**
   * @return the applidc
   */
  public NAsmField getApplidc() {
    return applidc;
  }

  /**
   * @return the applidp
   */
  public NAsmField getApplidp() {
    return applidp;
  }

  /**
   * @return the applidh
   */
  public NAsmField getApplidh() {
    return applidh;
  }

  /**
   * @return the applidv
   */
  public NAsmField getApplidv() {
    return applidv;
  }

  /**
   *
   * @throws NAsmException
   */
  public Applidf(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.APPLIDF);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int APPLIDA = 1;
    public static final int APPLIDC = 1;
    public static final int APPLIDP = 1;
    public static final int APPLIDH = 1;
    public static final int APPLIDV = 1;

    public static final int APPLIDF_SLOT = 1;
    public static final int APPLIDF = Len.APPLIDA
                                + Len.APPLIDC
                                + Len.APPLIDP
                                + Len.APPLIDH
                                + Len.APPLIDV;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int APPLIDA = 0;
    public static final int APPLIDC = Pos.APPLIDA + Len.APPLIDA;
    public static final int APPLIDP = Pos.APPLIDC + Len.APPLIDC;
    public static final int APPLIDH = Pos.APPLIDP + Len.APPLIDP;
    public static final int APPLIDV = Pos.APPLIDH + Len.APPLIDH;

  }

}
