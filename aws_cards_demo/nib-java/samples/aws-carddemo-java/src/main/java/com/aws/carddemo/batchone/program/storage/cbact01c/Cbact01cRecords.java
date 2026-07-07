package com.aws.carddemo.batchone.program.storage.cbact01c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cbact01cRecords extends NGroup {
  // COB:        01  FD-ACCTFILE-REC.
  public FdAcctfileRec fdAcctfileRec = new FdAcctfileRec();

  public static class FdAcctfileRec extends NGroup {

    // COB:            05 FD-ACCT-ID                        PIC 9(11).
    public NZoned fdAcctId = new NZoned(11, false);
    // COB:            05 FD-ACCT-DATA                      PIC X(289).
    public NChar fdAcctData = new NChar(289);
  }
}
