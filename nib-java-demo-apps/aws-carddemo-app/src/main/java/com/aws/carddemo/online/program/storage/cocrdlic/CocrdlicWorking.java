package com.aws.carddemo.online.program.storage.cocrdlic;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Cocom01y;
import com.aws.carddemo.common.copybook.Cocrdli;
import com.aws.carddemo.common.copybook.Cottl01y;
import com.aws.carddemo.common.copybook.Csdat01y;
import com.aws.carddemo.common.copybook.Csmsg01y;
import com.aws.carddemo.common.copybook.Csusr01y;
import com.aws.carddemo.common.copybook.Cvact02y;
import com.aws.carddemo.common.copybook.Cvcrd01y;
import com.nib.commons.storage.*;

public class CocrdlicWorking extends NGroup {
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
    // COB:          05 WS-INPUT-FLAG                          PIC X(1).
    public NChar wsInputFlag = new NChar(1);

    // COB:            88  INPUT-OK                            VALUES '0'
    public boolean inputOk() {
      return wsInputFlag.equals("0") || wsInputFlag.isSpaces() || wsInputFlag.isLowValues();
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

    // COB:          05  WS-EDIT-ACCT-FLAG                     PIC X(1).
    public NChar wsEditAcctFlag = new NChar(1);

    // COB:            88  FLG-ACCTFILTER-NOT-OK               VALUE '0'.
    public boolean flgAcctfilterNotOk() {
      return wsEditAcctFlag.equals("0");
    }

    public void setFlgAcctfilterNotOk(boolean value) {
      if (value) wsEditAcctFlag.setValue("0");
    }

    // COB:            88  FLG-ACCTFILTER-ISVALID             VALUE '1'.
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

    // COB:          05 WS-EDIT-SELECT-COUNTER                PIC S9(04)
    // COB:                                                   USAGE COMP-3
    // COB:                                                   VALUE 0.
    public NPacked wsEditSelectCounter = new NPacked(3).initial(0);
    // COB:          05 WS-EDIT-SELECT-FLAGS                  PIC X(7)
    // COB:                                                   VALUE LOW-VALUES.
    public NChar wsEditSelectFlags = new NChar(7).initial(NChar.LowValue);

    // COB:          05 WS-EDIT-SELECT-ARRAY REDEFINES  WS-EDIT-SELECT-FLAGS.
    public static class WsEditSelectArray extends NGroup {
      // COB:             10 WS-EDIT-SELECT                      PIC X(1)
      public NChar[] wsEditSelect = AbstractNField.occurs(7, new NChar(1));

      public NChar wsEditSelectAtIndex(int index) {
        return getOccursInstance(wsEditSelect, index);
      }

      // COB:                88 SELECT-OK                        VALUES 'S', 'U'.
      public boolean selectOk(int index) {
        return wsEditSelectAtIndex(index).equals("S") || wsEditSelectAtIndex(index).equals("U");
      }

      public void setSelectOk(int index, boolean value) {
        if (value) wsEditSelectAtIndex(index).setValue("S");
      }

      // COB:                88 VIEW-REQUESTED-ON                VALUE 'S'.
      public boolean viewRequestedOn(int index) {
        return wsEditSelectAtIndex(index).equals("S");
      }

      public void setViewRequestedOn(int index, boolean value) {
        if (value) wsEditSelectAtIndex(index).setValue("S");
      }

      // COB:                88 UPDATE-REQUESTED-ON              VALUE 'U'.
      public boolean updateRequestedOn(int index) {
        return wsEditSelectAtIndex(index).equals("U");
      }

      public void setUpdateRequestedOn(int index, boolean value) {
        if (value) wsEditSelectAtIndex(index).setValue("U");
      }

      // COB:                88 SELECT-BLANK                     VALUES
      public boolean selectBlank(int index) {
        return wsEditSelectAtIndex(index).isSpaces() || wsEditSelectAtIndex(index).isLowValues();
      }

      public void setSelectBlank(int index, boolean value) {
        if (value) wsEditSelectAtIndex(index).setValue(" ");
      }
    }

    public WsEditSelectArray wsEditSelectArray =
        (WsEditSelectArray) new WsEditSelectArray().redefines(wsEditSelectFlags);
    // COB:          05 WS-EDIT-SELECT-ERROR-FLAGS             PIC X(7).
    public NChar wsEditSelectErrorFlags = new NChar(7);

    // COB:          05 WS-EDIT-SELECT-ERROR-FLAGX     REDEFINES
    // COB:             WS-EDIT-SELECT-ERROR-FLAGS.
    public static class WsEditSelectErrorFlagx extends NGroup {
      // COB:             10 WS-EDIT-SELECT-ERRORS OCCURS 7 TIMES.
      public static class WsEditSelectErrors extends NGroup {
        // COB:                20 WS-ROW-CRDSELECT-ERROR          PIC X(1).
        public NChar wsRowCrdselectError = new NChar(1);

        // COB:                   88 WS-ROW-SELECT-ERROR          VALUE '1'.
        public boolean wsRowSelectError() {
          return wsRowCrdselectError.equals("1");
        }

        public void setWsRowSelectError(boolean value) {
          if (value) wsRowCrdselectError.setValue("1");
        }
      }

      public WsEditSelectErrors[] wsEditSelectErrors =
          AbstractNField.occurs(7, new WsEditSelectErrors());

      public WsEditSelectErrors wsEditSelectErrorsAtIndex(int index) {
        return getOccursInstance(wsEditSelectErrors, index);
      }
    }

    public WsEditSelectErrorFlagx wsEditSelectErrorFlagx =
        (WsEditSelectErrorFlagx) new WsEditSelectErrorFlagx().redefines(wsEditSelectErrorFlags);

    // COB:          05 WS-SUBSCRIPT-VARS.
    public static class WsSubscriptVars extends NGroup {
      // COB:             10 I                                  PIC S9(4) COMP
      // COB:                                                   VALUE 0.
      public NInt16 i = new NInt16().initial(0);
      // COB:             10 I-SELECTED                         PIC S9(4) COMP
      // COB:                                                   VALUE 0.
      public NInt16 iSelected = new NInt16().initial(0);

      // COB:                88 DETAIL-WAS-REQUESTED            VALUES 1 THRU 7.
      public boolean detailWasRequested() {
        return iSelected.inRange(1, 7);
      }

      public void setDetailWasRequested(boolean value) {
        if (value) iSelected.setValue(1);
      }
    }

    public WsSubscriptVars wsSubscriptVars = new WsSubscriptVars();

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
      // COB:            10  FLG-PROTECT-SELECT-ROWS             PIC X(1).
      public NChar flgProtectSelectRows = new NChar(1);

      // COB:            88  FLG-PROTECT-SELECT-ROWS-NO          VALUE '0'.
      public boolean flgProtectSelectRowsNo() {
        return flgProtectSelectRows.equals("0");
      }

      public void setFlgProtectSelectRowsNo(boolean value) {
        if (value) flgProtectSelectRows.setValue("0");
      }

      // COB:            88  FLG-PROTECT-SELECT-ROWS-YES         VALUE '1'.
      public boolean flgProtectSelectRowsYes() {
        return flgProtectSelectRows.equals("1");
      }

      public void setFlgProtectSelectRowsYes(boolean value) {
        if (value) flgProtectSelectRows.setValue("1");
      }
    }

