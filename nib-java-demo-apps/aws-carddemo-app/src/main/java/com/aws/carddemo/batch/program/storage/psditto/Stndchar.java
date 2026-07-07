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
public class Stndchar extends NAsmField {

  // .0.1.2.3.4.5.6.7.8.9.A.B.C.D.E.F
  // 0.
  private NAsmField dummy1011 = new NAsmField(this, "DUMMY1011", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1011, Len.DUMMY1011);
  // 1.
  private NAsmField dummy1012 = new NAsmField(this, "DUMMY1012", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1012, Len.DUMMY1012);
  // 2.
  private NAsmField dummy1013 = new NAsmField(this, "DUMMY1013", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1013, Len.DUMMY1013);
  // 3.
  private NAsmField dummy1014 = new NAsmField(this, "DUMMY1014", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1014, Len.DUMMY1014);
  // 4. *
  private NAsmField dummy1015 = new NAsmField(this, "DUMMY1015", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1015, Len.DUMMY1015);
  // 5. *
  private NAsmField dummy1016 = new NAsmField(this, "DUMMY1016", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1016, Len.DUMMY1016);
  // 6. *
  private NAsmField dummy1017 = new NAsmField(this, "DUMMY1017", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1017, Len.DUMMY1017);
  // 7. *
  private NAsmField dummy1018 = new NAsmField(this, "DUMMY1018", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1018, Len.DUMMY1018);
  // 8.
  private NAsmField dummy1019 = new NAsmField(this, "DUMMY1019", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1019, Len.DUMMY1019);
  // 9.
  private NAsmField dummy1020 = new NAsmField(this, "DUMMY1020", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1020, Len.DUMMY1020);
  // A.
  private NAsmField dummy1021 = new NAsmField(this, "DUMMY1021", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1021, Len.DUMMY1021);
  // B.
  private NAsmField dummy1022 = new NAsmField(this, "DUMMY1022", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1022, Len.DUMMY1022);
  // C. * A-I
  private NAsmField dummy1023 = new NAsmField(this, "DUMMY1023", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1023, Len.DUMMY1023);
  // D. * J-R
  private NAsmField dummy1024 = new NAsmField(this, "DUMMY1024", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1024, Len.DUMMY1024);
  // E. * S-Z
  private NAsmField dummy1025 = new NAsmField(this, "DUMMY1025", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1025, Len.DUMMY1025);
  // F. * 0-9
  private NAsmField dummy1026 = new NAsmField(this, "DUMMY1026", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY1026, Len.DUMMY1026);

  /**
   * @return the dummy1011
   */
  public NAsmField getDummy1011() {
    return dummy1011;
  }

  /**
   * @return the dummy1012
   */
  public NAsmField getDummy1012() {
    return dummy1012;
  }

  /**
   * @return the dummy1013
   */
  public NAsmField getDummy1013() {
    return dummy1013;
  }

  /**
   * @return the dummy1014
   */
  public NAsmField getDummy1014() {
    return dummy1014;
  }

  /**
   * @return the dummy1015
   */
  public NAsmField getDummy1015() {
    return dummy1015;
  }

  /**
   * @return the dummy1016
   */
  public NAsmField getDummy1016() {
    return dummy1016;
  }

  /**
   * @return the dummy1017
   */
  public NAsmField getDummy1017() {
    return dummy1017;
  }

  /**
   * @return the dummy1018
   */
  public NAsmField getDummy1018() {
    return dummy1018;
  }

  /**
   * @return the dummy1019
   */
  public NAsmField getDummy1019() {
    return dummy1019;
  }

  /**
   * @return the dummy1020
   */
  public NAsmField getDummy1020() {
    return dummy1020;
  }

  /**
   * @return the dummy1021
   */
  public NAsmField getDummy1021() {
    return dummy1021;
  }

  /**
   * @return the dummy1022
   */
  public NAsmField getDummy1022() {
    return dummy1022;
  }

  /**
   * @return the dummy1023
   */
  public NAsmField getDummy1023() {
    return dummy1023;
  }

  /**
   * @return the dummy1024
   */
  public NAsmField getDummy1024() {
    return dummy1024;
  }

  /**
   * @return the dummy1025
   */
  public NAsmField getDummy1025() {
    return dummy1025;
  }

  /**
   * @return the dummy1026
   */
  public NAsmField getDummy1026() {
    return dummy1026;
  }

