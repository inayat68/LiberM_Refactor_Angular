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
public class Cosgn00m extends NAsmField {

  // . START OF MAP DEFINITION
  private Cosgn0as cosgn0as = new Cosgn0as(this, "COSGN0AS", Pos.COSGN0AS);   
  private NAsmField cosgn0ai = new NAsmField(cosgn0as, "COSGN0AI", NAsmFieldType.ALPHANUMERIC, Pos.COSGN0AI, Cosgn0as.Len.COSGN0AS);
  private NAsmField cosgn0ao = new NAsmField(cosgn0as, "COSGN0AO", NAsmFieldType.ALPHANUMERIC, Pos.COSGN0AO, Cosgn0as.Len.COSGN0AS);

  /**
   * @return the cosgn0as
   */
  public Cosgn0as getCosgn0as() {
    return cosgn0as;
  }

  /**
   * @return the cosgn0ai
   */
  public NAsmField getCosgn0ai() {
    return cosgn0ai;
  }

  /**
   * @return the cosgn0ao
   */
  public NAsmField getCosgn0ao() {
    return cosgn0ao;
  }

  /**
   *
   * @throws NAsmException
   */
  public Cosgn00m(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.COSGN00M);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int COSGN00M = Cosgn0as.Len.COSGN0AS;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int COSGN0AS = 0;
    public static final int COSGN0AE = 0;
    public static final int COSGN0AI = 0;
    public static final int COSGN0AO = 0;

  }

}
