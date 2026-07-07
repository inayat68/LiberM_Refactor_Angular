package com.aws.carddemo.batch.program;

import com.aws.carddemo.batch.program.storage.cbcus01c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.io.*;
import com.nib.commons.io.vsam.*;
import com.nib.commons.storage.*;

public class CBCUS01C extends AbstractProgram {
  private KeySequentialFile custfileFile;
  final Cbcus01cRecords fs = new Cbcus01cRecords();

  @ProgramStorage final Cbcus01cWorking ws = new Cbcus01cWorking();

  // Linkage
  public static class Cbcus01cLinkage extends NGroup {}

  final Cbcus01cLinkage params = new Cbcus01cLinkage();

  public CBCUS01C(Context context) {
    super(context);
  }

  @ProgramInit
  protected void initialize() {
    custfileFile =
        new KeySequentialFile(context, "CUSTFILE") //
            .recordKeyIs(fs.fdCustfileRec.fdCustId) //
            .fileStatusIs(ws.custfileStatus) //
            .recordIs(fs.fdCustfileRec);
  }

  @Override
  protected int procedure(AbstractNField... args) throws ContextException {
    if (args != null) {
      params.allocate();
    }
    procedure();
    return RETURN_CODE;
  }

  /***
   *****************************************************************/
  protected void procedure() {
    // COB(71): DISPLAY 'START OF EXECUTION OF PROGRAM CBCUS01C'.
    sysout.display("START OF EXECUTION OF PROGRAM CBCUS01C");
    // COB(72): PERFORM 0000-CUSTFILE-OPEN.
    _0000CustfileOpen();
    // COB(74): PERFORM UNTIL END-OF-FILE = 'Y'
    while (!ws.endOfFile.equals("Y")) {
      // COB(75): IF  END-OF-FILE = 'N'
      if (ws.endOfFile.equals("N")) {
        // COB(76): PERFORM 1000-CUSTFILE-GET-NEXT
        _1000CustfileGetNext();
        // COB(77): IF  END-OF-FILE = 'N'
        if (ws.endOfFile.equals("N")) {
          // COB(78): DISPLAY CUSTOMER-RECORD
          sysout.display(ws.cvcus01y.customerRecord);
          // COB(79): END-IF
        }
        // COB(80): END-IF
      }
      // COB(81): END-PERFORM.
    }
    // COB(83): PERFORM 9000-CUSTFILE-CLOSE.
    _9000CustfileClose();
    // COB(85): DISPLAY 'END OF EXECUTION OF PROGRAM CBCUS01C'.
    sysout.display("END OF EXECUTION OF PROGRAM CBCUS01C");
    // COB(87): GOBACK.
    context.goback();
  }