  /**
   *
   * @throws NAsmException
   */
  public Stndchar(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.STNDCHAR);
    dummy1011.setHexValue("0x40404040404040404040404040404040");
    dummy1012.setHexValue("0x40404040404040404040404040404040");
    dummy1013.setHexValue("0x40404040404040404040404040404040");
    dummy1014.setHexValue("0x40404040404040404040404040404040");
    dummy1015.setHexValue("0x404040404040404040404a4b4c4d4e4f");
    dummy1016.setHexValue("0x504040404040404040405a5b5c5d5e5f");
    dummy1017.setHexValue("0x60614040404040404040406b6c6d6e6f");
    dummy1018.setHexValue("0x404040404040404040407a7b7c7d7e7f");
    dummy1019.setHexValue("0x40818283848586878889404040404040");
    dummy1020.setHexValue("0x40919293949596979899404040404040");
    dummy1021.setHexValue("0x4040a2a3a4a5a6a7a8a9404040404040");
    dummy1022.setHexValue("0xb0b1b2b3b4b5b6b7b8b9404040404040");
    dummy1023.setHexValue("0x40c1c2c3c4c5c6c7c8c9404040404040");
    dummy1024.setHexValue("0x40d1d2d3d4d5d6d7d8d9404040404040");
    dummy1025.setHexValue("0x4040e2e3e4e5e6e7e8e9404040404040");
    dummy1026.setHexValue("0xf0f1f2f3f4f5f6f7f8f9404040404040");
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY1011 = 16;
    public static final int DUMMY1012 = 16;
    public static final int DUMMY1013 = 16;
    public static final int DUMMY1014 = 16;
    public static final int DUMMY1015 = 16;
    public static final int DUMMY1016 = 16;
    public static final int DUMMY1017 = 16;
    public static final int DUMMY1018 = 16;
    public static final int DUMMY1019 = 16;
    public static final int DUMMY1020 = 16;
    public static final int DUMMY1021 = 16;
    public static final int DUMMY1022 = 16;
    public static final int DUMMY1023 = 16;
    public static final int DUMMY1024 = 16;
    public static final int DUMMY1025 = 16;
    public static final int DUMMY1026 = 16;

    public static final int STNDCHAR_SLOT = 256;
    public static final int STNDCHAR = Len.DUMMY1011
                                + Len.DUMMY1012
                                + Len.DUMMY1013
                                + Len.DUMMY1014
                                + Len.DUMMY1015
                                + Len.DUMMY1016
                                + Len.DUMMY1017
                                + Len.DUMMY1018
                                + Len.DUMMY1019
                                + Len.DUMMY1020
                                + Len.DUMMY1021
                                + Len.DUMMY1022
                                + Len.DUMMY1023
                                + Len.DUMMY1024
                                + Len.DUMMY1025
                                + Len.DUMMY1026;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY1011 = 0;
    public static final int DUMMY1012 = Pos.DUMMY1011 + Len.DUMMY1011;
    public static final int DUMMY1013 = Pos.DUMMY1012 + Len.DUMMY1012;
    public static final int DUMMY1014 = Pos.DUMMY1013 + Len.DUMMY1013;
    public static final int DUMMY1015 = Pos.DUMMY1014 + Len.DUMMY1014;
    public static final int DUMMY1016 = Pos.DUMMY1015 + Len.DUMMY1015;
    public static final int DUMMY1017 = Pos.DUMMY1016 + Len.DUMMY1016;
    public static final int DUMMY1018 = Pos.DUMMY1017 + Len.DUMMY1017;
    public static final int DUMMY1019 = Pos.DUMMY1018 + Len.DUMMY1018;
    public static final int DUMMY1020 = Pos.DUMMY1019 + Len.DUMMY1019;
    public static final int DUMMY1021 = Pos.DUMMY1020 + Len.DUMMY1020;
    public static final int DUMMY1022 = Pos.DUMMY1021 + Len.DUMMY1021;
    public static final int DUMMY1023 = Pos.DUMMY1022 + Len.DUMMY1022;
    public static final int DUMMY1024 = Pos.DUMMY1023 + Len.DUMMY1023;
    public static final int DUMMY1025 = Pos.DUMMY1024 + Len.DUMMY1024;
    public static final int DUMMY1026 = Pos.DUMMY1025 + Len.DUMMY1025;

  }

}
