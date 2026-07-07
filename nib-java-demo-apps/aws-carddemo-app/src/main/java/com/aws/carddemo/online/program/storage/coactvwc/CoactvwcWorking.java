package com.aws.carddemo.online.program.storage.coactvwc;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Coactvw;
import com.aws.carddemo.common.copybook.Cocom01y;
import com.aws.carddemo.common.copybook.Cottl01y;
import com.aws.carddemo.common.copybook.Csdat01y;
import com.aws.carddemo.common.copybook.Csmsg01y;
import com.aws.carddemo.common.copybook.Csmsg02y;
import com.aws.carddemo.common.copybook.Csusr01y;
import com.aws.carddemo.common.copybook.Cvact01y;
import com.aws.carddemo.common.copybook.Cvact02y;
import com.aws.carddemo.common.copybook.Cvact03y;
import com.aws.carddemo.common.copybook.Cvcrd01y;
import com.aws.carddemo.common.copybook.Cvcus01y;
import com.nib.commons.storage.*;

public class CoactvwcWorking extends NGroup {
  // COB:        01  WS-MISC-STORAGE.
  public WsMiscStorage wsMiscStorage = new WsMiscStorage();

  public static class WsMiscStorage extends NGroup {

    // COB:          05 WS-CICS-PROCESSNG-VARS.
    public static class WsCicsProcessngVars extends NGroup {
      // COB:             07 WS-RESP-CD                          PIC S9(09) COMP
      // COB:                                                    VALUE ZEROS.
      public NInt32 wsRespCd = new NInt32().initial(0);
      // COB:             07 WS-REAS-CD                          PIC S9(09) COMP
      // COB:                                                    VALUE ZEROS.
      public NInt32 wsReasCd = new NInt32().initial(0);
      // COB:             07 WS-TRANID                           PIC X(4)
      // COB:                                                    VALUE SPACES.
      public NChar wsTranid = new NChar(4).initial(NChar.Space);
    }

    public WsCicsProcessngVars wsCicsProcessngVars = new WsCicsProcessngVars();
    // COB:          05  WS-INPUT-FLAG                         PIC X(1).
    public NChar wsInputFlag = new NChar(1);

    // COB:            88  INPUT-OK                            VALUE '0'.
    public boolean inputOk() {
      return wsInputFlag.equals("0");
    }

    public void setInputOk(boolean value) {
      if (value) wsInputFlag.setValue("0");
    }

    // COB:            88  INPUT-ERROR                         VALUE '1'.
    public boolean inputError() {
      return wsInputFlag.equals("1");
    }

    public void setInputError(boolean value) {
      if (value) wsInputFlag.setValue("1");
    }

    // COB:            88  INPUT-PENDING                       VALUE LOW-VALUES.
    public boolean wsInputFlagInputPending() {
      return wsInputFlag.isLowValues();
    }

    public void setWsInputFlagInputPending(boolean value) {
      if (value) wsInputFlag.lowValues();
    }

    // COB:          05  WS-PFK-FLAG                           PIC X(1).
    public NChar wsPfkFlag = new NChar(1);

    // COB:            88  PFK-VALID                           VALUE '0'.
    public boolean pfkValid() {
      return wsPfkFlag.equals("0");
    }

    public void setPfkValid(boolean value) {
      if (value) wsPfkFlag.setValue("0");
    }

    // COB:            88  PFK-INVALID                         VALUE '1'.
    public boolean pfkInvalid() {
      return wsPfkFlag.equals("1");
    }

    public void setPfkInvalid(boolean value) {
      if (value) wsPfkFlag.setValue("1");
    }

    // COB:            88  INPUT-PENDING                       VALUE LOW-VALUES.
    public boolean wsPfkFlagInputPending() {
      return wsPfkFlag.isLowValues();
    }

    public void setWsPfkFlagInputPending(boolean value) {
      if (value) wsPfkFlag.lowValues();
    }

    // COB:          05  WS-EDIT-ACCT-FLAG                     PIC X(1).
    public NChar wsEditAcctFlag = new NChar(1);

    // COB:            88  FLG-ACCTFILTER-NOT-OK               VALUE '0'.
    public boolean flgAcctfilterNotOk() {
      return wsEditAcctFlag.equals("0");
    }

    public void setFlgAcctfilterNotOk(boolean value) {
      if (value) wsEditAcctFlag.setValue("0");
    }

    // COB:            88  FLG-ACCTFILTER-ISVALID              VALUE '1'.
    public boolean flgAcctfilterIsvalid() {
      return wsEditAcctFlag.equals("1");
    }

