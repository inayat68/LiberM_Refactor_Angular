package com.aws.carddemo.online.program.storage.csutldtc;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class CsutldtcWorking extends NGroup {
  // COB:          01 WS-DATE-TO-TEST.
  public WsDateToTest wsDateToTest = new WsDateToTest();

  public static class WsDateToTest extends NGroup {

    // COB:               02  Vstring-length      PIC S9(4) BINARY.
    public NInt16 vstringLength = new NInt16();

    // COB:               02  Vstring-text.
    public static class VstringText extends NGroup {
      // COB:                   03  Vstring-char    PIC X
      // COB:                               OCCURS 0 TO 256 TIMES
      // COB:                               DEPENDING ON Vstring-length
      public NChar[] vstringChar = AbstractNField.occurs(256, new NChar(1));

      public NChar vstringCharAtIndex(int index) {
        return getOccursInstance(vstringChar, index);
      }
    }

    public VstringText vstringText = new VstringText();
  }

  // COB:          01 WS-DATE-FORMAT.
  public WsDateFormat wsDateFormat = new WsDateFormat();

  public static class WsDateFormat extends NGroup {

    // COB:               02  Vstring-length      PIC S9(4) BINARY.
    public NInt16 vstringLength = new NInt16();

    // COB:               02  Vstring-text.
    public static class VstringText extends NGroup {
      // COB:                   03  Vstring-char    PIC X
      // COB:                               OCCURS 0 TO 256 TIMES
      // COB:                               DEPENDING ON Vstring-length
      public NChar[] vstringChar = AbstractNField.occurs(256, new NChar(1));

      public NChar vstringCharAtIndex(int index) {
        return getOccursInstance(vstringChar, index);
      }
    }

    public VstringText vstringText = new VstringText();
  }

  // COB:          01 OUTPUT-LILLIAN    PIC S9(9) USAGE IS BINARY.
  public NInt32 outputLillian = new NInt32();
  // COB:          01 WS-MESSAGE.
  public WsMessage wsMessage = new WsMessage();

  public static class WsMessage extends NGroup {

    // COB:               02 WS-SEVERITY  PIC X(04).
    public NChar wsSeverity = new NChar(4);
    // COB:               02 WS-SEVERITY-N REDEFINES WS-SEVERITY PIC 9(4).
    public NZoned wsSeverityN = new NZoned(4, false).redefines(wsSeverity);
    // COB:               02 FILLER       PIC X(11) VALUE 'Mesg Code:'.
    public NChar filler45 = new NChar(11).initial("Mesg Code:");
    // COB:               02 WS-MSG-NO    PIC X(04).
    public NChar wsMsgNo = new NChar(4);
    // COB:               02 WS-MSG-NO-N  REDEFINES WS-MSG-NO PIC 9(4).
    public NZoned wsMsgNoN = new NZoned(4, false).redefines(wsMsgNo);
    // COB:               02 FILLER       PIC X(01) VALUE SPACE.
    public NChar filler48 = new NChar(1).initial(NChar.Space);
    // COB:               02 WS-RESULT    PIC X(15).
    public NChar wsResult = new NChar(15);
    // COB:               02 FILLER       PIC X(01) VALUE SPACE.
    public NChar filler50 = new NChar(1).initial(NChar.Space);
    // COB:               02 FILLER       PIC X(09) VALUE 'TstDate:'.
    public NChar filler51 = new NChar(9).initial("TstDate:");
    // COB:               02 WS-DATE      PIC X(10) VALUE SPACES.
    public NChar wsDate = new NChar(10).initial(NChar.Space);
    // COB:               02 FILLER       PIC X(01) VALUE SPACE.
    public NChar filler53 = new NChar(1).initial(NChar.Space);
    // COB:               02 FILLER       PIC X(10) VALUE 'Mask used:'.
    public NChar filler54 = new NChar(10).initial("Mask used:");
    // COB:               02 WS-DATE-FMT  PIC X(10).
    public NChar wsDateFmt = new NChar(10);
    // COB:               02 FILLER       PIC X(01) VALUE SPACE.
    public NChar filler56 = new NChar(1).initial(NChar.Space);
    // COB:               02 FILLER       PIC X(03) VALUE SPACES.
    public NChar filler57 = new NChar(3).initial(NChar.Space);
  }

  // COB:           01 FEEDBACK-CODE.
  public FeedbackCode feedbackCode = new FeedbackCode();

  public static class FeedbackCode extends NGroup {

    // COB:            02  FEEDBACK-TOKEN-VALUE.
    public static class FeedbackTokenValue extends NGroup {
      // COB:              88  FC-INVALID-DATE       VALUE X'0000000000000000'.
      public boolean fcInvalidDate() {
        return this.equals(hexToString("0000000000000000"));
      }

      public void setFcInvalidDate(boolean value) {
        if (value) this.setValue(hexToString("0000000000000000"));
      }

      // COB:              88  FC-INSUFFICIENT-DATA  VALUE X'000309CB59C3C5C5'.
      public boolean fcInsufficientData() {
        return this.equals(hexToString("000309CB59C3C5C5"));
      }

      public void setFcInsufficientData(boolean value) {
        if (value) this.setValue(hexToString("000309CB59C3C5C5"));
      }

      // COB:              88  FC-BAD-DATE-VALUE     VALUE X'000309CC59C3C5C5'.
      public boolean fcBadDateValue() {
        return this.equals(hexToString("000309CC59C3C5C5"));
      }

      public void setFcBadDateValue(boolean value) {
        if (value) this.setValue(hexToString("000309CC59C3C5C5"));
      }

      // COB:              88  FC-INVALID-ERA        VALUE X'000309CD59C3C5C5'.
      public boolean fcInvalidEra() {
        return this.equals(hexToString("000309CD59C3C5C5"));
      }

      public void setFcInvalidEra(boolean value) {
        if (value) this.setValue(hexToString("000309CD59C3C5C5"));
      }

      // COB:              88  FC-UNSUPP-RANGE       VALUE X'000309D159C3C5C5'.
      public boolean fcUnsuppRange() {
        return this.equals(hexToString("000309D159C3C5C5"));
      }

      public void setFcUnsuppRange(boolean value) {
        if (value) this.setValue(hexToString("000309D159C3C5C5"));
      }

      // COB:              88  FC-INVALID-MONTH      VALUE X'000309D559C3C5C5'.
      public boolean fcInvalidMonth() {
        return this.equals(hexToString("000309D559C3C5C5"));
      }

      public void setFcInvalidMonth(boolean value) {
        if (value) this.setValue(hexToString("000309D559C3C5C5"));
      }

      // COB:              88  FC-BAD-PIC-STRING     VALUE X'000309D659C3C5C5'.
      public boolean fcBadPicString() {
        return this.equals(hexToString("000309D659C3C5C5"));
      }

      public void setFcBadPicString(boolean value) {
        if (value) this.setValue(hexToString("000309D659C3C5C5"));
      }

      // COB:              88  FC-NON-NUMERIC-DATA   VALUE X'000309D859C3C5C5'.
      public boolean fcNonNumericData() {
        return this.equals(hexToString("000309D859C3C5C5"));
      }

      public void setFcNonNumericData(boolean value) {
        if (value) this.setValue(hexToString("000309D859C3C5C5"));
      }

      // COB:              88  FC-YEAR-IN-ERA-ZERO   VALUE X'000309D959C3C5C5'.
      public boolean fcYearInEraZero() {
        return this.equals(hexToString("000309D959C3C5C5"));
      }

      public void setFcYearInEraZero(boolean value) {
        if (value) this.setValue(hexToString("000309D959C3C5C5"));
      }

      // COB:                03  CASE-1-CONDITION-ID.
      public static class Case1ConditionId extends NGroup {
        // COB:                    04  SEVERITY        PIC S9(4) BINARY.
        public NInt16 severity = new NInt16();
        // COB:                    04  MSG-NO          PIC S9(4) BINARY.
        public NInt16 msgNo = new NInt16();
      }

      public Case1ConditionId case1ConditionId = new Case1ConditionId();

      // COB:                03  CASE-2-CONDITION-ID
      // COB:                          REDEFINES CASE-1-CONDITION-ID.
      public static class Case2ConditionId extends NGroup {
        // COB:                    04  CLASS-CODE      PIC S9(4) BINARY.
        public NInt16 classCode = new NInt16();
        // COB:                    04  CAUSE-CODE      PIC S9(4) BINARY.
        public NInt16 causeCode = new NInt16();
      }

      public Case2ConditionId case2ConditionId =
          (Case2ConditionId) new Case2ConditionId().redefines(case1ConditionId);
      // COB:                03  CASE-SEV-CTL    PIC X.
      public NChar caseSevCtl = new NChar(1);
      // COB:                03  FACILITY-ID     PIC XXX.
      public NChar facilityId = new NChar(3);
    }

    public FeedbackTokenValue feedbackTokenValue = new FeedbackTokenValue();
    // COB:            02  I-S-INFO        PIC S9(9) BINARY.
    public NInt32 iSInfo = new NInt32();
  }
}
