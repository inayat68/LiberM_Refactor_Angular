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
public class Cosgn0ai extends NAsmField {

  private NAsmField cosgn0ao = new NAsmField(this, "COSGN0AO", NAsmFieldType.ALPHANUMERIC, Pos.COSGN0AO, Len.COSGN0AO);

  /**
   * @return the cosgn0ao
   */
  public NAsmField getCosgn0ao() {
    return cosgn0ao;
  }

  /**
   *
   * @throws NAsmException
   */
  public Cosgn0ai(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.COSGN0AI);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int COSGN0AO = 308;

    public static final int COSGN0AI_SLOT = 308;
    public static final int COSGN0AI = Len.COSGN0AO;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int COSGN0AO = 0;

  }

}
