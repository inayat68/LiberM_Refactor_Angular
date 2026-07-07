package com.aws.carddemo.online.program.storage.coactupc;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Coactup;
import com.aws.carddemo.common.copybook.Cocom01y;
import com.aws.carddemo.common.copybook.Cottl01y;
import com.aws.carddemo.common.copybook.Csdat01y;
import com.aws.carddemo.common.copybook.Cslkpcdy;
import com.aws.carddemo.common.copybook.Csmsg01y;
import com.aws.carddemo.common.copybook.Csmsg02y;
import com.aws.carddemo.common.copybook.Csusr01y;
import com.aws.carddemo.common.copybook.Cvact01y;
import com.aws.carddemo.common.copybook.Cvact03y;
import com.aws.carddemo.common.copybook.Cvcrd01y;
import com.aws.carddemo.common.copybook.Cvcus01y;
import com.nib.commons.storage.*;

public class CoactupcWorking extends NGroup {
  // COB:        01  WS-MISC-STORAGE.
  public WsMiscStorage wsMiscStorage = new WsMiscStorage();

  public static class WsMiscStorage extends NGroup {

    // COB:          05 WS-CICS-PROCESSNG-VARS.
    public static class WsCicsProcessngVars extends NGroup {
      // COB:             07 WS-RESP-CD                          PIC S9(09) COMP
      // COB:                                                    VALUE ZEROS.
      public NInt32 wsRespCd = new NInt32().initial(0);
      // COB:             07 WS-REAS-CD                          PIC S9(09) COMP
      // COB:                                                    VALUE ZEROS.
      public NInt32 wsReasCd = new NInt32().initial(0);
      // COB:             07 WS-TRANID                           PIC X(4)
      // COB:                                                    VALUE SPACES.
      public NChar wsTranid = new NChar(4).initial(NChar.Space);
      // COB:             07 WS-UCTRANS                          PIC X(4)
      // COB:                                                    VALUE SPACES.
      public NChar wsUctrans = new NChar(4).initial(NChar.Space);
    }

    public WsCicsProcessngVars wsCicsProcessngVars = new WsCicsProcessngVars();

    // COB:          05  WS-GENERIC-EDITS.
    public static class WsGenericEdits extends NGroup {
      // COB:            10 WS-EDIT-VARIABLE-NAME                PIC X(25).
      public NChar wsEditVariableName = new NChar(25);
      // COB:            10 WS-EDIT-SIGNED-NUMBER-9V2-X          PIC X(15).
      public NChar wsEditSignedNumber9v2X = new NChar(15);
      // COB:            10 WS-FLG-SIGNED-NUMBER-EDIT            PIC X(1).
      public NChar wsFlgSignedNumberEdit = new NChar(1);

      // COB:               88  FLG-SIGNED-NUMBER-ISVALID        VALUE LOW-VALUES.
      public boolean flgSignedNumberIsvalid() {
        return wsFlgSignedNumberEdit.isLowValues();
      }

      public void setFlgSignedNumberIsvalid(boolean value) {
        if (value) wsFlgSignedNumberEdit.lowValues();
      }

      // COB:               88  FLG-SIGNED-NUMBER-NOT-OK         VALUE '0'.
      public boolean flgSignedNumberNotOk() {
        return wsFlgSignedNumberEdit.equals("0");
      }

      public void setFlgSignedNumberNotOk(boolean value) {
        if (value) wsFlgSignedNumberEdit.setValue("0");
      }

      // COB:               88  FLG-SIGNED-NUMBER-BLANK          VALUE 'B'.
      public boolean flgSignedNumberBlank() {
        return wsFlgSignedNumberEdit.equals("B");
      }

      public void setFlgSignedNumberBlank(boolean value) {
        if (value) wsFlgSignedNumberEdit.setValue("B");
      }

      // COB:            10 WS-EDIT-ALPHANUM-ONLY                PIC X(256).
      public NChar wsEditAlphanumOnly = new NChar(256);
      // COB:            10 WS-EDIT-ALPHANUM-LENGTH              PIC S9(4) COMP-3.
      public NPacked wsEditAlphanumLength = new NPacked(3);
      // COB:            10 WS-EDIT-ALPHA-ONLY-FLAGS             PIC X(1).
      public NChar wsEditAlphaOnlyFlags = new NChar(1);

      // COB:               88  FLG-ALPHA-ISVALID                VALUE LOW-VALUES.
      public boolean flgAlphaIsvalid() {
        return wsEditAlphaOnlyFlags.isLowValues();
      }

      public void setFlgAlphaIsvalid(boolean value) {
        if (value) wsEditAlphaOnlyFlags.lowValues();
      }

      // COB:               88  FLG-ALPHA-NOT-OK                 VALUE '0'.
      public boolean flgAlphaNotOk() {
        return wsEditAlphaOnlyFlags.equals("0");
      }

      public void setFlgAlphaNotOk(boolean value) {
        if (value) wsEditAlphaOnlyFlags.setValue("0");
      }

      // COB:               88  FLG-ALPHA-BLANK                  VALUE 'B'.
      public boolean flgAlphaBlank() {
        return wsEditAlphaOnlyFlags.equals("B");
      }

      public void setFlgAlphaBlank(boolean value) {
        if (value) wsEditAlphaOnlyFlags.setValue("B");
      }

      // COB:            10 WS-EDIT-ALPHANUM-ONLY-FLAGS          PIC X(1).
      public NChar wsEditAlphanumOnlyFlags = new NChar(1);

      // COB:               88  FLG-ALPHNANUM-ISVALID            VALUE LOW-VALUES.
      public boolean flgAlphnanumIsvalid() {
        return wsEditAlphanumOnlyFlags.isLowValues();
      }

      public void setFlgAlphnanumIsvalid(boolean value) {
        if (value) wsEditAlphanumOnlyFlags.lowValues();
      }

      // COB:               88  FLG-ALPHNANUM-NOT-OK             VALUE '0'.
      public boolean flgAlphnanumNotOk() {
        return wsEditAlphanumOnlyFlags.equals("0");
      }

      public void setFlgAlphnanumNotOk(boolean value) {
        if (value) wsEditAlphanumOnlyFlags.setValue("0");
      }

      // COB:               88  FLG-ALPHNANUM-BLANK              VALUE 'B'.
      public boolean flgAlphnanumBlank() {
        return wsEditAlphanumOnlyFlags.equals("B");
      }

      public void setFlgAlphnanumBlank(boolean value) {
        if (value) wsEditAlphanumOnlyFlags.setValue("B");
      }

      // COB:            10 WS-EDIT-MANDATORY-FLAGS              PIC X(1).
      public NChar wsEditMandatoryFlags = new NChar(1);

      // COB:               88  FLG-MANDATORY-ISVALID            VALUE LOW-VALUES.
      public boolean flgMandatoryIsvalid() {
        return wsEditMandatoryFlags.isLowValues();
      }

      public void setFlgMandatoryIsvalid(boolean value) {
        if (value) wsEditMandatoryFlags.lowValues();
      }

      // COB:               88  FLG-MANDATORY-NOT-OK             VALUE '0'.
      public boolean flgMandatoryNotOk() {
        return wsEditMandatoryFlags.equals("0");
      }

      public void setFlgMandatoryNotOk(boolean value) {
        if (value) wsEditMandatoryFlags.setValue("0");
      }

      // COB:               88  FLG-MANDATORY-BLANK              VALUE 'B'.
      public boolean flgMandatoryBlank() {
        return wsEditMandatoryFlags.equals("B");
      }

      public void setFlgMandatoryBlank(boolean value) {
        if (value) wsEditMandatoryFlags.setValue("B");
      }

      // COB:            10 WS-EDIT-YES-NO                       PIC X(1)
      // COB:                                                    VALUE 'N'.
      public NChar wsEditYesNo = new NChar(1).initial("N");

      // COB:               88 FLG-YES-NO-ISVALID                VALUES 'Y', 'N'.
      public boolean flgYesNoIsvalid() {
        return wsEditYesNo.equals("Y") || wsEditYesNo.equals("N");
      }

      public void setFlgYesNoIsvalid(boolean value) {
        if (value) wsEditYesNo.setValue("Y");
      }

      // COB:               88 FLG-YES-NO-NOT-OK                 VALUE '0'.
      public boolean flgYesNoNotOk() {
        return wsEditYesNo.equals("0");
      }

      public void setFlgYesNoNotOk(boolean value) {
        if (value) wsEditYesNo.setValue("0");
      }

      // COB:               88 FLG-YES-NO-BLANK                  VALUE 'B'.
      public boolean flgYesNoBlank() {
        return wsEditYesNo.equals("B");
      }

      public void setFlgYesNoBlank(boolean value) {
        if (value) wsEditYesNo.setValue("B");
      }

      // COB:            10 WS-EDIT-US-PHONE-NUM                 PIC X(15).
      public NChar wsEditUsPhoneNum = new NChar(15);

      // COB:            10 WS-EDIT-US-PHONE-NUM-X REDEFINES
      // COB:               WS-EDIT-US-PHONE-NUM.
      public static class WsEditUsPhoneNumX extends NGroup {
        // COB:               20 FILLER                            PIC X(1).
        public NChar filler85 = new NChar(1);
        // COB:               20 WS-EDIT-US-PHONE-NUMA             PIC X(3).
        public NChar wsEditUsPhoneNuma = new NChar(3);
        // COB:               20 WS-EDIT-US-PHONE-NUMA-N REDEFINES
        // COB:                  WS-EDIT-US-PHONE-NUMA             PIC 9(3).
        public NZoned wsEditUsPhoneNumaN = new NZoned(3, false).redefines(wsEditUsPhoneNuma);
        // COB:               20 FILLER                            PIC X(1).
        public NChar filler90 = new NChar(1);
        // COB:               20 WS-EDIT-US-PHONE-NUMB             PIC X(3).
        public NChar wsEditUsPhoneNumb = new NChar(3);
        // COB:               20 WS-EDIT-US-PHONE-NUMB-N REDEFINES
        // COB:                  WS-EDIT-US-PHONE-NUMB             PIC 9(3).
        public NZoned wsEditUsPhoneNumbN = new NZoned(3, false).redefines(wsEditUsPhoneNumb);
        // COB:               20 FILLER                            PIC X(1).
        public NChar filler95 = new NChar(1);
        // COB:               20 WS-EDIT-US-PHONE-NUMC             PIC X(4).
        public NChar wsEditUsPhoneNumc = new NChar(4);
        // COB:               20 WS-EDIT-US-PHONE-NUMC-N REDEFINES
        // COB:                  WS-EDIT-US-PHONE-NUMC             PIC 9(4).
        public NZoned wsEditUsPhoneNumcN = new NZoned(4, false).redefines(wsEditUsPhoneNumc);
        // COB:               20 FILLER                            PIC X(2).
        public NChar filler100 = new NChar(2);
      }

      public WsEditUsPhoneNumX wsEditUsPhoneNumX =
          (WsEditUsPhoneNumX) new WsEditUsPhoneNumX().redefines(wsEditUsPhoneNum);

      // COB:            10 WS-EDIT-US-PHONE-NUM-FLGS.
      public static class WsEditUsPhoneNumFlgs extends NGroup {
        // COB:                88 WS-EDIT-US-PHONE-IS-INVALID      VALUE '000'.
        public boolean wsEditUsPhoneIsInvalid() {
          return this.equals("000");
        }

        public void setWsEditUsPhoneIsInvalid(boolean value) {
          if (value) this.setValue("000");
        }

        // COB:                88 WS-EDIT-US-PHONE-IS-VALID        VALUE LOW-VALUES.
        public boolean wsEditUsPhoneIsValid() {
          return this.isLowValues();
        }

        public void setWsEditUsPhoneIsValid(boolean value) {
          if (value) this.lowValues();
        }

        // COB:                20 WS-EDIT-US-PHONEA-FLG            PIC X(01).
        public NChar wsEditUsPhoneaFlg = new NChar(1);

        // COB:                   88 FLG-EDIT-US-PHONEA-ISVALID    VALUE LOW-VALUES.
        public boolean flgEditUsPhoneaIsvalid() {
          return wsEditUsPhoneaFlg.isLowValues();
        }

        public void setFlgEditUsPhoneaIsvalid(boolean value) {
          if (value) wsEditUsPhoneaFlg.lowValues();
        }

        // COB:                   88 FLG-EDIT-US-PHONEA-NOT-OK     VALUE '0'.
        public boolean flgEditUsPhoneaNotOk() {
          return wsEditUsPhoneaFlg.equals("0");
        }

        public void setFlgEditUsPhoneaNotOk(boolean value) {
          if (value) wsEditUsPhoneaFlg.setValue("0");
        }

        // COB:                   88 FLG-EDIT-US-PHONEA-BLANK      VALUE 'B'.
        public boolean flgEditUsPhoneaBlank() {
          return wsEditUsPhoneaFlg.equals("B");
        }

        public void setFlgEditUsPhoneaBlank(boolean value) {
          if (value) wsEditUsPhoneaFlg.setValue("B");
        }

        // COB:                20 WS-EDIT-EDIT-US-PHONEB           PIC X(01).
        public NChar wsEditEditUsPhoneb = new NChar(1);

        // COB:                   88 FLG-EDIT-US-PHONEB-ISVALID    VALUE LOW-VALUES.
        public boolean flgEditUsPhonebIsvalid() {
          return wsEditEditUsPhoneb.isLowValues();
        }

        public void setFlgEditUsPhonebIsvalid(boolean value) {
          if (value) wsEditEditUsPhoneb.lowValues();
        }

        // COB:                   88 FLG-EDIT-US-PHONEB-NOT-OK     VALUE '0'.
        public boolean flgEditUsPhonebNotOk() {
          return wsEditEditUsPhoneb.equals("0");
        }

        public void setFlgEditUsPhonebNotOk(boolean value) {
          if (value) wsEditEditUsPhoneb.setValue("0");
        }

        // COB:                   88 FLG-EDIT-US-PHONEB-BLANK      VALUE 'B'.
        public boolean flgEditUsPhonebBlank() {
          return wsEditEditUsPhoneb.equals("B");
        }

        public void setFlgEditUsPhonebBlank(boolean value) {
          if (value) wsEditEditUsPhoneb.setValue("B");
        }

        // COB:                20 WS-EDIT-EDIT-PHONEC              PIC X(01).
        public NChar wsEditEditPhonec = new NChar(1);

        // COB:                   88 FLG-EDIT-US-PHONEC-ISVALID    VALUE LOW-VALUES.
        public boolean flgEditUsPhonecIsvalid() {
          return wsEditEditPhonec.isLowValues();
        }

        public void setFlgEditUsPhonecIsvalid(boolean value) {
          if (value) wsEditEditPhonec.lowValues();
        }

        // COB:                   88 FLG-EDIT-US-PHONEC-NOT-OK     VALUE '0'.
        public boolean flgEditUsPhonecNotOk() {
          return wsEditEditPhonec.equals("0");
        }

        public void setFlgEditUsPhonecNotOk(boolean value) {
          if (value) wsEditEditPhonec.setValue("0");
        }

        // COB:                   88 FLG-EDIT-US-PHONEC-BLANK      VALUE 'B'.
        public boolean flgEditUsPhonecBlank() {
          return wsEditEditPhonec.equals("B");
        }

        public void setFlgEditUsPhonecBlank(boolean value) {
          if (value) wsEditEditPhonec.setValue("B");
        }
      }

      public WsEditUsPhoneNumFlgs wsEditUsPhoneNumFlgs = new WsEditUsPhoneNumFlgs();

      // COB:            10 WS-EDIT-US-SSN.
      public static class WsEditUsSsn extends NGroup {
        // COB:                20 WS-EDIT-US-SSN-PART1              PIC X(3).
        public NChar wsEditUsSsnPart1 = new NChar(3);
        // COB:                20 WS-EDIT-US-SSN-PART1-N REDEFINES
        // COB:                   WS-EDIT-US-SSN-PART1              PIC 9(3).
        public NZoned wsEditUsSsnPart1N = new NZoned(3, false).redefines(wsEditUsSsnPart1);

        // COB:                   88 INVALID-SSN-PART1  VALUES      0,
        public boolean invalidSsnPart1() {
          return wsEditUsSsnPart1N.equals(0)
              || wsEditUsSsnPart1N.equals(666)
              || wsEditUsSsnPart1N.inRange(900, 999);
        }

        public void setInvalidSsnPart1(boolean value) {
          if (value) wsEditUsSsnPart1N.setValue(0);
        }

        // COB:                20 WS-EDIT-US-SSN-PART2              PIC X(2).
        public NChar wsEditUsSsnPart2 = new NChar(2);
        // COB:                20 WS-EDIT-US-SSN-PART2-N REDEFINES
        // COB:                   WS-EDIT-US-SSN-PART2              PIC 9(2).
        public NZoned wsEditUsSsnPart2N = new NZoned(2, false).redefines(wsEditUsSsnPart2);
        // COB:                20 WS-EDIT-US-SSN-PART3              PIC X(4).
        public NChar wsEditUsSsnPart3 = new NChar(4);
        // COB:                20 WS-EDIT-US-SSN-PART3-N REDEFINES
        // COB:                   WS-EDIT-US-SSN-PART3              PIC 9(4).
        public NZoned wsEditUsSsnPart3N = new NZoned(4, false).redefines(wsEditUsSsnPart3);
      }

      public WsEditUsSsn wsEditUsSsn = new WsEditUsSsn();
      // COB:            10 WS-EDIT-US-SSN-N REDEFINES
      // COB:               WS-EDIT-US-SSN                        PIC 9(09).
      public NZoned wsEditUsSsnN = new NZoned(9, false).redefines(wsEditUsSsn);

      // COB:            10 WS-EDIT-US-SSN-FLGS.
      public static class WsEditUsSsnFlgs extends NGroup {
        // COB:                88 WS-EDIT-US-SSN-IS-INVALID         VALUE '000'.
        public boolean wsEditUsSsnIsInvalid() {
          return this.equals("000");
        }

        public void setWsEditUsSsnIsInvalid(boolean value) {
          if (value) this.setValue("000");
        }

        // COB:                88 WS-EDIT-US-SSN-IS-VALID           VALUE LOW-VALUES.
        public boolean wsEditUsSsnIsValid() {
          return this.isLowValues();
        }

        public void setWsEditUsSsnIsValid(boolean value) {
          if (value) this.lowValues();
        }

        // COB:                20 WS-EDIT-US-SSN-PART1-FLGS         PIC X(01).
        public NChar wsEditUsSsnPart1Flgs = new NChar(1);

        // COB:                   88 FLG-EDIT-US-SSN-PART1-ISVALID  VALUE LOW-VALUES.
        public boolean flgEditUsSsnPart1Isvalid() {
          return wsEditUsSsnPart1Flgs.isLowValues();
        }

        public void setFlgEditUsSsnPart1Isvalid(boolean value) {
          if (value) wsEditUsSsnPart1Flgs.lowValues();
        }

        // COB:                   88 FLG-EDIT-US-SSN-PART1-NOT-OK   VALUE '0'.
        public boolean flgEditUsSsnPart1NotOk() {
          return wsEditUsSsnPart1Flgs.equals("0");
        }

