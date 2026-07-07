package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Comen01 extends NGroup {

  // COB:        01  COMEN1AI.
  public static class Comen1ai extends NGroup {
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
    // COB:            02  OPTN001L    COMP  PIC  S9(4).
    public NInt16 optn001l = new NInt16();
    // COB:            02  OPTN001F    PICTURE X.
    public NChar optn001f = new NChar(1);

    // COB:            02  FILLER REDEFINES OPTN001F.
    public static class Filler57 extends NGroup {
      // COB:              03 OPTN001A    PICTURE X.
      public NChar optn001a = new NChar(1);
    }

    public Filler57 filler57 = (Filler57) new Filler57().redefines(optn001f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler59 = new NChar(4);
    // COB:            02  OPTN001I  PIC X(40).
    public NChar optn001i = new NChar(40);
    // COB:            02  OPTN002L    COMP  PIC  S9(4).
    public NInt16 optn002l = new NInt16();
    // COB:            02  OPTN002F    PICTURE X.
    public NChar optn002f = new NChar(1);

    // COB:            02  FILLER REDEFINES OPTN002F.
    public static class Filler63 extends NGroup {
      // COB:              03 OPTN002A    PICTURE X.
      public NChar optn002a = new NChar(1);
    }

    public Filler63 filler63 = (Filler63) new Filler63().redefines(optn002f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler65 = new NChar(4);
    // COB:            02  OPTN002I  PIC X(40).
    public NChar optn002i = new NChar(40);
    // COB:            02  OPTN003L    COMP  PIC  S9(4).
    public NInt16 optn003l = new NInt16();
    // COB:            02  OPTN003F    PICTURE X.
    public NChar optn003f = new NChar(1);

    // COB:            02  FILLER REDEFINES OPTN003F.
    public static class Filler69 extends NGroup {
      // COB:              03 OPTN003A    PICTURE X.
      public NChar optn003a = new NChar(1);
    }

    public Filler69 filler69 = (Filler69) new Filler69().redefines(optn003f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler71 = new NChar(4);
    // COB:            02  OPTN003I  PIC X(40).
    public NChar optn003i = new NChar(40);
    // COB:            02  OPTN004L    COMP  PIC  S9(4).
    public NInt16 optn004l = new NInt16();
    // COB:            02  OPTN004F    PICTURE X.
    public NChar optn004f = new NChar(1);

    // COB:            02  FILLER REDEFINES OPTN004F.
    public static class Filler75 extends NGroup {
      // COB:              03 OPTN004A    PICTURE X.
      public NChar optn004a = new NChar(1);
    }

    public Filler75 filler75 = (Filler75) new Filler75().redefines(optn004f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler77 = new NChar(4);
    // COB:            02  OPTN004I  PIC X(40).
    public NChar optn004i = new NChar(40);
    // COB:            02  OPTN005L    COMP  PIC  S9(4).
    public NInt16 optn005l = new NInt16();
    // COB:            02  OPTN005F    PICTURE X.
    public NChar optn005f = new NChar(1);

    // COB:            02  FILLER REDEFINES OPTN005F.
    public static class Filler81 extends NGroup {
      // COB:              03 OPTN005A    PICTURE X.
      public NChar optn005a = new NChar(1);
    }

    public Filler81 filler81 = (Filler81) new Filler81().redefines(optn005f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler83 = new NChar(4);
    // COB:            02  OPTN005I  PIC X(40).
    public NChar optn005i = new NChar(40);
    // COB:            02  OPTN006L    COMP  PIC  S9(4).
    public NInt16 optn006l = new NInt16();
    // COB:            02  OPTN006F    PICTURE X.
    public NChar optn006f = new NChar(1);

    // COB:            02  FILLER REDEFINES OPTN006F.
    public static class Filler87 extends NGroup {
      // COB:              03 OPTN006A    PICTURE X.
      public NChar optn006a = new NChar(1);
    }

    public Filler87 filler87 = (Filler87) new Filler87().redefines(optn006f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler89 = new NChar(4);
    // COB:            02  OPTN006I  PIC X(40).
    public NChar optn006i = new NChar(40);
    // COB:            02  OPTN007L    COMP  PIC  S9(4).
    public NInt16 optn007l = new NInt16();
    // COB:            02  OPTN007F    PICTURE X.
    public NChar optn007f = new NChar(1);

    // COB:            02  FILLER REDEFINES OPTN007F.
    public static class Filler93 extends NGroup {
      // COB:              03 OPTN007A    PICTURE X.
      public NChar optn007a = new NChar(1);
    }

    public Filler93 filler93 = (Filler93) new Filler93().redefines(optn007f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler95 = new NChar(4);
    // COB:            02  OPTN007I  PIC X(40).
    public NChar optn007i = new NChar(40);
    // COB:            02  OPTN008L    COMP  PIC  S9(4).
    public NInt16 optn008l = new NInt16();
    // COB:            02  OPTN008F    PICTURE X.
    public NChar optn008f = new NChar(1);

    // COB:            02  FILLER REDEFINES OPTN008F.
    public static class Filler99 extends NGroup {
      // COB:              03 OPTN008A    PICTURE X.
      public NChar optn008a = new NChar(1);
    }

    public Filler99 filler99 = (Filler99) new Filler99().redefines(optn008f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler101 = new NChar(4);
    // COB:            02  OPTN008I  PIC X(40).
    public NChar optn008i = new NChar(40);
    // COB:            02  OPTN009L    COMP  PIC  S9(4).
    public NInt16 optn009l = new NInt16();
    // COB:            02  OPTN009F    PICTURE X.
    public NChar optn009f = new NChar(1);

    // COB:            02  FILLER REDEFINES OPTN009F.
    public static class Filler105 extends NGroup {
      // COB:              03 OPTN009A    PICTURE X.
      public NChar optn009a = new NChar(1);
    }

    public Filler105 filler105 = (Filler105) new Filler105().redefines(optn009f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler107 = new NChar(4);
    // COB:            02  OPTN009I  PIC X(40).
    public NChar optn009i = new NChar(40);
    // COB:            02  OPTN010L    COMP  PIC  S9(4).
    public NInt16 optn010l = new NInt16();
    // COB:            02  OPTN010F    PICTURE X.
    public NChar optn010f = new NChar(1);

    // COB:            02  FILLER REDEFINES OPTN010F.
    public static class Filler111 extends NGroup {
      // COB:              03 OPTN010A    PICTURE X.
      public NChar optn010a = new NChar(1);
    }

    public Filler111 filler111 = (Filler111) new Filler111().redefines(optn010f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler113 = new NChar(4);
    // COB:            02  OPTN010I  PIC X(40).
    public NChar optn010i = new NChar(40);
    // COB:            02  OPTN011L    COMP  PIC  S9(4).
    public NInt16 optn011l = new NInt16();
    // COB:            02  OPTN011F    PICTURE X.
    public NChar optn011f = new NChar(1);

    // COB:            02  FILLER REDEFINES OPTN011F.
    public static class Filler117 extends NGroup {
      // COB:              03 OPTN011A    PICTURE X.
      public NChar optn011a = new NChar(1);
    }

    public Filler117 filler117 = (Filler117) new Filler117().redefines(optn011f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler119 = new NChar(4);
    // COB:            02  OPTN011I  PIC X(40).
    public NChar optn011i = new NChar(40);
    // COB:            02  OPTN012L    COMP  PIC  S9(4).
    public NInt16 optn012l = new NInt16();
    // COB:            02  OPTN012F    PICTURE X.
    public NChar optn012f = new NChar(1);

    // COB:            02  FILLER REDEFINES OPTN012F.
    public static class Filler123 extends NGroup {
      // COB:              03 OPTN012A    PICTURE X.
      public NChar optn012a = new NChar(1);
    }

    public Filler123 filler123 = (Filler123) new Filler123().redefines(optn012f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler125 = new NChar(4);
    // COB:            02  OPTN012I  PIC X(40).
    public NChar optn012i = new NChar(40);
    // COB:            02  OPTIONL    COMP  PIC  S9(4).
    public NInt16 optionl = new NInt16();
    // COB:            02  OPTIONF    PICTURE X.
    public NChar optionf = new NChar(1);

    // COB:            02  FILLER REDEFINES OPTIONF.
    public static class Filler129 extends NGroup {
      // COB:              03 OPTIONA    PICTURE X.
      public NChar optiona = new NChar(1);
    }

    public Filler129 filler129 = (Filler129) new Filler129().redefines(optionf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler131 = new NChar(4);
    // COB:            02  OPTIONI  PIC X(2).
    public NChar optioni = new NChar(2);
    // COB:            02  ERRMSGL    COMP  PIC  S9(4).
    public NInt16 errmsgl = new NInt16();
    // COB:            02  ERRMSGF    PICTURE X.
    public NChar errmsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES ERRMSGF.
    public static class Filler135 extends NGroup {
      // COB:              03 ERRMSGA    PICTURE X.
      public NChar errmsga = new NChar(1);
    }

    public Filler135 filler135 = (Filler135) new Filler135().redefines(errmsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler137 = new NChar(4);
    // COB:            02  ERRMSGI  PIC X(78).
    public NChar errmsgi = new NChar(78);
  }

  public Comen1ai comen1ai = new Comen1ai();

  // COB:        01  COMEN1AO REDEFINES COMEN1AI.
  public static class Comen1ao extends NGroup {
    // COB:            02  FILLER PIC X(12).
    public NChar filler140 = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler141 = new NChar(3);
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
    public NChar filler147 = new NChar(3);
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
    public NChar filler153 = new NChar(3);
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
    public NChar filler159 = new NChar(3);
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
    public NChar filler165 = new NChar(3);
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
    public NChar filler171 = new NChar(3);
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
    public NChar filler177 = new NChar(3);
    // COB:            02  OPTN001C    PICTURE X.
    public NChar optn001c = new NChar(1);
    // COB:            02  OPTN001P    PICTURE X.
    public NChar optn001p = new NChar(1);
    // COB:            02  OPTN001H    PICTURE X.
    public NChar optn001h = new NChar(1);
    // COB:            02  OPTN001V    PICTURE X.
    public NChar optn001v = new NChar(1);
    // COB:            02  OPTN001O  PIC X(40).
    public NChar optn001o = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler183 = new NChar(3);
    // COB:            02  OPTN002C    PICTURE X.
    public NChar optn002c = new NChar(1);
    // COB:            02  OPTN002P    PICTURE X.
    public NChar optn002p = new NChar(1);
    // COB:            02  OPTN002H    PICTURE X.
    public NChar optn002h = new NChar(1);
    // COB:            02  OPTN002V    PICTURE X.
    public NChar optn002v = new NChar(1);
    // COB:            02  OPTN002O  PIC X(40).
    public NChar optn002o = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler189 = new NChar(3);
    // COB:            02  OPTN003C    PICTURE X.
    public NChar optn003c = new NChar(1);
    // COB:            02  OPTN003P    PICTURE X.
    public NChar optn003p = new NChar(1);
    // COB:            02  OPTN003H    PICTURE X.
    public NChar optn003h = new NChar(1);
    // COB:            02  OPTN003V    PICTURE X.
    public NChar optn003v = new NChar(1);
    // COB:            02  OPTN003O  PIC X(40).
    public NChar optn003o = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler195 = new NChar(3);
    // COB:            02  OPTN004C    PICTURE X.
    public NChar optn004c = new NChar(1);
    // COB:            02  OPTN004P    PICTURE X.
    public NChar optn004p = new NChar(1);
    // COB:            02  OPTN004H    PICTURE X.
    public NChar optn004h = new NChar(1);
    // COB:            02  OPTN004V    PICTURE X.
    public NChar optn004v = new NChar(1);
    // COB:            02  OPTN004O  PIC X(40).
    public NChar optn004o = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler201 = new NChar(3);
    // COB:            02  OPTN005C    PICTURE X.
    public NChar optn005c = new NChar(1);
    // COB:            02  OPTN005P    PICTURE X.
    public NChar optn005p = new NChar(1);
    // COB:            02  OPTN005H    PICTURE X.
    public NChar optn005h = new NChar(1);
    // COB:            02  OPTN005V    PICTURE X.
    public NChar optn005v = new NChar(1);
    // COB:            02  OPTN005O  PIC X(40).
    public NChar optn005o = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler207 = new NChar(3);
    // COB:            02  OPTN006C    PICTURE X.
    public NChar optn006c = new NChar(1);
    // COB:            02  OPTN006P    PICTURE X.
    public NChar optn006p = new NChar(1);
    // COB:            02  OPTN006H    PICTURE X.
    public NChar optn006h = new NChar(1);
    // COB:            02  OPTN006V    PICTURE X.
    public NChar optn006v = new NChar(1);
    // COB:            02  OPTN006O  PIC X(40).
    public NChar optn006o = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler213 = new NChar(3);
    // COB:            02  OPTN007C    PICTURE X.
    public NChar optn007c = new NChar(1);
    // COB:            02  OPTN007P    PICTURE X.
    public NChar optn007p = new NChar(1);
    // COB:            02  OPTN007H    PICTURE X.
    public NChar optn007h = new NChar(1);
    // COB:            02  OPTN007V    PICTURE X.
    public NChar optn007v = new NChar(1);
    // COB:            02  OPTN007O  PIC X(40).
    public NChar optn007o = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler219 = new NChar(3);
    // COB:            02  OPTN008C    PICTURE X.
    public NChar optn008c = new NChar(1);
    // COB:            02  OPTN008P    PICTURE X.
    public NChar optn008p = new NChar(1);
    // COB:            02  OPTN008H    PICTURE X.
    public NChar optn008h = new NChar(1);
    // COB:            02  OPTN008V    PICTURE X.
    public NChar optn008v = new NChar(1);
    // COB:            02  OPTN008O  PIC X(40).
    public NChar optn008o = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler225 = new NChar(3);
    // COB:            02  OPTN009C    PICTURE X.
    public NChar optn009c = new NChar(1);
    // COB:            02  OPTN009P    PICTURE X.
    public NChar optn009p = new NChar(1);
    // COB:            02  OPTN009H    PICTURE X.
    public NChar optn009h = new NChar(1);
    // COB:            02  OPTN009V    PICTURE X.
    public NChar optn009v = new NChar(1);
    // COB:            02  OPTN009O  PIC X(40).
    public NChar optn009o = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler231 = new NChar(3);
    // COB:            02  OPTN010C    PICTURE X.
    public NChar optn010c = new NChar(1);
    // COB:            02  OPTN010P    PICTURE X.
    public NChar optn010p = new NChar(1);
    // COB:            02  OPTN010H    PICTURE X.
    public NChar optn010h = new NChar(1);
    // COB:            02  OPTN010V    PICTURE X.
    public NChar optn010v = new NChar(1);
    // COB:            02  OPTN010O  PIC X(40).
    public NChar optn010o = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler237 = new NChar(3);
    // COB:            02  OPTN011C    PICTURE X.
    public NChar optn011c = new NChar(1);
    // COB:            02  OPTN011P    PICTURE X.
    public NChar optn011p = new NChar(1);
    // COB:            02  OPTN011H    PICTURE X.
    public NChar optn011h = new NChar(1);
    // COB:            02  OPTN011V    PICTURE X.
    public NChar optn011v = new NChar(1);
    // COB:            02  OPTN011O  PIC X(40).
    public NChar optn011o = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler243 = new NChar(3);
    // COB:            02  OPTN012C    PICTURE X.
    public NChar optn012c = new NChar(1);
    // COB:            02  OPTN012P    PICTURE X.
    public NChar optn012p = new NChar(1);
    // COB:            02  OPTN012H    PICTURE X.
    public NChar optn012h = new NChar(1);
    // COB:            02  OPTN012V    PICTURE X.
    public NChar optn012v = new NChar(1);
    // COB:            02  OPTN012O  PIC X(40).
    public NChar optn012o = new NChar(40);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler249 = new NChar(3);
    // COB:            02  OPTIONC    PICTURE X.
    public NChar optionc = new NChar(1);
    // COB:            02  OPTIONP    PICTURE X.
    public NChar optionp = new NChar(1);
    // COB:            02  OPTIONH    PICTURE X.
    public NChar optionh = new NChar(1);
    // COB:            02  OPTIONV    PICTURE X.
    public NChar optionv = new NChar(1);
    // COB:            02  OPTIONO  PIC X(2).
    public NChar optiono = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler255 = new NChar(3);
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

  public Comen1ao comen1ao = (Comen1ao) new Comen1ao().redefines(comen1ai);
}
