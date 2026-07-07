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
public class Pspsline extends NAsmField {

  private NAsmField dummy961 = new NAsmField(this, "DUMMY961", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY961, Len.DUMMY961);
  private NAsmField pspsX = new NAsmField(this, "PSPSX", NAsmFieldType.ALPHANUMERIC, Pos.PSPSX, Len.PSPSX);
  private NAsmField dummy963 = new NAsmField(this, "DUMMY963", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY963, Len.DUMMY963);
  private NAsmField dummy964 = new NAsmField(this, "DUMMY964", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY964, Len.DUMMY964);
  private NAsmField startout = new NAsmField(this, "STARTOUT", NAsmFieldType.ALPHANUMERIC, Pos.STARTOUT, Len.STARTOUT);
  private NAsmField dummy966 = new NAsmField(this, "DUMMY966", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY966, Len.DUMMY966);
  private NAsmField dummy967 = new NAsmField(this, "DUMMY967", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY967, Len.DUMMY967);
  private NAsmField pspslen = new NAsmField(this, "PSPSLEN", NAsmFieldType.ALPHANUMERIC, Pos.PSPSLEN, Len.PSPSLEN);
  private NAsmField dummy969 = new NAsmField(this, "DUMMY969", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY969, Len.DUMMY969);

  /**
   * @return the dummy961
   */
  public NAsmField getDummy961() {
    return dummy961;
  }

  /**
   * @return the pspsX
   */
  public NAsmField getPspsX() {
    return pspsX;
  }

  /**
   * @return the dummy963
   */
  public NAsmField getDummy963() {
    return dummy963;
  }

  /**
   * @return the dummy964
   */
  public NAsmField getDummy964() {
    return dummy964;
  }

  /**
   * @return the startout
   */
  public NAsmField getStartout() {
    return startout;
  }

  /**
   * @return the dummy966
   */
  public NAsmField getDummy966() {
    return dummy966;
  }

  /**
   * @return the dummy967
   */
  public NAsmField getDummy967() {
    return dummy967;
  }

  /**
   * @return the pspslen
   */
  public NAsmField getPspslen() {
    return pspslen;
  }

  /**
   * @return the dummy969
   */
  public NAsmField getDummy969() {
    return dummy969;
  }

  /**
   *
   * @throws NAsmException
   */
  public Pspsline(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.PSPSLINE);
    dummy961.set(_SPACE(Len.DUMMY961));
    pspsX.set(_PAD("PSPS", Len.PSPSX));
    dummy963.set(_SPACE(Len.DUMMY963));
    dummy964.set("START POSITION=");
    startout.set(_SPACE(Len.STARTOUT));
    dummy966.set(_SPACE(Len.DUMMY966));
    dummy967.set("LENGTH=");
    pspslen.set(_SPACE(Len.PSPSLEN));
    dummy969.set(_SPACE(Len.DUMMY969));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY961 = 20;
    public static final int PSPSX = 8;
    public static final int DUMMY963 = 3;
    public static final int DUMMY964 = 15;
    public static final int STARTOUT = 4;
    public static final int DUMMY966 = 3;
    public static final int DUMMY967 = 7;
    public static final int PSPSLEN = 3;
    public static final int DUMMY969 = 70;

    public static final int PSPSLINE_SLOT = 133;
    public static final int PSPSLINE = Len.DUMMY961
                                + Len.PSPSX
                                + Len.DUMMY963
                                + Len.DUMMY964
                                + Len.STARTOUT
                                + Len.DUMMY966
                                + Len.DUMMY967
                                + Len.PSPSLEN
                                + Len.DUMMY969;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY961 = 0;
    public static final int PSPSX = Pos.DUMMY961 + Len.DUMMY961;
    public static final int DUMMY963 = Pos.PSPSX + Len.PSPSX;
    public static final int DUMMY964 = Pos.DUMMY963 + Len.DUMMY963;
    public static final int STARTOUT = Pos.DUMMY964 + Len.DUMMY964;
    public static final int DUMMY966 = Pos.STARTOUT + Len.STARTOUT;
    public static final int DUMMY967 = Pos.DUMMY966 + Len.DUMMY966;
    public static final int PSPSLEN = Pos.DUMMY967 + Len.DUMMY967;
    public static final int DUMMY969 = Pos.PSPSLEN + Len.PSPSLEN;

  }

}
