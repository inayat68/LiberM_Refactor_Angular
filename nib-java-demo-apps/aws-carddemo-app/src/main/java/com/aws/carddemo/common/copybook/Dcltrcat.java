package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Dcltrcat extends NGroup {

  // COB:        01  DCLTRANSACTION-TYPE-CATEGORY.
  public static class DcltransactionTypeCategory extends NGroup {
    // COB:            10 DCL-TRC-TYPE-CODE    PIC X(2).
    public NChar dclTrcTypeCode = new NChar(2);
    // COB:            10 DCL-TRC-TYPE-CATEGORY
    public NChar dclTrcTypeCategory = new NChar(4);

    // COB:            10 DCL-TRC-CAT-DATA.
    public static class DclTrcCatData extends NGroup {
      // COB:               49 DCL-TRC-CAT-DATA-LEN
      public NInt16 dclTrcCatDataLen = new NInt16();
      // COB:               49 DCL-TRC-CAT-DATA-TEXT
      public NChar dclTrcCatDataText = new NChar(50);
    }

    public DclTrcCatData dclTrcCatData = new DclTrcCatData();
  }

  public DcltransactionTypeCategory dcltransactionTypeCategory = new DcltransactionTypeCategory();
}
