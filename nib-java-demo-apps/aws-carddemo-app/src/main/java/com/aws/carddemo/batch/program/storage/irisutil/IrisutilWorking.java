package com.aws.carddemo.batch.program.storage.irisutil;

import com.aws.carddemo.common.copybook.Iriscomm;
import com.aws.carddemo.common.copybook.Irissqlc;
import com.nib.commons.storage.*;

public class IrisutilWorking extends NGroup {
  public Iriscomm iriscomm = new Iriscomm();
  public Irissqlc irissqlc = new Irissqlc();
  // COB:        01 WS-IMS-API                  PIC X(8) VALUE SPACES.
  public NChar wsImsApi = new NChar(8).initial(NChar.Space);
  // COB:        01 WS-JOBNAME-PTR                USAGE POINTER.
  public NPointer wsJobnamePtr = new NPointer();
  // COB:        01 WS-JOBNAME-ADDR  REDEFINES WS-JOBNAME-PTR  PIC S9(9) COMP.
  public NInt32 wsJobnameAddr = new NInt32().redefines(wsJobnamePtr);
  // COB:        01 WS-JOBNAME-CHARS              PIC X(8).
  public NChar wsJobnameChars = new NChar(8);
  // COB:        01 WS-JOBNAME-JBNM               PIC X(8).
  public NChar wsJobnameJbnm = new NChar(8);
  // COB:        01 WS-JULIAN-DATE.
  public WsJulianDate wsJulianDate = new WsJulianDate();

  public class WsJulianDate extends NGroup {

    // COB:          03 WS-JULIAN-YEAR              PIC X(4).
    public NChar wsJulianYear = new NChar(4);
    // COB:          03 WS-JULIAN-DAY               PIC X(3).
    public NChar wsJulianDay = new NChar(3);
  }

  // COB:        01 WS-TIME.
  public WsTime wsTime = new WsTime();

  public class WsTime extends NGroup {

    // COB:          03 WS-TIME-HH                  PIC X(2).
    public NChar wsTimeHh = new NChar(2);
    // COB:          03 WS-TIME-MM                  PIC X(2).
    public NChar wsTimeMm = new NChar(2);
    // COB:          03 WS-TIME-SS                  PIC X(2).
    public NChar wsTimeSs = new NChar(2);
    // COB:          03 WS-TIME-CC                  PIC X(2).
    public NChar wsTimeCc = new NChar(2);
  }

  // COB:        01 WS-CURRENT-DATETIME.
  public WsCurrentDatetime wsCurrentDatetime = new WsCurrentDatetime();

  public class WsCurrentDatetime extends NGroup {

    // COB:          03 WS-CURRENT-DATE             PIC X(8).
    public NChar wsCurrentDate = new NChar(8);
    // COB:          03 WS-CURRENT-TIME             PIC X(8).
    public NChar wsCurrentTime = new NChar(8);
    // COB:          03 WS-CURRENT-GT               PIC X(5).
    public NChar wsCurrentGt = new NChar(5);
  }

  // COB:        01 WS-TRACE-LEVEL                PIC X.
  public NChar wsTraceLevel = new NChar(1);
  // COB:        01 WS-SQLCODE-N                  PIC S9(5).
  public NZoned wsSqlcodeN = new NZoned(5);
  // COB:        01 WS-SQLCODE-E                  PIC -ZZZZ9.
  public NZoned wsSqlcodeE = new NZoned(6).formatAs("-####0");
  // COB:        01 WS-CLEAN-UP                   PIC S9(8) COMP VALUE +1.
  public NInt32 wsCleanUp = new NInt32().initial(1);
  // COB:        01 WS-ABEND-CODE                 PIC X(4).
  public NChar wsAbendCode = new NChar(4);
  // COB:        01 WS-PARAM-NUM                  PIC S9(9) COMP.
  public NInt32 wsParamNum = new NInt32();
  // COB:        01 WS-SCHED-PARAM-NUM            PIC S9(9) COMP.
  public NInt32 wsSchedParamNum = new NInt32();
  // COB:        01 WS-MESSAGE-ID-EDITED          PIC ZZZZ9.
  public NZoned wsMessageIdEdited = new NZoned(5, false).formatAs("####0");
  // COB:        01 WS-MESSAGE                    PIC X(32000).
  public NChar wsMessage = new NChar(32000);
  // COB:        01 WS-ERROR-DESCRIPTION          PIC X(80).
  public NChar wsErrorDescription = new NChar(80);
  // COB:        01 WS-ERROR-DESCRIPTION-LEN      PIC S9(4)   COMP.
  public NInt16 wsErrorDescriptionLen = new NInt16();
  // COB:        01 WS-INDEX                      PIC S9(4)   COMP.
  public NInt16 wsIndex = new NInt16();
  // COB:        01 WS-INDEX-C                    PIC S9(4)   COMP.
  public NInt16 wsIndexC = new NInt16();
  // COB:        01 WS-INDEX-2                    PIC S9(4)   COMP.
  public NInt16 wsIndex2 = new NInt16();
  // COB:        01 WS-INDEX-Z                    PIC ZZ9.
  public NZoned wsIndexZ = new NZoned(3, false).formatAs("##0");
  // COB:        01 WS-ADDR-9                     PIC 9(9).
  public NZoned wsAddr9 = new NZoned(9, false);
  // COB:        01 WS-LAST-IORTN-SECTION         PIC X(30).
  public NChar wsLastIortnSection = new NChar(30);
  // COB:        01 WS-PTR                        POINTER.
  public NPointer wsPtr = new NPointer();
  // COB: TMPDBG 01 WS-ADDR REDEFINES WS-PTR PIC S9(9) COMP.
  public NInt32 wsAddr = new NInt32().redefines(wsPtr);
  // COB:        01 WS-PSB-PCBS-PTR               POINTER.
  public NPointer wsPsbPcbsPtr = new NPointer();
  // COB:        01 WS-PSB-SENSEGS-PTR            POINTER.
  public NPointer wsPsbSensegsPtr = new NPointer();
  // COB:        01 WS-TABLE-NAME                 PIC X(8).
  public NChar wsTableName = new NChar(8);
  // COB:        01 WS-TABLE-ACCESS               PIC X(8).
  public NChar wsTableAccess = new NChar(8);
  // COB:        01 WS-TSK-NR                     PIC S9(8) COMP-3 VALUE ZERO.
  public NPacked wsTskNr = new NPacked(5).initial(0);
  // COB:        01 WS-IO-RTN.
  public WsIoRtn wsIoRtn = new WsIoRtn();