        public void setFlgEditUsSsnPart1NotOk(boolean value) {
          if (value) wsEditUsSsnPart1Flgs.setValue("0");
        }

        // COB:                   88 FLG-EDIT-US-SSN-PART1-BLANK    VALUE 'B'.
        public boolean flgEditUsSsnPart1Blank() {
          return wsEditUsSsnPart1Flgs.equals("B");
        }

        public void setFlgEditUsSsnPart1Blank(boolean value) {
          if (value) wsEditUsSsnPart1Flgs.setValue("B");
        }

        // COB:                20 WS-EDIT-US-SSN-PART2-FLGS         PIC X(01).
        public NChar wsEditUsSsnPart2Flgs = new NChar(1);

        // COB:                   88 FLG-EDIT-US-SSN-PART2-ISVALID  VALUE LOW-VALUES.
        public boolean flgEditUsSsnPart2Isvalid() {
          return wsEditUsSsnPart2Flgs.isLowValues();
        }

        public void setFlgEditUsSsnPart2Isvalid(boolean value) {
          if (value) wsEditUsSsnPart2Flgs.lowValues();
        }

        // COB:                   88 FLG-EDIT-US-SSN-PART2-NOT-OK   VALUE '0'.
        public boolean flgEditUsSsnPart2NotOk() {
          return wsEditUsSsnPart2Flgs.equals("0");
        }

        public void setFlgEditUsSsnPart2NotOk(boolean value) {
          if (value) wsEditUsSsnPart2Flgs.setValue("0");
        }

        // COB:                   88 FLG-EDIT-US-SSN-PART2-BLANK    VALUE 'B'.
        public boolean flgEditUsSsnPart2Blank() {
          return wsEditUsSsnPart2Flgs.equals("B");
        }

        public void setFlgEditUsSsnPart2Blank(boolean value) {
          if (value) wsEditUsSsnPart2Flgs.setValue("B");
        }

        // COB:                20 WS-EDIT-US-SSN-PART3-FLGS         PIC X(01).
        public NChar wsEditUsSsnPart3Flgs = new NChar(1);

        // COB:                   88 FLG-EDIT-US-SSN-PART3-ISVALID  VALUE LOW-VALUES.
        public boolean flgEditUsSsnPart3Isvalid() {
          return wsEditUsSsnPart3Flgs.isLowValues();
        }

        public void setFlgEditUsSsnPart3Isvalid(boolean value) {
          if (value) wsEditUsSsnPart3Flgs.lowValues();
        }

        // COB:                   88 FLG-EDIT-US-SSN-PART3-NOT-OK   VALUE '0'.
        public boolean flgEditUsSsnPart3NotOk() {
          return wsEditUsSsnPart3Flgs.equals("0");
        }

        public void setFlgEditUsSsnPart3NotOk(boolean value) {
          if (value) wsEditUsSsnPart3Flgs.setValue("0");
        }

        // COB:                   88 FLG-EDIT-US-SSN-PART3-BLANK    VALUE 'B'.
        public boolean flgEditUsSsnPart3Blank() {
          return wsEditUsSsnPart3Flgs.equals("B");
        }

        public void setFlgEditUsSsnPart3Blank(boolean value) {
          if (value) wsEditUsSsnPart3Flgs.setValue("B");
        }
      }

      public WsEditUsSsnFlgs wsEditUsSsnFlgs = new WsEditUsSsnFlgs();
    }

    public WsGenericEdits wsGenericEdits = new WsGenericEdits();

    // COB:          05 WS-CALCULATION-VARS.
    public static class WsCalculationVars extends NGroup {
      // COB:           10 WS-DIV-BY                             PIC S9(4) COMP-3
      // COB:                                                    VALUE 4.
      public NPacked wsDivBy = new NPacked(3).initial(4);
      // COB:           10 WS-DIVIDEND                           PIC S9(4) COMP-3
      // COB:                                                    VALUE 0.
      public NPacked wsDividend = new NPacked(3).initial(0);
      // COB:           10 WS-REMAINDER                          PIC S9(4) COMP-3
      // COB:                                                    VALUE 0.
      public NPacked wsRemainder = new NPacked(3).initial(0);
      // COB:           10 WS-CURR-DATE                          PIC X(21)
      // COB:                                                    VALUE SPACES.
      public NChar wsCurrDate = new NChar(21).initial(NChar.Space);

      // COB:            10 WS-EDIT-DATE-CCYYMMDD.
      public static class WsEditDateCcyymmdd extends NGroup {
        // COB:               20 WS-EDIT-DATE-CCYY.
        public static class WsEditDateCcyy extends NGroup {
          // COB:                  25 WS-EDIT-DATE-CC                PIC X(2).
          public NChar wsEditDateCc = new NChar(2);
          // COB:                  25 WS-EDIT-DATE-CC-N REDEFINES    WS-EDIT-DATE-CC
          public NZoned wsEditDateCcN = new NZoned(2, false).redefines(wsEditDateCc);

          // COB:                     88 THIS-CENTURY                VALUE 20.
          public boolean thisCentury() {
            return wsEditDateCcN.equals(20);
          }

          public void setThisCentury(boolean value) {
            if (value) wsEditDateCcN.setValue(20);
          }

          // COB:                     88 LAST-CENTURY                VALUE 19.
          public boolean lastCentury() {
            return wsEditDateCcN.equals(19);
          }

          public void setLastCentury(boolean value) {
            if (value) wsEditDateCcN.setValue(19);
          }

          // COB:                  25 WS-EDIT-DATE-YY                PIC X(2).
          public NChar wsEditDateYy = new NChar(2);
          // COB:                  25 WS-EDIT-DATE-YY-N REDEFINES    WS-EDIT-DATE-YY
          public NZoned wsEditDateYyN = new NZoned(2, false).redefines(wsEditDateYy);
        }

        public WsEditDateCcyy wsEditDateCcyy = new WsEditDateCcyy();
        // COB:               20 WS-EDIT-DATE-CCYY-N  REDEFINES
        // COB:                  WS-EDIT-DATE-CCYY                 PIC 9(4).
        public NZoned wsEditDateCcyyN = new NZoned(4, false).redefines(wsEditDateCcyy);
        // COB:               20 WS-EDIT-DATE-MM                   PIC X(2).
        public NChar wsEditDateMm = new NChar(2);
        // COB:               20 WS-EDIT-DATE-MM-N REDEFINES WS-EDIT-DATE-MM
        public NZoned wsEditDateMmN = new NZoned(2, false).redefines(wsEditDateMm);

        // COB:                  88 WS-VALID-MONTH                 VALUES
        public boolean wsValidMonth() {
          return wsEditDateMmN.inRange(1, 12);
        }

        public void setWsValidMonth(boolean value) {
          if (value) wsEditDateMmN.setValue(1);
        }

        // COB:                  88 WS-31-DAY-MONTH                VALUES
        public boolean ws31DayMonth() {
          return wsEditDateMmN.equals(1)
              || wsEditDateMmN.equals(3)
              || wsEditDateMmN.equals(5)
              || wsEditDateMmN.equals(7)
              || wsEditDateMmN.equals(8)
              || wsEditDateMmN.equals(10)
              || wsEditDateMmN.equals(12);
        }

        public void setWs31DayMonth(boolean value) {
          if (value) wsEditDateMmN.setValue(1);
        }

        // COB:                  88 WS-FEBRUARY                    VALUE 2.
        public boolean wsFebruary() {
          return wsEditDateMmN.equals(2);
        }

        public void setWsFebruary(boolean value) {
          if (value) wsEditDateMmN.setValue(2);
        }

        // COB:               20 WS-EDIT-DATE-DD                   PIC X(2).
        public NChar wsEditDateDd = new NChar(2);
        // COB:               20 WS-EDIT-DATE-DD-N REDEFINES WS-EDIT-DATE-DD
        public NZoned wsEditDateDdN = new NZoned(2, false).redefines(wsEditDateDd);

        // COB:                  88 WS-VALID-DAY                   VALUES
        public boolean wsValidDay() {
          return wsEditDateDdN.inRange(1, 31);
        }

        public void setWsValidDay(boolean value) {
          if (value) wsEditDateDdN.setValue(1);
        }

        // COB:                  88 WS-DAY-31                      VALUE 31.
        public boolean wsDay31() {
          return wsEditDateDdN.equals(31);
        }

        public void setWsDay31(boolean value) {
          if (value) wsEditDateDdN.setValue(31);
        }

        // COB:                  88 WS-DAY-30                      VALUE 30.
        public boolean wsDay30() {
          return wsEditDateDdN.equals(30);
        }

        public void setWsDay30(boolean value) {
          if (value) wsEditDateDdN.setValue(30);
        }

        // COB:                  88 WS-DAY-29                      VALUE 29.
        public boolean wsDay29() {
          return wsEditDateDdN.equals(29);
        }

        public void setWsDay29(boolean value) {
          if (value) wsEditDateDdN.setValue(29);
        }

        // COB:                  88 WS-VALID-FEB-DAY               VALUES
        public boolean wsValidFebDay() {
          return wsEditDateDdN.inRange(1, 28);
        }

        public void setWsValidFebDay(boolean value) {
          if (value) wsEditDateDdN.setValue(1);
        }
      }

      public WsEditDateCcyymmdd wsEditDateCcyymmdd = new WsEditDateCcyymmdd();
      // COB:            10 WS-EDIT-DATE-CCYYMMDD-N REDEFINES
      // COB:               WS-EDIT-DATE-CCYYMMDD                PIC 9(8).
      public NZoned wsEditDateCcyymmddN = new NZoned(8, false).redefines(wsEditDateCcyymmdd);
      // COB:            10 WS-EDIT-DATE-BINARY                  PIC S9(9) BINARY.
      public NInt32 wsEditDateBinary = new NInt32();

      // COB:            10 WS-CURRENT-DATE.
      public static class WsCurrentDate extends NGroup {
        // COB:               20 WS-CURRENT-DATE-YYYYMMDD          PIC X(8).
        public NChar wsCurrentDateYyyymmdd = new NChar(8);
        // COB:               20 WS-CURRENT-DATE-YYYYMMDD-N REDEFINES
        // COB:                  WS-CURRENT-DATE-YYYYMMDD          PIC 9(8).
        public NZoned wsCurrentDateYyyymmddN =
            new NZoned(8, false).redefines(wsCurrentDateYyyymmdd);
        // COB:               20 WS-CURRENT-DATE-BINARY            PIC S9(9) BINARY.
        public NInt32 wsCurrentDateBinary = new NInt32();
      }

      public WsCurrentDate wsCurrentDate = new WsCurrentDate();

      // COB:            10 WS-EDIT-DATE-FLGS.
      public static class WsEditDateFlgs extends NGroup {
        // COB:                88 WS-EDIT-DATE-IS-VALID            VALUE LOW-VALUES.
        public boolean wsEditDateIsValid() {
          return this.isLowValues();
        }

        public void setWsEditDateIsValid(boolean value) {
          if (value) this.lowValues();
        }

        // COB:                88 WS-EDIT-DATE-IS-INVALID          VALUE '000'.
        public boolean wsEditDateIsInvalid() {
          return this.equals("000");
        }

        public void setWsEditDateIsInvalid(boolean value) {
          if (value) this.setValue("000");
        }

        // COB:                20 WS-EDIT-YEAR-FLG                 PIC X(01).
        public NChar wsEditYearFlg = new NChar(1);

        // COB:                   88 FLG-YEAR-ISVALID              VALUE LOW-VALUES.
        public boolean flgYearIsvalid() {
          return wsEditYearFlg.isLowValues();
        }

        public void setFlgYearIsvalid(boolean value) {
          if (value) wsEditYearFlg.lowValues();
        }

        // COB:                   88 FLG-YEAR-NOT-OK               VALUE '0'.
        public boolean flgYearNotOk() {
          return wsEditYearFlg.equals("0");
        }

        public void setFlgYearNotOk(boolean value) {
          if (value) wsEditYearFlg.setValue("0");
        }

        // COB:                   88 FLG-YEAR-BLANK                VALUE 'B'.
        public boolean flgYearBlank() {
          return wsEditYearFlg.equals("B");
        }

        public void setFlgYearBlank(boolean value) {
          if (value) wsEditYearFlg.setValue("B");
        }

        // COB:                20 WS-EDIT-MONTH                    PIC X(01).
        public NChar wsEditMonth = new NChar(1);

        // COB:                   88 FLG-MONTH-ISVALID             VALUE LOW-VALUES.
        public boolean flgMonthIsvalid() {
          return wsEditMonth.isLowValues();
        }

        public void setFlgMonthIsvalid(boolean value) {
          if (value) wsEditMonth.lowValues();
        }

        // COB:                   88 FLG-MONTH-NOT-OK              VALUE '0'.
        public boolean flgMonthNotOk() {
          return wsEditMonth.equals("0");
        }

        public void setFlgMonthNotOk(boolean value) {
          if (value) wsEditMonth.setValue("0");
        }

        // COB:                   88 FLG-MONTH-BLANK               VALUE 'B'.
        public boolean flgMonthBlank() {
          return wsEditMonth.equals("B");
        }

        public void setFlgMonthBlank(boolean value) {
          if (value) wsEditMonth.setValue("B");
        }

        // COB:                20 WS-EDIT-DAY                      PIC X(01).
        public NChar wsEditDay = new NChar(1);

        // COB:                   88 FLG-DAY-ISVALID               VALUE LOW-VALUES.
        public boolean flgDayIsvalid() {
          return wsEditDay.isLowValues();
        }

        public void setFlgDayIsvalid(boolean value) {
          if (value) wsEditDay.lowValues();
        }

        // COB:                   88 FLG-DAY-NOT-OK                VALUE '0'.
        public boolean flgDayNotOk() {
          return wsEditDay.equals("0");
        }

        public void setFlgDayNotOk(boolean value) {
          if (value) wsEditDay.setValue("0");
        }

        // COB:                   88 FLG-DAY-BLANK                 VALUE 'B'.
        public boolean flgDayBlank() {
          return wsEditDay.equals("B");
        }

        public void setFlgDayBlank(boolean value) {
          if (value) wsEditDay.setValue("B");
        }
      }

      public WsEditDateFlgs wsEditDateFlgs = new WsEditDateFlgs();
      // COB:           10 WS-DATE-FORMAT                        PIC X(08)
      // COB:                                                    VALUE 'YYYYMMDD'.
      public NChar wsDateFormat = new NChar(8).initial("YYYYMMDD");

      // COB:           10 WS-DATE-VALIDATION-RESULT .
      public static class WsDateValidationResult extends NGroup {
        // COB:                20 WS-SEVERITY                      PIC X(04).
        public NChar wsSeverity = new NChar(4);
        // COB:                20 WS-SEVERITY-N                    REDEFINES
        // COB:                   WS-SEVERITY                      PIC 9(4).
        public NZoned wsSeverityN = new NZoned(4, false).redefines(wsSeverity);
        // COB:                20 FILLER                           PIC X(11)
        // COB:                                                    VALUE 'Mesg Code:'.
        public NChar filler64 = new NChar(11).initial("Mesg Code:");
        // COB:                20 WS-MSG-NO                        PIC X(04).
        public NChar wsMsgNo = new NChar(4);
        // COB:                20 WS-MSG-NO-N                      REDEFINES
        // COB:                   WS-MSG-NO                        Pic 9(4).
        public NZoned wsMsgNoN = new NZoned(4, false).redefines(wsMsgNo);
        // COB:                20 FILLER                           PIC X(01)
        // COB:                                                    VALUE SPACE.
        public NChar filler69 = new NChar(1).initial(NChar.Space);
        // COB:                20 WS-RESULT                        PIC X(15).
        public NChar wsResult = new NChar(15);
        // COB:                20 FILLER                           PIC X(01)
        // COB:                                                    VALUE SPACE.
        public NChar filler72 = new NChar(1).initial(NChar.Space);
        // COB:                20 FILLER                           PIC X(09)
        // COB:                                                    VALUE 'TstDate:'.
        public NChar filler74 = new NChar(9).initial("TstDate:");
        // COB:                20 WS-DATE                          PIC X(10).
        public NChar wsDate = new NChar(10);
        // COB:                20 FILLER                           PIC X(01)
        // COB:                                                    VALUE SPACE.
        public NChar filler77 = new NChar(1).initial(NChar.Space);
        // COB:                20 FILLER                           PIC X(10)
        // COB:                                                    VALUE 'Mask used:' .
        public NChar filler79 = new NChar(10).initial("Mask used:");
        // COB:                20 WS-DATE-FMT                      PIC X(10).
        public NChar wsDateFmt = new NChar(10);
        // COB:                20 FILLER                           PIC X(01)
        // COB:                                                    VALUE SPACE.
        public NChar filler82 = new NChar(1).initial(NChar.Space);
        // COB:                20 FILLER                           PIC X(03)
        // COB:                                                    VALUE SPACES.
        public NChar filler84 = new NChar(3).initial(NChar.Space);
      }

      public WsDateValidationResult wsDateValidationResult = new WsDateValidationResult();
    }

    public WsCalculationVars wsCalculationVars = new WsCalculationVars();
    // COB:          05  WS-DATACHANGED-FLAG                   PIC X(1).
    public NChar wsDatachangedFlag = new NChar(1);

    // COB:            88  NO-CHANGES-FOUND                    VALUE '0'.
    public boolean noChangesFound() {
      return wsDatachangedFlag.equals("0");
    }

    public void setNoChangesFound(boolean value) {
      if (value) wsDatachangedFlag.setValue("0");
    }

    // COB:            88  CHANGE-HAS-OCCURRED                 VALUE '1'.
    public boolean changeHasOccurred() {
      return wsDatachangedFlag.equals("1");
    }

    public void setChangeHasOccurred(boolean value) {
      if (value) wsDatachangedFlag.setValue("1");
    }

    // COB:          05  WS-INPUT-FLAG                         PIC X(1).
    public NChar wsInputFlag = new NChar(1);

    // COB:            88  INPUT-OK                            VALUE '0'.
    public boolean inputOk() {
      return wsInputFlag.equals("0");
    }

    public void setInputOk(boolean value) {
      if (value) wsInputFlag.setValue("0");
    }

    // COB:            88  INPUT-ERROR                         VALUE '1'.
    public boolean inputError() {
      return wsInputFlag.equals("1");
    }

    public void setInputError(boolean value) {
      if (value) wsInputFlag.setValue("1");
    }

    // COB:            88  INPUT-PENDING                       VALUE LOW-VALUES.
    public boolean inputPending() {
      return wsInputFlag.isLowValues();
    }

    public void setInputPending(boolean value) {
      if (value) wsInputFlag.lowValues();
    }

    // COB:          05  WS-RETURN-FLAG                        PIC X(1).
    public NChar wsReturnFlag = new NChar(1);

    // COB:            88  WS-RETURN-FLAG-OFF                  VALUE LOW-VALUES.
    public boolean wsReturnFlagOff() {
      return wsReturnFlag.isLowValues();
    }

