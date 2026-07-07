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
public class Title02i extends NAsmField {

  // . OUTPUT DATA FIELD
  private NAsmField title02o = new NAsmField(this, "TITLE02O", NAsmFieldType.ALPHANUMERIC, Pos.TITLE02O, Len.TITLE02O);

  /**
   * @return the title02o
   */
  public NAsmField getTitle02o() {
    return title02o;
  }

  /**
   *
   * @throws NAsmException
   */
  public Title02i(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.TITLE02I);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int TITLE02O = 40;

    public static final int TITLE02I_SLOT = 40;
    public static final int TITLE02I = Len.TITLE02O;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int TITLE02O = 0;

  }

}
