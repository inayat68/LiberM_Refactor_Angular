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
public class Dummy765 extends NAsmField {

  private NAsmField dummy765001 = new NAsmField(this, "DUMMY765", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY765001, Len.DUMMY765001);
  private NAsmField dummy765002 = new NAsmField(this, "DUMMY765", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY765002, Len.DUMMY765002);
  private NAsmField dummy765003 = new NAsmField(this, "DUMMY765", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY765003, Len.DUMMY765003);

  /**
   * @return the dummy765001
   */
  public NAsmField getDummy765001() {
    return dummy765001;
  }

  /**
   * @return the dummy765002
   */
  public NAsmField getDummy765002() {
    return dummy765002;
  }

  /**
   * @return the dummy765003
   */
  public NAsmField getDummy765003() {
    return dummy765003;
  }

  /**
   *
   * @throws NAsmException
   */
  public Dummy765(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DUMMY765);
    dummy765001.set("LC2");
    dummy765002.set(_LOW(Len.DUMMY765002));
    dummy765003.set(268);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY765001 = 3;
    public static final int DUMMY765002 = 1;
    public static final int DUMMY765003 = 4;

    public static final int DUMMY765 = 8;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY765001 = 0;
    public static final int DUMMY765002 = Pos.DUMMY765001 + Len.DUMMY765001;
    public static final int DUMMY765003 = Pos.DUMMY765002 + Len.DUMMY765002;

  }

}