  public class WsIoRtn extends NGroup {

    // COB:          03 WS-IO-RTN-DBDNAME           PIC X(8)       VALUE SPACES.
    public NChar wsIoRtnDbdname = new NChar(8).initial(NChar.Space);

    // COB:          03 FILLER REDEFINES WS-IO-RTN-DBDNAME.
    public class Filler78 extends NGroup {
      // COB:            05 FILLER                    PIC X(6).
      public NChar filler79 = new NChar(6);

      // COB: IRISDC     88 RTN-IS-PCBIO                             VALUE 'PCB_IO',
      public boolean rtnIsPcbio() {
        return filler79.equals("PCB_IO") || filler79.equals("ALT_IO");
      }

      public void setRtnIsPcbio(boolean value) {
        if (value) filler79.setValue("PCB_IO");
      }

      // COB:            88 RTN-IS-GSAM                              VALUE 'GSAMIO'.
      public boolean rtnIsGsam() {
        return filler79.equals("GSAMIO");
      }

      public void setRtnIsGsam(boolean value) {
        if (value) filler79.setValue("GSAMIO");
      }

      // COB:            05 FILLER                    PIC X(2).
      public NChar filler83 = new NChar(2);
    }

    public Filler78 filler78 = (Filler78) new Filler78().redefines(wsIoRtnDbdname);

    // COB:          03 FILLER REDEFINES WS-IO-RTN-DBDNAME.
    public class Filler84 extends NGroup {
      // COB:            05 FILLER                    PIC X(2).
      public NChar filler88 = new NChar(2);
      // COB:            05 FILLER                    PIC X(1).
      public NChar filler89 = new NChar(1);

      // COB:              88 RTN-IS-GSAMDBD                         VALUE 'G'.
      public boolean rtnIsGsamdbd() {
        return filler89.equals("G");
      }

      public void setRtnIsGsamdbd(boolean value) {
        if (value) filler89.setValue("G");
      }

      // COB:            05 FILLER                    PIC X(5).
      public NChar filler91 = new NChar(5);
    }

    public Filler84 filler84 = (Filler84) new Filler84().redefines(wsIoRtnDbdname);
  }

  // COB:        01 WS-DUMMY-PCB.
  public WsDummyPcb wsDummyPcb = new WsDummyPcb();

  public class WsDummyPcb extends NGroup {

    // COB:          03 WS-DUMMY-PCB-HEADER         PIC X(36).
    public NChar wsDummyPcbHeader = new NChar(36);

    // COB:          03 FILLER REDEFINES WS-DUMMY-PCB-HEADER.
    public class Filler94 extends NGroup {
      // COB:            05 WS-DUMMY-DBD-NAME         PIC X(8).
      public NChar wsDummyDbdName = new NChar(8);
      // COB:            05 WS-DUMMY-SEG-LVL          PIC 9(2).
      public NZoned wsDummySegLvl = new NZoned(2, false);
      // COB:            05 WS-DUMMY-STS-CODE         PIC X(2).
      public NChar wsDummyStsCode = new NChar(2);
      // COB:            05 WS-DUMMY-PROC-OPT         PIC X(4).
      public NChar wsDummyProcOpt = new NChar(4);
      // COB:            05 WS-DUMMY-PCB-RESERVED     PIC S9(9)  COMP.
      public NInt32 wsDummyPcbReserved = new NInt32();
      // COB:            05 FILLER REDEFINES WS-DUMMY-PCB-RESERVED PIC X(4).
      public NChar filler100 = new NChar(4).redefines(wsDummyPcbReserved);

      // COB:              88 WS-DUMMY-PCB-IRIS-EYE   VALUE 'IRIS'.
      public boolean wsDummyPcbIrisEye() {
        return filler100.equals("IRIS");
      }

      public void setWsDummyPcbIrisEye(boolean value) {
        if (value) filler100.setValue("IRIS");
      }
    }

    public Filler94 filler94 = (Filler94) new Filler94().redefines(wsDummyPcbHeader);
    // COB:          03 WS-DUMMY-FB-KEY             PIC X(1587).
    public NChar wsDummyFbKey = new NChar(1587);
    // COB:          03 WS-DUMMY-FB-RNTM            PIC X(377).
    public NChar wsDummyFbRntm = new NChar(377);
    // COB:          03 WS-DUMMY-CNTL-FIXED         PIC X(1000).
    public NChar wsDummyCntlFixed = new NChar(1000);
    // COB:          03 WS-DUMMY-CNTL-VAR           PIC X(32000).
    public NChar wsDummyCntlVar = new NChar(32000);
  }

  // COB:        01 IRIS-DLIUIB.
  public IrisDliuib irisDliuib = new IrisDliuib();

  public class IrisDliuib extends NGroup {

    // COB:          03 IRIS-UIBPCBAL               POINTER.
    public NPointer irisUibpcbal = new NPointer();

    // COB:          03 IRIS-UIBRCODE.
    public class IrisUibrcode extends NGroup {
      // COB:               05 IRIS-UIBFCTR           PIC X.
      public NChar irisUibfctr = new NChar(1);
      // COB:               05 IRIS-UIBDLTR           PIC X.
      public NChar irisUibdltr = new NChar(1);
    }

    public IrisUibrcode irisUibrcode = new IrisUibrcode();
  }

  // COB:        01 FILLER                        PIC S9(4)   COMP.
  public NInt16 filler113 = new NInt16();

  // COB:          88 WS-PSB-NOT-ALLOCATED        VALUE 0.
  public boolean wsPsbNotAllocated() {
    return filler113.equals(0);
  }

  public void setWsPsbNotAllocated(boolean value) {
    if (value) filler113.setValue(0);
  }

