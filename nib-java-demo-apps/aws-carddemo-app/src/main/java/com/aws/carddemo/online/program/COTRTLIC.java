package com.aws.carddemo.online.program;

import com.nib.commons.*;
import com.nib.commons.storage.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.values.*;
import com.nib.supernaut.exceptions.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import com.nib.commons.esql.*;
import java.sql.Types;

import com.aws.carddemo.online.program.storage.cotrtlic.*;

public class COTRTLIC extends AbstractOnlineProgram {

  protected enum Flow
  {
    Exit,
    // 0000-MAIN
    _0000Main,
    // 1220-EDIT-TYPECD
    _1220EditTypecd,
    // 1220-EDIT-TYPECD-EXIT
    _1220EditTypecdExit,
    // 1230-EDIT-DESC
    _1230EditDesc,
    // 1230-EDIT-DESC-EXIT
    _1230EditDescExit,
    // 8100-READ-BACKWARDS
    _8100ReadBackwards,
    // 8100-READ-BACKWARDS-EXIT
    _8100ReadBackwardsExit,
    // COMMON-RETURN
    commonReturn
  }

  private Flow rcNext;

  private final Sqlca sqlca = new Sqlca(this);
  private NSqlCursor cTrTypeBackward;
  private NSqlCursor cTrTypeForward;
  @ProgramStorage
  final CotrtlicWorking ws = new CotrtlicWorking();

  //Linkage
  public static class CotrtlicLinkage extends NGroup {
        //COB: 049300 01  DFHCOMMAREA.
    public Dfhcommarea dfhcommarea = new Dfhcommarea();

public static class Dfhcommarea extends NGroup {

          //COB: 049400   05  FILLER                                PIC X(1)
          //COB: 049500       OCCURS 1 TO 32767 TIMES DEPENDING ON EIBCALEN.
        public NChar[] filler494 = AbstractNField.occurs(32767, new NChar(1));
        public NChar filler494AtIndex(int index) {
          return getOccursInstance(filler494, index);
        }
}
}

  final CotrtlicLinkage params = new CotrtlicLinkage();

