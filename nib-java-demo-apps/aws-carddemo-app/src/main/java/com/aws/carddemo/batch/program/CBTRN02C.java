package com.aws.carddemo.batch.program;

import com.aws.carddemo.batch.program.storage.cbtrn02c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.io.*;
import com.nib.commons.io.vsam.*;
import com.nib.commons.storage.*;

public class CBTRN02C extends AbstractProgram {
  private SequentialFile dalytranFile;
  private KeySequentialFile transactFile;
  private KeySequentialFile xrefFile;
  private SequentialFile dalyrejsFile;
  private KeySequentialFile accountFile;
  private KeySequentialFile tcatbalFile;
  final Cbtrn02cRecords fs = new Cbtrn02cRecords();

  @ProgramStorage final Cbtrn02cWorking ws = new Cbtrn02cWorking();

  // Linkage
  public static class Cbtrn02cLinkage extends NGroup {}

  final Cbtrn02cLinkage params = new Cbtrn02cLinkage();

  public CBTRN02C(Context context) {
    super(context);
  }

  @ProgramInit
  protected void initialize() {
    dalytranFile =
        new SequentialFile(context, "DALYTRAN") //
            .fileStatusIs(ws.dalytranStatus) //
            .recordIs(fs.fdTranRecord);
    transactFile =
        new KeySequentialFile(context, "TRANFILE") //
            .recordKeyIs(fs.fdTranfileRec.fdTransId) //
            .fileStatusIs(ws.tranfileStatus) //
            .recordIs(fs.fdTranfileRec);
    xrefFile =
        new KeySequentialFile(context, "XREFFILE") //
            .recordKeyIs(fs.fdXreffileRec.fdXrefCardNum) //
            .fileStatusIs(ws.xreffileStatus) //
            .recordIs(fs.fdXreffileRec);
    dalyrejsFile =
        new SequentialFile(context, "DALYREJS") //
            .fileStatusIs(ws.dalyrejsStatus) //
            .recordIs(fs.fdRejsRecord);
    accountFile =
        new KeySequentialFile(context, "ACCTFILE") //
            .recordKeyIs(fs.fdAcctfileRec.fdAcctId) //
            .fileStatusIs(ws.acctfileStatus) //
            .recordIs(fs.fdAcctfileRec);
    tcatbalFile =
        new KeySequentialFile(context, "TCATBALF") //
            .recordKeyIs(fs.fdTranCatBalRecord.fdTranCatKey) //
            .fileStatusIs(ws.tcatbalfStatus) //
            .recordIs(fs.fdTranCatBalRecord);
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
    // COB(194): DISPLAY 'START OF EXECUTION OF PROGRAM CBTRN02C'.
    sysout.display("START OF EXECUTION OF PROGRAM CBTRN02C");
    // COB(195): PERFORM 0000-DALYTRAN-OPEN.
    _0000DalytranOpen();
    // COB(196): PERFORM 0100-TRANFILE-OPEN.
    _0100TranfileOpen();
    // COB(197): PERFORM 0200-XREFFILE-OPEN.
    _0200XreffileOpen();
    // COB(198): PERFORM 0300-DALYREJS-OPEN.
    _0300DalyrejsOpen();
    // COB(199): PERFORM 0400-ACCTFILE-OPEN.
    _0400AcctfileOpen();
    // COB(200): PERFORM 0500-TCATBALF-OPEN.
    _0500TcatbalfOpen();
    // COB(202): PERFORM UNTIL END-OF-FILE = 'Y'
    while (!ws.endOfFile.equals("Y")) {
      // COB(203): IF  END-OF-FILE = 'N'
      if (ws.endOfFile.equals("N")) {
        // COB(204): PERFORM 1000-DALYTRAN-GET-NEXT
        _1000DalytranGetNext();
        // COB(205): IF  END-OF-FILE = 'N'
        if (ws.endOfFile.equals("N")) {
          // COB(206): ADD 1 TO WS-TRANSACTION-COUNT
          ws.wsCounters.wsTransactionCount.add(1);
          // COB(208): MOVE 0 TO WS-VALIDATION-FAIL-REASON
          //               DISPLAY DALYTRAN-RECORD
          ws.wsValidationTrailer.wsValidationFailReason.setValue(0);
          // COB(209): MOVE SPACES TO WS-VALIDATION-FAIL-REASON-DESC
          ws.wsValidationTrailer.wsValidationFailReasonDesc.spaces();
          // COB(210): PERFORM 1500-VALIDATE-TRAN
          _1500ValidateTran();
          // COB(211): IF WS-VALIDATION-FAIL-REASON = 0
          if (ws.wsValidationTrailer.wsValidationFailReason.equals(0)) {
            // COB(212): PERFORM 2000-POST-TRANSACTION
            _2000PostTransaction();
            // COB(213): ELSE
          } else {
            // COB(214): ADD 1 TO WS-REJECT-COUNT
            ws.wsCounters.wsRejectCount.add(1);
            // COB(215): PERFORM 2500-WRITE-REJECT-REC
            _2500WriteRejectRec();
            // COB(216): END-IF
          }
          // COB(217): END-IF
        }
        // COB(218): END-IF
      }
      // COB(219): END-PERFORM.
    }
    // COB(221): PERFORM 9000-DALYTRAN-CLOSE.
    _9000DalytranClose();
    // COB(222): PERFORM 9100-TRANFILE-CLOSE.
    _9100TranfileClose();
    // COB(223): PERFORM 9200-XREFFILE-CLOSE.
    _9200XreffileClose();
    // COB(224): PERFORM 9300-DALYREJS-CLOSE.
    _9300DalyrejsClose();
    // COB(225): PERFORM 9400-ACCTFILE-CLOSE.
    _9400AcctfileClose();
    // COB(226): PERFORM 9500-TCATBALF-CLOSE.
    _9500TcatbalfClose();
    // COB(227): DISPLAY 'TRANSACTIONS PROCESSED :' WS-TRANSACTION-COUNT
    sysout.display("TRANSACTIONS PROCESSED :", ws.wsCounters.wsTransactionCount);
    // COB(228): DISPLAY 'TRANSACTIONS REJECTED  :' WS-REJECT-COUNT
    sysout.display("TRANSACTIONS REJECTED  :", ws.wsCounters.wsRejectCount);
    // COB(229): IF WS-REJECT-COUNT > 0
    if (ws.wsCounters.wsRejectCount.greaterThan(0)) {
      // COB(230): MOVE 4 TO RETURN-CODE
      RETURN_CODE = 4;
      // COB(231): END-IF
    }
    // COB(232): DISPLAY 'END OF EXECUTION OF PROGRAM CBTRN02C'.
    sysout.display("END OF EXECUTION OF PROGRAM CBTRN02C");
    // COB(234): GOBACK.
    context.goback();
  }

