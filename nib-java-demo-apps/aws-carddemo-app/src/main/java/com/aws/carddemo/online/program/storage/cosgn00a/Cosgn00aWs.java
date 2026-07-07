package com.aws.carddemo.online.program.storage.cosgn00a;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.storage.NAsmField;

import com.aws.carddemo.common.copybook.asm.cosgn00m.*;

/**
 * 
 * <b>Divergeek - ASM370 to JAVA.</b> <br/>
 * <br/>
 * 
 * <br/>
 * 
 * @version 2.2.8
 * 
 * @author divergeek
 */
public class Cosgn00aWs extends NAsmField {

  // ENSURE ALIGNMENT
  private Cosgn00m cosgn00m = new Cosgn00m(this, "COSGN00M", Pos.COSGN00M);
  private WsVariables wsVariables = new WsVariables(this, "WS_VARIABLES", Pos.WS_VARIABLES);   
  //      COPY COCOM01Y
  private NAsmField cdCommarea = new NAsmField(this, "CD_COMMAREA", NAsmFieldType.ALPHANUMERIC, Pos.CD_COMMAREA, Len.CD_COMMAREA_SLOT);
  private CdGeneInfo cdGeneInfo = new CdGeneInfo(this, "CD_GENE_INFO", Pos.CD_GENE_INFO);   
  private NAsmField cdCustInfo = new NAsmField(this, "CD_CUST_INFO", NAsmFieldType.ALPHANUMERIC, Pos.CD_CUST_INFO, Len.CD_CUST_INFO);
  private NAsmField cdAcctInfo = new NAsmField(this, "CD_ACCT_INFO", NAsmFieldType.ALPHANUMERIC, Pos.CD_ACCT_INFO, Len.CD_ACCT_INFO);
  private NAsmField cdCardInfo = new NAsmField(this, "CD_CARD_INFO", NAsmFieldType.ALPHANUMERIC, Pos.CD_CARD_INFO, Len.CD_CARD_INFO);
  private NAsmField cdMoreInfo = new NAsmField(this, "CD_MORE_INFO", NAsmFieldType.ALPHANUMERIC, Pos.CD_MORE_INFO, Len.CD_MORE_INFO);
  private NAsmField lcdCommarea = new NAsmField(this, "LCD_COMMAREA", NAsmFieldType.NUMERIC_BINARY, Pos.LCD_COMMAREA, Len.LCD_COMMAREA);
  //--------------------------------------
  // RP STORAGE RESET
  private CcdaScreenTitle ccdaScreenTitle = new CcdaScreenTitle(this, "CCDA_SCREEN_TITLE", Pos.CCDA_SCREEN_TITLE);   
  private NAsmField wsAbstime = new NAsmField(this, "WS_ABSTIME", NAsmFieldType.NUMERIC_PACKED, Pos.WS_ABSTIME, Len.WS_ABSTIME);
  private NAsmField wsCurdateMmddyy = new NAsmField(this, "WS_CURDATE_MMDDYY", NAsmFieldType.ALPHANUMERIC, Pos.WS_CURDATE_MMDDYY, Len.WS_CURDATE_MMDDYY);
  private NAsmField wsCurtimeHhmmss = new NAsmField(this, "WS_CURTIME_HHMMSS", NAsmFieldType.ALPHANUMERIC, Pos.WS_CURTIME_HHMMSS, Len.WS_CURTIME_HHMMSS);
  private CcdaCommonMessages ccdaCommonMessages = new CcdaCommonMessages(this, "CCDA_COMMON_MESSAGES", Pos.CCDA_COMMON_MESSAGES);   
  private SecUserData secUserData = new SecUserData(this, "SEC_USER_DATA", Pos.SEC_USER_DATA);   
  //----------------------------
  private NAsmField blank = new NAsmField(this, "BLANK", NAsmFieldType.ALPHANUMERIC, Pos.BLANK, Len.BLANK);
  private NAsmField saver7 = new NAsmField(this, "SAVER7", NAsmFieldType.NUMERIC_BINARY, Pos.SAVER7, Len.SAVER7);
  private NAsmField saver8 = new NAsmField(this, "SAVER8", NAsmFieldType.NUMERIC_BINARY, Pos.SAVER8, Len.SAVER8);
  private NAsmField saver9 = new NAsmField(this, "SAVER9", NAsmFieldType.NUMERIC_BINARY, Pos.SAVER9, Len.SAVER9);
  private NAsmField saver9b = new NAsmField(this, "SAVER9B", NAsmFieldType.NUMERIC_BINARY, Pos.SAVER9B, Len.SAVER9B);