  // COB:          88 WS-PSB-ALLOCATED            VALUE 1.
  public boolean wsPsbAllocated() {
    return filler113.equals(1);
  }

  public void setWsPsbAllocated(boolean value) {
    if (value) filler113.setValue(1);
  }

  // COB:        01 FILLER                        PIC S9(4)   COMP.
  public NInt16 filler116 = new NInt16();

  // COB:          88 WS-IRISDBD-OK              VALUE 0.
  public boolean wsIrisdbdOk() {
    return filler116.equals(0);
  }

  public void setWsIrisdbdOk(boolean value) {
    if (value) filler116.setValue(0);
  }

  // COB:          88 WS-IRISDBD-NOK             VALUE 1.
  public boolean wsIrisdbdNok() {
    return filler116.equals(1);
  }

  public void setWsIrisdbdNok(boolean value) {
    if (value) filler116.setValue(1);
  }

  // COB:        01 WS-RUN-DBD-NAME               PIC X(8).
  public NChar wsRunDbdName = new NChar(8);
  // COB:        01 WS-RUN-DBD-STATUS             PIC S9(4)   COMP.
  public NInt16 wsRunDbdStatus = new NInt16();

  // COB:          88 WS-DUAL-IMS-ONLY            VALUE 1.
  public boolean wsDualImsOnly() {
    return wsRunDbdStatus.equals(1);
  }

  public void setWsDualImsOnly(boolean value) {
    if (value) wsRunDbdStatus.setValue(1);
  }

  // COB:          88 WS-DUAL-SQL-ONLY            VALUE 2.
  public boolean wsDualSqlOnly() {
    return wsRunDbdStatus.equals(2);
  }

  public void setWsDualSqlOnly(boolean value) {
    if (value) wsRunDbdStatus.setValue(2);
  }

  // COB:          88 WS-DUAL-BOTH                VALUE 3 6.
  public boolean wsDualBoth() {
    return wsRunDbdStatus.equals(3) || wsRunDbdStatus.equals(6);
  }

  public void setWsDualBoth(boolean value) {
    if (value) wsRunDbdStatus.setValue(3);
  }

  // COB:          88 WS-DUAL-UPDATE-ONLY         VALUE 4.
  public boolean wsDualUpdateOnly() {
    return wsRunDbdStatus.equals(4);
  }

  public void setWsDualUpdateOnly(boolean value) {
    if (value) wsRunDbdStatus.setValue(4);
  }

  // COB:          88 WS-DUAL-BOTH-IMS-PRIMARY    VALUE 5 7.
  public boolean wsDualBothImsPrimary() {
    return wsRunDbdStatus.equals(5) || wsRunDbdStatus.equals(7);
  }

  public void setWsDualBothImsPrimary(boolean value) {
    if (value) wsRunDbdStatus.setValue(5);
  }

  // COB:          88 WS-DUAL-BOTH-NO-ABEND       VALUE 6.
  public boolean wsDualBothNoAbend() {
    return wsRunDbdStatus.equals(6);
  }

  public void setWsDualBothNoAbend(boolean value) {
    if (value) wsRunDbdStatus.setValue(6);
  }

  // COB:          88 WS-DUAL-BOTH-IMS-PR-NO-ABEND VALUE 7.
  public boolean wsDualBothImsPrNoAbend() {
    return wsRunDbdStatus.equals(7);
  }

  public void setWsDualBothImsPrNoAbend(boolean value) {
    if (value) wsRunDbdStatus.setValue(7);
  }

  // COB:          88 WS-TP                       VALUE 99.
  public boolean wsTp() {
    return wsRunDbdStatus.equals(99);
  }

  public void setWsTp(boolean value) {
    if (value) wsRunDbdStatus.setValue(99);
  }

  // COB:        01 WS-RUN-DBD-STATUS-DECOD.
  public WsRunDbdStatusDecod wsRunDbdStatusDecod = new WsRunDbdStatusDecod();

  public class WsRunDbdStatusDecod extends NGroup {

    // COB:          03 FILLER PIC X(20) VALUE 'IMS ONLY'.
    public NChar filler131 = new NChar(20).initial("IMS ONLY");
    // COB:          03 FILLER PIC X(20) VALUE 'SQL ONLY'.
    public NChar filler132 = new NChar(20).initial("SQL ONLY");
    // COB:          03 FILLER PIC X(20) VALUE 'DUAL DB2 PRIMARY'.
    public NChar filler133 = new NChar(20).initial("DUAL DB2 PRIMARY");
    // COB:          03 FILLER PIC X(20) VALUE 'DUAL UPDATE ONLY'.
    public NChar filler134 = new NChar(20).initial("DUAL UPDATE ONLY");
    // COB:          03 FILLER PIC X(20) VALUE 'DUAL IMS PRIMARY'.
    public NChar filler135 = new NChar(20).initial("DUAL IMS PRIMARY");
    // COB:          03 FILLER PIC X(20) VALUE 'DUAL LIGHT DB2 PR.'.
    public NChar filler136 = new NChar(20).initial("DUAL LIGHT DB2 PR.");
    // COB:          03 FILLER PIC X(20) VALUE 'DUAL LIGHT IMS PR.'.
    public NChar filler137 = new NChar(20).initial("DUAL LIGHT IMS PR.");
  }

  // COB:        01 FILLER REDEFINES WS-RUN-DBD-STATUS-DECOD.
  public class Filler138 extends NGroup {
    // COB:          03 WS-DUAL-DECOD PIC X(20) OCCURS 7.
    public NChar[] wsDualDecod = AbstractNField.occurs(7, new NChar(20));

    public NChar wsDualDecodAtIndex(int index) {
      return getOccursInstance(wsDualDecod, index);
    }
  }

  public Filler138 filler138 = (Filler138) new Filler138().redefines(wsRunDbdStatusDecod);
  // COB:        01 WS-DBD-LOOKUP-TABLE-COUNT     PIC S9(4)   COMP.
  public NInt16 wsDbdLookupTableCount = new NInt16();
  // COB:        01 WS-DBD-LOOKUP-AREA.
  public WsDbdLookupArea wsDbdLookupArea = new WsDbdLookupArea();

