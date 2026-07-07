package com.aws.carddemo.common.include;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cosgn00 extends NGroup {

  // PLI:  DCL 1 COSGN0AI,
  public static class Cosgn0ai extends NGroup {
    // PLI:        2 DFHMS1 CHAR(12),
    public NChar dfhms1 = new NChar(12);
    // PLI:        2 TRNNAMEL FIXED BIN(15),      /* Transaction Name Length */
    public NInt16 trnnamel = new NInt16();

    // PLI:        2 TRNNAMEF,                    /* Transaction Name Format */
    public static class Trnnamef extends NGroup {
      // PLI:          3 TRNNAMEA CHAR,             /*  Transaction Name Alpha */
      public NChar trnnamea = new NChar(1);
    }

    public Trnnamef trnnamef = new Trnnamef();
    // PLI:        2 DFHMS3 CHAR(4),
    public NChar dfhms3 = new NChar(4);
    // PLI:        2 TRNNAMEI CHAR(4),            /* Transaction Name Identifier */
    public NChar trnnamei = new NChar(4);
    // PLI:        2 TITLE01L FIXED BIN(15),      /* Title 01 Length */
    public NInt16 title01l = new NInt16();

    // PLI:        2 TITLE01F,                  /* Title 01 Format */
    public static class Title01f extends NGroup {
      // PLI:         3 TITLE01A CHAR,            /* Title 01 Alpha */
      public NChar title01a = new NChar(1);
    }

    public Title01f title01f = new Title01f();
    // PLI:        2 DFHMS5 CHAR(4),
    public NChar dfhms5 = new NChar(4);
    // PLI:        2 TITLE01I CHAR(40),           /* Title 01 Identifier */
    public NChar title01i = new NChar(40);
    // PLI:        2 CURDATEL FIXED BIN(15),      /* Current Date Length */
    public NInt16 curdatel = new NInt16();

    // PLI:        2 CURDATEF,                    /* Current Date Format */
    public static class Curdatef extends NGroup {
      // PLI:         3 CURDATEA CHAR,              /* Current Date Alpha */
      public NChar curdatea = new NChar(1);
    }

    public Curdatef curdatef = new Curdatef();
    // PLI:        2 DFHMS7 CHAR(4),
    public NChar dfhms7 = new NChar(4);
    // PLI:        2 CURDATEI CHAR(8),            /* Current Date Identifier */
    public NChar curdatei = new NChar(8);
    // PLI:        2 PGMNAMEL FIXED BIN(15),      /* Program Name Length */
    public NInt16 pgmnamel = new NInt16();

    // PLI:        2 PGMNAMEF,                   /* Program Name Format */
    public static class Pgmnamef extends NGroup {
      // PLI:         3 PGMNAMEA CHAR,             /* Program Name Alpha */
      public NChar pgmnamea = new NChar(1);
    }

    public Pgmnamef pgmnamef = new Pgmnamef();
    // PLI:        2 DFHMS9 CHAR(4),
    public NChar dfhms9 = new NChar(4);
    // PLI:        2 PGMNAMEI CHAR(8),            /* Program Name Identifier */
    public NChar pgmnamei = new NChar(8);
    // PLI:        2 TITLE02L FIXED BIN(15),     /* Title 02 Length */
    public NInt16 title02l = new NInt16();

    // PLI:        2 TITLE02F,                   /* Title 02 Format */
    public static class Title02f extends NGroup {
      // PLI:         3 TITLE02A CHAR,             /* Title 02 Alpha */
      public NChar title02a = new NChar(1);
    }

    public Title02f title02f = new Title02f();
    // PLI:        2 DFHMS11 CHAR(4),
    public NChar dfhms11 = new NChar(4);
    // PLI:        2 TITLE02I CHAR(40),          /* Title 02 Identifier */
    public NChar title02i = new NChar(40);
    // PLI:        2 CURTIMEL FIXED BIN(15),     /* Current Time Length */
    public NInt16 curtimel = new NInt16();

    // PLI:        2 CURTIMEF,                   /* Current Time Format */
    public static class Curtimef extends NGroup {
      // PLI:         3 CURTIMEA CHAR,             /* Current Time Alpha */
      public NChar curtimea = new NChar(1);
    }

    public Curtimef curtimef = new Curtimef();
    // PLI:        2 DFHMS13 CHAR(4),
    public NChar dfhms13 = new NChar(4);
    // PLI:        2 CURTIMEI CHAR(9),            /* Current Time Identifier */
    public NChar curtimei = new NChar(9);
    // PLI:        2 APPLIDL FIXED BIN(15),       /* Application ID Length */
    public NInt16 applidl = new NInt16();

    // PLI:        2 APPLIDF,                     /* Application ID Format */
    public static class Applidf extends NGroup {
      // PLI:         3 APPLIDA CHAR,               /* Application ID Alpha */
      public NChar applida = new NChar(1);
    }

    public Applidf applidf = new Applidf();
    // PLI:        2 DFHMS15 CHAR(4),
    public NChar dfhms15 = new NChar(4);
    // PLI:        2 APPLIDI CHAR(8),             /* Application ID Identifier */
    public NChar applidi = new NChar(8);
    // PLI:        2 SYSIDL  FIXED BIN(15),       /* System ID Length */
    public NInt16 sysidl = new NInt16();

    // PLI:        2 SYSIDF,                      /* System ID Format */
    public static class Sysidf extends NGroup {
      // PLI:         3 SYSIDA CHAR,                /* System ID Alpha */
      public NChar sysida = new NChar(1);
    }

    public Sysidf sysidf = new Sysidf();
    // PLI:        2 DFHMS17 CHAR(4),
    public NChar dfhms17 = new NChar(4);
    // PLI:        2 SYSIDI CHAR(8),              /* System ID Identifier */
    public NChar sysidi = new NChar(8);
    // PLI:        2 USERIDL FIXED BIN(15),       /* User ID Length */
    public NInt16 useridl = new NInt16();

    // PLI:        2 USERIDF,                     /* User ID Format */
    public static class Useridf extends NGroup {
      // PLI:         3 USERIDA CHAR,               /* User ID Alpha */
      public NChar userida = new NChar(1);
    }

    public Useridf useridf = new Useridf();
    // PLI:        2 DFHMS19 CHAR(4),
    public NChar dfhms19 = new NChar(4);
    // PLI:        2 USERIDI CHAR(8),             /* User ID Identifier */
    public NChar useridi = new NChar(8);
    // PLI:        2 PASSWDL FIXED BIN(15),       /* Password Length */
    public NInt16 passwdl = new NInt16();

    // PLI:        2 PASSWDF,                     /* Password Format */
    public static class Passwdf extends NGroup {
      // PLI:         3 PASSWDA CHAR,               /* Password Alpha */
      public NChar passwda = new NChar(1);
    }

    public Passwdf passwdf = new Passwdf();
    // PLI:        2 DFHMS21 CHAR(4),
    public NChar dfhms21 = new NChar(4);
    // PLI:        2 PASSWDI CHAR(8),             /* Password Identifier */
    public NChar passwdi = new NChar(8);
    // PLI:        2 ERRMSGL FIXED BIN(15),       /* Error Message Length */
    public NInt16 errmsgl = new NInt16();

    // PLI:        2 ERRMSGF,                     /* Error Message Format */
    public static class Errmsgf extends NGroup {
      // PLI:         3 ERRMSGA CHAR,               /* Error Message Alpha */
      public NChar errmsga = new NChar(1);
    }

    public Errmsgf errmsgf = new Errmsgf();
    // PLI:        2 DFHMS23 CHAR(4),
    public NChar dfhms23 = new NChar(4);
    // PLI:        2 ERRMSGI CHAR(78);            /* Error Message Identifier */
    public NChar errmsgi = new NChar(78);
  }

  public Cosgn0ai cosgn0ai = new Cosgn0ai();

  // PLI:  DCL 1 COSGN0AO BASED(ADDR(COSGN0AI)) UNALIGNED,
  public static class Cosgn0ao extends NGroup {
    // PLI:        2 DFHMS24 CHAR(12),
    public NChar dfhms24 = new NChar(12);
    // PLI:        2 DFHMS25 CHAR(3),
    public NChar dfhms25 = new NChar(3);
    // PLI:        2 TRNNAMEC CHAR,               /* Transaction Name Character */
    public NChar trnnamec = new NChar(1);
    // PLI:        2 TRNNAMEP CHAR,               /* Transaction Name Picture */
    public NChar trnnamep = new NChar(1);
    // PLI:        2 TRNNAMEH CHAR,               /* Transaction Name Highlight */
    public NChar trnnameh = new NChar(1);
    // PLI:        2 TRNNAMEV CHAR,               /* Transaction Name Value */
    public NChar trnnamev = new NChar(1);
    // PLI:        2 TRNNAMEO CHAR(4),            /* Transaction Name Output */
    public NChar trnnameo = new NChar(4);
    // PLI:        2 DFHMS26 CHAR(3),
    public NChar dfhms26 = new NChar(3);
    // PLI:        2 TITLE01C CHAR,               /* Title 01 Character */
    public NChar title01c = new NChar(1);
    // PLI:        2 TITLE01P CHAR,               /* Title 01 Picture */
    public NChar title01p = new NChar(1);
    // PLI:        2 TITLE01H CHAR,               /* Title 01 Highlight */
    public NChar title01h = new NChar(1);
    // PLI:        2 TITLE01V CHAR,               /* Title 01 Value */
    public NChar title01v = new NChar(1);
    // PLI:        2 TITLE01O CHAR(40),           /* Title 01 Output */
    public NChar title01o = new NChar(40);
    // PLI:        2 DFHMS27 CHAR(3),
    public NChar dfhms27 = new NChar(3);
    // PLI:        2 CURDATEC CHAR,               /* Current Date Character */
    public NChar curdatec = new NChar(1);
    // PLI:        2 CURDATEP CHAR,               /* Current Date Picture */
    public NChar curdatep = new NChar(1);
    // PLI:        2 CURDATEH CHAR,               /* Current Date Highlight */
    public NChar curdateh = new NChar(1);
    // PLI:        2 CURDATEV CHAR,               /* Current Date Value */
    public NChar curdatev = new NChar(1);
    // PLI:        2 CURDATEO CHAR(8),            /* Current Date Output */
    public NChar curdateo = new NChar(8);
    // PLI:        2 DFHMS28 CHAR(3),
    public NChar dfhms28 = new NChar(3);
    // PLI:        2 PGMNAMEC CHAR,               /* Program Name Character */
    public NChar pgmnamec = new NChar(1);
    // PLI:        2 PGMNAMEP CHAR,               /* Program Name Picture */
    public NChar pgmnamep = new NChar(1);
    // PLI:        2 PGMNAMEH CHAR,               /* Program Name Highlight */
    public NChar pgmnameh = new NChar(1);
    // PLI:        2 PGMNAMEV CHAR,               /* Program Name Value */
    public NChar pgmnamev = new NChar(1);
    // PLI:        2 PGMNAMEO CHAR(8),            /* Program Name Output */
    public NChar pgmnameo = new NChar(8);
    // PLI:        2 DFHMS29 CHAR(3),
    public NChar dfhms29 = new NChar(3);
    // PLI:        2 TITLE02C CHAR,               /* Title 02 Character */
    public NChar title02c = new NChar(1);
    // PLI:        2 TITLE02P CHAR,               /* Title 02 Picture */
    public NChar title02p = new NChar(1);
    // PLI:        2 TITLE02H CHAR,               /* Title 02 Highlight */
    public NChar title02h = new NChar(1);
    // PLI:        2 TITLE02V CHAR,               /* Title 02 Value */
    public NChar title02v = new NChar(1);
    // PLI:        2 TITLE02O CHAR(40),           /* Title 02 Output */
    public NChar title02o = new NChar(40);
    // PLI:        2 DFHMS30 CHAR(3),
    public NChar dfhms30 = new NChar(3);
    // PLI:        2 CURTIMEC CHAR,               /* Current Time Character */
    public NChar curtimec = new NChar(1);
    // PLI:        2 CURTIMEP CHAR,               /* Current Time Picture */
    public NChar curtimep = new NChar(1);
    // PLI:        2 CURTIMEH CHAR,               /* Current Time Highlight */
    public NChar curtimeh = new NChar(1);
    // PLI:        2 CURTIMEV CHAR,               /* Current Time Value */
    public NChar curtimev = new NChar(1);
    // PLI:        2 CURTIMEO CHAR(9),            /* Current Time Output */
    public NChar curtimeo = new NChar(9);
    // PLI:        2 DFHMS31 CHAR(3),
    public NChar dfhms31 = new NChar(3);
    // PLI:        2 APPLIDC CHAR,                /* Application ID Character */
    public NChar applidc = new NChar(1);
    // PLI:        2 APPLIDP CHAR,                /* Application ID Picture */
    public NChar applidp = new NChar(1);
    // PLI:        2 APPLIDH CHAR,                /* Application ID Highlight */
    public NChar applidh = new NChar(1);
    // PLI:        2 APPLIDV CHAR,                /* Application ID Value */
    public NChar applidv = new NChar(1);
    // PLI:        2 APPLIDO CHAR(8),             /* Application ID Output */
    public NChar applido = new NChar(8);
    // PLI:        2 DFHMS32 CHAR(3),
    public NChar dfhms32 = new NChar(3);
    // PLI:        2 SYSIDC CHAR,                 /* System ID Character */
    public NChar sysidc = new NChar(1);
    // PLI:        2 SYSIDP CHAR,                 /* System ID Picture */
    public NChar sysidp = new NChar(1);
    // PLI:        2 SYSIDH CHAR,                 /* System ID Highlight */
    public NChar sysidh = new NChar(1);
    // PLI:        2 SYSIDV CHAR,                 /* System ID Value */
    public NChar sysidv = new NChar(1);
    // PLI:        2 SYSIDO CHAR(8),              /* System ID Output */
    public NChar sysido = new NChar(8);
    // PLI:        2 DFHMS33 CHAR(3),
    public NChar dfhms33 = new NChar(3);
    // PLI:        2 USERIDC CHAR,                /* User ID Character */
    public NChar useridc = new NChar(1);
    // PLI:        2 USERIDP CHAR,                /* User ID Picture */
    public NChar useridp = new NChar(1);
    // PLI:        2 USERIDH CHAR,                /* User ID Highlight */
    public NChar useridh = new NChar(1);
    // PLI:        2 USERIDV CHAR,                /* User ID Value */
    public NChar useridv = new NChar(1);
    // PLI:        2 USERIDO CHAR(8),             /* User ID Output */
    public NChar userido = new NChar(8);
    // PLI:        2 DFHMS34 CHAR(3),
    public NChar dfhms34 = new NChar(3);
    // PLI:        2 PASSWDC CHAR,                /* Password Character */
    public NChar passwdc = new NChar(1);
    // PLI:        2 PASSWDP CHAR,                /* Password Picture */
    public NChar passwdp = new NChar(1);
    // PLI:        2 PASSWDH CHAR,                /* Password Highlight */
    public NChar passwdh = new NChar(1);
    // PLI:        2 PASSWDV CHAR,                /* Password Value */
    public NChar passwdv = new NChar(1);
    // PLI:        2 PASSWDO CHAR(8),             /* Password Output */
    public NChar passwdo = new NChar(8);
    // PLI:        2 DFHMS35 CHAR(3),
    public NChar dfhms35 = new NChar(3);
    // PLI:        2 ERRMSGC CHAR,                /* Error Message Character */
    public NChar errmsgc = new NChar(1);
    // PLI:        2 ERRMSGP CHAR,                /* Error Message Picture */
    public NChar errmsgp = new NChar(1);
    // PLI:        2 ERRMSGH CHAR,                /* Error Message Highlight */
    public NChar errmsgh = new NChar(1);
    // PLI:        2 ERRMSGV CHAR,                /* Error Message Value */
    public NChar errmsgv = new NChar(1);
    // PLI:        2 ERRMSGO CHAR(78);            /* Error Message Output */
    public NChar errmsgo = new NChar(78);
  }

  public Cosgn0ao cosgn0ao = (Cosgn0ao) new Cosgn0ao().redefines(cosgn0ai);
}
