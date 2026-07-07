package com.aws.carddemo.batch.program;

import com.aws.carddemo.batch.program.storage.cobtupdt.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.esql.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.io.*;
import com.nib.commons.io.vsam.*;
import com.nib.commons.storage.*;

public class COBTUPDT extends AbstractProgram {
  private SequentialFile trRecord;
  final CobtupdtRecords fs = new CobtupdtRecords();

  protected enum Flow {
    Exit,
    // 0001-OPEN-FILES
    _0001OpenFiles,
    // 1001-READ-NEXT-RECORDS
    _1001ReadNextRecords
  }

  private Flow rcNext;

  private final Sqlca sqlca = new Sqlca(this);
  @ProgramStorage final CobtupdtWorking ws = new CobtupdtWorking();

  // Linkage
  public static class CobtupdtLinkage extends NGroup {}

  final CobtupdtLinkage params = new CobtupdtLinkage();

  public COBTUPDT(Context context) {
    super(context);
  }

  @ProgramInit
  protected void initialize() {
    trRecord =
        new SequentialFile(context, "INPFILE") //
            .fileStatusIs(ws.wsInfStatus) //
            .recordIs(fs.wsInputVars);
  }

  @Override
  protected int procedure(AbstractNField... args) throws ContextException {
    if (args != null) {
      params.allocate();
    }
    rcNext = Flow._0001OpenFiles;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case _0001OpenFiles:
          _0001OpenFiles();
          rcNext = Flow._1001ReadNextRecords;
          break;
        case _1001ReadNextRecords:
          _1001ReadNextRecords();
          rcNext = Flow.Exit;
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    return RETURN_CODE;
  }

  protected void _0001OpenFiles() {
    // COB(83): OPEN INPUT TR-RECORD.
    trRecord.open(OpenMode.Input);
    // COB(84): IF WS-INF-STATUS = '00' THEN
    if (ws.wsInfStatus.equals("00")) {
      // COB(85): DISPLAY 'OPEN FILE OK'
      sysout.display("OPEN FILE OK");
      // COB(86): ELSE
    } else {
      // COB(87): DISPLAY 'OPEN FILE NOT OK'
      sysout.display("OPEN FILE NOT OK");
      // COB(88): END-IF
    }
    // COB(89): EXIT.
    return;
  }

  protected void _1001ReadNextRecords() {
    // COB(92): PERFORM 1002-READ-RECORDS
    _1002ReadRecords();
    // COB(93): PERFORM UNTIL LASTREC = 'Y'
    while (!ws.flags.lastrec.equals("Y")) {
      // COB(94): PERFORM 1003-TREAT-RECORD
      _1003TreatRecord();
      // COB(95): PERFORM 1002-READ-RECORDS
      _1002ReadRecords();
      // COB(96): END-PERFORM.
    }
    // COB(97): PERFORM 2001-CLOSE-STOP
    _2001CloseStop();
    // COB(98): EXIT.
    // continue
    // COB(99): STOP RUN.
    context.stopRun(RETURN_CODE);
  }

  protected void _1002ReadRecords() {
    // COB(101): READ TR-RECORD NEXT RECORD INTO WS-INPUT-REC
    // COB(101): AT END MOVE 'Y' TO LASTREC
    // COB(101): END-READ.
    trRecord
        .readNext(ws.wsInputRec) //
        .atEnd(this::trRecordReadAtEnd);
    // COB(104): IF LASTREC NOT EQUAL TO 'Y' THEN
    if (!ws.flags.lastrec.equals("Y")) {
      // COB(105): DISPLAY 'PROCESSING   ' WS-INPUT-REC
      sysout.display("PROCESSING   ", ws.wsInputRec);
      // COB(106): END-IF.
    }
    // COB(107): EXIT.
    return;
  }

