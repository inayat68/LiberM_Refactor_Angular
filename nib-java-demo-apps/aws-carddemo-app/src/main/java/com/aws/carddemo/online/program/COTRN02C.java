package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cotrn02c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COTRN02C extends AbstractOnlineProgram {

  @ProgramStorage final Cotrn02cWorking ws = new Cotrn02cWorking();

  // Linkage
  public static class Cotrn02cLinkage extends NGroup {
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

  final Cotrn02cLinkage params = new Cotrn02cLinkage();

  public COTRN02C(Context supernaut) {
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
    // COB(109): SET ERR-FLG-OFF     TO TRUE
    ws.wsVariables.setErrFlgOff(true);
    // COB(110): SET USR-MODIFIED-NO TO TRUE
    ws.wsVariables.setUsrModifiedNo(true);
    // COB(112): MOVE SPACES TO WS-MESSAGE
    // COB(112):                ERRMSGO OF COTRN2AO
    ws.wsVariables.wsMessage.spaces();
    ws.cotrn02.cotrn2ao.errmsgo.spaces();
    // COB(115): IF EIBCALEN = 0
    if (dfheiblk.getEibcalen() == 0) {
      // COB(116): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(117): PERFORM RETURN-TO-PREV-SCREEN
      returnToPrevScreen();
      // COB(118): ELSE
    } else {
      // COB(119): MOVE DFHCOMMAREA(1:EIBCALEN) TO CARDDEMO-COMMAREA
      ws.carddemoCommarea.setValue(params.dfhcommarea, 0, dfheiblk.getEibcalen());
      // COB(120): IF NOT CDEMO-PGM-REENTER
      if (!ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
        // COB(121): SET CDEMO-PGM-REENTER    TO TRUE
        ws.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
        // COB(122): MOVE LOW-VALUES          TO COTRN2AO
        ws.cotrn02.cotrn2ao.lowValues();
        // COB(123): MOVE -1       TO ACTIDINL OF COTRN2AI
        ws.cotrn02.cotrn2ai.actidinl.setValue(-1);
        // COB(124): IF CDEMO-CT02-TRN-SELECTED NOT =
        // COB(124):                            SPACES AND LOW-VALUES
        if (!ws.carddemoCommarea.cdemoCt02Info.cdemoCt02TrnSelected.isSpaces()
            && !ws.carddemoCommarea.cdemoCt02Info.cdemoCt02TrnSelected.isLowValues()) {
          // COB(126): MOVE CDEMO-CT02-TRN-SELECTED TO
          // COB(126):      CARDNINI OF COTRN2AI
          ws.cotrn02.cotrn2ai.cardnini.setValue(
              ws.carddemoCommarea.cdemoCt02Info.cdemoCt02TrnSelected);
          // COB(128): PERFORM PROCESS-ENTER-KEY
          processEnterKey();
          // COB(129): END-IF
        }
        // COB(130): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(131): ELSE
      } else {
        // COB(132): PERFORM RECEIVE-TRNADD-SCREEN
        receiveTrnaddScreen();
        // COB(133): EVALUATE EIBAID
        switch (dfheiblk.getEibaid()) {
            // COB(134): WHEN DFHENTER
          case DFHENTER:
            // COB(135): PERFORM PROCESS-ENTER-KEY
            processEnterKey();
            // COB(136): WHEN DFHPF3
            break;
          case DFHPF3:
            // COB(137): IF CDEMO-FROM-PROGRAM = SPACES OR LOW-VALUES
            if (ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isSpaces()
                || ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isLowValues()) {
              // COB(138): MOVE 'COMEN01C' TO CDEMO-TO-PROGRAM
              ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COMEN01C");
              // COB(139): ELSE
            } else {
              // COB(140): MOVE CDEMO-FROM-PROGRAM TO
              // COB(140): CDEMO-TO-PROGRAM
              ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
                  ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram);
              // COB(142): END-IF
            }
            // COB(143): PERFORM RETURN-TO-PREV-SCREEN
            returnToPrevScreen();
            // COB(144): WHEN DFHPF4
            break;
          case DFHPF4:
            // COB(145): PERFORM CLEAR-CURRENT-SCREEN
            clearCurrentScreen();
            // COB(146): WHEN DFHPF5
            break;
          case DFHPF5:
            // COB(147): PERFORM COPY-LAST-TRAN-DATA
            copyLastTranData();
            // COB(148): WHEN OTHER
            break;
          default:
            // COB(149): MOVE 'Y'                       TO WS-ERR-FLG
            ws.wsVariables.wsErrFlg.setString("Y");
            // COB(150): MOVE CCDA-MSG-INVALID-KEY      TO WS-MESSAGE
            ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgInvalidKey);
            // COB(151): PERFORM SEND-TRNADD-SCREEN
            sendTrnaddScreen();
            // COB(152): END-EVALUATE
        }
        // COB(153): END-IF
      }
      // COB(154): END-IF
    }
    // COB(156): EXEC CICS RETURN
    // COB(156):           TRANSID (WS-TRANID)
    // COB(156):           COMMAREA (CARDDEMO-COMMAREA)
    // COB(156): END-EXEC.
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
    // COB(166): PERFORM VALIDATE-INPUT-KEY-FIELDS
    validateInputKeyFields();
    // COB(167): PERFORM VALIDATE-INPUT-DATA-FIELDS.
    validateInputDataFields();
    // COB(169): EVALUATE CONFIRMI OF COTRN2AI
    // COB(170): WHEN 'Y'
    if ((ws.cotrn02.cotrn2ai.confirmi.equals("Y"))
        // COB(171): WHEN 'y'
        || ws.cotrn02.cotrn2ai.confirmi.equals("y")) {
      // COB(172): PERFORM ADD-TRANSACTION
      addTransaction();
      // COB(173): WHEN 'N'
    } else if (ws.cotrn02.cotrn2ai.confirmi.equals("N")
        // COB(174): WHEN 'n'
        || ws.cotrn02.cotrn2ai.confirmi.equals("n")
        // COB(175): WHEN SPACES
        || ws.cotrn02.cotrn2ai.confirmi.isSpaces()
        // COB(176): WHEN LOW-VALUES
        || ws.cotrn02.cotrn2ai.confirmi.isLowValues()) {
      // COB(177): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(178): MOVE 'Confirm to add this transaction...'
      // COB(178):              TO WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Confirm to add this transaction...");
      // COB(180): MOVE -1      TO CONFIRML OF COTRN2AI
      ws.cotrn02.cotrn2ai.confirml.setValue(-1);
      // COB(181): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(182): WHEN OTHER
    } else {
      // COB(183): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(184): MOVE 'Invalid value. Valid values are (Y/N)...'
      // COB(184):              TO WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Invalid value. Valid values are (Y/N)...");
      // COB(186): MOVE -1      TO CONFIRML OF COTRN2AI
      ws.cotrn02.cotrn2ai.confirml.setValue(-1);
      // COB(187): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(188): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      VALIDATE-INPUT-KEY-FIELDS
   *----------------------------------------------------------------*/
  protected void validateInputKeyFields() {
    // COB(195): EVALUATE TRUE
    // COB(196): WHEN ACTIDINI OF COTRN2AI NOT = SPACES AND LOW-VALUES
    if (!ws.cotrn02.cotrn2ai.actidini.isSpaces() && !ws.cotrn02.cotrn2ai.actidini.isLowValues()) {
      // COB(197): IF ACTIDINI OF COTRN2AI IS NOT NUMERIC
      if (!ws.cotrn02.cotrn2ai.actidini.isNumeric()) {
        // COB(198): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(199): MOVE 'Account ID must be Numeric...' TO
        // COB(199):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Account ID must be Numeric...");
        // COB(201): MOVE -1       TO ACTIDINL OF COTRN2AI
        ws.cotrn02.cotrn2ai.actidinl.setValue(-1);
        // COB(202): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(203): END-IF
      }
      // COB(204): COMPUTE WS-ACCT-ID-N = FUNCTION NUMVAL(ACTIDINI OF
      // COB(204): COTRN2AI)
      ws.wsVariables.wsAcctIdN.setValue(ws.cotrn02.cotrn2ai.actidini.convertToNumeric());
      // COB(206): MOVE WS-ACCT-ID-N            TO XREF-ACCT-ID
      // COB(206):                              ACTIDINI OF COTRN2AI
      ws.cvact03y.cardXrefRecord.xrefAcctId.setValue(ws.wsVariables.wsAcctIdN);
      ws.cotrn02.cotrn2ai.actidini.setValue(ws.wsVariables.wsAcctIdN);
      // COB(208): PERFORM READ-CXACAIX-FILE
      readCxacaixFile();
      // COB(209): MOVE XREF-CARD-NUM         TO CARDNINI OF COTRN2AI
      ws.cotrn02.cotrn2ai.cardnini.setValue(ws.cvact03y.cardXrefRecord.xrefCardNum);
      // COB(210): WHEN CARDNINI OF COTRN2AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.cotrn02.cotrn2ai.cardnini.isSpaces()
        && !ws.cotrn02.cotrn2ai.cardnini.isLowValues()) {
      // COB(211): IF CARDNINI OF COTRN2AI IS NOT NUMERIC
      if (!ws.cotrn02.cotrn2ai.cardnini.isNumeric()) {
        // COB(212): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(213): MOVE 'Card Number must be Numeric...' TO
        // COB(213):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Card Number must be Numeric...");
        // COB(215): MOVE -1       TO CARDNINL OF COTRN2AI
        ws.cotrn02.cotrn2ai.cardninl.setValue(-1);
        // COB(216): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(217): END-IF
      }
      // COB(218): COMPUTE WS-CARD-NUM-N = FUNCTION NUMVAL(CARDNINI OF
      // COB(218): COTRN2AI)
      ws.wsVariables.wsCardNumN.setValue(ws.cotrn02.cotrn2ai.cardnini.convertToNumeric());
      // COB(220): MOVE WS-CARD-NUM-N        TO XREF-CARD-NUM
      // COB(220):                              CARDNINI OF COTRN2AI
      ws.cvact03y.cardXrefRecord.xrefCardNum.setValue(ws.wsVariables.wsCardNumN);
      ws.cotrn02.cotrn2ai.cardnini.setValue(ws.wsVariables.wsCardNumN);
      // COB(222): PERFORM READ-CCXREF-FILE
      readCcxrefFile();
      // COB(223): MOVE XREF-ACCT-ID         TO ACTIDINI OF COTRN2AI
      ws.cotrn02.cotrn2ai.actidini.setValue(ws.cvact03y.cardXrefRecord.xrefAcctId);
      // COB(224): WHEN OTHER
    } else {
      // COB(225): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(226): MOVE 'Account or Card Number must be entered...' TO
      // COB(226):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Account or Card Number must be entered...");
      // COB(228): MOVE -1       TO ACTIDINL OF COTRN2AI
      ws.cotrn02.cotrn2ai.actidinl.setValue(-1);
      // COB(229): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(230): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                 VALIDATE-INPUT-DATA-FIELDS
   *----------------------------------------------------------------*/
  protected void validateInputDataFields() {
    // COB(237): IF ERR-FLG-ON
    if (ws.wsVariables.errFlgOn()) {
      // COB(238): MOVE SPACES      TO TTYPCDI  OF COTRN2AI
      // COB(238):                     TCATCDI  OF COTRN2AI
      // COB(238):                     TRNSRCI  OF COTRN2AI
      // COB(238):                     TRNAMTI  OF COTRN2AI
      // COB(238):                     TDESCI   OF COTRN2AI
      // COB(238):                     TORIGDTI OF COTRN2AI
      // COB(238):                     TPROCDTI OF COTRN2AI
      // COB(238):                     MIDI     OF COTRN2AI
      // COB(238):                     MNAMEI   OF COTRN2AI
      // COB(238):                     MCITYI   OF COTRN2AI
      // COB(238):                     MZIPI    OF COTRN2AI
      ws.cotrn02.cotrn2ai.ttypcdi.spaces();
      ws.cotrn02.cotrn2ai.tcatcdi.spaces();
      ws.cotrn02.cotrn2ai.trnsrci.spaces();
      ws.cotrn02.cotrn2ai.trnamti.spaces();
      ws.cotrn02.cotrn2ai.tdesci.spaces();
      ws.cotrn02.cotrn2ai.torigdti.spaces();
      ws.cotrn02.cotrn2ai.tprocdti.spaces();
      ws.cotrn02.cotrn2ai.midi.spaces();
      ws.cotrn02.cotrn2ai.mnamei.spaces();
      ws.cotrn02.cotrn2ai.mcityi.spaces();
      ws.cotrn02.cotrn2ai.mzipi.spaces();
      // COB(249): END-IF.
    }
    // COB(251): EVALUATE TRUE
    // COB(252): WHEN TTYPCDI OF COTRN2AI = SPACES OR LOW-VALUES
    if (ws.cotrn02.cotrn2ai.ttypcdi.isSpaces() || ws.cotrn02.cotrn2ai.ttypcdi.isLowValues()) {
      // COB(253): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(254): MOVE 'Type CD can NOT be empty...' TO
      // COB(254):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Type CD can NOT be empty...");
      // COB(256): MOVE -1       TO TTYPCDL OF COTRN2AI
      ws.cotrn02.cotrn2ai.ttypcdl.setValue(-1);
      // COB(257): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(258): WHEN TCATCDI OF COTRN2AI = SPACES OR LOW-VALUES
    } else if (ws.cotrn02.cotrn2ai.tcatcdi.isSpaces()
        || ws.cotrn02.cotrn2ai.tcatcdi.isLowValues()) {
      // COB(259): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(260): MOVE 'Category CD can NOT be empty...' TO
      // COB(260):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Category CD can NOT be empty...");
      // COB(262): MOVE -1       TO TCATCDL OF COTRN2AI
      ws.cotrn02.cotrn2ai.tcatcdl.setValue(-1);
      // COB(263): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(264): WHEN TRNSRCI OF COTRN2AI = SPACES OR LOW-VALUES
    } else if (ws.cotrn02.cotrn2ai.trnsrci.isSpaces()
        || ws.cotrn02.cotrn2ai.trnsrci.isLowValues()) {
      // COB(265): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(266): MOVE 'Source can NOT be empty...' TO
      // COB(266):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Source can NOT be empty...");
      // COB(268): MOVE -1       TO TRNSRCL OF COTRN2AI
      ws.cotrn02.cotrn2ai.trnsrcl.setValue(-1);
      // COB(269): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(270): WHEN TDESCI OF COTRN2AI = SPACES OR LOW-VALUES
    } else if (ws.cotrn02.cotrn2ai.tdesci.isSpaces() || ws.cotrn02.cotrn2ai.tdesci.isLowValues()) {
      // COB(271): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(272): MOVE 'Description can NOT be empty...' TO
      // COB(272):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Description can NOT be empty...");
      // COB(274): MOVE -1       TO TDESCL OF COTRN2AI
      ws.cotrn02.cotrn2ai.tdescl.setValue(-1);
      // COB(275): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(276): WHEN TRNAMTI OF COTRN2AI = SPACES OR LOW-VALUES
    } else if (ws.cotrn02.cotrn2ai.trnamti.isSpaces()
        || ws.cotrn02.cotrn2ai.trnamti.isLowValues()) {
      // COB(277): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(278): MOVE 'Amount can NOT be empty...' TO
      // COB(278):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Amount can NOT be empty...");
      // COB(280): MOVE -1       TO TRNAMTL OF COTRN2AI
      ws.cotrn02.cotrn2ai.trnamtl.setValue(-1);
      // COB(281): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(282): WHEN TORIGDTI OF COTRN2AI = SPACES OR LOW-VALUES
    } else if (ws.cotrn02.cotrn2ai.torigdti.isSpaces()
        || ws.cotrn02.cotrn2ai.torigdti.isLowValues()) {
      // COB(283): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(284): MOVE 'Orig Date can NOT be empty...' TO
      // COB(284):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Orig Date can NOT be empty...");
      // COB(286): MOVE -1       TO TORIGDTL OF COTRN2AI
      ws.cotrn02.cotrn2ai.torigdtl.setValue(-1);
      // COB(287): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(288): WHEN TPROCDTI OF COTRN2AI = SPACES OR LOW-VALUES
    } else if (ws.cotrn02.cotrn2ai.tprocdti.isSpaces()
        || ws.cotrn02.cotrn2ai.tprocdti.isLowValues()) {
      // COB(289): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(290): MOVE 'Proc Date can NOT be empty...' TO
      // COB(290):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Proc Date can NOT be empty...");
      // COB(292): MOVE -1       TO TPROCDTL OF COTRN2AI
      ws.cotrn02.cotrn2ai.tprocdtl.setValue(-1);
      // COB(293): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(294): WHEN MIDI OF COTRN2AI = SPACES OR LOW-VALUES
    } else if (ws.cotrn02.cotrn2ai.midi.isSpaces() || ws.cotrn02.cotrn2ai.midi.isLowValues()) {
      // COB(295): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(296): MOVE 'Merchant ID can NOT be empty...' TO
      // COB(296):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Merchant ID can NOT be empty...");
      // COB(298): MOVE -1       TO MIDL OF COTRN2AI
      ws.cotrn02.cotrn2ai.midl.setValue(-1);
      // COB(299): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(300): WHEN MNAMEI OF COTRN2AI = SPACES OR LOW-VALUES
    } else if (ws.cotrn02.cotrn2ai.mnamei.isSpaces() || ws.cotrn02.cotrn2ai.mnamei.isLowValues()) {
      // COB(301): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(302): MOVE 'Merchant Name can NOT be empty...' TO
      // COB(302):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Merchant Name can NOT be empty...");
      // COB(304): MOVE -1       TO MNAMEL OF COTRN2AI
      ws.cotrn02.cotrn2ai.mnamel.setValue(-1);
      // COB(305): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(306): WHEN MCITYI OF COTRN2AI = SPACES OR LOW-VALUES
    } else if (ws.cotrn02.cotrn2ai.mcityi.isSpaces() || ws.cotrn02.cotrn2ai.mcityi.isLowValues()) {
      // COB(307): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(308): MOVE 'Merchant City can NOT be empty...' TO
      // COB(308):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Merchant City can NOT be empty...");
      // COB(310): MOVE -1       TO MCITYL OF COTRN2AI
      ws.cotrn02.cotrn2ai.mcityl.setValue(-1);
      // COB(311): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(312): WHEN MZIPI OF COTRN2AI = SPACES OR LOW-VALUES
    } else if (ws.cotrn02.cotrn2ai.mzipi.isSpaces() || ws.cotrn02.cotrn2ai.mzipi.isLowValues()) {
      // COB(313): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(314): MOVE 'Merchant Zip can NOT be empty...' TO
      // COB(314):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Merchant Zip can NOT be empty...");
      // COB(316): MOVE -1       TO MZIPL OF COTRN2AI
      ws.cotrn02.cotrn2ai.mzipl.setValue(-1);
      // COB(317): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(318): WHEN OTHER
    } else {
      // COB(319): CONTINUE
      // do nothing
      // COB(320): END-EVALUATE.
    }
    // COB(322): EVALUATE TRUE
    // COB(323): WHEN TTYPCDI OF COTRN2AI NOT NUMERIC
    if (!ws.cotrn02.cotrn2ai.ttypcdi.isNumeric()) {
      // COB(324): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(325): MOVE 'Type CD must be Numeric...' TO
      // COB(325):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Type CD must be Numeric...");
      // COB(327): MOVE -1       TO TTYPCDL OF COTRN2AI
      ws.cotrn02.cotrn2ai.ttypcdl.setValue(-1);
      // COB(328): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(329): WHEN TCATCDI OF COTRN2AI NOT NUMERIC
    } else if (!ws.cotrn02.cotrn2ai.tcatcdi.isNumeric()) {
      // COB(330): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(331): MOVE 'Category CD must be Numeric...' TO
      // COB(331):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Category CD must be Numeric...");
      // COB(333): MOVE -1       TO TCATCDL OF COTRN2AI
      ws.cotrn02.cotrn2ai.tcatcdl.setValue(-1);
      // COB(334): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(335): WHEN OTHER
    } else {
      // COB(336): CONTINUE
      // do nothing
      // COB(337): END-EVALUATE
    }
    // COB(339): EVALUATE TRUE
    // COB(340): WHEN TRNAMTI OF COTRN2AI(1:1) NOT EQUAL '-' AND '+'
    if ((!ws.cotrn02.cotrn2ai.trnamti.getString(0, 1).equals("-")
            && !ws.cotrn02.cotrn2ai.trnamti.getString(0, 1).equals("+"))
        // COB(341): WHEN TRNAMTI OF COTRN2AI(2:8) NOT NUMERIC
        || !ws.cotrn02.cotrn2ai.trnamti.getString(1, 8).isNumeric()
        // COB(342): WHEN TRNAMTI OF COTRN2AI(10:1) NOT = '.'
        || !ws.cotrn02.cotrn2ai.trnamti.getString(9, 1).equals(".")
        // COB(343): WHEN TRNAMTI OF COTRN2AI(11:2) IS NOT NUMERIC
        || !ws.cotrn02.cotrn2ai.trnamti.getString(10, 2).isNumeric()) {
      // COB(344): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(345): MOVE 'Amount should be in format -99999999.99' TO
      // COB(345):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Amount should be in format -99999999.99");
      // COB(347): MOVE -1       TO TRNAMTL OF COTRN2AI
      ws.cotrn02.cotrn2ai.trnamtl.setValue(-1);
      // COB(348): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(349): WHEN OTHER
    } else {
      // COB(350): CONTINUE
      // do nothing
      // COB(351): END-EVALUATE
    }
    // COB(353): EVALUATE TRUE
    // COB(354): WHEN TORIGDTI OF COTRN2AI(1:4) IS NOT NUMERIC
    if ((!ws.cotrn02.cotrn2ai.torigdti.getString(0, 4).isNumeric())
        // COB(355): WHEN TORIGDTI OF COTRN2AI(5:1) NOT EQUAL '-'
        || !ws.cotrn02.cotrn2ai.torigdti.getString(4, 1).equals("-")
        // COB(356): WHEN TORIGDTI OF COTRN2AI(6:2) NOT NUMERIC
        || !ws.cotrn02.cotrn2ai.torigdti.getString(5, 2).isNumeric()
        // COB(357): WHEN TORIGDTI OF COTRN2AI(8:1) NOT EQUAL '-'
        || !ws.cotrn02.cotrn2ai.torigdti.getString(7, 1).equals("-")
        // COB(358): WHEN TORIGDTI OF COTRN2AI(9:2) NOT NUMERIC
        || !ws.cotrn02.cotrn2ai.torigdti.getString(8, 2).isNumeric()) {
      // COB(359): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(360): MOVE 'Orig Date should be in format YYYY-MM-DD' TO
      // COB(360):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Orig Date should be in format YYYY-MM-DD");
      // COB(362): MOVE -1       TO TORIGDTL OF COTRN2AI
      ws.cotrn02.cotrn2ai.torigdtl.setValue(-1);
      // COB(363): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(364): WHEN OTHER
    } else {
      // COB(365): CONTINUE
      // do nothing
      // COB(366): END-EVALUATE
    }
    // COB(368): EVALUATE TRUE
    // COB(369): WHEN TPROCDTI OF COTRN2AI(1:4) IS NOT NUMERIC
    if ((!ws.cotrn02.cotrn2ai.tprocdti.getString(0, 4).isNumeric())
        // COB(370): WHEN TPROCDTI OF COTRN2AI(5:1) NOT EQUAL '-'
        || !ws.cotrn02.cotrn2ai.tprocdti.getString(4, 1).equals("-")
        // COB(371): WHEN TPROCDTI OF COTRN2AI(6:2) NOT NUMERIC
        || !ws.cotrn02.cotrn2ai.tprocdti.getString(5, 2).isNumeric()
        // COB(372): WHEN TPROCDTI OF COTRN2AI(8:1) NOT EQUAL '-'
        || !ws.cotrn02.cotrn2ai.tprocdti.getString(7, 1).equals("-")
        // COB(373): WHEN TPROCDTI OF COTRN2AI(9:2) NOT NUMERIC
        || !ws.cotrn02.cotrn2ai.tprocdti.getString(8, 2).isNumeric()) {
      // COB(374): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(375): MOVE 'Proc Date should be in format YYYY-MM-DD' TO
      // COB(375):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Proc Date should be in format YYYY-MM-DD");
      // COB(377): MOVE -1       TO TPROCDTL OF COTRN2AI
      ws.cotrn02.cotrn2ai.tprocdtl.setValue(-1);
      // COB(378): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(379): WHEN OTHER
    } else {
      // COB(380): CONTINUE
      // do nothing
      // COB(381): END-EVALUATE
    }
    // COB(383): COMPUTE WS-TRAN-AMT-N = FUNCTION NUMVAL-C(TRNAMTI OF
    // COB(383): COTRN2AI)
    ws.wsVariables.wsTranAmtN.setValue(ws.cotrn02.cotrn2ai.trnamti.convertCurrencyToNumeric());
    // COB(385): MOVE WS-TRAN-AMT-N TO WS-TRAN-AMT-E
    ws.wsVariables.wsTranAmtE.setValue(ws.wsVariables.wsTranAmtN);
    // COB(386): MOVE WS-TRAN-AMT-E TO TRNAMTI OF COTRN2AI
    ws.cotrn02.cotrn2ai.trnamti.setValue(ws.wsVariables.wsTranAmtE);
    // COB(389): MOVE TORIGDTI OF COTRN2AI TO CSUTLDTC-DATE
    //
    //
    ws.csutldtcParm.csutldtcDate.setValue(ws.cotrn02.cotrn2ai.torigdti);
    // COB(390): MOVE WS-DATE-FORMAT       TO CSUTLDTC-DATE-FORMAT
    ws.csutldtcParm.csutldtcDateFormat.setValue(ws.wsVariables.wsDateFormat);
    // COB(391): MOVE SPACES               TO CSUTLDTC-RESULT
    ws.csutldtcParm.csutldtcResult.spaces();
    // COB(393): CALL 'CSUTLDTC' USING   CSUTLDTC-DATE
    // COB(393):                         CSUTLDTC-DATE-FORMAT
    // COB(393):                         CSUTLDTC-RESULT
    supernaut.call(
        "CSUTLDTC",
        ws.csutldtcParm.csutldtcDate,
        ws.csutldtcParm.csutldtcDateFormat,
        ws.csutldtcParm.csutldtcResult);
    // COB(397): IF CSUTLDTC-RESULT-SEV-CD = '0000'
    if (ws.csutldtcParm.csutldtcResult.csutldtcResultSevCd.equals("0000")) {
      // COB(398): CONTINUE
      // do nothing
      // COB(399): ELSE
    } else {
      // COB(400): IF CSUTLDTC-RESULT-MSG-NUM NOT = '2513'
      if (!ws.csutldtcParm.csutldtcResult.csutldtcResultMsgNum.equals("2513")) {
        // COB(401): MOVE 'Orig Date - Not a valid date...'
        // COB(401):   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Orig Date - Not a valid date...");
        // COB(403): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(404): MOVE -1       TO TORIGDTL OF COTRN2AI
        ws.cotrn02.cotrn2ai.torigdtl.setValue(-1);
        // COB(405): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(406): END-IF
      }
      // COB(407): END-IF
    }
    // COB(409): MOVE TPROCDTI OF COTRN2AI TO CSUTLDTC-DATE
    ws.csutldtcParm.csutldtcDate.setValue(ws.cotrn02.cotrn2ai.tprocdti);
    // COB(410): MOVE WS-DATE-FORMAT       TO CSUTLDTC-DATE-FORMAT
    ws.csutldtcParm.csutldtcDateFormat.setValue(ws.wsVariables.wsDateFormat);
    // COB(411): MOVE SPACES               TO CSUTLDTC-RESULT
    ws.csutldtcParm.csutldtcResult.spaces();
    // COB(413): CALL 'CSUTLDTC' USING   CSUTLDTC-DATE
    // COB(413):                         CSUTLDTC-DATE-FORMAT
    // COB(413):                         CSUTLDTC-RESULT
    supernaut.call(
        "CSUTLDTC",
        ws.csutldtcParm.csutldtcDate,
        ws.csutldtcParm.csutldtcDateFormat,
        ws.csutldtcParm.csutldtcResult);
    // COB(417): IF CSUTLDTC-RESULT-SEV-CD = '0000'
    if (ws.csutldtcParm.csutldtcResult.csutldtcResultSevCd.equals("0000")) {
      // COB(418): CONTINUE
      // do nothing
      // COB(419): ELSE
    } else {
      // COB(420): IF CSUTLDTC-RESULT-MSG-NUM NOT = '2513'
      if (!ws.csutldtcParm.csutldtcResult.csutldtcResultMsgNum.equals("2513")) {
        // COB(421): MOVE 'Proc Date - Not a valid date...'
        // COB(421):   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Proc Date - Not a valid date...");
        // COB(423): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(424): MOVE -1       TO TPROCDTL OF COTRN2AI
        ws.cotrn02.cotrn2ai.tprocdtl.setValue(-1);
        // COB(425): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(426): END-IF
      }
      // COB(427): END-IF
    }
    // COB(430): IF MIDI OF COTRN2AI IS NOT NUMERIC
    //
    //
    if (!ws.cotrn02.cotrn2ai.midi.isNumeric()) {
      // COB(431): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(432): MOVE 'Merchant ID must be Numeric...' TO
      // COB(432):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Merchant ID must be Numeric...");
      // COB(434): MOVE -1       TO MIDL OF COTRN2AI
      ws.cotrn02.cotrn2ai.midl.setValue(-1);
      // COB(435): PERFORM SEND-TRNADD-SCREEN
      sendTrnaddScreen();
      // COB(436): END-IF
    }
  }

  /***
   *----------------------------------------------------------------*
   *                        ADD-TRANSACTION
   *----------------------------------------------------------------*/
  protected void addTransaction() {
    // COB(444): MOVE HIGH-VALUES TO TRAN-ID
    ws.cvtra05y.tranRecord.tranId.highValues();
    // COB(445): PERFORM STARTBR-TRANSACT-FILE
    startbrTransactFile();
    // COB(446): PERFORM READPREV-TRANSACT-FILE
    readprevTransactFile();
    // COB(447): PERFORM ENDBR-TRANSACT-FILE
    endbrTransactFile();
    // COB(448): MOVE TRAN-ID     TO WS-TRAN-ID-N
    ws.wsVariables.wsTranIdN.setValue(ws.cvtra05y.tranRecord.tranId);
    // COB(449): ADD 1 TO WS-TRAN-ID-N
    ws.wsVariables.wsTranIdN.add(1);
    // COB(450): INITIALIZE TRAN-RECORD
    ws.cvtra05y.tranRecord.initialize();
    // COB(451): MOVE WS-TRAN-ID-N         TO TRAN-ID
    ws.cvtra05y.tranRecord.tranId.setValue(ws.wsVariables.wsTranIdN);
    // COB(452): MOVE TTYPCDI  OF COTRN2AI TO TRAN-TYPE-CD
    ws.cvtra05y.tranRecord.tranTypeCd.setValue(ws.cotrn02.cotrn2ai.ttypcdi);
    // COB(453): MOVE TCATCDI  OF COTRN2AI TO TRAN-CAT-CD
    ws.cvtra05y.tranRecord.tranCatCd.setValue(ws.cotrn02.cotrn2ai.tcatcdi);
    // COB(454): MOVE TRNSRCI  OF COTRN2AI TO TRAN-SOURCE
    ws.cvtra05y.tranRecord.tranSource.setValue(ws.cotrn02.cotrn2ai.trnsrci);
    // COB(455): MOVE TDESCI   OF COTRN2AI TO TRAN-DESC
    ws.cvtra05y.tranRecord.tranDesc.setValue(ws.cotrn02.cotrn2ai.tdesci);
    // COB(456): COMPUTE WS-TRAN-AMT-N = FUNCTION NUMVAL-C(TRNAMTI OF
    // COB(456): COTRN2AI)
    ws.wsVariables.wsTranAmtN.setValue(ws.cotrn02.cotrn2ai.trnamti.convertCurrencyToNumeric());
    // COB(458): MOVE WS-TRAN-AMT-N TO TRAN-AMT
    ws.cvtra05y.tranRecord.tranAmt.setValue(ws.wsVariables.wsTranAmtN);
    // COB(459): MOVE CARDNINI OF COTRN2AI TO TRAN-CARD-NUM
    ws.cvtra05y.tranRecord.tranCardNum.setValue(ws.cotrn02.cotrn2ai.cardnini);
    // COB(460): MOVE MIDI     OF COTRN2AI TO TRAN-MERCHANT-ID
    ws.cvtra05y.tranRecord.tranMerchantId.setValue(ws.cotrn02.cotrn2ai.midi);
    // COB(461): MOVE MNAMEI   OF COTRN2AI TO TRAN-MERCHANT-NAME
    ws.cvtra05y.tranRecord.tranMerchantName.setValue(ws.cotrn02.cotrn2ai.mnamei);
    // COB(462): MOVE MCITYI   OF COTRN2AI TO TRAN-MERCHANT-CITY
    ws.cvtra05y.tranRecord.tranMerchantCity.setValue(ws.cotrn02.cotrn2ai.mcityi);
    // COB(463): MOVE MZIPI    OF COTRN2AI TO TRAN-MERCHANT-ZIP
    ws.cvtra05y.tranRecord.tranMerchantZip.setValue(ws.cotrn02.cotrn2ai.mzipi);
    // COB(464): MOVE TORIGDTI OF COTRN2AI TO TRAN-ORIG-TS
    ws.cvtra05y.tranRecord.tranOrigTs.setValue(ws.cotrn02.cotrn2ai.torigdti);
    // COB(465): MOVE TPROCDTI OF COTRN2AI TO TRAN-PROC-TS
    ws.cvtra05y.tranRecord.tranProcTs.setValue(ws.cotrn02.cotrn2ai.tprocdti);
    // COB(466): PERFORM WRITE-TRANSACT-FILE.
    writeTransactFile();
  }

  /***
   *----------------------------------------------------------------*
   *                      COPY-LAST-TRAN-DATA
   *----------------------------------------------------------------*/
  protected void copyLastTranData() {
    // COB(473): PERFORM VALIDATE-INPUT-KEY-FIELDS
    validateInputKeyFields();
    // COB(475): MOVE HIGH-VALUES TO TRAN-ID
    ws.cvtra05y.tranRecord.tranId.highValues();
    // COB(476): PERFORM STARTBR-TRANSACT-FILE
    startbrTransactFile();
    // COB(477): PERFORM READPREV-TRANSACT-FILE
    readprevTransactFile();
    // COB(478): PERFORM ENDBR-TRANSACT-FILE
    endbrTransactFile();
    // COB(480): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(481): MOVE TRAN-AMT TO WS-TRAN-AMT-E
      ws.wsVariables.wsTranAmtE.setValue(ws.cvtra05y.tranRecord.tranAmt);
      // COB(482): MOVE TRAN-TYPE-CD        TO TTYPCDI  OF COTRN2AI
      ws.cotrn02.cotrn2ai.ttypcdi.setValue(ws.cvtra05y.tranRecord.tranTypeCd);
      // COB(483): MOVE TRAN-CAT-CD         TO TCATCDI  OF COTRN2AI
      ws.cotrn02.cotrn2ai.tcatcdi.setValue(ws.cvtra05y.tranRecord.tranCatCd);
      // COB(484): MOVE TRAN-SOURCE         TO TRNSRCI  OF COTRN2AI
      ws.cotrn02.cotrn2ai.trnsrci.setValue(ws.cvtra05y.tranRecord.tranSource);
      // COB(485): MOVE WS-TRAN-AMT-E       TO TRNAMTI  OF COTRN2AI
      ws.cotrn02.cotrn2ai.trnamti.setValue(ws.wsVariables.wsTranAmtE);
      // COB(486): MOVE TRAN-DESC           TO TDESCI   OF COTRN2AI
      ws.cotrn02.cotrn2ai.tdesci.setValue(ws.cvtra05y.tranRecord.tranDesc);
      // COB(487): MOVE TRAN-ORIG-TS        TO TORIGDTI OF COTRN2AI
      ws.cotrn02.cotrn2ai.torigdti.setValue(ws.cvtra05y.tranRecord.tranOrigTs);
      // COB(488): MOVE TRAN-PROC-TS        TO TPROCDTI OF COTRN2AI
      ws.cotrn02.cotrn2ai.tprocdti.setValue(ws.cvtra05y.tranRecord.tranProcTs);
      // COB(489): MOVE TRAN-MERCHANT-ID    TO MIDI     OF COTRN2AI
      ws.cotrn02.cotrn2ai.midi.setValue(ws.cvtra05y.tranRecord.tranMerchantId);
      // COB(490): MOVE TRAN-MERCHANT-NAME  TO MNAMEI   OF COTRN2AI
      ws.cotrn02.cotrn2ai.mnamei.setValue(ws.cvtra05y.tranRecord.tranMerchantName);
      // COB(491): MOVE TRAN-MERCHANT-CITY  TO MCITYI   OF COTRN2AI
      ws.cotrn02.cotrn2ai.mcityi.setValue(ws.cvtra05y.tranRecord.tranMerchantCity);
      // COB(492): MOVE TRAN-MERCHANT-ZIP   TO MZIPI    OF COTRN2AI
      ws.cotrn02.cotrn2ai.mzipi.setValue(ws.cvtra05y.tranRecord.tranMerchantZip);
      // COB(493): END-IF
    }
    // COB(495): PERFORM PROCESS-ENTER-KEY.
    processEnterKey();
  }

  /***
   *----------------------------------------------------------------*
   *                      RETURN-TO-PREV-SCREEN
   *----------------------------------------------------------------*/
  protected void returnToPrevScreen() {
    // COB(502): IF CDEMO-TO-PROGRAM = LOW-VALUES OR SPACES
    if (ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isLowValues()
        || ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isSpaces()) {
      // COB(503): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(504): END-IF
    }
    // COB(505): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsVariables.wsTranid);
    // COB(506): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsVariables.wsPgmname);
    // COB(507): MOVE ZEROS        TO CDEMO-PGM-CONTEXT
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.zeros();
    // COB(508): EXEC CICS
    // COB(508):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
    // COB(508):     COMMAREA(CARDDEMO-COMMAREA)
    // COB(508): END-EXEC.
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
   *                      SEND-TRNADD-SCREEN
   *----------------------------------------------------------------*/
  protected void sendTrnaddScreen() {
    // COB(518): PERFORM POPULATE-HEADER-INFO
    populateHeaderInfo();
    // COB(520): MOVE WS-MESSAGE TO ERRMSGO OF COTRN2AO
    ws.cotrn02.cotrn2ao.errmsgo.setValue(ws.wsVariables.wsMessage);
    // COB(522): EXEC CICS SEND
    // COB(522):           MAP('COTRN2A')
    // COB(522):           MAPSET('COTRN02')
    // COB(522):           FROM(COTRN2AO)
    // COB(522):           ERASE
    // COB(522):           CURSOR
    // COB(522): END-EXEC.
    try {
      supernaut
          .sendMap("COTRN2A") //
          .mapset("COTRN02") //
          .from(ws.cotrn02.cotrn2ao) //
          .erase() //
          .cursor() //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    // COB(530): EXEC CICS RETURN
    // COB(530):           TRANSID (WS-TRANID)
    // COB(530):           COMMAREA (CARDDEMO-COMMAREA)
    // COB(530):       *              LENGTH(LENGTH OF CARDDEMO-COMMAREA)
    // COB(530): END-EXEC.
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
   *                      RECEIVE-TRNADD-SCREEN
   *----------------------------------------------------------------*/
  protected void receiveTrnaddScreen() {
    // COB(541): EXEC CICS RECEIVE
    // COB(541):           MAP('COTRN2A')
    // COB(541):           MAPSET('COTRN02')
    // COB(541):           INTO(COTRN2AI)
    // COB(541):           RESP(WS-RESP-CD)
    // COB(541):           RESP2(WS-REAS-CD)
    // COB(541): END-EXEC.
    supernaut
        .receiveMap("COTRN2A") //
        .mapset("COTRN02") //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cotrn02.cotrn2ai) //
        .exec();
  }

  /***
   *----------------------------------------------------------------*
   *                      POPULATE-HEADER-INFO
   *----------------------------------------------------------------*/
  protected void populateHeaderInfo() {
    // COB(554): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(556): MOVE CCDA-TITLE01           TO TITLE01O OF COTRN2AO
    ws.cotrn02.cotrn2ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(557): MOVE CCDA-TITLE02           TO TITLE02O OF COTRN2AO
    ws.cotrn02.cotrn2ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(558): MOVE WS-TRANID              TO TRNNAMEO OF COTRN2AO
    ws.cotrn02.cotrn2ao.trnnameo.setValue(ws.wsVariables.wsTranid);
    // COB(559): MOVE WS-PGMNAME             TO PGMNAMEO OF COTRN2AO
    ws.cotrn02.cotrn2ao.pgmnameo.setValue(ws.wsVariables.wsPgmname);
    // COB(561): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(562): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(563): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(565): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF COTRN2AO
    ws.cotrn02.cotrn2ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(567): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(568): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(569): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(571): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF COTRN2AO.
    ws.cotrn02.cotrn2ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***
   *----------------------------------------------------------------*
   *                      READ-CXACAIX-FILE
   *----------------------------------------------------------------*/
  protected void readCxacaixFile() {
    // COB(578): EXEC CICS READ
    // COB(578):      DATASET   (WS-CXACAIX-FILE)
    // COB(578):      INTO      (CARD-XREF-RECORD)
    // COB(578):      LENGTH    (LENGTH OF CARD-XREF-RECORD)
    // COB(578):      RIDFLD    (XREF-ACCT-ID)
    // COB(578):      KEYLENGTH (LENGTH OF XREF-ACCT-ID)
    // COB(578):      RESP      (WS-RESP-CD)
    // COB(578):      RESP2     (WS-REAS-CD)
    // COB(578): END-EXEC
    supernaut
        .read(ws.wsVariables.wsCxacaixFile.getValue()) //
        .length(ws.cvact03y.cardXrefRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cvact03y.cardXrefRecord) //
        .ridfld(ws.cvact03y.cardXrefRecord.xrefAcctId) //
        .keylength(ws.cvact03y.cardXrefRecord.xrefAcctId.length()) //
        .exec();
    // COB(588): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(589): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(590): CONTINUE
        // do nothing
        // COB(591): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(592): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(593): MOVE 'Account ID NOT found...' TO
        // COB(593):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Account ID NOT found...");
        // COB(595): MOVE -1       TO ACTIDINL OF COTRN2AI
        ws.cotrn02.cotrn2ai.actidinl.setValue(-1);
        // COB(596): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(597): WHEN OTHER
        break;
      default:
        // COB(598): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(599): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(600): MOVE 'Unable to lookup Acct in XREF AIX file...' TO
        // COB(600):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup Acct in XREF AIX file...");
        // COB(602): MOVE -1       TO ACTIDINL OF COTRN2AI
        ws.cotrn02.cotrn2ai.actidinl.setValue(-1);
        // COB(603): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(604): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      READ-CCXREF-FILE
   *----------------------------------------------------------------*/
  protected void readCcxrefFile() {
    // COB(611): EXEC CICS READ
    // COB(611):      DATASET   (WS-CCXREF-FILE)
    // COB(611):      INTO      (CARD-XREF-RECORD)
    // COB(611):      LENGTH    (LENGTH OF CARD-XREF-RECORD)
    // COB(611):      RIDFLD    (XREF-CARD-NUM)
    // COB(611):      KEYLENGTH (LENGTH OF XREF-CARD-NUM)
    // COB(611):      RESP      (WS-RESP-CD)
    // COB(611):      RESP2     (WS-REAS-CD)
    // COB(611): END-EXEC
    supernaut
        .read(ws.wsVariables.wsCcxrefFile.getValue()) //
        .length(ws.cvact03y.cardXrefRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cvact03y.cardXrefRecord) //
        .ridfld(ws.cvact03y.cardXrefRecord.xrefCardNum) //
        .keylength(ws.cvact03y.cardXrefRecord.xrefCardNum.length()) //
        .exec();
    // COB(621): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(622): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(623): CONTINUE
        // do nothing
        // COB(624): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(625): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(626): MOVE 'Card Number NOT found...' TO
        // COB(626):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Card Number NOT found...");
        // COB(628): MOVE -1       TO CARDNINL OF COTRN2AI
        ws.cotrn02.cotrn2ai.cardninl.setValue(-1);
        // COB(629): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(630): WHEN OTHER
        break;
      default:
        // COB(631): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(632): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(633): MOVE 'Unable to lookup Card # in XREF file...' TO
        // COB(633):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup Card # in XREF file...");
        // COB(635): MOVE -1       TO CARDNINL OF COTRN2AI
        ws.cotrn02.cotrn2ai.cardninl.setValue(-1);
        // COB(636): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(637): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                    STARTBR-TRANSACT-FILE
   *----------------------------------------------------------------*/
  protected void startbrTransactFile() {
    // COB(644): EXEC CICS STARTBR
    // COB(644):      DATASET   (WS-TRANSACT-FILE)
    // COB(644):      RIDFLD    (TRAN-ID)
    // COB(644):      KEYLENGTH (LENGTH OF TRAN-ID)
    // COB(644):      RESP      (WS-RESP-CD)
    // COB(644):      RESP2     (WS-REAS-CD)
    // COB(644): END-EXEC
    supernaut
        .startbr(ws.wsVariables.wsTransactFile.getValue()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .ridfld(ws.cvtra05y.tranRecord.tranId) //
        .keylength(ws.cvtra05y.tranRecord.tranId.length()) //
        .exec();
    // COB(652): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(653): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(654): CONTINUE
        // do nothing
        // COB(655): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(656): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(657): MOVE 'Transaction ID NOT found...' TO
        // COB(657):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Transaction ID NOT found...");
        // COB(659): MOVE -1       TO ACTIDINL OF COTRN2AI
        ws.cotrn02.cotrn2ai.actidinl.setValue(-1);
        // COB(660): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(661): WHEN OTHER
        break;
      default:
        // COB(662): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(663): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(664): MOVE 'Unable to lookup Transaction...' TO
        // COB(664):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup Transaction...");
        // COB(666): MOVE -1       TO ACTIDINL OF COTRN2AI
        ws.cotrn02.cotrn2ai.actidinl.setValue(-1);
        // COB(667): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(668): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                    READPREV-TRANSACT-FILE
   *----------------------------------------------------------------*/
  protected void readprevTransactFile() {
    // COB(675): EXEC CICS READPREV
    // COB(675):      DATASET   (WS-TRANSACT-FILE)
    // COB(675):      INTO      (TRAN-RECORD)
    // COB(675):      LENGTH    (LENGTH OF TRAN-RECORD)
    // COB(675):      RIDFLD    (TRAN-ID)
    // COB(675):      KEYLENGTH (LENGTH OF TRAN-ID)
    // COB(675):      RESP      (WS-RESP-CD)
    // COB(675):      RESP2     (WS-REAS-CD)
    // COB(675): END-EXEC
    supernaut
        .readPrev(ws.wsVariables.wsTransactFile.getValue()) //
        .length(ws.cvtra05y.tranRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cvtra05y.tranRecord) //
        .ridfld(ws.cvtra05y.tranRecord.tranId) //
        .keylength(ws.cvtra05y.tranRecord.tranId.length()) //
        .exec();
    // COB(685): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(686): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(687): CONTINUE
        // do nothing
        // COB(688): WHEN DFHRESP(ENDFILE)
        break;
      case Dfhresp.ENDFILE:
        // COB(689): MOVE ZEROS TO TRAN-ID
        ws.cvtra05y.tranRecord.tranId.zeros();
        // COB(690): WHEN OTHER
        break;
      default:
        // COB(691): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(692): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(693): MOVE 'Unable to lookup Transaction...' TO
        // COB(693):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup Transaction...");
        // COB(695): MOVE -1       TO ACTIDINL OF COTRN2AI
        ws.cotrn02.cotrn2ai.actidinl.setValue(-1);
        // COB(696): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(697): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                    ENDBR-TRANSACT-FILE
   *----------------------------------------------------------------*/
  protected void endbrTransactFile() {
    // COB(704): EXEC CICS ENDBR
    // COB(704):      DATASET   (WS-TRANSACT-FILE)
    // COB(704): END-EXEC.
    try {
      supernaut
          .endbr(ws.wsVariables.wsTransactFile.getValue()) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                    WRITE-TRANSACT-FILE
   *----------------------------------------------------------------*/
  protected void writeTransactFile() {
    // COB(713): EXEC CICS WRITE
    // COB(713):      DATASET   (WS-TRANSACT-FILE)
    // COB(713):      FROM      (TRAN-RECORD)
    // COB(713):      LENGTH    (LENGTH OF TRAN-RECORD)
    // COB(713):      RIDFLD    (TRAN-ID)
    // COB(713):      KEYLENGTH (LENGTH OF TRAN-ID)
    // COB(713):      RESP      (WS-RESP-CD)
    // COB(713):      RESP2     (WS-REAS-CD)
    // COB(713): END-EXEC
    supernaut
        .write(ws.wsVariables.wsTransactFile.getValue()) //
        .length(ws.cvtra05y.tranRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .from(ws.cvtra05y.tranRecord) //
        .ridfld(ws.cvtra05y.tranRecord.tranId) //
        .keylength(ws.cvtra05y.tranRecord.tranId.length()) //
        .exec();
    // COB(723): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(724): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(725): PERFORM INITIALIZE-ALL-FIELDS
        initializeAllFields();
        // COB(726): MOVE SPACES             TO WS-MESSAGE
        ws.wsVariables.wsMessage.spaces();
        // COB(727): MOVE DFHGREEN           TO ERRMSGC  OF COTRN2AO
        ws.cotrn02.cotrn2ao.errmsgc.setValue(Dfhbmsca.DFHGREEN.getValue());
        // COB(728): STRING 'Transaction added successfully. '
        // COB(728):                             DELIMITED BY SIZE
        // COB(728):   ' Your Tran ID is ' DELIMITED BY SIZE
        // COB(728):        TRAN-ID  DELIMITED BY SPACE
        // COB(728):        '.' DELIMITED BY SIZE
        // COB(728):   INTO WS-MESSAGE
        ws.wsVariables.wsMessage.concat(
            "Transaction added successfully. ",
            " Your Tran ID is ",
            ws.cvtra05y.tranRecord.tranId.substringToValue(" "),
            ".");
        // COB(734): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(735): WHEN DFHRESP(DUPKEY)
        break;
      case Dfhresp.DUPKEY:
        // COB(736): WHEN DFHRESP(DUPREC)
      case Dfhresp.DUPREC:
        // COB(737): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(738): MOVE 'Tran ID already exist...' TO
        // COB(738):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Tran ID already exist...");
        // COB(740): MOVE -1       TO ACTIDINL OF COTRN2AI
        ws.cotrn02.cotrn2ai.actidinl.setValue(-1);
        // COB(741): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(742): WHEN OTHER
        break;
      default:
        // COB(743): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(744): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(745): MOVE 'Unable to Add Transaction...' TO
        // COB(745):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to Add Transaction...");
        // COB(747): MOVE -1       TO ACTIDINL OF COTRN2AI
        ws.cotrn02.cotrn2ai.actidinl.setValue(-1);
        // COB(748): PERFORM SEND-TRNADD-SCREEN
        sendTrnaddScreen();
        // COB(749): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                    CLEAR-CURRENT-SCREEN
   *----------------------------------------------------------------*/
  protected void clearCurrentScreen() {
    // COB(756): PERFORM INITIALIZE-ALL-FIELDS.
    initializeAllFields();
    // COB(757): PERFORM SEND-TRNADD-SCREEN.
    sendTrnaddScreen();
  }

  /***
   *----------------------------------------------------------------*
   *                    INITIALIZE-ALL-FIELDS
   *----------------------------------------------------------------*/
  protected void initializeAllFields() {
    // COB(764): MOVE -1              TO ACTIDINL OF COTRN2AI
    ws.cotrn02.cotrn2ai.actidinl.setValue(-1);
    // COB(765): MOVE SPACES          TO ACTIDINI OF COTRN2AI
    // COB(765):                         CARDNINI OF COTRN2AI
    // COB(765):                         TTYPCDI  OF COTRN2AI
    // COB(765):                         TCATCDI  OF COTRN2AI
    // COB(765):                         TRNSRCI  OF COTRN2AI
    // COB(765):                         TRNAMTI  OF COTRN2AI
    // COB(765):                         TDESCI   OF COTRN2AI
    // COB(765):                         TORIGDTI OF COTRN2AI
    // COB(765):                         TPROCDTI OF COTRN2AI
    // COB(765):                         MIDI     OF COTRN2AI
    // COB(765):                         MNAMEI   OF COTRN2AI
    // COB(765):                         MCITYI   OF COTRN2AI
    // COB(765):                         MZIPI    OF COTRN2AI
    // COB(765):                         CONFIRMI OF COTRN2AI
    // COB(765):                         WS-MESSAGE.
    ws.cotrn02.cotrn2ai.actidini.spaces();
    ws.cotrn02.cotrn2ai.cardnini.spaces();
    ws.cotrn02.cotrn2ai.ttypcdi.spaces();
    ws.cotrn02.cotrn2ai.tcatcdi.spaces();
    ws.cotrn02.cotrn2ai.trnsrci.spaces();
    ws.cotrn02.cotrn2ai.trnamti.spaces();
    ws.cotrn02.cotrn2ai.tdesci.spaces();
    ws.cotrn02.cotrn2ai.torigdti.spaces();
    ws.cotrn02.cotrn2ai.tprocdti.spaces();
    ws.cotrn02.cotrn2ai.midi.spaces();
    ws.cotrn02.cotrn2ai.mnamei.spaces();
    ws.cotrn02.cotrn2ai.mcityi.spaces();
    ws.cotrn02.cotrn2ai.mzipi.spaces();
    ws.cotrn02.cotrn2ai.confirmi.spaces();
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
