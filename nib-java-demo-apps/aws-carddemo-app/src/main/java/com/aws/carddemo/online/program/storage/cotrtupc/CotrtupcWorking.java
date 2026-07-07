package com.aws.carddemo.online.program.storage.cotrtupc;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Cocom01y;
import com.aws.carddemo.common.copybook.Cotrtup;
import com.aws.carddemo.common.copybook.Cottl01y;
import com.aws.carddemo.common.copybook.Csdat01y;
import com.aws.carddemo.common.copybook.Csmsg01y;
import com.aws.carddemo.common.copybook.Csmsg02y;
import com.aws.carddemo.common.copybook.Csusr01y;
import com.aws.carddemo.common.copybook.Cvcrd01y;
import com.aws.carddemo.common.copybook.Dcltrcat;
import com.aws.carddemo.common.copybook.Dcltrtyp;
import com.nib.commons.storage.*;

public class CotrtupcWorking extends NGroup {
  // COB: 003500 01  WS-MISC-STORAGE.
  public WsMiscStorage wsMiscStorage = new WsMiscStorage();

  public static class WsMiscStorage extends NGroup {

    // COB: 003900   05 WS-CICS-PROCESSNG-VARS.
    public static class WsCicsProcessngVars extends NGroup {
      // COB: 004000      07 WS-RESP-CD                          PIC S9(09) COMP
      // COB: 004100                                             VALUE ZEROS.
      public NInt32 wsRespCd = new NInt32().initial(0);
      // COB: 004200      07 WS-REAS-CD                          PIC S9(09) COMP
      // COB: 004300                                             VALUE ZEROS.
      public NInt32 wsReasCd = new NInt32().initial(0);
      // COB: 004400      07 WS-TRANID                           PIC X(4)
      // COB: 004500                                             VALUE SPACES.
      public NChar wsTranid = new NChar(4).initial(NChar.Space);
      // COB: 004600      07 WS-UCTRANS                          PIC X(4)
      // COB: 004700                                             VALUE SPACES.
      public NChar wsUctrans = new NChar(4).initial(NChar.Space);
    }

    public WsCicsProcessngVars wsCicsProcessngVars = new WsCicsProcessngVars();

    // COB: 005200   05  WS-GENERIC-EDITS.
    public static class WsGenericEdits extends NGroup {
      // COB: 005300     10 WS-EDIT-VARIABLE-NAME                PIC X(25).
      public NChar wsEditVariableName = new NChar(25);
      // COB: 005500     10 WS-EDIT-ALPHANUM-ONLY                PIC X(256).
      public NChar wsEditAlphanumOnly = new NChar(256);
      // COB: 005600     10 WS-EDIT-ALPHANUM-LENGTH              PIC S9(4) COMP-3.
      public NPacked wsEditAlphanumLength = new NPacked(3);
      // COB: 005800     10 WS-EDIT-ALPHANUM-ONLY-FLAGS          PIC X(1).
      public NChar wsEditAlphanumOnlyFlags = new NChar(1);

      // COB: 005900        88  FLG-ALPHNANUM-ISVALID            VALUE LOW-VALUES.
      public boolean flgAlphnanumIsvalid() {
        return wsEditAlphanumOnlyFlags.isLowValues();
      }

      public void setFlgAlphnanumIsvalid(boolean value) {
        if (value) wsEditAlphanumOnlyFlags.lowValues();
      }

      // COB: 006000        88  FLG-ALPHNANUM-NOT-OK             VALUE '0'.
      public boolean flgAlphnanumNotOk() {
        return wsEditAlphanumOnlyFlags.equals("0");
      }

      public void setFlgAlphnanumNotOk(boolean value) {
        if (value) wsEditAlphanumOnlyFlags.setValue("0");
      }

      // COB: 006100        88  FLG-ALPHNANUM-BLANK              VALUE 'B'.
      public boolean flgAlphnanumBlank() {
        return wsEditAlphanumOnlyFlags.equals("B");
      }

      public void setFlgAlphnanumBlank(boolean value) {
        if (value) wsEditAlphanumOnlyFlags.setValue("B");
      }
    }

    public WsGenericEdits wsGenericEdits = new WsGenericEdits();

    // COB: 006700    05 WS-MISC-VARS.
    public static class WsMiscVars extends NGroup {
      // COB: 006800      10 WS-DISP-SQLCODE                    PIC ----9.
      public NZoned wsDispSqlcode = new NZoned(5).formatAs("----0");
      // COB: 006900      10 WS-STRING-MID                      PIC 9(3) VALUE 0.
      public NZoned wsStringMid = new NZoned(3, false).initial(0);
      // COB: 007000      10 WS-STRING-LEN                      PIC 9(3) VALUE 0.
      public NZoned wsStringLen = new NZoned(3, false).initial(0);
      // COB: 007100      10 WS-STRING-OUT                      PIC X(40).
      public NChar wsStringOut = new NChar(40);

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

