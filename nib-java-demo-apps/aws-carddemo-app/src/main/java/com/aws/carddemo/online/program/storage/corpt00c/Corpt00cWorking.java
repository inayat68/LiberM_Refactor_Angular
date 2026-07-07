package com.aws.carddemo.online.program.storage.corpt00c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Cocom01y;
import com.aws.carddemo.common.copybook.Corpt00;
import com.aws.carddemo.common.copybook.Cottl01y;
import com.aws.carddemo.common.copybook.Csdat01y;
import com.aws.carddemo.common.copybook.Csmsg01y;
import com.aws.carddemo.common.copybook.Cvtra05y;
import com.nib.commons.storage.*;

public class Corpt00cWorking extends NGroup {
  // COB:        01 WS-VARIABLES.
  public WsVariables wsVariables = new WsVariables();

  public static class WsVariables extends NGroup {

    // COB:          05 WS-PGMNAME                 PIC X(08) VALUE 'CORPT00C'.
    public NChar wsPgmname = new NChar(8).initial("CORPT00C");
    // COB:          05 WS-TRANID                  PIC X(04) VALUE 'CR00'.
    public NChar wsTranid = new NChar(4).initial("CR00");
    // COB:          05 WS-MESSAGE                 PIC X(80) VALUE SPACES.
    public NChar wsMessage = new NChar(80).initial(NChar.Space);
    // COB:          05 WS-TRANSACT-FILE             PIC X(08) VALUE 'TRANSACT'.
    public NChar wsTransactFile = new NChar(8).initial("TRANSACT");
    // COB:          05 WS-ERR-FLG                 PIC X(01) VALUE 'N'.
    public NChar wsErrFlg = new NChar(1).initial("N");

    // COB:            88 ERR-FLG-ON                         VALUE 'Y'.
    public boolean errFlgOn() {
      return wsErrFlg.equals("Y");
    }

    public void setErrFlgOn(boolean value) {
      if (value) wsErrFlg.setValue("Y");
    }

    // COB:            88 ERR-FLG-OFF                        VALUE 'N'.
    public boolean errFlgOff() {
      return wsErrFlg.equals("N");
    }

    public void setErrFlgOff(boolean value) {
      if (value) wsErrFlg.setValue("N");
    }

    // COB:          05 WS-TRANSACT-EOF            PIC X(01) VALUE 'N'.
    public NChar wsTransactEof = new NChar(1).initial("N");

    // COB:            88 TRANSACT-EOF                       VALUE 'Y'.
    public boolean transactEof() {
      return wsTransactEof.equals("Y");
    }

    public void setTransactEof(boolean value) {
      if (value) wsTransactEof.setValue("Y");
    }

    // COB:            88 TRANSACT-NOT-EOF                   VALUE 'N'.
    public boolean transactNotEof() {
      return wsTransactEof.equals("N");
    }

    public void setTransactNotEof(boolean value) {
      if (value) wsTransactEof.setValue("N");
    }

    // COB:          05 WS-SEND-ERASE-FLG          PIC X(01) VALUE 'Y'.
    public NChar wsSendEraseFlg = new NChar(1).initial("Y");

    // COB:            88 SEND-ERASE-YES                     VALUE 'Y'.
    public boolean sendEraseYes() {
      return wsSendEraseFlg.equals("Y");
    }

    public void setSendEraseYes(boolean value) {
      if (value) wsSendEraseFlg.setValue("Y");
    }

    // COB:            88 SEND-ERASE-NO                      VALUE 'N'.
    public boolean sendEraseNo() {
      return wsSendEraseFlg.equals("N");
    }

    public void setSendEraseNo(boolean value) {
      if (value) wsSendEraseFlg.setValue("N");
    }

    // COB:          05 WS-END-LOOP                PIC X(01) VALUE 'N'.
    public NChar wsEndLoop = new NChar(1).initial("N");