    public void setWsReturnFlagOff(boolean value) {
      if (value) wsReturnFlag.lowValues();
    }

    // COB:            88  WS-RETURN-FLAG-ON                   VALUE '1'.
    public boolean wsReturnFlagOn() {
      return wsReturnFlag.equals("1");
    }

    public void setWsReturnFlagOn(boolean value) {
      if (value) wsReturnFlag.setValue("1");
    }

    // COB:          05  WS-PFK-FLAG                           PIC X(1).
    public NChar wsPfkFlag = new NChar(1);

    // COB:            88  PFK-VALID                           VALUE '0'.
    public boolean pfkValid() {
      return wsPfkFlag.equals("0");
    }

    public void setPfkValid(boolean value) {
      if (value) wsPfkFlag.setValue("0");
    }

    // COB:            88  PFK-INVALID                         VALUE '1'.
    public boolean pfkInvalid() {
      return wsPfkFlag.equals("1");
    }

    public void setPfkInvalid(boolean value) {
      if (value) wsPfkFlag.setValue("1");
    }

    // COB:          05  WS-EDIT-ACCT-FLAG                     PIC X(1).
    public NChar wsEditAcctFlag = new NChar(1);

    // COB:            88  FLG-ACCTFILTER-ISVALID              VALUE '1'.
    public boolean flgAcctfilterIsvalid() {
      return wsEditAcctFlag.equals("1");
    }

    public void setFlgAcctfilterIsvalid(boolean value) {
      if (value) wsEditAcctFlag.setValue("1");
    }

    // COB:            88  FLG-ACCTFILTER-NOT-OK               VALUE '0'.
    public boolean flgAcctfilterNotOk() {
      return wsEditAcctFlag.equals("0");
    }

    public void setFlgAcctfilterNotOk(boolean value) {
      if (value) wsEditAcctFlag.setValue("0");
    }

    // COB:            88  FLG-ACCTFILTER-BLANK                VALUE ' '.
    public boolean flgAcctfilterBlank() {
      return wsEditAcctFlag.isSpaces();
    }

    public void setFlgAcctfilterBlank(boolean value) {
      if (value) wsEditAcctFlag.setValue(" ");
    }

    // COB:          05  WS-EDIT-CUST-FLAG                     PIC X(1).
    public NChar wsEditCustFlag = new NChar(1);

    // COB:            88  FLG-CUSTFILTER-ISVALID              VALUE '1'.
    public boolean flgCustfilterIsvalid() {
      return wsEditCustFlag.equals("1");
    }

    public void setFlgCustfilterIsvalid(boolean value) {
      if (value) wsEditCustFlag.setValue("1");
    }

    // COB:            88  FLG-CUSTFILTER-NOT-OK               VALUE '0'.
    public boolean flgCustfilterNotOk() {
      return wsEditCustFlag.equals("0");
    }

    public void setFlgCustfilterNotOk(boolean value) {
      if (value) wsEditCustFlag.setValue("0");
    }

    // COB:            88  FLG-CUSTFILTER-BLANK                VALUE ' '.
    public boolean flgCustfilterBlank() {
      return wsEditCustFlag.isSpaces();
    }

    public void setFlgCustfilterBlank(boolean value) {
      if (value) wsEditCustFlag.setValue(" ");
    }

    // COB:          05 WS-NON-KEY-FLAGS.
    public static class WsNonKeyFlags extends NGroup {
      // COB:            10  WS-EDIT-ACCT-STATUS                 PIC  X(1).
      public NChar wsEditAcctStatus = new NChar(1);

      // COB:                88  FLG-ACCT-STATUS-ISVALID         VALUES 'Y', 'N'.
      public boolean flgAcctStatusIsvalid() {
        return wsEditAcctStatus.equals("Y") || wsEditAcctStatus.equals("N");
      }

      public void setFlgAcctStatusIsvalid(boolean value) {
        if (value) wsEditAcctStatus.setValue("Y");
      }

      // COB:                88  FLG-ACCT-STATUS-NOT-OK          VALUE '0'.
      public boolean flgAcctStatusNotOk() {
        return wsEditAcctStatus.equals("0");
      }

      public void setFlgAcctStatusNotOk(boolean value) {
        if (value) wsEditAcctStatus.setValue("0");
      }

      // COB:                88  FLG-ACCT-STATUS-BLANK           VALUE 'B'.
      public boolean flgAcctStatusBlank() {
        return wsEditAcctStatus.equals("B");
      }

      public void setFlgAcctStatusBlank(boolean value) {
        if (value) wsEditAcctStatus.setValue("B");
      }

      // COB:            10  WS-EDIT-CREDIT-LIMIT                PIC  X(1).
      public NChar wsEditCreditLimit = new NChar(1);

      // COB:                88  FLG-CRED-LIMIT-ISVALID          VALUE LOW-VALUES.
      public boolean flgCredLimitIsvalid() {
        return wsEditCreditLimit.isLowValues();
      }

      public void setFlgCredLimitIsvalid(boolean value) {
        if (value) wsEditCreditLimit.lowValues();
      }

      // COB:                88  FLG-CRED-LIMIT-NOT-OK           VALUE '0'.
      public boolean flgCredLimitNotOk() {
        return wsEditCreditLimit.equals("0");
      }

      public void setFlgCredLimitNotOk(boolean value) {
        if (value) wsEditCreditLimit.setValue("0");
      }

      // COB:                88  FLG-CRED-LIMIT-BLANK            VALUE 'B'.
      public boolean flgCredLimitBlank() {
        return wsEditCreditLimit.equals("B");
      }

      public void setFlgCredLimitBlank(boolean value) {
        if (value) wsEditCreditLimit.setValue("B");
      }

      // COB:            10  WS-EDIT-CASH-CREDIT-LIMIT           PIC  X(1).
      public NChar wsEditCashCreditLimit = new NChar(1);

      // COB:                88  FLG-CASH-CREDIT-LIMIT-ISVALID   VALUE LOW-VALUES.
      public boolean flgCashCreditLimitIsvalid() {
        return wsEditCashCreditLimit.isLowValues();
      }

      public void setFlgCashCreditLimitIsvalid(boolean value) {
        if (value) wsEditCashCreditLimit.lowValues();
      }

      // COB:                88  FLG-CASH-CREDIT-LIMIT-NOT-OK    VALUE '0'.
      public boolean flgCashCreditLimitNotOk() {
        return wsEditCashCreditLimit.equals("0");
      }

      public void setFlgCashCreditLimitNotOk(boolean value) {
        if (value) wsEditCashCreditLimit.setValue("0");
      }

      // COB:                88  FLG-CASH-CREDIT-LIMIT-BLANK     VALUE 'B'.
      public boolean flgCashCreditLimitBlank() {
        return wsEditCashCreditLimit.equals("B");
      }

      public void setFlgCashCreditLimitBlank(boolean value) {
        if (value) wsEditCashCreditLimit.setValue("B");
      }

      // COB:            10  WS-EDIT-CURR-BAL                    PIC  X(1).
      public NChar wsEditCurrBal = new NChar(1);

      // COB:                88  FLG-CURR-BAL-ISVALID            VALUE LOW-VALUES.
      public boolean flgCurrBalIsvalid() {
        return wsEditCurrBal.isLowValues();
      }

      public void setFlgCurrBalIsvalid(boolean value) {
        if (value) wsEditCurrBal.lowValues();
      }

      // COB:                88  FLG-CURR-BAL-NOT-OK             VALUE '0'.
      public boolean flgCurrBalNotOk() {
        return wsEditCurrBal.equals("0");
      }

      public void setFlgCurrBalNotOk(boolean value) {
        if (value) wsEditCurrBal.setValue("0");
      }

      // COB:                88  FLG-CURR-BAL-BLANK              VALUE 'B'.
      public boolean flgCurrBalBlank() {
        return wsEditCurrBal.equals("B");
      }

      public void setFlgCurrBalBlank(boolean value) {
        if (value) wsEditCurrBal.setValue("B");
      }

      // COB:            10  WS-EDIT-CURR-CYC-CREDIT             PIC  X(1).
      public NChar wsEditCurrCycCredit = new NChar(1);

      // COB:                88  FLG-CURR-CYC-CREDIT-ISVALID     VALUE LOW-VALUES.
      public boolean flgCurrCycCreditIsvalid() {
        return wsEditCurrCycCredit.isLowValues();
      }

      public void setFlgCurrCycCreditIsvalid(boolean value) {
        if (value) wsEditCurrCycCredit.lowValues();
      }

      // COB:                88  FLG-CURR-CYC-CREDIT-NOT-OK      VALUE '0'.
      public boolean flgCurrCycCreditNotOk() {
        return wsEditCurrCycCredit.equals("0");
      }

      public void setFlgCurrCycCreditNotOk(boolean value) {
        if (value) wsEditCurrCycCredit.setValue("0");
      }

      // COB:                88  FLG-CURR-CYC-CREDIT-BLANK       VALUE 'B'.
      public boolean flgCurrCycCreditBlank() {
        return wsEditCurrCycCredit.equals("B");
      }

      public void setFlgCurrCycCreditBlank(boolean value) {
        if (value) wsEditCurrCycCredit.setValue("B");
      }

      // COB:            10  WS-EDIT-CURR-CYC-DEBIT              PIC  X(1).
      public NChar wsEditCurrCycDebit = new NChar(1);

      // COB:                88  FLG-CURR-CYC-DEBIT-ISVALID      VALUE LOW-VALUES.
      public boolean flgCurrCycDebitIsvalid() {
        return wsEditCurrCycDebit.isLowValues();
      }

      public void setFlgCurrCycDebitIsvalid(boolean value) {
        if (value) wsEditCurrCycDebit.lowValues();
      }

      // COB:                88  FLG-CURR-CYC-DEBIT-NOT-OK       VALUE '0'.
      public boolean flgCurrCycDebitNotOk() {
        return wsEditCurrCycDebit.equals("0");
      }

      public void setFlgCurrCycDebitNotOk(boolean value) {
        if (value) wsEditCurrCycDebit.setValue("0");
      }

      // COB:                88  FLG-CURR-CYC-DEBIT-BLANK        VALUE 'B'.
      public boolean flgCurrCycDebitBlank() {
        return wsEditCurrCycDebit.equals("B");
      }

      public void setFlgCurrCycDebitBlank(boolean value) {
        if (value) wsEditCurrCycDebit.setValue("B");
      }

      // COB:            10 WS-EDIT-DT-OF-BIRTH-FLGS.
      public static class WsEditDtOfBirthFlgs extends NGroup {
        // COB:                88 WS-EDIT-DT-OF-BIRTH-INVALID      VALUE '000'.
        public boolean wsEditDtOfBirthInvalid() {
          return this.equals("000");
        }

        public void setWsEditDtOfBirthInvalid(boolean value) {
          if (value) this.setValue("000");
        }

        // COB:                88 WS-EDIT-DT-OF-BIRTH-ISVALID      VALUE LOW-VALUES.
        public boolean wsEditDtOfBirthIsvalid() {
          return this.isLowValues();
        }

        public void setWsEditDtOfBirthIsvalid(boolean value) {
          if (value) this.lowValues();
        }

        // COB:                20 WS-EDIT-DT-OF-BIRTH-YEAR-FLG     PIC X(01).
        public NChar wsEditDtOfBirthYearFlg = new NChar(1);

        // COB:                   88 FLG-DT-OF-BIRTH-YEAR-ISVALID  VALUE LOW-VALUES.
        public boolean flgDtOfBirthYearIsvalid() {
          return wsEditDtOfBirthYearFlg.isLowValues();
        }

        public void setFlgDtOfBirthYearIsvalid(boolean value) {
          if (value) wsEditDtOfBirthYearFlg.lowValues();
        }

        // COB:                   88 FLG-DT-OF-BIRTH-YEAR-NOT-OK   VALUE '0'.
        public boolean flgDtOfBirthYearNotOk() {
          return wsEditDtOfBirthYearFlg.equals("0");
        }

        public void setFlgDtOfBirthYearNotOk(boolean value) {
          if (value) wsEditDtOfBirthYearFlg.setValue("0");
        }

        // COB:                   88 FLG-DT-OF-BIRTH-YEAR-BLANK    VALUE 'B'.
        public boolean flgDtOfBirthYearBlank() {
          return wsEditDtOfBirthYearFlg.equals("B");
        }

        public void setFlgDtOfBirthYearBlank(boolean value) {
          if (value) wsEditDtOfBirthYearFlg.setValue("B");
        }

        // COB:                20 WS-EDIT-DT-OF-BIRTH-MONTH        PIC X(01).
        public NChar wsEditDtOfBirthMonth = new NChar(1);

        // COB:                   88 FLG-DT-OF-BIRTH-MONTH-ISVALID VALUE LOW-VALUES.
        public boolean flgDtOfBirthMonthIsvalid() {
          return wsEditDtOfBirthMonth.isLowValues();
        }

        public void setFlgDtOfBirthMonthIsvalid(boolean value) {
          if (value) wsEditDtOfBirthMonth.lowValues();
        }

        // COB:                   88 FLG-DT-OF-BIRTH-MONTH-NOT-OK  VALUE '0'.
        public boolean flgDtOfBirthMonthNotOk() {
          return wsEditDtOfBirthMonth.equals("0");
        }

        public void setFlgDtOfBirthMonthNotOk(boolean value) {
          if (value) wsEditDtOfBirthMonth.setValue("0");
        }

        // COB:                   88 FLG-DT-OF-BIRTH-MONTH-BLANK   VALUE 'B'.
        public boolean flgDtOfBirthMonthBlank() {
          return wsEditDtOfBirthMonth.equals("B");
        }

        public void setFlgDtOfBirthMonthBlank(boolean value) {
          if (value) wsEditDtOfBirthMonth.setValue("B");
        }

        // COB:                20 WS-EDIT-DT-OF-BIRTH-DAY          PIC X(01).
        public NChar wsEditDtOfBirthDay = new NChar(1);

        // COB:                   88 FLG-DT-OF-BIRTH-DAY-ISVALID   VALUE LOW-VALUES.
        public boolean flgDtOfBirthDayIsvalid() {
          return wsEditDtOfBirthDay.isLowValues();
        }

        public void setFlgDtOfBirthDayIsvalid(boolean value) {
          if (value) wsEditDtOfBirthDay.lowValues();
        }

        // COB:                   88 FLG-DT-OF-BIRTH-DAY-NOT-OK    VALUE '0'.
        public boolean flgDtOfBirthDayNotOk() {
          return wsEditDtOfBirthDay.equals("0");
        }

        public void setFlgDtOfBirthDayNotOk(boolean value) {
          if (value) wsEditDtOfBirthDay.setValue("0");
        }

        // COB:                   88 FLG-DT-OF-BIRTH-DAY-BLANK     VALUE 'B'.
        public boolean flgDtOfBirthDayBlank() {
          return wsEditDtOfBirthDay.equals("B");
        }

        public void setFlgDtOfBirthDayBlank(boolean value) {
          if (value) wsEditDtOfBirthDay.setValue("B");
        }
      }

      public WsEditDtOfBirthFlgs wsEditDtOfBirthFlgs = new WsEditDtOfBirthFlgs();
      // COB:            10  WS-EDIT-FICO-SCORE-FLGS             PIC  X(1).
      public NChar wsEditFicoScoreFlgs = new NChar(1);

      // COB:                88  FLG-FICO-SCORE-ISVALID          VALUE LOW-VALUES.
      public boolean flgFicoScoreIsvalid() {
        return wsEditFicoScoreFlgs.isLowValues();
      }

      public void setFlgFicoScoreIsvalid(boolean value) {
        if (value) wsEditFicoScoreFlgs.lowValues();
      }

      // COB:                88  FLG-FICO-SCORE-NOT-OK           VALUE '0'.
      public boolean flgFicoScoreNotOk() {
        return wsEditFicoScoreFlgs.equals("0");
      }

      public void setFlgFicoScoreNotOk(boolean value) {
        if (value) wsEditFicoScoreFlgs.setValue("0");
      }

      // COB:                88  FLG-FICO-SCORE-BLANK            VALUE 'B'.
      public boolean flgFicoScoreBlank() {
        return wsEditFicoScoreFlgs.equals("B");
      }

      public void setFlgFicoScoreBlank(boolean value) {
        if (value) wsEditFicoScoreFlgs.setValue("B");
      }

      // COB:            10 WS-EDIT-OPEN-DATE-FLGS.
      public static class WsEditOpenDateFlgs extends NGroup {
        // COB:                88 WS-EDIT-OPEN-DATE-IS-INVALID     VALUE '000'.
        public boolean wsEditOpenDateIsInvalid() {
          return this.equals("000");
        }

        public void setWsEditOpenDateIsInvalid(boolean value) {
          if (value) this.setValue("000");
        }

        // COB:                20 WS-EDIT-OPEN-YEAR-FLG            PIC X(01).
        public NChar wsEditOpenYearFlg = new NChar(1);

        // COB:                   88 FLG-OPEN-YEAR-ISVALID         VALUE LOW-VALUES.
        public boolean flgOpenYearIsvalid() {
          return wsEditOpenYearFlg.isLowValues();
        }

        public void setFlgOpenYearIsvalid(boolean value) {
          if (value) wsEditOpenYearFlg.lowValues();
        }

        // COB:                   88 FLG-OPEN-YEAR-NOT-OK          VALUE '0'.
        public boolean flgOpenYearNotOk() {
          return wsEditOpenYearFlg.equals("0");
        }

        public void setFlgOpenYearNotOk(boolean value) {
          if (value) wsEditOpenYearFlg.setValue("0");
        }

        // COB:                   88 FLG-OPEN-YEAR-BLANK           VALUE 'B'.
        public boolean flgOpenYearBlank() {
          return wsEditOpenYearFlg.equals("B");
        }

        public void setFlgOpenYearBlank(boolean value) {
          if (value) wsEditOpenYearFlg.setValue("B");
        }

        // COB:                20 WS-EDIT-OPEN-MONTH               PIC X(01).
        public NChar wsEditOpenMonth = new NChar(1);

        // COB:                   88 FLG-OPEN-MONTH-ISVALID        VALUE LOW-VALUES.
        public boolean flgOpenMonthIsvalid() {
          return wsEditOpenMonth.isLowValues();
        }

        public void setFlgOpenMonthIsvalid(boolean value) {
          if (value) wsEditOpenMonth.lowValues();
        }

        // COB:                   88 FLG-OPEN-MONTH-NOT-OK         VALUE '0'.
        public boolean flgOpenMonthNotOk() {
          return wsEditOpenMonth.equals("0");
        }

        public void setFlgOpenMonthNotOk(boolean value) {
          if (value) wsEditOpenMonth.setValue("0");
        }

        // COB:                   88 FLG-OPEN-MONTH-BLANK          VALUE 'B'.
        public boolean flgOpenMonthBlank() {
          return wsEditOpenMonth.equals("B");
        }

        public void setFlgOpenMonthBlank(boolean value) {
          if (value) wsEditOpenMonth.setValue("B");
        }

