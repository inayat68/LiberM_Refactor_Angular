package com.aws.carddemo.online.program.storage.iristrac;

import com.aws.carddemo.common.copybook.Iriscomm;
import com.nib.commons.storage.*;

public class IristracWorking extends NGroup {
  public Iriscomm iriscomm = new Iriscomm();
  // COB:        01 WS-LILIAN                       PIC S9(9) COMP.
  public NInt32 wsLilian = new NInt32();
  // COB:        01 WS-SECONDS                      PIC S9(18) COMP.
  public NInt64 wsSeconds = new NInt64();
  // COB:        01 WS-GREGORN                      PIC X(17).
  public NChar wsGregorn = new NChar(17);
  // COB:        01 WS-FC.
  public WsFc wsFc = new WsFc();

  public static class WsFc extends NGroup {

    // COB:           03 WS-CEEIGZCT-RC               PIC X.
    public NChar wsCeeigzctRc = new NChar(1);

    // COB:              88 WS-CEE000                 VALUE LOW-VALUE.
    public boolean wsCee000() {
      return wsCeeigzctRc.isLowValues();
    }

    public void setWsCee000(boolean value) {
      if (value) wsCeeigzctRc.setValue(LOW);
    }

    // COB:           03 FILLER                       PIC X(11).
    public NChar filler26 = new NChar(11);
  }

  // COB:        01 WS-MAX-LEN                      PIC S9(9) COMP.
  public NInt32 wsMaxLen = new NInt32();
  // COB:        01 WS-LINE-LEN                     PIC S9(9) COMP.
  public NInt32 wsLineLen = new NInt32();
  // COB:        01 WS-LINE-TXT                     PIC X(4096).
  public NChar wsLineTxt = new NChar(4096);
  // COB:        01 WS-LINE-POS                     PIC S9(9) COMP.
  public NInt32 wsLinePos = new NInt32();
  // COB:        01 WS-LINE-PLEN                    PIC S9(9) COMP.
  public NInt32 wsLinePlen = new NInt32();
  // COB:        01 WS-IDX                          PIC S9(9) COMP.
  public NInt32 wsIdx = new NInt32();
  // COB:        01 FILLER                          PIC 9              VALUE 0.
  public NZoned filler37 = new NZoned(1, false);

  // COB:          88 NO-SHIFT-LINE                                    VALUE 0.
  public boolean noShiftLine() {
    return filler37.equals(0);
  }

  public void setNoShiftLine(boolean value) {
    if (value) filler37.setValue(0);
  }

  // COB:          88 SHIFT-LINE                                       VALUE 1.
  public boolean shiftLine() {
    return filler37.equals(1);
  }

  public void setShiftLine(boolean value) {
    if (value) filler37.setValue(1);
  }

  // COB:        01 WS-BIN-BIGEND                   PIC S9(4) COMP     VALUE 1.
  public NInt16 wsBinBigend = new NInt16().initial(1);
  // COB:        01 WS-BIN-LITEND REDEFINES WS-BIN-BIGEND
  public NInt16 wsBinLitend = new NInt16().redefines(wsBinBigend);

  // COB:          88 IS-ENV-BIGENDIAN                                 VALUE 1.
  public boolean isEnvBigendian() {
    return wsBinLitend.equals(1);
  }

  public void setIsEnvBigendian(boolean value) {
    if (value) wsBinLitend.setValue(1);
  }
}
