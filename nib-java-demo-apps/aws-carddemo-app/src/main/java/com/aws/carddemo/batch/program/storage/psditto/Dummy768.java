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
public class Dummy768 extends NAsmField {

  private NAsmField dummy768001 = new NAsmField(this, "DUMMY768", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY768001, Len.DUMMY768001);
  private NAsmField dummy768002 = new NAsmField(this, "DUMMY768", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY768002, Len.DUMMY768002);
  private NAsmField dummy768003 = new NAsmField(this, "DUMMY768", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY768003, Len.DUMMY768003);

  /**
   * @return the dummy768001
   */
  public NAsmField getDummy768001() {
    return dummy768001;
  }

  /**
   * @return the dummy768002
   */
  public NAsmField getDummy768002() {
    return dummy768002;
  }

  /**
   * @return the dummy768003
   */
  public NAsmField getDummy768003() {
    return dummy768003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy768(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY768);
    dummy768001.set("ECM");
    dummy768002.set(_LOW(Len.DUMMY768002));
    dummy768003.set(274);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY768001 = 3;
    public static final int DUMMY768002 = 1;
    public static final int DUMMY768003 = 4;

    public static final int DUMMY768 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY768001 = 0;
    public static final int DUMMY768002 = Pos.DUMMY768001 + Len.DUMMY768001;
    public static final int DUMMY768003 = Pos.DUMMY768002 + Len.DUMMY768002;

  }

}