        // COB:                20 WS-EDIT-OPEN-DAY                 PIC X(01).
        public NChar wsEditOpenDay = new NChar(1);

        // COB:                   88 FLG-OPEN-DAY-ISVALID          VALUE LOW-VALUES.
        public boolean flgOpenDayIsvalid() {
          return wsEditOpenDay.isLowValues();
        }

        public void setFlgOpenDayIsvalid(boolean value) {
          if (value) wsEditOpenDay.lowValues();
        }

        // COB:                   88 FLG-OPEN-DAY-NOT-OK           VALUE '0'.
        public boolean flgOpenDayNotOk() {
          return wsEditOpenDay.equals("0");
        }

        public void setFlgOpenDayNotOk(boolean value) {
          if (value) wsEditOpenDay.setValue("0");
        }

        // COB:                   88 FLG-OPEN-DAY-BLANK            VALUE 'B'.
        public boolean flgOpenDayBlank() {
          return wsEditOpenDay.equals("B");
        }

        public void setFlgOpenDayBlank(boolean value) {
          if (value) wsEditOpenDay.setValue("B");
        }
      }

      public WsEditOpenDateFlgs wsEditOpenDateFlgs = new WsEditOpenDateFlgs();

      // COB:            10 WS-EXPIRY-DATE-FLGS.
      public static class WsExpiryDateFlgs extends NGroup {
        // COB:                88 WS-EDIT-EXPIRY-IS-INVALID        VALUE '000'.
        public boolean wsEditExpiryIsInvalid() {
          return this.equals("000");
        }

        public void setWsEditExpiryIsInvalid(boolean value) {
          if (value) this.setValue("000");
        }

        // COB:                20 WS-EDIT-EXPIRY-YEAR-FLG          PIC X(01).
        public NChar wsEditExpiryYearFlg = new NChar(1);

        // COB:                   88 FLG-EXPIRY-YEAR-ISVALID       VALUE LOW-VALUES.
        public boolean flgExpiryYearIsvalid() {
          return wsEditExpiryYearFlg.isLowValues();
        }

        public void setFlgExpiryYearIsvalid(boolean value) {
          if (value) wsEditExpiryYearFlg.lowValues();
        }

        // COB:                   88 FLG-EXPIRY-YEAR-NOT-OK        VALUE '0'.
        public boolean flgExpiryYearNotOk() {
          return wsEditExpiryYearFlg.equals("0");
        }

        public void setFlgExpiryYearNotOk(boolean value) {
          if (value) wsEditExpiryYearFlg.setValue("0");
        }

        // COB:                   88 FLG-EXPIRY-YEAR-BLANK         VALUE 'B'.
        public boolean flgExpiryYearBlank() {
          return wsEditExpiryYearFlg.equals("B");
        }

        public void setFlgExpiryYearBlank(boolean value) {
          if (value) wsEditExpiryYearFlg.setValue("B");
        }

        // COB:                20 WS-EDIT-EXPIRY-MONTH             PIC X(01).
        public NChar wsEditExpiryMonth = new NChar(1);

        // COB:                   88 FLG-EXPIRY-MONTH-ISVALID      VALUE LOW-VALUES.
        public boolean flgExpiryMonthIsvalid() {
          return wsEditExpiryMonth.isLowValues();
        }

        public void setFlgExpiryMonthIsvalid(boolean value) {
          if (value) wsEditExpiryMonth.lowValues();
        }

        // COB:                   88 FLG-EXPIRY-MONTH-NOT-OK       VALUE '0'.
        public boolean flgExpiryMonthNotOk() {
          return wsEditExpiryMonth.equals("0");
        }

        public void setFlgExpiryMonthNotOk(boolean value) {
          if (value) wsEditExpiryMonth.setValue("0");
        }

        // COB:                   88 FLG-EXPIRY-MONTH-BLANK        VALUE 'B'.
        public boolean flgExpiryMonthBlank() {
          return wsEditExpiryMonth.equals("B");
        }

        public void setFlgExpiryMonthBlank(boolean value) {
          if (value) wsEditExpiryMonth.setValue("B");
        }

        // COB:                20 WS-EDIT-EXPIRY-DAY               PIC X(01).
        public NChar wsEditExpiryDay = new NChar(1);

        // COB:                   88 FLG-EXPIRY-DAY-ISVALID        VALUE LOW-VALUES.
        public boolean flgExpiryDayIsvalid() {
          return wsEditExpiryDay.isLowValues();
        }

        public void setFlgExpiryDayIsvalid(boolean value) {
          if (value) wsEditExpiryDay.lowValues();
        }

        // COB:                   88 FLG-EXPIRY-DAY-NOT-OK         VALUE '0'.
        public boolean flgExpiryDayNotOk() {
          return wsEditExpiryDay.equals("0");
        }

        public void setFlgExpiryDayNotOk(boolean value) {
          if (value) wsEditExpiryDay.setValue("0");
        }

        // COB:                   88 FLG-EXPIRY-DAY-BLANK          VALUE 'B'.
        public boolean flgExpiryDayBlank() {
          return wsEditExpiryDay.equals("B");
        }

        public void setFlgExpiryDayBlank(boolean value) {
          if (value) wsEditExpiryDay.setValue("B");
        }
      }

      public WsExpiryDateFlgs wsExpiryDateFlgs = new WsExpiryDateFlgs();

      // COB:            10 WS-EDIT-REISSUE-DATE-FLGS.
      public static class WsEditReissueDateFlgs extends NGroup {
        // COB:                88 WS-EDIT-REISSUE-DATE-INVALID     VALUE '000'.
        public boolean wsEditReissueDateInvalid() {
          return this.equals("000");
        }

        public void setWsEditReissueDateInvalid(boolean value) {
          if (value) this.setValue("000");
        }

        // COB:                20 WS-EDIT-REISSUE-YEAR-FLG         PIC X(01).
        public NChar wsEditReissueYearFlg = new NChar(1);

        // COB:                   88 FLG-REISSUE-YEAR-ISVALID      VALUE LOW-VALUES.
        public boolean flgReissueYearIsvalid() {
          return wsEditReissueYearFlg.isLowValues();
        }

        public void setFlgReissueYearIsvalid(boolean value) {
          if (value) wsEditReissueYearFlg.lowValues();
        }

        // COB:                   88 FLG-REISSUE-YEAR-NOT-OK       VALUE '0'.
        public boolean flgReissueYearNotOk() {
          return wsEditReissueYearFlg.equals("0");
        }

        public void setFlgReissueYearNotOk(boolean value) {
          if (value) wsEditReissueYearFlg.setValue("0");
        }

        // COB:                   88 FLG-REISSUE-YEAR-BLANK        VALUE 'B'.
        public boolean flgReissueYearBlank() {
          return wsEditReissueYearFlg.equals("B");
        }

        public void setFlgReissueYearBlank(boolean value) {
          if (value) wsEditReissueYearFlg.setValue("B");
        }

        // COB:                20 WS-EDIT-REISSUE-MONTH            PIC X(01).
        public NChar wsEditReissueMonth = new NChar(1);

        // COB:                   88 FLG-REISSUE-MONTH-ISVALID     VALUE LOW-VALUES.
        public boolean flgReissueMonthIsvalid() {
          return wsEditReissueMonth.isLowValues();
        }

        public void setFlgReissueMonthIsvalid(boolean value) {
          if (value) wsEditReissueMonth.lowValues();
        }

        // COB:                   88 FLG-REISSUE-MONTH-NOT-OK      VALUE '0'.
        public boolean flgReissueMonthNotOk() {
          return wsEditReissueMonth.equals("0");
        }

        public void setFlgReissueMonthNotOk(boolean value) {
          if (value) wsEditReissueMonth.setValue("0");
        }

        // COB:                   88 FLG-REISSUE-MONTH-BLANK       VALUE 'B'.
        public boolean flgReissueMonthBlank() {
          return wsEditReissueMonth.equals("B");
        }

        public void setFlgReissueMonthBlank(boolean value) {
          if (value) wsEditReissueMonth.setValue("B");
        }

        // COB:                20 WS-EDIT-REISSUE-DAY              PIC X(01).
        public NChar wsEditReissueDay = new NChar(1);

        // COB:                   88 FLG-REISSUE-DAY-ISVALID       VALUE LOW-VALUES.
        public boolean flgReissueDayIsvalid() {
          return wsEditReissueDay.isLowValues();
        }

        public void setFlgReissueDayIsvalid(boolean value) {
          if (value) wsEditReissueDay.lowValues();
        }

        // COB:                   88 FLG-REISSUE-DAY-NOT-OK        VALUE '0'.
        public boolean flgReissueDayNotOk() {
          return wsEditReissueDay.equals("0");
        }

        public void setFlgReissueDayNotOk(boolean value) {
          if (value) wsEditReissueDay.setValue("0");
        }

        // COB:                   88 FLG-REISSUE-DAY-BLANK         VALUE 'B'.
        public boolean flgReissueDayBlank() {
          return wsEditReissueDay.equals("B");
        }

        public void setFlgReissueDayBlank(boolean value) {
          if (value) wsEditReissueDay.setValue("B");
        }
      }

      public WsEditReissueDateFlgs wsEditReissueDateFlgs = new WsEditReissueDateFlgs();

      // COB:            10 WS-EDIT-NAME-FLAGS.
      public static class WsEditNameFlags extends NGroup {
        // COB:                20 WS-EDIT-FIRST-NAME-FLGS          PIC X(01).
        public NChar wsEditFirstNameFlgs = new NChar(1);

        // COB:                   88 FLG-FIRST-NAME-ISVALID        VALUE LOW-VALUES.
        public boolean flgFirstNameIsvalid() {
          return wsEditFirstNameFlgs.isLowValues();
        }

        public void setFlgFirstNameIsvalid(boolean value) {
          if (value) wsEditFirstNameFlgs.lowValues();
        }

        // COB:                   88 FLG-FIRST-NAME-NOT-OK         VALUE '0'.
        public boolean flgFirstNameNotOk() {
          return wsEditFirstNameFlgs.equals("0");
        }

        public void setFlgFirstNameNotOk(boolean value) {
          if (value) wsEditFirstNameFlgs.setValue("0");
        }

        // COB:                   88 FLG-FIRST-NAME-BLANK          VALUE 'B'.
        public boolean flgFirstNameBlank() {
          return wsEditFirstNameFlgs.equals("B");
        }

        public void setFlgFirstNameBlank(boolean value) {
          if (value) wsEditFirstNameFlgs.setValue("B");
        }

        // COB:                20 WS-EDIT-MIDDLE-NAME-FLGS         PIC X(01).
        public NChar wsEditMiddleNameFlgs = new NChar(1);

        // COB:                   88 FLG-MIDDLE-NAME-ISVALID       VALUE LOW-VALUES.
        public boolean flgMiddleNameIsvalid() {
          return wsEditMiddleNameFlgs.isLowValues();
        }

        public void setFlgMiddleNameIsvalid(boolean value) {
          if (value) wsEditMiddleNameFlgs.lowValues();
        }

        // COB:                   88 FLG-MIDDLE-NAME-NOT-OK        VALUE '0'.
        public boolean flgMiddleNameNotOk() {
          return wsEditMiddleNameFlgs.equals("0");
        }

        public void setFlgMiddleNameNotOk(boolean value) {
          if (value) wsEditMiddleNameFlgs.setValue("0");
        }

        // COB:                   88 FLG-MIDDLE-NAME-BLANK         VALUE 'B'.
        public boolean flgMiddleNameBlank() {
          return wsEditMiddleNameFlgs.equals("B");
        }

        public void setFlgMiddleNameBlank(boolean value) {
          if (value) wsEditMiddleNameFlgs.setValue("B");
        }

        // COB:                20 WS-EDIT-LAST-NAME-FLGS           PIC X(01).
        public NChar wsEditLastNameFlgs = new NChar(1);

        // COB:                   88 FLG-LAST-NAME-ISVALID         VALUE LOW-VALUES.
        public boolean flgLastNameIsvalid() {
          return wsEditLastNameFlgs.isLowValues();
        }

        public void setFlgLastNameIsvalid(boolean value) {
          if (value) wsEditLastNameFlgs.lowValues();
        }

        // COB:                   88 FLG-LAST-NAME-NOT-OK          VALUE '0'.
        public boolean flgLastNameNotOk() {
          return wsEditLastNameFlgs.equals("0");
        }

        public void setFlgLastNameNotOk(boolean value) {
          if (value) wsEditLastNameFlgs.setValue("0");
        }

        // COB:                   88 FLG-LAST-NAME-BLANK           VALUE 'B'.
        public boolean flgLastNameBlank() {
          return wsEditLastNameFlgs.equals("B");
        }

        public void setFlgLastNameBlank(boolean value) {
          if (value) wsEditLastNameFlgs.setValue("B");
        }
      }

      public WsEditNameFlags wsEditNameFlags = new WsEditNameFlags();

      // COB:            10 WS-EDIT-ADDRESS-FLAGS.
      public static class WsEditAddressFlags extends NGroup {
        // COB:                20 WS-EDIT-ADDRESS-LINE-1-FLGS      PIC X(01).
        public NChar wsEditAddressLine1Flgs = new NChar(1);

        // COB:                   88 FLG-ADDRESS-LINE-1-ISVALID    VALUE LOW-VALUES.
        public boolean flgAddressLine1Isvalid() {
          return wsEditAddressLine1Flgs.isLowValues();
        }

        public void setFlgAddressLine1Isvalid(boolean value) {
          if (value) wsEditAddressLine1Flgs.lowValues();
        }

        // COB:                   88 FLG-ADDRESS-LINE-1-NOT-OK     VALUE '0'.
        public boolean flgAddressLine1NotOk() {
          return wsEditAddressLine1Flgs.equals("0");
        }

        public void setFlgAddressLine1NotOk(boolean value) {
          if (value) wsEditAddressLine1Flgs.setValue("0");
        }

        // COB:                   88 FLG-ADDRESS-LINE-1-BLANK      VALUE 'B'.
        public boolean flgAddressLine1Blank() {
          return wsEditAddressLine1Flgs.equals("B");
        }

        public void setFlgAddressLine1Blank(boolean value) {
          if (value) wsEditAddressLine1Flgs.setValue("B");
        }

        // COB:                20 WS-EDIT-ADDRESS-LINE-2-FLGS      PIC X(01).
        public NChar wsEditAddressLine2Flgs = new NChar(1);

        // COB:                   88 FLG-ADDRESS-LINE-2-ISVALID    VALUE LOW-VALUES.
        public boolean flgAddressLine2Isvalid() {
          return wsEditAddressLine2Flgs.isLowValues();
        }

        public void setFlgAddressLine2Isvalid(boolean value) {
          if (value) wsEditAddressLine2Flgs.lowValues();
        }

        // COB:                   88 FLG-ADDRESS-LINE-2-NOT-OK     VALUE '0'.
        public boolean flgAddressLine2NotOk() {
          return wsEditAddressLine2Flgs.equals("0");
        }

        public void setFlgAddressLine2NotOk(boolean value) {
          if (value) wsEditAddressLine2Flgs.setValue("0");
        }

        // COB:                   88 FLG-ADDRESS-LINE-2-BLANK      VALUE 'B'.
        public boolean flgAddressLine2Blank() {
          return wsEditAddressLine2Flgs.equals("B");
        }

        public void setFlgAddressLine2Blank(boolean value) {
          if (value) wsEditAddressLine2Flgs.setValue("B");
        }

        // COB:                20 WS-EDIT-CITY-FLGS                PIC X(01).
        public NChar wsEditCityFlgs = new NChar(1);

        // COB:                   88 FLG-CITY-ISVALID              VALUE LOW-VALUES.
        public boolean flgCityIsvalid() {
          return wsEditCityFlgs.isLowValues();
        }

        public void setFlgCityIsvalid(boolean value) {
          if (value) wsEditCityFlgs.lowValues();
        }

        // COB:                   88 FLG-CITY-NOT-OK               VALUE '0'.
        public boolean flgCityNotOk() {
          return wsEditCityFlgs.equals("0");
        }

        public void setFlgCityNotOk(boolean value) {
          if (value) wsEditCityFlgs.setValue("0");
        }

        // COB:                   88 FLG-CITY-BLANK                VALUE 'B'.
        public boolean flgCityBlank() {
          return wsEditCityFlgs.equals("B");
        }

        public void setFlgCityBlank(boolean value) {
          if (value) wsEditCityFlgs.setValue("B");
        }

        // COB:                20 WS-EDIT-STATE-FLGS               PIC X(01).
        public NChar wsEditStateFlgs = new NChar(1);

        // COB:                   88 FLG-STATE-ISVALID             VALUE LOW-VALUES.
        public boolean flgStateIsvalid() {
          return wsEditStateFlgs.isLowValues();
        }

        public void setFlgStateIsvalid(boolean value) {
          if (value) wsEditStateFlgs.lowValues();
        }

        // COB:                   88 FLG-STATE-NOT-OK              VALUE '0'.
        public boolean flgStateNotOk() {
          return wsEditStateFlgs.equals("0");
        }

        public void setFlgStateNotOk(boolean value) {
          if (value) wsEditStateFlgs.setValue("0");
        }

        // COB:                   88 FLG-STATE-BLANK               VALUE 'B'.
        public boolean flgStateBlank() {
          return wsEditStateFlgs.equals("B");
        }

        public void setFlgStateBlank(boolean value) {
          if (value) wsEditStateFlgs.setValue("B");
        }

        // COB:                20 WS-EDIT-ZIPCODE-FLGS             PIC X(01).
        public NChar wsEditZipcodeFlgs = new NChar(1);

        // COB:                   88 FLG-ZIPCODE-ISVALID           VALUE LOW-VALUES.
        public boolean flgZipcodeIsvalid() {
          return wsEditZipcodeFlgs.isLowValues();
        }

        public void setFlgZipcodeIsvalid(boolean value) {
          if (value) wsEditZipcodeFlgs.lowValues();
        }

        // COB:                   88 FLG-ZIPCODE-NOT-OK            VALUE '0'.
        public boolean flgZipcodeNotOk() {
          return wsEditZipcodeFlgs.equals("0");
        }

        public void setFlgZipcodeNotOk(boolean value) {
          if (value) wsEditZipcodeFlgs.setValue("0");
        }

        // COB:                   88 FLG-ZIPCODE-BLANK             VALUE 'B'.
        public boolean flgZipcodeBlank() {
          return wsEditZipcodeFlgs.equals("B");
        }

        public void setFlgZipcodeBlank(boolean value) {
          if (value) wsEditZipcodeFlgs.setValue("B");
        }

        // COB:                20 WS-EDIT-COUNTRY-FLGS             PIC X(01).
        public NChar wsEditCountryFlgs = new NChar(1);

        // COB:                   88 FLG-COUNTRY-ISVALID           VALUE LOW-VALUES.
        public boolean flgCountryIsvalid() {
          return wsEditCountryFlgs.isLowValues();
        }

        public void setFlgCountryIsvalid(boolean value) {
          if (value) wsEditCountryFlgs.lowValues();
        }