  public class WsDbdLookupArea extends NGroup {

    // COB:          03 WS-DBD-LOOKUP-TABLE         OCCURS 255.
    public class WsDbdLookupTable extends NGroup {
      // COB:            05 WS-LOOKUP-DBD             PIC X(3).
      public NChar wsLookupDbd = new NChar(3);
      // COB:            05 WS-LOOKUP-STATUS          PIC S9(4)   COMP.
      public NInt16 wsLookupStatus = new NInt16();
    }

    public WsDbdLookupTable[] wsDbdLookupTable = AbstractNField.occurs(255, new WsDbdLookupTable());

    public WsDbdLookupTable wsDbdLookupTableAtIndex(int index) {
      return getOccursInstance(wsDbdLookupTable, index);
    }
  }

  // COB:        01 WS-CMPAT-IDX                  PIC S9(4)   COMP.
  public NInt16 wsCmpatIdx = new NInt16();
  // COB:        01 WS-CHKP-ID                    PIC X(14).
  public NChar wsChkpId = new NChar(14);
  // COB:        01 WS-IRIS-JOB-NAME              PIC X(08).
  public NChar wsIrisJobName = new NChar(8);
  // COB:        01 WS-IRIS-PGM-NAME              PIC X(08).
  public NChar wsIrisPgmName = new NChar(8);
  // COB:        01 WS-IRIS-PSB-NAME              PIC X(08).
  public NChar wsIrisPsbName = new NChar(8);
  // COB:        01 IRIS-IDX                      PIC S9(4) COMP.
  public NInt16 irisIdx = new NInt16();
  // COB:        01 IRIS-MAX-PCBS                 PIC S9(4)   COMP  VALUE 45.
  public NInt16 irisMaxPcbs = new NInt16().initial(45);
  // COB:        01 WS-DBD-NAME                   PIC X(3) VALUE SPACE.
  public NChar wsDbdName = new NChar(3).initial(NChar.Space);
  // COB:        01 WS-CHECKPOINT-TIME    PIC X(16).
  public NChar wsCheckpointTime = new NChar(16);
  // COB:        01 WS-SQLCODE            PIC S9(9) COMP.
  public NInt32 wsSqlcode = new NInt32();
  // COB:        01 IRIS-IO-AREAS.
  public IrisIoAreas irisIoAreas = new IrisIoAreas();

  public class IrisIoAreas extends NGroup {

    // COB:          03 FILLER                      OCCURS 45.
    public class Filler164 extends NGroup {
      // COB:            05 IRIS-IO-AREA              PIC X(1000).
      public NChar irisIoArea = new NChar(1000);
    }

    public Filler164[] filler164 = AbstractNField.occurs(45, new Filler164());

    public Filler164 filler164AtIndex(int index) {
      return getOccursInstance(filler164, index);
    }
  }

  // COB:        01 IRIS-WS-BLL-POINTER           POINTER.
  public NPointer irisWsBllPointer = new NPointer();
  // COB:        01 IRIS-WS-BLL-ADDR REDEFINES IRIS-WS-BLL-POINTER PIC S9(9) COMP.
  public NInt32 irisWsBllAddr = new NInt32().redefines(irisWsBllPointer);
  // COB:        01 IRIS-BLL-CELLS.
  public IrisBllCells irisBllCells = new IrisBllCells();

  public class IrisBllCells extends NGroup {

    // COB:          03 IRIS-BLL-CELL               OCCURS 45.
    public class IrisBllCell extends NGroup {
      // COB:            05 IRIS-BLL-POINTER          POINTER.
      public NPointer irisBllPointer = new NPointer();
    }

    public IrisBllCell[] irisBllCell = AbstractNField.occurs(45, new IrisBllCell());

    public IrisBllCell irisBllCellAtIndex(int index) {
      return getOccursInstance(irisBllCell, index);
    }
  }

  // COB:        01 IRIS-PCBS-AREA.
  public IrisPcbsArea irisPcbsArea = new IrisPcbsArea();

  public class IrisPcbsArea extends NGroup {

    // COB:          03 IRIS-PCBS-TAB               OCCURS 45.
    public class IrisPcbsTab extends NGroup {
      // COB:            05 IRIS-PCB-HEADER.
      public class IrisPcbHeader extends NGroup {
        // COB:              07 IRIS-PCB-DBD-NAME       PIC X(8).
        public NChar irisPcbDbdName = new NChar(8);
        // COB:              07 IRIS-PCB-SEGMENT-LEVEL  PIC 9(2).
        public NZoned irisPcbSegmentLevel = new NZoned(2, false);
        // COB:              07 IRIS-PCB-STATUS-CODE    PIC X(2).
        public NChar irisPcbStatusCode = new NChar(2);
        // COB:              07 IRIS-PCB-PROC-OPTS      PIC X(4).
        public NChar irisPcbProcOpts = new NChar(4);
        // COB:              07 IRIS-PCB-RESERVED       PIC S9(9)  COMP.
        public NInt32 irisPcbReserved = new NInt32();
        // COB:              07 IRIS-PCB-SEGMENT-NAME   PIC X(8).
        public NChar irisPcbSegmentName = new NChar(8);
        // COB:              07 IRIS-PCB-FB-KEY-LENGTH  PIC S9(9)  COMP.
        public NInt32 irisPcbFbKeyLength = new NInt32();
        // COB:              07 IRIS-PCB-NUM-SENSEGS    PIC S9(9)  COMP.
        public NInt32 irisPcbNumSensegs = new NInt32();
      }

      public IrisPcbHeader irisPcbHeader = new IrisPcbHeader();
      // COB:            05  IRIS-PCB-FEEDBACK        PIC X(1587).
      public NChar irisPcbFeedback = new NChar(1587);

