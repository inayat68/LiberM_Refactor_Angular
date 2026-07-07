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
public class CcdaCommonMessages extends NAsmField {

  private NAsmField msgThankYou = new NAsmField(this, "MSG_THANK_YOU", NAsmFieldType.ALPHANUMERIC, Pos.MSG_THANK_YOU, Len.MSG_THANK_YOU);
  private NAsmField msgInvKey = new NAsmField(this, "MSG_INV_KEY", NAsmFieldType.ALPHANUMERIC, Pos.MSG_INV_KEY, Len.MSG_INV_KEY);

  /**
   * @return the msgThankYou
   */
  public NAsmField getMsgThankYou() {
    return msgThankYou;
  }

  /**
   * @return the msgInvKey
   */
  public NAsmField getMsgInvKey() {
    return msgInvKey;
  }

  /**
   *
   * @throws NAsmException
   */
  public CcdaCommonMessages(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.CCDA_COMMON_MESSAGES);
    msgThankYou.set(_PAD("Thank you for using CardDemo application...", Len.MSG_THANK_YOU));
    msgInvKey.set(_PAD("Invalid key pressed. Please see below...", Len.MSG_INV_KEY));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int MSG_THANK_YOU = 80;
    public static final int MSG_INV_KEY = 50;

    public static final int CCDA_COMMON_MESSAGES = Len.MSG_THANK_YOU
                                + Len.MSG_INV_KEY;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int MSG_THANK_YOU = 0;
    public static final int MSG_INV_KEY = Pos.MSG_THANK_YOU + Len.MSG_THANK_YOU;

  }

}