        // COB:                   88 FLG-COUNTRY-NOT-OK            VALUE '0'.
        public boolean flgCountryNotOk() {
          return wsEditCountryFlgs.equals("0");
        }

        public void setFlgCountryNotOk(boolean value) {
          if (value) wsEditCountryFlgs.setValue("0");
        }

        // COB:                   88 FLG-COUNTRY-BLANK             VALUE 'B'.
        public boolean flgCountryBlank() {
          return wsEditCountryFlgs.equals("B");
        }

        public void setFlgCountryBlank(boolean value) {
          if (value) wsEditCountryFlgs.setValue("B");
        }

        // COB:                20 WS-EDIT-PHONE-NUM-1-FLGS.
        public static class WsEditPhoneNum1Flgs extends NGroup {
          // COB:                   88 WS-EDIT-PHONE-NUM-1-IS-INVALID
          public boolean wsEditPhoneNum1IsInvalid() {
            return this.equals("000");
          }

          public void setWsEditPhoneNum1IsInvalid(boolean value) {
            if (value) this.setValue("000");
          }

          // COB:                   30 WS-EDIT-PHONE-NUM-1A-FLG      PIC X(01).
          public NChar wsEditPhoneNum1aFlg = new NChar(1);

          // COB:                      88 FLG-PHONE-NUM-1A-ISVALID   VALUE LOW-VALUES.
          public boolean flgPhoneNum1aIsvalid() {
            return wsEditPhoneNum1aFlg.isLowValues();
          }

          public void setFlgPhoneNum1aIsvalid(boolean value) {
            if (value) wsEditPhoneNum1aFlg.lowValues();
          }

          // COB:                      88 FLG-PHONE-NUM-1A-NOT-OK    VALUE '0'.
          public boolean flgPhoneNum1aNotOk() {
            return wsEditPhoneNum1aFlg.equals("0");
          }

          public void setFlgPhoneNum1aNotOk(boolean value) {
            if (value) wsEditPhoneNum1aFlg.setValue("0");
          }

          // COB:                      88 FLG-PHONE-NUM-1A-BLANK     VALUE 'B'.
          public boolean flgPhoneNum1aBlank() {
            return wsEditPhoneNum1aFlg.equals("B");
          }

          public void setFlgPhoneNum1aBlank(boolean value) {
            if (value) wsEditPhoneNum1aFlg.setValue("B");
          }

          // COB:                   30 WS-EDIT-PHONE-NUM-1B          PIC X(01).
          public NChar wsEditPhoneNum1b = new NChar(1);

          // COB:                      88 FLG-PHONE-NUM-1B-ISVALID   VALUE LOW-VALUES.
          public boolean flgPhoneNum1bIsvalid() {
            return wsEditPhoneNum1b.isLowValues();
          }

          public void setFlgPhoneNum1bIsvalid(boolean value) {
            if (value) wsEditPhoneNum1b.lowValues();
          }

          // COB:                      88 FLG-PHONE-NUM-1B-NOT-OK    VALUE '0'.
          public boolean flgPhoneNum1bNotOk() {
            return wsEditPhoneNum1b.equals("0");
          }

          public void setFlgPhoneNum1bNotOk(boolean value) {
            if (value) wsEditPhoneNum1b.setValue("0");
          }

          // COB:                      88 FLG-PHONE-NUM-1B-BLANK     VALUE 'B'.
          public boolean flgPhoneNum1bBlank() {
            return wsEditPhoneNum1b.equals("B");
          }

          public void setFlgPhoneNum1bBlank(boolean value) {
            if (value) wsEditPhoneNum1b.setValue("B");
          }

          // COB:                   30 WS-EDIT-PHONE-NUM-1C          PIC X(01).
          public NChar wsEditPhoneNum1c = new NChar(1);

          // COB:                      88 FLG-PHONE-NUM-1C-ISVALID   VALUE LOW-VALUES.
          public boolean flgPhoneNum1cIsvalid() {
            return wsEditPhoneNum1c.isLowValues();
          }

          public void setFlgPhoneNum1cIsvalid(boolean value) {
            if (value) wsEditPhoneNum1c.lowValues();
          }

          // COB:                      88 FLG-PHONE-NUM-1C-NOT-OK    VALUE '0'.
          public boolean flgPhoneNum1cNotOk() {
            return wsEditPhoneNum1c.equals("0");
          }

          public void setFlgPhoneNum1cNotOk(boolean value) {
            if (value) wsEditPhoneNum1c.setValue("0");
          }

          // COB:                      88 FLG-PHONE-NUM-1C-BLANK     VALUE 'B'.
          public boolean flgPhoneNum1cBlank() {
            return wsEditPhoneNum1c.equals("B");
          }

          public void setFlgPhoneNum1cBlank(boolean value) {
            if (value) wsEditPhoneNum1c.setValue("B");
          }
        }

        public WsEditPhoneNum1Flgs wsEditPhoneNum1Flgs = new WsEditPhoneNum1Flgs();

        // COB:                20 WS-EDIT-PHONE-NUM-2-FLGS.
        public static class WsEditPhoneNum2Flgs extends NGroup {
          // COB:                   88 WS-EDIT-PHONE-NUM-2-IS-INVALID
          public boolean wsEditPhoneNum2IsInvalid() {
            return this.equals("000");
          }

          public void setWsEditPhoneNum2IsInvalid(boolean value) {
            if (value) this.setValue("000");
          }

          // COB:                   30 WS-EDIT-PHONE-NUM-2A-FLG      PIC X(01).
          public NChar wsEditPhoneNum2aFlg = new NChar(1);

          // COB:                      88 FLG-PHONE-NUM-2A-ISVALID   VALUE LOW-VALUES.
          public boolean flgPhoneNum2aIsvalid() {
            return wsEditPhoneNum2aFlg.isLowValues();
          }

          public void setFlgPhoneNum2aIsvalid(boolean value) {
            if (value) wsEditPhoneNum2aFlg.lowValues();
          }

          // COB:                      88 FLG-PHONE-NUM-2A-NOT-OK    VALUE '0'.
          public boolean flgPhoneNum2aNotOk() {
            return wsEditPhoneNum2aFlg.equals("0");
          }

          public void setFlgPhoneNum2aNotOk(boolean value) {
            if (value) wsEditPhoneNum2aFlg.setValue("0");
          }

          // COB:                      88 FLG-PHONE-NUM-2A-BLANK     VALUE 'B'.
          public boolean flgPhoneNum2aBlank() {
            return wsEditPhoneNum2aFlg.equals("B");
          }

          public void setFlgPhoneNum2aBlank(boolean value) {
            if (value) wsEditPhoneNum2aFlg.setValue("B");
          }

          // COB:                   30 WS-EDIT-PHONE-NUM-2B          PIC X(01).
          public NChar wsEditPhoneNum2b = new NChar(1);

          // COB:                      88 FLG-PHONE-NUM-2B-ISVALID   VALUE LOW-VALUES.
          public boolean flgPhoneNum2bIsvalid() {
            return wsEditPhoneNum2b.isLowValues();
          }

          public void setFlgPhoneNum2bIsvalid(boolean value) {
            if (value) wsEditPhoneNum2b.lowValues();
          }

          // COB:                      88 FLG-PHONE-NUM-2B-NOT-OK    VALUE '0'.
          public boolean flgPhoneNum2bNotOk() {
            return wsEditPhoneNum2b.equals("0");
          }

          public void setFlgPhoneNum2bNotOk(boolean value) {
            if (value) wsEditPhoneNum2b.setValue("0");
          }

          // COB:                      88 FLG-PHONE-NUM-2B-BLANK     VALUE 'B'.
          public boolean flgPhoneNum2bBlank() {
            return wsEditPhoneNum2b.equals("B");
          }

          public void setFlgPhoneNum2bBlank(boolean value) {
            if (value) wsEditPhoneNum2b.setValue("B");
          }

          // COB:                   30 WS-EDIT-PHONE-NUM-2C          PIC X(01).
          public NChar wsEditPhoneNum2c = new NChar(1);

          // COB:                      88 FLG-PHONE-NUM-2C-ISVALID   VALUE LOW-VALUES.
          public boolean flgPhoneNum2cIsvalid() {
            return wsEditPhoneNum2c.isLowValues();
          }

          public void setFlgPhoneNum2cIsvalid(boolean value) {
            if (value) wsEditPhoneNum2c.lowValues();
          }

          // COB:                      88 FLG-PHONE-NUM-2C-NOT-OK    VALUE '0'.
          public boolean flgPhoneNum2cNotOk() {
            return wsEditPhoneNum2c.equals("0");
          }

          public void setFlgPhoneNum2cNotOk(boolean value) {
            if (value) wsEditPhoneNum2c.setValue("0");
          }

          // COB:                      88 FLG-PHONE-NUM-2C-BLANK     VALUE 'B'.
          public boolean flgPhoneNum2cBlank() {
            return wsEditPhoneNum2c.equals("B");
          }

          public void setFlgPhoneNum2cBlank(boolean value) {
            if (value) wsEditPhoneNum2c.setValue("B");
          }
        }

        public WsEditPhoneNum2Flgs wsEditPhoneNum2Flgs = new WsEditPhoneNum2Flgs();
      }

      public WsEditAddressFlags wsEditAddressFlags = new WsEditAddressFlags();
      // COB:            10  WS-EFT-ACCOUNT-ID-FLGS              PIC X(01).
      public NChar wsEftAccountIdFlgs = new NChar(1);

      // COB:                88 FLG-EFT-ACCOUNT-ID-ISVALID       VALUE LOW-VALUES.
      public boolean flgEftAccountIdIsvalid() {
        return wsEftAccountIdFlgs.isLowValues();
      }

      public void setFlgEftAccountIdIsvalid(boolean value) {
        if (value) wsEftAccountIdFlgs.lowValues();
      }

      // COB:                88 FLG-EFT-ACCOUNT-ID-NOT-OK        VALUE '0'.
      public boolean flgEftAccountIdNotOk() {
        return wsEftAccountIdFlgs.equals("0");
      }

      public void setFlgEftAccountIdNotOk(boolean value) {
        if (value) wsEftAccountIdFlgs.setValue("0");
      }

      // COB:                88 FLG-EFT-ACCOUNT-ID-BLANK         VALUE 'B'.
      public boolean flgEftAccountIdBlank() {
        return wsEftAccountIdFlgs.equals("B");
      }

      public void setFlgEftAccountIdBlank(boolean value) {
        if (value) wsEftAccountIdFlgs.setValue("B");
      }

      // COB:            10  WS-EDIT-PRI-CARDHOLDER              PIC  X(1).
      public NChar wsEditPriCardholder = new NChar(1);

      // COB:                88  FLG-PRI-CARDHOLDER-ISVALID      VALUES 'Y', 'N'.
      public boolean flgPriCardholderIsvalid() {
        return wsEditPriCardholder.equals("Y") || wsEditPriCardholder.equals("N");
      }

      public void setFlgPriCardholderIsvalid(boolean value) {
        if (value) wsEditPriCardholder.setValue("Y");
      }

      // COB:                88  FLG-PRI-CARDHOLDER-NOT-OK       VALUE '0'.
      public boolean flgPriCardholderNotOk() {
        return wsEditPriCardholder.equals("0");
      }

      public void setFlgPriCardholderNotOk(boolean value) {
        if (value) wsEditPriCardholder.setValue("0");
      }

      // COB:                88  FLG-PRI-CARDHOLDER-BLANK        VALUE 'B'.
      public boolean flgPriCardholderBlank() {
        return wsEditPriCardholder.equals("B");
      }

      public void setFlgPriCardholderBlank(boolean value) {
        if (value) wsEditPriCardholder.setValue("B");
      }
    }

    public WsNonKeyFlags wsNonKeyFlags = new WsNonKeyFlags();

    // COB:          05 CICS-OUTPUT-EDIT-VARS.
    public static class CicsOutputEditVars extends NGroup {
      // COB:            10  CUST-ACCT-ID-X                      PIC X(11).
      public NChar custAcctIdX = new NChar(11);
      // COB:            10  CUST-ACCT-ID-N REDEFINES CUST-ACCT-ID-X
      public NZoned custAcctIdN = new NZoned(11, false).redefines(custAcctIdX);
      // COB:            10  WS-EDIT-DATE-X                      PIC X(10).
      public NChar wsEditDateX = new NChar(10);

      // COB:            10  FILLER REDEFINES WS-EDIT-DATE-X.
      public static class Filler363 extends NGroup {
        // COB:                20 WS-EDIT-DATE-X-YEAR              PIC X(4).
        public NChar wsEditDateXYear = new NChar(4);
        // COB:                20 FILLER                           PIC X(1).
        public NChar filler365 = new NChar(1);
        // COB:                20 WS-EDIT-DATE-MONTH               PIC X(2).
        public NChar wsEditDateMonth = new NChar(2);
        // COB:                20 FILLER                           PIC X(1).
        public NChar filler367 = new NChar(1);
        // COB:                20 WS-EDIT-DATE-DAY                 PIC X(2).
        public NChar wsEditDateDay = new NChar(2);
      }

      public Filler363 filler363 = (Filler363) new Filler363().redefines(wsEditDateX);
      // COB:            10  WS-EDIT-DATE-X REDEFINES
      // COB:                WS-EDIT-DATE-X                      PIC 9(10).
      public NZoned wsEditDateX_ = new NZoned(10, false).redefines(wsEditDateX);
      // COB:            10  WS-EDIT-CURRENCY-9-2                PIC X(15).
      public NChar wsEditCurrency9_2 = new NChar(15);
      // COB:            10  WS-EDIT-CURRENCY-9-2-F              PIC +ZZZ,ZZZ,ZZZ.99.
      public NZoned wsEditCurrency9_2F = new NZoned(15).formatAs("+###,###,###.00");
    }

    public CicsOutputEditVars cicsOutputEditVars = new CicsOutputEditVars();

    // COB:          05 WS-XREF-RID.
    public static class WsXrefRid extends NGroup {
      // COB:            10  WS-CARD-RID-CARDNUM                 PIC X(16).
      public NChar wsCardRidCardnum = new NChar(16);
      // COB:            10  WS-CARD-RID-CUST-ID                 PIC 9(09).
      public NZoned wsCardRidCustId = new NZoned(9, false);
      // COB:            10  WS-CARD-RID-CUST-ID-X REDEFINES
      // COB:                   WS-CARD-RID-CUST-ID              PIC X(09).
      public NChar wsCardRidCustIdX = new NChar(9).redefines(wsCardRidCustId);
      // COB:            10  WS-CARD-RID-ACCT-ID                 PIC 9(11).
      public NZoned wsCardRidAcctId = new NZoned(11, false);
      // COB:            10  WS-CARD-RID-ACCT-ID-X REDEFINES
      // COB:                   WS-CARD-RID-ACCT-ID              PIC X(11).
      public NChar wsCardRidAcctIdX = new NChar(11).redefines(wsCardRidAcctId);
    }

    public WsXrefRid wsXrefRid = new WsXrefRid();

    // COB:          05  WS-FILE-READ-FLAGS.
    public static class WsFileReadFlags extends NGroup {
      // COB:            10 WS-ACCOUNT-MASTER-READ-FLAG          PIC X(1).
      public NChar wsAccountMasterReadFlag = new NChar(1);

      // COB:               88 FOUND-ACCT-IN-MASTER              VALUE '1'.
      public boolean foundAcctInMaster() {
        return wsAccountMasterReadFlag.equals("1");
      }

      public void setFoundAcctInMaster(boolean value) {
        if (value) wsAccountMasterReadFlag.setValue("1");
      }

      // COB:            10 WS-CUST-MASTER-READ-FLAG             PIC X(1).
      public NChar wsCustMasterReadFlag = new NChar(1);

      // COB:               88 FOUND-CUST-IN-MASTER              VALUE '1'.
      public boolean foundCustInMaster() {
        return wsCustMasterReadFlag.equals("1");
      }

      public void setFoundCustInMaster(boolean value) {
        if (value) wsCustMasterReadFlag.setValue("1");
      }
    }

    public WsFileReadFlags wsFileReadFlags = new WsFileReadFlags();

    // COB:          05  WS-FILE-ERROR-MESSAGE.
    public static class WsFileErrorMessage extends NGroup {
      // COB:            10  FILLER                         PIC X(12)
      // COB:                                                    VALUE 'File Error: '.
      public NChar filler391 = new NChar(12).initial("File Error: ");
      // COB:            10  ERROR-OPNAME                        PIC X(8)
      // COB:                                                    VALUE SPACES.
      public NChar errorOpname = new NChar(8).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(4)
      // COB:                                                    VALUE ' on '.
      public NChar filler395 = new NChar(4).initial(" on ");
      // COB:            10  ERROR-FILE                          PIC X(9)
      // COB:                                                    VALUE SPACES.
      public NChar errorFile = new NChar(9).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(15)
      // COB:                                                    VALUE
      // COB:                                                    ' returned RESP '.
      public NChar filler399 = new NChar(15).initial(" returned RESP ");
      // COB:            10  ERROR-RESP                          PIC X(10)
      // COB:                                                    VALUE SPACES.
      public NChar errorResp = new NChar(10).initial(NChar.Space);
      // COB:            10  FILLER                              PIC X(7)
      // COB:                                                    VALUE ',RESP2 '.
      public NChar filler404 = new NChar(7).initial(",RESP2 ");
      // COB:            10  ERROR-RESP2                         PIC X(10)
      // COB:                                                    VALUE SPACES.
      public NChar errorResp2 = new NChar(10).initial(NChar.Space);
      // COB:           10  FILLER                               PIC X(5)
      // COB:                                                    VALUE SPACES.
      public NChar filler408 = new NChar(5).initial(NChar.Space);
    }

    public WsFileErrorMessage wsFileErrorMessage = new WsFileErrorMessage();

    // COB:           05 ALPHA-VARS-FOR-DATA-EDITING.
    public static class AlphaVarsForDataEditing extends NGroup {
      // COB:              15 ACUP-NEW-CREDIT-LIMIT-X            PIC X(15).
      public NChar acupNewCreditLimitX = new NChar(15);
      // COB:              15 ACUP-NEW-CASH-CREDIT-LIMIT-X       PIC X(15).
      public NChar acupNewCashCreditLimitX = new NChar(15);
      // COB:              15 ACUP-NEW-CURR-BAL-X                PIC X(15).
      public NChar acupNewCurrBalX = new NChar(15);
      // COB:              15 ACUP-NEW-CURR-CYC-CREDIT-X         PIC X(15).
      public NChar acupNewCurrCycCreditX = new NChar(15);
      // COB:              15 ACUP-NEW-CURR-CYC-DEBIT-X          PIC X(15).
      public NChar acupNewCurrCycDebitX = new NChar(15);
    }

    public AlphaVarsForDataEditing alphaVarsForDataEditing = new AlphaVarsForDataEditing();

