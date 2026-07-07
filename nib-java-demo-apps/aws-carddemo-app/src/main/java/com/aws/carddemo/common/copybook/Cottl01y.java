package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cottl01y extends NGroup {

  // COB:        01 CCDA-SCREEN-TITLE.
  public static class CcdaScreenTitle extends NGroup {
    // COB:          05 CCDA-TITLE01    PIC X(40) VALUE
    // COB:             '      AWS Mainframe Modernization       '.
    public NChar ccdaTitle01 = new NChar(40).initial("      AWS Mainframe Modernization       ");
    // COB:          05 CCDA-TITLE02    PIC X(40) VALUE
    // COB:       *     '  Credit Card Demo Application (CCDA)   '.
    // COB:             '              CardDemo                  '.
    public NChar ccdaTitle02 = new NChar(40).initial("              CardDemo                  ");
    // COB:          05 CCDA-THANK-YOU  PIC X(40) VALUE
    // COB:             'Thank you for using CCDA application... '.
    public NChar ccdaThankYou = new NChar(40).initial("Thank you for using CCDA application... ");
  }

  public CcdaScreenTitle ccdaScreenTitle = new CcdaScreenTitle();
}