  /**
   * @return the cosgn00m
   */
  public Cosgn00m getCosgn00m() {
    return cosgn00m;
  }

  /**
   * @return the wsVariables
   */
  public WsVariables getWsVariables() {
    return wsVariables;
  }

  /**
   * @return the cdCommarea
   */
  public NAsmField getCdCommarea() {
    return cdCommarea;
  }

  /**
   * @return the cdGeneInfo
   */
  public CdGeneInfo getCdGeneInfo() {
    return cdGeneInfo;
  }

  /**
   * @return the cdCustInfo
   */
  public NAsmField getCdCustInfo() {
    return cdCustInfo;
  }

  /**
   * @return the cdAcctInfo
   */
  public NAsmField getCdAcctInfo() {
    return cdAcctInfo;
  }

  /**
   * @return the cdCardInfo
   */
  public NAsmField getCdCardInfo() {
    return cdCardInfo;
  }

  /**
   * @return the cdMoreInfo
   */
  public NAsmField getCdMoreInfo() {
    return cdMoreInfo;
  }

  /**
   * @return the lcdCommarea
   */
  public NAsmField getLcdCommarea() {
    return lcdCommarea;
  }

  /**
   * @return the ccdaScreenTitle
   */
  public CcdaScreenTitle getCcdaScreenTitle() {
    return ccdaScreenTitle;
  }

  /**
   * @return the wsAbstime
   */
  public NAsmField getWsAbstime() {
    return wsAbstime;
  }

  /**
   * @return the wsCurdateMmddyy
   */
  public NAsmField getWsCurdateMmddyy() {
    return wsCurdateMmddyy;
  }

  /**
   * @return the wsCurtimeHhmmss
   */
  public NAsmField getWsCurtimeHhmmss() {
    return wsCurtimeHhmmss;
  }

  /**
   * @return the ccdaCommonMessages
   */
  public CcdaCommonMessages getCcdaCommonMessages() {
    return ccdaCommonMessages;
  }

  /**
   * @return the secUserData
   */
  public SecUserData getSecUserData() {
    return secUserData;
  }

  /**
   * @return the blank
   */
  public NAsmField getBlank() {
    return blank;
  }

  /**
   * @return the saver7
   */
  public NAsmField getSaver7() {
    return saver7;
  }

  /**
   * @return the saver8
   */
  public NAsmField getSaver8() {
    return saver8;
  }

  /**
   * @return the saver9
   */
  public NAsmField getSaver9() {
    return saver9;
  }

  /**
   * @return the saver9b
   */
  public NAsmField getSaver9b() {
    return saver9b;
  }

