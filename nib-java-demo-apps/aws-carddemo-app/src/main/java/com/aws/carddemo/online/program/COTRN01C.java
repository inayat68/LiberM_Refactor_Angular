package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cotrn01c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COTRN01C extends AbstractOnlineProgram {

  @ProgramStorage final Cotrn01cWorking ws = new Cotrn01cWorking();

  // Linkage
  public static class Cotrn01cLinkage extends NGroup {
    // COB:        01  DFHCOMMAREA.
    public Dfhcommarea dfhcommarea = new Dfhcommarea();

    public static class Dfhcommarea extends NGroup {

      // COB:          05  LK-COMMAREA                           PIC X(01)
      // COB:              OCCURS 1 TO 32767 TIMES DEPENDING ON EIBCALEN.
      public NChar[] lkCommarea = AbstractNField.occurs(32767, new NChar(1));

      public NChar lkCommareaAtIndex(int index) {
        return getOccursInstance(lkCommarea, index);
      }
    }
  }

  final Cotrn01cLinkage params = new Cotrn01cLinkage();

  public COTRN01C(Context supernaut) {
    super(supernaut);
  }

  @Override
  protected int procedure(AbstractNField dfhcommarea) throws ContextException {
    if (dfhcommarea != null) {
      params.dfhcommarea.setAddress(dfhcommarea.getAddress());
    } else {
      params.dfhcommarea.allocate();
    }
    mainPara();
    return 0;
  }

  protected void mainPara() {
    // COB(88): SET ERR-FLG-OFF     TO TRUE
    ws.wsVariables.setErrFlgOff(true);
    // COB(89): SET USR-MODIFIED-NO TO TRUE
    ws.wsVariables.setUsrModifiedNo(true);
    // COB(91): MOVE SPACES TO WS-MESSAGE
    // COB(91):                ERRMSGO OF COTRN1AO
    ws.wsVariables.wsMessage.spaces();
    ws.cotrn01.cotrn1ao.errmsgo.spaces();
    // COB(94): IF EIBCALEN = 0
    if (dfheiblk.getEibcalen() == 0) {
      // COB(95): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(96): PERFORM RETURN-TO-PREV-SCREEN
      returnToPrevScreen();
      // COB(97): ELSE
    } else {
      // COB(98): MOVE DFHCOMMAREA(1:EIBCALEN) TO CARDDEMO-COMMAREA
      ws.carddemoCommarea.setValue(params.dfhcommarea, 0, dfheiblk.getEibcalen());
      // COB(99): IF NOT CDEMO-PGM-REENTER
      if (!ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
        // COB(100): SET CDEMO-PGM-REENTER    TO TRUE
        ws.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
        // COB(101): MOVE LOW-VALUES          TO COTRN1AO
        ws.cotrn01.cotrn1ao.lowValues();
        // COB(102): MOVE -1       TO TRNIDINL OF COTRN1AI
        ws.cotrn01.cotrn1ai.trnidinl.setValue(-1);
        // COB(103): IF CDEMO-CT01-TRN-SELECTED NOT =
        // COB(103):                            SPACES AND LOW-VALUES
        if (!ws.carddemoCommarea.cdemoCt01Info.cdemoCt01TrnSelected.isSpaces()
            && !ws.carddemoCommarea.cdemoCt01Info.cdemoCt01TrnSelected.isLowValues()) {
          // COB(105): MOVE CDEMO-CT01-TRN-SELECTED TO
          // COB(105):      TRNIDINI OF COTRN1AI
          ws.cotrn01.cotrn1ai.trnidini.setValue(
              ws.carddemoCommarea.cdemoCt01Info.cdemoCt01TrnSelected);
          // COB(107): PERFORM PROCESS-ENTER-KEY
          processEnterKey();
          // COB(108): END-IF
        }
        // COB(109): PERFORM SEND-TRNVIEW-SCREEN
        sendTrnviewScreen();
        // COB(110): ELSE
      } else {
        // COB(111): PERFORM RECEIVE-TRNVIEW-SCREEN
        receiveTrnviewScreen();
        // COB(112): EVALUATE EIBAID
        switch (dfheiblk.getEibaid()) {
            // COB(113): WHEN DFHENTER
          case DFHENTER:
            // COB(114): PERFORM PROCESS-ENTER-KEY
            processEnterKey();
            // COB(115): WHEN DFHPF3
            break;
          case DFHPF3:
            // COB(116): IF CDEMO-FROM-PROGRAM = SPACES OR LOW-VALUES
            if (ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isSpaces()
                || ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isLowValues()) {
              // COB(117): MOVE 'COMEN01C' TO CDEMO-TO-PROGRAM
              ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COMEN01C");
              // COB(118): ELSE
            } else {
              // COB(119): MOVE CDEMO-FROM-PROGRAM TO
              // COB(119): CDEMO-TO-PROGRAM
              ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
                  ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram);
              // COB(121): END-IF
            }
            // COB(122): PERFORM RETURN-TO-PREV-SCREEN
            returnToPrevScreen();
            // COB(123): WHEN DFHPF4
            break;
          case DFHPF4:
            // COB(124): PERFORM CLEAR-CURRENT-SCREEN
            clearCurrentScreen();
            // COB(125): WHEN DFHPF5
            break;
          case DFHPF5:
            // COB(126): MOVE 'COTRN00C' TO CDEMO-TO-PROGRAM
            ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COTRN00C");
            // COB(127): PERFORM RETURN-TO-PREV-SCREEN
            returnToPrevScreen();
            // COB(128): WHEN OTHER
            break;
          default:
            // COB(129): MOVE 'Y'                       TO WS-ERR-FLG
            ws.wsVariables.wsErrFlg.setString("Y");
            // COB(130): MOVE CCDA-MSG-INVALID-KEY      TO WS-MESSAGE
            ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgInvalidKey);
            // COB(131): PERFORM SEND-TRNVIEW-SCREEN
            sendTrnviewScreen();
            // COB(132): END-EVALUATE
        }
        // COB(133): END-IF
      }
      // COB(134): END-IF
    }
    // COB(136): EXEC CICS RETURN
    // COB(136):           TRANSID (WS-TRANID)
    // COB(136):           COMMAREA (CARDDEMO-COMMAREA)
    // COB(136): END-EXEC.
    try {
      supernaut
          .returnTransid(ws.wsVariables.wsTranid) //
          .commarea(ws.carddemoCommarea) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      PROCESS-ENTER-KEY
   *----------------------------------------------------------------*/
  protected void processEnterKey() {
    // COB(146): EVALUATE TRUE
    // COB(147): WHEN TRNIDINI OF COTRN1AI = SPACES OR LOW-VALUES
    if (ws.cotrn01.cotrn1ai.trnidini.isSpaces() || ws.cotrn01.cotrn1ai.trnidini.isLowValues()) {
      // COB(148): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(149): MOVE 'Tran ID can NOT be empty...' TO
      // COB(149):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Tran ID can NOT be empty...");
      // COB(151): MOVE -1       TO TRNIDINL OF COTRN1AI
      ws.cotrn01.cotrn1ai.trnidinl.setValue(-1);
      // COB(152): PERFORM SEND-TRNVIEW-SCREEN
      sendTrnviewScreen();
      // COB(153): WHEN OTHER
    } else {
      // COB(154): MOVE -1       TO TRNIDINL OF COTRN1AI
      ws.cotrn01.cotrn1ai.trnidinl.setValue(-1);
      // COB(155): CONTINUE
      // do nothing
      // COB(156): END-EVALUATE
    }
    // COB(158): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(159): MOVE SPACES      TO TRNIDI   OF COTRN1AI
      // COB(159):                     CARDNUMI OF COTRN1AI
      // COB(159):                     TTYPCDI  OF COTRN1AI
      // COB(159):                     TCATCDI  OF COTRN1AI
      // COB(159):                     TRNSRCI  OF COTRN1AI
      // COB(159):                     TRNAMTI  OF COTRN1AI
      // COB(159):                     TDESCI   OF COTRN1AI
      // COB(159):                     TORIGDTI OF COTRN1AI
      // COB(159):                     TPROCDTI OF COTRN1AI
      // COB(159):                     MIDI     OF COTRN1AI
      // COB(159):                     MNAMEI   OF COTRN1AI
      // COB(159):                     MCITYI   OF COTRN1AI
      // COB(159):                     MZIPI    OF COTRN1AI
      ws.cotrn01.cotrn1ai.trnidi.spaces();
      ws.cotrn01.cotrn1ai.cardnumi.spaces();
      ws.cotrn01.cotrn1ai.ttypcdi.spaces();
      ws.cotrn01.cotrn1ai.tcatcdi.spaces();
      ws.cotrn01.cotrn1ai.trnsrci.spaces();
      ws.cotrn01.cotrn1ai.trnamti.spaces();
      ws.cotrn01.cotrn1ai.tdesci.spaces();
      ws.cotrn01.cotrn1ai.torigdti.spaces();
      ws.cotrn01.cotrn1ai.tprocdti.spaces();
      ws.cotrn01.cotrn1ai.midi.spaces();
      ws.cotrn01.cotrn1ai.mnamei.spaces();
      ws.cotrn01.cotrn1ai.mcityi.spaces();
      ws.cotrn01.cotrn1ai.mzipi.spaces();
      // COB(172): MOVE TRNIDINI  OF COTRN1AI TO TRAN-ID
      ws.cvtra05y.tranRecord.tranId.setValue(ws.cotrn01.cotrn1ai.trnidini);
      // COB(173): PERFORM READ-TRANSACT-FILE
      readTransactFile();
      // COB(174): END-IF.
    }
    // COB(176): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(177): MOVE TRAN-AMT TO WS-TRAN-AMT
      ws.wsVariables.wsTranAmt.setValue(ws.cvtra05y.tranRecord.tranAmt);
      // COB(178): MOVE TRAN-ID      TO TRNIDI    OF COTRN1AI
      ws.cotrn01.cotrn1ai.trnidi.setValue(ws.cvtra05y.tranRecord.tranId);
      // COB(179): MOVE TRAN-CARD-NUM      TO CARDNUMI    OF COTRN1AI
      ws.cotrn01.cotrn1ai.cardnumi.setValue(ws.cvtra05y.tranRecord.tranCardNum);
      // COB(180): MOVE TRAN-TYPE-CD        TO TTYPCDI   OF COTRN1AI
      ws.cotrn01.cotrn1ai.ttypcdi.setValue(ws.cvtra05y.tranRecord.tranTypeCd);
      // COB(181): MOVE TRAN-CAT-CD        TO TCATCDI   OF COTRN1AI
      ws.cotrn01.cotrn1ai.tcatcdi.setValue(ws.cvtra05y.tranRecord.tranCatCd);
      // COB(182): MOVE TRAN-SOURCE       TO TRNSRCI  OF COTRN1AI
      ws.cotrn01.cotrn1ai.trnsrci.setValue(ws.cvtra05y.tranRecord.tranSource);
      // COB(183): MOVE WS-TRAN-AMT      TO TRNAMTI    OF COTRN1AI
      ws.cotrn01.cotrn1ai.trnamti.setValue(ws.wsVariables.wsTranAmt);
      // COB(184): MOVE TRAN-DESC      TO TDESCI    OF COTRN1AI
      ws.cotrn01.cotrn1ai.tdesci.setValue(ws.cvtra05y.tranRecord.tranDesc);
      // COB(185): MOVE TRAN-ORIG-TS        TO TORIGDTI   OF COTRN1AI
      ws.cotrn01.cotrn1ai.torigdti.setValue(ws.cvtra05y.tranRecord.tranOrigTs);
      // COB(186): MOVE TRAN-PROC-TS       TO TPROCDTI  OF COTRN1AI
      ws.cotrn01.cotrn1ai.tprocdti.setValue(ws.cvtra05y.tranRecord.tranProcTs);
      // COB(187): MOVE TRAN-MERCHANT-ID       TO MIDI  OF COTRN1AI
      ws.cotrn01.cotrn1ai.midi.setValue(ws.cvtra05y.tranRecord.tranMerchantId);
      // COB(188): MOVE TRAN-MERCHANT-NAME       TO MNAMEI  OF COTRN1AI
      ws.cotrn01.cotrn1ai.mnamei.setValue(ws.cvtra05y.tranRecord.tranMerchantName);
      // COB(189): MOVE TRAN-MERCHANT-CITY       TO MCITYI  OF COTRN1AI
      ws.cotrn01.cotrn1ai.mcityi.setValue(ws.cvtra05y.tranRecord.tranMerchantCity);
      // COB(190): MOVE TRAN-MERCHANT-ZIP       TO MZIPI  OF COTRN1AI
      ws.cotrn01.cotrn1ai.mzipi.setValue(ws.cvtra05y.tranRecord.tranMerchantZip);
      // COB(191): PERFORM SEND-TRNVIEW-SCREEN
      sendTrnviewScreen();
      // COB(192): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RETURN-TO-PREV-SCREEN
   *----------------------------------------------------------------*/
  protected void returnToPrevScreen() {
    // COB(199): IF CDEMO-TO-PROGRAM = LOW-VALUES OR SPACES
    if (ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isLowValues()
        || ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isSpaces()) {
      // COB(200): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(201): END-IF
    }
    // COB(202): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsVariables.wsTranid);
    // COB(203): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsVariables.wsPgmname);
    // COB(204): MOVE ZEROS        TO CDEMO-PGM-CONTEXT
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.zeros();
    // COB(205): EXEC CICS
    // COB(205):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
    // COB(205):     COMMAREA(CARDDEMO-COMMAREA)
    // COB(205): END-EXEC.
    try {
      supernaut
          .xctl(ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
          .commarea(ws.carddemoCommarea) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      SEND-TRNVIEW-SCREEN
   *----------------------------------------------------------------*/
  protected void sendTrnviewScreen() {
    // COB(215): PERFORM POPULATE-HEADER-INFO
    populateHeaderInfo();
    // COB(217): MOVE WS-MESSAGE TO ERRMSGO OF COTRN1AO
    ws.cotrn01.cotrn1ao.errmsgo.setValue(ws.wsVariables.wsMessage);
    // COB(219): EXEC CICS SEND
    // COB(219):           MAP('COTRN1A')
    // COB(219):           MAPSET('COTRN01')
    // COB(219):           FROM(COTRN1AO)
    // COB(219):           ERASE
    // COB(219):           CURSOR
    // COB(219): END-EXEC.
    try {
      supernaut
          .sendMap("COTRN1A") //
          .mapset("COTRN01") //
          .from(ws.cotrn01.cotrn1ao) //
          .erase() //
          .cursor() //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RECEIVE-TRNVIEW-SCREEN
   *----------------------------------------------------------------*/
  protected void receiveTrnviewScreen() {
    // COB(232): EXEC CICS RECEIVE
    // COB(232):           MAP('COTRN1A')
    // COB(232):           MAPSET('COTRN01')
    // COB(232):           INTO(COTRN1AI)
    // COB(232):           RESP(WS-RESP-CD)
    // COB(232):           RESP2(WS-REAS-CD)
    // COB(232): END-EXEC.
    supernaut
        .receiveMap("COTRN1A") //
        .mapset("COTRN01") //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cotrn01.cotrn1ai) //
        .exec();
  }

  /***
   *----------------------------------------------------------------*
   *                      POPULATE-HEADER-INFO
   *----------------------------------------------------------------*/
  protected void populateHeaderInfo() {
    // COB(245): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(247): MOVE CCDA-TITLE01           TO TITLE01O OF COTRN1AO
    ws.cotrn01.cotrn1ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(248): MOVE CCDA-TITLE02           TO TITLE02O OF COTRN1AO
    ws.cotrn01.cotrn1ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(249): MOVE WS-TRANID              TO TRNNAMEO OF COTRN1AO
    ws.cotrn01.cotrn1ao.trnnameo.setValue(ws.wsVariables.wsTranid);
    // COB(250): MOVE WS-PGMNAME             TO PGMNAMEO OF COTRN1AO
    ws.cotrn01.cotrn1ao.pgmnameo.setValue(ws.wsVariables.wsPgmname);
    // COB(252): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(253): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(254): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(256): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF COTRN1AO
    ws.cotrn01.cotrn1ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(258): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(259): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(260): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(262): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF COTRN1AO.
    ws.cotrn01.cotrn1ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***
   *----------------------------------------------------------------*
   *                      READ-TRANSACT-FILE
   *----------------------------------------------------------------*/
  protected void readTransactFile() {
    // COB(269): EXEC CICS READ
    // COB(269):      DATASET   (WS-TRANSACT-FILE)
    // COB(269):      INTO      (TRAN-RECORD)
    // COB(269):      LENGTH    (LENGTH OF TRAN-RECORD)
    // COB(269):      RIDFLD    (TRAN-ID)
    // COB(269):      KEYLENGTH (LENGTH OF TRAN-ID)
    // COB(269):      UPDATE
    // COB(269):      RESP      (WS-RESP-CD)
    // COB(269):      RESP2     (WS-REAS-CD)
    // COB(269): END-EXEC.
    supernaut
        .read(ws.wsVariables.wsTransactFile.getValue()) //
        .update() //
        .length(ws.cvtra05y.tranRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cvtra05y.tranRecord) //
        .ridfld(ws.cvtra05y.tranRecord.tranId) //
        .keylength(ws.cvtra05y.tranRecord.tranId.length()) //
        .exec();
    // COB(280): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(281): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(282): CONTINUE
        // do nothing
        // COB(283): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(284): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(285): MOVE 'Transaction ID NOT found...' TO
        // COB(285):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Transaction ID NOT found...");
        // COB(287): MOVE -1       TO TRNIDINL OF COTRN1AI
        ws.cotrn01.cotrn1ai.trnidinl.setValue(-1);
        // COB(288): PERFORM SEND-TRNVIEW-SCREEN
        sendTrnviewScreen();
        // COB(289): WHEN OTHER
        break;
      default:
        // COB(290): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(291): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(292): MOVE 'Unable to lookup Transaction...' TO
        // COB(292):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup Transaction...");
        // COB(294): MOVE -1       TO TRNIDINL OF COTRN1AI
        ws.cotrn01.cotrn1ai.trnidinl.setValue(-1);
        // COB(295): PERFORM SEND-TRNVIEW-SCREEN
        sendTrnviewScreen();
        // COB(296): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      CLEAR-CURRENT-SCREEN
   *----------------------------------------------------------------*/
  protected void clearCurrentScreen() {
    // COB(303): PERFORM INITIALIZE-ALL-FIELDS.
    initializeAllFields();
    // COB(304): PERFORM SEND-TRNVIEW-SCREEN.
    sendTrnviewScreen();
  }

  /***
   *----------------------------------------------------------------*
   *                      INITIALIZE-ALL-FIELDS
   *----------------------------------------------------------------*/
  protected void initializeAllFields() {
    // COB(311): MOVE -1              TO TRNIDINL OF COTRN1AI
    ws.cotrn01.cotrn1ai.trnidinl.setValue(-1);
    // COB(312): MOVE SPACES          TO TRNIDINI OF COTRN1AI
    // COB(312):                         TRNIDI   OF COTRN1AI
    // COB(312):                         CARDNUMI OF COTRN1AI
    // COB(312):                         TTYPCDI  OF COTRN1AI
    // COB(312):                         TCATCDI  OF COTRN1AI
    // COB(312):                         TRNSRCI  OF COTRN1AI
    // COB(312):                         TRNAMTI  OF COTRN1AI
    // COB(312):                         TDESCI   OF COTRN1AI
    // COB(312):                         TORIGDTI OF COTRN1AI
    // COB(312):                         TPROCDTI OF COTRN1AI
    // COB(312):                         MIDI     OF COTRN1AI
    // COB(312):                         MNAMEI   OF COTRN1AI
    // COB(312):                         MCITYI   OF COTRN1AI
    // COB(312):                         MZIPI    OF COTRN1AI
    // COB(312):                         WS-MESSAGE.
    ws.cotrn01.cotrn1ai.trnidini.spaces();
    ws.cotrn01.cotrn1ai.trnidi.spaces();
    ws.cotrn01.cotrn1ai.cardnumi.spaces();
    ws.cotrn01.cotrn1ai.ttypcdi.spaces();
    ws.cotrn01.cotrn1ai.tcatcdi.spaces();
    ws.cotrn01.cotrn1ai.trnsrci.spaces();
    ws.cotrn01.cotrn1ai.trnamti.spaces();
    ws.cotrn01.cotrn1ai.tdesci.spaces();
    ws.cotrn01.cotrn1ai.torigdti.spaces();
    ws.cotrn01.cotrn1ai.tprocdti.spaces();
    ws.cotrn01.cotrn1ai.midi.spaces();
    ws.cotrn01.cotrn1ai.mnamei.spaces();
    ws.cotrn01.cotrn1ai.mcityi.spaces();
    ws.cotrn01.cotrn1ai.mzipi.spaces();
    ws.wsVariables.wsMessage.spaces();
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
