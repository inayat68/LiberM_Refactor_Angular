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
public class Title01i extends NAsmField {

  // . OUTPUT DATA FIELD
  private NAsmField title01o = new NAsmField(this, "TITLE01O", NAsmFieldType.ALPHANUMERIC, Pos.TITLE01O, Len.TITLE01O);

  /**
   * @return the title01o
   */
  public NAsmField getTitle01o() {
    return title01o;
  }

  /**
   *
   * @throws NAsmException
   */
  public Title01i(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.TITLE01I);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int TITLE01O = 40;

    public static final int TITLE01I_SLOT = 40;
    public static final int TITLE01I = Len.TITLE01O;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int TITLE01O = 0;

  }

}
