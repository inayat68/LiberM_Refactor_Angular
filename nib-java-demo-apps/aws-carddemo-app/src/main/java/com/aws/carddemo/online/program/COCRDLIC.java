package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cocrdlic.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;
import java.math.BigDecimal;

public class COCRDLIC extends AbstractOnlineProgram {

  protected enum Flow {
    Exit,
    // 0000-MAIN
    _0000Main,
    // 9100-READ-BACKWARDS
    _9100ReadBackwards,
    // 9100-READ-BACKWARDS-EXIT
    _9100ReadBackwardsExit,
    // COMMON-RETURN
    commonReturn
  }

  private Flow rcNext;

  @ProgramStorage final CocrdlicWorking ws = new CocrdlicWorking();

  // Linkage
  public static class CocrdlicLinkage extends NGroup {
    // COB:        01  DFHCOMMAREA.
    public Dfhcommarea dfhcommarea = new Dfhcommarea();

    public static class Dfhcommarea extends NGroup {

      // COB:          05  FILLER                                PIC X(1)
      // COB:              OCCURS 1 TO 32767 TIMES DEPENDING ON EIBCALEN.
      public NChar[] filler294 = AbstractNField.occurs(32767, new NChar(1));

      public NChar filler294AtIndex(int index) {
        return getOccursInstance(filler294, index);
      }
    }
  }

  final CocrdlicLinkage params = new CocrdlicLinkage();

