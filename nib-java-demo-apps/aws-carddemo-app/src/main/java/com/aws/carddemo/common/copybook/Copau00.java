package com.aws.carddemo.common.copybook;

import com.nib.commons.storage.*;

public class Copau00 extends NGroup {

  // COB:        01  COPAU0AI.
  public class Copau0ai extends NGroup {
    // COB:            02  FILLER PIC X(12).
    public NChar filler18 = new NChar(12);
    // COB:            02  TRNNAMEL    COMP  PIC  S9(4).
    public NInt16 trnnamel = new NInt16();
    // COB:            02  TRNNAMEF    PICTURE X.
    public NChar trnnamef = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNNAMEF.
    public class Filler21 extends NGroup {
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
    public class Filler27 extends NGroup {
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
    public class Filler33 extends NGroup {
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
    public class Filler39 extends NGroup {
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
    public class Filler45 extends NGroup {
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
    public class Filler51 extends NGroup {
      // COB:              03 CURTIMEA    PICTURE X.
      public NChar curtimea = new NChar(1);
    }

    public Filler51 filler51 = (Filler51) new Filler51().redefines(curtimef);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler53 = new NChar(4);
    // COB:            02  CURTIMEI  PIC X(8).
    public NChar curtimei = new NChar(8);
    // COB:            02  ACCTIDL    COMP  PIC  S9(4).
    public NInt16 acctidl = new NInt16();
    // COB:            02  ACCTIDF    PICTURE X.
    public NChar acctidf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACCTIDF.
    public class Filler57 extends NGroup {
      // COB:              03 ACCTIDA    PICTURE X.
      public NChar acctida = new NChar(1);
    }

