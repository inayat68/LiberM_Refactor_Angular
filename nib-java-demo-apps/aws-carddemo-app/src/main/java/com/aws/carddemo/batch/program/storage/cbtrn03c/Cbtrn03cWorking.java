package com.aws.carddemo.batch.program.storage.cbtrn03c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Cvact03y;
import com.aws.carddemo.common.copybook.Cvtra03y;
import com.aws.carddemo.common.copybook.Cvtra04y;
import com.aws.carddemo.common.copybook.Cvtra05y;
import com.aws.carddemo.common.copybook.Cvtra07y;
import com.nib.commons.storage.*;

public class Cbtrn03cWorking extends NGroup {
  public Cvtra05y cvtra05y = new Cvtra05y();
  // COB:        01 TRANFILE-STATUS.
  public TranfileStatus tranfileStatus = new TranfileStatus();

  public static class TranfileStatus extends NGroup {

    // COB:           05 TRANFILE-STAT1     PIC X.
    public NChar tranfileStat1 = new NChar(1);
    // COB:           05 TRANFILE-STAT2     PIC X.
    public NChar tranfileStat2 = new NChar(1);
  }

  public Cvact03y cvact03y = new Cvact03y();
  // COB:        01  CARDXREF-STATUS.
  public CardxrefStatus cardxrefStatus = new CardxrefStatus();

  public static class CardxrefStatus extends NGroup {

    // COB:            05  CARDXREF-STAT1      PIC X.
    public NChar cardxrefStat1 = new NChar(1);
    // COB:            05  CARDXREF-STAT2      PIC X.
    public NChar cardxrefStat2 = new NChar(1);
  }

  public Cvtra03y cvtra03y = new Cvtra03y();
  // COB:        01  TRANTYPE-STATUS.
  public TrantypeStatus trantypeStatus = new TrantypeStatus();

  public static class TrantypeStatus extends NGroup {

    // COB:            05  TRANTYPE-STAT1      PIC X.
    public NChar trantypeStat1 = new NChar(1);
    // COB:            05  TRANTYPE-STAT2      PIC X.
    public NChar trantypeStat2 = new NChar(1);
  }

  public Cvtra04y cvtra04y = new Cvtra04y();
  // COB:        01  TRANCATG-STATUS.
  public TrancatgStatus trancatgStatus = new TrancatgStatus();

  public static class TrancatgStatus extends NGroup {

    // COB:            05  TRANCATG-STAT1      PIC X.
    public NChar trancatgStat1 = new NChar(1);
    // COB:            05  TRANCATG-STAT2      PIC X.
    public NChar trancatgStat2 = new NChar(1);
  }

  public Cvtra07y cvtra07y = new Cvtra07y();
  // COB:        01 TRANREPT-STATUS.
  public TranreptStatus tranreptStatus = new TranreptStatus();

  public static class TranreptStatus extends NGroup {

    // COB:            05 REPTFILE-STAT1     PIC X.
    public NChar reptfileStat1 = new NChar(1);
    // COB:            05 REPTFILE-STAT2     PIC X.
    public NChar reptfileStat2 = new NChar(1);
  }

  // COB:        01 DATEPARM-STATUS.
  public DateparmStatus dateparmStatus = new DateparmStatus();

  public static class DateparmStatus extends NGroup {

    // COB:            05 DATEPARM-STAT1     PIC X.
    public NChar dateparmStat1 = new NChar(1);
    // COB:            05 DATEPARM-STAT2     PIC X.
    public NChar dateparmStat2 = new NChar(1);
  }

  // COB:        01 WS-DATEPARM-RECORD.
  public WsDateparmRecord wsDateparmRecord = new WsDateparmRecord();

  public static class WsDateparmRecord extends NGroup {

    // COB:            05 WS-START-DATE      PIC X(10).
    public NChar wsStartDate = new NChar(10);
    // COB:            05 FILLER             PIC X(01).
    public NChar filler124 = new NChar(1);
    // COB:            05 WS-END-DATE        PIC X(10).
    public NChar wsEndDate = new NChar(10);
  }

