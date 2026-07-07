package com.aws.carddemo.batch.program.storage.cbact02c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Cvact02y;
import com.nib.commons.storage.*;

public class Cbact02cWorking extends NGroup {
  public Cvact02y cvact02y = new Cvact02y();
  // COB:        01  CARDFILE-STATUS.
  public CardfileStatus cardfileStatus = new CardfileStatus();

  public static class CardfileStatus extends NGroup {

    // COB:            05  CARDFILE-STAT1      PIC X.
    public NChar cardfileStat1 = new NChar(1);
    // COB:            05  CARDFILE-STAT2      PIC X.
    public NChar cardfileStat2 = new NChar(1);
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
}
