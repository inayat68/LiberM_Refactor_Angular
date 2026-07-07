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
public class Errmsgf extends NAsmField {

  // . DATA FIELD ATTRIBUTE
  private NAsmField errmsga = new NAsmField(this, "ERRMSGA", NAsmFieldType.ALPHANUMERIC, Pos.ERRMSGA, Len.ERRMSGA);
  // . COLOUR ATTRIBUTE
  private NAsmField errmsgc = new NAsmField(this, "ERRMSGC", NAsmFieldType.ALPHANUMERIC, Pos.ERRMSGC, Len.ERRMSGC);
  // . PS ATTRIBUTE
  private NAsmField errmsgp = new NAsmField(this, "ERRMSGP", NAsmFieldType.ALPHANUMERIC, Pos.ERRMSGP, Len.ERRMSGP);
  // . HIGHLIGHTING ATTRIBUTE
  private NAsmField errmsgh = new NAsmField(this, "ERRMSGH", NAsmFieldType.ALPHANUMERIC, Pos.ERRMSGH, Len.ERRMSGH);
  // . VALIDATION ATTRIBUTE
  private NAsmField errmsgv = new NAsmField(this, "ERRMSGV", NAsmFieldType.ALPHANUMERIC, Pos.ERRMSGV, Len.ERRMSGV);

  /**
   * @return the errmsga
   */
  public NAsmField getErrmsga() {
    return errmsga;
  }

  /**
   * @return the errmsgc
   */
  public NAsmField getErrmsgc() {
    return errmsgc;
  }

  /**
   * @return the errmsgp
   */
  public NAsmField getErrmsgp() {
    return errmsgp;
  }

  /**
   * @return the errmsgh
   */
  public NAsmField getErrmsgh() {
    return errmsgh;
  }

  /**
   * @return the errmsgv
   */
  public NAsmField getErrmsgv() {
    return errmsgv;
  }

  /**
   *
   * @throws NAsmException
   */
  public Errmsgf(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.ERRMSGF);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int ERRMSGA = 1;
    public static final int ERRMSGC = 1;
    public static final int ERRMSGP = 1;
    public static final int ERRMSGH = 1;
    public static final int ERRMSGV = 1;

    public static final int ERRMSGF_SLOT = 1;
    public static final int ERRMSGF = Len.ERRMSGA
                                + Len.ERRMSGC
                                + Len.ERRMSGP
                                + Len.ERRMSGH
                                + Len.ERRMSGV;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int ERRMSGA = 0;
    public static final int ERRMSGC = Pos.ERRMSGA + Len.ERRMSGA;
    public static final int ERRMSGP = Pos.ERRMSGC + Len.ERRMSGC;
    public static final int ERRMSGH = Pos.ERRMSGP + Len.ERRMSGP;
    public static final int ERRMSGV = Pos.ERRMSGH + Len.ERRMSGH;

  }

}
