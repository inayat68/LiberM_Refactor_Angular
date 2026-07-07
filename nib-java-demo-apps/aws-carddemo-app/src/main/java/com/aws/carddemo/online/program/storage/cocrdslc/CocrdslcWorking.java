package com.aws.carddemo.online.program.storage.cocrdslc;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Cocom01y;
import com.aws.carddemo.common.copybook.Cocrdsl;
import com.aws.carddemo.common.copybook.Cottl01y;
import com.aws.carddemo.common.copybook.Csdat01y;
import com.aws.carddemo.common.copybook.Csmsg01y;
import com.aws.carddemo.common.copybook.Csmsg02y;
import com.aws.carddemo.common.copybook.Csusr01y;
import com.aws.carddemo.common.copybook.Cvact02y;
import com.aws.carddemo.common.copybook.Cvcrd01y;
import com.aws.carddemo.common.copybook.Cvcus01y;
import com.nib.commons.storage.*;

public class CocrdslcWorking extends NGroup {
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
    public boolean inputPending() {
      return wsInputFlag.isLowValues();
    }

    public void setInputPending(boolean value) {
      if (value) wsInputFlag.lowValues();
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

    // COB:          05  WS-EDIT-CARD-FLAG                     PIC X(1).
    public NChar wsEditCardFlag = new NChar(1);

    // COB:            88  FLG-CARDFILTER-NOT-OK               VALUE '0'.
    public boolean flgCardfilterNotOk() {
      return wsEditCardFlag.equals("0");
    }

    public void setFlgCardfilterNotOk(boolean value) {
      if (value) wsEditCardFlag.setValue("0");
    }

    // COB:            88  FLG-CARDFILTER-ISVALID             VALUE '1'.
    public boolean flgCardfilterIsvalid() {
      return wsEditCardFlag.equals("1");
    }

    public void setFlgCardfilterIsvalid(boolean value) {
      if (value) wsEditCardFlag.setValue("1");
    }

    // COB:            88  FLG-CARDFILTER-BLANK                VALUE ' '.
    public boolean flgCardfilterBlank() {
      return wsEditCardFlag.isSpaces();
    }

    public void setFlgCardfilterBlank(boolean value) {
      if (value) wsEditCardFlag.setValue(" ");
    }

    // COB:          05  WS-RETURN-FLAG                        PIC X(1).
    public NChar wsReturnFlag = new NChar(1);

    // COB:            88  WS-RETURN-FLAG-OFF                  VALUE LOW-VALUES.
    public boolean wsReturnFlagOff() {
      return wsReturnFlag.isLowValues();
    }

    public void setWsReturnFlagOff(boolean value) {
      if (value) wsReturnFlag.lowValues();
    }

    // COB:            88  WS-RETURN-FLAG-ON                   VALUE '1'.
    public boolean wsReturnFlagOn() {
      return wsReturnFlag.equals("1");
    }

    public void setWsReturnFlagOn(boolean value) {
      if (value) wsReturnFlag.setValue("1");
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

    // COB:          05 CICS-OUTPUT-EDIT-VARS.
    public static class CicsOutputEditVars extends NGroup {
      // COB:            10  CARD-ACCT-ID-X                      PIC X(11).
      public NChar cardAcctIdX = new NChar(11);
      // COB:            10  CARD-ACCT-ID-N REDEFINES CARD-ACCT-ID-X
      public NZoned cardAcctIdN = new NZoned(11, false).redefines(cardAcctIdX);
      // COB:            10  CARD-CVV-CD-X                       PIC X(03).
      public NChar cardCvvCdX = new NChar(3);
      // COB:            10  CARD-CVV-CD-N REDEFINES  CARD-CVV-CD-X
      public NZoned cardCvvCdN = new NZoned(3, false).redefines(cardCvvCdX);
      // COB:            10  CARD-CARD-NUM-X                     PIC X(16).
      public NChar cardCardNumX = new NChar(16);
      // COB:            10  CARD-CARD-NUM-N REDEFINES  CARD-CARD-NUM-X
      public NZoned cardCardNumN = new NZoned(16, false).redefines(cardCardNumX);
      // COB:            10  CARD-NAME-EMBOSSED-X                PIC X(50).
      public NChar cardNameEmbossedX = new NChar(50);
      // COB:            10  CARD-STATUS-X                       PIC X.
      public NChar cardStatusX = new NChar(1);
      // COB:            10  CARD-EXPIRAION-DATE-X               PIC X(10).
      public NChar cardExpiraionDateX = new NChar(10);

      // COB:            10  FILLER REDEFINES CARD-EXPIRAION-DATE-X.
      public static class Filler85 extends NGroup {
        // COB:                20 CARD-EXPIRY-YEAR                 PIC X(4).
        public NChar cardExpiryYear = new NChar(4);
        // COB:                20 FILLER                           PIC X(1).
        public NChar filler87 = new NChar(1);
        // COB:                20 CARD-EXPIRY-MONTH                PIC X(2).
        public NChar cardExpiryMonth = new NChar(2);
        // COB:                20 FILLER                           PIC X(1).
        public NChar filler89 = new NChar(1);
        // COB:                20 CARD-EXPIRY-DAY                  PIC X(2).
        public NChar cardExpiryDay = new NChar(2);
      }

      public Filler85 filler85 = (Filler85) new Filler85().redefines(cardExpiraionDateX);
      // COB:            10  CARD-EXPIRAION-DATE-N REDEFINES
      // COB:                CARD-EXPIRAION-DATE-X               PIC 9(10).
      public NZoned cardExpiraionDateN = new NZoned(10, false).redefines(cardExpiraionDateX);
    }

    public CicsOutputEditVars cicsOutputEditVars = new CicsOutputEditVars();

    // COB:          05  WS-CARD-RID.
    public static class WsCardRid extends NGroup {
      // COB:            10  WS-CARD-RID-CARDNUM                 PIC X(16).
      public NChar wsCardRidCardnum = new NChar(16);
      // COB:            10  WS-CARD-RID-ACCT-ID                 PIC 9(11).
      public NZoned wsCardRidAcctId = new NZoned(11, false);
      // COB:            10  WS-CARD-RID-ACCT-ID-X REDEFINES
      // COB:                   WS-CARD-RID-ACCT-ID              PIC X(11).
      public NChar wsCardRidAcctIdX = new NChar(11).redefines(wsCardRidAcctId);
    }

    public WsCardRid wsCardRid = new WsCardRid();

    // COB:          05  WS-FILE-ERROR-MESSAGE.
    public static class WsFileErrorMessage extends NGroup {
      // COB:            10  FILLER                              PIC X(12)
      // COB:                                                    VALUE 'File Error: '.
      public NChar filler103 = new NChar(12).initial("File Error: ");
      // COB:            10  ERROR-OPNAME                        PIC X(8)
      // COB:                                                    VALUE SPACES.
      public NChar errorOpname = new NChar(8).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(4)
      // COB:                                                    VALUE ' on '.
      public NChar filler107 = new NChar(4).initial(" on ");
      // COB:            10  ERROR-FILE                          PIC X(9)
      // COB:                                                    VALUE SPACES.
      public NChar errorFile = new NChar(9).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(15)
      // COB:                                                    VALUE
      // COB:                                                    ' returned RESP '.
      public NChar filler111 = new NChar(15).initial(" returned RESP ");
      // COB:            10  ERROR-RESP                          PIC X(10)
      // COB:                                                    VALUE SPACES.
      public NChar errorResp = new NChar(10).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(7)
      // COB:                                                    VALUE ',RESP2 '.
      public NChar filler116 = new NChar(7).initial(",RESP2 ");
      // COB:            10  ERROR-RESP2                         PIC X(10)
      // COB:                                                    VALUE SPACES.
      public NChar errorResp2 = new NChar(10).initial(NChar.Space);
      // COB:           10  FILLER                               PIC X(5)
      // COB:                                                    VALUE SPACES.
      public NChar filler120 = new NChar(5).initial(NChar.Space);
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

    // COB:            88  FOUND-CARDS-FOR-ACCOUNT             VALUE
    public boolean foundCardsForAccount() {
      return wsInfoMsg.equals("   Displaying requested details");
    }

    public void setFoundCardsForAccount(boolean value) {
      if (value) wsInfoMsg.setValue("   Displaying requested details");
    }

    // COB:            88  WS-PROMPT-FOR-INPUT                 VALUE
    public boolean wsPromptForInput() {
      return wsInfoMsg.equals("Please enter Account and Card Number");
    }

    public void setWsPromptForInput(boolean value) {
      if (value) wsInfoMsg.setValue("Please enter Account and Card Number");
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

    // COB:            88  WS-PROMPT-FOR-CARD                  VALUE
    public boolean wsPromptForCard() {
      return wsReturnMsg.equals("Card number not provided");
    }

    public void setWsPromptForCard(boolean value) {
      if (value) wsReturnMsg.setValue("Card number not provided");
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

    // COB:            88  SEARCHED-CARD-NOT-NUMERIC           VALUE
    public boolean searchedCardNotNumeric() {
      return wsReturnMsg.equals("Card number if supplied must be a 16 digit number");
    }

    public void setSearchedCardNotNumeric(boolean value) {
      if (value) wsReturnMsg.setValue("Card number if supplied must be a 16 digit number");
    }

    // COB:            88  DID-NOT-FIND-ACCT-IN-CARDXREF       VALUE
    public boolean didNotFindAcctInCardxref() {
      return wsReturnMsg.equals("Did not find this account in cards database");
    }

    public void setDidNotFindAcctInCardxref(boolean value) {
      if (value) wsReturnMsg.setValue("Did not find this account in cards database");
    }

    // COB:            88  DID-NOT-FIND-ACCTCARD-COMBO         VALUE
    public boolean didNotFindAcctcardCombo() {
      return wsReturnMsg.equals("Did not find cards for this search condition");
    }

    public void setDidNotFindAcctcardCombo(boolean value) {
      if (value) wsReturnMsg.setValue("Did not find cards for this search condition");
    }

    // COB:            88  XREF-READ-ERROR                     VALUE
    public boolean xrefReadError() {
      return wsReturnMsg.equals("Error reading Card Data File");
    }

    public void setXrefReadError(boolean value) {
      if (value) wsReturnMsg.setValue("Error reading Card Data File");
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
    // COB:                                                    VALUE 'COCRDSLC'.
    public NChar litThispgm = new NChar(8).initial("COCRDSLC");
    // COB:           05 LIT-THISTRANID                        PIC X(4)
    // COB:                                                    VALUE 'CCDL'.
    public NChar litThistranid = new NChar(4).initial("CCDL");
    // COB:           05 LIT-THISMAPSET                        PIC X(8)
    // COB:                                                    VALUE 'COCRDSL '.
    public NChar litThismapset = new NChar(8).initial("COCRDSL ");
    // COB:           05 LIT-THISMAP                           PIC X(7)
    // COB:                                                    VALUE 'CCRDSLA'.
    public NChar litThismap = new NChar(7).initial("CCRDSLA");
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
    // COB:           05 LIT-CARDFILENAME                      PIC X(8)
    // COB:                                                    VALUE 'CARDDAT '.
    public NChar litCardfilename = new NChar(8).initial("CARDDAT ");
    // COB:           05 LIT-CARDFILENAME-ACCT-PATH            PIC X(8)
    // COB:                                                    VALUE 'CARDAIX '.
    public NChar litCardfilenameAcctPath = new NChar(8).initial("CARDAIX ");
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
  public Cocrdsl cocrdsl = new Cocrdsl();
  public Csdat01y csdat01y = new Csdat01y();
  public Csmsg01y csmsg01y = new Csmsg01y();
  public Csmsg02y csmsg02y = new Csmsg02y();
  public Csusr01y csusr01y = new Csusr01y();
  public Cvact02y cvact02y = new Cvact02y();
  public Cvcus01y cvcus01y = new Cvcus01y();
}
