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
public class PsdittoStorage extends NAsmField {

  //---------------------------------------------------------------------*
  // E R R O R   M E S S A G E S                                  *
  //---------------------------------------------------------------------*
  private User1S user1S = new User1S(this, "USER1W", Pos.USER1S);
  private User2S user2S = new User2S(this, "USER2W", Pos.USER2S);
  private User3S user3S = new User3S(this, "USER3W", Pos.USER3S);
  private User4S user4S = new User4S(this, "USER4W", Pos.USER4S);
  private User5S user5S = new User5S(this, "USER5W", Pos.USER5S);
  private FailS failS = new FailS(this, "FAILW", Pos.FAILS);
  // ******* DITTO OPERATION SWITCH ***************
  private NAsmField mode = new NAsmField(this, "MODE", NAsmFieldType.ALPHANUMERIC, Pos.MODE, Len.MODE);
  // EQU   B'00000001'                                        *
  // EQU   B'00000010'                                        *
  // EQU   B'00000100'                                        *
  // EQU   B'00001000'                                        *
  // EQU   B'00010000'                                        *
  // ******* DITTO OPERATION SWITCH ***************
  private NAsmField mode2 = new NAsmField(this, "MODE2", NAsmFieldType.ALPHANUMERIC, Pos.MODE2, Len.MODE2);
  // SEGMENT DESCP TBL (POM REVERSE ORDER)
  private Desctbl desctbl = new Desctbl(this, "DESCTBL", Pos.DESCTBL);
  // 280 SEGMENT DESCP TBL (POM ASCEND ORDER)
  private Desctbl2 desctbl2 = new Desctbl2(this, "DESCTBL2", Pos.DESCTBL2);
  private NAsmField hexzeros = new NAsmField(this, "HEXZEROS", NAsmFieldType.ALPHANUMERIC, Pos.HEXZEROS, Len.HEXZEROS, Array.HEXZEROS);
  private NAsmField lastline = new NAsmField(this, "LASTLINE", NAsmFieldType.ALPHANUMERIC, Pos.LASTLINE, Len.LASTLINE);
  private NAsmField lcrcards = new NAsmField(this, "LCRCARDS", NAsmFieldType.ALPHANUMERIC, Pos.LCRCARDS, Len.LCRCARDS);
  private NAsmField selhdr = new NAsmField(this, "SELHDR", NAsmFieldType.ALPHANUMERIC, Pos.SELHDR, Len.SELHDR);
  private NAsmField descsave = new NAsmField(this, "DESCSAVE", NAsmFieldType.ALPHANUMERIC, Pos.DESCSAVE, Len.DESCSAVE);
  private NAsmField edwork = new NAsmField(this, "EDWORK", NAsmFieldType.ALPHANUMERIC, Pos.EDWORK, Len.EDWORK);
  private NAsmField work12 = new NAsmField(this, "WORK12", NAsmFieldType.ALPHANUMERIC, Pos.WORK12, Len.WORK12);
  private NAsmField hdr280flg = new NAsmField(this, "HDR280FLG", NAsmFieldType.ALPHANUMERIC, Pos.HDR280FLG, Len.HDR280FLG);
  private NAsmField pkone = new NAsmField(this, "PKONE", NAsmFieldType.NUMERIC_PACKED, Pos.PKONE, Len.PKONE);
  private NAsmField pktwo = new NAsmField(this, "PKTWO", NAsmFieldType.NUMERIC_PACKED, Pos.PKTWO, Len.PKTWO);
  private NAsmField pkzero = new NAsmField(this, "PKZERO", NAsmFieldType.NUMERIC_PACKED, Pos.PKZERO, Len.PKZERO);
  private NAsmField numlcrf = new NAsmField(this, "NUMLCRF", NAsmFieldType.NUMERIC_PACKED, Pos.NUMLCRF, Len.NUMLCRF);
  private NAsmField guidectr = new NAsmField(this, "GUIDECTR", NAsmFieldType.NUMERIC_PACKED, Pos.GUIDECTR, Len.GUIDECTR);
  // GEN PURPOSE PAGE COUNTER
  private NAsmField pagectr = new NAsmField(this, "PAGECTR", NAsmFieldType.NUMERIC_PACKED, Pos.PAGECTR, Len.PAGECTR);
  // GEN PURPOSE LINE COUNTER
  private NAsmField linect = new NAsmField(this, "LINECT", NAsmFieldType.NUMERIC_PACKED, Pos.LINECT, Len.LINECT);
  // GEN PURPOSE RECORD COUNTER
  private NAsmField recct = new NAsmField(this, "RECCT", NAsmFieldType.NUMERIC_PACKED, Pos.RECCT, Len.RECCT);
  private NAsmField want = new NAsmField(this, "WANT", NAsmFieldType.NUMERIC_PACKED, Pos.WANT, Len.WANT);
  private NAsmField pack4 = new NAsmField(this, "PACK4", NAsmFieldType.NUMERIC_PACKED, Pos.PACK4, Len.PACK4);
  private NAsmField seglocst = new NAsmField(this, "SEGLOCST", NAsmFieldType.NUMERIC_PACKED, Pos.SEGLOCST, Len.SEGLOCST);
  private NAsmField lreclsav = new NAsmField(this, "LRECLSAV", NAsmFieldType.NUMERIC_BINARY, Pos.LRECLSAV, Len.LRECLSAV);
  private NAsmField reclen = new NAsmField(this, "RECLEN", NAsmFieldType.NUMERIC_BINARY, Pos.RECLEN, Len.RECLEN);
  private NAsmField save13f = new NAsmField(this, "SAVE13F", NAsmFieldType.NUMERIC_BINARY, Pos.SAVE13F, Len.SAVE13F, Array.SAVE13F);
  private NAsmField saver1 = new NAsmField(this, "SAVER1", NAsmFieldType.NUMERIC_BINARY, Pos.SAVER1, Len.SAVER1);
  private NAsmField saver2 = new NAsmField(this, "SAVER2", NAsmFieldType.NUMERIC_BINARY, Pos.SAVER2, Len.SAVER2);
  private NAsmField saver7 = new NAsmField(this, "SAVER7", NAsmFieldType.NUMERIC_BINARY, Pos.SAVER7, Len.SAVER7);
  private NAsmField saver9 = new NAsmField(this, "SAVER9", NAsmFieldType.NUMERIC_BINARY, Pos.SAVER9, Len.SAVER9);
  private NAsmField savprint = new NAsmField(this, "SAVPRINT", NAsmFieldType.NUMERIC_BINARY, Pos.SAVPRINT, Len.SAVPRINT);
  private NAsmField recaddr = new NAsmField(this, "RECADDR", NAsmFieldType.NUMERIC_BINARY, Pos.RECADDR, Len.RECADDR);
  private NAsmField lcraddr = new NAsmField(this, "LCRADDR", NAsmFieldType.NUMERIC_BINARY, Pos.LCRADDR, Len.LCRADDR);
  private NAsmField lcrwork = new NAsmField(this, "LCRWORK", NAsmFieldType.NUMERIC_BINARY, Pos.LCRWORK, Len.LCRWORK);
  private NAsmField wrkdbl = new NAsmField(this, "WRKDBL", NAsmFieldType.NUMERIC_PACKED, Pos.WRKDBL, Len.WRKDBL);
  private NAsmField dathold = new NAsmField(this, "DATHOLD", NAsmFieldType.NUMERIC_PACKED, Pos.DATHOLD, Len.DATHOLD);
  private NAsmField full = new NAsmField(this, "FULL", NAsmFieldType.NUMERIC_BINARY, Pos.FULL, Len.FULL);
  private NAsmField desctbl2Ptr = new NAsmField(this, "DESCTBL2-PTR", NAsmFieldType.NUMERIC_BINARY, Pos.DESCTBL2PTR, Len.DESCTBL2PTR);
  private NAsmField ptrn4 = new NAsmField(this, "PTRN4", NAsmFieldType.NUMERIC_BINARY, Pos.PTRN4, Len.PTRN4);
  private NAsmField zptrn4 = new NAsmField(this, "ZPTRN4", NAsmFieldType.NUMERIC_BINARY, Pos.ZPTRN4, Len.ZPTRN4);
  private NAsmField ptrn5 = new NAsmField(this, "PTRN5", NAsmFieldType.ALPHANUMERIC, Pos.PTRN5, Len.PTRN5);
  private NAsmField ptrn6 = new NAsmField(this, "PTRN6", NAsmFieldType.ALPHANUMERIC, Pos.PTRN6, Len.PTRN6);
  private NAsmField ptrn8 = new NAsmField(this, "PTRN8", NAsmFieldType.NUMERIC_BINARY, Pos.PTRN8, Len.PTRN8);
  private NAsmField ptrn10 = new NAsmField(this, "PTRN10", NAsmFieldType.ALPHANUMERIC, Pos.PTRN10, Len.PTRN10);
  private NAsmField cardin = new NAsmField(this, "CARDIN", NAsmFieldType.ALPHANUMERIC, Pos.CARDIN, Len.CARDIN);
  private NAsmField stat = new NAsmField(this, "STAT", NAsmFieldType.ALPHANUMERIC, Pos.STAT, Len.STAT);
  private NAsmField blanks = new NAsmField(this, "BLANKS", NAsmFieldType.ALPHANUMERIC, Pos.BLANKS, Len.BLANKS);
  private Exlst exlst = new Exlst(this, "EXLST", Pos.EXLST);
  private NAsmField dummy833 = new NAsmField(this, "DUMMY833", NAsmFieldType.NUMERIC_BINARY, Pos.DUMMY833, Len.DUMMY833);
  private NAsmField injfcb = new NAsmField(this, "INJFCB", NAsmFieldType.ALPHANUMERIC, Pos.INJFCB, Len.INJFCB);
  private Header1 header1 = new Header1(this, "HEADER1", Pos.HEADER1);
  private Subtit1 subtit1 = new Subtit1(this, "SUBTIT1", Pos.SUBTIT1);
  private Subtit2 subtit2 = new Subtit2(this, "SUBTIT2", Pos.SUBTIT2);
  private Charline charline = new Charline(this, "CHARLINE", Pos.CHARLINE);
  private Zoneline zoneline = new Zoneline(this, "ZONELINE", Pos.ZONELINE);
  private Numrline numrline = new Numrline(this, "NUMRLINE", Pos.NUMRLINE);
  private Guide guide = new Guide(this, "GUIDE", Pos.GUIDE);
  // SPECIAL LCR GUIDE LINE
  private Lguide lguide = new Lguide(this, "LGUIDE", Pos.LGUIDE);
  private Pspsline pspsline = new Pspsline(this, "PSPSLINE", Pos.PSPSLINE);
  private NAsmField startpos = new NAsmField(this, "STARTPOS", NAsmFieldType.NUMERIC_PACKED, Pos.STARTPOS, Len.STARTPOS);
  private NAsmField saveplen = new NAsmField(this, "SAVEPLEN", NAsmFieldType.NUMERIC_PACKED, Pos.SAVEPLEN, Len.SAVEPLEN);
  private NAsmField dubble = new NAsmField(this, "DUBBLE", NAsmFieldType.NUMERIC_PACKED, Pos.DUBBLE, Len.DUBBLE);
  private NAsmField saver6 = new NAsmField(this, "SAVER6", NAsmFieldType.NUMERIC_BINARY, Pos.SAVER6, Len.SAVER6);
  private NAsmField saver5x = new NAsmField(this, "SAVER5X", NAsmFieldType.NUMERIC_BINARY, Pos.SAVER5X, Len.SAVER5X);
  private NAsmField saver4x = new NAsmField(this, "SAVER4X", NAsmFieldType.NUMERIC_BINARY, Pos.SAVER4X, Len.SAVER4X);
  private NAsmField notnum = new NAsmField(this, "NOTNUM", NAsmFieldType.ALPHANUMERIC, Pos.NOTNUM, Len.NOTNUM, Array.NOTNUM);
  private Trans trans = new Trans(this, "TRANS", Pos.TRANS);
  private Stndchar stndchar = new Stndchar(this, "STNDCHAR", Pos.STNDCHAR);
  private NAsmField charwork = new NAsmField(this, "CHARWORK", NAsmFieldType.ALPHANUMERIC, Pos.CHARWORK, Len.CHARWORK);
  private NAsmField ldig = new NAsmField(this, "LDIG", NAsmFieldType.NUMERIC_BINARY, Pos.LDIG, Len.LDIG);
  private NAsmField digits = new NAsmField(this, "DIGITS", NAsmFieldType.NUMERIC_BINARY, Pos.DIGITS, Len.DIGITS);
  private NAsmField saver5 = new NAsmField(this, "SAVER5", NAsmFieldType.NUMERIC_BINARY, Pos.SAVER5, Len.SAVER5);
  private NAsmField wrkdbl2 = new NAsmField(this, "WRKDBL2", NAsmFieldType.NUMERIC_PACKED, Pos.WRKDBL2, Len.WRKDBL2);
  private Cmsditto cmsditto = new Cmsditto(this, "CMSDITTO", Pos.CMSDITTO);
  //---------------------------------------------------------------------*
  // WORKING STORAGE FOR SETUP SUB-PGM
  //---------------------------------------------------------------------*
  private NAsmField lcrhead = new NAsmField(this, "LCRHEAD", NAsmFieldType.ALPHANUMERIC, Pos.LCRHEAD, Len.LCRHEAD);

