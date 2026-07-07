package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cocrdsl extends NGroup {

  // COB:        01  CCRDSLAI.
  public static class Ccrdslai extends NGroup {
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
    // COB:            02  ACCTSIDL    COMP  PIC  S9(4).
    public NInt16 acctsidl = new NInt16();
    // COB:            02  ACCTSIDF    PICTURE X.
    public NChar acctsidf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACCTSIDF.
    public static class Filler57 extends NGroup {
      // COB:              03 ACCTSIDA    PICTURE X.
      public NChar acctsida = new NChar(1);
    }

    public Filler57 filler57 = (Filler57) new Filler57().redefines(acctsidf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler59 = new NChar(4);
    // COB:            02  ACCTSIDI  PIC X(11).
    public NChar acctsidi = new NChar(11);
    // COB:            02  CARDSIDL    COMP  PIC  S9(4).
    public NInt16 cardsidl = new NInt16();
    // COB:            02  CARDSIDF    PICTURE X.
    public NChar cardsidf = new NChar(1);

    // COB:            02  FILLER REDEFINES CARDSIDF.
    public static class Filler63 extends NGroup {
      // COB:              03 CARDSIDA    PICTURE X.
      public NChar cardsida = new NChar(1);
    }

    public Filler63 filler63 = (Filler63) new Filler63().redefines(cardsidf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler65 = new NChar(4);
    // COB:            02  CARDSIDI  PIC X(16).
    public NChar cardsidi = new NChar(16);
    // COB:            02  CRDNAMEL    COMP  PIC  S9(4).
    public NInt16 crdnamel = new NInt16();
    // COB:            02  CRDNAMEF    PICTURE X.
    public NChar crdnamef = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDNAMEF.
    public static class Filler69 extends NGroup {
      // COB:              03 CRDNAMEA    PICTURE X.
      public NChar crdnamea = new NChar(1);
    }

    public Filler69 filler69 = (Filler69) new Filler69().redefines(crdnamef);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler71 = new NChar(4);
    // COB:            02  CRDNAMEI  PIC X(50).
    public NChar crdnamei = new NChar(50);
    // COB:            02  CRDSTCDL    COMP  PIC  S9(4).
    public NInt16 crdstcdl = new NInt16();
    // COB:            02  CRDSTCDF    PICTURE X.
    public NChar crdstcdf = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSTCDF.
    public static class Filler75 extends NGroup {
      // COB:              03 CRDSTCDA    PICTURE X.
      public NChar crdstcda = new NChar(1);
    }

    public Filler75 filler75 = (Filler75) new Filler75().redefines(crdstcdf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler77 = new NChar(4);
    // COB:            02  CRDSTCDI  PIC X(1).
    public NChar crdstcdi = new NChar(1);
    // COB:            02  EXPMONL    COMP  PIC  S9(4).
    public NInt16 expmonl = new NInt16();
    // COB:            02  EXPMONF    PICTURE X.
    public NChar expmonf = new NChar(1);

    // COB:            02  FILLER REDEFINES EXPMONF.
    public static class Filler81 extends NGroup {
      // COB:              03 EXPMONA    PICTURE X.
      public NChar expmona = new NChar(1);
    }

    public Filler81 filler81 = (Filler81) new Filler81().redefines(expmonf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler83 = new NChar(4);
    // COB:            02  EXPMONI  PIC X(2).
    public NChar expmoni = new NChar(2);
    // COB:            02  EXPYEARL    COMP  PIC  S9(4).
    public NInt16 expyearl = new NInt16();
    // COB:            02  EXPYEARF    PICTURE X.
    public NChar expyearf = new NChar(1);

    // COB:            02  FILLER REDEFINES EXPYEARF.
    public static class Filler87 extends NGroup {
      // COB:              03 EXPYEARA    PICTURE X.
      public NChar expyeara = new NChar(1);
    }

    public Filler87 filler87 = (Filler87) new Filler87().redefines(expyearf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler89 = new NChar(4);
    // COB:            02  EXPYEARI  PIC X(4).
    public NChar expyeari = new NChar(4);
    // COB:            02  INFOMSGL    COMP  PIC  S9(4).
    public NInt16 infomsgl = new NInt16();
    // COB:            02  INFOMSGF    PICTURE X.
    public NChar infomsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES INFOMSGF.
    public static class Filler93 extends NGroup {
      // COB:              03 INFOMSGA    PICTURE X.
      public NChar infomsga = new NChar(1);
    }

    public Filler93 filler93 = (Filler93) new Filler93().redefines(infomsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler95 = new NChar(4);
    // COB:            02  INFOMSGI  PIC X(40).
    public NChar infomsgi = new NChar(40);
    // COB:            02  ERRMSGL    COMP  PIC  S9(4).
    public NInt16 errmsgl = new NInt16();
    // COB:            02  ERRMSGF    PICTURE X.
    public NChar errmsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES ERRMSGF.
    public static class Filler99 extends NGroup {
      // COB:              03 ERRMSGA    PICTURE X.
      public NChar errmsga = new NChar(1);
    }

    public Filler99 filler99 = (Filler99) new Filler99().redefines(errmsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler101 = new NChar(4);
    // COB:            02  ERRMSGI  PIC X(80).
    public NChar errmsgi = new NChar(80);
    // COB:            02  FKEYSL    COMP  PIC  S9(4).
    public NInt16 fkeysl = new NInt16();
    // COB:            02  FKEYSF    PICTURE X.
    public NChar fkeysf = new NChar(1);

    // COB:            02  FILLER REDEFINES FKEYSF.
    public static class Filler105 extends NGroup {
      // COB:              03 FKEYSA    PICTURE X.
      public NChar fkeysa = new NChar(1);
    }

    public Filler105 filler105 = (Filler105) new Filler105().redefines(fkeysf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler107 = new NChar(4);
    // COB:            02  FKEYSI  PIC X(75).
    public NChar fkeysi = new NChar(75);
  }

  public Ccrdslai ccrdslai = new Ccrdslai();

  // COB:        01  CCRDSLAO REDEFINES CCRDSLAI.
  public static class Ccrdslao extends NGroup {
    // COB:            02  FILLER PIC X(12).
    public NChar filler110 = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler111 = new NChar(3);
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
    public NChar filler117 = new NChar(3);
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
    public NChar filler123 = new NChar(3);
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
    public NChar filler129 = new NChar(3);
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
    public NChar filler135 = new NChar(3);
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
    public NChar filler141 = new NChar(3);
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
    public NChar filler147 = new NChar(3);
    // COB:            02  ACCTSIDC    PICTURE X.
    public NChar acctsidc = new NChar(1);
    // COB:            02  ACCTSIDP    PICTURE X.
    public NChar acctsidp = new NChar(1);
    // COB:            02  ACCTSIDH    PICTURE X.
    public NChar acctsidh = new NChar(1);
    // COB:            02  ACCTSIDV    PICTURE X.
    public NChar acctsidv = new NChar(1);
    // COB:            02  ACCTSIDO  PIC X(11).
    public NChar acctsido = new NChar(11);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler153 = new NChar(3);
    // COB:            02  CARDSIDC    PICTURE X.
    public NChar cardsidc = new NChar(1);
    // COB:            02  CARDSIDP    PICTURE X.
    public NChar cardsidp = new NChar(1);
    // COB:            02  CARDSIDH    PICTURE X.
    public NChar cardsidh = new NChar(1);
    // COB:            02  CARDSIDV    PICTURE X.
    public NChar cardsidv = new NChar(1);
    // COB:            02  CARDSIDO  PIC X(16).
    public NChar cardsido = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler159 = new NChar(3);
    // COB:            02  CRDNAMEC    PICTURE X.
    public NChar crdnamec = new NChar(1);
    // COB:            02  CRDNAMEP    PICTURE X.
    public NChar crdnamep = new NChar(1);
    // COB:            02  CRDNAMEH    PICTURE X.
    public NChar crdnameh = new NChar(1);
    // COB:            02  CRDNAMEV    PICTURE X.
    public NChar crdnamev = new NChar(1);
    // COB:            02  CRDNAMEO  PIC X(50).
    public NChar crdnameo = new NChar(50);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler165 = new NChar(3);
    // COB:            02  CRDSTCDC    PICTURE X.
    public NChar crdstcdc = new NChar(1);
    // COB:            02  CRDSTCDP    PICTURE X.
    public NChar crdstcdp = new NChar(1);
    // COB:            02  CRDSTCDH    PICTURE X.
    public NChar crdstcdh = new NChar(1);
    // COB:            02  CRDSTCDV    PICTURE X.
    public NChar crdstcdv = new NChar(1);
    // COB:            02  CRDSTCDO  PIC X(1).
    public NChar crdstcdo = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler171 = new NChar(3);
    // COB:            02  EXPMONC    PICTURE X.
    public NChar expmonc = new NChar(1);
    // COB:            02  EXPMONP    PICTURE X.
    public NChar expmonp = new NChar(1);
    // COB:            02  EXPMONH    PICTURE X.
    public NChar expmonh = new NChar(1);
    // COB:            02  EXPMONV    PICTURE X.
    public NChar expmonv = new NChar(1);
    // COB:            02  EXPMONO  PIC X(2).
    public NChar expmono = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler177 = new NChar(3);
    // COB:            02  EXPYEARC    PICTURE X.
    public NChar expyearc = new NChar(1);
    // COB:            02  EXPYEARP    PICTURE X.
    public NChar expyearp = new NChar(1);
    // COB:            02  EXPYEARH    PICTURE X.
    public NChar expyearh = new NChar(1);
    // COB:            02  EXPYEARV    PICTURE X.
    public NChar expyearv = new NChar(1);
    // COB:            02  EXPYEARO  PIC X(4).
    public NChar expyearo = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler183 = new NChar(3);
    // COB:            02  INFOMSGC    PICTURE X.
    public NChar infomsgc = new NChar(1);
    // COB:            02  INFOMSGP    PICTURE X.
    public NChar infomsgp = new NChar(1);
    // COB:            02  INFOMSGH    PICTURE X.
    public NChar infomsgh = new NChar(1);
    // COB:            02  INFOMSGV    PICTURE X.
    public NChar infomsgv = new NChar(1);
    // COB:            02  INFOMSGO  PIC X(40).
    public NChar infomsgo = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler189 = new NChar(3);
    // COB:            02  ERRMSGC    PICTURE X.
    public NChar errmsgc = new NChar(1);
    // COB:            02  ERRMSGP    PICTURE X.
    public NChar errmsgp = new NChar(1);
    // COB:            02  ERRMSGH    PICTURE X.
    public NChar errmsgh = new NChar(1);
    // COB:            02  ERRMSGV    PICTURE X.
    public NChar errmsgv = new NChar(1);
    // COB:            02  ERRMSGO  PIC X(80).
    public NChar errmsgo = new NChar(80);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler195 = new NChar(3);
    // COB:            02  FKEYSC    PICTURE X.
    public NChar fkeysc = new NChar(1);
    // COB:            02  FKEYSP    PICTURE X.
    public NChar fkeysp = new NChar(1);
    // COB:            02  FKEYSH    PICTURE X.
    public NChar fkeysh = new NChar(1);
    // COB:            02  FKEYSV    PICTURE X.
    public NChar fkeysv = new NChar(1);
    // COB:            02  FKEYSO  PIC X(75).
    public NChar fkeyso = new NChar(75);
  }

  public Ccrdslao ccrdslao = (Ccrdslao) new Ccrdslao().redefines(ccrdslai);
}
