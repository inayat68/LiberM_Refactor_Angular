package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cousr01 extends NGroup {

  // COB:        01  COUSR1AI.
  public static class Cousr1ai extends NGroup {
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
    // COB:            02  CURTIMEI  PIC X(8).
    public NChar curtimei = new NChar(8);
    // COB:            02  FNAMEL    COMP  PIC  S9(4).
    public NInt16 fnamel = new NInt16();
    // COB:            02  FNAMEF    PICTURE X.
    public NChar fnamef = new NChar(1);

    // COB:            02  FILLER REDEFINES FNAMEF.
    public static class Filler57 extends NGroup {
      // COB:              03 FNAMEA    PICTURE X.
      public NChar fnamea = new NChar(1);
    }

    public Filler57 filler57 = (Filler57) new Filler57().redefines(fnamef);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler59 = new NChar(4);
    // COB:            02  FNAMEI  PIC X(20).
    public NChar fnamei = new NChar(20);
    // COB:            02  LNAMEL    COMP  PIC  S9(4).
    public NInt16 lnamel = new NInt16();
    // COB:            02  LNAMEF    PICTURE X.
    public NChar lnamef = new NChar(1);

    // COB:            02  FILLER REDEFINES LNAMEF.
    public static class Filler63 extends NGroup {
      // COB:              03 LNAMEA    PICTURE X.
      public NChar lnamea = new NChar(1);
    }

    public Filler63 filler63 = (Filler63) new Filler63().redefines(lnamef);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler65 = new NChar(4);
    // COB:            02  LNAMEI  PIC X(20).
    public NChar lnamei = new NChar(20);
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
    // COB:            02  USRTYPEL    COMP  PIC  S9(4).
    public NInt16 usrtypel = new NInt16();
    // COB:            02  USRTYPEF    PICTURE X.
    public NChar usrtypef = new NChar(1);

    // COB:            02  FILLER REDEFINES USRTYPEF.
    public static class Filler81 extends NGroup {
      // COB:              03 USRTYPEA    PICTURE X.
      public NChar usrtypea = new NChar(1);
    }

    public Filler81 filler81 = (Filler81) new Filler81().redefines(usrtypef);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler83 = new NChar(4);
    // COB:            02  USRTYPEI  PIC X(1).
    public NChar usrtypei = new NChar(1);
    // COB:            02  ERRMSGL    COMP  PIC  S9(4).
    public NInt16 errmsgl = new NInt16();
    // COB:            02  ERRMSGF    PICTURE X.
    public NChar errmsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES ERRMSGF.
    public static class Filler87 extends NGroup {
      // COB:              03 ERRMSGA    PICTURE X.
      public NChar errmsga = new NChar(1);
    }

    public Filler87 filler87 = (Filler87) new Filler87().redefines(errmsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler89 = new NChar(4);
    // COB:            02  ERRMSGI  PIC X(78).
    public NChar errmsgi = new NChar(78);
  }

  public Cousr1ai cousr1ai = new Cousr1ai();

  // COB:        01  COUSR1AO REDEFINES COUSR1AI.
  public static class Cousr1ao extends NGroup {
    // COB:            02  FILLER PIC X(12).
    public NChar filler92 = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler93 = new NChar(3);
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
    public NChar filler99 = new NChar(3);
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
    public NChar filler105 = new NChar(3);
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
    public NChar filler111 = new NChar(3);
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
    public NChar filler117 = new NChar(3);
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
    public NChar filler123 = new NChar(3);
    // COB:            02  CURTIMEC    PICTURE X.
    public NChar curtimec = new NChar(1);
    // COB:            02  CURTIMEP    PICTURE X.
    public NChar curtimep = new NChar(1);
    // COB:            02  CURTIMEH    PICTURE X.
    public NChar curtimeh = new NChar(1);
    // COB:            02  CURTIMEV    PICTURE X.
    public NChar curtimev = new NChar(1);
    // COB:            02  CURTIMEO  PIC X(8).
    public NChar curtimeo = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler129 = new NChar(3);
    // COB:            02  FNAMEC    PICTURE X.
    public NChar fnamec = new NChar(1);
    // COB:            02  FNAMEP    PICTURE X.
    public NChar fnamep = new NChar(1);
    // COB:            02  FNAMEH    PICTURE X.
    public NChar fnameh = new NChar(1);
    // COB:            02  FNAMEV    PICTURE X.
    public NChar fnamev = new NChar(1);
    // COB:            02  FNAMEO  PIC X(20).
    public NChar fnameo = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler135 = new NChar(3);
    // COB:            02  LNAMEC    PICTURE X.
    public NChar lnamec = new NChar(1);
    // COB:            02  LNAMEP    PICTURE X.
    public NChar lnamep = new NChar(1);
    // COB:            02  LNAMEH    PICTURE X.
    public NChar lnameh = new NChar(1);
    // COB:            02  LNAMEV    PICTURE X.
    public NChar lnamev = new NChar(1);
    // COB:            02  LNAMEO  PIC X(20).
    public NChar lnameo = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler141 = new NChar(3);
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
    public NChar filler147 = new NChar(3);
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
    public NChar filler153 = new NChar(3);
    // COB:            02  USRTYPEC    PICTURE X.
    public NChar usrtypec = new NChar(1);
    // COB:            02  USRTYPEP    PICTURE X.
    public NChar usrtypep = new NChar(1);
    // COB:            02  USRTYPEH    PICTURE X.
    public NChar usrtypeh = new NChar(1);
    // COB:            02  USRTYPEV    PICTURE X.
    public NChar usrtypev = new NChar(1);
    // COB:            02  USRTYPEO  PIC X(1).
    public NChar usrtypeo = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler159 = new NChar(3);
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

  public Cousr1ao cousr1ao = (Cousr1ao) new Cousr1ao().redefines(cousr1ai);
}