  /**
   * @return the user1S
   */
  public User1S getUser1S() {
    return user1S;
  }

  /**
   * @return the user2S
   */
  public User2S getUser2S() {
    return user2S;
  }

  /**
   * @return the user3S
   */
  public User3S getUser3S() {
    return user3S;
  }

  /**
   * @return the user4S
   */
  public User4S getUser4S() {
    return user4S;
  }

  /**
   * @return the user5S
   */
  public User5S getUser5S() {
    return user5S;
  }

  /**
   * @return the failS
   */
  public FailS getFailS() {
    return failS;
  }

  /**
   * @return the mode
   */
  public NAsmField getMode() {
    return mode;
  }

  /**
   * @return the mode2
   */
  public NAsmField getMode2() {
    return mode2;
  }

  /**
   * @return the desctbl
   */
  public Desctbl getDesctbl() {
    return desctbl;
  }

  /**
   * @return the desctbl2
   */
  public Desctbl2 getDesctbl2() {
    return desctbl2;
  }

  /**
   * @return the hexzeros
   */
  public NAsmField getHexzeros() {
    return hexzeros;
  }

  /**
   * @return the hexzeros at index
   * @throws NAsmException
   */
  public NAsmField getHexzerosAtIndex(int index) throws NAsmException {
    return hexzeros.getAtIndex(index);
  }

