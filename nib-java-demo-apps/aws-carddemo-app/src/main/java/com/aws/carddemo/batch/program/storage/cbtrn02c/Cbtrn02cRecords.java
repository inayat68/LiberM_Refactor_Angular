package com.aws.carddemo.batch.program.storage.cbtrn02c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cbtrn02cRecords extends NGroup {
  // COB:        01  FD-TRAN-RECORD.
  public FdTranRecord fdTranRecord = new FdTranRecord();

  public static class FdTranRecord extends NGroup {

    // COB:            05 FD-TRAN-ID                        PIC X(16).
    public NChar fdTranId = new NChar(16);
    // COB:            05 FD-CUST-DATA                      PIC X(334).
    public NChar fdCustData = new NChar(334);
  }

  // COB:        01  FD-TRANFILE-REC.
  public FdTranfileRec fdTranfileRec = new FdTranfileRec();

  public static class FdTranfileRec extends NGroup {

    // COB:            05 FD-TRANS-ID                       PIC X(16).
    public NChar fdTransId = new NChar(16);
    // COB:            05 FD-ACCT-DATA                      PIC X(334).
    public NChar fdAcctData = new NChar(334);
  }

  // COB:        01  FD-XREFFILE-REC.
  public FdXreffileRec fdXreffileRec = new FdXreffileRec();

  public static class FdXreffileRec extends NGroup {

    // COB:            05 FD-XREF-CARD-NUM                  PIC X(16).
    public NChar fdXrefCardNum = new NChar(16);
    // COB:            05 FD-XREF-DATA                      PIC X(34).
    public NChar fdXrefData = new NChar(34);
  }

  // COB:        01  FD-REJS-RECORD.
  public FdRejsRecord fdRejsRecord = new FdRejsRecord();

  public static class FdRejsRecord extends NGroup {

    // COB:            05 FD-REJECT-RECORD                  PIC X(350).
    public NChar fdRejectRecord = new NChar(350);
    // COB:            05 FD-VALIDATION-TRAILER             PIC X(80).
    public NChar fdValidationTrailer = new NChar(80);
  }

  // COB:        01  FD-ACCTFILE-REC.
  public FdAcctfileRec fdAcctfileRec = new FdAcctfileRec();

  public static class FdAcctfileRec extends NGroup {

    // COB:            05 FD-ACCT-ID                        PIC 9(11).
    public NZoned fdAcctId = new NZoned(11, false);
    // COB:            05 FD-ACCT-DATA                      PIC X(289).
    public NChar fdAcctData = new NChar(289);
  }

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
}
