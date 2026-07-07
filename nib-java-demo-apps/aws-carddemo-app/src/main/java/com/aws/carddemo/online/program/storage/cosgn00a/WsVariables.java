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
public class WsVariables extends NAsmField {

  private NAsmField wsPgmname = new NAsmField(this, "WS_PGMNAME", NAsmFieldType.ALPHANUMERIC, Pos.WS_PGMNAME, Len.WS_PGMNAME);
  private NAsmField wsTranid = new NAsmField(this, "WS_TRANID", NAsmFieldType.ALPHANUMERIC, Pos.WS_TRANID, Len.WS_TRANID);
  private NAsmField wsMessage = new NAsmField(this, "WS_MESSAGE", NAsmFieldType.ALPHANUMERIC, Pos.WS_MESSAGE, Len.WS_MESSAGE);
  private NAsmField wsUsrsecFile = new NAsmField(this, "WS_USRSEC_FILE", NAsmFieldType.ALPHANUMERIC, Pos.WS_USRSEC_FILE, Len.WS_USRSEC_FILE);
  private NAsmField wsErrFlg = new NAsmField(this, "WS_ERR_FLG", NAsmFieldType.ALPHANUMERIC, Pos.WS_ERR_FLG, Len.WS_ERR_FLG);
  private NAsmField wsRespCd = new NAsmField(this, "WS_RESP_CD", NAsmFieldType.NUMERIC_BINARY, Pos.WS_RESP_CD, Len.WS_RESP_CD);
  private NAsmField wsReasCd = new NAsmField(this, "WS_REAS_CD", NAsmFieldType.NUMERIC_BINARY, Pos.WS_REAS_CD, Len.WS_REAS_CD);
  private NAsmField wsUserId = new NAsmField(this, "WS_USER_ID", NAsmFieldType.ALPHANUMERIC, Pos.WS_USER_ID, Len.WS_USER_ID);
  private NAsmField wsUserPwd = new NAsmField(this, "WS_USER_PWD", NAsmFieldType.ALPHANUMERIC, Pos.WS_USER_PWD, Len.WS_USER_PWD);

  /**
   * @return the wsPgmname
   */
  public NAsmField getWsPgmname() {
    return wsPgmname;
  }

  /**
   * @return the wsTranid
   */
  public NAsmField getWsTranid() {
    return wsTranid;
  }

  /**
   * @return the wsMessage
   */
  public NAsmField getWsMessage() {
    return wsMessage;
  }

  /**
   * @return the wsUsrsecFile
   */
  public NAsmField getWsUsrsecFile() {
    return wsUsrsecFile;
  }

  /**
   * @return the wsErrFlg
   */
  public NAsmField getWsErrFlg() {
    return wsErrFlg;
  }

  /**
   * @return the wsRespCd
   */
  public NAsmField getWsRespCd() {
    return wsRespCd;
  }

  /**
   * @return the wsReasCd
   */
  public NAsmField getWsReasCd() {
    return wsReasCd;
  }

  /**
   * @return the wsUserId
   */
  public NAsmField getWsUserId() {
    return wsUserId;
  }

  /**
   * @return the wsUserPwd
   */
  public NAsmField getWsUserPwd() {
    return wsUserPwd;
  }

  /**
   *
   * @throws NAsmException
   */
  public WsVariables(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.WS_VARIABLES);
    wsPgmname.set("COSGN00A");
    wsTranid.set("AA00");
    wsMessage.set(_SPACE(Len.WS_MESSAGE));
    wsUsrsecFile.set(_PAD("USRSEC", Len.WS_USRSEC_FILE));
    wsErrFlg.set("N");
    wsUserId.set(_SPACE(Len.WS_USER_ID));
    wsUserPwd.set(_SPACE(Len.WS_USER_PWD));
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int WS_PGMNAME = 8;
    public static final int WS_TRANID = 4;
    public static final int WS_MESSAGE = 80;
    public static final int WS_USRSEC_FILE = 8;
    public static final int WS_ERR_FLG = 1;
    public static final int WS_RESP_CD = 4;
    public static final int WS_REAS_CD = 4;
    public static final int WS_USER_ID = 8;
    public static final int WS_USER_PWD = 8;

    public static final int WS_VARIABLES = Len.WS_PGMNAME
                                + Len.WS_TRANID
                                + Len.WS_MESSAGE
                                + Len.WS_USRSEC_FILE
                                + Len.WS_ERR_FLG
                                + Len.WS_RESP_CD
                                + Len.WS_REAS_CD
                                + Len.WS_USER_ID
                                + Len.WS_USER_PWD;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int WS_PGMNAME = 0;
    public static final int WS_TRANID = Pos.WS_PGMNAME + Len.WS_PGMNAME;
    public static final int WS_MESSAGE = Pos.WS_TRANID + Len.WS_TRANID;
    public static final int WS_USRSEC_FILE = Pos.WS_MESSAGE + Len.WS_MESSAGE;
    public static final int WS_ERR_FLG = Pos.WS_USRSEC_FILE + Len.WS_USRSEC_FILE;
    public static final int WS_RESP_CD = Pos.WS_ERR_FLG + Len.WS_ERR_FLG;
    public static final int WS_REAS_CD = Pos.WS_RESP_CD + Len.WS_RESP_CD;
    public static final int WS_USER_ID = Pos.WS_REAS_CD + Len.WS_REAS_CD;
    public static final int WS_USER_PWD = Pos.WS_USER_ID + Len.WS_USER_ID;

  }

}