    public WsMiscVars wsMiscVars = new WsMiscVars();
    // COB: 007800   05  WS-DATACHANGED-FLAG                   PIC X(1).
    public NChar wsDatachangedFlag = new NChar(1);

    // COB: 007900     88  NO-CHANGES-FOUND                    VALUE '0'.
    public boolean noChangesFound() {
      return wsDatachangedFlag.equals("0");
    }

    public void setNoChangesFound(boolean value) {
      if (value) wsDatachangedFlag.setValue("0");
    }

    // COB: 008000     88  CHANGE-HAS-OCCURRED                 VALUE '1'.
    public boolean changeHasOccurred() {
      return wsDatachangedFlag.equals("1");
    }

    public void setChangeHasOccurred(boolean value) {
      if (value) wsDatachangedFlag.setValue("1");
    }

    // COB: 008100   05  WS-INPUT-FLAG                         PIC X(1).
    public NChar wsInputFlag = new NChar(1);

    // COB: 008200     88  INPUT-OK                            VALUE '0'.
    public boolean inputOk() {
      return wsInputFlag.equals("0");
    }

    public void setInputOk(boolean value) {
      if (value) wsInputFlag.setValue("0");
    }

    // COB: 008300     88  INPUT-ERROR                         VALUE '1'.
    public boolean inputError() {
      return wsInputFlag.equals("1");
    }

    public void setInputError(boolean value) {
      if (value) wsInputFlag.setValue("1");
    }

    // COB: 008400     88  INPUT-PENDING                       VALUE LOW-VALUES.
    public boolean inputPending() {
      return wsInputFlag.isLowValues();
    }

    public void setInputPending(boolean value) {
      if (value) wsInputFlag.lowValues();
    }

    // COB: 008500   05  WS-RETURN-FLAG                        PIC X(1).
    public NChar wsReturnFlag = new NChar(1);

    // COB: 008600     88  WS-RETURN-FLAG-OFF                  VALUE LOW-VALUES.
    public boolean wsReturnFlagOff() {
      return wsReturnFlag.isLowValues();
    }

    public void setWsReturnFlagOff(boolean value) {
      if (value) wsReturnFlag.lowValues();
    }

    // COB: 008700     88  WS-RETURN-FLAG-ON                   VALUE '1'.
    public boolean wsReturnFlagOn() {
      return wsReturnFlag.equals("1");
    }

    public void setWsReturnFlagOn(boolean value) {
      if (value) wsReturnFlag.setValue("1");
    }

    // COB: 008800   05  WS-PFK-FLAG                           PIC X(1).
    public NChar wsPfkFlag = new NChar(1);

    // COB: 008900     88  PFK-VALID                           VALUE '0'.
    public boolean pfkValid() {
      return wsPfkFlag.equals("0");
    }

    public void setPfkValid(boolean value) {
      if (value) wsPfkFlag.setValue("0");
    }

    // COB: 009000     88  PFK-INVALID                         VALUE '1'.
    public boolean pfkInvalid() {
      return wsPfkFlag.equals("1");
    }

    public void setPfkInvalid(boolean value) {
      if (value) wsPfkFlag.setValue("1");
    }

    // COB: 009400   05  WS-EDIT-TTYP-FLAG                     PIC X(1).
    public NChar wsEditTtypFlag = new NChar(1);

    // COB: 009500     88  FLG-TRANFILTER-ISVALID              VALUE LOW-VALUES.
    public boolean flgTranfilterIsvalid() {
      return wsEditTtypFlag.isLowValues();
    }

    public void setFlgTranfilterIsvalid(boolean value) {
      if (value) wsEditTtypFlag.lowValues();
    }

    // COB: 009600     88  FLG-TRANFILTER-NOT-OK               VALUE '0'.
    public boolean flgTranfilterNotOk() {
      return wsEditTtypFlag.equals("0");
    }

    public void setFlgTranfilterNotOk(boolean value) {
      if (value) wsEditTtypFlag.setValue("0");
    }

    // COB: 009700     88  FLG-TRANFILTER-BLANK                VALUE 'B'.
    public boolean flgTranfilterBlank() {
      return wsEditTtypFlag.equals("B");
    }

    public void setFlgTranfilterBlank(boolean value) {
      if (value) wsEditTtypFlag.setValue("B");
    }

    // COB: 009900   05 WS-NON-KEY-FLAGS.
    public static class WsNonKeyFlags extends NGroup {
      // COB: 010000     10  WS-EDIT-DESC-FLAGS                  PIC X(1).
      public NChar wsEditDescFlags = new NChar(1);