      // COB:        01 MEMORY-PCB-AREA.
      public class MemoryPcbArea extends NGroup {
        // COB:          07 DT-ROOT-SEGM                     PIC S9(9) COMP.
        public NInt32 dtRootSegm = new NInt32();
        // COB:          07 DT-PHYS-ROOT-SEGM                PIC S9(9) COMP.
        public NInt32 dtPhysRootSegm = new NInt32();
        // COB:          07 DT-CURR-SEGM                     PIC S9(9) COMP.
        public NInt32 dtCurrSegm = new NInt32();
        // COB:          07 DT-PARENTAGE                     PIC S9(9) COMP.
        public NInt32 dtParentage = new NInt32();
        // COB:          07 DT-POSITION-STATUS               PIC 9.
        public NZoned dtPositionStatus = new NZoned(1, false);

        // COB:            88 DT-HAS-NOT-POSITION            VALUE 0.
        public boolean dtHasNotPosition() {
          return dtPositionStatus.equals(0);
        }

        public void setDtHasNotPosition(boolean value) {
          if (value) dtPositionStatus.setValue(0);
        }

        // COB:            88 DT-HAS-POSITION                VALUE 1.
        public boolean dtHasPosition() {
          return dtPositionStatus.equals(1);
        }

        public void setDtHasPosition(boolean value) {
          if (value) dtPositionStatus.setValue(1);
        }

        // COB:          07 DT-PSB-PCB.
        public class DtPsbPcb extends NGroup {
          // COB:            09 DT-PSB-NAME                    PIC X(8).
          public NChar dtPsbName = new NChar(8);
          // COB:            09 DT-PCB-PROGRESSIVE             PIC S9(4) COMP.
          public NInt16 dtPcbProgressive = new NInt16();
          // COB:            09 DT-PCB-NAME                    PIC X(8).
          public NChar dtPcbName = new NChar(8);
          // COB:            09 DT-PCB-TYPE                    PIC S9(4) COMP.
          public NInt16 dtPcbType = new NInt16();

          // COB:              88 DT-PCB-TYPE-TP               VALUE +1050 +1051.
          public boolean dtPcbTypeTp() {
            return dtPcbType.equals(1050) || dtPcbType.equals(1051);
          }

          public void setDtPcbTypeTp(boolean value) {
            if (value) dtPcbType.setValue(1050);
          }

          // COB:              88 DT-PCB-TYPE-TP-NAME          VALUE +1050.
          public boolean dtPcbTypeTpName() {
            return dtPcbType.equals(1050);
          }

          public void setDtPcbTypeTpName(boolean value) {
            if (value) dtPcbType.setValue(1050);
          }

          // COB:              88 DT-PCB-TYPE-TP-LTERM         VALUE +1051.
          public boolean dtPcbTypeTpLterm() {
            return dtPcbType.equals(1051);
          }

          public void setDtPcbTypeTpLterm(boolean value) {
            if (value) dtPcbType.setValue(1051);
          }

          // COB:              88 DT-PCB-TYPE-DB               VALUE +1052.
          public boolean dtPcbTypeDb() {
            return dtPcbType.equals(1052);
          }

          public void setDtPcbTypeDb(boolean value) {
            if (value) dtPcbType.setValue(1052);
          }

          // COB:              88 DT-PCB-TYPE-GSAM             VALUE +1053.
          public boolean dtPcbTypeGsam() {
            return dtPcbType.equals(1053);
          }

          public void setDtPcbTypeGsam(boolean value) {
            if (value) dtPcbType.setValue(1053);
          }

          // COB:            09 DT-DBD-NAME                    PIC X(8).
          public NChar dtDbdName = new NChar(8);
          // COB:            09 DT-PCB-KEY-LENGTH              PIC S9(4) COMP.
          public NInt16 dtPcbKeyLength = new NInt16();
          // COB:            09 DT-COPIES                      PIC S9(4) COMP.
          public NInt16 dtCopies = new NInt16();
          // COB:            09 DT-PCB-PROC-OPTS               PIC X(4).
          public NChar dtPcbProcOpts = new NChar(4);

          // COB:              88 DT-PROCOPT-ALL               VALUE 'A   '.
          public boolean dtProcoptAll() {
            return dtPcbProcOpts.equals("A   ");
          }

          public void setDtProcoptAll(boolean value) {
            if (value) dtPcbProcOpts.setValue("A   ");
          }

          // COB:              88 DT-PROCOPT-GS                VALUE 'GS  '.
          public boolean dtProcoptGs() {
            return dtPcbProcOpts.equals("GS  ");
          }

          public void setDtProcoptGs(boolean value) {
            if (value) dtPcbProcOpts.setValue("GS  ");
          }

          // COB:              88 DT-PROCOPT-LS                VALUE 'LS  '.
          public boolean dtProcoptLs() {
            return dtPcbProcOpts.equals("LS  ");
          }

          public void setDtProcoptLs(boolean value) {
            if (value) dtPcbProcOpts.setValue("LS  ");
          }

          // COB:            09 DT-PROC-SEQ                    PIC X(8).
          public NChar dtProcSeq = new NChar(8);
          // COB:            09 DT-PCB-POS                     PIC S9(4) COMP.
          public NInt16 dtPcbPos = new NInt16();

          // COB:              88 DT-POS-SIGLE                 VALUE +1054.
          public boolean dtPosSigle() {
            return dtPcbPos.equals(1054);
          }

          public void setDtPosSigle(boolean value) {
            if (value) dtPcbPos.setValue(1054);
          }

          // COB:              88 DT-POS-MULTIPLY              VALUE +1055.
          public boolean dtPosMultiply() {
            return dtPcbPos.equals(1055);
          }

          public void setDtPosMultiply(boolean value) {
            if (value) dtPcbPos.setValue(1055);
          }

          // COB:            09 DT-PCB-ALTRESP                 PIC S9(4) COMP.
          public NInt16 dtPcbAltresp = new NInt16();

          // COB:              88 DT-IS-ALT-RESP-NO            VALUE 0.
          public boolean dtIsAltRespNo() {
            return dtPcbAltresp.equals(0);
          }

          public void setDtIsAltRespNo(boolean value) {
            if (value) dtPcbAltresp.setValue(0);
          }

          // COB:              88 DT-IS-ALT-RESP-YES           VALUE +1.
          public boolean dtIsAltRespYes() {
            return dtPcbAltresp.equals(1);
          }