    public CicsOutputEditVars cicsOutputEditVars = new CicsOutputEditVars();
    // COB:          05  WS-LONG-MSG                           PIC X(500).
    public NChar wsLongMsg = new NChar(500);
    // COB:          05  WS-INFO-MSG                           PIC X(45).
    public NChar wsInfoMsg = new NChar(45);

    // COB:            88  WS-NO-INFO-MESSAGE                 VALUES
    public boolean wsNoInfoMessage() {
      return wsInfoMsg.isSpaces() || wsInfoMsg.isLowValues();
    }

    public void setWsNoInfoMessage(boolean value) {
      if (value) wsInfoMsg.setValue(SPACES);
    }

    // COB:            88  WS-INFORM-REC-ACTIONS          VALUE
    public boolean wsInformRecActions() {
      return wsInfoMsg.equals("TYPE S FOR DETAIL, U TO UPDATE ANY RECORD");
    }

    public void setWsInformRecActions(boolean value) {
      if (value) wsInfoMsg.setValue("TYPE S FOR DETAIL, U TO UPDATE ANY RECORD");
    }

    // COB:          05  WS-ERROR-MSG                         PIC X(75).
    public NChar wsErrorMsg = new NChar(75);

    // COB:            88  WS-ERROR-MSG-OFF                   VALUE SPACES.
    public boolean wsErrorMsgOff() {
      return wsErrorMsg.isSpaces();
    }

