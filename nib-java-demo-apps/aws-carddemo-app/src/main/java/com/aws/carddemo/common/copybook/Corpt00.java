package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Corpt00 extends NGroup {

  // COB:        01  CORPT0AI.
  public static class Corpt0ai extends NGroup {
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
    // COB:            02  MONTHLYL    COMP  PIC  S9(4).
    public NInt16 monthlyl = new NInt16();
    // COB:            02  MONTHLYF    PICTURE X.
    public NChar monthlyf = new NChar(1);

    // COB:            02  FILLER REDEFINES MONTHLYF.
    public static class Filler57 extends NGroup {
      // COB:              03 MONTHLYA    PICTURE X.
      public NChar monthlya = new NChar(1);
    }

    public Filler57 filler57 = (Filler57) new Filler57().redefines(monthlyf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler59 = new NChar(4);
    // COB:            02  MONTHLYI  PIC X(1).
    public NChar monthlyi = new NChar(1);
    // COB:            02  YEARLYL    COMP  PIC  S9(4).
    public NInt16 yearlyl = new NInt16();
    // COB:            02  YEARLYF    PICTURE X.
    public NChar yearlyf = new NChar(1);

    // COB:            02  FILLER REDEFINES YEARLYF.
    public static class Filler63 extends NGroup {
      // COB:              03 YEARLYA    PICTURE X.
      public NChar yearlya = new NChar(1);
    }

    public Filler63 filler63 = (Filler63) new Filler63().redefines(yearlyf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler65 = new NChar(4);
    // COB:            02  YEARLYI  PIC X(1).
    public NChar yearlyi = new NChar(1);
    // COB:            02  CUSTOML    COMP  PIC  S9(4).
    public NInt16 customl = new NInt16();
    // COB:            02  CUSTOMF    PICTURE X.
    public NChar customf = new NChar(1);

    // COB:            02  FILLER REDEFINES CUSTOMF.
    public static class Filler69 extends NGroup {
      // COB:              03 CUSTOMA    PICTURE X.
      public NChar customa = new NChar(1);
    }

    public Filler69 filler69 = (Filler69) new Filler69().redefines(customf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler71 = new NChar(4);
    // COB:            02  CUSTOMI  PIC X(1).
    public NChar customi = new NChar(1);
    // COB:            02  SDTMML    COMP  PIC  S9(4).
    public NInt16 sdtmml = new NInt16();
    // COB:            02  SDTMMF    PICTURE X.
    public NChar sdtmmf = new NChar(1);

    // COB:            02  FILLER REDEFINES SDTMMF.
    public static class Filler75 extends NGroup {
      // COB:              03 SDTMMA    PICTURE X.
      public NChar sdtmma = new NChar(1);
    }

    public Filler75 filler75 = (Filler75) new Filler75().redefines(sdtmmf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler77 = new NChar(4);
    // COB:            02  SDTMMI  PIC X(2).
    public NChar sdtmmi = new NChar(2);
    // COB:            02  SDTDDL    COMP  PIC  S9(4).
    public NInt16 sdtddl = new NInt16();
    // COB:            02  SDTDDF    PICTURE X.
    public NChar sdtddf = new NChar(1);

    // COB:            02  FILLER REDEFINES SDTDDF.
    public static class Filler81 extends NGroup {
      // COB:              03 SDTDDA    PICTURE X.
      public NChar sdtdda = new NChar(1);
    }

    public Filler81 filler81 = (Filler81) new Filler81().redefines(sdtddf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler83 = new NChar(4);
    // COB:            02  SDTDDI  PIC X(2).
    public NChar sdtddi = new NChar(2);
    // COB:            02  SDTYYYYL    COMP  PIC  S9(4).
    public NInt16 sdtyyyyl = new NInt16();
    // COB:            02  SDTYYYYF    PICTURE X.
    public NChar sdtyyyyf = new NChar(1);

    // COB:            02  FILLER REDEFINES SDTYYYYF.
    public static class Filler87 extends NGroup {
      // COB:              03 SDTYYYYA    PICTURE X.
      public NChar sdtyyyya = new NChar(1);
    }

    public Filler87 filler87 = (Filler87) new Filler87().redefines(sdtyyyyf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler89 = new NChar(4);
    // COB:            02  SDTYYYYI  PIC X(4).
    public NChar sdtyyyyi = new NChar(4);
    // COB:            02  EDTMML    COMP  PIC  S9(4).
    public NInt16 edtmml = new NInt16();
    // COB:            02  EDTMMF    PICTURE X.
    public NChar edtmmf = new NChar(1);

    // COB:            02  FILLER REDEFINES EDTMMF.
    public static class Filler93 extends NGroup {
      // COB:              03 EDTMMA    PICTURE X.
      public NChar edtmma = new NChar(1);
    }

    public Filler93 filler93 = (Filler93) new Filler93().redefines(edtmmf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler95 = new NChar(4);
    // COB:            02  EDTMMI  PIC X(2).
    public NChar edtmmi = new NChar(2);
    // COB:            02  EDTDDL    COMP  PIC  S9(4).
    public NInt16 edtddl = new NInt16();
    // COB:            02  EDTDDF    PICTURE X.
    public NChar edtddf = new NChar(1);

    // COB:            02  FILLER REDEFINES EDTDDF.
    public static class Filler99 extends NGroup {
      // COB:              03 EDTDDA    PICTURE X.
      public NChar edtdda = new NChar(1);
    }

    public Filler99 filler99 = (Filler99) new Filler99().redefines(edtddf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler101 = new NChar(4);
    // COB:            02  EDTDDI  PIC X(2).
    public NChar edtddi = new NChar(2);
    // COB:            02  EDTYYYYL    COMP  PIC  S9(4).
    public NInt16 edtyyyyl = new NInt16();
    // COB:            02  EDTYYYYF    PICTURE X.
    public NChar edtyyyyf = new NChar(1);

    // COB:            02  FILLER REDEFINES EDTYYYYF.
    public static class Filler105 extends NGroup {
      // COB:              03 EDTYYYYA    PICTURE X.
      public NChar edtyyyya = new NChar(1);
    }

    public Filler105 filler105 = (Filler105) new Filler105().redefines(edtyyyyf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler107 = new NChar(4);
    // COB:            02  EDTYYYYI  PIC X(4).
    public NChar edtyyyyi = new NChar(4);
    // COB:            02  CONFIRML    COMP  PIC  S9(4).
    public NInt16 confirml = new NInt16();
    // COB:            02  CONFIRMF    PICTURE X.
    public NChar confirmf = new NChar(1);

    // COB:            02  FILLER REDEFINES CONFIRMF.
    public static class Filler111 extends NGroup {
      // COB:              03 CONFIRMA    PICTURE X.
      public NChar confirma = new NChar(1);
    }

    public Filler111 filler111 = (Filler111) new Filler111().redefines(confirmf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler113 = new NChar(4);
    // COB:            02  CONFIRMI  PIC X(1).
    public NChar confirmi = new NChar(1);
    // COB:            02  ERRMSGL    COMP  PIC  S9(4).
    public NInt16 errmsgl = new NInt16();
    // COB:            02  ERRMSGF    PICTURE X.
    public NChar errmsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES ERRMSGF.
    public static class Filler117 extends NGroup {
      // COB:              03 ERRMSGA    PICTURE X.
      public NChar errmsga = new NChar(1);
    }

    public Filler117 filler117 = (Filler117) new Filler117().redefines(errmsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler119 = new NChar(4);
    // COB:            02  ERRMSGI  PIC X(78).
    public NChar errmsgi = new NChar(78);
  }

  public Corpt0ai corpt0ai = new Corpt0ai();

  // COB:        01  CORPT0AO REDEFINES CORPT0AI.
  public static class Corpt0ao extends NGroup {
    // COB:            02  FILLER PIC X(12).
    public NChar filler122 = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler123 = new NChar(3);
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
    public NChar filler129 = new NChar(3);
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
    public NChar filler135 = new NChar(3);
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
    public NChar filler141 = new NChar(3);
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
    public NChar filler147 = new NChar(3);
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
    public NChar filler153 = new NChar(3);
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
    public NChar filler159 = new NChar(3);
    // COB:            02  MONTHLYC    PICTURE X.
    public NChar monthlyc = new NChar(1);
    // COB:            02  MONTHLYP    PICTURE X.
    public NChar monthlyp = new NChar(1);
    // COB:            02  MONTHLYH    PICTURE X.
    public NChar monthlyh = new NChar(1);
    // COB:            02  MONTHLYV    PICTURE X.
    public NChar monthlyv = new NChar(1);
    // COB:            02  MONTHLYO  PIC X(1).
    public NChar monthlyo = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler165 = new NChar(3);
    // COB:            02  YEARLYC    PICTURE X.
    public NChar yearlyc = new NChar(1);
    // COB:            02  YEARLYP    PICTURE X.
    public NChar yearlyp = new NChar(1);
    // COB:            02  YEARLYH    PICTURE X.
    public NChar yearlyh = new NChar(1);
    // COB:            02  YEARLYV    PICTURE X.
    public NChar yearlyv = new NChar(1);
    // COB:            02  YEARLYO  PIC X(1).
    public NChar yearlyo = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler171 = new NChar(3);
    // COB:            02  CUSTOMC    PICTURE X.
    public NChar customc = new NChar(1);
    // COB:            02  CUSTOMP    PICTURE X.
    public NChar customp = new NChar(1);
    // COB:            02  CUSTOMH    PICTURE X.
    public NChar customh = new NChar(1);
    // COB:            02  CUSTOMV    PICTURE X.
    public NChar customv = new NChar(1);
    // COB:            02  CUSTOMO  PIC X(1).
    public NChar customo = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler177 = new NChar(3);
    // COB:            02  SDTMMC    PICTURE X.
    public NChar sdtmmc = new NChar(1);
    // COB:            02  SDTMMP    PICTURE X.
    public NChar sdtmmp = new NChar(1);
    // COB:            02  SDTMMH    PICTURE X.
    public NChar sdtmmh = new NChar(1);
    // COB:            02  SDTMMV    PICTURE X.
    public NChar sdtmmv = new NChar(1);
    // COB:            02  SDTMMO  PIC X(2).
    public NChar sdtmmo = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler183 = new NChar(3);
    // COB:            02  SDTDDC    PICTURE X.
    public NChar sdtddc = new NChar(1);
    // COB:            02  SDTDDP    PICTURE X.
    public NChar sdtddp = new NChar(1);
    // COB:            02  SDTDDH    PICTURE X.
    public NChar sdtddh = new NChar(1);
    // COB:            02  SDTDDV    PICTURE X.
    public NChar sdtddv = new NChar(1);
    // COB:            02  SDTDDO  PIC X(2).
    public NChar sdtddo = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler189 = new NChar(3);
    // COB:            02  SDTYYYYC    PICTURE X.
    public NChar sdtyyyyc = new NChar(1);
    // COB:            02  SDTYYYYP    PICTURE X.
    public NChar sdtyyyyp = new NChar(1);
    // COB:            02  SDTYYYYH    PICTURE X.
    public NChar sdtyyyyh = new NChar(1);
    // COB:            02  SDTYYYYV    PICTURE X.
    public NChar sdtyyyyv = new NChar(1);
    // COB:            02  SDTYYYYO  PIC X(4).
    public NChar sdtyyyyo = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler195 = new NChar(3);
    // COB:            02  EDTMMC    PICTURE X.
    public NChar edtmmc = new NChar(1);
    // COB:            02  EDTMMP    PICTURE X.
    public NChar edtmmp = new NChar(1);
    // COB:            02  EDTMMH    PICTURE X.
    public NChar edtmmh = new NChar(1);
    // COB:            02  EDTMMV    PICTURE X.
    public NChar edtmmv = new NChar(1);
    // COB:            02  EDTMMO  PIC X(2).
    public NChar edtmmo = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler201 = new NChar(3);
    // COB:            02  EDTDDC    PICTURE X.
    public NChar edtddc = new NChar(1);
    // COB:            02  EDTDDP    PICTURE X.
    public NChar edtddp = new NChar(1);
    // COB:            02  EDTDDH    PICTURE X.
    public NChar edtddh = new NChar(1);
    // COB:            02  EDTDDV    PICTURE X.
    public NChar edtddv = new NChar(1);
    // COB:            02  EDTDDO  PIC X(2).
    public NChar edtddo = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler207 = new NChar(3);
    // COB:            02  EDTYYYYC    PICTURE X.
    public NChar edtyyyyc = new NChar(1);
    // COB:            02  EDTYYYYP    PICTURE X.
    public NChar edtyyyyp = new NChar(1);
    // COB:            02  EDTYYYYH    PICTURE X.
    public NChar edtyyyyh = new NChar(1);
    // COB:            02  EDTYYYYV    PICTURE X.
    public NChar edtyyyyv = new NChar(1);
    // COB:            02  EDTYYYYO  PIC X(4).
    public NChar edtyyyyo = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler213 = new NChar(3);
    // COB:            02  CONFIRMC    PICTURE X.
    public NChar confirmc = new NChar(1);
    // COB:            02  CONFIRMP    PICTURE X.
    public NChar confirmp = new NChar(1);
    // COB:            02  CONFIRMH    PICTURE X.
    public NChar confirmh = new NChar(1);
    // COB:            02  CONFIRMV    PICTURE X.
    public NChar confirmv = new NChar(1);
    // COB:            02  CONFIRMO  PIC X(1).
    public NChar confirmo = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler219 = new NChar(3);
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

  public Corpt0ao corpt0ao = (Corpt0ao) new Corpt0ao().redefines(corpt0ai);
}