    // COB:           05 ACCT-UPDATE-RECORD.
    public static class AcctUpdateRecord extends NGroup {
      // COB:                15  ACCT-UPDATE-ID                      PIC 9(11).
      public NZoned acctUpdateId = new NZoned(11, false);
      // COB:                15  ACCT-UPDATE-ACTIVE-STATUS           PIC X(01).
      public NChar acctUpdateActiveStatus = new NChar(1);
      // COB:                15  ACCT-UPDATE-CURR-BAL                PIC S9(10)V99.
      public NZoned acctUpdateCurrBal = new NZoned(12, 2);
      // COB:                15  ACCT-UPDATE-CREDIT-LIMIT            PIC S9(10)V99.
      public NZoned acctUpdateCreditLimit = new NZoned(12, 2);
      // COB:                15  ACCT-UPDATE-CASH-CREDIT-LIMIT       PIC S9(10)V99.
      public NZoned acctUpdateCashCreditLimit = new NZoned(12, 2);
      // COB:                15  ACCT-UPDATE-OPEN-DATE               PIC X(10).
      public NChar acctUpdateOpenDate = new NChar(10);
      // COB:                15  ACCT-UPDATE-EXPIRAION-DATE          PIC X(10).
      public NChar acctUpdateExpiraionDate = new NChar(10);
      // COB:                15  ACCT-UPDATE-REISSUE-DATE            PIC X(10).
      public NChar acctUpdateReissueDate = new NChar(10);
      // COB:                15  ACCT-UPDATE-CURR-CYC-CREDIT         PIC S9(10)V99.
      public NZoned acctUpdateCurrCycCredit = new NZoned(12, 2);
      // COB:                15  ACCT-UPDATE-CURR-CYC-DEBIT          PIC S9(10)V99.
      public NZoned acctUpdateCurrCycDebit = new NZoned(12, 2);
      // COB:                15  ACCT-UPDATE-GROUP-ID                PIC X(10).
      public NChar acctUpdateGroupId = new NChar(10);
      // COB:                15  FILLER                              PIC X(188).
      public NChar filler434 = new NChar(188);
    }

    public AcctUpdateRecord acctUpdateRecord = new AcctUpdateRecord();

    // COB:           05 CUST-UPDATE-RECORD.
    public static class CustUpdateRecord extends NGroup {
      // COB:                15  CUST-UPDATE-ID                      PIC 9(09).
      public NZoned custUpdateId = new NZoned(9, false);
      // COB:                15  CUST-UPDATE-FIRST-NAME              PIC X(25).
      public NChar custUpdateFirstName = new NChar(25);
      // COB:                15  CUST-UPDATE-MIDDLE-NAME             PIC X(25).
      public NChar custUpdateMiddleName = new NChar(25);
      // COB:                15  CUST-UPDATE-LAST-NAME               PIC X(25).
      public NChar custUpdateLastName = new NChar(25);
      // COB:                15  CUST-UPDATE-ADDR-LINE-1             PIC X(50).
      public NChar custUpdateAddrLine1 = new NChar(50);
      // COB:                15  CUST-UPDATE-ADDR-LINE-2             PIC X(50).
      public NChar custUpdateAddrLine2 = new NChar(50);
      // COB:                15  CUST-UPDATE-ADDR-LINE-3             PIC X(50).
      public NChar custUpdateAddrLine3 = new NChar(50);
      // COB:                15  CUST-UPDATE-ADDR-STATE-CD           PIC X(02).
      public NChar custUpdateAddrStateCd = new NChar(2);
      // COB:                15  CUST-UPDATE-ADDR-COUNTRY-CD         PIC X(03).
      public NChar custUpdateAddrCountryCd = new NChar(3);
      // COB:                15  CUST-UPDATE-ADDR-ZIP                PIC X(10).
      public NChar custUpdateAddrZip = new NChar(10);
      // COB:                15  CUST-UPDATE-PHONE-NUM-1             PIC X(15).
      public NChar custUpdatePhoneNum1 = new NChar(15);
      // COB:                15  CUST-UPDATE-PHONE-NUM-2             PIC X(15).
      public NChar custUpdatePhoneNum2 = new NChar(15);
      // COB:                15  CUST-UPDATE-SSN                     PIC 9(09).
      public NZoned custUpdateSsn = new NZoned(9, false);
      // COB:                15  CUST-UPDATE-GOVT-ISSUED-ID          PIC X(20).
      public NChar custUpdateGovtIssuedId = new NChar(20);
      // COB:                15  CUST-UPDATE-DOB-YYYY-MM-DD          PIC X(10).
      public NChar custUpdateDobYyyyMmDd = new NChar(10);
      // COB:                15  CUST-UPDATE-EFT-ACCOUNT-ID          PIC X(10).
      public NChar custUpdateEftAccountId = new NChar(10);
      // COB:                15  CUST-UPDATE-PRI-CARD-IND            PIC X(01).
      public NChar custUpdatePriCardInd = new NChar(1);
      // COB:                15  CUST-UPDATE-FICO-CREDIT-SCORE       PIC 9(03).
      public NZoned custUpdateFicoCreditScore = new NZoned(3, false);
      // COB:                15  FILLER                              PIC X(168).
      public NChar filler457 = new NChar(168);
    }

    public CustUpdateRecord custUpdateRecord = new CustUpdateRecord();
    // COB:          05  WS-LONG-MSG                           PIC X(500).
    public NChar wsLongMsg = new NChar(500);
    // COB:          05  WS-INFO-MSG                           PIC X(40).
    public NChar wsInfoMsg = new NChar(40);

    // COB:            88  WS-NO-INFO-MESSAGE                 VALUES
    public boolean wsNoInfoMessage() {
      return wsInfoMsg.isSpaces() || wsInfoMsg.isLowValues();
    }

    public void setWsNoInfoMessage(boolean value) {
      if (value) wsInfoMsg.setValue(SPACES);
    }

    // COB:            88  FOUND-ACCOUNT-DATA             VALUE
    public boolean foundAccountData() {
      return wsInfoMsg.equals("Details of selected account shown above");
    }

    public void setFoundAccountData(boolean value) {
      if (value) wsInfoMsg.setValue("Details of selected account shown above");
    }

    // COB:            88  PROMPT-FOR-SEARCH-KEYS              VALUE
    public boolean promptForSearchKeys() {
      return wsInfoMsg.equals("Enter or update id of account to update");
    }

    public void setPromptForSearchKeys(boolean value) {
      if (value) wsInfoMsg.setValue("Enter or update id of account to update");
    }

    // COB:            88  PROMPT-FOR-CHANGES                  VALUE
    public boolean promptForChanges() {
      return wsInfoMsg.equals("Update account details presented above.");
    }

    public void setPromptForChanges(boolean value) {
      if (value) wsInfoMsg.setValue("Update account details presented above.");
    }

    // COB:            88  PROMPT-FOR-CONFIRMATION             VALUE
    public boolean promptForConfirmation() {
      return wsInfoMsg.equals("Changes validated.Press F5 to save");
    }

    public void setPromptForConfirmation(boolean value) {
      if (value) wsInfoMsg.setValue("Changes validated.Press F5 to save");
    }

    // COB:            88  CONFIRM-UPDATE-SUCCESS              VALUE
    public boolean confirmUpdateSuccess() {
      return wsInfoMsg.equals("Changes committed to database");
    }

    public void setConfirmUpdateSuccess(boolean value) {
      if (value) wsInfoMsg.setValue("Changes committed to database");
    }

    // COB:            88  INFORM-FAILURE                      VALUE
    public boolean informFailure() {
      return wsInfoMsg.equals("Changes unsuccessful. Please try again");
    }

    public void setInformFailure(boolean value) {
      if (value) wsInfoMsg.setValue("Changes unsuccessful. Please try again");
    }

    // COB:          05  WS-RETURN-MSG                         PIC X(75).
    public NChar wsReturnMsg = new NChar(75);

    // COB:            88  WS-RETURN-MSG-OFF                   VALUE SPACES.
    public boolean wsReturnMsgOff() {
      return wsReturnMsg.isSpaces();
    }

    public void setWsReturnMsgOff(boolean value) {
      if (value) wsReturnMsg.setValue(SPACES);
    }

    // COB:            88  WS-EXIT-MESSAGE                     VALUE
    public boolean wsExitMessage() {
      return wsReturnMsg.equals("PF03 pressed.Exiting              ");
    }

    public void setWsExitMessage(boolean value) {
      if (value) wsReturnMsg.setValue("PF03 pressed.Exiting              ");
    }

    // COB:            88  WS-PROMPT-FOR-ACCT                  VALUE
    public boolean wsPromptForAcct() {
      return wsReturnMsg.equals("Account number not provided");
    }

    public void setWsPromptForAcct(boolean value) {
      if (value) wsReturnMsg.setValue("Account number not provided");
    }

    // COB:            88  WS-PROMPT-FOR-LASTNAME              VALUE
    public boolean wsPromptForLastname() {
      return wsReturnMsg.equals("Last name not provided");
    }

    public void setWsPromptForLastname(boolean value) {
      if (value) wsReturnMsg.setValue("Last name not provided");
    }

    // COB:            88  WS-NAME-MUST-BE-ALPHA               VALUE
    public boolean wsNameMustBeAlpha() {
      return wsReturnMsg.equals("Name can only contain alphabets and spaces");
    }

    public void setWsNameMustBeAlpha(boolean value) {
      if (value) wsReturnMsg.setValue("Name can only contain alphabets and spaces");
    }

    // COB:            88  NO-SEARCH-CRITERIA-RECEIVED         VALUE
    public boolean noSearchCriteriaReceived() {
      return wsReturnMsg.equals("No input received");
    }

    public void setNoSearchCriteriaReceived(boolean value) {
      if (value) wsReturnMsg.setValue("No input received");
    }

    // COB:            88  NO-CHANGES-DETECTED                 VALUE
    public boolean noChangesDetected() {
      return wsReturnMsg.equals("No change detected with respect to values fetched.");
    }

    public void setNoChangesDetected(boolean value) {
      if (value) wsReturnMsg.setValue("No change detected with respect to values fetched.");
    }

    // COB:            88  SEARCHED-ACCT-ZEROES                VALUE
    public boolean searchedAcctZeroes() {
      return wsReturnMsg.equals("Account number must be a non zero 11 digit number");
    }

    public void setSearchedAcctZeroes(boolean value) {
      if (value) wsReturnMsg.setValue("Account number must be a non zero 11 digit number");
    }

    // COB:            88  SEARCHED-ACCT-NOT-NUMERIC           VALUE
    public boolean searchedAcctNotNumeric() {
      return wsReturnMsg.equals("Account number must be a non zero 11 digit number");
    }

    public void setSearchedAcctNotNumeric(boolean value) {
      if (value) wsReturnMsg.setValue("Account number must be a non zero 11 digit number");
    }

    // COB:            88  DID-NOT-FIND-ACCT-IN-CARDXREF       VALUE
    public boolean wsReturnMsgDidNotFindAcctInCardxref() {
      return wsReturnMsg.equals("Did not find this account in account card xref file");
    }

    public void setWsReturnMsgDidNotFindAcctInCardxref(boolean value) {
      if (value) wsReturnMsg.setValue("Did not find this account in account card xref file");
    }

    // COB:            88  DID-NOT-FIND-ACCT-IN-ACCTDAT        VALUE
    public boolean didNotFindAcctInAcctdat() {
      return wsReturnMsg.equals("Did not find this account in account master file");
    }

    public void setDidNotFindAcctInAcctdat(boolean value) {
      if (value) wsReturnMsg.setValue("Did not find this account in account master file");
    }

    // COB:            88  DID-NOT-FIND-CUST-IN-CUSTDAT        VALUE
    public boolean didNotFindCustInCustdat() {
      return wsReturnMsg.equals("Did not find associated customer in master file");
    }

    public void setDidNotFindCustInCustdat(boolean value) {
      if (value) wsReturnMsg.setValue("Did not find associated customer in master file");
    }

    // COB:            88  ACCT-STATUS-MUST-BE-YES-NO          VALUE
    public boolean acctStatusMustBeYesNo() {
      return wsReturnMsg.equals("Account Active Status must be Y or N");
    }

    public void setAcctStatusMustBeYesNo(boolean value) {
      if (value) wsReturnMsg.setValue("Account Active Status must be Y or N");
    }

    // COB:            88  CRED-LIMIT-IS-BLANK                 VALUE
    public boolean credLimitIsBlank() {
      return wsReturnMsg.equals("Credit Limit must be supplied");
    }

    public void setCredLimitIsBlank(boolean value) {
      if (value) wsReturnMsg.setValue("Credit Limit must be supplied");
    }

    // COB:            88  CRED-LIMIT-IS-NOT-VALID             VALUE
    public boolean credLimitIsNotValid() {
      return wsReturnMsg.equals("Credit Limit is not valid");
    }

    public void setCredLimitIsNotValid(boolean value) {
      if (value) wsReturnMsg.setValue("Credit Limit is not valid");
    }

    // COB:            88  THIS-MONTH-NOT-VALID                VALUE
    public boolean thisMonthNotValid() {
      return wsReturnMsg.equals("Card expiry month must be between 1 and 12");
    }

    public void setThisMonthNotValid(boolean value) {
      if (value) wsReturnMsg.setValue("Card expiry month must be between 1 and 12");
    }

    // COB:            88  THIS-YEAR-NOT-VALID                 VALUE
    public boolean thisYearNotValid() {
      return wsReturnMsg.equals("Invalid card expiry year");
    }

    public void setThisYearNotValid(boolean value) {
      if (value) wsReturnMsg.setValue("Invalid card expiry year");
    }

    // COB:            88  DID-NOT-FIND-ACCT-IN-CARDXREF       VALUE
    public boolean didNotFindAcctInCardxref_() {
      return wsReturnMsg.equals("Did not find this account in cards database");
    }

    public void setDidNotFindAcctInCardxref_(boolean value) {
      if (value) wsReturnMsg.setValue("Did not find this account in cards database");
    }

    // COB:            88  DID-NOT-FIND-ACCTCARD-COMBO         VALUE
    public boolean didNotFindAcctcardCombo() {
      return wsReturnMsg.equals("Did not find cards for this search condition");
    }

    public void setDidNotFindAcctcardCombo(boolean value) {
      if (value) wsReturnMsg.setValue("Did not find cards for this search condition");
    }

    // COB:            88  COULD-NOT-LOCK-ACCT-FOR-UPDATE      VALUE
    public boolean couldNotLockAcctForUpdate() {
      return wsReturnMsg.equals("Could not lock account record for update");
    }

    public void setCouldNotLockAcctForUpdate(boolean value) {
      if (value) wsReturnMsg.setValue("Could not lock account record for update");
    }

    // COB:            88  COULD-NOT-LOCK-CUST-FOR-UPDATE      VALUE
    public boolean couldNotLockCustForUpdate() {
      return wsReturnMsg.equals("Could not lock customer record for update");
    }

    public void setCouldNotLockCustForUpdate(boolean value) {
      if (value) wsReturnMsg.setValue("Could not lock customer record for update");
    }

    // COB:            88  DATA-WAS-CHANGED-BEFORE-UPDATE      VALUE
    public boolean dataWasChangedBeforeUpdate() {
      return wsReturnMsg.equals("Record changed by some one else. Please review");
    }

    public void setDataWasChangedBeforeUpdate(boolean value) {
      if (value) wsReturnMsg.setValue("Record changed by some one else. Please review");
    }

    // COB:            88  LOCKED-BUT-UPDATE-FAILED            VALUE
    public boolean lockedButUpdateFailed() {
      return wsReturnMsg.equals("Update of record failed");
    }

    public void setLockedButUpdateFailed(boolean value) {
      if (value) wsReturnMsg.setValue("Update of record failed");
    }

    // COB:            88  XREF-READ-ERROR                     VALUE
    public boolean xrefReadError() {
      return wsReturnMsg.equals("Error reading Card Data File");
    }

    public void setXrefReadError(boolean value) {
      if (value) wsReturnMsg.setValue("Error reading Card Data File");
    }

    // COB:            88  CODING-TO-BE-DONE                   VALUE
    public boolean codingToBeDone() {
      return wsReturnMsg.equals("Looks Good.... so far");
    }

    public void setCodingToBeDone(boolean value) {
      if (value) wsReturnMsg.setValue("Looks Good.... so far");
    }
  }

  // COB:        01 WS-LITERALS.
  public WsLiterals wsLiterals = new WsLiterals();

  public static class WsLiterals extends NGroup {

    // COB:           05 LIT-THISPGM                           PIC X(8)
    // COB:                                                    VALUE 'COACTUPC'.
    public NChar litThispgm = new NChar(8).initial("COACTUPC");
    // COB:           05 LIT-THISTRANID                        PIC X(4)
    // COB:                                                    VALUE 'CAUP'.
    public NChar litThistranid = new NChar(4).initial("CAUP");
    // COB:           05 LIT-THISMAPSET                        PIC X(8)
    // COB:                                                    VALUE 'COACTUP '.
    public NChar litThismapset = new NChar(8).initial("COACTUP ");
    // COB:           05 LIT-THISMAP                           PIC X(7)
    // COB:                                                    VALUE 'CACTUPA'.
    public NChar litThismap = new NChar(7).initial("CACTUPA");
    // COB:           05 LIT-CARDUPDATE-PGM                    PIC X(8)
    // COB:                                                    VALUE 'COCRDUPC'.
    public NChar litCardupdatePgm = new NChar(8).initial("COCRDUPC");
    // COB:           05 LIT-CARDUPDATE-TRANID                 PIC X(4)
    // COB:                                                    VALUE 'CCUP'.
    public NChar litCardupdateTranid = new NChar(4).initial("CCUP");
    // COB:           05 LIT-CARDUPDATE-MAPSET                 PIC X(8)
    // COB:                                                    VALUE 'COCRDUP '.
    public NChar litCardupdateMapset = new NChar(8).initial("COCRDUP ");
    // COB:           05 LIT-CARDUPDATE-MAP                    PIC X(7)
    // COB:                                                    VALUE 'CCRDUPA'.
    public NChar litCardupdateMap = new NChar(7).initial("CCRDUPA");
    // COB:           05 LIT-CCLISTPGM                         PIC X(8)
    // COB:                                                    VALUE 'COCRDLIC'.
    public NChar litCclistpgm = new NChar(8).initial("COCRDLIC");
    // COB:           05 LIT-CCLISTTRANID                      PIC X(4)
    // COB:                                                    VALUE 'CCLI'.
    public NChar litCclisttranid = new NChar(4).initial("CCLI");
    // COB:           05 LIT-CCLISTMAPSET                      PIC X(7)
    // COB:                                                    VALUE 'COCRDLI'.
    public NChar litCclistmapset = new NChar(7).initial("COCRDLI");
    // COB:           05 LIT-CCLISTMAP                         PIC X(7)
    // COB:                                                    VALUE 'CCRDSLA'.
    public NChar litCclistmap = new NChar(7).initial("CCRDSLA");
    // COB:           05 LIT-MENUPGM                           PIC X(8)
    // COB:                                                    VALUE 'COMEN01C'.
    public NChar litMenupgm = new NChar(8).initial("COMEN01C");
    // COB:           05 LIT-MENUTRANID                        PIC X(4)
    // COB:                                                    VALUE 'CM00'.
    public NChar litMenutranid = new NChar(4).initial("CM00");
    // COB:           05 LIT-MENUMAPSET                        PIC X(7)
    // COB:                                                    VALUE 'COMEN01'.
    public NChar litMenumapset = new NChar(7).initial("COMEN01");
    // COB:           05 LIT-MENUMAP                           PIC X(7)
    // COB:                                                    VALUE 'COMEN1A'.
    public NChar litMenumap = new NChar(7).initial("COMEN1A");
    // COB:           05 LIT-CARDDTLPGM                        PIC X(8)
    // COB:                                                    VALUE 'COCRDSLC'.
    public NChar litCarddtlpgm = new NChar(8).initial("COCRDSLC");
    // COB:           05 LIT-CARDDTLTRANID                     PIC X(4)
    // COB:                                                    VALUE 'CCDL'.
    public NChar litCarddtltranid = new NChar(4).initial("CCDL");
    // COB:           05 LIT-CARDDTLMAPSET                     PIC X(7)
    // COB:                                                    VALUE 'COCRDSL'.
    public NChar litCarddtlmapset = new NChar(7).initial("COCRDSL");
    // COB:           05 LIT-CARDDTLMAP                        PIC X(7)
    // COB:                                                    VALUE 'CCRDSLA'.
    public NChar litCarddtlmap = new NChar(7).initial("CCRDSLA");
    // COB:           05 LIT-ACCTFILENAME                      PIC X(8)
    // COB:                                                    VALUE 'ACCTDAT '.
    public NChar litAcctfilename = new NChar(8).initial("ACCTDAT ");
    // COB:           05 LIT-CUSTFILENAME                      PIC X(8)
    // COB:                                                    VALUE 'CUSTDAT '.
    public NChar litCustfilename = new NChar(8).initial("CUSTDAT ");
    // COB:           05 LIT-CARDFILENAME                      PIC X(8)
    // COB:                                                    VALUE 'CARDDAT '.
    public NChar litCardfilename = new NChar(8).initial("CARDDAT ");
    // COB:           05 LIT-CARDFILENAME-ACCT-PATH            PIC X(8)
    // COB:                                                    VALUE 'CARDAIX '.
    public NChar litCardfilenameAcctPath = new NChar(8).initial("CARDAIX ");
    // COB:           05 LIT-CARDXREFNAME-ACCT-PATH            PIC X(8)
    // COB:                                                    VALUE 'CXACAIX '.
    public NChar litCardxrefnameAcctPath = new NChar(8).initial("CXACAIX ");

