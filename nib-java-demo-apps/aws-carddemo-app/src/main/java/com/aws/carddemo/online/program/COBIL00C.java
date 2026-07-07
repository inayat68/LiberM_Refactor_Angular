package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cobil00c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COBIL00C extends AbstractOnlineProgram {

  @ProgramStorage final Cobil00cWorking ws = new Cobil00cWorking();

  // Linkage
  public static class Cobil00cLinkage extends NGroup {
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

  final Cobil00cLinkage params = new Cobil00cLinkage();

  public COBIL00C(Context supernaut) {
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
    // COB(101): SET ERR-FLG-OFF     TO TRUE
    ws.wsVariables.setErrFlgOff(true);
    // COB(102): SET USR-MODIFIED-NO TO TRUE
    ws.wsVariables.setUsrModifiedNo(true);
    // COB(104): MOVE SPACES TO WS-MESSAGE
    // COB(104):                ERRMSGO OF COBIL0AO
    ws.wsVariables.wsMessage.spaces();
    ws.cobil00.cobil0ao.errmsgo.spaces();
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
        // COB(114): MOVE LOW-VALUES          TO COBIL0AO
        ws.cobil00.cobil0ao.lowValues();
        // COB(115): MOVE -1       TO ACTIDINL OF COBIL0AI
        ws.cobil00.cobil0ai.actidinl.setValue(-1);
        // COB(116): IF CDEMO-CB00-TRN-SELECTED NOT =
        // COB(116):                            SPACES AND LOW-VALUES
        if (!ws.carddemoCommarea.cdemoCb00Info.cdemoCb00TrnSelected.isSpaces()
            && !ws.carddemoCommarea.cdemoCb00Info.cdemoCb00TrnSelected.isLowValues()) {
          // COB(118): MOVE CDEMO-CB00-TRN-SELECTED TO
          // COB(118):      ACTIDINI OF COBIL0AI
          ws.cobil00.cobil0ai.actidini.setValue(
              ws.carddemoCommarea.cdemoCb00Info.cdemoCb00TrnSelected);
          // COB(120): PERFORM PROCESS-ENTER-KEY
          processEnterKey();
          // COB(121): END-IF
        }
        // COB(122): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(123): ELSE
      } else {
        // COB(124): PERFORM RECEIVE-BILLPAY-SCREEN
        receiveBillpayScreen();
        // COB(125): EVALUATE EIBAID
        switch (dfheiblk.getEibaid()) {
            // COB(126): WHEN DFHENTER
          case DFHENTER:
            // COB(127): PERFORM PROCESS-ENTER-KEY
            processEnterKey();
            // COB(128): WHEN DFHPF3
            break;
          case DFHPF3:
            // COB(129): IF CDEMO-FROM-PROGRAM = SPACES OR LOW-VALUES
            if (ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isSpaces()
                || ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isLowValues()) {
              // COB(130): MOVE 'COMEN01C' TO CDEMO-TO-PROGRAM
              ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COMEN01C");
              // COB(131): ELSE
            } else {
              // COB(132): MOVE CDEMO-FROM-PROGRAM TO
              // COB(132): CDEMO-TO-PROGRAM
              ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
                  ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram);
              // COB(134): END-IF
            }
            // COB(135): PERFORM RETURN-TO-PREV-SCREEN
            returnToPrevScreen();
            // COB(136): WHEN DFHPF4
            break;
          case DFHPF4:
            // COB(137): PERFORM CLEAR-CURRENT-SCREEN
            clearCurrentScreen();
            // COB(138): WHEN OTHER
            break;
          default:
            // COB(139): MOVE 'Y'                       TO WS-ERR-FLG
            ws.wsVariables.wsErrFlg.setString("Y");
            // COB(140): MOVE CCDA-MSG-INVALID-KEY      TO WS-MESSAGE
            ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgInvalidKey);
            // COB(141): PERFORM SEND-BILLPAY-SCREEN
            sendBillpayScreen();
            // COB(142): END-EVALUATE
        }
        // COB(143): END-IF
      }
      // COB(144): END-IF
    }
    // COB(146): EXEC CICS RETURN
    // COB(146):           TRANSID (WS-TRANID)
    // COB(146):           COMMAREA (CARDDEMO-COMMAREA)
    // COB(146): END-EXEC.
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
    // COB(156): SET CONF-PAY-NO TO TRUE
    ws.wsVariables.setConfPayNo(true);
    // COB(158): EVALUATE TRUE
    // COB(159): WHEN ACTIDINI OF COBIL0AI = SPACES OR LOW-VALUES
    if (ws.cobil00.cobil0ai.actidini.isSpaces() || ws.cobil00.cobil0ai.actidini.isLowValues()) {
      // COB(160): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(161): MOVE 'Acct ID can NOT be empty...' TO
      // COB(161):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Acct ID can NOT be empty...");
      // COB(163): MOVE -1       TO ACTIDINL OF COBIL0AI
      ws.cobil00.cobil0ai.actidinl.setValue(-1);
      // COB(164): PERFORM SEND-BILLPAY-SCREEN
      sendBillpayScreen();
      // COB(165): WHEN OTHER
    } else {
      // COB(166): CONTINUE
      // do nothing
      // COB(167): END-EVALUATE
    }
    // COB(169): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(170): MOVE ACTIDINI  OF COBIL0AI TO ACCT-ID
      // COB(170):                               XREF-ACCT-ID
      ws.cvact01y.accountRecord.acctId.setValue(ws.cobil00.cobil0ai.actidini);
      ws.cvact03y.cardXrefRecord.xrefAcctId.setValue(ws.cobil00.cobil0ai.actidini);
      // COB(173): EVALUATE CONFIRMI OF COBIL0AI
      // COB(174): WHEN 'Y'
      if ((ws.cobil00.cobil0ai.confirmi.equals("Y"))
          // COB(175): WHEN 'y'
          || ws.cobil00.cobil0ai.confirmi.equals("y")) {
        // COB(176): SET CONF-PAY-YES TO TRUE
        ws.wsVariables.setConfPayYes(true);
        // COB(177): PERFORM READ-ACCTDAT-FILE
        readAcctdatFile();
        // COB(178): WHEN 'N'
      } else if (ws.cobil00.cobil0ai.confirmi.equals("N")
          // COB(179): WHEN 'n'
          || ws.cobil00.cobil0ai.confirmi.equals("n")) {
        // COB(180): PERFORM CLEAR-CURRENT-SCREEN
        clearCurrentScreen();
        // COB(181): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(182): WHEN SPACES
      } else if (ws.cobil00.cobil0ai.confirmi.isSpaces()
          // COB(183): WHEN LOW-VALUES
          || ws.cobil00.cobil0ai.confirmi.isLowValues()) {
        // COB(184): PERFORM READ-ACCTDAT-FILE
        readAcctdatFile();
        // COB(185): WHEN OTHER
      } else {
        // COB(186): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(187): MOVE 'Invalid value. Valid values are (Y/N)...'
        // COB(187):              TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Invalid value. Valid values are (Y/N)...");
        // COB(189): MOVE -1      TO CONFIRML OF COBIL0AI
        ws.cobil00.cobil0ai.confirml.setValue(-1);
        // COB(190): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(191): END-EVALUATE
      }
      // COB(193): MOVE ACCT-CURR-BAL TO WS-CURR-BAL
      ws.wsVariables.wsCurrBal.setValue(ws.cvact01y.accountRecord.acctCurrBal);
      // COB(194): MOVE WS-CURR-BAL   TO CURBALI    OF COBIL0AI
      ws.cobil00.cobil0ai.curbali.setValue(ws.wsVariables.wsCurrBal);
      // COB(195): END-IF
    }
    // COB(197): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(198): IF ACCT-CURR-BAL <= ZEROS AND
      // COB(198):    ACTIDINI OF COBIL0AI NOT = SPACES AND LOW-VALUES
      if (ws.cvact01y.accountRecord.acctCurrBal.lessEqualThan(0)
          && !ws.cobil00.cobil0ai.actidini.isSpaces()
          && !ws.cobil00.cobil0ai.actidini.isLowValues()) {
        // COB(200): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(201): MOVE 'You have nothing to pay...' TO
        // COB(201):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("You have nothing to pay...");
        // COB(203): MOVE -1       TO ACTIDINL OF COBIL0AI
        ws.cobil00.cobil0ai.actidinl.setValue(-1);
        // COB(204): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(205): END-IF
      }
      // COB(206): END-IF
    }
    // COB(208): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(210): IF CONF-PAY-YES
      if (ws.wsVariables.confPayYes()) {
        // COB(211): PERFORM READ-CXACAIX-FILE
        readCxacaixFile();
        // COB(212): MOVE HIGH-VALUES TO TRAN-ID
        ws.cvtra05y.tranRecord.tranId.highValues();
        // COB(213): PERFORM STARTBR-TRANSACT-FILE
        startbrTransactFile();
        // COB(214): PERFORM READPREV-TRANSACT-FILE
        readprevTransactFile();
        // COB(215): PERFORM ENDBR-TRANSACT-FILE
        endbrTransactFile();
        // COB(216): MOVE TRAN-ID     TO WS-TRAN-ID-NUM
        ws.wsVariables.wsTranIdNum.setValue(ws.cvtra05y.tranRecord.tranId);
        // COB(217): ADD 1 TO WS-TRAN-ID-NUM
        ws.wsVariables.wsTranIdNum.add(1);
        // COB(218): INITIALIZE TRAN-RECORD
        ws.cvtra05y.tranRecord.initialize();
        // COB(219): MOVE WS-TRAN-ID-NUM       TO TRAN-ID
        ws.cvtra05y.tranRecord.tranId.setValue(ws.wsVariables.wsTranIdNum);
        // COB(220): MOVE '02'                 TO TRAN-TYPE-CD
        ws.cvtra05y.tranRecord.tranTypeCd.setString("02");
        // COB(221): MOVE 2                    TO TRAN-CAT-CD
        ws.cvtra05y.tranRecord.tranCatCd.setValue(2);
        // COB(222): MOVE 'POS TERM'           TO TRAN-SOURCE
        ws.cvtra05y.tranRecord.tranSource.setString("POS TERM");
        // COB(223): MOVE 'BILL PAYMENT - ONLINE' TO TRAN-DESC
        ws.cvtra05y.tranRecord.tranDesc.setString("BILL PAYMENT - ONLINE");
        // COB(224): MOVE ACCT-CURR-BAL        TO TRAN-AMT
        ws.cvtra05y.tranRecord.tranAmt.setValue(ws.cvact01y.accountRecord.acctCurrBal);
        // COB(225): MOVE XREF-CARD-NUM        TO TRAN-CARD-NUM
        ws.cvtra05y.tranRecord.tranCardNum.setValue(ws.cvact03y.cardXrefRecord.xrefCardNum);
        // COB(226): MOVE 999999999            TO TRAN-MERCHANT-ID
        ws.cvtra05y.tranRecord.tranMerchantId.setValue(999999999);
        // COB(227): MOVE 'BILL PAYMENT'       TO TRAN-MERCHANT-NAME
        ws.cvtra05y.tranRecord.tranMerchantName.setString("BILL PAYMENT");
        // COB(228): MOVE 'N/A'                TO TRAN-MERCHANT-CITY
        ws.cvtra05y.tranRecord.tranMerchantCity.setString("N/A");
        // COB(229): MOVE 'N/A'                TO TRAN-MERCHANT-ZIP
        ws.cvtra05y.tranRecord.tranMerchantZip.setString("N/A");
        // COB(230): PERFORM GET-CURRENT-TIMESTAMP
        getCurrentTimestamp();
        // COB(231): MOVE WS-TIMESTAMP         TO TRAN-ORIG-TS
        // COB(231):                              TRAN-PROC-TS
        ws.cvtra05y.tranRecord.tranOrigTs.setValue(ws.csdat01y.wsDateTime.wsTimestamp);
        ws.cvtra05y.tranRecord.tranProcTs.setValue(ws.csdat01y.wsDateTime.wsTimestamp);
        // COB(233): PERFORM WRITE-TRANSACT-FILE
        writeTransactFile();
        // COB(234): COMPUTE ACCT-CURR-BAL = ACCT-CURR-BAL - TRAN-AMT
        ws.cvact01y.accountRecord.acctCurrBal.setValue(
            ws.cvact01y
                .accountRecord
                .acctCurrBal
                .getValue()
                .subtract(ws.cvtra05y.tranRecord.tranAmt.getValue()));
        // COB(235): PERFORM UPDATE-ACCTDAT-FILE
        updateAcctdatFile();
        // COB(236): ELSE
      } else {
        // COB(237): MOVE 'Confirm to make a bill payment...' TO
        // COB(237):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Confirm to make a bill payment...");
        // COB(239): MOVE -1       TO CONFIRML OF COBIL0AI
        ws.cobil00.cobil0ai.confirml.setValue(-1);
        // COB(240): END-IF
      }
      // COB(242): PERFORM SEND-BILLPAY-SCREEN
      sendBillpayScreen();
      // COB(244): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      GET-CURRENT-TIMESTAMP
   *----------------------------------------------------------------*/
  protected void getCurrentTimestamp() {
    // COB(251): EXEC CICS ASKTIME
    // COB(251):   ABSTIME(WS-ABS-TIME)
    // COB(251): END-EXEC
    try {
      supernaut
          .askTime() //
          .abstime(ws.wsVariables.wsAbsTime) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    // COB(255): EXEC CICS FORMATTIME
    // COB(255):   ABSTIME(WS-ABS-TIME)
    // COB(255):   YYYYMMDD(WS-CUR-DATE-X10)
    // COB(255):   DATESEP('-')
    // COB(255):   TIME(WS-CUR-TIME-X08)
    // COB(255):   TIMESEP(':')
    // COB(255): END-EXEC
    try {
      supernaut
          .formatTime(ws.wsVariables.wsAbsTime) //
          .time(ws.wsVariables.wsCurTimeX08) //
          .yyyymmdd(ws.wsVariables.wsCurDateX10) //
          .datesep("-") //
          .timesep(":") //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    // COB(263): INITIALIZE WS-TIMESTAMP
    ws.csdat01y.wsDateTime.wsTimestamp.initialize();
    // COB(264): MOVE WS-CUR-DATE-X10 TO WS-TIMESTAMP(01:10)
    ws.csdat01y.wsDateTime.wsTimestamp.setValue(ws.wsVariables.wsCurDateX10, 0, 10);
    // COB(265): MOVE WS-CUR-TIME-X08 TO WS-TIMESTAMP(12:08)
    ws.csdat01y.wsDateTime.wsTimestamp.setValue(ws.wsVariables.wsCurTimeX08, 0, 11, 8);
    // COB(266): MOVE ZEROS           TO WS-TIMESTAMP-TM-MS6
    ws.csdat01y.wsDateTime.wsTimestamp.wsTimestampTmMs6.zeros();
  }

  /***
   *
   *----------------------------------------------------------------*
   *                      RETURN-TO-PREV-SCREEN
   *----------------------------------------------------------------*/
  protected void returnToPrevScreen() {
    // COB(275): IF CDEMO-TO-PROGRAM = LOW-VALUES OR SPACES
    if (ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isLowValues()
        || ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isSpaces()) {
      // COB(276): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(277): END-IF
    }
    // COB(278): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsVariables.wsTranid);
    // COB(279): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsVariables.wsPgmname);
    // COB(280): MOVE ZEROS        TO CDEMO-PGM-CONTEXT
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.zeros();
    // COB(281): EXEC CICS
    // COB(281):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
    // COB(281):     COMMAREA(CARDDEMO-COMMAREA)
    // COB(281): END-EXEC.
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
   *                      SEND-BILLPAY-SCREEN
   *----------------------------------------------------------------*/
  protected void sendBillpayScreen() {
    // COB(291): PERFORM POPULATE-HEADER-INFO
    populateHeaderInfo();
    // COB(293): MOVE WS-MESSAGE TO ERRMSGO OF COBIL0AO
    ws.cobil00.cobil0ao.errmsgo.setValue(ws.wsVariables.wsMessage);
    // COB(295): EXEC CICS SEND
    // COB(295):           MAP('COBIL0A')
    // COB(295):           MAPSET('COBIL00')
    // COB(295):           FROM(COBIL0AO)
    // COB(295):           ERASE
    // COB(295):           CURSOR
    // COB(295): END-EXEC.
    try {
      supernaut
          .sendMap("COBIL0A") //
          .mapset("COBIL00") //
          .from(ws.cobil00.cobil0ao) //
          .erase() //
          .cursor() //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RECEIVE-BILLPAY-SCREEN
   *----------------------------------------------------------------*/
  protected void receiveBillpayScreen() {
    // COB(308): EXEC CICS RECEIVE
    // COB(308):           MAP('COBIL0A')
    // COB(308):           MAPSET('COBIL00')
    // COB(308):           INTO(COBIL0AI)
    // COB(308):           RESP(WS-RESP-CD)
    // COB(308):           RESP2(WS-REAS-CD)
    // COB(308): END-EXEC.
    supernaut
        .receiveMap("COBIL0A") //
        .mapset("COBIL00") //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cobil00.cobil0ai) //
        .exec();
  }

  /***
   *----------------------------------------------------------------*
   *                      POPULATE-HEADER-INFO
   *----------------------------------------------------------------*/
  protected void populateHeaderInfo() {
    // COB(321): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(323): MOVE CCDA-TITLE01           TO TITLE01O OF COBIL0AO
    ws.cobil00.cobil0ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(324): MOVE CCDA-TITLE02           TO TITLE02O OF COBIL0AO
    ws.cobil00.cobil0ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(325): MOVE WS-TRANID              TO TRNNAMEO OF COBIL0AO
    ws.cobil00.cobil0ao.trnnameo.setValue(ws.wsVariables.wsTranid);
    // COB(326): MOVE WS-PGMNAME             TO PGMNAMEO OF COBIL0AO
    ws.cobil00.cobil0ao.pgmnameo.setValue(ws.wsVariables.wsPgmname);
    // COB(328): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(329): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(330): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(332): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF COBIL0AO
    ws.cobil00.cobil0ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(334): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(335): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(336): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(338): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF COBIL0AO.
    ws.cobil00.cobil0ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***
   *----------------------------------------------------------------*
   *                      READ-ACCTDAT-FILE
   *----------------------------------------------------------------*/
  protected void readAcctdatFile() {
    // COB(345): EXEC CICS READ
    // COB(345):      DATASET   (WS-ACCTDAT-FILE)
    // COB(345):      INTO      (ACCOUNT-RECORD)
    // COB(345):      LENGTH    (LENGTH OF ACCOUNT-RECORD)
    // COB(345):      RIDFLD    (ACCT-ID)
    // COB(345):      KEYLENGTH (LENGTH OF ACCT-ID)
    // COB(345):      UPDATE
    // COB(345):      RESP      (WS-RESP-CD)
    // COB(345):      RESP2     (WS-REAS-CD)
    // COB(345): END-EXEC
    supernaut
        .read(ws.wsVariables.wsAcctdatFile.getValue()) //
        .update() //
        .length(ws.cvact01y.accountRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cvact01y.accountRecord) //
        .ridfld(ws.cvact01y.accountRecord.acctId) //
        .keylength(ws.cvact01y.accountRecord.acctId.length()) //
        .exec();
    // COB(356): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(357): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(358): CONTINUE
        // do nothing
        // COB(359): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(360): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(361): MOVE 'Account ID NOT found...' TO
        // COB(361):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Account ID NOT found...");
        // COB(363): MOVE -1       TO ACTIDINL OF COBIL0AI
        ws.cobil00.cobil0ai.actidinl.setValue(-1);
        // COB(364): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(365): WHEN OTHER
        break;
      default:
        // COB(366): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(367): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(368): MOVE 'Unable to lookup Account...' TO
        // COB(368):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup Account...");
        // COB(370): MOVE -1       TO ACTIDINL OF COBIL0AI
        ws.cobil00.cobil0ai.actidinl.setValue(-1);
        // COB(371): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(372): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      UPDATE-ACCTDAT-FILE
   *----------------------------------------------------------------*/
  protected void updateAcctdatFile() {
    // COB(379): EXEC CICS REWRITE
    // COB(379):      DATASET   (WS-ACCTDAT-FILE)
    // COB(379):      FROM      (ACCOUNT-RECORD)
    // COB(379):      LENGTH    (LENGTH OF ACCOUNT-RECORD)
    // COB(379):      RESP      (WS-RESP-CD)
    // COB(379):      RESP2     (WS-REAS-CD)
    // COB(379): END-EXEC
    supernaut
        .rewrite(ws.wsVariables.wsAcctdatFile.getValue()) //
        .length(ws.cvact01y.accountRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .from(ws.cvact01y.accountRecord) //
        .exec();
    // COB(387): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(388): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(389): CONTINUE
        // do nothing
        // COB(390): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(391): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(392): MOVE 'Account ID NOT found...' TO
        // COB(392):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Account ID NOT found...");
        // COB(394): MOVE -1       TO ACTIDINL OF COBIL0AI
        ws.cobil00.cobil0ai.actidinl.setValue(-1);
        // COB(395): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(396): WHEN OTHER
        break;
      default:
        // COB(397): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(398): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(399): MOVE 'Unable to Update Account...' TO
        // COB(399):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to Update Account...");
        // COB(401): MOVE -1       TO ACTIDINL OF COBIL0AI
        ws.cobil00.cobil0ai.actidinl.setValue(-1);
        // COB(402): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(403): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      READ-CXACAIX-FILE
   *----------------------------------------------------------------*/
  protected void readCxacaixFile() {
    // COB(410): EXEC CICS READ
    // COB(410):      DATASET   (WS-CXACAIX-FILE)
    // COB(410):      INTO      (CARD-XREF-RECORD)
    // COB(410):      LENGTH    (LENGTH OF CARD-XREF-RECORD)
    // COB(410):      RIDFLD    (XREF-ACCT-ID)
    // COB(410):      KEYLENGTH (LENGTH OF XREF-ACCT-ID)
    // COB(410):      RESP      (WS-RESP-CD)
    // COB(410):      RESP2     (WS-REAS-CD)
    // COB(410): END-EXEC
    supernaut
        .read(ws.wsVariables.wsCxacaixFile.getValue()) //
        .length(ws.cvact03y.cardXrefRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cvact03y.cardXrefRecord) //
        .ridfld(ws.cvact03y.cardXrefRecord.xrefAcctId) //
        .keylength(ws.cvact03y.cardXrefRecord.xrefAcctId.length()) //
        .exec();
    // COB(420): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(421): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(422): CONTINUE
        // do nothing
        // COB(423): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(424): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(425): MOVE 'Account ID NOT found...' TO
        // COB(425):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Account ID NOT found...");
        // COB(427): MOVE -1       TO ACTIDINL OF COBIL0AI
        ws.cobil00.cobil0ai.actidinl.setValue(-1);
        // COB(428): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(429): WHEN OTHER
        break;
      default:
        // COB(430): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(431): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(432): MOVE 'Unable to lookup XREF AIX file...' TO
        // COB(432):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup XREF AIX file...");
        // COB(434): MOVE -1       TO ACTIDINL OF COBIL0AI
        ws.cobil00.cobil0ai.actidinl.setValue(-1);
        // COB(435): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(436): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      STARTBR-TRANSACT-FILE
   *----------------------------------------------------------------*/
  protected void startbrTransactFile() {
    // COB(443): EXEC CICS STARTBR
    // COB(443):      DATASET   (WS-TRANSACT-FILE)
    // COB(443):      RIDFLD    (TRAN-ID)
    // COB(443):      KEYLENGTH (LENGTH OF TRAN-ID)
    // COB(443):      RESP      (WS-RESP-CD)
    // COB(443):      RESP2     (WS-REAS-CD)
    // COB(443): END-EXEC
    supernaut
        .startbr(ws.wsVariables.wsTransactFile.getValue()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .ridfld(ws.cvtra05y.tranRecord.tranId) //
        .keylength(ws.cvtra05y.tranRecord.tranId.length()) //
        .exec();
    // COB(451): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(452): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(453): CONTINUE
        // do nothing
        // COB(454): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(455): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(456): MOVE 'Transaction ID NOT found...' TO
        // COB(456):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Transaction ID NOT found...");
        // COB(458): MOVE -1       TO ACTIDINL OF COBIL0AI
        ws.cobil00.cobil0ai.actidinl.setValue(-1);
        // COB(459): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(460): WHEN OTHER
        break;
      default:
        // COB(461): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(462): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(463): MOVE 'Unable to lookup Transaction...' TO
        // COB(463):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup Transaction...");
        // COB(465): MOVE -1       TO ACTIDINL OF COBIL0AI
        ws.cobil00.cobil0ai.actidinl.setValue(-1);
        // COB(466): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(467): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      READPREV-TRANSACT-FILE
   *----------------------------------------------------------------*/
  protected void readprevTransactFile() {
    // COB(474): EXEC CICS READPREV
    // COB(474):      DATASET   (WS-TRANSACT-FILE)
    // COB(474):      INTO      (TRAN-RECORD)
    // COB(474):      LENGTH    (LENGTH OF TRAN-RECORD)
    // COB(474):      RIDFLD    (TRAN-ID)
    // COB(474):      KEYLENGTH (LENGTH OF TRAN-ID)
    // COB(474):      RESP      (WS-RESP-CD)
    // COB(474):      RESP2     (WS-REAS-CD)
    // COB(474): END-EXEC
    supernaut
        .readPrev(ws.wsVariables.wsTransactFile.getValue()) //
        .length(ws.cvtra05y.tranRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cvtra05y.tranRecord) //
        .ridfld(ws.cvtra05y.tranRecord.tranId) //
        .keylength(ws.cvtra05y.tranRecord.tranId.length()) //
        .exec();
    // COB(484): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(485): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(486): CONTINUE
        // do nothing
        // COB(487): WHEN DFHRESP(ENDFILE)
        break;
      case Dfhresp.ENDFILE:
        // COB(488): MOVE ZEROS TO TRAN-ID
        ws.cvtra05y.tranRecord.tranId.zeros();
        // COB(489): WHEN OTHER
        break;
      default:
        // COB(490): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(491): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(492): MOVE 'Unable to lookup Transaction...' TO
        // COB(492):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup Transaction...");
        // COB(494): MOVE -1       TO ACTIDINL OF COBIL0AI
        ws.cobil00.cobil0ai.actidinl.setValue(-1);
        // COB(495): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(496): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      ENDBR-TRANSACT-FILE
   *----------------------------------------------------------------*/
  protected void endbrTransactFile() {
    // COB(503): EXEC CICS ENDBR
    // COB(503):      DATASET   (WS-TRANSACT-FILE)
    // COB(503): END-EXEC.
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
   *                      WRITE-TRANSACT-FILE
   *----------------------------------------------------------------*/
  protected void writeTransactFile() {
    // COB(512): EXEC CICS WRITE
    // COB(512):      DATASET   (WS-TRANSACT-FILE)
    // COB(512):      FROM      (TRAN-RECORD)
    // COB(512):      LENGTH    (LENGTH OF TRAN-RECORD)
    // COB(512):      RIDFLD    (TRAN-ID)
    // COB(512):      KEYLENGTH (LENGTH OF TRAN-ID)
    // COB(512):      RESP      (WS-RESP-CD)
    // COB(512):      RESP2     (WS-REAS-CD)
    // COB(512): END-EXEC
    supernaut
        .write(ws.wsVariables.wsTransactFile.getValue()) //
        .length(ws.cvtra05y.tranRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .from(ws.cvtra05y.tranRecord) //
        .ridfld(ws.cvtra05y.tranRecord.tranId) //
        .keylength(ws.cvtra05y.tranRecord.tranId.length()) //
        .exec();
    // COB(522): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(523): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(524): PERFORM INITIALIZE-ALL-FIELDS
        initializeAllFields();
        // COB(525): MOVE SPACES             TO WS-MESSAGE
        ws.wsVariables.wsMessage.spaces();
        // COB(526): MOVE DFHGREEN           TO ERRMSGC  OF COBIL0AO
        ws.cobil00.cobil0ao.errmsgc.setValue(Dfhbmsca.DFHGREEN.getValue());
        // COB(527): STRING 'Payment successful. '     DELIMITED BY SIZE
        // COB(527):   ' Your Transaction ID is ' DELIMITED BY SIZE
        // COB(527):        TRAN-ID  DELIMITED BY SPACE
        // COB(527):        '.' DELIMITED BY SIZE
        // COB(527):   INTO WS-MESSAGE
        ws.wsVariables.wsMessage.concat(
            "Payment successful. ",
            " Your Transaction ID is ",
            ws.cvtra05y.tranRecord.tranId.substringToValue(" "),
            ".");
        // COB(532): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(533): WHEN DFHRESP(DUPKEY)
        break;
      case Dfhresp.DUPKEY:
        // COB(534): WHEN DFHRESP(DUPREC)
      case Dfhresp.DUPREC:
        // COB(535): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(536): MOVE 'Tran ID already exist...' TO
        // COB(536):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Tran ID already exist...");
        // COB(538): MOVE -1       TO ACTIDINL OF COBIL0AI
        ws.cobil00.cobil0ai.actidinl.setValue(-1);
        // COB(539): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(540): WHEN OTHER
        break;
      default:
        // COB(541): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(542): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(543): MOVE 'Unable to Add Bill pay Transaction...' TO
        // COB(543):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to Add Bill pay Transaction...");
        // COB(545): MOVE -1       TO ACTIDINL OF COBIL0AI
        ws.cobil00.cobil0ai.actidinl.setValue(-1);
        // COB(546): PERFORM SEND-BILLPAY-SCREEN
        sendBillpayScreen();
        // COB(547): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      CLEAR-CURRENT-SCREEN
   *----------------------------------------------------------------*/
  protected void clearCurrentScreen() {
    // COB(554): PERFORM INITIALIZE-ALL-FIELDS
    initializeAllFields();
    // COB(555): PERFORM SEND-BILLPAY-SCREEN.
    sendBillpayScreen();
  }

  /***
   *----------------------------------------------------------------*
   *                      INITIALIZE-ALL-FIELDS
   *----------------------------------------------------------------*/
  protected void initializeAllFields() {
    // COB(562): MOVE -1              TO ACTIDINL OF COBIL0AI
    ws.cobil00.cobil0ai.actidinl.setValue(-1);
    // COB(563): MOVE SPACES          TO ACTIDINI OF COBIL0AI
    // COB(563):                         CURBALI  OF COBIL0AI
    // COB(563):                         CONFIRMI OF COBIL0AI
    // COB(563):                         WS-MESSAGE.
    ws.cobil00.cobil0ai.actidini.spaces();
    ws.cobil00.cobil0ai.curbali.spaces();
    ws.cobil00.cobil0ai.confirmi.spaces();
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
