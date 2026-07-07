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
public class SecUserData extends NAsmField {

  private NAsmField secUsrId = new NAsmField(this, "SEC_USR_ID", NAsmFieldType.ALPHANUMERIC, Pos.SEC_USR_ID, Len.SEC_USR_ID);
  private NAsmField secUsrFname = new NAsmField(this, "SEC_USR_FNAME", NAsmFieldType.ALPHANUMERIC, Pos.SEC_USR_FNAME, Len.SEC_USR_FNAME);
  private NAsmField secUsrLname = new NAsmField(this, "SEC_USR_LNAME", NAsmFieldType.ALPHANUMERIC, Pos.SEC_USR_LNAME, Len.SEC_USR_LNAME);
  private NAsmField secUsrPwd = new NAsmField(this, "SEC_USR_PWD", NAsmFieldType.ALPHANUMERIC, Pos.SEC_USR_PWD, Len.SEC_USR_PWD);
  private NAsmField secUsrType = new NAsmField(this, "SEC_USR_TYPE", NAsmFieldType.ALPHANUMERIC, Pos.SEC_USR_TYPE, Len.SEC_USR_TYPE);
  private NAsmField secUsrFiller = new NAsmField(this, "SEC_USR_FILLER", NAsmFieldType.ALPHANUMERIC, Pos.SEC_USR_FILLER, Len.SEC_USR_FILLER);

  /**
   * @return the secUsrId
   */
  public NAsmField getSecUsrId() {
    return secUsrId;
  }

  /**
   * @return the secUsrFname
   */
  public NAsmField getSecUsrFname() {
    return secUsrFname;
  }

  /**
   * @return the secUsrLname
   */
  public NAsmField getSecUsrLname() {
    return secUsrLname;
  }

  /**
   * @return the secUsrPwd
   */
  public NAsmField getSecUsrPwd() {
    return secUsrPwd;
  }

  /**
   * @return the secUsrType
   */
  public NAsmField getSecUsrType() {
    return secUsrType;
  }

  /**
   * @return the secUsrFiller
   */
  public NAsmField getSecUsrFiller() {
    return secUsrFiller;
  }

  /**
   *
   * @throws NAsmException
   */
  public SecUserData(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.SEC_USER_DATA);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int SEC_USR_ID = 8;
    public static final int SEC_USR_FNAME = 20;
    public static final int SEC_USR_LNAME = 20;
    public static final int SEC_USR_PWD = 8;
    public static final int SEC_USR_TYPE = 1;
    public static final int SEC_USR_FILLER = 23;

    public static final int SEC_USER_DATA_SLOT = 80;
    public static final int SEC_USER_DATA = Len.SEC_USR_ID
                                + Len.SEC_USR_FNAME
                                + Len.SEC_USR_LNAME
                                + Len.SEC_USR_PWD
                                + Len.SEC_USR_TYPE
                                + Len.SEC_USR_FILLER;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int SEC_USR_ID = 0;
    public static final int SEC_USR_FNAME = Pos.SEC_USR_ID + Len.SEC_USR_ID;
    public static final int SEC_USR_LNAME = Pos.SEC_USR_FNAME + Len.SEC_USR_FNAME;
    public static final int SEC_USR_PWD = Pos.SEC_USR_LNAME + Len.SEC_USR_LNAME;
    public static final int SEC_USR_TYPE = Pos.SEC_USR_PWD + Len.SEC_USR_PWD;
    public static final int SEC_USR_FILLER = Pos.SEC_USR_TYPE + Len.SEC_USR_TYPE;

  }

}
