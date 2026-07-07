package com.aws.carddemo.common.include;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Csusr01y extends NGroup {

  // PLI:  DCL 1 SEC_USER_DATA,
  public static class SecUserData extends NGroup {
    // PLI:          2 SEC_USR_ID                 CHAR(8),
    public NChar secUsrId = new NChar(8);
    // PLI:          2 SEC_USR_FNAME              CHAR(20),
    public NChar secUsrFname = new NChar(20);
    // PLI:          2 SEC_USR_LNAME              CHAR(20),
    public NChar secUsrLname = new NChar(20);
    // PLI:          2 SEC_USR_PWD                CHAR(8),
    public NChar secUsrPwd = new NChar(8);
    // PLI:          2 SEC_USR_TYPE               CHAR(1),
    public NChar secUsrType = new NChar(1);
    // PLI:          2 SEC_USR_FILLER             CHAR(23);
    public NChar secUsrFiller = new NChar(23);
  }

  public SecUserData secUserData = new SecUserData();
}