  /**
   * @return the lastline
   */
  public NAsmField getLastline() {
    return lastline;
  }

  /**
   * @return the lcrcards
   */
  public NAsmField getLcrcards() {
    return lcrcards;
  }

  /**
   * @return the selhdr
   */
  public NAsmField getSelhdr() {
    return selhdr;
  }

  /**
   * @return the descsave
   */
  public NAsmField getDescsave() {
    return descsave;
  }

  /**
   * @return the edwork
   */
  public NAsmField getEdwork() {
    return edwork;
  }

  /**
   * @return the work12
   */
  public NAsmField getWork12() {
    return work12;
  }

  /**
   * @return the hdr280flg
   */
  public NAsmField getHdr280flg() {
    return hdr280flg;
  }

  /**
   * @return the pkone
   */
  public NAsmField getPkone() {
    return pkone;
  }

  /**
   * @return the pktwo
   */
  public NAsmField getPktwo() {
    return pktwo;
  }

  /**
   * @return the pkzero
   */
  public NAsmField getPkzero() {
    return pkzero;
  }

  /**
   * @return the numlcrf
   */
  public NAsmField getNumlcrf() {
    return numlcrf;
  }

  /**
   * @return the guidectr
   */
  public NAsmField getGuidectr() {
    return guidectr;
  }