  /***---------------------------------------------------------------*/
  protected void _0000DalytranOpen() {
    // COB(237): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(238): OPEN INPUT DALYTRAN-FILE
    dalytranFile.open(OpenMode.Input);
    // COB(239): IF  DALYTRAN-STATUS = '00'
    if (ws.dalytranStatus.equals("00")) {
      // COB(240): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(241): ELSE
    } else {
      // COB(242): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(243): END-IF
    }
    // COB(244): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(245): CONTINUE
      // do nothing
      // COB(246): ELSE
    } else {
      // COB(247): DISPLAY 'ERROR OPENING DALYTRAN'
      sysout.display("ERROR OPENING DALYTRAN");
      // COB(248): MOVE DALYTRAN-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.dalytranStatus);
      // COB(249): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(250): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(251): END-IF
    }
    // COB(252): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0100TranfileOpen() {
    // COB(255): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(256): OPEN OUTPUT TRANSACT-FILE
    transactFile.open(OpenMode.Output);
    // COB(257): IF  TRANFILE-STATUS = '00'
    if (ws.tranfileStatus.equals("00")) {
      // COB(258): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(259): ELSE
    } else {
      // COB(260): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(261): END-IF
    }
    // COB(262): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(263): CONTINUE
      // do nothing
      // COB(264): ELSE
    } else {
      // COB(265): DISPLAY 'ERROR OPENING TRANSACTION FILE'
      sysout.display("ERROR OPENING TRANSACTION FILE");
      // COB(266): MOVE TRANFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.tranfileStatus);
      // COB(267): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(268): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(269): END-IF
    }
    // COB(270): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0200XreffileOpen() {
    // COB(274): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(275): OPEN INPUT XREF-FILE
    xrefFile.open(OpenMode.Input);
    // COB(276): IF  XREFFILE-STATUS = '00'
    if (ws.xreffileStatus.equals("00")) {
      // COB(277): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(278): ELSE
    } else {
      // COB(279): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(280): END-IF
    }
    // COB(281): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(282): CONTINUE
      // do nothing
      // COB(283): ELSE
    } else {
      // COB(284): DISPLAY 'ERROR OPENING CROSS REF FILE'
      sysout.display("ERROR OPENING CROSS REF FILE");
      // COB(285): MOVE XREFFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.xreffileStatus);
      // COB(286): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(287): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(288): END-IF
    }
    // COB(289): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0300DalyrejsOpen() {
    // COB(292): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(293): OPEN OUTPUT DALYREJS-FILE
    dalyrejsFile.open(OpenMode.Output);
    // COB(294): IF  DALYREJS-STATUS = '00'
    if (ws.dalyrejsStatus.equals("00")) {
      // COB(295): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(296): ELSE
    } else {
      // COB(297): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(298): END-IF
    }
    // COB(299): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(300): CONTINUE
      // do nothing
      // COB(301): ELSE
    } else {
      // COB(302): DISPLAY 'ERROR OPENING DALY REJECTS FILE'
      sysout.display("ERROR OPENING DALY REJECTS FILE");
      // COB(303): MOVE DALYREJS-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.dalyrejsStatus);
      // COB(304): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(305): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(306): END-IF
    }
    // COB(307): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0400AcctfileOpen() {
    // COB(310): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(311): OPEN I-O  ACCOUNT-FILE
    accountFile.open(OpenMode.InputOutput);
    // COB(312): IF  ACCTFILE-STATUS = '00'
    if (ws.acctfileStatus.equals("00")) {
      // COB(313): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(314): ELSE
    } else {
      // COB(315): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(316): END-IF
    }
    // COB(317): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(318): CONTINUE
      // do nothing
      // COB(319): ELSE
    } else {
      // COB(320): DISPLAY 'ERROR OPENING ACCOUNT MASTER FILE'
      sysout.display("ERROR OPENING ACCOUNT MASTER FILE");
      // COB(321): MOVE ACCTFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.acctfileStatus);
      // COB(322): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(323): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(324): END-IF
    }
    // COB(325): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0500TcatbalfOpen() {
    // COB(328): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(329): OPEN I-O  TCATBAL-FILE
    tcatbalFile.open(OpenMode.InputOutput);
    // COB(330): IF  TCATBALF-STATUS = '00'
    if (ws.tcatbalfStatus.equals("00")) {
      // COB(331): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(332): ELSE
    } else {
      // COB(333): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(334): END-IF
    }
    // COB(335): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(336): CONTINUE
      // do nothing
      // COB(337): ELSE
    } else {
      // COB(338): DISPLAY 'ERROR OPENING TRANSACTION BALANCE FILE'
      sysout.display("ERROR OPENING TRANSACTION BALANCE FILE");
      // COB(339): MOVE TCATBALF-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.tcatbalfStatus);
      // COB(340): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(341): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(342): END-IF
    }
    // COB(343): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1000DalytranGetNext() {
    // COB(346): READ DALYTRAN-FILE INTO DALYTRAN-RECORD.
    dalytranFile.readNext(ws.cvtra06y.dalytranRecord);
    // COB(347): IF  DALYTRAN-STATUS = '00'
    if (ws.dalytranStatus.equals("00")) {
      // COB(348): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(350): ELSE
      //         DISPLAY DALYTRAN-RECORD
    } else {
      // COB(351): IF  DALYTRAN-STATUS = '10'
      if (ws.dalytranStatus.equals("10")) {
        // COB(352): MOVE 16 TO APPL-RESULT
        ws.applResult.setValue(16);
        // COB(353): ELSE
      } else {
        // COB(354): MOVE 12 TO APPL-RESULT
        ws.applResult.setValue(12);
        // COB(355): END-IF
      }
      // COB(356): END-IF
    }
    // COB(357): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(358): CONTINUE
      // do nothing
      // COB(359): ELSE
    } else {
      // COB(360): IF  APPL-EOF
      if (ws.applEof()) {
        // COB(361): MOVE 'Y' TO END-OF-FILE
        ws.endOfFile.setString("Y");
        // COB(362): ELSE
      } else {
        // COB(363): DISPLAY 'ERROR READING DALYTRAN FILE'
        sysout.display("ERROR READING DALYTRAN FILE");
        // COB(364): MOVE DALYTRAN-STATUS TO IO-STATUS
        ws.ioStatus.setValue(ws.dalytranStatus);
        // COB(365): PERFORM 9910-DISPLAY-IO-STATUS
        _9910DisplayIoStatus();
        // COB(366): PERFORM 9999-ABEND-PROGRAM
        _9999AbendProgram();
        // COB(367): END-IF
      }
      // COB(368): END-IF
    }
    // COB(369): EXIT.
    return;
  }

  protected void _1500ValidateTran() {
    // COB(371): PERFORM 1500-A-LOOKUP-XREF.
    _1500ALookupXref();
    // COB(372): IF WS-VALIDATION-FAIL-REASON = 0
    if (ws.wsValidationTrailer.wsValidationFailReason.equals(0)) {
      // COB(373): PERFORM 1500-B-LOOKUP-ACCT
      _1500BLookupAcct();
      // COB(374): ELSE
    } else {
      // COB(375): CONTINUE
      // do nothing
      // COB(376): END-IF
    }
    // COB(378): EXIT.
    //  ADD MORE VALIDATIONS HERE
    return;
  }

  protected void _1500ALookupXref() {
    // COB(382): MOVE DALYTRAN-CARD-NUM TO FD-XREF-CARD-NUM
    //     DISPLAY 'CARD NUMBER: ' DALYTRAN-CARD-NUM
    fs.fdXreffileRec.fdXrefCardNum.setValue(ws.cvtra06y.dalytranRecord.dalytranCardNum);
    // COB(383): READ XREF-FILE INTO CARD-XREF-RECORD
    // COB(383):    INVALID KEY
    // COB(383):      MOVE 100 TO WS-VALIDATION-FAIL-REASON
    // COB(383):      MOVE 'INVALID CARD NUMBER FOUND'
    // COB(383):        TO WS-VALIDATION-FAIL-REASON-DESC
    // COB(383):    NOT INVALID KEY
    // COB(383):       *           DISPLAY 'ACCOUNT RECORD FOUND'
    // COB(383):        CONTINUE
    // COB(383): END-READ
    xrefFile
        .readInto(ws.cvact03y.cardXrefRecord) //
        .invalidKey(this::xrefFileReadInvalidKey) //
        .notInvalidKey(this::xrefFileReadNotInvalidKey);
    // COB(392): EXIT.
    return;
  }

  protected void _1500BLookupAcct() {
    // COB(394): MOVE XREF-ACCT-ID TO FD-ACCT-ID
    fs.fdAcctfileRec.fdAcctId.setValue(ws.cvact03y.cardXrefRecord.xrefAcctId);
    // COB(395): READ ACCOUNT-FILE INTO ACCOUNT-RECORD
    // COB(395):    INVALID KEY
    // COB(395):      MOVE 101 TO WS-VALIDATION-FAIL-REASON
    // COB(395):      MOVE 'ACCOUNT RECORD NOT FOUND'
    // COB(395):        TO WS-VALIDATION-FAIL-REASON-DESC
    // COB(395):    NOT INVALID KEY
    // COB(395):       *         DISPLAY 'ACCT-CREDIT-LIMIT:' ACCT-CREDIT-LIMIT
    // COB(395):       *         DISPLAY 'TRAN-AMT         :' DALYTRAN-AMT
    // COB(395):      COMPUTE WS-TEMP-BAL = ACCT-CURR-CYC-CREDIT
    // COB(395):                          - ACCT-CURR-CYC-DEBIT
    // COB(395):                          + DALYTRAN-AMT
    // COB(395):
    // COB(395):      IF ACCT-CREDIT-LIMIT >= WS-TEMP-BAL
    // COB(395):        CONTINUE
    // COB(395):      ELSE
    // COB(395):        MOVE 102 TO WS-VALIDATION-FAIL-REASON
    // COB(395):        MOVE 'OVERLIMIT TRANSACTION'
    // COB(395):          TO WS-VALIDATION-FAIL-REASON-DESC
    // COB(395):      END-IF
    // COB(395):      IF ACCT-EXPIRAION-DATE >= DALYTRAN-ORIG-TS (1:10)
    // COB(395):        CONTINUE
    // COB(395):      ELSE
    // COB(395):        MOVE 103 TO WS-VALIDATION-FAIL-REASON
    // COB(395):        MOVE 'TRANSACTION RECEIVED AFTER ACCT EXPIRATION'
    // COB(395):          TO WS-VALIDATION-FAIL-REASON-DESC
    // COB(395):      END-IF
    // COB(395): END-READ
    accountFile
        .readInto(ws.cvact01y.accountRecord) //
        .invalidKey(this::accountFileReadInvalidKey) //
        .notInvalidKey(this::accountFileReadNotInvalidKey);
    // COB(422): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _2000PostTransaction() {
    // COB(425): MOVE  DALYTRAN-ID            TO    TRAN-ID
    ws.cvtra05y.tranRecord.tranId.setValue(ws.cvtra06y.dalytranRecord.dalytranId);
    // COB(426): MOVE  DALYTRAN-TYPE-CD       TO    TRAN-TYPE-CD
    ws.cvtra05y.tranRecord.tranTypeCd.setValue(ws.cvtra06y.dalytranRecord.dalytranTypeCd);
    // COB(427): MOVE  DALYTRAN-CAT-CD        TO    TRAN-CAT-CD
    ws.cvtra05y.tranRecord.tranCatCd.setValue(ws.cvtra06y.dalytranRecord.dalytranCatCd);
    // COB(428): MOVE  DALYTRAN-SOURCE        TO    TRAN-SOURCE
    ws.cvtra05y.tranRecord.tranSource.setValue(ws.cvtra06y.dalytranRecord.dalytranSource);
    // COB(429): MOVE  DALYTRAN-DESC          TO    TRAN-DESC
    ws.cvtra05y.tranRecord.tranDesc.setValue(ws.cvtra06y.dalytranRecord.dalytranDesc);
    // COB(430): MOVE  DALYTRAN-AMT           TO    TRAN-AMT
    ws.cvtra05y.tranRecord.tranAmt.setValue(ws.cvtra06y.dalytranRecord.dalytranAmt);
    // COB(431): MOVE  DALYTRAN-MERCHANT-ID   TO    TRAN-MERCHANT-ID
    ws.cvtra05y.tranRecord.tranMerchantId.setValue(ws.cvtra06y.dalytranRecord.dalytranMerchantId);
    // COB(432): MOVE  DALYTRAN-MERCHANT-NAME TO    TRAN-MERCHANT-NAME
    ws.cvtra05y.tranRecord.tranMerchantName.setValue(
        ws.cvtra06y.dalytranRecord.dalytranMerchantName);
    // COB(433): MOVE  DALYTRAN-MERCHANT-CITY TO    TRAN-MERCHANT-CITY
    ws.cvtra05y.tranRecord.tranMerchantCity.setValue(
        ws.cvtra06y.dalytranRecord.dalytranMerchantCity);
    // COB(434): MOVE  DALYTRAN-MERCHANT-ZIP  TO    TRAN-MERCHANT-ZIP
    ws.cvtra05y.tranRecord.tranMerchantZip.setValue(ws.cvtra06y.dalytranRecord.dalytranMerchantZip);
    // COB(435): MOVE  DALYTRAN-CARD-NUM      TO    TRAN-CARD-NUM
    ws.cvtra05y.tranRecord.tranCardNum.setValue(ws.cvtra06y.dalytranRecord.dalytranCardNum);
    // COB(436): MOVE  DALYTRAN-ORIG-TS       TO    TRAN-ORIG-TS
    ws.cvtra05y.tranRecord.tranOrigTs.setValue(ws.cvtra06y.dalytranRecord.dalytranOrigTs);
    // COB(437): PERFORM Z-GET-DB2-FORMAT-TIMESTAMP
    zGetDb2FormatTimestamp();
    // COB(438): MOVE  DB2-FORMAT-TS          TO    TRAN-PROC-TS
    ws.cvtra05y.tranRecord.tranProcTs.setValue(ws.db2FormatTs);
    // COB(440): PERFORM 2700-UPDATE-TCATBAL
    _2700UpdateTcatbal();
    // COB(441): PERFORM 2800-UPDATE-ACCOUNT-REC
    _2800UpdateAccountRec();
    // COB(442): PERFORM 2900-WRITE-TRANSACTION-FILE
    _2900WriteTransactionFile();
    // COB(444): EXIT.
    return;
  }

  protected void _2500WriteRejectRec() {
    // COB(447): MOVE DALYTRAN-RECORD TO REJECT-TRAN-DATA
    ws.rejectRecord.rejectTranData.setValue(ws.cvtra06y.dalytranRecord);
    // COB(448): MOVE WS-VALIDATION-TRAILER TO VALIDATION-TRAILER
    ws.rejectRecord.validationTrailer.setValue(ws.wsValidationTrailer);
    // COB(450): MOVE 8 TO APPL-RESULT
    //      DISPLAY '***' REJECT-RECORD
    ws.applResult.setValue(8);
    // COB(451): WRITE FD-REJS-RECORD FROM REJECT-RECORD
    dalyrejsFile.write(ws.rejectRecord);
    // COB(452): IF DALYREJS-STATUS = '00'
    if (ws.dalyrejsStatus.equals("00")) {
      // COB(453): MOVE 0 TO  APPL-RESULT
      ws.applResult.setValue(0);
      // COB(454): ELSE
    } else {
      // COB(455): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(456): END-IF
    }
    // COB(457): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(458): CONTINUE
      // do nothing
      // COB(459): ELSE
    } else {
      // COB(460): DISPLAY 'ERROR WRITING TO REJECTS FILE'
      sysout.display("ERROR WRITING TO REJECTS FILE");
      // COB(461): MOVE DALYREJS-STATUS  TO IO-STATUS
      ws.ioStatus.setValue(ws.dalyrejsStatus);
      // COB(462): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(463): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(464): END-IF
    }
    // COB(465): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _2700UpdateTcatbal() {
    // COB(469): MOVE XREF-ACCT-ID TO FD-TRANCAT-ACCT-ID
    //  Update the balances in transaction balance file.
    fs.fdTranCatBalRecord.fdTranCatKey.fdTrancatAcctId.setValue(
        ws.cvact03y.cardXrefRecord.xrefAcctId);
    // COB(470): MOVE DALYTRAN-TYPE-CD TO FD-TRANCAT-TYPE-CD
    fs.fdTranCatBalRecord.fdTranCatKey.fdTrancatTypeCd.setValue(
        ws.cvtra06y.dalytranRecord.dalytranTypeCd);
    // COB(471): MOVE DALYTRAN-CAT-CD TO FD-TRANCAT-CD
    fs.fdTranCatBalRecord.fdTranCatKey.fdTrancatCd.setValue(
        ws.cvtra06y.dalytranRecord.dalytranCatCd);
    // COB(473): MOVE 'N' TO WS-CREATE-TRANCAT-REC
    ws.wsFlags.wsCreateTrancatRec.setString("N");
    // COB(474): READ TCATBAL-FILE INTO TRAN-CAT-BAL-RECORD
    // COB(474):    INVALID KEY
    // COB(474):      DISPLAY 'TCATBAL record not found for key : '
    // COB(474):         FD-TRAN-CAT-KEY '.. Creating.'
    // COB(474):      MOVE 'Y' TO WS-CREATE-TRANCAT-REC
    // COB(474): END-READ.
    tcatbalFile
        .readInto(ws.cvtra01y.tranCatBalRecord) //
        .invalidKey(this::tcatbalFileReadInvalidKey);
    // COB(481): IF  TCATBALF-STATUS = '00'  OR '23'
    if (ws.tcatbalfStatus.equals("00") || ws.tcatbalfStatus.equals("23")) {
      // COB(482): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(483): ELSE
    } else {
      // COB(484): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(485): END-IF
    }
    // COB(486): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(487): CONTINUE
      // do nothing
      // COB(488): ELSE
    } else {
      // COB(489): DISPLAY 'ERROR READING TRANSACTION BALANCE FILE'
      sysout.display("ERROR READING TRANSACTION BALANCE FILE");
      // COB(490): MOVE TCATBALF-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.tcatbalfStatus);
      // COB(491): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(492): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(493): END-IF.
    }
    // COB(495): IF WS-CREATE-TRANCAT-REC = 'Y'
    if (ws.wsFlags.wsCreateTrancatRec.equals("Y")) {
      // COB(496): PERFORM 2700-A-CREATE-TCATBAL-REC
      _2700ACreateTcatbalRec();
      // COB(497): ELSE
    } else {
      // COB(498): PERFORM 2700-B-UPDATE-TCATBAL-REC
      _2700BUpdateTcatbalRec();
      // COB(499): END-IF
    }
    // COB(501): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _2700ACreateTcatbalRec() {
    // COB(504): INITIALIZE TRAN-CAT-BAL-RECORD
    ws.cvtra01y.tranCatBalRecord.initialize();
    // COB(505): MOVE XREF-ACCT-ID TO TRANCAT-ACCT-ID
    ws.cvtra01y.tranCatBalRecord.tranCatKey.trancatAcctId.setValue(
        ws.cvact03y.cardXrefRecord.xrefAcctId);
    // COB(506): MOVE DALYTRAN-TYPE-CD TO TRANCAT-TYPE-CD
    ws.cvtra01y.tranCatBalRecord.tranCatKey.trancatTypeCd.setValue(
        ws.cvtra06y.dalytranRecord.dalytranTypeCd);
    // COB(507): MOVE DALYTRAN-CAT-CD TO TRANCAT-CD
    ws.cvtra01y.tranCatBalRecord.tranCatKey.trancatCd.setValue(
        ws.cvtra06y.dalytranRecord.dalytranCatCd);
    // COB(508): ADD DALYTRAN-AMT TO TRAN-CAT-BAL
    ws.cvtra01y.tranCatBalRecord.tranCatBal.add(ws.cvtra06y.dalytranRecord.dalytranAmt);
    // COB(510): WRITE FD-TRAN-CAT-BAL-RECORD FROM TRAN-CAT-BAL-RECORD
    tcatbalFile.write(ws.cvtra01y.tranCatBalRecord);
    // COB(512): IF  TCATBALF-STATUS = '00'
    if (ws.tcatbalfStatus.equals("00")) {
      // COB(513): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(514): ELSE
    } else {
      // COB(515): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(516): END-IF
    }
    // COB(517): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(518): CONTINUE
      // do nothing
      // COB(519): ELSE
    } else {
      // COB(520): DISPLAY 'ERROR WRITING TRANSACTION BALANCE FILE'
      sysout.display("ERROR WRITING TRANSACTION BALANCE FILE");
      // COB(521): MOVE TCATBALF-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.tcatbalfStatus);
      // COB(522): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(523): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(524): END-IF.
    }
  }

  /***---------------------------------------------------------------*/
  protected void _2700BUpdateTcatbalRec() {
    // COB(527): ADD DALYTRAN-AMT TO TRAN-CAT-BAL
    ws.cvtra01y.tranCatBalRecord.tranCatBal.add(ws.cvtra06y.dalytranRecord.dalytranAmt);
    // COB(528): REWRITE FD-TRAN-CAT-BAL-RECORD FROM TRAN-CAT-BAL-RECORD
    tcatbalFile.rewrite(ws.cvtra01y.tranCatBalRecord);
    // COB(530): IF  TCATBALF-STATUS = '00'
    if (ws.tcatbalfStatus.equals("00")) {
      // COB(531): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(532): ELSE
    } else {
      // COB(533): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(534): END-IF
    }
    // COB(535): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(536): CONTINUE
      // do nothing
      // COB(537): ELSE
    } else {
      // COB(538): DISPLAY 'ERROR REWRITING TRANSACTION BALANCE FILE'
      sysout.display("ERROR REWRITING TRANSACTION BALANCE FILE");
      // COB(539): MOVE TCATBALF-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.tcatbalfStatus);
      // COB(540): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(541): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(542): END-IF.
    }
  }

  /***---------------------------------------------------------------*/
  protected void _2800UpdateAccountRec() {
    // COB(547): ADD DALYTRAN-AMT  TO ACCT-CURR-BAL
    //  Update the balances in account record to reflect posted trans.
    ws.cvact01y.accountRecord.acctCurrBal.add(ws.cvtra06y.dalytranRecord.dalytranAmt);
    // COB(548): IF DALYTRAN-AMT >= 0
    if (ws.cvtra06y.dalytranRecord.dalytranAmt.greaterEqualThan(0)) {
      // COB(549): ADD DALYTRAN-AMT TO ACCT-CURR-CYC-CREDIT
      ws.cvact01y.accountRecord.acctCurrCycCredit.add(ws.cvtra06y.dalytranRecord.dalytranAmt);
      // COB(550): ELSE
    } else {
      // COB(551): ADD DALYTRAN-AMT TO ACCT-CURR-CYC-DEBIT
      ws.cvact01y.accountRecord.acctCurrCycDebit.add(ws.cvtra06y.dalytranRecord.dalytranAmt);
      // COB(552): END-IF
    }
    // COB(554): REWRITE FD-ACCTFILE-REC FROM  ACCOUNT-RECORD
    // COB(554):    INVALID KEY
    // COB(554):      MOVE 109 TO WS-VALIDATION-FAIL-REASON
    // COB(554):      MOVE 'ACCOUNT RECORD NOT FOUND'
    // COB(554):        TO WS-VALIDATION-FAIL-REASON-DESC
    // COB(554): END-REWRITE.
    accountFile
        .rewrite(ws.cvact01y.accountRecord) //
        .invalidKey(this::accountFileRewriteInvalidKey);
    // COB(560): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _2900WriteTransactionFile() {
    // COB(563): MOVE 8 TO  APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(564): WRITE FD-TRANFILE-REC FROM TRAN-RECORD
    transactFile.write(ws.cvtra05y.tranRecord);
    // COB(566): IF  TRANFILE-STATUS = '00'
    if (ws.tranfileStatus.equals("00")) {
      // COB(567): MOVE 0 TO  APPL-RESULT
      ws.applResult.setValue(0);
      // COB(568): ELSE
    } else {
      // COB(569): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(570): END-IF
    }
    // COB(571): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(572): CONTINUE
      // do nothing
      // COB(573): ELSE
    } else {
      // COB(574): DISPLAY 'ERROR WRITING TO TRANSACTION FILE'
      sysout.display("ERROR WRITING TO TRANSACTION FILE");
      // COB(575): MOVE TRANFILE-STATUS  TO IO-STATUS
      ws.ioStatus.setValue(ws.tranfileStatus);
      // COB(576): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(577): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(578): END-IF
    }
    // COB(579): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9000DalytranClose() {
    // COB(583): MOVE 8 TO  APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(584): CLOSE DALYTRAN-FILE
    dalytranFile.close();
    // COB(585): IF  DALYTRAN-STATUS = '00'
    if (ws.dalytranStatus.equals("00")) {
      // COB(586): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(587): ELSE
    } else {
      // COB(588): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(589): END-IF
    }
    // COB(590): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(591): CONTINUE
      // do nothing
      // COB(592): ELSE
    } else {
      // COB(593): DISPLAY 'ERROR CLOSING DALYTRAN FILE'
      sysout.display("ERROR CLOSING DALYTRAN FILE");
      // COB(594): MOVE DALYTRAN-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.dalytranStatus);
      // COB(595): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(596): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(597): END-IF
    }
    // COB(598): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9100TranfileClose() {
    // COB(601): MOVE 8 TO  APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(602): CLOSE TRANSACT-FILE
    transactFile.close();
    // COB(603): IF  TRANFILE-STATUS = '00'
    if (ws.tranfileStatus.equals("00")) {
      // COB(604): MOVE 0 TO  APPL-RESULT
      ws.applResult.setValue(0);
      // COB(605): ELSE
    } else {
      // COB(606): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(607): END-IF
    }
    // COB(608): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(609): CONTINUE
      // do nothing
      // COB(610): ELSE
    } else {
      // COB(611): DISPLAY 'ERROR CLOSING TRANSACTION FILE'
      sysout.display("ERROR CLOSING TRANSACTION FILE");
      // COB(612): MOVE TRANFILE-STATUS  TO IO-STATUS
      ws.ioStatus.setValue(ws.tranfileStatus);
      // COB(613): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(614): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(615): END-IF
    }
    // COB(616): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9200XreffileClose() {
    // COB(620): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(621): CLOSE XREF-FILE
    xrefFile.close();
    // COB(622): IF  XREFFILE-STATUS = '00'
    if (ws.xreffileStatus.equals("00")) {
      // COB(623): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(624): ELSE
    } else {
      // COB(625): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(626): END-IF
    }
    // COB(627): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(628): CONTINUE
      // do nothing
      // COB(629): ELSE
    } else {
      // COB(630): DISPLAY 'ERROR CLOSING CROSS REF FILE'
      sysout.display("ERROR CLOSING CROSS REF FILE");
      // COB(631): MOVE XREFFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.xreffileStatus);
      // COB(632): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(633): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(634): END-IF
    }
    // COB(635): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9300DalyrejsClose() {
    // COB(638): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(639): CLOSE DALYREJS-FILE
    dalyrejsFile.close();
    // COB(640): IF  DALYREJS-STATUS = '00'
    if (ws.dalyrejsStatus.equals("00")) {
      // COB(641): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(642): ELSE
    } else {
      // COB(643): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(644): END-IF
    }
    // COB(645): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(646): CONTINUE
      // do nothing
      // COB(647): ELSE
    } else {
      // COB(648): DISPLAY 'ERROR CLOSING DAILY REJECTS FILE'
      sysout.display("ERROR CLOSING DAILY REJECTS FILE");
      // COB(649): MOVE XREFFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.xreffileStatus);
      // COB(650): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(651): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(652): END-IF
    }
    // COB(653): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9400AcctfileClose() {
    // COB(656): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(657): CLOSE ACCOUNT-FILE
    accountFile.close();
    // COB(658): IF  ACCTFILE-STATUS  = '00'
    if (ws.acctfileStatus.equals("00")) {
      // COB(659): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(660): ELSE
    } else {
      // COB(661): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(662): END-IF
    }
    // COB(663): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(664): CONTINUE
      // do nothing
      // COB(665): ELSE
    } else {
      // COB(666): DISPLAY 'ERROR CLOSING ACCOUNT FILE'
      sysout.display("ERROR CLOSING ACCOUNT FILE");
      // COB(667): MOVE ACCTFILE-STATUS  TO IO-STATUS
      ws.ioStatus.setValue(ws.acctfileStatus);
      // COB(668): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(669): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(670): END-IF
    }
    // COB(671): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9500TcatbalfClose() {
    // COB(675): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(676): CLOSE TCATBAL-FILE
    tcatbalFile.close();
    // COB(677): IF  TCATBALF-STATUS  = '00'
    if (ws.tcatbalfStatus.equals("00")) {
      // COB(678): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(679): ELSE
    } else {
      // COB(680): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(681): END-IF
    }
    // COB(682): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(683): CONTINUE
      // do nothing
      // COB(684): ELSE
    } else {
      // COB(685): DISPLAY 'ERROR CLOSING TRANSACTION BALANCE FILE'
      sysout.display("ERROR CLOSING TRANSACTION BALANCE FILE");
      // COB(686): MOVE TCATBALF-STATUS  TO IO-STATUS
      ws.ioStatus.setValue(ws.tcatbalfStatus);
      // COB(687): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(688): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(689): END-IF
    }
    // COB(690): EXIT.
    return;
  }

  protected void zGetDb2FormatTimestamp() {
    // COB(693): MOVE FUNCTION CURRENT-DATE TO COBOL-TS
    ws.cobolTs.setValue(environment.now().toCurrentDate());
    // COB(694): MOVE COB-YYYY TO DB2-YYYY
    ws.filler160.db2Yyyy.setValue(ws.cobolTs.cobYyyy);
    // COB(695): MOVE COB-MM   TO DB2-MM
    ws.filler160.db2Mm.setValue(ws.cobolTs.cobMm);
    // COB(696): MOVE COB-DD   TO DB2-DD
    ws.filler160.db2Dd.setValue(ws.cobolTs.cobDd);
    // COB(697): MOVE COB-HH   TO DB2-HH
    ws.filler160.db2Hh.setValue(ws.cobolTs.cobHh);
    // COB(698): MOVE COB-MIN  TO DB2-MIN
    ws.filler160.db2Min.setValue(ws.cobolTs.cobMin);
    // COB(699): MOVE COB-SS   TO DB2-SS
    ws.filler160.db2Ss.setValue(ws.cobolTs.cobSs);
    // COB(700): MOVE COB-MIL  TO DB2-MIL
    ws.filler160.db2Mil.setValue(ws.cobolTs.cobMil);
    // COB(701): MOVE '0000'   TO DB2-REST
    ws.filler160.db2Rest.setString("0000");
    // COB(702): MOVE '-' TO DB2-STREEP-1 DB2-STREEP-2 DB2-STREEP-3
    ws.filler160.db2Streep1.setString("-");
    ws.filler160.db2Streep2.setString("-");
    ws.filler160.db2Streep3.setString("-");
    // COB(703): MOVE '.' TO DB2-DOT-1 DB2-DOT-2 DB2-DOT-3
    ws.filler160.db2Dot1.setString(".");
    ws.filler160.db2Dot2.setString(".");
    ws.filler160.db2Dot3.setString(".");
    // COB(705): EXIT.
    //     DISPLAY 'DB2-TIMESTAMP = ' DB2-FORMAT-TS
    return;
  }

  protected void _9999AbendProgram() {
    // COB(708): DISPLAY 'ABENDING PROGRAM'
    sysout.display("ABENDING PROGRAM");
    // COB(709): MOVE 0 TO TIMING
    ws.timing.setValue(0);
    // COB(710): MOVE 999 TO ABCODE
    ws.abcode.setValue(999);
    // COB(711): CALL 'CEE3ABD'.
    throw new AbendException(ws.abcode.getInt());
  }

  /*****************************************************************/
  protected void _9910DisplayIoStatus() {
    // COB(715): IF  IO-STATUS NOT NUMERIC
    // COB(715): OR  IO-STAT1 = '9'
    if (!ws.ioStatus.isNumeric() || ws.ioStatus.ioStat1.equals("9")) {
      // COB(717): MOVE IO-STAT1 TO IO-STATUS-04(1:1)
      ws.ioStatus04.setValue(ws.ioStatus.ioStat1, 0, 1);
      // COB(718): MOVE 0        TO TWO-BYTES-BINARY
      ws.twoBytesBinary.setValue(0);
      // COB(719): MOVE IO-STAT2 TO TWO-BYTES-RIGHT
      ws.twoBytesAlpha.twoBytesRight.setValue(ws.ioStatus.ioStat2);
      // COB(720): MOVE TWO-BYTES-BINARY TO IO-STATUS-0403
      ws.ioStatus04.ioStatus0403.setValue(ws.twoBytesBinary);
      // COB(721): DISPLAY 'FILE STATUS IS: NNNN' IO-STATUS-04
      sysout.display("FILE STATUS IS: NNNN", ws.ioStatus04);
      // COB(722): ELSE
    } else {
      // COB(723): MOVE '0000' TO IO-STATUS-04
      ws.ioStatus04.setString("0000");
      // COB(724): MOVE IO-STATUS TO IO-STATUS-04(3:2)
      ws.ioStatus04.setValue(ws.ioStatus, 0, 2, 2);
      // COB(725): DISPLAY 'FILE STATUS IS: NNNN' IO-STATUS-04
      sysout.display("FILE STATUS IS: NNNN", ws.ioStatus04);
      // COB(726): END-IF
    }
    // COB(727): EXIT.
    return;
  }

  protected void xrefFileReadInvalidKey() {
    // COB(385): MOVE 100 TO WS-VALIDATION-FAIL-REASON
    ws.wsValidationTrailer.wsValidationFailReason.setValue(100);
    // COB(386): MOVE 'INVALID CARD NUMBER FOUND'
    // COB(386):   TO WS-VALIDATION-FAIL-REASON-DESC
    ws.wsValidationTrailer.wsValidationFailReasonDesc.setString("INVALID CARD NUMBER FOUND");
  }

  protected void xrefFileReadNotInvalidKey() {
    // COB(390): CONTINUE
    //            DISPLAY 'ACCOUNT RECORD FOUND'
    // do nothing
  }

  protected void accountFileReadInvalidKey() {
    // COB(397): MOVE 101 TO WS-VALIDATION-FAIL-REASON
    ws.wsValidationTrailer.wsValidationFailReason.setValue(101);
    // COB(398): MOVE 'ACCOUNT RECORD NOT FOUND'
    // COB(398):   TO WS-VALIDATION-FAIL-REASON-DESC
    ws.wsValidationTrailer.wsValidationFailReasonDesc.setString("ACCOUNT RECORD NOT FOUND");
  }

  protected void accountFileReadNotInvalidKey() {
    // COB(403): COMPUTE WS-TEMP-BAL = ACCT-CURR-CYC-CREDIT
    // COB(403):                     - ACCT-CURR-CYC-DEBIT
    // COB(403):                     + DALYTRAN-AMT
    //          DISPLAY 'ACCT-CREDIT-LIMIT:' ACCT-CREDIT-LIMIT
    //          DISPLAY 'TRAN-AMT         :' DALYTRAN-AMT
    ws.wsCounters.wsTempBal.setValue(
        ws.cvact01y
            .accountRecord
            .acctCurrCycCredit
            .getValue()
            .subtract(ws.cvact01y.accountRecord.acctCurrCycDebit.getValue())
            .add(ws.cvtra06y.dalytranRecord.dalytranAmt.getValue()));
    // COB(407): IF ACCT-CREDIT-LIMIT >= WS-TEMP-BAL
    if (ws.cvact01y.accountRecord.acctCreditLimit.greaterEqualThan(ws.wsCounters.wsTempBal)) {
      // COB(408): CONTINUE
      // do nothing
      // COB(409): ELSE
    } else {
      // COB(410): MOVE 102 TO WS-VALIDATION-FAIL-REASON
      ws.wsValidationTrailer.wsValidationFailReason.setValue(102);
      // COB(411): MOVE 'OVERLIMIT TRANSACTION'
      ws.wsValidationTrailer.wsValidationFailReasonDesc.setString("OVERLIMIT TRANSACTION");
      // COB(413): END-IF
    }
    // COB(414): IF ACCT-EXPIRAION-DATE >= DALYTRAN-ORIG-TS (1:10)
    if (ws.cvact01y.accountRecord.acctExpiraionDate.greaterEqualThan(
        ws.cvtra06y.dalytranRecord.dalytranOrigTs.getString(0, 10))) {
      // COB(415): CONTINUE
      // do nothing
      // COB(416): ELSE
    } else {
      // COB(417): MOVE 103 TO WS-VALIDATION-FAIL-REASON
      ws.wsValidationTrailer.wsValidationFailReason.setValue(103);
      // COB(418): MOVE 'TRANSACTION RECEIVED AFTER ACCT EXPIRATION'
      ws.wsValidationTrailer.wsValidationFailReasonDesc.setString(
          "TRANSACTION RECEIVED AFTER ACCT EXPIRATION");
      // COB(420): END-IF
    }
  }

  protected void tcatbalFileReadInvalidKey() {
    // COB(476): DISPLAY 'TCATBAL record not found for key : '
    // COB(476):    FD-TRAN-CAT-KEY '.. Creating.'
    sysout.display(
        "TCATBAL record not found for key : ", fs.fdTranCatBalRecord.fdTranCatKey, ".. Creating.");
    // COB(478): MOVE 'Y' TO WS-CREATE-TRANCAT-REC
    ws.wsFlags.wsCreateTrancatRec.setString("Y");
  }

  protected void accountFileRewriteInvalidKey() {
    // COB(556): MOVE 109 TO WS-VALIDATION-FAIL-REASON
    ws.wsValidationTrailer.wsValidationFailReason.setValue(109);
    // COB(557): MOVE 'ACCOUNT RECORD NOT FOUND'
    // COB(557):   TO WS-VALIDATION-FAIL-REASON-DESC
    ws.wsValidationTrailer.wsValidationFailReasonDesc.setString("ACCOUNT RECORD NOT FOUND");
  }
}
