package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Csmsg01y extends NGroup {

  // COB:        01 CCDA-COMMON-MESSAGES.
  public static class CcdaCommonMessages extends NGroup {
    // COB:          05 CCDA-MSG-THANK-YOU         PIC X(50) VALUE
    // COB:               'Thank you for using CardDemo application...      '.
    public NChar ccdaMsgThankYou =
        new NChar(50).initial("Thank you for using CardDemo application...      ");
    // COB:          05 CCDA-MSG-INVALID-KEY       PIC X(50) VALUE
    // COB:               'Invalid key pressed. Please see below...         '.
    public NChar ccdaMsgInvalidKey =
        new NChar(50).initial("Invalid key pressed. Please see below...         ");
  }

  public CcdaCommonMessages ccdaCommonMessages = new CcdaCommonMessages();
}
