package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cotrtli extends NGroup {

  // COB:        01  CTRTLIAI.
  public static class Ctrtliai extends NGroup {
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
    // COB:            02  TRTYPEL    COMP  PIC  S9(4).
    public NInt16 trtypel = new NInt16();
    // COB:            02  TRTYPEF    PICTURE X.
    public NChar trtypef = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTYPEF.
    public static class Filler63 extends NGroup {
      // COB:              03 TRTYPEA    PICTURE X.
      public NChar trtypea = new NChar(1);
    }

    public Filler63 filler63 = (Filler63) new Filler63().redefines(trtypef);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler65 = new NChar(4);
    // COB:            02  TRTYPEI  PIC X(2).
    public NChar trtypei = new NChar(2);
    // COB:            02  TRDESCL    COMP  PIC  S9(4).
    public NInt16 trdescl = new NInt16();
    // COB:            02  TRDESCF    PICTURE X.
    public NChar trdescf = new NChar(1);

    // COB:            02  FILLER REDEFINES TRDESCF.
    public static class Filler69 extends NGroup {
      // COB:              03 TRDESCA    PICTURE X.
      public NChar trdesca = new NChar(1);
    }

    public Filler69 filler69 = (Filler69) new Filler69().redefines(trdescf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler71 = new NChar(4);
    // COB:            02  TRDESCI  PIC X(50).
    public NChar trdesci = new NChar(50);
    // COB:            02  TRTSEL1L    COMP  PIC  S9(4).
    public NInt16 trtsel1l = new NInt16();
    // COB:            02  TRTSEL1F    PICTURE X.
    public NChar trtsel1f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTSEL1F.
    public static class Filler75 extends NGroup {
      // COB:              03 TRTSEL1A    PICTURE X.
      public NChar trtsel1a = new NChar(1);
    }

    public Filler75 filler75 = (Filler75) new Filler75().redefines(trtsel1f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler77 = new NChar(4);
    // COB:            02  TRTSEL1I  PIC X(1).
    public NChar trtsel1i = new NChar(1);
    // COB:            02  TRTTYP1L    COMP  PIC  S9(4).
    public NInt16 trttyp1l = new NInt16();
    // COB:            02  TRTTYP1F    PICTURE X.
    public NChar trttyp1f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTTYP1F.
    public static class Filler81 extends NGroup {
      // COB:              03 TRTTYP1A    PICTURE X.
      public NChar trttyp1a = new NChar(1);
    }

    public Filler81 filler81 = (Filler81) new Filler81().redefines(trttyp1f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler83 = new NChar(4);
    // COB:            02  TRTTYP1I  PIC X(2).
    public NChar trttyp1i = new NChar(2);
    // COB:            02  TRTYPD1L    COMP  PIC  S9(4).
    public NInt16 trtypd1l = new NInt16();
    // COB:            02  TRTYPD1F    PICTURE X.
    public NChar trtypd1f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTYPD1F.
    public static class Filler87 extends NGroup {
      // COB:              03 TRTYPD1A    PICTURE X.
      public NChar trtypd1a = new NChar(1);
    }

    public Filler87 filler87 = (Filler87) new Filler87().redefines(trtypd1f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler89 = new NChar(4);
    // COB:            02  TRTYPD1I  PIC X(50).
    public NChar trtypd1i = new NChar(50);
    // COB:            02  TRTSEL2L    COMP  PIC  S9(4).
    public NInt16 trtsel2l = new NInt16();
    // COB:            02  TRTSEL2F    PICTURE X.
    public NChar trtsel2f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTSEL2F.
    public static class Filler93 extends NGroup {
      // COB:              03 TRTSEL2A    PICTURE X.
      public NChar trtsel2a = new NChar(1);
    }

    public Filler93 filler93 = (Filler93) new Filler93().redefines(trtsel2f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler95 = new NChar(4);
    // COB:            02  TRTSEL2I  PIC X(1).
    public NChar trtsel2i = new NChar(1);
    // COB:            02  TRTTYP2L    COMP  PIC  S9(4).
    public NInt16 trttyp2l = new NInt16();
    // COB:            02  TRTTYP2F    PICTURE X.
    public NChar trttyp2f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTTYP2F.
    public static class Filler99 extends NGroup {
      // COB:              03 TRTTYP2A    PICTURE X.
      public NChar trttyp2a = new NChar(1);
    }

    public Filler99 filler99 = (Filler99) new Filler99().redefines(trttyp2f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler101 = new NChar(4);
    // COB:            02  TRTTYP2I  PIC X(2).
    public NChar trttyp2i = new NChar(2);
    // COB:            02  TRTYPD2L    COMP  PIC  S9(4).
    public NInt16 trtypd2l = new NInt16();
    // COB:            02  TRTYPD2F    PICTURE X.
    public NChar trtypd2f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTYPD2F.
    public static class Filler105 extends NGroup {
      // COB:              03 TRTYPD2A    PICTURE X.
      public NChar trtypd2a = new NChar(1);
    }

    public Filler105 filler105 = (Filler105) new Filler105().redefines(trtypd2f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler107 = new NChar(4);
    // COB:            02  TRTYPD2I  PIC X(50).
    public NChar trtypd2i = new NChar(50);
    // COB:            02  TRTSEL3L    COMP  PIC  S9(4).
    public NInt16 trtsel3l = new NInt16();
    // COB:            02  TRTSEL3F    PICTURE X.
    public NChar trtsel3f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTSEL3F.
    public static class Filler111 extends NGroup {
      // COB:              03 TRTSEL3A    PICTURE X.
      public NChar trtsel3a = new NChar(1);
    }

    public Filler111 filler111 = (Filler111) new Filler111().redefines(trtsel3f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler113 = new NChar(4);
    // COB:            02  TRTSEL3I  PIC X(1).
    public NChar trtsel3i = new NChar(1);
    // COB:            02  TRTTYP3L    COMP  PIC  S9(4).
    public NInt16 trttyp3l = new NInt16();
    // COB:            02  TRTTYP3F    PICTURE X.
    public NChar trttyp3f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTTYP3F.
    public static class Filler117 extends NGroup {
      // COB:              03 TRTTYP3A    PICTURE X.
      public NChar trttyp3a = new NChar(1);
    }

    public Filler117 filler117 = (Filler117) new Filler117().redefines(trttyp3f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler119 = new NChar(4);
    // COB:            02  TRTTYP3I  PIC X(2).
    public NChar trttyp3i = new NChar(2);
    // COB:            02  TRTYPD3L    COMP  PIC  S9(4).
    public NInt16 trtypd3l = new NInt16();
    // COB:            02  TRTYPD3F    PICTURE X.
    public NChar trtypd3f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTYPD3F.
    public static class Filler123 extends NGroup {
      // COB:              03 TRTYPD3A    PICTURE X.
      public NChar trtypd3a = new NChar(1);
    }

    public Filler123 filler123 = (Filler123) new Filler123().redefines(trtypd3f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler125 = new NChar(4);
    // COB:            02  TRTYPD3I  PIC X(50).
    public NChar trtypd3i = new NChar(50);
    // COB:            02  TRTSEL4L    COMP  PIC  S9(4).
    public NInt16 trtsel4l = new NInt16();
    // COB:            02  TRTSEL4F    PICTURE X.
    public NChar trtsel4f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTSEL4F.
    public static class Filler129 extends NGroup {
      // COB:              03 TRTSEL4A    PICTURE X.
      public NChar trtsel4a = new NChar(1);
    }

    public Filler129 filler129 = (Filler129) new Filler129().redefines(trtsel4f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler131 = new NChar(4);
    // COB:            02  TRTSEL4I  PIC X(1).
    public NChar trtsel4i = new NChar(1);
    // COB:            02  TRTTYP4L    COMP  PIC  S9(4).
    public NInt16 trttyp4l = new NInt16();
    // COB:            02  TRTTYP4F    PICTURE X.
    public NChar trttyp4f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTTYP4F.
    public static class Filler135 extends NGroup {
      // COB:              03 TRTTYP4A    PICTURE X.
      public NChar trttyp4a = new NChar(1);
    }

    public Filler135 filler135 = (Filler135) new Filler135().redefines(trttyp4f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler137 = new NChar(4);
    // COB:            02  TRTTYP4I  PIC X(2).
    public NChar trttyp4i = new NChar(2);
    // COB:            02  TRTYPD4L    COMP  PIC  S9(4).
    public NInt16 trtypd4l = new NInt16();
    // COB:            02  TRTYPD4F    PICTURE X.
    public NChar trtypd4f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTYPD4F.
    public static class Filler141 extends NGroup {
      // COB:              03 TRTYPD4A    PICTURE X.
      public NChar trtypd4a = new NChar(1);
    }

    public Filler141 filler141 = (Filler141) new Filler141().redefines(trtypd4f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler143 = new NChar(4);
    // COB:            02  TRTYPD4I  PIC X(50).
    public NChar trtypd4i = new NChar(50);
    // COB:            02  TRTSEL5L    COMP  PIC  S9(4).
    public NInt16 trtsel5l = new NInt16();
    // COB:            02  TRTSEL5F    PICTURE X.
    public NChar trtsel5f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTSEL5F.
    public static class Filler147 extends NGroup {
      // COB:              03 TRTSEL5A    PICTURE X.
      public NChar trtsel5a = new NChar(1);
    }

    public Filler147 filler147 = (Filler147) new Filler147().redefines(trtsel5f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler149 = new NChar(4);
    // COB:            02  TRTSEL5I  PIC X(1).
    public NChar trtsel5i = new NChar(1);
    // COB:            02  TRTTYP5L    COMP  PIC  S9(4).
    public NInt16 trttyp5l = new NInt16();
    // COB:            02  TRTTYP5F    PICTURE X.
    public NChar trttyp5f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTTYP5F.
    public static class Filler153 extends NGroup {
      // COB:              03 TRTTYP5A    PICTURE X.
      public NChar trttyp5a = new NChar(1);
    }

    public Filler153 filler153 = (Filler153) new Filler153().redefines(trttyp5f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler155 = new NChar(4);
    // COB:            02  TRTTYP5I  PIC X(2).
    public NChar trttyp5i = new NChar(2);
    // COB:            02  TRTYPD5L    COMP  PIC  S9(4).
    public NInt16 trtypd5l = new NInt16();
    // COB:            02  TRTYPD5F    PICTURE X.
    public NChar trtypd5f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTYPD5F.
    public static class Filler159 extends NGroup {
      // COB:              03 TRTYPD5A    PICTURE X.
      public NChar trtypd5a = new NChar(1);
    }

    public Filler159 filler159 = (Filler159) new Filler159().redefines(trtypd5f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler161 = new NChar(4);
    // COB:            02  TRTYPD5I  PIC X(50).
    public NChar trtypd5i = new NChar(50);
    // COB:            02  TRTSEL6L    COMP  PIC  S9(4).
    public NInt16 trtsel6l = new NInt16();
    // COB:            02  TRTSEL6F    PICTURE X.
    public NChar trtsel6f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTSEL6F.
    public static class Filler165 extends NGroup {
      // COB:              03 TRTSEL6A    PICTURE X.
      public NChar trtsel6a = new NChar(1);
    }

    public Filler165 filler165 = (Filler165) new Filler165().redefines(trtsel6f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler167 = new NChar(4);
    // COB:            02  TRTSEL6I  PIC X(1).
    public NChar trtsel6i = new NChar(1);
    // COB:            02  TRTTYP6L    COMP  PIC  S9(4).
    public NInt16 trttyp6l = new NInt16();
    // COB:            02  TRTTYP6F    PICTURE X.
    public NChar trttyp6f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTTYP6F.
    public static class Filler171 extends NGroup {
      // COB:              03 TRTTYP6A    PICTURE X.
      public NChar trttyp6a = new NChar(1);
    }

    public Filler171 filler171 = (Filler171) new Filler171().redefines(trttyp6f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler173 = new NChar(4);
    // COB:            02  TRTTYP6I  PIC X(2).
    public NChar trttyp6i = new NChar(2);
    // COB:            02  TRTYPD6L    COMP  PIC  S9(4).
    public NInt16 trtypd6l = new NInt16();
    // COB:            02  TRTYPD6F    PICTURE X.
    public NChar trtypd6f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTYPD6F.
    public static class Filler177 extends NGroup {
      // COB:              03 TRTYPD6A    PICTURE X.
      public NChar trtypd6a = new NChar(1);
    }

    public Filler177 filler177 = (Filler177) new Filler177().redefines(trtypd6f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler179 = new NChar(4);
    // COB:            02  TRTYPD6I  PIC X(50).
    public NChar trtypd6i = new NChar(50);
    // COB:            02  TRTSEL7L    COMP  PIC  S9(4).
    public NInt16 trtsel7l = new NInt16();
    // COB:            02  TRTSEL7F    PICTURE X.
    public NChar trtsel7f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTSEL7F.
    public static class Filler183 extends NGroup {
      // COB:              03 TRTSEL7A    PICTURE X.
      public NChar trtsel7a = new NChar(1);
    }

    public Filler183 filler183 = (Filler183) new Filler183().redefines(trtsel7f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler185 = new NChar(4);
    // COB:            02  TRTSEL7I  PIC X(1).
    public NChar trtsel7i = new NChar(1);
    // COB:            02  TRTTYP7L    COMP  PIC  S9(4).
    public NInt16 trttyp7l = new NInt16();
    // COB:            02  TRTTYP7F    PICTURE X.
    public NChar trttyp7f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTTYP7F.
    public static class Filler189 extends NGroup {
      // COB:              03 TRTTYP7A    PICTURE X.
      public NChar trttyp7a = new NChar(1);
    }

    public Filler189 filler189 = (Filler189) new Filler189().redefines(trttyp7f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler191 = new NChar(4);
    // COB:            02  TRTTYP7I  PIC X(2).
    public NChar trttyp7i = new NChar(2);
    // COB:            02  TRTYPD7L    COMP  PIC  S9(4).
    public NInt16 trtypd7l = new NInt16();
    // COB:            02  TRTYPD7F    PICTURE X.
    public NChar trtypd7f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTYPD7F.
    public static class Filler195 extends NGroup {
      // COB:              03 TRTYPD7A    PICTURE X.
      public NChar trtypd7a = new NChar(1);
    }

    public Filler195 filler195 = (Filler195) new Filler195().redefines(trtypd7f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler197 = new NChar(4);
    // COB:            02  TRTYPD7I  PIC X(50).
    public NChar trtypd7i = new NChar(50);
    // COB:            02  TRTSELAL    COMP  PIC  S9(4).
    public NInt16 trtselal = new NInt16();
    // COB:            02  TRTSELAF    PICTURE X.
    public NChar trtselaf = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTSELAF.
    public static class Filler201 extends NGroup {
      // COB:              03 TRTSELAA    PICTURE X.
      public NChar trtselaa = new NChar(1);
    }

    public Filler201 filler201 = (Filler201) new Filler201().redefines(trtselaf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler203 = new NChar(4);
    // COB:            02  TRTSELAI  PIC X(1).
    public NChar trtselai = new NChar(1);
    // COB:            02  TRTTYPAL    COMP  PIC  S9(4).
    public NInt16 trttypal = new NInt16();
    // COB:            02  TRTTYPAF    PICTURE X.
    public NChar trttypaf = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTTYPAF.
    public static class Filler207 extends NGroup {
      // COB:              03 TRTTYPAA    PICTURE X.
      public NChar trttypaa = new NChar(1);
    }

    public Filler207 filler207 = (Filler207) new Filler207().redefines(trttypaf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler209 = new NChar(4);
    // COB:            02  TRTTYPAI  PIC X(2).
    public NChar trttypai = new NChar(2);
    // COB:            02  TRTDSCAL    COMP  PIC  S9(4).
    public NInt16 trtdscal = new NInt16();
    // COB:            02  TRTDSCAF    PICTURE X.
    public NChar trtdscaf = new NChar(1);

    // COB:            02  FILLER REDEFINES TRTDSCAF.
    public static class Filler213 extends NGroup {
      // COB:              03 TRTDSCAA    PICTURE X.
      public NChar trtdscaa = new NChar(1);
    }

    public Filler213 filler213 = (Filler213) new Filler213().redefines(trtdscaf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler215 = new NChar(4);
    // COB:            02  TRTDSCAI  PIC X(50).
    public NChar trtdscai = new NChar(50);
    // COB:            02  INFOMSGL    COMP  PIC  S9(4).
    public NInt16 infomsgl = new NInt16();
    // COB:            02  INFOMSGF    PICTURE X.
    public NChar infomsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES INFOMSGF.
    public static class Filler219 extends NGroup {
      // COB:              03 INFOMSGA    PICTURE X.
      public NChar infomsga = new NChar(1);
    }

    public Filler219 filler219 = (Filler219) new Filler219().redefines(infomsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler221 = new NChar(4);
    // COB:            02  INFOMSGI  PIC X(45).
    public NChar infomsgi = new NChar(45);
    // COB:            02  ERRMSGL    COMP  PIC  S9(4).
    public NInt16 errmsgl = new NInt16();
    // COB:            02  ERRMSGF    PICTURE X.
    public NChar errmsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES ERRMSGF.
    public static class Filler225 extends NGroup {
      // COB:              03 ERRMSGA    PICTURE X.
      public NChar errmsga = new NChar(1);
    }

    public Filler225 filler225 = (Filler225) new Filler225().redefines(errmsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler227 = new NChar(4);
    // COB:            02  ERRMSGI  PIC X(78).
    public NChar errmsgi = new NChar(78);
    // COB:            02  BUTNF02L    COMP  PIC  S9(4).
    public NInt16 butnf02l = new NInt16();
    // COB:            02  BUTNF02F    PICTURE X.
    public NChar butnf02f = new NChar(1);

    // COB:            02  FILLER REDEFINES BUTNF02F.
    public static class Filler231 extends NGroup {
      // COB:              03 BUTNF02A    PICTURE X.
      public NChar butnf02a = new NChar(1);
    }

    public Filler231 filler231 = (Filler231) new Filler231().redefines(butnf02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler233 = new NChar(4);
    // COB:            02  BUTNF02I  PIC X(7).
    public NChar butnf02i = new NChar(7);
    // COB:            02  BUTNF03L    COMP  PIC  S9(4).
    public NInt16 butnf03l = new NInt16();
    // COB:            02  BUTNF03F    PICTURE X.
    public NChar butnf03f = new NChar(1);

    // COB:            02  FILLER REDEFINES BUTNF03F.
    public static class Filler237 extends NGroup {
      // COB:              03 BUTNF03A    PICTURE X.
      public NChar butnf03a = new NChar(1);
    }

    public Filler237 filler237 = (Filler237) new Filler237().redefines(butnf03f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler239 = new NChar(4);
    // COB:            02  BUTNF03I  PIC X(7).
    public NChar butnf03i = new NChar(7);
    // COB:            02  BUTNF07L    COMP  PIC  S9(4).
    public NInt16 butnf07l = new NInt16();
    // COB:            02  BUTNF07F    PICTURE X.
    public NChar butnf07f = new NChar(1);

    // COB:            02  FILLER REDEFINES BUTNF07F.
    public static class Filler243 extends NGroup {
      // COB:              03 BUTNF07A    PICTURE X.
      public NChar butnf07a = new NChar(1);
    }

    public Filler243 filler243 = (Filler243) new Filler243().redefines(butnf07f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler245 = new NChar(4);
    // COB:            02  BUTNF07I  PIC X(10).
    public NChar butnf07i = new NChar(10);
    // COB:            02  BUTNF08L    COMP  PIC  S9(4).
    public NInt16 butnf08l = new NInt16();
    // COB:            02  BUTNF08F    PICTURE X.
    public NChar butnf08f = new NChar(1);

    // COB:            02  FILLER REDEFINES BUTNF08F.
    public static class Filler249 extends NGroup {
      // COB:              03 BUTNF08A    PICTURE X.
      public NChar butnf08a = new NChar(1);
    }

    public Filler249 filler249 = (Filler249) new Filler249().redefines(butnf08f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler251 = new NChar(4);
    // COB:            02  BUTNF08I  PIC X(10).
    public NChar butnf08i = new NChar(10);
    // COB:            02  BUTNF10L    COMP  PIC  S9(4).
    public NInt16 butnf10l = new NInt16();
    // COB:            02  BUTNF10F    PICTURE X.
    public NChar butnf10f = new NChar(1);

    // COB:            02  FILLER REDEFINES BUTNF10F.
    public static class Filler255 extends NGroup {
      // COB:              03 BUTNF10A    PICTURE X.
      public NChar butnf10a = new NChar(1);
    }

    public Filler255 filler255 = (Filler255) new Filler255().redefines(butnf10f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler257 = new NChar(4);
    // COB:            02  BUTNF10I  PIC X(8).
    public NChar butnf10i = new NChar(8);
  }

  public Ctrtliai ctrtliai = new Ctrtliai();

  // COB:        01  CTRTLIAO REDEFINES CTRTLIAI.
  public static class Ctrtliao extends NGroup {
    // COB:            02  FILLER PIC X(12).
    public NChar filler260 = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler261 = new NChar(3);
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
    public NChar filler267 = new NChar(3);
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
    public NChar filler273 = new NChar(3);
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
    public NChar filler279 = new NChar(3);
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
    public NChar filler285 = new NChar(3);
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
    public NChar filler291 = new NChar(3);
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
    public NChar filler297 = new NChar(3);
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
    public NChar filler303 = new NChar(3);
    // COB:            02  TRTYPEC    PICTURE X.
    public NChar trtypec = new NChar(1);
    // COB:            02  TRTYPEP    PICTURE X.
    public NChar trtypep = new NChar(1);
    // COB:            02  TRTYPEH    PICTURE X.
    public NChar trtypeh = new NChar(1);
    // COB:            02  TRTYPEV    PICTURE X.
    public NChar trtypev = new NChar(1);
    // COB:            02  TRTYPEO  PIC X(2).
    public NChar trtypeo = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler309 = new NChar(3);
    // COB:            02  TRDESCC    PICTURE X.
    public NChar trdescc = new NChar(1);
    // COB:            02  TRDESCP    PICTURE X.
    public NChar trdescp = new NChar(1);
    // COB:            02  TRDESCH    PICTURE X.
    public NChar trdesch = new NChar(1);
    // COB:            02  TRDESCV    PICTURE X.
    public NChar trdescv = new NChar(1);
    // COB:            02  TRDESCO  PIC X(50).
    public NChar trdesco = new NChar(50);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler315 = new NChar(3);
    // COB:            02  TRTSEL1C    PICTURE X.
    public NChar trtsel1c = new NChar(1);
    // COB:            02  TRTSEL1P    PICTURE X.
    public NChar trtsel1p = new NChar(1);
    // COB:            02  TRTSEL1H    PICTURE X.
    public NChar trtsel1h = new NChar(1);
    // COB:            02  TRTSEL1V    PICTURE X.
    public NChar trtsel1v = new NChar(1);
    // COB:            02  TRTSEL1O  PIC X(1).
    public NChar trtsel1o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler321 = new NChar(3);
    // COB:            02  TRTTYP1C    PICTURE X.
    public NChar trttyp1c = new NChar(1);
    // COB:            02  TRTTYP1P    PICTURE X.
    public NChar trttyp1p = new NChar(1);
    // COB:            02  TRTTYP1H    PICTURE X.
    public NChar trttyp1h = new NChar(1);
    // COB:            02  TRTTYP1V    PICTURE X.
    public NChar trttyp1v = new NChar(1);
    // COB:            02  TRTTYP1O  PIC X(2).
    public NChar trttyp1o = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler327 = new NChar(3);
    // COB:            02  TRTYPD1C    PICTURE X.
    public NChar trtypd1c = new NChar(1);
    // COB:            02  TRTYPD1P    PICTURE X.
    public NChar trtypd1p = new NChar(1);
    // COB:            02  TRTYPD1H    PICTURE X.
    public NChar trtypd1h = new NChar(1);
    // COB:            02  TRTYPD1V    PICTURE X.
    public NChar trtypd1v = new NChar(1);
    // COB:            02  TRTYPD1O  PIC X(50).
    public NChar trtypd1o = new NChar(50);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler333 = new NChar(3);
    // COB:            02  TRTSEL2C    PICTURE X.
    public NChar trtsel2c = new NChar(1);
    // COB:            02  TRTSEL2P    PICTURE X.
    public NChar trtsel2p = new NChar(1);
    // COB:            02  TRTSEL2H    PICTURE X.
    public NChar trtsel2h = new NChar(1);
    // COB:            02  TRTSEL2V    PICTURE X.
    public NChar trtsel2v = new NChar(1);
    // COB:            02  TRTSEL2O  PIC X(1).
    public NChar trtsel2o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler339 = new NChar(3);
    // COB:            02  TRTTYP2C    PICTURE X.
    public NChar trttyp2c = new NChar(1);
    // COB:            02  TRTTYP2P    PICTURE X.
    public NChar trttyp2p = new NChar(1);
    // COB:            02  TRTTYP2H    PICTURE X.
    public NChar trttyp2h = new NChar(1);
    // COB:            02  TRTTYP2V    PICTURE X.
    public NChar trttyp2v = new NChar(1);
    // COB:            02  TRTTYP2O  PIC X(2).
    public NChar trttyp2o = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler345 = new NChar(3);
    // COB:            02  TRTYPD2C    PICTURE X.
    public NChar trtypd2c = new NChar(1);
    // COB:            02  TRTYPD2P    PICTURE X.
    public NChar trtypd2p = new NChar(1);
    // COB:            02  TRTYPD2H    PICTURE X.
    public NChar trtypd2h = new NChar(1);
    // COB:            02  TRTYPD2V    PICTURE X.
    public NChar trtypd2v = new NChar(1);
    // COB:            02  TRTYPD2O  PIC X(50).
    public NChar trtypd2o = new NChar(50);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler351 = new NChar(3);
    // COB:            02  TRTSEL3C    PICTURE X.
    public NChar trtsel3c = new NChar(1);
    // COB:            02  TRTSEL3P    PICTURE X.
    public NChar trtsel3p = new NChar(1);
    // COB:            02  TRTSEL3H    PICTURE X.
    public NChar trtsel3h = new NChar(1);
    // COB:            02  TRTSEL3V    PICTURE X.
    public NChar trtsel3v = new NChar(1);
    // COB:            02  TRTSEL3O  PIC X(1).
    public NChar trtsel3o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler357 = new NChar(3);
    // COB:            02  TRTTYP3C    PICTURE X.
    public NChar trttyp3c = new NChar(1);
    // COB:            02  TRTTYP3P    PICTURE X.
    public NChar trttyp3p = new NChar(1);
    // COB:            02  TRTTYP3H    PICTURE X.
    public NChar trttyp3h = new NChar(1);
    // COB:            02  TRTTYP3V    PICTURE X.
    public NChar trttyp3v = new NChar(1);
    // COB:            02  TRTTYP3O  PIC X(2).
    public NChar trttyp3o = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler363 = new NChar(3);
    // COB:            02  TRTYPD3C    PICTURE X.
    public NChar trtypd3c = new NChar(1);
    // COB:            02  TRTYPD3P    PICTURE X.
    public NChar trtypd3p = new NChar(1);
    // COB:            02  TRTYPD3H    PICTURE X.
    public NChar trtypd3h = new NChar(1);
    // COB:            02  TRTYPD3V    PICTURE X.
    public NChar trtypd3v = new NChar(1);
    // COB:            02  TRTYPD3O  PIC X(50).
    public NChar trtypd3o = new NChar(50);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler369 = new NChar(3);
    // COB:            02  TRTSEL4C    PICTURE X.
    public NChar trtsel4c = new NChar(1);
    // COB:            02  TRTSEL4P    PICTURE X.
    public NChar trtsel4p = new NChar(1);
    // COB:            02  TRTSEL4H    PICTURE X.
    public NChar trtsel4h = new NChar(1);
    // COB:            02  TRTSEL4V    PICTURE X.
    public NChar trtsel4v = new NChar(1);
    // COB:            02  TRTSEL4O  PIC X(1).
    public NChar trtsel4o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler375 = new NChar(3);
    // COB:            02  TRTTYP4C    PICTURE X.
    public NChar trttyp4c = new NChar(1);
    // COB:            02  TRTTYP4P    PICTURE X.
    public NChar trttyp4p = new NChar(1);
    // COB:            02  TRTTYP4H    PICTURE X.
    public NChar trttyp4h = new NChar(1);
    // COB:            02  TRTTYP4V    PICTURE X.
    public NChar trttyp4v = new NChar(1);
    // COB:            02  TRTTYP4O  PIC X(2).
    public NChar trttyp4o = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler381 = new NChar(3);
    // COB:            02  TRTYPD4C    PICTURE X.
    public NChar trtypd4c = new NChar(1);
    // COB:            02  TRTYPD4P    PICTURE X.
    public NChar trtypd4p = new NChar(1);
    // COB:            02  TRTYPD4H    PICTURE X.
    public NChar trtypd4h = new NChar(1);
    // COB:            02  TRTYPD4V    PICTURE X.
    public NChar trtypd4v = new NChar(1);
    // COB:            02  TRTYPD4O  PIC X(50).
    public NChar trtypd4o = new NChar(50);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler387 = new NChar(3);
    // COB:            02  TRTSEL5C    PICTURE X.
    public NChar trtsel5c = new NChar(1);
    // COB:            02  TRTSEL5P    PICTURE X.
    public NChar trtsel5p = new NChar(1);
    // COB:            02  TRTSEL5H    PICTURE X.
    public NChar trtsel5h = new NChar(1);
    // COB:            02  TRTSEL5V    PICTURE X.
    public NChar trtsel5v = new NChar(1);
    // COB:            02  TRTSEL5O  PIC X(1).
    public NChar trtsel5o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler393 = new NChar(3);
    // COB:            02  TRTTYP5C    PICTURE X.
    public NChar trttyp5c = new NChar(1);
    // COB:            02  TRTTYP5P    PICTURE X.
    public NChar trttyp5p = new NChar(1);
    // COB:            02  TRTTYP5H    PICTURE X.
    public NChar trttyp5h = new NChar(1);
    // COB:            02  TRTTYP5V    PICTURE X.
    public NChar trttyp5v = new NChar(1);
    // COB:            02  TRTTYP5O  PIC X(2).
    public NChar trttyp5o = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler399 = new NChar(3);
    // COB:            02  TRTYPD5C    PICTURE X.
    public NChar trtypd5c = new NChar(1);
    // COB:            02  TRTYPD5P    PICTURE X.
    public NChar trtypd5p = new NChar(1);
    // COB:            02  TRTYPD5H    PICTURE X.
    public NChar trtypd5h = new NChar(1);
    // COB:            02  TRTYPD5V    PICTURE X.
    public NChar trtypd5v = new NChar(1);
    // COB:            02  TRTYPD5O  PIC X(50).
    public NChar trtypd5o = new NChar(50);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler405 = new NChar(3);
    // COB:            02  TRTSEL6C    PICTURE X.
    public NChar trtsel6c = new NChar(1);
    // COB:            02  TRTSEL6P    PICTURE X.
    public NChar trtsel6p = new NChar(1);
    // COB:            02  TRTSEL6H    PICTURE X.
    public NChar trtsel6h = new NChar(1);
    // COB:            02  TRTSEL6V    PICTURE X.
    public NChar trtsel6v = new NChar(1);
    // COB:            02  TRTSEL6O  PIC X(1).
    public NChar trtsel6o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler411 = new NChar(3);
    // COB:            02  TRTTYP6C    PICTURE X.
    public NChar trttyp6c = new NChar(1);
    // COB:            02  TRTTYP6P    PICTURE X.
    public NChar trttyp6p = new NChar(1);
    // COB:            02  TRTTYP6H    PICTURE X.
    public NChar trttyp6h = new NChar(1);
    // COB:            02  TRTTYP6V    PICTURE X.
    public NChar trttyp6v = new NChar(1);
    // COB:            02  TRTTYP6O  PIC X(2).
    public NChar trttyp6o = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler417 = new NChar(3);
    // COB:            02  TRTYPD6C    PICTURE X.
    public NChar trtypd6c = new NChar(1);
    // COB:            02  TRTYPD6P    PICTURE X.
    public NChar trtypd6p = new NChar(1);
    // COB:            02  TRTYPD6H    PICTURE X.
    public NChar trtypd6h = new NChar(1);
    // COB:            02  TRTYPD6V    PICTURE X.
    public NChar trtypd6v = new NChar(1);
    // COB:            02  TRTYPD6O  PIC X(50).
    public NChar trtypd6o = new NChar(50);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler423 = new NChar(3);
    // COB:            02  TRTSEL7C    PICTURE X.
    public NChar trtsel7c = new NChar(1);
    // COB:            02  TRTSEL7P    PICTURE X.
    public NChar trtsel7p = new NChar(1);
    // COB:            02  TRTSEL7H    PICTURE X.
    public NChar trtsel7h = new NChar(1);
    // COB:            02  TRTSEL7V    PICTURE X.
    public NChar trtsel7v = new NChar(1);
    // COB:            02  TRTSEL7O  PIC X(1).
    public NChar trtsel7o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler429 = new NChar(3);
    // COB:            02  TRTTYP7C    PICTURE X.
    public NChar trttyp7c = new NChar(1);
    // COB:            02  TRTTYP7P    PICTURE X.
    public NChar trttyp7p = new NChar(1);
    // COB:            02  TRTTYP7H    PICTURE X.
    public NChar trttyp7h = new NChar(1);
    // COB:            02  TRTTYP7V    PICTURE X.
    public NChar trttyp7v = new NChar(1);
    // COB:            02  TRTTYP7O  PIC X(2).
    public NChar trttyp7o = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler435 = new NChar(3);
    // COB:            02  TRTYPD7C    PICTURE X.
    public NChar trtypd7c = new NChar(1);
    // COB:            02  TRTYPD7P    PICTURE X.
    public NChar trtypd7p = new NChar(1);
    // COB:            02  TRTYPD7H    PICTURE X.
    public NChar trtypd7h = new NChar(1);
    // COB:            02  TRTYPD7V    PICTURE X.
    public NChar trtypd7v = new NChar(1);
    // COB:            02  TRTYPD7O  PIC X(50).
    public NChar trtypd7o = new NChar(50);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler441 = new NChar(3);
    // COB:            02  TRTSELAC    PICTURE X.
    public NChar trtselac = new NChar(1);
    // COB:            02  TRTSELAP    PICTURE X.
    public NChar trtselap = new NChar(1);
    // COB:            02  TRTSELAH    PICTURE X.
    public NChar trtselah = new NChar(1);
    // COB:            02  TRTSELAV    PICTURE X.
    public NChar trtselav = new NChar(1);
    // COB:            02  TRTSELAO  PIC X(1).
    public NChar trtselao = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler447 = new NChar(3);
    // COB:            02  TRTTYPAC    PICTURE X.
    public NChar trttypac = new NChar(1);
    // COB:            02  TRTTYPAP    PICTURE X.
    public NChar trttypap = new NChar(1);
    // COB:            02  TRTTYPAH    PICTURE X.
    public NChar trttypah = new NChar(1);
    // COB:            02  TRTTYPAV    PICTURE X.
    public NChar trttypav = new NChar(1);
    // COB:            02  TRTTYPAO  PIC X(2).
    public NChar trttypao = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler453 = new NChar(3);
    // COB:            02  TRTDSCAC    PICTURE X.
    public NChar trtdscac = new NChar(1);
    // COB:            02  TRTDSCAP    PICTURE X.
    public NChar trtdscap = new NChar(1);
    // COB:            02  TRTDSCAH    PICTURE X.
    public NChar trtdscah = new NChar(1);
    // COB:            02  TRTDSCAV    PICTURE X.
    public NChar trtdscav = new NChar(1);
    // COB:            02  TRTDSCAO  PIC X(50).
    public NChar trtdscao = new NChar(50);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler459 = new NChar(3);
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
    public NChar filler465 = new NChar(3);
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
    public NChar filler471 = new NChar(3);
    // COB:            02  BUTNF02C    PICTURE X.
    public NChar butnf02c = new NChar(1);
    // COB:            02  BUTNF02P    PICTURE X.
    public NChar butnf02p = new NChar(1);
    // COB:            02  BUTNF02H    PICTURE X.
    public NChar butnf02h = new NChar(1);
    // COB:            02  BUTNF02V    PICTURE X.
    public NChar butnf02v = new NChar(1);
    // COB:            02  BUTNF02O  PIC X(7).
    public NChar butnf02o = new NChar(7);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler477 = new NChar(3);
    // COB:            02  BUTNF03C    PICTURE X.
    public NChar butnf03c = new NChar(1);
    // COB:            02  BUTNF03P    PICTURE X.
    public NChar butnf03p = new NChar(1);
    // COB:            02  BUTNF03H    PICTURE X.
    public NChar butnf03h = new NChar(1);
    // COB:            02  BUTNF03V    PICTURE X.
    public NChar butnf03v = new NChar(1);
    // COB:            02  BUTNF03O  PIC X(7).
    public NChar butnf03o = new NChar(7);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler483 = new NChar(3);
    // COB:            02  BUTNF07C    PICTURE X.
    public NChar butnf07c = new NChar(1);
    // COB:            02  BUTNF07P    PICTURE X.
    public NChar butnf07p = new NChar(1);
    // COB:            02  BUTNF07H    PICTURE X.
    public NChar butnf07h = new NChar(1);
    // COB:            02  BUTNF07V    PICTURE X.
    public NChar butnf07v = new NChar(1);
    // COB:            02  BUTNF07O  PIC X(10).
    public NChar butnf07o = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler489 = new NChar(3);
    // COB:            02  BUTNF08C    PICTURE X.
    public NChar butnf08c = new NChar(1);
    // COB:            02  BUTNF08P    PICTURE X.
    public NChar butnf08p = new NChar(1);
    // COB:            02  BUTNF08H    PICTURE X.
    public NChar butnf08h = new NChar(1);
    // COB:            02  BUTNF08V    PICTURE X.
    public NChar butnf08v = new NChar(1);
    // COB:            02  BUTNF08O  PIC X(10).
    public NChar butnf08o = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler495 = new NChar(3);
    // COB:            02  BUTNF10C    PICTURE X.
    public NChar butnf10c = new NChar(1);
    // COB:            02  BUTNF10P    PICTURE X.
    public NChar butnf10p = new NChar(1);
    // COB:            02  BUTNF10H    PICTURE X.
    public NChar butnf10h = new NChar(1);
    // COB:            02  BUTNF10V    PICTURE X.
    public NChar butnf10v = new NChar(1);
    // COB:            02  BUTNF10O  PIC X(8).
    public NChar butnf10o = new NChar(8);
  }

  public Ctrtliao ctrtliao = (Ctrtliao) new Ctrtliao().redefines(ctrtliai);
}
