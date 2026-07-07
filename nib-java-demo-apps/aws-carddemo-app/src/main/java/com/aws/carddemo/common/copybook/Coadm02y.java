package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Coadm02y extends NGroup {

  // COB:        01 CARDDEMO-ADMIN-MENU-OPTIONS.
  public static class CarddemoAdminMenuOptions extends NGroup {
    // COB:          05 CDEMO-ADMIN-OPT-COUNT           PIC 9(02) VALUE 4.
    public NZoned cdemoAdminOptCount = new NZoned(2, false).initial(4);

    // COB:          05 CDEMO-ADMIN-OPTIONS-DATA.
    public static class CdemoAdminOptionsData extends NGroup {
      // COB:            10 FILLER                        PIC 9(02) VALUE 1.
      public NZoned filler24 = new NZoned(2, false).initial(1);
      // COB:            10 FILLER                        PIC X(35) VALUE
      // COB:                'User List (Security)               '.
      public NChar filler25 = new NChar(35).initial("User List (Security)               ");
      // COB:            10 FILLER                        PIC X(08) VALUE 'COUSR00C'.
      public NChar filler27 = new NChar(8).initial("COUSR00C");
      // COB:            10 FILLER                        PIC 9(02) VALUE 2.
      public NZoned filler29 = new NZoned(2, false).initial(2);
      // COB:            10 FILLER                        PIC X(35) VALUE
      // COB:                'User Add (Security)                '.
      public NChar filler30 = new NChar(35).initial("User Add (Security)                ");
      // COB:            10 FILLER                        PIC X(08) VALUE 'COUSR01C'.
      public NChar filler32 = new NChar(8).initial("COUSR01C");
      // COB:            10 FILLER                        PIC 9(02) VALUE 3.
      public NZoned filler34 = new NZoned(2, false).initial(3);
      // COB:            10 FILLER                        PIC X(35) VALUE
      // COB:                'User Update (Security)             '.
      public NChar filler35 = new NChar(35).initial("User Update (Security)             ");
      // COB:            10 FILLER                        PIC X(08) VALUE 'COUSR02C'.
      public NChar filler37 = new NChar(8).initial("COUSR02C");
      // COB:            10 FILLER                        PIC 9(02) VALUE 4.
      public NZoned filler39 = new NZoned(2, false).initial(4);
      // COB:            10 FILLER                        PIC X(35) VALUE
      // COB:                'User Delete (Security)             '.
      public NChar filler40 = new NChar(35).initial("User Delete (Security)             ");
      // COB:            10 FILLER                        PIC X(08) VALUE 'COUSR03C'.
      public NChar filler42 = new NChar(8).initial("COUSR03C");
      // COB:          05 CDEMO-ADMIN-OPTIONS-DATA.
      public NChar filler22 = new NChar(2);
    }

    public CdemoAdminOptionsData cdemoAdminOptionsData = new CdemoAdminOptionsData();

    // COB:          05 CDEMO-ADMIN-OPTIONS REDEFINES CDEMO-ADMIN-OPTIONS-DATA.
    public static class CdemoAdminOptions extends NGroup {
      // COB:            10 CDEMO-ADMIN-OPT OCCURS 9 TIMES.
      public static class CdemoAdminOpt extends NGroup {
        // COB:              15 CDEMO-ADMIN-OPT-NUM           PIC 9(02).
        public NZoned cdemoAdminOptNum = new NZoned(2, false);
        // COB:              15 CDEMO-ADMIN-OPT-NAME          PIC X(35).
        public NChar cdemoAdminOptName = new NChar(35);
        // COB:              15 CDEMO-ADMIN-OPT-PGMNAME       PIC X(08).
        public NChar cdemoAdminOptPgmname = new NChar(8);
      }

      public CdemoAdminOpt[] cdemoAdminOpt = AbstractNField.occurs(9, new CdemoAdminOpt());

      public CdemoAdminOpt cdemoAdminOptAtIndex(int index) {
        return getOccursInstance(cdemoAdminOpt, index);
      }
    }

    public CdemoAdminOptions cdemoAdminOptions =
        (CdemoAdminOptions) new CdemoAdminOptions().redefines(cdemoAdminOptionsData);
  }

  public CarddemoAdminMenuOptions carddemoAdminMenuOptions = new CarddemoAdminMenuOptions();
}