    public Filler57 filler57 = (Filler57) new Filler57().redefines(acctidf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler59 = new NChar(4);
    // COB:            02  ACCTIDI  PIC X(11).
    public NChar acctidi = new NChar(11);
    // COB:            02  CNAMEL    COMP  PIC  S9(4).
    public NInt16 cnamel = new NInt16();
    // COB:            02  CNAMEF    PICTURE X.
    public NChar cnamef = new NChar(1);

    // COB:            02  FILLER REDEFINES CNAMEF.
    public class Filler63 extends NGroup {
      // COB:              03 CNAMEA    PICTURE X.
      public NChar cnamea = new NChar(1);
    }

    public Filler63 filler63 = (Filler63) new Filler63().redefines(cnamef);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler65 = new NChar(4);
    // COB:            02  CNAMEI  PIC X(25).
    public NChar cnamei = new NChar(25);
    // COB:            02  CUSTIDL    COMP  PIC  S9(4).
    public NInt16 custidl = new NInt16();
    // COB:            02  CUSTIDF    PICTURE X.
    public NChar custidf = new NChar(1);

    // COB:            02  FILLER REDEFINES CUSTIDF.
    public class Filler69 extends NGroup {
      // COB:              03 CUSTIDA    PICTURE X.
      public NChar custida = new NChar(1);
    }

    public Filler69 filler69 = (Filler69) new Filler69().redefines(custidf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler71 = new NChar(4);
    // COB:            02  CUSTIDI  PIC X(9).
    public NChar custidi = new NChar(9);
    // COB:            02  ADDR001L    COMP  PIC  S9(4).
    public NInt16 addr001l = new NInt16();
    // COB:            02  ADDR001F    PICTURE X.
    public NChar addr001f = new NChar(1);

    // COB:            02  FILLER REDEFINES ADDR001F.
    public class Filler75 extends NGroup {
      // COB:              03 ADDR001A    PICTURE X.
      public NChar addr001a = new NChar(1);
    }

    public Filler75 filler75 = (Filler75) new Filler75().redefines(addr001f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler77 = new NChar(4);
    // COB:            02  ADDR001I  PIC X(25).
    public NChar addr001i = new NChar(25);
    // COB:            02  ACCSTATL    COMP  PIC  S9(4).
    public NInt16 accstatl = new NInt16();
    // COB:            02  ACCSTATF    PICTURE X.
    public NChar accstatf = new NChar(1);

    // COB:            02  FILLER REDEFINES ACCSTATF.
    public class Filler81 extends NGroup {
      // COB:              03 ACCSTATA    PICTURE X.
      public NChar accstata = new NChar(1);
    }

    public Filler81 filler81 = (Filler81) new Filler81().redefines(accstatf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler83 = new NChar(4);
    // COB:            02  ACCSTATI  PIC X(1).
    public NChar accstati = new NChar(1);
    // COB:            02  ADDR002L    COMP  PIC  S9(4).
    public NInt16 addr002l = new NInt16();
    // COB:            02  ADDR002F    PICTURE X.
    public NChar addr002f = new NChar(1);

    // COB:            02  FILLER REDEFINES ADDR002F.
    public class Filler87 extends NGroup {
      // COB:              03 ADDR002A    PICTURE X.
      public NChar addr002a = new NChar(1);
    }

    public Filler87 filler87 = (Filler87) new Filler87().redefines(addr002f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler89 = new NChar(4);
    // COB:            02  ADDR002I  PIC X(25).
    public NChar addr002i = new NChar(25);
    // COB:            02  PHONE1L    COMP  PIC  S9(4).
    public NInt16 phone1l = new NInt16();
    // COB:            02  PHONE1F    PICTURE X.
    public NChar phone1f = new NChar(1);

    // COB:            02  FILLER REDEFINES PHONE1F.
    public class Filler93 extends NGroup {
      // COB:              03 PHONE1A    PICTURE X.
      public NChar phone1a = new NChar(1);
    }

    public Filler93 filler93 = (Filler93) new Filler93().redefines(phone1f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler95 = new NChar(4);
    // COB:            02  PHONE1I  PIC X(13).
    public NChar phone1i = new NChar(13);
    // COB:            02  APPRCNTL    COMP  PIC  S9(4).
    public NInt16 apprcntl = new NInt16();
    // COB:            02  APPRCNTF    PICTURE X.
    public NChar apprcntf = new NChar(1);

    // COB:            02  FILLER REDEFINES APPRCNTF.
    public class Filler99 extends NGroup {
      // COB:              03 APPRCNTA    PICTURE X.
      public NChar apprcnta = new NChar(1);
    }

    public Filler99 filler99 = (Filler99) new Filler99().redefines(apprcntf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler101 = new NChar(4);
    // COB:            02  APPRCNTI  PIC X(3).
    public NChar apprcnti = new NChar(3);
    // COB:            02  DECLCNTL    COMP  PIC  S9(4).
    public NInt16 declcntl = new NInt16();
    // COB:            02  DECLCNTF    PICTURE X.
    public NChar declcntf = new NChar(1);

    // COB:            02  FILLER REDEFINES DECLCNTF.
    public class Filler105 extends NGroup {
      // COB:              03 DECLCNTA    PICTURE X.
      public NChar declcnta = new NChar(1);
    }

    public Filler105 filler105 = (Filler105) new Filler105().redefines(declcntf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler107 = new NChar(4);
    // COB:            02  DECLCNTI  PIC X(3).
    public NChar declcnti = new NChar(3);
    // COB:            02  CREDLIML    COMP  PIC  S9(4).
    public NInt16 credliml = new NInt16();
    // COB:            02  CREDLIMF    PICTURE X.
    public NChar credlimf = new NChar(1);

    // COB:            02  FILLER REDEFINES CREDLIMF.
    public class Filler111 extends NGroup {
      // COB:              03 CREDLIMA    PICTURE X.
      public NChar credlima = new NChar(1);
    }

    public Filler111 filler111 = (Filler111) new Filler111().redefines(credlimf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler113 = new NChar(4);
    // COB:            02  CREDLIMI  PIC X(12).
    public NChar credlimi = new NChar(12);
    // COB:            02  CASHLIML    COMP  PIC  S9(4).
    public NInt16 cashliml = new NInt16();
    // COB:            02  CASHLIMF    PICTURE X.
    public NChar cashlimf = new NChar(1);

    // COB:            02  FILLER REDEFINES CASHLIMF.
    public class Filler117 extends NGroup {
      // COB:              03 CASHLIMA    PICTURE X.
      public NChar cashlima = new NChar(1);
    }

    public Filler117 filler117 = (Filler117) new Filler117().redefines(cashlimf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler119 = new NChar(4);
    // COB:            02  CASHLIMI  PIC X(9).
    public NChar cashlimi = new NChar(9);
    // COB:            02  APPRAMTL    COMP  PIC  S9(4).
    public NInt16 appramtl = new NInt16();
    // COB:            02  APPRAMTF    PICTURE X.
    public NChar appramtf = new NChar(1);

    // COB:            02  FILLER REDEFINES APPRAMTF.
    public class Filler123 extends NGroup {
      // COB:              03 APPRAMTA    PICTURE X.
      public NChar appramta = new NChar(1);
    }

    public Filler123 filler123 = (Filler123) new Filler123().redefines(appramtf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler125 = new NChar(4);
    // COB:            02  APPRAMTI  PIC X(10).
    public NChar appramti = new NChar(10);
    // COB:            02  CREDBALL    COMP  PIC  S9(4).
    public NInt16 credball = new NInt16();
    // COB:            02  CREDBALF    PICTURE X.
    public NChar credbalf = new NChar(1);

    // COB:            02  FILLER REDEFINES CREDBALF.
    public class Filler129 extends NGroup {
      // COB:              03 CREDBALA    PICTURE X.
      public NChar credbala = new NChar(1);
    }

    public Filler129 filler129 = (Filler129) new Filler129().redefines(credbalf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler131 = new NChar(4);
    // COB:            02  CREDBALI  PIC X(12).
    public NChar credbali = new NChar(12);
    // COB:            02  CASHBALL    COMP  PIC  S9(4).
    public NInt16 cashball = new NInt16();
    // COB:            02  CASHBALF    PICTURE X.
    public NChar cashbalf = new NChar(1);

    // COB:            02  FILLER REDEFINES CASHBALF.
    public class Filler135 extends NGroup {
      // COB:              03 CASHBALA    PICTURE X.
      public NChar cashbala = new NChar(1);
    }

    public Filler135 filler135 = (Filler135) new Filler135().redefines(cashbalf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler137 = new NChar(4);
    // COB:            02  CASHBALI  PIC X(9).
    public NChar cashbali = new NChar(9);
    // COB:            02  DECLAMTL    COMP  PIC  S9(4).
    public NInt16 declamtl = new NInt16();
    // COB:            02  DECLAMTF    PICTURE X.
    public NChar declamtf = new NChar(1);

    // COB:            02  FILLER REDEFINES DECLAMTF.
    public class Filler141 extends NGroup {
      // COB:              03 DECLAMTA    PICTURE X.
      public NChar declamta = new NChar(1);
    }

    public Filler141 filler141 = (Filler141) new Filler141().redefines(declamtf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler143 = new NChar(4);
    // COB:            02  DECLAMTI  PIC X(10).
    public NChar declamti = new NChar(10);
    // COB:            02  SEL0001L    COMP  PIC  S9(4).
    public NInt16 sel0001l = new NInt16();
    // COB:            02  SEL0001F    PICTURE X.
    public NChar sel0001f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0001F.
    public class Filler147 extends NGroup {
      // COB:              03 SEL0001A    PICTURE X.
      public NChar sel0001a = new NChar(1);
    }

    public Filler147 filler147 = (Filler147) new Filler147().redefines(sel0001f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler149 = new NChar(4);
    // COB:            02  SEL0001I  PIC X(1).
    public NChar sel0001i = new NChar(1);
    // COB:            02  TRNID01L    COMP  PIC  S9(4).
    public NInt16 trnid01l = new NInt16();
    // COB:            02  TRNID01F    PICTURE X.
    public NChar trnid01f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID01F.
    public class Filler153 extends NGroup {
      // COB:              03 TRNID01A    PICTURE X.
      public NChar trnid01a = new NChar(1);
    }

    public Filler153 filler153 = (Filler153) new Filler153().redefines(trnid01f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler155 = new NChar(4);
    // COB:            02  TRNID01I  PIC X(16).
    public NChar trnid01i = new NChar(16);
    // COB:            02  PDATE01L    COMP  PIC  S9(4).
    public NInt16 pdate01l = new NInt16();
    // COB:            02  PDATE01F    PICTURE X.
    public NChar pdate01f = new NChar(1);

    // COB:            02  FILLER REDEFINES PDATE01F.
    public class Filler159 extends NGroup {
      // COB:              03 PDATE01A    PICTURE X.
      public NChar pdate01a = new NChar(1);
    }

    public Filler159 filler159 = (Filler159) new Filler159().redefines(pdate01f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler161 = new NChar(4);
    // COB:            02  PDATE01I  PIC X(8).
    public NChar pdate01i = new NChar(8);
    // COB:            02  PTIME01L    COMP  PIC  S9(4).
    public NInt16 ptime01l = new NInt16();
    // COB:            02  PTIME01F    PICTURE X.
    public NChar ptime01f = new NChar(1);

    // COB:            02  FILLER REDEFINES PTIME01F.
    public class Filler165 extends NGroup {
      // COB:              03 PTIME01A    PICTURE X.
      public NChar ptime01a = new NChar(1);
    }

    public Filler165 filler165 = (Filler165) new Filler165().redefines(ptime01f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler167 = new NChar(4);
    // COB:            02  PTIME01I  PIC X(8).
    public NChar ptime01i = new NChar(8);
    // COB:            02  PTYPE01L    COMP  PIC  S9(4).
    public NInt16 ptype01l = new NInt16();
    // COB:            02  PTYPE01F    PICTURE X.
    public NChar ptype01f = new NChar(1);

    // COB:            02  FILLER REDEFINES PTYPE01F.
    public class Filler171 extends NGroup {
      // COB:              03 PTYPE01A    PICTURE X.
      public NChar ptype01a = new NChar(1);
    }

    public Filler171 filler171 = (Filler171) new Filler171().redefines(ptype01f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler173 = new NChar(4);
    // COB:            02  PTYPE01I  PIC X(4).
    public NChar ptype01i = new NChar(4);
    // COB:            02  PAPRV01L    COMP  PIC  S9(4).
    public NInt16 paprv01l = new NInt16();
    // COB:            02  PAPRV01F    PICTURE X.
    public NChar paprv01f = new NChar(1);

    // COB:            02  FILLER REDEFINES PAPRV01F.
    public class Filler177 extends NGroup {
      // COB:              03 PAPRV01A    PICTURE X.
      public NChar paprv01a = new NChar(1);
    }

    public Filler177 filler177 = (Filler177) new Filler177().redefines(paprv01f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler179 = new NChar(4);
    // COB:            02  PAPRV01I  PIC X(1).
    public NChar paprv01i = new NChar(1);
    // COB:            02  PSTAT01L    COMP  PIC  S9(4).
    public NInt16 pstat01l = new NInt16();
    // COB:            02  PSTAT01F    PICTURE X.
    public NChar pstat01f = new NChar(1);

    // COB:            02  FILLER REDEFINES PSTAT01F.
    public class Filler183 extends NGroup {
      // COB:              03 PSTAT01A    PICTURE X.
      public NChar pstat01a = new NChar(1);
    }

    public Filler183 filler183 = (Filler183) new Filler183().redefines(pstat01f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler185 = new NChar(4);
    // COB:            02  PSTAT01I  PIC X(1).
    public NChar pstat01i = new NChar(1);
    // COB:            02  PAMT001L    COMP  PIC  S9(4).
    public NInt16 pamt001l = new NInt16();
    // COB:            02  PAMT001F    PICTURE X.
    public NChar pamt001f = new NChar(1);

    // COB:            02  FILLER REDEFINES PAMT001F.
    public class Filler189 extends NGroup {
      // COB:              03 PAMT001A    PICTURE X.
      public NChar pamt001a = new NChar(1);
    }

    public Filler189 filler189 = (Filler189) new Filler189().redefines(pamt001f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler191 = new NChar(4);
    // COB:            02  PAMT001I  PIC X(12).
    public NChar pamt001i = new NChar(12);
    // COB:            02  SEL0002L    COMP  PIC  S9(4).
    public NInt16 sel0002l = new NInt16();
    // COB:            02  SEL0002F    PICTURE X.
    public NChar sel0002f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0002F.
    public class Filler195 extends NGroup {
      // COB:              03 SEL0002A    PICTURE X.
      public NChar sel0002a = new NChar(1);
    }

    public Filler195 filler195 = (Filler195) new Filler195().redefines(sel0002f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler197 = new NChar(4);
    // COB:            02  SEL0002I  PIC X(1).
    public NChar sel0002i = new NChar(1);
    // COB:            02  TRNID02L    COMP  PIC  S9(4).
    public NInt16 trnid02l = new NInt16();
    // COB:            02  TRNID02F    PICTURE X.
    public NChar trnid02f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID02F.
    public class Filler201 extends NGroup {
      // COB:              03 TRNID02A    PICTURE X.
      public NChar trnid02a = new NChar(1);
    }

    public Filler201 filler201 = (Filler201) new Filler201().redefines(trnid02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler203 = new NChar(4);
    // COB:            02  TRNID02I  PIC X(16).
    public NChar trnid02i = new NChar(16);
    // COB:            02  PDATE02L    COMP  PIC  S9(4).
    public NInt16 pdate02l = new NInt16();
    // COB:            02  PDATE02F    PICTURE X.
    public NChar pdate02f = new NChar(1);

    // COB:            02  FILLER REDEFINES PDATE02F.
    public class Filler207 extends NGroup {
      // COB:              03 PDATE02A    PICTURE X.
      public NChar pdate02a = new NChar(1);
    }

    public Filler207 filler207 = (Filler207) new Filler207().redefines(pdate02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler209 = new NChar(4);
    // COB:            02  PDATE02I  PIC X(8).
    public NChar pdate02i = new NChar(8);
    // COB:            02  PTIME02L    COMP  PIC  S9(4).
    public NInt16 ptime02l = new NInt16();
    // COB:            02  PTIME02F    PICTURE X.
    public NChar ptime02f = new NChar(1);

    // COB:            02  FILLER REDEFINES PTIME02F.
    public class Filler213 extends NGroup {
      // COB:              03 PTIME02A    PICTURE X.
      public NChar ptime02a = new NChar(1);
    }

    public Filler213 filler213 = (Filler213) new Filler213().redefines(ptime02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler215 = new NChar(4);
    // COB:            02  PTIME02I  PIC X(8).
    public NChar ptime02i = new NChar(8);
    // COB:            02  PTYPE02L    COMP  PIC  S9(4).
    public NInt16 ptype02l = new NInt16();
    // COB:            02  PTYPE02F    PICTURE X.
    public NChar ptype02f = new NChar(1);

    // COB:            02  FILLER REDEFINES PTYPE02F.
    public class Filler219 extends NGroup {
      // COB:              03 PTYPE02A    PICTURE X.
      public NChar ptype02a = new NChar(1);
    }

    public Filler219 filler219 = (Filler219) new Filler219().redefines(ptype02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler221 = new NChar(4);
    // COB:            02  PTYPE02I  PIC X(4).
    public NChar ptype02i = new NChar(4);
    // COB:            02  PAPRV02L    COMP  PIC  S9(4).
    public NInt16 paprv02l = new NInt16();
    // COB:            02  PAPRV02F    PICTURE X.
    public NChar paprv02f = new NChar(1);

    // COB:            02  FILLER REDEFINES PAPRV02F.
    public class Filler225 extends NGroup {
      // COB:              03 PAPRV02A    PICTURE X.
      public NChar paprv02a = new NChar(1);
    }

    public Filler225 filler225 = (Filler225) new Filler225().redefines(paprv02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler227 = new NChar(4);
    // COB:            02  PAPRV02I  PIC X(1).
    public NChar paprv02i = new NChar(1);
    // COB:            02  PSTAT02L    COMP  PIC  S9(4).
    public NInt16 pstat02l = new NInt16();
    // COB:            02  PSTAT02F    PICTURE X.
    public NChar pstat02f = new NChar(1);

    // COB:            02  FILLER REDEFINES PSTAT02F.
    public class Filler231 extends NGroup {
      // COB:              03 PSTAT02A    PICTURE X.
      public NChar pstat02a = new NChar(1);
    }

    public Filler231 filler231 = (Filler231) new Filler231().redefines(pstat02f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler233 = new NChar(4);
    // COB:            02  PSTAT02I  PIC X(1).
    public NChar pstat02i = new NChar(1);
    // COB:            02  PAMT002L    COMP  PIC  S9(4).
    public NInt16 pamt002l = new NInt16();
    // COB:            02  PAMT002F    PICTURE X.
    public NChar pamt002f = new NChar(1);

    // COB:            02  FILLER REDEFINES PAMT002F.
    public class Filler237 extends NGroup {
      // COB:              03 PAMT002A    PICTURE X.
      public NChar pamt002a = new NChar(1);
    }

    public Filler237 filler237 = (Filler237) new Filler237().redefines(pamt002f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler239 = new NChar(4);
    // COB:            02  PAMT002I  PIC X(12).
    public NChar pamt002i = new NChar(12);
    // COB:            02  SEL0003L    COMP  PIC  S9(4).
    public NInt16 sel0003l = new NInt16();
    // COB:            02  SEL0003F    PICTURE X.
    public NChar sel0003f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0003F.
    public class Filler243 extends NGroup {
      // COB:              03 SEL0003A    PICTURE X.
      public NChar sel0003a = new NChar(1);
    }

    public Filler243 filler243 = (Filler243) new Filler243().redefines(sel0003f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler245 = new NChar(4);
    // COB:            02  SEL0003I  PIC X(1).
    public NChar sel0003i = new NChar(1);
    // COB:            02  TRNID03L    COMP  PIC  S9(4).
    public NInt16 trnid03l = new NInt16();
    // COB:            02  TRNID03F    PICTURE X.
    public NChar trnid03f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID03F.
    public class Filler249 extends NGroup {
      // COB:              03 TRNID03A    PICTURE X.
      public NChar trnid03a = new NChar(1);
    }

    public Filler249 filler249 = (Filler249) new Filler249().redefines(trnid03f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler251 = new NChar(4);
    // COB:            02  TRNID03I  PIC X(16).
    public NChar trnid03i = new NChar(16);
    // COB:            02  PDATE03L    COMP  PIC  S9(4).
    public NInt16 pdate03l = new NInt16();
    // COB:            02  PDATE03F    PICTURE X.
    public NChar pdate03f = new NChar(1);

    // COB:            02  FILLER REDEFINES PDATE03F.
    public class Filler255 extends NGroup {
      // COB:              03 PDATE03A    PICTURE X.
      public NChar pdate03a = new NChar(1);
    }

    public Filler255 filler255 = (Filler255) new Filler255().redefines(pdate03f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler257 = new NChar(4);
    // COB:            02  PDATE03I  PIC X(8).
    public NChar pdate03i = new NChar(8);
    // COB:            02  PTIME03L    COMP  PIC  S9(4).
    public NInt16 ptime03l = new NInt16();
    // COB:            02  PTIME03F    PICTURE X.
    public NChar ptime03f = new NChar(1);

    // COB:            02  FILLER REDEFINES PTIME03F.
    public class Filler261 extends NGroup {
      // COB:              03 PTIME03A    PICTURE X.
      public NChar ptime03a = new NChar(1);
    }

    public Filler261 filler261 = (Filler261) new Filler261().redefines(ptime03f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler263 = new NChar(4);
    // COB:            02  PTIME03I  PIC X(8).
    public NChar ptime03i = new NChar(8);
    // COB:            02  PTYPE03L    COMP  PIC  S9(4).
    public NInt16 ptype03l = new NInt16();
    // COB:            02  PTYPE03F    PICTURE X.
    public NChar ptype03f = new NChar(1);

    // COB:            02  FILLER REDEFINES PTYPE03F.
    public class Filler267 extends NGroup {
      // COB:              03 PTYPE03A    PICTURE X.
      public NChar ptype03a = new NChar(1);
    }

    public Filler267 filler267 = (Filler267) new Filler267().redefines(ptype03f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler269 = new NChar(4);
    // COB:            02  PTYPE03I  PIC X(4).
    public NChar ptype03i = new NChar(4);
    // COB:            02  PAPRV03L    COMP  PIC  S9(4).
    public NInt16 paprv03l = new NInt16();
    // COB:            02  PAPRV03F    PICTURE X.
    public NChar paprv03f = new NChar(1);

    // COB:            02  FILLER REDEFINES PAPRV03F.
    public class Filler273 extends NGroup {
      // COB:              03 PAPRV03A    PICTURE X.
      public NChar paprv03a = new NChar(1);
    }

    public Filler273 filler273 = (Filler273) new Filler273().redefines(paprv03f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler275 = new NChar(4);
    // COB:            02  PAPRV03I  PIC X(1).
    public NChar paprv03i = new NChar(1);
    // COB:            02  PSTAT03L    COMP  PIC  S9(4).
    public NInt16 pstat03l = new NInt16();
    // COB:            02  PSTAT03F    PICTURE X.
    public NChar pstat03f = new NChar(1);

    // COB:            02  FILLER REDEFINES PSTAT03F.
    public class Filler279 extends NGroup {
      // COB:              03 PSTAT03A    PICTURE X.
      public NChar pstat03a = new NChar(1);
    }

    public Filler279 filler279 = (Filler279) new Filler279().redefines(pstat03f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler281 = new NChar(4);
    // COB:            02  PSTAT03I  PIC X(1).
    public NChar pstat03i = new NChar(1);
    // COB:            02  PAMT003L    COMP  PIC  S9(4).
    public NInt16 pamt003l = new NInt16();
    // COB:            02  PAMT003F    PICTURE X.
    public NChar pamt003f = new NChar(1);

    // COB:            02  FILLER REDEFINES PAMT003F.
    public class Filler285 extends NGroup {
      // COB:              03 PAMT003A    PICTURE X.
      public NChar pamt003a = new NChar(1);
    }

    public Filler285 filler285 = (Filler285) new Filler285().redefines(pamt003f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler287 = new NChar(4);
    // COB:            02  PAMT003I  PIC X(12).
    public NChar pamt003i = new NChar(12);
    // COB:            02  SEL0004L    COMP  PIC  S9(4).
    public NInt16 sel0004l = new NInt16();
    // COB:            02  SEL0004F    PICTURE X.
    public NChar sel0004f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0004F.
    public class Filler291 extends NGroup {
      // COB:              03 SEL0004A    PICTURE X.
      public NChar sel0004a = new NChar(1);
    }

    public Filler291 filler291 = (Filler291) new Filler291().redefines(sel0004f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler293 = new NChar(4);
    // COB:            02  SEL0004I  PIC X(1).
    public NChar sel0004i = new NChar(1);
    // COB:            02  TRNID04L    COMP  PIC  S9(4).
    public NInt16 trnid04l = new NInt16();
    // COB:            02  TRNID04F    PICTURE X.
    public NChar trnid04f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID04F.
    public class Filler297 extends NGroup {
      // COB:              03 TRNID04A    PICTURE X.
      public NChar trnid04a = new NChar(1);
    }

    public Filler297 filler297 = (Filler297) new Filler297().redefines(trnid04f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler299 = new NChar(4);
    // COB:            02  TRNID04I  PIC X(16).
    public NChar trnid04i = new NChar(16);
    // COB:            02  PDATE04L    COMP  PIC  S9(4).
    public NInt16 pdate04l = new NInt16();
    // COB:            02  PDATE04F    PICTURE X.
    public NChar pdate04f = new NChar(1);

    // COB:            02  FILLER REDEFINES PDATE04F.
    public class Filler303 extends NGroup {
      // COB:              03 PDATE04A    PICTURE X.
      public NChar pdate04a = new NChar(1);
    }

    public Filler303 filler303 = (Filler303) new Filler303().redefines(pdate04f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler305 = new NChar(4);
    // COB:            02  PDATE04I  PIC X(8).
    public NChar pdate04i = new NChar(8);
    // COB:            02  PTIME04L    COMP  PIC  S9(4).
    public NInt16 ptime04l = new NInt16();
    // COB:            02  PTIME04F    PICTURE X.
    public NChar ptime04f = new NChar(1);

    // COB:            02  FILLER REDEFINES PTIME04F.
    public class Filler309 extends NGroup {
      // COB:              03 PTIME04A    PICTURE X.
      public NChar ptime04a = new NChar(1);
    }

    public Filler309 filler309 = (Filler309) new Filler309().redefines(ptime04f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler311 = new NChar(4);
    // COB:            02  PTIME04I  PIC X(8).
    public NChar ptime04i = new NChar(8);
    // COB:            02  PTYPE04L    COMP  PIC  S9(4).
    public NInt16 ptype04l = new NInt16();
    // COB:            02  PTYPE04F    PICTURE X.
    public NChar ptype04f = new NChar(1);

    // COB:            02  FILLER REDEFINES PTYPE04F.
    public class Filler315 extends NGroup {
      // COB:              03 PTYPE04A    PICTURE X.
      public NChar ptype04a = new NChar(1);
    }

    public Filler315 filler315 = (Filler315) new Filler315().redefines(ptype04f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler317 = new NChar(4);
    // COB:            02  PTYPE04I  PIC X(4).
    public NChar ptype04i = new NChar(4);
    // COB:            02  PAPRV04L    COMP  PIC  S9(4).
    public NInt16 paprv04l = new NInt16();
    // COB:            02  PAPRV04F    PICTURE X.
    public NChar paprv04f = new NChar(1);

    // COB:            02  FILLER REDEFINES PAPRV04F.
    public class Filler321 extends NGroup {
      // COB:              03 PAPRV04A    PICTURE X.
      public NChar paprv04a = new NChar(1);
    }

    public Filler321 filler321 = (Filler321) new Filler321().redefines(paprv04f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler323 = new NChar(4);
    // COB:            02  PAPRV04I  PIC X(1).
    public NChar paprv04i = new NChar(1);
    // COB:            02  PSTAT04L    COMP  PIC  S9(4).
    public NInt16 pstat04l = new NInt16();
    // COB:            02  PSTAT04F    PICTURE X.
    public NChar pstat04f = new NChar(1);

    // COB:            02  FILLER REDEFINES PSTAT04F.
    public class Filler327 extends NGroup {
      // COB:              03 PSTAT04A    PICTURE X.
      public NChar pstat04a = new NChar(1);
    }

    public Filler327 filler327 = (Filler327) new Filler327().redefines(pstat04f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler329 = new NChar(4);
    // COB:            02  PSTAT04I  PIC X(1).
    public NChar pstat04i = new NChar(1);
    // COB:            02  PAMT004L    COMP  PIC  S9(4).
    public NInt16 pamt004l = new NInt16();
    // COB:            02  PAMT004F    PICTURE X.
    public NChar pamt004f = new NChar(1);

    // COB:            02  FILLER REDEFINES PAMT004F.
    public class Filler333 extends NGroup {
      // COB:              03 PAMT004A    PICTURE X.
      public NChar pamt004a = new NChar(1);
    }

    public Filler333 filler333 = (Filler333) new Filler333().redefines(pamt004f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler335 = new NChar(4);
    // COB:            02  PAMT004I  PIC X(12).
    public NChar pamt004i = new NChar(12);
    // COB:            02  TRNID05L    COMP  PIC  S9(4).
    public NInt16 trnid05l = new NInt16();
    // COB:            02  TRNID05F    PICTURE X.
    public NChar trnid05f = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNID05F.
    public class Filler339 extends NGroup {
      // COB:              03 TRNID05A    PICTURE X.
      public NChar trnid05a = new NChar(1);
    }

    public Filler339 filler339 = (Filler339) new Filler339().redefines(trnid05f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler341 = new NChar(4);
    // COB:            02  TRNID05I  PIC X(16).
    public NChar trnid05i = new NChar(16);
    // COB:            02  PDATE05L    COMP  PIC  S9(4).
    public NInt16 pdate05l = new NInt16();
    // COB:            02  PDATE05F    PICTURE X.
    public NChar pdate05f = new NChar(1);

    // COB:            02  FILLER REDEFINES PDATE05F.
    public class Filler345 extends NGroup {
      // COB:              03 PDATE05A    PICTURE X.
      public NChar pdate05a = new NChar(1);
    }

    public Filler345 filler345 = (Filler345) new Filler345().redefines(pdate05f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler347 = new NChar(4);
    // COB:            02  PDATE05I  PIC X(8).
    public NChar pdate05i = new NChar(8);
    // COB:            02  PTIME05L    COMP  PIC  S9(4).
    public NInt16 ptime05l = new NInt16();
    // COB:            02  PTIME05F    PICTURE X.
    public NChar ptime05f = new NChar(1);

    // COB:            02  FILLER REDEFINES PTIME05F.
    public class Filler351 extends NGroup {
      // COB:              03 PTIME05A    PICTURE X.
      public NChar ptime05a = new NChar(1);
    }

    public Filler351 filler351 = (Filler351) new Filler351().redefines(ptime05f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler353 = new NChar(4);
    // COB:            02  PTIME05I  PIC X(8).
    public NChar ptime05i = new NChar(8);
    // COB:            02  PTYPE05L    COMP  PIC  S9(4).
    public NInt16 ptype05l = new NInt16();
    // COB:            02  PTYPE05F    PICTURE X.
    public NChar ptype05f = new NChar(1);

    // COB:            02  FILLER REDEFINES PTYPE05F.
    public class Filler357 extends NGroup {
      // COB:              03 PTYPE05A    PICTURE X.
      public NChar ptype05a = new NChar(1);
    }

    public Filler357 filler357 = (Filler357) new Filler357().redefines(ptype05f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler359 = new NChar(4);
    // COB:            02  PTYPE05I  PIC X(4).
    public NChar ptype05i = new NChar(4);
    // COB:            02  PAPRV05L    COMP  PIC  S9(4).
    public NInt16 paprv05l = new NInt16();
    // COB:            02  PAPRV05F    PICTURE X.
    public NChar paprv05f = new NChar(1);

    // COB:            02  FILLER REDEFINES PAPRV05F.
    public class Filler363 extends NGroup {
      // COB:              03 PAPRV05A    PICTURE X.
      public NChar paprv05a = new NChar(1);
    }

    public Filler363 filler363 = (Filler363) new Filler363().redefines(paprv05f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler365 = new NChar(4);
    // COB:            02  PAPRV05I  PIC X(1).
    public NChar paprv05i = new NChar(1);
    // COB:            02  PSTAT05L    COMP  PIC  S9(4).
    public NInt16 pstat05l = new NInt16();
    // COB:            02  PSTAT05F    PICTURE X.
    public NChar pstat05f = new NChar(1);

    // COB:            02  FILLER REDEFINES PSTAT05F.
    public class Filler369 extends NGroup {
      // COB:              03 PSTAT05A    PICTURE X.
      public NChar pstat05a = new NChar(1);
    }

    public Filler369 filler369 = (Filler369) new Filler369().redefines(pstat05f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler371 = new NChar(4);
    // COB:            02  PSTAT05I  PIC X(1).
    public NChar pstat05i = new NChar(1);
    // COB:            02  PAMT005L    COMP  PIC  S9(4).
    public NInt16 pamt005l = new NInt16();
    // COB:            02  PAMT005F    PICTURE X.
    public NChar pamt005f = new NChar(1);

    // COB:            02  FILLER REDEFINES PAMT005F.
    public class Filler375 extends NGroup {
      // COB:              03 PAMT005A    PICTURE X.
      public NChar pamt005a = new NChar(1);
    }

    public Filler375 filler375 = (Filler375) new Filler375().redefines(pamt005f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler377 = new NChar(4);
    // COB:            02  PAMT005I  PIC X(12).
    public NChar pamt005i = new NChar(12);
    // COB:            02  SEL0005L    COMP  PIC  S9(4).
    public NInt16 sel0005l = new NInt16();
    // COB:            02  SEL0005F    PICTURE X.
    public NChar sel0005f = new NChar(1);

    // COB:            02  FILLER REDEFINES SEL0005F.
    public class Filler381 extends NGroup {
      // COB:              03 SEL0005A    PICTURE X.
      public NChar sel0005a = new NChar(1);
    }

    public Filler381 filler381 = (Filler381) new Filler381().redefines(sel0005f);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler383 = new NChar(4);
    // COB:            02  SEL0005I  PIC X(1).
    public NChar sel0005i = new NChar(1);
    // COB:            02  ERRMSGL    COMP  PIC  S9(4).
    public NInt16 errmsgl = new NInt16();
    // COB:            02  ERRMSGF    PICTURE X.
    public NChar errmsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES ERRMSGF.
    public class Filler387 extends NGroup {
      // COB:              03 ERRMSGA    PICTURE X.
      public NChar errmsga = new NChar(1);
    }

    public Filler387 filler387 = (Filler387) new Filler387().redefines(errmsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler389 = new NChar(4);
    // COB:            02  ERRMSGI  PIC X(78).
    public NChar errmsgi = new NChar(78);
  }

  public Copau0ai copau0ai = new Copau0ai();

  // COB:        01  COPAU0AO REDEFINES COPAU0AI.
  public class Copau0ao extends NGroup {
    // COB:            02  FILLER PIC X(12).
    public NChar filler392 = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler393 = new NChar(3);
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
    public NChar filler399 = new NChar(3);
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
    public NChar filler405 = new NChar(3);
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
    public NChar filler411 = new NChar(3);
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
    public NChar filler417 = new NChar(3);
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
    public NChar filler423 = new NChar(3);
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
    public NChar filler429 = new NChar(3);
    // COB:            02  ACCTIDC    PICTURE X.
    public NChar acctidc = new NChar(1);
    // COB:            02  ACCTIDP    PICTURE X.
    public NChar acctidp = new NChar(1);
    // COB:            02  ACCTIDH    PICTURE X.
    public NChar acctidh = new NChar(1);
    // COB:            02  ACCTIDV    PICTURE X.
    public NChar acctidv = new NChar(1);
    // COB:            02  ACCTIDO  PIC X(11).
    public NChar acctido = new NChar(11);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler435 = new NChar(3);
    // COB:            02  CNAMEC    PICTURE X.
    public NChar cnamec = new NChar(1);
    // COB:            02  CNAMEP    PICTURE X.
    public NChar cnamep = new NChar(1);
    // COB:            02  CNAMEH    PICTURE X.
    public NChar cnameh = new NChar(1);
    // COB:            02  CNAMEV    PICTURE X.
    public NChar cnamev = new NChar(1);
    // COB:            02  CNAMEO  PIC X(25).
    public NChar cnameo = new NChar(25);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler441 = new NChar(3);
    // COB:            02  CUSTIDC    PICTURE X.
    public NChar custidc = new NChar(1);
    // COB:            02  CUSTIDP    PICTURE X.
    public NChar custidp = new NChar(1);
    // COB:            02  CUSTIDH    PICTURE X.
    public NChar custidh = new NChar(1);
    // COB:            02  CUSTIDV    PICTURE X.
    public NChar custidv = new NChar(1);
    // COB:            02  CUSTIDO  PIC X(9).
    public NChar custido = new NChar(9);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler447 = new NChar(3);
    // COB:            02  ADDR001C    PICTURE X.
    public NChar addr001c = new NChar(1);
    // COB:            02  ADDR001P    PICTURE X.
    public NChar addr001p = new NChar(1);
    // COB:            02  ADDR001H    PICTURE X.
    public NChar addr001h = new NChar(1);
    // COB:            02  ADDR001V    PICTURE X.
    public NChar addr001v = new NChar(1);
    // COB:            02  ADDR001O  PIC X(25).
    public NChar addr001o = new NChar(25);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler453 = new NChar(3);
    // COB:            02  ACCSTATC    PICTURE X.
    public NChar accstatc = new NChar(1);
    // COB:            02  ACCSTATP    PICTURE X.
    public NChar accstatp = new NChar(1);
    // COB:            02  ACCSTATH    PICTURE X.
    public NChar accstath = new NChar(1);
    // COB:            02  ACCSTATV    PICTURE X.
    public NChar accstatv = new NChar(1);
    // COB:            02  ACCSTATO  PIC X(1).
    public NChar accstato = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler459 = new NChar(3);
    // COB:            02  ADDR002C    PICTURE X.
    public NChar addr002c = new NChar(1);
    // COB:            02  ADDR002P    PICTURE X.
    public NChar addr002p = new NChar(1);
    // COB:            02  ADDR002H    PICTURE X.
    public NChar addr002h = new NChar(1);
    // COB:            02  ADDR002V    PICTURE X.
    public NChar addr002v = new NChar(1);
    // COB:            02  ADDR002O  PIC X(25).
    public NChar addr002o = new NChar(25);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler465 = new NChar(3);
    // COB:            02  PHONE1C    PICTURE X.
    public NChar phone1c = new NChar(1);
    // COB:            02  PHONE1P    PICTURE X.
    public NChar phone1p = new NChar(1);
    // COB:            02  PHONE1H    PICTURE X.
    public NChar phone1h = new NChar(1);
    // COB:            02  PHONE1V    PICTURE X.
    public NChar phone1v = new NChar(1);
    // COB:            02  PHONE1O  PIC X(13).
    public NChar phone1o = new NChar(13);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler471 = new NChar(3);
    // COB:            02  APPRCNTC    PICTURE X.
    public NChar apprcntc = new NChar(1);
    // COB:            02  APPRCNTP    PICTURE X.
    public NChar apprcntp = new NChar(1);
    // COB:            02  APPRCNTH    PICTURE X.
    public NChar apprcnth = new NChar(1);
    // COB:            02  APPRCNTV    PICTURE X.
    public NChar apprcntv = new NChar(1);
    // COB:            02  APPRCNTO  PIC X(3).
    public NChar apprcnto = new NChar(3);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler477 = new NChar(3);
    // COB:            02  DECLCNTC    PICTURE X.
    public NChar declcntc = new NChar(1);
    // COB:            02  DECLCNTP    PICTURE X.
    public NChar declcntp = new NChar(1);
    // COB:            02  DECLCNTH    PICTURE X.
    public NChar declcnth = new NChar(1);
    // COB:            02  DECLCNTV    PICTURE X.
    public NChar declcntv = new NChar(1);
    // COB:            02  DECLCNTO  PIC X(3).
    public NChar declcnto = new NChar(3);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler483 = new NChar(3);
    // COB:            02  CREDLIMC    PICTURE X.
    public NChar credlimc = new NChar(1);
    // COB:            02  CREDLIMP    PICTURE X.
    public NChar credlimp = new NChar(1);
    // COB:            02  CREDLIMH    PICTURE X.
    public NChar credlimh = new NChar(1);
    // COB:            02  CREDLIMV    PICTURE X.
    public NChar credlimv = new NChar(1);
    // COB:            02  CREDLIMO  PIC X(12).
    public NChar credlimo = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler489 = new NChar(3);
    // COB:            02  CASHLIMC    PICTURE X.
    public NChar cashlimc = new NChar(1);
    // COB:            02  CASHLIMP    PICTURE X.
    public NChar cashlimp = new NChar(1);
    // COB:            02  CASHLIMH    PICTURE X.
    public NChar cashlimh = new NChar(1);
    // COB:            02  CASHLIMV    PICTURE X.
    public NChar cashlimv = new NChar(1);
    // COB:            02  CASHLIMO  PIC X(9).
    public NChar cashlimo = new NChar(9);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler495 = new NChar(3);
    // COB:            02  APPRAMTC    PICTURE X.
    public NChar appramtc = new NChar(1);
    // COB:            02  APPRAMTP    PICTURE X.
    public NChar appramtp = new NChar(1);
    // COB:            02  APPRAMTH    PICTURE X.
    public NChar appramth = new NChar(1);
    // COB:            02  APPRAMTV    PICTURE X.
    public NChar appramtv = new NChar(1);
    // COB:            02  APPRAMTO  PIC X(10).
    public NChar appramto = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler501 = new NChar(3);
    // COB:            02  CREDBALC    PICTURE X.
    public NChar credbalc = new NChar(1);
    // COB:            02  CREDBALP    PICTURE X.
    public NChar credbalp = new NChar(1);
    // COB:            02  CREDBALH    PICTURE X.
    public NChar credbalh = new NChar(1);
    // COB:            02  CREDBALV    PICTURE X.
    public NChar credbalv = new NChar(1);
    // COB:            02  CREDBALO  PIC X(12).
    public NChar credbalo = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler507 = new NChar(3);
    // COB:            02  CASHBALC    PICTURE X.
    public NChar cashbalc = new NChar(1);
    // COB:            02  CASHBALP    PICTURE X.
    public NChar cashbalp = new NChar(1);
    // COB:            02  CASHBALH    PICTURE X.
    public NChar cashbalh = new NChar(1);
    // COB:            02  CASHBALV    PICTURE X.
    public NChar cashbalv = new NChar(1);
    // COB:            02  CASHBALO  PIC X(9).
    public NChar cashbalo = new NChar(9);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler513 = new NChar(3);
    // COB:            02  DECLAMTC    PICTURE X.
    public NChar declamtc = new NChar(1);
    // COB:            02  DECLAMTP    PICTURE X.
    public NChar declamtp = new NChar(1);
    // COB:            02  DECLAMTH    PICTURE X.
    public NChar declamth = new NChar(1);
    // COB:            02  DECLAMTV    PICTURE X.
    public NChar declamtv = new NChar(1);
    // COB:            02  DECLAMTO  PIC X(10).
    public NChar declamto = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler519 = new NChar(3);
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
    public NChar filler525 = new NChar(3);
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
    public NChar filler531 = new NChar(3);
    // COB:            02  PDATE01C    PICTURE X.
    public NChar pdate01c = new NChar(1);
    // COB:            02  PDATE01P    PICTURE X.
    public NChar pdate01p = new NChar(1);
    // COB:            02  PDATE01H    PICTURE X.
    public NChar pdate01h = new NChar(1);
    // COB:            02  PDATE01V    PICTURE X.
    public NChar pdate01v = new NChar(1);
    // COB:            02  PDATE01O  PIC X(8).
    public NChar pdate01o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler537 = new NChar(3);
    // COB:            02  PTIME01C    PICTURE X.
    public NChar ptime01c = new NChar(1);
    // COB:            02  PTIME01P    PICTURE X.
    public NChar ptime01p = new NChar(1);
    // COB:            02  PTIME01H    PICTURE X.
    public NChar ptime01h = new NChar(1);
    // COB:            02  PTIME01V    PICTURE X.
    public NChar ptime01v = new NChar(1);
    // COB:            02  PTIME01O  PIC X(8).
    public NChar ptime01o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler543 = new NChar(3);
    // COB:            02  PTYPE01C    PICTURE X.
    public NChar ptype01c = new NChar(1);
    // COB:            02  PTYPE01P    PICTURE X.
    public NChar ptype01p = new NChar(1);
    // COB:            02  PTYPE01H    PICTURE X.
    public NChar ptype01h = new NChar(1);
    // COB:            02  PTYPE01V    PICTURE X.
    public NChar ptype01v = new NChar(1);
    // COB:            02  PTYPE01O  PIC X(4).
    public NChar ptype01o = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler549 = new NChar(3);
    // COB:            02  PAPRV01C    PICTURE X.
    public NChar paprv01c = new NChar(1);
    // COB:            02  PAPRV01P    PICTURE X.
    public NChar paprv01p = new NChar(1);
    // COB:            02  PAPRV01H    PICTURE X.
    public NChar paprv01h = new NChar(1);
    // COB:            02  PAPRV01V    PICTURE X.
    public NChar paprv01v = new NChar(1);
    // COB:            02  PAPRV01O  PIC X(1).
    public NChar paprv01o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler555 = new NChar(3);
    // COB:            02  PSTAT01C    PICTURE X.
    public NChar pstat01c = new NChar(1);
    // COB:            02  PSTAT01P    PICTURE X.
    public NChar pstat01p = new NChar(1);
    // COB:            02  PSTAT01H    PICTURE X.
    public NChar pstat01h = new NChar(1);
    // COB:            02  PSTAT01V    PICTURE X.
    public NChar pstat01v = new NChar(1);
    // COB:            02  PSTAT01O  PIC X(1).
    public NChar pstat01o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler561 = new NChar(3);
    // COB:            02  PAMT001C    PICTURE X.
    public NChar pamt001c = new NChar(1);
    // COB:            02  PAMT001P    PICTURE X.
    public NChar pamt001p = new NChar(1);
    // COB:            02  PAMT001H    PICTURE X.
    public NChar pamt001h = new NChar(1);
    // COB:            02  PAMT001V    PICTURE X.
    public NChar pamt001v = new NChar(1);
    // COB:            02  PAMT001O  PIC X(12).
    public NChar pamt001o = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler567 = new NChar(3);
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
    public NChar filler573 = new NChar(3);
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
    public NChar filler579 = new NChar(3);
    // COB:            02  PDATE02C    PICTURE X.
    public NChar pdate02c = new NChar(1);
    // COB:            02  PDATE02P    PICTURE X.
    public NChar pdate02p = new NChar(1);
    // COB:            02  PDATE02H    PICTURE X.
    public NChar pdate02h = new NChar(1);
    // COB:            02  PDATE02V    PICTURE X.
    public NChar pdate02v = new NChar(1);
    // COB:            02  PDATE02O  PIC X(8).
    public NChar pdate02o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler585 = new NChar(3);
    // COB:            02  PTIME02C    PICTURE X.
    public NChar ptime02c = new NChar(1);
    // COB:            02  PTIME02P    PICTURE X.
    public NChar ptime02p = new NChar(1);
    // COB:            02  PTIME02H    PICTURE X.
    public NChar ptime02h = new NChar(1);
    // COB:            02  PTIME02V    PICTURE X.
    public NChar ptime02v = new NChar(1);
    // COB:            02  PTIME02O  PIC X(8).
    public NChar ptime02o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler591 = new NChar(3);
    // COB:            02  PTYPE02C    PICTURE X.
    public NChar ptype02c = new NChar(1);
    // COB:            02  PTYPE02P    PICTURE X.
    public NChar ptype02p = new NChar(1);
    // COB:            02  PTYPE02H    PICTURE X.
    public NChar ptype02h = new NChar(1);
    // COB:            02  PTYPE02V    PICTURE X.
    public NChar ptype02v = new NChar(1);
    // COB:            02  PTYPE02O  PIC X(4).
    public NChar ptype02o = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler597 = new NChar(3);
    // COB:            02  PAPRV02C    PICTURE X.
    public NChar paprv02c = new NChar(1);
    // COB:            02  PAPRV02P    PICTURE X.
    public NChar paprv02p = new NChar(1);
    // COB:            02  PAPRV02H    PICTURE X.
    public NChar paprv02h = new NChar(1);
    // COB:            02  PAPRV02V    PICTURE X.
    public NChar paprv02v = new NChar(1);
    // COB:            02  PAPRV02O  PIC X(1).
    public NChar paprv02o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler603 = new NChar(3);
    // COB:            02  PSTAT02C    PICTURE X.
    public NChar pstat02c = new NChar(1);
    // COB:            02  PSTAT02P    PICTURE X.
    public NChar pstat02p = new NChar(1);
    // COB:            02  PSTAT02H    PICTURE X.
    public NChar pstat02h = new NChar(1);
    // COB:            02  PSTAT02V    PICTURE X.
    public NChar pstat02v = new NChar(1);
    // COB:            02  PSTAT02O  PIC X(1).
    public NChar pstat02o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler609 = new NChar(3);
    // COB:            02  PAMT002C    PICTURE X.
    public NChar pamt002c = new NChar(1);
    // COB:            02  PAMT002P    PICTURE X.
    public NChar pamt002p = new NChar(1);
    // COB:            02  PAMT002H    PICTURE X.
    public NChar pamt002h = new NChar(1);
    // COB:            02  PAMT002V    PICTURE X.
    public NChar pamt002v = new NChar(1);
    // COB:            02  PAMT002O  PIC X(12).
    public NChar pamt002o = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler615 = new NChar(3);
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
    public NChar filler621 = new NChar(3);
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
    public NChar filler627 = new NChar(3);
    // COB:            02  PDATE03C    PICTURE X.
    public NChar pdate03c = new NChar(1);
    // COB:            02  PDATE03P    PICTURE X.
    public NChar pdate03p = new NChar(1);
    // COB:            02  PDATE03H    PICTURE X.
    public NChar pdate03h = new NChar(1);
    // COB:            02  PDATE03V    PICTURE X.
    public NChar pdate03v = new NChar(1);
    // COB:            02  PDATE03O  PIC X(8).
    public NChar pdate03o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler633 = new NChar(3);
    // COB:            02  PTIME03C    PICTURE X.
    public NChar ptime03c = new NChar(1);
    // COB:            02  PTIME03P    PICTURE X.
    public NChar ptime03p = new NChar(1);
    // COB:            02  PTIME03H    PICTURE X.
    public NChar ptime03h = new NChar(1);
    // COB:            02  PTIME03V    PICTURE X.
    public NChar ptime03v = new NChar(1);
    // COB:            02  PTIME03O  PIC X(8).
    public NChar ptime03o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler639 = new NChar(3);
    // COB:            02  PTYPE03C    PICTURE X.
    public NChar ptype03c = new NChar(1);
    // COB:            02  PTYPE03P    PICTURE X.
    public NChar ptype03p = new NChar(1);
    // COB:            02  PTYPE03H    PICTURE X.
    public NChar ptype03h = new NChar(1);
    // COB:            02  PTYPE03V    PICTURE X.
    public NChar ptype03v = new NChar(1);
    // COB:            02  PTYPE03O  PIC X(4).
    public NChar ptype03o = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler645 = new NChar(3);
    // COB:            02  PAPRV03C    PICTURE X.
    public NChar paprv03c = new NChar(1);
    // COB:            02  PAPRV03P    PICTURE X.
    public NChar paprv03p = new NChar(1);
    // COB:            02  PAPRV03H    PICTURE X.
    public NChar paprv03h = new NChar(1);
    // COB:            02  PAPRV03V    PICTURE X.
    public NChar paprv03v = new NChar(1);
    // COB:            02  PAPRV03O  PIC X(1).
    public NChar paprv03o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler651 = new NChar(3);
    // COB:            02  PSTAT03C    PICTURE X.
    public NChar pstat03c = new NChar(1);
    // COB:            02  PSTAT03P    PICTURE X.
    public NChar pstat03p = new NChar(1);
    // COB:            02  PSTAT03H    PICTURE X.
    public NChar pstat03h = new NChar(1);
    // COB:            02  PSTAT03V    PICTURE X.
    public NChar pstat03v = new NChar(1);
    // COB:            02  PSTAT03O  PIC X(1).
    public NChar pstat03o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler657 = new NChar(3);
    // COB:            02  PAMT003C    PICTURE X.
    public NChar pamt003c = new NChar(1);
    // COB:            02  PAMT003P    PICTURE X.
    public NChar pamt003p = new NChar(1);
    // COB:            02  PAMT003H    PICTURE X.
    public NChar pamt003h = new NChar(1);
    // COB:            02  PAMT003V    PICTURE X.
    public NChar pamt003v = new NChar(1);
    // COB:            02  PAMT003O  PIC X(12).
    public NChar pamt003o = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler663 = new NChar(3);
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
    public NChar filler669 = new NChar(3);
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
    public NChar filler675 = new NChar(3);
    // COB:            02  PDATE04C    PICTURE X.
    public NChar pdate04c = new NChar(1);
    // COB:            02  PDATE04P    PICTURE X.
    public NChar pdate04p = new NChar(1);
    // COB:            02  PDATE04H    PICTURE X.
    public NChar pdate04h = new NChar(1);
    // COB:            02  PDATE04V    PICTURE X.
    public NChar pdate04v = new NChar(1);
    // COB:            02  PDATE04O  PIC X(8).
    public NChar pdate04o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler681 = new NChar(3);
    // COB:            02  PTIME04C    PICTURE X.
    public NChar ptime04c = new NChar(1);
    // COB:            02  PTIME04P    PICTURE X.
    public NChar ptime04p = new NChar(1);
    // COB:            02  PTIME04H    PICTURE X.
    public NChar ptime04h = new NChar(1);
    // COB:            02  PTIME04V    PICTURE X.
    public NChar ptime04v = new NChar(1);
    // COB:            02  PTIME04O  PIC X(8).
    public NChar ptime04o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler687 = new NChar(3);
    // COB:            02  PTYPE04C    PICTURE X.
    public NChar ptype04c = new NChar(1);
    // COB:            02  PTYPE04P    PICTURE X.
    public NChar ptype04p = new NChar(1);
    // COB:            02  PTYPE04H    PICTURE X.
    public NChar ptype04h = new NChar(1);
    // COB:            02  PTYPE04V    PICTURE X.
    public NChar ptype04v = new NChar(1);
    // COB:            02  PTYPE04O  PIC X(4).
    public NChar ptype04o = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler693 = new NChar(3);
    // COB:            02  PAPRV04C    PICTURE X.
    public NChar paprv04c = new NChar(1);
    // COB:            02  PAPRV04P    PICTURE X.
    public NChar paprv04p = new NChar(1);
    // COB:            02  PAPRV04H    PICTURE X.
    public NChar paprv04h = new NChar(1);
    // COB:            02  PAPRV04V    PICTURE X.
    public NChar paprv04v = new NChar(1);
    // COB:            02  PAPRV04O  PIC X(1).
    public NChar paprv04o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler699 = new NChar(3);
    // COB:            02  PSTAT04C    PICTURE X.
    public NChar pstat04c = new NChar(1);
    // COB:            02  PSTAT04P    PICTURE X.
    public NChar pstat04p = new NChar(1);
    // COB:            02  PSTAT04H    PICTURE X.
    public NChar pstat04h = new NChar(1);
    // COB:            02  PSTAT04V    PICTURE X.
    public NChar pstat04v = new NChar(1);
    // COB:            02  PSTAT04O  PIC X(1).
    public NChar pstat04o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler705 = new NChar(3);
    // COB:            02  PAMT004C    PICTURE X.
    public NChar pamt004c = new NChar(1);
    // COB:            02  PAMT004P    PICTURE X.
    public NChar pamt004p = new NChar(1);
    // COB:            02  PAMT004H    PICTURE X.
    public NChar pamt004h = new NChar(1);
    // COB:            02  PAMT004V    PICTURE X.
    public NChar pamt004v = new NChar(1);
    // COB:            02  PAMT004O  PIC X(12).
    public NChar pamt004o = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler711 = new NChar(3);
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
    public NChar filler717 = new NChar(3);
    // COB:            02  PDATE05C    PICTURE X.
    public NChar pdate05c = new NChar(1);
    // COB:            02  PDATE05P    PICTURE X.
    public NChar pdate05p = new NChar(1);
    // COB:            02  PDATE05H    PICTURE X.
    public NChar pdate05h = new NChar(1);
    // COB:            02  PDATE05V    PICTURE X.
    public NChar pdate05v = new NChar(1);
    // COB:            02  PDATE05O  PIC X(8).
    public NChar pdate05o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler723 = new NChar(3);
    // COB:            02  PTIME05C    PICTURE X.
    public NChar ptime05c = new NChar(1);
    // COB:            02  PTIME05P    PICTURE X.
    public NChar ptime05p = new NChar(1);
    // COB:            02  PTIME05H    PICTURE X.
    public NChar ptime05h = new NChar(1);
    // COB:            02  PTIME05V    PICTURE X.
    public NChar ptime05v = new NChar(1);
    // COB:            02  PTIME05O  PIC X(8).
    public NChar ptime05o = new NChar(8);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler729 = new NChar(3);
    // COB:            02  PTYPE05C    PICTURE X.
    public NChar ptype05c = new NChar(1);
    // COB:            02  PTYPE05P    PICTURE X.
    public NChar ptype05p = new NChar(1);
    // COB:            02  PTYPE05H    PICTURE X.
    public NChar ptype05h = new NChar(1);
    // COB:            02  PTYPE05V    PICTURE X.
    public NChar ptype05v = new NChar(1);
    // COB:            02  PTYPE05O  PIC X(4).
    public NChar ptype05o = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler735 = new NChar(3);
    // COB:            02  PAPRV05C    PICTURE X.
    public NChar paprv05c = new NChar(1);
    // COB:            02  PAPRV05P    PICTURE X.
    public NChar paprv05p = new NChar(1);
    // COB:            02  PAPRV05H    PICTURE X.
    public NChar paprv05h = new NChar(1);
    // COB:            02  PAPRV05V    PICTURE X.
    public NChar paprv05v = new NChar(1);
    // COB:            02  PAPRV05O  PIC X(1).
    public NChar paprv05o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler741 = new NChar(3);
    // COB:            02  PSTAT05C    PICTURE X.
    public NChar pstat05c = new NChar(1);
    // COB:            02  PSTAT05P    PICTURE X.
    public NChar pstat05p = new NChar(1);
    // COB:            02  PSTAT05H    PICTURE X.
    public NChar pstat05h = new NChar(1);
    // COB:            02  PSTAT05V    PICTURE X.
    public NChar pstat05v = new NChar(1);
    // COB:            02  PSTAT05O  PIC X(1).
    public NChar pstat05o = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler747 = new NChar(3);
    // COB:            02  PAMT005C    PICTURE X.
    public NChar pamt005c = new NChar(1);
    // COB:            02  PAMT005P    PICTURE X.
    public NChar pamt005p = new NChar(1);
    // COB:            02  PAMT005H    PICTURE X.
    public NChar pamt005h = new NChar(1);
    // COB:            02  PAMT005V    PICTURE X.
    public NChar pamt005v = new NChar(1);
    // COB:            02  PAMT005O  PIC X(12).
    public NChar pamt005o = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler753 = new NChar(3);
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
    public NChar filler759 = new NChar(3);
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

  public Copau0ao copau0ao = (Copau0ao) new Copau0ao().redefines(copau0ai);
}
