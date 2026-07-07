package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cotrn00 extends NGroup {

  // COB:        01  COTRN0AI.
  public static class Cotrn0ai extends NGroup {
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
    // COB:            02  TRNIDINL    COMP  PIC  S9(4).
    public NInt16 trnidinl = new NInt16();
    // COB:            02  TRNIDINF    PICTURE X.
    public NChar trnidinf = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNIDINF.
    public static class Filler63 extends NGroup {
      // COB:              03 TRNIDINA    PICTURE X.
      public NChar trnidina = new NChar(1);
    }

    public Filler63 filler63 = (Filler63) new Filler63().redefines(trnidinf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler65 = new NChar(4);
    // COB:            02  TRNIDINI  PIC X(16).
    public NChar trnidini = new NChar(16);
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
    // COB:            02  TRNID01L    COMP  PIC  S9(4).
    public NInt16 trnid01l = new NInt16();
    // COB:            02  TRNID01F    PICTURE X.
    public NChar trnid01f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID01F.
    public static class Filler75 extends NGroup {
      // COB:              03 TRNID01A    PICTURE X.
      public NChar trnid01a = new NChar(1);
    }

    public Filler75 filler75 = (Filler75) new Filler75().redefines(trnid01f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler77 = new NChar(4);
    // COB:            02  TRNID01I  PIC X(16).
    public NChar trnid01i = new NChar(16);
    // COB:            02  TDATE01L    COMP  PIC  S9(4).
    public NInt16 tdate01l = new NInt16();
    // COB:            02  TDATE01F    PICTURE X.
    public NChar tdate01f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDATE01F.
    public static class Filler81 extends NGroup {
      // COB:              03 TDATE01A    PICTURE X.
      public NChar tdate01a = new NChar(1);
    }

    public Filler81 filler81 = (Filler81) new Filler81().redefines(tdate01f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler83 = new NChar(4);
    // COB:            02  TDATE01I  PIC X(8).
    public NChar tdate01i = new NChar(8);
    // COB:            02  TDESC01L    COMP  PIC  S9(4).
    public NInt16 tdesc01l = new NInt16();
    // COB:            02  TDESC01F    PICTURE X.
    public NChar tdesc01f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDESC01F.
    public static class Filler87 extends NGroup {
      // COB:              03 TDESC01A    PICTURE X.
      public NChar tdesc01a = new NChar(1);
    }

    public Filler87 filler87 = (Filler87) new Filler87().redefines(tdesc01f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler89 = new NChar(4);
    // COB:            02  TDESC01I  PIC X(26).
    public NChar tdesc01i = new NChar(26);
    // COB:            02  TAMT001L    COMP  PIC  S9(4).
    public NInt16 tamt001l = new NInt16();
    // COB:            02  TAMT001F    PICTURE X.
    public NChar tamt001f = new NChar(1);

    // COB:            02  FILLER REDEFINES TAMT001F.
    public static class Filler93 extends NGroup {
      // COB:              03 TAMT001A    PICTURE X.
      public NChar tamt001a = new NChar(1);
    }

    public Filler93 filler93 = (Filler93) new Filler93().redefines(tamt001f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler95 = new NChar(4);
    // COB:            02  TAMT001I  PIC X(12).
    public NChar tamt001i = new NChar(12);
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
    // COB:            02  TRNID02L    COMP  PIC  S9(4).
    public NInt16 trnid02l = new NInt16();
    // COB:            02  TRNID02F    PICTURE X.
    public NChar trnid02f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID02F.
    public static class Filler105 extends NGroup {
      // COB:              03 TRNID02A    PICTURE X.
      public NChar trnid02a = new NChar(1);
    }

    public Filler105 filler105 = (Filler105) new Filler105().redefines(trnid02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler107 = new NChar(4);
    // COB:            02  TRNID02I  PIC X(16).
    public NChar trnid02i = new NChar(16);
    // COB:            02  TDATE02L    COMP  PIC  S9(4).
    public NInt16 tdate02l = new NInt16();
    // COB:            02  TDATE02F    PICTURE X.
    public NChar tdate02f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDATE02F.
    public static class Filler111 extends NGroup {
      // COB:              03 TDATE02A    PICTURE X.
      public NChar tdate02a = new NChar(1);
    }

    public Filler111 filler111 = (Filler111) new Filler111().redefines(tdate02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler113 = new NChar(4);
    // COB:            02  TDATE02I  PIC X(8).
    public NChar tdate02i = new NChar(8);
    // COB:            02  TDESC02L    COMP  PIC  S9(4).
    public NInt16 tdesc02l = new NInt16();
    // COB:            02  TDESC02F    PICTURE X.
    public NChar tdesc02f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDESC02F.
    public static class Filler117 extends NGroup {
      // COB:              03 TDESC02A    PICTURE X.
      public NChar tdesc02a = new NChar(1);
    }

    public Filler117 filler117 = (Filler117) new Filler117().redefines(tdesc02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler119 = new NChar(4);
    // COB:            02  TDESC02I  PIC X(26).
    public NChar tdesc02i = new NChar(26);
    // COB:            02  TAMT002L    COMP  PIC  S9(4).
    public NInt16 tamt002l = new NInt16();
    // COB:            02  TAMT002F    PICTURE X.
    public NChar tamt002f = new NChar(1);

    // COB:            02  FILLER REDEFINES TAMT002F.
    public static class Filler123 extends NGroup {
      // COB:              03 TAMT002A    PICTURE X.
      public NChar tamt002a = new NChar(1);
    }

    public Filler123 filler123 = (Filler123) new Filler123().redefines(tamt002f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler125 = new NChar(4);
    // COB:            02  TAMT002I  PIC X(12).
    public NChar tamt002i = new NChar(12);
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
    // COB:            02  TRNID03L    COMP  PIC  S9(4).
    public NInt16 trnid03l = new NInt16();
    // COB:            02  TRNID03F    PICTURE X.
    public NChar trnid03f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID03F.
    public static class Filler135 extends NGroup {
      // COB:              03 TRNID03A    PICTURE X.
      public NChar trnid03a = new NChar(1);
    }

    public Filler135 filler135 = (Filler135) new Filler135().redefines(trnid03f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler137 = new NChar(4);
    // COB:            02  TRNID03I  PIC X(16).
    public NChar trnid03i = new NChar(16);
    // COB:            02  TDATE03L    COMP  PIC  S9(4).
    public NInt16 tdate03l = new NInt16();
    // COB:            02  TDATE03F    PICTURE X.
    public NChar tdate03f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDATE03F.
    public static class Filler141 extends NGroup {
      // COB:              03 TDATE03A    PICTURE X.
      public NChar tdate03a = new NChar(1);
    }

    public Filler141 filler141 = (Filler141) new Filler141().redefines(tdate03f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler143 = new NChar(4);
    // COB:            02  TDATE03I  PIC X(8).
    public NChar tdate03i = new NChar(8);
    // COB:            02  TDESC03L    COMP  PIC  S9(4).
    public NInt16 tdesc03l = new NInt16();
    // COB:            02  TDESC03F    PICTURE X.
    public NChar tdesc03f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDESC03F.
    public static class Filler147 extends NGroup {
      // COB:              03 TDESC03A    PICTURE X.
      public NChar tdesc03a = new NChar(1);
    }

    public Filler147 filler147 = (Filler147) new Filler147().redefines(tdesc03f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler149 = new NChar(4);
    // COB:            02  TDESC03I  PIC X(26).
    public NChar tdesc03i = new NChar(26);
    // COB:            02  TAMT003L    COMP  PIC  S9(4).
    public NInt16 tamt003l = new NInt16();
    // COB:            02  TAMT003F    PICTURE X.
    public NChar tamt003f = new NChar(1);

    // COB:            02  FILLER REDEFINES TAMT003F.
    public static class Filler153 extends NGroup {
      // COB:              03 TAMT003A    PICTURE X.
      public NChar tamt003a = new NChar(1);
    }

    public Filler153 filler153 = (Filler153) new Filler153().redefines(tamt003f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler155 = new NChar(4);
    // COB:            02  TAMT003I  PIC X(12).
    public NChar tamt003i = new NChar(12);
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
    // COB:            02  TRNID04L    COMP  PIC  S9(4).
    public NInt16 trnid04l = new NInt16();
    // COB:            02  TRNID04F    PICTURE X.
    public NChar trnid04f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID04F.
    public static class Filler165 extends NGroup {
      // COB:              03 TRNID04A    PICTURE X.
      public NChar trnid04a = new NChar(1);
    }

    public Filler165 filler165 = (Filler165) new Filler165().redefines(trnid04f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler167 = new NChar(4);
    // COB:            02  TRNID04I  PIC X(16).
    public NChar trnid04i = new NChar(16);
    // COB:            02  TDATE04L    COMP  PIC  S9(4).
    public NInt16 tdate04l = new NInt16();
    // COB:            02  TDATE04F    PICTURE X.
    public NChar tdate04f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDATE04F.
    public static class Filler171 extends NGroup {
      // COB:              03 TDATE04A    PICTURE X.
      public NChar tdate04a = new NChar(1);
    }

    public Filler171 filler171 = (Filler171) new Filler171().redefines(tdate04f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler173 = new NChar(4);
    // COB:            02  TDATE04I  PIC X(8).
    public NChar tdate04i = new NChar(8);
    // COB:            02  TDESC04L    COMP  PIC  S9(4).
    public NInt16 tdesc04l = new NInt16();
    // COB:            02  TDESC04F    PICTURE X.
    public NChar tdesc04f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDESC04F.
    public static class Filler177 extends NGroup {
      // COB:              03 TDESC04A    PICTURE X.
      public NChar tdesc04a = new NChar(1);
    }

    public Filler177 filler177 = (Filler177) new Filler177().redefines(tdesc04f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler179 = new NChar(4);
    // COB:            02  TDESC04I  PIC X(26).
    public NChar tdesc04i = new NChar(26);
    // COB:            02  TAMT004L    COMP  PIC  S9(4).
    public NInt16 tamt004l = new NInt16();
    // COB:            02  TAMT004F    PICTURE X.
    public NChar tamt004f = new NChar(1);

    // COB:            02  FILLER REDEFINES TAMT004F.
    public static class Filler183 extends NGroup {
      // COB:              03 TAMT004A    PICTURE X.
      public NChar tamt004a = new NChar(1);
    }

    public Filler183 filler183 = (Filler183) new Filler183().redefines(tamt004f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler185 = new NChar(4);
    // COB:            02  TAMT004I  PIC X(12).
    public NChar tamt004i = new NChar(12);
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
    // COB:            02  TRNID05L    COMP  PIC  S9(4).
    public NInt16 trnid05l = new NInt16();
    // COB:            02  TRNID05F    PICTURE X.
    public NChar trnid05f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID05F.
    public static class Filler195 extends NGroup {
      // COB:              03 TRNID05A    PICTURE X.
      public NChar trnid05a = new NChar(1);
    }

    public Filler195 filler195 = (Filler195) new Filler195().redefines(trnid05f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler197 = new NChar(4);
    // COB:            02  TRNID05I  PIC X(16).
    public NChar trnid05i = new NChar(16);
    // COB:            02  TDATE05L    COMP  PIC  S9(4).
    public NInt16 tdate05l = new NInt16();
    // COB:            02  TDATE05F    PICTURE X.
    public NChar tdate05f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDATE05F.
    public static class Filler201 extends NGroup {
      // COB:              03 TDATE05A    PICTURE X.
      public NChar tdate05a = new NChar(1);
    }

    public Filler201 filler201 = (Filler201) new Filler201().redefines(tdate05f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler203 = new NChar(4);
    // COB:            02  TDATE05I  PIC X(8).
    public NChar tdate05i = new NChar(8);
    // COB:            02  TDESC05L    COMP  PIC  S9(4).
    public NInt16 tdesc05l = new NInt16();
    // COB:            02  TDESC05F    PICTURE X.
    public NChar tdesc05f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDESC05F.
    public static class Filler207 extends NGroup {
      // COB:              03 TDESC05A    PICTURE X.
      public NChar tdesc05a = new NChar(1);
    }

    public Filler207 filler207 = (Filler207) new Filler207().redefines(tdesc05f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler209 = new NChar(4);
    // COB:            02  TDESC05I  PIC X(26).
    public NChar tdesc05i = new NChar(26);
    // COB:            02  TAMT005L    COMP  PIC  S9(4).
    public NInt16 tamt005l = new NInt16();
    // COB:            02  TAMT005F    PICTURE X.
    public NChar tamt005f = new NChar(1);

    // COB:            02  FILLER REDEFINES TAMT005F.
    public static class Filler213 extends NGroup {
      // COB:              03 TAMT005A    PICTURE X.
      public NChar tamt005a = new NChar(1);
    }

    public Filler213 filler213 = (Filler213) new Filler213().redefines(tamt005f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler215 = new NChar(4);
    // COB:            02  TAMT005I  PIC X(12).
    public NChar tamt005i = new NChar(12);
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
    // COB:            02  TRNID06L    COMP  PIC  S9(4).
    public NInt16 trnid06l = new NInt16();
    // COB:            02  TRNID06F    PICTURE X.
    public NChar trnid06f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID06F.
    public static class Filler225 extends NGroup {
      // COB:              03 TRNID06A    PICTURE X.
      public NChar trnid06a = new NChar(1);
    }

    public Filler225 filler225 = (Filler225) new Filler225().redefines(trnid06f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler227 = new NChar(4);
    // COB:            02  TRNID06I  PIC X(16).
    public NChar trnid06i = new NChar(16);
    // COB:            02  TDATE06L    COMP  PIC  S9(4).
    public NInt16 tdate06l = new NInt16();
    // COB:            02  TDATE06F    PICTURE X.
    public NChar tdate06f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDATE06F.
    public static class Filler231 extends NGroup {
      // COB:              03 TDATE06A    PICTURE X.
      public NChar tdate06a = new NChar(1);
    }

    public Filler231 filler231 = (Filler231) new Filler231().redefines(tdate06f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler233 = new NChar(4);
    // COB:            02  TDATE06I  PIC X(8).
    public NChar tdate06i = new NChar(8);
    // COB:            02  TDESC06L    COMP  PIC  S9(4).
    public NInt16 tdesc06l = new NInt16();
    // COB:            02  TDESC06F    PICTURE X.
    public NChar tdesc06f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDESC06F.
    public static class Filler237 extends NGroup {
      // COB:              03 TDESC06A    PICTURE X.
      public NChar tdesc06a = new NChar(1);
    }

    public Filler237 filler237 = (Filler237) new Filler237().redefines(tdesc06f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler239 = new NChar(4);
    // COB:            02  TDESC06I  PIC X(26).
    public NChar tdesc06i = new NChar(26);
    // COB:            02  TAMT006L    COMP  PIC  S9(4).
    public NInt16 tamt006l = new NInt16();
    // COB:            02  TAMT006F    PICTURE X.
    public NChar tamt006f = new NChar(1);

    // COB:            02  FILLER REDEFINES TAMT006F.
    public static class Filler243 extends NGroup {
      // COB:              03 TAMT006A    PICTURE X.
      public NChar tamt006a = new NChar(1);
    }

    public Filler243 filler243 = (Filler243) new Filler243().redefines(tamt006f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler245 = new NChar(4);
    // COB:            02  TAMT006I  PIC X(12).
    public NChar tamt006i = new NChar(12);
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
    // COB:            02  TRNID07L    COMP  PIC  S9(4).
    public NInt16 trnid07l = new NInt16();
    // COB:            02  TRNID07F    PICTURE X.
    public NChar trnid07f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID07F.
    public static class Filler255 extends NGroup {
      // COB:              03 TRNID07A    PICTURE X.
      public NChar trnid07a = new NChar(1);
    }

    public Filler255 filler255 = (Filler255) new Filler255().redefines(trnid07f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler257 = new NChar(4);
    // COB:            02  TRNID07I  PIC X(16).
    public NChar trnid07i = new NChar(16);
    // COB:            02  TDATE07L    COMP  PIC  S9(4).
    public NInt16 tdate07l = new NInt16();
    // COB:            02  TDATE07F    PICTURE X.
    public NChar tdate07f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDATE07F.
    public static class Filler261 extends NGroup {
      // COB:              03 TDATE07A    PICTURE X.
      public NChar tdate07a = new NChar(1);
    }

    public Filler261 filler261 = (Filler261) new Filler261().redefines(tdate07f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler263 = new NChar(4);
    // COB:            02  TDATE07I  PIC X(8).
    public NChar tdate07i = new NChar(8);
    // COB:            02  TDESC07L    COMP  PIC  S9(4).
    public NInt16 tdesc07l = new NInt16();
    // COB:            02  TDESC07F    PICTURE X.
    public NChar tdesc07f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDESC07F.
    public static class Filler267 extends NGroup {
      // COB:              03 TDESC07A    PICTURE X.
      public NChar tdesc07a = new NChar(1);
    }

    public Filler267 filler267 = (Filler267) new Filler267().redefines(tdesc07f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler269 = new NChar(4);
    // COB:            02  TDESC07I  PIC X(26).
    public NChar tdesc07i = new NChar(26);
    // COB:            02  TAMT007L    COMP  PIC  S9(4).
    public NInt16 tamt007l = new NInt16();
    // COB:            02  TAMT007F    PICTURE X.
    public NChar tamt007f = new NChar(1);

    // COB:            02  FILLER REDEFINES TAMT007F.
    public static class Filler273 extends NGroup {
      // COB:              03 TAMT007A    PICTURE X.
      public NChar tamt007a = new NChar(1);
    }

    public Filler273 filler273 = (Filler273) new Filler273().redefines(tamt007f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler275 = new NChar(4);
    // COB:            02  TAMT007I  PIC X(12).
    public NChar tamt007i = new NChar(12);
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
    // COB:            02  TRNID08L    COMP  PIC  S9(4).
    public NInt16 trnid08l = new NInt16();
    // COB:            02  TRNID08F    PICTURE X.
    public NChar trnid08f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID08F.
    public static class Filler285 extends NGroup {
      // COB:              03 TRNID08A    PICTURE X.
      public NChar trnid08a = new NChar(1);
    }

    public Filler285 filler285 = (Filler285) new Filler285().redefines(trnid08f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler287 = new NChar(4);
    // COB:            02  TRNID08I  PIC X(16).
    public NChar trnid08i = new NChar(16);
    // COB:            02  TDATE08L    COMP  PIC  S9(4).
    public NInt16 tdate08l = new NInt16();
    // COB:            02  TDATE08F    PICTURE X.
    public NChar tdate08f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDATE08F.
    public static class Filler291 extends NGroup {
      // COB:              03 TDATE08A    PICTURE X.
      public NChar tdate08a = new NChar(1);
    }

    public Filler291 filler291 = (Filler291) new Filler291().redefines(tdate08f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler293 = new NChar(4);
    // COB:            02  TDATE08I  PIC X(8).
    public NChar tdate08i = new NChar(8);
    // COB:            02  TDESC08L    COMP  PIC  S9(4).
    public NInt16 tdesc08l = new NInt16();
    // COB:            02  TDESC08F    PICTURE X.
    public NChar tdesc08f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDESC08F.
    public static class Filler297 extends NGroup {
      // COB:              03 TDESC08A    PICTURE X.
      public NChar tdesc08a = new NChar(1);
    }

    public Filler297 filler297 = (Filler297) new Filler297().redefines(tdesc08f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler299 = new NChar(4);
    // COB:            02  TDESC08I  PIC X(26).
    public NChar tdesc08i = new NChar(26);
    // COB:            02  TAMT008L    COMP  PIC  S9(4).
    public NInt16 tamt008l = new NInt16();
    // COB:            02  TAMT008F    PICTURE X.
    public NChar tamt008f = new NChar(1);

    // COB:            02  FILLER REDEFINES TAMT008F.
    public static class Filler303 extends NGroup {
      // COB:              03 TAMT008A    PICTURE X.
      public NChar tamt008a = new NChar(1);
    }

    public Filler303 filler303 = (Filler303) new Filler303().redefines(tamt008f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler305 = new NChar(4);
    // COB:            02  TAMT008I  PIC X(12).
    public NChar tamt008i = new NChar(12);
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
    // COB:            02  TRNID09L    COMP  PIC  S9(4).
    public NInt16 trnid09l = new NInt16();
    // COB:            02  TRNID09F    PICTURE X.
    public NChar trnid09f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID09F.
    public static class Filler315 extends NGroup {
      // COB:              03 TRNID09A    PICTURE X.
      public NChar trnid09a = new NChar(1);
    }

    public Filler315 filler315 = (Filler315) new Filler315().redefines(trnid09f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler317 = new NChar(4);
    // COB:            02  TRNID09I  PIC X(16).
    public NChar trnid09i = new NChar(16);
    // COB:            02  TDATE09L    COMP  PIC  S9(4).
    public NInt16 tdate09l = new NInt16();
    // COB:            02  TDATE09F    PICTURE X.
    public NChar tdate09f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDATE09F.
    public static class Filler321 extends NGroup {
      // COB:              03 TDATE09A    PICTURE X.
      public NChar tdate09a = new NChar(1);
    }

    public Filler321 filler321 = (Filler321) new Filler321().redefines(tdate09f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler323 = new NChar(4);
    // COB:            02  TDATE09I  PIC X(8).
    public NChar tdate09i = new NChar(8);
    // COB:            02  TDESC09L    COMP  PIC  S9(4).
    public NInt16 tdesc09l = new NInt16();
    // COB:            02  TDESC09F    PICTURE X.
    public NChar tdesc09f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDESC09F.
    public static class Filler327 extends NGroup {
      // COB:              03 TDESC09A    PICTURE X.
      public NChar tdesc09a = new NChar(1);
    }

    public Filler327 filler327 = (Filler327) new Filler327().redefines(tdesc09f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler329 = new NChar(4);
    // COB:            02  TDESC09I  PIC X(26).
    public NChar tdesc09i = new NChar(26);
    // COB:            02  TAMT009L    COMP  PIC  S9(4).
    public NInt16 tamt009l = new NInt16();
    // COB:            02  TAMT009F    PICTURE X.
    public NChar tamt009f = new NChar(1);

    // COB:            02  FILLER REDEFINES TAMT009F.
    public static class Filler333 extends NGroup {
      // COB:              03 TAMT009A    PICTURE X.
      public NChar tamt009a = new NChar(1);
    }

    public Filler333 filler333 = (Filler333) new Filler333().redefines(tamt009f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler335 = new NChar(4);
    // COB:            02  TAMT009I  PIC X(12).
    public NChar tamt009i = new NChar(12);
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
    // COB:            02  TRNID10L    COMP  PIC  S9(4).
    public NInt16 trnid10l = new NInt16();
    // COB:            02  TRNID10F    PICTURE X.
    public NChar trnid10f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID10F.
    public static class Filler345 extends NGroup {
      // COB:              03 TRNID10A    PICTURE X.
      public NChar trnid10a = new NChar(1);
    }

    public Filler345 filler345 = (Filler345) new Filler345().redefines(trnid10f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler347 = new NChar(4);
    // COB:            02  TRNID10I  PIC X(16).
    public NChar trnid10i = new NChar(16);
    // COB:            02  TDATE10L    COMP  PIC  S9(4).
    public NInt16 tdate10l = new NInt16();
    // COB:            02  TDATE10F    PICTURE X.
    public NChar tdate10f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDATE10F.
    public static class Filler351 extends NGroup {
      // COB:              03 TDATE10A    PICTURE X.
      public NChar tdate10a = new NChar(1);
    }

    public Filler351 filler351 = (Filler351) new Filler351().redefines(tdate10f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler353 = new NChar(4);
    // COB:            02  TDATE10I  PIC X(8).
    public NChar tdate10i = new NChar(8);
    // COB:            02  TDESC10L    COMP  PIC  S9(4).
    public NInt16 tdesc10l = new NInt16();
    // COB:            02  TDESC10F    PICTURE X.
    public NChar tdesc10f = new NChar(1);

    // COB:            02  FILLER REDEFINES TDESC10F.
    public static class Filler357 extends NGroup {
      // COB:              03 TDESC10A    PICTURE X.
      public NChar tdesc10a = new NChar(1);
    }

    public Filler357 filler357 = (Filler357) new Filler357().redefines(tdesc10f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler359 = new NChar(4);
    // COB:            02  TDESC10I  PIC X(26).
    public NChar tdesc10i = new NChar(26);
    // COB:            02  TAMT010L    COMP  PIC  S9(4).
    public NInt16 tamt010l = new NInt16();
    // COB:            02  TAMT010F    PICTURE X.
    public NChar tamt010f = new NChar(1);

    // COB:            02  FILLER REDEFINES TAMT010F.
    public static class Filler363 extends NGroup {
      // COB:              03 TAMT010A    PICTURE X.
      public NChar tamt010a = new NChar(1);
    }

    public Filler363 filler363 = (Filler363) new Filler363().redefines(tamt010f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler365 = new NChar(4);
    // COB:            02  TAMT010I  PIC X(12).
    public NChar tamt010i = new NChar(12);
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

  public Cotrn0ai cotrn0ai = new Cotrn0ai();

  // COB:        01  COTRN0AO REDEFINES COTRN0AI.
  public static class Cotrn0ao extends NGroup {
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
    // COB:            02  TRNIDINC    PICTURE X.
    public NChar trnidinc = new NChar(1);
    // COB:            02  TRNIDINP    PICTURE X.
    public NChar trnidinp = new NChar(1);
    // COB:            02  TRNIDINH    PICTURE X.
    public NChar trnidinh = new NChar(1);
    // COB:            02  TRNIDINV    PICTURE X.
    public NChar trnidinv = new NChar(1);
    // COB:            02  TRNIDINO  PIC X(16).
    public NChar trnidino = new NChar(16);
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
    // COB:            02  TRNID01C    PICTURE X.
    public NChar trnid01c = new NChar(1);
    // COB:            02  TRNID01P    PICTURE X.
    public NChar trnid01p = new NChar(1);
    // COB:            02  TRNID01H    PICTURE X.
    public NChar trnid01h = new NChar(1);
    // COB:            02  TRNID01V    PICTURE X.
    public NChar trnid01v = new NChar(1);
    // COB:            02  TRNID01O  PIC X(16).
    public NChar trnid01o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler435 = new NChar(3);
    // COB:            02  TDATE01C    PICTURE X.
    public NChar tdate01c = new NChar(1);
    // COB:            02  TDATE01P    PICTURE X.
    public NChar tdate01p = new NChar(1);
    // COB:            02  TDATE01H    PICTURE X.
    public NChar tdate01h = new NChar(1);
    // COB:            02  TDATE01V    PICTURE X.
    public NChar tdate01v = new NChar(1);
    // COB:            02  TDATE01O  PIC X(8).
    public NChar tdate01o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler441 = new NChar(3);
    // COB:            02  TDESC01C    PICTURE X.
    public NChar tdesc01c = new NChar(1);
    // COB:            02  TDESC01P    PICTURE X.
    public NChar tdesc01p = new NChar(1);
    // COB:            02  TDESC01H    PICTURE X.
    public NChar tdesc01h = new NChar(1);
    // COB:            02  TDESC01V    PICTURE X.
    public NChar tdesc01v = new NChar(1);
    // COB:            02  TDESC01O  PIC X(26).
    public NChar tdesc01o = new NChar(26);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler447 = new NChar(3);
    // COB:            02  TAMT001C    PICTURE X.
    public NChar tamt001c = new NChar(1);
    // COB:            02  TAMT001P    PICTURE X.
    public NChar tamt001p = new NChar(1);
    // COB:            02  TAMT001H    PICTURE X.
    public NChar tamt001h = new NChar(1);
    // COB:            02  TAMT001V    PICTURE X.
    public NChar tamt001v = new NChar(1);
    // COB:            02  TAMT001O  PIC X(12).
    public NChar tamt001o = new NChar(12);
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
    // COB:            02  TRNID02C    PICTURE X.
    public NChar trnid02c = new NChar(1);
    // COB:            02  TRNID02P    PICTURE X.
    public NChar trnid02p = new NChar(1);
    // COB:            02  TRNID02H    PICTURE X.
    public NChar trnid02h = new NChar(1);
    // COB:            02  TRNID02V    PICTURE X.
    public NChar trnid02v = new NChar(1);
    // COB:            02  TRNID02O  PIC X(16).
    public NChar trnid02o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler465 = new NChar(3);
    // COB:            02  TDATE02C    PICTURE X.
    public NChar tdate02c = new NChar(1);
    // COB:            02  TDATE02P    PICTURE X.
    public NChar tdate02p = new NChar(1);
    // COB:            02  TDATE02H    PICTURE X.
    public NChar tdate02h = new NChar(1);
    // COB:            02  TDATE02V    PICTURE X.
    public NChar tdate02v = new NChar(1);
    // COB:            02  TDATE02O  PIC X(8).
    public NChar tdate02o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler471 = new NChar(3);
    // COB:            02  TDESC02C    PICTURE X.
    public NChar tdesc02c = new NChar(1);
    // COB:            02  TDESC02P    PICTURE X.
    public NChar tdesc02p = new NChar(1);
    // COB:            02  TDESC02H    PICTURE X.
    public NChar tdesc02h = new NChar(1);
    // COB:            02  TDESC02V    PICTURE X.
    public NChar tdesc02v = new NChar(1);
    // COB:            02  TDESC02O  PIC X(26).
    public NChar tdesc02o = new NChar(26);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler477 = new NChar(3);
    // COB:            02  TAMT002C    PICTURE X.
    public NChar tamt002c = new NChar(1);
    // COB:            02  TAMT002P    PICTURE X.
    public NChar tamt002p = new NChar(1);
    // COB:            02  TAMT002H    PICTURE X.
    public NChar tamt002h = new NChar(1);
    // COB:            02  TAMT002V    PICTURE X.
    public NChar tamt002v = new NChar(1);
    // COB:            02  TAMT002O  PIC X(12).
    public NChar tamt002o = new NChar(12);
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
    // COB:            02  TRNID03C    PICTURE X.
    public NChar trnid03c = new NChar(1);
    // COB:            02  TRNID03P    PICTURE X.
    public NChar trnid03p = new NChar(1);
    // COB:            02  TRNID03H    PICTURE X.
    public NChar trnid03h = new NChar(1);
    // COB:            02  TRNID03V    PICTURE X.
    public NChar trnid03v = new NChar(1);
    // COB:            02  TRNID03O  PIC X(16).
    public NChar trnid03o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler495 = new NChar(3);
    // COB:            02  TDATE03C    PICTURE X.
    public NChar tdate03c = new NChar(1);
    // COB:            02  TDATE03P    PICTURE X.
    public NChar tdate03p = new NChar(1);
    // COB:            02  TDATE03H    PICTURE X.
    public NChar tdate03h = new NChar(1);
    // COB:            02  TDATE03V    PICTURE X.
    public NChar tdate03v = new NChar(1);
    // COB:            02  TDATE03O  PIC X(8).
    public NChar tdate03o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler501 = new NChar(3);
    // COB:            02  TDESC03C    PICTURE X.
    public NChar tdesc03c = new NChar(1);
    // COB:            02  TDESC03P    PICTURE X.
    public NChar tdesc03p = new NChar(1);
    // COB:            02  TDESC03H    PICTURE X.
    public NChar tdesc03h = new NChar(1);
    // COB:            02  TDESC03V    PICTURE X.
    public NChar tdesc03v = new NChar(1);
    // COB:            02  TDESC03O  PIC X(26).
    public NChar tdesc03o = new NChar(26);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler507 = new NChar(3);
    // COB:            02  TAMT003C    PICTURE X.
    public NChar tamt003c = new NChar(1);
    // COB:            02  TAMT003P    PICTURE X.
    public NChar tamt003p = new NChar(1);
    // COB:            02  TAMT003H    PICTURE X.
    public NChar tamt003h = new NChar(1);
    // COB:            02  TAMT003V    PICTURE X.
    public NChar tamt003v = new NChar(1);
    // COB:            02  TAMT003O  PIC X(12).
    public NChar tamt003o = new NChar(12);
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
    // COB:            02  TRNID04C    PICTURE X.
    public NChar trnid04c = new NChar(1);
    // COB:            02  TRNID04P    PICTURE X.
    public NChar trnid04p = new NChar(1);
    // COB:            02  TRNID04H    PICTURE X.
    public NChar trnid04h = new NChar(1);
    // COB:            02  TRNID04V    PICTURE X.
    public NChar trnid04v = new NChar(1);
    // COB:            02  TRNID04O  PIC X(16).
    public NChar trnid04o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler525 = new NChar(3);
    // COB:            02  TDATE04C    PICTURE X.
    public NChar tdate04c = new NChar(1);
    // COB:            02  TDATE04P    PICTURE X.
    public NChar tdate04p = new NChar(1);
    // COB:            02  TDATE04H    PICTURE X.
    public NChar tdate04h = new NChar(1);
    // COB:            02  TDATE04V    PICTURE X.
    public NChar tdate04v = new NChar(1);
    // COB:            02  TDATE04O  PIC X(8).
    public NChar tdate04o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler531 = new NChar(3);
    // COB:            02  TDESC04C    PICTURE X.
    public NChar tdesc04c = new NChar(1);
    // COB:            02  TDESC04P    PICTURE X.
    public NChar tdesc04p = new NChar(1);
    // COB:            02  TDESC04H    PICTURE X.
    public NChar tdesc04h = new NChar(1);
    // COB:            02  TDESC04V    PICTURE X.
    public NChar tdesc04v = new NChar(1);
    // COB:            02  TDESC04O  PIC X(26).
    public NChar tdesc04o = new NChar(26);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler537 = new NChar(3);
    // COB:            02  TAMT004C    PICTURE X.
    public NChar tamt004c = new NChar(1);
    // COB:            02  TAMT004P    PICTURE X.
    public NChar tamt004p = new NChar(1);
    // COB:            02  TAMT004H    PICTURE X.
    public NChar tamt004h = new NChar(1);
    // COB:            02  TAMT004V    PICTURE X.
    public NChar tamt004v = new NChar(1);
    // COB:            02  TAMT004O  PIC X(12).
    public NChar tamt004o = new NChar(12);
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
    // COB:            02  TRNID05C    PICTURE X.
    public NChar trnid05c = new NChar(1);
    // COB:            02  TRNID05P    PICTURE X.
    public NChar trnid05p = new NChar(1);
    // COB:            02  TRNID05H    PICTURE X.
    public NChar trnid05h = new NChar(1);
    // COB:            02  TRNID05V    PICTURE X.
    public NChar trnid05v = new NChar(1);
    // COB:            02  TRNID05O  PIC X(16).
    public NChar trnid05o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler555 = new NChar(3);
    // COB:            02  TDATE05C    PICTURE X.
    public NChar tdate05c = new NChar(1);
    // COB:            02  TDATE05P    PICTURE X.
    public NChar tdate05p = new NChar(1);
    // COB:            02  TDATE05H    PICTURE X.
    public NChar tdate05h = new NChar(1);
    // COB:            02  TDATE05V    PICTURE X.
    public NChar tdate05v = new NChar(1);
    // COB:            02  TDATE05O  PIC X(8).
    public NChar tdate05o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler561 = new NChar(3);
    // COB:            02  TDESC05C    PICTURE X.
    public NChar tdesc05c = new NChar(1);
    // COB:            02  TDESC05P    PICTURE X.
    public NChar tdesc05p = new NChar(1);
    // COB:            02  TDESC05H    PICTURE X.
    public NChar tdesc05h = new NChar(1);
    // COB:            02  TDESC05V    PICTURE X.
    public NChar tdesc05v = new NChar(1);
    // COB:            02  TDESC05O  PIC X(26).
    public NChar tdesc05o = new NChar(26);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler567 = new NChar(3);
    // COB:            02  TAMT005C    PICTURE X.
    public NChar tamt005c = new NChar(1);
    // COB:            02  TAMT005P    PICTURE X.
    public NChar tamt005p = new NChar(1);
    // COB:            02  TAMT005H    PICTURE X.
    public NChar tamt005h = new NChar(1);
    // COB:            02  TAMT005V    PICTURE X.
    public NChar tamt005v = new NChar(1);
    // COB:            02  TAMT005O  PIC X(12).
    public NChar tamt005o = new NChar(12);
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
    // COB:            02  TRNID06C    PICTURE X.
    public NChar trnid06c = new NChar(1);
    // COB:            02  TRNID06P    PICTURE X.
    public NChar trnid06p = new NChar(1);
    // COB:            02  TRNID06H    PICTURE X.
    public NChar trnid06h = new NChar(1);
    // COB:            02  TRNID06V    PICTURE X.
    public NChar trnid06v = new NChar(1);
    // COB:            02  TRNID06O  PIC X(16).
    public NChar trnid06o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler585 = new NChar(3);
    // COB:            02  TDATE06C    PICTURE X.
    public NChar tdate06c = new NChar(1);
    // COB:            02  TDATE06P    PICTURE X.
    public NChar tdate06p = new NChar(1);
    // COB:            02  TDATE06H    PICTURE X.
    public NChar tdate06h = new NChar(1);
    // COB:            02  TDATE06V    PICTURE X.
    public NChar tdate06v = new NChar(1);
    // COB:            02  TDATE06O  PIC X(8).
    public NChar tdate06o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler591 = new NChar(3);
    // COB:            02  TDESC06C    PICTURE X.
    public NChar tdesc06c = new NChar(1);
    // COB:            02  TDESC06P    PICTURE X.
    public NChar tdesc06p = new NChar(1);
    // COB:            02  TDESC06H    PICTURE X.
    public NChar tdesc06h = new NChar(1);
    // COB:            02  TDESC06V    PICTURE X.
    public NChar tdesc06v = new NChar(1);
    // COB:            02  TDESC06O  PIC X(26).
    public NChar tdesc06o = new NChar(26);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler597 = new NChar(3);
    // COB:            02  TAMT006C    PICTURE X.
    public NChar tamt006c = new NChar(1);
    // COB:            02  TAMT006P    PICTURE X.
    public NChar tamt006p = new NChar(1);
    // COB:            02  TAMT006H    PICTURE X.
    public NChar tamt006h = new NChar(1);
    // COB:            02  TAMT006V    PICTURE X.
    public NChar tamt006v = new NChar(1);
    // COB:            02  TAMT006O  PIC X(12).
    public NChar tamt006o = new NChar(12);
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
    // COB:            02  TRNID07C    PICTURE X.
    public NChar trnid07c = new NChar(1);
    // COB:            02  TRNID07P    PICTURE X.
    public NChar trnid07p = new NChar(1);
    // COB:            02  TRNID07H    PICTURE X.
    public NChar trnid07h = new NChar(1);
    // COB:            02  TRNID07V    PICTURE X.
    public NChar trnid07v = new NChar(1);
    // COB:            02  TRNID07O  PIC X(16).
    public NChar trnid07o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler615 = new NChar(3);
    // COB:            02  TDATE07C    PICTURE X.
    public NChar tdate07c = new NChar(1);
    // COB:            02  TDATE07P    PICTURE X.
    public NChar tdate07p = new NChar(1);
    // COB:            02  TDATE07H    PICTURE X.
    public NChar tdate07h = new NChar(1);
    // COB:            02  TDATE07V    PICTURE X.
    public NChar tdate07v = new NChar(1);
    // COB:            02  TDATE07O  PIC X(8).
    public NChar tdate07o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler621 = new NChar(3);
    // COB:            02  TDESC07C    PICTURE X.
    public NChar tdesc07c = new NChar(1);
    // COB:            02  TDESC07P    PICTURE X.
    public NChar tdesc07p = new NChar(1);
    // COB:            02  TDESC07H    PICTURE X.
    public NChar tdesc07h = new NChar(1);
    // COB:            02  TDESC07V    PICTURE X.
    public NChar tdesc07v = new NChar(1);
    // COB:            02  TDESC07O  PIC X(26).
    public NChar tdesc07o = new NChar(26);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler627 = new NChar(3);
    // COB:            02  TAMT007C    PICTURE X.
    public NChar tamt007c = new NChar(1);
    // COB:            02  TAMT007P    PICTURE X.
    public NChar tamt007p = new NChar(1);
    // COB:            02  TAMT007H    PICTURE X.
    public NChar tamt007h = new NChar(1);
    // COB:            02  TAMT007V    PICTURE X.
    public NChar tamt007v = new NChar(1);
    // COB:            02  TAMT007O  PIC X(12).
    public NChar tamt007o = new NChar(12);
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
    // COB:            02  TRNID08C    PICTURE X.
    public NChar trnid08c = new NChar(1);
    // COB:            02  TRNID08P    PICTURE X.
    public NChar trnid08p = new NChar(1);
    // COB:            02  TRNID08H    PICTURE X.
    public NChar trnid08h = new NChar(1);
    // COB:            02  TRNID08V    PICTURE X.
    public NChar trnid08v = new NChar(1);
    // COB:            02  TRNID08O  PIC X(16).
    public NChar trnid08o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler645 = new NChar(3);
    // COB:            02  TDATE08C    PICTURE X.
    public NChar tdate08c = new NChar(1);
    // COB:            02  TDATE08P    PICTURE X.
    public NChar tdate08p = new NChar(1);
    // COB:            02  TDATE08H    PICTURE X.
    public NChar tdate08h = new NChar(1);
    // COB:            02  TDATE08V    PICTURE X.
    public NChar tdate08v = new NChar(1);
    // COB:            02  TDATE08O  PIC X(8).
    public NChar tdate08o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler651 = new NChar(3);
    // COB:            02  TDESC08C    PICTURE X.
    public NChar tdesc08c = new NChar(1);
    // COB:            02  TDESC08P    PICTURE X.
    public NChar tdesc08p = new NChar(1);
    // COB:            02  TDESC08H    PICTURE X.
    public NChar tdesc08h = new NChar(1);
    // COB:            02  TDESC08V    PICTURE X.
    public NChar tdesc08v = new NChar(1);
    // COB:            02  TDESC08O  PIC X(26).
    public NChar tdesc08o = new NChar(26);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler657 = new NChar(3);
    // COB:            02  TAMT008C    PICTURE X.
    public NChar tamt008c = new NChar(1);
    // COB:            02  TAMT008P    PICTURE X.
    public NChar tamt008p = new NChar(1);
    // COB:            02  TAMT008H    PICTURE X.
    public NChar tamt008h = new NChar(1);
    // COB:            02  TAMT008V    PICTURE X.
    public NChar tamt008v = new NChar(1);
    // COB:            02  TAMT008O  PIC X(12).
    public NChar tamt008o = new NChar(12);
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
    // COB:            02  TRNID09C    PICTURE X.
    public NChar trnid09c = new NChar(1);
    // COB:            02  TRNID09P    PICTURE X.
    public NChar trnid09p = new NChar(1);
    // COB:            02  TRNID09H    PICTURE X.
    public NChar trnid09h = new NChar(1);
    // COB:            02  TRNID09V    PICTURE X.
    public NChar trnid09v = new NChar(1);
    // COB:            02  TRNID09O  PIC X(16).
    public NChar trnid09o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler675 = new NChar(3);
    // COB:            02  TDATE09C    PICTURE X.
    public NChar tdate09c = new NChar(1);
    // COB:            02  TDATE09P    PICTURE X.
    public NChar tdate09p = new NChar(1);
    // COB:            02  TDATE09H    PICTURE X.
    public NChar tdate09h = new NChar(1);
    // COB:            02  TDATE09V    PICTURE X.
    public NChar tdate09v = new NChar(1);
    // COB:            02  TDATE09O  PIC X(8).
    public NChar tdate09o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler681 = new NChar(3);
    // COB:            02  TDESC09C    PICTURE X.
    public NChar tdesc09c = new NChar(1);
    // COB:            02  TDESC09P    PICTURE X.
    public NChar tdesc09p = new NChar(1);
    // COB:            02  TDESC09H    PICTURE X.
    public NChar tdesc09h = new NChar(1);
    // COB:            02  TDESC09V    PICTURE X.
    public NChar tdesc09v = new NChar(1);
    // COB:            02  TDESC09O  PIC X(26).
    public NChar tdesc09o = new NChar(26);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler687 = new NChar(3);
    // COB:            02  TAMT009C    PICTURE X.
    public NChar tamt009c = new NChar(1);
    // COB:            02  TAMT009P    PICTURE X.
    public NChar tamt009p = new NChar(1);
    // COB:            02  TAMT009H    PICTURE X.
    public NChar tamt009h = new NChar(1);
    // COB:            02  TAMT009V    PICTURE X.
    public NChar tamt009v = new NChar(1);
    // COB:            02  TAMT009O  PIC X(12).
    public NChar tamt009o = new NChar(12);
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
    // COB:            02  TRNID10C    PICTURE X.
    public NChar trnid10c = new NChar(1);
    // COB:            02  TRNID10P    PICTURE X.
    public NChar trnid10p = new NChar(1);
    // COB:            02  TRNID10H    PICTURE X.
    public NChar trnid10h = new NChar(1);
    // COB:            02  TRNID10V    PICTURE X.
    public NChar trnid10v = new NChar(1);
    // COB:            02  TRNID10O  PIC X(16).
    public NChar trnid10o = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler705 = new NChar(3);
    // COB:            02  TDATE10C    PICTURE X.
    public NChar tdate10c = new NChar(1);
    // COB:            02  TDATE10P    PICTURE X.
    public NChar tdate10p = new NChar(1);
    // COB:            02  TDATE10H    PICTURE X.
    public NChar tdate10h = new NChar(1);
    // COB:            02  TDATE10V    PICTURE X.
    public NChar tdate10v = new NChar(1);
    // COB:            02  TDATE10O  PIC X(8).
    public NChar tdate10o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler711 = new NChar(3);
    // COB:            02  TDESC10C    PICTURE X.
    public NChar tdesc10c = new NChar(1);
    // COB:            02  TDESC10P    PICTURE X.
    public NChar tdesc10p = new NChar(1);
    // COB:            02  TDESC10H    PICTURE X.
    public NChar tdesc10h = new NChar(1);
    // COB:            02  TDESC10V    PICTURE X.
    public NChar tdesc10v = new NChar(1);
    // COB:            02  TDESC10O  PIC X(26).
    public NChar tdesc10o = new NChar(26);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler717 = new NChar(3);
    // COB:            02  TAMT010C    PICTURE X.
    public NChar tamt010c = new NChar(1);
    // COB:            02  TAMT010P    PICTURE X.
    public NChar tamt010p = new NChar(1);
    // COB:            02  TAMT010H    PICTURE X.
    public NChar tamt010h = new NChar(1);
    // COB:            02  TAMT010V    PICTURE X.
    public NChar tamt010v = new NChar(1);
    // COB:            02  TAMT010O  PIC X(12).
    public NChar tamt010o = new NChar(12);
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

  public Cotrn0ao cotrn0ao = (Cotrn0ao) new Cotrn0ao().redefines(cotrn0ai);
}
