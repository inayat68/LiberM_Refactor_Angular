package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cvact02y extends NGroup {

  // COB:        01  CARD-RECORD.
  public static class CardRecord extends NGroup {
    // COB:            05  CARD-NUM                          PIC X(16).
    public NChar cardNum = new NChar(16);
    // COB:            05  CARD-ACCT-ID                      PIC 9(11).
    public NZoned cardAcctId = new NZoned(11, false);
    // COB:            05  CARD-CVV-CD                       PIC 9(03).
    public NZoned cardCvvCd = new NZoned(3, false);
    // COB:            05  CARD-EMBOSSED-NAME                PIC X(50).
    public NChar cardEmbossedName = new NChar(50);
    // COB:            05  CARD-EXPIRAION-DATE               PIC X(10).
    public NChar cardExpiraionDate = new NChar(10);
    // COB:            05  CARD-ACTIVE-STATUS                PIC X(01).
    public NChar cardActiveStatus = new NChar(1);
    // COB:            05  FILLER                            PIC X(59).
    public NChar filler11 = new NChar(59);
  }

  public CardRecord cardRecord = new CardRecord();
}
