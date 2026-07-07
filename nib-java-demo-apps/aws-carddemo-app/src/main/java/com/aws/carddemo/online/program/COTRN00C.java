package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cotrn00c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;
import java.math.BigDecimal;

public class COTRN00C extends AbstractOnlineProgram {

  @ProgramStorage final Cotrn00cWorking ws = new Cotrn00cWorking();

  // Linkage
  public static class Cotrn00cLinkage extends NGroup {
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

  final Cotrn00cLinkage params = new Cotrn00cLinkage();

  public COTRN00C(Context supernaut) {
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
    // COB(97): SET ERR-FLG-OFF TO TRUE
    ws.wsVariables.setErrFlgOff(true);
    // COB(98): SET TRANSACT-NOT-EOF TO TRUE
    ws.wsVariables.setTransactNotEof(true);
    // COB(99): SET NEXT-PAGE-NO TO TRUE
    ws.carddemoCommarea.cdemoCt00Info.setNextPageNo(true);
    // COB(100): SET SEND-ERASE-YES TO TRUE
    ws.wsVariables.setSendEraseYes(true);
    // COB(102): MOVE SPACES TO WS-MESSAGE
    // COB(102):                ERRMSGO OF COTRN0AO
    ws.wsVariables.wsMessage.spaces();
    ws.cotrn00.cotrn0ao.errmsgo.spaces();
    // COB(105): MOVE -1       TO TRNIDINL OF COTRN0AI
    ws.cotrn00.cotrn0ai.trnidinl.setValue(-1);
    // COB(107): IF EIBCALEN = 0
    if (dfheiblk.getEibcalen() == 0) {
      // COB(108): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(109): PERFORM RETURN-TO-PREV-SCREEN
      returnToPrevScreen();
      // COB(110): ELSE
    } else {
      // COB(111): MOVE DFHCOMMAREA(1:EIBCALEN) TO CARDDEMO-COMMAREA
      ws.carddemoCommarea.setValue(params.dfhcommarea, 0, dfheiblk.getEibcalen());
      // COB(112): IF NOT CDEMO-PGM-REENTER
      if (!ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
        // COB(113): SET CDEMO-PGM-REENTER    TO TRUE
        ws.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
        // COB(114): MOVE LOW-VALUES          TO COTRN0AO
        ws.cotrn00.cotrn0ao.lowValues();
        // COB(115): PERFORM PROCESS-ENTER-KEY
        processEnterKey();
        // COB(116): PERFORM SEND-TRNLST-SCREEN
        sendTrnlstScreen();
        // COB(117): ELSE
      } else {
        // COB(118): PERFORM RECEIVE-TRNLST-SCREEN
        receiveTrnlstScreen();
        // COB(119): EVALUATE EIBAID
        switch (dfheiblk.getEibaid()) {
            // COB(120): WHEN DFHENTER
          case DFHENTER:
            // COB(121): PERFORM PROCESS-ENTER-KEY
            processEnterKey();
            // COB(122): WHEN DFHPF3
            break;
          case DFHPF3:
            // COB(123): MOVE 'COMEN01C' TO CDEMO-TO-PROGRAM
            ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COMEN01C");
            // COB(124): PERFORM RETURN-TO-PREV-SCREEN
            returnToPrevScreen();
            // COB(125): WHEN DFHPF7
            break;
          case DFHPF7:
            // COB(126): PERFORM PROCESS-PF7-KEY
            processPf7Key();
            // COB(127): WHEN DFHPF8
            break;
          case DFHPF8:
            // COB(128): PERFORM PROCESS-PF8-KEY
            processPf8Key();
            // COB(129): WHEN OTHER
            break;
          default:
            // COB(130): MOVE 'Y'                       TO WS-ERR-FLG
            ws.wsVariables.wsErrFlg.setString("Y");
            // COB(131): MOVE -1       TO TRNIDINL OF COTRN0AI
            ws.cotrn00.cotrn0ai.trnidinl.setValue(-1);
            // COB(132): MOVE CCDA-MSG-INVALID-KEY      TO WS-MESSAGE
            ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgInvalidKey);
            // COB(133): PERFORM SEND-TRNLST-SCREEN
            sendTrnlstScreen();
            // COB(134): END-EVALUATE
        }
        // COB(135): END-IF
      }
      // COB(136): END-IF
    }
    // COB(138): EXEC CICS RETURN
    // COB(138):           TRANSID (WS-TRANID)
    // COB(138):           COMMAREA (CARDDEMO-COMMAREA)
    // COB(138): END-EXEC.
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
    // COB(148): EVALUATE TRUE
    // COB(149): WHEN SEL0001I OF COTRN0AI NOT = SPACES AND LOW-VALUES
    if (!ws.cotrn00.cotrn0ai.sel0001i.isSpaces() && !ws.cotrn00.cotrn0ai.sel0001i.isLowValues()) {
      // COB(150): MOVE SEL0001I OF COTRN0AI TO CDEMO-CT00-TRN-SEL-FLG
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.setValue(ws.cotrn00.cotrn0ai.sel0001i);
      // COB(151): MOVE TRNID01I OF COTRN0AI TO CDEMO-CT00-TRN-SELECTED
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelected.setValue(ws.cotrn00.cotrn0ai.trnid01i);
      // COB(152): WHEN SEL0002I OF COTRN0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cotrn00.cotrn0ai.sel0002i.isSpaces()
        && !ws.cotrn00.cotrn0ai.sel0002i.isLowValues()) {
      // COB(153): MOVE SEL0002I OF COTRN0AI TO CDEMO-CT00-TRN-SEL-FLG
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.setValue(ws.cotrn00.cotrn0ai.sel0002i);
      // COB(154): MOVE TRNID02I OF COTRN0AI TO CDEMO-CT00-TRN-SELECTED
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelected.setValue(ws.cotrn00.cotrn0ai.trnid02i);
      // COB(155): WHEN SEL0003I OF COTRN0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cotrn00.cotrn0ai.sel0003i.isSpaces()
        && !ws.cotrn00.cotrn0ai.sel0003i.isLowValues()) {
      // COB(156): MOVE SEL0003I OF COTRN0AI TO CDEMO-CT00-TRN-SEL-FLG
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.setValue(ws.cotrn00.cotrn0ai.sel0003i);
      // COB(157): MOVE TRNID03I OF COTRN0AI TO CDEMO-CT00-TRN-SELECTED
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelected.setValue(ws.cotrn00.cotrn0ai.trnid03i);
      // COB(158): WHEN SEL0004I OF COTRN0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cotrn00.cotrn0ai.sel0004i.isSpaces()
        && !ws.cotrn00.cotrn0ai.sel0004i.isLowValues()) {
      // COB(159): MOVE SEL0004I OF COTRN0AI TO CDEMO-CT00-TRN-SEL-FLG
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.setValue(ws.cotrn00.cotrn0ai.sel0004i);
      // COB(160): MOVE TRNID04I OF COTRN0AI TO CDEMO-CT00-TRN-SELECTED
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelected.setValue(ws.cotrn00.cotrn0ai.trnid04i);
      // COB(161): WHEN SEL0005I OF COTRN0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cotrn00.cotrn0ai.sel0005i.isSpaces()
        && !ws.cotrn00.cotrn0ai.sel0005i.isLowValues()) {
      // COB(162): MOVE SEL0005I OF COTRN0AI TO CDEMO-CT00-TRN-SEL-FLG
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.setValue(ws.cotrn00.cotrn0ai.sel0005i);
      // COB(163): MOVE TRNID05I OF COTRN0AI TO CDEMO-CT00-TRN-SELECTED
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelected.setValue(ws.cotrn00.cotrn0ai.trnid05i);
      // COB(164): WHEN SEL0006I OF COTRN0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cotrn00.cotrn0ai.sel0006i.isSpaces()
        && !ws.cotrn00.cotrn0ai.sel0006i.isLowValues()) {
      // COB(165): MOVE SEL0006I OF COTRN0AI TO CDEMO-CT00-TRN-SEL-FLG
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.setValue(ws.cotrn00.cotrn0ai.sel0006i);
      // COB(166): MOVE TRNID06I OF COTRN0AI TO CDEMO-CT00-TRN-SELECTED
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelected.setValue(ws.cotrn00.cotrn0ai.trnid06i);
      // COB(167): WHEN SEL0007I OF COTRN0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cotrn00.cotrn0ai.sel0007i.isSpaces()
        && !ws.cotrn00.cotrn0ai.sel0007i.isLowValues()) {
      // COB(168): MOVE SEL0007I OF COTRN0AI TO CDEMO-CT00-TRN-SEL-FLG
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.setValue(ws.cotrn00.cotrn0ai.sel0007i);
      // COB(169): MOVE TRNID07I OF COTRN0AI TO CDEMO-CT00-TRN-SELECTED
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelected.setValue(ws.cotrn00.cotrn0ai.trnid07i);
      // COB(170): WHEN SEL0008I OF COTRN0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cotrn00.cotrn0ai.sel0008i.isSpaces()
        && !ws.cotrn00.cotrn0ai.sel0008i.isLowValues()) {
      // COB(171): MOVE SEL0008I OF COTRN0AI TO CDEMO-CT00-TRN-SEL-FLG
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.setValue(ws.cotrn00.cotrn0ai.sel0008i);
      // COB(172): MOVE TRNID08I OF COTRN0AI TO CDEMO-CT00-TRN-SELECTED
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelected.setValue(ws.cotrn00.cotrn0ai.trnid08i);
      // COB(173): WHEN SEL0009I OF COTRN0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cotrn00.cotrn0ai.sel0009i.isSpaces()
        && !ws.cotrn00.cotrn0ai.sel0009i.isLowValues()) {
      // COB(174): MOVE SEL0009I OF COTRN0AI TO CDEMO-CT00-TRN-SEL-FLG
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.setValue(ws.cotrn00.cotrn0ai.sel0009i);
      // COB(175): MOVE TRNID09I OF COTRN0AI TO CDEMO-CT00-TRN-SELECTED
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelected.setValue(ws.cotrn00.cotrn0ai.trnid09i);
      // COB(176): WHEN SEL0010I OF COTRN0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cotrn00.cotrn0ai.sel0010i.isSpaces()
        && !ws.cotrn00.cotrn0ai.sel0010i.isLowValues()) {
      // COB(177): MOVE SEL0010I OF COTRN0AI TO CDEMO-CT00-TRN-SEL-FLG
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.setValue(ws.cotrn00.cotrn0ai.sel0010i);
      // COB(178): MOVE TRNID10I OF COTRN0AI TO CDEMO-CT00-TRN-SELECTED
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelected.setValue(ws.cotrn00.cotrn0ai.trnid10i);
      // COB(179): WHEN OTHER
    } else {
      // COB(180): MOVE SPACES   TO CDEMO-CT00-TRN-SEL-FLG
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.spaces();
      // COB(181): MOVE SPACES   TO CDEMO-CT00-TRN-SELECTED
      ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelected.spaces();
      // COB(182): END-EVALUATE
    }
    // COB(183): IF (CDEMO-CT00-TRN-SEL-FLG NOT = SPACES AND LOW-VALUES) AND
    // COB(183):    (CDEMO-CT00-TRN-SELECTED NOT = SPACES AND LOW-VALUES)
    if ((!ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.isSpaces()
            && !ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.isLowValues())
        && (!ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelected.isSpaces()
            && !ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelected.isLowValues())) {
      // COB(185): EVALUATE CDEMO-CT00-TRN-SEL-FLG
      // COB(186): WHEN 'S'
      if ((ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.equals("S"))
          // COB(187): WHEN 's'
          || ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnSelFlg.equals("s")) {
        // COB(188): MOVE 'COTRN01C'   TO CDEMO-TO-PROGRAM
        ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COTRN01C");
        // COB(189): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
        ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsVariables.wsTranid);
        // COB(190): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
        ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsVariables.wsPgmname);
        // COB(191): MOVE 0        TO CDEMO-PGM-CONTEXT
        ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.setValue(0);
        // COB(192): EXEC CICS
        // COB(192):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
        // COB(192):     COMMAREA(CARDDEMO-COMMAREA)
        // COB(192): END-EXEC
        try {
          supernaut
              .xctl(ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
              .commarea(ws.carddemoCommarea) //
              .exec();
        } catch (HandledConditionException e) {
          handleCommandCondition(e);
        }
        // COB(196): WHEN OTHER
      } else {
        // COB(198): MOVE
        // COB(198): 'Invalid selection. Valid value is S' TO
        // COB(198):                 WS-MESSAGE
        //                 SET TRANSACT-EOF TO TRUE
        ws.wsVariables.wsMessage.setString("Invalid selection. Valid value is S");
        // COB(201): MOVE -1       TO TRNIDINL OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnidinl.setValue(-1);
        // COB(203): END-EVALUATE
        //                 PERFORM SEND-TRNLST-SCREEN
      }
      // COB(204): END-IF
    }
    // COB(206): IF TRNIDINI OF COTRN0AI = SPACES OR LOW-VALUES
    if (ws.cotrn00.cotrn0ai.trnidini.isSpaces() || ws.cotrn00.cotrn0ai.trnidini.isLowValues()) {
      // COB(207): MOVE LOW-VALUES TO TRAN-ID
      ws.cvtra05y.tranRecord.tranId.lowValues();
      // COB(208): ELSE
    } else {
      // COB(209): IF TRNIDINI  OF COTRN0AI IS NUMERIC
      if (ws.cotrn00.cotrn0ai.trnidini.isNumeric()) {
        // COB(210): MOVE TRNIDINI  OF COTRN0AI    TO TRAN-ID
        ws.cvtra05y.tranRecord.tranId.setValue(ws.cotrn00.cotrn0ai.trnidini);
        // COB(211): ELSE
      } else {
        // COB(212): MOVE 'Y'                       TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(213): MOVE
        // COB(213): 'Tran ID must be Numeric ...' TO
        // COB(213):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Tran ID must be Numeric ...");
        // COB(216): MOVE -1                 TO TRNIDINL OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnidinl.setValue(-1);
        // COB(217): PERFORM SEND-TRNLST-SCREEN
        sendTrnlstScreen();
        // COB(218): END-IF
      }
      // COB(219): END-IF
    }
    // COB(221): MOVE -1       TO TRNIDINL OF COTRN0AI
    ws.cotrn00.cotrn0ai.trnidinl.setValue(-1);
    // COB(224): MOVE 0       TO CDEMO-CT00-PAGE-NUM
    //
    //
    ws.carddemoCommarea.cdemoCt00Info.cdemoCt00PageNum.setValue(0);
    // COB(225): PERFORM PROCESS-PAGE-FORWARD
    processPageForward();
    // COB(227): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(228): MOVE SPACE   TO TRNIDINO  OF COTRN0AO
      ws.cotrn00.cotrn0ao.trnidino.spaces();
      // COB(229): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      PROCESS-PF7-KEY
   *----------------------------------------------------------------*/
  protected void processPf7Key() {
    // COB(236): IF CDEMO-CT00-TRNID-FIRST = SPACES OR LOW-VALUES
    if (ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnidFirst.isSpaces()
        || ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnidFirst.isLowValues()) {
      // COB(237): MOVE LOW-VALUES TO TRAN-ID
      ws.cvtra05y.tranRecord.tranId.lowValues();
      // COB(238): ELSE
    } else {
      // COB(239): MOVE CDEMO-CT00-TRNID-FIRST TO TRAN-ID
      ws.cvtra05y.tranRecord.tranId.setValue(ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnidFirst);
      // COB(240): END-IF
    }
    // COB(242): SET NEXT-PAGE-YES TO TRUE
    ws.carddemoCommarea.cdemoCt00Info.setNextPageYes(true);
    // COB(243): MOVE -1       TO TRNIDINL OF COTRN0AI
    ws.cotrn00.cotrn0ai.trnidinl.setValue(-1);
    // COB(245): IF CDEMO-CT00-PAGE-NUM > 1
    if (ws.carddemoCommarea.cdemoCt00Info.cdemoCt00PageNum.greaterThan(1)) {
      // COB(246): PERFORM PROCESS-PAGE-BACKWARD
      processPageBackward();
      // COB(247): ELSE
    } else {
      // COB(248): MOVE 'You are already at the top of the page...' TO
      // COB(248):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("You are already at the top of the page...");
      // COB(250): SET SEND-ERASE-NO TO TRUE
      ws.wsVariables.setSendEraseNo(true);
      // COB(251): PERFORM SEND-TRNLST-SCREEN
      sendTrnlstScreen();
      // COB(252): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      PROCESS-PF8-KEY
   *----------------------------------------------------------------*/
  protected void processPf8Key() {
    // COB(259): IF CDEMO-CT00-TRNID-LAST = SPACES OR LOW-VALUES
    if (ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnidLast.isSpaces()
        || ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnidLast.isLowValues()) {
      // COB(260): MOVE HIGH-VALUES TO TRAN-ID
      ws.cvtra05y.tranRecord.tranId.highValues();
      // COB(261): ELSE
    } else {
      // COB(262): MOVE CDEMO-CT00-TRNID-LAST TO TRAN-ID
      ws.cvtra05y.tranRecord.tranId.setValue(ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnidLast);
      // COB(263): END-IF
    }
    // COB(265): MOVE -1       TO TRNIDINL OF COTRN0AI
    ws.cotrn00.cotrn0ai.trnidinl.setValue(-1);
    // COB(267): IF NEXT-PAGE-YES
    if (ws.carddemoCommarea.cdemoCt00Info.nextPageYes()) {
      // COB(268): PERFORM PROCESS-PAGE-FORWARD
      processPageForward();
      // COB(269): ELSE
    } else {
      // COB(270): MOVE 'You are already at the bottom of the page...' TO
      // COB(270):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("You are already at the bottom of the page...");
      // COB(272): SET SEND-ERASE-NO TO TRUE
      ws.wsVariables.setSendEraseNo(true);
      // COB(273): PERFORM SEND-TRNLST-SCREEN
      sendTrnlstScreen();
      // COB(274): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      PROCESS-PAGE-FORWARD
   *----------------------------------------------------------------*/
  protected void processPageForward() {
    // COB(281): PERFORM STARTBR-TRANSACT-FILE
    startbrTransactFile();
    // COB(283): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(285): IF EIBAID NOT = DFHENTER AND DFHPF7 AND DFHPF3
      if (!dfheiblk.getEibaid().equals(Dfhaid.DFHENTER)
          && !dfheiblk.getEibaid().equals(Dfhaid.DFHPF7)
          && !dfheiblk.getEibaid().equals(Dfhaid.DFHPF3)) {
        // COB(286): PERFORM READNEXT-TRANSACT-FILE
        readnextTransactFile();
        // COB(287): END-IF
      }
      // COB(289): IF TRANSACT-NOT-EOF AND ERR-FLG-OFF
      if (ws.wsVariables.transactNotEof() && ws.wsVariables.errFlgOff()) {
        // COB(290): PERFORM VARYING WS-IDX FROM 1 BY 1 UNTIL WS-IDX > 10
        ws.wsVariables.wsIdx.setValue(1);
        for (; !ws.wsVariables.wsIdx.greaterThan(10); ws.wsVariables.wsIdx.add(1)) {
          // COB(291): PERFORM INITIALIZE-TRAN-DATA
          initializeTranData();
          // COB(292): END-PERFORM
        }
        // COB(293): END-IF
      }
      // COB(295): MOVE 1             TO  WS-IDX
      ws.wsVariables.wsIdx.setValue(1);
      // COB(297): PERFORM UNTIL WS-IDX >= 11 OR TRANSACT-EOF OR ERR-FLG-ON
      while (!(ws.wsVariables.wsIdx.greaterEqualThan(11)
          || ws.wsVariables.transactEof()
          || ws.wsVariables.errFlgOn())) {
        // COB(298): PERFORM READNEXT-TRANSACT-FILE
        readnextTransactFile();
        // COB(299): IF TRANSACT-NOT-EOF AND ERR-FLG-OFF
        if (ws.wsVariables.transactNotEof() && ws.wsVariables.errFlgOff()) {
          // COB(300): PERFORM POPULATE-TRAN-DATA
          populateTranData();
          // COB(301): COMPUTE WS-IDX = WS-IDX + 1
          ws.wsVariables.wsIdx.setValue(ws.wsVariables.wsIdx.getValue().add(new BigDecimal("1")));
          // COB(302): END-IF
        }
        // COB(303): END-PERFORM
      }
      // COB(305): IF TRANSACT-NOT-EOF AND ERR-FLG-OFF
      if (ws.wsVariables.transactNotEof() && ws.wsVariables.errFlgOff()) {
        // COB(306): COMPUTE CDEMO-CT00-PAGE-NUM =
        // COB(306):         CDEMO-CT00-PAGE-NUM + 1
        ws.carddemoCommarea.cdemoCt00Info.cdemoCt00PageNum.setValue(
            ws.carddemoCommarea.cdemoCt00Info.cdemoCt00PageNum.getValue().add(new BigDecimal("1")));
        // COB(308): PERFORM READNEXT-TRANSACT-FILE
        readnextTransactFile();
        // COB(309): IF TRANSACT-NOT-EOF AND ERR-FLG-OFF
        if (ws.wsVariables.transactNotEof() && ws.wsVariables.errFlgOff()) {
          // COB(310): SET NEXT-PAGE-YES TO TRUE
          ws.carddemoCommarea.cdemoCt00Info.setNextPageYes(true);
          // COB(311): ELSE
        } else {
          // COB(312): SET NEXT-PAGE-NO TO TRUE
          ws.carddemoCommarea.cdemoCt00Info.setNextPageNo(true);
          // COB(313): END-IF
        }
        // COB(314): ELSE
      } else {
        // COB(315): SET NEXT-PAGE-NO TO TRUE
        ws.carddemoCommarea.cdemoCt00Info.setNextPageNo(true);
        // COB(316): IF WS-IDX > 1
        if (ws.wsVariables.wsIdx.greaterThan(1)) {
          // COB(317): COMPUTE CDEMO-CT00-PAGE-NUM = CDEMO-CT00-PAGE-NUM
          // COB(317):  + 1
          ws.carddemoCommarea.cdemoCt00Info.cdemoCt00PageNum.setValue(
              ws.carddemoCommarea
                  .cdemoCt00Info
                  .cdemoCt00PageNum
                  .getValue()
                  .add(new BigDecimal("1")));
          // COB(319): END-IF
        }
        // COB(320): END-IF
      }
      // COB(322): PERFORM ENDBR-TRANSACT-FILE
      endbrTransactFile();
      // COB(324): MOVE CDEMO-CT00-PAGE-NUM TO PAGENUMI  OF COTRN0AI
      ws.cotrn00.cotrn0ai.pagenumi.setValue(ws.carddemoCommarea.cdemoCt00Info.cdemoCt00PageNum);
      // COB(325): MOVE SPACE   TO TRNIDINO  OF COTRN0AO
      ws.cotrn00.cotrn0ao.trnidino.spaces();
      // COB(326): PERFORM SEND-TRNLST-SCREEN
      sendTrnlstScreen();
      // COB(328): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      PROCESS-PAGE-BACKWARD
   *----------------------------------------------------------------*/
  protected void processPageBackward() {
    // COB(335): PERFORM STARTBR-TRANSACT-FILE
    startbrTransactFile();
    // COB(337): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(339): IF EIBAID NOT = DFHENTER AND DFHPF8
      if (!dfheiblk.getEibaid().equals(Dfhaid.DFHENTER)
          && !dfheiblk.getEibaid().equals(Dfhaid.DFHPF8)) {
        // COB(340): PERFORM READPREV-TRANSACT-FILE
        readprevTransactFile();
        // COB(341): END-IF
      }
      // COB(343): IF TRANSACT-NOT-EOF AND ERR-FLG-OFF
      if (ws.wsVariables.transactNotEof() && ws.wsVariables.errFlgOff()) {
        // COB(344): PERFORM VARYING WS-IDX FROM 1 BY 1 UNTIL WS-IDX > 10
        ws.wsVariables.wsIdx.setValue(1);
        for (; !ws.wsVariables.wsIdx.greaterThan(10); ws.wsVariables.wsIdx.add(1)) {
          // COB(345): PERFORM INITIALIZE-TRAN-DATA
          initializeTranData();
          // COB(346): END-PERFORM
        }
        // COB(347): END-IF
      }
      // COB(349): MOVE 10          TO  WS-IDX
      ws.wsVariables.wsIdx.setValue(10);
      // COB(351): PERFORM UNTIL WS-IDX <= 0 OR TRANSACT-EOF OR ERR-FLG-ON
      while (!(ws.wsVariables.wsIdx.lessEqualThan(0)
          || ws.wsVariables.transactEof()
          || ws.wsVariables.errFlgOn())) {
        // COB(352): PERFORM READPREV-TRANSACT-FILE
        readprevTransactFile();
        // COB(353): IF TRANSACT-NOT-EOF AND ERR-FLG-OFF
        if (ws.wsVariables.transactNotEof() && ws.wsVariables.errFlgOff()) {
          // COB(354): PERFORM POPULATE-TRAN-DATA
          populateTranData();
          // COB(355): COMPUTE WS-IDX = WS-IDX - 1
          ws.wsVariables.wsIdx.setValue(
              ws.wsVariables.wsIdx.getValue().subtract(new BigDecimal("1")));
          // COB(356): END-IF
        }
        // COB(357): END-PERFORM
      }
      // COB(359): IF TRANSACT-NOT-EOF AND ERR-FLG-OFF
      if (ws.wsVariables.transactNotEof() && ws.wsVariables.errFlgOff()) {
        // COB(360): PERFORM READPREV-TRANSACT-FILE
        readprevTransactFile();
        // COB(361): IF NEXT-PAGE-YES
        if (ws.carddemoCommarea.cdemoCt00Info.nextPageYes()) {
          // COB(362): IF TRANSACT-NOT-EOF AND ERR-FLG-OFF AND
          // COB(362):    CDEMO-CT00-PAGE-NUM > 1
          if (ws.wsVariables.transactNotEof()
              && ws.wsVariables.errFlgOff()
              && ws.carddemoCommarea.cdemoCt00Info.cdemoCt00PageNum.greaterThan(1)) {
            // COB(364): SUBTRACT 1 FROM CDEMO-CT00-PAGE-NUM
            ws.carddemoCommarea.cdemoCt00Info.cdemoCt00PageNum.subtract(1);
            // COB(365): ELSE
          } else {
            // COB(366): MOVE 1 TO CDEMO-CT00-PAGE-NUM
            ws.carddemoCommarea.cdemoCt00Info.cdemoCt00PageNum.setValue(1);
            // COB(367): END-IF
          }
          // COB(368): END-IF
        }
        // COB(369): END-IF
      }
      // COB(371): PERFORM ENDBR-TRANSACT-FILE
      endbrTransactFile();
      // COB(373): MOVE CDEMO-CT00-PAGE-NUM TO PAGENUMI  OF COTRN0AI
      ws.cotrn00.cotrn0ai.pagenumi.setValue(ws.carddemoCommarea.cdemoCt00Info.cdemoCt00PageNum);
      // COB(374): PERFORM SEND-TRNLST-SCREEN
      sendTrnlstScreen();
      // COB(376): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      POPULATE-TRAN-DATA
   *----------------------------------------------------------------*/
  protected void populateTranData() {
    // COB(383): MOVE TRAN-AMT                  TO WS-TRAN-AMT
    ws.wsVariables.wsTranAmt.setValue(ws.cvtra05y.tranRecord.tranAmt);
    // COB(384): MOVE TRAN-ORIG-TS              TO WS-TIMESTAMP
    ws.csdat01y.wsDateTime.wsTimestamp.setValue(ws.cvtra05y.tranRecord.tranOrigTs);
    // COB(385): MOVE WS-TIMESTAMP-DT-YYYY(3:2) TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsTimestamp.wsTimestampDtYyyy, 2, 0, 2);
    // COB(386): MOVE WS-TIMESTAMP-DT-MM        TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsTimestamp.wsTimestampDtMm);
    // COB(387): MOVE WS-TIMESTAMP-DT-DD        TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsTimestamp.wsTimestampDtDd);
    // COB(388): MOVE WS-CURDATE-MM-DD-YY       TO WS-TRAN-DATE
    ws.wsVariables.wsTranDate.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(390): EVALUATE WS-IDX
    switch (ws.wsVariables.wsIdx.getInt()) {
        // COB(391): WHEN 1
      case 1:
        // COB(392): MOVE TRAN-ID    TO TRNID01I OF COTRN0AI
        // COB(392):                       CDEMO-CT00-TRNID-FIRST
        ws.cotrn00.cotrn0ai.trnid01i.setValue(ws.cvtra05y.tranRecord.tranId);
        ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnidFirst.setValue(
            ws.cvtra05y.tranRecord.tranId);
        // COB(394): MOVE WS-TRAN-DATE TO TDATE01I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate01i.setValue(ws.wsVariables.wsTranDate);
        // COB(395): MOVE TRAN-DESC TO TDESC01I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc01i.setValue(ws.cvtra05y.tranRecord.tranDesc);
        // COB(396): MOVE WS-TRAN-AMT  TO TAMT001I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt001i.setValue(ws.wsVariables.wsTranAmt);
        // COB(397): WHEN 2
        break;
      case 2:
        // COB(398): MOVE TRAN-ID    TO TRNID02I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid02i.setValue(ws.cvtra05y.tranRecord.tranId);
        // COB(399): MOVE WS-TRAN-DATE TO TDATE02I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate02i.setValue(ws.wsVariables.wsTranDate);
        // COB(400): MOVE TRAN-DESC TO TDESC02I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc02i.setValue(ws.cvtra05y.tranRecord.tranDesc);
        // COB(401): MOVE WS-TRAN-AMT  TO TAMT002I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt002i.setValue(ws.wsVariables.wsTranAmt);
        // COB(402): WHEN 3
        break;
      case 3:
        // COB(403): MOVE TRAN-ID    TO TRNID03I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid03i.setValue(ws.cvtra05y.tranRecord.tranId);
        // COB(404): MOVE WS-TRAN-DATE TO TDATE03I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate03i.setValue(ws.wsVariables.wsTranDate);
        // COB(405): MOVE TRAN-DESC TO TDESC03I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc03i.setValue(ws.cvtra05y.tranRecord.tranDesc);
        // COB(406): MOVE WS-TRAN-AMT  TO TAMT003I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt003i.setValue(ws.wsVariables.wsTranAmt);
        // COB(407): WHEN 4
        break;
      case 4:
        // COB(408): MOVE TRAN-ID    TO TRNID04I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid04i.setValue(ws.cvtra05y.tranRecord.tranId);
        // COB(409): MOVE WS-TRAN-DATE TO TDATE04I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate04i.setValue(ws.wsVariables.wsTranDate);
        // COB(410): MOVE TRAN-DESC TO TDESC04I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc04i.setValue(ws.cvtra05y.tranRecord.tranDesc);
        // COB(411): MOVE WS-TRAN-AMT  TO TAMT004I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt004i.setValue(ws.wsVariables.wsTranAmt);
        // COB(412): WHEN 5
        break;
      case 5:
        // COB(413): MOVE TRAN-ID    TO TRNID05I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid05i.setValue(ws.cvtra05y.tranRecord.tranId);
        // COB(414): MOVE WS-TRAN-DATE TO TDATE05I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate05i.setValue(ws.wsVariables.wsTranDate);
        // COB(415): MOVE TRAN-DESC TO TDESC05I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc05i.setValue(ws.cvtra05y.tranRecord.tranDesc);
        // COB(416): MOVE WS-TRAN-AMT  TO TAMT005I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt005i.setValue(ws.wsVariables.wsTranAmt);
        // COB(417): WHEN 6
        break;
      case 6:
        // COB(418): MOVE TRAN-ID    TO TRNID06I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid06i.setValue(ws.cvtra05y.tranRecord.tranId);
        // COB(419): MOVE WS-TRAN-DATE TO TDATE06I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate06i.setValue(ws.wsVariables.wsTranDate);
        // COB(420): MOVE TRAN-DESC TO TDESC06I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc06i.setValue(ws.cvtra05y.tranRecord.tranDesc);
        // COB(421): MOVE WS-TRAN-AMT  TO TAMT006I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt006i.setValue(ws.wsVariables.wsTranAmt);
        // COB(422): WHEN 7
        break;
      case 7:
        // COB(423): MOVE TRAN-ID    TO TRNID07I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid07i.setValue(ws.cvtra05y.tranRecord.tranId);
        // COB(424): MOVE WS-TRAN-DATE TO TDATE07I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate07i.setValue(ws.wsVariables.wsTranDate);
        // COB(425): MOVE TRAN-DESC TO TDESC07I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc07i.setValue(ws.cvtra05y.tranRecord.tranDesc);
        // COB(426): MOVE WS-TRAN-AMT  TO TAMT007I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt007i.setValue(ws.wsVariables.wsTranAmt);
        // COB(427): WHEN 8
        break;
      case 8:
        // COB(428): MOVE TRAN-ID    TO TRNID08I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid08i.setValue(ws.cvtra05y.tranRecord.tranId);
        // COB(429): MOVE WS-TRAN-DATE TO TDATE08I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate08i.setValue(ws.wsVariables.wsTranDate);
        // COB(430): MOVE TRAN-DESC TO TDESC08I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc08i.setValue(ws.cvtra05y.tranRecord.tranDesc);
        // COB(431): MOVE WS-TRAN-AMT  TO TAMT008I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt008i.setValue(ws.wsVariables.wsTranAmt);
        // COB(432): WHEN 9
        break;
      case 9:
        // COB(433): MOVE TRAN-ID    TO TRNID09I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid09i.setValue(ws.cvtra05y.tranRecord.tranId);
        // COB(434): MOVE WS-TRAN-DATE TO TDATE09I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate09i.setValue(ws.wsVariables.wsTranDate);
        // COB(435): MOVE TRAN-DESC TO TDESC09I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc09i.setValue(ws.cvtra05y.tranRecord.tranDesc);
        // COB(436): MOVE WS-TRAN-AMT  TO TAMT009I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt009i.setValue(ws.wsVariables.wsTranAmt);
        // COB(437): WHEN 10
        break;
      case 10:
        // COB(438): MOVE TRAN-ID    TO TRNID10I OF COTRN0AI
        // COB(438):                       CDEMO-CT00-TRNID-LAST
        ws.cotrn00.cotrn0ai.trnid10i.setValue(ws.cvtra05y.tranRecord.tranId);
        ws.carddemoCommarea.cdemoCt00Info.cdemoCt00TrnidLast.setValue(
            ws.cvtra05y.tranRecord.tranId);
        // COB(440): MOVE WS-TRAN-DATE TO TDATE10I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate10i.setValue(ws.wsVariables.wsTranDate);
        // COB(441): MOVE TRAN-DESC TO TDESC10I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc10i.setValue(ws.cvtra05y.tranRecord.tranDesc);
        // COB(442): MOVE WS-TRAN-AMT  TO TAMT010I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt010i.setValue(ws.wsVariables.wsTranAmt);
        // COB(443): WHEN OTHER
        break;
      default:
        // COB(444): CONTINUE
        // do nothing
        // COB(445): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      INITIALIZE-TRAN-DATA
   *----------------------------------------------------------------*/
  protected void initializeTranData() {
    // COB(452): EVALUATE WS-IDX
    switch (ws.wsVariables.wsIdx.getInt()) {
        // COB(453): WHEN 1
      case 1:
        // COB(454): MOVE SPACES TO TRNID01I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid01i.spaces();
        // COB(455): MOVE SPACES TO TDATE01I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate01i.spaces();
        // COB(456): MOVE SPACES TO TDESC01I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc01i.spaces();
        // COB(457): MOVE SPACES TO TAMT001I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt001i.spaces();
        // COB(458): WHEN 2
        break;
      case 2:
        // COB(459): MOVE SPACES TO TRNID02I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid02i.spaces();
        // COB(460): MOVE SPACES TO TDATE02I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate02i.spaces();
        // COB(461): MOVE SPACES TO TDESC02I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc02i.spaces();
        // COB(462): MOVE SPACES TO TAMT002I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt002i.spaces();
        // COB(463): WHEN 3
        break;
      case 3:
        // COB(464): MOVE SPACES TO TRNID03I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid03i.spaces();
        // COB(465): MOVE SPACES TO TDATE03I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate03i.spaces();
        // COB(466): MOVE SPACES TO TDESC03I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc03i.spaces();
        // COB(467): MOVE SPACES TO TAMT003I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt003i.spaces();
        // COB(468): WHEN 4
        break;
      case 4:
        // COB(469): MOVE SPACES TO TRNID04I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid04i.spaces();
        // COB(470): MOVE SPACES TO TDATE04I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate04i.spaces();
        // COB(471): MOVE SPACES TO TDESC04I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc04i.spaces();
        // COB(472): MOVE SPACES TO TAMT004I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt004i.spaces();
        // COB(473): WHEN 5
        break;
      case 5:
        // COB(474): MOVE SPACES TO TRNID05I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid05i.spaces();
        // COB(475): MOVE SPACES TO TDATE05I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate05i.spaces();
        // COB(476): MOVE SPACES TO TDESC05I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc05i.spaces();
        // COB(477): MOVE SPACES TO TAMT005I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt005i.spaces();
        // COB(478): WHEN 6
        break;
      case 6:
        // COB(479): MOVE SPACES TO TRNID06I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid06i.spaces();
        // COB(480): MOVE SPACES TO TDATE06I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate06i.spaces();
        // COB(481): MOVE SPACES TO TDESC06I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc06i.spaces();
        // COB(482): MOVE SPACES TO TAMT006I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt006i.spaces();
        // COB(483): WHEN 7
        break;
      case 7:
        // COB(484): MOVE SPACES TO TRNID07I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid07i.spaces();
        // COB(485): MOVE SPACES TO TDATE07I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate07i.spaces();
        // COB(486): MOVE SPACES TO TDESC07I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc07i.spaces();
        // COB(487): MOVE SPACES TO TAMT007I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt007i.spaces();
        // COB(488): WHEN 8
        break;
      case 8:
        // COB(489): MOVE SPACES TO TRNID08I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid08i.spaces();
        // COB(490): MOVE SPACES TO TDATE08I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate08i.spaces();
        // COB(491): MOVE SPACES TO TDESC08I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc08i.spaces();
        // COB(492): MOVE SPACES TO TAMT008I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt008i.spaces();
        // COB(493): WHEN 9
        break;
      case 9:
        // COB(494): MOVE SPACES TO TRNID09I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid09i.spaces();
        // COB(495): MOVE SPACES TO TDATE09I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate09i.spaces();
        // COB(496): MOVE SPACES TO TDESC09I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc09i.spaces();
        // COB(497): MOVE SPACES TO TAMT009I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt009i.spaces();
        // COB(498): WHEN 10
        break;
      case 10:
        // COB(499): MOVE SPACES TO TRNID10I OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnid10i.spaces();
        // COB(500): MOVE SPACES TO TDATE10I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdate10i.spaces();
        // COB(501): MOVE SPACES TO TDESC10I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tdesc10i.spaces();
        // COB(502): MOVE SPACES TO TAMT010I OF COTRN0AI
        ws.cotrn00.cotrn0ai.tamt010i.spaces();
        // COB(503): WHEN OTHER
        break;
      default:
        // COB(504): CONTINUE
        // do nothing
        // COB(505): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RETURN-TO-PREV-SCREEN
   *----------------------------------------------------------------*/
  protected void returnToPrevScreen() {
    // COB(512): IF CDEMO-TO-PROGRAM = LOW-VALUES OR SPACES
    if (ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isLowValues()
        || ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isSpaces()) {
      // COB(513): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(514): END-IF
    }
    // COB(515): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsVariables.wsTranid);
    // COB(516): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsVariables.wsPgmname);
    // COB(517): MOVE ZEROS        TO CDEMO-PGM-CONTEXT
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.zeros();
    // COB(518): EXEC CICS
    // COB(518):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
    // COB(518):     COMMAREA(CARDDEMO-COMMAREA)
    // COB(518): END-EXEC.
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
   *
   *----------------------------------------------------------------*
   *                      SEND-TRNLST-SCREEN
   *----------------------------------------------------------------*/
  protected void sendTrnlstScreen() {
    // COB(529): PERFORM POPULATE-HEADER-INFO
    populateHeaderInfo();
    // COB(531): MOVE WS-MESSAGE TO ERRMSGO OF COTRN0AO
    ws.cotrn00.cotrn0ao.errmsgo.setValue(ws.wsVariables.wsMessage);
    // COB(533): IF SEND-ERASE-YES
    if (ws.wsVariables.sendEraseYes()) {
      // COB(534): EXEC CICS SEND
      // COB(534):           MAP('COTRN0A')
      // COB(534):           MAPSET('COTRN00')
      // COB(534):           FROM(COTRN0AO)
      // COB(534):           ERASE
      // COB(534):           CURSOR
      // COB(534): END-EXEC
      try {
        supernaut
            .sendMap("COTRN0A") //
            .mapset("COTRN00") //
            .from(ws.cotrn00.cotrn0ao) //
            .erase() //
            .cursor() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(541): ELSE
    } else {
      // COB(542): EXEC CICS SEND
      // COB(542):           MAP('COTRN0A')
      // COB(542):           MAPSET('COTRN00')
      // COB(542):           FROM(COTRN0AO)
      // COB(542):       *                  ERASE
      // COB(542):           CURSOR
      // COB(542): END-EXEC
      try {
        supernaut
            .sendMap("COTRN0A") //
            .mapset("COTRN00") //
            .from(ws.cotrn00.cotrn0ao) //
            .cursor() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(549): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RECEIVE-TRNLST-SCREEN
   *----------------------------------------------------------------*/
  protected void receiveTrnlstScreen() {
    // COB(556): EXEC CICS RECEIVE
    // COB(556):           MAP('COTRN0A')
    // COB(556):           MAPSET('COTRN00')
    // COB(556):           INTO(COTRN0AI)
    // COB(556):           RESP(WS-RESP-CD)
    // COB(556):           RESP2(WS-REAS-CD)
    // COB(556): END-EXEC.
    supernaut
        .receiveMap("COTRN0A") //
        .mapset("COTRN00") //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cotrn00.cotrn0ai) //
        .exec();
  }

  /***
   *----------------------------------------------------------------*
   *                      POPULATE-HEADER-INFO
   *----------------------------------------------------------------*/
  protected void populateHeaderInfo() {
    // COB(569): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(571): MOVE CCDA-TITLE01           TO TITLE01O OF COTRN0AO
    ws.cotrn00.cotrn0ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(572): MOVE CCDA-TITLE02           TO TITLE02O OF COTRN0AO
    ws.cotrn00.cotrn0ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(573): MOVE WS-TRANID              TO TRNNAMEO OF COTRN0AO
    ws.cotrn00.cotrn0ao.trnnameo.setValue(ws.wsVariables.wsTranid);
    // COB(574): MOVE WS-PGMNAME             TO PGMNAMEO OF COTRN0AO
    ws.cotrn00.cotrn0ao.pgmnameo.setValue(ws.wsVariables.wsPgmname);
    // COB(576): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(577): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(578): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(580): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF COTRN0AO
    ws.cotrn00.cotrn0ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(582): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(583): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(584): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(586): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF COTRN0AO.
    ws.cotrn00.cotrn0ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***
   *----------------------------------------------------------------*
   *                      STARTBR-TRANSACT-FILE
   *----------------------------------------------------------------*/
  protected void startbrTransactFile() {
    // COB(593): EXEC CICS STARTBR
    // COB(593):      DATASET   (WS-TRANSACT-FILE)
    // COB(593):      RIDFLD    (TRAN-ID)
    // COB(593):      KEYLENGTH (LENGTH OF TRAN-ID)
    // COB(593):       *         GTEQ
    // COB(593):      RESP      (WS-RESP-CD)
    // COB(593):      RESP2     (WS-REAS-CD)
    // COB(593): END-EXEC.
    supernaut
        .startbr(ws.wsVariables.wsTransactFile.getValue()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .ridfld(ws.cvtra05y.tranRecord.tranId) //
        .keylength(ws.cvtra05y.tranRecord.tranId.length()) //
        .exec();
    // COB(602): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(603): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(604): CONTINUE
        // do nothing
        // COB(605): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(606): CONTINUE
        // do nothing
        // COB(607): SET TRANSACT-EOF TO TRUE
        ws.wsVariables.setTransactEof(true);
        // COB(608): MOVE 'You are at the top of the page...' TO
        // COB(608):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("You are at the top of the page...");
        // COB(610): MOVE -1       TO TRNIDINL OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnidinl.setValue(-1);
        // COB(611): PERFORM SEND-TRNLST-SCREEN
        sendTrnlstScreen();
        // COB(612): WHEN OTHER
        break;
      default:
        // COB(613): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(614): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(615): MOVE 'Unable to lookup transaction...' TO
        // COB(615):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup transaction...");
        // COB(617): MOVE -1       TO TRNIDINL OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnidinl.setValue(-1);
        // COB(618): PERFORM SEND-TRNLST-SCREEN
        sendTrnlstScreen();
        // COB(619): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      READNEXT-TRANSACT-FILE
   *----------------------------------------------------------------*/
  protected void readnextTransactFile() {
    // COB(626): EXEC CICS READNEXT
    // COB(626):      DATASET   (WS-TRANSACT-FILE)
    // COB(626):      INTO      (TRAN-RECORD)
    // COB(626):      LENGTH    (LENGTH OF TRAN-RECORD)
    // COB(626):      RIDFLD    (TRAN-ID)
    // COB(626):      KEYLENGTH (LENGTH OF TRAN-ID)
    // COB(626):      RESP      (WS-RESP-CD)
    // COB(626):      RESP2     (WS-REAS-CD)
    // COB(626): END-EXEC.
    supernaut
        .readNext(ws.wsVariables.wsTransactFile.getValue()) //
        .length(ws.cvtra05y.tranRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cvtra05y.tranRecord) //
        .ridfld(ws.cvtra05y.tranRecord.tranId) //
        .keylength(ws.cvtra05y.tranRecord.tranId.length()) //
        .exec();
    // COB(636): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(637): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(638): CONTINUE
        // do nothing
        // COB(639): WHEN DFHRESP(ENDFILE)
        break;
      case Dfhresp.ENDFILE:
        // COB(640): CONTINUE
        // do nothing
        // COB(641): SET TRANSACT-EOF TO TRUE
        ws.wsVariables.setTransactEof(true);
        // COB(642): MOVE 'You have reached the bottom of the page...' TO
        // COB(642):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("You have reached the bottom of the page...");
        // COB(644): MOVE -1       TO TRNIDINL OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnidinl.setValue(-1);
        // COB(645): PERFORM SEND-TRNLST-SCREEN
        sendTrnlstScreen();
        // COB(646): WHEN OTHER
        break;
      default:
        // COB(647): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(648): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(649): MOVE 'Unable to lookup transaction...' TO
        // COB(649):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup transaction...");
        // COB(651): MOVE -1       TO TRNIDINL OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnidinl.setValue(-1);
        // COB(652): PERFORM SEND-TRNLST-SCREEN
        sendTrnlstScreen();
        // COB(653): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      READPREV-TRANSACT-FILE
   *----------------------------------------------------------------*/
  protected void readprevTransactFile() {
    // COB(660): EXEC CICS READPREV
    // COB(660):      DATASET   (WS-TRANSACT-FILE)
    // COB(660):      INTO      (TRAN-RECORD)
    // COB(660):      LENGTH    (LENGTH OF TRAN-RECORD)
    // COB(660):      RIDFLD    (TRAN-ID)
    // COB(660):      KEYLENGTH (LENGTH OF TRAN-ID)
    // COB(660):      RESP      (WS-RESP-CD)
    // COB(660):      RESP2     (WS-REAS-CD)
    // COB(660): END-EXEC.
    supernaut
        .readPrev(ws.wsVariables.wsTransactFile.getValue()) //
        .length(ws.cvtra05y.tranRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cvtra05y.tranRecord) //
        .ridfld(ws.cvtra05y.tranRecord.tranId) //
        .keylength(ws.cvtra05y.tranRecord.tranId.length()) //
        .exec();
    // COB(670): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(671): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(672): CONTINUE
        // do nothing
        // COB(673): WHEN DFHRESP(ENDFILE)
        break;
      case Dfhresp.ENDFILE:
        // COB(674): CONTINUE
        // do nothing
        // COB(675): SET TRANSACT-EOF TO TRUE
        ws.wsVariables.setTransactEof(true);
        // COB(676): MOVE 'You have reached the top of the page...' TO
        // COB(676):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("You have reached the top of the page...");
        // COB(678): MOVE -1       TO TRNIDINL OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnidinl.setValue(-1);
        // COB(679): PERFORM SEND-TRNLST-SCREEN
        sendTrnlstScreen();
        // COB(680): WHEN OTHER
        break;
      default:
        // COB(681): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(682): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(683): MOVE 'Unable to lookup transaction...' TO
        // COB(683):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup transaction...");
        // COB(685): MOVE -1       TO TRNIDINL OF COTRN0AI
        ws.cotrn00.cotrn0ai.trnidinl.setValue(-1);
        // COB(686): PERFORM SEND-TRNLST-SCREEN
        sendTrnlstScreen();
        // COB(687): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      ENDBR-TRANSACT-FILE
   *----------------------------------------------------------------*/
  protected void endbrTransactFile() {
    // COB(694): EXEC CICS ENDBR
    // COB(694):      DATASET   (WS-TRANSACT-FILE)
    // COB(694): END-EXEC.
    try {
      supernaut
          .endbr(ws.wsVariables.wsTransactFile.getValue()) //
          .exec();
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
