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
public class Curdatei extends NAsmField {

  // . OUTPUT DATA FIELD
  private NAsmField curdateo = new NAsmField(this, "CURDATEO", NAsmFieldType.ALPHANUMERIC, Pos.CURDATEO, Len.CURDATEO);

  /**
   * @return the curdateo
   */
  public NAsmField getCurdateo() {
    return curdateo;
  }

  /**
   *
   * @throws NAsmException
   */
  public Curdatei(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.CURDATEI);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int CURDATEO = 8;

    public static final int CURDATEI_SLOT = 8;
    public static final int CURDATEI = Len.CURDATEO;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int CURDATEO = 0;

  }

}