    // COB:            88 END-LOOP-YES                       VALUE 'Y'.
    public boolean endLoopYes() {
      return wsEndLoop.equals("Y");
    }

    public void setEndLoopYes(boolean value) {
      if (value) wsEndLoop.setValue("Y");
    }

    // COB:            88 END-LOOP-NO                        VALUE 'N'.
    public boolean endLoopNo() {
      return wsEndLoop.equals("N");
    }

    public void setEndLoopNo(boolean value) {
      if (value) wsEndLoop.setValue("N");
    }

    // COB:          05 WS-RESP-CD                 PIC S9(09) COMP VALUE ZEROS.
    public NInt32 wsRespCd = new NInt32().initial(0);
    // COB:          05 WS-REAS-CD                 PIC S9(09) COMP VALUE ZEROS.
    public NInt32 wsReasCd = new NInt32().initial(0);
    // COB:          05 WS-REC-COUNT               PIC S9(04) COMP VALUE ZEROS.
    public NInt16 wsRecCount = new NInt16().initial(0);
    // COB:          05 WS-IDX                     PIC S9(04) COMP VALUE ZEROS.
    public NInt16 wsIdx = new NInt16().initial(0);
    // COB:          05 WS-REPORT-NAME             PIC X(10) VALUE SPACES.
    public NChar wsReportName = new NChar(10).initial(NChar.Space);

    // COB:          05 WS-START-DATE.
    public static class WsStartDate extends NGroup {
      // COB:             10 WS-START-DATE-YYYY      PIC X(04) VALUE SPACES.
      public NChar wsStartDateYyyy = new NChar(4).initial(NChar.Space);
      // COB:             10 FILLER                  PIC X(01) VALUE '-'.
      public NChar filler62 = new NChar(1).initial("-");
      // COB:             10 WS-START-DATE-MM        PIC X(02) VALUE SPACES.
      public NChar wsStartDateMm = new NChar(2).initial(NChar.Space);
      // COB:             10 FILLER                  PIC X(01) VALUE '-'.
      public NChar filler64 = new NChar(1).initial("-");
      // COB:             10 WS-START-DATE-DD        PIC X(02) VALUE SPACES.
      public NChar wsStartDateDd = new NChar(2).initial(NChar.Space);
    }

    public WsStartDate wsStartDate = new WsStartDate();

    // COB:          05 WS-END-DATE.
    public static class WsEndDate extends NGroup {
      // COB:             10 WS-END-DATE-YYYY        PIC X(04) VALUE SPACES.
      public NChar wsEndDateYyyy = new NChar(4).initial(NChar.Space);
      // COB:             10 FILLER                  PIC X(01) VALUE '-'.
      public NChar filler68 = new NChar(1).initial("-");
      // COB:             10 WS-END-DATE-MM          PIC X(02) VALUE SPACES.
      public NChar wsEndDateMm = new NChar(2).initial(NChar.Space);
      // COB:             10 FILLER                  PIC X(01) VALUE '-'.
      public NChar filler70 = new NChar(1).initial("-");
      // COB:             10 WS-END-DATE-DD          PIC X(02) VALUE SPACES.
      public NChar wsEndDateDd = new NChar(2).initial(NChar.Space);
    }

    public WsEndDate wsEndDate = new WsEndDate();
    // COB:          05 WS-DATE-FORMAT             PIC X(10) VALUE 'YYYY-MM-DD'.
    public NChar wsDateFormat = new NChar(10).initial("YYYY-MM-DD");
    // COB:          05 WS-NUM-99                  PIC 99   VALUE 0.
    public NZoned wsNum99 = new NZoned(2, false).initial(0);
    // COB:          05 WS-NUM-9999                PIC 9999 VALUE 0.
    public NZoned wsNum9999 = new NZoned(4, false).initial(0);
    // COB:          05 WS-TRAN-AMT                PIC +99999999.99.
    public NZoned wsTranAmt = new NZoned(12).formatAs("+00000000.00");
    // COB:          05 WS-TRAN-DATE               PIC X(08) VALUE '00/00/00'.
    public NChar wsTranDate = new NChar(8).initial("00/00/00");
    // COB:          05 JCL-RECORD                 PIC X(80) VALUE ' '.
    public NChar jclRecord = new NChar(80).initial(" ");
  }

