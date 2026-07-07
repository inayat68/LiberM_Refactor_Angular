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
public class Subtit1 extends NAsmField {

  private NAsmField dummy862 = new NAsmField(this, "DUMMY862", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY862, Len.DUMMY862);
  private NAsmField dummy863 = new NAsmField(this, "DUMMY863", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY863, Len.DUMMY863);
  private NAsmField recfm = new NAsmField(this, "RECFM", NAsmFieldType.ALPHANUMERIC, Pos.RECFM, Len.RECFM);
  private NAsmField dummy865 = new NAsmField(this, "DUMMY865", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY865, Len.DUMMY865);
  private NAsmField lrecl = new NAsmField(this, "LRECL", NAsmFieldType.ALPHANUMERIC, Pos.LRECL, Len.LRECL);
  private NAsmField dummy867 = new NAsmField(this, "DUMMY867", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY867, Len.DUMMY867);
  private NAsmField blksize = new NAsmField(this, "BLKSIZE", NAsmFieldType.ALPHANUMERIC, Pos.BLKSIZE, Len.BLKSIZE);
  private NAsmField dummy869 = new NAsmField(this, "DUMMY869", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY869, Len.DUMMY869);
  private NAsmField volser = new NAsmField(this, "VOLSER", NAsmFieldType.ALPHANUMERIC, Pos.VOLSER, Len.VOLSER);
  private NAsmField dummy871 = new NAsmField(this, "DUMMY871", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY871, Len.DUMMY871);

  /**
   * @return the dummy862
   */
  public NAsmField getDummy862() {
    return dummy862;
  }

  /**
   * @return the dummy863
   */
  public NAsmField getDummy863() {
    return dummy863;
  }

  /**
   * @return the recfm
   */
  public NAsmField getRecfm() {
    return recfm;
  }

  /**
   * @return the dummy865
   */
  public NAsmField getDummy865() {
    return dummy865;
  }

  /**
   * @return the lrecl
   */
  public NAsmField getLrecl() {
    return lrecl;
  }

  /**
   * @return the dummy867
   */
  public NAsmField getDummy867() {
    return dummy867;
  }

  /**
   * @return the blksize
   */
  public NAsmField getBlksize() {
    return blksize;
  }

  /**
   * @return the dummy869
   */
  public NAsmField getDummy869() {
    return dummy869;
  }

  /**
   * @return the volser
   */
  public NAsmField getVolser() {
    return volser;
  }

  /**
   * @return the dummy871
   */
  public NAsmField getDummy871() {
    return dummy871;
  }

  /**
   *
   * @throws NAsmException
   */
  public Subtit1(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.SUBTIT1);
    dummy862.set(_SPACE(Len.DUMMY862));
    dummy863.set("* * RECFM  ");
    dummy865.set(", LRECL");
    dummy867.set(", BLKSIZE");
    dummy869.set(", VOLSER  ");
    dummy871.set(_SPACE(Len.DUMMY871));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY862 = 1;
    public static final int DUMMY863 = 11;
    public static final int RECFM = 2;
    public static final int DUMMY865 = 7;
    public static final int LRECL = 8;
    public static final int DUMMY867 = 9;
    public static final int BLKSIZE = 8;
    public static final int DUMMY869 = 10;
    public static final int VOLSER = 6;
    public static final int DUMMY871 = 71;

    public static final int SUBTIT1_SLOT = 133;
    public static final int SUBTIT1 = Len.DUMMY862
                                + Len.DUMMY863
                                + Len.RECFM
                                + Len.DUMMY865
                                + Len.LRECL
                                + Len.DUMMY867
                                + Len.BLKSIZE
                                + Len.DUMMY869
                                + Len.VOLSER
                                + Len.DUMMY871;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY862 = 0;
    public static final int DUMMY863 = Pos.DUMMY862 + Len.DUMMY862;
    public static final int RECFM = Pos.DUMMY863 + Len.DUMMY863;
    public static final int DUMMY865 = Pos.RECFM + Len.RECFM;
    public static final int LRECL = Pos.DUMMY865 + Len.DUMMY865;
    public static final int DUMMY867 = Pos.LRECL + Len.LRECL;
    public static final int BLKSIZE = Pos.DUMMY867 + Len.DUMMY867;
    public static final int DUMMY869 = Pos.BLKSIZE + Len.BLKSIZE;
    public static final int VOLSER = Pos.DUMMY869 + Len.DUMMY869;
    public static final int DUMMY871 = Pos.VOLSER + Len.VOLSER;

  }

}