  public COTRTLIC(Context supernaut) {
    super(supernaut);
    cTrTypeBackward = new NSqlCursor(this,sqlca, "SELECT TR_TYPE, TR_DESCRIPTION FROM CARDDEMO.TRANSACTION_TYPE WHERE TR_TYPE < ? AND ((? = '1' AND TR_TYPE = ?) OR (? <> '1')) AND ((? = '1' AND TR_DESCRIPTION LIKE TRIM (?)) OR (? <> '1')) ORDER BY TR_TYPE DESC");
    cTrTypeBackward.where.add(ws.wsMiscStorage.wsDataFilters.wsStartKey) // 
.add(ws.wsMiscStorage.wsEditTypeFlag) // 
.add(ws.wsMiscStorage.wsDataFilters.wsTypeCdFilter) // 
.add(ws.wsMiscStorage.wsEditTypeFlag) // 
.add(ws.wsMiscStorage.wsEditDescFlag) // 
.add(ws.wsMiscStorage.wsDataFilters.wsTypeDescFilter) // 
.add(ws.wsMiscStorage.wsEditDescFlag);
    cTrTypeForward = new NSqlCursor(this,sqlca, "SELECT TR_TYPE, TR_DESCRIPTION FROM CARDDEMO.TRANSACTION_TYPE WHERE TR_TYPE >= ? AND ((? = '1' AND TR_TYPE = ?) OR (? <> '1')) AND ((? = '1' AND TR_DESCRIPTION LIKE TRIM (?)) OR (? <> '1')) ORDER BY TR_TYPE");
    cTrTypeForward.where.add(ws.wsMiscStorage.wsDataFilters.wsStartKey) // 
.add(ws.wsMiscStorage.wsEditTypeFlag) // 
.add(ws.wsMiscStorage.wsDataFilters.wsTypeCdFilter) // 
.add(ws.wsMiscStorage.wsEditTypeFlag) // 
.add(ws.wsMiscStorage.wsEditDescFlag) // 
.add(ws.wsMiscStorage.wsDataFilters.wsTypeDescFilter) // 
.add(ws.wsMiscStorage.wsEditDescFlag);
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
    //COB(500): 050000     INITIALIZE CC-WORK-AREA
    //COB(500): 050100                WS-MISC-STORAGE
    //COB(500): 050200                WS-COMMAREA
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.initialize();
    ws.wsMiscStorage.initialize();
    ws.wsCommarea.initialize();
    //COB(507): 050700     MOVE LIT-THISTRANID       TO WS-TRANID
  // 
  // ****************************************************************
  //  Store our context
  // ****************************************************************
    ws.wsMiscStorage.wsCicsProcessngVars.wsTranid.setValue(ws.wsConstants.litThistranid);
    //COB(511): 051100     SET WS-RETURN-MSG-OFF  TO TRUE
  // ****************************************************************
  //  Ensure error message is cleared                               *
  // ****************************************************************
    ws.wsMiscStorage.setWsReturnMsgOff(true);
    //COB(515): 051500     IF EIBCALEN = 0
  // ****************************************************************
  //  Retrieve passed data if  any. Initialize them if first run.
  // ****************************************************************
    if (dfheiblk.getEibcalen() == 0) {
      //COB(516): 051600        INITIALIZE CARDDEMO-COMMAREA
      //COB(516): 051700                   WS-THIS-PROGCOMMAREA
      ws.cocom01y.carddemoCommarea.initialize();
      ws.wsThisProgcommarea.initialize();
      //COB(518): 051800        MOVE LIT-THISTRANID        TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsConstants.litThistranid);
      //COB(519): 051900        MOVE LIT-THISPGM           TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsConstants.litThispgm);
      //COB(520): 052000        SET CDEMO-USRTYP-ADMIN     TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypAdmin(true);
      //COB(521): 052100        SET CDEMO-PGM-ENTER        TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      //COB(522): 052200        MOVE LIT-THISMAP           TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      //COB(523): 052300        MOVE LIT-THISMAPSET        TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(ws.wsConstants.litThismapset);
      //COB(524): 052400        SET CA-FIRST-PAGE          TO TRUE
      ws.wsThisProgcommarea.wsCaPagingVariables.setCaFirstPage(true);
      //COB(525): 052500        SET CA-LAST-PAGE-NOT-SHOWN TO TRUE
      ws.wsThisProgcommarea.wsCaPagingVariables.setCaLastPageNotShown(true);
      //COB(526): 052600     ELSE
    } else {
      //COB(527): 052700        MOVE DFHCOMMAREA (1:LENGTH OF CARDDEMO-COMMAREA) TO
      //COB(527): 052800                          CARDDEMO-COMMAREA
      ws.cocom01y.carddemoCommarea.setValue(params.dfhcommarea, 0, ws.cocom01y.carddemoCommarea.length());
      //COB(529): 052900        MOVE DFHCOMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
      //COB(529): 053000                         LENGTH OF WS-THIS-PROGCOMMAREA )TO
      //COB(529): 053100                          WS-THIS-PROGCOMMAREA
      ws.wsThisProgcommarea.setValue(params.dfhcommarea, ws.cocom01y.carddemoCommarea.length(), 0, ws.wsThisProgcommarea.length());
    //COB(532): 053200     END-IF
    }
    //COB(538): 053800     PERFORM YYYY-STORE-PFKEY
    //COB(538): 053900        THRU YYYY-STORE-PFKEY-EXIT
  // 
  // *****************************************************************
  //  Remap PFkeys as needed.
  //  Store the Mapped PF Key
  // ****************************************************************
    yyyyStorePfkey();
    //COB(544): 054400     IF (CDEMO-PGM-ENTER
    //COB(544): 054500     AND CDEMO-FROM-PROGRAM NOT EQUAL LIT-THISPGM)
    //COB(544): 054600     OR ( CCARD-AID-PFK03
    //COB(544): 054700     AND CDEMO-FROM-TRANID  EQUAL LIT-ADDTTRANID)
  // 
  // ****************************************************************
  //  If coming in from menu. Lets forget the past and start afresh *
  // ****************************************************************
    if ((ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()
        && !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(ws.wsConstants.litThispgm)
        )
        ||
        (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.equals(ws.wsConstants.litAddttranid)
        )) {
      //COB(548): 054800         INITIALIZE WS-THIS-PROGCOMMAREA
      ws.wsThisProgcommarea.initialize();
      //COB(549): 054900         SET CDEMO-PGM-ENTER      TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      //COB(550): 055000         SET CCARD-AID-ENTER      TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidEnter(true);
      //COB(551): 055100         MOVE LIT-THISMAP         TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      //COB(552): 055200         SET CA-FIRST-PAGE        TO TRUE
      ws.wsThisProgcommarea.wsCaPagingVariables.setCaFirstPage(true);
      //COB(553): 055300         SET CA-LAST-PAGE-NOT-SHOWN TO TRUE
      ws.wsThisProgcommarea.wsCaPagingVariables.setCaLastPageNotShown(true);
    //COB(554): 055400     END-IF
    }
    //COB(561): 056100     IF  EIBCALEN > 0
    //COB(561): 056200     AND CDEMO-FROM-PROGRAM  EQUAL LIT-THISPGM
  // 
  // *****************************************************************
  //  If something is present in commarea
  //  and the from program is this program itself,
  //  read and edit the inputs given
  // ****************************************************************
    if (dfheiblk.getEibcalen() > 0
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(ws.wsConstants.litThispgm)) {
      //COB(563): 056300         PERFORM 1000-RECEIVE-MAP
      //COB(563): 056400         THRU    1000-RECEIVE-MAP-EXIT
      _1000ReceiveMap();
    //COB(566): 056600     END-IF
    }
    //COB(574): 057400     SET PFK-INVALID TO TRUE
  // ****************************************************************
  //  Check the mapped key  to see if its valid at this point       *
  //  F3    - Exit
  //  Enter - List of cards for current start key
  //  F8    - Page down
  //  F7    - Page up
  // ****************************************************************
    ws.wsMiscStorage.setPfkInvalid(true);
    //COB(575): 057500     IF CCARD-AID-ENTER OR
    //COB(575): 057600        CCARD-AID-PFK02 OR
    //COB(575): 057700        CCARD-AID-PFK03 OR
    //COB(575): 057800        CCARD-AID-PFK07 OR
    //COB(575): 057900        CCARD-AID-PFK08 OR
    //COB(575): 058000       (CCARD-AID-PFK10 AND CA-DELETE-REQUESTED) OR
    //COB(575): 058100       (CCARD-AID-PFK10 AND CA-UPDATE-REQUESTED)
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidEnter()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk02()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk07()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk08()
        ||
        (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk10()
        && ws.wsThisProgcommarea.caDeleteRequested()
        )
        ||
        (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk10()
        && ws.wsThisProgcommarea.caUpdateRequested()
        )) {
      //COB(582): 058200        SET PFK-VALID TO TRUE
      ws.wsMiscStorage.setPfkValid(true);
    //COB(583): 058300     END-IF
    }
    //COB(585): 058500     IF PFK-INVALID
    if (ws.wsMiscStorage.pfkInvalid()) {
      //COB(586): 058600        SET CCARD-AID-ENTER TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidEnter(true);
    //COB(587): 058700     END-IF
    }
    //COB(591): 059100     IF CCARD-AID-PFK03
  // ****************************************************************
  //  If the user pressed PF3 go back to main menu
  // ****************************************************************
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()) {
      //COB(592): 059200        IF CDEMO-FROM-TRANID     EQUAL LOW-VALUES
      //COB(592): 059300        OR CDEMO-FROM-TRANID     EQUAL SPACES
      //COB(592): 059400        OR CDEMO-FROM-TRANID     EQUAL LIT-THISTRANID
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isSpaces()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.equals(ws.wsConstants.litThistranid)) {
        //COB(595): 059500           MOVE LIT-ADMINTRANID   TO CDEMO-TO-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToTranid.setValue(ws.wsConstants.litAdmintranid);
        //COB(596): 059600        ELSE
      } else {
        //COB(597): 059700           MOVE CDEMO-FROM-TRANID TO CDEMO-TO-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToTranid.setValue(ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid);
      //COB(598): 059800        END-IF
      }
      //COB(600): 060000        IF CDEMO-FROM-PROGRAM   EQUAL LOW-VALUES
      //COB(600): 060100        OR CDEMO-FROM-PROGRAM   EQUAL SPACES
      //COB(600): 060200        OR CDEMO-FROM-PROGRAM   EQUAL LIT-THISPGM
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isSpaces()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(ws.wsConstants.litThispgm)) {
        //COB(603): 060300           MOVE LIT-ADMINPGM       TO CDEMO-TO-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(ws.wsConstants.litAdminpgm);
        //COB(604): 060400        ELSE
      } else {
        //COB(605): 060500           MOVE CDEMO-FROM-PROGRAM TO CDEMO-TO-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram);
      //COB(606): 060600        END-IF
      }
      //COB(608): 060800        MOVE LIT-THISTRANID     TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsConstants.litThistranid);
      //COB(609): 060900        MOVE LIT-THISPGM        TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsConstants.litThispgm);
      //COB(611): 061100        SET  CDEMO-USRTYP-ADMIN TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypAdmin(true);
      //COB(612): 061200        SET  CDEMO-PGM-ENTER    TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      //COB(613): 061300        MOVE LIT-THISMAPSET     TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(ws.wsConstants.litThismapset);
      //COB(614): 061400        MOVE LIT-THISMAP        TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      //COB(616): 061600        EXEC CICS
      //COB(616): 061700             SYNCPOINT
      //COB(616): 061800        END-EXEC
      try {
        supernaut.syncpoint() //
                .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      //COB(620): 062000        EXEC CICS XCTL
      //COB(620): 062100             PROGRAM (CDEMO-TO-PROGRAM)
      //COB(620): 062200             COMMAREA(CARDDEMO-COMMAREA)
      //COB(620): 062300        END-EXEC
      try {
        supernaut.xctl(ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
                .commarea(ws.cocom01y.carddemoCommarea) //
                .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
    //COB(625): 062500     END-IF
    }
    //COB(630): 063000     IF  (CCARD-AID-PFK02
    //COB(630): 063100     AND CDEMO-FROM-PROGRAM  EQUAL LIT-THISPGM)
  // 
  // ****************************************************************
  //  If the user pressed PF2 transfer to add screen
  // ****************************************************************
    if ((ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk02()
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(ws.wsConstants.litThispgm)
        )) {
      //COB(632): 063200        MOVE LIT-THISTRANID   TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsConstants.litThistranid);
      //COB(633): 063300        MOVE LIT-THISPGM      TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsConstants.litThispgm);
      //COB(634): 063400        SET  CDEMO-USRTYP-USER TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypUser(true);
      //COB(635): 063500        SET  CDEMO-PGM-ENTER  TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      //COB(636): 063600        MOVE LIT-THISMAPSET   TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(ws.wsConstants.litThismapset);
      //COB(637): 063700        MOVE LIT-THISMAP      TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      //COB(638): 063800        MOVE LIT-ADDTPGM      TO CDEMO-TO-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(ws.wsConstants.litAddtpgm);
      //COB(640): 064000        MOVE LIT-ADDTMAPSET   TO CCARD-NEXT-MAPSET
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsConstants.litAddtmapset);
      //COB(641): 064100        MOVE LIT-ADDTMAP      TO CCARD-NEXT-MAP
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsConstants.litAddtmap);
      //COB(642): 064200        SET WS-EXIT-MESSAGE            TO TRUE
      ws.wsMiscStorage.setWsExitMessage(true);
      //COB(646): 064600        SET CDEMO-PGM-ENTER   TO TRUE
  // 
  //        CALL MENU PROGRAM
  // 
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      //COB(648): 064800        EXEC CICS XCTL
      //COB(648): 064900                  PROGRAM (LIT-ADDTPGM)
      //COB(648): 065000                  COMMAREA(CARDDEMO-COMMAREA)
      //COB(648): 065100        END-EXEC
      try {
        supernaut.xctl(ws.wsConstants.litAddtpgm.getValue()) //
                .commarea(ws.cocom01y.carddemoCommarea) //
                .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
    //COB(652): 065200     END-IF
    }
    //COB(657): 065700     IF CCARD-AID-PFK08
  // 
  // ****************************************************************
  //  If the user did not press PF8, lets reset the last page flag
  // ****************************************************************
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk08()) {
      //COB(658): 065800        CONTINUE
      //do nothing
      //COB(659): 065900     ELSE
    } else {
      //COB(660): 066000        SET CA-LAST-PAGE-NOT-SHOWN   TO TRUE
      ws.wsThisProgcommarea.wsCaPagingVariables.setCaLastPageNotShown(true);
    //COB(661): 066100     END-IF
    }
    //COB(666): 066600     IF  CCARD-AID-PFK10
  // ****************************************************************
  //     If the user pressed F10 to confirm delete
  //     But changed some criteria on screen. Treat it as ENTER
  // ****************************************************************
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk10()) {
      //COB(667): 066700         IF  (CA-DELETE-REQUESTED
      //COB(667): 066800         OR   CA-UPDATE-REQUESTED)
      //COB(667): 066900         AND FLG-TYPEFILTER-CHANGED-NO
      //COB(667): 067000         AND FLG-DESCFILTER-CHANGED-NO
      //COB(667): 067100         AND FLG-ROW-SELECTION-CHANGED-NO
      if ((ws.wsThisProgcommarea.caDeleteRequested()
          || ws.wsThisProgcommarea.caUpdateRequested()
          )
          && ws.wsMiscStorage.flgTypefilterChangedNo()
          && ws.wsMiscStorage.flgDescfilterChangedNo()
          && ws.wsMiscStorage.flgRowSelectionChangedNo()) {
        //COB(672): 067200             CONTINUE
        //do nothing
        //COB(673): 067300         ELSE
      } else {
        //COB(674): 067400             SET CCARD-AID-ENTER TO TRUE
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidEnter(true);
      //COB(675): 067500         END-IF
      }
      //COB(676): 067600     ELSE
    } else {
      //COB(677): 067700        CONTINUE
      //do nothing
    //COB(678): 067800     END-IF
    }
    //COB(684): 068400     PERFORM 9998-PRIMING-QUERY
    //COB(684): 068500        THRU 9998-PRIMING-QUERY-EXIT
  // 
  // 
  // ****************************************************************
  //   Check Db2 connectivity. Quit if no Access.
  // ****************************************************************
    _9998PrimingQuery();
    //COB(687): 068700     IF WS-DB2-ERROR
    if (ws.wsMiscStorage.wsDb2CommonVars.wsDb2Error()) {
      //COB(688): 068800        PERFORM SEND-LONG-TEXT
      //COB(688): 068900           THRU SEND-LONG-TEXT-EXIT
      sendLongText();
      //COB(690): 069000        GO TO COMMON-RETURN
      return Flow.commonReturn;
    //COB(691): 069100     END-IF
    }
    //COB(698): 069800     EVALUATE TRUE
  // 
  // 
  // 
  // ****************************************************************
  //  Now we decide what to do
  // ****************************************************************
      //COB(699): 069900         WHEN INPUT-ERROR
    if (ws.wsMiscStorage.inputError()) {
      //COB(703): 070300              MOVE WS-RETURN-MSG   TO CCARD-ERROR-MSG
  // ****************************************************************
  //         ASK FOR CORRECTIONS TO INPUTS
  // ****************************************************************
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
      //COB(704): 070400              MOVE LIT-THISPGM     TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsConstants.litThispgm);
      //COB(705): 070500              MOVE LIT-THISMAPSET  TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(ws.wsConstants.litThismapset);
      //COB(706): 070600              MOVE LIT-THISMAP     TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      //COB(708): 070800              MOVE LIT-THISPGM     TO CCARD-NEXT-PROG
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.setValue(ws.wsConstants.litThispgm);
      //COB(709): 070900              MOVE LIT-THISMAPSET  TO CCARD-NEXT-MAPSET
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsConstants.litThismapset);
      //COB(710): 071000              MOVE LIT-THISMAP     TO CCARD-NEXT-MAP
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsConstants.litThismap);
      //COB(711): 071100              MOVE WS-CA-FIRST-TR-CODE
      //COB(711): 071200                                   TO WS-START-KEY
      ws.wsMiscStorage.wsDataFilters.wsStartKey.setValue(ws.wsThisProgcommarea.wsCaPagingVariables.wsCaFirstTtypekey.wsCaFirstTrCode);
      //COB(713): 071300              IF  NOT FLG-TYPEFILTER-NOT-OK
      //COB(713): 071400              AND NOT FLG-DESCFILTER-NOT-OK
      if (!ws.wsMiscStorage.flgTypefilterNotOk()
          &&
          !ws.wsMiscStorage.flgDescfilterNotOk()) {
        //COB(715): 071500                 PERFORM 8000-READ-FORWARD
        //COB(715): 071600                    THRU 8000-READ-FORWARD-EXIT
        _8000ReadForward();
      //COB(717): 071700              END-IF
      }
      //COB(718): 071800              PERFORM 2000-SEND-MAP
      //COB(718): 071900                 THRU 2000-SEND-MAP-EXIT
      _2000SendMap();
      //COB(720): 072000              GO TO COMMON-RETURN
      return Flow.commonReturn;
      //COB(721): 072100         WHEN CCARD-AID-PFK07
      //COB(721): 072200              AND CA-FIRST-PAGE
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk07() && ws.wsThisProgcommarea.wsCaPagingVariables.caFirstPage()
      //COB(726): 072600         WHEN CCARD-AID-PFK07
      //COB(726): 072700              AND CA-FIRST-PAGE
  // ****************************************************************
  //         PAGE UP - PF7 - BUT ALREADY ON FIRST PAGE
  // ****************************************************************
      || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk07() && ws.wsThisProgcommarea.wsCaPagingVariables.caFirstPage()) {
      //COB(728): 072800              MOVE WS-CA-FIRST-TR-CODE
      //COB(728): 072900                            TO WS-START-KEY
      ws.wsMiscStorage.wsDataFilters.wsStartKey.setValue(ws.wsThisProgcommarea.wsCaPagingVariables.wsCaFirstTtypekey.wsCaFirstTrCode);
      //COB(730): 073000              PERFORM 8000-READ-FORWARD
      //COB(730): 073100                 THRU 8000-READ-FORWARD-EXIT
      _8000ReadForward();
      //COB(732): 073200              PERFORM 2000-SEND-MAP
      //COB(732): 073300                 THRU 2000-SEND-MAP-EXIT
      _2000SendMap();
      //COB(734): 073400              GO TO COMMON-RETURN
      return Flow.commonReturn;
      //COB(738): 073800         WHEN CCARD-AID-PFK03
  // ****************************************************************
  //         BACK - PF3 IF WE CAME FROM SOME OTHER PROGRAM
  // ****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()
      //COB(739): 073900         WHEN CDEMO-PGM-REENTER AND
      //COB(739): 074000              CDEMO-FROM-PROGRAM NOT EQUAL LIT-THISPGM
      || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter() && !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(ws.wsConstants.litThispgm)) {
      //COB(742): 074200              INITIALIZE CARDDEMO-COMMAREA
      //COB(742): 074300                         WS-THIS-PROGCOMMAREA
      //COB(742): 074400                         WS-MISC-STORAGE
      ws.cocom01y.carddemoCommarea.initialize();
      ws.wsThisProgcommarea.initialize();
      ws.wsMiscStorage.initialize();
      //COB(746): 074600              MOVE LIT-THISTRANID      TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsConstants.litThistranid);
      //COB(747): 074700              MOVE LIT-THISPGM         TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsConstants.litThispgm);
      //COB(748): 074800              MOVE LIT-THISMAP         TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      //COB(749): 074900              MOVE LIT-THISMAPSET      TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(ws.wsConstants.litThismapset);
      //COB(751): 075100              SET CDEMO-USRTYP-ADMIN   TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypAdmin(true);
      //COB(752): 075200              SET CDEMO-PGM-ENTER      TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      //COB(753): 075300              SET CA-FIRST-PAGE        TO TRUE
      ws.wsThisProgcommarea.wsCaPagingVariables.setCaFirstPage(true);
      //COB(754): 075400              SET CA-LAST-PAGE-NOT-SHOWN TO TRUE
      ws.wsThisProgcommarea.wsCaPagingVariables.setCaLastPageNotShown(true);
      //COB(756): 075600              MOVE WS-CA-FIRST-TR-CODE TO WS-START-KEY
      ws.wsMiscStorage.wsDataFilters.wsStartKey.setValue(ws.wsThisProgcommarea.wsCaPagingVariables.wsCaFirstTtypekey.wsCaFirstTrCode);
      //COB(758): 075800              PERFORM 8000-READ-FORWARD
      //COB(758): 075900                 THRU 8000-READ-FORWARD-EXIT
      _8000ReadForward();
      //COB(760): 076000              PERFORM 2000-SEND-MAP
      //COB(760): 076100                 THRU 2000-SEND-MAP-EXIT
      _2000SendMap();
      //COB(762): 076200              GO TO COMMON-RETURN
      return Flow.commonReturn;
      //COB(766): 076600         WHEN CCARD-AID-PFK08
      //COB(766): 076700              AND CA-NEXT-PAGE-EXISTS
  // ****************************************************************
  //         PAGE DOWN
  // ****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk08() && ws.wsThisProgcommarea.wsCaPagingVariables.caNextPageExists()) {
      //COB(768): 076800              MOVE WS-CA-LAST-TR-CODE
      //COB(768): 076900                            TO WS-START-KEY
      ws.wsMiscStorage.wsDataFilters.wsStartKey.setValue(ws.wsThisProgcommarea.wsCaPagingVariables.wsCaLastTtypekey.wsCaLastTrCode);
      //COB(770): 077000              ADD   +1      TO WS-CA-SCREEN-NUM
      ws.wsThisProgcommarea.wsCaPagingVariables.wsCaScreenNum.add(1);
      //COB(771): 077100              PERFORM 8000-READ-FORWARD
      //COB(771): 077200                 THRU 8000-READ-FORWARD-EXIT
      _8000ReadForward();
      //COB(773): 077300              INITIALIZE WS-EDIT-SELECT-FLAGS
      ws.wsMiscStorage.wsEditSelectFlags.initialize();
      //COB(774): 077400              PERFORM 2000-SEND-MAP
      //COB(774): 077500                 THRU 2000-SEND-MAP-EXIT
      _2000SendMap();
      //COB(776): 077600              GO TO COMMON-RETURN
      return Flow.commonReturn;
      //COB(780): 078000         WHEN CCARD-AID-PFK07
      //COB(780): 078100              AND NOT CA-FIRST-PAGE
  // ****************************************************************
  //         PAGE UP
  // ****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk07() && !ws.wsThisProgcommarea.wsCaPagingVariables.caFirstPage()) {
      //COB(782): 078200              MOVE WS-CA-FIRST-TR-CODE
      //COB(782): 078300                            TO WS-START-KEY
      ws.wsMiscStorage.wsDataFilters.wsStartKey.setValue(ws.wsThisProgcommarea.wsCaPagingVariables.wsCaFirstTtypekey.wsCaFirstTrCode);
      //COB(784): 078400              SUBTRACT 1    FROM WS-CA-SCREEN-NUM
      ws.wsThisProgcommarea.wsCaPagingVariables.wsCaScreenNum.subtract(1);
      //COB(785): 078500              PERFORM 8100-READ-BACKWARDS
      //COB(785): 078600                 THRU 8100-READ-BACKWARDS-EXIT
      rcNext = Flow._8100ReadBackwards;
      while (!rcNext.equals(Flow.Exit)) {
        switch (rcNext) {
        case _8100ReadBackwards:
          _8100ReadBackwards();
          rcNext = Flow._8100ReadBackwardsExit;
          break;
        case _8100ReadBackwardsExit:
          _8100ReadBackwardsExit();
          rcNext = Flow.Exit;
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
        }
      }
      //COB(787): 078700              INITIALIZE WS-EDIT-SELECT-FLAGS
      ws.wsMiscStorage.wsEditSelectFlags.initialize();
      //COB(788): 078800              PERFORM 2000-SEND-MAP
      //COB(788): 078900                 THRU 2000-SEND-MAP-EXIT
      _2000SendMap();
      //COB(790): 079000              GO TO COMMON-RETURN
      return Flow.commonReturn;
      //COB(794): 079400         WHEN CCARD-AID-ENTER
      //COB(794): 079500          AND WS-DELETES-REQUESTED > 0
      //COB(794): 079600          AND CDEMO-FROM-PROGRAM  EQUAL LIT-THISPGM
  // ****************************************************************
  //         ENTER AND DELETE REQUESTED
  // ****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidEnter() && ws.wsMiscStorage.wsActionsSelected.wsDeletesRequested.greaterThan(0) && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(ws.wsConstants.litThispgm)) {
      //COB(797): 079700              MOVE WS-CA-FIRST-TR-CODE
      //COB(797): 079800                                   TO WS-START-KEY
      ws.wsMiscStorage.wsDataFilters.wsStartKey.setValue(ws.wsThisProgcommarea.wsCaPagingVariables.wsCaFirstTtypekey.wsCaFirstTrCode);
      //COB(799): 079900              IF  NOT FLG-TYPEFILTER-NOT-OK
      //COB(799): 080000              AND NOT FLG-DESCFILTER-NOT-OK
      if (!ws.wsMiscStorage.flgTypefilterNotOk()
          &&
          !ws.wsMiscStorage.flgDescfilterNotOk()) {
        //COB(801): 080100                 PERFORM 8000-READ-FORWARD
        //COB(801): 080200                    THRU 8000-READ-FORWARD-EXIT
        _8000ReadForward();
      //COB(803): 080300              END-IF
      }
      //COB(804): 080400              PERFORM 2000-SEND-MAP
      //COB(804): 080500                 THRU 2000-SEND-MAP-EXIT
      _2000SendMap();
      //COB(806): 080600              GO TO COMMON-RETURN
      return Flow.commonReturn;
      //COB(810): 081000         WHEN CCARD-AID-PFK10
      //COB(810): 081100          AND WS-DELETES-REQUESTED > 0
      //COB(810): 081200          AND CDEMO-FROM-PROGRAM  EQUAL LIT-THISPGM
  // ****************************************************************
  //         F10  AFTER DELETE CONFIRM REQUESTED
  // ****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk10() && ws.wsMiscStorage.wsActionsSelected.wsDeletesRequested.greaterThan(0) && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(ws.wsConstants.litThispgm)) {
      //COB(814): 081400              PERFORM 9300-DELETE-RECORD
      //COB(814): 081500                 THRU 9300-DELETE-RECORD-EXIT
      _9300DeleteRecord();
      //COB(817): 081700              IF CA-DELETE-SUCCEEDED
      if (ws.wsThisProgcommarea.caDeleteSucceeded()) {
        //COB(818): 081800                 SET FLG-DELETED-YES   TO TRUE
        ws.wsMiscStorage.setFlgDeletedYes(true);
        //COB(819): 081900              ELSE
      } else {
        //COB(820): 082000                 SET FLG-DELETED-NO    TO TRUE
        ws.wsMiscStorage.setFlgDeletedNo(true);
      //COB(821): 082100              END-IF
      }
      //COB(823): 082300              PERFORM 2000-SEND-MAP
      //COB(823): 082400                 THRU 2000-SEND-MAP-EXIT
      _2000SendMap();
      //COB(826): 082600              IF FLG-DELETED-YES
      if (ws.wsMiscStorage.flgDeletedYes()) {
        //COB(827): 082700                 INITIALIZE CARDDEMO-COMMAREA
        //COB(827): 082800                         WS-THIS-PROGCOMMAREA
        //COB(827): 082900                         WS-MISC-STORAGE
        ws.cocom01y.carddemoCommarea.initialize();
        ws.wsThisProgcommarea.initialize();
        ws.wsMiscStorage.initialize();
        //COB(830): 083000                 SET CDEMO-PGM-ENTER      TO TRUE
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
        //COB(831): 083100                 SET CA-FIRST-PAGE        TO TRUE
        ws.wsThisProgcommarea.wsCaPagingVariables.setCaFirstPage(true);
        //COB(832): 083200                 SET CA-LAST-PAGE-NOT-SHOWN TO TRUE
        ws.wsThisProgcommarea.wsCaPagingVariables.setCaLastPageNotShown(true);
      //COB(833): 083300              END-IF
      }
      //COB(834): 083400             GO TO COMMON-RETURN
      return Flow.commonReturn;
      //COB(838): 083800         WHEN CCARD-AID-ENTER
      //COB(838): 083900          AND WS-UPDATES-REQUESTED > 0
      //COB(838): 084000          AND CDEMO-FROM-PROGRAM  EQUAL LIT-THISPGM
  // ****************************************************************
  //         ENTER AND UPDATE REQUESTED
  // ****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidEnter() && ws.wsMiscStorage.wsActionsSelected.wsUpdatesRequested.greaterThan(0) && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(ws.wsConstants.litThispgm)) {
      //COB(841): 084100              MOVE WS-CA-FIRST-TR-CODE
      //COB(841): 084200                                   TO WS-START-KEY
      ws.wsMiscStorage.wsDataFilters.wsStartKey.setValue(ws.wsThisProgcommarea.wsCaPagingVariables.wsCaFirstTtypekey.wsCaFirstTrCode);
      //COB(843): 084300              IF  NOT FLG-TYPEFILTER-NOT-OK
      //COB(843): 084400              AND NOT FLG-DESCFILTER-NOT-OK
      if (!ws.wsMiscStorage.flgTypefilterNotOk()
          &&
          !ws.wsMiscStorage.flgDescfilterNotOk()) {
        //COB(845): 084500                 PERFORM 8000-READ-FORWARD
        //COB(845): 084600                    THRU 8000-READ-FORWARD-EXIT
        _8000ReadForward();
      //COB(847): 084700              END-IF
      }
      //COB(848): 084800              PERFORM 2000-SEND-MAP
      //COB(848): 084900                 THRU 2000-SEND-MAP-EXIT
      _2000SendMap();
      //COB(850): 085000              GO TO COMMON-RETURN
      return Flow.commonReturn;
      //COB(854): 085400         WHEN CCARD-AID-PFK10
      //COB(854): 085500          AND WS-UPDATES-REQUESTED > 0
      //COB(854): 085600          AND CDEMO-FROM-PROGRAM  EQUAL LIT-THISPGM
  // ****************************************************************
  //         F10  AFTER UPDATE CONFIRM REQUESTED
  // ****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk10() && ws.wsMiscStorage.wsActionsSelected.wsUpdatesRequested.greaterThan(0) && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(ws.wsConstants.litThispgm)) {
      //COB(858): 085800              PERFORM 9200-UPDATE-RECORD
      //COB(858): 085900                 THRU 9200-UPDATE-RECORD-EXIT
      _9200UpdateRecord();
      //COB(860): 086000              IF CA-UPDATE-SUCCEEDED
      if (ws.wsThisProgcommarea.caUpdateSucceeded()) {
        //COB(861): 086100                 SET FLG-UPDATE-COMPLETED TO TRUE
        ws.wsMiscStorage.setFlgUpdateCompleted(true);
      //COB(862): 086200              END-IF
      }
      //COB(863): 086300                MOVE WS-CA-FIRST-TR-CODE
      //COB(863): 086400                            TO WS-START-KEY
      ws.wsMiscStorage.wsDataFilters.wsStartKey.setValue(ws.wsThisProgcommarea.wsCaPagingVariables.wsCaFirstTtypekey.wsCaFirstTrCode);
      //COB(865): 086500              PERFORM 8000-READ-FORWARD
      //COB(865): 086600                 THRU 8000-READ-FORWARD-EXIT
      _8000ReadForward();
      //COB(867): 086700              PERFORM 2000-SEND-MAP
      //COB(867): 086800                 THRU 2000-SEND-MAP-EXIT
      _2000SendMap();
      //COB(870): 087000         WHEN OTHER
  // ****************************************************************
    } else {
      //COB(872): 087200              MOVE WS-CA-FIRST-TR-CODE
      //COB(872): 087300                            TO WS-START-KEY
  // ****************************************************************
      ws.wsMiscStorage.wsDataFilters.wsStartKey.setValue(ws.wsThisProgcommarea.wsCaPagingVariables.wsCaFirstTtypekey.wsCaFirstTrCode);
      //COB(874): 087400              PERFORM 8000-READ-FORWARD
      //COB(874): 087500                 THRU 8000-READ-FORWARD-EXIT
      _8000ReadForward();
      //COB(876): 087600              PERFORM 2000-SEND-MAP
      //COB(876): 087700                 THRU 2000-SEND-MAP-EXIT
      _2000SendMap();
      //COB(878): 087800              GO TO COMMON-RETURN
      return Flow.commonReturn;
    //COB(879): 087900     END-EVALUATE
    }
    //COB(882): 088200     IF INPUT-ERROR
  // 
  //  If we had an error setup error message to display and return
    if (ws.wsMiscStorage.inputError()) {
      //COB(883): 088300        MOVE WS-RETURN-MSG   TO CCARD-ERROR-MSG
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
      //COB(884): 088400        MOVE LIT-THISPGM     TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsConstants.litThispgm);
      //COB(885): 088500        MOVE LIT-THISMAPSET  TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(ws.wsConstants.litThismapset);
      //COB(886): 088600        MOVE LIT-THISMAP     TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      //COB(888): 088800        MOVE LIT-THISPGM     TO CCARD-NEXT-PROG
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.setValue(ws.wsConstants.litThispgm);
      //COB(889): 088900        MOVE LIT-THISMAPSET  TO CCARD-NEXT-MAPSET
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsConstants.litThismapset);
      //COB(890): 089000        MOVE LIT-THISMAP     TO CCARD-NEXT-MAP
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsConstants.litThismap);
      //COB(892): 089200        GO TO COMMON-RETURN
      return Flow.commonReturn;
    //COB(893): 089300     END-IF
    }
    //COB(895): 089500     MOVE LIT-THISPGM        TO CCARD-NEXT-PROG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.setValue(ws.wsConstants.litThispgm);
    //COB(896): 089600     GO TO COMMON-RETURN
    return Flow.commonReturn;

  }

  /***/
  protected void commonReturn() {
    //COB(900): 090000     MOVE  LIT-THISTRANID  TO CDEMO-FROM-TRANID
    ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsConstants.litThistranid);
    //COB(901): 090100     MOVE  LIT-THISPGM     TO CDEMO-FROM-PROGRAM
    ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsConstants.litThispgm);
    //COB(902): 090200     MOVE  LIT-THISMAPSET  TO CDEMO-LAST-MAPSET
    ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(ws.wsConstants.litThismapset);
    //COB(903): 090300     MOVE  LIT-THISMAP     TO CDEMO-LAST-MAP
    ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
    //COB(904): 090400     MOVE  CARDDEMO-COMMAREA    TO WS-COMMAREA
    ws.wsCommarea.setValue(ws.cocom01y.carddemoCommarea);
    //COB(905): 090500     MOVE  WS-THIS-PROGCOMMAREA TO
    //COB(905): 090600            WS-COMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
    //COB(905): 090700                         LENGTH OF WS-THIS-PROGCOMMAREA )
    ws.wsCommarea.setValue(ws.wsThisProgcommarea, 0, ws.cocom01y.carddemoCommarea.length(), ws.wsThisProgcommarea.length());
    //COB(910): 091000     EXEC CICS RETURN
    //COB(910): 091100          TRANSID (LIT-THISTRANID)
    //COB(910): 091200          COMMAREA (WS-COMMAREA)
    //COB(910): 091300          LENGTH(LENGTH OF WS-COMMAREA)
    //COB(910): 091400     END-EXEC
  // 
  // 
    try {
      supernaut.returnTransid(ws.wsConstants.litThistranid) //
              .commarea(ws.wsCommarea) //
              .length(ws.wsCommarea.length()) //
              .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }

  }


  protected void _1000ReceiveMap() {
    //COB(920): 092000     PERFORM 1100-RECEIVE-SCREEN
    //COB(920): 092100        THRU 1100-RECEIVE-SCREEN-EXIT
    _1100ReceiveScreen();
    //COB(923): 092300     PERFORM 1200-EDIT-INPUTS
    //COB(923): 092400      THRU   1200-EDIT-INPUTS-EXIT
    _1200EditInputs();

  }

  /***/
  protected void _1100ReceiveScreen() {
    //COB(931): 093100     EXEC CICS RECEIVE MAP(LIT-THISMAP)
    //COB(931): 093200                    MAPSET(LIT-THISMAPSET)
    //COB(931): 093300                    INTO(CTRTLIAI)
    //COB(931): 093400                    RESP(WS-RESP-CD)
    //COB(931): 093500     END-EXEC
    supernaut.receiveMap(ws.wsConstants.litThismap.getValue()) //
            .mapset(ws.wsConstants.litThismapset.getValue()) //
            .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
            .into(ws.cotrtli.ctrtliai) //
            .exec();
    //COB(937): 093700     MOVE TRTYPEI  OF CTRTLIAI  TO WS-IN-TYPE-CD
    ws.wsMiscStorage.wsScreenEditVars.wsInTypeCd.setValue(ws.cotrtli.ctrtliai.trtypei);
    //COB(938): 093800     MOVE TRDESCI  OF CTRTLIAI  TO WS-IN-TYPE-DESC
    ws.wsMiscStorage.wsScreenEditVars.wsInTypeDesc.setValue(ws.cotrtli.ctrtliai.trdesci);
    //COB(940): 094000     PERFORM VARYING I FROM 1 BY 1 UNTIL I > WS-MAX-SCREEN-LINES
    ws.wsMiscStorage.wsSubscriptVars.i.setValue(1);
    for (;!ws.wsMiscStorage.wsSubscriptVars.i.greaterThan(ws.wsConstants.wsMaxScreenLines);ws.wsMiscStorage.wsSubscriptVars.i.add(1)) {
      //COB(941): 094100         MOVE TRTSELI(I)           TO WS-EDIT-SELECT(I)
      ws.wsMiscStorage.filler180.wsEditSelectAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).setValue(ws.filler434.wsRowDatai.eachRowiAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtseli);
      //COB(942): 094200         MOVE TRTTYPI(I)           TO WS-ROW-TR-CODE-IN(I)
      ws.wsMiscStorage.wsScreenDataIn.filler167.wsScreenRowsInAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsEachRowIn.wsEachTtypIn.wsRowTrCodeIn.setValue(ws.filler434.wsRowDatai.eachRowiAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trttypi);
      //COB(944): 094400         MOVE LOW-VALUES           TO WS-ROW-TR-DESC-IN(I)
      ws.wsMiscStorage.wsScreenDataIn.filler167.wsScreenRowsInAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsEachRowIn.wsEachTtypIn.wsRowTrDescIn.lowValues();
      //COB(945): 094500         IF   TRTYPDI(I) = LIT-ASTERISK
      //COB(945): 094600         OR   TRTYPDI(I) = SPACES
      if (ws.filler434.wsRowDatai.eachRowiAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtypdi.equals(ws.wsConstants.litAsterisk)
          || ws.filler434.wsRowDatai.eachRowiAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtypdi.isSpaces()) {
        //COB(947): 094700             CONTINUE
        //do nothing
        //COB(948): 094800         ELSE
      } else {
        //COB(949): 094900             MOVE FUNCTION TRIM(TRTYPDI(I))
        //COB(949): 095000                                   TO WS-ROW-TR-DESC-IN(I)
        ws.wsMiscStorage.wsScreenDataIn.filler167.wsScreenRowsInAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsEachRowIn.wsEachTtypIn.wsRowTrDescIn.setValue(ws.filler434.wsRowDatai.eachRowiAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtypdi.trim());
      //COB(951): 095100         END-IF
      }
    //COB(953): 095300     END-PERFORM
    }

  }

  /***/
  protected void _1200EditInputs() {
    //COB(962): 096200     SET INPUT-OK                   TO TRUE
    ws.wsMiscStorage.setInputOk(true);
    //COB(963): 096300     SET FLG-PROTECT-SELECT-ROWS-NO TO TRUE
    ws.wsMiscStorage.cicsOutputEditVars.setFlgProtectSelectRowsNo(true);
    //COB(965): 096500     PERFORM 1210-EDIT-ARRAY
    //COB(965): 096600        THRU 1210-EDIT-ARRAY-EXIT
    _1210EditArray();
    //COB(968): 096800     PERFORM 1230-EDIT-DESC
    //COB(968): 096900        THRU 1230-EDIT-DESC-EXIT
    rcNext = Flow._1230EditDesc;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
      case _1230EditDesc:
        rcNext = _1230EditDesc();
        if (rcNext.equals(Flow.Exit)) {
          rcNext = Flow._1230EditDescExit;
        }
        break;
      case _1230EditDescExit:
        _1230EditDescExit();
        rcNext = Flow.Exit;
        break;
      default:
        throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    //COB(971): 097100     PERFORM 1220-EDIT-TYPECD
    //COB(971): 097200        THRU 1220-EDIT-TYPECD-EXIT
    rcNext = Flow._1220EditTypecd;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
      case _1220EditTypecd:
        rcNext = _1220EditTypecd();
        if (rcNext.equals(Flow.Exit)) {
          rcNext = Flow._1220EditTypecdExit;
        }
        break;
      case _1220EditTypecdExit:
        _1220EditTypecdExit();
        rcNext = Flow.Exit;
        break;
      default:
        throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    //COB(974): 097400     PERFORM 1290-CROSS-EDITS
    //COB(974): 097500        THRU 1290-CROSS-EDITS-EXIT
    _1290CrossEdits();

  }

  /***/
  protected void _1210EditArray() {
    //COB(984): 098400     MOVE ZERO                     TO WS-ACTIONS-REQUESTED
    //COB(984): 098500                                      WS-NO-ACTIONS-SELECTED
    //COB(984): 098600                                      WS-DELETES-REQUESTED
    //COB(984): 098700                                      WS-UPDATES-REQUESTED
    //COB(984): 098800                                      WS-VALID-ACTIONS-SELECTED
    ws.wsMiscStorage.wsActionsSelected.wsActionsRequested.zeros();
    ws.wsMiscStorage.wsActionsSelected.wsNoActionsSelected.zeros();
    ws.wsMiscStorage.wsActionsSelected.wsDeletesRequested.zeros();
    ws.wsMiscStorage.wsActionsSelected.wsUpdatesRequested.zeros();
    ws.wsMiscStorage.wsValidActionsSelected.zeros();
    //COB(991): 099100     IF  FLG-TYPEFILTER-CHANGED-YES
    //COB(991): 099200     OR  FLG-DESCFILTER-CHANGED-YES
  // 
  // 
    if (ws.wsMiscStorage.flgTypefilterChangedYes()
        || ws.wsMiscStorage.flgDescfilterChangedYes()) {
      //COB(993): 099300         INITIALIZE                 WS-EDIT-SELECT-FLAGS
      ws.wsMiscStorage.wsEditSelectFlags.initialize();
      //COB(994): 099400         GO TO 1210-EDIT-ARRAY-EXIT
      return;
      //COB(995): 099500     ELSE
    } else {
      //COB(997): 099700     INSPECT  WS-EDIT-SELECT-FLAGS
      //COB(997): 099800     TALLYING WS-NO-ACTIONS-SELECTED FOR ALL SPACES
      //COB(997): 099900                                         LOW-VALUES
      //COB(997): 100000              WS-DELETES-REQUESTED   FOR ALL LIT-DELETE-FLAG
      //COB(997): 100100              WS-UPDATES-REQUESTED   FOR ALL LIT-UPDATE-FLAG
      ws.wsMiscStorage.wsActionsSelected.wsNoActionsSelected.setValue(ws.wsMiscStorage.wsEditSelectFlags.countStrings(SPACES, LOW_VALUES));
      ws.wsMiscStorage.wsActionsSelected.wsDeletesRequested.setValue(ws.wsMiscStorage.wsEditSelectFlags.countStrings(ws.wsConstants.litDeleteFlag));
      ws.wsMiscStorage.wsActionsSelected.wsUpdatesRequested.setValue(ws.wsMiscStorage.wsEditSelectFlags.countStrings(ws.wsConstants.litUpdateFlag));
      //COB(1003): 100300     COMPUTE  WS-ACTIONS-REQUESTED
      //COB(1003): 100400           =  WS-MAX-SCREEN-LINES
      //COB(1003): 100500           -  WS-NO-ACTIONS-SELECTED
      //COB(1003): 100600     END-COMPUTE
      ws.wsMiscStorage.wsActionsSelected.wsActionsRequested.setValue(ws.wsConstants.wsMaxScreenLines.getValue().subtract(ws.wsMiscStorage.wsActionsSelected.wsNoActionsSelected.getValue()));
      //COB(1009): 100900     COMPUTE WS-VALID-ACTIONS-SELECTED =
      //COB(1009): 101000             WS-DELETES-REQUESTED
      //COB(1009): 101100           + WS-UPDATES-REQUESTED
      //COB(1009): 101200     END-COMPUTE
  // 
  // 
      ws.wsMiscStorage.wsValidActionsSelected.setValue(ws.wsMiscStorage.wsActionsSelected.wsDeletesRequested.getValue().add(ws.wsMiscStorage.wsActionsSelected.wsUpdatesRequested.getValue()));
      //COB(1014): 101400     MOVE ZERO TO I-SELECTED
      ws.wsMiscStorage.wsSubscriptVars.iSelected.zeros();
      //COB(1015): 101500     SET FLG-BAD-ACTIONS-SELECTED-NO    TO TRUE
      ws.wsMiscStorage.setFlgBadActionsSelectedNo(true);
      //COB(1017): 101700     PERFORM VARYING I
      //COB(1017): 101800                FROM WS-MAX-SCREEN-LINES
      //COB(1017): 101900                  BY -1
      //COB(1017): 102000               UNTIL I = 0
      ws.wsMiscStorage.wsSubscriptVars.i.setValue(ws.wsConstants.wsMaxScreenLines);
      for (;!ws.wsMiscStorage.wsSubscriptVars.i.equals(0);ws.wsMiscStorage.wsSubscriptVars.i.add(-1)) {
        //COB(1021): 102100         EVALUATE TRUE
          //COB(1022): 102200           WHEN SELECT-OK(I)
        if (ws.wsMiscStorage.filler180.selectOk(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1)) {
          //COB(1023): 102300             MOVE I TO I-SELECTED
          ws.wsMiscStorage.wsSubscriptVars.iSelected.setValue(ws.wsMiscStorage.wsSubscriptVars.i);
          //COB(1024): 102400             IF WS-MORETHAN1ACTION
          if (ws.wsMiscStorage.wsActionsSelected.wsMorethan1action()) {
            //COB(1025): 102500                MOVE '1' TO WS-ROW-TRTSELECT-ERROR(I)
            ws.wsMiscStorage.filler192.wsEditSelectErrorsAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsRowTrtselectError.setString("1");
            //COB(1026): 102600                SET FLG-BAD-ACTIONS-SELECTED-YES   TO TRUE
            ws.wsMiscStorage.setFlgBadActionsSelectedYes(true);
          //COB(1027): 102700             END-IF
          }
          //COB(1028): 102800             IF UPDATE-REQUESTED-ON(I)
          if (ws.wsMiscStorage.filler180.updateRequestedOn(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1)) {
            //COB(1029): 102900                PERFORM 1211-EDIT-ARRAY-DESC
            //COB(1029): 103000                   THRU 1211-EDIT-ARRAY-DESC-EXIT
            _1211EditArrayDesc();
          //COB(1031): 103100             END-IF
          }
          //COB(1032): 103200           WHEN SELECT-BLANK(I)
        } else if (ws.wsMiscStorage.filler180.selectBlank(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1)) {
          //COB(1033): 103300             CONTINUE
          //do nothing
          //COB(1034): 103400           WHEN OTHER
        } else {
          //COB(1035): 103500             SET INPUT-ERROR TO TRUE
          ws.wsMiscStorage.setInputError(true);
          //COB(1036): 103600             MOVE '1' TO WS-ROW-TRTSELECT-ERROR(I)
          ws.wsMiscStorage.filler192.wsEditSelectErrorsAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsRowTrtselectError.setString("1");
          //COB(1037): 103700             SET FLG-BAD-ACTIONS-SELECTED-YES     TO TRUE
          ws.wsMiscStorage.setFlgBadActionsSelectedYes(true);
          //COB(1038): 103800             SET WS-MESG-INVALID-ACTION-CODE      TO TRUE
          ws.wsMiscStorage.setWsMesgInvalidActionCode(true);
        //COB(1039): 103900        END-EVALUATE
        }
      //COB(1040): 104000     END-PERFORM
      }
      //COB(1042): 104200     IF I-SELECTED EQUAL  WS-CA-ROW-SELECTED
      if (ws.wsMiscStorage.wsSubscriptVars.iSelected.equals(ws.wsThisProgcommarea.wsCaRowSelected)) {
        //COB(1043): 104300        SET FLG-ROW-SELECTION-CHANGED-NO          TO TRUE
        ws.wsMiscStorage.setFlgRowSelectionChangedNo(true);
        //COB(1044): 104400     ELSE
      } else {
        //COB(1045): 104500        SET FLG-ROW-SELECTION-CHANGED-YES         TO TRUE
        ws.wsMiscStorage.setFlgRowSelectionChangedYes(true);
        //COB(1046): 104600        MOVE I-SELECTED TO   WS-CA-ROW-SELECTED
        ws.wsThisProgcommarea.wsCaRowSelected.setValue(ws.wsMiscStorage.wsSubscriptVars.iSelected);
      //COB(1047): 104700     END-IF
      }
      //COB(1049): 104900     IF WS-MORETHAN1ACTION
      if (ws.wsMiscStorage.wsActionsSelected.wsMorethan1action()) {
        //COB(1050): 105000         SET INPUT-ERROR                          TO TRUE
        ws.wsMiscStorage.setInputError(true);
        //COB(1051): 105100         SET WS-MESG-MORE-THAN-1-ACTION           TO TRUE
        ws.wsMiscStorage.setWsMesgMoreThan1Action(true);
      //COB(1052): 105200     END-IF
      }
    //COB(1053): 105300     .
    }

  }

  /***
  */
  protected void _1211EditArrayDesc() {
    //COB(1062): 106200      SET NO-CHANGES-FOUND           TO TRUE
    ws.wsMiscStorage.setNoChangesFound(true);
    //COB(1064): 106400     IF  FUNCTION UPPER-CASE (
    //COB(1064): 106500         FUNCTION TRIM (WS-ROW-TR-DESC-IN(I)))=
    //COB(1064): 106600         FUNCTION UPPER-CASE (
    //COB(1064): 106700         FUNCTION TRIM (WS-CA-ROW-TR-DESC-OUT(I)))
    //COB(1064): 106800     AND FUNCTION LENGTH (
    //COB(1064): 106900         FUNCTION TRIM (WS-ROW-TR-DESC-IN(I)))=
    //COB(1064): 107000         FUNCTION LENGTH (
    //COB(1064): 107100         FUNCTION TRIM (WS-CA-ROW-TR-DESC-OUT(I)))
    if (ws.wsMiscStorage.wsScreenDataIn.filler167.wsScreenRowsInAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsEachRowIn.wsEachTtypIn.wsRowTrDescIn.trim().toUpperCase().equals(ws.wsThisProgcommarea.filler386.filler388.wsCaScreenRowsOutAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsCaEachRowOut.wsCaRowTrDescOut.trim().toUpperCase())
        && ws.wsMiscStorage.wsScreenDataIn.filler167.wsScreenRowsInAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsEachRowIn.wsEachTtypIn.wsRowTrDescIn.trim().length().equals(ws.wsThisProgcommarea.filler386.filler388.wsCaScreenRowsOutAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsCaEachRowOut.wsCaRowTrDescOut.trim().length())) {
      //COB(1072): 107200         SET WS-MESG-NO-CHANGES-DETECTED   TO TRUE
      ws.wsMiscStorage.setWsMesgNoChangesDetected(true);
      //COB(1073): 107300         GO TO 1211-EDIT-ARRAY-DESC-EXIT
      return;
      //COB(1074): 107400     ELSE
    } else {
      //COB(1075): 107500         SET CHANGES-HAVE-OCCURRED    TO TRUE
      ws.wsMiscStorage.setChangesHaveOccurred(true);
    //COB(1076): 107600     END-IF
    }
    //COB(1078): 107800     SET FLG-ROW-DESCRIPTION-NOT-OK  TO TRUE
    ws.wsMiscStorage.setFlgRowDescriptionNotOk(true);
    //COB(1083): 108300     MOVE 'Transaction Desc'       TO WS-EDIT-VARIABLE-NAME
  // 
  // *****************************************************************
  //     Edit Description
  // *****************************************************************
    ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.setString("Transaction Desc");
    //COB(1084): 108400     MOVE WS-ROW-TR-DESC-IN(I)     TO WS-EDIT-ALPHANUM-ONLY
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.setValue(ws.wsMiscStorage.wsScreenDataIn.filler167.wsScreenRowsInAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsEachRowIn.wsEachTtypIn.wsRowTrDescIn);
    //COB(1085): 108500     MOVE 50                       TO WS-EDIT-ALPHANUM-LENGTH
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.setValue(50);
    //COB(1086): 108600     PERFORM 1240-EDIT-ALPHANUM-REQD
    //COB(1086): 108700        THRU 1240-EDIT-ALPHANUM-REQD-EXIT
    _1240EditAlphanumReqd();
    //COB(1088): 108800     MOVE WS-EDIT-ALPHANUM-ONLY-FLAGS
    //COB(1088): 108900                                   TO WS-ARRAY-DESCRIPTION-FLGS
    ws.wsMiscStorage.wsArrayDescriptionFlgs.setValue(ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnlyFlags);

  }

  /***/
  protected Flow _1220EditTypecd() {
    //COB(1098): 109800     SET FLG-TYPEFILTER-BLANK TO TRUE
    ws.wsMiscStorage.setFlgTypefilterBlank(true);
    //COB(1101): 110100     IF WS-IN-TYPE-CD   EQUAL LOW-VALUES
    //COB(1101): 110200     OR WS-IN-TYPE-CD   EQUAL SPACES
    //COB(1101): 110300     OR WS-IN-TYPE-CD   EQUAL ZEROS
  // 
  //     Not supplied
    if (ws.wsMiscStorage.wsScreenEditVars.wsInTypeCd.isLowValues()
        || ws.wsMiscStorage.wsScreenEditVars.wsInTypeCd.isSpaces()
        || ws.wsMiscStorage.wsScreenEditVars.wsInTypeCd.equals(0)) {
      //COB(1104): 110400        SET FLG-TYPEFILTER-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgTypefilterBlank(true);
      //COB(1105): 110500        MOVE ZEROES       TO WS-TYPE-CD-FILTER
      ws.wsMiscStorage.wsDataFilters.wsTypeCdFilter.zeros();
      //COB(1106): 110600        GO TO  1220-EDIT-TYPECD-EXIT
      return Flow._1220EditTypecdExit;
    //COB(1107): 110700     END-IF
    }
    //COB(1111): 111100     IF WS-IN-TYPE-CD  IS NOT NUMERIC
  // 
  //     Not numeric
  //     Not 2 characters
    if (!ws.wsMiscStorage.wsScreenEditVars.wsInTypeCd.isNumeric()) {
      //COB(1112): 111200        SET INPUT-ERROR TO TRUE
      ws.wsMiscStorage.setInputError(true);
      //COB(1113): 111300        SET FLG-TYPEFILTER-NOT-OK TO TRUE
      ws.wsMiscStorage.setFlgTypefilterNotOk(true);
      //COB(1114): 111400        SET FLG-PROTECT-SELECT-ROWS-YES TO TRUE
      ws.wsMiscStorage.cicsOutputEditVars.setFlgProtectSelectRowsYes(true);
      //COB(1115): 111500        MOVE
      //COB(1115): 111600        'TYPE CODE FILTER,IF SUPPLIED MUST BE A 2 DIGIT NUMBER'
      //COB(1115): 111700                        TO WS-RETURN-MSG
      ws.wsMiscStorage.wsReturnMsg.setString("TYPE CODE FILTER,IF SUPPLIED MUST BE A 2 DIGIT NUMBER");
      //COB(1118): 111800        GO TO 1220-EDIT-TYPECD-EXIT
      return Flow._1220EditTypecdExit;
      //COB(1119): 111900     ELSE
    } else {
      //COB(1120): 112000        MOVE WS-IN-TYPE-CD TO WS-TYPE-CD-FILTER
      ws.wsMiscStorage.wsDataFilters.wsTypeCdFilter.setValue(ws.wsMiscStorage.wsScreenEditVars.wsInTypeCd);
      //COB(1121): 112100        SET FLG-TYPEFILTER-ISVALID TO TRUE
      ws.wsMiscStorage.setFlgTypefilterIsvalid(true);
    //COB(1122): 112200     END-IF
    }
        return Flow.Exit;
  }

  /***/
  protected void _1220EditTypecdExit() {
    //COB(1127): 112700     IF WS-IN-TYPE-CD EQUAL WS-CA-TYPE-CD
    //COB(1127): 112800     OR FLG-TYPEFILTER-BLANK
    //COB(1127): 112900                      AND  (WS-CA-TYPE-CD EQUAL ZEROES
    //COB(1127): 113000                       OR   WS-CA-TYPE-CD EQUAL LOW-VALUES
    //COB(1127): 113100                       OR   WS-CA-TYPE-CD EQUAL SPACES)
    if (ws.wsMiscStorage.wsScreenEditVars.wsInTypeCd.equals(ws.wsThisProgcommarea.wsCaTypeCd)
        || ws.wsMiscStorage.flgTypefilterBlank()
        &&
        (ws.wsThisProgcommarea.wsCaTypeCd.equals(0)
        || ws.wsThisProgcommarea.wsCaTypeCd.isLowValues()
        || ws.wsThisProgcommarea.wsCaTypeCd.isSpaces()
        )) {
      //COB(1132): 113200        SET FLG-TYPEFILTER-CHANGED-NO  TO TRUE
      ws.wsMiscStorage.setFlgTypefilterChangedNo(true);
      //COB(1133): 113300     ELSE
    } else {
      //COB(1134): 113400        INITIALIZE WS-CA-PAGING-VARIABLES
      ws.wsThisProgcommarea.wsCaPagingVariables.initialize();
      //COB(1135): 113500        MOVE WS-IN-TYPE-CD             TO WS-CA-TYPE-CD
      ws.wsThisProgcommarea.wsCaTypeCd.setValue(ws.wsMiscStorage.wsScreenEditVars.wsInTypeCd);
      //COB(1136): 113600        SET FLG-TYPEFILTER-CHANGED-YES TO TRUE
      ws.wsMiscStorage.setFlgTypefilterChangedYes(true);
    //COB(1137): 113700     END-IF
    }
    //COB(1139): 113900     EXIT
    return;

  }

  /***/
  protected Flow _1230EditDesc() {
    //COB(1144): 114400     SET FLG-DESCFILTER-BLANK TO TRUE
    ws.wsMiscStorage.setFlgDescfilterBlank(true);
    //COB(1147): 114700     IF WS-IN-TYPE-DESC   EQUAL LOW-VALUES
    //COB(1147): 114800     OR WS-IN-TYPE-DESC   EQUAL SPACES
  // 
  //     Not supplied
    if (ws.wsMiscStorage.wsScreenEditVars.wsInTypeDesc.isLowValues()
        || ws.wsMiscStorage.wsScreenEditVars.wsInTypeDesc.isSpaces()) {
      //COB(1149): 114900        SET FLG-DESCFILTER-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgDescfilterBlank(true);
      //COB(1150): 115000        GO TO 1230-EDIT-DESC-EXIT
      return Flow._1230EditDescExit;
      //COB(1151): 115100     ELSE
    } else {
      //COB(1152): 115200        SET FLG-DESCFILTER-ISVALID TO TRUE
      ws.wsMiscStorage.setFlgDescfilterIsvalid(true);
    //COB(1153): 115300     END-IF
    }
    //COB(1155): 115500     IF FLG-DESCFILTER-ISVALID
    if (ws.wsMiscStorage.flgDescfilterIsvalid()) {
      //COB(1156): 115600        STRING '%'
      //COB(1156): 115700               FUNCTION TRIM(WS-IN-TYPE-DESC)
      //COB(1156): 115800               '%'
      //COB(1156): 115900         DELIMITED BY SIZE
      //COB(1156): 116000         INTO
      //COB(1156): 116100         WS-TYPE-DESC-FILTER
      //COB(1156): 116200        END-STRING
      ws.wsMiscStorage.wsDataFilters.wsTypeDescFilter.concat("%", ws.wsMiscStorage.wsScreenEditVars.wsInTypeDesc.trim(), "%");
      //COB(1162): 116200        END-STRING
    //COB(1163): 116300     END-IF
    }
        return Flow.Exit;
  }


  protected void _1230EditDescExit() {
    //COB(1166): 116600     IF WS-IN-TYPE-DESC EQUAL WS-CA-TYPE-DESC
    //COB(1166): 116700     OR FLG-DESCFILTER-BLANK
    //COB(1166): 116800                      AND  (WS-CA-TYPE-DESC EQUAL LOW-VALUES
    //COB(1166): 116900                       OR   WS-CA-TYPE-DESC EQUAL SPACES)
    if (ws.wsMiscStorage.wsScreenEditVars.wsInTypeDesc.equals(ws.wsThisProgcommarea.wsCaTypeDesc)
        || ws.wsMiscStorage.flgDescfilterBlank()
        &&
        (ws.wsThisProgcommarea.wsCaTypeDesc.isLowValues()
        || ws.wsThisProgcommarea.wsCaTypeDesc.isSpaces()
        )) {
      //COB(1170): 117000        SET FLG-DESCFILTER-CHANGED-NO   TO TRUE
      ws.wsMiscStorage.setFlgDescfilterChangedNo(true);
      //COB(1171): 117100     ELSE
    } else {
      //COB(1172): 117200        INITIALIZE WS-CA-PAGING-VARIABLES
      ws.wsThisProgcommarea.wsCaPagingVariables.initialize();
      //COB(1173): 117300        MOVE WS-IN-TYPE-DESC            TO WS-CA-TYPE-DESC
      ws.wsThisProgcommarea.wsCaTypeDesc.setValue(ws.wsMiscStorage.wsScreenEditVars.wsInTypeDesc);
      //COB(1174): 117400        SET FLG-DESCFILTER-CHANGED-YES  TO TRUE
      ws.wsMiscStorage.setFlgDescfilterChangedYes(true);
    //COB(1175): 117500     END-IF
    }
    //COB(1177): 117700     EXIT
    return;

  }

  /***
  */
  protected void _1240EditAlphanumReqd() {
    //COB(1183): 118300     SET FLG-ALPHNANUM-NOT-OK          TO TRUE
  //     Initialize
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
    //COB(1186): 118600     IF WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    //COB(1186): 118700                                       EQUAL LOW-VALUES
    //COB(1186): 118800     OR WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    //COB(1186): 118900         EQUAL SPACES
    //COB(1186): 119000     OR FUNCTION LENGTH(FUNCTION TRIM(
    //COB(1186): 119100        WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH))) = 0
  // 
  //     Not supplied
    if (ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt()).isLowValues()
        || ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt()).isSpaces()
        || ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt()).trim().length().equals(0)) {
      //COB(1193): 119300        SET INPUT-ERROR                TO TRUE
      ws.wsMiscStorage.setInputError(true);
      //COB(1194): 119400        SET FLG-ALPHNANUM-BLANK        TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumBlank(true);
      //COB(1195): 119500        IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        //COB(1196): 119600           STRING
        //COB(1196): 119700             FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        //COB(1196): 119800             ' must be supplied.'
        //COB(1196): 119900             DELIMITED BY SIZE
        //COB(1196): 120000             INTO WS-RETURN-MSG
        //COB(1196): 120100           END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " must be supplied.");
        //COB(1201): 120100           END-STRING
      //COB(1202): 120200        END-IF
      }
      //COB(1204): 120400        GO TO  1240-EDIT-ALPHANUM-REQD-EXIT
      return;
    //COB(1205): 120500     END-IF
    }
    //COB(1208): 120800     MOVE LIT-ALL-ALPHANUM-FROM-X TO LIT-ALL-ALPHANUM-FROM
  // 
  //     Only Alphabets,numbers and space allowed
    ws.litAllAlphanumFrom.setValue(ws.wsConstants.litAllAlphanumFromX);
    //COB(1210): 121000     INSPECT WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    //COB(1210): 121100       CONVERTING LIT-ALL-ALPHANUM-FROM
    //COB(1210): 121200               TO LIT-ALPHANUM-SPACES-TO
    ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.inspectAndConvertCharacters(ws.litAllAlphanumFrom.getValue(), ws.litAlphanumSpacesTo.getValue(), 0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt());
    //COB(1214): 121400     IF FUNCTION LENGTH(
    //COB(1214): 121500             FUNCTION TRIM(
    //COB(1214): 121600             WS-EDIT-ALPHANUM-ONLY(1:WS-EDIT-ALPHANUM-LENGTH)
    //COB(1214): 121700                            )) = 0
    if (ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumOnly.getString(0, ws.wsMiscStorage.wsGenericEdits.wsEditAlphanumLength.getInt()).trim().length().equals(0)) {
      //COB(1218): 121800        CONTINUE
      //do nothing
      //COB(1219): 121900     ELSE
    } else {
      //COB(1220): 122000        SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      //COB(1221): 122100        SET FLG-ALPHNANUM-NOT-OK  TO TRUE
      ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumNotOk(true);
      //COB(1222): 122200        IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        //COB(1223): 122300           STRING
        //COB(1223): 122400             FUNCTION TRIM(WS-EDIT-VARIABLE-NAME)
        //COB(1223): 122500             ' can have numbers or alphabets only.'
        //COB(1223): 122600             DELIMITED BY SIZE
        //COB(1223): 122700             INTO WS-RETURN-MSG
        //COB(1223): 122800           END-STRING
        ws.wsMiscStorage.wsReturnMsg.concat(ws.wsMiscStorage.wsGenericEdits.wsEditVariableName.trim(), " can have numbers or alphabets only.");
        //COB(1228): 122800           END-STRING
      //COB(1229): 122900        END-IF
      }
      //COB(1230): 123000        GO TO  1240-EDIT-ALPHANUM-REQD-EXIT
      return;
    //COB(1231): 123100     END-IF
    }
    //COB(1233): 123300     SET FLG-ALPHNANUM-ISVALID    TO TRUE
    ws.wsMiscStorage.wsGenericEdits.setFlgAlphnanumIsvalid(true);

  }

  /***/
  protected void _1290CrossEdits() {
    //COB(1241): 124100     IF FLG-TYPEFILTER-ISVALID
    //COB(1241): 124200     OR FLG-DESCFILTER-ISVALID
    if (ws.wsMiscStorage.flgTypefilterIsvalid()
        || ws.wsMiscStorage.flgDescfilterIsvalid()) {
      //COB(1243): 124300        CONTINUE
      //do nothing
      //COB(1244): 124400     ELSE
    } else {
      //COB(1245): 124500         GO TO 1290-CROSS-EDITS-EXIT
      return;
    //COB(1246): 124600     END-IF
    }
    //COB(1248): 124800     PERFORM 9100-CHECK-FILTERS
    //COB(1248): 124900        THRU 9100-CHECK-FILTERS-EXIT
    _9100CheckFilters();
    //COB(1251): 125100     IF WS-RECORDS-COUNT = 0
    if (ws.wsMiscStorage.wsOtherEditVars.wsRecordsCount.equals(0)) {
      //COB(1252): 125200        SET INPUT-ERROR TO TRUE
      ws.wsMiscStorage.setInputError(true);
      //COB(1253): 125300        IF FLG-TYPEFILTER-ISVALID
      if (ws.wsMiscStorage.flgTypefilterIsvalid()) {
        //COB(1254): 125400           SET FLG-TYPEFILTER-NOT-OK TO TRUE
        ws.wsMiscStorage.setFlgTypefilterNotOk(true);
      //COB(1255): 125500        END-IF
      }
      //COB(1257): 125700        IF FLG-DESCFILTER-ISVALID
      if (ws.wsMiscStorage.flgDescfilterIsvalid()) {
        //COB(1258): 125800           SET FLG-DESCFILTER-NOT-OK TO TRUE
        ws.wsMiscStorage.setFlgDescfilterNotOk(true);
      //COB(1259): 125900        END-IF
      }
      //COB(1262): 126200        SET FLG-PROTECT-SELECT-ROWS-YES TO TRUE
  // 
  // 
      ws.wsMiscStorage.cicsOutputEditVars.setFlgProtectSelectRowsYes(true);
      //COB(1263): 126300        MOVE
      //COB(1263): 126400        'No Records found for these filter conditions'
      //COB(1263): 126500                        TO WS-RETURN-MSG
      ws.wsMiscStorage.wsReturnMsg.setString("No Records found for these filter conditions");
      //COB(1266): 126600        GO TO 1290-CROSS-EDITS-EXIT
      return;
    //COB(1267): 126700     END-IF
    }

  }

  /***
  */
  protected void _2000SendMap() {
    //COB(1276): 127600     PERFORM 2100-SCREEN-INIT
    //COB(1276): 127700        THRU 2100-SCREEN-INIT-EXIT
  // 
    _2100ScreenInit();
    //COB(1278): 127800     PERFORM 2200-SETUP-ARRAY-ATTRIBS
    //COB(1278): 127900        THRU 2200-SETUP-ARRAY-ATTRIBS-EXIT
    _2200SetupArrayAttribs();
    //COB(1280): 128000     PERFORM 2300-SCREEN-ARRAY-INIT
    //COB(1280): 128100        THRU 2300-SCREEN-ARRAY-INIT-EXIT
    _2300ScreenArrayInit();
    //COB(1282): 128200     PERFORM 2400-SETUP-SCREEN-ATTRS
    //COB(1282): 128300        THRU 2400-SETUP-SCREEN-ATTRS-EXIT
    _2400SetupScreenAttrs();
    //COB(1284): 128400     PERFORM 2500-SETUP-MESSAGE
    //COB(1284): 128500        THRU 2500-SETUP-MESSAGE-EXIT
    _2500SetupMessage();
    //COB(1286): 128600     PERFORM 2600-SEND-SCREEN
    //COB(1286): 128700        THRU 2600-SEND-SCREEN-EXIT
    _2600SendScreen();

  }


  protected void _2100ScreenInit() {
    //COB(1294): 129400     MOVE LOW-VALUES             TO CTRTLIAO
    ws.cotrtli.ctrtliao.lowValues();
    //COB(1296): 129600     MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    //COB(1298): 129800     MOVE CCDA-TITLE01           TO TITLE01O OF CTRTLIAO
    ws.cotrtli.ctrtliao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    //COB(1299): 129900     MOVE CCDA-TITLE02           TO TITLE02O OF CTRTLIAO
    ws.cotrtli.ctrtliao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    //COB(1300): 130000     MOVE LIT-THISTRANID         TO TRNNAMEO OF CTRTLIAO
    ws.cotrtli.ctrtliao.trnnameo.setValue(ws.wsConstants.litThistranid);
    //COB(1301): 130100     MOVE LIT-THISPGM            TO PGMNAMEO OF CTRTLIAO
    ws.cotrtli.ctrtliao.pgmnameo.setValue(ws.wsConstants.litThispgm);
    //COB(1303): 130300     MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    //COB(1305): 130500     MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    //COB(1306): 130600     MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    //COB(1307): 130700     MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    //COB(1309): 130900     MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF CTRTLIAO
    ws.cotrtli.ctrtliao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    //COB(1311): 131100     MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    //COB(1312): 131200     MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    //COB(1313): 131300     MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    //COB(1315): 131500     MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF CTRTLIAO
    ws.cotrtli.ctrtliao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
    //COB(1318): 131800     MOVE WS-CA-SCREEN-NUM       TO PAGENOO  OF CTRTLIAO
  //     PAGE NUMBER
  // 
    ws.cotrtli.ctrtliao.pagenoo.setValue(ws.wsThisProgcommarea.wsCaPagingVariables.wsCaScreenNum);
    //COB(1320): 132000     SET WS-NO-INFO-MESSAGE      TO TRUE
    ws.wsMiscStorage.setWsNoInfoMessage(true);
    //COB(1321): 132100     MOVE WS-INFO-MSG            TO INFOMSGO OF CTRTLIAO
    ws.cotrtli.ctrtliao.infomsgo.setValue(ws.wsMiscStorage.wsInfoMsg);
    //COB(1322): 132200     MOVE DFHBMDAR               TO INFOMSGC OF CTRTLIAO
    ws.cotrtli.ctrtliao.infomsgc.setValue(Dfhbmsca.DFHBMDAR.getValue());

  }

  /***/
  protected void _2200SetupArrayAttribs() {
    //COB(1333): 133300     PERFORM VARYING I
    //COB(1333): 133400                FROM WS-MAX-SCREEN-LINES
    //COB(1333): 133500                  BY -1
    //COB(1333): 133600               UNTIL I = 0
  //     REPLACE BMS GENERATED MAP WITH PROVIDED COPYBOOK
  //     AND CLEAN UP REPETITIVE CODE 
  // 
    ws.wsMiscStorage.wsSubscriptVars.i.setValue(ws.wsConstants.wsMaxScreenLines);
    for (;!ws.wsMiscStorage.wsSubscriptVars.i.equals(0);ws.wsMiscStorage.wsSubscriptVars.i.add(-1)) {
      //COB(1337): 133700        MOVE DFHBMPRF                 TO TRTYPDA(I)
      ws.filler434.wsRowDatai.eachRowiAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).filler452.trtypda.setValue(Dfhbmsca.DFHBMPRF.getValue());
      //COB(1339): 133900        IF   WS-CA-EACH-ROW-OUT(I)    EQUAL LOW-VALUES
      //COB(1339): 134000        OR   FLG-PROTECT-SELECT-ROWS-YES
      if (ws.wsThisProgcommarea.filler386.filler388.wsCaScreenRowsOutAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsCaEachRowOut.isLowValues()
          || ws.wsMiscStorage.cicsOutputEditVars.flgProtectSelectRowsYes()) {
        //COB(1341): 134100           MOVE DFHBMPRO              TO TRTSELA (I)
        ws.filler434.wsRowDatai.eachRowiAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).filler440.trtsela.setValue(Dfhbmsca.DFHBMPRO.getValue());
        //COB(1342): 134200        ELSE
      } else {
        //COB(1343): 134300           IF WS-ROW-TRTSELECT-ERROR(I) = '1'
        if (ws.wsMiscStorage.filler192.wsEditSelectErrorsAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsRowTrtselectError.equals("1")) {
          //COB(1344): 134400              MOVE DFHRED             TO TRTSELC(I)
          ws.filler457.eachRowoAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtselc.setValue(Dfhbmsca.DFHRED.getValue());
          //COB(1345): 134500              MOVE -1                 TO TRTSELL(I)
          ws.filler434.wsRowDatai.eachRowiAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtsell.setValue(-1);
        //COB(1346): 134600           END-IF
        }
        //COB(1348): 134800           IF DELETE-REQUESTED-ON(I)
        //COB(1348): 134900           AND WS-ONLY-1-VALID-ACTION
        //COB(1348): 135000           AND FLG-BAD-ACTIONS-SELECTED-NO
        if (ws.wsMiscStorage.filler180.deleteRequestedOn(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1)
            && ws.wsMiscStorage.wsOnly1ValidAction()
            && ws.wsMiscStorage.flgBadActionsSelectedNo()) {
          //COB(1351): 135100              MOVE DFHNEUTR           TO TRTTYPC(I)
          //COB(1351): 135200                                         TRTYPDC(I)
          ws.filler457.eachRowoAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trttypc.setValue(Dfhbmsca.DFHNEUTR.getValue());
          ws.filler457.eachRowoAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtypdc.setValue(Dfhbmsca.DFHNEUTR.getValue());
          //COB(1353): 135300              MOVE -1                 TO TRTSELL(I)
          ws.filler434.wsRowDatai.eachRowiAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtsell.setValue(-1);
        //COB(1354): 135400           END-IF
        }
        //COB(1356): 135600           IF UPDATE-REQUESTED-ON(I)
        //COB(1356): 135700           AND WS-ONLY-1-VALID-ACTION
        //COB(1356): 135800           AND FLG-BAD-ACTIONS-SELECTED-NO
        if (ws.wsMiscStorage.filler180.updateRequestedOn(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1)
            && ws.wsMiscStorage.wsOnly1ValidAction()
            && ws.wsMiscStorage.flgBadActionsSelectedNo()) {
          //COB(1359): 135900              MOVE DFHNEUTR           TO TRTTYPC(I)
          ws.filler457.eachRowoAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trttypc.setValue(Dfhbmsca.DFHNEUTR.getValue());
          //COB(1360): 136000              IF  FLG-UPDATE-COMPLETED
          if (ws.wsMiscStorage.flgUpdateCompleted()) {
            //COB(1361): 136100                  MOVE -1             TO TRTSELL(I)
            ws.filler434.wsRowDatai.eachRowiAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtsell.setValue(-1);
            //COB(1362): 136200                  MOVE DFHNEUTR       TO TRTYPDC(I)
            ws.filler457.eachRowoAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtypdc.setValue(Dfhbmsca.DFHNEUTR.getValue());
            //COB(1363): 136300              ELSE
          } else {
            //COB(1364): 136400                  MOVE -1             TO TRTYPDL(I)
            ws.filler434.wsRowDatai.eachRowiAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtypdl.setValue(-1);
            //COB(1365): 136500                  MOVE DFHBMFSE       TO TRTYPDA(I)
            ws.filler434.wsRowDatai.eachRowiAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).filler452.trtypda.setValue(Dfhbmsca.DFHBMFSE.getValue());
            //COB(1366): 136600                  IF NOT FLG-ROW-DESCRIPTION-ISVALID
            if (!ws.wsMiscStorage.flgRowDescriptionIsvalid()) {
              //COB(1367): 136700                     MOVE DFHRED      TO TRTYPDC(I)
              ws.filler457.eachRowoAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtypdc.setValue(Dfhbmsca.DFHRED.getValue());
            //COB(1368): 136800                  END-IF
            }
          //COB(1369): 136900              END-IF
          }
        //COB(1370): 137000           END-IF
        }
        //COB(1371): 137100           MOVE DFHBMFSE              TO TRTSELA(I)
        ws.filler434.wsRowDatai.eachRowiAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).filler440.trtsela.setValue(Dfhbmsca.DFHBMFSE.getValue());
      //COB(1372): 137200        END-IF
      }
    //COB(1373): 137300     END-PERFORM
    }

  }

  /***
  *
  */
  protected void _2300ScreenArrayInit() {
    //COB(1386): 138600     PERFORM VARYING I FROM 1 BY 1 UNTIL I > WS-MAX-SCREEN-LINES
  //     USING REDEFINES TO AVOID UP REPETITIVE CODE 
  // 
    ws.wsMiscStorage.wsSubscriptVars.i.setValue(1);
    for (;!ws.wsMiscStorage.wsSubscriptVars.i.greaterThan(ws.wsConstants.wsMaxScreenLines);ws.wsMiscStorage.wsSubscriptVars.i.add(1)) {
      //COB(1388): 138800        IF   WS-CA-EACH-ROW-OUT(I)         EQUAL LOW-VALUES
      if (ws.wsThisProgcommarea.filler386.filler388.wsCaScreenRowsOutAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsCaEachRowOut.isLowValues()) {
        //COB(1389): 138900           CONTINUE
        //do nothing
        //COB(1390): 139000        ELSE
      } else {
        //COB(1391): 139100           IF  DELETE-REQUESTED-ON(I)
        //COB(1391): 139200           AND WS-ONLY-1-VALID-ACTION
        //COB(1391): 139300           AND FLG-BAD-ACTIONS-SELECTED-NO
        if (ws.wsMiscStorage.filler180.deleteRequestedOn(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1)
            && ws.wsMiscStorage.wsOnly1ValidAction()
            && ws.wsMiscStorage.flgBadActionsSelectedNo()) {
          //COB(1394): 139400               IF  FLG-DELETED-YES
          if (ws.wsMiscStorage.flgDeletedYes()) {
            //COB(1395): 139500                   SET SELECT-BLANK(I)          TO TRUE
            ws.wsMiscStorage.filler180.setSelectBlank(ws.wsMiscStorage.wsSubscriptVars.i.getInt(),true);
            //COB(1396): 139600               ELSE
          } else {
            //COB(1397): 139700                   SET CA-DELETE-REQUESTED      TO TRUE
            ws.wsThisProgcommarea.setCaDeleteRequested(true);
          //COB(1398): 139800               END-IF
          }
        //COB(1399): 139900           END-IF
        }
        //COB(1402): 140200           MOVE WS-CA-ROW-TR-CODE-OUT(I)        TO TRTTYPO(I)
  // 
  //           Type code
        ws.filler457.eachRowoAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trttypo.setValue(ws.wsThisProgcommarea.filler386.filler388.wsCaScreenRowsOutAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsCaEachRowOut.wsCaRowTrCodeOut);
        //COB(1404): 140400           IF UPDATE-REQUESTED-ON(I)
        //COB(1404): 140500           AND WS-ONLY-1-VALID-ACTION
        //COB(1404): 140600           AND FLG-BAD-ACTIONS-SELECTED-NO
  //           Type Description
        if (ws.wsMiscStorage.filler180.updateRequestedOn(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1)
            && ws.wsMiscStorage.wsOnly1ValidAction()
            && ws.wsMiscStorage.flgBadActionsSelectedNo()) {
          //COB(1407): 140700               IF  FLG-UPDATE-COMPLETED
          if (ws.wsMiscStorage.flgUpdateCompleted()) {
            //COB(1408): 140800                   SET SELECT-BLANK(I)          TO TRUE
            ws.wsMiscStorage.filler180.setSelectBlank(ws.wsMiscStorage.wsSubscriptVars.i.getInt(), true);
            //COB(1409): 140900               ELSE
          } else {
            //COB(1410): 141000                   SET CA-UPDATE-REQUESTED      TO TRUE
            ws.wsThisProgcommarea.setCaUpdateRequested(true);
          //COB(1411): 141100               END-IF
          }
          //COB(1412): 141200               IF CHANGES-HAVE-OCCURRED
          if (ws.wsMiscStorage.changesHaveOccurred()) {
            //COB(1413): 141300                  EVALUATE TRUE
              //COB(1414): 141400                      WHEN FLG-ROW-DESCRIPTION-BLANK
            if (ws.wsMiscStorage.flgRowDescriptionBlank()) {
              //COB(1415): 141500                           MOVE LIT-ASTERISK    TO TRTYPDO(I)
              ws.filler457.eachRowoAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtypdo.setValue(ws.wsConstants.litAsterisk);
              //COB(1416): 141600                      WHEN OTHER
            } else {
              //COB(1417): 141700                           MOVE WS-ROW-TR-DESC-IN(I)
              //COB(1417): 141800                                                TO TRTYPDO(I)
              ws.filler457.eachRowoAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtypdo.setValue(ws.wsMiscStorage.wsScreenDataIn.filler167.wsScreenRowsInAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsEachRowIn.wsEachTtypIn.wsRowTrDescIn);
            //COB(1419): 141900                  END-EVALUATE
            }
            //COB(1420): 142000               ELSE
          } else {
            //COB(1421): 142100                  MOVE WS-CA-ROW-TR-DESC-OUT(I) TO TRTYPDO(I)
            ws.filler457.eachRowoAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtypdo.setValue(ws.wsThisProgcommarea.filler386.filler388.wsCaScreenRowsOutAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsCaEachRowOut.wsCaRowTrDescOut);
          //COB(1422): 142200               END-IF
          }
          //COB(1423): 142300           ELSE
        } else {
          //COB(1424): 142400               MOVE WS-CA-ROW-TR-DESC-OUT(I)    TO TRTYPDO(I)
          ws.filler457.eachRowoAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtypdo.setValue(ws.wsThisProgcommarea.filler386.filler388.wsCaScreenRowsOutAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).wsCaEachRowOut.wsCaRowTrDescOut);
        //COB(1425): 142500           END-IF
        }
        //COB(1428): 142800           MOVE WS-EDIT-SELECT(I)               TO TRTSELO(I)
  // 
  //           Select flag because we may update it above
        ws.filler457.eachRowoAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1).trtselo.setValue(ws.wsMiscStorage.filler180.wsEditSelectAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1));
      //COB(1429): 142900        END-IF
      }
    //COB(1430): 143000     END-PERFORM
    }

  }

  /***
  */
  protected void _2400SetupScreenAttrs() {
    //COB(1440): 144000     IF EIBCALEN = 0
    //COB(1440): 144100     OR (CDEMO-PGM-ENTER
    //COB(1440): 144200     AND CDEMO-FROM-PROGRAM = LIT-ADMINPGM)
  //     INITIALIZE SEARCH CRITERIA
    if (dfheiblk.getEibcalen() == 0
        ||
        (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(ws.wsConstants.litAdminpgm)
        )) {
      //COB(1443): 144300        CONTINUE
      //do nothing
      //COB(1444): 144400     ELSE
    } else {
      //COB(1445): 144500        EVALUATE TRUE
        //COB(1446): 144600            WHEN  WS-ACTIONS-REQUESTED > 0
      if (ws.wsMiscStorage.wsActionsSelected.wsActionsRequested.greaterThan(0)) {
        //COB(1447): 144700               MOVE WS-IN-TYPE-CD    TO TRTYPEO OF CTRTLIAO
        ws.cotrtli.ctrtliao.trtypeo.setValue(ws.wsMiscStorage.wsScreenEditVars.wsInTypeCd);
        //COB(1448): 144800               MOVE DFHBMASF         TO TRTYPEA OF CTRTLIAI
        ws.cotrtli.ctrtliai.filler63.trtypea.setValue(Dfhbmsca.DFHBMASF.getValue());
        //COB(1449): 144900               MOVE DFHBLUE          TO TRTYPEC OF CTRTLIAO
        ws.cotrtli.ctrtliao.trtypec.setValue(Dfhbmsca.DFHBLUE.getValue());
        //COB(1450): 145000            WHEN FLG-TYPEFILTER-ISVALID
      } else if (ws.wsMiscStorage.flgTypefilterIsvalid()
        //COB(1451): 145100            WHEN FLG-TYPEFILTER-NOT-OK
        || ws.wsMiscStorage.flgTypefilterNotOk()) {
        //COB(1452): 145200               MOVE WS-IN-TYPE-CD    TO TRTYPEO OF CTRTLIAO
        ws.cotrtli.ctrtliao.trtypeo.setValue(ws.wsMiscStorage.wsScreenEditVars.wsInTypeCd);
        //COB(1453): 145300               MOVE DFHBMFSE         TO TRTYPEA OF CTRTLIAI
        ws.cotrtli.ctrtliai.filler63.trtypea.setValue(Dfhbmsca.DFHBMFSE.getValue());
        //COB(1454): 145400            WHEN WS-IN-TYPE-CD = 0
      } else if (ws.wsMiscStorage.wsScreenEditVars.wsInTypeCd.equals(0)) {
        //COB(1455): 145500               MOVE LOW-VALUES       TO TRTYPEO OF CTRTLIAO
        ws.cotrtli.ctrtliao.trtypeo.lowValues();
        //COB(1456): 145600            WHEN OTHER
      } else {
        //COB(1457): 145700              MOVE LOW-VALUES        TO TRTYPEO OF CTRTLIAO
        ws.cotrtli.ctrtliao.trtypeo.lowValues();
        //COB(1458): 145800              MOVE DFHBMFSE          TO TRTYPEA OF CTRTLIAI
        ws.cotrtli.ctrtliai.filler63.trtypea.setValue(Dfhbmsca.DFHBMFSE.getValue());
      //COB(1459): 145900        END-EVALUATE
      }
      //COB(1461): 146100        EVALUATE TRUE
        //COB(1462): 146200            WHEN WS-ACTIONS-REQUESTED > 0
      if (ws.wsMiscStorage.wsActionsSelected.wsActionsRequested.greaterThan(0)) {
        //COB(1463): 146300               MOVE WS-IN-TYPE-DESC  TO TRDESCO OF CTRTLIAO
        ws.cotrtli.ctrtliao.trdesco.setValue(ws.wsMiscStorage.wsScreenEditVars.wsInTypeDesc);
        //COB(1464): 146400               MOVE DFHBMASF         TO TRDESCA OF CTRTLIAI
        ws.cotrtli.ctrtliai.filler69.trdesca.setValue(Dfhbmsca.DFHBMASF.getValue());
        //COB(1465): 146500               MOVE DFHBLUE          TO TRDESCC OF CTRTLIAO
        ws.cotrtli.ctrtliao.trdescc.setValue(Dfhbmsca.DFHBLUE.getValue());
        //COB(1466): 146600            WHEN FLG-DESCFILTER-ISVALID
      } else if (ws.wsMiscStorage.flgDescfilterIsvalid()
        //COB(1467): 146700            WHEN FLG-DESCFILTER-NOT-OK
        || ws.wsMiscStorage.flgDescfilterNotOk()) {
        //COB(1468): 146800               MOVE WS-IN-TYPE-DESC  TO TRDESCO OF CTRTLIAO
        ws.cotrtli.ctrtliao.trdesco.setValue(ws.wsMiscStorage.wsScreenEditVars.wsInTypeDesc);
        //COB(1469): 146900               MOVE DFHBMFSE         TO TRDESCA OF CTRTLIAI
        ws.cotrtli.ctrtliai.filler69.trdesca.setValue(Dfhbmsca.DFHBMFSE.getValue());
        //COB(1470): 147000            WHEN OTHER
      } else {
        //COB(1471): 147100              MOVE DFHBMFSE          TO TRDESCA OF CTRTLIAI
        ws.cotrtli.ctrtliai.filler69.trdesca.setValue(Dfhbmsca.DFHBMFSE.getValue());
      //COB(1472): 147200        END-EVALUATE
      }
    //COB(1473): 147300     END-IF
    }
    //COB(1477): 147700     IF FLG-TYPEFILTER-NOT-OK
  // 
  //     POSITION CURSOR
  // 
    if (ws.wsMiscStorage.flgTypefilterNotOk()) {
      //COB(1478): 147800        MOVE  DFHRED                 TO TRTYPEC OF CTRTLIAO
      ws.cotrtli.ctrtliao.trtypec.setValue(Dfhbmsca.DFHRED.getValue());
      //COB(1479): 147900        MOVE  -1                     TO TRTYPEL OF CTRTLIAI
      ws.cotrtli.ctrtliai.trtypel.setValue(-1);
    //COB(1480): 148000     END-IF
    }
    //COB(1482): 148200     IF FLG-DESCFILTER-NOT-OK
    if (ws.wsMiscStorage.flgDescfilterNotOk()) {
      //COB(1483): 148300        MOVE  DFHRED                 TO TRDESCC OF CTRTLIAO
      ws.cotrtli.ctrtliao.trdescc.setValue(Dfhbmsca.DFHRED.getValue());
      //COB(1484): 148400        MOVE  -1                     TO TRDESCL OF CTRTLIAI
      ws.cotrtli.ctrtliai.trdescl.setValue(-1);
    //COB(1485): 148500     END-IF
    }
    //COB(1489): 148900     IF INPUT-OK
  // 
  // 
  //     IF NO ERRORS POSITION CURSOR
    if (ws.wsMiscStorage.inputOk()) {
      //COB(1490): 149000        IF WS-ACTIONS-REQUESTED > 0
      //COB(1490): 149100        AND NOT CCARD-AID-PFK07
      //COB(1490): 149200        AND NOT CCARD-AID-PFK08
      if (ws.wsMiscStorage.wsActionsSelected.wsActionsRequested.greaterThan(0)
          &&
          !ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk07()
          &&
          !ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk08()) {
        //COB(1493): 149300            CONTINUE
        //do nothing
        //COB(1494): 149400        ELSE
      } else {
        //COB(1495): 149500            MOVE   -1                 TO TRTYPEL OF CTRTLIAI
        ws.cotrtli.ctrtliai.trtypel.setValue(-1);
      //COB(1496): 149600        END-IF
      }
    //COB(1497): 149700     END-IF
    }

  }

  /***
  */
  protected void _2500SetupMessage() {
    //COB(1506): 150600     EVALUATE TRUE
  //     SETUP MESSAGE
      //COB(1507): 150700          WHEN FLG-DELETED-YES
    if (ws.wsMiscStorage.flgDeletedYes()) {
      //COB(1508): 150800               SET WS-INFORM-DELETE-SUCCESS TO TRUE
      ws.wsMiscStorage.setWsInformDeleteSuccess(true);
      //COB(1509): 150900          WHEN FLG-UPDATE-COMPLETED
    } else if (ws.wsMiscStorage.flgUpdateCompleted()) {
      //COB(1510): 151000               SET WS-INFORM-UPDATE-SUCCESS TO TRUE
      ws.wsMiscStorage.setWsInformUpdateSuccess(true);
      //COB(1511): 151100          WHEN FLG-TYPEFILTER-NOT-OK
    } else if (ws.wsMiscStorage.flgTypefilterNotOk()
      //COB(1512): 151200          WHEN FLG-DESCFILTER-NOT-OK
      || ws.wsMiscStorage.flgDescfilterNotOk()) {
      //COB(1513): 151300            CONTINUE
      //do nothing
      //COB(1514): 151400          WHEN CCARD-AID-ENTER
      //COB(1514): 151500          AND WS-DELETES-REQUESTED > 0
      //COB(1514): 151600          AND WS-ONLY-1-ACTION
      //COB(1514): 151700          AND WS-ONLY-1-VALID-ACTION
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidEnter() && ws.wsMiscStorage.wsActionsSelected.wsDeletesRequested.greaterThan(0) && ws.wsMiscStorage.wsActionsSelected.wsOnly1Action() && ws.wsMiscStorage.wsOnly1ValidAction()) {
      //COB(1518): 151800             IF  WS-NO-INFO-MESSAGE
      //COB(1518): 151900             AND FLG-TYPEFILTER-CHANGED-NO
      //COB(1518): 152000             AND FLG-DESCFILTER-CHANGED-NO
      if (ws.wsMiscStorage.wsNoInfoMessage()
          && ws.wsMiscStorage.flgTypefilterChangedNo()
          && ws.wsMiscStorage.flgDescfilterChangedNo()) {
        //COB(1521): 152100                SET WS-INFORM-DELETE        TO TRUE
        ws.wsMiscStorage.setWsInformDelete(true);
      //COB(1522): 152200             END-IF
      }
      //COB(1523): 152300          WHEN CCARD-AID-ENTER
      //COB(1523): 152400          AND WS-UPDATES-REQUESTED > 0
      //COB(1523): 152500          AND WS-ONLY-1-ACTION
      //COB(1523): 152600          AND WS-ONLY-1-VALID-ACTION
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidEnter() && ws.wsMiscStorage.wsActionsSelected.wsUpdatesRequested.greaterThan(0) && ws.wsMiscStorage.wsActionsSelected.wsOnly1Action() && ws.wsMiscStorage.wsOnly1ValidAction()) {
      //COB(1527): 152700             IF  WS-NO-INFO-MESSAGE
      //COB(1527): 152800             AND FLG-TYPEFILTER-CHANGED-NO
      //COB(1527): 152900             AND FLG-DESCFILTER-CHANGED-NO
      if (ws.wsMiscStorage.wsNoInfoMessage()
          && ws.wsMiscStorage.flgTypefilterChangedNo()
          && ws.wsMiscStorage.flgDescfilterChangedNo()) {
        //COB(1530): 153000                SET WS-INFORM-UPDATE        TO TRUE
        ws.wsMiscStorage.setWsInformUpdate(true);
      //COB(1531): 153100             END-IF
      }
      //COB(1532): 153200          WHEN CCARD-AID-PFK07
      //COB(1532): 153300              AND CA-FIRST-PAGE
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk07() && ws.wsThisProgcommarea.wsCaPagingVariables.caFirstPage()) {
      //COB(1534): 153400            MOVE 'No previous pages to display'
      //COB(1534): 153500            TO WS-RETURN-MSG
      ws.wsMiscStorage.wsReturnMsg.setString("No previous pages to display");
      //COB(1536): 153600          WHEN CCARD-AID-PFK08
      //COB(1536): 153700           AND CA-NEXT-PAGE-NOT-EXISTS
      //COB(1536): 153800           AND CA-LAST-PAGE-SHOWN
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk08() && ws.wsThisProgcommarea.wsCaPagingVariables.caNextPageNotExists() && ws.wsThisProgcommarea.wsCaPagingVariables.caLastPageShown()) {
      //COB(1539): 153900            MOVE 'No more pages to display'
      //COB(1539): 154000            TO WS-RETURN-MSG
      ws.wsMiscStorage.wsReturnMsg.setString("No more pages to display");
      //COB(1541): 154100          WHEN CCARD-AID-PFK08
      //COB(1541): 154200           AND CA-NEXT-PAGE-NOT-EXISTS
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk08() && ws.wsThisProgcommarea.wsCaPagingVariables.caNextPageNotExists()) {
      //COB(1543): 154300            IF WS-NO-INFO-MESSAGE
      if (ws.wsMiscStorage.wsNoInfoMessage()) {
        //COB(1544): 154400               SET WS-INFORM-REC-ACTIONS    TO TRUE
        ws.wsMiscStorage.setWsInformRecActions(true);
      //COB(1545): 154500            END-IF
      }
      //COB(1546): 154600            IF  CA-LAST-PAGE-NOT-SHOWN
      //COB(1546): 154700            AND CA-NEXT-PAGE-NOT-EXISTS
      if (ws.wsThisProgcommarea.wsCaPagingVariables.caLastPageNotShown()
          && ws.wsThisProgcommarea.wsCaPagingVariables.caNextPageNotExists()) {
        //COB(1548): 154800                SET CA-LAST-PAGE-SHOWN      TO TRUE
        ws.wsThisProgcommarea.wsCaPagingVariables.setCaLastPageShown(true);
      //COB(1549): 154900            END-IF
      }
      //COB(1550): 155000          WHEN WS-NO-INFO-MESSAGE
    } else if (ws.wsMiscStorage.wsNoInfoMessage()
      //COB(1551): 155100          WHEN CA-NEXT-PAGE-EXISTS
      || ws.wsThisProgcommarea.wsCaPagingVariables.caNextPageExists()) {
      //COB(1552): 155200            SET WS-INFORM-REC-ACTIONS       TO TRUE
      ws.wsMiscStorage.setWsInformRecActions(true);
      //COB(1553): 155300          WHEN OTHER
    } else {
      //COB(1554): 155400             SET WS-NO-INFO-MESSAGE         TO TRUE
      ws.wsMiscStorage.setWsNoInfoMessage(true);
    //COB(1555): 155500     END-EVALUATE
    }
    //COB(1557): 155700     MOVE WS-RETURN-MSG          TO ERRMSGO OF CTRTLIAO
    ws.cotrtli.ctrtliao.errmsgo.setValue(ws.wsMiscStorage.wsReturnMsg);
    //COB(1562): 156200     COMPUTE WS-STRING-LEN =
    //COB(1562): 156300             FUNCTION LENGTH(
    //COB(1562): 156400                      FUNCTION TRIM(WS-INFO-MSG)
    //COB(1562): 156500                            )
  // 
  // 
  //  Center justify the text
  // 
    ws.wsMiscStorage.wsStringFormatVars.wsStringLen.setValue(ws.wsMiscStorage.wsInfoMsg.trim().length());
    //COB(1566): 156600     COMPUTE WS-STRING-MID =
    //COB(1566): 156700            (FUNCTION LENGTH(WS-INFO-MSG)
    //COB(1566): 156800                          - WS-STRING-LEN) / 2 + 1
    ws.wsMiscStorage.wsStringFormatVars.wsStringMid.setValue(((new BigDecimal(ws.wsMiscStorage.wsInfoMsg.length()).subtract(ws.wsMiscStorage.wsStringFormatVars.wsStringLen.getValue())).divide(new BigDecimal("2"), 0, RoundingMode.HALF_UP).add(new BigDecimal("1"))));
    //COB(1569): 156900     MOVE WS-INFO-MSG(1:WS-STRING-LEN)
    //COB(1569): 157000       TO WS-STRING-OUT(WS-STRING-MID:
    //COB(1569): 157100                        WS-STRING-LEN)
    ws.wsMiscStorage.wsStringFormatVars.wsStringOut.setValue(ws.wsMiscStorage.wsInfoMsg, 0, ws.wsMiscStorage.wsStringFormatVars.wsStringMid.getInt() - 1, ws.wsMiscStorage.wsStringFormatVars.wsStringLen.getInt());
    //COB(1575): 157500     IF  NOT WS-NO-INFO-MESSAGE
    //COB(1575): 157600     AND NOT WS-MESG-NO-RECORDS-FOUND
  // 
  // 
  // 
    if (!ws.wsMiscStorage.wsNoInfoMessage()
        &&
        !ws.wsMiscStorage.wsMesgNoRecordsFound()) {
      //COB(1577): 157700        MOVE WS-STRING-OUT      TO INFOMSGO OF CTRTLIAO
      ws.cotrtli.ctrtliao.infomsgo.setValue(ws.wsMiscStorage.wsStringFormatVars.wsStringOut);
      //COB(1578): 157800        MOVE DFHNEUTR           TO INFOMSGC OF CTRTLIAO
      ws.cotrtli.ctrtliao.infomsgc.setValue(Dfhbmsca.DFHNEUTR.getValue());
    //COB(1579): 157900     END-IF
    }

  }

  /***
  */
  protected void _2600SendScreen() {
    //COB(1588): 158800     EXEC CICS SEND MAP(LIT-THISMAP)
    //COB(1588): 158900                    MAPSET(LIT-THISMAPSET)
    //COB(1588): 159000                    FROM(CTRTLIAO)
    //COB(1588): 159100                    CURSOR
    //COB(1588): 159200                    ERASE
    //COB(1588): 159300                    RESP(WS-RESP-CD)
    //COB(1588): 159400                    FREEKB
    //COB(1588): 159500     END-EXEC
    supernaut.sendMap(ws.wsConstants.litThismap.getValue()) //
            .mapset(ws.wsConstants.litThismapset.getValue()) //
            .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
            .from(ws.cotrtli.ctrtliao) //
            .freekb() //
            .erase() //
            .cursor() //
            .exec();

  }

  /***
  *
  */
  protected void _8000ReadForward() {
    //COB(1604): 160400     MOVE LOW-VALUES           TO WS-CA-ALL-ROWS-OUT
    ws.wsThisProgcommarea.filler386.wsCaAllRowsOut.lowValues();
    //COB(1609): 160900     PERFORM 9400-OPEN-FORWARD-CURSOR
    //COB(1609): 161000        THRU 9400-OPEN-FORWARD-CURSOR-EXIT
  // 
  // ****************************************************************
  //     Start Reading
  // ****************************************************************
    _9400OpenForwardCursor();
    //COB(1612): 161200     IF WS-DB2-ERROR
    if (ws.wsMiscStorage.wsDb2CommonVars.wsDb2Error()) {
      //COB(1613): 161300        GO TO 8000-READ-FORWARD-EXIT
      return;
    //COB(1614): 161400     END-IF
    }
    //COB(1618): 161800     MOVE ZEROES TO WS-ROW-NUMBER
  // ****************************************************************
  //     Loop through records and fetch max screen records
  // ****************************************************************
    ws.wsMiscStorage.wsRowNumber.zeros();
    //COB(1619): 161900     SET CA-NEXT-PAGE-EXISTS    TO TRUE
    ws.wsThisProgcommarea.wsCaPagingVariables.setCaNextPageExists(true);
    //COB(1620): 162000     SET MORE-RECORDS-TO-READ   TO TRUE
    ws.wsMiscStorage.setMoreRecordsToRead(true);
    //COB(1622): 162200     PERFORM UNTIL READ-LOOP-EXIT
    while (!ws.wsMiscStorage.readLoopExit()) {
      //COB(1624): 162400     INITIALIZE DCLTRANSACTION-TYPE
      ws.dcltrtyp.dcltransactionType.initialize();
      //COB(1626): 162600     EXEC SQL
      //COB(1626): 162700          FETCH C-TR-TYPE-FORWARD
      //COB(1626): 162800          INTO :DCL-TR-TYPE
      //COB(1626): 162900              ,:DCL-TR-DESCRIPTION
      //COB(1626): 163000     END-EXEC
      cTrTypeForward.into.add(ws.dcltrtyp.dcltransactionType.dclTrType) // 
.add(ws.dcltrtyp.dcltransactionType.dclTrDescription);
      cTrTypeForward.fetch();
      //COB(1632): 163200     MOVE SQLCODE               TO WS-DISP-SQLCODE
      ws.wsMiscStorage.wsDb2CommonVars.wsDispSqlcode.setValue(sqlca.sqlcode);
      //COB(1634): 163400     EVALUATE TRUE
        //COB(1635): 163500         WHEN SQLCODE = ZERO
      if (sqlca.sqlcode.equals(0)) {
        //COB(1636): 163600             ADD 1              TO WS-ROW-NUMBER
        ws.wsMiscStorage.wsRowNumber.add(1);
        //COB(1638): 163800             MOVE DCL-TR-TYPE   TO WS-CA-ROW-TR-CODE-OUT(
        //COB(1638): 163900             WS-ROW-NUMBER)
        ws.wsThisProgcommarea.filler386.filler388.wsCaScreenRowsOutAtIndex(ws.wsMiscStorage.wsRowNumber.getInt() - 1).wsCaEachRowOut.wsCaRowTrCodeOut.setValue(ws.dcltrtyp.dcltransactionType.dclTrType);
        //COB(1641): 164100             MOVE DCL-TR-DESCRIPTION-TEXT
        //COB(1641): 164200                                TO WS-CA-ROW-TR-DESC-OUT(
        //COB(1641): 164300                                WS-ROW-NUMBER)
        ws.wsThisProgcommarea.filler386.filler388.wsCaScreenRowsOutAtIndex(ws.wsMiscStorage.wsRowNumber.getInt() - 1).wsCaEachRowOut.wsCaRowTrDescOut.setValue(ws.dcltrtyp.dcltransactionType.dclTrDescription.dclTrDescriptionText);
        //COB(1644): 164400             IF WS-ROW-NUMBER = 1
        if (ws.wsMiscStorage.wsRowNumber.equals(1)) {
          //COB(1645): 164500                MOVE DCL-TR-TYPE  TO WS-CA-FIRST-TR-CODE
          ws.wsThisProgcommarea.wsCaPagingVariables.wsCaFirstTtypekey.wsCaFirstTrCode.setValue(ws.dcltrtyp.dcltransactionType.dclTrType);
          //COB(1646): 164600                IF   WS-CA-SCREEN-NUM = 0
          if (ws.wsThisProgcommarea.wsCaPagingVariables.wsCaScreenNum.equals(0)) {
            //COB(1647): 164700                     ADD   +1     TO WS-CA-SCREEN-NUM
            ws.wsThisProgcommarea.wsCaPagingVariables.wsCaScreenNum.add(1);
            //COB(1648): 164800                ELSE
          } else {
            //COB(1649): 164900                    CONTINUE
            //do nothing
          //COB(1650): 165000                END-IF
          }
          //COB(1651): 165100             ELSE
        } else {
          //COB(1652): 165200                CONTINUE
          //do nothing
        //COB(1653): 165300             END-IF
        }
        //COB(1657): 165700             IF WS-ROW-NUMBER = WS-MAX-SCREEN-LINES
  // *****************************************************************
  //             Max Screen size
  // *****************************************************************
        if (ws.wsMiscStorage.wsRowNumber.equals(ws.wsConstants.wsMaxScreenLines)) {
          //COB(1658): 165800                SET READ-LOOP-EXIT  TO TRUE
          ws.wsMiscStorage.setReadLoopExit(true);
          //COB(1659): 165900                MOVE DCL-TR-TYPE    TO WS-CA-LAST-TR-CODE
          ws.wsThisProgcommarea.wsCaPagingVariables.wsCaLastTtypekey.wsCaLastTrCode.setValue(ws.dcltrtyp.dcltransactionType.dclTrType);
          //COB(1661): 166100                EXEC SQL
          //COB(1661): 166200                         FETCH C-TR-TYPE-FORWARD
          //COB(1661): 166300                         INTO :DCL-TR-TYPE
          //COB(1661): 166400                             ,:DCL-TR-DESCRIPTION
          //COB(1661): 166500                END-EXEC
          cTrTypeForward.into.add(ws.dcltrtyp.dcltransactionType.dclTrType) // 
.add(ws.dcltrtyp.dcltransactionType.dclTrDescription);
          cTrTypeForward.fetch();
          //COB(1667): 166700                MOVE SQLCODE        TO WS-DISP-SQLCODE
          ws.wsMiscStorage.wsDb2CommonVars.wsDispSqlcode.setValue(sqlca.sqlcode);
          //COB(1669): 166900                EVALUATE TRUE
            //COB(1670): 167000                   WHEN SQLCODE = ZERO
          if (sqlca.sqlcode.equals(0)) {
            //COB(1671): 167100                        SET CA-NEXT-PAGE-EXISTS
            //COB(1671): 167200                                          TO TRUE
            ws.wsThisProgcommarea.wsCaPagingVariables.setCaNextPageExists(true);
            //COB(1673): 167300                        MOVE DCL-TR-TYPE  TO WS-CA-LAST-TR-CODE
            ws.wsThisProgcommarea.wsCaPagingVariables.wsCaLastTtypekey.wsCaLastTrCode.setValue(ws.dcltrtyp.dcltransactionType.dclTrType);
            //COB(1674): 167400                   WHEN SQLCODE = +100
          } else if (sqlca.sqlcode.equals(100)) {
            //COB(1675): 167500                      SET CA-NEXT-PAGE-NOT-EXISTS     TO TRUE
            ws.wsThisProgcommarea.wsCaPagingVariables.setCaNextPageNotExists(true);
            //COB(1677): 167700                      IF WS-RETURN-MSG-OFF
            //COB(1677): 167800                      AND CCARD-AID-PFK08
            if (ws.wsMiscStorage.wsReturnMsgOff()
                && ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk08()) {
              //COB(1679): 167900                          SET WS-MESG-NO-MORE-RECORDS TO TRUE
              ws.wsMiscStorage.setWsMesgNoMoreRecords(true);
            //COB(1680): 168000                      END-IF
            }
            //COB(1681): 168100                   WHEN OTHER
          } else {
            //COB(1684): 168400                      SET READ-LOOP-EXIT      TO TRUE
  //                      This is some kind of error. Close Cursor
  //                      And exit
            ws.wsMiscStorage.setReadLoopExit(true);
            //COB(1685): 168500                      IF WS-RETURN-MSG-OFF
            if (ws.wsMiscStorage.wsReturnMsgOff()) {
              //COB(1686): 168600                         MOVE 'C-TR-TYPE-FORWARD fetch'
              //COB(1686): 168700                                              TO
              //COB(1686): 168800                                            WS-DB2-CURRENT-ACTION
              ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.setString("C-TR-TYPE-FORWARD fetch");
              //COB(1689): 168900                         PERFORM 9999-FORMAT-DB2-MESSAGE
              //COB(1689): 169000                            THRU 9999-FORMAT-DB2-MESSAGE-EXIT
              _9999FormatDb2Message();
            //COB(1691): 169100                      END-IF
            }
          //COB(1692): 169200                END-EVALUATE
          }
        //COB(1693): 169300            END-IF
        }
        //COB(1694): 169400        WHEN SQLCODE = +100
      } else if (sqlca.sqlcode.equals(100)) {
        //COB(1695): 169500            SET READ-LOOP-EXIT              TO TRUE
        ws.wsMiscStorage.setReadLoopExit(true);
        //COB(1696): 169600            SET CA-NEXT-PAGE-NOT-EXISTS     TO TRUE
        ws.wsThisProgcommarea.wsCaPagingVariables.setCaNextPageNotExists(true);
        //COB(1697): 169700            MOVE DCL-TR-TYPE                TO WS-CA-LAST-TR-CODE
        ws.wsThisProgcommarea.wsCaPagingVariables.wsCaLastTtypekey.wsCaLastTrCode.setValue(ws.dcltrtyp.dcltransactionType.dclTrType);
        //COB(1698): 169800            IF WS-RETURN-MSG-OFF
        //COB(1698): 169900            AND CCARD-AID-PFK08
        if (ws.wsMiscStorage.wsReturnMsgOff()
            && ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk08()) {
          //COB(1700): 170000               SET  WS-MESG-NO-MORE-RECORDS     TO TRUE
          ws.wsMiscStorage.setWsMesgNoMoreRecords(true);
        //COB(1701): 170100            END-IF
        }
        //COB(1702): 170200            IF WS-CA-SCREEN-NUM = 1
        //COB(1702): 170300            AND WS-ROW-NUMBER = 0
        if (ws.wsThisProgcommarea.wsCaPagingVariables.wsCaScreenNum.equals(1)
            && ws.wsMiscStorage.wsRowNumber.equals(0)) {
          //COB(1704): 170400                SET WS-MESG-NO-RECORDS-FOUND    TO TRUE
          ws.wsMiscStorage.setWsMesgNoRecordsFound(true);
        //COB(1705): 170500            END-IF
        }
        //COB(1706): 170600         WHEN OTHER
      } else {
        //COB(1709): 170900            SET READ-LOOP-EXIT             TO TRUE
  //            This is some kind of error. Change to END BR
  //            And exit
        ws.wsMiscStorage.setReadLoopExit(true);
        //COB(1710): 171000            SET WS-DB2-ERROR               TO TRUE
        ws.wsMiscStorage.wsDb2CommonVars.setWsDb2Error(true);
        //COB(1711): 171100            IF WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          //COB(1712): 171200              MOVE 'C-TR-TYPE-FORWARD close'
          //COB(1712): 171300                              TO WS-DB2-CURRENT-ACTION
          ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.setString("C-TR-TYPE-FORWARD close");
          //COB(1715): 171500              PERFORM 9999-FORMAT-DB2-MESSAGE
          //COB(1715): 171600                 THRU 9999-FORMAT-DB2-MESSAGE-EXIT
          _9999FormatDb2Message();
        //COB(1717): 171700             END-IF
        }
      //COB(1718): 171800     END-EVALUATE
      }
    //COB(1719): 171900     END-PERFORM
    }
    //COB(1721): 172100     PERFORM 9450-CLOSE-FORWARD-CURSOR
    //COB(1721): 172200        THRU 9450-CLOSE-FORWARD-CURSOR-EXIT
    _9450CloseForwardCursor();

  }


  protected void _8100ReadBackwards() {
    //COB(1729): 172900     MOVE LOW-VALUES           TO WS-CA-ALL-ROWS-OUT
    ws.wsThisProgcommarea.filler386.wsCaAllRowsOut.lowValues();
    //COB(1731): 173100     MOVE WS-CA-FIRST-TTYPEKEY TO WS-CA-LAST-TTYPEKEY
    ws.wsThisProgcommarea.wsCaPagingVariables.wsCaLastTtypekey.setValue(ws.wsThisProgcommarea.wsCaPagingVariables.wsCaFirstTtypekey);
    //COB(1735): 173500     COMPUTE WS-ROW-NUMBER =
    //COB(1735): 173600                             WS-MAX-SCREEN-LINES
    //COB(1735): 173700     END-COMPUTE
  // ****************************************************************
  //     Loop through records and fetch max screen records
  // ****************************************************************
    ws.wsMiscStorage.wsRowNumber.setValue(ws.wsConstants.wsMaxScreenLines.getInt());
    //COB(1738): 173800     SET CA-NEXT-PAGE-EXISTS    TO TRUE
    ws.wsThisProgcommarea.wsCaPagingVariables.setCaNextPageExists(true);
    //COB(1739): 173900     SET MORE-RECORDS-TO-READ   TO TRUE
    ws.wsMiscStorage.setMoreRecordsToRead(true);
    //COB(1746): 174600     PERFORM 9500-OPEN-BACKWARD-CURSOR
    //COB(1746): 174700        THRU 9500-OPEN-BACKWARD-CURSOR-EXIT
  // 
  // ****************************************************************
  //     Now we show the records from previous set.
  // ****************************************************************
  //     Start Reading Backwards
  // ****************************************************************
    _9500OpenBackwardCursor();
    //COB(1749): 174900     PERFORM UNTIL READ-LOOP-EXIT
    while (!ws.wsMiscStorage.readLoopExit()) {
      //COB(1751): 175100     INITIALIZE DCLTRANSACTION-TYPE
      ws.dcltrtyp.dcltransactionType.initialize();
      //COB(1753): 175300     EXEC SQL
      //COB(1753): 175400          FETCH C-TR-TYPE-BACKWARD
      //COB(1753): 175500          INTO :DCL-TR-TYPE
      //COB(1753): 175600              ,:DCL-TR-DESCRIPTION
      //COB(1753): 175700     END-EXEC
      cTrTypeBackward.into.add(ws.dcltrtyp.dcltransactionType.dclTrType) // 
.add(ws.dcltrtyp.dcltransactionType.dclTrDescription);
      cTrTypeBackward.fetch();
      //COB(1759): 175900     MOVE SQLCODE               TO WS-DISP-SQLCODE
      ws.wsMiscStorage.wsDb2CommonVars.wsDispSqlcode.setValue(sqlca.sqlcode);
      //COB(1761): 176100     EVALUATE TRUE
        //COB(1762): 176200         WHEN SQLCODE = ZERO
      if (sqlca.sqlcode.equals(0)) {
        //COB(1763): 176300              MOVE DCL-TR-TYPE
        //COB(1763): 176400                          TO WS-CA-ROW-TR-CODE-OUT(WS-ROW-NUMBER)
        ws.wsThisProgcommarea.filler386.filler388.wsCaScreenRowsOutAtIndex(ws.wsMiscStorage.wsRowNumber.getInt() - 1).wsCaEachRowOut.wsCaRowTrCodeOut.setValue(ws.dcltrtyp.dcltransactionType.dclTrType);
        //COB(1765): 176500              MOVE DCL-TR-DESCRIPTION-TEXT
        //COB(1765): 176600                          TO
        //COB(1765): 176700                          WS-CA-ROW-TR-DESC-OUT(WS-ROW-NUMBER)
        ws.wsThisProgcommarea.filler386.filler388.wsCaScreenRowsOutAtIndex(ws.wsMiscStorage.wsRowNumber.getInt() - 1).wsCaEachRowOut.wsCaRowTrDescOut.setValue(ws.dcltrtyp.dcltransactionType.dclTrDescription.dclTrDescriptionText);
        //COB(1769): 176900              SUBTRACT 1  FROM WS-ROW-NUMBER
        ws.wsMiscStorage.wsRowNumber.subtract(1);
        //COB(1770): 177000              IF WS-ROW-NUMBER = 0
        if (ws.wsMiscStorage.wsRowNumber.equals(0)) {
          //COB(1771): 177100                 SET READ-LOOP-EXIT  TO TRUE
          ws.wsMiscStorage.setReadLoopExit(true);
          //COB(1772): 177200                 MOVE DCL-TR-TYPE
          //COB(1772): 177300                          TO WS-CA-FIRST-TR-CODE
          ws.wsThisProgcommarea.wsCaPagingVariables.wsCaFirstTtypekey.wsCaFirstTrCode.setValue(ws.dcltrtyp.dcltransactionType.dclTrType);
          //COB(1774): 177400              ELSE
        } else {
          //COB(1775): 177500                 CONTINUE
          //do nothing
        //COB(1776): 177600              END-IF
        }
        //COB(1777): 177700         WHEN OTHER
      } else {
        //COB(1780): 178000            SET READ-LOOP-EXIT             TO TRUE
  //            This is some kind of error. Change to END BR
  //            And exit
        ws.wsMiscStorage.setReadLoopExit(true);
        //COB(1781): 178100            SET WS-DB2-ERROR               TO TRUE
        ws.wsMiscStorage.wsDb2CommonVars.setWsDb2Error(true);
        //COB(1783): 178300            IF WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          //COB(1784): 178400               MOVE 'Error on fetch Cursor C-TR-TYPE-BACKWARD'
          //COB(1784): 178500                                        TO WS-DB2-CURRENT-ACTION
          ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.setString("Error on fetch Cursor C-TR-TYPE-BACKWARD");
          //COB(1786): 178600               PERFORM 9999-FORMAT-DB2-MESSAGE
          //COB(1786): 178700                  THRU 9999-FORMAT-DB2-MESSAGE-EXIT
          _9999FormatDb2Message();
        //COB(1789): 178900             END-IF
        }
      //COB(1790): 179000     END-EVALUATE
      }
    //COB(1791): 179100     END-PERFORM
    }

  }

  /***/
  protected void _8100ReadBackwardsExit() {
    //COB(1795): 179500     PERFORM 9550-CLOSE-BACK-CURSOR
    //COB(1795): 179600        THRU 9550-CLOSE-BACK-CURSOR-EXIT
    _9550CloseBackCursor();
    //COB(1798): 179800     EXIT
    return;

  }

  /***/
  protected void _9100CheckFilters() {
    //COB(1803): 180300     EXEC SQL
    //COB(1803): 180400          SELECT COUNT(1)
    //COB(1803): 180500            INTO :WS-RECORDS-COUNT
    //COB(1803): 180600            FROM CARDDEMO.TRANSACTION_TYPE
    //COB(1803): 180700           WHERE ((:WS-EDIT-TYPE-FLAG = '1'
    //COB(1803): 180900                 AND  TR_TYPE = :WS-TYPE-CD-FILTER)
    //COB(1803): 181000                 OR  :WS-EDIT-TYPE-FLAG <> '1')
    //COB(1803): 181200             AND
    //COB(1803): 181300                ((:WS-EDIT-DESC-FLAG = '1'
    //COB(1803): 181500                  AND TR_DESCRIPTION LIKE
    //COB(1803): 181600                        TRIM(:WS-TYPE-DESC-FILTER))
    //COB(1803): 181700                  OR :WS-EDIT-DESC-FLAG <> '1')
    //COB(1803): 181900     END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
    sql.setCommand("SELECT COUNT (1) FROM CARDDEMO.TRANSACTION_TYPE WHERE ((? = '1' AND TR_TYPE = ?) OR ? <> '1') AND ((? = '1' AND TR_DESCRIPTION LIKE TRIM (?)) OR ? <> '1')");
    sql.into.add(ws.wsMiscStorage.wsOtherEditVars.wsRecordsCount);
    sql.where.add(ws.wsMiscStorage.wsEditTypeFlag) // 
.add(ws.wsMiscStorage.wsDataFilters.wsTypeCdFilter) // 
.add(ws.wsMiscStorage.wsEditTypeFlag) // 
.add(ws.wsMiscStorage.wsEditDescFlag) // 
.add(ws.wsMiscStorage.wsDataFilters.wsTypeDescFilter) // 
.add(ws.wsMiscStorage.wsEditDescFlag);
    sql.execute();
    }
    //COB(1817): 182100     MOVE SQLCODE                             TO WS-DISP-SQLCODE
    ws.wsMiscStorage.wsDb2CommonVars.wsDispSqlcode.setValue(sqlca.sqlcode);
    //COB(1819): 182300     EVALUATE TRUE
      //COB(1820): 182400         WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      //COB(1821): 182500             CONTINUE
      //do nothing
      //COB(1822): 182600         WHEN OTHER
    } else {
      //COB(1823): 182700            SET INPUT-ERROR                   TO TRUE
      ws.wsMiscStorage.setInputError(true);
      //COB(1825): 182900            IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        //COB(1826): 183000                MOVE 'Error reading TRANSACTION_TYPE table '
        //COB(1826): 183100                                         TO WS-DB2-CURRENT-ACTION
        ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.setString("Error reading TRANSACTION_TYPE table ");
        //COB(1828): 183200                PERFORM 9999-FORMAT-DB2-MESSAGE
        //COB(1828): 183300                   THRU 9999-FORMAT-DB2-MESSAGE-EXIT
        _9999FormatDb2Message();
      //COB(1830): 183400            END-IF
      }
      //COB(1831): 183500            GO TO 9100-CHECK-FILTERS-EXIT
      return;
    //COB(1832): 183600     END-EVALUATE
    }

  }


  protected void _9200UpdateRecord() {
    //COB(1839): 184300     MOVE WS-ROW-TR-CODE-IN (I-SELECTED)
    //COB(1839): 184400                             TO DCL-TR-TYPE
    ws.dcltrtyp.dcltransactionType.dclTrType.setValue(ws.wsMiscStorage.wsScreenDataIn.filler167.wsScreenRowsInAtIndex(ws.wsMiscStorage.wsSubscriptVars.iSelected.getInt() - 1).wsEachRowIn.wsEachTtypIn.wsRowTrCodeIn);
    //COB(1841): 184500     MOVE FUNCTION TRIM(WS-ROW-TR-DESC-IN (I-SELECTED))
    //COB(1841): 184600                             TO DCL-TR-DESCRIPTION-TEXT
    ws.dcltrtyp.dcltransactionType.dclTrDescription.dclTrDescriptionText.setValue(ws.wsMiscStorage.wsScreenDataIn.filler167.wsScreenRowsInAtIndex(ws.wsMiscStorage.wsSubscriptVars.iSelected.getInt() - 1).wsEachRowIn.wsEachTtypIn.wsRowTrDescIn.trim());
    //COB(1843): 184700     COMPUTE DCL-TR-DESCRIPTION-LEN
    //COB(1843): 184800      = FUNCTION LENGTH(WS-ROW-TR-DESC-IN (I-SELECTED))
    ws.dcltrtyp.dcltransactionType.dclTrDescription.dclTrDescriptionLen.setValue(ws.wsMiscStorage.wsScreenDataIn.filler167.wsScreenRowsInAtIndex(ws.wsMiscStorage.wsSubscriptVars.iSelected.getInt() - 1).wsEachRowIn.wsEachTtypIn.wsRowTrDescIn.length());
    //COB(1846): 185000     EXEC SQL
    //COB(1846): 185100          UPDATE CARDDEMO.TRANSACTION_TYPE
    //COB(1846): 185200             SET TR_DESCRIPTION = :DCL-TR-DESCRIPTION
    //COB(1846): 185300           WHERE TR_TYPE = :DCL-TR-TYPE
    //COB(1846): 185400     END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
    sql.setCommand("UPDATE CARDDEMO.TRANSACTION_TYPE SET TR_DESCRIPTION = ? WHERE TR_TYPE = ?");
    sql.set.add(ws.dcltrtyp.dcltransactionType.dclTrDescription);
    sql.where.add(ws.dcltrtyp.dcltransactionType.dclTrType);
    sql.execute();
    }
    //COB(1852): 185600     MOVE SQLCODE                             TO WS-DISP-SQLCODE
    ws.wsMiscStorage.wsDb2CommonVars.wsDispSqlcode.setValue(sqlca.sqlcode);
    //COB(1854): 185800     EVALUATE TRUE
      //COB(1855): 185900         WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      //COB(1856): 186000            EXEC CICS SYNCPOINT END-EXEC
      try {
        supernaut.syncpoint() //
                .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      //COB(1857): 186100            SET CA-UPDATE-SUCCEEDED           TO TRUE
      ws.wsThisProgcommarea.setCaUpdateSucceeded(true);
      //COB(1858): 186200            IF WS-NO-INFO-MESSAGE
      if (ws.wsMiscStorage.wsNoInfoMessage()) {
        //COB(1859): 186300               SET WS-INFORM-UPDATE-SUCCESS   TO TRUE
        ws.wsMiscStorage.setWsInformUpdateSuccess(true);
      //COB(1860): 186400            END-IF
      }
      //COB(1861): 186500         WHEN SQLCODE = +100
    } else if (sqlca.sqlcode.equals(100)) {
      //COB(1862): 186600            SET CA-UPDATE-REQUESTED           TO TRUE
      ws.wsThisProgcommarea.setCaUpdateRequested(true);
      //COB(1863): 186700            IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        //COB(1864): 186800                MOVE 'Record not found. Deleted by others ? '
        //COB(1864): 186900                                         TO WS-DB2-CURRENT-ACTION
        ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.setString("Record not found. Deleted by others ? ");
        //COB(1866): 187000                PERFORM 9999-FORMAT-DB2-MESSAGE
        //COB(1866): 187100                   THRU 9999-FORMAT-DB2-MESSAGE-EXIT
        _9999FormatDb2Message();
      //COB(1868): 187200            END-IF
      }
      //COB(1869): 187300            GO TO 9200-UPDATE-RECORD-EXIT
      return;
      //COB(1870): 187400         WHEN SQLCODE = -911
    } else if (sqlca.sqlcode.equals(-911)) {
      //COB(1871): 187500            SET CA-UPDATE-REQUESTED           TO TRUE
      ws.wsThisProgcommarea.setCaUpdateRequested(true);
      //COB(1872): 187600            SET INPUT-ERROR                   TO TRUE
      ws.wsMiscStorage.setInputError(true);
      //COB(1873): 187700            IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        //COB(1874): 187800                MOVE 'Deadlock. Someone else updating ?'
        //COB(1874): 187900                                         TO WS-DB2-CURRENT-ACTION
        ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.setString("Deadlock. Someone else updating ?");
        //COB(1876): 188000                PERFORM 9999-FORMAT-DB2-MESSAGE
        //COB(1876): 188100                   THRU 9999-FORMAT-DB2-MESSAGE-EXIT
        _9999FormatDb2Message();
      //COB(1878): 188200            END-IF
      }
      //COB(1879): 188300            GO TO 9200-UPDATE-RECORD-EXIT
      return;
      //COB(1880): 188400         WHEN SQLCODE < 0
    } else if (sqlca.sqlcode.lessThan(0)) {
      //COB(1881): 188500            SET CA-UPDATE-REQUESTED           TO TRUE
      ws.wsThisProgcommarea.setCaUpdateRequested(true);
      //COB(1882): 188600            IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        //COB(1883): 188700                MOVE 'Update failed with'
        //COB(1883): 188800                                         TO WS-DB2-CURRENT-ACTION
        ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.setString("Update failed with");
        //COB(1885): 188900                PERFORM 9999-FORMAT-DB2-MESSAGE
        //COB(1885): 189000                   THRU 9999-FORMAT-DB2-MESSAGE-EXIT
        _9999FormatDb2Message();
      //COB(1887): 189100            END-IF
      }
      //COB(1888): 189200            GO TO 9200-UPDATE-RECORD-EXIT
      return;
    //COB(1889): 189300     END-EVALUATE
    }

  }

  /***/
  protected void _9300DeleteRecord() {
    //COB(1898): 190200     MOVE WS-ROW-TR-CODE-IN (I-SELECTED)      TO  DCL-TR-TYPE
    ws.dcltrtyp.dcltransactionType.dclTrType.setValue(ws.wsMiscStorage.wsScreenDataIn.filler167.wsScreenRowsInAtIndex(ws.wsMiscStorage.wsSubscriptVars.iSelected.getInt() - 1).wsEachRowIn.wsEachTtypIn.wsRowTrCodeIn);
    //COB(1900): 190400     EXEC SQL
    //COB(1900): 190500          DELETE FROM CARDDEMO.TRANSACTION_TYPE
    //COB(1900): 190600           WHERE TR_TYPE = :DCL-TR-TYPE
    //COB(1900): 190700     END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
    sql.setCommand("DELETE FROM CARDDEMO.TRANSACTION_TYPE WHERE TR_TYPE = ?");
    sql.where.add(ws.dcltrtyp.dcltransactionType.dclTrType);
    sql.execute();
    }
    //COB(1905): 190900     MOVE SQLCODE                             TO WS-DISP-SQLCODE
    ws.wsMiscStorage.wsDb2CommonVars.wsDispSqlcode.setValue(sqlca.sqlcode);
    //COB(1907): 191100     EVALUATE TRUE
      //COB(1908): 191200         WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      //COB(1909): 191300            EXEC CICS SYNCPOINT END-EXEC
      try {
        supernaut.syncpoint() //
                .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      //COB(1910): 191400            SET CA-DELETE-SUCCEEDED           TO TRUE
      ws.wsThisProgcommarea.setCaDeleteSucceeded(true);
      //COB(1911): 191500            IF WS-NO-INFO-MESSAGE
      if (ws.wsMiscStorage.wsNoInfoMessage()) {
        //COB(1912): 191600               SET WS-INFORM-DELETE-SUCCESS   TO TRUE
        ws.wsMiscStorage.setWsInformDeleteSuccess(true);
      //COB(1913): 191700            END-IF
      }
      //COB(1914): 191800         WHEN SQLCODE = -532
    } else if (sqlca.sqlcode.equals(-532)) {
      //COB(1915): 191900            SET CA-DELETE-REQUESTED           TO TRUE
      ws.wsThisProgcommarea.setCaDeleteRequested(true);
      //COB(1917): 192100            IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        //COB(1918): 192200                MOVE
        //COB(1918): 192300                'Please delete associated child records first:'
        //COB(1918): 192400                                         TO WS-DB2-CURRENT-ACTION
        ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.setString("Please delete associated child records first:");
        //COB(1921): 192500                PERFORM 9999-FORMAT-DB2-MESSAGE
        //COB(1921): 192600                   THRU 9999-FORMAT-DB2-MESSAGE-EXIT
        _9999FormatDb2Message();
      //COB(1923): 192700            END-IF
      }
      //COB(1925): 192900            GO TO 9300-DELETE-RECORD-EXIT
      return;
      //COB(1926): 193000         WHEN OTHER
    } else {
      //COB(1927): 193100            IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        //COB(1928): 193200                MOVE
        //COB(1928): 193300                'Delete failed with message:'
        //COB(1928): 193400                                         TO WS-DB2-CURRENT-ACTION
        ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.setString("Delete failed with message:");
        //COB(1931): 193500                PERFORM 9999-FORMAT-DB2-MESSAGE
        //COB(1931): 193600                   THRU 9999-FORMAT-DB2-MESSAGE-EXIT
        _9999FormatDb2Message();
      //COB(1933): 193700            END-IF
      }
      //COB(1934): 193800            GO TO 9300-DELETE-RECORD-EXIT
      return;
    //COB(1935): 193900     END-EVALUATE
    }

  }

  /***/
  protected void _9400OpenForwardCursor() {
    //COB(1943): 194700     EXEC SQL
    //COB(1943): 194800          OPEN C-TR-TYPE-FORWARD
    //COB(1943): 194900     END-EXEC
    cTrTypeForward.open();
    //COB(1947): 195100     MOVE SQLCODE        TO WS-DISP-SQLCODE
    ws.wsMiscStorage.wsDb2CommonVars.wsDispSqlcode.setValue(sqlca.sqlcode);
    //COB(1949): 195300     EVALUATE TRUE
      //COB(1950): 195400        WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      //COB(1951): 195500           CONTINUE
      //do nothing
      //COB(1952): 195600        WHEN OTHER
    } else {
      //COB(1955): 195900           SET WS-DB2-ERROR        TO TRUE
  //           This is some kind of error. Close Cursor
  //           And exit
      ws.wsMiscStorage.wsDb2CommonVars.setWsDb2Error(true);
      //COB(1956): 196000           IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        //COB(1957): 196100                MOVE
        //COB(1957): 196200                'C-TR-TYPE-FORWARD Open'
        //COB(1957): 196300                                         TO WS-DB2-CURRENT-ACTION
        ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.setString("C-TR-TYPE-FORWARD Open");
        //COB(1960): 196400                PERFORM 9999-FORMAT-DB2-MESSAGE
        //COB(1960): 196500                   THRU 9999-FORMAT-DB2-MESSAGE-EXIT
        _9999FormatDb2Message();
      //COB(1962): 196600           END-IF
      }
    //COB(1963): 196700      END-EVALUATE
    }

  }

  /***
  */
  protected void _9450CloseForwardCursor() {
    //COB(1971): 197500     EXEC SQL
    //COB(1971): 197600          CLOSE C-TR-TYPE-FORWARD
    //COB(1971): 197700     END-EXEC
    cTrTypeForward.close();
    //COB(1975): 197900     MOVE SQLCODE        TO WS-DISP-SQLCODE
    ws.wsMiscStorage.wsDb2CommonVars.wsDispSqlcode.setValue(sqlca.sqlcode);
    //COB(1977): 198100     EVALUATE TRUE
      //COB(1978): 198200        WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      //COB(1979): 198300           CONTINUE
      //do nothing
      //COB(1980): 198400        WHEN OTHER
    } else {
      //COB(1983): 198700           SET WS-DB2-ERROR        TO TRUE
  //           This is some kind of error. Close Cursor
  //           And exit
      ws.wsMiscStorage.wsDb2CommonVars.setWsDb2Error(true);
      //COB(1984): 198800           IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        //COB(1985): 198900                MOVE
        //COB(1985): 199000                'C-TR-TYPE-FORWARD close'
        //COB(1985): 199100                                         TO WS-DB2-CURRENT-ACTION
        ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.setString("C-TR-TYPE-FORWARD close");
        //COB(1988): 199200                PERFORM 9999-FORMAT-DB2-MESSAGE
        //COB(1988): 199300                   THRU 9999-FORMAT-DB2-MESSAGE-EXIT
        _9999FormatDb2Message();
      //COB(1990): 199400           END-IF
      }
    //COB(1991): 199500      END-EVALUATE
    }

  }

  /***/
  protected void _9500OpenBackwardCursor() {
    //COB(1998): 200200     EXEC SQL
    //COB(1998): 200300          OPEN C-TR-TYPE-BACKWARD
    //COB(1998): 200400     END-EXEC
    cTrTypeBackward.open();
    //COB(2002): 200600     MOVE SQLCODE        TO WS-DISP-SQLCODE
    ws.wsMiscStorage.wsDb2CommonVars.wsDispSqlcode.setValue(sqlca.sqlcode);
    //COB(2004): 200800     EVALUATE TRUE
      //COB(2005): 200900        WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      //COB(2006): 201000           CONTINUE
      //do nothing
      //COB(2007): 201100        WHEN OTHER
    } else {
      //COB(2010): 201400           SET WS-DB2-ERROR        TO TRUE
  //           This is some kind of error. Close Cursor
  //           And exit
      ws.wsMiscStorage.wsDb2CommonVars.setWsDb2Error(true);
      //COB(2011): 201500           IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        //COB(2012): 201600                MOVE
        //COB(2012): 201700                'C-TR-TYPE-BACKWARD Open'
        //COB(2012): 201800                                         TO WS-DB2-CURRENT-ACTION
        ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.setString("C-TR-TYPE-BACKWARD Open");
        //COB(2015): 201900                PERFORM 9999-FORMAT-DB2-MESSAGE
        //COB(2015): 202000                   THRU 9999-FORMAT-DB2-MESSAGE-EXIT
        _9999FormatDb2Message();
      //COB(2017): 202100           END-IF
      }
    //COB(2019): 202300      END-EVALUATE
    }

  }

  /***
  */
  protected void _9550CloseBackCursor() {
    //COB(2027): 203100     EXEC SQL
    //COB(2027): 203200          CLOSE C-TR-TYPE-BACKWARD
    //COB(2027): 203300     END-EXEC
    cTrTypeBackward.close();
    //COB(2031): 203500     MOVE SQLCODE        TO WS-DISP-SQLCODE
    ws.wsMiscStorage.wsDb2CommonVars.wsDispSqlcode.setValue(sqlca.sqlcode);
    //COB(2033): 203700     EVALUATE TRUE
      //COB(2034): 203800        WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      //COB(2035): 203900           CONTINUE
      //do nothing
      //COB(2036): 204000        WHEN OTHER
    } else {
      //COB(2039): 204300           SET WS-DB2-ERROR        TO TRUE
  //           This is some kind of error. Close Cursor
  //           And exit
      ws.wsMiscStorage.wsDb2CommonVars.setWsDb2Error(true);
      //COB(2040): 204400           IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        //COB(2041): 204500                MOVE
        //COB(2041): 204600                'C-TR-TYPE-BACKWARD close'
        //COB(2041): 204700                                         TO WS-DB2-CURRENT-ACTION
        ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.setString("C-TR-TYPE-BACKWARD close");
        //COB(2044): 204800                PERFORM 9999-FORMAT-DB2-MESSAGE
        //COB(2044): 204900                   THRU 9999-FORMAT-DB2-MESSAGE-EXIT
        _9999FormatDb2Message();
      //COB(2046): 205000           END-IF
      }
    //COB(2047): 205100      END-EVALUATE
    }

  }

  /**000100******************************************************************00010000
  000200* CardDemo - Common Procedures for Db2                            00020000
  000300******************************************************************00030000
  000400* Copyright Amazon.com, Inc. or its affiliates.                   00040000
  000500* All Rights Reserved.                                            00050000
  000600*                                                                 00060000
  000700* Licensed under the Apache License, Version 2.0 (the "License"). 00070000
  000800* You may not use this file except in compliance with the License.00080000
  000900* You may obtain a copy of the License at                         00090000
  001000*                                                                 00100000
  001100*    http://www.apache.org/licenses/LICENSE-2.0                   00110000
  001200*                                                                 00120000
  001300* Unless required by applicable law or agreed to in writing,      00130000
  001400* software distributed under the License is distributed on an     00140000
  001500* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,    00150000
  001600* either express or implied. See the License for the specific     00160000
  001700* language governing permissions and limitations under the License00170000
  176800***************************************************************** 17680000
  176900* Dummy call to verify connectivity to Db2                      * 17690000
  177000***************************************************************** 17700000*/
  protected void _9998PrimingQuery() {
    //COB(23): 177300     EXEC SQL
    //COB(23): 177500          SELECT 1
    //COB(23): 177600            INTO :WS-DUMMY-DB2-INT
    //COB(23): 177700            FROM SYSIBM.SYSDUMMY1
    //COB(23): 177900            FETCH FIRST 1 ROW ONLY
    //COB(23): 178000     END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
    sql.setCommand("SELECT 1 FROM SYSIBM.SYSDUMMY1 FETCH FIRST 1 ROW ONLY");
    sql.into.add(ws.wsMiscStorage.wsDb2CommonVars.wsDummyDb2Int);
    sql.execute();
    }
    //COB(31): 178300     MOVE SQLCODE        TO WS-DISP-SQLCODE
    ws.wsMiscStorage.wsDb2CommonVars.wsDispSqlcode.setValue(sqlca.sqlcode);
    //COB(33): 178500     EVALUATE TRUE
      //COB(34): 178600        WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      //COB(35): 178700           CONTINUE
      //do nothing
      //COB(36): 178800        WHEN OTHER
    } else {
      //COB(38): 179000           SET WS-DB2-ERROR        TO TRUE
  //           This is some kind of error. Format message and exit
      ws.wsMiscStorage.wsDb2CommonVars.setWsDb2Error(true);
      //COB(40): 179200           MOVE 'Db2 access failure. '
      //COB(40): 179300                                        TO WS-DB2-CURRENT-ACTION
      ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.setString("Db2 access failure. ");
      //COB(42): 179400           PERFORM 9999-FORMAT-DB2-MESSAGE
      //COB(42): 179500              THRU 9999-FORMAT-DB2-MESSAGE-EXIT
      _9999FormatDb2Message();
    //COB(44): 179600      END-EVALUATE
    }

  }

  /**205300***************************************************************** 20530000
  205400* Construct formatted message using DSNTIAC utility             * 20540000
  205500* This is for Db2                                               * 20550000
  205600***************************************************************** 20560000*/
  protected void _9999FormatDb2Message() {
    //COB(55): MOVE +72                 TO WS-DSNTIAC-LRECL
  // 05800
    ws.wsMiscStorage.wsDsntiacLrecl.setValue(72);
    //COB(57): CALL LIT-DSNTIAC  USING  DFHEIBLK,
    //COB(57): 206000                              DFHCOMMAREA,
    //COB(57): 206100                              SQLCA,
    //COB(57): 206200                              WS-DSNTIAC-FORMATTED,
    //COB(57): 206300                              WS-DSNTIAC-LRECL
    //COB(57):  06400     END-CALL
    supernaut.call(ws.wsConstants.litDsntiac, dfheiblk, params.dfhcommarea, sqlca, ws.wsMiscStorage.wsDsntiacFormatted, ws.wsMiscStorage.wsDsntiacLrecl);
    //COB(64): 206402     COMPUTE WS-DSNTIAC-ERR-CD = RETURN-CODE
    ws.wsMiscStorage.wsDsntiacError.wsDsntiacErrCd.setValue(RETURN_CODE);
    //COB(67): 206410     IF WS-DSNTIAC-ERR-CD = ZEROES
    if (ws.wsMiscStorage.wsDsntiacError.wsDsntiacErrCd.equals(0)) {
      //COB(68): 206420        CONTINUE
      //do nothing
      //COB(69): 206430     ELSE
    } else {
      //COB(70): 206431        MOVE 'DSNTIAC CD: '       TO WS-DSNTIAC-ERR-MSG
      ws.wsMiscStorage.wsDsntiacError.wsDsntiacErrMsg.setString("DSNTIAC CD: ");
      //COB(71): 206432        MOVE WS-DSNTIAC-ERROR     TO WS-DSNTIAC-FMTD-TEXT
      ws.wsMiscStorage.wsDsntiacFormatted.wsDsntiacFmtdText.setValue(ws.wsMiscStorage.wsDsntiacError);
    //COB(72): 206450     END-IF
    }
    //COB(74): 206600     STRING
    //COB(74): 206700          FUNCTION TRIM(WS-DB2-CURRENT-ACTION)
    //COB(74): 206800                   ' SQLCODE:'
    //COB(74): 206900                   WS-DISP-SQLCODE
    //COB(74): 207000                   ' '
    //COB(74): 207100*                  SQLERRM OF SQLCA
    //COB(74): 207200                   WS-DSNTIAC-FMTD-TEXT
    //COB(74): 207300          DELIMITED BY SIZE
    //COB(74): 207400     INTO WS-LONG-MSG
    //COB(74): 207500     END-STRING
    ws.wsMiscStorage.wsLongMsg.concat(ws.wsMiscStorage.wsDb2CommonVars.wsDb2CurrentAction.trim(), " SQLCODE:", ws.wsMiscStorage.wsDb2CommonVars.wsDispSqlcode, " ", ws.wsMiscStorage.wsDsntiacFormatted.wsDsntiacFmtdText);
    //COB(83): 207500     END-STRING
    //COB(84): 207600     MOVE WS-LONG-MSG       TO WS-RETURN-MSG
    ws.wsMiscStorage.wsReturnMsg.setValue(ws.wsMiscStorage.wsLongMsg);

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
    //COB(21): EVALUATE TRUE
  // ****************************************************************
  //  Map AID to PFKey in COMMON Area
  // ****************************************************************
      //COB(22): WHEN EIBAID IS EQUAL TO DFHENTER
    if (dfheiblk.getEibaid().equals(Dfhaid.DFHENTER)) {
      //COB(23): SET CCARD-AID-ENTER TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidEnter(true);
      //COB(24): WHEN EIBAID IS EQUAL TO DFHCLEAR
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHCLEAR)) {
      //COB(25): SET CCARD-AID-CLEAR TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidClear(true);
      //COB(26): WHEN EIBAID IS EQUAL TO DFHPA1
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPA1)) {
      //COB(27): SET CCARD-AID-PA1  TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPa1(true);
      //COB(28): WHEN EIBAID IS EQUAL TO DFHPA2
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPA2)) {
      //COB(29): SET CCARD-AID-PA2  TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPa2(true);
      //COB(30): WHEN EIBAID IS EQUAL TO DFHPF1
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF1)) {
      //COB(31): SET CCARD-AID-PFK01 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk01(true);
      //COB(32): WHEN EIBAID IS EQUAL TO DFHPF2
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF2)) {
      //COB(33): SET CCARD-AID-PFK02 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk02(true);
      //COB(34): WHEN EIBAID IS EQUAL TO DFHPF3
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF3)) {
      //COB(35): SET CCARD-AID-PFK03 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk03(true);
      //COB(36): WHEN EIBAID IS EQUAL TO DFHPF4
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF4)) {
      //COB(37): SET CCARD-AID-PFK04 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk04(true);
      //COB(38): WHEN EIBAID IS EQUAL TO DFHPF5
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF5)) {
      //COB(39): SET CCARD-AID-PFK05 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk05(true);
      //COB(40): WHEN EIBAID IS EQUAL TO DFHPF6
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF6)) {
      //COB(41): SET CCARD-AID-PFK06 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk06(true);
      //COB(42): WHEN EIBAID IS EQUAL TO DFHPF7
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF7)) {
      //COB(43): SET CCARD-AID-PFK07 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk07(true);
      //COB(44): WHEN EIBAID IS EQUAL TO DFHPF8
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF8)) {
      //COB(45): SET CCARD-AID-PFK08 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk08(true);
      //COB(46): WHEN EIBAID IS EQUAL TO DFHPF9
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF9)) {
      //COB(47): SET CCARD-AID-PFK09 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk09(true);
      //COB(48): WHEN EIBAID IS EQUAL TO DFHPF10
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF10)) {
      //COB(49): SET CCARD-AID-PFK10 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk10(true);
      //COB(50): WHEN EIBAID IS EQUAL TO DFHPF11
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF11)) {
      //COB(51): SET CCARD-AID-PFK11 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk11(true);
      //COB(52): WHEN EIBAID IS EQUAL TO DFHPF12
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF12)) {
      //COB(53): SET CCARD-AID-PFK12 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk12(true);
      //COB(54): WHEN EIBAID IS EQUAL TO DFHPF13
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF13)) {
      //COB(55): SET CCARD-AID-PFK01 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk01(true);
      //COB(56): WHEN EIBAID IS EQUAL TO DFHPF14
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF14)) {
      //COB(57): SET CCARD-AID-PFK02 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk02(true);
      //COB(58): WHEN EIBAID IS EQUAL TO DFHPF15
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF15)) {
      //COB(59): SET CCARD-AID-PFK03 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk03(true);
      //COB(60): WHEN EIBAID IS EQUAL TO DFHPF16
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF16)) {
      //COB(61): SET CCARD-AID-PFK04 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk04(true);
      //COB(62): WHEN EIBAID IS EQUAL TO DFHPF17
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF17)) {
      //COB(63): SET CCARD-AID-PFK05 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk05(true);
      //COB(64): WHEN EIBAID IS EQUAL TO DFHPF18
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF18)) {
      //COB(65): SET CCARD-AID-PFK06 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk06(true);
      //COB(66): WHEN EIBAID IS EQUAL TO DFHPF19
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF19)) {
      //COB(67): SET CCARD-AID-PFK07 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk07(true);
      //COB(68): WHEN EIBAID IS EQUAL TO DFHPF20
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF20)) {
      //COB(69): SET CCARD-AID-PFK08 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk08(true);
      //COB(70): WHEN EIBAID IS EQUAL TO DFHPF21
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF21)) {
      //COB(71): SET CCARD-AID-PFK09 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk09(true);
      //COB(72): WHEN EIBAID IS EQUAL TO DFHPF22
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF22)) {
      //COB(73): SET CCARD-AID-PFK10 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk10(true);
      //COB(74): WHEN EIBAID IS EQUAL TO DFHPF23
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF23)) {
      //COB(75): SET CCARD-AID-PFK11 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk11(true);
      //COB(76): WHEN EIBAID IS EQUAL TO DFHPF24
    } else if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF24)) {
      //COB(77): SET CCARD-AID-PFK12 TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidPfk12(true);
    //COB(78): END-EVALUATE
    }

  }

  /***
  *****************************************************************
  * Plain text exit - Dont use in production                      *
  *****************************************************************/
  protected void sendPlainText() {
    //COB(2067): 207100     EXEC CICS SEND TEXT
    //COB(2067): 207200               FROM(WS-RETURN-MSG)
    //COB(2067): 207300               LENGTH(LENGTH OF WS-RETURN-MSG)
    //COB(2067): 207400               ERASE
    //COB(2067): 207500               FREEKB
    //COB(2067): 207600     END-EXEC
    try {
      supernaut.sendText() //
              .from(ws.wsMiscStorage.wsReturnMsg) //
              .length(ws.wsMiscStorage.wsReturnMsg.length()) //
              .freekb() //
              .erase() //
              .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    //COB(2074): 207800     EXEC CICS RETURN
    //COB(2074): 207900     END-EXEC
    try {
      supernaut.returnToCaller();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }

  }

  /*****************************************************************
  * Display Long text and exit                                    *
  * This is primarily for debugging and should not be used in     *
  * regular course                                                *
  *****************************************************************/
  protected void sendLongText() {
    //COB(2086): 209000     EXEC CICS SEND TEXT
    //COB(2086): 209100               FROM(WS-LONG-MSG)
    //COB(2086): 209200               LENGTH(LENGTH OF WS-LONG-MSG)
    //COB(2086): 209300               ERASE
    //COB(2086): 209400               FREEKB
    //COB(2086): 209500     END-EXEC
    try {
      supernaut.sendText() //
              .from(ws.wsMiscStorage.wsLongMsg) //
              .length(ws.wsMiscStorage.wsLongMsg.length()) //
              .freekb() //
              .erase() //
              .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    //COB(2093): 209700     EXEC CICS RETURN
    //COB(2093): 209800     END-EXEC
    try {
      supernaut.returnToCaller();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }

  }

  protected enum HandleLabel {
    Not_Defined(0)
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
    throw new RuntimeException(e);
  }

}
