package com.aws.carddemo.batch.program;

import com.aws.carddemo.batch.program.storage.dsn8bc3.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.esql.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.io.*;
import com.nib.commons.io.vsam.*;
import com.nib.commons.storage.*;
import com.nib.commons.utils.StringTools;

public class DSN8BC3 extends AbstractProgram {
  private SequentialFile cardin;
  private SequentialFile repout;
  final Dsn8bc3Records fs = new Dsn8bc3Records();

  protected enum Flow {
    Exit,
    // DBERROR
    dberror,
    // PROCEDURE
    procedure,
    // PROG-END
    progEnd,
    // PROG-START
    progStart
  }

  private Flow rcNext;

  private final Sqlca sqlca = new Sqlca(this);
  private NSqlCursor tele1;
  private NSqlCursor tele2;
  private NSqlCursor tele3;
  @ProgramStorage final Dsn8bc3Working ws = new Dsn8bc3Working();

  // Linkage
  public static class Dsn8bc3Linkage extends NGroup {}

  final Dsn8bc3Linkage params = new Dsn8bc3Linkage();

  public DSN8BC3(Context context) {
    super(context);
  }

  @ProgramInit
  protected void initialize() {
    cardin =
        new SequentialFile(context, "CARDIN") //
            .recordIs(fs.cardrec);
    repout =
        new SequentialFile(context, "REPORT") //
            .recordIs(fs.reprec);
    tele1 = new NSqlCursor(this, sqlca, "SELECT * FROM VPHONE");
    tele2 =
        new NSqlCursor(
            this, sqlca, "SELECT * FROM VPHONE WHERE LASTNAME LIKE ? AND FIRSTNAME LIKE ?");
    tele2
        .where
        .add(ws.lnameWork) //
        .add(ws.fnameWork);
    tele3 =
        new NSqlCursor(this, sqlca, "SELECT * FROM VPHONE WHERE LASTNAME = ? AND FIRSTNAME LIKE ?");
    tele3
        .where
        .add(ws.ioarea.lname) //
        .add(ws.fnameWork);
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
          rcNext = Flow.progStart;
          break;
        case progStart:
          progStart();
          rcNext = Flow.progEnd;
          break;
        case progEnd:
          progEnd();
          rcNext = Flow.Exit;
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    return RETURN_CODE;
  }

  protected void procedure() {
    // COB(336): EXEC SQL WHENEVER SQLERROR   GOTO DBERROR END-EXEC.
    // ------------------
    // ****************************************************
    //  SQL RETURN CODE HANDLING                          *
    // ****************************************************
    //
    sqlca.whenever(SqlCondition.Error, this::handleSqlDberror);
    // COB(337): EXEC SQL WHENEVER SQLWARNING GOTO DBERROR END-EXEC.
    sqlca.whenever(SqlCondition.Warning, this::handleSqlDberror);
    // COB(338): EXEC SQL WHENEVER NOT FOUND  CONTINUE     END-EXEC.
    sqlca.whenever(SqlCondition.NotFound);
  }

  /*****************************************************
   * MAIN PROGRAM ROUTINE                              *
   *****************************************************/
  protected void progStart() {
    // COB(345): OPEN INPUT  CARDIN
    // COB(345):      OUTPUT REPOUT.
    //                                  **OPEN FILES
    cardin.open(OpenMode.Input);
    repout.open(OpenMode.Output);
    // COB(349): READ CARDIN RECORD INTO IOAREA
    // COB(349):    AT END MOVE 'N' TO INPUT-SWITCH.
    //                                  **GET FIRST INPUT
    cardin
        .readNext(ws.ioarea) //
        .atEnd(this::cardinReadAtEnd);
    // COB(353): PERFORM PROCESS-INPUT
    // COB(353):    UNTIL NOMORE-INPUT.
    //                                  **MAIN ROUTINE
    while (!ws.nomoreInput()) {
      processInput();
    }
  }

  protected void progEnd() {
    // COB(357): CLOSE CARDIN
    // COB(357):       REPOUT.
    //                                  **CLOSE FILES
    cardin.close();
    repout.close();
    // COB(359): GOBACK.
    context.goback();
  }

