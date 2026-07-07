package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.csutldtc.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;

public class CSUTLDTC extends AbstractProgram {

  @ProgramStorage final CsutldtcWorking ws = new CsutldtcWorking();

  // Linkage
  public static class CsutldtcLinkage extends NGroup {
    // COB:           01 LS-DATE         PIC X(10).
    public NChar lsDate = new NChar(10);
    // COB:           01 LS-DATE-FORMAT  PIC X(10).
    public NChar lsDateFormat = new NChar(10);
    // COB:           01 LS-RESULT       PIC X(80).
    public NChar lsResult = new NChar(80);
  }

  final CsutldtcLinkage params = new CsutldtcLinkage();

  public CSUTLDTC(Context context) {
    super(context);
  }

  @Override
  protected int procedure(AbstractNField... args) throws ContextException {
    if (args != null) {
      params.allocate();
      if (args.length > 0) params.lsDate.setAddress(args[0].getAddress());
      if (args.length > 1) params.lsDateFormat.setAddress(args[1].getAddress());
      if (args.length > 2) params.lsResult.setAddress(args[2].getAddress());
    }
    procedure();
    return RETURN_CODE;
  }

  protected void procedure() {
    // COB(90): INITIALIZE WS-MESSAGE
    ws.wsMessage.initialize();
    // COB(91): MOVE SPACES TO WS-DATE
    ws.wsMessage.wsDate.spaces();
    // COB(93): PERFORM A000-MAIN
    // COB(93):    THRU A000-MAIN-EXIT
    a000Main();
    // COB(97): MOVE WS-MESSAGE                 TO LS-RESULT
    //
    //     DISPLAY WS-MESSAGE
    params.lsResult.setValue(ws.wsMessage);
    // COB(98): MOVE WS-SEVERITY-N              TO RETURN-CODE
    RETURN_CODE = ws.wsMessage.wsSeverityN.getInt();
    // COB(100): EXIT PROGRAM
    throw new ExitProgramException();
  }

  protected void a000Main() {
    // COB(105): MOVE LENGTH OF LS-DATE
    // COB(105):              TO VSTRING-LENGTH  OF WS-DATE-TO-TEST
    ws.wsDateToTest.vstringLength.setValue(params.lsDate.length());
    // COB(107): MOVE LS-DATE TO VSTRING-TEXT    OF WS-DATE-TO-TEST
    // COB(107):                 WS-DATE
    ws.wsDateToTest.vstringText.setValue(params.lsDate);
    ws.wsMessage.wsDate.setValue(params.lsDate);
    // COB(109): MOVE LENGTH OF LS-DATE-FORMAT
    // COB(109):               TO VSTRING-LENGTH OF WS-DATE-FORMAT
    ws.wsDateFormat.vstringLength.setValue(params.lsDateFormat.length());
    // COB(111): MOVE LS-DATE-FORMAT
    // COB(111):               TO VSTRING-TEXT   OF WS-DATE-FORMAT
    // COB(111):                  WS-DATE-FMT
    ws.wsDateFormat.vstringText.setValue(params.lsDateFormat);
    ws.wsMessage.wsDateFmt.setValue(params.lsDateFormat);
    // COB(114): MOVE 0        TO OUTPUT-LILLIAN
    ws.outputLillian.setValue(0);
    // COB(116): CALL "CEEDAYS" USING
    // COB(116):        WS-DATE-TO-TEST,
    // COB(116):        WS-DATE-FORMAT,
    // COB(116):        OUTPUT-LILLIAN,
    // COB(116):        FEEDBACK-CODE
    context.call("CEEDAYS", ws.wsDateToTest, ws.wsDateFormat, ws.outputLillian, ws.feedbackCode);
    // COB(122): MOVE WS-DATE-TO-TEST            TO WS-DATE
    ws.wsMessage.wsDate.setValue(ws.wsDateToTest);
    // COB(123): MOVE SEVERITY OF FEEDBACK-CODE  TO WS-SEVERITY-N
    ws.wsMessage.wsSeverityN.setValue(ws.feedbackCode.feedbackTokenValue.case1ConditionId.severity);
    // COB(124): MOVE MSG-NO OF FEEDBACK-CODE    TO WS-MSG-NO-N
    ws.wsMessage.wsMsgNoN.setValue(ws.feedbackCode.feedbackTokenValue.case1ConditionId.msgNo);
    // COB(128): EVALUATE TRUE
    //     WS-RESULT IS 15 CHARACTERS
    //                 123456789012345'
    // COB(129): WHEN FC-INVALID-DATE
    if (ws.feedbackCode.feedbackTokenValue.fcInvalidDate()) {
      // COB(130): MOVE 'Date is valid'      TO WS-RESULT
      ws.wsMessage.wsResult.setString("Date is valid");
      // COB(131): WHEN FC-INSUFFICIENT-DATA
    } else if (ws.feedbackCode.feedbackTokenValue.fcInsufficientData()) {
      // COB(132): MOVE 'Insufficient'       TO WS-RESULT
      ws.wsMessage.wsResult.setString("Insufficient");
      // COB(133): WHEN FC-BAD-DATE-VALUE
    } else if (ws.feedbackCode.feedbackTokenValue.fcBadDateValue()) {
      // COB(134): MOVE 'Datevalue error'    TO WS-RESULT
      ws.wsMessage.wsResult.setString("Datevalue error");
      // COB(135): WHEN FC-INVALID-ERA
    } else if (ws.feedbackCode.feedbackTokenValue.fcInvalidEra()) {
      // COB(136): MOVE 'Invalid Era    '    TO WS-RESULT
      ws.wsMessage.wsResult.setString("Invalid Era    ");
      // COB(137): WHEN FC-UNSUPP-RANGE
    } else if (ws.feedbackCode.feedbackTokenValue.fcUnsuppRange()) {
      // COB(138): MOVE 'Unsupp. Range  '    TO WS-RESULT
      ws.wsMessage.wsResult.setString("Unsupp. Range  ");
      // COB(139): WHEN FC-INVALID-MONTH
    } else if (ws.feedbackCode.feedbackTokenValue.fcInvalidMonth()) {
      // COB(140): MOVE 'Invalid month  '    TO WS-RESULT
      ws.wsMessage.wsResult.setString("Invalid month  ");
      // COB(141): WHEN FC-BAD-PIC-STRING
    } else if (ws.feedbackCode.feedbackTokenValue.fcBadPicString()) {
      // COB(142): MOVE 'Bad Pic String '    TO WS-RESULT
      ws.wsMessage.wsResult.setString("Bad Pic String ");
      // COB(143): WHEN FC-NON-NUMERIC-DATA
    } else if (ws.feedbackCode.feedbackTokenValue.fcNonNumericData()) {
      // COB(144): MOVE 'Nonnumeric data'    TO WS-RESULT
      ws.wsMessage.wsResult.setString("Nonnumeric data");
      // COB(145): WHEN FC-YEAR-IN-ERA-ZERO
    } else if (ws.feedbackCode.feedbackTokenValue.fcYearInEraZero()) {
      // COB(146): MOVE 'YearInEra is 0 '    TO WS-RESULT
      ws.wsMessage.wsResult.setString("YearInEra is 0 ");
      // COB(147): WHEN OTHER
    } else {
      // COB(148): MOVE 'Date is invalid'    TO WS-RESULT
      ws.wsMessage.wsResult.setString("Date is invalid");
      // COB(149): END-EVALUATE
    }
  }
}
