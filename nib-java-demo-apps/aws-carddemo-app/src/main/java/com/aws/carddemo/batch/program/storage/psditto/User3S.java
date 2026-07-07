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
public class User3S extends NAsmField {

  private NAsmField dummy681 = new NAsmField(this, "DUMMY681", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY681, Len.DUMMY681);
  private NAsmField dummy682 = new NAsmField(this, "DUMMY682", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY682, Len.DUMMY682);
  private NAsmField dummy684 = new NAsmField(this, "DUMMY684", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY684, Len.DUMMY684);
  private NAsmField dummy685 = new NAsmField(this, "DUMMY685", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY685, Len.DUMMY685);

  /**
   * @return the dummy681
   */
  public NAsmField getDummy681() {
    return dummy681;
  }

  /**
   * @return the dummy682
   */
  public NAsmField getDummy682() {
    return dummy682;
  }

  /**
   * @return the dummy684
   */
  public NAsmField getDummy684() {
    return dummy684;
  }

  /**
   * @return the dummy685
   */
  public NAsmField getDummy685() {
    return dummy685;
  }

  /**
   *
   * @throws NAsmException
   */
  public User3S(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.USER3S);
    dummy681.set(_PAD("PSDITTO - PROCESSING ERROR IN SPECIAL LCR DITTO ROUTINE.", Len.DUMMY681));
    dummy682.set(_PAD("END OF LCR FIELDS REACHED BEFORE END OF RECORD.", Len.DUMMY682));
    dummy684.set(_PAD("VERIFY THAT INPUT LCR FIELDS MATCH THE LCR RECORD LAYOUT.", Len.DUMMY684));
    dummy685.set(_PAD("ABEND 3003.    MODE=", Len.DUMMY685));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY681 = 75;
    public static final int DUMMY682 = 75;
    public static final int DUMMY684 = 75;
    public static final int DUMMY685 = 30;

    public static final int USER3S = Len.DUMMY681
                                + Len.DUMMY682
                                + Len.DUMMY684
                                + Len.DUMMY685;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY681 = 0;
    public static final int DUMMY682 = Pos.DUMMY681 + Len.DUMMY681;
    public static final int DUMMY684 = Pos.DUMMY682 + Len.DUMMY682;
    public static final int DUMMY685 = Pos.DUMMY684 + Len.DUMMY684;

  }

}
