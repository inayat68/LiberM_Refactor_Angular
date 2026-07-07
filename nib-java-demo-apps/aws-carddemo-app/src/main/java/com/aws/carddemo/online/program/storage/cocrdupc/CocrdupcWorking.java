package com.aws.carddemo.online.program.storage.cocrdupc;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Cocom01y;
import com.aws.carddemo.common.copybook.Cocrdup;
import com.aws.carddemo.common.copybook.Cottl01y;
import com.aws.carddemo.common.copybook.Csdat01y;
import com.aws.carddemo.common.copybook.Csmsg01y;
import com.aws.carddemo.common.copybook.Csmsg02y;
import com.aws.carddemo.common.copybook.Csusr01y;
import com.aws.carddemo.common.copybook.Cvact02y;
import com.aws.carddemo.common.copybook.Cvcrd01y;
import com.aws.carddemo.common.copybook.Cvcus01y;
import com.nib.commons.storage.*;

public class CocrdupcWorking extends NGroup {
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
      // COB:             07 WS-UCTRANS                          PIC X(4)
      // COB:                                                    VALUE SPACES.
      public NChar wsUctrans = new NChar(4).initial(NChar.Space);
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

    // COB:          05  WS-EDIT-CARDNAME-FLAG                 PIC X(1).
    public NChar wsEditCardnameFlag = new NChar(1);

    // COB:            88  FLG-CARDNAME-NOT-OK                 VALUE '0'.
    public boolean flgCardnameNotOk() {
      return wsEditCardnameFlag.equals("0");
    }

    public void setFlgCardnameNotOk(boolean value) {
      if (value) wsEditCardnameFlag.setValue("0");
    }

    // COB:            88  FLG-CARDNAME-ISVALID                VALUE '1'.
    public boolean flgCardnameIsvalid() {
      return wsEditCardnameFlag.equals("1");
    }

    public void setFlgCardnameIsvalid(boolean value) {
      if (value) wsEditCardnameFlag.setValue("1");
    }

    // COB:            88  FLG-CARDNAME-BLANK                  VALUE ' '.
    public boolean flgCardnameBlank() {
      return wsEditCardnameFlag.isSpaces();
    }

    public void setFlgCardnameBlank(boolean value) {
      if (value) wsEditCardnameFlag.setValue(" ");
    }

    // COB:          05  WS-EDIT-CARDSTATUS-FLAG              PIC X(1).
    public NChar wsEditCardstatusFlag = new NChar(1);

    // COB:            88  FLG-CARDSTATUS-NOT-OK               VALUE '0'.
    public boolean flgCardstatusNotOk() {
      return wsEditCardstatusFlag.equals("0");
    }

    public void setFlgCardstatusNotOk(boolean value) {
      if (value) wsEditCardstatusFlag.setValue("0");
    }

    // COB:            88  FLG-CARDSTATUS-ISVALID              VALUE '1'.
    public boolean flgCardstatusIsvalid() {
      return wsEditCardstatusFlag.equals("1");
    }

    public void setFlgCardstatusIsvalid(boolean value) {
      if (value) wsEditCardstatusFlag.setValue("1");
    }

    // COB:            88  FLG-CARDSTATUS-BLANK                VALUE ' '.
    public boolean flgCardstatusBlank() {
      return wsEditCardstatusFlag.isSpaces();
    }

    public void setFlgCardstatusBlank(boolean value) {
      if (value) wsEditCardstatusFlag.setValue(" ");
    }

    // COB:          05  WS-EDIT-CARDEXPMON-FLAG              PIC X(1).
    public NChar wsEditCardexpmonFlag = new NChar(1);

    // COB:            88  FLG-CARDEXPMON-NOT-OK               VALUE '0'.
    public boolean flgCardexpmonNotOk() {
      return wsEditCardexpmonFlag.equals("0");
    }

    public void setFlgCardexpmonNotOk(boolean value) {
      if (value) wsEditCardexpmonFlag.setValue("0");
    }

    // COB:            88  FLG-CARDEXPMON-ISVALID              VALUE '1'.
    public boolean flgCardexpmonIsvalid() {
      return wsEditCardexpmonFlag.equals("1");
    }

    public void setFlgCardexpmonIsvalid(boolean value) {
      if (value) wsEditCardexpmonFlag.setValue("1");
    }

