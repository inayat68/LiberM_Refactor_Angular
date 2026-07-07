package com.aws.carddemo.online.program.storage.cosgn00a;

import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.storage.NAsmField;

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
public class CdCommarea extends NAsmField {

  private CdGeneInfo cdGeneInfo = new CdGeneInfo(this, "CD_GENE_INFO", Pos.CD_GENE_INFO);   
  private NAsmField cdCustInfo = new NAsmField(this, "CD_CUST_INFO", NAsmFieldType.ALPHANUMERIC, Pos.CD_CUST_INFO, Len.CD_CUST_INFO);
  private NAsmField cdAcctInfo = new NAsmField(this, "CD_ACCT_INFO", NAsmFieldType.ALPHANUMERIC, Pos.CD_ACCT_INFO, Len.CD_ACCT_INFO);
  private NAsmField cdCardInfo = new NAsmField(this, "CD_CARD_INFO", NAsmFieldType.ALPHANUMERIC, Pos.CD_CARD_INFO, Len.CD_CARD_INFO);
  private NAsmField cdMoreInfo = new NAsmField(this, "CD_MORE_INFO", NAsmFieldType.ALPHANUMERIC, Pos.CD_MORE_INFO, Len.CD_MORE_INFO);
  private NAsmField lcdCommarea = new NAsmField(this, "LCD_COMMAREA", NAsmFieldType.NUMERIC_BINARY, Pos.LCD_COMMAREA, Len.LCD_COMMAREA);

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
   *
   * @throws NAsmException
   */
  public CdCommarea(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.CD_COMMAREA);
    lcdCommarea.set(160);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int CD_CUST_INFO = 84;
    public static final int CD_ACCT_INFO = 12;
    public static final int CD_CARD_INFO = 16;
    public static final int CD_MORE_INFO = 14;
    public static final int LCD_COMMAREA = 2;

    public static final int CD_COMMAREA_SLOT = 8;
    public static final int CD_COMMAREA = CdGeneInfo.Len.CD_GENE_INFO
                                + Len.CD_CUST_INFO
                                + Len.CD_ACCT_INFO
                                + Len.CD_CARD_INFO
                                + Len.CD_MORE_INFO
                                + Len.LCD_COMMAREA;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int CD_GENE_INFO = 0;
    public static final int CD_CUST_INFO = Pos.CD_GENE_INFO + CdGeneInfo.Len.CD_GENE_INFO;
    public static final int CD_ACCT_INFO = Pos.CD_CUST_INFO + Len.CD_CUST_INFO;
    public static final int CD_CARD_INFO = Pos.CD_ACCT_INFO + Len.CD_ACCT_INFO;
    public static final int CD_MORE_INFO = Pos.CD_CARD_INFO + Len.CD_CARD_INFO;
    public static final int LCD_COMMAREA = Pos.CD_MORE_INFO + Len.CD_MORE_INFO;

  }

}
