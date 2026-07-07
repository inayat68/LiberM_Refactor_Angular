package com.aws.carddemo.common.copybook;

import com.nib.commons.storage.*;

public class Copau01 extends NGroup {

  // COB:        01  COPAU1AI.
  public class Copau1ai extends NGroup {
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
    // COB:            02  CARDNUML    COMP  PIC  S9(4).
    public NInt16 cardnuml = new NInt16();
    // COB:            02  CARDNUMF    PICTURE X.
    public NChar cardnumf = new NChar(1);

    // COB:            02  FILLER REDEFINES CARDNUMF.
    public class Filler57 extends NGroup {
      // COB:              03 CARDNUMA    PICTURE X.
      public NChar cardnuma = new NChar(1);
    }

    public Filler57 filler57 = (Filler57) new Filler57().redefines(cardnumf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler59 = new NChar(4);
    // COB:            02  CARDNUMI  PIC X(16).
    public NChar cardnumi = new NChar(16);
    // COB:            02  AUTHDTL    COMP  PIC  S9(4).
    public NInt16 authdtl = new NInt16();
    // COB:            02  AUTHDTF    PICTURE X.
    public NChar authdtf = new NChar(1);

    // COB:            02  FILLER REDEFINES AUTHDTF.
    public class Filler63 extends NGroup {
      // COB:              03 AUTHDTA    PICTURE X.
      public NChar authdta = new NChar(1);
    }

    public Filler63 filler63 = (Filler63) new Filler63().redefines(authdtf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler65 = new NChar(4);
    // COB:            02  AUTHDTI  PIC X(10).
    public NChar authdti = new NChar(10);
    // COB:            02  AUTHTML    COMP  PIC  S9(4).
    public NInt16 authtml = new NInt16();
    // COB:            02  AUTHTMF    PICTURE X.
    public NChar authtmf = new NChar(1);

    // COB:            02  FILLER REDEFINES AUTHTMF.
    public class Filler69 extends NGroup {
      // COB:              03 AUTHTMA    PICTURE X.
      public NChar authtma = new NChar(1);
    }

    public Filler69 filler69 = (Filler69) new Filler69().redefines(authtmf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler71 = new NChar(4);
    // COB:            02  AUTHTMI  PIC X(10).
    public NChar authtmi = new NChar(10);
    // COB:            02  AUTHRSPL    COMP  PIC  S9(4).
    public NInt16 authrspl = new NInt16();
    // COB:            02  AUTHRSPF    PICTURE X.
    public NChar authrspf = new NChar(1);

    // COB:            02  FILLER REDEFINES AUTHRSPF.
    public class Filler75 extends NGroup {
      // COB:              03 AUTHRSPA    PICTURE X.
      public NChar authrspa = new NChar(1);
    }

    public Filler75 filler75 = (Filler75) new Filler75().redefines(authrspf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler77 = new NChar(4);
    // COB:            02  AUTHRSPI  PIC X(1).
    public NChar authrspi = new NChar(1);
    // COB:            02  AUTHRSNL    COMP  PIC  S9(4).
    public NInt16 authrsnl = new NInt16();
    // COB:            02  AUTHRSNF    PICTURE X.
    public NChar authrsnf = new NChar(1);

    // COB:            02  FILLER REDEFINES AUTHRSNF.
    public class Filler81 extends NGroup {
      // COB:              03 AUTHRSNA    PICTURE X.
      public NChar authrsna = new NChar(1);
    }

    public Filler81 filler81 = (Filler81) new Filler81().redefines(authrsnf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler83 = new NChar(4);
    // COB:            02  AUTHRSNI  PIC X(20).
    public NChar authrsni = new NChar(20);
    // COB:            02  AUTHCDL    COMP  PIC  S9(4).
    public NInt16 authcdl = new NInt16();
    // COB:            02  AUTHCDF    PICTURE X.
    public NChar authcdf = new NChar(1);

    // COB:            02  FILLER REDEFINES AUTHCDF.
    public class Filler87 extends NGroup {
      // COB:              03 AUTHCDA    PICTURE X.
      public NChar authcda = new NChar(1);
    }

    public Filler87 filler87 = (Filler87) new Filler87().redefines(authcdf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler89 = new NChar(4);
    // COB:            02  AUTHCDI  PIC X(6).
    public NChar authcdi = new NChar(6);
    // COB:            02  AUTHAMTL    COMP  PIC  S9(4).
    public NInt16 authamtl = new NInt16();
    // COB:            02  AUTHAMTF    PICTURE X.
    public NChar authamtf = new NChar(1);

    // COB:            02  FILLER REDEFINES AUTHAMTF.
    public class Filler93 extends NGroup {
      // COB:              03 AUTHAMTA    PICTURE X.
      public NChar authamta = new NChar(1);
    }

    public Filler93 filler93 = (Filler93) new Filler93().redefines(authamtf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler95 = new NChar(4);
    // COB:            02  AUTHAMTI  PIC X(12).
    public NChar authamti = new NChar(12);
    // COB:            02  POSEMDL    COMP  PIC  S9(4).
    public NInt16 posemdl = new NInt16();
    // COB:            02  POSEMDF    PICTURE X.
    public NChar posemdf = new NChar(1);

    // COB:            02  FILLER REDEFINES POSEMDF.
    public class Filler99 extends NGroup {
      // COB:              03 POSEMDA    PICTURE X.
      public NChar posemda = new NChar(1);
    }

    public Filler99 filler99 = (Filler99) new Filler99().redefines(posemdf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler101 = new NChar(4);
    // COB:            02  POSEMDI  PIC X(4).
    public NChar posemdi = new NChar(4);
    // COB:            02  AUTHSRCL    COMP  PIC  S9(4).
    public NInt16 authsrcl = new NInt16();
    // COB:            02  AUTHSRCF    PICTURE X.
    public NChar authsrcf = new NChar(1);

    // COB:            02  FILLER REDEFINES AUTHSRCF.
    public class Filler105 extends NGroup {
      // COB:              03 AUTHSRCA    PICTURE X.
      public NChar authsrca = new NChar(1);
    }

    public Filler105 filler105 = (Filler105) new Filler105().redefines(authsrcf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler107 = new NChar(4);
    // COB:            02  AUTHSRCI  PIC X(10).
    public NChar authsrci = new NChar(10);
    // COB:            02  MCCCDL    COMP  PIC  S9(4).
    public NInt16 mcccdl = new NInt16();
    // COB:            02  MCCCDF    PICTURE X.
    public NChar mcccdf = new NChar(1);

    // COB:            02  FILLER REDEFINES MCCCDF.
    public class Filler111 extends NGroup {
      // COB:              03 MCCCDA    PICTURE X.
      public NChar mcccda = new NChar(1);
    }

    public Filler111 filler111 = (Filler111) new Filler111().redefines(mcccdf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler113 = new NChar(4);
    // COB:            02  MCCCDI  PIC X(4).
    public NChar mcccdi = new NChar(4);
    // COB:            02  CRDEXPL    COMP  PIC  S9(4).
    public NInt16 crdexpl = new NInt16();
    // COB:            02  CRDEXPF    PICTURE X.
    public NChar crdexpf = new NChar(1);

    // COB:            02  FILLER REDEFINES CRDEXPF.
    public class Filler117 extends NGroup {
      // COB:              03 CRDEXPA    PICTURE X.
      public NChar crdexpa = new NChar(1);
    }

    public Filler117 filler117 = (Filler117) new Filler117().redefines(crdexpf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler119 = new NChar(4);
    // COB:            02  CRDEXPI  PIC X(5).
    public NChar crdexpi = new NChar(5);
    // COB:            02  AUTHTYPL    COMP  PIC  S9(4).
    public NInt16 authtypl = new NInt16();
    // COB:            02  AUTHTYPF    PICTURE X.
    public NChar authtypf = new NChar(1);

    // COB:            02  FILLER REDEFINES AUTHTYPF.
    public class Filler123 extends NGroup {
      // COB:              03 AUTHTYPA    PICTURE X.
      public NChar authtypa = new NChar(1);
    }

    public Filler123 filler123 = (Filler123) new Filler123().redefines(authtypf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler125 = new NChar(4);
    // COB:            02  AUTHTYPI  PIC X(14).
    public NChar authtypi = new NChar(14);
    // COB:            02  TRNIDL    COMP  PIC  S9(4).
    public NInt16 trnidl = new NInt16();
    // COB:            02  TRNIDF    PICTURE X.
    public NChar trnidf = new NChar(1);

    // COB:            02  FILLER REDEFINES TRNIDF.
    public class Filler129 extends NGroup {
      // COB:              03 TRNIDA    PICTURE X.
      public NChar trnida = new NChar(1);
    }

    public Filler129 filler129 = (Filler129) new Filler129().redefines(trnidf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler131 = new NChar(4);
    // COB:            02  TRNIDI  PIC X(15).
    public NChar trnidi = new NChar(15);
    // COB:            02  AUTHMTCL    COMP  PIC  S9(4).
    public NInt16 authmtcl = new NInt16();
    // COB:            02  AUTHMTCF    PICTURE X.
    public NChar authmtcf = new NChar(1);

    // COB:            02  FILLER REDEFINES AUTHMTCF.
    public class Filler135 extends NGroup {
      // COB:              03 AUTHMTCA    PICTURE X.
      public NChar authmtca = new NChar(1);
    }

    public Filler135 filler135 = (Filler135) new Filler135().redefines(authmtcf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler137 = new NChar(4);
    // COB:            02  AUTHMTCI  PIC X(1).
    public NChar authmtci = new NChar(1);
    // COB:            02  AUTHFRDL    COMP  PIC  S9(4).
    public NInt16 authfrdl = new NInt16();
    // COB:            02  AUTHFRDF    PICTURE X.
    public NChar authfrdf = new NChar(1);

    // COB:            02  FILLER REDEFINES AUTHFRDF.
    public class Filler141 extends NGroup {
      // COB:              03 AUTHFRDA    PICTURE X.
      public NChar authfrda = new NChar(1);
    }

    public Filler141 filler141 = (Filler141) new Filler141().redefines(authfrdf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler143 = new NChar(4);
    // COB:            02  AUTHFRDI  PIC X(10).
    public NChar authfrdi = new NChar(10);
    // COB:            02  MERNAMEL    COMP  PIC  S9(4).
    public NInt16 mernamel = new NInt16();
    // COB:            02  MERNAMEF    PICTURE X.
    public NChar mernamef = new NChar(1);

    // COB:            02  FILLER REDEFINES MERNAMEF.
    public class Filler147 extends NGroup {
      // COB:              03 MERNAMEA    PICTURE X.
      public NChar mernamea = new NChar(1);
    }

    public Filler147 filler147 = (Filler147) new Filler147().redefines(mernamef);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler149 = new NChar(4);
    // COB:            02  MERNAMEI  PIC X(25).
    public NChar mernamei = new NChar(25);
    // COB:            02  MERIDL    COMP  PIC  S9(4).
    public NInt16 meridl = new NInt16();
    // COB:            02  MERIDF    PICTURE X.
    public NChar meridf = new NChar(1);

    // COB:            02  FILLER REDEFINES MERIDF.
    public class Filler153 extends NGroup {
      // COB:              03 MERIDA    PICTURE X.
      public NChar merida = new NChar(1);
    }

    public Filler153 filler153 = (Filler153) new Filler153().redefines(meridf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler155 = new NChar(4);
    // COB:            02  MERIDI  PIC X(15).
    public NChar meridi = new NChar(15);
    // COB:            02  MERCITYL    COMP  PIC  S9(4).
    public NInt16 mercityl = new NInt16();
    // COB:            02  MERCITYF    PICTURE X.
    public NChar mercityf = new NChar(1);

    // COB:            02  FILLER REDEFINES MERCITYF.
    public class Filler159 extends NGroup {
      // COB:              03 MERCITYA    PICTURE X.
      public NChar mercitya = new NChar(1);
    }

    public Filler159 filler159 = (Filler159) new Filler159().redefines(mercityf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler161 = new NChar(4);
    // COB:            02  MERCITYI  PIC X(25).
    public NChar mercityi = new NChar(25);
    // COB:            02  MERSTL    COMP  PIC  S9(4).
    public NInt16 merstl = new NInt16();
    // COB:            02  MERSTF    PICTURE X.
    public NChar merstf = new NChar(1);

    // COB:            02  FILLER REDEFINES MERSTF.
    public class Filler165 extends NGroup {
      // COB:              03 MERSTA    PICTURE X.
      public NChar mersta = new NChar(1);
    }

    public Filler165 filler165 = (Filler165) new Filler165().redefines(merstf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler167 = new NChar(4);
    // COB:            02  MERSTI  PIC X(2).
    public NChar mersti = new NChar(2);
    // COB:            02  MERZIPL    COMP  PIC  S9(4).
    public NInt16 merzipl = new NInt16();
    // COB:            02  MERZIPF    PICTURE X.
    public NChar merzipf = new NChar(1);

    // COB:            02  FILLER REDEFINES MERZIPF.
    public class Filler171 extends NGroup {
      // COB:              03 MERZIPA    PICTURE X.
      public NChar merzipa = new NChar(1);
    }

    public Filler171 filler171 = (Filler171) new Filler171().redefines(merzipf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler173 = new NChar(4);
    // COB:            02  MERZIPI  PIC X(10).
    public NChar merzipi = new NChar(10);
    // COB:            02  ERRMSGL    COMP  PIC  S9(4).
    public NInt16 errmsgl = new NInt16();
    // COB:            02  ERRMSGF    PICTURE X.
    public NChar errmsgf = new NChar(1);

    // COB:            02  FILLER REDEFINES ERRMSGF.
    public class Filler177 extends NGroup {
      // COB:              03 ERRMSGA    PICTURE X.
      public NChar errmsga = new NChar(1);
    }

    public Filler177 filler177 = (Filler177) new Filler177().redefines(errmsgf);
    // COB:            02  FILLER   PICTURE X(4).
    public NChar filler179 = new NChar(4);
    // COB:            02  ERRMSGI  PIC X(78).
    public NChar errmsgi = new NChar(78);
  }

  public Copau1ai copau1ai = new Copau1ai();

  // COB:        01  COPAU1AO REDEFINES COPAU1AI.
  public class Copau1ao extends NGroup {
    // COB:            02  FILLER PIC X(12).
    public NChar filler182 = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler183 = new NChar(3);
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
    public NChar filler189 = new NChar(3);
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
    public NChar filler195 = new NChar(3);
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
    public NChar filler201 = new NChar(3);
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
    public NChar filler207 = new NChar(3);
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
    public NChar filler213 = new NChar(3);
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
    public NChar filler219 = new NChar(3);
    // COB:            02  CARDNUMC    PICTURE X.
    public NChar cardnumc = new NChar(1);
    // COB:            02  CARDNUMP    PICTURE X.
    public NChar cardnump = new NChar(1);
    // COB:            02  CARDNUMH    PICTURE X.
    public NChar cardnumh = new NChar(1);
    // COB:            02  CARDNUMV    PICTURE X.
    public NChar cardnumv = new NChar(1);
    // COB:            02  CARDNUMO  PIC X(16).
    public NChar cardnumo = new NChar(16);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler225 = new NChar(3);
    // COB:            02  AUTHDTC    PICTURE X.
    public NChar authdtc = new NChar(1);
    // COB:            02  AUTHDTP    PICTURE X.
    public NChar authdtp = new NChar(1);
    // COB:            02  AUTHDTH    PICTURE X.
    public NChar authdth = new NChar(1);
    // COB:            02  AUTHDTV    PICTURE X.
    public NChar authdtv = new NChar(1);
    // COB:            02  AUTHDTO  PIC X(10).
    public NChar authdto = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler231 = new NChar(3);
    // COB:            02  AUTHTMC    PICTURE X.
    public NChar authtmc = new NChar(1);
    // COB:            02  AUTHTMP    PICTURE X.
    public NChar authtmp = new NChar(1);
    // COB:            02  AUTHTMH    PICTURE X.
    public NChar authtmh = new NChar(1);
    // COB:            02  AUTHTMV    PICTURE X.
    public NChar authtmv = new NChar(1);
    // COB:            02  AUTHTMO  PIC X(10).
    public NChar authtmo = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler237 = new NChar(3);
    // COB:            02  AUTHRSPC    PICTURE X.
    public NChar authrspc = new NChar(1);
    // COB:            02  AUTHRSPP    PICTURE X.
    public NChar authrspp = new NChar(1);
    // COB:            02  AUTHRSPH    PICTURE X.
    public NChar authrsph = new NChar(1);
    // COB:            02  AUTHRSPV    PICTURE X.
    public NChar authrspv = new NChar(1);
    // COB:            02  AUTHRSPO  PIC X(1).
    public NChar authrspo = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler243 = new NChar(3);
    // COB:            02  AUTHRSNC    PICTURE X.
    public NChar authrsnc = new NChar(1);
    // COB:            02  AUTHRSNP    PICTURE X.
    public NChar authrsnp = new NChar(1);
    // COB:            02  AUTHRSNH    PICTURE X.
    public NChar authrsnh = new NChar(1);
    // COB:            02  AUTHRSNV    PICTURE X.
    public NChar authrsnv = new NChar(1);
    // COB:            02  AUTHRSNO  PIC X(20).
    public NChar authrsno = new NChar(20);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler249 = new NChar(3);
    // COB:            02  AUTHCDC    PICTURE X.
    public NChar authcdc = new NChar(1);
    // COB:            02  AUTHCDP    PICTURE X.
    public NChar authcdp = new NChar(1);
    // COB:            02  AUTHCDH    PICTURE X.
    public NChar authcdh = new NChar(1);
    // COB:            02  AUTHCDV    PICTURE X.
    public NChar authcdv = new NChar(1);
    // COB:            02  AUTHCDO  PIC X(6).
    public NChar authcdo = new NChar(6);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler255 = new NChar(3);
    // COB:            02  AUTHAMTC    PICTURE X.
    public NChar authamtc = new NChar(1);
    // COB:            02  AUTHAMTP    PICTURE X.
    public NChar authamtp = new NChar(1);
    // COB:            02  AUTHAMTH    PICTURE X.
    public NChar authamth = new NChar(1);
    // COB:            02  AUTHAMTV    PICTURE X.
    public NChar authamtv = new NChar(1);
    // COB:            02  AUTHAMTO  PIC X(12).
    public NChar authamto = new NChar(12);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler261 = new NChar(3);
    // COB:            02  POSEMDC    PICTURE X.
    public NChar posemdc = new NChar(1);
    // COB:            02  POSEMDP    PICTURE X.
    public NChar posemdp = new NChar(1);
    // COB:            02  POSEMDH    PICTURE X.
    public NChar posemdh = new NChar(1);
    // COB:            02  POSEMDV    PICTURE X.
    public NChar posemdv = new NChar(1);
    // COB:            02  POSEMDO  PIC X(4).
    public NChar posemdo = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler267 = new NChar(3);
    // COB:            02  AUTHSRCC    PICTURE X.
    public NChar authsrcc = new NChar(1);
    // COB:            02  AUTHSRCP    PICTURE X.
    public NChar authsrcp = new NChar(1);
    // COB:            02  AUTHSRCH    PICTURE X.
    public NChar authsrch = new NChar(1);
    // COB:            02  AUTHSRCV    PICTURE X.
    public NChar authsrcv = new NChar(1);
    // COB:            02  AUTHSRCO  PIC X(10).
    public NChar authsrco = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler273 = new NChar(3);
    // COB:            02  MCCCDC    PICTURE X.
    public NChar mcccdc = new NChar(1);
    // COB:            02  MCCCDP    PICTURE X.
    public NChar mcccdp = new NChar(1);
    // COB:            02  MCCCDH    PICTURE X.
    public NChar mcccdh = new NChar(1);
    // COB:            02  MCCCDV    PICTURE X.
    public NChar mcccdv = new NChar(1);
    // COB:            02  MCCCDO  PIC X(4).
    public NChar mcccdo = new NChar(4);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler279 = new NChar(3);
    // COB:            02  CRDEXPC    PICTURE X.
    public NChar crdexpc = new NChar(1);
    // COB:            02  CRDEXPP    PICTURE X.
    public NChar crdexpp = new NChar(1);
    // COB:            02  CRDEXPH    PICTURE X.
    public NChar crdexph = new NChar(1);
    // COB:            02  CRDEXPV    PICTURE X.
    public NChar crdexpv = new NChar(1);
    // COB:            02  CRDEXPO  PIC X(5).
    public NChar crdexpo = new NChar(5);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler285 = new NChar(3);
    // COB:            02  AUTHTYPC    PICTURE X.
    public NChar authtypc = new NChar(1);
    // COB:            02  AUTHTYPP    PICTURE X.
    public NChar authtypp = new NChar(1);
    // COB:            02  AUTHTYPH    PICTURE X.
    public NChar authtyph = new NChar(1);
    // COB:            02  AUTHTYPV    PICTURE X.
    public NChar authtypv = new NChar(1);
    // COB:            02  AUTHTYPO  PIC X(14).
    public NChar authtypo = new NChar(14);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler291 = new NChar(3);
    // COB:            02  TRNIDC    PICTURE X.
    public NChar trnidc = new NChar(1);
    // COB:            02  TRNIDP    PICTURE X.
    public NChar trnidp = new NChar(1);
    // COB:            02  TRNIDH    PICTURE X.
    public NChar trnidh = new NChar(1);
    // COB:            02  TRNIDV    PICTURE X.
    public NChar trnidv = new NChar(1);
    // COB:            02  TRNIDO  PIC X(15).
    public NChar trnido = new NChar(15);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler297 = new NChar(3);
    // COB:            02  AUTHMTCC    PICTURE X.
    public NChar authmtcc = new NChar(1);
    // COB:            02  AUTHMTCP    PICTURE X.
    public NChar authmtcp = new NChar(1);
    // COB:            02  AUTHMTCH    PICTURE X.
    public NChar authmtch = new NChar(1);
    // COB:            02  AUTHMTCV    PICTURE X.
    public NChar authmtcv = new NChar(1);
    // COB:            02  AUTHMTCO  PIC X(1).
    public NChar authmtco = new NChar(1);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler303 = new NChar(3);
    // COB:            02  AUTHFRDC    PICTURE X.
    public NChar authfrdc = new NChar(1);
    // COB:            02  AUTHFRDP    PICTURE X.
    public NChar authfrdp = new NChar(1);
    // COB:            02  AUTHFRDH    PICTURE X.
    public NChar authfrdh = new NChar(1);
    // COB:            02  AUTHFRDV    PICTURE X.
    public NChar authfrdv = new NChar(1);
    // COB:            02  AUTHFRDO  PIC X(10).
    public NChar authfrdo = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler309 = new NChar(3);
    // COB:            02  MERNAMEC    PICTURE X.
    public NChar mernamec = new NChar(1);
    // COB:            02  MERNAMEP    PICTURE X.
    public NChar mernamep = new NChar(1);
    // COB:            02  MERNAMEH    PICTURE X.
    public NChar mernameh = new NChar(1);
    // COB:            02  MERNAMEV    PICTURE X.
    public NChar mernamev = new NChar(1);
    // COB:            02  MERNAMEO  PIC X(25).
    public NChar mernameo = new NChar(25);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler315 = new NChar(3);
    // COB:            02  MERIDC    PICTURE X.
    public NChar meridc = new NChar(1);
    // COB:            02  MERIDP    PICTURE X.
    public NChar meridp = new NChar(1);
    // COB:            02  MERIDH    PICTURE X.
    public NChar meridh = new NChar(1);
    // COB:            02  MERIDV    PICTURE X.
    public NChar meridv = new NChar(1);
    // COB:            02  MERIDO  PIC X(15).
    public NChar merido = new NChar(15);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler321 = new NChar(3);
    // COB:            02  MERCITYC    PICTURE X.
    public NChar mercityc = new NChar(1);
    // COB:            02  MERCITYP    PICTURE X.
    public NChar mercityp = new NChar(1);
    // COB:            02  MERCITYH    PICTURE X.
    public NChar mercityh = new NChar(1);
    // COB:            02  MERCITYV    PICTURE X.
    public NChar mercityv = new NChar(1);
    // COB:            02  MERCITYO  PIC X(25).
    public NChar mercityo = new NChar(25);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler327 = new NChar(3);
    // COB:            02  MERSTC    PICTURE X.
    public NChar merstc = new NChar(1);
    // COB:            02  MERSTP    PICTURE X.
    public NChar merstp = new NChar(1);
    // COB:            02  MERSTH    PICTURE X.
    public NChar mersth = new NChar(1);
    // COB:            02  MERSTV    PICTURE X.
    public NChar merstv = new NChar(1);
    // COB:            02  MERSTO  PIC X(2).
    public NChar mersto = new NChar(2);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler333 = new NChar(3);
    // COB:            02  MERZIPC    PICTURE X.
    public NChar merzipc = new NChar(1);
    // COB:            02  MERZIPP    PICTURE X.
    public NChar merzipp = new NChar(1);
    // COB:            02  MERZIPH    PICTURE X.
    public NChar merziph = new NChar(1);
    // COB:            02  MERZIPV    PICTURE X.
    public NChar merzipv = new NChar(1);
    // COB:            02  MERZIPO  PIC X(10).
    public NChar merzipo = new NChar(10);
    // COB:            02  FILLER PICTURE X(3).
    public NChar filler339 = new NChar(3);
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

  public Copau1ao copau1ao = (Copau1ao) new Copau1ao().redefines(copau1ai);
}
