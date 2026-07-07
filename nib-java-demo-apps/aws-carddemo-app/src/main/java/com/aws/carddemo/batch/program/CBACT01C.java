package com.aws.carddemo.batch.program;

import com.aws.carddemo.batch.program.storage.cbact01c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.io.*;
import com.nib.commons.io.vsam.*;
import com.nib.commons.storage.*;

public class CBACT01C extends AbstractProgram {
  private KeySequentialFile acctfileFile;
  final Cbact01cRecords fs = new Cbact01cRecords();

  @ProgramStorage final Cbact01cWorking ws = new Cbact01cWorking();

  // Linkage
  public static class Cbact01cLinkage extends NGroup {}

  final Cbact01cLinkage params = new Cbact01cLinkage();

  public CBACT01C(Context context) {
    super(context);
  }

  @ProgramInit
  protected void initialize() {
    acctfileFile =
        new KeySequentialFile(context, "ACCTFILE") //
            .recordKeyIs(fs.fdAcctfileRec.fdAcctId) //
            .fileStatusIs(ws.acctfileStatus) //
            .recordIs(fs.fdAcctfileRec);
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
    // COB(71): DISPLAY 'START OF EXECUTION OF PROGRAM CBACT01C'.
    sysout.display("START OF EXECUTION OF PROGRAM CBACT01C");
    // COB(72): PERFORM 0000-ACCTFILE-OPEN.
    _0000AcctfileOpen();
    // COB(74): PERFORM UNTIL END-OF-FILE = 'Y'
    while (!ws.endOfFile.equals("Y")) {
      // COB(75): IF  END-OF-FILE = 'N'
      if (ws.endOfFile.equals("N")) {
        // COB(76): PERFORM 1000-ACCTFILE-GET-NEXT
        _1000AcctfileGetNext();
        // COB(77): IF  END-OF-FILE = 'N'
        if (ws.endOfFile.equals("N")) {
          // COB(78): DISPLAY ACCOUNT-RECORD
          sysout.display(ws.cvact01y.accountRecord);
          // COB(79): END-IF
        }
        // COB(80): END-IF
      }
      // COB(81): END-PERFORM.
    }
    // COB(83): PERFORM 9000-ACCTFILE-CLOSE.
    _9000AcctfileClose();
    // COB(85): DISPLAY 'END OF EXECUTION OF PROGRAM CBACT01C'.
    sysout.display("END OF EXECUTION OF PROGRAM CBACT01C");
    // COB(87): GOBACK.
    context.goback();
  }

