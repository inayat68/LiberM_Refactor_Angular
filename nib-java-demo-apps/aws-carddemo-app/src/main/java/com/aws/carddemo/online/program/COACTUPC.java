package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.coactupc.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COACTUPC extends AbstractOnlineProgram {

  protected enum Flow {
    Exit,
    // 0000-MAIN
    _0000Main,
    // 1260-EDIT-US-PHONE-NUM
    _1260EditUsPhoneNum,
    // 9600-WRITE-PROCESSING
    _9600WriteProcessing,
    // 9600-WRITE-PROCESSING-EXIT
    _9600WriteProcessingExit,
    // 9700-CHECK-CHANGE-IN-REC
    _9700CheckChangeInRec,
    // COMMON-RETURN
    commonReturn,
    // EDIT-AREA-CODE
    editAreaCode,
    // EDIT-DATE-CCYYMMDD
    editDateCcyymmdd,
    // EDIT-DATE-CCYYMMDD-EXIT
    editDateCcyymmddExit,
    // EDIT-DATE-LE
    editDateLe,
    // EDIT-DATE-LE-EXIT
    editDateLeExit,
    // EDIT-DAY
    editDay,
    // EDIT-DAY-EXIT
    editDayExit,
    // EDIT-DAY-MONTH-YEAR
    editDayMonthYear,
    // EDIT-DAY-MONTH-YEAR-EXIT
    editDayMonthYearExit,
    // EDIT-MONTH
    editMonth,
    // EDIT-MONTH-EXIT
    editMonthExit,
    // EDIT-US-PHONE-EXIT
    editUsPhoneExit,
    // EDIT-US-PHONE-LINENUM
    editUsPhoneLinenum,
    // EDIT-US-PHONE-PREFIX
    editUsPhonePrefix,
    // EDIT-YEAR-CCYY
    editYearCcyy,
    // EDIT-YEAR-CCYY-EXIT
    editYearCcyyExit
  }

  private Flow rcNext;

  @ProgramStorage final CoactupcWorking ws = new CoactupcWorking();

  // Linkage
  public static class CoactupcLinkage extends NGroup {
    // COB:        01  DFHCOMMAREA.
    public Dfhcommarea dfhcommarea = new Dfhcommarea();

    public static class Dfhcommarea extends NGroup {

      // COB:          05  FILLER                                PIC X(1)
      // COB:              OCCURS 1 TO 32767 TIMES DEPENDING ON EIBCALEN.
      public NChar[] filler855 = AbstractNField.occurs(32767, new NChar(1));

      public NChar filler855AtIndex(int index) {
        return getOccursInstance(filler855, index);
      }
    }
  }

  final CoactupcLinkage params = new CoactupcLinkage();

  public COACTUPC(Context supernaut) {
    super(supernaut);
  }

  @Override
  protected int procedure(AbstractNField dfhcommarea) throws ContextException {
    if (dfhcommarea != null) {
      params.dfhcommarea.setAddress(dfhcommarea.getAddress());
    } else {
      params.dfhcommarea.allocate();
    }
    rcNext = Flow._0000Main;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case _0000Main:
          rcNext = _0000Main();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.commonReturn;
          }
          break;
        case commonReturn:
          commonReturn();
          rcNext = Flow.Exit;
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    return 0;
  }

  protected Flow _0000Main() {
    // COB(860): EXEC CICS HANDLE ABEND
    // COB(860):           LABEL(ABEND-ROUTINE)
    // COB(860): END-EXEC
    supernaut
        .handleAbend() //
        .label(HandleLabel.abendRoutine.getValue()) //
        .exec();
    // COB(864): INITIALIZE CC-WORK-AREA
    // COB(864):            WS-MISC-STORAGE
    // COB(864):            WS-COMMAREA
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.initialize();
    ws.wsMiscStorage.initialize();
    ws.wsCommarea.initialize();
    // COB(870): MOVE LIT-THISTRANID       TO WS-TRANID
    // ****************************************************************
    //  Store our context
    // ****************************************************************
    ws.wsMiscStorage.wsCicsProcessngVars.wsTranid.setValue(ws.wsLiterals.litThistranid);
    // COB(874): SET WS-RETURN-MSG-OFF  TO TRUE
    // ****************************************************************
    //  Ensure error message is cleared                               *
    // ****************************************************************
    ws.wsMiscStorage.setWsReturnMsgOff(true);
    // COB(878): IF EIBCALEN IS EQUAL TO 0
    // COB(878):     OR (CDEMO-FROM-PROGRAM = LIT-MENUPGM
    // COB(878):     AND NOT CDEMO-PGM-REENTER)
    // ****************************************************************
    //  Store passed data if  any                *
    // ****************************************************************
    if (dfheiblk.getEibcalen() == 0
        || (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsLiterals.litMenupgm)
            && !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter())) {
      // COB(881): INITIALIZE CARDDEMO-COMMAREA
      // COB(881):            WS-THIS-PROGCOMMAREA
      ws.cocom01y.carddemoCommarea.initialize();
      ws.wsThisProgcommarea.initialize();
      // COB(883): SET CDEMO-PGM-ENTER TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(884): SET ACUP-DETAILS-NOT-FETCHED TO TRUE
      ws.wsThisProgcommarea.acctUpdateScreenData.setAcupDetailsNotFetched(true);
      // COB(885): ELSE
    } else {
      // COB(886): MOVE DFHCOMMAREA (1:LENGTH OF CARDDEMO-COMMAREA)  TO
      // COB(886):                   CARDDEMO-COMMAREA
      ws.cocom01y.carddemoCommarea.setValue(
          params.dfhcommarea, 0, ws.cocom01y.carddemoCommarea.length());
      // COB(888): MOVE DFHCOMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
      // COB(888):                  LENGTH OF WS-THIS-PROGCOMMAREA ) TO
      // COB(888):                   WS-THIS-PROGCOMMAREA
      ws.wsThisProgcommarea.setValue(
          params.dfhcommarea,
          ws.cocom01y.carddemoCommarea.length(),
          0,
          ws.wsThisProgcommarea.length());
      // COB(891): END-IF
    }
    // COB(896): PERFORM YYYY-STORE-PFKEY
    // COB(896):    THRU YYYY-STORE-PFKEY-EXIT
    // ****************************************************************
    //  Remap PFkeys as needed.
    //  Store the Mapped PF Key
    // ****************************************************************
    yyyyStorePfkey();
    // COB(903): SET PFK-INVALID TO TRUE
    // ****************************************************************
    //  Check the AID to see if its valid at this point               *
    //  F3 - Exit
    //  Enter show screen again
    // ****************************************************************
    ws.wsMiscStorage.setPfkInvalid(true);
    // COB(904): IF CCARD-AID-ENTER OR
    // COB(904):    CCARD-AID-PFK03 OR
    // COB(904):    (CCARD-AID-PFK05 AND ACUP-CHANGES-OK-NOT-CONFIRMED)
    // COB(904):                    OR
    // COB(904):    (CCARD-AID-PFK12 AND NOT ACUP-DETAILS-NOT-FETCHED)
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidEnter()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()
        || (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk05()
            && ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkNotConfirmed())
        || (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk12()
            && !ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched())) {
      // COB(909): SET PFK-VALID TO TRUE
      ws.wsMiscStorage.setPfkValid(true);
      // COB(910): END-IF
    }
    // COB(912): IF PFK-INVALID
    if (ws.wsMiscStorage.pfkInvalid()) {
      // COB(913): SET CCARD-AID-ENTER TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidEnter(true);
      // COB(914): END-IF
    }
    // COB(919): EVALUATE TRUE
    //
    // ****************************************************************
    //  Decide what to do based on inputs received
    // ****************************************************************
    // COB(925): WHEN CCARD-AID-PFK03
    // *****************************************************************
    //        USER PRESSES PF03 TO EXIT
    //   OR   USER IS DONE WITH UPDATE
    //             XCTL TO CALLING PROGRAM OR MAIN MENU
    // *****************************************************************
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()) {
      // COB(926): SET CCARD-AID-PFK03     TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk03(true);
      // COB(928): IF CDEMO-FROM-TRANID    EQUAL LOW-VALUES
      // COB(928): OR CDEMO-FROM-TRANID    EQUAL SPACES
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isSpaces()) {
        // COB(930): MOVE LIT-MENUTRANID  TO CDEMO-TO-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToTranid.setValue(
            ws.wsLiterals.litMenutranid);
        // COB(931): ELSE
      } else {
        // COB(932): MOVE CDEMO-FROM-TRANID  TO CDEMO-TO-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToTranid.setValue(
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid);
        // COB(933): END-IF
      }
      // COB(935): IF CDEMO-FROM-PROGRAM   EQUAL LOW-VALUES
      // COB(935): OR CDEMO-FROM-PROGRAM   EQUAL SPACES
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isSpaces()) {
        // COB(937): MOVE LIT-MENUPGM     TO CDEMO-TO-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
            ws.wsLiterals.litMenupgm);
        // COB(938): ELSE
      } else {
        // COB(939): MOVE CDEMO-FROM-PROGRAM TO CDEMO-TO-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram);
        // COB(940): END-IF
      }
      // COB(942): MOVE LIT-THISTRANID     TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
          ws.wsLiterals.litThistranid);
      // COB(943): MOVE LIT-THISPGM        TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
          ws.wsLiterals.litThispgm);
      // COB(945): SET  CDEMO-USRTYP-USER  TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypUser(true);
      // COB(946): SET  CDEMO-PGM-ENTER    TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(947): MOVE LIT-THISMAPSET     TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(
          ws.wsLiterals.litThismapset);
      // COB(948): MOVE LIT-THISMAP        TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsLiterals.litThismap);
      // COB(950): EXEC CICS
      // COB(950):      SYNCPOINT
      // COB(950): END-EXEC
      try {
        supernaut
            .syncpoint() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(954): EXEC CICS XCTL
      // COB(954):      PROGRAM (CDEMO-TO-PROGRAM)
      // COB(954):      COMMAREA(CARDDEMO-COMMAREA)
      // COB(954): END-EXEC
      try {
        supernaut
            .xctl(ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
            .commarea(ws.cocom01y.carddemoCommarea) //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(962): WHEN ACUP-DETAILS-NOT-FETCHED
      // COB(962):  AND CDEMO-PGM-ENTER
      // *****************************************************************
      //        FRESH ENTRY INTO PROGRAM
      //             ASK THE USER FOR THE KEYS TO FETCH CARD TO BE UPDATED
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched()
            && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()
        // COB(964): WHEN CDEMO-FROM-PROGRAM   EQUAL LIT-MENUPGM
        // COB(964):  AND NOT CDEMO-PGM-REENTER
        || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsLiterals.litMenupgm)
            && !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(966): INITIALIZE WS-THIS-PROGCOMMAREA
      ws.wsThisProgcommarea.initialize();
      // COB(967): PERFORM 3000-SEND-MAP THRU
      // COB(967):         3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(969): SET CDEMO-PGM-REENTER        TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
      // COB(970): SET ACUP-DETAILS-NOT-FETCHED TO TRUE
      ws.wsThisProgcommarea.acctUpdateScreenData.setAcupDetailsNotFetched(true);
      // COB(971): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(977): WHEN ACUP-CHANGES-OKAYED-AND-DONE
      // *****************************************************************
      //        ACCT DATA CHANGES REVIEWED, OKAYED AND DONE SUCESSFULLY
      //             RESET THE SEARCH KEYS
      //             ASK THE USER FOR FRESH SEARCH CRITERIA
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedAndDone()
        // COB(978): WHEN ACUP-CHANGES-FAILED
        || ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesFailed()) {
      // COB(979): INITIALIZE WS-THIS-PROGCOMMAREA
      // COB(979):            WS-MISC-STORAGE
      // COB(979):            CDEMO-ACCT-ID
      ws.wsThisProgcommarea.initialize();
      ws.wsMiscStorage.initialize();
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.initialize();
      // COB(982): SET CDEMO-PGM-ENTER            TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(983): PERFORM 3000-SEND-MAP THRU
      // COB(983):         3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(985): SET CDEMO-PGM-REENTER          TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
      // COB(986): SET ACUP-DETAILS-NOT-FETCHED   TO TRUE
      ws.wsThisProgcommarea.acctUpdateScreenData.setAcupDetailsNotFetched(true);
      // COB(987): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(994): WHEN OTHER
      // *****************************************************************
      //       ACCT DATA HAS BEEN PRESENTED TO USER
      //             CHECK THE USER INPUTS
      //             DECIDE WHAT TO DO
      //             PRESENT NEXT STEPS TO USER
      // *****************************************************************
    } else {
      // COB(995): PERFORM 1000-PROCESS-INPUTS
      // COB(995):    THRU 1000-PROCESS-INPUTS-EXIT
      _1000ProcessInputs();
      // COB(997): PERFORM 2000-DECIDE-ACTION
      // COB(997):    THRU 2000-DECIDE-ACTION-EXIT
      _2000DecideAction();
      // COB(999): PERFORM 3000-SEND-MAP
      // COB(999):    THRU 3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(1001): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(1002): END-EVALUATE
    }
    return Flow.Exit;
  }

  /***/
  protected void commonReturn() {
    // COB(1006): MOVE WS-RETURN-MSG     TO CCARD-ERROR-MSG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
    // COB(1008): MOVE  CARDDEMO-COMMAREA    TO WS-COMMAREA
    ws.wsCommarea.setValue(ws.cocom01y.carddemoCommarea);
    // COB(1009): MOVE  WS-THIS-PROGCOMMAREA TO
    // COB(1009):        WS-COMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
    // COB(1009):                     LENGTH OF WS-THIS-PROGCOMMAREA )
    ws.wsCommarea.setValue(
        ws.wsThisProgcommarea,
        0,
        ws.cocom01y.carddemoCommarea.length(),
        ws.wsThisProgcommarea.length());
    // COB(1013): EXEC CICS RETURN
    // COB(1013):      TRANSID (LIT-THISTRANID)
    // COB(1013):      COMMAREA (WS-COMMAREA)
    // COB(1013):      LENGTH(LENGTH OF WS-COMMAREA)
    // COB(1013): END-EXEC
    try {
      supernaut
          .returnTransid(ws.wsLiterals.litThistranid) //
          .commarea(ws.wsCommarea) //
          .length(ws.wsCommarea.length()) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***/
  protected void _1000ProcessInputs() {
    // COB(1024): PERFORM 1100-RECEIVE-MAP
    // COB(1024):    THRU 1100-RECEIVE-MAP-EXIT
    _1100ReceiveMap();
    // COB(1026): PERFORM 1200-EDIT-MAP-INPUTS
    // COB(1026):    THRU 1200-EDIT-MAP-INPUTS-EXIT
    _1200EditMapInputs();
    // COB(1028): MOVE WS-RETURN-MSG  TO CCARD-ERROR-MSG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
    // COB(1029): MOVE LIT-THISPGM    TO CCARD-NEXT-PROG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.setValue(ws.wsLiterals.litThispgm);
    // COB(1030): MOVE LIT-THISMAPSET TO CCARD-NEXT-MAPSET
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsLiterals.litThismapset);
    // COB(1031): MOVE LIT-THISMAP    TO CCARD-NEXT-MAP
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsLiterals.litThismap);
  }

  protected void _1100ReceiveMap() {
    // COB(1038): EXEC CICS RECEIVE MAP(LIT-THISMAP)
    // COB(1038):           MAPSET(LIT-THISMAPSET)
    // COB(1038):           INTO(CACTUPAI)
    // COB(1038):           RESP(WS-RESP-CD)
    // COB(1038):           RESP2(WS-REAS-CD)
    // COB(1038): END-EXEC
    supernaut
        .receiveMap(ws.wsLiterals.litThismap.getValue()) //
        .mapset(ws.wsLiterals.litThismapset.getValue()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.coactup.cactupai) //
        .exec();
    // COB(1045): INITIALIZE ACUP-NEW-DETAILS
    ws.wsThisProgcommarea.acupNewDetails.initialize();
    // COB(1049): IF  ACCTSIDI OF CACTUPAI = '*'
    // COB(1049): OR  ACCTSIDI OF CACTUPAI = SPACES
    // *****************************************************************
    //     Account Master data
    // *****************************************************************
    if (ws.coactup.cactupai.acctsidi.equals("*") || ws.coactup.cactupai.acctsidi.isSpaces()) {
      // COB(1051): MOVE LOW-VALUES           TO CC-ACCT-ID
      // COB(1051):                              ACUP-NEW-ACCT-ID-X
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.lowValues();
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewAcctIdX.lowValues();
      // COB(1053): ELSE
    } else {
      // COB(1054): MOVE ACCTSIDI OF CACTUPAI TO CC-ACCT-ID
      // COB(1054):                              ACUP-NEW-ACCT-ID-X
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.setValue(ws.coactup.cactupai.acctsidi);
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewAcctIdX.setValue(
          ws.coactup.cactupai.acctsidi);
      // COB(1056): END-IF
    }
    // COB(1058): IF ACUP-DETAILS-NOT-FETCHED
    if (ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched()) {
      // COB(1059): GO TO 1100-RECEIVE-MAP-EXIT
      return;
      // COB(1060): END-IF
    }
    // COB(1063): IF  ACSTTUSI OF CACTUPAI = '*'
    // COB(1063): OR  ACSTTUSI OF CACTUPAI = SPACES
    //
    //  Active Status
    if (ws.coactup.cactupai.acsttusi.equals("*") || ws.coactup.cactupai.acsttusi.isSpaces()) {
      // COB(1065): MOVE LOW-VALUES           TO ACUP-NEW-ACTIVE-STATUS
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewActiveStatus.lowValues();
      // COB(1066): ELSE
    } else {
      // COB(1067): MOVE ACSTTUSI OF CACTUPAI TO ACUP-NEW-ACTIVE-STATUS
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewActiveStatus.setValue(
          ws.coactup.cactupai.acsttusi);
      // COB(1068): END-IF
    }
    // COB(1071): IF  ACRDLIMI OF CACTUPAI = '*'
    // COB(1071): OR  ACRDLIMI OF CACTUPAI = SPACES
    //
    //  Credit Limit
    if (ws.coactup.cactupai.acrdlimi.equals("*") || ws.coactup.cactupai.acrdlimi.isSpaces()) {
      // COB(1073): MOVE LOW-VALUES           TO ACUP-NEW-CREDIT-LIMIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCreditLimitX.lowValues();
      // COB(1074): ELSE
    } else {
      // COB(1075): MOVE ACRDLIMI OF CACTUPAI TO ACUP-NEW-CREDIT-LIMIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCreditLimitX.setValue(
          ws.coactup.cactupai.acrdlimi);
      // COB(1076): IF FUNCTION TEST-NUMVAL-C(ACUP-NEW-CREDIT-LIMIT-X) = 0
      if (ws.wsMiscStorage
          .alphaVarsForDataEditing
          .acupNewCreditLimitX
          .testConvertCurrencyToNumeric()
          .equals(0)) {
        // COB(1077): COMPUTE ACUP-NEW-CREDIT-LIMIT-N =
        // COB(1077):    FUNCTION NUMVAL-C(ACRDLIMI OF CACTUPAI)
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCreditLimitN.setValue(
            ws.coactup.cactupai.acrdlimi.convertCurrencyToNumeric());
        // COB(1079): ELSE
      } else {
        // COB(1080): CONTINUE
        // do nothing
        // COB(1081): END-IF
      }
      // COB(1082): END-IF
    }
    // COB(1085): IF  ACSHLIMI OF CACTUPAI = '*'
    // COB(1085): OR  ACSHLIMI OF CACTUPAI = SPACES
    //
    //  Cash Limit
    if (ws.coactup.cactupai.acshlimi.equals("*") || ws.coactup.cactupai.acshlimi.isSpaces()) {
      // COB(1087): MOVE LOW-VALUES           TO ACUP-NEW-CASH-CREDIT-LIMIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCashCreditLimitX.lowValues();
      // COB(1088): ELSE
    } else {
      // COB(1089): MOVE ACSHLIMI OF CACTUPAI TO ACUP-NEW-CASH-CREDIT-LIMIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCashCreditLimitX.setValue(
          ws.coactup.cactupai.acshlimi);
      // COB(1090): IF FUNCTION TEST-NUMVAL-C(ACUP-NEW-CASH-CREDIT-LIMIT-X) = 0
      if (ws.wsMiscStorage
          .alphaVarsForDataEditing
          .acupNewCashCreditLimitX
          .testConvertCurrencyToNumeric()
          .equals(0)) {
        // COB(1091): COMPUTE ACUP-NEW-CASH-CREDIT-LIMIT-N =
        // COB(1091):      FUNCTION NUMVAL-C(ACSHLIMI OF CACTUPAI)
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCashCreditLimitN.setValue(
            ws.coactup.cactupai.acshlimi.convertCurrencyToNumeric());
        // COB(1093): ELSE
      } else {
        // COB(1094): CONTINUE
        // do nothing
        // COB(1095): END-IF
      }
      // COB(1096): END-IF
    }
    // COB(1099): IF  ACURBALI OF CACTUPAI = '*'
    // COB(1099): OR  ACURBALI OF CACTUPAI = SPACES
    //
    //  Current Balance
    if (ws.coactup.cactupai.acurbali.equals("*") || ws.coactup.cactupai.acurbali.isSpaces()) {
      // COB(1101): MOVE LOW-VALUES           TO ACUP-NEW-CURR-BAL-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrBalX.lowValues();
      // COB(1102): ELSE
    } else {
      // COB(1103): MOVE ACURBALI OF CACTUPAI TO ACUP-NEW-CURR-BAL-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrBalX.setValue(
          ws.coactup.cactupai.acurbali);
      // COB(1104): IF  FUNCTION TEST-NUMVAL-C(ACUP-NEW-CURR-BAL-X) = 0
      if (ws.wsMiscStorage
          .alphaVarsForDataEditing
          .acupNewCurrBalX
          .testConvertCurrencyToNumeric()
          .equals(0)) {
        // COB(1105): COMPUTE ACUP-NEW-CURR-BAL-N =
        // COB(1105):   FUNCTION NUMVAL-C(ACUP-NEW-CURR-BAL-X)
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrBalN.setValue(
            ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrBalX.convertCurrencyToNumeric());
        // COB(1107): ELSE
      } else {
        // COB(1108): CONTINUE
        // do nothing
        // COB(1109): END-IF
      }
      // COB(1110): END-IF
    }
    // COB(1113): IF  ACRCYCRI OF CACTUPAI = '*'
    // COB(1113): OR  ACRCYCRI OF CACTUPAI = SPACES
    //
    // Current Cycle Credit
    if (ws.coactup.cactupai.acrcycri.equals("*") || ws.coactup.cactupai.acrcycri.isSpaces()) {
      // COB(1115): MOVE LOW-VALUES           TO ACUP-NEW-CURR-CYC-CREDIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycCreditX.lowValues();
      // COB(1116): ELSE
    } else {
      // COB(1117): MOVE ACRCYCRI OF CACTUPAI TO ACUP-NEW-CURR-CYC-CREDIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycCreditX.setValue(
          ws.coactup.cactupai.acrcycri);
      // COB(1118): IF FUNCTION TEST-NUMVAL-C(ACUP-NEW-CURR-CYC-CREDIT-X) = 0
      if (ws.wsMiscStorage
          .alphaVarsForDataEditing
          .acupNewCurrCycCreditX
          .testConvertCurrencyToNumeric()
          .equals(0)) {
        // COB(1119): COMPUTE ACUP-NEW-CURR-CYC-CREDIT-N =
        // COB(1119):   FUNCTION NUMVAL-C(ACRCYCRI OF CACTUPAI)
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrCycCreditN.setValue(
            ws.coactup.cactupai.acrcycri.convertCurrencyToNumeric());
        // COB(1121): ELSE
      } else {
        // COB(1122): CONTINUE
        // do nothing
        // COB(1123): END-IF
      }
      // COB(1124): END-IF
    }
    // COB(1127): IF  ACRCYDBI OF CACTUPAI = '*'
    // COB(1127): OR  ACRCYDBI OF CACTUPAI = SPACES
    //
    // Current Cycle Debit
    if (ws.coactup.cactupai.acrcydbi.equals("*") || ws.coactup.cactupai.acrcydbi.isSpaces()) {
      // COB(1129): MOVE LOW-VALUES           TO ACUP-NEW-CURR-CYC-DEBIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycDebitX.lowValues();
      // COB(1130): ELSE
    } else {
      // COB(1131): MOVE ACRCYDBI OF CACTUPAI TO ACUP-NEW-CURR-CYC-DEBIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycDebitX.setValue(
          ws.coactup.cactupai.acrcydbi);
      // COB(1132): IF  FUNCTION TEST-NUMVAL-C(ACUP-NEW-CURR-CYC-DEBIT-X) = 0
      if (ws.wsMiscStorage
          .alphaVarsForDataEditing
          .acupNewCurrCycDebitX
          .testConvertCurrencyToNumeric()
          .equals(0)) {
        // COB(1133): COMPUTE ACUP-NEW-CURR-CYC-DEBIT-N =
        // COB(1133):   FUNCTION NUMVAL-C(ACRCYDBI OF CACTUPAI)
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrCycDebitN.setValue(
            ws.coactup.cactupai.acrcydbi.convertCurrencyToNumeric());
        // COB(1135): ELSE
      } else {
        // COB(1136): CONTINUE
        // do nothing
        // COB(1137): END-IF
      }
      // COB(1138): END-IF
    }
    // COB(1142): IF  OPNYEARI OF CACTUPAI = '*'
    // COB(1142): OR  OPNYEARI OF CACTUPAI = SPACES
    //
    // Open date
    //
    if (ws.coactup.cactupai.opnyeari.equals("*") || ws.coactup.cactupai.opnyeari.isSpaces()) {
      // COB(1144): MOVE LOW-VALUES           TO ACUP-NEW-OPEN-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenYear
          .lowValues();
      // COB(1145): ELSE
    } else {
      // COB(1146): MOVE OPNYEARI OF CACTUPAI TO ACUP-NEW-OPEN-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenYear
          .setValue(ws.coactup.cactupai.opnyeari);
      // COB(1147): END-IF
    }
    // COB(1149): IF  OPNMONI OF CACTUPAI = '*'
    // COB(1149): OR  OPNMONI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.opnmoni.equals("*") || ws.coactup.cactupai.opnmoni.isSpaces()) {
      // COB(1151): MOVE LOW-VALUES           TO ACUP-NEW-OPEN-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenMon
          .lowValues();
      // COB(1152): ELSE
    } else {
      // COB(1153): MOVE OPNMONI OF CACTUPAI TO  ACUP-NEW-OPEN-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenMon
          .setValue(ws.coactup.cactupai.opnmoni);
      // COB(1154): END-IF
    }
    // COB(1156): IF  OPNDAYI OF CACTUPAI = '*'
    // COB(1156): OR  OPNDAYI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.opndayi.equals("*") || ws.coactup.cactupai.opndayi.isSpaces()) {
      // COB(1158): MOVE LOW-VALUES           TO ACUP-NEW-OPEN-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenDay
          .lowValues();
      // COB(1159): ELSE
    } else {
      // COB(1160): MOVE OPNDAYI OF CACTUPAI TO  ACUP-NEW-OPEN-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenDay
          .setValue(ws.coactup.cactupai.opndayi);
      // COB(1161): END-IF
    }
    // COB(1165): IF  EXPYEARI OF CACTUPAI = '*'
    // COB(1165): OR  EXPYEARI OF CACTUPAI = SPACES
    //
    // Expiry date
    //
    if (ws.coactup.cactupai.expyeari.equals("*") || ws.coactup.cactupai.expyeari.isSpaces()) {
      // COB(1167): MOVE LOW-VALUES           TO ACUP-NEW-EXP-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDateParts.acupNewExpYear
          .lowValues();
      // COB(1168): ELSE
    } else {
      // COB(1169): MOVE EXPYEARI OF CACTUPAI TO ACUP-NEW-EXP-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDateParts.acupNewExpYear
          .setValue(ws.coactup.cactupai.expyeari);
      // COB(1170): END-IF
    }
    // COB(1172): IF  EXPMONI OF CACTUPAI = '*'
    // COB(1172): OR  EXPMONI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.expmoni.equals("*") || ws.coactup.cactupai.expmoni.isSpaces()) {
      // COB(1174): MOVE LOW-VALUES           TO ACUP-NEW-EXP-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDateParts.acupNewExpMon
          .lowValues();
      // COB(1175): ELSE
    } else {
      // COB(1176): MOVE EXPMONI OF CACTUPAI TO  ACUP-NEW-EXP-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDateParts.acupNewExpMon
          .setValue(ws.coactup.cactupai.expmoni);
      // COB(1177): END-IF
    }
    // COB(1179): IF  EXPDAYI OF CACTUPAI = '*'
    // COB(1179): OR  EXPDAYI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.expdayi.equals("*") || ws.coactup.cactupai.expdayi.isSpaces()) {
      // COB(1181): MOVE LOW-VALUES           TO ACUP-NEW-EXP-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDateParts.acupNewExpDay
          .lowValues();
      // COB(1182): ELSE
    } else {
      // COB(1183): MOVE EXPDAYI OF CACTUPAI TO  ACUP-NEW-EXP-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDateParts.acupNewExpDay
          .setValue(ws.coactup.cactupai.expdayi);
      // COB(1184): END-IF
    }
    // COB(1188): IF  RISYEARI OF CACTUPAI = '*'
    // COB(1188): OR  RISYEARI OF CACTUPAI = SPACES
    //
    // Reissue date
    //
    if (ws.coactup.cactupai.risyeari.equals("*") || ws.coactup.cactupai.risyeari.isSpaces()) {
      // COB(1190): MOVE LOW-VALUES           TO ACUP-NEW-REISSUE-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDateParts
          .acupNewReissueYear.lowValues();
      // COB(1191): ELSE
    } else {
      // COB(1192): MOVE RISYEARI OF CACTUPAI TO ACUP-NEW-REISSUE-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDateParts
          .acupNewReissueYear.setValue(ws.coactup.cactupai.risyeari);
      // COB(1193): END-IF
    }
    // COB(1195): IF  RISMONI OF CACTUPAI = '*'
    // COB(1195): OR  RISMONI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.rismoni.equals("*") || ws.coactup.cactupai.rismoni.isSpaces()) {
      // COB(1197): MOVE LOW-VALUES           TO ACUP-NEW-REISSUE-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDateParts.acupNewReissueMon
          .lowValues();
      // COB(1198): ELSE
    } else {
      // COB(1199): MOVE RISMONI OF CACTUPAI TO  ACUP-NEW-REISSUE-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDateParts.acupNewReissueMon
          .setValue(ws.coactup.cactupai.rismoni);
      // COB(1200): END-IF
    }
    // COB(1202): IF  RISDAYI OF CACTUPAI = '*'
    // COB(1202): OR  RISDAYI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.risdayi.equals("*") || ws.coactup.cactupai.risdayi.isSpaces()) {
      // COB(1204): MOVE LOW-VALUES           TO ACUP-NEW-REISSUE-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDateParts.acupNewReissueDay
          .lowValues();
      // COB(1205): ELSE
    } else {
      // COB(1206): MOVE RISDAYI OF CACTUPAI TO  ACUP-NEW-REISSUE-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDateParts.acupNewReissueDay
          .setValue(ws.coactup.cactupai.risdayi);
      // COB(1207): END-IF
    }
    // COB(1211): IF  AADDGRPI OF CACTUPAI = '*'
    // COB(1211): OR  AADDGRPI OF CACTUPAI = SPACES
    //
    // Account Group
    //
    if (ws.coactup.cactupai.aaddgrpi.equals("*") || ws.coactup.cactupai.aaddgrpi.isSpaces()) {
      // COB(1213): MOVE LOW-VALUES           TO ACUP-NEW-GROUP-ID
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewGroupId.lowValues();
      // COB(1214): ELSE
    } else {
      // COB(1215): MOVE AADDGRPI OF CACTUPAI TO ACUP-NEW-GROUP-ID
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewGroupId.setValue(
          ws.coactup.cactupai.aaddgrpi);
      // COB(1216): END-IF
    }
    // COB(1222): IF  ACSTNUMI OF CACTUPAI = '*'
    // COB(1222): OR  ACSTNUMI OF CACTUPAI = SPACES
    // *****************************************************************
    //     Customer Master data
    // *****************************************************************
    // Customer Id (actually not editable)
    //
    if (ws.coactup.cactupai.acstnumi.equals("*") || ws.coactup.cactupai.acstnumi.isSpaces()) {
      // COB(1224): MOVE LOW-VALUES           TO ACUP-NEW-CUST-ID-X
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustIdX.lowValues();
      // COB(1225): ELSE
    } else {
      // COB(1226): MOVE ACSTNUMI OF CACTUPAI TO ACUP-NEW-CUST-ID-X
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustIdX.setValue(
          ws.coactup.cactupai.acstnumi);
      // COB(1227): END-IF
    }
    // COB(1231): IF  ACTSSN1I OF CACTUPAI = '*'
    // COB(1231): OR  ACTSSN1I OF CACTUPAI = SPACES
    //
    // Social Security Number
    //
    if (ws.coactup.cactupai.actssn1i.equals("*") || ws.coactup.cactupai.actssn1i.isSpaces()) {
      // COB(1233): MOVE LOW-VALUES           TO ACUP-NEW-CUST-SSN-1
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn1
          .lowValues();
      // COB(1234): ELSE
    } else {
      // COB(1235): MOVE ACTSSN1I OF CACTUPAI TO ACUP-NEW-CUST-SSN-1
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn1.setValue(
          ws.coactup.cactupai.actssn1i);
      // COB(1236): END-IF
    }
    // COB(1238): IF  ACTSSN2I OF CACTUPAI = '*'
    // COB(1238): OR  ACTSSN2I OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.actssn2i.equals("*") || ws.coactup.cactupai.actssn2i.isSpaces()) {
      // COB(1240): MOVE LOW-VALUES           TO ACUP-NEW-CUST-SSN-2
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn2
          .lowValues();
      // COB(1241): ELSE
    } else {
      // COB(1242): MOVE ACTSSN2I OF CACTUPAI TO ACUP-NEW-CUST-SSN-2
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn2.setValue(
          ws.coactup.cactupai.actssn2i);
      // COB(1243): END-IF
    }
    // COB(1245): IF  ACTSSN3I OF CACTUPAI = '*'
    // COB(1245): OR  ACTSSN3I OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.actssn3i.equals("*") || ws.coactup.cactupai.actssn3i.isSpaces()) {
      // COB(1247): MOVE LOW-VALUES           TO ACUP-NEW-CUST-SSN-3
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn3
          .lowValues();
      // COB(1248): ELSE
    } else {
      // COB(1249): MOVE ACTSSN3I OF CACTUPAI TO ACUP-NEW-CUST-SSN-3
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn3.setValue(
          ws.coactup.cactupai.actssn3i);
      // COB(1250): END-IF
    }
    // COB(1254): IF  DOBYEARI OF CACTUPAI = '*'
    // COB(1254): OR  DOBYEARI OF CACTUPAI = SPACES
    //
    // Date of birth
    //
    if (ws.coactup.cactupai.dobyeari.equals("*") || ws.coactup.cactupai.dobyeari.isSpaces()) {
      // COB(1256): MOVE LOW-VALUES           TO ACUP-NEW-CUST-DOB-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobYear
          .lowValues();
      // COB(1257): ELSE
    } else {
      // COB(1258): MOVE DOBYEARI OF CACTUPAI TO ACUP-NEW-CUST-DOB-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobYear
          .setValue(ws.coactup.cactupai.dobyeari);
      // COB(1259): END-IF
    }
    // COB(1261): IF  DOBMONI OF CACTUPAI = '*'
    // COB(1261): OR  DOBMONI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.dobmoni.equals("*") || ws.coactup.cactupai.dobmoni.isSpaces()) {
      // COB(1263): MOVE LOW-VALUES           TO ACUP-NEW-CUST-DOB-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobMon
          .lowValues();
      // COB(1264): ELSE
    } else {
      // COB(1265): MOVE DOBMONI OF CACTUPAI  TO ACUP-NEW-CUST-DOB-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobMon
          .setValue(ws.coactup.cactupai.dobmoni);
      // COB(1266): END-IF
    }
    // COB(1268): IF  DOBDAYI OF CACTUPAI = '*'
    // COB(1268): OR  DOBDAYI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.dobdayi.equals("*") || ws.coactup.cactupai.dobdayi.isSpaces()) {
      // COB(1270): MOVE LOW-VALUES           TO ACUP-NEW-CUST-DOB-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobDay
          .lowValues();
      // COB(1271): ELSE
    } else {
      // COB(1272): MOVE DOBDAYI OF CACTUPAI  TO ACUP-NEW-CUST-DOB-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobDay
          .setValue(ws.coactup.cactupai.dobdayi);
      // COB(1273): END-IF
    }
    // COB(1277): IF  ACSTFCOI OF CACTUPAI = '*'
    // COB(1277): OR  ACSTFCOI OF CACTUPAI = SPACES
    //
    // FICO
    //
    if (ws.coactup.cactupai.acstfcoi.equals("*") || ws.coactup.cactupai.acstfcoi.isSpaces()) {
      // COB(1279): MOVE LOW-VALUES           TO ACUP-NEW-CUST-FICO-SCORE-X
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFicoScoreX.lowValues();
      // COB(1280): ELSE
    } else {
      // COB(1281): MOVE ACSTFCOI OF CACTUPAI TO ACUP-NEW-CUST-FICO-SCORE-X
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFicoScoreX.setValue(
          ws.coactup.cactupai.acstfcoi);
      // COB(1282): END-IF
    }
    // COB(1286): IF  ACSFNAMI OF CACTUPAI = '*'
    // COB(1286): OR  ACSFNAMI OF CACTUPAI = SPACES
    //
    // First Name
    //
    if (ws.coactup.cactupai.acsfnami.equals("*") || ws.coactup.cactupai.acsfnami.isSpaces()) {
      // COB(1288): MOVE LOW-VALUES           TO ACUP-NEW-CUST-FIRST-NAME
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFirstName.lowValues();
      // COB(1289): ELSE
    } else {
      // COB(1290): MOVE ACSFNAMI OF CACTUPAI TO ACUP-NEW-CUST-FIRST-NAME
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFirstName.setValue(
          ws.coactup.cactupai.acsfnami);
      // COB(1291): END-IF
    }
    // COB(1295): IF  ACSMNAMI OF CACTUPAI = '*'
    // COB(1295): OR  ACSMNAMI OF CACTUPAI = SPACES
    //
    // Middle Name
    //
    if (ws.coactup.cactupai.acsmnami.equals("*") || ws.coactup.cactupai.acsmnami.isSpaces()) {
      // COB(1297): MOVE LOW-VALUES           TO ACUP-NEW-CUST-MIDDLE-NAME
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustMiddleName.lowValues();
      // COB(1298): ELSE
    } else {
      // COB(1299): MOVE ACSMNAMI OF CACTUPAI TO ACUP-NEW-CUST-MIDDLE-NAME
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustMiddleName.setValue(
          ws.coactup.cactupai.acsmnami);
      // COB(1300): END-IF
    }
    // COB(1304): IF  ACSLNAMI OF CACTUPAI = '*'
    // COB(1304): OR  ACSLNAMI OF CACTUPAI = SPACES
    //
    // Last Name
    //
    if (ws.coactup.cactupai.acslnami.equals("*") || ws.coactup.cactupai.acslnami.isSpaces()) {
      // COB(1306): MOVE LOW-VALUES           TO ACUP-NEW-CUST-LAST-NAME
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustLastName.lowValues();
      // COB(1307): ELSE
    } else {
      // COB(1308): MOVE ACSLNAMI OF CACTUPAI TO ACUP-NEW-CUST-LAST-NAME
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustLastName.setValue(
          ws.coactup.cactupai.acslnami);
      // COB(1309): END-IF
    }
    // COB(1313): IF  ACSADL1I OF CACTUPAI = '*'
    // COB(1313): OR  ACSADL1I OF CACTUPAI = SPACES
    //
    // Address
    //
    if (ws.coactup.cactupai.acsadl1i.equals("*") || ws.coactup.cactupai.acsadl1i.isSpaces()) {
      // COB(1315): MOVE LOW-VALUES           TO ACUP-NEW-CUST-ADDR-LINE-1
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine1.lowValues();
      // COB(1316): ELSE
    } else {
      // COB(1317): MOVE ACSADL1I OF CACTUPAI TO ACUP-NEW-CUST-ADDR-LINE-1
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine1.setValue(
          ws.coactup.cactupai.acsadl1i);
      // COB(1318): END-IF
    }
    // COB(1320): IF  ACSADL2I OF CACTUPAI = '*'
    // COB(1320): OR  ACSADL2I OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsadl2i.equals("*") || ws.coactup.cactupai.acsadl2i.isSpaces()) {
      // COB(1322): MOVE LOW-VALUES           TO ACUP-NEW-CUST-ADDR-LINE-2
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine2.lowValues();
      // COB(1323): ELSE
    } else {
      // COB(1324): MOVE ACSADL2I OF CACTUPAI TO ACUP-NEW-CUST-ADDR-LINE-2
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine2.setValue(
          ws.coactup.cactupai.acsadl2i);
      // COB(1325): END-IF
    }
    // COB(1327): IF  ACSCITYI OF CACTUPAI = '*'
    // COB(1327): OR  ACSCITYI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acscityi.equals("*") || ws.coactup.cactupai.acscityi.isSpaces()) {
      // COB(1329): MOVE LOW-VALUES           TO ACUP-NEW-CUST-ADDR-LINE-3
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine3.lowValues();
      // COB(1330): ELSE
    } else {
      // COB(1331): MOVE ACSCITYI OF CACTUPAI TO ACUP-NEW-CUST-ADDR-LINE-3
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine3.setValue(
          ws.coactup.cactupai.acscityi);
      // COB(1332): END-IF
    }
    // COB(1334): IF  ACSSTTEI OF CACTUPAI = '*'
    // COB(1334): OR  ACSSTTEI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acssttei.equals("*") || ws.coactup.cactupai.acssttei.isSpaces()) {
      // COB(1336): MOVE LOW-VALUES           TO ACUP-NEW-CUST-ADDR-STATE-CD
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrStateCd.lowValues();
      // COB(1337): ELSE
    } else {
      // COB(1338): MOVE ACSSTTEI OF CACTUPAI TO ACUP-NEW-CUST-ADDR-STATE-CD
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrStateCd.setValue(
          ws.coactup.cactupai.acssttei);
      // COB(1339): END-IF
    }
    // COB(1341): IF  ACSCTRYI OF CACTUPAI = '*'
    // COB(1341): OR  ACSCTRYI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsctryi.equals("*") || ws.coactup.cactupai.acsctryi.isSpaces()) {
      // COB(1343): MOVE LOW-VALUES           TO ACUP-NEW-CUST-ADDR-COUNTRY-CD
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrCountryCd.lowValues();
      // COB(1344): ELSE
    } else {
      // COB(1345): MOVE ACSCTRYI OF CACTUPAI TO ACUP-NEW-CUST-ADDR-COUNTRY-CD
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrCountryCd.setValue(
          ws.coactup.cactupai.acsctryi);
      // COB(1346): END-IF
    }
    // COB(1348): IF  ACSZIPCI OF CACTUPAI = '*'
    // COB(1348): OR  ACSZIPCI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acszipci.equals("*") || ws.coactup.cactupai.acszipci.isSpaces()) {
      // COB(1350): MOVE LOW-VALUES           TO ACUP-NEW-CUST-ADDR-ZIP
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrZip.lowValues();
      // COB(1351): ELSE
    } else {
      // COB(1352): MOVE ACSZIPCI OF CACTUPAI TO ACUP-NEW-CUST-ADDR-ZIP
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrZip.setValue(
          ws.coactup.cactupai.acszipci);
      // COB(1353): END-IF
    }
    // COB(1355): IF  ACSPH1AI OF CACTUPAI = '*'
    // COB(1355): OR  ACSPH1AI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsph1ai.equals("*") || ws.coactup.cactupai.acsph1ai.isSpaces()) {
      // COB(1357): MOVE LOW-VALUES           TO ACUP-NEW-CUST-PHONE-NUM-1A
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
          .acupNewCustPhoneNum1a.lowValues();
      // COB(1358): ELSE
    } else {
      // COB(1359): MOVE ACSPH1AI OF CACTUPAI TO ACUP-NEW-CUST-PHONE-NUM-1A
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
          .acupNewCustPhoneNum1a.setValue(ws.coactup.cactupai.acsph1ai);
      // COB(1360): END-IF
    }
    // COB(1362): IF  ACSPH1BI OF CACTUPAI = '*'
    // COB(1362): OR  ACSPH1BI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsph1bi.equals("*") || ws.coactup.cactupai.acsph1bi.isSpaces()) {
      // COB(1364): MOVE LOW-VALUES           TO ACUP-NEW-CUST-PHONE-NUM-1B
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
          .acupNewCustPhoneNum1b.lowValues();
      // COB(1365): ELSE
    } else {
      // COB(1366): MOVE ACSPH1BI OF CACTUPAI TO ACUP-NEW-CUST-PHONE-NUM-1B
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
          .acupNewCustPhoneNum1b.setValue(ws.coactup.cactupai.acsph1bi);
      // COB(1367): END-IF
    }
    // COB(1369): IF  ACSPH1CI OF CACTUPAI = '*'
    // COB(1369): OR  ACSPH1CI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsph1ci.equals("*") || ws.coactup.cactupai.acsph1ci.isSpaces()) {
      // COB(1371): MOVE LOW-VALUES           TO ACUP-NEW-CUST-PHONE-NUM-1C
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
          .acupNewCustPhoneNum1c.lowValues();
      // COB(1372): ELSE
    } else {
      // COB(1373): MOVE ACSPH1CI OF CACTUPAI TO ACUP-NEW-CUST-PHONE-NUM-1C
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
          .acupNewCustPhoneNum1c.setValue(ws.coactup.cactupai.acsph1ci);
      // COB(1374): END-IF
    }
    // COB(1376): IF  ACSPH2AI OF CACTUPAI = '*'
    // COB(1376): OR  ACSPH2AI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsph2ai.equals("*") || ws.coactup.cactupai.acsph2ai.isSpaces()) {
      // COB(1378): MOVE LOW-VALUES           TO ACUP-NEW-CUST-PHONE-NUM-2A
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
          .acupNewCustPhoneNum2a.lowValues();
      // COB(1379): ELSE
    } else {
      // COB(1380): MOVE ACSPH2AI OF CACTUPAI TO ACUP-NEW-CUST-PHONE-NUM-2A
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
          .acupNewCustPhoneNum2a.setValue(ws.coactup.cactupai.acsph2ai);
      // COB(1381): END-IF
    }
    // COB(1383): IF  ACSPH2BI OF CACTUPAI = '*'
    // COB(1383): OR  ACSPH2BI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsph2bi.equals("*") || ws.coactup.cactupai.acsph2bi.isSpaces()) {
      // COB(1385): MOVE LOW-VALUES           TO ACUP-NEW-CUST-PHONE-NUM-2B
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
          .acupNewCustPhoneNum2b.lowValues();
      // COB(1386): ELSE
    } else {
      // COB(1387): MOVE ACSPH2BI OF CACTUPAI TO ACUP-NEW-CUST-PHONE-NUM-2B
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
          .acupNewCustPhoneNum2b.setValue(ws.coactup.cactupai.acsph2bi);
      // COB(1388): END-IF
    }
    // COB(1390): IF  ACSPH2CI OF CACTUPAI = '*'
    // COB(1390): OR  ACSPH2CI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsph2ci.equals("*") || ws.coactup.cactupai.acsph2ci.isSpaces()) {
      // COB(1392): MOVE LOW-VALUES           TO ACUP-NEW-CUST-PHONE-NUM-2C
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
          .acupNewCustPhoneNum2c.lowValues();
      // COB(1393): ELSE
    } else {
      // COB(1394): MOVE ACSPH2CI OF CACTUPAI TO ACUP-NEW-CUST-PHONE-NUM-2C
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
          .acupNewCustPhoneNum2c.setValue(ws.coactup.cactupai.acsph2ci);
      // COB(1395): END-IF
    }
    // COB(1399): IF  ACSGOVTI OF CACTUPAI = '*'
    // COB(1399): OR  ACSGOVTI OF CACTUPAI = SPACES
    //
    // Government Id
    //
    if (ws.coactup.cactupai.acsgovti.equals("*") || ws.coactup.cactupai.acsgovti.isSpaces()) {
      // COB(1401): MOVE LOW-VALUES           TO ACUP-NEW-CUST-GOVT-ISSUED-ID
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustGovtIssuedId.lowValues();
      // COB(1402): ELSE
    } else {
      // COB(1403): MOVE ACSGOVTI OF CACTUPAI TO ACUP-NEW-CUST-GOVT-ISSUED-ID
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustGovtIssuedId.setValue(
          ws.coactup.cactupai.acsgovti);
      // COB(1404): END-IF
    }
    // COB(1408): IF  ACSEFTCI OF CACTUPAI = '*'
    // COB(1408): OR  ACSEFTCI OF CACTUPAI = SPACES
    //
    // EFT Code
    //
    if (ws.coactup.cactupai.acseftci.equals("*") || ws.coactup.cactupai.acseftci.isSpaces()) {
      // COB(1410): MOVE LOW-VALUES           TO ACUP-NEW-CUST-EFT-ACCOUNT-ID
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustEftAccountId.lowValues();
      // COB(1411): ELSE
    } else {
      // COB(1412): MOVE ACSEFTCI OF CACTUPAI TO ACUP-NEW-CUST-EFT-ACCOUNT-ID
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustEftAccountId.setValue(
          ws.coactup.cactupai.acseftci);
      // COB(1413): END-IF
    }
    // COB(1417): IF  ACSPFLGI OF CACTUPAI = '*'
    // COB(1417): OR  ACSPFLGI OF CACTUPAI = SPACES
    //
    // Primary Holder Indicator
    //
    if (ws.coactup.cactupai.acspflgi.equals("*") || ws.coactup.cactupai.acspflgi.isSpaces()) {
      // COB(1419): MOVE LOW-VALUES            TO ACUP-NEW-CUST-PRI-HOLDER-IND
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPriHolderInd.lowValues();
      // COB(1420): ELSE
    } else {
      // COB(1421): MOVE ACSPFLGI OF CACTUPAI  TO ACUP-NEW-CUST-PRI-HOLDER-IND
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPriHolderInd.setValue(
          ws.coactup.cactupai.acspflgi);
      // COB(1422): END-IF
    }
  }

  protected void _1200EditMapInputs() {
    // COB(1429): SET INPUT-OK                  TO TRUE
    ws.wsMiscStorage.setInputOk(true);
    // COB(1431): IF  ACUP-DETAILS-NOT-FETCHED
    if (ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched()) {
      // COB(1433): PERFORM 1210-EDIT-ACCOUNT
      // COB(1433):    THRU 1210-EDIT-ACCOUNT-EXIT
      //         VALIDATE THE SEARCH KEYS
      _1210EditAccount();
      // COB(1436): MOVE LOW-VALUES           TO ACUP-OLD-ACCT-DATA
      ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.lowValues();
      // COB(1439): IF  FLG-ACCTFILTER-BLANK
      //
      //        IF THE SEARCH CONDITIONS HAVE PROBLEMS FLAG THEM
      if (ws.wsMiscStorage.flgAcctfilterBlank()) {
        // COB(1440): SET NO-SEARCH-CRITERIA-RECEIVED TO TRUE
        ws.wsMiscStorage.setNoSearchCriteriaReceived(true);
        // COB(1441): END-IF
      }
      // COB(1444): GO TO 1200-EDIT-MAP-INPUTS-EXIT
      //
      //        AT THIS STAGE. NO DETAILS FETCHED. NOTHING MORE TO EDIT.
      return;
      // COB(1445): ELSE
    } else {
      // COB(1446): CONTINUE
      // do nothing
      // COB(1447): END-IF
    }
    // COB(1450): SET FOUND-ACCOUNT-DATA        TO TRUE
    //
    //     SEARCH KEYS ALREADY VALIDATED AND DATA FETCHED
    ws.wsMiscStorage.setFoundAccountData(true);
    // COB(1451): SET FOUND-ACCT-IN-MASTER      TO TRUE
    ws.wsMiscStorage.wsFileReadFlags.setFoundAcctInMaster(true);
    // COB(1452): SET FLG-ACCTFILTER-ISVALID    TO TRUE
    ws.wsMiscStorage.setFlgAcctfilterIsvalid(true);
    // COB(1454): SET FOUND-CUST-IN-MASTER      TO TRUE
    ws.wsMiscStorage.wsFileReadFlags.setFoundCustInMaster(true);
    // COB(1455): SET FLG-CUSTFILTER-ISVALID    TO TRUE
    ws.wsMiscStorage.setFlgCustfilterIsvalid(true);
    // COB(1458): PERFORM 1205-COMPARE-OLD-NEW
    // COB(1458):    THRU 1205-COMPARE-OLD-NEW-EXIT
    //
    //
    _1205CompareOldNew();
    // COB(1461): IF  NO-CHANGES-FOUND
    // COB(1461): OR  ACUP-CHANGES-OK-NOT-CONFIRMED
    // COB(1461): OR  ACUP-CHANGES-OKAYED-AND-DONE
    if (ws.wsMiscStorage.noChangesFound()
        || ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkNotConfirmed()
        || ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedAndDone()) {
      // COB(1464): MOVE LOW-VALUES           TO WS-NON-KEY-FLAGS
      ws.wsMiscStorage.wsNonKeyFlags.lowValues();
      // COB(1465): GO TO 1200-EDIT-MAP-INPUTS-EXIT
      return;
      // COB(1466): END-IF
    }
    // COB(1468): SET ACUP-CHANGES-NOT-OK       TO TRUE
    ws.wsThisProgcommarea.acctUpdateScreenData.setAcupChangesNotOk(true);
    // COB(1470): MOVE 'Account Status'          TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Account Status");
    // COB(1471): MOVE ACUP-NEW-ACTIVE-STATUS    TO WS-EDIT-YES-NO
    ws.wsMiscStorage.wsGenericEdits.wsEditYesNo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewActiveStatus);
    // COB(1472): PERFORM 1220-EDIT-YESNO
    // COB(1472):    THRU 1220-EDIT-YESNO-EXIT
    _1220EditYesno();
    // COB(1474): MOVE WS-EDIT-YES-NO            TO WS-EDIT-ACCT-STATUS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAcctStatus.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditYesNo);
    // COB(1476): MOVE 'Open Date'              TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Open Date");
    // COB(1477): MOVE ACUP-NEW-OPEN-DATE       TO WS-EDIT-DATE-CCYYMMDD
    ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDate);
    // COB(1478): PERFORM EDIT-DATE-CCYYMMDD
    // COB(1478):    THRU EDIT-DATE-CCYYMMDD-EXIT
    rcNext = Flow.editDateCcyymmdd;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case editDateCcyymmdd:
          editDateCcyymmdd();
          rcNext = Flow.editYearCcyy;
          break;
        case editYearCcyy:
          rcNext = editYearCcyy();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editYearCcyyExit;
          }
          break;
        case editYearCcyyExit:
          editYearCcyyExit();
          rcNext = Flow.editMonth;
          break;
        case editMonth:
          rcNext = editMonth();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editMonthExit;
          }
          break;
        case editMonthExit:
          editMonthExit();
          rcNext = Flow.editDay;
          break;
        case editDay:
          rcNext = editDay();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editDayExit;
          }
          break;
        case editDayExit:
          editDayExit();
          rcNext = Flow.editDayMonthYear;
          break;
        case editDayMonthYear:
          rcNext = editDayMonthYear();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editDayMonthYearExit;
          }
          break;
        case editDayMonthYearExit:
          editDayMonthYearExit();
          rcNext = Flow.editDateLe;
          break;
        case editDateLe:
          rcNext = editDateLe();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editDateLeExit;
          }
          break;
        case editDateLeExit:
          editDateLeExit();
          rcNext = Flow.editDateCcyymmddExit;
          break;
        case editDateCcyymmddExit:
          editDateCcyymmddExit();
          rcNext = Flow.Exit;
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    // COB(1480): MOVE WS-EDIT-DATE-FLGS        TO WS-EDIT-OPEN-DATE-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.setValue(
        ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs);
    // COB(1482): MOVE 'Credit Limit'           TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Credit Limit");
    // COB(1483): MOVE ACUP-NEW-CREDIT-LIMIT-X  TO WS-EDIT-SIGNED-NUMBER-9V2-X
    ws.wsMiscStorage.wsGenericEdits.wsEditSignedNumber9v2X.setValue(
        ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCreditLimitX);
    // COB(1484): PERFORM 1250-EDIT-SIGNED-9V2
    // COB(1484):    THRU 1250-EDIT-SIGNED-9V2-EXIT
    _1250EditSigned9v2();
    // COB(1486): MOVE WS-FLG-SIGNED-NUMBER-EDIT  TO WS-EDIT-CREDIT-LIMIT
    ws.wsMiscStorage.wsNonKeyFlags.wsEditCreditLimit.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsFlgSignedNumberEdit);
    // COB(1488): MOVE 'Expiry Date'            TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Expiry Date");
    // COB(1489): MOVE ACUP-NEW-EXPIRAION-DATE  TO WS-EDIT-DATE-CCYYMMDD
    ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDate);
    // COB(1490): PERFORM EDIT-DATE-CCYYMMDD
    // COB(1490):    THRU EDIT-DATE-CCYYMMDD-EXIT
    rcNext = Flow.editDateCcyymmdd;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case editDateCcyymmdd:
          editDateCcyymmdd();
          rcNext = Flow.editYearCcyy;
          break;
        case editYearCcyy:
          rcNext = editYearCcyy();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editYearCcyyExit;
          }
          break;
        case editYearCcyyExit:
          editYearCcyyExit();
          rcNext = Flow.editMonth;
          break;
        case editMonth:
          rcNext = editMonth();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editMonthExit;
          }
          break;
        case editMonthExit:
          editMonthExit();
          rcNext = Flow.editDay;
          break;
        case editDay:
          rcNext = editDay();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editDayExit;
          }
          break;
        case editDayExit:
          editDayExit();
          rcNext = Flow.editDayMonthYear;
          break;
        case editDayMonthYear:
          rcNext = editDayMonthYear();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editDayMonthYearExit;
          }
          break;
        case editDayMonthYearExit:
          editDayMonthYearExit();
          rcNext = Flow.editDateLe;
          break;
        case editDateLe:
          rcNext = editDateLe();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editDateLeExit;
          }
          break;
        case editDateLeExit:
          editDateLeExit();
          rcNext = Flow.editDateCcyymmddExit;
          break;
        case editDateCcyymmddExit:
          editDateCcyymmddExit();
          rcNext = Flow.Exit;
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    // COB(1492): MOVE WS-EDIT-DATE-FLGS        TO WS-EXPIRY-DATE-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.setValue(
        ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs);
    // COB(1494): MOVE 'Cash Credit Limit'      TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Cash Credit Limit");
    // COB(1495): MOVE ACUP-NEW-CASH-CREDIT-LIMIT-X
    // COB(1495):                               TO WS-EDIT-SIGNED-NUMBER-9V2-X
    ws.wsMiscStorage.wsGenericEdits.wsEditSignedNumber9v2X.setValue(
        ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCashCreditLimitX);
    // COB(1497): PERFORM 1250-EDIT-SIGNED-9V2
    // COB(1497):    THRU 1250-EDIT-SIGNED-9V2-EXIT
    _1250EditSigned9v2();
    // COB(1499): MOVE WS-FLG-SIGNED-NUMBER-EDIT TO WS-EDIT-CASH-CREDIT-LIMIT
    ws.wsMiscStorage.wsNonKeyFlags.wsEditCashCreditLimit.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsFlgSignedNumberEdit);
    // COB(1501): MOVE 'Reissue Date'           TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Reissue Date");
    // COB(1502): MOVE ACUP-NEW-REISSUE-DATE    TO WS-EDIT-DATE-CCYYMMDD
    ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDate);
    // COB(1503): PERFORM EDIT-DATE-CCYYMMDD
    // COB(1503):    THRU EDIT-DATE-CCYYMMDD-EXIT
    rcNext = Flow.editDateCcyymmdd;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case editDateCcyymmdd:
          editDateCcyymmdd();
          rcNext = Flow.editYearCcyy;
          break;
        case editYearCcyy:
          rcNext = editYearCcyy();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editYearCcyyExit;
          }
          break;
        case editYearCcyyExit:
          editYearCcyyExit();
          rcNext = Flow.editMonth;
          break;
        case editMonth:
          rcNext = editMonth();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editMonthExit;
          }
          break;
        case editMonthExit:
          editMonthExit();
          rcNext = Flow.editDay;
          break;
        case editDay:
          rcNext = editDay();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editDayExit;
          }
          break;
        case editDayExit:
          editDayExit();
          rcNext = Flow.editDayMonthYear;
          break;
        case editDayMonthYear:
          rcNext = editDayMonthYear();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editDayMonthYearExit;
          }
          break;
        case editDayMonthYearExit:
          editDayMonthYearExit();
          rcNext = Flow.editDateLe;
          break;
        case editDateLe:
          rcNext = editDateLe();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editDateLeExit;
          }
          break;
        case editDateLeExit:
          editDateLeExit();
          rcNext = Flow.editDateCcyymmddExit;
          break;
        case editDateCcyymmddExit:
          editDateCcyymmddExit();
          rcNext = Flow.Exit;
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    // COB(1505): MOVE WS-EDIT-DATE-FLGS        TO WS-EDIT-REISSUE-DATE-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.setValue(
        ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs);
    // COB(1507): MOVE 'Current Balance'        TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Current Balance");
    // COB(1508): MOVE ACUP-NEW-CURR-BAL-X      TO WS-EDIT-SIGNED-NUMBER-9V2-X
    ws.wsMiscStorage.wsGenericEdits.wsEditSignedNumber9v2X.setValue(
        ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrBalX);
    // COB(1509): PERFORM 1250-EDIT-SIGNED-9V2
    // COB(1509):    THRU 1250-EDIT-SIGNED-9V2-EXIT
    _1250EditSigned9v2();
    // COB(1511): MOVE WS-FLG-SIGNED-NUMBER-EDIT   TO WS-EDIT-CURR-BAL
    ws.wsMiscStorage.wsNonKeyFlags.wsEditCurrBal.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsFlgSignedNumberEdit);
    // COB(1513): MOVE 'Current Cycle Credit Limit' TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Current Cycle Credit Limit");
    // COB(1514): MOVE ACUP-NEW-CURR-CYC-CREDIT-X
    // COB(1514):                               TO WS-EDIT-SIGNED-NUMBER-9V2-X
    ws.wsMiscStorage.wsGenericEdits.wsEditSignedNumber9v2X.setValue(
        ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycCreditX);
    // COB(1516): PERFORM 1250-EDIT-SIGNED-9V2
    // COB(1516):    THRU 1250-EDIT-SIGNED-9V2-EXIT
    _1250EditSigned9v2();
    // COB(1518): MOVE WS-FLG-SIGNED-NUMBER-EDIT   TO WS-EDIT-CURR-CYC-CREDIT
    ws.wsMiscStorage.wsNonKeyFlags.wsEditCurrCycCredit.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsFlgSignedNumberEdit);
    // COB(1520): MOVE 'Current Cycle Debit Limit' TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Current Cycle Debit Limit");
    // COB(1521): MOVE ACUP-NEW-CURR-CYC-DEBIT-X
    // COB(1521):                               TO WS-EDIT-SIGNED-NUMBER-9V2-X
    ws.wsMiscStorage.wsGenericEdits.wsEditSignedNumber9v2X.setValue(
        ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycDebitX);
    // COB(1523): PERFORM 1250-EDIT-SIGNED-9V2
    // COB(1523):    THRU 1250-EDIT-SIGNED-9V2-EXIT
    _1250EditSigned9v2();
    // COB(1525): MOVE WS-FLG-SIGNED-NUMBER-EDIT   TO WS-EDIT-CURR-CYC-DEBIT
    ws.wsMiscStorage.wsNonKeyFlags.wsEditCurrCycDebit.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsFlgSignedNumberEdit);
    // COB(1527): MOVE 'SSN'                    TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("SSN");
    // COB(1528): PERFORM 1265-EDIT-US-SSN
    // COB(1528):    THRU 1265-EDIT-US-SSN-EXIT
    _1265EditUsSsn();
    // COB(1531): MOVE 'Date of Birth'          TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Date of Birth");
    // COB(1532): MOVE   ACUP-NEW-CUST-DOB-YYYY-MM-DD
    // COB(1532):                               TO WS-EDIT-DATE-CCYYMMDD
    ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobYyyyMmDd);
    // COB(1534): PERFORM EDIT-DATE-CCYYMMDD
    // COB(1534):    THRU EDIT-DATE-CCYYMMDD-EXIT
    rcNext = Flow.editDateCcyymmdd;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case editDateCcyymmdd:
          editDateCcyymmdd();
          rcNext = Flow.editYearCcyy;
          break;
        case editYearCcyy:
          rcNext = editYearCcyy();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editYearCcyyExit;
          }
          break;
        case editYearCcyyExit:
          editYearCcyyExit();
          rcNext = Flow.editMonth;
          break;
        case editMonth:
          rcNext = editMonth();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editMonthExit;
          }
          break;
        case editMonthExit:
          editMonthExit();
          rcNext = Flow.editDay;
          break;
        case editDay:
          rcNext = editDay();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editDayExit;
          }
          break;
        case editDayExit:
          editDayExit();
          rcNext = Flow.editDayMonthYear;
          break;
        case editDayMonthYear:
          rcNext = editDayMonthYear();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editDayMonthYearExit;
          }
          break;
        case editDayMonthYearExit:
          editDayMonthYearExit();
          rcNext = Flow.editDateLe;
          break;
        case editDateLe:
          rcNext = editDateLe();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editDateLeExit;
          }
          break;
        case editDateLeExit:
          editDateLeExit();
          rcNext = Flow.editDateCcyymmddExit;
          break;
        case editDateCcyymmddExit:
          editDateCcyymmddExit();
          rcNext = Flow.Exit;
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    // COB(1536): MOVE WS-EDIT-DATE-FLGS        TO WS-EDIT-DT-OF-BIRTH-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.setValue(
        ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs);
    // COB(1537): IF WS-EDIT-DT-OF-BIRTH-ISVALID
    if (ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.wsEditDtOfBirthIsvalid()) {
      // COB(1538): PERFORM  EDIT-DATE-OF-BIRTH
      // COB(1538):    THRU  EDIT-DATE-OF-BIRTH-EXIT
      editDateOfBirth();
      // COB(1540): MOVE WS-EDIT-DATE-FLGS    TO WS-EDIT-DT-OF-BIRTH-FLGS
      ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.setValue(
          ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs);
      // COB(1541): END-IF
    }
    // COB(1543): MOVE 'FICO Score'             TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("FICO Score");
    // COB(1544): MOVE ACUP-NEW-CUST-FICO-SCORE-X
    // COB(1544):                               TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFicoScoreX);
    // COB(1546): MOVE 3                        TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(3);
    // COB(1547): PERFORM 1245-EDIT-NUM-REQD
    // COB(1547):    THRU 1245-EDIT-NUM-REQD-EXIT
    _1245EditNumReqd();
    // COB(1549): MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
    // COB(1549):                               TO WS-EDIT-FICO-SCORE-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditFicoScoreFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);
    // COB(1551): IF FLG-FICO-SCORE-ISVALID
    if (ws.wsMiscStorage.wsNonKeyFlags.flgFicoScoreIsvalid()) {
      // COB(1552): PERFORM  1275-EDIT-FICO-SCORE
      // COB(1552):    THRU  1275-EDIT-FICO-SCORE-EXIT
      _1275EditFicoScore();
      // COB(1554): END-IF
    }
    // COB(1558): MOVE 'First Name'             TO WS-EDIT-VARIABLE-NAME
    // *****************************************************************
    //     Edit names
    // *****************************************************************
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("First Name");
    // COB(1559): MOVE ACUP-NEW-CUST-FIRST-NAME TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFirstName);
    // COB(1560): MOVE 25                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(25);
    // COB(1561): PERFORM 1225-EDIT-ALPHA-REQD
    // COB(1561):    THRU 1225-EDIT-ALPHA-REQD-EXIT
    _1225EditAlphaReqd();
    // COB(1563): MOVE WS-EDIT-ALPHA-ONLY-FLAGS
    // COB(1563):                               TO WS-EDIT-FIRST-NAME-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.wsEditFirstNameFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphaOnlyFlags);
    // COB(1566): MOVE 'Middle Name'            TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Middle Name");
    // COB(1567): MOVE ACUP-NEW-CUST-MIDDLE-NAME TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustMiddleName);
    // COB(1568): MOVE 25                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(25);
    // COB(1569): PERFORM 1235-EDIT-ALPHA-OPT
    // COB(1569):    THRU 1235-EDIT-ALPHA-OPT-EXIT
    _1235EditAlphaOpt();
    // COB(1571): MOVE WS-EDIT-ALPHA-ONLY-FLAGS
    // COB(1571):                               TO WS-EDIT-MIDDLE-NAME-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.wsEditMiddleNameFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphaOnlyFlags);
    // COB(1574): MOVE 'Last Name'              TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Last Name");
    // COB(1575): MOVE ACUP-NEW-CUST-LAST-NAME  TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustLastName);
    // COB(1576): MOVE 25                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(25);
    // COB(1577): PERFORM 1225-EDIT-ALPHA-REQD
    // COB(1577):    THRU 1225-EDIT-ALPHA-REQD-EXIT
    _1225EditAlphaReqd();
    // COB(1579): MOVE WS-EDIT-ALPHA-ONLY-FLAGS
    // COB(1579):                              TO WS-EDIT-LAST-NAME-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.wsEditLastNameFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphaOnlyFlags);
    // COB(1582): MOVE 'Address Line 1'         TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Address Line 1");
    // COB(1583): MOVE ACUP-NEW-CUST-ADDR-LINE-1 TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine1);
    // COB(1584): MOVE 50                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(50);
    // COB(1585): PERFORM 1215-EDIT-MANDATORY
    // COB(1585):    THRU 1215-EDIT-MANDATORY-EXIT
    _1215EditMandatory();
    // COB(1587): MOVE WS-EDIT-MANDATORY-FLAGS
    // COB(1587):                               TO WS-EDIT-ADDRESS-LINE-1-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditAddressLine1Flgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditMandatoryFlags);
    // COB(1590): MOVE 'State'                  TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("State");
    // COB(1591): MOVE ACUP-NEW-CUST-ADDR-STATE-CD TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrStateCd);
    // COB(1592): MOVE 2                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(2);
    // COB(1593): PERFORM 1225-EDIT-ALPHA-REQD
    // COB(1593):    THRU 1225-EDIT-ALPHA-REQD-EXIT
    _1225EditAlphaReqd();
    // COB(1595): MOVE WS-EDIT-ALPHA-ONLY-FLAGS
    // COB(1595):                               TO WS-EDIT-STATE-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditStateFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphaOnlyFlags);
    // COB(1597): IF FLG-ALPHA-ISVALID
    if (ws.wsMiscStorage.wsGenericEdits.flgAlphaIsvalid()) {
      // COB(1598): PERFORM 1270-EDIT-US-STATE-CD
      // COB(1598):    THRU 1270-EDIT-US-STATE-CD-EXIT
      _1270EditUsStateCd();
      // COB(1600): END-IF
    }
    // COB(1603): MOVE 'Zip'                    TO WS-EDIT-VARIABLE-NAME
    //
    //
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Zip");
    // COB(1604): MOVE ACUP-NEW-CUST-ADDR-ZIP   TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrZip);
    // COB(1605): MOVE 5                        TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(5);
    // COB(1606): PERFORM 1245-EDIT-NUM-REQD
    // COB(1606):    THRU 1245-EDIT-NUM-REQD-EXIT
    _1245EditNumReqd();
    // COB(1608): MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
    // COB(1608):                               TO WS-EDIT-ZIPCODE-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditZipcodeFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);
    // COB(1613): MOVE 'City'                   TO WS-EDIT-VARIABLE-NAME
    //
    //     Address Line 2 is optional
    //     MOVE 'Address Line 2'         TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("City");
    // COB(1614): MOVE ACUP-NEW-CUST-ADDR-LINE-3 TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine3);
    // COB(1615): MOVE 50                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(50);
    // COB(1616): PERFORM 1225-EDIT-ALPHA-REQD
    // COB(1616):    THRU 1225-EDIT-ALPHA-REQD-EXIT
    _1225EditAlphaReqd();
    // COB(1618): MOVE WS-EDIT-ALPHA-ONLY-FLAGS
    // COB(1618):                               TO WS-EDIT-CITY-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditCityFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphaOnlyFlags);
    // COB(1621): MOVE 'Country'                TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Country");
    // COB(1622): MOVE ACUP-NEW-CUST-ADDR-COUNTRY-CD
    // COB(1622):                              TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrCountryCd);
    // COB(1624): MOVE 3                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(3);
    // COB(1625): PERFORM 1225-EDIT-ALPHA-REQD
    // COB(1625):    THRU 1225-EDIT-ALPHA-REQD-EXIT
    _1225EditAlphaReqd();
    // COB(1627): MOVE WS-EDIT-ALPHA-ONLY-FLAGS
    // COB(1627):                               TO WS-EDIT-COUNTRY-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditCountryFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphaOnlyFlags);
    // COB(1630): MOVE 'Phone Number 1'         TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Phone Number 1");
    // COB(1631): MOVE ACUP-NEW-CUST-PHONE-NUM-1
    // COB(1631):                               TO WS-EDIT-US-PHONE-NUM
    ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNum.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1);
    // COB(1633): PERFORM 1260-EDIT-US-PHONE-NUM
    // COB(1633):    THRU 1260-EDIT-US-PHONE-NUM-EXIT
    rcNext = Flow._1260EditUsPhoneNum;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case _1260EditUsPhoneNum:
          rcNext = _1260EditUsPhoneNum();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editAreaCode;
          }
          break;
        case editUsPhoneExit:
          editUsPhoneExit();
          rcNext = Flow.Exit;
          break;
        case editAreaCode:
          rcNext = editAreaCode();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editUsPhonePrefix;
          }
          break;
        case editUsPhonePrefix:
          rcNext = editUsPhonePrefix();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editUsPhoneLinenum;
          }
          break;
        case editUsPhoneLinenum:
          rcNext = editUsPhoneLinenum();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editUsPhoneExit;
          }
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    // COB(1635): MOVE WS-EDIT-US-PHONE-NUM-FLGS
    // COB(1635):                               TO  WS-EDIT-PHONE-NUM-1-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs);
    // COB(1638): MOVE 'Phone Number 2'         TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Phone Number 2");
    // COB(1639): MOVE ACUP-NEW-CUST-PHONE-NUM-2
    // COB(1639):                               TO WS-EDIT-US-PHONE-NUM
    ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNum.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2);
    // COB(1641): PERFORM 1260-EDIT-US-PHONE-NUM
    // COB(1641):    THRU 1260-EDIT-US-PHONE-NUM-EXIT
    rcNext = Flow._1260EditUsPhoneNum;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case _1260EditUsPhoneNum:
          rcNext = _1260EditUsPhoneNum();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editAreaCode;
          }
          break;
        case editUsPhoneExit:
          editUsPhoneExit();
          rcNext = Flow.Exit;
          break;
        case editAreaCode:
          rcNext = editAreaCode();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editUsPhonePrefix;
          }
          break;
        case editUsPhonePrefix:
          rcNext = editUsPhonePrefix();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editUsPhoneLinenum;
          }
          break;
        case editUsPhoneLinenum:
          rcNext = editUsPhoneLinenum();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow.editUsPhoneExit;
          }
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    // COB(1643): MOVE WS-EDIT-US-PHONE-NUM-FLGS
    // COB(1643):                               TO WS-EDIT-PHONE-NUM-2-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs);
    // COB(1646): MOVE 'EFT Account Id'         TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("EFT Account Id");
    // COB(1647): MOVE ACUP-NEW-CUST-EFT-ACCOUNT-ID
    // COB(1647):                               TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustEftAccountId);
    // COB(1649): MOVE 10                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(10);
    // COB(1650): PERFORM 1245-EDIT-NUM-REQD
    // COB(1650):    THRU 1245-EDIT-NUM-REQD-EXIT
    _1245EditNumReqd();
    // COB(1652): MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
    // COB(1652):                               TO WS-EFT-ACCOUNT-ID-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEftAccountIdFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);
    // COB(1655): MOVE 'Primary Card Holder'    TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Primary Card Holder");
    // COB(1656): MOVE ACUP-NEW-CUST-PRI-HOLDER-IND
    // COB(1656):                               TO WS-EDIT-YES-NO
    ws.wsMiscStorage.wsGenericEdits.wsEditYesNo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPriHolderInd);
    // COB(1658): PERFORM 1220-EDIT-YESNO
    // COB(1658):    THRU 1220-EDIT-YESNO-EXIT
    _1220EditYesno();
    // COB(1660): MOVE WS-EDIT-YES-NO           TO WS-EDIT-PRI-CARDHOLDER
    ws.wsMiscStorage.wsNonKeyFlags.wsEditPriCardholder.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditYesNo);
    // COB(1663): IF  FLG-STATE-ISVALID
    // COB(1663): AND FLG-ZIPCODE-ISVALID
    //
    //     Cross field edits begin here
    if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgStateIsvalid()
        && ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgZipcodeIsvalid()) {
      // COB(1665): PERFORM 1280-EDIT-US-STATE-ZIP-CD
      // COB(1665):    THRU 1280-EDIT-US-STATE-ZIP-CD-EXIT
      _1280EditUsStateZipCd();
      // COB(1667): END-IF
    }
    // COB(1669): IF INPUT-ERROR
    if (ws.wsMiscStorage.inputError()) {
      // COB(1670): CONTINUE
      // do nothing
      // COB(1671): ELSE
    } else {
      // COB(1672): SET ACUP-CHANGES-OK-NOT-CONFIRMED TO TRUE
      ws.wsThisProgcommarea.acctUpdateScreenData.setAcupChangesOkNotConfirmed(true);
      // COB(1673): END-IF
    }
  }

  protected void _1205CompareOldNew() {
    // COB(1680): SET NO-CHANGES-FOUND           TO TRUE
    ws.wsMiscStorage.setNoChangesFound(true);
    // COB(1682): IF  ACUP-NEW-ACCT-ID-X         = ACUP-OLD-ACCT-ID-X
    // COB(1682): AND FUNCTION UPPER-CASE (
    // COB(1682):     ACUP-NEW-ACTIVE-STATUS)    =
    // COB(1682):     FUNCTION UPPER-CASE (
    // COB(1682):     ACUP-OLD-ACTIVE-STATUS)
    // COB(1682): AND ACUP-NEW-CURR-BAL          = ACUP-OLD-CURR-BAL
    // COB(1682): AND ACUP-NEW-CREDIT-LIMIT      = ACUP-OLD-CREDIT-LIMIT
    // COB(1682): AND ACUP-NEW-CASH-CREDIT-LIMIT = ACUP-OLD-CASH-CREDIT-LIMIT
    // COB(1682): AND ACUP-NEW-OPEN-DATE         = ACUP-OLD-OPEN-DATE
    // COB(1682): AND ACUP-NEW-EXPIRAION-DATE    = ACUP-OLD-EXPIRAION-DATE
    // COB(1682): AND ACUP-NEW-REISSUE-DATE      = ACUP-OLD-REISSUE-DATE
    // COB(1682): AND ACUP-NEW-CURR-CYC-CREDIT   = ACUP-OLD-CURR-CYC-CREDIT
    // COB(1682): AND ACUP-NEW-CURR-CYC-DEBIT    = ACUP-OLD-CURR-CYC-DEBIT
    // COB(1682): AND FUNCTION UPPER-CASE (
    // COB(1682):     FUNCTION TRIM (ACUP-NEW-GROUP-ID))=
    // COB(1682):     FUNCTION UPPER-CASE (
    // COB(1682):     FUNCTION TRIM (ACUP-OLD-GROUP-ID))
    if (ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewAcctIdX.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldAcctIdX)
        && ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewActiveStatus
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldActiveStatus
                    .toUpperCase())
        && ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrBal.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrBal)
        && ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCreditLimit.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCreditLimit)
        && ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCashCreditLimit.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCashCreditLimit)
        && ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDate.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldOpenDate)
        && ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDate.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldExpiraionDate)
        && ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDate.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldReissueDate)
        && ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrCycCredit.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrCycCredit)
        && ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrCycDebit.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrCycDebit)
        && ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewGroupId
            .trim()
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldAcctData
                    .acupOldGroupId
                    .trim()
                    .toUpperCase())) {
      // COB(1699): CONTINUE
      // do nothing
      // COB(1700): ELSE
    } else {
      // COB(1701): SET CHANGE-HAS-OCCURRED   TO TRUE
      ws.wsMiscStorage.setChangeHasOccurred(true);
      // COB(1702): GO TO 1205-COMPARE-OLD-NEW-EXIT
      return;
      // COB(1703): END-IF
    }
    // COB(1706): IF  FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-NEW-CUST-ID-X))=
    // COB(1706):     FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-OLD-CUST-ID-X))
    // COB(1706): AND FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-NEW-CUST-FIRST-NAME))=
    // COB(1706):     FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-OLD-CUST-FIRST-NAME))
    // COB(1706): AND FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-NEW-CUST-MIDDLE-NAME))=
    // COB(1706):     FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-OLD-CUST-MIDDLE-NAME))
    // COB(1706): AND FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-NEW-CUST-LAST-NAME))=
    // COB(1706):     FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-OLD-CUST-LAST-NAME))
    // COB(1706): AND FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-NEW-CUST-ADDR-LINE-1))=
    // COB(1706):     FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-OLD-CUST-ADDR-LINE-1))
    // COB(1706): AND FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-NEW-CUST-ADDR-LINE-2))=
    // COB(1706):     FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-OLD-CUST-ADDR-LINE-2))
    // COB(1706): AND FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-NEW-CUST-ADDR-LINE-3))=
    // COB(1706):     FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-OLD-CUST-ADDR-LINE-3))
    // COB(1706): AND FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-NEW-CUST-ADDR-STATE-CD))=
    // COB(1706):     FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-OLD-CUST-ADDR-STATE-CD))
    // COB(1706): AND FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-NEW-CUST-ADDR-COUNTRY-CD))=
    // COB(1706):     FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-OLD-CUST-ADDR-COUNTRY-CD))
    // COB(1706): AND FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-NEW-CUST-ADDR-ZIP))=
    // COB(1706):     FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-OLD-CUST-ADDR-ZIP))
    // COB(1706): AND ACUP-NEW-CUST-PHONE-NUM-1A = ACUP-OLD-CUST-PHONE-NUM-1A
    // COB(1706): AND ACUP-NEW-CUST-PHONE-NUM-1B = ACUP-OLD-CUST-PHONE-NUM-1B
    // COB(1706): AND ACUP-NEW-CUST-PHONE-NUM-1C = ACUP-OLD-CUST-PHONE-NUM-1C
    // COB(1706): AND ACUP-NEW-CUST-PHONE-NUM-2A = ACUP-OLD-CUST-PHONE-NUM-2A
    // COB(1706): AND ACUP-NEW-CUST-PHONE-NUM-2B = ACUP-OLD-CUST-PHONE-NUM-2B
    // COB(1706): AND ACUP-NEW-CUST-PHONE-NUM-2C = ACUP-OLD-CUST-PHONE-NUM-2C
    // COB(1706): AND ACUP-NEW-CUST-SSN-X       = ACUP-OLD-CUST-SSN-X
    // COB(1706): AND FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-NEW-CUST-GOVT-ISSUED-ID ))=
    // COB(1706):     FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-OLD-CUST-GOVT-ISSUED-ID))
    // COB(1706): AND ACUP-NEW-CUST-DOB-YYYY-MM-DD
    // COB(1706):                           = ACUP-OLD-CUST-DOB-YYYY-MM-DD
    // COB(1706): AND ACUP-NEW-CUST-EFT-ACCOUNT-ID
    // COB(1706):                           = ACUP-OLD-CUST-EFT-ACCOUNT-ID
    // COB(1706): AND FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-NEW-CUST-PRI-HOLDER-IND))=
    // COB(1706):     FUNCTION UPPER-CASE (
    // COB(1706):     FUNCTION TRIM (ACUP-OLD-CUST-PRI-HOLDER-IND))
    // COB(1706): AND ACUP-NEW-CUST-FICO-SCORE-X
    // COB(1706):                           = ACUP-OLD-CUST-FICO-SCORE-X
    //
    //
    if (ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustIdX
            .trim()
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldCustData
                    .acupOldCustIdX
                    .trim()
                    .toUpperCase())
        && ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustFirstName
            .trim()
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldCustData
                    .acupOldCustFirstName
                    .trim()
                    .toUpperCase())
        && ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustMiddleName
            .trim()
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldCustData
                    .acupOldCustMiddleName
                    .trim()
                    .toUpperCase())
        && ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustLastName
            .trim()
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldCustData
                    .acupOldCustLastName
                    .trim()
                    .toUpperCase())
        && ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustAddrLine1
            .trim()
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldCustData
                    .acupOldCustAddrLine1
                    .trim()
                    .toUpperCase())
        && ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustAddrLine2
            .trim()
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldCustData
                    .acupOldCustAddrLine2
                    .trim()
                    .toUpperCase())
        && ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustAddrLine3
            .trim()
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldCustData
                    .acupOldCustAddrLine3
                    .trim()
                    .toUpperCase())
        && ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustAddrStateCd
            .trim()
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldCustData
                    .acupOldCustAddrStateCd
                    .trim()
                    .toUpperCase())
        && ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustAddrCountryCd
            .trim()
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldCustData
                    .acupOldCustAddrCountryCd
                    .trim()
                    .toUpperCase())
        && ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustAddrZip
            .trim()
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldCustData
                    .acupOldCustAddrZip
                    .trim()
                    .toUpperCase())
        && ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
            .acupNewCustPhoneNum1a.equals(
            ws.wsThisProgcommarea
                .acupOldDetails
                .acupOldCustData
                .acupOldCustPhoneNum1X
                .acupOldCustPhoneNum1a)
        && ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
            .acupNewCustPhoneNum1b.equals(
            ws.wsThisProgcommarea
                .acupOldDetails
                .acupOldCustData
                .acupOldCustPhoneNum1X
                .acupOldCustPhoneNum1b)
        && ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
            .acupNewCustPhoneNum1c.equals(
            ws.wsThisProgcommarea
                .acupOldDetails
                .acupOldCustData
                .acupOldCustPhoneNum1X
                .acupOldCustPhoneNum1c)
        && ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
            .acupNewCustPhoneNum2a.equals(
            ws.wsThisProgcommarea
                .acupOldDetails
                .acupOldCustData
                .acupOldCustPhoneNum2X
                .acupOldCustPhoneNum2a)
        && ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
            .acupNewCustPhoneNum2b.equals(
            ws.wsThisProgcommarea
                .acupOldDetails
                .acupOldCustData
                .acupOldCustPhoneNum2X
                .acupOldCustPhoneNum2b)
        && ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
            .acupNewCustPhoneNum2c.equals(
            ws.wsThisProgcommarea
                .acupOldDetails
                .acupOldCustData
                .acupOldCustPhoneNum2X
                .acupOldCustPhoneNum2c)
        && ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustSsnX)
        && ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustGovtIssuedId
            .trim()
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldCustData
                    .acupOldCustGovtIssuedId
                    .trim()
                    .toUpperCase())
        && ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobYyyyMmDd.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustDobYyyyMmDd)
        && ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustEftAccountId.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustEftAccountId)
        && ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPriHolderInd
            .trim()
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldCustData
                    .acupOldCustPriHolderInd
                    .trim()
                    .toUpperCase())
        && ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFicoScoreX.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustFicoScoreX)) {
      // COB(1767): SET NO-CHANGES-DETECTED   TO TRUE
      ws.wsMiscStorage.setNoChangesDetected(true);
      // COB(1768): ELSE
    } else {
      // COB(1769): SET CHANGE-HAS-OCCURRED   TO TRUE
      ws.wsMiscStorage.setChangeHasOccurred(true);
      // COB(1770): GO TO 1205-COMPARE-OLD-NEW-EXIT
      return;
      // COB(1771): END-IF
    }
  }

  /***
   *
   */
  protected void _1210EditAccount() {
    // COB(1782): SET FLG-ACCTFILTER-NOT-OK    TO TRUE
    ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
    // COB(1785): IF CC-ACCT-ID   EQUAL LOW-VALUES
    // COB(1785): OR CC-ACCT-ID   EQUAL SPACES
    //
    //     Not supplied
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isLowValues()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isSpaces()) {
      // COB(1787): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1788): SET FLG-ACCTFILTER-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterBlank(true);
      // COB(1789): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1790): SET WS-PROMPT-FOR-ACCT TO TRUE
        ws.wsMiscStorage.setWsPromptForAcct(true);
        // COB(1791): END-IF
      }
      // COB(1792): MOVE ZEROES               TO CDEMO-ACCT-ID
      // COB(1792):                              ACUP-NEW-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewAcctId.zeros();
      // COB(1794): GO TO  1210-EDIT-ACCOUNT-EXIT
      return;
      // COB(1795): END-IF
    }
    // COB(1799): MOVE CC-ACCT-ID              TO ACUP-NEW-ACCT-ID
    //
    //     Not numeric
    //     Not 11 characters
    ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewAcctId.setValue(
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
    // COB(1800): IF CC-ACCT-ID   IS NOT NUMERIC
    // COB(1800): OR CC-ACCT-ID-N EQUAL ZEROS
    if (!ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isNumeric()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctIdN.equals(0)) {
      // COB(1802): SET INPUT-ERROR TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1803): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1804): STRING
        // COB(1804):  'Account Number if supplied must be a 11 digit'
        // COB(1804):  ' Non-Zero Number'
        // COB(1804): DELIMITED BY SIZE
        // COB(1804): INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            "Account Number if supplied must be a 11 digit", " Non-Zero Number");
        // COB(1809): END-IF
      }
      // COB(1810): MOVE ZEROES               TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
      // COB(1811): GO TO 1210-EDIT-ACCOUNT-EXIT
      return;
      // COB(1812): ELSE
    } else {
      // COB(1813): MOVE CC-ACCT-ID TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.setValue(
          ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
      // COB(1814): SET FLG-ACCTFILTER-ISVALID TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterIsvalid(true);
      // COB(1815): END-IF
    }
  }

  /***/
  protected void _1215EditMandatory() {
    // COB(1824): SET FLG-MANDATORY-NOT-OK    TO TRUE
    //     Initialize
    ws.wsMiscStorage.wsGenericEdits.setFlgMandatoryNotOk(true);
    // COB(1827): IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1827):                             EQUAL LOW-VALUES
    // COB(1827): OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1827):                             EQUAL SPACES
    // COB(1827): OR FUNCTION LENGTH(FUNCTION TRIM(
    // COB(1827):    WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
    //
    //     Not supplied
    if (ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .isLowValues()
        || ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .isSpaces()
        || ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .trim()
            .length()
            .equals(0)) {
      // COB(1834): SET INPUT-ERROR          TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1835): SET FLG-MANDATORY-BLANK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgMandatoryBlank(true);
      // COB(1836): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1837): STRING
        // COB(1837):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(1837):   ' must be supplied.'
        // COB(1837):   DELIMITED BY SIZE
        // COB(1837):   INTO WS-RETURN-MSG
        // COB(1837): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        // COB(1842): END-STRING
        // COB(1843): END-IF
      }
      // COB(1845): GO TO  1215-EDIT-MANDATORY-EXIT
      return;
      // COB(1846): END-IF
    }
    // COB(1848): SET FLG-MANDATORY-ISVALID   TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgMandatoryIsvalid(true);
  }

  /***/
  protected void _1220EditYesno() {
    // COB(1859): IF WS-EDIT-YES-NO             EQUAL LOW-VALUES
    // COB(1859): OR WS-EDIT-YES-NO             EQUAL SPACES
    // COB(1859): OR WS-EDIT-YES-NO             EQUAL ZEROS
    //     Must be Y or N
    //     SET FLG-YES-NO-NOT-OK         TO TRUE
    //
    //     Not supplied
    if (ws.wsMiscStorage.wsGenericEdits.wsEditYesNo.isLowValues()
        || ws.wsMiscStorage.wsGenericEdits.wsEditYesNo.isSpaces()
        || ws.wsMiscStorage.wsGenericEdits.wsEditYesNo.equals(0)) {
      // COB(1862): SET INPUT-ERROR            TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1863): SET FLG-YES-NO-BLANK       TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgYesNoBlank(true);
      // COB(1864): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1865): STRING
        // COB(1865):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(1865):   ' must be supplied.'
        // COB(1865):   DELIMITED BY SIZE
        // COB(1865):   INTO WS-RETURN-MSG
        // COB(1865): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        // COB(1870): END-STRING
        // COB(1871): END-IF
      }
      // COB(1872): GO TO  1220-EDIT-YESNO-EXIT
      return;
      // COB(1873): END-IF
    }
    // COB(1876): IF FLG-YES-NO-ISVALID
    //
    //
    if (ws.wsMiscStorage.wsGenericEdits.flgYesNoIsvalid()) {
      // COB(1877): CONTINUE
      // do nothing
      // COB(1878): ELSE
    } else {
      // COB(1879): SET INPUT-ERROR             TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1880): SET FLG-YES-NO-NOT-OK       TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgYesNoNotOk(true);
      // COB(1881): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1882): STRING
        // COB(1882):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(1882):   ' must be Y or N.'
        // COB(1882):   DELIMITED BY SIZE
        // COB(1882):   INTO WS-RETURN-MSG
        // COB(1882): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be Y or N.");
        // COB(1887): END-STRING
        // COB(1888): END-IF
      }
      // COB(1889): GO TO  1220-EDIT-YESNO-EXIT
      return;
      // COB(1890): END-IF
    }
  }

  /***/
  protected void _1225EditAlphaReqd() {
    // COB(1898): SET FLG-ALPHA-NOT-OK              TO TRUE
    //     Initialize
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphaNotOk(true);
    // COB(1901): IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1901):                                   EQUAL LOW-VALUES
    // COB(1901): OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1901):     EQUAL SPACES
    // COB(1901): OR FUNCTION LENGTH(FUNCTION TRIM(
    // COB(1901):    WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
    //
    //     Not supplied
    if (ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .isLowValues()
        || ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .isSpaces()
        || ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .trim()
            .length()
            .equals(0)) {
      // COB(1908): SET INPUT-ERROR                TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1909): SET FLG-ALPHA-BLANK            TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphaBlank(true);
      // COB(1910): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1911): STRING
        // COB(1911):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(1911):   ' must be supplied.'
        // COB(1911):   DELIMITED BY SIZE
        // COB(1911):   INTO WS-RETURN-MSG
        // COB(1911): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        // COB(1916): END-STRING
        // COB(1917): END-IF
      }
      // COB(1919): GO TO  1225-EDIT-ALPHA-REQD-EXIT
      return;
      // COB(1920): END-IF
    }
    // COB(1923): MOVE LIT-ALL-ALPHA-FROM-X   TO LIT-ALL-ALPHA-FROM
    //
    //     Only Alphabets and space allowed
    ws.litAllAlphaFrom.setValue(ws.wsLiterals.litAllAlphanumFromX.litAllAlphaFromX);
    // COB(1924): INSPECT WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1924):   CONVERTING LIT-ALL-ALPHA-FROM
    // COB(1924):           TO LIT-ALPHA-SPACES-TO
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.inspectAndConvertCharacters(
        ws.litAllAlphaFrom.getValue(),
        ws.litAlphaSpacesTo.getValue(),
        0,
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt());
    // COB(1928): IF FUNCTION LENGTH(
    // COB(1928):         FUNCTION TRIM(
    // COB(1928):         WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1928):                        )) = 0
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditAlphanumOnly
        .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
        .trim()
        .length()
        .equals(0)) {
      // COB(1932): CONTINUE
      // do nothing
      // COB(1933): ELSE
    } else {
      // COB(1934): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1935): SET FLG-ALPHA-NOT-OK      TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphaNotOk(true);
      // COB(1936): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1937): STRING
        // COB(1937):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(1937):   ' can have alphabets only.'
        // COB(1937):   DELIMITED BY SIZE
        // COB(1937):   INTO WS-RETURN-MSG
        // COB(1937): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " can have alphabets only.");
        // COB(1942): END-STRING
        // COB(1943): END-IF
      }
      // COB(1944): GO TO  1225-EDIT-ALPHA-REQD-EXIT
      return;
      // COB(1945): END-IF
    }
    // COB(1947): SET FLG-ALPHA-ISVALID        TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphaIsvalid(true);
  }

  /***/
  protected void _1230EditAlphanumReqd() {
    // COB(1955): SET FLG-ALPHNANUM-NOT-OK          TO TRUE
    //     Initialize
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
    // COB(1958): IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1958):                                   EQUAL LOW-VALUES
    // COB(1958): OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1958):     EQUAL SPACES
    // COB(1958): OR FUNCTION LENGTH(FUNCTION TRIM(
    // COB(1958):    WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
    //
    //     Not supplied
    if (ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .isLowValues()
        || ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .isSpaces()
        || ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .trim()
            .length()
            .equals(0)) {
      // COB(1965): SET INPUT-ERROR                TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1966): SET FLG-ALPHNANUM-BLANK        TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumBlank(true);
      // COB(1967): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1968): STRING
        // COB(1968):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(1968):   ' must be supplied.'
        // COB(1968):   DELIMITED BY SIZE
        // COB(1968):   INTO WS-RETURN-MSG
        // COB(1968): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        // COB(1973): END-STRING
        // COB(1974): END-IF
      }
      // COB(1976): GO TO  1230-EDIT-ALPHANUM-REQD-EXIT
      return;
      // COB(1977): END-IF
    }
    // COB(1980): MOVE LIT-ALL-ALPHANUM-FROM-X TO LIT-ALL-ALPHANUM-FROM
    //
    //     Only Alphabets,numbers and space allowed
    ws.litAllAlphanumFrom.setValue(ws.wsLiterals.litAllAlphanumFromX);
    // COB(1982): INSPECT WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1982):   CONVERTING LIT-ALL-ALPHANUM-FROM
    // COB(1982):           TO LIT-ALPHANUM-SPACES-TO
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.inspectAndConvertCharacters(
        ws.litAllAlphanumFrom.getValue(),
        ws.litAlphanumSpacesTo.getValue(),
        0,
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt());
    // COB(1986): IF FUNCTION LENGTH(
    // COB(1986):         FUNCTION TRIM(
    // COB(1986):         WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1986):                        )) = 0
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditAlphanumOnly
        .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
        .trim()
        .length()
        .equals(0)) {
      // COB(1990): CONTINUE
      // do nothing
      // COB(1991): ELSE
    } else {
      // COB(1992): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1993): SET FLG-ALPHNANUM-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
      // COB(1994): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1995): STRING
        // COB(1995):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(1995):   ' can have numbers or alphabets only.'
        // COB(1995):   DELIMITED BY SIZE
        // COB(1995):   INTO WS-RETURN-MSG
        // COB(1995): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            " can have numbers or alphabets only.");
        // COB(2000): END-STRING
        // COB(2001): END-IF
      }
      // COB(2002): GO TO  1230-EDIT-ALPHANUM-REQD-EXIT
      return;
      // COB(2003): END-IF
    }
    // COB(2005): SET FLG-ALPHNANUM-ISVALID    TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumIsvalid(true);
  }

  protected void _1235EditAlphaOpt() {
    // COB(2012): SET FLG-ALPHA-NOT-OK              TO TRUE
    //     Initialize
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphaNotOk(true);
    // COB(2015): IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2015):                                   EQUAL LOW-VALUES
    // COB(2015): OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2015):     EQUAL SPACES
    // COB(2015): OR FUNCTION LENGTH(FUNCTION TRIM(
    // COB(2015):    WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
    //
    //     Not supplied
    if (ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .isLowValues()
        || ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .isSpaces()
        || ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .trim()
            .length()
            .equals(0)) {
      // COB(2022): SET FLG-ALPHA-ISVALID          TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphaIsvalid(true);
      // COB(2023): GO TO  1235-EDIT-ALPHA-OPT-EXIT
      return;
      // COB(2024): ELSE
    } else {
      // COB(2025): CONTINUE
      // do nothing
      // COB(2026): END-IF
    }
    // COB(2029): MOVE LIT-ALL-ALPHA-FROM-X    TO LIT-ALL-ALPHA-FROM
    //
    //     Only Alphabets and space allowed
    ws.litAllAlphaFrom.setValue(ws.wsLiterals.litAllAlphanumFromX.litAllAlphaFromX);
    // COB(2030): INSPECT WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2030):   CONVERTING LIT-ALL-ALPHA-FROM
    // COB(2030):           TO LIT-ALPHA-SPACES-TO
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.inspectAndConvertCharacters(
        ws.litAllAlphaFrom.getValue(),
        ws.litAlphaSpacesTo.getValue(),
        0,
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt());
    // COB(2034): IF FUNCTION LENGTH(
    // COB(2034):         FUNCTION TRIM(
    // COB(2034):         WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2034):                        )) = 0
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditAlphanumOnly
        .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
        .trim()
        .length()
        .equals(0)) {
      // COB(2038): CONTINUE
      // do nothing
      // COB(2039): ELSE
    } else {
      // COB(2040): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2041): SET FLG-ALPHA-NOT-OK      TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphaNotOk(true);
      // COB(2042): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2043): STRING
        // COB(2043):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2043):   ' can have alphabets only.'
        // COB(2043):   DELIMITED BY SIZE
        // COB(2043):   INTO WS-RETURN-MSG
        // COB(2043): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " can have alphabets only.");
        // COB(2048): END-STRING
        // COB(2049): END-IF
      }
      // COB(2050): GO TO  1235-EDIT-ALPHA-OPT-EXIT
      return;
      // COB(2051): END-IF
    }
    // COB(2053): SET FLG-ALPHA-ISVALID        TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphaIsvalid(true);
  }

  /***/
  protected void _1240EditAlphanumOpt() {
    // COB(2061): SET FLG-ALPHNANUM-NOT-OK          TO TRUE
    //     Initialize
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
    // COB(2064): IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2064):                                   EQUAL LOW-VALUES
    // COB(2064): OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2064):     EQUAL SPACES
    // COB(2064): OR FUNCTION LENGTH(FUNCTION TRIM(
    // COB(2064):    WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
    //
    //     Not supplied, but ok as optional
    if (ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .isLowValues()
        || ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .isSpaces()
        || ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .trim()
            .length()
            .equals(0)) {
      // COB(2070): SET FLG-ALPHNANUM-ISVALID     TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumIsvalid(true);
      // COB(2071): GO TO  1240-EDIT-ALPHANUM-OPT-EXIT
      return;
      // COB(2072): ELSE
    } else {
      // COB(2073): CONTINUE
      // do nothing
      // COB(2074): END-IF
    }
    // COB(2077): MOVE LIT-ALL-ALPHANUM-FROM-X TO LIT-ALL-ALPHANUM-FROM
    //
    //     Only Alphabets and space allowed
    ws.litAllAlphanumFrom.setValue(ws.wsLiterals.litAllAlphanumFromX);
    // COB(2078): INSPECT WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2078):   CONVERTING LIT-ALL-ALPHANUM-FROM
    // COB(2078):           TO LIT-ALPHANUM-SPACES-TO
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.inspectAndConvertCharacters(
        ws.litAllAlphanumFrom.getValue(),
        ws.litAlphanumSpacesTo.getValue(),
        0,
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt());
    // COB(2082): IF FUNCTION LENGTH(
    // COB(2082):         FUNCTION TRIM(
    // COB(2082):         WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2082):                        )) = 0
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditAlphanumOnly
        .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
        .trim()
        .length()
        .equals(0)) {
      // COB(2086): CONTINUE
      // do nothing
      // COB(2087): ELSE
    } else {
      // COB(2088): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2089): SET FLG-ALPHNANUM-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
      // COB(2090): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2091): STRING
        // COB(2091):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2091):   ' can have numbers or alphabets only.'
        // COB(2091):   DELIMITED BY SIZE
        // COB(2091):   INTO WS-RETURN-MSG
        // COB(2091): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            " can have numbers or alphabets only.");
        // COB(2096): END-STRING
        // COB(2097): END-IF
      }
      // COB(2098): GO TO  1240-EDIT-ALPHANUM-OPT-EXIT
      return;
      // COB(2099): END-IF
    }
    // COB(2101): SET FLG-ALPHNANUM-ISVALID    TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumIsvalid(true);
  }

  /***/
  protected void _1245EditNumReqd() {
    // COB(2109): SET FLG-ALPHNANUM-NOT-OK          TO TRUE
    //     Initialize
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
    // COB(2112): IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2112):                                   EQUAL LOW-VALUES
    // COB(2112): OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2112):     EQUAL SPACES
    // COB(2112): OR FUNCTION LENGTH(FUNCTION TRIM(
    // COB(2112):    WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
    //
    //     Not supplied
    if (ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .isLowValues()
        || ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .isSpaces()
        || ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
            .trim()
            .length()
            .equals(0)) {
      // COB(2119): SET INPUT-ERROR                TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2120): SET FLG-ALPHNANUM-BLANK        TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumBlank(true);
      // COB(2121): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2122): STRING
        // COB(2122):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2122):   ' must be supplied.'
        // COB(2122):   DELIMITED BY SIZE
        // COB(2122):   INTO WS-RETURN-MSG
        // COB(2122): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        // COB(2127): END-STRING
        // COB(2128): END-IF
      }
      // COB(2130): GO TO  1245-EDIT-NUM-REQD-EXIT
      return;
      // COB(2131): END-IF
    }
    // COB(2135): IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2135):        IS NUMERIC
    //
    //     Only all numeric allowed
    //
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditAlphanumOnly
        .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
        .isNumeric()) {
      // COB(2137): CONTINUE
      // do nothing
      // COB(2138): ELSE
    } else {
      // COB(2139): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2140): SET FLG-ALPHNANUM-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
      // COB(2141): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2142): STRING
        // COB(2142):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2142):   ' must be all numeric.'
        // COB(2142):   DELIMITED BY SIZE
        // COB(2142):   INTO WS-RETURN-MSG
        // COB(2142): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be all numeric.");
        // COB(2147): END-STRING
        // COB(2148): END-IF
      }
      // COB(2149): GO TO  1245-EDIT-NUM-REQD-EXIT
      return;
      // COB(2150): END-IF
    }
    // COB(2154): IF FUNCTION NUMVAL(WS-EDIT-ALPHANUM-ONLY(1:
    // COB(2154):                    WS-EDIT-ALPHANUM-LENGTH)) = 0
    //
    //     Must not be zero
    //
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditAlphanumOnly
        .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
        .convertToNumeric()
        .equals(0)) {
      // COB(2156): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2157): SET FLG-ALPHNANUM-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
      // COB(2158): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2159): STRING
        // COB(2159):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2159):   ' must not be zero.'
        // COB(2159):   DELIMITED BY SIZE
        // COB(2159):   INTO WS-RETURN-MSG
        // COB(2159): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must not be zero.");
        // COB(2164): END-STRING
        // COB(2165): END-IF
      }
      // COB(2166): GO TO  1245-EDIT-NUM-REQD-EXIT
      return;
      // COB(2167): ELSE
    } else {
      // COB(2168): CONTINUE
      // do nothing
      // COB(2169): END-IF
    }
    // COB(2172): SET FLG-ALPHNANUM-ISVALID    TO TRUE
    //
    //
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumIsvalid(true);
  }

  /***/
  protected void _1250EditSigned9v2() {
    // COB(2179): SET FLG-SIGNED-NUMBER-NOT-OK    TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgSignedNumberNotOk(true);
    // COB(2182): IF WS-EDIT-SIGNED-NUMBER-9V2-X  EQUAL LOW-VALUES
    // COB(2182): OR WS-EDIT-SIGNED-NUMBER-9V2-X  EQUAL SPACES
    //
    //     Not supplied
    if (ws.wsMiscStorage.wsGenericEdits.wsEditSignedNumber9v2X.isLowValues()
        || ws.wsMiscStorage.wsGenericEdits.wsEditSignedNumber9v2X.isSpaces()) {
      // COB(2184): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2185): SET FLG-SIGNED-NUMBER-BLANK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgSignedNumberBlank(true);
      // COB(2186): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2187): STRING
        // COB(2187):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2187):   ' must be supplied.'
        // COB(2187):   DELIMITED BY SIZE
        // COB(2187):   INTO WS-RETURN-MSG
        // COB(2187): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        // COB(2192): END-STRING
        // COB(2193): END-IF
      }
      // COB(2194): GO TO  1250-EDIT-SIGNED-9V2-EXIT
      return;
      // COB(2195): ELSE
    } else {
      // COB(2196): CONTINUE
      // do nothing
      // COB(2197): END-IF
    }
    // COB(2199): IF FUNCTION TEST-NUMVAL-C(WS-EDIT-SIGNED-NUMBER-9V2-X) = 0
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditSignedNumber9v2X
        .testConvertCurrencyToNumeric()
        .equals(0)) {
      // COB(2200): CONTINUE
      // do nothing
      // COB(2201): ELSE
    } else {
      // COB(2202): SET INPUT-ERROR             TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2203): SET FLG-SIGNED-NUMBER-NOT-OK   TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgSignedNumberNotOk(true);
      // COB(2204): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2205): STRING
        // COB(2205):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2205):   ' is not valid'
        // COB(2205):   DELIMITED BY SIZE
        // COB(2205):   INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " is not valid");
        // COB(2210): END-IF
      }
      // COB(2211): GO TO  1250-EDIT-SIGNED-9V2-EXIT
      return;
      // COB(2213): END-IF
    }
    // COB(2216): SET FLG-SIGNED-NUMBER-ISVALID  TO TRUE
    //
    //     If we got here all edits were cleared
    ws.wsMiscStorage.wsGenericEdits.setFlgSignedNumberIsvalid(true);
  }

  /***/
  protected Flow _1260EditUsPhoneNum() {
    // COB(2230): SET WS-EDIT-US-PHONE-IS-INVALID TO TRUE
    //
    //     The database stores date in X(15) format (999)999-9999
    //                                              1234567890123
    //     So we take the X(15) input into WS-EDIT-US-PHONE-NUM
    //     and edit it
    //
    ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setWsEditUsPhoneIsInvalid(true);
    // COB(2232): IF  (WS-EDIT-US-PHONE-NUMA EQUAL SPACES
    // COB(2232): OR   WS-EDIT-US-PHONE-NUMA EQUAL LOW-VALUES)
    // COB(2232): AND (WS-EDIT-US-PHONE-NUMB EQUAL SPACES
    // COB(2232): OR   WS-EDIT-US-PHONE-NUMB EQUAL LOW-VALUES)
    // COB(2232): AND (WS-EDIT-US-PHONE-NUMA EQUAL SPACES
    // COB(2232): OR   WS-EDIT-US-PHONE-NUMC EQUAL LOW-VALUES)
    //     Not mandatory to enter a phone number
    if ((ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNuma.isSpaces()
            || ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNuma.isLowValues())
        && (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumb.isSpaces()
            || ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumb.isLowValues())
        && (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNuma.isSpaces()
            || ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumc.isLowValues())) {
      // COB(2238): SET WS-EDIT-US-PHONE-IS-VALID TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setWsEditUsPhoneIsValid(true);
      // COB(2239): GO TO EDIT-US-PHONE-EXIT
      return Flow.editUsPhoneExit;
      // COB(2240): ELSE
    } else {
      // COB(2241): CONTINUE
      // do nothing
      // COB(2242): END-IF
    }
    return Flow.Exit;
  }

  protected Flow editAreaCode() {
    // COB(2245): IF WS-EDIT-US-PHONE-NUMA EQUAL SPACES
    // COB(2245): OR WS-EDIT-US-PHONE-NUMA EQUAL LOW-VALUES
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNuma.isSpaces()
        || ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNuma.isLowValues()) {
      // COB(2247): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2248): SET FLG-EDIT-US-PHONEA-BLANK    TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhoneaBlank(true);
      // COB(2249): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2250): STRING
        // COB(2250):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2250):   ': Area code must be supplied.'
        // COB(2250):   DELIMITED BY SIZE
        // COB(2250):   INTO WS-RETURN-MSG
        // COB(2250): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Area code must be supplied.");
        // COB(2255): END-STRING
        // COB(2256): END-IF
      }
      // COB(2257): GO TO  EDIT-US-PHONE-PREFIX
      return Flow.editUsPhonePrefix;
      // COB(2258): ELSE
    } else {
      // COB(2259): CONTINUE
      // do nothing
      // COB(2260): END-IF
    }
    // COB(2262): IF  WS-EDIT-US-PHONE-NUMA       IS NUMERIC
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNuma.isNumeric()) {
      // COB(2263): CONTINUE
      // do nothing
      // COB(2264): ELSE
    } else {
      // COB(2265): SET INPUT-ERROR                 TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2266): SET  FLG-EDIT-US-PHONEA-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhoneaNotOk(true);
      // COB(2267): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2268): STRING
        // COB(2268):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2268):   ': Area code must be A 3 digit number.'
        // COB(2268):   DELIMITED BY SIZE
        // COB(2268):   INTO WS-RETURN-MSG
        // COB(2268): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Area code must be A 3 digit number.");
        // COB(2273): END-STRING
        // COB(2274): END-IF
      }
      // COB(2275): GO TO  EDIT-US-PHONE-PREFIX
      return Flow.editUsPhonePrefix;
      // COB(2276): END-IF
    }
    // COB(2278): IF  WS-EDIT-US-PHONE-NUMA-N = 0
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumaN.equals(0)) {
      // COB(2279): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2280): SET  FLG-EDIT-US-PHONEA-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhoneaNotOk(true);
      // COB(2281): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2282): STRING
        // COB(2282):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2282):   ': Area code cannot be zero'
        // COB(2282):   DELIMITED BY SIZE
        // COB(2282):   INTO WS-RETURN-MSG
        // COB(2282): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Area code cannot be zero");
        // COB(2287): END-STRING
        // COB(2288): END-IF
      }
      // COB(2289): GO TO  EDIT-US-PHONE-PREFIX
      return Flow.editUsPhonePrefix;
      // COB(2290): ELSE
    } else {
      // COB(2291): CONTINUE
      // do nothing
      // COB(2292): END-IF
    }
    // COB(2294): MOVE FUNCTION TRIM (WS-EDIT-US-PHONE-NUMA)
    // COB(2294):   TO WS-US-PHONE-AREA-CODE-TO-EDIT
    ws.cslkpcdy.wsUsPhoneAreaCodeToEdit.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNuma.trim());
    // COB(2296): IF VALID-GENERAL-PURP-CODE
    if (ws.cslkpcdy.validGeneralPurpCode()) {
      // COB(2297): CONTINUE
      // do nothing
      // COB(2298): ELSE
    } else {
      // COB(2299): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2300): SET  FLG-EDIT-US-PHONEA-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhoneaNotOk(true);
      // COB(2301): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2302): STRING
        // COB(2302):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2302):   ': Not valid North America general purpose area code'
        // COB(2302):   DELIMITED BY SIZE
        // COB(2302):   INTO WS-RETURN-MSG
        // COB(2302): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Not valid North America general purpose area code");
        // COB(2307): END-STRING
        // COB(2308): END-IF
      }
      // COB(2309): GO TO  EDIT-US-PHONE-PREFIX
      return Flow.editUsPhonePrefix;
      // COB(2310): END-IF
    }
    // COB(2312): SET FLG-EDIT-US-PHONEA-ISVALID    TO TRUE
    ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhoneaIsvalid(true);
    return Flow.Exit;
  }

  protected Flow editUsPhonePrefix() {
    // COB(2316): IF WS-EDIT-US-PHONE-NUMB EQUAL SPACES
    // COB(2316): OR WS-EDIT-US-PHONE-NUMB EQUAL LOW-VALUES
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumb.isSpaces()
        || ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumb.isLowValues()) {
      // COB(2318): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2319): SET FLG-EDIT-US-PHONEB-BLANK    TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonebBlank(true);
      // COB(2320): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2321): STRING
        // COB(2321):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2321):   ': Prefix code must be supplied.'
        // COB(2321):   DELIMITED BY SIZE
        // COB(2321):   INTO WS-RETURN-MSG
        // COB(2321): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Prefix code must be supplied.");
        // COB(2326): END-STRING
        // COB(2327): END-IF
      }
      // COB(2328): GO TO  EDIT-US-PHONE-LINENUM
      return Flow.editUsPhoneLinenum;
      // COB(2329): ELSE
    } else {
      // COB(2330): CONTINUE
      // do nothing
      // COB(2331): END-IF
    }
    // COB(2333): IF  WS-EDIT-US-PHONE-NUMB          IS NUMERIC
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumb.isNumeric()) {
      // COB(2334): CONTINUE
      // do nothing
      // COB(2335): ELSE
    } else {
      // COB(2336): SET INPUT-ERROR                 TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2337): SET  FLG-EDIT-US-PHONEB-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonebNotOk(true);
      // COB(2338): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2339): STRING
        // COB(2339):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2339):   ': Prefix code must be A 3 digit number.'
        // COB(2339):   DELIMITED BY SIZE
        // COB(2339):   INTO WS-RETURN-MSG
        // COB(2339): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Prefix code must be A 3 digit number.");
        // COB(2344): END-STRING
        // COB(2345): END-IF
      }
      // COB(2346): GO TO  EDIT-US-PHONE-LINENUM
      return Flow.editUsPhoneLinenum;
      // COB(2347): END-IF
    }
    // COB(2349): IF  WS-EDIT-US-PHONE-NUMB-N = 0
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumbN.equals(0)) {
      // COB(2350): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2351): SET  FLG-EDIT-US-PHONEB-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonebNotOk(true);
      // COB(2352): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2353): STRING
        // COB(2353):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2353):   ': Prefix code cannot be zero'
        // COB(2353):   DELIMITED BY SIZE
        // COB(2353):   INTO WS-RETURN-MSG
        // COB(2353): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Prefix code cannot be zero");
        // COB(2358): END-STRING
        // COB(2359): END-IF
      }
      // COB(2360): GO TO  EDIT-US-PHONE-LINENUM
      return Flow.editUsPhoneLinenum;
      // COB(2361): ELSE
    } else {
      // COB(2362): CONTINUE
      // do nothing
      // COB(2363): END-IF
    }
    // COB(2365): SET FLG-EDIT-US-PHONEB-ISVALID    TO TRUE
    ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonebIsvalid(true);
    return Flow.Exit;
  }

  /***/
  protected Flow editUsPhoneLinenum() {
    // COB(2369): IF WS-EDIT-US-PHONE-NUMC EQUAL SPACES
    // COB(2369): OR WS-EDIT-US-PHONE-NUMC EQUAL LOW-VALUES
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumc.isSpaces()
        || ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumc.isLowValues()) {
      // COB(2371): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2372): SET FLG-EDIT-US-PHONEC-BLANK    TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonecBlank(true);
      // COB(2373): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2374): STRING
        // COB(2374):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2374):   ': Line number code must be supplied.'
        // COB(2374):   DELIMITED BY SIZE
        // COB(2374):   INTO WS-RETURN-MSG
        // COB(2374): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Line number code must be supplied.");
        // COB(2379): END-STRING
        // COB(2380): END-IF
      }
      // COB(2381): GO TO EDIT-US-PHONE-EXIT
      return Flow.editUsPhoneExit;
      // COB(2382): ELSE
    } else {
      // COB(2383): CONTINUE
      // do nothing
      // COB(2384): END-IF
    }
    // COB(2386): IF  WS-EDIT-US-PHONE-NUMC          IS NUMERIC
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumc.isNumeric()) {
      // COB(2387): CONTINUE
      // do nothing
      // COB(2388): ELSE
    } else {
      // COB(2389): SET INPUT-ERROR                 TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2390): SET  FLG-EDIT-US-PHONEC-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonecNotOk(true);
      // COB(2391): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2392): STRING
        // COB(2392):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2392):   ': Line number code must be A 4 digit number.'
        // COB(2392):   DELIMITED BY SIZE
        // COB(2392):   INTO WS-RETURN-MSG
        // COB(2392): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Line number code must be A 4 digit number.");
        // COB(2397): END-STRING
        // COB(2398): END-IF
      }
      // COB(2399): GO TO  EDIT-US-PHONE-EXIT
      return Flow.editUsPhoneExit;
      // COB(2400): END-IF
    }
    // COB(2402): IF  WS-EDIT-US-PHONE-NUMC-N = 0
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumcN.equals(0)) {
      // COB(2403): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2404): SET  FLG-EDIT-US-PHONEC-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonecNotOk(true);
      // COB(2405): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2406): STRING
        // COB(2406):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2406):   ': Line number code cannot be zero'
        // COB(2406):   DELIMITED BY SIZE
        // COB(2406):   INTO WS-RETURN-MSG
        // COB(2406): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Line number code cannot be zero");
        // COB(2411): END-STRING
        // COB(2412): END-IF
      }
      // COB(2413): GO TO  EDIT-US-PHONE-EXIT
      return Flow.editUsPhoneExit;
      // COB(2414): ELSE
    } else {
      // COB(2415): CONTINUE
      // do nothing
      // COB(2416): END-IF
    }
    // COB(2419): SET FLG-EDIT-US-PHONEC-ISVALID    TO TRUE
    //
    //
    ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonecIsvalid(true);
    return Flow.Exit;
  }

  /***/
  protected void editUsPhoneExit() {
    // COB(2423): EXIT
    return;
  }

  /***/
  protected void _1265EditUsSsn() {
    // COB(2437): MOVE 'SSN: First 3 chars'     TO WS-EDIT-VARIABLE-NAME
    // Format xxx-xx-xxxx
    // Part1 :should have 3 digits
    // Part2 :should have 2 digits and it should be from 01 to 99
    // Part3 should have 4 digits from 0001 to 9999.
    // *****************************************************************
    //     Edit SSN Part 1
    // *****************************************************************
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("SSN: First 3 chars");
    // COB(2438): MOVE ACUP-NEW-CUST-SSN-1      TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn1);
    // COB(2439): MOVE 3                        TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(3);
    // COB(2440): PERFORM 1245-EDIT-NUM-REQD
    // COB(2440):    THRU 1245-EDIT-NUM-REQD-EXIT
    _1245EditNumReqd();
    // COB(2442): MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
    // COB(2442):                               TO WS-EDIT-US-SSN-PART1-FLGS
    ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.wsEditUsSsnPart1Flgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);
    // COB(2446): IF FLG-EDIT-US-SSN-PART1-ISVALID
    //
    // Part1 :should not be 000, 666, or between 900 and 999
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart1Isvalid()) {
      // COB(2447): MOVE ACUP-NEW-CUST-SSN-1   TO WS-EDIT-US-SSN-PART1
      ws.wsMiscStorage.wsGenericEdits.wsEditUsSsn.wsEditUsSsnPart1.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn1);
      // COB(2448): IF INVALID-SSN-PART1
      if (ws.wsMiscStorage.wsGenericEdits.wsEditUsSsn.invalidSsnPart1()) {
        // COB(2449): SET INPUT-ERROR            TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(2450): SET FLG-EDIT-US-SSN-PART1-NOT-OK
        // COB(2450):                    TO TRUE
        ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.setFlgEditUsSsnPart1NotOk(true);
        // COB(2452): IF WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(2453): STRING
          // COB(2453):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
          // COB(2453):   ': should not be 000, 666, or between 900 and 999'
          // COB(2453):   DELIMITED BY SIZE
          // COB(2453):   INTO WS-RETURN-MSG
          // COB(2453): END-STRING
          ws.wsMiscStorage.wsReturnMsg.concat(
              ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
              ": should not be 000, 666, or between 900 and 999");
          // COB(2458): END-STRING
          // COB(2459): ELSE
        } else {
          // COB(2460): CONTINUE
          // do nothing
          // COB(2461): END-IF
        }
        // COB(2462): END-IF
      }
      // COB(2467): MOVE 'SSN 4th & 5th chars'    TO WS-EDIT-VARIABLE-NAME
      //
      // *****************************************************************
      //     Edit SSN Part 2
      // *****************************************************************
      ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("SSN 4th & 5th chars");
      // COB(2468): MOVE ACUP-NEW-CUST-SSN-2      TO WS-EDIT-ALPHANUM-ONLY
      ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn2);
      // COB(2469): MOVE 2                        TO WS-EDIT-ALPHANUM-LENGTH
      ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(2);
      // COB(2470): PERFORM 1245-EDIT-NUM-REQD
      // COB(2470):    THRU 1245-EDIT-NUM-REQD-EXIT
      _1245EditNumReqd();
      // COB(2472): MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
      // COB(2472):                               TO WS-EDIT-US-SSN-PART2-FLGS
      ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.wsEditUsSsnPart2Flgs.setValue(
          ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);
      // COB(2479): MOVE 'SSN Last 4 chars'       TO WS-EDIT-VARIABLE-NAME
      //
      //
      // *****************************************************************
      //     Edit SSN Part 3
      // *****************************************************************
      ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("SSN Last 4 chars");
      // COB(2480): MOVE ACUP-NEW-CUST-SSN-3      TO WS-EDIT-ALPHANUM-ONLY
      ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn3);
      // COB(2481): MOVE 4                        TO WS-EDIT-ALPHANUM-LENGTH
      ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(4);
      // COB(2482): PERFORM 1245-EDIT-NUM-REQD
      // COB(2482):    THRU 1245-EDIT-NUM-REQD-EXIT
      _1245EditNumReqd();
      // COB(2484): MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
      // COB(2484):                               TO WS-EDIT-US-SSN-PART3-FLGS
      ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.wsEditUsSsnPart3Flgs.setValue(
          ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);
      // COB(2486): .
    }
  }

  /***/
  protected void _1270EditUsStateCd() {
    // COB(2492): MOVE ACUP-NEW-CUST-ADDR-STATE-CD TO US-STATE-CODE-TO-EDIT
    ws.cslkpcdy.usStateCodeToEdit.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrStateCd);
    // COB(2493): IF VALID-US-STATE-CODE
    if (ws.cslkpcdy.validUsStateCode()) {
      // COB(2494): CONTINUE
      // do nothing
      // COB(2495): ELSE
    } else {
      // COB(2496): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2497): SET FLG-STATE-NOT-OK         TO TRUE
      ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.setFlgStateNotOk(true);
      // COB(2498): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2499): STRING
        // COB(2499):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2499):   ': is not a valid state code'
        // COB(2499):   DELIMITED BY SIZE
        // COB(2499):   INTO WS-RETURN-MSG
        // COB(2499): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": is not a valid state code");
        // COB(2504): END-STRING
        // COB(2505): END-IF
      }
      // COB(2506): GO TO  1270-EDIT-US-STATE-CD-EXIT
      return;
      // COB(2507): END-IF
    }
  }

  protected void _1275EditFicoScore() {
    // COB(2513): IF FICO-RANGE-IS-VALID
    if (ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.ficoRangeIsValid()) {
      // COB(2514): CONTINUE
      // do nothing
      // COB(2515): ELSE
    } else {
      // COB(2516): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2517): SET FLG-FICO-SCORE-NOT-OK    TO TRUE
      ws.wsMiscStorage.wsNonKeyFlags.setFlgFicoScoreNotOk(true);
      // COB(2518): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2519): STRING
        // COB(2519):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2519):   ': should be between 300 and 850'
        // COB(2519):   DELIMITED BY SIZE
        // COB(2519):   INTO WS-RETURN-MSG
        // COB(2519): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": should be between 300 and 850");
        // COB(2524): END-STRING
        // COB(2525): END-IF
      }
      // COB(2526): GO TO  1275-EDIT-FICO-SCORE-EXIT
      return;
      // COB(2527): END-IF
    }
  }

  /***
   *A crude zip code edit based on data from USPS web site*/
  protected void _1280EditUsStateZipCd() {
    // COB(2535): STRING ACUP-NEW-CUST-ADDR-STATE-CD
    // COB(2535):        ACUP-NEW-CUST-ADDR-ZIP(1:2)
    // COB(2535):   DELIMITED BY SIZE
    // COB(2535):   INTO US-STATE-AND-FIRST-ZIP2
    ws.cslkpcdy.usStateZipcodeToEdit.usStateAndFirstZip2.concat(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrStateCd,
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrZip.getString(0, 2));
    // COB(2540): IF VALID-US-STATE-ZIP-CD2-COMBO
    if (ws.cslkpcdy.usStateZipcodeToEdit.validUsStateZipCd2Combo()) {
      // COB(2541): CONTINUE
      // do nothing
      // COB(2542): ELSE
    } else {
      // COB(2543): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2544): SET FLG-STATE-NOT-OK         TO TRUE
      ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.setFlgStateNotOk(true);
      // COB(2545): SET FLG-ZIPCODE-NOT-OK       TO TRUE
      ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.setFlgZipcodeNotOk(true);
      // COB(2546): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2547): STRING
        // COB(2547):   'Invalid zip code for state'
        // COB(2547):   DELIMITED BY SIZE
        // COB(2547):   INTO WS-RETURN-MSG
        // COB(2547): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat("Invalid zip code for state");
        // COB(2551): END-STRING
        // COB(2552): END-IF
      }
      // COB(2553): GO TO  1280-EDIT-US-STATE-ZIP-CD-EXIT
      return;
      // COB(2554): END-IF
    }
  }

  /***/
  protected void _2000DecideAction() {
    // COB(2561): EVALUATE TRUE
    // COB(2566): WHEN ACUP-DETAILS-NOT-FETCHED
    // *****************************************************************
    //        NO DETAILS SHOWN.
    //        SO GET THEM AND SETUP DETAIL EDIT SCREEN
    // *****************************************************************
    if ((ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched())
        // COB(2570): WHEN CCARD-AID-PFK12
        // *****************************************************************
        //        CHANGES MADE. BUT USER CANCELS
        // *****************************************************************
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk12()) {
      // COB(2571): IF  FLG-ACCTFILTER-ISVALID
      if (ws.wsMiscStorage.flgAcctfilterIsvalid()) {
        // COB(2572): SET WS-RETURN-MSG-OFF       TO TRUE
        ws.wsMiscStorage.setWsReturnMsgOff(true);
        // COB(2573): PERFORM 9000-READ-ACCT
        // COB(2573):    THRU 9000-READ-ACCT-EXIT
        _9000ReadAcct();
        // COB(2575): IF FOUND-CUST-IN-MASTER
        if (ws.wsMiscStorage.wsFileReadFlags.foundCustInMaster()) {
          // COB(2576): SET ACUP-SHOW-DETAILS    TO TRUE
          ws.wsThisProgcommarea.acctUpdateScreenData.setAcupShowDetails(true);
          // COB(2577): END-IF
        }
        // COB(2578): END-IF
      }
      // COB(2583): WHEN ACUP-SHOW-DETAILS
      // *****************************************************************
      //        DETAILS SHOWN
      //        CHECK CHANGES AND ASK CONFIRMATION IF GOOD
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupShowDetails()) {
      // COB(2584): IF INPUT-ERROR
      // COB(2584): OR NO-CHANGES-DETECTED
      if (ws.wsMiscStorage.inputError() || ws.wsMiscStorage.noChangesDetected()) {
        // COB(2586): CONTINUE
        // do nothing
        // COB(2587): ELSE
      } else {
        // COB(2588): SET ACUP-CHANGES-OK-NOT-CONFIRMED TO TRUE
        ws.wsThisProgcommarea.acctUpdateScreenData.setAcupChangesOkNotConfirmed(true);
        // COB(2589): END-IF
      }
      // COB(2594): WHEN ACUP-CHANGES-NOT-OK
      // *****************************************************************
      //        DETAILS SHOWN
      //        BUT INPUT EDIT ERRORS FOUND
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesNotOk()) {
      // COB(2595): CONTINUE
      // do nothing
      // COB(2600): WHEN ACUP-CHANGES-OK-NOT-CONFIRMED
      // COB(2600):  AND CCARD-AID-PFK05
      // *****************************************************************
      //        DETAILS EDITED , FOUND OK, CONFIRM SAVE REQUESTED
      //        CONFIRMATION GIVEN.SO SAVE THE CHANGES
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkNotConfirmed()
        && ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk05()) {
      // COB(2602): PERFORM 9600-WRITE-PROCESSING
      // COB(2602):    THRU 9600-WRITE-PROCESSING-EXIT
      rcNext = Flow._9600WriteProcessing;
      while (!rcNext.equals(Flow.Exit)) {
        switch (rcNext) {
          case _9600WriteProcessing:
            rcNext = _9600WriteProcessing();
            if (rcNext.equals(Flow.Exit)) {
              rcNext = Flow._9600WriteProcessingExit;
            }
            break;
          case _9600WriteProcessingExit:
            _9600WriteProcessingExit();
            rcNext = Flow.Exit;
            break;
          default:
            throw new RuntimeException("Invalid flow option: " + rcNext);
        }
      }
      // COB(2604): EVALUATE TRUE
      // COB(2605): WHEN COULD-NOT-LOCK-ACCT-FOR-UPDATE
      if (ws.wsMiscStorage.couldNotLockAcctForUpdate()) {
        // COB(2606): SET ACUP-CHANGES-OKAYED-LOCK-ERROR TO TRUE
        ws.wsThisProgcommarea.acctUpdateScreenData.setAcupChangesOkayedLockError(true);
        // COB(2607): WHEN LOCKED-BUT-UPDATE-FAILED
      } else if (ws.wsMiscStorage.lockedButUpdateFailed()) {
        // COB(2608): SET ACUP-CHANGES-OKAYED-BUT-FAILED TO TRUE
        ws.wsThisProgcommarea.acctUpdateScreenData.setAcupChangesOkayedButFailed(true);
        // COB(2609): WHEN DATA-WAS-CHANGED-BEFORE-UPDATE
      } else if (ws.wsMiscStorage.dataWasChangedBeforeUpdate()) {
        // COB(2610): SET ACUP-SHOW-DETAILS            TO TRUE
        ws.wsThisProgcommarea.acctUpdateScreenData.setAcupShowDetails(true);
        // COB(2611): WHEN OTHER
      } else {
        // COB(2612): SET ACUP-CHANGES-OKAYED-AND-DONE   TO TRUE
        ws.wsThisProgcommarea.acctUpdateScreenData.setAcupChangesOkayedAndDone(true);
        // COB(2613): END-EVALUATE
      }
      // COB(2618): WHEN ACUP-CHANGES-OK-NOT-CONFIRMED
      // *****************************************************************
      //        DETAILS EDITED , FOUND OK, CONFIRM SAVE REQUESTED
      //        CONFIRMATION NOT GIVEN. SO SHOW DETAILS AGAIN
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkNotConfirmed()) {
      // COB(2619): CONTINUE
      // do nothing
      // COB(2623): WHEN ACUP-CHANGES-OKAYED-AND-DONE
      // *****************************************************************
      //        SHOW CONFIRMATION. GO BACK TO SQUARE 1
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedAndDone()) {
      // COB(2624): SET ACUP-SHOW-DETAILS TO TRUE
      ws.wsThisProgcommarea.acctUpdateScreenData.setAcupShowDetails(true);
      // COB(2625): IF CDEMO-FROM-TRANID    EQUAL LOW-VALUES
      // COB(2625): OR CDEMO-FROM-TRANID    EQUAL SPACES
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isSpaces()) {
        // COB(2627): MOVE ZEROES       TO CDEMO-ACCT-ID
        // COB(2627):                      CDEMO-CARD-NUM
        ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
        ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.zeros();
        // COB(2629): MOVE LOW-VALUES   TO CDEMO-ACCT-STATUS
        ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctStatus.lowValues();
        // COB(2630): END-IF
      }
      // COB(2631): WHEN OTHER
    } else {
      // COB(2632): MOVE LIT-THISPGM    TO ABEND-CULPRIT
      ws.csmsg02y.abendData.abendCulprit.setValue(ws.wsLiterals.litThispgm);
      // COB(2633): MOVE '0001'         TO ABEND-CODE
      ws.csmsg02y.abendData.abendCode.setString("0001");
      // COB(2634): MOVE SPACES         TO ABEND-REASON
      ws.csmsg02y.abendData.abendReason.spaces();
      // COB(2635): MOVE 'UNEXPECTED DATA SCENARIO'
      // COB(2635):                     TO ABEND-MSG
      ws.csmsg02y.abendData.abendMsg.setString("UNEXPECTED DATA SCENARIO");
      // COB(2637): PERFORM ABEND-ROUTINE
      // COB(2637):    THRU ABEND-ROUTINE-EXIT
      abendRoutine();
      // COB(2639): END-EVALUATE
    }
  }

  /***
   *
   */
  protected void _3000SendMap() {
    // COB(2648): PERFORM 3100-SCREEN-INIT
    // COB(2648):    THRU 3100-SCREEN-INIT-EXIT
    _3100ScreenInit();
    // COB(2650): PERFORM 3200-SETUP-SCREEN-VARS
    // COB(2650):    THRU 3200-SETUP-SCREEN-VARS-EXIT
    _3200SetupScreenVars();
    // COB(2652): PERFORM 3250-SETUP-INFOMSG
    // COB(2652):    THRU 3250-SETUP-INFOMSG-EXIT
    _3250SetupInfomsg();
    // COB(2654): PERFORM 3300-SETUP-SCREEN-ATTRS
    // COB(2654):    THRU 3300-SETUP-SCREEN-ATTRS-EXIT
    _3300SetupScreenAttrs();
    // COB(2656): PERFORM 3390-SETUP-INFOMSG-ATTRS
    // COB(2656):    THRU 3390-SETUP-INFOMSG-ATTRS-EXIT
    _3390SetupInfomsgAttrs();
    // COB(2658): PERFORM 3400-SEND-SCREEN
    // COB(2658):    THRU 3400-SEND-SCREEN-EXIT
    _3400SendScreen();
  }

  /***/
  protected void _3100ScreenInit() {
    // COB(2667): MOVE LOW-VALUES TO CACTUPAO
    ws.coactup.cactupao.lowValues();
    // COB(2669): MOVE FUNCTION CURRENT-DATE     TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(2671): MOVE CCDA-TITLE01              TO TITLE01O OF CACTUPAO
    ws.coactup.cactupao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(2672): MOVE CCDA-TITLE02              TO TITLE02O OF CACTUPAO
    ws.coactup.cactupao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(2673): MOVE LIT-THISTRANID            TO TRNNAMEO OF CACTUPAO
    ws.coactup.cactupao.trnnameo.setValue(ws.wsLiterals.litThistranid);
    // COB(2674): MOVE LIT-THISPGM               TO PGMNAMEO OF CACTUPAO
    ws.coactup.cactupao.pgmnameo.setValue(ws.wsLiterals.litThispgm);
    // COB(2676): MOVE FUNCTION CURRENT-DATE     TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(2678): MOVE WS-CURDATE-MONTH          TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(2679): MOVE WS-CURDATE-DAY            TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(2680): MOVE WS-CURDATE-YEAR(3:2)      TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(2682): MOVE WS-CURDATE-MM-DD-YY       TO CURDATEO OF CACTUPAO
    ws.coactup.cactupao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(2684): MOVE WS-CURTIME-HOURS          TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(2685): MOVE WS-CURTIME-MINUTE         TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(2686): MOVE WS-CURTIME-SECOND         TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(2688): MOVE WS-CURTIME-HH-MM-SS       TO CURTIMEO OF CACTUPAO
    ws.coactup.cactupao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***/
  protected void _3200SetupScreenVars() {
    // COB(2698): IF CDEMO-PGM-ENTER
    //     INITIALIZE SEARCH CRITERIA
    if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()) {
      // COB(2699): CONTINUE
      // do nothing
      // COB(2700): ELSE
    } else {
      // COB(2701): IF CC-ACCT-ID-N = 0
      // COB(2701): AND FLG-ACCTFILTER-ISVALID
      if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctIdN.equals(0)
          && ws.wsMiscStorage.flgAcctfilterIsvalid()) {
        // COB(2703): MOVE LOW-VALUES                TO ACCTSIDO OF CACTUPAO
        ws.coactup.cactupao.acctsido.lowValues();
        // COB(2704): ELSE
      } else {
        // COB(2705): MOVE CC-ACCT-ID                TO ACCTSIDO OF CACTUPAO
        ws.coactup.cactupao.acctsido.setValue(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
        // COB(2706): END-IF
      }
      // COB(2708): EVALUATE TRUE
      // COB(2709): WHEN ACUP-DETAILS-NOT-FETCHED
      if ((ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched())
          // COB(2710): WHEN CC-ACCT-ID-N =  0
          || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctIdN.equals(0)) {
        // COB(2711): PERFORM 3201-SHOW-INITIAL-VALUES
        // COB(2711):    THRU 3201-SHOW-INITIAL-VALUES-EXIT
        _3201ShowInitialValues();
        // COB(2713): WHEN ACUP-SHOW-DETAILS
      } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupShowDetails()) {
        // COB(2714): PERFORM 3202-SHOW-ORIGINAL-VALUES
        // COB(2714):    THRU 3202-SHOW-ORIGINAL-VALUES-EXIT
        _3202ShowOriginalValues();
        // COB(2716): WHEN ACUP-CHANGES-MADE
      } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesMade()) {
        // COB(2717): PERFORM 3203-SHOW-UPDATED-VALUES
        // COB(2717):    THRU 3203-SHOW-UPDATED-VALUES-EXIT
        _3203ShowUpdatedValues();
        // COB(2719): WHEN OTHER
      } else {
        // COB(2720): PERFORM 3202-SHOW-ORIGINAL-VALUES
        // COB(2720):    THRU 3202-SHOW-ORIGINAL-VALUES-EXIT
        _3202ShowOriginalValues();
        // COB(2722): END-EVALUATE
      }
      // COB(2723): END-IF
    }
  }

  /***/
  protected void _3201ShowInitialValues() {
    // COB(2730): MOVE LOW-VALUES                     TO  ACSTTUSO OF CACTUPAO
    // COB(2730):                                         ACRDLIMO OF CACTUPAO
    // COB(2730):       *Account Limits
    // COB(2730):                                         ACURBALO OF CACTUPAO
    // COB(2730):                                         ACSHLIMO OF CACTUPAO
    // COB(2730):                                         ACRCYCRO OF CACTUPAO
    // COB(2730):                                         ACRCYDBO OF CACTUPAO
    // COB(2730):       *Account Dates
    // COB(2730):                                         OPNYEARO OF CACTUPAO
    // COB(2730):                                         OPNMONO  OF CACTUPAO
    // COB(2730):                                         OPNDAYO  OF CACTUPAO
    // COB(2730):                                         EXPYEARO OF CACTUPAO
    // COB(2730):                                         EXPMONO  OF CACTUPAO
    // COB(2730):                                         EXPDAYO  OF CACTUPAO
    // COB(2730):                                         RISYEARO OF CACTUPAO
    // COB(2730):                                         RISMONO  OF CACTUPAO
    // COB(2730):                                         RISDAYO  OF CACTUPAO
    // COB(2730):                                         AADDGRPO OF CACTUPAO
    // COB(2730):       *Customer data
    // COB(2730):                                         ACSTNUMO OF CACTUPAO
    // COB(2730):                                         ACTSSN1O OF CACTUPAO
    // COB(2730):                                         ACTSSN2O OF CACTUPAO
    // COB(2730):                                         ACTSSN3O OF CACTUPAO
    // COB(2730):                                         ACSTFCOO OF CACTUPAO
    // COB(2730):                                         DOBYEARO OF CACTUPAO
    // COB(2730):                                         DOBMONO  OF CACTUPAO
    // COB(2730):                                         DOBDAYO  OF CACTUPAO
    // COB(2730):                                         ACSFNAMO OF CACTUPAO
    // COB(2730):                                         ACSMNAMO OF CACTUPAO
    // COB(2730):                                         ACSLNAMO OF CACTUPAO
    // COB(2730):       *Customer address and contact info
    // COB(2730):                                         ACSADL1O OF CACTUPAO
    // COB(2730):                                         ACSADL2O OF CACTUPAO
    // COB(2730):                                         ACSCITYO OF CACTUPAO
    // COB(2730):                                         ACSSTTEO OF CACTUPAO
    // COB(2730):                                         ACSZIPCO OF CACTUPAO
    // COB(2730):                                         ACSCTRYO OF CACTUPAO
    // COB(2730):       *
    // COB(2730):                                         ACSPH1AO OF CACTUPAO
    // COB(2730):                                         ACSPH1BO OF CACTUPAO
    // COB(2730):                                         ACSPH1CO OF CACTUPAO
    // COB(2730):                                         ACSPH2AO OF CACTUPAO
    // COB(2730):                                         ACSPH2BO OF CACTUPAO
    // COB(2730):                                         ACSPH2CO OF CACTUPAO
    // COB(2730):       *
    // COB(2730):       *Customer other good stuff
    // COB(2730):                                         ACSGOVTO OF CACTUPAO
    // COB(2730):                                         ACSEFTCO OF CACTUPAO
    // COB(2730):                                         ACSPFLGO OF CACTUPAO
    ws.coactup.cactupao.acsttuso.lowValues();
    ws.coactup.cactupao.acrdlimo.lowValues();
    ws.coactup.cactupao.acurbalo.lowValues();
    ws.coactup.cactupao.acshlimo.lowValues();
    ws.coactup.cactupao.acrcycro.lowValues();
    ws.coactup.cactupao.acrcydbo.lowValues();
    ws.coactup.cactupao.opnyearo.lowValues();
    ws.coactup.cactupao.opnmono.lowValues();
    ws.coactup.cactupao.opndayo.lowValues();
    ws.coactup.cactupao.expyearo.lowValues();
    ws.coactup.cactupao.expmono.lowValues();
    ws.coactup.cactupao.expdayo.lowValues();
    ws.coactup.cactupao.risyearo.lowValues();
    ws.coactup.cactupao.rismono.lowValues();
    ws.coactup.cactupao.risdayo.lowValues();
    ws.coactup.cactupao.aaddgrpo.lowValues();
    ws.coactup.cactupao.acstnumo.lowValues();
    ws.coactup.cactupao.actssn1o.lowValues();
    ws.coactup.cactupao.actssn2o.lowValues();
    ws.coactup.cactupao.actssn3o.lowValues();
    ws.coactup.cactupao.acstfcoo.lowValues();
    ws.coactup.cactupao.dobyearo.lowValues();
    ws.coactup.cactupao.dobmono.lowValues();
    ws.coactup.cactupao.dobdayo.lowValues();
    ws.coactup.cactupao.acsfnamo.lowValues();
    ws.coactup.cactupao.acsmnamo.lowValues();
    ws.coactup.cactupao.acslnamo.lowValues();
    ws.coactup.cactupao.acsadl1o.lowValues();
    ws.coactup.cactupao.acsadl2o.lowValues();
    ws.coactup.cactupao.acscityo.lowValues();
    ws.coactup.cactupao.acsstteo.lowValues();
    ws.coactup.cactupao.acszipco.lowValues();
    ws.coactup.cactupao.acsctryo.lowValues();
    ws.coactup.cactupao.acsph1ao.lowValues();
    ws.coactup.cactupao.acsph1bo.lowValues();
    ws.coactup.cactupao.acsph1co.lowValues();
    ws.coactup.cactupao.acsph2ao.lowValues();
    ws.coactup.cactupao.acsph2bo.lowValues();
    ws.coactup.cactupao.acsph2co.lowValues();
    ws.coactup.cactupao.acsgovto.lowValues();
    ws.coactup.cactupao.acseftco.lowValues();
    ws.coactup.cactupao.acspflgo.lowValues();
  }

  /***/
  protected void _3202ShowOriginalValues() {
    // COB(2787): MOVE LOW-VALUES                     TO WS-NON-KEY-FLAGS
    ws.wsMiscStorage.wsNonKeyFlags.lowValues();
    // COB(2789): SET PROMPT-FOR-CHANGES              TO TRUE
    ws.wsMiscStorage.setPromptForChanges(true);
    // COB(2791): IF FOUND-ACCT-IN-MASTER
    // COB(2791): OR FOUND-CUST-IN-MASTER
    if (ws.wsMiscStorage.wsFileReadFlags.foundAcctInMaster()
        || ws.wsMiscStorage.wsFileReadFlags.foundCustInMaster()) {
      // COB(2793): MOVE ACUP-OLD-ACTIVE-STATUS      TO ACSTTUSO OF CACTUPAO
      ws.coactup.cactupao.acsttuso.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldActiveStatus);
      // COB(2795): MOVE ACUP-OLD-CURR-BAL-N         TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrBalN);
      // COB(2796): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACURBALO OF CACTUPAO
      ws.coactup.cactupao.acurbalo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2798): MOVE ACUP-OLD-CREDIT-LIMIT-N     TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCreditLimitN);
      // COB(2799): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACRDLIMO OF CACTUPAO
      ws.coactup.cactupao.acrdlimo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2801): MOVE ACUP-OLD-CASH-CREDIT-LIMIT-N
      // COB(2801):                                  TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCashCreditLimitN);
      // COB(2803): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACSHLIMO OF CACTUPAO
      ws.coactup.cactupao.acshlimo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2805): MOVE ACUP-OLD-CURR-CYC-CREDIT-N  TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrCycCreditN);
      // COB(2806): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACRCYCRO OF CACTUPAO
      ws.coactup.cactupao.acrcycro.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2808): MOVE ACUP-OLD-CURR-CYC-DEBIT-N   TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrCycDebitN);
      // COB(2809): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACRCYDBO OF CACTUPAO
      ws.coactup.cactupao.acrcydbo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2811): MOVE ACUP-OLD-OPEN-YEAR          TO OPNYEARO OF CACTUPAO
      ws.coactup.cactupao.opnyearo.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldAcctData
              .acupOldOpenDateParts
              .acupOldOpenYear);
      // COB(2812): MOVE ACUP-OLD-OPEN-MON           TO OPNMONO  OF CACTUPAO
      ws.coactup.cactupao.opnmono.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldOpenDateParts.acupOldOpenMon);
      // COB(2813): MOVE ACUP-OLD-OPEN-DAY           TO OPNDAYO  OF CACTUPAO
      ws.coactup.cactupao.opndayo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldOpenDateParts.acupOldOpenDay);
      // COB(2815): MOVE ACUP-OLD-EXP-YEAR           TO EXPYEARO OF CACTUPAO
      ws.coactup.cactupao.expyearo.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldAcctData
              .acupOldExpiraionDateParts
              .acupOldExpYear);
      // COB(2816): MOVE ACUP-OLD-EXP-MON            TO EXPMONO  OF CACTUPAO
      ws.coactup.cactupao.expmono.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldAcctData
              .acupOldExpiraionDateParts
              .acupOldExpMon);
      // COB(2817): MOVE ACUP-OLD-EXP-DAY            TO EXPDAYO  OF CACTUPAO
      ws.coactup.cactupao.expdayo.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldAcctData
              .acupOldExpiraionDateParts
              .acupOldExpDay);
      // COB(2819): MOVE ACUP-OLD-REISSUE-YEAR       TO RISYEARO OF CACTUPAO
      ws.coactup.cactupao.risyearo.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldAcctData
              .acupOldReissueDateParts
              .acupOldReissueYear);
      // COB(2820): MOVE ACUP-OLD-REISSUE-MON        TO RISMONO  OF CACTUPAO
      ws.coactup.cactupao.rismono.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldAcctData
              .acupOldReissueDateParts
              .acupOldReissueMon);
      // COB(2821): MOVE ACUP-OLD-REISSUE-DAY        TO RISDAYO  OF CACTUPAO
      ws.coactup.cactupao.risdayo.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldAcctData
              .acupOldReissueDateParts
              .acupOldReissueDay);
      // COB(2822): MOVE ACUP-OLD-GROUP-ID           TO AADDGRPO OF CACTUPAO
      ws.coactup.cactupao.aaddgrpo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldGroupId);
      // COB(2823): END-IF
    }
    // COB(2825): IF FOUND-CUST-IN-MASTER
    if (ws.wsMiscStorage.wsFileReadFlags.foundCustInMaster()) {
      // COB(2826): MOVE ACUP-OLD-CUST-ID-X          TO ACSTNUMO OF CACTUPAO
      ws.coactup.cactupao.acstnumo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustIdX);
      // COB(2827): MOVE ACUP-OLD-CUST-SSN-X(1:3)    TO ACTSSN1O OF CACTUPAO
      ws.coactup.cactupao.actssn1o.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustSsnX, 0, 3);
      // COB(2828): MOVE ACUP-OLD-CUST-SSN-X(4:2)    TO ACTSSN2O OF CACTUPAO
      ws.coactup.cactupao.actssn2o.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustSsnX, 3, 0, 2);
      // COB(2829): MOVE ACUP-OLD-CUST-SSN-X(6:4)    TO ACTSSN3O OF CACTUPAO
      ws.coactup.cactupao.actssn3o.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustSsnX, 5, 0, 4);
      // COB(2830): MOVE ACUP-OLD-CUST-FICO-SCORE-X  TO ACSTFCOO OF CACTUPAO
      ws.coactup.cactupao.acstfcoo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustFicoScoreX);
      // COB(2831): MOVE ACUP-OLD-CUST-DOB-YEAR      TO DOBYEARO OF CACTUPAO
      ws.coactup.cactupao.dobyearo.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldCustData
              .acupOldCustDobParts
              .acupOldCustDobYear);
      // COB(2832): MOVE ACUP-OLD-CUST-DOB-MON       TO DOBMONO  OF CACTUPAO
      ws.coactup.cactupao.dobmono.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldCustData
              .acupOldCustDobParts
              .acupOldCustDobMon);
      // COB(2833): MOVE ACUP-OLD-CUST-DOB-DAY       TO DOBDAYO  OF CACTUPAO
      ws.coactup.cactupao.dobdayo.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldCustData
              .acupOldCustDobParts
              .acupOldCustDobDay);
      // COB(2834): MOVE ACUP-OLD-CUST-FIRST-NAME    TO ACSFNAMO OF CACTUPAO
      ws.coactup.cactupao.acsfnamo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustFirstName);
      // COB(2835): MOVE ACUP-OLD-CUST-MIDDLE-NAME   TO ACSMNAMO OF CACTUPAO
      ws.coactup.cactupao.acsmnamo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustMiddleName);
      // COB(2836): MOVE ACUP-OLD-CUST-LAST-NAME     TO ACSLNAMO OF CACTUPAO
      ws.coactup.cactupao.acslnamo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustLastName);
      // COB(2837): MOVE ACUP-OLD-CUST-ADDR-LINE-1   TO ACSADL1O OF CACTUPAO
      ws.coactup.cactupao.acsadl1o.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine1);
      // COB(2838): MOVE ACUP-OLD-CUST-ADDR-LINE-2   TO ACSADL2O OF CACTUPAO
      ws.coactup.cactupao.acsadl2o.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine2);
      // COB(2839): MOVE ACUP-OLD-CUST-ADDR-LINE-3   TO ACSCITYO OF CACTUPAO
      ws.coactup.cactupao.acscityo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine3);
      // COB(2840): MOVE ACUP-OLD-CUST-ADDR-STATE-CD TO ACSSTTEO OF CACTUPAO
      ws.coactup.cactupao.acsstteo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrStateCd);
      // COB(2841): MOVE ACUP-OLD-CUST-ADDR-ZIP      TO ACSZIPCO OF CACTUPAO
      ws.coactup.cactupao.acszipco.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrZip);
      // COB(2842): MOVE ACUP-OLD-CUST-ADDR-COUNTRY-CD
      // COB(2842):                                  TO ACSCTRYO OF CACTUPAO
      ws.coactup.cactupao.acsctryo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrCountryCd);
      // COB(2844): MOVE ACUP-OLD-CUST-PHONE-NUM-1(2:3)
      // COB(2844):                                  TO ACSPH1AO OF CACTUPAO
      ws.coactup.cactupao.acsph1ao.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum1, 1, 0, 3);
      // COB(2846): MOVE ACUP-OLD-CUST-PHONE-NUM-1(6:3)
      // COB(2846):                                  TO ACSPH1BO OF CACTUPAO
      ws.coactup.cactupao.acsph1bo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum1, 5, 0, 3);
      // COB(2848): MOVE ACUP-OLD-CUST-PHONE-NUM-1(10:4)
      // COB(2848):                                  TO ACSPH1CO OF CACTUPAO
      ws.coactup.cactupao.acsph1co.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum1, 9, 0, 4);
      // COB(2850): MOVE ACUP-OLD-CUST-PHONE-NUM-2(2:3)
      // COB(2850):                                  TO ACSPH2AO OF CACTUPAO
      ws.coactup.cactupao.acsph2ao.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum2, 1, 0, 3);
      // COB(2852): MOVE ACUP-OLD-CUST-PHONE-NUM-2(6:3)
      // COB(2852):                                  TO ACSPH2BO OF CACTUPAO
      ws.coactup.cactupao.acsph2bo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum2, 5, 0, 3);
      // COB(2854): MOVE ACUP-OLD-CUST-PHONE-NUM-2(10:4)
      // COB(2854):                                  TO ACSPH2CO OF CACTUPAO
      ws.coactup.cactupao.acsph2co.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum2, 9, 0, 4);
      // COB(2856): MOVE ACUP-OLD-CUST-GOVT-ISSUED-ID
      // COB(2856):                                  TO ACSGOVTO OF CACTUPAO
      ws.coactup.cactupao.acsgovto.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustGovtIssuedId);
      // COB(2858): MOVE ACUP-OLD-CUST-EFT-ACCOUNT-ID
      // COB(2858):                                  TO ACSEFTCO OF CACTUPAO
      ws.coactup.cactupao.acseftco.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustEftAccountId);
      // COB(2860): MOVE ACUP-OLD-CUST-PRI-HOLDER-IND
      // COB(2860):                                  TO ACSPFLGO OF CACTUPAO
      ws.coactup.cactupao.acspflgo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPriHolderInd);
      // COB(2862): END-IF
    }
  }

  protected void _3203ShowUpdatedValues() {
    // COB(2870): MOVE ACUP-NEW-ACTIVE-STATUS         TO ACSTTUSO OF CACTUPAO
    ws.coactup.cactupao.acsttuso.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewActiveStatus);
    // COB(2872): IF FLG-CRED-LIMIT-ISVALID
    if (ws.wsMiscStorage.wsNonKeyFlags.flgCredLimitIsvalid()) {
      // COB(2873): MOVE ACUP-NEW-CREDIT-LIMIT-N     TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCreditLimitN);
      // COB(2874): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACRDLIMO OF CACTUPAO
      ws.coactup.cactupao.acrdlimo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2875): ELSE
    } else {
      // COB(2876): MOVE ACUP-NEW-CREDIT-LIMIT-X     TO ACRDLIMO OF CACTUPAO
      ws.coactup.cactupao.acrdlimo.setValue(
          ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCreditLimitX);
      // COB(2877): END-IF
    }
    // COB(2879): IF FLG-CASH-CREDIT-LIMIT-ISVALID
    if (ws.wsMiscStorage.wsNonKeyFlags.flgCashCreditLimitIsvalid()) {
      // COB(2880): MOVE ACUP-NEW-CASH-CREDIT-LIMIT-N
      // COB(2880):                                  TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCashCreditLimitN);
      // COB(2882): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACSHLIMO OF CACTUPAO
      ws.coactup.cactupao.acshlimo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2883): ELSE
    } else {
      // COB(2884): MOVE ACUP-NEW-CASH-CREDIT-LIMIT-X
      // COB(2884):                                  TO ACSHLIMO OF CACTUPAO
      ws.coactup.cactupao.acshlimo.setValue(
          ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCashCreditLimitX);
      // COB(2886): END-IF
    }
    // COB(2888): IF FLG-CURR-BAL-ISVALID
    if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrBalIsvalid()) {
      // COB(2889): MOVE ACUP-NEW-CURR-BAL-N         TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrBalN);
      // COB(2890): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACURBALO OF CACTUPAO
      ws.coactup.cactupao.acurbalo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2891): ELSE
    } else {
      // COB(2892): MOVE ACUP-NEW-CURR-BAL-X         TO ACURBALO OF CACTUPAO
      ws.coactup.cactupao.acurbalo.setValue(
          ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrBalX);
      // COB(2893): END-IF
    }
    // COB(2895): IF FLG-CURR-CYC-CREDIT-ISVALID
    if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycCreditIsvalid()) {
      // COB(2896): MOVE ACUP-NEW-CURR-CYC-CREDIT-N  TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrCycCreditN);
      // COB(2897): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACRCYCRO OF CACTUPAO
      ws.coactup.cactupao.acrcycro.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2898): ELSE
    } else {
      // COB(2899): MOVE ACUP-NEW-CURR-CYC-CREDIT-X  TO ACRCYCRO OF CACTUPAO
      ws.coactup.cactupao.acrcycro.setValue(
          ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycCreditX);
      // COB(2900): END-IF
    }
    // COB(2902): IF FLG-CURR-CYC-DEBIT-ISVALID
    if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycDebitIsvalid()) {
      // COB(2903): MOVE ACUP-NEW-CURR-CYC-DEBIT-N   TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrCycDebitN);
      // COB(2904): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACRCYDBO OF CACTUPAO
      ws.coactup.cactupao.acrcydbo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2905): ELSE
    } else {
      // COB(2906): MOVE ACUP-NEW-CURR-CYC-DEBIT-X   TO ACRCYDBO OF CACTUPAO
      ws.coactup.cactupao.acrcydbo.setValue(
          ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycDebitX);
      // COB(2907): END-IF
    }
    // COB(2909): MOVE ACUP-NEW-OPEN-YEAR             TO OPNYEARO OF CACTUPAO
    ws.coactup.cactupao.opnyearo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenYear);
    // COB(2910): MOVE ACUP-NEW-OPEN-MON              TO OPNMONO  OF CACTUPAO
    ws.coactup.cactupao.opnmono.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenMon);
    // COB(2911): MOVE ACUP-NEW-OPEN-DAY              TO OPNDAYO  OF CACTUPAO
    ws.coactup.cactupao.opndayo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenDay);
    // COB(2913): MOVE ACUP-NEW-EXP-YEAR              TO EXPYEARO OF CACTUPAO
    ws.coactup.cactupao.expyearo.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewExpiraionDateParts
            .acupNewExpYear);
    // COB(2914): MOVE ACUP-NEW-EXP-MON               TO EXPMONO  OF CACTUPAO
    ws.coactup.cactupao.expmono.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewExpiraionDateParts
            .acupNewExpMon);
    // COB(2915): MOVE ACUP-NEW-EXP-DAY               TO EXPDAYO  OF CACTUPAO
    ws.coactup.cactupao.expdayo.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewExpiraionDateParts
            .acupNewExpDay);
    // COB(2916): MOVE ACUP-NEW-REISSUE-YEAR          TO RISYEARO OF CACTUPAO
    ws.coactup.cactupao.risyearo.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewReissueDateParts
            .acupNewReissueYear);
    // COB(2917): MOVE ACUP-NEW-REISSUE-MON           TO RISMONO  OF CACTUPAO
    ws.coactup.cactupao.rismono.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewReissueDateParts
            .acupNewReissueMon);
    // COB(2918): MOVE ACUP-NEW-REISSUE-DAY           TO RISDAYO  OF CACTUPAO
    ws.coactup.cactupao.risdayo.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewReissueDateParts
            .acupNewReissueDay);
    // COB(2919): MOVE ACUP-NEW-GROUP-ID              TO AADDGRPO OF CACTUPAO
    ws.coactup.cactupao.aaddgrpo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewGroupId);
    // COB(2920): MOVE ACUP-NEW-CUST-ID-X             TO ACSTNUMO OF CACTUPAO
    ws.coactup.cactupao.acstnumo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustIdX);
    // COB(2921): MOVE ACUP-NEW-CUST-SSN-1            TO ACTSSN1O OF CACTUPAO
    ws.coactup.cactupao.actssn1o.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn1);
    // COB(2922): MOVE ACUP-NEW-CUST-SSN-2            TO ACTSSN2O OF CACTUPAO
    ws.coactup.cactupao.actssn2o.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn2);
    // COB(2923): MOVE ACUP-NEW-CUST-SSN-3            TO ACTSSN3O OF CACTUPAO
    ws.coactup.cactupao.actssn3o.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn3);
    // COB(2924): MOVE ACUP-NEW-CUST-FICO-SCORE-X     TO ACSTFCOO OF CACTUPAO
    ws.coactup.cactupao.acstfcoo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFicoScoreX);
    // COB(2925): MOVE ACUP-NEW-CUST-DOB-YEAR         TO DOBYEARO OF CACTUPAO
    ws.coactup.cactupao.dobyearo.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustDobParts
            .acupNewCustDobYear);
    // COB(2926): MOVE ACUP-NEW-CUST-DOB-MON          TO DOBMONO  OF CACTUPAO
    ws.coactup.cactupao.dobmono.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobMon);
    // COB(2927): MOVE ACUP-NEW-CUST-DOB-DAY          TO DOBDAYO  OF CACTUPAO
    ws.coactup.cactupao.dobdayo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobDay);
    // COB(2928): MOVE ACUP-NEW-CUST-FIRST-NAME       TO ACSFNAMO OF CACTUPAO
    ws.coactup.cactupao.acsfnamo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFirstName);
    // COB(2929): MOVE ACUP-NEW-CUST-MIDDLE-NAME      TO ACSMNAMO OF CACTUPAO
    ws.coactup.cactupao.acsmnamo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustMiddleName);
    // COB(2930): MOVE ACUP-NEW-CUST-LAST-NAME        TO ACSLNAMO OF CACTUPAO
    ws.coactup.cactupao.acslnamo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustLastName);
    // COB(2931): MOVE ACUP-NEW-CUST-ADDR-LINE-1      TO ACSADL1O OF CACTUPAO
    ws.coactup.cactupao.acsadl1o.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine1);
    // COB(2932): MOVE ACUP-NEW-CUST-ADDR-LINE-2      TO ACSADL2O OF CACTUPAO
    ws.coactup.cactupao.acsadl2o.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine2);
    // COB(2933): MOVE ACUP-NEW-CUST-ADDR-LINE-3      TO ACSCITYO OF CACTUPAO
    ws.coactup.cactupao.acscityo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine3);
    // COB(2934): MOVE ACUP-NEW-CUST-ADDR-STATE-CD    TO ACSSTTEO OF CACTUPAO
    ws.coactup.cactupao.acsstteo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrStateCd);
    // COB(2935): MOVE ACUP-NEW-CUST-ADDR-ZIP         TO ACSZIPCO OF CACTUPAO
    ws.coactup.cactupao.acszipco.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrZip);
    // COB(2936): MOVE ACUP-NEW-CUST-ADDR-COUNTRY-CD  TO ACSCTRYO OF CACTUPAO
    ws.coactup.cactupao.acsctryo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrCountryCd);
    // COB(2937): MOVE ACUP-NEW-CUST-PHONE-NUM-1A     TO ACSPH1AO OF CACTUPAO
    ws.coactup.cactupao.acsph1ao.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum1X
            .acupNewCustPhoneNum1a);
    // COB(2938): MOVE ACUP-NEW-CUST-PHONE-NUM-1B     TO ACSPH1BO OF CACTUPAO
    ws.coactup.cactupao.acsph1bo.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum1X
            .acupNewCustPhoneNum1b);
    // COB(2939): MOVE ACUP-NEW-CUST-PHONE-NUM-1C     TO ACSPH1CO OF CACTUPAO
    ws.coactup.cactupao.acsph1co.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum1X
            .acupNewCustPhoneNum1c);
    // COB(2940): MOVE ACUP-NEW-CUST-PHONE-NUM-2A     TO ACSPH2AO OF CACTUPAO
    ws.coactup.cactupao.acsph2ao.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum2X
            .acupNewCustPhoneNum2a);
    // COB(2941): MOVE ACUP-NEW-CUST-PHONE-NUM-2B     TO ACSPH2BO OF CACTUPAO
    ws.coactup.cactupao.acsph2bo.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum2X
            .acupNewCustPhoneNum2b);
    // COB(2942): MOVE ACUP-NEW-CUST-PHONE-NUM-2C     TO ACSPH2CO OF CACTUPAO
    ws.coactup.cactupao.acsph2co.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum2X
            .acupNewCustPhoneNum2c);
    // COB(2943): MOVE ACUP-NEW-CUST-GOVT-ISSUED-ID   TO ACSGOVTO OF CACTUPAO
    ws.coactup.cactupao.acsgovto.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustGovtIssuedId);
    // COB(2944): MOVE ACUP-NEW-CUST-EFT-ACCOUNT-ID   TO ACSEFTCO OF CACTUPAO
    ws.coactup.cactupao.acseftco.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustEftAccountId);
    // COB(2945): MOVE ACUP-NEW-CUST-PRI-HOLDER-IND   TO ACSPFLGO OF CACTUPAO
    ws.coactup.cactupao.acspflgo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPriHolderInd);
  }

  /***/
  protected void _3250SetupInfomsg() {
    // COB(2955): EVALUATE TRUE
    //     SETUP INFORMATION MESSAGE
    // COB(2956): WHEN CDEMO-PGM-ENTER
    if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()) {
      // COB(2957): SET  PROMPT-FOR-SEARCH-KEYS TO TRUE
      ws.wsMiscStorage.setPromptForSearchKeys(true);
      // COB(2958): WHEN ACUP-DETAILS-NOT-FETCHED
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched()) {
      // COB(2959): SET PROMPT-FOR-SEARCH-KEYS      TO TRUE
      ws.wsMiscStorage.setPromptForSearchKeys(true);
      // COB(2960): WHEN ACUP-SHOW-DETAILS
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupShowDetails()) {
      // COB(2961): SET PROMPT-FOR-CHANGES         TO TRUE
      ws.wsMiscStorage.setPromptForChanges(true);
      // COB(2962): WHEN ACUP-CHANGES-NOT-OK
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesNotOk()) {
      // COB(2963): SET PROMPT-FOR-CHANGES         TO TRUE
      ws.wsMiscStorage.setPromptForChanges(true);
      // COB(2964): WHEN ACUP-CHANGES-OK-NOT-CONFIRMED
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkNotConfirmed()) {
      // COB(2965): SET PROMPT-FOR-CONFIRMATION    TO TRUE
      ws.wsMiscStorage.setPromptForConfirmation(true);
      // COB(2966): WHEN ACUP-CHANGES-OKAYED-AND-DONE
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedAndDone()) {
      // COB(2967): SET CONFIRM-UPDATE-SUCCESS     TO TRUE
      ws.wsMiscStorage.setConfirmUpdateSuccess(true);
      // COB(2969): WHEN ACUP-CHANGES-OKAYED-LOCK-ERROR
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedLockError()) {
      // COB(2970): SET INFORM-FAILURE             TO TRUE
      ws.wsMiscStorage.setInformFailure(true);
      // COB(2971): WHEN ACUP-CHANGES-OKAYED-BUT-FAILED
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedButFailed()) {
      // COB(2972): SET INFORM-FAILURE             TO TRUE
      ws.wsMiscStorage.setInformFailure(true);
      // COB(2973): WHEN WS-NO-INFO-MESSAGE
    } else if (ws.wsMiscStorage.wsNoInfoMessage()) {
      // COB(2974): SET PROMPT-FOR-SEARCH-KEYS      TO TRUE
      ws.wsMiscStorage.setPromptForSearchKeys(true);
      // COB(2975): END-EVALUATE
    }
    // COB(2977): MOVE WS-INFO-MSG                    TO INFOMSGO OF CACTUPAO
    ws.coactup.cactupao.infomsgo.setValue(ws.wsMiscStorage.wsInfoMsg);
    // COB(2979): MOVE WS-RETURN-MSG                  TO ERRMSGO OF CACTUPAO
    ws.coactup.cactupao.errmsgo.setValue(ws.wsMiscStorage.wsReturnMsg);
  }

  protected void _3300SetupScreenAttrs() {
    // COB(2987): PERFORM 3310-PROTECT-ALL-ATTRS
    // COB(2987):    THRU 3310-PROTECT-ALL-ATTRS-EXIT
    //
    //     PROTECT ALL FIELDS
    _3310ProtectAllAttrs();
    // COB(2991): EVALUATE TRUE
    //
    //     UNPROTECT BASED ON CONTEXT
    // COB(2992): WHEN ACUP-DETAILS-NOT-FETCHED
    if (ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched()) {
      // COB(2994): MOVE DFHBMFSE      TO ACCTSIDA OF CACTUPAI
      //             Make Account Id editable
      ws.coactup.cactupai.filler57.acctsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(2995): WHEN  ACUP-SHOW-DETAILS
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupShowDetails()
        // COB(2996): WHEN  ACUP-CHANGES-NOT-OK
        || ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesNotOk()) {
      // COB(2997): PERFORM 3320-UNPROTECT-FEW-ATTRS
      // COB(2997):    THRU 3320-UNPROTECT-FEW-ATTRS-EXIT
      _3320UnprotectFewAttrs();
      // COB(2999): WHEN ACUP-CHANGES-OK-NOT-CONFIRMED
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkNotConfirmed()
        // COB(3000): WHEN ACUP-CHANGES-OKAYED-AND-DONE
        || ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedAndDone()) {
      // COB(3001): CONTINUE
      // do nothing
      // COB(3002): WHEN OTHER
    } else {
      // COB(3003): MOVE DFHBMFSE      TO ACCTSIDA OF CACTUPAI
      ws.coactup.cactupai.filler57.acctsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(3004): END-EVALUATE
    }
    // COB(3007): EVALUATE TRUE
    //
    //     POSITION CURSOR - ORDER BASED ON SCREEN LOCATION
    // COB(3008): WHEN FOUND-ACCOUNT-DATA
    if ((ws.wsMiscStorage.foundAccountData())
        // COB(3009): WHEN NO-CHANGES-DETECTED
        || ws.wsMiscStorage.noChangesDetected()) {
      // COB(3010): MOVE -1              TO ACSTTUSL OF CACTUPAI
      ws.coactup.cactupai.acsttusl.setValue(-1);
      // COB(3011): WHEN FLG-ACCTFILTER-NOT-OK
    } else if (ws.wsMiscStorage.flgAcctfilterNotOk()
        // COB(3012): WHEN FLG-ACCTFILTER-BLANK
        || ws.wsMiscStorage.flgAcctfilterBlank()) {
      // COB(3013): MOVE -1             TO ACCTSIDL OF CACTUPAI
      ws.coactup.cactupai.acctsidl.setValue(-1);
      // COB(3015): WHEN FLG-ACCT-STATUS-NOT-OK
      //     Account Status
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgAcctStatusNotOk()
        // COB(3016): WHEN FLG-ACCT-STATUS-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgAcctStatusBlank()) {
      // COB(3017): MOVE -1              TO ACSTTUSL OF CACTUPAI
      ws.coactup.cactupai.acsttusl.setValue(-1);
      // COB(3019): WHEN FLG-OPEN-YEAR-NOT-OK
      //     Open Year
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenYearNotOk()
        // COB(3020): WHEN FLG-OPEN-YEAR-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenYearBlank()) {
      // COB(3021): MOVE -1              TO OPNYEARL OF CACTUPAI
      ws.coactup.cactupai.opnyearl.setValue(-1);
      // COB(3023): WHEN FLG-OPEN-MONTH-NOT-OK
      //     Open Month
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenMonthNotOk()
        // COB(3024): WHEN FLG-OPEN-MONTH-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenMonthBlank()) {
      // COB(3025): MOVE -1              TO OPNMONL  OF CACTUPAI
      ws.coactup.cactupai.opnmonl.setValue(-1);
      // COB(3027): WHEN FLG-OPEN-DAY-NOT-OK
      //     Open Day
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenDayNotOk()
        // COB(3028): WHEN FLG-OPEN-DAY-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenDayBlank()) {
      // COB(3029): MOVE -1              TO OPNDAYL  OF CACTUPAI
      ws.coactup.cactupai.opndayl.setValue(-1);
      // COB(3031): WHEN FLG-CRED-LIMIT-NOT-OK
      //     Credit Limit
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgCredLimitNotOk()
        // COB(3032): WHEN FLG-CRED-LIMIT-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgCredLimitBlank()) {
      // COB(3033): MOVE -1              TO ACRDLIML OF CACTUPAI
      ws.coactup.cactupai.acrdliml.setValue(-1);
      // COB(3035): WHEN FLG-EXPIRY-YEAR-NOT-OK
      //     Expiry Year
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryYearNotOk()
        // COB(3036): WHEN FLG-EXPIRY-YEAR-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryYearBlank()) {
      // COB(3037): MOVE -1              TO EXPYEARL OF CACTUPAI
      ws.coactup.cactupai.expyearl.setValue(-1);
      // COB(3039): WHEN FLG-EXPIRY-MONTH-NOT-OK
      //     Expiry Month
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryMonthNotOk()
        // COB(3040): WHEN FLG-EXPIRY-MONTH-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryMonthBlank()) {
      // COB(3041): MOVE -1              TO EXPMONL  OF CACTUPAI
      ws.coactup.cactupai.expmonl.setValue(-1);
      // COB(3043): WHEN FLG-EXPIRY-DAY-NOT-OK
      //     Expiry Day
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryDayNotOk()
        // COB(3044): WHEN FLG-EXPIRY-DAY-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryDayBlank()) {
      // COB(3045): MOVE -1              TO EXPDAYL  OF CACTUPAI
      ws.coactup.cactupai.expdayl.setValue(-1);
      // COB(3047): WHEN FLG-CASH-CREDIT-LIMIT-NOT-OK
      //     Cash credit limit
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgCashCreditLimitNotOk()
        // COB(3048): WHEN FLG-CASH-CREDIT-LIMIT-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgCashCreditLimitBlank()) {
      // COB(3049): MOVE -1              TO ACSHLIML OF CACTUPAI
      ws.coactup.cactupai.acshliml.setValue(-1);
      // COB(3051): WHEN FLG-REISSUE-YEAR-NOT-OK
      //     Reissue Year
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueYearNotOk()
        // COB(3052): WHEN FLG-REISSUE-YEAR-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueYearBlank()) {
      // COB(3053): MOVE -1              TO RISYEARL OF CACTUPAI
      ws.coactup.cactupai.risyearl.setValue(-1);
      // COB(3055): WHEN FLG-REISSUE-MONTH-NOT-OK
      //     Expiry Month
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueMonthNotOk()
        // COB(3056): WHEN FLG-REISSUE-MONTH-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueMonthBlank()) {
      // COB(3057): MOVE -1              TO RISMONL  OF CACTUPAI
      ws.coactup.cactupai.rismonl.setValue(-1);
      // COB(3059): WHEN FLG-REISSUE-DAY-NOT-OK
      //     Expiry Day
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueDayNotOk()
        // COB(3060): WHEN FLG-REISSUE-DAY-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueDayBlank()) {
      // COB(3061): MOVE -1              TO RISDAYL  OF CACTUPAI
      ws.coactup.cactupai.risdayl.setValue(-1);
      // COB(3064): WHEN FLG-CURR-BAL-NOT-OK
      //
      //     Current Balance
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrBalNotOk()
        // COB(3065): WHEN FLG-CURR-BAL-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgCurrBalBlank()) {
      // COB(3066): MOVE -1              TO ACURBALL OF CACTUPAI
      ws.coactup.cactupai.acurball.setValue(-1);
      // COB(3068): WHEN FLG-CURR-CYC-CREDIT-NOT-OK
      //     Current Cycle Credit
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycCreditNotOk()
        // COB(3069): WHEN FLG-CURR-CYC-CREDIT-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycCreditBlank()) {
      // COB(3070): MOVE -1              TO ACRCYCRL OF CACTUPAI
      ws.coactup.cactupai.acrcycrl.setValue(-1);
      // COB(3072): WHEN FLG-CURR-CYC-DEBIT-NOT-OK
      //     Current Cycle Debit
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycDebitNotOk()
        // COB(3073): WHEN FLG-CURR-CYC-DEBIT-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycDebitBlank()) {
      // COB(3074): MOVE -1              TO ACRCYDBL OF CACTUPAI
      ws.coactup.cactupai.acrcydbl.setValue(-1);
      // COB(3076): WHEN FLG-EDIT-US-SSN-PART1-NOT-OK
      //     SSN Part 1
    } else if (ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart1NotOk()
        // COB(3077): WHEN FLG-EDIT-US-SSN-PART1-BLANK
        || ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart1Blank()) {
      // COB(3078): MOVE -1              TO ACTSSN1L OF CACTUPAI
      ws.coactup.cactupai.actssn1l.setValue(-1);
      // COB(3080): WHEN FLG-EDIT-US-SSN-PART2-NOT-OK
      //     SSN Part 2
    } else if (ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart2NotOk()
        // COB(3081): WHEN FLG-EDIT-US-SSN-PART2-BLANK
        || ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart2Blank()) {
      // COB(3082): MOVE -1              TO ACTSSN2L  OF CACTUPAI
      ws.coactup.cactupai.actssn2l.setValue(-1);
      // COB(3084): WHEN FLG-EDIT-US-SSN-PART3-NOT-OK
      //     SSN Part 3
    } else if (ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart3NotOk()
        // COB(3085): WHEN FLG-EDIT-US-SSN-PART3-BLANK
        || ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart3Blank()) {
      // COB(3086): MOVE -1              TO ACTSSN3L  OF CACTUPAI
      ws.coactup.cactupai.actssn3l.setValue(-1);
      // COB(3088): WHEN FLG-DT-OF-BIRTH-YEAR-NOT-OK
      //     Date of Birth Year
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthYearNotOk()
        // COB(3089): WHEN FLG-DT-OF-BIRTH-YEAR-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthYearBlank()) {
      // COB(3090): MOVE -1              TO DOBYEARL OF CACTUPAI
      ws.coactup.cactupai.dobyearl.setValue(-1);
      // COB(3092): WHEN FLG-DT-OF-BIRTH-MONTH-NOT-OK
      //     Date of Birth Month
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthMonthNotOk()
        // COB(3093): WHEN FLG-DT-OF-BIRTH-MONTH-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthMonthBlank()) {
      // COB(3094): MOVE -1              TO DOBMONL  OF CACTUPAI
      ws.coactup.cactupai.dobmonl.setValue(-1);
      // COB(3096): WHEN FLG-DT-OF-BIRTH-DAY-NOT-OK
      //     Date of Birth Day
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthDayNotOk()
        // COB(3097): WHEN FLG-DT-OF-BIRTH-DAY-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthDayBlank()) {
      // COB(3098): MOVE -1              TO DOBDAYL  OF CACTUPAI
      ws.coactup.cactupai.dobdayl.setValue(-1);
      // COB(3100): WHEN FLG-FICO-SCORE-NOT-OK
      //     FICO Score
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgFicoScoreNotOk()
        // COB(3101): WHEN FLG-FICO-SCORE-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgFicoScoreBlank()) {
      // COB(3102): MOVE -1              TO ACSTFCOL OF CACTUPAI
      ws.coactup.cactupai.acstfcol.setValue(-1);
      // COB(3104): WHEN FLG-FIRST-NAME-NOT-OK
      //     First Name
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgFirstNameNotOk()
        // COB(3105): WHEN FLG-FIRST-NAME-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgFirstNameBlank()) {
      // COB(3106): MOVE -1              TO ACSFNAML OF CACTUPAI
      ws.coactup.cactupai.acsfnaml.setValue(-1);
      // COB(3108): WHEN FLG-MIDDLE-NAME-NOT-OK
      //     Middle Name
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgMiddleNameNotOk()) {
      // COB(3109): MOVE -1              TO ACSMNAML OF CACTUPAI
      ws.coactup.cactupai.acsmnaml.setValue(-1);
      // COB(3111): WHEN FLG-LAST-NAME-NOT-OK
      //     Last Name
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgLastNameNotOk()
        // COB(3112): WHEN FLG-LAST-NAME-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgLastNameBlank()) {
      // COB(3113): MOVE -1              TO ACSLNAML OF CACTUPAI
      ws.coactup.cactupai.acslnaml.setValue(-1);
      // COB(3115): WHEN FLG-ADDRESS-LINE-1-NOT-OK
      //     Address Line 1
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgAddressLine1NotOk()
        // COB(3116): WHEN FLG-ADDRESS-LINE-1-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgAddressLine1Blank()) {
      // COB(3117): MOVE -1              TO ACSADL1L OF CACTUPAI
      ws.coactup.cactupai.acsadl1l.setValue(-1);
      // COB(3119): WHEN FLG-STATE-NOT-OK
      //     State (appears next to Line 2 on screen before city)
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgStateNotOk()
        // COB(3120): WHEN FLG-STATE-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgStateBlank()) {
      // COB(3121): MOVE -1              TO ACSSTTEL OF CACTUPAI
      ws.coactup.cactupai.acssttel.setValue(-1);
      // COB(3124): WHEN FLG-ZIPCODE-NOT-OK
      //     Address Line 2 has no edits
      //     Zip code
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgZipcodeNotOk()
        // COB(3125): WHEN FLG-ZIPCODE-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgZipcodeBlank()) {
      // COB(3126): MOVE -1              TO ACSZIPCL OF CACTUPAI
      ws.coactup.cactupai.acszipcl.setValue(-1);
      // COB(3128): WHEN FLG-CITY-NOT-OK
      //     Address Line 3 (City)
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgCityNotOk()
        // COB(3129): WHEN FLG-CITY-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgCityBlank()) {
      // COB(3130): MOVE -1              TO ACSCITYL OF CACTUPAI
      ws.coactup.cactupai.acscityl.setValue(-1);
      // COB(3132): WHEN FLG-COUNTRY-NOT-OK
      //     Country edits.
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgCountryNotOk()
        // COB(3133): WHEN FLG-COUNTRY-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgCountryBlank()) {
      // COB(3134): MOVE -1              TO ACSCTRYL OF CACTUPAI
      ws.coactup.cactupai.acsctryl.setValue(-1);
      // COB(3136): WHEN FLG-PHONE-NUM-1A-NOT-OK
      //     Phone 1
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
            .flgPhoneNum1aNotOk()
        // COB(3137): WHEN FLG-PHONE-NUM-1A-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
            .flgPhoneNum1aBlank()) {
      // COB(3138): MOVE -1              TO ACSPH1AL OF CACTUPAI
      ws.coactup.cactupai.acsph1al.setValue(-1);
      // COB(3139): WHEN FLG-PHONE-NUM-1B-NOT-OK
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
            .flgPhoneNum1bNotOk()
        // COB(3140): WHEN FLG-PHONE-NUM-1B-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
            .flgPhoneNum1bBlank()) {
      // COB(3141): MOVE -1              TO ACSPH1BL OF CACTUPAI
      ws.coactup.cactupai.acsph1bl.setValue(-1);
      // COB(3142): WHEN FLG-PHONE-NUM-1C-NOT-OK
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
            .flgPhoneNum1cNotOk()
        // COB(3143): WHEN FLG-PHONE-NUM-1C-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
            .flgPhoneNum1cBlank()) {
      // COB(3144): MOVE -1              TO ACSPH1CL OF CACTUPAI
      ws.coactup.cactupai.acsph1cl.setValue(-1);
      // COB(3146): WHEN FLG-PHONE-NUM-2A-NOT-OK
      //     Phone 2
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
            .flgPhoneNum2aNotOk()
        // COB(3147): WHEN FLG-PHONE-NUM-2A-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
            .flgPhoneNum2aBlank()) {
      // COB(3148): MOVE -1              TO ACSPH2AL OF CACTUPAI
      ws.coactup.cactupai.acsph2al.setValue(-1);
      // COB(3149): WHEN FLG-PHONE-NUM-2B-NOT-OK
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
            .flgPhoneNum2bNotOk()
        // COB(3150): WHEN FLG-PHONE-NUM-2B-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
            .flgPhoneNum2bBlank()) {
      // COB(3151): MOVE -1              TO ACSPH2BL OF CACTUPAI
      ws.coactup.cactupai.acsph2bl.setValue(-1);
      // COB(3152): WHEN FLG-PHONE-NUM-2C-NOT-OK
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
            .flgPhoneNum2cNotOk()
        // COB(3153): WHEN FLG-PHONE-NUM-2C-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
            .flgPhoneNum2cBlank()) {
      // COB(3154): MOVE -1              TO ACSPH2CL OF CACTUPAI
      ws.coactup.cactupai.acsph2cl.setValue(-1);
      // COB(3156): WHEN FLG-EFT-ACCOUNT-ID-NOT-OK
      //     EFT Account Id
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgEftAccountIdNotOk()
        // COB(3157): WHEN FLG-EFT-ACCOUNT-ID-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgEftAccountIdBlank()) {
      // COB(3158): MOVE -1              TO ACSEFTCL OF CACTUPAI
      ws.coactup.cactupai.acseftcl.setValue(-1);
      // COB(3160): WHEN FLG-PRI-CARDHOLDER-NOT-OK
      //     Primary Card Holder
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgPriCardholderNotOk()
        // COB(3161): WHEN FLG-PRI-CARDHOLDER-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgPriCardholderBlank()) {
      // COB(3162): MOVE -1              TO ACSPFLGL OF CACTUPAI
      ws.coactup.cactupai.acspflgl.setValue(-1);
      // COB(3163): WHEN OTHER
    } else {
      // COB(3164): MOVE -1              TO ACCTSIDL OF CACTUPAI
      ws.coactup.cactupai.acctsidl.setValue(-1);
      // COB(3165): END-EVALUATE
    }
    // COB(3169): IF CDEMO-LAST-MAPSET   EQUAL LIT-CCLISTMAPSET
    //
    //
    //     SETUP COLOR
    if (ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.equals(
        ws.wsLiterals.litCclistmapset)) {
      // COB(3170): MOVE DFHDFCOL            TO ACCTSIDC OF CACTUPAO
      ws.coactup.cactupao.acctsidc.setValue(Dfhbmsca.DFHDFCOL.getValue());
      // COB(3171): END-IF
    }
    // COB(3174): IF FLG-ACCTFILTER-NOT-OK
    //
    //     Account Filter
    if (ws.wsMiscStorage.flgAcctfilterNotOk()) {
      // COB(3175): MOVE DFHRED              TO ACCTSIDC OF CACTUPAO
      ws.coactup.cactupao.acctsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(3176): END-IF
    }
    // COB(3178): IF  FLG-ACCTFILTER-BLANK
    // COB(3178): AND CDEMO-PGM-REENTER
    if (ws.wsMiscStorage.flgAcctfilterBlank()
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(3180): MOVE '*'                TO ACCTSIDO OF CACTUPAO
      ws.coactup.cactupao.acctsido.setString("*");
      // COB(3181): MOVE DFHRED             TO ACCTSIDC OF CACTUPAO
      ws.coactup.cactupao.acctsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(3182): END-IF
    }
    // COB(3184): IF ACUP-DETAILS-NOT-FETCHED
    // COB(3184): OR FLG-ACCTFILTER-BLANK
    // COB(3184): OR FLG-ACCTFILTER-NOT-OK
    if (ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched()
        || ws.wsMiscStorage.flgAcctfilterBlank()
        || ws.wsMiscStorage.flgAcctfilterNotOk()) {
      // COB(3187): GO TO 3300-SETUP-SCREEN-ATTRS-EXIT
      return;
      // COB(3188): ELSE
    } else {
      // COB(3189): CONTINUE
      // do nothing
      // COB(3190): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    if ((ws.wsMiscStorage.wsNonKeyFlags.flgAcctStatusNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.flgAcctStatusBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acsttusc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.flgAcctStatusBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acsttuso.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenYearNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenYearBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.opnyearc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenYearBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.opnyearo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenMonthNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenMonthBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.opnmonc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenMonthBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.opnmono.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenDayNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenDayBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.opndayc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenDayBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.opndayo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.flgCredLimitNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.flgCredLimitBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acrdlimc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.flgCredLimitBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acrdlimo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryYearNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryYearBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.expyearc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryYearBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.expyearo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryMonthNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryMonthBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.expmonc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryMonthBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.expmono.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryDayNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryDayBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.expdayc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryDayBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.expdayo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.flgCashCreditLimitNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.flgCashCreditLimitBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acshlimc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.flgCashCreditLimitBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acshlimo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueYearNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueYearBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.risyearc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueYearBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.risyearo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueMonthNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueMonthBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.rismonc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueMonthBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.rismono.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueDayNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueDayBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.risdayc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueDayBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.risdayo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.flgCurrBalNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.flgCurrBalBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acurbalc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrBalBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acurbalo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycCreditNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycCreditBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acrcycrc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycCreditBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acrcycro.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycDebitNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycDebitBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acrcydbc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycDebitBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acrcydbo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart1NotOk()
            || ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart1Blank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.actssn1c.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart1Blank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.actssn1o.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart2NotOk()
            || ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart2Blank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.actssn2c.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart2Blank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.actssn2o.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart3NotOk()
            || ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart3Blank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.actssn3c.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart3Blank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.actssn3o.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthYearNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthYearBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.dobyearc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthYearBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.dobyearo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthMonthNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthMonthBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.dobmonc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthMonthBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.dobmono.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthDayNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthDayBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.dobdayc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthDayBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.dobdayo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.flgFicoScoreNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.flgFicoScoreBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acstfcoc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.flgFicoScoreBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acstfcoo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgFirstNameNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgFirstNameBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acsfnamc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgFirstNameBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acsfnamo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgMiddleNameNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgMiddleNameBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acsmnamc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgMiddleNameBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acsmnamo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgLastNameNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgLastNameBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acslnamc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgLastNameBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acslnamo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgAddressLine1NotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgAddressLine1Blank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acsadl1c.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgAddressLine1Blank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acsadl1o.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgStateNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgStateBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acssttec.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgStateBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acsstteo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgAddressLine2NotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgAddressLine2Blank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acsadl2c.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgAddressLine2Blank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acsadl2o.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgZipcodeNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgZipcodeBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acszipcc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgZipcodeBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acszipco.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgCityNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgCityBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acscityc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgCityBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acscityo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgCountryNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgCountryBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acsctryc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgCountryBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acsctryo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs.flgPhoneNum1aNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
                .flgPhoneNum1aBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acsph1ac.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
          .flgPhoneNum1aBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acsph1ao.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs.flgPhoneNum1bNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
                .flgPhoneNum1bBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acsph1bc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
          .flgPhoneNum1bBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acsph1bo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs.flgPhoneNum1cNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
                .flgPhoneNum1cBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acsph1cc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
          .flgPhoneNum1cBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acsph1co.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs.flgPhoneNum2aNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
                .flgPhoneNum2aBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acsph2ac.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
          .flgPhoneNum2aBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acsph2ao.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs.flgPhoneNum2bNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
                .flgPhoneNum2bBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acsph2bc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
          .flgPhoneNum2bBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acsph2bo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs.flgPhoneNum2cNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
                .flgPhoneNum2cBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acsph2cc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
          .flgPhoneNum2cBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acsph2co.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.flgPriCardholderNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.flgPriCardholderBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acspflgc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.flgPriCardholderBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acspflgo.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    //  Copyright Amazon.com, Inc. or its affiliates.
    //  All Rights Reserved.
    //
    //  Licensed under the Apache License, Version 2.0 (the "License").
    //  You may not use this file except in compliance with the License.
    //  You may obtain a copy of the License at
    //
    //     http:--www.apache.org-licenses-LICENSE-2.0
    //
    //  Unless required by applicable law or agreed to in writing,
    //  software distributed under the License is distributed on an
    //  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //  either express or implied. See the License for the specific
    //  language governing permissions and limitations under the License
    // *****************************************************************
    //     Set (TESTVAR1) to red if in error and * if blankACSHLIM
    if ((ws.wsMiscStorage.wsNonKeyFlags.flgEftAccountIdNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.flgEftAccountIdBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.coactup.cactupao.acseftcc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.flgEftAccountIdBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.coactup.cactupao.acseftco.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
  }

  /***/
  protected void _3310ProtectAllAttrs() {
    // COB(3440): MOVE DFHBMPRF              TO ACCTSIDA OF CACTUPAI
    // COB(3440):                               ACSTTUSA OF CACTUPAI
    // COB(3440):       *Account Limits
    // COB(3440):                               ACRDLIMA OF CACTUPAI
    // COB(3440):                               ACSHLIMA OF CACTUPAI
    // COB(3440):                               ACURBALA OF CACTUPAI
    // COB(3440):                               ACRCYCRA OF CACTUPAI
    // COB(3440):                               ACRCYDBA OF CACTUPAI
    // COB(3440):       *Account dates
    // COB(3440):                               OPNYEARA OF CACTUPAI
    // COB(3440):                               OPNMONA  OF CACTUPAI
    // COB(3440):                               OPNDAYA  OF CACTUPAI
    // COB(3440):                               EXPYEARA OF CACTUPAI
    // COB(3440):                               EXPMONA  OF CACTUPAI
    // COB(3440):                               EXPDAYA  OF CACTUPAI
    // COB(3440):                               RISYEARA OF CACTUPAI
    // COB(3440):                               RISMONA  OF CACTUPAI
    // COB(3440):                               RISDAYA  OF CACTUPAI
    // COB(3440):       *
    // COB(3440):                               AADDGRPA OF CACTUPAI
    // COB(3440):       *Customer data
    // COB(3440):                               ACSTNUMA OF CACTUPAI
    // COB(3440):                               ACTSSN1A OF CACTUPAI
    // COB(3440):                               ACTSSN2A OF CACTUPAI
    // COB(3440):                               ACTSSN3A OF CACTUPAI
    // COB(3440):                               ACSTFCOA OF CACTUPAI
    // COB(3440):       *Date of Birth
    // COB(3440):                               DOBYEARA OF CACTUPAI
    // COB(3440):                               DOBMONA  OF CACTUPAI
    // COB(3440):                               DOBDAYA  OF CACTUPAI
    // COB(3440):       *
    // COB(3440):                               ACSFNAMA OF CACTUPAI
    // COB(3440):                               ACSMNAMA OF CACTUPAI
    // COB(3440):                               ACSLNAMA OF CACTUPAI
    // COB(3440):       *Address
    // COB(3440):                               ACSADL1A OF CACTUPAI
    // COB(3440):                               ACSADL2A OF CACTUPAI
    // COB(3440):                               ACSCITYA OF CACTUPAI
    // COB(3440):                               ACSSTTEA OF CACTUPAI
    // COB(3440):                               ACSZIPCA OF CACTUPAI
    // COB(3440):                               ACSCTRYA OF CACTUPAI
    // COB(3440):       *
    // COB(3440):                               ACSPH1AA OF CACTUPAI
    // COB(3440):                               ACSPH1BA OF CACTUPAI
    // COB(3440):                               ACSPH1CA OF CACTUPAI
    // COB(3440):                               ACSPH2AA OF CACTUPAI
    // COB(3440):                               ACSPH2BA OF CACTUPAI
    // COB(3440):                               ACSPH2CA OF CACTUPAI
    // COB(3440):       *
    // COB(3440):                               ACSGOVTA OF CACTUPAI
    // COB(3440):                               ACSEFTCA OF CACTUPAI
    // COB(3440):                               ACSPFLGA OF CACTUPAI
    // COB(3440):                               INFOMSGA OF CACTUPAI
    ws.coactup.cactupai.filler57.acctsida.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler63.acsttusa.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler87.acrdlima.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler111.acshlima.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler135.acurbala.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler141.acrcycra.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler153.acrcydba.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler69.opnyeara.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler75.opnmona.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler81.opndaya.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler93.expyeara.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler99.expmona.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler105.expdaya.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler117.risyeara.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler123.rismona.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler129.risdaya.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler147.aaddgrpa.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler159.acstnuma.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler165.actssn1a.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler171.actssn2a.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler177.actssn3a.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler201.acstfcoa.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler183.dobyeara.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler189.dobmona.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler195.dobdaya.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler207.acsfnama.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler213.acsmnama.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler219.acslnama.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler225.acsadl1a.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler237.acsadl2a.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler249.acscitya.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler231.acssttea.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler243.acszipca.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler255.acsctrya.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler261.acsph1aa.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler267.acsph1ba.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler273.acsph1ca.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler285.acsph2aa.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler291.acsph2ba.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler297.acsph2ca.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler279.acsgovta.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler303.acseftca.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler309.acspflga.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.coactup.cactupai.filler315.infomsga.setValue(Dfhbmsca.DFHBMPRF.getValue());
  }

  /***/
  protected void _3320UnprotectFewAttrs() {
    // COB(3500): MOVE DFHBMFSE              TO ACSTTUSA OF CACTUPAI
    // COB(3500):       *Account Limits
    // COB(3500):                               ACRDLIMA OF CACTUPAI
    // COB(3500):                               ACSHLIMA OF CACTUPAI
    // COB(3500):                               ACURBALA OF CACTUPAI
    // COB(3500):                               ACRCYCRA OF CACTUPAI
    // COB(3500):                               ACRCYDBA OF CACTUPAI
    // COB(3500):       *Account dates
    // COB(3500):       *Open Date
    // COB(3500):                               OPNYEARA OF CACTUPAI
    // COB(3500):                               OPNMONA  OF CACTUPAI
    // COB(3500):                               OPNDAYA  OF CACTUPAI
    // COB(3500):       *Expiry date
    // COB(3500):                               EXPYEARA OF CACTUPAI
    // COB(3500):                               EXPMONA  OF CACTUPAI
    // COB(3500):                               EXPDAYA  OF CACTUPAI
    // COB(3500):       *Reissue date
    // COB(3500):                               RISYEARA OF CACTUPAI
    // COB(3500):                               RISMONA  OF CACTUPAI
    // COB(3500):                               RISDAYA  OF CACTUPAI
    // COB(3500):       *Date of Birth
    // COB(3500):                               DOBYEARA OF CACTUPAI
    // COB(3500):                               DOBMONA  OF CACTUPAI
    // COB(3500):                               DOBDAYA  OF CACTUPAI
    // COB(3500):       *
    // COB(3500):       *
    // COB(3500):       *
    // COB(3500):                               AADDGRPA OF CACTUPAI
    ws.coactup.cactupai.filler63.acsttusa.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler87.acrdlima.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler111.acshlima.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler135.acurbala.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler141.acrcycra.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler153.acrcydba.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler69.opnyeara.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler75.opnmona.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler81.opndaya.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler93.expyeara.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler99.expmona.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler105.expdaya.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler117.risyeara.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler123.rismona.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler129.risdaya.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler183.dobyeara.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler189.dobmona.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler195.dobdaya.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler147.aaddgrpa.setValue(Dfhbmsca.DFHBMFSE.getValue());
    // COB(3529): MOVE DFHBMPRF            TO  ACSTNUMA OF CACTUPAI
    // Customer data
    ws.coactup.cactupai.filler159.acstnuma.setValue(Dfhbmsca.DFHBMPRF.getValue());
    // COB(3530): MOVE DFHBMFSE            TO  ACTSSN1A OF CACTUPAI
    // COB(3530):                              ACTSSN2A OF CACTUPAI
    // COB(3530):                              ACTSSN3A OF CACTUPAI
    // COB(3530):                              ACSTFCOA OF CACTUPAI
    // COB(3530):       *
    // COB(3530):                              ACSFNAMA OF CACTUPAI
    // COB(3530):                              ACSMNAMA OF CACTUPAI
    // COB(3530):                              ACSLNAMA OF CACTUPAI
    // COB(3530):       *Address
    // COB(3530):                              ACSADL1A OF CACTUPAI
    // COB(3530):                              ACSADL2A OF CACTUPAI
    // COB(3530):                              ACSCITYA OF CACTUPAI
    // COB(3530):                              ACSSTTEA OF CACTUPAI
    // COB(3530):                              ACSZIPCA OF CACTUPAI
    ws.coactup.cactupai.filler165.actssn1a.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler171.actssn2a.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler177.actssn3a.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler201.acstfcoa.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler207.acsfnama.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler213.acsmnama.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler219.acslnama.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler225.acsadl1a.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler237.acsadl2a.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler249.acscitya.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler231.acssttea.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler243.acszipca.setValue(Dfhbmsca.DFHBMFSE.getValue());
    // COB(3545): MOVE DFHBMPRF              TO ACSCTRYA OF CACTUPAI
    // Since most of the edits are USA specific protected country
    ws.coactup.cactupai.filler255.acsctrya.setValue(Dfhbmsca.DFHBMPRF.getValue());
    // COB(3547): MOVE DFHBMFSE              TO ACSPH1AA OF CACTUPAI
    // COB(3547):                               ACSPH1BA OF CACTUPAI
    // COB(3547):                               ACSPH1CA OF CACTUPAI
    ws.coactup.cactupai.filler261.acsph1aa.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler267.acsph1ba.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler273.acsph1ca.setValue(Dfhbmsca.DFHBMFSE.getValue());
    // COB(3551): MOVE DFHBMFSE              TO ACSPH2AA OF CACTUPAI
    // COB(3551):                               ACSPH2BA OF CACTUPAI
    // COB(3551):                               ACSPH2CA OF CACTUPAI
    // COB(3551):       *
    // COB(3551):                               ACSGOVTA OF CACTUPAI
    // COB(3551):                               ACSEFTCA OF CACTUPAI
    // COB(3551):                               ACSPFLGA OF CACTUPAI
    ws.coactup.cactupai.filler285.acsph2aa.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler291.acsph2ba.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler297.acsph2ca.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler279.acsgovta.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler303.acseftca.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler309.acspflga.setValue(Dfhbmsca.DFHBMFSE.getValue());
    // COB(3558): MOVE DFHBMPRF              TO INFOMSGA OF CACTUPAI
    ws.coactup.cactupai.filler315.infomsga.setValue(Dfhbmsca.DFHBMPRF.getValue());
  }

  /***/
  protected void _3390SetupInfomsgAttrs() {
    // COB(3565): IF  WS-NO-INFO-MESSAGE
    if (ws.wsMiscStorage.wsNoInfoMessage()) {
      // COB(3566): MOVE DFHBMDAR           TO INFOMSGA OF CACTUPAI
      ws.coactup.cactupai.filler315.infomsga.setValue(Dfhbmsca.DFHBMDAR.getValue());
      // COB(3567): ELSE
    } else {
      // COB(3568): MOVE DFHBMASB           TO INFOMSGA OF CACTUPAI
      ws.coactup.cactupai.filler315.infomsga.setValue(Dfhbmsca.DFHBMASB.getValue());
      // COB(3569): END-IF
    }
    // COB(3571): IF ACUP-CHANGES-MADE
    // COB(3571): AND NOT ACUP-CHANGES-OKAYED-AND-DONE
    if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesMade()
        && !ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedAndDone()) {
      // COB(3573): MOVE DFHBMASB           TO FKEY12A  OF CACTUPAI
      ws.coactup.cactupai.filler339.fkey12a.setValue(Dfhbmsca.DFHBMASB.getValue());
      // COB(3574): END-IF
    }
    // COB(3576): IF PROMPT-FOR-CONFIRMATION
    if (ws.wsMiscStorage.promptForConfirmation()) {
      // COB(3577): MOVE DFHBMASB           TO FKEY05A  OF CACTUPAI
      ws.coactup.cactupai.filler333.fkey05a.setValue(Dfhbmsca.DFHBMASB.getValue());
      // COB(3578): MOVE DFHBMASB           TO FKEY12A  OF CACTUPAI
      ws.coactup.cactupai.filler339.fkey12a.setValue(Dfhbmsca.DFHBMASB.getValue());
      // COB(3579): END-IF
    }
  }

  /***
   */
  protected void _3400SendScreen() {
    // COB(3589): MOVE LIT-THISMAPSET         TO CCARD-NEXT-MAPSET
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsLiterals.litThismapset);
    // COB(3590): MOVE LIT-THISMAP            TO CCARD-NEXT-MAP
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsLiterals.litThismap);
    // COB(3592): EXEC CICS SEND MAP(CCARD-NEXT-MAP)
    // COB(3592):                MAPSET(CCARD-NEXT-MAPSET)
    // COB(3592):                FROM(CACTUPAO)
    // COB(3592):                CURSOR
    // COB(3592):                ERASE
    // COB(3592):                FREEKB
    // COB(3592):                RESP(WS-RESP-CD)
    // COB(3592): END-EXEC
    supernaut
        .sendMap(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.getValue()) //
        .mapset(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.getValue()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .from(ws.coactup.cactupao) //
        .freekb() //
        .erase() //
        .cursor() //
        .exec();
  }

  /***
   */
  protected void _9000ReadAcct() {
    // COB(3608): INITIALIZE ACUP-OLD-DETAILS
    ws.wsThisProgcommarea.acupOldDetails.initialize();
    // COB(3610): SET  WS-NO-INFO-MESSAGE      TO TRUE
    ws.wsMiscStorage.setWsNoInfoMessage(true);
    // COB(3612): MOVE CC-ACCT-ID              TO ACUP-OLD-ACCT-ID
    // COB(3612):                                 WS-CARD-RID-ACCT-ID
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldAcctId.setValue(
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
    ws.wsMiscStorage.wsXrefRid.wsCardRidAcctId.setValue(
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
    // COB(3615): PERFORM 9200-GETCARDXREF-BYACCT
    // COB(3615):    THRU 9200-GETCARDXREF-BYACCT-EXIT
    _9200GetcardxrefByacct();
    // COB(3618): IF FLG-ACCTFILTER-NOT-OK
    if (ws.wsMiscStorage.flgAcctfilterNotOk()) {
      // COB(3619): GO TO 9000-READ-ACCT-EXIT
      return;
      // COB(3620): END-IF
    }
    // COB(3622): PERFORM 9300-GETACCTDATA-BYACCT
    // COB(3622):    THRU 9300-GETACCTDATA-BYACCT-EXIT
    _9300GetacctdataByacct();
    // COB(3625): IF DID-NOT-FIND-ACCT-IN-ACCTDAT
    if (ws.wsMiscStorage.didNotFindAcctInAcctdat()) {
      // COB(3626): GO TO 9000-READ-ACCT-EXIT
      return;
      // COB(3627): END-IF
    }
    // COB(3629): MOVE CDEMO-CUST-ID TO WS-CARD-RID-CUST-ID
    ws.wsMiscStorage.wsXrefRid.wsCardRidCustId.setValue(
        ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustId);
    // COB(3631): PERFORM 9400-GETCUSTDATA-BYCUST
    // COB(3631):    THRU 9400-GETCUSTDATA-BYCUST-EXIT
    _9400GetcustdataBycust();
    // COB(3634): IF DID-NOT-FIND-CUST-IN-CUSTDAT
    if (ws.wsMiscStorage.didNotFindCustInCustdat()) {
      // COB(3635): GO TO 9000-READ-ACCT-EXIT
      return;
      // COB(3636): END-IF
    }
    // COB(3640): PERFORM 9500-STORE-FETCHED-DATA
    // COB(3640):    THRU 9500-STORE-FETCHED-DATA-EXIT
    //
    //
    //
    _9500StoreFetchedData();
  }

  protected void _9200GetcardxrefByacct() {
    // COB(3652): EXEC CICS READ
    // COB(3652):      DATASET   (LIT-CARDXREFNAME-ACCT-PATH)
    // COB(3652):      RIDFLD    (WS-CARD-RID-ACCT-ID-X)
    // COB(3652):      KEYLENGTH (LENGTH OF WS-CARD-RID-ACCT-ID-X)
    // COB(3652):      INTO      (CARD-XREF-RECORD)
    // COB(3652):      LENGTH    (LENGTH OF CARD-XREF-RECORD)
    // COB(3652):      RESP      (WS-RESP-CD)
    // COB(3652):      RESP2     (WS-REAS-CD)
    // COB(3652): END-EXEC
    //
    //     Read the Card file. Access via alternate index ACCTID
    //
    supernaut
        .read(ws.wsLiterals.litCardxrefnameAcctPath.getValue()) //
        .length(ws.cvact03y.cardXrefRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvact03y.cardXrefRecord) //
        .ridfld(ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX) //
        .keylength(ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX.length()) //
        .exec();
    // COB(3662): EVALUATE WS-RESP-CD
    switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
        // COB(3663): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(3664): MOVE XREF-CUST-ID               TO CDEMO-CUST-ID
        ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustId.setValue(
            ws.cvact03y.cardXrefRecord.xrefCustId);
        // COB(3665): MOVE XREF-CARD-NUM              TO CDEMO-CARD-NUM
        ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.setValue(
            ws.cvact03y.cardXrefRecord.xrefCardNum);
        // COB(3666): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(3667): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(3668): SET FLG-ACCTFILTER-NOT-OK       TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(3669): IF WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(3670): MOVE WS-RESP-CD               TO ERROR-RESP
          ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
          // COB(3671): MOVE WS-REAS-CD               TO ERROR-RESP2
          ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
          // COB(3672): STRING
          // COB(3672): 'Account:'
          // COB(3672):  WS-CARD-RID-ACCT-ID-X
          // COB(3672): ' not found in'
          // COB(3672): ' Cross ref file.  Resp:'
          // COB(3672): ERROR-RESP
          // COB(3672): ' Reas:'
          // COB(3672): ERROR-RESP2
          // COB(3672): DELIMITED BY SIZE
          // COB(3672): INTO WS-RETURN-MSG
          // COB(3672): END-STRING
          ws.wsMiscStorage.wsReturnMsg.concat(
              "Account:",
              ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX,
              " not found in",
              " Cross ref file.  Resp:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp,
              " Reas:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp2);
          // COB(3682): END-STRING
          // COB(3683): END-IF
        }
        // COB(3684): WHEN OTHER
        break;
      default:
        // COB(3685): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(3686): SET FLG-ACCTFILTER-NOT-OK                TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(3687): MOVE 'READ'                     TO ERROR-OPNAME
        ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
        // COB(3688): MOVE LIT-CARDXREFNAME-ACCT-PATH TO ERROR-FILE
        ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(
            ws.wsLiterals.litCardxrefnameAcctPath);
        // COB(3689): MOVE WS-RESP-CD                 TO ERROR-RESP
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(3690): MOVE WS-REAS-CD                 TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(3691): MOVE WS-FILE-ERROR-MESSAGE      TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
        // COB(3694): END-EVALUATE
        //                                               WS-LONG-MSG
        //           PERFORM SEND-LONG-TEXT
    }
  }

  protected void _9300GetacctdataByacct() {
    // COB(3701): EXEC CICS READ
    // COB(3701):      DATASET   (LIT-ACCTFILENAME)
    // COB(3701):      RIDFLD    (WS-CARD-RID-ACCT-ID-X)
    // COB(3701):      KEYLENGTH (LENGTH OF WS-CARD-RID-ACCT-ID-X)
    // COB(3701):      INTO      (ACCOUNT-RECORD)
    // COB(3701):      LENGTH    (LENGTH OF ACCOUNT-RECORD)
    // COB(3701):      RESP      (WS-RESP-CD)
    // COB(3701):      RESP2     (WS-REAS-CD)
    // COB(3701): END-EXEC
    supernaut
        .read(ws.wsLiterals.litAcctfilename.getValue()) //
        .length(ws.cvact01y.accountRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvact01y.accountRecord) //
        .ridfld(ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX) //
        .keylength(ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX.length()) //
        .exec();
    // COB(3711): EVALUATE WS-RESP-CD
    switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
        // COB(3712): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(3713): SET FOUND-ACCT-IN-MASTER        TO TRUE
        ws.wsMiscStorage.wsFileReadFlags.setFoundAcctInMaster(true);
        // COB(3714): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(3715): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(3716): SET FLG-ACCTFILTER-NOT-OK       TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(3718): IF WS-RETURN-MSG-OFF
        //            SET DID-NOT-FIND-ACCT-IN-ACCTDAT TO TRUE
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(3719): MOVE WS-RESP-CD               TO ERROR-RESP
          ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
          // COB(3720): MOVE WS-REAS-CD               TO ERROR-RESP2
          ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
          // COB(3721): STRING
          // COB(3721): 'Account:'
          // COB(3721):  WS-CARD-RID-ACCT-ID-X
          // COB(3721): ' not found in'
          // COB(3721): ' Acct Master file.Resp:'
          // COB(3721): ERROR-RESP
          // COB(3721): ' Reas:'
          // COB(3721): ERROR-RESP2
          // COB(3721): DELIMITED BY SIZE
          // COB(3721): INTO WS-RETURN-MSG
          // COB(3721): END-STRING
          ws.wsMiscStorage.wsReturnMsg.concat(
              "Account:",
              ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX,
              " not found in",
              " Acct Master file.Resp:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp,
              " Reas:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp2);
          // COB(3731): END-STRING
          // COB(3732): END-IF
        }
        // COB(3734): WHEN OTHER
        break;
      default:
        // COB(3735): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(3736): SET FLG-ACCTFILTER-NOT-OK                TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(3737): MOVE 'READ'                     TO ERROR-OPNAME
        ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
        // COB(3738): MOVE LIT-ACCTFILENAME           TO ERROR-FILE
        ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(ws.wsLiterals.litAcctfilename);
        // COB(3739): MOVE WS-RESP-CD                 TO ERROR-RESP
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(3740): MOVE WS-REAS-CD                 TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(3741): MOVE WS-FILE-ERROR-MESSAGE      TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
        // COB(3744): END-EVALUATE
        //                                               WS-LONG-MSG
        //            PERFORM SEND-LONG-TEXT
    }
  }

  /***/
  protected void _9400GetcustdataBycust() {
    // COB(3751): EXEC CICS READ
    // COB(3751):      DATASET   (LIT-CUSTFILENAME)
    // COB(3751):      RIDFLD    (WS-CARD-RID-CUST-ID-X)
    // COB(3751):      KEYLENGTH (LENGTH OF WS-CARD-RID-CUST-ID-X)
    // COB(3751):      INTO      (CUSTOMER-RECORD)
    // COB(3751):      LENGTH    (LENGTH OF CUSTOMER-RECORD)
    // COB(3751):      RESP      (WS-RESP-CD)
    // COB(3751):      RESP2     (WS-REAS-CD)
    // COB(3751): END-EXEC
    supernaut
        .read(ws.wsLiterals.litCustfilename.getValue()) //
        .length(ws.cvcus01y.customerRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvcus01y.customerRecord) //
        .ridfld(ws.wsMiscStorage.wsXrefRid.wsCardRidCustIdX) //
        .keylength(ws.wsMiscStorage.wsXrefRid.wsCardRidCustIdX.length()) //
        .exec();
    // COB(3761): EVALUATE WS-RESP-CD
    switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
        // COB(3762): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(3763): SET FOUND-CUST-IN-MASTER        TO TRUE
        ws.wsMiscStorage.wsFileReadFlags.setFoundCustInMaster(true);
        // COB(3764): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(3765): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(3766): SET FLG-CUSTFILTER-NOT-OK       TO TRUE
        ws.wsMiscStorage.setFlgCustfilterNotOk(true);
        // COB(3768): MOVE WS-RESP-CD               TO ERROR-RESP
        //            SET DID-NOT-FIND-CUST-IN-CUSTDAT TO TRUE
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(3769): MOVE WS-REAS-CD               TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(3770): IF WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(3771): STRING
          // COB(3771): 'CustId:'
          // COB(3771):  WS-CARD-RID-CUST-ID-X
          // COB(3771): ' not found'
          // COB(3771): ' in customer master.Resp: '
          // COB(3771): ERROR-RESP
          // COB(3771): ' REAS:'
          // COB(3771): ERROR-RESP2
          // COB(3771): DELIMITED BY SIZE
          // COB(3771): INTO WS-RETURN-MSG
          // COB(3771): END-STRING
          ws.wsMiscStorage.wsReturnMsg.concat(
              "CustId:",
              ws.wsMiscStorage.wsXrefRid.wsCardRidCustIdX,
              " not found",
              " in customer master.Resp: ",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp,
              " REAS:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp2);
          // COB(3781): END-STRING
          // COB(3782): END-IF
        }
        // COB(3783): WHEN OTHER
        break;
      default:
        // COB(3784): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(3785): SET FLG-CUSTFILTER-NOT-OK                TO TRUE
        ws.wsMiscStorage.setFlgCustfilterNotOk(true);
        // COB(3786): MOVE 'READ'                     TO ERROR-OPNAME
        ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
        // COB(3787): MOVE LIT-CUSTFILENAME           TO ERROR-FILE
        ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(ws.wsLiterals.litCustfilename);
        // COB(3788): MOVE WS-RESP-CD                 TO ERROR-RESP
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(3789): MOVE WS-REAS-CD                 TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(3790): MOVE WS-FILE-ERROR-MESSAGE      TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
        // COB(3793): END-EVALUATE
        //                                               WS-LONG-MSG
        //            PERFORM SEND-LONG-TEXT
    }
  }

  /***/
  protected void _9500StoreFetchedData() {
    // COB(3803): MOVE ACCT-ID                   TO CDEMO-ACCT-ID
    //
    //     Store Context in Commarea
    //
    ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.setValue(
        ws.cvact01y.accountRecord.acctId);
    // COB(3804): MOVE CUST-ID                   TO CDEMO-CUST-ID
    ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustId.setValue(
        ws.cvcus01y.customerRecord.custId);
    // COB(3805): MOVE CUST-FIRST-NAME           TO CDEMO-CUST-FNAME
    ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustFname.setValue(
        ws.cvcus01y.customerRecord.custFirstName);
    // COB(3806): MOVE CUST-MIDDLE-NAME          TO CDEMO-CUST-MNAME
    ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustMname.setValue(
        ws.cvcus01y.customerRecord.custMiddleName);
    // COB(3807): MOVE CUST-LAST-NAME            TO CDEMO-CUST-LNAME
    ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustLname.setValue(
        ws.cvcus01y.customerRecord.custLastName);
    // COB(3808): MOVE ACCT-ACTIVE-STATUS        TO CDEMO-ACCT-STATUS
    ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctStatus.setValue(
        ws.cvact01y.accountRecord.acctActiveStatus);
    // COB(3809): MOVE XREF-CARD-NUM             TO CDEMO-CARD-NUM
    ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.setValue(
        ws.cvact03y.cardXrefRecord.xrefCardNum);
    // COB(3811): INITIALIZE ACUP-OLD-DETAILS
    ws.wsThisProgcommarea.acupOldDetails.initialize();
    // COB(3815): MOVE ACCT-ID                  TO ACUP-OLD-ACCT-ID
    // *****************************************************************
    //     Account Master data
    // *****************************************************************
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldAcctId.setValue(
        ws.cvact01y.accountRecord.acctId);
    // COB(3817): MOVE ACCT-ACTIVE-STATUS       TO ACUP-OLD-ACTIVE-STATUS
    //  Active Status
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldActiveStatus.setValue(
        ws.cvact01y.accountRecord.acctActiveStatus);
    // COB(3819): MOVE ACCT-CURR-BAL            TO ACUP-OLD-CURR-BAL-N
    //  Current Balance
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrBalN.setValue(
        ws.cvact01y.accountRecord.acctCurrBal);
    // COB(3821): MOVE ACCT-CREDIT-LIMIT        TO ACUP-OLD-CREDIT-LIMIT-N
    //  Credit Limit
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCreditLimitN.setValue(
        ws.cvact01y.accountRecord.acctCreditLimit);
    // COB(3823): MOVE ACCT-CASH-CREDIT-LIMIT   TO ACUP-OLD-CASH-CREDIT-LIMIT-N
    //  Cash Limit
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCashCreditLimitN.setValue(
        ws.cvact01y.accountRecord.acctCashCreditLimit);
    // COB(3825): MOVE ACCT-CURR-CYC-CREDIT     TO ACUP-OLD-CURR-CYC-CREDIT-N
    //  Current Cycle Credit
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrCycCreditN.setValue(
        ws.cvact01y.accountRecord.acctCurrCycCredit);
    // COB(3827): MOVE ACCT-CURR-CYC-DEBIT      TO ACUP-OLD-CURR-CYC-DEBIT-N
    //  Current Cycle Debit
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrCycDebitN.setValue(
        ws.cvact01y.accountRecord.acctCurrCycDebit);
    // COB(3830): MOVE ACCT-OPEN-DATE(1:4)      TO ACUP-OLD-OPEN-YEAR
    //  Open date
    //     MOVE ACCT-OPEN-DATE           TO ACUP-OLD-OPEN-DATE
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldOpenDateParts.acupOldOpenYear
        .setValue(ws.cvact01y.accountRecord.acctOpenDate, 0, 4);
    // COB(3831): MOVE ACCT-OPEN-DATE(6:2)      TO ACUP-OLD-OPEN-MON
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldOpenDateParts.acupOldOpenMon
        .setValue(ws.cvact01y.accountRecord.acctOpenDate, 5, 0, 2);
    // COB(3832): MOVE ACCT-OPEN-DATE(9:2)      TO ACUP-OLD-OPEN-DAY
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldOpenDateParts.acupOldOpenDay
        .setValue(ws.cvact01y.accountRecord.acctOpenDate, 8, 0, 2);
    // COB(3835): MOVE ACCT-EXPIRAION-DATE(1:4) TO ACUP-OLD-EXP-YEAR
    //  Expiry date
    //     MOVE ACCT-EXPIRAION-DATE      TO ACUP-OLD-EXPIRAION-DATE
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldExpiraionDateParts.acupOldExpYear
        .setValue(ws.cvact01y.accountRecord.acctExpiraionDate, 0, 4);
    // COB(3836): MOVE ACCT-EXPIRAION-DATE(6:2) TO ACUP-OLD-EXP-MON
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldExpiraionDateParts.acupOldExpMon
        .setValue(ws.cvact01y.accountRecord.acctExpiraionDate, 5, 0, 2);
    // COB(3837): MOVE ACCT-EXPIRAION-DATE(9:2) TO ACUP-OLD-EXP-DAY
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldExpiraionDateParts.acupOldExpDay
        .setValue(ws.cvact01y.accountRecord.acctExpiraionDate, 8, 0, 2);
    // COB(3841): MOVE ACCT-REISSUE-DATE(1:4)   TO ACUP-OLD-REISSUE-YEAR
    //
    //  Reissue date
    //     MOVE ACCT-REISSUE-DATE        TO ACUP-OLD-REISSUE-DATE
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldReissueDateParts.acupOldReissueYear
        .setValue(ws.cvact01y.accountRecord.acctReissueDate, 0, 4);
    // COB(3842): MOVE ACCT-REISSUE-DATE(6:2)   TO ACUP-OLD-REISSUE-MON
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldReissueDateParts.acupOldReissueMon
        .setValue(ws.cvact01y.accountRecord.acctReissueDate, 5, 0, 2);
    // COB(3843): MOVE ACCT-REISSUE-DATE(9:2)   TO ACUP-OLD-REISSUE-DAY
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldReissueDateParts.acupOldReissueDay
        .setValue(ws.cvact01y.accountRecord.acctReissueDate, 8, 0, 2);
    // COB(3845): MOVE ACCT-GROUP-ID            TO ACUP-OLD-GROUP-ID
    //  Account Group
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldGroupId.setValue(
        ws.cvact01y.accountRecord.acctGroupId);
    // COB(3850): MOVE CUST-ID                  TO ACUP-OLD-CUST-ID
    // *****************************************************************
    //     Customer Master data
    // *****************************************************************
    // Customer Id (actually not editable)
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustId.setValue(
        ws.cvcus01y.customerRecord.custId);
    // COB(3852): MOVE CUST-SSN                 TO ACUP-OLD-CUST-SSN
    // Social Security Number
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustSsn.setValue(
        ws.cvcus01y.customerRecord.custSsn);
    // COB(3855): MOVE CUST-DOB-YYYY-MM-DD(1:4) TO ACUP-OLD-CUST-DOB-YEAR
    // Date of birth
    //     MOVE CUST-DOB-YYYY-MM-DD      TO ACUP-OLD-CUST-DOB-YYYY-MM-DD
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustDobParts.acupOldCustDobYear
        .setValue(ws.cvcus01y.customerRecord.custDobYyyyMmDd, 0, 4);
    // COB(3856): MOVE CUST-DOB-YYYY-MM-DD(6:2) TO ACUP-OLD-CUST-DOB-MON
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustDobParts.acupOldCustDobMon
        .setValue(ws.cvcus01y.customerRecord.custDobYyyyMmDd, 5, 0, 2);
    // COB(3857): MOVE CUST-DOB-YYYY-MM-DD(9:2) TO ACUP-OLD-CUST-DOB-DAY
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustDobParts.acupOldCustDobDay
        .setValue(ws.cvcus01y.customerRecord.custDobYyyyMmDd, 8, 0, 2);
    // COB(3859): MOVE CUST-FICO-CREDIT-SCORE   TO ACUP-OLD-CUST-FICO-SCORE
    // FICO
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustFicoScore.setValue(
        ws.cvcus01y.customerRecord.custFicoCreditScore);
    // COB(3861): MOVE CUST-FIRST-NAME          TO ACUP-OLD-CUST-FIRST-NAME
    // First Name
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustFirstName.setValue(
        ws.cvcus01y.customerRecord.custFirstName);
    // COB(3863): MOVE CUST-MIDDLE-NAME         TO ACUP-OLD-CUST-MIDDLE-NAME
    // Middle Name
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustMiddleName.setValue(
        ws.cvcus01y.customerRecord.custMiddleName);
    // COB(3865): MOVE CUST-LAST-NAME           TO ACUP-OLD-CUST-LAST-NAME
    // Last Name
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustLastName.setValue(
        ws.cvcus01y.customerRecord.custLastName);
    // COB(3867): MOVE CUST-ADDR-LINE-1         TO ACUP-OLD-CUST-ADDR-LINE-1
    // Address
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine1.setValue(
        ws.cvcus01y.customerRecord.custAddrLine1);
    // COB(3868): MOVE CUST-ADDR-LINE-2         TO ACUP-OLD-CUST-ADDR-LINE-2
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine2.setValue(
        ws.cvcus01y.customerRecord.custAddrLine2);
    // COB(3869): MOVE CUST-ADDR-LINE-3         TO ACUP-OLD-CUST-ADDR-LINE-3
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine3.setValue(
        ws.cvcus01y.customerRecord.custAddrLine3);
    // COB(3870): MOVE CUST-ADDR-STATE-CD       TO ACUP-OLD-CUST-ADDR-STATE-CD
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrStateCd.setValue(
        ws.cvcus01y.customerRecord.custAddrStateCd);
    // COB(3871): MOVE CUST-ADDR-COUNTRY-CD     TO
    // COB(3871):                                ACUP-OLD-CUST-ADDR-COUNTRY-CD
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrCountryCd.setValue(
        ws.cvcus01y.customerRecord.custAddrCountryCd);
    // COB(3873): MOVE CUST-ADDR-ZIP            TO ACUP-OLD-CUST-ADDR-ZIP
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrZip.setValue(
        ws.cvcus01y.customerRecord.custAddrZip);
    // COB(3874): MOVE CUST-PHONE-NUM-1         TO ACUP-OLD-CUST-PHONE-NUM-1
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum1.setValue(
        ws.cvcus01y.customerRecord.custPhoneNum1);
    // COB(3875): MOVE CUST-PHONE-NUM-2         TO ACUP-OLD-CUST-PHONE-NUM-2
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum2.setValue(
        ws.cvcus01y.customerRecord.custPhoneNum2);
    // COB(3877): MOVE CUST-GOVT-ISSUED-ID      TO ACUP-OLD-CUST-GOVT-ISSUED-ID
    // Government Id
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustGovtIssuedId.setValue(
        ws.cvcus01y.customerRecord.custGovtIssuedId);
    // COB(3879): MOVE CUST-EFT-ACCOUNT-ID      TO ACUP-OLD-CUST-EFT-ACCOUNT-ID
    // EFT Code
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustEftAccountId.setValue(
        ws.cvcus01y.customerRecord.custEftAccountId);
    // COB(3881): MOVE CUST-PRI-CARD-HOLDER-IND TO ACUP-OLD-CUST-PRI-HOLDER-IND
    // Primary Holder Indicator
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPriHolderInd.setValue(
        ws.cvcus01y.customerRecord.custPriCardHolderInd);
  }

  protected Flow _9600WriteProcessing() {
    // COB(3890): MOVE CC-ACCT-ID              TO WS-CARD-RID-ACCT-ID
    //
    //     Read the account file for update
    //
    ws.wsMiscStorage.wsXrefRid.wsCardRidAcctId.setValue(
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
    // COB(3892): EXEC CICS READ
    // COB(3892):      FILE      (LIT-ACCTFILENAME)
    // COB(3892):      UPDATE
    // COB(3892):      RIDFLD    (WS-CARD-RID-ACCT-ID-X)
    // COB(3892):      KEYLENGTH (LENGTH OF WS-CARD-RID-ACCT-ID-X)
    // COB(3892):      INTO      (ACCOUNT-RECORD)
    // COB(3892):      LENGTH    (LENGTH OF ACCOUNT-RECORD)
    // COB(3892):      RESP      (WS-RESP-CD)
    // COB(3892):      RESP2     (WS-REAS-CD)
    // COB(3892): END-EXEC
    supernaut
        .read(ws.wsLiterals.litAcctfilename.getValue()) //
        .update() //
        .length(ws.cvact01y.accountRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvact01y.accountRecord) //
        .ridfld(ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX) //
        .keylength(ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX.length()) //
        .exec();
    // COB(3905): IF WS-RESP-CD EQUAL TO DFHRESP(NORMAL)
    // ****************************************************************
    //     Could we lock the account record ?
    // ****************************************************************
    if (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.equals(Dfhresp.NORMAL)) {
      // COB(3906): CONTINUE
      // do nothing
      // COB(3907): ELSE
    } else {
      // COB(3908): SET INPUT-ERROR                    TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(3909): IF  WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(3910): SET COULD-NOT-LOCK-ACCT-FOR-UPDATE  TO TRUE
        ws.wsMiscStorage.setCouldNotLockAcctForUpdate(true);
        // COB(3911): END-IF
      }
      // COB(3912): GO TO 9600-WRITE-PROCESSING-EXIT
      return Flow._9600WriteProcessingExit;
      // COB(3913): END-IF
    }
    // COB(3917): MOVE CDEMO-CUST-ID                   TO WS-CARD-RID-CUST-ID
    //
    //     Read the customer file for update
    //
    ws.wsMiscStorage.wsXrefRid.wsCardRidCustId.setValue(
        ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustId);
    // COB(3919): EXEC CICS READ
    // COB(3919):      FILE      (LIT-CUSTFILENAME)
    // COB(3919):      UPDATE
    // COB(3919):      RIDFLD    (WS-CARD-RID-CUST-ID-X)
    // COB(3919):      KEYLENGTH (LENGTH OF WS-CARD-RID-CUST-ID-X)
    // COB(3919):      INTO      (CUSTOMER-RECORD)
    // COB(3919):      LENGTH    (LENGTH OF CUSTOMER-RECORD)
    // COB(3919):      RESP      (WS-RESP-CD)
    // COB(3919):      RESP2     (WS-REAS-CD)
    // COB(3919): END-EXEC
    supernaut
        .read(ws.wsLiterals.litCustfilename.getValue()) //
        .update() //
        .length(ws.cvcus01y.customerRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvcus01y.customerRecord) //
        .ridfld(ws.wsMiscStorage.wsXrefRid.wsCardRidCustIdX) //
        .keylength(ws.wsMiscStorage.wsXrefRid.wsCardRidCustIdX.length()) //
        .exec();
    // COB(3932): IF WS-RESP-CD EQUAL TO DFHRESP(NORMAL)
    // ****************************************************************
    //     Could we lock the customer record ?
    // ****************************************************************
    if (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.equals(Dfhresp.NORMAL)) {
      // COB(3933): CONTINUE
      // do nothing
      // COB(3934): ELSE
    } else {
      // COB(3935): SET INPUT-ERROR                    TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(3936): IF  WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(3937): SET COULD-NOT-LOCK-CUST-FOR-UPDATE  TO TRUE
        ws.wsMiscStorage.setCouldNotLockCustForUpdate(true);
        // COB(3938): END-IF
      }
      // COB(3939): GO TO 9600-WRITE-PROCESSING-EXIT
      return Flow._9600WriteProcessingExit;
      // COB(3940): END-IF
    }
    // COB(3945): PERFORM 9700-CHECK-CHANGE-IN-REC
    // COB(3945):    THRU 9700-CHECK-CHANGE-IN-REC-EXIT
    //
    // ****************************************************************
    //     Did someone change the record while we were out ?
    // ****************************************************************
    rcNext = Flow._9700CheckChangeInRec;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case _9700CheckChangeInRec:
          rcNext = _9700CheckChangeInRec();
          break;
        case _9600WriteProcessingExit:
          _9600WriteProcessingExit();
          rcNext = Flow._9700CheckChangeInRec;
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    // COB(3948): IF DATA-WAS-CHANGED-BEFORE-UPDATE
    if (ws.wsMiscStorage.dataWasChangedBeforeUpdate()) {
      // COB(3949): GO TO 9600-WRITE-PROCESSING-EXIT
      return Flow._9600WriteProcessingExit;
      // COB(3950): END-IF
    }
    // COB(3954): INITIALIZE ACCT-UPDATE-RECORD
    // ****************************************************************
    //  Prepare the update
    // ****************************************************************
    ws.wsMiscStorage.acctUpdateRecord.initialize();
    // COB(3958): MOVE ACUP-NEW-ACCT-ID         TO ACCT-UPDATE-ID
    // *****************************************************************
    //     Account Master data
    // *****************************************************************
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateId.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewAcctId);
    // COB(3960): MOVE ACUP-NEW-ACTIVE-STATUS   TO ACCT-UPDATE-ACTIVE-STATUS
    //  Active Status
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateActiveStatus.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewActiveStatus);
    // COB(3962): MOVE ACUP-NEW-CURR-BAL-N      TO ACCT-UPDATE-CURR-BAL
    //  Current Balance
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateCurrBal.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrBalN);
    // COB(3964): MOVE ACUP-NEW-CREDIT-LIMIT-N  TO ACCT-UPDATE-CREDIT-LIMIT
    //  Credit Limit
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateCreditLimit.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCreditLimitN);
    // COB(3966): MOVE ACUP-NEW-CASH-CREDIT-LIMIT-N
    // COB(3966):                            TO ACCT-UPDATE-CASH-CREDIT-LIMIT
    //  Cash Limit
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateCashCreditLimit.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCashCreditLimitN);
    // COB(3969): MOVE ACUP-NEW-CURR-CYC-CREDIT-N
    // COB(3969):                                TO ACCT-UPDATE-CURR-CYC-CREDIT
    //  Current Cycle Credit
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateCurrCycCredit.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrCycCreditN);
    // COB(3972): MOVE ACUP-NEW-CURR-CYC-DEBIT-N TO ACCT-UPDATE-CURR-CYC-DEBIT
    //  Current Cycle Debit
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateCurrCycDebit.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrCycDebitN);
    // COB(3974): STRING ACUP-NEW-OPEN-YEAR
    // COB(3974):        '-'
    // COB(3974):        ACUP-NEW-OPEN-MON
    // COB(3974):        '-'
    // COB(3974):        ACUP-NEW-OPEN-DAY
    // COB(3974): DELIMITED BY SIZE
    // COB(3974):                             INTO ACCT-UPDATE-OPEN-DATE
    //  Open date
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateOpenDate.concat(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenYear,
        "-",
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenMon,
        "-",
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenDay);
    // COB(3982): STRING ACUP-NEW-EXP-YEAR
    // COB(3982):        '-'
    // COB(3982):        ACUP-NEW-EXP-MON
    // COB(3982):        '-'
    // COB(3982):        ACUP-NEW-EXP-DAY
    // COB(3982): DELIMITED BY SIZE
    // COB(3982):                             INTO ACCT-UPDATE-EXPIRAION-DATE
    //  Expiry date
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateExpiraionDate.concat(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewExpiraionDateParts
            .acupNewExpYear,
        "-",
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewExpiraionDateParts
            .acupNewExpMon,
        "-",
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewExpiraionDateParts
            .acupNewExpDay);
    // COB(3991): MOVE ACCT-REISSUE-DATE        TO ACCT-UPDATE-REISSUE-DATE
    //
    //  Reissue date
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateReissueDate.setValue(
        ws.cvact01y.accountRecord.acctReissueDate);
    // COB(3992): STRING ACUP-NEW-REISSUE-YEAR
    // COB(3992):        '-'
    // COB(3992):        ACUP-NEW-REISSUE-MON
    // COB(3992):        '-'
    // COB(3992):        ACUP-NEW-REISSUE-DAY
    // COB(3992): DELIMITED BY SIZE
    // COB(3992):                             INTO ACCT-UPDATE-REISSUE-DATE
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateReissueDate.concat(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewReissueDateParts
            .acupNewReissueYear,
        "-",
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewReissueDateParts
            .acupNewReissueMon,
        "-",
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewReissueDateParts
            .acupNewReissueDay);
    // COB(4000): MOVE ACUP-NEW-GROUP-ID        TO ACCT-UPDATE-GROUP-ID
    //  Account Group
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateGroupId.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewGroupId);
    // COB(4005): INITIALIZE CUST-UPDATE-RECORD
    //
    // *****************************************************************
    //     Customer data
    // *****************************************************************
    ws.wsMiscStorage.custUpdateRecord.initialize();
    // COB(4007): MOVE  ACUP-NEW-CUST-ID        TO CUST-UPDATE-ID
    ws.wsMiscStorage.custUpdateRecord.custUpdateId.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustId);
    // COB(4008): MOVE  ACUP-NEW-CUST-FIRST-NAME
    // COB(4008):                         TO CUST-UPDATE-FIRST-NAME
    ws.wsMiscStorage.custUpdateRecord.custUpdateFirstName.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFirstName);
    // COB(4010): MOVE  ACUP-NEW-CUST-MIDDLE-NAME
    // COB(4010):                         TO CUST-UPDATE-MIDDLE-NAME
    ws.wsMiscStorage.custUpdateRecord.custUpdateMiddleName.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustMiddleName);
    // COB(4012): MOVE  ACUP-NEW-CUST-LAST-NAME TO CUST-UPDATE-LAST-NAME
    ws.wsMiscStorage.custUpdateRecord.custUpdateLastName.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustLastName);
    // COB(4013): MOVE  ACUP-NEW-CUST-ADDR-LINE-1
    // COB(4013):                         TO CUST-UPDATE-ADDR-LINE-1
    ws.wsMiscStorage.custUpdateRecord.custUpdateAddrLine1.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine1);
    // COB(4015): MOVE  ACUP-NEW-CUST-ADDR-LINE-2
    // COB(4015):                         TO CUST-UPDATE-ADDR-LINE-2
    ws.wsMiscStorage.custUpdateRecord.custUpdateAddrLine2.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine2);
    // COB(4017): MOVE  ACUP-NEW-CUST-ADDR-LINE-3
    // COB(4017):                         TO CUST-UPDATE-ADDR-LINE-3
    ws.wsMiscStorage.custUpdateRecord.custUpdateAddrLine3.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine3);
    // COB(4019): MOVE  ACUP-NEW-CUST-ADDR-STATE-CD
    // COB(4019):                         TO CUST-UPDATE-ADDR-STATE-CD
    ws.wsMiscStorage.custUpdateRecord.custUpdateAddrStateCd.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrStateCd);
    // COB(4021): MOVE  ACUP-NEW-CUST-ADDR-COUNTRY-CD
    // COB(4021):                         TO CUST-UPDATE-ADDR-COUNTRY-CD
    ws.wsMiscStorage.custUpdateRecord.custUpdateAddrCountryCd.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrCountryCd);
    // COB(4023): MOVE  ACUP-NEW-CUST-ADDR-ZIP  TO CUST-UPDATE-ADDR-ZIP
    ws.wsMiscStorage.custUpdateRecord.custUpdateAddrZip.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrZip);
    // COB(4025): STRING '(',
    // COB(4025):        ACUP-NEW-CUST-PHONE-NUM-1A,
    // COB(4025):        ')',
    // COB(4025):        ACUP-NEW-CUST-PHONE-NUM-1B,
    // COB(4025):        '-',
    // COB(4025):        ACUP-NEW-CUST-PHONE-NUM-1C
    // COB(4025): DELIMITED BY SIZE    INTO CUST-UPDATE-PHONE-NUM-1
    ws.wsMiscStorage.custUpdateRecord.custUpdatePhoneNum1.concat(
        "(",
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum1X
            .acupNewCustPhoneNum1a,
        ")",
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum1X
            .acupNewCustPhoneNum1b,
        "-",
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum1X
            .acupNewCustPhoneNum1c);
    // COB(4033): STRING '(',
    // COB(4033):        ACUP-NEW-CUST-PHONE-NUM-2A,
    // COB(4033):        ')',
    // COB(4033):        ACUP-NEW-CUST-PHONE-NUM-2B,
    // COB(4033):        '-',
    // COB(4033):        ACUP-NEW-CUST-PHONE-NUM-2C
    // COB(4033): DELIMITED BY SIZE    INTO CUST-UPDATE-PHONE-NUM-2
    ws.wsMiscStorage.custUpdateRecord.custUpdatePhoneNum2.concat(
        "(",
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum2X
            .acupNewCustPhoneNum2a,
        ")",
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum2X
            .acupNewCustPhoneNum2b,
        "-",
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum2X
            .acupNewCustPhoneNum2c);
    // COB(4042): MOVE  ACUP-NEW-CUST-SSN       TO CUST-UPDATE-SSN
    //
    //
    ws.wsMiscStorage.custUpdateRecord.custUpdateSsn.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsn);
    // COB(4043): MOVE  ACUP-NEW-CUST-GOVT-ISSUED-ID
    // COB(4043):                         TO CUST-UPDATE-GOVT-ISSUED-ID
    ws.wsMiscStorage.custUpdateRecord.custUpdateGovtIssuedId.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustGovtIssuedId);
    // COB(4045): STRING ACUP-NEW-CUST-DOB-YEAR
    // COB(4045):        '-'
    // COB(4045):        ACUP-NEW-CUST-DOB-MON
    // COB(4045):        '-'
    // COB(4045):        ACUP-NEW-CUST-DOB-DAY
    // COB(4045): DELIMITED BY SIZE           INTO CUST-UPDATE-DOB-YYYY-MM-DD
    ws.wsMiscStorage.custUpdateRecord.custUpdateDobYyyyMmDd.concat(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobYear,
        "-",
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobMon,
        "-",
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobDay);
    // COB(4052): MOVE ACUP-NEW-CUST-EFT-ACCOUNT-ID
    // COB(4052):                               TO CUST-UPDATE-EFT-ACCOUNT-ID
    ws.wsMiscStorage.custUpdateRecord.custUpdateEftAccountId.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustEftAccountId);
    // COB(4054): MOVE ACUP-NEW-CUST-PRI-HOLDER-IND
    // COB(4054):                               TO CUST-UPDATE-PRI-CARD-IND
    ws.wsMiscStorage.custUpdateRecord.custUpdatePriCardInd.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPriHolderInd);
    // COB(4056): MOVE ACUP-NEW-CUST-FICO-SCORE TO
    // COB(4056):                         CUST-UPDATE-FICO-CREDIT-SCORE
    ws.wsMiscStorage.custUpdateRecord.custUpdateFicoCreditScore.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFicoScore);
    // COB(4063): EXEC CICS
    // COB(4063):      REWRITE FILE(LIT-ACCTFILENAME)
    // COB(4063):              FROM(ACCT-UPDATE-RECORD)
    // COB(4063):              LENGTH(LENGTH OF ACCT-UPDATE-RECORD)
    // COB(4063):              RESP      (WS-RESP-CD)
    // COB(4063):              RESP2     (WS-REAS-CD)
    // COB(4063): END-EXEC.
    // ****************************************************************
    //  Update account *
    // ****************************************************************
    //
    //
    supernaut
        .rewrite(ws.wsLiterals.litAcctfilename.getValue()) //
        .length(ws.wsMiscStorage.acctUpdateRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .from(ws.wsMiscStorage.acctUpdateRecord) //
        .exec();
    // COB(4074): IF WS-RESP-CD EQUAL TO DFHRESP(NORMAL)
    //
    // ****************************************************************
    //  Did account update succeed ?  *
    // ****************************************************************
    if (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.equals(Dfhresp.NORMAL)) {
      // COB(4075): CONTINUE
      // do nothing
      // COB(4076): ELSE
    } else {
      // COB(4077): SET LOCKED-BUT-UPDATE-FAILED    TO TRUE
      ws.wsMiscStorage.setLockedButUpdateFailed(true);
      // COB(4078): GO TO 9600-WRITE-PROCESSING-EXIT
      return Flow._9600WriteProcessingExit;
      // COB(4079): END-IF
    }
    // COB(4083): EXEC CICS
    // COB(4083):              REWRITE FILE(LIT-CUSTFILENAME)
    // COB(4083):              FROM(CUST-UPDATE-RECORD)
    // COB(4083):              LENGTH(LENGTH OF CUST-UPDATE-RECORD)
    // COB(4083):              RESP      (WS-RESP-CD)
    // COB(4083):              RESP2     (WS-REAS-CD)
    // COB(4083): END-EXEC.
    // ****************************************************************
    //  Update customer *
    // ****************************************************************
    supernaut
        .rewrite(ws.wsLiterals.litCustfilename.getValue()) //
        .length(ws.wsMiscStorage.custUpdateRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .from(ws.wsMiscStorage.custUpdateRecord) //
        .exec();
    // COB(4093): IF WS-RESP-CD EQUAL TO DFHRESP(NORMAL)
    // ****************************************************************
    //  Did customer update succeed ? *
    // ****************************************************************
    if (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.equals(Dfhresp.NORMAL)) {
      // COB(4094): CONTINUE
      // do nothing
      // COB(4095): ELSE
    } else {
      // COB(4096): SET LOCKED-BUT-UPDATE-FAILED    TO TRUE
      ws.wsMiscStorage.setLockedButUpdateFailed(true);
      // COB(4097): EXEC CICS
      // COB(4097):    SYNCPOINT ROLLBACK
      // COB(4097): END-EXEC
      try {
        supernaut
            .syncpoint() //
            .rollback() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(4100): GO TO 9600-WRITE-PROCESSING-EXIT
      return Flow._9600WriteProcessingExit;
      // COB(4101): END-IF
    }
    return Flow.Exit;
  }

  protected void _9600WriteProcessingExit() {
    // COB(4104): EXIT
    return;
  }

  /***/
  protected Flow _9700CheckChangeInRec() {
    // COB(4113): IF  ACCT-ACTIVE-STATUS      EQUAL ACUP-OLD-ACTIVE-STATUS
    // COB(4113):       * Current Balance
    // COB(4113): AND ACCT-CURR-BAL           EQUAL ACUP-OLD-CURR-BAL-N
    // COB(4113):       * Credit Limit
    // COB(4113): AND ACCT-CREDIT-LIMIT       EQUAL ACUP-OLD-CREDIT-LIMIT-N
    // COB(4113):       * Cash Limit
    // COB(4113): AND ACCT-CASH-CREDIT-LIMIT EQUAL ACUP-OLD-CASH-CREDIT-LIMIT-N
    // COB(4113):       * Current Cycle Credit
    // COB(4113): AND ACCT-CURR-CYC-CREDIT    EQUAL ACUP-OLD-CURR-CYC-CREDIT-N
    // COB(4113):       * Current Cycle Debit
    // COB(4113): AND ACCT-CURR-CYC-DEBIT     EQUAL ACUP-OLD-CURR-CYC-DEBIT-N
    // COB(4113):       * Open date
    // COB(4113): AND ACCT-OPEN-DATE(1:4)     EQUAL ACUP-OLD-OPEN-YEAR
    // COB(4113): AND ACCT-OPEN-DATE(6:2)     EQUAL ACUP-OLD-OPEN-MON
    // COB(4113): AND ACCT-OPEN-DATE(9:2)     EQUAL ACUP-OLD-OPEN-DAY
    // COB(4113):       * Expiry date
    // COB(4113): AND ACCT-EXPIRAION-DATE(1:4)EQUAL ACUP-OLD-EXP-YEAR
    // COB(4113): AND ACCT-EXPIRAION-DATE(6:2)EQUAL ACUP-OLD-EXP-MON
    // COB(4113): AND ACCT-EXPIRAION-DATE(9:2)EQUAL ACUP-OLD-EXP-DAY
    // COB(4113):       * Reissue date
    // COB(4113): AND ACCT-REISSUE-DATE(1:4)  EQUAL ACUP-OLD-REISSUE-YEAR
    // COB(4113): AND ACCT-REISSUE-DATE(6:2)  EQUAL ACUP-OLD-REISSUE-MON
    // COB(4113): AND ACCT-REISSUE-DATE(9:2)  EQUAL ACUP-OLD-REISSUE-DAY
    // COB(4113):       * Account Group
    // COB(4113): AND FUNCTION LOWER-CASE (ACCT-GROUP-ID)           EQUAL
    // COB(4113):     FUNCTION LOWER-CASE (ACUP-OLD-GROUP-ID)
    //
    //
    // *****************************************************************
    //     Account Master data
    // *****************************************************************
    if (ws.cvact01y.accountRecord.acctActiveStatus.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldActiveStatus)
        && ws.cvact01y.accountRecord.acctCurrBal.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrBalN)
        && ws.cvact01y.accountRecord.acctCreditLimit.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCreditLimitN)
        && ws.cvact01y.accountRecord.acctCashCreditLimit.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCashCreditLimitN)
        && ws.cvact01y.accountRecord.acctCurrCycCredit.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrCycCreditN)
        && ws.cvact01y.accountRecord.acctCurrCycDebit.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrCycDebitN)
        && ws.cvact01y
            .accountRecord
            .acctOpenDate
            .getString(0, 4)
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldAcctData
                    .acupOldOpenDateParts
                    .acupOldOpenYear)
        && ws.cvact01y
            .accountRecord
            .acctOpenDate
            .getString(5, 2)
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldAcctData
                    .acupOldOpenDateParts
                    .acupOldOpenMon)
        && ws.cvact01y
            .accountRecord
            .acctOpenDate
            .getString(8, 2)
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldAcctData
                    .acupOldOpenDateParts
                    .acupOldOpenDay)
        && ws.cvact01y
            .accountRecord
            .acctExpiraionDate
            .getString(0, 4)
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldAcctData
                    .acupOldExpiraionDateParts
                    .acupOldExpYear)
        && ws.cvact01y
            .accountRecord
            .acctExpiraionDate
            .getString(5, 2)
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldAcctData
                    .acupOldExpiraionDateParts
                    .acupOldExpMon)
        && ws.cvact01y
            .accountRecord
            .acctExpiraionDate
            .getString(8, 2)
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldAcctData
                    .acupOldExpiraionDateParts
                    .acupOldExpDay)
        && ws.cvact01y
            .accountRecord
            .acctReissueDate
            .getString(0, 4)
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldAcctData
                    .acupOldReissueDateParts
                    .acupOldReissueYear)
        && ws.cvact01y
            .accountRecord
            .acctReissueDate
            .getString(5, 2)
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldAcctData
                    .acupOldReissueDateParts
                    .acupOldReissueMon)
        && ws.cvact01y
            .accountRecord
            .acctReissueDate
            .getString(8, 2)
            .equals(
                ws.wsThisProgcommarea
                    .acupOldDetails
                    .acupOldAcctData
                    .acupOldReissueDateParts
                    .acupOldReissueDay)
        && ws.cvact01y
            .accountRecord
            .acctGroupId
            .toLowerCase()
            .equals(
                ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldGroupId
                    .toLowerCase())) {
      // COB(4139): CONTINUE
      // do nothing
      // COB(4140): ELSE
    } else {
      // COB(4141): SET DATA-WAS-CHANGED-BEFORE-UPDATE TO TRUE
      ws.wsMiscStorage.setDataWasChangedBeforeUpdate(true);
      // COB(4142): GO TO 9600-WRITE-PROCESSING-EXIT
      return Flow._9600WriteProcessingExit;
      // COB(4143): END-IF
    }
    // COB(4150): IF  FUNCTION UPPER-CASE (CUST-FIRST-NAME          ) EQUAL
    // COB(4150):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-FIRST-NAME )
    // COB(4150): AND FUNCTION UPPER-CASE (CUST-MIDDLE-NAME         ) EQUAL
    // COB(4150):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-MIDDLE-NAME)
    // COB(4150): AND FUNCTION UPPER-CASE (CUST-LAST-NAME           ) EQUAL
    // COB(4150):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-LAST-NAME  )
    // COB(4150): AND FUNCTION UPPER-CASE (CUST-ADDR-LINE-1         ) EQUAL
    // COB(4150):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-ADDR-LINE-1)
    // COB(4150): AND FUNCTION UPPER-CASE (CUST-ADDR-LINE-2         ) EQUAL
    // COB(4150):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-ADDR-LINE-2)
    // COB(4150): AND FUNCTION UPPER-CASE (CUST-ADDR-LINE-3         ) EQUAL
    // COB(4150):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-ADDR-LINE-3)
    // COB(4150): AND FUNCTION UPPER-CASE (CUST-ADDR-STATE-CD       ) EQUAL
    // COB(4150):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-ADDR-STATE-CD)
    // COB(4150): AND FUNCTION UPPER-CASE (CUST-ADDR-COUNTRY-CD     ) EQUAL
    // COB(4150):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-ADDR-COUNTRY-CD )
    // COB(4150): AND CUST-ADDR-ZIP           EQUAL ACUP-OLD-CUST-ADDR-ZIP
    // COB(4150): AND CUST-PHONE-NUM-1        EQUAL ACUP-OLD-CUST-PHONE-NUM-1
    // COB(4150): AND CUST-PHONE-NUM-2        EQUAL ACUP-OLD-CUST-PHONE-NUM-2
    // COB(4150): AND CUST-SSN                EQUAL ACUP-OLD-CUST-SSN
    // COB(4150): AND FUNCTION UPPER-CASE (CUST-GOVT-ISSUED-ID      ) EQUAL
    // COB(4150):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-GOVT-ISSUED-ID )
    // COB(4150): AND CUST-DOB-YYYY-MM-DD (1:4)                       EQUAL
    // COB(4150):     ACUP-OLD-CUST-DOB-YYYY-MM-DD (1:4)
    // COB(4150): AND CUST-DOB-YYYY-MM-DD (6:2)                       EQUAL
    // COB(4150):     ACUP-OLD-CUST-DOB-YYYY-MM-DD (5:2)
    // COB(4150): AND CUST-DOB-YYYY-MM-DD (9:2)                       EQUAL
    // COB(4150):     ACUP-OLD-CUST-DOB-YYYY-MM-DD (7:2)
    // COB(4150):       *
    // COB(4150): AND CUST-EFT-ACCOUNT-ID     EQUAL
    // COB(4150):                                  ACUP-OLD-CUST-EFT-ACCOUNT-ID
    // COB(4150): AND CUST-PRI-CARD-HOLDER-IND
    // COB(4150):                             EQUAL
    // COB(4150):                                  ACUP-OLD-CUST-PRI-HOLDER-IND
    // COB(4150): AND CUST-FICO-CREDIT-SCORE  EQUAL ACUP-OLD-CUST-FICO-SCORE
    //
    // *****************************************************************
    //     Customer  data - Split into 2 IFs for easier reading
    //     And maybe put logic to update only 1 file if only date
    //     pertaining to one of them is updated
    // *****************************************************************
    if (ws.cvcus01y
            .customerRecord
            .custFirstName
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustFirstName
                    .toUpperCase())
        && ws.cvcus01y
            .customerRecord
            .custMiddleName
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustMiddleName
                    .toUpperCase())
        && ws.cvcus01y
            .customerRecord
            .custLastName
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustLastName
                    .toUpperCase())
        && ws.cvcus01y
            .customerRecord
            .custAddrLine1
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine1
                    .toUpperCase())
        && ws.cvcus01y
            .customerRecord
            .custAddrLine2
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine2
                    .toUpperCase())
        && ws.cvcus01y
            .customerRecord
            .custAddrLine3
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine3
                    .toUpperCase())
        && ws.cvcus01y
            .customerRecord
            .custAddrStateCd
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrStateCd
                    .toUpperCase())
        && ws.cvcus01y
            .customerRecord
            .custAddrCountryCd
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrCountryCd
                    .toUpperCase())
        && ws.cvcus01y.customerRecord.custAddrZip.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrZip)
        && ws.cvcus01y.customerRecord.custPhoneNum1.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum1)
        && ws.cvcus01y.customerRecord.custPhoneNum2.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum2)
        && ws.cvcus01y.customerRecord.custSsn.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustSsn)
        && ws.cvcus01y
            .customerRecord
            .custGovtIssuedId
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustGovtIssuedId
                    .toUpperCase())
        && ws.cvcus01y
            .customerRecord
            .custDobYyyyMmDd
            .getString(0, 4)
            .equals(
                ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustDobYyyyMmDd
                    .getString(0, 4))
        && ws.cvcus01y
            .customerRecord
            .custDobYyyyMmDd
            .getString(5, 2)
            .equals(
                ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustDobYyyyMmDd
                    .getString(4, 2))
        && ws.cvcus01y
            .customerRecord
            .custDobYyyyMmDd
            .getString(8, 2)
            .equals(
                ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustDobYyyyMmDd
                    .getString(6, 2))
        && ws.cvcus01y.customerRecord.custEftAccountId.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustEftAccountId)
        && ws.cvcus01y.customerRecord.custPriCardHolderInd.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPriHolderInd)
        && ws.cvcus01y.customerRecord.custFicoCreditScore.equals(
            ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustFicoScore)) {
      // COB(4185): CONTINUE
      // do nothing
      // COB(4186): ELSE
    } else {
      // COB(4187): SET DATA-WAS-CHANGED-BEFORE-UPDATE TO TRUE
      ws.wsMiscStorage.setDataWasChangedBeforeUpdate(true);
      // COB(4188): GO TO 9600-WRITE-PROCESSING-EXIT
      return Flow._9600WriteProcessingExit;
      // COB(4189): END-IF
    }
    return Flow.Exit;
  }

  /******************************************************************
   * Copyright Amazon.com, Inc. or its affiliates.
   * All Rights Reserved.
   *
   * Licensed under the Apache License, Version 2.0 (the "License").
   * You may not use this file except in compliance with the License.
   * You may obtain a copy of the License at
   *
   *    http://www.apache.org/licenses/LICENSE-2.0
   *
   * Unless required by applicable law or agreed to in writing,
   * software distributed under the License is distributed on an
   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
   * either express or implied. See the License for the specific
   * language governing permissions and limitations under the License
   ******************************************************************/
  protected void yyyyStorePfkey() {
    // COB(21): EVALUATE TRUE
    // ****************************************************************
    //  Map AID to PFKey in COMMON Area
    // ****************************************************************
    // COB(22): WHEN EIBAID IS EQUAL TO DFHENTER
    if (dfheiblk.getEibaid().equals(Dfhaid.DFHENTER)) {
      // COB(23): SET CCARD-AID-ENTER TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidEnter(true);
      // COB(24): WHEN EIBAID IS EQUAL TO DFHCLEAR
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHCLEAR)) {
      // COB(25): SET CCARD-AID-CLEAR TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidClear(true);
      // COB(26): WHEN EIBAID IS EQUAL TO DFHPA1
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPA1)) {
      // COB(27): SET CCARD-AID-PA1  TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPa1(true);
      // COB(28): WHEN EIBAID IS EQUAL TO DFHPA2
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPA2)) {
      // COB(29): SET CCARD-AID-PA2  TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPa2(true);
      // COB(30): WHEN EIBAID IS EQUAL TO DFHPF1
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF1)) {
      // COB(31): SET CCARD-AID-PFK01 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk01(true);
      // COB(32): WHEN EIBAID IS EQUAL TO DFHPF2
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF2)) {
      // COB(33): SET CCARD-AID-PFK02 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk02(true);
      // COB(34): WHEN EIBAID IS EQUAL TO DFHPF3
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF3)) {
      // COB(35): SET CCARD-AID-PFK03 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk03(true);
      // COB(36): WHEN EIBAID IS EQUAL TO DFHPF4
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF4)) {
      // COB(37): SET CCARD-AID-PFK04 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk04(true);
      // COB(38): WHEN EIBAID IS EQUAL TO DFHPF5
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF5)) {
      // COB(39): SET CCARD-AID-PFK05 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk05(true);
      // COB(40): WHEN EIBAID IS EQUAL TO DFHPF6
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF6)) {
      // COB(41): SET CCARD-AID-PFK06 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk06(true);
      // COB(42): WHEN EIBAID IS EQUAL TO DFHPF7
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF7)) {
      // COB(43): SET CCARD-AID-PFK07 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk07(true);
      // COB(44): WHEN EIBAID IS EQUAL TO DFHPF8
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF8)) {
      // COB(45): SET CCARD-AID-PFK08 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk08(true);
      // COB(46): WHEN EIBAID IS EQUAL TO DFHPF9
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF9)) {
      // COB(47): SET CCARD-AID-PFK09 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk09(true);
      // COB(48): WHEN EIBAID IS EQUAL TO DFHPF10
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF10)) {
      // COB(49): SET CCARD-AID-PFK10 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk10(true);
      // COB(50): WHEN EIBAID IS EQUAL TO DFHPF11
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF11)) {
      // COB(51): SET CCARD-AID-PFK11 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk11(true);
      // COB(52): WHEN EIBAID IS EQUAL TO DFHPF12
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF12)) {
      // COB(53): SET CCARD-AID-PFK12 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk12(true);
      // COB(54): WHEN EIBAID IS EQUAL TO DFHPF13
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF13)) {
      // COB(55): SET CCARD-AID-PFK01 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk01(true);
      // COB(56): WHEN EIBAID IS EQUAL TO DFHPF14
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF14)) {
      // COB(57): SET CCARD-AID-PFK02 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk02(true);
      // COB(58): WHEN EIBAID IS EQUAL TO DFHPF15
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF15)) {
      // COB(59): SET CCARD-AID-PFK03 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk03(true);
      // COB(60): WHEN EIBAID IS EQUAL TO DFHPF16
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF16)) {
      // COB(61): SET CCARD-AID-PFK04 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk04(true);
      // COB(62): WHEN EIBAID IS EQUAL TO DFHPF17
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF17)) {
      // COB(63): SET CCARD-AID-PFK05 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk05(true);
      // COB(64): WHEN EIBAID IS EQUAL TO DFHPF18
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF18)) {
      // COB(65): SET CCARD-AID-PFK06 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk06(true);
      // COB(66): WHEN EIBAID IS EQUAL TO DFHPF19
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF19)) {
      // COB(67): SET CCARD-AID-PFK07 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk07(true);
      // COB(68): WHEN EIBAID IS EQUAL TO DFHPF20
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF20)) {
      // COB(69): SET CCARD-AID-PFK08 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk08(true);
      // COB(70): WHEN EIBAID IS EQUAL TO DFHPF21
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF21)) {
      // COB(71): SET CCARD-AID-PFK09 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk09(true);
      // COB(72): WHEN EIBAID IS EQUAL TO DFHPF22
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF22)) {
      // COB(73): SET CCARD-AID-PFK10 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk10(true);
      // COB(74): WHEN EIBAID IS EQUAL TO DFHPF23
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF23)) {
      // COB(75): SET CCARD-AID-PFK11 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk11(true);
      // COB(76): WHEN EIBAID IS EQUAL TO DFHPF24
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF24)) {
      // COB(77): SET CCARD-AID-PFK12 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk12(true);
      // COB(78): END-EVALUATE
    }
  }

  /***
   */
  protected void abendRoutine() {
    // COB(4203): IF ABEND-MSG EQUAL LOW-VALUES
    if (ws.csmsg02y.abendData.abendMsg.isLowValues()) {
      // COB(4204): MOVE 'UNEXPECTED ABEND OCCURRED.' TO ABEND-MSG
      ws.csmsg02y.abendData.abendMsg.setString("UNEXPECTED ABEND OCCURRED.");
      // COB(4205): END-IF
    }
    // COB(4207): MOVE LIT-THISPGM       TO ABEND-CULPRIT
    ws.csmsg02y.abendData.abendCulprit.setValue(ws.wsLiterals.litThispgm);
    // COB(4209): EXEC CICS SEND
    // COB(4209):                  FROM (ABEND-DATA)
    // COB(4209):                  LENGTH(LENGTH OF ABEND-DATA)
    // COB(4209):                  NOHANDLE
    // COB(4209):                  ERASE
    // COB(4209): END-EXEC
    supernaut
        .send() //
        .length(ws.csmsg02y.abendData.length()) //
        .nohandle() //
        .from(ws.csmsg02y.abendData) //
        .erase() //
        .exec();
    // COB(4216): EXEC CICS HANDLE ABEND
    // COB(4216):      CANCEL
    // COB(4216): END-EXEC
    supernaut
        .handleAbend() //
        .cancel() //
        .exec();
    // COB(4220): EXEC CICS ABEND
    // COB(4220):      ABCODE('9999')
    // COB(4220): END-EXEC
    supernaut
        .abend() //
        .abcode("9999") //
        .exec();
  }

  /******************************************************************
   *Procedure Division Copybook for DATE related code
   ******************************************************************
   *Date validation paragraph for reuse and hopefully not misuse
   *Accompanying WORKING Storage is CSUTLDTR
   ******************************************************************
   * ***  PERFORM EDIT-DATE-CCYYMMDD
   *         THRU EDIT-DATE-CCYYMMDD-EXIT
   *         to validate CCYYMMDD dates
   *      Reusable paras
   *      a) EDIT-YEAR-CCYY
   *      b) EDIT-MONTH
   *      c) EDIT-DAY
   *      d) EDIT-DATE-OF-BIRTH
   *      e) EDIT-DATE-OF-BIRTH
   ******************************************************************/
  protected void editDateCcyymmdd() {
    // COB(19): SET WS-EDIT-DATE-IS-INVALID   TO TRUE
    ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setWsEditDateIsInvalid(true);
  }

  /******************************************************************
   *Check for valid year and century
   ******************************************************************/
  protected Flow editYearCcyy() {
    // COB(27): SET FLG-YEAR-NOT-OK             TO TRUE
    ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgYearNotOk(true);
    // COB(30): IF WS-EDIT-DATE-CCYY            EQUAL LOW-VALUES
    // COB(30): OR WS-EDIT-DATE-CCYY            EQUAL SPACES
    //     Not supplied
    if (ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateCcyy.isLowValues()
        || ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateCcyy.isSpaces()) {
      // COB(32): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(33): SET  FLG-YEAR-BLANK          TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgYearBlank(true);
      // COB(34): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(35): STRING
        // COB(35):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(35):   ' : Year must be supplied.'
        // COB(35):   DELIMITED BY SIZE
        // COB(35):   INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " : Year must be supplied.");
        // COB(40): END-IF
      }
      // COB(42): GO TO EDIT-YEAR-CCYY-EXIT
      //        Intentional violation of structured programming norms
      return Flow.editYearCcyyExit;
      // COB(43): ELSE
    } else {
      // COB(44): CONTINUE
      // do nothing
      // COB(45): END-IF
    }
    // COB(48): IF WS-EDIT-DATE-CCYY            IS NOT NUMERIC
    //     Not numeric
    if (!ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateCcyy.isNumeric()) {
      // COB(49): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(50): SET  FLG-YEAR-NOT-OK         TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgYearNotOk(true);
      // COB(51): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(52): STRING
        // COB(52):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(52):   ' must be 4 digit number.'
        // COB(52):   DELIMITED BY SIZE
        // COB(52):   INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be 4 digit number.");
        // COB(57): END-IF
      }
      // COB(58): GO TO EDIT-YEAR-CCYY-EXIT
      return Flow.editYearCcyyExit;
      // COB(59): ELSE
    } else {
      // COB(60): CONTINUE
      // do nothing
      // COB(61): END-IF
    }
    // COB(70): IF THIS-CENTURY
    // COB(70): OR LAST-CENTURY
    // *****************************************************************
    //     Century not reasonable
    // *****************************************************************
    //   Not having learnt our lesson from history and Y2K
    //   And being unable to imagine COBOL in the 2100s
    //   We code only 19 and 20 as valid century values
    // *****************************************************************
    if (ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateCcyy.thisCentury()
        || ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateCcyy.lastCentury()) {
      // COB(72): CONTINUE
      // do nothing
      // COB(73): ELSE
    } else {
      // COB(74): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(75): SET  FLG-YEAR-NOT-OK         TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgYearNotOk(true);
      // COB(76): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(77): STRING
        // COB(77):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(77):   ' : Century is not valid.'
        // COB(77):   DELIMITED BY SIZE
        // COB(77):   INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " : Century is not valid.");
        // COB(82): END-IF
      }
      // COB(83): GO TO EDIT-YEAR-CCYY-EXIT
      return Flow.editYearCcyyExit;
      // COB(84): END-IF
    }
    // COB(86): SET FLG-YEAR-ISVALID            TO TRUE
    ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgYearIsvalid(true);
    return Flow.Exit;
  }

  protected void editYearCcyyExit() {
    // COB(89): EXIT
    return;
  }

  protected Flow editMonth() {
    // COB(92): SET FLG-MONTH-NOT-OK            TO TRUE
    ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgMonthNotOk(true);
    // COB(94): IF WS-EDIT-DATE-MM              EQUAL LOW-VALUES
    // COB(94): OR WS-EDIT-DATE-MM              EQUAL SPACES
    if (ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateMm.isLowValues()
        || ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateMm.isSpaces()) {
      // COB(96): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(97): SET  FLG-MONTH-BLANK         TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgMonthBlank(true);
      // COB(98): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(99): STRING
        // COB(99):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(99):   ' : Month must be supplied.'
        // COB(99):   DELIMITED BY SIZE
        // COB(99):   INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            " : Month must be supplied.");
        // COB(104): END-IF
      }
      // COB(105): GO TO EDIT-MONTH-EXIT
      return Flow.editMonthExit;
      // COB(106): ELSE
    } else {
      // COB(107): CONTINUE
      // do nothing
      // COB(108): END-IF
    }
    // COB(111): IF WS-VALID-MONTH
    //     Month not reasonable
    if (ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsValidMonth()) {
      // COB(112): CONTINUE
      // do nothing
      // COB(113): ELSE
    } else {
      // COB(114): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(115): SET  FLG-MONTH-NOT-OK        TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgMonthNotOk(true);
      // COB(116): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(117): STRING
        // COB(117):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(117):   ': Month must be a number between 1 and 12.'
        // COB(117):   DELIMITED BY SIZE
        // COB(117):   INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Month must be a number between 1 and 12.");
        // COB(122): END-IF
      }
      // COB(123): GO TO EDIT-MONTH-EXIT
      return Flow.editMonthExit;
      // COB(124): END-IF
    }
    // COB(126): IF FUNCTION TEST-NUMVAL (WS-EDIT-DATE-MM) = 0
    if (ws.wsMiscStorage
        .wsCalculationVars
        .wsEditDateCcyymmdd
        .wsEditDateMm
        .testConvertToNumeric()
        .equals(0)) {
      // COB(127): COMPUTE WS-EDIT-DATE-MM-N
      // COB(127):             = FUNCTION NUMVAL (WS-EDIT-DATE-MM)
      // COB(127): END-COMPUTE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateMmN.setValue(
          ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateMm.convertToNumeric());
      // COB(130): ELSE
    } else {
      // COB(131): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(132): SET  FLG-MONTH-NOT-OK        TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgMonthNotOk(true);
      // COB(133): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(134): STRING
        // COB(134):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(134):   ': Month must be a number between 1 and 12.'
        // COB(134):   DELIMITED BY SIZE
        // COB(134):   INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Month must be a number between 1 and 12.");
        // COB(139): END-IF
      }
      // COB(140): GO TO EDIT-MONTH-EXIT
      return Flow.editMonthExit;
      // COB(141): END-IF
    }
    // COB(143): SET FLG-MONTH-ISVALID           TO TRUE
    ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgMonthIsvalid(true);
    return Flow.Exit;
  }

  protected void editMonthExit() {
    // COB(146): EXIT
    return;
  }

  protected Flow editDay() {
    // COB(152): SET FLG-DAY-ISVALID             TO TRUE
    ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgDayIsvalid(true);
    // COB(154): IF WS-EDIT-DATE-DD              EQUAL LOW-VALUES
    // COB(154): OR WS-EDIT-DATE-DD              EQUAL SPACES
    if (ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateDd.isLowValues()
        || ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateDd.isSpaces()) {
      // COB(156): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(157): SET  FLG-DAY-BLANK           TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgDayBlank(true);
      // COB(158): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(159): STRING
        // COB(159):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(159):   ' : Day must be supplied.'
        // COB(159):   DELIMITED BY SIZE
        // COB(159):   INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " : Day must be supplied.");
        // COB(164): END-IF
      }
      // COB(165): GO TO EDIT-DAY-EXIT
      return Flow.editDayExit;
      // COB(166): ELSE
    } else {
      // COB(167): CONTINUE
      // do nothing
      // COB(168): END-IF
    }
    // COB(170): IF FUNCTION TEST-NUMVAL (WS-EDIT-DATE-DD) = 0
    if (ws.wsMiscStorage
        .wsCalculationVars
        .wsEditDateCcyymmdd
        .wsEditDateDd
        .testConvertToNumeric()
        .equals(0)) {
      // COB(171): COMPUTE WS-EDIT-DATE-DD-N
      // COB(171):             = FUNCTION NUMVAL (WS-EDIT-DATE-DD)
      // COB(171): END-COMPUTE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateDdN.setValue(
          ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateDd.convertToNumeric());
      // COB(174): ELSE
    } else {
      // COB(175): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(176): SET  FLG-DAY-NOT-OK          TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgDayNotOk(true);
      // COB(177): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(178): STRING
        // COB(178):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(178):   ':day must be a number between 1 and 31.'
        // COB(178):   DELIMITED BY SIZE
        // COB(178):   INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ":day must be a number between 1 and 31.");
        // COB(183): END-IF
      }
      // COB(184): GO TO EDIT-DAY-EXIT
      return Flow.editDayExit;
      // COB(185): END-IF
    }
    // COB(187): IF WS-VALID-DAY
    if (ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsValidDay()) {
      // COB(188): CONTINUE
      // do nothing
      // COB(189): ELSE
    } else {
      // COB(190): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(191): SET FLG-DAY-NOT-OK          TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgDayNotOk(true);
      // COB(192): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(193): STRING
        // COB(193):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(193):   ':day must be a number between 1 and 31.'
        // COB(193):   DELIMITED BY SIZE
        // COB(193):   INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ":day must be a number between 1 and 31.");
        // COB(198): END-IF
      }
      // COB(199): GO TO EDIT-DAY-EXIT
      return Flow.editDayExit;
      // COB(200): END-IF
    }
    // COB(203): SET FLG-DAY-ISVALID           TO TRUE
    //
    ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgDayIsvalid(true);
    return Flow.Exit;
  }

  protected void editDayExit() {
    // COB(206): EXIT
    return;
  }

  protected Flow editDayMonthYear() {
    // COB(213): IF  NOT WS-31-DAY-MONTH
    // COB(213): AND WS-DAY-31
    // *****************************************************************
    //     Checking for any other combinations
    // *****************************************************************
    if (!ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.ws31DayMonth()
        && ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsDay31()) {
      // COB(215): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(216): SET FLG-DAY-NOT-OK           TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgDayNotOk(true);
      // COB(217): SET FLG-MONTH-NOT-OK         TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgMonthNotOk(true);
      // COB(218): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(219): STRING
        // COB(219):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(219):   ':Cannot have 31 days in this month.'
        // COB(219):   DELIMITED BY SIZE
        // COB(219):   INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ":Cannot have 31 days in this month.");
        // COB(224): END-IF
      }
      // COB(225): GO TO EDIT-DATE-CCYYMMDD-EXIT
      return Flow.editDateCcyymmddExit;
      // COB(226): END-IF
    }
    // COB(228): IF  WS-FEBRUARY
    // COB(228): AND WS-DAY-30
    if (ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsFebruary()
        && ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsDay30()) {
      // COB(230): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(231): SET FLG-DAY-NOT-OK           TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgDayNotOk(true);
      // COB(232): SET FLG-MONTH-NOT-OK         TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgMonthNotOk(true);
      // COB(233): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(234): STRING
        // COB(234):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(234):   ':Cannot have 30 days in this month.'
        // COB(234):   DELIMITED BY SIZE
        // COB(234):   INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ":Cannot have 30 days in this month.");
        // COB(239): END-IF
      }
      // COB(240): GO TO EDIT-DATE-CCYYMMDD-EXIT
      return Flow.editDateCcyymmddExit;
      // COB(241): END-IF
    }
    // COB(243): IF  WS-FEBRUARY
    // COB(243): AND WS-DAY-29
    if (ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsFebruary()
        && ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsDay29()) {
      // COB(245): IF WS-EDIT-DATE-YY-N = 0
      if (ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateCcyy.wsEditDateYyN.equals(
          0)) {
        // COB(246): MOVE 400                TO  WS-DIV-BY
        ws.wsMiscStorage.wsCalculationVars.wsDivBy.setValue(400);
        // COB(247): ELSE
      } else {
        // COB(248): MOVE 4                  TO  WS-DIV-BY
        ws.wsMiscStorage.wsCalculationVars.wsDivBy.setValue(4);
        // COB(249): END-IF
      }
      // COB(251): DIVIDE WS-EDIT-DATE-CCYY-N
      // COB(251):     BY WS-DIV-BY
      // COB(251): GIVING WS-DIVIDEND
      // COB(251): REMAINDER WS-REMAINDER
      ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.wsEditDateCcyyN.divideBy(
          ws.wsMiscStorage.wsCalculationVars.wsDivBy,
          ws.wsMiscStorage.wsCalculationVars.wsDividend,
          ws.wsMiscStorage.wsCalculationVars.wsRemainder);
      // COB(256): IF WS-REMAINDER = ZEROES
      if (ws.wsMiscStorage.wsCalculationVars.wsRemainder.equals(0)) {
        // COB(257): CONTINUE
        // do nothing
        // COB(258): ELSE
      } else {
        // COB(259): SET INPUT-ERROR          TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(260): SET FLG-DAY-NOT-OK       TO TRUE
        ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgDayNotOk(true);
        // COB(261): SET FLG-MONTH-NOT-OK     TO TRUE
        ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgMonthNotOk(true);
        // COB(262): SET FLG-YEAR-NOT-OK      TO TRUE
        ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgYearNotOk(true);
        // COB(263): IF WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(264): STRING
          // COB(264):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
          // COB(264):  ':Not a leap year.Cannot have 29 days in this month.'
          // COB(264):   DELIMITED BY SIZE
          // COB(264):  INTO WS-RETURN-MSG
          ws.wsMiscStorage.wsReturnMsg.concat(
              ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
              ":Not a leap year.Cannot have 29 days in this month.");
          // COB(269): END-IF
        }
        // COB(270): GO TO EDIT-DATE-CCYYMMDD-EXIT
        return Flow.editDateCcyymmddExit;
        // COB(271): END-IF
      }
      // COB(272): END-IF
    }
    // COB(274): IF WS-EDIT-DATE-IS-VALID
    if (ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.wsEditDateIsValid()) {
      // COB(275): CONTINUE
      // do nothing
      // COB(276): ELSE
    } else {
      // COB(277): GO TO EDIT-DATE-CCYYMMDD-EXIT
      return Flow.editDateCcyymmddExit;
      // COB(278): END-IF
    }
    return Flow.Exit;
  }

  protected void editDayMonthYearExit() {
    // COB(281): EXIT
    return;
  }

  protected Flow editDateLe() {
    // COB(290): INITIALIZE WS-DATE-VALIDATION-RESULT
    // *****************************************************************
    //     In case some one managed to enter a bad date that passsed all
    //     the edits above ......
    //                   Use LE Services to verify the supplied date
    // *****************************************************************
    ws.wsMiscStorage.wsCalculationVars.wsDateValidationResult.initialize();
    // COB(291): MOVE 'YYYYMMDD'                   TO WS-DATE-FORMAT
    ws.wsMiscStorage.wsCalculationVars.wsDateFormat.setString("YYYYMMDD");
    // COB(293): 005100     CALL 'CSUTLDTC'
    // COB(293):            USING WS-EDIT-DATE-CCYYMMDD
    // COB(293):                , WS-DATE-FORMAT
    // COB(293):                , WS-DATE-VALIDATION-RESULT
    supernaut.call(
        "CSUTLDTC",
        ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd,
        ws.wsMiscStorage.wsCalculationVars.wsDateFormat,
        ws.wsMiscStorage.wsCalculationVars.wsDateValidationResult);
    // COB(298): IF WS-SEVERITY-N = 0
    if (ws.wsMiscStorage.wsCalculationVars.wsDateValidationResult.wsSeverityN.equals(0)) {
      // COB(299): CONTINUE
      // do nothing
      // COB(300): ELSE
    } else {
      // COB(301): SET INPUT-ERROR                TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(302): SET FLG-DAY-NOT-OK             TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgDayNotOk(true);
      // COB(303): SET FLG-MONTH-NOT-OK           TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgMonthNotOk(true);
      // COB(304): SET FLG-YEAR-NOT-OK            TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgYearNotOk(true);
      // COB(305): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(306): STRING
        // COB(306):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(306):   ' validation error Sev code: '
        // COB(306):   WS-SEVERITY
        // COB(306):   ' Message code: '
        // COB(306):   WS-MSG-NO
        // COB(306):   DELIMITED BY SIZE
        // COB(306):  INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            " validation error Sev code: ",
            ws.wsMiscStorage.wsCalculationVars.wsDateValidationResult.wsSeverity,
            " Message code: ",
            ws.wsMiscStorage.wsCalculationVars.wsDateValidationResult.wsMsgNo);
        // COB(314): END-IF
      }
      // COB(315): GO TO EDIT-DATE-LE-EXIT
      return Flow.editDateLeExit;
      // COB(316): END-IF
    }
    // COB(318): IF NOT INPUT-ERROR
    if (!ws.wsMiscStorage.inputError()) {
      // COB(319): SET FLG-DAY-ISVALID           TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgDayIsvalid(true);
      // COB(320): END-IF
    }
    return Flow.Exit;
  }

  protected void editDateLeExit() {
    // COB(324): EXIT
    // continue
    // COB(327): SET WS-EDIT-DATE-IS-VALID        TO TRUE
    //
    //     If we got here all edits were cleared
    ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setWsEditDateIsValid(true);
  }

  protected void editDateCcyymmddExit() {
    // COB(330): EXIT
    return;
  }

  /******************************************************************
   *Date of Birth Reasonableness check
   ******************************************************************
   *  At the time of writing this program
   *  Time travel was not possible.
   *  Date of birth in the future is not acceptable
   ******************************************************************
   */
  protected void editDateOfBirth() {
    // COB(343): MOVE FUNCTION CURRENT-DATE TO WS-CURRENT-DATE-YYYYMMDD
    ws.wsMiscStorage.wsCalculationVars.wsCurrentDate.wsCurrentDateYyyymmdd.setValue(
        environment.now().toCurrentDate());
    // COB(345): COMPUTE WS-EDIT-DATE-BINARY =
    // COB(345):     FUNCTION INTEGER-OF-DATE (WS-EDIT-DATE-CCYYMMDD-N)
    ws.wsMiscStorage.wsCalculationVars.wsEditDateBinary.setValue(
        AbstractNField.integerOfDate(
            ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmddN.getInt()));
    // COB(347): COMPUTE WS-CURRENT-DATE-BINARY =
    // COB(347):     FUNCTION INTEGER-OF-DATE (WS-CURRENT-DATE-YYYYMMDD-N)
    ws.wsMiscStorage.wsCalculationVars.wsCurrentDate.wsCurrentDateBinary.setValue(
        AbstractNField.integerOfDate(
            ws.wsMiscStorage.wsCalculationVars.wsCurrentDate.wsCurrentDateYyyymmddN.getInt()));
    // COB(350): IF WS-CURRENT-DATE-BINARY > WS-EDIT-DATE-BINARY
    if (ws.wsMiscStorage.wsCalculationVars.wsCurrentDate.wsCurrentDateBinary.greaterThan(
        ws.wsMiscStorage.wsCalculationVars.wsEditDateBinary)) {
      // COB(354): CONTINUE
      //     IF FUNCTION FIND-DURATION(FUNCTION CURRENT-DATE
      //                              ,WS-EDIT-DATE-CCYYMMDD)
      //                              ,DAYS) > 0
      // do nothing
      // COB(355): ELSE
    } else {
      // COB(356): SET INPUT-ERROR                TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(357): SET FLG-DAY-NOT-OK             TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgDayNotOk(true);
      // COB(358): SET FLG-MONTH-NOT-OK           TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgMonthNotOk(true);
      // COB(359): SET FLG-YEAR-NOT-OK            TO TRUE
      ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs.setFlgYearNotOk(true);
      // COB(360): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(361): STRING
        // COB(361):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(361):   ':cannot be in the future '
        // COB(361):   DELIMITED BY SIZE
        // COB(361):  INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), ":cannot be in the future ");
        // COB(366): END-IF
      }
      // COB(367): GO TO EDIT-DATE-OF-BIRTH-EXIT
      return;
      // COB(368): END-IF
    }
  }

  protected enum HandleLabel {
    Not_Defined(0),
    abendRoutine(1) // ABEND-ROUTINE
  ;

    private final Integer value;

    HandleLabel(Integer value) {
      this.value = value;
    }

    public int getValue() {
      return this.value;
    }

    public static HandleLabel get(Integer handleId) {
      for (HandleLabel hndLbl : HandleLabel.values()) {
        if (hndLbl.getValue() == handleId) {
          return hndLbl;
        }
      }
      return Not_Defined;
    }
  }

  protected void handleCommandCondition(HandledConditionException e) {
    switch (HandleLabel.get(e.getHandleId())) {
      case abendRoutine:
        abendRoutine();
        break;
      default:
        throw new RuntimeException(e);
    }
  }
}