    public void setWsErrorMsgOff(boolean value) {
      if (value) wsErrorMsg.setValue(SPACES);
    }

    // COB:            88  WS-EXIT-MESSAGE                     VALUE
    public boolean wsExitMessage() {
      return wsErrorMsg.equals("PF03 PRESSED.EXITING");
    }

    public void setWsExitMessage(boolean value) {
      if (value) wsErrorMsg.setValue("PF03 PRESSED.EXITING");
    }

    // COB:            88  WS-NO-RECORDS-FOUND                 VALUE
    public boolean wsNoRecordsFound() {
      return wsErrorMsg.equals("NO RECORDS FOUND FOR THIS SEARCH CONDITION.");
    }

    public void setWsNoRecordsFound(boolean value) {
      if (value) wsErrorMsg.setValue("NO RECORDS FOUND FOR THIS SEARCH CONDITION.");
    }

    // COB:            88  WS-MORE-THAN-1-ACTION              VALUE
    public boolean wsMoreThan1Action() {
      return wsErrorMsg.equals("PLEASE SELECT ONLY ONE RECORD TO VIEW OR UPDATE");
    }

    public void setWsMoreThan1Action(boolean value) {
      if (value) wsErrorMsg.setValue("PLEASE SELECT ONLY ONE RECORD TO VIEW OR UPDATE");
    }

    // COB:            88  WS-INVALID-ACTION-CODE              VALUE
    public boolean wsInvalidActionCode() {
      return wsErrorMsg.equals("INVALID ACTION CODE");
    }