      // COB: 010100         88  FLG-DESCRIPTION-ISVALID          VALUE LOW-VALUES.
      public boolean flgDescriptionIsvalid() {
        return wsEditDescFlags.isLowValues();
      }

      public void setFlgDescriptionIsvalid(boolean value) {
        if (value) wsEditDescFlags.lowValues();
      }

      // COB: 010200         88  FLG-DESCRIPTION-NOT-OK           VALUE '0'.
      public boolean flgDescriptionNotOk() {
        return wsEditDescFlags.equals("0");
      }

      public void setFlgDescriptionNotOk(boolean value) {
        if (value) wsEditDescFlags.setValue("0");
      }

      // COB: 010300         88  FLG-DESCRIPTION-BLANK            VALUE 'B'.
      public boolean flgDescriptionBlank() {
        return wsEditDescFlags.equals("B");
      }

      public void setFlgDescriptionBlank(boolean value) {
        if (value) wsEditDescFlags.setValue("B");
      }
    }

    public WsNonKeyFlags wsNonKeyFlags = new WsNonKeyFlags();

    // COB: 010700   05 CICS-OUTPUT-EDIT-VARS.
    public static class CicsOutputEditVars extends NGroup {
      // COB: 010800     10  WS-EDIT-DATE-X                      PIC X(10).
      public NChar wsEditDateX = new NChar(10);

      // COB: 010900     10  FILLER REDEFINES WS-EDIT-DATE-X.
      public static class Filler109 extends NGroup {
        // COB: 011000         20 WS-EDIT-DATE-X-YEAR              PIC X(4).
        public NChar wsEditDateXYear = new NChar(4);
        // COB: 011100         20 FILLER                           PIC X(1).
        public NChar filler111 = new NChar(1);
        // COB: 011200         20 WS-EDIT-DATE-MONTH               PIC X(2).
        public NChar wsEditDateMonth = new NChar(2);
        // COB: 011300         20 FILLER                           PIC X(1).
        public NChar filler113 = new NChar(1);
        // COB: 011400         20 WS-EDIT-DATE-DAY                 PIC X(2).
        public NChar wsEditDateDay = new NChar(2);
      }

      public Filler109 filler109 = (Filler109) new Filler109().redefines(wsEditDateX);
      // COB: 011500     10  WS-EDIT-DATE-X REDEFINES
      // COB: 011600         WS-EDIT-DATE-X                      PIC 9(10).
      public NZoned wsEditDateX_ = new NZoned(10, false).redefines(wsEditDateX);
      // COB: 011700     10  WS-EDIT-CURRENCY-9-2                PIC X(15).
      public NChar wsEditCurrency9_2 = new NChar(15);
      // COB: 011800     10  WS-EDIT-CURRENCY-9-2-F              PIC +ZZZ,ZZZ,ZZZ.99.
      public NZoned wsEditCurrency9_2F = new NZoned(15).formatAs("+###,###,###.00");
      // COB: 011900     10  WS-EDIT-NUMERIC-2                   PIC 9(02).
      public NZoned wsEditNumeric2 = new NZoned(2, false);
      // COB: 012000     10  WS-EDIT-ALPHANUMERIC-2              PIC X(02).
      public NChar wsEditAlphanumeric2 = new NChar(2);
    }

    public CicsOutputEditVars cicsOutputEditVars = new CicsOutputEditVars();

    // COB: 012500   05  WS-TABLE-READ-FLAGS.
    public static class WsTableReadFlags extends NGroup {
      // COB: 012600     10 WS-TRANTYPE-MASTER-READ-FLAG         PIC X(1).
      public NChar wsTrantypeMasterReadFlag = new NChar(1);

      // COB: 012700        88 FOUND-TRANTYPE-IN-TABLE          VALUE '1'.
      public boolean foundTrantypeInTable() {
        return wsTrantypeMasterReadFlag.equals("1");
      }

      public void setFoundTrantypeInTable(boolean value) {
        if (value) wsTrantypeMasterReadFlag.setValue("1");
      }
    }

    public WsTableReadFlags wsTableReadFlags = new WsTableReadFlags();

    // COB: 013000    05 TTYP-UPDATE-RECORD.
    public static class TtypUpdateRecord extends NGroup {
      // COB: 013400         15  TTUP-UPDATE-TTYP-TYPE               PIC X(02).
      public NChar ttupUpdateTtypType = new NChar(2);
      // COB: 013500         15  TTUP-UPDATE-TTYP-TYPE-DESC          PIC X(50).
      public NChar ttupUpdateTtypTypeDesc = new NChar(50);
      // COB: 013600         15  FILLER                              PIC X(08).
      public NChar filler136 = new NChar(8);
    }

