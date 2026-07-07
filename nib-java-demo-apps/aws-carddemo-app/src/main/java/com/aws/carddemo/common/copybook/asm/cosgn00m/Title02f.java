package com.aws.carddemo.common.copybook.asm.cosgn00m;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.storage.NAsmField;

/**
 * 
 * <b>Divergeek - ASM370 to JAVA.</b> <br/>
 * <br/>
 * 
 * <br/>
 * 
 * @version 2.2.8
 * 
 * @author divergeek
 */
public class Title02f extends NAsmField {

  // . DATA FIELD ATTRIBUTE
  private NAsmField title02a = new NAsmField(this, "TITLE02A", NAsmFieldType.ALPHANUMERIC, Pos.TITLE02A, Len.TITLE02A);
  // . COLOUR ATTRIBUTE
  private NAsmField title02c = new NAsmField(this, "TITLE02C", NAsmFieldType.ALPHANUMERIC, Pos.TITLE02C, Len.TITLE02C);
  // . PS ATTRIBUTE
  private NAsmField title02p = new NAsmField(this, "TITLE02P", NAsmFieldType.ALPHANUMERIC, Pos.TITLE02P, Len.TITLE02P);
  // . HIGHLIGHTING ATTRIBUTE
  private NAsmField title02h = new NAsmField(this, "TITLE02H", NAsmFieldType.ALPHANUMERIC, Pos.TITLE02H, Len.TITLE02H);
  // . VALIDATION ATTRIBUTE
  private NAsmField title02v = new NAsmField(this, "TITLE02V", NAsmFieldType.ALPHANUMERIC, Pos.TITLE02V, Len.TITLE02V);

  /**
   * @return the title02a
   */
  public NAsmField getTitle02a() {
    return title02a;
  }

  /**
   * @return the title02c
   */
  public NAsmField getTitle02c() {
    return title02c;
  }

  /**
   * @return the title02p
   */
  public NAsmField getTitle02p() {
    return title02p;
  }

  /**
   * @return the title02h
   */
  public NAsmField getTitle02h() {
    return title02h;
  }

  /**
   * @return the title02v
   */
  public NAsmField getTitle02v() {
    return title02v;
  }

  /**
   *
   * @throws NAsmException
   */
  public Title02f(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.TITLE02F);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int TITLE02A = 1;
    public static final int TITLE02C = 1;
    public static final int TITLE02P = 1;
    public static final int TITLE02H = 1;
    public static final int TITLE02V = 1;

    public static final int TITLE02F_SLOT = 1;
    public static final int TITLE02F = Len.TITLE02A
                                + Len.TITLE02C
                                + Len.TITLE02P
                                + Len.TITLE02H
                                + Len.TITLE02V;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int TITLE02A = 0;
    public static final int TITLE02C = Pos.TITLE02A + Len.TITLE02A;
    public static final int TITLE02P = Pos.TITLE02C + Len.TITLE02C;
    public static final int TITLE02H = Pos.TITLE02P + Len.TITLE02P;
    public static final int TITLE02V = Pos.TITLE02H + Len.TITLE02H;

  }

}