    public void setWsInvalidActionCode(boolean value) {
      if (value) wsErrorMsg.setValue("INVALID ACTION CODE");
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

    // COB:          05  WS-CONTEXT-FLAG                       PIC X(1).
    public NChar wsContextFlag = new NChar(1);

    // COB:            88  WS-CONTEXT-FRESH-START              VALUE '0'.
    public boolean wsContextFreshStart() {
      return wsContextFlag.equals("0");
    }

    public void setWsContextFreshStart(boolean value) {
      if (value) wsContextFlag.setValue("0");
    }

    // COB:            88  WS-CONTEXT-FRESH-START-NO           VALUE '1'.
    public boolean wsContextFreshStartNo() {
      return wsContextFlag.equals("1");
    }

    public void setWsContextFreshStartNo(boolean value) {
      if (value) wsContextFlag.setValue("1");
    }

    // COB:          05 WS-FILE-HANDLING-VARS.
    public static class WsFileHandlingVars extends NGroup {
      // COB:             10  WS-CARD-RID.
      public static class WsCardRid extends NGroup {
        // COB:                 20  WS-CARD-RID-CARDNUM            PIC X(16).
        public NChar wsCardRidCardnum = new NChar(16);
        // COB:                 20  WS-CARD-RID-ACCT-ID            PIC 9(11).
        public NZoned wsCardRidAcctId = new NZoned(11, false);
        // COB:                 20  WS-CARD-RID-ACCT-ID-X          REDEFINES
        // COB:                     WS-CARD-RID-ACCT-ID            PIC X(11).
        public NChar wsCardRidAcctIdX = new NChar(11).redefines(wsCardRidAcctId);
      }

      public WsCardRid wsCardRid = new WsCardRid();
    }

    public WsFileHandlingVars wsFileHandlingVars = new WsFileHandlingVars();
    // COB:          05  WS-SCRN-COUNTER               PIC S9(4) COMP VALUE 0.
    public NInt16 wsScrnCounter = new NInt16().initial(0);
    // COB:          05  WS-FILTER-RECORD-FLAG                 PIC X(1).
    public NChar wsFilterRecordFlag = new NChar(1);

    // COB:            88  WS-EXCLUDE-THIS-RECORD               VALUE '0'.
    public boolean wsExcludeThisRecord() {
      return wsFilterRecordFlag.equals("0");
    }

    public void setWsExcludeThisRecord(boolean value) {
      if (value) wsFilterRecordFlag.setValue("0");
    }

    // COB:            88  WS-DONOT-EXCLUDE-THIS-RECORD         VALUE '1'.
    public boolean wsDonotExcludeThisRecord() {
      return wsFilterRecordFlag.equals("1");
    }

    public void setWsDonotExcludeThisRecord(boolean value) {
      if (value) wsFilterRecordFlag.setValue("1");
    }

    // COB:          05  WS-RECORDS-TO-PROCESS-FLAG            PIC X(1).
    public NChar wsRecordsToProcessFlag = new NChar(1);

    // COB:            88  READ-LOOP-EXIT                      VALUE '0'.
    public boolean readLoopExit() {
      return wsRecordsToProcessFlag.equals("0");
    }

    public void setReadLoopExit(boolean value) {
      if (value) wsRecordsToProcessFlag.setValue("0");
    }

    // COB:            88  MORE-RECORDS-TO-READ                VALUE '1'.
    public boolean moreRecordsToRead() {
      return wsRecordsToProcessFlag.equals("1");
    }

    public void setMoreRecordsToRead(boolean value) {
      if (value) wsRecordsToProcessFlag.setValue("1");
    }

    // COB:          05  WS-FILE-ERROR-MESSAGE.
    public static class WsFileErrorMessage extends NGroup {
      // COB:            10  FILLER                              PIC X(12)
      // COB:                                                    VALUE 'File Error:'.
      public NChar filler154 = new NChar(12).initial("File Error:");
      // COB:            10  ERROR-OPNAME                        PIC X(8)
      // COB:                                                    VALUE SPACES.
      public NChar errorOpname = new NChar(8).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(4)
      // COB:                                                    VALUE ' on '.
      public NChar filler158 = new NChar(4).initial(" on ");
      // COB:            10  ERROR-FILE                          PIC X(9)
      // COB:                                                    VALUE SPACES.
      public NChar errorFile = new NChar(9).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(15)
      // COB:                                                    VALUE
      // COB:                                                    ' returned RESP '.
      public NChar filler162 = new NChar(15).initial(" returned RESP ");
      // COB:            10  ERROR-RESP                          PIC X(10)
      // COB:                                                    VALUE SPACES.
      public NChar errorResp = new NChar(10).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(7)
      // COB:                                                    VALUE ',RESP2 '.
      public NChar filler167 = new NChar(7).initial(",RESP2 ");
      // COB:            10  ERROR-RESP2                         PIC X(10)
      // COB:                                                    VALUE SPACES.
      public NChar errorResp2 = new NChar(10).initial(NChar.Space);
      // COB:           10  FILLER                               PIC X(5).
      public NChar filler171 = new NChar(5);
    }

    public WsFileErrorMessage wsFileErrorMessage = new WsFileErrorMessage();
  }

  // COB:        01 WS-CONSTANTS.
  public WsConstants wsConstants = new WsConstants();

  public static class WsConstants extends NGroup {