    public void setFlgAcctfilterIsvalid(boolean value) {
      if (value) wsEditAcctFlag.setValue("1");
    }

    // COB:            88  FLG-ACCTFILTER-BLANK                VALUE ' '.
    public boolean flgAcctfilterBlank() {
      return wsEditAcctFlag.isSpaces();
    }

    public void setFlgAcctfilterBlank(boolean value) {
      if (value) wsEditAcctFlag.setValue(" ");
    }

    // COB:          05  WS-EDIT-CUST-FLAG                     PIC X(1).
    public NChar wsEditCustFlag = new NChar(1);

    // COB:            88  FLG-CUSTFILTER-NOT-OK               VALUE '0'.
    public boolean flgCustfilterNotOk() {
      return wsEditCustFlag.equals("0");
    }

    public void setFlgCustfilterNotOk(boolean value) {
      if (value) wsEditCustFlag.setValue("0");
    }

    // COB:            88  FLG-CUSTFILTER-ISVALID              VALUE '1'.
    public boolean flgCustfilterIsvalid() {
      return wsEditCustFlag.equals("1");
    }

    public void setFlgCustfilterIsvalid(boolean value) {
      if (value) wsEditCustFlag.setValue("1");
    }

    // COB:            88  FLG-CUSTFILTER-BLANK                VALUE ' '.
    public boolean flgCustfilterBlank() {
      return wsEditCustFlag.isSpaces();
    }

    public void setFlgCustfilterBlank(boolean value) {
      if (value) wsEditCustFlag.setValue(" ");
    }

    // COB:          05  WS-XREF-RID.
    public static class WsXrefRid extends NGroup {
      // COB:            10  WS-CARD-RID-CARDNUM                 PIC X(16).
      public NChar wsCardRidCardnum = new NChar(16);
      // COB:            10  WS-CARD-RID-CUST-ID                 PIC 9(09).
      public NZoned wsCardRidCustId = new NZoned(9, false);
      // COB:            10  WS-CARD-RID-CUST-ID-X REDEFINES
      // COB:                   WS-CARD-RID-CUST-ID              PIC X(09).
      public NChar wsCardRidCustIdX = new NChar(9).redefines(wsCardRidCustId);
      // COB:            10  WS-CARD-RID-ACCT-ID                 PIC 9(11).
      public NZoned wsCardRidAcctId = new NZoned(11, false);
      // COB:            10  WS-CARD-RID-ACCT-ID-X REDEFINES
      // COB:                   WS-CARD-RID-ACCT-ID              PIC X(11).
      public NChar wsCardRidAcctIdX = new NChar(11).redefines(wsCardRidAcctId);
    }

    public WsXrefRid wsXrefRid = new WsXrefRid();

    // COB:          05  WS-FILE-READ-FLAGS.
    public static class WsFileReadFlags extends NGroup {
      // COB:            10 WS-ACCOUNT-MASTER-READ-FLAG          PIC X(1).
      public NChar wsAccountMasterReadFlag = new NChar(1);

      // COB:               88 FOUND-ACCT-IN-MASTER              VALUE '1'.
      public boolean foundAcctInMaster() {
        return wsAccountMasterReadFlag.equals("1");
      }

      public void setFoundAcctInMaster(boolean value) {
        if (value) wsAccountMasterReadFlag.setValue("1");
      }

      // COB:            10 WS-CUST-MASTER-READ-FLAG             PIC X(1).
      public NChar wsCustMasterReadFlag = new NChar(1);

      // COB:               88 FOUND-CUST-IN-MASTER              VALUE '1'.
      public boolean foundCustInMaster() {
        return wsCustMasterReadFlag.equals("1");
      }

      public void setFoundCustInMaster(boolean value) {
        if (value) wsCustMasterReadFlag.setValue("1");
      }
    }

    public WsFileReadFlags wsFileReadFlags = new WsFileReadFlags();

    // COB:          05  WS-FILE-ERROR-MESSAGE.
    public static class WsFileErrorMessage extends NGroup {
      // COB:            10  FILLER                              PIC X(12)
      // COB:                                                    VALUE 'File Error: '.
      public NChar filler87 = new NChar(12).initial("File Error: ");
      // COB:            10  ERROR-OPNAME                        PIC X(8)
      // COB:                                                    VALUE SPACES.
      public NChar errorOpname = new NChar(8).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(4)
      // COB:                                                    VALUE ' on '.
      public NChar filler91 = new NChar(4).initial(" on ");
      // COB:            10  ERROR-FILE                          PIC X(9)
      // COB:                                                    VALUE SPACES.
      public NChar errorFile = new NChar(9).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(15)
      // COB:                                                    VALUE
      // COB:                                                    ' returned RESP '.
      public NChar filler95 = new NChar(15).initial(" returned RESP ");
      // COB:            10  ERROR-RESP                          PIC X(10)
      // COB:                                                    VALUE SPACES.
      public NChar errorResp = new NChar(10).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(7)
      // COB:                                                    VALUE ',RESP2 '.
      public NChar filler100 = new NChar(7).initial(",RESP2 ");
      // COB:            10  ERROR-RESP2                         PIC X(10)
      // COB:                                                    VALUE SPACES.
      public NChar errorResp2 = new NChar(10).initial(NChar.Space);
      // COB:           10  FILLER                               PIC X(5)
      // COB:                                                    VALUE SPACES.
      public NChar filler104 = new NChar(5).initial(NChar.Space);
    }

