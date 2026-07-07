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
public class Dummy892 extends NAsmField {

  private NAsmField dummy893 = new NAsmField(this, "DUMMY893", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY893, Len.DUMMY893);
  private NAsmField segname = new NAsmField(this, "SEGNAME", NAsmFieldType.ALPHANUMERIC, Pos.SEGNAME, Len.SEGNAME);
  private NAsmField segsize = new NAsmField(this, "SEGSIZE", NAsmFieldType.ALPHANUMERIC, Pos.SEGSIZE, Len.SEGSIZE);

  /**
   * @return the dummy893
   */
  public NAsmField getDummy893() {
    return dummy893;
  }

  /**
   * @return the segname
   */
  public NAsmField getSegname() {
    return segname;
  }

  /**
   * @return the segsize
   */
  public NAsmField getSegsize() {
    return segsize;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy892(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY892);
    dummy893.set(_SPACE(Len.DUMMY893));
    segname.set(_SPACE(Len.SEGNAME));
    segsize.set(_SPACE(Len.SEGSIZE));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY893 = 7;
    public static final int SEGNAME = 3;
    public static final int SEGSIZE = 8;

    public static final int DUMMY892 = 18;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY893 = 0;
    public static final int SEGNAME = Pos.DUMMY893 + Len.DUMMY893;
    public static final int SEGSIZE = Pos.SEGNAME + Len.SEGNAME;

  }

}
