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
public class Trnnamei extends NAsmField {

  // . OUTPUT DATA FIELD
  private NAsmField trnnameo = new NAsmField(this, "TRNNAMEO", NAsmFieldType.ALPHANUMERIC, Pos.TRNNAMEO, Len.TRNNAMEO);

  /**
   * @return the trnnameo
   */
  public NAsmField getTrnnameo() {
    return trnnameo;
  }

  /**
   *
   * @throws NAsmException
   */
  public Trnnamei(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.TRNNAMEI);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int TRNNAMEO = 4;

    public static final int TRNNAMEI_SLOT = 4;
    public static final int TRNNAMEI = Len.TRNNAMEO;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int TRNNAMEO = 0;

  }

}
