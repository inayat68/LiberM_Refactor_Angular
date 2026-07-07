package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cvtra03y extends NGroup {

  // COB:        01  TRAN-TYPE-RECORD.
  public static class TranTypeRecord extends NGroup {
    // COB:            05  TRAN-TYPE                               PIC X(02).
    public NChar tranType = new NChar(2);
    // COB:            05  TRAN-TYPE-DESC                          PIC X(50).
    public NChar tranTypeDesc = new NChar(50);
    // COB:            05  FILLER                                  PIC X(08).
    public NChar filler7 = new NChar(8);
  }

  public TranTypeRecord tranTypeRecord = new TranTypeRecord();
}
