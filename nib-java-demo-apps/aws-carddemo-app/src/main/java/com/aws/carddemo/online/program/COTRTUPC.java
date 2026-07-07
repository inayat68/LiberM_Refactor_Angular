package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cotrtupc.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.esql.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class COTRTUPC extends AbstractOnlineProgram {

  protected enum Flow {
    Exit,
    // 0000-MAIN
    _0000Main,
    // COMMON-RETURN
    commonReturn
  }
  public abstract char testConvertToNumeric();
  private Flow rcNext;

  private final Sqlca sqlca = new Sqlca(this);
  @ProgramStorage final CotrtupcWorking ws = new CotrtupcWorking();

  // Linkage
  public static class CotrtupcLinkage extends NGroup {
    // COB: 034000 01  DFHCOMMAREA.
    public Dfhcommarea dfhcommarea = new Dfhcommarea();

    public static class Dfhcommarea extends NGroup {

      // COB: 034100   05  FILLER                                PIC X(1)
      // COB: 034200       OCCURS 1 TO 32767 TIMES DEPENDING ON EIBCALEN.
      public NChar[] filler341 = AbstractNField.occurs(32767, new NChar(1));

      public NChar filler341AtIndex(int index) {
        return getOccursInstance(filler341, index);
      }
    }
  }

  final CotrtupcLinkage params = new CotrtupcLinkage();

  public COTRTUPC(Context supernaut) {
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
    // COB(348): 034800     EXEC CICS HANDLE ABEND
    // COB(348): 034900               LABEL(ABEND-ROUTINE)
    // COB(348): 035000     END-EXEC
    // 34600                                                                  03460000
    // 34700                                                                  03470000
    supernaut
        .handleAbend() //
        .label(HandleLabel.abendRoutine.getValue()) //
        .exec();
    // COB(352): 035200     INITIALIZE CC-WORK-AREA
    // COB(352): 035300                WS-MISC-STORAGE
    // COB(352): 035400                WS-COMMAREA
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.initialize();
    ws.wsMiscStorage.initialize();
    ws.wsCommarea.initialize();
    // COB(358): 035800     MOVE LIT-THISTRANID       TO WS-TRANID
    // ****************************************************************
    //  Store our context
    // ****************************************************************
    ws.wsMiscStorage.wsCicsProcessngVars.wsTranid.setValue(ws.wsLiterals.litThistranid);
    // COB(362): 036200     SET WS-RETURN-MSG-OFF  TO TRUE
    // ****************************************************************
    //  Ensure error message is cleared                               *
    // ****************************************************************
    ws.wsMiscStorage.setWsReturnMsgOff(true);
    // COB(366): 036600     IF EIBCALEN IS EQUAL TO 0
    // COB(366): 036700         OR (CDEMO-FROM-PROGRAM = LIT-ADMINPGM
    // COB(366): 036800         AND NOT CDEMO-PGM-REENTER)
    // COB(366): 036900         OR (CDEMO-FROM-PROGRAM = LIT-LISTTPGM
    // COB(366): 037000         AND NOT CDEMO-PGM-REENTER)
    // ****************************************************************
    //  Store passed data if  any                *
    // ****************************************************************
    if (dfheiblk.getEibcalen() == 0
        || (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsLiterals.litAdminpgm)
            && !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter())
        || (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsLiterals.litListtpgm)
            && !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter())) {
      // COB(371): 037100        INITIALIZE CARDDEMO-COMMAREA
      // COB(371): 037200                   WS-THIS-PROGCOMMAREA
      ws.cocom01y.carddemoCommarea.initialize();
      ws.wsThisProgcommarea.initialize();
      // COB(373): 037300        SET CDEMO-PGM-ENTER TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(374): 037400        SET TTUP-DETAILS-NOT-FETCHED TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupDetailsNotFetched(true);
      // COB(375): 037500     ELSE
    } else {
      // COB(376): 037600        MOVE DFHCOMMAREA (1:LENGTH OF CARDDEMO-COMMAREA)  TO
      // COB(376): 037700                          CARDDEMO-COMMAREA
      ws.cocom01y.carddemoCommarea.setValue(
          params.dfhcommarea, 0, ws.cocom01y.carddemoCommarea.length());
      // COB(378): 037800        MOVE DFHCOMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
      // COB(378): 037900                         LENGTH OF WS-THIS-PROGCOMMAREA ) TO
      // COB(378): 038000                          WS-THIS-PROGCOMMAREA
      ws.wsThisProgcommarea.setValue(
          params.dfhcommarea,
          ws.cocom01y.carddemoCommarea.length(),
          0,
          ws.wsThisProgcommarea.length());
      // COB(381): 038100     END-IF
    }
    // COB(386): 038600     PERFORM YYYY-STORE-PFKEY
    // COB(386): 038700        THRU YYYY-STORE-PFKEY-EXIT
    // ****************************************************************
    //  Store the Mapped PF Key
    //  Remap PFkeys as needed.
    // ****************************************************************
    yyyyStorePfkey();
    // COB(398): 039800     SET PFK-INVALID TO TRUE
    // ****************************************************************
    //  Check the AID to see if its valid at this point               *
    //  Change the key to some valid value if possible
    //  F3 - Exit
    //  Enter show screen again
    //  F4 - Delete
    //  F5 - Save
    //  F12 - Cancel
    // ****************************************************************
    ws.wsMiscStorage.setPfkInvalid(true);
    // COB(400): 040000     PERFORM 0001-CHECK-PFKEYS
    // COB(400): 040100        THRU 0001-CHECK-PFKEYS-EXIT
    _0001CheckPfkeys();
    // COB(405): 040500     EVALUATE TRUE
    // ****************************************************************
    //        Simulate initial entry if the following flags are set
    // ****************************************************************
    // COB(406): 040600        WHEN CCARD-AID-PFK12
    // COB(406): 040700         AND (TTUP-SHOW-DETAILS
    // COB(406): 040800          OR  TTUP-CREATE-NEW-RECORD
    // COB(406): 040900          OR  TTUP-DETAILS-NOT-FOUND)
    if ((ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk12()
            && (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupShowDetails()
                || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupCreateNewRecord()
                || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFound()))
        // COB(410): 041000        WHEN TTUP-CHANGES-OKAYED-AND-DONE
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkayedAndDone()
        // COB(411): 041100        WHEN TTUP-CHANGES-FAILED
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesFailed()
        // COB(412): 041200        WHEN TTUP-CHANGES-BACKED-OUT
        // COB(412): 041300         AND  (TTUP-OLD-DETAILS EQUAL LOW-VALUES
        // COB(412): 041400          OR   TTUP-OLD-DETAILS EQUAL SPACES)
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesBackedOut()
            && (ws.wsThisProgcommarea.ttupOldDetails.isLowValues()
                || ws.wsThisProgcommarea.ttupOldDetails.isSpaces())
        // COB(415): 041500        WHEN TTUP-DELETE-DONE
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDeleteDone()
        // COB(416): 041600        WHEN TTUP-DELETE-FAILED
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDeleteFailed()) {
      // COB(417): 041700             SET CDEMO-PGM-ENTER          TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(418): 041800             SET TTUP-DETAILS-NOT-FETCHED TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupDetailsNotFetched(true);
      // COB(419): 041900     END-EVALUATE
    }
    // COB(423): 042300     EVALUATE TRUE
    // ****************************************************************
    //  Decide what to do based on PF KEY PRESSED AND CONTEXT
    // ****************************************************************
    // COB(429): 042900        WHEN CCARD-AID-PFK03
    // *****************************************************************
    //        USER PRESSES PF03 TO EXIT
    //   OR   USER IS DONE WITH UPDATE
    //             XCTL TO CALLING PROGRAM OR MAIN MENU
    // *****************************************************************
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()) {
      // COB(431): 043100             IF CDEMO-FROM-TRANID    EQUAL LOW-VALUES
      // COB(431): 043200             OR CDEMO-FROM-TRANID    EQUAL SPACES
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isSpaces()) {
        // COB(433): 043300                MOVE LIT-ADMINTRANID   TO CDEMO-TO-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToTranid.setValue(
            ws.wsLiterals.litAdmintranid);
        // COB(434): 043400             ELSE
      } else {
        // COB(435): 043500                MOVE CDEMO-FROM-TRANID TO CDEMO-TO-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToTranid.setValue(
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid);
        // COB(436): 043600             END-IF
      }
      // COB(438): 043800             IF CDEMO-FROM-PROGRAM   EQUAL LOW-VALUES
      // COB(438): 043900             OR CDEMO-FROM-PROGRAM   EQUAL SPACES
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isSpaces()) {
        // COB(440): 044000                MOVE LIT-ADMINPGM     TO CDEMO-TO-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
            ws.wsLiterals.litAdminpgm);
        // COB(441): 044100             ELSE
      } else {
        // COB(442): 044200                MOVE CDEMO-FROM-PROGRAM TO CDEMO-TO-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram);
        // COB(443): 044300             END-IF
      }
      // COB(445): 044500             MOVE LIT-THISTRANID     TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
          ws.wsLiterals.litThistranid);
      // COB(446): 044600             MOVE LIT-THISPGM        TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
          ws.wsLiterals.litThispgm);
      // COB(448): 044800             SET  CDEMO-USRTYP-ADMIN TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypAdmin(true);
      // COB(449): 044900             SET  CDEMO-PGM-ENTER    TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(450): 045000             MOVE LIT-THISMAPSET     TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(
          ws.wsLiterals.litThismapset);
      // COB(451): 045100             MOVE LIT-THISMAP        TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsLiterals.litThismap);
      // COB(453): 045300             EXEC CICS
      // COB(453): 045400                  SYNCPOINT
      // COB(453): 045500             END-EXEC
      try {
        supernaut
            .syncpoint() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(457): 045700             EXEC CICS XCTL
      // COB(457): 045800                  PROGRAM (CDEMO-TO-PROGRAM)
      // COB(457): 045900                  COMMAREA(CARDDEMO-COMMAREA)
      // COB(457): 046000             END-EXEC
      try {
        supernaut
            .xctl(ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
            .commarea(ws.cocom01y.carddemoCommarea) //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(465): 046500        WHEN NOT CDEMO-PGM-REENTER
      // COB(465): 046600         AND CDEMO-FROM-PROGRAM   EQUAL LIT-ADMINPGM
      // *****************************************************************
      //        CLEAR SCREEN, CLEAR SAVED CONTEXT
      //        ASK USER FOR SEARCH KEYS
      // *****************************************************************
    } else if (!ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()
            && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsLiterals.litAdminpgm)
        // COB(467): 046700        WHEN NOT CDEMO-PGM-REENTER
        // COB(467): 046800         AND CDEMO-FROM-PROGRAM   EQUAL LIT-LISTTPGM
        || !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()
            && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsLiterals.litListtpgm)
        // COB(469): 046900        WHEN CDEMO-PGM-ENTER
        // COB(469): 047000         AND TTUP-DETAILS-NOT-FETCHED
        || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()
            && ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFetched()) {
      // COB(471): 047100             INITIALIZE WS-THIS-PROGCOMMAREA
      // COB(471): 047200                        WS-MISC-STORAGE
      // COB(471): 047300                        CDEMO-ACCT-ID
      ws.wsThisProgcommarea.initialize();
      ws.wsMiscStorage.initialize();
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.initialize();
      // COB(474): 047400             PERFORM 3000-SEND-MAP THRU
      // COB(474): 047500                     3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(476): 047600             SET CDEMO-PGM-REENTER        TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
      // COB(477): 047700             SET TTUP-DETAILS-NOT-FETCHED TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupDetailsNotFetched(true);
      // COB(478): 047800             GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(482): 048200        WHEN CCARD-AID-PFK04
      // COB(482): 048300         AND TTUP-CONFIRM-DELETE
      // *****************************************************************
      //        USER PRESSED F04 AFTER BEING ASKED TO VERIFY DELETE
      // *****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk04()
        && ws.wsThisProgcommarea.ttupUpdateScreenData.ttupConfirmDelete()) {
      // COB(484): 048400             SET TTUP-START-DELETE                TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupStartDelete(true);
      // COB(485): 048500             PERFORM 9800-DELETE-PROCESSING
      // COB(485): 048600                THRU 9800-DELETE-PROCESSING-EXIT
      _9800DeleteProcessing();
      // COB(487): 048700             PERFORM 3000-SEND-MAP THRU
      // COB(487): 048800                     3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(489): 048900             GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(493): 049300        WHEN CCARD-AID-PFK04
      // COB(493): 049400         AND TTUP-SHOW-DETAILS
      // *****************************************************************
      //        USER PRESSED F04.ASK FOR DELETE CONFIRMATION
      // *****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk04()
        && ws.wsThisProgcommarea.ttupUpdateScreenData.ttupShowDetails()) {
      // COB(495): 049500             SET TTUP-CONFIRM-DELETE              TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupConfirmDelete(true);
      // COB(496): 049600             PERFORM 3000-SEND-MAP THRU
      // COB(496): 049700                     3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(498): 049800             GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(503): 050300        WHEN CCARD-AID-PFK05
      // COB(503): 050400         AND TTUP-DETAILS-NOT-FOUND
      // *****************************************************************
      //        USER PRESSED F05. WHEN NO RECORD WAS FOUND.
      //        ASK TO CONFIRM NEW RECORD CREATION
      // *****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk05()
        && ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFound()) {
      // COB(505): 050500            SET TTUP-CREATE-NEW-RECORD TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupCreateNewRecord(true);
      // COB(506): 050600             PERFORM 3000-SEND-MAP THRU
      // COB(506): 050700                     3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(508): 050800             GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(514): 051400        WHEN CCARD-AID-PFK05
      // COB(514): 051500         AND TTUP-CHANGES-OK-NOT-CONFIRMED
      // *****************************************************************
      //        USER PRESSED F05 AND CONFIRMED THAT CHANGES CAN BE SAVED
      //        EDITS HAVE PASSED
      //        SO SAVE THE CHANGES
      // *****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk05()
        && ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkNotConfirmed()) {
      // COB(516): 051600           PERFORM 9600-WRITE-PROCESSING
      // COB(516): 051700              THRU 9600-WRITE-PROCESSING-EXIT
      _9600WriteProcessing();
      // COB(518): 051800             PERFORM 3000-SEND-MAP
      // COB(518): 051900                THRU 3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(520): 052000             GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(524): 052400         WHEN CCARD-AID-PFK12
      // COB(524): 052500         AND (TTUP-CHANGES-OK-NOT-CONFIRMED
      // COB(524): 052600          OR  TTUP-CONFIRM-DELETE
      // COB(524): 052700          OR  TTUP-SHOW-DETAILS)
      // *****************************************************************
      //        USER PRESSED F12. CANCEL THE ACTION
      // *****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk12()
        && (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkNotConfirmed()
            || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupConfirmDelete()
            || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupShowDetails())) {
      // COB(528): 052800             SET FOUND-TRANTYPE-IN-TABLE  TO TRUE
      ws.wsMiscStorage.wsTableReadFlags.setFoundTrantypeInTable(true);
      // COB(529): 052900             PERFORM 2000-DECIDE-ACTION
      // COB(529): 053000                THRU 2000-DECIDE-ACTION-EXIT
      _2000DecideAction();
      // COB(531): 053100             PERFORM 3000-SEND-MAP
      // COB(531): 053200                THRU 3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(533): 053300             GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(539): 053900        WHEN WS-INVALID-KEY-PRESSED
      // *****************************************************************
      //        CHECK THE USER INPUTS
      //        DECIDE WHAT TO DO
      //        PRESENT NEXT STEPS TO USER
      // *****************************************************************
    } else if (ws.wsMiscStorage.wsInvalidKeyPressed()) {
      // COB(540): 054000             PERFORM 3000-SEND-MAP
      // COB(540): 054100                THRU 3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(542): 054200             GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(548): 054800        WHEN OTHER
      // *****************************************************************
      //        CHECK THE USER INPUTS
      //        DECIDE WHAT TO DO
      //        PRESENT NEXT STEPS TO USER
      // *****************************************************************
    } else {
      // COB(549): 054900             PERFORM 1000-PROCESS-INPUTS
      // COB(549): 055000                THRU 1000-PROCESS-INPUTS-EXIT
      _1000ProcessInputs();
      // COB(551): 055100             PERFORM 2000-DECIDE-ACTION
      // COB(551): 055200                THRU 2000-DECIDE-ACTION-EXIT
      _2000DecideAction();
      // COB(553): 055300             PERFORM 3000-SEND-MAP
      // COB(553): 055400                THRU 3000-SEND-MAP-EXIT
      _3000SendMap();
      // COB(555): 055500             GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(556): 055600     END-EVALUATE
    }
    return Flow.Exit;
  }

  protected void commonReturn() {
    // COB(560): 056000     MOVE WS-RETURN-MSG     TO CCARD-ERROR-MSG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
    // COB(562): 056200     MOVE  CARDDEMO-COMMAREA    TO WS-COMMAREA
    ws.wsCommarea.setValue(ws.cocom01y.carddemoCommarea);
    // COB(563): 056300     MOVE  WS-THIS-PROGCOMMAREA TO
    // COB(563): 056400            WS-COMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
    // COB(563): 056500                         LENGTH OF WS-THIS-PROGCOMMAREA )
    ws.wsCommarea.setValue(
        ws.wsThisProgcommarea,
        0,
        ws.cocom01y.carddemoCommarea.length(),
        ws.wsThisProgcommarea.length());
    // COB(567): 056700     EXEC CICS RETURN
    // COB(567): 056800          TRANSID (LIT-THISTRANID)
    // COB(567): 056900          COMMAREA (WS-COMMAREA)
    // COB(567): 057000          LENGTH(LENGTH OF WS-COMMAREA)
    // COB(567): 057100     END-EXEC
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

  protected void _0001CheckPfkeys() {
    // COB(582): 058200     IF (CCARD-AID-PFK03)
    // COB(582): 058300     OR (CCARD-AID-ENTER AND NOT TTUP-CONFIRM-DELETE)
    // COB(582): 058400     OR (CCARD-AID-PFK04 AND (TTUP-SHOW-DETAILS
    // COB(582): 058500                        OR   TTUP-CONFIRM-DELETE )
    // COB(582): 058600        )
    // COB(582): 058700
    // COB(582): 058800     OR (CCARD-AID-PFK05 AND (
    // COB(582): 058900                             TTUP-CHANGES-OK-NOT-CONFIRMED
    // COB(582): 059000                        OR   TTUP-DETAILS-NOT-FOUND
    // COB(582): 059100                        OR   TTUP-DELETE-IN-PROGRESS
    // COB(582): 059200                             )
    // COB(582): 059300        )
    // COB(582): 059400     OR (CCARD-AID-PFK12 AND (
    // COB(582): 059500                             TTUP-CHANGES-OK-NOT-CONFIRMED
    // COB(582): 059600                        OR   TTUP-SHOW-DETAILS
    // COB(582): 059700                        OR   TTUP-DETAILS-NOT-FOUND
    // COB(582): 059800                        OR   TTUP-CONFIRM-DELETE
    // COB(582): 059900                        OR   TTUP-CREATE-NEW-RECORD
    // COB(582): 060000                             )
    // COB(582): 060100       )
    //     Should mirror logic in PFKey attribut para
    //     3391-PFKEY-ATTRS
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()
        || (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidEnter()
            && !ws.wsThisProgcommarea.ttupUpdateScreenData.ttupConfirmDelete())
        || (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk04()
            && (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupShowDetails()
                || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupConfirmDelete()))
        || (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk05()
            && (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkNotConfirmed()
                || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFound()
                || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDeleteInProgress()))
        || (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk12()
            && (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkNotConfirmed()
                || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupShowDetails()
                || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFound()
                || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupConfirmDelete()
                || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupCreateNewRecord()))) {
      // COB(602): 060200        SET PFK-VALID                  TO TRUE
      ws.wsMiscStorage.setPfkValid(true);
      // COB(603): 060300     ELSE
    } else {
      // COB(604): 060400        SET PFK-INVALID                TO TRUE
      ws.wsMiscStorage.setPfkInvalid(true);
      // COB(605): 060500        IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(606): 060600           SET WS-INVALID-KEY-PRESSED  TO TRUE
        ws.wsMiscStorage.setWsInvalidKeyPressed(true);
        // COB(607): 060700        END-IF
      }
      // COB(608): 060800     END-IF
    }
  }

  protected void _1000ProcessInputs() {
    // COB(626): 062600     PERFORM 1100-RECEIVE-MAP
    // COB(626): 062700        THRU 1100-RECEIVE-MAP-EXIT
    _1100ReceiveMap();
    // COB(628): 062800     PERFORM 1150-STORE-MAP-IN-NEW
    // COB(628): 062900        THRU 1150-STORE-MAP-IN-NEW-EXIT
    _1150StoreMapInNew();
    // COB(630): 063000     PERFORM 1200-EDIT-MAP-INPUTS
    // COB(630): 063100        THRU 1200-EDIT-MAP-INPUTS-EXIT
    _1200EditMapInputs();
    // COB(632): 063200     MOVE WS-RETURN-MSG  TO CCARD-ERROR-MSG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
    // COB(633): 063300     MOVE LIT-THISPGM    TO CCARD-NEXT-PROG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.setValue(ws.wsLiterals.litThispgm);
    // COB(634): 063400     MOVE LIT-THISMAPSET TO CCARD-NEXT-MAPSET
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsLiterals.litThismapset);
    // COB(635): 063500     MOVE LIT-THISMAP    TO CCARD-NEXT-MAP
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsLiterals.litThismap);
  }

  protected void _1100ReceiveMap() {
    // COB(642): 064200     EXEC CICS RECEIVE MAP(LIT-THISMAP)
    // COB(642): 064300               MAPSET(LIT-THISMAPSET)
    // COB(642): 064400               INTO(CTRTUPAI)
    // COB(642): 064500               RESP(WS-RESP-CD)
    // COB(642): 064600               RESP2(WS-REAS-CD)
    // COB(642): 064700     END-EXEC
    supernaut
        .receiveMap(ws.wsLiterals.litThismap.getValue()) //
        .mapset(ws.wsLiterals.litThismapset.getValue()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cotrtup.ctrtupai) //
        .exec();
  }

  protected void _1150StoreMapInNew() {
    // COB(654): 065400     IF  TTUP-DETAILS-NOT-FOUND
    // COB(654): 065500     AND NOT CCARD-AID-PFK05
    // COB(654): 065600     AND FUNCTION TRIM(TRTYPCDI OF CTRTUPAI)
    // COB(654): 065700           = TTUP-NEW-TTYP-TYPE
    if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFound()
        && !ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk05()
        && ws.cotrtup
            .ctrtupai
            .trtypcdi
            .trim()
            .equals(ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypType)) {
      // COB(658): 065800         GO TO 1150-STORE-MAP-IN-NEW-EXIT
      return;
      // COB(659): 065900     ELSE
    } else {
      // COB(660): 066000         CONTINUE
      // do nothing
      // COB(661): 066100     END-IF
    }
    // COB(663): 066300     INITIALIZE TTUP-NEW-DETAILS
    ws.wsThisProgcommarea.ttupNewDetails.initialize();
    // COB(667): 066700     IF  TRTYPCDI OF CTRTUPAI = '*'
    // COB(667): 066800     OR  TRTYPCDI OF CTRTUPAI = SPACES
    // *****************************************************************
    //     Transaction Type
    // *****************************************************************
    if (ws.cotrtup.ctrtupai.trtypcdi.equals("*") || ws.cotrtup.ctrtupai.trtypcdi.isSpaces()) {
      // COB(669): 066900         MOVE LOW-VALUES           TO TTUP-NEW-TTYP-TYPE
      ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypType.lowValues();
      // COB(670): 067000     ELSE
    } else {
      // COB(671): 067100         MOVE FUNCTION TRIM(TRTYPCDI OF CTRTUPAI)
      // COB(671): 067200                                   TO TTUP-NEW-TTYP-TYPE
      ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypType.setValue(
          ws.cotrtup.ctrtupai.trtypcdi.trim());
      // COB(673): 067300     END-IF
    }
    // COB(678): 067800     IF  TRTYDSCI OF CTRTUPAI = '*'
    // COB(678): 067900     OR  TRTYDSCI OF CTRTUPAI = SPACES
    // *****************************************************************
    //     Transaction Desc
    // *****************************************************************
    if (ws.cotrtup.ctrtupai.trtydsci.equals("*") || ws.cotrtup.ctrtupai.trtydsci.isSpaces()) {
      // COB(680): 068000         MOVE LOW-VALUES           TO TTUP-NEW-TTYP-TYPE-DESC
      ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypTypeDesc.lowValues();
      // COB(681): 068100     ELSE
    } else {
      // COB(682): 068200         MOVE FUNCTION TRIM(TRTYDSCI OF CTRTUPAI)
      // COB(682): 068300                                   TO TTUP-NEW-TTYP-TYPE-DESC
      ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypTypeDesc.setValue(
          ws.cotrtup.ctrtupai.trtydsci.trim());
      // COB(684): 068400     END-IF
    }
  }

  protected void _1200EditMapInputs() {
    // COB(690): 069000     SET INPUT-OK                  TO TRUE
    ws.wsMiscStorage.setInputOk(true);
    // COB(698): 069800     IF  TTUP-DETAILS-NOT-FOUND
    // COB(698): 069900     AND FUNCTION TRIM(TRTYPCDI OF CTRTUPAI)
    // COB(698): 070000           = TTUP-NEW-TTYP-TYPE
    // *****************************************************************
    //     VALIDATE THE SEARCH KEYS
    // *****************************************************************
    //     The key  was not in database. User sent the same key. So
    //     dont edit again. Set tran filter to valid and skip
    //     rest of edits
    //
    if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFound()
        && ws.cotrtup
            .ctrtupai
            .trtypcdi
            .trim()
            .equals(ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypType)) {
      // COB(701): 070100         IF CCARD-AID-PFK05
      if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk05()) {
        // COB(702): 070200            CONTINUE
        // do nothing
        // COB(703): 070300         ELSE
      } else {
        // COB(704): 070400            SET TTUP-DETAILS-NOT-FETCHED    TO TRUE
        ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupDetailsNotFetched(true);
        // COB(705): 070500         END-IF
      }
      // COB(706): 070600         SET FLG-TRANFILTER-ISVALID         TO TRUE
      ws.wsMiscStorage.setFlgTranfilterIsvalid(true);
      // COB(707): 070700         GO TO 1200-EDIT-MAP-INPUTS-EXIT
      return;
      // COB(708): 070800     ELSE
    } else {
      // COB(709): 070900         CONTINUE
      // do nothing
      // COB(710): 071000     END-IF
    }
    // COB(712): 071200     IF  TTUP-CREATE-NEW-RECORD
    // COB(712): 071300     OR  TTUP-CHANGES-OK-NOT-CONFIRMED
    if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupCreateNewRecord()
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkNotConfirmed()) {
      // COB(714): 071400         CONTINUE
      // do nothing
      // COB(715): 071500     ELSE
    } else {
      // COB(716): 071600         PERFORM 1210-EDIT-TRANTYPE
      // COB(716): 071700            THRU 1210-EDIT-TRANTYPE-EXIT
      _1210EditTrantype();
      // COB(720): 072000         IF  FLG-TRANFILTER-BLANK
      //         IF THE SEARCH CONDITIONS HAVE PROBLEMS FLAG THEM
      if (ws.wsMiscStorage.flgTranfilterBlank()) {
        // COB(721): 072100             IF WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(722): 072200                SET NO-SEARCH-CRITERIA-RECEIVED TO TRUE
          ws.wsMiscStorage.setNoSearchCriteriaReceived(true);
          // COB(723): 072300             END-IF
        }
        // COB(724): 072400             SET TTUP-DETAILS-NOT-FETCHED       TO TRUE
        ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupDetailsNotFetched(true);
        // COB(725): 072500             GO TO 1200-EDIT-MAP-INPUTS-EXIT
        return;
        // COB(726): 072600         END-IF
      }
      // COB(728): 072800         IF  FLG-TRANFILTER-NOT-OK
      if (ws.wsMiscStorage.flgTranfilterNotOk()) {
        // COB(729): 072900             SET TTUP-INVALID-SEARCH-KEYS       TO TRUE
        ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupInvalidSearchKeys(true);
        // COB(730): 073000             SET TTUP-DETAILS-NOT-FETCHED       TO TRUE
        ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupDetailsNotFetched(true);
        // COB(731): 073100             GO TO 1200-EDIT-MAP-INPUTS-EXIT
        return;
        // COB(732): 073200         END-IF
      }
      // COB(734): 073400         IF TTUP-DETAILS-NOT-FETCHED
      if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFetched()) {
        // COB(735): 073500            GO TO 1200-EDIT-MAP-INPUTS-EXIT
        return;
        // COB(736): 073600         END-IF
      }
      // COB(737): 073700     END-IF
    }
    // COB(741): 074100     SET FLG-TRANFILTER-ISVALID    TO TRUE
    // *****************************************************************
    //     SEARCH KEYS ALREADY VALIDATED. CHECK OTHER INPUTS
    // *****************************************************************
    ws.wsMiscStorage.setFlgTranfilterIsvalid(true);
    // COB(743): 074300     PERFORM 1205-COMPARE-OLD-NEW
    // COB(743): 074400        THRU 1205-COMPARE-OLD-NEW-EXIT
    _1205CompareOldNew();
    // COB(746): 074600     IF  NO-CHANGES-FOUND
    // COB(746): 074700     OR  TTUP-CHANGES-OK-NOT-CONFIRMED
    // COB(746): 074800     OR  TTUP-CHANGES-OKAYED-AND-DONE
    if (ws.wsMiscStorage.noChangesFound()
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkNotConfirmed()
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkayedAndDone()) {
      // COB(749): 074900         MOVE LOW-VALUES           TO WS-NON-KEY-FLAGS
      ws.wsMiscStorage.wsNonKeyFlags.lowValues();
      // COB(750): 075000         GO TO 1200-EDIT-MAP-INPUTS-EXIT
      return;
      // COB(751): 075100     END-IF
    }
    // COB(753): 075300     SET TTUP-CHANGES-NOT-OK       TO TRUE
    ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupChangesNotOk(true);
    // COB(758): 075800     MOVE 'Transaction Desc'       TO WS-EDIT-VARIABLE-NAME
    // *****************************************************************
    //     Edit Description
    // *****************************************************************
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Transaction Desc");
    // COB(759): 075900     MOVE TTUP-NEW-TTYP-TYPE-DESC  TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypTypeDesc);
    // COB(760): 076000     MOVE 50                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(50);
    // COB(761): 076100     PERFORM 1230-EDIT-ALPHANUM-REQD
    // COB(761): 076200        THRU 1230-EDIT-ALPHANUM-REQD-EXIT
    _1230EditAlphanumReqd();
    // COB(763): 076300     MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
    // COB(763): 076400                                   TO WS-EDIT-DESC-FLAGS
    ws.wsMiscStorage.wsNonKeyFlags.wsEditDescFlags.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);
    // COB(772): 077200     IF INPUT-ERROR
    //     Cross field edits begin here
    //
    //        No cross edits in this program so far
    //     Set green light for confirmation if no errors found
    if (ws.wsMiscStorage.inputError()) {
      // COB(773): 077300        CONTINUE
      // do nothing
      // COB(774): 077400     ELSE
    } else {
      // COB(775): 077500        SET TTUP-CHANGES-OK-NOT-CONFIRMED TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupChangesOkNotConfirmed(true);
      // COB(776): 077600     END-IF
    }
  }

  protected void _1205CompareOldNew() {
    // COB(784): 078400     SET NO-CHANGES-FOUND           TO TRUE
    ws.wsMiscStorage.setNoChangesFound(true);
    // COB(786): 078600     IF  FUNCTION UPPER-CASE (
    // COB(786): 078700         TTUP-NEW-TTYP-TYPE)    =
    // COB(786): 078800         FUNCTION UPPER-CASE (
    // COB(786): 078900         TTUP-OLD-TTYP-TYPE)
    // COB(786): 079000     AND FUNCTION UPPER-CASE (
    // COB(786): 079100         FUNCTION TRIM (TTUP-NEW-TTYP-TYPE-DESC))=
    // COB(786): 079200         FUNCTION UPPER-CASE (
    // COB(786): 079300         FUNCTION TRIM (TTUP-OLD-TTYP-TYPE-DESC))
    // COB(786): 079400     AND FUNCTION LENGTH (
    // COB(786): 079500         FUNCTION TRIM (TTUP-NEW-TTYP-TYPE-DESC))=
    // COB(786): 079600         FUNCTION LENGTH (
    // COB(786): 079700         FUNCTION TRIM (TTUP-OLD-TTYP-TYPE-DESC))
    if (ws.wsThisProgcommarea
            .ttupNewDetails
            .ttupNewTtypData
            .ttupNewTtypType
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea.ttupOldDetails.ttupOldTtypData.ttupOldTtypType.toUpperCase())
        && ws.wsThisProgcommarea
            .ttupNewDetails
            .ttupNewTtypData
            .ttupNewTtypTypeDesc
            .trim()
            .toUpperCase()
            .equals(
                ws.wsThisProgcommarea
                    .ttupOldDetails
                    .ttupOldTtypData
                    .ttupOldTtypTypeDesc
                    .trim()
                    .toUpperCase())
        && ws.wsThisProgcommarea
            .ttupNewDetails
            .ttupNewTtypData
            .ttupNewTtypTypeDesc
            .trim()
            .length()
            .equals(
                ws.wsThisProgcommarea
                    .ttupOldDetails
                    .ttupOldTtypData
                    .ttupOldTtypTypeDesc
                    .trim()
                    .length())) {
      // COB(799): 079900         IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(800): 080000            SET NO-CHANGES-DETECTED   TO TRUE
        ws.wsMiscStorage.setNoChangesDetected(true);
        // COB(801): 080100         ELSE
      } else {
        // COB(802): 080200            CONTINUE
        // do nothing
        // COB(803): 080300         END-IF
      }
      // COB(804): 080400     ELSE
    } else {
      // COB(805): 080500         IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(806): 080600            SET CHANGE-HAS-OCCURRED   TO TRUE
        ws.wsMiscStorage.setChangeHasOccurred(true);
        // COB(807): 080700         ELSE
      } else {
        // COB(808): 080800             CONTINUE
        // do nothing
        // COB(809): 080900         END-IF
      }
      // COB(810): 081000         GO TO 1205-COMPARE-OLD-NEW-EXIT
      return;
      // COB(811): 081100     END-IF
    }
  }

  /***                                                                 08190000*/
  protected void _1210EditTrantype() {
    // COB(821): 082100     SET FLG-TRANFILTER-NOT-OK    TO TRUE
    ws.wsMiscStorage.setFlgTranfilterNotOk(true);
    // COB(826): 082600     MOVE 'Tran Type code'         TO WS-EDIT-VARIABLE-NAME
    // *****************************************************************
    //     Edit Tran Type code
    // *****************************************************************
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Tran Type code");
    // COB(827): 082700     MOVE TTUP-NEW-TTYP-TYPE       TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(
        ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypType);
    // COB(828): 082800     MOVE 2                        TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(2);
    // COB(829): 082900     PERFORM 1245-EDIT-NUM-REQD
    // COB(829): 083000        THRU 1245-EDIT-NUM-REQD-EXIT
    _1245EditNumReqd();
    // COB(831): 083100     MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
    // COB(831): 083200                                   TO WS-EDIT-TTYP-FLAG
    ws.wsMiscStorage.wsEditTtypFlag.setValue(
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);
    // COB(834): 083400     IF FLG-TRANFILTER-ISVALID
    if (ws.wsMiscStorage.flgTranfilterIsvalid()) {
      // COB(835): 083500        COMPUTE WS-EDIT-NUMERIC-2
      // COB(835): 083600             = FUNCTION NUMVAL(TTUP-NEW-TTYP-TYPE)
      // COB(835): 083700        END-COMPUTE
      ws.wsMiscStorage.cicsOutputEditVars.wsEditNumeric2.setValue(
          ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypType.convertToNumeric());
      // COB(838): 083800        MOVE WS-EDIT-NUMERIC-2      TO WS-EDIT-ALPHANUMERIC-2
      ws.wsMiscStorage.cicsOutputEditVars.wsEditAlphanumeric2.setValue(
          ws.wsMiscStorage.cicsOutputEditVars.wsEditNumeric2);
      // COB(839): 083900        INSPECT WS-EDIT-ALPHANUMERIC-2
      // COB(839): 084000                REPLACING ALL SPACES BY ZEROS
      ws.wsMiscStorage.cicsOutputEditVars.wsEditAlphanumeric2.inspectAndReplaceAll(SPACES, "0");
      // COB(841): 084100        MOVE WS-EDIT-ALPHANUMERIC-2 TO TTUP-NEW-TTYP-TYPE
      ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypType.setValue(
          ws.wsMiscStorage.cicsOutputEditVars.wsEditAlphanumeric2);
      // COB(842): 084200     END-IF
    }
  }

  protected void _1230EditAlphanumReqd() {
    // COB(851): 085100     SET FLG-ALPHNANUM-NOT-OK          TO TRUE
    //     Initialize
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
    // COB(854): 085400     IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(854): 085500                                       EQUAL LOW-VALUES
    // COB(854): 085600     OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(854): 085700         EQUAL SPACES
    // COB(854): 085800     OR FUNCTION LENGTH(FUNCTION TRIM(
    // COB(854): 085900        WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
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
      // COB(861): 086100        SET INPUT-ERROR                TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(862): 086200        SET FLG-ALPHNANUM-BLANK        TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumBlank(true);
      // COB(863): 086300        IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(864): 086400           STRING
        // COB(864): 086500             FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(864): 086600             ' must be supplied.'
        // COB(864): 086700             DELIMITED BY SIZE
        // COB(864): 086800             INTO WS-RETURN-MSG
        // COB(864): 086900           END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        // COB(869): 086900           END-STRING
        // COB(870): 087000        END-IF
      }
      // COB(872): 087200        GO TO  1230-EDIT-ALPHANUM-REQD-EXIT
      return;
      // COB(873): 087300     END-IF
    }
    // COB(876): 087600     MOVE LIT-ALL-ALPHANUM-FROM-X TO LIT-ALL-ALPHANUM-FROM
    //     Only Alphabets,numbers and space allowed
    ws.litAllAlphanumFrom.setValue(ws.wsLiterals.litAllAlphanumFromX);
    // COB(878): 087800     INSPECT WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(878): 087900       CONVERTING LIT-ALL-ALPHANUM-FROM
    // COB(878): 088000               TO LIT-ALPHANUM-SPACES-TO
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.inspectAndConvertCharacters(
        ws.litAllAlphanumFrom.getValue(),
        ws.litAlphanumSpacesTo.getValue(),
        0,
        ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt());
    // COB(882): 088200     IF FUNCTION LENGTH(
    // COB(882): 088300             FUNCTION TRIM(
    // COB(882): 088400             WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(882): 088500                            )) = 0
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditAlphanumOnly
        .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
        .trim()
        .length()
        .equals(0)) {
      // COB(886): 088600        CONTINUE
      // do nothing
      // COB(887): 088700     ELSE
    } else {
      // COB(888): 088800        SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(889): 088900        SET FLG-ALPHNANUM-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
      // COB(890): 089000        IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(891): 089100           STRING
        // COB(891): 089200             FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(891): 089300             ' can have numbers or alphabets only.'
        // COB(891): 089400             DELIMITED BY SIZE
        // COB(891): 089500             INTO WS-RETURN-MSG
        // COB(891): 089600           END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(),
            " can have numbers or alphabets only.");
        // COB(896): 089600           END-STRING
        // COB(897): 089700        END-IF
      }
      // COB(898): 089800        GO TO  1230-EDIT-ALPHANUM-REQD-EXIT
      return;
      // COB(899): 089900     END-IF
    }
    // COB(901): 090100     SET FLG-ALPHNANUM-ISVALID    TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumIsvalid(true);
  }

  protected void _1245EditNumReqd() {
    // COB(909): 090900     SET FLG-ALPHNANUM-NOT-OK          TO TRUE
    //     Initialize
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
    // COB(912): 091200     IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(912): 091300                                       EQUAL LOW-VALUES
    // COB(912): 091400     OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    // COB(912): 091500         EQUAL SPACES
    // COB(912): 091600     OR FUNCTION LENGTH(FUNCTION TRIM(
    // COB(912): 091700        WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
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
      // COB(919): 091900        SET INPUT-ERROR                TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(920): 092000        SET FLG-ALPHNANUM-BLANK        TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumBlank(true);
      // COB(921): 092100        IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(922): 092200           STRING
        // COB(922): 092300             FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(922): 092400             ' must be supplied.'
        // COB(922): 092500             DELIMITED BY SIZE
        // COB(922): 092600             INTO WS-RETURN-MSG
        // COB(922): 092700           END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        // COB(927): 092700           END-STRING
        // COB(928): 092800        END-IF
      }
      // COB(929): 092900        GO TO  1245-EDIT-NUM-REQD-EXIT
      return;
      // COB(930): 093000     END-IF
    }
    // COB(934): 093400     IF FUNCTION TEST-NUMVAL(WS-EDIT-ALPHANUM-ONLY(1:
    // COB(934): 093500                             WS-EDIT-ALPHANUM-LENGTH)) = 0
    //     Only all numeric allowed

      NChar c1 = new NChar(ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt());
    c1.allocate();
    c1.setValue(ws.wsMiscStorage
            .wsGenericEdits
            .wsEditAlphanumOnly
            .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt()));

      if (c1
        .testConvertToNumeric()
        .equals(0)) {
      // COB(936): 093600        CONTINUE
      // do nothing
      // COB(937): 093700     ELSE
    } else {
      // COB(938): 093800        SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(939): 093900        SET FLG-ALPHNANUM-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
      // COB(940): 094000        IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(941): 094100           STRING
        // COB(941): 094200             FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(941): 094300             ' must be numeric.'
        // COB(941): 094400             DELIMITED BY SIZE
        // COB(941): 094500             INTO WS-RETURN-MSG
        // COB(941): 094600           END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be numeric.");
        // COB(946): 094600           END-STRING
        // COB(947): 094700        END-IF
      }
      // COB(948): 094800        GO TO  1245-EDIT-NUM-REQD-EXIT
      return;
      // COB(949): 094900     END-IF
    }
    // COB(954): 095400     IF FUNCTION NUMVAL(WS-EDIT-ALPHANUM-ONLY(1:
    // COB(954): 095500                        WS-EDIT-ALPHANUM-LENGTH)) = 0
    //
    //     Must not be zero
    if (ws.wsMiscStorage
        .wsGenericEdits
        .wsEditAlphanumOnly
        .getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt())
        .convertToNumeric()
        .equals(0)) {
      // COB(956): 095600        SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(957): 095700        SET FLG-ALPHNANUM-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
      // COB(958): 095800        IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(959): 095900           STRING
        // COB(959): 096000             FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        // COB(959): 096100             ' must not be zero.'
        // COB(959): 096200             DELIMITED BY SIZE
        // COB(959): 096300             INTO WS-RETURN-MSG
        // COB(959): 096400           END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
            ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must not be zero.");
        // COB(964): 096400           END-STRING
        // COB(965): 096500        END-IF
      }
      // COB(966): 096600        GO TO  1245-EDIT-NUM-REQD-EXIT
      return;
      // COB(967): 096700     ELSE
    } else {
      // COB(968): 096800        CONTINUE
      // do nothing
      // COB(969): 096900     END-IF
    }
    // COB(972): 097200     SET FLG-ALPHNANUM-ISVALID    TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumIsvalid(true);
  }

  protected void _2000DecideAction() {
    // COB(979): 097900     EVALUATE TRUE
    // COB(984): 098400        WHEN TTUP-DETAILS-NOT-FETCHED
    // *****************************************************************
    //        NO DETAILS SHOWN.
    //        SO GET THEM AND SETUP DETAIL EDIT SCREEN
    // *****************************************************************
    if ((ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFetched())
        // COB(988): 098800        WHEN CCARD-AID-PFK12
        // *****************************************************************
        //        CHANGES MADE. BUT USER CANCELS
        // *****************************************************************
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk12()) {
      // COB(989): 098900           IF  FLG-TRANFILTER-ISVALID
      if (ws.wsMiscStorage.flgTranfilterIsvalid()) {
        // COB(990): 099000               SET WS-RETURN-MSG-OFF               TO TRUE
        ws.wsMiscStorage.setWsReturnMsgOff(true);
        // COB(991): 099100               PERFORM 9000-READ-TRANTYPE
        // COB(991): 099200                  THRU 9000-READ-TRANTYPE-EXIT
        _9000ReadTrantype();
        // COB(993): 099300               IF FOUND-TRANTYPE-IN-TABLE
        if (ws.wsMiscStorage.wsTableReadFlags.foundTrantypeInTable()) {
          // COB(994): 099400                  SET TTUP-SHOW-DETAILS            TO TRUE
          ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupShowDetails(true);
          // COB(995): 099500               ELSE
        } else {
          // COB(996): 099600                  SET TTUP-DETAILS-NOT-FOUND       TO TRUE
          ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupDetailsNotFound(true);
          // COB(997): 099700               END-IF
        }
        // COB(998): 099800           ELSE
      } else {
        // COB(999): 099900               EVALUATE TRUE
        // COB(1000): 100000                  WHEN TTUP-CONFIRM-DELETE
        if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupConfirmDelete()) {
          // COB(1001): 100100                   SET WS-DELETE-WAS-CANCELLED     TO TRUE
          ws.wsMiscStorage.setWsDeleteWasCancelled(true);
          // COB(1002): 100200                   SET TTUP-DETAILS-NOT-FETCHED    TO TRUE
          ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupDetailsNotFetched(true);
          // COB(1003): 100300                  WHEN TTUP-CHANGES-OK-NOT-CONFIRMED
        } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkNotConfirmed()) {
          // COB(1004): 100400                   SET WS-UPDATE-WAS-CANCELLED     TO TRUE
          ws.wsMiscStorage.setWsUpdateWasCancelled(true);
          // COB(1005): 100500                   SET TTUP-CHANGES-BACKED-OUT     TO TRUE
          ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupChangesBackedOut(true);
          // COB(1006): 100600                  WHEN OTHER
        } else {
          // COB(1007): 100700                   SET TTUP-DETAILS-NOT-FETCHED    TO TRUE
          ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupDetailsNotFetched(true);
          // COB(1008): 100800               END-EVALUATE
        }
        // COB(1010): 101000           END-IF
      }
      // COB(1016): 101600        WHEN TTUP-CONFIRM-DELETE
      // COB(1016): 101700         AND CCARD-AID-PFK12
      // *****************************************************************
      //        DETAILS SHOWN
      //        BUT USER PRESSES F4 FOR DELETE
      //        ASK THE USER TO CONFIRM THE DELETE
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupConfirmDelete()
        && ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk12()) {
      // COB(1018): 101800           SET TTUP-CONFIRM-DELETE                 TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupConfirmDelete(true);
      // COB(1023): 102300        WHEN TTUP-SHOW-DETAILS
      // *****************************************************************
      //        DETAILS SHOWN
      //        CHECK CHANGES AND ASK CONFIRMATION IF GOOD
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupShowDetails()) {
      // COB(1024): 102400           IF INPUT-ERROR
      // COB(1024): 102500           OR NO-CHANGES-DETECTED
      // COB(1024): 102600           OR WS-INVALID-KEY
      if (ws.wsMiscStorage.inputError()
          || ws.wsMiscStorage.noChangesDetected()
          || ws.wsMiscStorage.wsInvalidKey()) {
        // COB(1027): 102700              CONTINUE
        // do nothing
        // COB(1028): 102800           ELSE
      } else {
        // COB(1029): 102900              SET TTUP-CHANGES-OK-NOT-CONFIRMED TO TRUE
        ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupChangesOkNotConfirmed(true);
        // COB(1030): 103000           END-IF
      }
      // COB(1035): 103500        WHEN TTUP-CHANGES-NOT-OK
      // *****************************************************************
      //        DETAILS SHOWN
      //        BUT INPUT EDIT ERRORS FOUND
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesNotOk()) {
      // COB(1036): 103600            CONTINUE
      // do nothing
      // COB(1041): 104100        WHEN TTUP-CHANGES-BACKED-OUT
      // *****************************************************************
      //        CHANGES BACKED OUT
      //        GO BACK TO CHANGES NOT OK STATE
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesBackedOut()) {
      // COB(1042): 104200            SET TTUP-CHANGES-NOT-OK            TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupChangesNotOk(true);
      // COB(1046): 104600        WHEN TTUP-INVALID-SEARCH-KEYS
      // *****************************************************************
      //        PROBLEMS FOUND IN SEARCH KEYS
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupInvalidSearchKeys()) {
      // COB(1047): 104700            CONTINUE
      // do nothing
      // COB(1053): 105300        WHEN CCARD-AID-PFK05
      // COB(1053): 105400         AND TTUP-DETAILS-NOT-FOUND
      // *****************************************************************
      //        SEARCH KEY WAS VALID.
      //        BUT DATA WAS NOT FOUND IN TABLE
      //        CUSTOMER DECIDES TO CONTINUE AND ADD RECORD
      // *****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk05()
        && ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFound()) {
      // COB(1055): 105500            SET TTUP-CREATE-NEW-RECORD TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupCreateNewRecord(true);
      // COB(1060): 106000        WHEN TTUP-CHANGES-OK-NOT-CONFIRMED
      // *****************************************************************
      //        DETAILS EDITED , FOUND OK, CONFIRM SAVE REQUESTED
      //        CONFIRMATION NOT GIVEN. SO SHOW DETAILS AGAIN
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkNotConfirmed()) {
      // COB(1061): 106100            CONTINUE
      // do nothing
      // COB(1065): 106500        WHEN TTUP-CHANGES-OKAYED-AND-DONE
      // *****************************************************************
      //        SHOW CONFIRMATION. GO BACK TO SQUARE 1
      // *****************************************************************
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkayedAndDone()) {
      // COB(1066): 106600            SET TTUP-SHOW-DETAILS TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupShowDetails(true);
      // COB(1067): 106700            IF CDEMO-FROM-TRANID    EQUAL LOW-VALUES
      // COB(1067): 106800            OR CDEMO-FROM-TRANID    EQUAL SPACES
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isSpaces()) {
        // COB(1069): 106900               MOVE ZEROES       TO CDEMO-ACCT-ID
        // COB(1069): 107000                                    CDEMO-CARD-NUM
        ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
        ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.zeros();
        // COB(1071): 107100               MOVE LOW-VALUES   TO CDEMO-ACCT-STATUS
        ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctStatus.lowValues();
        // COB(1072): 107200            END-IF
      }
      // COB(1073): 107300        WHEN OTHER
    } else {
      // COB(1074): 107400             MOVE LIT-THISPGM    TO ABEND-CULPRIT
      ws.csmsg02y.abendData.abendCulprit.setValue(ws.wsLiterals.litThispgm);
      // COB(1075): 107500             MOVE '0001'         TO ABEND-CODE
      ws.csmsg02y.abendData.abendCode.setString("0001");
      // COB(1076): 107600             MOVE SPACES         TO ABEND-REASON
      ws.csmsg02y.abendData.abendReason.spaces();
      // COB(1077): 107700             MOVE 'UNEXPECTED DATA SCENARIO'
      // COB(1077): 107800                                 TO ABEND-MSG
      ws.csmsg02y.abendData.abendMsg.setString("UNEXPECTED DATA SCENARIO");
      // COB(1079): 107900             PERFORM ABEND-ROUTINE
      // COB(1079): 108000                THRU ABEND-ROUTINE-EXIT
      abendRoutine();
      // COB(1081): 108100     END-EVALUATE
    }
  }

  protected void _3000SendMap() {
    // COB(1090): 109000     PERFORM 3100-SCREEN-INIT
    // COB(1090): 109100        THRU 3100-SCREEN-INIT-EXIT
    _3100ScreenInit();
    // COB(1092): 109200     PERFORM 3200-SETUP-SCREEN-VARS
    // COB(1092): 109300        THRU 3200-SETUP-SCREEN-VARS-EXIT
    _3200SetupScreenVars();
    // COB(1094): 109400     PERFORM 3250-SETUP-INFOMSG
    // COB(1094): 109500        THRU 3250-SETUP-INFOMSG-EXIT
    _3250SetupInfomsg();
    // COB(1096): 109600     PERFORM 3300-SETUP-SCREEN-ATTRS
    // COB(1096): 109700        THRU 3300-SETUP-SCREEN-ATTRS-EXIT
    _3300SetupScreenAttrs();
    // COB(1098): 109800     PERFORM 3390-SETUP-INFOMSG-ATTRS
    // COB(1098): 109900        THRU 3390-SETUP-INFOMSG-ATTRS-EXIT
    _3390SetupInfomsgAttrs();
    // COB(1100): 110000     PERFORM 3391-SETUP-PFKEY-ATTRS
    // COB(1100): 110100        THRU 3391-SETUP-PFKEY-ATTRS-EXIT
    _3391SetupPfkeyAttrs();
    // COB(1102): 110200     PERFORM 3400-SEND-SCREEN
    // COB(1102): 110300        THRU 3400-SEND-SCREEN-EXIT
    _3400SendScreen();
  }

  protected void _3100ScreenInit() {
    // COB(1111): 111100     MOVE LOW-VALUES                TO CTRTUPAO
    ws.cotrtup.ctrtupao.lowValues();
    // COB(1113): 111300     MOVE FUNCTION CURRENT-DATE     TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(1115): 111500     MOVE CCDA-TITLE01              TO TITLE01O OF CTRTUPAO
    ws.cotrtup.ctrtupao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(1116): 111600     MOVE CCDA-TITLE02              TO TITLE02O OF CTRTUPAO
    ws.cotrtup.ctrtupao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(1117): 111700     MOVE LIT-THISTRANID            TO TRNNAMEO OF CTRTUPAO
    ws.cotrtup.ctrtupao.trnnameo.setValue(ws.wsLiterals.litThistranid);
    // COB(1118): 111800     MOVE LIT-THISPGM               TO PGMNAMEO OF CTRTUPAO
    ws.cotrtup.ctrtupao.pgmnameo.setValue(ws.wsLiterals.litThispgm);
    // COB(1120): 112000     MOVE FUNCTION CURRENT-DATE     TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(1122): 112200     MOVE WS-CURDATE-MONTH          TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(1123): 112300     MOVE WS-CURDATE-DAY            TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(1124): 112400     MOVE WS-CURDATE-YEAR(3:2)      TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(1126): 112600     MOVE WS-CURDATE-MM-DD-YY       TO CURDATEO OF CTRTUPAO
    ws.cotrtup.ctrtupao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(1128): 112800     MOVE WS-CURTIME-HOURS          TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(1129): 112900     MOVE WS-CURTIME-MINUTE         TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(1130): 113000     MOVE WS-CURTIME-SECOND         TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(1132): 113200     MOVE WS-CURTIME-HH-MM-SS       TO CURTIMEO OF CTRTUPAO
    ws.cotrtup.ctrtupao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  protected void _3200SetupScreenVars() {
    // COB(1142): 114200     IF CDEMO-PGM-ENTER
    //     INITIALIZE SEARCH CRITERIA
    if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()) {
      // COB(1143): 114300        CONTINUE
      // do nothing
      // COB(1144): 114400     ELSE
    } else {
      // COB(1145): 114500        EVALUATE TRUE
      // COB(1146): 114600         WHEN TTUP-DETAILS-NOT-FETCHED
      if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFetched()) {
        // COB(1147): 114700            PERFORM 3201-SHOW-INITIAL-VALUES
        // COB(1147): 114800               THRU 3201-SHOW-INITIAL-VALUES-EXIT
        _3201ShowInitialValues();
        // COB(1149): 114900         WHEN TTUP-SHOW-DETAILS
      } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupShowDetails()
          // COB(1150): 115000         WHEN TTUP-CONFIRM-DELETE
          || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupConfirmDelete()
          // COB(1151): 115100         WHEN TTUP-DELETE-FAILED
          || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDeleteFailed()
          // COB(1152): 115200         WHEN TTUP-DELETE-DONE
          || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDeleteDone()
          // COB(1153): 115300         WHEN TTUP-CHANGES-BACKED-OUT
          || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesBackedOut()) {
        // COB(1154): 115400            INITIALIZE TTUP-NEW-DETAILS
        ws.wsThisProgcommarea.ttupNewDetails.initialize();
        // COB(1155): 115500            PERFORM 3202-SHOW-ORIGINAL-VALUES
        // COB(1155): 115600               THRU 3202-SHOW-ORIGINAL-VALUES-EXIT
        _3202ShowOriginalValues();
        // COB(1157): 115700         WHEN TTUP-CHANGES-MADE
      } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesMade()
          // COB(1158): 115800         WHEN TTUP-CHANGES-NOT-OK
          || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesNotOk()
          // COB(1159): 115900         WHEN TTUP-DETAILS-NOT-FOUND
          || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFound()
          // COB(1160): 116000         WHEN TTUP-INVALID-SEARCH-KEYS
          || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupInvalidSearchKeys()
          // COB(1161): 116100         WHEN TTUP-CREATE-NEW-RECORD
          || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupCreateNewRecord()
          // COB(1162): 116200         WHEN TTUP-CHANGES-OKAYED-AND-DONE
          || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkayedAndDone()) {
        // COB(1163): 116300            PERFORM 3203-SHOW-UPDATED-VALUES
        // COB(1163): 116400               THRU 3203-SHOW-UPDATED-VALUES-EXIT
        _3203ShowUpdatedValues();
        // COB(1165): 116500         WHEN OTHER
      } else {
        // COB(1166): 116600            INITIALIZE TTUP-NEW-DETAILS
        ws.wsThisProgcommarea.ttupNewDetails.initialize();
        // COB(1167): 116700            PERFORM 3202-SHOW-ORIGINAL-VALUES
        // COB(1167): 116800               THRU 3202-SHOW-ORIGINAL-VALUES-EXIT
        _3202ShowOriginalValues();
        // COB(1169): 116900        END-EVALUATE
      }
      // COB(1170): 117000      END-IF
    }
  }

  protected void _3201ShowInitialValues() {
    // COB(1177): 117700     MOVE LOW-VALUES                     TO  TRTYPCDO OF CTRTUPAO
    // COB(1177): 117800                                             TRTYPCDO OF CTRTUPAO
    ws.cotrtup.ctrtupao.trtypcdo.lowValues();
    ws.cotrtup.ctrtupao.trtypcdo.lowValues();
  }

  protected void _3202ShowOriginalValues() {
    // COB(1187): 118700     MOVE LOW-VALUES                     TO WS-NON-KEY-FLAGS
    ws.wsMiscStorage.wsNonKeyFlags.lowValues();
    // COB(1189): 118900     MOVE TTUP-OLD-TTYP-TYPE             TO TRTYPCDO OF CTRTUPAO
    ws.cotrtup.ctrtupao.trtypcdo.setValue(
        ws.wsThisProgcommarea.ttupOldDetails.ttupOldTtypData.ttupOldTtypType);
    // COB(1190): 119000     MOVE TTUP-OLD-TTYP-TYPE-DESC        TO TRTYDSCO OF CTRTUPAO
    ws.cotrtup.ctrtupao.trtydsco.setValue(
        ws.wsThisProgcommarea.ttupOldDetails.ttupOldTtypData.ttupOldTtypTypeDesc);
  }

  protected void _3203ShowUpdatedValues() {
    // COB(1199): 119900     MOVE TTUP-NEW-TTYP-TYPE             TO TRTYPCDO OF CTRTUPAO
    ws.cotrtup.ctrtupao.trtypcdo.setValue(
        ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypType);
    // COB(1200): 120000     MOVE TTUP-NEW-TTYP-TYPE-DESC        TO TRTYDSCO OF CTRTUPAO
    ws.cotrtup.ctrtupao.trtydsco.setValue(
        ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypTypeDesc);
  }

  protected void _3250SetupInfomsg() {
    // COB(1213): 121300         EVALUATE TRUE
    //     SETUP INFORMATION MESSAGE
    // COB(1214): 121400         WHEN CDEMO-PGM-ENTER
    if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()) {
      // COB(1215): 121500              SET  PROMPT-FOR-SEARCH-KEYS    TO TRUE
      ws.wsMiscStorage.setPromptForSearchKeys(true);
      // COB(1216): 121600         WHEN TTUP-DETAILS-NOT-FETCHED
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFetched()
        // COB(1217): 121700         WHEN TTUP-INVALID-SEARCH-KEYS
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupInvalidSearchKeys()) {
      // COB(1218): 121800              SET PROMPT-FOR-SEARCH-KEYS     TO TRUE
      ws.wsMiscStorage.setPromptForSearchKeys(true);
      // COB(1219): 121900         WHEN TTUP-DETAILS-NOT-FOUND
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFound()) {
      // COB(1220): 122000              SET PROMPT-CREATE-NEW-RECORD   TO TRUE
      ws.wsMiscStorage.setPromptCreateNewRecord(true);
      // COB(1221): 122100         WHEN TTUP-SHOW-DETAILS
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupShowDetails()
        // COB(1222): 122200         WHEN TTUP-CHANGES-BACKED-OUT
        // COB(1222): 122300         AND (TTUP-OLD-TTYP-TYPE    = LOW-VALUES
        // COB(1222): 122400         OR   TTUP-OLD-TTYP-TYPE    = SPACES)
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesBackedOut()
            && (ws.wsThisProgcommarea.ttupOldDetails.ttupOldTtypData.ttupOldTtypType.isLowValues()
                || ws.wsThisProgcommarea.ttupOldDetails.ttupOldTtypData.ttupOldTtypType
                    .isSpaces())) {
      // COB(1225): 122500              SET  PROMPT-FOR-SEARCH-KEYS    TO TRUE
      ws.wsMiscStorage.setPromptForSearchKeys(true);
      // COB(1226): 122600         WHEN TTUP-CHANGES-BACKED-OUT
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesBackedOut()
        // COB(1227): 122700         WHEN TTUP-CHANGES-NOT-OK
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesNotOk()) {
      // COB(1228): 122800              SET PROMPT-FOR-CHANGES         TO TRUE
      ws.wsMiscStorage.setPromptForChanges(true);
      // COB(1229): 122900         WHEN TTUP-CONFIRM-DELETE
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupConfirmDelete()) {
      // COB(1230): 123000              SET PROMPT-DELETE-CONFIRM      TO TRUE
      ws.wsMiscStorage.setPromptDeleteConfirm(true);
      // COB(1231): 123100         WHEN TTUP-DELETE-FAILED
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDeleteFailed()) {
      // COB(1232): 123200              SET INFORM-FAILURE             TO TRUE
      ws.wsMiscStorage.setInformFailure(true);
      // COB(1233): 123300         WHEN TTUP-DELETE-DONE
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDeleteDone()) {
      // COB(1234): 123400              SET CONFIRM-DELETE-SUCCESS     TO TRUE
      ws.wsMiscStorage.setConfirmDeleteSuccess(true);
      // COB(1235): 123500         WHEN TTUP-CREATE-NEW-RECORD
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupCreateNewRecord()) {
      // COB(1236): 123600              SET PROMPT-FOR-NEWDATA         TO TRUE
      ws.wsMiscStorage.setPromptForNewdata(true);
      // COB(1237): 123700         WHEN TTUP-CHANGES-OK-NOT-CONFIRMED
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkNotConfirmed()) {
      // COB(1238): 123800              SET PROMPT-FOR-CONFIRMATION    TO TRUE
      ws.wsMiscStorage.setPromptForConfirmation(true);
      // COB(1239): 123900         WHEN TTUP-CHANGES-OKAYED-AND-DONE
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkayedAndDone()) {
      // COB(1240): 124000              SET CONFIRM-UPDATE-SUCCESS     TO TRUE
      ws.wsMiscStorage.setConfirmUpdateSuccess(true);
      // COB(1241): 124100         WHEN TTUP-CHANGES-OKAYED-LOCK-ERROR
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkayedLockError()) {
      // COB(1242): 124200              SET INFORM-FAILURE             TO TRUE
      ws.wsMiscStorage.setInformFailure(true);
      // COB(1243): 124300         WHEN TTUP-CHANGES-OKAYED-BUT-FAILED
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkayedButFailed()) {
      // COB(1244): 124400              SET INFORM-FAILURE             TO TRUE
      ws.wsMiscStorage.setInformFailure(true);
      // COB(1245): 124500         WHEN WS-NO-INFO-MESSAGE
    } else if (ws.wsMiscStorage.wsNoInfoMessage()) {
      // COB(1246): 124600             SET PROMPT-FOR-SEARCH-KEYS      TO TRUE
      ws.wsMiscStorage.setPromptForSearchKeys(true);
      // COB(1247): 124700     END-EVALUATE
    }
    // COB(1251): 125100     COMPUTE WS-STRING-LEN =
    // COB(1251): 125200             FUNCTION LENGTH(
    // COB(1251): 125300                      FUNCTION TRIM(WS-INFO-MSG)
    // COB(1251): 125400                            )
    //  Center justify the text
    //
    ws.wsMiscStorage.wsMiscVars.wsStringLen.setValue(ws.wsMiscStorage.wsInfoMsg.trim().length());
    // COB(1255): 125500     COMPUTE WS-STRING-MID =
    // COB(1255): 125600            (FUNCTION LENGTH(WS-INFO-MSG)
    // COB(1255): 125700                          - WS-STRING-LEN) / 2 + 1
    ws.wsMiscStorage.wsMiscVars.wsStringMid.setValue((new BigDecimal
            (ws.wsMiscStorage
            .wsInfoMsg
            .length())
            .subtract(ws.wsMiscStorage.wsMiscVars.wsStringLen.getValue()))
            .divide(new BigDecimal("2"), 0, RoundingMode.HALF_UP)
            .add(new BigDecimal("1")));
    // COB(1258): 125800     MOVE WS-INFO-MSG(1:WS-STRING-LEN)
    // COB(1258): 125900       TO WS-STRING-OUT(WS-STRING-MID:
    // COB(1258): 126000                        WS-STRING-LEN)
    ws.wsMiscStorage.wsMiscVars.wsStringOut.setValue(
        ws.wsMiscStorage.wsInfoMsg,
        0,
        ws.wsMiscStorage.wsMiscVars.wsStringMid.getInt() - 1,
        ws.wsMiscStorage.wsMiscVars.wsStringLen.getInt());
    // COB(1262): 126200     MOVE WS-STRING-OUT                  TO INFOMSGO OF CTRTUPAO
    ws.cotrtup.ctrtupao.infomsgo.setValue(ws.wsMiscStorage.wsMiscVars.wsStringOut);
    // COB(1264): 126400     MOVE WS-RETURN-MSG                  TO ERRMSGO  OF CTRTUPAO
    ws.cotrtup.ctrtupao.errmsgo.setValue(ws.wsMiscStorage.wsReturnMsg);
  }

  protected void _3300SetupScreenAttrs() {
    // COB(1272): 127200     PERFORM 3310-PROTECT-ALL-ATTRS
    // COB(1272): 127300        THRU 3310-PROTECT-ALL-ATTRS-EXIT
    //     PROTECT ALL FIELDS
    _3310ProtectAllAttrs();
    // COB(1276): 127600     EVALUATE TRUE
    //     UNPROTECT BASED ON CONTEXT
    // COB(1277): 127700        WHEN TTUP-DETAILS-NOT-FETCHED
    if ((ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFetched())
        // COB(1278): 127800        WHEN TTUP-INVALID-SEARCH-KEYS
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupInvalidSearchKeys()
        // COB(1279): 127900        WHEN TTUP-DETAILS-NOT-FOUND
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFound()
        // COB(1280): 128000        WHEN TTUP-CHANGES-BACKED-OUT
        // COB(1280): 128100         AND (TTUP-OLD-TTYP-TYPE    = LOW-VALUES
        // COB(1280): 128200         OR   TTUP-OLD-TTYP-TYPE    = SPACES)
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesBackedOut()
            && (ws.wsThisProgcommarea.ttupOldDetails.ttupOldTtypData.ttupOldTtypType.isLowValues()
                || ws.wsThisProgcommarea.ttupOldDetails.ttupOldTtypData.ttupOldTtypType
                    .isSpaces())) {
      // COB(1284): 128400             MOVE DFHBMFSE      TO TRTYPCDA OF CTRTUPAI
      //             Make Search Keys editable
      ws.cotrtup.ctrtupai.filler57.trtypcda.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(1285): 128500        WHEN TTUP-SHOW-DETAILS
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupShowDetails()
        // COB(1286): 128600        WHEN TTUP-CHANGES-NOT-OK
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesNotOk()
        // COB(1287): 128700        WHEN TTUP-CREATE-NEW-RECORD
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupCreateNewRecord()
        // COB(1288): 128800        WHEN TTUP-CHANGES-BACKED-OUT
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesBackedOut()) {
      // COB(1289): 128900             PERFORM 3320-UNPROTECT-FEW-ATTRS
      // COB(1289): 129000                THRU 3320-UNPROTECT-FEW-ATTRS-EXIT
      _3320UnprotectFewAttrs();
      // COB(1291): 129100        WHEN TTUP-CHANGES-OK-NOT-CONFIRMED
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkNotConfirmed()
        // COB(1292): 129200        WHEN TTUP-CHANGES-OKAYED-AND-DONE
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkayedAndDone()
        // COB(1293): 129300        WHEN TTUP-DELETE-IN-PROGRESS
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDeleteInProgress()) {
      // COB(1295): 129500             CONTINUE
      //             Keep all fields protected
      // do nothing
      // COB(1296): 129600        WHEN OTHER
    } else {
      // COB(1297): 129700             MOVE DFHBMFSE      TO TRTYPCDA OF CTRTUPAI
      ws.cotrtup.ctrtupai.filler57.trtypcda.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(1298): 129800     END-EVALUATE
    }
    // COB(1303): 130300     EVALUATE TRUE
    // *****************************************************************
    //     POSITION CURSOR - ORDER BASED ON SCREEN LOCATION
    // *****************************************************************
    // COB(1304): 130400        WHEN TTUP-DETAILS-NOT-FETCHED
    if ((ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFetched())
        // COB(1305): 130500        WHEN TTUP-DETAILS-NOT-FOUND
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFound()
        // COB(1306): 130600        WHEN TTUP-INVALID-SEARCH-KEYS
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupInvalidSearchKeys()
        // COB(1307): 130700        WHEN FLG-TRANFILTER-NOT-OK
        || ws.wsMiscStorage.flgTranfilterNotOk()
        // COB(1308): 130800        WHEN FLG-TRANFILTER-BLANK
        || ws.wsMiscStorage.flgTranfilterBlank()
        // COB(1309): 130900        WHEN TTUP-CHANGES-OKAYED-AND-DONE
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkayedAndDone()
        // COB(1310): 131000        WHEN TTUP-CHANGES-BACKED-OUT
        // COB(1310): 131100         AND (TTUP-OLD-TTYP-TYPE    = LOW-VALUES
        // COB(1310): 131200         OR   TTUP-OLD-TTYP-TYPE    = SPACES)
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesBackedOut()
            && (ws.wsThisProgcommarea.ttupOldDetails.ttupOldTtypData.ttupOldTtypType.isLowValues()
                || ws.wsThisProgcommarea.ttupOldDetails.ttupOldTtypData.ttupOldTtypType
                    .isSpaces())) {
      // COB(1313): 131300             MOVE -1             TO TRTYPCDL OF CTRTUPAI
      ws.cotrtup.ctrtupai.trtypcdl.setValue(-1);
      // COB(1315): 131500        WHEN TTUP-CREATE-NEW-RECORD
      //     Description
    } else if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupCreateNewRecord()
        // COB(1316): 131600        WHEN NO-CHANGES-DETECTED
        || ws.wsMiscStorage.noChangesDetected()
        // COB(1317): 131700        WHEN FLG-DESCRIPTION-NOT-OK
        || ws.wsMiscStorage.wsNonKeyFlags.flgDescriptionNotOk()
        // COB(1318): 131800        WHEN FLG-DESCRIPTION-BLANK
        || ws.wsMiscStorage.wsNonKeyFlags.flgDescriptionBlank()
        // COB(1319): 131900        WHEN TTUP-CHANGES-MADE
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesMade()
        // COB(1320): 132000        WHEN TTUP-CHANGES-BACKED-OUT
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesBackedOut()
        // COB(1321): 132100        WHEN TTUP-SHOW-DETAILS
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupShowDetails()) {
      // COB(1322): 132200            MOVE -1              TO TRTYDSCL OF CTRTUPAI
      ws.cotrtup.ctrtupai.trtydscl.setValue(-1);
      // COB(1323): 132300        WHEN OTHER
    } else {
      // COB(1324): 132400            MOVE -1              TO TRTYPCDL OF CTRTUPAI
      ws.cotrtup.ctrtupai.trtypcdl.setValue(-1);
      // COB(1325): 132500      END-EVALUATE
    }
    // COB(1331): 133100     IF FLG-TRANFILTER-NOT-OK
    // COB(1331): 133200     OR TTUP-DELETE-FAILED
    // *****************************************************************
    //     SETUP COLOR
    // *****************************************************************
    //     Transaction Type code filer
    if (ws.wsMiscStorage.flgTranfilterNotOk()
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDeleteFailed()) {
      // COB(1333): 133300        MOVE DFHRED              TO TRTYPCDC OF CTRTUPAO
      ws.cotrtup.ctrtupao.trtypcdc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(1334): 133400     END-IF
    }
    // COB(1336): 133600     IF  FLG-TRANFILTER-BLANK
    // COB(1336): 133700     AND CDEMO-PGM-REENTER
    if (ws.wsMiscStorage.flgTranfilterBlank()
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(1338): 133800         MOVE '*'                TO TRTYPCDO OF CTRTUPAO
      ws.cotrtup.ctrtupao.trtypcdo.setString("*");
      // COB(1339): 133900         MOVE DFHRED             TO TRTYPCDC OF CTRTUPAO
      ws.cotrtup.ctrtupao.trtypcdc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(1340): 134000     END-IF
    }
    // COB(1342): 134200     IF TTUP-DETAILS-NOT-FETCHED
    // COB(1342): 134300     OR TTUP-DETAILS-NOT-FOUND
    // COB(1342): 134400     OR TTUP-INVALID-SEARCH-KEYS
    // COB(1342): 134500     OR FLG-TRANFILTER-BLANK
    // COB(1342): 134600     OR FLG-TRANFILTER-NOT-OK
    if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFetched()
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFound()
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupInvalidSearchKeys()
        || ws.wsMiscStorage.flgTranfilterBlank()
        || ws.wsMiscStorage.flgTranfilterNotOk()) {
      // COB(1347): 134700        GO TO 3300-SETUP-SCREEN-ATTRS-EXIT
      return;
      // COB(1348): 134800     ELSE
    } else {
      // COB(1349): 134900        CONTINUE
      // do nothing
      // COB(1350): 135000     END-IF
    }
    // COB(18): IF (FLG-(TESTVAR1)-NOT-OK
    // COB(18): OR  FLG-(TESTVAR1)-BLANK)
    // COB(18): AND CDEMO-PGM-REENTER
    if ((ws.wsMiscStorage.wsNonKeyFlags.flgDescriptionNotOk()
            || ws.wsMiscStorage.wsNonKeyFlags.flgDescriptionBlank())
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(21): MOVE DFHRED             TO
      // COB(21):      (SCRNVAR2)C OF (MAPNAME3)O
      ws.cotrtup.ctrtupao.trtydscc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(23): IF  FLG-(TESTVAR1)-BLANK
      if (ws.wsMiscStorage.wsNonKeyFlags.flgDescriptionBlank()) {
        // COB(24): MOVE '*'            TO
        // COB(24):  (SCRNVAR2)O OF (MAPNAME3)O
        ws.cotrtup.ctrtupao.trtydsco.setString("*");
        // COB(26): END-IF
      }
      // COB(27): END-IF
    }
  }

  protected void _3310ProtectAllAttrs() {
    // COB(1369): 136900     MOVE DFHBMPRF              TO TRTYPCDA OF CTRTUPAI
    // COB(1369): 137000                                   TRTYDSCA OF CTRTUPAI
    // COB(1369): 137100                                   INFOMSGA OF CTRTUPAI
    ws.cotrtup.ctrtupai.filler57.trtypcda.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.cotrtup.ctrtupai.filler63.trtydsca.setValue(Dfhbmsca.DFHBMPRF.getValue());
    ws.cotrtup.ctrtupai.filler69.infomsga.setValue(Dfhbmsca.DFHBMPRF.getValue());
  }

  protected void _3320UnprotectFewAttrs() {
    // COB(1379): 137900     MOVE DFHBMFSE              TO TRTYDSCA OF CTRTUPAI
    ws.cotrtup.ctrtupai.filler63.trtydsca.setValue(Dfhbmsca.DFHBMFSE.getValue());
    // COB(1380): 138000     MOVE DFHBMPRF              TO INFOMSGA OF CTRTUPAI
    ws.cotrtup.ctrtupai.filler69.infomsga.setValue(Dfhbmsca.DFHBMPRF.getValue());
  }

  protected void _3390SetupInfomsgAttrs() {
    // COB(1387): 138700     IF  WS-NO-INFO-MESSAGE
    if (ws.wsMiscStorage.wsNoInfoMessage()) {
      // COB(1388): 138800         MOVE DFHBMDAR           TO INFOMSGA OF CTRTUPAI
      ws.cotrtup.ctrtupai.filler69.infomsga.setValue(Dfhbmsca.DFHBMDAR.getValue());
      // COB(1389): 138900     ELSE
    } else {
      // COB(1390): 139000         MOVE DFHBMASB           TO INFOMSGA OF CTRTUPAI
      ws.cotrtup.ctrtupai.filler69.infomsga.setValue(Dfhbmsca.DFHBMASB.getValue());
      // COB(1391): 139100     END-IF
    }
  }

  protected void _3391SetupPfkeyAttrs() {
    // COB(1400): 140000     IF TTUP-CONFIRM-DELETE
    //     Should reflect in 0001-CHECK-PFKEYS
    //     Enter key
    if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupConfirmDelete()) {
      // COB(1401): 140100        MOVE DFHBMDAR            TO FKEYSA   OF CTRTUPAI
      ws.cotrtup.ctrtupai.filler81.fkeysa.setValue(Dfhbmsca.DFHBMDAR.getValue());
      // COB(1402): 140200     ELSE
    } else {
      // COB(1403): 140300        MOVE DFHBMASB            TO FKEYSA   OF CTRTUPAI
      ws.cotrtup.ctrtupai.filler81.fkeysa.setValue(Dfhbmsca.DFHBMASB.getValue());
      // COB(1404): 140400     END-IF
    }
    // COB(1406): 140600     IF TTUP-SHOW-DETAILS
    // COB(1406): 140700     OR TTUP-CONFIRM-DELETE
    //     F4
    if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupShowDetails()
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupConfirmDelete()) {
      // COB(1408): 140800         MOVE DFHBMASB           TO FKEY04A  OF CTRTUPAI
      ws.cotrtup.ctrtupai.filler87.fkey04a.setValue(Dfhbmsca.DFHBMASB.getValue());
      // COB(1409): 140900     END-IF
    }
    // COB(1411): 141100     IF TTUP-CHANGES-OK-NOT-CONFIRMED
    // COB(1411): 141200     OR TTUP-DETAILS-NOT-FOUND
    //     F5
    if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkNotConfirmed()
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFound()) {
      // COB(1413): 141300         MOVE DFHBMASB           TO FKEY05A  OF CTRTUPAI
      ws.cotrtup.ctrtupai.filler93.fkey05a.setValue(Dfhbmsca.DFHBMASB.getValue());
      // COB(1414): 141400     END-IF
    }
    // COB(1416): 141600     IF TTUP-CHANGES-OK-NOT-CONFIRMED
    // COB(1416): 141700     OR TTUP-SHOW-DETAILS
    // COB(1416): 141800     OR TTUP-DETAILS-NOT-FOUND
    // COB(1416): 141900     OR TTUP-CONFIRM-DELETE
    // COB(1416): 142000     OR TTUP-CREATE-NEW-RECORD
    //     F12
    if (ws.wsThisProgcommarea.ttupUpdateScreenData.ttupChangesOkNotConfirmed()
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupShowDetails()
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupDetailsNotFound()
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupConfirmDelete()
        || ws.wsThisProgcommarea.ttupUpdateScreenData.ttupCreateNewRecord()) {
      // COB(1421): 142100         MOVE DFHBMASB           TO FKEY12A  OF CTRTUPAI
      ws.cotrtup.ctrtupai.filler105.fkey12a.setValue(Dfhbmsca.DFHBMASB.getValue());
      // COB(1422): 142200     END-IF
    }
  }

  protected void _3400SendScreen() {
    // COB(1430): 143000     MOVE LIT-THISMAPSET         TO CCARD-NEXT-MAPSET
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsLiterals.litThismapset);
    // COB(1431): 143100     MOVE LIT-THISMAP            TO CCARD-NEXT-MAP
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsLiterals.litThismap);
    // COB(1433): 143300     EXEC CICS SEND MAP(CCARD-NEXT-MAP)
    // COB(1433): 143400                    MAPSET(CCARD-NEXT-MAPSET)
    // COB(1433): 143500                    FROM(CTRTUPAO)
    // COB(1433): 143600                    CURSOR
    // COB(1433): 143700                    ERASE
    // COB(1433): 143800                    FREEKB
    // COB(1433): 143900                    RESP(WS-RESP-CD)
    // COB(1433): 144000     END-EXEC
    supernaut
        .sendMap(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.getValue()) //
        .mapset(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.getValue()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .from(ws.cotrtup.ctrtupao) //
        .freekb() //
        .erase() //
        .cursor() //
        .exec();
  }

  protected void _9000ReadTrantype() {
    // COB(1449): 144900     INITIALIZE TTUP-OLD-DETAILS
    ws.wsThisProgcommarea.ttupOldDetails.initialize();
    // COB(1451): 145100     SET  WS-NO-INFO-MESSAGE      TO TRUE
    ws.wsMiscStorage.setWsNoInfoMessage(true);
    // COB(1453): 145300     PERFORM 9100-GET-TRANSACTION-TYPE
    // COB(1453): 145400        THRU 9100-GET-TRANSACTION-TYPE-EXIT
    _9100GetTransactionType();
    // COB(1456): 145600     IF FLG-TRANFILTER-NOT-OK
    if (ws.wsMiscStorage.flgTranfilterNotOk()) {
      // COB(1457): 145700        GO TO 9000-READ-TRANTYPE-EXIT
      return;
      // COB(1458): 145800     END-IF
    }
    // COB(1461): 146100     PERFORM 9500-STORE-FETCHED-DATA
    // COB(1461): 146200        THRU 9500-STORE-FETCHED-DATA-EXIT
    _9500StoreFetchedData();
  }

  protected void _9100GetTransactionType() {
    // COB(1473): 147300     MOVE TTUP-NEW-TTYP-TYPE TO DCL-TR-TYPE
    //     Read the Card file. Access via alternate index ACCTID
    //
    ws.dcltrtyp.dcltransactionType.dclTrType.setValue(
        ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypType);
    // COB(1475): 147500     EXEC SQL
    // COB(1475): 147600          SELECT TR_TYPE
    // COB(1475): 147700                ,TR_DESCRIPTION
    // COB(1475): 147800            INTO :DCL-TR-TYPE
    // COB(1475): 147900                ,:DCL-TR-DESCRIPTION
    // COB(1475): 148000            FROM CARDDEMO.TRANSACTION_TYPE
    // COB(1475): 148100           WHERE TR_TYPE = :DCL-TR-TYPE
    // COB(1475): 148200     END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand(
          "SELECT TR_TYPE, TR_DESCRIPTION FROM CARDDEMO.TRANSACTION_TYPE WHERE TR_TYPE = ?");
      sql.into
          .add(ws.dcltrtyp.dcltransactionType.dclTrType) //
          .add(ws.dcltrtyp.dcltransactionType.dclTrDescription);
      sql.where.add(ws.dcltrtyp.dcltransactionType.dclTrType);
      sql.execute();
    }
    // COB(1484): 148400     MOVE SQLCODE                            TO WS-DISP-SQLCODE
    ws.wsMiscStorage.wsMiscVars.wsDispSqlcode.setValue(sqlca.sqlcode);
    // COB(1486): 148600     EVALUATE TRUE
    // COB(1487): 148700         WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(1488): 148800            SET FOUND-TRANTYPE-IN-TABLE     TO TRUE
      ws.wsMiscStorage.wsTableReadFlags.setFoundTrantypeInTable(true);
      // COB(1489): 148900         WHEN SQLCODE = +100
    } else if (sqlca.sqlcode.equals(100)) {
      // COB(1490): 149000            SET INPUT-ERROR                 TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1491): 149100            SET FLG-TRANFILTER-NOT-OK       TO TRUE
      ws.wsMiscStorage.setFlgTranfilterNotOk(true);
      // COB(1492): 149200            IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1493): 149300              SET WS-RECORD-NOT-FOUND       TO TRUE
        ws.wsMiscStorage.setWsRecordNotFound(true);
        // COB(1494): 149400            END-IF
      }
      // COB(1495): 149500         WHEN SQLCODE < 0
    } else if (sqlca.sqlcode.lessThan(0)) {
      // COB(1496): 149600            SET INPUT-ERROR                 TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1497): 149700            SET FLG-TRANFILTER-NOT-OK       TO TRUE
      ws.wsMiscStorage.setFlgTranfilterNotOk(true);
      // COB(1498): 149800            IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1499): 149900              STRING
        // COB(1499): 150000              'Error accessing:'
        // COB(1499): 150100              ' TRANSACTION_TYPE table. SQLCODE:'
        // COB(1499): 150200              WS-DISP-SQLCODE
        // COB(1499): 150300              ':'
        // COB(1499): 150400              SQLERRM OF SQLCA
        // COB(1499): 150500              DELIMITED BY SIZE
        // COB(1499): 150600              INTO WS-RETURN-MSG
        // COB(1499): 150700              END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(
                (Object) "Error accessing:",
            " TRANSACTION_TYPE table. SQLCODE:",
            ws.wsMiscStorage.wsMiscVars.wsDispSqlcode,
            ":",
            sqlca.sqlerrmc.getString(
                    0,
                    sqlca.sqlerrml.getInt() )
            );
        // COB(1507): 150700              END-STRING
        // COB(1508): 150800            END-IF
      }
      // COB(1509): 150900     END-EVALUATE
    }
    // COB(1510): 151000     EXIT
    return;
  }

  protected void _9500StoreFetchedData() {
    // COB(1519): 151900     INITIALIZE TTUP-OLD-DETAILS
    ws.wsThisProgcommarea.ttupOldDetails.initialize();
    // COB(1523): 152300     MOVE DCL-TR-TYPE         TO TTUP-OLD-TTYP-TYPE
    // *****************************************************************
    //     Transaction Type data
    // *****************************************************************
    ws.wsThisProgcommarea.ttupOldDetails.ttupOldTtypData.ttupOldTtypType.setValue(
        ws.dcltrtyp.dcltransactionType.dclTrType);
    // COB(1524): 152400     MOVE DCL-TR-DESCRIPTION-TEXT(1: DCL-TR-DESCRIPTION-LEN)
    // COB(1524): 152500                              TO TTUP-OLD-TTYP-TYPE-DESC
    ws.wsThisProgcommarea.ttupOldDetails.ttupOldTtypData.ttupOldTtypTypeDesc.setValue(
        ws.dcltrtyp.dcltransactionType.dclTrDescription.dclTrDescriptionText,
        0,
        ws.dcltrtyp.dcltransactionType.dclTrDescription.dclTrDescriptionLen.getInt());
  }

  protected void _9600WriteProcessing() {
    // COB(1538): 153800     MOVE TTUP-NEW-TTYP-TYPE TO DCL-TR-TYPE
    // ****************************************************************
    //  Update Transaction Type *
    // ****************************************************************
    //     Issue Update
    //
    ws.dcltrtyp.dcltransactionType.dclTrType.setValue(
        ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypType);
    // COB(1539): 153900     MOVE FUNCTION TRIM(TTUP-NEW-TTYP-TYPE-DESC)
    // COB(1539): 154000                             TO DCL-TR-DESCRIPTION-TEXT
    ws.dcltrtyp.dcltransactionType.dclTrDescription.dclTrDescriptionText.setValue(
        ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypTypeDesc.trim());
    // COB(1541): 154100     COMPUTE DCL-TR-DESCRIPTION-LEN
    // COB(1541): 154200      = FUNCTION LENGTH(TTUP-NEW-TTYP-TYPE-DESC)
    ws.dcltrtyp.dcltransactionType.dclTrDescription.dclTrDescriptionLen.setValue(
        ws.wsThisProgcommarea.ttupNewDetails.ttupNewTtypData.ttupNewTtypTypeDesc.length());
    // COB(1544): 154400     EXEC SQL
    // COB(1544): 154500          UPDATE CARDDEMO.TRANSACTION_TYPE
    // COB(1544): 154600             SET TR_DESCRIPTION = :DCL-TR-DESCRIPTION
    // COB(1544): 154700           WHERE TR_TYPE = :DCL-TR-TYPE
    // COB(1544): 154800     END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand("UPDATE CARDDEMO.TRANSACTION_TYPE SET TR_DESCRIPTION = ? WHERE TR_TYPE = ?");
      sql.set.add(ws.dcltrtyp.dcltransactionType.dclTrDescription);
      sql.where.add(ws.dcltrtyp.dcltransactionType.dclTrType);
      sql.execute();
    }
    // COB(1553): 155300     MOVE SQLCODE                       TO WS-DISP-SQLCODE
    // ****************************************************************
    //  Did Transaction Type update succeed ?  *
    // ****************************************************************
    ws.wsMiscStorage.wsMiscVars.wsDispSqlcode.setValue(sqlca.sqlcode);
    // COB(1555): 155500     EVALUATE TRUE
    // COB(1556): 155600         WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(1557): 155700            EXEC CICS SYNCPOINT END-EXEC
      try {
        supernaut
            .syncpoint() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(1558): 155800         WHEN SQLCODE = +100
    } else if (sqlca.sqlcode.equals(100)) {
      // COB(1559): 155900            PERFORM 9700-INSERT-RECORD
      // COB(1559): 156000               THRU 9700-INSERT-RECORD-EXIT
      _9700InsertRecord();
      // COB(1561): 156100         WHEN SQLCODE = -911
    } else if (sqlca.sqlcode.equals(-911)) {
      // COB(1562): 156200            SET INPUT-ERROR                    TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1563): 156300            IF  WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(1564): 156400                SET COULD-NOT-LOCK-REC-FOR-UPDATE
        // COB(1564): 156500                                               TO TRUE
        ws.wsMiscStorage.setCouldNotLockRecForUpdate(true);
        // COB(1566): 156600            END-IF
      }
      // COB(1567): 156700         WHEN SQLCODE < 0
    } else if (sqlca.sqlcode.lessThan(0)) {
      // COB(1568): 156800            SET TABLE-UPDATE-FAILED            TO TRUE
      ws.wsMiscStorage.setTableUpdateFailed(true);
      // COB(1569): 156900              STRING
      // COB(1569): 157000              'Error updating:'
      // COB(1569): 157100              ' TRANSACTION_TYPE Table. SQLCODE:'
      // COB(1569): 157200              WS-DISP-SQLCODE
      // COB(1569): 157300              ':'
      // COB(1569): 157400              SQLERRM OF SQLCA
      // COB(1569): 157500              DELIMITED BY SIZE
      // COB(1569): 157600              INTO WS-RETURN-MSG
      // COB(1569): 157700              END-STRING
      ws.wsMiscStorage.wsReturnMsg.concat(
          "Error updating:",
          " TRANSACTION_TYPE Table. SQLCODE:",
          ws.wsMiscStorage.wsMiscVars.wsDispSqlcode,
          ":",
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(1577): 157700              END-STRING
      // COB(1578): 157800     END-EVALUATE
    }
    // COB(1580): 158000     EVALUATE TRUE
    // COB(1581): 158100        WHEN COULD-NOT-LOCK-REC-FOR-UPDATE
    if (ws.wsMiscStorage.couldNotLockRecForUpdate()) {
      // COB(1582): 158200             SET TTUP-CHANGES-OKAYED-LOCK-ERROR TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupChangesOkayedLockError(true);
      // COB(1583): 158300        WHEN TABLE-UPDATE-FAILED
    } else if (ws.wsMiscStorage.tableUpdateFailed()) {
      // COB(1584): 158400             SET TTUP-CHANGES-OKAYED-BUT-FAILED TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupChangesOkayedButFailed(true);
      // COB(1585): 158500        WHEN DATA-WAS-CHANGED-BEFORE-UPDATE
    } else if (ws.wsMiscStorage.dataWasChangedBeforeUpdate()) {
      // COB(1586): 158600             SET TTUP-SHOW-DETAILS              TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupShowDetails(true);
      // COB(1587): 158700        WHEN OTHER
    } else {
      // COB(1588): 158800           SET TTUP-CHANGES-OKAYED-AND-DONE     TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupChangesOkayedAndDone(true);
      // COB(1589): 158900     END-EVALUATE
    }
    // COB(1591): 159100     EXIT
    return;
  }

  protected void _9700InsertRecord() {
    // COB(1597): 159700     EXEC SQL
    // COB(1597): 159800          INSERT INTO CARDDEMO.TRANSACTION_TYPE
    // COB(1597): 159900                     (TR_TYPE, TR_DESCRIPTION)
    // COB(1597): 160000             VALUES ( :DCL-TR-TYPE
    // COB(1597): 160100                     ,:DCL-TR-DESCRIPTION)
    // COB(1597): 160200     END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand(
          "INSERT INTO CARDDEMO.TRANSACTION_TYPE (TR_TYPE , TR_DESCRIPTION) VALUES (?, ?)");
      sql.values
          .add(ws.dcltrtyp.dcltransactionType.dclTrType) //
          .add(ws.dcltrtyp.dcltransactionType.dclTrDescription);
      sql.execute();
    }
    // COB(1604): 160400     EVALUATE TRUE
    // COB(1605): 160500         WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(1606): 160600            EXEC CICS SYNCPOINT END-EXEC
      try {
        supernaut
            .syncpoint() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(1607): 160700         WHEN OTHER
    } else {
      // COB(1608): 160800            SET TABLE-UPDATE-FAILED            TO TRUE
      ws.wsMiscStorage.setTableUpdateFailed(true);
      // COB(1609): 160900              STRING
      // COB(1609): 161000              'Error inserting record into:'
      // COB(1609): 161100              ' TRANSACTION_TYPE Table. SQLCODE:'
      // COB(1609): 161200              WS-DISP-SQLCODE
      // COB(1609): 161300              ':'
      // COB(1609): 161400              SQLERRM OF SQLCA
      // COB(1609): 161500              DELIMITED BY SIZE
      // COB(1609): 161600              INTO WS-RETURN-MSG
      // COB(1609): 161700              END-STRING
      ws.wsMiscStorage.wsReturnMsg.concat(
          "Error inserting record into:",
          " TRANSACTION_TYPE Table. SQLCODE:",
          ws.wsMiscStorage.wsMiscVars.wsDispSqlcode,
          ":",
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(1617): 161700              END-STRING
      // COB(1618): 161800            GO TO 9700-INSERT-RECORD-EXIT
      return;
      // COB(1619): 161900     END-EVALUATE
    }
  }

  protected void _9800DeleteProcessing() {
    // COB(1625): 162500     MOVE TTUP-OLD-TTYP-TYPE TO DCL-TR-TYPE
    ws.dcltrtyp.dcltransactionType.dclTrType.setValue(
        ws.wsThisProgcommarea.ttupOldDetails.ttupOldTtypData.ttupOldTtypType);
    // COB(1627): 162700     EXEC SQL
    // COB(1627): 162800          DELETE FROM CARDDEMO.TRANSACTION_TYPE
    // COB(1627): 162900           WHERE TR_TYPE = :DCL-TR-TYPE
    // COB(1627): 163000     END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand("DELETE FROM CARDDEMO.TRANSACTION_TYPE WHERE TR_TYPE = ?");
      sql.where.add(ws.dcltrtyp.dcltransactionType.dclTrType);
      sql.execute();
    }
    // COB(1632): 163200     MOVE SQLCODE                             TO WS-DISP-SQLCODE
    ws.wsMiscStorage.wsMiscVars.wsDispSqlcode.setValue(sqlca.sqlcode);
    // COB(1634): 163400     EVALUATE TRUE
    // COB(1635): 163500         WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(1636): 163600            SET TTUP-DELETE-DONE              TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupDeleteDone(true);
      // COB(1637): 163700            EXEC CICS SYNCPOINT END-EXEC
      try {
        supernaut
            .syncpoint() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(1638): 163800         WHEN SQLCODE = -532
    } else if (sqlca.sqlcode.equals(-532)) {
      // COB(1639): 163900            SET RECORD-DELETE-FAILED          TO TRUE
      ws.wsMiscStorage.setRecordDeleteFailed(true);
      // COB(1640): 164000              STRING
      // COB(1640): 164100              'Please delete associated child records first:'
      // COB(1640): 164200              'SQLCODE :'
      // COB(1640): 164300              WS-DISP-SQLCODE
      // COB(1640): 164400              ':'
      // COB(1640): 164500              SQLERRM OF SQLCA
      // COB(1640): 164600              SQLERRM OF SQLCA
      // COB(1640): 164700              DELIMITED BY SIZE
      // COB(1640): 164800              INTO WS-RETURN-MSG
      // COB(1640): 164900              END-STRING
      ws.wsMiscStorage.wsReturnMsg.concat(
          "Please delete associated child records first:",
          "SQLCODE :",
          ws.wsMiscStorage.wsMiscVars.wsDispSqlcode,
          ":",
              sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()),
              sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(1649): 164900              END-STRING
      // COB(1650): 165000         WHEN OTHER
    } else {
      // COB(1651): 165100            SET RECORD-DELETE-FAILED          TO TRUE
      ws.wsMiscStorage.setRecordDeleteFailed(true);
      // COB(1652): 165200            SET TTUP-DELETE-FAILED            TO TRUE
      ws.wsThisProgcommarea.ttupUpdateScreenData.setTtupDeleteFailed(true);
      // COB(1653): 165300              STRING
      // COB(1653): 165400              'Delete failed with message:'
      // COB(1653): 165500              'SQLCODE :'
      // COB(1653): 165600              WS-DISP-SQLCODE
      // COB(1653): 165700              ':'
      // COB(1653): 165800              SQLERRM OF SQLCA
      // COB(1653): 165900              DELIMITED BY SIZE
      // COB(1653): 166000              INTO WS-RETURN-MSG
      // COB(1653): 166100              END-STRING
      ws.wsMiscStorage.wsReturnMsg.concat(
          "Delete failed with message:",
          "SQLCODE :",
          ws.wsMiscStorage.wsMiscVars.wsDispSqlcode,
          ":",
              sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(1661): 166100              END-STRING
      // COB(1662): 166200     END-EVALUATE
    }
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
    // COB(1677): 167700     IF ABEND-MSG EQUAL LOW-VALUES
    if (ws.csmsg02y.abendData.abendMsg.isLowValues()) {
      // COB(1678): 167800        MOVE 'UNEXPECTED ABEND OCCURRED.' TO ABEND-MSG
      ws.csmsg02y.abendData.abendMsg.setString("UNEXPECTED ABEND OCCURRED.");
      // COB(1679): 167900     END-IF
    }
    // COB(1681): 168100     MOVE LIT-THISPGM       TO ABEND-CULPRIT
    ws.csmsg02y.abendData.abendCulprit.setValue(ws.wsLiterals.litThispgm);
    // COB(1682): 168200     MOVE '9999'            TO ABEND-CODE
    ws.csmsg02y.abendData.abendCode.setString("9999");
    // COB(1684): 168400     EXEC CICS SEND
    // COB(1684): 168500                      FROM (ABEND-DATA)
    // COB(1684): 168600                      LENGTH(LENGTH OF ABEND-DATA)
    // COB(1684): 168700                      NOHANDLE
    // COB(1684): 168800                      ERASE
    // COB(1684): 168900     END-EXEC
    supernaut
        .send() //
        .length(ws.csmsg02y.abendData.length()) //
        .nohandle() //
        .from(ws.csmsg02y.abendData) //
        .erase() //
        .exec();
    // COB(1691): 169100     EXEC CICS HANDLE ABEND
    // COB(1691): 169200          CANCEL
    // COB(1691): 169300     END-EXEC
    supernaut
        .handleAbend() //
        .cancel() //
        .exec();
    // COB(1695): 169500     EXEC CICS ABEND
    // COB(1695): 169600          ABCODE(ABEND-CODE)
    // COB(1695): 169700     END-EXEC
    supernaut
        .abend() //
        .abcode(ws.csmsg02y.abendData.abendCode.getValue()) //
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