  /*****************************************************
   * CREATE REPORT HEADING                             *
   * SELECT ACTION                                     *
   *****************************************************/
  protected void processInput() {
    // COB(367): WRITE REPREC FROM REPHDR1
    // COB(367):    AFTER ADVANCING TO-TOP-OF-PAGE.
    //                                              **PRINT HEADING
    repout.writeAfter(Advancing.EnvironmentVariable("C01"), ws.rephdr1);
    // COB(369): WRITE REPREC FROM REPHDR2
    // COB(369):    AFTER ADVANCING 2 LINES.
    repout.writeAfter(Advancing.Lines(2), ws.rephdr2);
    // COB(371): WRITE REPREC FROM REPHDR3.
    repout.write(ws.rephdr3);
    // COB(374): IF ACTION = 'L'
    //                                              **SELECT ACTION
    if (ws.ioarea.action.equals("L")) {
      // COB(375): PERFORM LIST-FUNCTION
      listFunction();
      // COB(376): ELSE
    } else {
      // COB(377): IF ACTION = 'U'
      if (ws.ioarea.action.equals("U")) {
        // COB(378): PERFORM TELEPHONE-UPDATE
        telephoneUpdate();
        // COB(380): ELSE
      } else {
        // COB(383): MOVE '068E' TO MSGCODE
        //                                            **INVALID REQUEST
        //                                            **PRINT ERROR MESSAGE
        ws.msgcode.setString("068E");
        // COB(384): CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG
        context.call("DSN8MCG", ws.major, ws.msgcode, ws.outmsg);
        // COB(385): MOVE OUTMSG TO OUTMSG2
        ws.msgRec2.outmsg2.setValue(ws.outmsg);
        // COB(386): WRITE REPREC FROM MSG-REC2
        // COB(386):          AFTER ADVANCING 2 LINES.
        repout.writeAfter(Advancing.Lines(2), ws.msgRec2);
        // COB(387): AFTER ADVANCING 2 LINES.
      }
      // COB(387): AFTER ADVANCING 2 LINES.
    }
    // COB(388): READ CARDIN RECORD INTO IOAREA
    // COB(388):    AT END MOVE 'N' TO INPUT-SWITCH.
    cardin
        .readNext(ws.ioarea) //
        .atEnd(this::cardinReadAtEnd1);
  }

  /***
   *****************************************************
   * DETERMINE FORM OF NAME USED TO LIST EMPLOYEES     *
   *****************************************************/
  protected void listFunction() {
    // COB(396): IF LNAME = SPACES
    //                                            **NO LAST NAME GIVEN
    if (ws.ioarea.lname.isSpaces()) {
      // COB(397): MOVE '%' TO LNAME.
      ws.ioarea.lname.setString("%");
      // COB(397): MOVE '%' TO LNAME.
    }
    // COB(399): IF FNAME = SPACES
    //                                            **NO FIRST NAME GIVEN
    if (ws.ioarea.fname.isSpaces()) {
      // COB(400): MOVE '%' TO FNAME.
      ws.ioarea.fname.setString("%");
      // COB(400): MOVE '%' TO FNAME.
    }
    // COB(402): IF LNAME = '*'
    //                                            **LIST ALL EMPLOYEES
    if (ws.ioarea.lname.equals("*")) {
      // COB(403): PERFORM LIST-ALL
      listAll();
      // COB(404): ELSE
    } else {
      // COB(406): UNSTRING LNAME
      // COB(406):    DELIMITED BY SPACE
      // COB(406):    INTO     LNAME-WORKC
      // COB(406):    COUNT IN LNAME-WORKL
      //                                            **UNSTRING LAST NAME
      StringTools.unstring(ws.ioarea.lname)
          .delimited(' ')
          .into(ws.lnameWork.lnameWorkc)
          .count(ws.lnameWork.lnameWorkl)
          .exec();
      // COB(411): UNSTRING FNAME
      // COB(411):    DELIMITED BY SPACE
      // COB(411):    INTO     FNAME-WORKC
      // COB(411):    COUNT IN FNAME-WORKL
      //                                            **UNSTRING FIRST NAME
      StringTools.unstring(ws.ioarea.fname)
          .delimited(' ')
          .into(ws.fnameWork.fnameWorkc)
          .count(ws.fnameWork.fnameWorkl)
          .exec();
      // COB(416): MOVE ZERO TO PERCENT-COUNTER
      //                                             **COUNT %'S
      ws.percentCounter.zeros();
      // COB(417): INSPECT LNAME
      // COB(417):    TALLYING PERCENT-COUNTER FOR ALL '%'
      ws.percentCounter.setValue(ws.ioarea.lname.countStrings("%"));
      // COB(419): IF PERCENT-COUNTER > ZERO
      if (ws.percentCounter.greaterThan(0)) {
        // COB(424): PERFORM LIST-GENERIC
        //                                          **IF NO %'S THEN
        //                                          **LIST SPECIFIC NAME(S)
        //                                          **ELSE
        //                                          **LIST GENERIC NAME(S)
        listGeneric();
        // COB(425): ELSE
      } else {
        // COB(426): PERFORM LIST-SPECIFIC.
        listSpecific();
        // COB(426): PERFORM LIST-SPECIFIC.
      }
      // COB(426): PERFORM LIST-SPECIFIC.
    }
  }