    public TtypUpdateRecord ttypUpdateRecord = new TtypUpdateRecord();
    // COB: 014200   05  WS-INFO-MSG                           PIC X(40).
    public NChar wsInfoMsg = new NChar(40);

    // COB: 014300     88  WS-NO-INFO-MESSAGE                 VALUES
    public boolean wsNoInfoMessage() {
      return wsInfoMsg.isSpaces() || wsInfoMsg.isLowValues();
    }

    public void setWsNoInfoMessage(boolean value) {
      if (value) wsInfoMsg.setValue(SPACES);
    }

    // COB: 014500     88  FOUND-TRANTYPE-DATA                 VALUE
    public boolean foundTrantypeData() {
      return wsInfoMsg.equals("Selected transaction type shown above");
    }

    public void setFoundTrantypeData(boolean value) {
      if (value) wsInfoMsg.setValue("Selected transaction type shown above");
    }

    // COB: 014700     88  PROMPT-FOR-SEARCH-KEYS              VALUE
    public boolean promptForSearchKeys() {
      return wsInfoMsg.equals("Enter transaction type to be maintained");
    }

    public void setPromptForSearchKeys(boolean value) {
      if (value) wsInfoMsg.setValue("Enter transaction type to be maintained");
    }

    // COB: 014900     88  PROMPT-CREATE-NEW-RECORD            VALUE
    public boolean promptCreateNewRecord() {
      return wsInfoMsg.equals("Press F05 to add. F12 to cancel");
    }

    public void setPromptCreateNewRecord(boolean value) {
      if (value) wsInfoMsg.setValue("Press F05 to add. F12 to cancel");
    }

    // COB: 015100     88  PROMPT-DELETE-CONFIRM               VALUE
    public boolean promptDeleteConfirm() {
      return wsInfoMsg.equals("Delete this record ? Press F4 to confirm");
    }

    public void setPromptDeleteConfirm(boolean value) {
      if (value) wsInfoMsg.setValue("Delete this record ? Press F4 to confirm");
    }

    // COB: 015300     88  CONFIRM-DELETE-SUCCESS              VALUE
    public boolean confirmDeleteSuccess() {
      return wsInfoMsg.equals("Delete successful.");
    }

    public void setConfirmDeleteSuccess(boolean value) {
      if (value) wsInfoMsg.setValue("Delete successful.");
    }

    // COB: 015500     88  PROMPT-FOR-CHANGES                  VALUE
    public boolean promptForChanges() {
      return wsInfoMsg.equals("Update transaction type details shown.");
    }

    public void setPromptForChanges(boolean value) {
      if (value) wsInfoMsg.setValue("Update transaction type details shown.");
    }

    // COB: 015700     88  PROMPT-FOR-NEWDATA                  VALUE
    public boolean promptForNewdata() {
      return wsInfoMsg.equals("Enter new transaction type details.");
    }

    public void setPromptForNewdata(boolean value) {
      if (value) wsInfoMsg.setValue("Enter new transaction type details.");
    }

    // COB: 016000     88  PROMPT-FOR-CONFIRMATION             VALUE
    public boolean promptForConfirmation() {
      return wsInfoMsg.equals("Changes validated.Press F5 to save");
    }

    public void setPromptForConfirmation(boolean value) {
      if (value) wsInfoMsg.setValue("Changes validated.Press F5 to save");
    }

    // COB: 016200     88  CONFIRM-UPDATE-SUCCESS              VALUE
    public boolean confirmUpdateSuccess() {
      return wsInfoMsg.equals("Changes committed to database");
    }

    public void setConfirmUpdateSuccess(boolean value) {
      if (value) wsInfoMsg.setValue("Changes committed to database");
    }

    // COB: 016400     88  INFORM-FAILURE                      VALUE
    public boolean informFailure() {
      return wsInfoMsg.equals("Changes unsuccessful");
    }

    public void setInformFailure(boolean value) {
      if (value) wsInfoMsg.setValue("Changes unsuccessful");
    }

    // COB: 016700   05  WS-RETURN-MSG                         PIC X(75).
    public NChar wsReturnMsg = new NChar(75);

    // COB: 016800     88  WS-RETURN-MSG-OFF                   VALUE SPACES.
    public boolean wsReturnMsgOff() {
      return wsReturnMsg.isSpaces();
    }

    public void setWsReturnMsgOff(boolean value) {
      if (value) wsReturnMsg.setValue(SPACES);
    }

    // COB: 016900     88  WS-EXIT-MESSAGE                     VALUE
    public boolean wsExitMessage() {
      return wsReturnMsg.equals("PF03 pressed.Exiting              ");
    }

    public void setWsExitMessage(boolean value) {
      if (value) wsReturnMsg.setValue("PF03 pressed.Exiting              ");
    }