    public WsFileErrorMessage wsFileErrorMessage = new WsFileErrorMessage();
    // COB:          05  WS-LONG-MSG                           PIC X(500).
    public NChar wsLongMsg = new NChar(500);
    // COB:          05  WS-INFO-MSG                           PIC X(40).
    public NChar wsInfoMsg = new NChar(40);

    // COB:            88  WS-NO-INFO-MESSAGE                 VALUES
    public boolean wsNoInfoMessage() {
      return wsInfoMsg.isSpaces() || wsInfoMsg.isLowValues();
    }

    public void setWsNoInfoMessage(boolean value) {
      if (value) wsInfoMsg.setValue(SPACES);
    }

    // COB:            88  WS-PROMPT-FOR-INPUT                 VALUE
    public boolean wsPromptForInput() {
      return wsInfoMsg.equals("Enter or update id of account to display");
    }

    public void setWsPromptForInput(boolean value) {
      if (value) wsInfoMsg.setValue("Enter or update id of account to display");
    }

    // COB:            88  WS-INFORM-OUTPUT                    VALUE
    public boolean wsInformOutput() {
      return wsInfoMsg.equals("Displaying details of given Account");
    }

    public void setWsInformOutput(boolean value) {
      if (value) wsInfoMsg.setValue("Displaying details of given Account");
    }

    // COB:          05  WS-RETURN-MSG                         PIC X(75).
    public NChar wsReturnMsg = new NChar(75);

    // COB:            88  WS-RETURN-MSG-OFF                   VALUE SPACES.
    public boolean wsReturnMsgOff() {
      return wsReturnMsg.isSpaces();
    }

    public void setWsReturnMsgOff(boolean value) {
      if (value) wsReturnMsg.setValue(SPACES);
    }

    // COB:            88  WS-EXIT-MESSAGE                     VALUE
    public boolean wsExitMessage() {
      return wsReturnMsg.equals("PF03 pressed.Exiting              ");
    }

    public void setWsExitMessage(boolean value) {
      if (value) wsReturnMsg.setValue("PF03 pressed.Exiting              ");
    }

    // COB:            88  WS-PROMPT-FOR-ACCT                  VALUE
    public boolean wsPromptForAcct() {
      return wsReturnMsg.equals("Account number not provided");
    }

    public void setWsPromptForAcct(boolean value) {
      if (value) wsReturnMsg.setValue("Account number not provided");
    }

    // COB:            88  NO-SEARCH-CRITERIA-RECEIVED         VALUE
    public boolean noSearchCriteriaReceived() {
      return wsReturnMsg.equals("No input received");
    }

    public void setNoSearchCriteriaReceived(boolean value) {
      if (value) wsReturnMsg.setValue("No input received");
    }

    // COB:            88  SEARCHED-ACCT-ZEROES                VALUE
    public boolean searchedAcctZeroes() {
      return wsReturnMsg.equals("Account number must be a non zero 11 digit number");
    }

    public void setSearchedAcctZeroes(boolean value) {
      if (value) wsReturnMsg.setValue("Account number must be a non zero 11 digit number");
    }

    // COB:            88  SEARCHED-ACCT-NOT-NUMERIC           VALUE
    public boolean searchedAcctNotNumeric() {
      return wsReturnMsg.equals("Account number must be a non zero 11 digit number");
    }

    public void setSearchedAcctNotNumeric(boolean value) {
      if (value) wsReturnMsg.setValue("Account number must be a non zero 11 digit number");
    }

    // COB:            88  DID-NOT-FIND-ACCT-IN-CARDXREF       VALUE
    public boolean didNotFindAcctInCardxref() {
      return wsReturnMsg.equals("Did not find this account in account card xref file");
    }

    public void setDidNotFindAcctInCardxref(boolean value) {
      if (value) wsReturnMsg.setValue("Did not find this account in account card xref file");
    }

