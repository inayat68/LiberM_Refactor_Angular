package com.aws.carddemo.batch.program;

import com.aws.carddemo.batch.program.storage.cbtrn03c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.io.*;
import com.nib.commons.io.vsam.*;
import com.nib.commons.storage.*;

public class CBTRN03C extends AbstractProgram {
  private SequentialFile transactFile;
  private KeySequentialFile xrefFile;
  private KeySequentialFile trantypeFile;
  private KeySequentialFile trancatgFile;
  private SequentialFile reportFile;
  private SequentialFile dateParmsFile;
  final Cbtrn03cRecords fs = new Cbtrn03cRecords();

  @ProgramStorage final Cbtrn03cWorking ws = new Cbtrn03cWorking();

  // Linkage
  public static class Cbtrn03cLinkage extends NGroup {}

  final Cbtrn03cLinkage params = new Cbtrn03cLinkage();

  public CBTRN03C(Context context) {
    super(context);
  }

  @ProgramInit
  protected void initialize() {
    transactFile =
        new SequentialFile(context, "TRANFILE") //
            .fileStatusIs(ws.tranfileStatus) //
            .recordIs(fs.fdTranfileRec);
    xrefFile =
        new KeySequentialFile(context, "CARDXREF") //
            .recordKeyIs(fs.fdCardxrefRec.fdXrefCardNum) //
            .fileStatusIs(ws.cardxrefStatus) //
            .recordIs(fs.fdCardxrefRec);
    trantypeFile =
        new KeySequentialFile(context, "TRANTYPE") //
            .recordKeyIs(fs.fdTrantypeRec.fdTranType) //
            .fileStatusIs(ws.trantypeStatus) //
            .recordIs(fs.fdTrantypeRec);
    trancatgFile =
        new KeySequentialFile(context, "TRANCATG") //
            .recordKeyIs(fs.fdTranCatRecord.fdTranCatKey) //
            .fileStatusIs(ws.trancatgStatus) //
            .recordIs(fs.fdTranCatRecord);
    reportFile =
        new SequentialFile(context, "TRANREPT") //
            .fileStatusIs(ws.tranreptStatus) //
            .recordIs(fs.fdReptfileRec);
    dateParmsFile =
        new SequentialFile(context, "DATEPARM") //
            .fileStatusIs(ws.dateparmStatus) //
            .recordIs(fs.fdDateparmRec);
  }

  @Override
  protected int procedure(AbstractNField... args) throws ContextException {
    if (args != null) {
      params.allocate();
    }
    procedure();
    return RETURN_CODE;
  }

