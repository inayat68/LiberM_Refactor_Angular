package com.aws.carddemo.online.program.storage.cotrn00c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Cotrn00;
import com.aws.carddemo.common.copybook.Cottl01y;
import com.aws.carddemo.common.copybook.Csdat01y;
import com.aws.carddemo.common.copybook.Csmsg01y;
import com.aws.carddemo.common.copybook.Cvtra05y;
import com.nib.commons.storage.*;

public class Cotrn00cWorking extends NGroup {
  // COB:        01 WS-VARIABLES.
  public WsVariables wsVariables = new WsVariables();

  public static class WsVariables extends NGroup {

    // COB:          05 WS-PGMNAME                 PIC X(08) VALUE 'COTRN00C'.
    public NChar wsPgmname = new NChar(8).initial("COTRN00C");
    // COB:          05 WS-TRANID                  PIC X(04) VALUE 'CT00'.
    public NChar wsTranid = new NChar(4).initial("CT00");
    // COB:          05 WS-MESSAGE                 PIC X(80) VALUE SPACES.
    public NChar wsMessage = new NChar(80).initial(NChar.Space);
    // COB:          05 WS-TRANSACT-FILE             PIC X(08) VALUE 'TRANSACT'.
    public NChar wsTransactFile = new NChar(8).initial("TRANSACT");
    // COB:          05 WS-ERR-FLG                 PIC X(01) VALUE 'N'.
    public NChar wsErrFlg = new NChar(1).initial("N");

    // COB:            88 ERR-FLG-ON                         VALUE 'Y'.
    public boolean errFlgOn() {
      return wsErrFlg.equals("Y");
    }

    public void setErrFlgOn(boolean value) {
      if (value) wsErrFlg.setValue("Y");
    }

    // COB:            88 ERR-FLG-OFF                        VALUE 'N'.
    public boolean errFlgOff() {
      return wsErrFlg.equals("N");
    }

    public void setErrFlgOff(boolean value) {
      if (value) wsErrFlg.setValue("N");
    }

    // COB:          05 WS-TRANSACT-EOF            PIC X(01) VALUE 'N'.
    public NChar wsTransactEof = new NChar(1).initial("N");

    // COB:            88 TRANSACT-EOF                       VALUE 'Y'.
    public boolean transactEof() {
      return wsTransactEof.equals("Y");
    }

    public void setTransactEof(boolean value) {
      if (value) wsTransactEof.setValue("Y");
    }

    // COB:            88 TRANSACT-NOT-EOF                   VALUE 'N'.
    public boolean transactNotEof() {
      return wsTransactEof.equals("N");
    }

    public void setTransactNotEof(boolean value) {
      if (value) wsTransactEof.setValue("N");
    }

    // COB:          05 WS-SEND-ERASE-FLG          PIC X(01) VALUE 'Y'.
    public NChar wsSendEraseFlg = new NChar(1).initial("Y");

    // COB:            88 SEND-ERASE-YES                     VALUE 'Y'.
    public boolean sendEraseYes() {
      return wsSendEraseFlg.equals("Y");
    }

    public void setSendEraseYes(boolean value) {
      if (value) wsSendEraseFlg.setValue("Y");
    }

    // COB:            88 SEND-ERASE-NO                      VALUE 'N'.
    public boolean sendEraseNo() {
      return wsSendEraseFlg.equals("N");
    }

    public void setSendEraseNo(boolean value) {
      if (value) wsSendEraseFlg.setValue("N");
    }

    // COB:          05 WS-RESP-CD                 PIC S9(09) COMP VALUE ZEROS.
    public NInt32 wsRespCd = new NInt32().initial(0);
    // COB:          05 WS-REAS-CD                 PIC S9(09) COMP VALUE ZEROS.
    public NInt32 wsReasCd = new NInt32().initial(0);
    // COB:          05 WS-REC-COUNT               PIC S9(04) COMP VALUE ZEROS.
    public NInt16 wsRecCount = new NInt16().initial(0);
    // COB:          05 WS-IDX                     PIC S9(04) COMP VALUE ZEROS.
    public NInt16 wsIdx = new NInt16().initial(0);
    // COB:          05 WS-PAGE-NUM                PIC S9(04) COMP VALUE ZEROS.
    public NInt16 wsPageNum = new NInt16().initial(0);
    // COB:          05 WS-TRAN-AMT                PIC +99999999.99.
    public NZoned wsTranAmt = new NZoned(12).formatAs("+00000000.00");
    // COB:          05 WS-TRAN-DATE               PIC X(08) VALUE '00/00/00'.
    public NChar wsTranDate = new NChar(8).initial("00/00/00");
  }

  // COB:        01 CARDDEMO-COMMAREA.
  public CarddemoCommarea carddemoCommarea = new CarddemoCommarea();

  public static class CarddemoCommarea extends NGroup {

    // COB:           05 CDEMO-GENERAL-INFO.
    public static class CdemoGeneralInfo extends NGroup {
      // COB:              10 CDEMO-FROM-TRANID             PIC X(04).
      public NChar cdemoFromTranid = new NChar(4);
      // COB:              10 CDEMO-FROM-PROGRAM            PIC X(08).
      public NChar cdemoFromProgram = new NChar(8);
      // COB:              10 CDEMO-TO-TRANID               PIC X(04).
      public NChar cdemoToTranid = new NChar(4);
      // COB:              10 CDEMO-TO-PROGRAM              PIC X(08).
      public NChar cdemoToProgram = new NChar(8);
      // COB:              10 CDEMO-USER-ID                 PIC X(08).
      public NChar cdemoUserId = new NChar(8);
      // COB:              10 CDEMO-USER-TYPE               PIC X(01).
      public NChar cdemoUserType = new NChar(1);

