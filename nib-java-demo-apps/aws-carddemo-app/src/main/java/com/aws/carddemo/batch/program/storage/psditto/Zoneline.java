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
public class Zoneline extends NAsmField {

  private NAsmField dummy911 = new NAsmField(this, "DUMMY911", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY911, Len.DUMMY911);
  private NAsmField zonestat = new NAsmField(this, "ZONESTAT", NAsmFieldType.ALPHANUMERIC, Pos.ZONESTAT, Len.ZONESTAT);
  private NAsmField dummy913 = new NAsmField(this, "DUMMY913", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY913, Len.DUMMY913);
  private NAsmField zone100 = new NAsmField(this, "ZONE100", NAsmFieldType.ALPHANUMERIC, Pos.ZONE100, Len.ZONE100);
  private NAsmField dummy915 = new NAsmField(this, "DUMMY915", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY915, Len.DUMMY915);

  /**
   * @return the dummy911
   */
  public NAsmField getDummy911() {
    return dummy911;
  }

  /**
   * @return the zonestat
   */
  public NAsmField getZonestat() {
    return zonestat;
  }

  /**
   * @return the dummy913
   */
  public NAsmField getDummy913() {
    return dummy913;
  }

  /**
   * @return the zone100
   */
  public NAsmField getZone100() {
    return zone100;
  }

  /**
   * @return the dummy915
   */
  public NAsmField getDummy915() {
    return dummy915;
  }

  /**
   *
   * @throws NAsmException
   */
  public Zoneline(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.ZONELINE);
    dummy911.set(_SPACE(Len.DUMMY911));
    zonestat.set(_SPACE(Len.ZONESTAT));
    dummy913.set("ZONE ");
    zone100.set(_SPACE(Len.ZONE100));
    dummy915.set(_SPACE(Len.DUMMY915));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY911 = 1;
    public static final int ZONESTAT = 22;
    public static final int DUMMY913 = 5;
    public static final int ZONE100 = 100;
    public static final int DUMMY915 = 5;

    public static final int ZONELINE_SLOT = 133;
    public static final int ZONELINE = Len.DUMMY911
                                + Len.ZONESTAT
                                + Len.DUMMY913
                                + Len.ZONE100
                                + Len.DUMMY915;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY911 = 0;
    public static final int ZONESTAT = Pos.DUMMY911 + Len.DUMMY911;
    public static final int DUMMY913 = Pos.ZONESTAT + Len.ZONESTAT;
    public static final int ZONE100 = Pos.DUMMY913 + Len.DUMMY913;
    public static final int DUMMY915 = Pos.ZONE100 + Len.ZONE100;

  }

}