  protected void _1003TreatRecord() {
    // COB(110): EVALUATE INPUT-REC-TYPE
    // COB(111): WHEN 'A'
    if (ws.wsInputRec.inputRecType.equals("A")) {
      // COB(112): DISPLAY 'ADDING RECORD'
      sysout.display("ADDING RECORD");
      // COB(113): PERFORM 10031-INSERT-DB
      _10031InsertDb();
      // COB(114): WHEN 'U'
    } else if (ws.wsInputRec.inputRecType.equals("U")) {
      // COB(115): DISPLAY 'UPDATING RECORD'
      sysout.display("UPDATING RECORD");
      // COB(116): PERFORM 10032-UPDATE-DB
      _10032UpdateDb();
      // COB(117): WHEN 'D'
    } else if (ws.wsInputRec.inputRecType.equals("D")) {
      // COB(118): DISPLAY 'DELETING RECORD'
      sysout.display("DELETING RECORD");
      // COB(119): PERFORM 10033-DELETE-DB
      _10033DeleteDb();
      // COB(120): WHEN '*'
    } else if (ws.wsInputRec.inputRecType.equals("*")) {
      // COB(121): DISPLAY 'IGNORING COMMENTED LINE'
      sysout.display("IGNORING COMMENTED LINE");
      // COB(122): WHEN OTHER
    } else {
      // COB(123): STRING
      // COB(123): 'ERROR: TYPE NOT VALID'
      // COB(123): DELIMITED BY SIZE
      // COB(123): INTO WS-RETURN-MSG
      // COB(123): END-STRING
      ws.workingVariables.wsReturnMsg.concat("ERROR: TYPE NOT VALID");
      // COB(127): END-STRING
      // COB(128): PERFORM 9999-ABEND
      _9999Abend();
      // COB(129): END-EVALUATE.
    }
    // COB(130): EXIT.
    return;
  }

