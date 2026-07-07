package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Csmsg02y extends NGroup {

  // COB: 001200 01  ABEND-DATA.
  public static class AbendData extends NGroup {
    // COB: 001300   05  ABEND-CODE                            PIC X(4)
    // COB: 001400       VALUE SPACES.
    public NChar abendCode = new NChar(4).initial(NChar.Space);
    // COB: 001500   05  ABEND-CULPRIT                         PIC X(8)
    // COB: 001600       VALUE SPACES.
    public NChar abendCulprit = new NChar(8).initial(NChar.Space);
    // COB: 001700   05  ABEND-REASON                          PIC X(50)
    // COB: 001800       VALUE SPACES.
    public NChar abendReason = new NChar(50).initial(NChar.Space);
    // COB: 001900   05  ABEND-MSG                             PIC X(72)
    // COB: 002000       VALUE SPACES.
    public NChar abendMsg = new NChar(72).initial(NChar.Space);
  }

  public AbendData abendData = new AbendData();
}