  /*****************************************************************/
  protected void procedure() {
    // COB(160): DISPLAY 'START OF EXECUTION OF PROGRAM CBTRN03C'.
    sysout.display("START OF EXECUTION OF PROGRAM CBTRN03C");
    // COB(161): PERFORM 0000-TRANFILE-OPEN.
    _0000TranfileOpen();
    // COB(162): PERFORM 0100-REPTFILE-OPEN.
    _0100ReptfileOpen();
    // COB(163): PERFORM 0200-CARDXREF-OPEN.
    _0200CardxrefOpen();
    // COB(164): PERFORM 0300-TRANTYPE-OPEN.
    _0300TrantypeOpen();
    // COB(165): PERFORM 0400-TRANCATG-OPEN.
    _0400TrancatgOpen();
    // COB(166): PERFORM 0500-DATEPARM-OPEN.
    _0500DateparmOpen();
    // COB(168): PERFORM 0550-DATEPARM-READ.
    _0550DateparmRead();
    nextSentence177:
    {
      // COB(170): PERFORM UNTIL END-OF-FILE = 'Y'
      while (!ws.endOfFile.equals("Y")) {
        // COB(171): IF END-OF-FILE = 'N'
        if (ws.endOfFile.equals("N")) {
          // COB(172): PERFORM 1000-TRANFILE-GET-NEXT
          _1000TranfileGetNext();
          // COB(173): IF TRAN-PROC-TS (1:10) >= WS-START-DATE
          // COB(173):    AND TRAN-PROC-TS (1:10) <= WS-END-DATE
          if (ws.cvtra05y
                  .tranRecord
                  .tranProcTs
                  .getString(0, 10)
                  .greaterEqualThan(ws.wsDateparmRecord.wsStartDate)
              && ws.cvtra05y
                  .tranRecord
                  .tranProcTs
                  .getString(0, 10)
                  .lessEqualThan(ws.wsDateparmRecord.wsEndDate)) {
            // COB(175): CONTINUE
            // do nothing
            // COB(176): ELSE
          } else {
            // COB(177): NEXT SENTENCE
            break nextSentence177;
            // COB(178): END-IF
          }
          // COB(179): IF END-OF-FILE = 'N'
          if (ws.endOfFile.equals("N")) {
            // COB(180): DISPLAY TRAN-RECORD
            sysout.display(ws.cvtra05y.tranRecord);
            // COB(181): IF WS-CURR-CARD-NUM NOT= TRAN-CARD-NUM
            if (!ws.wsReportVars.wsCurrCardNum.equals(ws.cvtra05y.tranRecord.tranCardNum)) {
              // COB(182): IF WS-FIRST-TIME = 'N'
              if (ws.wsReportVars.wsFirstTime.equals("N")) {
                // COB(183): PERFORM 1120-WRITE-ACCOUNT-TOTALS
                _1120WriteAccountTotals();
                // COB(184): END-IF
              }
              // COB(185): MOVE TRAN-CARD-NUM TO WS-CURR-CARD-NUM
              ws.wsReportVars.wsCurrCardNum.setValue(ws.cvtra05y.tranRecord.tranCardNum);
              // COB(186): MOVE TRAN-CARD-NUM TO FD-XREF-CARD-NUM
              fs.fdCardxrefRec.fdXrefCardNum.setValue(ws.cvtra05y.tranRecord.tranCardNum);
              // COB(187): PERFORM 1500-A-LOOKUP-XREF
              _1500ALookupXref();
              // COB(188): END-IF
            }
            // COB(189): MOVE TRAN-TYPE-CD OF TRAN-RECORD TO FD-TRAN-TYPE
            fs.fdTrantypeRec.fdTranType.setValue(ws.cvtra05y.tranRecord.tranTypeCd);
            // COB(190): PERFORM 1500-B-LOOKUP-TRANTYPE
            _1500BLookupTrantype();
            // COB(191): MOVE TRAN-TYPE-CD OF TRAN-RECORD
            // COB(191):   TO FD-TRAN-TYPE-CD OF FD-TRAN-CAT-KEY
            fs.fdTranCatRecord.fdTranCatKey.fdTranTypeCd.setValue(
                ws.cvtra05y.tranRecord.tranTypeCd);
            // COB(193): MOVE TRAN-CAT-CD OF TRAN-RECORD
            // COB(193):   TO FD-TRAN-CAT-CD OF FD-TRAN-CAT-KEY
            fs.fdTranCatRecord.fdTranCatKey.fdTranCatCd.setValue(ws.cvtra05y.tranRecord.tranCatCd);
            // COB(195): PERFORM 1500-C-LOOKUP-TRANCATG
            _1500CLookupTrancatg();
            // COB(196): PERFORM 1100-WRITE-TRANSACTION-REPORT
            _1100WriteTransactionReport();
            // COB(197): ELSE
          } else {
            // COB(198): DISPLAY 'TRAN-AMT ' TRAN-AMT
            sysout.display("TRAN-AMT ", ws.cvtra05y.tranRecord.tranAmt);
            // COB(199): DISPLAY 'WS-PAGE-TOTAL'  WS-PAGE-TOTAL
            sysout.display("WS-PAGE-TOTAL", ws.wsReportVars.wsPageTotal);
            // COB(200): ADD TRAN-AMT TO WS-PAGE-TOTAL
            // COB(200):                 WS-ACCOUNT-TOTAL
            ws.wsReportVars.wsPageTotal.add(ws.cvtra05y.tranRecord.tranAmt);
            ws.wsReportVars.wsAccountTotal.add(ws.cvtra05y.tranRecord.tranAmt);
            // COB(202): PERFORM 1110-WRITE-PAGE-TOTALS
            _1110WritePageTotals();
            // COB(203): PERFORM 1110-WRITE-GRAND-TOTALS
            _1110WriteGrandTotals();
            // COB(204): END-IF
          }
          // COB(205): END-IF
        }
        // COB(206): END-PERFORM.
      }
    } // nextSentence177
    // COB(208): PERFORM 9000-TRANFILE-CLOSE.
    _9000TranfileClose();
    // COB(209): PERFORM 9100-REPTFILE-CLOSE.
    _9100ReptfileClose();
    // COB(210): PERFORM 9200-CARDXREF-CLOSE.
    _9200CardxrefClose();
    // COB(211): PERFORM 9300-TRANTYPE-CLOSE.
    _9300TrantypeClose();
    // COB(212): PERFORM 9400-TRANCATG-CLOSE.
    _9400TrancatgClose();
    // COB(213): PERFORM 9500-DATEPARM-CLOSE.
    _9500DateparmClose();
    // COB(215): DISPLAY 'END OF EXECUTION OF PROGRAM CBTRN03C'.
    sysout.display("END OF EXECUTION OF PROGRAM CBTRN03C");
    // COB(217): GOBACK.
    context.goback();
  }

