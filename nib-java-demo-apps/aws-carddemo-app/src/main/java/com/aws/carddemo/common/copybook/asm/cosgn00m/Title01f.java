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
public class Title01f extends NAsmField {

  // . DATA FIELD ATTRIBUTE
  private NAsmField title01a = new NAsmField(this, "TITLE01A", NAsmFieldType.ALPHANUMERIC, Pos.TITLE01A, Len.TITLE01A);
  // . COLOUR ATTRIBUTE
  private NAsmField title01c = new NAsmField(this, "TITLE01C", NAsmFieldType.ALPHANUMERIC, Pos.TITLE01C, Len.TITLE01C);
  // . PS ATTRIBUTE
  private NAsmField title01p = new NAsmField(this, "TITLE01P", NAsmFieldType.ALPHANUMERIC, Pos.TITLE01P, Len.TITLE01P);
  // . HIGHLIGHTING ATTRIBUTE
  private NAsmField title01h = new NAsmField(this, "TITLE01H", NAsmFieldType.ALPHANUMERIC, Pos.TITLE01H, Len.TITLE01H);
  // . VALIDATION ATTRIBUTE
  private NAsmField title01v = new NAsmField(this, "TITLE01V", NAsmFieldType.ALPHANUMERIC, Pos.TITLE01V, Len.TITLE01V);

  /**
   * @return the title01a
   */
  public NAsmField getTitle01a() {
    return title01a;
  }

  /**
   * @return the title01c
   */
  public NAsmField getTitle01c() {
    return title01c;
  }

  /**
   * @return the title01p
   */
  public NAsmField getTitle01p() {
    return title01p;
  }

  /**
   * @return the title01h
   */
  public NAsmField getTitle01h() {
    return title01h;
  }

  /**
   * @return the title01v
   */
  public NAsmField getTitle01v() {
    return title01v;
  }

  /**
   *
   * @throws NAsmException
   */
  public Title01f(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.TITLE01F);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int TITLE01A = 1;
    public static final int TITLE01C = 1;
    public static final int TITLE01P = 1;
    public static final int TITLE01H = 1;
    public static final int TITLE01V = 1;

    public static final int TITLE01F_SLOT = 1;
    public static final int TITLE01F = Len.TITLE01A
                                + Len.TITLE01C
                                + Len.TITLE01P
                                + Len.TITLE01H
                                + Len.TITLE01V;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int TITLE01A = 0;
    public static final int TITLE01C = Pos.TITLE01A + Len.TITLE01A;
    public static final int TITLE01P = Pos.TITLE01C + Len.TITLE01C;
    public static final int TITLE01H = Pos.TITLE01P + Len.TITLE01P;
    public static final int TITLE01V = Pos.TITLE01H + Len.TITLE01H;

  }

}
