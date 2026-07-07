package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cotrn02 extends NGroup {

  // COB:        01  COTRN2AI.
  public static class Cotrn2ai extends NGroup {
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
    // COB:            02  ACTIDINL    COMP  PIC  S9(4).
    public NInt16 actidinl = new NInt16();
    // COB:            02  ACTIDINF    PICTURE X.
    public NChar actidinf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACTIDINF.
    public static class Filler57 extends NGroup {
      // COB:              03 ACTIDINA    PICTURE X.
      public NChar actidina = new NChar(1);
    }

    public Filler57 filler57 = (Filler57) new Filler57().redefines(actidinf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler59 = new NChar(4);
    // COB:            02  ACTIDINI  PIC X(11).
    public NChar actidini = new NChar(11);
    // COB:            02  CARDNINL    COMP  PIC  S9(4).
    public NInt16 cardninl = new NInt16();
    // COB:            02  CARDNINF    PICTURE X.
    public NChar cardninf = new NChar(1);

    // COB:            02  FILLER REDEFINES CARDNINF.
    public static class Filler63 extends NGroup {
      // COB:              03 CARDNINA    PICTURE X.
      public NChar cardnina = new NChar(1);
    }

    public Filler63 filler63 = (Filler63) new Filler63().redefines(cardninf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler65 = new NChar(4);
    // COB:            02  CARDNINI  PIC X(16).
    public NChar cardnini = new NChar(16);
    // COB:            02  TTYPCDL    COMP  PIC  S9(4).
    public NInt16 ttypcdl = new NInt16();
    // COB:            02  TTYPCDF    PICTURE X.
    public NChar ttypcdf = new NChar(1);

    // COB:            02  FILLER REDEFINES TTYPCDF.
    public static class Filler69 extends NGroup {
      // COB:              03 TTYPCDA    PICTURE X.
      public NChar ttypcda = new NChar(1);
    }

    public Filler69 filler69 = (Filler69) new Filler69().redefines(ttypcdf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler71 = new NChar(4);
    // COB:            02  TTYPCDI  PIC X(2).
    public NChar ttypcdi = new NChar(2);
    // COB:            02  TCATCDL    COMP  PIC  S9(4).
    public NInt16 tcatcdl = new NInt16();
    // COB:            02  TCATCDF    PICTURE X.
    public NChar tcatcdf = new NChar(1);

    // COB:            02  FILLER REDEFINES TCATCDF.
    public static class Filler75 extends NGroup {
      // COB:              03 TCATCDA    PICTURE X.
      public NChar tcatcda = new NChar(1);
    }

    public Filler75 filler75 = (Filler75) new Filler75().redefines(tcatcdf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler77 = new NChar(4);
    // COB:            02  TCATCDI  PIC X(4).
    public NChar tcatcdi = new NChar(4);
    // COB:            02  TRNSRCL    COMP  PIC  S9(4).
    public NInt16 trnsrcl = new NInt16();
    // COB:            02  TRNSRCF    PICTURE X.
    public NChar trnsrcf = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNSRCF.
    public static class Filler81 extends NGroup {
      // COB:              03 TRNSRCA    PICTURE X.
      public NChar trnsrca = new NChar(1);
    }

    public Filler81 filler81 = (Filler81) new Filler81().redefines(trnsrcf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler83 = new NChar(4);
    // COB:            02  TRNSRCI  PIC X(10).
    public NChar trnsrci = new NChar(10);
    // COB:            02  TDESCL    COMP  PIC  S9(4).
    public NInt16 tdescl = new NInt16();
    // COB:            02  TDESCF    PICTURE X.
    public NChar tdescf = new NChar(1);

    // COB:            02  FILLER REDEFINES TDESCF.
    public static class Filler87 extends NGroup {
      // COB:              03 TDESCA    PICTURE X.
      public NChar tdesca = new NChar(1);
    }

    public Filler87 filler87 = (Filler87) new Filler87().redefines(tdescf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler89 = new NChar(4);
    // COB:            02  TDESCI  PIC X(60).
    public NChar tdesci = new NChar(60);
    // COB:            02  TRNAMTL    COMP  PIC  S9(4).
    public NInt16 trnamtl = new NInt16();
    // COB:            02  TRNAMTF    PICTURE X.
    public NChar trnamtf = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNAMTF.
    public static class Filler93 extends NGroup {
      // COB:              03 TRNAMTA    PICTURE X.
      public NChar trnamta = new NChar(1);
    }

    public Filler93 filler93 = (Filler93) new Filler93().redefines(trnamtf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler95 = new NChar(4);
    // COB:            02  TRNAMTI  PIC X(12).
    public NChar trnamti = new NChar(12);
    // COB:            02  TORIGDTL    COMP  PIC  S9(4).
    public NInt16 torigdtl = new NInt16();
    // COB:            02  TORIGDTF    PICTURE X.
    public NChar torigdtf = new NChar(1);

    // COB:            02  FILLER REDEFINES TORIGDTF.
    public static class Filler99 extends NGroup {
      // COB:              03 TORIGDTA    PICTURE X.
      public NChar torigdta = new NChar(1);
    }

    public Filler99 filler99 = (Filler99) new Filler99().redefines(torigdtf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler101 = new NChar(4);
    // COB:            02  TORIGDTI  PIC X(10).
    public NChar torigdti = new NChar(10);
    // COB:            02  TPROCDTL    COMP  PIC  S9(4).
    public NInt16 tprocdtl = new NInt16();
    // COB:            02  TPROCDTF    PICTURE X.
    public NChar tprocdtf = new NChar(1);

    // COB:            02  FILLER REDEFINES TPROCDTF.
    public static class Filler105 extends NGroup {
      // COB:              03 TPROCDTA    PICTURE X.
      public NChar tprocdta = new NChar(1);
    }

    public Filler105 filler105 = (Filler105) new Filler105().redefines(tprocdtf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler107 = new NChar(4);
    // COB:            02  TPROCDTI  PIC X(10).
    public NChar tprocdti = new NChar(10);
    // COB:            02  MIDL    COMP  PIC  S9(4).
    public NInt16 midl = new NInt16();
    // COB:            02  MIDF    PICTURE X.
    public NChar midf = new NChar(1);

    // COB:            02  FILLER REDEFINES MIDF.
    public static class Filler111 extends NGroup {
      // COB:              03 MIDA    PICTURE X.
      public NChar mida = new NChar(1);
    }

    public Filler111 filler111 = (Filler111) new Filler111().redefines(midf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler113 = new NChar(4);
    // COB:            02  MIDI  PIC X(9).
    public NChar midi = new NChar(9);
    // COB:            02  MNAMEL    COMP  PIC  S9(4).
    public NInt16 mnamel = new NInt16();
    // COB:            02  MNAMEF    PICTURE X.
    public NChar mnamef = new NChar(1);

    // COB:            02  FILLER REDEFINES MNAMEF.
    public static class Filler117 extends NGroup {
      // COB:              03 MNAMEA    PICTURE X.
      public NChar mnamea = new NChar(1);
    }

    public Filler117 filler117 = (Filler117) new Filler117().redefines(mnamef);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler119 = new NChar(4);
    // COB:            02  MNAMEI  PIC X(30).
    public NChar mnamei = new NChar(30);
    // COB:            02  MCITYL    COMP  PIC  S9(4).
    public NInt16 mcityl = new NInt16();
    // COB:            02  MCITYF    PICTURE X.
    public NChar mcityf = new NChar(1);

    // COB:            02  FILLER REDEFINES MCITYF.
    public static class Filler123 extends NGroup {
      // COB:              03 MCITYA    PICTURE X.
      public NChar mcitya = new NChar(1);
    }

    public Filler123 filler123 = (Filler123) new Filler123().redefines(mcityf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler125 = new NChar(4);
    // COB:            02  MCITYI  PIC X(25).
    public NChar mcityi = new NChar(25);
    // COB:            02  MZIPL    COMP  PIC  S9(4).
    public NInt16 mzipl = new NInt16();
    // COB:            02  MZIPF    PICTURE X.
    public NChar mzipf = new NChar(1);

    // COB:            02  FILLER REDEFINES MZIPF.
    public static class Filler129 extends NGroup {
      // COB:              03 MZIPA    PICTURE X.
      public NChar mzipa = new NChar(1);
    }

    public Filler129 filler129 = (Filler129) new Filler129().redefines(mzipf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler131 = new NChar(4);
    // COB:            02  MZIPI  PIC X(10).
    public NChar mzipi = new NChar(10);
    // COB:            02  CONFIRML    COMP  PIC  S9(4).
    public NInt16 confirml = new NInt16();
    // COB:            02  CONFIRMF    PICTURE X.
    public NChar confirmf = new NChar(1);

    // COB:            02  FILLER REDEFINES CONFIRMF.
    public static class Filler135 extends NGroup {
      // COB:              03 CONFIRMA    PICTURE X.
      public NChar confirma = new NChar(1);
    }

    public Filler135 filler135 = (Filler135) new Filler135().redefines(confirmf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler137 = new NChar(4);
    // COB:            02  CONFIRMI  PIC X(1).
    public NChar confirmi = new NChar(1);
    // COB:            02  ERRMSGL    COMP  PIC  S9(4).
    public NInt16 errmsgl = new NInt16();
    // COB:            02  ERRMSGF    PICTURE X.
    public NChar errmsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES ERRMSGF.
    public static class Filler141 extends NGroup {
      // COB:              03 ERRMSGA    PICTURE X.
      public NChar errmsga = new NChar(1);
    }

    public Filler141 filler141 = (Filler141) new Filler141().redefines(errmsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler143 = new NChar(4);
    // COB:            02  ERRMSGI  PIC X(78).
    public NChar errmsgi = new NChar(78);
  }

  public Cotrn2ai cotrn2ai = new Cotrn2ai();

  // COB:        01  COTRN2AO REDEFINES COTRN2AI.
  public static class Cotrn2ao extends NGroup {
    // COB:            02  FILLER PIC X(12).
    public NChar filler146 = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler147 = new NChar(3);
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
    public NChar filler153 = new NChar(3);
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
    public NChar filler159 = new NChar(3);
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
    public NChar filler165 = new NChar(3);
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
    public NChar filler171 = new NChar(3);
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
    public NChar filler177 = new NChar(3);
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
    public NChar filler183 = new NChar(3);
    // COB:            02  ACTIDINC    PICTURE X.
    public NChar actidinc = new NChar(1);
    // COB:            02  ACTIDINP    PICTURE X.
    public NChar actidinp = new NChar(1);
    // COB:            02  ACTIDINH    PICTURE X.
    public NChar actidinh = new NChar(1);
    // COB:            02  ACTIDINV    PICTURE X.
    public NChar actidinv = new NChar(1);
    // COB:            02  ACTIDINO  PIC X(11).
    public NChar actidino = new NChar(11);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler189 = new NChar(3);
    // COB:            02  CARDNINC    PICTURE X.
    public NChar cardninc = new NChar(1);
    // COB:            02  CARDNINP    PICTURE X.
    public NChar cardninp = new NChar(1);
    // COB:            02  CARDNINH    PICTURE X.
    public NChar cardninh = new NChar(1);
    // COB:            02  CARDNINV    PICTURE X.
    public NChar cardninv = new NChar(1);
    // COB:            02  CARDNINO  PIC X(16).
    public NChar cardnino = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler195 = new NChar(3);
    // COB:            02  TTYPCDC    PICTURE X.
    public NChar ttypcdc = new NChar(1);
    // COB:            02  TTYPCDP    PICTURE X.
    public NChar ttypcdp = new NChar(1);
    // COB:            02  TTYPCDH    PICTURE X.
    public NChar ttypcdh = new NChar(1);
    // COB:            02  TTYPCDV    PICTURE X.
    public NChar ttypcdv = new NChar(1);
    // COB:            02  TTYPCDO  PIC X(2).
    public NChar ttypcdo = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler201 = new NChar(3);
    // COB:            02  TCATCDC    PICTURE X.
    public NChar tcatcdc = new NChar(1);
    // COB:            02  TCATCDP    PICTURE X.
    public NChar tcatcdp = new NChar(1);
    // COB:            02  TCATCDH    PICTURE X.
    public NChar tcatcdh = new NChar(1);
    // COB:            02  TCATCDV    PICTURE X.
    public NChar tcatcdv = new NChar(1);
    // COB:            02  TCATCDO  PIC X(4).
    public NChar tcatcdo = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler207 = new NChar(3);
    // COB:            02  TRNSRCC    PICTURE X.
    public NChar trnsrcc = new NChar(1);
    // COB:            02  TRNSRCP    PICTURE X.
    public NChar trnsrcp = new NChar(1);
    // COB:            02  TRNSRCH    PICTURE X.
    public NChar trnsrch = new NChar(1);
    // COB:            02  TRNSRCV    PICTURE X.
    public NChar trnsrcv = new NChar(1);
    // COB:            02  TRNSRCO  PIC X(10).
    public NChar trnsrco = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler213 = new NChar(3);
    // COB:            02  TDESCC    PICTURE X.
    public NChar tdescc = new NChar(1);
    // COB:            02  TDESCP    PICTURE X.
    public NChar tdescp = new NChar(1);
    // COB:            02  TDESCH    PICTURE X.
    public NChar tdesch = new NChar(1);
    // COB:            02  TDESCV    PICTURE X.
    public NChar tdescv = new NChar(1);
    // COB:            02  TDESCO  PIC X(60).
    public NChar tdesco = new NChar(60);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler219 = new NChar(3);
    // COB:            02  TRNAMTC    PICTURE X.
    public NChar trnamtc = new NChar(1);
    // COB:            02  TRNAMTP    PICTURE X.
    public NChar trnamtp = new NChar(1);
    // COB:            02  TRNAMTH    PICTURE X.
    public NChar trnamth = new NChar(1);
    // COB:            02  TRNAMTV    PICTURE X.
    public NChar trnamtv = new NChar(1);
    // COB:            02  TRNAMTO  PIC X(12).
    public NChar trnamto = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler225 = new NChar(3);
    // COB:            02  TORIGDTC    PICTURE X.
    public NChar torigdtc = new NChar(1);
    // COB:            02  TORIGDTP    PICTURE X.
    public NChar torigdtp = new NChar(1);
    // COB:            02  TORIGDTH    PICTURE X.
    public NChar torigdth = new NChar(1);
    // COB:            02  TORIGDTV    PICTURE X.
    public NChar torigdtv = new NChar(1);
    // COB:            02  TORIGDTO  PIC X(10).
    public NChar torigdto = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler231 = new NChar(3);
    // COB:            02  TPROCDTC    PICTURE X.
    public NChar tprocdtc = new NChar(1);
    // COB:            02  TPROCDTP    PICTURE X.
    public NChar tprocdtp = new NChar(1);
    // COB:            02  TPROCDTH    PICTURE X.
    public NChar tprocdth = new NChar(1);
    // COB:            02  TPROCDTV    PICTURE X.
    public NChar tprocdtv = new NChar(1);
    // COB:            02  TPROCDTO  PIC X(10).
    public NChar tprocdto = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler237 = new NChar(3);
    // COB:            02  MIDC    PICTURE X.
    public NChar midc = new NChar(1);
    // COB:            02  MIDP    PICTURE X.
    public NChar midp = new NChar(1);
    // COB:            02  MIDH    PICTURE X.
    public NChar midh = new NChar(1);
    // COB:            02  MIDV    PICTURE X.
    public NChar midv = new NChar(1);
    // COB:            02  MIDO  PIC X(9).
    public NChar mido = new NChar(9);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler243 = new NChar(3);
    // COB:            02  MNAMEC    PICTURE X.
    public NChar mnamec = new NChar(1);
    // COB:            02  MNAMEP    PICTURE X.
    public NChar mnamep = new NChar(1);
    // COB:            02  MNAMEH    PICTURE X.
    public NChar mnameh = new NChar(1);
    // COB:            02  MNAMEV    PICTURE X.
    public NChar mnamev = new NChar(1);
    // COB:            02  MNAMEO  PIC X(30).
    public NChar mnameo = new NChar(30);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler249 = new NChar(3);
    // COB:            02  MCITYC    PICTURE X.
    public NChar mcityc = new NChar(1);
    // COB:            02  MCITYP    PICTURE X.
    public NChar mcityp = new NChar(1);
    // COB:            02  MCITYH    PICTURE X.
    public NChar mcityh = new NChar(1);
    // COB:            02  MCITYV    PICTURE X.
    public NChar mcityv = new NChar(1);
    // COB:            02  MCITYO  PIC X(25).
    public NChar mcityo = new NChar(25);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler255 = new NChar(3);
    // COB:            02  MZIPC    PICTURE X.
    public NChar mzipc = new NChar(1);
    // COB:            02  MZIPP    PICTURE X.
    public NChar mzipp = new NChar(1);
    // COB:            02  MZIPH    PICTURE X.
    public NChar mziph = new NChar(1);
    // COB:            02  MZIPV    PICTURE X.
    public NChar mzipv = new NChar(1);
    // COB:            02  MZIPO  PIC X(10).
    public NChar mzipo = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler261 = new NChar(3);
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
    public NChar filler267 = new NChar(3);
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

  public Cotrn2ao cotrn2ao = (Cotrn2ao) new Cotrn2ao().redefines(cotrn2ai);
}
