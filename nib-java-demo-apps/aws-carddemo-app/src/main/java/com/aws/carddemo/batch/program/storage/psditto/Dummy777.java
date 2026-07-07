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
public class Dummy777 extends NAsmField {

  private NAsmField dummy777001 = new NAsmField(this, "DUMMY777", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY777001, Len.DUMMY777001);
  private NAsmField dummy777002 = new NAsmField(this, "DUMMY777", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY777002, Len.DUMMY777002);
  private NAsmField dummy777003 = new NAsmField(this, "DUMMY777", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY777003, Len.DUMMY777003);

  /**
   * @return the dummy777001
   */
  public NAsmField getDummy777001() {
    return dummy777001;
  }

  /**
   * @return the dummy777002
   */
  public NAsmField getDummy777002() {
    return dummy777002;
  }

  /**
   * @return the dummy777003
   */
  public NAsmField getDummy777003() {
    return dummy777003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy777(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY777);
    dummy777001.set("MDB");
    dummy777002.set(_LOW(Len.DUMMY777002));
    dummy777003.set(192);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY777001 = 3;
    public static final int DUMMY777002 = 1;
    public static final int DUMMY777003 = 4;

    public static final int DUMMY777 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY777001 = 0;
    public static final int DUMMY777002 = Pos.DUMMY777001 + Len.DUMMY777001;
    public static final int DUMMY777003 = Pos.DUMMY777002 + Len.DUMMY777002;

  }

}
