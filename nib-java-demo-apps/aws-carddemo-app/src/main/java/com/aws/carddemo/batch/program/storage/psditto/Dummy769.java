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
public class Dummy769 extends NAsmField {

  private NAsmField dummy769001 = new NAsmField(this, "DUMMY769", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY769001, Len.DUMMY769001);
  private NAsmField dummy769002 = new NAsmField(this, "DUMMY769", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY769002, Len.DUMMY769002);
  private NAsmField dummy769003 = new NAsmField(this, "DUMMY769", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY769003, Len.DUMMY769003);

  /**
   * @return the dummy769001
   */
  public NAsmField getDummy769001() {
    return dummy769001;
  }

  /**
   * @return the dummy769002
   */
  public NAsmField getDummy769002() {
    return dummy769002;
  }

  /**
   * @return the dummy769003
   */
  public NAsmField getDummy769003() {
    return dummy769003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy769(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY769);
    dummy769001.set("DMS");
    dummy769002.set(_LOW(Len.DUMMY769002));
    dummy769003.set(276);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY769001 = 3;
    public static final int DUMMY769002 = 1;
    public static final int DUMMY769003 = 4;

    public static final int DUMMY769 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY769001 = 0;
    public static final int DUMMY769002 = Pos.DUMMY769001 + Len.DUMMY769001;
    public static final int DUMMY769003 = Pos.DUMMY769002 + Len.DUMMY769002;

  }

}