    // COB:            88  DID-NOT-FIND-ACCT-IN-ACCTDAT        VALUE
    public boolean didNotFindAcctInAcctdat() {
      return wsReturnMsg.equals("Did not find this account in account master file");
    }

    public void setDidNotFindAcctInAcctdat(boolean value) {
      if (value) wsReturnMsg.setValue("Did not find this account in account master file");
    }

    // COB:            88  DID-NOT-FIND-CUST-IN-CUSTDAT        VALUE
    public boolean didNotFindCustInCustdat() {
      return wsReturnMsg.equals("Did not find associated customer in master file");
    }

    public void setDidNotFindCustInCustdat(boolean value) {
      if (value) wsReturnMsg.setValue("Did not find associated customer in master file");
    }

    // COB:            88  XREF-READ-ERROR                     VALUE
    public boolean xrefReadError() {
      return wsReturnMsg.equals("Error reading account card xref File");
    }

    public void setXrefReadError(boolean value) {
      if (value) wsReturnMsg.setValue("Error reading account card xref File");
    }

    // COB:            88  CODING-TO-BE-DONE                   VALUE
    public boolean codingToBeDone() {
      return wsReturnMsg.equals("Looks Good.... so far");
    }

    public void setCodingToBeDone(boolean value) {
      if (value) wsReturnMsg.setValue("Looks Good.... so far");
    }
  }

  // COB:        01 WS-LITERALS.
  public WsLiterals wsLiterals = new WsLiterals();

  public static class WsLiterals extends NGroup {

