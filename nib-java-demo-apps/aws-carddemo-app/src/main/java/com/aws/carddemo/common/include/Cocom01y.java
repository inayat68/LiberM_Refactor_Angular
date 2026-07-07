package com.aws.carddemo.common.include;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cocom01y extends NGroup {

  // PLI:   DCL  1 CARDDEMO_COMMAREA,
  public static class CarddemoCommarea extends NGroup {
    // PLI:          2 CDEMO_GENERAL_INFO,
    public static class CdemoGeneralInfo extends NGroup {
      // PLI:            3 CDEMO_FROM_TRANID             CHAR(4),
      public NChar cdemoFromTranid = new NChar(4);
      // PLI:            3 CDEMO_FROM_PROGRAM            CHAR(8),
      public NChar cdemoFromProgram = new NChar(8);
      // PLI:            3 CDEMO_TO_TRANID               CHAR(4),
      public NChar cdemoToTranid = new NChar(4);
      // PLI:            3 CDEMO_TO_PROGRAM              CHAR(8),
      public NChar cdemoToProgram = new NChar(8);
      // PLI:            3 CDEMO_USER_ID                 CHAR(8),
      public NChar cdemoUserId = new NChar(8);
      // PLI:            3 CDEMO_USER_TYPE               CHAR,
      public NChar cdemoUserType = new NChar(1);
      // PLI:            3 CDEMO_PGM_CONTEXT             PIC '9',
      public NZoned cdemoPgmContext = new NZoned(1, false);
    }

    public CdemoGeneralInfo cdemoGeneralInfo = new CdemoGeneralInfo();

    // PLI:          2 CDEMO_CUSTOMER_INFO,
    public static class CdemoCustomerInfo extends NGroup {
      // PLI:            3 CDEMO_CUST_ID                 PIC '(9)9',
      public NZoned cdemoCustId = new NZoned(9, false);
      // PLI:            3 CDEMO_CUST_FNAME              CHAR(25),
      public NChar cdemoCustFname = new NChar(25);
      // PLI:            3 CDEMO_CUST_MNAME              CHAR(25),
      public NChar cdemoCustMname = new NChar(25);
      // PLI:            3 CDEMO_CUST_LNAME              CHAR(25),
      public NChar cdemoCustLname = new NChar(25);
    }

    public CdemoCustomerInfo cdemoCustomerInfo = new CdemoCustomerInfo();

    // PLI:          2 CDEMO_ACCOUNT_INFO,
    public static class CdemoAccountInfo extends NGroup {
      // PLI:            3 CDEMO_ACCT_ID                 PIC '(11)9',
      public NZoned cdemoAcctId = new NZoned(11, false);
      // PLI:            3 CDEMO_ACCT_STATUS             CHAR,
      public NChar cdemoAcctStatus = new NChar(1);
    }

    public CdemoAccountInfo cdemoAccountInfo = new CdemoAccountInfo();

    // PLI:          2 CDEMO_CARD_INFO,
    public static class CdemoCardInfo extends NGroup {
      // PLI:            3 CDEMO_CARD_NUM                PIC '(16)9',
      public NZoned cdemoCardNum = new NZoned(16, false);
    }

    public CdemoCardInfo cdemoCardInfo = new CdemoCardInfo();

    // PLI:          2 CDEMO_MORE_INFO,
    public static class CdemoMoreInfo extends NGroup {
      // PLI:            3  CDEMO_LAST_MAP               CHAR(7),
      public NChar cdemoLastMap = new NChar(7);
      // PLI:            3  CDEMO_LAST_MAPSET            CHAR(7);
      public NChar cdemoLastMapset = new NChar(7);
    }

    public CdemoMoreInfo cdemoMoreInfo = new CdemoMoreInfo();
  }

  public CarddemoCommarea carddemoCommarea = new CarddemoCommarea();
  // PLI:   DCL CDEMO_USRTYP_ADMIN                   CHAR INIT('A');
  public NChar cdemoUsrtypAdmin = new NChar(1).initial("A");
  // PLI:   DCL CDEMO_USRTYP_USER                    CHAR INIT('U');
  public NChar cdemoUsrtypUser = new NChar(1).initial("U");
  // PLI:   DCL CDEMO_PGM_ENTER                      PIC '9' INIT(0);
  public NZoned cdemoPgmEnter = new NZoned(1, false).initial(0);
  // PLI:   DCL CDEMO_PGM_REENTER                    PIC '9' INIT(1);
  public NZoned cdemoPgmReenter = new NZoned(1, false).initial(1);
}
