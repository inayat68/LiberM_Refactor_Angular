package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cousr00c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;
import java.math.BigDecimal;

public class COUSR00C extends AbstractOnlineProgram {

  @ProgramStorage final Cousr00cWorking ws = new Cousr00cWorking();

  // Linkage
  public static class Cousr00cLinkage extends NGroup {
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

  final Cousr00cLinkage params = new Cousr00cLinkage();

  public COUSR00C(Context supernaut) {
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
    // COB(100): SET ERR-FLG-OFF TO TRUE
    ws.wsVariables.setErrFlgOff(true);
    // COB(101): SET USER-SEC-NOT-EOF TO TRUE
    ws.wsVariables.setUserSecNotEof(true);
    // COB(102): SET NEXT-PAGE-NO TO TRUE
    ws.carddemoCommarea.cdemoCu00Info.setNextPageNo(true);
    // COB(103): SET SEND-ERASE-YES TO TRUE
    ws.wsVariables.setSendEraseYes(true);
    // COB(105): MOVE SPACES TO WS-MESSAGE
    // COB(105):                ERRMSGO OF COUSR0AO
    ws.wsVariables.wsMessage.spaces();
    ws.cousr00.cousr0ao.errmsgo.spaces();
    // COB(108): MOVE -1       TO USRIDINL OF COUSR0AI
    ws.cousr00.cousr0ai.usridinl.setValue(-1);
    // COB(110): IF EIBCALEN = 0
    if (dfheiblk.getEibcalen() == 0) {
      // COB(111): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(112): PERFORM RETURN-TO-PREV-SCREEN
      returnToPrevScreen();
      // COB(113): ELSE
    } else {
      // COB(114): MOVE DFHCOMMAREA(1:EIBCALEN) TO CARDDEMO-COMMAREA
      ws.carddemoCommarea.setValue(params.dfhcommarea, 0, dfheiblk.getEibcalen());
      // COB(115): IF NOT CDEMO-PGM-REENTER
      if (!ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
        // COB(116): SET CDEMO-PGM-REENTER    TO TRUE
        ws.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
        // COB(117): MOVE LOW-VALUES          TO COUSR0AO
        ws.cousr00.cousr0ao.lowValues();
        // COB(118): PERFORM PROCESS-ENTER-KEY
        processEnterKey();
        // COB(119): PERFORM SEND-USRLST-SCREEN
        sendUsrlstScreen();
        // COB(120): ELSE
      } else {
        // COB(121): PERFORM RECEIVE-USRLST-SCREEN
        receiveUsrlstScreen();
        // COB(122): EVALUATE EIBAID
        switch (dfheiblk.getEibaid()) {
            // COB(123): WHEN DFHENTER
          case DFHENTER:
            // COB(124): PERFORM PROCESS-ENTER-KEY
            processEnterKey();
            // COB(125): WHEN DFHPF3
            break;
          case DFHPF3:
            // COB(126): MOVE 'COADM01C' TO CDEMO-TO-PROGRAM
            ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COADM01C");
            // COB(127): PERFORM RETURN-TO-PREV-SCREEN
            returnToPrevScreen();
            // COB(128): WHEN DFHPF7
            break;
          case DFHPF7:
            // COB(129): PERFORM PROCESS-PF7-KEY
            processPf7Key();
            // COB(130): WHEN DFHPF8
            break;
          case DFHPF8:
            // COB(131): PERFORM PROCESS-PF8-KEY
            processPf8Key();
            // COB(132): WHEN OTHER
            break;
          default:
            // COB(133): MOVE 'Y'                       TO WS-ERR-FLG
            ws.wsVariables.wsErrFlg.setString("Y");
            // COB(134): MOVE -1       TO USRIDINL OF COUSR0AI
            ws.cousr00.cousr0ai.usridinl.setValue(-1);
            // COB(135): MOVE CCDA-MSG-INVALID-KEY      TO WS-MESSAGE
            ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgInvalidKey);
            // COB(136): PERFORM SEND-USRLST-SCREEN
            sendUsrlstScreen();
            // COB(137): END-EVALUATE
        }
        // COB(138): END-IF
      }
      // COB(139): END-IF
    }
    // COB(141): EXEC CICS RETURN
    // COB(141):           TRANSID (WS-TRANID)
    // COB(141):           COMMAREA (CARDDEMO-COMMAREA)
    // COB(141): END-EXEC.
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
    // COB(151): EVALUATE TRUE
    // COB(152): WHEN SEL0001I OF COUSR0AI NOT = SPACES AND LOW-VALUES
    if (!ws.cousr00.cousr0ai.sel0001i.isSpaces() && !ws.cousr00.cousr0ai.sel0001i.isLowValues()) {
      // COB(153): MOVE SEL0001I OF COUSR0AI TO CDEMO-CU00-USR-SEL-FLG
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.setValue(ws.cousr00.cousr0ai.sel0001i);
      // COB(154): MOVE USRID01I OF COUSR0AI TO CDEMO-CU00-USR-SELECTED
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelected.setValue(ws.cousr00.cousr0ai.usrid01i);
      // COB(155): WHEN SEL0002I OF COUSR0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cousr00.cousr0ai.sel0002i.isSpaces()
        && !ws.cousr00.cousr0ai.sel0002i.isLowValues()) {
      // COB(156): MOVE SEL0002I OF COUSR0AI TO CDEMO-CU00-USR-SEL-FLG
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.setValue(ws.cousr00.cousr0ai.sel0002i);
      // COB(157): MOVE USRID02I OF COUSR0AI TO CDEMO-CU00-USR-SELECTED
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelected.setValue(ws.cousr00.cousr0ai.usrid02i);
      // COB(158): WHEN SEL0003I OF COUSR0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cousr00.cousr0ai.sel0003i.isSpaces()
        && !ws.cousr00.cousr0ai.sel0003i.isLowValues()) {
      // COB(159): MOVE SEL0003I OF COUSR0AI TO CDEMO-CU00-USR-SEL-FLG
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.setValue(ws.cousr00.cousr0ai.sel0003i);
      // COB(160): MOVE USRID03I OF COUSR0AI TO CDEMO-CU00-USR-SELECTED
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelected.setValue(ws.cousr00.cousr0ai.usrid03i);
      // COB(161): WHEN SEL0004I OF COUSR0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cousr00.cousr0ai.sel0004i.isSpaces()
        && !ws.cousr00.cousr0ai.sel0004i.isLowValues()) {
      // COB(162): MOVE SEL0004I OF COUSR0AI TO CDEMO-CU00-USR-SEL-FLG
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.setValue(ws.cousr00.cousr0ai.sel0004i);
      // COB(163): MOVE USRID04I OF COUSR0AI TO CDEMO-CU00-USR-SELECTED
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelected.setValue(ws.cousr00.cousr0ai.usrid04i);
      // COB(164): WHEN SEL0005I OF COUSR0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cousr00.cousr0ai.sel0005i.isSpaces()
        && !ws.cousr00.cousr0ai.sel0005i.isLowValues()) {
      // COB(165): MOVE SEL0005I OF COUSR0AI TO CDEMO-CU00-USR-SEL-FLG
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.setValue(ws.cousr00.cousr0ai.sel0005i);
      // COB(166): MOVE USRID05I OF COUSR0AI TO CDEMO-CU00-USR-SELECTED
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelected.setValue(ws.cousr00.cousr0ai.usrid05i);
      // COB(167): WHEN SEL0006I OF COUSR0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cousr00.cousr0ai.sel0006i.isSpaces()
        && !ws.cousr00.cousr0ai.sel0006i.isLowValues()) {
      // COB(168): MOVE SEL0006I OF COUSR0AI TO CDEMO-CU00-USR-SEL-FLG
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.setValue(ws.cousr00.cousr0ai.sel0006i);
      // COB(169): MOVE USRID06I OF COUSR0AI TO CDEMO-CU00-USR-SELECTED
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelected.setValue(ws.cousr00.cousr0ai.usrid06i);
      // COB(170): WHEN SEL0007I OF COUSR0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cousr00.cousr0ai.sel0007i.isSpaces()
        && !ws.cousr00.cousr0ai.sel0007i.isLowValues()) {
      // COB(171): MOVE SEL0007I OF COUSR0AI TO CDEMO-CU00-USR-SEL-FLG
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.setValue(ws.cousr00.cousr0ai.sel0007i);
      // COB(172): MOVE USRID07I OF COUSR0AI TO CDEMO-CU00-USR-SELECTED
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelected.setValue(ws.cousr00.cousr0ai.usrid07i);
      // COB(173): WHEN SEL0008I OF COUSR0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cousr00.cousr0ai.sel0008i.isSpaces()
        && !ws.cousr00.cousr0ai.sel0008i.isLowValues()) {
      // COB(174): MOVE SEL0008I OF COUSR0AI TO CDEMO-CU00-USR-SEL-FLG
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.setValue(ws.cousr00.cousr0ai.sel0008i);
      // COB(175): MOVE USRID08I OF COUSR0AI TO CDEMO-CU00-USR-SELECTED
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelected.setValue(ws.cousr00.cousr0ai.usrid08i);
      // COB(176): WHEN SEL0009I OF COUSR0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cousr00.cousr0ai.sel0009i.isSpaces()
        && !ws.cousr00.cousr0ai.sel0009i.isLowValues()) {
      // COB(177): MOVE SEL0009I OF COUSR0AI TO CDEMO-CU00-USR-SEL-FLG
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.setValue(ws.cousr00.cousr0ai.sel0009i);
      // COB(178): MOVE USRID09I OF COUSR0AI TO CDEMO-CU00-USR-SELECTED
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelected.setValue(ws.cousr00.cousr0ai.usrid09i);
      // COB(179): WHEN SEL0010I OF COUSR0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cousr00.cousr0ai.sel0010i.isSpaces()
        && !ws.cousr00.cousr0ai.sel0010i.isLowValues()) {
      // COB(180): MOVE SEL0010I OF COUSR0AI TO CDEMO-CU00-USR-SEL-FLG
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.setValue(ws.cousr00.cousr0ai.sel0010i);
      // COB(181): MOVE USRID10I OF COUSR0AI TO CDEMO-CU00-USR-SELECTED
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelected.setValue(ws.cousr00.cousr0ai.usrid10i);
      // COB(182): WHEN OTHER
    } else {
      // COB(183): MOVE SPACES   TO CDEMO-CU00-USR-SEL-FLG
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.spaces();
      // COB(184): MOVE SPACES   TO CDEMO-CU00-USR-SELECTED
      ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelected.spaces();
      // COB(185): END-EVALUATE
    }
    // COB(187): IF (CDEMO-CU00-USR-SEL-FLG NOT = SPACES AND LOW-VALUES) AND
    // COB(187):    (CDEMO-CU00-USR-SELECTED NOT = SPACES AND LOW-VALUES)
    if ((!ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.isSpaces()
            && !ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.isLowValues())
        && (!ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelected.isSpaces()
            && !ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelected.isLowValues())) {
      // COB(189): EVALUATE CDEMO-CU00-USR-SEL-FLG
      // COB(190): WHEN 'U'
      if ((ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.equals("U"))
          // COB(191): WHEN 'u'
          || ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.equals("u")) {
        // COB(192): MOVE 'COUSR02C'   TO CDEMO-TO-PROGRAM
        ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COUSR02C");
        // COB(193): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
        ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsVariables.wsTranid);
        // COB(194): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
        ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsVariables.wsPgmname);
        // COB(195): MOVE 0        TO CDEMO-PGM-CONTEXT
        ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.setValue(0);
        // COB(196): EXEC CICS
        // COB(196):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
        // COB(196):     COMMAREA(CARDDEMO-COMMAREA)
        // COB(196): END-EXEC
        try {
          supernaut
              .xctl(ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
              .commarea(ws.carddemoCommarea) //
              .exec();
        } catch (HandledConditionException e) {
          handleCommandCondition(e);
        }
        // COB(200): WHEN 'D'
      } else if (ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.equals("D")
          // COB(201): WHEN 'd'
          || ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsrSelFlg.equals("d")) {
        // COB(202): MOVE 'COUSR03C'   TO CDEMO-TO-PROGRAM
        ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COUSR03C");
        // COB(203): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
        ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsVariables.wsTranid);
        // COB(204): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
        ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsVariables.wsPgmname);
        // COB(205): MOVE 0        TO CDEMO-PGM-CONTEXT
        ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.setValue(0);
        // COB(206): EXEC CICS
        // COB(206):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
        // COB(206):     COMMAREA(CARDDEMO-COMMAREA)
        // COB(206): END-EXEC
        try {
          supernaut
              .xctl(ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
              .commarea(ws.carddemoCommarea) //
              .exec();
        } catch (HandledConditionException e) {
          handleCommandCondition(e);
        }
        // COB(210): WHEN OTHER
      } else {
        // COB(211): MOVE
        // COB(211): 'Invalid selection. Valid values are U and D' TO
        // COB(211):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Invalid selection. Valid values are U and D");
        // COB(214): MOVE -1       TO USRIDINL OF COUSR0AI
        ws.cousr00.cousr0ai.usridinl.setValue(-1);
        // COB(215): END-EVALUATE
      }
      // COB(216): END-IF
    }
    // COB(218): IF USRIDINI OF COUSR0AI = SPACES OR LOW-VALUES
    if (ws.cousr00.cousr0ai.usridini.isSpaces() || ws.cousr00.cousr0ai.usridini.isLowValues()) {
      // COB(219): MOVE LOW-VALUES TO SEC-USR-ID
      ws.csusr01y.secUserData.secUsrId.lowValues();
      // COB(220): ELSE
    } else {
      // COB(221): MOVE USRIDINI  OF COUSR0AI TO SEC-USR-ID
      ws.csusr01y.secUserData.secUsrId.setValue(ws.cousr00.cousr0ai.usridini);
      // COB(222): END-IF
    }
    // COB(224): MOVE -1       TO USRIDINL OF COUSR0AI
    ws.cousr00.cousr0ai.usridinl.setValue(-1);
    // COB(227): MOVE 0       TO CDEMO-CU00-PAGE-NUM
    //
    //
    ws.carddemoCommarea.cdemoCu00Info.cdemoCu00PageNum.setValue(0);
    // COB(228): PERFORM PROCESS-PAGE-FORWARD
    processPageForward();
    // COB(230): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(231): MOVE SPACE   TO USRIDINO  OF COUSR0AO
      ws.cousr00.cousr0ao.usridino.spaces();
      // COB(232): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      PROCESS-PF7-KEY
   *----------------------------------------------------------------*/
  protected void processPf7Key() {
    // COB(239): IF CDEMO-CU00-USRID-FIRST = SPACES OR LOW-VALUES
    if (ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsridFirst.isSpaces()
        || ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsridFirst.isLowValues()) {
      // COB(240): MOVE LOW-VALUES TO SEC-USR-ID
      ws.csusr01y.secUserData.secUsrId.lowValues();
      // COB(241): ELSE
    } else {
      // COB(242): MOVE CDEMO-CU00-USRID-FIRST TO SEC-USR-ID
      ws.csusr01y.secUserData.secUsrId.setValue(
          ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsridFirst);
      // COB(243): END-IF
    }
    // COB(245): SET NEXT-PAGE-YES TO TRUE
    ws.carddemoCommarea.cdemoCu00Info.setNextPageYes(true);
    // COB(246): MOVE -1       TO USRIDINL OF COUSR0AI
    ws.cousr00.cousr0ai.usridinl.setValue(-1);
    // COB(248): IF CDEMO-CU00-PAGE-NUM > 1
    if (ws.carddemoCommarea.cdemoCu00Info.cdemoCu00PageNum.greaterThan(1)) {
      // COB(249): PERFORM PROCESS-PAGE-BACKWARD
      processPageBackward();
      // COB(250): ELSE
    } else {
      // COB(251): MOVE 'You are already at the top of the page...' TO
      // COB(251):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("You are already at the top of the page...");
      // COB(253): SET SEND-ERASE-NO TO TRUE
      ws.wsVariables.setSendEraseNo(true);
      // COB(254): PERFORM SEND-USRLST-SCREEN
      sendUsrlstScreen();
      // COB(255): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      PROCESS-PF8-KEY
   *----------------------------------------------------------------*/
  protected void processPf8Key() {
    // COB(262): IF CDEMO-CU00-USRID-LAST = SPACES OR LOW-VALUES
    if (ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsridLast.isSpaces()
        || ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsridLast.isLowValues()) {
      // COB(263): MOVE HIGH-VALUES TO SEC-USR-ID
      ws.csusr01y.secUserData.secUsrId.highValues();
      // COB(264): ELSE
    } else {
      // COB(265): MOVE CDEMO-CU00-USRID-LAST TO SEC-USR-ID
      ws.csusr01y.secUserData.secUsrId.setValue(
          ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsridLast);
      // COB(266): END-IF
    }
    // COB(268): MOVE -1       TO USRIDINL OF COUSR0AI
    ws.cousr00.cousr0ai.usridinl.setValue(-1);
    // COB(270): IF NEXT-PAGE-YES
    if (ws.carddemoCommarea.cdemoCu00Info.nextPageYes()) {
      // COB(271): PERFORM PROCESS-PAGE-FORWARD
      processPageForward();
      // COB(272): ELSE
    } else {
      // COB(273): MOVE 'You are already at the bottom of the page...' TO
      // COB(273):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("You are already at the bottom of the page...");
      // COB(275): SET SEND-ERASE-NO TO TRUE
      ws.wsVariables.setSendEraseNo(true);
      // COB(276): PERFORM SEND-USRLST-SCREEN
      sendUsrlstScreen();
      // COB(277): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      PROCESS-PAGE-FORWARD
   *----------------------------------------------------------------*/
  protected void processPageForward() {
    // COB(284): PERFORM STARTBR-USER-SEC-FILE
    startbrUserSecFile();
    // COB(286): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(288): IF EIBAID NOT = DFHENTER AND DFHPF7 AND DFHPF3
      if (!dfheiblk.getEibaid().equals(Dfhaid.DFHENTER)
          && !dfheiblk.getEibaid().equals(Dfhaid.DFHPF7)
          && !dfheiblk.getEibaid().equals(Dfhaid.DFHPF3)) {
        // COB(289): PERFORM READNEXT-USER-SEC-FILE
        readnextUserSecFile();
        // COB(290): END-IF
      }
      // COB(292): IF USER-SEC-NOT-EOF AND ERR-FLG-OFF
      if (ws.wsVariables.userSecNotEof() && ws.wsVariables.errFlgOff()) {
        // COB(293): PERFORM VARYING WS-IDX FROM 1 BY 1 UNTIL WS-IDX > 10
        ws.wsVariables.wsIdx.setValue(1);
        for (; !ws.wsVariables.wsIdx.greaterThan(10); ws.wsVariables.wsIdx.add(1)) {
          // COB(294): PERFORM INITIALIZE-USER-DATA
          initializeUserData();
          // COB(295): END-PERFORM
        }
        // COB(296): END-IF
      }
      // COB(298): MOVE 1             TO  WS-IDX
      ws.wsVariables.wsIdx.setValue(1);
      // COB(300): PERFORM UNTIL WS-IDX >= 11 OR USER-SEC-EOF OR ERR-FLG-ON
      while (!(ws.wsVariables.wsIdx.greaterEqualThan(11)
          || ws.wsVariables.userSecEof()
          || ws.wsVariables.errFlgOn())) {
        // COB(301): PERFORM READNEXT-USER-SEC-FILE
        readnextUserSecFile();
        // COB(302): IF USER-SEC-NOT-EOF AND ERR-FLG-OFF
        if (ws.wsVariables.userSecNotEof() && ws.wsVariables.errFlgOff()) {
          // COB(303): PERFORM POPULATE-USER-DATA
          populateUserData();
          // COB(304): COMPUTE WS-IDX = WS-IDX + 1
          ws.wsVariables.wsIdx.setValue(ws.wsVariables.wsIdx.getValue().add(new BigDecimal("1")));
          // COB(305): END-IF
        }
        // COB(306): END-PERFORM
      }
      // COB(308): IF USER-SEC-NOT-EOF AND ERR-FLG-OFF
      if (ws.wsVariables.userSecNotEof() && ws.wsVariables.errFlgOff()) {
        // COB(309): COMPUTE CDEMO-CU00-PAGE-NUM =
        // COB(309):         CDEMO-CU00-PAGE-NUM + 1
        ws.carddemoCommarea.cdemoCu00Info.cdemoCu00PageNum.setValue(
            ws.carddemoCommarea.cdemoCu00Info.cdemoCu00PageNum.getValue().add(new BigDecimal("1")));
        // COB(311): PERFORM READNEXT-USER-SEC-FILE
        readnextUserSecFile();
        // COB(312): IF USER-SEC-NOT-EOF AND ERR-FLG-OFF
        if (ws.wsVariables.userSecNotEof() && ws.wsVariables.errFlgOff()) {
          // COB(313): SET NEXT-PAGE-YES TO TRUE
          ws.carddemoCommarea.cdemoCu00Info.setNextPageYes(true);
          // COB(314): ELSE
        } else {
          // COB(315): SET NEXT-PAGE-NO TO TRUE
          ws.carddemoCommarea.cdemoCu00Info.setNextPageNo(true);
          // COB(316): END-IF
        }
        // COB(317): ELSE
      } else {
        // COB(318): SET NEXT-PAGE-NO TO TRUE
        ws.carddemoCommarea.cdemoCu00Info.setNextPageNo(true);
        // COB(319): IF WS-IDX > 1
        if (ws.wsVariables.wsIdx.greaterThan(1)) {
          // COB(320): COMPUTE CDEMO-CU00-PAGE-NUM = CDEMO-CU00-PAGE-NUM
          // COB(320):  + 1
          ws.carddemoCommarea.cdemoCu00Info.cdemoCu00PageNum.setValue(
              ws.carddemoCommarea
                  .cdemoCu00Info
                  .cdemoCu00PageNum
                  .getValue()
                  .add(new BigDecimal("1")));
          // COB(322): END-IF
        }
        // COB(323): END-IF
      }
      // COB(325): PERFORM ENDBR-USER-SEC-FILE
      endbrUserSecFile();
      // COB(327): MOVE CDEMO-CU00-PAGE-NUM TO PAGENUMI  OF COUSR0AI
      ws.cousr00.cousr0ai.pagenumi.setValue(ws.carddemoCommarea.cdemoCu00Info.cdemoCu00PageNum);
      // COB(328): MOVE SPACE   TO USRIDINO  OF COUSR0AO
      ws.cousr00.cousr0ao.usridino.spaces();
      // COB(329): PERFORM SEND-USRLST-SCREEN
      sendUsrlstScreen();
      // COB(331): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      PROCESS-PAGE-BACKWARD
   *----------------------------------------------------------------*/
  protected void processPageBackward() {
    // COB(338): PERFORM STARTBR-USER-SEC-FILE
    startbrUserSecFile();
    // COB(340): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(342): IF EIBAID NOT = DFHENTER  AND DFHPF8
      if (!dfheiblk.getEibaid().equals(Dfhaid.DFHENTER)
          && !dfheiblk.getEibaid().equals(Dfhaid.DFHPF8)) {
        // COB(343): PERFORM READPREV-USER-SEC-FILE
        readprevUserSecFile();
        // COB(344): END-IF
      }
      // COB(346): IF USER-SEC-NOT-EOF AND ERR-FLG-OFF
      if (ws.wsVariables.userSecNotEof() && ws.wsVariables.errFlgOff()) {
        // COB(347): PERFORM VARYING WS-IDX FROM 1 BY 1 UNTIL WS-IDX > 10
        ws.wsVariables.wsIdx.setValue(1);
        for (; !ws.wsVariables.wsIdx.greaterThan(10); ws.wsVariables.wsIdx.add(1)) {
          // COB(348): PERFORM INITIALIZE-USER-DATA
          initializeUserData();
          // COB(349): END-PERFORM
        }
        // COB(350): END-IF
      }
      // COB(352): MOVE 10          TO  WS-IDX
      ws.wsVariables.wsIdx.setValue(10);
      // COB(354): PERFORM UNTIL WS-IDX <= 0 OR USER-SEC-EOF OR ERR-FLG-ON
      while (!(ws.wsVariables.wsIdx.lessEqualThan(0)
          || ws.wsVariables.userSecEof()
          || ws.wsVariables.errFlgOn())) {
        // COB(355): PERFORM READPREV-USER-SEC-FILE
        readprevUserSecFile();
        // COB(356): IF USER-SEC-NOT-EOF AND ERR-FLG-OFF
        if (ws.wsVariables.userSecNotEof() && ws.wsVariables.errFlgOff()) {
          // COB(357): PERFORM POPULATE-USER-DATA
          populateUserData();
          // COB(358): COMPUTE WS-IDX = WS-IDX - 1
          ws.wsVariables.wsIdx.setValue(
              ws.wsVariables.wsIdx.getValue().subtract(new BigDecimal("1")));
          // COB(359): END-IF
        }
        // COB(360): END-PERFORM
      }
      // COB(362): IF USER-SEC-NOT-EOF AND ERR-FLG-OFF
      if (ws.wsVariables.userSecNotEof() && ws.wsVariables.errFlgOff()) {
        // COB(363): PERFORM READPREV-USER-SEC-FILE
        readprevUserSecFile();
        // COB(364): IF NEXT-PAGE-YES
        if (ws.carddemoCommarea.cdemoCu00Info.nextPageYes()) {
          // COB(365): IF USER-SEC-NOT-EOF AND ERR-FLG-OFF AND
          // COB(365):     CDEMO-CU00-PAGE-NUM > 1
          if (ws.wsVariables.userSecNotEof()
              && ws.wsVariables.errFlgOff()
              && ws.carddemoCommarea.cdemoCu00Info.cdemoCu00PageNum.greaterThan(1)) {
            // COB(367): SUBTRACT 1 FROM CDEMO-CU00-PAGE-NUM
            ws.carddemoCommarea.cdemoCu00Info.cdemoCu00PageNum.subtract(1);
            // COB(368): ELSE
          } else {
            // COB(369): MOVE 1 TO CDEMO-CU00-PAGE-NUM
            ws.carddemoCommarea.cdemoCu00Info.cdemoCu00PageNum.setValue(1);
            // COB(370): END-IF
          }
          // COB(371): END-IF
        }
        // COB(372): END-IF
      }
      // COB(374): PERFORM ENDBR-USER-SEC-FILE
      endbrUserSecFile();
      // COB(376): MOVE CDEMO-CU00-PAGE-NUM TO PAGENUMI  OF COUSR0AI
      ws.cousr00.cousr0ai.pagenumi.setValue(ws.carddemoCommarea.cdemoCu00Info.cdemoCu00PageNum);
      // COB(377): PERFORM SEND-USRLST-SCREEN
      sendUsrlstScreen();
      // COB(379): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      POPULATE-USER-DATA
   *----------------------------------------------------------------*/
  protected void populateUserData() {
    // COB(386): EVALUATE WS-IDX
    switch (ws.wsVariables.wsIdx.getInt()) {
        // COB(387): WHEN 1
      case 1:
        // COB(388): MOVE SEC-USR-ID    TO USRID01I OF COUSR0AI
        // COB(388):                       CDEMO-CU00-USRID-FIRST
        ws.cousr00.cousr0ai.usrid01i.setValue(ws.csusr01y.secUserData.secUsrId);
        ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsridFirst.setValue(
            ws.csusr01y.secUserData.secUsrId);
        // COB(390): MOVE SEC-USR-FNAME TO FNAME01I OF COUSR0AI
        ws.cousr00.cousr0ai.fname01i.setValue(ws.csusr01y.secUserData.secUsrFname);
        // COB(391): MOVE SEC-USR-LNAME TO LNAME01I OF COUSR0AI
        ws.cousr00.cousr0ai.lname01i.setValue(ws.csusr01y.secUserData.secUsrLname);
        // COB(392): MOVE SEC-USR-TYPE  TO UTYPE01I OF COUSR0AI
        ws.cousr00.cousr0ai.utype01i.setValue(ws.csusr01y.secUserData.secUsrType);
        // COB(393): WHEN 2
        break;
      case 2:
        // COB(394): MOVE SEC-USR-ID    TO USRID02I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid02i.setValue(ws.csusr01y.secUserData.secUsrId);
        // COB(395): MOVE SEC-USR-FNAME TO FNAME02I OF COUSR0AI
        ws.cousr00.cousr0ai.fname02i.setValue(ws.csusr01y.secUserData.secUsrFname);
        // COB(396): MOVE SEC-USR-LNAME TO LNAME02I OF COUSR0AI
        ws.cousr00.cousr0ai.lname02i.setValue(ws.csusr01y.secUserData.secUsrLname);
        // COB(397): MOVE SEC-USR-TYPE  TO UTYPE02I OF COUSR0AI
        ws.cousr00.cousr0ai.utype02i.setValue(ws.csusr01y.secUserData.secUsrType);
        // COB(398): WHEN 3
        break;
      case 3:
        // COB(399): MOVE SEC-USR-ID    TO USRID03I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid03i.setValue(ws.csusr01y.secUserData.secUsrId);
        // COB(400): MOVE SEC-USR-FNAME TO FNAME03I OF COUSR0AI
        ws.cousr00.cousr0ai.fname03i.setValue(ws.csusr01y.secUserData.secUsrFname);
        // COB(401): MOVE SEC-USR-LNAME TO LNAME03I OF COUSR0AI
        ws.cousr00.cousr0ai.lname03i.setValue(ws.csusr01y.secUserData.secUsrLname);
        // COB(402): MOVE SEC-USR-TYPE  TO UTYPE03I OF COUSR0AI
        ws.cousr00.cousr0ai.utype03i.setValue(ws.csusr01y.secUserData.secUsrType);
        // COB(403): WHEN 4
        break;
      case 4:
        // COB(404): MOVE SEC-USR-ID    TO USRID04I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid04i.setValue(ws.csusr01y.secUserData.secUsrId);
        // COB(405): MOVE SEC-USR-FNAME TO FNAME04I OF COUSR0AI
        ws.cousr00.cousr0ai.fname04i.setValue(ws.csusr01y.secUserData.secUsrFname);
        // COB(406): MOVE SEC-USR-LNAME TO LNAME04I OF COUSR0AI
        ws.cousr00.cousr0ai.lname04i.setValue(ws.csusr01y.secUserData.secUsrLname);
        // COB(407): MOVE SEC-USR-TYPE  TO UTYPE04I OF COUSR0AI
        ws.cousr00.cousr0ai.utype04i.setValue(ws.csusr01y.secUserData.secUsrType);
        // COB(408): WHEN 5
        break;
      case 5:
        // COB(409): MOVE SEC-USR-ID    TO USRID05I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid05i.setValue(ws.csusr01y.secUserData.secUsrId);
        // COB(410): MOVE SEC-USR-FNAME TO FNAME05I OF COUSR0AI
        ws.cousr00.cousr0ai.fname05i.setValue(ws.csusr01y.secUserData.secUsrFname);
        // COB(411): MOVE SEC-USR-LNAME TO LNAME05I OF COUSR0AI
        ws.cousr00.cousr0ai.lname05i.setValue(ws.csusr01y.secUserData.secUsrLname);
        // COB(412): MOVE SEC-USR-TYPE  TO UTYPE05I OF COUSR0AI
        ws.cousr00.cousr0ai.utype05i.setValue(ws.csusr01y.secUserData.secUsrType);
        // COB(413): WHEN 6
        break;
      case 6:
        // COB(414): MOVE SEC-USR-ID    TO USRID06I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid06i.setValue(ws.csusr01y.secUserData.secUsrId);
        // COB(415): MOVE SEC-USR-FNAME TO FNAME06I OF COUSR0AI
        ws.cousr00.cousr0ai.fname06i.setValue(ws.csusr01y.secUserData.secUsrFname);
        // COB(416): MOVE SEC-USR-LNAME TO LNAME06I OF COUSR0AI
        ws.cousr00.cousr0ai.lname06i.setValue(ws.csusr01y.secUserData.secUsrLname);
        // COB(417): MOVE SEC-USR-TYPE  TO UTYPE06I OF COUSR0AI
        ws.cousr00.cousr0ai.utype06i.setValue(ws.csusr01y.secUserData.secUsrType);
        // COB(418): WHEN 7
        break;
      case 7:
        // COB(419): MOVE SEC-USR-ID    TO USRID07I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid07i.setValue(ws.csusr01y.secUserData.secUsrId);
        // COB(420): MOVE SEC-USR-FNAME TO FNAME07I OF COUSR0AI
        ws.cousr00.cousr0ai.fname07i.setValue(ws.csusr01y.secUserData.secUsrFname);
        // COB(421): MOVE SEC-USR-LNAME TO LNAME07I OF COUSR0AI
        ws.cousr00.cousr0ai.lname07i.setValue(ws.csusr01y.secUserData.secUsrLname);
        // COB(422): MOVE SEC-USR-TYPE  TO UTYPE07I OF COUSR0AI
        ws.cousr00.cousr0ai.utype07i.setValue(ws.csusr01y.secUserData.secUsrType);
        // COB(423): WHEN 8
        break;
      case 8:
        // COB(424): MOVE SEC-USR-ID    TO USRID08I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid08i.setValue(ws.csusr01y.secUserData.secUsrId);
        // COB(425): MOVE SEC-USR-FNAME TO FNAME08I OF COUSR0AI
        ws.cousr00.cousr0ai.fname08i.setValue(ws.csusr01y.secUserData.secUsrFname);
        // COB(426): MOVE SEC-USR-LNAME TO LNAME08I OF COUSR0AI
        ws.cousr00.cousr0ai.lname08i.setValue(ws.csusr01y.secUserData.secUsrLname);
        // COB(427): MOVE SEC-USR-TYPE  TO UTYPE08I OF COUSR0AI
        ws.cousr00.cousr0ai.utype08i.setValue(ws.csusr01y.secUserData.secUsrType);
        // COB(428): WHEN 9
        break;
      case 9:
        // COB(429): MOVE SEC-USR-ID    TO USRID09I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid09i.setValue(ws.csusr01y.secUserData.secUsrId);
        // COB(430): MOVE SEC-USR-FNAME TO FNAME09I OF COUSR0AI
        ws.cousr00.cousr0ai.fname09i.setValue(ws.csusr01y.secUserData.secUsrFname);
        // COB(431): MOVE SEC-USR-LNAME TO LNAME09I OF COUSR0AI
        ws.cousr00.cousr0ai.lname09i.setValue(ws.csusr01y.secUserData.secUsrLname);
        // COB(432): MOVE SEC-USR-TYPE  TO UTYPE09I OF COUSR0AI
        ws.cousr00.cousr0ai.utype09i.setValue(ws.csusr01y.secUserData.secUsrType);
        // COB(433): WHEN 10
        break;
      case 10:
        // COB(434): MOVE SEC-USR-ID    TO USRID10I OF COUSR0AI
        // COB(434):                       CDEMO-CU00-USRID-LAST
        ws.cousr00.cousr0ai.usrid10i.setValue(ws.csusr01y.secUserData.secUsrId);
        ws.carddemoCommarea.cdemoCu00Info.cdemoCu00UsridLast.setValue(
            ws.csusr01y.secUserData.secUsrId);
        // COB(436): MOVE SEC-USR-FNAME TO FNAME10I OF COUSR0AI
        ws.cousr00.cousr0ai.fname10i.setValue(ws.csusr01y.secUserData.secUsrFname);
        // COB(437): MOVE SEC-USR-LNAME TO LNAME10I OF COUSR0AI
        ws.cousr00.cousr0ai.lname10i.setValue(ws.csusr01y.secUserData.secUsrLname);
        // COB(438): MOVE SEC-USR-TYPE  TO UTYPE10I OF COUSR0AI
        ws.cousr00.cousr0ai.utype10i.setValue(ws.csusr01y.secUserData.secUsrType);
        // COB(439): WHEN OTHER
        break;
      default:
        // COB(440): CONTINUE
        // do nothing
        // COB(441): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      INITIALIZE-USER-DATA
   *----------------------------------------------------------------*/
  protected void initializeUserData() {
    // COB(448): EVALUATE WS-IDX
    switch (ws.wsVariables.wsIdx.getInt()) {
        // COB(449): WHEN 1
      case 1:
        // COB(450): MOVE SPACES TO USRID01I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid01i.spaces();
        // COB(451): MOVE SPACES TO FNAME01I OF COUSR0AI
        ws.cousr00.cousr0ai.fname01i.spaces();
        // COB(452): MOVE SPACES TO LNAME01I OF COUSR0AI
        ws.cousr00.cousr0ai.lname01i.spaces();
        // COB(453): MOVE SPACES TO UTYPE01I OF COUSR0AI
        ws.cousr00.cousr0ai.utype01i.spaces();
        // COB(454): WHEN 2
        break;
      case 2:
        // COB(455): MOVE SPACES TO USRID02I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid02i.spaces();
        // COB(456): MOVE SPACES TO FNAME02I OF COUSR0AI
        ws.cousr00.cousr0ai.fname02i.spaces();
        // COB(457): MOVE SPACES TO LNAME02I OF COUSR0AI
        ws.cousr00.cousr0ai.lname02i.spaces();
        // COB(458): MOVE SPACES TO UTYPE02I OF COUSR0AI
        ws.cousr00.cousr0ai.utype02i.spaces();
        // COB(459): WHEN 3
        break;
      case 3:
        // COB(460): MOVE SPACES TO USRID03I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid03i.spaces();
        // COB(461): MOVE SPACES TO FNAME03I OF COUSR0AI
        ws.cousr00.cousr0ai.fname03i.spaces();
        // COB(462): MOVE SPACES TO LNAME03I OF COUSR0AI
        ws.cousr00.cousr0ai.lname03i.spaces();
        // COB(463): MOVE SPACES TO UTYPE03I OF COUSR0AI
        ws.cousr00.cousr0ai.utype03i.spaces();
        // COB(464): WHEN 4
        break;
      case 4:
        // COB(465): MOVE SPACES TO USRID04I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid04i.spaces();
        // COB(466): MOVE SPACES TO FNAME04I OF COUSR0AI
        ws.cousr00.cousr0ai.fname04i.spaces();
        // COB(467): MOVE SPACES TO LNAME04I OF COUSR0AI
        ws.cousr00.cousr0ai.lname04i.spaces();
        // COB(468): MOVE SPACES TO UTYPE04I OF COUSR0AI
        ws.cousr00.cousr0ai.utype04i.spaces();
        // COB(469): WHEN 5
        break;
      case 5:
        // COB(470): MOVE SPACES TO USRID05I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid05i.spaces();
        // COB(471): MOVE SPACES TO FNAME05I OF COUSR0AI
        ws.cousr00.cousr0ai.fname05i.spaces();
        // COB(472): MOVE SPACES TO LNAME05I OF COUSR0AI
        ws.cousr00.cousr0ai.lname05i.spaces();
        // COB(473): MOVE SPACES TO UTYPE05I OF COUSR0AI
        ws.cousr00.cousr0ai.utype05i.spaces();
        // COB(474): WHEN 6
        break;
      case 6:
        // COB(475): MOVE SPACES TO USRID06I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid06i.spaces();
        // COB(476): MOVE SPACES TO FNAME06I OF COUSR0AI
        ws.cousr00.cousr0ai.fname06i.spaces();
        // COB(477): MOVE SPACES TO LNAME06I OF COUSR0AI
        ws.cousr00.cousr0ai.lname06i.spaces();
        // COB(478): MOVE SPACES TO UTYPE06I OF COUSR0AI
        ws.cousr00.cousr0ai.utype06i.spaces();
        // COB(479): WHEN 7
        break;
      case 7:
        // COB(480): MOVE SPACES TO USRID07I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid07i.spaces();
        // COB(481): MOVE SPACES TO FNAME07I OF COUSR0AI
        ws.cousr00.cousr0ai.fname07i.spaces();
        // COB(482): MOVE SPACES TO LNAME07I OF COUSR0AI
        ws.cousr00.cousr0ai.lname07i.spaces();
        // COB(483): MOVE SPACES TO UTYPE07I OF COUSR0AI
        ws.cousr00.cousr0ai.utype07i.spaces();
        // COB(484): WHEN 8
        break;
      case 8:
        // COB(485): MOVE SPACES TO USRID08I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid08i.spaces();
        // COB(486): MOVE SPACES TO FNAME08I OF COUSR0AI
        ws.cousr00.cousr0ai.fname08i.spaces();
        // COB(487): MOVE SPACES TO LNAME08I OF COUSR0AI
        ws.cousr00.cousr0ai.lname08i.spaces();
        // COB(488): MOVE SPACES TO UTYPE08I OF COUSR0AI
        ws.cousr00.cousr0ai.utype08i.spaces();
        // COB(489): WHEN 9
        break;
      case 9:
        // COB(490): MOVE SPACES TO USRID09I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid09i.spaces();
        // COB(491): MOVE SPACES TO FNAME09I OF COUSR0AI
        ws.cousr00.cousr0ai.fname09i.spaces();
        // COB(492): MOVE SPACES TO LNAME09I OF COUSR0AI
        ws.cousr00.cousr0ai.lname09i.spaces();
        // COB(493): MOVE SPACES TO UTYPE09I OF COUSR0AI
        ws.cousr00.cousr0ai.utype09i.spaces();
        // COB(494): WHEN 10
        break;
      case 10:
        // COB(495): MOVE SPACES TO USRID10I OF COUSR0AI
        ws.cousr00.cousr0ai.usrid10i.spaces();
        // COB(496): MOVE SPACES TO FNAME10I OF COUSR0AI
        ws.cousr00.cousr0ai.fname10i.spaces();
        // COB(497): MOVE SPACES TO LNAME10I OF COUSR0AI
        ws.cousr00.cousr0ai.lname10i.spaces();
        // COB(498): MOVE SPACES TO UTYPE10I OF COUSR0AI
        ws.cousr00.cousr0ai.utype10i.spaces();
        // COB(499): WHEN OTHER
        break;
      default:
        // COB(500): CONTINUE
        // do nothing
        // COB(501): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RETURN-TO-PREV-SCREEN
   *----------------------------------------------------------------*/
  protected void returnToPrevScreen() {
    // COB(508): IF CDEMO-TO-PROGRAM = LOW-VALUES OR SPACES
    if (ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isLowValues()
        || ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isSpaces()) {
      // COB(509): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(510): END-IF
    }
    // COB(511): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsVariables.wsTranid);
    // COB(512): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsVariables.wsPgmname);
    // COB(513): MOVE ZEROS        TO CDEMO-PGM-CONTEXT
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.zeros();
    // COB(514): EXEC CICS
    // COB(514):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
    // COB(514):     COMMAREA(CARDDEMO-COMMAREA)
    // COB(514): END-EXEC.
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
   *                      SEND-USRLST-SCREEN
   *----------------------------------------------------------------*/
  protected void sendUsrlstScreen() {
    // COB(524): PERFORM POPULATE-HEADER-INFO
    populateHeaderInfo();
    // COB(526): MOVE WS-MESSAGE TO ERRMSGO OF COUSR0AO
    ws.cousr00.cousr0ao.errmsgo.setValue(ws.wsVariables.wsMessage);
    // COB(528): IF SEND-ERASE-YES
    if (ws.wsVariables.sendEraseYes()) {
      // COB(529): EXEC CICS SEND
      // COB(529):           MAP('COUSR0A')
      // COB(529):           MAPSET('COUSR00')
      // COB(529):           FROM(COUSR0AO)
      // COB(529):           ERASE
      // COB(529):           CURSOR
      // COB(529): END-EXEC
      try {
        supernaut
            .sendMap("COUSR0A") //
            .mapset("COUSR00") //
            .from(ws.cousr00.cousr0ao) //
            .erase() //
            .cursor() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(536): ELSE
    } else {
      // COB(537): EXEC CICS SEND
      // COB(537):           MAP('COUSR0A')
      // COB(537):           MAPSET('COUSR00')
      // COB(537):           FROM(COUSR0AO)
      // COB(537):       *                  ERASE
      // COB(537):           CURSOR
      // COB(537): END-EXEC
      try {
        supernaut
            .sendMap("COUSR0A") //
            .mapset("COUSR00") //
            .from(ws.cousr00.cousr0ao) //
            .cursor() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(544): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RECEIVE-USRLST-SCREEN
   *----------------------------------------------------------------*/
  protected void receiveUsrlstScreen() {
    // COB(551): EXEC CICS RECEIVE
    // COB(551):           MAP('COUSR0A')
    // COB(551):           MAPSET('COUSR00')
    // COB(551):           INTO(COUSR0AI)
    // COB(551):           RESP(WS-RESP-CD)
    // COB(551):           RESP2(WS-REAS-CD)
    // COB(551): END-EXEC.
    supernaut
        .receiveMap("COUSR0A") //
        .mapset("COUSR00") //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cousr00.cousr0ai) //
        .exec();
  }

  /***
   *----------------------------------------------------------------*
   *                      POPULATE-HEADER-INFO
   *----------------------------------------------------------------*/
  protected void populateHeaderInfo() {
    // COB(564): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(566): MOVE CCDA-TITLE01           TO TITLE01O OF COUSR0AO
    ws.cousr00.cousr0ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(567): MOVE CCDA-TITLE02           TO TITLE02O OF COUSR0AO
    ws.cousr00.cousr0ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(568): MOVE WS-TRANID              TO TRNNAMEO OF COUSR0AO
    ws.cousr00.cousr0ao.trnnameo.setValue(ws.wsVariables.wsTranid);
    // COB(569): MOVE WS-PGMNAME             TO PGMNAMEO OF COUSR0AO
    ws.cousr00.cousr0ao.pgmnameo.setValue(ws.wsVariables.wsPgmname);
    // COB(571): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(572): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(573): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(575): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF COUSR0AO
    ws.cousr00.cousr0ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(577): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(578): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(579): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(581): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF COUSR0AO.
    ws.cousr00.cousr0ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***
   *----------------------------------------------------------------*
   *                      STARTBR-USER-SEC-FILE
   *----------------------------------------------------------------*/
  protected void startbrUserSecFile() {
    // COB(588): EXEC CICS STARTBR
    // COB(588):      DATASET   (WS-USRSEC-FILE)
    // COB(588):      RIDFLD    (SEC-USR-ID)
    // COB(588):      KEYLENGTH (LENGTH OF SEC-USR-ID)
    // COB(588):       *         GTEQ
    // COB(588):      RESP      (WS-RESP-CD)
    // COB(588):      RESP2     (WS-REAS-CD)
    // COB(588): END-EXEC.
    supernaut
        .startbr(ws.wsVariables.wsUsrsecFile.getValue()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .ridfld(ws.csusr01y.secUserData.secUsrId) //
        .keylength(ws.csusr01y.secUserData.secUsrId.length()) //
        .exec();
    // COB(597): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(598): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(599): CONTINUE
        // do nothing
        // COB(600): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(601): CONTINUE
        // do nothing
        // COB(602): SET USER-SEC-EOF TO TRUE
        ws.wsVariables.setUserSecEof(true);
        // COB(603): MOVE 'You are at the top of the page...' TO
        // COB(603):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("You are at the top of the page...");
        // COB(605): MOVE -1       TO USRIDINL OF COUSR0AI
        ws.cousr00.cousr0ai.usridinl.setValue(-1);
        // COB(606): PERFORM SEND-USRLST-SCREEN
        sendUsrlstScreen();
        // COB(607): WHEN OTHER
        break;
      default:
        // COB(608): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(609): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(610): MOVE 'Unable to lookup User...' TO
        // COB(610):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup User...");
        // COB(612): MOVE -1       TO USRIDINL OF COUSR0AI
        ws.cousr00.cousr0ai.usridinl.setValue(-1);
        // COB(613): PERFORM SEND-USRLST-SCREEN
        sendUsrlstScreen();
        // COB(614): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      READNEXT-USER-SEC-FILE
   *----------------------------------------------------------------*/
  protected void readnextUserSecFile() {
    // COB(621): EXEC CICS READNEXT
    // COB(621):      DATASET   (WS-USRSEC-FILE)
    // COB(621):      INTO      (SEC-USER-DATA)
    // COB(621):      LENGTH    (LENGTH OF SEC-USER-DATA)
    // COB(621):      RIDFLD    (SEC-USR-ID)
    // COB(621):      KEYLENGTH (LENGTH OF SEC-USR-ID)
    // COB(621):      RESP      (WS-RESP-CD)
    // COB(621):      RESP2     (WS-REAS-CD)
    // COB(621): END-EXEC.
    supernaut
        .readNext(ws.wsVariables.wsUsrsecFile.getValue()) //
        .length(ws.csusr01y.secUserData.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.csusr01y.secUserData) //
        .ridfld(ws.csusr01y.secUserData.secUsrId) //
        .keylength(ws.csusr01y.secUserData.secUsrId.length()) //
        .exec();
    // COB(631): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(632): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(633): CONTINUE
        // do nothing
        // COB(634): WHEN DFHRESP(ENDFILE)
        break;
      case Dfhresp.ENDFILE:
        // COB(635): CONTINUE
        // do nothing
        // COB(636): SET USER-SEC-EOF TO TRUE
        ws.wsVariables.setUserSecEof(true);
        // COB(637): MOVE 'You have reached the bottom of the page...' TO
        // COB(637):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("You have reached the bottom of the page...");
        // COB(639): MOVE -1       TO USRIDINL OF COUSR0AI
        ws.cousr00.cousr0ai.usridinl.setValue(-1);
        // COB(640): PERFORM SEND-USRLST-SCREEN
        sendUsrlstScreen();
        // COB(641): WHEN OTHER
        break;
      default:
        // COB(642): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(643): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(644): MOVE 'Unable to lookup User...' TO
        // COB(644):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup User...");
        // COB(646): MOVE -1       TO USRIDINL OF COUSR0AI
        ws.cousr00.cousr0ai.usridinl.setValue(-1);
        // COB(647): PERFORM SEND-USRLST-SCREEN
        sendUsrlstScreen();
        // COB(648): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      READPREV-USER-SEC-FILE
   *----------------------------------------------------------------*/
  protected void readprevUserSecFile() {
    // COB(655): EXEC CICS READPREV
    // COB(655):      DATASET   (WS-USRSEC-FILE)
    // COB(655):      INTO      (SEC-USER-DATA)
    // COB(655):      LENGTH    (LENGTH OF SEC-USER-DATA)
    // COB(655):      RIDFLD    (SEC-USR-ID)
    // COB(655):      KEYLENGTH (LENGTH OF SEC-USR-ID)
    // COB(655):      RESP      (WS-RESP-CD)
    // COB(655):      RESP2     (WS-REAS-CD)
    // COB(655): END-EXEC.
    supernaut
        .readPrev(ws.wsVariables.wsUsrsecFile.getValue()) //
        .length(ws.csusr01y.secUserData.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.csusr01y.secUserData) //
        .ridfld(ws.csusr01y.secUserData.secUsrId) //
        .keylength(ws.csusr01y.secUserData.secUsrId.length()) //
        .exec();
    // COB(665): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(666): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(667): CONTINUE
        // do nothing
        // COB(668): WHEN DFHRESP(ENDFILE)
        break;
      case Dfhresp.ENDFILE:
        // COB(669): CONTINUE
        // do nothing
        // COB(670): SET USER-SEC-EOF TO TRUE
        ws.wsVariables.setUserSecEof(true);
        // COB(671): MOVE 'You have reached the top of the page...' TO
        // COB(671):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("You have reached the top of the page...");
        // COB(673): MOVE -1       TO USRIDINL OF COUSR0AI
        ws.cousr00.cousr0ai.usridinl.setValue(-1);
        // COB(674): PERFORM SEND-USRLST-SCREEN
        sendUsrlstScreen();
        // COB(675): WHEN OTHER
        break;
      default:
        // COB(676): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(677): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(678): MOVE 'Unable to lookup User...' TO
        // COB(678):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup User...");
        // COB(680): MOVE -1       TO USRIDINL OF COUSR0AI
        ws.cousr00.cousr0ai.usridinl.setValue(-1);
        // COB(681): PERFORM SEND-USRLST-SCREEN
        sendUsrlstScreen();
        // COB(682): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      ENDBR-USER-SEC-FILE
   *----------------------------------------------------------------*/
  protected void endbrUserSecFile() {
    // COB(689): EXEC CICS ENDBR
    // COB(689):      DATASET   (WS-USRSEC-FILE)
    // COB(689): END-EXEC.
    try {
      supernaut
          .endbr(ws.wsVariables.wsUsrsecFile.getValue()) //
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
