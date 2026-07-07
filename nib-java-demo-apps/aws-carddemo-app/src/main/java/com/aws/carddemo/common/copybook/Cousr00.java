package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cousr00 extends NGroup {

  // COB:        01  COUSR0AI.
  public static class Cousr0ai extends NGroup {
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
    // COB:            02  PAGENUML    COMP  PIC  S9(4).
    public NInt16 pagenuml = new NInt16();
    // COB:            02  PAGENUMF    PICTURE X.
    public NChar pagenumf = new NChar(1);

    // COB:            02  FILLER REDEFINES PAGENUMF.
    public static class Filler57 extends NGroup {
      // COB:              03 PAGENUMA    PICTURE X.
      public NChar pagenuma = new NChar(1);
    }

    public Filler57 filler57 = (Filler57) new Filler57().redefines(pagenumf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler59 = new NChar(4);
    // COB:            02  PAGENUMI  PIC X(8).
    public NChar pagenumi = new NChar(8);
    // COB:            02  USRIDINL    COMP  PIC  S9(4).
    public NInt16 usridinl = new NInt16();
    // COB:            02  USRIDINF    PICTURE X.
    public NChar usridinf = new NChar(1);

    // COB:            02  FILLER REDEFINES USRIDINF.
    public static class Filler63 extends NGroup {
      // COB:              03 USRIDINA    PICTURE X.
      public NChar usridina = new NChar(1);
    }

    public Filler63 filler63 = (Filler63) new Filler63().redefines(usridinf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler65 = new NChar(4);
    // COB:            02  USRIDINI  PIC X(8).
    public NChar usridini = new NChar(8);
    // COB:            02  SEL0001L    COMP  PIC  S9(4).
    public NInt16 sel0001l = new NInt16();
    // COB:            02  SEL0001F    PICTURE X.
    public NChar sel0001f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0001F.
    public static class Filler69 extends NGroup {
      // COB:              03 SEL0001A    PICTURE X.
      public NChar sel0001a = new NChar(1);
    }

    public Filler69 filler69 = (Filler69) new Filler69().redefines(sel0001f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler71 = new NChar(4);
    // COB:            02  SEL0001I  PIC X(1).
    public NChar sel0001i = new NChar(1);
    // COB:            02  USRID01L    COMP  PIC  S9(4).
    public NInt16 usrid01l = new NInt16();
    // COB:            02  USRID01F    PICTURE X.
    public NChar usrid01f = new NChar(1);

    // COB:            02  FILLER REDEFINES USRID01F.
    public static class Filler75 extends NGroup {
      // COB:              03 USRID01A    PICTURE X.
      public NChar usrid01a = new NChar(1);
    }

    public Filler75 filler75 = (Filler75) new Filler75().redefines(usrid01f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler77 = new NChar(4);
    // COB:            02  USRID01I  PIC X(8).
    public NChar usrid01i = new NChar(8);
    // COB:            02  FNAME01L    COMP  PIC  S9(4).
    public NInt16 fname01l = new NInt16();
    // COB:            02  FNAME01F    PICTURE X.
    public NChar fname01f = new NChar(1);

    // COB:            02  FILLER REDEFINES FNAME01F.
    public static class Filler81 extends NGroup {
      // COB:              03 FNAME01A    PICTURE X.
      public NChar fname01a = new NChar(1);
    }

    public Filler81 filler81 = (Filler81) new Filler81().redefines(fname01f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler83 = new NChar(4);
    // COB:            02  FNAME01I  PIC X(20).
    public NChar fname01i = new NChar(20);
    // COB:            02  LNAME01L    COMP  PIC  S9(4).
    public NInt16 lname01l = new NInt16();
    // COB:            02  LNAME01F    PICTURE X.
    public NChar lname01f = new NChar(1);

    // COB:            02  FILLER REDEFINES LNAME01F.
    public static class Filler87 extends NGroup {
      // COB:              03 LNAME01A    PICTURE X.
      public NChar lname01a = new NChar(1);
    }

    public Filler87 filler87 = (Filler87) new Filler87().redefines(lname01f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler89 = new NChar(4);
    // COB:            02  LNAME01I  PIC X(20).
    public NChar lname01i = new NChar(20);
    // COB:            02  UTYPE01L    COMP  PIC  S9(4).
    public NInt16 utype01l = new NInt16();
    // COB:            02  UTYPE01F    PICTURE X.
    public NChar utype01f = new NChar(1);

    // COB:            02  FILLER REDEFINES UTYPE01F.
    public static class Filler93 extends NGroup {
      // COB:              03 UTYPE01A    PICTURE X.
      public NChar utype01a = new NChar(1);
    }

    public Filler93 filler93 = (Filler93) new Filler93().redefines(utype01f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler95 = new NChar(4);
    // COB:            02  UTYPE01I  PIC X(1).
    public NChar utype01i = new NChar(1);
    // COB:            02  SEL0002L    COMP  PIC  S9(4).
    public NInt16 sel0002l = new NInt16();
    // COB:            02  SEL0002F    PICTURE X.
    public NChar sel0002f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0002F.
    public static class Filler99 extends NGroup {
      // COB:              03 SEL0002A    PICTURE X.
      public NChar sel0002a = new NChar(1);
    }

    public Filler99 filler99 = (Filler99) new Filler99().redefines(sel0002f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler101 = new NChar(4);
    // COB:            02  SEL0002I  PIC X(1).
    public NChar sel0002i = new NChar(1);
    // COB:            02  USRID02L    COMP  PIC  S9(4).
    public NInt16 usrid02l = new NInt16();
    // COB:            02  USRID02F    PICTURE X.
    public NChar usrid02f = new NChar(1);

    // COB:            02  FILLER REDEFINES USRID02F.
    public static class Filler105 extends NGroup {
      // COB:              03 USRID02A    PICTURE X.
      public NChar usrid02a = new NChar(1);
    }

    public Filler105 filler105 = (Filler105) new Filler105().redefines(usrid02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler107 = new NChar(4);
    // COB:            02  USRID02I  PIC X(8).
    public NChar usrid02i = new NChar(8);
    // COB:            02  FNAME02L    COMP  PIC  S9(4).
    public NInt16 fname02l = new NInt16();
    // COB:            02  FNAME02F    PICTURE X.
    public NChar fname02f = new NChar(1);

    // COB:            02  FILLER REDEFINES FNAME02F.
    public static class Filler111 extends NGroup {
      // COB:              03 FNAME02A    PICTURE X.
      public NChar fname02a = new NChar(1);
    }

    public Filler111 filler111 = (Filler111) new Filler111().redefines(fname02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler113 = new NChar(4);
    // COB:            02  FNAME02I  PIC X(20).
    public NChar fname02i = new NChar(20);
    // COB:            02  LNAME02L    COMP  PIC  S9(4).
    public NInt16 lname02l = new NInt16();
    // COB:            02  LNAME02F    PICTURE X.
    public NChar lname02f = new NChar(1);

    // COB:            02  FILLER REDEFINES LNAME02F.
    public static class Filler117 extends NGroup {
      // COB:              03 LNAME02A    PICTURE X.
      public NChar lname02a = new NChar(1);
    }

    public Filler117 filler117 = (Filler117) new Filler117().redefines(lname02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler119 = new NChar(4);
    // COB:            02  LNAME02I  PIC X(20).
    public NChar lname02i = new NChar(20);
    // COB:            02  UTYPE02L    COMP  PIC  S9(4).
    public NInt16 utype02l = new NInt16();
    // COB:            02  UTYPE02F    PICTURE X.
    public NChar utype02f = new NChar(1);

    // COB:            02  FILLER REDEFINES UTYPE02F.
    public static class Filler123 extends NGroup {
      // COB:              03 UTYPE02A    PICTURE X.
      public NChar utype02a = new NChar(1);
    }

    public Filler123 filler123 = (Filler123) new Filler123().redefines(utype02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler125 = new NChar(4);
    // COB:            02  UTYPE02I  PIC X(1).
    public NChar utype02i = new NChar(1);
    // COB:            02  SEL0003L    COMP  PIC  S9(4).
    public NInt16 sel0003l = new NInt16();
    // COB:            02  SEL0003F    PICTURE X.
    public NChar sel0003f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0003F.
    public static class Filler129 extends NGroup {
      // COB:              03 SEL0003A    PICTURE X.
      public NChar sel0003a = new NChar(1);
    }

    public Filler129 filler129 = (Filler129) new Filler129().redefines(sel0003f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler131 = new NChar(4);
    // COB:            02  SEL0003I  PIC X(1).
    public NChar sel0003i = new NChar(1);
    // COB:            02  USRID03L    COMP  PIC  S9(4).
    public NInt16 usrid03l = new NInt16();
    // COB:            02  USRID03F    PICTURE X.
    public NChar usrid03f = new NChar(1);

    // COB:            02  FILLER REDEFINES USRID03F.
    public static class Filler135 extends NGroup {
      // COB:              03 USRID03A    PICTURE X.
      public NChar usrid03a = new NChar(1);
    }

    public Filler135 filler135 = (Filler135) new Filler135().redefines(usrid03f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler137 = new NChar(4);
    // COB:            02  USRID03I  PIC X(8).
    public NChar usrid03i = new NChar(8);
    // COB:            02  FNAME03L    COMP  PIC  S9(4).
    public NInt16 fname03l = new NInt16();
    // COB:            02  FNAME03F    PICTURE X.
    public NChar fname03f = new NChar(1);

    // COB:            02  FILLER REDEFINES FNAME03F.
    public static class Filler141 extends NGroup {
      // COB:              03 FNAME03A    PICTURE X.
      public NChar fname03a = new NChar(1);
    }

    public Filler141 filler141 = (Filler141) new Filler141().redefines(fname03f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler143 = new NChar(4);
    // COB:            02  FNAME03I  PIC X(20).
    public NChar fname03i = new NChar(20);
    // COB:            02  LNAME03L    COMP  PIC  S9(4).
    public NInt16 lname03l = new NInt16();
    // COB:            02  LNAME03F    PICTURE X.
    public NChar lname03f = new NChar(1);

    // COB:            02  FILLER REDEFINES LNAME03F.
    public static class Filler147 extends NGroup {
      // COB:              03 LNAME03A    PICTURE X.
      public NChar lname03a = new NChar(1);
    }

    public Filler147 filler147 = (Filler147) new Filler147().redefines(lname03f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler149 = new NChar(4);
    // COB:            02  LNAME03I  PIC X(20).
    public NChar lname03i = new NChar(20);
    // COB:            02  UTYPE03L    COMP  PIC  S9(4).
    public NInt16 utype03l = new NInt16();
    // COB:            02  UTYPE03F    PICTURE X.
    public NChar utype03f = new NChar(1);

    // COB:            02  FILLER REDEFINES UTYPE03F.
    public static class Filler153 extends NGroup {
      // COB:              03 UTYPE03A    PICTURE X.
      public NChar utype03a = new NChar(1);
    }

    public Filler153 filler153 = (Filler153) new Filler153().redefines(utype03f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler155 = new NChar(4);
    // COB:            02  UTYPE03I  PIC X(1).
    public NChar utype03i = new NChar(1);
    // COB:            02  SEL0004L    COMP  PIC  S9(4).
    public NInt16 sel0004l = new NInt16();
    // COB:            02  SEL0004F    PICTURE X.
    public NChar sel0004f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0004F.
    public static class Filler159 extends NGroup {
      // COB:              03 SEL0004A    PICTURE X.
      public NChar sel0004a = new NChar(1);
    }

    public Filler159 filler159 = (Filler159) new Filler159().redefines(sel0004f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler161 = new NChar(4);
    // COB:            02  SEL0004I  PIC X(1).
    public NChar sel0004i = new NChar(1);
    // COB:            02  USRID04L    COMP  PIC  S9(4).
    public NInt16 usrid04l = new NInt16();
    // COB:            02  USRID04F    PICTURE X.
    public NChar usrid04f = new NChar(1);

    // COB:            02  FILLER REDEFINES USRID04F.
    public static class Filler165 extends NGroup {
      // COB:              03 USRID04A    PICTURE X.
      public NChar usrid04a = new NChar(1);
    }

    public Filler165 filler165 = (Filler165) new Filler165().redefines(usrid04f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler167 = new NChar(4);
    // COB:            02  USRID04I  PIC X(8).
    public NChar usrid04i = new NChar(8);
    // COB:            02  FNAME04L    COMP  PIC  S9(4).
    public NInt16 fname04l = new NInt16();
    // COB:            02  FNAME04F    PICTURE X.
    public NChar fname04f = new NChar(1);

    // COB:            02  FILLER REDEFINES FNAME04F.
    public static class Filler171 extends NGroup {
      // COB:              03 FNAME04A    PICTURE X.
      public NChar fname04a = new NChar(1);
    }

    public Filler171 filler171 = (Filler171) new Filler171().redefines(fname04f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler173 = new NChar(4);
    // COB:            02  FNAME04I  PIC X(20).
    public NChar fname04i = new NChar(20);
    // COB:            02  LNAME04L    COMP  PIC  S9(4).
    public NInt16 lname04l = new NInt16();
    // COB:            02  LNAME04F    PICTURE X.
    public NChar lname04f = new NChar(1);

    // COB:            02  FILLER REDEFINES LNAME04F.
    public static class Filler177 extends NGroup {
      // COB:              03 LNAME04A    PICTURE X.
      public NChar lname04a = new NChar(1);
    }

    public Filler177 filler177 = (Filler177) new Filler177().redefines(lname04f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler179 = new NChar(4);
    // COB:            02  LNAME04I  PIC X(20).
    public NChar lname04i = new NChar(20);
    // COB:            02  UTYPE04L    COMP  PIC  S9(4).
    public NInt16 utype04l = new NInt16();
    // COB:            02  UTYPE04F    PICTURE X.
    public NChar utype04f = new NChar(1);

    // COB:            02  FILLER REDEFINES UTYPE04F.
    public static class Filler183 extends NGroup {
      // COB:              03 UTYPE04A    PICTURE X.
      public NChar utype04a = new NChar(1);
    }

    public Filler183 filler183 = (Filler183) new Filler183().redefines(utype04f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler185 = new NChar(4);
    // COB:            02  UTYPE04I  PIC X(1).
    public NChar utype04i = new NChar(1);
    // COB:            02  SEL0005L    COMP  PIC  S9(4).
    public NInt16 sel0005l = new NInt16();
    // COB:            02  SEL0005F    PICTURE X.
    public NChar sel0005f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0005F.
    public static class Filler189 extends NGroup {
      // COB:              03 SEL0005A    PICTURE X.
      public NChar sel0005a = new NChar(1);
    }

    public Filler189 filler189 = (Filler189) new Filler189().redefines(sel0005f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler191 = new NChar(4);
    // COB:            02  SEL0005I  PIC X(1).
    public NChar sel0005i = new NChar(1);
    // COB:            02  USRID05L    COMP  PIC  S9(4).
    public NInt16 usrid05l = new NInt16();
    // COB:            02  USRID05F    PICTURE X.
    public NChar usrid05f = new NChar(1);

    // COB:            02  FILLER REDEFINES USRID05F.
    public static class Filler195 extends NGroup {
      // COB:              03 USRID05A    PICTURE X.
      public NChar usrid05a = new NChar(1);
    }

    public Filler195 filler195 = (Filler195) new Filler195().redefines(usrid05f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler197 = new NChar(4);
    // COB:            02  USRID05I  PIC X(8).
    public NChar usrid05i = new NChar(8);
    // COB:            02  FNAME05L    COMP  PIC  S9(4).
    public NInt16 fname05l = new NInt16();
    // COB:            02  FNAME05F    PICTURE X.
    public NChar fname05f = new NChar(1);

    // COB:            02  FILLER REDEFINES FNAME05F.
    public static class Filler201 extends NGroup {
      // COB:              03 FNAME05A    PICTURE X.
      public NChar fname05a = new NChar(1);
    }

    public Filler201 filler201 = (Filler201) new Filler201().redefines(fname05f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler203 = new NChar(4);
    // COB:            02  FNAME05I  PIC X(20).
    public NChar fname05i = new NChar(20);
    // COB:            02  LNAME05L    COMP  PIC  S9(4).
    public NInt16 lname05l = new NInt16();
    // COB:            02  LNAME05F    PICTURE X.
    public NChar lname05f = new NChar(1);

    // COB:            02  FILLER REDEFINES LNAME05F.
    public static class Filler207 extends NGroup {
      // COB:              03 LNAME05A    PICTURE X.
      public NChar lname05a = new NChar(1);
    }

    public Filler207 filler207 = (Filler207) new Filler207().redefines(lname05f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler209 = new NChar(4);
    // COB:            02  LNAME05I  PIC X(20).
    public NChar lname05i = new NChar(20);
    // COB:            02  UTYPE05L    COMP  PIC  S9(4).
    public NInt16 utype05l = new NInt16();
    // COB:            02  UTYPE05F    PICTURE X.
    public NChar utype05f = new NChar(1);

    // COB:            02  FILLER REDEFINES UTYPE05F.
    public static class Filler213 extends NGroup {
      // COB:              03 UTYPE05A    PICTURE X.
      public NChar utype05a = new NChar(1);
    }

    public Filler213 filler213 = (Filler213) new Filler213().redefines(utype05f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler215 = new NChar(4);
    // COB:            02  UTYPE05I  PIC X(1).
    public NChar utype05i = new NChar(1);
    // COB:            02  SEL0006L    COMP  PIC  S9(4).
    public NInt16 sel0006l = new NInt16();
    // COB:            02  SEL0006F    PICTURE X.
    public NChar sel0006f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0006F.
    public static class Filler219 extends NGroup {
      // COB:              03 SEL0006A    PICTURE X.
      public NChar sel0006a = new NChar(1);
    }

    public Filler219 filler219 = (Filler219) new Filler219().redefines(sel0006f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler221 = new NChar(4);
    // COB:            02  SEL0006I  PIC X(1).
    public NChar sel0006i = new NChar(1);
    // COB:            02  USRID06L    COMP  PIC  S9(4).
    public NInt16 usrid06l = new NInt16();
    // COB:            02  USRID06F    PICTURE X.
    public NChar usrid06f = new NChar(1);

    // COB:            02  FILLER REDEFINES USRID06F.
    public static class Filler225 extends NGroup {
      // COB:              03 USRID06A    PICTURE X.
      public NChar usrid06a = new NChar(1);
    }

    public Filler225 filler225 = (Filler225) new Filler225().redefines(usrid06f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler227 = new NChar(4);
    // COB:            02  USRID06I  PIC X(8).
    public NChar usrid06i = new NChar(8);
    // COB:            02  FNAME06L    COMP  PIC  S9(4).
    public NInt16 fname06l = new NInt16();
    // COB:            02  FNAME06F    PICTURE X.
    public NChar fname06f = new NChar(1);

    // COB:            02  FILLER REDEFINES FNAME06F.
    public static class Filler231 extends NGroup {
      // COB:              03 FNAME06A    PICTURE X.
      public NChar fname06a = new NChar(1);
    }

    public Filler231 filler231 = (Filler231) new Filler231().redefines(fname06f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler233 = new NChar(4);
    // COB:            02  FNAME06I  PIC X(20).
    public NChar fname06i = new NChar(20);
    // COB:            02  LNAME06L    COMP  PIC  S9(4).
    public NInt16 lname06l = new NInt16();
    // COB:            02  LNAME06F    PICTURE X.
    public NChar lname06f = new NChar(1);

    // COB:            02  FILLER REDEFINES LNAME06F.
    public static class Filler237 extends NGroup {
      // COB:              03 LNAME06A    PICTURE X.
      public NChar lname06a = new NChar(1);
    }

    public Filler237 filler237 = (Filler237) new Filler237().redefines(lname06f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler239 = new NChar(4);
    // COB:            02  LNAME06I  PIC X(20).
    public NChar lname06i = new NChar(20);
    // COB:            02  UTYPE06L    COMP  PIC  S9(4).
    public NInt16 utype06l = new NInt16();
    // COB:            02  UTYPE06F    PICTURE X.
    public NChar utype06f = new NChar(1);

    // COB:            02  FILLER REDEFINES UTYPE06F.
    public static class Filler243 extends NGroup {
      // COB:              03 UTYPE06A    PICTURE X.
      public NChar utype06a = new NChar(1);
    }

    public Filler243 filler243 = (Filler243) new Filler243().redefines(utype06f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler245 = new NChar(4);
    // COB:            02  UTYPE06I  PIC X(1).
    public NChar utype06i = new NChar(1);
    // COB:            02  SEL0007L    COMP  PIC  S9(4).
    public NInt16 sel0007l = new NInt16();
    // COB:            02  SEL0007F    PICTURE X.
    public NChar sel0007f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0007F.
    public static class Filler249 extends NGroup {
      // COB:              03 SEL0007A    PICTURE X.
      public NChar sel0007a = new NChar(1);
    }

    public Filler249 filler249 = (Filler249) new Filler249().redefines(sel0007f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler251 = new NChar(4);
    // COB:            02  SEL0007I  PIC X(1).
    public NChar sel0007i = new NChar(1);
    // COB:            02  USRID07L    COMP  PIC  S9(4).
    public NInt16 usrid07l = new NInt16();
    // COB:            02  USRID07F    PICTURE X.
    public NChar usrid07f = new NChar(1);

    // COB:            02  FILLER REDEFINES USRID07F.
    public static class Filler255 extends NGroup {
      // COB:              03 USRID07A    PICTURE X.
      public NChar usrid07a = new NChar(1);
    }

    public Filler255 filler255 = (Filler255) new Filler255().redefines(usrid07f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler257 = new NChar(4);
    // COB:            02  USRID07I  PIC X(8).
    public NChar usrid07i = new NChar(8);
    // COB:            02  FNAME07L    COMP  PIC  S9(4).
    public NInt16 fname07l = new NInt16();
    // COB:            02  FNAME07F    PICTURE X.
    public NChar fname07f = new NChar(1);

    // COB:            02  FILLER REDEFINES FNAME07F.
    public static class Filler261 extends NGroup {
      // COB:              03 FNAME07A    PICTURE X.
      public NChar fname07a = new NChar(1);
    }

    public Filler261 filler261 = (Filler261) new Filler261().redefines(fname07f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler263 = new NChar(4);
    // COB:            02  FNAME07I  PIC X(20).
    public NChar fname07i = new NChar(20);
    // COB:            02  LNAME07L    COMP  PIC  S9(4).
    public NInt16 lname07l = new NInt16();
    // COB:            02  LNAME07F    PICTURE X.
    public NChar lname07f = new NChar(1);

    // COB:            02  FILLER REDEFINES LNAME07F.
    public static class Filler267 extends NGroup {
      // COB:              03 LNAME07A    PICTURE X.
      public NChar lname07a = new NChar(1);
    }

    public Filler267 filler267 = (Filler267) new Filler267().redefines(lname07f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler269 = new NChar(4);
    // COB:            02  LNAME07I  PIC X(20).
    public NChar lname07i = new NChar(20);
    // COB:            02  UTYPE07L    COMP  PIC  S9(4).
    public NInt16 utype07l = new NInt16();
    // COB:            02  UTYPE07F    PICTURE X.
    public NChar utype07f = new NChar(1);

    // COB:            02  FILLER REDEFINES UTYPE07F.
    public static class Filler273 extends NGroup {
      // COB:              03 UTYPE07A    PICTURE X.
      public NChar utype07a = new NChar(1);
    }

    public Filler273 filler273 = (Filler273) new Filler273().redefines(utype07f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler275 = new NChar(4);
    // COB:            02  UTYPE07I  PIC X(1).
    public NChar utype07i = new NChar(1);
    // COB:            02  SEL0008L    COMP  PIC  S9(4).
    public NInt16 sel0008l = new NInt16();
    // COB:            02  SEL0008F    PICTURE X.
    public NChar sel0008f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0008F.
    public static class Filler279 extends NGroup {
      // COB:              03 SEL0008A    PICTURE X.
      public NChar sel0008a = new NChar(1);
    }

    public Filler279 filler279 = (Filler279) new Filler279().redefines(sel0008f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler281 = new NChar(4);
    // COB:            02  SEL0008I  PIC X(1).
    public NChar sel0008i = new NChar(1);
    // COB:            02  USRID08L    COMP  PIC  S9(4).
    public NInt16 usrid08l = new NInt16();
    // COB:            02  USRID08F    PICTURE X.
    public NChar usrid08f = new NChar(1);

    // COB:            02  FILLER REDEFINES USRID08F.
    public static class Filler285 extends NGroup {
      // COB:              03 USRID08A    PICTURE X.
      public NChar usrid08a = new NChar(1);
    }

    public Filler285 filler285 = (Filler285) new Filler285().redefines(usrid08f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler287 = new NChar(4);
    // COB:            02  USRID08I  PIC X(8).
    public NChar usrid08i = new NChar(8);
    // COB:            02  FNAME08L    COMP  PIC  S9(4).
    public NInt16 fname08l = new NInt16();
    // COB:            02  FNAME08F    PICTURE X.
    public NChar fname08f = new NChar(1);

    // COB:            02  FILLER REDEFINES FNAME08F.
    public static class Filler291 extends NGroup {
      // COB:              03 FNAME08A    PICTURE X.
      public NChar fname08a = new NChar(1);
    }

    public Filler291 filler291 = (Filler291) new Filler291().redefines(fname08f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler293 = new NChar(4);
    // COB:            02  FNAME08I  PIC X(20).
    public NChar fname08i = new NChar(20);
    // COB:            02  LNAME08L    COMP  PIC  S9(4).
    public NInt16 lname08l = new NInt16();
    // COB:            02  LNAME08F    PICTURE X.
    public NChar lname08f = new NChar(1);

    // COB:            02  FILLER REDEFINES LNAME08F.
    public static class Filler297 extends NGroup {
      // COB:              03 LNAME08A    PICTURE X.
      public NChar lname08a = new NChar(1);
    }

    public Filler297 filler297 = (Filler297) new Filler297().redefines(lname08f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler299 = new NChar(4);
    // COB:            02  LNAME08I  PIC X(20).
    public NChar lname08i = new NChar(20);
    // COB:            02  UTYPE08L    COMP  PIC  S9(4).
    public NInt16 utype08l = new NInt16();
    // COB:            02  UTYPE08F    PICTURE X.
    public NChar utype08f = new NChar(1);

    // COB:            02  FILLER REDEFINES UTYPE08F.
    public static class Filler303 extends NGroup {
      // COB:              03 UTYPE08A    PICTURE X.
      public NChar utype08a = new NChar(1);
    }

    public Filler303 filler303 = (Filler303) new Filler303().redefines(utype08f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler305 = new NChar(4);
    // COB:            02  UTYPE08I  PIC X(1).
    public NChar utype08i = new NChar(1);
    // COB:            02  SEL0009L    COMP  PIC  S9(4).
    public NInt16 sel0009l = new NInt16();
    // COB:            02  SEL0009F    PICTURE X.
    public NChar sel0009f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0009F.
    public static class Filler309 extends NGroup {
      // COB:              03 SEL0009A    PICTURE X.
      public NChar sel0009a = new NChar(1);
    }

    public Filler309 filler309 = (Filler309) new Filler309().redefines(sel0009f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler311 = new NChar(4);
    // COB:            02  SEL0009I  PIC X(1).
    public NChar sel0009i = new NChar(1);
    // COB:            02  USRID09L    COMP  PIC  S9(4).
    public NInt16 usrid09l = new NInt16();
    // COB:            02  USRID09F    PICTURE X.
    public NChar usrid09f = new NChar(1);

    // COB:            02  FILLER REDEFINES USRID09F.
    public static class Filler315 extends NGroup {
      // COB:              03 USRID09A    PICTURE X.
      public NChar usrid09a = new NChar(1);
    }

    public Filler315 filler315 = (Filler315) new Filler315().redefines(usrid09f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler317 = new NChar(4);
    // COB:            02  USRID09I  PIC X(8).
    public NChar usrid09i = new NChar(8);
    // COB:            02  FNAME09L    COMP  PIC  S9(4).
    public NInt16 fname09l = new NInt16();
    // COB:            02  FNAME09F    PICTURE X.
    public NChar fname09f = new NChar(1);

    // COB:            02  FILLER REDEFINES FNAME09F.
    public static class Filler321 extends NGroup {
      // COB:              03 FNAME09A    PICTURE X.
      public NChar fname09a = new NChar(1);
    }

    public Filler321 filler321 = (Filler321) new Filler321().redefines(fname09f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler323 = new NChar(4);
    // COB:            02  FNAME09I  PIC X(20).
    public NChar fname09i = new NChar(20);
    // COB:            02  LNAME09L    COMP  PIC  S9(4).
    public NInt16 lname09l = new NInt16();
    // COB:            02  LNAME09F    PICTURE X.
    public NChar lname09f = new NChar(1);

    // COB:            02  FILLER REDEFINES LNAME09F.
    public static class Filler327 extends NGroup {
      // COB:              03 LNAME09A    PICTURE X.
      public NChar lname09a = new NChar(1);
    }

    public Filler327 filler327 = (Filler327) new Filler327().redefines(lname09f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler329 = new NChar(4);
    // COB:            02  LNAME09I  PIC X(20).
    public NChar lname09i = new NChar(20);
    // COB:            02  UTYPE09L    COMP  PIC  S9(4).
    public NInt16 utype09l = new NInt16();
    // COB:            02  UTYPE09F    PICTURE X.
    public NChar utype09f = new NChar(1);

    // COB:            02  FILLER REDEFINES UTYPE09F.
    public static class Filler333 extends NGroup {
      // COB:              03 UTYPE09A    PICTURE X.
      public NChar utype09a = new NChar(1);
    }

    public Filler333 filler333 = (Filler333) new Filler333().redefines(utype09f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler335 = new NChar(4);
    // COB:            02  UTYPE09I  PIC X(1).
    public NChar utype09i = new NChar(1);
    // COB:            02  SEL0010L    COMP  PIC  S9(4).
    public NInt16 sel0010l = new NInt16();
    // COB:            02  SEL0010F    PICTURE X.
    public NChar sel0010f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0010F.
    public static class Filler339 extends NGroup {
      // COB:              03 SEL0010A    PICTURE X.
      public NChar sel0010a = new NChar(1);
    }

    public Filler339 filler339 = (Filler339) new Filler339().redefines(sel0010f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler341 = new NChar(4);
    // COB:            02  SEL0010I  PIC X(1).
    public NChar sel0010i = new NChar(1);
    // COB:            02  USRID10L    COMP  PIC  S9(4).
    public NInt16 usrid10l = new NInt16();
    // COB:            02  USRID10F    PICTURE X.
    public NChar usrid10f = new NChar(1);

    // COB:            02  FILLER REDEFINES USRID10F.
    public static class Filler345 extends NGroup {
      // COB:              03 USRID10A    PICTURE X.
      public NChar usrid10a = new NChar(1);
    }

    public Filler345 filler345 = (Filler345) new Filler345().redefines(usrid10f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler347 = new NChar(4);
    // COB:            02  USRID10I  PIC X(8).
    public NChar usrid10i = new NChar(8);
    // COB:            02  FNAME10L    COMP  PIC  S9(4).
    public NInt16 fname10l = new NInt16();
    // COB:            02  FNAME10F    PICTURE X.
    public NChar fname10f = new NChar(1);

    // COB:            02  FILLER REDEFINES FNAME10F.
    public static class Filler351 extends NGroup {
      // COB:              03 FNAME10A    PICTURE X.
      public NChar fname10a = new NChar(1);
    }

    public Filler351 filler351 = (Filler351) new Filler351().redefines(fname10f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler353 = new NChar(4);
    // COB:            02  FNAME10I  PIC X(20).
    public NChar fname10i = new NChar(20);
    // COB:            02  LNAME10L    COMP  PIC  S9(4).
    public NInt16 lname10l = new NInt16();
    // COB:            02  LNAME10F    PICTURE X.
    public NChar lname10f = new NChar(1);

    // COB:            02  FILLER REDEFINES LNAME10F.
    public static class Filler357 extends NGroup {
      // COB:              03 LNAME10A    PICTURE X.
      public NChar lname10a = new NChar(1);
    }

    public Filler357 filler357 = (Filler357) new Filler357().redefines(lname10f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler359 = new NChar(4);
    // COB:            02  LNAME10I  PIC X(20).
    public NChar lname10i = new NChar(20);
    // COB:            02  UTYPE10L    COMP  PIC  S9(4).
    public NInt16 utype10l = new NInt16();
    // COB:            02  UTYPE10F    PICTURE X.
    public NChar utype10f = new NChar(1);

    // COB:            02  FILLER REDEFINES UTYPE10F.
    public static class Filler363 extends NGroup {
      // COB:              03 UTYPE10A    PICTURE X.
      public NChar utype10a = new NChar(1);
    }

    public Filler363 filler363 = (Filler363) new Filler363().redefines(utype10f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler365 = new NChar(4);
    // COB:            02  UTYPE10I  PIC X(1).
    public NChar utype10i = new NChar(1);
    // COB:            02  ERRMSGL    COMP  PIC  S9(4).
    public NInt16 errmsgl = new NInt16();
    // COB:            02  ERRMSGF    PICTURE X.
    public NChar errmsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES ERRMSGF.
    public static class Filler369 extends NGroup {
      // COB:              03 ERRMSGA    PICTURE X.
      public NChar errmsga = new NChar(1);
    }

    public Filler369 filler369 = (Filler369) new Filler369().redefines(errmsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler371 = new NChar(4);
    // COB:            02  ERRMSGI  PIC X(78).
    public NChar errmsgi = new NChar(78);
  }

  public Cousr0ai cousr0ai = new Cousr0ai();

  // COB:        01  COUSR0AO REDEFINES COUSR0AI.
  public static class Cousr0ao extends NGroup {
    // COB:            02  FILLER PIC X(12).
    public NChar filler374 = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler375 = new NChar(3);
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
    public NChar filler381 = new NChar(3);
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
    public NChar filler387 = new NChar(3);
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
    public NChar filler393 = new NChar(3);
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
    public NChar filler399 = new NChar(3);
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
    public NChar filler405 = new NChar(3);
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
    public NChar filler411 = new NChar(3);
    // COB:            02  PAGENUMC    PICTURE X.
    public NChar pagenumc = new NChar(1);
    // COB:            02  PAGENUMP    PICTURE X.
    public NChar pagenump = new NChar(1);
    // COB:            02  PAGENUMH    PICTURE X.
    public NChar pagenumh = new NChar(1);
    // COB:            02  PAGENUMV    PICTURE X.
    public NChar pagenumv = new NChar(1);
    // COB:            02  PAGENUMO  PIC X(8).
    public NChar pagenumo = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler417 = new NChar(3);
    // COB:            02  USRIDINC    PICTURE X.
    public NChar usridinc = new NChar(1);
    // COB:            02  USRIDINP    PICTURE X.
    public NChar usridinp = new NChar(1);
    // COB:            02  USRIDINH    PICTURE X.
    public NChar usridinh = new NChar(1);
    // COB:            02  USRIDINV    PICTURE X.
    public NChar usridinv = new NChar(1);
    // COB:            02  USRIDINO  PIC X(8).
    public NChar usridino = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler423 = new NChar(3);
    // COB:            02  SEL0001C    PICTURE X.
    public NChar sel0001c = new NChar(1);
    // COB:            02  SEL0001P    PICTURE X.
    public NChar sel0001p = new NChar(1);
    // COB:            02  SEL0001H    PICTURE X.
    public NChar sel0001h = new NChar(1);
    // COB:            02  SEL0001V    PICTURE X.
    public NChar sel0001v = new NChar(1);
    // COB:            02  SEL0001O  PIC X(1).
    public NChar sel0001o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler429 = new NChar(3);
    // COB:            02  USRID01C    PICTURE X.
    public NChar usrid01c = new NChar(1);
    // COB:            02  USRID01P    PICTURE X.
    public NChar usrid01p = new NChar(1);
    // COB:            02  USRID01H    PICTURE X.
    public NChar usrid01h = new NChar(1);
    // COB:            02  USRID01V    PICTURE X.
    public NChar usrid01v = new NChar(1);
    // COB:            02  USRID01O  PIC X(8).
    public NChar usrid01o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler435 = new NChar(3);
    // COB:            02  FNAME01C    PICTURE X.
    public NChar fname01c = new NChar(1);
    // COB:            02  FNAME01P    PICTURE X.
    public NChar fname01p = new NChar(1);
    // COB:            02  FNAME01H    PICTURE X.
    public NChar fname01h = new NChar(1);
    // COB:            02  FNAME01V    PICTURE X.
    public NChar fname01v = new NChar(1);
    // COB:            02  FNAME01O  PIC X(20).
    public NChar fname01o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler441 = new NChar(3);
    // COB:            02  LNAME01C    PICTURE X.
    public NChar lname01c = new NChar(1);
    // COB:            02  LNAME01P    PICTURE X.
    public NChar lname01p = new NChar(1);
    // COB:            02  LNAME01H    PICTURE X.
    public NChar lname01h = new NChar(1);
    // COB:            02  LNAME01V    PICTURE X.
    public NChar lname01v = new NChar(1);
    // COB:            02  LNAME01O  PIC X(20).
    public NChar lname01o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler447 = new NChar(3);
    // COB:            02  UTYPE01C    PICTURE X.
    public NChar utype01c = new NChar(1);
    // COB:            02  UTYPE01P    PICTURE X.
    public NChar utype01p = new NChar(1);
    // COB:            02  UTYPE01H    PICTURE X.
    public NChar utype01h = new NChar(1);
    // COB:            02  UTYPE01V    PICTURE X.
    public NChar utype01v = new NChar(1);
    // COB:            02  UTYPE01O  PIC X(1).
    public NChar utype01o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler453 = new NChar(3);
    // COB:            02  SEL0002C    PICTURE X.
    public NChar sel0002c = new NChar(1);
    // COB:            02  SEL0002P    PICTURE X.
    public NChar sel0002p = new NChar(1);
    // COB:            02  SEL0002H    PICTURE X.
    public NChar sel0002h = new NChar(1);
    // COB:            02  SEL0002V    PICTURE X.
    public NChar sel0002v = new NChar(1);
    // COB:            02  SEL0002O  PIC X(1).
    public NChar sel0002o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler459 = new NChar(3);
    // COB:            02  USRID02C    PICTURE X.
    public NChar usrid02c = new NChar(1);
    // COB:            02  USRID02P    PICTURE X.
    public NChar usrid02p = new NChar(1);
    // COB:            02  USRID02H    PICTURE X.
    public NChar usrid02h = new NChar(1);
    // COB:            02  USRID02V    PICTURE X.
    public NChar usrid02v = new NChar(1);
    // COB:            02  USRID02O  PIC X(8).
    public NChar usrid02o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler465 = new NChar(3);
    // COB:            02  FNAME02C    PICTURE X.
    public NChar fname02c = new NChar(1);
    // COB:            02  FNAME02P    PICTURE X.
    public NChar fname02p = new NChar(1);
    // COB:            02  FNAME02H    PICTURE X.
    public NChar fname02h = new NChar(1);
    // COB:            02  FNAME02V    PICTURE X.
    public NChar fname02v = new NChar(1);
    // COB:            02  FNAME02O  PIC X(20).
    public NChar fname02o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler471 = new NChar(3);
    // COB:            02  LNAME02C    PICTURE X.
    public NChar lname02c = new NChar(1);
    // COB:            02  LNAME02P    PICTURE X.
    public NChar lname02p = new NChar(1);
    // COB:            02  LNAME02H    PICTURE X.
    public NChar lname02h = new NChar(1);
    // COB:            02  LNAME02V    PICTURE X.
    public NChar lname02v = new NChar(1);
    // COB:            02  LNAME02O  PIC X(20).
    public NChar lname02o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler477 = new NChar(3);
    // COB:            02  UTYPE02C    PICTURE X.
    public NChar utype02c = new NChar(1);
    // COB:            02  UTYPE02P    PICTURE X.
    public NChar utype02p = new NChar(1);
    // COB:            02  UTYPE02H    PICTURE X.
    public NChar utype02h = new NChar(1);
    // COB:            02  UTYPE02V    PICTURE X.
    public NChar utype02v = new NChar(1);
    // COB:            02  UTYPE02O  PIC X(1).
    public NChar utype02o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler483 = new NChar(3);
    // COB:            02  SEL0003C    PICTURE X.
    public NChar sel0003c = new NChar(1);
    // COB:            02  SEL0003P    PICTURE X.
    public NChar sel0003p = new NChar(1);
    // COB:            02  SEL0003H    PICTURE X.
    public NChar sel0003h = new NChar(1);
    // COB:            02  SEL0003V    PICTURE X.
    public NChar sel0003v = new NChar(1);
    // COB:            02  SEL0003O  PIC X(1).
    public NChar sel0003o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler489 = new NChar(3);
    // COB:            02  USRID03C    PICTURE X.
    public NChar usrid03c = new NChar(1);
    // COB:            02  USRID03P    PICTURE X.
    public NChar usrid03p = new NChar(1);
    // COB:            02  USRID03H    PICTURE X.
    public NChar usrid03h = new NChar(1);
    // COB:            02  USRID03V    PICTURE X.
    public NChar usrid03v = new NChar(1);
    // COB:            02  USRID03O  PIC X(8).
    public NChar usrid03o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler495 = new NChar(3);
    // COB:            02  FNAME03C    PICTURE X.
    public NChar fname03c = new NChar(1);
    // COB:            02  FNAME03P    PICTURE X.
    public NChar fname03p = new NChar(1);
    // COB:            02  FNAME03H    PICTURE X.
    public NChar fname03h = new NChar(1);
    // COB:            02  FNAME03V    PICTURE X.
    public NChar fname03v = new NChar(1);
    // COB:            02  FNAME03O  PIC X(20).
    public NChar fname03o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler501 = new NChar(3);
    // COB:            02  LNAME03C    PICTURE X.
    public NChar lname03c = new NChar(1);
    // COB:            02  LNAME03P    PICTURE X.
    public NChar lname03p = new NChar(1);
    // COB:            02  LNAME03H    PICTURE X.
    public NChar lname03h = new NChar(1);
    // COB:            02  LNAME03V    PICTURE X.
    public NChar lname03v = new NChar(1);
    // COB:            02  LNAME03O  PIC X(20).
    public NChar lname03o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler507 = new NChar(3);
    // COB:            02  UTYPE03C    PICTURE X.
    public NChar utype03c = new NChar(1);
    // COB:            02  UTYPE03P    PICTURE X.
    public NChar utype03p = new NChar(1);
    // COB:            02  UTYPE03H    PICTURE X.
    public NChar utype03h = new NChar(1);
    // COB:            02  UTYPE03V    PICTURE X.
    public NChar utype03v = new NChar(1);
    // COB:            02  UTYPE03O  PIC X(1).
    public NChar utype03o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler513 = new NChar(3);
    // COB:            02  SEL0004C    PICTURE X.
    public NChar sel0004c = new NChar(1);
    // COB:            02  SEL0004P    PICTURE X.
    public NChar sel0004p = new NChar(1);
    // COB:            02  SEL0004H    PICTURE X.
    public NChar sel0004h = new NChar(1);
    // COB:            02  SEL0004V    PICTURE X.
    public NChar sel0004v = new NChar(1);
    // COB:            02  SEL0004O  PIC X(1).
    public NChar sel0004o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler519 = new NChar(3);
    // COB:            02  USRID04C    PICTURE X.
    public NChar usrid04c = new NChar(1);
    // COB:            02  USRID04P    PICTURE X.
    public NChar usrid04p = new NChar(1);
    // COB:            02  USRID04H    PICTURE X.
    public NChar usrid04h = new NChar(1);
    // COB:            02  USRID04V    PICTURE X.
    public NChar usrid04v = new NChar(1);
    // COB:            02  USRID04O  PIC X(8).
    public NChar usrid04o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler525 = new NChar(3);
    // COB:            02  FNAME04C    PICTURE X.
    public NChar fname04c = new NChar(1);
    // COB:            02  FNAME04P    PICTURE X.
    public NChar fname04p = new NChar(1);
    // COB:            02  FNAME04H    PICTURE X.
    public NChar fname04h = new NChar(1);
    // COB:            02  FNAME04V    PICTURE X.
    public NChar fname04v = new NChar(1);
    // COB:            02  FNAME04O  PIC X(20).
    public NChar fname04o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler531 = new NChar(3);
    // COB:            02  LNAME04C    PICTURE X.
    public NChar lname04c = new NChar(1);
    // COB:            02  LNAME04P    PICTURE X.
    public NChar lname04p = new NChar(1);
    // COB:            02  LNAME04H    PICTURE X.
    public NChar lname04h = new NChar(1);
    // COB:            02  LNAME04V    PICTURE X.
    public NChar lname04v = new NChar(1);
    // COB:            02  LNAME04O  PIC X(20).
    public NChar lname04o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler537 = new NChar(3);
    // COB:            02  UTYPE04C    PICTURE X.
    public NChar utype04c = new NChar(1);
    // COB:            02  UTYPE04P    PICTURE X.
    public NChar utype04p = new NChar(1);
    // COB:            02  UTYPE04H    PICTURE X.
    public NChar utype04h = new NChar(1);
    // COB:            02  UTYPE04V    PICTURE X.
    public NChar utype04v = new NChar(1);
    // COB:            02  UTYPE04O  PIC X(1).
    public NChar utype04o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler543 = new NChar(3);
    // COB:            02  SEL0005C    PICTURE X.
    public NChar sel0005c = new NChar(1);
    // COB:            02  SEL0005P    PICTURE X.
    public NChar sel0005p = new NChar(1);
    // COB:            02  SEL0005H    PICTURE X.
    public NChar sel0005h = new NChar(1);
    // COB:            02  SEL0005V    PICTURE X.
    public NChar sel0005v = new NChar(1);
    // COB:            02  SEL0005O  PIC X(1).
    public NChar sel0005o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler549 = new NChar(3);
    // COB:            02  USRID05C    PICTURE X.
    public NChar usrid05c = new NChar(1);
    // COB:            02  USRID05P    PICTURE X.
    public NChar usrid05p = new NChar(1);
    // COB:            02  USRID05H    PICTURE X.
    public NChar usrid05h = new NChar(1);
    // COB:            02  USRID05V    PICTURE X.
    public NChar usrid05v = new NChar(1);
    // COB:            02  USRID05O  PIC X(8).
    public NChar usrid05o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler555 = new NChar(3);
    // COB:            02  FNAME05C    PICTURE X.
    public NChar fname05c = new NChar(1);
    // COB:            02  FNAME05P    PICTURE X.
    public NChar fname05p = new NChar(1);
    // COB:            02  FNAME05H    PICTURE X.
    public NChar fname05h = new NChar(1);
    // COB:            02  FNAME05V    PICTURE X.
    public NChar fname05v = new NChar(1);
    // COB:            02  FNAME05O  PIC X(20).
    public NChar fname05o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler561 = new NChar(3);
    // COB:            02  LNAME05C    PICTURE X.
    public NChar lname05c = new NChar(1);
    // COB:            02  LNAME05P    PICTURE X.
    public NChar lname05p = new NChar(1);
    // COB:            02  LNAME05H    PICTURE X.
    public NChar lname05h = new NChar(1);
    // COB:            02  LNAME05V    PICTURE X.
    public NChar lname05v = new NChar(1);
    // COB:            02  LNAME05O  PIC X(20).
    public NChar lname05o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler567 = new NChar(3);
    // COB:            02  UTYPE05C    PICTURE X.
    public NChar utype05c = new NChar(1);
    // COB:            02  UTYPE05P    PICTURE X.
    public NChar utype05p = new NChar(1);
    // COB:            02  UTYPE05H    PICTURE X.
    public NChar utype05h = new NChar(1);
    // COB:            02  UTYPE05V    PICTURE X.
    public NChar utype05v = new NChar(1);
    // COB:            02  UTYPE05O  PIC X(1).
    public NChar utype05o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler573 = new NChar(3);
    // COB:            02  SEL0006C    PICTURE X.
    public NChar sel0006c = new NChar(1);
    // COB:            02  SEL0006P    PICTURE X.
    public NChar sel0006p = new NChar(1);
    // COB:            02  SEL0006H    PICTURE X.
    public NChar sel0006h = new NChar(1);
    // COB:            02  SEL0006V    PICTURE X.
    public NChar sel0006v = new NChar(1);
    // COB:            02  SEL0006O  PIC X(1).
    public NChar sel0006o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler579 = new NChar(3);
    // COB:            02  USRID06C    PICTURE X.
    public NChar usrid06c = new NChar(1);
    // COB:            02  USRID06P    PICTURE X.
    public NChar usrid06p = new NChar(1);
    // COB:            02  USRID06H    PICTURE X.
    public NChar usrid06h = new NChar(1);
    // COB:            02  USRID06V    PICTURE X.
    public NChar usrid06v = new NChar(1);
    // COB:            02  USRID06O  PIC X(8).
    public NChar usrid06o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler585 = new NChar(3);
    // COB:            02  FNAME06C    PICTURE X.
    public NChar fname06c = new NChar(1);
    // COB:            02  FNAME06P    PICTURE X.
    public NChar fname06p = new NChar(1);
    // COB:            02  FNAME06H    PICTURE X.
    public NChar fname06h = new NChar(1);
    // COB:            02  FNAME06V    PICTURE X.
    public NChar fname06v = new NChar(1);
    // COB:            02  FNAME06O  PIC X(20).
    public NChar fname06o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler591 = new NChar(3);
    // COB:            02  LNAME06C    PICTURE X.
    public NChar lname06c = new NChar(1);
    // COB:            02  LNAME06P    PICTURE X.
    public NChar lname06p = new NChar(1);
    // COB:            02  LNAME06H    PICTURE X.
    public NChar lname06h = new NChar(1);
    // COB:            02  LNAME06V    PICTURE X.
    public NChar lname06v = new NChar(1);
    // COB:            02  LNAME06O  PIC X(20).
    public NChar lname06o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler597 = new NChar(3);
    // COB:            02  UTYPE06C    PICTURE X.
    public NChar utype06c = new NChar(1);
    // COB:            02  UTYPE06P    PICTURE X.
    public NChar utype06p = new NChar(1);
    // COB:            02  UTYPE06H    PICTURE X.
    public NChar utype06h = new NChar(1);
    // COB:            02  UTYPE06V    PICTURE X.
    public NChar utype06v = new NChar(1);
    // COB:            02  UTYPE06O  PIC X(1).
    public NChar utype06o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler603 = new NChar(3);
    // COB:            02  SEL0007C    PICTURE X.
    public NChar sel0007c = new NChar(1);
    // COB:            02  SEL0007P    PICTURE X.
    public NChar sel0007p = new NChar(1);
    // COB:            02  SEL0007H    PICTURE X.
    public NChar sel0007h = new NChar(1);
    // COB:            02  SEL0007V    PICTURE X.
    public NChar sel0007v = new NChar(1);
    // COB:            02  SEL0007O  PIC X(1).
    public NChar sel0007o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler609 = new NChar(3);
    // COB:            02  USRID07C    PICTURE X.
    public NChar usrid07c = new NChar(1);
    // COB:            02  USRID07P    PICTURE X.
    public NChar usrid07p = new NChar(1);
    // COB:            02  USRID07H    PICTURE X.
    public NChar usrid07h = new NChar(1);
    // COB:            02  USRID07V    PICTURE X.
    public NChar usrid07v = new NChar(1);
    // COB:            02  USRID07O  PIC X(8).
    public NChar usrid07o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler615 = new NChar(3);
    // COB:            02  FNAME07C    PICTURE X.
    public NChar fname07c = new NChar(1);
    // COB:            02  FNAME07P    PICTURE X.
    public NChar fname07p = new NChar(1);
    // COB:            02  FNAME07H    PICTURE X.
    public NChar fname07h = new NChar(1);
    // COB:            02  FNAME07V    PICTURE X.
    public NChar fname07v = new NChar(1);
    // COB:            02  FNAME07O  PIC X(20).
    public NChar fname07o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler621 = new NChar(3);
    // COB:            02  LNAME07C    PICTURE X.
    public NChar lname07c = new NChar(1);
    // COB:            02  LNAME07P    PICTURE X.
    public NChar lname07p = new NChar(1);
    // COB:            02  LNAME07H    PICTURE X.
    public NChar lname07h = new NChar(1);
    // COB:            02  LNAME07V    PICTURE X.
    public NChar lname07v = new NChar(1);
    // COB:            02  LNAME07O  PIC X(20).
    public NChar lname07o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler627 = new NChar(3);
    // COB:            02  UTYPE07C    PICTURE X.
    public NChar utype07c = new NChar(1);
    // COB:            02  UTYPE07P    PICTURE X.
    public NChar utype07p = new NChar(1);
    // COB:            02  UTYPE07H    PICTURE X.
    public NChar utype07h = new NChar(1);
    // COB:            02  UTYPE07V    PICTURE X.
    public NChar utype07v = new NChar(1);
    // COB:            02  UTYPE07O  PIC X(1).
    public NChar utype07o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler633 = new NChar(3);
    // COB:            02  SEL0008C    PICTURE X.
    public NChar sel0008c = new NChar(1);
    // COB:            02  SEL0008P    PICTURE X.
    public NChar sel0008p = new NChar(1);
    // COB:            02  SEL0008H    PICTURE X.
    public NChar sel0008h = new NChar(1);
    // COB:            02  SEL0008V    PICTURE X.
    public NChar sel0008v = new NChar(1);
    // COB:            02  SEL0008O  PIC X(1).
    public NChar sel0008o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler639 = new NChar(3);
    // COB:            02  USRID08C    PICTURE X.
    public NChar usrid08c = new NChar(1);
    // COB:            02  USRID08P    PICTURE X.
    public NChar usrid08p = new NChar(1);
    // COB:            02  USRID08H    PICTURE X.
    public NChar usrid08h = new NChar(1);
    // COB:            02  USRID08V    PICTURE X.
    public NChar usrid08v = new NChar(1);
    // COB:            02  USRID08O  PIC X(8).
    public NChar usrid08o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler645 = new NChar(3);
    // COB:            02  FNAME08C    PICTURE X.
    public NChar fname08c = new NChar(1);
    // COB:            02  FNAME08P    PICTURE X.
    public NChar fname08p = new NChar(1);
    // COB:            02  FNAME08H    PICTURE X.
    public NChar fname08h = new NChar(1);
    // COB:            02  FNAME08V    PICTURE X.
    public NChar fname08v = new NChar(1);
    // COB:            02  FNAME08O  PIC X(20).
    public NChar fname08o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler651 = new NChar(3);
    // COB:            02  LNAME08C    PICTURE X.
    public NChar lname08c = new NChar(1);
    // COB:            02  LNAME08P    PICTURE X.
    public NChar lname08p = new NChar(1);
    // COB:            02  LNAME08H    PICTURE X.
    public NChar lname08h = new NChar(1);
    // COB:            02  LNAME08V    PICTURE X.
    public NChar lname08v = new NChar(1);
    // COB:            02  LNAME08O  PIC X(20).
    public NChar lname08o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler657 = new NChar(3);
    // COB:            02  UTYPE08C    PICTURE X.
    public NChar utype08c = new NChar(1);
    // COB:            02  UTYPE08P    PICTURE X.
    public NChar utype08p = new NChar(1);
    // COB:            02  UTYPE08H    PICTURE X.
    public NChar utype08h = new NChar(1);
    // COB:            02  UTYPE08V    PICTURE X.
    public NChar utype08v = new NChar(1);
    // COB:            02  UTYPE08O  PIC X(1).
    public NChar utype08o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler663 = new NChar(3);
    // COB:            02  SEL0009C    PICTURE X.
    public NChar sel0009c = new NChar(1);
    // COB:            02  SEL0009P    PICTURE X.
    public NChar sel0009p = new NChar(1);
    // COB:            02  SEL0009H    PICTURE X.
    public NChar sel0009h = new NChar(1);
    // COB:            02  SEL0009V    PICTURE X.
    public NChar sel0009v = new NChar(1);
    // COB:            02  SEL0009O  PIC X(1).
    public NChar sel0009o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler669 = new NChar(3);
    // COB:            02  USRID09C    PICTURE X.
    public NChar usrid09c = new NChar(1);
    // COB:            02  USRID09P    PICTURE X.
    public NChar usrid09p = new NChar(1);
    // COB:            02  USRID09H    PICTURE X.
    public NChar usrid09h = new NChar(1);
    // COB:            02  USRID09V    PICTURE X.
    public NChar usrid09v = new NChar(1);
    // COB:            02  USRID09O  PIC X(8).
    public NChar usrid09o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler675 = new NChar(3);
    // COB:            02  FNAME09C    PICTURE X.
    public NChar fname09c = new NChar(1);
    // COB:            02  FNAME09P    PICTURE X.
    public NChar fname09p = new NChar(1);
    // COB:            02  FNAME09H    PICTURE X.
    public NChar fname09h = new NChar(1);
    // COB:            02  FNAME09V    PICTURE X.
    public NChar fname09v = new NChar(1);
    // COB:            02  FNAME09O  PIC X(20).
    public NChar fname09o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler681 = new NChar(3);
    // COB:            02  LNAME09C    PICTURE X.
    public NChar lname09c = new NChar(1);
    // COB:            02  LNAME09P    PICTURE X.
    public NChar lname09p = new NChar(1);
    // COB:            02  LNAME09H    PICTURE X.
    public NChar lname09h = new NChar(1);
    // COB:            02  LNAME09V    PICTURE X.
    public NChar lname09v = new NChar(1);
    // COB:            02  LNAME09O  PIC X(20).
    public NChar lname09o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler687 = new NChar(3);
    // COB:            02  UTYPE09C    PICTURE X.
    public NChar utype09c = new NChar(1);
    // COB:            02  UTYPE09P    PICTURE X.
    public NChar utype09p = new NChar(1);
    // COB:            02  UTYPE09H    PICTURE X.
    public NChar utype09h = new NChar(1);
    // COB:            02  UTYPE09V    PICTURE X.
    public NChar utype09v = new NChar(1);
    // COB:            02  UTYPE09O  PIC X(1).
    public NChar utype09o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler693 = new NChar(3);
    // COB:            02  SEL0010C    PICTURE X.
    public NChar sel0010c = new NChar(1);
    // COB:            02  SEL0010P    PICTURE X.
    public NChar sel0010p = new NChar(1);
    // COB:            02  SEL0010H    PICTURE X.
    public NChar sel0010h = new NChar(1);
    // COB:            02  SEL0010V    PICTURE X.
    public NChar sel0010v = new NChar(1);
    // COB:            02  SEL0010O  PIC X(1).
    public NChar sel0010o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler699 = new NChar(3);
    // COB:            02  USRID10C    PICTURE X.
    public NChar usrid10c = new NChar(1);
    // COB:            02  USRID10P    PICTURE X.
    public NChar usrid10p = new NChar(1);
    // COB:            02  USRID10H    PICTURE X.
    public NChar usrid10h = new NChar(1);
    // COB:            02  USRID10V    PICTURE X.
    public NChar usrid10v = new NChar(1);
    // COB:            02  USRID10O  PIC X(8).
    public NChar usrid10o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler705 = new NChar(3);
    // COB:            02  FNAME10C    PICTURE X.
    public NChar fname10c = new NChar(1);
    // COB:            02  FNAME10P    PICTURE X.
    public NChar fname10p = new NChar(1);
    // COB:            02  FNAME10H    PICTURE X.
    public NChar fname10h = new NChar(1);
    // COB:            02  FNAME10V    PICTURE X.
    public NChar fname10v = new NChar(1);
    // COB:            02  FNAME10O  PIC X(20).
    public NChar fname10o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler711 = new NChar(3);
    // COB:            02  LNAME10C    PICTURE X.
    public NChar lname10c = new NChar(1);
    // COB:            02  LNAME10P    PICTURE X.
    public NChar lname10p = new NChar(1);
    // COB:            02  LNAME10H    PICTURE X.
    public NChar lname10h = new NChar(1);
    // COB:            02  LNAME10V    PICTURE X.
    public NChar lname10v = new NChar(1);
    // COB:            02  LNAME10O  PIC X(20).
    public NChar lname10o = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler717 = new NChar(3);
    // COB:            02  UTYPE10C    PICTURE X.
    public NChar utype10c = new NChar(1);
    // COB:            02  UTYPE10P    PICTURE X.
    public NChar utype10p = new NChar(1);
    // COB:            02  UTYPE10H    PICTURE X.
    public NChar utype10h = new NChar(1);
    // COB:            02  UTYPE10V    PICTURE X.
    public NChar utype10v = new NChar(1);
    // COB:            02  UTYPE10O  PIC X(1).
    public NChar utype10o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler723 = new NChar(3);
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

  public Cousr0ao cousr0ao = (Cousr0ao) new Cousr0ao().redefines(cousr0ai);
}
