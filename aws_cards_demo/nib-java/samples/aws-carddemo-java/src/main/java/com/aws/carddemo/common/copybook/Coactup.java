package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Coactup extends NGroup {

  // COB:        01  CACTUPAI.
  public static class Cactupai extends NGroup {
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
    // COB:            02  ACSTTUSL    COMP  PIC  S9(4).
    public NInt16 acsttusl = new NInt16();
    // COB:            02  ACSTTUSF    PICTURE X.
    public NChar acsttusf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSTTUSF.
    public static class Filler63 extends NGroup {
      // COB:              03 ACSTTUSA    PICTURE X.
      public NChar acsttusa = new NChar(1);
    }

    public Filler63 filler63 = (Filler63) new Filler63().redefines(acsttusf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler65 = new NChar(4);
    // COB:            02  ACSTTUSI  PIC X(1).
    public NChar acsttusi = new NChar(1);
    // COB:            02  OPNYEARL    COMP  PIC  S9(4).
    public NInt16 opnyearl = new NInt16();
    // COB:            02  OPNYEARF    PICTURE X.
    public NChar opnyearf = new NChar(1);

    // COB:            02  FILLER REDEFINES OPNYEARF.
    public static class Filler69 extends NGroup {
      // COB:              03 OPNYEARA    PICTURE X.
      public NChar opnyeara = new NChar(1);
    }

    public Filler69 filler69 = (Filler69) new Filler69().redefines(opnyearf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler71 = new NChar(4);
    // COB:            02  OPNYEARI  PIC X(4).
    public NChar opnyeari = new NChar(4);
    // COB:            02  OPNMONL    COMP  PIC  S9(4).
    public NInt16 opnmonl = new NInt16();
    // COB:            02  OPNMONF    PICTURE X.
    public NChar opnmonf = new NChar(1);

    // COB:            02  FILLER REDEFINES OPNMONF.
    public static class Filler75 extends NGroup {
      // COB:              03 OPNMONA    PICTURE X.
      public NChar opnmona = new NChar(1);
    }

    public Filler75 filler75 = (Filler75) new Filler75().redefines(opnmonf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler77 = new NChar(4);
    // COB:            02  OPNMONI  PIC X(2).
    public NChar opnmoni = new NChar(2);
    // COB:            02  OPNDAYL    COMP  PIC  S9(4).
    public NInt16 opndayl = new NInt16();
    // COB:            02  OPNDAYF    PICTURE X.
    public NChar opndayf = new NChar(1);

    // COB:            02  FILLER REDEFINES OPNDAYF.
    public static class Filler81 extends NGroup {
      // COB:              03 OPNDAYA    PICTURE X.
      public NChar opndaya = new NChar(1);
    }

    public Filler81 filler81 = (Filler81) new Filler81().redefines(opndayf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler83 = new NChar(4);
    // COB:            02  OPNDAYI  PIC X(2).
    public NChar opndayi = new NChar(2);
    // COB:            02  ACRDLIML    COMP  PIC  S9(4).
    public NInt16 acrdliml = new NInt16();
    // COB:            02  ACRDLIMF    PICTURE X.
    public NChar acrdlimf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACRDLIMF.
    public static class Filler87 extends NGroup {
      // COB:              03 ACRDLIMA    PICTURE X.
      public NChar acrdlima = new NChar(1);
    }

    public Filler87 filler87 = (Filler87) new Filler87().redefines(acrdlimf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler89 = new NChar(4);
    // COB:            02  ACRDLIMI  PIC X(15).
    public NChar acrdlimi = new NChar(15);
    // COB:            02  EXPYEARL    COMP  PIC  S9(4).
    public NInt16 expyearl = new NInt16();
    // COB:            02  EXPYEARF    PICTURE X.
    public NChar expyearf = new NChar(1);

    // COB:            02  FILLER REDEFINES EXPYEARF.
    public static class Filler93 extends NGroup {
      // COB:              03 EXPYEARA    PICTURE X.
      public NChar expyeara = new NChar(1);
    }

    public Filler93 filler93 = (Filler93) new Filler93().redefines(expyearf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler95 = new NChar(4);
    // COB:            02  EXPYEARI  PIC X(4).
    public NChar expyeari = new NChar(4);
    // COB:            02  EXPMONL    COMP  PIC  S9(4).
    public NInt16 expmonl = new NInt16();
    // COB:            02  EXPMONF    PICTURE X.
    public NChar expmonf = new NChar(1);

    // COB:            02  FILLER REDEFINES EXPMONF.
    public static class Filler99 extends NGroup {
      // COB:              03 EXPMONA    PICTURE X.
      public NChar expmona = new NChar(1);
    }

    public Filler99 filler99 = (Filler99) new Filler99().redefines(expmonf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler101 = new NChar(4);
    // COB:            02  EXPMONI  PIC X(2).
    public NChar expmoni = new NChar(2);
    // COB:            02  EXPDAYL    COMP  PIC  S9(4).
    public NInt16 expdayl = new NInt16();
    // COB:            02  EXPDAYF    PICTURE X.
    public NChar expdayf = new NChar(1);

    // COB:            02  FILLER REDEFINES EXPDAYF.
    public static class Filler105 extends NGroup {
      // COB:              03 EXPDAYA    PICTURE X.
      public NChar expdaya = new NChar(1);
    }

    public Filler105 filler105 = (Filler105) new Filler105().redefines(expdayf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler107 = new NChar(4);
    // COB:            02  EXPDAYI  PIC X(2).
    public NChar expdayi = new NChar(2);
    // COB:            02  ACSHLIML    COMP  PIC  S9(4).
    public NInt16 acshliml = new NInt16();
    // COB:            02  ACSHLIMF    PICTURE X.
    public NChar acshlimf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSHLIMF.
    public static class Filler111 extends NGroup {
      // COB:              03 ACSHLIMA    PICTURE X.
      public NChar acshlima = new NChar(1);
    }

    public Filler111 filler111 = (Filler111) new Filler111().redefines(acshlimf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler113 = new NChar(4);
    // COB:            02  ACSHLIMI  PIC X(15).
    public NChar acshlimi = new NChar(15);
    // COB:            02  RISYEARL    COMP  PIC  S9(4).
    public NInt16 risyearl = new NInt16();
    // COB:            02  RISYEARF    PICTURE X.
    public NChar risyearf = new NChar(1);

    // COB:            02  FILLER REDEFINES RISYEARF.
    public static class Filler117 extends NGroup {
      // COB:              03 RISYEARA    PICTURE X.
      public NChar risyeara = new NChar(1);
    }

    public Filler117 filler117 = (Filler117) new Filler117().redefines(risyearf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler119 = new NChar(4);
    // COB:            02  RISYEARI  PIC X(4).
    public NChar risyeari = new NChar(4);
    // COB:            02  RISMONL    COMP  PIC  S9(4).
    public NInt16 rismonl = new NInt16();
    // COB:            02  RISMONF    PICTURE X.
    public NChar rismonf = new NChar(1);

    // COB:            02  FILLER REDEFINES RISMONF.
    public static class Filler123 extends NGroup {
      // COB:              03 RISMONA    PICTURE X.
      public NChar rismona = new NChar(1);
    }

    public Filler123 filler123 = (Filler123) new Filler123().redefines(rismonf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler125 = new NChar(4);
    // COB:            02  RISMONI  PIC X(2).
    public NChar rismoni = new NChar(2);
    // COB:            02  RISDAYL    COMP  PIC  S9(4).
    public NInt16 risdayl = new NInt16();
    // COB:            02  RISDAYF    PICTURE X.
    public NChar risdayf = new NChar(1);

    // COB:            02  FILLER REDEFINES RISDAYF.
    public static class Filler129 extends NGroup {
      // COB:              03 RISDAYA    PICTURE X.
      public NChar risdaya = new NChar(1);
    }

    public Filler129 filler129 = (Filler129) new Filler129().redefines(risdayf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler131 = new NChar(4);
    // COB:            02  RISDAYI  PIC X(2).
    public NChar risdayi = new NChar(2);
    // COB:            02  ACURBALL    COMP  PIC  S9(4).
    public NInt16 acurball = new NInt16();
    // COB:            02  ACURBALF    PICTURE X.
    public NChar acurbalf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACURBALF.
    public static class Filler135 extends NGroup {
      // COB:              03 ACURBALA    PICTURE X.
      public NChar acurbala = new NChar(1);
    }

    public Filler135 filler135 = (Filler135) new Filler135().redefines(acurbalf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler137 = new NChar(4);
    // COB:            02  ACURBALI  PIC X(15).
    public NChar acurbali = new NChar(15);
    // COB:            02  ACRCYCRL    COMP  PIC  S9(4).
    public NInt16 acrcycrl = new NInt16();
    // COB:            02  ACRCYCRF    PICTURE X.
    public NChar acrcycrf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACRCYCRF.
    public static class Filler141 extends NGroup {
      // COB:              03 ACRCYCRA    PICTURE X.
      public NChar acrcycra = new NChar(1);
    }

    public Filler141 filler141 = (Filler141) new Filler141().redefines(acrcycrf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler143 = new NChar(4);
    // COB:            02  ACRCYCRI  PIC X(15).
    public NChar acrcycri = new NChar(15);
    // COB:            02  AADDGRPL    COMP  PIC  S9(4).
    public NInt16 aaddgrpl = new NInt16();
    // COB:            02  AADDGRPF    PICTURE X.
    public NChar aaddgrpf = new NChar(1);

    // COB:            02  FILLER REDEFINES AADDGRPF.
    public static class Filler147 extends NGroup {
      // COB:              03 AADDGRPA    PICTURE X.
      public NChar aaddgrpa = new NChar(1);
    }

    public Filler147 filler147 = (Filler147) new Filler147().redefines(aaddgrpf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler149 = new NChar(4);
    // COB:            02  AADDGRPI  PIC X(10).
    public NChar aaddgrpi = new NChar(10);
    // COB:            02  ACRCYDBL    COMP  PIC  S9(4).
    public NInt16 acrcydbl = new NInt16();
    // COB:            02  ACRCYDBF    PICTURE X.
    public NChar acrcydbf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACRCYDBF.
    public static class Filler153 extends NGroup {
      // COB:              03 ACRCYDBA    PICTURE X.
      public NChar acrcydba = new NChar(1);
    }

    public Filler153 filler153 = (Filler153) new Filler153().redefines(acrcydbf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler155 = new NChar(4);
    // COB:            02  ACRCYDBI  PIC X(15).
    public NChar acrcydbi = new NChar(15);
    // COB:            02  ACSTNUML    COMP  PIC  S9(4).
    public NInt16 acstnuml = new NInt16();
    // COB:            02  ACSTNUMF    PICTURE X.
    public NChar acstnumf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSTNUMF.
    public static class Filler159 extends NGroup {
      // COB:              03 ACSTNUMA    PICTURE X.
      public NChar acstnuma = new NChar(1);
    }

    public Filler159 filler159 = (Filler159) new Filler159().redefines(acstnumf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler161 = new NChar(4);
    // COB:            02  ACSTNUMI  PIC X(9).
    public NChar acstnumi = new NChar(9);
    // COB:            02  ACTSSN1L    COMP  PIC  S9(4).
    public NInt16 actssn1l = new NInt16();
    // COB:            02  ACTSSN1F    PICTURE X.
    public NChar actssn1f = new NChar(1);

    // COB:            02  FILLER REDEFINES ACTSSN1F.
    public static class Filler165 extends NGroup {
      // COB:              03 ACTSSN1A    PICTURE X.
      public NChar actssn1a = new NChar(1);
    }

    public Filler165 filler165 = (Filler165) new Filler165().redefines(actssn1f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler167 = new NChar(4);
    // COB:            02  ACTSSN1I  PIC X(3).
    public NChar actssn1i = new NChar(3);
    // COB:            02  ACTSSN2L    COMP  PIC  S9(4).
    public NInt16 actssn2l = new NInt16();
    // COB:            02  ACTSSN2F    PICTURE X.
    public NChar actssn2f = new NChar(1);

    // COB:            02  FILLER REDEFINES ACTSSN2F.
    public static class Filler171 extends NGroup {
      // COB:              03 ACTSSN2A    PICTURE X.
      public NChar actssn2a = new NChar(1);
    }

    public Filler171 filler171 = (Filler171) new Filler171().redefines(actssn2f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler173 = new NChar(4);
    // COB:            02  ACTSSN2I  PIC X(2).
    public NChar actssn2i = new NChar(2);
    // COB:            02  ACTSSN3L    COMP  PIC  S9(4).
    public NInt16 actssn3l = new NInt16();
    // COB:            02  ACTSSN3F    PICTURE X.
    public NChar actssn3f = new NChar(1);

    // COB:            02  FILLER REDEFINES ACTSSN3F.
    public static class Filler177 extends NGroup {
      // COB:              03 ACTSSN3A    PICTURE X.
      public NChar actssn3a = new NChar(1);
    }

    public Filler177 filler177 = (Filler177) new Filler177().redefines(actssn3f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler179 = new NChar(4);
    // COB:            02  ACTSSN3I  PIC X(4).
    public NChar actssn3i = new NChar(4);
    // COB:            02  DOBYEARL    COMP  PIC  S9(4).
    public NInt16 dobyearl = new NInt16();
    // COB:            02  DOBYEARF    PICTURE X.
    public NChar dobyearf = new NChar(1);

    // COB:            02  FILLER REDEFINES DOBYEARF.
    public static class Filler183 extends NGroup {
      // COB:              03 DOBYEARA    PICTURE X.
      public NChar dobyeara = new NChar(1);
    }

    public Filler183 filler183 = (Filler183) new Filler183().redefines(dobyearf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler185 = new NChar(4);
    // COB:            02  DOBYEARI  PIC X(4).
    public NChar dobyeari = new NChar(4);
    // COB:            02  DOBMONL    COMP  PIC  S9(4).
    public NInt16 dobmonl = new NInt16();
    // COB:            02  DOBMONF    PICTURE X.
    public NChar dobmonf = new NChar(1);

    // COB:            02  FILLER REDEFINES DOBMONF.
    public static class Filler189 extends NGroup {
      // COB:              03 DOBMONA    PICTURE X.
      public NChar dobmona = new NChar(1);
    }

    public Filler189 filler189 = (Filler189) new Filler189().redefines(dobmonf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler191 = new NChar(4);
    // COB:            02  DOBMONI  PIC X(2).
    public NChar dobmoni = new NChar(2);
    // COB:            02  DOBDAYL    COMP  PIC  S9(4).
    public NInt16 dobdayl = new NInt16();
    // COB:            02  DOBDAYF    PICTURE X.
    public NChar dobdayf = new NChar(1);

    // COB:            02  FILLER REDEFINES DOBDAYF.
    public static class Filler195 extends NGroup {
      // COB:              03 DOBDAYA    PICTURE X.
      public NChar dobdaya = new NChar(1);
    }

    public Filler195 filler195 = (Filler195) new Filler195().redefines(dobdayf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler197 = new NChar(4);
    // COB:            02  DOBDAYI  PIC X(2).
    public NChar dobdayi = new NChar(2);
    // COB:            02  ACSTFCOL    COMP  PIC  S9(4).
    public NInt16 acstfcol = new NInt16();
    // COB:            02  ACSTFCOF    PICTURE X.
    public NChar acstfcof = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSTFCOF.
    public static class Filler201 extends NGroup {
      // COB:              03 ACSTFCOA    PICTURE X.
      public NChar acstfcoa = new NChar(1);
    }

    public Filler201 filler201 = (Filler201) new Filler201().redefines(acstfcof);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler203 = new NChar(4);
    // COB:            02  ACSTFCOI  PIC X(3).
    public NChar acstfcoi = new NChar(3);
    // COB:            02  ACSFNAML    COMP  PIC  S9(4).
    public NInt16 acsfnaml = new NInt16();
    // COB:            02  ACSFNAMF    PICTURE X.
    public NChar acsfnamf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSFNAMF.
    public static class Filler207 extends NGroup {
      // COB:              03 ACSFNAMA    PICTURE X.
      public NChar acsfnama = new NChar(1);
    }

    public Filler207 filler207 = (Filler207) new Filler207().redefines(acsfnamf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler209 = new NChar(4);
    // COB:            02  ACSFNAMI  PIC X(25).
    public NChar acsfnami = new NChar(25);
    // COB:            02  ACSMNAML    COMP  PIC  S9(4).
    public NInt16 acsmnaml = new NInt16();
    // COB:            02  ACSMNAMF    PICTURE X.
    public NChar acsmnamf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSMNAMF.
    public static class Filler213 extends NGroup {
      // COB:              03 ACSMNAMA    PICTURE X.
      public NChar acsmnama = new NChar(1);
    }

    public Filler213 filler213 = (Filler213) new Filler213().redefines(acsmnamf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler215 = new NChar(4);
    // COB:            02  ACSMNAMI  PIC X(25).
    public NChar acsmnami = new NChar(25);
    // COB:            02  ACSLNAML    COMP  PIC  S9(4).
    public NInt16 acslnaml = new NInt16();
    // COB:            02  ACSLNAMF    PICTURE X.
    public NChar acslnamf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSLNAMF.
    public static class Filler219 extends NGroup {
      // COB:              03 ACSLNAMA    PICTURE X.
      public NChar acslnama = new NChar(1);
    }

    public Filler219 filler219 = (Filler219) new Filler219().redefines(acslnamf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler221 = new NChar(4);
    // COB:            02  ACSLNAMI  PIC X(25).
    public NChar acslnami = new NChar(25);
    // COB:            02  ACSADL1L    COMP  PIC  S9(4).
    public NInt16 acsadl1l = new NInt16();
    // COB:            02  ACSADL1F    PICTURE X.
    public NChar acsadl1f = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSADL1F.
    public static class Filler225 extends NGroup {
      // COB:              03 ACSADL1A    PICTURE X.
      public NChar acsadl1a = new NChar(1);
    }

    public Filler225 filler225 = (Filler225) new Filler225().redefines(acsadl1f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler227 = new NChar(4);
    // COB:            02  ACSADL1I  PIC X(50).
    public NChar acsadl1i = new NChar(50);
    // COB:            02  ACSSTTEL    COMP  PIC  S9(4).
    public NInt16 acssttel = new NInt16();
    // COB:            02  ACSSTTEF    PICTURE X.
    public NChar acssttef = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSSTTEF.
    public static class Filler231 extends NGroup {
      // COB:              03 ACSSTTEA    PICTURE X.
      public NChar acssttea = new NChar(1);
    }

    public Filler231 filler231 = (Filler231) new Filler231().redefines(acssttef);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler233 = new NChar(4);
    // COB:            02  ACSSTTEI  PIC X(2).
    public NChar acssttei = new NChar(2);
    // COB:            02  ACSADL2L    COMP  PIC  S9(4).
    public NInt16 acsadl2l = new NInt16();
    // COB:            02  ACSADL2F    PICTURE X.
    public NChar acsadl2f = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSADL2F.
    public static class Filler237 extends NGroup {
      // COB:              03 ACSADL2A    PICTURE X.
      public NChar acsadl2a = new NChar(1);
    }

    public Filler237 filler237 = (Filler237) new Filler237().redefines(acsadl2f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler239 = new NChar(4);
    // COB:            02  ACSADL2I  PIC X(50).
    public NChar acsadl2i = new NChar(50);
    // COB:            02  ACSZIPCL    COMP  PIC  S9(4).
    public NInt16 acszipcl = new NInt16();
    // COB:            02  ACSZIPCF    PICTURE X.
    public NChar acszipcf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSZIPCF.
    public static class Filler243 extends NGroup {
      // COB:              03 ACSZIPCA    PICTURE X.
      public NChar acszipca = new NChar(1);
    }

    public Filler243 filler243 = (Filler243) new Filler243().redefines(acszipcf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler245 = new NChar(4);
    // COB:            02  ACSZIPCI  PIC X(5).
    public NChar acszipci = new NChar(5);
    // COB:            02  ACSCITYL    COMP  PIC  S9(4).
    public NInt16 acscityl = new NInt16();
    // COB:            02  ACSCITYF    PICTURE X.
    public NChar acscityf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSCITYF.
    public static class Filler249 extends NGroup {
      // COB:              03 ACSCITYA    PICTURE X.
      public NChar acscitya = new NChar(1);
    }

    public Filler249 filler249 = (Filler249) new Filler249().redefines(acscityf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler251 = new NChar(4);
    // COB:            02  ACSCITYI  PIC X(50).
    public NChar acscityi = new NChar(50);
    // COB:            02  ACSCTRYL    COMP  PIC  S9(4).
    public NInt16 acsctryl = new NInt16();
    // COB:            02  ACSCTRYF    PICTURE X.
    public NChar acsctryf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSCTRYF.
    public static class Filler255 extends NGroup {
      // COB:              03 ACSCTRYA    PICTURE X.
      public NChar acsctrya = new NChar(1);
    }

    public Filler255 filler255 = (Filler255) new Filler255().redefines(acsctryf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler257 = new NChar(4);
    // COB:            02  ACSCTRYI  PIC X(3).
    public NChar acsctryi = new NChar(3);
    // COB:            02  ACSPH1AL    COMP  PIC  S9(4).
    public NInt16 acsph1al = new NInt16();
    // COB:            02  ACSPH1AF    PICTURE X.
    public NChar acsph1af = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSPH1AF.
    public static class Filler261 extends NGroup {
      // COB:              03 ACSPH1AA    PICTURE X.
      public NChar acsph1aa = new NChar(1);
    }

    public Filler261 filler261 = (Filler261) new Filler261().redefines(acsph1af);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler263 = new NChar(4);
    // COB:            02  ACSPH1AI  PIC X(3).
    public NChar acsph1ai = new NChar(3);
    // COB:            02  ACSPH1BL    COMP  PIC  S9(4).
    public NInt16 acsph1bl = new NInt16();
    // COB:            02  ACSPH1BF    PICTURE X.
    public NChar acsph1bf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSPH1BF.
    public static class Filler267 extends NGroup {
      // COB:              03 ACSPH1BA    PICTURE X.
      public NChar acsph1ba = new NChar(1);
    }

    public Filler267 filler267 = (Filler267) new Filler267().redefines(acsph1bf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler269 = new NChar(4);
    // COB:            02  ACSPH1BI  PIC X(3).
    public NChar acsph1bi = new NChar(3);
    // COB:            02  ACSPH1CL    COMP  PIC  S9(4).
    public NInt16 acsph1cl = new NInt16();
    // COB:            02  ACSPH1CF    PICTURE X.
    public NChar acsph1cf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSPH1CF.
    public static class Filler273 extends NGroup {
      // COB:              03 ACSPH1CA    PICTURE X.
      public NChar acsph1ca = new NChar(1);
    }

    public Filler273 filler273 = (Filler273) new Filler273().redefines(acsph1cf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler275 = new NChar(4);
    // COB:            02  ACSPH1CI  PIC X(4).
    public NChar acsph1ci = new NChar(4);
    // COB:            02  ACSGOVTL    COMP  PIC  S9(4).
    public NInt16 acsgovtl = new NInt16();
    // COB:            02  ACSGOVTF    PICTURE X.
    public NChar acsgovtf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSGOVTF.
    public static class Filler279 extends NGroup {
      // COB:              03 ACSGOVTA    PICTURE X.
      public NChar acsgovta = new NChar(1);
    }

    public Filler279 filler279 = (Filler279) new Filler279().redefines(acsgovtf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler281 = new NChar(4);
    // COB:            02  ACSGOVTI  PIC X(20).
    public NChar acsgovti = new NChar(20);
    // COB:            02  ACSPH2AL    COMP  PIC  S9(4).
    public NInt16 acsph2al = new NInt16();
    // COB:            02  ACSPH2AF    PICTURE X.
    public NChar acsph2af = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSPH2AF.
    public static class Filler285 extends NGroup {
      // COB:              03 ACSPH2AA    PICTURE X.
      public NChar acsph2aa = new NChar(1);
    }

    public Filler285 filler285 = (Filler285) new Filler285().redefines(acsph2af);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler287 = new NChar(4);
    // COB:            02  ACSPH2AI  PIC X(3).
    public NChar acsph2ai = new NChar(3);
    // COB:            02  ACSPH2BL    COMP  PIC  S9(4).
    public NInt16 acsph2bl = new NInt16();
    // COB:            02  ACSPH2BF    PICTURE X.
    public NChar acsph2bf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSPH2BF.
    public static class Filler291 extends NGroup {
      // COB:              03 ACSPH2BA    PICTURE X.
      public NChar acsph2ba = new NChar(1);
    }

    public Filler291 filler291 = (Filler291) new Filler291().redefines(acsph2bf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler293 = new NChar(4);
    // COB:            02  ACSPH2BI  PIC X(3).
    public NChar acsph2bi = new NChar(3);
    // COB:            02  ACSPH2CL    COMP  PIC  S9(4).
    public NInt16 acsph2cl = new NInt16();
    // COB:            02  ACSPH2CF    PICTURE X.
    public NChar acsph2cf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSPH2CF.
    public static class Filler297 extends NGroup {
      // COB:              03 ACSPH2CA    PICTURE X.
      public NChar acsph2ca = new NChar(1);
    }

    public Filler297 filler297 = (Filler297) new Filler297().redefines(acsph2cf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler299 = new NChar(4);
    // COB:            02  ACSPH2CI  PIC X(4).
    public NChar acsph2ci = new NChar(4);
    // COB:            02  ACSEFTCL    COMP  PIC  S9(4).
    public NInt16 acseftcl = new NInt16();
    // COB:            02  ACSEFTCF    PICTURE X.
    public NChar acseftcf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSEFTCF.
    public static class Filler303 extends NGroup {
      // COB:              03 ACSEFTCA    PICTURE X.
      public NChar acseftca = new NChar(1);
    }

    public Filler303 filler303 = (Filler303) new Filler303().redefines(acseftcf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler305 = new NChar(4);
    // COB:            02  ACSEFTCI  PIC X(10).
    public NChar acseftci = new NChar(10);
    // COB:            02  ACSPFLGL    COMP  PIC  S9(4).
    public NInt16 acspflgl = new NInt16();
    // COB:            02  ACSPFLGF    PICTURE X.
    public NChar acspflgf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACSPFLGF.
    public static class Filler309 extends NGroup {
      // COB:              03 ACSPFLGA    PICTURE X.
      public NChar acspflga = new NChar(1);
    }

    public Filler309 filler309 = (Filler309) new Filler309().redefines(acspflgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler311 = new NChar(4);
    // COB:            02  ACSPFLGI  PIC X(1).
    public NChar acspflgi = new NChar(1);
    // COB:            02  INFOMSGL    COMP  PIC  S9(4).
    public NInt16 infomsgl = new NInt16();
    // COB:            02  INFOMSGF    PICTURE X.
    public NChar infomsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES INFOMSGF.
    public static class Filler315 extends NGroup {
      // COB:              03 INFOMSGA    PICTURE X.
      public NChar infomsga = new NChar(1);
    }

    public Filler315 filler315 = (Filler315) new Filler315().redefines(infomsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler317 = new NChar(4);
    // COB:            02  INFOMSGI  PIC X(45).
    public NChar infomsgi = new NChar(45);
    // COB:            02  ERRMSGL    COMP  PIC  S9(4).
    public NInt16 errmsgl = new NInt16();
    // COB:            02  ERRMSGF    PICTURE X.
    public NChar errmsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES ERRMSGF.
    public static class Filler321 extends NGroup {
      // COB:              03 ERRMSGA    PICTURE X.
      public NChar errmsga = new NChar(1);
    }

    public Filler321 filler321 = (Filler321) new Filler321().redefines(errmsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler323 = new NChar(4);
    // COB:            02  ERRMSGI  PIC X(78).
    public NChar errmsgi = new NChar(78);
    // COB:            02  FKEYSL    COMP  PIC  S9(4).
    public NInt16 fkeysl = new NInt16();
    // COB:            02  FKEYSF    PICTURE X.
    public NChar fkeysf = new NChar(1);

    // COB:            02  FILLER REDEFINES FKEYSF.
    public static class Filler327 extends NGroup {
      // COB:              03 FKEYSA    PICTURE X.
      public NChar fkeysa = new NChar(1);
    }

    public Filler327 filler327 = (Filler327) new Filler327().redefines(fkeysf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler329 = new NChar(4);
    // COB:            02  FKEYSI  PIC X(21).
    public NChar fkeysi = new NChar(21);
    // COB:            02  FKEY05L    COMP  PIC  S9(4).
    public NInt16 fkey05l = new NInt16();
    // COB:            02  FKEY05F    PICTURE X.
    public NChar fkey05f = new NChar(1);

    // COB:            02  FILLER REDEFINES FKEY05F.
    public static class Filler333 extends NGroup {
      // COB:              03 FKEY05A    PICTURE X.
      public NChar fkey05a = new NChar(1);
    }

    public Filler333 filler333 = (Filler333) new Filler333().redefines(fkey05f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler335 = new NChar(4);
    // COB:            02  FKEY05I  PIC X(7).
    public NChar fkey05i = new NChar(7);
    // COB:            02  FKEY12L    COMP  PIC  S9(4).
    public NInt16 fkey12l = new NInt16();
    // COB:            02  FKEY12F    PICTURE X.
    public NChar fkey12f = new NChar(1);

    // COB:            02  FILLER REDEFINES FKEY12F.
    public static class Filler339 extends NGroup {
      // COB:              03 FKEY12A    PICTURE X.
      public NChar fkey12a = new NChar(1);
    }

    public Filler339 filler339 = (Filler339) new Filler339().redefines(fkey12f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler341 = new NChar(4);
    // COB:            02  FKEY12I  PIC X(10).
    public NChar fkey12i = new NChar(10);
  }

  public Cactupai cactupai = new Cactupai();

  // COB:        01  CACTUPAO REDEFINES CACTUPAI.
  public static class Cactupao extends NGroup {
    // COB:            02  FILLER PIC X(12).
    public NChar filler344 = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler345 = new NChar(3);
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
    public NChar filler351 = new NChar(3);
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
    public NChar filler357 = new NChar(3);
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
    public NChar filler363 = new NChar(3);
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
    public NChar filler369 = new NChar(3);
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
    public NChar filler375 = new NChar(3);
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
    public NChar filler381 = new NChar(3);
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
    public NChar filler387 = new NChar(3);
    // COB:            02  ACSTTUSC    PICTURE X.
    public NChar acsttusc = new NChar(1);
    // COB:            02  ACSTTUSP    PICTURE X.
    public NChar acsttusp = new NChar(1);
    // COB:            02  ACSTTUSH    PICTURE X.
    public NChar acsttush = new NChar(1);
    // COB:            02  ACSTTUSV    PICTURE X.
    public NChar acsttusv = new NChar(1);
    // COB:            02  ACSTTUSO  PIC X(1).
    public NChar acsttuso = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler393 = new NChar(3);
    // COB:            02  OPNYEARC    PICTURE X.
    public NChar opnyearc = new NChar(1);
    // COB:            02  OPNYEARP    PICTURE X.
    public NChar opnyearp = new NChar(1);
    // COB:            02  OPNYEARH    PICTURE X.
    public NChar opnyearh = new NChar(1);
    // COB:            02  OPNYEARV    PICTURE X.
    public NChar opnyearv = new NChar(1);
    // COB:            02  OPNYEARO  PIC X(4).
    public NChar opnyearo = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler399 = new NChar(3);
    // COB:            02  OPNMONC    PICTURE X.
    public NChar opnmonc = new NChar(1);
    // COB:            02  OPNMONP    PICTURE X.
    public NChar opnmonp = new NChar(1);
    // COB:            02  OPNMONH    PICTURE X.
    public NChar opnmonh = new NChar(1);
    // COB:            02  OPNMONV    PICTURE X.
    public NChar opnmonv = new NChar(1);
    // COB:            02  OPNMONO  PIC X(2).
    public NChar opnmono = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler405 = new NChar(3);
    // COB:            02  OPNDAYC    PICTURE X.
    public NChar opndayc = new NChar(1);
    // COB:            02  OPNDAYP    PICTURE X.
    public NChar opndayp = new NChar(1);
    // COB:            02  OPNDAYH    PICTURE X.
    public NChar opndayh = new NChar(1);
    // COB:            02  OPNDAYV    PICTURE X.
    public NChar opndayv = new NChar(1);
    // COB:            02  OPNDAYO  PIC X(2).
    public NChar opndayo = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler411 = new NChar(3);
    // COB:            02  ACRDLIMC    PICTURE X.
    public NChar acrdlimc = new NChar(1);
    // COB:            02  ACRDLIMP    PICTURE X.
    public NChar acrdlimp = new NChar(1);
    // COB:            02  ACRDLIMH    PICTURE X.
    public NChar acrdlimh = new NChar(1);
    // COB:            02  ACRDLIMV    PICTURE X.
    public NChar acrdlimv = new NChar(1);
    // COB:            02  ACRDLIMO  PIC X(15).
    public NChar acrdlimo = new NChar(15);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler417 = new NChar(3);
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
    public NChar filler423 = new NChar(3);
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
    public NChar filler429 = new NChar(3);
    // COB:            02  EXPDAYC    PICTURE X.
    public NChar expdayc = new NChar(1);
    // COB:            02  EXPDAYP    PICTURE X.
    public NChar expdayp = new NChar(1);
    // COB:            02  EXPDAYH    PICTURE X.
    public NChar expdayh = new NChar(1);
    // COB:            02  EXPDAYV    PICTURE X.
    public NChar expdayv = new NChar(1);
    // COB:            02  EXPDAYO  PIC X(2).
    public NChar expdayo = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler435 = new NChar(3);
    // COB:            02  ACSHLIMC    PICTURE X.
    public NChar acshlimc = new NChar(1);
    // COB:            02  ACSHLIMP    PICTURE X.
    public NChar acshlimp = new NChar(1);
    // COB:            02  ACSHLIMH    PICTURE X.
    public NChar acshlimh = new NChar(1);
    // COB:            02  ACSHLIMV    PICTURE X.
    public NChar acshlimv = new NChar(1);
    // COB:            02  ACSHLIMO  PIC X(15).
    public NChar acshlimo = new NChar(15);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler441 = new NChar(3);
    // COB:            02  RISYEARC    PICTURE X.
    public NChar risyearc = new NChar(1);
    // COB:            02  RISYEARP    PICTURE X.
    public NChar risyearp = new NChar(1);
    // COB:            02  RISYEARH    PICTURE X.
    public NChar risyearh = new NChar(1);
    // COB:            02  RISYEARV    PICTURE X.
    public NChar risyearv = new NChar(1);
    // COB:            02  RISYEARO  PIC X(4).
    public NChar risyearo = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler447 = new NChar(3);
    // COB:            02  RISMONC    PICTURE X.
    public NChar rismonc = new NChar(1);
    // COB:            02  RISMONP    PICTURE X.
    public NChar rismonp = new NChar(1);
    // COB:            02  RISMONH    PICTURE X.
    public NChar rismonh = new NChar(1);
    // COB:            02  RISMONV    PICTURE X.
    public NChar rismonv = new NChar(1);
    // COB:            02  RISMONO  PIC X(2).
    public NChar rismono = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler453 = new NChar(3);
    // COB:            02  RISDAYC    PICTURE X.
    public NChar risdayc = new NChar(1);
    // COB:            02  RISDAYP    PICTURE X.
    public NChar risdayp = new NChar(1);
    // COB:            02  RISDAYH    PICTURE X.
    public NChar risdayh = new NChar(1);
    // COB:            02  RISDAYV    PICTURE X.
    public NChar risdayv = new NChar(1);
    // COB:            02  RISDAYO  PIC X(2).
    public NChar risdayo = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler459 = new NChar(3);
    // COB:            02  ACURBALC    PICTURE X.
    public NChar acurbalc = new NChar(1);
    // COB:            02  ACURBALP    PICTURE X.
    public NChar acurbalp = new NChar(1);
    // COB:            02  ACURBALH    PICTURE X.
    public NChar acurbalh = new NChar(1);
    // COB:            02  ACURBALV    PICTURE X.
    public NChar acurbalv = new NChar(1);
    // COB:            02  ACURBALO  PIC X(15).
    public NChar acurbalo = new NChar(15);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler465 = new NChar(3);
    // COB:            02  ACRCYCRC    PICTURE X.
    public NChar acrcycrc = new NChar(1);
    // COB:            02  ACRCYCRP    PICTURE X.
    public NChar acrcycrp = new NChar(1);
    // COB:            02  ACRCYCRH    PICTURE X.
    public NChar acrcycrh = new NChar(1);
    // COB:            02  ACRCYCRV    PICTURE X.
    public NChar acrcycrv = new NChar(1);
    // COB:            02  ACRCYCRO  PIC X(15).
    public NChar acrcycro = new NChar(15);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler471 = new NChar(3);
    // COB:            02  AADDGRPC    PICTURE X.
    public NChar aaddgrpc = new NChar(1);
    // COB:            02  AADDGRPP    PICTURE X.
    public NChar aaddgrpp = new NChar(1);
    // COB:            02  AADDGRPH    PICTURE X.
    public NChar aaddgrph = new NChar(1);
    // COB:            02  AADDGRPV    PICTURE X.
    public NChar aaddgrpv = new NChar(1);
    // COB:            02  AADDGRPO  PIC X(10).
    public NChar aaddgrpo = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler477 = new NChar(3);
    // COB:            02  ACRCYDBC    PICTURE X.
    public NChar acrcydbc = new NChar(1);
    // COB:            02  ACRCYDBP    PICTURE X.
    public NChar acrcydbp = new NChar(1);
    // COB:            02  ACRCYDBH    PICTURE X.
    public NChar acrcydbh = new NChar(1);
    // COB:            02  ACRCYDBV    PICTURE X.
    public NChar acrcydbv = new NChar(1);
    // COB:            02  ACRCYDBO  PIC X(15).
    public NChar acrcydbo = new NChar(15);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler483 = new NChar(3);
    // COB:            02  ACSTNUMC    PICTURE X.
    public NChar acstnumc = new NChar(1);
    // COB:            02  ACSTNUMP    PICTURE X.
    public NChar acstnump = new NChar(1);
    // COB:            02  ACSTNUMH    PICTURE X.
    public NChar acstnumh = new NChar(1);
    // COB:            02  ACSTNUMV    PICTURE X.
    public NChar acstnumv = new NChar(1);
    // COB:            02  ACSTNUMO  PIC X(9).
    public NChar acstnumo = new NChar(9);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler489 = new NChar(3);
    // COB:            02  ACTSSN1C    PICTURE X.
    public NChar actssn1c = new NChar(1);
    // COB:            02  ACTSSN1P    PICTURE X.
    public NChar actssn1p = new NChar(1);
    // COB:            02  ACTSSN1H    PICTURE X.
    public NChar actssn1h = new NChar(1);
    // COB:            02  ACTSSN1V    PICTURE X.
    public NChar actssn1v = new NChar(1);
    // COB:            02  ACTSSN1O  PIC X(3).
    public NChar actssn1o = new NChar(3);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler495 = new NChar(3);
    // COB:            02  ACTSSN2C    PICTURE X.
    public NChar actssn2c = new NChar(1);
    // COB:            02  ACTSSN2P    PICTURE X.
    public NChar actssn2p = new NChar(1);
    // COB:            02  ACTSSN2H    PICTURE X.
    public NChar actssn2h = new NChar(1);
    // COB:            02  ACTSSN2V    PICTURE X.
    public NChar actssn2v = new NChar(1);
    // COB:            02  ACTSSN2O  PIC X(2).
    public NChar actssn2o = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler501 = new NChar(3);
    // COB:            02  ACTSSN3C    PICTURE X.
    public NChar actssn3c = new NChar(1);
    // COB:            02  ACTSSN3P    PICTURE X.
    public NChar actssn3p = new NChar(1);
    // COB:            02  ACTSSN3H    PICTURE X.
    public NChar actssn3h = new NChar(1);
    // COB:            02  ACTSSN3V    PICTURE X.
    public NChar actssn3v = new NChar(1);
    // COB:            02  ACTSSN3O  PIC X(4).
    public NChar actssn3o = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler507 = new NChar(3);
    // COB:            02  DOBYEARC    PICTURE X.
    public NChar dobyearc = new NChar(1);
    // COB:            02  DOBYEARP    PICTURE X.
    public NChar dobyearp = new NChar(1);
    // COB:            02  DOBYEARH    PICTURE X.
    public NChar dobyearh = new NChar(1);
    // COB:            02  DOBYEARV    PICTURE X.
    public NChar dobyearv = new NChar(1);
    // COB:            02  DOBYEARO  PIC X(4).
    public NChar dobyearo = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler513 = new NChar(3);
    // COB:            02  DOBMONC    PICTURE X.
    public NChar dobmonc = new NChar(1);
    // COB:            02  DOBMONP    PICTURE X.
    public NChar dobmonp = new NChar(1);
    // COB:            02  DOBMONH    PICTURE X.
    public NChar dobmonh = new NChar(1);
    // COB:            02  DOBMONV    PICTURE X.
    public NChar dobmonv = new NChar(1);
    // COB:            02  DOBMONO  PIC X(2).
    public NChar dobmono = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler519 = new NChar(3);
    // COB:            02  DOBDAYC    PICTURE X.
    public NChar dobdayc = new NChar(1);
    // COB:            02  DOBDAYP    PICTURE X.
    public NChar dobdayp = new NChar(1);
    // COB:            02  DOBDAYH    PICTURE X.
    public NChar dobdayh = new NChar(1);
    // COB:            02  DOBDAYV    PICTURE X.
    public NChar dobdayv = new NChar(1);
    // COB:            02  DOBDAYO  PIC X(2).
    public NChar dobdayo = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler525 = new NChar(3);
    // COB:            02  ACSTFCOC    PICTURE X.
    public NChar acstfcoc = new NChar(1);
    // COB:            02  ACSTFCOP    PICTURE X.
    public NChar acstfcop = new NChar(1);
    // COB:            02  ACSTFCOH    PICTURE X.
    public NChar acstfcoh = new NChar(1);
    // COB:            02  ACSTFCOV    PICTURE X.
    public NChar acstfcov = new NChar(1);
    // COB:            02  ACSTFCOO  PIC X(3).
    public NChar acstfcoo = new NChar(3);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler531 = new NChar(3);
    // COB:            02  ACSFNAMC    PICTURE X.
    public NChar acsfnamc = new NChar(1);
    // COB:            02  ACSFNAMP    PICTURE X.
    public NChar acsfnamp = new NChar(1);
    // COB:            02  ACSFNAMH    PICTURE X.
    public NChar acsfnamh = new NChar(1);
    // COB:            02  ACSFNAMV    PICTURE X.
    public NChar acsfnamv = new NChar(1);
    // COB:            02  ACSFNAMO  PIC X(25).
    public NChar acsfnamo = new NChar(25);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler537 = new NChar(3);
    // COB:            02  ACSMNAMC    PICTURE X.
    public NChar acsmnamc = new NChar(1);
    // COB:            02  ACSMNAMP    PICTURE X.
    public NChar acsmnamp = new NChar(1);
    // COB:            02  ACSMNAMH    PICTURE X.
    public NChar acsmnamh = new NChar(1);
    // COB:            02  ACSMNAMV    PICTURE X.
    public NChar acsmnamv = new NChar(1);
    // COB:            02  ACSMNAMO  PIC X(25).
    public NChar acsmnamo = new NChar(25);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler543 = new NChar(3);
    // COB:            02  ACSLNAMC    PICTURE X.
    public NChar acslnamc = new NChar(1);
    // COB:            02  ACSLNAMP    PICTURE X.
    public NChar acslnamp = new NChar(1);
    // COB:            02  ACSLNAMH    PICTURE X.
    public NChar acslnamh = new NChar(1);
    // COB:            02  ACSLNAMV    PICTURE X.
    public NChar acslnamv = new NChar(1);
    // COB:            02  ACSLNAMO  PIC X(25).
    public NChar acslnamo = new NChar(25);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler549 = new NChar(3);
    // COB:            02  ACSADL1C    PICTURE X.
    public NChar acsadl1c = new NChar(1);
    // COB:            02  ACSADL1P    PICTURE X.
    public NChar acsadl1p = new NChar(1);
    // COB:            02  ACSADL1H    PICTURE X.
    public NChar acsadl1h = new NChar(1);
    // COB:            02  ACSADL1V    PICTURE X.
    public NChar acsadl1v = new NChar(1);
    // COB:            02  ACSADL1O  PIC X(50).
    public NChar acsadl1o = new NChar(50);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler555 = new NChar(3);
    // COB:            02  ACSSTTEC    PICTURE X.
    public NChar acssttec = new NChar(1);
    // COB:            02  ACSSTTEP    PICTURE X.
    public NChar acssttep = new NChar(1);
    // COB:            02  ACSSTTEH    PICTURE X.
    public NChar acsstteh = new NChar(1);
    // COB:            02  ACSSTTEV    PICTURE X.
    public NChar acssttev = new NChar(1);
    // COB:            02  ACSSTTEO  PIC X(2).
    public NChar acsstteo = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler561 = new NChar(3);
    // COB:            02  ACSADL2C    PICTURE X.
    public NChar acsadl2c = new NChar(1);
    // COB:            02  ACSADL2P    PICTURE X.
    public NChar acsadl2p = new NChar(1);
    // COB:            02  ACSADL2H    PICTURE X.
    public NChar acsadl2h = new NChar(1);
    // COB:            02  ACSADL2V    PICTURE X.
    public NChar acsadl2v = new NChar(1);
    // COB:            02  ACSADL2O  PIC X(50).
    public NChar acsadl2o = new NChar(50);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler567 = new NChar(3);
    // COB:            02  ACSZIPCC    PICTURE X.
    public NChar acszipcc = new NChar(1);
    // COB:            02  ACSZIPCP    PICTURE X.
    public NChar acszipcp = new NChar(1);
    // COB:            02  ACSZIPCH    PICTURE X.
    public NChar acszipch = new NChar(1);
    // COB:            02  ACSZIPCV    PICTURE X.
    public NChar acszipcv = new NChar(1);
    // COB:            02  ACSZIPCO  PIC X(5).
    public NChar acszipco = new NChar(5);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler573 = new NChar(3);
    // COB:            02  ACSCITYC    PICTURE X.
    public NChar acscityc = new NChar(1);
    // COB:            02  ACSCITYP    PICTURE X.
    public NChar acscityp = new NChar(1);
    // COB:            02  ACSCITYH    PICTURE X.
    public NChar acscityh = new NChar(1);
    // COB:            02  ACSCITYV    PICTURE X.
    public NChar acscityv = new NChar(1);
    // COB:            02  ACSCITYO  PIC X(50).
    public NChar acscityo = new NChar(50);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler579 = new NChar(3);
    // COB:            02  ACSCTRYC    PICTURE X.
    public NChar acsctryc = new NChar(1);
    // COB:            02  ACSCTRYP    PICTURE X.
    public NChar acsctryp = new NChar(1);
    // COB:            02  ACSCTRYH    PICTURE X.
    public NChar acsctryh = new NChar(1);
    // COB:            02  ACSCTRYV    PICTURE X.
    public NChar acsctryv = new NChar(1);
    // COB:            02  ACSCTRYO  PIC X(3).
    public NChar acsctryo = new NChar(3);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler585 = new NChar(3);
    // COB:            02  ACSPH1AC    PICTURE X.
    public NChar acsph1ac = new NChar(1);
    // COB:            02  ACSPH1AP    PICTURE X.
    public NChar acsph1ap = new NChar(1);
    // COB:            02  ACSPH1AH    PICTURE X.
    public NChar acsph1ah = new NChar(1);
    // COB:            02  ACSPH1AV    PICTURE X.
    public NChar acsph1av = new NChar(1);
    // COB:            02  ACSPH1AO  PIC X(3).
    public NChar acsph1ao = new NChar(3);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler591 = new NChar(3);
    // COB:            02  ACSPH1BC    PICTURE X.
    public NChar acsph1bc = new NChar(1);
    // COB:            02  ACSPH1BP    PICTURE X.
    public NChar acsph1bp = new NChar(1);
    // COB:            02  ACSPH1BH    PICTURE X.
    public NChar acsph1bh = new NChar(1);
    // COB:            02  ACSPH1BV    PICTURE X.
    public NChar acsph1bv = new NChar(1);
    // COB:            02  ACSPH1BO  PIC X(3).
    public NChar acsph1bo = new NChar(3);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler597 = new NChar(3);
    // COB:            02  ACSPH1CC    PICTURE X.
    public NChar acsph1cc = new NChar(1);
    // COB:            02  ACSPH1CP    PICTURE X.
    public NChar acsph1cp = new NChar(1);
    // COB:            02  ACSPH1CH    PICTURE X.
    public NChar acsph1ch = new NChar(1);
    // COB:            02  ACSPH1CV    PICTURE X.
    public NChar acsph1cv = new NChar(1);
    // COB:            02  ACSPH1CO  PIC X(4).
    public NChar acsph1co = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler603 = new NChar(3);
    // COB:            02  ACSGOVTC    PICTURE X.
    public NChar acsgovtc = new NChar(1);
    // COB:            02  ACSGOVTP    PICTURE X.
    public NChar acsgovtp = new NChar(1);
    // COB:            02  ACSGOVTH    PICTURE X.
    public NChar acsgovth = new NChar(1);
    // COB:            02  ACSGOVTV    PICTURE X.
    public NChar acsgovtv = new NChar(1);
    // COB:            02  ACSGOVTO  PIC X(20).
    public NChar acsgovto = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler609 = new NChar(3);
    // COB:            02  ACSPH2AC    PICTURE X.
    public NChar acsph2ac = new NChar(1);
    // COB:            02  ACSPH2AP    PICTURE X.
    public NChar acsph2ap = new NChar(1);
    // COB:            02  ACSPH2AH    PICTURE X.
    public NChar acsph2ah = new NChar(1);
    // COB:            02  ACSPH2AV    PICTURE X.
    public NChar acsph2av = new NChar(1);
    // COB:            02  ACSPH2AO  PIC X(3).
    public NChar acsph2ao = new NChar(3);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler615 = new NChar(3);
    // COB:            02  ACSPH2BC    PICTURE X.
    public NChar acsph2bc = new NChar(1);
    // COB:            02  ACSPH2BP    PICTURE X.
    public NChar acsph2bp = new NChar(1);
    // COB:            02  ACSPH2BH    PICTURE X.
    public NChar acsph2bh = new NChar(1);
    // COB:            02  ACSPH2BV    PICTURE X.
    public NChar acsph2bv = new NChar(1);
    // COB:            02  ACSPH2BO  PIC X(3).
    public NChar acsph2bo = new NChar(3);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler621 = new NChar(3);
    // COB:            02  ACSPH2CC    PICTURE X.
    public NChar acsph2cc = new NChar(1);
    // COB:            02  ACSPH2CP    PICTURE X.
    public NChar acsph2cp = new NChar(1);
    // COB:            02  ACSPH2CH    PICTURE X.
    public NChar acsph2ch = new NChar(1);
    // COB:            02  ACSPH2CV    PICTURE X.
    public NChar acsph2cv = new NChar(1);
    // COB:            02  ACSPH2CO  PIC X(4).
    public NChar acsph2co = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler627 = new NChar(3);
    // COB:            02  ACSEFTCC    PICTURE X.
    public NChar acseftcc = new NChar(1);
    // COB:            02  ACSEFTCP    PICTURE X.
    public NChar acseftcp = new NChar(1);
    // COB:            02  ACSEFTCH    PICTURE X.
    public NChar acseftch = new NChar(1);
    // COB:            02  ACSEFTCV    PICTURE X.
    public NChar acseftcv = new NChar(1);
    // COB:            02  ACSEFTCO  PIC X(10).
    public NChar acseftco = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler633 = new NChar(3);
    // COB:            02  ACSPFLGC    PICTURE X.
    public NChar acspflgc = new NChar(1);
    // COB:            02  ACSPFLGP    PICTURE X.
    public NChar acspflgp = new NChar(1);
    // COB:            02  ACSPFLGH    PICTURE X.
    public NChar acspflgh = new NChar(1);
    // COB:            02  ACSPFLGV    PICTURE X.
    public NChar acspflgv = new NChar(1);
    // COB:            02  ACSPFLGO  PIC X(1).
    public NChar acspflgo = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler639 = new NChar(3);
    // COB:            02  INFOMSGC    PICTURE X.
    public NChar infomsgc = new NChar(1);
    // COB:            02  INFOMSGP    PICTURE X.
    public NChar infomsgp = new NChar(1);
    // COB:            02  INFOMSGH    PICTURE X.
    public NChar infomsgh = new NChar(1);
    // COB:            02  INFOMSGV    PICTURE X.
    public NChar infomsgv = new NChar(1);
    // COB:            02  INFOMSGO  PIC X(45).
    public NChar infomsgo = new NChar(45);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler645 = new NChar(3);
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
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler651 = new NChar(3);
    // COB:            02  FKEYSC    PICTURE X.
    public NChar fkeysc = new NChar(1);
    // COB:            02  FKEYSP    PICTURE X.
    public NChar fkeysp = new NChar(1);
    // COB:            02  FKEYSH    PICTURE X.
    public NChar fkeysh = new NChar(1);
    // COB:            02  FKEYSV    PICTURE X.
    public NChar fkeysv = new NChar(1);
    // COB:            02  FKEYSO  PIC X(21).
    public NChar fkeyso = new NChar(21);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler657 = new NChar(3);
    // COB:            02  FKEY05C    PICTURE X.
    public NChar fkey05c = new NChar(1);
    // COB:            02  FKEY05P    PICTURE X.
    public NChar fkey05p = new NChar(1);
    // COB:            02  FKEY05H    PICTURE X.
    public NChar fkey05h = new NChar(1);
    // COB:            02  FKEY05V    PICTURE X.
    public NChar fkey05v = new NChar(1);
    // COB:            02  FKEY05O  PIC X(7).
    public NChar fkey05o = new NChar(7);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler663 = new NChar(3);
    // COB:            02  FKEY12C    PICTURE X.
    public NChar fkey12c = new NChar(1);
    // COB:            02  FKEY12P    PICTURE X.
    public NChar fkey12p = new NChar(1);
    // COB:            02  FKEY12H    PICTURE X.
    public NChar fkey12h = new NChar(1);
    // COB:            02  FKEY12V    PICTURE X.
    public NChar fkey12v = new NChar(1);
    // COB:            02  FKEY12O  PIC X(10).
    public NChar fkey12o = new NChar(10);
  }

  public Cactupao cactupao = (Cactupao) new Cactupao().redefines(cactupai);
}
