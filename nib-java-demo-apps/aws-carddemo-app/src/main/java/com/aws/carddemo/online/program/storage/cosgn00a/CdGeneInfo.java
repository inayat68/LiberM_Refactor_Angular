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
public class CdGeneInfo extends NAsmField {

  private NAsmField cdFromTran = new NAsmField(this, "CD_FROM_TRAN", NAsmFieldType.ALPHANUMERIC, Pos.CD_FROM_TRAN, Len.CD_FROM_TRAN);
  private NAsmField cdFromProg = new NAsmField(this, "CD_FROM_PROG", NAsmFieldType.ALPHANUMERIC, Pos.CD_FROM_PROG, Len.CD_FROM_PROG);
  private NAsmField cdToTran = new NAsmField(this, "CD_TO_TRAN", NAsmFieldType.ALPHANUMERIC, Pos.CD_TO_TRAN, Len.CD_TO_TRAN);
  private NAsmField cdToProg = new NAsmField(this, "CD_TO_PROG", NAsmFieldType.ALPHANUMERIC, Pos.CD_TO_PROG, Len.CD_TO_PROG);
  private NAsmField cdUserId = new NAsmField(this, "CD_USER_ID", NAsmFieldType.ALPHANUMERIC, Pos.CD_USER_ID, Len.CD_USER_ID);
  private NAsmField cdUserType = new NAsmField(this, "CD_USER_TYPE", NAsmFieldType.ALPHANUMERIC, Pos.CD_USER_TYPE, Len.CD_USER_TYPE);
  private NAsmField cdProgCtx = new NAsmField(this, "CD_PROG_CTX", NAsmFieldType.ALPHANUMERIC, Pos.CD_PROG_CTX, Len.CD_PROG_CTX);

  /**
   * @return the cdFromTran
   */
  public NAsmField getCdFromTran() {
    return cdFromTran;
  }

  /**
   * @return the cdFromProg
   */
  public NAsmField getCdFromProg() {
    return cdFromProg;
  }

  /**
   * @return the cdToTran
   */
  public NAsmField getCdToTran() {
    return cdToTran;
  }

  /**
   * @return the cdToProg
   */
  public NAsmField getCdToProg() {
    return cdToProg;
  }

  /**
   * @return the cdUserId
   */
  public NAsmField getCdUserId() {
    return cdUserId;
  }

  /**
   * @return the cdUserType
   */
  public NAsmField getCdUserType() {
    return cdUserType;
  }

  /**
   * @return the cdProgCtx
   */
  public NAsmField getCdProgCtx() {
    return cdProgCtx;
  }

  /**
   *
   * @throws NAsmException
   */
  public CdGeneInfo(NAsmField parent, String name, int offset) throws NAsmException {
    super(parent, name, NAsmFieldType.ALPHANUMERIC, offset, Len.CD_GENE_INFO);
  }

  //
  //Fields LEN
  //
  public static class Len {

    public static final int CD_FROM_TRAN = 4;
    public static final int CD_FROM_PROG = 8;
    public static final int CD_TO_TRAN = 4;
    public static final int CD_TO_PROG = 8;
    public static final int CD_USER_ID = 8;
    public static final int CD_USER_TYPE = 1;
    public static final int CD_PROG_CTX = 1;

    public static final int CD_GENE_INFO_SLOT = 34;
    public static final int CD_GENE_INFO = Len.CD_FROM_TRAN
                                + Len.CD_FROM_PROG
                                + Len.CD_TO_TRAN
                                + Len.CD_TO_PROG
                                + Len.CD_USER_ID
                                + Len.CD_USER_TYPE
                                + Len.CD_PROG_CTX;

  }

  //
  //Fields POS
  //
  public static class Pos {

    public static final int CD_FROM_TRAN = 0;
    public static final int CD_FROM_PROG = Pos.CD_FROM_TRAN + Len.CD_FROM_TRAN;
    public static final int CD_TO_TRAN = Pos.CD_FROM_PROG + Len.CD_FROM_PROG;
    public static final int CD_TO_PROG = Pos.CD_TO_TRAN + Len.CD_TO_TRAN;
    public static final int CD_USER_ID = Pos.CD_TO_PROG + Len.CD_TO_PROG;
    public static final int CD_USER_TYPE = Pos.CD_USER_ID + Len.CD_USER_ID;
    public static final int CD_PROG_CTX = Pos.CD_USER_TYPE + Len.CD_USER_TYPE;

  }

}