  /***
   *****************************************************
   * LIST ALL EMPLOYEES                                *
   *****************************************************/
  protected void listAll() {
    // COB(433): EXEC SQL OPEN TELE1 END-EXEC.
    //                                            **OPEN CURSOR
    tele1.open();
    // COB(436): EXEC SQL FETCH TELE1 INTO :PPHONE END-EXEC.
    //                                            **GET EMPLOYEES
    tele1.into.add(ws.pphone);
    tele1.fetch();
    // COB(438): IF SQLCODE = NOT-FOUND
    if (sqlca.sqlcode.equals(ws.notFound)) {
      // COB(441): MOVE '008I' TO MSGCODE
      //                                            **NO EMPLOYEE FOUND
      //                                            **PRINT ERROR MESSAGE
      ws.msgcode.setString("008I");
      // COB(442): CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG
      context.call("DSN8MCG", ws.major, ws.msgcode, ws.outmsg);
      // COB(443): MOVE OUTMSG TO OUTMSG2
      ws.msgRec2.outmsg2.setValue(ws.outmsg);
      // COB(444): WRITE REPREC FROM MSG-REC2
      // COB(444):     AFTER ADVANCING 2 LINES
      repout.writeAfter(Advancing.Lines(2), ws.msgRec2);
      // COB(446): ELSE
    } else {
      // COB(448): PERFORM PRINT-AND-GET1
      // COB(448):    UNTIL SQLCODE IS NOT EQUAL TO ZERO.
      //                                            **LIST ALL EMPLOYEES
      while (sqlca.sqlcode.equals(0)) {
        printAndGet1();
      }
      // COB(449): UNTIL SQLCODE IS NOT EQUAL TO ZERO.
    }
    // COB(452): EXEC SQL CLOSE TELE1 END-EXEC.
    //                                            **CLOSE CURSOR
    tele1.close();
  }

  protected void printAndGet1() {
    // COB(455): PERFORM PRINT-A-LINE.
    printALine();
    // COB(456): EXEC SQL FETCH TELE1 INTO :PPHONE END-EXEC.
    tele1.into.add(ws.pphone);
    tele1.fetch();
  }

  /***
   *****************************************************
   * LIST GENERIC EMPLOYEES                            *
   *****************************************************/
  protected void listGeneric() {
    // COB(463): EXEC SQL OPEN  TELE2 END-EXEC.
    //                                            **OPEN CURSOR
    tele2.open();
    // COB(466): EXEC SQL FETCH TELE2 INTO :PPHONE END-EXEC.
    //                                            **GET EMPLOYEES
    tele2.into.add(ws.pphone);
    tele2.fetch();
    // COB(468): IF SQLCODE = NOT-FOUND
    if (sqlca.sqlcode.equals(ws.notFound)) {
      // COB(471): MOVE '008I' TO MSGCODE
      //                                            **NO EMPLOYEE FOUND
      //                                            **PRINT ERROR MESSAGE
      ws.msgcode.setString("008I");
      // COB(472): CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG
      context.call("DSN8MCG", ws.major, ws.msgcode, ws.outmsg);
      // COB(473): MOVE OUTMSG TO OUTMSG2
      ws.msgRec2.outmsg2.setValue(ws.outmsg);
      // COB(474): WRITE REPREC FROM MSG-REC2
      // COB(474):     AFTER ADVANCING 2 LINES
      repout.writeAfter(Advancing.Lines(2), ws.msgRec2);
      // COB(476): ELSE
    } else {
      // COB(478): PERFORM PRINT-AND-GET2
      // COB(478):    UNTIL SQLCODE IS NOT EQUAL TO ZERO.
      //                                       **LIST GENERIC EMPLOYEE(S)
      while (sqlca.sqlcode.equals(0)) {
        printAndGet2();
      }
      // COB(479): UNTIL SQLCODE IS NOT EQUAL TO ZERO.
    }
    // COB(482): EXEC SQL CLOSE TELE2 END-EXEC.
    //                                            **CLOSE CURSOR
    tele2.close();
  }