          public void setDtIsAltRespYes(boolean value) {
            if (value) dtPcbAltresp.setValue(1);
          }

          // COB:            09 DT-PCB-SAMETRM                 PIC S9(4) COMP.
          public NInt16 dtPcbSametrm = new NInt16();

          // COB:              88 DT-IS-SAME-TRM-NO            VALUE 0.
          public boolean dtIsSameTrmNo() {
            return dtPcbSametrm.equals(0);
          }

          public void setDtIsSameTrmNo(boolean value) {
            if (value) dtPcbSametrm.setValue(0);
          }

          // COB:              88 DT-IS-SAME-TRM-YES           VALUE +1.
          public boolean dtIsSameTrmYes() {
            return dtPcbSametrm.equals(1);
          }

          public void setDtIsSameTrmYes(boolean value) {
            if (value) dtPcbSametrm.setValue(1);
          }

          // COB:            09 DT-PCB-MODIFY                  PIC S9(4) COMP.
          public NInt16 dtPcbModify = new NInt16();

          // COB:              88 DT-IS-MODIFY-NO              VALUE 0.
          public boolean dtIsModifyNo() {
            return dtPcbModify.equals(0);
          }

          public void setDtIsModifyNo(boolean value) {
            if (value) dtPcbModify.setValue(0);
          }

          // COB:              88 DT-IS-MODIFY-YES             VALUE +1.
          public boolean dtIsModifyYes() {
            return dtPcbModify.equals(1);
          }

          public void setDtIsModifyYes(boolean value) {
            if (value) dtPcbModify.setValue(1);
          }

          // COB:            09 DT-PCB-EXPRESS                 PIC S9(4) COMP.
          public NInt16 dtPcbExpress = new NInt16();

          // COB:              88 DT-IS-EXPRESS-NO             VALUE 0.
          public boolean dtIsExpressNo() {
            return dtPcbExpress.equals(0);
          }

          public void setDtIsExpressNo(boolean value) {
            if (value) dtPcbExpress.setValue(0);
          }

          // COB:              88 DT-IS-EXPRESS-YES            VALUE +1.
          public boolean dtIsExpressYes() {
            return dtPcbExpress.equals(1);
          }

          public void setDtIsExpressYes(boolean value) {
            if (value) dtPcbExpress.setValue(1);
          }

          // COB:            09 DT-PCB-LIST                    PIC S9(4) COMP.
          public NInt16 dtPcbList = new NInt16();

          // COB:              88 DT-IS-LIST-NO                VALUE 0.
          public boolean dtIsListNo() {
            return dtPcbList.equals(0);
          }

          public void setDtIsListNo(boolean value) {
            if (value) dtPcbList.setValue(0);
          }

          // COB:              88 DT-IS-LIST-YES               VALUE +1.
          public boolean dtIsListYes() {
            return dtPcbList.equals(1);
          }

          public void setDtIsListYes(boolean value) {
            if (value) dtPcbList.setValue(1);
          }
        }

        public DtPsbPcb dtPsbPcb = new DtPsbPcb();
        // COB:          07 DT-LAST-KEY-SEC                  PIC X(256).
        public NChar dtLastKeySec = new NChar(256);
        // COB:          07 DT-SAVE-LEVEL                    PIC S9(9) COMP.
        public NInt32 dtSaveLevel = new NInt32();
        // COB:          07 DT-NUM-SENSEG                    PIC S9(9) COMP.
        public NInt32 dtNumSenseg = new NInt32();
        // COB:          07 DT-PHYS-NUM-SENSEG               PIC S9(9) COMP.
        public NInt32 dtPhysNumSenseg = new NInt32();
        // COB:          07 DT-ADD-NUM-SENSEG                PIC S9(9) COMP.
        public NInt32 dtAddNumSenseg = new NInt32();
        // COB:          07 DT-PSB-SENSEG-PTR                USAGE POINTER.
        public NPointer dtPsbSensegPtr = new NPointer();
        // COB:          07 DT-DBD-SEGM-PTR                  USAGE POINTER.
        public NPointer dtDbdSegmPtr = new NPointer();
        // COB:          07 DT-INFO-PTR                      USAGE POINTER.
        public NPointer dtInfoPtr = new NPointer();
        // COB:          07 DT-DC-PTR REDEFINES DT-INFO-PTR  USAGE POINTER.
        public NPointer dtDcPtr = new NPointer().redefines(dtInfoPtr);
        // COB:          07 DT-NUM-FIELDS-OBSOLETE           PIC S9(9) COMP.
        public NInt32 dtNumFieldsObsolete = new NInt32();
        // COB:          07 DT-DBD-FIELD-PTR                 USAGE POINTER.
        public NPointer dtDbdFieldPtr = new NPointer();
        // COB:          07 DT-SECONDARY-KEY                 PIC S9(9) COMP.
        public NInt32 dtSecondaryKey = new NInt32();
        // COB:          07 DT-LAST-PRESENTED                PIC X(8).
        public NChar dtLastPresented = new NChar(8);
      }

      public MemoryPcbArea memoryPcbArea = new MemoryPcbArea();

      // COB:        01 RUN-CONTROL-AREA.
      public class IrisRntCntlArea extends NGroup {
        // COB:          07 RUN-RUN-FIXED.
        public class RunRunFixed extends NGroup {
          // COB:            09 RUN-FB-KEY-MAX-LENGTH              PIC S9(9) COMP.
          public NInt32 runFbKeyMaxLength = new NInt32();
          // COB:            09 RUN-USED-KEY-SECONDARY             PIC X(255).
          public NChar runUsedKeySecondary = new NChar(255);
          // COB:            09 RUN-USED-KEY-GNUNQ-NEXT-SEG        PIC X(8).
          public NChar runUsedKeyGnunqNextSeg = new NChar(8);
          // COB:            09 RUN-USED-KEY-GNUNQ-SAVE-POS        PIC X(512).
          public NChar runUsedKeyGnunqSavePos = new NChar(512);
          // COB:            09 RUN-DBD-STATUS                     PIC S9(4) COMP.
          public NInt16 runDbdStatus = new NInt16();