  /**
   * @return the pagectr
   */
  public NAsmField getPagectr() {
    return pagectr;
  }

  /**
   * @return the linect
   */
  public NAsmField getLinect() {
    return linect;
  }

  /**
   * @return the recct
   */
  public NAsmField getRecct() {
    return recct;
  }

  /**
   * @return the want
   */
  public NAsmField getWant() {
    return want;
  }

  /**
   * @return the pack4
   */
  public NAsmField getPack4() {
    return pack4;
  }

  /**
   * @return the seglocst
   */
  public NAsmField getSeglocst() {
    return seglocst;
  }

  /**
   * @return the lreclsav
   */
  public NAsmField getLreclsav() {
    return lreclsav;
  }

  /**
   * @return the reclen
   */
  public NAsmField getReclen() {
    return reclen;
  }

  /**
   * @return the save13f
   */
  public NAsmField getSave13f() {
    return save13f;
  }

  /**
   * @return the save13f at index
   * @throws NAsmException
   */
  public NAsmField getSave13fAtIndex(int index) throws NAsmException {
    return save13f.getAtIndex(index);
  }

  /**
   * @return the saver1
   */
  public NAsmField getSaver1() {
    return saver1;
  }

  /**
   * @return the saver2
   */
  public NAsmField getSaver2() {
    return saver2;
  }

  /**
   * @return the saver7
   */
  public NAsmField getSaver7() {
    return saver7;
  }

  /**
   * @return the saver9
   */
  public NAsmField getSaver9() {
    return saver9;
  }

  /**
   * @return the savprint
   */
  public NAsmField getSavprint() {
    return savprint;
  }

  /**
   * @return the recaddr
   */
  public NAsmField getRecaddr() {
    return recaddr;
  }

  /**
   * @return the lcraddr
   */
  public NAsmField getLcraddr() {
    return lcraddr;
  }

  /**
   * @return the lcrwork
   */
  public NAsmField getLcrwork() {
    return lcrwork;
  }

  /**
   * @return the wrkdbl
   */
  public NAsmField getWrkdbl() {
    return wrkdbl;
  }

  /**
   * @return the dathold
   */
  public NAsmField getDathold() {
    return dathold;
  }

  /**
   * @return the full
   */
  public NAsmField getFull() {
    return full;
  }

  /**
   * @return the desctbl2Ptr
   */
  public NAsmField getDesctbl2Ptr() {
    return desctbl2Ptr;
  }

  /**
   * @return the ptrn4
   */
  public NAsmField getPtrn4() {
    return ptrn4;
  }

  /**
   * @return the zptrn4
   */
  public NAsmField getZptrn4() {
    return zptrn4;
  }

  /**
   * @return the ptrn5
   */
  public NAsmField getPtrn5() {
    return ptrn5;
  }

  /**
   * @return the ptrn6
   */
  public NAsmField getPtrn6() {
    return ptrn6;
  }

  /**
   * @return the ptrn8
   */
  public NAsmField getPtrn8() {
    return ptrn8;
  }

  /**
   * @return the ptrn10
   */
  public NAsmField getPtrn10() {
    return ptrn10;
  }

  /**
   * @return the cardin
   */
  public NAsmField getCardin() {
    return cardin;
  }

  /**
   * @return the stat
   */
  public NAsmField getStat() {
    return stat;
  }

  /**
   * @return the blanks
   */
  public NAsmField getBlanks() {
    return blanks;
  }

  /**
   * @return the exlst
   */
  public Exlst getExlst() {
    return exlst;
  }

  /**
   * @return the dummy833
   */
  public NAsmField getDummy833() {
    return dummy833;
  }

