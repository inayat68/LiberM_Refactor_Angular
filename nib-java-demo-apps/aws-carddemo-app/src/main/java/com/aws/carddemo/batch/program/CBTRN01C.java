package com.aws.carddemo.batch.program;

import com.aws.carddemo.batch.program.storage.cbtrn01c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.io.*;
import com.nib.commons.io.vsam.*;
import com.nib.commons.storage.*;

public class CBTRN01C extends AbstractProgram {
  private SequentialFile dalytranFile;
  private KeySequentialFile customerFile;
  private KeySequentialFile xrefFile;
  private KeySequentialFile cardFile;
  private KeySequentialFile accountFile;
  private KeySequentialFile transactFile;
  final Cbtrn01cRecords fs = new Cbtrn01cRecords();

  @ProgramStorage final Cbtrn01cWorking ws = new Cbtrn01cWorking();

  // Linkage
  public static class Cbtrn01cLinkage extends NGroup {}

  final Cbtrn01cLinkage params = new Cbtrn01cLinkage();

  public CBTRN01C(Context context) {
    super(context);
  }

  @ProgramInit
  protected void initialize() {
    dalytranFile =
        new SequentialFile(context, "DALYTRAN") //
            .fileStatusIs(ws.dalytranStatus) //
            .recordIs(fs.fdTranRecord);
    customerFile =
        new KeySequentialFile(context, "CUSTFILE") //
            .recordKeyIs(fs.fdCustfileRec.fdCustId) //
            .fileStatusIs(ws.custfileStatus) //
            .recordIs(fs.fdCustfileRec);
    xrefFile =
        new KeySequentialFile(context, "XREFFILE") //
            .recordKeyIs(fs.fdXreffileRec.fdXrefCardNum) //
            .fileStatusIs(ws.xreffileStatus) //
            .recordIs(fs.fdXreffileRec);
    cardFile =
        new KeySequentialFile(context, "CARDFILE") //
            .recordKeyIs(fs.fdCardfileRec.fdCardNum) //
            .fileStatusIs(ws.cardfileStatus) //
            .recordIs(fs.fdCardfileRec);
    accountFile =
        new KeySequentialFile(context, "ACCTFILE") //
            .recordKeyIs(fs.fdAcctfileRec.fdAcctId) //
            .fileStatusIs(ws.acctfileStatus) //
            .recordIs(fs.fdAcctfileRec);
    transactFile =
        new KeySequentialFile(context, "TRANFILE") //
            .recordKeyIs(fs.fdTranfileRec.fdTransId) //
            .fileStatusIs(ws.tranfileStatus) //
            .recordIs(fs.fdTranfileRec);
  }

  @Override
  protected int procedure(AbstractNField... args) throws ContextException {
    if (args != null) {
      params.allocate();
    }
    mainPara();
    return RETURN_CODE;
  }

