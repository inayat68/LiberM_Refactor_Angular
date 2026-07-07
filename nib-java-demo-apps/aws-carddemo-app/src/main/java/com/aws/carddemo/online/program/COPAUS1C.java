package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.copaus1c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

import java.sql.SQLException;

public class COPAUS1C extends AbstractOnlineProgram {

  @ProgramStorage final Copaus1cWorking ws = new Copaus1cWorking();

  // Linkage
  public class Copaus1cLinkage extends NGroup {
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

  final Copaus1cLinkage params = new Copaus1cLinkage();

  public COPAUS1C(Context supernaut) {
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

  protected void mainPara() {
    // COB(165): SET ERR-FLG-OFF     TO TRUE
    ws.wsVariables.setErrFlgOff(true);
    // COB(166): SET SEND-ERASE-YES  TO TRUE
    ws.wsVariables.setSendEraseYes(true);
    // COB(168): MOVE SPACES TO WS-MESSAGE
    // COB(168):                ERRMSGO OF COPAU1AO
    ws.wsVariables.wsMessage.spaces();
    ws.copau01.copau1ao.errmsgo.spaces();
    // COB(171): IF EIBCALEN = 0
    if (dfheiblk.getEibcalen() == 0) {
      // COB(172): INITIALIZE CARDDEMO-COMMAREA
      ws.carddemoCommarea.initialize();
      // COB(174): MOVE WS-PGM-AUTH-SMRY        TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(ws.wsVariables.wsPgmAuthSmry);
      // COB(175): PERFORM RETURN-TO-PREV-SCREEN
      returnToPrevScreen();
      // COB(176): ELSE
    } else {
      // COB(177): MOVE DFHCOMMAREA(1:EIBCALEN) TO CARDDEMO-COMMAREA
      ws.carddemoCommarea.setValue(params.dfhcommarea, dfheiblk.getEibcalen());
      // COB(178): MOVE SPACES                  TO CDEMO-CPVD-FRAUD-DATA
      ws.carddemoCommarea.cdemoCpvdInfo.cdemoCpvdFraudData.spaces();
      // COB(179): IF NOT CDEMO-PGM-REENTER
      if (!ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
        // COB(180): SET CDEMO-PGM-REENTER    TO TRUE
        ws.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
        // COB(181): PERFORM PROCESS-ENTER-KEY
        processEnterKey();
        // COB(183): PERFORM SEND-AUTHVIEW-SCREEN
        sendAuthviewScreen();
        // COB(184): ELSE
      } else {
        // COB(185): PERFORM RECEIVE-AUTHVIEW-SCREEN
        receiveAuthviewScreen();
        // COB(186): EVALUATE EIBAID
        switch (dfheiblk.getEibaid()) {
            // COB(187): WHEN DFHENTER
          case DFHENTER:
            // COB(188): PERFORM PROCESS-ENTER-KEY
            processEnterKey();
            // COB(189): PERFORM SEND-AUTHVIEW-SCREEN
            sendAuthviewScreen();
            // COB(190): WHEN DFHPF3
            break;
          case DFHPF3:
            // COB(191): MOVE WS-PGM-AUTH-SMRY     TO CDEMO-TO-PROGRAM
            ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
                ws.wsVariables.wsPgmAuthSmry);
            // COB(192): PERFORM RETURN-TO-PREV-SCREEN
            returnToPrevScreen();
            // COB(193): WHEN DFHPF5
            break;
          case DFHPF5:
            // COB(194): PERFORM MARK-AUTH-FRAUD
            markAuthFraud();
            // COB(195): PERFORM SEND-AUTHVIEW-SCREEN
            sendAuthviewScreen();
            // COB(196): WHEN DFHPF8
            break;
          case DFHPF8:
            // COB(197): PERFORM PROCESS-PF8-KEY
            processPf8Key();
            // COB(198): PERFORM SEND-AUTHVIEW-SCREEN
            sendAuthviewScreen();
            // COB(199): WHEN OTHER
            break;
          default:
            // COB(200): PERFORM PROCESS-ENTER-KEY
            processEnterKey();
            // COB(202): MOVE CCDA-MSG-INVALID-KEY TO WS-MESSAGE
            ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgInvalidKey);
            // COB(203): PERFORM SEND-AUTHVIEW-SCREEN
            sendAuthviewScreen();
            // COB(204): END-EVALUATE
        }
        // COB(205): END-IF
      }
      // COB(206): END-IF
    }
    // COB(208): EXEC CICS RETURN
    // COB(208):           TRANSID (WS-CICS-TRANID)
    // COB(208):           COMMAREA (CARDDEMO-COMMAREA)
    // COB(208): END-EXEC
    try {
      supernaut
          .returnTransid(ws.wsVariables.wsCicsTranid) //
          .commarea(ws.carddemoCommarea) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***/
  protected void processEnterKey() {
    // COB(216): MOVE LOW-VALUES          TO COPAU1AO
    ws.copau01.copau1ao.lowValues();
    // COB(217): IF CDEMO-ACCT-ID IS NUMERIC AND
    // COB(217):    CDEMO-CPVD-PAU-SELECTED NOT = SPACES AND LOW-VALUES
    if (ws.carddemoCommarea.cdemoAccountInfo.cdemoAcctId.isNumeric()
        && !ws.carddemoCommarea.cdemoCpvdInfo.cdemoCpvdPauSelected.isSpaces()
        && !ws.carddemoCommarea.cdemoCpvdInfo.cdemoCpvdPauSelected.isLowValues()) {
      // COB(219): MOVE CDEMO-ACCT-ID            TO WS-ACCT-ID
      ws.wsVariables.wsAcctId.setValue(ws.carddemoCommarea.cdemoAccountInfo.cdemoAcctId);
      // COB(220): MOVE CDEMO-CPVD-PAU-SELECTED
      // COB(220):                               TO WS-AUTH-KEY
      ws.wsVariables.wsAuthKey.setValue(ws.carddemoCommarea.cdemoCpvdInfo.cdemoCpvdPauSelected);
      // COB(222): PERFORM READ-AUTH-RECORD
      readAuthRecord();
      // COB(224): IF IMS-PSB-SCHD
      if (ws.wsImsVariables.imsPsbSchd()) {
        // COB(225): SET IMS-PSB-NOT-SCHD      TO TRUE
        ws.wsImsVariables.setImsPsbNotSchd(true);
        // COB(226): PERFORM TAKE-SYNCPOINT
        takeSyncpoint();
        // COB(227): END-IF
      }
      // COB(229): ELSE
    } else {
      // COB(230): SET ERR-FLG-ON                TO TRUE
      ws.wsVariables.setErrFlgOn(true);
      // COB(231): END-IF
    }
    // COB(233): PERFORM POPULATE-AUTH-DETAILS
    populateAuthDetails();
  }

  /***/
  protected void markAuthFraud() {
    // COB(237): MOVE CDEMO-ACCT-ID            TO WS-ACCT-ID
    ws.wsVariables.wsAcctId.setValue(ws.carddemoCommarea.cdemoAccountInfo.cdemoAcctId);
    // COB(238): MOVE CDEMO-CPVD-PAU-SELECTED  TO WS-AUTH-KEY
    ws.wsVariables.wsAuthKey.setValue(ws.carddemoCommarea.cdemoCpvdInfo.cdemoCpvdPauSelected);
    // COB(240): PERFORM READ-AUTH-RECORD
    readAuthRecord();
    // COB(242): IF PA-FRAUD-CONFIRMED
    if (ws.pendingAuthDetails.paFraudConfirmed()) {
      // COB(243): SET PA-FRAUD-REMOVED          TO TRUE
      ws.pendingAuthDetails.setPaFraudRemoved(true);
      // COB(244): SET WS-REMOVE-FRAUD           TO TRUE
      ws.wsFraudData.wsFraudStatusRecord.setWsRemoveFraud(true);
      // COB(245): ELSE
    } else {
      // COB(246): SET PA-FRAUD-CONFIRMED        TO TRUE
      ws.pendingAuthDetails.setPaFraudConfirmed(true);
      // COB(247): SET WS-REPORT-FRAUD           TO TRUE
      ws.wsFraudData.wsFraudStatusRecord.setWsReportFraud(true);
      // COB(248): END-IF
    }
    // COB(250): MOVE PENDING-AUTH-DETAILS        TO WS-FRAUD-AUTH-RECORD
    ws.wsFraudData.wsFraudAuthRecord.setValue(ws.pendingAuthDetails);
    // COB(251): MOVE CDEMO-ACCT-ID               TO WS-FRD-ACCT-ID
    ws.wsFraudData.wsFrdAcctId.setValue(ws.carddemoCommarea.cdemoAccountInfo.cdemoAcctId);
    // COB(252): MOVE CDEMO-CUST-ID               TO WS-FRD-CUST-ID
    ws.wsFraudData.wsFrdCustId.setValue(ws.carddemoCommarea.cdemoCustomerInfo.cdemoCustId);
    // COB(260): ML         MOVE ZERO TO WS-RESP-CD
    //       * mLogica: DB2 access not implemented
    //       *    EXEC CICS LINK
    //       *         PROGRAM(WS-PGM-AUTH-FRAUD)
    //       *         COMMAREA(WS-FRAUD-DATA)
    //       *         NOHANDLE
    //       *    END-EXEC
    //       *    MOVE EIBRESP TO WS-RESP-CD
    ws.wsVariables.wsRespCd.setValue(0);
    // COB(261): ML         SET WS-FRD-UPDT-SUCCESS TO TRUE
    ws.wsFraudData.wsFraudStatusRecord.setWsFrdUpdtSuccess(true);
    // COB(262): IF WS-RESP-CD = DFHRESP(NORMAL)
    if (ws.wsVariables.wsRespCd.equals(Dfhresp.NORMAL)) {
      // COB(263): IF WS-FRD-UPDT-SUCCESS
      if (ws.wsFraudData.wsFraudStatusRecord.wsFrdUpdtSuccess()) {
        // COB(264): PERFORM UPDATE-AUTH-DETAILS
        updateAuthDetails();
        // COB(265): ELSE
      } else {
        // COB(266): MOVE WS-FRD-ACT-MSG     TO WS-MESSAGE
        ws.wsVariables.wsMessage.setValue(ws.wsFraudData.wsFraudStatusRecord.wsFrdActMsg);
        // COB(267): PERFORM ROLL-BACK
        rollBack();
        // COB(268): END-IF
      }
      // COB(269): ELSE
    } else {
      // COB(270): PERFORM ROLL-BACK
      rollBack();
      // COB(271): END-IF
    }
    // COB(273): MOVE PA-AUTHORIZATION-KEY     TO CDEMO-CPVD-PAU-SELECTED
    ws.carddemoCommarea.cdemoCpvdInfo.cdemoCpvdPauSelected.setValue(
        ws.pendingAuthDetails.paAuthorizationKey);
    // COB(274): PERFORM POPULATE-AUTH-DETAILS
    populateAuthDetails();
  }

  /***/
  protected void processPf8Key() {
    // COB(279): MOVE CDEMO-ACCT-ID            TO WS-ACCT-ID
    ws.wsVariables.wsAcctId.setValue(ws.carddemoCommarea.cdemoAccountInfo.cdemoAcctId);
    // COB(280): MOVE CDEMO-CPVD-PAU-SELECTED  TO WS-AUTH-KEY
    ws.wsVariables.wsAuthKey.setValue(ws.carddemoCommarea.cdemoCpvdInfo.cdemoCpvdPauSelected);
    // COB(282): PERFORM READ-AUTH-RECORD
    readAuthRecord();
    // COB(283): PERFORM READ-NEXT-AUTH-RECORD
    readNextAuthRecord();
    // COB(285): IF IMS-PSB-SCHD
    if (ws.wsImsVariables.imsPsbSchd()) {
      // COB(286): SET IMS-PSB-NOT-SCHD      TO TRUE
      ws.wsImsVariables.setImsPsbNotSchd(true);
      // COB(287): PERFORM TAKE-SYNCPOINT
      takeSyncpoint();
      // COB(288): END-IF
    }
    // COB(290): IF AUTHS-EOF
    if (ws.wsVariables.authsEof()) {
      // COB(291): SET SEND-ERASE-NO          TO TRUE
      ws.wsVariables.setSendEraseNo(true);
      // COB(292): MOVE 'Already at the last Authorization...'
      // COB(292):                            TO WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Already at the last Authorization...");
      // COB(294): ELSE
    } else {
      // COB(295): MOVE PA-AUTHORIZATION-KEY  TO CDEMO-CPVD-PAU-SELECTED
      ws.carddemoCommarea.cdemoCpvdInfo.cdemoCpvdPauSelected.setValue(
          ws.pendingAuthDetails.paAuthorizationKey);
      // COB(296): PERFORM POPULATE-AUTH-DETAILS
      populateAuthDetails();
      // COB(297): END-IF
    }
  }

  /***/
  protected void populateAuthDetails() {
    // COB(303): IF ERR-FLG-OFF
    if (ws.wsVariables.errFlgOff()) {
      // COB(304): MOVE PA-CARD-NUM               TO CARDNUMO
      ws.copau01.copau1ao.cardnumo.setValue(ws.pendingAuthDetails.paCardNum);
      // COB(306): MOVE PA-AUTH-ORIG-DATE(1:2)    TO WS-CURDATE-YY
      ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
          ws.pendingAuthDetails.paAuthOrigDate, 2);
      // COB(307): MOVE PA-AUTH-ORIG-DATE(3:2)    TO WS-CURDATE-MM
      ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
          ws.pendingAuthDetails.paAuthOrigDate, 2, 0, 2);
      // COB(308): MOVE PA-AUTH-ORIG-DATE(5:2)    TO WS-CURDATE-DD
      ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
          ws.pendingAuthDetails.paAuthOrigDate, 4, 0, 2);
      // COB(309): MOVE WS-CURDATE-MM-DD-YY       TO WS-AUTH-DATE
      ws.wsVariables.wsAuthDate.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
      // COB(310): MOVE WS-AUTH-DATE              TO AUTHDTO
      ws.copau01.copau1ao.authdto.setValue(ws.wsVariables.wsAuthDate);
      // COB(312): MOVE PA-AUTH-ORIG-TIME(1:2)    TO WS-AUTH-TIME(1:2)
      ws.wsVariables.wsAuthTime.setValue(ws.pendingAuthDetails.paAuthOrigTime, 2);
      // COB(313): MOVE PA-AUTH-ORIG-TIME(3:2)    TO WS-AUTH-TIME(4:2)
      ws.wsVariables.wsAuthTime.setValue(ws.pendingAuthDetails.paAuthOrigTime, 2, 3, 2);
      // COB(314): MOVE PA-AUTH-ORIG-TIME(5:2)    TO WS-AUTH-TIME(7:2)
      ws.wsVariables.wsAuthTime.setValue(ws.pendingAuthDetails.paAuthOrigTime, 4, 6, 2);
      // COB(315): MOVE WS-AUTH-TIME              TO AUTHTMO
      ws.copau01.copau1ao.authtmo.setValue(ws.wsVariables.wsAuthTime);
      // COB(317): MOVE PA-APPROVED-AMT           TO WS-AUTH-AMT
      ws.wsVariables.wsAuthAmt.setValue(ws.pendingAuthDetails.paApprovedAmt);
      // COB(318): MOVE WS-AUTH-AMT               TO AUTHAMTO
      ws.copau01.copau1ao.authamto.setValue(ws.wsVariables.wsAuthAmt);
      // COB(320): IF PA-AUTH-RESP-CODE = '00'
      if (ws.pendingAuthDetails.paAuthRespCode.equals("00")) {
        // COB(321): MOVE 'A'                    TO AUTHRSPO
        ws.copau01.copau1ao.authrspo.setString("A");
        // COB(322): MOVE DFHGREEN               TO AUTHRSPC
        ws.copau01.copau1ao.authrspc.setValue(Dfhbmsca.DFHGREEN.getValue());
        // COB(323): ELSE
      } else {
        // COB(324): MOVE 'D'                    TO AUTHRSPO
        ws.copau01.copau1ao.authrspo.setString("D");
        // COB(325): MOVE DFHRED                 TO AUTHRSPC
        ws.copau01.copau1ao.authrspc.setValue(Dfhbmsca.DFHRED.getValue());
        // COB(326): END-IF
      }
      // COB(328): SEARCH ALL WS-DECLINE-REASON-TAB
      // COB(328):     AT END
      // COB(328):          MOVE '9999'                     TO AUTHRSNO
      // COB(328):          MOVE '-'                        TO AUTHRSNO(5:1)
      // COB(328):          MOVE 'ERROR'                    TO AUTHRSNO(6:)
      // COB(328):     WHEN DECL-CODE(WS-DECL-RSN-IDX) = PA-AUTH-RESP-REASON
      // FIXME-[NOT_CONVERTED] SEARCH :328 >COB: [               SEARCH ALL WS-DECLINE-REASON-TAB]
      // COB(330): MOVE '9999'                     TO AUTHRSNO
      ws.copau01.copau1ao.authrsno.setString("9999");
      // COB(331): MOVE '-'                        TO AUTHRSNO(5:1)
      ws.copau01.copau1ao.authrsno.setString(4, 1, "-");
      // COB(332): MOVE 'ERROR'                    TO AUTHRSNO(6:)
      ws.copau01.copau1ao.authrsno.setString(5, ws.copau01.copau1ao.authrsno.length() - 5, "ERROR");
      // COB(334): MOVE PA-AUTH-RESP-REASON        TO AUTHRSNO
      ws.copau01.copau1ao.authrsno.setValue(ws.pendingAuthDetails.paAuthRespReason);
      // COB(335): MOVE '-'                        TO AUTHRSNO(5:1)
      ws.copau01.copau1ao.authrsno.setString(4, 1, "-");
      // COB(336): MOVE DECL-DESC(WS-DECL-RSN-IDX) TO AUTHRSNO(6:)
      ws.copau01.copau1ao.authrsno.setValue(
          ws.wsTables.filler73.wsDeclineReasonTabAtIndex(ws.wsDeclRsnIdx.getInt() - 1).declDesc,
          0,
          5,
          ws.copau01.copau1ao.authrsno.length() - 5);
      // COB(337): END-SEARCH
      // COB(340): MOVE PA-PROCESSING-CODE        TO AUTHCDO
      //       *
      //       *
      ws.copau01.copau1ao.authcdo.setValue(ws.pendingAuthDetails.paProcessingCode);
      // COB(341): MOVE PA-POS-ENTRY-MODE         TO POSEMDO
      ws.copau01.copau1ao.posemdo.setValue(ws.pendingAuthDetails.paPosEntryMode);
      // COB(342): MOVE PA-MESSAGE-SOURCE         TO AUTHSRCO
      ws.copau01.copau1ao.authsrco.setValue(ws.pendingAuthDetails.paMessageSource);
      // COB(343): MOVE PA-MERCHANT-CATAGORY-CODE TO MCCCDO
      ws.copau01.copau1ao.mcccdo.setValue(ws.pendingAuthDetails.paMerchantCatagoryCode);
      // COB(345): MOVE PA-CARD-EXPIRY-DATE(1:2)  TO CRDEXPO(1:2)
      ws.copau01.copau1ao.crdexpo.setValue(ws.pendingAuthDetails.paCardExpiryDate, 2);
      // COB(346): MOVE '/'                       TO CRDEXPO(3:1)
      ws.copau01.copau1ao.crdexpo.setString(2, 1, "/");
      // COB(347): MOVE PA-CARD-EXPIRY-DATE(3:2)  TO CRDEXPO(4:2)
      ws.copau01.copau1ao.crdexpo.setValue(ws.pendingAuthDetails.paCardExpiryDate, 2, 3, 2);
      // COB(349): MOVE PA-AUTH-TYPE              TO AUTHTYPO
      ws.copau01.copau1ao.authtypo.setValue(ws.pendingAuthDetails.paAuthType);
      // COB(350): MOVE PA-TRANSACTION-ID         TO TRNIDO
      ws.copau01.copau1ao.trnido.setValue(ws.pendingAuthDetails.paTransactionId);
      // COB(351): MOVE PA-MATCH-STATUS           TO AUTHMTCO
      ws.copau01.copau1ao.authmtco.setValue(ws.pendingAuthDetails.paMatchStatus);
      // COB(353): IF PA-FRAUD-CONFIRMED OR PA-FRAUD-REMOVED
      if (ws.pendingAuthDetails.paFraudConfirmed() || ws.pendingAuthDetails.paFraudRemoved()) {
        // COB(354): MOVE PA-AUTH-FRAUD          TO AUTHFRDO(1:1)
        ws.copau01.copau1ao.authfrdo.setValue(ws.pendingAuthDetails.paAuthFraud, 1);
        // COB(355): MOVE '-'                    TO AUTHFRDO(2:1)
        ws.copau01.copau1ao.authfrdo.setString(1, 1, "-");
        // COB(356): MOVE PA-FRAUD-RPT-DATE      TO AUTHFRDO(3:)
        ws.copau01.copau1ao.authfrdo.setValue(
            ws.pendingAuthDetails.paFraudRptDate, 0, 2, ws.copau01.copau1ao.authfrdo.length() - 2);
        // COB(357): ELSE
      } else {
        // COB(358): MOVE '-'                    TO AUTHFRDO
        ws.copau01.copau1ao.authfrdo.setString("-");
        // COB(359): END-IF
      }
      // COB(361): MOVE PA-MERCHANT-NAME          TO MERNAMEO
      ws.copau01.copau1ao.mernameo.setValue(ws.pendingAuthDetails.paMerchantName);
      // COB(362): MOVE PA-MERCHANT-ID            TO MERIDO
      ws.copau01.copau1ao.merido.setValue(ws.pendingAuthDetails.paMerchantId);
      // COB(363): MOVE PA-MERCHANT-CITY          TO MERCITYO
      ws.copau01.copau1ao.mercityo.setValue(ws.pendingAuthDetails.paMerchantCity);
      // COB(364): MOVE PA-MERCHANT-STATE         TO MERSTO
      ws.copau01.copau1ao.mersto.setValue(ws.pendingAuthDetails.paMerchantState);
      // COB(365): MOVE PA-MERCHANT-ZIP           TO MERZIPO
      ws.copau01.copau1ao.merzipo.setValue(ws.pendingAuthDetails.paMerchantZip);
      // COB(366): END-IF
    }
  }

  /***/
  protected void returnToPrevScreen() {
    // COB(371): MOVE WS-CICS-TRANID TO CDEMO-FROM-TRANID
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsVariables.wsCicsTranid);
    // COB(372): MOVE WS-PGM-AUTH-DTL TO CDEMO-FROM-PROGRAM
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsVariables.wsPgmAuthDtl);
    // COB(373): MOVE ZEROS          TO CDEMO-PGM-CONTEXT
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.setValue(0);
    // COB(374): SET CDEMO-PGM-ENTER TO TRUE
    ws.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmEnter(true);
    // COB(376): EXEC CICS
    // COB(376):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
    // COB(376):     COMMAREA(CARDDEMO-COMMAREA)
    // COB(376): END-EXEC.
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
   */
  protected void sendAuthviewScreen() {
    // COB(384): PERFORM POPULATE-HEADER-INFO
    populateHeaderInfo();
    // COB(386): MOVE WS-MESSAGE TO ERRMSGO OF COPAU1AO
    ws.copau01.copau1ao.errmsgo.setValue(ws.wsVariables.wsMessage);
    // COB(387): MOVE -1       TO CARDNUML
    ws.copau01.copau1ai.cardnuml.setValue(-1);
    // COB(389): IF SEND-ERASE-YES
    if (ws.wsVariables.sendEraseYes()) {
      // COB(390): EXEC CICS SEND
      // COB(390):        MAP('COPAU1A')
      // COB(390):        MAPSET('COPAU01')
      // COB(390):        FROM(COPAU1AO)
      // COB(390):        ERASE
      // COB(390):        CURSOR
      // COB(390): END-EXEC
      try {
        supernaut
            .sendMap("COPAU1A") //
            .mapset("COPAU01") //
            .from(ws.copau01.copau1ao) //
            .erase() //
            .cursor() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(397): ELSE
    } else {
      // COB(398): EXEC CICS SEND
      // COB(398):        MAP('COPAU1A')
      // COB(398):        MAPSET('COPAU01')
      // COB(398):        FROM(COPAU1AO)
      // COB(398):        CURSOR
      // COB(398): END-EXEC
      try {
        supernaut
            .sendMap("COPAU1A") //
            .mapset("COPAU01") //
            .from(ws.copau01.copau1ao) //
            .cursor() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(404): END-IF
    }
  }

  /***/
  protected void receiveAuthviewScreen() {
    // COB(409): EXEC CICS RECEIVE
    // COB(409):           MAP('COPAU1A')
    // COB(409):           MAPSET('COPAU01')
    // COB(409):           INTO(COPAU1AI)
    // COB(409):           NOHANDLE
    // COB(409): END-EXEC
    supernaut
        .receiveMap("COPAU1A") //
        .mapset("COPAU01") //
        .nohandle() //
        .into(ws.copau01.copau1ai) //
        .exec();
  }

  /***
   */
  protected void populateHeaderInfo() {
    // COB(420): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(422): MOVE CCDA-TITLE01           TO TITLE01O OF COPAU1AO
    ws.copau01.copau1ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(423): MOVE CCDA-TITLE02           TO TITLE02O OF COPAU1AO
    ws.copau01.copau1ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(424): MOVE WS-CICS-TRANID         TO TRNNAMEO OF COPAU1AO
    ws.copau01.copau1ao.trnnameo.setValue(ws.wsVariables.wsCicsTranid);
    // COB(425): MOVE WS-PGM-AUTH-DTL        TO PGMNAMEO OF COPAU1AO
    ws.copau01.copau1ao.pgmnameo.setValue(ws.wsVariables.wsPgmAuthDtl);
    // COB(427): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(428): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(429): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear.getString(2, 2));
    // COB(431): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF COPAU1AO
    ws.copau01.copau1ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(433): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(434): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(435): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(437): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF COPAU1AO
    ws.copau01.copau1ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***/
  protected void readAuthRecord() {
    // COB(442): PERFORM SCHEDULE-PSB
    schedulePsb();
    // COB(445): MOVE WS-ACCT-ID                TO PA-ACCT-ID
    //       *
    //       *
    ws.pendingAuthSummary.paAcctId.setValue(ws.wsVariables.wsAcctId);
    // COB(446): MOVE WS-AUTH-KEY               TO PA-AUTHORIZATION-KEY
    ws.pendingAuthDetails.paAuthorizationKey.setValue(ws.wsVariables.wsAuthKey);
    // COB(453): IRISDB     MOVE 1 TO IRIS-CALL-ID
    //       *
    //       *    EXEC DLI GU USING PCB(PAUT-PCB-NUM)
    //       *        SEGMENT (PAUTSUM0)
    //       *        INTO (PENDING-AUTH-SUMMARY)
    //       *        WHERE (ACCNTID = PA-ACCT-ID)
    //       *    END-EXEC
    ws.irisWorkArea.irisCallId.setValue(1);
    // COB(454): IRISDB     MOVE SPACE TO IRIS-KEYVALUE-TAB
    ws.irisWorkArea.irisKeyvalueTab.spaces();
    // COB(455): IRISDB     MOVE 'DBPAUTP0' TO IRIS-WS-RTN
    ws.iriscomm.irisWsRtn.setString("DBPAUTP0");
    // COB(456): IRISDB     SET IRIS-FUNC-GU TO TRUE
    ws.irisWorkArea.setIrisFuncGu(true);
    // COB(457): IRISDB     MOVE 8 TO IRIS-CUSTOM-FUNC-ID
    ws.irisWorkArea.irisCustomFuncId.setValue(8);
    // COB(458): IRISDB     MOVE PAUT-PCB-NUM TO IRIS-PCB-NUM
    ws.irisWorkArea.irisPcbNum.setValue(ws.wsImsVariables.pcbOffset.pautPcbNum);
    // COB(459): IRISDB     IF PA-ACCT-ID NOT NUMERIC
    if (!ws.pendingAuthSummary.paAcctId.isNumeric()) {
      // COB(460): IRISDB       MOVE ZERO TO IRIS-KEYVALUE-PACKED(1, 1)
      ws.irisWorkArea
          .irisKeyvalueTab
          .irisKeysLevelsAtIndex(0)
          .irisKeyvaluesRed2
          .irisKeyvaluesPAtIndex(0)
          .irisKeyvaluePacked
          .setValue(0);
      // COB(461): IRISDB     ELSE
    } else {
      // COB(462): IRISDB       MOVE PA-ACCT-ID TO IRIS-KEYVALUE-PACKED(1, 1)
      ws.irisWorkArea
          .irisKeyvalueTab
          .irisKeysLevelsAtIndex(0)
          .irisKeyvaluesRed2
          .irisKeyvaluesPAtIndex(0)
          .irisKeyvaluePacked
          .setValue(ws.pendingAuthSummary.paAcctId);
      // COB(463): IRISDB     END-IF
    }
    // COB(464): IRISDB     MOVE 'PAUTSUM0' TO IRIS-SEGMENT
    ws.irisWorkArea.irisSegment.setString("PAUTSUM0");
    // COB(465): IRISDB     SET IRIS-EXECDLI TO TRUE
    ws.irisWorkArea.setIrisExecdli(true);
    // COB(466): IRISDB     SET SQL-USER-CUSTOM TO TRUE
    ws.irisWorkArea.setSqlUserCustom(true);
    // COB(467): IRISDB     MOVE 3 TO IRIS-PARAM-NUM
    ws.irisWorkArea.irisParamNum.setValue(3);
    // COB(468): IRISDB     CALL IRIS-WS-RTN USING IRIS-WORK-AREA
    // COB(468): IRISDB                            DLZDIB
    // COB(468): IRISDB                            PENDING-AUTH-SUMMARY
    supernaut.call(
        ws.iriscomm.irisWsRtn, ws.irisWorkArea, ws.iriscomm.dlzdib, ws.pendingAuthSummary);
    // COB(471): MOVE DIBSTAT                          TO IMS-RETURN-CODE
    ws.wsImsVariables.imsReturnCode.setValue(ws.iriscomm.irisDibBlock.dibstat);
    // COB(472): EVALUATE TRUE
    // COB(473): WHEN STATUS-OK
    if (ws.wsImsVariables.statusOk()) {
      // COB(474): SET AUTHS-NOT-EOF              TO TRUE
      ws.wsVariables.setAuthsNotEof(true);
      // COB(475): WHEN SEGMENT-NOT-FOUND
    } else if (ws.wsImsVariables.segmentNotFound()
        // COB(476): WHEN END-OF-DB
        || ws.wsImsVariables.endOfDb()) {
      // COB(477): SET AUTHS-EOF                  TO TRUE
      ws.wsVariables.setAuthsEof(true);
      // COB(478): SET ERR-FLG-ON                 TO TRUE
      ws.wsVariables.setErrFlgOn(true);
      // COB(479): WHEN OTHER
    } else {
      // COB(480): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(482): STRING
      // COB(482): ' System error while reading Auth Summary: Code:'
      // COB(482): IMS-RETURN-CODE
      // COB(482): DELIMITED BY SIZE
      // COB(482): INTO WS-MESSAGE
      // COB(482): END-STRING
      ws.wsVariables.wsMessage.concat(
          " System error while reading Auth Summary: Code:", ws.wsImsVariables.imsReturnCode);
      // COB(487): END-STRING
      // COB(488): PERFORM SEND-AUTHVIEW-SCREEN
      sendAuthviewScreen();
      // COB(489): END-EVALUATE
    }
    // COB(491): IF AUTHS-NOT-EOF
    if (ws.wsVariables.authsNotEof()) {
      // COB(497): IRISDB        MOVE 2 TO IRIS-CALL-ID
      //       *       EXEC DLI GNP USING PCB(PAUT-PCB-NUM)
      //       *           SEGMENT (PAUTDTL1)
      //       *           INTO (PENDING-AUTH-DETAILS)
      //       *           WHERE (PAUT9CTS = PA-AUTHORIZATION-KEY)
      //       *       END-EXEC
      ws.irisWorkArea.irisCallId.setValue(2);
      // COB(498): IRISDB        MOVE SPACE TO IRIS-KEYVALUE-TAB
      ws.irisWorkArea.irisKeyvalueTab.spaces();
      // COB(499): IRISDB        MOVE 'DBPAUTP0' TO IRIS-WS-RTN
      ws.iriscomm.irisWsRtn.setString("DBPAUTP0");
      // COB(500): IRISDB        MOVE 'PAUTDTL1' TO IRIS-SEGMENT
      ws.irisWorkArea.irisSegment.setString("PAUTDTL1");
      // COB(501): IRISDB        SET IRIS-FUNC-GNP TO TRUE
      ws.irisWorkArea.setIrisFuncGnp(true);
      // COB(502): IRISDB        MOVE 12 TO IRIS-CUSTOM-FUNC-ID
      ws.irisWorkArea.irisCustomFuncId.setValue(12);
      // COB(503): IRISDB        MOVE PAUT-PCB-NUM TO IRIS-PCB-NUM
      ws.irisWorkArea.irisPcbNum.setValue(ws.wsImsVariables.pcbOffset.pautPcbNum);
      // COB(504): IRISDB        MOVE PA-AUTHORIZATION-KEY TO IRIS-KEYVALUE(1, 1)
      ws.irisWorkArea
          .irisKeyvalueTab
          .irisKeysLevelsAtIndex(0)
          .irisKeyvaluesRed1
          .irisKeyvaluesAtIndex(0)
          .irisKeyvalue
          .setValue(ws.pendingAuthDetails.paAuthorizationKey);
      // COB(505): IRISDB        SET IRIS-EXECDLI TO TRUE
      ws.irisWorkArea.setIrisExecdli(true);
      // COB(506): IRISDB        SET SQL-USER-CUSTOM TO TRUE
      ws.irisWorkArea.setSqlUserCustom(true);
      // COB(507): IRISDB        MOVE 3 TO IRIS-PARAM-NUM
      ws.irisWorkArea.irisParamNum.setValue(3);
      // COB(508): IRISDB        CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      // COB(508): IRISDB                               DLZDIB
      // COB(508): IRISDB                               PENDING-AUTH-DETAILS
      supernaut.call(
          ws.iriscomm.irisWsRtn, ws.irisWorkArea, ws.iriscomm.dlzdib, ws.pendingAuthDetails);
      // COB(511): MOVE DIBSTAT                          TO IMS-RETURN-CODE
      ws.wsImsVariables.imsReturnCode.setValue(ws.iriscomm.irisDibBlock.dibstat);
      // COB(512): EVALUATE TRUE
      // COB(513): WHEN STATUS-OK
      if (ws.wsImsVariables.statusOk()) {
        // COB(514): SET AUTHS-NOT-EOF              TO TRUE
        ws.wsVariables.setAuthsNotEof(true);
        // COB(515): WHEN SEGMENT-NOT-FOUND
      } else if (ws.wsImsVariables.segmentNotFound()
          // COB(516): WHEN END-OF-DB
          || ws.wsImsVariables.endOfDb()) {
        // COB(517): SET AUTHS-EOF                  TO TRUE
        ws.wsVariables.setAuthsEof(true);
        // COB(518): SET ERR-FLG-ON                 TO TRUE
        ws.wsVariables.setErrFlgOn(true);
        // COB(519): WHEN OTHER
      } else {
        // COB(520): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(522): STRING
        // COB(522): ' System error while reading Auth Details: Code:'
        // COB(522): IMS-RETURN-CODE
        // COB(522): DELIMITED BY SIZE
        // COB(522): INTO WS-MESSAGE
        // COB(522): END-STRING
        ws.wsVariables.wsMessage.concat(
            " System error while reading Auth Details: Code:", ws.wsImsVariables.imsReturnCode);
        // COB(527): END-STRING
        // COB(528): PERFORM SEND-AUTHVIEW-SCREEN
        sendAuthviewScreen();
        // COB(529): END-EVALUATE
      }
      // COB(530): END-IF
    }
  }

  /***/
  protected void readNextAuthRecord() {
    // COB(540): IRISDB     MOVE 3 TO IRIS-CALL-ID
    ws.irisWorkArea.irisCallId.setValue(3);
    // COB(541): IRISDB     MOVE SPACE TO IRIS-KEYVALUE-TAB
    ws.irisWorkArea.irisKeyvalueTab.spaces();
    // COB(542): IRISDB     MOVE 'DBPAUTP0' TO IRIS-WS-RTN
    ws.iriscomm.irisWsRtn.setString("DBPAUTP0");
    // COB(543): IRISDB     MOVE 'PAUTDTL1' TO IRIS-SEGMENT
    ws.irisWorkArea.irisSegment.setString("PAUTDTL1");
    // COB(544): IRISDB     SET IRIS-FUNC-GNP TO TRUE
    ws.irisWorkArea.setIrisFuncGnp(true);
    // COB(545): IRISDB     MOVE 2 TO IRIS-CUSTOM-FUNC-ID
    ws.irisWorkArea.irisCustomFuncId.setValue(2);
    // COB(546): IRISDB     MOVE PAUT-PCB-NUM TO IRIS-PCB-NUM
    ws.irisWorkArea.irisPcbNum.setValue(ws.wsImsVariables.pcbOffset.pautPcbNum);
    // COB(547): IRISDB     SET IRIS-EXECDLI TO TRUE
    ws.irisWorkArea.setIrisExecdli(true);
    // COB(548): IRISDB     SET SQL-USER-CUSTOM TO TRUE
    ws.irisWorkArea.setSqlUserCustom(true);
    // COB(549): IRISDB     MOVE 3 TO IRIS-PARAM-NUM
    ws.irisWorkArea.irisParamNum.setValue(3);
    // COB(550): IRISDB     CALL IRIS-WS-RTN USING IRIS-WORK-AREA
    // COB(550): IRISDB                            DLZDIB
    // COB(550): IRISDB                            PENDING-AUTH-DETAILS
    supernaut.call(
        ws.iriscomm.irisWsRtn, ws.irisWorkArea, ws.iriscomm.dlzdib, ws.pendingAuthDetails);
    // COB(553): MOVE DIBSTAT                          TO IMS-RETURN-CODE
    ws.wsImsVariables.imsReturnCode.setValue(ws.iriscomm.irisDibBlock.dibstat);
    // COB(554): EVALUATE TRUE
    // COB(555): WHEN STATUS-OK
    if (ws.wsImsVariables.statusOk()) {
      // COB(556): SET AUTHS-NOT-EOF              TO TRUE
      ws.wsVariables.setAuthsNotEof(true);
      // COB(557): WHEN SEGMENT-NOT-FOUND
    } else if (ws.wsImsVariables.segmentNotFound()
        // COB(558): WHEN END-OF-DB
        || ws.wsImsVariables.endOfDb()) {
      // COB(559): SET AUTHS-EOF                  TO TRUE
      ws.wsVariables.setAuthsEof(true);
      // COB(560): SET ERR-FLG-ON                 TO TRUE
      ws.wsVariables.setErrFlgOn(true);
      // COB(561): WHEN OTHER
    } else {
      // COB(562): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(564): STRING
      // COB(564): ' System error while reading next Auth: Code:'
      // COB(564): IMS-RETURN-CODE
      // COB(564): DELIMITED BY SIZE
      // COB(564): INTO WS-MESSAGE
      // COB(564): END-STRING
      ws.wsVariables.wsMessage.concat(
          " System error while reading next Auth: Code:", ws.wsImsVariables.imsReturnCode);
      // COB(569): END-STRING
      // COB(570): PERFORM SEND-AUTHVIEW-SCREEN
      sendAuthviewScreen();
      // COB(571): END-EVALUATE
    }
  }

  /***/
  protected void updateAuthDetails() {

    supernaut
            .read(ws.wsVariables.wsCustfilename.getValue()) //
            .update() //
            .length(ws.cvcus01y.customerRecord.length()) //
            .resp(ws.wsVariables.wsRespCd) //
            .resp2(ws.wsVariables.wsReasCd) //
            .into(ws.cvcus01y.customerRecord) //
            .ridfld(ws.carddemoCommarea.cdemoCustomerInfo.cdemoCustId) //
            .keylength(ws.carddemoCommarea.cdemoCustomerInfo.cdemoCustId.length()) //
            .exec();
    switch (ws.wsVariables.wsRespCd.getInt()) {
      case Dfhresp.NORMAL:
        break;
      case Dfhresp.NOTFND:
        ws.wsVariables.wsRespCdDis.setValue(ws.wsVariables.wsRespCd);
        ws.wsVariables.wsReasCdDis.setValue(ws.wsVariables.wsReasCd);
        ws.wsVariables.wsMessage.concat(
                "Customer:",
                ws.carddemoCommarea.cdemoCustomerInfo.cdemoCustId.toString(),
                " not found in CUST file. Resp:",
                ws.wsVariables.wsRespCdDis,
                " Reas:",
                ws.wsVariables.wsReasCdDis);
        break;
      default:
        rollBack();
        ws.wsVariables.wsRespCdDis.setValue(ws.wsVariables.wsRespCd);
        ws.wsVariables.wsReasCdDis.setValue(ws.wsVariables.wsReasCd);
        ws.wsVariables.wsErrFlg.setString("Y");
        ws.wsVariables.wsMessage.concat(
                "Customer:",
                ws.carddemoCommarea.cdemoCustomerInfo.cdemoCustId.toString(),
                " System error while reading CUST file. Resp:",
                ws.wsVariables.wsRespCdDis,
                " Reas:",
                ws.wsVariables.wsReasCdDis);
        sendAuthviewScreen();
    }

    // COB(576): MOVE WS-FRAUD-AUTH-RECORD           TO PENDING-AUTH-DETAILS
    ws.pendingAuthDetails.setValue(ws.wsFraudData.wsFraudAuthRecord);
    // COB(577): DISPLAY 'RPT DT: ' PA-FRAUD-RPT-DATE
    sysout.display("RPT DT: ", ws.pendingAuthDetails.paFraudRptDate);
    // COB(583): IRISDB     MOVE 4 TO IRIS-CALL-ID
    //       *
    //       *    EXEC DLI REPL USING PCB(PAUT-PCB-NUM)
    //       *         SEGMENT (PAUTDTL1)
    //       *         FROM (PENDING-AUTH-DETAILS)
    //       *    END-EXEC
    ws.irisWorkArea.irisCallId.setValue(4);
    // COB(584): IRISDB     MOVE SPACE TO IRIS-KEYVALUE-TAB
    ws.irisWorkArea.irisKeyvalueTab.spaces();
    // COB(585): IRISDB     MOVE 'DBPAUTP0' TO IRIS-WS-RTN
    ws.iriscomm.irisWsRtn.setString("DBPAUTP0");
    // COB(586): IRISDB     MOVE 'PAUTDTL1' TO IRIS-SEGMENT
    ws.irisWorkArea.irisSegment.setString("PAUTDTL1");
    // COB(587): IRISDB     SET IRIS-FUNC-REPL TO TRUE
    ws.irisWorkArea.setIrisFuncRepl(true);
    // COB(588): IRISDB     MOVE 13 TO IRIS-CUSTOM-FUNC-ID
    ws.irisWorkArea.irisCustomFuncId.setValue(13);
    // COB(589): IRISDB     MOVE PAUT-PCB-NUM TO IRIS-PCB-NUM
    ws.irisWorkArea.irisPcbNum.setValue(ws.wsImsVariables.pcbOffset.pautPcbNum);
    // COB(590): IRISDB     SET IRIS-EXECDLI TO TRUE
    ws.irisWorkArea.setIrisExecdli(true);
    // COB(591): IRISDB     SET SQL-USER-CUSTOM TO TRUE
    ws.irisWorkArea.setSqlUserCustom(true);
    // COB(592): IRISDB     MOVE 3 TO IRIS-PARAM-NUM
    ws.irisWorkArea.irisParamNum.setValue(3);
    // COB(593): IRISDB     CALL IRIS-WS-RTN USING IRIS-WORK-AREA
    // COB(593): IRISDB                            DLZDIB
    // COB(593): IRISDB                            PENDING-AUTH-DETAILS
    supernaut.call(
        ws.iriscomm.irisWsRtn, ws.irisWorkArea, ws.iriscomm.dlzdib, ws.pendingAuthDetails);
    // COB(596): MOVE DIBSTAT                        TO IMS-RETURN-CODE
    ws.wsImsVariables.imsReturnCode.setValue(ws.iriscomm.irisDibBlock.dibstat);
    // COB(597): EVALUATE TRUE
    // COB(598): WHEN STATUS-OK
    if (ws.wsImsVariables.statusOk()) {
      String fraudFlag;
      // COB(600): IF PA-FRAUD-REMOVED
      if (ws.pendingAuthDetails.paFraudRemoved()) {
        // COB(601): MOVE 'AUTH FRAUD REMOVED...'   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("AUTH FRAUD REMOVED...");
        fraudFlag = " ** FRAUD:R";
        // COB(602): ELSE
      } else {
        // COB(603): MOVE 'AUTH MARKED FRAUD...'    TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("AUTH MARKED FRAUD...");
        fraudFlag = " ** FRAUD:F";
        // COB(604): END-IF
      }
      String custName = ws.cvcus01y.customerRecord.custFirstName.getValue().trim();
      if (custName.contains(" ** ")){
        custName = custName.substring(0, custName.indexOf(" ** "));
      }
      custName += fraudFlag;
      ws.cvcus01y.customerRecord.custFirstName.setValue(custName);
      supernaut
              .rewrite(ws.wsVariables.wsCustfilename.getValue()) //
              .length(ws.cvcus01y.customerRecord.length()) //
              .resp(ws.wsVariables.wsRespCd) //
              .resp2(ws.wsVariables.wsReasCd) //
              .from(ws.cvcus01y.customerRecord) //
              .exec();
      switch (ws.wsVariables.wsRespCd.getInt()) {
        case Dfhresp.NORMAL:
          break;
        default:
          rollBack();
          ws.wsVariables.wsRespCdDis.setValue(ws.wsVariables.wsRespCd);
          ws.wsVariables.wsReasCdDis.setValue(ws.wsVariables.wsReasCd);
          ws.wsVariables.wsErrFlg.setString("Y");
          ws.wsVariables.wsMessage.concat(
                  "Customer:",
                  ws.carddemoCommarea.cdemoCustomerInfo.cdemoCustId.toString(),
                  " System error while reading CUST file. Resp:",
                  ws.wsVariables.wsRespCdDis,
                  " Reas:",
                  ws.wsVariables.wsReasCdDis);
          sendAuthviewScreen();
      }
      // COB(599): PERFORM TAKE-SYNCPOINT
      takeSyncpoint();
      // COB(605): WHEN OTHER
    } else {
      // COB(606): PERFORM ROLL-BACK
      rollBack();
      // COB(608): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(610): STRING
      // COB(610): ' System error while FRAUD Tagging, ROLLBACK||'
      // COB(610): IMS-RETURN-CODE
      // COB(610): DELIMITED BY SIZE
      // COB(610): INTO WS-MESSAGE
      // COB(610): END-STRING
      ws.wsVariables.wsMessage.concat(
          " System error while FRAUD Tagging, ROLLBACK||", ws.wsImsVariables.imsReturnCode);
      // COB(615): END-STRING
      // COB(616): PERFORM SEND-AUTHVIEW-SCREEN
      sendAuthviewScreen();
      // COB(617): END-EVALUATE
    }
  }

  /***
   *****************************************************************
   * TAKE SYNCPOINT                                                *
   *****************************************************************/
  protected void takeSyncpoint() {
    // COB(626): CONTINUE.
    //supernaut.commit(true, ws.wsVariables.wsRespCd, ws.wsVariables.wsReasCd);
  }

  /***
   *****************************************************************
   * ROLLBACK THE DB CHANGES                                       *
   *****************************************************************/
  protected void rollBack() {
    // COB(632): EXEC CICS
    // COB(632):    SYNCPOINT ROLLBACK
    // COB(632): END-EXEC
    try {
      supernaut
          .syncpoint() //
          .rollback() //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *****************************************************************
   * SCHEDULE PSB                                                  *
   *****************************************************************/
  protected void schedulePsb() {
    // COB(645): IRISDB     MOVE 5 TO IRIS-CALL-ID
    ws.irisWorkArea.irisCallId.setValue(5);
    // COB(646): IRISDB     MOVE SPACE TO IRIS-KEYVALUE-TAB
    ws.irisWorkArea.irisKeyvalueTab.spaces();
    // COB(647): IRISDB     SET IRISUTIL-RTN TO TRUE
    ws.iriscomm.setIrisutilRtn(true);
    // COB(648): IRISDB     SET IRIS-FUNC-SCHD TO TRUE
    ws.irisWorkArea.setIrisFuncSchd(true);
    // COB(649): IRISDB     MOVE 6 TO IRIS-CUSTOM-FUNC-ID
    ws.irisWorkArea.irisCustomFuncId.setValue(6);
    // COB(650): IRISDB     SET IRIS-EXECDLI TO TRUE
    ws.irisWorkArea.setIrisExecdli(true);
    // COB(651): IRISDB     MOVE 2 TO IRIS-PARAM-NUM
    ws.irisWorkArea.irisParamNum.setValue(2);
    // COB(652): IRISDB     CALL IRIS-WS-RTN USING IRIS-WORK-AREA
    // COB(652): IRISDB                            DLZDIB
    // COB(652): IRISDB                            PSB-NAME
    supernaut.call(
        ws.iriscomm.irisWsRtn, ws.irisWorkArea, ws.iriscomm.dlzdib, ws.wsImsVariables.psbName);
    // COB(655): MOVE DIBSTAT        TO IMS-RETURN-CODE
    ws.wsImsVariables.imsReturnCode.setValue(ws.iriscomm.irisDibBlock.dibstat);
    // COB(656): IF PSB-SCHEDULED-MORE-THAN-ONCE
    if (ws.wsImsVariables.psbScheduledMoreThanOnce()) {
      // COB(659): IRISDB        MOVE 6 TO IRIS-CALL-ID
      //       *       EXEC DLI TERM
      //       *       END-EXEC
      ws.irisWorkArea.irisCallId.setValue(6);
      // COB(660): IRISDB        SET IRISUTIL-RTN TO TRUE
      ws.iriscomm.setIrisutilRtn(true);
      // COB(661): IRISDB        SET IRIS-FUNC-TERM TO TRUE
      ws.irisWorkArea.setIrisFuncTerm(true);
      // COB(662): IRISDB        MOVE 7 TO IRIS-CUSTOM-FUNC-ID
      ws.irisWorkArea.irisCustomFuncId.setValue(7);
      // COB(663): IRISDB        SET IRIS-EXECDLI TO TRUE
      ws.irisWorkArea.setIrisExecdli(true);
      // COB(664): IRISDB        MOVE 2 TO IRIS-PARAM-NUM
      ws.irisWorkArea.irisParamNum.setValue(2);
      // COB(665): IRISDB        CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      // COB(665): IRISDB                               DLZDIB
      supernaut.call(ws.iriscomm.irisWsRtn, ws.irisWorkArea, ws.iriscomm.dlzdib);
      // COB(672): IRISDB        MOVE 7 TO IRIS-CALL-ID
      //       *
      //       *       EXEC DLI SCHD
      //       *            PSB((PSB-NAME))
      //       *            NODHABEND
      //       *       END-EXEC
      ws.irisWorkArea.irisCallId.setValue(7);
      // COB(673): IRISDB        MOVE SPACE TO IRIS-KEYVALUE-TAB
      ws.irisWorkArea.irisKeyvalueTab.spaces();
      // COB(674): IRISDB        SET IRISUTIL-RTN TO TRUE
      ws.iriscomm.setIrisutilRtn(true);
      // COB(675): IRISDB        SET IRIS-FUNC-SCHD TO TRUE
      ws.irisWorkArea.setIrisFuncSchd(true);
      // COB(676): IRISDB        MOVE 6 TO IRIS-CUSTOM-FUNC-ID
      ws.irisWorkArea.irisCustomFuncId.setValue(6);
      // COB(677): IRISDB        SET IRIS-EXECDLI TO TRUE
      ws.irisWorkArea.setIrisExecdli(true);
      // COB(678): IRISDB        MOVE 2 TO IRIS-PARAM-NUM
      ws.irisWorkArea.irisParamNum.setValue(2);
      // COB(679): IRISDB        CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      // COB(679): IRISDB                               DLZDIB
      // COB(679): IRISDB                               PSB-NAME
      supernaut.call(
          ws.iriscomm.irisWsRtn, ws.irisWorkArea, ws.iriscomm.dlzdib, ws.wsImsVariables.psbName);
      // COB(682): MOVE DIBSTAT     TO IMS-RETURN-CODE
      ws.wsImsVariables.imsReturnCode.setValue(ws.iriscomm.irisDibBlock.dibstat);
      // COB(683): END-IF
    }
    // COB(684): IF STATUS-OK
    if (ws.wsImsVariables.statusOk()) {
      // COB(685): SET IMS-PSB-SCHD           TO TRUE
      ws.wsImsVariables.setImsPsbSchd(true);
      // COB(686): ELSE
    } else {
      // COB(687): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(689): STRING
      // COB(689):     ' System error while scheduling PSB: Code:'
      // COB(689): IMS-RETURN-CODE
      // COB(689): DELIMITED BY SIZE
      // COB(689): INTO WS-MESSAGE
      // COB(689): END-STRING
      ws.wsVariables.wsMessage.concat(
          " System error while scheduling PSB: Code:", ws.wsImsVariables.imsReturnCode);
      // COB(694): END-STRING
      // COB(695): PERFORM SEND-AUTHVIEW-SCREEN
      sendAuthviewScreen();
      // COB(696): END-IF
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
