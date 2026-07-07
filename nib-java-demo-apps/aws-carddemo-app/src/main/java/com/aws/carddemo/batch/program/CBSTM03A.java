package com.aws.carddemo.batch.program;

import com.aws.carddemo.batch.program.storage.cbstm03a.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.io.*;
import com.nib.commons.io.vsam.*;
import com.nib.commons.storage.*;

public class CBSTM03A extends AbstractProgram {
  private SequentialFile stmtFile;
  private SequentialFile htmlFile;
  final Cbstm03aRecords fs = new Cbstm03aRecords();

  protected enum Flow {
    Exit,
    // 0000-START
    _0000Start,
    // 1000-MAINLINE
    _1000Mainline,
    // 1000-XREFFILE-GET-NEXT
    _1000XreffileGetNext,
    // 2000-CUSTFILE-GET
    _2000CustfileGet,
    // 3000-ACCTFILE-GET
    _3000AcctfileGet,
    // 4000-TRNXFILE-GET
    _4000TrnxfileGet,
    // 5000-CREATE-STATEMENT
    _5000CreateStatement,
    // 5100-WRITE-HTML-HEADER
    _5100WriteHtmlHeader,
    // 5200-WRITE-HTML-NMADBS
    _5200WriteHtmlNmadbs,
    // 6000-WRITE-TRANS
    _6000WriteTrans,
    // 8100-FILE-OPEN
    _8100FileOpen,
    // 8100-TRNXFILE-OPEN
    _8100TrnxfileOpen,
    // 8200-XREFFILE-OPEN
    _8200XreffileOpen,
    // 8300-CUSTFILE-OPEN
    _8300CustfileOpen,
    // 8400-ACCTFILE-OPEN
    _8400AcctfileOpen,
    // 8500-READTRNX-READ
    _8500ReadtrnxRead,
    // 8599-EXIT
    _8599Exit,
    // 9100-TRNXFILE-CLOSE
    _9100TrnxfileClose,
    // 9999-GOBACK
    _9999Goback,
    // PROCEDURE
    procedure
  }

  private Flow rcNext;
  private Flow _8100FileOpenVar; // 8100-FILE-OPEN

  @ProgramStorage final Cbstm03aWorking ws = new Cbstm03aWorking();

  // Linkage
  public static class Cbstm03aLinkage extends NGroup {
    // COB:        01  ALIGN-PSA        PIC 9(16) BINARY.
    public NInt64 alignPsa = new NInt64();
    // COB:        01  PSA-BLOCK.
    public PsaBlock psaBlock = new PsaBlock();

    public static class PsaBlock extends NGroup {

      // COB:            05  FILLER       PIC X(536).
      public NChar filler242 = new NChar(536);
      // COB:            05  TCB-POINT    POINTER.
      public NPointer tcbPoint = new NPointer();
    }

    // COB:        01  TCB-BLOCK.
    public TcbBlock tcbBlock = new TcbBlock();

    public static class TcbBlock extends NGroup {

      // COB:            05  FILLER       PIC X(12).
      public NChar filler245 = new NChar(12);
      // COB:            05  TIOT-POINT   POINTER.
      public NPointer tiotPoint = new NPointer();
    }

    // COB:        01  TIOT-BLOCK.
    public TiotBlock tiotBlock = new TiotBlock();

    public static class TiotBlock extends NGroup {

      // COB:            05  TIOTNJOB     PIC X(08).
      public NChar tiotnjob = new NChar(8);
      // COB:            05  TIOTJSTP     PIC X(08).
      public NChar tiotjstp = new NChar(8);
      // COB:            05  TIOTPSTP     PIC X(08).
      public NChar tiotpstp = new NChar(8);
    }

    // COB:        01  TIOT-ENTRY.
    public TiotEntry tiotEntry = new TiotEntry();

    public static class TiotEntry extends NGroup {

      // COB:            05  TIOT-SEG.
      public static class TiotSeg extends NGroup {
        // COB:                10  TIO-LEN  PIC X(01).
        public NChar tioLen = new NChar(1);
        // COB:                10  FILLER   PIC X(03).
        public NChar filler254 = new NChar(3);
        // COB:                10  TIOCDDNM PIC X(08).
        public NChar tiocddnm = new NChar(8);
        // COB:                10  FILLER   PIC X(05).
        public NChar filler256 = new NChar(5);
        // COB:                10  UCB-ADDR PIC X(03).
        public NChar ucbAddr = new NChar(3);

        // COB:                    88 NULL-UCB    VALUES LOW-VALUES.
        public boolean nullUcb() {
          return ucbAddr.isLowValues();
        }

        public void setNullUcb(boolean value) {
          if (value) ucbAddr.lowValues();
        }
      }

      public TiotSeg tiotSeg = new TiotSeg();
      // COB:            05  FILLER       PIC X(04).
      public NChar filler259 = new NChar(4);

      // COB:              88  END-OF-TIOT      VALUE LOW-VALUES.
      public boolean endOfTiot() {
        return filler259.isLowValues();
      }

      public void setEndOfTiot(boolean value) {
        if (value) filler259.lowValues();
      }
    }
  }

  final Cbstm03aLinkage params = new Cbstm03aLinkage();

  public CBSTM03A(Context context) {
    super(context);
  }

  @ProgramInit
  protected void initialize() {
    stmtFile =
        new SequentialFile(context, "STMTFILE") //
            .recordIs(fs.fdStmtfileRec);
    htmlFile =
        new SequentialFile(context, "HTMLFILE") //
            .recordIs(fs.fdHtmlfileRec);
  }

