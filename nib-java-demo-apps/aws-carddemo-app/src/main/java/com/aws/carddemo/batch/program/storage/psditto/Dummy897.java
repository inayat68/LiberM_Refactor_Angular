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
public class Dummy897 extends NAsmField {

  private NAsmField recnum = new NAsmField(this, "RECNUM", NAsmFieldType.ALPHANUMERIC, Pos.RECNUM, Len.RECNUM);
  private NAsmField dummy899 = new NAsmField(this, "DUMMY899", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY899, Len.DUMMY899);
  private NAsmField recsize = new NAsmField(this, "RECSIZE", NAsmFieldType.ALPHANUMERIC, Pos.RECSIZE, Len.RECSIZE);

  /**
   * @return the recnum
   */
  public NAsmField getRecnum() {
    return recnum;
  }

  /**
   * @return the dummy899
   */
  public NAsmField getDummy899() {
    return dummy899;
  }

  /**
   * @return the recsize
   */
  public NAsmField getRecsize() {
    return recsize;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy897(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY897);
    recnum.set(_SPACE(Len.RECNUM));
    dummy899.set(_SPACE(Len.DUMMY899));
    recsize.set(_SPACE(Len.RECSIZE));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int RECNUM = 6;
    public static final int DUMMY899 = 4;
    public static final int RECSIZE = 8;

    public static final int DUMMY897 = 18;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int RECNUM = 0;
    public static final int DUMMY899 = Pos.RECNUM + Len.RECNUM;
    public static final int RECSIZE = Pos.DUMMY899 + Len.DUMMY899;

  }

}
