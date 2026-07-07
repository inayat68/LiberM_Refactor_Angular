package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cocrdslc.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COCRDSLC extends AbstractOnlineProgram {

  protected enum Flow {
    Exit,
    // 0000-MAIN
    _0000Main,
    // COMMON-RETURN
    commonReturn
  }

  private Flow rcNext;

  @ProgramStorage final CocrdslcWorking ws = new CocrdslcWorking();

  // Linkage
  public static class CocrdslcLinkage extends NGroup {
    // COB:        01  DFHCOMMAREA.
    public Dfhcommarea dfhcommarea = new Dfhcommarea();

    public static class Dfhcommarea extends NGroup {

      // COB:          05  FILLER                                PIC X(1)
      // COB:              OCCURS 1 TO 32767 TIMES DEPENDING ON EIBCALEN.
      public NChar[] filler244 = AbstractNField.occurs(32767, new NChar(1));

      public NChar filler244AtIndex(int index) {
        return getOccursInstance(filler244, index);
      }
    }
  }

  final CocrdslcLinkage params = new CocrdslcLinkage();

  public COCRDSLC(Context supernaut) {
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
    // COB(250): EXEC CICS HANDLE ABEND
    // COB(250):           LABEL(ABEND-ROUTINE)
    // COB(250): END-EXEC
    supernaut
        .handleAbend() //
        .label(HandleLabel.abendRoutine.getValue()) //
        .exec();
    // COB(254): INITIALIZE CC-WORK-AREA
    // COB(254):            WS-MISC-STORAGE
    // COB(254):            WS-COMMAREA
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.initialize();
    ws.wsMiscStorage.initialize();
    ws.wsCommarea.initialize();
    // COB(260): MOVE LIT-THISTRANID       TO WS-TRANID
    // ****************************************************************
    //  Store our context
    // ****************************************************************
    ws.wsMiscStorage.wsCicsProcessngVars.wsTranid.setValue(ws.wsLiterals.litThistranid);
    // COB(264): SET WS-RETURN-MSG-OFF  TO TRUE
    // ****************************************************************
    //  Ensure error message is cleared                               *
    // ****************************************************************
    ws.wsMiscStorage.setWsReturnMsgOff(true);
    // COB(268): IF EIBCALEN IS EQUAL TO 0
    // COB(268):     OR (CDEMO-FROM-PROGRAM = LIT-MENUPGM
    // COB(268):     AND NOT CDEMO-PGM-REENTER)
    // ****************************************************************
    //  Store passed data if  any                *
    // ****************************************************************
    if (dfheiblk.getEibcalen() == 0
        || (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsLiterals.litMenupgm)
            && !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter())) {
      // COB(271): INITIALIZE CARDDEMO-COMMAREA
      // COB(271):            WS-THIS-PROGCOMMAREA
      ws.cocom01y.carddemoCommarea.initialize();
      ws.wsThisProgcommarea.initialize();
      // COB(273): ELSE
    } else {
      // COB(274): MOVE DFHCOMMAREA (1:LENGTH OF CARDDEMO-COMMAREA)  TO
      // COB(274):                   CARDDEMO-COMMAREA
      ws.cocom01y.carddemoCommarea.setValue(
          params.dfhcommarea, 0, ws.cocom01y.carddemoCommarea.length());
      // COB(276): MOVE DFHCOMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
      // COB(276):                  LENGTH OF WS-THIS-PROGCOMMAREA ) TO
      // COB(276):                   WS-THIS-PROGCOMMAREA
      ws.wsThisProgcommarea.setValue(
          params.dfhcommarea,
          ws.cocom01y.carddemoCommarea.length(),
          0,
          ws.wsThisProgcommarea.length());
      // COB(279): END-IF
    }
    // COB(284): PERFORM YYYY-STORE-PFKEY
    // COB(284):    THRU YYYY-STORE-PFKEY-EXIT
    // ****************************************************************
    //  Remap PFkeys as needed.
    //  Store the Mapped PF Key
    // ****************************************************************
    yyyyStorePfkey();
    // COB(291): SET PFK-INVALID TO TRUE
    // ****************************************************************
    //  Check the AID to see if its valid at this point               *
    //  F3 - Exit
    //  Enter show screen again
    // ****************************************************************
    ws.wsMiscStorage.setPfkInvalid(true);
    // COB(292): IF CCARD-AID-ENTER OR
    // COB(292):    CCARD-AID-PFK03
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidEnter()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()) {
      // COB(294): SET PFK-VALID TO TRUE
      ws.wsMiscStorage.setPfkValid(true);
      // COB(295): END-IF
    }
    // COB(297): IF PFK-INVALID
    if (ws.wsMiscStorage.pfkInvalid()) {
      // COB(298): SET CCARD-AID-ENTER TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidEnter(true);
      // COB(299): END-IF
    }
    // COB(304): EVALUATE TRUE
    // ****************************************************************
    //  Decide what to do based on inputs received
    // ****************************************************************
    // COB(305): WHEN CCARD-AID-PFK03
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()) {
      // COB(309): IF CDEMO-FROM-TRANID    EQUAL LOW-VALUES
      // COB(309): OR CDEMO-FROM-TRANID    EQUAL SPACES
      // *****************************************************************
      //             XCTL TO CALLING PROGRAM OR MAIN MENU
      // *****************************************************************
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isSpaces()) {
        // COB(311): MOVE LIT-MENUTRANID  TO CDEMO-TO-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToTranid.setValue(
            ws.wsLiterals.litMenutranid);
        // COB(312): ELSE
      } else {
        // COB(313): MOVE CDEMO-FROM-TRANID  TO CDEMO-TO-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToTranid.setValue(
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid);
        // COB(314): END-IF
      }
      // COB(316): IF CDEMO-FROM-PROGRAM   EQUAL LOW-VALUES
      // COB(316): OR CDEMO-FROM-PROGRAM   EQUAL SPACES
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isSpaces()) {
        // COB(318): MOVE LIT-MENUPGM     TO CDEMO-TO-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
            ws.wsLiterals.litMenupgm);
        // COB(319): ELSE
      } else {
        // COB(320): MOVE CDEMO-FROM-PROGRAM TO CDEMO-TO-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram);
        // COB(321): END-IF
      }
      // COB(323): MOVE LIT-THISTRANID     TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
          ws.wsLiterals.litThistranid);
      // COB(324): MOVE LIT-THISPGM        TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
          ws.wsLiterals.litThispgm);
      // COB(326): SET  CDEMO-USRTYP-USER  TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypUser(true);
      // COB(327): SET  CDEMO-PGM-ENTER    TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(328): MOVE LIT-THISMAPSET     TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(
          ws.wsLiterals.litThismapset);
      // COB(329): MOVE LIT-THISMAP        TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsLiterals.litThismap);
      // COB(331): EXEC CICS XCTL
      // COB(331):           PROGRAM (CDEMO-TO-PROGRAM)
      // COB(331):           COMMAREA(CARDDEMO-COMMAREA)
      // COB(331): END-EXEC
      try {
        supernaut
            .xctl(ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
            .commarea(ws.cocom01y.carddemoCommarea) //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(339): WHEN CDEMO-PGM-ENTER
      // COB(339):  AND CDEMO-FROM-PROGRAM  EQUAL LIT-CCLISTPGM
      // *****************************************************************
      //             COMING FROM CREDIT CARD LIST SCREEN
      //             SELECTION CRITERIA ALREADY VALIDATED
      // *****************************************************************
    } else if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
            ws.wsLiterals.litCclistpgm)) {
      // COB(341): SET INPUT-OK TO TRUE
      ws.wsMiscStorage.setInputOk(true);
      // COB(342): MOVE CDEMO-ACCT-ID       TO CC-ACCT-ID-N
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctIdN.setValue(
          ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId);
      // COB(343): MOVE CDEMO-CARD-NUM      TO CC-CARD-NUM-N
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNumN.setValue(
          ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum);
      // COB(344): PERFORM 9000-READ-DATA
      // COB(344):    THRU 9000-READ-DATA-EXIT
      _9000ReadData();
      // COB(346): PERFORM 1000-SEND-MAP
      // COB(346):   THRU 1000-SEND-MAP-EXIT
      _1000SendMap();
      // COB(348): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(349): WHEN CDEMO-PGM-ENTER
    } else if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()) {
      // COB(354): PERFORM 1000-SEND-MAP THRU
      // COB(354):         1000-SEND-MAP-EXIT
      // *****************************************************************
      //             COMING FROM SOME OTHER CONTEXT
      //             SELECTION CRITERIA TO BE GATHERED
      // *****************************************************************
      _1000SendMap();
      // COB(356): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(357): WHEN CDEMO-PGM-REENTER
    } else if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(358): PERFORM 2000-PROCESS-INPUTS
      // COB(358):    THRU 2000-PROCESS-INPUTS-EXIT
      _2000ProcessInputs();
      // COB(360): IF INPUT-ERROR
      if (ws.wsMiscStorage.inputError()) {
        // COB(361): PERFORM 1000-SEND-MAP
        // COB(361):    THRU 1000-SEND-MAP-EXIT
        _1000SendMap();
        // COB(363): GO TO COMMON-RETURN
        return Flow.commonReturn;
        // COB(364): ELSE
      } else {
        // COB(365): PERFORM 9000-READ-DATA
        // COB(365):    THRU 9000-READ-DATA-EXIT
        _9000ReadData();
        // COB(367): PERFORM 1000-SEND-MAP
        // COB(367):    THRU 1000-SEND-MAP-EXIT
        _1000SendMap();
        // COB(369): GO TO COMMON-RETURN
        return Flow.commonReturn;
        // COB(371): END-IF
      }
      // COB(373): WHEN OTHER
    } else {
      // COB(374): MOVE LIT-THISPGM    TO ABEND-CULPRIT
      ws.csmsg02y.abendData.abendCulprit.setValue(ws.wsLiterals.litThispgm);
      // COB(375): MOVE '0001'         TO ABEND-CODE
      ws.csmsg02y.abendData.abendCode.setString("0001");
      // COB(376): MOVE SPACES         TO ABEND-REASON
      ws.csmsg02y.abendData.abendReason.spaces();
      // COB(377): MOVE 'UNEXPECTED DATA SCENARIO'
      // COB(377):                     TO WS-RETURN-MSG
      ws.wsMiscStorage.wsReturnMsg.setString("UNEXPECTED DATA SCENARIO");
      // COB(379): PERFORM SEND-PLAIN-TEXT
      // COB(379):    THRU SEND-PLAIN-TEXT-EXIT
      sendPlainText();
      // COB(381): END-EVALUATE
    }
    // COB(386): IF INPUT-ERROR
    //  If we had an error setup error message that slipped through
    //  Display and return
    if (ws.wsMiscStorage.inputError()) {
      // COB(387): MOVE WS-RETURN-MSG  TO CCARD-ERROR-MSG
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
      // COB(388): PERFORM 1000-SEND-MAP
      // COB(388):    THRU 1000-SEND-MAP-EXIT
      _1000SendMap();
      // COB(390): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(391): END-IF
    }
    return Flow.Exit;
  }

  protected void commonReturn() {
    // COB(395): MOVE WS-RETURN-MSG     TO CCARD-ERROR-MSG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
    // COB(397): MOVE  CARDDEMO-COMMAREA    TO WS-COMMAREA
    ws.wsCommarea.setValue(ws.cocom01y.carddemoCommarea);
    // COB(398): MOVE  WS-THIS-PROGCOMMAREA TO
    // COB(398):        WS-COMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
    // COB(398):                     LENGTH OF WS-THIS-PROGCOMMAREA )
    ws.wsCommarea.setValue(
        ws.wsThisProgcommarea,
        0,
        ws.cocom01y.carddemoCommarea.length(),
        ws.wsThisProgcommarea.length());
    // COB(402): EXEC CICS RETURN
    // COB(402):      TRANSID (LIT-THISTRANID)
    // COB(402):      COMMAREA (WS-COMMAREA)
    // COB(402):      LENGTH(LENGTH OF WS-COMMAREA)
    // COB(402): END-EXEC
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

  protected void _1000SendMap() {
    // COB(413): PERFORM 1100-SCREEN-INIT
    // COB(413):    THRU 1100-SCREEN-INIT-EXIT
    _1100ScreenInit();
    // COB(415): PERFORM 1200-SETUP-SCREEN-VARS
    // COB(415):    THRU 1200-SETUP-SCREEN-VARS-EXIT
    _1200SetupScreenVars();
    // COB(417): PERFORM 1300-SETUP-SCREEN-ATTRS
    // COB(417):    THRU 1300-SETUP-SCREEN-ATTRS-EXIT
    _1300SetupScreenAttrs();
    // COB(419): PERFORM 1400-SEND-SCREEN
    // COB(419):    THRU 1400-SEND-SCREEN-EXIT
    _1400SendScreen();
  }

  protected void _1100ScreenInit() {
    // COB(428): MOVE LOW-VALUES TO CCRDSLAO
    ws.cocrdsl.ccrdslao.lowValues();
    // COB(430): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(432): MOVE CCDA-TITLE01           TO TITLE01O OF CCRDSLAO
    ws.cocrdsl.ccrdslao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(433): MOVE CCDA-TITLE02           TO TITLE02O OF CCRDSLAO
    ws.cocrdsl.ccrdslao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(434): MOVE LIT-THISTRANID         TO TRNNAMEO OF CCRDSLAO
    ws.cocrdsl.ccrdslao.trnnameo.setValue(ws.wsLiterals.litThistranid);
    // COB(435): MOVE LIT-THISPGM            TO PGMNAMEO OF CCRDSLAO
    ws.cocrdsl.ccrdslao.pgmnameo.setValue(ws.wsLiterals.litThispgm);
    // COB(437): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(439): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(440): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(441): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(443): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF CCRDSLAO
    ws.cocrdsl.ccrdslao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(445): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(446): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(447): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(449): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF CCRDSLAO
    ws.cocrdsl.ccrdslao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  protected void _1200SetupScreenVars() {
    // COB(459): IF EIBCALEN = 0
    //     INITIALIZE SEARCH CRITERIA
    if (dfheiblk.getEibcalen() == 0) {
      // COB(460): SET  WS-PROMPT-FOR-INPUT TO TRUE
      ws.wsMiscStorage.setWsPromptForInput(true);
      // COB(461): ELSE
    } else {
      // COB(462): IF CDEMO-ACCT-ID = 0
      if (ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.equals(0)) {
        // COB(463): MOVE LOW-VALUES   TO ACCTSIDO OF CCRDSLAO
        ws.cocrdsl.ccrdslao.acctsido.lowValues();
        // COB(464): ELSE
      } else {
        // COB(465): MOVE CC-ACCT-ID   TO ACCTSIDO OF CCRDSLAO
        ws.cocrdsl.ccrdslao.acctsido.setValue(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
        // COB(466): END-IF
      }
      // COB(468): IF CDEMO-CARD-NUM = 0
      if (ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.equals(0)) {
        // COB(469): MOVE LOW-VALUES   TO CARDSIDO OF CCRDSLAO
        ws.cocrdsl.ccrdslao.cardsido.lowValues();
        // COB(470): ELSE
      } else {
        // COB(471): MOVE CC-CARD-NUM  TO CARDSIDO OF CCRDSLAO
        ws.cocrdsl.ccrdslao.cardsido.setValue(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum);
        // COB(472): END-IF
      }
      // COB(474): IF FOUND-CARDS-FOR-ACCOUNT
      if (ws.wsMiscStorage.foundCardsForAccount()) {
        // COB(475): MOVE CARD-EMBOSSED-NAME
        // COB(475):                        TO CRDNAMEO OF CCRDSLAO
        ws.cocrdsl.ccrdslao.crdnameo.setValue(ws.cvact02y.cardRecord.cardEmbossedName);
        // COB(477): MOVE CARD-EXPIRAION-DATE
        // COB(477):                        TO CARD-EXPIRAION-DATE-X
        ws.wsMiscStorage.cicsOutputEditVars.cardExpiraionDateX.setValue(
            ws.cvact02y.cardRecord.cardExpiraionDate);
        // COB(480): MOVE CARD-EXPIRY-MONTH TO EXPMONO  OF CCRDSLAO
        ws.cocrdsl.ccrdslao.expmono.setValue(
            ws.wsMiscStorage.cicsOutputEditVars.filler85.cardExpiryMonth);
        // COB(482): MOVE CARD-EXPIRY-YEAR  TO EXPYEARO OF CCRDSLAO
        ws.cocrdsl.ccrdslao.expyearo.setValue(
            ws.wsMiscStorage.cicsOutputEditVars.filler85.cardExpiryYear);
        // COB(484): MOVE CARD-ACTIVE-STATUS TO CRDSTCDO OF CCRDSLAO
        ws.cocrdsl.ccrdslao.crdstcdo.setValue(ws.cvact02y.cardRecord.cardActiveStatus);
        // COB(485): END-IF
      }
      // COB(486): END-IF
    }
    // COB(490): IF WS-NO-INFO-MESSAGE
    //     SETUP MESSAGE
    if (ws.wsMiscStorage.wsNoInfoMessage()) {
      // COB(491): SET WS-PROMPT-FOR-INPUT TO TRUE
      ws.wsMiscStorage.setWsPromptForInput(true);
      // COB(492): END-IF
    }
    // COB(494): MOVE WS-RETURN-MSG          TO ERRMSGO OF CCRDSLAO
    ws.cocrdsl.ccrdslao.errmsgo.setValue(ws.wsMiscStorage.wsReturnMsg);
    // COB(496): MOVE WS-INFO-MSG            TO INFOMSGO OF CCRDSLAO
    ws.cocrdsl.ccrdslao.infomsgo.setValue(ws.wsMiscStorage.wsInfoMsg);
  }

  protected void _1300SetupScreenAttrs() {
    // COB(505): IF  CDEMO-LAST-MAPSET  EQUAL LIT-CCLISTMAPSET
    // COB(505): AND CDEMO-FROM-PROGRAM EQUAL LIT-CCLISTPGM
    //     PROTECT OR UNPROTECT BASED ON CONTEXT
    if (ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.equals(
            ws.wsLiterals.litCclistmapset)
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
            ws.wsLiterals.litCclistpgm)) {
      // COB(507): MOVE DFHBMPRF     TO ACCTSIDA OF CCRDSLAI
      ws.cocrdsl.ccrdslai.filler57.acctsida.setValue(Dfhbmsca.DFHBMPRF.getValue());
      // COB(508): MOVE DFHBMPRF     TO CARDSIDA OF CCRDSLAI
      ws.cocrdsl.ccrdslai.filler63.cardsida.setValue(Dfhbmsca.DFHBMPRF.getValue());
      // COB(509): ELSE
    } else {
      // COB(510): MOVE DFHBMFSE      TO ACCTSIDA OF CCRDSLAI
      ws.cocrdsl.ccrdslai.filler57.acctsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(511): MOVE DFHBMFSE      TO CARDSIDA OF CCRDSLAI
      ws.cocrdsl.ccrdslai.filler63.cardsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
      // COB(512): END-IF
    }
    // COB(515): EVALUATE TRUE
    //     POSITION CURSOR
    // COB(516): WHEN FLG-ACCTFILTER-NOT-OK
    if ((ws.wsMiscStorage.flgAcctfilterNotOk())
        // COB(517): WHEN FLG-ACCTFILTER-BLANK
        || ws.wsMiscStorage.flgAcctfilterBlank()) {
      // COB(518): MOVE -1             TO ACCTSIDL OF CCRDSLAI
      ws.cocrdsl.ccrdslai.acctsidl.setValue(-1);
      // COB(519): WHEN FLG-CARDFILTER-NOT-OK
    } else if (ws.wsMiscStorage.flgCardfilterNotOk()
        // COB(520): WHEN FLG-CARDFILTER-BLANK
        || ws.wsMiscStorage.flgCardfilterBlank()) {
      // COB(521): MOVE -1             TO CARDSIDL OF CCRDSLAI
      ws.cocrdsl.ccrdslai.cardsidl.setValue(-1);
      // COB(522): WHEN OTHER
    } else {
      // COB(523): MOVE -1             TO ACCTSIDL OF CCRDSLAI
      ws.cocrdsl.ccrdslai.acctsidl.setValue(-1);
      // COB(524): END-EVALUATE
    }
    // COB(527): IF  CDEMO-LAST-MAPSET   EQUAL LIT-CCLISTMAPSET
    // COB(527): AND CDEMO-FROM-PROGRAM  EQUAL LIT-CCLISTPGM
    //     SETUP COLOR
    if (ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.equals(
            ws.wsLiterals.litCclistmapset)
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
            ws.wsLiterals.litCclistpgm)) {
      // COB(529): MOVE DFHDFCOL     TO ACCTSIDC OF CCRDSLAO
      ws.cocrdsl.ccrdslao.acctsidc.setValue(Dfhbmsca.DFHDFCOL.getValue());
      // COB(530): MOVE DFHDFCOL     TO CARDSIDC OF CCRDSLAO
      ws.cocrdsl.ccrdslao.cardsidc.setValue(Dfhbmsca.DFHDFCOL.getValue());
      // COB(531): END-IF
    }
    // COB(533): IF FLG-ACCTFILTER-NOT-OK
    if (ws.wsMiscStorage.flgAcctfilterNotOk()) {
      // COB(534): MOVE DFHRED              TO ACCTSIDC OF CCRDSLAO
      ws.cocrdsl.ccrdslao.acctsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(535): END-IF
    }
    // COB(537): IF FLG-CARDFILTER-NOT-OK
    if (ws.wsMiscStorage.flgCardfilterNotOk()) {
      // COB(538): MOVE DFHRED              TO CARDSIDC OF CCRDSLAO
      ws.cocrdsl.ccrdslao.cardsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(539): END-IF
    }
    // COB(541): IF  FLG-ACCTFILTER-BLANK
    // COB(541): AND CDEMO-PGM-REENTER
    if (ws.wsMiscStorage.flgAcctfilterBlank()
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(543): MOVE '*'                TO ACCTSIDO OF CCRDSLAO
      ws.cocrdsl.ccrdslao.acctsido.setString("*");
      // COB(544): MOVE DFHRED             TO ACCTSIDC OF CCRDSLAO
      ws.cocrdsl.ccrdslao.acctsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(545): END-IF
    }
    // COB(547): IF  FLG-CARDFILTER-BLANK
    // COB(547): AND CDEMO-PGM-REENTER
    if (ws.wsMiscStorage.flgCardfilterBlank()
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(549): MOVE '*'                TO CARDSIDO OF CCRDSLAO
      ws.cocrdsl.ccrdslao.cardsido.setString("*");
      // COB(550): MOVE DFHRED             TO CARDSIDC OF CCRDSLAO
      ws.cocrdsl.ccrdslao.cardsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(551): END-IF
    }
    // COB(553): IF  WS-NO-INFO-MESSAGE
    if (ws.wsMiscStorage.wsNoInfoMessage()) {
      // COB(554): MOVE DFHBMDAR           TO INFOMSGC OF CCRDSLAO
      ws.cocrdsl.ccrdslao.infomsgc.setValue(Dfhbmsca.DFHBMDAR.getValue());
      // COB(555): ELSE
    } else {
      // COB(556): MOVE DFHNEUTR           TO INFOMSGC OF CCRDSLAO
      ws.cocrdsl.ccrdslao.infomsgc.setValue(Dfhbmsca.DFHNEUTR.getValue());
      // COB(557): END-IF
    }
  }

  protected void _1400SendScreen() {
    // COB(565): MOVE LIT-THISMAPSET         TO CCARD-NEXT-MAPSET
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsLiterals.litThismapset);
    // COB(566): MOVE LIT-THISMAP            TO CCARD-NEXT-MAP
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsLiterals.litThismap);
    // COB(567): SET  CDEMO-PGM-REENTER TO TRUE
    ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
    // COB(569): EXEC CICS SEND MAP(CCARD-NEXT-MAP)
    // COB(569):                MAPSET(CCARD-NEXT-MAPSET)
    // COB(569):                FROM(CCRDSLAO)
    // COB(569):                CURSOR
    // COB(569):                ERASE
    // COB(569):                FREEKB
    // COB(569):                RESP(WS-RESP-CD)
    // COB(569): END-EXEC
    supernaut
        .sendMap(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.getValue()) //
        .mapset(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.getValue()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .from(ws.cocrdsl.ccrdslao) //
        .freekb() //
        .erase() //
        .cursor() //
        .exec();
  }

  protected void _2000ProcessInputs() {
    // COB(583): PERFORM 2100-RECEIVE-MAP
    // COB(583):    THRU 2100-RECEIVE-MAP-EXIT
    _2100ReceiveMap();
    // COB(585): PERFORM 2200-EDIT-MAP-INPUTS
    // COB(585):    THRU 2200-EDIT-MAP-INPUTS-EXIT
    _2200EditMapInputs();
    // COB(587): MOVE WS-RETURN-MSG  TO CCARD-ERROR-MSG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
    // COB(588): MOVE LIT-THISPGM    TO CCARD-NEXT-PROG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.setValue(ws.wsLiterals.litThispgm);
    // COB(589): MOVE LIT-THISMAPSET TO CCARD-NEXT-MAPSET
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsLiterals.litThismapset);
    // COB(590): MOVE LIT-THISMAP    TO CCARD-NEXT-MAP
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsLiterals.litThismap);
  }

  protected void _2100ReceiveMap() {
    // COB(597): EXEC CICS RECEIVE MAP(LIT-THISMAP)
    // COB(597):           MAPSET(LIT-THISMAPSET)
    // COB(597):           INTO(CCRDSLAI)
    // COB(597):           RESP(WS-RESP-CD)
    // COB(597):           RESP2(WS-REAS-CD)
    // COB(597): END-EXEC
    supernaut
        .receiveMap(ws.wsLiterals.litThismap.getValue()) //
        .mapset(ws.wsLiterals.litThismapset.getValue()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cocrdsl.ccrdslai) //
        .exec();
  }

  protected void _2200EditMapInputs() {
    // COB(610): SET INPUT-OK                  TO TRUE
    ws.wsMiscStorage.setInputOk(true);
    // COB(611): SET FLG-CARDFILTER-ISVALID    TO TRUE
    ws.wsMiscStorage.setFlgCardfilterIsvalid(true);
    // COB(612): SET FLG-ACCTFILTER-ISVALID    TO TRUE
    ws.wsMiscStorage.setFlgAcctfilterIsvalid(true);
    // COB(615): IF  ACCTSIDI OF CCRDSLAI = '*'
    // COB(615): OR  ACCTSIDI OF CCRDSLAI = SPACES
    //     REPLACE * WITH LOW-VALUES
    if (ws.cocrdsl.ccrdslai.acctsidi.equals("*") || ws.cocrdsl.ccrdslai.acctsidi.isSpaces()) {
      // COB(617): MOVE LOW-VALUES           TO  CC-ACCT-ID
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.lowValues();
      // COB(618): ELSE
    } else {
      // COB(619): MOVE ACCTSIDI OF CCRDSLAI TO  CC-ACCT-ID
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.setValue(ws.cocrdsl.ccrdslai.acctsidi);
      // COB(620): END-IF
    }
    // COB(622): IF  CARDSIDI OF CCRDSLAI = '*'
    // COB(622): OR  CARDSIDI OF CCRDSLAI = SPACES
    if (ws.cocrdsl.ccrdslai.cardsidi.equals("*") || ws.cocrdsl.ccrdslai.cardsidi.isSpaces()) {
      // COB(624): MOVE LOW-VALUES           TO  CC-CARD-NUM
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum.lowValues();
      // COB(625): ELSE
    } else {
      // COB(626): MOVE CARDSIDI OF CCRDSLAI TO  CC-CARD-NUM
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum.setValue(ws.cocrdsl.ccrdslai.cardsidi);
      // COB(627): END-IF
    }
    // COB(630): PERFORM 2210-EDIT-ACCOUNT
    // COB(630):    THRU 2210-EDIT-ACCOUNT-EXIT
    //     INDIVIDUAL FIELD EDITS
    _2210EditAccount();
    // COB(633): PERFORM 2220-EDIT-CARD
    // COB(633):    THRU 2220-EDIT-CARD-EXIT
    _2220EditCard();
    // COB(637): IF  FLG-ACCTFILTER-BLANK
    // COB(637): AND FLG-CARDFILTER-BLANK
    //     CROSS FIELD EDITS
    if (ws.wsMiscStorage.flgAcctfilterBlank() && ws.wsMiscStorage.flgCardfilterBlank()) {
      // COB(639): SET NO-SEARCH-CRITERIA-RECEIVED TO TRUE
      ws.wsMiscStorage.setNoSearchCriteriaReceived(true);
      // COB(640): END-IF
    }
  }

  protected void _2210EditAccount() {
    // COB(648): SET FLG-ACCTFILTER-NOT-OK TO TRUE
    ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
    // COB(651): IF CC-ACCT-ID   EQUAL LOW-VALUES
    // COB(651): OR CC-ACCT-ID   EQUAL SPACES
    // COB(651): OR CC-ACCT-ID-N EQUAL ZEROS
    //     Not supplied
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isLowValues()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isSpaces()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctIdN.equals(0)) {
      // COB(654): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(655): SET FLG-ACCTFILTER-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterBlank(true);
      // COB(656): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(657): SET WS-PROMPT-FOR-ACCT TO TRUE
        ws.wsMiscStorage.setWsPromptForAcct(true);
        // COB(658): END-IF
      }
      // COB(659): MOVE ZEROES       TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
      // COB(660): GO TO  2210-EDIT-ACCOUNT-EXIT
      return;
      // COB(661): END-IF
    }
    // COB(665): IF CC-ACCT-ID  IS NOT NUMERIC
    //
    //     Not numeric
    //     Not 11 characters
    if (!ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isNumeric()) {
      // COB(666): SET INPUT-ERROR TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(667): SET FLG-ACCTFILTER-NOT-OK TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
      // COB(668): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(669): MOVE
        // COB(669):               'ACCOUNT FILTER,IF SUPPLIED MUST BE A 11 DIGIT NUMBER'
        // COB(669):               TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setString(
            "ACCOUNT FILTER,IF SUPPLIED MUST BE A 11 DIGIT NUMBER");
        // COB(672): END-IF
      }
      // COB(673): MOVE ZERO       TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
      // COB(674): GO TO 2210-EDIT-ACCOUNT-EXIT
      return;
      // COB(675): ELSE
    } else {
      // COB(676): MOVE CC-ACCT-ID TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.setValue(
          ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
      // COB(677): SET FLG-ACCTFILTER-ISVALID TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterIsvalid(true);
      // COB(678): END-IF
    }
  }

  protected void _2220EditCard() {
    // COB(688): SET FLG-CARDFILTER-NOT-OK TO TRUE
    //     Not numeric
    //     Not 16 characters
    ws.wsMiscStorage.setFlgCardfilterNotOk(true);
    // COB(691): IF CC-CARD-NUM   EQUAL LOW-VALUES
    // COB(691): OR CC-CARD-NUM   EQUAL SPACES
    // COB(691): OR CC-CARD-NUM-N EQUAL ZEROS
    //     Not supplied
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum.isLowValues()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum.isSpaces()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNumN.equals(0)) {
      // COB(694): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(695): SET FLG-CARDFILTER-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgCardfilterBlank(true);
      // COB(696): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(697): SET WS-PROMPT-FOR-CARD TO TRUE
        ws.wsMiscStorage.setWsPromptForCard(true);
        // COB(698): END-IF
      }
      // COB(700): MOVE ZEROES       TO CDEMO-CARD-NUM
      ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.zeros();
      // COB(701): GO TO  2220-EDIT-CARD-EXIT
      return;
      // COB(702): END-IF
    }
    // COB(706): IF CC-CARD-NUM  IS NOT NUMERIC
    //
    //     Not numeric
    //     Not 16 characters
    if (!ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum.isNumeric()) {
      // COB(707): SET INPUT-ERROR TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(708): SET FLG-CARDFILTER-NOT-OK TO TRUE
      ws.wsMiscStorage.setFlgCardfilterNotOk(true);
      // COB(709): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(710): MOVE
        // COB(710):               'CARD ID FILTER,IF SUPPLIED MUST BE A 16 DIGIT NUMBER'
        // COB(710):              TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setString(
            "CARD ID FILTER,IF SUPPLIED MUST BE A 16 DIGIT NUMBER");
        // COB(713): END-IF
      }
      // COB(714): MOVE ZERO       TO CDEMO-CARD-NUM
      ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.zeros();
      // COB(715): GO TO 2220-EDIT-CARD-EXIT
      return;
      // COB(716): ELSE
    } else {
      // COB(717): MOVE CC-CARD-NUM-N TO CDEMO-CARD-NUM
      ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.setValue(
          ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNumN);
      // COB(718): SET FLG-CARDFILTER-ISVALID TO TRUE
      ws.wsMiscStorage.setFlgCardfilterIsvalid(true);
      // COB(719): END-IF
    }
  }

  protected void _9000ReadData() {
    // COB(728): PERFORM 9100-GETCARD-BYACCTCARD
    // COB(728):    THRU 9100-GETCARD-BYACCTCARD-EXIT
    _9100GetcardByacctcard();
  }

  protected void _9100GetcardByacctcard() {
    // COB(740): MOVE CC-CARD-NUM       TO WS-CARD-RID-CARDNUM
    //     Read the Card file
    //
    //     MOVE CC-ACCT-ID-N      TO WS-CARD-RID-ACCT-ID
    ws.wsMiscStorage.wsCardRid.wsCardRidCardnum.setValue(
        ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccCardNum);
    // COB(742): EXEC CICS READ
    // COB(742):      FILE      (LIT-CARDFILENAME)
    // COB(742):      RIDFLD    (WS-CARD-RID-CARDNUM)
    // COB(742):      KEYLENGTH (LENGTH OF WS-CARD-RID-CARDNUM)
    // COB(742):      INTO      (CARD-RECORD)
    // COB(742):      LENGTH    (LENGTH OF CARD-RECORD)
    // COB(742):      RESP      (WS-RESP-CD)
    // COB(742):      RESP2     (WS-REAS-CD)
    // COB(742): END-EXEC
    supernaut
        .read(ws.wsLiterals.litCardfilename.getValue()) //
        .length(ws.cvact02y.cardRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvact02y.cardRecord) //
        .ridfld(ws.wsMiscStorage.wsCardRid.wsCardRidCardnum) //
        .keylength(ws.wsMiscStorage.wsCardRid.wsCardRidCardnum.length()) //
        .exec();
    // COB(752): EVALUATE WS-RESP-CD
    switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
        // COB(753): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(754): SET FOUND-CARDS-FOR-ACCOUNT TO TRUE
        ws.wsMiscStorage.setFoundCardsForAccount(true);
        // COB(755): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(756): SET INPUT-ERROR                    TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(757): SET FLG-ACCTFILTER-NOT-OK          TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(758): SET FLG-CARDFILTER-NOT-OK          TO TRUE
        ws.wsMiscStorage.setFlgCardfilterNotOk(true);
        // COB(759): IF  WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(760): SET DID-NOT-FIND-ACCTCARD-COMBO TO TRUE
          ws.wsMiscStorage.setDidNotFindAcctcardCombo(true);
          // COB(761): END-IF
        }
        // COB(762): WHEN OTHER
        break;
      default:
        // COB(763): SET INPUT-ERROR                    TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(764): IF  WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(765): SET FLG-ACCTFILTER-NOT-OK      TO TRUE
          ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
          // COB(766): END-IF
        }
        // COB(767): MOVE 'READ'                        TO ERROR-OPNAME
        ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
        // COB(768): MOVE LIT-CARDFILENAME                TO ERROR-FILE
        ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(ws.wsLiterals.litCardfilename);
        // COB(769): MOVE WS-RESP-CD                    TO ERROR-RESP
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(770): MOVE WS-REAS-CD                    TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(771): MOVE WS-FILE-ERROR-MESSAGE         TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
        // COB(772): END-EVALUATE
    }
  }

  protected void _9150GetcardByacct() {
    // COB(783): EXEC CICS READ
    // COB(783):      FILE      (LIT-CARDFILENAME-ACCT-PATH)
    // COB(783):      RIDFLD    (WS-CARD-RID-ACCT-ID)
    // COB(783):      KEYLENGTH (LENGTH OF WS-CARD-RID-ACCT-ID)
    // COB(783):      INTO      (CARD-RECORD)
    // COB(783):      LENGTH    (LENGTH OF CARD-RECORD)
    // COB(783):      RESP      (WS-RESP-CD)
    // COB(783):      RESP2     (WS-REAS-CD)
    // COB(783): END-EXEC
    //     Read the Card file. Access via alternate index ACCTID
    //
    supernaut
        .read(ws.wsLiterals.litCardfilenameAcctPath.getValue()) //
        .length(ws.cvact02y.cardRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvact02y.cardRecord) //
        .ridfld(ws.wsMiscStorage.wsCardRid.wsCardRidAcctId) //
        .keylength(ws.wsMiscStorage.wsCardRid.wsCardRidAcctId.length()) //
        .exec();
    // COB(793): EVALUATE WS-RESP-CD
    switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
        // COB(794): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(795): SET FOUND-CARDS-FOR-ACCOUNT TO TRUE
        ws.wsMiscStorage.setFoundCardsForAccount(true);
        // COB(796): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(797): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(798): SET FLG-ACCTFILTER-NOT-OK                TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(799): SET DID-NOT-FIND-ACCT-IN-CARDXREF TO TRUE
        ws.wsMiscStorage.setDidNotFindAcctInCardxref(true);
        // COB(800): WHEN OTHER
        break;
      default:
        // COB(801): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(802): SET FLG-ACCTFILTER-NOT-OK                TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(803): MOVE 'READ'                     TO ERROR-OPNAME
        ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
        // COB(804): MOVE LIT-CARDFILENAME-ACCT-PATH TO ERROR-FILE
        ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(
            ws.wsLiterals.litCardfilenameAcctPath);
        // COB(805): MOVE WS-RESP-CD                 TO ERROR-RESP
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(806): MOVE WS-REAS-CD                 TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(807): MOVE WS-FILE-ERROR-MESSAGE      TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
        // COB(808): END-EVALUATE
    }
  }

  /*****************************************************************
   * Display Long text and exit                                    *
   * This is primarily for debugging and should not be used in     *
   * regular course                                                *
   *****************************************************************/
  protected void sendLongText() {
    // COB(821): EXEC CICS SEND TEXT
    // COB(821):           FROM(WS-LONG-MSG)
    // COB(821):           LENGTH(LENGTH OF WS-LONG-MSG)
    // COB(821):           ERASE
    // COB(821):           FREEKB
    // COB(821): END-EXEC
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
    // COB(828): EXEC CICS RETURN
    // COB(828): END-EXEC
    try {
      supernaut.returnToCaller();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /*****************************************************************
   * Plain text exit - Dont use in production                      *
   *****************************************************************/
  protected void sendPlainText() {
    // COB(839): EXEC CICS SEND TEXT
    // COB(839):           FROM(WS-RETURN-MSG)
    // COB(839):           LENGTH(LENGTH OF WS-RETURN-MSG)
    // COB(839):           ERASE
    // COB(839):           FREEKB
    // COB(839): END-EXEC
    try {
      supernaut
          .sendText() //
          .from(ws.wsMiscStorage.wsReturnMsg) //
          .length(ws.wsMiscStorage.wsReturnMsg.length()) //
          .freekb() //
          .erase() //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    // COB(846): EXEC CICS RETURN
    // COB(846): END-EXEC
    try {
      supernaut.returnToCaller();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
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
    // COB(859): IF ABEND-MSG EQUAL LOW-VALUES
    if (ws.csmsg02y.abendData.abendMsg.isLowValues()) {
      // COB(860): MOVE 'UNEXPECTED ABEND OCCURRED.' TO ABEND-MSG
      ws.csmsg02y.abendData.abendMsg.setString("UNEXPECTED ABEND OCCURRED.");
      // COB(861): END-IF
    }
    // COB(863): MOVE LIT-THISPGM       TO ABEND-CULPRIT
    ws.csmsg02y.abendData.abendCulprit.setValue(ws.wsLiterals.litThispgm);
    // COB(865): EXEC CICS SEND
    // COB(865):                  FROM (ABEND-DATA)
    // COB(865):                  LENGTH(LENGTH OF ABEND-DATA)
    // COB(865):                  NOHANDLE
    // COB(865): END-EXEC
    supernaut
        .send() //
        .length(ws.csmsg02y.abendData.length()) //
        .nohandle() //
        .from(ws.csmsg02y.abendData) //
        .exec();
    // COB(871): EXEC CICS HANDLE ABEND
    // COB(871):      CANCEL
    // COB(871): END-EXEC
    supernaut
        .handleAbend() //
        .cancel() //
        .exec();
    // COB(875): EXEC CICS ABEND
    // COB(875):      ABCODE('9999')
    // COB(875): END-EXEC
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
