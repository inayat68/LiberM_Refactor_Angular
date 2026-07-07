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
public class Lguide extends NAsmField {

  private NAsmField dummy951 = new NAsmField(this, "DUMMY951", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY951, Len.DUMMY951);
  private NAsmField lsegloc = new NAsmField(this, "LSEGLOC", NAsmFieldType.ALPHANUMERIC, Pos.LSEGLOC, Len.LSEGLOC);
  private NAsmField lposfld = new NAsmField(this, "LPOSFLD", NAsmFieldType.ALPHANUMERIC, Pos.LPOSFLD, Len.LPOSFLD);
  private NAsmField lguide2 = new NAsmField(this, "LGUIDE2", NAsmFieldType.ALPHANUMERIC, Pos.LGUIDE2, Len.LGUIDE2, Array.LGUIDE2);
  private NAsmField dummy955 = new NAsmField(this, "DUMMY955", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY955, Len.DUMMY955);

  /**
   * @return the dummy951
   */
  public NAsmField getDummy951() {
    return dummy951;
  }

  /**
   * @return the lsegloc
   */
  public NAsmField getLsegloc() {
    return lsegloc;
  }

  /**
   * @return the lposfld
   */
  public NAsmField getLposfld() {
    return lposfld;
  }

  /**
   * @return the lguide2
   */
  public NAsmField getLguide2() {
    return lguide2;
  }

  /**
   * @return the lguide2 at index
   * @throws NAsmException
   */
  public NAsmField getLguide2AtIndex(int index) throws NAsmException {
    return lguide2.getAtIndex(index);
  }

  /**
   * @return the dummy955
   */
  public NAsmField getDummy955() {
    return dummy955;
  }

  /**
   *
   * @throws NAsmException
   */
  public Lguide(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.LGUIDE);
    dummy951.set(_SPACE(Len.DUMMY951));
    lsegloc.set(_SPACE(Len.LSEGLOC));
    lposfld.set(_SPACE(Len.LPOSFLD));
    lguide2.set(_FILL('.', Len.LGUIDE2 * Array.LGUIDE2));
    dummy955.set(_SPACE(Len.DUMMY955));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY951 = 14;
    public static final int LSEGLOC = 8;
    public static final int LPOSFLD = 6;
    public static final int LGUIDE2 = 1;
    public static final int DUMMY955 = 5;

    public static final int LGUIDE_SLOT = 133;
    public static final int LGUIDE = Len.DUMMY951
                                + Len.LSEGLOC
                                + Len.LPOSFLD
                                + (Len.LGUIDE2 * Array.LGUIDE2)
                                + Len.DUMMY955;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY951 = 0;
    public static final int LSEGLOC = Pos.DUMMY951 + Len.DUMMY951;
    public static final int LPOSFLD = Pos.LSEGLOC + Len.LSEGLOC;
    public static final int LGUIDE2 = Pos.LPOSFLD + Len.LPOSFLD;
    public static final int DUMMY955 = Pos.LGUIDE2 + (Len.LGUIDE2 * Array.LGUIDE2);

  }

  //
  //Fields ARRAY
  //
  public static class Array {

    public static final int LGUIDE2 = 101;

  }

}