  /**
   * @return the injfcb
   */
  public NAsmField getInjfcb() {
    return injfcb;
  }

  /**
   * @return the header1
   */
  public Header1 getHeader1() {
    return header1;
  }

  /**
   * @return the subtit1
   */
  public Subtit1 getSubtit1() {
    return subtit1;
  }

  /**
   * @return the subtit2
   */
  public Subtit2 getSubtit2() {
    return subtit2;
  }

  /**
   * @return the charline
   */
  public Charline getCharline() {
    return charline;
  }

  /**
   * @return the zoneline
   */
  public Zoneline getZoneline() {
    return zoneline;
  }

  /**
   * @return the numrline
   */
  public Numrline getNumrline() {
    return numrline;
  }

  /**
   * @return the guide
   */
  public Guide getGuide() {
    return guide;
  }

  /**
   * @return the lguide
   */
  public Lguide getLguide() {
    return lguide;
  }

  /**
   * @return the pspsline
   */
  public Pspsline getPspsline() {
    return pspsline;
  }

  /**
   * @return the startpos
   */
  public NAsmField getStartpos() {
    return startpos;
  }

  /**
   * @return the saveplen
   */
  public NAsmField getSaveplen() {
    return saveplen;
  }

  /**
   * @return the dubble
   */
  public NAsmField getDubble() {
    return dubble;
  }

  /**
   * @return the saver6
   */
  public NAsmField getSaver6() {
    return saver6;
  }

  /**
   * @return the saver5x
   */
  public NAsmField getSaver5x() {
    return saver5x;
  }

  /**
   * @return the saver4x
   */
  public NAsmField getSaver4x() {
    return saver4x;
  }

  /**
   * @return the notnum
   */
  public NAsmField getNotnum() {
    return notnum;
  }

  /**
   * @return the notnum at index
   * @throws NAsmException
   */
  public NAsmField getNotnumAtIndex(int index) throws NAsmException {
    return notnum.getAtIndex(index);
  }

  /**
   * @return the trans
   */
  public Trans getTrans() {
    return trans;
  }

  /**
   * @return the stndchar
   */
  public Stndchar getStndchar() {
    return stndchar;
  }

  /**
   * @return the charwork
   */
  public NAsmField getCharwork() {
    return charwork;
  }

  /**
   * @return the ldig
   */
  public NAsmField getLdig() {
    return ldig;
  }

  /**
   * @return the digits
   */
  public NAsmField getDigits() {
    return digits;
  }

  /**
   * @return the saver5
   */
  public NAsmField getSaver5() {
    return saver5;
  }

  /**
   * @return the wrkdbl2
   */
  public NAsmField getWrkdbl2() {
    return wrkdbl2;
  }

  /**
   * @return the cmsditto
   */
  public Cmsditto getCmsditto() {
    return cmsditto;
  }

  /**
   * @return the lcrhead
   */
  public NAsmField getLcrhead() {
    return lcrhead;
  }