    // COB:           05 LIT-ALL-ALPHANUM-FROM-X.
    public static class LitAllAlphanumFromX extends NGroup {
      // COB:              10 LIT-ALL-ALPHA-FROM-X.
      public static class LitAllAlphaFromX extends NGroup {
        // COB:                 15 LIT-UPPER                       PIC X(26)
        // COB:                                  VALUE 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.
        public NChar litUpper = new NChar(26).initial("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        // COB:                 15 LIT-LOWER                       PIC X(26)
        // COB:                                  VALUE 'abcdefghijklmnopqrstuvwxyz'.
        public NChar litLower = new NChar(26).initial("abcdefghijklmnopqrstuvwxyz");
      }

      public LitAllAlphaFromX litAllAlphaFromX = new LitAllAlphaFromX();
      // COB:              10 LIT-NUMBERS                        PIC X(10)
      // COB:                                  VALUE '0123456789'.
      public NChar litNumbers = new NChar(10).initial("0123456789");
    }

    public LitAllAlphanumFromX litAllAlphanumFromX = new LitAllAlphanumFromX();
  }

  public Cvcrd01y cvcrd01y = new Cvcrd01y();
  public Cslkpcdy cslkpcdy = new Cslkpcdy();
  // COB:        01  LIT-ALL-ALPHA-FROM     PIC X(52) VALUE SPACES.
  public NChar litAllAlphaFrom = new NChar(52).initial(NChar.Space);
  // COB:        01  LIT-ALL-ALPHANUM-FROM  PIC X(62) VALUE SPACES.
  public NChar litAllAlphanumFrom = new NChar(62).initial(NChar.Space);
  // COB:        01  LIT-ALL-NUM-FROM       PIC X(10) VALUE SPACES.
  public NChar litAllNumFrom = new NChar(10).initial(NChar.Space);
  // COB:        77  LIT-ALPHA-SPACES-TO    PIC X(52) VALUE SPACES.
  public NChar litAlphaSpacesTo = new NChar(52).initial(NChar.Space);
  // COB:        77  LIT-ALPHANUM-SPACES-TO PIC X(62) VALUE SPACES.
  public NChar litAlphanumSpacesTo = new NChar(62).initial(NChar.Space);
  // COB:        77  LIT-NUM-SPACES-TO      PIC X(10) VALUE SPACES.
  public NChar litNumSpacesTo = new NChar(10).initial(NChar.Space);
  public Cottl01y cottl01y = new Cottl01y();
  public Coactup coactup = new Coactup();
  public Csdat01y csdat01y = new Csdat01y();
  public Csmsg01y csmsg01y = new Csmsg01y();
  public Csmsg02y csmsg02y = new Csmsg02y();
  public Csusr01y csusr01y = new Csusr01y();
  public Cvact01y cvact01y = new Cvact01y();
  public Cvact03y cvact03y = new Cvact03y();
  public Cvcus01y cvcus01y = new Cvcus01y();
  public Cocom01y cocom01y = new Cocom01y();
  // COB:        01 WS-THIS-PROGCOMMAREA.
  public WsThisProgcommarea wsThisProgcommarea = new WsThisProgcommarea();

  public static class WsThisProgcommarea extends NGroup {

    // COB:           05 ACCT-UPDATE-SCREEN-DATA.
    public static class AcctUpdateScreenData extends NGroup {
      // COB:              10 ACUP-CHANGE-ACTION                     PIC X(1)
      // COB:                                                        VALUE LOW-VALUES.
      public NChar acupChangeAction = new NChar(1).initial(NChar.LowValue);

      // COB:                 88 ACUP-DETAILS-NOT-FETCHED            VALUES
      public boolean acupDetailsNotFetched() {
        return acupChangeAction.isLowValues() || acupChangeAction.isSpaces();
      }

      public void setAcupDetailsNotFetched(boolean value) {
        if (value) acupChangeAction.lowValues();
      }

      // COB:                 88 ACUP-SHOW-DETAILS                   VALUE 'S'.
      public boolean acupShowDetails() {
        return acupChangeAction.equals("S");
      }

      public void setAcupShowDetails(boolean value) {
        if (value) acupChangeAction.setValue("S");
      }

      // COB:                 88 ACUP-CHANGES-MADE                   VALUES 'E', 'N'
      public boolean acupChangesMade() {
        return acupChangeAction.equals("E")
            || acupChangeAction.equals("N")
            || acupChangeAction.equals("C")
            || acupChangeAction.equals("L")
            || acupChangeAction.equals("F");
      }

      public void setAcupChangesMade(boolean value) {
        if (value) acupChangeAction.setValue("E");
      }

      // COB:                 88 ACUP-CHANGES-NOT-OK                 VALUE 'E'.
      public boolean acupChangesNotOk() {
        return acupChangeAction.equals("E");
      }

      public void setAcupChangesNotOk(boolean value) {
        if (value) acupChangeAction.setValue("E");
      }

      // COB:                 88 ACUP-CHANGES-OK-NOT-CONFIRMED       VALUE 'N'.
      public boolean acupChangesOkNotConfirmed() {
        return acupChangeAction.equals("N");
      }

      public void setAcupChangesOkNotConfirmed(boolean value) {
        if (value) acupChangeAction.setValue("N");
      }

      // COB:                 88 ACUP-CHANGES-OKAYED-AND-DONE        VALUE 'C'.
      public boolean acupChangesOkayedAndDone() {
        return acupChangeAction.equals("C");
      }

      public void setAcupChangesOkayedAndDone(boolean value) {
        if (value) acupChangeAction.setValue("C");
      }

      // COB:                 88 ACUP-CHANGES-FAILED                 VALUES 'L', 'F'.
      public boolean acupChangesFailed() {
        return acupChangeAction.equals("L") || acupChangeAction.equals("F");
      }

      public void setAcupChangesFailed(boolean value) {
        if (value) acupChangeAction.setValue("L");
      }

      // COB:                 88 ACUP-CHANGES-OKAYED-LOCK-ERROR      VALUE 'L'.
      public boolean acupChangesOkayedLockError() {
        return acupChangeAction.equals("L");
      }

      public void setAcupChangesOkayedLockError(boolean value) {
        if (value) acupChangeAction.setValue("L");
      }

      // COB:                 88 ACUP-CHANGES-OKAYED-BUT-FAILED      VALUE 'F'.
      public boolean acupChangesOkayedButFailed() {
        return acupChangeAction.equals("F");
      }

      public void setAcupChangesOkayedButFailed(boolean value) {
        if (value) acupChangeAction.setValue("F");
      }
    }

    public AcctUpdateScreenData acctUpdateScreenData = new AcctUpdateScreenData();

    // COB:           05 ACUP-OLD-DETAILS.
    public static class AcupOldDetails extends NGroup {
      // COB:              10 ACUP-OLD-ACCT-DATA.
      public static class AcupOldAcctData extends NGroup {
        // COB:                 15  ACUP-OLD-ACCT-ID-X                 PIC X(11).
        public NChar acupOldAcctIdX = new NChar(11);
        // COB:                 15  ACUP-OLD-ACCT-ID                   REDEFINES
        // COB:                     ACUP-OLD-ACCT-ID-X                 PIC 9(11).
        public NZoned acupOldAcctId = new NZoned(11, false).redefines(acupOldAcctIdX);
        // COB:                 15  ACUP-OLD-ACTIVE-STATUS             PIC X(01).
        public NChar acupOldActiveStatus = new NChar(1);
        // COB:                 15  ACUP-OLD-CURR-BAL                  PIC X(12).
        public NChar acupOldCurrBal = new NChar(12);
        // COB:                 15  ACUP-OLD-CURR-BAL-N REDEFINES
        // COB:                     ACUP-OLD-CURR-BAL                  PIC S9(10)V99.
        public NZoned acupOldCurrBalN = new NZoned(12, 2).redefines(acupOldCurrBal);
        // COB:                 15  ACUP-OLD-CREDIT-LIMIT              PIC X(12).
        public NChar acupOldCreditLimit = new NChar(12);
        // COB:                 15  ACUP-OLD-CREDIT-LIMIT-N            REDEFINES
        // COB:                     ACUP-OLD-CREDIT-LIMIT              PIC S9(10)V99.
        public NZoned acupOldCreditLimitN = new NZoned(12, 2).redefines(acupOldCreditLimit);
        // COB:                 15  ACUP-OLD-CASH-CREDIT-LIMIT         PIC X(12).
        public NChar acupOldCashCreditLimit = new NChar(12);
        // COB:                 15  ACUP-OLD-CASH-CREDIT-LIMIT-N       REDEFINES
        // COB:                     ACUP-OLD-CASH-CREDIT-LIMIT         PIC S9(10)V99.
        public NZoned acupOldCashCreditLimitN = new NZoned(12, 2).redefines(acupOldCashCreditLimit);
        // COB:                 15  ACUP-OLD-OPEN-DATE                 PIC X(08).
        public NChar acupOldOpenDate = new NChar(8);

        // COB:                 15  ACUP-OLD-OPEN-DATE-PARTS           REDEFINES
        // COB:                     ACUP-OLD-OPEN-DATE.
        public static class AcupOldOpenDateParts extends NGroup {
          // COB:                     20 ACUP-OLD-OPEN-YEAR              PIC X(4).
          public NChar acupOldOpenYear = new NChar(4);
          // COB:                     20 ACUP-OLD-OPEN-MON               PIC X(2).
          public NChar acupOldOpenMon = new NChar(2);
          // COB:                     20 ACUP-OLD-OPEN-DAY               PIC X(2).
          public NChar acupOldOpenDay = new NChar(2);
        }

        public AcupOldOpenDateParts acupOldOpenDateParts =
            (AcupOldOpenDateParts) new AcupOldOpenDateParts().redefines(acupOldOpenDate);
        // COB:                 15  ACUP-OLD-EXPIRAION-DATE            PIC X(08).
        public NChar acupOldExpiraionDate = new NChar(8);

        // COB:                 15  ACUP-OLD-EXPIRAION-DATE-PARTS      REDEFINES
        // COB:                     ACUP-OLD-EXPIRAION-DATE.
        public static class AcupOldExpiraionDateParts extends NGroup {
          // COB:                     20 ACUP-OLD-EXP-YEAR                PIC X(4).
          public NChar acupOldExpYear = new NChar(4);
          // COB:                     20 ACUP-OLD-EXP-MON                 PIC X(2).
          public NChar acupOldExpMon = new NChar(2);
          // COB:                     20 ACUP-OLD-EXP-DAY                 PIC X(2).
          public NChar acupOldExpDay = new NChar(2);
        }

        public AcupOldExpiraionDateParts acupOldExpiraionDateParts =
            (AcupOldExpiraionDateParts)
                new AcupOldExpiraionDateParts().redefines(acupOldExpiraionDate);
        // COB:                 15  ACUP-OLD-REISSUE-DATE              PIC X(08).
        public NChar acupOldReissueDate = new NChar(8);

        // COB:                 15  ACUP-OLD-REISSUE-DATE-PARTS        REDEFINES
        // COB:                     ACUP-OLD-REISSUE-DATE.
        public static class AcupOldReissueDateParts extends NGroup {
          // COB:                     20 ACUP-OLD-REISSUE-YEAR           PIC X(4).
          public NChar acupOldReissueYear = new NChar(4);
          // COB:                     20 ACUP-OLD-REISSUE-MON            PIC X(2).
          public NChar acupOldReissueMon = new NChar(2);
          // COB:                     20 ACUP-OLD-REISSUE-DAY            PIC X(2).
          public NChar acupOldReissueDay = new NChar(2);
        }

        public AcupOldReissueDateParts acupOldReissueDateParts =
            (AcupOldReissueDateParts) new AcupOldReissueDateParts().redefines(acupOldReissueDate);
        // COB:                 15  ACUP-OLD-CURR-CYC-CREDIT           PIC X(12).
        public NChar acupOldCurrCycCredit = new NChar(12);
        // COB:                 15  ACUP-OLD-CURR-CYC-CREDIT-N         REDEFINES
        // COB:                     ACUP-OLD-CURR-CYC-CREDIT           PIC S9(10)V99.
        public NZoned acupOldCurrCycCreditN = new NZoned(12, 2).redefines(acupOldCurrCycCredit);
        // COB:                 15  ACUP-OLD-CURR-CYC-DEBIT            PIC X(12).
        public NChar acupOldCurrCycDebit = new NChar(12);
        // COB:                 15  ACUP-OLD-CURR-CYC-DEBIT-N          REDEFINES
        // COB:                     ACUP-OLD-CURR-CYC-DEBIT            PIC S9(10)V99.
        public NZoned acupOldCurrCycDebitN = new NZoned(12, 2).redefines(acupOldCurrCycDebit);
        // COB:                 15  ACUP-OLD-GROUP-ID                  PIC X(10).
        public NChar acupOldGroupId = new NChar(10);
      }

      public AcupOldAcctData acupOldAcctData = new AcupOldAcctData();

      // COB:              10 ACUP-OLD-CUST-DATA.
      public static class AcupOldCustData extends NGroup {
        // COB:                 15  ACUP-OLD-CUST-ID-X                 PIC X(09).
        public NChar acupOldCustIdX = new NChar(9);
        // COB:                 15  ACUP-OLD-CUST-ID                   REDEFINES
        // COB:                     ACUP-OLD-CUST-ID-X                 PIC 9(09).
        public NZoned acupOldCustId = new NZoned(9, false).redefines(acupOldCustIdX);
        // COB:                 15  ACUP-OLD-CUST-FIRST-NAME           PIC X(25).
        public NChar acupOldCustFirstName = new NChar(25);
        // COB:                 15  ACUP-OLD-CUST-MIDDLE-NAME          PIC X(25).
        public NChar acupOldCustMiddleName = new NChar(25);
        // COB:                 15  ACUP-OLD-CUST-LAST-NAME            PIC X(25).
        public NChar acupOldCustLastName = new NChar(25);
        // COB:                 15  ACUP-OLD-CUST-ADDR-LINE-1          PIC X(50).
        public NChar acupOldCustAddrLine1 = new NChar(50);
        // COB:                 15  ACUP-OLD-CUST-ADDR-LINE-2          PIC X(50).
        public NChar acupOldCustAddrLine2 = new NChar(50);
        // COB:                 15  ACUP-OLD-CUST-ADDR-LINE-3          PIC X(50).
        public NChar acupOldCustAddrLine3 = new NChar(50);
        // COB:                 15  ACUP-OLD-CUST-ADDR-STATE-CD        PIC X(02).
        public NChar acupOldCustAddrStateCd = new NChar(2);
        // COB:                 15  ACUP-OLD-CUST-ADDR-COUNTRY-CD      PIC X(03).
        public NChar acupOldCustAddrCountryCd = new NChar(3);
        // COB:                 15  ACUP-OLD-CUST-ADDR-ZIP             PIC X(10).
        public NChar acupOldCustAddrZip = new NChar(10);
        // COB:                 15  ACUP-OLD-CUST-PHONE-NUM-1          PIC X(15).
        public NChar acupOldCustPhoneNum1 = new NChar(15);

        // COB:                 15  ACUP-OLD-CUST-PHONE-NUM-1-X REDEFINES
        // COB:                     ACUP-OLD-CUST-PHONE-NUM-1.
        public static class AcupOldCustPhoneNum1X extends NGroup {
          // COB:                     20 FILLER                          PIC X(1).
          public NChar filler726 = new NChar(1);
          // COB:                     20 ACUP-OLD-CUST-PHONE-NUM-1A      PIC X(3).
          public NChar acupOldCustPhoneNum1a = new NChar(3);
          // COB:                     20 FILLER                          PIC X(1).
          public NChar filler728 = new NChar(1);
          // COB:                     20 ACUP-OLD-CUST-PHONE-NUM-1B      PIC X(3).
          public NChar acupOldCustPhoneNum1b = new NChar(3);
          // COB:                     20 FILLER                          PIC X(1).
          public NChar filler730 = new NChar(1);
          // COB:                     20 ACUP-OLD-CUST-PHONE-NUM-1C      PIC X(4).
          public NChar acupOldCustPhoneNum1c = new NChar(4);
          // COB:                     20 FILLER                          PIC X(2).
          public NChar filler732 = new NChar(2);
        }

        public AcupOldCustPhoneNum1X acupOldCustPhoneNum1X =
            (AcupOldCustPhoneNum1X) new AcupOldCustPhoneNum1X().redefines(acupOldCustPhoneNum1);
        // COB:                 15  ACUP-OLD-CUST-PHONE-NUM-2          PIC X(15).
        public NChar acupOldCustPhoneNum2 = new NChar(15);

