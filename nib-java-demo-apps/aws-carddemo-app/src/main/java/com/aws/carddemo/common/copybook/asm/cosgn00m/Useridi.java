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
public class Useridi extends NAsmField {

  // . OUTPUT DATA FIELD
  private NAsmField userido = new NAsmField(this, "USERIDO", NAsmFieldType.ALPHANUMERIC, Pos.USERIDO, Len.USERIDO);

  /**
   * @return the userido
   */
  public NAsmField getUserido() {
    return userido;
  }

  /**
   *
   * @throws NAsmException
   */
  public Useridi(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.USERIDI);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int USERIDO = 8;

    public static final int USERIDI_SLOT = 8;
    public static final int USERIDI = Len.USERIDO;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int USERIDO = 0;

  }

}