      // COB:                 88 CDEMO-USRTYP-ADMIN         VALUE 'A'.
      public boolean cdemoUsrtypAdmin() {
        return cdemoUserType.equals("A");
      }

      public void setCdemoUsrtypAdmin(boolean value) {
        if (value) cdemoUserType.setValue("A");
      }

      // COB:                 88 CDEMO-USRTYP-USER          VALUE 'U'.
      public boolean cdemoUsrtypUser() {
        return cdemoUserType.equals("U");
      }

      public void setCdemoUsrtypUser(boolean value) {
        if (value) cdemoUserType.setValue("U");
      }

      // COB:              10 CDEMO-PGM-CONTEXT             PIC 9(01).
      public NZoned cdemoPgmContext = new NZoned(1, false);

      // COB:                 88 CDEMO-PGM-ENTER            VALUE 0.
      public boolean cdemoPgmEnter() {
        return cdemoPgmContext.equals(0);
      }

      public void setCdemoPgmEnter(boolean value) {
        if (value) cdemoPgmContext.setValue(0);
      }

      // COB:                 88 CDEMO-PGM-REENTER          VALUE 1.
      public boolean cdemoPgmReenter() {
        return cdemoPgmContext.equals(1);
      }

      public void setCdemoPgmReenter(boolean value) {
        if (value) cdemoPgmContext.setValue(1);
      }
    }

    public CdemoGeneralInfo cdemoGeneralInfo = new CdemoGeneralInfo();

    // COB:           05 CDEMO-CUSTOMER-INFO.
    public static class CdemoCustomerInfo extends NGroup {
      // COB:              10 CDEMO-CUST-ID                 PIC 9(09).
      public NZoned cdemoCustId = new NZoned(9, false);
      // COB:              10 CDEMO-CUST-FNAME              PIC X(25).
      public NChar cdemoCustFname = new NChar(25);
      // COB:              10 CDEMO-CUST-MNAME              PIC X(25).
      public NChar cdemoCustMname = new NChar(25);
      // COB:              10 CDEMO-CUST-LNAME              PIC X(25).
      public NChar cdemoCustLname = new NChar(25);
    }

    public CdemoCustomerInfo cdemoCustomerInfo = new CdemoCustomerInfo();

    // COB:           05 CDEMO-ACCOUNT-INFO.
    public static class CdemoAccountInfo extends NGroup {
      // COB:              10 CDEMO-ACCT-ID                 PIC 9(11).
      public NZoned cdemoAcctId = new NZoned(11, false);
      // COB:              10 CDEMO-ACCT-STATUS             PIC X(01).
      public NChar cdemoAcctStatus = new NChar(1);
    }

    public CdemoAccountInfo cdemoAccountInfo = new CdemoAccountInfo();

    // COB:           05 CDEMO-CARD-INFO.
    public static class CdemoCardInfo extends NGroup {
      // COB:              10 CDEMO-CARD-NUM                PIC 9(16).
      public NZoned cdemoCardNum = new NZoned(16, false);
    }

    public CdemoCardInfo cdemoCardInfo = new CdemoCardInfo();

    // COB:           05 CDEMO-MORE-INFO.
    public static class CdemoMoreInfo extends NGroup {
      // COB:              10  CDEMO-LAST-MAP               PIC X(7).
      public NChar cdemoLastMap = new NChar(7);
      // COB:              10  CDEMO-LAST-MAPSET            PIC X(7).
      public NChar cdemoLastMapset = new NChar(7);
    }

    public CdemoMoreInfo cdemoMoreInfo = new CdemoMoreInfo();

    // COB:           05 CDEMO-CT00-INFO.
    public static class CdemoCt00Info extends NGroup {
      // COB:              10 CDEMO-CT00-TRNID-FIRST     PIC X(16).
      public NChar cdemoCt00TrnidFirst = new NChar(16);
      // COB:              10 CDEMO-CT00-TRNID-LAST      PIC X(16).
      public NChar cdemoCt00TrnidLast = new NChar(16);
      // COB:              10 CDEMO-CT00-PAGE-NUM        PIC 9(08).
      public NZoned cdemoCt00PageNum = new NZoned(8, false);
      // COB:              10 CDEMO-CT00-NEXT-PAGE-FLG   PIC X(01) VALUE 'N'.
      public NChar cdemoCt00NextPageFlg = new NChar(1).initial("N");

      // COB:                 88 NEXT-PAGE-YES                     VALUE 'Y'.
      public boolean nextPageYes() {
        return cdemoCt00NextPageFlg.equals("Y");
      }

      public void setNextPageYes(boolean value) {
        if (value) cdemoCt00NextPageFlg.setValue("Y");
      }

      // COB:                 88 NEXT-PAGE-NO                      VALUE 'N'.
      public boolean nextPageNo() {
        return cdemoCt00NextPageFlg.equals("N");
      }

      public void setNextPageNo(boolean value) {
        if (value) cdemoCt00NextPageFlg.setValue("N");
      }

      // COB:              10 CDEMO-CT00-TRN-SEL-FLG     PIC X(01).
      public NChar cdemoCt00TrnSelFlg = new NChar(1);
      // COB:              10 CDEMO-CT00-TRN-SELECTED    PIC X(16).
      public NChar cdemoCt00TrnSelected = new NChar(16);
    }

    public CdemoCt00Info cdemoCt00Info = new CdemoCt00Info();
  }

  public Cotrn00 cotrn00 = new Cotrn00();
  public Cottl01y cottl01y = new Cottl01y();
  public Csdat01y csdat01y = new Csdat01y();
  public Csmsg01y csmsg01y = new Csmsg01y();
  public Cvtra05y cvtra05y = new Cvtra05y();
}
