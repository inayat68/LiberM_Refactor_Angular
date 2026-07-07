package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Costm01 extends NGroup {

  // COB:        01  TRNX-RECORD.
  public static class TrnxRecord extends NGroup {
    // COB:            05  TRNX-KEY.
    public static class TrnxKey extends NGroup {
      // COB:                10  TRNX-CARD-NUM                       PIC X(16).
      public NChar trnxCardNum = new NChar(16);
      // COB:                10  TRNX-ID                             PIC X(16).
      public NChar trnxId = new NChar(16);
    }

    public TrnxKey trnxKey = new TrnxKey();

    // COB:            05  TRNX-REST.
    public static class TrnxRest extends NGroup {
      // COB:                10  TRNX-TYPE-CD                        PIC X(02).
      public NChar trnxTypeCd = new NChar(2);
      // COB:                10  TRNX-CAT-CD                         PIC 9(04).
      public NZoned trnxCatCd = new NZoned(4, false);
      // COB:                10  TRNX-SOURCE                         PIC X(10).
      public NChar trnxSource = new NChar(10);
      // COB:                10  TRNX-DESC                           PIC X(100).
      public NChar trnxDesc = new NChar(100);
      // COB:                10  TRNX-AMT                            PIC S9(09)V99.
      public NZoned trnxAmt = new NZoned(11, 2);
      // COB:                10  TRNX-MERCHANT-ID                    PIC 9(09).
      public NZoned trnxMerchantId = new NZoned(9, false);
      // COB:                10  TRNX-MERCHANT-NAME                  PIC X(50).
      public NChar trnxMerchantName = new NChar(50);
      // COB:                10  TRNX-MERCHANT-CITY                  PIC X(50).
      public NChar trnxMerchantCity = new NChar(50);
      // COB:                10  TRNX-MERCHANT-ZIP                   PIC X(10).
      public NChar trnxMerchantZip = new NChar(10);
      // COB:                10  TRNX-ORIG-TS                        PIC X(26).
      public NChar trnxOrigTs = new NChar(26);
      // COB:                10  TRNX-PROC-TS                        PIC X(26).
      public NChar trnxProcTs = new NChar(26);
      // COB:                10  FILLER                              PIC X(20).
      public NChar filler36 = new NChar(20);
    }

    public TrnxRest trnxRest = new TrnxRest();
  }

  public TrnxRecord trnxRecord = new TrnxRecord();
}