  /*** Read the date parameter file.*/
  protected void _0550DateparmRead() {
    // COB(221): READ DATE-PARMS-FILE INTO WS-DATEPARM-RECORD
    dateParmsFile.readNext(ws.wsDateparmRecord);
    // COB(222): EVALUATE DATEPARM-STATUS
    // COB(223): WHEN '00'
    if (ws.dateparmStatus.equals("00")) {
      // COB(224): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(225): WHEN '10'
    } else if (ws.dateparmStatus.equals("10")) {
      // COB(226): MOVE 16 TO APPL-RESULT
      ws.applResult.setValue(16);
      // COB(227): WHEN OTHER
    } else {
      // COB(228): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(229): END-EVALUATE
    }
    // COB(231): IF APPL-AOK
    if (ws.applAok()) {
      // COB(232): DISPLAY 'Reporting from ' WS-START-DATE
      // COB(232):    ' to ' WS-END-DATE
      sysout.display(
          "Reporting from ",
          ws.wsDateparmRecord.wsStartDate,
          " to ",
          ws.wsDateparmRecord.wsEndDate);
      // COB(234): ELSE
    } else {
      // COB(235): IF APPL-EOF
      if (ws.applEof()) {
        // COB(236): MOVE 'Y' TO END-OF-FILE
        ws.endOfFile.setString("Y");
        // COB(237): ELSE
      } else {
        // COB(238): DISPLAY 'ERROR READING DATEPARM FILE'
        sysout.display("ERROR READING DATEPARM FILE");
        // COB(239): MOVE DATEPARM-STATUS TO IO-STATUS
        ws.ioStatus.setValue(ws.dateparmStatus);
        // COB(240): PERFORM 9910-DISPLAY-IO-STATUS
        _9910DisplayIoStatus();
        // COB(241): PERFORM 9999-ABEND-PROGRAM
        _9999AbendProgram();
        // COB(242): END-IF
      }
      // COB(243): .
    }
  }

