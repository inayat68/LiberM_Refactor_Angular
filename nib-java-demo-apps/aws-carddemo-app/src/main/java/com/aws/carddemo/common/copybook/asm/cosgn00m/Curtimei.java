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
public class Curtimei extends NAsmField {

  // . OUTPUT DATA FIELD
  private NAsmField curtimeo = new NAsmField(this, "CURTIMEO", NAsmFieldType.ALPHANUMERIC, Pos.CURTIMEO, Len.CURTIMEO);

  /**
   * @return the curtimeo
   */
  public NAsmField getCurtimeo() {
    return curtimeo;
  }

  /**
   *
   * @throws NAsmException
   */
  public Curtimei(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.CURTIMEI);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int CURTIMEO = 9;

    public static final int CURTIMEI_SLOT = 9;
    public static final int CURTIMEI = Len.CURTIMEO;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int CURTIMEO = 0;

  }

}
