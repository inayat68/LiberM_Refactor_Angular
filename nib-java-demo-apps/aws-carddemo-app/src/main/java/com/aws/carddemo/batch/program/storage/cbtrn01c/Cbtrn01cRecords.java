package com.aws.carddemo.batch.program.storage.cbtrn01c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cbtrn01cRecords extends NGroup {
  // COB:        01  FD-TRAN-RECORD.
  public FdTranRecord fdTranRecord = new FdTranRecord();

  public static class FdTranRecord extends NGroup {

    // COB:            05 FD-TRAN-ID                        PIC X(16).
    public NChar fdTranId = new NChar(16);
    // COB:            05 FD-CUST-DATA                      PIC X(334).
    public NChar fdCustData = new NChar(334);
  }

  // COB:        01  FD-CUSTFILE-REC.
  public FdCustfileRec fdCustfileRec = new FdCustfileRec();

  public static class FdCustfileRec extends NGroup {

    // COB:            05 FD-CUST-ID                        PIC 9(09).
    public NZoned fdCustId = new NZoned(9, false);
    // COB:            05 FD-CUST-DATA                      PIC X(491).
    public NChar fdCustData = new NChar(491);
  }

  // COB:        01  FD-XREFFILE-REC.
  public FdXreffileRec fdXreffileRec = new FdXreffileRec();

  public static class FdXreffileRec extends NGroup {

    // COB:            05 FD-XREF-CARD-NUM                  PIC X(16).
    public NChar fdXrefCardNum = new NChar(16);
    // COB:            05 FD-XREF-DATA                      PIC X(34).
    public NChar fdXrefData = new NChar(34);
  }

  // COB:        01  FD-CARDFILE-REC.
  public FdCardfileRec fdCardfileRec = new FdCardfileRec();

  public static class FdCardfileRec extends NGroup {

    // COB:            05 FD-CARD-NUM                       PIC X(16).
    public NChar fdCardNum = new NChar(16);
    // COB:            05 FD-CARD-DATA                      PIC X(134).
    public NChar fdCardData = new NChar(134);
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
