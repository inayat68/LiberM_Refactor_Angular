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
public class Charline extends NAsmField {

  private NAsmField ccon = new NAsmField(this, "CCON", NAsmFieldType.ALPHANUMERIC, Pos.CCON, Len.CCON);
  private NAsmField chardata = new NAsmField(this, "CHARDATA", NAsmFieldType.ALPHANUMERIC, Pos.CHARDATA, Len.CHARDATA);
  private NAsmField charflds = new NAsmField(this, "CHARFLDS", NAsmFieldType.ALPHANUMERIC, Pos.CHARFLDS, Len.CHARFLDS);
  private Dummy892 dummy892 = new Dummy892(charflds, "DUMMY892", 0);
  private Dummy897 dummy897 = new Dummy897(charflds, "DUMMY897", 0);
  private NAsmField dummy902 = new NAsmField(this, "DUMMY902", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY902, Len.DUMMY902);
  private NAsmField char100 = new NAsmField(this, "CHAR100", NAsmFieldType.ALPHANUMERIC, Pos.CHAR100, Len.CHAR100);
  private NAsmField dummy904 = new NAsmField(this, "DUMMY904", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY904, Len.DUMMY904);

  /**
   * @return the ccon
   */
  public NAsmField getCcon() {
    return ccon;
  }

  /**
   * @return the chardata
   */
  public NAsmField getChardata() {
    return chardata;
  }

  /**
   * @return the charflds
   */
  public NAsmField getCharflds() {
    return charflds;
  }

  /**
   * @return the dummy892
   */
  public Dummy892 getDummy892() {
    return dummy892;
  }

  /**
   * @return the dummy897
   */
  public Dummy897 getDummy897() {
    return dummy897;
  }

  /**
   * @return the dummy902
   */
  public NAsmField getDummy902() {
    return dummy902;
  }

  /**
   * @return the char100
   */
  public NAsmField getChar100() {
    return char100;
  }

  /**
   * @return the dummy904
   */
  public NAsmField getDummy904() {
    return dummy904;
  }

  /**
   *
   * @throws NAsmException
   */
  public Charline(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.CHARLINE);
    ccon.set("0");
    chardata.set(_SPACE(Len.CHARDATA));
    dummy902.set(" CHAR ");
    char100.set(_SPACE(Len.CHAR100));
    dummy904.set(_SPACE(Len.DUMMY904));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int CCON = 1;
    public static final int CHARDATA = 3;
    public static final int CHARFLDS = 18;
    public static final int DUMMY902 = 6;
    public static final int CHAR100 = 100;
    public static final int DUMMY904 = 5;

    public static final int CHARLINE_SLOT = 133;
    public static final int CHARLINE = Len.CCON
                                + Len.CHARDATA
                                + Len.CHARFLDS
                                + Len.DUMMY902
                                + Len.CHAR100
                                + Len.DUMMY904;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int CCON = 0;
    public static final int CHARDATA = Pos.CCON + Len.CCON;
    public static final int CHARFLDS = Pos.CHARDATA + Len.CHARDATA;
    public static final int DUMMY892 = Pos.CHARFLDS;
    public static final int DUMMY897 = Pos.CHARFLDS;
    public static final int DUMMY902 = Pos.DUMMY897 + Dummy897.Len.DUMMY897;
    public static final int CHAR100 = Pos.DUMMY902 + Len.DUMMY902;
    public static final int DUMMY904 = Pos.CHAR100 + Len.CHAR100;

  }

}