    // COB: 017100     88  WS-INVALID-KEY                      VALUE
    public boolean wsInvalidKey() {
      return wsReturnMsg.equals("Invalid Key pressed. ");
    }

    public void setWsInvalidKey(boolean value) {
      if (value) wsReturnMsg.setValue("Invalid Key pressed. ");
    }

    // COB: 017300     88  WS-NAME-MUST-BE-ALPHA               VALUE
    public boolean wsNameMustBeAlpha() {
      return wsReturnMsg.equals("Name can only contain alphabets and spaces");
    }

    public void setWsNameMustBeAlpha(boolean value) {
      if (value) wsReturnMsg.setValue("Name can only contain alphabets and spaces");
    }

    // COB: 017500     88  WS-RECORD-NOT-FOUND                 VALUE
    public boolean wsRecordNotFound() {
      return wsReturnMsg.equals("No record found for this key in database");
    }

    public void setWsRecordNotFound(boolean value) {
      if (value) wsReturnMsg.setValue("No record found for this key in database");
    }

    // COB: 017700     88  NO-SEARCH-CRITERIA-RECEIVED         VALUE
    public boolean noSearchCriteriaReceived() {
      return wsReturnMsg.equals("No input received");
    }

    public void setNoSearchCriteriaReceived(boolean value) {
      if (value) wsReturnMsg.setValue("No input received");
    }

    // COB: 017900     88  NO-CHANGES-DETECTED                 VALUE
    public boolean noChangesDetected() {
      return wsReturnMsg.equals("No change detected with respect to values fetched.");
    }

    public void setNoChangesDetected(boolean value) {
      if (value) wsReturnMsg.setValue("No change detected with respect to values fetched.");
    }

    // COB: 018100     88  COULD-NOT-LOCK-REC-FOR-UPDATE       VALUE
    public boolean couldNotLockRecForUpdate() {
      return wsReturnMsg.equals("Could not lock record for update");
    }

    public void setCouldNotLockRecForUpdate(boolean value) {
      if (value) wsReturnMsg.setValue("Could not lock record for update");
    }

    // COB: 018300     88  DATA-WAS-CHANGED-BEFORE-UPDATE      VALUE
    public boolean dataWasChangedBeforeUpdate() {
      return wsReturnMsg.equals("Record changed by some one else. Please review");
    }

    public void setDataWasChangedBeforeUpdate(boolean value) {
      if (value) wsReturnMsg.setValue("Record changed by some one else. Please review");
    }

    // COB: 018500     88  WS-UPDATE-WAS-CANCELLED             VALUE
    public boolean wsUpdateWasCancelled() {
      return wsReturnMsg.equals("Update was cancelled");
    }

    public void setWsUpdateWasCancelled(boolean value) {
      if (value) wsReturnMsg.setValue("Update was cancelled");
    }

    // COB: 018700     88  TABLE-UPDATE-FAILED                 VALUE
    public boolean tableUpdateFailed() {
      return wsReturnMsg.equals("Update of record failed");
    }

    public void setTableUpdateFailed(boolean value) {
      if (value) wsReturnMsg.setValue("Update of record failed");
    }

    // COB: 018900     88  RECORD-DELETE-FAILED                VALUE
    public boolean recordDeleteFailed() {
      return wsReturnMsg.equals("Delete of record failed");
    }

    public void setRecordDeleteFailed(boolean value) {
      if (value) wsReturnMsg.setValue("Delete of record failed");
    }

    // COB: 019100     88  WS-DELETE-WAS-CANCELLED             VALUE
    public boolean wsDeleteWasCancelled() {
      return wsReturnMsg.equals("Delete was cancelled");
    }

    public void setWsDeleteWasCancelled(boolean value) {
      if (value) wsReturnMsg.setValue("Delete was cancelled");
    }

    // COB: 019300     88  WS-INVALID-KEY-PRESSED              VALUE
    public boolean wsInvalidKeyPressed() {
      return wsReturnMsg.equals("Invalid key pressed");
    }

    public void setWsInvalidKeyPressed(boolean value) {
      if (value) wsReturnMsg.setValue("Invalid key pressed");
    }

    // COB: 019500     88  CODING-TO-BE-DONE                   VALUE
    public boolean codingToBeDone() {
      return wsReturnMsg.equals("Looks Good.... so far");
    }

    public void setCodingToBeDone(boolean value) {
      if (value) wsReturnMsg.setValue("Looks Good.... so far");
    }
  }

  // COB: 020000 01 WS-LITERALS.
  public WsLiterals wsLiterals = new WsLiterals();

  public static class WsLiterals extends NGroup {

