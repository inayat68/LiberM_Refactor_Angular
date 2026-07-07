package com.aws.carddemo.batch.program.storage.cbtrn02c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Cvact01y;
import com.aws.carddemo.common.copybook.Cvact03y;
import com.aws.carddemo.common.copybook.Cvtra01y;
import com.aws.carddemo.common.copybook.Cvtra05y;
import com.aws.carddemo.common.copybook.Cvtra06y;
import com.nib.commons.storage.*;

public class Cbtrn02cWorking extends NGroup {
  public Cvtra06y cvtra06y = new Cvtra06y();
  // COB:        01  DALYTRAN-STATUS.
  public DalytranStatus dalytranStatus = new DalytranStatus();

  public static class DalytranStatus extends NGroup {

    // COB:            05  DALYTRAN-STAT1      PIC X.
    public NChar dalytranStat1 = new NChar(1);
    // COB:            05  DALYTRAN-STAT2      PIC X.
    public NChar dalytranStat2 = new NChar(1);
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

  public Cvact03y cvact03y = new Cvact03y();
  // COB:        01  XREFFILE-STATUS.
  public XreffileStatus xreffileStatus = new XreffileStatus();

  public static class XreffileStatus extends NGroup {

    // COB:            05  XREFFILE-STAT1      PIC X.
    public NChar xreffileStat1 = new NChar(1);
    // COB:            05  XREFFILE-STAT2      PIC X.
    public NChar xreffileStat2 = new NChar(1);
  }

  // COB:        01  DALYREJS-STATUS.
  public DalyrejsStatus dalyrejsStatus = new DalyrejsStatus();

  public static class DalyrejsStatus extends NGroup {

    // COB:            05  DALYREJS-STAT1      PIC X.
    public NChar dalyrejsStat1 = new NChar(1);
    // COB:            05  DALYREJS-STAT2      PIC X.
    public NChar dalyrejsStat2 = new NChar(1);
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

  public Cvtra01y cvtra01y = new Cvtra01y();
  // COB:        01  TCATBALF-STATUS.
  public TcatbalfStatus tcatbalfStatus = new TcatbalfStatus();

  public static class TcatbalfStatus extends NGroup {

    // COB:            05  TCATBALF-STAT1      PIC X.
    public NChar tcatbalfStat1 = new NChar(1);
    // COB:            05  TCATBALF-STAT2      PIC X.
    public NChar tcatbalfStat2 = new NChar(1);
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

  // COB:        01  END-OF-FILE             PIC X(01)    VALUE 'N'.
  public NChar endOfFile = new NChar(1).initial("N");
  // COB:        01  ABCODE                  PIC S9(9) BINARY.
  public NInt32 abcode = new NInt32();
  // COB:        01  TIMING                  PIC S9(9) BINARY.
  public NInt32 timing = new NInt32();
  // COB:        01  COBOL-TS.
  public CobolTs cobolTs = new CobolTs();

  public static class CobolTs extends NGroup {

    // COB:            05 COB-YYYY                  PIC X(04).
    public NChar cobYyyy = new NChar(4);
    // COB:            05 COB-MM                    PIC X(02).
    public NChar cobMm = new NChar(2);
    // COB:            05 COB-DD                    PIC X(02).
    public NChar cobDd = new NChar(2);
    // COB:            05 COB-HH                    PIC X(02).
    public NChar cobHh = new NChar(2);
    // COB:            05 COB-MIN                   PIC X(02).
    public NChar cobMin = new NChar(2);
    // COB:            05 COB-SS                    PIC X(02).
    public NChar cobSs = new NChar(2);
    // COB:            05 COB-MIL                   PIC X(02).
    public NChar cobMil = new NChar(2);
    // COB:            05 COB-REST                  PIC X(05).
    public NChar cobRest = new NChar(5);
  }

  // COB:        01  DB2-FORMAT-TS                PIC X(26).
  public NChar db2FormatTs = new NChar(26);

  // COB:        01  FILLER REDEFINES DB2-FORMAT-TS.
  public static class Filler160 extends NGroup {
    // COB:            06 DB2-YYYY                  PIC X(004).
    public NChar db2Yyyy = new NChar(4);
    // COB:            06 DB2-STREEP-1              PIC X.
    public NChar db2Streep1 = new NChar(1);
    // COB:            06 DB2-MM                    PIC X(002).
    public NChar db2Mm = new NChar(2);
    // COB:            06 DB2-STREEP-2              PIC X.
    public NChar db2Streep2 = new NChar(1);
    // COB:            06 DB2-DD                    PIC X(002).
    public NChar db2Dd = new NChar(2);
    // COB:            06 DB2-STREEP-3              PIC X.
    public NChar db2Streep3 = new NChar(1);
    // COB:            06 DB2-HH                    PIC X(002).
    public NChar db2Hh = new NChar(2);
    // COB:            06 DB2-DOT-1                 PIC X.
    public NChar db2Dot1 = new NChar(1);
    // COB:            06 DB2-MIN                   PIC X(002).
    public NChar db2Min = new NChar(2);
    // COB:            06 DB2-DOT-2                 PIC X.
    public NChar db2Dot2 = new NChar(1);
    // COB:            06 DB2-SS                    PIC X(002).
    public NChar db2Ss = new NChar(2);
    // COB:            06 DB2-DOT-3                 PIC X.
    public NChar db2Dot3 = new NChar(1);
    // COB:            06 DB2-MIL                   PIC 9(002).
    public NZoned db2Mil = new NZoned(2, false);
    // COB:            06 DB2-REST                  PIC X(04).
    public NChar db2Rest = new NChar(4);
  }

  public Filler160 filler160 = (Filler160) new Filler160().redefines(db2FormatTs);
  // COB:         01 REJECT-RECORD.
  public RejectRecord rejectRecord = new RejectRecord();

  public static class RejectRecord extends NGroup {

    // COB:            05 REJECT-TRAN-DATA          PIC X(350).
    public NChar rejectTranData = new NChar(350);
    // COB:            05 VALIDATION-TRAILER        PIC X(80).
    public NChar validationTrailer = new NChar(80);
  }

  // COB:         01 WS-VALIDATION-TRAILER.
  public WsValidationTrailer wsValidationTrailer = new WsValidationTrailer();

  public static class WsValidationTrailer extends NGroup {

    // COB:            05 WS-VALIDATION-FAIL-REASON      PIC 9(04).
    public NZoned wsValidationFailReason = new NZoned(4, false);
    // COB:            05 WS-VALIDATION-FAIL-REASON-DESC PIC X(76).
    public NChar wsValidationFailReasonDesc = new NChar(76);
  }

  // COB:         01 WS-COUNTERS.
  public WsCounters wsCounters = new WsCounters();

  public static class WsCounters extends NGroup {

    // COB:            05 WS-TRANSACTION-COUNT          PIC 9(09) VALUE 0.
    public NZoned wsTransactionCount = new NZoned(9, false).initial(0);
    // COB:            05 WS-REJECT-COUNT               PIC 9(09) VALUE 0.
    public NZoned wsRejectCount = new NZoned(9, false).initial(0);
    // COB:            05 WS-TEMP-BAL                   PIC S9(09)V99.
    public NZoned wsTempBal = new NZoned(11, 2);
  }

  // COB:         01 WS-FLAGS.
  public WsFlags wsFlags = new WsFlags();

  public static class WsFlags extends NGroup {

    // COB:            05 WS-CREATE-TRANCAT-REC         PIC X(01) VALUE 'N'.
    public NChar wsCreateTrancatRec = new NChar(1).initial("N");
  }
}
