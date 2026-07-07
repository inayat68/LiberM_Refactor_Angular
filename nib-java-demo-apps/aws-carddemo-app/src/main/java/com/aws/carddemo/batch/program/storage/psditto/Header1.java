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
public class Header1 extends NAsmField {

  private NAsmField dummy848 = new NAsmField(this, "DUMMY848", NAsmFieldType.ALPHANUMERIC, Pos.DUMMY848, Len.DUMMY848);
  private NAsmField program = new NAsmField(this, "PROGRAM", NAsmFieldType.ALPHANUMERIC, Pos.PROGRAM, Len.PROGRAM);
  private NAsmField hfill1 = new NAsmField(this, "HFILL1", NAsmFieldType.ALPHANUMERIC, Pos.HFILL1, Len.HFILL1);
  private NAsmField title = new NAsmField(this, "TITLE", NAsmFieldType.ALPHANUMERIC, Pos.TITLE, Len.TITLE);
  private NAsmField hfill2 = new NAsmField(this, "HFILL2", NAsmFieldType.ALPHANUMERIC, Pos.HFILL2, Len.HFILL2);
  private NAsmField page = new NAsmField(this, "PAGE", NAsmFieldType.ALPHANUMERIC, Pos.PAGE, Len.PAGE);
  private NAsmField pagecnt = new NAsmField(this, "PAGECNT", NAsmFieldType.ALPHANUMERIC, Pos.PAGECNT, Len.PAGECNT);
  private NAsmField hfill3 = new NAsmField(this, "HFILL3", NAsmFieldType.ALPHANUMERIC, Pos.HFILL3, Len.HFILL3);

  /**
   * @return the dummy848
   */
  public NAsmField getDummy848() {
    return dummy848;
  }

  /**
   * @return the program
   */
  public NAsmField getProgram() {
    return program;
  }

  /**
   * @return the hfill1
   */
  public NAsmField getHfill1() {
    return hfill1;
  }

  /**
   * @return the title
   */
  public NAsmField getTitle() {
    return title;
  }

  /**
   * @return the hfill2
   */
  public NAsmField getHfill2() {
    return hfill2;
  }

  /**
   * @return the page
   */
  public NAsmField getPage() {
    return page;
  }

  /**
   * @return the pagecnt
   */
  public NAsmField getPagecnt() {
    return pagecnt;
  }

  /**
   * @return the hfill3
   */
  public NAsmField getHfill3() {
    return hfill3;
  }

  /**
   *
   * @throws NAsmException
   */
  public Header1(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.HEADER1);
    dummy848.set("1");
    program.set(_PAD("    CMS/DITTO", Len.PROGRAM));
    hfill1.set(_SPACE(Len.HFILL1));
    title.set("E Q U I F A X");
    hfill2.set(_SPACE(Len.HFILL2));
    page.set("PAGE");
    pagecnt.set(_SPACE(Len.PAGECNT));
    hfill3.set(_SPACE(Len.HFILL3));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int DUMMY848 = 1;
    public static final int PROGRAM = 20;
    public static final int HFILL1 = 38;
    public static final int TITLE = 13;
    public static final int HFILL2 = 48;
    public static final int PAGE = 4;
    public static final int PAGECNT = 4;
    public static final int HFILL3 = 5;

    public static final int HEADER1_SLOT = 133;
    public static final int HEADER1 = Len.DUMMY848
                                + Len.PROGRAM
                                + Len.HFILL1
                                + Len.TITLE
                                + Len.HFILL2
                                + Len.PAGE
                                + Len.PAGECNT
                                + Len.HFILL3;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int DUMMY848 = 0;
    public static final int PROGRAM = Pos.DUMMY848 + Len.DUMMY848;
    public static final int HFILL1 = Pos.PROGRAM + Len.PROGRAM;
    public static final int TITLE = Pos.HFILL1 + Len.HFILL1;
    public static final int HFILL2 = Pos.TITLE + Len.TITLE;
    public static final int PAGE = Pos.HFILL2 + Len.HFILL2;
    public static final int PAGECNT = Pos.PAGE + Len.PAGE;
    public static final int HFILL3 = Pos.PAGECNT + Len.PAGECNT;

  }

}
