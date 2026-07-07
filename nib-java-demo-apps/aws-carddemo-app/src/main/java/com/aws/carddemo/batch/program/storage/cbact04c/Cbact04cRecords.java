package com.aws.carddemo.batch.program.storage.cbact04c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cbact04cRecords extends NGroup {
  // COB:        01  FD-TRAN-CAT-BAL-RECORD.
  public FdTranCatBalRecord fdTranCatBalRecord = new FdTranCatBalRecord();

  public static class FdTranCatBalRecord extends NGroup {

    // COB:            05 FD-TRAN-CAT-KEY.
    public static class FdTranCatKey extends NGroup {
      // COB:               10 FD-TRANCAT-ACCT-ID             PIC 9(11).
      public NZoned fdTrancatAcctId = new NZoned(11, false);
      // COB:               10 FD-TRANCAT-TYPE-CD             PIC X(02).
      public NChar fdTrancatTypeCd = new NChar(2);
      // COB:               10 FD-TRANCAT-CD                  PIC 9(04).
      public NZoned fdTrancatCd = new NZoned(4, false);
    }

    public FdTranCatKey fdTranCatKey = new FdTranCatKey();
    // COB:            05 FD-FD-TRAN-CAT-DATA               PIC X(33).
    public NChar fdFdTranCatData = new NChar(33);
  }

  // COB:        01  FD-XREFFILE-REC.
  public FdXreffileRec fdXreffileRec = new FdXreffileRec();

  public static class FdXreffileRec extends NGroup {

    // COB:            05 FD-XREF-CARD-NUM                  PIC X(16).
    public NChar fdXrefCardNum = new NChar(16);
    // COB:            05 FD-XREF-CUST-NUM                  PIC 9(09).
    public NZoned fdXrefCustNum = new NZoned(9, false);
    // COB:            05 FD-XREF-ACCT-ID                   PIC 9(11).
    public NZoned fdXrefAcctId = new NZoned(11, false);
    // COB:            05 FD-XREF-FILLER                    PIC X(14).
    public NChar fdXrefFiller = new NChar(14);
  }

  // COB:        01  FD-DISCGRP-REC.
  public FdDiscgrpRec fdDiscgrpRec = new FdDiscgrpRec();

  public static class FdDiscgrpRec extends NGroup {

    // COB:            05 FD-DISCGRP-KEY.
    public static class FdDiscgrpKey extends NGroup {
      // COB:               10 FD-DIS-ACCT-GROUP-ID           PIC X(10).
      public NChar fdDisAcctGroupId = new NChar(10);
      // COB:               10 FD-DIS-TRAN-TYPE-CD            PIC X(02).
      public NChar fdDisTranTypeCd = new NChar(2);
      // COB:               10 FD-DIS-TRAN-CAT-CD             PIC 9(04).
      public NZoned fdDisTranCatCd = new NZoned(4, false);
    }

    public FdDiscgrpKey fdDiscgrpKey = new FdDiscgrpKey();
    // COB:            05 FD-DISCGRP-DATA                   PIC X(34).
    public NChar fdDiscgrpData = new NChar(34);
  }

  // COB:        01  FD-ACCTFILE-REC.
  public FdAcctfileRec fdAcctfileRec = new FdAcctfileRec();

  public static class FdAcctfileRec extends NGroup {

    // COB:            05 FD-ACCT-ID                        PIC 9(11).
    public NZoned fdAcctId = new NZoned(11, false);
    // COB:            05 FD-ACCT-DATA                      PIC X(289).
    public NChar fdAcctData = new NChar(289);
  }

  // COB:        01  FD-TRANFILE-REC.
  public FdTranfileRec fdTranfileRec = new FdTranfileRec();

  public static class FdTranfileRec extends NGroup {

    // COB:            05 FD-TRANS-ID                       PIC X(16).
    public NChar fdTransId = new NChar(16);
    // COB:            05 FD-ACCT-DATA                      PIC X(334).
    public NChar fdAcctData = new NChar(334);
  }
}
