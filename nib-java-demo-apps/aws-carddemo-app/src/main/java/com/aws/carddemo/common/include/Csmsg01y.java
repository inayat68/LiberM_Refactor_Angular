package com.aws.carddemo.common.include;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Csmsg01y extends NGroup {

  // PLI:  DCL 1 CCDA_COMMON_MESSAGES,
  public static class CcdaCommonMessages extends NGroup {
    // PLI:        2 CCDA_MSG_THANK_YOU    CHAR(50)
    // PLI:            INIT('Thank you for using CardDemo application...      '),
    public NChar ccdaMsgThankYou =
        new NChar(50).initial("Thank you for using CardDemo application...      ");
    // PLI:        2 CCDA_MSG_INVALID_KEY  CHAR(50)
    // PLI:            INIT('Invalid key pressed. Please see below...         ');
    public NChar ccdaMsgInvalidKey =
        new NChar(50).initial("Invalid key pressed. Please see below...         ");
  }

  public CcdaCommonMessages ccdaCommonMessages = new CcdaCommonMessages();
}
