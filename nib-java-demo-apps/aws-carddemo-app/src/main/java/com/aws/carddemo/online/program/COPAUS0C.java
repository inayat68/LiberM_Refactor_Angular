package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.copaus0c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COPAUS0C extends AbstractOnlineProgram {

  @ProgramStorage final Copaus0cWorking ws = new Copaus0cWorking();

  // Linkage
  public class Copaus0cLinkage extends NGroup {
    // COB:        01  DFHCOMMAREA.
    public Dfhcommarea dfhcommarea = new Dfhcommarea();

    public class Dfhcommarea extends NGroup {

      // COB:          05  LK-COMMAREA                           PIC X(01)
      // COB:              OCCURS 1 TO 32767 TIMES DEPENDING ON EIBCALEN.
      public NChar[] lkCommarea = AbstractNField.occurs(32767, new NChar(1));

      public NChar lkCommareaAtIndex(int index) {
        return getOccursInstance(lkCommarea, index);
      }
    }
  }

  final Copaus0cLinkage params = new Copaus0cLinkage();

  public COPAUS0C(Context supernaut) {
    super(supernaut);
  }

  @Override
  protected int procedure(AbstractNField dfhcommarea) throws ContextException {
    if (dfhcommarea != null) {
      params.dfhcommarea.setAddress(dfhcommarea.getAddress());
    }
    mainPara();
    return 0;
  }

  /*****************************************************************/
  protected void mainPara() {
    // COB(186): SET ERR-FLG-OFF TO TRUE
    ws.wsSwitches.setErrFlgOff(true);
    // COB(187): SET AUTHS-NOT-EOF TO TRUE
    ws.wsSwitches.setAuthsNotEof(true);
    // COB(188): SET NEXT-PAGE-NO TO TRUE
    ws.carddemoCommarea.cdemoCpvsInfo.setNextPageNo(true);
    // COB(189): SET SEND-ERASE-YES TO TRUE
    ws.wsSwitches.setSendEraseYes(true);
    // COB(191): MOVE SPACES TO WS-MESSAGE ERRMSGO OF COPAU0AO
    ws.wsVariables.wsMessage.spaces();
    ws.copau00.copau0ao.errmsgo.spaces();
    // COB(193): MOVE -1       TO ACCTIDL OF COPAU0AI
    ws.copau00.copau0ai.acctidl.setValue(-1);
    // COB(195): IF EIBCALEN = 0
    if (dfheiblk.getEibcalen() == 0) {
      // COB(196): INITIALIZE CARDDEMO-COMMAREA
      ws.carddemoCommarea.initialize();
      // COB(197): MOVE WS-PGM-AUTH-SMRY    TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(ws.wsVariables.wsPgmAuthSmry);
      // COB(199): SET CDEMO-PGM-REENTER    TO TRUE
      ws.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
      // COB(200): MOVE LOW-VALUES          TO COPAU0AO
      ws.copau00.copau0ao.lowValues();
      // COB(201): MOVE -1                  TO ACCTIDL OF COPAU0AI
      ws.copau00.copau0ai.acctidl.setValue(-1);
      // COB(203): PERFORM SEND-PAULST-SCREEN
      sendPaulstScreen();
      // COB(204): ELSE
    } else {
      // COB(205): MOVE DFHCOMMAREA(1:EIBCALEN) TO CARDDEMO-COMMAREA
      ws.carddemoCommarea.setValue(params.dfhcommarea, dfheiblk.getEibcalen());
      // COB(207): IF NOT CDEMO-PGM-REENTER
      if (!ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
        // COB(208): SET CDEMO-PGM-REENTER     TO TRUE
        ws.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
        // COB(210): MOVE LOW-VALUES           TO COPAU0AO
        ws.copau00.copau0ao.lowValues();
        // COB(212): IF CDEMO-ACCT-ID IS NUMERIC
        if (ws.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.isNumeric()) {
          // COB(213): MOVE CDEMO-ACCT-ID     TO WS-ACCT-ID
          // COB(213):                           ACCTIDO OF COPAU0AO
          ws.wsVariables.wsAcctId.setValue(ws.carddemoCommarea.cdemoAccountInfo.cdemoAcctId);
          ws.copau00.copau0ao.acctido.setValue(ws.carddemoCommarea.cdemoAccountInfo.cdemoAcctId);
          // COB(215): ELSE
        } else {
          // COB(216): MOVE SPACE             TO ACCTIDO OF COPAU0AO
          ws.copau00.copau0ao.acctido.spaces();
          // COB(217): MOVE LOW-VALUES        TO WS-ACCT-ID
          ws.wsVariables.wsAcctId.lowValues();
          // COB(218): END-IF
        }
        // COB(220): PERFORM GATHER-DETAILS
        gatherDetails();
        // COB(222): SET SEND-ERASE-YES TO TRUE
        ws.wsSwitches.setSendEraseYes(true);
        // COB(224): PERFORM SEND-PAULST-SCREEN
        sendPaulstScreen();
        // COB(226): ELSE
      } else {
        // COB(227): PERFORM RECEIVE-PAULST-SCREEN
        receivePaulstScreen();
        // COB(229): EVALUATE EIBAID
        switch (dfheiblk.getEibaid()) {
            // COB(230): WHEN DFHENTER
          case DFHENTER:
            // COB(231): PERFORM PROCESS-ENTER-KEY
            processEnterKey();
            // COB(233): IF WS-ACCT-ID = LOW-VALUES
            if (ws.wsVariables.wsAcctId.isLowValues()) {
              // COB(234): MOVE SPACE           TO ACCTIDO   OF COPAU0AO
              ws.copau00.copau0ao.acctido.spaces();
              // COB(235): ELSE
            } else {
              // COB(236): MOVE WS-ACCT-ID      TO ACCTIDO   OF COPAU0AO
              ws.copau00.copau0ao.acctido.setValue(ws.wsVariables.wsAcctId);
              // COB(237): END-IF
            }
            // COB(239): PERFORM SEND-PAULST-SCREEN
            sendPaulstScreen();
            // COB(240): WHEN DFHPF3
            break;
          case DFHPF3:
            // COB(241): MOVE WS-PGM-MENU        TO CDEMO-TO-PROGRAM
            ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(ws.wsVariables.wsPgmMenu);
            // COB(242): PERFORM RETURN-TO-PREV-SCREEN
            returnToPrevScreen();
            // COB(243): PERFORM SEND-PAULST-SCREEN
            sendPaulstScreen();
            // COB(244): WHEN DFHPF7
            break;
          case DFHPF7:
            // COB(245): PERFORM PROCESS-PF7-KEY
            processPf7Key();
            // COB(246): PERFORM SEND-PAULST-SCREEN
            sendPaulstScreen();
            // COB(247): WHEN DFHPF8
            break;
          case DFHPF8:
            // COB(248): PERFORM PROCESS-PF8-KEY
            processPf8Key();
            // COB(249): PERFORM SEND-PAULST-SCREEN
            sendPaulstScreen();
            // COB(250): WHEN OTHER
            break;
          default:
            // COB(251): MOVE 'Y'              TO WS-ERR-FLG
            ws.wsSwitches.wsErrFlg.setString("Y");
            // COB(252): MOVE -1               TO ACCTIDL OF COPAU0AI
            ws.copau00.copau0ai.acctidl.setValue(-1);
            // COB(253): MOVE CCDA-MSG-INVALID-KEY  TO WS-MESSAGE
            ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgInvalidKey);
            // COB(254): PERFORM SEND-PAULST-SCREEN
            sendPaulstScreen();
            // COB(255): END-EVALUATE
        }
        // COB(256): END-IF
      }
      // COB(257): END-IF
    }
    // COB(259): EXEC CICS RETURN
    // COB(259):           TRANSID (WS-CICS-TRANID)
    // COB(259):           COMMAREA (CARDDEMO-COMMAREA)
    // COB(259): END-EXEC.
    try {
      supernaut
          .returnTransid(ws.wsVariables.wsCicsTranid) //
          .commarea(ws.carddemoCommarea) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *
   *****************************************************************/
  protected void processEnterKey() {
    // COB(269): IF ACCTIDI OF COPAU0AI = SPACES OR LOW-VALUES
    if (ws.copau00.copau0ai.acctidi.isSpaces() || ws.copau00.copau0ai.acctidi.isLowValues()) {
      // COB(270): MOVE LOW-VALUES                 TO WS-ACCT-ID
      ws.wsVariables.wsAcctId.lowValues();
      // COB(272): MOVE 'Y'                        TO WS-ERR-FLG
      ws.wsSwitches.wsErrFlg.setString("Y");
      // COB(273): MOVE
      // COB(273): 'Please enter Acct Id...'       TO WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Please enter Acct Id...");
      // COB(276): MOVE -1                         TO ACCTIDL OF COPAU0AI
      ws.copau00.copau0ai.acctidl.setValue(-1);
      // COB(277): ELSE
    } else {
      // COB(278): IF ACCTIDI OF COPAU0AI IS NOT NUMERIC
      if (!ws.copau00.copau0ai.acctidi.isNumeric()) {
        // COB(279): MOVE LOW-VALUES               TO WS-ACCT-ID
        ws.wsVariables.wsAcctId.lowValues();
        // COB(281): MOVE 'Y'                      TO WS-ERR-FLG
        ws.wsSwitches.wsErrFlg.setString("Y");
        // COB(282): MOVE
        // COB(282): 'Acct Id must be Numeric ...' TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Acct Id must be Numeric ...");
        // COB(285): MOVE -1                       TO ACCTIDL OF COPAU0AI
        ws.copau00.copau0ai.acctidl.setValue(-1);
        // COB(287): ELSE
      } else {
        // COB(288): MOVE ACCTIDI OF COPAU0AI      TO WS-ACCT-ID
        // COB(288):                                  CDEMO-ACCT-ID
        ws.wsVariables.wsAcctId.setValue(ws.copau00.copau0ai.acctidi);
        ws.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.setValue(ws.copau00.copau0ai.acctidi);
        // COB(290): EVALUATE TRUE
        // COB(291): WHEN SEL0001I OF COPAU0AI NOT = SPACES AND LOW-VALUES
        if (!ws.copau00.copau0ai.sel0001i.isSpaces()
            && !ws.copau00.copau0ai.sel0001i.isLowValues()) {
          // COB(292): MOVE SEL0001I OF COPAU0AI TO CDEMO-CPVS-PAU-SEL-FLG
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelFlg.setValue(
              ws.copau00.copau0ai.sel0001i);
          // COB(293): MOVE CDEMO-CPVS-AUTH-KEYS(1)
          // COB(293):                           TO CDEMO-CPVS-PAU-SELECTED
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelected.setValue(
              ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsAuthKeysAtIndex(0));
          // COB(295): WHEN SEL0002I OF COPAU0AI NOT = SPACES AND LOW-VALUES
        } else if (!ws.copau00.copau0ai.sel0002i.isSpaces()
            && !ws.copau00.copau0ai.sel0002i.isLowValues()) {
          // COB(296): MOVE SEL0002I OF COPAU0AI TO CDEMO-CPVS-PAU-SEL-FLG
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelFlg.setValue(
              ws.copau00.copau0ai.sel0002i);
          // COB(297): MOVE CDEMO-CPVS-AUTH-KEYS(2)
          // COB(297):                           TO CDEMO-CPVS-PAU-SELECTED
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelected.setValue(
              ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsAuthKeysAtIndex(1));
          // COB(299): WHEN SEL0003I OF COPAU0AI NOT = SPACES AND LOW-VALUES
        } else if (!ws.copau00.copau0ai.sel0003i.isSpaces()
            && !ws.copau00.copau0ai.sel0003i.isLowValues()) {
          // COB(300): MOVE SEL0003I OF COPAU0AI TO CDEMO-CPVS-PAU-SEL-FLG
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelFlg.setValue(
              ws.copau00.copau0ai.sel0003i);
          // COB(301): MOVE CDEMO-CPVS-AUTH-KEYS(3)
          // COB(301):                           TO CDEMO-CPVS-PAU-SELECTED
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelected.setValue(
              ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsAuthKeysAtIndex(2));
          // COB(303): WHEN SEL0004I OF COPAU0AI NOT = SPACES AND LOW-VALUES
        } else if (!ws.copau00.copau0ai.sel0004i.isSpaces()
            && !ws.copau00.copau0ai.sel0004i.isLowValues()) {
          // COB(304): MOVE SEL0004I OF COPAU0AI TO CDEMO-CPVS-PAU-SEL-FLG
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelFlg.setValue(
              ws.copau00.copau0ai.sel0004i);
          // COB(305): MOVE CDEMO-CPVS-AUTH-KEYS(4)
          // COB(305):                           TO CDEMO-CPVS-PAU-SELECTED
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelected.setValue(
              ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsAuthKeysAtIndex(3));
          // COB(307): WHEN SEL0005I OF COPAU0AI NOT = SPACES AND LOW-VALUES
        } else if (!ws.copau00.copau0ai.sel0005i.isSpaces()
            && !ws.copau00.copau0ai.sel0005i.isLowValues()) {
          // COB(308): MOVE SEL0005I OF COPAU0AI TO CDEMO-CPVS-PAU-SEL-FLG
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelFlg.setValue(
              ws.copau00.copau0ai.sel0005i);
          // COB(309): MOVE CDEMO-CPVS-AUTH-KEYS(5)
          // COB(309):                           TO CDEMO-CPVS-PAU-SELECTED
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelected.setValue(
              ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsAuthKeysAtIndex(4));
          // COB(311): WHEN OTHER
        } else {
          // COB(312): MOVE SPACES   TO CDEMO-CPVS-PAU-SEL-FLG
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelFlg.spaces();
          // COB(313): MOVE SPACES   TO CDEMO-CPVS-PAU-SELECTED
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelected.spaces();
          // COB(314): END-EVALUATE
        }
        // COB(315): IF (CDEMO-CPVS-PAU-SEL-FLG NOT = SPACES AND LOW-VALUES)
        // COB(315):    AND
        // COB(315):    (CDEMO-CPVS-PAU-SELECTED NOT = SPACES AND LOW-VALUES)
        if ((!ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelFlg.isSpaces()
                && !ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelFlg.isLowValues())
            && (!ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelected.isSpaces()
                && !ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelected.isLowValues())) {
          // COB(318): EVALUATE CDEMO-CPVS-PAU-SEL-FLG
          // COB(319): WHEN 'S'
          if ((ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelFlg.getValue().equals("S"))
              // COB(320): WHEN 's'
              || ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPauSelFlg.getValue().equals("s")) {
            // COB(321): MOVE WS-PGM-AUTH-DTL  TO CDEMO-TO-PROGRAM
            ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
                ws.wsVariables.wsPgmAuthDtl);
            // COB(322): MOVE WS-CICS-TRANID   TO CDEMO-FROM-TRANID
            ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
                ws.wsVariables.wsCicsTranid);
            // COB(323): MOVE WS-PGM-AUTH-SMRY TO CDEMO-FROM-PROGRAM
            ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
                ws.wsVariables.wsPgmAuthSmry);
            // COB(324): MOVE 0                TO CDEMO-PGM-CONTEXT
            ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.setValue(0);
            // COB(325): SET CDEMO-PGM-ENTER   TO TRUE
            ws.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
            // COB(327): EXEC CICS
            // COB(327):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
            // COB(327):     COMMAREA(CARDDEMO-COMMAREA)
            // COB(327): END-EXEC
            try {
              supernaut
                  .xctl(ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
                  .commarea(ws.carddemoCommarea) //
                  .exec();
            } catch (HandledConditionException e) {
              handleCommandCondition(e);
            }
            // COB(331): WHEN OTHER
          } else {
            // COB(332): MOVE
            // COB(332): 'Invalid selection. Valid value is S'
            // COB(332):                        TO WS-MESSAGE
            ws.wsVariables.wsMessage.setString("Invalid selection. Valid value is S");
            // COB(335): MOVE -1                TO ACCTIDL OF COPAU0AI
            ws.copau00.copau0ai.acctidl.setValue(-1);
            // COB(336): END-EVALUATE
          }
          // COB(337): END-IF
        }
        // COB(339): END-IF
      }
      // COB(340): END-IF
    }
    // COB(342): PERFORM GATHER-DETAILS
    gatherDetails();
  }

  /***
   *
   *****************************************************************/
  protected void gatherDetails() {
    // COB(350): MOVE -1       TO ACCTIDL OF COPAU0AI
    ws.copau00.copau0ai.acctidl.setValue(-1);
    // COB(352): MOVE 0        TO CDEMO-CPVS-PAGE-NUM
    ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPageNum.setValue(0);
    // COB(354): IF WS-ACCT-ID NOT = LOW-VALUES
    if (!ws.wsVariables.wsAcctId.isLowValues()) {
      // COB(355): PERFORM GATHER-ACCOUNT-DETAILS
      gatherAccountDetails();
      // COB(357): PERFORM INITIALIZE-AUTH-DATA
      initializeAuthData();
      // COB(359): IF FOUND-PAUT-SMRY-SEG
      if (ws.wsSwitches.foundPautSmrySeg()) {
        // COB(360): PERFORM PROCESS-PAGE-FORWARD
        processPageForward();
        // COB(361): END-IF
      }
      // COB(362): END-IF
    }
  }

  /***
   *
   *****************************************************************/
  protected void processPf7Key() {
    // COB(370): IF CDEMO-CPVS-PAGE-NUM > 1
    if (ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPageNum.greaterThan(1)) {
      // COB(371): COMPUTE CDEMO-CPVS-PAGE-NUM = CDEMO-CPVS-PAGE-NUM - 1
      ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPageNum.setValue(
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPageNum.getInt() - 1);
      // COB(373): MOVE CDEMO-CPVS-PAUKEY-PREV-PG(CDEMO-CPVS-PAGE-NUM)
      // COB(373):                              TO WS-AUTH-KEY-SAVE
      ws.wsVariables.wsAuthKeySave.setValue(
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPaukeyPrevPgAtIndex(
              ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPageNum.getInt() - 1));
      // COB(375): PERFORM GET-AUTH-SUMMARY
      getAuthSummary();
      // COB(377): SET SEND-ERASE-NO            TO TRUE
      ws.wsSwitches.setSendEraseNo(true);
      // COB(379): SET NEXT-PAGE-YES            TO TRUE
      ws.carddemoCommarea.cdemoCpvsInfo.setNextPageYes(true);
      // COB(380): MOVE -1                      TO ACCTIDL OF COPAU0AI
      ws.copau00.copau0ai.acctidl.setValue(-1);
      // COB(382): PERFORM INITIALIZE-AUTH-DATA
      initializeAuthData();
      // COB(384): PERFORM PROCESS-PAGE-FORWARD
      processPageForward();
      // COB(385): ELSE
    } else {
      // COB(386): MOVE 'You are already at the top of the page...' TO
      // COB(386):                  WS-MESSAGE
      ws.wsVariables.wsMessage.setString("You are already at the top of the page...");
      // COB(388): SET SEND-ERASE-NO            TO TRUE
      ws.wsSwitches.setSendEraseNo(true);
      // COB(389): END-IF
    }
  }

  /***
   *****************************************************************/
  protected void processPf8Key() {
    // COB(396): IF CDEMO-CPVS-PAUKEY-LAST = SPACES OR LOW-VALUES
    if (ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPaukeyLast.isSpaces()
        || ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPaukeyLast.isLowValues()) {
      // COB(397): MOVE LOW-VALUES             TO WS-AUTH-KEY-SAVE
      ws.wsVariables.wsAuthKeySave.lowValues();
      // COB(398): ELSE
    } else {
      // COB(399): MOVE CDEMO-CPVS-PAUKEY-LAST TO WS-AUTH-KEY-SAVE
      ws.wsVariables.wsAuthKeySave.setValue(ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPaukeyLast);
      // COB(401): PERFORM GET-AUTH-SUMMARY
      getAuthSummary();
      // COB(402): PERFORM REPOSITION-AUTHORIZATIONS
      repositionAuthorizations();
      // COB(403): END-IF
    }
    // COB(405): MOVE -1                         TO ACCTIDL OF COPAU0AI
    ws.copau00.copau0ai.acctidl.setValue(-1);
    // COB(407): SET SEND-ERASE-NO               TO TRUE
    ws.wsSwitches.setSendEraseNo(true);
    // COB(409): IF NEXT-PAGE-YES
    if (ws.carddemoCommarea.cdemoCpvsInfo.nextPageYes()) {
      // COB(410): PERFORM INITIALIZE-AUTH-DATA
      initializeAuthData();
      // COB(412): PERFORM PROCESS-PAGE-FORWARD
      processPageForward();
      // COB(413): ELSE
    } else {
      // COB(414): MOVE 'You are already at the bottom of the page...'
      // COB(414):                             TO WS-MESSAGE
      ws.wsVariables.wsMessage.setString("You are already at the bottom of the page...");
      // COB(416): END-IF
    }
  }

  /***
   *****************************************************************/
  protected void processPageForward() {
    // COB(423): IF ERR-FLG-OFF
    if (ws.wsSwitches.errFlgOff()) {
      // COB(425): MOVE 1             TO  WS-IDX
      ws.wsVariables.wsIdx.setValue(1);
      // COB(427): MOVE LOW-VALUES    TO CDEMO-CPVS-PAUKEY-LAST
      ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPaukeyLast.lowValues();
      // COB(429): PERFORM UNTIL WS-IDX > 5 OR AUTHS-EOF OR ERR-FLG-ON
      while (!(ws.wsVariables.wsIdx.greaterThan(5)
          || ws.wsSwitches.authsEof()
          || ws.wsSwitches.errFlgOn())) {
        // COB(430): IF EIBAID = DFHPF7 AND WS-IDX = 1
        if (dfheiblk.getEibaid().equals(Dfhaid.DFHPF7) && ws.wsVariables.wsIdx.equals(1)) {
          // COB(431): PERFORM REPOSITION-AUTHORIZATIONS
          repositionAuthorizations();
          // COB(432): ELSE
        } else {
          // COB(433): PERFORM GET-AUTHORIZATIONS
          getAuthorizations();
          // COB(434): END-IF
        }
        // COB(435): IF AUTHS-NOT-EOF AND ERR-FLG-OFF
        if (ws.wsSwitches.authsNotEof() && ws.wsSwitches.errFlgOff()) {
          // COB(436): PERFORM POPULATE-AUTH-LIST
          populateAuthList();
          // COB(437): COMPUTE WS-IDX = WS-IDX + 1
          ws.wsVariables.wsIdx.setValue(ws.wsVariables.wsIdx.getInt() + 1);
          // COB(439): MOVE PA-AUTHORIZATION-KEY TO
          // COB(439):                       CDEMO-CPVS-PAUKEY-LAST
          ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPaukeyLast.setValue(
              ws.pendingAuthDetails.paAuthorizationKey);
          // COB(441): IF WS-IDX = 2
          if (ws.wsVariables.wsIdx.equals(2)) {
            // COB(442): COMPUTE CDEMO-CPVS-PAGE-NUM =
            // COB(442):         CDEMO-CPVS-PAGE-NUM + 1
            ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPageNum.setValue(
                ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPageNum.getInt() + 1);
            // COB(444): MOVE PA-AUTHORIZATION-KEY TO
            // COB(444): CDEMO-CPVS-PAUKEY-PREV-PG(CDEMO-CPVS-PAGE-NUM)
            ws.carddemoCommarea
                .cdemoCpvsInfo
                .cdemoCpvsPaukeyPrevPgAtIndex(
                    ws.carddemoCommarea.cdemoCpvsInfo.cdemoCpvsPageNum.getInt() - 1)
                .setValue(ws.pendingAuthDetails.paAuthorizationKey);
            // COB(446): END-IF
          }
          // COB(447): END-IF
        }
        // COB(448): END-PERFORM
      }
      // COB(450): IF AUTHS-NOT-EOF AND ERR-FLG-OFF
      if (ws.wsSwitches.authsNotEof() && ws.wsSwitches.errFlgOff()) {
        // COB(451): PERFORM GET-AUTHORIZATIONS
        getAuthorizations();
        // COB(452): IF AUTHS-NOT-EOF AND ERR-FLG-OFF
        if (ws.wsSwitches.authsNotEof() && ws.wsSwitches.errFlgOff()) {
          // COB(453): SET NEXT-PAGE-YES TO TRUE
          ws.carddemoCommarea.cdemoCpvsInfo.setNextPageYes(true);
          // COB(454): ELSE
        } else {
          // COB(455): SET NEXT-PAGE-NO TO TRUE
          ws.carddemoCommarea.cdemoCpvsInfo.setNextPageNo(true);
          // COB(456): END-IF
        }
        // COB(457): END-IF
      }
      // COB(459): END-IF.
    }
  }

  /***
   *
   *****************************************************************/
  protected void getAuthorizations() {
    // COB(470): IRISDB     MOVE 1 TO IRIS-CALL-ID
    ws.irisWorkArea.irisCallId.setValue(1);
    // COB(471): IRISDB     MOVE SPACE TO IRIS-KEYVALUE-TAB
    ws.irisWorkArea.irisKeyvalueTab.spaces();
    // COB(472): IRISDB     MOVE 'DBPAUTP0' TO IRIS-WS-RTN
    ws.iriscomm.irisWsRtn.setString("DBPAUTP0");
    // COB(473): IRISDB     MOVE 'PAUTDTL1' TO IRIS-SEGMENT
    ws.irisWorkArea.irisSegment.setString("PAUTDTL1");
    // COB(474): IRISDB     SET IRIS-FUNC-GNP TO TRUE
    ws.irisWorkArea.setIrisFuncGnp(true);
    // COB(475): IRISDB     MOVE 2 TO IRIS-CUSTOM-FUNC-ID
    ws.irisWorkArea.irisCustomFuncId.setValue(2);
    // COB(476): IRISDB     MOVE PAUT-PCB-NUM TO IRIS-PCB-NUM
    ws.irisWorkArea.irisPcbNum.setValue(ws.wsImsVariables.pcbOffset.pautPcbNum);
    // COB(477): IRISDB     SET IRIS-EXECDLI TO TRUE
    ws.irisWorkArea.setIrisExecdli(true);
    // COB(478): IRISDB     SET SQL-USER-CUSTOM TO TRUE
    ws.irisWorkArea.setSqlUserCustom(true);
    // COB(479): IRISDB     MOVE 3 TO IRIS-PARAM-NUM
    ws.irisWorkArea.irisParamNum.setValue(3);
    // COB(480): IRISDB     CALL IRIS-WS-RTN USING IRIS-WORK-AREA
    // COB(480): IRISDB                            DLZDIB
    // COB(480): IRISDB                            PENDING-AUTH-DETAILS
    supernaut.call(
        ws.iriscomm.irisWsRtn, ws.irisWorkArea, ws.iriscomm.dlzdib, ws.pendingAuthDetails);
    // COB(483): MOVE DIBSTAT                          TO IMS-RETURN-CODE
    ws.wsImsVariables.imsReturnCode.setValue(ws.iriscomm.irisDibBlock.dibstat);
    // COB(484): EVALUATE TRUE
    // COB(485): WHEN STATUS-OK
    if (ws.wsImsVariables.statusOk()) {
      // COB(486): SET AUTHS-NOT-EOF              TO TRUE
      ws.wsSwitches.setAuthsNotEof(true);
      // COB(487): WHEN SEGMENT-NOT-FOUND
    } else if (ws.wsImsVariables.segmentNotFound()
        // COB(488): WHEN END-OF-DB
        || ws.wsImsVariables.endOfDb()) {
      // COB(489): SET AUTHS-EOF                  TO TRUE
      ws.wsSwitches.setAuthsEof(true);
      // COB(490): WHEN OTHER
    } else {
      // COB(491): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsSwitches.wsErrFlg.setString("Y");
      // COB(493): STRING
      // COB(493): ' System error while reading AUTH Details: Code:'
      // COB(493): IMS-RETURN-CODE
      // COB(493): DELIMITED BY SIZE
      // COB(493): INTO WS-MESSAGE
      // COB(493): END-STRING
      ws.wsVariables.wsMessage.concat(
          " System error while reading AUTH Details: Code:", ws.wsImsVariables.imsReturnCode);
      // COB(498): END-STRING
      // COB(499): MOVE -1       TO ACCTIDL OF COPAU0AI
      ws.copau00.copau0ai.acctidl.setValue(-1);
      // COB(500): PERFORM SEND-PAULST-SCREEN
      sendPaulstScreen();
      // COB(501): END-EVALUATE
    }
  }

  /*****************************************************************/
  protected void repositionAuthorizations() {
    // COB(508): MOVE WS-AUTH-KEY-SAVE          TO PA-AUTHORIZATION-KEY
    ws.pendingAuthDetails.paAuthorizationKey.setValue(ws.wsVariables.wsAuthKeySave);
    // COB(515): IRISDB     MOVE 2 TO IRIS-CALL-ID
    //       *
    //       *    EXEC DLI GNP USING PCB(PAUT-PCB-NUM)
    //       *        SEGMENT (PAUTDTL1)
    //       *        INTO (PENDING-AUTH-DETAILS)
    //       *        WHERE (PAUT9CTS = PA-AUTHORIZATION-KEY)
    //       *    END-EXEC
    ws.irisWorkArea.irisCallId.setValue(2);
    // COB(516): IRISDB     MOVE SPACE TO IRIS-KEYVALUE-TAB
    ws.irisWorkArea.irisKeyvalueTab.spaces();
    // COB(517): IRISDB     MOVE 'DBPAUTP0' TO IRIS-WS-RTN
    ws.iriscomm.irisWsRtn.setString("DBPAUTP0");
    // COB(518): IRISDB     MOVE 'PAUTDTL1' TO IRIS-SEGMENT
    ws.irisWorkArea.irisSegment.setString("PAUTDTL1");
    // COB(519): IRISDB     SET IRIS-FUNC-GNP TO TRUE
    ws.irisWorkArea.setIrisFuncGnp(true);
    // COB(520): IRISDB     MOVE 12 TO IRIS-CUSTOM-FUNC-ID
    ws.irisWorkArea.irisCustomFuncId.setValue(12);
    // COB(521): IRISDB     MOVE PAUT-PCB-NUM TO IRIS-PCB-NUM
    ws.irisWorkArea.irisPcbNum.setValue(ws.wsImsVariables.pcbOffset.pautPcbNum);
    // COB(522): IRISDB     MOVE PA-AUTHORIZATION-KEY TO IRIS-KEYVALUE(1, 1)
    ws.irisWorkArea
        .irisKeyvalueTab
        .irisKeysLevelsAtIndex(0)
        .irisKeyvaluesRed1
        .irisKeyvaluesAtIndex(0)
        .irisKeyvalue
        .setValue(ws.pendingAuthDetails.paAuthorizationKey);
    // COB(523): IRISDB     SET IRIS-EXECDLI TO TRUE
    ws.irisWorkArea.setIrisExecdli(true);
    // COB(524): IRISDB     SET SQL-USER-CUSTOM TO TRUE
    ws.irisWorkArea.setSqlUserCustom(true);
    // COB(525): IRISDB     MOVE 3 TO IRIS-PARAM-NUM
    ws.irisWorkArea.irisParamNum.setValue(3);
    // COB(526): IRISDB     CALL IRIS-WS-RTN USING IRIS-WORK-AREA
    // COB(526): IRISDB                            DLZDIB
    // COB(526): IRISDB                            PENDING-AUTH-DETAILS
    supernaut.call(
        ws.iriscomm.irisWsRtn, ws.irisWorkArea, ws.iriscomm.dlzdib, ws.pendingAuthDetails);
    // COB(529): MOVE DIBSTAT                          TO IMS-RETURN-CODE
    ws.wsImsVariables.imsReturnCode.setValue(ws.iriscomm.irisDibBlock.dibstat);
    // COB(530): EVALUATE TRUE
    // COB(531): WHEN STATUS-OK
    if (ws.wsImsVariables.statusOk()) {
      // COB(532): SET AUTHS-NOT-EOF              TO TRUE
      ws.wsSwitches.setAuthsNotEof(true);
      // COB(533): WHEN SEGMENT-NOT-FOUND
    } else if (ws.wsImsVariables.segmentNotFound()
        // COB(534): WHEN END-OF-DB
        || ws.wsImsVariables.endOfDb()) {
      // COB(535): SET AUTHS-EOF                  TO TRUE
      ws.wsSwitches.setAuthsEof(true);
      // COB(536): WHEN OTHER
    } else {
      // COB(537): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsSwitches.wsErrFlg.setString("Y");
      // COB(539): STRING
      // COB(539): ' System error while repos. AUTH Details: Code:'
      // COB(539): IMS-RETURN-CODE
      // COB(539): DELIMITED BY SIZE
      // COB(539): INTO WS-MESSAGE
      // COB(539): END-STRING
      ws.wsVariables.wsMessage.concat(
          " System error while repos. AUTH Details: Code:", ws.wsImsVariables.imsReturnCode);
      // COB(544): END-STRING
      // COB(545): MOVE -1       TO ACCTIDL OF COPAU0AI
      ws.copau00.copau0ai.acctidl.setValue(-1);
      // COB(546): PERFORM SEND-PAULST-SCREEN
      sendPaulstScreen();
      // COB(547): END-EVALUATE
    }
  }

  /***
   *****************************************************************/
  protected void populateAuthList() {
    // COB(555): MOVE PA-APPROVED-AMT           TO WS-AUTH-AMT
    ws.wsVariables.wsAuthAmt.setValue(ws.pendingAuthDetails.paApprovedAmt);
    // COB(557): MOVE PA-AUTH-ORIG-TIME(1:2)    TO WS-AUTH-TIME(1:2)
    ws.wsVariables.wsAuthTime.setValue(ws.pendingAuthDetails.paAuthOrigTime, 2);
    // COB(558): MOVE PA-AUTH-ORIG-TIME(3:2)    TO WS-AUTH-TIME(4:2)
    ws.wsVariables.wsAuthTime.setValue(ws.pendingAuthDetails.paAuthOrigTime, 2, 3, 2);
    // COB(559): MOVE PA-AUTH-ORIG-TIME(5:2)    TO WS-AUTH-TIME(7:2)
    ws.wsVariables.wsAuthTime.setValue(ws.pendingAuthDetails.paAuthOrigTime, 4, 6, 2);
    // COB(561): MOVE PA-AUTH-ORIG-DATE(1:2)    TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.pendingAuthDetails.paAuthOrigDate, 2);
    // COB(562): MOVE PA-AUTH-ORIG-DATE(3:2)    TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.pendingAuthDetails.paAuthOrigDate, 2, 0, 2);
    // COB(563): MOVE PA-AUTH-ORIG-DATE(5:2)    TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.pendingAuthDetails.paAuthOrigDate, 4, 0, 2);
    // COB(564): MOVE WS-CURDATE-MM-DD-YY       TO WS-AUTH-DATE
    ws.wsVariables.wsAuthDate.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(566): IF PA-AUTH-RESP-CODE = '00'
    if (ws.pendingAuthDetails.paAuthRespCode.equals("00")) {
      // COB(567): MOVE 'A'               TO WS-AUTH-APRV-STAT
      ws.wsVariables.wsAuthAprvStat.setString("A");
      // COB(568): ELSE
    } else {
      // COB(569): MOVE 'D'               TO WS-AUTH-APRV-STAT
      ws.wsVariables.wsAuthAprvStat.setString("D");
      // COB(570): END-IF
    }
    // COB(572): EVALUATE WS-IDX
    switch (ws.wsVariables.wsIdx.getInt()) {
        // COB(573): WHEN 1
      case 1:
        // COB(574): MOVE PA-AUTHORIZATION-KEY
        // COB(574):                        TO CDEMO-CPVS-AUTH-KEYS(1)
        ws.carddemoCommarea
            .cdemoCpvsInfo
            .cdemoCpvsAuthKeysAtIndex(0)
            .setValue(ws.pendingAuthDetails.paAuthorizationKey);
        // COB(577): MOVE PA-TRANSACTION-ID TO TRNID01I OF COPAU0AI
        ws.copau00.copau0ai.trnid01i.setValue(ws.pendingAuthDetails.paTransactionId);
        // COB(578): MOVE WS-AUTH-DATE      TO PDATE01I OF COPAU0AI
        ws.copau00.copau0ai.pdate01i.setValue(ws.wsVariables.wsAuthDate);
        // COB(579): MOVE WS-AUTH-TIME      TO PTIME01I OF COPAU0AI
        ws.copau00.copau0ai.ptime01i.setValue(ws.wsVariables.wsAuthTime);
        // COB(580): MOVE PA-AUTH-TYPE      TO PTYPE01I OF COPAU0AI
        ws.copau00.copau0ai.ptype01i.setValue(ws.pendingAuthDetails.paAuthType);
        // COB(581): MOVE WS-AUTH-APRV-STAT TO PAPRV01I OF COPAU0AI
        ws.copau00.copau0ai.paprv01i.setValue(ws.wsVariables.wsAuthAprvStat);
        // COB(582): MOVE PA-MATCH-STATUS   TO PSTAT01I OF COPAU0AI
        ws.copau00.copau0ai.pstat01i.setValue(ws.pendingAuthDetails.paMatchStatus);
        // COB(583): MOVE WS-AUTH-AMT       TO PAMT001I OF COPAU0AI
        ws.copau00.copau0ai.pamt001i.setValue(ws.wsVariables.wsAuthAmt);
        // COB(584): MOVE DFHBMUNP          TO SEL0001A OF COPAU0AI
        ws.copau00.copau0ai.filler147.sel0001a.setValue(Dfhbmsca.DFHBMUNP.getValue());
        // COB(585): WHEN 2
        break;
      case 2:
        // COB(586): MOVE PA-AUTHORIZATION-KEY
        // COB(586):                        TO CDEMO-CPVS-AUTH-KEYS(2)
        ws.carddemoCommarea
            .cdemoCpvsInfo
            .cdemoCpvsAuthKeysAtIndex(1)
            .setValue(ws.pendingAuthDetails.paAuthorizationKey);
        // COB(589): MOVE PA-TRANSACTION-ID TO TRNID02I OF COPAU0AI
        ws.copau00.copau0ai.trnid02i.setValue(ws.pendingAuthDetails.paTransactionId);
        // COB(590): MOVE WS-AUTH-DATE      TO PDATE02I OF COPAU0AI
        ws.copau00.copau0ai.pdate02i.setValue(ws.wsVariables.wsAuthDate);
        // COB(591): MOVE WS-AUTH-TIME      TO PTIME02I OF COPAU0AI
        ws.copau00.copau0ai.ptime02i.setValue(ws.wsVariables.wsAuthTime);
        // COB(592): MOVE PA-AUTH-TYPE      TO PTYPE02I OF COPAU0AI
        ws.copau00.copau0ai.ptype02i.setValue(ws.pendingAuthDetails.paAuthType);
        // COB(593): MOVE WS-AUTH-APRV-STAT TO PAPRV02I OF COPAU0AI
        ws.copau00.copau0ai.paprv02i.setValue(ws.wsVariables.wsAuthAprvStat);
        // COB(594): MOVE PA-MATCH-STATUS   TO PSTAT02I OF COPAU0AI
        ws.copau00.copau0ai.pstat02i.setValue(ws.pendingAuthDetails.paMatchStatus);
        // COB(595): MOVE WS-AUTH-AMT       TO PAMT002I OF COPAU0AI
        ws.copau00.copau0ai.pamt002i.setValue(ws.wsVariables.wsAuthAmt);
        // COB(596): MOVE DFHBMUNP          TO SEL0002A OF COPAU0AI
        ws.copau00.copau0ai.filler195.sel0002a.setValue(Dfhbmsca.DFHBMUNP.getValue());
        // COB(597): WHEN 3
        break;
      case 3:
        // COB(598): MOVE PA-AUTHORIZATION-KEY
        // COB(598):                        TO CDEMO-CPVS-AUTH-KEYS(3)
        ws.carddemoCommarea
            .cdemoCpvsInfo
            .cdemoCpvsAuthKeysAtIndex(2)
            .setValue(ws.pendingAuthDetails.paAuthorizationKey);
        // COB(601): MOVE PA-TRANSACTION-ID TO TRNID03I OF COPAU0AI
        ws.copau00.copau0ai.trnid03i.setValue(ws.pendingAuthDetails.paTransactionId);
        // COB(602): MOVE WS-AUTH-DATE      TO PDATE03I OF COPAU0AI
        ws.copau00.copau0ai.pdate03i.setValue(ws.wsVariables.wsAuthDate);
        // COB(603): MOVE WS-AUTH-TIME      TO PTIME03I OF COPAU0AI
        ws.copau00.copau0ai.ptime03i.setValue(ws.wsVariables.wsAuthTime);
        // COB(604): MOVE PA-AUTH-TYPE      TO PTYPE03I OF COPAU0AI
        ws.copau00.copau0ai.ptype03i.setValue(ws.pendingAuthDetails.paAuthType);
        // COB(605): MOVE WS-AUTH-APRV-STAT TO PAPRV03I OF COPAU0AI
        ws.copau00.copau0ai.paprv03i.setValue(ws.wsVariables.wsAuthAprvStat);
        // COB(606): MOVE PA-MATCH-STATUS   TO PSTAT03I OF COPAU0AI
        ws.copau00.copau0ai.pstat03i.setValue(ws.pendingAuthDetails.paMatchStatus);
        // COB(607): MOVE WS-AUTH-AMT       TO PAMT003I OF COPAU0AI
        ws.copau00.copau0ai.pamt003i.setValue(ws.wsVariables.wsAuthAmt);
        // COB(608): MOVE DFHBMUNP          TO SEL0003A OF COPAU0AI
        ws.copau00.copau0ai.filler243.sel0003a.setValue(Dfhbmsca.DFHBMUNP.getValue());
        // COB(609): WHEN 4
        break;
      case 4:
        // COB(610): MOVE PA-AUTHORIZATION-KEY
        // COB(610):                        TO CDEMO-CPVS-AUTH-KEYS(4)
        ws.carddemoCommarea
            .cdemoCpvsInfo
            .cdemoCpvsAuthKeysAtIndex(3)
            .setValue(ws.pendingAuthDetails.paAuthorizationKey);
        // COB(613): MOVE PA-TRANSACTION-ID TO TRNID04I OF COPAU0AI
        ws.copau00.copau0ai.trnid04i.setValue(ws.pendingAuthDetails.paTransactionId);
        // COB(614): MOVE WS-AUTH-DATE      TO PDATE04I OF COPAU0AI
        ws.copau00.copau0ai.pdate04i.setValue(ws.wsVariables.wsAuthDate);
        // COB(615): MOVE WS-AUTH-TIME      TO PTIME04I OF COPAU0AI
        ws.copau00.copau0ai.ptime04i.setValue(ws.wsVariables.wsAuthTime);
        // COB(616): MOVE PA-AUTH-TYPE      TO PTYPE04I OF COPAU0AI
        ws.copau00.copau0ai.ptype04i.setValue(ws.pendingAuthDetails.paAuthType);
        // COB(617): MOVE WS-AUTH-APRV-STAT TO PAPRV04I OF COPAU0AI
        ws.copau00.copau0ai.paprv04i.setValue(ws.wsVariables.wsAuthAprvStat);
        // COB(618): MOVE PA-MATCH-STATUS   TO PSTAT04I OF COPAU0AI
        ws.copau00.copau0ai.pstat04i.setValue(ws.pendingAuthDetails.paMatchStatus);
        // COB(619): MOVE WS-AUTH-AMT       TO PAMT004I OF COPAU0AI
        ws.copau00.copau0ai.pamt004i.setValue(ws.wsVariables.wsAuthAmt);
        // COB(620): MOVE DFHBMUNP          TO SEL0004A OF COPAU0AI
        ws.copau00.copau0ai.filler291.sel0004a.setValue(Dfhbmsca.DFHBMUNP.getValue());
        // COB(621): WHEN 5
        break;
      case 5:
        // COB(622): MOVE PA-AUTHORIZATION-KEY
        // COB(622):                        TO CDEMO-CPVS-AUTH-KEYS(5)
        ws.carddemoCommarea
            .cdemoCpvsInfo
            .cdemoCpvsAuthKeysAtIndex(4)
            .setValue(ws.pendingAuthDetails.paAuthorizationKey);
        // COB(625): MOVE PA-TRANSACTION-ID TO TRNID05I OF COPAU0AI
        ws.copau00.copau0ai.trnid05i.setValue(ws.pendingAuthDetails.paTransactionId);
        // COB(626): MOVE WS-AUTH-DATE      TO PDATE05I OF COPAU0AI
        ws.copau00.copau0ai.pdate05i.setValue(ws.wsVariables.wsAuthDate);
        // COB(627): MOVE WS-AUTH-TIME      TO PTIME05I OF COPAU0AI
        ws.copau00.copau0ai.ptime05i.setValue(ws.wsVariables.wsAuthTime);
        // COB(628): MOVE PA-AUTH-TYPE      TO PTYPE05I OF COPAU0AI
        ws.copau00.copau0ai.ptype05i.setValue(ws.pendingAuthDetails.paAuthType);
        // COB(629): MOVE WS-AUTH-APRV-STAT TO PAPRV05I OF COPAU0AI
        ws.copau00.copau0ai.paprv05i.setValue(ws.wsVariables.wsAuthAprvStat);
        // COB(630): MOVE PA-MATCH-STATUS   TO PSTAT05I OF COPAU0AI
        ws.copau00.copau0ai.pstat05i.setValue(ws.pendingAuthDetails.paMatchStatus);
        // COB(631): MOVE WS-AUTH-AMT       TO PAMT005I OF COPAU0AI
        ws.copau00.copau0ai.pamt005i.setValue(ws.wsVariables.wsAuthAmt);
        // COB(632): MOVE DFHBMUNP          TO SEL0005A OF COPAU0AI
        ws.copau00.copau0ai.filler381.sel0005a.setValue(Dfhbmsca.DFHBMUNP.getValue());
        // COB(633): WHEN OTHER
        break;
      default:
        // COB(634): CONTINUE
        // do nothing
        // COB(635): END-EVALUATE.
    }
  }

  /***
   *****************************************************************/
  protected void initializeAuthData() {
    // COB(641): PERFORM VARYING WS-IDX FROM 1 BY 1 UNTIL WS-IDX > 5
    ws.wsVariables.wsIdx.setValue(1);
    for (; !ws.wsVariables.wsIdx.greaterThan(5); ws.wsVariables.wsIdx.add(1)) {
      // COB(642): EVALUATE WS-IDX
      switch (ws.wsVariables.wsIdx.getInt()) {
          // COB(643): WHEN 1
        case 1:
          // COB(644): MOVE DFHBMPRO TO SEL0001A OF COPAU0AI
          ws.copau00.copau0ai.filler147.sel0001a.setValue(Dfhbmsca.DFHBMPRO.getValue());
          // COB(645): MOVE SPACES   TO TRNID01I OF COPAU0AI
          ws.copau00.copau0ai.trnid01i.spaces();
          // COB(646): MOVE SPACES   TO PDATE01I OF COPAU0AI
          ws.copau00.copau0ai.pdate01i.spaces();
          // COB(647): MOVE SPACES   TO PTIME01I OF COPAU0AI
          ws.copau00.copau0ai.ptime01i.spaces();
          // COB(648): MOVE SPACES   TO PTYPE01I OF COPAU0AI
          ws.copau00.copau0ai.ptype01i.spaces();
          // COB(649): MOVE SPACES   TO PAPRV01I OF COPAU0AI
          ws.copau00.copau0ai.paprv01i.spaces();
          // COB(650): MOVE SPACES   TO PSTAT01I OF COPAU0AI
          ws.copau00.copau0ai.pstat01i.spaces();
          // COB(651): MOVE SPACES   TO PAMT001I OF COPAU0AI
          ws.copau00.copau0ai.pamt001i.spaces();
          // COB(652): WHEN 2
          break;
        case 2:
          // COB(653): MOVE DFHBMPRO TO SEL0002A OF COPAU0AI
          ws.copau00.copau0ai.filler195.sel0002a.setValue(Dfhbmsca.DFHBMPRO.getValue());
          // COB(654): MOVE SPACES   TO TRNID02I OF COPAU0AI
          ws.copau00.copau0ai.trnid02i.spaces();
          // COB(655): MOVE SPACES   TO PDATE02I OF COPAU0AI
          ws.copau00.copau0ai.pdate02i.spaces();
          // COB(656): MOVE SPACES   TO PTIME02I OF COPAU0AI
          ws.copau00.copau0ai.ptime02i.spaces();
          // COB(657): MOVE SPACES   TO PTYPE02I OF COPAU0AI
          ws.copau00.copau0ai.ptype02i.spaces();
          // COB(658): MOVE SPACES   TO PAPRV02I OF COPAU0AI
          ws.copau00.copau0ai.paprv02i.spaces();
          // COB(659): MOVE SPACES   TO PSTAT02I OF COPAU0AI
          ws.copau00.copau0ai.pstat02i.spaces();
          // COB(660): MOVE SPACES   TO PAMT002I OF COPAU0AI
          ws.copau00.copau0ai.pamt002i.spaces();
          // COB(661): WHEN 3
          break;
        case 3:
          // COB(662): MOVE DFHBMPRO TO SEL0003A OF COPAU0AI
          ws.copau00.copau0ai.filler243.sel0003a.setValue(Dfhbmsca.DFHBMPRO.getValue());
          // COB(663): MOVE SPACES   TO TRNID03I OF COPAU0AI
          ws.copau00.copau0ai.trnid03i.spaces();
          // COB(664): MOVE SPACES   TO PDATE03I OF COPAU0AI
          ws.copau00.copau0ai.pdate03i.spaces();
          // COB(665): MOVE SPACES   TO PTIME03I OF COPAU0AI
          ws.copau00.copau0ai.ptime03i.spaces();
          // COB(666): MOVE SPACES   TO PTYPE03I OF COPAU0AI
          ws.copau00.copau0ai.ptype03i.spaces();
          // COB(667): MOVE SPACES   TO PAPRV03I OF COPAU0AI
          ws.copau00.copau0ai.paprv03i.spaces();
          // COB(668): MOVE SPACES   TO PSTAT03I OF COPAU0AI
          ws.copau00.copau0ai.pstat03i.spaces();
          // COB(669): MOVE SPACES   TO PAMT003I OF COPAU0AI
          ws.copau00.copau0ai.pamt003i.spaces();
          // COB(670): WHEN 4
          break;
        case 4:
          // COB(671): MOVE DFHBMPRO TO SEL0004A OF COPAU0AI
          ws.copau00.copau0ai.filler291.sel0004a.setValue(Dfhbmsca.DFHBMPRO.getValue());
          // COB(672): MOVE SPACES   TO TRNID04I OF COPAU0AI
          ws.copau00.copau0ai.trnid04i.spaces();
          // COB(673): MOVE SPACES   TO PDATE04I OF COPAU0AI
          ws.copau00.copau0ai.pdate04i.spaces();
          // COB(674): MOVE SPACES   TO PTIME04I OF COPAU0AI
          ws.copau00.copau0ai.ptime04i.spaces();
          // COB(675): MOVE SPACES   TO PTYPE04I OF COPAU0AI
          ws.copau00.copau0ai.ptype04i.spaces();
          // COB(676): MOVE SPACES   TO PAPRV04I OF COPAU0AI
          ws.copau00.copau0ai.paprv04i.spaces();
          // COB(677): MOVE SPACES   TO PSTAT04I OF COPAU0AI
          ws.copau00.copau0ai.pstat04i.spaces();
          // COB(678): MOVE SPACES   TO PAMT004I OF COPAU0AI
          ws.copau00.copau0ai.pamt004i.spaces();
          // COB(679): WHEN 5
          break;
        case 5:
          // COB(680): MOVE DFHBMPRO TO SEL0005A OF COPAU0AI
          ws.copau00.copau0ai.filler381.sel0005a.setValue(Dfhbmsca.DFHBMPRO.getValue());
          // COB(681): MOVE SPACES   TO TRNID05I OF COPAU0AI
          ws.copau00.copau0ai.trnid05i.spaces();
          // COB(682): MOVE SPACES   TO PDATE05I OF COPAU0AI
          ws.copau00.copau0ai.pdate05i.spaces();
          // COB(683): MOVE SPACES   TO PTIME05I OF COPAU0AI
          ws.copau00.copau0ai.ptime05i.spaces();
          // COB(684): MOVE SPACES   TO PTYPE05I OF COPAU0AI
          ws.copau00.copau0ai.ptype05i.spaces();
          // COB(685): MOVE SPACES   TO PAPRV05I OF COPAU0AI
          ws.copau00.copau0ai.paprv05i.spaces();
          // COB(686): MOVE SPACES   TO PSTAT05I OF COPAU0AI
          ws.copau00.copau0ai.pstat05i.spaces();
          // COB(687): MOVE SPACES   TO PAMT005I OF COPAU0AI
          ws.copau00.copau0ai.pamt005i.spaces();
          // COB(688): WHEN OTHER
          break;
        default:
          // COB(689): CONTINUE
          // do nothing
          // COB(690): END-EVALUATE
      }
      // COB(691): END-PERFORM
    }
  }

  /***
   *****************************************************************/
  protected void returnToPrevScreen() {
    // COB(698): IF CDEMO-TO-PROGRAM = LOW-VALUES OR SPACES
    if (ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isLowValues()
        || ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isSpaces()) {
      // COB(699): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(700): END-IF
    }
    // COB(701): MOVE WS-CICS-TRANID  TO CDEMO-FROM-TRANID
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsVariables.wsCicsTranid);
    // COB(702): MOVE WS-PGM-AUTH-SMRY TO CDEMO-FROM-PROGRAM
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsVariables.wsPgmAuthSmry);
    // COB(703): MOVE ZEROS           TO CDEMO-PGM-CONTEXT
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.setValue(0);
    // COB(704): EXEC CICS
    // COB(704):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
    // COB(704):     COMMAREA(CARDDEMO-COMMAREA)
    // COB(704): END-EXEC.
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
   *****************************************************************/
  protected void sendPaulstScreen() {
    // COB(714): IF IMS-PSB-SCHD
    if (ws.wsImsVariables.imsPsbSchd()) {
      // COB(715): SET IMS-PSB-NOT-SCHD      TO TRUE
      ws.wsImsVariables.setImsPsbNotSchd(true);
      // COB(718): END-IF
      //       *       EXEC CICS SYNCPOINT
      //       *       END-EXEC
    }
    // COB(720): PERFORM POPULATE-HEADER-INFO
    populateHeaderInfo();
    // COB(722): MOVE WS-MESSAGE TO ERRMSGO OF COPAU0AO
    ws.copau00.copau0ao.errmsgo.setValue(ws.wsVariables.wsMessage);
    // COB(724): IF SEND-ERASE-YES
    if (ws.wsSwitches.sendEraseYes()) {
      // COB(725): EXEC CICS SEND
      // COB(725):           MAP('COPAU0A')
      // COB(725):           MAPSET('COPAU00')
      // COB(725):           FROM(COPAU0AO)
      // COB(725):           ERASE
      // COB(725):           CURSOR
      // COB(725): END-EXEC
      try {
        supernaut
            .sendMap("COPAU0A") //
            .mapset("COPAU00") //
            .from(ws.copau00.copau0ao) //
            .erase() //
            .cursor() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(732): ELSE
    } else {
      // COB(733): EXEC CICS SEND
      // COB(733):           MAP('COPAU0A')
      // COB(733):           MAPSET('COPAU00')
      // COB(733):           FROM(COPAU0AO)
      // COB(733):           CURSOR
      // COB(733): END-EXEC
      try {
        supernaut
            .sendMap("COPAU0A") //
            .mapset("COPAU00") //
            .from(ws.copau00.copau0ao) //
            .cursor() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(739): END-IF.
    }
  }

  /***
   *****************************************************************/
  protected void receivePaulstScreen() {
    // COB(745): EXEC CICS RECEIVE
    // COB(745):           MAP('COPAU0A')
    // COB(745):           MAPSET('COPAU00')
    // COB(745):           INTO(COPAU0AI)
    // COB(745):           RESP(WS-RESP-CD)
    // COB(745):           RESP2(WS-REAS-CD)
    // COB(745): END-EXEC
    supernaut
        .receiveMap("COPAU0A") //
        .mapset("COPAU00") //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.copau00.copau0ai) //
        .exec();
  }

  /***
   *
   *****************************************************************/
  protected void populateHeaderInfo() {
    // COB(759): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(761): MOVE CCDA-TITLE01           TO TITLE01O OF COPAU0AO
    ws.copau00.copau0ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(762): MOVE CCDA-TITLE02           TO TITLE02O OF COPAU0AO
    ws.copau00.copau0ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(763): MOVE WS-CICS-TRANID         TO TRNNAMEO OF COPAU0AO
    ws.copau00.copau0ao.trnnameo.setValue(ws.wsVariables.wsCicsTranid);
    // COB(764): MOVE WS-PGM-AUTH-SMRY       TO PGMNAMEO OF COPAU0AO
    ws.copau00.copau0ao.pgmnameo.setValue(ws.wsVariables.wsPgmAuthSmry);
    // COB(766): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(767): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(768): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear.getString(2, 2));
    // COB(770): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF COPAU0AO
    ws.copau00.copau0ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(772): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(773): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(774): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(776): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF COPAU0AO
    ws.copau00.copau0ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***
   *****************************************************************/
  protected void gatherAccountDetails() {
    // COB(783): PERFORM GETCARDXREF-BYACCT
    getcardxrefByacct();
    // COB(784): PERFORM GETACCTDATA-BYACCT
    getacctdataByacct();
    // COB(785): PERFORM GETCUSTDATA-BYCUST
    getcustdataBycust();
    // COB(787): MOVE CUST-ID                TO CUSTIDO
    ws.copau00.copau0ao.custido.setValue(ws.cvcus01y.customerRecord.custId);
    // COB(788): STRING CUST-FIRST-NAME DELIMITED BY SPACES
    // COB(788):        ' ' DELIMITED BY SIZE
    // COB(788):        CUST-MIDDLE-NAME(1:1) DELIMITED BY SIZE
    // COB(788):        ' ' DELIMITED BY SIZE
    // COB(788):        CUST-LAST-NAME DELIMITED BY SPACES
    // COB(788):        INTO CNAMEO
    // COB(788): END-STRING
    ws.copau00.copau0ao.cnameo.concat(
        ws.cvcus01y.customerRecord.custFirstName.substringToValue(" "),
        " ",
        ws.cvcus01y.customerRecord.custMiddleName.getString(0, 1),
        " ",
        ws.cvcus01y.customerRecord.custLastName.substringToValue(" "));
    // COB(794): END-STRING
    // COB(796): STRING CUST-ADDR-LINE-1 DELIMITED BY '  '
    // COB(796):        ',' DELIMITED BY SIZE
    // COB(796):        CUST-ADDR-LINE-2 DELIMITED BY '  '
    // COB(796):        INTO ADDR001O
    // COB(796): END-STRING
    ws.copau00.copau0ao.addr001o.concat(
        ws.cvcus01y.customerRecord.custAddrLine1.substringToValue("  "),
        ",",
        ws.cvcus01y.customerRecord.custAddrLine2.substringToValue("  "));
    // COB(800): END-STRING
    // COB(801): STRING CUST-ADDR-LINE-3 DELIMITED BY '  '
    // COB(801):        ',' DELIMITED BY SIZE
    // COB(801):        CUST-ADDR-STATE-CD DELIMITED BY SIZE
    // COB(801):        ',' DELIMITED BY SIZE
    // COB(801):        CUST-ADDR-ZIP(1:5) DELIMITED BY SIZE
    // COB(801):        INTO ADDR002O
    // COB(801): END-STRING
    ws.copau00.copau0ao.addr002o.concat(
        ws.cvcus01y.customerRecord.custAddrLine3.substringToValue("  "),
        ",",
        ws.cvcus01y.customerRecord.custAddrStateCd,
        ",",
        ws.cvcus01y.customerRecord.custAddrZip.getString(0, 5));
    // COB(807): END-STRING
    // COB(809): MOVE CUST-PHONE-NUM-1       TO PHONE1O
    ws.copau00.copau0ao.phone1o.setValue(ws.cvcus01y.customerRecord.custPhoneNum1);
    // COB(810): MOVE ACCT-CREDIT-LIMIT      TO WS-DISPLAY-AMT12
    ws.wsVariables.wsDisplayAmt12.setValue(ws.cvact01y.accountRecord.acctCreditLimit);
    // COB(811): MOVE WS-DISPLAY-AMT12       TO CREDLIMO
    ws.copau00.copau0ao.credlimo.setValue(ws.wsVariables.wsDisplayAmt12);
    // COB(812): MOVE ACCT-CASH-CREDIT-LIMIT TO WS-DISPLAY-AMT9
    ws.wsVariables.wsDisplayAmt9.setValue(ws.cvact01y.accountRecord.acctCashCreditLimit);
    // COB(813): MOVE WS-DISPLAY-AMT9        TO CASHLIMO
    ws.copau00.copau0ao.cashlimo.setValue(ws.wsVariables.wsDisplayAmt9);
    // COB(815): PERFORM GET-AUTH-SUMMARY
    getAuthSummary();
    // COB(817): IF FOUND-PAUT-SMRY-SEG
    if (ws.wsSwitches.foundPautSmrySeg()) {
      // COB(818): MOVE PA-APPROVED-AUTH-CNT   TO WS-DISPLAY-COUNT
      ws.wsVariables.wsDisplayCount.setValue(ws.pendingAuthSummary.paApprovedAuthCnt);
      // COB(819): MOVE WS-DISPLAY-COUNT       TO APPRCNTO
      ws.copau00.copau0ao.apprcnto.setValue(ws.wsVariables.wsDisplayCount);
      // COB(820): MOVE PA-DECLINED-AUTH-CNT   TO WS-DISPLAY-COUNT
      ws.wsVariables.wsDisplayCount.setValue(ws.pendingAuthSummary.paDeclinedAuthCnt);
      // COB(821): MOVE WS-DISPLAY-COUNT       TO DECLCNTO
      ws.copau00.copau0ao.declcnto.setValue(ws.wsVariables.wsDisplayCount);
      // COB(822): MOVE PA-CREDIT-BALANCE      TO WS-DISPLAY-AMT12
      ws.wsVariables.wsDisplayAmt12.setValue(ws.pendingAuthSummary.paCreditBalance);
      // COB(823): MOVE WS-DISPLAY-AMT12       TO CREDBALO
      ws.copau00.copau0ao.credbalo.setValue(ws.wsVariables.wsDisplayAmt12);
      // COB(824): MOVE PA-CASH-BALANCE        TO WS-DISPLAY-AMT9
      ws.wsVariables.wsDisplayAmt9.setValue(ws.pendingAuthSummary.paCashBalance);
      // COB(825): MOVE WS-DISPLAY-AMT9        TO CASHBALO
      ws.copau00.copau0ao.cashbalo.setValue(ws.wsVariables.wsDisplayAmt9);
      // COB(826): MOVE PA-APPROVED-AUTH-AMT   TO WS-DISPLAY-AMT9
      ws.wsVariables.wsDisplayAmt9.setValue(ws.pendingAuthSummary.paApprovedAuthAmt);
      // COB(827): MOVE WS-DISPLAY-AMT9        TO APPRAMTO
      ws.copau00.copau0ao.appramto.setValue(ws.wsVariables.wsDisplayAmt9);
      // COB(828): MOVE PA-DECLINED-AUTH-AMT   TO WS-DISPLAY-AMT9
      ws.wsVariables.wsDisplayAmt9.setValue(ws.pendingAuthSummary.paDeclinedAuthAmt);
      // COB(829): MOVE WS-DISPLAY-AMT9        TO DECLAMTO
      ws.copau00.copau0ao.declamto.setValue(ws.wsVariables.wsDisplayAmt9);
      // COB(830): ELSE
    } else {
      // COB(831): MOVE ZERO                   TO APPRCNTO
      // COB(831):                                DECLCNTO
      // COB(831):                                CREDBALO
      // COB(831):                                CASHBALO
      // COB(831):                                APPRAMTO
      // COB(831):                                DECLAMTO
      ws.copau00.copau0ao.apprcnto.setValue(0);
      ws.copau00.copau0ao.declcnto.setValue(0);
      ws.copau00.copau0ao.credbalo.setValue(0);
      ws.copau00.copau0ao.cashbalo.setValue(0);
      ws.copau00.copau0ao.appramto.setValue(0);
      ws.copau00.copau0ao.declamto.setValue(0);
      // COB(837): END-IF
    }
  }

  /***
   *
   *****************************************************************/
  protected void getcardxrefByacct() {
    // COB(847): MOVE WS-ACCT-ID          TO WS-CARD-RID-ACCT-ID-X
    ws.wsVariables.wsXrefRid.wsCardRidAcctIdX.setValue(ws.wsVariables.wsAcctId);
    // COB(848): EXEC CICS READ
    // COB(848):      DATASET   (WS-CARDXREFNAME-ACCT-PATH)
    // COB(848):      RIDFLD    (WS-CARD-RID-ACCT-ID-X)
    // COB(848):      KEYLENGTH (LENGTH OF WS-CARD-RID-ACCT-ID-X)
    // COB(848):      INTO      (CARD-XREF-RECORD)
    // COB(848):      LENGTH    (LENGTH OF CARD-XREF-RECORD)
    // COB(848):      RESP      (WS-RESP-CD)
    // COB(848):      RESP2     (WS-REAS-CD)
    // COB(848): END-EXEC
    supernaut
        .read(ws.wsVariables.wsCardxrefnameAcctPath.getValue()) //
        .length(ws.cvact03y.cardXrefRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cvact03y.cardXrefRecord) //
        .ridfld(ws.wsVariables.wsXrefRid.wsCardRidAcctIdX) //
        .keylength(ws.wsVariables.wsXrefRid.wsCardRidAcctIdX.length()) //
        .exec();
    // COB(858): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(859): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(860): MOVE XREF-CUST-ID               TO CDEMO-CUST-ID
        ws.carddemoCommarea.cdemoCustomerInfo.cdemoCustId.setValue(
            ws.cvact03y.cardXrefRecord.xrefCustId);
        // COB(861): MOVE XREF-CARD-NUM              TO CDEMO-CARD-NUM
        ws.carddemoCommarea.cdemoCardInfo.cdemoCardNum.setValue(
            ws.cvact03y.cardXrefRecord.xrefCardNum);
        // COB(862): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(863): MOVE WS-RESP-CD        TO WS-RESP-CD-DIS
        ws.wsVariables.wsRespCdDis.setValue(ws.wsVariables.wsRespCd);
        // COB(864): MOVE WS-REAS-CD        TO WS-REAS-CD-DIS
        ws.wsVariables.wsReasCdDis.setValue(ws.wsVariables.wsReasCd);
        // COB(866): STRING
        // COB(866): 'Account:'
        // COB(866):  WS-ACCT-ID
        // COB(866): ' not found in XREF file. Resp:' WS-RESP-CD-DIS
        // COB(866): ' Reas:' WS-REAS-CD-DIS
        // COB(866): DELIMITED BY SIZE
        // COB(866): INTO WS-MESSAGE
        // COB(866): END-STRING
        ws.wsVariables.wsMessage.concat(
            "Account:",
            ws.wsVariables.wsAcctId,
            " not found in XREF file. Resp:",
            ws.wsVariables.wsRespCdDis,
            " Reas:",
            ws.wsVariables.wsReasCdDis);
        // COB(873): END-STRING
        // COB(874): MOVE -1       TO ACCTIDL OF COPAU0AI
        ws.copau00.copau0ai.acctidl.setValue(-1);
        // COB(875): PERFORM SEND-PAULST-SCREEN
        sendPaulstScreen();
        // COB(876): WHEN OTHER
        break;
      default:
        // COB(877): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsSwitches.wsErrFlg.setString("Y");
        // COB(878): MOVE WS-RESP-CD        TO WS-RESP-CD-DIS
        ws.wsVariables.wsRespCdDis.setValue(ws.wsVariables.wsRespCd);
        // COB(879): MOVE WS-REAS-CD        TO WS-REAS-CD-DIS
        ws.wsVariables.wsReasCdDis.setValue(ws.wsVariables.wsReasCd);
        // COB(881): STRING
        // COB(881): 'Account:'
        // COB(881):  WS-CARD-RID-ACCT-ID-X
        // COB(881): ' System error while reading XREF file. Resp:'
        // COB(881): WS-RESP-CD-DIS ' Reas:' WS-REAS-CD-DIS
        // COB(881): DELIMITED BY SIZE
        // COB(881): INTO WS-MESSAGE
        // COB(881): END-STRING
        ws.wsVariables.wsMessage.concat(
            "Account:",
            ws.wsVariables.wsXrefRid.wsCardRidAcctIdX,
            " System error while reading XREF file. Resp:",
            ws.wsVariables.wsRespCdDis,
            " Reas:",
            ws.wsVariables.wsReasCdDis);
        // COB(888): END-STRING
        // COB(889): MOVE -1       TO ACCTIDL OF COPAU0AI
        ws.copau00.copau0ai.acctidl.setValue(-1);
        // COB(890): PERFORM SEND-PAULST-SCREEN
        sendPaulstScreen();
        // COB(891): END-EVALUATE
    }
  }

  /***
   *****************************************************************/
  protected void getacctdataByacct() {
    // COB(898): MOVE XREF-ACCT-ID            TO WS-CARD-RID-ACCT-ID
    ws.wsVariables.wsXrefRid.wsCardRidAcctId.setValue(ws.cvact03y.cardXrefRecord.xrefAcctId);
    // COB(899): EXEC CICS READ
    // COB(899):      DATASET   (WS-ACCTFILENAME)
    // COB(899):      RIDFLD    (WS-CARD-RID-ACCT-ID-X)
    // COB(899):      KEYLENGTH (LENGTH OF WS-CARD-RID-ACCT-ID-X)
    // COB(899):      INTO      (ACCOUNT-RECORD)
    // COB(899):      LENGTH    (LENGTH OF ACCOUNT-RECORD)
    // COB(899):      RESP      (WS-RESP-CD)
    // COB(899):      RESP2     (WS-REAS-CD)
    // COB(899): END-EXEC
    supernaut
        .read(ws.wsVariables.wsAcctfilename.getValue()) //
        .length(ws.cvact01y.accountRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cvact01y.accountRecord) //
        .ridfld(ws.wsVariables.wsXrefRid.wsCardRidAcctIdX) //
        .keylength(ws.wsVariables.wsXrefRid.wsCardRidAcctIdX.length()) //
        .exec();
    // COB(909): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(910): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(911): continue
        // do nothing
        // COB(912): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(913): MOVE WS-RESP-CD        TO WS-RESP-CD-DIS
        ws.wsVariables.wsRespCdDis.setValue(ws.wsVariables.wsRespCd);
        // COB(914): MOVE WS-REAS-CD        TO WS-REAS-CD-DIS
        ws.wsVariables.wsReasCdDis.setValue(ws.wsVariables.wsReasCd);
        // COB(916): STRING
        // COB(916): 'Account:'
        // COB(916):  WS-CARD-RID-ACCT-ID-X
        // COB(916): ' not found in ACCT file. Resp:' WS-RESP-CD-DIS
        // COB(916): ' Reas:' WS-REAS-CD-DIS
        // COB(916): DELIMITED BY SIZE
        // COB(916): INTO WS-MESSAGE
        // COB(916): END-STRING
        ws.wsVariables.wsMessage.concat(
            "Account:",
            ws.wsVariables.wsXrefRid.wsCardRidAcctIdX,
            " not found in ACCT file. Resp:",
            ws.wsVariables.wsRespCdDis,
            " Reas:",
            ws.wsVariables.wsReasCdDis);
        // COB(923): END-STRING
        // COB(924): MOVE -1       TO ACCTIDL OF COPAU0AI
        ws.copau00.copau0ai.acctidl.setValue(-1);
        // COB(925): PERFORM SEND-PAULST-SCREEN
        sendPaulstScreen();
        // COB(926): WHEN OTHER
        break;
      default:
        // COB(927): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsSwitches.wsErrFlg.setString("Y");
        // COB(928): MOVE WS-RESP-CD        TO WS-RESP-CD-DIS
        ws.wsVariables.wsRespCdDis.setValue(ws.wsVariables.wsRespCd);
        // COB(929): MOVE WS-REAS-CD        TO WS-REAS-CD-DIS
        ws.wsVariables.wsReasCdDis.setValue(ws.wsVariables.wsReasCd);
        // COB(931): STRING
        // COB(931): 'Account:'
        // COB(931):  WS-CARD-RID-ACCT-ID-X
        // COB(931): ' System error while reading ACCT file. Resp:'
        // COB(931): WS-RESP-CD-DIS ' Reas:' WS-REAS-CD-DIS
        // COB(931): DELIMITED BY SIZE
        // COB(931): INTO WS-MESSAGE
        // COB(931): END-STRING
        ws.wsVariables.wsMessage.concat(
            "Account:",
            ws.wsVariables.wsXrefRid.wsCardRidAcctIdX,
            " System error while reading ACCT file. Resp:",
            ws.wsVariables.wsRespCdDis,
            " Reas:",
            ws.wsVariables.wsReasCdDis);
        // COB(938): END-STRING
        // COB(939): MOVE -1       TO ACCTIDL OF COPAU0AI
        ws.copau00.copau0ai.acctidl.setValue(-1);
        // COB(940): PERFORM SEND-PAULST-SCREEN
        sendPaulstScreen();
        // COB(941): END-EVALUATE
    }
  }

  /***
   *****************************************************************/
  protected void getcustdataBycust() {
    // COB(948): MOVE XREF-CUST-ID              TO WS-CARD-RID-CUST-ID
    ws.wsVariables.wsXrefRid.wsCardRidCustId.setValue(ws.cvact03y.cardXrefRecord.xrefCustId);
    // COB(950): EXEC CICS READ
    // COB(950):      DATASET   (WS-CUSTFILENAME)
    // COB(950):      RIDFLD    (WS-CARD-RID-CUST-ID-X)
    // COB(950):      KEYLENGTH (LENGTH OF WS-CARD-RID-CUST-ID-X)
    // COB(950):      INTO      (CUSTOMER-RECORD)
    // COB(950):      LENGTH    (LENGTH OF CUSTOMER-RECORD)
    // COB(950):      RESP      (WS-RESP-CD)
    // COB(950):      RESP2     (WS-REAS-CD)
    // COB(950): END-EXEC
    supernaut
        .read(ws.wsVariables.wsCustfilename.getValue()) //
        .length(ws.cvcus01y.customerRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cvcus01y.customerRecord) //
        .ridfld(ws.wsVariables.wsXrefRid.wsCardRidCustIdX) //
        .keylength(ws.wsVariables.wsXrefRid.wsCardRidCustIdX.length()) //
        .exec();
    // COB(960): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(961): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(962): CONTINUE
        // do nothing
        // COB(963): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(964): MOVE WS-RESP-CD        TO WS-RESP-CD-DIS
        ws.wsVariables.wsRespCdDis.setValue(ws.wsVariables.wsRespCd);
        // COB(965): MOVE WS-REAS-CD        TO WS-REAS-CD-DIS
        ws.wsVariables.wsReasCdDis.setValue(ws.wsVariables.wsReasCd);
        // COB(967): STRING
        // COB(967): 'Customer:'
        // COB(967):  WS-CARD-RID-CUST-ID-X
        // COB(967): ' not found in CUST file. Resp:' WS-RESP-CD-DIS
        // COB(967): ' Reas:' WS-REAS-CD-DIS
        // COB(967): DELIMITED BY SIZE
        // COB(967): INTO WS-MESSAGE
        // COB(967): END-STRING
        ws.wsVariables.wsMessage.concat(
            "Customer:",
            ws.wsVariables.wsXrefRid.wsCardRidCustIdX,
            " not found in CUST file. Resp:",
            ws.wsVariables.wsRespCdDis,
            " Reas:",
            ws.wsVariables.wsReasCdDis);
        // COB(974): END-STRING
        // COB(975): MOVE -1       TO ACCTIDL OF COPAU0AI
        ws.copau00.copau0ai.acctidl.setValue(-1);
        // COB(976): PERFORM SEND-PAULST-SCREEN
        sendPaulstScreen();
        // COB(977): WHEN OTHER
        break;
      default:
        // COB(978): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsSwitches.wsErrFlg.setString("Y");
        // COB(979): MOVE WS-RESP-CD        TO WS-RESP-CD-DIS
        ws.wsVariables.wsRespCdDis.setValue(ws.wsVariables.wsRespCd);
        // COB(980): MOVE WS-REAS-CD        TO WS-REAS-CD-DIS
        ws.wsVariables.wsReasCdDis.setValue(ws.wsVariables.wsReasCd);
        // COB(982): STRING
        // COB(982): 'Customer:'
        // COB(982):  WS-CARD-RID-CUST-ID-X
        // COB(982): ' System error while reading CUST file. Resp:'
        // COB(982): WS-RESP-CD-DIS ' Reas:' WS-REAS-CD-DIS
        // COB(982): DELIMITED BY SIZE
        // COB(982): INTO WS-MESSAGE
        // COB(982): END-STRING
        ws.wsVariables.wsMessage.concat(
            "Customer:",
            ws.wsVariables.wsXrefRid.wsCardRidCustIdX,
            " System error while reading CUST file. Resp:",
            ws.wsVariables.wsRespCdDis,
            " Reas:",
            ws.wsVariables.wsReasCdDis);
        // COB(989): END-STRING
        // COB(990): MOVE -1       TO ACCTIDL OF COPAU0AI
        ws.copau00.copau0ai.acctidl.setValue(-1);
        // COB(991): PERFORM SEND-PAULST-SCREEN
        sendPaulstScreen();
        // COB(992): END-EVALUATE
    }
  }

  /***
   *****************************************************************/
  protected void getAuthSummary() {
    // COB(999): PERFORM SCHEDULE-PSB
    schedulePsb();
    // COB(1001): MOVE CDEMO-ACCT-ID                   TO PA-ACCT-ID
    ws.pendingAuthSummary.paAcctId.setValue(ws.carddemoCommarea.cdemoAccountInfo.cdemoAcctId);
    // COB(1008): IRISDB     MOVE 3 TO IRIS-CALL-ID
    //       *    MOVE XREF-ACCT-ID                    TO PA-ACCT-ID
    //       *    EXEC DLI GU USING PCB(PAUT-PCB-NUM)
    //       *        SEGMENT (PAUTSUM0)
    //       *        INTO (PENDING-AUTH-SUMMARY)
    //       *        WHERE (ACCNTID = PA-ACCT-ID)
    //       *    END-EXEC
    ws.irisWorkArea.irisCallId.setValue(3);
    // COB(1009): IRISDB     MOVE SPACE TO IRIS-KEYVALUE-TAB
    ws.irisWorkArea.irisKeyvalueTab.spaces();
    // COB(1010): IRISDB     MOVE 'DBPAUTP0' TO IRIS-WS-RTN
    ws.iriscomm.irisWsRtn.setString("DBPAUTP0");
    // COB(1011): IRISDB     SET IRIS-FUNC-GU TO TRUE
    ws.irisWorkArea.setIrisFuncGu(true);
    // COB(1012): IRISDB     MOVE 8 TO IRIS-CUSTOM-FUNC-ID
    ws.irisWorkArea.irisCustomFuncId.setValue(8);
    // COB(1013): IRISDB     MOVE PAUT-PCB-NUM TO IRIS-PCB-NUM
    ws.irisWorkArea.irisPcbNum.setValue(ws.wsImsVariables.pcbOffset.pautPcbNum);
    // COB(1014): IRISDB     IF PA-ACCT-ID NOT NUMERIC
    if (!ws.pendingAuthSummary.paAcctId.isNumeric()) {
      // COB(1015): IRISDB       MOVE ZERO TO IRIS-KEYVALUE-PACKED(1, 1)
      ws.irisWorkArea
          .irisKeyvalueTab
          .irisKeysLevelsAtIndex(0)
          .irisKeyvaluesRed2
          .irisKeyvaluesPAtIndex(0)
          .irisKeyvaluePacked
          .setValue(0);
      // COB(1016): IRISDB     ELSE
    } else {
      // COB(1017): IRISDB       MOVE PA-ACCT-ID TO IRIS-KEYVALUE-PACKED(1, 1)
      ws.irisWorkArea
          .irisKeyvalueTab
          .irisKeysLevelsAtIndex(0)
          .irisKeyvaluesRed2
          .irisKeyvaluesPAtIndex(0)
          .irisKeyvaluePacked
          .setValue(ws.pendingAuthSummary.paAcctId);
      // COB(1018): IRISDB     END-IF
    }
    // COB(1019): IRISDB     MOVE 'PAUTSUM0' TO IRIS-SEGMENT
    ws.irisWorkArea.irisSegment.setString("PAUTSUM0");
    // COB(1020): IRISDB     SET IRIS-EXECDLI TO TRUE
    ws.irisWorkArea.setIrisExecdli(true);
    // COB(1021): IRISDB     SET SQL-USER-CUSTOM TO TRUE
    ws.irisWorkArea.setSqlUserCustom(true);
    // COB(1022): IRISDB     MOVE 3 TO IRIS-PARAM-NUM
    ws.irisWorkArea.irisParamNum.setValue(3);
    // COB(1023): IRISDB     CALL IRIS-WS-RTN USING IRIS-WORK-AREA
    // COB(1023): IRISDB                            DLZDIB
    // COB(1023): IRISDB                            PENDING-AUTH-SUMMARY
    supernaut.call(
        ws.iriscomm.irisWsRtn, ws.irisWorkArea, ws.iriscomm.dlzdib, ws.pendingAuthSummary);
    // COB(1026): MOVE DIBSTAT                          TO IMS-RETURN-CODE
    ws.wsImsVariables.imsReturnCode.setValue(ws.iriscomm.irisDibBlock.dibstat);
    // COB(1027): EVALUATE TRUE
    // COB(1028): WHEN STATUS-OK
    if (ws.wsImsVariables.statusOk()) {
      // COB(1029): SET FOUND-PAUT-SMRY-SEG        TO TRUE
      ws.wsSwitches.setFoundPautSmrySeg(true);
      // COB(1030): WHEN SEGMENT-NOT-FOUND
    } else if (ws.wsImsVariables.segmentNotFound()) {
      // COB(1031): SET NFOUND-PAUT-SMRY-SEG       TO TRUE
      ws.wsSwitches.setNfoundPautSmrySeg(true);
      // COB(1032): WHEN OTHER
    } else {
      // COB(1033): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsSwitches.wsErrFlg.setString("Y");
      // COB(1035): STRING
      // COB(1035): ' System error while reading AUTH Summary: Code:'
      // COB(1035): IMS-RETURN-CODE
      // COB(1035): DELIMITED BY SIZE
      // COB(1035): INTO WS-MESSAGE
      // COB(1035): END-STRING
      ws.wsVariables.wsMessage.concat(
          " System error while reading AUTH Summary: Code:", ws.wsImsVariables.imsReturnCode);
      // COB(1040): END-STRING
      // COB(1041): MOVE -1       TO ACCTIDL OF COPAU0AI
      ws.copau00.copau0ai.acctidl.setValue(-1);
      // COB(1042): PERFORM SEND-PAULST-SCREEN
      sendPaulstScreen();
      // COB(1043): END-EVALUATE
    }
  }

  /*****************************************************************
   * SCHEDULE PSB                                                  *
   *****************************************************************/
  protected void schedulePsb() {
    // COB(1053): IRISDB     MOVE 4 TO IRIS-CALL-ID
    ws.irisWorkArea.irisCallId.setValue(4);
    // COB(1054): IRISDB     MOVE SPACE TO IRIS-KEYVALUE-TAB
    ws.irisWorkArea.irisKeyvalueTab.spaces();
    // COB(1055): IRISDB     SET IRISUTIL-RTN TO TRUE
    ws.iriscomm.setIrisutilRtn(true);
    // COB(1056): IRISDB     SET IRIS-FUNC-SCHD TO TRUE
    ws.irisWorkArea.setIrisFuncSchd(true);
    // COB(1057): IRISDB     MOVE 6 TO IRIS-CUSTOM-FUNC-ID
    ws.irisWorkArea.irisCustomFuncId.setValue(6);
    // COB(1058): IRISDB     SET IRIS-EXECDLI TO TRUE
    ws.irisWorkArea.setIrisExecdli(true);
    // COB(1059): IRISDB     MOVE 2 TO IRIS-PARAM-NUM
    ws.irisWorkArea.irisParamNum.setValue(2);
    // COB(1060): IRISDB     CALL IRIS-WS-RTN USING IRIS-WORK-AREA
    // COB(1060): IRISDB                            DLZDIB
    // COB(1060): IRISDB                            PSB-NAME
    supernaut.call(
        ws.iriscomm.irisWsRtn, ws.irisWorkArea, ws.iriscomm.dlzdib, ws.wsImsVariables.psbName);
    // COB(1063): MOVE DIBSTAT        TO IMS-RETURN-CODE
    ws.wsImsVariables.imsReturnCode.setValue(ws.iriscomm.irisDibBlock.dibstat);
    // COB(1064): IF PSB-SCHEDULED-MORE-THAN-ONCE
    if (ws.wsImsVariables.psbScheduledMoreThanOnce()) {
      // COB(1067): IRISDB        MOVE 5 TO IRIS-CALL-ID
      //       *       EXEC DLI TERM
      //       *       END-EXEC
      ws.irisWorkArea.irisCallId.setValue(5);
      // COB(1068): IRISDB        SET IRISUTIL-RTN TO TRUE
      ws.iriscomm.setIrisutilRtn(true);
      // COB(1069): IRISDB        SET IRIS-FUNC-TERM TO TRUE
      ws.irisWorkArea.setIrisFuncTerm(true);
      // COB(1070): IRISDB        MOVE 7 TO IRIS-CUSTOM-FUNC-ID
      ws.irisWorkArea.irisCustomFuncId.setValue(7);
      // COB(1071): IRISDB        SET IRIS-EXECDLI TO TRUE
      ws.irisWorkArea.setIrisExecdli(true);
      // COB(1072): IRISDB        MOVE 2 TO IRIS-PARAM-NUM
      ws.irisWorkArea.irisParamNum.setValue(2);
      // COB(1073): IRISDB        CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      // COB(1073): IRISDB                               DLZDIB
      supernaut.call(ws.iriscomm.irisWsRtn, ws.irisWorkArea, ws.iriscomm.dlzdib);
      // COB(1080): IRISDB        MOVE 6 TO IRIS-CALL-ID
      //       *
      //       *       EXEC DLI SCHD
      //       *            PSB((PSB-NAME))
      //       *            NODHABEND
      //       *       END-EXEC
      ws.irisWorkArea.irisCallId.setValue(6);
      // COB(1081): IRISDB        MOVE SPACE TO IRIS-KEYVALUE-TAB
      ws.irisWorkArea.irisKeyvalueTab.spaces();
      // COB(1082): IRISDB        SET IRISUTIL-RTN TO TRUE
      ws.iriscomm.setIrisutilRtn(true);
      // COB(1083): IRISDB        SET IRIS-FUNC-SCHD TO TRUE
      ws.irisWorkArea.setIrisFuncSchd(true);
      // COB(1084): IRISDB        MOVE 6 TO IRIS-CUSTOM-FUNC-ID
      ws.irisWorkArea.irisCustomFuncId.setValue(6);
      // COB(1085): IRISDB        SET IRIS-EXECDLI TO TRUE
      ws.irisWorkArea.setIrisExecdli(true);
      // COB(1086): IRISDB        MOVE 2 TO IRIS-PARAM-NUM
      ws.irisWorkArea.irisParamNum.setValue(2);
      // COB(1087): IRISDB        CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      // COB(1087): IRISDB                               DLZDIB
      // COB(1087): IRISDB                               PSB-NAME
      supernaut.call(
          ws.iriscomm.irisWsRtn, ws.irisWorkArea, ws.iriscomm.dlzdib, ws.wsImsVariables.psbName);
      // COB(1090): MOVE DIBSTAT     TO IMS-RETURN-CODE
      ws.wsImsVariables.imsReturnCode.setValue(ws.iriscomm.irisDibBlock.dibstat);
      // COB(1091): END-IF
    }
    // COB(1092): IF STATUS-OK
    if (ws.wsImsVariables.statusOk()) {
      // COB(1093): SET IMS-PSB-SCHD           TO TRUE
      ws.wsImsVariables.setImsPsbSchd(true);
      // COB(1094): ELSE
    } else {
      // COB(1095): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsSwitches.wsErrFlg.setString("Y");
      // COB(1097): STRING
      // COB(1097):     ' System error while scheduling PSB: Code:'
      // COB(1097): IMS-RETURN-CODE
      // COB(1097): DELIMITED BY SIZE
      // COB(1097): INTO WS-MESSAGE
      // COB(1097): END-STRING
      ws.wsVariables.wsMessage.concat(
          " System error while scheduling PSB: Code:", ws.wsImsVariables.imsReturnCode);
      // COB(1102): END-STRING
      // COB(1103): MOVE -1       TO ACCTIDL OF COPAU0AI
      ws.copau00.copau0ai.acctidl.setValue(-1);
      // COB(1104): PERFORM SEND-PAULST-SCREEN
      sendPaulstScreen();
      // COB(1105): END-IF
    }
  }

  private enum HandleLabel {
    Not_Defined(0);

    private Integer value = null;

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
      default:
        throw new RuntimeException(e);
    }
  }
}
