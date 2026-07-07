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
public class Errmsgi extends NAsmField {

  // . OUTPUT DATA FIELD
  private NAsmField errmsgo = new NAsmField(this, "ERRMSGO", NAsmFieldType.ALPHANUMERIC, Pos.ERRMSGO, Len.ERRMSGO);

  /**
   * @return the errmsgo
   */
  public NAsmField getErrmsgo() {
    return errmsgo;
  }

  /**
   *
   * @throws NAsmException
   */
  public Errmsgi(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.ERRMSGI);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int ERRMSGO = 78;

    public static final int ERRMSGI_SLOT = 78;
    public static final int ERRMSGI = Len.ERRMSGO;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int ERRMSGO = 0;

  }

}