  /*****************************************************************
   * I/O ROUTINES TO ACCESS A KSDS, VSAM DATA SET...               *
   *****************************************************************/
  protected void _1000TranfileGetNext() {
    // COB(249): READ TRANSACT-FILE INTO TRAN-RECORD.
    transactFile.readNext(ws.cvtra05y.tranRecord);
    // COB(251): EVALUATE TRANFILE-STATUS
    // COB(252): WHEN '00'
    if (ws.tranfileStatus.equals("00")) {
      // COB(253): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(254): WHEN '10'
    } else if (ws.tranfileStatus.equals("10")) {
      // COB(255): MOVE 16 TO APPL-RESULT
      ws.applResult.setValue(16);
      // COB(256): WHEN OTHER
    } else {
      // COB(257): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(258): END-EVALUATE
    }
    // COB(260): IF APPL-AOK
    if (ws.applAok()) {
      // COB(261): CONTINUE
      // do nothing
      // COB(262): ELSE
    } else {
      // COB(263): IF APPL-EOF
      if (ws.applEof()) {
        // COB(264): MOVE 'Y' TO END-OF-FILE
        ws.endOfFile.setString("Y");
        // COB(265): ELSE
      } else {
        // COB(266): DISPLAY 'ERROR READING TRANSACTION FILE'
        sysout.display("ERROR READING TRANSACTION FILE");
        // COB(267): MOVE TRANFILE-STATUS TO IO-STATUS
        ws.ioStatus.setValue(ws.tranfileStatus);
        // COB(268): PERFORM 9910-DISPLAY-IO-STATUS
        _9910DisplayIoStatus();
        // COB(269): PERFORM 9999-ABEND-PROGRAM
        _9999AbendProgram();
        // COB(270): END-IF
      }
      // COB(271): END-IF
    }
    // COB(272): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1100WriteTransactionReport() {
    // COB(275): IF WS-FIRST-TIME = 'Y'
    if (ws.wsReportVars.wsFirstTime.equals("Y")) {
      // COB(276): MOVE 'N' TO WS-FIRST-TIME
      ws.wsReportVars.wsFirstTime.setString("N");
      // COB(277): MOVE WS-START-DATE TO REPT-START-DATE
      ws.cvtra07y.reportNameHeader.reptStartDate.setValue(ws.wsDateparmRecord.wsStartDate);
      // COB(278): MOVE WS-END-DATE TO REPT-END-DATE
      ws.cvtra07y.reportNameHeader.reptEndDate.setValue(ws.wsDateparmRecord.wsEndDate);
      // COB(279): PERFORM 1120-WRITE-HEADERS
      _1120WriteHeaders();
      // COB(280): END-IF
    }
    // COB(282): IF FUNCTION MOD(WS-LINE-COUNTER, WS-PAGE-SIZE) = 0
    if (AbstractNField.mod(
            ws.wsReportVars.wsLineCounter.getInt(), ws.wsReportVars.wsPageSize.getInt())
        .equals(0)) {
      // COB(283): PERFORM 1110-WRITE-PAGE-TOTALS
      _1110WritePageTotals();
      // COB(284): PERFORM 1120-WRITE-HEADERS
      _1120WriteHeaders();
      // COB(285): END-IF
    }
    // COB(287): ADD TRAN-AMT TO WS-PAGE-TOTAL
    // COB(287):                 WS-ACCOUNT-TOTAL
    ws.wsReportVars.wsPageTotal.add(ws.cvtra05y.tranRecord.tranAmt);
    ws.wsReportVars.wsAccountTotal.add(ws.cvtra05y.tranRecord.tranAmt);
    // COB(289): PERFORM 1120-WRITE-DETAIL
    _1120WriteDetail();
    // COB(290): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1110WritePageTotals() {
    // COB(294): MOVE WS-PAGE-TOTAL TO REPT-PAGE-TOTAL
    ws.cvtra07y.reportPageTotals.reptPageTotal.setValue(ws.wsReportVars.wsPageTotal);
    // COB(295): MOVE REPORT-PAGE-TOTALS TO FD-REPTFILE-REC
    fs.fdReptfileRec.setValue(ws.cvtra07y.reportPageTotals);
    // COB(296): PERFORM 1111-WRITE-REPORT-REC
    _1111WriteReportRec();
    // COB(297): ADD WS-PAGE-TOTAL TO WS-GRAND-TOTAL
    ws.wsReportVars.wsGrandTotal.add(ws.wsReportVars.wsPageTotal);
    // COB(298): MOVE 0 TO WS-PAGE-TOTAL
    ws.wsReportVars.wsPageTotal.setValue(0);
    // COB(299): ADD 1 TO WS-LINE-COUNTER
    ws.wsReportVars.wsLineCounter.add(1);
    // COB(300): MOVE TRANSACTION-HEADER-2 TO FD-REPTFILE-REC
    fs.fdReptfileRec.setValue(ws.cvtra07y.transactionHeader2);
    // COB(301): PERFORM 1111-WRITE-REPORT-REC
    _1111WriteReportRec();
    // COB(302): ADD 1 TO WS-LINE-COUNTER
    ws.wsReportVars.wsLineCounter.add(1);
    // COB(304): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1120WriteAccountTotals() {
    // COB(307): MOVE WS-ACCOUNT-TOTAL   TO REPT-ACCOUNT-TOTAL
    ws.cvtra07y.reportAccountTotals.reptAccountTotal.setValue(ws.wsReportVars.wsAccountTotal);
    // COB(308): MOVE REPORT-ACCOUNT-TOTALS TO FD-REPTFILE-REC
    fs.fdReptfileRec.setValue(ws.cvtra07y.reportAccountTotals);
    // COB(309): PERFORM 1111-WRITE-REPORT-REC
    _1111WriteReportRec();
    // COB(310): MOVE 0 TO WS-ACCOUNT-TOTAL
    ws.wsReportVars.wsAccountTotal.setValue(0);
    // COB(311): ADD 1 TO WS-LINE-COUNTER
    ws.wsReportVars.wsLineCounter.add(1);
    // COB(312): MOVE TRANSACTION-HEADER-2 TO FD-REPTFILE-REC
    fs.fdReptfileRec.setValue(ws.cvtra07y.transactionHeader2);
    // COB(313): PERFORM 1111-WRITE-REPORT-REC
    _1111WriteReportRec();
    // COB(314): ADD 1 TO WS-LINE-COUNTER
    ws.wsReportVars.wsLineCounter.add(1);
    // COB(316): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1110WriteGrandTotals() {
    // COB(319): MOVE WS-GRAND-TOTAL TO REPT-GRAND-TOTAL
    ws.cvtra07y.reportGrandTotals.reptGrandTotal.setValue(ws.wsReportVars.wsGrandTotal);
    // COB(320): MOVE REPORT-GRAND-TOTALS TO FD-REPTFILE-REC
    fs.fdReptfileRec.setValue(ws.cvtra07y.reportGrandTotals);
    // COB(321): PERFORM 1111-WRITE-REPORT-REC
    _1111WriteReportRec();
    // COB(322): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1120WriteHeaders() {
    // COB(325): MOVE REPORT-NAME-HEADER TO FD-REPTFILE-REC
    fs.fdReptfileRec.setValue(ws.cvtra07y.reportNameHeader);
    // COB(326): PERFORM 1111-WRITE-REPORT-REC
    _1111WriteReportRec();
    // COB(327): ADD 1 TO WS-LINE-COUNTER
    ws.wsReportVars.wsLineCounter.add(1);
    // COB(329): MOVE WS-BLANK-LINE TO FD-REPTFILE-REC
    fs.fdReptfileRec.setValue(ws.wsReportVars.wsBlankLine);
    // COB(330): PERFORM 1111-WRITE-REPORT-REC
    _1111WriteReportRec();
    // COB(331): ADD 1 TO WS-LINE-COUNTER
    ws.wsReportVars.wsLineCounter.add(1);
    // COB(333): MOVE TRANSACTION-HEADER-1 TO FD-REPTFILE-REC
    fs.fdReptfileRec.setValue(ws.cvtra07y.transactionHeader1);
    // COB(334): PERFORM 1111-WRITE-REPORT-REC
    _1111WriteReportRec();
    // COB(335): ADD 1 TO WS-LINE-COUNTER
    ws.wsReportVars.wsLineCounter.add(1);
    // COB(337): MOVE TRANSACTION-HEADER-2 TO FD-REPTFILE-REC
    fs.fdReptfileRec.setValue(ws.cvtra07y.transactionHeader2);
    // COB(338): PERFORM 1111-WRITE-REPORT-REC
    _1111WriteReportRec();
    // COB(339): ADD 1 TO WS-LINE-COUNTER
    ws.wsReportVars.wsLineCounter.add(1);
    // COB(341): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1111WriteReportRec() {
    // COB(345): WRITE FD-REPTFILE-REC
    reportFile.write();
    // COB(346): IF TRANREPT-STATUS = '00'
    if (ws.tranreptStatus.equals("00")) {
      // COB(347): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(348): ELSE
    } else {
      // COB(349): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(350): END-IF
    }
    // COB(351): IF APPL-AOK
    if (ws.applAok()) {
      // COB(352): CONTINUE
      // do nothing
      // COB(353): ELSE
    } else {
      // COB(354): DISPLAY 'ERROR WRITING REPTFILE'
      sysout.display("ERROR WRITING REPTFILE");
      // COB(355): MOVE TRANREPT-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.tranreptStatus);
      // COB(356): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(357): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(358): END-IF
    }
    // COB(359): EXIT.
    return;
  }