  /**
   *
   * @throws NAsmException
   */
  public PsdittoStorage() throws NAsmException {
    super("STORAGE", NAsmFieldType.ALPHANUMERIC, Pos.STORAGE, Len.STORAGE);
    mode.set(_LOW(Len.MODE));
    mode2.setHexValue("0x10");
    hexzeros.set(_LOW(Len.HEXZEROS * Array.HEXZEROS));
    lastline.set(_LOW(Len.LASTLINE));
    lcrcards.set(_LOW(Len.LCRCARDS));
    selhdr.set(_LOW(Len.SELHDR));
    pkone.set(1);
    pktwo.set(2);
    pkzero.set(0);
    guidectr.set(0);
    pagectr.set(0);
    linect.set(60);
    recct.set(0);
    want.set(0);
    ptrn4.setHexValue("0x40202021");
    zptrn4.setHexValue("0xf0202020");
    ptrn5.setHexValue("0x4020202021");
    ptrn6.setHexValue("0x402020202021");
    ptrn8.setHexValue("0x4020202020202021");
    ptrn10.setHexValue("0x40202020202020202120");
    cardin.set(_SPACE(Len.CARDIN));
    stat.set(_SPACE(Len.STAT));
    blanks.set(_SPACE(Len.BLANKS));
    dummy833.setHexValue("0x80000000");
    startpos.set(0);
    saveplen.set(0);
    notnum.setByteString(_HIGH(Len.NOTNUM * Array.NOTNUM));
    charwork.set(_SPACE(Len.CHARWORK));
    ldig.set(0);
    digits.set(0);
    lcrhead.set("LISTING OF INSTREAM CARDS FOR LCRFLDS DD STATEMENT");
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int MODE = 1;
    public static final int MODE2 = 1;
    public static final int HEXZEROS = 1;
    public static final int LASTLINE = 1;
    public static final int LCRCARDS = 1;
    public static final int SELHDR = 1;
    public static final int DESCSAVE = 3;
    public static final int EDWORK = 4;
    public static final int WORK12 = 12;
    public static final int HDR280FLG = 1;
    public static final int PKONE = 1;
    public static final int PKTWO = 1;
    public static final int PKZERO = 1;
    public static final int NUMLCRF = 2;
    public static final int GUIDECTR = 2;
    public static final int PAGECTR = 2;
    public static final int LINECT = 2;
    public static final int RECCT = 3;
    public static final int WANT = 3;
    public static final int PACK4 = 4;
    public static final int SEGLOCST = 4;
    public static final int LRECLSAV = 2;
    public static final int RECLEN = 2;
    public static final int SAVE13F = 4;
    public static final int SAVER1 = 4;
    public static final int SAVER2 = 4;
    public static final int SAVER7 = 4;
    public static final int SAVER9 = 4;
    public static final int SAVPRINT = 4;
    public static final int RECADDR = 4;
    public static final int LCRADDR = 4;
    public static final int LCRWORK = 4;
    public static final int WRKDBL = 8;
    public static final int DATHOLD = 8;
    public static final int FULL = 4;
    public static final int DESCTBL2PTR = 4;
    public static final int PTRN4 = 4;
    public static final int ZPTRN4 = 4;
    public static final int PTRN5 = 5;
    public static final int PTRN6 = 6;
    public static final int PTRN8 = 8;
    public static final int PTRN10 = 10;
    public static final int CARDIN = 80;
    public static final int STAT = 133;
    public static final int BLANKS = 133;
    public static final int DUMMY833 = 4;
    public static final int INJFCB = 176;
    public static final int STARTPOS = 3;
    public static final int SAVEPLEN = 3;
    public static final int DUBBLE = 8;
    public static final int SAVER6 = 4;
    public static final int SAVER5X = 4;
    public static final int SAVER4X = 4;
    public static final int NOTNUM = 1;
    public static final int CHARWORK = 4;
    public static final int LDIG = 2;
    public static final int DIGITS = 2;
    public static final int SAVER5 = 4;
    public static final int WRKDBL2 = 8;
    public static final int LCRHEAD = 50;

    public static final int STORAGE = User1S.Len.USER1S
                                + User2S.Len.USER2S
                                + User3S.Len.USER3S
                                + User4S.Len.USER4S
                                + User5S.Len.USER5S
                                + FailS.Len.FAILS
                                + Len.MODE
                                + Len.MODE2
                                + Desctbl.Len.DESCTBL
                                + Desctbl2.Len.DESCTBL2
                                + (Len.HEXZEROS * Array.HEXZEROS)
                                + Len.LASTLINE
                                + Len.LCRCARDS
                                + Len.SELHDR
                                + Len.DESCSAVE
                                + Len.EDWORK
                                + Len.WORK12
                                + Len.HDR280FLG
                                + Len.PKONE
                                + Len.PKTWO
                                + Len.PKZERO
                                + Len.NUMLCRF
                                + Len.GUIDECTR
                                + Len.PAGECTR
                                + Len.LINECT
                                + Len.RECCT
                                + Len.WANT
                                + Len.PACK4
                                + Len.SEGLOCST
                                + Len.LRECLSAV
                                + Len.RECLEN
                                + (Len.SAVE13F * Array.SAVE13F)
                                + Len.SAVER1
                                + Len.SAVER2
                                + Len.SAVER7
                                + Len.SAVER9
                                + Len.SAVPRINT
                                + Len.RECADDR
                                + Len.LCRADDR
                                + Len.LCRWORK
                                + Len.WRKDBL
                                + Len.DATHOLD
                                + Len.FULL
                                + Len.DESCTBL2PTR
                                + Len.PTRN4
                                + Len.ZPTRN4
                                + Len.PTRN5
                                + Len.PTRN6
                                + Len.PTRN8
                                + Len.PTRN10
                                + Len.CARDIN
                                + Len.STAT
                                + Len.BLANKS
                                + Exlst.Len.EXLST
                                + Len.DUMMY833
                                + Len.INJFCB
                                + Header1.Len.HEADER1
                                + Subtit1.Len.SUBTIT1
                                + Subtit2.Len.SUBTIT2
                                + Charline.Len.CHARLINE
                                + Zoneline.Len.ZONELINE
                                + Numrline.Len.NUMRLINE
                                + Guide.Len.GUIDE
                                + Lguide.Len.LGUIDE
                                + Pspsline.Len.PSPSLINE
                                + Len.STARTPOS
                                + Len.SAVEPLEN
                                + Len.DUBBLE
                                + Len.SAVER6
                                + Len.SAVER5X
                                + Len.SAVER4X
                                + (Len.NOTNUM * Array.NOTNUM)
                                + Trans.Len.TRANS
                                + Stndchar.Len.STNDCHAR
                                + Len.CHARWORK
                                + Len.LDIG
                                + Len.DIGITS
                                + Len.SAVER5
                                + Len.WRKDBL2
                                + Cmsditto.Len.CMSDITTO
                                + Len.LCRHEAD;

  }