        // COB:                 15  ACUP-OLD-CUST-PHONE-NUM-2-X REDEFINES
        // COB:                     ACUP-OLD-CUST-PHONE-NUM-2.
        public static class AcupOldCustPhoneNum2X extends NGroup {
          // COB:                     20 FILLER                          PIC X(1).
          public NChar filler736 = new NChar(1);
          // COB:                     20 ACUP-OLD-CUST-PHONE-NUM-2A      PIC X(3).
          public NChar acupOldCustPhoneNum2a = new NChar(3);
          // COB:                     20 FILLER                          PIC X(1).
          public NChar filler738 = new NChar(1);
          // COB:                     20 ACUP-OLD-CUST-PHONE-NUM-2B      PIC X(3).
          public NChar acupOldCustPhoneNum2b = new NChar(3);
          // COB:                     20 FILLER                          PIC X(1).
          public NChar filler740 = new NChar(1);
          // COB:                     20 ACUP-OLD-CUST-PHONE-NUM-2C      PIC X(4).
          public NChar acupOldCustPhoneNum2c = new NChar(4);
          // COB:                     20 FILLER                          PIC X(2).
          public NChar filler742 = new NChar(2);
        }

        public AcupOldCustPhoneNum2X acupOldCustPhoneNum2X =
            (AcupOldCustPhoneNum2X) new AcupOldCustPhoneNum2X().redefines(acupOldCustPhoneNum2);
        // COB:                 15  ACUP-OLD-CUST-SSN-X                PIC X(09).
        public NChar acupOldCustSsnX = new NChar(9);
        // COB:                 15  ACUP-OLD-CUST-SSN                  REDEFINES
        // COB:                     ACUP-OLD-CUST-SSN-X                PIC 9(09).
        public NZoned acupOldCustSsn = new NZoned(9, false).redefines(acupOldCustSsnX);
        // COB:                 15  ACUP-OLD-CUST-GOVT-ISSUED-ID       PIC X(20).
        public NChar acupOldCustGovtIssuedId = new NChar(20);
        // COB:                 15  ACUP-OLD-CUST-DOB-YYYY-MM-DD       PIC X(08).
        public NChar acupOldCustDobYyyyMmDd = new NChar(8);

        // COB:                 15  ACUP-OLD-CUST-DOB-PARTS            REDEFINES
        // COB:                     ACUP-OLD-CUST-DOB-YYYY-MM-DD.
        public static class AcupOldCustDobParts extends NGroup {
          // COB:                     20 ACUP-OLD-CUST-DOB-YEAR          PIC X(4).
          public NChar acupOldCustDobYear = new NChar(4);
          // COB:                     20 ACUP-OLD-CUST-DOB-MON           PIC X(2).
          public NChar acupOldCustDobMon = new NChar(2);
          // COB:                     20 ACUP-OLD-CUST-DOB-DAY           PIC X(2).
          public NChar acupOldCustDobDay = new NChar(2);
        }

        public AcupOldCustDobParts acupOldCustDobParts =
            (AcupOldCustDobParts) new AcupOldCustDobParts().redefines(acupOldCustDobYyyyMmDd);
        // COB:                 15  ACUP-OLD-CUST-EFT-ACCOUNT-ID       PIC X(10).
        public NChar acupOldCustEftAccountId = new NChar(10);
        // COB:                 15  ACUP-OLD-CUST-PRI-HOLDER-IND       PIC X(01).
        public NChar acupOldCustPriHolderInd = new NChar(1);
        // COB:                 15  ACUP-OLD-CUST-FICO-SCORE-X         PIC X(03).
        public NChar acupOldCustFicoScoreX = new NChar(3);
        // COB:                 15  ACUP-OLD-CUST-FICO-SCORE           REDEFINES
        // COB:                     ACUP-OLD-CUST-FICO-SCORE-X         PIC 9(03).
        public NZoned acupOldCustFicoScore = new NZoned(3, false).redefines(acupOldCustFicoScoreX);
      }

      public AcupOldCustData acupOldCustData = new AcupOldCustData();
    }

    public AcupOldDetails acupOldDetails = new AcupOldDetails();

    // COB:           05 ACUP-NEW-DETAILS.
    public static class AcupNewDetails extends NGroup {
      // COB:              10 ACUP-NEW-ACCT-DATA.
      public static class AcupNewAcctData extends NGroup {
        // COB:                 15  ACUP-NEW-ACCT-ID-X                 PIC X(11).
        public NChar acupNewAcctIdX = new NChar(11);
        // COB:                 15  ACUP-NEW-ACCT-ID                   REDEFINES
        // COB:                     ACUP-NEW-ACCT-ID-X                 PIC 9(11).
        public NZoned acupNewAcctId = new NZoned(11, false).redefines(acupNewAcctIdX);
        // COB:                 15  ACUP-NEW-ACTIVE-STATUS             PIC X(01).
        public NChar acupNewActiveStatus = new NChar(1);
        // COB:                 15  ACUP-NEW-CURR-BAL                  PIC X(12).
        public NChar acupNewCurrBal = new NChar(12);
        // COB:                 15  ACUP-NEW-CURR-BAL-N                REDEFINES
        // COB:                     ACUP-NEW-CURR-BAL                  PIC S9(10)V99.
        public NZoned acupNewCurrBalN = new NZoned(12, 2).redefines(acupNewCurrBal);
        // COB:                 15  ACUP-NEW-CREDIT-LIMIT              PIC X(12).
        public NChar acupNewCreditLimit = new NChar(12);
        // COB:                 15  ACUP-NEW-CREDIT-LIMIT-N            REDEFINES
        // COB:                     ACUP-NEW-CREDIT-LIMIT              PIC S9(10)V99.
        public NZoned acupNewCreditLimitN = new NZoned(12, 2).redefines(acupNewCreditLimit);
        // COB:                 15  ACUP-NEW-CASH-CREDIT-LIMIT         PIC X(12).
        public NChar acupNewCashCreditLimit = new NChar(12);
        // COB:                 15  ACUP-NEW-CASH-CREDIT-LIMIT-N       REDEFINES
        // COB:                     ACUP-NEW-CASH-CREDIT-LIMIT         PIC S9(10)V99.
        public NZoned acupNewCashCreditLimitN = new NZoned(12, 2).redefines(acupNewCashCreditLimit);
        // COB:                 15  ACUP-NEW-OPEN-DATE                 PIC X(08).
        public NChar acupNewOpenDate = new NChar(8);

        // COB:                 15  ACUP-NEW-OPEN-DATE-PARTS           REDEFINES
        // COB:                     ACUP-NEW-OPEN-DATE.
        public static class AcupNewOpenDateParts extends NGroup {
          // COB:                     20 ACUP-NEW-OPEN-YEAR              PIC X(4).
          public NChar acupNewOpenYear = new NChar(4);
          // COB:                     20 ACUP-NEW-OPEN-MON               PIC X(2).
          public NChar acupNewOpenMon = new NChar(2);
          // COB:                     20 ACUP-NEW-OPEN-DAY               PIC X(2).
          public NChar acupNewOpenDay = new NChar(2);
        }

        public AcupNewOpenDateParts acupNewOpenDateParts =
            (AcupNewOpenDateParts) new AcupNewOpenDateParts().redefines(acupNewOpenDate);
        // COB:                 15  ACUP-NEW-EXPIRAION-DATE            PIC X(08).
        public NChar acupNewExpiraionDate = new NChar(8);

        // COB:                 15  ACUP-NEW-EXPIRAION-DATE-PARTS      REDEFINES
        // COB:                     ACUP-NEW-EXPIRAION-DATE.
        public static class AcupNewExpiraionDateParts extends NGroup {
          // COB:                     20 ACUP-NEW-EXP-YEAR                PIC X(4).
          public NChar acupNewExpYear = new NChar(4);
          // COB:                     20 ACUP-NEW-EXP-MON                 PIC X(2).
          public NChar acupNewExpMon = new NChar(2);
          // COB:                     20 ACUP-NEW-EXP-DAY                 PIC X(2).
          public NChar acupNewExpDay = new NChar(2);
        }

        public AcupNewExpiraionDateParts acupNewExpiraionDateParts =
            (AcupNewExpiraionDateParts)
                new AcupNewExpiraionDateParts().redefines(acupNewExpiraionDate);
        // COB:                 15  ACUP-NEW-REISSUE-DATE              PIC X(08).
        public NChar acupNewReissueDate = new NChar(8);

        // COB:                 15  ACUP-NEW-REISSUE-DATE-PARTS        REDEFINES
        // COB:                     ACUP-NEW-REISSUE-DATE.
        public static class AcupNewReissueDateParts extends NGroup {
          // COB:                     20 ACUP-NEW-REISSUE-YEAR           PIC X(4).
          public NChar acupNewReissueYear = new NChar(4);
          // COB:                     20 ACUP-NEW-REISSUE-MON            PIC X(2).
          public NChar acupNewReissueMon = new NChar(2);
          // COB:                     20 ACUP-NEW-REISSUE-DAY            PIC X(2).
          public NChar acupNewReissueDay = new NChar(2);
        }

        public AcupNewReissueDateParts acupNewReissueDateParts =
            (AcupNewReissueDateParts) new AcupNewReissueDateParts().redefines(acupNewReissueDate);
        // COB:                 15  ACUP-NEW-CURR-CYC-CREDIT           PIC X(12).
        public NChar acupNewCurrCycCredit = new NChar(12);
        // COB:                 15  ACUP-NEW-CURR-CYC-CREDIT-N         REDEFINES
        // COB:                     ACUP-NEW-CURR-CYC-CREDIT           PIC S9(10)V99.
        public NZoned acupNewCurrCycCreditN = new NZoned(12, 2).redefines(acupNewCurrCycCredit);
        // COB:                 15  ACUP-NEW-CURR-CYC-DEBIT            PIC X(12).
        public NChar acupNewCurrCycDebit = new NChar(12);
        // COB:                 15  ACUP-NEW-CURR-CYC-DEBIT-N          REDEFINES
        // COB:                     ACUP-NEW-CURR-CYC-DEBIT            PIC S9(10)V99.
        public NZoned acupNewCurrCycDebitN = new NZoned(12, 2).redefines(acupNewCurrCycDebit);
        // COB:                 15  ACUP-NEW-GROUP-ID                  PIC X(10).
        public NChar acupNewGroupId = new NChar(10);
      }

      public AcupNewAcctData acupNewAcctData = new AcupNewAcctData();

      // COB:              10 ACUP-NEW-CUST-DATA.
      public static class AcupNewCustData extends NGroup {
        // COB:                 15  ACUP-NEW-CUST-ID-X                 PIC X(09).
        public NChar acupNewCustIdX = new NChar(9);
        // COB:                 15  ACUP-NEW-CUST-ID                   REDEFINES
        // COB:                     ACUP-NEW-CUST-ID-X                 PIC 9(09).
        public NZoned acupNewCustId = new NZoned(9, false).redefines(acupNewCustIdX);
        // COB:                 15  ACUP-NEW-CUST-FIRST-NAME           PIC X(25).
        public NChar acupNewCustFirstName = new NChar(25);
        // COB:                 15  ACUP-NEW-CUST-MIDDLE-NAME          PIC X(25).
        public NChar acupNewCustMiddleName = new NChar(25);
        // COB:                 15  ACUP-NEW-CUST-LAST-NAME            PIC X(25).
        public NChar acupNewCustLastName = new NChar(25);
        // COB:                 15  ACUP-NEW-CUST-ADDR-LINE-1          PIC X(50).
        public NChar acupNewCustAddrLine1 = new NChar(50);
        // COB:                 15  ACUP-NEW-CUST-ADDR-LINE-2          PIC X(50).
        public NChar acupNewCustAddrLine2 = new NChar(50);
        // COB:                 15  ACUP-NEW-CUST-ADDR-LINE-3          PIC X(50).
        public NChar acupNewCustAddrLine3 = new NChar(50);
        // COB:                 15  ACUP-NEW-CUST-ADDR-STATE-CD        PIC X(02).
        public NChar acupNewCustAddrStateCd = new NChar(2);
        // COB:                 15  ACUP-NEW-CUST-ADDR-COUNTRY-CD      PIC X(03).
        public NChar acupNewCustAddrCountryCd = new NChar(3);
        // COB:                 15  ACUP-NEW-CUST-ADDR-ZIP             PIC X(10).
        public NChar acupNewCustAddrZip = new NChar(10);
        // COB:                 15  ACUP-NEW-CUST-PHONE-NUM-1          PIC X(15).
        public NChar acupNewCustPhoneNum1 = new NChar(15);

        // COB:                 15  ACUP-NEW-CUST-PHONE-NUM-1-X REDEFINES
        // COB:                     ACUP-NEW-CUST-PHONE-NUM-1.
        public static class AcupNewCustPhoneNum1X extends NGroup {
          // COB:                     20 FILLER                          PIC X(1).
          public NChar filler814 = new NChar(1);
          // COB:                     20 ACUP-NEW-CUST-PHONE-NUM-1A      PIC X(3).
          public NChar acupNewCustPhoneNum1a = new NChar(3);
          // COB:                     20 FILLER                          PIC X(1).
          public NChar filler816 = new NChar(1);
          // COB:                     20 ACUP-NEW-CUST-PHONE-NUM-1B      PIC X(3).
          public NChar acupNewCustPhoneNum1b = new NChar(3);
          // COB:                     20 FILLER                          PIC X(1).
          public NChar filler818 = new NChar(1);
          // COB:                     20 ACUP-NEW-CUST-PHONE-NUM-1C      PIC X(4).
          public NChar acupNewCustPhoneNum1c = new NChar(4);
          // COB:                     20 FILLER                          PIC X(2).
          public NChar filler820 = new NChar(2);
        }

        public AcupNewCustPhoneNum1X acupNewCustPhoneNum1X =
            (AcupNewCustPhoneNum1X) new AcupNewCustPhoneNum1X().redefines(acupNewCustPhoneNum1);
        // COB:                 15  ACUP-NEW-CUST-PHONE-NUM-2          PIC X(15).
        public NChar acupNewCustPhoneNum2 = new NChar(15);

        // COB:                 15  ACUP-NEW-CUST-PHONE-NUM-2-X REDEFINES
        // COB:                     ACUP-NEW-CUST-PHONE-NUM-2.
        public static class AcupNewCustPhoneNum2X extends NGroup {
          // COB:                     20 FILLER                          PIC X(1).
          public NChar filler824 = new NChar(1);
          // COB:                     20 ACUP-NEW-CUST-PHONE-NUM-2A      PIC X(3).
          public NChar acupNewCustPhoneNum2a = new NChar(3);
          // COB:                     20 FILLER                          PIC X(1).
          public NChar filler826 = new NChar(1);
          // COB:                     20 ACUP-NEW-CUST-PHONE-NUM-2B      PIC X(3).
          public NChar acupNewCustPhoneNum2b = new NChar(3);
          // COB:                     20 FILLER                          PIC X(1).
          public NChar filler828 = new NChar(1);
          // COB:                     20 ACUP-NEW-CUST-PHONE-NUM-2C      PIC X(4).
          public NChar acupNewCustPhoneNum2c = new NChar(4);
          // COB:                     20 FILLER                          PIC X(2).
          public NChar filler830 = new NChar(2);
        }

        public AcupNewCustPhoneNum2X acupNewCustPhoneNum2X =
            (AcupNewCustPhoneNum2X) new AcupNewCustPhoneNum2X().redefines(acupNewCustPhoneNum2);

        // COB:                 15  ACUP-NEW-CUST-SSN-X.
        public static class AcupNewCustSsnX extends NGroup {
          // COB:                     20 ACUP-NEW-CUST-SSN-1             PIC X(03).
          public NChar acupNewCustSsn1 = new NChar(3);
          // COB:                     20 ACUP-NEW-CUST-SSN-2             PIC X(02).
          public NChar acupNewCustSsn2 = new NChar(2);
          // COB:                     20 ACUP-NEW-CUST-SSN-3             PIC X(04).
          public NChar acupNewCustSsn3 = new NChar(4);
        }

        public AcupNewCustSsnX acupNewCustSsnX = new AcupNewCustSsnX();
        // COB:                 15  ACUP-NEW-CUST-SSN                  REDEFINES
        // COB:                     ACUP-NEW-CUST-SSN-X                PIC 9(09).
        public NZoned acupNewCustSsn = new NZoned(9, false).redefines(acupNewCustSsnX);
        // COB:                 15  ACUP-NEW-CUST-GOVT-ISSUED-ID       PIC X(20).
        public NChar acupNewCustGovtIssuedId = new NChar(20);
        // COB:                 15  ACUP-NEW-CUST-DOB-YYYY-MM-DD       PIC X(08).
        public NChar acupNewCustDobYyyyMmDd = new NChar(8);

        // COB:                 15  ACUP-NEW-CUST-DOB-PARTS            REDEFINES
        // COB:                     ACUP-NEW-CUST-DOB-YYYY-MM-DD.
        public static class AcupNewCustDobParts extends NGroup {
          // COB:                     20 ACUP-NEW-CUST-DOB-YEAR          PIC X(4).
          public NChar acupNewCustDobYear = new NChar(4);
          // COB:                     20 ACUP-NEW-CUST-DOB-MON           PIC X(2).
          public NChar acupNewCustDobMon = new NChar(2);
          // COB:                     20 ACUP-NEW-CUST-DOB-DAY           PIC X(2).
          public NChar acupNewCustDobDay = new NChar(2);
        }

        public AcupNewCustDobParts acupNewCustDobParts =
            (AcupNewCustDobParts) new AcupNewCustDobParts().redefines(acupNewCustDobYyyyMmDd);
        // COB:                 15  ACUP-NEW-CUST-EFT-ACCOUNT-ID       PIC X(10).
        public NChar acupNewCustEftAccountId = new NChar(10);
        // COB:                 15  ACUP-NEW-CUST-PRI-HOLDER-IND       PIC X(01).
        public NChar acupNewCustPriHolderInd = new NChar(1);
        // COB:                 15  ACUP-NEW-CUST-FICO-SCORE-X         PIC X(03).
        public NChar acupNewCustFicoScoreX = new NChar(3);
        // COB:                 15  ACUP-NEW-CUST-FICO-SCORE           REDEFINES
        // COB:                     ACUP-NEW-CUST-FICO-SCORE-X         PIC 9(03).
        public NZoned acupNewCustFicoScore = new NZoned(3, false).redefines(acupNewCustFicoScoreX);

        // COB:                     88 FICO-RANGE-IS-VALID             VALUES 300
        public boolean ficoRangeIsValid() {
          return acupNewCustFicoScore.inRange(300, 850);
        }

        public void setFicoRangeIsValid(boolean value) {
          if (value) acupNewCustFicoScore.setValue(300);
        }
      }

      public AcupNewCustData acupNewCustData = new AcupNewCustData();
    }

    public AcupNewDetails acupNewDetails = new AcupNewDetails();
  }

  // COB:        01  WS-COMMAREA                                 PIC X(2000).
  public NChar wsCommarea = new NChar(2000);
  // COB:        01  OVERFLOW                                    PIC 99.
  public NZoned overflow = new NZoned(2, false);
}
