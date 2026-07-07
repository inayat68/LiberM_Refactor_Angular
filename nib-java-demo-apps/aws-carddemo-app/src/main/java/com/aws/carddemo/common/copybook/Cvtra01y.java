package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cvtra01y extends NGroup {

  // COB:        01  TRAN-CAT-BAL-RECORD.
  public static class TranCatBalRecord extends NGroup {
    // COB:            05  TRAN-CAT-KEY.
    public static class TranCatKey extends NGroup {
      // COB:               10 TRANCAT-ACCT-ID                       PIC 9(11).
      public NZoned trancatAcctId = new NZoned(11, false);
      // COB:               10 TRANCAT-TYPE-CD                       PIC X(02).
      public NChar trancatTypeCd = new NChar(2);
      // COB:               10 TRANCAT-CD                            PIC 9(04).
      public NZoned trancatCd = new NZoned(4, false);
    }

    public TranCatKey tranCatKey = new TranCatKey();
    // COB:            05  TRAN-CAT-BAL                            PIC S9(09)V99.
    public NZoned tranCatBal = new NZoned(11, 2);
    // COB:            05  FILLER                                  PIC X(22).
    public NChar filler10 = new NChar(22);
  }

  public TranCatBalRecord tranCatBalRecord = new TranCatBalRecord();
}
