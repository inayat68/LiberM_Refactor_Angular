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
public class Dummy775 extends NAsmField {

  private NAsmField dummy775001 = new NAsmField(this, "DUMMY775", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY775001, Len.DUMMY775001);
  private NAsmField dummy775002 = new NAsmField(this, "DUMMY775", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY775002, Len.DUMMY775002);
  private NAsmField dummy775003 = new NAsmField(this, "DUMMY775", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY775003, Len.DUMMY775003);

  /**
   * @return the dummy775001
   */
  public NAsmField getDummy775001() {
    return dummy775001;
  }

  /**
   * @return the dummy775002
   */
  public NAsmField getDummy775002() {
    return dummy775002;
  }

  /**
   * @return the dummy775003
   */
  public NAsmField getDummy775003() {
    return dummy775003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy775(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY775);
    dummy775001.set("AF4");
    dummy775002.set(_LOW(Len.DUMMY775002));
    dummy775003.set(264);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY775001 = 3;
    public static final int DUMMY775002 = 1;
    public static final int DUMMY775003 = 4;

    public static final int DUMMY775 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY775001 = 0;
    public static final int DUMMY775002 = Pos.DUMMY775001 + Len.DUMMY775001;
    public static final int DUMMY775003 = Pos.DUMMY775002 + Len.DUMMY775002;

  }

}