    // COB: 020100    05 LIT-THISPGM                           PIC X(8)
    // COB: 020200                                             VALUE 'COTRTUPC'.
    public NChar litThispgm = new NChar(8).initial("COTRTUPC");
    // COB: 020300    05 LIT-THISTRANID                        PIC X(4)
    // COB: 020400                                             VALUE 'CTTU'.
    public NChar litThistranid = new NChar(4).initial("CTTU");
    // COB: 020500    05 LIT-THISMAPSET                        PIC X(8)
    // COB: 020600                                             VALUE 'COTRTUP '.
    public NChar litThismapset = new NChar(8).initial("COTRTUP ");
    // COB: 020700    05 LIT-THISMAP                           PIC X(7)
    // COB: 020800                                             VALUE 'CTRTUPA'.
    public NChar litThismap = new NChar(7).initial("CTRTUPA");
    // COB: 020900    05 LIT-ADMINPGM                           PIC X(8)
    // COB: 021000                                             VALUE 'COADM01C'.
    public NChar litAdminpgm = new NChar(8).initial("COADM01C");
    // COB: 021100    05 LIT-ADMINTRANID                        PIC X(4)
    // COB: 021200                                             VALUE 'CA00'.
    public NChar litAdmintranid = new NChar(4).initial("CA00");
    // COB: 021300    05 LIT-ADMINMAPSET                        PIC X(7)
    // COB: 021400                                             VALUE 'COADM01'.
    public NChar litAdminmapset = new NChar(7).initial("COADM01");
    // COB: 021500    05 LIT-ADMINMAP                           PIC X(7)
    // COB: 021600                                             VALUE 'COADM1A'.
    public NChar litAdminmap = new NChar(7).initial("COADM1A");
    // COB: 021700    05 LIT-LISTTPGM                           PIC X(8)
    // COB: 021800                                             VALUE 'COTRTLIC'.
    public NChar litListtpgm = new NChar(8).initial("COTRTLIC");
    // COB: 021900    05 LIT-LISTTTRANID                        PIC X(4)
    // COB: 022000                                             VALUE 'CTLI'.
    public NChar litListttranid = new NChar(4).initial("CTLI");
    // COB: 022100    05 LIT-LISTTMAPSET                        PIC X(7)
    // COB: 022200                                             VALUE 'COTRTLI'.
    public NChar litListtmapset = new NChar(7).initial("COTRTLI");
    // COB: 022300    05 LIT-LISTTMAP                           PIC X(7)
    // COB: 022400                                             VALUE 'CTRTLIA'.
    public NChar litListtmap = new NChar(7).initial("CTRTLIA");

    // COB: 023000    05 LIT-ALL-ALPHANUM-FROM-X.
    public static class LitAllAlphanumFromX extends NGroup {
      // COB: 023100       10 LIT-ALL-ALPHA-FROM-X.
      public static class LitAllAlphaFromX extends NGroup {
        // COB: 023200          15 LIT-UPPER                       PIC X(26)
        // COB: 023300                           VALUE 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.
        public NChar litUpper = new NChar(26).initial("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        // COB: 023400          15 LIT-LOWER                       PIC X(26)
        // COB: 023500                           VALUE 'abcdefghijklmnopqrstuvwxyz'.
        public NChar litLower = new NChar(26).initial("abcdefghijklmnopqrstuvwxyz");
      }

      public LitAllAlphaFromX litAllAlphaFromX = new LitAllAlphaFromX();
      // COB: 023600       10 LIT-NUMBERS                        PIC X(10)
      // COB: 023700                           VALUE '0123456789'.
      public NChar litNumbers = new NChar(10).initial("0123456789");
    }

    public LitAllAlphanumFromX litAllAlphanumFromX = new LitAllAlphanumFromX();
  }

  public Cvcrd01y cvcrd01y = new Cvcrd01y();
  // COB: 024900 01  LIT-ALL-ALPHA-FROM     PIC X(52) VALUE SPACES.
  public NChar litAllAlphaFrom = new NChar(52).initial(NChar.Space);
  // COB: 025000 01  LIT-ALL-ALPHANUM-FROM  PIC X(62) VALUE SPACES.
  public NChar litAllAlphanumFrom = new NChar(62).initial(NChar.Space);
  // COB: 025100 01  LIT-ALL-NUM-FROM       PIC X(10) VALUE SPACES.
  public NChar litAllNumFrom = new NChar(10).initial(NChar.Space);
  // COB: 025200 77  LIT-ALPHA-SPACES-TO    PIC X(52) VALUE SPACES.
  public NChar litAlphaSpacesTo = new NChar(52).initial(NChar.Space);
  // COB: 025300 77  LIT-ALPHANUM-SPACES-TO PIC X(62) VALUE SPACES.
  public NChar litAlphanumSpacesTo = new NChar(62).initial(NChar.Space);
  // COB: 025400 77  LIT-NUM-SPACES-TO      PIC X(10) VALUE SPACES.
  public NChar litNumSpacesTo = new NChar(10).initial(NChar.Space);
  public Cottl01y cottl01y = new Cottl01y();
  public Cotrtup cotrtup = new Cotrtup();
  public Csdat01y csdat01y = new Csdat01y();
  public Csmsg01y csmsg01y = new Csmsg01y();
  public Csmsg02y csmsg02y = new Csmsg02y();
  public Csusr01y csusr01y = new Csusr01y();
  public Dcltrtyp dcltrtyp = new Dcltrtyp();
  public Dcltrcat dcltrcat = new Dcltrcat();
  public Cocom01y cocom01y = new Cocom01y();
  // COB: 029400 01 WS-THIS-PROGCOMMAREA.
  public WsThisProgcommarea wsThisProgcommarea = new WsThisProgcommarea();