  /***
   *****************************************************************
   * I/O ROUTINES TO ACCESS A KSDS, VSAM DATA SET...               *
   *****************************************************************/
  protected void _1000CustfileGetNext() {
    // COB(93): READ CUSTFILE-FILE INTO CUSTOMER-RECORD.
    custfileFile.readNext(ws.cvcus01y.customerRecord);
    // COB(94): IF  CUSTFILE-STATUS = '00'
    if (ws.custfileStatus.equals("00")) {
      // COB(95): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(96): DISPLAY CUSTOMER-RECORD
      sysout.display(ws.cvcus01y.customerRecord);
      // COB(97): ELSE
    } else {
      // COB(98): IF  CUSTFILE-STATUS = '10'
      if (ws.custfileStatus.equals("10")) {
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
        // COB(110): DISPLAY 'ERROR READING CUSTOMER FILE'
        sysout.display("ERROR READING CUSTOMER FILE");
        // COB(111): MOVE CUSTFILE-STATUS TO IO-STATUS
        ws.ioStatus.setValue(ws.custfileStatus);
        // COB(112): PERFORM Z-DISPLAY-IO-STATUS
        zDisplayIoStatus();
        // COB(113): PERFORM Z-ABEND-PROGRAM
        zAbendProgram();
        // COB(114): END-IF
      }
      // COB(115): END-IF
    }
    // COB(116): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _0000CustfileOpen() {
    // COB(119): MOVE 8 TO APPL-RESULT.
    ws.applResult.setValue(8);
    // COB(120): OPEN INPUT CUSTFILE-FILE
    custfileFile.open(OpenMode.Input);
    // COB(121): IF  CUSTFILE-STATUS = '00'
    if (ws.custfileStatus.equals("00")) {
      // COB(122): MOVE 0 TO APPL-RESULT
      ws.applResult.setValue(0);
      // COB(123): ELSE
    } else {
      // COB(124): MOVE 12 TO APPL-RESULT
      ws.applResult.setValue(12);
      // COB(125): END-IF
    }
    // COB(126): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(127): CONTINUE
      // do nothing
      // COB(128): ELSE
    } else {
      // COB(129): DISPLAY 'ERROR OPENING CUSTFILE'
      sysout.display("ERROR OPENING CUSTFILE");
      // COB(130): MOVE CUSTFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.custfileStatus);
      // COB(131): PERFORM Z-DISPLAY-IO-STATUS
      zDisplayIoStatus();
      // COB(132): PERFORM Z-ABEND-PROGRAM
      zAbendProgram();
      // COB(133): END-IF
    }
    // COB(134): EXIT.
    return;
  }

  /***---------------------------------------------------------------*/
  protected void _9000CustfileClose() {
    // COB(137): ADD 8 TO ZERO GIVING APPL-RESULT.
    ws.applResult.add(8);
    // COB(138): CLOSE CUSTFILE-FILE
    custfileFile.close();
    // COB(139): IF  CUSTFILE-STATUS = '00'
    if (ws.custfileStatus.equals("00")) {
      // COB(140): SUBTRACT APPL-RESULT FROM APPL-RESULT
      ws.applResult.subtract(ws.applResult);
      // COB(141): ELSE
    } else {
      // COB(142): ADD 12 TO ZERO GIVING APPL-RESULT
      ws.applResult.add(12);
      // COB(143): END-IF
    }
    // COB(144): IF  APPL-AOK
    if (ws.applAok()) {
      // COB(145): CONTINUE
      // do nothing
      // COB(146): ELSE
    } else {
      // COB(147): DISPLAY 'ERROR CLOSING CUSTOMER FILE'
      sysout.display("ERROR CLOSING CUSTOMER FILE");
      // COB(148): MOVE CUSTFILE-STATUS TO IO-STATUS
      ws.ioStatus.setValue(ws.custfileStatus);
      // COB(149): PERFORM Z-DISPLAY-IO-STATUS
      zDisplayIoStatus();
      // COB(150): PERFORM Z-ABEND-PROGRAM
      zAbendProgram();
      // COB(151): END-IF
    }
    // COB(152): EXIT.
    return;
  }

  /***/
  protected void zAbendProgram() {
    // COB(155): DISPLAY 'ABENDING PROGRAM'
    sysout.display("ABENDING PROGRAM");
    // COB(156): MOVE 0 TO TIMING
    ws.timing.setValue(0);
    // COB(157): MOVE 999 TO ABCODE
    ws.abcode.setValue(999);
    // COB(158): CALL 'CEE3ABD'.
    throw new AbendException(ws.abcode.getInt());
  }

  /***
   *****************************************************************/
  protected void zDisplayIoStatus() {
    // COB(162): IF  IO-STATUS NOT NUMERIC
    // COB(162): OR  IO-STAT1 = '9'
    if (!ws.ioStatus.isNumeric() || ws.ioStatus.ioStat1.equals("9")) {
      // COB(164): MOVE IO-STAT1 TO IO-STATUS-04(1:1)
      ws.ioStatus04.setValue(ws.ioStatus.ioStat1, 0, 1);
      // COB(165): MOVE 0        TO TWO-BYTES-BINARY
      ws.twoBytesBinary.setValue(0);
      // COB(166): MOVE IO-STAT2 TO TWO-BYTES-RIGHT
      ws.twoBytesAlpha.twoBytesRight.setValue(ws.ioStatus.ioStat2);
      // COB(167): MOVE TWO-BYTES-BINARY TO IO-STATUS-0403
      ws.ioStatus04.ioStatus0403.setValue(ws.twoBytesBinary);
      // COB(168): DISPLAY 'FILE STATUS IS: NNNN' IO-STATUS-04
      sysout.display("FILE STATUS IS: NNNN", ws.ioStatus04);
      // COB(169): ELSE
    } else {
      // COB(170): MOVE '0000' TO IO-STATUS-04
      ws.ioStatus04.setString("0000");
      // COB(171): MOVE IO-STATUS TO IO-STATUS-04(3:2)
      ws.ioStatus04.setValue(ws.ioStatus, 0, 2, 2);
      // COB(172): DISPLAY 'FILE STATUS IS: NNNN' IO-STATUS-04
      sysout.display("FILE STATUS IS: NNNN", ws.ioStatus04);
      // COB(173): END-IF
    }
    // COB(174): EXIT.
    return;
  }
}
