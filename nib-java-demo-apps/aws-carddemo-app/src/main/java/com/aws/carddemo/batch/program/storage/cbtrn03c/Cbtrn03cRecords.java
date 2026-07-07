package com.aws.carddemo.batch.program.storage.cbtrn03c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cbtrn03cRecords extends NGroup {
  // COB:        01 FD-TRANFILE-REC.
  public FdTranfileRec fdTranfileRec = new FdTranfileRec();

  public static class FdTranfileRec extends NGroup {

    // COB:           05 FD-TRANS-DATA      PIC X(304).
    public NChar fdTransData = new NChar(304);
    // COB:           05 FD-TRAN-PROC-TS    PIC X(26).
    public NChar fdTranProcTs = new NChar(26);
    // COB:           05 FD-FILLER          PIC X(20).
    public NChar fdFiller = new NChar(20);
  }

  // COB:        01  FD-CARDXREF-REC.
  public FdCardxrefRec fdCardxrefRec = new FdCardxrefRec();

  public static class FdCardxrefRec extends NGroup {

    // COB:            05 FD-XREF-CARD-NUM                  PIC X(16).
    public NChar fdXrefCardNum = new NChar(16);
    // COB:            05 FD-XREF-DATA                      PIC X(34).
    public NChar fdXrefData = new NChar(34);
  }

  // COB:        01 FD-TRANTYPE-REC.
  public FdTrantypeRec fdTrantypeRec = new FdTrantypeRec();

  public static class FdTrantypeRec extends NGroup {

    // COB:           05 FD-TRAN-TYPE       PIC X(02).
    public NChar fdTranType = new NChar(2);
    // COB:           05 FD-TRAN-DATA       PIC X(58).
    public NChar fdTranData = new NChar(58);
  }

  // COB:        01 FD-TRAN-CAT-RECORD.
  public FdTranCatRecord fdTranCatRecord = new FdTranCatRecord();

  public static class FdTranCatRecord extends NGroup {

    // COB:            05  FD-TRAN-CAT-KEY.
    public static class FdTranCatKey extends NGroup {
      // COB:               10  FD-TRAN-TYPE-CD                         PIC X(02).
      public NChar fdTranTypeCd = new NChar(2);
      // COB:               10  FD-TRAN-CAT-CD                          PIC 9(04).
      public NZoned fdTranCatCd = new NZoned(4, false);
    }

    public FdTranCatKey fdTranCatKey = new FdTranCatKey();
    // COB:            05  FD-TRAN-CAT-DATA                           PIC X(54).
    public NChar fdTranCatData = new NChar(54);
  }

  public NChar fdReptfileRec = new NChar(133);
  public NChar fdDateparmRec = new NChar(80);
}
