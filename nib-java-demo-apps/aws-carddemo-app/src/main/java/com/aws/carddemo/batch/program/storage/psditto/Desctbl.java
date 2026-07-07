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
public class Desctbl extends NAsmField {

  private Dummy749 dummy749 = new Dummy749(this, "DUMMY749", Pos.DUMMY749);
  private Dummy750 dummy750 = new Dummy750(this, "DUMMY750", Pos.DUMMY750);
  private Dummy751 dummy751 = new Dummy751(this, "DUMMY751", Pos.DUMMY751);
  private Dummy752 dummy752 = new Dummy752(this, "DUMMY752", Pos.DUMMY752);
  private Dummy753 dummy753 = new Dummy753(this, "DUMMY753", Pos.DUMMY753);
  private Dummy754 dummy754 = new Dummy754(this, "DUMMY754", Pos.DUMMY754);
  private Dummy755 dummy755 = new Dummy755(this, "DUMMY755", Pos.DUMMY755);

  /**
   * @return the dummy749
   */
  public Dummy749 getDummy749() {
    return dummy749;
  }

  /**
   * @return the dummy750
   */
  public Dummy750 getDummy750() {
    return dummy750;
  }

  /**
   * @return the dummy751
   */
  public Dummy751 getDummy751() {
    return dummy751;
  }

  /**
   * @return the dummy752
   */
  public Dummy752 getDummy752() {
    return dummy752;
  }

  /**
   * @return the dummy753
   */
  public Dummy753 getDummy753() {
    return dummy753;
  }

  /**
   * @return the dummy754
   */
  public Dummy754 getDummy754() {
    return dummy754;
  }

  /**
   * @return the dummy755
   */
  public Dummy755 getDummy755() {
    return dummy755;
  }

  /**
   *
   * @throws NAsmException
   */
  public Desctbl(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.DESCTBL);
  }

  //
  //Fields LEN
  //
  public static class Len {


    public static final int DESCTBL_SLOT = 28;
    public static final int DESCTBL = Dummy749.Len.DUMMY749
                                + Dummy750.Len.DUMMY750
                                + Dummy751.Len.DUMMY751
                                + Dummy752.Len.DUMMY752
                                + Dummy753.Len.DUMMY753
                                + Dummy754.Len.DUMMY754
                                + Dummy755.Len.DUMMY755;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY749 = 0;
    public static final int DUMMY750 = Pos.DUMMY749 + Dummy749.Len.DUMMY749;
    public static final int DUMMY751 = Pos.DUMMY750 + Dummy750.Len.DUMMY750;
    public static final int DUMMY752 = Pos.DUMMY751 + Dummy751.Len.DUMMY751;
    public static final int DUMMY753 = Pos.DUMMY752 + Dummy752.Len.DUMMY752;
    public static final int DUMMY754 = Pos.DUMMY753 + Dummy753.Len.DUMMY753;
    public static final int DUMMY755 = Pos.DUMMY754 + Dummy754.Len.DUMMY754;

  }

}
