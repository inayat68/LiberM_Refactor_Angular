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
public class Trans extends NAsmField {

  // .0.1.2.3.4.5.6.7.8.9.A.B.C.D.E.F
  // 0.
  private NAsmField dummy991 = new NAsmField(this, "DUMMY991", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY991, Len.DUMMY991);
  // 1.
  private NAsmField dummy992 = new NAsmField(this, "DUMMY992", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY992, Len.DUMMY992);
  // 2.
  private NAsmField dummy993 = new NAsmField(this, "DUMMY993", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY993, Len.DUMMY993);
  // 3.
  private NAsmField dummy994 = new NAsmField(this, "DUMMY994", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY994, Len.DUMMY994);
  // 4.
  private NAsmField dummy995 = new NAsmField(this, "DUMMY995", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY995, Len.DUMMY995);
  // 5.
  private NAsmField dummy996 = new NAsmField(this, "DUMMY996", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY996, Len.DUMMY996);
  // 6.
  private NAsmField dummy997 = new NAsmField(this, "DUMMY997", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY997, Len.DUMMY997);
  // 7.
  private NAsmField dummy998 = new NAsmField(this, "DUMMY998", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY998, Len.DUMMY998);
  // 8.
  private NAsmField dummy999 = new NAsmField(this, "DUMMY999", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY999, Len.DUMMY999);
  // 9.
  private NAsmField dummy1000 = new NAsmField(this, "DUMMY1000", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1000, Len.DUMMY1000);
  // A.
  private NAsmField dummy1001 = new NAsmField(this, "DUMMY1001", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1001, Len.DUMMY1001);
  // B.
  private NAsmField dummy1002 = new NAsmField(this, "DUMMY1002", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1002, Len.DUMMY1002);
  // C.
  private NAsmField dummy1003 = new NAsmField(this, "DUMMY1003", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1003, Len.DUMMY1003);
  // D.
  private NAsmField dummy1004 = new NAsmField(this, "DUMMY1004", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1004, Len.DUMMY1004);
  // E.
  private NAsmField dummy1005 = new NAsmField(this, "DUMMY1005", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1005, Len.DUMMY1005);
  // F.
  private NAsmField dummy1006 = new NAsmField(this, "DUMMY1006", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1006, Len.DUMMY1006);

  /**
   * @return the dummy991
   */
  public NAsmField getDummy991() {
    return dummy991;
  }

  /**
   * @return the dummy992
   */
  public NAsmField getDummy992() {
    return dummy992;
  }

  /**
   * @return the dummy993
   */
  public NAsmField getDummy993() {
    return dummy993;
  }

  /**
   * @return the dummy994
   */
  public NAsmField getDummy994() {
    return dummy994;
  }

  /**
   * @return the dummy995
   */
  public NAsmField getDummy995() {
    return dummy995;
  }

  /**
   * @return the dummy996
   */
  public NAsmField getDummy996() {
    return dummy996;
  }

  /**
   * @return the dummy997
   */
  public NAsmField getDummy997() {
    return dummy997;
  }

  /**
   * @return the dummy998
   */
  public NAsmField getDummy998() {
    return dummy998;
  }

  /**
   * @return the dummy999
   */
  public NAsmField getDummy999() {
    return dummy999;
  }

  /**
   * @return the dummy1000
   */
  public NAsmField getDummy1000() {
    return dummy1000;
  }

  /**
   * @return the dummy1001
   */
  public NAsmField getDummy1001() {
    return dummy1001;
  }

  /**
   * @return the dummy1002
   */
  public NAsmField getDummy1002() {
    return dummy1002;
  }

  /**
   * @return the dummy1003
   */
  public NAsmField getDummy1003() {
    return dummy1003;
  }

  /**
   * @return the dummy1004
   */
  public NAsmField getDummy1004() {
    return dummy1004;
  }

  /**
   * @return the dummy1005
   */
  public NAsmField getDummy1005() {
    return dummy1005;
  }

  /**
   * @return the dummy1006
   */
  public NAsmField getDummy1006() {
    return dummy1006;
  }

