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
public class Guide extends NAsmField {

  private NAsmField dummy933 = new NAsmField(this, "DUMMY933", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY933, Len.DUMMY933);
  private NAsmField segloc = new NAsmField(this, "SEGLOC", NAsmFieldType.ALPHANUMERIC, Pos.SEGLOC, Len.SEGLOC);
  private NAsmField dummy935 = new NAsmField(this, "DUMMY935", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY935, Len.DUMMY935);
  private NAsmField guideX = new NAsmField(this, "GUIDEX", NAsmFieldType.ALPHANUMERIC, Pos.GUIDEX, Len.GUIDEX);
  private NAsmField dummy937 = new NAsmField(this, "DUMMY937", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY937, Len.DUMMY937);
  private NAsmField dummy938 = new NAsmField(this, "DUMMY938", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY938, Len.DUMMY938);
  private NAsmField dummy939 = new NAsmField(this, "DUMMY939", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY939, Len.DUMMY939);
  private NAsmField dummy940 = new NAsmField(this, "DUMMY940", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY940, Len.DUMMY940);
  private NAsmField dummy941 = new NAsmField(this, "DUMMY941", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY941, Len.DUMMY941);
  private NAsmField dummy942 = new NAsmField(this, "DUMMY942", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY942, Len.DUMMY942);
  private NAsmField dummy943 = new NAsmField(this, "DUMMY943", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY943, Len.DUMMY943);
  private NAsmField dummy944 = new NAsmField(this, "DUMMY944", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY944, Len.DUMMY944);
  private NAsmField dummy945 = new NAsmField(this, "DUMMY945", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY945, Len.DUMMY945);
  private NAsmField dummy946 = new NAsmField(this, "DUMMY946", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY946, Len.DUMMY946);
  private NAsmField dummy947 = new NAsmField(this, "DUMMY947", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY947, Len.DUMMY947);

  /**
   * @return the dummy933
   */
  public NAsmField getDummy933() {
    return dummy933;
  }

  /**
   * @return the segloc
   */
  public NAsmField getSegloc() {
    return segloc;
  }

  /**
   * @return the dummy935
   */
  public NAsmField getDummy935() {
    return dummy935;
  }

  /**
   * @return the guideX
   */
  public NAsmField getGuideX() {
    return guideX;
  }

  /**
   * @return the dummy937
   */
  public NAsmField getDummy937() {
    return dummy937;
  }

  /**
   * @return the dummy938
   */
  public NAsmField getDummy938() {
    return dummy938;
  }

  /**
   * @return the dummy939
   */
  public NAsmField getDummy939() {
    return dummy939;
  }

  /**
   * @return the dummy940
   */
  public NAsmField getDummy940() {
    return dummy940;
  }

  /**
   * @return the dummy941
   */
  public NAsmField getDummy941() {
    return dummy941;
  }

  /**
   * @return the dummy942
   */
  public NAsmField getDummy942() {
    return dummy942;
  }

  /**
   * @return the dummy943
   */
  public NAsmField getDummy943() {
    return dummy943;
  }

  /**
   * @return the dummy944
   */
  public NAsmField getDummy944() {
    return dummy944;
  }

  /**
   * @return the dummy945
   */
  public NAsmField getDummy945() {
    return dummy945;
  }

  /**
   * @return the dummy946
   */
  public NAsmField getDummy946() {
    return dummy946;
  }

  /**
   * @return the dummy947
   */
  public NAsmField getDummy947() {
    return dummy947;
  }

  /**
   *
   * @throws NAsmException
   */
  public Guide(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.GUIDE);
    dummy933.set(_SPACE(Len.DUMMY933));
    segloc.set(_SPACE(Len.SEGLOC));
    dummy935.set(_SPACE(Len.DUMMY935));
    guideX.set(_SPACE(Len.GUIDEX));
    dummy937.set("01...5...10");
    dummy938.set("...15...20");
    dummy939.set("...25...30");
    dummy940.set("...35...40");
    dummy941.set("...45...50");
    dummy942.set("...55...60");
    dummy943.set("...65...70");
    dummy944.set("...75...80");
    dummy945.set("...85...90");
    dummy946.set("...95.....");
    dummy947.set(_SPACE(Len.DUMMY947));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY933 = 14;
    public static final int SEGLOC = 8;
    public static final int DUMMY935 = 2;
    public static final int GUIDEX = 3;
    public static final int DUMMY937 = 11;
    public static final int DUMMY938 = 10;
    public static final int DUMMY939 = 10;
    public static final int DUMMY940 = 10;
    public static final int DUMMY941 = 10;
    public static final int DUMMY942 = 10;
    public static final int DUMMY943 = 10;
    public static final int DUMMY944 = 10;
    public static final int DUMMY945 = 10;
    public static final int DUMMY946 = 10;
    public static final int DUMMY947 = 5;

    public static final int GUIDE_SLOT = 133;
    public static final int GUIDE = Len.DUMMY933
                                + Len.SEGLOC
                                + Len.DUMMY935
                                + Len.GUIDEX
                                + Len.DUMMY937
                                + Len.DUMMY938
                                + Len.DUMMY939
                                + Len.DUMMY940
                                + Len.DUMMY941
                                + Len.DUMMY942
                                + Len.DUMMY943
                                + Len.DUMMY944
                                + Len.DUMMY945
                                + Len.DUMMY946
                                + Len.DUMMY947;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY933 = 0;
    public static final int SEGLOC = Pos.DUMMY933 + Len.DUMMY933;
    public static final int DUMMY935 = Pos.SEGLOC + Len.SEGLOC;
    public static final int GUIDEX = Pos.DUMMY935 + Len.DUMMY935;
    public static final int DUMMY937 = Pos.GUIDEX + Len.GUIDEX;
    public static final int DUMMY938 = Pos.DUMMY937 + Len.DUMMY937;
    public static final int DUMMY939 = Pos.DUMMY938 + Len.DUMMY938;
    public static final int DUMMY940 = Pos.DUMMY939 + Len.DUMMY939;
    public static final int DUMMY941 = Pos.DUMMY940 + Len.DUMMY940;
    public static final int DUMMY942 = Pos.DUMMY941 + Len.DUMMY941;
    public static final int DUMMY943 = Pos.DUMMY942 + Len.DUMMY942;
    public static final int DUMMY944 = Pos.DUMMY943 + Len.DUMMY943;
    public static final int DUMMY945 = Pos.DUMMY944 + Len.DUMMY944;
    public static final int DUMMY946 = Pos.DUMMY945 + Len.DUMMY945;
    public static final int DUMMY947 = Pos.DUMMY946 + Len.DUMMY946;

  }

}
