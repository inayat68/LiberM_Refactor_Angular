package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Dcltrtyp extends NGroup {

  // COB:        01  DCLTRANSACTION-TYPE.
  public static class DcltransactionType extends NGroup {
    // COB:            10 DCL-TR-TYPE          PIC X(2).
    public NChar dclTrType = new NChar(2);

    // COB:            10 DCL-TR-DESCRIPTION.
    public static class DclTrDescription extends NGroup {
      // COB:               49 DCL-TR-DESCRIPTION-LEN
      public NInt16 dclTrDescriptionLen = new NInt16();
      // COB:               49 DCL-TR-DESCRIPTION-TEXT
      public NChar dclTrDescriptionText = new NChar(50);
    }

    public DclTrDescription dclTrDescription = new DclTrDescription();
  }

  public DcltransactionType dcltransactionType = new DcltransactionType();
}