  /*****************************************************************
   * I/O ROUTINES TO ACCESS A KSDS, VSAM DATA SET...               *
   *****************************************************************/
  protected void _1000AcctfileGetNext() {
    // COB(93): READ ACCTFILE-FILE INTO ACCOUNT-RECORD.
    acctfileFile.readNext(ws.cvact01y.accountRecord);
    // COB(94): IF  ACCTFILE-STATUS = '00'
    if (ws.acctfileStatus.equals("00")) {
      // COB(95): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(96): PERFORM 1100-DISPLAY-ACCT-RECORD
      _1100DisplayAcctRecord();
      // COB(97): ELSE
    } else {
      // COB(98): IF  ACCTFILE-STATUS = '10'
      if (ws.acctfileStatus.equals("10")) {
        // COB(99): MOVE 16 TO APPL-RESULT
        ws.applResult.setValue(16);
        // COB(100): ELSE
      } else {
        // COB(101): MOVE 12 TO APPL-RESULT
        ws.applResult.setValue(12);
        // COB(102): END-IF
      }
      // COB(103): END-IF
    }
    // COB(104): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(105): CONTINUE
      // do nothing
      // COB(106): ELSE
    } else {
      // COB(107): IF  APPL-EOF
      if (ws.applEof()) {
        // COB(108): MOVE 'Y' TO END-OF-FILE
        ws.endOfFile.setString("Y");
        // COB(109): ELSE
      } else {
        // COB(110): DISPLAY 'ERROR READING ACCOUNT FILE'
        sysout.display("ERROR READING ACCOUNT FILE");
        // COB(111): MOVE ACCTFILE-STATUS TO IO-STATUS
        ws.ioStatus.setValue(ws.acctfileStatus);
        // COB(112): PERFORM 9910-DISPLAY-IO-STATUS
        _9910DisplayIoStatus();
        // COB(113): PERFORM 9999-ABEND-PROGRAM
        _9999AbendProgram();
        // COB(114): END-IF
      }
      // COB(115): END-IF
    }
    // COB(116): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _1100DisplayAcctRecord() {
    // COB(119): DISPLAY 'ACCT-ID                 :'   ACCT-ID
    sysout.display("ACCT-ID                 :", ws.cvact01y.accountRecord.acctId);
    // COB(120): DISPLAY 'ACCT-ACTIVE-STATUS      :'   ACCT-ACTIVE-STATUS
    sysout.display("ACCT-ACTIVE-STATUS      :", ws.cvact01y.accountRecord.acctActiveStatus);
    // COB(121): DISPLAY 'ACCT-CURR-BAL           :'   ACCT-CURR-BAL
    sysout.display("ACCT-CURR-BAL           :", ws.cvact01y.accountRecord.acctCurrBal);
    // COB(122): DISPLAY 'ACCT-CREDIT-LIMIT       :'   ACCT-CREDIT-LIMIT
    sysout.display("ACCT-CREDIT-LIMIT       :", ws.cvact01y.accountRecord.acctCreditLimit);
    // COB(123): DISPLAY 'ACCT-CASH-CREDIT-LIMIT  :'   ACCT-CASH-CREDIT-LIMIT
    sysout.display("ACCT-CASH-CREDIT-LIMIT  :", ws.cvact01y.accountRecord.acctCashCreditLimit);
    // COB(124): DISPLAY 'ACCT-OPEN-DATE          :'   ACCT-OPEN-DATE
    sysout.display("ACCT-OPEN-DATE          :", ws.cvact01y.accountRecord.acctOpenDate);
    // COB(125): DISPLAY 'ACCT-EXPIRAION-DATE     :'   ACCT-EXPIRAION-DATE
    sysout.display("ACCT-EXPIRAION-DATE     :", ws.cvact01y.accountRecord.acctExpiraionDate);
    // COB(126): DISPLAY 'ACCT-REISSUE-DATE       :'   ACCT-REISSUE-DATE
    sysout.display("ACCT-REISSUE-DATE       :", ws.cvact01y.accountRecord.acctReissueDate);
    // COB(127): DISPLAY 'ACCT-CURR-CYC-CREDIT    :'   ACCT-CURR-CYC-CREDIT
    sysout.display("ACCT-CURR-CYC-CREDIT    :", ws.cvact01y.accountRecord.acctCurrCycCredit);
    // COB(128): DISPLAY 'ACCT-CURR-CYC-DEBIT     :'   ACCT-CURR-CYC-DEBIT
    sysout.display("ACCT-CURR-CYC-DEBIT     :", ws.cvact01y.accountRecord.acctCurrCycDebit);
    // COB(129): DISPLAY 'ACCT-GROUP-ID           :'   ACCT-GROUP-ID
    sysout.display("ACCT-GROUP-ID           :", ws.cvact01y.accountRecord.acctGroupId);
    // COB(130): DISPLAY '-------------------------------------------------'
    sysout.display("-------------------------------------------------");
    // COB(131): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0000AcctfileOpen() {
    // COB(134): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(135): OPEN INPUT ACCTFILE-FILE
    acctfileFile.open(OpenMode.Input);
    // COB(136): IF  ACCTFILE-STATUS = '00'
    if (ws.acctfileStatus.equals("00")) {
      // COB(137): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(138): ELSE
    } else {
      // COB(139): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(140): END-IF
    }
    // COB(141): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(142): CONTINUE
      // do nothing
      // COB(143): ELSE
    } else {
      // COB(144): DISPLAY 'ERROR OPENING ACCTFILE'
      sysout.display("ERROR OPENING ACCTFILE");
      // COB(145): MOVE ACCTFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.acctfileStatus);
      // COB(146): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(147): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(148): END-IF
    }
    // COB(149): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9000AcctfileClose() {
    // COB(152): ADD 8 TO ZERO GIVING APPL-RESULT.
    ws.applResult.add(8);
    // COB(153): CLOSE ACCTFILE-FILE
    acctfileFile.close();
    // COB(154): IF  ACCTFILE-STATUS = '00'
    if (ws.acctfileStatus.equals("00")) {
      // COB(155): SUBTRACT APPL-RESULT FROM APPL-RESULT
      ws.applResult.subtract(ws.applResult);
      // COB(156): ELSE
    } else {
      // COB(157): ADD 12 TO ZERO GIVING APPL-RESULT
      ws.applResult.add(12);
      // COB(158): END-IF
    }
    // COB(159): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(160): CONTINUE
      // do nothing
      // COB(161): ELSE
    } else {
      // COB(162): DISPLAY 'ERROR CLOSING ACCOUNT FILE'
      sysout.display("ERROR CLOSING ACCOUNT FILE");
      // COB(163): MOVE ACCTFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.acctfileStatus);
      // COB(164): PERFORM 9910-DISPLAY-IO-STATUS
      _9910DisplayIoStatus();
      // COB(165): PERFORM 9999-ABEND-PROGRAM
      _9999AbendProgram();
      // COB(166): END-IF
    }
    // COB(167): EXIT.
    return;
  }

  protected void _9999AbendProgram() {
    // COB(170): DISPLAY 'ABENDING PROGRAM'
    sysout.display("ABENDING PROGRAM");
    // COB(171): MOVE 0 TO TIMING
    ws.timing.setValue(0);
    // COB(172): MOVE 999 TO ABCODE
    ws.abcode.setValue(999);
    // COB(173): CALL 'CEE3ABD'.
    throw new AbendException(ws.abcode.getInt());
  }

  /*****************************************************************/
  protected void _9910DisplayIoStatus() {
    // COB(177): IF  IO-STATUS NOT NUMERIC
    // COB(177): OR  IO-STAT1 = '9'
    if (!ws.ioStatus.isNumeric() || ws.ioStatus.ioStat1.equals("9")) {
      // COB(179): MOVE IO-STAT1 TO IO-STATUS-04(1:1)
      ws.ioStatus04.setValue(ws.ioStatus.ioStat1, 0, 1);
      // COB(180): MOVE 0        TO TWO-BYTES-BINARY
      ws.twoBytesBinary.setValue(0);
      // COB(181): MOVE IO-STAT2 TO TWO-BYTES-RIGHT
      ws.twoBytesAlpha.twoBytesRight.setValue(ws.ioStatus.ioStat2);
      // COB(182): MOVE TWO-BYTES-BINARY TO IO-STATUS-0403
      ws.ioStatus04.ioStatus0403.setValue(ws.twoBytesBinary);
      // COB(183): DISPLAY 'FILE STATUS IS: NNNN' IO-STATUS-04
      sysout.display("FILE STATUS IS: NNNN", ws.ioStatus04);
      // COB(184): ELSE
    } else {
      // COB(185): MOVE '0000' TO IO-STATUS-04
      ws.ioStatus04.setString("0000");
      // COB(186): MOVE IO-STATUS TO IO-STATUS-04(3:2)
      ws.ioStatus04.setValue(ws.ioStatus, 0, 2, 2);
      // COB(187): DISPLAY 'FILE STATUS IS: NNNN' IO-STATUS-04
      sysout.display("FILE STATUS IS: NNNN", ws.ioStatus04);
      // COB(188): END-IF
    }
    // COB(189): EXIT.
    return;
  }
}
