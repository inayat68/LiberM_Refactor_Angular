package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.corpt00c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class CORPT00C extends AbstractOnlineProgram {

  protected enum Flow {
    Exit,
    // RETURN-TO-CICS
    returnToCics,
    // SEND-TRNRPT-SCREEN
    sendTrnrptScreen
  }

  private Flow rcNext;

  @ProgramStorage final Corpt00cWorking ws = new Corpt00cWorking();

  // Linkage
  public static class Corpt00cLinkage extends NGroup {
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

  final Corpt00cLinkage params = new Corpt00cLinkage();

  public CORPT00C(Context supernaut) {
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
    // COB(165): SET ERR-FLG-OFF TO TRUE
    ws.wsVariables.setErrFlgOff(true);
    // COB(166): SET TRANSACT-NOT-EOF TO TRUE
    ws.wsVariables.setTransactNotEof(true);
    // COB(167): SET SEND-ERASE-YES TO TRUE
    ws.wsVariables.setSendEraseYes(true);
    // COB(169): MOVE SPACES TO WS-MESSAGE
    // COB(169):                ERRMSGO OF CORPT0AO
    ws.wsVariables.wsMessage.spaces();
    ws.corpt00.corpt0ao.errmsgo.spaces();
    // COB(172): IF EIBCALEN = 0
    if (dfheiblk.getEibcalen() == 0) {
      // COB(173): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(174): PERFORM RETURN-TO-PREV-SCREEN
      returnToPrevScreen();
      // COB(175): ELSE
    } else {
      // COB(176): MOVE DFHCOMMAREA(1:EIBCALEN) TO CARDDEMO-COMMAREA
      ws.cocom01y.carddemoCommarea.setValue(params.dfhcommarea, 0, dfheiblk.getEibcalen());
      // COB(177): IF NOT CDEMO-PGM-REENTER
      if (!ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
        // COB(178): SET CDEMO-PGM-REENTER    TO TRUE
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
        // COB(179): MOVE LOW-VALUES          TO CORPT0AO
        ws.corpt00.corpt0ao.lowValues();
        // COB(180): MOVE -1       TO MONTHLYL OF CORPT0AI
        ws.corpt00.corpt0ai.monthlyl.setValue(-1);
        // COB(181): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(182): ELSE
      } else {
        // COB(183): PERFORM RECEIVE-TRNRPT-SCREEN
        receiveTrnrptScreen();
        // COB(184): EVALUATE EIBAID
        switch (dfheiblk.getEibaid()) {
            // COB(185): WHEN DFHENTER
          case DFHENTER:
            // COB(186): PERFORM PROCESS-ENTER-KEY
            processEnterKey();
            // COB(187): WHEN DFHPF3
            break;
          case DFHPF3:
            // COB(188): MOVE 'COMEN01C' TO CDEMO-TO-PROGRAM
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COMEN01C");
            // COB(189): PERFORM RETURN-TO-PREV-SCREEN
            returnToPrevScreen();
            // COB(190): WHEN OTHER
            break;
          default:
            // COB(191): MOVE 'Y'                       TO WS-ERR-FLG
            ws.wsVariables.wsErrFlg.setString("Y");
            // COB(192): MOVE -1       TO MONTHLYL OF CORPT0AI
            ws.corpt00.corpt0ai.monthlyl.setValue(-1);
            // COB(193): MOVE CCDA-MSG-INVALID-KEY      TO WS-MESSAGE
            ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgInvalidKey);
            // COB(194): PERFORM SEND-TRNRPT-SCREEN
            rcNext = Flow.sendTrnrptScreen;
            while (!rcNext.equals(Flow.Exit)) {
              switch (rcNext) {
                case sendTrnrptScreen:
                  rcNext = sendTrnrptScreen();
                  break;
                case returnToCics:
                  returnToCics();
                  rcNext = Flow.Exit;
                  break;
                default:
                  throw new RuntimeException("Invalid flow option: " + rcNext);
              }
            }
            // COB(195): END-EVALUATE
        }
        // COB(196): END-IF
      }
      // COB(197): END-IF
    }
    // COB(199): EXEC CICS RETURN
    // COB(199):           TRANSID (WS-TRANID)
    // COB(199):           COMMAREA (CARDDEMO-COMMAREA)
    // COB(199): END-EXEC.
    try {
      supernaut
          .returnTransid(ws.wsVariables.wsTranid) //
          .commarea(ws.cocom01y.carddemoCommarea) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *
   *----------------------------------------------------------------*
   *                      PROCESS-ENTER-KEY
   *----------------------------------------------------------------*/
  protected void processEnterKey() {
    // COB(210): DISPLAY 'PROCESS ENTER KEY'
    sysout.display("PROCESS ENTER KEY");
    // COB(212): EVALUATE TRUE
    // COB(213): WHEN MONTHLYI OF CORPT0AI NOT = SPACES AND LOW-VALUES
    if (!ws.corpt00.corpt0ai.monthlyi.isSpaces() && !ws.corpt00.corpt0ai.monthlyi.isLowValues()) {
      // COB(214): MOVE 'Monthly'   TO WS-REPORT-NAME
      ws.wsVariables.wsReportName.setString("Monthly");
      // COB(215): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
      ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
      // COB(217): MOVE WS-CURDATE-YEAR     TO WS-START-DATE-YYYY
      ws.wsVariables.wsStartDate.wsStartDateYyyy.setValue(
          ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear);
      // COB(218): MOVE WS-CURDATE-MONTH    TO WS-START-DATE-MM
      ws.wsVariables.wsStartDate.wsStartDateMm.setValue(
          ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
      // COB(219): MOVE '01'                TO WS-START-DATE-DD
      ws.wsVariables.wsStartDate.wsStartDateDd.setString("01");
      // COB(220): MOVE WS-START-DATE       TO PARM-START-DATE-1
      // COB(220):                             PARM-START-DATE-2
      ws.jobData.jobData1.filler1.parmStartDate1.setValue(ws.wsVariables.wsStartDate);
      ws.jobData.jobData1.filler3.parmStartDate2.setValue(ws.wsVariables.wsStartDate);
      // COB(223): MOVE 1              TO WS-CURDATE-DAY
      ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay.setValue(1);
      // COB(224): ADD 1               TO WS-CURDATE-MONTH
      ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth.add(1);
      // COB(225): IF WS-CURDATE-MONTH > 12
      if (ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth.greaterThan(12)) {
        // COB(226): ADD 1           TO WS-CURDATE-YEAR
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear.add(1);
        // COB(227): MOVE 1          TO WS-CURDATE-MONTH
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth.setValue(1);
        // COB(228): END-IF
      }
      // COB(229): COMPUTE WS-CURDATE-N = FUNCTION DATE-OF-INTEGER(
      // COB(229):         FUNCTION INTEGER-OF-DATE(WS-CURDATE-N) - 1)
      ws.csdat01y.wsDateTime.wsCurdateData.wsCurdateN.setValue(
          AbstractNField.dateOfInteger(
              AbstractNField.integerOfDate(ws.csdat01y.wsDateTime.wsCurdateData.wsCurdateN.getInt())
                  - 1));
      // COB(232): MOVE WS-CURDATE-YEAR     TO WS-END-DATE-YYYY
      ws.wsVariables.wsEndDate.wsEndDateYyyy.setValue(
          ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear);
      // COB(233): MOVE WS-CURDATE-MONTH    TO WS-END-DATE-MM
      ws.wsVariables.wsEndDate.wsEndDateMm.setValue(
          ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
      // COB(234): MOVE WS-CURDATE-DAY      TO WS-END-DATE-DD
      ws.wsVariables.wsEndDate.wsEndDateDd.setValue(
          ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
      // COB(235): MOVE WS-END-DATE         TO PARM-END-DATE-1
      // COB(235):                             PARM-END-DATE-2
      ws.jobData.jobData1.filler2.parmEndDate1.setValue(ws.wsVariables.wsEndDate);
      ws.jobData.jobData1.filler3.parmEndDate2.setValue(ws.wsVariables.wsEndDate);
      // COB(238): PERFORM SUBMIT-JOB-TO-INTRDR
      submitJobToIntrdr();
      // COB(239): WHEN YEARLYI OF CORPT0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.corpt00.corpt0ai.yearlyi.isSpaces()
        && !ws.corpt00.corpt0ai.yearlyi.isLowValues()) {
      // COB(240): MOVE 'Yearly'   TO WS-REPORT-NAME
      ws.wsVariables.wsReportName.setString("Yearly");
      // COB(241): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
      ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
      // COB(243): MOVE WS-CURDATE-YEAR     TO WS-START-DATE-YYYY
      // COB(243):                             WS-END-DATE-YYYY
      ws.wsVariables.wsStartDate.wsStartDateYyyy.setValue(
          ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear);
      ws.wsVariables.wsEndDate.wsEndDateYyyy.setValue(
          ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear);
      // COB(245): MOVE '01'                TO WS-START-DATE-MM
      // COB(245):                             WS-START-DATE-DD
      ws.wsVariables.wsStartDate.wsStartDateMm.setString("01");
      ws.wsVariables.wsStartDate.wsStartDateDd.setString("01");
      // COB(247): MOVE WS-START-DATE       TO PARM-START-DATE-1
      // COB(247):                             PARM-START-DATE-2
      ws.jobData.jobData1.filler1.parmStartDate1.setValue(ws.wsVariables.wsStartDate);
      ws.jobData.jobData1.filler3.parmStartDate2.setValue(ws.wsVariables.wsStartDate);
      // COB(250): MOVE '12'                TO WS-END-DATE-MM
      ws.wsVariables.wsEndDate.wsEndDateMm.setString("12");
      // COB(251): MOVE '31'                TO WS-END-DATE-DD
      ws.wsVariables.wsEndDate.wsEndDateDd.setString("31");
      // COB(252): MOVE WS-END-DATE         TO PARM-END-DATE-1
      // COB(252):                             PARM-END-DATE-2
      ws.jobData.jobData1.filler2.parmEndDate1.setValue(ws.wsVariables.wsEndDate);
      ws.jobData.jobData1.filler3.parmEndDate2.setValue(ws.wsVariables.wsEndDate);
      // COB(255): PERFORM SUBMIT-JOB-TO-INTRDR
      submitJobToIntrdr();
      // COB(256): WHEN CUSTOMI OF CORPT0AI NOT = SPACES AND LOW-VALUES
    } else if (!ws.corpt00.corpt0ai.customi.isSpaces()
        && !ws.corpt00.corpt0ai.customi.isLowValues()) {
      // COB(258): EVALUATE TRUE
      // COB(259): WHEN SDTMMI OF CORPT0AI = SPACES OR
      // COB(259):                             LOW-VALUES
      if (ws.corpt00.corpt0ai.sdtmmi.isSpaces() || ws.corpt00.corpt0ai.sdtmmi.isLowValues()) {
        // COB(261): MOVE 'Start Date - Month can NOT be empty...'
        // COB(261):   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Start Date - Month can NOT be empty...");
        // COB(263): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(264): MOVE -1       TO SDTMML OF CORPT0AI
        ws.corpt00.corpt0ai.sdtmml.setValue(-1);
        // COB(265): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(266): WHEN SDTDDI OF CORPT0AI = SPACES OR
        // COB(266):                             LOW-VALUES
      } else if (ws.corpt00.corpt0ai.sdtddi.isSpaces()
          || ws.corpt00.corpt0ai.sdtddi.isLowValues()) {
        // COB(268): MOVE 'Start Date - Day can NOT be empty...'
        // COB(268):   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Start Date - Day can NOT be empty...");
        // COB(270): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(271): MOVE -1       TO SDTDDL OF CORPT0AI
        ws.corpt00.corpt0ai.sdtddl.setValue(-1);
        // COB(272): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(273): WHEN SDTYYYYI OF CORPT0AI = SPACES OR
        // COB(273):                             LOW-VALUES
      } else if (ws.corpt00.corpt0ai.sdtyyyyi.isSpaces()
          || ws.corpt00.corpt0ai.sdtyyyyi.isLowValues()) {
        // COB(275): MOVE 'Start Date - Year can NOT be empty...'
        // COB(275):   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Start Date - Year can NOT be empty...");
        // COB(277): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(278): MOVE -1       TO SDTYYYYL OF CORPT0AI
        ws.corpt00.corpt0ai.sdtyyyyl.setValue(-1);
        // COB(279): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(280): WHEN EDTMMI OF CORPT0AI = SPACES OR
        // COB(280):                             LOW-VALUES
      } else if (ws.corpt00.corpt0ai.edtmmi.isSpaces()
          || ws.corpt00.corpt0ai.edtmmi.isLowValues()) {
        // COB(282): MOVE 'End Date - Month can NOT be empty...'
        // COB(282):   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("End Date - Month can NOT be empty...");
        // COB(284): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(285): MOVE -1       TO EDTMML OF CORPT0AI
        ws.corpt00.corpt0ai.edtmml.setValue(-1);
        // COB(286): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(287): WHEN EDTDDI OF CORPT0AI = SPACES OR
        // COB(287):                             LOW-VALUES
      } else if (ws.corpt00.corpt0ai.edtddi.isSpaces()
          || ws.corpt00.corpt0ai.edtddi.isLowValues()) {
        // COB(289): MOVE 'End Date - Day can NOT be empty...'
        // COB(289):   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("End Date - Day can NOT be empty...");
        // COB(291): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(292): MOVE -1       TO EDTDDL OF CORPT0AI
        ws.corpt00.corpt0ai.edtddl.setValue(-1);
        // COB(293): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(294): WHEN EDTYYYYI OF CORPT0AI = SPACES OR
        // COB(294):                             LOW-VALUES
      } else if (ws.corpt00.corpt0ai.edtyyyyi.isSpaces()
          || ws.corpt00.corpt0ai.edtyyyyi.isLowValues()) {
        // COB(296): MOVE 'End Date - Year can NOT be empty...'
        // COB(296):   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("End Date - Year can NOT be empty...");
        // COB(298): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(299): MOVE -1       TO EDTYYYYL OF CORPT0AI
        ws.corpt00.corpt0ai.edtyyyyl.setValue(-1);
        // COB(300): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(301): WHEN OTHER
      } else {
        // COB(302): CONTINUE
        // do nothing
        // COB(303): END-EVALUATE
      }
      // COB(305): COMPUTE WS-NUM-99 = FUNCTION NUMVAL-C
      // COB(305):                       (SDTMMI OF CORPT0AI)
      ws.wsVariables.wsNum99.setValue(ws.corpt00.corpt0ai.sdtmmi.convertCurrencyToNumeric());
      // COB(307): MOVE WS-NUM-99      TO SDTMMI OF CORPT0AI
      ws.corpt00.corpt0ai.sdtmmi.setValue(ws.wsVariables.wsNum99);
      // COB(309): COMPUTE WS-NUM-99 = FUNCTION NUMVAL-C
      // COB(309):                       (SDTDDI OF CORPT0AI)
      ws.wsVariables.wsNum99.setValue(ws.corpt00.corpt0ai.sdtddi.convertCurrencyToNumeric());
      // COB(311): MOVE WS-NUM-99      TO SDTDDI OF CORPT0AI
      ws.corpt00.corpt0ai.sdtddi.setValue(ws.wsVariables.wsNum99);
      // COB(313): COMPUTE WS-NUM-9999 = FUNCTION NUMVAL-C
      // COB(313):                         (SDTYYYYI OF CORPT0AI)
      ws.wsVariables.wsNum9999.setValue(ws.corpt00.corpt0ai.sdtyyyyi.convertCurrencyToNumeric());
      // COB(315): MOVE WS-NUM-9999      TO SDTYYYYI OF CORPT0AI
      ws.corpt00.corpt0ai.sdtyyyyi.setValue(ws.wsVariables.wsNum9999);
      // COB(317): COMPUTE WS-NUM-99 = FUNCTION NUMVAL-C
      // COB(317):                       (EDTMMI OF CORPT0AI)
      ws.wsVariables.wsNum99.setValue(ws.corpt00.corpt0ai.edtmmi.convertCurrencyToNumeric());
      // COB(319): MOVE WS-NUM-99      TO EDTMMI OF CORPT0AI
      ws.corpt00.corpt0ai.edtmmi.setValue(ws.wsVariables.wsNum99);
      // COB(321): COMPUTE WS-NUM-99 = FUNCTION NUMVAL-C
      // COB(321):                       (EDTDDI OF CORPT0AI)
      ws.wsVariables.wsNum99.setValue(ws.corpt00.corpt0ai.edtddi.convertCurrencyToNumeric());
      // COB(323): MOVE WS-NUM-99      TO EDTDDI OF CORPT0AI
      ws.corpt00.corpt0ai.edtddi.setValue(ws.wsVariables.wsNum99);
      // COB(325): COMPUTE WS-NUM-9999 = FUNCTION NUMVAL-C
      // COB(325):                         (EDTYYYYI OF CORPT0AI)
      ws.wsVariables.wsNum9999.setValue(ws.corpt00.corpt0ai.edtyyyyi.convertCurrencyToNumeric());
      // COB(327): MOVE WS-NUM-9999      TO EDTYYYYI OF CORPT0AI
      ws.corpt00.corpt0ai.edtyyyyi.setValue(ws.wsVariables.wsNum9999);
      // COB(329): IF SDTMMI OF CORPT0AI IS NOT NUMERIC OR
      // COB(329):    SDTMMI OF CORPT0AI > '12'
      if (!ws.corpt00.corpt0ai.sdtmmi.isNumeric() || ws.corpt00.corpt0ai.sdtmmi.greaterThan("12")) {
        // COB(331): MOVE 'Start Date - Not a valid Month...'
        // COB(331):   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Start Date - Not a valid Month...");
        // COB(333): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(334): MOVE -1       TO SDTMML OF CORPT0AI
        ws.corpt00.corpt0ai.sdtmml.setValue(-1);
        // COB(335): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(336): END-IF
      }
      // COB(338): IF SDTDDI OF CORPT0AI IS NOT NUMERIC OR
      // COB(338):    SDTDDI OF CORPT0AI > '31'
      if (!ws.corpt00.corpt0ai.sdtddi.isNumeric() || ws.corpt00.corpt0ai.sdtddi.greaterThan("31")) {
        // COB(340): MOVE 'Start Date - Not a valid Day...'
        // COB(340):   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Start Date - Not a valid Day...");
        // COB(342): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(343): MOVE -1       TO SDTDDL OF CORPT0AI
        ws.corpt00.corpt0ai.sdtddl.setValue(-1);
        // COB(344): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(345): END-IF
      }
      // COB(347): IF SDTYYYYI OF CORPT0AI IS NOT NUMERIC
      if (!ws.corpt00.corpt0ai.sdtyyyyi.isNumeric()) {
        // COB(348): MOVE 'Start Date - Not a valid Year...'
        // COB(348):   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Start Date - Not a valid Year...");
        // COB(350): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(351): MOVE -1       TO SDTYYYYL OF CORPT0AI
        ws.corpt00.corpt0ai.sdtyyyyl.setValue(-1);
        // COB(352): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(353): END-IF
      }
      // COB(355): IF EDTMMI OF CORPT0AI IS NOT NUMERIC OR
      // COB(355):    EDTMMI OF CORPT0AI > '12'
      if (!ws.corpt00.corpt0ai.edtmmi.isNumeric() || ws.corpt00.corpt0ai.edtmmi.greaterThan("12")) {
        // COB(357): MOVE 'End Date - Not a valid Month...'
        // COB(357):   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("End Date - Not a valid Month...");
        // COB(359): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(360): MOVE -1       TO EDTMML OF CORPT0AI
        ws.corpt00.corpt0ai.edtmml.setValue(-1);
        // COB(361): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(362): END-IF
      }
      // COB(364): IF EDTDDI OF CORPT0AI IS NOT NUMERIC OR
      // COB(364):    EDTDDI OF CORPT0AI > '31'
      if (!ws.corpt00.corpt0ai.edtddi.isNumeric() || ws.corpt00.corpt0ai.edtddi.greaterThan("31")) {
        // COB(366): MOVE 'End Date - Not a valid Day...'
        // COB(366):   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("End Date - Not a valid Day...");
        // COB(368): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(369): MOVE -1       TO EDTDDL OF CORPT0AI
        ws.corpt00.corpt0ai.edtddl.setValue(-1);
        // COB(370): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(371): END-IF
      }
      // COB(373): IF EDTYYYYI OF CORPT0AI IS NOT NUMERIC
      if (!ws.corpt00.corpt0ai.edtyyyyi.isNumeric()) {
        // COB(374): MOVE 'End Date - Not a valid Year...'
        // COB(374):   TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("End Date - Not a valid Year...");
        // COB(376): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(377): MOVE -1       TO EDTYYYYL OF CORPT0AI
        ws.corpt00.corpt0ai.edtyyyyl.setValue(-1);
        // COB(378): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(379): END-IF
      }
      // COB(381): MOVE SDTYYYYI OF CORPT0AI TO WS-START-DATE-YYYY
      ws.wsVariables.wsStartDate.wsStartDateYyyy.setValue(ws.corpt00.corpt0ai.sdtyyyyi);
      // COB(382): MOVE SDTMMI   OF CORPT0AI TO WS-START-DATE-MM
      ws.wsVariables.wsStartDate.wsStartDateMm.setValue(ws.corpt00.corpt0ai.sdtmmi);
      // COB(383): MOVE SDTDDI   OF CORPT0AI TO WS-START-DATE-DD
      ws.wsVariables.wsStartDate.wsStartDateDd.setValue(ws.corpt00.corpt0ai.sdtddi);
      // COB(384): MOVE EDTYYYYI OF CORPT0AI TO WS-END-DATE-YYYY
      ws.wsVariables.wsEndDate.wsEndDateYyyy.setValue(ws.corpt00.corpt0ai.edtyyyyi);
      // COB(385): MOVE EDTMMI   OF CORPT0AI TO WS-END-DATE-MM
      ws.wsVariables.wsEndDate.wsEndDateMm.setValue(ws.corpt00.corpt0ai.edtmmi);
      // COB(386): MOVE EDTDDI   OF CORPT0AI TO WS-END-DATE-DD
      ws.wsVariables.wsEndDate.wsEndDateDd.setValue(ws.corpt00.corpt0ai.edtddi);
      // COB(388): MOVE WS-START-DATE        TO CSUTLDTC-DATE
      ws.csutldtcParm.csutldtcDate.setValue(ws.wsVariables.wsStartDate);
      // COB(389): MOVE WS-DATE-FORMAT       TO CSUTLDTC-DATE-FORMAT
      ws.csutldtcParm.csutldtcDateFormat.setValue(ws.wsVariables.wsDateFormat);
      // COB(390): MOVE SPACES               TO CSUTLDTC-RESULT
      ws.csutldtcParm.csutldtcResult.spaces();
      // COB(392): CALL 'CSUTLDTC' USING   CSUTLDTC-DATE
      // COB(392):                         CSUTLDTC-DATE-FORMAT
      // COB(392):                         CSUTLDTC-RESULT
      supernaut.call(
          "CSUTLDTC",
          ws.csutldtcParm.csutldtcDate,
          ws.csutldtcParm.csutldtcDateFormat,
          ws.csutldtcParm.csutldtcResult);
      // COB(396): IF CSUTLDTC-RESULT-SEV-CD = '0000'
      if (ws.csutldtcParm.csutldtcResult.csutldtcResultSevCd.equals("0000")) {
        // COB(397): CONTINUE
        // do nothing
        // COB(398): ELSE
      } else {
        // COB(399): IF CSUTLDTC-RESULT-MSG-NUM NOT = '2513'
        if (!ws.csutldtcParm.csutldtcResult.csutldtcResultMsgNum.equals("2513")) {
          // COB(400): MOVE 'Start Date - Not a valid date...'
          // COB(400):   TO WS-MESSAGE
          ws.wsVariables.wsMessage.setString("Start Date - Not a valid date...");
          // COB(402): MOVE 'Y'     TO WS-ERR-FLG
          ws.wsVariables.wsErrFlg.setString("Y");
          // COB(403): MOVE -1       TO SDTMML OF CORPT0AI
          ws.corpt00.corpt0ai.sdtmml.setValue(-1);
          // COB(404): PERFORM SEND-TRNRPT-SCREEN
          rcNext = Flow.sendTrnrptScreen;
          while (!rcNext.equals(Flow.Exit)) {
            switch (rcNext) {
              case sendTrnrptScreen:
                rcNext = sendTrnrptScreen();
                break;
              case returnToCics:
                returnToCics();
                rcNext = Flow.Exit;
                break;
              default:
                throw new RuntimeException("Invalid flow option: " + rcNext);
            }
          }
          // COB(405): END-IF
        }
        // COB(406): END-IF
      }
      // COB(408): MOVE WS-END-DATE          TO CSUTLDTC-DATE
      ws.csutldtcParm.csutldtcDate.setValue(ws.wsVariables.wsEndDate);
      // COB(409): MOVE WS-DATE-FORMAT       TO CSUTLDTC-DATE-FORMAT
      ws.csutldtcParm.csutldtcDateFormat.setValue(ws.wsVariables.wsDateFormat);
      // COB(410): MOVE SPACES               TO CSUTLDTC-RESULT
      ws.csutldtcParm.csutldtcResult.spaces();
      // COB(412): CALL 'CSUTLDTC' USING   CSUTLDTC-DATE
      // COB(412):                         CSUTLDTC-DATE-FORMAT
      // COB(412):                         CSUTLDTC-RESULT
      supernaut.call(
          "CSUTLDTC",
          ws.csutldtcParm.csutldtcDate,
          ws.csutldtcParm.csutldtcDateFormat,
          ws.csutldtcParm.csutldtcResult);
      // COB(416): IF CSUTLDTC-RESULT-SEV-CD = '0000'
      if (ws.csutldtcParm.csutldtcResult.csutldtcResultSevCd.equals("0000")) {
        // COB(417): CONTINUE
        // do nothing
        // COB(418): ELSE
      } else {
        // COB(419): IF CSUTLDTC-RESULT-MSG-NUM NOT = '2513'
        if (!ws.csutldtcParm.csutldtcResult.csutldtcResultMsgNum.equals("2513")) {
          // COB(420): MOVE 'End Date - Not a valid date...'
          // COB(420):   TO WS-MESSAGE
          ws.wsVariables.wsMessage.setString("End Date - Not a valid date...");
          // COB(422): MOVE 'Y'     TO WS-ERR-FLG
          ws.wsVariables.wsErrFlg.setString("Y");
          // COB(423): MOVE -1       TO EDTMML OF CORPT0AI
          ws.corpt00.corpt0ai.edtmml.setValue(-1);
          // COB(424): PERFORM SEND-TRNRPT-SCREEN
          rcNext = Flow.sendTrnrptScreen;
          while (!rcNext.equals(Flow.Exit)) {
            switch (rcNext) {
              case sendTrnrptScreen:
                rcNext = sendTrnrptScreen();
                break;
              case returnToCics:
                returnToCics();
                rcNext = Flow.Exit;
                break;
              default:
                throw new RuntimeException("Invalid flow option: " + rcNext);
            }
          }
          // COB(425): END-IF
        }
        // COB(426): END-IF
      }
      // COB(429): MOVE WS-START-DATE       TO PARM-START-DATE-1
      // COB(429):                             PARM-START-DATE-2
      //
      //
      ws.jobData.jobData1.filler1.parmStartDate1.setValue(ws.wsVariables.wsStartDate);
      ws.jobData.jobData1.filler3.parmStartDate2.setValue(ws.wsVariables.wsStartDate);
      // COB(431): MOVE WS-END-DATE         TO PARM-END-DATE-1
      // COB(431):                             PARM-END-DATE-2
      ws.jobData.jobData1.filler2.parmEndDate1.setValue(ws.wsVariables.wsEndDate);
      ws.jobData.jobData1.filler3.parmEndDate2.setValue(ws.wsVariables.wsEndDate);
      // COB(433): MOVE 'Custom'   TO WS-REPORT-NAME
      ws.wsVariables.wsReportName.setString("Custom");
      // COB(434): IF NOT ERR-FLG-ON
      if (!ws.wsVariables.errFlgOn()) {
        // COB(435): PERFORM SUBMIT-JOB-TO-INTRDR
        submitJobToIntrdr();
        // COB(436): END-IF
      }
      // COB(437): WHEN OTHER
    } else {
      // COB(438): MOVE 'Select a report type to print report...' TO
      // COB(438):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Select a report type to print report...");
      // COB(440): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(441): MOVE -1       TO MONTHLYL OF CORPT0AI
      ws.corpt00.corpt0ai.monthlyl.setValue(-1);
      // COB(442): PERFORM SEND-TRNRPT-SCREEN
      rcNext = Flow.sendTrnrptScreen;
      while (!rcNext.equals(Flow.Exit)) {
        switch (rcNext) {
          case sendTrnrptScreen:
            rcNext = sendTrnrptScreen();
            break;
          case returnToCics:
            returnToCics();
            rcNext = Flow.Exit;
            break;
          default:
            throw new RuntimeException("Invalid flow option: " + rcNext);
        }
      }
      // COB(443): END-EVALUATE
    }
    // COB(445): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(447): PERFORM INITIALIZE-ALL-FIELDS
      initializeAllFields();
      // COB(448): MOVE DFHGREEN           TO ERRMSGC  OF CORPT0AO
      ws.corpt00.corpt0ao.errmsgc.setValue(Dfhbmsca.DFHGREEN.getValue());
      // COB(449): STRING WS-REPORT-NAME   DELIMITED BY SPACE
      // COB(449):   ' report submitted for printing ...'
      // COB(449):                         DELIMITED BY SIZE
      // COB(449):   INTO WS-MESSAGE
      ws.wsVariables.wsMessage.concat(
          ws.wsVariables.wsReportName.substringToValue(" "), " report submitted for printing ...");
      // COB(453): MOVE -1       TO MONTHLYL OF CORPT0AI
      ws.corpt00.corpt0ai.monthlyl.setValue(-1);
      // COB(454): PERFORM SEND-TRNRPT-SCREEN
      rcNext = Flow.sendTrnrptScreen;
      while (!rcNext.equals(Flow.Exit)) {
        switch (rcNext) {
          case sendTrnrptScreen:
            rcNext = sendTrnrptScreen();
            break;
          case returnToCics:
            returnToCics();
            rcNext = Flow.Exit;
            break;
          default:
            throw new RuntimeException("Invalid flow option: " + rcNext);
        }
      }
      // COB(456): END-IF.
    }
  }

  /***
   *
   *----------------------------------------------------------------*
   *                      SUBMIT-JOB-TO-INTRDR
   *----------------------------------------------------------------*/
  protected void submitJobToIntrdr() {
    // COB(464): IF CONFIRMI OF CORPT0AI = SPACES OR LOW-VALUES
    if (ws.corpt00.corpt0ai.confirmi.isSpaces() || ws.corpt00.corpt0ai.confirmi.isLowValues()) {
      // COB(465): STRING
      // COB(465):   'Please confirm to print the '
      // COB(465):                     DELIMITED BY SIZE
      // COB(465):   WS-REPORT-NAME    DELIMITED BY SPACE
      // COB(465):   ' report...'      DELIMITED BY SIZE
      // COB(465):   INTO WS-MESSAGE
      ws.wsVariables.wsMessage.concat(
          "Please confirm to print the ",
          ws.wsVariables.wsReportName.substringToValue(" "),
          " report...");
      // COB(471): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(472): MOVE -1       TO CONFIRML OF CORPT0AI
      ws.corpt00.corpt0ai.confirml.setValue(-1);
      // COB(473): PERFORM SEND-TRNRPT-SCREEN
      rcNext = Flow.sendTrnrptScreen;
      while (!rcNext.equals(Flow.Exit)) {
        switch (rcNext) {
          case sendTrnrptScreen:
            rcNext = sendTrnrptScreen();
            break;
          case returnToCics:
            returnToCics();
            rcNext = Flow.Exit;
            break;
          default:
            throw new RuntimeException("Invalid flow option: " + rcNext);
        }
      }
      // COB(474): END-IF
    }
    // COB(476): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(477): EVALUATE TRUE
      // COB(478): WHEN CONFIRMI OF CORPT0AI = 'Y' OR 'y'
      if (ws.corpt00.corpt0ai.confirmi.equals("Y") || ws.corpt00.corpt0ai.confirmi.equals("y")) {
        // COB(479): CONTINUE
        // do nothing
        // COB(480): WHEN CONFIRMI OF CORPT0AI = 'N' OR 'n'
      } else if (ws.corpt00.corpt0ai.confirmi.equals("N")
          || ws.corpt00.corpt0ai.confirmi.equals("n")) {
        // COB(481): PERFORM INITIALIZE-ALL-FIELDS
        initializeAllFields();
        // COB(482): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(483): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(484): WHEN OTHER
      } else {
        // COB(485): STRING
        // COB(485):   '"'               DELIMITED BY SIZE
        // COB(485):   CONFIRMI OF CORPT0AI    DELIMITED BY SPACE
        // COB(485):   '" is not a valid value to confirm...'
        // COB(485):                     DELIMITED BY SIZE
        // COB(485):   INTO WS-MESSAGE
        ws.wsVariables.wsMessage.concat(
            "\"",
            ws.corpt00.corpt0ai.confirmi.substringToValue(" "),
            "\" is not a valid value to confirm...");
        // COB(491): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(492): MOVE -1       TO CONFIRML OF CORPT0AI
        ws.corpt00.corpt0ai.confirml.setValue(-1);
        // COB(493): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(494): END-EVALUATE
      }
      // COB(496): SET END-LOOP-NO TO TRUE
      ws.wsVariables.setEndLoopNo(true);
      // COB(498): PERFORM VARYING WS-IDX FROM 1 BY 1 UNTIL WS-IDX > 1000 OR
      // COB(498):                        END-LOOP-YES  OR ERR-FLG-ON
      ws.wsVariables.wsIdx.setValue(1);
      for (;
          !(ws.wsVariables.wsIdx.greaterThan(1000)
              || ws.wsVariables.endLoopYes()
              || ws.wsVariables.errFlgOn());
          ws.wsVariables.wsIdx.add(1)) {
        // COB(501): MOVE JOB-LINES(WS-IDX) TO JCL-RECORD
        ws.wsVariables.jclRecord.setValue(
            ws.jobData.jobData2.jobLinesAtIndex(ws.wsVariables.wsIdx.getInt() - 1));
        // COB(502): IF JCL-RECORD = '/*EOF' OR
        // COB(502):    JCL-RECORD = SPACES OR LOW-VALUES
        if (ws.wsVariables.jclRecord.equals("/*EOF")
            || ws.wsVariables.jclRecord.isSpaces()
            || ws.wsVariables.jclRecord.isLowValues()) {
          // COB(504): SET END-LOOP-YES TO TRUE
          ws.wsVariables.setEndLoopYes(true);
          // COB(505): END-IF
        }
        // COB(507): PERFORM WIRTE-JOBSUB-TDQ
        wirteJobsubTdq();
        // COB(508): END-PERFORM
      }
      // COB(510): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      WIRTE-JOBSUB-TDQ
   *----------------------------------------------------------------*/
  protected void wirteJobsubTdq() {
    // COB(517): EXEC CICS WRITEQ TD
    // COB(517):   QUEUE ('JOBS')
    // COB(517):   FROM (JCL-RECORD)
    // COB(517):   LENGTH (LENGTH OF JCL-RECORD)
    // COB(517):   RESP(WS-RESP-CD)
    // COB(517):   RESP2(WS-REAS-CD)
    // COB(517): END-EXEC.
    supernaut
        .writeQTd("JOBS") //
        .length(ws.wsVariables.jclRecord.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .from(ws.wsVariables.jclRecord) //
        .exec();
    // COB(525): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(526): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(527): CONTINUE
        // do nothing
        // COB(528): WHEN OTHER
        break;
      default:
        // COB(529): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(530): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(531): MOVE 'Unable to Write TDQ (JOBS)...' TO
        // COB(531):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to Write TDQ (JOBS)...");
        // COB(533): MOVE -1       TO MONTHLYL OF CORPT0AI
        ws.corpt00.corpt0ai.monthlyl.setValue(-1);
        // COB(534): PERFORM SEND-TRNRPT-SCREEN
        rcNext = Flow.sendTrnrptScreen;
        while (!rcNext.equals(Flow.Exit)) {
          switch (rcNext) {
            case sendTrnrptScreen:
              rcNext = sendTrnrptScreen();
              break;
            case returnToCics:
              returnToCics();
              rcNext = Flow.Exit;
              break;
            default:
              throw new RuntimeException("Invalid flow option: " + rcNext);
          }
        }
        // COB(535): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RETURN-TO-PREV-SCREEN
   *----------------------------------------------------------------*/
  protected void returnToPrevScreen() {
    // COB(542): IF CDEMO-TO-PROGRAM = LOW-VALUES OR SPACES
    if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isLowValues()
        || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isSpaces()) {
      // COB(543): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(544): END-IF
    }
    // COB(545): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
    ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsVariables.wsTranid);
    // COB(546): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
    ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
        ws.wsVariables.wsPgmname);
    // COB(547): MOVE ZEROS        TO CDEMO-PGM-CONTEXT
    ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.zeros();
    // COB(548): EXEC CICS
    // COB(548):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
    // COB(548):     COMMAREA(CARDDEMO-COMMAREA)
    // COB(548): END-EXEC.
    try {
      supernaut
          .xctl(ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
          .commarea(ws.cocom01y.carddemoCommarea) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      SEND-TRNRPT-SCREEN
   *----------------------------------------------------------------*/
  protected Flow sendTrnrptScreen() {
    // COB(558): PERFORM POPULATE-HEADER-INFO
    populateHeaderInfo();
    // COB(560): MOVE WS-MESSAGE TO ERRMSGO OF CORPT0AO
    ws.corpt00.corpt0ao.errmsgo.setValue(ws.wsVariables.wsMessage);
    // COB(562): IF SEND-ERASE-YES
    if (ws.wsVariables.sendEraseYes()) {
      // COB(563): EXEC CICS SEND
      // COB(563):           MAP('CORPT0A')
      // COB(563):           MAPSET('CORPT00')
      // COB(563):           FROM(CORPT0AO)
      // COB(563):           ERASE
      // COB(563):           CURSOR
      // COB(563): END-EXEC
      try {
        supernaut
            .sendMap("CORPT0A") //
            .mapset("CORPT00") //
            .from(ws.corpt00.corpt0ao) //
            .erase() //
            .cursor() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(570): ELSE
    } else {
      // COB(571): EXEC CICS SEND
      // COB(571):           MAP('CORPT0A')
      // COB(571):           MAPSET('CORPT00')
      // COB(571):           FROM(CORPT0AO)
      // COB(571):       *                  ERASE
      // COB(571):           CURSOR
      // COB(571): END-EXEC
      try {
        supernaut
            .sendMap("CORPT0A") //
            .mapset("CORPT00") //
            .from(ws.corpt00.corpt0ao) //
            .cursor() //
            .exec();
      } catch (HandledConditionException e) {
        handleCommandCondition(e);
      }
      // COB(578): END-IF.
    }
    // COB(580): GO TO RETURN-TO-CICS.
    return Flow.returnToCics;
  }

  /***
   *----------------------------------------------------------------*
   *                         RETURN-TO-CICS
   *----------------------------------------------------------------*/
  protected void returnToCics() {
    // COB(587): EXEC CICS RETURN
    // COB(587):           TRANSID (WS-TRANID)
    // COB(587):           COMMAREA (CARDDEMO-COMMAREA)
    // COB(587):       *              LENGTH(LENGTH OF CARDDEMO-COMMAREA)
    // COB(587): END-EXEC.
    try {
      supernaut
          .returnTransid(ws.wsVariables.wsTranid) //
          .commarea(ws.cocom01y.carddemoCommarea) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RECEIVE-TRNRPT-SCREEN
   *----------------------------------------------------------------*/
  protected void receiveTrnrptScreen() {
    // COB(598): EXEC CICS RECEIVE
    // COB(598):           MAP('CORPT0A')
    // COB(598):           MAPSET('CORPT00')
    // COB(598):           INTO(CORPT0AI)
    // COB(598):           RESP(WS-RESP-CD)
    // COB(598):           RESP2(WS-REAS-CD)
    // COB(598): END-EXEC.
    supernaut
        .receiveMap("CORPT0A") //
        .mapset("CORPT00") //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.corpt00.corpt0ai) //
        .exec();
  }

  /***
   *----------------------------------------------------------------*
   *                      POPULATE-HEADER-INFO
   *----------------------------------------------------------------*/
  protected void populateHeaderInfo() {
    // COB(611): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(613): MOVE CCDA-TITLE01           TO TITLE01O OF CORPT0AO
    ws.corpt00.corpt0ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(614): MOVE CCDA-TITLE02           TO TITLE02O OF CORPT0AO
    ws.corpt00.corpt0ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(615): MOVE WS-TRANID              TO TRNNAMEO OF CORPT0AO
    ws.corpt00.corpt0ao.trnnameo.setValue(ws.wsVariables.wsTranid);
    // COB(616): MOVE WS-PGMNAME             TO PGMNAMEO OF CORPT0AO
    ws.corpt00.corpt0ao.pgmnameo.setValue(ws.wsVariables.wsPgmname);
    // COB(618): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(619): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(620): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(622): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF CORPT0AO
    ws.corpt00.corpt0ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(624): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(625): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(626): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(628): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF CORPT0AO.
    ws.corpt00.corpt0ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***
   *----------------------------------------------------------------*
   *                      INITIALIZE-ALL-FIELDS
   *----------------------------------------------------------------*/
  protected void initializeAllFields() {
    // COB(635): MOVE -1              TO MONTHLYL OF CORPT0AI
    ws.corpt00.corpt0ai.monthlyl.setValue(-1);
    // COB(636): INITIALIZE              MONTHLYI OF CORPT0AI
    // COB(636):                         YEARLYI  OF CORPT0AI
    // COB(636):                         CUSTOMI  OF CORPT0AI
    // COB(636):                         SDTMMI   OF CORPT0AI
    // COB(636):                         SDTDDI   OF CORPT0AI
    // COB(636):                         SDTYYYYI OF CORPT0AI
    // COB(636):                         EDTMMI   OF CORPT0AI
    // COB(636):                         EDTDDI   OF CORPT0AI
    // COB(636):                         EDTYYYYI OF CORPT0AI
    // COB(636):                         CONFIRMI OF CORPT0AI
    // COB(636):                         WS-MESSAGE.
    ws.corpt00.corpt0ai.monthlyi.initialize();
    ws.corpt00.corpt0ai.yearlyi.initialize();
    ws.corpt00.corpt0ai.customi.initialize();
    ws.corpt00.corpt0ai.sdtmmi.initialize();
    ws.corpt00.corpt0ai.sdtddi.initialize();
    ws.corpt00.corpt0ai.sdtyyyyi.initialize();
    ws.corpt00.corpt0ai.edtmmi.initialize();
    ws.corpt00.corpt0ai.edtddi.initialize();
    ws.corpt00.corpt0ai.edtyyyyi.initialize();
    ws.corpt00.corpt0ai.confirmi.initialize();
    ws.wsVariables.wsMessage.initialize();
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