    // COB:          05  WS-MAX-SCREEN-LINES                    PIC S9(4) COMP
    // COB:                                                     VALUE 7.
    public NInt16 wsMaxScreenLines = new NInt16().initial(7);
    // COB:          05  LIT-THISPGM                            PIC X(8)
    // COB:              VALUE 'COCRDLIC'.
    public NChar litThispgm = new NChar(8).initial("COCRDLIC");
    // COB:          05  LIT-THISTRANID                         PIC X(4)
    // COB:              VALUE 'CCLI'.
    public NChar litThistranid = new NChar(4).initial("CCLI");
    // COB:          05  LIT-THISMAPSET                         PIC X(7)
    // COB:              VALUE 'COCRDLI'.
    public NChar litThismapset = new NChar(7).initial("COCRDLI");
    // COB:          05  LIT-THISMAP                            PIC X(7)
    // COB:              VALUE 'CCRDLIA'.
    public NChar litThismap = new NChar(7).initial("CCRDLIA");
    // COB:          05  LIT-MENUPGM                            PIC X(8)
    // COB:              VALUE 'COMEN01C'.
    public NChar litMenupgm = new NChar(8).initial("COMEN01C");
    // COB:          05  LIT-MENUTRANID                         PIC X(4)
    // COB:              VALUE 'CM00'.
    public NChar litMenutranid = new NChar(4).initial("CM00");
    // COB:          05  LIT-MENUMAPSET                         PIC X(7)
    // COB:              VALUE 'COMEN01'.
    public NChar litMenumapset = new NChar(7).initial("COMEN01");
    // COB:          05  LIT-MENUMAP                            PIC X(7)
    // COB:              VALUE 'COMEN1A'.
    public NChar litMenumap = new NChar(7).initial("COMEN1A");
    // COB:          05  LIT-CARDDTLPGM                         PIC X(8)
    // COB:              VALUE 'COCRDSLC'.
    public NChar litCarddtlpgm = new NChar(8).initial("COCRDSLC");
    // COB:          05  LIT-CARDDTLTRANID                      PIC X(4)
    // COB:              VALUE 'CCDL'.
    public NChar litCarddtltranid = new NChar(4).initial("CCDL");
    // COB:          05  LIT-CARDDTLMAPSET                      PIC X(7)
    // COB:              VALUE 'COCRDSL'.
    public NChar litCarddtlmapset = new NChar(7).initial("COCRDSL");
    // COB:          05  LIT-CARDDTLMAP                         PIC X(7)
    // COB:              VALUE 'CCRDSLA'.
    public NChar litCarddtlmap = new NChar(7).initial("CCRDSLA");
    // COB:          05  LIT-CARDUPDPGM                         PIC X(8)
    // COB:              VALUE 'COCRDUPC'.
    public NChar litCardupdpgm = new NChar(8).initial("COCRDUPC");
    // COB:          05  LIT-CARDUPDTRANID                      PIC X(4)
    // COB:              VALUE 'CCUP'.
    public NChar litCardupdtranid = new NChar(4).initial("CCUP");
    // COB:          05  LIT-CARDUPDMAPSET                      PIC X(7)
    // COB:              VALUE 'COCRDUP'.
    public NChar litCardupdmapset = new NChar(7).initial("COCRDUP");
    // COB:          05  LIT-CARDUPDMAP                         PIC X(7)
    // COB:              VALUE 'CCRDUPA'.
    public NChar litCardupdmap = new NChar(7).initial("CCRDUPA");
    // COB:          05  LIT-CARD-FILE                          PIC X(8)
    // COB:                                                    VALUE 'CARDDAT '.
    public NChar litCardFile = new NChar(8).initial("CARDDAT ");
    // COB:          05  LIT-CARD-FILE-ACCT-PATH                PIC X(8)
    // COB:
    // COB:                                                    VALUE 'CARDAIX '.
    public NChar litCardFileAcctPath = new NChar(8).initial("CARDAIX ");
  }

  public Cvcrd01y cvcrd01y = new Cvcrd01y();
  public Cocom01y cocom01y = new Cocom01y();
  // COB:        01 WS-THIS-PROGCOMMAREA.
  public WsThisProgcommarea wsThisProgcommarea = new WsThisProgcommarea();

  public static class WsThisProgcommarea extends NGroup {

    // COB:             10 WS-CA-LAST-CARDKEY.
    public static class WsCaLastCardkey extends NGroup {
      // COB:                15  WS-CA-LAST-CARD-NUM                PIC X(16).
      public NChar wsCaLastCardNum = new NChar(16);
      // COB:                15  WS-CA-LAST-CARD-ACCT-ID            PIC 9(11).
      public NZoned wsCaLastCardAcctId = new NZoned(11, false);
    }

    public WsCaLastCardkey wsCaLastCardkey = new WsCaLastCardkey();

    // COB:             10 WS-CA-FIRST-CARDKEY.
    public static class WsCaFirstCardkey extends NGroup {
      // COB:                15  WS-CA-FIRST-CARD-NUM               PIC X(16).
      public NChar wsCaFirstCardNum = new NChar(16);
      // COB:                15  WS-CA-FIRST-CARD-ACCT-ID           PIC 9(11).
      public NZoned wsCaFirstCardAcctId = new NZoned(11, false);
    }

