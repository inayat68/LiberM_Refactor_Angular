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
      public NChar[] filler856 = AbstractNField.occurs(32767, new NChar(1));

      public NChar filler856AtIndex(int index) {
        return getOccursInstance(filler856, index);
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
    // COB(864): EXEC CICS HANDLE ABEND
    // COB(864):           LABEL(ABEND-ROUTINE)
    // COB(864): END-EXEC
    //       *
    //       *
    supernaut
        .handleAbend() //
        .label(HandleLabel.abendRoutine.getValue()) //
        .exec();
    // COB(868): INITIALIZE CC-WORK-AREA
    // COB(868):            WS-MISC-STORAGE
    // COB(868):            WS-COMMAREA
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.initialize();
    ws.wsMiscStorage.initialize();
    ws.wsCommarea.initialize();
    // COB(874): MOVE LIT-THISTRANID       TO WS-TRANID
    //       *****************************************************************
    //       * Store our context
    //       *****************************************************************
    ws.wsMiscStorage.wsCicsProcessngVars.wsTranid.setValue(ws.wsLiterals.litThistranid);
    // COB(878): SET WS-RETURN-MSG-OFF  TO TRUE
    //       *****************************************************************
    //       * Ensure error message is cleared                               *
    //       *****************************************************************
    ws.wsMiscStorage.setWsReturnMsgOff(true);
    // COB(880): IF OVERFLOW > ZERO
    if (ws.dfhrespTab.overflow.greaterThan(0)) {
      // COB(881): DISPLAY "CIAONE"
      sysout.display("CIAONE");
      // COB(882): END-IF
    }
    // COB(886): IF EIBCALEN IS EQUAL TO 0
    // COB(886):     OR (CDEMO-FROM-PROGRAM = LIT-MENUPGM
    // COB(886):     AND NOT CDEMO-PGM-REENTER)
    //       *****************************************************************
    //       * Store passed data if  any                *
    //       *****************************************************************
    if (dfheiblk.getEibcalen() == 0
        || (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsLiterals.litMenupgm)
            && !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter())) {
      // COB(889): INITIALIZE CARDDEMO-COMMAREA
      // COB(889):            WS-THIS-PROGCOMMAREA
      ws.cocom01y.carddemoCommarea.initialize();
      ws.wsThisProgcommarea.initialize();
      // COB(891): SET CDEMO-PGM-ENTER TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(892): SET ACUP-DETAILS-NOT-FETCHED TO TRUE
      ws.wsThisProgcommarea.acctUpdateScreenData.setAcupDetailsNotFetched(true);
      // COB(893): ELSE
    } else {
      // COB(894): MOVE DFHCOMMAREA (1:LENGTH OF CARDDEMO-COMMAREA)  TO
      // COB(894):                   CARDDEMO-COMMAREA
      ws.cocom01y.carddemoCommarea.setValue(
          params.dfhcommarea, 0, ws.cocom01y.carddemoCommarea.length());
      // COB(896): MOVE DFHCOMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
      // COB(896):                  LENGTH OF WS-THIS-PROGCOMMAREA ) TO
      // COB(896):                   WS-THIS-PROGCOMMAREA
      ws.wsThisProgcommarea.setValue(
          params.dfhcommarea,
          ws.cocom01y.carddemoCommarea.length(),
          0,
          ws.wsThisProgcommarea.length());
      // COB(899): END-IF
    }
    // COB(904): PERFORM YYYY-STORE-PFKEY
    // COB(904):    THRU YYYY-STORE-PFKEY-EXIT
    //       *****************************************************************
    //       * Remap PFkeys as needed.
    //       * Store the Mapped PF Key
    //       *****************************************************************
    yyyyStorePfkey();
    // COB(911): SET PFK-INVALID TO TRUE
    //       *****************************************************************
    //       * Check the AID to see if its valid at this point               *
    //       * F3 - Exit
    //       * Enter show screen again
    //       *****************************************************************
    ws.wsMiscStorage.setPfkInvalid(true);
    // COB(912): IF CCARD-AID-ENTER OR
    // COB(912):    CCARD-AID-PFK03 OR
    // COB(912):    (CCARD-AID-PFK05 AND ACUP-CHANGES-OK-NOT-CONFIRMED)
    // COB(912):                    OR
    // COB(912):    (CCARD-AID-PFK12 AND NOT ACUP-DETAILS-NOT-FETCHED)
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidEnter()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()
        || (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk05()
            && ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkNotConfirmed())
        || (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk12()
            && !ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched())) {
      // COB(917): SET PFK-VALID TO TRUE
      ws.wsMiscStorage.setPfkValid(true);
      // COB(918): END-IF
    }
    // COB(920): IF PFK-INVALID
    if (ws.wsMiscStorage.pfkInvalid()) {
      // COB(921): SET CCARD-AID-ENTER TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidEnter(true);
      // COB(922): END-IF
    }
    // COB(923): EVALUATE CDEMO-FROM-TRANID    EQUAL LOW-VALUES
    // FIXME-[NOT_CONVERTED] EVALUATE :923 >COB: [           EVALUATE CDEMO-FROM-TRANID    EQUAL
    // LOW-VALUES]
    // COB(924): WHEN TRUE
    // FIXME-[NOT_CONVERTED] WHEN :924 >COB: [           WHEN TRUE]
    // COB(925): DISPLAY 'EVALUATE IS TRUE'
    sysout.display("EVALUATE IS TRUE");
    // COB(926): WHEN OTHER
    // FIXME-[NOT_CONVERTED] WHEN :926 >COB: [           WHEN OTHER]
    // COB(927): DISPLAY 'EVALUATE IS FALSE'
    sysout.display("EVALUATE IS FALSE");
    // COB(928): END-EVALUATE
    // FIXME-[NOT_CONVERTED] END-EVALUATE :928 >COB: [           END-EVALUATE]
    // COB(933): EVALUATE TRUE
    //       *
    //       *****************************************************************
    //       * Decide what to do based on inputs received
    //       *****************************************************************
    // COB(939): WHEN CCARD-AID-PFK03
    //       ******************************************************************
    //       *       USER PRESSES PF03 TO EXIT
    //       *  OR   USER IS DONE WITH UPDATE
    //       *            XCTL TO CALLING PROGRAM OR MAIN MENU
    //       ******************************************************************
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()) {
      // COB(940): SET CCARD-AID-PFK03     TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk03(true);
      // COB(942): IF CDEMO-FROM-TRANID    EQUAL LOW-VALUES
      // COB(942): OR CDEMO-FROM-TRANID    EQUAL SPACES
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isSpaces()) {
        // COB(944): MOVE LIT-MENUTRANID  TO CDEMO-TO-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToTranid.setValue(
            ws.wsLiterals.litMenutranid);
        // COB(945): ELSE
      } else {
        // COB(946): MOVE CDEMO-FROM-TRANID  TO CDEMO-TO-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToTranid.setValue(
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid);
        // COB(947): END-IF
      }
      // COB(949): IF CDEMO-FROM-PROGRAM   EQUAL LOW-VALUES
      // COB(949): OR CDEMO-FROM-PROGRAM   EQUAL SPACES
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isSpaces()) {
        // COB(951): MOVE LIT-MENUPGM     TO CDEMO-TO-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
            ws.wsLiterals.litMenupgm);
        // COB(952): ELSE
      } else {
        // COB(953): MOVE CDEMO-FROM-PROGRAM TO CDEMO-TO-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram);
        // COB(954): END-IF
      }
      // COB(956): MOVE LIT-THISTRANID     TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
          ws.wsLiterals.litThistranid);
      // COB(957): MOVE LIT-THISPGM        TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
          ws.wsLiterals.litThispgm);
      // COB(959): SET  CDEMO-USRTYP-USER  TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypUser(true);
      // COB(960): SET  CDEMO-PGM-ENTER    TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(961): MOVE LIT-THISMAPSET     TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(
          ws.wsLiterals.litThismapset);
      // COB(962): MOVE LIT-THISMAP        TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsLiterals.litThismap);
      // COB(964): EXEC CICS
      // COB(964):      SYNCPOINT
      // COB(964): END-EXEC
      try {
        supernaut
            .syncpoint() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(968): EXEC CICS XCTL
      // COB(968):      PROGRAM (CDEMO-TO-PROGRAM)
      // COB(968):      COMMAREA(CARDDEMO-COMMAREA)
      // COB(968): END-EXEC
      try {
        supernaut
            .xctl(ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
            .commarea(ws.cocom01y.carddemoCommarea) //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(976): WHEN ACUP-DETAILS-NOT-FETCHED
      // COB(976):  AND CDEMO-PGM-ENTER
      //       ******************************************************************
      //       *       FRESH ENTRY INTO PROGRAM
      //       *            ASK THE USER FOR THE KEYS TO FETCH CARD TO BE UPDATED
      //       ******************************************************************
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched()
            && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()
        // COB(978): WHEN CDEMO-FROM-PROGRAM   EQUAL LIT-MENUPGM
        // COB(978):  AND NOT CDEMO-PGM-REENTER
        || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsLiterals.litMenupgm)
            && !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(980): INITIALIZE WS-THIS-PROGCOMMAREA
      ws.wsThisProgcommarea.initialize();
      // COB(981): PERFORM 3000-SEND-MAP THRU
      // COB(981):         3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(983): SET CDEMO-PGM-REENTER        TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
      // COB(984): SET ACUP-DETAILS-NOT-FETCHED TO TRUE
      ws.wsThisProgcommarea.acctUpdateScreenData.setAcupDetailsNotFetched(true);
      // COB(985): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(991): WHEN ACUP-CHANGES-OKAYED-AND-DONE
      //       ******************************************************************
      //       *       ACCT DATA CHANGES REVIEWED, OKAYED AND DONE SUCESSFULLY
      //       *            RESET THE SEARCH KEYS
      //       *            ASK THE USER FOR FRESH SEARCH CRITERIA
      //       ******************************************************************
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedAndDone()
        // COB(992): WHEN ACUP-CHANGES-FAILED
        || ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesFailed()) {
      // COB(993): INITIALIZE WS-THIS-PROGCOMMAREA
      // COB(993):            WS-MISC-STORAGE
      // COB(993):            CDEMO-ACCT-ID
      ws.wsThisProgcommarea.initialize();
      ws.wsMiscStorage.initialize();
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.initialize();
      // COB(996): SET CDEMO-PGM-ENTER            TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(997): PERFORM 3000-SEND-MAP THRU
      // COB(997):         3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(999): SET CDEMO-PGM-REENTER          TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
      // COB(1000): SET ACUP-DETAILS-NOT-FETCHED   TO TRUE
      ws.wsThisProgcommarea.acctUpdateScreenData.setAcupDetailsNotFetched(true);
      // COB(1001): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(1008): WHEN OTHER
      //       ******************************************************************
      //       *      ACCT DATA HAS BEEN PRESENTED TO USER
      //       *            CHECK THE USER INPUTS
      //       *            DECIDE WHAT TO DO
      //       *            PRESENT NEXT STEPS TO USER
      //       ******************************************************************
    } else {
      // COB(1009): PERFORM 1000-PROCESS-INPUTS
      // COB(1009):    THRU 1000-PROCESS-INPUTS-EXIT
      _1000ProcessInputs();
      // COB(1011): PERFORM 2000-DECIDE-ACTION
      // COB(1011):    THRU 2000-DECIDE-ACTION-EXIT
      _2000DecideAction();
      // COB(1013): PERFORM 3000-SEND-MAP
      // COB(1013):    THRU 3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(1015): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(1016): END-EVALUATE
    }
    return Flow.Exit;
  }

  /***/
  protected void commonReturn() {
    // COB(1020): MOVE WS-RETURN-MSG     TO CCARD-ERROR-MSG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
    // COB(1022): MOVE  CARDDEMO-COMMAREA    TO WS-COMMAREA
    ws.wsCommarea.setValue(ws.cocom01y.carddemoCommarea);
    // COB(1023): MOVE  WS-THIS-PROGCOMMAREA TO
    // COB(1023):        WS-COMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
    // COB(1023):                     LENGTH OF WS-THIS-PROGCOMMAREA )
    ws.wsCommarea.setValue(
        ws.wsThisProgcommarea,
        0,
        ws.cocom01y.carddemoCommarea.length(),
        ws.wsThisProgcommarea.length());
    // COB(1027): EXEC CICS RETURN
    // COB(1027):      TRANSID (LIT-THISTRANID)
    // COB(1027):      COMMAREA (WS-COMMAREA)
    // COB(1027):      LENGTH(LENGTH OF WS-COMMAREA)
    // COB(1027): END-EXEC
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
    // COB(1038): PERFORM 1100-RECEIVE-MAP
    // COB(1038):    THRU 1100-RECEIVE-MAP-EXIT
    _1100ReceiveMap();
    // COB(1040): PERFORM 1200-EDIT-MAP-INPUTS
    // COB(1040):    THRU 1200-EDIT-MAP-INPUTS-EXIT
    _1200EditMapInputs();
    // COB(1042): MOVE WS-RETURN-MSG  TO CCARD-ERROR-MSG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
    // COB(1043): MOVE LIT-THISPGM    TO CCARD-NEXT-PROG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.setValue(ws.wsLiterals.litThispgm);
    // COB(1044): MOVE LIT-THISMAPSET TO CCARD-NEXT-MAPSET
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsLiterals.litThismapset);
    // COB(1045): MOVE LIT-THISMAP    TO CCARD-NEXT-MAP
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsLiterals.litThismap);
  }

  protected void _1100ReceiveMap() {
    // COB(1052): EXEC CICS RECEIVE MAP(LIT-THISMAP)
    // COB(1052):           MAPSET(LIT-THISMAPSET)
    // COB(1052):           INTO(CACTUPAI)
    // COB(1052):           RESP(WS-RESP-CD)
    // COB(1052):           RESP2(WS-REAS-CD)
    // COB(1052): END-EXEC
    supernaut
        .receiveMap(ws.wsLiterals.litThismap.getValue()) //
        .mapset(ws.wsLiterals.litThismapset.getValue()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.coactup.cactupai) //
        .exec();
    // COB(1059): INITIALIZE ACUP-NEW-DETAILS
    ws.wsThisProgcommarea.acupNewDetails.initialize();
    // COB(1063): IF  ACCTSIDI OF CACTUPAI = '*'
    // COB(1063): OR  ACCTSIDI OF CACTUPAI = SPACES
    //       ******************************************************************
    //       *    Account Master data
    //       ******************************************************************
    if (ws.coactup.cactupai.acctsidi.equals("*") || ws.coactup.cactupai.acctsidi.isSpaces()) {
      // COB(1065): MOVE LOW-VALUES           TO CC-ACCT-ID
      // COB(1065):                              ACUP-NEW-ACCT-ID-X
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.lowValues();
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewAcctIdX.lowValues();
      // COB(1067): ELSE
    } else {
      // COB(1068): MOVE ACCTSIDI OF CACTUPAI TO CC-ACCT-ID
      // COB(1068):                              ACUP-NEW-ACCT-ID-X
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.setValue(ws.coactup.cactupai.acctsidi);
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewAcctIdX.setValue(
          ws.coactup.cactupai.acctsidi);
      // COB(1070): END-IF
    }
    // COB(1072): IF ACUP-DETAILS-NOT-FETCHED
    if (ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched()) {
      // COB(1073): GO TO 1100-RECEIVE-MAP-EXIT
      return;
      // COB(1074): END-IF
    }
    // COB(1077): IF  ACSTTUSI OF CACTUPAI = '*'
    // COB(1077): OR  ACSTTUSI OF CACTUPAI = SPACES
    //       *
    //       * Active Status
    if (ws.coactup.cactupai.acsttusi.equals("*") || ws.coactup.cactupai.acsttusi.isSpaces()) {
      // COB(1079): MOVE LOW-VALUES           TO ACUP-NEW-ACTIVE-STATUS
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewActiveStatus.lowValues();
      // COB(1080): ELSE
    } else {
      // COB(1081): MOVE ACSTTUSI OF CACTUPAI TO ACUP-NEW-ACTIVE-STATUS
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewActiveStatus.setValue(
          ws.coactup.cactupai.acsttusi);
      // COB(1082): END-IF
    }
    // COB(1085): IF  ACRDLIMI OF CACTUPAI = '*'
    // COB(1085): OR  ACRDLIMI OF CACTUPAI = SPACES
    //       *
    //       * Credit Limit
    if (ws.coactup.cactupai.acrdlimi.equals("*") || ws.coactup.cactupai.acrdlimi.isSpaces()) {
      // COB(1087): MOVE LOW-VALUES           TO ACUP-NEW-CREDIT-LIMIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCreditLimitX.lowValues();
      // COB(1088): ELSE
    } else {
      // COB(1089): MOVE ACRDLIMI OF CACTUPAI TO ACUP-NEW-CREDIT-LIMIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCreditLimitX.setValue(
          ws.coactup.cactupai.acrdlimi);
      // COB(1090): IF FUNCTION TEST-NUMVAL-C(ACUP-NEW-CREDIT-LIMIT-X) = 0
      if (ws.wsMiscStorage
          .alphaVarsForDataEditing
          .acupNewCreditLimitX
          .testConvertCurrencyToNumeric()
          .equals(0)) {
        // COB(1091): COMPUTE ACUP-NEW-CREDIT-LIMIT-N =
        // COB(1091):    FUNCTION NUMVAL-C(ACRDLIMI OF CACTUPAI)
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCreditLimitN.setValue(
            ws.coactup.cactupai.acrdlimi.convertCurrencyToNumeric());
        // COB(1093): ELSE
      } else {
        // COB(1094): CONTINUE
        // do nothing
        // COB(1095): END-IF
      }
      // COB(1096): END-IF
    }
    // COB(1099): IF  ACSHLIMI OF CACTUPAI = '*'
    // COB(1099): OR  ACSHLIMI OF CACTUPAI = SPACES
    //       *
    //       * Cash Limit
    if (ws.coactup.cactupai.acshlimi.equals("*") || ws.coactup.cactupai.acshlimi.isSpaces()) {
      // COB(1101): MOVE LOW-VALUES           TO ACUP-NEW-CASH-CREDIT-LIMIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCashCreditLimitX.lowValues();
      // COB(1102): ELSE
    } else {
      // COB(1103): MOVE ACSHLIMI OF CACTUPAI TO ACUP-NEW-CASH-CREDIT-LIMIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCashCreditLimitX.setValue(
          ws.coactup.cactupai.acshlimi);
      // COB(1104): IF FUNCTION TEST-NUMVAL-C(ACUP-NEW-CASH-CREDIT-LIMIT-X) = 0
      if (ws.wsMiscStorage
          .alphaVarsForDataEditing
          .acupNewCashCreditLimitX
          .testConvertCurrencyToNumeric()
          .equals(0)) {
        // COB(1105): COMPUTE ACUP-NEW-CASH-CREDIT-LIMIT-N =
        // COB(1105):      FUNCTION NUMVAL-C(ACSHLIMI OF CACTUPAI)
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCashCreditLimitN.setValue(
            ws.coactup.cactupai.acshlimi.convertCurrencyToNumeric());
        // COB(1107): ELSE
      } else {
        // COB(1108): CONTINUE
        // do nothing
        // COB(1109): END-IF
      }
      // COB(1110): END-IF
    }
    // COB(1113): IF  ACURBALI OF CACTUPAI = '*'
    // COB(1113): OR  ACURBALI OF CACTUPAI = SPACES
    //       *
    //       * Current Balance
    if (ws.coactup.cactupai.acurbali.equals("*") || ws.coactup.cactupai.acurbali.isSpaces()) {
      // COB(1115): MOVE LOW-VALUES           TO ACUP-NEW-CURR-BAL-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrBalX.lowValues();
      // COB(1116): ELSE
    } else {
      // COB(1117): MOVE ACURBALI OF CACTUPAI TO ACUP-NEW-CURR-BAL-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrBalX.setValue(
          ws.coactup.cactupai.acurbali);
      // COB(1118): IF  FUNCTION TEST-NUMVAL-C(ACUP-NEW-CURR-BAL-X) = 0
      if (ws.wsMiscStorage
          .alphaVarsForDataEditing
          .acupNewCurrBalX
          .testConvertCurrencyToNumeric()
          .equals(0)) {
        // COB(1119): COMPUTE ACUP-NEW-CURR-BAL-N =
        // COB(1119):   FUNCTION NUMVAL-C(ACUP-NEW-CURR-BAL-X)
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrBalN.setValue(
            ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrBalX.convertCurrencyToNumeric());
        // COB(1121): ELSE
      } else {
        // COB(1122): CONTINUE
        // do nothing
        // COB(1123): END-IF
      }
      // COB(1124): END-IF
    }
    // COB(1127): IF  ACRCYCRI OF CACTUPAI = '*'
    // COB(1127): OR  ACRCYCRI OF CACTUPAI = SPACES
    //       *
    //       *Current Cycle Credit
    if (ws.coactup.cactupai.acrcycri.equals("*") || ws.coactup.cactupai.acrcycri.isSpaces()) {
      // COB(1129): MOVE LOW-VALUES           TO ACUP-NEW-CURR-CYC-CREDIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycCreditX.lowValues();
      // COB(1130): ELSE
    } else {
      // COB(1131): MOVE ACRCYCRI OF CACTUPAI TO ACUP-NEW-CURR-CYC-CREDIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycCreditX.setValue(
          ws.coactup.cactupai.acrcycri);
      // COB(1132): IF FUNCTION TEST-NUMVAL-C(ACUP-NEW-CURR-CYC-CREDIT-X) = 0
      if (ws.wsMiscStorage
          .alphaVarsForDataEditing
          .acupNewCurrCycCreditX
          .testConvertCurrencyToNumeric()
          .equals(0)) {
        // COB(1133): COMPUTE ACUP-NEW-CURR-CYC-CREDIT-N =
        // COB(1133):   FUNCTION NUMVAL-C(ACRCYCRI OF CACTUPAI)
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrCycCreditN.setValue(
            ws.coactup.cactupai.acrcycri.convertCurrencyToNumeric());
        // COB(1135): ELSE
      } else {
        // COB(1136): CONTINUE
        // do nothing
        // COB(1137): END-IF
      }
      // COB(1138): END-IF
    }
    // COB(1141): IF  ACRCYDBI OF CACTUPAI = '*'
    // COB(1141): OR  ACRCYDBI OF CACTUPAI = SPACES
    //       *
    //       *Current Cycle Debit
    if (ws.coactup.cactupai.acrcydbi.equals("*") || ws.coactup.cactupai.acrcydbi.isSpaces()) {
      // COB(1143): MOVE LOW-VALUES           TO ACUP-NEW-CURR-CYC-DEBIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycDebitX.lowValues();
      // COB(1144): ELSE
    } else {
      // COB(1145): MOVE ACRCYDBI OF CACTUPAI TO ACUP-NEW-CURR-CYC-DEBIT-X
      ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycDebitX.setValue(
          ws.coactup.cactupai.acrcydbi);
      // COB(1146): IF  FUNCTION TEST-NUMVAL-C(ACUP-NEW-CURR-CYC-DEBIT-X) = 0
      if (ws.wsMiscStorage
          .alphaVarsForDataEditing
          .acupNewCurrCycDebitX
          .testConvertCurrencyToNumeric()
          .equals(0)) {
        // COB(1147): COMPUTE ACUP-NEW-CURR-CYC-DEBIT-N =
        // COB(1147):   FUNCTION NUMVAL-C(ACRCYDBI OF CACTUPAI)
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrCycDebitN.setValue(
            ws.coactup.cactupai.acrcydbi.convertCurrencyToNumeric());
        // COB(1149): ELSE
      } else {
        // COB(1150): CONTINUE
        // do nothing
        // COB(1151): END-IF
      }
      // COB(1152): END-IF
    }
    // COB(1156): IF  OPNYEARI OF CACTUPAI = '*'
    // COB(1156): OR  OPNYEARI OF CACTUPAI = SPACES
    //       *
    //       *Open date
    //       *
    if (ws.coactup.cactupai.opnyeari.equals("*") || ws.coactup.cactupai.opnyeari.isSpaces()) {
      // COB(1158): MOVE LOW-VALUES           TO ACUP-NEW-OPEN-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenYear
          .lowValues();
      // COB(1159): ELSE
    } else {
      // COB(1160): MOVE OPNYEARI OF CACTUPAI TO ACUP-NEW-OPEN-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenYear
          .setValue(ws.coactup.cactupai.opnyeari);
      // COB(1161): END-IF
    }
    // COB(1163): IF  OPNMONI OF CACTUPAI = '*'
    // COB(1163): OR  OPNMONI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.opnmoni.equals("*") || ws.coactup.cactupai.opnmoni.isSpaces()) {
      // COB(1165): MOVE LOW-VALUES           TO ACUP-NEW-OPEN-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenMon
          .lowValues();
      // COB(1166): ELSE
    } else {
      // COB(1167): MOVE OPNMONI OF CACTUPAI TO  ACUP-NEW-OPEN-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenMon
          .setValue(ws.coactup.cactupai.opnmoni);
      // COB(1168): END-IF
    }
    // COB(1170): IF  OPNDAYI OF CACTUPAI = '*'
    // COB(1170): OR  OPNDAYI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.opndayi.equals("*") || ws.coactup.cactupai.opndayi.isSpaces()) {
      // COB(1172): MOVE LOW-VALUES           TO ACUP-NEW-OPEN-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenDay
          .lowValues();
      // COB(1173): ELSE
    } else {
      // COB(1174): MOVE OPNDAYI OF CACTUPAI TO  ACUP-NEW-OPEN-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenDay
          .setValue(ws.coactup.cactupai.opndayi);
      // COB(1175): END-IF
    }
    // COB(1179): IF  EXPYEARI OF CACTUPAI = '*'
    // COB(1179): OR  EXPYEARI OF CACTUPAI = SPACES
    //       *
    //       *Expiry date
    //       *
    if (ws.coactup.cactupai.expyeari.equals("*") || ws.coactup.cactupai.expyeari.isSpaces()) {
      // COB(1181): MOVE LOW-VALUES           TO ACUP-NEW-EXP-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDateParts.acupNewExpYear
          .lowValues();
      // COB(1182): ELSE
    } else {
      // COB(1183): MOVE EXPYEARI OF CACTUPAI TO ACUP-NEW-EXP-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDateParts.acupNewExpYear
          .setValue(ws.coactup.cactupai.expyeari);
      // COB(1184): END-IF
    }
    // COB(1186): IF  EXPMONI OF CACTUPAI = '*'
    // COB(1186): OR  EXPMONI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.expmoni.equals("*") || ws.coactup.cactupai.expmoni.isSpaces()) {
      // COB(1188): MOVE LOW-VALUES           TO ACUP-NEW-EXP-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDateParts.acupNewExpMon
          .lowValues();
      // COB(1189): ELSE
    } else {
      // COB(1190): MOVE EXPMONI OF CACTUPAI TO  ACUP-NEW-EXP-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDateParts.acupNewExpMon
          .setValue(ws.coactup.cactupai.expmoni);
      // COB(1191): END-IF
    }
    // COB(1193): IF  EXPDAYI OF CACTUPAI = '*'
    // COB(1193): OR  EXPDAYI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.expdayi.equals("*") || ws.coactup.cactupai.expdayi.isSpaces()) {
      // COB(1195): MOVE LOW-VALUES           TO ACUP-NEW-EXP-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDateParts.acupNewExpDay
          .lowValues();
      // COB(1196): ELSE
    } else {
      // COB(1197): MOVE EXPDAYI OF CACTUPAI TO  ACUP-NEW-EXP-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDateParts.acupNewExpDay
          .setValue(ws.coactup.cactupai.expdayi);
      // COB(1198): END-IF
    }
    // COB(1202): IF  RISYEARI OF CACTUPAI = '*'
    // COB(1202): OR  RISYEARI OF CACTUPAI = SPACES
    //       *
    //       *Reissue date
    //       *
    if (ws.coactup.cactupai.risyeari.equals("*") || ws.coactup.cactupai.risyeari.isSpaces()) {
      // COB(1204): MOVE LOW-VALUES           TO ACUP-NEW-REISSUE-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDateParts
          .acupNewReissueYear.lowValues();
      // COB(1205): ELSE
    } else {
      // COB(1206): MOVE RISYEARI OF CACTUPAI TO ACUP-NEW-REISSUE-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDateParts
          .acupNewReissueYear.setValue(ws.coactup.cactupai.risyeari);
      // COB(1207): END-IF
    }
    // COB(1209): IF  RISMONI OF CACTUPAI = '*'
    // COB(1209): OR  RISMONI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.rismoni.equals("*") || ws.coactup.cactupai.rismoni.isSpaces()) {
      // COB(1211): MOVE LOW-VALUES           TO ACUP-NEW-REISSUE-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDateParts.acupNewReissueMon
          .lowValues();
      // COB(1212): ELSE
    } else {
      // COB(1213): MOVE RISMONI OF CACTUPAI TO  ACUP-NEW-REISSUE-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDateParts.acupNewReissueMon
          .setValue(ws.coactup.cactupai.rismoni);
      // COB(1214): END-IF
    }
    // COB(1216): IF  RISDAYI OF CACTUPAI = '*'
    // COB(1216): OR  RISDAYI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.risdayi.equals("*") || ws.coactup.cactupai.risdayi.isSpaces()) {
      // COB(1218): MOVE LOW-VALUES           TO ACUP-NEW-REISSUE-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDateParts.acupNewReissueDay
          .lowValues();
      // COB(1219): ELSE
    } else {
      // COB(1220): MOVE RISDAYI OF CACTUPAI TO  ACUP-NEW-REISSUE-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDateParts.acupNewReissueDay
          .setValue(ws.coactup.cactupai.risdayi);
      // COB(1221): END-IF
    }
    // COB(1225): IF  AADDGRPI OF CACTUPAI = '*'
    // COB(1225): OR  AADDGRPI OF CACTUPAI = SPACES
    //       *
    //       *Account Group
    //       *
    if (ws.coactup.cactupai.aaddgrpi.equals("*") || ws.coactup.cactupai.aaddgrpi.isSpaces()) {
      // COB(1227): MOVE LOW-VALUES           TO ACUP-NEW-GROUP-ID
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewGroupId.lowValues();
      // COB(1228): ELSE
    } else {
      // COB(1229): MOVE AADDGRPI OF CACTUPAI TO ACUP-NEW-GROUP-ID
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewGroupId.setValue(
          ws.coactup.cactupai.aaddgrpi);
      // COB(1230): END-IF
    }
    // COB(1236): IF  ACSTNUMI OF CACTUPAI = '*'
    // COB(1236): OR  ACSTNUMI OF CACTUPAI = SPACES
    //       ******************************************************************
    //       *    Customer Master data
    //       ******************************************************************
    //       *Customer Id (actually not editable)
    //       *
    if (ws.coactup.cactupai.acstnumi.equals("*") || ws.coactup.cactupai.acstnumi.isSpaces()) {
      // COB(1238): MOVE LOW-VALUES           TO ACUP-NEW-CUST-ID-X
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustIdX.lowValues();
      // COB(1239): ELSE
    } else {
      // COB(1240): MOVE ACSTNUMI OF CACTUPAI TO ACUP-NEW-CUST-ID-X
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustIdX.setValue(
          ws.coactup.cactupai.acstnumi);
      // COB(1241): END-IF
    }
    // COB(1245): IF  ACTSSN1I OF CACTUPAI = '*'
    // COB(1245): OR  ACTSSN1I OF CACTUPAI = SPACES
    //       *
    //       *Social Security Number
    //       *
    if (ws.coactup.cactupai.actssn1i.equals("*") || ws.coactup.cactupai.actssn1i.isSpaces()) {
      // COB(1247): MOVE LOW-VALUES           TO ACUP-NEW-CUST-SSN-1
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn1
          .lowValues();
      // COB(1248): ELSE
    } else {
      // COB(1249): MOVE ACTSSN1I OF CACTUPAI TO ACUP-NEW-CUST-SSN-1
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn1.setValue(
          ws.coactup.cactupai.actssn1i);
      // COB(1250): END-IF
    }
    // COB(1252): IF  ACTSSN2I OF CACTUPAI = '*'
    // COB(1252): OR  ACTSSN2I OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.actssn2i.equals("*") || ws.coactup.cactupai.actssn2i.isSpaces()) {
      // COB(1254): MOVE LOW-VALUES           TO ACUP-NEW-CUST-SSN-2
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn2
          .lowValues();
      // COB(1255): ELSE
    } else {
      // COB(1256): MOVE ACTSSN2I OF CACTUPAI TO ACUP-NEW-CUST-SSN-2
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn2.setValue(
          ws.coactup.cactupai.actssn2i);
      // COB(1257): END-IF
    }
    // COB(1259): IF  ACTSSN3I OF CACTUPAI = '*'
    // COB(1259): OR  ACTSSN3I OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.actssn3i.equals("*") || ws.coactup.cactupai.actssn3i.isSpaces()) {
      // COB(1261): MOVE LOW-VALUES           TO ACUP-NEW-CUST-SSN-3
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn3
          .lowValues();
      // COB(1262): ELSE
    } else {
      // COB(1263): MOVE ACTSSN3I OF CACTUPAI TO ACUP-NEW-CUST-SSN-3
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn3.setValue(
          ws.coactup.cactupai.actssn3i);
      // COB(1264): END-IF
    }
    // COB(1268): IF  DOBYEARI OF CACTUPAI = '*'
    // COB(1268): OR  DOBYEARI OF CACTUPAI = SPACES
    //       *
    //       *Date of birth
    //       *
    if (ws.coactup.cactupai.dobyeari.equals("*") || ws.coactup.cactupai.dobyeari.isSpaces()) {
      // COB(1270): MOVE LOW-VALUES           TO ACUP-NEW-CUST-DOB-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobYear
          .lowValues();
      // COB(1271): ELSE
    } else {
      // COB(1272): MOVE DOBYEARI OF CACTUPAI TO ACUP-NEW-CUST-DOB-YEAR
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobYear
          .setValue(ws.coactup.cactupai.dobyeari);
      // COB(1273): END-IF
    }
    // COB(1275): IF  DOBMONI OF CACTUPAI = '*'
    // COB(1275): OR  DOBMONI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.dobmoni.equals("*") || ws.coactup.cactupai.dobmoni.isSpaces()) {
      // COB(1277): MOVE LOW-VALUES           TO ACUP-NEW-CUST-DOB-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobMon
          .lowValues();
      // COB(1278): ELSE
    } else {
      // COB(1279): MOVE DOBMONI OF CACTUPAI  TO ACUP-NEW-CUST-DOB-MON
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobMon
          .setValue(ws.coactup.cactupai.dobmoni);
      // COB(1280): END-IF
    }
    // COB(1282): IF  DOBDAYI OF CACTUPAI = '*'
    // COB(1282): OR  DOBDAYI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.dobdayi.equals("*") || ws.coactup.cactupai.dobdayi.isSpaces()) {
      // COB(1284): MOVE LOW-VALUES           TO ACUP-NEW-CUST-DOB-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobDay
          .lowValues();
      // COB(1285): ELSE
    } else {
      // COB(1286): MOVE DOBDAYI OF CACTUPAI  TO ACUP-NEW-CUST-DOB-DAY
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobDay
          .setValue(ws.coactup.cactupai.dobdayi);
      // COB(1287): END-IF
    }
    // COB(1291): IF  ACSTFCOI OF CACTUPAI = '*'
    // COB(1291): OR  ACSTFCOI OF CACTUPAI = SPACES
    //       *
    //       *FICO
    //       *
    if (ws.coactup.cactupai.acstfcoi.equals("*") || ws.coactup.cactupai.acstfcoi.isSpaces()) {
      // COB(1293): MOVE LOW-VALUES           TO ACUP-NEW-CUST-FICO-SCORE-X
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFicoScoreX.lowValues();
      // COB(1294): ELSE
    } else {
      // COB(1295): MOVE ACSTFCOI OF CACTUPAI TO ACUP-NEW-CUST-FICO-SCORE-X
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFicoScoreX.setValue(
          ws.coactup.cactupai.acstfcoi);
      // COB(1296): END-IF
    }
    // COB(1300): IF  ACSFNAMI OF CACTUPAI = '*'
    // COB(1300): OR  ACSFNAMI OF CACTUPAI = SPACES
    //       *
    //       *First Name
    //       *
    if (ws.coactup.cactupai.acsfnami.equals("*") || ws.coactup.cactupai.acsfnami.isSpaces()) {
      // COB(1302): MOVE LOW-VALUES           TO ACUP-NEW-CUST-FIRST-NAME
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFirstName.lowValues();
      // COB(1303): ELSE
    } else {
      // COB(1304): MOVE ACSFNAMI OF CACTUPAI TO ACUP-NEW-CUST-FIRST-NAME
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFirstName.setValue(
          ws.coactup.cactupai.acsfnami);
      // COB(1305): END-IF
    }
    // COB(1309): IF  ACSMNAMI OF CACTUPAI = '*'
    // COB(1309): OR  ACSMNAMI OF CACTUPAI = SPACES
    //       *
    //       *Middle Name
    //       *
    if (ws.coactup.cactupai.acsmnami.equals("*") || ws.coactup.cactupai.acsmnami.isSpaces()) {
      // COB(1311): MOVE LOW-VALUES           TO ACUP-NEW-CUST-MIDDLE-NAME
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustMiddleName.lowValues();
      // COB(1312): ELSE
    } else {
      // COB(1313): MOVE ACSMNAMI OF CACTUPAI TO ACUP-NEW-CUST-MIDDLE-NAME
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustMiddleName.setValue(
          ws.coactup.cactupai.acsmnami);
      // COB(1314): END-IF
    }
    // COB(1318): IF  ACSLNAMI OF CACTUPAI = '*'
    // COB(1318): OR  ACSLNAMI OF CACTUPAI = SPACES
    //       *
    //       *Last Name
    //       *
    if (ws.coactup.cactupai.acslnami.equals("*") || ws.coactup.cactupai.acslnami.isSpaces()) {
      // COB(1320): MOVE LOW-VALUES           TO ACUP-NEW-CUST-LAST-NAME
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustLastName.lowValues();
      // COB(1321): ELSE
    } else {
      // COB(1322): MOVE ACSLNAMI OF CACTUPAI TO ACUP-NEW-CUST-LAST-NAME
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustLastName.setValue(
          ws.coactup.cactupai.acslnami);
      // COB(1323): END-IF
    }
    // COB(1327): IF  ACSADL1I OF CACTUPAI = '*'
    // COB(1327): OR  ACSADL1I OF CACTUPAI = SPACES
    //       *
    //       *Address
    //       *
    if (ws.coactup.cactupai.acsadl1i.equals("*") || ws.coactup.cactupai.acsadl1i.isSpaces()) {
      // COB(1329): MOVE LOW-VALUES           TO ACUP-NEW-CUST-ADDR-LINE-1
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine1.lowValues();
      // COB(1330): ELSE
    } else {
      // COB(1331): MOVE ACSADL1I OF CACTUPAI TO ACUP-NEW-CUST-ADDR-LINE-1
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine1.setValue(
          ws.coactup.cactupai.acsadl1i);
      // COB(1332): END-IF
    }
    // COB(1334): IF  ACSADL2I OF CACTUPAI = '*'
    // COB(1334): OR  ACSADL2I OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsadl2i.equals("*") || ws.coactup.cactupai.acsadl2i.isSpaces()) {
      // COB(1336): MOVE LOW-VALUES           TO ACUP-NEW-CUST-ADDR-LINE-2
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine2.lowValues();
      // COB(1337): ELSE
    } else {
      // COB(1338): MOVE ACSADL2I OF CACTUPAI TO ACUP-NEW-CUST-ADDR-LINE-2
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine2.setValue(
          ws.coactup.cactupai.acsadl2i);
      // COB(1339): END-IF
    }
    // COB(1341): IF  ACSCITYI OF CACTUPAI = '*'
    // COB(1341): OR  ACSCITYI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acscityi.equals("*") || ws.coactup.cactupai.acscityi.isSpaces()) {
      // COB(1343): MOVE LOW-VALUES           TO ACUP-NEW-CUST-ADDR-LINE-3
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine3.lowValues();
      // COB(1344): ELSE
    } else {
      // COB(1345): MOVE ACSCITYI OF CACTUPAI TO ACUP-NEW-CUST-ADDR-LINE-3
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine3.setValue(
          ws.coactup.cactupai.acscityi);
      // COB(1346): END-IF
    }
    // COB(1348): IF  ACSSTTEI OF CACTUPAI = '*'
    // COB(1348): OR  ACSSTTEI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acssttei.equals("*") || ws.coactup.cactupai.acssttei.isSpaces()) {
      // COB(1350): MOVE LOW-VALUES           TO ACUP-NEW-CUST-ADDR-STATE-CD
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrStateCd.lowValues();
      // COB(1351): ELSE
    } else {
      // COB(1352): MOVE ACSSTTEI OF CACTUPAI TO ACUP-NEW-CUST-ADDR-STATE-CD
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrStateCd.setValue(
          ws.coactup.cactupai.acssttei);
      // COB(1353): END-IF
    }
    // COB(1355): IF  ACSCTRYI OF CACTUPAI = '*'
    // COB(1355): OR  ACSCTRYI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsctryi.equals("*") || ws.coactup.cactupai.acsctryi.isSpaces()) {
      // COB(1357): MOVE LOW-VALUES           TO ACUP-NEW-CUST-ADDR-COUNTRY-CD
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrCountryCd.lowValues();
      // COB(1358): ELSE
    } else {
      // COB(1359): MOVE ACSCTRYI OF CACTUPAI TO ACUP-NEW-CUST-ADDR-COUNTRY-CD
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrCountryCd.setValue(
          ws.coactup.cactupai.acsctryi);
      // COB(1360): END-IF
    }
    // COB(1362): IF  ACSZIPCI OF CACTUPAI = '*'
    // COB(1362): OR  ACSZIPCI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acszipci.equals("*") || ws.coactup.cactupai.acszipci.isSpaces()) {
      // COB(1364): MOVE LOW-VALUES           TO ACUP-NEW-CUST-ADDR-ZIP
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrZip.lowValues();
      // COB(1365): ELSE
    } else {
      // COB(1366): MOVE ACSZIPCI OF CACTUPAI TO ACUP-NEW-CUST-ADDR-ZIP
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrZip.setValue(
          ws.coactup.cactupai.acszipci);
      // COB(1367): END-IF
    }
    // COB(1369): IF  ACSPH1AI OF CACTUPAI = '*'
    // COB(1369): OR  ACSPH1AI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsph1ai.equals("*") || ws.coactup.cactupai.acsph1ai.isSpaces()) {
      // COB(1371): MOVE LOW-VALUES           TO ACUP-NEW-CUST-PHONE-NUM-1A
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
          .acupNewCustPhoneNum1a.lowValues();
      // COB(1372): ELSE
    } else {
      // COB(1373): MOVE ACSPH1AI OF CACTUPAI TO ACUP-NEW-CUST-PHONE-NUM-1A
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
          .acupNewCustPhoneNum1a.setValue(ws.coactup.cactupai.acsph1ai);
      // COB(1374): END-IF
    }
    // COB(1376): IF  ACSPH1BI OF CACTUPAI = '*'
    // COB(1376): OR  ACSPH1BI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsph1bi.equals("*") || ws.coactup.cactupai.acsph1bi.isSpaces()) {
      // COB(1378): MOVE LOW-VALUES           TO ACUP-NEW-CUST-PHONE-NUM-1B
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
          .acupNewCustPhoneNum1b.lowValues();
      // COB(1379): ELSE
    } else {
      // COB(1380): MOVE ACSPH1BI OF CACTUPAI TO ACUP-NEW-CUST-PHONE-NUM-1B
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
          .acupNewCustPhoneNum1b.setValue(ws.coactup.cactupai.acsph1bi);
      // COB(1381): END-IF
    }
    // COB(1383): IF  ACSPH1CI OF CACTUPAI = '*'
    // COB(1383): OR  ACSPH1CI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsph1ci.equals("*") || ws.coactup.cactupai.acsph1ci.isSpaces()) {
      // COB(1385): MOVE LOW-VALUES           TO ACUP-NEW-CUST-PHONE-NUM-1C
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
          .acupNewCustPhoneNum1c.lowValues();
      // COB(1386): ELSE
    } else {
      // COB(1387): MOVE ACSPH1CI OF CACTUPAI TO ACUP-NEW-CUST-PHONE-NUM-1C
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1X
          .acupNewCustPhoneNum1c.setValue(ws.coactup.cactupai.acsph1ci);
      // COB(1388): END-IF
    }
    // COB(1390): IF  ACSPH2AI OF CACTUPAI = '*'
    // COB(1390): OR  ACSPH2AI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsph2ai.equals("*") || ws.coactup.cactupai.acsph2ai.isSpaces()) {
      // COB(1392): MOVE LOW-VALUES           TO ACUP-NEW-CUST-PHONE-NUM-2A
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
          .acupNewCustPhoneNum2a.lowValues();
      // COB(1393): ELSE
    } else {
      // COB(1394): MOVE ACSPH2AI OF CACTUPAI TO ACUP-NEW-CUST-PHONE-NUM-2A
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
          .acupNewCustPhoneNum2a.setValue(ws.coactup.cactupai.acsph2ai);
      // COB(1395): END-IF
    }
    // COB(1397): IF  ACSPH2BI OF CACTUPAI = '*'
    // COB(1397): OR  ACSPH2BI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsph2bi.equals("*") || ws.coactup.cactupai.acsph2bi.isSpaces()) {
      // COB(1399): MOVE LOW-VALUES           TO ACUP-NEW-CUST-PHONE-NUM-2B
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
          .acupNewCustPhoneNum2b.lowValues();
      // COB(1400): ELSE
    } else {
      // COB(1401): MOVE ACSPH2BI OF CACTUPAI TO ACUP-NEW-CUST-PHONE-NUM-2B
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
          .acupNewCustPhoneNum2b.setValue(ws.coactup.cactupai.acsph2bi);
      // COB(1402): END-IF
    }
    // COB(1404): IF  ACSPH2CI OF CACTUPAI = '*'
    // COB(1404): OR  ACSPH2CI OF CACTUPAI = SPACES
    if (ws.coactup.cactupai.acsph2ci.equals("*") || ws.coactup.cactupai.acsph2ci.isSpaces()) {
      // COB(1406): MOVE LOW-VALUES           TO ACUP-NEW-CUST-PHONE-NUM-2C
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
          .acupNewCustPhoneNum2c.lowValues();
      // COB(1407): ELSE
    } else {
      // COB(1408): MOVE ACSPH2CI OF CACTUPAI TO ACUP-NEW-CUST-PHONE-NUM-2C
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2X
          .acupNewCustPhoneNum2c.setValue(ws.coactup.cactupai.acsph2ci);
      // COB(1409): END-IF
    }
    // COB(1413): IF  ACSGOVTI OF CACTUPAI = '*'
    // COB(1413): OR  ACSGOVTI OF CACTUPAI = SPACES
    //       *
    //       *Government Id
    //       *
    if (ws.coactup.cactupai.acsgovti.equals("*") || ws.coactup.cactupai.acsgovti.isSpaces()) {
      // COB(1415): MOVE LOW-VALUES           TO ACUP-NEW-CUST-GOVT-ISSUED-ID
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustGovtIssuedId.lowValues();
      // COB(1416): ELSE
    } else {
      // COB(1417): MOVE ACSGOVTI OF CACTUPAI TO ACUP-NEW-CUST-GOVT-ISSUED-ID
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustGovtIssuedId.setValue(
          ws.coactup.cactupai.acsgovti);
      // COB(1418): END-IF
    }
    // COB(1422): IF  ACSEFTCI OF CACTUPAI = '*'
    // COB(1422): OR  ACSEFTCI OF CACTUPAI = SPACES
    //       *
    //       *EFT Code
    //       *
    if (ws.coactup.cactupai.acseftci.equals("*") || ws.coactup.cactupai.acseftci.isSpaces()) {
      // COB(1424): MOVE LOW-VALUES           TO ACUP-NEW-CUST-EFT-ACCOUNT-ID
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustEftAccountId.lowValues();
      // COB(1425): ELSE
    } else {
      // COB(1426): MOVE ACSEFTCI OF CACTUPAI TO ACUP-NEW-CUST-EFT-ACCOUNT-ID
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustEftAccountId.setValue(
          ws.coactup.cactupai.acseftci);
      // COB(1427): END-IF
    }
    // COB(1431): IF  ACSPFLGI OF CACTUPAI = '*'
    // COB(1431): OR  ACSPFLGI OF CACTUPAI = SPACES
    //       *
    //       *Primary Holder Indicator
    //       *
    if (ws.coactup.cactupai.acspflgi.equals("*") || ws.coactup.cactupai.acspflgi.isSpaces()) {
      // COB(1433): MOVE LOW-VALUES            TO ACUP-NEW-CUST-PRI-HOLDER-IND
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPriHolderInd.lowValues();
      // COB(1434): ELSE
    } else {
      // COB(1435): MOVE ACSPFLGI OF CACTUPAI  TO ACUP-NEW-CUST-PRI-HOLDER-IND
      ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPriHolderInd.setValue(
          ws.coactup.cactupai.acspflgi);
      // COB(1436): END-IF
    }
  }

  protected void _1200EditMapInputs() {
    // COB(1443): SET INPUT-OK                  TO TRUE
    ws.wsMiscStorage.setInputOk(true);
    // COB(1445): IF  ACUP-DETAILS-NOT-FETCHED
    if (ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched()) {
      // COB(1447): PERFORM 1210-EDIT-ACCOUNT
      // COB(1447):    THRU 1210-EDIT-ACCOUNT-EXIT
      //       *        VALIDATE THE SEARCH KEYS
      _1210EditAccount();
      // COB(1450): MOVE LOW-VALUES           TO ACUP-OLD-ACCT-DATA
      ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.lowValues();
      // COB(1453): IF  FLG-ACCTFILTER-BLANK
      //       *
      //       *       IF THE SEARCH CONDITIONS HAVE PROBLEMS FLAG THEM
      if (ws.wsMiscStorage.flgAcctfilterBlank()) {
        // COB(1454): SET NO-SEARCH-CRITERIA-RECEIVED TO TRUE
        ws.wsMiscStorage.setNoSearchCriteriaReceived(true);
        // COB(1455): END-IF
      }
      // COB(1458): GO TO 1200-EDIT-MAP-INPUTS-EXIT
      //       *
      //       *       AT THIS STAGE. NO DETAILS FETCHED. NOTHING MORE TO EDIT.
      return;
      // COB(1459): ELSE
    } else {
      // COB(1460): CONTINUE
      // do nothing
      // COB(1461): END-IF
    }
    // COB(1464): SET FOUND-ACCOUNT-DATA        TO TRUE
    //       *
    //       *    SEARCH KEYS ALREADY VALIDATED AND DATA FETCHED
    ws.wsMiscStorage.setFoundAccountData(true);
    // COB(1465): SET FOUND-ACCT-IN-MASTER      TO TRUE
    ws.wsMiscStorage.wsFileReadFlags.setFoundAcctInMaster(true);
    // COB(1466): SET FLG-ACCTFILTER-ISVALID    TO TRUE
    ws.wsMiscStorage.setFlgAcctfilterIsvalid(true);
    // COB(1468): SET FOUND-CUST-IN-MASTER      TO TRUE
    ws.wsMiscStorage.wsFileReadFlags.setFoundCustInMaster(true);
    // COB(1469): SET FLG-CUSTFILTER-ISVALID    TO TRUE
    ws.wsMiscStorage.setFlgCustfilterIsvalid(true);
    // COB(1472): PERFORM 1205-COMPARE-OLD-NEW
    // COB(1472):    THRU 1205-COMPARE-OLD-NEW-EXIT
    //       *
    //       *
    _1205CompareOldNew();
    // COB(1475): IF  NO-CHANGES-FOUND
    // COB(1475): OR  ACUP-CHANGES-OK-NOT-CONFIRMED
    // COB(1475): OR  ACUP-CHANGES-OKAYED-AND-DONE
    if (ws.wsMiscStorage.noChangesFound()
        || ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkNotConfirmed()
        || ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedAndDone()) {
      // COB(1478): MOVE LOW-VALUES           TO WS-NON-KEY-FLAGS
      ws.wsMiscStorage.wsNonKeyFlags.lowValues();
      // COB(1479): GO TO 1200-EDIT-MAP-INPUTS-EXIT
      return;
      // COB(1480): END-IF
    }
    // COB(1482): SET ACUP-CHANGES-NOT-OK       TO TRUE
    ws.wsThisProgcommarea.acctUpdateScreenData.setAcupChangesNotOk(true);
    // COB(1484): MOVE 'Account Status'          TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Account Status");
    // COB(1485): MOVE ACUP-NEW-ACTIVE-STATUS    TO WS-EDIT-YES-NO
    ws.wsMiscStorage.wsGenericEdits.wsEditYesNo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewActiveStatus);
    // COB(1486): PERFORM 1220-EDIT-YESNO
    // COB(1486):    THRU 1220-EDIT-YESNO-EXIT
    _1220EditYesno();
    // COB(1488): MOVE WS-EDIT-YES-NO            TO WS-EDIT-ACCT-STATUS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAcctStatus.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditYesNo);
    // COB(1490): MOVE 'Open Date'              TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Open Date");
    // COB(1491): MOVE ACUP-NEW-OPEN-DATE       TO WS-EDIT-DATE-CCYYMMDD
    ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDate);
    // COB(1492): PERFORM EDIT-DATE-CCYYMMDD
    // COB(1492):    THRU EDIT-DATE-CCYYMMDD-EXIT
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
    // COB(1494): MOVE WS-EDIT-DATE-FLGS        TO WS-EDIT-OPEN-DATE-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.setValue(
        ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs);
    // COB(1496): MOVE 'Credit Limit'           TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Credit Limit");
    // COB(1497): MOVE ACUP-NEW-CREDIT-LIMIT-X  TO WS-EDIT-SIGNED-NUMBER-9V2-X
    ws.wsMiscStorage.wsGenericEdits.wsEditSignedNumber9v2X.setValue(
        ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCreditLimitX);
    // COB(1498): PERFORM 1250-EDIT-SIGNED-9V2
    // COB(1498):    THRU 1250-EDIT-SIGNED-9V2-EXIT
    _1250EditSigned9v2();
    // COB(1500): MOVE WS-FLG-SIGNED-NUMBER-EDIT  TO WS-EDIT-CREDIT-LIMIT
    ws.wsMiscStorage.wsNonKeyFlags.wsEditCreditLimit.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsFlgSignedNumberEdit);
    // COB(1502): MOVE 'Expiry Date'            TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Expiry Date");
    // COB(1503): MOVE ACUP-NEW-EXPIRAION-DATE  TO WS-EDIT-DATE-CCYYMMDD
    ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewExpiraionDate);
    // COB(1504): PERFORM EDIT-DATE-CCYYMMDD
    // COB(1504):    THRU EDIT-DATE-CCYYMMDD-EXIT
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
    // COB(1506): MOVE WS-EDIT-DATE-FLGS        TO WS-EXPIRY-DATE-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.setValue(
        ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs);
    // COB(1508): MOVE 'Cash Credit Limit'      TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Cash Credit Limit");
    // COB(1509): MOVE ACUP-NEW-CASH-CREDIT-LIMIT-X
    // COB(1509):                               TO WS-EDIT-SIGNED-NUMBER-9V2-X
    ws.wsMiscStorage.wsGenericEdits.wsEditSignedNumber9v2X.setValue(
        ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCashCreditLimitX);
    // COB(1511): PERFORM 1250-EDIT-SIGNED-9V2
    // COB(1511):    THRU 1250-EDIT-SIGNED-9V2-EXIT
    _1250EditSigned9v2();
    // COB(1513): MOVE WS-FLG-SIGNED-NUMBER-EDIT TO WS-EDIT-CASH-CREDIT-LIMIT
    ws.wsMiscStorage.wsNonKeyFlags.wsEditCashCreditLimit.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsFlgSignedNumberEdit);
    // COB(1515): MOVE 'Reissue Date'           TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Reissue Date");
    // COB(1516): MOVE ACUP-NEW-REISSUE-DATE    TO WS-EDIT-DATE-CCYYMMDD
    ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewReissueDate);
    // COB(1517): PERFORM EDIT-DATE-CCYYMMDD
    // COB(1517):    THRU EDIT-DATE-CCYYMMDD-EXIT
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
    // COB(1519): MOVE WS-EDIT-DATE-FLGS        TO WS-EDIT-REISSUE-DATE-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.setValue(
        ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs);
    // COB(1521): MOVE 'Current Balance'        TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Current Balance");
    // COB(1522): MOVE ACUP-NEW-CURR-BAL-X      TO WS-EDIT-SIGNED-NUMBER-9V2-X
    ws.wsMiscStorage.wsGenericEdits.wsEditSignedNumber9v2X.setValue(
        ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrBalX);
    // COB(1523): PERFORM 1250-EDIT-SIGNED-9V2
    // COB(1523):    THRU 1250-EDIT-SIGNED-9V2-EXIT
    _1250EditSigned9v2();
    // COB(1525): MOVE WS-FLG-SIGNED-NUMBER-EDIT   TO WS-EDIT-CURR-BAL
    ws.wsMiscStorage.wsNonKeyFlags.wsEditCurrBal.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsFlgSignedNumberEdit);
    // COB(1527): MOVE 'Current Cycle Credit Limit' TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Current Cycle Credit Limit");
    // COB(1528): MOVE ACUP-NEW-CURR-CYC-CREDIT-X
    // COB(1528):                               TO WS-EDIT-SIGNED-NUMBER-9V2-X
    ws.wsMiscStorage.wsGenericEdits.wsEditSignedNumber9v2X.setValue(
        ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycCreditX);
    // COB(1530): PERFORM 1250-EDIT-SIGNED-9V2
    // COB(1530):    THRU 1250-EDIT-SIGNED-9V2-EXIT
    _1250EditSigned9v2();
    // COB(1532): MOVE WS-FLG-SIGNED-NUMBER-EDIT   TO WS-EDIT-CURR-CYC-CREDIT
    ws.wsMiscStorage.wsNonKeyFlags.wsEditCurrCycCredit.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsFlgSignedNumberEdit);
    // COB(1534): MOVE 'Current Cycle Debit Limit' TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Current Cycle Debit Limit");
    // COB(1535): MOVE ACUP-NEW-CURR-CYC-DEBIT-X
    // COB(1535):                               TO WS-EDIT-SIGNED-NUMBER-9V2-X
    ws.wsMiscStorage.wsGenericEdits.wsEditSignedNumber9v2X.setValue(
        ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycDebitX);
    // COB(1537): PERFORM 1250-EDIT-SIGNED-9V2
    // COB(1537):    THRU 1250-EDIT-SIGNED-9V2-EXIT
    _1250EditSigned9v2();
    // COB(1539): MOVE WS-FLG-SIGNED-NUMBER-EDIT   TO WS-EDIT-CURR-CYC-DEBIT
    ws.wsMiscStorage.wsNonKeyFlags.wsEditCurrCycDebit.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsFlgSignedNumberEdit);
    // COB(1541): MOVE 'SSN'                    TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("SSN");
    // COB(1542): PERFORM 1265-EDIT-US-SSN
    // COB(1542):    THRU 1265-EDIT-US-SSN-EXIT
    _1265EditUsSsn();
    // COB(1545): MOVE 'Date of Birth'          TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Date of Birth");
    // COB(1546): MOVE   ACUP-NEW-CUST-DOB-YYYY-MM-DD
    // COB(1546):                               TO WS-EDIT-DATE-CCYYMMDD
    ws.wsMiscStorage.wsCalculationVars.wsEditDateCcyymmdd.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobYyyyMmDd);
    // COB(1548): PERFORM EDIT-DATE-CCYYMMDD
    // COB(1548):    THRU EDIT-DATE-CCYYMMDD-EXIT
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
    // COB(1550): MOVE WS-EDIT-DATE-FLGS        TO WS-EDIT-DT-OF-BIRTH-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.setValue(
        ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs);
    // COB(1551): IF WS-EDIT-DT-OF-BIRTH-ISVALID
    if (ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.wsEditDtOfBirthIsvalid()) {
      // COB(1552): PERFORM  EDIT-DATE-OF-BIRTH
      // COB(1552):    THRU  EDIT-DATE-OF-BIRTH-EXIT
      editDateOfBirth();
      // COB(1554): MOVE WS-EDIT-DATE-FLGS    TO WS-EDIT-DT-OF-BIRTH-FLGS
      ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.setValue(
          ws.wsMiscStorage.wsCalculationVars.wsEditDateFlgs);
      // COB(1555): END-IF
    }
    // COB(1557): MOVE 'FICO Score'             TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("FICO Score");
    // COB(1558): MOVE ACUP-NEW-CUST-FICO-SCORE-X
    // COB(1558):                               TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFicoScoreX);
    // COB(1560): MOVE 3                        TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(3);
    // COB(1561): PERFORM 1245-EDIT-NUM-REQD
    // COB(1561):    THRU 1245-EDIT-NUM-REQD-EXIT
    _1245EditNumReqd();
    // COB(1563): MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
    // COB(1563):                               TO WS-EDIT-FICO-SCORE-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditFicoScoreFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);
    // COB(1565): IF FLG-FICO-SCORE-ISVALID
    if (ws.wsMiscStorage.wsNonKeyFlags.flgFicoScoreIsvalid()) {
      // COB(1566): PERFORM  1275-EDIT-FICO-SCORE
      // COB(1566):    THRU  1275-EDIT-FICO-SCORE-EXIT
      _1275EditFicoScore();
      // COB(1568): END-IF
    }
    // COB(1572): MOVE 'First Name'             TO WS-EDIT-VARIABLE-NAME
    //       ******************************************************************
    //       *    Edit names
    //       ******************************************************************
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("First Name");
    // COB(1573): MOVE ACUP-NEW-CUST-FIRST-NAME TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFirstName);
    // COB(1574): MOVE 25                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(25);
    // COB(1575): PERFORM 1225-EDIT-ALPHA-REQD
    // COB(1575):    THRU 1225-EDIT-ALPHA-REQD-EXIT
    _1225EditAlphaReqd();
    // COB(1577): MOVE WS-EDIT-ALPHA-ONLY-FLAGS
    // COB(1577):                               TO WS-EDIT-FIRST-NAME-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.wsEditFirstNameFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphaOnlyFlags);
    // COB(1580): MOVE 'Middle Name'            TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Middle Name");
    // COB(1581): MOVE ACUP-NEW-CUST-MIDDLE-NAME TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustMiddleName);
    // COB(1582): MOVE 25                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(25);
    // COB(1583): PERFORM 1235-EDIT-ALPHA-OPT
    // COB(1583):    THRU 1235-EDIT-ALPHA-OPT-EXIT
    _1235EditAlphaOpt();
    // COB(1585): MOVE WS-EDIT-ALPHA-ONLY-FLAGS
    // COB(1585):                               TO WS-EDIT-MIDDLE-NAME-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.wsEditMiddleNameFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphaOnlyFlags);
    // COB(1588): MOVE 'Last Name'              TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Last Name");
    // COB(1589): MOVE ACUP-NEW-CUST-LAST-NAME  TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustLastName);
    // COB(1590): MOVE 25                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(25);
    // COB(1591): PERFORM 1225-EDIT-ALPHA-REQD
    // COB(1591):    THRU 1225-EDIT-ALPHA-REQD-EXIT
    _1225EditAlphaReqd();
    // COB(1593): MOVE WS-EDIT-ALPHA-ONLY-FLAGS
    // COB(1593):                              TO WS-EDIT-LAST-NAME-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.wsEditLastNameFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphaOnlyFlags);
    // COB(1596): MOVE 'Address Line 1'         TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Address Line 1");
    // COB(1597): MOVE ACUP-NEW-CUST-ADDR-LINE-1 TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine1);
    // COB(1598): MOVE 50                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(50);
    // COB(1599): PERFORM 1215-EDIT-MANDATORY
    // COB(1599):    THRU 1215-EDIT-MANDATORY-EXIT
    _1215EditMandatory();
    // COB(1601): MOVE WS-EDIT-MANDATORY-FLAGS
    // COB(1601):                               TO WS-EDIT-ADDRESS-LINE-1-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditAddressLine1Flgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditMandatoryFlags);
    // COB(1604): MOVE 'State'                  TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("State");
    // COB(1605): MOVE ACUP-NEW-CUST-ADDR-STATE-CD TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrStateCd);
    // COB(1606): MOVE 2                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(2);
    // COB(1607): PERFORM 1225-EDIT-ALPHA-REQD
    // COB(1607):    THRU 1225-EDIT-ALPHA-REQD-EXIT
    _1225EditAlphaReqd();
    // COB(1609): MOVE WS-EDIT-ALPHA-ONLY-FLAGS
    // COB(1609):                               TO WS-EDIT-STATE-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditStateFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphaOnlyFlags);
    // COB(1611): IF FLG-ALPHA-ISVALID
    if (ws.wsMiscStorage.wsGenericEdits.flgAlphaIsvalid()) {
      // COB(1612): PERFORM 1270-EDIT-US-STATE-CD
      // COB(1612):    THRU 1270-EDIT-US-STATE-CD-EXIT
      _1270EditUsStateCd();
      // COB(1614): END-IF
    }
    // COB(1617): MOVE 'Zip'                    TO WS-EDIT-VARIABLE-NAME
    //       *
    //       *
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Zip");
    // COB(1618): MOVE ACUP-NEW-CUST-ADDR-ZIP   TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrZip);
    // COB(1619): MOVE 5                        TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(5);
    // COB(1620): PERFORM 1245-EDIT-NUM-REQD
    // COB(1620):    THRU 1245-EDIT-NUM-REQD-EXIT
    _1245EditNumReqd();
    // COB(1622): MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
    // COB(1622):                               TO WS-EDIT-ZIPCODE-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditZipcodeFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);
    // COB(1627): MOVE 'City'                   TO WS-EDIT-VARIABLE-NAME
    //       *
    //       *    Address Line 2 is optional
    //       *    MOVE 'Address Line 2'         TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("City");
    // COB(1628): MOVE ACUP-NEW-CUST-ADDR-LINE-3 TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine3);
    // COB(1629): MOVE 50                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(50);
    // COB(1630): PERFORM 1225-EDIT-ALPHA-REQD
    // COB(1630):    THRU 1225-EDIT-ALPHA-REQD-EXIT
    _1225EditAlphaReqd();
    // COB(1632): MOVE WS-EDIT-ALPHA-ONLY-FLAGS
    // COB(1632):                               TO WS-EDIT-CITY-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditCityFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphaOnlyFlags);
    // COB(1635): MOVE 'Country'                TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Country");
    // COB(1636): MOVE ACUP-NEW-CUST-ADDR-COUNTRY-CD
    // COB(1636):                              TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrCountryCd);
    // COB(1638): MOVE 3                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(3);
    // COB(1639): PERFORM 1225-EDIT-ALPHA-REQD
    // COB(1639):    THRU 1225-EDIT-ALPHA-REQD-EXIT
    _1225EditAlphaReqd();
    // COB(1641): MOVE WS-EDIT-ALPHA-ONLY-FLAGS
    // COB(1641):                               TO WS-EDIT-COUNTRY-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditCountryFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphaOnlyFlags);
    // COB(1644): MOVE 'Phone Number 1'         TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Phone Number 1");
    // COB(1645): MOVE ACUP-NEW-CUST-PHONE-NUM-1
    // COB(1645):                               TO WS-EDIT-US-PHONE-NUM
    ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNum.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum1);
    // COB(1647): PERFORM 1260-EDIT-US-PHONE-NUM
    // COB(1647):    THRU 1260-EDIT-US-PHONE-NUM-EXIT
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
    // COB(1649): MOVE WS-EDIT-US-PHONE-NUM-FLGS
    // COB(1649):                               TO  WS-EDIT-PHONE-NUM-1-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs);
    // COB(1652): MOVE 'Phone Number 2'         TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Phone Number 2");
    // COB(1653): MOVE ACUP-NEW-CUST-PHONE-NUM-2
    // COB(1653):                               TO WS-EDIT-US-PHONE-NUM
    ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNum.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPhoneNum2);
    // COB(1655): PERFORM 1260-EDIT-US-PHONE-NUM
    // COB(1655):    THRU 1260-EDIT-US-PHONE-NUM-EXIT
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
    // COB(1657): MOVE WS-EDIT-US-PHONE-NUM-FLGS
    // COB(1657):                               TO WS-EDIT-PHONE-NUM-2-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs);
    // COB(1660): MOVE 'EFT Account Id'         TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("EFT Account Id");
    // COB(1661): MOVE ACUP-NEW-CUST-EFT-ACCOUNT-ID
    // COB(1661):                               TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustEftAccountId);
    // COB(1663): MOVE 10                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(10);
    // COB(1664): PERFORM 1245-EDIT-NUM-REQD
    // COB(1664):    THRU 1245-EDIT-NUM-REQD-EXIT
    _1245EditNumReqd();
    // COB(1666): MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
    // COB(1666):                               TO WS-EFT-ACCOUNT-ID-FLGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEftAccountIdFlgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);
    // COB(1669): MOVE 'Primary Card Holder'    TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Primary Card Holder");
    // COB(1670): MOVE ACUP-NEW-CUST-PRI-HOLDER-IND
    // COB(1670):                               TO WS-EDIT-YES-NO
    ws.wsMiscStorage.wsGenericEdits.wsEditYesNo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPriHolderInd);
    // COB(1672): PERFORM 1220-EDIT-YESNO
    // COB(1672):    THRU 1220-EDIT-YESNO-EXIT
    _1220EditYesno();
    // COB(1674): MOVE WS-EDIT-YES-NO           TO WS-EDIT-PRI-CARDHOLDER
    ws.wsMiscStorage.wsNonKeyFlags.wsEditPriCardholder.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditYesNo);
    // COB(1677): IF  FLG-STATE-ISVALID
    // COB(1677): AND FLG-ZIPCODE-ISVALID
    //       *
    //       *    Cross field edits begin here
    if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgStateIsvalid()
        && ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgZipcodeIsvalid()) {
      // COB(1679): PERFORM 1280-EDIT-US-STATE-ZIP-CD
      // COB(1679):    THRU 1280-EDIT-US-STATE-ZIP-CD-EXIT
      _1280EditUsStateZipCd();
      // COB(1681): END-IF
    }
    // COB(1683): IF INPUT-ERROR
    if (ws.wsMiscStorage.inputError()) {
      // COB(1684): CONTINUE
      // do nothing
      // COB(1685): ELSE
    } else {
      // COB(1686): SET ACUP-CHANGES-OK-NOT-CONFIRMED TO TRUE
      ws.wsThisProgcommarea.acctUpdateScreenData.setAcupChangesOkNotConfirmed(true);
      // COB(1687): END-IF
    }
  }

  protected void _1205CompareOldNew() {
    // COB(1694): SET NO-CHANGES-FOUND           TO TRUE
    ws.wsMiscStorage.setNoChangesFound(true);
    // COB(1696): IF  ACUP-NEW-ACCT-ID-X         = ACUP-OLD-ACCT-ID-X
    // COB(1696): AND FUNCTION UPPER-CASE (
    // COB(1696):     ACUP-NEW-ACTIVE-STATUS)    =
    // COB(1696):     FUNCTION UPPER-CASE (
    // COB(1696):     ACUP-OLD-ACTIVE-STATUS)
    // COB(1696): AND ACUP-NEW-CURR-BAL          = ACUP-OLD-CURR-BAL
    // COB(1696): AND ACUP-NEW-CREDIT-LIMIT      = ACUP-OLD-CREDIT-LIMIT
    // COB(1696): AND ACUP-NEW-CASH-CREDIT-LIMIT = ACUP-OLD-CASH-CREDIT-LIMIT
    // COB(1696): AND ACUP-NEW-OPEN-DATE         = ACUP-OLD-OPEN-DATE
    // COB(1696): AND ACUP-NEW-EXPIRAION-DATE    = ACUP-OLD-EXPIRAION-DATE
    // COB(1696): AND ACUP-NEW-REISSUE-DATE      = ACUP-OLD-REISSUE-DATE
    // COB(1696): AND ACUP-NEW-CURR-CYC-CREDIT   = ACUP-OLD-CURR-CYC-CREDIT
    // COB(1696): AND ACUP-NEW-CURR-CYC-DEBIT    = ACUP-OLD-CURR-CYC-DEBIT
    // COB(1696): AND FUNCTION UPPER-CASE (
    // COB(1696):     FUNCTION TRIM (ACUP-NEW-GROUP-ID))=
    // COB(1696):     FUNCTION UPPER-CASE (
    // COB(1696):     FUNCTION TRIM (ACUP-OLD-GROUP-ID))
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
      // COB(1713): CONTINUE
      // do nothing
      // COB(1714): ELSE
    } else {
      // COB(1715): SET CHANGE-HAS-OCCURRED   TO TRUE
      ws.wsMiscStorage.setChangeHasOccurred(true);
      // COB(1716): GO TO 1205-COMPARE-OLD-NEW-EXIT
      return;
      // COB(1717): END-IF
    }
    // COB(1720): IF  FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-NEW-CUST-ID-X))=
    // COB(1720):     FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-OLD-CUST-ID-X))
    // COB(1720): AND FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-NEW-CUST-FIRST-NAME))=
    // COB(1720):     FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-OLD-CUST-FIRST-NAME))
    // COB(1720): AND FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-NEW-CUST-MIDDLE-NAME))=
    // COB(1720):     FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-OLD-CUST-MIDDLE-NAME))
    // COB(1720): AND FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-NEW-CUST-LAST-NAME))=
    // COB(1720):     FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-OLD-CUST-LAST-NAME))
    // COB(1720): AND FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-NEW-CUST-ADDR-LINE-1))=
    // COB(1720):     FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-OLD-CUST-ADDR-LINE-1))
    // COB(1720): AND FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-NEW-CUST-ADDR-LINE-2))=
    // COB(1720):     FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-OLD-CUST-ADDR-LINE-2))
    // COB(1720): AND FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-NEW-CUST-ADDR-LINE-3))=
    // COB(1720):     FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-OLD-CUST-ADDR-LINE-3))
    // COB(1720): AND FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-NEW-CUST-ADDR-STATE-CD))=
    // COB(1720):     FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-OLD-CUST-ADDR-STATE-CD))
    // COB(1720): AND FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-NEW-CUST-ADDR-COUNTRY-CD))=
    // COB(1720):     FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-OLD-CUST-ADDR-COUNTRY-CD))
    // COB(1720): AND FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-NEW-CUST-ADDR-ZIP))=
    // COB(1720):     FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-OLD-CUST-ADDR-ZIP))
    // COB(1720): AND ACUP-NEW-CUST-PHONE-NUM-1A = ACUP-OLD-CUST-PHONE-NUM-1A
    // COB(1720): AND ACUP-NEW-CUST-PHONE-NUM-1B = ACUP-OLD-CUST-PHONE-NUM-1B
    // COB(1720): AND ACUP-NEW-CUST-PHONE-NUM-1C = ACUP-OLD-CUST-PHONE-NUM-1C
    // COB(1720): AND ACUP-NEW-CUST-PHONE-NUM-2A = ACUP-OLD-CUST-PHONE-NUM-2A
    // COB(1720): AND ACUP-NEW-CUST-PHONE-NUM-2B = ACUP-OLD-CUST-PHONE-NUM-2B
    // COB(1720): AND ACUP-NEW-CUST-PHONE-NUM-2C = ACUP-OLD-CUST-PHONE-NUM-2C
    // COB(1720): AND ACUP-NEW-CUST-SSN-X       = ACUP-OLD-CUST-SSN-X
    // COB(1720): AND FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-NEW-CUST-GOVT-ISSUED-ID ))=
    // COB(1720):     FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-OLD-CUST-GOVT-ISSUED-ID))
    // COB(1720): AND ACUP-NEW-CUST-DOB-YYYY-MM-DD
    // COB(1720):                           = ACUP-OLD-CUST-DOB-YYYY-MM-DD
    // COB(1720): AND ACUP-NEW-CUST-EFT-ACCOUNT-ID
    // COB(1720):                           = ACUP-OLD-CUST-EFT-ACCOUNT-ID
    // COB(1720): AND FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-NEW-CUST-PRI-HOLDER-IND))=
    // COB(1720):     FUNCTION UPPER-CASE (
    // COB(1720):     FUNCTION TRIM (ACUP-OLD-CUST-PRI-HOLDER-IND))
    // COB(1720): AND ACUP-NEW-CUST-FICO-SCORE-X
    // COB(1720):                           = ACUP-OLD-CUST-FICO-SCORE-X
    //       *
    //       *
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
      // COB(1781): SET NO-CHANGES-DETECTED   TO TRUE
      ws.wsMiscStorage.setNoChangesDetected(true);
      // COB(1782): ELSE
    } else {
      // COB(1783): SET CHANGE-HAS-OCCURRED   TO TRUE
      ws.wsMiscStorage.setChangeHasOccurred(true);
      // COB(1784): GO TO 1205-COMPARE-OLD-NEW-EXIT
      return;
      // COB(1785): END-IF
    }
  }

  /***
   *
   */
  protected void _1210EditAccount() {
    // COB(1796): SET FLG-ACCTFILTER-NOT-OK    TO TRUE
    ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
    // COB(1799): IF CC-ACCT-ID   EQUAL LOW-VALUES
    // COB(1799): OR CC-ACCT-ID   EQUAL SPACES
    //       *
    //       *    Not supplied
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isLowValues()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isSpaces()) {
      // COB(1801): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1802): SET FLG-ACCTFILTER-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterBlank(true);
      // COB(1803): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1804): SET WS-PROMPT-FOR-ACCT TO TRUE
        ws.wsMiscStorage.setWsPromptForAcct(true);
        // COB(1805): END-IF
      }
      // COB(1806): MOVE ZEROES               TO CDEMO-ACCT-ID
      // COB(1806):                              ACUP-NEW-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
      ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewAcctId.zeros();
      // COB(1808): GO TO  1210-EDIT-ACCOUNT-EXIT
      return;
      // COB(1809): END-IF
    }
    // COB(1813): MOVE CC-ACCT-ID              TO ACUP-NEW-ACCT-ID
    //       *
    //       *    Not numeric
    //       *    Not 11 characters
    ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewAcctId.setValue(
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
    // COB(1814): IF CC-ACCT-ID   IS NOT NUMERIC
    // COB(1814): OR CC-ACCT-ID-N EQUAL ZEROS
    if (!ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isNumeric()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctIdN.equals(0)) {
      // COB(1816): SET INPUT-ERROR TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1817): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1818): STRING
        // COB(1818):  'Account Number if supplied must be a 11 digit'
        // COB(1818):  ' Non-Zero Number'
        // COB(1818): DELIMITED BY SIZE
        // COB(1818): INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            "Account Number if supplied must be a 11 digit", " Non-Zero Number");
        // COB(1823): END-IF
      }
      // COB(1824): MOVE ZEROES               TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
      // COB(1825): GO TO 1210-EDIT-ACCOUNT-EXIT
      return;
      // COB(1826): ELSE
    } else {
      // COB(1827): MOVE CC-ACCT-ID TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.setValue(
          ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
      // COB(1828): SET FLG-ACCTFILTER-ISVALID TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterIsvalid(true);
      // COB(1829): END-IF
    }
  }

  /***/
  protected void _1215EditMandatory() {
    // COB(1838): SET FLG-MANDATORY-NOT-OK    TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgMandatoryNotOk(true);
    // COB(1841): IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1841):                             EQUAL LOW-VALUES
    // COB(1841): OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1841):                             EQUAL SPACES
    // COB(1841): OR FUNCTION LENGTH(FUNCTION TRIM(
    // COB(1841):    WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
    //       *
    //       *    Not supplied
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
      // COB(1848): SET INPUT-ERROR          TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1849): SET FLG-MANDATORY-BLANK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgMandatoryBlank(true);
      // COB(1850): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1851): STRING
        // COB(1851):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(1851):   ' must be supplied.'
        // COB(1851):   DELIMITED BY SIZE
        // COB(1851):   INTO WS-RETURN-MSG
        // COB(1851): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        // COB(1856): END-STRING
        // COB(1857): END-IF
      }
      // COB(1859): GO TO  1215-EDIT-MANDATORY-EXIT
      return;
      // COB(1860): END-IF
    }
    // COB(1862): SET FLG-MANDATORY-ISVALID   TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgMandatoryIsvalid(true);
  }

  /***/
  protected void _1220EditYesno() {
    // COB(1873): IF WS-EDIT-YES-NO             EQUAL LOW-VALUES
    // COB(1873): OR WS-EDIT-YES-NO             EQUAL SPACES
    // COB(1873): OR WS-EDIT-YES-NO             EQUAL ZEROS
    if (ws.wsMiscStorage.wsGenericEdits.wsEditYesNo.isLowValues()
        || ws.wsMiscStorage.wsGenericEdits.wsEditYesNo.isSpaces()
        || ws.wsMiscStorage.wsGenericEdits.wsEditYesNo.equals(0)) {
      // COB(1876): SET INPUT-ERROR            TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1877): SET FLG-YES-NO-BLANK       TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgYesNoBlank(true);
      // COB(1878): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1879): STRING
        // COB(1879):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(1879):   ' must be supplied.'
        // COB(1879):   DELIMITED BY SIZE
        // COB(1879):   INTO WS-RETURN-MSG
        // COB(1879): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        // COB(1884): END-STRING
        // COB(1885): END-IF
      }
      // COB(1886): GO TO  1220-EDIT-YESNO-EXIT
      return;
      // COB(1887): END-IF
    }
    // COB(1890): IF FLG-YES-NO-ISVALID
    //       *
    //       *
    if (ws.wsMiscStorage.wsGenericEdits.flgYesNoIsvalid()) {
      // COB(1891): CONTINUE
      // do nothing
      // COB(1892): ELSE
    } else {
      // COB(1893): SET INPUT-ERROR             TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1894): SET FLG-YES-NO-NOT-OK       TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgYesNoNotOk(true);
      // COB(1895): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1896): STRING
        // COB(1896):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(1896):   ' must be Y or N.'
        // COB(1896):   DELIMITED BY SIZE
        // COB(1896):   INTO WS-RETURN-MSG
        // COB(1896): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be Y or N.");
        // COB(1901): END-STRING
        // COB(1902): END-IF
      }
      // COB(1903): GO TO  1220-EDIT-YESNO-EXIT
      return;
      // COB(1904): END-IF
    }
  }

  /***/
  protected void _1225EditAlphaReqd() {
    // COB(1912): SET FLG-ALPHA-NOT-OK              TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphaNotOk(true);
    // COB(1915): IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1915):                                   EQUAL LOW-VALUES
    // COB(1915): OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1915):     EQUAL SPACES
    // COB(1915): OR FUNCTION LENGTH(FUNCTION TRIM(
    // COB(1915):    WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
    //       *
    //       *    Not supplied
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
      // COB(1922): SET INPUT-ERROR                TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1923): SET FLG-ALPHA-BLANK            TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphaBlank(true);
      // COB(1924): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1925): STRING
        // COB(1925):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(1925):   ' must be supplied.'
        // COB(1925):   DELIMITED BY SIZE
        // COB(1925):   INTO WS-RETURN-MSG
        // COB(1925): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        // COB(1930): END-STRING
        // COB(1931): END-IF
      }
      // COB(1933): GO TO  1225-EDIT-ALPHA-REQD-EXIT
      return;
      // COB(1934): END-IF
    }
    // COB(1937): MOVE LIT-ALL-ALPHA-FROM-X   TO LIT-ALL-ALPHA-FROM
    //       *
    //       *    Only Alphabets and space allowed
    ws.litAllAlphaFrom.setValue(ws.wsLiterals.litAllAlphanumFromX.litAllAlphaFromX);
    // COB(1938): INSPECT WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1938):   CONVERTING LIT-ALL-ALPHA-FROM
    // COB(1938):           TO LIT-ALPHA-SPACES-TO
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.inspectAndConvertCharacters(
        ws.litAllAlphaFrom.getValue(),
        ws.litAlphaSpacesTo.getValue(),
        0,
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt());
    // COB(1942): IF FUNCTION LENGTH(
    // COB(1942):         FUNCTION TRIM(
    // COB(1942):         WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1942):                        )) = 0
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditAlphanumOnly
        .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
        .trim()
        .length()
        .equals(0)) {
      // COB(1946): CONTINUE
      // do nothing
      // COB(1947): ELSE
    } else {
      // COB(1948): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1949): SET FLG-ALPHA-NOT-OK      TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphaNotOk(true);
      // COB(1950): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1951): STRING
        // COB(1951):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(1951):   ' can have alphabets only.'
        // COB(1951):   DELIMITED BY SIZE
        // COB(1951):   INTO WS-RETURN-MSG
        // COB(1951): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " can have alphabets only.");
        // COB(1956): END-STRING
        // COB(1957): END-IF
      }
      // COB(1958): GO TO  1225-EDIT-ALPHA-REQD-EXIT
      return;
      // COB(1959): END-IF
    }
    // COB(1961): SET FLG-ALPHA-ISVALID        TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphaIsvalid(true);
  }

  /***/
  protected void _1230EditAlphanumReqd() {
    // COB(1969): SET FLG-ALPHNANUM-NOT-OK          TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
    // COB(1972): IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1972):                                   EQUAL LOW-VALUES
    // COB(1972): OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1972):     EQUAL SPACES
    // COB(1972): OR FUNCTION LENGTH(FUNCTION TRIM(
    // COB(1972):    WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
    //       *
    //       *    Not supplied
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
      // COB(1979): SET INPUT-ERROR                TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1980): SET FLG-ALPHNANUM-BLANK        TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumBlank(true);
      // COB(1981): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1982): STRING
        // COB(1982):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(1982):   ' must be supplied.'
        // COB(1982):   DELIMITED BY SIZE
        // COB(1982):   INTO WS-RETURN-MSG
        // COB(1982): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        // COB(1987): END-STRING
        // COB(1988): END-IF
      }
      // COB(1990): GO TO  1230-EDIT-ALPHANUM-REQD-EXIT
      return;
      // COB(1991): END-IF
    }
    // COB(1994): MOVE LIT-ALL-ALPHANUM-FROM-X TO LIT-ALL-ALPHANUM-FROM
    //       *
    //       *    Only Alphabets,numbers and space allowed
    ws.litAllAlphanumFrom.setValue(ws.wsLiterals.litAllAlphanumFromX);
    // COB(1996): INSPECT WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(1996):   CONVERTING LIT-ALL-ALPHANUM-FROM
    // COB(1996):           TO LIT-ALPHANUM-SPACES-TO
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.inspectAndConvertCharacters(
        ws.litAllAlphanumFrom.getValue(),
        ws.litAlphanumSpacesTo.getValue(),
        0,
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt());
    // COB(2000): IF FUNCTION LENGTH(
    // COB(2000):         FUNCTION TRIM(
    // COB(2000):         WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2000):                        )) = 0
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditAlphanumOnly
        .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
        .trim()
        .length()
        .equals(0)) {
      // COB(2004): CONTINUE
      // do nothing
      // COB(2005): ELSE
    } else {
      // COB(2006): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2007): SET FLG-ALPHNANUM-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
      // COB(2008): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2009): STRING
        // COB(2009):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2009):   ' can have numbers or alphabets only.'
        // COB(2009):   DELIMITED BY SIZE
        // COB(2009):   INTO WS-RETURN-MSG
        // COB(2009): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            " can have numbers or alphabets only.");
        // COB(2014): END-STRING
        // COB(2015): END-IF
      }
      // COB(2016): GO TO  1230-EDIT-ALPHANUM-REQD-EXIT
      return;
      // COB(2017): END-IF
    }
    // COB(2019): SET FLG-ALPHNANUM-ISVALID    TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumIsvalid(true);
  }

  protected void _1235EditAlphaOpt() {
    // COB(2026): SET FLG-ALPHA-NOT-OK              TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphaNotOk(true);
    // COB(2029): IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2029):                                   EQUAL LOW-VALUES
    // COB(2029): OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2029):     EQUAL SPACES
    // COB(2029): OR FUNCTION LENGTH(FUNCTION TRIM(
    // COB(2029):    WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
    //       *
    //       *    Not supplied
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
      // COB(2036): SET FLG-ALPHA-ISVALID          TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphaIsvalid(true);
      // COB(2037): GO TO  1235-EDIT-ALPHA-OPT-EXIT
      return;
      // COB(2038): ELSE
    } else {
      // COB(2039): CONTINUE
      // do nothing
      // COB(2040): END-IF
    }
    // COB(2043): MOVE LIT-ALL-ALPHA-FROM-X    TO LIT-ALL-ALPHA-FROM
    //       *
    //       *    Only Alphabets and space allowed
    ws.litAllAlphaFrom.setValue(ws.wsLiterals.litAllAlphanumFromX.litAllAlphaFromX);
    // COB(2044): INSPECT WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2044):   CONVERTING LIT-ALL-ALPHA-FROM
    // COB(2044):           TO LIT-ALPHA-SPACES-TO
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.inspectAndConvertCharacters(
        ws.litAllAlphaFrom.getValue(),
        ws.litAlphaSpacesTo.getValue(),
        0,
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt());
    // COB(2048): IF FUNCTION LENGTH(
    // COB(2048):         FUNCTION TRIM(
    // COB(2048):         WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2048):                        )) = 0
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditAlphanumOnly
        .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
        .trim()
        .length()
        .equals(0)) {
      // COB(2052): CONTINUE
      // do nothing
      // COB(2053): ELSE
    } else {
      // COB(2054): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2055): SET FLG-ALPHA-NOT-OK      TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphaNotOk(true);
      // COB(2056): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2057): STRING
        // COB(2057):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2057):   ' can have alphabets only.'
        // COB(2057):   DELIMITED BY SIZE
        // COB(2057):   INTO WS-RETURN-MSG
        // COB(2057): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " can have alphabets only.");
        // COB(2062): END-STRING
        // COB(2063): END-IF
      }
      // COB(2064): GO TO  1235-EDIT-ALPHA-OPT-EXIT
      return;
      // COB(2065): END-IF
    }
    // COB(2067): SET FLG-ALPHA-ISVALID        TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphaIsvalid(true);
  }

  /***/
  protected void _1240EditAlphanumOpt() {
    // COB(2075): SET FLG-ALPHNANUM-NOT-OK          TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
    // COB(2078): IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2078):                                   EQUAL LOW-VALUES
    // COB(2078): OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2078):     EQUAL SPACES
    // COB(2078): OR FUNCTION LENGTH(FUNCTION TRIM(
    // COB(2078):    WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
    //       *
    //       *    Not supplied, but ok as optional
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
      // COB(2084): SET FLG-ALPHNANUM-ISVALID     TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumIsvalid(true);
      // COB(2085): GO TO  1240-EDIT-ALPHANUM-OPT-EXIT
      return;
      // COB(2086): ELSE
    } else {
      // COB(2087): CONTINUE
      // do nothing
      // COB(2088): END-IF
    }
    // COB(2091): MOVE LIT-ALL-ALPHANUM-FROM-X TO LIT-ALL-ALPHANUM-FROM
    //       *
    //       *    Only Alphabets and space allowed
    ws.litAllAlphanumFrom.setValue(ws.wsLiterals.litAllAlphanumFromX);
    // COB(2092): INSPECT WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2092):   CONVERTING LIT-ALL-ALPHANUM-FROM
    // COB(2092):           TO LIT-ALPHANUM-SPACES-TO
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.inspectAndConvertCharacters(
        ws.litAllAlphanumFrom.getValue(),
        ws.litAlphanumSpacesTo.getValue(),
        0,
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt());
    // COB(2096): IF FUNCTION LENGTH(
    // COB(2096):         FUNCTION TRIM(
    // COB(2096):         WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2096):                        )) = 0
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditAlphanumOnly
        .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
        .trim()
        .length()
        .equals(0)) {
      // COB(2100): CONTINUE
      // do nothing
      // COB(2101): ELSE
    } else {
      // COB(2102): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2103): SET FLG-ALPHNANUM-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
      // COB(2104): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2105): STRING
        // COB(2105):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2105):   ' can have numbers or alphabets only.'
        // COB(2105):   DELIMITED BY SIZE
        // COB(2105):   INTO WS-RETURN-MSG
        // COB(2105): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            " can have numbers or alphabets only.");
        // COB(2110): END-STRING
        // COB(2111): END-IF
      }
      // COB(2112): GO TO  1240-EDIT-ALPHANUM-OPT-EXIT
      return;
      // COB(2113): END-IF
    }
    // COB(2115): SET FLG-ALPHNANUM-ISVALID    TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumIsvalid(true);
  }

  /***/
  protected void _1245EditNumReqd() {
    // COB(2123): SET FLG-ALPHNANUM-NOT-OK          TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
    // COB(2126): IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2126):                                   EQUAL LOW-VALUES
    // COB(2126): OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2126):     EQUAL SPACES
    // COB(2126): OR FUNCTION LENGTH(FUNCTION TRIM(
    // COB(2126):    WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
    //       *
    //       *    Not supplied
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
      // COB(2133): SET INPUT-ERROR                TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2134): SET FLG-ALPHNANUM-BLANK        TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumBlank(true);
      // COB(2135): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2136): STRING
        // COB(2136):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2136):   ' must be supplied.'
        // COB(2136):   DELIMITED BY SIZE
        // COB(2136):   INTO WS-RETURN-MSG
        // COB(2136): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        // COB(2141): END-STRING
        // COB(2142): END-IF
      }
      // COB(2144): GO TO  1245-EDIT-NUM-REQD-EXIT
      return;
      // COB(2145): END-IF
    }
    // COB(2149): IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(2149):        IS NUMERIC
    //       *
    //       *    Only all numeric allowed
    //       *
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditAlphanumOnly
        .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
        .isNumeric()) {
      // COB(2151): CONTINUE
      // do nothing
      // COB(2152): ELSE
    } else {
      // COB(2153): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2154): SET FLG-ALPHNANUM-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
      // COB(2155): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2156): STRING
        // COB(2156):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2156):   ' must be all numeric.'
        // COB(2156):   DELIMITED BY SIZE
        // COB(2156):   INTO WS-RETURN-MSG
        // COB(2156): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be all numeric.");
        // COB(2161): END-STRING
        // COB(2162): END-IF
      }
      // COB(2163): GO TO  1245-EDIT-NUM-REQD-EXIT
      return;
      // COB(2164): END-IF
    }
    // COB(2168): IF FUNCTION NUMVAL(WS-EDIT-ALPHANUM-ONLY(1:
    // COB(2168):                    WS-EDIT-ALPHANUM-LENGTH)) = 0
    //       *
    //       *    Must not be zero
    //       *
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditAlphanumOnly
        .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
        .convertToNumeric()
        .equals(0)) {
      // COB(2170): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2171): SET FLG-ALPHNANUM-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
      // COB(2172): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2173): STRING
        // COB(2173):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2173):   ' must not be zero.'
        // COB(2173):   DELIMITED BY SIZE
        // COB(2173):   INTO WS-RETURN-MSG
        // COB(2173): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must not be zero.");
        // COB(2178): END-STRING
        // COB(2179): END-IF
      }
      // COB(2180): GO TO  1245-EDIT-NUM-REQD-EXIT
      return;
      // COB(2181): ELSE
    } else {
      // COB(2182): CONTINUE
      // do nothing
      // COB(2183): END-IF
    }
    // COB(2186): SET FLG-ALPHNANUM-ISVALID    TO TRUE
    //       *
    //       *
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumIsvalid(true);
  }

  /***/
  protected void _1250EditSigned9v2() {
    // COB(2193): SET FLG-SIGNED-NUMBER-NOT-OK    TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgSignedNumberNotOk(true);
    // COB(2196): IF WS-EDIT-SIGNED-NUMBER-9V2-X  EQUAL LOW-VALUES
    // COB(2196): OR WS-EDIT-SIGNED-NUMBER-9V2-X  EQUAL SPACES
    //       *
    //       *    Not supplied
    if (ws.wsMiscStorage.wsGenericEdits.wsEditSignedNumber9v2X.isLowValues()
        || ws.wsMiscStorage.wsGenericEdits.wsEditSignedNumber9v2X.isSpaces()) {
      // COB(2198): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2199): SET FLG-SIGNED-NUMBER-BLANK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgSignedNumberBlank(true);
      // COB(2200): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2201): STRING
        // COB(2201):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2201):   ' must be supplied.'
        // COB(2201):   DELIMITED BY SIZE
        // COB(2201):   INTO WS-RETURN-MSG
        // COB(2201): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        // COB(2206): END-STRING
        // COB(2207): END-IF
      }
      // COB(2208): GO TO  1250-EDIT-SIGNED-9V2-EXIT
      return;
      // COB(2209): ELSE
    } else {
      // COB(2210): CONTINUE
      // do nothing
      // COB(2211): END-IF
    }
    // COB(2213): IF FUNCTION TEST-NUMVAL-C(WS-EDIT-SIGNED-NUMBER-9V2-X) = 0
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditSignedNumber9v2X
        .testConvertCurrencyToNumeric()
        .equals(0)) {
      // COB(2214): CONTINUE
      // do nothing
      // COB(2215): ELSE
    } else {
      // COB(2216): SET INPUT-ERROR             TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2217): SET FLG-SIGNED-NUMBER-NOT-OK   TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgSignedNumberNotOk(true);
      // COB(2218): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2219): STRING
        // COB(2219):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2219):   ' is not valid'
        // COB(2219):   DELIMITED BY SIZE
        // COB(2219):   INTO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " is not valid");
        // COB(2224): END-IF
      }
      // COB(2225): GO TO  1250-EDIT-SIGNED-9V2-EXIT
      return;
      // COB(2227): END-IF
    }
    // COB(2230): SET FLG-SIGNED-NUMBER-ISVALID  TO TRUE
    //       *
    //       *    If we got here all edits were cleared
    ws.wsMiscStorage.wsGenericEdits.setFlgSignedNumberIsvalid(true);
  }

  /***/
  protected Flow _1260EditUsPhoneNum() {
    // COB(2244): SET WS-EDIT-US-PHONE-IS-INVALID TO TRUE
    ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setWsEditUsPhoneIsInvalid(true);
    // COB(2246): IF  (WS-EDIT-US-PHONE-NUMA EQUAL SPACES
    // COB(2246): OR   WS-EDIT-US-PHONE-NUMA EQUAL LOW-VALUES)
    // COB(2246): AND (WS-EDIT-US-PHONE-NUMB EQUAL SPACES
    // COB(2246): OR   WS-EDIT-US-PHONE-NUMB EQUAL LOW-VALUES)
    // COB(2246): AND (WS-EDIT-US-PHONE-NUMA EQUAL SPACES
    // COB(2246): OR   WS-EDIT-US-PHONE-NUMC EQUAL LOW-VALUES)
    //       *    Not mandatory to enter a phone number
    if ((ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNuma.isSpaces()
            || ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNuma.isLowValues())
        && (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumb.isSpaces()
            || ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumb.isLowValues())
        && (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNuma.isSpaces()
            || ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumc.isLowValues())) {
      // COB(2252): SET WS-EDIT-US-PHONE-IS-VALID TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setWsEditUsPhoneIsValid(true);
      // COB(2253): GO TO EDIT-US-PHONE-EXIT
      return Flow.editUsPhoneExit;
      // COB(2254): ELSE
    } else {
      // COB(2255): CONTINUE
      // do nothing
      // COB(2256): END-IF
    }
    return Flow.Exit;
  }

  protected Flow editAreaCode() {
    // COB(2259): IF WS-EDIT-US-PHONE-NUMA EQUAL SPACES
    // COB(2259): OR WS-EDIT-US-PHONE-NUMA EQUAL LOW-VALUES
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNuma.isSpaces()
        || ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNuma.isLowValues()) {
      // COB(2261): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2262): SET FLG-EDIT-US-PHONEA-BLANK    TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhoneaBlank(true);
      // COB(2263): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2264): STRING
        // COB(2264):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2264):   ': Area code must be supplied.'
        // COB(2264):   DELIMITED BY SIZE
        // COB(2264):   INTO WS-RETURN-MSG
        // COB(2264): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Area code must be supplied.");
        // COB(2269): END-STRING
        // COB(2270): END-IF
      }
      // COB(2271): GO TO  EDIT-US-PHONE-PREFIX
      return Flow.editUsPhonePrefix;
      // COB(2272): ELSE
    } else {
      // COB(2273): CONTINUE
      // do nothing
      // COB(2274): END-IF
    }
    // COB(2276): IF  WS-EDIT-US-PHONE-NUMA       IS NUMERIC
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNuma.isNumeric()) {
      // COB(2277): CONTINUE
      // do nothing
      // COB(2278): ELSE
    } else {
      // COB(2279): SET INPUT-ERROR                 TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2280): SET  FLG-EDIT-US-PHONEA-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhoneaNotOk(true);
      // COB(2281): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2282): STRING
        // COB(2282):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2282):   ': Area code must be A 3 digit number.'
        // COB(2282):   DELIMITED BY SIZE
        // COB(2282):   INTO WS-RETURN-MSG
        // COB(2282): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Area code must be A 3 digit number.");
        // COB(2287): END-STRING
        // COB(2288): END-IF
      }
      // COB(2289): GO TO  EDIT-US-PHONE-PREFIX
      return Flow.editUsPhonePrefix;
      // COB(2290): END-IF
    }
    // COB(2292): IF  WS-EDIT-US-PHONE-NUMA-N = 0
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumaN.equals(0)) {
      // COB(2293): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2294): SET  FLG-EDIT-US-PHONEA-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhoneaNotOk(true);
      // COB(2295): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2296): STRING
        // COB(2296):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2296):   ': Area code cannot be zero'
        // COB(2296):   DELIMITED BY SIZE
        // COB(2296):   INTO WS-RETURN-MSG
        // COB(2296): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Area code cannot be zero");
        // COB(2301): END-STRING
        // COB(2302): END-IF
      }
      // COB(2303): GO TO  EDIT-US-PHONE-PREFIX
      return Flow.editUsPhonePrefix;
      // COB(2304): ELSE
    } else {
      // COB(2305): CONTINUE
      // do nothing
      // COB(2306): END-IF
    }
    // COB(2308): MOVE FUNCTION TRIM (WS-EDIT-US-PHONE-NUMA)
    // COB(2308):   TO WS-US-PHONE-AREA-CODE-TO-EDIT
    ws.cslkpcdy.wsUsPhoneAreaCodeToEdit.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNuma.trim());
    // COB(2310): IF VALID-GENERAL-PURP-CODE
    if (ws.cslkpcdy.validGeneralPurpCode()) {
      // COB(2311): CONTINUE
      // do nothing
      // COB(2312): ELSE
    } else {
      // COB(2313): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2314): SET  FLG-EDIT-US-PHONEA-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhoneaNotOk(true);
      // COB(2315): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2316): STRING
        // COB(2316):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2316):   ': Not valid North America general purpose area code'
        // COB(2316):   DELIMITED BY SIZE
        // COB(2316):   INTO WS-RETURN-MSG
        // COB(2316): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Not valid North America general purpose area code");
        // COB(2321): END-STRING
        // COB(2322): END-IF
      }
      // COB(2323): GO TO  EDIT-US-PHONE-PREFIX
      return Flow.editUsPhonePrefix;
      // COB(2324): END-IF
    }
    // COB(2326): SET FLG-EDIT-US-PHONEA-ISVALID    TO TRUE
    ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhoneaIsvalid(true);
    return Flow.Exit;
  }

  protected Flow editUsPhonePrefix() {
    // COB(2330): IF WS-EDIT-US-PHONE-NUMB EQUAL SPACES
    // COB(2330): OR WS-EDIT-US-PHONE-NUMB EQUAL LOW-VALUES
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumb.isSpaces()
        || ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumb.isLowValues()) {
      // COB(2332): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2333): SET FLG-EDIT-US-PHONEB-BLANK    TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonebBlank(true);
      // COB(2334): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2335): STRING
        // COB(2335):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2335):   ': Prefix code must be supplied.'
        // COB(2335):   DELIMITED BY SIZE
        // COB(2335):   INTO WS-RETURN-MSG
        // COB(2335): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Prefix code must be supplied.");
        // COB(2340): END-STRING
        // COB(2341): END-IF
      }
      // COB(2342): GO TO  EDIT-US-PHONE-LINENUM
      return Flow.editUsPhoneLinenum;
      // COB(2343): ELSE
    } else {
      // COB(2344): CONTINUE
      // do nothing
      // COB(2345): END-IF
    }
    // COB(2347): IF  WS-EDIT-US-PHONE-NUMB          IS NUMERIC
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumb.isNumeric()) {
      // COB(2348): CONTINUE
      // do nothing
      // COB(2349): ELSE
    } else {
      // COB(2350): SET INPUT-ERROR                 TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2351): SET  FLG-EDIT-US-PHONEB-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonebNotOk(true);
      // COB(2352): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2353): STRING
        // COB(2353):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2353):   ': Prefix code must be A 3 digit number.'
        // COB(2353):   DELIMITED BY SIZE
        // COB(2353):   INTO WS-RETURN-MSG
        // COB(2353): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Prefix code must be A 3 digit number.");
        // COB(2358): END-STRING
        // COB(2359): END-IF
      }
      // COB(2360): GO TO  EDIT-US-PHONE-LINENUM
      return Flow.editUsPhoneLinenum;
      // COB(2361): END-IF
    }
    // COB(2363): IF  WS-EDIT-US-PHONE-NUMB-N = 0
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumbN.equals(0)) {
      // COB(2364): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2365): SET  FLG-EDIT-US-PHONEB-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonebNotOk(true);
      // COB(2366): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2367): STRING
        // COB(2367):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2367):   ': Prefix code cannot be zero'
        // COB(2367):   DELIMITED BY SIZE
        // COB(2367):   INTO WS-RETURN-MSG
        // COB(2367): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Prefix code cannot be zero");
        // COB(2372): END-STRING
        // COB(2373): END-IF
      }
      // COB(2374): GO TO  EDIT-US-PHONE-LINENUM
      return Flow.editUsPhoneLinenum;
      // COB(2375): ELSE
    } else {
      // COB(2376): CONTINUE
      // do nothing
      // COB(2377): END-IF
    }
    // COB(2379): SET FLG-EDIT-US-PHONEB-ISVALID    TO TRUE
    ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonebIsvalid(true);
    return Flow.Exit;
  }

  /***/
  protected Flow editUsPhoneLinenum() {
    // COB(2383): IF WS-EDIT-US-PHONE-NUMC EQUAL SPACES
    // COB(2383): OR WS-EDIT-US-PHONE-NUMC EQUAL LOW-VALUES
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumc.isSpaces()
        || ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumc.isLowValues()) {
      // COB(2385): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2386): SET FLG-EDIT-US-PHONEC-BLANK    TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonecBlank(true);
      // COB(2387): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2388): STRING
        // COB(2388):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2388):   ': Line number code must be supplied.'
        // COB(2388):   DELIMITED BY SIZE
        // COB(2388):   INTO WS-RETURN-MSG
        // COB(2388): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Line number code must be supplied.");
        // COB(2393): END-STRING
        // COB(2394): END-IF
      }
      // COB(2395): GO TO EDIT-US-PHONE-EXIT
      return Flow.editUsPhoneExit;
      // COB(2396): ELSE
    } else {
      // COB(2397): CONTINUE
      // do nothing
      // COB(2398): END-IF
    }
    // COB(2400): IF  WS-EDIT-US-PHONE-NUMC          IS NUMERIC
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumc.isNumeric()) {
      // COB(2401): CONTINUE
      // do nothing
      // COB(2402): ELSE
    } else {
      // COB(2403): SET INPUT-ERROR                 TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2404): SET  FLG-EDIT-US-PHONEC-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonecNotOk(true);
      // COB(2405): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2406): STRING
        // COB(2406):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2406):   ': Line number code must be A 4 digit number.'
        // COB(2406):   DELIMITED BY SIZE
        // COB(2406):   INTO WS-RETURN-MSG
        // COB(2406): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Line number code must be A 4 digit number.");
        // COB(2411): END-STRING
        // COB(2412): END-IF
      }
      // COB(2413): GO TO  EDIT-US-PHONE-EXIT
      return Flow.editUsPhoneExit;
      // COB(2414): END-IF
    }
    // COB(2416): IF  WS-EDIT-US-PHONE-NUMC-N = 0
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumX.wsEditUsPhoneNumcN.equals(0)) {
      // COB(2417): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2418): SET  FLG-EDIT-US-PHONEC-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonecNotOk(true);
      // COB(2419): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2420): STRING
        // COB(2420):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2420):   ': Line number code cannot be zero'
        // COB(2420):   DELIMITED BY SIZE
        // COB(2420):   INTO WS-RETURN-MSG
        // COB(2420): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": Line number code cannot be zero");
        // COB(2425): END-STRING
        // COB(2426): END-IF
      }
      // COB(2427): GO TO  EDIT-US-PHONE-EXIT
      return Flow.editUsPhoneExit;
      // COB(2428): ELSE
    } else {
      // COB(2429): CONTINUE
      // do nothing
      // COB(2430): END-IF
    }
    // COB(2433): SET FLG-EDIT-US-PHONEC-ISVALID    TO TRUE
    //       *
    //       *
    ws.wsMiscStorage.wsGenericEdits.wsEditUsPhoneNumFlgs.setFlgEditUsPhonecIsvalid(true);
    return Flow.Exit;
  }

  /***/
  protected void editUsPhoneExit() {
    // COB(2437): EXIT
    return;
  }

  /***/
  protected void _1265EditUsSsn() {
    // COB(2451): MOVE 'SSN: First 3 chars'     TO WS-EDIT-VARIABLE-NAME
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("SSN: First 3 chars");
    // COB(2452): MOVE ACUP-NEW-CUST-SSN-1      TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn1);
    // COB(2453): MOVE 3                        TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(3);
    // COB(2454): PERFORM 1245-EDIT-NUM-REQD
    // COB(2454):    THRU 1245-EDIT-NUM-REQD-EXIT
    _1245EditNumReqd();
    // COB(2456): MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
    // COB(2456):                               TO WS-EDIT-US-SSN-PART1-FLGS
    ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.wsEditUsSsnPart1Flgs.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);
    // COB(2460): IF FLG-EDIT-US-SSN-PART1-ISVALID
    //       *
    //       *Part1 :should not be 000, 666, or between 900 and 999
    if (ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart1Isvalid()) {
      // COB(2461): MOVE ACUP-NEW-CUST-SSN-1   TO WS-EDIT-US-SSN-PART1
      ws.wsMiscStorage.wsGenericEdits.wsEditUsSsn.wsEditUsSsnPart1.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn1);
      // COB(2462): IF INVALID-SSN-PART1
      if (ws.wsMiscStorage.wsGenericEdits.wsEditUsSsn.invalidSsnPart1()) {
        // COB(2463): SET INPUT-ERROR            TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(2464): SET FLG-EDIT-US-SSN-PART1-NOT-OK
        // COB(2464):                    TO TRUE
        ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.setFlgEditUsSsnPart1NotOk(true);
        // COB(2466): IF WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(2467): STRING
          // COB(2467):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
          // COB(2467):   ': should not be 000, 666, or between 900 and 999'
          // COB(2467):   DELIMITED BY SIZE
          // COB(2467):   INTO WS-RETURN-MSG
          // COB(2467): END-STRING
          ws.wsMiscStorage.wsReturnMsg.concat(
              ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
              ": should not be 000, 666, or between 900 and 999");
          // COB(2472): END-STRING
          // COB(2473): ELSE
        } else {
          // COB(2474): CONTINUE
          // do nothing
          // COB(2475): END-IF
        }
        // COB(2476): END-IF
      }
      // COB(2481): MOVE 'SSN 4th & 5th chars'    TO WS-EDIT-VARIABLE-NAME
      //       *
      //       ******************************************************************
      //       *    Edit SSN Part 2
      //       ******************************************************************
      ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("SSN 4th & 5th chars");
      // COB(2482): MOVE ACUP-NEW-CUST-SSN-2      TO WS-EDIT-ALPHANUM-ONLY
      ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn2);
      // COB(2483): MOVE 2                        TO WS-EDIT-ALPHANUM-LENGTH
      ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(2);
      // COB(2484): PERFORM 1245-EDIT-NUM-REQD
      // COB(2484):    THRU 1245-EDIT-NUM-REQD-EXIT
      _1245EditNumReqd();
      // COB(2486): MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
      // COB(2486):                               TO WS-EDIT-US-SSN-PART2-FLGS
      ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.wsEditUsSsnPart2Flgs.setValue(
          ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);
      // COB(2493): MOVE 'SSN Last 4 chars'       TO WS-EDIT-VARIABLE-NAME
      //       *
      //       *
      //       ******************************************************************
      //       *    Edit SSN Part 3
      //       ******************************************************************
      ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("SSN Last 4 chars");
      // COB(2494): MOVE ACUP-NEW-CUST-SSN-3      TO WS-EDIT-ALPHANUM-ONLY
      ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn3);
      // COB(2495): MOVE 4                        TO WS-EDIT-ALPHANUM-LENGTH
      ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(4);
      // COB(2496): PERFORM 1245-EDIT-NUM-REQD
      // COB(2496):    THRU 1245-EDIT-NUM-REQD-EXIT
      _1245EditNumReqd();
      // COB(2498): MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
      // COB(2498):                               TO WS-EDIT-US-SSN-PART3-FLGS
      ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.wsEditUsSsnPart3Flgs.setValue(
          ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);
      // COB(2500): .
    }
  }

  /***/
  protected void _1270EditUsStateCd() {
    // COB(2506): MOVE ACUP-NEW-CUST-ADDR-STATE-CD TO US-STATE-CODE-TO-EDIT
    ws.cslkpcdy.usStateCodeToEdit.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrStateCd);
    // COB(2507): IF VALID-US-STATE-CODE
    if (ws.cslkpcdy.validUsStateCode()) {
      // COB(2508): CONTINUE
      // do nothing
      // COB(2509): ELSE
    } else {
      // COB(2510): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2511): SET FLG-STATE-NOT-OK         TO TRUE
      ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.setFlgStateNotOk(true);
      // COB(2512): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2513): STRING
        // COB(2513):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2513):   ': is not a valid state code'
        // COB(2513):   DELIMITED BY SIZE
        // COB(2513):   INTO WS-RETURN-MSG
        // COB(2513): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": is not a valid state code");
        // COB(2518): END-STRING
        // COB(2519): END-IF
      }
      // COB(2520): GO TO  1270-EDIT-US-STATE-CD-EXIT
      return;
      // COB(2521): END-IF
    }
  }

  protected void _1275EditFicoScore() {
    // COB(2527): IF FICO-RANGE-IS-VALID
    if (ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.ficoRangeIsValid()) {
      // COB(2528): CONTINUE
      // do nothing
      // COB(2529): ELSE
    } else {
      // COB(2530): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2531): SET FLG-FICO-SCORE-NOT-OK    TO TRUE
      ws.wsMiscStorage.wsNonKeyFlags.setFlgFicoScoreNotOk(true);
      // COB(2532): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2533): STRING
        // COB(2533):   FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(2533):   ': should be between 300 and 850'
        // COB(2533):   DELIMITED BY SIZE
        // COB(2533):   INTO WS-RETURN-MSG
        // COB(2533): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            ": should be between 300 and 850");
        // COB(2538): END-STRING
        // COB(2539): END-IF
      }
      // COB(2540): GO TO  1275-EDIT-FICO-SCORE-EXIT
      return;
      // COB(2541): END-IF
    }
  }

  /***
   *A crude zip code edit based on data from USPS web site*/
  protected void _1280EditUsStateZipCd() {
    // COB(2549): STRING ACUP-NEW-CUST-ADDR-STATE-CD
    // COB(2549):        ACUP-NEW-CUST-ADDR-ZIP(1:2)
    // COB(2549):   DELIMITED BY SIZE
    // COB(2549):   INTO US-STATE-AND-FIRST-ZIP2
    ws.cslkpcdy.usStateZipcodeToEdit.usStateAndFirstZip2.concat(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrStateCd,
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrZip.getString(0, 2));
    // COB(2554): IF VALID-US-STATE-ZIP-CD2-COMBO
    if (ws.cslkpcdy.usStateZipcodeToEdit.validUsStateZipCd2Combo()) {
      // COB(2555): CONTINUE
      // do nothing
      // COB(2556): ELSE
    } else {
      // COB(2557): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(2558): SET FLG-STATE-NOT-OK         TO TRUE
      ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.setFlgStateNotOk(true);
      // COB(2559): SET FLG-ZIPCODE-NOT-OK       TO TRUE
      ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.setFlgZipcodeNotOk(true);
      // COB(2560): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(2561): STRING
        // COB(2561):   'Invalid zip code for state'
        // COB(2561):   DELIMITED BY SIZE
        // COB(2561):   INTO WS-RETURN-MSG
        // COB(2561): END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat("Invalid zip code for state");
        // COB(2565): END-STRING
        // COB(2566): END-IF
      }
      // COB(2567): GO TO  1280-EDIT-US-STATE-ZIP-CD-EXIT
      return;
      // COB(2568): END-IF
    }
  }

  /***/
  protected void _2000DecideAction() {
    // COB(2575): EVALUATE TRUE
    // COB(2580): WHEN ACUP-DETAILS-NOT-FETCHED
    //       ******************************************************************
    //       *       NO DETAILS SHOWN.
    //       *       SO GET THEM AND SETUP DETAIL EDIT SCREEN
    //       ******************************************************************
    if ((ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched())
        // COB(2584): WHEN CCARD-AID-PFK12
        //       ******************************************************************
        //       *       CHANGES MADE. BUT USER CANCELS
        //       ******************************************************************
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk12()) {
      // COB(2585): IF  FLG-ACCTFILTER-ISVALID
      if (ws.wsMiscStorage.flgAcctfilterIsvalid()) {
        // COB(2586): SET WS-RETURN-MSG-OFF       TO TRUE
        ws.wsMiscStorage.setWsReturnMsgOff(true);
        // COB(2587): PERFORM 9000-READ-ACCT
        // COB(2587):    THRU 9000-READ-ACCT-EXIT
        _9000ReadAcct();
        // COB(2589): IF FOUND-CUST-IN-MASTER
        if (ws.wsMiscStorage.wsFileReadFlags.foundCustInMaster()) {
          // COB(2590): SET ACUP-SHOW-DETAILS    TO TRUE
          ws.wsThisProgcommarea.acctUpdateScreenData.setAcupShowDetails(true);
          // COB(2591): END-IF
        }
        // COB(2592): END-IF
      }
      // COB(2597): WHEN ACUP-SHOW-DETAILS
      //       ******************************************************************
      //       *       DETAILS SHOWN
      //       *       CHECK CHANGES AND ASK CONFIRMATION IF GOOD
      //       ******************************************************************
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupShowDetails()) {
      // COB(2598): IF INPUT-ERROR
      // COB(2598): OR NO-CHANGES-DETECTED
      if (ws.wsMiscStorage.inputError() || ws.wsMiscStorage.noChangesDetected()) {
        // COB(2600): CONTINUE
        // do nothing
        // COB(2601): ELSE
      } else {
        // COB(2602): SET ACUP-CHANGES-OK-NOT-CONFIRMED TO TRUE
        ws.wsThisProgcommarea.acctUpdateScreenData.setAcupChangesOkNotConfirmed(true);
        // COB(2603): END-IF
      }
      // COB(2608): WHEN ACUP-CHANGES-NOT-OK
      //       ******************************************************************
      //       *       DETAILS SHOWN
      //       *       BUT INPUT EDIT ERRORS FOUND
      //       ******************************************************************
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesNotOk()) {
      // COB(2609): CONTINUE
      // do nothing
      // COB(2614): WHEN ACUP-CHANGES-OK-NOT-CONFIRMED
      // COB(2614):  AND CCARD-AID-PFK05
      //       ******************************************************************
      //       *       DETAILS EDITED , FOUND OK, CONFIRM SAVE REQUESTED
      //       *       CONFIRMATION GIVEN.SO SAVE THE CHANGES
      //       ******************************************************************
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkNotConfirmed()
        && ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk05()) {
      // COB(2616): PERFORM 9600-WRITE-PROCESSING
      // COB(2616):    THRU 9600-WRITE-PROCESSING-EXIT
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
      // COB(2618): EVALUATE TRUE
      // COB(2619): WHEN COULD-NOT-LOCK-ACCT-FOR-UPDATE
      if (ws.wsMiscStorage.couldNotLockAcctForUpdate()) {
        // COB(2620): SET ACUP-CHANGES-OKAYED-LOCK-ERROR TO TRUE
        ws.wsThisProgcommarea.acctUpdateScreenData.setAcupChangesOkayedLockError(true);
        // COB(2621): WHEN LOCKED-BUT-UPDATE-FAILED
      } else if (ws.wsMiscStorage.lockedButUpdateFailed()) {
        // COB(2622): SET ACUP-CHANGES-OKAYED-BUT-FAILED TO TRUE
        ws.wsThisProgcommarea.acctUpdateScreenData.setAcupChangesOkayedButFailed(true);
        // COB(2623): WHEN DATA-WAS-CHANGED-BEFORE-UPDATE
      } else if (ws.wsMiscStorage.dataWasChangedBeforeUpdate()) {
        // COB(2624): SET ACUP-SHOW-DETAILS            TO TRUE
        ws.wsThisProgcommarea.acctUpdateScreenData.setAcupShowDetails(true);
        // COB(2625): WHEN OTHER
      } else {
        // COB(2626): SET ACUP-CHANGES-OKAYED-AND-DONE   TO TRUE
        ws.wsThisProgcommarea.acctUpdateScreenData.setAcupChangesOkayedAndDone(true);
        // COB(2627): END-EVALUATE
      }
      // COB(2632): WHEN ACUP-CHANGES-OK-NOT-CONFIRMED
      //       ******************************************************************
      //       *       DETAILS EDITED , FOUND OK, CONFIRM SAVE REQUESTED
      //       *       CONFIRMATION NOT GIVEN. SO SHOW DETAILS AGAIN
      //       ******************************************************************
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkNotConfirmed()) {
      // COB(2633): CONTINUE
      // do nothing
      // COB(2637): WHEN ACUP-CHANGES-OKAYED-AND-DONE
      //       ******************************************************************
      //       *       SHOW CONFIRMATION. GO BACK TO SQUARE 1
      //       ******************************************************************
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedAndDone()) {
      // COB(2638): SET ACUP-SHOW-DETAILS TO TRUE
      ws.wsThisProgcommarea.acctUpdateScreenData.setAcupShowDetails(true);
      // COB(2639): IF CDEMO-FROM-TRANID    EQUAL LOW-VALUES
      // COB(2639): OR CDEMO-FROM-TRANID    EQUAL SPACES
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isSpaces()) {
        // COB(2641): MOVE ZEROES       TO CDEMO-ACCT-ID
        // COB(2641):                      CDEMO-CARD-NUM
        ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
        ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.zeros();
        // COB(2643): MOVE LOW-VALUES   TO CDEMO-ACCT-STATUS
        ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctStatus.lowValues();
        // COB(2644): END-IF
      }
      // COB(2645): WHEN OTHER
    } else {
      // COB(2646): MOVE LIT-THISPGM    TO ABEND-CULPRIT
      ws.csmsg02y.abendData.abendCulprit.setValue(ws.wsLiterals.litThispgm);
      // COB(2647): MOVE '0001'         TO ABEND-CODE
      ws.csmsg02y.abendData.abendCode.setString("0001");
      // COB(2648): MOVE SPACES         TO ABEND-REASON
      ws.csmsg02y.abendData.abendReason.spaces();
      // COB(2649): MOVE 'UNEXPECTED DATA SCENARIO'
      // COB(2649):                     TO ABEND-MSG
      ws.csmsg02y.abendData.abendMsg.setString("UNEXPECTED DATA SCENARIO");
      // COB(2651): PERFORM ABEND-ROUTINE
      // COB(2651):    THRU ABEND-ROUTINE-EXIT
      abendRoutine();
      // COB(2653): END-EVALUATE
    }
  }

  /***
   *
   */
  protected void _3000SendMap() {
    // COB(2662): PERFORM 3100-SCREEN-INIT
    // COB(2662):    THRU 3100-SCREEN-INIT-EXIT
    _3100ScreenInit();
    // COB(2664): PERFORM 3200-SETUP-SCREEN-VARS
    // COB(2664):    THRU 3200-SETUP-SCREEN-VARS-EXIT
    _3200SetupScreenVars();
    // COB(2666): PERFORM 3250-SETUP-INFOMSG
    // COB(2666):    THRU 3250-SETUP-INFOMSG-EXIT
    _3250SetupInfomsg();
    // COB(2668): PERFORM 3300-SETUP-SCREEN-ATTRS
    // COB(2668):    THRU 3300-SETUP-SCREEN-ATTRS-EXIT
    _3300SetupScreenAttrs();
    // COB(2670): PERFORM 3390-SETUP-INFOMSG-ATTRS
    // COB(2670):    THRU 3390-SETUP-INFOMSG-ATTRS-EXIT
    _3390SetupInfomsgAttrs();
    // COB(2672): PERFORM 3400-SEND-SCREEN
    // COB(2672):    THRU 3400-SEND-SCREEN-EXIT
    _3400SendScreen();
  }

  /***/
  protected void _3100ScreenInit() {
    // COB(2681): MOVE LOW-VALUES TO CACTUPAO
    ws.coactup.cactupao.lowValues();
    // COB(2683): MOVE FUNCTION CURRENT-DATE     TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(2685): MOVE CCDA-TITLE01              TO TITLE01O OF CACTUPAO
    ws.coactup.cactupao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(2686): MOVE CCDA-TITLE02              TO TITLE02O OF CACTUPAO
    ws.coactup.cactupao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(2687): MOVE LIT-THISTRANID            TO TRNNAMEO OF CACTUPAO
    ws.coactup.cactupao.trnnameo.setValue(ws.wsLiterals.litThistranid);
    // COB(2688): MOVE LIT-THISPGM               TO PGMNAMEO OF CACTUPAO
    ws.coactup.cactupao.pgmnameo.setValue(ws.wsLiterals.litThispgm);
    // COB(2690): MOVE FUNCTION CURRENT-DATE     TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(2692): MOVE WS-CURDATE-MONTH          TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(2693): MOVE WS-CURDATE-DAY            TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(2694): MOVE WS-CURDATE-YEAR(3:2)      TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(2696): MOVE WS-CURDATE-MM-DD-YY       TO CURDATEO OF CACTUPAO
    ws.coactup.cactupao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(2698): MOVE WS-CURTIME-HOURS          TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(2699): MOVE WS-CURTIME-MINUTE         TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(2700): MOVE WS-CURTIME-SECOND         TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(2702): MOVE WS-CURTIME-HH-MM-SS       TO CURTIMEO OF CACTUPAO
    ws.coactup.cactupao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***/
  protected void _3200SetupScreenVars() {
    // COB(2712): IF CDEMO-PGM-ENTER
    if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()) {
      // COB(2713): CONTINUE
      // do nothing
      // COB(2714): ELSE
    } else {
      // COB(2715): IF CC-ACCT-ID-N = 0
      // COB(2715): AND FLG-ACCTFILTER-ISVALID
      if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctIdN.equals(0)
          && ws.wsMiscStorage.flgAcctfilterIsvalid()) {
        // COB(2717): MOVE LOW-VALUES                TO ACCTSIDO OF CACTUPAO
        ws.coactup.cactupao.acctsido.lowValues();
        // COB(2718): ELSE
      } else {
        // COB(2719): MOVE CC-ACCT-ID                TO ACCTSIDO OF CACTUPAO
        ws.coactup.cactupao.acctsido.setValue(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
        // COB(2720): END-IF
      }
      // COB(2722): EVALUATE TRUE
      // COB(2723): WHEN ACUP-DETAILS-NOT-FETCHED
      if ((ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched())
          // COB(2724): WHEN CC-ACCT-ID-N =  0
          || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctIdN.equals(0)) {
        // COB(2725): PERFORM 3201-SHOW-INITIAL-VALUES
        // COB(2725):    THRU 3201-SHOW-INITIAL-VALUES-EXIT
        _3201ShowInitialValues();
        // COB(2727): WHEN ACUP-SHOW-DETAILS
      } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupShowDetails()) {
        // COB(2728): PERFORM 3202-SHOW-ORIGINAL-VALUES
        // COB(2728):    THRU 3202-SHOW-ORIGINAL-VALUES-EXIT
        _3202ShowOriginalValues();
        // COB(2730): WHEN ACUP-CHANGES-MADE
      } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesMade()) {
        // COB(2731): PERFORM 3203-SHOW-UPDATED-VALUES
        // COB(2731):    THRU 3203-SHOW-UPDATED-VALUES-EXIT
        _3203ShowUpdatedValues();
        // COB(2733): WHEN OTHER
      } else {
        // COB(2734): PERFORM 3202-SHOW-ORIGINAL-VALUES
        // COB(2734):    THRU 3202-SHOW-ORIGINAL-VALUES-EXIT
        _3202ShowOriginalValues();
        // COB(2736): END-EVALUATE
      }
      // COB(2737): END-IF
    }
  }

  /***/
  protected void _3201ShowInitialValues() {
    // COB(2744): MOVE LOW-VALUES                     TO  ACSTTUSO OF CACTUPAO
    // COB(2744):                                         ACRDLIMO OF CACTUPAO
    // COB(2744):       *Account Limits
    // COB(2744):                                         ACURBALO OF CACTUPAO
    // COB(2744):                                         ACSHLIMO OF CACTUPAO
    // COB(2744):                                         ACRCYCRO OF CACTUPAO
    // COB(2744):                                         ACRCYDBO OF CACTUPAO
    // COB(2744):       *Account Dates
    // COB(2744):                                         OPNYEARO OF CACTUPAO
    // COB(2744):                                         OPNMONO  OF CACTUPAO
    // COB(2744):                                         OPNDAYO  OF CACTUPAO
    // COB(2744):                                         EXPYEARO OF CACTUPAO
    // COB(2744):                                         EXPMONO  OF CACTUPAO
    // COB(2744):                                         EXPDAYO  OF CACTUPAO
    // COB(2744):                                         RISYEARO OF CACTUPAO
    // COB(2744):                                         RISMONO  OF CACTUPAO
    // COB(2744):                                         RISDAYO  OF CACTUPAO
    // COB(2744):                                         AADDGRPO OF CACTUPAO
    // COB(2744):       *Customer data
    // COB(2744):                                         ACSTNUMO OF CACTUPAO
    // COB(2744):                                         ACTSSN1O OF CACTUPAO
    // COB(2744):                                         ACTSSN2O OF CACTUPAO
    // COB(2744):                                         ACTSSN3O OF CACTUPAO
    // COB(2744):                                         ACSTFCOO OF CACTUPAO
    // COB(2744):                                         DOBYEARO OF CACTUPAO
    // COB(2744):                                         DOBMONO  OF CACTUPAO
    // COB(2744):                                         DOBDAYO  OF CACTUPAO
    // COB(2744):                                         ACSFNAMO OF CACTUPAO
    // COB(2744):                                         ACSMNAMO OF CACTUPAO
    // COB(2744):                                         ACSLNAMO OF CACTUPAO
    // COB(2744):       *Customer address and contact info
    // COB(2744):                                         ACSADL1O OF CACTUPAO
    // COB(2744):                                         ACSADL2O OF CACTUPAO
    // COB(2744):                                         ACSCITYO OF CACTUPAO
    // COB(2744):                                         ACSSTTEO OF CACTUPAO
    // COB(2744):                                         ACSZIPCO OF CACTUPAO
    // COB(2744):                                         ACSCTRYO OF CACTUPAO
    // COB(2744):       *
    // COB(2744):                                         ACSPH1AO OF CACTUPAO
    // COB(2744):                                         ACSPH1BO OF CACTUPAO
    // COB(2744):                                         ACSPH1CO OF CACTUPAO
    // COB(2744):                                         ACSPH2AO OF CACTUPAO
    // COB(2744):                                         ACSPH2BO OF CACTUPAO
    // COB(2744):                                         ACSPH2CO OF CACTUPAO
    // COB(2744):       *
    // COB(2744):       *Customer other good stuff
    // COB(2744):                                         ACSGOVTO OF CACTUPAO
    // COB(2744):                                         ACSEFTCO OF CACTUPAO
    // COB(2744):                                         ACSPFLGO OF CACTUPAO
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
    // COB(2801): MOVE LOW-VALUES                     TO WS-NON-KEY-FLAGS
    ws.wsMiscStorage.wsNonKeyFlags.lowValues();
    // COB(2803): SET PROMPT-FOR-CHANGES              TO TRUE
    ws.wsMiscStorage.setPromptForChanges(true);
    // COB(2805): IF FOUND-ACCT-IN-MASTER
    // COB(2805): OR FOUND-CUST-IN-MASTER
    if (ws.wsMiscStorage.wsFileReadFlags.foundAcctInMaster()
        || ws.wsMiscStorage.wsFileReadFlags.foundCustInMaster()) {
      // COB(2807): MOVE ACUP-OLD-ACTIVE-STATUS      TO ACSTTUSO OF CACTUPAO
      ws.coactup.cactupao.acsttuso.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldActiveStatus);
      // COB(2809): MOVE ACUP-OLD-CURR-BAL-N         TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrBalN);
      // COB(2810): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACURBALO OF CACTUPAO
      ws.coactup.cactupao.acurbalo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2812): MOVE ACUP-OLD-CREDIT-LIMIT-N     TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCreditLimitN);
      // COB(2813): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACRDLIMO OF CACTUPAO
      ws.coactup.cactupao.acrdlimo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2815): MOVE ACUP-OLD-CASH-CREDIT-LIMIT-N
      // COB(2815):                                  TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCashCreditLimitN);
      // COB(2817): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACSHLIMO OF CACTUPAO
      ws.coactup.cactupao.acshlimo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2819): MOVE ACUP-OLD-CURR-CYC-CREDIT-N  TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrCycCreditN);
      // COB(2820): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACRCYCRO OF CACTUPAO
      ws.coactup.cactupao.acrcycro.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2822): MOVE ACUP-OLD-CURR-CYC-DEBIT-N   TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrCycDebitN);
      // COB(2823): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACRCYDBO OF CACTUPAO
      ws.coactup.cactupao.acrcydbo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2825): MOVE ACUP-OLD-OPEN-YEAR          TO OPNYEARO OF CACTUPAO
      ws.coactup.cactupao.opnyearo.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldAcctData
              .acupOldOpenDateParts
              .acupOldOpenYear);
      // COB(2826): MOVE ACUP-OLD-OPEN-MON           TO OPNMONO  OF CACTUPAO
      ws.coactup.cactupao.opnmono.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldOpenDateParts.acupOldOpenMon);
      // COB(2827): MOVE ACUP-OLD-OPEN-DAY           TO OPNDAYO  OF CACTUPAO
      ws.coactup.cactupao.opndayo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldOpenDateParts.acupOldOpenDay);
      // COB(2829): MOVE ACUP-OLD-EXP-YEAR           TO EXPYEARO OF CACTUPAO
      ws.coactup.cactupao.expyearo.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldAcctData
              .acupOldExpiraionDateParts
              .acupOldExpYear);
      // COB(2830): MOVE ACUP-OLD-EXP-MON            TO EXPMONO  OF CACTUPAO
      ws.coactup.cactupao.expmono.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldAcctData
              .acupOldExpiraionDateParts
              .acupOldExpMon);
      // COB(2831): MOVE ACUP-OLD-EXP-DAY            TO EXPDAYO  OF CACTUPAO
      ws.coactup.cactupao.expdayo.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldAcctData
              .acupOldExpiraionDateParts
              .acupOldExpDay);
      // COB(2833): MOVE ACUP-OLD-REISSUE-YEAR       TO RISYEARO OF CACTUPAO
      ws.coactup.cactupao.risyearo.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldAcctData
              .acupOldReissueDateParts
              .acupOldReissueYear);
      // COB(2834): MOVE ACUP-OLD-REISSUE-MON        TO RISMONO  OF CACTUPAO
      ws.coactup.cactupao.rismono.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldAcctData
              .acupOldReissueDateParts
              .acupOldReissueMon);
      // COB(2835): MOVE ACUP-OLD-REISSUE-DAY        TO RISDAYO  OF CACTUPAO
      ws.coactup.cactupao.risdayo.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldAcctData
              .acupOldReissueDateParts
              .acupOldReissueDay);
      // COB(2836): MOVE ACUP-OLD-GROUP-ID           TO AADDGRPO OF CACTUPAO
      ws.coactup.cactupao.aaddgrpo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldGroupId);
      // COB(2837): END-IF
    }
    // COB(2839): IF FOUND-CUST-IN-MASTER
    if (ws.wsMiscStorage.wsFileReadFlags.foundCustInMaster()) {
      // COB(2840): MOVE ACUP-OLD-CUST-ID-X          TO ACSTNUMO OF CACTUPAO
      ws.coactup.cactupao.acstnumo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustIdX);
      // COB(2841): MOVE ACUP-OLD-CUST-SSN-X(1:3)    TO ACTSSN1O OF CACTUPAO
      ws.coactup.cactupao.actssn1o.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustSsnX, 0, 3);
      // COB(2842): MOVE ACUP-OLD-CUST-SSN-X(4:2)    TO ACTSSN2O OF CACTUPAO
      ws.coactup.cactupao.actssn2o.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustSsnX, 3, 0, 2);
      // COB(2843): MOVE ACUP-OLD-CUST-SSN-X(6:4)    TO ACTSSN3O OF CACTUPAO
      ws.coactup.cactupao.actssn3o.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustSsnX, 5, 0, 4);
      // COB(2844): MOVE ACUP-OLD-CUST-FICO-SCORE-X  TO ACSTFCOO OF CACTUPAO
      ws.coactup.cactupao.acstfcoo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustFicoScoreX);
      // COB(2845): MOVE ACUP-OLD-CUST-DOB-YEAR      TO DOBYEARO OF CACTUPAO
      ws.coactup.cactupao.dobyearo.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldCustData
              .acupOldCustDobParts
              .acupOldCustDobYear);
      // COB(2846): MOVE ACUP-OLD-CUST-DOB-MON       TO DOBMONO  OF CACTUPAO
      ws.coactup.cactupao.dobmono.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldCustData
              .acupOldCustDobParts
              .acupOldCustDobMon);
      // COB(2847): MOVE ACUP-OLD-CUST-DOB-DAY       TO DOBDAYO  OF CACTUPAO
      ws.coactup.cactupao.dobdayo.setValue(
          ws.wsThisProgcommarea
              .acupOldDetails
              .acupOldCustData
              .acupOldCustDobParts
              .acupOldCustDobDay);
      // COB(2848): MOVE ACUP-OLD-CUST-FIRST-NAME    TO ACSFNAMO OF CACTUPAO
      ws.coactup.cactupao.acsfnamo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustFirstName);
      // COB(2849): MOVE ACUP-OLD-CUST-MIDDLE-NAME   TO ACSMNAMO OF CACTUPAO
      ws.coactup.cactupao.acsmnamo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustMiddleName);
      // COB(2850): MOVE ACUP-OLD-CUST-LAST-NAME     TO ACSLNAMO OF CACTUPAO
      ws.coactup.cactupao.acslnamo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustLastName);
      // COB(2851): MOVE ACUP-OLD-CUST-ADDR-LINE-1   TO ACSADL1O OF CACTUPAO
      ws.coactup.cactupao.acsadl1o.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine1);
      // COB(2852): MOVE ACUP-OLD-CUST-ADDR-LINE-2   TO ACSADL2O OF CACTUPAO
      ws.coactup.cactupao.acsadl2o.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine2);
      // COB(2853): MOVE ACUP-OLD-CUST-ADDR-LINE-3   TO ACSCITYO OF CACTUPAO
      ws.coactup.cactupao.acscityo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine3);
      // COB(2854): MOVE ACUP-OLD-CUST-ADDR-STATE-CD TO ACSSTTEO OF CACTUPAO
      ws.coactup.cactupao.acsstteo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrStateCd);
      // COB(2855): MOVE ACUP-OLD-CUST-ADDR-ZIP      TO ACSZIPCO OF CACTUPAO
      ws.coactup.cactupao.acszipco.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrZip);
      // COB(2856): MOVE ACUP-OLD-CUST-ADDR-COUNTRY-CD
      // COB(2856):                                  TO ACSCTRYO OF CACTUPAO
      ws.coactup.cactupao.acsctryo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrCountryCd);
      // COB(2858): MOVE ACUP-OLD-CUST-PHONE-NUM-1(2:3)
      // COB(2858):                                  TO ACSPH1AO OF CACTUPAO
      ws.coactup.cactupao.acsph1ao.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum1, 1, 0, 3);
      // COB(2860): MOVE ACUP-OLD-CUST-PHONE-NUM-1(6:3)
      // COB(2860):                                  TO ACSPH1BO OF CACTUPAO
      ws.coactup.cactupao.acsph1bo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum1, 5, 0, 3);
      // COB(2862): MOVE ACUP-OLD-CUST-PHONE-NUM-1(10:4)
      // COB(2862):                                  TO ACSPH1CO OF CACTUPAO
      ws.coactup.cactupao.acsph1co.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum1, 9, 0, 4);
      // COB(2864): MOVE ACUP-OLD-CUST-PHONE-NUM-2(2:3)
      // COB(2864):                                  TO ACSPH2AO OF CACTUPAO
      ws.coactup.cactupao.acsph2ao.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum2, 1, 0, 3);
      // COB(2866): MOVE ACUP-OLD-CUST-PHONE-NUM-2(6:3)
      // COB(2866):                                  TO ACSPH2BO OF CACTUPAO
      ws.coactup.cactupao.acsph2bo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum2, 5, 0, 3);
      // COB(2868): MOVE ACUP-OLD-CUST-PHONE-NUM-2(10:4)
      // COB(2868):                                  TO ACSPH2CO OF CACTUPAO
      ws.coactup.cactupao.acsph2co.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum2, 9, 0, 4);
      // COB(2870): MOVE ACUP-OLD-CUST-GOVT-ISSUED-ID
      // COB(2870):                                  TO ACSGOVTO OF CACTUPAO
      ws.coactup.cactupao.acsgovto.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustGovtIssuedId);
      // COB(2872): MOVE ACUP-OLD-CUST-EFT-ACCOUNT-ID
      // COB(2872):                                  TO ACSEFTCO OF CACTUPAO
      ws.coactup.cactupao.acseftco.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustEftAccountId);
      // COB(2874): MOVE ACUP-OLD-CUST-PRI-HOLDER-IND
      // COB(2874):                                  TO ACSPFLGO OF CACTUPAO
      ws.coactup.cactupao.acspflgo.setValue(
          ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPriHolderInd);
      // COB(2876): END-IF
    }
  }

  protected void _3203ShowUpdatedValues() {
    // COB(2884): MOVE ACUP-NEW-ACTIVE-STATUS         TO ACSTTUSO OF CACTUPAO
    ws.coactup.cactupao.acsttuso.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewActiveStatus);
    // COB(2886): IF FLG-CRED-LIMIT-ISVALID
    if (ws.wsMiscStorage.wsNonKeyFlags.flgCredLimitIsvalid()) {
      // COB(2887): MOVE ACUP-NEW-CREDIT-LIMIT-N     TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCreditLimitN);
      // COB(2888): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACRDLIMO OF CACTUPAO
      ws.coactup.cactupao.acrdlimo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2889): ELSE
    } else {
      // COB(2890): MOVE ACUP-NEW-CREDIT-LIMIT-X     TO ACRDLIMO OF CACTUPAO
      ws.coactup.cactupao.acrdlimo.setValue(
          ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCreditLimitX);
      // COB(2891): END-IF
    }
    // COB(2893): IF FLG-CASH-CREDIT-LIMIT-ISVALID
    if (ws.wsMiscStorage.wsNonKeyFlags.flgCashCreditLimitIsvalid()) {
      // COB(2894): MOVE ACUP-NEW-CASH-CREDIT-LIMIT-N
      // COB(2894):                                  TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCashCreditLimitN);
      // COB(2896): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACSHLIMO OF CACTUPAO
      ws.coactup.cactupao.acshlimo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2897): ELSE
    } else {
      // COB(2898): MOVE ACUP-NEW-CASH-CREDIT-LIMIT-X
      // COB(2898):                                  TO ACSHLIMO OF CACTUPAO
      ws.coactup.cactupao.acshlimo.setValue(
          ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCashCreditLimitX);
      // COB(2900): END-IF
    }
    // COB(2902): IF FLG-CURR-BAL-ISVALID
    if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrBalIsvalid()) {
      // COB(2903): MOVE ACUP-NEW-CURR-BAL-N         TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrBalN);
      // COB(2904): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACURBALO OF CACTUPAO
      ws.coactup.cactupao.acurbalo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2905): ELSE
    } else {
      // COB(2906): MOVE ACUP-NEW-CURR-BAL-X         TO ACURBALO OF CACTUPAO
      ws.coactup.cactupao.acurbalo.setValue(
          ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrBalX);
      // COB(2907): END-IF
    }
    // COB(2909): IF FLG-CURR-CYC-CREDIT-ISVALID
    if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycCreditIsvalid()) {
      // COB(2910): MOVE ACUP-NEW-CURR-CYC-CREDIT-N  TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrCycCreditN);
      // COB(2911): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACRCYCRO OF CACTUPAO
      ws.coactup.cactupao.acrcycro.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2912): ELSE
    } else {
      // COB(2913): MOVE ACUP-NEW-CURR-CYC-CREDIT-X  TO ACRCYCRO OF CACTUPAO
      ws.coactup.cactupao.acrcycro.setValue(
          ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycCreditX);
      // COB(2914): END-IF
    }
    // COB(2916): IF FLG-CURR-CYC-DEBIT-ISVALID
    if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycDebitIsvalid()) {
      // COB(2917): MOVE ACUP-NEW-CURR-CYC-DEBIT-N   TO WS-EDIT-CURRENCY-9-2-F
      ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F.setValue(
          ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrCycDebitN);
      // COB(2918): MOVE WS-EDIT-CURRENCY-9-2-F      TO ACRCYDBO OF CACTUPAO
      ws.coactup.cactupao.acrcydbo.setValue(ws.wsMiscStorage.cicsOutputEditVars.wsEditCurrency9_2F);
      // COB(2919): ELSE
    } else {
      // COB(2920): MOVE ACUP-NEW-CURR-CYC-DEBIT-X   TO ACRCYDBO OF CACTUPAO
      ws.coactup.cactupao.acrcydbo.setValue(
          ws.wsMiscStorage.alphaVarsForDataEditing.acupNewCurrCycDebitX);
      // COB(2921): END-IF
    }
    // COB(2923): MOVE ACUP-NEW-OPEN-YEAR             TO OPNYEARO OF CACTUPAO
    ws.coactup.cactupao.opnyearo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenYear);
    // COB(2924): MOVE ACUP-NEW-OPEN-MON              TO OPNMONO  OF CACTUPAO
    ws.coactup.cactupao.opnmono.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenMon);
    // COB(2925): MOVE ACUP-NEW-OPEN-DAY              TO OPNDAYO  OF CACTUPAO
    ws.coactup.cactupao.opndayo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenDay);
    // COB(2927): MOVE ACUP-NEW-EXP-YEAR              TO EXPYEARO OF CACTUPAO
    ws.coactup.cactupao.expyearo.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewExpiraionDateParts
            .acupNewExpYear);
    // COB(2928): MOVE ACUP-NEW-EXP-MON               TO EXPMONO  OF CACTUPAO
    ws.coactup.cactupao.expmono.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewExpiraionDateParts
            .acupNewExpMon);
    // COB(2929): MOVE ACUP-NEW-EXP-DAY               TO EXPDAYO  OF CACTUPAO
    ws.coactup.cactupao.expdayo.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewExpiraionDateParts
            .acupNewExpDay);
    // COB(2930): MOVE ACUP-NEW-REISSUE-YEAR          TO RISYEARO OF CACTUPAO
    ws.coactup.cactupao.risyearo.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewReissueDateParts
            .acupNewReissueYear);
    // COB(2931): MOVE ACUP-NEW-REISSUE-MON           TO RISMONO  OF CACTUPAO
    ws.coactup.cactupao.rismono.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewReissueDateParts
            .acupNewReissueMon);
    // COB(2932): MOVE ACUP-NEW-REISSUE-DAY           TO RISDAYO  OF CACTUPAO
    ws.coactup.cactupao.risdayo.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewAcctData
            .acupNewReissueDateParts
            .acupNewReissueDay);
    // COB(2933): MOVE ACUP-NEW-GROUP-ID              TO AADDGRPO OF CACTUPAO
    ws.coactup.cactupao.aaddgrpo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewGroupId);
    // COB(2934): MOVE ACUP-NEW-CUST-ID-X             TO ACSTNUMO OF CACTUPAO
    ws.coactup.cactupao.acstnumo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustIdX);
    // COB(2935): MOVE ACUP-NEW-CUST-SSN-1            TO ACTSSN1O OF CACTUPAO
    ws.coactup.cactupao.actssn1o.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn1);
    // COB(2936): MOVE ACUP-NEW-CUST-SSN-2            TO ACTSSN2O OF CACTUPAO
    ws.coactup.cactupao.actssn2o.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn2);
    // COB(2937): MOVE ACUP-NEW-CUST-SSN-3            TO ACTSSN3O OF CACTUPAO
    ws.coactup.cactupao.actssn3o.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsnX.acupNewCustSsn3);
    // COB(2938): MOVE ACUP-NEW-CUST-FICO-SCORE-X     TO ACSTFCOO OF CACTUPAO
    ws.coactup.cactupao.acstfcoo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFicoScoreX);
    // COB(2939): MOVE ACUP-NEW-CUST-DOB-YEAR         TO DOBYEARO OF CACTUPAO
    ws.coactup.cactupao.dobyearo.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustDobParts
            .acupNewCustDobYear);
    // COB(2940): MOVE ACUP-NEW-CUST-DOB-MON          TO DOBMONO  OF CACTUPAO
    ws.coactup.cactupao.dobmono.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobMon);
    // COB(2941): MOVE ACUP-NEW-CUST-DOB-DAY          TO DOBDAYO  OF CACTUPAO
    ws.coactup.cactupao.dobdayo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobDay);
    // COB(2942): MOVE ACUP-NEW-CUST-FIRST-NAME       TO ACSFNAMO OF CACTUPAO
    ws.coactup.cactupao.acsfnamo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFirstName);
    // COB(2943): MOVE ACUP-NEW-CUST-MIDDLE-NAME      TO ACSMNAMO OF CACTUPAO
    ws.coactup.cactupao.acsmnamo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustMiddleName);
    // COB(2944): MOVE ACUP-NEW-CUST-LAST-NAME        TO ACSLNAMO OF CACTUPAO
    ws.coactup.cactupao.acslnamo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustLastName);
    // COB(2945): MOVE ACUP-NEW-CUST-ADDR-LINE-1      TO ACSADL1O OF CACTUPAO
    ws.coactup.cactupao.acsadl1o.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine1);
    // COB(2946): MOVE ACUP-NEW-CUST-ADDR-LINE-2      TO ACSADL2O OF CACTUPAO
    ws.coactup.cactupao.acsadl2o.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine2);
    // COB(2947): MOVE ACUP-NEW-CUST-ADDR-LINE-3      TO ACSCITYO OF CACTUPAO
    ws.coactup.cactupao.acscityo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine3);
    // COB(2948): MOVE ACUP-NEW-CUST-ADDR-STATE-CD    TO ACSSTTEO OF CACTUPAO
    ws.coactup.cactupao.acsstteo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrStateCd);
    // COB(2949): MOVE ACUP-NEW-CUST-ADDR-ZIP         TO ACSZIPCO OF CACTUPAO
    ws.coactup.cactupao.acszipco.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrZip);
    // COB(2950): MOVE ACUP-NEW-CUST-ADDR-COUNTRY-CD  TO ACSCTRYO OF CACTUPAO
    ws.coactup.cactupao.acsctryo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrCountryCd);
    // COB(2951): MOVE ACUP-NEW-CUST-PHONE-NUM-1A     TO ACSPH1AO OF CACTUPAO
    ws.coactup.cactupao.acsph1ao.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum1X
            .acupNewCustPhoneNum1a);
    // COB(2952): MOVE ACUP-NEW-CUST-PHONE-NUM-1B     TO ACSPH1BO OF CACTUPAO
    ws.coactup.cactupao.acsph1bo.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum1X
            .acupNewCustPhoneNum1b);
    // COB(2953): MOVE ACUP-NEW-CUST-PHONE-NUM-1C     TO ACSPH1CO OF CACTUPAO
    ws.coactup.cactupao.acsph1co.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum1X
            .acupNewCustPhoneNum1c);
    // COB(2954): MOVE ACUP-NEW-CUST-PHONE-NUM-2A     TO ACSPH2AO OF CACTUPAO
    ws.coactup.cactupao.acsph2ao.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum2X
            .acupNewCustPhoneNum2a);
    // COB(2955): MOVE ACUP-NEW-CUST-PHONE-NUM-2B     TO ACSPH2BO OF CACTUPAO
    ws.coactup.cactupao.acsph2bo.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum2X
            .acupNewCustPhoneNum2b);
    // COB(2956): MOVE ACUP-NEW-CUST-PHONE-NUM-2C     TO ACSPH2CO OF CACTUPAO
    ws.coactup.cactupao.acsph2co.setValue(
        ws.wsThisProgcommarea
            .acupNewDetails
            .acupNewCustData
            .acupNewCustPhoneNum2X
            .acupNewCustPhoneNum2c);
    // COB(2957): MOVE ACUP-NEW-CUST-GOVT-ISSUED-ID   TO ACSGOVTO OF CACTUPAO
    ws.coactup.cactupao.acsgovto.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustGovtIssuedId);
    // COB(2958): MOVE ACUP-NEW-CUST-EFT-ACCOUNT-ID   TO ACSEFTCO OF CACTUPAO
    ws.coactup.cactupao.acseftco.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustEftAccountId);
    // COB(2959): MOVE ACUP-NEW-CUST-PRI-HOLDER-IND   TO ACSPFLGO OF CACTUPAO
    ws.coactup.cactupao.acspflgo.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPriHolderInd);
  }

  /***/
  protected void _3250SetupInfomsg() {
    // COB(2969): EVALUATE TRUE
    // COB(2970): WHEN CDEMO-PGM-ENTER
    if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()) {
      // COB(2971): SET  PROMPT-FOR-SEARCH-KEYS TO TRUE
      ws.wsMiscStorage.setPromptForSearchKeys(true);
      // COB(2972): WHEN ACUP-DETAILS-NOT-FETCHED
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched()) {
      // COB(2973): SET PROMPT-FOR-SEARCH-KEYS      TO TRUE
      ws.wsMiscStorage.setPromptForSearchKeys(true);
      // COB(2974): WHEN ACUP-SHOW-DETAILS
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupShowDetails()) {
      // COB(2975): SET PROMPT-FOR-CHANGES         TO TRUE
      ws.wsMiscStorage.setPromptForChanges(true);
      // COB(2976): WHEN ACUP-CHANGES-NOT-OK
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesNotOk()) {
      // COB(2977): SET PROMPT-FOR-CHANGES         TO TRUE
      ws.wsMiscStorage.setPromptForChanges(true);
      // COB(2978): WHEN ACUP-CHANGES-OK-NOT-CONFIRMED
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkNotConfirmed()) {
      // COB(2979): SET PROMPT-FOR-CONFIRMATION    TO TRUE
      ws.wsMiscStorage.setPromptForConfirmation(true);
      // COB(2980): WHEN ACUP-CHANGES-OKAYED-AND-DONE
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedAndDone()) {
      // COB(2981): SET CONFIRM-UPDATE-SUCCESS     TO TRUE
      ws.wsMiscStorage.setConfirmUpdateSuccess(true);
      // COB(2983): WHEN ACUP-CHANGES-OKAYED-LOCK-ERROR
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedLockError()) {
      // COB(2984): SET INFORM-FAILURE             TO TRUE
      ws.wsMiscStorage.setInformFailure(true);
      // COB(2985): WHEN ACUP-CHANGES-OKAYED-BUT-FAILED
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedButFailed()) {
      // COB(2986): SET INFORM-FAILURE             TO TRUE
      ws.wsMiscStorage.setInformFailure(true);
      // COB(2987): WHEN WS-NO-INFO-MESSAGE
    } else if (ws.wsMiscStorage.wsNoInfoMessage()) {
      // COB(2988): SET PROMPT-FOR-SEARCH-KEYS      TO TRUE
      ws.wsMiscStorage.setPromptForSearchKeys(true);
      // COB(2989): END-EVALUATE
    }
    // COB(2991): MOVE WS-INFO-MSG                    TO INFOMSGO OF CACTUPAO
    ws.coactup.cactupao.infomsgo.setValue(ws.wsMiscStorage.wsInfoMsg);
    // COB(2993): MOVE WS-RETURN-MSG                  TO ERRMSGO OF CACTUPAO
    ws.coactup.cactupao.errmsgo.setValue(ws.wsMiscStorage.wsReturnMsg);
  }

  protected void _3300SetupScreenAttrs() {
    // COB(3001): PERFORM 3310-PROTECT-ALL-ATTRS
    // COB(3001):    THRU 3310-PROTECT-ALL-ATTRS-EXIT
    _3310ProtectAllAttrs();
    // COB(3005): EVALUATE TRUE
    //       *
    //       *    UNPROTECT BASED ON CONTEXT
    // COB(3006): WHEN ACUP-DETAILS-NOT-FETCHED
    if (ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched()) {
      // COB(3008): MOVE DFHBMFSE      TO ACCTSIDA OF CACTUPAI
      //       *            Make Account Id editable
      ws.coactup.cactupai.filler57.acctsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(3009): WHEN  ACUP-SHOW-DETAILS
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupShowDetails()
        // COB(3010): WHEN  ACUP-CHANGES-NOT-OK
        || ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesNotOk()) {
      // COB(3011): PERFORM 3320-UNPROTECT-FEW-ATTRS
      // COB(3011):    THRU 3320-UNPROTECT-FEW-ATTRS-EXIT
      _3320UnprotectFewAttrs();
      // COB(3013): WHEN ACUP-CHANGES-OK-NOT-CONFIRMED
    } else if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkNotConfirmed()
        // COB(3014): WHEN ACUP-CHANGES-OKAYED-AND-DONE
        || ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedAndDone()) {
      // COB(3015): CONTINUE
      // do nothing
      // COB(3016): WHEN OTHER
    } else {
      // COB(3017): MOVE DFHBMFSE      TO ACCTSIDA OF CACTUPAI
      ws.coactup.cactupai.filler57.acctsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(3018): END-EVALUATE
    }
    // COB(3021): EVALUATE TRUE
    //       *
    //       *    POSITION CURSOR - ORDER BASED ON SCREEN LOCATION
    // COB(3022): WHEN FOUND-ACCOUNT-DATA
    if ((ws.wsMiscStorage.foundAccountData())
        // COB(3023): WHEN NO-CHANGES-DETECTED
        || ws.wsMiscStorage.noChangesDetected()) {
      // COB(3024): MOVE -1              TO ACSTTUSL OF CACTUPAI
      ws.coactup.cactupai.acsttusl.setValue(-1);
      // COB(3025): WHEN FLG-ACCTFILTER-NOT-OK
    } else if (ws.wsMiscStorage.flgAcctfilterNotOk()
        // COB(3026): WHEN FLG-ACCTFILTER-BLANK
        || ws.wsMiscStorage.flgAcctfilterBlank()) {
      // COB(3027): MOVE -1             TO ACCTSIDL OF CACTUPAI
      ws.coactup.cactupai.acctsidl.setValue(-1);
      // COB(3029): WHEN FLG-ACCT-STATUS-NOT-OK
      //       *    Account Status
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgAcctStatusNotOk()
        // COB(3030): WHEN FLG-ACCT-STATUS-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgAcctStatusBlank()) {
      // COB(3031): MOVE -1              TO ACSTTUSL OF CACTUPAI
      ws.coactup.cactupai.acsttusl.setValue(-1);
      // COB(3033): WHEN FLG-OPEN-YEAR-NOT-OK
      //       *    Open Year
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenYearNotOk()
        // COB(3034): WHEN FLG-OPEN-YEAR-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenYearBlank()) {
      // COB(3035): MOVE -1              TO OPNYEARL OF CACTUPAI
      ws.coactup.cactupai.opnyearl.setValue(-1);
      // COB(3037): WHEN FLG-OPEN-MONTH-NOT-OK
      //       *    Open Month
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenMonthNotOk()
        // COB(3038): WHEN FLG-OPEN-MONTH-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenMonthBlank()) {
      // COB(3039): MOVE -1              TO OPNMONL  OF CACTUPAI
      ws.coactup.cactupai.opnmonl.setValue(-1);
      // COB(3041): WHEN FLG-OPEN-DAY-NOT-OK
      //       *    Open Day
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenDayNotOk()
        // COB(3042): WHEN FLG-OPEN-DAY-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditOpenDateFlgs.flgOpenDayBlank()) {
      // COB(3043): MOVE -1              TO OPNDAYL  OF CACTUPAI
      ws.coactup.cactupai.opndayl.setValue(-1);
      // COB(3045): WHEN FLG-CRED-LIMIT-NOT-OK
      //       *    Credit Limit
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgCredLimitNotOk()
        // COB(3046): WHEN FLG-CRED-LIMIT-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgCredLimitBlank()) {
      // COB(3047): MOVE -1              TO ACRDLIML OF CACTUPAI
      ws.coactup.cactupai.acrdliml.setValue(-1);
      // COB(3049): WHEN FLG-EXPIRY-YEAR-NOT-OK
      //       *    Expiry Year
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryYearNotOk()
        // COB(3050): WHEN FLG-EXPIRY-YEAR-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryYearBlank()) {
      // COB(3051): MOVE -1              TO EXPYEARL OF CACTUPAI
      ws.coactup.cactupai.expyearl.setValue(-1);
      // COB(3053): WHEN FLG-EXPIRY-MONTH-NOT-OK
      //       *    Expiry Month
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryMonthNotOk()
        // COB(3054): WHEN FLG-EXPIRY-MONTH-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryMonthBlank()) {
      // COB(3055): MOVE -1              TO EXPMONL  OF CACTUPAI
      ws.coactup.cactupai.expmonl.setValue(-1);
      // COB(3057): WHEN FLG-EXPIRY-DAY-NOT-OK
      //       *    Expiry Day
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryDayNotOk()
        // COB(3058): WHEN FLG-EXPIRY-DAY-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsExpiryDateFlgs.flgExpiryDayBlank()) {
      // COB(3059): MOVE -1              TO EXPDAYL  OF CACTUPAI
      ws.coactup.cactupai.expdayl.setValue(-1);
      // COB(3061): WHEN FLG-CASH-CREDIT-LIMIT-NOT-OK
      //       *    Cash credit limit
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgCashCreditLimitNotOk()
        // COB(3062): WHEN FLG-CASH-CREDIT-LIMIT-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgCashCreditLimitBlank()) {
      // COB(3063): MOVE -1              TO ACSHLIML OF CACTUPAI
      ws.coactup.cactupai.acshliml.setValue(-1);
      // COB(3065): WHEN FLG-REISSUE-YEAR-NOT-OK
      //       *    Reissue Year
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueYearNotOk()
        // COB(3066): WHEN FLG-REISSUE-YEAR-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueYearBlank()) {
      // COB(3067): MOVE -1              TO RISYEARL OF CACTUPAI
      ws.coactup.cactupai.risyearl.setValue(-1);
      // COB(3069): WHEN FLG-REISSUE-MONTH-NOT-OK
      //       *    Expiry Month
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueMonthNotOk()
        // COB(3070): WHEN FLG-REISSUE-MONTH-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueMonthBlank()) {
      // COB(3071): MOVE -1              TO RISMONL  OF CACTUPAI
      ws.coactup.cactupai.rismonl.setValue(-1);
      // COB(3073): WHEN FLG-REISSUE-DAY-NOT-OK
      //       *    Expiry Day
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueDayNotOk()
        // COB(3074): WHEN FLG-REISSUE-DAY-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditReissueDateFlgs.flgReissueDayBlank()) {
      // COB(3075): MOVE -1              TO RISDAYL  OF CACTUPAI
      ws.coactup.cactupai.risdayl.setValue(-1);
      // COB(3078): WHEN FLG-CURR-BAL-NOT-OK
      //       *
      //       *    Current Balance
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrBalNotOk()
        // COB(3079): WHEN FLG-CURR-BAL-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgCurrBalBlank()) {
      // COB(3080): MOVE -1              TO ACURBALL OF CACTUPAI
      ws.coactup.cactupai.acurball.setValue(-1);
      // COB(3082): WHEN FLG-CURR-CYC-CREDIT-NOT-OK
      //       *    Current Cycle Credit
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycCreditNotOk()
        // COB(3083): WHEN FLG-CURR-CYC-CREDIT-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycCreditBlank()) {
      // COB(3084): MOVE -1              TO ACRCYCRL OF CACTUPAI
      ws.coactup.cactupai.acrcycrl.setValue(-1);
      // COB(3086): WHEN FLG-CURR-CYC-DEBIT-NOT-OK
      //       *    Current Cycle Debit
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycDebitNotOk()
        // COB(3087): WHEN FLG-CURR-CYC-DEBIT-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgCurrCycDebitBlank()) {
      // COB(3088): MOVE -1              TO ACRCYDBL OF CACTUPAI
      ws.coactup.cactupai.acrcydbl.setValue(-1);
      // COB(3090): WHEN FLG-EDIT-US-SSN-PART1-NOT-OK
      //       *    SSN Part 1
    } else if (ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart1NotOk()
        // COB(3091): WHEN FLG-EDIT-US-SSN-PART1-BLANK
        || ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart1Blank()) {
      // COB(3092): MOVE -1              TO ACTSSN1L OF CACTUPAI
      ws.coactup.cactupai.actssn1l.setValue(-1);
      // COB(3094): WHEN FLG-EDIT-US-SSN-PART2-NOT-OK
      //       *    SSN Part 2
    } else if (ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart2NotOk()
        // COB(3095): WHEN FLG-EDIT-US-SSN-PART2-BLANK
        || ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart2Blank()) {
      // COB(3096): MOVE -1              TO ACTSSN2L  OF CACTUPAI
      ws.coactup.cactupai.actssn2l.setValue(-1);
      // COB(3098): WHEN FLG-EDIT-US-SSN-PART3-NOT-OK
      //       *    SSN Part 3
    } else if (ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart3NotOk()
        // COB(3099): WHEN FLG-EDIT-US-SSN-PART3-BLANK
        || ws.wsMiscStorage.wsGenericEdits.wsEditUsSsnFlgs.flgEditUsSsnPart3Blank()) {
      // COB(3100): MOVE -1              TO ACTSSN3L  OF CACTUPAI
      ws.coactup.cactupai.actssn3l.setValue(-1);
      // COB(3102): WHEN FLG-DT-OF-BIRTH-YEAR-NOT-OK
      //       *    Date of Birth Year
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthYearNotOk()
        // COB(3103): WHEN FLG-DT-OF-BIRTH-YEAR-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthYearBlank()) {
      // COB(3104): MOVE -1              TO DOBYEARL OF CACTUPAI
      ws.coactup.cactupai.dobyearl.setValue(-1);
      // COB(3106): WHEN FLG-DT-OF-BIRTH-MONTH-NOT-OK
      //       *    Date of Birth Month
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthMonthNotOk()
        // COB(3107): WHEN FLG-DT-OF-BIRTH-MONTH-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthMonthBlank()) {
      // COB(3108): MOVE -1              TO DOBMONL  OF CACTUPAI
      ws.coactup.cactupai.dobmonl.setValue(-1);
      // COB(3110): WHEN FLG-DT-OF-BIRTH-DAY-NOT-OK
      //       *    Date of Birth Day
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthDayNotOk()
        // COB(3111): WHEN FLG-DT-OF-BIRTH-DAY-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditDtOfBirthFlgs.flgDtOfBirthDayBlank()) {
      // COB(3112): MOVE -1              TO DOBDAYL  OF CACTUPAI
      ws.coactup.cactupai.dobdayl.setValue(-1);
      // COB(3114): WHEN FLG-FICO-SCORE-NOT-OK
      //       *    FICO Score
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgFicoScoreNotOk()
        // COB(3115): WHEN FLG-FICO-SCORE-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgFicoScoreBlank()) {
      // COB(3116): MOVE -1              TO ACSTFCOL OF CACTUPAI
      ws.coactup.cactupai.acstfcol.setValue(-1);
      // COB(3118): WHEN FLG-FIRST-NAME-NOT-OK
      //       *    First Name
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgFirstNameNotOk()
        // COB(3119): WHEN FLG-FIRST-NAME-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgFirstNameBlank()) {
      // COB(3120): MOVE -1              TO ACSFNAML OF CACTUPAI
      ws.coactup.cactupai.acsfnaml.setValue(-1);
      // COB(3122): WHEN FLG-MIDDLE-NAME-NOT-OK
      //       *    Middle Name
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgMiddleNameNotOk()) {
      // COB(3123): MOVE -1              TO ACSMNAML OF CACTUPAI
      ws.coactup.cactupai.acsmnaml.setValue(-1);
      // COB(3125): WHEN FLG-LAST-NAME-NOT-OK
      //       *    Last Name
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgLastNameNotOk()
        // COB(3126): WHEN FLG-LAST-NAME-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditNameFlags.flgLastNameBlank()) {
      // COB(3127): MOVE -1              TO ACSLNAML OF CACTUPAI
      ws.coactup.cactupai.acslnaml.setValue(-1);
      // COB(3129): WHEN FLG-ADDRESS-LINE-1-NOT-OK
      //       *    Address Line 1
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgAddressLine1NotOk()
        // COB(3130): WHEN FLG-ADDRESS-LINE-1-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgAddressLine1Blank()) {
      // COB(3131): MOVE -1              TO ACSADL1L OF CACTUPAI
      ws.coactup.cactupai.acsadl1l.setValue(-1);
      // COB(3133): WHEN FLG-STATE-NOT-OK
      //       *    State (appears next to Line 2 on screen before city)
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgStateNotOk()
        // COB(3134): WHEN FLG-STATE-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgStateBlank()) {
      // COB(3135): MOVE -1              TO ACSSTTEL OF CACTUPAI
      ws.coactup.cactupai.acssttel.setValue(-1);
      // COB(3138): WHEN FLG-ZIPCODE-NOT-OK
      //       *    Address Line 2 has no edits
      //       *    Zip code
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgZipcodeNotOk()
        // COB(3139): WHEN FLG-ZIPCODE-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgZipcodeBlank()) {
      // COB(3140): MOVE -1              TO ACSZIPCL OF CACTUPAI
      ws.coactup.cactupai.acszipcl.setValue(-1);
      // COB(3142): WHEN FLG-CITY-NOT-OK
      //       *    Address Line 3 (City)
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgCityNotOk()
        // COB(3143): WHEN FLG-CITY-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgCityBlank()) {
      // COB(3144): MOVE -1              TO ACSCITYL OF CACTUPAI
      ws.coactup.cactupai.acscityl.setValue(-1);
      // COB(3146): WHEN FLG-COUNTRY-NOT-OK
      //       *    Country edits.
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgCountryNotOk()
        // COB(3147): WHEN FLG-COUNTRY-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.flgCountryBlank()) {
      // COB(3148): MOVE -1              TO ACSCTRYL OF CACTUPAI
      ws.coactup.cactupai.acsctryl.setValue(-1);
      // COB(3150): WHEN FLG-PHONE-NUM-1A-NOT-OK
      //       *    Phone 1
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
            .flgPhoneNum1aNotOk()
        // COB(3151): WHEN FLG-PHONE-NUM-1A-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
            .flgPhoneNum1aBlank()) {
      // COB(3152): MOVE -1              TO ACSPH1AL OF CACTUPAI
      ws.coactup.cactupai.acsph1al.setValue(-1);
      // COB(3153): WHEN FLG-PHONE-NUM-1B-NOT-OK
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
            .flgPhoneNum1bNotOk()
        // COB(3154): WHEN FLG-PHONE-NUM-1B-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
            .flgPhoneNum1bBlank()) {
      // COB(3155): MOVE -1              TO ACSPH1BL OF CACTUPAI
      ws.coactup.cactupai.acsph1bl.setValue(-1);
      // COB(3156): WHEN FLG-PHONE-NUM-1C-NOT-OK
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
            .flgPhoneNum1cNotOk()
        // COB(3157): WHEN FLG-PHONE-NUM-1C-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum1Flgs
            .flgPhoneNum1cBlank()) {
      // COB(3158): MOVE -1              TO ACSPH1CL OF CACTUPAI
      ws.coactup.cactupai.acsph1cl.setValue(-1);
      // COB(3160): WHEN FLG-PHONE-NUM-2A-NOT-OK
      //       *    Phone 2
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
            .flgPhoneNum2aNotOk()
        // COB(3161): WHEN FLG-PHONE-NUM-2A-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
            .flgPhoneNum2aBlank()) {
      // COB(3162): MOVE -1              TO ACSPH2AL OF CACTUPAI
      ws.coactup.cactupai.acsph2al.setValue(-1);
      // COB(3163): WHEN FLG-PHONE-NUM-2B-NOT-OK
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
            .flgPhoneNum2bNotOk()
        // COB(3164): WHEN FLG-PHONE-NUM-2B-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
            .flgPhoneNum2bBlank()) {
      // COB(3165): MOVE -1              TO ACSPH2BL OF CACTUPAI
      ws.coactup.cactupai.acsph2bl.setValue(-1);
      // COB(3166): WHEN FLG-PHONE-NUM-2C-NOT-OK
    } else if (ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
            .flgPhoneNum2cNotOk()
        // COB(3167): WHEN FLG-PHONE-NUM-2C-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.wsEditAddressFlags.wsEditPhoneNum2Flgs
            .flgPhoneNum2cBlank()) {
      // COB(3168): MOVE -1              TO ACSPH2CL OF CACTUPAI
      ws.coactup.cactupai.acsph2cl.setValue(-1);
      // COB(3170): WHEN FLG-EFT-ACCOUNT-ID-NOT-OK
      //       *    EFT Account Id
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgEftAccountIdNotOk()
        // COB(3171): WHEN FLG-EFT-ACCOUNT-ID-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgEftAccountIdBlank()) {
      // COB(3172): MOVE -1              TO ACSEFTCL OF CACTUPAI
      ws.coactup.cactupai.acseftcl.setValue(-1);
      // COB(3174): WHEN FLG-PRI-CARDHOLDER-NOT-OK
      //       *    Primary Card Holder
    } else if (ws.wsMiscStorage.wsNonKeyFlags.flgPriCardholderNotOk()
        // COB(3175): WHEN FLG-PRI-CARDHOLDER-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgPriCardholderBlank()) {
      // COB(3176): MOVE -1              TO ACSPFLGL OF CACTUPAI
      ws.coactup.cactupai.acspflgl.setValue(-1);
      // COB(3177): WHEN OTHER
    } else {
      // COB(3178): MOVE -1              TO ACCTSIDL OF CACTUPAI
      ws.coactup.cactupai.acctsidl.setValue(-1);
      // COB(3179): END-EVALUATE
    }
    // COB(3183): IF CDEMO-LAST-MAPSET   EQUAL LIT-CCLISTMAPSET
    //       *
    //       *
    //       *    SETUP COLOR
    if (ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.equals(
        ws.wsLiterals.litCclistmapset)) {
      // COB(3184): MOVE DFHDFCOL            TO ACCTSIDC OF CACTUPAO
      ws.coactup.cactupao.acctsidc.setValue(Dfhbmsca.DFHDFCOL.getValue());
      // COB(3185): END-IF
    }
    // COB(3188): IF FLG-ACCTFILTER-NOT-OK
    //       *
    //       *    Account Filter
    if (ws.wsMiscStorage.flgAcctfilterNotOk()) {
      // COB(3189): MOVE DFHRED              TO ACCTSIDC OF CACTUPAO
      ws.coactup.cactupao.acctsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(3190): END-IF
    }
    // COB(3192): IF  FLG-ACCTFILTER-BLANK
    // COB(3192): AND CDEMO-PGM-REENTER
    if (ws.wsMiscStorage.flgAcctfilterBlank()
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(3194): MOVE '*'                TO ACCTSIDO OF CACTUPAO
      ws.coactup.cactupao.acctsido.setString("*");
      // COB(3195): MOVE DFHRED             TO ACCTSIDC OF CACTUPAO
      ws.coactup.cactupao.acctsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(3196): END-IF
    }
    // COB(3198): IF ACUP-DETAILS-NOT-FETCHED
    // COB(3198): OR FLG-ACCTFILTER-BLANK
    // COB(3198): OR FLG-ACCTFILTER-NOT-OK
    if (ws.wsThisProgcommarea.acctUpdateScreenData.acupDetailsNotFetched()
        || ws.wsMiscStorage.flgAcctfilterBlank()
        || ws.wsMiscStorage.flgAcctfilterNotOk()) {
      // COB(3201): GO TO 3300-SETUP-SCREEN-ATTRS-EXIT
      return;
      // COB(3202): ELSE
    } else {
      // COB(3203): CONTINUE
      // do nothing
      // COB(3204): END-IF
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    //       * Copyright Amazon.com, Inc. or its affiliates.
    //       * All Rights Reserved.
    //       *
    //       * Licensed under the Apache License, Version 2.0 (the "License").
    //       * You may not use this file except in compliance with the License.
    //       * You may obtain a copy of the License at
    //       *
    //       *    http:--www.apache.org-licenses-LICENSE-2.0
    //       *
    //       * Unless required by applicable law or agreed to in writing,
    //       * software distributed under the License is distributed on an
    //       * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    //       * either express or implied. See the License for the specific
    //       * language governing permissions and limitations under the License
    //       ******************************************************************
    //       *    Set (TESTVAR1) to red if in error and * if blankACSHLIM
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
    // COB(3454): MOVE DFHBMPRF              TO ACCTSIDA OF CACTUPAI
    // COB(3454):                               ACSTTUSA OF CACTUPAI
    // COB(3454):       *Account Limits
    // COB(3454):                               ACRDLIMA OF CACTUPAI
    // COB(3454):                               ACSHLIMA OF CACTUPAI
    // COB(3454):                               ACURBALA OF CACTUPAI
    // COB(3454):                               ACRCYCRA OF CACTUPAI
    // COB(3454):                               ACRCYDBA OF CACTUPAI
    // COB(3454):       *Account dates
    // COB(3454):                               OPNYEARA OF CACTUPAI
    // COB(3454):                               OPNMONA  OF CACTUPAI
    // COB(3454):                               OPNDAYA  OF CACTUPAI
    // COB(3454):                               EXPYEARA OF CACTUPAI
    // COB(3454):                               EXPMONA  OF CACTUPAI
    // COB(3454):                               EXPDAYA  OF CACTUPAI
    // COB(3454):                               RISYEARA OF CACTUPAI
    // COB(3454):                               RISMONA  OF CACTUPAI
    // COB(3454):                               RISDAYA  OF CACTUPAI
    // COB(3454):       *
    // COB(3454):                               AADDGRPA OF CACTUPAI
    // COB(3454):       *Customer data
    // COB(3454):                               ACSTNUMA OF CACTUPAI
    // COB(3454):                               ACTSSN1A OF CACTUPAI
    // COB(3454):                               ACTSSN2A OF CACTUPAI
    // COB(3454):                               ACTSSN3A OF CACTUPAI
    // COB(3454):                               ACSTFCOA OF CACTUPAI
    // COB(3454):       *Date of Birth
    // COB(3454):                               DOBYEARA OF CACTUPAI
    // COB(3454):                               DOBMONA  OF CACTUPAI
    // COB(3454):                               DOBDAYA  OF CACTUPAI
    // COB(3454):       *
    // COB(3454):                               ACSFNAMA OF CACTUPAI
    // COB(3454):                               ACSMNAMA OF CACTUPAI
    // COB(3454):                               ACSLNAMA OF CACTUPAI
    // COB(3454):       *Address
    // COB(3454):                               ACSADL1A OF CACTUPAI
    // COB(3454):                               ACSADL2A OF CACTUPAI
    // COB(3454):                               ACSCITYA OF CACTUPAI
    // COB(3454):                               ACSSTTEA OF CACTUPAI
    // COB(3454):                               ACSZIPCA OF CACTUPAI
    // COB(3454):                               ACSCTRYA OF CACTUPAI
    // COB(3454):       *
    // COB(3454):                               ACSPH1AA OF CACTUPAI
    // COB(3454):                               ACSPH1BA OF CACTUPAI
    // COB(3454):                               ACSPH1CA OF CACTUPAI
    // COB(3454):                               ACSPH2AA OF CACTUPAI
    // COB(3454):                               ACSPH2BA OF CACTUPAI
    // COB(3454):                               ACSPH2CA OF CACTUPAI
    // COB(3454):       *
    // COB(3454):                               ACSGOVTA OF CACTUPAI
    // COB(3454):                               ACSEFTCA OF CACTUPAI
    // COB(3454):                               ACSPFLGA OF CACTUPAI
    // COB(3454):                               INFOMSGA OF CACTUPAI
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
    // COB(3514): MOVE DFHBMFSE              TO ACSTTUSA OF CACTUPAI
    // COB(3514):       *Account Limits
    // COB(3514):                               ACRDLIMA OF CACTUPAI
    // COB(3514):                               ACSHLIMA OF CACTUPAI
    // COB(3514):                               ACURBALA OF CACTUPAI
    // COB(3514):                               ACRCYCRA OF CACTUPAI
    // COB(3514):                               ACRCYDBA OF CACTUPAI
    // COB(3514):       *Account dates
    // COB(3514):       *Open Date
    // COB(3514):                               OPNYEARA OF CACTUPAI
    // COB(3514):                               OPNMONA  OF CACTUPAI
    // COB(3514):                               OPNDAYA  OF CACTUPAI
    // COB(3514):       *Expiry date
    // COB(3514):                               EXPYEARA OF CACTUPAI
    // COB(3514):                               EXPMONA  OF CACTUPAI
    // COB(3514):                               EXPDAYA  OF CACTUPAI
    // COB(3514):       *Reissue date
    // COB(3514):                               RISYEARA OF CACTUPAI
    // COB(3514):                               RISMONA  OF CACTUPAI
    // COB(3514):                               RISDAYA  OF CACTUPAI
    // COB(3514):       *Date of Birth
    // COB(3514):                               DOBYEARA OF CACTUPAI
    // COB(3514):                               DOBMONA  OF CACTUPAI
    // COB(3514):                               DOBDAYA  OF CACTUPAI
    // COB(3514):       *
    // COB(3514):       *
    // COB(3514):       *
    // COB(3514):                               AADDGRPA OF CACTUPAI
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
    // COB(3543): MOVE DFHBMPRF            TO  ACSTNUMA OF CACTUPAI
    //       *Customer data
    ws.coactup.cactupai.filler159.acstnuma.setValue(Dfhbmsca.DFHBMPRF.getValue());
    // COB(3544): MOVE DFHBMFSE            TO  ACTSSN1A OF CACTUPAI
    // COB(3544):                              ACTSSN2A OF CACTUPAI
    // COB(3544):                              ACTSSN3A OF CACTUPAI
    // COB(3544):                              ACSTFCOA OF CACTUPAI
    // COB(3544):       *
    // COB(3544):                              ACSFNAMA OF CACTUPAI
    // COB(3544):                              ACSMNAMA OF CACTUPAI
    // COB(3544):                              ACSLNAMA OF CACTUPAI
    // COB(3544):       *Address
    // COB(3544):                              ACSADL1A OF CACTUPAI
    // COB(3544):                              ACSADL2A OF CACTUPAI
    // COB(3544):                              ACSCITYA OF CACTUPAI
    // COB(3544):                              ACSSTTEA OF CACTUPAI
    // COB(3544):                              ACSZIPCA OF CACTUPAI
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
    // COB(3559): MOVE DFHBMPRF              TO ACSCTRYA OF CACTUPAI
    //       *Since most of the edits are USA specific protected country
    ws.coactup.cactupai.filler255.acsctrya.setValue(Dfhbmsca.DFHBMPRF.getValue());
    // COB(3561): MOVE DFHBMFSE              TO ACSPH1AA OF CACTUPAI
    // COB(3561):                               ACSPH1BA OF CACTUPAI
    // COB(3561):                               ACSPH1CA OF CACTUPAI
    ws.coactup.cactupai.filler261.acsph1aa.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler267.acsph1ba.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler273.acsph1ca.setValue(Dfhbmsca.DFHBMFSE.getValue());
    // COB(3565): MOVE DFHBMFSE              TO ACSPH2AA OF CACTUPAI
    // COB(3565):                               ACSPH2BA OF CACTUPAI
    // COB(3565):                               ACSPH2CA OF CACTUPAI
    // COB(3565):       *
    // COB(3565):                               ACSGOVTA OF CACTUPAI
    // COB(3565):                               ACSEFTCA OF CACTUPAI
    // COB(3565):                               ACSPFLGA OF CACTUPAI
    ws.coactup.cactupai.filler285.acsph2aa.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler291.acsph2ba.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler297.acsph2ca.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler279.acsgovta.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler303.acseftca.setValue(Dfhbmsca.DFHBMFSE.getValue());
    ws.coactup.cactupai.filler309.acspflga.setValue(Dfhbmsca.DFHBMFSE.getValue());
    // COB(3572): MOVE DFHBMPRF              TO INFOMSGA OF CACTUPAI
    ws.coactup.cactupai.filler315.infomsga.setValue(Dfhbmsca.DFHBMPRF.getValue());
  }

  /***/
  protected void _3390SetupInfomsgAttrs() {
    // COB(3579): IF  WS-NO-INFO-MESSAGE
    if (ws.wsMiscStorage.wsNoInfoMessage()) {
      // COB(3580): MOVE DFHBMDAR           TO INFOMSGA OF CACTUPAI
      ws.coactup.cactupai.filler315.infomsga.setValue(Dfhbmsca.DFHBMDAR.getValue());
      // COB(3581): ELSE
    } else {
      // COB(3582): MOVE DFHBMASB           TO INFOMSGA OF CACTUPAI
      ws.coactup.cactupai.filler315.infomsga.setValue(Dfhbmsca.DFHBMASB.getValue());
      // COB(3583): END-IF
    }
    // COB(3585): IF ACUP-CHANGES-MADE
    // COB(3585): AND NOT ACUP-CHANGES-OKAYED-AND-DONE
    if (ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesMade()
        && !ws.wsThisProgcommarea.acctUpdateScreenData.acupChangesOkayedAndDone()) {
      // COB(3587): MOVE DFHBMASB           TO FKEY12A  OF CACTUPAI
      ws.coactup.cactupai.filler339.fkey12a.setValue(Dfhbmsca.DFHBMASB.getValue());
      // COB(3588): END-IF
    }
    // COB(3590): IF PROMPT-FOR-CONFIRMATION
    if (ws.wsMiscStorage.promptForConfirmation()) {
      // COB(3591): MOVE DFHBMASB           TO FKEY05A  OF CACTUPAI
      ws.coactup.cactupai.filler333.fkey05a.setValue(Dfhbmsca.DFHBMASB.getValue());
      // COB(3592): MOVE DFHBMASB           TO FKEY12A  OF CACTUPAI
      ws.coactup.cactupai.filler339.fkey12a.setValue(Dfhbmsca.DFHBMASB.getValue());
      // COB(3593): END-IF
    }
  }

  /***
   */
  protected void _3400SendScreen() {
    // COB(3603): MOVE LIT-THISMAPSET         TO CCARD-NEXT-MAPSET
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsLiterals.litThismapset);
    // COB(3604): MOVE LIT-THISMAP            TO CCARD-NEXT-MAP
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsLiterals.litThismap);
    // COB(3606): EXEC CICS SEND MAP(CCARD-NEXT-MAP)
    // COB(3606):                MAPSET(CCARD-NEXT-MAPSET)
    // COB(3606):                FROM(CACTUPAO)
    // COB(3606):                CURSOR
    // COB(3606):                ERASE
    // COB(3606):                FREEKB
    // COB(3606):                RESP(WS-RESP-CD)
    // COB(3606): END-EXEC
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
    // COB(3622): INITIALIZE ACUP-OLD-DETAILS
    ws.wsThisProgcommarea.acupOldDetails.initialize();
    // COB(3624): SET  WS-NO-INFO-MESSAGE      TO TRUE
    ws.wsMiscStorage.setWsNoInfoMessage(true);
    // COB(3626): MOVE CC-ACCT-ID              TO ACUP-OLD-ACCT-ID
    // COB(3626):                                 WS-CARD-RID-ACCT-ID
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldAcctId.setValue(
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
    ws.wsMiscStorage.wsXrefRid.wsCardRidAcctId.setValue(
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
    // COB(3629): PERFORM 9200-GETCARDXREF-BYACCT
    // COB(3629):    THRU 9200-GETCARDXREF-BYACCT-EXIT
    _9200GetcardxrefByacct();
    // COB(3632): IF FLG-ACCTFILTER-NOT-OK
    if (ws.wsMiscStorage.flgAcctfilterNotOk()) {
      // COB(3633): GO TO 9000-READ-ACCT-EXIT
      return;
      // COB(3634): END-IF
    }
    // COB(3636): PERFORM 9300-GETACCTDATA-BYACCT
    // COB(3636):    THRU 9300-GETACCTDATA-BYACCT-EXIT
    _9300GetacctdataByacct();
    // COB(3639): IF DID-NOT-FIND-ACCT-IN-ACCTDAT
    if (ws.wsMiscStorage.didNotFindAcctInAcctdat()) {
      // COB(3640): GO TO 9000-READ-ACCT-EXIT
      return;
      // COB(3641): END-IF
    }
    // COB(3643): MOVE CDEMO-CUST-ID TO WS-CARD-RID-CUST-ID
    ws.wsMiscStorage.wsXrefRid.wsCardRidCustId.setValue(
        ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustId);
    // COB(3645): PERFORM 9400-GETCUSTDATA-BYCUST
    // COB(3645):    THRU 9400-GETCUSTDATA-BYCUST-EXIT
    _9400GetcustdataBycust();
    // COB(3648): IF DID-NOT-FIND-CUST-IN-CUSTDAT
    if (ws.wsMiscStorage.didNotFindCustInCustdat()) {
      // COB(3649): GO TO 9000-READ-ACCT-EXIT
      return;
      // COB(3650): END-IF
    }
    // COB(3654): PERFORM 9500-STORE-FETCHED-DATA
    // COB(3654):    THRU 9500-STORE-FETCHED-DATA-EXIT
    //       *
    //       *
    //       *
    _9500StoreFetchedData();
  }

  protected void _9200GetcardxrefByacct() {
    // COB(3666): EXEC CICS READ
    // COB(3666):      DATASET   (LIT-CARDXREFNAME-ACCT-PATH)
    // COB(3666):      RIDFLD    (WS-CARD-RID-ACCT-ID-X)
    // COB(3666):      KEYLENGTH (LENGTH OF WS-CARD-RID-ACCT-ID-X)
    // COB(3666):      INTO      (CARD-XREF-RECORD)
    // COB(3666):      LENGTH    (LENGTH OF CARD-XREF-RECORD)
    // COB(3666):      RESP      (WS-RESP-CD)
    // COB(3666):      RESP2     (WS-REAS-CD)
    // COB(3666): END-EXEC
    supernaut
        .read(ws.wsLiterals.litCardxrefnameAcctPath.getValue()) //
        .length(ws.cvact03y.cardXrefRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvact03y.cardXrefRecord) //
        .ridfld(ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX) //
        .keylength(ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX.length()) //
        .exec();
    // COB(3676): EVALUATE WS-RESP-CD
    switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
        // COB(3677): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(3678): MOVE XREF-CUST-ID               TO CDEMO-CUST-ID
        ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustId.setValue(
            ws.cvact03y.cardXrefRecord.xrefCustId);
        // COB(3679): MOVE XREF-CARD-NUM              TO CDEMO-CARD-NUM
        ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.setValue(
            ws.cvact03y.cardXrefRecord.xrefCardNum);
        // COB(3680): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(3681): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(3682): SET FLG-ACCTFILTER-NOT-OK       TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(3683): IF WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(3684): MOVE WS-RESP-CD               TO ERROR-RESP
          ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
          // COB(3685): MOVE WS-REAS-CD               TO ERROR-RESP2
          ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
          // COB(3686): STRING
          // COB(3686): 'Account:'
          // COB(3686):  WS-CARD-RID-ACCT-ID-X
          // COB(3686): ' not found in'
          // COB(3686): ' Cross ref file.  Resp:'
          // COB(3686): ERROR-RESP
          // COB(3686): ' Reas:'
          // COB(3686): ERROR-RESP2
          // COB(3686): DELIMITED BY SIZE
          // COB(3686): INTO WS-RETURN-MSG
          // COB(3686): END-STRING
          ws.wsMiscStorage.wsReturnMsg.concat(
              "Account:",
              ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX,
              " not found in",
              " Cross ref file.  Resp:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp,
              " Reas:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp2);
          // COB(3696): END-STRING
          // COB(3697): END-IF
        }
        // COB(3698): WHEN OTHER
        break;
      default:
        // COB(3699): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(3700): SET FLG-ACCTFILTER-NOT-OK                TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(3701): MOVE 'READ'                     TO ERROR-OPNAME
        ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
        // COB(3702): MOVE LIT-CARDXREFNAME-ACCT-PATH TO ERROR-FILE
        ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(
            ws.wsLiterals.litCardxrefnameAcctPath);
        // COB(3703): MOVE WS-RESP-CD                 TO ERROR-RESP
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(3704): MOVE WS-REAS-CD                 TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(3705): MOVE WS-FILE-ERROR-MESSAGE      TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
        // COB(3708): END-EVALUATE
        //       *                                              WS-LONG-MSG
        //       *          PERFORM SEND-LONG-TEXT
    }
  }

  protected void _9300GetacctdataByacct() {
    // COB(3715): EXEC CICS READ
    // COB(3715):      DATASET   (LIT-ACCTFILENAME)
    // COB(3715):      RIDFLD    (WS-CARD-RID-ACCT-ID-X)
    // COB(3715):      KEYLENGTH (LENGTH OF WS-CARD-RID-ACCT-ID-X)
    // COB(3715):      INTO      (ACCOUNT-RECORD)
    // COB(3715):      LENGTH    (LENGTH OF ACCOUNT-RECORD)
    // COB(3715):      RESP      (WS-RESP-CD)
    // COB(3715):      RESP2     (WS-REAS-CD)
    // COB(3715): END-EXEC
    supernaut
        .read(ws.wsLiterals.litAcctfilename.getValue()) //
        .length(ws.cvact01y.accountRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvact01y.accountRecord) //
        .ridfld(ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX) //
        .keylength(ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX.length()) //
        .exec();
    // COB(3725): EVALUATE WS-RESP-CD
    switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
        // COB(3726): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(3727): SET FOUND-ACCT-IN-MASTER        TO TRUE
        ws.wsMiscStorage.wsFileReadFlags.setFoundAcctInMaster(true);
        // COB(3728): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(3729): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(3730): SET FLG-ACCTFILTER-NOT-OK       TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(3732): IF WS-RETURN-MSG-OFF
        //       *           SET DID-NOT-FIND-ACCT-IN-ACCTDAT TO TRUE
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(3733): MOVE WS-RESP-CD               TO ERROR-RESP
          ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
          // COB(3734): MOVE WS-REAS-CD               TO ERROR-RESP2
          ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
          // COB(3735): STRING
          // COB(3735): 'Account:'
          // COB(3735):  WS-CARD-RID-ACCT-ID-X
          // COB(3735): ' not found in'
          // COB(3735): ' Acct Master file.Resp:'
          // COB(3735): ERROR-RESP
          // COB(3735): ' Reas:'
          // COB(3735): ERROR-RESP2
          // COB(3735): DELIMITED BY SIZE
          // COB(3735): INTO WS-RETURN-MSG
          // COB(3735): END-STRING
          ws.wsMiscStorage.wsReturnMsg.concat(
              "Account:",
              ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX,
              " not found in",
              " Acct Master file.Resp:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp,
              " Reas:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp2);
          // COB(3745): END-STRING
          // COB(3746): END-IF
        }
        // COB(3748): WHEN OTHER
        break;
      default:
        // COB(3749): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(3750): SET FLG-ACCTFILTER-NOT-OK                TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(3751): MOVE 'READ'                     TO ERROR-OPNAME
        ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
        // COB(3752): MOVE LIT-ACCTFILENAME           TO ERROR-FILE
        ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(ws.wsLiterals.litAcctfilename);
        // COB(3753): MOVE WS-RESP-CD                 TO ERROR-RESP
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(3754): MOVE WS-REAS-CD                 TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(3755): MOVE WS-FILE-ERROR-MESSAGE      TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
        // COB(3758): END-EVALUATE
        //       *                                              WS-LONG-MSG
        //       *           PERFORM SEND-LONG-TEXT
    }
  }

  /***/
  protected void _9400GetcustdataBycust() {
    // COB(3765): EXEC CICS READ
    // COB(3765):      DATASET   (LIT-CUSTFILENAME)
    // COB(3765):      RIDFLD    (WS-CARD-RID-CUST-ID-X)
    // COB(3765):      KEYLENGTH (LENGTH OF WS-CARD-RID-CUST-ID-X)
    // COB(3765):      INTO      (CUSTOMER-RECORD)
    // COB(3765):      LENGTH    (LENGTH OF CUSTOMER-RECORD)
    // COB(3765):      RESP      (WS-RESP-CD)
    // COB(3765):      RESP2     (WS-REAS-CD)
    // COB(3765): END-EXEC
    supernaut
        .read(ws.wsLiterals.litCustfilename.getValue()) //
        .length(ws.cvcus01y.customerRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvcus01y.customerRecord) //
        .ridfld(ws.wsMiscStorage.wsXrefRid.wsCardRidCustIdX) //
        .keylength(ws.wsMiscStorage.wsXrefRid.wsCardRidCustIdX.length()) //
        .exec();
    // COB(3775): EVALUATE WS-RESP-CD
    switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
        // COB(3776): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(3777): SET FOUND-CUST-IN-MASTER        TO TRUE
        ws.wsMiscStorage.wsFileReadFlags.setFoundCustInMaster(true);
        // COB(3778): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(3779): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(3780): SET FLG-CUSTFILTER-NOT-OK       TO TRUE
        ws.wsMiscStorage.setFlgCustfilterNotOk(true);
        // COB(3782): MOVE WS-RESP-CD               TO ERROR-RESP
        //       *           SET DID-NOT-FIND-CUST-IN-CUSTDAT TO TRUE
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(3783): MOVE WS-REAS-CD               TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(3784): IF WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(3785): STRING
          // COB(3785): 'CustId:'
          // COB(3785):  WS-CARD-RID-CUST-ID-X
          // COB(3785): ' not found'
          // COB(3785): ' in customer master.Resp: '
          // COB(3785): ERROR-RESP
          // COB(3785): ' REAS:'
          // COB(3785): ERROR-RESP2
          // COB(3785): DELIMITED BY SIZE
          // COB(3785): INTO WS-RETURN-MSG
          // COB(3785): END-STRING
          ws.wsMiscStorage.wsReturnMsg.concat(
              "CustId:",
              ws.wsMiscStorage.wsXrefRid.wsCardRidCustIdX,
              " not found",
              " in customer master.Resp: ",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp,
              " REAS:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp2);
          // COB(3795): END-STRING
          // COB(3796): END-IF
        }
        // COB(3797): WHEN OTHER
        break;
      default:
        // COB(3798): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(3799): SET FLG-CUSTFILTER-NOT-OK                TO TRUE
        ws.wsMiscStorage.setFlgCustfilterNotOk(true);
        // COB(3800): MOVE 'READ'                     TO ERROR-OPNAME
        ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
        // COB(3801): MOVE LIT-CUSTFILENAME           TO ERROR-FILE
        ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(ws.wsLiterals.litCustfilename);
        // COB(3802): MOVE WS-RESP-CD                 TO ERROR-RESP
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(3803): MOVE WS-REAS-CD                 TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(3804): MOVE WS-FILE-ERROR-MESSAGE      TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
        // COB(3807): END-EVALUATE
        //       *                                              WS-LONG-MSG
        //       *           PERFORM SEND-LONG-TEXT
    }
  }

  /***/
  protected void _9500StoreFetchedData() {
    // COB(3817): MOVE ACCT-ID                   TO CDEMO-ACCT-ID
    ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.setValue(
        ws.cvact01y.accountRecord.acctId);
    // COB(3818): MOVE CUST-ID                   TO CDEMO-CUST-ID
    ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustId.setValue(
        ws.cvcus01y.customerRecord.custId);
    // COB(3819): MOVE CUST-FIRST-NAME           TO CDEMO-CUST-FNAME
    ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustFname.setValue(
        ws.cvcus01y.customerRecord.custFirstName);
    // COB(3820): MOVE CUST-MIDDLE-NAME          TO CDEMO-CUST-MNAME
    ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustMname.setValue(
        ws.cvcus01y.customerRecord.custMiddleName);
    // COB(3821): MOVE CUST-LAST-NAME            TO CDEMO-CUST-LNAME
    ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustLname.setValue(
        ws.cvcus01y.customerRecord.custLastName);
    // COB(3822): MOVE ACCT-ACTIVE-STATUS        TO CDEMO-ACCT-STATUS
    ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctStatus.setValue(
        ws.cvact01y.accountRecord.acctActiveStatus);
    // COB(3823): MOVE XREF-CARD-NUM             TO CDEMO-CARD-NUM
    ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.setValue(
        ws.cvact03y.cardXrefRecord.xrefCardNum);
    // COB(3825): INITIALIZE ACUP-OLD-DETAILS
    ws.wsThisProgcommarea.acupOldDetails.initialize();
    // COB(3829): MOVE ACCT-ID                  TO ACUP-OLD-ACCT-ID
    //       ******************************************************************
    //       *    Account Master data
    //       ******************************************************************
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldAcctId.setValue(
        ws.cvact01y.accountRecord.acctId);
    // COB(3831): MOVE ACCT-ACTIVE-STATUS       TO ACUP-OLD-ACTIVE-STATUS
    //       * Active Status
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldActiveStatus.setValue(
        ws.cvact01y.accountRecord.acctActiveStatus);
    // COB(3833): MOVE ACCT-CURR-BAL            TO ACUP-OLD-CURR-BAL-N
    //       * Current Balance
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrBalN.setValue(
        ws.cvact01y.accountRecord.acctCurrBal);
    // COB(3835): MOVE ACCT-CREDIT-LIMIT        TO ACUP-OLD-CREDIT-LIMIT-N
    //       * Credit Limit
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCreditLimitN.setValue(
        ws.cvact01y.accountRecord.acctCreditLimit);
    // COB(3837): MOVE ACCT-CASH-CREDIT-LIMIT   TO ACUP-OLD-CASH-CREDIT-LIMIT-N
    //       * Cash Limit
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCashCreditLimitN.setValue(
        ws.cvact01y.accountRecord.acctCashCreditLimit);
    // COB(3839): MOVE ACCT-CURR-CYC-CREDIT     TO ACUP-OLD-CURR-CYC-CREDIT-N
    //       * Current Cycle Credit
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrCycCreditN.setValue(
        ws.cvact01y.accountRecord.acctCurrCycCredit);
    // COB(3841): MOVE ACCT-CURR-CYC-DEBIT      TO ACUP-OLD-CURR-CYC-DEBIT-N
    //       * Current Cycle Debit
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldCurrCycDebitN.setValue(
        ws.cvact01y.accountRecord.acctCurrCycDebit);
    // COB(3844): MOVE ACCT-OPEN-DATE(1:4)      TO ACUP-OLD-OPEN-YEAR
    //       * Open date
    //       *    MOVE ACCT-OPEN-DATE           TO ACUP-OLD-OPEN-DATE
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldOpenDateParts.acupOldOpenYear
        .setValue(ws.cvact01y.accountRecord.acctOpenDate, 0, 4);
    // COB(3845): MOVE ACCT-OPEN-DATE(6:2)      TO ACUP-OLD-OPEN-MON
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldOpenDateParts.acupOldOpenMon
        .setValue(ws.cvact01y.accountRecord.acctOpenDate, 5, 0, 2);
    // COB(3846): MOVE ACCT-OPEN-DATE(9:2)      TO ACUP-OLD-OPEN-DAY
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldOpenDateParts.acupOldOpenDay
        .setValue(ws.cvact01y.accountRecord.acctOpenDate, 8, 0, 2);
    // COB(3849): MOVE ACCT-EXPIRAION-DATE(1:4) TO ACUP-OLD-EXP-YEAR
    //       * Expiry date
    //       *    MOVE ACCT-EXPIRAION-DATE      TO ACUP-OLD-EXPIRAION-DATE
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldExpiraionDateParts.acupOldExpYear
        .setValue(ws.cvact01y.accountRecord.acctExpiraionDate, 0, 4);
    // COB(3850): MOVE ACCT-EXPIRAION-DATE(6:2) TO ACUP-OLD-EXP-MON
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldExpiraionDateParts.acupOldExpMon
        .setValue(ws.cvact01y.accountRecord.acctExpiraionDate, 5, 0, 2);
    // COB(3851): MOVE ACCT-EXPIRAION-DATE(9:2) TO ACUP-OLD-EXP-DAY
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldExpiraionDateParts.acupOldExpDay
        .setValue(ws.cvact01y.accountRecord.acctExpiraionDate, 8, 0, 2);
    // COB(3855): MOVE ACCT-REISSUE-DATE(1:4)   TO ACUP-OLD-REISSUE-YEAR
    //       *
    //       * Reissue date
    //       *    MOVE ACCT-REISSUE-DATE        TO ACUP-OLD-REISSUE-DATE
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldReissueDateParts.acupOldReissueYear
        .setValue(ws.cvact01y.accountRecord.acctReissueDate, 0, 4);
    // COB(3856): MOVE ACCT-REISSUE-DATE(6:2)   TO ACUP-OLD-REISSUE-MON
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldReissueDateParts.acupOldReissueMon
        .setValue(ws.cvact01y.accountRecord.acctReissueDate, 5, 0, 2);
    // COB(3857): MOVE ACCT-REISSUE-DATE(9:2)   TO ACUP-OLD-REISSUE-DAY
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldReissueDateParts.acupOldReissueDay
        .setValue(ws.cvact01y.accountRecord.acctReissueDate, 8, 0, 2);
    // COB(3859): MOVE ACCT-GROUP-ID            TO ACUP-OLD-GROUP-ID
    //       * Account Group
    ws.wsThisProgcommarea.acupOldDetails.acupOldAcctData.acupOldGroupId.setValue(
        ws.cvact01y.accountRecord.acctGroupId);
    // COB(3864): MOVE CUST-ID                  TO ACUP-OLD-CUST-ID
    //       ******************************************************************
    //       *    Customer Master data
    //       ******************************************************************
    //       *Customer Id (actually not editable)
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustId.setValue(
        ws.cvcus01y.customerRecord.custId);
    // COB(3866): MOVE CUST-SSN                 TO ACUP-OLD-CUST-SSN
    //       *Social Security Number
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustSsn.setValue(
        ws.cvcus01y.customerRecord.custSsn);
    // COB(3869): MOVE CUST-DOB-YYYY-MM-DD(1:4) TO ACUP-OLD-CUST-DOB-YEAR
    //       *Date of birth
    //       *    MOVE CUST-DOB-YYYY-MM-DD      TO ACUP-OLD-CUST-DOB-YYYY-MM-DD
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustDobParts.acupOldCustDobYear
        .setValue(ws.cvcus01y.customerRecord.custDobYyyyMmDd, 0, 4);
    // COB(3870): MOVE CUST-DOB-YYYY-MM-DD(6:2) TO ACUP-OLD-CUST-DOB-MON
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustDobParts.acupOldCustDobMon
        .setValue(ws.cvcus01y.customerRecord.custDobYyyyMmDd, 5, 0, 2);
    // COB(3871): MOVE CUST-DOB-YYYY-MM-DD(9:2) TO ACUP-OLD-CUST-DOB-DAY
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustDobParts.acupOldCustDobDay
        .setValue(ws.cvcus01y.customerRecord.custDobYyyyMmDd, 8, 0, 2);
    // COB(3873): MOVE CUST-FICO-CREDIT-SCORE   TO ACUP-OLD-CUST-FICO-SCORE
    //       *FICO
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustFicoScore.setValue(
        ws.cvcus01y.customerRecord.custFicoCreditScore);
    // COB(3875): MOVE CUST-FIRST-NAME          TO ACUP-OLD-CUST-FIRST-NAME
    //       *First Name
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustFirstName.setValue(
        ws.cvcus01y.customerRecord.custFirstName);
    // COB(3877): MOVE CUST-MIDDLE-NAME         TO ACUP-OLD-CUST-MIDDLE-NAME
    //       *Middle Name
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustMiddleName.setValue(
        ws.cvcus01y.customerRecord.custMiddleName);
    // COB(3879): MOVE CUST-LAST-NAME           TO ACUP-OLD-CUST-LAST-NAME
    //       *Last Name
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustLastName.setValue(
        ws.cvcus01y.customerRecord.custLastName);
    // COB(3881): MOVE CUST-ADDR-LINE-1         TO ACUP-OLD-CUST-ADDR-LINE-1
    //       *Address
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine1.setValue(
        ws.cvcus01y.customerRecord.custAddrLine1);
    // COB(3882): MOVE CUST-ADDR-LINE-2         TO ACUP-OLD-CUST-ADDR-LINE-2
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine2.setValue(
        ws.cvcus01y.customerRecord.custAddrLine2);
    // COB(3883): MOVE CUST-ADDR-LINE-3         TO ACUP-OLD-CUST-ADDR-LINE-3
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrLine3.setValue(
        ws.cvcus01y.customerRecord.custAddrLine3);
    // COB(3884): MOVE CUST-ADDR-STATE-CD       TO ACUP-OLD-CUST-ADDR-STATE-CD
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrStateCd.setValue(
        ws.cvcus01y.customerRecord.custAddrStateCd);
    // COB(3885): MOVE CUST-ADDR-COUNTRY-CD     TO
    // COB(3885):                                ACUP-OLD-CUST-ADDR-COUNTRY-CD
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrCountryCd.setValue(
        ws.cvcus01y.customerRecord.custAddrCountryCd);
    // COB(3887): MOVE CUST-ADDR-ZIP            TO ACUP-OLD-CUST-ADDR-ZIP
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustAddrZip.setValue(
        ws.cvcus01y.customerRecord.custAddrZip);
    // COB(3888): MOVE CUST-PHONE-NUM-1         TO ACUP-OLD-CUST-PHONE-NUM-1
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum1.setValue(
        ws.cvcus01y.customerRecord.custPhoneNum1);
    // COB(3889): MOVE CUST-PHONE-NUM-2         TO ACUP-OLD-CUST-PHONE-NUM-2
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPhoneNum2.setValue(
        ws.cvcus01y.customerRecord.custPhoneNum2);
    // COB(3891): MOVE CUST-GOVT-ISSUED-ID      TO ACUP-OLD-CUST-GOVT-ISSUED-ID
    //       *Government Id
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustGovtIssuedId.setValue(
        ws.cvcus01y.customerRecord.custGovtIssuedId);
    // COB(3893): MOVE CUST-EFT-ACCOUNT-ID      TO ACUP-OLD-CUST-EFT-ACCOUNT-ID
    //       *EFT Code
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustEftAccountId.setValue(
        ws.cvcus01y.customerRecord.custEftAccountId);
    // COB(3895): MOVE CUST-PRI-CARD-HOLDER-IND TO ACUP-OLD-CUST-PRI-HOLDER-IND
    //       *Primary Holder Indicator
    ws.wsThisProgcommarea.acupOldDetails.acupOldCustData.acupOldCustPriHolderInd.setValue(
        ws.cvcus01y.customerRecord.custPriCardHolderInd);
  }

  protected Flow _9600WriteProcessing() {
    // COB(3904): MOVE CC-ACCT-ID              TO WS-CARD-RID-ACCT-ID
    ws.wsMiscStorage.wsXrefRid.wsCardRidAcctId.setValue(
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
    // COB(3906): EXEC CICS READ
    // COB(3906):      FILE      (LIT-ACCTFILENAME)
    // COB(3906):      UPDATE
    // COB(3906):      RIDFLD    (WS-CARD-RID-ACCT-ID-X)
    // COB(3906):      KEYLENGTH (LENGTH OF WS-CARD-RID-ACCT-ID-X)
    // COB(3906):      INTO      (ACCOUNT-RECORD)
    // COB(3906):      LENGTH    (LENGTH OF ACCOUNT-RECORD)
    // COB(3906):      RESP      (WS-RESP-CD)
    // COB(3906):      RESP2     (WS-REAS-CD)
    // COB(3906): END-EXEC
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
    // COB(3919): IF WS-RESP-CD EQUAL TO DFHRESP(NORMAL)
    //       *****************************************************************
    //       *    Could we lock the account record ?
    //       *****************************************************************
    if (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.equals(Dfhresp.NORMAL)) {
      // COB(3920): CONTINUE
      // do nothing
      // COB(3921): ELSE
    } else {
      // COB(3922): SET INPUT-ERROR                    TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(3923): IF  WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(3924): SET COULD-NOT-LOCK-ACCT-FOR-UPDATE  TO TRUE
        ws.wsMiscStorage.setCouldNotLockAcctForUpdate(true);
        // COB(3925): END-IF
      }
      // COB(3926): GO TO 9600-WRITE-PROCESSING-EXIT
      return Flow._9600WriteProcessingExit;
      // COB(3927): END-IF
    }
    // COB(3931): MOVE CDEMO-CUST-ID                   TO WS-CARD-RID-CUST-ID
    //       *
    //       *    Read the customer file for update
    //       *
    ws.wsMiscStorage.wsXrefRid.wsCardRidCustId.setValue(
        ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustId);
    // COB(3933): EXEC CICS READ
    // COB(3933):      FILE      (LIT-CUSTFILENAME)
    // COB(3933):      UPDATE
    // COB(3933):      RIDFLD    (WS-CARD-RID-CUST-ID-X)
    // COB(3933):      KEYLENGTH (LENGTH OF WS-CARD-RID-CUST-ID-X)
    // COB(3933):      INTO      (CUSTOMER-RECORD)
    // COB(3933):      LENGTH    (LENGTH OF CUSTOMER-RECORD)
    // COB(3933):      RESP      (WS-RESP-CD)
    // COB(3933):      RESP2     (WS-REAS-CD)
    // COB(3933): END-EXEC
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
    // COB(3946): IF WS-RESP-CD EQUAL TO DFHRESP(NORMAL)
    //       *****************************************************************
    //       *    Could we lock the customer record ?
    //       *****************************************************************
    if (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.equals(Dfhresp.NORMAL)) {
      // COB(3947): CONTINUE
      // do nothing
      // COB(3948): ELSE
    } else {
      // COB(3949): SET INPUT-ERROR                    TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(3950): IF  WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(3951): SET COULD-NOT-LOCK-CUST-FOR-UPDATE  TO TRUE
        ws.wsMiscStorage.setCouldNotLockCustForUpdate(true);
        // COB(3952): END-IF
      }
      // COB(3953): GO TO 9600-WRITE-PROCESSING-EXIT
      return Flow._9600WriteProcessingExit;
      // COB(3954): END-IF
    }
    // COB(3959): PERFORM 9700-CHECK-CHANGE-IN-REC
    // COB(3959):    THRU 9700-CHECK-CHANGE-IN-REC-EXIT
    //       *
    //       *****************************************************************
    //       *    Did someone change the record while we were out ?
    //       *****************************************************************
    rcNext = Flow._9700CheckChangeInRec;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case _9700CheckChangeInRec:
          rcNext = _9700CheckChangeInRec();
          break;
        case _9600WriteProcessingExit:
          return Flow.Exit;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    // COB(3962): IF DATA-WAS-CHANGED-BEFORE-UPDATE
    if (ws.wsMiscStorage.dataWasChangedBeforeUpdate()) {
      // COB(3963): GO TO 9600-WRITE-PROCESSING-EXIT
      return Flow._9600WriteProcessingExit;
      // COB(3964): END-IF
    }
    // COB(3968): INITIALIZE ACCT-UPDATE-RECORD
    //       *****************************************************************
    //       * Prepare the update
    //       *****************************************************************
    ws.wsMiscStorage.acctUpdateRecord.initialize();
    // COB(3972): MOVE ACUP-NEW-ACCT-ID         TO ACCT-UPDATE-ID
    //       ******************************************************************
    //       *    Account Master data
    //       ******************************************************************
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateId.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewAcctId);
    // COB(3974): MOVE ACUP-NEW-ACTIVE-STATUS   TO ACCT-UPDATE-ACTIVE-STATUS
    //       * Active Status
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateActiveStatus.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewActiveStatus);
    // COB(3976): MOVE ACUP-NEW-CURR-BAL-N      TO ACCT-UPDATE-CURR-BAL
    //       * Current Balance
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateCurrBal.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrBalN);
    // COB(3978): MOVE ACUP-NEW-CREDIT-LIMIT-N  TO ACCT-UPDATE-CREDIT-LIMIT
    //       * Credit Limit
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateCreditLimit.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCreditLimitN);
    // COB(3980): MOVE ACUP-NEW-CASH-CREDIT-LIMIT-N
    // COB(3980):                            TO ACCT-UPDATE-CASH-CREDIT-LIMIT
    //       * Cash Limit
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateCashCreditLimit.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCashCreditLimitN);
    // COB(3983): MOVE ACUP-NEW-CURR-CYC-CREDIT-N
    // COB(3983):                                TO ACCT-UPDATE-CURR-CYC-CREDIT
    //       * Current Cycle Credit
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateCurrCycCredit.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrCycCreditN);
    // COB(3986): MOVE ACUP-NEW-CURR-CYC-DEBIT-N TO ACCT-UPDATE-CURR-CYC-DEBIT
    //       * Current Cycle Debit
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateCurrCycDebit.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewCurrCycDebitN);
    // COB(3988): STRING ACUP-NEW-OPEN-YEAR
    // COB(3988):        '-'
    // COB(3988):        ACUP-NEW-OPEN-MON
    // COB(3988):        '-'
    // COB(3988):        ACUP-NEW-OPEN-DAY
    // COB(3988): DELIMITED BY SIZE
    // COB(3988):                             INTO ACCT-UPDATE-OPEN-DATE
    //       * Open date
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateOpenDate.concat(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenYear,
        "-",
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenMon,
        "-",
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewOpenDateParts.acupNewOpenDay);
    // COB(3996): STRING ACUP-NEW-EXP-YEAR
    // COB(3996):        '-'
    // COB(3996):        ACUP-NEW-EXP-MON
    // COB(3996):        '-'
    // COB(3996):        ACUP-NEW-EXP-DAY
    // COB(3996): DELIMITED BY SIZE
    // COB(3996):                             INTO ACCT-UPDATE-EXPIRAION-DATE
    //       * Expiry date
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
    // COB(4005): MOVE ACCT-REISSUE-DATE        TO ACCT-UPDATE-REISSUE-DATE
    //       *
    //       * Reissue date
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateReissueDate.setValue(
        ws.cvact01y.accountRecord.acctReissueDate);
    // COB(4006): STRING ACUP-NEW-REISSUE-YEAR
    // COB(4006):        '-'
    // COB(4006):        ACUP-NEW-REISSUE-MON
    // COB(4006):        '-'
    // COB(4006):        ACUP-NEW-REISSUE-DAY
    // COB(4006): DELIMITED BY SIZE
    // COB(4006):                             INTO ACCT-UPDATE-REISSUE-DATE
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
    // COB(4014): MOVE ACUP-NEW-GROUP-ID        TO ACCT-UPDATE-GROUP-ID
    //       * Account Group
    ws.wsMiscStorage.acctUpdateRecord.acctUpdateGroupId.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewAcctData.acupNewGroupId);
    // COB(4019): INITIALIZE CUST-UPDATE-RECORD
    //       *
    //       ******************************************************************
    //       *    Customer data
    //       ******************************************************************
    ws.wsMiscStorage.custUpdateRecord.initialize();
    // COB(4021): MOVE  ACUP-NEW-CUST-ID        TO CUST-UPDATE-ID
    ws.wsMiscStorage.custUpdateRecord.custUpdateId.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustId);
    // COB(4022): MOVE  ACUP-NEW-CUST-FIRST-NAME
    // COB(4022):                         TO CUST-UPDATE-FIRST-NAME
    ws.wsMiscStorage.custUpdateRecord.custUpdateFirstName.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFirstName);
    // COB(4024): MOVE  ACUP-NEW-CUST-MIDDLE-NAME
    // COB(4024):                         TO CUST-UPDATE-MIDDLE-NAME
    ws.wsMiscStorage.custUpdateRecord.custUpdateMiddleName.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustMiddleName);
    // COB(4026): MOVE  ACUP-NEW-CUST-LAST-NAME TO CUST-UPDATE-LAST-NAME
    ws.wsMiscStorage.custUpdateRecord.custUpdateLastName.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustLastName);
    // COB(4027): MOVE  ACUP-NEW-CUST-ADDR-LINE-1
    // COB(4027):                         TO CUST-UPDATE-ADDR-LINE-1
    ws.wsMiscStorage.custUpdateRecord.custUpdateAddrLine1.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine1);
    // COB(4029): MOVE  ACUP-NEW-CUST-ADDR-LINE-2
    // COB(4029):                         TO CUST-UPDATE-ADDR-LINE-2
    ws.wsMiscStorage.custUpdateRecord.custUpdateAddrLine2.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine2);
    // COB(4031): MOVE  ACUP-NEW-CUST-ADDR-LINE-3
    // COB(4031):                         TO CUST-UPDATE-ADDR-LINE-3
    ws.wsMiscStorage.custUpdateRecord.custUpdateAddrLine3.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrLine3);
    // COB(4033): MOVE  ACUP-NEW-CUST-ADDR-STATE-CD
    // COB(4033):                         TO CUST-UPDATE-ADDR-STATE-CD
    ws.wsMiscStorage.custUpdateRecord.custUpdateAddrStateCd.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrStateCd);
    // COB(4035): MOVE  ACUP-NEW-CUST-ADDR-COUNTRY-CD
    // COB(4035):                         TO CUST-UPDATE-ADDR-COUNTRY-CD
    ws.wsMiscStorage.custUpdateRecord.custUpdateAddrCountryCd.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrCountryCd);
    // COB(4037): MOVE  ACUP-NEW-CUST-ADDR-ZIP  TO CUST-UPDATE-ADDR-ZIP
    ws.wsMiscStorage.custUpdateRecord.custUpdateAddrZip.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustAddrZip);
    // COB(4039): STRING '(',
    // COB(4039):        ACUP-NEW-CUST-PHONE-NUM-1A,
    // COB(4039):        ')',
    // COB(4039):        ACUP-NEW-CUST-PHONE-NUM-1B,
    // COB(4039):        '-',
    // COB(4039):        ACUP-NEW-CUST-PHONE-NUM-1C
    // COB(4039): DELIMITED BY SIZE    INTO CUST-UPDATE-PHONE-NUM-1
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
    // COB(4047): STRING '(',
    // COB(4047):        ACUP-NEW-CUST-PHONE-NUM-2A,
    // COB(4047):        ')',
    // COB(4047):        ACUP-NEW-CUST-PHONE-NUM-2B,
    // COB(4047):        '-',
    // COB(4047):        ACUP-NEW-CUST-PHONE-NUM-2C
    // COB(4047): DELIMITED BY SIZE    INTO CUST-UPDATE-PHONE-NUM-2
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
    // COB(4056): MOVE  ACUP-NEW-CUST-SSN       TO CUST-UPDATE-SSN
    //       *
    //       *
    ws.wsMiscStorage.custUpdateRecord.custUpdateSsn.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustSsn);
    // COB(4057): MOVE  ACUP-NEW-CUST-GOVT-ISSUED-ID
    // COB(4057):                         TO CUST-UPDATE-GOVT-ISSUED-ID
    ws.wsMiscStorage.custUpdateRecord.custUpdateGovtIssuedId.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustGovtIssuedId);
    // COB(4059): STRING ACUP-NEW-CUST-DOB-YEAR
    // COB(4059):        '-'
    // COB(4059):        ACUP-NEW-CUST-DOB-MON
    // COB(4059):        '-'
    // COB(4059):        ACUP-NEW-CUST-DOB-DAY
    // COB(4059): DELIMITED BY SIZE           INTO CUST-UPDATE-DOB-YYYY-MM-DD
    ws.wsMiscStorage.custUpdateRecord.custUpdateDobYyyyMmDd.concat(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobYear,
        "-",
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobMon,
        "-",
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustDobParts.acupNewCustDobDay);
    // COB(4066): MOVE ACUP-NEW-CUST-EFT-ACCOUNT-ID
    // COB(4066):                               TO CUST-UPDATE-EFT-ACCOUNT-ID
    ws.wsMiscStorage.custUpdateRecord.custUpdateEftAccountId.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustEftAccountId);
    // COB(4068): MOVE ACUP-NEW-CUST-PRI-HOLDER-IND
    // COB(4068):                               TO CUST-UPDATE-PRI-CARD-IND
    ws.wsMiscStorage.custUpdateRecord.custUpdatePriCardInd.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustPriHolderInd);
    // COB(4070): MOVE ACUP-NEW-CUST-FICO-SCORE TO
    // COB(4070):                         CUST-UPDATE-FICO-CREDIT-SCORE
    ws.wsMiscStorage.custUpdateRecord.custUpdateFicoCreditScore.setValue(
        ws.wsThisProgcommarea.acupNewDetails.acupNewCustData.acupNewCustFicoScore);
    // COB(4077): EXEC CICS
    // COB(4077):      REWRITE FILE(LIT-ACCTFILENAME)
    // COB(4077):              FROM(ACCT-UPDATE-RECORD)
    // COB(4077):              LENGTH(LENGTH OF ACCT-UPDATE-RECORD)
    // COB(4077):              RESP      (WS-RESP-CD)
    // COB(4077):              RESP2     (WS-REAS-CD)
    // COB(4077): END-EXEC.
    //       *****************************************************************
    //       * Update account *
    //       *****************************************************************
    //       *
    //       *
    supernaut
        .rewrite(ws.wsLiterals.litAcctfilename.getValue()) //
        .length(ws.wsMiscStorage.acctUpdateRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .from(ws.wsMiscStorage.acctUpdateRecord) //
        .exec();
    // COB(4088): IF WS-RESP-CD EQUAL TO DFHRESP(NORMAL)
    //       *
    //       *****************************************************************
    //       * Did account update succeed ?  *
    //       *****************************************************************
    if (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.equals(Dfhresp.NORMAL)) {
      // COB(4089): CONTINUE
      // do nothing
      // COB(4090): ELSE
    } else {
      // COB(4091): SET LOCKED-BUT-UPDATE-FAILED    TO TRUE
      ws.wsMiscStorage.setLockedButUpdateFailed(true);
      // COB(4092): GO TO 9600-WRITE-PROCESSING-EXIT
      return Flow._9600WriteProcessingExit;
      // COB(4093): END-IF
    }
    // COB(4097): EXEC CICS
    // COB(4097):              REWRITE FILE(LIT-CUSTFILENAME)
    // COB(4097):              FROM(CUST-UPDATE-RECORD)
    // COB(4097):              LENGTH(LENGTH OF CUST-UPDATE-RECORD)
    // COB(4097):              RESP      (WS-RESP-CD)
    // COB(4097):              RESP2     (WS-REAS-CD)
    // COB(4097): END-EXEC.
    //       *****************************************************************
    //       * Update customer *
    //       *****************************************************************
    supernaut
        .rewrite(ws.wsLiterals.litCustfilename.getValue()) //
        .length(ws.wsMiscStorage.custUpdateRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .from(ws.wsMiscStorage.custUpdateRecord) //
        .exec();
    // COB(4107): IF WS-RESP-CD EQUAL TO DFHRESP(NORMAL)
    //       *****************************************************************
    //       * Did customer update succeed ? *
    //       *****************************************************************
    if (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.equals(Dfhresp.NORMAL)) {
      // COB(4108): CONTINUE
      // do nothing
      // COB(4109): ELSE
    } else {
      // COB(4110): SET LOCKED-BUT-UPDATE-FAILED    TO TRUE
      ws.wsMiscStorage.setLockedButUpdateFailed(true);
      // COB(4111): EXEC CICS
      // COB(4111):    SYNCPOINT ROLLBACK
      // COB(4111): END-EXEC
      try {
        supernaut
            .syncpoint() //
            .rollback() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(4114): GO TO 9600-WRITE-PROCESSING-EXIT
      return Flow._9600WriteProcessingExit;
      // COB(4115): END-IF
    }
    return Flow.Exit;
  }

  protected void _9600WriteProcessingExit() {
    // COB(4118): EXIT
    return;
  }

  /***/
  protected Flow _9700CheckChangeInRec() {
    // COB(4127): IF  ACCT-ACTIVE-STATUS      EQUAL ACUP-OLD-ACTIVE-STATUS
    // COB(4127):       * Current Balance
    // COB(4127): AND ACCT-CURR-BAL           EQUAL ACUP-OLD-CURR-BAL-N
    // COB(4127):       * Credit Limit
    // COB(4127): AND ACCT-CREDIT-LIMIT       EQUAL ACUP-OLD-CREDIT-LIMIT-N
    // COB(4127):       * Cash Limit
    // COB(4127): AND ACCT-CASH-CREDIT-LIMIT EQUAL ACUP-OLD-CASH-CREDIT-LIMIT-N
    // COB(4127):       * Current Cycle Credit
    // COB(4127): AND ACCT-CURR-CYC-CREDIT    EQUAL ACUP-OLD-CURR-CYC-CREDIT-N
    // COB(4127):       * Current Cycle Debit
    // COB(4127): AND ACCT-CURR-CYC-DEBIT     EQUAL ACUP-OLD-CURR-CYC-DEBIT-N
    // COB(4127):       * Open date
    // COB(4127): AND ACCT-OPEN-DATE(1:4)     EQUAL ACUP-OLD-OPEN-YEAR
    // COB(4127): AND ACCT-OPEN-DATE(6:2)     EQUAL ACUP-OLD-OPEN-MON
    // COB(4127): AND ACCT-OPEN-DATE(9:2)     EQUAL ACUP-OLD-OPEN-DAY
    // COB(4127):       * Expiry date
    // COB(4127): AND ACCT-EXPIRAION-DATE(1:4)EQUAL ACUP-OLD-EXP-YEAR
    // COB(4127): AND ACCT-EXPIRAION-DATE(6:2)EQUAL ACUP-OLD-EXP-MON
    // COB(4127): AND ACCT-EXPIRAION-DATE(9:2)EQUAL ACUP-OLD-EXP-DAY
    // COB(4127):       * Reissue date
    // COB(4127): AND ACCT-REISSUE-DATE(1:4)  EQUAL ACUP-OLD-REISSUE-YEAR
    // COB(4127): AND ACCT-REISSUE-DATE(6:2)  EQUAL ACUP-OLD-REISSUE-MON
    // COB(4127): AND ACCT-REISSUE-DATE(9:2)  EQUAL ACUP-OLD-REISSUE-DAY
    // COB(4127):       * Account Group
    // COB(4127): AND FUNCTION LOWER-CASE (ACCT-GROUP-ID)           EQUAL
    // COB(4127):     FUNCTION LOWER-CASE (ACUP-OLD-GROUP-ID)
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
      // COB(4153): CONTINUE
      // do nothing
      // COB(4154): ELSE
    } else {
      // COB(4155): SET DATA-WAS-CHANGED-BEFORE-UPDATE TO TRUE
      ws.wsMiscStorage.setDataWasChangedBeforeUpdate(true);
      // COB(4156): GO TO 9600-WRITE-PROCESSING-EXIT
      return Flow._9600WriteProcessingExit;
      // COB(4157): END-IF
    }
    // COB(4164): IF  FUNCTION UPPER-CASE (CUST-FIRST-NAME          ) EQUAL
    // COB(4164):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-FIRST-NAME )
    // COB(4164): AND FUNCTION UPPER-CASE (CUST-MIDDLE-NAME         ) EQUAL
    // COB(4164):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-MIDDLE-NAME)
    // COB(4164): AND FUNCTION UPPER-CASE (CUST-LAST-NAME           ) EQUAL
    // COB(4164):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-LAST-NAME  )
    // COB(4164): AND FUNCTION UPPER-CASE (CUST-ADDR-LINE-1         ) EQUAL
    // COB(4164):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-ADDR-LINE-1)
    // COB(4164): AND FUNCTION UPPER-CASE (CUST-ADDR-LINE-2         ) EQUAL
    // COB(4164):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-ADDR-LINE-2)
    // COB(4164): AND FUNCTION UPPER-CASE (CUST-ADDR-LINE-3         ) EQUAL
    // COB(4164):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-ADDR-LINE-3)
    // COB(4164): AND FUNCTION UPPER-CASE (CUST-ADDR-STATE-CD       ) EQUAL
    // COB(4164):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-ADDR-STATE-CD)
    // COB(4164): AND FUNCTION UPPER-CASE (CUST-ADDR-COUNTRY-CD     ) EQUAL
    // COB(4164):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-ADDR-COUNTRY-CD )
    // COB(4164): AND CUST-ADDR-ZIP           EQUAL ACUP-OLD-CUST-ADDR-ZIP
    // COB(4164): AND CUST-PHONE-NUM-1        EQUAL ACUP-OLD-CUST-PHONE-NUM-1
    // COB(4164): AND CUST-PHONE-NUM-2        EQUAL ACUP-OLD-CUST-PHONE-NUM-2
    // COB(4164): AND CUST-SSN                EQUAL ACUP-OLD-CUST-SSN
    // COB(4164): AND FUNCTION UPPER-CASE (CUST-GOVT-ISSUED-ID      ) EQUAL
    // COB(4164):     FUNCTION UPPER-CASE (ACUP-OLD-CUST-GOVT-ISSUED-ID )
    // COB(4164): AND CUST-DOB-YYYY-MM-DD (1:4)                       EQUAL
    // COB(4164):     ACUP-OLD-CUST-DOB-YYYY-MM-DD (1:4)
    // COB(4164): AND CUST-DOB-YYYY-MM-DD (6:2)                       EQUAL
    // COB(4164):     ACUP-OLD-CUST-DOB-YYYY-MM-DD (5:2)
    // COB(4164): AND CUST-DOB-YYYY-MM-DD (9:2)                       EQUAL
    // COB(4164):     ACUP-OLD-CUST-DOB-YYYY-MM-DD (7:2)
    // COB(4164):       *
    // COB(4164): AND CUST-EFT-ACCOUNT-ID     EQUAL
    // COB(4164):                                  ACUP-OLD-CUST-EFT-ACCOUNT-ID
    // COB(4164): AND CUST-PRI-CARD-HOLDER-IND
    // COB(4164):                             EQUAL
    // COB(4164):                                  ACUP-OLD-CUST-PRI-HOLDER-IND
    // COB(4164): AND CUST-FICO-CREDIT-SCORE  EQUAL ACUP-OLD-CUST-FICO-SCORE
    //       *
    //       ******************************************************************
    //       *    Customer  data - Split into 2 IFs for easier reading
    //       *    And maybe put logic to update only 1 file if only date
    //       *    pertaining to one of them is updated
    //       ******************************************************************
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
      // COB(4199): CONTINUE
      // do nothing
      // COB(4200): ELSE
    } else {
      // COB(4201): SET DATA-WAS-CHANGED-BEFORE-UPDATE TO TRUE
      ws.wsMiscStorage.setDataWasChangedBeforeUpdate(true);
      // COB(4202): GO TO 9600-WRITE-PROCESSING-EXIT
      return Flow._9600WriteProcessingExit;
      // COB(4203): END-IF
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
    // COB(4217): IF ABEND-MSG EQUAL LOW-VALUES
    if (ws.csmsg02y.abendData.abendMsg.isLowValues()) {
      // COB(4218): MOVE 'UNEXPECTED ABEND OCCURRED.' TO ABEND-MSG
      ws.csmsg02y.abendData.abendMsg.setString("UNEXPECTED ABEND OCCURRED.");
      // COB(4219): END-IF
    }
    // COB(4221): MOVE LIT-THISPGM       TO ABEND-CULPRIT
    ws.csmsg02y.abendData.abendCulprit.setValue(ws.wsLiterals.litThispgm);
    // COB(4223): EXEC CICS SEND
    // COB(4223):                  FROM (ABEND-DATA)
    // COB(4223):                  LENGTH(LENGTH OF ABEND-DATA)
    // COB(4223):                  NOHANDLE
    // COB(4223):                  ERASE
    // COB(4223): END-EXEC
    supernaut
        .send() //
        .length(ws.csmsg02y.abendData.length()) //
        .nohandle() //
        .from(ws.csmsg02y.abendData) //
        .erase() //
        .exec();
    // COB(4230): EXEC CICS HANDLE ABEND
    // COB(4230):      CANCEL
    // COB(4230): END-EXEC
    supernaut
        .handleAbend() //
        .cancel() //
        .exec();
    // COB(4234): EXEC CICS ABEND
    // COB(4234):      ABCODE('9999')
    // COB(4234): END-EXEC
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
    //       *    Not supplied
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
      //       *       Intentional violation of structured programming norms
      return Flow.editYearCcyyExit;
      // COB(43): ELSE
    } else {
      // COB(44): CONTINUE
      // do nothing
      // COB(45): END-IF
    }
    // COB(48): IF WS-EDIT-DATE-CCYY            IS NOT NUMERIC
    //       *    Not numeric
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
    //       ******************************************************************
    //       *    Century not reasonable
    //       ******************************************************************
    //       *  Not having learnt our lesson from history and Y2K
    //       *  And being unable to imagine COBOL in the 2100s
    //       *  We code only 19 and 20 as valid century values
    //       ******************************************************************
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
    //       *    Month not reasonable
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
    //            .
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
    //       ******************************************************************
    //       *    Checking for any other combinations
    //       ******************************************************************
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
    //       ******************************************************************
    //       *                  Use LE Services to verify the supplied date
    //       *    the edits above ......
    //       *    In case some one managed to enter a bad date that passsed all
    //       ******************************************************************
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
    //            .
    //       *    If we got here all edits were cleared
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
      //       *    IF FUNCTION FIND-DURATION(FUNCTION CURRENT-DATE
      //       *                             ,WS-EDIT-DATE-CCYYMMDD)
      //       *                             ,DAYS) > 0
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
