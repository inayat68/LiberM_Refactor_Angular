package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.coactvwc.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COACTVWC extends AbstractOnlineProgram {

  protected enum Flow {
    Exit,
    // 0000-MAIN
    _0000Main,
    // COMMON-RETURN
    commonReturn
  }

  private Flow rcNext;

  @ProgramStorage final CoactvwcWorking ws = new CoactvwcWorking();

  // Linkage
  public static class CoactvwcLinkage extends NGroup {
    // COB:        01  DFHCOMMAREA.
    public Dfhcommarea dfhcommarea = new Dfhcommarea();

    public static class Dfhcommarea extends NGroup {

      // COB:          05  FILLER                                PIC X(1)
      // COB:              OCCURS 1 TO 32767 TIMES DEPENDING ON EIBCALEN.
      public NChar[] filler258 = AbstractNField.occurs(32767, new NChar(1));

      public NChar filler258AtIndex(int index) {
        return getOccursInstance(filler258, index);
      }
    }
  }

  final CoactvwcLinkage params = new CoactvwcLinkage();

  public COACTVWC(Context supernaut) {
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
    // COB(264): EXEC CICS HANDLE ABEND
    // COB(264):           LABEL(ABEND-ROUTINE)
    // COB(264): END-EXEC
    supernaut
        .handleAbend() //
        .label(HandleLabel.abendRoutine.getValue()) //
        .exec();
    // COB(268): INITIALIZE CC-WORK-AREA
    // COB(268):            WS-MISC-STORAGE
    // COB(268):            WS-COMMAREA
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.initialize();
    ws.wsMiscStorage.initialize();
    ws.wsCommarea.initialize();
    // COB(274): MOVE LIT-THISTRANID       TO WS-TRANID
    // ****************************************************************
    //  Store our context
    // ****************************************************************
    ws.wsMiscStorage.wsCicsProcessngVars.wsTranid.setValue(ws.wsLiterals.litThistranid);
    // COB(278): SET WS-RETURN-MSG-OFF  TO TRUE
    // ****************************************************************
    //  Ensure error message is cleared                               *
    // ****************************************************************
    ws.wsMiscStorage.setWsReturnMsgOff(true);
    // COB(282): IF EIBCALEN IS EQUAL TO 0
    // COB(282):     OR (CDEMO-FROM-PROGRAM = LIT-MENUPGM
    // COB(282):     AND NOT CDEMO-PGM-REENTER)
    // ****************************************************************
    //  Store passed data if  any                *
    // ****************************************************************
    if (dfheiblk.getEibcalen() == 0
        || (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.equals(
                ws.wsLiterals.litMenupgm)
            && !ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter())) {
      // COB(285): INITIALIZE CARDDEMO-COMMAREA
      // COB(285):            WS-THIS-PROGCOMMAREA
      ws.cocom01y.carddemoCommarea.initialize();
      ws.wsThisProgcommarea.initialize();
      // COB(287): ELSE
    } else {
      // COB(288): MOVE DFHCOMMAREA (1:LENGTH OF CARDDEMO-COMMAREA)  TO
      // COB(288):                   CARDDEMO-COMMAREA
      ws.cocom01y.carddemoCommarea.setValue(
          params.dfhcommarea, 0, ws.cocom01y.carddemoCommarea.length());
      // COB(290): MOVE DFHCOMMAREA(LENGTH OF CARDDEMO-COMMAREA + 1:
      // COB(290):                  LENGTH OF WS-THIS-PROGCOMMAREA ) TO
      // COB(290):                   WS-THIS-PROGCOMMAREA
      ws.wsThisProgcommarea.setValue(
          params.dfhcommarea,
          ws.cocom01y.carddemoCommarea.length(),
          0,
          ws.wsThisProgcommarea.length());
      // COB(293): END-IF
    }
    // COB(299): PERFORM YYYY-STORE-PFKEY
    // COB(299):    THRU YYYY-STORE-PFKEY-EXIT
    // ****************************************************************
    //  Remap PFkeys as needed.
    //  Store the Mapped PF Key
    // ****************************************************************
    yyyyStorePfkey();
    // COB(306): SET PFK-INVALID TO TRUE
    // ****************************************************************
    //  Check the AID to see if its valid at this point               *
    //  F3 - Exit
    //  Enter show screen again
    // ****************************************************************
    ws.wsMiscStorage.setPfkInvalid(true);
    // COB(307): IF CCARD-AID-ENTER OR
    // COB(307):    CCARD-AID-PFK03
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidEnter()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()) {
      // COB(309): SET PFK-VALID TO TRUE
      ws.wsMiscStorage.setPfkValid(true);
      // COB(310): END-IF
    }
    // COB(312): IF PFK-INVALID
    if (ws.wsMiscStorage.pfkInvalid()) {
      // COB(313): SET CCARD-AID-ENTER TO TRUE
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.setCcardAidEnter(true);
      // COB(314): END-IF
    }
    // COB(323): EVALUATE TRUE
    // ****************************************************************
    //  Decide what to do based on inputs received
    // ****************************************************************
    // ****************************************************************
    // ****************************************************************
    //  Decide what to do based on inputs received
    // ****************************************************************
    // COB(324): WHEN CCARD-AID-PFK03
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardAidPfk03()) {
      // COB(328): IF CDEMO-FROM-TRANID    EQUAL LOW-VALUES
      // COB(328): OR CDEMO-FROM-TRANID    EQUAL SPACES
      // *****************************************************************
      //             XCTL TO CALLING PROGRAM OR MAIN MENU
      // *****************************************************************
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.isSpaces()) {
        // COB(330): MOVE LIT-MENUTRANID  TO CDEMO-TO-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToTranid.setValue(
            ws.wsLiterals.litMenutranid);
        // COB(331): ELSE
      } else {
        // COB(332): MOVE CDEMO-FROM-TRANID  TO CDEMO-TO-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToTranid.setValue(
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid);
        // COB(333): END-IF
      }
      // COB(334): IF CDEMO-FROM-PROGRAM   EQUAL LOW-VALUES
      // COB(334): OR CDEMO-FROM-PROGRAM   EQUAL SPACES
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isLowValues()
          || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isSpaces()) {
        // COB(336): MOVE LIT-MENUPGM     TO CDEMO-TO-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
            ws.wsLiterals.litMenupgm);
        // COB(337): ELSE
      } else {
        // COB(338): MOVE CDEMO-FROM-PROGRAM TO CDEMO-TO-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram);
        // COB(339): END-IF
      }
      // COB(341): MOVE LIT-THISTRANID     TO CDEMO-FROM-TRANID
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
          ws.wsLiterals.litThistranid);
      // COB(342): MOVE LIT-THISPGM        TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
          ws.wsLiterals.litThispgm);
      // COB(344): SET  CDEMO-USRTYP-USER  TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoUsrtypUser(true);
      // COB(345): SET  CDEMO-PGM-ENTER    TO TRUE
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
      // COB(346): MOVE LIT-THISMAPSET     TO CDEMO-LAST-MAPSET
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMapset.setValue(
          ws.wsLiterals.litThismapset);
      // COB(347): MOVE LIT-THISMAP        TO CDEMO-LAST-MAP
      ws.cocom01y.carddemoCommarea.cdemoMoreInfo.cdemoLastMap.setValue(ws.wsLiterals.litThismap);
      // COB(349): EXEC CICS XCTL
      // COB(349):           PROGRAM (CDEMO-TO-PROGRAM)
      // COB(349):           COMMAREA(CARDDEMO-COMMAREA)
      // COB(349): END-EXEC
      try {
        supernaut
            .xctl(ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
            .commarea(ws.cocom01y.carddemoCommarea) //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(353): WHEN CDEMO-PGM-ENTER
    } else if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmEnter()) {
      // COB(358): PERFORM 1000-SEND-MAP THRU
      // COB(358):         1000-SEND-MAP-EXIT
      // *****************************************************************
      //             COMING FROM SOME OTHER CONTEXT
      //             SELECTION CRITERIA TO BE GATHERED
      // *****************************************************************
      _1000SendMap();
      // COB(360): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(361): WHEN CDEMO-PGM-REENTER
    } else if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(362): PERFORM 2000-PROCESS-INPUTS
      // COB(362):    THRU 2000-PROCESS-INPUTS-EXIT
      _2000ProcessInputs();
      // COB(364): IF INPUT-ERROR
      if (ws.wsMiscStorage.inputError()) {
        // COB(365): PERFORM 1000-SEND-MAP
        // COB(365):    THRU 1000-SEND-MAP-EXIT
        _1000SendMap();
        // COB(367): GO TO COMMON-RETURN
        return Flow.commonReturn;
        // COB(368): ELSE
      } else {
        // COB(369): PERFORM 9000-READ-ACCT
        // COB(369):    THRU 9000-READ-ACCT-EXIT
        _9000ReadAcct();
        // COB(371): PERFORM 1000-SEND-MAP
        // COB(371):    THRU 1000-SEND-MAP-EXIT
        _1000SendMap();
        // COB(373): GO TO COMMON-RETURN
        return Flow.commonReturn;
        // COB(374): END-IF
      }
      // COB(375): WHEN OTHER
    } else {
      // COB(376): MOVE LIT-THISPGM    TO ABEND-CULPRIT
      ws.csmsg02y.abendData.abendCulprit.setValue(ws.wsLiterals.litThispgm);
      // COB(377): MOVE '0001'         TO ABEND-CODE
      ws.csmsg02y.abendData.abendCode.setString("0001");
      // COB(378): MOVE SPACES         TO ABEND-REASON
      ws.csmsg02y.abendData.abendReason.spaces();
      // COB(379): MOVE 'UNEXPECTED DATA SCENARIO'
      // COB(379):                     TO WS-RETURN-MSG
      ws.wsMiscStorage.wsReturnMsg.setString("UNEXPECTED DATA SCENARIO");
      // COB(381): PERFORM SEND-PLAIN-TEXT
      // COB(381):    THRU SEND-PLAIN-TEXT-EXIT
      sendPlainText();
      // COB(383): END-EVALUATE
    }
    // COB(387): IF INPUT-ERROR
    //  If we had an error setup error message that slipped through
    //  Display and return
    if (ws.wsMiscStorage.inputError()) {
      // COB(388): MOVE WS-RETURN-MSG  TO CCARD-ERROR-MSG
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
      // COB(389): PERFORM 1000-SEND-MAP
      // COB(389):    THRU 1000-SEND-MAP-EXIT
      _1000SendMap();
      // COB(391): GO TO COMMON-RETURN
      return Flow.commonReturn;
      // COB(392): END-IF
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
    // COB(417): PERFORM 1100-SCREEN-INIT
    // COB(417):    THRU 1100-SCREEN-INIT-EXIT
    _1100ScreenInit();
    // COB(419): PERFORM 1200-SETUP-SCREEN-VARS
    // COB(419):    THRU 1200-SETUP-SCREEN-VARS-EXIT
    _1200SetupScreenVars();
    // COB(421): PERFORM 1300-SETUP-SCREEN-ATTRS
    // COB(421):    THRU 1300-SETUP-SCREEN-ATTRS-EXIT
    _1300SetupScreenAttrs();
    // COB(423): PERFORM 1400-SEND-SCREEN
    // COB(423):    THRU 1400-SEND-SCREEN-EXIT
    _1400SendScreen();
  }

  protected void _1100ScreenInit() {
    // COB(432): MOVE LOW-VALUES             TO CACTVWAO
    ws.coactvw.cactvwao.lowValues();
    // COB(434): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(436): MOVE CCDA-TITLE01           TO TITLE01O OF CACTVWAO
    ws.coactvw.cactvwao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(437): MOVE CCDA-TITLE02           TO TITLE02O OF CACTVWAO
    ws.coactvw.cactvwao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(438): MOVE LIT-THISTRANID         TO TRNNAMEO OF CACTVWAO
    ws.coactvw.cactvwao.trnnameo.setValue(ws.wsLiterals.litThistranid);
    // COB(439): MOVE LIT-THISPGM            TO PGMNAMEO OF CACTVWAO
    ws.coactvw.cactvwao.pgmnameo.setValue(ws.wsLiterals.litThispgm);
    // COB(441): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(443): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(444): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(445): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(447): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF CACTVWAO
    ws.coactvw.cactvwao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(449): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(450): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(451): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(453): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF CACTVWAO
    ws.coactvw.cactvwao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  protected void _1200SetupScreenVars() {
    // COB(462): IF EIBCALEN = 0
    //     INITIALIZE SEARCH CRITERIA
    if (dfheiblk.getEibcalen() == 0) {
      // COB(463): SET  WS-PROMPT-FOR-INPUT TO TRUE
      ws.wsMiscStorage.setWsPromptForInput(true);
      // COB(464): ELSE
    } else {
      // COB(465): IF FLG-ACCTFILTER-BLANK
      if (ws.wsMiscStorage.flgAcctfilterBlank()) {
        // COB(466): MOVE LOW-VALUES   TO ACCTSIDO OF CACTVWAO
        ws.coactvw.cactvwao.acctsido.lowValues();
        // COB(467): ELSE
      } else {
        // COB(468): MOVE CC-ACCT-ID   TO ACCTSIDO OF CACTVWAO
        ws.coactvw.cactvwao.acctsido.setValue(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
        // COB(469): END-IF
      }
      // COB(471): IF FOUND-ACCT-IN-MASTER
      // COB(471): OR FOUND-CUST-IN-MASTER
      if (ws.wsMiscStorage.wsFileReadFlags.foundAcctInMaster()
          || ws.wsMiscStorage.wsFileReadFlags.foundCustInMaster()) {
        // COB(473): MOVE ACCT-ACTIVE-STATUS  TO ACSTTUSO OF CACTVWAO
        ws.coactvw.cactvwao.acsttuso.setValue(ws.cvact01y.accountRecord.acctActiveStatus);
        // COB(475): MOVE ACCT-CURR-BAL       TO ACURBALO OF CACTVWAO
        ws.coactvw.cactvwao.acurbalo.setValue(ws.cvact01y.accountRecord.acctCurrBal);
        // COB(477): MOVE ACCT-CREDIT-LIMIT   TO ACRDLIMO OF CACTVWAO
        ws.coactvw.cactvwao.acrdlimo.setValue(ws.cvact01y.accountRecord.acctCreditLimit);
        // COB(479): MOVE ACCT-CASH-CREDIT-LIMIT
        // COB(479):                          TO ACSHLIMO OF CACTVWAO
        ws.coactvw.cactvwao.acshlimo.setValue(ws.cvact01y.accountRecord.acctCashCreditLimit);
        // COB(482): MOVE ACCT-CURR-CYC-CREDIT
        // COB(482):                          TO ACRCYCRO OF CACTVWAO
        ws.coactvw.cactvwao.acrcycro.setValue(ws.cvact01y.accountRecord.acctCurrCycCredit);
        // COB(485): MOVE ACCT-CURR-CYC-DEBIT TO ACRCYDBO OF CACTVWAO
        ws.coactvw.cactvwao.acrcydbo.setValue(ws.cvact01y.accountRecord.acctCurrCycDebit);
        // COB(487): MOVE ACCT-OPEN-DATE      TO ADTOPENO OF CACTVWAO
        ws.coactvw.cactvwao.adtopeno.setValue(ws.cvact01y.accountRecord.acctOpenDate);
        // COB(488): MOVE ACCT-EXPIRAION-DATE TO AEXPDTO  OF CACTVWAO
        ws.coactvw.cactvwao.aexpdto.setValue(ws.cvact01y.accountRecord.acctExpiraionDate);
        // COB(489): MOVE ACCT-REISSUE-DATE   TO AREISDTO OF CACTVWAO
        ws.coactvw.cactvwao.areisdto.setValue(ws.cvact01y.accountRecord.acctReissueDate);
        // COB(490): MOVE ACCT-GROUP-ID       TO AADDGRPO OF CACTVWAO
        ws.coactvw.cactvwao.aaddgrpo.setValue(ws.cvact01y.accountRecord.acctGroupId);
        // COB(491): END-IF
      }
      // COB(493): IF FOUND-CUST-IN-MASTER
      if (ws.wsMiscStorage.wsFileReadFlags.foundCustInMaster()) {
        // COB(494): MOVE CUST-ID              TO ACSTNUMO OF CACTVWAO
        ws.coactvw.cactvwao.acstnumo.setValue(ws.cvcus01y.customerRecord.custId);
        // COB(496): STRING
        // COB(496):     CUST-SSN(1:3)
        // COB(496):     '-'
        // COB(496):     CUST-SSN(4:2)
        // COB(496):     '-'
        // COB(496):     CUST-SSN(6:4)
        // COB(496):     DELIMITED BY SIZE
        // COB(496):     INTO ACSTSSNO OF CACTVWAO
        // COB(496): END-STRING
        //          MOVE CUST-SSN             TO ACSTSSNO OF CACTVWAO
        ws.coactvw.cactvwao.acstssno.concat(
            ws.cvcus01y.customerRecord.custSsn.getString(0, 3),
            "-",
            ws.cvcus01y.customerRecord.custSsn.getString(3, 2),
            "-",
            ws.cvcus01y.customerRecord.custSsn.getString(5, 4));
        // COB(504): END-STRING
        // COB(505): MOVE CUST-FICO-CREDIT-SCORE
        // COB(505):                           TO ACSTFCOO OF CACTVWAO
        ws.coactvw.cactvwao.acstfcoo.setValue(ws.cvcus01y.customerRecord.custFicoCreditScore);
        // COB(507): MOVE CUST-DOB-YYYY-MM-DD  TO ACSTDOBO OF CACTVWAO
        ws.coactvw.cactvwao.acstdobo.setValue(ws.cvcus01y.customerRecord.custDobYyyyMmDd);
        // COB(508): MOVE CUST-FIRST-NAME      TO ACSFNAMO OF CACTVWAO
        ws.coactvw.cactvwao.acsfnamo.setValue(ws.cvcus01y.customerRecord.custFirstName);
        // COB(509): MOVE CUST-MIDDLE-NAME     TO ACSMNAMO OF CACTVWAO
        ws.coactvw.cactvwao.acsmnamo.setValue(ws.cvcus01y.customerRecord.custMiddleName);
        // COB(510): MOVE CUST-LAST-NAME       TO ACSLNAMO OF CACTVWAO
        ws.coactvw.cactvwao.acslnamo.setValue(ws.cvcus01y.customerRecord.custLastName);
        // COB(511): MOVE CUST-ADDR-LINE-1     TO ACSADL1O OF CACTVWAO
        ws.coactvw.cactvwao.acsadl1o.setValue(ws.cvcus01y.customerRecord.custAddrLine1);
        // COB(512): MOVE CUST-ADDR-LINE-2     TO ACSADL2O OF CACTVWAO
        ws.coactvw.cactvwao.acsadl2o.setValue(ws.cvcus01y.customerRecord.custAddrLine2);
        // COB(513): MOVE CUST-ADDR-LINE-3     TO ACSCITYO OF CACTVWAO
        ws.coactvw.cactvwao.acscityo.setValue(ws.cvcus01y.customerRecord.custAddrLine3);
        // COB(514): MOVE CUST-ADDR-STATE-CD   TO ACSSTTEO OF CACTVWAO
        ws.coactvw.cactvwao.acsstteo.setValue(ws.cvcus01y.customerRecord.custAddrStateCd);
        // COB(515): MOVE CUST-ADDR-ZIP        TO ACSZIPCO OF CACTVWAO
        ws.coactvw.cactvwao.acszipco.setValue(ws.cvcus01y.customerRecord.custAddrZip);
        // COB(516): MOVE CUST-ADDR-COUNTRY-CD TO ACSCTRYO OF CACTVWAO
        ws.coactvw.cactvwao.acsctryo.setValue(ws.cvcus01y.customerRecord.custAddrCountryCd);
        // COB(517): MOVE CUST-PHONE-NUM-1     TO ACSPHN1O OF CACTVWAO
        ws.coactvw.cactvwao.acsphn1o.setValue(ws.cvcus01y.customerRecord.custPhoneNum1);
        // COB(518): MOVE CUST-PHONE-NUM-2     TO ACSPHN2O OF CACTVWAO
        ws.coactvw.cactvwao.acsphn2o.setValue(ws.cvcus01y.customerRecord.custPhoneNum2);
        // COB(519): MOVE CUST-GOVT-ISSUED-ID  TO ACSGOVTO OF CACTVWAO
        ws.coactvw.cactvwao.acsgovto.setValue(ws.cvcus01y.customerRecord.custGovtIssuedId);
        // COB(520): MOVE CUST-EFT-ACCOUNT-ID  TO ACSEFTCO OF CACTVWAO
        ws.coactvw.cactvwao.acseftco.setValue(ws.cvcus01y.customerRecord.custEftAccountId);
        // COB(521): MOVE CUST-PRI-CARD-HOLDER-IND
        // COB(521):                           TO ACSPFLGO OF CACTVWAO
        ws.coactvw.cactvwao.acspflgo.setValue(ws.cvcus01y.customerRecord.custPriCardHolderInd);
        // COB(523): END-IF
      }
      // COB(525): END-IF
    }
    // COB(528): IF WS-NO-INFO-MESSAGE
    //     SETUP MESSAGE
    if (ws.wsMiscStorage.wsNoInfoMessage()) {
      // COB(529): SET WS-PROMPT-FOR-INPUT TO TRUE
      ws.wsMiscStorage.setWsPromptForInput(true);
      // COB(530): END-IF
    }
    // COB(532): MOVE WS-RETURN-MSG          TO ERRMSGO OF CACTVWAO
    ws.coactvw.cactvwao.errmsgo.setValue(ws.wsMiscStorage.wsReturnMsg);
    // COB(534): MOVE WS-INFO-MSG            TO INFOMSGO OF CACTVWAO
    ws.coactvw.cactvwao.infomsgo.setValue(ws.wsMiscStorage.wsInfoMsg);
  }

  protected void _1300SetupScreenAttrs() {
    // COB(543): MOVE DFHBMFSE               TO ACCTSIDA OF CACTVWAI
    //     PROTECT OR UNPROTECT BASED ON CONTEXT
    ws.coactvw.cactvwai.filler57.acctsida.setValue(Dfhbmsca.DFHBMFSE.getValue());
    // COB(546): EVALUATE TRUE
    //     POSITION CURSOR
    // COB(547): WHEN FLG-ACCTFILTER-NOT-OK
    if ((ws.wsMiscStorage.flgAcctfilterNotOk())
        // COB(548): WHEN FLG-ACCTFILTER-BLANK
        || ws.wsMiscStorage.flgAcctfilterBlank()) {
      // COB(549): MOVE -1             TO ACCTSIDL OF CACTVWAI
      ws.coactvw.cactvwai.acctsidl.setValue(-1);
      // COB(550): WHEN OTHER
    } else {
      // COB(551): MOVE -1             TO ACCTSIDL OF CACTVWAI
      ws.coactvw.cactvwai.acctsidl.setValue(-1);
      // COB(552): END-EVALUATE
    }
    // COB(555): MOVE DFHDFCOL               TO ACCTSIDC OF CACTVWAO
    //     SETUP COLOR
    ws.coactvw.cactvwao.acctsidc.setValue(Dfhbmsca.DFHDFCOL.getValue());
    // COB(557): IF FLG-ACCTFILTER-NOT-OK
    if (ws.wsMiscStorage.flgAcctfilterNotOk()) {
      // COB(558): MOVE DFHRED              TO ACCTSIDC OF CACTVWAO
      ws.coactvw.cactvwao.acctsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(559): END-IF
    }
    // COB(561): IF  FLG-ACCTFILTER-BLANK
    // COB(561): AND CDEMO-PGM-REENTER
    if (ws.wsMiscStorage.flgAcctfilterBlank()
        && ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
      // COB(563): MOVE '*'                TO ACCTSIDO OF CACTVWAO
      ws.coactvw.cactvwao.acctsido.setString("*");
      // COB(564): MOVE DFHRED             TO ACCTSIDC OF CACTVWAO
      ws.coactvw.cactvwao.acctsidc.setValue(Dfhbmsca.DFHRED.getValue());
      // COB(565): END-IF
    }
    // COB(567): IF  WS-NO-INFO-MESSAGE
    if (ws.wsMiscStorage.wsNoInfoMessage()) {
      // COB(568): MOVE DFHBMDAR           TO INFOMSGC OF CACTVWAO
      ws.coactvw.cactvwao.infomsgc.setValue(Dfhbmsca.DFHBMDAR.getValue());
      // COB(569): ELSE
    } else {
      // COB(570): MOVE DFHNEUTR           TO INFOMSGC OF CACTVWAO
      ws.coactvw.cactvwao.infomsgc.setValue(Dfhbmsca.DFHNEUTR.getValue());
      // COB(571): END-IF
    }
  }

  protected void _1400SendScreen() {
    // COB(579): MOVE LIT-THISMAPSET         TO CCARD-NEXT-MAPSET
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsLiterals.litThismapset);
    // COB(580): MOVE LIT-THISMAP            TO CCARD-NEXT-MAP
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsLiterals.litThismap);
    // COB(581): SET  CDEMO-PGM-REENTER TO TRUE
    ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
    // COB(583): EXEC CICS SEND MAP(CCARD-NEXT-MAP)
    // COB(583):                MAPSET(CCARD-NEXT-MAPSET)
    // COB(583):                FROM(CACTVWAO)
    // COB(583):                CURSOR
    // COB(583):                ERASE
    // COB(583):                FREEKB
    // COB(583):                RESP(WS-RESP-CD)
    // COB(583): END-EXEC
    supernaut
        .sendMap(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.getValue()) //
        .mapset(ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.getValue()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .from(ws.coactvw.cactvwao) //
        .freekb() //
        .erase() //
        .cursor() //
        .exec();
  }

  protected void _2000ProcessInputs() {
    // COB(597): PERFORM 2100-RECEIVE-MAP
    // COB(597):    THRU 2100-RECEIVE-MAP-EXIT
    _2100ReceiveMap();
    // COB(599): PERFORM 2200-EDIT-MAP-INPUTS
    // COB(599):    THRU 2200-EDIT-MAP-INPUTS-EXIT
    _2200EditMapInputs();
    // COB(601): MOVE WS-RETURN-MSG  TO CCARD-ERROR-MSG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardErrorMsg.setValue(ws.wsMiscStorage.wsReturnMsg);
    // COB(602): MOVE LIT-THISPGM    TO CCARD-NEXT-PROG
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextProg.setValue(ws.wsLiterals.litThispgm);
    // COB(603): MOVE LIT-THISMAPSET TO CCARD-NEXT-MAPSET
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMapset.setValue(ws.wsLiterals.litThismapset);
    // COB(604): MOVE LIT-THISMAP    TO CCARD-NEXT-MAP
    ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccardNextMap.setValue(ws.wsLiterals.litThismap);
  }

  protected void _2100ReceiveMap() {
    // COB(611): EXEC CICS RECEIVE MAP(LIT-THISMAP)
    // COB(611):           MAPSET(LIT-THISMAPSET)
    // COB(611):           INTO(CACTVWAI)
    // COB(611):           RESP(WS-RESP-CD)
    // COB(611):           RESP2(WS-REAS-CD)
    // COB(611): END-EXEC
    supernaut
        .receiveMap(ws.wsLiterals.litThismap.getValue()) //
        .mapset(ws.wsLiterals.litThismapset.getValue()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.coactvw.cactvwai) //
        .exec();
  }

  protected void _2200EditMapInputs() {
    // COB(624): SET INPUT-OK                  TO TRUE
    ws.wsMiscStorage.setInputOk(true);
    // COB(625): SET FLG-ACCTFILTER-ISVALID    TO TRUE
    ws.wsMiscStorage.setFlgAcctfilterIsvalid(true);
    // COB(628): IF  ACCTSIDI OF CACTVWAI = '*'
    // COB(628): OR  ACCTSIDI OF CACTVWAI = SPACES
    //     REPLACE * WITH LOW-VALUES
    if (ws.coactvw.cactvwai.acctsidi.equals("*") || ws.coactvw.cactvwai.acctsidi.isSpaces()) {
      // COB(630): MOVE LOW-VALUES           TO  CC-ACCT-ID
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.lowValues();
      // COB(631): ELSE
    } else {
      // COB(632): MOVE ACCTSIDI OF CACTVWAI TO  CC-ACCT-ID
      ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.setValue(ws.coactvw.cactvwai.acctsidi);
      // COB(633): END-IF
    }
    // COB(636): PERFORM 2210-EDIT-ACCOUNT
    // COB(636):    THRU 2210-EDIT-ACCOUNT-EXIT
    //     INDIVIDUAL FIELD EDITS
    _2210EditAccount();
    // COB(640): IF  FLG-ACCTFILTER-BLANK
    //     CROSS FIELD EDITS
    if (ws.wsMiscStorage.flgAcctfilterBlank()) {
      // COB(641): SET NO-SEARCH-CRITERIA-RECEIVED TO TRUE
      ws.wsMiscStorage.setNoSearchCriteriaReceived(true);
      // COB(642): END-IF
    }
  }

  protected void _2210EditAccount() {
    // COB(650): SET FLG-ACCTFILTER-NOT-OK TO TRUE
    ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
    // COB(653): IF CC-ACCT-ID   EQUAL LOW-VALUES
    // COB(653): OR CC-ACCT-ID   EQUAL SPACES
    //     Not supplied
    if (ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isLowValues()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isSpaces()) {
      // COB(655): SET INPUT-ERROR           TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(656): SET FLG-ACCTFILTER-BLANK  TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterBlank(true);
      // COB(657): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(658): SET WS-PROMPT-FOR-ACCT TO TRUE
        ws.wsMiscStorage.setWsPromptForAcct(true);
        // COB(659): END-IF
      }
      // COB(660): MOVE ZEROES       TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
      // COB(661): GO TO  2210-EDIT-ACCOUNT-EXIT
      return;
      // COB(662): END-IF
    }
    // COB(666): IF CC-ACCT-ID  IS NOT NUMERIC
    // COB(666): OR CC-ACCT-ID  EQUAL ZEROES
    //
    //     Not numeric
    //     Not 11 characters
    if (!ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.isNumeric()
        || ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId.equals(0)) {
      // COB(668): SET INPUT-ERROR TO TRUE
      ws.wsMiscStorage.setInputError(true);
      // COB(669): SET FLG-ACCTFILTER-NOT-OK TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
      // COB(670): IF WS-RETURN-MSG-OFF
      if (ws.wsMiscStorage.wsReturnMsgOff()) {
        // COB(671): MOVE
        // COB(671):               'Account Filter must  be a non-zero 11 digit number'
        // COB(671):               TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setString(
            "Account Filter must  be a non-zero 11 digit number");
        // COB(674): END-IF
      }
      // COB(675): MOVE ZERO       TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.zeros();
      // COB(676): GO TO 2210-EDIT-ACCOUNT-EXIT
      return;
      // COB(677): ELSE
    } else {
      // COB(678): MOVE CC-ACCT-ID TO CDEMO-ACCT-ID
      ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.setValue(
          ws.cvcrd01y.ccWorkAreas.ccWorkArea.ccAcctId);
      // COB(679): SET FLG-ACCTFILTER-ISVALID TO TRUE
      ws.wsMiscStorage.setFlgAcctfilterIsvalid(true);
      // COB(680): END-IF
    }
  }

  protected void _9000ReadAcct() {
    // COB(689): SET  WS-NO-INFO-MESSAGE  TO TRUE
    ws.wsMiscStorage.setWsNoInfoMessage(true);
    // COB(691): MOVE CDEMO-ACCT-ID TO WS-CARD-RID-ACCT-ID
    ws.wsMiscStorage.wsXrefRid.wsCardRidAcctId.setValue(
        ws.cocom01y.carddemoCommarea.cdemoAccountInfo.cdemoAcctId);
    // COB(693): PERFORM 9200-GETCARDXREF-BYACCT
    // COB(693):    THRU 9200-GETCARDXREF-BYACCT-EXIT
    _9200GetcardxrefByacct();
    // COB(697): IF FLG-ACCTFILTER-NOT-OK
    //     IF DID-NOT-FIND-ACCT-IN-CARDXREF
    if (ws.wsMiscStorage.flgAcctfilterNotOk()) {
      // COB(698): GO TO 9000-READ-ACCT-EXIT
      return;
      // COB(699): END-IF
    }
    // COB(701): PERFORM 9300-GETACCTDATA-BYACCT
    // COB(701):    THRU 9300-GETACCTDATA-BYACCT-EXIT
    _9300GetacctdataByacct();
    // COB(704): IF DID-NOT-FIND-ACCT-IN-ACCTDAT
    if (ws.wsMiscStorage.didNotFindAcctInAcctdat()) {
      // COB(705): GO TO 9000-READ-ACCT-EXIT
      return;
      // COB(706): END-IF
    }
    // COB(708): MOVE CDEMO-CUST-ID TO WS-CARD-RID-CUST-ID
    ws.wsMiscStorage.wsXrefRid.wsCardRidCustId.setValue(
        ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustId);
    // COB(710): PERFORM 9400-GETCUSTDATA-BYCUST
    // COB(710):    THRU 9400-GETCUSTDATA-BYCUST-EXIT
    _9400GetcustdataBycust();
    // COB(713): IF DID-NOT-FIND-CUST-IN-CUSTDAT
    if (ws.wsMiscStorage.didNotFindCustInCustdat()) {
      // COB(714): GO TO 9000-READ-ACCT-EXIT
      return;
      // COB(715): END-IF
    }
  }

  protected void _9200GetcardxrefByacct() {
    // COB(727): EXEC CICS READ
    // COB(727):      DATASET   (LIT-CARDXREFNAME-ACCT-PATH)
    // COB(727):      RIDFLD    (WS-CARD-RID-ACCT-ID-X)
    // COB(727):      KEYLENGTH (LENGTH OF WS-CARD-RID-ACCT-ID-X)
    // COB(727):      INTO      (CARD-XREF-RECORD)
    // COB(727):      LENGTH    (LENGTH OF CARD-XREF-RECORD)
    // COB(727):      RESP      (WS-RESP-CD)
    // COB(727):      RESP2     (WS-REAS-CD)
    // COB(727): END-EXEC
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
    // COB(737): EVALUATE WS-RESP-CD
    switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
        // COB(738): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(739): MOVE XREF-CUST-ID               TO CDEMO-CUST-ID
        ws.cocom01y.carddemoCommarea.cdemoCustomerInfo.cdemoCustId.setValue(
            ws.cvact03y.cardXrefRecord.xrefCustId);
        // COB(740): MOVE XREF-CARD-NUM              TO CDEMO-CARD-NUM
        ws.cocom01y.carddemoCommarea.cdemoCardInfo.cdemoCardNum.setValue(
            ws.cvact03y.cardXrefRecord.xrefCardNum);
        // COB(741): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(742): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(743): SET FLG-ACCTFILTER-NOT-OK       TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(744): IF WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(745): MOVE WS-RESP-CD               TO ERROR-RESP
          ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
          // COB(746): MOVE WS-REAS-CD               TO ERROR-RESP2
          ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
          // COB(747): STRING
          // COB(747): 'Account:'
          // COB(747):  WS-CARD-RID-ACCT-ID-X
          // COB(747): ' not found in'
          // COB(747): ' Cross ref file.  Resp:'
          // COB(747): ERROR-RESP
          // COB(747): ' Reas:'
          // COB(747): ERROR-RESP2
          // COB(747): DELIMITED BY SIZE
          // COB(747): INTO WS-RETURN-MSG
          // COB(747): END-STRING
          ws.wsMiscStorage.wsReturnMsg.concat(
              "Account:",
              ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX,
              " not found in",
              " Cross ref file.  Resp:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp,
              " Reas:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp2);
          // COB(757): END-STRING
          // COB(758): END-IF
        }
        // COB(759): WHEN OTHER
        break;
      default:
        // COB(760): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(761): SET FLG-ACCTFILTER-NOT-OK                TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(762): MOVE 'READ'                     TO ERROR-OPNAME
        ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
        // COB(763): MOVE LIT-CARDXREFNAME-ACCT-PATH TO ERROR-FILE
        ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(
            ws.wsLiterals.litCardxrefnameAcctPath);
        // COB(764): MOVE WS-RESP-CD                 TO ERROR-RESP
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(765): MOVE WS-REAS-CD                 TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(766): MOVE WS-FILE-ERROR-MESSAGE      TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
        // COB(769): END-EVALUATE
        //                                               WS-LONG-MSG
        //           PERFORM SEND-LONG-TEXT
    }
  }

  protected void _9300GetacctdataByacct() {
    // COB(776): EXEC CICS READ
    // COB(776):      DATASET   (LIT-ACCTFILENAME)
    // COB(776):      RIDFLD    (WS-CARD-RID-ACCT-ID-X)
    // COB(776):      KEYLENGTH (LENGTH OF WS-CARD-RID-ACCT-ID-X)
    // COB(776):      INTO      (ACCOUNT-RECORD)
    // COB(776):      LENGTH    (LENGTH OF ACCOUNT-RECORD)
    // COB(776):      RESP      (WS-RESP-CD)
    // COB(776):      RESP2     (WS-REAS-CD)
    // COB(776): END-EXEC
    supernaut
        .read(ws.wsLiterals.litAcctfilename.getValue()) //
        .length(ws.cvact01y.accountRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvact01y.accountRecord) //
        .ridfld(ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX) //
        .keylength(ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX.length()) //
        .exec();
    // COB(786): EVALUATE WS-RESP-CD
    switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
        // COB(787): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(788): SET FOUND-ACCT-IN-MASTER        TO TRUE
        ws.wsMiscStorage.wsFileReadFlags.setFoundAcctInMaster(true);
        // COB(789): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(790): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(791): SET FLG-ACCTFILTER-NOT-OK       TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(793): IF WS-RETURN-MSG-OFF
        //            SET DID-NOT-FIND-ACCT-IN-ACCTDAT TO TRUE
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(794): MOVE WS-RESP-CD               TO ERROR-RESP
          ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
          // COB(795): MOVE WS-REAS-CD               TO ERROR-RESP2
          ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
              ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
          // COB(796): STRING
          // COB(796): 'Account:'
          // COB(796):  WS-CARD-RID-ACCT-ID-X
          // COB(796): ' not found in'
          // COB(796): ' Acct Master file.Resp:'
          // COB(796): ERROR-RESP
          // COB(796): ' Reas:'
          // COB(796): ERROR-RESP2
          // COB(796): DELIMITED BY SIZE
          // COB(796): INTO WS-RETURN-MSG
          // COB(796): END-STRING
          ws.wsMiscStorage.wsReturnMsg.concat(
              "Account:",
              ws.wsMiscStorage.wsXrefRid.wsCardRidAcctIdX,
              " not found in",
              " Acct Master file.Resp:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp,
              " Reas:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp2);
          // COB(806): END-STRING
          // COB(807): END-IF
        }
        // COB(809): WHEN OTHER
        break;
      default:
        // COB(810): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(811): SET FLG-ACCTFILTER-NOT-OK                TO TRUE
        ws.wsMiscStorage.setFlgAcctfilterNotOk(true);
        // COB(812): MOVE 'READ'                     TO ERROR-OPNAME
        ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
        // COB(813): MOVE LIT-ACCTFILENAME           TO ERROR-FILE
        ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(ws.wsLiterals.litAcctfilename);
        // COB(814): MOVE WS-RESP-CD                 TO ERROR-RESP
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(815): MOVE WS-REAS-CD                 TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(816): MOVE WS-FILE-ERROR-MESSAGE      TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
        // COB(819): END-EVALUATE
        //                                               WS-LONG-MSG
        //            PERFORM SEND-LONG-TEXT
    }
  }

  protected void _9400GetcustdataBycust() {
    // COB(826): EXEC CICS READ
    // COB(826):      DATASET   (LIT-CUSTFILENAME)
    // COB(826):      RIDFLD    (WS-CARD-RID-CUST-ID-X)
    // COB(826):      KEYLENGTH (LENGTH OF WS-CARD-RID-CUST-ID-X)
    // COB(826):      INTO      (CUSTOMER-RECORD)
    // COB(826):      LENGTH    (LENGTH OF CUSTOMER-RECORD)
    // COB(826):      RESP      (WS-RESP-CD)
    // COB(826):      RESP2     (WS-REAS-CD)
    // COB(826): END-EXEC
    supernaut
        .read(ws.wsLiterals.litCustfilename.getValue()) //
        .length(ws.cvcus01y.customerRecord.length()) //
        .resp(ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd) //
        .resp2(ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd) //
        .into(ws.cvcus01y.customerRecord) //
        .ridfld(ws.wsMiscStorage.wsXrefRid.wsCardRidCustIdX) //
        .keylength(ws.wsMiscStorage.wsXrefRid.wsCardRidCustIdX.length()) //
        .exec();
    // COB(836): EVALUATE WS-RESP-CD
    switch (ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd.getInt()) {
        // COB(837): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(838): SET FOUND-CUST-IN-MASTER        TO TRUE
        ws.wsMiscStorage.wsFileReadFlags.setFoundCustInMaster(true);
        // COB(839): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(840): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(841): SET FLG-CUSTFILTER-NOT-OK       TO TRUE
        ws.wsMiscStorage.setFlgCustfilterNotOk(true);
        // COB(843): MOVE WS-RESP-CD               TO ERROR-RESP
        //            SET DID-NOT-FIND-CUST-IN-CUSTDAT TO TRUE
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(844): MOVE WS-REAS-CD               TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(845): IF WS-RETURN-MSG-OFF
        if (ws.wsMiscStorage.wsReturnMsgOff()) {
          // COB(846): STRING
          // COB(846): 'CustId:'
          // COB(846):  WS-CARD-RID-CUST-ID-X
          // COB(846): ' not found'
          // COB(846): ' in customer master.Resp: '
          // COB(846): ERROR-RESP
          // COB(846): ' REAS:'
          // COB(846): ERROR-RESP2
          // COB(846): DELIMITED BY SIZE
          // COB(846): INTO WS-RETURN-MSG
          // COB(846): END-STRING
          ws.wsMiscStorage.wsReturnMsg.concat(
              "CustId:",
              ws.wsMiscStorage.wsXrefRid.wsCardRidCustIdX,
              " not found",
              " in customer master.Resp: ",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp,
              " REAS:",
              ws.wsMiscStorage.wsFileErrorMessage.errorResp2);
          // COB(856): END-STRING
          // COB(857): END-IF
        }
        // COB(858): WHEN OTHER
        break;
      default:
        // COB(859): SET INPUT-ERROR                 TO TRUE
        ws.wsMiscStorage.setInputError(true);
        // COB(860): SET FLG-CUSTFILTER-NOT-OK                TO TRUE
        ws.wsMiscStorage.setFlgCustfilterNotOk(true);
        // COB(861): MOVE 'READ'                     TO ERROR-OPNAME
        ws.wsMiscStorage.wsFileErrorMessage.errorOpname.setString("READ");
        // COB(862): MOVE LIT-CUSTFILENAME           TO ERROR-FILE
        ws.wsMiscStorage.wsFileErrorMessage.errorFile.setValue(ws.wsLiterals.litCustfilename);
        // COB(863): MOVE WS-RESP-CD                 TO ERROR-RESP
        ws.wsMiscStorage.wsFileErrorMessage.errorResp.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsRespCd);
        // COB(864): MOVE WS-REAS-CD                 TO ERROR-RESP2
        ws.wsMiscStorage.wsFileErrorMessage.errorResp2.setValue(
            ws.wsMiscStorage.wsCicsProcessngVars.wsReasCd);
        // COB(865): MOVE WS-FILE-ERROR-MESSAGE      TO WS-RETURN-MSG
        ws.wsMiscStorage.wsReturnMsg.setValue(ws.wsMiscStorage.wsFileErrorMessage);
        // COB(868): END-EVALUATE
        //                                               WS-LONG-MSG
        //            PERFORM SEND-LONG-TEXT
    }
  }

  /*****************************************************************
   * Plain text exit - Dont use in production                      *
   *****************************************************************/
  protected void sendPlainText() {
    // COB(878): EXEC CICS SEND TEXT
    // COB(878):           FROM(WS-RETURN-MSG)
    // COB(878):           LENGTH(LENGTH OF WS-RETURN-MSG)
    // COB(878):           ERASE
    // COB(878):           FREEKB
    // COB(878): END-EXEC
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
    // COB(885): EXEC CICS RETURN
    // COB(885): END-EXEC
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
    // COB(897): EXEC CICS SEND TEXT
    // COB(897):           FROM(WS-LONG-MSG)
    // COB(897):           LENGTH(LENGTH OF WS-LONG-MSG)
    // COB(897):           ERASE
    // COB(897):           FREEKB
    // COB(897): END-EXEC
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
    // COB(904): EXEC CICS RETURN
    // COB(904): END-EXEC
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

  /***/
  protected void abendRoutine() {
    // COB(918): IF ABEND-MSG EQUAL LOW-VALUES
    if (ws.csmsg02y.abendData.abendMsg.isLowValues()) {
      // COB(919): MOVE 'UNEXPECTED ABEND OCCURRED.' TO ABEND-MSG
      ws.csmsg02y.abendData.abendMsg.setString("UNEXPECTED ABEND OCCURRED.");
      // COB(920): END-IF
    }
    // COB(922): MOVE LIT-THISPGM       TO ABEND-CULPRIT
    ws.csmsg02y.abendData.abendCulprit.setValue(ws.wsLiterals.litThispgm);
    // COB(924): EXEC CICS SEND
    // COB(924):                  FROM (ABEND-DATA)
    // COB(924):                  LENGTH(LENGTH OF ABEND-DATA)
    // COB(924):                  NOHANDLE
    // COB(924): END-EXEC
    supernaut
        .send() //
        .length(ws.csmsg02y.abendData.length()) //
        .nohandle() //
        .from(ws.csmsg02y.abendData) //
        .exec();
    // COB(930): EXEC CICS HANDLE ABEND
    // COB(930):      CANCEL
    // COB(930): END-EXEC
    supernaut
        .handleAbend() //
        .cancel() //
        .exec();
    // COB(934): EXEC CICS ABEND
    // COB(934):      ABCODE('9999')
    // COB(934): END-EXEC
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