    public WsCaFirstCardkey wsCaFirstCardkey = new WsCaFirstCardkey();
    // COB:             10 WS-CA-SCREEN-NUM                       PIC 9(1).
    public NZoned wsCaScreenNum = new NZoned(1, false);

    // COB:                88 CA-FIRST-PAGE                          VALUE 1.
    public boolean caFirstPage() {
      return wsCaScreenNum.equals(1);
    }

    public void setCaFirstPage(boolean value) {
      if (value) wsCaScreenNum.setValue(1);
    }

    // COB:             10 WS-CA-LAST-PAGE-DISPLAYED              PIC 9(1).
    public NZoned wsCaLastPageDisplayed = new NZoned(1, false);

    // COB:                88 CA-LAST-PAGE-SHOWN                     VALUE 0.
    public boolean caLastPageShown() {
      return wsCaLastPageDisplayed.equals(0);
    }

    public void setCaLastPageShown(boolean value) {
      if (value) wsCaLastPageDisplayed.setValue(0);
    }

    // COB:                88 CA-LAST-PAGE-NOT-SHOWN                 VALUE 9.
    public boolean caLastPageNotShown() {
      return wsCaLastPageDisplayed.equals(9);
    }

    public void setCaLastPageNotShown(boolean value) {
      if (value) wsCaLastPageDisplayed.setValue(9);
    }

    // COB:             10 WS-CA-NEXT-PAGE-IND                    PIC X(1).
    public NChar wsCaNextPageInd = new NChar(1);

    // COB:                88 CA-NEXT-PAGE-NOT-EXISTS             VALUE LOW-VALUES.
    public boolean caNextPageNotExists() {
      return wsCaNextPageInd.isLowValues();
    }

    public void setCaNextPageNotExists(boolean value) {
      if (value) wsCaNextPageInd.lowValues();
    }

    // COB:                88 CA-NEXT-PAGE-EXISTS                 VALUE 'Y'.
    public boolean caNextPageExists() {
      return wsCaNextPageInd.equals("Y");
    }

    public void setCaNextPageExists(boolean value) {
      if (value) wsCaNextPageInd.setValue("Y");
    }

    // COB:             10 WS-RETURN-FLAG                        PIC X(1).
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

    // COB:          05 WS-SCREEN-DATA.
    public static class WsScreenData extends NGroup {
      // COB:             10 WS-ALL-ROWS                         PIC X(196).
      public NChar wsAllRows = new NChar(196);

      // COB:             10 FILLER REDEFINES WS-ALL-ROWS.
      public static class Filler254 extends NGroup {
        // COB:                15 WS-SCREEN-ROWS OCCURS  7 TIMES.
        public static class WsScreenRows extends NGroup {
          // COB:                   20 WS-EACH-ROW.
          public static class WsEachRow extends NGroup {
            // COB:                      25 WS-EACH-CARD.
            public static class WsEachCard extends NGroup {
              // COB:                         30 WS-ROW-ACCTNO           PIC X(11).
              public NChar wsRowAcctno = new NChar(11);
              // COB:                         30 WS-ROW-CARD-NUM         PIC X(16).
              public NChar wsRowCardNum = new NChar(16);
              // COB:                         30 WS-ROW-CARD-STATUS      PIC X(1).
              public NChar wsRowCardStatus = new NChar(1);
            }

            public WsEachCard wsEachCard = new WsEachCard();
          }

          public WsEachRow wsEachRow = new WsEachRow();
        }

        public WsScreenRows[] wsScreenRows = AbstractNField.occurs(7, new WsScreenRows());

        public WsScreenRows wsScreenRowsAtIndex(int index) {
          return getOccursInstance(wsScreenRows, index);
        }
      }

      public Filler254 filler254 = (Filler254) new Filler254().redefines(wsAllRows);
    }

    public WsScreenData wsScreenData = new WsScreenData();
  }

  // COB:        01  WS-COMMAREA                             PIC X(2000).
  public NChar wsCommarea = new NChar(2000);
  public Cottl01y cottl01y = new Cottl01y();
  public Cocrdli cocrdli = new Cocrdli();
  public Csdat01y csdat01y = new Csdat01y();
  public Csmsg01y csmsg01y = new Csmsg01y();
  public Csusr01y csusr01y = new Csusr01y();
  public Cvact02y cvact02y = new Cvact02y();
}
