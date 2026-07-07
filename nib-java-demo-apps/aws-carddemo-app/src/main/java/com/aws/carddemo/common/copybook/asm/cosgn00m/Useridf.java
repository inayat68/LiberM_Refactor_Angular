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
public class Useridf extends NAsmField {

  // . DATA FIELD ATTRIBUTE
  private NAsmField userida = new NAsmField(this, "USERIDA", NAsmFieldType.ALPHANUMERIC, Pos.USERIDA, Len.USERIDA);
  // . COLOUR ATTRIBUTE
  private NAsmField useridc = new NAsmField(this, "USERIDC", NAsmFieldType.ALPHANUMERIC, Pos.USERIDC, Len.USERIDC);
  // . PS ATTRIBUTE
  private NAsmField useridp = new NAsmField(this, "USERIDP", NAsmFieldType.ALPHANUMERIC, Pos.USERIDP, Len.USERIDP);
  // . HIGHLIGHTING ATTRIBUTE
  private NAsmField useridh = new NAsmField(this, "USERIDH", NAsmFieldType.ALPHANUMERIC, Pos.USERIDH, Len.USERIDH);
  // . VALIDATION ATTRIBUTE
  private NAsmField useridv = new NAsmField(this, "USERIDV", NAsmFieldType.ALPHANUMERIC, Pos.USERIDV, Len.USERIDV);

  /**
   * @return the userida
   */
  public NAsmField getUserida() {
    return userida;
  }

  /**
   * @return the useridc
   */
  public NAsmField getUseridc() {
    return useridc;
  }

  /**
   * @return the useridp
   */
  public NAsmField getUseridp() {
    return useridp;
  }

  /**
   * @return the useridh
   */
  public NAsmField getUseridh() {
    return useridh;
  }

  /**
   * @return the useridv
   */
  public NAsmField getUseridv() {
    return useridv;
  }

  /**
   *
   * @throws NAsmException
   */
  public Useridf(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.USERIDF);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int USERIDA = 1;
    public static final int USERIDC = 1;
    public static final int USERIDP = 1;
    public static final int USERIDH = 1;
    public static final int USERIDV = 1;

    public static final int USERIDF_SLOT = 1;
    public static final int USERIDF = Len.USERIDA
                                + Len.USERIDC
                                + Len.USERIDP
                                + Len.USERIDH
                                + Len.USERIDV;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int USERIDA = 0;
    public static final int USERIDC = Pos.USERIDA + Len.USERIDA;
    public static final int USERIDP = Pos.USERIDC + Len.USERIDC;
    public static final int USERIDH = Pos.USERIDP + Len.USERIDP;
    public static final int USERIDV = Pos.USERIDH + Len.USERIDH;

  }

}
