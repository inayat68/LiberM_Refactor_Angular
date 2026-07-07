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
public class Numrline extends NAsmField {

  private NAsmField dummy922 = new NAsmField(this, "DUMMY922", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY922, Len.DUMMY922);
  private NAsmField numstat = new NAsmField(this, "NUMSTAT", NAsmFieldType.ALPHANUMERIC, Pos.NUMSTAT, Len.NUMSTAT);
  private NAsmField dummy924 = new NAsmField(this, "DUMMY924", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY924, Len.DUMMY924);
  private NAsmField numr100 = new NAsmField(this, "NUMR100", NAsmFieldType.ALPHANUMERIC, Pos.NUMR100, Len.NUMR100);
  private NAsmField dummy926 = new NAsmField(this, "DUMMY926", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY926, Len.DUMMY926);

  /**
   * @return the dummy922
   */
  public NAsmField getDummy922() {
    return dummy922;
  }

  /**
   * @return the numstat
   */
  public NAsmField getNumstat() {
    return numstat;
  }

  /**
   * @return the dummy924
   */
  public NAsmField getDummy924() {
    return dummy924;
  }

  /**
   * @return the numr100
   */
  public NAsmField getNumr100() {
    return numr100;
  }

  /**
   * @return the dummy926
   */
  public NAsmField getDummy926() {
    return dummy926;
  }

  /**
   *
   * @throws NAsmException
   */
  public Numrline(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.NUMRLINE);
    dummy922.set(_SPACE(Len.DUMMY922));
    numstat.set(_SPACE(Len.NUMSTAT));
    dummy924.set("NUMR ");
    numr100.set(_LOW(Len.NUMR100));
    dummy926.set(_SPACE(Len.DUMMY926));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY922 = 1;
    public static final int NUMSTAT = 22;
    public static final int DUMMY924 = 5;
    public static final int NUMR100 = 100;
    public static final int DUMMY926 = 5;

    public static final int NUMRLINE_SLOT = 133;
    public static final int NUMRLINE = Len.DUMMY922
                                + Len.NUMSTAT
                                + Len.DUMMY924
                                + Len.NUMR100
                                + Len.DUMMY926;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY922 = 0;
    public static final int NUMSTAT = Pos.DUMMY922 + Len.DUMMY922;
    public static final int DUMMY924 = Pos.NUMSTAT + Len.NUMSTAT;
    public static final int NUMR100 = Pos.DUMMY924 + Len.DUMMY924;
    public static final int DUMMY926 = Pos.NUMR100 + Len.NUMR100;

  }

}