  /**
   *
   * @throws NAsmException
   */
  public Trans(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.TRANS);
    dummy991.setHexValue("0xf0f1f2f3f4f5f6f7f8f9c1c2c3c4c5c6");
    dummy992.setHexValue("0xf1404040404040404040404040404040");
    dummy993.setHexValue("0xf2404040404040404040404040404040");
    dummy994.setHexValue("0xf3404040404040404040404040404040");
    dummy995.setHexValue("0xf4404040404040404040404040404040");
    dummy996.setHexValue("0xf5404040404040404040404040404040");
    dummy997.setHexValue("0xf6404040404040404040404040404040");
    dummy998.setHexValue("0xf7404040404040404040404040404040");
    dummy999.setHexValue("0xf8404040404040404040404040404040");
    dummy1000.setHexValue("0xf9404040404040404040404040404040");
    dummy1001.setHexValue("0xc1404040404040404040404040404040");
    dummy1002.setHexValue("0xc2404040404040404040404040404040");
    dummy1003.setHexValue("0xc3404040404040404040404040404040");
    dummy1004.setHexValue("0xc4404040404040404040404040404040");
    dummy1005.setHexValue("0xc5404040404040404040404040404040");
    dummy1006.setHexValue("0xc6404040404040404040404040404040");
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY991 = 16;
    public static final int DUMMY992 = 16;
    public static final int DUMMY993 = 16;
    public static final int DUMMY994 = 16;
    public static final int DUMMY995 = 16;
    public static final int DUMMY996 = 16;
    public static final int DUMMY997 = 16;
    public static final int DUMMY998 = 16;
    public static final int DUMMY999 = 16;
    public static final int DUMMY1000 = 16;
    public static final int DUMMY1001 = 16;
    public static final int DUMMY1002 = 16;
    public static final int DUMMY1003 = 16;
    public static final int DUMMY1004 = 16;
    public static final int DUMMY1005 = 16;
    public static final int DUMMY1006 = 16;

    public static final int TRANS_SLOT = 256;
    public static final int TRANS = Len.DUMMY991
                                + Len.DUMMY992
                                + Len.DUMMY993
                                + Len.DUMMY994
                                + Len.DUMMY995
                                + Len.DUMMY996
                                + Len.DUMMY997
                                + Len.DUMMY998
                                + Len.DUMMY999
                                + Len.DUMMY1000
                                + Len.DUMMY1001
                                + Len.DUMMY1002
                                + Len.DUMMY1003
                                + Len.DUMMY1004
                                + Len.DUMMY1005
                                + Len.DUMMY1006;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY991 = 0;
    public static final int DUMMY992 = Pos.DUMMY991 + Len.DUMMY991;
    public static final int DUMMY993 = Pos.DUMMY992 + Len.DUMMY992;
    public static final int DUMMY994 = Pos.DUMMY993 + Len.DUMMY993;
    public static final int DUMMY995 = Pos.DUMMY994 + Len.DUMMY994;
    public static final int DUMMY996 = Pos.DUMMY995 + Len.DUMMY995;
    public static final int DUMMY997 = Pos.DUMMY996 + Len.DUMMY996;
    public static final int DUMMY998 = Pos.DUMMY997 + Len.DUMMY997;
    public static final int DUMMY999 = Pos.DUMMY998 + Len.DUMMY998;
    public static final int DUMMY1000 = Pos.DUMMY999 + Len.DUMMY999;
    public static final int DUMMY1001 = Pos.DUMMY1000 + Len.DUMMY1000;
    public static final int DUMMY1002 = Pos.DUMMY1001 + Len.DUMMY1001;
    public static final int DUMMY1003 = Pos.DUMMY1002 + Len.DUMMY1002;
    public static final int DUMMY1004 = Pos.DUMMY1003 + Len.DUMMY1003;
    public static final int DUMMY1005 = Pos.DUMMY1004 + Len.DUMMY1004;
    public static final int DUMMY1006 = Pos.DUMMY1005 + Len.DUMMY1005;

  }

}
