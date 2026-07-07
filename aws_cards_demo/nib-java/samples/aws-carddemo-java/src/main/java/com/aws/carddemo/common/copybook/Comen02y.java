package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Comen02y extends NGroup {

  // COB:        01 CARDDEMO-MAIN-MENU-OPTIONS.
  public static class CarddemoMainMenuOptions extends NGroup {
    // COB:          05 CDEMO-MENU-OPT-COUNT           PIC 9(02) VALUE 10.
    public NZoned cdemoMenuOptCount = new NZoned(2, false).initial(10);

    // COB:          05 CDEMO-MENU-OPTIONS-DATA.
    public static class CdemoMenuOptionsData extends NGroup {
      // COB:            10 FILLER                       PIC 9(02) VALUE 1.
      public NZoned filler25 = new NZoned(2, false).initial(1);
      // COB:            10 FILLER                       PIC X(35) VALUE
      // COB:                'Account View                       '.
      public NChar filler26 = new NChar(35).initial("Account View                       ");
      // COB:            10 FILLER                       PIC X(08) VALUE 'COACTVWC'.
      public NChar filler28 = new NChar(8).initial("COACTVWC");
      // COB:            10 FILLER                       PIC X(01) VALUE 'U'.
      public NChar filler29 = new NChar(1).initial("U");
      // COB:            10 FILLER                       PIC 9(02) VALUE 2.
      public NZoned filler31 = new NZoned(2, false).initial(2);
      // COB:            10 FILLER                       PIC X(35) VALUE
      // COB:                'Account Update                     '.
      public NChar filler32 = new NChar(35).initial("Account Update                     ");
      // COB:            10 FILLER                       PIC X(08) VALUE 'COACTUPC'.
      public NChar filler34 = new NChar(8).initial("COACTUPC");
      // COB:            10 FILLER                       PIC X(01) VALUE 'U'.
      public NChar filler35 = new NChar(1).initial("U");
      // COB:            10 FILLER                       PIC 9(02) VALUE 3.
      public NZoned filler37 = new NZoned(2, false).initial(3);
      // COB:            10 FILLER                       PIC X(35) VALUE
      // COB:                'Credit Card List                   '.
      public NChar filler38 = new NChar(35).initial("Credit Card List                   ");
      // COB:            10 FILLER                       PIC X(08) VALUE 'COCRDLIC'.
      public NChar filler40 = new NChar(8).initial("COCRDLIC");
      // COB:            10 FILLER                       PIC X(01) VALUE 'U'.
      public NChar filler41 = new NChar(1).initial("U");
      // COB:            10 FILLER                       PIC 9(02) VALUE 4.
      public NZoned filler43 = new NZoned(2, false).initial(4);
      // COB:            10 FILLER                       PIC X(35) VALUE
      // COB:                'Credit Card View                   '.
      public NChar filler44 = new NChar(35).initial("Credit Card View                   ");
      // COB:            10 FILLER                       PIC X(08) VALUE 'COCRDSLC'.
      public NChar filler46 = new NChar(8).initial("COCRDSLC");
      // COB:            10 FILLER                       PIC X(01) VALUE 'U'.
      public NChar filler47 = new NChar(1).initial("U");
      // COB:            10 FILLER                       PIC 9(02) VALUE 5.
      public NZoned filler49 = new NZoned(2, false).initial(5);
      // COB:            10 FILLER                       PIC X(35) VALUE
      // COB:                'Credit Card Update                 '.
      public NChar filler50 = new NChar(35).initial("Credit Card Update                 ");
      // COB:            10 FILLER                       PIC X(08) VALUE 'COCRDUPC'.
      public NChar filler52 = new NChar(8).initial("COCRDUPC");
      // COB:            10 FILLER                       PIC X(01) VALUE 'U'.
      public NChar filler53 = new NChar(1).initial("U");
      // COB:            10 FILLER                       PIC 9(02) VALUE 6.
      public NZoned filler55 = new NZoned(2, false).initial(6);
      // COB:            10 FILLER                       PIC X(35) VALUE
      // COB:                'Transaction List                   '.
      public NChar filler56 = new NChar(35).initial("Transaction List                   ");
      // COB:            10 FILLER                       PIC X(08) VALUE 'COTRN00C'.
      public NChar filler58 = new NChar(8).initial("COTRN00C");
      // COB:            10 FILLER                       PIC X(01) VALUE 'U'.
      public NChar filler59 = new NChar(1).initial("U");
      // COB:            10 FILLER                       PIC 9(02) VALUE 7.
      public NZoned filler61 = new NZoned(2, false).initial(7);
      // COB:            10 FILLER                       PIC X(35) VALUE
      // COB:                'Transaction View                   '.
      public NChar filler62 = new NChar(35).initial("Transaction View                   ");
      // COB:            10 FILLER                       PIC X(08) VALUE 'COTRN01C'.
      public NChar filler64 = new NChar(8).initial("COTRN01C");
      // COB:            10 FILLER                       PIC X(01) VALUE 'U'.
      public NChar filler65 = new NChar(1).initial("U");
      // COB:            10 FILLER                        PIC 9(02) VALUE 8.
      public NZoned filler67 = new NZoned(2, false).initial(8);
      // COB:            10 FILLER                       PIC X(35) VALUE
      // COB:       *        'Transaction Add (Admin Only)       '.
      // COB:                'Transaction Add                    '.
      public NChar filler68 = new NChar(35).initial("Transaction Add                    ");
      // COB:            10 FILLER                       PIC X(08) VALUE 'COTRN02C'.
      public NChar filler71 = new NChar(8).initial("COTRN02C");
      // COB:            10 FILLER                       PIC X(01) VALUE 'U'.
      public NChar filler72 = new NChar(1).initial("U");
      // COB:            10 FILLER                       PIC 9(02) VALUE 9.
      public NZoned filler74 = new NZoned(2, false).initial(9);
      // COB:            10 FILLER                       PIC X(35) VALUE
      // COB:                'Transaction Reports                '.
      public NChar filler75 = new NChar(35).initial("Transaction Reports                ");
      // COB:            10 FILLER                       PIC X(08) VALUE 'CORPT00C'.
      public NChar filler77 = new NChar(8).initial("CORPT00C");
      // COB:            10 FILLER                       PIC X(01) VALUE 'U'.
      public NChar filler78 = new NChar(1).initial("U");
      // COB:            10 FILLER                       PIC 9(02) VALUE 10.
      public NZoned filler80 = new NZoned(2, false).initial(10);
      // COB:            10 FILLER                       PIC X(35) VALUE
      // COB:                'Bill Payment                       '.
      public NChar filler81 = new NChar(35).initial("Bill Payment                       ");
      // COB:            10 FILLER                       PIC X(08) VALUE 'COBIL00C'.
      public NChar filler83 = new NChar(8).initial("COBIL00C");
      // COB:            10 FILLER                       PIC X(01) VALUE 'U'.
      public NChar filler84 = new NChar(1).initial("U");
    }

    public CdemoMenuOptionsData cdemoMenuOptionsData = new CdemoMenuOptionsData();

    // COB:          05 CDEMO-MENU-OPTIONS REDEFINES CDEMO-MENU-OPTIONS-DATA.
    public static class CdemoMenuOptions extends NGroup {
      // COB:            10 CDEMO-MENU-OPT OCCURS 12 TIMES.
      public static class CdemoMenuOpt extends NGroup {
        // COB:              15 CDEMO-MENU-OPT-NUM           PIC 9(02).
        public NZoned cdemoMenuOptNum = new NZoned(2, false);
        // COB:              15 CDEMO-MENU-OPT-NAME          PIC X(35).
        public NChar cdemoMenuOptName = new NChar(35);
        // COB:              15 CDEMO-MENU-OPT-PGMNAME       PIC X(08).
        public NChar cdemoMenuOptPgmname = new NChar(8);
        // COB:              15 CDEMO-MENU-OPT-USRTYPE       PIC X(01).
        public NChar cdemoMenuOptUsrtype = new NChar(1);
      }

      public CdemoMenuOpt[] cdemoMenuOpt = AbstractNField.occurs(12, new CdemoMenuOpt());

      public CdemoMenuOpt cdemoMenuOptAtIndex(int index) {
        return getOccursInstance(cdemoMenuOpt, index);
      }
    }

    public CdemoMenuOptions cdemoMenuOptions =
        (CdemoMenuOptions) new CdemoMenuOptions().redefines(cdemoMenuOptionsData);
  }

  public CarddemoMainMenuOptions carddemoMainMenuOptions = new CarddemoMainMenuOptions();
}
