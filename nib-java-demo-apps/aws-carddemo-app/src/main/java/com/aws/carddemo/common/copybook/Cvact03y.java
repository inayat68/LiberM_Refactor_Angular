package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cvact03y extends NGroup {

  // COB:        01 CARD-XREF-RECORD.
  public static class CardXrefRecord extends NGroup {
    // COB:            05  XREF-CARD-NUM                     PIC X(16).
    public NChar xrefCardNum = new NChar(16);
    // COB:            05  XREF-CUST-ID                      PIC 9(09).
    public NZoned xrefCustId = new NZoned(9, false);
    // COB:            05  XREF-ACCT-ID                      PIC 9(11).
    public NZoned xrefAcctId = new NZoned(11, false);
    // COB:            05  FILLER                            PIC X(14).
    public NChar filler8 = new NChar(14);
  }

  public CardXrefRecord cardXrefRecord = new CardXrefRecord();
}