          // COB:              88 DUAL-IMS-ONLY                    VALUE 0.
          public boolean dualImsOnly() {
            return runDbdStatus.equals(0);
          }

          public void setDualImsOnly(boolean value) {
            if (value) runDbdStatus.setValue(0);
          }

          // COB:              88 DUAL-SQL-ONLY                    VALUE 1.
          public boolean dualSqlOnly() {
            return runDbdStatus.equals(1);
          }

          public void setDualSqlOnly(boolean value) {
            if (value) runDbdStatus.setValue(1);
          }

          // COB:              88 DUAL-BOTH                        VALUE 2.
          public boolean dualBoth() {
            return runDbdStatus.equals(2);
          }

          public void setDualBoth(boolean value) {
            if (value) runDbdStatus.setValue(2);
          }

          // COB:            09 RUN-IMS-DUAL-POINTER               POINTER.
          public NPointer runImsDualPointer = new NPointer();
          // COB:            09 RUN-PCB-INDEX                      PIC 9(3).
          public NZoned runPcbIndex = new NZoned(3, false);
          // COB:            09 RUN-IO-AREA-PTR                    USAGE POINTER.
          public NPointer runIoAreaPtr = new NPointer();
          // COB:            09 FILLER                             PIC X(2).
          public NChar filler29 = new NChar(2);
          // COB:            09 RUN-LAST-DYN-SSA-FUNCID         PIC S9(9) COMP.
          public NInt32 runLastDynSsaFuncid = new NInt32();
          // COB:            09 FILLER                             PIC X(202).
          public NChar filler31 = new NChar(202);
        }

        public RunRunFixed runRunFixed = new RunRunFixed();

        // COB:          07 RUN-RUN-VAR.
        public class RunRunVar extends NGroup {
          // COB:            09 RUN-SEGMENT-DETAIL OCCURS 16.
          public class RunSegmentDetail extends NGroup {
            // COB:              11 RUN-USED-SSA-INFO.
            public class RunUsedSsaInfo extends NGroup {
              // COB:                13 RUN-SEQUENCE-NAME              PIC X(8).
              public NChar runSequenceName = new NChar(8);
              // COB:                13 RUN-SEQUENCE-LENGTH            PIC S9(9) COMP.
              public NInt32 runSequenceLength = new NInt32();
              // COB:                13 RUN-SEQUENCE-TYPE              PIC 9.
              public NZoned runSequenceType = new NZoned(1, false);

              // COB:                  88 RUN-SEQUENCE-NONE            VALUE 0.
              public boolean runSequenceNone() {
                return runSequenceType.equals(0);
              }

              public void setRunSequenceNone(boolean value) {
                if (value) runSequenceType.setValue(0);
              }

              // COB:                  88 RUN-SEQUENCE-UNIQUE          VALUE 1.
              public boolean runSequenceUnique() {
                return runSequenceType.equals(1);
              }

              public void setRunSequenceUnique(boolean value) {
                if (value) runSequenceType.setValue(1);
              }

              // COB:                  88 RUN-SEQUENCE-MULTI           VALUE 2.
              public boolean runSequenceMulti() {
                return runSequenceType.equals(2);
              }

              public void setRunSequenceMulti(boolean value) {
                if (value) runSequenceType.setValue(2);
              }

              // COB:                13 RUN-SEQUENCE-DATA-TYPE         PIC S9(9) COMP.
              public NInt32 runSequenceDataType = new NInt32();

              // COB:                  88 RUN-SEQUENCE-TYPE-NONE       VALUE 0.
              public boolean runSequenceTypeNone() {
                return runSequenceDataType.equals(0);
              }

              public void setRunSequenceTypeNone(boolean value) {
                if (value) runSequenceDataType.setValue(0);
              }

              // COB:                  88 RUN-SEQUENCE-TYPE-HEX        VALUE 1026.
              public boolean runSequenceTypeHex() {
                return runSequenceDataType.equals(1026);
              }

              public void setRunSequenceTypeHex(boolean value) {
                if (value) runSequenceDataType.setValue(1026);
              }

              // COB:                  88 RUN-SEQUENCE-TYPE-PACKED     VALUE 1027.
              public boolean runSequenceTypePacked() {
                return runSequenceDataType.equals(1027);
              }

              public void setRunSequenceTypePacked(boolean value) {
                if (value) runSequenceDataType.setValue(1027);
              }

              // COB:                  88 RUN-SEQUENCE-TYPE-ALPHA      VALUE 1028.
              public boolean runSequenceTypeAlpha() {
                return runSequenceDataType.equals(1028);
              }

              public void setRunSequenceTypeAlpha(boolean value) {
                if (value) runSequenceDataType.setValue(1028);
              }

              // COB:                  88 RUN-SEQUENCE-TYPE-F-WORD     VALUE 1029.
              public boolean runSequenceTypeFWord() {
                return runSequenceDataType.equals(1029);
              }

              public void setRunSequenceTypeFWord(boolean value) {
                if (value) runSequenceDataType.setValue(1029);
              }

              // COB:                  88 RUN-SEQUENCE-TYPE-H-WORD     VALUE 1030.
              public boolean runSequenceTypeHWord() {
                return runSequenceDataType.equals(1030);
              }

              public void setRunSequenceTypeHWord(boolean value) {
                if (value) runSequenceDataType.setValue(1030);
              }

              // COB:                  88 RUN-SEQUENCE-TYPE-NUM-ZONED  VALUE 1031.
              public boolean runSequenceTypeNumZoned() {
                return runSequenceDataType.equals(1031);
              }

              public void setRunSequenceTypeNumZoned(boolean value) {
                if (value) runSequenceDataType.setValue(1031);
              }

              // COB:                  88 RUN-SEQUENCE-TYPE-INDEX      VALUE 91000.
              public boolean runSequenceTypeIndex() {
                return runSequenceDataType.equals(91000);
              }

              public void setRunSequenceTypeIndex(boolean value) {
                if (value) runSequenceDataType.setValue(91000);
              }

              // COB:                  88 RUN-SEQUENCE-TYPE-XDFLD      VALUE 91001.
              public boolean runSequenceTypeXdfld() {
                return runSequenceDataType.equals(91001);
              }