  protected void printAndGet2() {
    // COB(485): PERFORM PRINT-A-LINE.
    printALine();
    // COB(486): EXEC SQL FETCH TELE2 INTO :PPHONE END-EXEC.
    tele2.into.add(ws.pphone);
    tele2.fetch();
  }

  /***
   *****************************************************
   * LIST SPECIFIC EMPLOYEES                           *
   *****************************************************/
  protected void listSpecific() {
    // COB(493): EXEC SQL OPEN  TELE3 END-EXEC.
    //                                            **OPEN CURSOR
    tele3.open();
    // COB(496): EXEC SQL FETCH TELE3 INTO :PPHONE END-EXEC.
    //                                            **GET EMPLOYEES
    tele3.into.add(ws.pphone);
    tele3.fetch();
    // COB(498): IF SQLCODE = NOT-FOUND
    if (sqlca.sqlcode.equals(ws.notFound)) {
      // COB(501): MOVE '008I' TO MSGCODE
      //                                            **NO EMPLOYEE FOUND
      //                                            **PRINT ERROR MESSAGE
      ws.msgcode.setString("008I");
      // COB(502): CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG
      context.call("DSN8MCG", ws.major, ws.msgcode, ws.outmsg);
      // COB(503): MOVE OUTMSG TO OUTMSG2
      ws.msgRec2.outmsg2.setValue(ws.outmsg);
      // COB(504): WRITE REPREC FROM MSG-REC2
      // COB(504):     AFTER ADVANCING 2 LINES
      repout.writeAfter(Advancing.Lines(2), ws.msgRec2);
      // COB(506): ELSE
    } else {
      // COB(508): PERFORM PRINT-AND-GET3
      // COB(508):    UNTIL SQLCODE IS NOT EQUAL TO ZERO.
      //                                      **LIST SPECIFIC EMPLOYEE(S)
      while (sqlca.sqlcode.equals(0)) {
        printAndGet3();
      }
      // COB(509): UNTIL SQLCODE IS NOT EQUAL TO ZERO.
    }
    // COB(512): EXEC SQL CLOSE TELE3 END-EXEC.
    //                                            **CLOSE CURSOR
    tele3.close();
  }

  protected void printAndGet3() {
    // COB(515): PERFORM PRINT-A-LINE.
    printALine();
    // COB(516): EXEC SQL FETCH TELE3 INTO :PPHONE END-EXEC.
    tele3.into.add(ws.pphone);
    tele3.fetch();
  }

  /***
   *****************************************************
   * PRINT A LINE OF INFORMATION FROM DIRECTORY        *
   *****************************************************/
  protected void printALine() {
    // COB(523): MOVE LASTNAMEC TO RLNAME.
    //                                            **GET INFORMATION
    ws.repdata.rlname.setValue(ws.pphone.lastname.lastnamec);
    // COB(524): MOVE FIRSTNAMEC TO RFNAME.
    ws.repdata.rfname.setValue(ws.pphone.firstname.firstnamec);
    // COB(525): MOVE MIDDLEINITIAL TO RMIDINIT.
    ws.repdata.rmidinit.setValue(ws.pphone.middleinitial);
    // COB(526): MOVE PHONENUMBER OF PPHONE TO RPHONE.
    ws.repdata.rphone.setValue(ws.pphone.phonenumber);
    // COB(527): MOVE EMPLOYEENUMBER OF PPHONE TO REMPNO.
    ws.repdata.rempno.setValue(ws.pphone.employeenumber);
    // COB(528): MOVE DEPTNUMBER TO RDEPTNO.
    ws.repdata.rdeptno.setValue(ws.pphone.deptnumber);
    // COB(529): MOVE DEPTNAMEC TO RDEPTNAME.
    ws.repdata.rdeptname.setValue(ws.pphone.deptname.deptnamec);
    // COB(531): WRITE REPREC FROM REPDATA
    // COB(531):    AFTER ADVANCING 2 LINES.
    //                                            **PRINT INFORMATION
    repout.writeAfter(Advancing.Lines(2), ws.repdata);
    // COB(534): MOVE SPACES TO LASTNAMEC
    // COB(534):                FIRSTNAMEC
    // COB(534):                DEPTNAMEC.
    ws.pphone.lastname.lastnamec.spaces();
    ws.pphone.firstname.firstnamec.spaces();
    ws.pphone.deptname.deptnamec.spaces();
  }