  protected void mainPara() {
    // COB(156): DISPLAY 'START OF EXECUTION OF PROGRAM CBTRN01C'.
    sysout.display("START OF EXECUTION OF PROGRAM CBTRN01C");
    // COB(157): PERFORM 0000-DALYTRAN-OPEN.
    _0000DalytranOpen();
    // COB(158): PERFORM 0100-CUSTFILE-OPEN.
    _0100CustfileOpen();
    // COB(159): PERFORM 0200-XREFFILE-OPEN.
    _0200XreffileOpen();
    // COB(160): PERFORM 0300-CARDFILE-OPEN.
    _0300CardfileOpen();
    // COB(161): PERFORM 0400-ACCTFILE-OPEN.
    _0400AcctfileOpen();
    // COB(162): PERFORM 0500-TRANFILE-OPEN.
    _0500TranfileOpen();
    // COB(164): PERFORM UNTIL END-OF-DAILY-TRANS-FILE = 'Y'
    while (!ws.endOfDailyTransFile.equals("Y")) {
      // COB(165): IF  END-OF-DAILY-TRANS-FILE = 'N'
      if (ws.endOfDailyTransFile.equals("N")) {
        // COB(166): PERFORM 1000-DALYTRAN-GET-NEXT
        _1000DalytranGetNext();
        // COB(167): IF  END-OF-DAILY-TRANS-FILE = 'N'
        if (ws.endOfDailyTransFile.equals("N")) {
          // COB(168): DISPLAY DALYTRAN-RECORD
          sysout.display(ws.cvtra06y.dalytranRecord);
          // COB(169): END-IF
        }
        // COB(170): MOVE 0                 TO WS-XREF-READ-STATUS
        ws.wsMiscVariables.wsXrefReadStatus.setValue(0);
        // COB(171): MOVE DALYTRAN-CARD-NUM TO XREF-CARD-NUM
        ws.cvact03y.cardXrefRecord.xrefCardNum.setValue(ws.cvtra06y.dalytranRecord.dalytranCardNum);
        // COB(172): PERFORM 2000-LOOKUP-XREF
        _2000LookupXref();
        // COB(173): IF WS-XREF-READ-STATUS = 0
        if (ws.wsMiscVariables.wsXrefReadStatus.equals(0)) {
          // COB(174): MOVE 0            TO WS-ACCT-READ-STATUS
          ws.wsMiscVariables.wsAcctReadStatus.setValue(0);
          // COB(175): MOVE XREF-ACCT-ID TO ACCT-ID
          ws.cvact01y.accountRecord.acctId.setValue(ws.cvact03y.cardXrefRecord.xrefAcctId);
          // COB(176): PERFORM 3000-READ-ACCOUNT
          _3000ReadAccount();
          // COB(177): IF WS-ACCT-READ-STATUS NOT = 0
          if (!ws.wsMiscVariables.wsAcctReadStatus.equals(0)) {
            // COB(178): DISPLAY 'ACCOUNT ' ACCT-ID ' NOT FOUND'
            sysout.display("ACCOUNT ", ws.cvact01y.accountRecord.acctId, " NOT FOUND");
            // COB(179): END-IF
          }
          // COB(180): ELSE
        } else {
          // COB(181): DISPLAY 'CARD NUMBER ' DALYTRAN-CARD-NUM
          // COB(181): ' COULD NOT BE VERIFIED. SKIPPING TRANSACTION ID-'
          // COB(181): DALYTRAN-ID
          sysout.display(
              "CARD NUMBER ",
              ws.cvtra06y.dalytranRecord.dalytranCardNum,
              " COULD NOT BE VERIFIED. SKIPPING TRANSACTION ID-",
              ws.cvtra06y.dalytranRecord.dalytranId);
          // COB(184): END-IF
        }
        // COB(185): END-IF
      }
      // COB(186): END-PERFORM.
    }
    // COB(188): PERFORM 9000-DALYTRAN-CLOSE.
    _9000DalytranClose();
    // COB(189): PERFORM 9100-CUSTFILE-CLOSE.
    _9100CustfileClose();
    // COB(190): PERFORM 9200-XREFFILE-CLOSE.
    _9200XreffileClose();
    // COB(191): PERFORM 9300-CARDFILE-CLOSE.
    _9300CardfileClose();
    // COB(192): PERFORM 9400-ACCTFILE-CLOSE.
    _9400AcctfileClose();
    // COB(193): PERFORM 9500-TRANFILE-CLOSE.
    _9500TranfileClose();
    // COB(195): DISPLAY 'END OF EXECUTION OF PROGRAM CBTRN01C'.
    sysout.display("END OF EXECUTION OF PROGRAM CBTRN01C");
    // COB(197): GOBACK.
    context.goback();
  }

