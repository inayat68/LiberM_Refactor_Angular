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
public class Dummy749 extends NAsmField {

  private NAsmField dummy749001 = new NAsmField(this, "DUMMY749", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY749001, Len.DUMMY749001);
  private NAsmField dummy749002 = new NAsmField(this, "DUMMY749", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY749002, Len.DUMMY749002);

  /**
   * @return the dummy749001
   */
  public NAsmField getDummy749001() {
    return dummy749001;
  }

  /**
   * @return the dummy749002
   */
  public NAsmField getDummy749002() {
    return dummy749002;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy749(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY749);
    dummy749001.set("MDB");
    dummy749002.set(_LOW(Len.DUMMY749002));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY749001 = 3;
    public static final int DUMMY749002 = 1;

    public static final int DUMMY749 = 4;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY749001 = 0;
    public static final int DUMMY749002 = Pos.DUMMY749001 + Len.DUMMY749001;

  }

}
