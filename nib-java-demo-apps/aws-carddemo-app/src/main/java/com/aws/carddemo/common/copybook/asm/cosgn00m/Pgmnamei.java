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
public class Pgmnamei extends NAsmField {

  // . OUTPUT DATA FIELD
  private NAsmField pgmnameo = new NAsmField(this, "PGMNAMEO", NAsmFieldType.ALPHANUMERIC, Pos.PGMNAMEO, Len.PGMNAMEO);

  /**
   * @return the pgmnameo
   */
  public NAsmField getPgmnameo() {
    return pgmnameo;
  }

  /**
   *
   * @throws NAsmException
   */
  public Pgmnamei(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.PGMNAMEI);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int PGMNAMEO = 8;

    public static final int PGMNAMEI_SLOT = 8;
    public static final int PGMNAMEI = Len.PGMNAMEO;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int PGMNAMEO = 0;

  }

}