  protected void _1120WriteDetail() {
    // COB(362): INITIALIZE TRANSACTION-DETAIL-REPORT
    ws.cvtra07y.transactionDetailReport.initialize();
    // COB(363): MOVE TRAN-ID TO TRAN-REPORT-TRANS-ID
    ws.cvtra07y.transactionDetailReport.tranReportTransId.setValue(ws.cvtra05y.tranRecord.tranId);
    // COB(364): MOVE XREF-ACCT-ID TO TRAN-REPORT-ACCOUNT-ID
    ws.cvtra07y.transactionDetailReport.tranReportAccountId.setValue(
        ws.cvact03y.cardXrefRecord.xrefAcctId);
    // COB(365): MOVE TRAN-TYPE-CD OF TRAN-RECORD TO TRAN-REPORT-TYPE-CD
    ws.cvtra07y.transactionDetailReport.tranReportTypeCd.setValue(
        ws.cvtra05y.tranRecord.tranTypeCd);
    // COB(366): MOVE TRAN-TYPE-DESC TO TRAN-REPORT-TYPE-DESC
    ws.cvtra07y.transactionDetailReport.tranReportTypeDesc.setValue(
        ws.cvtra03y.tranTypeRecord.tranTypeDesc);
    // COB(367): MOVE TRAN-CAT-CD OF TRAN-RECORD  TO TRAN-REPORT-CAT-CD
    ws.cvtra07y.transactionDetailReport.tranReportCatCd.setValue(ws.cvtra05y.tranRecord.tranCatCd);
    // COB(368): MOVE TRAN-CAT-TYPE-DESC TO TRAN-REPORT-CAT-DESC
    ws.cvtra07y.transactionDetailReport.tranReportCatDesc.setValue(
        ws.cvtra04y.tranCatRecord.tranCatTypeDesc);
    // COB(369): MOVE TRAN-SOURCE TO TRAN-REPORT-SOURCE
    ws.cvtra07y.transactionDetailReport.tranReportSource.setValue(
        ws.cvtra05y.tranRecord.tranSource);
    // COB(370): MOVE TRAN-AMT TO TRAN-REPORT-AMT
    ws.cvtra07y.transactionDetailReport.tranReportAmt.setValue(ws.cvtra05y.tranRecord.tranAmt);
    // COB(371): MOVE TRANSACTION-DETAIL-REPORT TO FD-REPTFILE-REC
    fs.fdReptfileRec.setValue(ws.cvtra07y.transactionDetailReport);
    // COB(372): PERFORM 1111-WRITE-REPORT-REC
    _1111WriteReportRec();
    // COB(373): ADD 1 TO WS-LINE-COUNTER
    ws.wsReportVars.wsLineCounter.add(1);
    // COB(374): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0000TranfileOpen() {
    // COB(377): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(378): OPEN INPUT TRANSACT-FILE
    transactFile.open(OpenMode.Input);
    // COB(379): IF TRANFILE-STATUS = '00'
    if (ws.tranfileStatus.equals("00")) {
      // COB(380): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(381): ELSE
    } else {
      // COB(382): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(383): END-IF
    }
    // COB(384): IF APPL-AOK
    if (ws.applAok()) {
      // COB(385): CONTINUE
      // do nothing
      // COB(386): ELSE
    } else {
      // COB(387): DISPLAY 'ERROR OPENING TRANFILE'
      sysout.display("ERROR OPENING TRANFILE");
      // COB(388): MOVE TRANFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.tranfileStatus);
      // COB(389): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(390): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(391): END-IF
    }
    // COB(392): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0100ReptfileOpen() {
    // COB(395): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(396): OPEN OUTPUT REPORT-FILE
    reportFile.open(OpenMode.Output);
    // COB(397): IF TRANREPT-STATUS = '00'
    if (ws.tranreptStatus.equals("00")) {
      // COB(398): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(399): ELSE
    } else {
      // COB(400): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(401): END-IF
    }
    // COB(402): IF APPL-AOK
    if (ws.applAok()) {
      // COB(403): CONTINUE
      // do nothing
      // COB(404): ELSE
    } else {
      // COB(405): DISPLAY 'ERROR OPENING REPTFILE'
      sysout.display("ERROR OPENING REPTFILE");
      // COB(406): MOVE TRANREPT-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.tranreptStatus);
      // COB(407): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(408): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(409): END-IF
    }
    // COB(410): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0200CardxrefOpen() {
    // COB(413): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(414): OPEN INPUT XREF-FILE
    xrefFile.open(OpenMode.Input);
    // COB(415): IF  CARDXREF-STATUS = '00'
    if (ws.cardxrefStatus.equals("00")) {
      // COB(416): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(417): ELSE
    } else {
      // COB(418): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(419): END-IF
    }
    // COB(420): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(421): CONTINUE
      // do nothing
      // COB(422): ELSE
    } else {
      // COB(423): DISPLAY 'ERROR OPENING CROSS REF FILE'
      sysout.display("ERROR OPENING CROSS REF FILE");
      // COB(424): MOVE CARDXREF-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.cardxrefStatus);
      // COB(425): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(426): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(427): END-IF
    }
    // COB(428): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0300TrantypeOpen() {
    // COB(431): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(432): OPEN INPUT TRANTYPE-FILE
    trantypeFile.open(OpenMode.Input);
    // COB(433): IF  TRANTYPE-STATUS = '00'
    if (ws.trantypeStatus.equals("00")) {
      // COB(434): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(435): ELSE
    } else {
      // COB(436): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(437): END-IF
    }
    // COB(438): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(439): CONTINUE
      // do nothing
      // COB(440): ELSE
    } else {
      // COB(441): DISPLAY 'ERROR OPENING TRANSACTION TYPE FILE'
      sysout.display("ERROR OPENING TRANSACTION TYPE FILE");
      // COB(442): MOVE TRANTYPE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.trantypeStatus);
      // COB(443): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(444): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(445): END-IF
    }
    // COB(446): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0400TrancatgOpen() {
    // COB(449): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(450): OPEN INPUT TRANCATG-FILE
    trancatgFile.open(OpenMode.Input);
    // COB(451): IF  TRANCATG-STATUS = '00'
    if (ws.trancatgStatus.equals("00")) {
      // COB(452): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(453): ELSE
    } else {
      // COB(454): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(455): END-IF
    }
    // COB(456): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(457): CONTINUE
      // do nothing
      // COB(458): ELSE
    } else {
      // COB(459): DISPLAY 'ERROR OPENING TRANSACTION CATG FILE'
      sysout.display("ERROR OPENING TRANSACTION CATG FILE");
      // COB(460): MOVE TRANCATG-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.trancatgStatus);
      // COB(461): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(462): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(463): END-IF
    }
    // COB(464): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0500DateparmOpen() {
    // COB(467): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(468): OPEN INPUT DATE-PARMS-FILE
    dateParmsFile.open(OpenMode.Input);
    // COB(469): IF  DATEPARM-STATUS = '00'
    if (ws.dateparmStatus.equals("00")) {
      // COB(470): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(471): ELSE
    } else {
      // COB(472): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(473): END-IF
    }
    // COB(474): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(475): CONTINUE
      // do nothing
      // COB(476): ELSE
    } else {
      // COB(477): DISPLAY 'ERROR OPENING DATE PARM FILE'
      sysout.display("ERROR OPENING DATE PARM FILE");
      // COB(478): MOVE DATEPARM-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.dateparmStatus);
      // COB(479): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(480): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(481): END-IF
    }
    // COB(482): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1500ALookupXref() {
    // COB(485): READ XREF-FILE INTO CARD-XREF-RECORD
    // COB(485):    INVALID KEY
    // COB(485):       DISPLAY 'INVALID CARD NUMBER : '  FD-XREF-CARD-NUM
    // COB(485):       MOVE 23 TO IO-STATUS
    // COB(485):       PERFORM 9910-DISPLAY-IO-STATUS
    // COB(485):       PERFORM 9999-ABEND-PROGRAM
    // COB(485): END-READ
    xrefFile
        .readInto(ws.cvact03y.cardXrefRecord) //
        .invalidKey(this::xrefFileReadInvalidKey);
    // COB(492): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1500BLookupTrantype() {
    // COB(495): READ TRANTYPE-FILE INTO TRAN-TYPE-RECORD
    // COB(495):    INVALID KEY
    // COB(495):       DISPLAY 'INVALID TRANSACTION TYPE : '  FD-TRAN-TYPE
    // COB(495):       MOVE 23 TO IO-STATUS
    // COB(495):       PERFORM 9910-DISPLAY-IO-STATUS
    // COB(495):       PERFORM 9999-ABEND-PROGRAM
    // COB(495): END-READ
    trantypeFile
        .readInto(ws.cvtra03y.tranTypeRecord) //
        .invalidKey(this::trantypeFileReadInvalidKey);
    // COB(502): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1500CLookupTrancatg() {
    // COB(505): READ TRANCATG-FILE INTO TRAN-CAT-RECORD
    // COB(505):    INVALID KEY
    // COB(505):       DISPLAY 'INVALID TRAN CATG KEY : '  FD-TRAN-CAT-KEY
    // COB(505):       MOVE 23 TO IO-STATUS
    // COB(505):       PERFORM 9910-DISPLAY-IO-STATUS
    // COB(505):       PERFORM 9999-ABEND-PROGRAM
    // COB(505): END-READ
    trancatgFile
        .readInto(ws.cvtra04y.tranCatRecord) //
        .invalidKey(this::trancatgFileReadInvalidKey);
    // COB(512): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9000TranfileClose() {
    // COB(515): ADD 8 TO ZERO GIVING APPL-RESULT.
    ws.applResult.add(8);
    // COB(516): CLOSE TRANSACT-FILE
    transactFile.close();
    // COB(517): IF TRANFILE-STATUS = '00'
    if (ws.tranfileStatus.equals("00")) {
      // COB(518): SUBTRACT APPL-RESULT FROM APPL-RESULT
      ws.applResult.subtract(ws.applResult);
      // COB(519): ELSE
    } else {
      // COB(520): ADD 12 TO ZERO GIVING APPL-RESULT
      ws.applResult.add(12);
      // COB(521): END-IF
    }
    // COB(522): IF APPL-AOK
    if (ws.applAok()) {
      // COB(523): CONTINUE
      // do nothing
      // COB(524): ELSE
    } else {
      // COB(525): DISPLAY 'ERROR CLOSING POSTED TRANSACTION FILE'
      sysout.display("ERROR CLOSING POSTED TRANSACTION FILE");
      // COB(526): MOVE TRANFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.tranfileStatus);
      // COB(527): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(528): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(529): END-IF
    }
    // COB(530): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9100ReptfileClose() {
    // COB(533): ADD 8 TO ZERO GIVING APPL-RESULT.
    ws.applResult.add(8);
    // COB(534): CLOSE REPORT-FILE
    reportFile.close();
    // COB(535): IF TRANREPT-STATUS = '00'
    if (ws.tranreptStatus.equals("00")) {
      // COB(536): SUBTRACT APPL-RESULT FROM APPL-RESULT
      ws.applResult.subtract(ws.applResult);
      // COB(537): ELSE
    } else {
      // COB(538): ADD 12 TO ZERO GIVING APPL-RESULT
      ws.applResult.add(12);
      // COB(539): END-IF
    }
    // COB(540): IF APPL-AOK
    if (ws.applAok()) {
      // COB(541): CONTINUE
      // do nothing
      // COB(542): ELSE
    } else {
      // COB(543): DISPLAY 'ERROR CLOSING REPORT FILE'
      sysout.display("ERROR CLOSING REPORT FILE");
      // COB(544): MOVE TRANREPT-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.tranreptStatus);
      // COB(545): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(546): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(547): END-IF
    }
    // COB(548): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9200CardxrefClose() {
    // COB(552): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(553): CLOSE XREF-FILE
    xrefFile.close();
    // COB(554): IF  CARDXREF-STATUS = '00'
    if (ws.cardxrefStatus.equals("00")) {
      // COB(555): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(556): ELSE
    } else {
      // COB(557): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(558): END-IF
    }
    // COB(559): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(560): CONTINUE
      // do nothing
      // COB(561): ELSE
    } else {
      // COB(562): DISPLAY 'ERROR CLOSING CROSS REF FILE'
      sysout.display("ERROR CLOSING CROSS REF FILE");
      // COB(563): MOVE CARDXREF-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.cardxrefStatus);
      // COB(564): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(565): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(566): END-IF
    }
    // COB(567): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9300TrantypeClose() {
    // COB(570): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(571): CLOSE TRANTYPE-FILE
    trantypeFile.close();
    // COB(572): IF  TRANTYPE-STATUS = '00'
    if (ws.trantypeStatus.equals("00")) {
      // COB(573): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(574): ELSE
    } else {
      // COB(575): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(576): END-IF
    }
    // COB(577): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(578): CONTINUE
      // do nothing
      // COB(579): ELSE
    } else {
      // COB(580): DISPLAY 'ERROR CLOSING TRANSACTION TYPE FILE'
      sysout.display("ERROR CLOSING TRANSACTION TYPE FILE");
      // COB(581): MOVE TRANTYPE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.trantypeStatus);
      // COB(582): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(583): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(584): END-IF
    }
    // COB(585): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9400TrancatgClose() {
    // COB(588): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(589): CLOSE TRANCATG-FILE
    trancatgFile.close();
    // COB(590): IF  TRANCATG-STATUS = '00'
    if (ws.trancatgStatus.equals("00")) {
      // COB(591): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(592): ELSE
    } else {
      // COB(593): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(594): END-IF
    }
    // COB(595): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(596): CONTINUE
      // do nothing
      // COB(597): ELSE
    } else {
      // COB(598): DISPLAY 'ERROR CLOSING TRANSACTION CATG FILE'
      sysout.display("ERROR CLOSING TRANSACTION CATG FILE");
      // COB(599): MOVE TRANCATG-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.trancatgStatus);
      // COB(600): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(601): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(602): END-IF
    }
    // COB(603): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9500DateparmClose() {
    // COB(606): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(607): CLOSE DATE-PARMS-FILE
    dateParmsFile.close();
    // COB(608): IF  DATEPARM-STATUS = '00'
    if (ws.dateparmStatus.equals("00")) {
      // COB(609): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(610): ELSE
    } else {
      // COB(611): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(612): END-IF
    }
    // COB(613): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(614): CONTINUE
      // do nothing
      // COB(615): ELSE
    } else {
      // COB(616): DISPLAY 'ERROR CLOSING DATE PARM FILE'
      sysout.display("ERROR CLOSING DATE PARM FILE");
      // COB(617): MOVE DATEPARM-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.dateparmStatus);
      // COB(618): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(619): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(620): END-IF
    }
    // COB(621): EXIT.
    return;
  }

  protected void _9999AbendProgram() {
    // COB(627): DISPLAY 'ABENDING PROGRAM'
    sysout.display("ABENDING PROGRAM");
    // COB(628): MOVE 0 TO TIMING
    ws.timing.setValue(0);
    // COB(629): MOVE 999 TO ABCODE
    ws.abcode.setValue(999);
    // COB(630): CALL 'CEE3ABD'.
    throw new AbendException(ws.abcode.getInt());
  }

  /*****************************************************************/
  protected void _9910DisplayIoStatus() {
    // COB(634): IF IO-STATUS NOT NUMERIC
    // COB(634):    OR IO-STAT1 = '9'
    if (!ws.ioStatus.isNumeric() || ws.ioStatus.ioStat1.equals("9")) {
      // COB(636): MOVE IO-STAT1 TO IO-STATUS-04(1:1)
      ws.ioStatus04.setValue(ws.ioStatus.ioStat1, 0, 1);
      // COB(637): MOVE 0 TO TWO-BYTES-BINARY
      ws.twoBytesBinary.setValue(0);
      // COB(638): MOVE IO-STAT2 TO TWO-BYTES-RIGHT
      ws.twoBytesAlpha.twoBytesRight.setValue(ws.ioStatus.ioStat2);
      // COB(639): MOVE TWO-BYTES-BINARY TO IO-STATUS-0403
      ws.ioStatus04.ioStatus0403.setValue(ws.twoBytesBinary);
      // COB(640): DISPLAY 'FILE STATUS IS: NNNN' IO-STATUS-04
      sysout.display("FILE STATUS IS: NNNN", ws.ioStatus04);
      // COB(641): ELSE
    } else {
      // COB(642): MOVE '0000' TO IO-STATUS-04
      ws.ioStatus04.setString("0000");
      // COB(643): MOVE IO-STATUS TO IO-STATUS-04(3:2)
      ws.ioStatus04.setValue(ws.ioStatus, 0, 2, 2);
      // COB(644): DISPLAY 'FILE STATUS IS: NNNN' IO-STATUS-04
      sysout.display("FILE STATUS IS: NNNN", ws.ioStatus04);
      // COB(645): END-IF
    }
    // COB(646): EXIT.
    return;
  }

  protected void xrefFileReadInvalidKey() {
    // COB(487): DISPLAY 'INVALID CARD NUMBER : '  FD-XREF-CARD-NUM
    sysout.display("INVALID CARD NUMBER : ", fs.fdCardxrefRec.fdXrefCardNum);
    // COB(488): MOVE 23 TO IO-STATUS
    ws.ioStatus.setValue(23);
    // COB(489): PERFORM 9910-DISPLAY-IO-STATUS
    _9910DisplayIoStatus();
    // COB(490): PERFORM 9999-ABEND-PROGRAM
    _9999AbendProgram();
  }

  protected void trantypeFileReadInvalidKey() {
    // COB(497): DISPLAY 'INVALID TRANSACTION TYPE : '  FD-TRAN-TYPE
    sysout.display("INVALID TRANSACTION TYPE : ", fs.fdTrantypeRec.fdTranType);
    // COB(498): MOVE 23 TO IO-STATUS
    ws.ioStatus.setValue(23);
    // COB(499): PERFORM 9910-DISPLAY-IO-STATUS
    _9910DisplayIoStatus();
    // COB(500): PERFORM 9999-ABEND-PROGRAM
    _9999AbendProgram();
  }

  protected void trancatgFileReadInvalidKey() {
    // COB(507): DISPLAY 'INVALID TRAN CATG KEY : '  FD-TRAN-CAT-KEY
    sysout.display("INVALID TRAN CATG KEY : ", fs.fdTranCatRecord.fdTranCatKey);
    // COB(508): MOVE 23 TO IO-STATUS
    ws.ioStatus.setValue(23);
    // COB(509): PERFORM 9910-DISPLAY-IO-STATUS
    _9910DisplayIoStatus();
    // COB(510): PERFORM 9999-ABEND-PROGRAM
    _9999AbendProgram();
  }
}