    // COB:            88  FLG-CARDEXPMON-BLANK                VALUE ' '.
    public boolean flgCardexpmonBlank() {
      return wsEditCardexpmonFlag.isSpaces();
    }

    public void setFlgCardexpmonBlank(boolean value) {
      if (value) wsEditCardexpmonFlag.setValue(" ");
    }

    // COB:          05  WS-EDIT-CARDEXPYEAR-FLAG             PIC X(1).
    public NChar wsEditCardexpyearFlag = new NChar(1);

    // COB:            88  FLG-CARDEXPYEAR-NOT-OK              VALUE '0'.
    public boolean flgCardexpyearNotOk() {
      return wsEditCardexpyearFlag.equals("0");
    }

    public void setFlgCardexpyearNotOk(boolean value) {
      if (value) wsEditCardexpyearFlag.setValue("0");
    }

    // COB:            88  FLG-CARDEXPYEAR-ISVALID             VALUE '1'.
    public boolean flgCardexpyearIsvalid() {
      return wsEditCardexpyearFlag.equals("1");
    }

    public void setFlgCardexpyearIsvalid(boolean value) {
      if (value) wsEditCardexpyearFlag.setValue("1");
    }

    // COB:            88  FLG-CARDEXPYEAR-BLANK               VALUE ' '.
    public boolean flgCardexpyearBlank() {
      return wsEditCardexpyearFlag.isSpaces();
    }