  // COB:        01 JOB-DATA.
  public JobData jobData = new JobData();

  public static class JobData extends NGroup {

    // COB:         02 JOB-DATA-1.
    public static class JobData1 extends NGroup {
      // COB:          05 FILLER                     PIC X(80) VALUE
      // COB:          "//TRNRPT00 JOB 'TRAN REPORT',CLASS=A,MSGCLASS=0,".
      public NChar filler83 =
          new NChar(80).initial("//TRNRPT00 JOB 'TRAN REPORT',CLASS=A,MSGCLASS=0,");
      // COB:          05 FILLER                     PIC X(80) VALUE
      // COB:          "// NOTIFY=&SYSUID".
      public NChar filler85 = new NChar(80).initial("// NOTIFY=&SYSUID");
      // COB:          05 FILLER                     PIC X(80) VALUE
      // COB:          "//*".
      public NChar filler87 = new NChar(80).initial("//*");
      // COB:          05 FILLER                     PIC X(80) VALUE
      // COB:          "//JOBLIB JCLLIB ORDER=('AWS.M2.CARDDEMO.PROC')".
      public NChar filler89 =
          new NChar(80).initial("//JOBLIB JCLLIB ORDER=('AWS.M2.CARDDEMO.PROC')");
      // COB:          05 FILLER                     PIC X(80) VALUE
      // COB:          "//*".
      public NChar filler91 = new NChar(80).initial("//*");
      // COB:          05 FILLER                     PIC X(80) VALUE
      // COB:          "//STEP10 EXEC PROC=TRANREPT".
      public NChar filler93 = new NChar(80).initial("//STEP10 EXEC PROC=TRANREPT");
      // COB:          05 FILLER                     PIC X(80) VALUE
      // COB:          "//*".
      public NChar filler95 = new NChar(80).initial("//*");
      // COB:          05 FILLER                     PIC X(80) VALUE
      // COB:          "//STEP05R.SYMNAMES DD *".
      public NChar filler97 = new NChar(80).initial("//STEP05R.SYMNAMES DD *");
      // COB:          05 FILLER                     PIC X(80) VALUE
      // COB:          "TRAN-CARD-NUM,263,16,ZD".
      public NChar filler99 = new NChar(80).initial("TRAN-CARD-NUM,263,16,ZD");
      // COB:          05 FILLER                     PIC X(80) VALUE
      // COB:          "TRAN-PROC-DT,305,10,CH".
      public NChar filler101 = new NChar(80).initial("TRAN-PROC-DT,305,10,CH");

      // COB:          05 FILLER-1.
      public static class Filler1 extends NGroup {
        // COB:             10 FILLER                  PIC X(18) VALUE
        // COB:          "PARM-START-DATE,C'".
        public NChar filler104 = new NChar(18).initial("PARM-START-DATE,C'");
        // COB:             10 PARM-START-DATE-1       PIC X(10) VALUE SPACES.
        public NChar parmStartDate1 = new NChar(10).initial(NChar.Space);
        // COB:             10 FILLER                  PIC X(52) VALUE "'".
        public NChar filler107 = new NChar(52).initial("'");
      }

      public Filler1 filler1 = new Filler1();

      // COB:          05 FILLER-2.
      public static class Filler2 extends NGroup {
        // COB:             10 FILLER                  PIC X(16) VALUE
        // COB:          "PARM-END-DATE,C'".
        public NChar filler109 = new NChar(16).initial("PARM-END-DATE,C'");
        // COB:             10 PARM-END-DATE-1         PIC X(10) VALUE SPACES.
        public NChar parmEndDate1 = new NChar(10).initial(NChar.Space);
        // COB:             10 FILLER                  PIC X(54) VALUE "'".
        public NChar filler112 = new NChar(54).initial("'");
      }

