package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cocrdupc.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COCRDUPC extends AbstractOnlineProgram {

  protected enum Flow {
    Exit,
    // 0000-MAIN
    _0000Main,
    // 9200-WRITE-PROCESSING
    _9200WriteProcessing,
    // 9200-WRITE-PROCESSING-EXIT
    _9200WriteProcessingExit,
    // 9300-CHECK-CHANGE-IN-REC
    _9300CheckChangeInRec,
    // COMMON-RETURN
    commonReturn
  }

  private Flow rcNext;

  @ProgramStorage final CocrdupcWorking ws = new CocrdupcWorking();

  // Linkage
  public static class CocrdupcLinkage extends NGroup {
    // COB:        01  DFHCOMMAREA.
    public Dfhcommarea dfhcommarea = new Dfhcommarea();

    public static class Dfhcommarea extends NGroup {

      // COB:          05  FILLER                                PIC X(1)
      // COB:              OCCURS 1 TO 32767 TIMES DEPENDING ON EIBCALEN.
      public NChar[] filler363 = AbstractNField.occurs(32767, new NChar(1));

      public NChar filler363AtIndex(int index) {
        return getOccursInstance(filler363, index);
      }
    }
  }

  final CocrdupcLinkage params = new CocrdupcLinkage();

  public COCRDUPC(Context supernaut) {
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
    // COB(370): EXEC CICS HANDLE ABEND
    // COB(370):           LABEL(ABEND-ROUTINE)
    // COB(370): END-EXEC
    supernaut
        .handleAbend() //
        .label(HandleLabel.abendRoutine.getValue()) //
        .exec();
    // COB(374): INITIALIZE CC-WORK-AREA
    // COB(374):            WS-MISC-STORAGE
    // COB(374):            WS-COMMAREA
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.initialize();
    ws.wsMiscStorage.initialize();
    ws.wsCommarea.initialize();
    // COB(380): MOVE LIT-THISTRANID       TO WS-TRANID
    // ****************************************************************
    //  Store our context
    // ****************************************************************
    ws.wsMiscStorage.wsCicsProcessngVars.wsTranid.setValue(ws.wsLiterals.litThistranid);
    // COB(384): SET WS-RETURN-MSG-OFF  TO TRUE
    // ****************************************************************
    //  Ensure error message is cleared                               *
    // ****************************************************************
    ws.wsMiscStorage.setWsReturnMsgOff(true);
    // COB(388): IF EIBCALEN IS EQUAL TO 0
    // COB(388):     OR (CDEMO-FROM-PROGRAM = LIT-MENUPGM
    // COB(388):     AND NOT CDEMO-PGM-REENTER)
    // ****************************************************************
    //  Store passed data if  any                *
    // ****************************************************************
    if (dfheiblk.getEibcalen() == 0
        || (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsLiterals.litMenupgm)
            && !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter())) {
      // COB(391): INITIALIZE CARDDEMO-COMMAREA
      // COB(391):            WS-THIS-PROGCOMMAREA
      ws.cocom01y.carddemoCommarea.initialize();
      ws.wsThisProgcommarea.initialize();
      // COB(393): SET CDEMO-PGM-ENTER TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(394): SET CCUP-DETAILS-NOT-FETCHED TO TRUE
      ws.wsThisProgcommarea.cardUpdateScreenData.setCcupDetailsNotFetched(true);
      // COB(395): ELSE
    } else {
      // COB(396): MOVE DFHCOMMAREA (1:LENGTH OF CARDDEMO-COMMAREA)  TO
      // COB(396):                   CARDDEMO-COMMAREA
      ws.cocom01y.carddemoCommarea.setValue(
          params.dfhcommarea, 0, ws.cocom01y.carddemoCommarea.length());
      // COB(398): MOVE DFHCOMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
      // COB(398):                  LENGTH OF WS-THIS-PROGCOMMAREA ) TO
      // COB(398):                   WS-THIS-PROGCOMMAREA
      ws.wsThisProgcommarea.setValue(
          params.dfhcommarea,
          ws.cocom01y.carddemoCommarea.length(),
          0,
          ws.wsThisProgcommarea.length());
      // COB(401): END-IF
    }
    // COB(406): PERFORM YYYY-STORE-PFKEY
    // COB(406):    THRU YYYY-STORE-PFKEY-EXIT
    // ****************************************************************
    //  Remap PFkeys as needed.
    //  Store the Mapped PF Key
    // ****************************************************************
    yyyyStorePfkey();
    // COB(413): SET PFK-INVALID TO TRUE
    // ****************************************************************
    //  Check the AID to see if its valid at this point               *
    //  F3 - Exit
    //  Enter show screen again
    // ****************************************************************
    ws.wsMiscStorage.setPfkInvalid(true);
    // COB(414): IF CCARD-AID-ENTER OR
    // COB(414):    CCARD-AID-PFK03 OR
    // COB(414):    (CCARD-AID-PFK05 AND CCUP-CHANGES-OK-NOT-CONFIRMED)
    // COB(414):                    OR
    // COB(414):    (CCARD-AID-PFK12 AND NOT CCUP-DETAILS-NOT-FETCHED)
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidEnter()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()
        || (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk05()
            && ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesOkNotConfirmed())
        || (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk12()
            && !ws.wsThisProgcommarea.cardUpdateScreenData.ccupDetailsNotFetched())) {
      // COB(419): SET PFK-VALID TO TRUE
      ws.wsMiscStorage.setPfkValid(true);
      // COB(420): END-IF
    }
    // COB(422): IF PFK-INVALID
    if (ws.wsMiscStorage.pfkInvalid()) {
      // COB(423): SET CCARD-AID-ENTER TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidEnter(true);
      // COB(424): END-IF
    }
    // COB(429): EVALUATE TRUE
    // ****************************************************************
    //  Decide what to do based on inputs received
    // ****************************************************************
    // COB(435): WHEN CCARD-AID-PFK03
    // *****************************************************************
    //        USER PRESSES PF03 TO EXIT
    //   OR   USER IS DONE WITH UPDATE
    //             XCTL TO CALLING PROGRAM OR MAIN MENU
    // *****************************************************************
    if ((ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03())
        // COB(436): WHEN (CCUP-CHANGES-OKAYED-AND-DONE
        // COB(436):  AND  CDEMO-LAST-MAPSET   EQUAL LIT-CCLISTMAPSET)
        || (ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesOkayedAndDone()
            && ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.equals(
                ws.wsLiterals.litCclistmapset))
        // COB(438): WHEN (CCUP-CHANGES-FAILED
        // COB(438):  AND  CDEMO-LAST-MAPSET   EQUAL LIT-CCLISTMAPSET)
        || (ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesFailed()
            && ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.equals(
                ws.wsLiterals.litCclistmapset))) {
      // COB(440): SET CCARD-AID-PFK03     TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk03(true);
      // COB(442): IF CDEMO-FROM-TRANID    EQUAL LOW-VALUES
      // COB(442): OR CDEMO-FROM-TRANID    EQUAL SPACES
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isSpaces()) {
        // COB(444): MOVE LIT-MENUTRANID  TO CDEMO-TO-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToTranid.setValue(
            ws.wsLiterals.litMenutranid);
        // COB(445): ELSE
      } else {
        // COB(446): MOVE CDEMO-FROM-TRANID  TO CDEMO-TO-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToTranid.setValue(
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid);
        // COB(447): END-IF
      }
      // COB(449): IF CDEMO-FROM-PROGRAM   EQUAL LOW-VALUES
      // COB(449): OR CDEMO-FROM-PROGRAM   EQUAL SPACES
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isSpaces()) {
        // COB(451): MOVE LIT-MENUPGM     TO CDEMO-TO-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
            ws.wsLiterals.litMenupgm);
        // COB(452): ELSE
      } else {
        // COB(453): MOVE CDEMO-FROM-PROGRAM TO CDEMO-TO-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram);
        // COB(454): END-IF
      }
      // COB(456): MOVE LIT-THISTRANID     TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
          ws.wsLiterals.litThistranid);
      // COB(457): MOVE LIT-THISPGM        TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
          ws.wsLiterals.litThispgm);
      // COB(459): IF CDEMO-LAST-MAPSET    EQUAL LIT-CCLISTMAPSET
      if (ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.equals(
          ws.wsLiterals.litCclistmapset)) {
        // COB(460): MOVE ZEROS          TO CDEMO-ACCT-ID
        // COB(460):                        CDEMO-CARD-NUM
        ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
        ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.zeros();
        // COB(462): END-IF
      }
      // COB(464): SET  CDEMO-USRTYP-USER  TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypUser(true);
      // COB(465): SET  CDEMO-PGM-ENTER    TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(466): MOVE LIT-THISMAPSET     TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(
          ws.wsLiterals.litThismapset);
      // COB(467): MOVE LIT-THISMAP        TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsLiterals.litThismap);
      // COB(469): EXEC CICS
      // COB(469):      SYNCPOINT
      // COB(469): END-EXEC
      try {
        supernaut
            .syncpoint() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(473): EXEC CICS XCTL
      // COB(473):      PROGRAM (CDEMO-TO-PROGRAM)
      // COB(473):      COMMAREA(CARDDEMO-COMMAREA)
      // COB(473): END-EXEC
      try {
        supernaut
            .xctl(ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
            .commarea(ws.cocom01y.carddemoCommarea) //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(482): WHEN CDEMO-PGM-ENTER
      // COB(482):  AND CDEMO-FROM-PROGRAM  EQUAL LIT-CCLISTPGM
      // *****************************************************************
      //        USER CAME FROM CREDIT CARD LIST SCREEN
      //             SO WE ALREADY HAVE THE FILTER KEYS
      //             FETCH THE ASSSOCIATED CARD DETAILS FOR UPDATE
      // *****************************************************************
    } else if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()
            && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsLiterals.litCclistpgm)
        // COB(484): WHEN CCARD-AID-PFK12
        // COB(484):  AND CDEMO-FROM-PROGRAM  EQUAL LIT-CCLISTPGM
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk12()
            && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsLiterals.litCclistpgm)) {
      // COB(486): SET CDEMO-PGM-REENTER    TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
      // COB(487): SET INPUT-OK             TO TRUE
      ws.wsMiscStorage.setInputOk(true);
      // COB(488): SET FLG-ACCTFILTER-ISVALID  TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterIsvalid(true);
      // COB(489): SET FLG-CARDFILTER-ISVALID  TO TRUE
      ws.wsMiscStorage.setFlgCardfilterIsvalid(true);
      // COB(490): MOVE CDEMO-ACCT-ID       TO CC-ACCT-ID-N
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctIdN.setValue(
          ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId);
      // COB(491): MOVE CDEMO-CARD-NUM      TO CC-CARD-NUM-N
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNumN.setValue(
          ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum);
      // COB(492): PERFORM 9000-READ-DATA
      // COB(492):    THRU 9000-READ-DATA-EXIT
      _9000ReadData();
      // COB(494): SET CCUP-SHOW-DETAILS TO TRUE
      ws.wsThisProgcommarea.cardUpdateScreenData.setCcupShowDetails(true);
      // COB(495): PERFORM 3000-SEND-MAP
      // COB(495):    THRU 3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(497): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(502): WHEN CCUP-DETAILS-NOT-FETCHED
      // COB(502):  AND CDEMO-PGM-ENTER
      // *****************************************************************
      //        FRESH ENTRY INTO PROGRAM
      //             ASK THE USER FOR THE KEYS TO FETCH CARD TO BE UPDATED
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupDetailsNotFetched()
            && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()
        // COB(504): WHEN CDEMO-FROM-PROGRAM   EQUAL LIT-MENUPGM
        // COB(504):  AND NOT CDEMO-PGM-REENTER
        || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsLiterals.litMenupgm)
            && !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(506): INITIALIZE WS-THIS-PROGCOMMAREA
      ws.wsThisProgcommarea.initialize();
      // COB(507): PERFORM 3000-SEND-MAP THRU
      // COB(507):         3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(509): SET CDEMO-PGM-REENTER        TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
      // COB(510): SET CCUP-DETAILS-NOT-FETCHED TO TRUE
      ws.wsThisProgcommarea.cardUpdateScreenData.setCcupDetailsNotFetched(true);
      // COB(511): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(517): WHEN CCUP-CHANGES-OKAYED-AND-DONE
      // *****************************************************************
      //        CARD DATA CHANGES REVIEWED, OKAYED AND DONE SUCESSFULLY
      //             RESET THE SEARCH KEYS
      //             ASK THE USER FOR FRESH SEARCH CRITERIA
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesOkayedAndDone()
        // COB(518): WHEN CCUP-CHANGES-FAILED
        || ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesFailed()) {
      // COB(519): INITIALIZE WS-THIS-PROGCOMMAREA
      // COB(519):            WS-MISC-STORAGE
      // COB(519):            CDEMO-ACCT-ID
      // COB(519):            CDEMO-CARD-NUM
      ws.wsThisProgcommarea.initialize();
      ws.wsMiscStorage.initialize();
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.initialize();
      ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.initialize();
      // COB(523): SET CDEMO-PGM-ENTER            TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(524): PERFORM 3000-SEND-MAP THRU
      // COB(524):         3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(526): SET CDEMO-PGM-REENTER          TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
      // COB(527): SET CCUP-DETAILS-NOT-FETCHED   TO TRUE
      ws.wsThisProgcommarea.cardUpdateScreenData.setCcupDetailsNotFetched(true);
      // COB(528): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(535): WHEN OTHER
      // *****************************************************************
      //       CARD DATA HAS BEEN PRESENTED TO USER
      //             CHECK THE USER INPUTS
      //             DECIDE WHAT TO DO
      //             PRESENT NEXT STEPS TO USER
      // *****************************************************************
    } else {
      // COB(536): PERFORM 1000-PROCESS-INPUTS
      // COB(536):    THRU 1000-PROCESS-INPUTS-EXIT
      _1000ProcessInputs();
      // COB(538): PERFORM 2000-DECIDE-ACTION
      // COB(538):    THRU 2000-DECIDE-ACTION-EXIT
      _2000DecideAction();
      // COB(540): PERFORM 3000-SEND-MAP
      // COB(540):    THRU 3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(542): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(543): END-EVALUATE
    }
    return Flow.Exit;
  }

  protected void commonReturn() {
    // COB(547): MOVE WS-RETURN-MSG     TO CCARD-ERROR-MSG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
    // COB(549): MOVE  CARDDEMO-COMMAREA    TO WS-COMMAREA
    ws.wsCommarea.setValue(ws.cocom01y.carddemoCommarea);
    // COB(550): MOVE  WS-THIS-PROGCOMMAREA TO
    // COB(550):        WS-COMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
    // COB(550):                     LENGTH OF WS-THIS-PROGCOMMAREA )
    ws.wsCommarea.setValue(
        ws.wsThisProgcommarea,
        0,
        ws.cocom01y.carddemoCommarea.length(),
        ws.wsThisProgcommarea.length());
    // COB(554): EXEC CICS RETURN
    // COB(554):      TRANSID (LIT-THISTRANID)
    // COB(554):      COMMAREA (WS-COMMAREA)
    // COB(554):      LENGTH(LENGTH OF WS-COMMAREA)
    // COB(554): END-EXEC
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

  protected void _1000ProcessInputs() {
    // COB(565): PERFORM 1100-RECEIVE-MAP
    // COB(565):    THRU 1100-RECEIVE-MAP-EXIT
    _1100ReceiveMap();
    // COB(567): PERFORM 1200-EDIT-MAP-INPUTS
    // COB(567):    THRU 1200-EDIT-MAP-INPUTS-EXIT
    _1200EditMapInputs();
    // COB(569): MOVE WS-RETURN-MSG  TO CCARD-ERROR-MSG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
    // COB(570): MOVE LIT-THISPGM    TO CCARD-NEXT-PROG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.setValue(ws.wsLiterals.litThispgm);
    // COB(571): MOVE LIT-THISMAPSET TO CCARD-NEXT-MAPSET
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsLiterals.litThismapset);
    // COB(572): MOVE LIT-THISMAP    TO CCARD-NEXT-MAP
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsLiterals.litThismap);
  }

  protected void _1100ReceiveMap() {
    // COB(579): EXEC CICS RECEIVE MAP(LIT-THISMAP)
    // COB(579):           MAPSET(LIT-THISMAPSET)
    // COB(579):           INTO(CCRDUPAI)
    // COB(579):           RESP(WS-RESP-CD)
    // COB(579):           RESP2(WS-REAS-CD)
    // COB(579): END-EXEC
    supernaut
        .receiveMap(ws.wsLiterals.litThismap.getValue()) //
        .mapset(ws.wsLiterals.litThismapset.getValue()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cocrdup.ccrdupai) //
        .exec();
    // COB(586): INITIALIZE CCUP-NEW-DETAILS
    ws.wsThisProgcommarea.ccupNewDetails.initialize();
    // COB(589): IF  ACCTSIDI OF CCRDUPAI = '*'
    // COB(589): OR  ACCTSIDI OF CCRDUPAI = SPACES
    //     REPLACE * WITH LOW-VALUES
    if (ws.cocrdup.ccrdupai.acctsidi.equals("*") || ws.cocrdup.ccrdupai.acctsidi.isSpaces()) {
      // COB(591): MOVE LOW-VALUES           TO  CC-ACCT-ID
      // COB(591):                               CCUP-NEW-ACCTID
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.lowValues();
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewAcctid.lowValues();
      // COB(593): ELSE
    } else {
      // COB(594): MOVE ACCTSIDI OF CCRDUPAI TO  CC-ACCT-ID
      // COB(594):                               CCUP-NEW-ACCTID
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.setValue(ws.cocrdup.ccrdupai.acctsidi);
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewAcctid.setValue(ws.cocrdup.ccrdupai.acctsidi);
      // COB(596): END-IF
    }
    // COB(598): IF  CARDSIDI OF CCRDUPAI = '*'
    // COB(598): OR  CARDSIDI OF CCRDUPAI = SPACES
    if (ws.cocrdup.ccrdupai.cardsidi.equals("*") || ws.cocrdup.ccrdupai.cardsidi.isSpaces()) {
      // COB(600): MOVE LOW-VALUES           TO  CC-CARD-NUM
      // COB(600):                               CCUP-NEW-CARDID
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum.lowValues();
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewCardid.lowValues();
      // COB(602): ELSE
    } else {
      // COB(603): MOVE CARDSIDI OF CCRDUPAI TO  CC-CARD-NUM
      // COB(603):                               CCUP-NEW-CARDID
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum.setValue(ws.cocrdup.ccrdupai.cardsidi);
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewCardid.setValue(ws.cocrdup.ccrdupai.cardsidi);
      // COB(605): END-IF
    }
    // COB(607): IF  CRDNAMEI OF CCRDUPAI = '*'
    // COB(607): OR  CRDNAMEI OF CCRDUPAI = SPACES
    if (ws.cocrdup.ccrdupai.crdnamei.equals("*") || ws.cocrdup.ccrdupai.crdnamei.isSpaces()) {
      // COB(609): MOVE LOW-VALUES           TO  CCUP-NEW-CRDNAME
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdname.lowValues();
      // COB(610): ELSE
    } else {
      // COB(611): MOVE CRDNAMEI OF CCRDUPAI TO  CCUP-NEW-CRDNAME
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdname.setValue(
          ws.cocrdup.ccrdupai.crdnamei);
      // COB(612): END-IF
    }
    // COB(614): IF  CRDSTCDI OF CCRDUPAI = '*'
    // COB(614): OR  CRDSTCDI OF CCRDUPAI = SPACES
    if (ws.cocrdup.ccrdupai.crdstcdi.equals("*") || ws.cocrdup.ccrdupai.crdstcdi.isSpaces()) {
      // COB(616): MOVE LOW-VALUES           TO  CCUP-NEW-CRDSTCD
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdstcd.lowValues();
      // COB(617): ELSE
    } else {
      // COB(618): MOVE CRDSTCDI OF CCRDUPAI TO  CCUP-NEW-CRDSTCD
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdstcd.setValue(
          ws.cocrdup.ccrdupai.crdstcdi);
      // COB(619): END-IF
    }
    // COB(621): MOVE EXPDAYI     OF CCRDUPAI  TO  CCUP-NEW-EXPDAY
    ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpday
        .setValue(ws.cocrdup.ccrdupai.expdayi);
    // COB(623): IF  EXPMONI OF CCRDUPAI = '*'
    // COB(623): OR  EXPMONI OF CCRDUPAI = SPACES
    if (ws.cocrdup.ccrdupai.expmoni.equals("*") || ws.cocrdup.ccrdupai.expmoni.isSpaces()) {
      // COB(625): MOVE LOW-VALUES           TO  CCUP-NEW-EXPMON
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpmon
          .lowValues();
      // COB(626): ELSE
    } else {
      // COB(627): MOVE EXPMONI OF CCRDUPAI  TO  CCUP-NEW-EXPMON
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpmon
          .setValue(ws.cocrdup.ccrdupai.expmoni);
      // COB(628): END-IF
    }
    // COB(630): IF  EXPYEARI OF CCRDUPAI = '*'
    // COB(630): OR  EXPYEARI OF CCRDUPAI = SPACES
    if (ws.cocrdup.ccrdupai.expyeari.equals("*") || ws.cocrdup.ccrdupai.expyeari.isSpaces()) {
      // COB(632): MOVE LOW-VALUES           TO  CCUP-NEW-EXPYEAR
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpyear
          .lowValues();
      // COB(633): ELSE
    } else {
      // COB(634): MOVE EXPYEARI OF CCRDUPAI TO  CCUP-NEW-EXPYEAR
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpyear
          .setValue(ws.cocrdup.ccrdupai.expyeari);
      // COB(635): END-IF
    }
  }

  protected void _1200EditMapInputs() {
    // COB(643): SET INPUT-OK                  TO TRUE
    ws.wsMiscStorage.setInputOk(true);
    // COB(645): IF  CCUP-DETAILS-NOT-FETCHED
    if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupDetailsNotFetched()) {
      // COB(647): PERFORM 1210-EDIT-ACCOUNT
      // COB(647):    THRU 1210-EDIT-ACCOUNT-EXIT
      //         VALIDATE THE SEARCH KEYS
      _1210EditAccount();
      // COB(650): PERFORM 1220-EDIT-CARD
      // COB(650):    THRU 1220-EDIT-CARD-EXIT
      _1220EditCard();
      // COB(653): MOVE LOW-VALUES                 TO CCUP-NEW-CARDDATA
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.lowValues();
      // COB(656): IF  FLG-ACCTFILTER-BLANK
      // COB(656): AND FLG-CARDFILTER-BLANK
      //        IF THE SEARCH CONDITIONS HAVE PROBLEMS SKIP OTHER EDITS
      if (ws.wsMiscStorage.flgAcctfilterBlank() && ws.wsMiscStorage.flgCardfilterBlank()) {
        // COB(658): SET NO-SEARCH-CRITERIA-RECEIVED TO TRUE
        ws.wsMiscStorage.setNoSearchCriteriaReceived(true);
        // COB(659): END-IF
      }
      // COB(661): GO TO 1200-EDIT-MAP-INPUTS-EXIT
      return;
      // COB(663): ELSE
    } else {
      // COB(664): CONTINUE
      // do nothing
      // COB(665): END-IF
    }
    // COB(668): SET FOUND-CARDS-FOR-ACCOUNT TO TRUE
    //     SEARCH KEYS ALREADY VALIDATED AND DATA FETCHED
    ws.wsMiscStorage.setFoundCardsForAccount(true);
    // COB(669): SET FLG-ACCTFILTER-ISVALID  TO TRUE
    ws.wsMiscStorage.setFlgAcctfilterIsvalid(true);
    // COB(670): SET FLG-CARDFILTER-ISVALID  TO TRUE
    ws.wsMiscStorage.setFlgCardfilterIsvalid(true);
    // COB(671): MOVE CCUP-OLD-ACCTID     TO CDEMO-ACCT-ID
    ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.setValue(
        ws.wsThisProgcommarea.ccupOldDetails.ccupOldAcctid);
    // COB(672): MOVE CCUP-OLD-CARDID     TO CDEMO-CARD-NUM
    ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.setValue(
        ws.wsThisProgcommarea.ccupOldDetails.ccupOldCardid);
    // COB(673): MOVE CCUP-OLD-CRDNAME    TO CARD-EMBOSSED-NAME
    ws.cvact02y.cardRecord.cardEmbossedName.setValue(
        ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldCrdname);
    // COB(674): MOVE CCUP-OLD-CRDSTCD    TO CARD-ACTIVE-STATUS
    ws.cvact02y.cardRecord.cardActiveStatus.setValue(
        ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldCrdstcd);
    // COB(675): MOVE CCUP-OLD-EXPDAY     TO CARD-EXPIRY-DAY
    ws.wsMiscStorage.cicsOutputEditVars.filler116.cardExpiryDay.setValue(
        ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldExpiraionDate.ccupOldExpday);
    // COB(676): MOVE CCUP-OLD-EXPMON     TO CARD-EXPIRY-MONTH
    ws.wsMiscStorage.cicsOutputEditVars.filler116.cardExpiryMonth.setValue(
        ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldExpiraionDate.ccupOldExpmon);
    // COB(677): MOVE CCUP-OLD-EXPYEAR    TO CARD-EXPIRY-YEAR
    ws.wsMiscStorage.cicsOutputEditVars.filler116.cardExpiryYear.setValue(
        ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldExpiraionDate.ccupOldExpyear);
    // COB(680): IF  (FUNCTION UPPER-CASE(CCUP-NEW-CARDDATA) EQUAL
    // COB(680):      FUNCTION UPPER-CASE(CCUP-OLD-CARDDATA))
    //     NEW DATA IS SAME AS OLD DATA
    if (ws.wsThisProgcommarea
        .ccupNewDetails
        .ccupNewCarddata
        .toUpperCase()
        .equals(ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.toUpperCase())) {
      // COB(682): SET NO-CHANGES-DETECTED TO TRUE
      ws.wsMiscStorage.setNoChangesDetected(true);
      // COB(683): END-IF
    }
    // COB(685): IF  NO-CHANGES-DETECTED
    // COB(685): OR  CCUP-CHANGES-OK-NOT-CONFIRMED
    // COB(685): OR  CCUP-CHANGES-OKAYED-AND-DONE
    if (ws.wsMiscStorage.noChangesDetected()
        || ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesOkNotConfirmed()
        || ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesOkayedAndDone()) {
      // COB(688): SET FLG-CARDNAME-ISVALID    TO TRUE
      ws.wsMiscStorage.setFlgCardnameIsvalid(true);
      // COB(689): SET FLG-CARDSTATUS-ISVALID  TO TRUE
      ws.wsMiscStorage.setFlgCardstatusIsvalid(true);
      // COB(690): SET FLG-CARDEXPMON-ISVALID  TO TRUE
      ws.wsMiscStorage.setFlgCardexpmonIsvalid(true);
      // COB(691): SET FLG-CARDEXPYEAR-ISVALID TO TRUE
      ws.wsMiscStorage.setFlgCardexpyearIsvalid(true);
      // COB(692): GO TO 1200-EDIT-MAP-INPUTS-EXIT
      return;
      // COB(693): END-IF
    }
    // COB(696): SET CCUP-CHANGES-NOT-OK    TO TRUE
    ws.wsThisProgcommarea.cardUpdateScreenData.setCcupChangesNotOk(true);
    // COB(698): PERFORM 1230-EDIT-NAME
    // COB(698):    THRU 1230-EDIT-NAME-EXIT
    _1230EditName();
    // COB(701): PERFORM 1240-EDIT-CARDSTATUS
    // COB(701):    THRU 1240-EDIT-CARDSTATUS-EXIT
    _1240EditCardstatus();
    // COB(704): PERFORM 1250-EDIT-EXPIRY-MON
    // COB(704):    THRU 1250-EDIT-EXPIRY-MON-EXIT
    _1250EditExpiryMon();
    // COB(707): PERFORM 1260-EDIT-EXPIRY-YEAR
    // COB(707):    THRU 1260-EDIT-EXPIRY-YEAR-EXIT
    _1260EditExpiryYear();
    // COB(710): IF INPUT-ERROR
    if (ws.wsMiscStorage.inputError()) {
      // COB(711): CONTINUE
      // do nothing
      // COB(712): ELSE
    } else {
      // COB(713): SET CCUP-CHANGES-OK-NOT-CONFIRMED TO TRUE
      ws.wsThisProgcommarea.cardUpdateScreenData.setCcupChangesOkNotConfirmed(true);
      // COB(714): END-IF
    }
  }

  protected void _1210EditAccount() {
    // COB(722): SET FLG-ACCTFILTER-NOT-OK TO TRUE
    ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
    // COB(725): IF CC-ACCT-ID   EQUAL LOW-VALUES
    // COB(725): OR CC-ACCT-ID   EQUAL SPACES
    // COB(725): OR CC-ACCT-ID-N EQUAL ZEROS
    //     Not supplied
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isLowValues()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isSpaces()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctIdN.equals(0)) {
      // COB(728): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(729): SET FLG-ACCTFILTER-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterBlank(true);
      // COB(730): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(731): SET WS-PROMPT-FOR-ACCT TO TRUE
        ws.wsMiscStorage.setWsPromptForAcct(true);
        // COB(732): END-IF
      }
      // COB(733): MOVE ZEROES       TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
      // COB(734): MOVE LOW-VALUES   TO CCUP-NEW-ACCTID
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewAcctid.lowValues();
      // COB(735): GO TO  1210-EDIT-ACCOUNT-EXIT
      return;
      // COB(736): END-IF
    }
    // COB(740): IF CC-ACCT-ID  IS NOT NUMERIC
    //
    //     Not numeric
    //     Not 11 characters
    if (!ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isNumeric()) {
      // COB(741): SET INPUT-ERROR TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(742): SET FLG-ACCTFILTER-NOT-OK TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
      // COB(743): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(744): MOVE
        // COB(744):               'ACCOUNT FILTER,IF SUPPLIED MUST BE A 11 DIGIT NUMBER'
        // COB(744):               TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setString(
            "ACCOUNT FILTER,IF SUPPLIED MUST BE A 11 DIGIT NUMBER");
        // COB(747): END-IF
      }
      // COB(748): MOVE ZERO       TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
      // COB(749): MOVE LOW-VALUES TO CCUP-NEW-ACCTID
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewAcctid.lowValues();
      // COB(750): GO TO 1210-EDIT-ACCOUNT-EXIT
      return;
      // COB(751): ELSE
    } else {
      // COB(752): MOVE CC-ACCT-ID TO CDEMO-ACCT-ID
      // COB(752):                    CCUP-NEW-ACCTID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.setValue(
          ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewAcctid.setValue(
          ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
      // COB(754): SET FLG-ACCTFILTER-ISVALID TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterIsvalid(true);
      // COB(755): END-IF
    }
  }

  protected void _1220EditCard() {
    // COB(765): SET FLG-CARDFILTER-NOT-OK TO TRUE
    //     Not numeric
    //     Not 16 characters
    ws.wsMiscStorage.setFlgCardfilterNotOk(true);
    // COB(768): IF CC-CARD-NUM   EQUAL LOW-VALUES
    // COB(768): OR CC-CARD-NUM   EQUAL SPACES
    // COB(768): OR CC-CARD-NUM-N EQUAL ZEROS
    //     Not supplied
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum.isLowValues()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum.isSpaces()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNumN.equals(0)) {
      // COB(771): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(772): SET FLG-CARDFILTER-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgCardfilterBlank(true);
      // COB(773): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(774): SET WS-PROMPT-FOR-CARD TO TRUE
        ws.wsMiscStorage.setWsPromptForCard(true);
        // COB(775): END-IF
      }
      // COB(777): MOVE ZEROES        TO CDEMO-CARD-NUM
      // COB(777):                      CCUP-NEW-CARDID
      ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.zeros();
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewCardid.zeros();
      // COB(779): GO TO  1220-EDIT-CARD-EXIT
      return;
      // COB(780): END-IF
    }
    // COB(784): IF CC-CARD-NUM  IS NOT NUMERIC
    //
    //     Not numeric
    //     Not 16 characters
    if (!ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum.isNumeric()) {
      // COB(785): SET INPUT-ERROR TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(786): SET FLG-CARDFILTER-NOT-OK TO TRUE
      ws.wsMiscStorage.setFlgCardfilterNotOk(true);
      // COB(787): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(788): MOVE
        // COB(788):               'CARD ID FILTER,IF SUPPLIED MUST BE A 16 DIGIT NUMBER'
        // COB(788):                 TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setString(
            "CARD ID FILTER,IF SUPPLIED MUST BE A 16 DIGIT NUMBER");
        // COB(791): END-IF
      }
      // COB(792): MOVE ZERO          TO CDEMO-CARD-NUM
      ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.zeros();
      // COB(793): MOVE LOW-VALUES    TO CCUP-NEW-CARDID
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewCardid.lowValues();
      // COB(794): GO TO 1220-EDIT-CARD-EXIT
      return;
      // COB(795): ELSE
    } else {
      // COB(796): MOVE CC-CARD-NUM-N TO CDEMO-CARD-NUM
      ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.setValue(
          ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNumN);
      // COB(797): MOVE CC-CARD-NUM   TO CCUP-NEW-CARDID
      ws.wsThisProgcommarea.ccupNewDetails.ccupNewCardid.setValue(
          ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum);
      // COB(798): SET FLG-CARDFILTER-ISVALID TO TRUE
      ws.wsMiscStorage.setFlgCardfilterIsvalid(true);
      // COB(799): END-IF
    }
  }

  protected void _1230EditName() {
    // COB(808): SET FLG-CARDNAME-NOT-OK      TO TRUE
    //     Not BLANK
    ws.wsMiscStorage.setFlgCardnameNotOk(true);
    // COB(811): IF CCUP-NEW-CRDNAME   EQUAL LOW-VALUES
    // COB(811): OR CCUP-NEW-CRDNAME   EQUAL SPACES
    // COB(811): OR CCUP-NEW-CRDNAME   EQUAL ZEROS
    //     Not supplied
    if (ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdname.isLowValues()
        || ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdname.isSpaces()
        || ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdname.equals(0)) {
      // COB(814): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(815): SET FLG-CARDNAME-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgCardnameBlank(true);
      // COB(816): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(817): SET WS-PROMPT-FOR-NAME TO TRUE
        ws.wsMiscStorage.setWsPromptForName(true);
        // COB(818): END-IF
      }
      // COB(819): GO TO  1230-EDIT-NAME-EXIT
      return;
      // COB(820): END-IF
    }
    // COB(823): MOVE CCUP-NEW-CRDNAME        TO CARD-NAME-CHECK
    //     Only Alphabets and space allowed
    ws.wsMiscStorage.cardNameCheck.setValue(
        ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdname);
    // COB(824): INSPECT CARD-NAME-CHECK
    // COB(824):   CONVERTING LIT-ALL-ALPHA-FROM
    // COB(824):           TO LIT-ALL-SPACES-TO
    ws.wsMiscStorage.cardNameCheck.inspectAndConvertCharacters(
        ws.wsLiterals.litAllAlphaFrom.getValue(), ws.wsLiterals.litAllSpacesTo.getValue());
    // COB(828): IF FUNCTION LENGTH(FUNCTION TRIM(CARD-NAME-CHECK)) = 0
    if (ws.wsMiscStorage.cardNameCheck.trim().length().equals(0)) {
      // COB(829): CONTINUE
      // do nothing
      // COB(830): ELSE
    } else {
      // COB(831): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(832): SET FLG-CARDNAME-NOT-OK   TO TRUE
      ws.wsMiscStorage.setFlgCardnameNotOk(true);
      // COB(833): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(834): SET WS-NAME-MUST-BE-ALPHA  TO TRUE
        ws.wsMiscStorage.setWsNameMustBeAlpha(true);
        // COB(835): END-IF
      }
      // COB(836): GO TO  1230-EDIT-NAME-EXIT
      return;
      // COB(837): END-IF
    }
    // COB(839): SET FLG-CARDNAME-ISVALID     TO TRUE
    ws.wsMiscStorage.setFlgCardnameIsvalid(true);
  }

  protected void _1240EditCardstatus() {
    // COB(847): SET FLG-CARDSTATUS-NOT-OK      TO TRUE
    //     Must be Y or N
    ws.wsMiscStorage.setFlgCardstatusNotOk(true);
    // COB(850): IF CCUP-NEW-CRDSTCD   EQUAL LOW-VALUES
    // COB(850): OR CCUP-NEW-CRDSTCD   EQUAL SPACES
    // COB(850): OR CCUP-NEW-CRDSTCD   EQUAL ZEROS
    //     Not supplied
    if (ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdstcd.isLowValues()
        || ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdstcd.isSpaces()
        || ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdstcd.equals(0)) {
      // COB(853): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(854): SET FLG-CARDSTATUS-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgCardstatusBlank(true);
      // COB(855): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(856): SET CARD-STATUS-MUST-BE-YES-NO TO TRUE
        ws.wsMiscStorage.setCardStatusMustBeYesNo(true);
        // COB(857): END-IF
      }
      // COB(858): GO TO  1240-EDIT-CARDSTATUS-EXIT
      return;
      // COB(859): END-IF
    }
    // COB(861): MOVE CCUP-NEW-CRDSTCD          TO FLG-YES-NO-CHECK
    ws.wsMiscStorage.flgYesNoCheck.setValue(
        ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdstcd);
    // COB(863): IF FLG-YES-NO-VALID
    if (ws.wsMiscStorage.flgYesNoValid()) {
      // COB(864): SET FLG-CARDSTATUS-ISVALID  TO TRUE
      ws.wsMiscStorage.setFlgCardstatusIsvalid(true);
      // COB(865): ELSE
    } else {
      // COB(866): SET INPUT-ERROR             TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(867): SET FLG-CARDSTATUS-NOT-OK   TO TRUE
      ws.wsMiscStorage.setFlgCardstatusNotOk(true);
      // COB(868): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(869): SET CARD-STATUS-MUST-BE-YES-NO  TO TRUE
        ws.wsMiscStorage.setCardStatusMustBeYesNo(true);
        // COB(870): END-IF
      }
      // COB(871): GO TO  1240-EDIT-CARDSTATUS-EXIT
      return;
      // COB(872): END-IF
    }
  }

  protected void _1250EditExpiryMon() {
    // COB(880): SET FLG-CARDEXPMON-NOT-OK      TO TRUE
    ws.wsMiscStorage.setFlgCardexpmonNotOk(true);
    // COB(883): IF CCUP-NEW-EXPMON   EQUAL LOW-VALUES
    // COB(883): OR CCUP-NEW-EXPMON   EQUAL SPACES
    // COB(883): OR CCUP-NEW-EXPMON   EQUAL ZEROS
    //     Not supplied
    if (ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpmon
            .isLowValues()
        || ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpmon
            .isSpaces()
        || ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpmon
            .equals(0)) {
      // COB(886): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(887): SET FLG-CARDEXPMON-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgCardexpmonBlank(true);
      // COB(888): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(889): SET CARD-EXPIRY-MONTH-NOT-VALID TO TRUE
        ws.wsMiscStorage.setCardExpiryMonthNotValid(true);
        // COB(890): END-IF
      }
      // COB(891): GO TO  1250-EDIT-EXPIRY-MON-EXIT
      return;
      // COB(892): END-IF
    }
    // COB(896): MOVE CCUP-NEW-EXPMON           TO CARD-MONTH-CHECK
    //     Must be numeric
    //     Must be 1 to 12
    ws.wsMiscStorage.cardMonthCheck.setValue(
        ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpmon);
    // COB(898): IF VALID-MONTH
    if (ws.wsMiscStorage.validMonth()) {
      // COB(899): SET FLG-CARDEXPMON-ISVALID  TO TRUE
      ws.wsMiscStorage.setFlgCardexpmonIsvalid(true);
      // COB(900): ELSE
    } else {
      // COB(901): SET INPUT-ERROR             TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(902): SET FLG-CARDEXPMON-NOT-OK   TO TRUE
      ws.wsMiscStorage.setFlgCardexpmonNotOk(true);
      // COB(903): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(904): SET CARD-EXPIRY-MONTH-NOT-VALID  TO TRUE
        ws.wsMiscStorage.setCardExpiryMonthNotValid(true);
        // COB(905): END-IF
      }
      // COB(906): GO TO  1250-EDIT-EXPIRY-MON-EXIT
      return;
      // COB(907): END-IF
    }
  }

  protected void _1260EditExpiryYear() {
    // COB(916): IF CCUP-NEW-EXPYEAR   EQUAL LOW-VALUES
    // COB(916): OR CCUP-NEW-EXPYEAR   EQUAL SPACES
    // COB(916): OR CCUP-NEW-EXPYEAR   EQUAL ZEROS
    //     Not supplied
    if (ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpyear
            .isLowValues()
        || ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpyear
            .isSpaces()
        || ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpyear
            .equals(0)) {
      // COB(919): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(920): SET FLG-CARDEXPYEAR-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgCardexpyearBlank(true);
      // COB(921): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(922): SET CARD-EXPIRY-YEAR-NOT-VALID TO TRUE
        ws.wsMiscStorage.setCardExpiryYearNotValid(true);
        // COB(923): END-IF
      }
      // COB(924): GO TO  1260-EDIT-EXPIRY-YEAR-EXIT
      return;
      // COB(925): END-IF
    }
    // COB(930): SET FLG-CARDEXPYEAR-NOT-OK      TO TRUE
    //     Must be numeric
    //     Must be 1 to 12
    ws.wsMiscStorage.setFlgCardexpyearNotOk(true);
    // COB(932): MOVE CCUP-NEW-EXPYEAR           TO CARD-YEAR-CHECK
    ws.wsMiscStorage.cardYearCheck.setValue(
        ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpyear);
    // COB(934): IF VALID-YEAR
    if (ws.wsMiscStorage.validYear()) {
      // COB(935): SET FLG-CARDEXPYEAR-ISVALID  TO TRUE
      ws.wsMiscStorage.setFlgCardexpyearIsvalid(true);
      // COB(936): ELSE
    } else {
      // COB(937): SET INPUT-ERROR              TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(938): SET FLG-CARDEXPYEAR-NOT-OK   TO TRUE
      ws.wsMiscStorage.setFlgCardexpyearNotOk(true);
      // COB(939): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(940): SET CARD-EXPIRY-YEAR-NOT-VALID  TO TRUE
        ws.wsMiscStorage.setCardExpiryYearNotValid(true);
        // COB(941): END-IF
      }
      // COB(942): GO TO  1260-EDIT-EXPIRY-YEAR-EXIT
      return;
      // COB(943): END-IF
    }
  }

  protected void _2000DecideAction() {
    // COB(949): EVALUATE TRUE
    // COB(954): WHEN CCUP-DETAILS-NOT-FETCHED
    // *****************************************************************
    //        NO DETAILS SHOWN.
    //        SO GET THEM AND SETUP DETAIL EDIT SCREEN
    // *****************************************************************
    if ((ws.wsThisProgcommarea.cardUpdateScreenData.ccupDetailsNotFetched())
        // COB(958): WHEN CCARD-AID-PFK12
        // *****************************************************************
        //        CHANGES MADE. BUT USER CANCELS
        // *****************************************************************
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk12()) {
      // COB(959): IF  FLG-ACCTFILTER-ISVALID
      // COB(959): AND FLG-CARDFILTER-ISVALID
      if (ws.wsMiscStorage.flgAcctfilterIsvalid() && ws.wsMiscStorage.flgCardfilterIsvalid()) {
        // COB(961): PERFORM 9000-READ-DATA
        // COB(961):    THRU 9000-READ-DATA-EXIT
        _9000ReadData();
        // COB(963): IF FOUND-CARDS-FOR-ACCOUNT
        if (ws.wsMiscStorage.foundCardsForAccount()) {
          // COB(964): SET CCUP-SHOW-DETAILS    TO TRUE
          ws.wsThisProgcommarea.cardUpdateScreenData.setCcupShowDetails(true);
          // COB(965): END-IF
        }
        // COB(966): END-IF
      }
      // COB(971): WHEN CCUP-SHOW-DETAILS
      // *****************************************************************
      //        DETAILS SHOWN
      //        CHECK CHANGES AND ASK CONFIRMATION IF GOOD
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupShowDetails()) {
      // COB(972): IF INPUT-ERROR
      // COB(972): OR NO-CHANGES-DETECTED
      if (ws.wsMiscStorage.inputError() || ws.wsMiscStorage.noChangesDetected()) {
        // COB(974): CONTINUE
        // do nothing
        // COB(975): ELSE
      } else {
        // COB(976): SET CCUP-CHANGES-OK-NOT-CONFIRMED TO TRUE
        ws.wsThisProgcommarea.cardUpdateScreenData.setCcupChangesOkNotConfirmed(true);
        // COB(977): END-IF
      }
      // COB(982): WHEN CCUP-CHANGES-NOT-OK
      // *****************************************************************
      //        DETAILS SHOWN
      //        BUT INPUT EDIT ERRORS FOUND
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesNotOk()) {
      // COB(983): CONTINUE
      // do nothing
      // COB(988): WHEN CCUP-CHANGES-OK-NOT-CONFIRMED
      // COB(988):  AND CCARD-AID-PFK05
      // *****************************************************************
      //        DETAILS EDITED , FOUND OK, CONFIRM SAVE REQUESTED
      //        CONFIRMATION GIVEN.SO SAVE THE CHANGES
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesOkNotConfirmed()
        && ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk05()) {
      // COB(990): PERFORM 9200-WRITE-PROCESSING
      // COB(990):    THRU 9200-WRITE-PROCESSING-EXIT
      rcNext = Flow._9200WriteProcessing;
      while (!rcNext.equals(Flow.Exit)) {
        switch (rcNext) {
          case _9200WriteProcessing:
            rcNext = _9200WriteProcessing();
            if (rcNext.equals(Flow.Exit)) {
              rcNext = Flow._9200WriteProcessingExit;
            }
            break;
          case _9200WriteProcessingExit:
            _9200WriteProcessingExit();
            rcNext = Flow.Exit;
            break;
          default:
            throw new RuntimeException("Invalid flow option: " + rcNext);
        }
      }
      // COB(992): EVALUATE TRUE
      // COB(993): WHEN COULD-NOT-LOCK-FOR-UPDATE
      if (ws.wsMiscStorage.couldNotLockForUpdate()) {
        // COB(994): SET CCUP-CHANGES-OKAYED-LOCK-ERROR TO TRUE
        ws.wsThisProgcommarea.cardUpdateScreenData.setCcupChangesOkayedLockError(true);
        // COB(995): WHEN LOCKED-BUT-UPDATE-FAILED
      } else if (ws.wsMiscStorage.lockedButUpdateFailed()) {
        // COB(996): SET CCUP-CHANGES-OKAYED-BUT-FAILED TO TRUE
        ws.wsThisProgcommarea.cardUpdateScreenData.setCcupChangesOkayedButFailed(true);
        // COB(997): WHEN DATA-WAS-CHANGED-BEFORE-UPDATE
      } else if (ws.wsMiscStorage.dataWasChangedBeforeUpdate()) {
        // COB(998): SET CCUP-SHOW-DETAILS            TO TRUE
        ws.wsThisProgcommarea.cardUpdateScreenData.setCcupShowDetails(true);
        // COB(999): WHEN OTHER
      } else {
        // COB(1000): SET CCUP-CHANGES-OKAYED-AND-DONE   TO TRUE
        ws.wsThisProgcommarea.cardUpdateScreenData.setCcupChangesOkayedAndDone(true);
        // COB(1001): END-EVALUATE
      }
      // COB(1006): WHEN CCUP-CHANGES-OK-NOT-CONFIRMED
      // *****************************************************************
      //        DETAILS EDITED , FOUND OK, CONFIRM SAVE REQUESTED
      //        CONFIRMATION NOT GIVEN. SO SHOW DETAILS AGAIN
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesOkNotConfirmed()) {
      // COB(1007): CONTINUE
      // do nothing
      // COB(1011): WHEN CCUP-CHANGES-OKAYED-AND-DONE
      // *****************************************************************
      //        SHOW CONFIRMATION. GO BACK TO SQUARE 1
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesOkayedAndDone()) {
      // COB(1012): SET CCUP-SHOW-DETAILS TO TRUE
      ws.wsThisProgcommarea.cardUpdateScreenData.setCcupShowDetails(true);
      // COB(1013): IF CDEMO-FROM-TRANID    EQUAL LOW-VALUES
      // COB(1013): OR CDEMO-FROM-TRANID    EQUAL SPACES
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isSpaces()) {
        // COB(1015): MOVE ZEROES       TO CDEMO-ACCT-ID
        // COB(1015):                      CDEMO-CARD-NUM
        ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
        ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.zeros();
        // COB(1017): MOVE LOW-VALUES   TO CDEMO-ACCT-STATUS
        ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctStatus.lowValues();
        // COB(1018): END-IF
      }
      // COB(1019): WHEN OTHER
    } else {
      // COB(1020): MOVE LIT-THISPGM    TO ABEND-CULPRIT
      ws.csmsg02y.abendData.abendCulprit.setValue(ws.wsLiterals.litThispgm);
      // COB(1021): MOVE '0001'         TO ABEND-CODE
      ws.csmsg02y.abendData.abendCode.setString("0001");
      // COB(1022): MOVE SPACES         TO ABEND-REASON
      ws.csmsg02y.abendData.abendReason.spaces();
      // COB(1023): MOVE 'UNEXPECTED DATA SCENARIO'
      // COB(1023):                     TO ABEND-MSG
      ws.csmsg02y.abendData.abendMsg.setString("UNEXPECTED DATA SCENARIO");
      // COB(1025): PERFORM ABEND-ROUTINE
      // COB(1025):    THRU ABEND-ROUTINE-EXIT
      abendRoutine();
      // COB(1027): END-EVALUATE
    }
  }

  protected void _3000SendMap() {
    // COB(1036): PERFORM 3100-SCREEN-INIT
    // COB(1036):    THRU 3100-SCREEN-INIT-EXIT
    _3100ScreenInit();
    // COB(1038): PERFORM 3200-SETUP-SCREEN-VARS
    // COB(1038):    THRU 3200-SETUP-SCREEN-VARS-EXIT
    _3200SetupScreenVars();
    // COB(1040): PERFORM 3250-SETUP-INFOMSG
    // COB(1040):    THRU 3250-SETUP-INFOMSG-EXIT
    _3250SetupInfomsg();
    // COB(1042): PERFORM 3300-SETUP-SCREEN-ATTRS
    // COB(1042):    THRU 3300-SETUP-SCREEN-ATTRS-EXIT
    _3300SetupScreenAttrs();
    // COB(1044): PERFORM 3400-SEND-SCREEN
    // COB(1044):    THRU 3400-SEND-SCREEN-EXIT
    _3400SendScreen();
  }

  protected void _3100ScreenInit() {
    // COB(1053): MOVE LOW-VALUES TO CCRDUPAO
    ws.cocrdup.ccrdupao.lowValues();
    // COB(1055): MOVE FUNCTION CURRENT-DATE     TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(1057): MOVE CCDA-TITLE01              TO TITLE01O OF CCRDUPAO
    ws.cocrdup.ccrdupao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(1058): MOVE CCDA-TITLE02              TO TITLE02O OF CCRDUPAO
    ws.cocrdup.ccrdupao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(1059): MOVE LIT-THISTRANID            TO TRNNAMEO OF CCRDUPAO
    ws.cocrdup.ccrdupao.trnnameo.setValue(ws.wsLiterals.litThistranid);
    // COB(1060): MOVE LIT-THISPGM               TO PGMNAMEO OF CCRDUPAO
    ws.cocrdup.ccrdupao.pgmnameo.setValue(ws.wsLiterals.litThispgm);
    // COB(1062): MOVE FUNCTION CURRENT-DATE     TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(1064): MOVE WS-CURDATE-MONTH          TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(1065): MOVE WS-CURDATE-DAY            TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(1066): MOVE WS-CURDATE-YEAR(3:2)      TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(1068): MOVE WS-CURDATE-MM-DD-YY       TO CURDATEO OF CCRDUPAO
    ws.cocrdup.ccrdupao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(1070): MOVE WS-CURTIME-HOURS          TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(1071): MOVE WS-CURTIME-MINUTE         TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(1072): MOVE WS-CURTIME-SECOND         TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(1074): MOVE WS-CURTIME-HH-MM-SS       TO CURTIMEO OF CCRDUPAO
    ws.cocrdup.ccrdupao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  protected void _3200SetupScreenVars() {
    // COB(1084): IF CDEMO-PGM-ENTER
    //     INITIALIZE SEARCH CRITERIA
    if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()) {
      // COB(1085): CONTINUE
      // do nothing
      // COB(1086): ELSE
    } else {
      // COB(1087): IF CC-ACCT-ID-N = 0
      if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctIdN.equals(0)) {
        // COB(1088): MOVE LOW-VALUES          TO ACCTSIDO OF CCRDUPAO
        ws.cocrdup.ccrdupao.acctsido.lowValues();
        // COB(1089): ELSE
      } else {
        // COB(1090): MOVE CC-ACCT-ID          TO ACCTSIDO OF CCRDUPAO
        ws.cocrdup.ccrdupao.acctsido.setValue(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
        // COB(1091): END-IF
      }
      // COB(1093): IF CC-CARD-NUM-N = 0
      if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNumN.equals(0)) {
        // COB(1094): MOVE LOW-VALUES           TO CARDSIDO OF CCRDUPAO
        ws.cocrdup.ccrdupao.cardsido.lowValues();
        // COB(1095): ELSE
      } else {
        // COB(1096): MOVE CC-CARD-NUM          TO CARDSIDO OF CCRDUPAO
        ws.cocrdup.ccrdupao.cardsido.setValue(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum);
        // COB(1097): END-IF
      }
      // COB(1099): EVALUATE TRUE
      // COB(1100): WHEN CCUP-DETAILS-NOT-FETCHED
      if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupDetailsNotFetched()) {
        // COB(1101): MOVE LOW-VALUES         TO CRDNAMEO OF CCRDUPAO
        // COB(1101):                            CRDNAMEO OF CCRDUPAO
        // COB(1101):                            CRDSTCDO OF CCRDUPAO
        // COB(1101):                            EXPDAYO  OF CCRDUPAO
        // COB(1101):                            EXPMONO  OF CCRDUPAO
        // COB(1101):                            EXPYEARO OF CCRDUPAO
        ws.cocrdup.ccrdupao.crdnameo.lowValues();
        ws.cocrdup.ccrdupao.crdnameo.lowValues();
        ws.cocrdup.ccrdupao.crdstcdo.lowValues();
        ws.cocrdup.ccrdupao.expdayo.lowValues();
        ws.cocrdup.ccrdupao.expmono.lowValues();
        ws.cocrdup.ccrdupao.expyearo.lowValues();
        // COB(1107): WHEN CCUP-SHOW-DETAILS
      } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupShowDetails()) {
        // COB(1108): MOVE CCUP-OLD-CRDNAME    TO CRDNAMEO OF CCRDUPAO
        ws.cocrdup.ccrdupao.crdnameo.setValue(
            ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldCrdname);
        // COB(1109): MOVE CCUP-OLD-CRDSTCD    TO CRDSTCDO OF CCRDUPAO
        ws.cocrdup.ccrdupao.crdstcdo.setValue(
            ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldCrdstcd);
        // COB(1110): MOVE CCUP-OLD-EXPDAY     TO EXPDAYO  OF CCRDUPAO
        ws.cocrdup.ccrdupao.expdayo.setValue(
            ws.wsThisProgcommarea
                .ccupOldDetails
                .ccupOldCarddata
                .ccupOldExpiraionDate
                .ccupOldExpday);
        // COB(1111): MOVE CCUP-OLD-EXPMON     TO EXPMONO  OF CCRDUPAO
        ws.cocrdup.ccrdupao.expmono.setValue(
            ws.wsThisProgcommarea
                .ccupOldDetails
                .ccupOldCarddata
                .ccupOldExpiraionDate
                .ccupOldExpmon);
        // COB(1112): MOVE CCUP-OLD-EXPYEAR    TO EXPYEARO OF CCRDUPAO
        ws.cocrdup.ccrdupao.expyearo.setValue(
            ws.wsThisProgcommarea
                .ccupOldDetails
                .ccupOldCarddata
                .ccupOldExpiraionDate
                .ccupOldExpyear);
        // COB(1113): WHEN CCUP-CHANGES-MADE
      } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesMade()) {
        // COB(1114): MOVE CCUP-NEW-CRDNAME    TO CRDNAMEO OF CCRDUPAO
        ws.cocrdup.ccrdupao.crdnameo.setValue(
            ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdname);
        // COB(1115): MOVE CCUP-NEW-CRDSTCD    TO CRDSTCDO OF CCRDUPAO
        ws.cocrdup.ccrdupao.crdstcdo.setValue(
            ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdstcd);
        // COB(1116): MOVE CCUP-NEW-EXPMON     TO EXPMONO  OF CCRDUPAO
        ws.cocrdup.ccrdupao.expmono.setValue(
            ws.wsThisProgcommarea
                .ccupNewDetails
                .ccupNewCarddata
                .ccupNewExpiraionDate
                .ccupNewExpmon);
        // COB(1117): MOVE CCUP-NEW-EXPYEAR    TO EXPYEARO OF CCRDUPAO
        ws.cocrdup.ccrdupao.expyearo.setValue(
            ws.wsThisProgcommarea
                .ccupNewDetails
                .ccupNewCarddata
                .ccupNewExpiraionDate
                .ccupNewExpyear);
        // COB(1123): MOVE CCUP-OLD-EXPDAY     TO EXPDAYO  OF CCRDUPAO
        // *****************************************************************
        //                MOVE OLD VALUES TO NON-DISPLAY FIELDS
        //                THAT WE ARE NOT ALLOWING USER TO CHANGE(FOR NOW)
        // ****************************************************************
        //                MOVE CCUP-NEW-EXPDAY     TO EXPDAYO  OF CCRDUPAO
        ws.cocrdup.ccrdupao.expdayo.setValue(
            ws.wsThisProgcommarea
                .ccupOldDetails
                .ccupOldCarddata
                .ccupOldExpiraionDate
                .ccupOldExpday);
        // COB(1124): WHEN OTHER
      } else {
        // COB(1125): MOVE CCUP-OLD-CRDNAME    TO CRDNAMEO OF CCRDUPAO
        ws.cocrdup.ccrdupao.crdnameo.setValue(
            ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldCrdname);
        // COB(1126): MOVE CCUP-OLD-CRDSTCD    TO CRDSTCDO OF CCRDUPAO
        ws.cocrdup.ccrdupao.crdstcdo.setValue(
            ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldCrdstcd);
        // COB(1127): MOVE CCUP-OLD-EXPDAY     TO EXPDAYO  OF CCRDUPAO
        ws.cocrdup.ccrdupao.expdayo.setValue(
            ws.wsThisProgcommarea
                .ccupOldDetails
                .ccupOldCarddata
                .ccupOldExpiraionDate
                .ccupOldExpday);
        // COB(1128): MOVE CCUP-OLD-EXPMON     TO EXPMONO  OF CCRDUPAO
        ws.cocrdup.ccrdupao.expmono.setValue(
            ws.wsThisProgcommarea
                .ccupOldDetails
                .ccupOldCarddata
                .ccupOldExpiraionDate
                .ccupOldExpmon);
        // COB(1129): MOVE CCUP-OLD-EXPYEAR    TO EXPYEARO OF CCRDUPAO
        ws.cocrdup.ccrdupao.expyearo.setValue(
            ws.wsThisProgcommarea
                .ccupOldDetails
                .ccupOldCarddata
                .ccupOldExpiraionDate
                .ccupOldExpyear);
        // COB(1130): END-EVALUATE
      }
      // COB(1133): END-IF
    }
  }

  protected void _3250SetupInfomsg() {
    // COB(1140): EVALUATE TRUE
    //     SETUP INFORMATION MESSAGE
    // COB(1141): WHEN CDEMO-PGM-ENTER
    if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()) {
      // COB(1142): SET  PROMPT-FOR-SEARCH-KEYS TO TRUE
      ws.wsMiscStorage.setPromptForSearchKeys(true);
      // COB(1143): WHEN CCUP-DETAILS-NOT-FETCHED
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupDetailsNotFetched()) {
      // COB(1144): SET PROMPT-FOR-SEARCH-KEYS      TO TRUE
      ws.wsMiscStorage.setPromptForSearchKeys(true);
      // COB(1145): WHEN CCUP-SHOW-DETAILS
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupShowDetails()) {
      // COB(1146): SET FOUND-CARDS-FOR-ACCOUNT    TO TRUE
      ws.wsMiscStorage.setFoundCardsForAccount(true);
      // COB(1147): WHEN CCUP-CHANGES-NOT-OK
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesNotOk()) {
      // COB(1148): SET PROMPT-FOR-CHANGES         TO TRUE
      ws.wsMiscStorage.setPromptForChanges(true);
      // COB(1149): WHEN CCUP-CHANGES-OK-NOT-CONFIRMED
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesOkNotConfirmed()) {
      // COB(1150): SET PROMPT-FOR-CONFIRMATION    TO TRUE
      ws.wsMiscStorage.setPromptForConfirmation(true);
      // COB(1151): WHEN CCUP-CHANGES-OKAYED-AND-DONE
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesOkayedAndDone()) {
      // COB(1152): SET CONFIRM-UPDATE-SUCCESS     TO TRUE
      ws.wsMiscStorage.setConfirmUpdateSuccess(true);
      // COB(1153): WHEN CCUP-CHANGES-OKAYED-LOCK-ERROR
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesOkayedLockError()) {
      // COB(1154): SET INFORM-FAILURE             TO TRUE
      ws.wsMiscStorage.setInformFailure(true);
      // COB(1155): WHEN CCUP-CHANGES-OKAYED-BUT-FAILED
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesOkayedButFailed()) {
      // COB(1156): SET INFORM-FAILURE             TO TRUE
      ws.wsMiscStorage.setInformFailure(true);
      // COB(1157): WHEN WS-NO-INFO-MESSAGE
    } else if (ws.wsMiscStorage.wsNoInfoMessage()) {
      // COB(1158): SET PROMPT-FOR-SEARCH-KEYS      TO TRUE
      ws.wsMiscStorage.setPromptForSearchKeys(true);
      // COB(1159): END-EVALUATE
    }
    // COB(1161): MOVE WS-INFO-MSG                    TO INFOMSGO OF CCRDUPAO
    ws.cocrdup.ccrdupao.infomsgo.setValue(ws.wsMiscStorage.wsInfoMsg);
    // COB(1163): MOVE WS-RETURN-MSG                  TO ERRMSGO OF CCRDUPAO
    ws.cocrdup.ccrdupao.errmsgo.setValue(ws.wsMiscStorage.wsReturnMsg);
  }

  protected void _3300SetupScreenAttrs() {
    // COB(1172): EVALUATE TRUE
    //     PROTECT OR UNPROTECT BASED ON CONTEXT
    // COB(1173): WHEN CCUP-DETAILS-NOT-FETCHED
    if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupDetailsNotFetched()) {
      // COB(1174): MOVE DFHBMFSE      TO ACCTSIDA OF CCRDUPAI
      // COB(1174):                       CARDSIDA OF CCRDUPAI
      ws.cocrdup.ccrdupai.filler57.acctsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
      ws.cocrdup.ccrdupai.filler63.cardsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(1176): MOVE DFHBMPRF      TO CRDNAMEA OF CCRDUPAI
      // COB(1176):                       CRDSTCDA OF CCRDUPAI
      // COB(1176):       *                                  EXPDAYA  OF CCRDUPAI
      // COB(1176):                       EXPMONA  OF CCRDUPAI
      // COB(1176):                       EXPYEARA OF CCRDUPAI
      ws.cocrdup.ccrdupai.filler69.crdnamea.setValue(Dfhbmsca.DFHBMPRF.getValue());
      ws.cocrdup.ccrdupai.filler75.crdstcda.setValue(Dfhbmsca.DFHBMPRF.getValue());
      ws.cocrdup.ccrdupai.filler81.expmona.setValue(Dfhbmsca.DFHBMPRF.getValue());
      ws.cocrdup.ccrdupai.filler87.expyeara.setValue(Dfhbmsca.DFHBMPRF.getValue());
      // COB(1181): WHEN  CCUP-SHOW-DETAILS
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupShowDetails()
        // COB(1182): WHEN  CCUP-CHANGES-NOT-OK
        || ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesNotOk()) {
      // COB(1183): MOVE DFHBMPRF      TO ACCTSIDA OF CCRDUPAI
      // COB(1183):                       CARDSIDA OF CCRDUPAI
      ws.cocrdup.ccrdupai.filler57.acctsida.setValue(Dfhbmsca.DFHBMPRF.getValue());
      ws.cocrdup.ccrdupai.filler63.cardsida.setValue(Dfhbmsca.DFHBMPRF.getValue());
      // COB(1186): MOVE DFHBMFSE      TO CRDNAMEA OF CCRDUPAI
      // COB(1186):                       CRDSTCDA OF CCRDUPAI
      // COB(1186):
      // COB(1186):                       EXPMONA  OF CCRDUPAI
      // COB(1186):                       EXPYEARA OF CCRDUPAI
      //                                   EXPDAYA  OF CCRDUPAI
      ws.cocrdup.ccrdupai.filler69.crdnamea.setValue(Dfhbmsca.DFHBMFSE.getValue());
      ws.cocrdup.ccrdupai.filler75.crdstcda.setValue(Dfhbmsca.DFHBMFSE.getValue());
      ws.cocrdup.ccrdupai.filler81.expmona.setValue(Dfhbmsca.DFHBMFSE.getValue());
      ws.cocrdup.ccrdupai.filler87.expyeara.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(1191): WHEN CCUP-CHANGES-OK-NOT-CONFIRMED
    } else if (ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesOkNotConfirmed()
        // COB(1192): WHEN CCUP-CHANGES-OKAYED-AND-DONE
        || ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesOkayedAndDone()) {
      // COB(1193): MOVE DFHBMPRF      TO ACCTSIDA OF CCRDUPAI
      // COB(1193):                       CARDSIDA OF CCRDUPAI
      // COB(1193):                       CRDNAMEA OF CCRDUPAI
      // COB(1193):                       CRDSTCDA OF CCRDUPAI
      // COB(1193):       *                                  EXPDAYA  OF CCRDUPAI
      // COB(1193):                       EXPMONA  OF CCRDUPAI
      // COB(1193):                       EXPYEARA OF CCRDUPAI
      ws.cocrdup.ccrdupai.filler57.acctsida.setValue(Dfhbmsca.DFHBMPRF.getValue());
      ws.cocrdup.ccrdupai.filler63.cardsida.setValue(Dfhbmsca.DFHBMPRF.getValue());
      ws.cocrdup.ccrdupai.filler69.crdnamea.setValue(Dfhbmsca.DFHBMPRF.getValue());
      ws.cocrdup.ccrdupai.filler75.crdstcda.setValue(Dfhbmsca.DFHBMPRF.getValue());
      ws.cocrdup.ccrdupai.filler81.expmona.setValue(Dfhbmsca.DFHBMPRF.getValue());
      ws.cocrdup.ccrdupai.filler87.expyeara.setValue(Dfhbmsca.DFHBMPRF.getValue());
      // COB(1200): WHEN OTHER
    } else {
      // COB(1201): MOVE DFHBMFSE      TO ACCTSIDA OF CCRDUPAI
      // COB(1201):                       CARDSIDA OF CCRDUPAI
      ws.cocrdup.ccrdupai.filler57.acctsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
      ws.cocrdup.ccrdupai.filler63.cardsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(1203): MOVE DFHBMPRF      TO CRDNAMEA OF CCRDUPAI
      // COB(1203):                       CRDSTCDA OF CCRDUPAI
      // COB(1203):       *                                  EXPDAYA  OF CCRDUPAI
      // COB(1203):                       EXPMONA  OF CCRDUPAI
      // COB(1203):                       EXPYEARA OF CCRDUPAI
      ws.cocrdup.ccrdupai.filler69.crdnamea.setValue(Dfhbmsca.DFHBMPRF.getValue());
      ws.cocrdup.ccrdupai.filler75.crdstcda.setValue(Dfhbmsca.DFHBMPRF.getValue());
      ws.cocrdup.ccrdupai.filler81.expmona.setValue(Dfhbmsca.DFHBMPRF.getValue());
      ws.cocrdup.ccrdupai.filler87.expyeara.setValue(Dfhbmsca.DFHBMPRF.getValue());
      // COB(1208): END-EVALUATE
    }
    // COB(1211): EVALUATE TRUE
    //     POSITION CURSOR
    // COB(1212): WHEN FOUND-CARDS-FOR-ACCOUNT
    if ((ws.wsMiscStorage.foundCardsForAccount())
        // COB(1213): WHEN NO-CHANGES-DETECTED
        || ws.wsMiscStorage.noChangesDetected()) {
      // COB(1214): MOVE -1              TO CRDNAMEL OF CCRDUPAI
      ws.cocrdup.ccrdupai.crdnamel.setValue(-1);
      // COB(1215): WHEN FLG-ACCTFILTER-NOT-OK
    } else if (ws.wsMiscStorage.flgAcctfilterNotOk()
        // COB(1216): WHEN FLG-ACCTFILTER-BLANK
        || ws.wsMiscStorage.flgAcctfilterBlank()) {
      // COB(1217): MOVE -1             TO ACCTSIDL OF CCRDUPAI
      ws.cocrdup.ccrdupai.acctsidl.setValue(-1);
      // COB(1218): WHEN FLG-CARDFILTER-NOT-OK
    } else if (ws.wsMiscStorage.flgCardfilterNotOk()
        // COB(1219): WHEN FLG-CARDFILTER-BLANK
        || ws.wsMiscStorage.flgCardfilterBlank()) {
      // COB(1220): MOVE -1             TO CARDSIDL OF CCRDUPAI
      ws.cocrdup.ccrdupai.cardsidl.setValue(-1);
      // COB(1221): WHEN FLG-CARDNAME-NOT-OK
    } else if (ws.wsMiscStorage.flgCardnameNotOk()
        // COB(1222): WHEN FLG-CARDNAME-BLANK
        || ws.wsMiscStorage.flgCardnameBlank()) {
      // COB(1223): MOVE -1              TO CRDNAMEL OF  CCRDUPAI
      ws.cocrdup.ccrdupai.crdnamel.setValue(-1);
      // COB(1224): WHEN FLG-CARDSTATUS-NOT-OK
    } else if (ws.wsMiscStorage.flgCardstatusNotOk()
        // COB(1225): WHEN FLG-CARDSTATUS-BLANK
        || ws.wsMiscStorage.flgCardstatusBlank()) {
      // COB(1226): MOVE -1              TO CRDSTCDL OF  CCRDUPAI
      ws.cocrdup.ccrdupai.crdstcdl.setValue(-1);
      // COB(1227): WHEN FLG-CARDEXPMON-NOT-OK
    } else if (ws.wsMiscStorage.flgCardexpmonNotOk()
        // COB(1228): WHEN FLG-CARDEXPMON-BLANK
        || ws.wsMiscStorage.flgCardexpmonBlank()) {
      // COB(1229): MOVE -1              TO EXPMONL  OF  CCRDUPAI
      ws.cocrdup.ccrdupai.expmonl.setValue(-1);
      // COB(1230): WHEN FLG-CARDEXPYEAR-NOT-OK
    } else if (ws.wsMiscStorage.flgCardexpyearNotOk()
        // COB(1231): WHEN FLG-CARDEXPYEAR-BLANK
        || ws.wsMiscStorage.flgCardexpyearBlank()) {
      // COB(1232): MOVE -1              TO EXPYEARL OF  CCRDUPAI
      ws.cocrdup.ccrdupai.expyearl.setValue(-1);
      // COB(1233): WHEN OTHER
    } else {
      // COB(1234): MOVE -1              TO ACCTSIDL OF CCRDUPAI
      ws.cocrdup.ccrdupai.acctsidl.setValue(-1);
      // COB(1235): END-EVALUATE
    }
    // COB(1238): IF CDEMO-LAST-MAPSET   EQUAL LIT-CCLISTMAPSET
    //     SETUP COLOR
    if (ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.equals(
        ws.wsLiterals.litCclistmapset)) {
      // COB(1239): MOVE DFHDFCOL            TO ACCTSIDC OF CCRDUPAO
      ws.cocrdup.ccrdupao.acctsidc.setValue(Dfhbmsca.DFHDFCOL.getValue());
      // COB(1240): MOVE DFHDFCOL            TO CARDSIDC OF CCRDUPAO
      ws.cocrdup.ccrdupao.cardsidc.setValue(Dfhbmsca.DFHDFCOL.getValue());
      // COB(1241): END-IF
    }
    // COB(1243): IF FLG-ACCTFILTER-NOT-OK
    if (ws.wsMiscStorage.flgAcctfilterNotOk()) {
      // COB(1244): MOVE DFHRED              TO ACCTSIDC OF CCRDUPAO
      ws.cocrdup.ccrdupao.acctsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(1245): END-IF
    }
    // COB(1247): IF  FLG-ACCTFILTER-BLANK
    // COB(1247): AND CDEMO-PGM-REENTER
    if (ws.wsMiscStorage.flgAcctfilterBlank()
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(1249): MOVE '*'                TO ACCTSIDO OF CCRDUPAO
      ws.cocrdup.ccrdupao.acctsido.setString("*");
      // COB(1250): MOVE DFHRED             TO ACCTSIDC OF CCRDUPAO
      ws.cocrdup.ccrdupao.acctsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(1251): END-IF
    }
    // COB(1253): IF FLG-CARDFILTER-NOT-OK
    if (ws.wsMiscStorage.flgCardfilterNotOk()) {
      // COB(1254): MOVE DFHRED              TO CARDSIDC OF CCRDUPAO
      ws.cocrdup.ccrdupao.cardsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(1255): END-IF
    }
    // COB(1257): IF  FLG-CARDFILTER-BLANK
    // COB(1257): AND CDEMO-PGM-REENTER
    if (ws.wsMiscStorage.flgCardfilterBlank()
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(1259): MOVE '*'                TO CARDSIDO OF CCRDUPAO
      ws.cocrdup.ccrdupao.cardsido.setString("*");
      // COB(1260): MOVE DFHRED             TO CARDSIDC OF CCRDUPAO
      ws.cocrdup.ccrdupao.cardsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(1261): END-IF
    }
    // COB(1263): IF FLG-CARDNAME-NOT-OK
    // COB(1263): AND CCUP-CHANGES-NOT-OK
    if (ws.wsMiscStorage.flgCardnameNotOk()
        && ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesNotOk()) {
      // COB(1265): MOVE DFHRED              TO CRDNAMEC OF CCRDUPAO
      ws.cocrdup.ccrdupao.crdnamec.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(1266): END-IF
    }
    // COB(1268): IF  FLG-CARDNAME-BLANK
    // COB(1268): AND CCUP-CHANGES-NOT-OK
    if (ws.wsMiscStorage.flgCardnameBlank()
        && ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesNotOk()) {
      // COB(1270): MOVE '*'                TO CRDNAMEO OF CCRDUPAO
      ws.cocrdup.ccrdupao.crdnameo.setString("*");
      // COB(1271): MOVE DFHRED             TO CRDNAMEC OF CCRDUPAO
      ws.cocrdup.ccrdupao.crdnamec.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(1272): END-IF
    }
    // COB(1274): IF FLG-CARDSTATUS-NOT-OK
    // COB(1274): AND CCUP-CHANGES-NOT-OK
    if (ws.wsMiscStorage.flgCardstatusNotOk()
        && ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesNotOk()) {
      // COB(1276): MOVE DFHRED              TO CRDSTCDC OF CCRDUPAO
      ws.cocrdup.ccrdupao.crdstcdc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(1277): END-IF
    }
    // COB(1279): IF  FLG-CARDSTATUS-BLANK
    // COB(1279): AND CCUP-CHANGES-NOT-OK
    if (ws.wsMiscStorage.flgCardstatusBlank()
        && ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesNotOk()) {
      // COB(1281): MOVE '*'                TO CRDSTCDO OF CCRDUPAO
      ws.cocrdup.ccrdupao.crdstcdo.setString("*");
      // COB(1282): MOVE DFHRED             TO CRDSTCDC OF CCRDUPAO
      ws.cocrdup.ccrdupao.crdstcdc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(1283): END-IF
    }
    // COB(1285): MOVE DFHBMDAR               TO EXPDAYC  OF CCRDUPAO
    ws.cocrdup.ccrdupao.expdayc.setValue(Dfhbmsca.DFHBMDAR.getValue());
    // COB(1287): IF FLG-CARDEXPMON-NOT-OK
    // COB(1287): AND CCUP-CHANGES-NOT-OK
    if (ws.wsMiscStorage.flgCardexpmonNotOk()
        && ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesNotOk()) {
      // COB(1289): MOVE DFHRED              TO EXPMONC  OF CCRDUPAO
      ws.cocrdup.ccrdupao.expmonc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(1290): END-IF
    }
    // COB(1292): IF  FLG-CARDEXPMON-BLANK
    // COB(1292): AND CCUP-CHANGES-NOT-OK
    if (ws.wsMiscStorage.flgCardexpmonBlank()
        && ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesNotOk()) {
      // COB(1294): MOVE '*'                TO EXPMONO  OF CCRDUPAO
      ws.cocrdup.ccrdupao.expmono.setString("*");
      // COB(1295): MOVE DFHRED             TO EXPMONC  OF CCRDUPAO
      ws.cocrdup.ccrdupao.expmonc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(1296): END-IF
    }
    // COB(1298): IF  FLG-CARDEXPYEAR-NOT-OK
    // COB(1298): AND CCUP-CHANGES-NOT-OK
    if (ws.wsMiscStorage.flgCardexpyearNotOk()
        && ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesNotOk()) {
      // COB(1300): MOVE DFHRED              TO EXPYEARC OF CCRDUPAO
      ws.cocrdup.ccrdupao.expyearc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(1301): END-IF
    }
    // COB(1303): IF  FLG-CARDEXPYEAR-BLANK
    // COB(1303): AND CCUP-CHANGES-NOT-OK
    if (ws.wsMiscStorage.flgCardexpyearBlank()
        && ws.wsThisProgcommarea.cardUpdateScreenData.ccupChangesNotOk()) {
      // COB(1305): MOVE '*'                TO EXPYEARO OF CCRDUPAO
      ws.cocrdup.ccrdupao.expyearo.setString("*");
      // COB(1306): MOVE DFHRED             TO EXPYEARC OF CCRDUPAO
      ws.cocrdup.ccrdupao.expyearc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(1307): END-IF
    }
    // COB(1309): IF  WS-NO-INFO-MESSAGE
    if (ws.wsMiscStorage.wsNoInfoMessage()) {
      // COB(1310): MOVE DFHBMDAR           TO INFOMSGA OF CCRDUPAI
      ws.cocrdup.ccrdupai.filler99.infomsga.setValue(Dfhbmsca.DFHBMDAR.getValue());
      // COB(1311): ELSE
    } else {
      // COB(1312): MOVE DFHBMBRY           TO INFOMSGA OF CCRDUPAI
      ws.cocrdup.ccrdupai.filler99.infomsga.setValue(Dfhbmsca.DFHBMBRY.getValue());
      // COB(1313): END-IF
    }
    // COB(1315): IF PROMPT-FOR-CONFIRMATION
    if (ws.wsMiscStorage.promptForConfirmation()) {
      // COB(1316): MOVE DFHBMBRY            TO FKEYSCA  OF CCRDUPAI
      ws.cocrdup.ccrdupai.filler117.fkeysca.setValue(Dfhbmsca.DFHBMBRY.getValue());
      // COB(1317): END-IF
    }
  }

  protected void _3400SendScreen() {
    // COB(1326): MOVE LIT-THISMAPSET         TO CCARD-NEXT-MAPSET
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsLiterals.litThismapset);
    // COB(1327): MOVE LIT-THISMAP            TO CCARD-NEXT-MAP
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsLiterals.litThismap);
    // COB(1329): EXEC CICS SEND MAP(CCARD-NEXT-MAP)
    // COB(1329):                MAPSET(CCARD-NEXT-MAPSET)
    // COB(1329):                FROM(CCRDUPAO)
    // COB(1329):                CURSOR
    // COB(1329):                ERASE
    // COB(1329):                FREEKB
    // COB(1329):                RESP(WS-RESP-CD)
    // COB(1329): END-EXEC
    supernaut
        .sendMap(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.getValue()) //
        .mapset(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.getValue()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .from(ws.cocrdup.ccrdupao) //
        .freekb() //
        .erase() //
        .cursor() //
        .exec();
  }

  protected void _9000ReadData() {
    // COB(1345): INITIALIZE CCUP-OLD-DETAILS
    ws.wsThisProgcommarea.ccupOldDetails.initialize();
    // COB(1346): MOVE CC-ACCT-ID              TO CCUP-OLD-ACCTID
    ws.wsThisProgcommarea.ccupOldDetails.ccupOldAcctid.setValue(
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
    // COB(1347): MOVE CC-CARD-NUM             TO CCUP-OLD-CARDID
    ws.wsThisProgcommarea.ccupOldDetails.ccupOldCardid.setValue(
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum);
    // COB(1349): PERFORM 9100-GETCARD-BYACCTCARD
    // COB(1349):    THRU 9100-GETCARD-BYACCTCARD-EXIT
    _9100GetcardByacctcard();
    // COB(1352): IF FOUND-CARDS-FOR-ACCOUNT
    if (ws.wsMiscStorage.foundCardsForAccount()) {
      // COB(1354): MOVE CARD-CVV-CD          TO CCUP-OLD-CVV-CD
      ws.wsThisProgcommarea.ccupOldDetails.ccupOldCvvCd.setValue(ws.cvact02y.cardRecord.cardCvvCd);
      // COB(1356): INSPECT CARD-EMBOSSED-NAME
      // COB(1356): CONVERTING LIT-LOWER
      // COB(1356):         TO LIT-UPPER
      ws.cvact02y.cardRecord.cardEmbossedName.inspectAndConvertCharacters(
          ws.wsLiterals.litLower.getValue(), ws.wsLiterals.litUpper.getValue());
      // COB(1360): MOVE CARD-EMBOSSED-NAME   TO CCUP-OLD-CRDNAME
      ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldCrdname.setValue(
          ws.cvact02y.cardRecord.cardEmbossedName);
      // COB(1361): MOVE CARD-EXPIRAION-DATE(1:4)
      // COB(1361):                           TO CCUP-OLD-EXPYEAR
      ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldExpiraionDate.ccupOldExpyear
          .setValue(ws.cvact02y.cardRecord.cardExpiraionDate, 0, 4);
      // COB(1363): MOVE CARD-EXPIRAION-DATE(6:2)
      // COB(1363):                           TO CCUP-OLD-EXPMON
      ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldExpiraionDate.ccupOldExpmon
          .setValue(ws.cvact02y.cardRecord.cardExpiraionDate, 5, 0, 2);
      // COB(1365): MOVE CARD-EXPIRAION-DATE(9:2)
      // COB(1365):                           TO CCUP-OLD-EXPDAY
      ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldExpiraionDate.ccupOldExpday
          .setValue(ws.cvact02y.cardRecord.cardExpiraionDate, 8, 0, 2);
      // COB(1367): MOVE CARD-ACTIVE-STATUS   TO CCUP-OLD-CRDSTCD
      ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldCrdstcd.setValue(
          ws.cvact02y.cardRecord.cardActiveStatus);
      // COB(1369): END-IF
    }
  }

  protected void _9100GetcardByacctcard() {
    // COB(1380): MOVE CC-CARD-NUM             TO WS-CARD-RID-CARDNUM
    //     Read the Card file
    //
    //     MOVE CC-ACCT-ID-N            TO WS-CARD-RID-ACCT-ID
    ws.wsMiscStorage.wsCardRid.wsCardRidCardnum.setValue(
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum);
    // COB(1382): EXEC CICS READ
    // COB(1382):      FILE      (LIT-CARDFILENAME)
    // COB(1382):      RIDFLD    (WS-CARD-RID-CARDNUM)
    // COB(1382):      KEYLENGTH (LENGTH OF WS-CARD-RID-CARDNUM)
    // COB(1382):      INTO      (CARD-RECORD)
    // COB(1382):      LENGTH    (LENGTH OF CARD-RECORD)
    // COB(1382):      RESP      (WS-RESP-CD)
    // COB(1382):      RESP2     (WS-REAS-CD)
    // COB(1382): END-EXEC
    supernaut
        .read(ws.wsLiterals.litCardfilename.getValue()) //
        .length(ws.cvact02y.cardRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvact02y.cardRecord) //
        .ridfld(ws.wsMiscStorage.wsCardRid.wsCardRidCardnum) //
        .keylength(ws.wsMiscStorage.wsCardRid.wsCardRidCardnum.length()) //
        .exec();
    // COB(1392): EVALUATE WS-RESP-CD
    switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
        // COB(1393): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(1394): SET FOUND-CARDS-FOR-ACCOUNT TO TRUE
        ws.wsMiscStorage.setFoundCardsForAccount(true);
        // COB(1395): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(1396): SET INPUT-ERROR                    TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(1397): SET FLG-ACCTFILTER-NOT-OK          TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(1398): SET FLG-CARDFILTER-NOT-OK          TO TRUE
        ws.wsMiscStorage.setFlgCardfilterNotOk(true);
        // COB(1399): IF  WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(1400): SET DID-NOT-FIND-ACCTCARD-COMBO TO TRUE
          ws.wsMiscStorage.setDidNotFindAcctcardCombo(true);
          // COB(1401): END-IF
        }
        // COB(1402): WHEN OTHER
        break;
      default:
        // COB(1403): SET INPUT-ERROR                    TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(1404): IF  WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(1405): SET FLG-ACCTFILTER-NOT-OK      TO TRUE
          ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
          // COB(1406): END-IF
        }
        // COB(1407): MOVE 'READ'                        TO ERROR-OPNAME
        ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
        // COB(1408): MOVE LIT-CARDFILENAME              TO ERROR-FILE
        ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(ws.wsLiterals.litCardfilename);
        // COB(1409): MOVE WS-RESP-CD                    TO ERROR-RESP
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(1410): MOVE WS-REAS-CD                    TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(1411): MOVE WS-FILE-ERROR-MESSAGE         TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
        // COB(1412): END-EVALUATE
    }
  }

  protected Flow _9200WriteProcessing() {
    // COB(1425): MOVE CC-CARD-NUM             TO WS-CARD-RID-CARDNUM
    //     Read the Card file
    //
    //     MOVE CC-ACCT-ID-N            TO WS-CARD-RID-ACCT-ID
    ws.wsMiscStorage.wsCardRid.wsCardRidCardnum.setValue(
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum);
    // COB(1427): EXEC CICS READ
    // COB(1427):      FILE      (LIT-CARDFILENAME)
    // COB(1427):      UPDATE
    // COB(1427):      RIDFLD    (WS-CARD-RID-CARDNUM)
    // COB(1427):      KEYLENGTH (LENGTH OF WS-CARD-RID-CARDNUM)
    // COB(1427):      INTO      (CARD-RECORD)
    // COB(1427):      LENGTH    (LENGTH OF CARD-RECORD)
    // COB(1427):      RESP      (WS-RESP-CD)
    // COB(1427):      RESP2     (WS-REAS-CD)
    // COB(1427): END-EXEC
    supernaut
        .read(ws.wsLiterals.litCardfilename.getValue()) //
        .update() //
        .length(ws.cvact02y.cardRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvact02y.cardRecord) //
        .ridfld(ws.wsMiscStorage.wsCardRid.wsCardRidCardnum) //
        .keylength(ws.wsMiscStorage.wsCardRid.wsCardRidCardnum.length()) //
        .exec();
    // COB(1441): IF WS-RESP-CD EQUAL TO DFHRESP(NORMAL)
    // ****************************************************************
    //     Could we lock the record ?
    // ****************************************************************
    if (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.equals(Dfhresp.NORMAL)) {
      // COB(1442): CONTINUE
      // do nothing
      // COB(1443): ELSE
    } else {
      // COB(1444): SET INPUT-ERROR                    TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1445): IF  WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1446): SET COULD-NOT-LOCK-FOR-UPDATE  TO TRUE
        ws.wsMiscStorage.setCouldNotLockForUpdate(true);
        // COB(1447): END-IF
      }
      // COB(1448): GO TO 9200-WRITE-PROCESSING-EXIT
      return Flow._9200WriteProcessingExit;
      // COB(1449): END-IF
    }
    // COB(1453): PERFORM 9300-CHECK-CHANGE-IN-REC
    // COB(1453):    THRU 9300-CHECK-CHANGE-IN-REC-EXIT
    // ****************************************************************
    //     Did someone change the record while we were out ?
    // ****************************************************************
    rcNext = Flow._9300CheckChangeInRec;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case _9300CheckChangeInRec:
          rcNext = _9300CheckChangeInRec();
          break;
        case _9200WriteProcessingExit:
          _9200WriteProcessingExit();
          rcNext = Flow._9300CheckChangeInRec;
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    // COB(1455): IF DATA-WAS-CHANGED-BEFORE-UPDATE
    if (ws.wsMiscStorage.dataWasChangedBeforeUpdate()) {
      // COB(1456): GO TO 9200-WRITE-PROCESSING-EXIT
      return Flow._9200WriteProcessingExit;
      // COB(1457): END-IF
    }
    // COB(1461): INITIALIZE CARD-UPDATE-RECORD
    // ****************************************************************
    //  Prepare the update
    // ****************************************************************
    ws.wsThisProgcommarea.cardUpdateRecord.initialize();
    // COB(1462): MOVE CCUP-NEW-CARDID             TO CARD-UPDATE-NUM
    ws.wsThisProgcommarea.cardUpdateRecord.cardUpdateNum.setValue(
        ws.wsThisProgcommarea.ccupNewDetails.ccupNewCardid);
    // COB(1463): MOVE CC-ACCT-ID-N                TO CARD-UPDATE-ACCT-ID
    ws.wsThisProgcommarea.cardUpdateRecord.cardUpdateAcctId.setValue(
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctIdN);
    // COB(1464): MOVE CCUP-NEW-CVV-CD             TO CARD-CVV-CD-X
    ws.wsMiscStorage.cicsOutputEditVars.cardCvvCdX.setValue(
        ws.wsThisProgcommarea.ccupNewDetails.ccupNewCvvCd);
    // COB(1465): MOVE CARD-CVV-CD-N               TO CARD-UPDATE-CVV-CD
    ws.wsThisProgcommarea.cardUpdateRecord.cardUpdateCvvCd.setValue(
        ws.wsMiscStorage.cicsOutputEditVars.cardCvvCdN);
    // COB(1466): MOVE CCUP-NEW-CRDNAME            TO CARD-UPDATE-EMBOSSED-NAME
    ws.wsThisProgcommarea.cardUpdateRecord.cardUpdateEmbossedName.setValue(
        ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdname);
    // COB(1467): STRING  CCUP-NEW-EXPYEAR
    // COB(1467):         '-'
    // COB(1467):         CCUP-NEW-EXPMON
    // COB(1467):         '-'
    // COB(1467):         CCUP-NEW-EXPDAY
    // COB(1467):         DELIMITED BY SIZE
    // COB(1467):    INTO CARD-UPDATE-EXPIRAION-DATE
    // COB(1467): END-STRING
    ws.wsThisProgcommarea.cardUpdateRecord.cardUpdateExpiraionDate.concat(
        ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpyear,
        "-",
        ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpmon,
        "-",
        ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewExpiraionDate.ccupNewExpday);
    // COB(1474): END-STRING
    // COB(1475): MOVE CCUP-NEW-CRDSTCD            TO CARD-UPDATE-ACTIVE-STATUS
    ws.wsThisProgcommarea.cardUpdateRecord.cardUpdateActiveStatus.setValue(
        ws.wsThisProgcommarea.ccupNewDetails.ccupNewCarddata.ccupNewCrdstcd);
    // COB(1477): EXEC CICS
    // COB(1477):      REWRITE FILE(LIT-CARDFILENAME)
    // COB(1477):              FROM(CARD-UPDATE-RECORD)
    // COB(1477):              LENGTH(LENGTH OF CARD-UPDATE-RECORD)
    // COB(1477):              RESP      (WS-RESP-CD)
    // COB(1477):              RESP2     (WS-REAS-CD)
    // COB(1477): END-EXEC.
    supernaut
        .rewrite(ws.wsLiterals.litCardfilename.getValue()) //
        .length(ws.wsThisProgcommarea.cardUpdateRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .from(ws.wsThisProgcommarea.cardUpdateRecord) //
        .exec();
    // COB(1488): IF WS-RESP-CD EQUAL TO DFHRESP(NORMAL)
    // ****************************************************************
    //  Did the update succeed ?  *
    // ****************************************************************
    if (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.equals(Dfhresp.NORMAL)) {
      // COB(1489): CONTINUE
      // do nothing
      // COB(1490): ELSE
    } else {
      // COB(1491): SET LOCKED-BUT-UPDATE-FAILED    TO TRUE
      ws.wsMiscStorage.setLockedButUpdateFailed(true);
      // COB(1492): END-IF
    }
    return Flow.Exit;
  }

  protected void _9200WriteProcessingExit() {
    // COB(1495): EXIT
    return;
  }

  protected Flow _9300CheckChangeInRec() {
    // COB(1499): INSPECT CARD-EMBOSSED-NAME
    // COB(1499): CONVERTING LIT-LOWER
    // COB(1499):         TO LIT-UPPER
    ws.cvact02y.cardRecord.cardEmbossedName.inspectAndConvertCharacters(
        ws.wsLiterals.litLower.getValue(), ws.wsLiterals.litUpper.getValue());
    // COB(1503): IF  CARD-CVV-CD              EQUAL  TO CCUP-OLD-CVV-CD
    // COB(1503): AND CARD-EMBOSSED-NAME       EQUAL  TO CCUP-OLD-CRDNAME
    // COB(1503): AND CARD-EXPIRAION-DATE(1:4) EQUAL  TO CCUP-OLD-EXPYEAR
    // COB(1503): AND CARD-EXPIRAION-DATE(6:2) EQUAL  TO CCUP-OLD-EXPMON
    // COB(1503): AND CARD-EXPIRAION-DATE(9:2) EQUAL  TO CCUP-OLD-EXPDAY
    // COB(1503): AND CARD-ACTIVE-STATUS       EQUAL  TO CCUP-OLD-CRDSTCD
    if (ws.cvact02y.cardRecord.cardCvvCd.equals(ws.wsThisProgcommarea.ccupOldDetails.ccupOldCvvCd)
        && ws.cvact02y.cardRecord.cardEmbossedName.equals(
            ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldCrdname)
        && ws.cvact02y
            .cardRecord
            .cardExpiraionDate
            .getString(0, 4)
            .equals(
                ws.wsThisProgcommarea
                    .ccupOldDetails
                    .ccupOldCarddata
                    .ccupOldExpiraionDate
                    .ccupOldExpyear)
        && ws.cvact02y
            .cardRecord
            .cardExpiraionDate
            .getString(5, 2)
            .equals(
                ws.wsThisProgcommarea
                    .ccupOldDetails
                    .ccupOldCarddata
                    .ccupOldExpiraionDate
                    .ccupOldExpmon)
        && ws.cvact02y
            .cardRecord
            .cardExpiraionDate
            .getString(8, 2)
            .equals(
                ws.wsThisProgcommarea
                    .ccupOldDetails
                    .ccupOldCarddata
                    .ccupOldExpiraionDate
                    .ccupOldExpday)
        && ws.cvact02y.cardRecord.cardActiveStatus.equals(
            ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldCrdstcd)) {
      // COB(1509): CONTINUE
      // do nothing
      // COB(1510): ELSE
    } else {
      // COB(1511): SET DATA-WAS-CHANGED-BEFORE-UPDATE TO TRUE
      ws.wsMiscStorage.setDataWasChangedBeforeUpdate(true);
      // COB(1512): MOVE CARD-CVV-CD                 TO CCUP-OLD-CVV-CD
      ws.wsThisProgcommarea.ccupOldDetails.ccupOldCvvCd.setValue(ws.cvact02y.cardRecord.cardCvvCd);
      // COB(1513): MOVE CARD-EMBOSSED-NAME          TO CCUP-OLD-CRDNAME
      ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldCrdname.setValue(
          ws.cvact02y.cardRecord.cardEmbossedName);
      // COB(1514): MOVE CARD-EXPIRAION-DATE(1:4)    TO CCUP-OLD-EXPYEAR
      ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldExpiraionDate.ccupOldExpyear
          .setValue(ws.cvact02y.cardRecord.cardExpiraionDate, 0, 4);
      // COB(1515): MOVE CARD-EXPIRAION-DATE(6:2)    TO CCUP-OLD-EXPMON
      ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldExpiraionDate.ccupOldExpmon
          .setValue(ws.cvact02y.cardRecord.cardExpiraionDate, 5, 0, 2);
      // COB(1516): MOVE CARD-EXPIRAION-DATE(9:2)    TO CCUP-OLD-EXPDAY
      ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldExpiraionDate.ccupOldExpday
          .setValue(ws.cvact02y.cardRecord.cardExpiraionDate, 8, 0, 2);
      // COB(1517): MOVE CARD-ACTIVE-STATUS          TO CCUP-OLD-CRDSTCD
      ws.wsThisProgcommarea.ccupOldDetails.ccupOldCarddata.ccupOldCrdstcd.setValue(
          ws.cvact02y.cardRecord.cardActiveStatus);
      // COB(1518): GO TO 9200-WRITE-PROCESSING-EXIT
      return Flow._9200WriteProcessingExit;
      // COB(1519): END-IF EXIT
    }
    // COB(1519): END-IF EXIT
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

  protected void abendRoutine() {
    // COB(1533): IF ABEND-MSG EQUAL LOW-VALUES
    if (ws.csmsg02y.abendData.abendMsg.isLowValues()) {
      // COB(1534): MOVE 'UNEXPECTED ABEND OCCURRED.' TO ABEND-MSG
      ws.csmsg02y.abendData.abendMsg.setString("UNEXPECTED ABEND OCCURRED.");
      // COB(1535): END-IF
    }
    // COB(1537): MOVE LIT-THISPGM       TO ABEND-CULPRIT
    ws.csmsg02y.abendData.abendCulprit.setValue(ws.wsLiterals.litThispgm);
    // COB(1539): EXEC CICS SEND
    // COB(1539):                  FROM (ABEND-DATA)
    // COB(1539):                  LENGTH(LENGTH OF ABEND-DATA)
    // COB(1539):                  NOHANDLE
    // COB(1539):                  ERASE
    // COB(1539): END-EXEC
    supernaut
        .send() //
        .length(ws.csmsg02y.abendData.length()) //
        .nohandle() //
        .from(ws.csmsg02y.abendData) //
        .erase() //
        .exec();
    // COB(1546): EXEC CICS HANDLE ABEND
    // COB(1546):      CANCEL
    // COB(1546): END-EXEC
    supernaut
        .handleAbend() //
        .cancel() //
        .exec();
    // COB(1550): EXEC CICS ABEND
    // COB(1550):      ABCODE('9999')
    // COB(1550): END-EXEC
    supernaut
        .abend() //
        .abcode("9999") //
        .exec();
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
