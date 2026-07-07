package com.aws.carddemo.batch.program;

import com.aws.carddemo.batch.program.storage.cbstm03b.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.io.*;
import com.nib.commons.io.vsam.*;
import com.nib.commons.storage.*;

public class CBSTM03B extends AbstractProgram {
  private KeySequentialFile trnxFile;
  private KeySequentialFile xrefFile;
  private KeySequentialFile custFile;
  private KeySequentialFile acctFile;
  final Cbstm03bRecords fs = new Cbstm03bRecords();

  protected enum Flow {
    Exit,
    // 0000-START
    _0000Start,
    // 1000-TRNXFILE-PROC
    _1000TrnxfileProc,
    // 1900-EXIT
    _1900Exit,
    // 2000-XREFFILE-PROC
    _2000XreffileProc,
    // 2900-EXIT
    _2900Exit,
    // 3000-CUSTFILE-PROC
    _3000CustfileProc,
    // 3900-EXIT
    _3900Exit,
    // 4000-ACCTFILE-PROC
    _4000AcctfileProc,
    // 4900-EXIT
    _4900Exit,
    // 9999-GOBACK
    _9999Goback
  }

  private Flow rcNext;

  @ProgramStorage final Cbstm03bWorking ws = new Cbstm03bWorking();

  // Linkage
  public static class Cbstm03bLinkage extends NGroup {
    // COB:        01  LK-M03B-AREA.
    public LkM03bArea lkM03bArea = new LkM03bArea();

    public static class LkM03bArea extends NGroup {

      // COB:            05  LK-M03B-DD          PIC X(08).
      public NChar lkM03bDd = new NChar(8);
      // COB:            05  LK-M03B-OPER        PIC X(01).
      public NChar lkM03bOper = new NChar(1);

      // COB:              88  M03B-OPEN       VALUE 'O'.
      public boolean m03bOpen() {
        return lkM03bOper.equals("O");
      }

      public void setM03bOpen(boolean value) {
        if (value) lkM03bOper.setValue("O");
      }

      // COB:              88  M03B-CLOSE      VALUE 'C'.
      public boolean m03bClose() {
        return lkM03bOper.equals("C");
      }

      public void setM03bClose(boolean value) {
        if (value) lkM03bOper.setValue("C");
      }

      // COB:              88  M03B-READ       VALUE 'R'.
      public boolean m03bRead() {
        return lkM03bOper.equals("R");
      }

      public void setM03bRead(boolean value) {
        if (value) lkM03bOper.setValue("R");
      }

      // COB:              88  M03B-READ-K     VALUE 'K'.
      public boolean m03bReadK() {
        return lkM03bOper.equals("K");
      }

      public void setM03bReadK(boolean value) {
        if (value) lkM03bOper.setValue("K");
      }

      // COB:              88  M03B-WRITE      VALUE 'W'.
      public boolean m03bWrite() {
        return lkM03bOper.equals("W");
      }

      public void setM03bWrite(boolean value) {
        if (value) lkM03bOper.setValue("W");
      }

      // COB:              88  M03B-REWRITE    VALUE 'Z'.
      public boolean m03bRewrite() {
        return lkM03bOper.equals("Z");
      }

      public void setM03bRewrite(boolean value) {
        if (value) lkM03bOper.setValue("Z");
      }

      // COB:            05  LK-M03B-RC          PIC X(02).
      public NChar lkM03bRc = new NChar(2);
      // COB:            05  LK-M03B-KEY         PIC X(25).
      public NChar lkM03bKey = new NChar(25);
      // COB:            05  LK-M03B-KEY-LN      PIC S9(4).
      public NZoned lkM03bKeyLn = new NZoned(4);
      // COB:            05  LK-M03B-FLDT        PIC X(1000).
      public NChar lkM03bFldt = new NChar(1000);
    }
  }

  final Cbstm03bLinkage params = new Cbstm03bLinkage();

  public CBSTM03B(Context context) {
    super(context);
  }

  @ProgramInit
  protected void initialize() {
    trnxFile =
        new KeySequentialFile(context, "TRNXFILE") //
            .recordKeyIs(fs.fdTrnxfileRec.fdTrnxsId) //
            .fileStatusIs(ws.trnxfileStatus) //
            .recordIs(fs.fdTrnxfileRec);
    xrefFile =
        new KeySequentialFile(context, "XREFFILE") //
            .recordKeyIs(fs.fdXreffileRec.fdXrefCardNum) //
            .fileStatusIs(ws.xreffileStatus) //
            .recordIs(fs.fdXreffileRec);
    custFile =
        new KeySequentialFile(context, "CUSTFILE") //
            .recordKeyIs(fs.fdCustfileRec.fdCustId) //
            .fileStatusIs(ws.custfileStatus) //
            .recordIs(fs.fdCustfileRec);
    acctFile =
        new KeySequentialFile(context, "ACCTFILE") //
            .recordKeyIs(fs.fdAcctfileRec.fdAcctId) //
            .fileStatusIs(ws.acctfileStatus) //
            .recordIs(fs.fdAcctfileRec);
  }

