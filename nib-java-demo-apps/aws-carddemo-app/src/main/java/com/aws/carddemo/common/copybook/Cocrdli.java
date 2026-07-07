package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cocrdli extends NGroup {

  // COB:        01  CCRDLIAI.
  public static class Ccrdliai extends NGroup {
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
    // COB:            02  PAGENOL    COMP  PIC  S9(4).
    public NInt16 pagenol = new NInt16();
    // COB:            02  PAGENOF    PICTURE X.
    public NChar pagenof = new NChar(1);

    // COB:            02  FILLER REDEFINES PAGENOF.
    public static class Filler57 extends NGroup {
      // COB:              03 PAGENOA    PICTURE X.
      public NChar pagenoa = new NChar(1);
    }

    public Filler57 filler57 = (Filler57) new Filler57().redefines(pagenof);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler59 = new NChar(4);
    // COB:            02  PAGENOI  PIC X(3).
    public NChar pagenoi = new NChar(3);
    // COB:            02  ACCTSIDL    COMP  PIC  S9(4).
    public NInt16 acctsidl = new NInt16();
    // COB:            02  ACCTSIDF    PICTURE X.
    public NChar acctsidf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACCTSIDF.
    public static class Filler63 extends NGroup {
      // COB:              03 ACCTSIDA    PICTURE X.
      public NChar acctsida = new NChar(1);
    }

    public Filler63 filler63 = (Filler63) new Filler63().redefines(acctsidf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler65 = new NChar(4);
    // COB:            02  ACCTSIDI  PIC X(11).
    public NChar acctsidi = new NChar(11);
    // COB:            02  CARDSIDL    COMP  PIC  S9(4).
    public NInt16 cardsidl = new NInt16();
    // COB:            02  CARDSIDF    PICTURE X.
    public NChar cardsidf = new NChar(1);

    // COB:            02  FILLER REDEFINES CARDSIDF.
    public static class Filler69 extends NGroup {
      // COB:              03 CARDSIDA    PICTURE X.
      public NChar cardsida = new NChar(1);
    }

    public Filler69 filler69 = (Filler69) new Filler69().redefines(cardsidf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler71 = new NChar(4);
    // COB:            02  CARDSIDI  PIC X(16).
    public NChar cardsidi = new NChar(16);
    // COB:            02  CRDSEL1L    COMP  PIC  S9(4).
    public NInt16 crdsel1l = new NInt16();
    // COB:            02  CRDSEL1F    PICTURE X.
    public NChar crdsel1f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSEL1F.
    public static class Filler75 extends NGroup {
      // COB:              03 CRDSEL1A    PICTURE X.
      public NChar crdsel1a = new NChar(1);
    }

    public Filler75 filler75 = (Filler75) new Filler75().redefines(crdsel1f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler77 = new NChar(4);
    // COB:            02  CRDSEL1I  PIC X(1).
    public NChar crdsel1i = new NChar(1);
    // COB:            02  ACCTNO1L    COMP  PIC  S9(4).
    public NInt16 acctno1l = new NInt16();
    // COB:            02  ACCTNO1F    PICTURE X.
    public NChar acctno1f = new NChar(1);

    // COB:            02  FILLER REDEFINES ACCTNO1F.
    public static class Filler81 extends NGroup {
      // COB:              03 ACCTNO1A    PICTURE X.
      public NChar acctno1a = new NChar(1);
    }

    public Filler81 filler81 = (Filler81) new Filler81().redefines(acctno1f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler83 = new NChar(4);
    // COB:            02  ACCTNO1I  PIC X(11).
    public NChar acctno1i = new NChar(11);
    // COB:            02  CRDNUM1L    COMP  PIC  S9(4).
    public NInt16 crdnum1l = new NInt16();
    // COB:            02  CRDNUM1F    PICTURE X.
    public NChar crdnum1f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDNUM1F.
    public static class Filler87 extends NGroup {
      // COB:              03 CRDNUM1A    PICTURE X.
      public NChar crdnum1a = new NChar(1);
    }

    public Filler87 filler87 = (Filler87) new Filler87().redefines(crdnum1f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler89 = new NChar(4);
    // COB:            02  CRDNUM1I  PIC X(16).
    public NChar crdnum1i = new NChar(16);
    // COB:            02  CRDSTS1L    COMP  PIC  S9(4).
    public NInt16 crdsts1l = new NInt16();
    // COB:            02  CRDSTS1F    PICTURE X.
    public NChar crdsts1f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSTS1F.
    public static class Filler93 extends NGroup {
      // COB:              03 CRDSTS1A    PICTURE X.
      public NChar crdsts1a = new NChar(1);
    }

    public Filler93 filler93 = (Filler93) new Filler93().redefines(crdsts1f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler95 = new NChar(4);
    // COB:            02  CRDSTS1I  PIC X(1).
    public NChar crdsts1i = new NChar(1);
    // COB:            02  CRDSEL2L    COMP  PIC  S9(4).
    public NInt16 crdsel2l = new NInt16();
    // COB:            02  CRDSEL2F    PICTURE X.
    public NChar crdsel2f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSEL2F.
    public static class Filler99 extends NGroup {
      // COB:              03 CRDSEL2A    PICTURE X.
      public NChar crdsel2a = new NChar(1);
    }

    public Filler99 filler99 = (Filler99) new Filler99().redefines(crdsel2f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler101 = new NChar(4);
    // COB:            02  CRDSEL2I  PIC X(1).
    public NChar crdsel2i = new NChar(1);
    // COB:            02  CRDSTP2L    COMP  PIC  S9(4).
    public NInt16 crdstp2l = new NInt16();
    // COB:            02  CRDSTP2F    PICTURE X.
    public NChar crdstp2f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSTP2F.
    public static class Filler105 extends NGroup {
      // COB:              03 CRDSTP2A    PICTURE X.
      public NChar crdstp2a = new NChar(1);
    }

    public Filler105 filler105 = (Filler105) new Filler105().redefines(crdstp2f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler107 = new NChar(4);
    // COB:            02  CRDSTP2I  PIC X(1).
    public NChar crdstp2i = new NChar(1);
    // COB:            02  ACCTNO2L    COMP  PIC  S9(4).
    public NInt16 acctno2l = new NInt16();
    // COB:            02  ACCTNO2F    PICTURE X.
    public NChar acctno2f = new NChar(1);

    // COB:            02  FILLER REDEFINES ACCTNO2F.
    public static class Filler111 extends NGroup {
      // COB:              03 ACCTNO2A    PICTURE X.
      public NChar acctno2a = new NChar(1);
    }

    public Filler111 filler111 = (Filler111) new Filler111().redefines(acctno2f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler113 = new NChar(4);
    // COB:            02  ACCTNO2I  PIC X(11).
    public NChar acctno2i = new NChar(11);
    // COB:            02  CRDNUM2L    COMP  PIC  S9(4).
    public NInt16 crdnum2l = new NInt16();
    // COB:            02  CRDNUM2F    PICTURE X.
    public NChar crdnum2f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDNUM2F.
    public static class Filler117 extends NGroup {
      // COB:              03 CRDNUM2A    PICTURE X.
      public NChar crdnum2a = new NChar(1);
    }

    public Filler117 filler117 = (Filler117) new Filler117().redefines(crdnum2f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler119 = new NChar(4);
    // COB:            02  CRDNUM2I  PIC X(16).
    public NChar crdnum2i = new NChar(16);
    // COB:            02  CRDSTS2L    COMP  PIC  S9(4).
    public NInt16 crdsts2l = new NInt16();
    // COB:            02  CRDSTS2F    PICTURE X.
    public NChar crdsts2f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSTS2F.
    public static class Filler123 extends NGroup {
      // COB:              03 CRDSTS2A    PICTURE X.
      public NChar crdsts2a = new NChar(1);
    }

    public Filler123 filler123 = (Filler123) new Filler123().redefines(crdsts2f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler125 = new NChar(4);
    // COB:            02  CRDSTS2I  PIC X(1).
    public NChar crdsts2i = new NChar(1);
    // COB:            02  CRDSEL3L    COMP  PIC  S9(4).
    public NInt16 crdsel3l = new NInt16();
    // COB:            02  CRDSEL3F    PICTURE X.
    public NChar crdsel3f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSEL3F.
    public static class Filler129 extends NGroup {
      // COB:              03 CRDSEL3A    PICTURE X.
      public NChar crdsel3a = new NChar(1);
    }

    public Filler129 filler129 = (Filler129) new Filler129().redefines(crdsel3f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler131 = new NChar(4);
    // COB:            02  CRDSEL3I  PIC X(1).
    public NChar crdsel3i = new NChar(1);
    // COB:            02  CRDSTP3L    COMP  PIC  S9(4).
    public NInt16 crdstp3l = new NInt16();
    // COB:            02  CRDSTP3F    PICTURE X.
    public NChar crdstp3f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSTP3F.
    public static class Filler135 extends NGroup {
      // COB:              03 CRDSTP3A    PICTURE X.
      public NChar crdstp3a = new NChar(1);
    }

    public Filler135 filler135 = (Filler135) new Filler135().redefines(crdstp3f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler137 = new NChar(4);
    // COB:            02  CRDSTP3I  PIC X(1).
    public NChar crdstp3i = new NChar(1);
    // COB:            02  ACCTNO3L    COMP  PIC  S9(4).
    public NInt16 acctno3l = new NInt16();
    // COB:            02  ACCTNO3F    PICTURE X.
    public NChar acctno3f = new NChar(1);

    // COB:            02  FILLER REDEFINES ACCTNO3F.
    public static class Filler141 extends NGroup {
      // COB:              03 ACCTNO3A    PICTURE X.
      public NChar acctno3a = new NChar(1);
    }

    public Filler141 filler141 = (Filler141) new Filler141().redefines(acctno3f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler143 = new NChar(4);
    // COB:            02  ACCTNO3I  PIC X(11).
    public NChar acctno3i = new NChar(11);
    // COB:            02  CRDNUM3L    COMP  PIC  S9(4).
    public NInt16 crdnum3l = new NInt16();
    // COB:            02  CRDNUM3F    PICTURE X.
    public NChar crdnum3f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDNUM3F.
    public static class Filler147 extends NGroup {
      // COB:              03 CRDNUM3A    PICTURE X.
      public NChar crdnum3a = new NChar(1);
    }

    public Filler147 filler147 = (Filler147) new Filler147().redefines(crdnum3f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler149 = new NChar(4);
    // COB:            02  CRDNUM3I  PIC X(16).
    public NChar crdnum3i = new NChar(16);
    // COB:            02  CRDSTS3L    COMP  PIC  S9(4).
    public NInt16 crdsts3l = new NInt16();
    // COB:            02  CRDSTS3F    PICTURE X.
    public NChar crdsts3f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSTS3F.
    public static class Filler153 extends NGroup {
      // COB:              03 CRDSTS3A    PICTURE X.
      public NChar crdsts3a = new NChar(1);
    }

    public Filler153 filler153 = (Filler153) new Filler153().redefines(crdsts3f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler155 = new NChar(4);
    // COB:            02  CRDSTS3I  PIC X(1).
    public NChar crdsts3i = new NChar(1);
    // COB:            02  CRDSEL4L    COMP  PIC  S9(4).
    public NInt16 crdsel4l = new NInt16();
    // COB:            02  CRDSEL4F    PICTURE X.
    public NChar crdsel4f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSEL4F.
    public static class Filler159 extends NGroup {
      // COB:              03 CRDSEL4A    PICTURE X.
      public NChar crdsel4a = new NChar(1);
    }

    public Filler159 filler159 = (Filler159) new Filler159().redefines(crdsel4f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler161 = new NChar(4);
    // COB:            02  CRDSEL4I  PIC X(1).
    public NChar crdsel4i = new NChar(1);
    // COB:            02  CRDSTP4L    COMP  PIC  S9(4).
    public NInt16 crdstp4l = new NInt16();
    // COB:            02  CRDSTP4F    PICTURE X.
    public NChar crdstp4f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSTP4F.
    public static class Filler165 extends NGroup {
      // COB:              03 CRDSTP4A    PICTURE X.
      public NChar crdstp4a = new NChar(1);
    }

    public Filler165 filler165 = (Filler165) new Filler165().redefines(crdstp4f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler167 = new NChar(4);
    // COB:            02  CRDSTP4I  PIC X(1).
    public NChar crdstp4i = new NChar(1);
    // COB:            02  ACCTNO4L    COMP  PIC  S9(4).
    public NInt16 acctno4l = new NInt16();
    // COB:            02  ACCTNO4F    PICTURE X.
    public NChar acctno4f = new NChar(1);

    // COB:            02  FILLER REDEFINES ACCTNO4F.
    public static class Filler171 extends NGroup {
      // COB:              03 ACCTNO4A    PICTURE X.
      public NChar acctno4a = new NChar(1);
    }

    public Filler171 filler171 = (Filler171) new Filler171().redefines(acctno4f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler173 = new NChar(4);
    // COB:            02  ACCTNO4I  PIC X(11).
    public NChar acctno4i = new NChar(11);
    // COB:            02  CRDNUM4L    COMP  PIC  S9(4).
    public NInt16 crdnum4l = new NInt16();
    // COB:            02  CRDNUM4F    PICTURE X.
    public NChar crdnum4f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDNUM4F.
    public static class Filler177 extends NGroup {
      // COB:              03 CRDNUM4A    PICTURE X.
      public NChar crdnum4a = new NChar(1);
    }

    public Filler177 filler177 = (Filler177) new Filler177().redefines(crdnum4f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler179 = new NChar(4);
    // COB:            02  CRDNUM4I  PIC X(16).
    public NChar crdnum4i = new NChar(16);
    // COB:            02  CRDSTS4L    COMP  PIC  S9(4).
    public NInt16 crdsts4l = new NInt16();
    // COB:            02  CRDSTS4F    PICTURE X.
    public NChar crdsts4f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSTS4F.
    public static class Filler183 extends NGroup {
      // COB:              03 CRDSTS4A    PICTURE X.
      public NChar crdsts4a = new NChar(1);
    }

    public Filler183 filler183 = (Filler183) new Filler183().redefines(crdsts4f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler185 = new NChar(4);
    // COB:            02  CRDSTS4I  PIC X(1).
    public NChar crdsts4i = new NChar(1);
    // COB:            02  CRDSEL5L    COMP  PIC  S9(4).
    public NInt16 crdsel5l = new NInt16();
    // COB:            02  CRDSEL5F    PICTURE X.
    public NChar crdsel5f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSEL5F.
    public static class Filler189 extends NGroup {
      // COB:              03 CRDSEL5A    PICTURE X.
      public NChar crdsel5a = new NChar(1);
    }

    public Filler189 filler189 = (Filler189) new Filler189().redefines(crdsel5f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler191 = new NChar(4);
    // COB:            02  CRDSEL5I  PIC X(1).
    public NChar crdsel5i = new NChar(1);
    // COB:            02  CRDSTP5L    COMP  PIC  S9(4).
    public NInt16 crdstp5l = new NInt16();
    // COB:            02  CRDSTP5F    PICTURE X.
    public NChar crdstp5f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSTP5F.
    public static class Filler195 extends NGroup {
      // COB:              03 CRDSTP5A    PICTURE X.
      public NChar crdstp5a = new NChar(1);
    }

    public Filler195 filler195 = (Filler195) new Filler195().redefines(crdstp5f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler197 = new NChar(4);
    // COB:            02  CRDSTP5I  PIC X(1).
    public NChar crdstp5i = new NChar(1);
    // COB:            02  ACCTNO5L    COMP  PIC  S9(4).
    public NInt16 acctno5l = new NInt16();
    // COB:            02  ACCTNO5F    PICTURE X.
    public NChar acctno5f = new NChar(1);

    // COB:            02  FILLER REDEFINES ACCTNO5F.
    public static class Filler201 extends NGroup {
      // COB:              03 ACCTNO5A    PICTURE X.
      public NChar acctno5a = new NChar(1);
    }

    public Filler201 filler201 = (Filler201) new Filler201().redefines(acctno5f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler203 = new NChar(4);
    // COB:            02  ACCTNO5I  PIC X(11).
    public NChar acctno5i = new NChar(11);
    // COB:            02  CRDNUM5L    COMP  PIC  S9(4).
    public NInt16 crdnum5l = new NInt16();
    // COB:            02  CRDNUM5F    PICTURE X.
    public NChar crdnum5f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDNUM5F.
    public static class Filler207 extends NGroup {
      // COB:              03 CRDNUM5A    PICTURE X.
      public NChar crdnum5a = new NChar(1);
    }

    public Filler207 filler207 = (Filler207) new Filler207().redefines(crdnum5f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler209 = new NChar(4);
    // COB:            02  CRDNUM5I  PIC X(16).
    public NChar crdnum5i = new NChar(16);
    // COB:            02  CRDSTS5L    COMP  PIC  S9(4).
    public NInt16 crdsts5l = new NInt16();
    // COB:            02  CRDSTS5F    PICTURE X.
    public NChar crdsts5f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSTS5F.
    public static class Filler213 extends NGroup {
      // COB:              03 CRDSTS5A    PICTURE X.
      public NChar crdsts5a = new NChar(1);
    }

    public Filler213 filler213 = (Filler213) new Filler213().redefines(crdsts5f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler215 = new NChar(4);
    // COB:            02  CRDSTS5I  PIC X(1).
    public NChar crdsts5i = new NChar(1);
    // COB:            02  CRDSEL6L    COMP  PIC  S9(4).
    public NInt16 crdsel6l = new NInt16();
    // COB:            02  CRDSEL6F    PICTURE X.
    public NChar crdsel6f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSEL6F.
    public static class Filler219 extends NGroup {
      // COB:              03 CRDSEL6A    PICTURE X.
      public NChar crdsel6a = new NChar(1);
    }

    public Filler219 filler219 = (Filler219) new Filler219().redefines(crdsel6f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler221 = new NChar(4);
    // COB:            02  CRDSEL6I  PIC X(1).
    public NChar crdsel6i = new NChar(1);
    // COB:            02  CRDSTP6L    COMP  PIC  S9(4).
    public NInt16 crdstp6l = new NInt16();
    // COB:            02  CRDSTP6F    PICTURE X.
    public NChar crdstp6f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSTP6F.
    public static class Filler225 extends NGroup {
      // COB:              03 CRDSTP6A    PICTURE X.
      public NChar crdstp6a = new NChar(1);
    }

    public Filler225 filler225 = (Filler225) new Filler225().redefines(crdstp6f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler227 = new NChar(4);
    // COB:            02  CRDSTP6I  PIC X(1).
    public NChar crdstp6i = new NChar(1);
    // COB:            02  ACCTNO6L    COMP  PIC  S9(4).
    public NInt16 acctno6l = new NInt16();
    // COB:            02  ACCTNO6F    PICTURE X.
    public NChar acctno6f = new NChar(1);

    // COB:            02  FILLER REDEFINES ACCTNO6F.
    public static class Filler231 extends NGroup {
      // COB:              03 ACCTNO6A    PICTURE X.
      public NChar acctno6a = new NChar(1);
    }

    public Filler231 filler231 = (Filler231) new Filler231().redefines(acctno6f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler233 = new NChar(4);
    // COB:            02  ACCTNO6I  PIC X(11).
    public NChar acctno6i = new NChar(11);
    // COB:            02  CRDNUM6L    COMP  PIC  S9(4).
    public NInt16 crdnum6l = new NInt16();
    // COB:            02  CRDNUM6F    PICTURE X.
    public NChar crdnum6f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDNUM6F.
    public static class Filler237 extends NGroup {
      // COB:              03 CRDNUM6A    PICTURE X.
      public NChar crdnum6a = new NChar(1);
    }

    public Filler237 filler237 = (Filler237) new Filler237().redefines(crdnum6f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler239 = new NChar(4);
    // COB:            02  CRDNUM6I  PIC X(16).
    public NChar crdnum6i = new NChar(16);
    // COB:            02  CRDSTS6L    COMP  PIC  S9(4).
    public NInt16 crdsts6l = new NInt16();
    // COB:            02  CRDSTS6F    PICTURE X.
    public NChar crdsts6f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSTS6F.
    public static class Filler243 extends NGroup {
      // COB:              03 CRDSTS6A    PICTURE X.
      public NChar crdsts6a = new NChar(1);
    }

    public Filler243 filler243 = (Filler243) new Filler243().redefines(crdsts6f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler245 = new NChar(4);
    // COB:            02  CRDSTS6I  PIC X(1).
    public NChar crdsts6i = new NChar(1);
    // COB:            02  CRDSEL7L    COMP  PIC  S9(4).
    public NInt16 crdsel7l = new NInt16();
    // COB:            02  CRDSEL7F    PICTURE X.
    public NChar crdsel7f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSEL7F.
    public static class Filler249 extends NGroup {
      // COB:              03 CRDSEL7A    PICTURE X.
      public NChar crdsel7a = new NChar(1);
    }

    public Filler249 filler249 = (Filler249) new Filler249().redefines(crdsel7f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler251 = new NChar(4);
    // COB:            02  CRDSEL7I  PIC X(1).
    public NChar crdsel7i = new NChar(1);
    // COB:            02  CRDSTP7L    COMP  PIC  S9(4).
    public NInt16 crdstp7l = new NInt16();
    // COB:            02  CRDSTP7F    PICTURE X.
    public NChar crdstp7f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSTP7F.
    public static class Filler255 extends NGroup {
      // COB:              03 CRDSTP7A    PICTURE X.
      public NChar crdstp7a = new NChar(1);
    }

    public Filler255 filler255 = (Filler255) new Filler255().redefines(crdstp7f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler257 = new NChar(4);
    // COB:            02  CRDSTP7I  PIC X(1).
    public NChar crdstp7i = new NChar(1);
    // COB:            02  ACCTNO7L    COMP  PIC  S9(4).
    public NInt16 acctno7l = new NInt16();
    // COB:            02  ACCTNO7F    PICTURE X.
    public NChar acctno7f = new NChar(1);

    // COB:            02  FILLER REDEFINES ACCTNO7F.
    public static class Filler261 extends NGroup {
      // COB:              03 ACCTNO7A    PICTURE X.
      public NChar acctno7a = new NChar(1);
    }

    public Filler261 filler261 = (Filler261) new Filler261().redefines(acctno7f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler263 = new NChar(4);
    // COB:            02  ACCTNO7I  PIC X(11).
    public NChar acctno7i = new NChar(11);
    // COB:            02  CRDNUM7L    COMP  PIC  S9(4).
    public NInt16 crdnum7l = new NInt16();
    // COB:            02  CRDNUM7F    PICTURE X.
    public NChar crdnum7f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDNUM7F.
    public static class Filler267 extends NGroup {
      // COB:              03 CRDNUM7A    PICTURE X.
      public NChar crdnum7a = new NChar(1);
    }

    public Filler267 filler267 = (Filler267) new Filler267().redefines(crdnum7f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler269 = new NChar(4);
    // COB:            02  CRDNUM7I  PIC X(16).
    public NChar crdnum7i = new NChar(16);
    // COB:            02  CRDSTS7L    COMP  PIC  S9(4).
    public NInt16 crdsts7l = new NInt16();
    // COB:            02  CRDSTS7F    PICTURE X.
    public NChar crdsts7f = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDSTS7F.
    public static class Filler273 extends NGroup {
      // COB:              03 CRDSTS7A    PICTURE X.
      public NChar crdsts7a = new NChar(1);
    }

    public Filler273 filler273 = (Filler273) new Filler273().redefines(crdsts7f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler275 = new NChar(4);
    // COB:            02  CRDSTS7I  PIC X(1).
    public NChar crdsts7i = new NChar(1);
    // COB:            02  INFOMSGL    COMP  PIC  S9(4).
    public NInt16 infomsgl = new NInt16();
    // COB:            02  INFOMSGF    PICTURE X.
    public NChar infomsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES INFOMSGF.
    public static class Filler279 extends NGroup {
      // COB:              03 INFOMSGA    PICTURE X.
      public NChar infomsga = new NChar(1);
    }

    public Filler279 filler279 = (Filler279) new Filler279().redefines(infomsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler281 = new NChar(4);
    // COB:            02  INFOMSGI  PIC X(45).
    public NChar infomsgi = new NChar(45);
    // COB:            02  ERRMSGL    COMP  PIC  S9(4).
    public NInt16 errmsgl = new NInt16();
    // COB:            02  ERRMSGF    PICTURE X.
    public NChar errmsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES ERRMSGF.
    public static class Filler285 extends NGroup {
      // COB:              03 ERRMSGA    PICTURE X.
      public NChar errmsga = new NChar(1);
    }

    public Filler285 filler285 = (Filler285) new Filler285().redefines(errmsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler287 = new NChar(4);
    // COB:            02  ERRMSGI  PIC X(78).
    public NChar errmsgi = new NChar(78);
  }

  public Ccrdliai ccrdliai = new Ccrdliai();

  // COB:        01  CCRDLIAO REDEFINES CCRDLIAI.
  public static class Ccrdliao extends NGroup {
    // COB:            02  FILLER PIC X(12).
    public NChar filler290 = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler291 = new NChar(3);
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
    public NChar filler297 = new NChar(3);
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
    public NChar filler303 = new NChar(3);
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
    public NChar filler309 = new NChar(3);
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
    public NChar filler315 = new NChar(3);
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
    public NChar filler321 = new NChar(3);
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
    public NChar filler327 = new NChar(3);
    // COB:            02  PAGENOC    PICTURE X.
    public NChar pagenoc = new NChar(1);
    // COB:            02  PAGENOP    PICTURE X.
    public NChar pagenop = new NChar(1);
    // COB:            02  PAGENOH    PICTURE X.
    public NChar pagenoh = new NChar(1);
    // COB:            02  PAGENOV    PICTURE X.
    public NChar pagenov = new NChar(1);
    // COB:            02  PAGENOO  PIC X(3).
    public NChar pagenoo = new NChar(3);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler333 = new NChar(3);
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
    public NChar filler339 = new NChar(3);
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
    public NChar filler345 = new NChar(3);
    // COB:            02  CRDSEL1C    PICTURE X.
    public NChar crdsel1c = new NChar(1);
    // COB:            02  CRDSEL1P    PICTURE X.
    public NChar crdsel1p = new NChar(1);
    // COB:            02  CRDSEL1H    PICTURE X.
    public NChar crdsel1h = new NChar(1);
    // COB:            02  CRDSEL1V    PICTURE X.
    public NChar crdsel1v = new NChar(1);
    // COB:            02  CRDSEL1O  PIC X(1).
    public NChar crdsel1o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler351 = new NChar(3);
    // COB:            02  ACCTNO1C    PICTURE X.
    public NChar acctno1c = new NChar(1);
    // COB:            02  ACCTNO1P    PICTURE X.
    public NChar acctno1p = new NChar(1);
    // COB:            02  ACCTNO1H    PICTURE X.
    public NChar acctno1h = new NChar(1);
    // COB:            02  ACCTNO1V    PICTURE X.
    public NChar acctno1v = new NChar(1);
    // COB:            02  ACCTNO1O  PIC X(11).
    public NChar acctno1o = new NChar(11);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler357 = new NChar(3);
    // COB:            02  CRDNUM1C    PICTURE X.
    public NChar crdnum1c = new NChar(1);
    // COB:            02  CRDNUM1P    PICTURE X.
    public NChar crdnum1p = new NChar(1);
    // COB:            02  CRDNUM1H    PICTURE X.
    public NChar crdnum1h = new NChar(1);
    // COB:            02  CRDNUM1V    PICTURE X.
    public NChar crdnum1v = new NChar(1);
    // COB:            02  CRDNUM1O  PIC X(16).
    public NChar crdnum1o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler363 = new NChar(3);
    // COB:            02  CRDSTS1C    PICTURE X.
    public NChar crdsts1c = new NChar(1);
    // COB:            02  CRDSTS1P    PICTURE X.
    public NChar crdsts1p = new NChar(1);
    // COB:            02  CRDSTS1H    PICTURE X.
    public NChar crdsts1h = new NChar(1);
    // COB:            02  CRDSTS1V    PICTURE X.
    public NChar crdsts1v = new NChar(1);
    // COB:            02  CRDSTS1O  PIC X(1).
    public NChar crdsts1o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler369 = new NChar(3);
    // COB:            02  CRDSEL2C    PICTURE X.
    public NChar crdsel2c = new NChar(1);
    // COB:            02  CRDSEL2P    PICTURE X.
    public NChar crdsel2p = new NChar(1);
    // COB:            02  CRDSEL2H    PICTURE X.
    public NChar crdsel2h = new NChar(1);
    // COB:            02  CRDSEL2V    PICTURE X.
    public NChar crdsel2v = new NChar(1);
    // COB:            02  CRDSEL2O  PIC X(1).
    public NChar crdsel2o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler375 = new NChar(3);
    // COB:            02  CRDSTP2C    PICTURE X.
    public NChar crdstp2c = new NChar(1);
    // COB:            02  CRDSTP2P    PICTURE X.
    public NChar crdstp2p = new NChar(1);
    // COB:            02  CRDSTP2H    PICTURE X.
    public NChar crdstp2h = new NChar(1);
    // COB:            02  CRDSTP2V    PICTURE X.
    public NChar crdstp2v = new NChar(1);
    // COB:            02  CRDSTP2O  PIC X(1).
    public NChar crdstp2o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler381 = new NChar(3);
    // COB:            02  ACCTNO2C    PICTURE X.
    public NChar acctno2c = new NChar(1);
    // COB:            02  ACCTNO2P    PICTURE X.
    public NChar acctno2p = new NChar(1);
    // COB:            02  ACCTNO2H    PICTURE X.
    public NChar acctno2h = new NChar(1);
    // COB:            02  ACCTNO2V    PICTURE X.
    public NChar acctno2v = new NChar(1);
    // COB:            02  ACCTNO2O  PIC X(11).
    public NChar acctno2o = new NChar(11);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler387 = new NChar(3);
    // COB:            02  CRDNUM2C    PICTURE X.
    public NChar crdnum2c = new NChar(1);
    // COB:            02  CRDNUM2P    PICTURE X.
    public NChar crdnum2p = new NChar(1);
    // COB:            02  CRDNUM2H    PICTURE X.
    public NChar crdnum2h = new NChar(1);
    // COB:            02  CRDNUM2V    PICTURE X.
    public NChar crdnum2v = new NChar(1);
    // COB:            02  CRDNUM2O  PIC X(16).
    public NChar crdnum2o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler393 = new NChar(3);
    // COB:            02  CRDSTS2C    PICTURE X.
    public NChar crdsts2c = new NChar(1);
    // COB:            02  CRDSTS2P    PICTURE X.
    public NChar crdsts2p = new NChar(1);
    // COB:            02  CRDSTS2H    PICTURE X.
    public NChar crdsts2h = new NChar(1);
    // COB:            02  CRDSTS2V    PICTURE X.
    public NChar crdsts2v = new NChar(1);
    // COB:            02  CRDSTS2O  PIC X(1).
    public NChar crdsts2o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler399 = new NChar(3);
    // COB:            02  CRDSEL3C    PICTURE X.
    public NChar crdsel3c = new NChar(1);
    // COB:            02  CRDSEL3P    PICTURE X.
    public NChar crdsel3p = new NChar(1);
    // COB:            02  CRDSEL3H    PICTURE X.
    public NChar crdsel3h = new NChar(1);
    // COB:            02  CRDSEL3V    PICTURE X.
    public NChar crdsel3v = new NChar(1);
    // COB:            02  CRDSEL3O  PIC X(1).
    public NChar crdsel3o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler405 = new NChar(3);
    // COB:            02  CRDSTP3C    PICTURE X.
    public NChar crdstp3c = new NChar(1);
    // COB:            02  CRDSTP3P    PICTURE X.
    public NChar crdstp3p = new NChar(1);
    // COB:            02  CRDSTP3H    PICTURE X.
    public NChar crdstp3h = new NChar(1);
    // COB:            02  CRDSTP3V    PICTURE X.
    public NChar crdstp3v = new NChar(1);
    // COB:            02  CRDSTP3O  PIC X(1).
    public NChar crdstp3o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler411 = new NChar(3);
    // COB:            02  ACCTNO3C    PICTURE X.
    public NChar acctno3c = new NChar(1);
    // COB:            02  ACCTNO3P    PICTURE X.
    public NChar acctno3p = new NChar(1);
    // COB:            02  ACCTNO3H    PICTURE X.
    public NChar acctno3h = new NChar(1);
    // COB:            02  ACCTNO3V    PICTURE X.
    public NChar acctno3v = new NChar(1);
    // COB:            02  ACCTNO3O  PIC X(11).
    public NChar acctno3o = new NChar(11);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler417 = new NChar(3);
    // COB:            02  CRDNUM3C    PICTURE X.
    public NChar crdnum3c = new NChar(1);
    // COB:            02  CRDNUM3P    PICTURE X.
    public NChar crdnum3p = new NChar(1);
    // COB:            02  CRDNUM3H    PICTURE X.
    public NChar crdnum3h = new NChar(1);
    // COB:            02  CRDNUM3V    PICTURE X.
    public NChar crdnum3v = new NChar(1);
    // COB:            02  CRDNUM3O  PIC X(16).
    public NChar crdnum3o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler423 = new NChar(3);
    // COB:            02  CRDSTS3C    PICTURE X.
    public NChar crdsts3c = new NChar(1);
    // COB:            02  CRDSTS3P    PICTURE X.
    public NChar crdsts3p = new NChar(1);
    // COB:            02  CRDSTS3H    PICTURE X.
    public NChar crdsts3h = new NChar(1);
    // COB:            02  CRDSTS3V    PICTURE X.
    public NChar crdsts3v = new NChar(1);
    // COB:            02  CRDSTS3O  PIC X(1).
    public NChar crdsts3o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler429 = new NChar(3);
    // COB:            02  CRDSEL4C    PICTURE X.
    public NChar crdsel4c = new NChar(1);
    // COB:            02  CRDSEL4P    PICTURE X.
    public NChar crdsel4p = new NChar(1);
    // COB:            02  CRDSEL4H    PICTURE X.
    public NChar crdsel4h = new NChar(1);
    // COB:            02  CRDSEL4V    PICTURE X.
    public NChar crdsel4v = new NChar(1);
    // COB:            02  CRDSEL4O  PIC X(1).
    public NChar crdsel4o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler435 = new NChar(3);
    // COB:            02  CRDSTP4C    PICTURE X.
    public NChar crdstp4c = new NChar(1);
    // COB:            02  CRDSTP4P    PICTURE X.
    public NChar crdstp4p = new NChar(1);
    // COB:            02  CRDSTP4H    PICTURE X.
    public NChar crdstp4h = new NChar(1);
    // COB:            02  CRDSTP4V    PICTURE X.
    public NChar crdstp4v = new NChar(1);
    // COB:            02  CRDSTP4O  PIC X(1).
    public NChar crdstp4o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler441 = new NChar(3);
    // COB:            02  ACCTNO4C    PICTURE X.
    public NChar acctno4c = new NChar(1);
    // COB:            02  ACCTNO4P    PICTURE X.
    public NChar acctno4p = new NChar(1);
    // COB:            02  ACCTNO4H    PICTURE X.
    public NChar acctno4h = new NChar(1);
    // COB:            02  ACCTNO4V    PICTURE X.
    public NChar acctno4v = new NChar(1);
    // COB:            02  ACCTNO4O  PIC X(11).
    public NChar acctno4o = new NChar(11);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler447 = new NChar(3);
    // COB:            02  CRDNUM4C    PICTURE X.
    public NChar crdnum4c = new NChar(1);
    // COB:            02  CRDNUM4P    PICTURE X.
    public NChar crdnum4p = new NChar(1);
    // COB:            02  CRDNUM4H    PICTURE X.
    public NChar crdnum4h = new NChar(1);
    // COB:            02  CRDNUM4V    PICTURE X.
    public NChar crdnum4v = new NChar(1);
    // COB:            02  CRDNUM4O  PIC X(16).
    public NChar crdnum4o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler453 = new NChar(3);
    // COB:            02  CRDSTS4C    PICTURE X.
    public NChar crdsts4c = new NChar(1);
    // COB:            02  CRDSTS4P    PICTURE X.
    public NChar crdsts4p = new NChar(1);
    // COB:            02  CRDSTS4H    PICTURE X.
    public NChar crdsts4h = new NChar(1);
    // COB:            02  CRDSTS4V    PICTURE X.
    public NChar crdsts4v = new NChar(1);
    // COB:            02  CRDSTS4O  PIC X(1).
    public NChar crdsts4o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler459 = new NChar(3);
    // COB:            02  CRDSEL5C    PICTURE X.
    public NChar crdsel5c = new NChar(1);
    // COB:            02  CRDSEL5P    PICTURE X.
    public NChar crdsel5p = new NChar(1);
    // COB:            02  CRDSEL5H    PICTURE X.
    public NChar crdsel5h = new NChar(1);
    // COB:            02  CRDSEL5V    PICTURE X.
    public NChar crdsel5v = new NChar(1);
    // COB:            02  CRDSEL5O  PIC X(1).
    public NChar crdsel5o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler465 = new NChar(3);
    // COB:            02  CRDSTP5C    PICTURE X.
    public NChar crdstp5c = new NChar(1);
    // COB:            02  CRDSTP5P    PICTURE X.
    public NChar crdstp5p = new NChar(1);
    // COB:            02  CRDSTP5H    PICTURE X.
    public NChar crdstp5h = new NChar(1);
    // COB:            02  CRDSTP5V    PICTURE X.
    public NChar crdstp5v = new NChar(1);
    // COB:            02  CRDSTP5O  PIC X(1).
    public NChar crdstp5o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler471 = new NChar(3);
    // COB:            02  ACCTNO5C    PICTURE X.
    public NChar acctno5c = new NChar(1);
    // COB:            02  ACCTNO5P    PICTURE X.
    public NChar acctno5p = new NChar(1);
    // COB:            02  ACCTNO5H    PICTURE X.
    public NChar acctno5h = new NChar(1);
    // COB:            02  ACCTNO5V    PICTURE X.
    public NChar acctno5v = new NChar(1);
    // COB:            02  ACCTNO5O  PIC X(11).
    public NChar acctno5o = new NChar(11);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler477 = new NChar(3);
    // COB:            02  CRDNUM5C    PICTURE X.
    public NChar crdnum5c = new NChar(1);
    // COB:            02  CRDNUM5P    PICTURE X.
    public NChar crdnum5p = new NChar(1);
    // COB:            02  CRDNUM5H    PICTURE X.
    public NChar crdnum5h = new NChar(1);
    // COB:            02  CRDNUM5V    PICTURE X.
    public NChar crdnum5v = new NChar(1);
    // COB:            02  CRDNUM5O  PIC X(16).
    public NChar crdnum5o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler483 = new NChar(3);
    // COB:            02  CRDSTS5C    PICTURE X.
    public NChar crdsts5c = new NChar(1);
    // COB:            02  CRDSTS5P    PICTURE X.
    public NChar crdsts5p = new NChar(1);
    // COB:            02  CRDSTS5H    PICTURE X.
    public NChar crdsts5h = new NChar(1);
    // COB:            02  CRDSTS5V    PICTURE X.
    public NChar crdsts5v = new NChar(1);
    // COB:            02  CRDSTS5O  PIC X(1).
    public NChar crdsts5o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler489 = new NChar(3);
    // COB:            02  CRDSEL6C    PICTURE X.
    public NChar crdsel6c = new NChar(1);
    // COB:            02  CRDSEL6P    PICTURE X.
    public NChar crdsel6p = new NChar(1);
    // COB:            02  CRDSEL6H    PICTURE X.
    public NChar crdsel6h = new NChar(1);
    // COB:            02  CRDSEL6V    PICTURE X.
    public NChar crdsel6v = new NChar(1);
    // COB:            02  CRDSEL6O  PIC X(1).
    public NChar crdsel6o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler495 = new NChar(3);
    // COB:            02  CRDSTP6C    PICTURE X.
    public NChar crdstp6c = new NChar(1);
    // COB:            02  CRDSTP6P    PICTURE X.
    public NChar crdstp6p = new NChar(1);
    // COB:            02  CRDSTP6H    PICTURE X.
    public NChar crdstp6h = new NChar(1);
    // COB:            02  CRDSTP6V    PICTURE X.
    public NChar crdstp6v = new NChar(1);
    // COB:            02  CRDSTP6O  PIC X(1).
    public NChar crdstp6o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler501 = new NChar(3);
    // COB:            02  ACCTNO6C    PICTURE X.
    public NChar acctno6c = new NChar(1);
    // COB:            02  ACCTNO6P    PICTURE X.
    public NChar acctno6p = new NChar(1);
    // COB:            02  ACCTNO6H    PICTURE X.
    public NChar acctno6h = new NChar(1);
    // COB:            02  ACCTNO6V    PICTURE X.
    public NChar acctno6v = new NChar(1);
    // COB:            02  ACCTNO6O  PIC X(11).
    public NChar acctno6o = new NChar(11);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler507 = new NChar(3);
    // COB:            02  CRDNUM6C    PICTURE X.
    public NChar crdnum6c = new NChar(1);
    // COB:            02  CRDNUM6P    PICTURE X.
    public NChar crdnum6p = new NChar(1);
    // COB:            02  CRDNUM6H    PICTURE X.
    public NChar crdnum6h = new NChar(1);
    // COB:            02  CRDNUM6V    PICTURE X.
    public NChar crdnum6v = new NChar(1);
    // COB:            02  CRDNUM6O  PIC X(16).
    public NChar crdnum6o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler513 = new NChar(3);
    // COB:            02  CRDSTS6C    PICTURE X.
    public NChar crdsts6c = new NChar(1);
    // COB:            02  CRDSTS6P    PICTURE X.
    public NChar crdsts6p = new NChar(1);
    // COB:            02  CRDSTS6H    PICTURE X.
    public NChar crdsts6h = new NChar(1);
    // COB:            02  CRDSTS6V    PICTURE X.
    public NChar crdsts6v = new NChar(1);
    // COB:            02  CRDSTS6O  PIC X(1).
    public NChar crdsts6o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler519 = new NChar(3);
    // COB:            02  CRDSEL7C    PICTURE X.
    public NChar crdsel7c = new NChar(1);
    // COB:            02  CRDSEL7P    PICTURE X.
    public NChar crdsel7p = new NChar(1);
    // COB:            02  CRDSEL7H    PICTURE X.
    public NChar crdsel7h = new NChar(1);
    // COB:            02  CRDSEL7V    PICTURE X.
    public NChar crdsel7v = new NChar(1);
    // COB:            02  CRDSEL7O  PIC X(1).
    public NChar crdsel7o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler525 = new NChar(3);
    // COB:            02  CRDSTP7C    PICTURE X.
    public NChar crdstp7c = new NChar(1);
    // COB:            02  CRDSTP7P    PICTURE X.
    public NChar crdstp7p = new NChar(1);
    // COB:            02  CRDSTP7H    PICTURE X.
    public NChar crdstp7h = new NChar(1);
    // COB:            02  CRDSTP7V    PICTURE X.
    public NChar crdstp7v = new NChar(1);
    // COB:            02  CRDSTP7O  PIC X(1).
    public NChar crdstp7o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler531 = new NChar(3);
    // COB:            02  ACCTNO7C    PICTURE X.
    public NChar acctno7c = new NChar(1);
    // COB:            02  ACCTNO7P    PICTURE X.
    public NChar acctno7p = new NChar(1);
    // COB:            02  ACCTNO7H    PICTURE X.
    public NChar acctno7h = new NChar(1);
    // COB:            02  ACCTNO7V    PICTURE X.
    public NChar acctno7v = new NChar(1);
    // COB:            02  ACCTNO7O  PIC X(11).
    public NChar acctno7o = new NChar(11);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler537 = new NChar(3);
    // COB:            02  CRDNUM7C    PICTURE X.
    public NChar crdnum7c = new NChar(1);
    // COB:            02  CRDNUM7P    PICTURE X.
    public NChar crdnum7p = new NChar(1);
    // COB:            02  CRDNUM7H    PICTURE X.
    public NChar crdnum7h = new NChar(1);
    // COB:            02  CRDNUM7V    PICTURE X.
    public NChar crdnum7v = new NChar(1);
    // COB:            02  CRDNUM7O  PIC X(16).
    public NChar crdnum7o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler543 = new NChar(3);
    // COB:            02  CRDSTS7C    PICTURE X.
    public NChar crdsts7c = new NChar(1);
    // COB:            02  CRDSTS7P    PICTURE X.
    public NChar crdsts7p = new NChar(1);
    // COB:            02  CRDSTS7H    PICTURE X.
    public NChar crdsts7h = new NChar(1);
    // COB:            02  CRDSTS7V    PICTURE X.
    public NChar crdsts7v = new NChar(1);
    // COB:            02  CRDSTS7O  PIC X(1).
    public NChar crdsts7o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler549 = new NChar(3);
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
    public NChar filler555 = new NChar(3);
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

  public Ccrdliao ccrdliao = (Ccrdliao) new Ccrdliao().redefines(ccrdliai);
}
