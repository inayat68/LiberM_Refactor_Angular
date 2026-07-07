package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cvtra06y extends NGroup {

  // COB:        01  DALYTRAN-RECORD.
  public static class DalytranRecord extends NGroup {
    // COB:            05  DALYTRAN-ID                             PIC X(16).
    public NChar dalytranId = new NChar(16);
    // COB:            05  DALYTRAN-TYPE-CD                        PIC X(02).
    public NChar dalytranTypeCd = new NChar(2);
    // COB:            05  DALYTRAN-CAT-CD                         PIC 9(04).
    public NZoned dalytranCatCd = new NZoned(4, false);
    // COB:            05  DALYTRAN-SOURCE                         PIC X(10).
    public NChar dalytranSource = new NChar(10);
    // COB:            05  DALYTRAN-DESC                           PIC X(100).
    public NChar dalytranDesc = new NChar(100);
    // COB:            05  DALYTRAN-AMT                            PIC S9(09)V99.
    public NZoned dalytranAmt = new NZoned(11, 2);
    // COB:            05  DALYTRAN-MERCHANT-ID                    PIC 9(09).
    public NZoned dalytranMerchantId = new NZoned(9, false);
    // COB:            05  DALYTRAN-MERCHANT-NAME                  PIC X(50).
    public NChar dalytranMerchantName = new NChar(50);
    // COB:            05  DALYTRAN-MERCHANT-CITY                  PIC X(50).
    public NChar dalytranMerchantCity = new NChar(50);
    // COB:            05  DALYTRAN-MERCHANT-ZIP                   PIC X(10).
    public NChar dalytranMerchantZip = new NChar(10);
    // COB:            05  DALYTRAN-CARD-NUM                       PIC X(16).
    public NChar dalytranCardNum = new NChar(16);
    // COB:            05  DALYTRAN-ORIG-TS                        PIC X(26).
    public NChar dalytranOrigTs = new NChar(26);
    // COB:            05  DALYTRAN-PROC-TS                        PIC X(26).
    public NChar dalytranProcTs = new NChar(26);
    // COB:            05  FILLER                                  PIC X(20).
    public NChar filler18 = new NChar(20);
  }

  public DalytranRecord dalytranRecord = new DalytranRecord();
}
