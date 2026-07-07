package com.aws.carddemo.batch.program.storage.cbstm03b;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cbstm03bRecords extends NGroup {
  // COB:        01  FD-TRNXFILE-REC.
  public FdTrnxfileRec fdTrnxfileRec = new FdTrnxfileRec();

  public static class FdTrnxfileRec extends NGroup {

    // COB:            05 FD-TRNXS-ID.
    public static class FdTrnxsId extends NGroup {
      // COB:               10  FD-TRNX-CARD                  PIC X(16).
      public NChar fdTrnxCard = new NChar(16);
      // COB:               10  FD-TRNX-ID                    PIC X(16).
      public NChar fdTrnxId = new NChar(16);
    }

    public FdTrnxsId fdTrnxsId = new FdTrnxsId();
    // COB:            05 FD-ACCT-DATA                      PIC X(318).
    public NChar fdAcctData = new NChar(318);
  }

  // COB:        01  FD-XREFFILE-REC.
  public FdXreffileRec fdXreffileRec = new FdXreffileRec();

  public static class FdXreffileRec extends NGroup {

    // COB:            05 FD-XREF-CARD-NUM                  PIC X(16).
    public NChar fdXrefCardNum = new NChar(16);
    // COB:            05 FD-XREF-DATA                      PIC X(34).
    public NChar fdXrefData = new NChar(34);
  }

  // COB:        01  FD-CUSTFILE-REC.
  public FdCustfileRec fdCustfileRec = new FdCustfileRec();

  public static class FdCustfileRec extends NGroup {

    // COB:            05 FD-CUST-ID                        PIC X(09).
    public NChar fdCustId = new NChar(9);
    // COB:            05 FD-CUST-DATA                      PIC X(491).
    public NChar fdCustData = new NChar(491);
  }

  // COB:        01  FD-ACCTFILE-REC.
  public FdAcctfileRec fdAcctfileRec = new FdAcctfileRec();

  public static class FdAcctfileRec extends NGroup {

    // COB:            05 FD-ACCT-ID                        PIC 9(11).
    public NZoned fdAcctId = new NZoned(11, false);
    // COB:            05 FD-ACCT-DATA                      PIC X(289).
    public NChar fdAcctData = new NChar(289);
  }
}