  // COB:        01 WS-REPORT-VARS.
  public WsReportVars wsReportVars = new WsReportVars();

  public static class WsReportVars extends NGroup {

    // COB:            05 WS-FIRST-TIME      PIC X      VALUE 'Y'.
    public NChar wsFirstTime = new NChar(1).initial("Y");
    // COB:            05 WS-LINE-COUNTER    PIC 9(09) COMP-3
    // COB:                                             VALUE 0.
    public NPacked wsLineCounter = new NPacked(5, false).initial(0);
    // COB:            05 WS-PAGE-SIZE       PIC 9(03) COMP-3
    // COB:                                             VALUE 20.
    public NPacked wsPageSize = new NPacked(2, false).initial(20);
    // COB:            05 WS-BLANK-LINE      PIC X(133) VALUE SPACES.
    public NChar wsBlankLine = new NChar(133).initial(NChar.Space);
    // COB:            05 WS-PAGE-TOTAL      PIC S9(09)V99 VALUE 0.
    public NZoned wsPageTotal = new NZoned(11, 2).initial(0);
    // COB:            05 WS-ACCOUNT-TOTAL   PIC S9(09)V99 VALUE 0.
    public NZoned wsAccountTotal = new NZoned(11, 2).initial(0);
    // COB:            05 WS-GRAND-TOTAL     PIC S9(09)V99 VALUE 0.
    public NZoned wsGrandTotal = new NZoned(11, 2).initial(0);
    // COB:            05 WS-CURR-CARD-NUM   PIC X(16) VALUE SPACES.
    public NChar wsCurrCardNum = new NChar(16).initial(NChar.Space);
  }

  // COB:        01 IO-STATUS.
  public IoStatus ioStatus = new IoStatus();

  public static class IoStatus extends NGroup {

    // COB:           05 IO-STAT1           PIC X.
    public NChar ioStat1 = new NChar(1);
    // COB:           05 IO-STAT2           PIC X.
    public NChar ioStat2 = new NChar(1);
  }

  // COB:        01 TWO-BYTES-BINARY      PIC 9(4) BINARY.
  public NInt16 twoBytesBinary = new NInt16();

  // COB:        01 TWO-BYTES-ALPHA REDEFINES TWO-BYTES-BINARY.
  public static class TwoBytesAlpha extends NGroup {
    // COB:           05 TWO-BYTES-LEFT     PIC X.
    public NChar twoBytesLeft = new NChar(1);
    // COB:           05 TWO-BYTES-RIGHT    PIC X.
    public NChar twoBytesRight = new NChar(1);
  }

  public TwoBytesAlpha twoBytesAlpha =
      (TwoBytesAlpha) new TwoBytesAlpha().redefines(twoBytesBinary);
  // COB:        01 IO-STATUS-04.
  public IoStatus04 ioStatus04 = new IoStatus04();

  public static class IoStatus04 extends NGroup {

    // COB:           05 IO-STATUS-0401     PIC 9      VALUE 0.
    public NZoned ioStatus0401 = new NZoned(1, false).initial(0);
    // COB:           05 IO-STATUS-0403     PIC 999    VALUE 0.
    public NZoned ioStatus0403 = new NZoned(3, false).initial(0);
  }

  // COB:        01 APPL-RESULT           PIC S9(9) COMP.
  public NInt32 applResult = new NInt32();

  // COB:           88 APPL-AOK                      VALUE 0.
  public boolean applAok() {
    return applResult.equals(0);
  }

  public void setApplAok(boolean value) {
    if (value) applResult.setValue(0);
  }

  // COB:           88 APPL-EOF                      VALUE 16.
  public boolean applEof() {
    return applResult.equals(16);
  }

  public void setApplEof(boolean value) {
    if (value) applResult.setValue(16);
  }

  // COB:        01 END-OF-FILE           PIC X(01)  VALUE 'N'.
  public NChar endOfFile = new NChar(1).initial("N");
  // COB:        01 ABCODE                PIC S9(9) BINARY.
  public NInt32 abcode = new NInt32();
  // COB:        01 TIMING                PIC S9(9) BINARY.
  public NInt32 timing = new NInt32();
}
