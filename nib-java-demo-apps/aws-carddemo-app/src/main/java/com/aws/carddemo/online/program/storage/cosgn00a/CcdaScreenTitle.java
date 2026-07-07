package com.aws.carddemo.online.program.storage.cosgn00a;

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
public class CcdaScreenTitle extends NAsmField {

  private NAsmField ccdaTitle01 = new NAsmField(this, "CCDA_TITLE01", NAsmFieldType.ALPHANUMERIC, Pos.CCDA_TITLE01, Len.CCDA_TITLE01);
  private NAsmField ccdaTitle02 = new NAsmField(this, "CCDA_TITLE02", NAsmFieldType.ALPHANUMERIC, Pos.CCDA_TITLE02, Len.CCDA_TITLE02);

  /**
   * @return the ccdaTitle01
   */
  public NAsmField getCcdaTitle01() {
    return ccdaTitle01;
  }

  /**
   * @return the ccdaTitle02
   */
  public NAsmField getCcdaTitle02() {
    return ccdaTitle02;
  }

  /**
   *
   * @throws NAsmException
   */
  public CcdaScreenTitle(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.CCDA_SCREEN_TITLE);
    ccdaTitle01.set(_PAD("      AWS Mainframe Modernization", Len.CCDA_TITLE01));
    ccdaTitle02.set(_PAD("              ASM CardDemo ", Len.CCDA_TITLE02));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int CCDA_TITLE01 = 40;
    public static final int CCDA_TITLE02 = 40;

    public static final int CCDA_SCREEN_TITLE = Len.CCDA_TITLE01
                                + Len.CCDA_TITLE02;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int CCDA_TITLE01 = 0;
    public static final int CCDA_TITLE02 = Pos.CCDA_TITLE01 + Len.CCDA_TITLE01;

  }

}
