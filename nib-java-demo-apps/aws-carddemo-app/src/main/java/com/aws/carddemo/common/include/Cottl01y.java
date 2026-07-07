package com.aws.carddemo.common.include;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cottl01y extends NGroup {

  // PLI:  DECLARE 1 CCDA_SCREEN_TITLE,
  public static class CcdaScreenTitle extends NGroup {
    // PLI:         2 CCDA_TITLE01 CHAR(40)
    // PLI:                    INIT ('      AWS Mainframe Modernization       '),
    public NChar ccdaTitle01 = new NChar(40).initial("      AWS Mainframe Modernization       ");
    // PLI:         2 CCDA_TITLE02 CHAR(40)
    // PLI:                    INIT ('              PL/I CardDemo             '),
    public NChar ccdaTitle02 = new NChar(40).initial("              PL/I CardDemo             ");
    // PLI:         2 CCDA_THANK_YOU CHAR(40)
    // PLI:                    INIT ('Thank you for using CCDA application... ');
    public NChar ccdaThankYou = new NChar(40).initial("Thank you for using CCDA application... ");
  }

  public CcdaScreenTitle ccdaScreenTitle = new CcdaScreenTitle();
}