  public static class WsThisProgcommarea extends NGroup {

    // COB: 029500    05 TTUP-UPDATE-SCREEN-DATA.
    public static class TtupUpdateScreenData extends NGroup {
      // COB: 029600       10 TTUP-CHANGE-ACTION                     PIC X(1)
      // COB: 029700                                                 VALUE LOW-VALUES.
      public NChar ttupChangeAction = new NChar(1).initial(NChar.LowValue);

      // COB: 029800          88 TTUP-DETAILS-NOT-FETCHED            VALUES
      public boolean ttupDetailsNotFetched() {
        return ttupChangeAction.isLowValues() || ttupChangeAction.isSpaces();
      }

      public void setTtupDetailsNotFetched(boolean value) {
        if (value) ttupChangeAction.lowValues();
      }

      // COB: 030100          88 TTUP-INVALID-SEARCH-KEYS            VALUE 'K'.
      public boolean ttupInvalidSearchKeys() {
        return ttupChangeAction.equals("K");
      }

      public void setTtupInvalidSearchKeys(boolean value) {
        if (value) ttupChangeAction.setValue("K");
      }

      // COB: 030200          88 TTUP-DETAILS-NOT-FOUND              VALUE 'X'.
      public boolean ttupDetailsNotFound() {
        return ttupChangeAction.equals("X");
      }

      public void setTtupDetailsNotFound(boolean value) {
        if (value) ttupChangeAction.setValue("X");
      }

      // COB: 030300          88 TTUP-SHOW-DETAILS                   VALUE 'S'.
      public boolean ttupShowDetails() {
        return ttupChangeAction.equals("S");
      }

      public void setTtupShowDetails(boolean value) {
        if (value) ttupChangeAction.setValue("S");
      }

      // COB: 030500          88 TTUP-CREATE-NEW-RECORD              VALUE 'R'.
      public boolean ttupCreateNewRecord() {
        return ttupChangeAction.equals("R");
      }

      public void setTtupCreateNewRecord(boolean value) {
        if (value) ttupChangeAction.setValue("R");
      }

      // COB: 030600          88 TTUP-REVIEW-NEW-RECORD              VALUE 'V'.
      public boolean ttupReviewNewRecord() {
        return ttupChangeAction.equals("V");
      }

      public void setTtupReviewNewRecord(boolean value) {
        if (value) ttupChangeAction.setValue("V");
      }

      // COB: 030700          88 TTUP-DELETE-IN-PROGRESS             VALUES '9'
      public boolean ttupDeleteInProgress() {
        return ttupChangeAction.equals("9")
            || ttupChangeAction.equals("8")
            || ttupChangeAction.equals("7")
            || ttupChangeAction.equals("6");
      }

      public void setTtupDeleteInProgress(boolean value) {
        if (value) ttupChangeAction.setValue("9");
      }

      // COB: 031000          88 TTUP-CONFIRM-DELETE                 VALUE '9'.
      public boolean ttupConfirmDelete() {
        return ttupChangeAction.equals("9");
      }

      public void setTtupConfirmDelete(boolean value) {
        if (value) ttupChangeAction.setValue("9");
      }

      // COB: 031100          88 TTUP-START-DELETE                   VALUE '8'.
      public boolean ttupStartDelete() {
        return ttupChangeAction.equals("8");
      }

      public void setTtupStartDelete(boolean value) {
        if (value) ttupChangeAction.setValue("8");
      }

      // COB: 031200          88 TTUP-DELETE-DONE                    VALUE '7'.
      public boolean ttupDeleteDone() {
        return ttupChangeAction.equals("7");
      }

      public void setTtupDeleteDone(boolean value) {
        if (value) ttupChangeAction.setValue("7");
      }

      // COB: 031300          88 TTUP-DELETE-FAILED                  VALUE '6'.
      public boolean ttupDeleteFailed() {
        return ttupChangeAction.equals("6");
      }

