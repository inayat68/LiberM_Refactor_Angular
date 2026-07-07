package com.aws.carddemo.batch.program.storage.dsn8bc3;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Dsn8bc3Working extends NGroup {
  // COB:        01  IOAREA.
  public Ioarea ioarea = new Ioarea();

  public static class Ioarea extends NGroup {

    // COB:                02  ACTION             PIC X(01).
    public NChar action = new NChar(1);
    // COB:                02  LNAME              PIC X(15).
    public NChar lname = new NChar(15);
    // COB:                02  FNAME              PIC X(12).
    public NChar fname = new NChar(12);
    // COB:                02  ENO                PIC X(06).
    public NChar eno = new NChar(6);
    // COB:                02  NEWNO              PIC X(04).
    public NChar newno = new NChar(4);
    // COB:                02  FILLER             PIC X(42).
    public NChar filler166 = new NChar(42);
  }

  // COB:        01  REPHDR1.
  public Rephdr1 rephdr1 = new Rephdr1();

  public static class Rephdr1 extends NGroup {

    // COB:                02  FILLER PIC X(29)
    // COB:                    VALUE '-----------------------------'.
    public NChar filler172 = new NChar(29).initial("-----------------------------");
    // COB:                02  FILLER PIC X(21)
    // COB:                    VALUE ' TELEPHONE DIRECTORY '.
    public NChar filler174 = new NChar(21).initial(" TELEPHONE DIRECTORY ");
    // COB:                02  FILLER PIC X(29)
    // COB:                    VALUE '-----------------------------'.
    public NChar filler176 = new NChar(29).initial("-----------------------------");
  }

  // COB:        01  REPHDR2.
  public Rephdr2 rephdr2 = new Rephdr2();

  public static class Rephdr2 extends NGroup {

    // COB:                02  FILLER PIC X(09) VALUE 'LAST NAME'.
    public NChar filler179 = new NChar(9).initial("LAST NAME");
    // COB:                02  FILLER PIC X(07) VALUE SPACES.
    public NChar filler180 = new NChar(7).initial(NChar.Space);
    // COB:                02  FILLER PIC X(10) VALUE 'FIRST NAME'.
    public NChar filler181 = new NChar(10).initial("FIRST NAME");
    // COB:                02  FILLER PIC X(03) VALUE SPACES.
    public NChar filler182 = new NChar(3).initial(NChar.Space);
    // COB:                02  FILLER PIC X(08) VALUE 'INITIAL'.
    public NChar filler183 = new NChar(8).initial("INITIAL");
    // COB:                02  FILLER PIC X(07) VALUE 'PHONE'.
    public NChar filler184 = new NChar(7).initial("PHONE");
    // COB:                02  FILLER PIC X(09) VALUE 'EMPLOYEE'.
    public NChar filler185 = new NChar(9).initial("EMPLOYEE");
    // COB:                02  FILLER PIC X(05) VALUE 'WORK'.
    public NChar filler186 = new NChar(5).initial("WORK");
    // COB:                02  FILLER PIC X(04) VALUE 'WORK'.
    public NChar filler187 = new NChar(4).initial("WORK");
  }

  // COB:        01  REPHDR3.
  public Rephdr3 rephdr3 = new Rephdr3();

  public static class Rephdr3 extends NGroup {

    // COB:                02  FILLER PIC X(37) VALUE SPACES.
    public NChar filler189 = new NChar(37).initial(NChar.Space);
    // COB:                02  FILLER PIC X(07) VALUE 'NUMBER'.
    public NChar filler190 = new NChar(7).initial("NUMBER");
    // COB:                02  FILLER PIC X(09) VALUE 'NUMBER'.
    public NChar filler191 = new NChar(9).initial("NUMBER");
    // COB:                02  FILLER PIC X(05) VALUE 'DEPT'.
    public NChar filler192 = new NChar(5).initial("DEPT");
    // COB:                02  FILLER PIC X(05) VALUE 'DEPT'.
    public NChar filler193 = new NChar(5).initial("DEPT");
    // COB:                02  FILLER PIC X(04) VALUE 'NAME'.
    public NChar filler194 = new NChar(4).initial("NAME");
  }

  // COB:        01  REPDATA.
  public Repdata repdata = new Repdata();

  public static class Repdata extends NGroup {

    // COB:                02  RLNAME    PIC X(15).
    public NChar rlname = new NChar(15);
    // COB:                02  FILLER    PIC X(01) VALUE SPACES.
    public NChar filler201 = new NChar(1).initial(NChar.Space);
    // COB:                02  RFNAME    PIC X(12).
    public NChar rfname = new NChar(12);
    // COB:                02  FILLER    PIC X(04) VALUE SPACES.
    public NChar filler203 = new NChar(4).initial(NChar.Space);
    // COB:                02  RMIDINIT  PIC X(01).
    public NChar rmidinit = new NChar(1);
    // COB:                02  FILLER    PIC X(04) VALUE SPACES.
    public NChar filler205 = new NChar(4).initial(NChar.Space);
    // COB:                02  RPHONE    PIC X(04).
    public NChar rphone = new NChar(4);
    // COB:                02  FILLER    PIC X(03) VALUE SPACES.
    public NChar filler207 = new NChar(3).initial(NChar.Space);
    // COB:                02  REMPNO    PIC X(06).
    public NChar rempno = new NChar(6);
    // COB:                02  FILLER    PIC X(03) VALUE SPACES.
    public NChar filler209 = new NChar(3).initial(NChar.Space);
    // COB:                02  RDEPTNO   PIC X(03).
    public NChar rdeptno = new NChar(3);
    // COB:                02  FILLER    PIC X(02) VALUE SPACES.
    public NChar filler211 = new NChar(2).initial(NChar.Space);
    // COB:                02  RDEPTNAME PIC X(36).
    public NChar rdeptname = new NChar(36);
  }

  // COB:        01  LNAME-WORK.
  public LnameWork lnameWork = new LnameWork();

  public static class LnameWork extends NGroup {

    // COB:             49 LNAME-WORKL     PIC S9(4)  COMP.
    public NInt16 lnameWorkl = new NInt16();
    // COB:             49 LNAME-WORKC     PIC X(15).
    public NChar lnameWorkc = new NChar(15);
  }

  // COB:        01  FNAME-WORK.
  public FnameWork fnameWork = new FnameWork();

  public static class FnameWork extends NGroup {

    // COB:             49 FNAME-WORKL     PIC S9(4)  COMP.
    public NInt16 fnameWorkl = new NInt16();
    // COB:             49 FNAME-WORKC     PIC X(12).
    public NChar fnameWorkc = new NChar(12);
  }

  // COB:        77  INPUT-SWITCH        PIC X          VALUE  'Y'.
  public NChar inputSwitch = new NChar(1).initial("Y");

  // COB:                88  NOMORE-INPUT               VALUE  'N'.
  public boolean nomoreInput() {
    return inputSwitch.equals("N");
  }

  public void setNomoreInput(boolean value) {
    if (value) inputSwitch.setValue("N");
  }

  // COB:        77  NOT-FOUND           PIC S9(9) COMP VALUE  +100.
  public NInt32 notFound = new NInt32().initial(100);
  // COB:        01  ERROR-MESSAGE.
  public ErrorMessage errorMessage = new ErrorMessage();

  public static class ErrorMessage extends NGroup {

    // COB:                02  ERROR-LEN   PIC S9(4)  COMP VALUE +960.
    public NInt16 errorLen = new NInt16().initial(960);
    // COB:                02  ERROR-TEXT  PIC X(120) OCCURS 10 TIMES
    public NChar[] errorText = AbstractNField.occurs(10, new NChar(120));

    public NChar errorTextAtIndex(int index) {
      return getOccursInstance(errorText, index);
    }
  }

  // COB:        77  ERROR-TEXT-LEN      PIC S9(9)  COMP VALUE +120.
  public NInt32 errorTextLen = new NInt32().initial(120);
  // COB:        01  PPHONE.
  public Pphone pphone = new Pphone();

  public static class Pphone extends NGroup {

    // COB:               02  LASTNAME.
    public static class Lastname extends NGroup {
      // COB:                     49  LASTNAMEL      PIC S9(4)  COMP.
      public NInt16 lastnamel = new NInt16();
      // COB:                     49  LASTNAMEC      PIC X(15)  VALUE SPACES.
      public NChar lastnamec = new NChar(15).initial(NChar.Space);
    }

    public Lastname lastname = new Lastname();

    // COB:               02  FIRSTNAME.
    public static class Firstname extends NGroup {
      // COB:                     49  FIRSTNAMEL     PIC S9(4)  COMP.
      public NInt16 firstnamel = new NInt16();
      // COB:                     49  FIRSTNAMEC     PIC X(12)  VALUE SPACES.
      public NChar firstnamec = new NChar(12).initial(NChar.Space);
    }

    public Firstname firstname = new Firstname();
    // COB:               02  MIDDLEINITIAL        PIC X(01).
    public NChar middleinitial = new NChar(1);
    // COB:               02  PHONENUMBER          PIC X(04).
    public NChar phonenumber = new NChar(4);
    // COB:               02  EMPLOYEENUMBER       PIC X(06).
    public NChar employeenumber = new NChar(6);
    // COB:               02  DEPTNUMBER           PIC X(03).
    public NChar deptnumber = new NChar(3);

    // COB:               02  DEPTNAME.
    public static class Deptname extends NGroup {
      // COB:                     49  DEPTNAMEL      PIC S9(4)  COMP.
      public NInt16 deptnamel = new NInt16();
      // COB:                     49  DEPTNAMEC      PIC X(36)  VALUE SPACES.
      public NChar deptnamec = new NChar(36).initial(NChar.Space);
    }

    public Deptname deptname = new Deptname();
  }

  // COB:        77  PERCENT-COUNTER             PIC S9(4)  COMP.
  public NInt16 percentCounter = new NInt16();
  // COB:        01  MAJOR                     PIC X(07) VALUE 'DSN8BC3'.
  public NChar major = new NChar(7).initial("DSN8BC3");
  // COB:        01  MSGCODE                   PIC X(4).
  public NChar msgcode = new NChar(4);
  // COB:        01  OUTMSG                    PIC X(69).
  public NChar outmsg = new NChar(69);
  // COB:        01  MSG-REC1.
  public MsgRec1 msgRec1 = new MsgRec1();

  public static class MsgRec1 extends NGroup {

    // COB:                02 OUTMSG1            PIC X(69).
    public NChar outmsg1 = new NChar(69);
    // COB:                02 RETCODE            PIC S9(9).
    public NZoned retcode = new NZoned(9);
  }

  // COB:        01  MSG-REC2.
  public MsgRec2 msgRec2 = new MsgRec2();

  public static class MsgRec2 extends NGroup {

    // COB:                02 OUTMSG2            PIC X(69).
    public NChar outmsg2 = new NChar(69);
  }

  public NIndex errorIndex = new NIndex(1);
}
