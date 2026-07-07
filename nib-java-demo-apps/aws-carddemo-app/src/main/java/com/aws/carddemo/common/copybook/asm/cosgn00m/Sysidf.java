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
public class Sysidf extends NAsmField {

  // . DATA FIELD ATTRIBUTE
  private NAsmField sysida = new NAsmField(this, "SYSIDA", NAsmFieldType.ALPHANUMERIC, Pos.SYSIDA, Len.SYSIDA);
  // . COLOUR ATTRIBUTE
  private NAsmField sysidc = new NAsmField(this, "SYSIDC", NAsmFieldType.ALPHANUMERIC, Pos.SYSIDC, Len.SYSIDC);
  // . PS ATTRIBUTE
  private NAsmField sysidp = new NAsmField(this, "SYSIDP", NAsmFieldType.ALPHANUMERIC, Pos.SYSIDP, Len.SYSIDP);
  // . HIGHLIGHTING ATTRIBUTE
  private NAsmField sysidh = new NAsmField(this, "SYSIDH", NAsmFieldType.ALPHANUMERIC, Pos.SYSIDH, Len.SYSIDH);
  // . VALIDATION ATTRIBUTE
  private NAsmField sysidv = new NAsmField(this, "SYSIDV", NAsmFieldType.ALPHANUMERIC, Pos.SYSIDV, Len.SYSIDV);

  /**
   * @return the sysida
   */
  public NAsmField getSysida() {
    return sysida;
  }

  /**
   * @return the sysidc
   */
  public NAsmField getSysidc() {
    return sysidc;
  }

  /**
   * @return the sysidp
   */
  public NAsmField getSysidp() {
    return sysidp;
  }

  /**
   * @return the sysidh
   */
  public NAsmField getSysidh() {
    return sysidh;
  }

  /**
   * @return the sysidv
   */
  public NAsmField getSysidv() {
    return sysidv;
  }

  /**
   *
   * @throws NAsmException
   */
  public Sysidf(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.SYSIDF);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int SYSIDA = 1;
    public static final int SYSIDC = 1;
    public static final int SYSIDP = 1;
    public static final int SYSIDH = 1;
    public static final int SYSIDV = 1;

    public static final int SYSIDF_SLOT = 1;
    public static final int SYSIDF = Len.SYSIDA
                                + Len.SYSIDC
                                + Len.SYSIDP
                                + Len.SYSIDH
                                + Len.SYSIDV;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int SYSIDA = 0;
    public static final int SYSIDC = Pos.SYSIDA + Len.SYSIDA;
    public static final int SYSIDP = Pos.SYSIDC + Len.SYSIDC;
    public static final int SYSIDH = Pos.SYSIDP + Len.SYSIDP;
    public static final int SYSIDV = Pos.SYSIDH + Len.SYSIDH;

  }

}
