package com.aws.carddemo.batch.program;

import com.aws.carddemo.batch.program.storage.cbact04c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.io.*;
import com.nib.commons.io.vsam.*;
import com.nib.commons.storage.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CBACT04C extends AbstractProgram {
  private KeySequentialFile tcatbalFile;
  private KeySequentialFile xrefFile;
  private KeySequentialFile accountFile;
  private KeySequentialFile discgrpFile;
  private SequentialFile transactFile;
  final Cbact04cRecords fs = new Cbact04cRecords();

  @ProgramStorage final Cbact04cWorking ws = new Cbact04cWorking();

  // Linkage
  public static class Cbact04cLinkage extends NGroup {
    // COB:        01  EXTERNAL-PARMS.
    public ExternalParms externalParms = new ExternalParms();

    public static class ExternalParms extends NGroup {

      // COB:            05  PARM-LENGTH         PIC S9(04) COMP.
      public NInt16 parmLength = new NInt16();
      // COB:            05  PARM-DATE           PIC X(10).
      public NChar parmDate = new NChar(10);
    }
  }

  final Cbact04cLinkage params = new Cbact04cLinkage();

  public CBACT04C(Context context) {
    super(context);
  }

  @ProgramInit
  protected void initialize() {
    tcatbalFile =
        new KeySequentialFile(context, "TCATBALF") //
            .recordKeyIs(fs.fdTranCatBalRecord.fdTranCatKey) //
            .fileStatusIs(ws.tcatbalfStatus) //
            .recordIs(fs.fdTranCatBalRecord);
    xrefFile =
        new KeySequentialFile(context, "XREFFILE") //
            .recordKeyIs(fs.fdXreffileRec.fdXrefCardNum) //
            .alternateRecordKeyIs(fs.fdXreffileRec.fdXrefAcctId) //
            .fileStatusIs(ws.xreffileStatus) //
            .recordIs(fs.fdXreffileRec);
    accountFile =
        new KeySequentialFile(context, "ACCTFILE") //
            .recordKeyIs(fs.fdAcctfileRec.fdAcctId) //
            .fileStatusIs(ws.acctfileStatus) //
            .recordIs(fs.fdAcctfileRec);
    discgrpFile =
        new KeySequentialFile(context, "DISCGRP") //
            .recordKeyIs(fs.fdDiscgrpRec.fdDiscgrpKey) //
            .fileStatusIs(ws.discgrpStatus) //
            .recordIs(fs.fdDiscgrpRec);
    transactFile =
        new SequentialFile(context, "TRANSACT") //
            .fileStatusIs(ws.tranfileStatus) //
            .recordIs(fs.fdTranfileRec);
  }

  @Override
  protected int procedure(AbstractNField... args) throws ContextException {
    if (args != null) {
      params.allocate();
      if (args.length > 0) params.externalParms.setAddress(args[0].getAddress());
    }
    procedure();
    return RETURN_CODE;
  }

  /*****************************************************************/
  protected void procedure() {
    // COB(181): DISPLAY 'START OF EXECUTION OF PROGRAM CBACT04C'.
    sysout.display("START OF EXECUTION OF PROGRAM CBACT04C");
    // COB(182): PERFORM 0000-TCATBALF-OPEN.
    _0000TcatbalfOpen();
    // COB(183): PERFORM 0100-XREFFILE-OPEN.
    _0100XreffileOpen();
    // COB(184): PERFORM 0200-DISCGRP-OPEN.
    _0200DiscgrpOpen();
    // COB(185): PERFORM 0300-ACCTFILE-OPEN.
    _0300AcctfileOpen();
    // COB(186): PERFORM 0400-TRANFILE-OPEN.
    _0400TranfileOpen();
    // COB(188): PERFORM UNTIL END-OF-FILE = 'Y'
    while (!ws.endOfFile.equals("Y")) {
      // COB(189): IF  END-OF-FILE = 'N'
      if (ws.endOfFile.equals("N")) {
        // COB(190): PERFORM 1000-TCATBALF-GET-NEXT
        _1000TcatbalfGetNext();
        // COB(191): IF  END-OF-FILE = 'N'
        if (ws.endOfFile.equals("N")) {
          // COB(192): ADD 1 TO WS-RECORD-COUNT
          ws.wsCounters.wsRecordCount.add(1);
          // COB(193): DISPLAY TRAN-CAT-BAL-RECORD
          sysout.display(ws.cvtra01y.tranCatBalRecord);
          // COB(194): IF TRANCAT-ACCT-ID NOT= WS-LAST-ACCT-NUM
          if (!ws.cvtra01y.tranCatBalRecord.tranCatKey.trancatAcctId.equals(
              ws.wsMiscVars.wsLastAcctNum)) {
            // COB(195): IF WS-FIRST-TIME NOT = 'Y'
            if (!ws.wsMiscVars.wsFirstTime.equals("Y")) {
              // COB(196): PERFORM 1050-UPDATE-ACCOUNT
              _1050UpdateAccount();
              // COB(197): ELSE
            } else {
              // COB(198): MOVE 'N' TO WS-FIRST-TIME
              ws.wsMiscVars.wsFirstTime.setString("N");
              // COB(199): END-IF
            }
            // COB(200): MOVE 0 TO WS-TOTAL-INT
            ws.wsMiscVars.wsTotalInt.setValue(0);
            // COB(201): MOVE TRANCAT-ACCT-ID TO WS-LAST-ACCT-NUM
            ws.wsMiscVars.wsLastAcctNum.setValue(
                ws.cvtra01y.tranCatBalRecord.tranCatKey.trancatAcctId);
            // COB(202): MOVE TRANCAT-ACCT-ID TO FD-ACCT-ID
            fs.fdAcctfileRec.fdAcctId.setValue(
                ws.cvtra01y.tranCatBalRecord.tranCatKey.trancatAcctId);
            // COB(203): PERFORM 1100-GET-ACCT-DATA
            _1100GetAcctData();
            // COB(204): MOVE TRANCAT-ACCT-ID TO FD-XREF-ACCT-ID
            fs.fdXreffileRec.fdXrefAcctId.setValue(
                ws.cvtra01y.tranCatBalRecord.tranCatKey.trancatAcctId);
            // COB(205): PERFORM 1110-GET-XREF-DATA
            _1110GetXrefData();
            // COB(206): END-IF
          }
          // COB(210): MOVE ACCT-GROUP-ID TO FD-DIS-ACCT-GROUP-ID
          //               DISPLAY 'ACCT-GROUP-ID: ' ACCT-GROUP-ID
          //               DISPLAY 'TRANCAT-CD: ' TRANCAT-CD
          //               DISPLAY 'TRANCAT-TYPE-CD: ' TRANCAT-TYPE-CD
          fs.fdDiscgrpRec.fdDiscgrpKey.fdDisAcctGroupId.setValue(
              ws.cvact01y.accountRecord.acctGroupId);
          // COB(211): MOVE TRANCAT-CD TO FD-DIS-TRAN-CAT-CD
          fs.fdDiscgrpRec.fdDiscgrpKey.fdDisTranCatCd.setValue(
              ws.cvtra01y.tranCatBalRecord.tranCatKey.trancatCd);
          // COB(212): MOVE TRANCAT-TYPE-CD TO FD-DIS-TRAN-TYPE-CD
          fs.fdDiscgrpRec.fdDiscgrpKey.fdDisTranTypeCd.setValue(
              ws.cvtra01y.tranCatBalRecord.tranCatKey.trancatTypeCd);
          // COB(213): PERFORM 1200-GET-INTEREST-RATE
          _1200GetInterestRate();
          // COB(214): IF DIS-INT-RATE NOT = 0
          if (!ws.cvtra02y.disGroupRecord.disIntRate.equals(0)) {
            // COB(215): PERFORM 1300-COMPUTE-INTEREST
            _1300ComputeInterest();
            // COB(216): PERFORM 1400-COMPUTE-FEES
            _1400ComputeFees();
            // COB(217): END-IF
          }
          // COB(218): END-IF
        }
        // COB(219): ELSE
      } else {
        // COB(220): PERFORM 1050-UPDATE-ACCOUNT
        _1050UpdateAccount();
        // COB(221): END-IF
      }
      // COB(222): END-PERFORM.
    }
    // COB(224): PERFORM 9000-TCATBALF-CLOSE.
    _9000TcatbalfClose();
    // COB(225): PERFORM 9100-XREFFILE-CLOSE.
    _9100XreffileClose();
    // COB(226): PERFORM 9200-DISCGRP-CLOSE.
    _9200DiscgrpClose();
    // COB(227): PERFORM 9300-ACCTFILE-CLOSE.
    _9300AcctfileClose();
    // COB(228): PERFORM 9400-TRANFILE-CLOSE.
    _9400TranfileClose();
    // COB(230): DISPLAY 'END OF EXECUTION OF PROGRAM CBACT04C'.
    sysout.display("END OF EXECUTION OF PROGRAM CBACT04C");
    // COB(232): GOBACK.
    context.goback();
  }

  /***---------------------------------------------------------------*/
  protected void _0000TcatbalfOpen() {
    // COB(235): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(236): OPEN INPUT TCATBAL-FILE
    tcatbalFile.open(OpenMode.Input);
    // COB(237): IF  TCATBALF-STATUS = '00'
    if (ws.tcatbalfStatus.equals("00")) {
      // COB(238): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(239): ELSE
    } else {
      // COB(240): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(241): END-IF
    }
    // COB(242): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(243): CONTINUE
      // do nothing
      // COB(244): ELSE
    } else {
      // COB(245): DISPLAY 'ERROR OPENING TRANSACTION CATEGORY BALANCE'
      sysout.display("ERROR OPENING TRANSACTION CATEGORY BALANCE");
      // COB(246): MOVE TCATBALF-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.tcatbalfStatus);
      // COB(247): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(248): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(249): END-IF
    }
    // COB(250): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0100XreffileOpen() {
    // COB(253): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(254): OPEN INPUT XREF-FILE
    xrefFile.open(OpenMode.Input);
    // COB(255): IF  XREFFILE-STATUS = '00'
    if (ws.xreffileStatus.equals("00")) {
      // COB(256): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(257): ELSE
    } else {
      // COB(258): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(259): END-IF
    }
    // COB(260): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(261): CONTINUE
      // do nothing
      // COB(262): ELSE
    } else {
      // COB(263): DISPLAY 'ERROR OPENING CROSS REF FILE'   XREFFILE-STATUS
      sysout.display("ERROR OPENING CROSS REF FILE", ws.xreffileStatus);
      // COB(264): MOVE XREFFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.xreffileStatus);
      // COB(265): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(266): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(267): END-IF
    }
    // COB(268): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0200DiscgrpOpen() {
    // COB(271): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(272): OPEN INPUT DISCGRP-FILE
    discgrpFile.open(OpenMode.Input);
    // COB(273): IF  DISCGRP-STATUS = '00'
    if (ws.discgrpStatus.equals("00")) {
      // COB(274): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(275): ELSE
    } else {
      // COB(276): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(277): END-IF
    }
    // COB(278): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(279): CONTINUE
      // do nothing
      // COB(280): ELSE
    } else {
      // COB(281): DISPLAY 'ERROR OPENING DALY REJECTS FILE'
      sysout.display("ERROR OPENING DALY REJECTS FILE");
      // COB(282): MOVE DISCGRP-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.discgrpStatus);
      // COB(283): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(284): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(285): END-IF
    }
    // COB(286): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0300AcctfileOpen() {
    // COB(290): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(291): OPEN I-O ACCOUNT-FILE
    accountFile.open(OpenMode.InputOutput);
    // COB(292): IF  ACCTFILE-STATUS = '00'
    if (ws.acctfileStatus.equals("00")) {
      // COB(293): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(294): ELSE
    } else {
      // COB(295): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(296): END-IF
    }
    // COB(297): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(298): CONTINUE
      // do nothing
      // COB(299): ELSE
    } else {
      // COB(300): DISPLAY 'ERROR OPENING ACCOUNT MASTER FILE'
      sysout.display("ERROR OPENING ACCOUNT MASTER FILE");
      // COB(301): MOVE ACCTFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.acctfileStatus);
      // COB(302): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(303): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(304): END-IF
    }
    // COB(305): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0400TranfileOpen() {
    // COB(308): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(309): OPEN OUTPUT TRANSACT-FILE
    transactFile.open(OpenMode.Output);
    // COB(310): IF  TRANFILE-STATUS = '00'
    if (ws.tranfileStatus.equals("00")) {
      // COB(311): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(312): ELSE
    } else {
      // COB(313): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(314): END-IF
    }
    // COB(315): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(316): CONTINUE
      // do nothing
      // COB(317): ELSE
    } else {
      // COB(318): DISPLAY 'ERROR OPENING TRANSACTION FILE'
      sysout.display("ERROR OPENING TRANSACTION FILE");
      // COB(319): MOVE TRANFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.tranfileStatus);
      // COB(320): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(321): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(322): END-IF
    }
    // COB(323): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1000TcatbalfGetNext() {
    // COB(326): READ TCATBAL-FILE INTO TRAN-CAT-BAL-RECORD.
    tcatbalFile.readNext(ws.cvtra01y.tranCatBalRecord);
    // COB(327): IF  TCATBALF-STATUS  = '00'
    if (ws.tcatbalfStatus.equals("00")) {
      // COB(328): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(329): ELSE
    } else {
      // COB(330): IF  TCATBALF-STATUS  = '10'
      if (ws.tcatbalfStatus.equals("10")) {
        // COB(331): MOVE 16 TO APPL-RESULT
        ws.applResult.setValue(16);
        // COB(332): ELSE
      } else {
        // COB(333): MOVE 12 TO APPL-RESULT
        ws.applResult.setValue(12);
        // COB(334): END-IF
      }
      // COB(335): END-IF
    }
    // COB(336): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(337): CONTINUE
      // do nothing
      // COB(338): ELSE
    } else {
      // COB(339): IF  APPL-EOF
      if (ws.applEof()) {
        // COB(340): MOVE 'Y' TO END-OF-FILE
        ws.endOfFile.setString("Y");
        // COB(341): ELSE
      } else {
        // COB(342): DISPLAY 'ERROR READING TRANSACTION CATEGORY FILE'
        sysout.display("ERROR READING TRANSACTION CATEGORY FILE");
        // COB(343): MOVE TCATBALF-STATUS TO IO-STATUS
        ws.ioStatus.setValue(ws.tcatbalfStatus);
        // COB(344): PERFORM 9910-DISPLAY-IO-STATUS
        _9910DisplayIoStatus();
        // COB(345): PERFORM 9999-ABEND-PROGRAM
        _9999AbendProgram();
        // COB(346): END-IF
      }
      // COB(347): END-IF
    }
    // COB(348): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1050UpdateAccount() {
    // COB(352): ADD WS-TOTAL-INT  TO ACCT-CURR-BAL
    //  Update the balances in account record to reflect posted trans.
    ws.cvact01y.accountRecord.acctCurrBal.add(ws.wsMiscVars.wsTotalInt);
    // COB(353): MOVE 0 TO ACCT-CURR-CYC-CREDIT
    ws.cvact01y.accountRecord.acctCurrCycCredit.setValue(0);
    // COB(354): MOVE 0 TO ACCT-CURR-CYC-DEBIT
    ws.cvact01y.accountRecord.acctCurrCycDebit.setValue(0);
    // COB(356): REWRITE FD-ACCTFILE-REC FROM  ACCOUNT-RECORD
    accountFile.rewrite(ws.cvact01y.accountRecord);
    // COB(357): IF  ACCTFILE-STATUS  = '00'
    if (ws.acctfileStatus.equals("00")) {
      // COB(358): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(359): ELSE
    } else {
      // COB(360): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(361): END-IF
    }
    // COB(362): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(363): CONTINUE
      // do nothing
      // COB(364): ELSE
    } else {
      // COB(365): DISPLAY 'ERROR RE-WRITING ACCOUNT FILE'
      sysout.display("ERROR RE-WRITING ACCOUNT FILE");
      // COB(366): MOVE ACCTFILE-STATUS  TO IO-STATUS
      ws.ioStatus.setValue(ws.acctfileStatus);
      // COB(367): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(368): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(369): END-IF
    }
    // COB(370): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1100GetAcctData() {
    // COB(373): READ ACCOUNT-FILE INTO ACCOUNT-RECORD
    // COB(373):     INVALID KEY
    // COB(373):        DISPLAY 'ACCOUNT NOT FOUND: ' FD-ACCT-ID
    // COB(373): END-READ
    accountFile
        .readInto(ws.cvact01y.accountRecord) //
        .invalidKey(this::accountFileReadInvalidKey);
    // COB(378): IF  ACCTFILE-STATUS  = '00'
    if (ws.acctfileStatus.equals("00")) {
      // COB(379): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(380): ELSE
    } else {
      // COB(381): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(382): END-IF
    }
    // COB(383): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(384): CONTINUE
      // do nothing
      // COB(385): ELSE
    } else {
      // COB(386): DISPLAY 'ERROR READING ACCOUNT FILE'
      sysout.display("ERROR READING ACCOUNT FILE");
      // COB(387): MOVE ACCTFILE-STATUS  TO IO-STATUS
      ws.ioStatus.setValue(ws.acctfileStatus);
      // COB(388): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(389): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(390): END-IF
    }
    // COB(391): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1110GetXrefData() {
    // COB(394): READ XREF-FILE INTO CARD-XREF-RECORD
    // COB(394):  KEY IS FD-XREF-ACCT-ID
    // COB(394):     INVALID KEY
    // COB(394):        DISPLAY 'ACCOUNT NOT FOUND: ' FD-XREF-ACCT-ID
    // COB(394): END-READ
    xrefFile
        .read(ws.cvact03y.cardXrefRecord, fs.fdXreffileRec.fdXrefAcctId) //
        .invalidKey(this::xrefFileReadInvalidKey);
    // COB(400): IF  XREFFILE-STATUS   = '00'
    if (ws.xreffileStatus.equals("00")) {
      // COB(401): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(402): ELSE
    } else {
      // COB(403): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(404): END-IF
    }
    // COB(405): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(406): CONTINUE
      // do nothing
      // COB(407): ELSE
    } else {
      // COB(408): DISPLAY 'ERROR READING XREF FILE'
      sysout.display("ERROR READING XREF FILE");
      // COB(409): MOVE XREFFILE-STATUS  TO IO-STATUS
      ws.ioStatus.setValue(ws.xreffileStatus);
      // COB(410): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(411): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(412): END-IF
    }
    // COB(413): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1200GetInterestRate() {
    // COB(416): READ DISCGRP-FILE INTO DIS-GROUP-RECORD
    // COB(416):      INVALID KEY
    // COB(416):         DISPLAY 'DISCLOSURE GROUP RECORD MISSING'
    // COB(416):         DISPLAY 'TRY WITH DEFAULT GROUP CODE'
    // COB(416): END-READ.
    discgrpFile
        .readInto(ws.cvtra02y.disGroupRecord) //
        .invalidKey(this::discgrpFileReadInvalidKey);
    // COB(422): IF  DISCGRP-STATUS  = '00'  OR '23'
    if (ws.discgrpStatus.equals("00") || ws.discgrpStatus.equals("23")) {
      // COB(423): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(424): ELSE
    } else {
      // COB(425): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(426): END-IF
    }
    // COB(428): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(429): CONTINUE
      // do nothing
      // COB(430): ELSE
    } else {
      // COB(431): DISPLAY 'ERROR READING DISCLOSURE GROUP FILE'
      sysout.display("ERROR READING DISCLOSURE GROUP FILE");
      // COB(432): MOVE DISCGRP-STATUS  TO IO-STATUS
      ws.ioStatus.setValue(ws.discgrpStatus);
      // COB(433): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(434): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(435): END-IF
    }
    // COB(436): IF  DISCGRP-STATUS  = '23'
    if (ws.discgrpStatus.equals("23")) {
      // COB(437): MOVE 'DEFAULT' TO FD-DIS-ACCT-GROUP-ID
      fs.fdDiscgrpRec.fdDiscgrpKey.fdDisAcctGroupId.setString("DEFAULT");
      // COB(438): PERFORM 1200-A-GET-DEFAULT-INT-RATE
      _1200AGetDefaultIntRate();
      // COB(439): END-IF
    }
    // COB(440): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1200AGetDefaultIntRate() {
    // COB(444): READ DISCGRP-FILE INTO DIS-GROUP-RECORD
    discgrpFile.readInto(ws.cvtra02y.disGroupRecord);
    // COB(446): IF  DISCGRP-STATUS  = '00'
    if (ws.discgrpStatus.equals("00")) {
      // COB(447): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(448): ELSE
    } else {
      // COB(449): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(450): END-IF
    }
    // COB(452): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(453): CONTINUE
      // do nothing
      // COB(454): ELSE
    } else {
      // COB(455): DISPLAY 'ERROR READING DEFAULT DISCLOSURE GROUP'
      sysout.display("ERROR READING DEFAULT DISCLOSURE GROUP");
      // COB(456): MOVE DISCGRP-STATUS  TO IO-STATUS
      ws.ioStatus.setValue(ws.discgrpStatus);
      // COB(457): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(458): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(459): END-IF
    }
    // COB(460): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1300ComputeInterest() {
    // COB(464): COMPUTE WS-MONTHLY-INT
    // COB(464):  = ( TRAN-CAT-BAL * DIS-INT-RATE) / 1200
    ws.wsMiscVars.wsMonthlyInt.setValue(
        ws.cvtra01y
            .tranCatBalRecord
            .tranCatBal
            .getValue()
            .multiply(ws.cvtra02y.disGroupRecord.disIntRate.getValue())
            .divide(new BigDecimal("1200"), 2, RoundingMode.HALF_UP));
    // COB(467): ADD WS-MONTHLY-INT  TO WS-TOTAL-INT
    ws.wsMiscVars.wsTotalInt.add(ws.wsMiscVars.wsMonthlyInt);
    // COB(468): PERFORM 1300-B-WRITE-TX.
    _1300BWriteTx();
    // COB(470): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1300BWriteTx() {
    // COB(474): ADD 1 TO WS-TRANID-SUFFIX
    ws.wsCounters.wsTranidSuffix.add(1);
    // COB(476): STRING PARM-DATE,
    // COB(476):        WS-TRANID-SUFFIX
    // COB(476):   DELIMITED BY SIZE
    // COB(476):   INTO TRAN-ID
    // COB(476): END-STRING.
    ws.cvtra05y.tranRecord.tranId.concat(
        params.externalParms.parmDate, ws.wsCounters.wsTranidSuffix);
    // COB(480): END-STRING.
    // COB(482): MOVE '01'                 TO TRAN-TYPE-CD
    ws.cvtra05y.tranRecord.tranTypeCd.setString("01");
    // COB(483): MOVE '05'                 TO TRAN-CAT-CD
    ws.cvtra05y.tranRecord.tranCatCd.setString("05");
    // COB(484): MOVE 'System'             TO TRAN-SOURCE
    ws.cvtra05y.tranRecord.tranSource.setString("System");
    // COB(485): STRING 'Int. for a/c ' ,
    // COB(485):        ACCT-ID
    // COB(485):        DELIMITED BY SIZE
    // COB(485):  INTO TRAN-DESC
    // COB(485): END-STRING
    ws.cvtra05y.tranRecord.tranDesc.concat("Int. for a/c ", ws.cvact01y.accountRecord.acctId);
    // COB(489): END-STRING
    // COB(490): MOVE WS-MONTHLY-INT       TO TRAN-AMT
    ws.cvtra05y.tranRecord.tranAmt.setValue(ws.wsMiscVars.wsMonthlyInt);
    // COB(491): MOVE 0                    TO TRAN-MERCHANT-ID
    ws.cvtra05y.tranRecord.tranMerchantId.setValue(0);
    // COB(492): MOVE SPACES               TO TRAN-MERCHANT-NAME
    ws.cvtra05y.tranRecord.tranMerchantName.spaces();
    // COB(493): MOVE SPACES               TO TRAN-MERCHANT-CITY
    ws.cvtra05y.tranRecord.tranMerchantCity.spaces();
    // COB(494): MOVE SPACES               TO TRAN-MERCHANT-ZIP
    ws.cvtra05y.tranRecord.tranMerchantZip.spaces();
    // COB(495): MOVE XREF-CARD-NUM        TO TRAN-CARD-NUM
    ws.cvtra05y.tranRecord.tranCardNum.setValue(ws.cvact03y.cardXrefRecord.xrefCardNum);
    // COB(496): PERFORM Z-GET-DB2-FORMAT-TIMESTAMP
    zGetDb2FormatTimestamp();
    // COB(497): MOVE DB2-FORMAT-TS        TO TRAN-ORIG-TS
    ws.cvtra05y.tranRecord.tranOrigTs.setValue(ws.db2FormatTs);
    // COB(498): MOVE DB2-FORMAT-TS        TO TRAN-PROC-TS
    ws.cvtra05y.tranRecord.tranProcTs.setValue(ws.db2FormatTs);
    // COB(500): WRITE FD-TRANFILE-REC FROM TRAN-RECORD
    transactFile.write(ws.cvtra05y.tranRecord);
    // COB(501): IF  TRANFILE-STATUS   = '00'
    if (ws.tranfileStatus.equals("00")) {
      // COB(502): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(503): ELSE
    } else {
      // COB(504): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(505): END-IF
    }
    // COB(507): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(508): CONTINUE
      // do nothing
      // COB(509): ELSE
    } else {
      // COB(510): DISPLAY 'ERROR WRITING TRANSACTION RECORD'
      sysout.display("ERROR WRITING TRANSACTION RECORD");
      // COB(511): MOVE TRANFILE-STATUS   TO IO-STATUS
      ws.ioStatus.setValue(ws.tranfileStatus);
      // COB(512): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(513): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(514): END-IF
    }
    // COB(515): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1400ComputeFees() {
    // COB(520): EXIT.
    //  To be implemented
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9000TcatbalfClose() {
    // COB(523): MOVE 8 TO  APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(524): CLOSE TCATBAL-FILE
    tcatbalFile.close();
    // COB(525): IF  TCATBALF-STATUS = '00'
    if (ws.tcatbalfStatus.equals("00")) {
      // COB(526): MOVE 0 TO  APPL-RESULT
      ws.applResult.setValue(0);
      // COB(527): ELSE
    } else {
      // COB(528): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(529): END-IF
    }
    // COB(530): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(531): CONTINUE
      // do nothing
      // COB(532): ELSE
    } else {
      // COB(533): DISPLAY 'ERROR CLOSING TRANSACTION BALANCE FILE'
      sysout.display("ERROR CLOSING TRANSACTION BALANCE FILE");
      // COB(534): MOVE TCATBALF-STATUS  TO IO-STATUS
      ws.ioStatus.setValue(ws.tcatbalfStatus);
      // COB(535): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(536): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(537): END-IF
    }
    // COB(538): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9100XreffileClose() {
    // COB(542): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(543): CLOSE XREF-FILE
    xrefFile.close();
    // COB(544): IF  XREFFILE-STATUS = '00'
    if (ws.xreffileStatus.equals("00")) {
      // COB(545): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(546): ELSE
    } else {
      // COB(547): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(548): END-IF
    }
    // COB(549): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(550): CONTINUE
      // do nothing
      // COB(551): ELSE
    } else {
      // COB(552): DISPLAY 'ERROR CLOSING CROSS REF FILE'
      sysout.display("ERROR CLOSING CROSS REF FILE");
      // COB(553): MOVE XREFFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.xreffileStatus);
      // COB(554): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(555): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(556): END-IF
    }
    // COB(557): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9200DiscgrpClose() {
    // COB(560): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(561): CLOSE DISCGRP-FILE
    discgrpFile.close();
    // COB(562): IF  DISCGRP-STATUS = '00'
    if (ws.discgrpStatus.equals("00")) {
      // COB(563): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(564): ELSE
    } else {
      // COB(565): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(566): END-IF
    }
    // COB(567): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(568): CONTINUE
      // do nothing
      // COB(569): ELSE
    } else {
      // COB(570): DISPLAY 'ERROR CLOSING DISCLOSURE GROUP FILE'
      sysout.display("ERROR CLOSING DISCLOSURE GROUP FILE");
      // COB(571): MOVE DISCGRP-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.discgrpStatus);
      // COB(572): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(573): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(574): END-IF
    }
    // COB(575): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9300AcctfileClose() {
    // COB(578): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(579): CLOSE ACCOUNT-FILE
    accountFile.close();
    // COB(580): IF  ACCTFILE-STATUS  = '00'
    if (ws.acctfileStatus.equals("00")) {
      // COB(581): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(582): ELSE
    } else {
      // COB(583): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(584): END-IF
    }
    // COB(585): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(586): CONTINUE
      // do nothing
      // COB(587): ELSE
    } else {
      // COB(588): DISPLAY 'ERROR CLOSING ACCOUNT FILE'
      sysout.display("ERROR CLOSING ACCOUNT FILE");
      // COB(589): MOVE ACCTFILE-STATUS  TO IO-STATUS
      ws.ioStatus.setValue(ws.acctfileStatus);
      // COB(590): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(591): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(592): END-IF
    }
    // COB(593): EXIT.
    return;
  }

  protected void _9400TranfileClose() {
    // COB(596): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(597): CLOSE TRANSACT-FILE
    transactFile.close();
    // COB(598): IF  TRANFILE-STATUS  = '00'
    if (ws.tranfileStatus.equals("00")) {
      // COB(599): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(600): ELSE
    } else {
      // COB(601): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(602): END-IF
    }
    // COB(603): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(604): CONTINUE
      // do nothing
      // COB(605): ELSE
    } else {
      // COB(606): DISPLAY 'ERROR CLOSING TRANSACTION FILE'
      sysout.display("ERROR CLOSING TRANSACTION FILE");
      // COB(607): MOVE TRANFILE-STATUS  TO IO-STATUS
      ws.ioStatus.setValue(ws.tranfileStatus);
      // COB(608): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(609): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(610): END-IF
    }
    // COB(611): EXIT.
    return;
  }

  protected void zGetDb2FormatTimestamp() {
    // COB(614): MOVE FUNCTION CURRENT-DATE TO COBOL-TS
    ws.cobolTs.setValue(environment.now().toCurrentDate());
    // COB(615): MOVE COB-YYYY TO DB2-YYYY
    ws.filler151.db2Yyyy.setValue(ws.cobolTs.cobYyyy);
    // COB(616): MOVE COB-MM   TO DB2-MM
    ws.filler151.db2Mm.setValue(ws.cobolTs.cobMm);
    // COB(617): MOVE COB-DD   TO DB2-DD
    ws.filler151.db2Dd.setValue(ws.cobolTs.cobDd);
    // COB(618): MOVE COB-HH   TO DB2-HH
    ws.filler151.db2Hh.setValue(ws.cobolTs.cobHh);
    // COB(619): MOVE COB-MIN  TO DB2-MIN
    ws.filler151.db2Min.setValue(ws.cobolTs.cobMin);
    // COB(620): MOVE COB-SS   TO DB2-SS
    ws.filler151.db2Ss.setValue(ws.cobolTs.cobSs);
    // COB(621): MOVE COB-MIL  TO DB2-MIL
    ws.filler151.db2Mil.setValue(ws.cobolTs.cobMil);
    // COB(622): MOVE '0000'   TO DB2-REST
    ws.filler151.db2Rest.setString("0000");
    // COB(623): MOVE '-' TO DB2-STREEP-1 DB2-STREEP-2 DB2-STREEP-3
    ws.filler151.db2Streep1.setString("-");
    ws.filler151.db2Streep2.setString("-");
    ws.filler151.db2Streep3.setString("-");
    // COB(624): MOVE '.' TO DB2-DOT-1 DB2-DOT-2 DB2-DOT-3
    ws.filler151.db2Dot1.setString(".");
    ws.filler151.db2Dot2.setString(".");
    ws.filler151.db2Dot3.setString(".");
    // COB(626): EXIT.
    //     DISPLAY 'DB2-TIMESTAMP = ' DB2-FORMAT-TS
    return;
  }

  protected void _9999AbendProgram() {
    // COB(629): DISPLAY 'ABENDING PROGRAM'
    sysout.display("ABENDING PROGRAM");
    // COB(630): MOVE 0 TO TIMING
    ws.timing.setValue(0);
    // COB(631): MOVE 999 TO ABCODE
    ws.abcode.setValue(999);
    // COB(632): CALL 'CEE3ABD'.
    throw new AbendException(ws.abcode.getInt());
  }

  /*****************************************************************/
  protected void _9910DisplayIoStatus() {
    // COB(636): IF  IO-STATUS NOT NUMERIC
    // COB(636): OR  IO-STAT1 = '9'
    if (!ws.ioStatus.isNumeric() || ws.ioStatus.ioStat1.equals("9")) {
      // COB(638): MOVE IO-STAT1 TO IO-STATUS-04(1:1)
      ws.ioStatus04.setValue(ws.ioStatus.ioStat1, 0, 1);
      // COB(639): MOVE 0        TO TWO-BYTES-BINARY
      ws.twoBytesBinary.setValue(0);
      // COB(640): MOVE IO-STAT2 TO TWO-BYTES-RIGHT
      ws.twoBytesAlpha.twoBytesRight.setValue(ws.ioStatus.ioStat2);
      // COB(641): MOVE TWO-BYTES-BINARY TO IO-STATUS-0403
      ws.ioStatus04.ioStatus0403.setValue(ws.twoBytesBinary);
      // COB(642): DISPLAY 'FILE STATUS IS: NNNN' IO-STATUS-04
      sysout.display("FILE STATUS IS: NNNN", ws.ioStatus04);
      // COB(643): ELSE
    } else {
      // COB(644): MOVE '0000' TO IO-STATUS-04
      ws.ioStatus04.setString("0000");
      // COB(645): MOVE IO-STATUS TO IO-STATUS-04(3:2)
      ws.ioStatus04.setValue(ws.ioStatus, 0, 2, 2);
      // COB(646): DISPLAY 'FILE STATUS IS: NNNN' IO-STATUS-04
      sysout.display("FILE STATUS IS: NNNN", ws.ioStatus04);
      // COB(647): END-IF
    }
    // COB(648): EXIT.
    return;
  }

  protected void accountFileReadInvalidKey() {
    // COB(375): DISPLAY 'ACCOUNT NOT FOUND: ' FD-ACCT-ID
    sysout.display("ACCOUNT NOT FOUND: ", fs.fdAcctfileRec.fdAcctId);
  }

  protected void xrefFileReadInvalidKey() {
    // COB(397): DISPLAY 'ACCOUNT NOT FOUND: ' FD-XREF-ACCT-ID
    sysout.display("ACCOUNT NOT FOUND: ", fs.fdXreffileRec.fdXrefAcctId);
  }

  protected void discgrpFileReadInvalidKey() {
    // COB(418): DISPLAY 'DISCLOSURE GROUP RECORD MISSING'
    sysout.display("DISCLOSURE GROUP RECORD MISSING");
    // COB(419): DISPLAY 'TRY WITH DEFAULT GROUP CODE'
    sysout.display("TRY WITH DEFAULT GROUP CODE");
  }
}