    public void setFlgCardexpyearBlank(boolean value) {
      if (value) wsEditCardexpyearFlag.setValue(" ");
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

    // COB:          05  CARD-NAME-CHECK                       PIC X(50)
    // COB:                                                    VALUE LOW-VALUES.
    public NChar cardNameCheck = new NChar(50).initial(NChar.LowValue);
    // COB:          05  FLG-YES-NO-CHECK                      PIC X(1)
    // COB:                                                    VALUE 'N'.
    public NChar flgYesNoCheck = new NChar(1).initial("N");

    // COB:            88 FLG-YES-NO-VALID                     VALUES 'Y', 'N'.
    public boolean flgYesNoValid() {
      return flgYesNoCheck.equals("Y") || flgYesNoCheck.equals("N");
    }

    public void setFlgYesNoValid(boolean value) {
      if (value) flgYesNoCheck.setValue("Y");
    }

    // COB:          05  CARD-MONTH-CHECK                      PIC X(2).
    public NChar cardMonthCheck = new NChar(2);
    // COB:          05  CARD-MONTH-CHECK-N REDEFINES
    // COB:              CARD-MONTH-CHECK                      PIC 9(2).
    public NZoned cardMonthCheckN = new NZoned(2, false).redefines(cardMonthCheck);

    // COB:              88 VALID-MONTH                        VALUES 1 THRU 12.
    public boolean validMonth() {
      return cardMonthCheckN.inRange(1, 12);
    }

    public void setValidMonth(boolean value) {
      if (value) cardMonthCheckN.setValue(1);
    }

    // COB:          05  CARD-YEAR-CHECK                      PIC X(4).
    public NChar cardYearCheck = new NChar(4);
    // COB:          05  CARD-YEAR-CHECK-N REDEFINES
    // COB:              CARD-YEAR-CHECK                      PIC 9(4).
    public NZoned cardYearCheckN = new NZoned(4, false).redefines(cardYearCheck);

    // COB:              88 VALID-YEAR                        VALUES 1950 THRU 2099.
    public boolean validYear() {
      return cardYearCheckN.inRange(1950, 2099);
    }

    public void setValidYear(boolean value) {
      if (value) cardYearCheckN.setValue(1950);
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
      public static class Filler116 extends NGroup {
        // COB:                20 CARD-EXPIRY-YEAR                 PIC X(4).
        public NChar cardExpiryYear = new NChar(4);
        // COB:                20 FILLER                           PIC X(1).
        public NChar filler118 = new NChar(1);
        // COB:                20 CARD-EXPIRY-MONTH                PIC X(2).
        public NChar cardExpiryMonth = new NChar(2);
        // COB:                20 FILLER                           PIC X(1).
        public NChar filler120 = new NChar(1);
        // COB:                20 CARD-EXPIRY-DAY                  PIC X(2).
        public NChar cardExpiryDay = new NChar(2);
      }

      public Filler116 filler116 = (Filler116) new Filler116().redefines(cardExpiraionDateX);
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
      public NChar filler134 = new NChar(12).initial("File Error: ");
      // COB:            10  ERROR-OPNAME                        PIC X(8)
      // COB:                                                    VALUE SPACES.
      public NChar errorOpname = new NChar(8).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(4)
      // COB:                                                    VALUE ' on '.
      public NChar filler138 = new NChar(4).initial(" on ");
      // COB:            10  ERROR-FILE                          PIC X(9)
      // COB:                                                    VALUE SPACES.
      public NChar errorFile = new NChar(9).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(15)
      // COB:                                                    VALUE
      // COB:                                                    ' returned RESP '.
      public NChar filler142 = new NChar(15).initial(" returned RESP ");
      // COB:            10  ERROR-RESP                          PIC X(10)
      // COB:                                                    VALUE SPACES.
      public NChar errorResp = new NChar(10).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(7)
      // COB:                                                    VALUE ',RESP2 '.
      public NChar filler147 = new NChar(7).initial(",RESP2 ");
      // COB:            10  ERROR-RESP2                         PIC X(10)
      // COB:                                                    VALUE SPACES.
      public NChar errorResp2 = new NChar(10).initial(NChar.Space);
      // COB:           10  FILLER                               PIC X(5)
      // COB:                                                    VALUE SPACES.
      public NChar filler151 = new NChar(5).initial(NChar.Space);
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
      return wsInfoMsg.equals("Details of selected card shown above");
    }

    public void setFoundCardsForAccount(boolean value) {
      if (value) wsInfoMsg.setValue("Details of selected card shown above");
    }

    // COB:            88  PROMPT-FOR-SEARCH-KEYS              VALUE
    public boolean promptForSearchKeys() {
      return wsInfoMsg.equals("Please enter Account and Card Number");
    }

    public void setPromptForSearchKeys(boolean value) {
      if (value) wsInfoMsg.setValue("Please enter Account and Card Number");
    }

    // COB:            88  PROMPT-FOR-CHANGES                  VALUE
    public boolean promptForChanges() {
      return wsInfoMsg.equals("Update card details presented above.");
    }

    public void setPromptForChanges(boolean value) {
      if (value) wsInfoMsg.setValue("Update card details presented above.");
    }

    // COB:            88  PROMPT-FOR-CONFIRMATION             VALUE
    public boolean promptForConfirmation() {
      return wsInfoMsg.equals("Changes validated.Press F5 to save");
    }

    public void setPromptForConfirmation(boolean value) {
      if (value) wsInfoMsg.setValue("Changes validated.Press F5 to save");
    }

    // COB:            88  CONFIRM-UPDATE-SUCCESS              VALUE
    public boolean confirmUpdateSuccess() {
      return wsInfoMsg.equals("Changes committed to database");
    }

    public void setConfirmUpdateSuccess(boolean value) {
      if (value) wsInfoMsg.setValue("Changes committed to database");
    }

    // COB:            88  INFORM-FAILURE                      VALUE
    public boolean informFailure() {
      return wsInfoMsg.equals("Changes unsuccessful. Please try again");
    }

    public void setInformFailure(boolean value) {
      if (value) wsInfoMsg.setValue("Changes unsuccessful. Please try again");
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

    // COB:            88  WS-PROMPT-FOR-NAME                  VALUE
    public boolean wsPromptForName() {
      return wsReturnMsg.equals("Card name not provided");
    }

    public void setWsPromptForName(boolean value) {
      if (value) wsReturnMsg.setValue("Card name not provided");
    }

    // COB:            88  WS-NAME-MUST-BE-ALPHA               VALUE
    public boolean wsNameMustBeAlpha() {
      return wsReturnMsg.equals("Card name can only contain alphabets and spaces");
    }

    public void setWsNameMustBeAlpha(boolean value) {
      if (value) wsReturnMsg.setValue("Card name can only contain alphabets and spaces");
    }

    // COB:            88  NO-SEARCH-CRITERIA-RECEIVED         VALUE
    public boolean noSearchCriteriaReceived() {
      return wsReturnMsg.equals("No input received");
    }

    public void setNoSearchCriteriaReceived(boolean value) {
      if (value) wsReturnMsg.setValue("No input received");
    }

    // COB:            88  NO-CHANGES-DETECTED                 VALUE
    public boolean noChangesDetected() {
      return wsReturnMsg.equals("No change detected with respect to values fetched.");
    }

    public void setNoChangesDetected(boolean value) {
      if (value) wsReturnMsg.setValue("No change detected with respect to values fetched.");
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

    // COB:            88  CARD-STATUS-MUST-BE-YES-NO          VALUE
    public boolean cardStatusMustBeYesNo() {
      return wsReturnMsg.equals("Card Active Status must be Y or N");
    }

    public void setCardStatusMustBeYesNo(boolean value) {
      if (value) wsReturnMsg.setValue("Card Active Status must be Y or N");
    }

    // COB:            88  CARD-EXPIRY-MONTH-NOT-VALID          VALUE
    public boolean cardExpiryMonthNotValid() {
      return wsReturnMsg.equals("Card expiry month must be between 1 and 12");
    }

    public void setCardExpiryMonthNotValid(boolean value) {
      if (value) wsReturnMsg.setValue("Card expiry month must be between 1 and 12");
    }

    // COB:            88  CARD-EXPIRY-YEAR-NOT-VALID          VALUE
    public boolean cardExpiryYearNotValid() {
      return wsReturnMsg.equals("Invalid card expiry year");
    }

    public void setCardExpiryYearNotValid(boolean value) {
      if (value) wsReturnMsg.setValue("Invalid card expiry year");
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

    // COB:            88  COULD-NOT-LOCK-FOR-UPDATE           VALUE
    public boolean couldNotLockForUpdate() {
      return wsReturnMsg.equals("Could not lock record for update");
    }

    public void setCouldNotLockForUpdate(boolean value) {
      if (value) wsReturnMsg.setValue("Could not lock record for update");
    }

    // COB:            88  DATA-WAS-CHANGED-BEFORE-UPDATE      VALUE
    public boolean dataWasChangedBeforeUpdate() {
      return wsReturnMsg.equals("Record changed by some one else. Please review");
    }

    public void setDataWasChangedBeforeUpdate(boolean value) {
      if (value) wsReturnMsg.setValue("Record changed by some one else. Please review");
    }

    // COB:            88  LOCKED-BUT-UPDATE-FAILED            VALUE
    public boolean lockedButUpdateFailed() {
      return wsReturnMsg.equals("Update of record failed");
    }

    public void setLockedButUpdateFailed(boolean value) {
      if (value) wsReturnMsg.setValue("Update of record failed");
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
    // COB:                                                    VALUE 'COCRDUPC'.
    public NChar litThispgm = new NChar(8).initial("COCRDUPC");
    // COB:           05 LIT-THISTRANID                        PIC X(4)
    // COB:                                                    VALUE 'CCUP'.
    public NChar litThistranid = new NChar(4).initial("CCUP");
    // COB:           05 LIT-THISMAPSET                        PIC X(8)
    // COB:                                                    VALUE 'COCRDUP '.
    public NChar litThismapset = new NChar(8).initial("COCRDUP ");
    // COB:           05 LIT-THISMAP                           PIC X(7)
    // COB:                                                    VALUE 'CCRDUPA'.
    public NChar litThismap = new NChar(7).initial("CCRDUPA");
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
    // COB:           05 LIT-CARDFILENAME                      PIC X(8)
    // COB:                                                    VALUE 'CARDDAT '.
    public NChar litCardfilename = new NChar(8).initial("CARDDAT ");
    // COB:           05 LIT-CARDFILENAME-ACCT-PATH            PIC X(8)
    // COB:                                                    VALUE 'CARDAIX '.
    public NChar litCardfilenameAcctPath = new NChar(8).initial("CARDAIX ");
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

    // COB:           05 CARD-UPDATE-SCREEN-DATA.
    public static class CardUpdateScreenData extends NGroup {
      // COB:              10 CCUP-CHANGE-ACTION                 PIC X(1)
      // COB:                                                    VALUE LOW-VALUES.
      public NChar ccupChangeAction = new NChar(1).initial(NChar.LowValue);

      // COB:                 88 CCUP-DETAILS-NOT-FETCHED        VALUES
      public boolean ccupDetailsNotFetched() {
        return ccupChangeAction.isLowValues() || ccupChangeAction.isSpaces();
      }

      public void setCcupDetailsNotFetched(boolean value) {
        if (value) ccupChangeAction.lowValues();
      }

      // COB:                 88 CCUP-SHOW-DETAILS               VALUE 'S'.
      public boolean ccupShowDetails() {
        return ccupChangeAction.equals("S");
      }

      public void setCcupShowDetails(boolean value) {
        if (value) ccupChangeAction.setValue("S");
      }

      // COB:                 88 CCUP-CHANGES-MADE               VALUES 'E', 'N'
      public boolean ccupChangesMade() {
        return ccupChangeAction.equals("E")
            || ccupChangeAction.equals("N")
            || ccupChangeAction.equals("C")
            || ccupChangeAction.equals("L")
            || ccupChangeAction.equals("F");
      }

      public void setCcupChangesMade(boolean value) {
        if (value) ccupChangeAction.setValue("E");
      }

      // COB:                 88 CCUP-CHANGES-NOT-OK             VALUE 'E'.
      public boolean ccupChangesNotOk() {
        return ccupChangeAction.equals("E");
      }

      public void setCcupChangesNotOk(boolean value) {
        if (value) ccupChangeAction.setValue("E");
      }

      // COB:                 88 CCUP-CHANGES-OK-NOT-CONFIRMED   VALUE 'N'.
      public boolean ccupChangesOkNotConfirmed() {
        return ccupChangeAction.equals("N");
      }

      public void setCcupChangesOkNotConfirmed(boolean value) {
        if (value) ccupChangeAction.setValue("N");
      }

      // COB:                 88 CCUP-CHANGES-OKAYED-AND-DONE    VALUE 'C'.
      public boolean ccupChangesOkayedAndDone() {
        return ccupChangeAction.equals("C");
      }

      public void setCcupChangesOkayedAndDone(boolean value) {
        if (value) ccupChangeAction.setValue("C");
      }

      // COB:                 88 CCUP-CHANGES-FAILED             VALUES 'L', 'F'.
      public boolean ccupChangesFailed() {
        return ccupChangeAction.equals("L") || ccupChangeAction.equals("F");
      }

      public void setCcupChangesFailed(boolean value) {
        if (value) ccupChangeAction.setValue("L");
      }

      // COB:                 88 CCUP-CHANGES-OKAYED-LOCK-ERROR  VALUE 'L'.
      public boolean ccupChangesOkayedLockError() {
        return ccupChangeAction.equals("L");
      }

      public void setCcupChangesOkayedLockError(boolean value) {
        if (value) ccupChangeAction.setValue("L");
      }

      // COB:                 88 CCUP-CHANGES-OKAYED-BUT-FAILED  VALUE 'F'.
      public boolean ccupChangesOkayedButFailed() {
        return ccupChangeAction.equals("F");
      }

      public void setCcupChangesOkayedButFailed(boolean value) {
        if (value) ccupChangeAction.setValue("F");
      }
    }

    public CardUpdateScreenData cardUpdateScreenData = new CardUpdateScreenData();

    // COB:           05 CCUP-OLD-DETAILS.
    public static class CcupOldDetails extends NGroup {
      // COB:              10 CCUP-OLD-ACCTID                    PIC X(11).
      public NChar ccupOldAcctid = new NChar(11);
      // COB:              10 CCUP-OLD-CARDID                    PIC X(16).
      public NChar ccupOldCardid = new NChar(16);
      // COB:              10 CCUP-OLD-CVV-CD                    PIC X(3).
      public NChar ccupOldCvvCd = new NChar(3);

      // COB:              10 CCUP-OLD-CARDDATA.
      public static class CcupOldCarddata extends NGroup {
        // COB:                 20 CCUP-OLD-CRDNAME                PIC X(50).
        public NChar ccupOldCrdname = new NChar(50);

        // COB:                 20 CCUP-OLD-EXPIRAION-DATE.
        public static class CcupOldExpiraionDate extends NGroup {
          // COB:                    25 CCUP-OLD-EXPYEAR             PIC X(4).
          public NChar ccupOldExpyear = new NChar(4);
          // COB:                    25 CCUP-OLD-EXPMON              PIC X(2).
          public NChar ccupOldExpmon = new NChar(2);
          // COB:                    25 CCUP-OLD-EXPDAY              PIC X(2).
          public NChar ccupOldExpday = new NChar(2);
        }

        public CcupOldExpiraionDate ccupOldExpiraionDate = new CcupOldExpiraionDate();
        // COB:                 20 CCUP-OLD-CRDSTCD                PIC X(1).
        public NChar ccupOldCrdstcd = new NChar(1);
      }

      public CcupOldCarddata ccupOldCarddata = new CcupOldCarddata();
    }

    public CcupOldDetails ccupOldDetails = new CcupOldDetails();

    // COB:           05 CCUP-NEW-DETAILS.
    public static class CcupNewDetails extends NGroup {
      // COB:              10 CCUP-NEW-ACCTID                    PIC X(11).
      public NChar ccupNewAcctid = new NChar(11);
      // COB:              10 CCUP-NEW-CARDID                    PIC X(16).
      public NChar ccupNewCardid = new NChar(16);
      // COB:              10 CCUP-NEW-CVV-CD                    PIC X(3).
      public NChar ccupNewCvvCd = new NChar(3);

      // COB:              10 CCUP-NEW-CARDDATA.
      public static class CcupNewCarddata extends NGroup {
        // COB:                 20 CCUP-NEW-CRDNAME                PIC X(50).
        public NChar ccupNewCrdname = new NChar(50);

        // COB:                 20 CCUP-NEW-EXPIRAION-DATE.
        public static class CcupNewExpiraionDate extends NGroup {
          // COB:                    25 CCUP-NEW-EXPYEAR             PIC X(4).
          public NChar ccupNewExpyear = new NChar(4);
          // COB:                    25 CCUP-NEW-EXPMON              PIC X(2).
          public NChar ccupNewExpmon = new NChar(2);
          // COB:                    25 CCUP-NEW-EXPDAY              PIC X(2).
          public NChar ccupNewExpday = new NChar(2);
        }

        public CcupNewExpiraionDate ccupNewExpiraionDate = new CcupNewExpiraionDate();
        // COB:                 20 CCUP-NEW-CRDSTCD                PIC X(1).
        public NChar ccupNewCrdstcd = new NChar(1);
      }

      public CcupNewCarddata ccupNewCarddata = new CcupNewCarddata();
    }

    public CcupNewDetails ccupNewDetails = new CcupNewDetails();

    // COB:           05 CARD-UPDATE-RECORD.
    public static class CardUpdateRecord extends NGroup {
      // COB:              10 CARD-UPDATE-NUM                   PIC X(16).
      public NChar cardUpdateNum = new NChar(16);
      // COB:              10 CARD-UPDATE-ACCT-ID               PIC 9(11).
      public NZoned cardUpdateAcctId = new NZoned(11, false);
      // COB:              10 CARD-UPDATE-CVV-CD                PIC 9(03).
      public NZoned cardUpdateCvvCd = new NZoned(3, false);
      // COB:              10 CARD-UPDATE-EMBOSSED-NAME         PIC X(50).
      public NChar cardUpdateEmbossedName = new NChar(50);
      // COB:              10 CARD-UPDATE-EXPIRAION-DATE        PIC X(10).
      public NChar cardUpdateExpiraionDate = new NChar(10);
      // COB:              10 CARD-UPDATE-ACTIVE-STATUS         PIC X(01).
      public NChar cardUpdateActiveStatus = new NChar(1);
      // COB:              10 FILLER                            PIC X(59).
      public NChar filler321 = new NChar(59);
    }

    public CardUpdateRecord cardUpdateRecord = new CardUpdateRecord();
  }

  // COB:        01  WS-COMMAREA                             PIC X(2000).
  public NChar wsCommarea = new NChar(2000);
  public Cottl01y cottl01y = new Cottl01y();
  public Cocrdup cocrdup = new Cocrdup();
  public Csdat01y csdat01y = new Csdat01y();
  public Csmsg01y csmsg01y = new Csmsg01y();
  public Csmsg02y csmsg02y = new Csmsg02y();
  public Csusr01y csusr01y = new Csusr01y();
  public Cvact02y cvact02y = new Cvact02y();
  public Cvcus01y cvcus01y = new Cvcus01y();
}
