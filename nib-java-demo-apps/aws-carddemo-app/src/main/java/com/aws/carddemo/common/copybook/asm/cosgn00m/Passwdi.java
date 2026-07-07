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
public class Passwdi extends NAsmField {

  // . OUTPUT DATA FIELD
  private NAsmField passwdo = new NAsmField(this, "PASSWDO", NAsmFieldType.ALPHANUMERIC, Pos.PASSWDO, Len.PASSWDO);

  /**
   * @return the passwdo
   */
  public NAsmField getPasswdo() {
    return passwdo;
  }

  /**
   *
   * @throws NAsmException
   */
  public Passwdi(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.PASSWDI);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int PASSWDO = 8;

    public static final int PASSWDI_SLOT = 8;
    public static final int PASSWDI = Len.PASSWDO;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int PASSWDO = 0;

  }

}
