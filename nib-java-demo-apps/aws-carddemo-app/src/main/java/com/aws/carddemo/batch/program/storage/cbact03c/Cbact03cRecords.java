package com.aws.carddemo.batch.program.storage.cbact03c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cbact03cRecords extends NGroup {
  // COB:        01  FD-XREFFILE-REC.
  public FdXreffileRec fdXreffileRec = new FdXreffileRec();

  public static class FdXreffileRec extends NGroup {

    // COB:            05 FD-XREF-CARD-NUM                  PIC X(16).
    public NChar fdXrefCardNum = new NChar(16);
    // COB:            05 FD-XREF-DATA                      PIC X(34).
    public NChar fdXrefData = new NChar(34);
  }
}
