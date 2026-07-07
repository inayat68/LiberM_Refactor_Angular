package com.aws.carddemo.batch.program.storage.cbtrn01c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Cvact01y;
import com.aws.carddemo.common.copybook.Cvact02y;
import com.aws.carddemo.common.copybook.Cvact03y;
import com.aws.carddemo.common.copybook.Cvcus01y;
import com.aws.carddemo.common.copybook.Cvtra05y;
import com.aws.carddemo.common.copybook.Cvtra06y;
import com.nib.commons.storage.*;

public class Cbtrn01cWorking extends NGroup {
  public Cvtra06y cvtra06y = new Cvtra06y();
  // COB:        01  DALYTRAN-STATUS.
  public DalytranStatus dalytranStatus = new DalytranStatus();

  public static class DalytranStatus extends NGroup {

    // COB:            05  DALYTRAN-STAT1      PIC X.
    public NChar dalytranStat1 = new NChar(1);
    // COB:            05  DALYTRAN-STAT2      PIC X.
    public NChar dalytranStat2 = new NChar(1);
  }

  public Cvcus01y cvcus01y = new Cvcus01y();
  // COB:        01  CUSTFILE-STATUS.
  public CustfileStatus custfileStatus = new CustfileStatus();

  public static class CustfileStatus extends NGroup {

    // COB:            05  CUSTFILE-STAT1      PIC X.
    public NChar custfileStat1 = new NChar(1);
    // COB:            05  CUSTFILE-STAT2      PIC X.
    public NChar custfileStat2 = new NChar(1);
  }

  public Cvact03y cvact03y = new Cvact03y();
  // COB:        01  XREFFILE-STATUS.
  public XreffileStatus xreffileStatus = new XreffileStatus();

  public static class XreffileStatus extends NGroup {

    // COB:            05  XREFFILE-STAT1      PIC X.
    public NChar xreffileStat1 = new NChar(1);
    // COB:            05  XREFFILE-STAT2      PIC X.
    public NChar xreffileStat2 = new NChar(1);
  }

  public Cvact02y cvact02y = new Cvact02y();
  // COB:        01  CARDFILE-STATUS.
  public CardfileStatus cardfileStatus = new CardfileStatus();

  public static class CardfileStatus extends NGroup {

    // COB:            05  CARDFILE-STAT1      PIC X.
    public NChar cardfileStat1 = new NChar(1);
    // COB:            05  CARDFILE-STAT2      PIC X.
    public NChar cardfileStat2 = new NChar(1);
  }

  public Cvact01y cvact01y = new Cvact01y();
  // COB:        01  ACCTFILE-STATUS.
  public AcctfileStatus acctfileStatus = new AcctfileStatus();

  public static class AcctfileStatus extends NGroup {

    // COB:            05  ACCTFILE-STAT1      PIC X.
    public NChar acctfileStat1 = new NChar(1);
    // COB:            05  ACCTFILE-STAT2      PIC X.
    public NChar acctfileStat2 = new NChar(1);
  }

  public Cvtra05y cvtra05y = new Cvtra05y();
  // COB:        01  TRANFILE-STATUS.
  public TranfileStatus tranfileStatus = new TranfileStatus();

  public static class TranfileStatus extends NGroup {

    // COB:            05  TRANFILE-STAT1      PIC X.
    public NChar tranfileStat1 = new NChar(1);
    // COB:            05  TRANFILE-STAT2      PIC X.
    public NChar tranfileStat2 = new NChar(1);
  }

  // COB:        01  IO-STATUS.
  public IoStatus ioStatus = new IoStatus();

  public static class IoStatus extends NGroup {

    // COB:            05  IO-STAT1            PIC X.
    public NChar ioStat1 = new NChar(1);
    // COB:            05  IO-STAT2            PIC X.
    public NChar ioStat2 = new NChar(1);
  }

  // COB:        01  TWO-BYTES-BINARY        PIC 9(4) BINARY.
  public NInt16 twoBytesBinary = new NInt16();

  // COB:        01  TWO-BYTES-ALPHA         REDEFINES TWO-BYTES-BINARY.
  public static class TwoBytesAlpha extends NGroup {
    // COB:            05  TWO-BYTES-LEFT      PIC X.
    public NChar twoBytesLeft = new NChar(1);
    // COB:            05  TWO-BYTES-RIGHT     PIC X.
    public NChar twoBytesRight = new NChar(1);
  }

  public TwoBytesAlpha twoBytesAlpha =
      (TwoBytesAlpha) new TwoBytesAlpha().redefines(twoBytesBinary);
  // COB:        01  IO-STATUS-04.
  public IoStatus04 ioStatus04 = new IoStatus04();

  public static class IoStatus04 extends NGroup {

    // COB:            05  IO-STATUS-0401      PIC 9   VALUE 0.
    public NZoned ioStatus0401 = new NZoned(1, false).initial(0);
    // COB:            05  IO-STATUS-0403      PIC 999 VALUE 0.
    public NZoned ioStatus0403 = new NZoned(3, false).initial(0);
  }

  // COB:        01  APPL-RESULT             PIC S9(9)   COMP.
  public NInt32 applResult = new NInt32();

  // COB:            88  APPL-AOK            VALUE 0.
  public boolean applAok() {
    return applResult.equals(0);
  }

  public void setApplAok(boolean value) {
    if (value) applResult.setValue(0);
  }

  // COB:            88  APPL-EOF            VALUE 16.
  public boolean applEof() {
    return applResult.equals(16);
  }

  public void setApplEof(boolean value) {
    if (value) applResult.setValue(16);
  }

  // COB:        01  END-OF-DAILY-TRANS-FILE             PIC X(01)    VALUE 'N'.
  public NChar endOfDailyTransFile = new NChar(1).initial("N");
  // COB:        01  ABCODE                  PIC S9(9) BINARY.
  public NInt32 abcode = new NInt32();
  // COB:        01  TIMING                  PIC S9(9) BINARY.
  public NInt32 timing = new NInt32();
  // COB:        01  WS-MISC-VARIABLES.
  public WsMiscVariables wsMiscVariables = new WsMiscVariables();

  public static class WsMiscVariables extends NGroup {

    // COB:            05 WS-XREF-READ-STATUS  PIC 9(04).
    public NZoned wsXrefReadStatus = new NZoned(4, false);
    // COB:            05 WS-ACCT-READ-STATUS  PIC 9(04).
    public NZoned wsAcctReadStatus = new NZoned(4, false);
  }
}
