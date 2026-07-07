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
public class Sysidi extends NAsmField {

  // . OUTPUT DATA FIELD
  private NAsmField sysido = new NAsmField(this, "SYSIDO", NAsmFieldType.ALPHANUMERIC, Pos.SYSIDO, Len.SYSIDO);

  /**
   * @return the sysido
   */
  public NAsmField getSysido() {
    return sysido;
  }

  /**
   *
   * @throws NAsmException
   */
  public Sysidi(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.SYSIDI);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int SYSIDO = 8;

    public static final int SYSIDI_SLOT = 8;
    public static final int SYSIDI = Len.SYSIDO;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int SYSIDO = 0;

  }

}
