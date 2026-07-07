package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Csusr01y extends NGroup {

  // COB:        01 SEC-USER-DATA.
  public static class SecUserData extends NGroup {
    // COB:          05 SEC-USR-ID                 PIC X(08).
    public NChar secUsrId = new NChar(8);
    // COB:          05 SEC-USR-FNAME              PIC X(20).
    public NChar secUsrFname = new NChar(20);
    // COB:          05 SEC-USR-LNAME              PIC X(20).
    public NChar secUsrLname = new NChar(20);
    // COB:          05 SEC-USR-PWD                PIC X(08).
    public NChar secUsrPwd = new NChar(8);
    // COB:          05 SEC-USR-TYPE               PIC X(01).
    public NChar secUsrType = new NChar(1);
    // COB:          05 SEC-USR-FILLER             PIC X(23).
    public NChar secUsrFiller = new NChar(23);
  }

  public SecUserData secUserData = new SecUserData();
}
