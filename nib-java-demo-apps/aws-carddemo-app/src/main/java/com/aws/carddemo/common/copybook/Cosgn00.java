package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cosgn00 extends NGroup {

  // COB:        01  COSGN0AI.
  public static class Cosgn0ai extends NGroup {
    // COB:            02  FILLER PIC X(12).
    public NChar filler18 = new NChar(12);
    // COB:            02  TRNNAMEL    COMP  PIC  S9(4).
    public NInt16 trnnamel = new NInt16();
    // COB:            02  TRNNAMEF    PICTURE X.
    public NChar trnnamef = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNNAMEF.
    public static class Filler21 extends NGroup {
      // COB:              03 TRNNAMEA    PICTURE X.
      public NChar trnnamea = new NChar(1);
    }

    public Filler21 filler21 = (Filler21) new Filler21().redefines(trnnamef);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler23 = new NChar(4);
    // COB:            02  TRNNAMEI  PIC X(4).
    public NChar trnnamei = new NChar(4);
    // COB:            02  TITLE01L    COMP  PIC  S9(4).
    public NInt16 title01l = new NInt16();
    // COB:            02  TITLE01F    PICTURE X.
    public NChar title01f = new NChar(1);

    // COB:            02  FILLER REDEFINES TITLE01F.
    public static class Filler27 extends NGroup {
      // COB:              03 TITLE01A    PICTURE X.
      public NChar title01a = new NChar(1);
    }

    public Filler27 filler27 = (Filler27) new Filler27().redefines(title01f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler29 = new NChar(4);
    // COB:            02  TITLE01I  PIC X(40).
    public NChar title01i = new NChar(40);
    // COB:            02  CURDATEL    COMP  PIC  S9(4).
    public NInt16 curdatel = new NInt16();
    // COB:            02  CURDATEF    PICTURE X.
    public NChar curdatef = new NChar(1);

    // COB:            02  FILLER REDEFINES CURDATEF.
    public static class Filler33 extends NGroup {
      // COB:              03 CURDATEA    PICTURE X.
      public NChar curdatea = new NChar(1);
    }

    public Filler33 filler33 = (Filler33) new Filler33().redefines(curdatef);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler35 = new NChar(4);
    // COB:            02  CURDATEI  PIC X(8).
    public NChar curdatei = new NChar(8);
    // COB:            02  PGMNAMEL    COMP  PIC  S9(4).
    public NInt16 pgmnamel = new NInt16();
    // COB:            02  PGMNAMEF    PICTURE X.
    public NChar pgmnamef = new NChar(1);

    // COB:            02  FILLER REDEFINES PGMNAMEF.
    public static class Filler39 extends NGroup {
      // COB:              03 PGMNAMEA    PICTURE X.
      public NChar pgmnamea = new NChar(1);
    }

    public Filler39 filler39 = (Filler39) new Filler39().redefines(pgmnamef);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler41 = new NChar(4);
    // COB:            02  PGMNAMEI  PIC X(8).
    public NChar pgmnamei = new NChar(8);
    // COB:            02  TITLE02L    COMP  PIC  S9(4).
    public NInt16 title02l = new NInt16();
    // COB:            02  TITLE02F    PICTURE X.
    public NChar title02f = new NChar(1);

    // COB:            02  FILLER REDEFINES TITLE02F.
    public static class Filler45 extends NGroup {
      // COB:              03 TITLE02A    PICTURE X.
      public NChar title02a = new NChar(1);
    }

    public Filler45 filler45 = (Filler45) new Filler45().redefines(title02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler47 = new NChar(4);
    // COB:            02  TITLE02I  PIC X(40).
    public NChar title02i = new NChar(40);
    // COB:            02  CURTIMEL    COMP  PIC  S9(4).
    public NInt16 curtimel = new NInt16();
    // COB:            02  CURTIMEF    PICTURE X.
    public NChar curtimef = new NChar(1);

    // COB:            02  FILLER REDEFINES CURTIMEF.
    public static class Filler51 extends NGroup {
      // COB:              03 CURTIMEA    PICTURE X.
      public NChar curtimea = new NChar(1);
    }

    public Filler51 filler51 = (Filler51) new Filler51().redefines(curtimef);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler53 = new NChar(4);
    // COB:            02  CURTIMEI  PIC X(9).
    public NChar curtimei = new NChar(9);
    // COB:            02  APPLIDL    COMP  PIC  S9(4).
    public NInt16 applidl = new NInt16();
    // COB:            02  APPLIDF    PICTURE X.
    public NChar applidf = new NChar(1);

    // COB:            02  FILLER REDEFINES APPLIDF.
    public static class Filler57 extends NGroup {
      // COB:              03 APPLIDA    PICTURE X.
      public NChar applida = new NChar(1);
    }

    public Filler57 filler57 = (Filler57) new Filler57().redefines(applidf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler59 = new NChar(4);
    // COB:            02  APPLIDI  PIC X(8).
    public NChar applidi = new NChar(8);
    // COB:            02  SYSIDL    COMP  PIC  S9(4).
    public NInt16 sysidl = new NInt16();
    // COB:            02  SYSIDF    PICTURE X.
    public NChar sysidf = new NChar(1);

    // COB:            02  FILLER REDEFINES SYSIDF.
    public static class Filler63 extends NGroup {
      // COB:              03 SYSIDA    PICTURE X.
      public NChar sysida = new NChar(1);
    }

    public Filler63 filler63 = (Filler63) new Filler63().redefines(sysidf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler65 = new NChar(4);
    // COB:            02  SYSIDI  PIC X(8).
    public NChar sysidi = new NChar(8);
    // COB:            02  USERIDL    COMP  PIC  S9(4).
    public NInt16 useridl = new NInt16();
    // COB:            02  USERIDF    PICTURE X.
    public NChar useridf = new NChar(1);

    // COB:            02  FILLER REDEFINES USERIDF.
    public static class Filler69 extends NGroup {
      // COB:              03 USERIDA    PICTURE X.
      public NChar userida = new NChar(1);
    }

    public Filler69 filler69 = (Filler69) new Filler69().redefines(useridf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler71 = new NChar(4);
    // COB:            02  USERIDI  PIC X(8).
    public NChar useridi = new NChar(8);
    // COB:            02  PASSWDL    COMP  PIC  S9(4).
    public NInt16 passwdl = new NInt16();
    // COB:            02  PASSWDF    PICTURE X.
    public NChar passwdf = new NChar(1);

    // COB:            02  FILLER REDEFINES PASSWDF.
    public static class Filler75 extends NGroup {
      // COB:              03 PASSWDA    PICTURE X.
      public NChar passwda = new NChar(1);
    }

    public Filler75 filler75 = (Filler75) new Filler75().redefines(passwdf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler77 = new NChar(4);
    // COB:            02  PASSWDI  PIC X(8).
    public NChar passwdi = new NChar(8);
    // COB:            02  ERRMSGL    COMP  PIC  S9(4).
    public NInt16 errmsgl = new NInt16();
    // COB:            02  ERRMSGF    PICTURE X.
    public NChar errmsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES ERRMSGF.
    public static class Filler81 extends NGroup {
      // COB:              03 ERRMSGA    PICTURE X.
      public NChar errmsga = new NChar(1);
    }

    public Filler81 filler81 = (Filler81) new Filler81().redefines(errmsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler83 = new NChar(4);
    // COB:            02  ERRMSGI  PIC X(78).
    public NChar errmsgi = new NChar(78);
  }

  public Cosgn0ai cosgn0ai = new Cosgn0ai();

  // COB:        01  COSGN0AO REDEFINES COSGN0AI.
  public static class Cosgn0ao extends NGroup {
    // COB:            02  FILLER PIC X(12).
    public NChar filler86 = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler87 = new NChar(3);
    // COB:            02  TRNNAMEC    PICTURE X.
    public NChar trnnamec = new NChar(1);
    // COB:            02  TRNNAMEP    PICTURE X.
    public NChar trnnamep = new NChar(1);
    // COB:            02  TRNNAMEH    PICTURE X.
    public NChar trnnameh = new NChar(1);
    // COB:            02  TRNNAMEV    PICTURE X.
    public NChar trnnamev = new NChar(1);
    // COB:            02  TRNNAMEO  PIC X(4).
    public NChar trnnameo = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler93 = new NChar(3);
    // COB:            02  TITLE01C    PICTURE X.
    public NChar title01c = new NChar(1);
    // COB:            02  TITLE01P    PICTURE X.
    public NChar title01p = new NChar(1);
    // COB:            02  TITLE01H    PICTURE X.
    public NChar title01h = new NChar(1);
    // COB:            02  TITLE01V    PICTURE X.
    public NChar title01v = new NChar(1);
    // COB:            02  TITLE01O  PIC X(40).
    public NChar title01o = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler99 = new NChar(3);
    // COB:            02  CURDATEC    PICTURE X.
    public NChar curdatec = new NChar(1);
    // COB:            02  CURDATEP    PICTURE X.
    public NChar curdatep = new NChar(1);
    // COB:            02  CURDATEH    PICTURE X.
    public NChar curdateh = new NChar(1);
    // COB:            02  CURDATEV    PICTURE X.
    public NChar curdatev = new NChar(1);
    // COB:            02  CURDATEO  PIC X(8).
    public NChar curdateo = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler105 = new NChar(3);
    // COB:            02  PGMNAMEC    PICTURE X.
    public NChar pgmnamec = new NChar(1);
    // COB:            02  PGMNAMEP    PICTURE X.
    public NChar pgmnamep = new NChar(1);
    // COB:            02  PGMNAMEH    PICTURE X.
    public NChar pgmnameh = new NChar(1);
    // COB:            02  PGMNAMEV    PICTURE X.
    public NChar pgmnamev = new NChar(1);
    // COB:            02  PGMNAMEO  PIC X(8).
    public NChar pgmnameo = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler111 = new NChar(3);
    // COB:            02  TITLE02C    PICTURE X.
    public NChar title02c = new NChar(1);
    // COB:            02  TITLE02P    PICTURE X.
    public NChar title02p = new NChar(1);
    // COB:            02  TITLE02H    PICTURE X.
    public NChar title02h = new NChar(1);
    // COB:            02  TITLE02V    PICTURE X.
    public NChar title02v = new NChar(1);
    // COB:            02  TITLE02O  PIC X(40).
    public NChar title02o = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler117 = new NChar(3);
    // COB:            02  CURTIMEC    PICTURE X.
    public NChar curtimec = new NChar(1);
    // COB:            02  CURTIMEP    PICTURE X.
    public NChar curtimep = new NChar(1);
    // COB:            02  CURTIMEH    PICTURE X.
    public NChar curtimeh = new NChar(1);
    // COB:            02  CURTIMEV    PICTURE X.
    public NChar curtimev = new NChar(1);
    // COB:            02  CURTIMEO  PIC X(9).
    public NChar curtimeo = new NChar(9);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler123 = new NChar(3);
    // COB:            02  APPLIDC    PICTURE X.
    public NChar applidc = new NChar(1);
    // COB:            02  APPLIDP    PICTURE X.
    public NChar applidp = new NChar(1);
    // COB:            02  APPLIDH    PICTURE X.
    public NChar applidh = new NChar(1);
    // COB:            02  APPLIDV    PICTURE X.
    public NChar applidv = new NChar(1);
    // COB:            02  APPLIDO  PIC X(8).
    public NChar applido = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler129 = new NChar(3);
    // COB:            02  SYSIDC    PICTURE X.
    public NChar sysidc = new NChar(1);
    // COB:            02  SYSIDP    PICTURE X.
    public NChar sysidp = new NChar(1);
    // COB:            02  SYSIDH    PICTURE X.
    public NChar sysidh = new NChar(1);
    // COB:            02  SYSIDV    PICTURE X.
    public NChar sysidv = new NChar(1);
    // COB:            02  SYSIDO  PIC X(8).
    public NChar sysido = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler135 = new NChar(3);
    // COB:            02  USERIDC    PICTURE X.
    public NChar useridc = new NChar(1);
    // COB:            02  USERIDP    PICTURE X.
    public NChar useridp = new NChar(1);
    // COB:            02  USERIDH    PICTURE X.
    public NChar useridh = new NChar(1);
    // COB:            02  USERIDV    PICTURE X.
    public NChar useridv = new NChar(1);
    // COB:            02  USERIDO  PIC X(8).
    public NChar userido = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler141 = new NChar(3);
    // COB:            02  PASSWDC    PICTURE X.
    public NChar passwdc = new NChar(1);
    // COB:            02  PASSWDP    PICTURE X.
    public NChar passwdp = new NChar(1);
    // COB:            02  PASSWDH    PICTURE X.
    public NChar passwdh = new NChar(1);
    // COB:            02  PASSWDV    PICTURE X.
    public NChar passwdv = new NChar(1);
    // COB:            02  PASSWDO  PIC X(8).
    public NChar passwdo = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler147 = new NChar(3);
    // COB:            02  ERRMSGC    PICTURE X.
    public NChar errmsgc = new NChar(1);
    // COB:            02  ERRMSGP    PICTURE X.
    public NChar errmsgp = new NChar(1);
    // COB:            02  ERRMSGH    PICTURE X.
    public NChar errmsgh = new NChar(1);
    // COB:            02  ERRMSGV    PICTURE X.
    public NChar errmsgv = new NChar(1);
    // COB:            02  ERRMSGO  PIC X(78).
    public NChar errmsgo = new NChar(78);
  }

  public Cosgn0ao cosgn0ao = (Cosgn0ao) new Cosgn0ao().redefines(cosgn0ai);
}