  /***
   *****************************************************
   * UPDATES PHONE NUMBERS FOR EMPLOYEES               *
   *****************************************************/
  protected void telephoneUpdate() {
    // COB(542): EXEC SQL UPDATE VEMPLP
    // COB(542):             SET   PHONENUMBER    = :NEWNO
    // COB(542):             WHERE EMPLOYEENUMBER = :ENO END-EXEC.
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand("UPDATE VEMPLP SET PHONENUMBER = ? WHERE EMPLOYEENUMBER = ?");
      sql.set.add(ws.ioarea.newno);
      sql.where.add(ws.ioarea.eno);
      sql.execute();
    }
    // COB(545): IF SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(550): MOVE '004I' TO MSGCODE
      //                                          **EMPLOYEE FOUND
      //                                          **UPDATE SUCCESSFUL
      //                                          **PRINT CONFIRMATION
      //                                          **MESSAGE
      ws.msgcode.setString("004I");
      // COB(551): ELSE
    } else {
      // COB(555): MOVE '007E' TO MSGCODE.
      //                                            **NO EMPLOYEE FOUND
      //                                            **UPDATE FAILED
      //                                            **PRINT ERROR MESSAGE
      ws.msgcode.setString("007E");
      // COB(555): MOVE '007E' TO MSGCODE.
    }
    // COB(556): CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG.
    context.call("DSN8MCG", ws.major, ws.msgcode, ws.outmsg);
    // COB(557): MOVE OUTMSG TO OUTMSG2.
    ws.msgRec2.outmsg2.setValue(ws.outmsg);
    // COB(558): WRITE REPREC FROM MSG-REC2
    // COB(558):       AFTER ADVANCING 2 LINES.
    repout.writeAfter(Advancing.Lines(2), ws.msgRec2);
  }

  /***
   *****************************************************
   * SQL ERROR OCCURRED - GET ERROR MESSAGE            *
   *****************************************************/
  protected Flow dberror() {
    // COB(567): MOVE '060E' TO MSGCODE
    //                                            **SQL ERROR
    //                                            **PRINT ERROR MESSAGE
    ws.msgcode.setString("060E");
    // COB(568): CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG.
    context.call("DSN8MCG", ws.major, ws.msgcode, ws.outmsg);
    // COB(569): MOVE OUTMSG TO OUTMSG1 OF  MSG-REC1.
    ws.msgRec1.outmsg1.setValue(ws.outmsg);
    // COB(570): MOVE SQLCODE TO RETCODE OF MSG-REC1.
    ws.msgRec1.retcode.setValue(sqlca.sqlcode);
    // COB(571): WRITE REPREC FROM MSG-REC1
    // COB(571):    AFTER ADVANCING 2 LINES.
    repout.writeAfter(Advancing.Lines(2), ws.msgRec1);
    // COB(574): CALL 'DSNTIAR' USING SQLCA ERROR-MESSAGE ERROR-TEXT-LEN.
    context.call("DSNTIAR", sqlca, ws.errorMessage, ws.errorTextLen);
    // COB(575): IF RETURN-CODE = ZERO
    if (RETURN_CODE == 0) {
      // COB(576): PERFORM ERROR-PRINT VARYING ERROR-INDEX
      // COB(576):    FROM 1 BY 1 UNTIL ERROR-INDEX GREATER THAN 10
      ws.errorIndex.setValue(1);
      for (; !ws.errorIndex.greaterThan(10); ws.errorIndex.increment()) {
        errorPrint();
      }
      // COB(578): ELSE
    } else {
      // COB(583): MOVE '075E' TO MSGCODE
      //                                            **MESSAGE FORMAT
      //                                            **ROUTINE ERROR
      //                                            **PRINT ERROR MESSAGE
      ws.msgcode.setString("075E");
      // COB(584): CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG
      context.call("DSN8MCG", ws.major, ws.msgcode, ws.outmsg);
      // COB(585): MOVE OUTMSG TO OUTMSG1 OF MSG-REC1
      ws.msgRec1.outmsg1.setValue(ws.outmsg);
      // COB(586): MOVE RETURN-CODE TO RETCODE OF MSG-REC1
      ws.msgRec1.retcode.setValue(RETURN_CODE);
      // COB(587): WRITE REPREC FROM MSG-REC1
      // COB(587):    AFTER ADVANCING 2 LINES.
      repout.writeAfter(Advancing.Lines(2), ws.msgRec1);
      // COB(588): AFTER ADVANCING 2 LINES.
    }
    // COB(594): EXEC SQL WHENEVER SQLERROR   CONTINUE     END-EXEC.
    // **********************************************************
    //  SQL RETURN CODE HANDLING WHEN PROCESSING CANNOT PROCEED *
    // **********************************************************
    //
    sqlca.whenever(SqlCondition.Error);
    // COB(595): EXEC SQL WHENEVER SQLWARNING CONTINUE     END-EXEC.
    sqlca.whenever(SqlCondition.Warning);
    // COB(596): EXEC SQL WHENEVER NOT FOUND  CONTINUE     END-EXEC.
    sqlca.whenever(SqlCondition.NotFound);
    // COB(599): EXEC SQL ROLLBACK END-EXEC.
    //                                             **PERFORM ROLLBACK
    sqlca.rollback();
    // COB(601): IF SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(606): MOVE '053I' TO MSGCODE
      //                                            **ROLLBACK SUCCESSFUL
      //                                            **PRINT CONFIRMATION
      //                                            **MESSAGE
      ws.msgcode.setString("053I");
      // COB(607): ELSE
    } else {
      // COB(611): MOVE '061E' TO MSGCODE.
      //                                             **ROLLBACK FAILED
      //                                            **PRINT ERROR MESSAGE
      ws.msgcode.setString("061E");
      // COB(611): MOVE '061E' TO MSGCODE.
    }
    // COB(612): CALL 'DSN8MCG' USING MAJOR MSGCODE OUTMSG.
    context.call("DSN8MCG", ws.major, ws.msgcode, ws.outmsg);
    // COB(613): MOVE OUTMSG TO OUTMSG1 OF MSG-REC1.
    ws.msgRec1.outmsg1.setValue(ws.outmsg);
    // COB(614): MOVE SQLCODE TO RETCODE OF MSG-REC1.
    ws.msgRec1.retcode.setValue(sqlca.sqlcode);
    // COB(615): WRITE REPREC FROM MSG-REC1
    // COB(615):       AFTER ADVANCING 2 LINES.
    repout.writeAfter(Advancing.Lines(2), ws.msgRec1);
    // COB(617): GO TO PROG-END.
    return Flow.progEnd;
  }

  /*****************************************************
   *  PRINT MESSAGE TEXT                               *
   *****************************************************/
  protected void errorPrint() {
    // COB(623): WRITE REPREC FROM ERROR-TEXT (ERROR-INDEX)
    // COB(623):    AFTER ADVANCING 1 LINE.
    repout.writeAfter(Advancing.Line, ws.errorMessage.errorTextAtIndex(ws.errorIndex.getInt() - 1));
  }

  protected void cardinReadAtEnd() {
    // COB(350): AT END MOVE 'N' TO INPUT-SWITCH.
    ws.inputSwitch.setString("N");
  }

  protected void cardinReadAtEnd1() {
    // COB(389): AT END MOVE 'N' TO INPUT-SWITCH.
    ws.inputSwitch.setString("N");
  }

  protected void handleSqlDberror() {
    rcNext = Flow.dberror;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case dberror:
          rcNext = dberror();
          break;
        case progEnd:
          progEnd();
          rcNext = Flow.Exit;
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
  }
}
