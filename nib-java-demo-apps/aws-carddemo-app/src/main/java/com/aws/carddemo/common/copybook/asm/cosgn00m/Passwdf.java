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
public class Passwdf extends NAsmField {

  // . DATA FIELD ATTRIBUTE
  private NAsmField passwda = new NAsmField(this, "PASSWDA", NAsmFieldType.ALPHANUMERIC, Pos.PASSWDA, Len.PASSWDA);
  // . COLOUR ATTRIBUTE
  private NAsmField passwdc = new NAsmField(this, "PASSWDC", NAsmFieldType.ALPHANUMERIC, Pos.PASSWDC, Len.PASSWDC);
  // . PS ATTRIBUTE
  private NAsmField passwdp = new NAsmField(this, "PASSWDP", NAsmFieldType.ALPHANUMERIC, Pos.PASSWDP, Len.PASSWDP);
  // . HIGHLIGHTING ATTRIBUTE
  private NAsmField passwdh = new NAsmField(this, "PASSWDH", NAsmFieldType.ALPHANUMERIC, Pos.PASSWDH, Len.PASSWDH);
  // . VALIDATION ATTRIBUTE
  private NAsmField passwdv = new NAsmField(this, "PASSWDV", NAsmFieldType.ALPHANUMERIC, Pos.PASSWDV, Len.PASSWDV);

  /**
   * @return the passwda
   */
  public NAsmField getPasswda() {
    return passwda;
  }

  /**
   * @return the passwdc
   */
  public NAsmField getPasswdc() {
    return passwdc;
  }

  /**
   * @return the passwdp
   */
  public NAsmField getPasswdp() {
    return passwdp;
  }

  /**
   * @return the passwdh
   */
  public NAsmField getPasswdh() {
    return passwdh;
  }

  /**
   * @return the passwdv
   */
  public NAsmField getPasswdv() {
    return passwdv;
  }

  /**
   *
   * @throws NAsmException
   */
  public Passwdf(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.PASSWDF);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int PASSWDA = 1;
    public static final int PASSWDC = 1;
    public static final int PASSWDP = 1;
    public static final int PASSWDH = 1;
    public static final int PASSWDV = 1;

    public static final int PASSWDF_SLOT = 1;
    public static final int PASSWDF = Len.PASSWDA
                                + Len.PASSWDC
                                + Len.PASSWDP
                                + Len.PASSWDH
                                + Len.PASSWDV;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int PASSWDA = 0;
    public static final int PASSWDC = Pos.PASSWDA + Len.PASSWDA;
    public static final int PASSWDP = Pos.PASSWDC + Len.PASSWDC;
    public static final int PASSWDH = Pos.PASSWDP + Len.PASSWDP;
    public static final int PASSWDV = Pos.PASSWDH + Len.PASSWDH;

  }

}