      public Filler2 filler2 = new Filler2();
      // COB:          05 FILLER                     PIC X(80) VALUE
      // COB:          "/*".
      public NChar filler113 = new NChar(80).initial("/*");
      // COB:          05 FILLER                     PIC X(80) VALUE
      // COB:          "//STEP10R.DATEPARM DD *".
      public NChar filler115 = new NChar(80).initial("//STEP10R.DATEPARM DD *");

      // COB:          05 FILLER-3.
      public static class Filler3 extends NGroup {
        // COB:             10 PARM-START-DATE-2       PIC X(10) VALUE SPACES.
        public NChar parmStartDate2 = new NChar(10).initial(NChar.Space);
        // COB:             10 FILLER                  PIC X VALUE SPACE.
        public NChar filler119 = new NChar(1).initial(NChar.Space);
        // COB:             10 PARM-END-DATE-2         PIC X(10) VALUE SPACES.
        public NChar parmEndDate2 = new NChar(10).initial(NChar.Space);
        // COB:             10 FILLER                  PIC X(59) VALUE SPACES.
        public NChar filler121 = new NChar(59).initial(NChar.Space);
      }

      public Filler3 filler3 = new Filler3();
      // COB:          05 FILLER                     PIC X(80) VALUE
      // COB:          "/*".
      public NChar filler122 = new NChar(80).initial("/*");
      // COB:          05 FILLER                     PIC X(80) VALUE
      // COB:          "/*EOF".
      public NChar filler124 = new NChar(80).initial("/*EOF");
      // COB:         02 JOB-DATA-1.
      public NChar filler82 = new NChar(78640);
    }

    public JobData1 jobData1 = new JobData1();

    // COB:         02 JOB-DATA-2 REDEFINES JOB-DATA-1.
    public static class JobData2 extends NGroup {
      // COB:          05 JOB-LINES OCCURS 1000 TIMES PIC X(80).
      public NChar[] jobLines = AbstractNField.occurs(1000, new NChar(80));

      public NChar jobLinesAtIndex(int index) {
        return getOccursInstance(jobLines, index);
      }
    }

    public JobData2 jobData2 = (JobData2) new JobData2().redefines(jobData1);
  }

  // COB:        01 CSUTLDTC-PARM.
  public CsutldtcParm csutldtcParm = new CsutldtcParm();

  public static class CsutldtcParm extends NGroup {

    // COB:           05 CSUTLDTC-DATE                   PIC X(10).
    public NChar csutldtcDate = new NChar(10);
    // COB:           05 CSUTLDTC-DATE-FORMAT            PIC X(10).
    public NChar csutldtcDateFormat = new NChar(10);

    // COB:           05 CSUTLDTC-RESULT.
    public static class CsutldtcResult extends NGroup {
      // COB:              10 CSUTLDTC-RESULT-SEV-CD       PIC X(04).
      public NChar csutldtcResultSevCd = new NChar(4);
      // COB:              10 FILLER                       PIC X(11).
      public NChar filler134 = new NChar(11);
      // COB:              10 CSUTLDTC-RESULT-MSG-NUM      PIC X(04).
      public NChar csutldtcResultMsgNum = new NChar(4);
      // COB:              10 CSUTLDTC-RESULT-MSG          PIC X(61).
      public NChar csutldtcResultMsg = new NChar(61);
    }

    public CsutldtcResult csutldtcResult = new CsutldtcResult();
  }

  public Cocom01y cocom01y = new Cocom01y();
  public Corpt00 corpt00 = new Corpt00();
  public Cottl01y cottl01y = new Cottl01y();
  public Csdat01y csdat01y = new Csdat01y();
  public Csmsg01y csmsg01y = new Csmsg01y();
  public Cvtra05y cvtra05y = new Cvtra05y();
}