              public void setRunSequenceTypeXdfld(boolean value) {
                if (value) runSequenceDataType.setValue(91001);
              }

              // COB:                  88 RUN-SEQUENCE-TYPE-EDITED     VALUE 91002.
              public boolean runSequenceTypeEdited() {
                return runSequenceDataType.equals(91002);
              }

              public void setRunSequenceTypeEdited(boolean value) {
                if (value) runSequenceDataType.setValue(91002);
              }

              // COB:                  88 RUN-SEQUENCE-TYPE-EBCDIC     VALUE 91003.
              public boolean runSequenceTypeEbcdic() {
                return runSequenceDataType.equals(91003);
              }

              public void setRunSequenceTypeEbcdic(boolean value) {
                if (value) runSequenceDataType.setValue(91003);
              }

              // COB:                13 RUN-PHYSICAL-INDEX             PIC S9(9) COMP.
              public NInt32 runPhysicalIndex = new NInt32();
              // COB:                13 RUN-PARENT-SEGMENT-INDEX       PIC S9(9) COMP.
              public NInt32 runParentSegmentIndex = new NInt32();
              // COB:                13 RUN-PHYSICAL-PARENT-INDEX      PIC S9(9) COMP.
              public NInt32 runPhysicalParentIndex = new NInt32();
              // COB:                13 RUN-USED-SSA-LEN               PIC S9(4) COMP.
              public NInt16 runUsedSsaLen = new NInt16();
              // COB:                13 RUN-USED-SSA                   PIC X(1024).
              public NChar runUsedSsa = new NChar(1024);
            }

            public RunUsedSsaInfo runUsedSsaInfo = new RunUsedSsaInfo();

            // COB:              11 RUN-USED-KEYS-PSB.
            public class RunUsedKeysPsb extends NGroup {
              // COB:                13 RUN-USED-KEY-STATUS            PIC X.
              public NChar runUsedKeyStatus = new NChar(1);

              // COB:                  88 RUN-USED-KEY-NOT-CHANGED     VALUE '0' X'00' ' '.
              public boolean runUsedKeyNotChanged() {
                return runUsedKeyStatus.equals("0")
                    || runUsedKeyStatus.equals(hexToString("00"))
                    || runUsedKeyStatus.equals(" ");
              }

              public void setRunUsedKeyNotChanged(boolean value) {
                if (value) runUsedKeyStatus.setValue("0");
              }

              // COB:                  88 RUN-USED-KEY-CHANGED         VALUE '1'.
              public boolean runUsedKeyChanged() {
                return runUsedKeyStatus.equals("1");
              }

              public void setRunUsedKeyChanged(boolean value) {
                if (value) runUsedKeyStatus.setValue("1");
              }

              // COB:                  88 RUN-USED-KEY-IS-INDEX        VALUE '2'.
              public boolean runUsedKeyIsIndex() {
                return runUsedKeyStatus.equals("2");
              }

              public void setRunUsedKeyIsIndex(boolean value) {
                if (value) runUsedKeyStatus.setValue("2");
              }

              // COB:                13 RUN-USED-KEY-VALUE.
              public class RunUsedKeyValue extends NGroup {
                // COB:                  15 RUN-USED-KEY-ALPHA           PIC X(256).
                public NChar runUsedKeyAlpha = new NChar(256);
                // COB:                  15 RUN-USED-KEY-NUMERIC         PIC S9(9) COMP.
                public NInt32 runUsedKeyNumeric = new NInt32();
                // COB:                  15 RUN-USED-KEY-NUMERIC-PREV    PIC S9(9) COMP.
                public NInt32 runUsedKeyNumericPrev = new NInt32();
                // COB:                  15 RUN-USED-KEY-NUMERIC-NEXT    PIC S9(9) COMP.
                public NInt32 runUsedKeyNumericNext = new NInt32();
                // COB:                  15 RUN-USED-KEY-PARENT-ALPHA    PIC X(256).
                public NChar runUsedKeyParentAlpha = new NChar(256);
                // COB:                  15 RUN-USED-KEY-PARENT-NUMERIC  PIC S9(9) COMP.
                public NInt32 runUsedKeyParentNumeric = new NInt32();

                // COB:                  15 RUN-LAST-SYSTEM-FLD-AREA OCCURS 32.
                public class RunLastSystemFldArea extends NGroup {
                  // COB:                    17 RUN-LAST-SX                PIC S9(9) COMP.
                  public NInt32 runLastSx = new NInt32();
                }

                public RunLastSystemFldArea[] runLastSystemFldArea =
                    AbstractNField.occurs(32, new RunLastSystemFldArea());

                public RunLastSystemFldArea runLastSystemFldAreaAtIndex(int index) {
                  return getOccursInstance(runLastSystemFldArea, index);
                }
              }

              public RunUsedKeyValue runUsedKeyValue = new RunUsedKeyValue();
            }

            public RunUsedKeysPsb runUsedKeysPsb = new RunUsedKeysPsb();
            // COB:               11 RUN-USED-LAST-SEGMENT           PIC X(8).
            public NChar runUsedLastSegment = new NChar(8);
            // COB:               11 RUN-USED-LAST-OPEN-CURSOR       PIC 9(8).
            public NZoned runUsedLastOpenCursor = new NZoned(8, false);
            // COB:               11 FILLER                          PIC X(272).
            public NChar filler89 = new NChar(272);
          }

          public RunSegmentDetail[] runSegmentDetail =
              AbstractNField.occurs(16, new RunSegmentDetail());

          public RunSegmentDetail runSegmentDetailAtIndex(int index) {
            return getOccursInstance(runSegmentDetail, index);
          }
        }

        public RunRunVar runRunVar = new RunRunVar();
      }

      public IrisRntCntlArea irisRntCntlArea = new IrisRntCntlArea();
    }

    public IrisPcbsTab[] irisPcbsTab = AbstractNField.occurs(45, new IrisPcbsTab());

    public IrisPcbsTab irisPcbsTabAtIndex(int index) {
      return getOccursInstance(irisPcbsTab, index);
    }
  }
}