    // COB:           05 LIT-THISPGM                           PIC X(8)
    // COB:                                                    VALUE 'COACTVWC'.
    public NChar litThispgm = new NChar(8).initial("COACTVWC");
    // COB:           05 LIT-THISTRANID                        PIC X(4)
    // COB:                                                    VALUE 'CAVW'.
    public NChar litThistranid = new NChar(4).initial("CAVW");
    // COB:           05 LIT-THISMAPSET                        PIC X(8)
    // COB:                                                    VALUE 'COACTVW '.
    public NChar litThismapset = new NChar(8).initial("COACTVW ");
    // COB:           05 LIT-THISMAP                           PIC X(7)
    // COB:                                                    VALUE 'CACTVWA'.
    public NChar litThismap = new NChar(7).initial("CACTVWA");
    // COB:           05 LIT-CCLISTPGM                         PIC X(8)
    // COB:                                                    VALUE 'COCRDLIC'.
    public NChar litCclistpgm = new NChar(8).initial("COCRDLIC");
    // COB:           05 LIT-CCLISTTRANID                      PIC X(4)
    // COB:                                                    VALUE 'CCLI'.
    public NChar litCclisttranid = new NChar(4).initial("CCLI");
    // COB:           05 LIT-CCLISTMAPSET                      PIC X(7)
    // COB:                                                    VALUE 'COCRDLI'.
    public NChar litCclistmapset = new NChar(7).initial("COCRDLI");
    // COB:           05 LIT-CCLISTMAP                         PIC X(7)
    // COB:                                                    VALUE 'CCRDSLA'.
    public NChar litCclistmap = new NChar(7).initial("CCRDSLA");
    // COB:           05 LIT-CARDUPDATEPGM                           PIC X(8)
    // COB:                                                    VALUE 'COCRDUPC'.
    public NChar litCardupdatepgm = new NChar(8).initial("COCRDUPC");
    // COB:           05 LIT-CARDUDPATETRANID                        PIC X(4)
    // COB:                                                    VALUE 'CCUP'.
    public NChar litCardudpatetranid = new NChar(4).initial("CCUP");
    // COB:           05 LIT-CARDUPDATEMAPSET                        PIC X(8)
    // COB:                                                    VALUE 'COCRDUP '.
    public NChar litCardupdatemapset = new NChar(8).initial("COCRDUP ");
    // COB:           05 LIT-CARDUPDATEMAP                           PIC X(7)
    // COB:                                                    VALUE 'CCRDUPA'.
    public NChar litCardupdatemap = new NChar(7).initial("CCRDUPA");
    // COB:           05 LIT-MENUPGM                           PIC X(8)
    // COB:                                                    VALUE 'COMEN01C'.
    public NChar litMenupgm = new NChar(8).initial("COMEN01C");
    // COB:           05 LIT-MENUTRANID                        PIC X(4)
    // COB:                                                    VALUE 'CM00'.
    public NChar litMenutranid = new NChar(4).initial("CM00");
    // COB:           05 LIT-MENUMAPSET                        PIC X(7)
    // COB:                                                    VALUE 'COMEN01'.
    public NChar litMenumapset = new NChar(7).initial("COMEN01");
    // COB:           05 LIT-MENUMAP                           PIC X(7)
    // COB:                                                    VALUE 'COMEN1A'.
    public NChar litMenumap = new NChar(7).initial("COMEN1A");
    // COB:           05  LIT-CARDDTLPGM                       PIC X(8)
    // COB:                                                    VALUE 'COCRDSLC'.
    public NChar litCarddtlpgm = new NChar(8).initial("COCRDSLC");
    // COB:           05  LIT-CARDDTLTRANID                    PIC X(4)
    // COB:                                                    VALUE 'CCDL'.
    public NChar litCarddtltranid = new NChar(4).initial("CCDL");
    // COB:           05  LIT-CARDDTLMAPSET                    PIC X(7)
    // COB:                                                    VALUE 'COCRDSL'.
    public NChar litCarddtlmapset = new NChar(7).initial("COCRDSL");
    // COB:           05  LIT-CARDDTLMAP                       PIC X(7)
    // COB:                                                    VALUE 'CCRDSLA'.
    public NChar litCarddtlmap = new NChar(7).initial("CCRDSLA");
    // COB:           05 LIT-ACCTFILENAME                      PIC X(8)
    // COB:                                                    VALUE 'ACCTDAT '.
    public NChar litAcctfilename = new NChar(8).initial("ACCTDAT ");
    // COB:           05 LIT-CARDFILENAME                      PIC X(8)
    // COB:                                                    VALUE 'CARDDAT '.
    public NChar litCardfilename = new NChar(8).initial("CARDDAT ");
    // COB:           05 LIT-CUSTFILENAME                      PIC X(8)
    // COB:                                                    VALUE 'CUSTDAT '.
    public NChar litCustfilename = new NChar(8).initial("CUSTDAT ");
    // COB:           05 LIT-CARDFILENAME-ACCT-PATH            PIC X(8)
    // COB:                                                    VALUE 'CARDAIX '.
    public NChar litCardfilenameAcctPath = new NChar(8).initial("CARDAIX ");
    // COB:           05 LIT-CARDXREFNAME-ACCT-PATH            PIC X(8)
    // COB:                                                    VALUE 'CXACAIX '.
    public NChar litCardxrefnameAcctPath = new NChar(8).initial("CXACAIX ");
    // COB:           05 LIT-ALL-ALPHA-FROM                    PIC X(52)
    // COB:              VALUE
    // COB:              'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.
    public NChar litAllAlphaFrom =
        new NChar(52).initial("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    // COB:           05 LIT-ALL-SPACES-TO                     PIC X(52)
    // COB:                                                    VALUE SPACES.
    public NChar litAllSpacesTo = new NChar(52).initial(NChar.Space);
    // COB:           05 LIT-UPPER                             PIC X(26)
    // COB:                                  VALUE 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.
    public NChar litUpper = new NChar(26).initial("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    // COB:           05 LIT-LOWER                             PIC X(26)
    // COB:                                  VALUE 'abcdefghijklmnopqrstuvwxyz'.
    public NChar litLower = new NChar(26).initial("abcdefghijklmnopqrstuvwxyz");
  }

  public Cvcrd01y cvcrd01y = new Cvcrd01y();
  public Cocom01y cocom01y = new Cocom01y();
  // COB:        01 WS-THIS-PROGCOMMAREA.
  public WsThisProgcommarea wsThisProgcommarea = new WsThisProgcommarea();

  public static class WsThisProgcommarea extends NGroup {

    // COB:           05 CA-CALL-CONTEXT.
    public static class CaCallContext extends NGroup {
      // COB:              10 CA-FROM-PROGRAM                    PIC X(08).
      public NChar caFromProgram = new NChar(8);
      // COB:              10 CA-FROM-TRANID                     PIC X(04).
      public NChar caFromTranid = new NChar(4);
    }

    public CaCallContext caCallContext = new CaCallContext();
  }

  // COB:        01  WS-COMMAREA                             PIC X(2000).
  public NChar wsCommarea = new NChar(2000);
  public Cottl01y cottl01y = new Cottl01y();
  public Coactvw coactvw = new Coactvw();
  public Csdat01y csdat01y = new Csdat01y();
  public Csmsg01y csmsg01y = new Csmsg01y();
  public Csmsg02y csmsg02y = new Csmsg02y();
  public Csusr01y csusr01y = new Csusr01y();
  public Cvact01y cvact01y = new Cvact01y();
  public Cvact02y cvact02y = new Cvact02y();
  public Cvact03y cvact03y = new Cvact03y();
  public Cvcus01y cvcus01y = new Cvcus01y();
}