  protected void _10031InsertDb() {
    // COB(137): EXEC SQL
    // COB(137):      INSERT INTO CARDDEMO.TRANSACTION_TYPE
    // COB(137):      (
    // COB(137):      TR_TYPE,
    // COB(137):      TR_DESCRIPTION
    // COB(137):      )
    // COB(137):      VALUES
    // COB(137):      (
    // COB(137):      :INPUT-REC-NUMBER,
    // COB(137):      :INPUT-REC-DESC
    // COB(137):      )
    // COB(137): END-EXEC.
    // *****************************************************************
    //  SQL TO INSERT THE RECORD
    // *****************************************************************
    //
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand(
          "INSERT INTO CARDDEMO.TRANSACTION_TYPE (TR_TYPE , TR_DESCRIPTION) VALUES (? , ?)");
      sql.values
          .add(ws.wsInputRec.inputRecNumber) //
          .add(ws.wsInputRec.inputRecDesc);
      sql.execute();
    }
    // COB(149): MOVE SQLCODE TO WS-VAR-SQLCODE
    ws.wsMiscVars.wsVarSqlcode.setValue(sqlca.sqlcode);
    // COB(151): EVALUATE TRUE
    // COB(152): WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(153): DISPLAY 'RECORD INSERTED SUCCESSFULLY'
      sysout.display("RECORD INSERTED SUCCESSFULLY");
      // COB(154): WHEN SQLCODE < 0
    } else if (sqlca.sqlcode.lessThan(0)) {
      // COB(155): STRING
      // COB(155): 'Error accessing:'
      // COB(155): ' TRANSACTION_TYPE table. SQLCODE:'
      // COB(155): WS-VAR-SQLCODE
      // COB(155): DELIMITED BY SIZE
      // COB(155): INTO WS-RETURN-MSG
      // COB(155): END-STRING
      ws.workingVariables.wsReturnMsg.concat(
          "Error accessing:", " TRANSACTION_TYPE table. SQLCODE:", ws.wsMiscVars.wsVarSqlcode);
      // COB(161): END-STRING
      // COB(162): PERFORM 9999-ABEND
      _9999Abend();
      // COB(163): END-EVALUATE
    }
    // COB(164): EXIT.
    return;
  }

  protected void _10032UpdateDb() {
    // COB(171): EXEC SQL
    // COB(171):      UPDATE CARDDEMO.TRANSACTION_TYPE
    // COB(171):         SET TR_DESCRIPTION = :INPUT-REC-DESC
    // COB(171):       WHERE TR_TYPE = :INPUT-REC-NUMBER
    // COB(171): END-EXEC
    // *****************************************************************
    //  SQL TO UPDATE THE RECORD
    // *****************************************************************
    //
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand("UPDATE CARDDEMO.TRANSACTION_TYPE SET TR_DESCRIPTION = ? WHERE TR_TYPE = ?");
      sql.set.add(ws.wsInputRec.inputRecDesc);
      sql.where.add(ws.wsInputRec.inputRecNumber);
      sql.execute();
    }
    // COB(176): MOVE SQLCODE TO WS-VAR-SQLCODE
    ws.wsMiscVars.wsVarSqlcode.setValue(sqlca.sqlcode);
    // COB(177): EVALUATE TRUE
    // COB(178): WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(179): DISPLAY 'RECORD UPDATED SUCCESSFULLY'
      sysout.display("RECORD UPDATED SUCCESSFULLY");
      // COB(180): WHEN SQLCODE = +100
    } else if (sqlca.sqlcode.equals(100)) {
      // COB(181): STRING 'No records found.' DELIMITED BY SIZE
      // COB(181):    INTO WS-RETURN-MSG
      // COB(181): END-STRING
      ws.workingVariables.wsReturnMsg.concat("No records found.");
      // COB(183): END-STRING
      // COB(184): PERFORM 9999-ABEND
      _9999Abend();
      // COB(185): WHEN SQLCODE < 0
    } else if (sqlca.sqlcode.lessThan(0)) {
      // COB(186): STRING
      // COB(186): 'Error accessing:'
      // COB(186): ' TRANSACTION_TYPE table. SQLCODE:'
      // COB(186): WS-VAR-SQLCODE
      // COB(186): DELIMITED BY SIZE
      // COB(186): INTO WS-RETURN-MSG
      // COB(186): END-STRING
      ws.workingVariables.wsReturnMsg.concat(
          "Error accessing:", " TRANSACTION_TYPE table. SQLCODE:", ws.wsMiscVars.wsVarSqlcode);
      // COB(192): END-STRING
      // COB(193): PERFORM 9999-ABEND
      _9999Abend();
      // COB(194): END-EVALUATE
    }
    // COB(195): EXIT.
    return;
  }

  protected void _10033DeleteDb() {
    // COB(201): EXEC SQL
    // COB(201):      DELETE FROM CARDDEMO.TRANSACTION_TYPE
    // COB(201):       WHERE TR_TYPE = :INPUT-REC-NUMBER
    // COB(201): END-EXEC.
    // *****************************************************************
    //  SQL TO DELETE THE RECORD
    // *****************************************************************
    //
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand("DELETE FROM CARDDEMO.TRANSACTION_TYPE WHERE TR_TYPE = ?");
      sql.where.add(ws.wsInputRec.inputRecNumber);
      sql.execute();
    }
    // COB(205): MOVE SQLCODE TO WS-VAR-SQLCODE
    ws.wsMiscVars.wsVarSqlcode.setValue(sqlca.sqlcode);
    // COB(207): EVALUATE TRUE
    // COB(208): WHEN SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(209): DISPLAY 'RECORD DELETED SUCCESSFULLY'
      sysout.display("RECORD DELETED SUCCESSFULLY");
      // COB(210): WHEN SQLCODE = +100
    } else if (sqlca.sqlcode.equals(100)) {
      // COB(211): STRING 'No records found.' DELIMITED BY SIZE
      // COB(211): INTO WS-RETURN-MSG
      // COB(211): END-STRING
      ws.workingVariables.wsReturnMsg.concat("No records found.");
      // COB(213): END-STRING
      // COB(214): PERFORM 9999-ABEND
      _9999Abend();
      // COB(216): WHEN SQLCODE < 0
    } else if (sqlca.sqlcode.lessThan(0)) {
      // COB(217): STRING
      // COB(217): 'Error accessing:'
      // COB(217): ' TRANSACTION_TYPE table. SQLCODE:'
      // COB(217): WS-VAR-SQLCODE
      // COB(217): DELIMITED BY SIZE
      // COB(217): INTO WS-RETURN-MSG
      // COB(217): END-STRING
      ws.workingVariables.wsReturnMsg.concat(
          "Error accessing:", " TRANSACTION_TYPE table. SQLCODE:", ws.wsMiscVars.wsVarSqlcode);
      // COB(223): END-STRING
      // COB(224): PERFORM 9999-ABEND
      _9999Abend();
      // COB(225): END-EVALUATE
    }
    // COB(226): EXIT.
    return;
  }

  protected void _9999Abend() {
    // COB(231): DISPLAY WS-RETURN-MSG.
    sysout.display(ws.workingVariables.wsReturnMsg);
    // COB(232): MOVE 4 TO RETURN-CODE
    RETURN_CODE = 4;
    // COB(233): EXIT.
    return;
  }

  protected void _2001CloseStop() {
    // COB(235): CLOSE TR-RECORD.
    trRecord.close();
    // COB(236): EXIT.
    return;
  }

  protected void trRecordReadAtEnd() {
    // COB(102): AT END MOVE 'Y' TO LASTREC
    ws.flags.lastrec.setString("Y");
  }
}
