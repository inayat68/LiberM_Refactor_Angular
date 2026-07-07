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
public class Applidi extends NAsmField {

  // . OUTPUT DATA FIELD
  private NAsmField applido = new NAsmField(this, "APPLIDO", NAsmFieldType.ALPHANUMERIC, Pos.APPLIDO, Len.APPLIDO);

  /**
   * @return the applido
   */
  public NAsmField getApplido() {
    return applido;
  }

  /**
   *
   * @throws NAsmException
   */
  public Applidi(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.APPLIDI);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int APPLIDO = 8;

    public static final int APPLIDI_SLOT = 8;
    public static final int APPLIDI = Len.APPLIDO;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int APPLIDO = 0;

  }

}