      public void setTtupDeleteFailed(boolean value) {
        if (value) ttupChangeAction.setValue("6");
      }

      // COB: 031500          88 TTUP-CHANGES-MADE                   VALUES 'E', 'N'
      public boolean ttupChangesMade() {
        return ttupChangeAction.equals("E")
            || ttupChangeAction.equals("N")
            || ttupChangeAction.equals("L")
            || ttupChangeAction.equals("F");
      }

      public void setTtupChangesMade(boolean value) {
        if (value) ttupChangeAction.setValue("E");
      }

      // COB: 031800          88 TTUP-CHANGES-NOT-OK                 VALUE 'E'.
      public boolean ttupChangesNotOk() {
        return ttupChangeAction.equals("E");
      }

      public void setTtupChangesNotOk(boolean value) {
        if (value) ttupChangeAction.setValue("E");
      }

      // COB: 031900          88 TTUP-CHANGES-OK-NOT-CONFIRMED       VALUE 'N'.
      public boolean ttupChangesOkNotConfirmed() {
        return ttupChangeAction.equals("N");
      }

      public void setTtupChangesOkNotConfirmed(boolean value) {
        if (value) ttupChangeAction.setValue("N");
      }

      // COB: 032200          88 TTUP-CHANGES-FAILED                 VALUES 'L', 'F'.
      public boolean ttupChangesFailed() {
        return ttupChangeAction.equals("L") || ttupChangeAction.equals("F");
      }

      public void setTtupChangesFailed(boolean value) {
        if (value) ttupChangeAction.setValue("L");
      }

      // COB: 032300          88 TTUP-CHANGES-OKAYED-LOCK-ERROR      VALUE 'L'.
      public boolean ttupChangesOkayedLockError() {
        return ttupChangeAction.equals("L");
      }

      public void setTtupChangesOkayedLockError(boolean value) {
        if (value) ttupChangeAction.setValue("L");
      }

      // COB: 032400          88 TTUP-CHANGES-OKAYED-BUT-FAILED      VALUE 'F'.
      public boolean ttupChangesOkayedButFailed() {
        return ttupChangeAction.equals("F");
      }

      public void setTtupChangesOkayedButFailed(boolean value) {
        if (value) ttupChangeAction.setValue("F");
      }

      // COB: 032600          88 TTUP-CHANGES-OKAYED-AND-DONE        VALUE 'C'.
      public boolean ttupChangesOkayedAndDone() {
        return ttupChangeAction.equals("C");
      }

      public void setTtupChangesOkayedAndDone(boolean value) {
        if (value) ttupChangeAction.setValue("C");
      }

      // COB: 032700          88 TTUP-CHANGES-BACKED-OUT             VALUE 'B'.
      public boolean ttupChangesBackedOut() {
        return ttupChangeAction.equals("B");
      }

      public void setTtupChangesBackedOut(boolean value) {
        if (value) ttupChangeAction.setValue("B");
      }
    }

    public TtupUpdateScreenData ttupUpdateScreenData = new TtupUpdateScreenData();

    // COB: 032800    05 TTUP-OLD-DETAILS.
    public static class TtupOldDetails extends NGroup {
      // COB: 032900       10 TTUP-OLD-TTYP-DATA.
      public static class TtupOldTtypData extends NGroup {
        // COB: 033000          15  TTUP-OLD-TTYP-TYPE                 PIC X(02).
        public NChar ttupOldTtypType = new NChar(2);
        // COB: 033100          15  TTUP-OLD-TTYP-TYPE-DESC            PIC X(50).
        public NChar ttupOldTtypTypeDesc = new NChar(50);
      }

      public TtupOldTtypData ttupOldTtypData = new TtupOldTtypData();
    }

    public TtupOldDetails ttupOldDetails = new TtupOldDetails();

    // COB: 033200    05 TTUP-NEW-DETAILS.
    public static class TtupNewDetails extends NGroup {
      // COB: 033300       10 TTUP-NEW-TTYP-DATA.
      public static class TtupNewTtypData extends NGroup {
        // COB: 033400          15  TTUP-NEW-TTYP-TYPE                 PIC X(02).
        public NChar ttupNewTtypType = new NChar(2);
        // COB: 033500          15  TTUP-NEW-TTYP-TYPE-DESC            PIC X(50).
        public NChar ttupNewTtypTypeDesc = new NChar(50);
      }

      public TtupNewTtypData ttupNewTtypData = new TtupNewTtypData();
    }

    public TtupNewDetails ttupNewDetails = new TtupNewDetails();
  }

  // COB: 033600 01  WS-COMMAREA                                 PIC X(2000).
  public NChar wsCommarea = new NChar(2000);
}