  @Override
  protected int procedure(AbstractNField... args) throws ContextException {
    if (args != null) {
      params.allocate();
      if (args.length > 0) params.lkM03bArea.setAddress(args[0].getAddress());
    }
    rcNext = Flow._0000Start;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case _0000Start:
          rcNext = _0000Start();
          if (rcNext.equals(Flow.Exit)) {
            rcNext = Flow._9999Goback;
          }
          break;
        case _9999Goback:
          _9999Goback();
          rcNext = Flow.Exit;
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    return RETURN_CODE;
  }

  /***/
  protected Flow _0000Start() {
    // COB(118): EVALUATE LK-M03B-DD
    // COB(119): WHEN 'TRNXFILE'
    if (params.lkM03bArea.lkM03bDd.equals("TRNXFILE")) {
      // COB(120): PERFORM 1000-TRNXFILE-PROC THRU 1999-EXIT
      rcNext = Flow._1000TrnxfileProc;
      while (!rcNext.equals(Flow.Exit)) {
        switch (rcNext) {
          case _1000TrnxfileProc:
            rcNext = _1000TrnxfileProc();
            if (rcNext.equals(Flow.Exit)) {
              rcNext = Flow._1900Exit;
            }
            break;
          case _1900Exit:
            _1900Exit();
            rcNext = Flow.Exit;
            break;
          default:
            throw new RuntimeException("Invalid flow option: " + rcNext);
        }
      }
      // COB(121): WHEN 'XREFFILE'
    } else if (params.lkM03bArea.lkM03bDd.equals("XREFFILE")) {
      // COB(122): PERFORM 2000-XREFFILE-PROC THRU 2999-EXIT
      rcNext = Flow._2000XreffileProc;
      while (!rcNext.equals(Flow.Exit)) {
        switch (rcNext) {
          case _2000XreffileProc:
            rcNext = _2000XreffileProc();
            if (rcNext.equals(Flow.Exit)) {
              rcNext = Flow._2900Exit;
            }
            break;
          case _2900Exit:
            _2900Exit();
            rcNext = Flow.Exit;
            break;
          default:
            throw new RuntimeException("Invalid flow option: " + rcNext);
        }
      }
      // COB(123): WHEN 'CUSTFILE'
    } else if (params.lkM03bArea.lkM03bDd.equals("CUSTFILE")) {
      // COB(124): PERFORM 3000-CUSTFILE-PROC THRU 3999-EXIT
      rcNext = Flow._3000CustfileProc;
      while (!rcNext.equals(Flow.Exit)) {
        switch (rcNext) {
          case _3000CustfileProc:
            rcNext = _3000CustfileProc();
            if (rcNext.equals(Flow.Exit)) {
              rcNext = Flow._3900Exit;
            }
            break;
          case _3900Exit:
            _3900Exit();
            rcNext = Flow.Exit;
            break;
          default:
            throw new RuntimeException("Invalid flow option: " + rcNext);
        }
      }
      // COB(125): WHEN 'ACCTFILE'
    } else if (params.lkM03bArea.lkM03bDd.equals("ACCTFILE")) {
      // COB(126): PERFORM 4000-ACCTFILE-PROC THRU 4999-EXIT
      rcNext = Flow._4000AcctfileProc;
      while (!rcNext.equals(Flow.Exit)) {
        switch (rcNext) {
          case _4000AcctfileProc:
            rcNext = _4000AcctfileProc();
            if (rcNext.equals(Flow.Exit)) {
              rcNext = Flow._4900Exit;
            }
            break;
          case _4900Exit:
            _4900Exit();
            rcNext = Flow.Exit;
            break;
          default:
            throw new RuntimeException("Invalid flow option: " + rcNext);
        }
      }
      // COB(127): WHEN OTHER
    } else {
      // COB(128): GO TO 9999-GOBACK
      return Flow._9999Goback;
      // COB(129): END-EVALUATE.
    }
    return Flow.Exit;
  }

  /***/
  protected void _9999Goback() {
    // COB(132): GOBACK.
    context.goback();
  }

  /***/
  protected Flow _1000TrnxfileProc() {
    // COB(136): IF M03B-OPEN
    if (params.lkM03bArea.m03bOpen()) {
      // COB(137): OPEN INPUT TRNX-FILE
      trnxFile.open(OpenMode.Input);
      // COB(138): GO TO 1900-EXIT
      return Flow._1900Exit;
      // COB(139): END-IF.
    }
    // COB(141): IF M03B-READ
    if (params.lkM03bArea.m03bRead()) {
      // COB(142): READ TRNX-FILE INTO LK-M03B-FLDT
      // COB(142): END-READ
      trnxFile.readNext(params.lkM03bArea.lkM03bFldt);
      // COB(144): GO TO 1900-EXIT
      return Flow._1900Exit;
      // COB(145): END-IF.
    }
    // COB(147): IF M03B-CLOSE
    if (params.lkM03bArea.m03bClose()) {
      // COB(148): CLOSE TRNX-FILE
      trnxFile.close();
      // COB(149): GO TO 1900-EXIT
      return Flow._1900Exit;
      // COB(150): END-IF.
    }
    return Flow.Exit;
  }

