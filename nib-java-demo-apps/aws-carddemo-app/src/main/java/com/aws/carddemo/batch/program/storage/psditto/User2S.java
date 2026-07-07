package com.aws.carddemo.batch.program.storage.psditto;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.storage.NAsmField;

/**
 * 
 * <b>RP-Asm2Java - ASM370 to JAVA.</b> <br/>
 * <br/>
 * 
 * <br/>
 * 
 * @version 2.2.8
 * 
 * @author RP - Modern Systems
 */
public class User2S extends NAsmField {

  private NAsmField dummy674 = new NAsmField(this, "DUMMY674", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY674, Len.DUMMY674);
  private NAsmField dummy675 = new NAsmField(this, "DUMMY675", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY675, Len.DUMMY675);
  private NAsmField dummy676 = new NAsmField(this, "DUMMY676", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY676, Len.DUMMY676);
  private NAsmField dummy677 = new NAsmField(this, "DUMMY677", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY677, Len.DUMMY677);

  /**
   * @return the dummy674
   */
  public NAsmField getDummy674() {
    return dummy674;
  }

  /**
   * @return the dummy675
   */
  public NAsmField getDummy675() {
    return dummy675;
  }

  /**
   * @return the dummy676
   */
  public NAsmField getDummy676() {
    return dummy676;
  }

  /**
   * @return the dummy677
   */
  public NAsmField getDummy677() {
    return dummy677;
  }

  /**
   *
   * @throws NAsmException
   */
  public User2S(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.USER2S);
    dummy674.set(_PAD("PSDITTO - CARD ERROR ON LCRFLDS DD.  VERIFY FORMAT AS FOLLOWS.", Len.DUMMY674));
    dummy675.set(_PAD("OUTPUT FORM=XXXXX,  WHERE XXXXX IS CHECK OR AUDIT.", Len.DUMMY675));
    dummy676.set(_PAD("# OF FIELDS=XXX,  WHERE XXX IS NUMERIC.", Len.DUMMY676));
    dummy677.set(_PAD("ABEND 3002.    MODE=", Len.DUMMY677));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY674 = 75;
    public static final int DUMMY675 = 75;
    public static final int DUMMY676 = 75;
    public static final int DUMMY677 = 30;

    public static final int USER2S = Len.DUMMY674
                                + Len.DUMMY675
                                + Len.DUMMY676
                                + Len.DUMMY677;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY674 = 0;
    public static final int DUMMY675 = Pos.DUMMY674 + Len.DUMMY674;
    public static final int DUMMY676 = Pos.DUMMY675 + Len.DUMMY675;
    public static final int DUMMY677 = Pos.DUMMY676 + Len.DUMMY676;

  }

}
