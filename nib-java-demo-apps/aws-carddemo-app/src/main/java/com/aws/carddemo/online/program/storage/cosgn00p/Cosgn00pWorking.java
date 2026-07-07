package com.aws.carddemo.online.program.storage.cosgn00p;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.include.Cocom01y;
import com.aws.carddemo.common.include.Cosgn00;
import com.aws.carddemo.common.include.Cottl01y;
import com.aws.carddemo.common.include.Csdat01y;
import com.aws.carddemo.common.include.Csmsg01y;
import com.aws.carddemo.common.include.Csusr01y;
import com.nib.commons.storage.*;

public class Cosgn00pWorking extends NGroup {
  // PLI:  DCL COMMAREA_PTR     POINTER;
  public NPointer commareaPtr = new NPointer();

  // PLI:  DCL 1 DFHCOMMAREA BASED(COMMAREA_PTR),
  public static class Dfhcommarea extends NGroup {
    // PLI:   */   5 LK_COMMAREA    CHAR (32767) VAR;  /* IT'S
    public NChar lkCommarea = new NChar(32767);
  }

  public Dfhcommarea dfhcommarea = (Dfhcommarea) new Dfhcommarea().redefines(commareaPtr);
  // PLI:  DCL (LENGTH, UPPERCASE, LOW, DATETIME, STG) BUILTIN;
  // PLI:  DCL (LENGTH, UPPERCASE, LOW, DATETIME, STG) BUILTIN;
  // PLI:  DCL (LENGTH, UPPERCASE, LOW, DATETIME, STG) BUILTIN;
  // PLI:  DCL (LENGTH, UPPERCASE, LOW, DATETIME, STG) BUILTIN;
  // PLI:  DCL (LENGTH, UPPERCASE, LOW, DATETIME, STG) BUILTIN;
  // PLI:  DCL CICS_LEN  FIXED BIN(15);
  public NInt16 cicsLen = new NInt16();
  // PLI:  DCL CICS_LEN2 FIXED BIN(15);
  public NInt16 cicsLen2 = new NInt16();
  // PLI:  DCL 1 WS_VARIABLES,
  public WsVariables wsVariables = new WsVariables();

  public static class WsVariables extends NGroup {

    // PLI:        2 WS_PGMNAME     CHAR(8)         INIT('COSGN00P'),
    public NChar wsPgmname = new NChar(8).initial("COSGN00P");
    // PLI:        2 WS_TRANID      CHAR(4)         INIT('PP00'),
    public NChar wsTranid = new NChar(4).initial("PP00");
    // PLI:        2 WS_MESSAGE     CHAR(80)        INIT(' '),
    public NChar wsMessage = new NChar(80).initial(" ");
    // PLI:        2 WS_USRSEC_FILE CHAR(8)         INIT('USRSEC  '),
    public NChar wsUsrsecFile = new NChar(8).initial("USRSEC  ");
    // PLI:        2 WS_ERR_FLG     CHAR            INIT('N'),
    public NChar wsErrFlg = new NChar(1).initial("N");
    // PLI:        2 WS_RESP_CD     FIXED BIN(31)   INIT(0),
    public NInt32 wsRespCd = new NInt32().initial(0);
    // PLI:        2 WS_REAS_CD     FIXED BIN(31)   INIT(0),
    public NInt32 wsReasCd = new NInt32().initial(0);
    // PLI:        2 WS_USER_ID     CHAR(8),
    public NChar wsUserId = new NChar(8);
    // PLI:        2 WS_USER_PWD    CHAR(8);
    public NChar wsUserPwd = new NChar(8);
  }

  public Cocom01y cocom01y = new Cocom01y();
  public Cosgn00 cosgn00 = new Cosgn00();
  public Cottl01y cottl01y = new Cottl01y();
  public Csdat01y csdat01y = new Csdat01y();
  public Csmsg01y csmsg01y = new Csmsg01y();
  public Csusr01y csusr01y = new Csusr01y();
}