  /***/
  protected void _1900Exit() {
    // COB(153): MOVE TRNXFILE-STATUS TO LK-M03B-RC.
    params.lkM03bArea.lkM03bRc.setValue(ws.trnxfileStatus);
  }

  /***/
  protected Flow _2000XreffileProc() {
    // COB(160): IF M03B-OPEN
    if (params.lkM03bArea.m03bOpen()) {
      // COB(161): OPEN INPUT XREF-FILE
      xrefFile.open(OpenMode.Input);
      // COB(162): GO TO 2900-EXIT
      return Flow._2900Exit;
      // COB(163): END-IF.
    }
    // COB(165): IF M03B-READ
    if (params.lkM03bArea.m03bRead()) {
      // COB(166): READ XREF-FILE INTO LK-M03B-FLDT
      // COB(166): END-READ
      xrefFile.readNext(params.lkM03bArea.lkM03bFldt);
      // COB(168): GO TO 2900-EXIT
      return Flow._2900Exit;
      // COB(169): END-IF.
    }
    // COB(171): IF M03B-CLOSE
    if (params.lkM03bArea.m03bClose()) {
      // COB(172): CLOSE XREF-FILE
      xrefFile.close();
      // COB(173): GO TO 2900-EXIT
      return Flow._2900Exit;
      // COB(174): END-IF.
    }
    return Flow.Exit;
  }

  /***/
  protected void _2900Exit() {
    // COB(177): MOVE XREFFILE-STATUS TO LK-M03B-RC.
    params.lkM03bArea.lkM03bRc.setValue(ws.xreffileStatus);
  }

  /***/
  protected Flow _3000CustfileProc() {
    // COB(184): IF M03B-OPEN
    if (params.lkM03bArea.m03bOpen()) {
      // COB(185): OPEN INPUT CUST-FILE
      custFile.open(OpenMode.Input);
      // COB(186): GO TO 3900-EXIT
      return Flow._3900Exit;
      // COB(187): END-IF.
    }
    // COB(189): IF M03B-READ-K
    if (params.lkM03bArea.m03bReadK()) {
      // COB(190): MOVE LK-M03B-KEY (1:LK-M03B-KEY-LN) TO FD-CUST-ID
      fs.fdCustfileRec.fdCustId.setValue(
          params.lkM03bArea.lkM03bKey, 0, params.lkM03bArea.lkM03bKeyLn.getInt());
      // COB(191): READ CUST-FILE INTO LK-M03B-FLDT
      // COB(191): END-READ
      custFile.readInto(params.lkM03bArea.lkM03bFldt);
      // COB(193): GO TO 3900-EXIT
      return Flow._3900Exit;
      // COB(194): END-IF.
    }
    // COB(196): IF M03B-CLOSE
    if (params.lkM03bArea.m03bClose()) {
      // COB(197): CLOSE CUST-FILE
      custFile.close();
      // COB(198): GO TO 3900-EXIT
      return Flow._3900Exit;
      // COB(199): END-IF.
    }
    return Flow.Exit;
  }

  /***/
  protected void _3900Exit() {
    // COB(202): MOVE CUSTFILE-STATUS TO LK-M03B-RC.
    params.lkM03bArea.lkM03bRc.setValue(ws.custfileStatus);
  }

  /***/
  protected Flow _4000AcctfileProc() {
    // COB(209): IF M03B-OPEN
    if (params.lkM03bArea.m03bOpen()) {
      // COB(210): OPEN INPUT ACCT-FILE
      acctFile.open(OpenMode.Input);
      // COB(211): GO TO 4900-EXIT
      return Flow._4900Exit;
      // COB(212): END-IF.
    }
    // COB(214): IF M03B-READ-K
    if (params.lkM03bArea.m03bReadK()) {
      // COB(215): MOVE LK-M03B-KEY (1:LK-M03B-KEY-LN) TO FD-ACCT-ID
      fs.fdAcctfileRec.fdAcctId.setValue(
          params.lkM03bArea.lkM03bKey, 0, params.lkM03bArea.lkM03bKeyLn.getInt());
      // COB(216): READ ACCT-FILE INTO LK-M03B-FLDT
      // COB(216): END-READ
      acctFile.readInto(params.lkM03bArea.lkM03bFldt);
      // COB(218): GO TO 4900-EXIT
      return Flow._4900Exit;
      // COB(219): END-IF.
    }
    // COB(221): IF M03B-CLOSE
    if (params.lkM03bArea.m03bClose()) {
      // COB(222): CLOSE ACCT-FILE
      acctFile.close();
      // COB(223): GO TO 4900-EXIT
      return Flow._4900Exit;
      // COB(224): END-IF.
    }
    return Flow.Exit;
  }

  /***/
  protected void _4900Exit() {
    // COB(227): MOVE ACCTFILE-STATUS TO LK-M03B-RC.
    params.lkM03bArea.lkM03bRc.setValue(ws.acctfileStatus);
  }
}
