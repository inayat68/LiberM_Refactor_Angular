package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cvtra04y extends NGroup {

  // COB:        01  TRAN-CAT-RECORD.
  public static class TranCatRecord extends NGroup {
    // COB:            05  TRAN-CAT-KEY.
    public static class TranCatKey extends NGroup {
      // COB:               10  TRAN-TYPE-CD                         PIC X(02).
      public NChar tranTypeCd = new NChar(2);
      // COB:               10  TRAN-CAT-CD                          PIC 9(04).
      public NZoned tranCatCd = new NZoned(4, false);
    }

    public TranCatKey tranCatKey = new TranCatKey();
    // COB:            05  TRAN-CAT-TYPE-DESC                      PIC X(50).
    public NChar tranCatTypeDesc = new NChar(50);
    // COB:            05  FILLER                                  PIC X(04).
    public NChar filler9 = new NChar(4);
  }

  public TranCatRecord tranCatRecord = new TranCatRecord();
}
