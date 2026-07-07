package com.aws.carddemo.batch.program.storage.cobtupdt;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class CobtupdtRecords extends NGroup {
  // COB:        01 WS-INPUT-VARS.
  public WsInputVars wsInputVars = new WsInputVars();

  public static class WsInputVars extends NGroup {

    // COB:           05 INPUT-TYPE                            PIC X(1)
    // COB:                                                    VALUE SPACES.
    public NChar inputType = new NChar(1).initial(NChar.Space);
    // COB:           05 INPUT-TR-NUMBER                       PIC X(2)
    // COB:                                                    VALUE SPACES.
    public NChar inputTrNumber = new NChar(2).initial(NChar.Space);
    // COB:           05 INPUT-TR-DESC                         PIC X(50)
    // COB:                                                    VALUE SPACES.
    public NChar inputTrDesc = new NChar(50).initial(NChar.Space);
  }
}
