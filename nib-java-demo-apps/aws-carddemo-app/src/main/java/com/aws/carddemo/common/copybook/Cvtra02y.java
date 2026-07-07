package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cvtra02y extends NGroup {

  // COB:        01  DIS-GROUP-RECORD.
  public static class DisGroupRecord extends NGroup {
    // COB:            05  DIS-GROUP-KEY.
    public static class DisGroupKey extends NGroup {
      // COB:               10 DIS-ACCT-GROUP-ID                     PIC X(10).
      public NChar disAcctGroupId = new NChar(10);
      // COB:               10 DIS-TRAN-TYPE-CD                      PIC X(02).
      public NChar disTranTypeCd = new NChar(2);
      // COB:               10 DIS-TRAN-CAT-CD                       PIC 9(04).
      public NZoned disTranCatCd = new NZoned(4, false);
    }

    public DisGroupKey disGroupKey = new DisGroupKey();
    // COB:            05  DIS-INT-RATE                            PIC S9(04)V99.
    public NZoned disIntRate = new NZoned(6, 2);
    // COB:            05  FILLER                                  PIC X(28).
    public NChar filler10 = new NChar(28);
  }

  public DisGroupRecord disGroupRecord = new DisGroupRecord();
}
