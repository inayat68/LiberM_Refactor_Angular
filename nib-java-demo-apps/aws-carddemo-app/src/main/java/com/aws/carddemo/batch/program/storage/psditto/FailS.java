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
public class FailS extends NAsmField {

  private NAsmField dummy702 = new NAsmField(this, "DUMMY702", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY702, Len.DUMMY702);
  private NAsmField dummy704 = new NAsmField(this, "DUMMY704", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY704, Len.DUMMY704);
  private NAsmField dummy705 = new NAsmField(this, "DUMMY705", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY705, Len.DUMMY705);
  private NAsmField dummy706 = new NAsmField(this, "DUMMY706", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY706, Len.DUMMY706);

  /**
   * @return the dummy702
   */
  public NAsmField getDummy702() {
    return dummy702;
  }

  /**
   * @return the dummy704
   */
  public NAsmField getDummy704() {
    return dummy704;
  }

  /**
   * @return the dummy705
   */
  public NAsmField getDummy705() {
    return dummy705;
  }

  /**
   * @return the dummy706
   */
  public NAsmField getDummy706() {
    return dummy706;
  }

  /**
   *
   * @throws NAsmException
   */
  public FailS(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.FAILS);
    dummy702.set(_PAD("PSDITTO - FORMAT WARNING.", Len.DUMMY702));
    dummy704.set(_PAD("UNABLE TO DITTO 1 OR MORE RECORDS IN OSCR SEGMENTED DITTO FORMAT!", Len.DUMMY704));
    dummy705.set(_SPACE(Len.DUMMY705));
    dummy706.set(_PAD("NO ABEND.      MODE=", Len.DUMMY706));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY702 = 75;
    public static final int DUMMY704 = 75;
    public static final int DUMMY705 = 75;
    public static final int DUMMY706 = 30;

    public static final int FAILS = Len.DUMMY702
                                + Len.DUMMY704
                                + Len.DUMMY705
                                + Len.DUMMY706;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY702 = 0;
    public static final int DUMMY704 = Pos.DUMMY702 + Len.DUMMY702;
    public static final int DUMMY705 = Pos.DUMMY704 + Len.DUMMY704;
    public static final int DUMMY706 = Pos.DUMMY705 + Len.DUMMY705;

  }

}