  @Override
  protected int procedure(AbstractNField... args) throws ContextException {
    if (args != null) {
      params.allocate();
    }
    rcNext = Flow.procedure;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case procedure:
          procedure();
          rcNext = Flow._0000Start;
          break;
        case _0000Start:
          rcNext = _0000Start();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow._1000Mainline;
          }
          break;
        case _8100TrnxfileOpen:
          rcNext = _8100TrnxfileOpen();
          break;
        case _8100FileOpen:
          rcNext = _8100FileOpen();
          break;
        case _8200XreffileOpen:
          rcNext = _8200XreffileOpen();
          break;
        case _8300CustfileOpen:
          rcNext = _8300CustfileOpen();
          break;
        case _8400AcctfileOpen:
          rcNext = _8400AcctfileOpen();
          break;
        case _8500ReadtrnxRead:
          rcNext = _8500ReadtrnxRead();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow._8599Exit;
          }
          break;
        case _1000Mainline:
          _1000Mainline();
          rcNext = Flow._9999Goback;
          break;
        case _9999Goback:
          _9999Goback();
          rcNext = Flow._8599Exit;
          break;
        case _8599Exit:
          rcNext = _8599Exit();
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    return RETURN_CODE;
  }

  /*****************************************************************/
  protected void procedure() {
    // COB(287): IF NOT NULL-UCB
    // ****************************************************************
    //     Check Unit Control blocks                                  *
    // ****************************************************************
    //     SET ADDRESS OF PSA-BLOCK   TO PSAPTR.
    //     SET ADDRESS OF TCB-BLOCK   TO TCB-POINT.
    //     SET ADDRESS OF TIOT-BLOCK  TO TIOT-POINT.
    //     SET TIOT-INDEX             TO TIOT-POINT.
    //     DISPLAY 'Running JCL : ' TIOTNJOB ' Step ' TIOTJSTP.
    //
    //     COMPUTE BUMP-TIOT = BUMP-TIOT + LENGTH OF TIOT-BLOCK.
    //     SET ADDRESS OF TIOT-ENTRY  TO TIOT-INDEX.
    //
    //     DISPLAY 'DD Names from TIOT: '.
    //     PERFORM UNTIL END-OF-TIOT
    //                OR TIO-LEN = LOW-VALUES
    //         IF NOT NULL-UCB
    //             DISPLAY ': ' TIOCDDNM ' -- valid UCB'
    //         ELSE
    //             DISPLAY ': ' TIOCDDNM ' --  null UCB'
    //         END-IF
    //         COMPUTE BUMP-TIOT = BUMP-TIOT + LENGTH OF TIOT-SEG
    //         SET ADDRESS OF TIOT-ENTRY TO TIOT-INDEX
    //     END-PERFORM.
    //
    if (!params.tiotEntry.tiotSeg.nullUcb()) {
      // COB(288): DISPLAY ': ' TIOCDDNM ' -- valid UCB'
      sysout.display(": ", params.tiotEntry.tiotSeg.tiocddnm, " -- valid UCB");
      // COB(289): ELSE
    } else {
      // COB(290): DISPLAY ': ' TIOCDDNM ' -- null  UCB'
      sysout.display(": ", params.tiotEntry.tiotSeg.tiocddnm, " -- null  UCB");
      // COB(291): END-IF.
    }
    // COB(293): OPEN OUTPUT STMT-FILE HTML-FILE.
    stmtFile.open(OpenMode.Output);
    htmlFile.open(OpenMode.Output);
    // COB(294): INITIALIZE WS-TRNX-TABLE WS-TRN-TBL-CNTR.
    ws.wsTrnxTable.initialize();
    ws.wsTrnTblCntr.initialize();
  }

  /***/
  protected Flow _0000Start() {
    // COB(298): EVALUATE WS-FL-DD
    // COB(299): WHEN 'TRNXFILE'
    if (ws.miscVariables.wsFlDd.equals("TRNXFILE")) {
      // COB(300): ALTER 8100-FILE-OPEN TO PROCEED TO 8100-TRNXFILE-OPEN
      // COB(300): GO TO 8100-FILE-OPEN
      _8100FileOpenVar = Flow._8100TrnxfileOpen;
      // COB(301): GO TO 8100-FILE-OPEN
      return _8100FileOpenVar;
      // COB(302): WHEN 'XREFFILE'
    } else if (ws.miscVariables.wsFlDd.equals("XREFFILE")) {
      // COB(303): ALTER 8100-FILE-OPEN TO PROCEED TO 8200-XREFFILE-OPEN
      // COB(303): GO TO 8100-FILE-OPEN
      _8100FileOpenVar = Flow._8200XreffileOpen;
      // COB(304): GO TO 8100-FILE-OPEN
      return _8100FileOpenVar;
      // COB(305): WHEN 'CUSTFILE'
    } else if (ws.miscVariables.wsFlDd.equals("CUSTFILE")) {
      // COB(306): ALTER 8100-FILE-OPEN TO PROCEED TO 8300-CUSTFILE-OPEN
      // COB(306): GO TO 8100-FILE-OPEN
      _8100FileOpenVar = Flow._8300CustfileOpen;
      // COB(307): GO TO 8100-FILE-OPEN
      return _8100FileOpenVar;
      // COB(308): WHEN 'ACCTFILE'
    } else if (ws.miscVariables.wsFlDd.equals("ACCTFILE")) {
      // COB(309): ALTER 8100-FILE-OPEN TO PROCEED TO 8400-ACCTFILE-OPEN
      // COB(309): GO TO 8100-FILE-OPEN
      _8100FileOpenVar = Flow._8400AcctfileOpen;
      // COB(310): GO TO 8100-FILE-OPEN
      return _8100FileOpenVar;
      // COB(311): WHEN 'READTRNX'
    } else if (ws.miscVariables.wsFlDd.equals("READTRNX")) {
      // COB(312): GO TO 8500-READTRNX-READ
      return Flow._8500ReadtrnxRead;
      // COB(313): WHEN OTHER
    } else {
      // COB(314): GO TO 9999-GOBACK
      return Flow._9999Goback;
      // COB(315): END-EVALUATE.
    }
  }

  /***/
  protected void _1000Mainline() {
    // COB(318): PERFORM UNTIL END-OF-FILE = 'Y'
    while (!ws.miscVariables.endOfFile.equals("Y")) {
      // COB(319): IF  END-OF-FILE = 'N'
      if (ws.miscVariables.endOfFile.equals("N")) {
        // COB(320): PERFORM 1000-XREFFILE-GET-NEXT
        _1000XreffileGetNext();
        // COB(321): IF  END-OF-FILE = 'N'
        if (ws.miscVariables.endOfFile.equals("N")) {
          // COB(322): PERFORM 2000-CUSTFILE-GET
          _2000CustfileGet();
          // COB(323): PERFORM 3000-ACCTFILE-GET
          _3000AcctfileGet();
          // COB(324): PERFORM 5000-CREATE-STATEMENT
          _5000CreateStatement();
          // COB(325): MOVE 1 TO CR-JMP
          ws.compVariables.crJmp.setValue(1);
          // COB(326): MOVE ZERO TO WS-TOTAL-AMT
          ws.comp3Variables.wsTotalAmt.zeros();
          // COB(327): PERFORM 4000-TRNXFILE-GET
          _4000TrnxfileGet();
          // COB(328): END-IF
        }
        // COB(329): END-IF
      }
      // COB(330): END-PERFORM.
    }
    // COB(332): PERFORM 9100-TRNXFILE-CLOSE.
    _9100TrnxfileClose();
    // COB(334): PERFORM 9200-XREFFILE-CLOSE.
    _9200XreffileClose();
    // COB(336): PERFORM 9300-CUSTFILE-CLOSE.
    _9300CustfileClose();
    // COB(338): PERFORM 9400-ACCTFILE-CLOSE.
    _9400AcctfileClose();
    // COB(340): CLOSE STMT-FILE HTML-FILE.
    stmtFile.close();
    htmlFile.close();
  }

  /***/
  protected void _9999Goback() {
    // COB(343): GOBACK.
    context.goback();
  }

  /***
   *---------------------------------------------------------------*/
  protected void _1000XreffileGetNext() {
    // COB(348): MOVE 'XREFFILE' TO WS-M03B-DD.
    ws.wsM03bArea.wsM03bDd.setString("XREFFILE");
    // COB(349): SET M03B-READ TO TRUE.
    ws.wsM03bArea.setM03bRead(true);
    // COB(350): MOVE ZERO TO WS-M03B-RC.
    ws.wsM03bArea.wsM03bRc.zeros();
    // COB(351): MOVE SPACES TO WS-M03B-FLDT.
    ws.wsM03bArea.wsM03bFldt.spaces();
    // COB(352): CALL 'CBSTM03B' USING WS-M03B-AREA.
    context.call("CBSTM03B", ws.wsM03bArea);
    // COB(354): EVALUATE WS-M03B-RC
    // COB(355): WHEN '00'
    if (ws.wsM03bArea.wsM03bRc.equals("00")) {
      // COB(356): CONTINUE
      // do nothing
      // COB(357): WHEN '10'
    } else if (ws.wsM03bArea.wsM03bRc.equals("10")) {
      // COB(358): MOVE 'Y' TO END-OF-FILE
      ws.miscVariables.endOfFile.setString("Y");
      // COB(359): WHEN OTHER
    } else {
      // COB(360): DISPLAY 'ERROR READING XREFFILE'
      sysout.display("ERROR READING XREFFILE");
      // COB(361): DISPLAY 'RETURN CODE: ' WS-M03B-RC
      sysout.display("RETURN CODE: ", ws.wsM03bArea.wsM03bRc);
      // COB(362): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(363): END-EVALUATE.
    }
    // COB(365): MOVE WS-M03B-FLDT TO CARD-XREF-RECORD.
    ws.cvact03y.cardXrefRecord.setValue(ws.wsM03bArea.wsM03bFldt);
    // COB(367): EXIT.
    return;
  }

  /***/
  protected void _2000CustfileGet() {
    // COB(371): MOVE 'CUSTFILE' TO WS-M03B-DD.
    ws.wsM03bArea.wsM03bDd.setString("CUSTFILE");
    // COB(372): SET M03B-READ-K TO TRUE.
    ws.wsM03bArea.setM03bReadK(true);
    // COB(373): MOVE XREF-CUST-ID TO WS-M03B-KEY.
    ws.wsM03bArea.wsM03bKey.setValue(ws.cvact03y.cardXrefRecord.xrefCustId);
    // COB(374): MOVE ZERO TO WS-M03B-KEY-LN.
    ws.wsM03bArea.wsM03bKeyLn.zeros();
    // COB(375): COMPUTE WS-M03B-KEY-LN = LENGTH OF XREF-CUST-ID.
    ws.wsM03bArea.wsM03bKeyLn.setValue(ws.cvact03y.cardXrefRecord.xrefCustId.length());
    // COB(376): MOVE ZERO TO WS-M03B-RC.
    ws.wsM03bArea.wsM03bRc.zeros();
    // COB(377): MOVE SPACES TO WS-M03B-FLDT.
    ws.wsM03bArea.wsM03bFldt.spaces();
    // COB(378): CALL 'CBSTM03B' USING WS-M03B-AREA.
    context.call("CBSTM03B", ws.wsM03bArea);
    // COB(380): EVALUATE WS-M03B-RC
    // COB(381): WHEN '00'
    if (ws.wsM03bArea.wsM03bRc.equals("00")) {
      // COB(382): CONTINUE
      // do nothing
      // COB(383): WHEN OTHER
    } else {
      // COB(384): DISPLAY 'ERROR READING CUSTFILE'
      sysout.display("ERROR READING CUSTFILE");
      // COB(385): DISPLAY 'RETURN CODE: ' WS-M03B-RC
      sysout.display("RETURN CODE: ", ws.wsM03bArea.wsM03bRc);
      // COB(386): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(387): END-EVALUATE.
    }
    // COB(389): MOVE WS-M03B-FLDT TO CUSTOMER-RECORD.
    ws.custrec.customerRecord.setValue(ws.wsM03bArea.wsM03bFldt);
    // COB(391): EXIT.
    return;
  }

  /***/
  protected void _3000AcctfileGet() {
    // COB(395): MOVE 'ACCTFILE' TO WS-M03B-DD.
    ws.wsM03bArea.wsM03bDd.setString("ACCTFILE");
    // COB(396): SET M03B-READ-K TO TRUE.
    ws.wsM03bArea.setM03bReadK(true);
    // COB(397): MOVE XREF-ACCT-ID TO WS-M03B-KEY.
    ws.wsM03bArea.wsM03bKey.setValue(ws.cvact03y.cardXrefRecord.xrefAcctId);
    // COB(398): MOVE ZERO TO WS-M03B-KEY-LN.
    ws.wsM03bArea.wsM03bKeyLn.zeros();
    // COB(399): COMPUTE WS-M03B-KEY-LN = LENGTH OF XREF-ACCT-ID.
    ws.wsM03bArea.wsM03bKeyLn.setValue(ws.cvact03y.cardXrefRecord.xrefAcctId.length());
    // COB(400): MOVE ZERO TO WS-M03B-RC.
    ws.wsM03bArea.wsM03bRc.zeros();
    // COB(401): MOVE SPACES TO WS-M03B-FLDT.
    ws.wsM03bArea.wsM03bFldt.spaces();
    // COB(402): CALL 'CBSTM03B' USING WS-M03B-AREA.
    context.call("CBSTM03B", ws.wsM03bArea);
    // COB(404): EVALUATE WS-M03B-RC
    // COB(405): WHEN '00'
    if (ws.wsM03bArea.wsM03bRc.equals("00")) {
      // COB(406): CONTINUE
      // do nothing
      // COB(407): WHEN OTHER
    } else {
      // COB(408): DISPLAY 'ERROR READING ACCTFILE'
      sysout.display("ERROR READING ACCTFILE");
      // COB(409): DISPLAY 'RETURN CODE: ' WS-M03B-RC
      sysout.display("RETURN CODE: ", ws.wsM03bArea.wsM03bRc);
      // COB(410): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(411): END-EVALUATE.
    }
    // COB(413): MOVE WS-M03B-FLDT TO ACCOUNT-RECORD.
    ws.cvact01y.accountRecord.setValue(ws.wsM03bArea.wsM03bFldt);
    // COB(415): EXIT.
    return;
  }

  /***/
  protected void _4000TrnxfileGet() {
    // COB(418): PERFORM VARYING CR-JMP FROM 1 BY 1
    // COB(418):   UNTIL CR-JMP > CR-CNT
    // COB(418):   OR (WS-CARD-NUM (CR-JMP) > XREF-CARD-NUM)
    ws.compVariables.crJmp.setValue(1);
    for (;
        !(ws.compVariables.crJmp.greaterThan(ws.compVariables.crCnt)
            || ws.wsTrnxTable
                .wsCardTblAtIndex(ws.compVariables.crJmp.getInt() - 1)
                .wsCardNum
                .greaterThan(ws.cvact03y.cardXrefRecord.xrefCardNum));
        ws.compVariables.crJmp.add(1)) {
      // COB(421): IF XREF-CARD-NUM = WS-CARD-NUM (CR-JMP)
      if (ws.cvact03y.cardXrefRecord.xrefCardNum.equals(
          ws.wsTrnxTable.wsCardTblAtIndex(ws.compVariables.crJmp.getInt() - 1).wsCardNum)) {
        // COB(422): MOVE WS-CARD-NUM (CR-JMP) TO TRNX-CARD-NUM
        ws.costm01.trnxRecord.trnxKey.trnxCardNum.setValue(
            ws.wsTrnxTable.wsCardTblAtIndex(ws.compVariables.crJmp.getInt() - 1).wsCardNum);
        // COB(423): PERFORM VARYING TR-JMP FROM 1 BY 1
        // COB(423):   UNTIL (TR-JMP > WS-TRCT (CR-JMP))
        ws.compVariables.trJmp.setValue(1);
        for (;
            !ws.compVariables.trJmp.greaterThan(
                ws.wsTrnTblCntr.wsTrnTblCtrAtIndex(ws.compVariables.crJmp.getInt() - 1).wsTrct);
            ws.compVariables.trJmp.add(1)) {
          // COB(425): MOVE WS-TRAN-NUM (CR-JMP, TR-JMP)
          // COB(425):   TO TRNX-ID
          ws.costm01.trnxRecord.trnxKey.trnxId.setValue(
              ws.wsTrnxTable
                  .wsCardTblAtIndex(ws.compVariables.crJmp.getInt() - 1)
                  .wsTranTblAtIndex(ws.compVariables.trJmp.getInt() - 1)
                  .wsTranNum);
          // COB(427): MOVE WS-TRAN-REST (CR-JMP, TR-JMP)
          // COB(427):   TO TRNX-REST
          ws.costm01.trnxRecord.trnxRest.setValue(
              ws.wsTrnxTable
                  .wsCardTblAtIndex(ws.compVariables.crJmp.getInt() - 1)
                  .wsTranTblAtIndex(ws.compVariables.trJmp.getInt() - 1)
                  .wsTranRest);
          // COB(429): PERFORM 6000-WRITE-TRANS
          _6000WriteTrans();
          // COB(430): ADD TRNX-AMT TO WS-TOTAL-AMT
          ws.comp3Variables.wsTotalAmt.add(ws.costm01.trnxRecord.trnxRest.trnxAmt);
          // COB(431): END-PERFORM
        }
        // COB(432): END-IF
      }
      // COB(433): END-PERFORM.
    }
    // COB(434): MOVE WS-TOTAL-AMT TO WS-TRN-AMT.
    ws.miscVariables.wsTrnAmt.setValue(ws.comp3Variables.wsTotalAmt);
    // COB(435): MOVE WS-TRN-AMT TO ST-TOTAL-TRAMT.
    ws.statementLines.stLine14a.stTotalTramt.setValue(ws.miscVariables.wsTrnAmt);
    // COB(436): WRITE FD-STMTFILE-REC FROM ST-LINE12.
    stmtFile.write(ws.statementLines.stLine12);
    // COB(437): WRITE FD-STMTFILE-REC FROM ST-LINE14A.
    stmtFile.write(ws.statementLines.stLine14a);
    // COB(438): WRITE FD-STMTFILE-REC FROM ST-LINE15.
    stmtFile.write(ws.statementLines.stLine15);
    // COB(440): SET HTML-LTRS TO TRUE.
    ws.htmlLines.setHtmlLtrs(true);
    // COB(441): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(442): SET HTML-L10 TO TRUE.
    ws.htmlLines.setHtmlL10(true);
    // COB(443): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(444): SET HTML-L75 TO TRUE.
    ws.htmlLines.setHtmlL75(true);
    // COB(445): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(446): SET HTML-LTDE TO TRUE.
    ws.htmlLines.setHtmlLtde(true);
    // COB(447): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(448): SET HTML-LTRE TO TRUE.
    ws.htmlLines.setHtmlLtre(true);
    // COB(449): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(450): SET HTML-L78 TO TRUE.
    ws.htmlLines.setHtmlL78(true);
    // COB(451): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(452): SET HTML-L79 TO TRUE.
    ws.htmlLines.setHtmlL79(true);
    // COB(453): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(454): SET HTML-L80 TO TRUE.
    ws.htmlLines.setHtmlL80(true);
    // COB(455): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(457): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _5000CreateStatement() {
    // COB(460): INITIALIZE STATEMENT-LINES.
    ws.statementLines.initialize();
    // COB(461): WRITE FD-STMTFILE-REC FROM ST-LINE0.
    stmtFile.write(ws.statementLines.stLine0);
    // COB(462): PERFORM 5100-WRITE-HTML-HEADER THRU 5100-EXIT.
    _5100WriteHtmlHeader();
    // COB(463): STRING CUST-FIRST-NAME DELIMITED BY ' '
    // COB(463):        ' ' DELIMITED BY SIZE
    // COB(463):        CUST-MIDDLE-NAME DELIMITED BY ' '
    // COB(463):        ' ' DELIMITED BY SIZE
    // COB(463):        CUST-LAST-NAME DELIMITED BY ' '
    // COB(463):        ' ' DELIMITED BY SIZE
    // COB(463):        INTO ST-NAME
    // COB(463): END-STRING.
    ws.statementLines.stLine1.stName.concat(
        ws.custrec.customerRecord.custFirstName.substringToValue(" "),
        " ",
        ws.custrec.customerRecord.custMiddleName.substringToValue(" "),
        " ",
        ws.custrec.customerRecord.custLastName.substringToValue(" "),
        " ");
    // COB(470): END-STRING.
    // COB(471): MOVE CUST-ADDR-LINE-1 TO ST-ADD1.
    ws.statementLines.stLine2.stAdd1.setValue(ws.custrec.customerRecord.custAddrLine1);
    // COB(472): MOVE CUST-ADDR-LINE-2 TO ST-ADD2.
    ws.statementLines.stLine3.stAdd2.setValue(ws.custrec.customerRecord.custAddrLine2);
    // COB(473): STRING CUST-ADDR-LINE-3 DELIMITED BY ' '
    // COB(473):        ' ' DELIMITED BY SIZE
    // COB(473):        CUST-ADDR-STATE-CD DELIMITED BY ' '
    // COB(473):        ' ' DELIMITED BY SIZE
    // COB(473):        CUST-ADDR-COUNTRY-CD DELIMITED BY ' '
    // COB(473):        ' ' DELIMITED BY SIZE
    // COB(473):        CUST-ADDR-ZIP DELIMITED BY ' '
    // COB(473):        ' ' DELIMITED BY SIZE
    // COB(473):        INTO ST-ADD3
    // COB(473): END-STRING.
    ws.statementLines.stLine4.stAdd3.concat(
        ws.custrec.customerRecord.custAddrLine3.substringToValue(" "),
        " ",
        ws.custrec.customerRecord.custAddrStateCd.substringToValue(" "),
        " ",
        ws.custrec.customerRecord.custAddrCountryCd.substringToValue(" "),
        " ",
        ws.custrec.customerRecord.custAddrZip.substringToValue(" "),
        " ");
    // COB(482): END-STRING.
    // COB(484): MOVE ACCT-ID TO ST-ACCT-ID.
    ws.statementLines.stLine7.stAcctId.setValue(ws.cvact01y.accountRecord.acctId);
    // COB(485): MOVE ACCT-CURR-BAL TO ST-CURR-BAL.
    ws.statementLines.stLine8.stCurrBal.setValue(ws.cvact01y.accountRecord.acctCurrBal);
    // COB(486): MOVE CUST-FICO-CREDIT-SCORE TO ST-FICO-SCORE.
    ws.statementLines.stLine9.stFicoScore.setValue(ws.custrec.customerRecord.custFicoCreditScore);
    // COB(487): PERFORM 5200-WRITE-HTML-NMADBS THRU 5200-EXIT.
    _5200WriteHtmlNmadbs();
    // COB(489): WRITE FD-STMTFILE-REC FROM ST-LINE1.
    stmtFile.write(ws.statementLines.stLine1);
    // COB(490): WRITE FD-STMTFILE-REC FROM ST-LINE2.
    stmtFile.write(ws.statementLines.stLine2);
    // COB(491): WRITE FD-STMTFILE-REC FROM ST-LINE3.
    stmtFile.write(ws.statementLines.stLine3);
    // COB(492): WRITE FD-STMTFILE-REC FROM ST-LINE4.
    stmtFile.write(ws.statementLines.stLine4);
    // COB(493): WRITE FD-STMTFILE-REC FROM ST-LINE5.
    stmtFile.write(ws.statementLines.stLine5);
    // COB(494): WRITE FD-STMTFILE-REC FROM ST-LINE6.
    stmtFile.write(ws.statementLines.stLine6);
    // COB(495): WRITE FD-STMTFILE-REC FROM ST-LINE5.
    stmtFile.write(ws.statementLines.stLine5);
    // COB(496): WRITE FD-STMTFILE-REC FROM ST-LINE7.
    stmtFile.write(ws.statementLines.stLine7);
    // COB(497): WRITE FD-STMTFILE-REC FROM ST-LINE8.
    stmtFile.write(ws.statementLines.stLine8);
    // COB(498): WRITE FD-STMTFILE-REC FROM ST-LINE9.
    stmtFile.write(ws.statementLines.stLine9);
    // COB(499): WRITE FD-STMTFILE-REC FROM ST-LINE10.
    stmtFile.write(ws.statementLines.stLine10);
    // COB(500): WRITE FD-STMTFILE-REC FROM ST-LINE11.
    stmtFile.write(ws.statementLines.stLine11);
    // COB(501): WRITE FD-STMTFILE-REC FROM ST-LINE12.
    stmtFile.write(ws.statementLines.stLine12);
    // COB(502): WRITE FD-STMTFILE-REC FROM ST-LINE13.
    stmtFile.write(ws.statementLines.stLine13);
    // COB(503): WRITE FD-STMTFILE-REC FROM ST-LINE12.
    stmtFile.write(ws.statementLines.stLine12);
    // COB(505): EXIT.
    return;
  }

  /***/
  protected void _5100WriteHtmlHeader() {
    // COB(509): SET HTML-L01 TO TRUE.
    ws.htmlLines.setHtmlL01(true);
    // COB(510): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(511): SET HTML-L02 TO TRUE.
    ws.htmlLines.setHtmlL02(true);
    // COB(512): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(513): SET HTML-L03 TO TRUE.
    ws.htmlLines.setHtmlL03(true);
    // COB(514): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(515): SET HTML-L04 TO TRUE.
    ws.htmlLines.setHtmlL04(true);
    // COB(516): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(517): SET HTML-L05 TO TRUE.
    ws.htmlLines.setHtmlL05(true);
    // COB(518): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(519): SET HTML-L06 TO TRUE.
    ws.htmlLines.setHtmlL06(true);
    // COB(520): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(521): SET HTML-L07 TO TRUE.
    ws.htmlLines.setHtmlL07(true);
    // COB(522): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(523): SET HTML-L08 TO TRUE.
    ws.htmlLines.setHtmlL08(true);
    // COB(524): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(525): SET HTML-LTRS TO TRUE.
    ws.htmlLines.setHtmlLtrs(true);
    // COB(526): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(527): SET HTML-L10 TO TRUE.
    ws.htmlLines.setHtmlL10(true);
    // COB(528): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(530): MOVE ACCT-ID TO L11-ACCT.
    ws.htmlLines.htmlL11.l11Acct.setValue(ws.cvact01y.accountRecord.acctId);
    // COB(531): WRITE FD-HTMLFILE-REC FROM HTML-L11.
    htmlFile.write(ws.htmlLines.htmlL11);
    // COB(532): SET HTML-LTDE TO TRUE.
    ws.htmlLines.setHtmlLtde(true);
    // COB(533): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(534): SET HTML-LTRE TO TRUE.
    ws.htmlLines.setHtmlLtre(true);
    // COB(535): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(536): SET HTML-LTRS TO TRUE.
    ws.htmlLines.setHtmlLtrs(true);
    // COB(537): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(538): SET HTML-L15 TO TRUE.
    ws.htmlLines.setHtmlL15(true);
    // COB(539): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(540): SET HTML-L16 TO TRUE.
    ws.htmlLines.setHtmlL16(true);
    // COB(541): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(542): SET HTML-L17 TO TRUE.
    ws.htmlLines.setHtmlL17(true);
    // COB(543): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(544): SET HTML-L18 TO TRUE.
    ws.htmlLines.setHtmlL18(true);
    // COB(545): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(546): SET HTML-LTDE TO TRUE.
    ws.htmlLines.setHtmlLtde(true);
    // COB(547): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(548): SET HTML-LTRE TO TRUE.
    ws.htmlLines.setHtmlLtre(true);
    // COB(549): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(550): SET HTML-LTRS TO TRUE.
    ws.htmlLines.setHtmlLtrs(true);
    // COB(551): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(552): SET HTML-L22-35 TO TRUE.
    ws.htmlLines.setHtmlL22_35(true);
    // COB(553): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
  }

  /***
   *---------------------------------------------------------------*/
  protected void _5200WriteHtmlNmadbs() {
    // COB(561): MOVE ST-NAME TO L23-NAME.
    ws.htmlLines.htmlL23.l23Name.setValue(ws.statementLines.stLine1.stName);
    // COB(562): MOVE SPACES TO FD-HTMLFILE-REC
    fs.fdHtmlfileRec.spaces();
    // COB(563): STRING '<p style="font-size:16px">' DELIMITED BY '*'
    // COB(563):        L23-NAME DELIMITED BY '  '
    // COB(563):        '  ' DELIMITED BY SIZE
    // COB(563):        '</p>' DELIMITED BY '*'
    // COB(563):        INTO FD-HTMLFILE-REC
    // COB(563): END-STRING.
    fs.fdHtmlfileRec.concat(
        (new StringValue("<p style=\"font-size:16px\">")).substringToValue("*"),
        ws.htmlLines.htmlL23.l23Name.substringToValue("  "),
        "  ",
        (new StringValue("</p>")).substringToValue("*"));
    // COB(568): END-STRING.
    // COB(569): WRITE FD-HTMLFILE-REC.
    htmlFile.write();
    // COB(570): MOVE SPACES TO HTML-ADDR-LN.
    ws.htmlLines.htmlAddrLn.spaces();
    // COB(571): STRING '<p>' DELIMITED BY '*'
    // COB(571):        ST-ADD1 DELIMITED BY '  '
    // COB(571):        '  ' DELIMITED BY SIZE
    // COB(571):        '</p>' DELIMITED BY '*'
    // COB(571):        INTO HTML-ADDR-LN
    // COB(571): END-STRING.
    ws.htmlLines.htmlAddrLn.concat(
        (new StringValue("<p>")).substringToValue("*"),
        ws.statementLines.stLine2.stAdd1.substringToValue("  "),
        "  ",
        (new StringValue("</p>")).substringToValue("*"));
    // COB(576): END-STRING.
    // COB(577): WRITE FD-HTMLFILE-REC FROM HTML-ADDR-LN.
    htmlFile.write(ws.htmlLines.htmlAddrLn);
    // COB(578): MOVE SPACES TO HTML-ADDR-LN.
    ws.htmlLines.htmlAddrLn.spaces();
    // COB(579): STRING '<p>' DELIMITED BY '*'
    // COB(579):        ST-ADD2 DELIMITED BY '  '
    // COB(579):        '  ' DELIMITED BY SIZE
    // COB(579):        '</p>' DELIMITED BY '*'
    // COB(579):        INTO HTML-ADDR-LN
    // COB(579): END-STRING.
    ws.htmlLines.htmlAddrLn.concat(
        (new StringValue("<p>")).substringToValue("*"),
        ws.statementLines.stLine3.stAdd2.substringToValue("  "),
        "  ",
        (new StringValue("</p>")).substringToValue("*"));
    // COB(584): END-STRING.
    // COB(585): WRITE FD-HTMLFILE-REC FROM HTML-ADDR-LN.
    htmlFile.write(ws.htmlLines.htmlAddrLn);
    // COB(586): MOVE SPACES TO HTML-ADDR-LN.
    ws.htmlLines.htmlAddrLn.spaces();
    // COB(587): STRING '<p>' DELIMITED BY '*'
    // COB(587):        ST-ADD3 DELIMITED BY '  '
    // COB(587):        '  ' DELIMITED BY SIZE
    // COB(587):        '</p>' DELIMITED BY '*'
    // COB(587):        INTO HTML-ADDR-LN
    // COB(587): END-STRING.
    ws.htmlLines.htmlAddrLn.concat(
        (new StringValue("<p>")).substringToValue("*"),
        ws.statementLines.stLine4.stAdd3.substringToValue("  "),
        "  ",
        (new StringValue("</p>")).substringToValue("*"));
    // COB(592): END-STRING.
    // COB(593): WRITE FD-HTMLFILE-REC FROM HTML-ADDR-LN.
    htmlFile.write(ws.htmlLines.htmlAddrLn);
    // COB(595): SET HTML-LTDE TO TRUE.
    ws.htmlLines.setHtmlLtde(true);
    // COB(596): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(597): SET HTML-LTRE TO TRUE.
    ws.htmlLines.setHtmlLtre(true);
    // COB(598): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(599): SET HTML-LTRS TO TRUE.
    ws.htmlLines.setHtmlLtrs(true);
    // COB(600): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(601): SET HTML-L30-42 TO TRUE.
    ws.htmlLines.setHtmlL30_42(true);
    // COB(602): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(603): SET HTML-L31 TO TRUE.
    ws.htmlLines.setHtmlL31(true);
    // COB(604): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(605): SET HTML-LTDE TO TRUE.
    ws.htmlLines.setHtmlLtde(true);
    // COB(606): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(607): SET HTML-LTRE TO TRUE.
    ws.htmlLines.setHtmlLtre(true);
    // COB(608): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(609): SET HTML-LTRS TO TRUE.
    ws.htmlLines.setHtmlLtrs(true);
    // COB(610): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(611): SET HTML-L22-35 TO TRUE.
    ws.htmlLines.setHtmlL22_35(true);
    // COB(612): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(614): MOVE SPACES TO HTML-BSIC-LN.
    ws.htmlLines.htmlBsicLn.spaces();
    // COB(615): STRING '<p>Account ID         : ' DELIMITED BY '*'
    // COB(615):        ST-ACCT-ID DELIMITED BY '*'
    // COB(615):        '</p>' DELIMITED BY '*'
    // COB(615):        INTO HTML-BSIC-LN
    // COB(615): END-STRING.
    ws.htmlLines.htmlBsicLn.concat(
        (new StringValue("<p>Account ID         : ")).substringToValue("*"),
        ws.statementLines.stLine7.stAcctId.substringToValue("*"),
        (new StringValue("</p>")).substringToValue("*"));
    // COB(619): END-STRING.
    // COB(620): WRITE FD-HTMLFILE-REC FROM HTML-BSIC-LN.
    htmlFile.write(ws.htmlLines.htmlBsicLn);
    // COB(621): MOVE SPACES TO HTML-BSIC-LN.
    ws.htmlLines.htmlBsicLn.spaces();
    // COB(622): STRING '<p>Current Balance    : ' DELIMITED BY '*'
    // COB(622):        ST-CURR-BAL DELIMITED BY '*'
    // COB(622):        '</p>' DELIMITED BY '*'
    // COB(622):        INTO HTML-BSIC-LN
    // COB(622): END-STRING.
    ws.htmlLines.htmlBsicLn.concat(
        (new StringValue("<p>Current Balance    : ")).substringToValue("*"),
        ws.statementLines.stLine8.stCurrBal.substringToValue("*"),
        (new StringValue("</p>")).substringToValue("*"));
    // COB(626): END-STRING.
    // COB(627): WRITE FD-HTMLFILE-REC FROM HTML-BSIC-LN.
    htmlFile.write(ws.htmlLines.htmlBsicLn);
    // COB(628): MOVE SPACES TO HTML-BSIC-LN.
    ws.htmlLines.htmlBsicLn.spaces();
    // COB(629): STRING '<p>FICO Score         : ' DELIMITED BY '*'
    // COB(629):        ST-FICO-SCORE DELIMITED BY '*'
    // COB(629):        '</p>' DELIMITED BY '*'
    // COB(629):        INTO HTML-BSIC-LN
    // COB(629): END-STRING.
    ws.htmlLines.htmlBsicLn.concat(
        (new StringValue("<p>FICO Score         : ")).substringToValue("*"),
        ws.statementLines.stLine9.stFicoScore.substringToValue("*"),
        (new StringValue("</p>")).substringToValue("*"));
    // COB(633): END-STRING.
    // COB(634): WRITE FD-HTMLFILE-REC FROM HTML-BSIC-LN.
    htmlFile.write(ws.htmlLines.htmlBsicLn);
    // COB(635): SET HTML-LTDE TO TRUE.
    ws.htmlLines.setHtmlLtde(true);
    // COB(636): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(637): SET HTML-LTRE TO TRUE.
    ws.htmlLines.setHtmlLtre(true);
    // COB(638): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(639): SET HTML-LTRS TO TRUE.
    ws.htmlLines.setHtmlLtrs(true);
    // COB(640): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(641): SET HTML-L30-42 TO TRUE.
    ws.htmlLines.setHtmlL30_42(true);
    // COB(642): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(643): SET HTML-L43 TO TRUE.
    ws.htmlLines.setHtmlL43(true);
    // COB(644): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(645): SET HTML-LTDE TO TRUE.
    ws.htmlLines.setHtmlLtde(true);
    // COB(646): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(647): SET HTML-LTRE TO TRUE.
    ws.htmlLines.setHtmlLtre(true);
    // COB(648): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(649): SET HTML-LTRS TO TRUE.
    ws.htmlLines.setHtmlLtrs(true);
    // COB(650): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(651): SET HTML-L47 TO TRUE.
    ws.htmlLines.setHtmlL47(true);
    // COB(652): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(653): SET HTML-L48 TO TRUE.
    ws.htmlLines.setHtmlL48(true);
    // COB(654): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(655): SET HTML-LTDE TO TRUE.
    ws.htmlLines.setHtmlLtde(true);
    // COB(656): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(657): SET HTML-L50 TO TRUE.
    ws.htmlLines.setHtmlL50(true);
    // COB(658): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(659): SET HTML-L51 TO TRUE.
    ws.htmlLines.setHtmlL51(true);
    // COB(660): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(661): SET HTML-LTDE TO TRUE.
    ws.htmlLines.setHtmlLtde(true);
    // COB(662): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(663): SET HTML-L53 TO TRUE.
    ws.htmlLines.setHtmlL53(true);
    // COB(664): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(665): SET HTML-L54 TO TRUE.
    ws.htmlLines.setHtmlL54(true);
    // COB(666): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(667): SET HTML-LTDE TO TRUE.
    ws.htmlLines.setHtmlLtde(true);
    // COB(668): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(669): SET HTML-LTRE TO TRUE.
    ws.htmlLines.setHtmlLtre(true);
    // COB(670): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
  }

  /***
   *---------------------------------------------------------------*/
  protected void _6000WriteTrans() {
    // COB(677): MOVE TRNX-ID TO ST-TRANID.
    ws.statementLines.stLine14.stTranid.setValue(ws.costm01.trnxRecord.trnxKey.trnxId);
    // COB(678): MOVE TRNX-DESC TO ST-TRANDT.
    ws.statementLines.stLine14.stTrandt.setValue(ws.costm01.trnxRecord.trnxRest.trnxDesc);
    // COB(679): MOVE TRNX-AMT TO ST-TRANAMT.
    ws.statementLines.stLine14.stTranamt.setValue(ws.costm01.trnxRecord.trnxRest.trnxAmt);
    // COB(680): WRITE FD-STMTFILE-REC FROM ST-LINE14.
    stmtFile.write(ws.statementLines.stLine14);
    // COB(682): SET HTML-LTRS TO TRUE.
    ws.htmlLines.setHtmlLtrs(true);
    // COB(683): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(685): SET HTML-L58 TO TRUE.
    ws.htmlLines.setHtmlL58(true);
    // COB(686): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(687): MOVE SPACES TO HTML-TRAN-LN.
    ws.htmlLines.htmlTranLn.spaces();
    // COB(688): STRING '<p>' DELIMITED BY '*'
    // COB(688):        ST-TRANID DELIMITED BY '*'
    // COB(688):        '</p>' DELIMITED BY '*'
    // COB(688):        INTO HTML-TRAN-LN
    // COB(688): END-STRING.
    ws.htmlLines.htmlTranLn.concat(
        (new StringValue("<p>")).substringToValue("*"),
        ws.statementLines.stLine14.stTranid.substringToValue("*"),
        (new StringValue("</p>")).substringToValue("*"));
    // COB(692): END-STRING.
    // COB(693): WRITE FD-HTMLFILE-REC FROM HTML-TRAN-LN.
    htmlFile.write(ws.htmlLines.htmlTranLn);
    // COB(694): SET HTML-LTDE TO TRUE.
    ws.htmlLines.setHtmlLtde(true);
    // COB(695): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(697): SET HTML-L61 TO TRUE.
    ws.htmlLines.setHtmlL61(true);
    // COB(698): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(699): MOVE SPACES TO HTML-TRAN-LN.
    ws.htmlLines.htmlTranLn.spaces();
    // COB(700): STRING '<p>' DELIMITED BY '*'
    // COB(700):        ST-TRANDT DELIMITED BY '*'
    // COB(700):        '</p>' DELIMITED BY '*'
    // COB(700):        INTO HTML-TRAN-LN
    // COB(700): END-STRING.
    ws.htmlLines.htmlTranLn.concat(
        (new StringValue("<p>")).substringToValue("*"),
        ws.statementLines.stLine14.stTrandt.substringToValue("*"),
        (new StringValue("</p>")).substringToValue("*"));
    // COB(704): END-STRING.
    // COB(705): WRITE FD-HTMLFILE-REC FROM HTML-TRAN-LN.
    htmlFile.write(ws.htmlLines.htmlTranLn);
    // COB(706): SET HTML-LTDE TO TRUE.
    ws.htmlLines.setHtmlLtde(true);
    // COB(707): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(709): SET HTML-L64 TO TRUE.
    ws.htmlLines.setHtmlL64(true);
    // COB(710): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(711): MOVE SPACES TO HTML-TRAN-LN.
    ws.htmlLines.htmlTranLn.spaces();
    // COB(712): STRING '<p>' DELIMITED BY '*'
    // COB(712):        ST-TRANAMT DELIMITED BY '*'
    // COB(712):        '</p>' DELIMITED BY '*'
    // COB(712):        INTO HTML-TRAN-LN
    // COB(712): END-STRING.
    ws.htmlLines.htmlTranLn.concat(
        (new StringValue("<p>")).substringToValue("*"),
        ws.statementLines.stLine14.stTranamt.substringToValue("*"),
        (new StringValue("</p>")).substringToValue("*"));
    // COB(716): END-STRING.
    // COB(717): WRITE FD-HTMLFILE-REC FROM HTML-TRAN-LN.
    htmlFile.write(ws.htmlLines.htmlTranLn);
    // COB(718): SET HTML-LTDE TO TRUE.
    ws.htmlLines.setHtmlLtde(true);
    // COB(719): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(721): SET HTML-LTRE TO TRUE.
    ws.htmlLines.setHtmlLtre(true);
    // COB(722): WRITE FD-HTMLFILE-REC FROM HTML-FIXED-LN.
    htmlFile.write(ws.htmlLines.htmlFixedLn);
    // COB(724): EXIT.
    return;
  }

  /***
   *---------------------------------------------------------------*/
  protected Flow _8100FileOpen() {
    // COB(728): GO TO 8100-TRNXFILE-OPEN
    return Flow._8100TrnxfileOpen;
  }

  /***/
  protected Flow _8100TrnxfileOpen() {
    // COB(732): MOVE 'TRNXFILE' TO WS-M03B-DD.
    ws.wsM03bArea.wsM03bDd.setString("TRNXFILE");
    // COB(733): SET M03B-OPEN TO TRUE.
    ws.wsM03bArea.setM03bOpen(true);
    // COB(734): MOVE ZERO TO WS-M03B-RC.
    ws.wsM03bArea.wsM03bRc.zeros();
    // COB(735): CALL 'CBSTM03B' USING WS-M03B-AREA.
    context.call("CBSTM03B", ws.wsM03bArea);
    // COB(737): IF WS-M03B-RC = '00' OR '04'
    if (ws.wsM03bArea.wsM03bRc.equals("00") || ws.wsM03bArea.wsM03bRc.equals("04")) {
      // COB(738): CONTINUE
      // do nothing
      // COB(739): ELSE
    } else {
      // COB(740): DISPLAY 'ERROR OPENING TRNXFILE'
      sysout.display("ERROR OPENING TRNXFILE");
      // COB(741): DISPLAY 'RETURN CODE: ' WS-M03B-RC
      sysout.display("RETURN CODE: ", ws.wsM03bArea.wsM03bRc);
      // COB(742): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(743): END-IF.
    }
    // COB(745): SET M03B-READ TO TRUE.
    ws.wsM03bArea.setM03bRead(true);
    // COB(746): MOVE SPACES TO WS-M03B-FLDT.
    ws.wsM03bArea.wsM03bFldt.spaces();
    // COB(747): CALL 'CBSTM03B' USING WS-M03B-AREA.
    context.call("CBSTM03B", ws.wsM03bArea);
    // COB(749): IF WS-M03B-RC = '00' OR '04'
    if (ws.wsM03bArea.wsM03bRc.equals("00") || ws.wsM03bArea.wsM03bRc.equals("04")) {
      // COB(750): CONTINUE
      // do nothing
      // COB(751): ELSE
    } else {
      // COB(752): DISPLAY 'ERROR READING TRNXFILE'
      sysout.display("ERROR READING TRNXFILE");
      // COB(753): DISPLAY 'RETURN CODE: ' WS-M03B-RC
      sysout.display("RETURN CODE: ", ws.wsM03bArea.wsM03bRc);
      // COB(754): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(755): END-IF.
    }
    // COB(757): MOVE WS-M03B-FLDT TO TRNX-RECORD.
    ws.costm01.trnxRecord.setValue(ws.wsM03bArea.wsM03bFldt);
    // COB(758): MOVE TRNX-CARD-NUM TO WS-SAVE-CARD.
    ws.miscVariables.wsSaveCard.setValue(ws.costm01.trnxRecord.trnxKey.trnxCardNum);
    // COB(759): MOVE 1 TO CR-CNT.
    ws.compVariables.crCnt.setValue(1);
    // COB(760): MOVE 0 TO TR-CNT.
    ws.compVariables.trCnt.setValue(0);
    // COB(761): MOVE 'READTRNX' TO WS-FL-DD.
    ws.miscVariables.wsFlDd.setString("READTRNX");
    // COB(762): GO TO 0000-START.
    return Flow._0000Start;
    // COB(763): EXIT.

  }

  /***
   *---------------------------------------------------------------*/
  protected Flow _8200XreffileOpen() {
    // COB(767): MOVE 'XREFFILE' TO WS-M03B-DD.
    ws.wsM03bArea.wsM03bDd.setString("XREFFILE");
    // COB(768): SET M03B-OPEN TO TRUE.
    ws.wsM03bArea.setM03bOpen(true);
    // COB(769): MOVE ZERO TO WS-M03B-RC.
    ws.wsM03bArea.wsM03bRc.zeros();
    // COB(770): CALL 'CBSTM03B' USING WS-M03B-AREA.
    context.call("CBSTM03B", ws.wsM03bArea);
    // COB(772): IF WS-M03B-RC = '00' OR '04'
    if (ws.wsM03bArea.wsM03bRc.equals("00") || ws.wsM03bArea.wsM03bRc.equals("04")) {
      // COB(773): CONTINUE
      // do nothing
      // COB(774): ELSE
    } else {
      // COB(775): DISPLAY 'ERROR OPENING XREFFILE'
      sysout.display("ERROR OPENING XREFFILE");
      // COB(776): DISPLAY 'RETURN CODE: ' WS-M03B-RC
      sysout.display("RETURN CODE: ", ws.wsM03bArea.wsM03bRc);
      // COB(777): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(778): END-IF.
    }
    // COB(780): MOVE 'CUSTFILE' TO WS-FL-DD.
    ws.miscVariables.wsFlDd.setString("CUSTFILE");
    // COB(781): GO TO 0000-START.
    return Flow._0000Start;
    // COB(782): EXIT.

  }

  /***---------------------------------------------------------------*/
  protected Flow _8300CustfileOpen() {
    // COB(785): MOVE 'CUSTFILE' TO WS-M03B-DD.
    ws.wsM03bArea.wsM03bDd.setString("CUSTFILE");
    // COB(786): SET M03B-OPEN TO TRUE.
    ws.wsM03bArea.setM03bOpen(true);
    // COB(787): MOVE ZERO TO WS-M03B-RC.
    ws.wsM03bArea.wsM03bRc.zeros();
    // COB(788): CALL 'CBSTM03B' USING WS-M03B-AREA.
    context.call("CBSTM03B", ws.wsM03bArea);
    // COB(790): IF WS-M03B-RC = '00' OR '04'
    if (ws.wsM03bArea.wsM03bRc.equals("00") || ws.wsM03bArea.wsM03bRc.equals("04")) {
      // COB(791): CONTINUE
      // do nothing
      // COB(792): ELSE
    } else {
      // COB(793): DISPLAY 'ERROR OPENING CUSTFILE'
      sysout.display("ERROR OPENING CUSTFILE");
      // COB(794): DISPLAY 'RETURN CODE: ' WS-M03B-RC
      sysout.display("RETURN CODE: ", ws.wsM03bArea.wsM03bRc);
      // COB(795): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(796): END-IF.
    }
    // COB(798): MOVE 'ACCTFILE' TO WS-FL-DD.
    ws.miscVariables.wsFlDd.setString("ACCTFILE");
    // COB(799): GO TO 0000-START.
    return Flow._0000Start;
    // COB(800): EXIT.

  }

  /***---------------------------------------------------------------*/
  protected Flow _8400AcctfileOpen() {
    // COB(803): MOVE 'ACCTFILE' TO WS-M03B-DD.
    ws.wsM03bArea.wsM03bDd.setString("ACCTFILE");
    // COB(804): SET M03B-OPEN TO TRUE.
    ws.wsM03bArea.setM03bOpen(true);
    // COB(805): MOVE ZERO TO WS-M03B-RC.
    ws.wsM03bArea.wsM03bRc.zeros();
    // COB(806): CALL 'CBSTM03B' USING WS-M03B-AREA.
    context.call("CBSTM03B", ws.wsM03bArea);
    // COB(808): IF WS-M03B-RC = '00' OR '04'
    if (ws.wsM03bArea.wsM03bRc.equals("00") || ws.wsM03bArea.wsM03bRc.equals("04")) {
      // COB(809): CONTINUE
      // do nothing
      // COB(810): ELSE
    } else {
      // COB(811): DISPLAY 'ERROR OPENING ACCTFILE'
      sysout.display("ERROR OPENING ACCTFILE");
      // COB(812): DISPLAY 'RETURN CODE: ' WS-M03B-RC
      sysout.display("RETURN CODE: ", ws.wsM03bArea.wsM03bRc);
      // COB(813): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(814): END-IF.
    }
    // COB(816): GO TO 1000-MAINLINE.
    return Flow._1000Mainline;
    // COB(817): EXIT.

  }

  /***---------------------------------------------------------------*/
  protected Flow _8500ReadtrnxRead() {
    // COB(820): IF WS-SAVE-CARD = TRNX-CARD-NUM
    if (ws.miscVariables.wsSaveCard.equals(ws.costm01.trnxRecord.trnxKey.trnxCardNum)) {
      // COB(821): ADD 1 TO TR-CNT
      ws.compVariables.trCnt.add(1);
      // COB(822): ELSE
    } else {
      // COB(823): MOVE TR-CNT TO WS-TRCT (CR-CNT)
      ws.wsTrnTblCntr
          .wsTrnTblCtrAtIndex(ws.compVariables.crCnt.getInt() - 1)
          .wsTrct
          .setValue(ws.compVariables.trCnt);
      // COB(824): ADD 1 TO CR-CNT
      ws.compVariables.crCnt.add(1);
      // COB(825): MOVE 1 TO TR-CNT
      ws.compVariables.trCnt.setValue(1);
      // COB(826): END-IF.
    }
    // COB(828): MOVE TRNX-CARD-NUM TO WS-CARD-NUM (CR-CNT).
    ws.wsTrnxTable
        .wsCardTblAtIndex(ws.compVariables.crCnt.getInt() - 1)
        .wsCardNum
        .setValue(ws.costm01.trnxRecord.trnxKey.trnxCardNum);
    // COB(829): MOVE TRNX-ID TO WS-TRAN-NUM (CR-CNT, TR-CNT).
    ws.wsTrnxTable
        .wsCardTblAtIndex(ws.compVariables.crCnt.getInt() - 1)
        .wsTranTblAtIndex(ws.compVariables.trCnt.getInt() - 1)
        .wsTranNum
        .setValue(ws.costm01.trnxRecord.trnxKey.trnxId);
    // COB(830): MOVE TRNX-REST TO WS-TRAN-REST (CR-CNT, TR-CNT).
    ws.wsTrnxTable
        .wsCardTblAtIndex(ws.compVariables.crCnt.getInt() - 1)
        .wsTranTblAtIndex(ws.compVariables.trCnt.getInt() - 1)
        .wsTranRest
        .setValue(ws.costm01.trnxRecord.trnxRest);
    // COB(831): MOVE TRNX-CARD-NUM TO WS-SAVE-CARD.
    ws.miscVariables.wsSaveCard.setValue(ws.costm01.trnxRecord.trnxKey.trnxCardNum);
    // COB(833): MOVE 'TRNXFILE' TO WS-M03B-DD.
    ws.wsM03bArea.wsM03bDd.setString("TRNXFILE");
    // COB(834): SET M03B-READ TO TRUE.
    ws.wsM03bArea.setM03bRead(true);
    // COB(835): MOVE SPACES TO WS-M03B-FLDT.
    ws.wsM03bArea.wsM03bFldt.spaces();
    // COB(836): CALL 'CBSTM03B' USING WS-M03B-AREA.
    context.call("CBSTM03B", ws.wsM03bArea);
    // COB(838): EVALUATE WS-M03B-RC
    // COB(839): WHEN '00'
    if (ws.wsM03bArea.wsM03bRc.equals("00")) {
      // COB(840): MOVE WS-M03B-FLDT TO TRNX-RECORD
      ws.costm01.trnxRecord.setValue(ws.wsM03bArea.wsM03bFldt);
      // COB(841): GO TO 8500-READTRNX-READ
      return Flow._8500ReadtrnxRead;
      // COB(842): WHEN '10'
    } else if (ws.wsM03bArea.wsM03bRc.equals("10")) {
      // COB(843): GO TO 8599-EXIT
      return Flow._8599Exit;
      // COB(844): WHEN OTHER
    } else {
      // COB(845): DISPLAY 'ERROR READING TRNXFILE'
      sysout.display("ERROR READING TRNXFILE");
      // COB(846): DISPLAY 'RETURN CODE: ' WS-M03B-RC
      sysout.display("RETURN CODE: ", ws.wsM03bArea.wsM03bRc);
      // COB(847): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(848): END-EVALUATE.
    }
    return Flow.Exit;
  }

  /***/
  protected Flow _8599Exit() {
    // COB(851): MOVE TR-CNT TO WS-TRCT (CR-CNT).
    ws.wsTrnTblCntr
        .wsTrnTblCtrAtIndex(ws.compVariables.crCnt.getInt() - 1)
        .wsTrct
        .setValue(ws.compVariables.trCnt);
    // COB(852): MOVE 'XREFFILE' TO WS-FL-DD.
    ws.miscVariables.wsFlDd.setString("XREFFILE");
    // COB(853): GO TO 0000-START.
    return Flow._0000Start;
    // COB(854): EXIT.

  }

  /***
   *---------------------------------------------------------------*/
  protected void _9100TrnxfileClose() {
    // COB(858): MOVE 'TRNXFILE' TO WS-M03B-DD.
    ws.wsM03bArea.wsM03bDd.setString("TRNXFILE");
    // COB(859): SET M03B-CLOSE TO TRUE.
    ws.wsM03bArea.setM03bClose(true);
    // COB(860): MOVE ZERO TO WS-M03B-RC.
    ws.wsM03bArea.wsM03bRc.zeros();
    // COB(861): CALL 'CBSTM03B' USING WS-M03B-AREA.
    context.call("CBSTM03B", ws.wsM03bArea);
    // COB(863): IF WS-M03B-RC = '00' OR '04'
    if (ws.wsM03bArea.wsM03bRc.equals("00") || ws.wsM03bArea.wsM03bRc.equals("04")) {
      // COB(864): CONTINUE
      // do nothing
      // COB(865): ELSE
    } else {
      // COB(866): DISPLAY 'ERROR CLOSING TRNXFILE'
      sysout.display("ERROR CLOSING TRNXFILE");
      // COB(867): DISPLAY 'RETURN CODE: ' WS-M03B-RC
      sysout.display("RETURN CODE: ", ws.wsM03bArea.wsM03bRc);
      // COB(868): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(869): END-IF.
    }
    // COB(871): EXIT.
    return;
  }

  /***
   *---------------------------------------------------------------*/
  protected void _9200XreffileClose() {
    // COB(875): MOVE 'XREFFILE' TO WS-M03B-DD.
    ws.wsM03bArea.wsM03bDd.setString("XREFFILE");
    // COB(876): SET M03B-CLOSE TO TRUE.
    ws.wsM03bArea.setM03bClose(true);
    // COB(877): MOVE ZERO TO WS-M03B-RC.
    ws.wsM03bArea.wsM03bRc.zeros();
    // COB(878): CALL 'CBSTM03B' USING WS-M03B-AREA.
    context.call("CBSTM03B", ws.wsM03bArea);
    // COB(880): IF WS-M03B-RC = '00' OR '04'
    if (ws.wsM03bArea.wsM03bRc.equals("00") || ws.wsM03bArea.wsM03bRc.equals("04")) {
      // COB(881): CONTINUE
      // do nothing
      // COB(882): ELSE
    } else {
      // COB(883): DISPLAY 'ERROR CLOSING XREFFILE'
      sysout.display("ERROR CLOSING XREFFILE");
      // COB(884): DISPLAY 'RETURN CODE: ' WS-M03B-RC
      sysout.display("RETURN CODE: ", ws.wsM03bArea.wsM03bRc);
      // COB(885): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(886): END-IF.
    }
    // COB(888): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9300CustfileClose() {
    // COB(891): MOVE 'CUSTFILE' TO WS-M03B-DD.
    ws.wsM03bArea.wsM03bDd.setString("CUSTFILE");
    // COB(892): SET M03B-CLOSE TO TRUE.
    ws.wsM03bArea.setM03bClose(true);
    // COB(893): MOVE ZERO TO WS-M03B-RC.
    ws.wsM03bArea.wsM03bRc.zeros();
    // COB(894): CALL 'CBSTM03B' USING WS-M03B-AREA.
    context.call("CBSTM03B", ws.wsM03bArea);
    // COB(896): IF WS-M03B-RC = '00' OR '04'
    if (ws.wsM03bArea.wsM03bRc.equals("00") || ws.wsM03bArea.wsM03bRc.equals("04")) {
      // COB(897): CONTINUE
      // do nothing
      // COB(898): ELSE
    } else {
      // COB(899): DISPLAY 'ERROR CLOSING CUSTFILE'
      sysout.display("ERROR CLOSING CUSTFILE");
      // COB(900): DISPLAY 'RETURN CODE: ' WS-M03B-RC
      sysout.display("RETURN CODE: ", ws.wsM03bArea.wsM03bRc);
      // COB(901): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(902): END-IF.
    }
    // COB(904): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9400AcctfileClose() {
    // COB(907): MOVE 'ACCTFILE' TO WS-M03B-DD.
    ws.wsM03bArea.wsM03bDd.setString("ACCTFILE");
    // COB(908): SET M03B-CLOSE TO TRUE.
    ws.wsM03bArea.setM03bClose(true);
    // COB(909): MOVE ZERO TO WS-M03B-RC.
    ws.wsM03bArea.wsM03bRc.zeros();
    // COB(910): CALL 'CBSTM03B' USING WS-M03B-AREA.
    context.call("CBSTM03B", ws.wsM03bArea);
    // COB(912): IF WS-M03B-RC = '00' OR '04'
    if (ws.wsM03bArea.wsM03bRc.equals("00") || ws.wsM03bArea.wsM03bRc.equals("04")) {
      // COB(913): CONTINUE
      // do nothing
      // COB(914): ELSE
    } else {
      // COB(915): DISPLAY 'ERROR CLOSING ACCTFILE'
      sysout.display("ERROR CLOSING ACCTFILE");
      // COB(916): DISPLAY 'RETURN CODE: ' WS-M03B-RC
      sysout.display("RETURN CODE: ", ws.wsM03bArea.wsM03bRc);
      // COB(917): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(918): END-IF.
    }
    // COB(920): EXIT.
    return;
  }

  /***/
  protected void _9999AbendProgram() {
    // COB(923): DISPLAY 'ABENDING PROGRAM'
    sysout.display("ABENDING PROGRAM");
    // COB(924): CALL 'CEE3ABD'.
    throw new AbendException(999);
  }
}