  //
  //Fields POS
  //
  public static class Pos {

    // ASM_POS: 0
    public static final int STORAGE = 0;
    // ASM_POS: 0
    public static final int USER1S = 0;
    // ASM_POS: 255
    public static final int USER2S = Pos.USER1S + User1S.Len.USER1S;
    // ASM_POS: 510
    public static final int USER3S = Pos.USER2S + User2S.Len.USER2S;
    // ASM_POS: 765
    public static final int USER4S = Pos.USER3S + User3S.Len.USER3S;
    // ASM_POS: 1020
    public static final int USER5S = Pos.USER4S + User4S.Len.USER4S;
    // ASM_POS: 1275
    public static final int FAILS = Pos.USER5S + User5S.Len.USER5S;
    // ASM_POS: 1530
    public static final int MODE = Pos.FAILS + FailS.Len.FAILS;
    // ASM_POS: 1531
    public static final int MODE2 = Pos.MODE + Len.MODE;
    // ASM_POS: 1532
    public static final int DESCTBL = Pos.MODE2 + Len.MODE2;
    // ASM_POS: 1560
    public static final int DESCTBL2 = Pos.DESCTBL + Desctbl.Len.DESCTBL;
    // ASM_POS: 1712
    public static final int HEXZEROS = Pos.DESCTBL2 + Desctbl2.Len.DESCTBL2;
    // ASM_POS: 1716
    public static final int LASTLINE = Pos.HEXZEROS + (Len.HEXZEROS * Array.HEXZEROS);
    // ASM_POS: 1717
    public static final int LCRCARDS = Pos.LASTLINE + Len.LASTLINE;
    // ASM_POS: 1718
    public static final int SELHDR = Pos.LCRCARDS + Len.LCRCARDS;
    // ASM_POS: 1719
    public static final int DESCSAVE = Pos.SELHDR + Len.SELHDR;
    // ASM_POS: 1722
    public static final int EDWORK = Pos.DESCSAVE + Len.DESCSAVE;
    // ASM_POS: 1726
    public static final int WORK12 = Pos.EDWORK + Len.EDWORK;
    // ASM_POS: 1738
    public static final int HDR280FLG = Pos.WORK12 + Len.WORK12;
    // ASM_POS: 1739
    public static final int PKONE = Pos.HDR280FLG + Len.HDR280FLG;
    // ASM_POS: 1740
    public static final int PKTWO = Pos.PKONE + Len.PKONE;
    // ASM_POS: 1741
    public static final int PKZERO = Pos.PKTWO + Len.PKTWO;
    // ASM_POS: 1742
    public static final int NUMLCRF = Pos.PKZERO + Len.PKZERO;
    // ASM_POS: 1744
    public static final int GUIDECTR = Pos.NUMLCRF + Len.NUMLCRF;
    // ASM_POS: 1746
    public static final int PAGECTR = Pos.GUIDECTR + Len.GUIDECTR;
    // ASM_POS: 1748
    public static final int LINECT = Pos.PAGECTR + Len.PAGECTR;
    // ASM_POS: 1750
    public static final int RECCT = Pos.LINECT + Len.LINECT;
    // ASM_POS: 1753
    public static final int WANT = Pos.RECCT + Len.RECCT;
    // ASM_POS: 1756
    public static final int PACK4 = Pos.WANT + Len.WANT;
    // ASM_POS: 1760
    public static final int SEGLOCST = Pos.PACK4 + Len.PACK4;
    // ASM_POS: 1764
    public static final int LRECLSAV = Pos.SEGLOCST + Len.SEGLOCST;
    // ASM_POS: 1766
    public static final int RECLEN = Pos.LRECLSAV + Len.LRECLSAV;
    // ASM_POS: 1768
    public static final int SAVE13F = Pos.RECLEN + Len.RECLEN;
    // ASM_POS: 1820
    public static final int SAVER1 = Pos.SAVE13F + (Len.SAVE13F * Array.SAVE13F);
    // ASM_POS: 1824
    public static final int SAVER2 = Pos.SAVER1 + Len.SAVER1;
    // ASM_POS: 1828
    public static final int SAVER7 = Pos.SAVER2 + Len.SAVER2;
    // ASM_POS: 1832
    public static final int SAVER9 = Pos.SAVER7 + Len.SAVER7;
    // ASM_POS: 1836
    public static final int SAVPRINT = Pos.SAVER9 + Len.SAVER9;
    // ASM_POS: 1840
    public static final int RECADDR = Pos.SAVPRINT + Len.SAVPRINT;
    // ASM_POS: 1844
    public static final int LCRADDR = Pos.RECADDR + Len.RECADDR;
    // ASM_POS: 1848
    public static final int LCRWORK = Pos.LCRADDR + Len.LCRADDR;
    // ASM_POS: 1856
    public static final int WRKDBL = Pos.LCRWORK + Len.LCRWORK;
    // ASM_POS: 1864
    public static final int DATHOLD = Pos.WRKDBL + Len.WRKDBL;
    // ASM_POS: 1872
    public static final int FULL = Pos.DATHOLD + Len.DATHOLD;
    // ASM_POS: 1876
    public static final int DESCTBL2PTR = Pos.FULL + Len.FULL;
    // ASM_POS: 1880
    public static final int PTRN4 = Pos.DESCTBL2PTR + Len.DESCTBL2PTR;
    // ASM_POS: 1884
    public static final int ZPTRN4 = Pos.PTRN4 + Len.PTRN4;
    // ASM_POS: 1888
    public static final int PTRN5 = Pos.ZPTRN4 + Len.ZPTRN4;
    // ASM_POS: 1893
    public static final int PTRN6 = Pos.PTRN5 + Len.PTRN5;
    // ASM_POS: 1899
    public static final int PTRN8 = Pos.PTRN6 + Len.PTRN6;
    // ASM_POS: 1907
    public static final int PTRN10 = Pos.PTRN8 + Len.PTRN8;
    // ASM_POS: 1917
    public static final int CARDIN = Pos.PTRN10 + Len.PTRN10;
    // ASM_POS: 1997
    public static final int STAT = Pos.CARDIN + Len.CARDIN;
    // ASM_POS: 2130
    public static final int BLANKS = Pos.STAT + Len.STAT;
    // ASM_POS: 2264
    public static final int EXLST = Pos.BLANKS + Len.BLANKS;
    // ASM_POS: 2268
    public static final int DUMMY833 = Pos.EXLST + Exlst.Len.EXLST;
    // ASM_POS: 2272
    public static final int INJFCB = Pos.DUMMY833 + Len.DUMMY833;
    // ASM_POS: 2448
    public static final int HEADER1 = Pos.INJFCB + Len.INJFCB;
    // ASM_POS: 2581
    public static final int SUBTIT1 = Pos.HEADER1 + Header1.Len.HEADER1;
    // ASM_POS: 2714
    public static final int SUBTIT2 = Pos.SUBTIT1 + Subtit1.Len.SUBTIT1;
    // ASM_POS: 2847
    public static final int CHARLINE = Pos.SUBTIT2 + Subtit2.Len.SUBTIT2;
    // ASM_POS: 2980
    public static final int ZONELINE = Pos.CHARLINE + Charline.Len.CHARLINE;
    // ASM_POS: 3113
    public static final int NUMRLINE = Pos.ZONELINE + Zoneline.Len.ZONELINE;
    // ASM_POS: 3246
    public static final int GUIDE = Pos.NUMRLINE + Numrline.Len.NUMRLINE;
    // ASM_POS: 3379
    public static final int LGUIDE = Pos.GUIDE + Guide.Len.GUIDE;
    // ASM_POS: 3512
    public static final int PSPSLINE = Pos.LGUIDE + Lguide.Len.LGUIDE;
    // ASM_POS: 3646
    public static final int STARTPOS = Pos.PSPSLINE + Pspsline.Len.PSPSLINE;
    // ASM_POS: 3649
    public static final int SAVEPLEN = Pos.STARTPOS + Len.STARTPOS;
    // ASM_POS: 3652
    public static final int DUBBLE = Pos.SAVEPLEN + Len.SAVEPLEN;
    // ASM_POS: 3656
    public static final int SAVER6 = Pos.DUBBLE + Len.DUBBLE;
    // ASM_POS: 3668
    public static final int SAVER5X = Pos.SAVER6 + Len.SAVER6;
    // ASM_POS: 3672
    public static final int SAVER4X = Pos.SAVER5X + Len.SAVER5X;
    // ASM_POS: 3676
    public static final int NOTNUM = Pos.SAVER4X + Len.SAVER4X;
    // ASM_POS: 3932
    public static final int TRANS = Pos.NOTNUM + (Len.NOTNUM * Array.NOTNUM);
    // ASM_POS: 4188
    public static final int STNDCHAR = Pos.TRANS + Trans.Len.TRANS;
    // ASM_POS: 4444
    public static final int CHARWORK = Pos.STNDCHAR + Stndchar.Len.STNDCHAR;
    // ASM_POS: 4448
    public static final int LDIG = Pos.CHARWORK + Len.CHARWORK;
    // ASM_POS: 4450
    public static final int DIGITS = Pos.LDIG + Len.LDIG;
    // ASM_POS: 4452
    public static final int SAVER5 = Pos.DIGITS + Len.DIGITS;
    // ASM_POS: 4456
    public static final int WRKDBL2 = Pos.SAVER5 + Len.SAVER5;
    // ASM_POS: 4464
    public static final int CMSDITTO = Pos.WRKDBL2 + Len.WRKDBL2;
    // ASM_POS: 5988
    public static final int LCRHEAD = Pos.CMSDITTO + Cmsditto.Len.CMSDITTO;

  }

  //
  //Fields ARRAY
  //
  public static class Array {

    public static final int HEXZEROS = 4;
    public static final int SAVE13F = 13;
    public static final int NOTNUM = 256;

  }

}
