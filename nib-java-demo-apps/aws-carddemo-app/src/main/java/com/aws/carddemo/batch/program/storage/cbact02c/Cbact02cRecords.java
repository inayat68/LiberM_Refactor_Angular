package com.aws.carddemo.batch.program.storage.cbact02c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cbact02cRecords extends NGroup {
  // COB:        01  FD-CARDFILE-REC.
  public FdCardfileRec fdCardfileRec = new FdCardfileRec();

  public static class FdCardfileRec extends NGroup {

    // COB:            05 FD-CARD-NUM                       PIC X(16).
    public NChar fdCardNum = new NChar(16);
    // COB:            05 FD-CARD-DATA                      PIC X(134).
    public NChar fdCardData = new NChar(134);
  }
}