  public COCRDLIC(Context supernaut) {
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
    // COB(300): INITIALIZE CC-WORK-AREA
    // COB(300):            WS-MISC-STORAGE
    // COB(300):            WS-COMMAREA
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.initialize();
    ws.wsMiscStorage.initialize();
    ws.wsCommarea.initialize();
    // COB(307): MOVE LIT-THISTRANID       TO WS-TRANID
    // ****************************************************************
    //  Store our context
    // ****************************************************************
    ws.wsMiscStorage.wsCicsProcessngVars.wsTranid.setValue(ws.wsConstants.litThistranid);
    // COB(311): SET WS-ERROR-MSG-OFF  TO TRUE
    // ****************************************************************
    //  Ensure error message is cleared                               *
    // ****************************************************************
    ws.wsMiscStorage.setWsErrorMsgOff(true);
    // COB(315): IF EIBCALEN = 0
    // ****************************************************************
    //  Retrived passed data if  any. Initialize them if first run.
    // ****************************************************************
    if (dfheiblk.getEibcalen() == 0) {
      // COB(316): INITIALIZE CARDDEMO-COMMAREA
      // COB(316):            WS-THIS-PROGCOMMAREA
      ws.cocom01y.carddemoCommarea.initialize();
      ws.wsThisProgcommarea.initialize();
      // COB(318): MOVE LIT-THISTRANID        TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
          ws.wsConstants.litThistranid);
      // COB(319): MOVE LIT-THISPGM           TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
          ws.wsConstants.litThispgm);
      // COB(320): SET CDEMO-USRTYP-USER      TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypUser(true);
      // COB(321): SET CDEMO-PGM-ENTER        TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(322): MOVE LIT-THISMAP           TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      // COB(323): MOVE LIT-THISMAPSET        TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(
          ws.wsConstants.litThismapset);
      // COB(324): SET CA-FIRST-PAGE          TO TRUE
      ws.wsThisProgcommarea.setCaFirstPage(true);
      // COB(325): SET CA-LAST-PAGE-NOT-SHOWN TO TRUE
      ws.wsThisProgcommarea.setCaLastPageNotShown(true);
      // COB(326): ELSE
    } else {
      // COB(327): MOVE DFHCOMMAREA (1:LENGTH OF CARDDEMO-COMMAREA) TO
      // COB(327):                   CARDDEMO-COMMAREA
      ws.cocom01y.carddemoCommarea.setValue(
          params.dfhcommarea, 0, ws.cocom01y.carddemoCommarea.length());
      // COB(329): MOVE DFHCOMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
      // COB(329):                  LENGTH OF WS-THIS-PROGCOMMAREA )TO
      // COB(329):                   WS-THIS-PROGCOMMAREA
      ws.wsThisProgcommarea.setValue(
          params.dfhcommarea,
          ws.cocom01y.carddemoCommarea.length(),
          0,
          ws.wsThisProgcommarea.length());
      // COB(332): END-IF
    }
    // COB(336): IF (CDEMO-PGM-ENTER
    // COB(336): AND CDEMO-FROM-PROGRAM NOT EQUAL LIT-THISPGM)
    // ****************************************************************
    //  If coming in from menu. Lets forget the past and start afresh *
    // ****************************************************************
    if ((ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()
        && !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
            ws.wsConstants.litThispgm))) {
      // COB(338): INITIALIZE WS-THIS-PROGCOMMAREA
      ws.wsThisProgcommarea.initialize();
      // COB(339): SET CDEMO-PGM-ENTER      TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(340): MOVE LIT-THISMAP         TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      // COB(341): SET CA-FIRST-PAGE        TO TRUE
      ws.wsThisProgcommarea.setCaFirstPage(true);
      // COB(342): SET CA-LAST-PAGE-NOT-SHOWN TO TRUE
      ws.wsThisProgcommarea.setCaLastPageNotShown(true);
      // COB(343): END-IF
    }
    // COB(349): PERFORM YYYY-STORE-PFKEY
    // COB(349):    THRU YYYY-STORE-PFKEY-EXIT
    // *****************************************************************
    //  Remap PFkeys as needed.
    //  Store the Mapped PF Key
    // ****************************************************************
    yyyyStorePfkey();
    // COB(357): IF  EIBCALEN > 0
    // COB(357): AND CDEMO-FROM-PROGRAM  EQUAL LIT-THISPGM
    // *****************************************************************
    //  If something is present in commarea
    //  and the from program is this program itself,
    //  read and edit the inputs given
    // ****************************************************************
    if (dfheiblk.getEibcalen() > 0
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
            ws.wsConstants.litThispgm)) {
      // COB(359): PERFORM 2000-RECEIVE-MAP
      // COB(359): THRU    2000-RECEIVE-MAP-EXIT
      _2000ReceiveMap();
      // COB(362): END-IF
    }
    // COB(370): SET PFK-INVALID TO TRUE
    // ****************************************************************
    //  Check the mapped key  to see if its valid at this point       *
    //  F3    - Exit
    //  Enter - List of cards for current start key
    //  F8    - Page down
    //  F7    - Page up
    // ****************************************************************
    ws.wsMiscStorage.setPfkInvalid(true);
    // COB(371): IF CCARD-AID-ENTER OR
    // COB(371):    CCARD-AID-PFK03 OR
    // COB(371):    CCARD-AID-PFK07 OR
    // COB(371):    CCARD-AID-PFK08
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidEnter()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk07()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk08()) {
      // COB(375): SET PFK-VALID TO TRUE
      ws.wsMiscStorage.setPfkValid(true);
      // COB(376): END-IF
    }
    // COB(378): IF PFK-INVALID
    if (ws.wsMiscStorage.pfkInvalid()) {
      // COB(379): SET CCARD-AID-ENTER TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidEnter(true);
      // COB(380): END-IF
    }
    // COB(384): IF  (CCARD-AID-PFK03
    // COB(384): AND CDEMO-FROM-PROGRAM  EQUAL LIT-THISPGM)
    // ****************************************************************
    //  If the user pressed PF3 go back to main menu
    // ****************************************************************
    if ((ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
            ws.wsConstants.litThispgm))) {
      // COB(386): MOVE LIT-THISTRANID   TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
          ws.wsConstants.litThistranid);
      // COB(387): MOVE LIT-THISPGM      TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
          ws.wsConstants.litThispgm);
      // COB(388): SET  CDEMO-USRTYP-USER TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypUser(true);
      // COB(389): SET  CDEMO-PGM-ENTER  TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(390): MOVE LIT-THISMAPSET   TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(
          ws.wsConstants.litThismapset);
      // COB(391): MOVE LIT-THISMAP      TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      // COB(392): MOVE LIT-MENUPGM      TO CDEMO-TO-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
          ws.wsConstants.litMenupgm);
      // COB(394): MOVE LIT-MENUMAPSET   TO CCARD-NEXT-MAPSET
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsConstants.litMenumapset);
      // COB(395): MOVE LIT-THISMAP      TO CCARD-NEXT-MAP
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsConstants.litThismap);
      // COB(396): SET WS-EXIT-MESSAGE            TO TRUE
      ws.wsMiscStorage.setWsExitMessage(true);
      // COB(400): SET CDEMO-PGM-ENTER   TO TRUE
      //        CALL MENU PROGRAM
      //
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(402): EXEC CICS XCTL
      // COB(402):           PROGRAM (LIT-MENUPGM)
      // COB(402):           COMMAREA(CARDDEMO-COMMAREA)
      // COB(402): END-EXEC
      try {
        supernaut
            .xctl(ws.wsConstants.litMenupgm.getValue()) //
            .commarea(ws.cocom01y.carddemoCommarea) //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(406): END-IF
    }
    // COB(410): IF CCARD-AID-PFK08
    // ****************************************************************
    //  If the user did not press PF8, lets reset the last page flag
    // ****************************************************************
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk08()) {
      // COB(411): CONTINUE
      // do nothing
      // COB(412): ELSE
    } else {
      // COB(413): SET CA-LAST-PAGE-NOT-SHOWN   TO TRUE
      ws.wsThisProgcommarea.setCaLastPageNotShown(true);
      // COB(414): END-IF
    }
    // COB(418): EVALUATE TRUE
    // ****************************************************************
    //  Now we decide what to do
    // ****************************************************************
    // COB(419): WHEN INPUT-ERROR
    if (ws.wsMiscStorage.inputError()) {
      // COB(423): MOVE WS-ERROR-MSG    TO CCARD-ERROR-MSG
      // ****************************************************************
      //         ASK FOR CORRECTIONS TO INPUTS
      // ****************************************************************
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsErrorMsg);
      // COB(424): MOVE LIT-THISPGM     TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
          ws.wsConstants.litThispgm);
      // COB(425): MOVE LIT-THISMAPSET  TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(
          ws.wsConstants.litThismapset);
      // COB(426): MOVE LIT-THISMAP     TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      // COB(428): MOVE LIT-THISPGM     TO CCARD-NEXT-PROG
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.setValue(ws.wsConstants.litThispgm);
      // COB(429): MOVE LIT-THISMAPSET  TO CCARD-NEXT-MAPSET
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsConstants.litThismapset);
      // COB(430): MOVE LIT-THISMAP     TO CCARD-NEXT-MAP
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsConstants.litThismap);
      // COB(431): IF  NOT FLG-ACCTFILTER-NOT-OK
      // COB(431): AND NOT FLG-CARDFILTER-NOT-OK
      if (!ws.wsMiscStorage.flgAcctfilterNotOk() && !ws.wsMiscStorage.flgCardfilterNotOk()) {
        // COB(433): PERFORM 9000-READ-FORWARD
        // COB(433):    THRU 9000-READ-FORWARD-EXIT
        _9000ReadForward();
        // COB(435): END-IF
      }
      // COB(436): PERFORM 1000-SEND-MAP
      // COB(436):    THRU 1000-SEND-MAP
      _1000SendMap();
      // COB(438): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(439): WHEN CCARD-AID-PFK07
      // COB(439):      AND CA-FIRST-PAGE
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk07()
            && ws.wsThisProgcommarea.caFirstPage()
        // COB(444): WHEN CCARD-AID-PFK07
        // COB(444):      AND CA-FIRST-PAGE
        // ****************************************************************
        //         PAGE UP - PF7 - BUT ALREADY ON FIRST PAGE
        // ****************************************************************
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk07()
            && ws.wsThisProgcommarea.caFirstPage()) {
      // COB(446): MOVE WS-CA-FIRST-CARD-NUM
      // COB(446):               TO WS-CARD-RID-CARDNUM
      ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum.setValue(
          ws.wsThisProgcommarea.wsCaFirstCardkey.wsCaFirstCardNum);
      // COB(450): PERFORM 9000-READ-FORWARD
      // COB(450):    THRU 9000-READ-FORWARD-EXIT
      //              MOVE WS-CA-FIRST-CARD-ACCT-ID
      //                            TO WS-CARD-RID-ACCT-ID
      _9000ReadForward();
      // COB(452): PERFORM 1000-SEND-MAP
      // COB(452):    THRU 1000-SEND-MAP
      _1000SendMap();
      // COB(454): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(458): WHEN CCARD-AID-PFK03
      // ****************************************************************
      //         BACK - PF3 IF WE CAME FROM SOME OTHER PROGRAM
      // ****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()
        // COB(459): WHEN CDEMO-PGM-REENTER AND
        // COB(459):      CDEMO-FROM-PROGRAM NOT EQUAL LIT-THISPGM
        || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()
            && !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsConstants.litThispgm)) {
      // COB(462): INITIALIZE CARDDEMO-COMMAREA
      // COB(462):            WS-THIS-PROGCOMMAREA
      ws.cocom01y.carddemoCommarea.initialize();
      ws.wsThisProgcommarea.initialize();
      // COB(464): MOVE LIT-THISTRANID      TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
          ws.wsConstants.litThistranid);
      // COB(465): MOVE LIT-THISPGM         TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
          ws.wsConstants.litThispgm);
      // COB(466): SET CDEMO-USRTYP-USER    TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypUser(true);
      // COB(467): SET CDEMO-PGM-ENTER      TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(468): MOVE LIT-THISMAP         TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      // COB(469): MOVE LIT-THISMAPSET      TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(
          ws.wsConstants.litThismapset);
      // COB(470): SET CA-FIRST-PAGE        TO TRUE
      ws.wsThisProgcommarea.setCaFirstPage(true);
      // COB(471): SET CA-LAST-PAGE-NOT-SHOWN TO TRUE
      ws.wsThisProgcommarea.setCaLastPageNotShown(true);
      // COB(473): MOVE WS-CA-FIRST-CARD-NUM
      // COB(473):               TO WS-CARD-RID-CARDNUM
      ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum.setValue(
          ws.wsThisProgcommarea.wsCaFirstCardkey.wsCaFirstCardNum);
      // COB(478): PERFORM 9000-READ-FORWARD
      // COB(478):    THRU 9000-READ-FORWARD-EXIT
      //              MOVE WS-CA-FIRST-CARD-ACCT-ID
      //                            TO WS-CARD-RID-ACCT-ID
      _9000ReadForward();
      // COB(480): PERFORM 1000-SEND-MAP
      // COB(480):    THRU 1000-SEND-MAP
      _1000SendMap();
      // COB(482): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(486): WHEN CCARD-AID-PFK08
      // COB(486):      AND CA-NEXT-PAGE-EXISTS
      // ****************************************************************
      //         PAGE DOWN
      // ****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk08()
        && ws.wsThisProgcommarea.caNextPageExists()) {
      // COB(488): MOVE WS-CA-LAST-CARD-NUM
      // COB(488):               TO WS-CARD-RID-CARDNUM
      ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum.setValue(
          ws.wsThisProgcommarea.wsCaLastCardkey.wsCaLastCardNum);
      // COB(492): ADD   +1       TO WS-CA-SCREEN-NUM
      //              MOVE WS-CA-LAST-CARD-ACCT-ID
      //                            TO WS-CARD-RID-ACCT-ID
      ws.wsThisProgcommarea.wsCaScreenNum.add(1);
      // COB(493): PERFORM 9000-READ-FORWARD
      // COB(493):    THRU 9000-READ-FORWARD-EXIT
      _9000ReadForward();
      // COB(495): PERFORM 1000-SEND-MAP
      // COB(495):    THRU 1000-SEND-MAP-EXIT
      _1000SendMap();
      // COB(497): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(501): WHEN CCARD-AID-PFK07
      // COB(501):      AND NOT CA-FIRST-PAGE
      // ****************************************************************
      //         PAGE UP
      // ****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk07()
        && !ws.wsThisProgcommarea.caFirstPage()) {
      // COB(504): MOVE WS-CA-FIRST-CARD-NUM
      // COB(504):               TO WS-CARD-RID-CARDNUM
      ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum.setValue(
          ws.wsThisProgcommarea.wsCaFirstCardkey.wsCaFirstCardNum);
      // COB(508): SUBTRACT 1    FROM WS-CA-SCREEN-NUM
      //              MOVE WS-CA-FIRST-CARD-ACCT-ID
      //                            TO WS-CARD-RID-ACCT-ID
      ws.wsThisProgcommarea.wsCaScreenNum.subtract(1);
      // COB(509): PERFORM 9100-READ-BACKWARDS
      // COB(509):    THRU 9100-READ-BACKWARDS-EXIT
      rcNext = Flow._9100ReadBackwards;
      while (!rcNext.equals(Flow.Exit)) {
        switch (rcNext) {
          case _9100ReadBackwards:
            rcNext = _9100ReadBackwards();
            if (rcNext.equals(Flow.Exit)) {
              rcNext = Flow._9100ReadBackwardsExit;
            }
            break;
          case _9100ReadBackwardsExit:
            _9100ReadBackwardsExit();
            rcNext = Flow.Exit;
            break;
          default:
            throw new RuntimeException("Invalid flow option: " + rcNext);
        }
      }
      // COB(511): PERFORM 1000-SEND-MAP
      // COB(511):    THRU 1000-SEND-MAP-EXIT
      _1000SendMap();
      // COB(513): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(517): WHEN CCARD-AID-ENTER
      // COB(517):  AND VIEW-REQUESTED-ON(I-SELECTED)
      // COB(517):  AND CDEMO-FROM-PROGRAM  EQUAL LIT-THISPGM
      // ****************************************************************
      //         TRANSFER TO CARD DETAIL VIEW
      // ****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidEnter()
        && ws.wsMiscStorage.wsEditSelectArray.viewRequestedOn(
            ws.wsMiscStorage.wsSubscriptVars.iSelected.getInt() - 1)
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
            ws.wsConstants.litThispgm)) {
      // COB(520): MOVE LIT-THISTRANID    TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
          ws.wsConstants.litThistranid);
      // COB(521): MOVE LIT-THISPGM       TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
          ws.wsConstants.litThispgm);
      // COB(522): SET  CDEMO-USRTYP-USER TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypUser(true);
      // COB(523): SET  CDEMO-PGM-ENTER   TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(524): MOVE LIT-THISMAPSET    TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(
          ws.wsConstants.litThismapset);
      // COB(525): MOVE LIT-THISMAP       TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      // COB(526): MOVE LIT-CARDDTLPGM    TO CCARD-NEXT-PROG
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.setValue(ws.wsConstants.litCarddtlpgm);
      // COB(528): MOVE LIT-CARDDTLMAPSET TO CCARD-NEXT-MAPSET
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsConstants.litCarddtlmapset);
      // COB(529): MOVE LIT-CARDDTLMAP    TO CCARD-NEXT-MAP
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsConstants.litCarddtlmap);
      // COB(531): MOVE WS-ROW-ACCTNO (I-SELECTED)
      // COB(531):                        TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(
                  ws.wsMiscStorage.wsSubscriptVars.iSelected.getInt() - 1)
              .wsEachRow
              .wsEachCard
              .wsRowAcctno);
      // COB(533): MOVE WS-ROW-CARD-NUM (I-SELECTED)
      // COB(533):                        TO CDEMO-CARD-NUM
      ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(
                  ws.wsMiscStorage.wsSubscriptVars.iSelected.getInt() - 1)
              .wsEachRow
              .wsEachCard
              .wsRowCardNum);
      // COB(538): EXEC CICS XCTL
      // COB(538):      PROGRAM (CCARD-NEXT-PROG)
      // COB(538):      COMMAREA(CARDDEMO-COMMAREA)
      // COB(538): END-EXEC
      //             CALL CARD DETAIL PROGRAM
      //
      try {
        supernaut
            .xctl(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.getValue()) //
            .commarea(ws.cocom01y.carddemoCommarea) //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(545): WHEN CCARD-AID-ENTER
      // COB(545):  AND UPDATE-REQUESTED-ON(I-SELECTED)
      // COB(545):  AND CDEMO-FROM-PROGRAM  EQUAL LIT-THISPGM
      // ****************************************************************
      //         TRANSFER TO CARD UPDATED PROGRAM
      // ****************************************************************
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidEnter()
        && ws.wsMiscStorage.wsEditSelectArray.updateRequestedOn(
            ws.wsMiscStorage.wsSubscriptVars.iSelected.getInt() - 1)
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
            ws.wsConstants.litThispgm)) {
      // COB(548): MOVE LIT-THISTRANID    TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
          ws.wsConstants.litThistranid);
      // COB(549): MOVE LIT-THISPGM       TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
          ws.wsConstants.litThispgm);
      // COB(550): SET  CDEMO-USRTYP-USER TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypUser(true);
      // COB(551): SET  CDEMO-PGM-ENTER   TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(552): MOVE LIT-THISMAPSET    TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(
          ws.wsConstants.litThismapset);
      // COB(553): MOVE LIT-THISMAP       TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      // COB(554): MOVE LIT-CARDUPDPGM    TO CCARD-NEXT-PROG
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.setValue(ws.wsConstants.litCardupdpgm);
      // COB(556): MOVE LIT-CARDUPDMAPSET TO CCARD-NEXT-MAPSET
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsConstants.litCardupdmapset);
      // COB(557): MOVE LIT-CARDUPDMAP    TO CCARD-NEXT-MAP
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsConstants.litCardupdmap);
      // COB(559): MOVE WS-ROW-ACCTNO (I-SELECTED)
      // COB(559):                        TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(
                  ws.wsMiscStorage.wsSubscriptVars.iSelected.getInt() - 1)
              .wsEachRow
              .wsEachCard
              .wsRowAcctno);
      // COB(561): MOVE WS-ROW-CARD-NUM (I-SELECTED)
      // COB(561):                        TO CDEMO-CARD-NUM
      ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(
                  ws.wsMiscStorage.wsSubscriptVars.iSelected.getInt() - 1)
              .wsEachRow
              .wsEachCard
              .wsRowCardNum);
      // COB(566): EXEC CICS XCTL
      // COB(566):      PROGRAM (CCARD-NEXT-PROG)
      // COB(566):      COMMAREA(CARDDEMO-COMMAREA)
      // COB(566): END-EXEC
      //             CALL CARD UPDATE PROGRAM
      //
      try {
        supernaut
            .xctl(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.getValue()) //
            .commarea(ws.cocom01y.carddemoCommarea) //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(572): WHEN OTHER
      // ****************************************************************
    } else {
      // COB(574): MOVE WS-CA-FIRST-CARD-NUM
      // COB(574):               TO WS-CARD-RID-CARDNUM
      // ****************************************************************
      ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum.setValue(
          ws.wsThisProgcommarea.wsCaFirstCardkey.wsCaFirstCardNum);
      // COB(578): PERFORM 9000-READ-FORWARD
      // COB(578):    THRU 9000-READ-FORWARD-EXIT
      //              MOVE WS-CA-FIRST-CARD-ACCT-ID
      //                            TO WS-CARD-RID-ACCT-ID
      _9000ReadForward();
      // COB(580): PERFORM 1000-SEND-MAP
      // COB(580):    THRU 1000-SEND-MAP
      _1000SendMap();
      // COB(582): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(583): END-EVALUATE
    }
    // COB(586): IF INPUT-ERROR
    //  If we had an error setup error message to display and return
    if (ws.wsMiscStorage.inputError()) {
      // COB(587): MOVE WS-ERROR-MSG   TO CCARD-ERROR-MSG
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsErrorMsg);
      // COB(588): MOVE LIT-THISPGM     TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
          ws.wsConstants.litThispgm);
      // COB(589): MOVE LIT-THISMAPSET  TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(
          ws.wsConstants.litThismapset);
      // COB(590): MOVE LIT-THISMAP     TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
      // COB(592): MOVE LIT-THISPGM     TO CCARD-NEXT-PROG
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.setValue(ws.wsConstants.litThispgm);
      // COB(593): MOVE LIT-THISMAPSET  TO CCARD-NEXT-MAPSET
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsConstants.litThismapset);
      // COB(594): MOVE LIT-THISMAP     TO CCARD-NEXT-MAP
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsConstants.litThismap);
      // COB(597): GO TO COMMON-RETURN
      //        PERFORM 1000-SEND-MAP
      //           THRU 1000-SEND-MAP
      return Flow.commonReturn;
      // COB(598): END-IF
    }
    // COB(600): MOVE LIT-THISPGM        TO CCARD-NEXT-PROG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.setValue(ws.wsConstants.litThispgm);
    // COB(601): GO TO COMMON-RETURN
    return Flow.commonReturn;
  }

  protected void commonReturn() {
    // COB(605): MOVE  LIT-THISTRANID TO CDEMO-FROM-TRANID
    ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
        ws.wsConstants.litThistranid);
    // COB(606): MOVE  LIT-THISPGM     TO CDEMO-FROM-PROGRAM
    ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
        ws.wsConstants.litThispgm);
    // COB(607): MOVE  LIT-THISMAPSET  TO CDEMO-LAST-MAPSET
    ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(
        ws.wsConstants.litThismapset);
    // COB(608): MOVE  LIT-THISMAP     TO CDEMO-LAST-MAP
    ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsConstants.litThismap);
    // COB(609): MOVE  CARDDEMO-COMMAREA    TO WS-COMMAREA
    ws.wsCommarea.setValue(ws.cocom01y.carddemoCommarea);
    // COB(610): MOVE  WS-THIS-PROGCOMMAREA TO
    // COB(610):        WS-COMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
    // COB(610):                     LENGTH OF WS-THIS-PROGCOMMAREA )
    ws.wsCommarea.setValue(
        ws.wsThisProgcommarea,
        0,
        ws.cocom01y.carddemoCommarea.length(),
        ws.wsThisProgcommarea.length());
    // COB(615): EXEC CICS RETURN
    // COB(615):      TRANSID (LIT-THISTRANID)
    // COB(615):      COMMAREA (WS-COMMAREA)
    // COB(615):      LENGTH(LENGTH OF WS-COMMAREA)
    // COB(615): END-EXEC
    try {
      supernaut
          .returnTransid(ws.wsConstants.litThistranid) //
          .commarea(ws.wsCommarea) //
          .length(ws.wsCommarea.length()) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  protected void _1000SendMap() {
    // COB(625): PERFORM 1100-SCREEN-INIT
    // COB(625):    THRU 1100-SCREEN-INIT-EXIT
    _1100ScreenInit();
    // COB(627): PERFORM 1200-SCREEN-ARRAY-INIT
    // COB(627):    THRU 1200-SCREEN-ARRAY-INIT-EXIT
    _1200ScreenArrayInit();
    // COB(629): PERFORM 1250-SETUP-ARRAY-ATTRIBS
    // COB(629):    THRU 1250-SETUP-ARRAY-ATTRIBS-EXIT
    _1250SetupArrayAttribs();
    // COB(631): PERFORM 1300-SETUP-SCREEN-ATTRS
    // COB(631):    THRU 1300-SETUP-SCREEN-ATTRS-EXIT
    _1300SetupScreenAttrs();
    // COB(633): PERFORM 1400-SETUP-MESSAGE
    // COB(633):    THRU 1400-SETUP-MESSAGE-EXIT
    _1400SetupMessage();
    // COB(635): PERFORM 1500-SEND-SCREEN
    // COB(635):    THRU 1500-SEND-SCREEN-EXIT
    _1500SendScreen();
  }

  protected void _1100ScreenInit() {
    // COB(643): MOVE LOW-VALUES             TO CCRDLIAO
    ws.cocrdli.ccrdliao.lowValues();
    // COB(645): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(647): MOVE CCDA-TITLE01           TO TITLE01O OF CCRDLIAO
    ws.cocrdli.ccrdliao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(648): MOVE CCDA-TITLE02           TO TITLE02O OF CCRDLIAO
    ws.cocrdli.ccrdliao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(649): MOVE LIT-THISTRANID         TO TRNNAMEO OF CCRDLIAO
    ws.cocrdli.ccrdliao.trnnameo.setValue(ws.wsConstants.litThistranid);
    // COB(650): MOVE LIT-THISPGM            TO PGMNAMEO OF CCRDLIAO
    ws.cocrdli.ccrdliao.pgmnameo.setValue(ws.wsConstants.litThispgm);
    // COB(652): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(654): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(655): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(656): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(658): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF CCRDLIAO
    ws.cocrdli.ccrdliao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(660): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(661): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(662): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(664): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF CCRDLIAO
    ws.cocrdli.ccrdliao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
    // COB(667): MOVE WS-CA-SCREEN-NUM       TO PAGENOO  OF CCRDLIAO
    //     PAGE NUMBER
    //
    ws.cocrdli.ccrdliao.pagenoo.setValue(ws.wsThisProgcommarea.wsCaScreenNum);
    // COB(669): SET WS-NO-INFO-MESSAGE      TO TRUE
    ws.wsMiscStorage.setWsNoInfoMessage(true);
    // COB(670): MOVE WS-INFO-MSG            TO INFOMSGO OF CCRDLIAO
    ws.cocrdli.ccrdliao.infomsgo.setValue(ws.wsMiscStorage.wsInfoMsg);
    // COB(671): MOVE DFHBMDAR               TO INFOMSGC OF CCRDLIAO
    ws.cocrdli.ccrdliao.infomsgc.setValue(Dfhbmsca.DFHBMDAR.getValue());
  }

  protected void _1200ScreenArrayInit() {
    // COB(680): IF   WS-EACH-CARD(1)            EQUAL LOW-VALUES
    //     USE REDEFINES AND CLEAN UP REPETITIVE CODE
    if (ws.wsThisProgcommarea
        .wsScreenData
        .filler254
        .wsScreenRowsAtIndex(0)
        .wsEachRow
        .wsEachCard
        .isLowValues()) {
      // COB(681): CONTINUE
      // do nothing
      // COB(682): ELSE
    } else {
      // COB(683): MOVE WS-EDIT-SELECT(1)       TO CRDSEL1O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdsel1o.setValue(
          ws.wsMiscStorage.wsEditSelectArray.wsEditSelectAtIndex(0));
      // COB(684): MOVE WS-ROW-ACCTNO(1)        TO ACCTNO1O OF CCRDLIAO
      ws.cocrdli.ccrdliao.acctno1o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(0)
              .wsEachRow
              .wsEachCard
              .wsRowAcctno);
      // COB(685): MOVE WS-ROW-CARD-NUM(1)      TO CRDNUM1O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdnum1o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(0)
              .wsEachRow
              .wsEachCard
              .wsRowCardNum);
      // COB(686): MOVE WS-ROW-CARD-STATUS(1)   TO CRDSTS1O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdsts1o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(0)
              .wsEachRow
              .wsEachCard
              .wsRowCardStatus);
      // COB(687): END-IF
    }
    // COB(689): IF   WS-EACH-CARD(2)        EQUAL LOW-VALUES
    if (ws.wsThisProgcommarea
        .wsScreenData
        .filler254
        .wsScreenRowsAtIndex(1)
        .wsEachRow
        .wsEachCard
        .isLowValues()) {
      // COB(690): CONTINUE
      // do nothing
      // COB(691): ELSE
    } else {
      // COB(692): MOVE WS-EDIT-SELECT(2)       TO CRDSEL2O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdsel2o.setValue(
          ws.wsMiscStorage.wsEditSelectArray.wsEditSelectAtIndex(1));
      // COB(693): MOVE WS-ROW-ACCTNO(2)        TO ACCTNO2O OF CCRDLIAO
      ws.cocrdli.ccrdliao.acctno2o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(1)
              .wsEachRow
              .wsEachCard
              .wsRowAcctno);
      // COB(694): MOVE WS-ROW-CARD-NUM(2)      TO CRDNUM2O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdnum2o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(1)
              .wsEachRow
              .wsEachCard
              .wsRowCardNum);
      // COB(695): MOVE WS-ROW-CARD-STATUS(2)   TO CRDSTS2O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdsts2o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(1)
              .wsEachRow
              .wsEachCard
              .wsRowCardStatus);
      // COB(696): END-IF
    }
    // COB(698): IF   WS-EACH-CARD(3)        EQUAL LOW-VALUES
    if (ws.wsThisProgcommarea
        .wsScreenData
        .filler254
        .wsScreenRowsAtIndex(2)
        .wsEachRow
        .wsEachCard
        .isLowValues()) {
      // COB(699): CONTINUE
      // do nothing
      // COB(700): ELSE
    } else {
      // COB(701): MOVE WS-EDIT-SELECT(3)       TO CRDSEL3O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdsel3o.setValue(
          ws.wsMiscStorage.wsEditSelectArray.wsEditSelectAtIndex(2));
      // COB(702): MOVE WS-ROW-ACCTNO(3)        TO ACCTNO3O OF CCRDLIAO
      ws.cocrdli.ccrdliao.acctno3o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(2)
              .wsEachRow
              .wsEachCard
              .wsRowAcctno);
      // COB(703): MOVE WS-ROW-CARD-NUM(3)      TO CRDNUM3O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdnum3o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(2)
              .wsEachRow
              .wsEachCard
              .wsRowCardNum);
      // COB(704): MOVE WS-ROW-CARD-STATUS(3)   TO CRDSTS3O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdsts3o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(2)
              .wsEachRow
              .wsEachCard
              .wsRowCardStatus);
      // COB(705): END-IF
    }
    // COB(707): IF   WS-EACH-CARD(4)        EQUAL LOW-VALUES
    if (ws.wsThisProgcommarea
        .wsScreenData
        .filler254
        .wsScreenRowsAtIndex(3)
        .wsEachRow
        .wsEachCard
        .isLowValues()) {
      // COB(708): CONTINUE
      // do nothing
      // COB(709): ELSE
    } else {
      // COB(710): MOVE WS-EDIT-SELECT(4)       TO CRDSEL4O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdsel4o.setValue(
          ws.wsMiscStorage.wsEditSelectArray.wsEditSelectAtIndex(3));
      // COB(711): MOVE WS-ROW-ACCTNO(4)        TO ACCTNO4O OF CCRDLIAO
      ws.cocrdli.ccrdliao.acctno4o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(3)
              .wsEachRow
              .wsEachCard
              .wsRowAcctno);
      // COB(712): MOVE WS-ROW-CARD-NUM(4)      TO CRDNUM4O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdnum4o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(3)
              .wsEachRow
              .wsEachCard
              .wsRowCardNum);
      // COB(713): MOVE WS-ROW-CARD-STATUS(4)   TO CRDSTS4O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdsts4o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(3)
              .wsEachRow
              .wsEachCard
              .wsRowCardStatus);
      // COB(714): END-IF
    }
    // COB(716): IF   WS-EACH-CARD(5)        EQUAL LOW-VALUES
    if (ws.wsThisProgcommarea
        .wsScreenData
        .filler254
        .wsScreenRowsAtIndex(4)
        .wsEachRow
        .wsEachCard
        .isLowValues()) {
      // COB(717): CONTINUE
      // do nothing
      // COB(718): ELSE
    } else {
      // COB(719): MOVE WS-EDIT-SELECT(5)       TO CRDSEL5O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdsel5o.setValue(
          ws.wsMiscStorage.wsEditSelectArray.wsEditSelectAtIndex(4));
      // COB(720): MOVE WS-ROW-ACCTNO(5)        TO ACCTNO5O OF CCRDLIAO
      ws.cocrdli.ccrdliao.acctno5o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(4)
              .wsEachRow
              .wsEachCard
              .wsRowAcctno);
      // COB(721): MOVE WS-ROW-CARD-NUM(5)      TO CRDNUM5O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdnum5o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(4)
              .wsEachRow
              .wsEachCard
              .wsRowCardNum);
      // COB(722): MOVE WS-ROW-CARD-STATUS(5)   TO CRDSTS5O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdsts5o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(4)
              .wsEachRow
              .wsEachCard
              .wsRowCardStatus);
      // COB(723): END-IF
    }
    // COB(726): IF   WS-EACH-CARD(6)        EQUAL LOW-VALUES
    if (ws.wsThisProgcommarea
        .wsScreenData
        .filler254
        .wsScreenRowsAtIndex(5)
        .wsEachRow
        .wsEachCard
        .isLowValues()) {
      // COB(727): CONTINUE
      // do nothing
      // COB(728): ELSE
    } else {
      // COB(729): MOVE WS-EDIT-SELECT(6)       TO CRDSEL6O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdsel6o.setValue(
          ws.wsMiscStorage.wsEditSelectArray.wsEditSelectAtIndex(5));
      // COB(730): MOVE WS-ROW-ACCTNO(6)        TO ACCTNO6O OF CCRDLIAO
      ws.cocrdli.ccrdliao.acctno6o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(5)
              .wsEachRow
              .wsEachCard
              .wsRowAcctno);
      // COB(731): MOVE WS-ROW-CARD-NUM(6)      TO CRDNUM6O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdnum6o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(5)
              .wsEachRow
              .wsEachCard
              .wsRowCardNum);
      // COB(732): MOVE WS-ROW-CARD-STATUS(6)   TO CRDSTS6O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdsts6o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(5)
              .wsEachRow
              .wsEachCard
              .wsRowCardStatus);
      // COB(733): END-IF
    }
    // COB(735): IF   WS-EACH-CARD(7)        EQUAL LOW-VALUES
    if (ws.wsThisProgcommarea
        .wsScreenData
        .filler254
        .wsScreenRowsAtIndex(6)
        .wsEachRow
        .wsEachCard
        .isLowValues()) {
      // COB(736): CONTINUE
      // do nothing
      // COB(737): ELSE
    } else {
      // COB(738): MOVE WS-EDIT-SELECT(7)       TO CRDSEL7O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdsel7o.setValue(
          ws.wsMiscStorage.wsEditSelectArray.wsEditSelectAtIndex(6));
      // COB(739): MOVE WS-ROW-ACCTNO(7)        TO ACCTNO7O OF CCRDLIAO
      ws.cocrdli.ccrdliao.acctno7o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(6)
              .wsEachRow
              .wsEachCard
              .wsRowAcctno);
      // COB(740): MOVE WS-ROW-CARD-NUM(7)      TO CRDNUM7O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdnum7o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(6)
              .wsEachRow
              .wsEachCard
              .wsRowCardNum);
      // COB(741): MOVE WS-ROW-CARD-STATUS(7)   TO CRDSTS7O OF CCRDLIAO
      ws.cocrdli.ccrdliao.crdsts7o.setValue(
          ws.wsThisProgcommarea.wsScreenData.filler254.wsScreenRowsAtIndex(6)
              .wsEachRow
              .wsEachCard
              .wsRowCardStatus);
      // COB(742): END-IF
    }
  }

  protected void _1250SetupArrayAttribs() {
    // COB(751): IF   WS-EACH-CARD(1)            EQUAL LOW-VALUES
    // COB(751): OR   FLG-PROTECT-SELECT-ROWS-YES
    //     USE REDEFINES AND CLEAN UP REPETITIVE CODE
    if (ws.wsThisProgcommarea
            .wsScreenData
            .filler254
            .wsScreenRowsAtIndex(0)
            .wsEachRow
            .wsEachCard
            .isLowValues()
        || ws.wsMiscStorage.cicsOutputEditVars.flgProtectSelectRowsYes()) {
      // COB(753): MOVE DFHBMPRF                TO CRDSEL1A OF CCRDLIAI
      ws.cocrdli.ccrdliai.filler75.crdsel1a.setValue(Dfhbmsca.DFHBMPRF.getValue());
      // COB(754): ELSE
    } else {
      // COB(755): IF WS-ROW-CRDSELECT-ERROR(1) = '1'
      if (ws.wsMiscStorage
          .wsEditSelectErrorFlagx
          .wsEditSelectErrorsAtIndex(0)
          .wsRowCrdselectError
          .equals("1")) {
        // COB(756): MOVE DFHRED               TO CRDSEL1C OF CCRDLIAO
        ws.cocrdli.ccrdliao.crdsel1c.setValue(Dfhbmsca.DFHRED.getValue());
        // COB(757): IF WS-EDIT-SELECT(1) = SPACE OR LOW-VALUES
        if (ws.wsMiscStorage.wsEditSelectArray.wsEditSelectAtIndex(0).isSpaces()
            || ws.wsMiscStorage.wsEditSelectArray.wsEditSelectAtIndex(0).isLowValues()) {
          // COB(758): MOVE '*'               TO CRDSEL1O OF CCRDLIAO
          ws.cocrdli.ccrdliao.crdsel1o.setString("*");
          // COB(759): END-IF
        }
        // COB(760): END-IF
      }
      // COB(761): MOVE DFHBMFSE                TO CRDSEL1A OF CCRDLIAI
      ws.cocrdli.ccrdliai.filler75.crdsel1a.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(762): END-IF
    }
    // COB(764): IF   WS-EACH-CARD(2)            EQUAL LOW-VALUES
    // COB(764): OR   FLG-PROTECT-SELECT-ROWS-YES
    if (ws.wsThisProgcommarea
            .wsScreenData
            .filler254
            .wsScreenRowsAtIndex(1)
            .wsEachRow
            .wsEachCard
            .isLowValues()
        || ws.wsMiscStorage.cicsOutputEditVars.flgProtectSelectRowsYes()) {
      // COB(766): MOVE DFHBMPRO                TO CRDSEL2A OF CCRDLIAI
      ws.cocrdli.ccrdliai.filler99.crdsel2a.setValue(Dfhbmsca.DFHBMPRO.getValue());
      // COB(767): ELSE
    } else {
      // COB(768): IF WS-ROW-CRDSELECT-ERROR(2) = '1'
      if (ws.wsMiscStorage
          .wsEditSelectErrorFlagx
          .wsEditSelectErrorsAtIndex(1)
          .wsRowCrdselectError
          .equals("1")) {
        // COB(769): MOVE DFHRED               TO CRDSEL2C OF CCRDLIAO
        ws.cocrdli.ccrdliao.crdsel2c.setValue(Dfhbmsca.DFHRED.getValue());
        // COB(770): MOVE -1                   TO CRDSEL2L OF CCRDLIAI
        ws.cocrdli.ccrdliai.crdsel2l.setValue(-1);
        // COB(771): END-IF
      }
      // COB(772): MOVE DFHBMFSE                TO CRDSEL2A OF CCRDLIAI
      ws.cocrdli.ccrdliai.filler99.crdsel2a.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(773): END-IF
    }
    // COB(775): IF   WS-EACH-CARD(3)            EQUAL LOW-VALUES
    // COB(775): OR   FLG-PROTECT-SELECT-ROWS-YES
    if (ws.wsThisProgcommarea
            .wsScreenData
            .filler254
            .wsScreenRowsAtIndex(2)
            .wsEachRow
            .wsEachCard
            .isLowValues()
        || ws.wsMiscStorage.cicsOutputEditVars.flgProtectSelectRowsYes()) {
      // COB(777): MOVE DFHBMPRO                TO CRDSEL3A OF CCRDLIAI
      ws.cocrdli.ccrdliai.filler129.crdsel3a.setValue(Dfhbmsca.DFHBMPRO.getValue());
      // COB(779): ELSE
    } else {
      // COB(780): IF WS-ROW-CRDSELECT-ERROR(3) = '1'
      if (ws.wsMiscStorage
          .wsEditSelectErrorFlagx
          .wsEditSelectErrorsAtIndex(2)
          .wsRowCrdselectError
          .equals("1")) {
        // COB(781): MOVE DFHRED               TO CRDSEL3C OF CCRDLIAO
        ws.cocrdli.ccrdliao.crdsel3c.setValue(Dfhbmsca.DFHRED.getValue());
        // COB(782): MOVE -1                   TO CRDSEL3L OF CCRDLIAI
        ws.cocrdli.ccrdliai.crdsel3l.setValue(-1);
        // COB(783): END-IF
      }
      // COB(784): MOVE DFHBMFSE                TO CRDSEL3A OF CCRDLIAI
      ws.cocrdli.ccrdliai.filler129.crdsel3a.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(785): END-IF
    }
    // COB(787): IF   WS-EACH-CARD(4)            EQUAL LOW-VALUES
    // COB(787): OR   FLG-PROTECT-SELECT-ROWS-YES
    if (ws.wsThisProgcommarea
            .wsScreenData
            .filler254
            .wsScreenRowsAtIndex(3)
            .wsEachRow
            .wsEachCard
            .isLowValues()
        || ws.wsMiscStorage.cicsOutputEditVars.flgProtectSelectRowsYes()) {
      // COB(789): MOVE DFHBMPRO                TO CRDSEL4A OF CCRDLIAI
      // COB(789): I
      ws.cocrdli.ccrdliai.filler159.crdsel4a.setValue(Dfhbmsca.DFHBMPRO.getValue());
      ws.wsMiscStorage.wsSubscriptVars.i.setValue(Dfhbmsca.DFHBMPRO.getValue());
      // COB(791): ELSE
    } else {
      // COB(792): IF WS-ROW-CRDSELECT-ERROR(4) = '1'
      if (ws.wsMiscStorage
          .wsEditSelectErrorFlagx
          .wsEditSelectErrorsAtIndex(3)
          .wsRowCrdselectError
          .equals("1")) {
        // COB(793): MOVE DFHRED               TO CRDSEL4C OF CCRDLIAO
        ws.cocrdli.ccrdliao.crdsel4c.setValue(Dfhbmsca.DFHRED.getValue());
        // COB(794): MOVE -1                   TO CRDSEL4L OF CCRDLIAI
        ws.cocrdli.ccrdliai.crdsel4l.setValue(-1);
        // COB(795): END-IF
      }
      // COB(796): MOVE DFHBMFSE                TO CRDSEL4A OF CCRDLIAI
      ws.cocrdli.ccrdliai.filler159.crdsel4a.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(797): END-IF
    }
    // COB(799): IF   WS-EACH-CARD(5)            EQUAL LOW-VALUES
    // COB(799): OR   FLG-PROTECT-SELECT-ROWS-YES
    if (ws.wsThisProgcommarea
            .wsScreenData
            .filler254
            .wsScreenRowsAtIndex(4)
            .wsEachRow
            .wsEachCard
            .isLowValues()
        || ws.wsMiscStorage.cicsOutputEditVars.flgProtectSelectRowsYes()) {
      // COB(801): MOVE DFHBMPRO                TO CRDSEL5A OF CCRDLIAI
      ws.cocrdli.ccrdliai.filler189.crdsel5a.setValue(Dfhbmsca.DFHBMPRO.getValue());
      // COB(802): ELSE
    } else {
      // COB(803): IF WS-ROW-CRDSELECT-ERROR(5) = '1'
      if (ws.wsMiscStorage
          .wsEditSelectErrorFlagx
          .wsEditSelectErrorsAtIndex(4)
          .wsRowCrdselectError
          .equals("1")) {
        // COB(804): MOVE DFHRED               TO CRDSEL5C OF CCRDLIAO
        ws.cocrdli.ccrdliao.crdsel5c.setValue(Dfhbmsca.DFHRED.getValue());
        // COB(805): MOVE -1                   TO CRDSEL5L OF CCRDLIAI
        ws.cocrdli.ccrdliai.crdsel5l.setValue(-1);
        // COB(806): END-IF
      }
      // COB(807): MOVE DFHBMFSE                TO CRDSEL5A OF CCRDLIAI
      ws.cocrdli.ccrdliai.filler189.crdsel5a.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(808): END-IF
    }
    // COB(810): IF   WS-EACH-CARD(6)            EQUAL LOW-VALUES
    // COB(810): OR   FLG-PROTECT-SELECT-ROWS-YES
    if (ws.wsThisProgcommarea
            .wsScreenData
            .filler254
            .wsScreenRowsAtIndex(5)
            .wsEachRow
            .wsEachCard
            .isLowValues()
        || ws.wsMiscStorage.cicsOutputEditVars.flgProtectSelectRowsYes()) {
      // COB(812): MOVE DFHBMPRO                TO CRDSEL6A OF CCRDLIAI
      ws.cocrdli.ccrdliai.filler219.crdsel6a.setValue(Dfhbmsca.DFHBMPRO.getValue());
      // COB(814): ELSE
    } else {
      // COB(815): IF WS-ROW-CRDSELECT-ERROR(6) = '1'
      if (ws.wsMiscStorage
          .wsEditSelectErrorFlagx
          .wsEditSelectErrorsAtIndex(5)
          .wsRowCrdselectError
          .equals("1")) {
        // COB(816): MOVE DFHRED               TO CRDSEL6C OF CCRDLIAO
        ws.cocrdli.ccrdliao.crdsel6c.setValue(Dfhbmsca.DFHRED.getValue());
        // COB(817): MOVE -1                   TO CRDSEL6L OF CCRDLIAI
        ws.cocrdli.ccrdliai.crdsel6l.setValue(-1);
        // COB(818): END-IF
      }
      // COB(819): MOVE DFHBMFSE                TO CRDSEL6A OF CCRDLIAI
      ws.cocrdli.ccrdliai.filler219.crdsel6a.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(820): END-IF
    }
    // COB(822): IF   WS-EACH-CARD(7)            EQUAL LOW-VALUES
    // COB(822): OR   FLG-PROTECT-SELECT-ROWS-YES
    if (ws.wsThisProgcommarea
            .wsScreenData
            .filler254
            .wsScreenRowsAtIndex(6)
            .wsEachRow
            .wsEachCard
            .isLowValues()
        || ws.wsMiscStorage.cicsOutputEditVars.flgProtectSelectRowsYes()) {
      // COB(824): MOVE DFHBMPRO                TO CRDSEL7A OF CCRDLIAI
      ws.cocrdli.ccrdliai.filler249.crdsel7a.setValue(Dfhbmsca.DFHBMPRO.getValue());
      // COB(825): ELSE
    } else {
      // COB(826): IF WS-ROW-CRDSELECT-ERROR(7) = '1'
      if (ws.wsMiscStorage
          .wsEditSelectErrorFlagx
          .wsEditSelectErrorsAtIndex(6)
          .wsRowCrdselectError
          .equals("1")) {
        // COB(827): MOVE DFHRED               TO CRDSEL7C OF CCRDLIAO
        ws.cocrdli.ccrdliao.crdsel7c.setValue(Dfhbmsca.DFHRED.getValue());
        // COB(828): MOVE -1                   TO CRDSEL7L OF CCRDLIAI
        ws.cocrdli.ccrdliai.crdsel7l.setValue(-1);
        // COB(829): END-IF
      }
      // COB(830): MOVE DFHBMFSE                TO CRDSEL7A OF CCRDLIAI
      ws.cocrdli.ccrdliai.filler249.crdsel7a.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(831): END-IF
    }
  }

  protected void _1300SetupScreenAttrs() {
    // COB(839): IF EIBCALEN = 0
    // COB(839): OR (CDEMO-PGM-ENTER
    // COB(839): AND CDEMO-FROM-PROGRAM = LIT-MENUPGM)
    //     INITIALIZE SEARCH CRITERIA
    if (dfheiblk.getEibcalen() == 0
        || (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()
            && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsConstants.litMenupgm))) {
      // COB(842): CONTINUE
      // do nothing
      // COB(843): ELSE
    } else {
      // COB(844): EVALUATE TRUE
      // COB(845): WHEN FLG-ACCTFILTER-ISVALID
      if ((ws.wsMiscStorage.flgAcctfilterIsvalid())
          // COB(846): WHEN FLG-ACCTFILTER-NOT-OK
          || ws.wsMiscStorage.flgAcctfilterNotOk()) {
        // COB(847): MOVE CC-ACCT-ID   TO ACCTSIDO OF CCRDLIAO
        ws.cocrdli.ccrdliao.acctsido.setValue(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
        // COB(848): MOVE DFHBMFSE     TO ACCTSIDA OF CCRDLIAI
        ws.cocrdli.ccrdliai.filler63.acctsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
        // COB(849): WHEN CDEMO-ACCT-ID = 0
      } else if (ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.equals(0)) {
        // COB(850): MOVE LOW-VALUES   TO ACCTSIDO OF CCRDLIAO
        ws.cocrdli.ccrdliao.acctsido.lowValues();
        // COB(851): WHEN OTHER
      } else {
        // COB(852): MOVE CDEMO-ACCT-ID TO ACCTSIDO OF CCRDLIAO
        ws.cocrdli.ccrdliao.acctsido.setValue(
            ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId);
        // COB(853): MOVE DFHBMFSE      TO ACCTSIDA OF CCRDLIAI
        ws.cocrdli.ccrdliai.filler63.acctsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
        // COB(854): END-EVALUATE
      }
      // COB(856): EVALUATE TRUE
      // COB(857): WHEN FLG-CARDFILTER-ISVALID
      if ((ws.wsMiscStorage.flgCardfilterIsvalid())
          // COB(858): WHEN FLG-CARDFILTER-NOT-OK
          || ws.wsMiscStorage.flgCardfilterNotOk()) {
        // COB(859): MOVE CC-CARD-NUM  TO CARDSIDO OF CCRDLIAO
        ws.cocrdli.ccrdliao.cardsido.setValue(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum);
        // COB(860): MOVE DFHBMFSE     TO CARDSIDA OF CCRDLIAI
        ws.cocrdli.ccrdliai.filler69.cardsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
        // COB(861): WHEN CDEMO-CARD-NUM = 0
      } else if (ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.equals(0)) {
        // COB(862): MOVE LOW-VALUES   TO CARDSIDO OF CCRDLIAO
        ws.cocrdli.ccrdliao.cardsido.lowValues();
        // COB(863): WHEN OTHER
      } else {
        // COB(864): MOVE CDEMO-CARD-NUM
        // COB(864):                    TO CARDSIDO OF CCRDLIAO
        ws.cocrdli.ccrdliao.cardsido.setValue(
            ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum);
        // COB(866): MOVE DFHBMFSE      TO CARDSIDA OF CCRDLIAI
        ws.cocrdli.ccrdliai.filler69.cardsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
        // COB(867): END-EVALUATE
      }
      // COB(868): END-IF
    }
    // COB(872): IF FLG-ACCTFILTER-NOT-OK
    //     POSITION CURSOR
    if (ws.wsMiscStorage.flgAcctfilterNotOk()) {
      // COB(873): MOVE  DFHRED             TO ACCTSIDC OF CCRDLIAO
      ws.cocrdli.ccrdliao.acctsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(874): MOVE  -1                 TO ACCTSIDL OF CCRDLIAI
      ws.cocrdli.ccrdliai.acctsidl.setValue(-1);
      // COB(875): END-IF
    }
    // COB(877): IF FLG-CARDFILTER-NOT-OK
    if (ws.wsMiscStorage.flgCardfilterNotOk()) {
      // COB(878): MOVE  DFHRED             TO CARDSIDC OF CCRDLIAO
      ws.cocrdli.ccrdliao.cardsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(879): MOVE  -1                 TO CARDSIDL OF CCRDLIAI
      ws.cocrdli.ccrdliai.cardsidl.setValue(-1);
      // COB(880): END-IF
    }
    // COB(884): IF INPUT-OK
    //     IF NO ERRORS POSITION CURSOR AT ACCTID
    if (ws.wsMiscStorage.inputOk()) {
      // COB(885): MOVE   -1                 TO ACCTSIDL OF CCRDLIAI
      ws.cocrdli.ccrdliai.acctsidl.setValue(-1);
      // COB(886): END-IF
    }
  }

  protected void _1400SetupMessage() {
    // COB(897): EVALUATE TRUE
    //     SETUP MESSAGE
    // COB(898): WHEN FLG-ACCTFILTER-NOT-OK
    if ((ws.wsMiscStorage.flgAcctfilterNotOk())
        // COB(899): WHEN FLG-CARDFILTER-NOT-OK
        || ws.wsMiscStorage.flgCardfilterNotOk()) {
      // COB(900): CONTINUE
      // do nothing
      // COB(901): WHEN CCARD-AID-PFK07
      // COB(901):     AND CA-FIRST-PAGE
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk07()
        && ws.wsThisProgcommarea.caFirstPage()) {
      // COB(903): MOVE 'NO PREVIOUS PAGES TO DISPLAY'
      // COB(903): TO WS-ERROR-MSG
      ws.wsMiscStorage.wsErrorMsg.setString("NO PREVIOUS PAGES TO DISPLAY");
      // COB(905): WHEN CCARD-AID-PFK08
      // COB(905):  AND CA-NEXT-PAGE-NOT-EXISTS
      // COB(905):  AND CA-LAST-PAGE-SHOWN
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk08()
        && ws.wsThisProgcommarea.caNextPageNotExists()
        && ws.wsThisProgcommarea.caLastPageShown()) {
      // COB(908): MOVE 'NO MORE PAGES TO DISPLAY'
      // COB(908): TO WS-ERROR-MSG
      ws.wsMiscStorage.wsErrorMsg.setString("NO MORE PAGES TO DISPLAY");
      // COB(910): WHEN CCARD-AID-PFK08
      // COB(910):  AND CA-NEXT-PAGE-NOT-EXISTS
    } else if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk08()
        && ws.wsThisProgcommarea.caNextPageNotExists()) {
      // COB(912): SET WS-INFORM-REC-ACTIONS TO TRUE
      ws.wsMiscStorage.setWsInformRecActions(true);
      // COB(913): IF  CA-LAST-PAGE-NOT-SHOWN
      // COB(913): AND CA-NEXT-PAGE-NOT-EXISTS
      if (ws.wsThisProgcommarea.caLastPageNotShown()
          && ws.wsThisProgcommarea.caNextPageNotExists()) {
        // COB(915): SET CA-LAST-PAGE-SHOWN TO TRUE
        ws.wsThisProgcommarea.setCaLastPageShown(true);
        // COB(916): END-IF
      }
      // COB(917): WHEN WS-NO-INFO-MESSAGE
    } else if (ws.wsMiscStorage.wsNoInfoMessage()
        // COB(918): WHEN CA-NEXT-PAGE-EXISTS
        || ws.wsThisProgcommarea.caNextPageExists()) {
      // COB(919): SET WS-INFORM-REC-ACTIONS TO TRUE
      ws.wsMiscStorage.setWsInformRecActions(true);
      // COB(920): WHEN OTHER
    } else {
      // COB(921): SET WS-NO-INFO-MESSAGE TO TRUE
      ws.wsMiscStorage.setWsNoInfoMessage(true);
      // COB(922): END-EVALUATE
    }
    // COB(924): MOVE WS-ERROR-MSG          TO ERRMSGO OF CCRDLIAO
    ws.cocrdli.ccrdliao.errmsgo.setValue(ws.wsMiscStorage.wsErrorMsg);
    // COB(926): IF  NOT WS-NO-INFO-MESSAGE
    // COB(926): AND NOT WS-NO-RECORDS-FOUND
    if (!ws.wsMiscStorage.wsNoInfoMessage() && !ws.wsMiscStorage.wsNoRecordsFound()) {
      // COB(928): MOVE WS-INFO-MSG        TO INFOMSGO OF CCRDLIAO
      ws.cocrdli.ccrdliao.infomsgo.setValue(ws.wsMiscStorage.wsInfoMsg);
      // COB(929): MOVE DFHNEUTR           TO INFOMSGC OF CCRDLIAO
      ws.cocrdli.ccrdliao.infomsgc.setValue(Dfhbmsca.DFHNEUTR.getValue());
      // COB(930): END-IF
    }
  }

  protected void _1500SendScreen() {
    // COB(939): EXEC CICS SEND MAP(LIT-THISMAP)
    // COB(939):                MAPSET(LIT-THISMAPSET)
    // COB(939):                FROM(CCRDLIAO)
    // COB(939):                CURSOR
    // COB(939):                ERASE
    // COB(939):                RESP(WS-RESP-CD)
    // COB(939):                FREEKB
    // COB(939): END-EXEC
    supernaut
        .sendMap(ws.wsConstants.litThismap.getValue()) //
        .mapset(ws.wsConstants.litThismapset.getValue()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .from(ws.cocrdli.ccrdliao) //
        .freekb() //
        .erase() //
        .cursor() //
        .exec();
  }

  protected void _2000ReceiveMap() {
    // COB(952): PERFORM 2100-RECEIVE-SCREEN
    // COB(952):    THRU 2100-RECEIVE-SCREEN-EXIT
    _2100ReceiveScreen();
    // COB(955): PERFORM 2200-EDIT-INPUTS
    // COB(955):  THRU   2200-EDIT-INPUTS-EXIT
    _2200EditInputs();
  }

  protected void _2100ReceiveScreen() {
    // COB(963): EXEC CICS RECEIVE MAP(LIT-THISMAP)
    // COB(963):                MAPSET(LIT-THISMAPSET)
    // COB(963):                INTO(CCRDLIAI)
    // COB(963):                RESP(WS-RESP-CD)
    // COB(963): END-EXEC
    supernaut
        .receiveMap(ws.wsConstants.litThismap.getValue()) //
        .mapset(ws.wsConstants.litThismapset.getValue()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .into(ws.cocrdli.ccrdliai) //
        .exec();
    // COB(969): MOVE ACCTSIDI OF CCRDLIAI  TO CC-ACCT-ID
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.setValue(ws.cocrdli.ccrdliai.acctsidi);
    // COB(970): MOVE CARDSIDI OF CCRDLIAI  TO CC-CARD-NUM
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum.setValue(ws.cocrdli.ccrdliai.cardsidi);
    // COB(972): MOVE CRDSEL1I OF CCRDLIAI  TO WS-EDIT-SELECT(1)
    ws.wsMiscStorage
        .wsEditSelectArray
        .wsEditSelectAtIndex(0)
        .setValue(ws.cocrdli.ccrdliai.crdsel1i);
    // COB(973): MOVE CRDSEL2I OF CCRDLIAI  TO WS-EDIT-SELECT(2)
    ws.wsMiscStorage
        .wsEditSelectArray
        .wsEditSelectAtIndex(1)
        .setValue(ws.cocrdli.ccrdliai.crdsel2i);
    // COB(974): MOVE CRDSEL3I OF CCRDLIAI  TO WS-EDIT-SELECT(3)
    ws.wsMiscStorage
        .wsEditSelectArray
        .wsEditSelectAtIndex(2)
        .setValue(ws.cocrdli.ccrdliai.crdsel3i);
    // COB(975): MOVE CRDSEL4I OF CCRDLIAI  TO WS-EDIT-SELECT(4)
    ws.wsMiscStorage
        .wsEditSelectArray
        .wsEditSelectAtIndex(3)
        .setValue(ws.cocrdli.ccrdliai.crdsel4i);
    // COB(976): MOVE CRDSEL5I OF CCRDLIAI  TO WS-EDIT-SELECT(5)
    ws.wsMiscStorage
        .wsEditSelectArray
        .wsEditSelectAtIndex(4)
        .setValue(ws.cocrdli.ccrdliai.crdsel5i);
    // COB(977): MOVE CRDSEL6I OF CCRDLIAI  TO WS-EDIT-SELECT(6)
    ws.wsMiscStorage
        .wsEditSelectArray
        .wsEditSelectAtIndex(5)
        .setValue(ws.cocrdli.ccrdliai.crdsel6i);
    // COB(978): MOVE CRDSEL7I OF CCRDLIAI  TO WS-EDIT-SELECT(7)
    ws.wsMiscStorage
        .wsEditSelectArray
        .wsEditSelectAtIndex(6)
        .setValue(ws.cocrdli.ccrdliai.crdsel7i);
  }

  protected void _2200EditInputs() {
    // COB(986): SET INPUT-OK                   TO TRUE
    ws.wsMiscStorage.setInputOk(true);
    // COB(987): SET FLG-PROTECT-SELECT-ROWS-NO TO TRUE
    ws.wsMiscStorage.cicsOutputEditVars.setFlgProtectSelectRowsNo(true);
    // COB(989): PERFORM 2210-EDIT-ACCOUNT
    // COB(989):    THRU 2210-EDIT-ACCOUNT-EXIT
    _2210EditAccount();
    // COB(992): PERFORM 2220-EDIT-CARD
    // COB(992):    THRU 2220-EDIT-CARD-EXIT
    _2220EditCard();
    // COB(995): PERFORM 2250-EDIT-ARRAY
    // COB(995):    THRU 2250-EDIT-ARRAY-EXIT
    _2250EditArray();
  }

  protected void _2210EditAccount() {
    // COB(1004): SET FLG-ACCTFILTER-BLANK TO TRUE
    ws.wsMiscStorage.setFlgAcctfilterBlank(true);
    // COB(1007): IF CC-ACCT-ID   EQUAL LOW-VALUES
    // COB(1007): OR CC-ACCT-ID   EQUAL SPACES
    // COB(1007): OR CC-ACCT-ID-N EQUAL ZEROS
    //     Not supplied
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isLowValues()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isSpaces()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctIdN.equals(0)) {
      // COB(1010): SET FLG-ACCTFILTER-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterBlank(true);
      // COB(1011): MOVE ZEROES       TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
      // COB(1012): GO TO  2210-EDIT-ACCOUNT-EXIT
      return;
      // COB(1013): END-IF
    }
    // COB(1017): IF CC-ACCT-ID  IS NOT NUMERIC
    //
    //     Not numeric
    //     Not 11 characters
    if (!ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isNumeric()) {
      // COB(1018): SET INPUT-ERROR TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1019): SET FLG-ACCTFILTER-NOT-OK TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
      // COB(1020): SET FLG-PROTECT-SELECT-ROWS-YES TO TRUE
      ws.wsMiscStorage.cicsOutputEditVars.setFlgProtectSelectRowsYes(true);
      // COB(1021): MOVE
      // COB(1021): 'ACCOUNT FILTER,IF SUPPLIED MUST BE A 11 DIGIT NUMBER'
      // COB(1021):                 TO WS-ERROR-MSG
      ws.wsMiscStorage.wsErrorMsg.setString("ACCOUNT FILTER,IF SUPPLIED MUST BE A 11 DIGIT NUMBER");
      // COB(1024): MOVE ZERO       TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
      // COB(1025): GO TO 2210-EDIT-ACCOUNT-EXIT
      return;
      // COB(1026): ELSE
    } else {
      // COB(1027): MOVE CC-ACCT-ID TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.setValue(
          ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
      // COB(1028): SET FLG-ACCTFILTER-ISVALID TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterIsvalid(true);
      // COB(1029): END-IF
    }
  }

  protected void _2220EditCard() {
    // COB(1039): SET FLG-CARDFILTER-BLANK TO TRUE
    //     Not numeric
    //     Not 16 characters
    ws.wsMiscStorage.setFlgCardfilterBlank(true);
    // COB(1042): IF CC-CARD-NUM   EQUAL LOW-VALUES
    // COB(1042): OR CC-CARD-NUM   EQUAL SPACES
    // COB(1042): OR CC-CARD-NUM-N EQUAL ZEROS
    //     Not supplied
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum.isLowValues()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum.isSpaces()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNumN.equals(0)) {
      // COB(1045): SET FLG-CARDFILTER-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgCardfilterBlank(true);
      // COB(1046): MOVE ZEROES       TO CDEMO-CARD-NUM
      ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.zeros();
      // COB(1047): GO TO  2220-EDIT-CARD-EXIT
      return;
      // COB(1048): END-IF
    }
    // COB(1052): IF CC-CARD-NUM  IS NOT NUMERIC
    //
    //     Not numeric
    //     Not 16 characters
    if (!ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum.isNumeric()) {
      // COB(1053): SET INPUT-ERROR TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1054): SET FLG-CARDFILTER-NOT-OK TO TRUE
      ws.wsMiscStorage.setFlgCardfilterNotOk(true);
      // COB(1055): SET FLG-PROTECT-SELECT-ROWS-YES TO TRUE
      ws.wsMiscStorage.cicsOutputEditVars.setFlgProtectSelectRowsYes(true);
      // COB(1056): IF WS-ERROR-MSG-OFF
      if (ws.wsMiscStorage.wsErrorMsgOff()) {
        // COB(1057): MOVE
        // COB(1057):               'CARD ID FILTER,IF SUPPLIED MUST BE A 16 DIGIT NUMBER'
        // COB(1057):              TO WS-ERROR-MSG
        ws.wsMiscStorage.wsErrorMsg.setString(
            "CARD ID FILTER,IF SUPPLIED MUST BE A 16 DIGIT NUMBER");
        // COB(1060): END-IF
      }
      // COB(1061): MOVE ZERO       TO CDEMO-CARD-NUM
      ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.zeros();
      // COB(1062): GO TO 2220-EDIT-CARD-EXIT
      return;
      // COB(1063): ELSE
    } else {
      // COB(1064): MOVE CC-CARD-NUM-N TO CDEMO-CARD-NUM
      ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.setValue(
          ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNumN);
      // COB(1065): SET FLG-CARDFILTER-ISVALID TO TRUE
      ws.wsMiscStorage.setFlgCardfilterIsvalid(true);
      // COB(1066): END-IF
    }
  }

  protected void _2250EditArray() {
    // COB(1075): IF INPUT-ERROR
    if (ws.wsMiscStorage.inputError()) {
      // COB(1076): GO TO 2250-EDIT-ARRAY-EXIT
      return;
      // COB(1077): END-IF
    }
    // COB(1079): INSPECT  WS-EDIT-SELECT-FLAGS
    // COB(1079): TALLYING I
    // COB(1079): FOR ALL 'S'
    // COB(1079):     ALL 'U'
    ws.wsMiscStorage.wsSubscriptVars.i.setValue(
        ws.wsMiscStorage.wsEditSelectFlags.countStrings("S", "U"));
    // COB(1084): IF I > +1
    if (ws.wsMiscStorage.wsSubscriptVars.i.greaterThan(1)) {
      // COB(1085): SET INPUT-ERROR      TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(1086): SET WS-MORE-THAN-1-ACTION TO TRUE
      ws.wsMiscStorage.setWsMoreThan1Action(true);
      // COB(1088): MOVE WS-EDIT-SELECT-FLAGS
      // COB(1088):                     TO WS-EDIT-SELECT-ERROR-FLAGS
      ws.wsMiscStorage.wsEditSelectErrorFlags.setValue(ws.wsMiscStorage.wsEditSelectFlags);
      // COB(1090): INSPECT WS-EDIT-SELECT-ERROR-FLAGS
      // COB(1090):   REPLACING ALL 'S' BY '1'
      // COB(1090):             ALL 'U' BY '1'
      // COB(1090):   CHARACTERS        BY '0'
      ws.wsMiscStorage
          .wsEditSelectErrorFlags
          .inspectAndReplaceAll("S", "1")
          .inspectAndReplaceAll("U", "1")
          .inspectAndReplaceCharacters("0");
      // COB(1095): END-IF
    }
    // COB(1097): MOVE ZERO TO I-SELECTED
    ws.wsMiscStorage.wsSubscriptVars.iSelected.zeros();
    // COB(1099): PERFORM VARYING I FROM 1 BY 1 UNTIL I > 7
    ws.wsMiscStorage.wsSubscriptVars.i.setValue(1);
    for (;
        !ws.wsMiscStorage.wsSubscriptVars.i.greaterThan(7);
        ws.wsMiscStorage.wsSubscriptVars.i.add(1)) {
      // COB(1100): EVALUATE TRUE
      // COB(1101): WHEN SELECT-OK(I)
      if (ws.wsMiscStorage.wsEditSelectArray.selectOk(
          ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1)) {
        // COB(1102): MOVE I TO I-SELECTED
        ws.wsMiscStorage.wsSubscriptVars.iSelected.setValue(ws.wsMiscStorage.wsSubscriptVars.i);
        // COB(1103): IF WS-MORE-THAN-1-ACTION
        if (ws.wsMiscStorage.wsMoreThan1Action()) {
          // COB(1104): MOVE '1' TO WS-ROW-CRDSELECT-ERROR(I)
          ws.wsMiscStorage
              .wsEditSelectErrorFlagx
              .wsEditSelectErrorsAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1)
              .wsRowCrdselectError
              .setString("1");
          // COB(1105): END-IF
        }
        // COB(1106): WHEN SELECT-BLANK(I)
      } else if (ws.wsMiscStorage.wsEditSelectArray.selectBlank(
          ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1)) {
        // COB(1107): CONTINUE
        // do nothing
        // COB(1108): WHEN OTHER
      } else {
        // COB(1109): SET INPUT-ERROR TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(1110): MOVE '1' TO WS-ROW-CRDSELECT-ERROR(I)
        ws.wsMiscStorage
            .wsEditSelectErrorFlagx
            .wsEditSelectErrorsAtIndex(ws.wsMiscStorage.wsSubscriptVars.i.getInt() - 1)
            .wsRowCrdselectError
            .setString("1");
        // COB(1111): IF WS-ERROR-MSG-OFF
        if (ws.wsMiscStorage.wsErrorMsgOff()) {
          // COB(1112): SET WS-INVALID-ACTION-CODE TO TRUE
          ws.wsMiscStorage.setWsInvalidActionCode(true);
          // COB(1113): END-IF
        }
        // COB(1114): END-EVALUATE
      }
      // COB(1115): END-PERFORM
    }
  }

  protected void _9000ReadForward() {
    // COB(1124): MOVE LOW-VALUES           TO WS-ALL-ROWS
    ws.wsThisProgcommarea.wsScreenData.wsAllRows.lowValues();
    // COB(1129): EXEC CICS STARTBR
    // COB(1129):      DATASET(LIT-CARD-FILE)
    // COB(1129):      RIDFLD(WS-CARD-RID-CARDNUM)
    // COB(1129):      KEYLENGTH(LENGTH OF WS-CARD-RID-CARDNUM)
    // COB(1129):      GTEQ
    // COB(1129):      RESP(WS-RESP-CD)
    // COB(1129):      RESP2(WS-REAS-CD)
    // COB(1129): END-EXEC
    // ****************************************************************
    //     Start Browse
    // ****************************************************************
    supernaut
        .startbr(ws.wsConstants.litCardFile.getValue()) //
        .gteq() //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .ridfld(ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum) //
        .keylength(ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum.length()) //
        .exec();
    // COB(1140): MOVE ZEROES TO WS-SCRN-COUNTER
    // ****************************************************************
    //     Loop through records and fetch max screen records
    // ****************************************************************
    ws.wsMiscStorage.wsScrnCounter.zeros();
    // COB(1141): SET CA-NEXT-PAGE-EXISTS    TO TRUE
    ws.wsThisProgcommarea.setCaNextPageExists(true);
    // COB(1142): SET MORE-RECORDS-TO-READ   TO TRUE
    ws.wsMiscStorage.setMoreRecordsToRead(true);
    // COB(1144): PERFORM UNTIL READ-LOOP-EXIT
    while (!ws.wsMiscStorage.readLoopExit()) {
      // COB(1146): EXEC CICS READNEXT
      // COB(1146):      DATASET(LIT-CARD-FILE)
      // COB(1146):      INTO (CARD-RECORD)
      // COB(1146):      LENGTH(LENGTH OF CARD-RECORD)
      // COB(1146):      RIDFLD(WS-CARD-RID-CARDNUM)
      // COB(1146):      KEYLENGTH(LENGTH OF WS-CARD-RID-CARDNUM)
      // COB(1146):      RESP(WS-RESP-CD)
      // COB(1146):      RESP2(WS-REAS-CD)
      // COB(1146): END-EXEC
      supernaut
          .readNext(ws.wsConstants.litCardFile.getValue()) //
          .length(ws.cvact02y.cardRecord.length()) //
          .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
          .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
          .into(ws.cvact02y.cardRecord) //
          .ridfld(ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum) //
          .keylength(ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum.length()) //
          .exec();
      // COB(1156): EVALUATE WS-RESP-CD
      switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
          // COB(1157): WHEN DFHRESP(NORMAL)
        case Dfhresp.NORMAL:
          // COB(1158): WHEN DFHRESP(DUPREC)
        case Dfhresp.DUPREC:
          // COB(1159): PERFORM 9500-FILTER-RECORDS
          // COB(1159):    THRU 9500-FILTER-RECORDS-EXIT
          _9500FilterRecords();
          // COB(1162): IF WS-DONOT-EXCLUDE-THIS-RECORD
          if (ws.wsMiscStorage.wsDonotExcludeThisRecord()) {
            // COB(1163): ADD 1             TO WS-SCRN-COUNTER
            ws.wsMiscStorage.wsScrnCounter.add(1);
            // COB(1165): MOVE CARD-NUM     TO WS-ROW-CARD-NUM(
            // COB(1165): WS-SCRN-COUNTER)
            ws.wsThisProgcommarea
                .wsScreenData
                .filler254
                .wsScreenRowsAtIndex(ws.wsMiscStorage.wsScrnCounter.getInt() - 1)
                .wsEachRow
                .wsEachCard
                .wsRowCardNum
                .setValue(ws.cvact02y.cardRecord.cardNum);
            // COB(1167): MOVE CARD-ACCT-ID TO
            // COB(1167): WS-ROW-ACCTNO(WS-SCRN-COUNTER)
            ws.wsThisProgcommarea
                .wsScreenData
                .filler254
                .wsScreenRowsAtIndex(ws.wsMiscStorage.wsScrnCounter.getInt() - 1)
                .wsEachRow
                .wsEachCard
                .wsRowAcctno
                .setValue(ws.cvact02y.cardRecord.cardAcctId);
            // COB(1169): MOVE CARD-ACTIVE-STATUS
            // COB(1169):                   TO WS-ROW-CARD-STATUS(
            // COB(1169):                   WS-SCRN-COUNTER)
            ws.wsThisProgcommarea
                .wsScreenData
                .filler254
                .wsScreenRowsAtIndex(ws.wsMiscStorage.wsScrnCounter.getInt() - 1)
                .wsEachRow
                .wsEachCard
                .wsRowCardStatus
                .setValue(ws.cvact02y.cardRecord.cardActiveStatus);
            // COB(1173): IF WS-SCRN-COUNTER = 1
            if (ws.wsMiscStorage.wsScrnCounter.equals(1)) {
              // COB(1174): MOVE CARD-ACCT-ID
              // COB(1174):                TO WS-CA-FIRST-CARD-ACCT-ID
              ws.wsThisProgcommarea.wsCaFirstCardkey.wsCaFirstCardAcctId.setValue(
                  ws.cvact02y.cardRecord.cardAcctId);
              // COB(1176): MOVE CARD-NUM  TO WS-CA-FIRST-CARD-NUM
              ws.wsThisProgcommarea.wsCaFirstCardkey.wsCaFirstCardNum.setValue(
                  ws.cvact02y.cardRecord.cardNum);
              // COB(1177): IF   WS-CA-SCREEN-NUM = 0
              if (ws.wsThisProgcommarea.wsCaScreenNum.equals(0)) {
                // COB(1178): ADD   +1     TO WS-CA-SCREEN-NUM
                ws.wsThisProgcommarea.wsCaScreenNum.add(1);
                // COB(1179): ELSE
              } else {
                // COB(1180): CONTINUE
                // do nothing
                // COB(1181): END-IF
              }
              // COB(1182): ELSE
            } else {
              // COB(1183): CONTINUE
              // do nothing
              // COB(1184): END-IF
            }
            // COB(1185): ELSE
          } else {
            // COB(1186): CONTINUE
            // do nothing
            // COB(1187): END-IF
          }
          // COB(1191): IF WS-SCRN-COUNTER = WS-MAX-SCREEN-LINES
          // *****************************************************************
          //             Max Screen size
          // *****************************************************************
          if (ws.wsMiscStorage.wsScrnCounter.equals(ws.wsConstants.wsMaxScreenLines)) {
            // COB(1192): SET READ-LOOP-EXIT  TO TRUE
            ws.wsMiscStorage.setReadLoopExit(true);
            // COB(1194): MOVE CARD-ACCT-ID     TO WS-CA-LAST-CARD-ACCT-ID
            ws.wsThisProgcommarea.wsCaLastCardkey.wsCaLastCardAcctId.setValue(
                ws.cvact02y.cardRecord.cardAcctId);
            // COB(1195): MOVE CARD-NUM         TO WS-CA-LAST-CARD-NUM
            ws.wsThisProgcommarea.wsCaLastCardkey.wsCaLastCardNum.setValue(
                ws.cvact02y.cardRecord.cardNum);
            // COB(1197): EXEC CICS READNEXT
            // COB(1197):   DATASET(LIT-CARD-FILE)
            // COB(1197):   INTO (CARD-RECORD)
            // COB(1197):   LENGTH(LENGTH OF CARD-RECORD)
            // COB(1197):   RIDFLD(WS-CARD-RID-CARDNUM)
            // COB(1197):   KEYLENGTH(LENGTH OF WS-CARD-RID-CARDNUM)
            // COB(1197):   RESP(WS-RESP-CD)
            // COB(1197):   RESP2(WS-REAS-CD)
            // COB(1197): END-EXEC
            supernaut
                .readNext(ws.wsConstants.litCardFile.getValue()) //
                .length(ws.cvact02y.cardRecord.length()) //
                .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
                .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
                .into(ws.cvact02y.cardRecord) //
                .ridfld(ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum) //
                .keylength(
                    ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum.length()) //
                .exec();
            // COB(1207): EVALUATE WS-RESP-CD
            switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
                // COB(1208): WHEN DFHRESP(NORMAL)
              case Dfhresp.NORMAL:
                // COB(1209): WHEN DFHRESP(DUPREC)
              case Dfhresp.DUPREC:
                // COB(1210): SET CA-NEXT-PAGE-EXISTS
                // COB(1210):                   TO TRUE
                ws.wsThisProgcommarea.setCaNextPageExists(true);
                // COB(1212): MOVE CARD-ACCT-ID TO
                // COB(1212):      WS-CA-LAST-CARD-ACCT-ID
                ws.wsThisProgcommarea.wsCaLastCardkey.wsCaLastCardAcctId.setValue(
                    ws.cvact02y.cardRecord.cardAcctId);
                // COB(1214): MOVE CARD-NUM     TO WS-CA-LAST-CARD-NUM
                ws.wsThisProgcommarea.wsCaLastCardkey.wsCaLastCardNum.setValue(
                    ws.cvact02y.cardRecord.cardNum);
                // COB(1215): WHEN DFHRESP(ENDFILE)
                break;
              case Dfhresp.ENDFILE:
                // COB(1216): SET CA-NEXT-PAGE-NOT-EXISTS     TO TRUE
                ws.wsThisProgcommarea.setCaNextPageNotExists(true);
                // COB(1218): IF WS-ERROR-MSG-OFF
                if (ws.wsMiscStorage.wsErrorMsgOff()) {
                  // COB(1219): MOVE 'NO MORE RECORDS TO SHOW'
                  // COB(1219):                 TO WS-ERROR-MSG
                  ws.wsMiscStorage.wsErrorMsg.setString("NO MORE RECORDS TO SHOW");
                  // COB(1221): END-IF
                }
                // COB(1222): WHEN OTHER
                break;
              default:
                // COB(1225): SET READ-LOOP-EXIT      TO TRUE
                //                      This is some kind of error. Change to END BR
                //                      And exit
                ws.wsMiscStorage.setReadLoopExit(true);
                // COB(1226): MOVE 'READ'              TO ERROR-OPNAME
                ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
                // COB(1227): MOVE LIT-CARD-FILE       TO ERROR-FILE
                ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(ws.wsConstants.litCardFile);
                // COB(1228): MOVE WS-RESP-CD          TO ERROR-RESP
                ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
                    ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
                // COB(1229): MOVE WS-REAS-CD          TO ERROR-RESP2
                ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
                    ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
                // COB(1230): MOVE WS-FILE-ERROR-MESSAGE TO WS-ERROR-MSG
                ws.wsMiscStorage.wsErrorMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
                // COB(1231): END-EVALUATE
            }
            // COB(1232): END-IF
          }
          // COB(1233): WHEN DFHRESP(ENDFILE)
          break;
        case Dfhresp.ENDFILE:
          // COB(1234): SET READ-LOOP-EXIT              TO TRUE
          ws.wsMiscStorage.setReadLoopExit(true);
          // COB(1235): SET CA-NEXT-PAGE-NOT-EXISTS     TO TRUE
          ws.wsThisProgcommarea.setCaNextPageNotExists(true);
          // COB(1236): MOVE CARD-ACCT-ID     TO WS-CA-LAST-CARD-ACCT-ID
          ws.wsThisProgcommarea.wsCaLastCardkey.wsCaLastCardAcctId.setValue(
              ws.cvact02y.cardRecord.cardAcctId);
          // COB(1237): MOVE CARD-NUM         TO WS-CA-LAST-CARD-NUM
          ws.wsThisProgcommarea.wsCaLastCardkey.wsCaLastCardNum.setValue(
              ws.cvact02y.cardRecord.cardNum);
          // COB(1238): IF WS-ERROR-MSG-OFF
          if (ws.wsMiscStorage.wsErrorMsgOff()) {
            // COB(1239): MOVE 'NO MORE RECORDS TO SHOW'  TO WS-ERROR-MSG
            ws.wsMiscStorage.wsErrorMsg.setString("NO MORE RECORDS TO SHOW");
            // COB(1240): END-IF
          }
          // COB(1241): IF WS-CA-SCREEN-NUM = 1
          // COB(1241): AND WS-SCRN-COUNTER = 0
          if (ws.wsThisProgcommarea.wsCaScreenNum.equals(1)
              && ws.wsMiscStorage.wsScrnCounter.equals(0)) {
            // COB(1244): SET WS-NO-RECORDS-FOUND    TO TRUE
            //                MOVE 'NO RECORDS TO SHOW'  TO WS-ERROR-MSG
            ws.wsMiscStorage.setWsNoRecordsFound(true);
            // COB(1245): END-IF
          }
          // COB(1246): WHEN OTHER
          break;
        default:
          // COB(1249): SET READ-LOOP-EXIT             TO TRUE
          //            This is some kind of error. Change to END BR
          //            And exit
          ws.wsMiscStorage.setReadLoopExit(true);
          // COB(1250): MOVE 'READ'                     TO ERROR-OPNAME
          ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
          // COB(1251): MOVE LIT-CARD-FILE              TO ERROR-FILE
          ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(ws.wsConstants.litCardFile);
          // COB(1252): MOVE WS-RESP-CD                 TO ERROR-RESP
          ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
          // COB(1253): MOVE WS-REAS-CD                 TO ERROR-RESP2
          ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
          // COB(1254): MOVE WS-FILE-ERROR-MESSAGE      TO WS-ERROR-MSG
          ws.wsMiscStorage.wsErrorMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
          // COB(1255): END-EVALUATE
      }
      // COB(1256): END-PERFORM
    }
    // COB(1258): EXEC CICS ENDBR FILE(LIT-CARD-FILE)
    // COB(1258): END-EXEC
    try {
      supernaut
          .endbr(ws.wsConstants.litCardFile.getValue()) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  protected Flow _9100ReadBackwards() {
    // COB(1266): MOVE LOW-VALUES           TO WS-ALL-ROWS
    ws.wsThisProgcommarea.wsScreenData.wsAllRows.lowValues();
    // COB(1268): MOVE WS-CA-FIRST-CARDKEY  TO WS-CA-LAST-CARDKEY
    ws.wsThisProgcommarea.wsCaLastCardkey.setValue(ws.wsThisProgcommarea.wsCaFirstCardkey);
    // COB(1273): EXEC CICS STARTBR
    // COB(1273):      DATASET(LIT-CARD-FILE)
    // COB(1273):      RIDFLD(WS-CARD-RID-CARDNUM)
    // COB(1273):      KEYLENGTH(LENGTH OF WS-CARD-RID-CARDNUM)
    // COB(1273):      GTEQ
    // COB(1273):      RESP(WS-RESP-CD)
    // COB(1273):      RESP2(WS-REAS-CD)
    // COB(1273): END-EXEC
    // ****************************************************************
    //     Start Browse
    // ****************************************************************
    supernaut
        .startbr(ws.wsConstants.litCardFile.getValue()) //
        .gteq() //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .ridfld(ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum) //
        .keylength(ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum.length()) //
        .exec();
    // COB(1284): COMPUTE WS-SCRN-COUNTER =
    // COB(1284):                         WS-MAX-SCREEN-LINES + 1
    // COB(1284): END-COMPUTE
    // ****************************************************************
    //     Loop through records and fetch max screen records
    // ****************************************************************
    ws.wsMiscStorage.wsScrnCounter.setValue(
        ws.wsConstants.wsMaxScreenLines.getValue().add(new BigDecimal("1")));
    // COB(1287): SET CA-NEXT-PAGE-EXISTS    TO TRUE
    ws.wsThisProgcommarea.setCaNextPageExists(true);
    // COB(1288): SET MORE-RECORDS-TO-READ   TO TRUE
    ws.wsMiscStorage.setMoreRecordsToRead(true);
    // COB(1294): EXEC CICS READPREV
    // COB(1294):      DATASET(LIT-CARD-FILE)
    // COB(1294):      INTO (CARD-RECORD)
    // COB(1294):      LENGTH(LENGTH OF CARD-RECORD)
    // COB(1294):      RIDFLD(WS-CARD-RID-CARDNUM)
    // COB(1294):      KEYLENGTH(LENGTH OF WS-CARD-RID-CARDNUM)
    // COB(1294):      RESP(WS-RESP-CD)
    // COB(1294):      RESP2(WS-REAS-CD)
    // COB(1294): END-EXEC
    // ****************************************************************
    //     Now we show the records from previous set.
    // ****************************************************************
    supernaut
        .readPrev(ws.wsConstants.litCardFile.getValue()) //
        .length(ws.cvact02y.cardRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvact02y.cardRecord) //
        .ridfld(ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum) //
        .keylength(ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum.length()) //
        .exec();
    // COB(1304): EVALUATE WS-RESP-CD
    switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
        // COB(1305): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(1306): WHEN DFHRESP(DUPREC)
      case Dfhresp.DUPREC:
        // COB(1307): SUBTRACT 1          FROM WS-SCRN-COUNTER
        ws.wsMiscStorage.wsScrnCounter.subtract(1);
        // COB(1308): WHEN OTHER
        break;
      default:
        // COB(1311): SET READ-LOOP-EXIT             TO TRUE
        //            This is some kind of error. Change to END BR
        //            And exit
        ws.wsMiscStorage.setReadLoopExit(true);
        // COB(1312): MOVE 'READ'                     TO ERROR-OPNAME
        ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
        // COB(1313): MOVE LIT-CARD-FILE              TO ERROR-FILE
        ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(ws.wsConstants.litCardFile);
        // COB(1314): MOVE WS-RESP-CD                 TO ERROR-RESP
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(1315): MOVE WS-REAS-CD                 TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(1316): MOVE WS-FILE-ERROR-MESSAGE      TO WS-ERROR-MSG
        ws.wsMiscStorage.wsErrorMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
        // COB(1317): GO TO 9100-READ-BACKWARDS-EXIT
        return Flow._9100ReadBackwardsExit;
        // COB(1318): END-EVALUATE
    }
    // COB(1320): PERFORM UNTIL READ-LOOP-EXIT
    while (!ws.wsMiscStorage.readLoopExit()) {
      // COB(1322): EXEC CICS READPREV
      // COB(1322):      DATASET(LIT-CARD-FILE)
      // COB(1322):      INTO (CARD-RECORD)
      // COB(1322):      LENGTH(LENGTH OF CARD-RECORD)
      // COB(1322):      RIDFLD(WS-CARD-RID-CARDNUM)
      // COB(1322):      KEYLENGTH(LENGTH OF WS-CARD-RID-CARDNUM)
      // COB(1322):      RESP(WS-RESP-CD)
      // COB(1322):      RESP2(WS-REAS-CD)
      // COB(1322): END-EXEC
      supernaut
          .readPrev(ws.wsConstants.litCardFile.getValue()) //
          .length(ws.cvact02y.cardRecord.length()) //
          .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
          .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
          .into(ws.cvact02y.cardRecord) //
          .ridfld(ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum) //
          .keylength(ws.wsMiscStorage.wsFileHandlingVars.wsCardRid.wsCardRidCardnum.length()) //
          .exec();
      // COB(1332): EVALUATE WS-RESP-CD
      switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
          // COB(1333): WHEN DFHRESP(NORMAL)
        case Dfhresp.NORMAL:
          // COB(1334): WHEN DFHRESP(DUPREC)
        case Dfhresp.DUPREC:
          // COB(1335): PERFORM 9500-FILTER-RECORDS
          // COB(1335):    THRU 9500-FILTER-RECORDS-EXIT
          _9500FilterRecords();
          // COB(1337): IF WS-DONOT-EXCLUDE-THIS-RECORD
          if (ws.wsMiscStorage.wsDonotExcludeThisRecord()) {
            // COB(1338): MOVE CARD-NUM
            // COB(1338):             TO WS-ROW-CARD-NUM(WS-SCRN-COUNTER)
            ws.wsThisProgcommarea
                .wsScreenData
                .filler254
                .wsScreenRowsAtIndex(ws.wsMiscStorage.wsScrnCounter.getInt() - 1)
                .wsEachRow
                .wsEachCard
                .wsRowCardNum
                .setValue(ws.cvact02y.cardRecord.cardNum);
            // COB(1340): MOVE CARD-ACCT-ID
            // COB(1340):             TO WS-ROW-ACCTNO(WS-SCRN-COUNTER)
            ws.wsThisProgcommarea
                .wsScreenData
                .filler254
                .wsScreenRowsAtIndex(ws.wsMiscStorage.wsScrnCounter.getInt() - 1)
                .wsEachRow
                .wsEachCard
                .wsRowAcctno
                .setValue(ws.cvact02y.cardRecord.cardAcctId);
            // COB(1342): MOVE CARD-ACTIVE-STATUS
            // COB(1342):             TO
            // COB(1342):             WS-ROW-CARD-STATUS(WS-SCRN-COUNTER)
            ws.wsThisProgcommarea
                .wsScreenData
                .filler254
                .wsScreenRowsAtIndex(ws.wsMiscStorage.wsScrnCounter.getInt() - 1)
                .wsEachRow
                .wsEachCard
                .wsRowCardStatus
                .setValue(ws.cvact02y.cardRecord.cardActiveStatus);
            // COB(1346): SUBTRACT 1  FROM WS-SCRN-COUNTER
            ws.wsMiscStorage.wsScrnCounter.subtract(1);
            // COB(1347): IF WS-SCRN-COUNTER = 0
            if (ws.wsMiscStorage.wsScrnCounter.equals(0)) {
              // COB(1348): SET READ-LOOP-EXIT  TO TRUE
              ws.wsMiscStorage.setReadLoopExit(true);
              // COB(1350): MOVE CARD-ACCT-ID
              // COB(1350):          TO WS-CA-FIRST-CARD-ACCT-ID
              ws.wsThisProgcommarea.wsCaFirstCardkey.wsCaFirstCardAcctId.setValue(
                  ws.cvact02y.cardRecord.cardAcctId);
              // COB(1352): MOVE CARD-NUM
              // COB(1352):          TO WS-CA-FIRST-CARD-NUM
              ws.wsThisProgcommarea.wsCaFirstCardkey.wsCaFirstCardNum.setValue(
                  ws.cvact02y.cardRecord.cardNum);
              // COB(1354): ELSE
            } else {
              // COB(1355): CONTINUE
              // do nothing
              // COB(1356): END-IF
            }
            // COB(1357): ELSE
          } else {
            // COB(1358): CONTINUE
            // do nothing
            // COB(1359): END-IF
          }
          // COB(1361): WHEN OTHER
          break;
        default:
          // COB(1364): SET READ-LOOP-EXIT             TO TRUE
          //            This is some kind of error. Change to END BR
          //            And exit
          ws.wsMiscStorage.setReadLoopExit(true);
          // COB(1365): MOVE 'READ'                     TO ERROR-OPNAME
          ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
          // COB(1366): MOVE LIT-CARD-FILE              TO ERROR-FILE
          ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(ws.wsConstants.litCardFile);
          // COB(1367): MOVE WS-RESP-CD                 TO ERROR-RESP
          ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
          // COB(1368): MOVE WS-REAS-CD                 TO ERROR-RESP2
          ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
          // COB(1369): MOVE WS-FILE-ERROR-MESSAGE      TO WS-ERROR-MSG
          ws.wsMiscStorage.wsErrorMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
          // COB(1370): END-EVALUATE
      }
      // COB(1371): END-PERFORM
    }
    return Flow.Exit;
  }

  protected void _9100ReadBackwardsExit() {
    // COB(1375): EXEC CICS
    // COB(1375):      ENDBR FILE(LIT-CARD-FILE)
    // COB(1375): END-EXEC
    try {
      supernaut
          .endbr(ws.wsConstants.litCardFile.getValue()) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    // COB(1379): EXIT
    return;
  }

  protected void _9500FilterRecords() {
    // COB(1383): SET WS-DONOT-EXCLUDE-THIS-RECORD TO TRUE
    ws.wsMiscStorage.setWsDonotExcludeThisRecord(true);
    // COB(1385): IF FLG-ACCTFILTER-ISVALID
    if (ws.wsMiscStorage.flgAcctfilterIsvalid()) {
      // COB(1386): IF  CARD-ACCT-ID = CC-ACCT-ID
      if (ws.cvact02y.cardRecord.cardAcctId.equals(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId)) {
        // COB(1387): CONTINUE
        // do nothing
        // COB(1388): ELSE
      } else {
        // COB(1389): SET WS-EXCLUDE-THIS-RECORD  TO TRUE
        ws.wsMiscStorage.setWsExcludeThisRecord(true);
        // COB(1390): GO TO 9500-FILTER-RECORDS-EXIT
        return;
        // COB(1391): END-IF
      }
      // COB(1392): ELSE
    } else {
      // COB(1393): CONTINUE
      // do nothing
      // COB(1394): END-IF
    }
    // COB(1396): IF FLG-CARDFILTER-ISVALID
    if (ws.wsMiscStorage.flgCardfilterIsvalid()) {
      // COB(1397): IF  CARD-NUM = CC-CARD-NUM-N
      if (ws.cvact02y.cardRecord.cardNum.equals(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNumN)) {
        // COB(1398): CONTINUE
        // do nothing
        // COB(1399): ELSE
      } else {
        // COB(1400): SET WS-EXCLUDE-THIS-RECORD TO TRUE
        ws.wsMiscStorage.setWsExcludeThisRecord(true);
        // COB(1401): GO TO 9500-FILTER-RECORDS-EXIT
        return;
        // COB(1402): END-IF
      }
      // COB(1403): ELSE
    } else {
      // COB(1404): CONTINUE
      // do nothing
      // COB(1405): END-IF
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

  /***
   *****************************************************************
   * Plain text exit - Dont use in production                      *
   *****************************************************************/
  protected void sendPlainText() {
    // COB(1423): EXEC CICS SEND TEXT
    // COB(1423):           FROM(WS-ERROR-MSG)
    // COB(1423):           LENGTH(LENGTH OF WS-ERROR-MSG)
    // COB(1423):           ERASE
    // COB(1423):           FREEKB
    // COB(1423): END-EXEC
    try {
      supernaut
          .sendText() //
          .from(ws.wsMiscStorage.wsErrorMsg) //
          .length(ws.wsMiscStorage.wsErrorMsg.length()) //
          .freekb() //
          .erase() //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    // COB(1430): EXEC CICS RETURN
    // COB(1430): END-EXEC
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
    // COB(1442): EXEC CICS SEND TEXT
    // COB(1442):           FROM(WS-LONG-MSG)
    // COB(1442):           LENGTH(LENGTH OF WS-LONG-MSG)
    // COB(1442):           ERASE
    // COB(1442):           FREEKB
    // COB(1442): END-EXEC
    try {
      supernaut
          .sendText() //
          .from(ws.wsMiscStorage.wsLongMsg) //
          .length(ws.wsMiscStorage.wsLongMsg.length()) //
          .freekb() //
          .erase() //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    // COB(1449): EXEC CICS RETURN
    // COB(1449): END-EXEC
    try {
      supernaut.returnToCaller();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  protected enum HandleLabel {
    Not_Defined(0);

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
