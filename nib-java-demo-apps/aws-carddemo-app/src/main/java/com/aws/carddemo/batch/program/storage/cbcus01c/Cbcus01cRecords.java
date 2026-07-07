package com.aws.carddemo.batch.program.storage.cbcus01c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cbcus01cRecords extends NGroup {
  // COB:        01  FD-CUSTFILE-REC.
  public FdCustfileRec fdCustfileRec = new FdCustfileRec();

  public static class FdCustfileRec extends NGroup {

    // COB:            05 FD-CUST-ID                        PIC 9(09).
    public NZoned fdCustId = new NZoned(9, false);
    // COB:            05 FD-CUST-DATA                      PIC X(491).
    public NChar fdCustData = new NChar(491);
  }
}