  /***
   *****************************************************************
   * READS FILE                                                    *
   *****************************************************************/
  protected void _1000DalytranGetNext() {
    // COB(203): READ DALYTRAN-FILE INTO DALYTRAN-RECORD.
    dalytranFile.readNext(ws.cvtra06y.dalytranRecord);
    // COB(204): IF  DALYTRAN-STATUS = '00'
    if (ws.dalytranStatus.equals("00")) {
      // COB(205): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(206): ELSE
    } else {
      // COB(207): IF  DALYTRAN-STATUS = '10'
      if (ws.dalytranStatus.equals("10")) {
        // COB(208): MOVE 16 TO APPL-RESULT
        ws.applResult.setValue(16);
        // COB(209): ELSE
      } else {
        // COB(210): MOVE 12 TO APPL-RESULT
        ws.applResult.setValue(12);
        // COB(211): END-IF
      }
      // COB(212): END-IF
    }
    // COB(213): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(214): CONTINUE
      // do nothing
      // COB(215): ELSE
    } else {
      // COB(216): IF  APPL-EOF
      if (ws.applEof()) {
        // COB(217): MOVE 'Y' TO END-OF-DAILY-TRANS-FILE
        ws.endOfDailyTransFile.setString("Y");
        // COB(218): ELSE
      } else {
        // COB(219): DISPLAY 'ERROR READING DAILY TRANSACTION FILE'
        sysout.display("ERROR READING DAILY TRANSACTION FILE");
        // COB(220): MOVE DALYTRAN-STATUS TO IO-STATUS
        ws.ioStatus.setValue(ws.dalytranStatus);
        // COB(221): PERFORM Z-DISPLAY-IO-STATUS
        zDisplayIoStatus();
        // COB(222): PERFORM Z-ABEND-PROGRAM
        zAbendProgram();
        // COB(223): END-IF
      }
      // COB(224): END-IF
    }
    // COB(225): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _2000LookupXref() {
    // COB(228): MOVE XREF-CARD-NUM TO FD-XREF-CARD-NUM
    fs.fdXreffileRec.fdXrefCardNum.setValue(ws.cvact03y.cardXrefRecord.xrefCardNum);
    // COB(229): READ XREF-FILE  RECORD INTO CARD-XREF-RECORD
    // COB(229): KEY IS FD-XREF-CARD-NUM
    // COB(229):      INVALID KEY
    // COB(229):        DISPLAY 'INVALID CARD NUMBER FOR XREF'
    // COB(229):        MOVE 4 TO WS-XREF-READ-STATUS
    // COB(229):      NOT INVALID KEY
    // COB(229):        DISPLAY 'SUCCESSFUL READ OF XREF'
    // COB(229):        DISPLAY 'CARD NUMBER: ' XREF-CARD-NUM
    // COB(229):        DISPLAY 'ACCOUNT ID : ' XREF-ACCT-ID
    // COB(229):        DISPLAY 'CUSTOMER ID: ' XREF-CUST-ID
    // COB(229): END-READ.
    xrefFile
        .read(ws.cvact03y.cardXrefRecord, fs.fdXreffileRec.fdXrefCardNum) //
        .invalidKey(this::xrefFileReadInvalidKey) //
        .notInvalidKey(this::xrefFileReadNotInvalidKey);
  }

  /***---------------------------------------------------------------*/
  protected void _3000ReadAccount() {
    // COB(242): MOVE ACCT-ID TO FD-ACCT-ID
    fs.fdAcctfileRec.fdAcctId.setValue(ws.cvact01y.accountRecord.acctId);
    // COB(243): READ ACCOUNT-FILE RECORD INTO ACCOUNT-RECORD
    // COB(243): KEY IS FD-ACCT-ID
    // COB(243):      INVALID KEY
    // COB(243):        DISPLAY 'INVALID ACCOUNT NUMBER FOUND'
    // COB(243):        MOVE 4 TO WS-ACCT-READ-STATUS
    // COB(243):      NOT INVALID KEY
    // COB(243):        DISPLAY 'SUCCESSFUL READ OF ACCOUNT FILE'
    // COB(243): END-READ.
    accountFile
        .read(ws.cvact01y.accountRecord, fs.fdAcctfileRec.fdAcctId) //
        .invalidKey(this::accountFileReadInvalidKey) //
        .notInvalidKey(this::accountFileReadNotInvalidKey);
  }

  /***---------------------------------------------------------------*/
  protected void _0000DalytranOpen() {
    // COB(253): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(254): OPEN INPUT DALYTRAN-FILE
    dalytranFile.open(OpenMode.Input);
    // COB(255): IF  DALYTRAN-STATUS = '00'
    if (ws.dalytranStatus.equals("00")) {
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
      // COB(263): DISPLAY 'ERROR OPENING DAILY TRANSACTION FILE'
      sysout.display("ERROR OPENING DAILY TRANSACTION FILE");
      // COB(264): MOVE DALYTRAN-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.dalytranStatus);
      // COB(265): PERFORM Z-DISPLAY-IO-STATUS
      zDisplayIoStatus();
      // COB(266): PERFORM Z-ABEND-PROGRAM
      zAbendProgram();
      // COB(267): END-IF
    }
    // COB(268): EXIT.
    return;
  }

  /***
   *---------------------------------------------------------------*/
  protected void _0100CustfileOpen() {
    // COB(272): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(273): OPEN INPUT CUSTOMER-FILE
    customerFile.open(OpenMode.Input);
    // COB(274): IF  CUSTFILE-STATUS = '00'
    if (ws.custfileStatus.equals("00")) {
      // COB(275): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(276): ELSE
    } else {
      // COB(277): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(278): END-IF
    }
    // COB(279): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(280): CONTINUE
      // do nothing
      // COB(281): ELSE
    } else {
      // COB(282): DISPLAY 'ERROR OPENING CUSTOMER FILE'
      sysout.display("ERROR OPENING CUSTOMER FILE");
      // COB(283): MOVE CUSTFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.custfileStatus);
      // COB(284): PERFORM Z-DISPLAY-IO-STATUS
      zDisplayIoStatus();
      // COB(285): PERFORM Z-ABEND-PROGRAM
      zAbendProgram();
      // COB(286): END-IF
    }
    // COB(287): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0200XreffileOpen() {
    // COB(290): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(291): OPEN INPUT XREF-FILE
    xrefFile.open(OpenMode.Input);
    // COB(292): IF  XREFFILE-STATUS = '00'
    if (ws.xreffileStatus.equals("00")) {
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
      // COB(300): DISPLAY 'ERROR OPENING CROSS REF FILE'
      sysout.display("ERROR OPENING CROSS REF FILE");
      // COB(301): MOVE XREFFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.xreffileStatus);
      // COB(302): PERFORM Z-DISPLAY-IO-STATUS
      zDisplayIoStatus();
      // COB(303): PERFORM Z-ABEND-PROGRAM
      zAbendProgram();
      // COB(304): END-IF
    }
    // COB(305): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0300CardfileOpen() {
    // COB(308): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(309): OPEN INPUT CARD-FILE
    cardFile.open(OpenMode.Input);
    // COB(310): IF  CARDFILE-STATUS = '00'
    if (ws.cardfileStatus.equals("00")) {
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
      // COB(318): DISPLAY 'ERROR OPENING CARD FILE'
      sysout.display("ERROR OPENING CARD FILE");
      // COB(319): MOVE CARDFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.cardfileStatus);
      // COB(320): PERFORM Z-DISPLAY-IO-STATUS
      zDisplayIoStatus();
      // COB(321): PERFORM Z-ABEND-PROGRAM
      zAbendProgram();
      // COB(322): END-IF
    }
    // COB(323): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0400AcctfileOpen() {
    // COB(326): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(327): OPEN INPUT ACCOUNT-FILE
    accountFile.open(OpenMode.Input);
    // COB(328): IF  ACCTFILE-STATUS = '00'
    if (ws.acctfileStatus.equals("00")) {
      // COB(329): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(330): ELSE
    } else {
      // COB(331): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(332): END-IF
    }
    // COB(333): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(334): CONTINUE
      // do nothing
      // COB(335): ELSE
    } else {
      // COB(336): DISPLAY 'ERROR OPENING ACCOUNT FILE'
      sysout.display("ERROR OPENING ACCOUNT FILE");
      // COB(337): MOVE ACCTFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.acctfileStatus);
      // COB(338): PERFORM Z-DISPLAY-IO-STATUS
      zDisplayIoStatus();
      // COB(339): PERFORM Z-ABEND-PROGRAM
      zAbendProgram();
      // COB(340): END-IF
    }
    // COB(341): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0500TranfileOpen() {
    // COB(344): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(345): OPEN INPUT TRANSACT-FILE
    transactFile.open(OpenMode.Input);
    // COB(346): IF  TRANFILE-STATUS = '00'
    if (ws.tranfileStatus.equals("00")) {
      // COB(347): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(348): ELSE
    } else {
      // COB(349): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(350): END-IF
    }
    // COB(351): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(352): CONTINUE
      // do nothing
      // COB(353): ELSE
    } else {
      // COB(354): DISPLAY 'ERROR OPENING TRANSACTION FILE'
      sysout.display("ERROR OPENING TRANSACTION FILE");
      // COB(355): MOVE TRANFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.tranfileStatus);
      // COB(356): PERFORM Z-DISPLAY-IO-STATUS
      zDisplayIoStatus();
      // COB(357): PERFORM Z-ABEND-PROGRAM
      zAbendProgram();
      // COB(358): END-IF
    }
    // COB(359): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9000DalytranClose() {
    // COB(362): ADD 8 TO ZERO GIVING APPL-RESULT.
    ws.applResult.add(8);
    // COB(363): CLOSE DALYTRAN-FILE
    dalytranFile.close();
    // COB(364): IF  DALYTRAN-STATUS = '00'
    if (ws.dalytranStatus.equals("00")) {
      // COB(365): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(366): ELSE
    } else {
      // COB(367): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(368): END-IF
    }
    // COB(369): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(370): CONTINUE
      // do nothing
      // COB(371): ELSE
    } else {
      // COB(372): DISPLAY 'ERROR CLOSING CUSTOMER FILE'
      sysout.display("ERROR CLOSING CUSTOMER FILE");
      // COB(373): MOVE CUSTFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.custfileStatus);
      // COB(374): PERFORM Z-DISPLAY-IO-STATUS
      zDisplayIoStatus();
      // COB(375): PERFORM Z-ABEND-PROGRAM
      zAbendProgram();
      // COB(376): END-IF
    }
    // COB(377): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9100CustfileClose() {
    // COB(380): ADD 8 TO ZERO GIVING APPL-RESULT.
    ws.applResult.add(8);
    // COB(381): CLOSE CUSTOMER-FILE
    customerFile.close();
    // COB(382): IF  CUSTFILE-STATUS = '00'
    if (ws.custfileStatus.equals("00")) {
      // COB(383): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(384): ELSE
    } else {
      // COB(385): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(386): END-IF
    }
    // COB(387): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(388): CONTINUE
      // do nothing
      // COB(389): ELSE
    } else {
      // COB(390): DISPLAY 'ERROR CLOSING CUSTOMER FILE'
      sysout.display("ERROR CLOSING CUSTOMER FILE");
      // COB(391): MOVE CUSTFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.custfileStatus);
      // COB(392): PERFORM Z-DISPLAY-IO-STATUS
      zDisplayIoStatus();
      // COB(393): PERFORM Z-ABEND-PROGRAM
      zAbendProgram();
      // COB(394): END-IF
    }
    // COB(395): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9200XreffileClose() {
    // COB(398): ADD 8 TO ZERO GIVING APPL-RESULT.
    ws.applResult.add(8);
    // COB(399): CLOSE XREF-FILE
    xrefFile.close();
    // COB(400): IF  XREFFILE-STATUS = '00'
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
      // COB(408): DISPLAY 'ERROR CLOSING CROSS REF FILE'
      sysout.display("ERROR CLOSING CROSS REF FILE");
      // COB(409): MOVE XREFFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.xreffileStatus);
      // COB(410): PERFORM Z-DISPLAY-IO-STATUS
      zDisplayIoStatus();
      // COB(411): PERFORM Z-ABEND-PROGRAM
      zAbendProgram();
      // COB(412): END-IF
    }
    // COB(413): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9300CardfileClose() {
    // COB(416): ADD 8 TO ZERO GIVING APPL-RESULT.
    ws.applResult.add(8);
    // COB(417): CLOSE CARD-FILE
    cardFile.close();
    // COB(418): IF  CARDFILE-STATUS = '00'
    if (ws.cardfileStatus.equals("00")) {
      // COB(419): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(420): ELSE
    } else {
      // COB(421): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(422): END-IF
    }
    // COB(423): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(424): CONTINUE
      // do nothing
      // COB(425): ELSE
    } else {
      // COB(426): DISPLAY 'ERROR CLOSING CARD FILE'
      sysout.display("ERROR CLOSING CARD FILE");
      // COB(427): MOVE CARDFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.cardfileStatus);
      // COB(428): PERFORM Z-DISPLAY-IO-STATUS
      zDisplayIoStatus();
      // COB(429): PERFORM Z-ABEND-PROGRAM
      zAbendProgram();
      // COB(430): END-IF
    }
    // COB(431): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9400AcctfileClose() {
    // COB(434): ADD 8 TO ZERO GIVING APPL-RESULT.
    ws.applResult.add(8);
    // COB(435): CLOSE ACCOUNT-FILE
    accountFile.close();
    // COB(436): IF  ACCTFILE-STATUS = '00'
    if (ws.acctfileStatus.equals("00")) {
      // COB(437): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(438): ELSE
    } else {
      // COB(439): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(440): END-IF
    }
    // COB(441): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(442): CONTINUE
      // do nothing
      // COB(443): ELSE
    } else {
      // COB(444): DISPLAY 'ERROR CLOSING ACCOUNT FILE'
      sysout.display("ERROR CLOSING ACCOUNT FILE");
      // COB(445): MOVE ACCTFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.acctfileStatus);
      // COB(446): PERFORM Z-DISPLAY-IO-STATUS
      zDisplayIoStatus();
      // COB(447): PERFORM Z-ABEND-PROGRAM
      zAbendProgram();
      // COB(448): END-IF
    }
    // COB(449): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9500TranfileClose() {
    // COB(452): ADD 8 TO ZERO GIVING APPL-RESULT.
    ws.applResult.add(8);
    // COB(453): CLOSE TRANSACT-FILE
    transactFile.close();
    // COB(454): IF  TRANFILE-STATUS = '00'
    if (ws.tranfileStatus.equals("00")) {
      // COB(455): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(456): ELSE
    } else {
      // COB(457): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(458): END-IF
    }
    // COB(459): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(460): CONTINUE
      // do nothing
      // COB(461): ELSE
    } else {
      // COB(462): DISPLAY 'ERROR CLOSING TRANSACTION FILE'
      sysout.display("ERROR CLOSING TRANSACTION FILE");
      // COB(463): MOVE TRANFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.tranfileStatus);
      // COB(464): PERFORM Z-DISPLAY-IO-STATUS
      zDisplayIoStatus();
      // COB(465): PERFORM Z-ABEND-PROGRAM
      zAbendProgram();
      // COB(466): END-IF
    }
    // COB(467): EXIT.
    return;
  }

  /***/
  protected void zAbendProgram() {
    // COB(470): DISPLAY 'ABENDING PROGRAM'
    sysout.display("ABENDING PROGRAM");
    // COB(471): MOVE 0 TO TIMING
    ws.timing.setValue(0);
    // COB(472): MOVE 999 TO ABCODE
    ws.abcode.setValue(999);
    // COB(473): CALL 'CEE3ABD'.
    throw new AbendException(ws.abcode.getInt());
  }

  /***
   *****************************************************************/
  protected void zDisplayIoStatus() {
    // COB(477): IF  IO-STATUS NOT NUMERIC
    // COB(477): OR  IO-STAT1 = '9'
    if (!ws.ioStatus.isNumeric() || ws.ioStatus.ioStat1.equals("9")) {
      // COB(479): MOVE IO-STAT1 TO IO-STATUS-04(1:1)
      ws.ioStatus04.setValue(ws.ioStatus.ioStat1, 0, 1);
      // COB(480): MOVE 0        TO TWO-BYTES-BINARY
      ws.twoBytesBinary.setValue(0);
      // COB(481): MOVE IO-STAT2 TO TWO-BYTES-RIGHT
      ws.twoBytesAlpha.twoBytesRight.setValue(ws.ioStatus.ioStat2);
      // COB(482): MOVE TWO-BYTES-BINARY TO IO-STATUS-0403
      ws.ioStatus04.ioStatus0403.setValue(ws.twoBytesBinary);
      // COB(483): DISPLAY 'FILE STATUS IS: NNNN' IO-STATUS-04
      sysout.display("FILE STATUS IS: NNNN", ws.ioStatus04);
      // COB(484): ELSE
    } else {
      // COB(485): MOVE '0000' TO IO-STATUS-04
      ws.ioStatus04.setString("0000");
      // COB(486): MOVE IO-STATUS TO IO-STATUS-04(3:2)
      ws.ioStatus04.setValue(ws.ioStatus, 0, 2, 2);
      // COB(487): DISPLAY 'FILE STATUS IS: NNNN' IO-STATUS-04
      sysout.display("FILE STATUS IS: NNNN", ws.ioStatus04);
      // COB(488): END-IF
    }
    // COB(489): EXIT.
    return;
  }

  protected void xrefFileReadInvalidKey() {
    // COB(232): DISPLAY 'INVALID CARD NUMBER FOR XREF'
    sysout.display("INVALID CARD NUMBER FOR XREF");
    // COB(233): MOVE 4 TO WS-XREF-READ-STATUS
    ws.wsMiscVariables.wsXrefReadStatus.setValue(4);
  }

  protected void xrefFileReadNotInvalidKey() {
    // COB(235): DISPLAY 'SUCCESSFUL READ OF XREF'
    sysout.display("SUCCESSFUL READ OF XREF");
    // COB(236): DISPLAY 'CARD NUMBER: ' XREF-CARD-NUM
    sysout.display("CARD NUMBER: ", ws.cvact03y.cardXrefRecord.xrefCardNum);
    // COB(237): DISPLAY 'ACCOUNT ID : ' XREF-ACCT-ID
    sysout.display("ACCOUNT ID : ", ws.cvact03y.cardXrefRecord.xrefAcctId);
    // COB(238): DISPLAY 'CUSTOMER ID: ' XREF-CUST-ID
    sysout.display("CUSTOMER ID: ", ws.cvact03y.cardXrefRecord.xrefCustId);
  }

  protected void accountFileReadInvalidKey() {
    // COB(246): DISPLAY 'INVALID ACCOUNT NUMBER FOUND'
    sysout.display("INVALID ACCOUNT NUMBER FOUND");
    // COB(247): MOVE 4 TO WS-ACCT-READ-STATUS
    ws.wsMiscVariables.wsAcctReadStatus.setValue(4);
  }

  protected void accountFileReadNotInvalidKey() {
    // COB(249): DISPLAY 'SUCCESSFUL READ OF ACCOUNT FILE'
    sysout.display("SUCCESSFUL READ OF ACCOUNT FILE");
  }
}