  /**
   *
   * @throws NAsmException
   */
  public Cosgn00aWs() throws NAsmException {
    super("WS", NAsmFieldType.ALPHANUMERIC, Pos.WS, Len.WS);
    lcdCommarea.set(160);
    blank.set(_SPACE(Len.BLANK));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int CD_COMMAREA = 0;
    public static final int CD_COMMAREA_SLOT = 8;
    public static final int CD_CUST_INFO = 84;
    public static final int CD_ACCT_INFO = 12;
    public static final int CD_CARD_INFO = 16;
    public static final int CD_MORE_INFO = 14;
    public static final int LCD_COMMAREA = 2;
    public static final int WS_ABSTIME = 8;
    public static final int WS_CURDATE_MMDDYY = 8;
    public static final int WS_CURTIME_HHMMSS = 9;
    public static final int BLANK = 250;
    public static final int SAVER7 = 4;
    public static final int SAVER8 = 4;
    public static final int SAVER9 = 4;
    public static final int SAVER9B = 4;

    public static final int WS = Cosgn00m.Len.COSGN00M
                                + WsVariables.Len.WS_VARIABLES
                                + Len.CD_COMMAREA
                                + CdGeneInfo.Len.CD_GENE_INFO
                                + Len.CD_CUST_INFO
                                + Len.CD_ACCT_INFO
                                + Len.CD_CARD_INFO
                                + Len.CD_MORE_INFO
                                + Len.LCD_COMMAREA
                                + CcdaScreenTitle.Len.CCDA_SCREEN_TITLE
                                + Len.WS_ABSTIME
                                + Len.WS_CURDATE_MMDDYY
                                + Len.WS_CURTIME_HHMMSS
                                + CcdaCommonMessages.Len.CCDA_COMMON_MESSAGES
                                + SecUserData.Len.SEC_USER_DATA
                                + Len.BLANK
                                + Len.SAVER7
                                + Len.SAVER8
                                + Len.SAVER9
                                + Len.SAVER9B;

  }

  //
  //Fields POS
  //
  public static class Pos {

    // ASM_POS: 0
    public static final int WS = 0;
    // ASM_POS: 0
    public static final int COSGN00M = 0;
    // ASM_POS: 0
    public static final int WS_VARIABLES = Pos.COSGN00M + Cosgn00m.Len.COSGN00M;
    // ASM_POS: 128
    public static final int CD_COMMAREA = Pos.WS_VARIABLES + WsVariables.Len.WS_VARIABLES;
    // ASM_POS: 128
    public static final int CD_GENE_INFO = Pos.CD_COMMAREA + Len.CD_COMMAREA;
    // ASM_POS: 162
    public static final int CD_CUST_INFO = Pos.CD_GENE_INFO + CdGeneInfo.Len.CD_GENE_INFO;
    // ASM_POS: 246
    public static final int CD_ACCT_INFO = Pos.CD_CUST_INFO + Len.CD_CUST_INFO;
    // ASM_POS: 258
    public static final int CD_CARD_INFO = Pos.CD_ACCT_INFO + Len.CD_ACCT_INFO;
    // ASM_POS: 274
    public static final int CD_MORE_INFO = Pos.CD_CARD_INFO + Len.CD_CARD_INFO;
    // ASM_POS: 288
    public static final int LCD_COMMAREA = Pos.CD_MORE_INFO + Len.CD_MORE_INFO;
    // ASM_POS: 290
    public static final int CCDA_SCREEN_TITLE = Pos.LCD_COMMAREA + Len.LCD_COMMAREA;
    // ASM_POS: 372
    public static final int WS_ABSTIME = Pos.CCDA_SCREEN_TITLE + CcdaScreenTitle.Len.CCDA_SCREEN_TITLE;
    // ASM_POS: 380
    public static final int WS_CURDATE_MMDDYY = Pos.WS_ABSTIME + Len.WS_ABSTIME;
    // ASM_POS: 388
    public static final int WS_CURTIME_HHMMSS = Pos.WS_CURDATE_MMDDYY + Len.WS_CURDATE_MMDDYY;
    // ASM_POS: 396
    public static final int CCDA_COMMON_MESSAGES = Pos.WS_CURTIME_HHMMSS + Len.WS_CURTIME_HHMMSS;
    // ASM_POS: 496
    public static final int SEC_USER_DATA = Pos.CCDA_COMMON_MESSAGES + CcdaCommonMessages.Len.CCDA_COMMON_MESSAGES;
    // ASM_POS: 0
    public static final int BLANK = Pos.SEC_USER_DATA + SecUserData.Len.SEC_USER_DATA;
    // ASM_POS: 16
    public static final int SAVER7 = Pos.BLANK + Len.BLANK;
    // ASM_POS: 20
    public static final int SAVER8 = Pos.SAVER7 + Len.SAVER7;
    // ASM_POS: 24
    public static final int SAVER9 = Pos.SAVER8 + Len.SAVER8;
    // ASM_POS: 28
    public static final int SAVER9B = Pos.SAVER9 + Len.SAVER9;

  }

}
