package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cvtra05y extends NGroup {

  // COB:        01  TRAN-RECORD.
  public static class TranRecord extends NGroup {
    // COB:            05  TRAN-ID                                 PIC X(16).
    public NChar tranId = new NChar(16);
    // COB:            05  TRAN-TYPE-CD                            PIC X(02).
    public NChar tranTypeCd = new NChar(2);
    // COB:            05  TRAN-CAT-CD                             PIC 9(04).
    public NZoned tranCatCd = new NZoned(4, false);
    // COB:            05  TRAN-SOURCE                             PIC X(10).
    public NChar tranSource = new NChar(10);
    // COB:            05  TRAN-DESC                               PIC X(100).
    public NChar tranDesc = new NChar(100);
    // COB:            05  TRAN-AMT                                PIC S9(09)V99.
    public NZoned tranAmt = new NZoned(11, 2);
    // COB:            05  TRAN-MERCHANT-ID                        PIC 9(09).
    public NZoned tranMerchantId = new NZoned(9, false);
    // COB:            05  TRAN-MERCHANT-NAME                      PIC X(50).
    public NChar tranMerchantName = new NChar(50);
    // COB:            05  TRAN-MERCHANT-CITY                      PIC X(50).
    public NChar tranMerchantCity = new NChar(50);
    // COB:            05  TRAN-MERCHANT-ZIP                       PIC X(10).
    public NChar tranMerchantZip = new NChar(10);
    // COB:            05  TRAN-CARD-NUM                           PIC X(16).
    public NChar tranCardNum = new NChar(16);
    // COB:            05  TRAN-ORIG-TS                            PIC X(26).
    public NChar tranOrigTs = new NChar(26);
    // COB:            05  TRAN-PROC-TS                            PIC X(26).
    public NChar tranProcTs = new NChar(26);
    // COB:            05  FILLER                                  PIC X(20).
    public NChar filler18 = new NChar(20);
  }

  public TranRecord tranRecord = new TranRecord();
}
