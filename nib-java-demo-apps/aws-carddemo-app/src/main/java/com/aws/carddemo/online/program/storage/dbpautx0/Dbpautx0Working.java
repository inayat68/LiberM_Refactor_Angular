package com.aws.carddemo.online.program.storage.dbpautx0;

import com.aws.carddemo.common.copybook.Iriscomm;
import com.aws.carddemo.common.copybook.Irissqlc;
import com.aws.carddemo.common.copybook.Pautdtl1Copy;
import com.aws.carddemo.common.copybook.Pautsum0Copy;
import com.aws.carddemo.common.copybook.SqldaCopy;
import com.nib.commons.storage.*;

public class Dbpautx0Working extends NGroup {
  public Iriscomm iriscomm = new Iriscomm();
  public Irissqlc irissqlc = new Irissqlc();
  public SqldaCopy sqldacopy = new SqldaCopy();
  // COB:        01 WS-IMS-API                    PIC X(8).
  public NChar wsImsApi = new NChar(8);
  // COB:        01  WS-SQL-STM.
  public WsSqlStm wsSqlStm = new WsSqlStm();

  public static class WsSqlStm extends NGroup {

    // COB:          49  WS-SQL-STM-LEN             PIC S9(4) COMP.
    public NInt16 wsSqlStmLen = new NInt16();
    // COB:          49  WS-SQL-STM-TXT             PIC X(32000).
    public NChar wsSqlStmTxt = new NChar(32000);
  }

  // COB:        01  SQL-ORDERBY-CLAUSE.
  public SqlOrderbyClause sqlOrderbyClause = new SqlOrderbyClause();

  public static class SqlOrderbyClause extends NGroup {

    // COB:          49  SQL-ORDERBY-CLAUSE-LENGTH  PIC S9(4) COMP.
    public NInt16 sqlOrderbyClauseLength = new NInt16();
    // COB:          49  SQL-ORDERBY-CLAUSE-TEXT    PIC X(32000).
    public NChar sqlOrderbyClauseText = new NChar(32000);
  }

  // COB:        01 WS-DUAL-TRACE                 PIC 9 VALUE 0.
  public NZoned wsDualTrace = new NZoned(1, false);

  // COB:          88 DUAL-TRACE-ON               VALUE 0.
  public boolean dualTraceOn() {
    return wsDualTrace.equals(0);
  }

  public void setDualTraceOn(boolean value) {
    if (value) wsDualTrace.setValue(0);
  }

  // COB:          88 DUAL-TRACE-OFF              VALUE 1.
  public boolean dualTraceOff() {
    return wsDualTrace.equals(1);
  }

  public void setDualTraceOff(boolean value) {
    if (value) wsDualTrace.setValue(1);
  }

  // COB:        01 WS-KEY-ACCNTID                PIC X(6).
  public NChar wsKeyAccntid = new NChar(6);
  // COB:        01 WS-LOW-VALUES                 PIC X(256)     VALUE LOW-VALUE.
  public NChar wsLowValues = new NChar(256).initial(NChar.LowValue);
  // COB:        01 WS-HIGH-VALUES                PIC X(256)     VALUE HIGH-VALUE.
  public NChar wsHighValues = new NChar(256).initial(NChar.HighValue);
  // COB:        01 WS-IO-AREA                    PIC X(32000).
  public NChar wsIoArea = new NChar(32000);
  // COB:        01 WS-PATHCALL-AREA              PIC X(32000).
  public NChar wsPathcallArea = new NChar(32000);
  // COB:        01 WS-PATHCALL-LEN               PIC S9(4) COMP.
  public NInt16 wsPathcallLen = new NInt16();
  // COB:        01 WS-INIT-PATHCALL-LEN          PIC S9(4) COMP.
  public NInt16 wsInitPathcallLen = new NInt16();
  // COB:        01 WS-WHERE                      PIC X(4096).
  public NChar wsWhere = new NChar(4096);
  // COB:        01 WS-WHERE-LEN                  PIC S9(4) COMP.
  public NInt16 wsWhereLen = new NInt16();
  // COB:        01 WS-ORDERBY                    PIC X(1024).
  public NChar wsOrderby = new NChar(1024);
  // COB:        01 WS-ORDERBY-LEN                PIC S9(4) COMP.
  public NInt16 wsOrderbyLen = new NInt16();
  // COB:        01 WS-SAVE-ROUTINE-SQLCODE       PIC S9(9) COMP.
  public NInt32 wsSaveRoutineSqlcode = new NInt32();
  // COB:        01 WS-SQLCODE-N                  PIC S9(5).
  public NZoned wsSqlcodeN = new NZoned(5);
  // COB:        01 WS-SQLCODE-E                  PIC -ZZZZ9.
  public NZoned wsSqlcodeE = new NZoned(6).formatAs("-####0");
  // COB:        01 WS-SAVE-ROUTINE-SQLCA         PIC X(1024).
  public NChar wsSaveRoutineSqlca = new NChar(1024);
  // COB:        01 WS-MESSAGE-ID-EDITED          PIC ZZZZ9.
  public NZoned wsMessageIdEdited = new NZoned(5, false).formatAs("####0");
  // COB:        01 WS-MESSAGE                    PIC X(32000).
  public NChar wsMessage = new NChar(32000);
  // COB:        01 WS-ERROR-DESCRIPTION          PIC X(80).
  public NChar wsErrorDescription = new NChar(80);
  // COB:        01 WS-ERROR-DESCRIPTION-LEN      PIC S9(4) COMP.
  public NInt16 wsErrorDescriptionLen = new NInt16();
  // COB:        01 WS-INDEX                      PIC S9(4) COMP.
  public NInt16 wsIndex = new NInt16();
  // COB:        01 WS-LEN                        PIC S9(4) COMP.
  public NInt16 wsLen = new NInt16();
  // COB:        01 WS-IDX                        PIC 9(3).
  public NZoned wsIdx = new NZoned(3, false);
  // COB:        01 WS-FLAG                       PIC 9 VALUE 0.
  public NZoned wsFlag = new NZoned(1, false);

  // COB:          88 WS-NOT-FOUND                VALUE 0.
  public boolean wsNotFound() {
    return wsFlag.equals(0);
  }

  public void setWsNotFound(boolean value) {
    if (value) wsFlag.setValue(0);
  }

  // COB:          88 WS-FOUND                    VALUE 1.
  public boolean wsFound() {
    return wsFlag.equals(1);
  }

  public void setWsFound(boolean value) {
    if (value) wsFlag.setValue(1);
  }

  // COB:        01 WS-PTR-IN-LK-IO-AREA          POINTER.
  public NPointer wsPtrInLkIoArea = new NPointer();
  // COB:        01 WS-PTR-WS-IO-AREA             POINTER.
  public NPointer wsPtrWsIoArea = new NPointer();
  // COB:        01 WS-ZONED-FIELD-1              PIC 9.
  public NZoned wsZonedField1 = new NZoned(1, false);
  // COB:        01 WS-ZONED-FIELD-2              PIC 9(2).
  public NZoned wsZonedField2 = new NZoned(2, false);
  // COB:        01 WS-ZONED-FIELD-3              PIC 9(3).
  public NZoned wsZonedField3 = new NZoned(3, false);
  // COB:        01 WS-ZONED-FIELD-4              PIC 9(4).
  public NZoned wsZonedField4 = new NZoned(4, false);
  // COB:        01 WS-ZONED-FIELD-5              PIC 9(5).
  public NZoned wsZonedField5 = new NZoned(5, false);
  // COB:        01 WS-ZONED-FIELD-6              PIC 9(6).
  public NZoned wsZonedField6 = new NZoned(6, false);
  // COB:        01 WS-ZONED-FIELD-7              PIC 9(7).
  public NZoned wsZonedField7 = new NZoned(7, false);
  // COB:        01 WS-ZONED-FIELD-8              PIC 9(8).
  public NZoned wsZonedField8 = new NZoned(8, false);
  // COB:        01 WS-ZONED-FIELD-9              PIC 9(9).
  public NZoned wsZonedField9 = new NZoned(9, false);
  // COB:        01 WS-ZONED-FIELD-10             PIC 9(10).
  public NZoned wsZonedField10 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-1           PIC 9(10).
  public NZoned wsZonedField101 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-2           PIC 9(10).
  public NZoned wsZonedField102 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-3           PIC 9(10).
  public NZoned wsZonedField103 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-4           PIC 9(10).
  public NZoned wsZonedField104 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-5           PIC 9(10).
  public NZoned wsZonedField105 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-6           PIC 9(10).
  public NZoned wsZonedField106 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-7           PIC 9(10).
  public NZoned wsZonedField107 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-8           PIC 9(10).
  public NZoned wsZonedField108 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-9           PIC 9(10).
  public NZoned wsZonedField109 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-10          PIC 9(10).
  public NZoned wsZonedField1010 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-11          PIC 9(10).
  public NZoned wsZonedField1011 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-12          PIC 9(10).
  public NZoned wsZonedField1012 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-13          PIC 9(10).
  public NZoned wsZonedField1013 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-14          PIC 9(10).
  public NZoned wsZonedField1014 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-15          PIC 9(10).
  public NZoned wsZonedField1015 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-10-16          PIC 9(10).
  public NZoned wsZonedField1016 = new NZoned(10, false);
  // COB:        01 WS-ZONED-FIELD-11             PIC 9(11).
  public NZoned wsZonedField11 = new NZoned(11, false);
  // COB:        01 WS-ZONED-FIELD-12             PIC 9(12).
  public NZoned wsZonedField12 = new NZoned(12, false);
  // COB:        01 WS-ZONED-FIELD-13             PIC 9(13).
  public NZoned wsZonedField13 = new NZoned(13, false);
  // COB:        01 WS-ZONED-FIELD-14             PIC 9(14).
  public NZoned wsZonedField14 = new NZoned(14, false);
  // COB:        01 WS-ZONED-FIELD-15             PIC 9(15).
  public NZoned wsZonedField15 = new NZoned(15, false);
  // COB:        01 WS-ZONED-FIELD-16             PIC 9(16).
  public NZoned wsZonedField16 = new NZoned(16, false);
  // COB:        01 WS-ZONED-FIELD-17             PIC 9(17).
  public NZoned wsZonedField17 = new NZoned(17, false);
  // COB:        01 WS-ZONED-FIELD-18             PIC 9(18).
  public NZoned wsZonedField18 = new NZoned(18, false);
  // COB:        01 WS-BINARY-FIELD               PIC S9(4) COMP.
  public NInt16 wsBinaryField = new NInt16();

  // COB:        01 FILLER                        REDEFINES WS-BINARY-FIELD.
  public static class Filler115 extends NGroup {
    // COB:          03 WS-BINARY-FIELD-X           PIC X(2).
    public NChar wsBinaryFieldX = new NChar(2);
  }

  public Filler115 filler115 = (Filler115) new Filler115().redefines(wsBinaryField);
  // COB:        01 WS-BINARY-BIG-END             PIC S9(4) COMP.
  public NInt16 wsBinaryBigEnd = new NInt16();

  // COB:        01 WS-BINARY-BIG-END-RED         REDEFINES WS-BINARY-BIG-END.
  public static class WsBinaryBigEndRed extends NGroup {
    // COB:          03 WS-BIG-END-BYTE-1           PIC X.
    public NChar wsBigEndByte1 = new NChar(1);
    // COB:          03 WS-BIG-END-BYTE-2           PIC X.
    public NChar wsBigEndByte2 = new NChar(1);
  }

  public WsBinaryBigEndRed wsBinaryBigEndRed =
      (WsBinaryBigEndRed) new WsBinaryBigEndRed().redefines(wsBinaryBigEnd);
  // COB:        01 WS-BINARY-LIT-END             PIC S9(4) COMP.
  public NInt16 wsBinaryLitEnd = new NInt16();

  // COB:        01 WS-BINARY-LIT-END-RED         REDEFINES WS-BINARY-LIT-END.
  public static class WsBinaryLitEndRed extends NGroup {
    // COB:          03 WS-LIT-END-BYTE-1           PIC X.
    public NChar wsLitEndByte1 = new NChar(1);
    // COB:          03 WS-LIT-END-BYTE-2           PIC X.
    public NChar wsLitEndByte2 = new NChar(1);
  }

  public WsBinaryLitEndRed wsBinaryLitEndRed =
      (WsBinaryLitEndRed) new WsBinaryLitEndRed().redefines(wsBinaryLitEnd);
  // COB:        01 WS-BINARY-BIG4-END            PIC S9(9) COMP.
  public NInt32 wsBinaryBig4End = new NInt32();

  // COB:        01 WS-BINARY-BIG4-END-RED        REDEFINES WS-BINARY-BIG4-END.
  public static class WsBinaryBig4EndRed extends NGroup {
    // COB:          03 WS-BIG4-END-BYTE-1          PIC X.
    public NChar wsBig4EndByte1 = new NChar(1);
    // COB:          03 WS-BIG4-END-BYTE-2          PIC X.
    public NChar wsBig4EndByte2 = new NChar(1);
    // COB:          03 WS-BIG4-END-BYTE-3          PIC X.
    public NChar wsBig4EndByte3 = new NChar(1);
    // COB:          03 WS-BIG4-END-BYTE-4          PIC X.
    public NChar wsBig4EndByte4 = new NChar(1);
  }

  public WsBinaryBig4EndRed wsBinaryBig4EndRed =
      (WsBinaryBig4EndRed) new WsBinaryBig4EndRed().redefines(wsBinaryBig4End);
  // COB:        01 WS-BINARY-LIT4-END            PIC S9(9) COMP.
  public NInt32 wsBinaryLit4End = new NInt32();

  // COB:        01 WS-BINARY-LIT4-END-RED        REDEFINES WS-BINARY-LIT4-END.
  public static class WsBinaryLit4EndRed extends NGroup {
    // COB:          03 WS-LIT4-END-BYTE-1          PIC X.
    public NChar wsLit4EndByte1 = new NChar(1);
    // COB:          03 WS-LIT4-END-BYTE-2          PIC X.
    public NChar wsLit4EndByte2 = new NChar(1);
    // COB:          03 WS-LIT4-END-BYTE-3          PIC X.
    public NChar wsLit4EndByte3 = new NChar(1);
    // COB:          03 WS-LIT4-END-BYTE-4          PIC X.
    public NChar wsLit4EndByte4 = new NChar(1);
  }

  public WsBinaryLit4EndRed wsBinaryLit4EndRed =
      (WsBinaryLit4EndRed) new WsBinaryLit4EndRed().redefines(wsBinaryLit4End);
  // COB:        01 WK-COMMAND-CODE               PIC S9(9) COMP.
  public NInt32 wkCommandCode = new NInt32();

  // COB:          88 COMMAND-CODE-HERE           VALUE 0 1021.
  public boolean commandCodeHere() {
    return wkCommandCode.equals(0) || wkCommandCode.equals(1021);
  }

  public void setCommandCodeHere(boolean value) {
    if (value) wkCommandCode.setValue(0);
  }

  // COB:          88 COMMAND-CODE-FIRST          VALUE 1020.
  public boolean commandCodeFirst() {
    return wkCommandCode.equals(1020);
  }

  public void setCommandCodeFirst(boolean value) {
    if (value) wkCommandCode.setValue(1020);
  }

  // COB:          88 COMMAND-CODE-LAST           VALUE 1019.
  public boolean commandCodeLast() {
    return wkCommandCode.equals(1019);
  }

  public void setCommandCodeLast(boolean value) {
    if (value) wkCommandCode.setValue(1019);
  }

  // COB:        01 WS-PATHCALLS                  PIC 9.
  public NZoned wsPathcalls = new NZoned(1, false);

  // COB:          88 HAS-NOT-PATHCALLS           VALUE 0.
  public boolean hasNotPathcalls() {
    return wsPathcalls.equals(0);
  }

  public void setHasNotPathcalls(boolean value) {
    if (value) wsPathcalls.setValue(0);
  }

  // COB:          88 HAS-PATHCALLS               VALUE 1.
  public boolean hasPathcalls() {
    return wsPathcalls.equals(1);
  }

  public void setHasPathcalls(boolean value) {
    if (value) wsPathcalls.setValue(1);
  }

  // COB:        01 WS-PATHCALLS-ERROR            PIC 9.
  public NZoned wsPathcallsError = new NZoned(1, false);

  // COB:          88 HAS-PATHCALLS-ERROR         VALUE 0.
  public boolean hasPathcallsError() {
    return wsPathcallsError.equals(0);
  }

  public void setHasPathcallsError(boolean value) {
    if (value) wsPathcallsError.setValue(0);
  }

  // COB:          88 HAS-PATHCALLS-NO-ERROR      VALUE 1.
  public boolean hasPathcallsNoError() {
    return wsPathcallsError.equals(1);
  }

  public void setHasPathcallsNoError(boolean value) {
    if (value) wsPathcallsError.setValue(1);
  }

  // COB:        01 WS-PATHCALL-REVERSE           PIC 9.
  public NZoned wsPathcallReverse = new NZoned(1, false);

  // COB:          88 IS-NOT-PATHCALL-REVERSE     VALUE 0.
  public boolean isNotPathcallReverse() {
    return wsPathcallReverse.equals(0);
  }

  public void setIsNotPathcallReverse(boolean value) {
    if (value) wsPathcallReverse.setValue(0);
  }

  // COB:          88 IS-PATHCALL-REVERSE         VALUE 1.
  public boolean isPathcallReverse() {
    return wsPathcallReverse.equals(1);
  }

  public void setIsPathcallReverse(boolean value) {
    if (value) wsPathcallReverse.setValue(1);
  }

  // COB:        01 WS-PATHCALL-LEVEL             PIC S9(4) COMP.
  public NInt16 wsPathcallLevel = new NInt16();
  // COB:        01 WS-FULL-SCAN                  PIC 9.
  public NZoned wsFullScan = new NZoned(1, false);

  // COB:          88 FULL-SCAN-FALSE             VALUE 0.
  public boolean fullScanFalse() {
    return wsFullScan.equals(0);
  }

  public void setFullScanFalse(boolean value) {
    if (value) wsFullScan.setValue(0);
  }

  // COB:          88 FULL-SCAN-TRUE              VALUE 1.
  public boolean fullScanTrue() {
    return wsFullScan.equals(1);
  }

  public void setFullScanTrue(boolean value) {
    if (value) wsFullScan.setValue(1);
  }

  // COB:        01 WS-COMMON-FLD                    PIC X(4608).
  public NChar wsCommonFld = new NChar(4608);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD.
  public static class Filler155 extends NGroup {
    // COB:          03 WS-ZONED-FLD     OCCURS 256    PIC S9(18).
    public NZoned[] wsZonedFld = AbstractNField.occurs(256, new NZoned(18));

    public NZoned wsZonedFldAtIndex(int index) {
      return getOccursInstance(wsZonedFld, index);
    }
  }

  public Filler155 filler155 = (Filler155) new Filler155().redefines(wsCommonFld);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD.
  public static class Filler157 extends NGroup {
    // COB:          03 WS-ZONED-FLD-CHR OCCURS 256    PIC X(18).
    public NChar[] wsZonedFldChr = AbstractNField.occurs(256, new NChar(18));

    public NChar wsZonedFldChrAtIndex(int index) {
      return getOccursInstance(wsZonedFldChr, index);
    }
  }

  public Filler157 filler157 = (Filler157) new Filler157().redefines(wsCommonFld);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD.
  public static class Filler159 extends NGroup {
    // COB:          03 FILLER OCCURS 256.
    public static class Filler160 extends NGroup {
      // COB:            05 WS-COMP-FLD-2                PIC S9(4) COMP.
      public NInt16 wsCompFld2 = new NInt16();
      // COB:            05 FILLER                       PIC X(16).
      public NChar filler162 = new NChar(16);
    }

    public Filler160[] filler160 = AbstractNField.occurs(256, new Filler160());

    public Filler160 filler160AtIndex(int index) {
      return getOccursInstance(filler160, index);
    }
  }

  public Filler159 filler159 = (Filler159) new Filler159().redefines(wsCommonFld);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD.
  public static class Filler163 extends NGroup {
    // COB:          03 FILLER OCCURS 256.
    public static class Filler164 extends NGroup {
      // COB:            05 WS-COMP-FLD-4                PIC S9(9) COMP.
      public NInt32 wsCompFld4 = new NInt32();
      // COB:            05 FILLER                       PIC X(14).
      public NChar filler166 = new NChar(14);
    }

    public Filler164[] filler164 = AbstractNField.occurs(256, new Filler164());

    public Filler164 filler164AtIndex(int index) {
      return getOccursInstance(filler164, index);
    }
  }

  public Filler163 filler163 = (Filler163) new Filler163().redefines(wsCommonFld);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD.
  public static class Filler167 extends NGroup {
    // COB:          03 FILLER OCCURS 256.
    public static class Filler168 extends NGroup {
      // COB:            05 WS-COMP-FLD-8                PIC S9(18) COMP.
      public NInt64 wsCompFld8 = new NInt64();
      // COB:            05 FILLER                       PIC X(10).
      public NChar filler170 = new NChar(10);
    }

    public Filler168[] filler168 = AbstractNField.occurs(256, new Filler168());

    public Filler168 filler168AtIndex(int index) {
      return getOccursInstance(filler168, index);
    }
  }

  public Filler167 filler167 = (Filler167) new Filler167().redefines(wsCommonFld);
  // COB:        01 WS-COMMON-FLD-PACKED             PIC X(2560)
  // COB:                                            VALUE LOW-VALUE.
  public NChar wsCommonFldPacked = new NChar(2560).initial(NChar.LowValue);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler174 extends NGroup {
    // COB:          03 WS-PACKED-FLD-18-00 OCCURS 256 PIC S9(18)         COMP-3.
    public NPacked[] wsPackedFld1800 = AbstractNField.occurs(256, new NPacked(10));

    public NPacked wsPackedFld1800AtIndex(int index) {
      return getOccursInstance(wsPackedFld1800, index);
    }
  }

  public Filler174 filler174 = (Filler174) new Filler174().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler176 extends NGroup {
    // COB:          03 WS-PACKED-FLD-17-01 OCCURS 256 PIC S9(17)V9       COMP-3.
    public NPacked[] wsPackedFld1701 = AbstractNField.occurs(256, new NPacked(10));

    public NPacked wsPackedFld1701AtIndex(int index) {
      return getOccursInstance(wsPackedFld1701, index);
    }
  }

  public Filler176 filler176 = (Filler176) new Filler176().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler178 extends NGroup {
    // COB:          03 WS-PACKED-FLD-16-02 OCCURS 256 PIC S9(16)V9(02)   COMP-3.
    public NPacked[] wsPackedFld1602 = AbstractNField.occurs(256, new NPacked(10, 2));

    public NPacked wsPackedFld1602AtIndex(int index) {
      return getOccursInstance(wsPackedFld1602, index);
    }
  }

  public Filler178 filler178 = (Filler178) new Filler178().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler180 extends NGroup {
    // COB:          03 WS-PACKED-FLD-15-03 OCCURS 256 PIC S9(15)V9(03)   COMP-3.
    public NPacked[] wsPackedFld1503 = AbstractNField.occurs(256, new NPacked(10, 3));

    public NPacked wsPackedFld1503AtIndex(int index) {
      return getOccursInstance(wsPackedFld1503, index);
    }
  }

  public Filler180 filler180 = (Filler180) new Filler180().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler182 extends NGroup {
    // COB:          03 WS-PACKED-FLD-14-04 OCCURS 256 PIC S9(14)V9(04)   COMP-3.
    public NPacked[] wsPackedFld1404 = AbstractNField.occurs(256, new NPacked(10, 4));

    public NPacked wsPackedFld1404AtIndex(int index) {
      return getOccursInstance(wsPackedFld1404, index);
    }
  }

  public Filler182 filler182 = (Filler182) new Filler182().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler184 extends NGroup {
    // COB:          03 WS-PACKED-FLD-13-05 OCCURS 256 PIC S9(13)V9(05)   COMP-3.
    public NPacked[] wsPackedFld1305 = AbstractNField.occurs(256, new NPacked(10, 5));

    public NPacked wsPackedFld1305AtIndex(int index) {
      return getOccursInstance(wsPackedFld1305, index);
    }
  }

  public Filler184 filler184 = (Filler184) new Filler184().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler186 extends NGroup {
    // COB:          03 WS-PACKED-FLD-12-06 OCCURS 256 PIC S9(12)V9(06)   COMP-3.
    public NPacked[] wsPackedFld1206 = AbstractNField.occurs(256, new NPacked(10, 6));

    public NPacked wsPackedFld1206AtIndex(int index) {
      return getOccursInstance(wsPackedFld1206, index);
    }
  }

  public Filler186 filler186 = (Filler186) new Filler186().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler188 extends NGroup {
    // COB:          03 WS-PACKED-FLD-11-07 OCCURS 256 PIC S9(11)V9(07)   COMP-3.
    public NPacked[] wsPackedFld1107 = AbstractNField.occurs(256, new NPacked(10, 7));

    public NPacked wsPackedFld1107AtIndex(int index) {
      return getOccursInstance(wsPackedFld1107, index);
    }
  }

  public Filler188 filler188 = (Filler188) new Filler188().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler190 extends NGroup {
    // COB:          03 WS-PACKED-FLD-10-08 OCCURS 256 PIC S9(10)V9(08)   COMP-3.
    public NPacked[] wsPackedFld1008 = AbstractNField.occurs(256, new NPacked(10, 8));

    public NPacked wsPackedFld1008AtIndex(int index) {
      return getOccursInstance(wsPackedFld1008, index);
    }
  }

  public Filler190 filler190 = (Filler190) new Filler190().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler192 extends NGroup {
    // COB:          03 WS-PACKED-FLD-09-09 OCCURS 256 PIC S9(09)V9(09)   COMP-3.
    public NPacked[] wsPackedFld0909 = AbstractNField.occurs(256, new NPacked(10, 9));

    public NPacked wsPackedFld0909AtIndex(int index) {
      return getOccursInstance(wsPackedFld0909, index);
    }
  }

  public Filler192 filler192 = (Filler192) new Filler192().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler194 extends NGroup {
    // COB:          03 WS-PACKED-FLD-08-10 OCCURS 256 PIC S9(08)V9(10)   COMP-3.
    public NPacked[] wsPackedFld0810 = AbstractNField.occurs(256, new NPacked(10, 10));

    public NPacked wsPackedFld0810AtIndex(int index) {
      return getOccursInstance(wsPackedFld0810, index);
    }
  }

  public Filler194 filler194 = (Filler194) new Filler194().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler196 extends NGroup {
    // COB:          03 WS-PACKED-FLD-07-11 OCCURS 256 PIC S9(07)V9(11)   COMP-3.
    public NPacked[] wsPackedFld0711 = AbstractNField.occurs(256, new NPacked(10, 11));

    public NPacked wsPackedFld0711AtIndex(int index) {
      return getOccursInstance(wsPackedFld0711, index);
    }
  }

  public Filler196 filler196 = (Filler196) new Filler196().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler198 extends NGroup {
    // COB:          03 WS-PACKED-FLD-06-12 OCCURS 256 PIC S9(06)V9(12)   COMP-3.
    public NPacked[] wsPackedFld0612 = AbstractNField.occurs(256, new NPacked(10, 12));

    public NPacked wsPackedFld0612AtIndex(int index) {
      return getOccursInstance(wsPackedFld0612, index);
    }
  }

  public Filler198 filler198 = (Filler198) new Filler198().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler200 extends NGroup {
    // COB:          03 WS-PACKED-FLD-05-13 OCCURS 256 PIC S9(05)V9(13)   COMP-3.
    public NPacked[] wsPackedFld0513 = AbstractNField.occurs(256, new NPacked(10, 13));

    public NPacked wsPackedFld0513AtIndex(int index) {
      return getOccursInstance(wsPackedFld0513, index);
    }
  }

  public Filler200 filler200 = (Filler200) new Filler200().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler202 extends NGroup {
    // COB:          03 WS-PACKED-FLD-04-14 OCCURS 256 PIC S9(04)V9(14)   COMP-3.
    public NPacked[] wsPackedFld0414 = AbstractNField.occurs(256, new NPacked(10, 14));

    public NPacked wsPackedFld0414AtIndex(int index) {
      return getOccursInstance(wsPackedFld0414, index);
    }
  }

  public Filler202 filler202 = (Filler202) new Filler202().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler204 extends NGroup {
    // COB:          03 WS-PACKED-FLD-03-15 OCCURS 256 PIC S9(03)V9(15)   COMP-3.
    public NPacked[] wsPackedFld0315 = AbstractNField.occurs(256, new NPacked(10, 15));

    public NPacked wsPackedFld0315AtIndex(int index) {
      return getOccursInstance(wsPackedFld0315, index);
    }
  }

  public Filler204 filler204 = (Filler204) new Filler204().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler206 extends NGroup {
    // COB:          03 WS-PACKED-FLD-02-16 OCCURS 256 PIC S9(02)V9(16)   COMP-3.
    public NPacked[] wsPackedFld0216 = AbstractNField.occurs(256, new NPacked(10, 16));

    public NPacked wsPackedFld0216AtIndex(int index) {
      return getOccursInstance(wsPackedFld0216, index);
    }
  }

  public Filler206 filler206 = (Filler206) new Filler206().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler208 extends NGroup {
    // COB:          03 WS-PACKED-FLD-01-17 OCCURS 256 PIC S9(01)V9(17)   COMP-3.
    public NPacked[] wsPackedFld0117 = AbstractNField.occurs(256, new NPacked(10, 17));

    public NPacked wsPackedFld0117AtIndex(int index) {
      return getOccursInstance(wsPackedFld0117, index);
    }
  }

  public Filler208 filler208 = (Filler208) new Filler208().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler210 extends NGroup {
    // COB:          03 WS-PACKED-FLD-00-18 OCCURS 256 PIC SV9(18)        COMP-3.
    public NPacked[] wsPackedFld0018 = AbstractNField.occurs(256, new NPacked(10, 18));

    public NPacked wsPackedFld0018AtIndex(int index) {
      return getOccursInstance(wsPackedFld0018, index);
    }
  }

  public Filler210 filler210 = (Filler210) new Filler210().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler212 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-18-00 OCCURS 256 PIC 9(18)       COMP-3.
    public NPacked[] wsPackedNsFld1800 = AbstractNField.occurs(256, new NPacked(10, false));

    public NPacked wsPackedNsFld1800AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld1800, index);
    }
  }

  public Filler212 filler212 = (Filler212) new Filler212().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler214 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-17-01 OCCURS 256 PIC 9(17)V9     COMP-3.
    public NPacked[] wsPackedNsFld1701 = AbstractNField.occurs(256, new NPacked(10, false));

    public NPacked wsPackedNsFld1701AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld1701, index);
    }
  }

  public Filler214 filler214 = (Filler214) new Filler214().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler216 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-16-02 OCCURS 256 PIC 9(16)V9(02) COMP-3.
    public NPacked[] wsPackedNsFld1602 = AbstractNField.occurs(256, new NPacked(10, 2, false));

    public NPacked wsPackedNsFld1602AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld1602, index);
    }
  }

  public Filler216 filler216 = (Filler216) new Filler216().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler218 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-15-03 OCCURS 256 PIC 9(15)V9(03) COMP-3.
    public NPacked[] wsPackedNsFld1503 = AbstractNField.occurs(256, new NPacked(10, 3, false));

    public NPacked wsPackedNsFld1503AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld1503, index);
    }
  }

  public Filler218 filler218 = (Filler218) new Filler218().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler220 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-14-04 OCCURS 256 PIC 9(14)V9(04) COMP-3.
    public NPacked[] wsPackedNsFld1404 = AbstractNField.occurs(256, new NPacked(10, 4, false));

    public NPacked wsPackedNsFld1404AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld1404, index);
    }
  }

  public Filler220 filler220 = (Filler220) new Filler220().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler222 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-13-05 OCCURS 256 PIC 9(13)V9(05) COMP-3.
    public NPacked[] wsPackedNsFld1305 = AbstractNField.occurs(256, new NPacked(10, 5, false));

    public NPacked wsPackedNsFld1305AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld1305, index);
    }
  }

  public Filler222 filler222 = (Filler222) new Filler222().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler224 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-12-06 OCCURS 256 PIC 9(12)V9(06) COMP-3.
    public NPacked[] wsPackedNsFld1206 = AbstractNField.occurs(256, new NPacked(10, 6, false));

    public NPacked wsPackedNsFld1206AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld1206, index);
    }
  }

  public Filler224 filler224 = (Filler224) new Filler224().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler226 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-11-07 OCCURS 256 PIC 9(11)V9(07) COMP-3.
    public NPacked[] wsPackedNsFld1107 = AbstractNField.occurs(256, new NPacked(10, 7, false));

    public NPacked wsPackedNsFld1107AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld1107, index);
    }
  }

  public Filler226 filler226 = (Filler226) new Filler226().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler228 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-10-08 OCCURS 256 PIC 9(10)V9(08) COMP-3.
    public NPacked[] wsPackedNsFld1008 = AbstractNField.occurs(256, new NPacked(10, 8, false));

    public NPacked wsPackedNsFld1008AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld1008, index);
    }
  }

  public Filler228 filler228 = (Filler228) new Filler228().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler230 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-09-09 OCCURS 256 PIC 9(09)V9(09) COMP-3.
    public NPacked[] wsPackedNsFld0909 = AbstractNField.occurs(256, new NPacked(10, 9, false));

    public NPacked wsPackedNsFld0909AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld0909, index);
    }
  }

  public Filler230 filler230 = (Filler230) new Filler230().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler232 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-08-10 OCCURS 256 PIC 9(08)V9(10) COMP-3.
    public NPacked[] wsPackedNsFld0810 = AbstractNField.occurs(256, new NPacked(10, 10, false));

    public NPacked wsPackedNsFld0810AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld0810, index);
    }
  }

  public Filler232 filler232 = (Filler232) new Filler232().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler234 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-07-11 OCCURS 256 PIC 9(07)V9(11) COMP-3.
    public NPacked[] wsPackedNsFld0711 = AbstractNField.occurs(256, new NPacked(10, 11, false));

    public NPacked wsPackedNsFld0711AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld0711, index);
    }
  }

  public Filler234 filler234 = (Filler234) new Filler234().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler236 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-06-12 OCCURS 256 PIC 9(06)V9(12) COMP-3.
    public NPacked[] wsPackedNsFld0612 = AbstractNField.occurs(256, new NPacked(10, 12, false));

    public NPacked wsPackedNsFld0612AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld0612, index);
    }
  }

  public Filler236 filler236 = (Filler236) new Filler236().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler238 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-05-13 OCCURS 256 PIC 9(05)V9(13) COMP-3.
    public NPacked[] wsPackedNsFld0513 = AbstractNField.occurs(256, new NPacked(10, 13, false));

    public NPacked wsPackedNsFld0513AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld0513, index);
    }
  }

  public Filler238 filler238 = (Filler238) new Filler238().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler240 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-04-14 OCCURS 256 PIC 9(04)V9(14) COMP-3.
    public NPacked[] wsPackedNsFld0414 = AbstractNField.occurs(256, new NPacked(10, 14, false));

    public NPacked wsPackedNsFld0414AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld0414, index);
    }
  }

  public Filler240 filler240 = (Filler240) new Filler240().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler242 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-03-15 OCCURS 256 PIC 9(03)V9(15) COMP-3.
    public NPacked[] wsPackedNsFld0315 = AbstractNField.occurs(256, new NPacked(10, 15, false));

    public NPacked wsPackedNsFld0315AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld0315, index);
    }
  }

  public Filler242 filler242 = (Filler242) new Filler242().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler244 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-02-16 OCCURS 256 PIC 9(02)V9(16) COMP-3.
    public NPacked[] wsPackedNsFld0216 = AbstractNField.occurs(256, new NPacked(10, 16, false));

    public NPacked wsPackedNsFld0216AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld0216, index);
    }
  }

  public Filler244 filler244 = (Filler244) new Filler244().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler246 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-01-17 OCCURS 256 PIC 9(01)V9(17) COMP-3.
    public NPacked[] wsPackedNsFld0117 = AbstractNField.occurs(256, new NPacked(10, 17, false));

    public NPacked wsPackedNsFld0117AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld0117, index);
    }
  }

  public Filler246 filler246 = (Filler246) new Filler246().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler248 extends NGroup {
    // COB:          03 WS-PACKED-NS-FLD-00-18 OCCURS 256 PIC V9(18)      COMP-3.
    public NPacked[] wsPackedNsFld0018 = AbstractNField.occurs(256, new NPacked(10, 18, false));

    public NPacked wsPackedNsFld0018AtIndex(int index) {
      return getOccursInstance(wsPackedNsFld0018, index);
    }
  }

  public Filler248 filler248 = (Filler248) new Filler248().redefines(wsCommonFldPacked);

  // COB:        01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
  public static class Filler250 extends NGroup {
    // COB:          03 WS-PACKED-FLD-CHR      OCCURS 256         PIC X(10).
    public NChar[] wsPackedFldChr = AbstractNField.occurs(256, new NChar(10));

    public NChar wsPackedFldChrAtIndex(int index) {
      return getOccursInstance(wsPackedFldChr, index);
    }
  }

  public Filler250 filler250 = (Filler250) new Filler250().redefines(wsCommonFldPacked);
  // COB:        01 FILLER.
  public Filler253 filler253 = new Filler253();

  public static class Filler253 extends NGroup {

    // COB:          03 WS-XZONED-18-CHR OCCURS 256               PIC X(18).
    public NChar[] wsXzoned18Chr = AbstractNField.occurs(256, new NChar(18));

    public NChar wsXzoned18ChrAtIndex(int index) {
      return getOccursInstance(wsXzoned18Chr, index);
    }
  }

  // COB:        01 WS-XZONED-18                                PIC X(18).
  public NChar wsXzoned18 = new NChar(18);
  // COB:        01 WS-NUMZONED-18-00 REDEFINES WS-XZONED-18    PIC S9(18).
  public NZoned wsNumzoned1800 = new NZoned(18).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-17-01 REDEFINES WS-XZONED-18    PIC S9(17)V9.
  public NZoned wsNumzoned1701 = new NZoned(18).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-16-02 REDEFINES WS-XZONED-18    PIC S9(16)V9(02).
  public NZoned wsNumzoned1602 = new NZoned(18, 2).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-15-03 REDEFINES WS-XZONED-18    PIC S9(15)V9(03).
  public NZoned wsNumzoned1503 = new NZoned(18, 3).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-14-04 REDEFINES WS-XZONED-18    PIC S9(14)V9(04).
  public NZoned wsNumzoned1404 = new NZoned(18, 4).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-13-05 REDEFINES WS-XZONED-18    PIC S9(13)V9(05).
  public NZoned wsNumzoned1305 = new NZoned(18, 5).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-12-06 REDEFINES WS-XZONED-18    PIC S9(12)V9(06).
  public NZoned wsNumzoned1206 = new NZoned(18, 6).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-11-07 REDEFINES WS-XZONED-18    PIC S9(11)V9(07).
  public NZoned wsNumzoned1107 = new NZoned(18, 7).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-10-08 REDEFINES WS-XZONED-18    PIC S9(10)V9(08).
  public NZoned wsNumzoned1008 = new NZoned(18, 8).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-09-09 REDEFINES WS-XZONED-18    PIC S9(09)V9(09).
  public NZoned wsNumzoned0909 = new NZoned(18, 9).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-08-10 REDEFINES WS-XZONED-18    PIC S9(08)V9(10).
  public NZoned wsNumzoned0810 = new NZoned(18, 10).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-07-11 REDEFINES WS-XZONED-18    PIC S9(07)V9(11).
  public NZoned wsNumzoned0711 = new NZoned(18, 11).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-06-12 REDEFINES WS-XZONED-18    PIC S9(06)V9(12).
  public NZoned wsNumzoned0612 = new NZoned(18, 12).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-05-13 REDEFINES WS-XZONED-18    PIC S9(05)V9(13).
  public NZoned wsNumzoned0513 = new NZoned(18, 13).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-04-14 REDEFINES WS-XZONED-18    PIC S9(04)V9(14).
  public NZoned wsNumzoned0414 = new NZoned(18, 14).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-03-15 REDEFINES WS-XZONED-18    PIC S9(03)V9(15).
  public NZoned wsNumzoned0315 = new NZoned(18, 15).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-02-16 REDEFINES WS-XZONED-18    PIC S9(02)V9(16).
  public NZoned wsNumzoned0216 = new NZoned(18, 16).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-01-17 REDEFINES WS-XZONED-18    PIC S9(01)V9(17).
  public NZoned wsNumzoned0117 = new NZoned(18, 17).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-00-18 REDEFINES WS-XZONED-18    PIC SV9(18).
  public NZoned wsNumzoned0018 = new NZoned(18, 18).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-18-00 REDEFINES WS-XZONED-18 PIC 9(18).
  public NZoned wsNumzonedNs1800 = new NZoned(18, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-17-01 REDEFINES WS-XZONED-18 PIC 9(17)V9.
  public NZoned wsNumzonedNs1701 = new NZoned(18, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-16-02 REDEFINES WS-XZONED-18 PIC 9(16)V9(02).
  public NZoned wsNumzonedNs1602 = new NZoned(18, 2, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-15-03 REDEFINES WS-XZONED-18 PIC 9(15)V9(03).
  public NZoned wsNumzonedNs1503 = new NZoned(18, 3, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-14-04 REDEFINES WS-XZONED-18 PIC 9(14)V9(04).
  public NZoned wsNumzonedNs1404 = new NZoned(18, 4, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-13-05 REDEFINES WS-XZONED-18 PIC 9(13)V9(05).
  public NZoned wsNumzonedNs1305 = new NZoned(18, 5, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-12-06 REDEFINES WS-XZONED-18 PIC 9(12)V9(06).
  public NZoned wsNumzonedNs1206 = new NZoned(18, 6, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-11-07 REDEFINES WS-XZONED-18 PIC 9(11)V9(07).
  public NZoned wsNumzonedNs1107 = new NZoned(18, 7, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-10-08 REDEFINES WS-XZONED-18 PIC 9(10)V9(08).
  public NZoned wsNumzonedNs1008 = new NZoned(18, 8, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-09-09 REDEFINES WS-XZONED-18 PIC 9(09)V9(09).
  public NZoned wsNumzonedNs0909 = new NZoned(18, 9, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-08-10 REDEFINES WS-XZONED-18 PIC 9(08)V9(10).
  public NZoned wsNumzonedNs0810 = new NZoned(18, 10, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-07-11 REDEFINES WS-XZONED-18 PIC 9(07)V9(11).
  public NZoned wsNumzonedNs0711 = new NZoned(18, 11, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-06-12 REDEFINES WS-XZONED-18 PIC 9(06)V9(12).
  public NZoned wsNumzonedNs0612 = new NZoned(18, 12, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-05-13 REDEFINES WS-XZONED-18 PIC 9(05)V9(13).
  public NZoned wsNumzonedNs0513 = new NZoned(18, 13, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-04-14 REDEFINES WS-XZONED-18 PIC 9(04)V9(14).
  public NZoned wsNumzonedNs0414 = new NZoned(18, 14, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-03-15 REDEFINES WS-XZONED-18 PIC 9(03)V9(15).
  public NZoned wsNumzonedNs0315 = new NZoned(18, 15, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-02-16 REDEFINES WS-XZONED-18 PIC 9(02)V9(16).
  public NZoned wsNumzonedNs0216 = new NZoned(18, 16, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-01-17 REDEFINES WS-XZONED-18 PIC 9(01)V9(17).
  public NZoned wsNumzonedNs0117 = new NZoned(18, 17, false).redefines(wsXzoned18);
  // COB:        01 WS-NUMZONED-NS-00-18 REDEFINES WS-XZONED-18 PIC V9(18).
  public NZoned wsNumzonedNs0018 = new NZoned(18, 18, false).redefines(wsXzoned18);
  // COB:        01 WS-CK-POS                     PIC S9(4) COMP.
  public NInt16 wsCkPos = new NInt16();
  // COB:        01 WS-CK-LEN                     PIC S9(4) COMP.
  public NInt16 wsCkLen = new NInt16();
  // COB:        01 PAUTSUM0-CONCATENATED-KEY     PIC X(4096).
  public NChar pautsum0ConcatenatedKey = new NChar(4096);
  // COB:        01 PAUTDTL1-CONCATENATED-KEY     PIC X(4096).
  public NChar pautdtl1ConcatenatedKey = new NChar(4096);
  // COB:        01 PAUTINDX-CONCATENATED-KEY     PIC X(4096).
  public NChar pautindxConcatenatedKey = new NChar(4096);
  // COB:        01 PAUTSUM0-LEN                  PIC S9(9) COMP VALUE 100.
  public NInt32 pautsum0Len = new NInt32().initial(100);
  // COB:        01 PAUTDTL1-LEN                  PIC S9(9) COMP VALUE 200.
  public NInt32 pautdtl1Len = new NInt32().initial(200);
  // COB:        01 PAUTINDX-LEN                  PIC S9(9) COMP VALUE 6.
  public NInt32 pautindxLen = new NInt32().initial(6);
  // COB:        01 PAUTSUM0-LVL                  PIC S9(4) COMP VALUE 1.
  public NInt16 pautsum0Lvl = new NInt16().initial(1);
  // COB:        01 PAUTDTL1-LVL                  PIC S9(4) COMP VALUE 2.
  public NInt16 pautdtl1Lvl = new NInt16().initial(2);
  // COB:        01 PAUTINDX-LVL                  PIC S9(4) COMP VALUE 1.
  public NInt16 pautindxLvl = new NInt16().initial(1);
  // COB:        01 WS-SEGMENTS-MAX-LVL           PIC S9(4) COMP VALUE 2.
  public NInt16 wsSegmentsMaxLvl = new NInt16().initial(2);
  // COB:        01 SEGMENTS-LAST-AREA.
  public SegmentsLastArea segmentsLastArea = new SegmentsLastArea();

  public static class SegmentsLastArea extends NGroup {

    // COB:          03 PAUTSUM0-LAST-AREA               PIC X(100).
    public NChar pautsum0LastArea = new NChar(100);
    // COB:          03 PAUTDTL1-LAST-AREA               PIC X(200).
    public NChar pautdtl1LastArea = new NChar(200);
    // COB:          03 PAUTINDX-LAST-AREA               PIC X(6).
    public NChar pautindxLastArea = new NChar(6);
  }

  // COB:        01 WS-SEGMENTS-AREA.
  public WsSegmentsArea wsSegmentsArea = new WsSegmentsArea();

  public static class WsSegmentsArea extends NGroup {

    // COB:          03 PAUTSUM0-AREA               PIC X(100).
    public NChar pautsum0Area = new NChar(100);
    // COB:          03 PAUTDTL1-AREA               PIC X(200).
    public NChar pautdtl1Area = new NChar(200);
    // COB:          03 PAUTINDX-AREA               PIC X(6).
    public NChar pautindxArea = new NChar(6);
  }

  // COB:        01 WS-IMS-GU                     PIC X(4)  VALUE 'GU  '.
  public NChar wsImsGu = new NChar(4).initial("GU  ");
  // COB:        01 WS-IMS-GN                     PIC X(4)  VALUE 'GN  '.
  public NChar wsImsGn = new NChar(4).initial("GN  ");
  // COB:        01 WS-IMS-GNP                    PIC X(4)  VALUE 'GNP '.
  public NChar wsImsGnp = new NChar(4).initial("GNP ");
  // COB:        01 PAUTSUM0-SSA.
  public Pautsum0Ssa pautsum0Ssa = new Pautsum0Ssa();

  public static class Pautsum0Ssa extends NGroup {

    // COB:          03  FILLER                   PIC X(19)
    // COB:              VALUE 'PAUTSUM0(ACCNTID ='.
    public NChar filler325 = new NChar(19).initial("PAUTSUM0(ACCNTID =");
    // COB:          03  PAUTSUM0-KEY                PIC X(6).
    public NChar pautsum0Key = new NChar(6);
    // COB:          03  FILLER                   PIC X(2)  VALUE ') '.
    public NChar filler328 = new NChar(2).initial(") ");
  }

  // COB:        01 PAUTSUM0-SSA-UNQ.
  public Pautsum0SsaUnq pautsum0SsaUnq = new Pautsum0SsaUnq();

  public static class Pautsum0SsaUnq extends NGroup {

    // COB:          03  FILLER                   PIC X(9)
    // COB:              VALUE 'PAUTSUM0 '.
    public NChar filler330 = new NChar(9).initial("PAUTSUM0 ");
  }

  // COB:        01 PAUTDTL1-SSA.
  public Pautdtl1Ssa pautdtl1Ssa = new Pautdtl1Ssa();

  public static class Pautdtl1Ssa extends NGroup {

    // COB:          03  FILLER                   PIC X(19)
    // COB:              VALUE 'PAUTDTL1(PAUT9CTS='.
    public NChar filler333 = new NChar(19).initial("PAUTDTL1(PAUT9CTS=");
    // COB:          03  PAUTDTL1-KEY                PIC X(8).
    public NChar pautdtl1Key = new NChar(8);
    // COB:          03  FILLER                   PIC X(2)  VALUE ') '.
    public NChar filler336 = new NChar(2).initial(") ");
  }

  // COB:        01 PAUTDTL1-SSA-UNQ.
  public Pautdtl1SsaUnq pautdtl1SsaUnq = new Pautdtl1SsaUnq();

  public static class Pautdtl1SsaUnq extends NGroup {

    // COB:          03  FILLER                   PIC X(9)
    // COB:              VALUE 'PAUTDTL1 '.
    public NChar filler338 = new NChar(9).initial("PAUTDTL1 ");
  }

  // COB:        01 PAUTINDX-SSA.
  public PautindxSsa pautindxSsa = new PautindxSsa();

  public static class PautindxSsa extends NGroup {

    // COB:          03  FILLER                   PIC X(19)
    // COB:              VALUE 'PAUTINDX(ACCNTID ='.
    public NChar filler341 = new NChar(19).initial("PAUTINDX(ACCNTID =");
    // COB:          03  PAUTINDX-KEY                PIC X(6).
    public NChar pautindxKey = new NChar(6);
    // COB:          03  FILLER                   PIC X(2)  VALUE ') '.
    public NChar filler344 = new NChar(2).initial(") ");
  }

  // COB:        01 PAUTINDX-SSA-UNQ.
  public PautindxSsaUnq pautindxSsaUnq = new PautindxSsaUnq();

  public static class PautindxSsaUnq extends NGroup {

    // COB:          03  FILLER                   PIC X(9)
    // COB:              VALUE 'PAUTINDX '.
    public NChar filler346 = new NChar(9).initial("PAUTINDX ");
  }

  // COB:        01 WS-SEGMENT-NAME               PIC X(8)  VALUE SPACE.
  public NChar wsSegmentName = new NChar(8).initial(NChar.Space);
  // COB:        01 WS-SEGMENT-LEVEL              PIC 9(2)  VALUE ZERO.
  public NZoned wsSegmentLevel = new NZoned(2, false).initial(0);
  // COB:        01 WS-SEGMENT-LEN                PIC S9(4) COMP VALUE ZERO.
  public NInt16 wsSegmentLen = new NInt16().initial(0);
  // COB:        01 WS-SAVE-SEGM-LEN              PIC S9(4) COMP VALUE ZERO.
  public NInt16 wsSaveSegmLen = new NInt16().initial(0);
  // COB:        01 LG-RTN-USED-KEYS-AREA.
  public LgRtnUsedKeysArea lgRtnUsedKeysArea = new LgRtnUsedKeysArea();

  public static class LgRtnUsedKeysArea extends NGroup {

    // COB:          03 LG-RTN-FB-KEY-MAX-LENGTH           PIC S9(9) COMP.
    public NInt32 lgRtnFbKeyMaxLength = new NInt32();
    // COB:          03 LG-RTN-USED-KEY-SECONDARY          PIC X(255).
    public NChar lgRtnUsedKeySecondary = new NChar(255);
    // COB:          03 LG-RTN-USED-KEY-GNUNQ-NEXT-SEG     PIC X(8).
    public NChar lgRtnUsedKeyGnunqNextSeg = new NChar(8);
    // COB:          03 LG-RTN-USED-KEY-GNUNQ-SAVE-POS     PIC X(512).
    public NChar lgRtnUsedKeyGnunqSavePos = new NChar(512);
    // COB:          03 LG-RUN-DBD-STATUS                  PIC S9(4) COMP.
    public NInt16 lgRunDbdStatus = new NInt16();
    // COB:          03 LG-RUN-IMS-DUAL-POINTER            POINTER.
    public NPointer lgRunImsDualPointer = new NPointer();
    // COB:          03 LG-RUN-PCB-INDEX                   PIC 9(3).
    public NZoned lgRunPcbIndex = new NZoned(3, false);
    // COB:          03 LG-RUN-IO-AREA-PTR                 POINTER.
    public NPointer lgRunIoAreaPtr = new NPointer();
    // COB:          03 LG-INIT-DBD-STATUS                 PIC S9(4) COMP.
    public NInt16 lgInitDbdStatus = new NInt16();
    // COB:          03 FILLER                             PIC X(206).
    public NChar filler368 = new NChar(206);

    // COB:          03 LG-RTN-USED-KEY-VALUE OCCURS 8.
    public static class LgRtnUsedKeyValue extends NGroup {
      // COB:            05 LG-RTN-USED-SSA-INFO             PIC X(1055).
      public NChar lgRtnUsedSsaInfo = new NChar(1055);
      // COB:            05 LG-RTN-USED-KEY-STATUS           PIC X.
      public NChar lgRtnUsedKeyStatus = new NChar(1);

      // COB:              88 LG-RTN-USED-KEY-NOT-CHANGED    VALUE '0' X'00' ' '.
      public boolean lgRtnUsedKeyNotChanged() {
        return lgRtnUsedKeyStatus.equals("0")
            || lgRtnUsedKeyStatus.equals(hexToString("00"))
            || lgRtnUsedKeyStatus.equals(" ");
      }

      public void setLgRtnUsedKeyNotChanged(boolean value) {
        if (value) lgRtnUsedKeyStatus.setValue("0");
      }

      // COB:              88 LG-RTN-USED-KEY-CHANGED        VALUE '1'.
      public boolean lgRtnUsedKeyChanged() {
        return lgRtnUsedKeyStatus.equals("1");
      }

      public void setLgRtnUsedKeyChanged(boolean value) {
        if (value) lgRtnUsedKeyStatus.setValue("1");
      }

      // COB:              88 LG-RTN-USED-KEY-IS-INDEX       VALUE '2'.
      public boolean lgRtnUsedKeyIsIndex() {
        return lgRtnUsedKeyStatus.equals("2");
      }

      public void setLgRtnUsedKeyIsIndex(boolean value) {
        if (value) lgRtnUsedKeyStatus.setValue("2");
      }

      // COB:              88 LG-RTN-USED-KEY-DUPREC         VALUE '3'.
      public boolean lgRtnUsedKeyDuprec() {
        return lgRtnUsedKeyStatus.equals("3");
      }

      public void setLgRtnUsedKeyDuprec(boolean value) {
        if (value) lgRtnUsedKeyStatus.setValue("3");
      }

      // COB:            05 LG-RTN-USED-SSA-KEYS.
      public static class LgRtnUsedSsaKeys extends NGroup {
        // COB:              07 LG-RTN-USED-KEY-ALPHA          PIC X(256).
        public NChar lgRtnUsedKeyAlpha = new NChar(256);
        // COB:              07 LG-RTN-USED-KEY-PACKED REDEFINES LG-RTN-USED-KEY-ALPHA
        public NPacked lgRtnUsedKeyPacked = new NPacked(10).redefines(lgRtnUsedKeyAlpha);
        // COB:              07 LG-RTN-USED-KEY-NUMERIC        PIC S9(9) COMP.
        public NInt32 lgRtnUsedKeyNumeric = new NInt32();
        // COB:              07 LG-RTN-USED-KEY-NUMERIC-PREV   PIC S9(9) COMP.
        public NInt32 lgRtnUsedKeyNumericPrev = new NInt32();
        // COB:              07 LG-RTN-USED-KEY-NUMERIC-NEXT   PIC S9(9) COMP.
        public NInt32 lgRtnUsedKeyNumericNext = new NInt32();
        // COB:              07 LG-RTN-USED-KEY-PARENT-ALPHA   PIC X(256).
        public NChar lgRtnUsedKeyParentAlpha = new NChar(256);
        // COB:              07 LG-RTN-USED-KEY-PARENT-NUMERIC PIC S9(9) COMP.
        public NInt32 lgRtnUsedKeyParentNumeric = new NInt32();

        // COB:              07 FILLER OCCURS 32.
        public static class Filler388 extends NGroup {
          // COB:                09 LG-RTN-USED-KEY-LAST-SX      PIC S9(9) COMP.
          public NInt32 lgRtnUsedKeyLastSx = new NInt32();
        }

        public Filler388[] filler388 = AbstractNField.occurs(32, new Filler388());

        public Filler388 filler388AtIndex(int index) {
          return getOccursInstance(filler388, index);
        }

        // COB:              07 LG-RTN-USED-LAST-SEGMENT       PIC X(8).
        public NChar lgRtnUsedLastSegment = new NChar(8);
        // COB:              07 LG-RTN-LAST-OPEN-CURSOR        PIC 9(8).
        public NZoned lgRtnLastOpenCursor = new NZoned(8, false);
        // COB:              07 FILLER                         PIC X(272).
        public NChar filler392 = new NChar(272);
      }

      public LgRtnUsedSsaKeys lgRtnUsedSsaKeys = new LgRtnUsedSsaKeys();
    }

    public LgRtnUsedKeyValue[] lgRtnUsedKeyValue =
        AbstractNField.occurs(8, new LgRtnUsedKeyValue());

    public LgRtnUsedKeyValue lgRtnUsedKeyValueAtIndex(int index) {
      return getOccursInstance(lgRtnUsedKeyValue, index);
    }
  }

  // COB:        01 LG-FB-KEY-LENGTH                     PIC S9(9) COMP.
  public NInt32 lgFbKeyLength = new NInt32();
  // COB:        01 LG-IO-AREA                           PIC X(32000).
  public NChar lgIoArea = new NChar(32000);
  // COB:        01 LG-FB-KEY-AREA                       PIC X(32000).
  public NChar lgFbKeyArea = new NChar(32000);
  // COB:        01 LG-IO-AREA-POS                       PIC S9(9) COMP.
  public NInt32 lgIoAreaPos = new NInt32();
  // COB:        01 LG-FB-KEY-AREA-POS                   PIC S9(9) COMP.
  public NInt32 lgFbKeyAreaPos = new NInt32();
  // COB:        01 WS-FUNCTION-HOLD-CLAUSE       PIC 9     VALUE ZERO.
  public NZoned wsFunctionHoldClause = new NZoned(1, false);

  // COB:          88 COMMAND-WITHOUT-HOLD        VALUE 0.
  public boolean commandWithoutHold() {
    return wsFunctionHoldClause.equals(0);
  }

  public void setCommandWithoutHold(boolean value) {
    if (value) wsFunctionHoldClause.setValue(0);
  }

  // COB:          88 COMMAND-WITH-HOLD           VALUE 1.
  public boolean commandWithHold() {
    return wsFunctionHoldClause.equals(1);
  }

  public void setCommandWithHold(boolean value) {
    if (value) wsFunctionHoldClause.setValue(1);
  }

  // COB:        01 SQL-CONDITION-CLAUSE.
  public SqlConditionClause sqlConditionClause = new SqlConditionClause();

  public static class SqlConditionClause extends NGroup {

    // COB:          05 SQL-CONDITION-CLAUSE-LENGTH PIC S9(4) COMP.
    public NInt16 sqlConditionClauseLength = new NInt16();
    // COB:          05 SQL-CONDITION-CLAUSE-TEXT   PIC X(32000).
    public NChar sqlConditionClauseText = new NChar(32000);
  }

  // COB:        01 SQL-JOIN-CLAUSE.
  public SqlJoinClause sqlJoinClause = new SqlJoinClause();

  public static class SqlJoinClause extends NGroup {

    // COB:          05 SQL-JOIN-CLAUSE-LENGTH      PIC S9(4) COMP.
    public NInt16 sqlJoinClauseLength = new NInt16();
    // COB:          05 SQL-JOIN-CLAUSE-TEXT        PIC X(32000).
    public NChar sqlJoinClauseText = new NChar(32000);
  }

  // COB:        01 WS-FB-KEY-LENGTH              PIC S9(9) COMP.
  public NInt32 wsFbKeyLength = new NInt32();
  // COB:        01 WS-FB-KEY-AREA                PIC X(32000).
  public NChar wsFbKeyArea = new NChar(32000);
  // COB:        01 WS-DBD-NAME                   PIC X(8)  VALUE 'DBPAUTX0'.
  public NChar wsDbdName = new NChar(8).initial("DBPAUTX0");
  // COB:        01 WS-LAST-SEGMENT-NAME          PIC X(8).
  public NChar wsLastSegmentName = new NChar(8);
  // COB:        01 WS-LAST-SEGMENT-LEVEL         PIC S9(9) COMP.
  public NInt32 wsLastSegmentLevel = new NInt32();
  // COB:        01 WS-INIT-SEGMENT-LEVEL         PIC S9(4) COMP.
  public NInt16 wsInitSegmentLevel = new NInt16();
  // COB:        01 WS-LAST-IORTN-SECTION         PIC X(30).
  public NChar wsLastIortnSection = new NChar(30);
  // COB:        01 WS-NEXT-SEGMENT-NAME          PIC X(8) VALUE SPACE.
  public NChar wsNextSegmentName = new NChar(8).initial(NChar.Space);
  // COB:        01 WS-NEXT-FUNCTION              PIC X(20) VALUE SPACE.
  public NChar wsNextFunction = new NChar(20).initial(NChar.Space);
  // COB:        01 FILLER                        PIC X VALUE SPACE.
  public NChar filler421 = new NChar(1);

  // COB:          88 EXEC-DLI-ACCESS             VALUE '0'.
  public boolean execDliAccess() {
    return filler421.equals("0");
  }

  public void setExecDliAccess(boolean value) {
    if (value) filler421.setValue("0");
  }

  // COB:          88 SKIP-DLI-ACCESS             VALUE '1'.
  public boolean skipDliAccess() {
    return filler421.equals("1");
  }

  public void setSkipDliAccess(boolean value) {
    if (value) filler421.setValue("1");
  }

  // COB:        01 FILLER                        PIC X VALUE SPACE.
  public NChar filler424 = new NChar(1);

  // COB:          88 EXEC-SEGMENT-ACCESS         VALUE '0'.
  public boolean execSegmentAccess() {
    return filler424.equals("0");
  }

  public void setExecSegmentAccess(boolean value) {
    if (value) filler424.setValue("0");
  }

  // COB:          88 SKIP-SEGMENT-ACCESS         VALUE '1'.
  public boolean skipSegmentAccess() {
    return filler424.equals("1");
  }

  public void setSkipSegmentAccess(boolean value) {
    if (value) filler424.setValue("1");
  }

  // COB:        01 FILLER                        PIC X VALUE SPACE.
  public NChar filler427 = new NChar(1);

  // COB:          88 EXEC-SAVE-AREA              VALUE '0'.
  public boolean execSaveArea() {
    return filler427.equals("0");
  }

  public void setExecSaveArea(boolean value) {
    if (value) filler427.setValue("0");
  }

  // COB:          88 SKIP-SAVE-AREA              VALUE '1'.
  public boolean skipSaveArea() {
    return filler427.equals("1");
  }

  public void setSkipSaveArea(boolean value) {
    if (value) filler427.setValue("1");
  }

  // COB:        01 FILLER                        PIC X VALUE SPACE.
  public NChar filler430 = new NChar(1);

  // COB:          88 EXEC-CHECK-MISMATCH         VALUE '0'.
  public boolean execCheckMismatch() {
    return filler430.equals("0");
  }

  public void setExecCheckMismatch(boolean value) {
    if (value) filler430.setValue("0");
  }

  // COB:          88 SKIP-CHECK-MISMATCH         VALUE '1'.
  public boolean skipCheckMismatch() {
    return filler430.equals("1");
  }

  public void setSkipCheckMismatch(boolean value) {
    if (value) filler430.setValue("1");
  }

  // COB:        01 FILLER                        PIC X VALUE SPACE.
  public NChar filler433 = new NChar(1);

  // COB:          88 DEFAULT-DYNAMIC-ACCESS      VALUE '0'.
  public boolean defaultDynamicAccess() {
    return filler433.equals("0");
  }

  public void setDefaultDynamicAccess(boolean value) {
    if (value) filler433.setValue("0");
  }

  // COB:          88 CUSTOM-DYNAMIC-ACCESS       VALUE '1'.
  public boolean customDynamicAccess() {
    return filler433.equals("1");
  }

  public void setCustomDynamicAccess(boolean value) {
    if (value) filler433.setValue("1");
  }

  // COB:        01 FILLER                        PIC X VALUE SPACE.
  public NChar filler436 = new NChar(1);

  // COB:          88 CUSTOM-STATIC-ACCESS        VALUE '0'.
  public boolean customStaticAccess() {
    return filler436.equals("0");
  }

  public void setCustomStaticAccess(boolean value) {
    if (value) filler436.setValue("0");
  }

  // COB:          88 CUSTOM-CURSOR-ACCESS        VALUE '1'.
  public boolean customCursorAccess() {
    return filler436.equals("1");
  }

  public void setCustomCursorAccess(boolean value) {
    if (value) filler436.setValue("1");
  }

  // COB:        01 WS-DBD-STATUS                 PIC S9(4) COMP.
  public NInt16 wsDbdStatus = new NInt16();

  // COB:          88 WS-KEEP-AS-IS               VALUE 0.
  public boolean wsKeepAsIs() {
    return wsDbdStatus.equals(0);
  }

  public void setWsKeepAsIs(boolean value) {
    if (value) wsDbdStatus.setValue(0);
  }

  // COB:          88 WS-REVERT-TO-SQL-ONLY       VALUE 1.
  public boolean wsRevertToSqlOnly() {
    return wsDbdStatus.equals(1);
  }

  public void setWsRevertToSqlOnly(boolean value) {
    if (value) wsDbdStatus.setValue(1);
  }

  // COB:        01 WS-STATUS-CODE                PIC X(2) VALUE SPACE.
  public NChar wsStatusCode = new NChar(2);

  // COB:           88 WS-PCB-MISMATCH            VALUE 'XY'.
  public boolean wsPcbMismatch() {
    return wsStatusCode.equals("XY");
  }

  public void setWsPcbMismatch(boolean value) {
    if (value) wsStatusCode.setValue("XY");
  }

  // COB:           88 WS-AREA-MISMATCH           VALUE 'XZ'.
  public boolean wsAreaMismatch() {
    return wsStatusCode.equals("XZ");
  }

  public void setWsAreaMismatch(boolean value) {
    if (value) wsStatusCode.setValue("XZ");
  }

  // COB:        01 WS-SSA-ALIAS.
  public WsSsaAlias wsSsaAlias = new WsSsaAlias();

  public static class WsSsaAlias extends NGroup {

    // COB:          03 WS-FUNC-ALIAS               PIC S9(9) COMP.
    public NInt32 wsFuncAlias = new NInt32();
    // COB:          03 FILLER                      PIC 9.
    public NZoned filler447 = new NZoned(1, false);

    // COB:            88 IS-NOT-ALIAS              VALUE 0.
    public boolean isNotAlias() {
      return filler447.equals(0);
    }

    public void setIsNotAlias(boolean value) {
      if (value) filler447.setValue(0);
    }

    // COB:            88 IS-ALIAS                  VALUE 1.
    public boolean isAlias() {
      return filler447.equals(1);
    }

    public void setIsAlias(boolean value) {
      if (value) filler447.setValue(1);
    }
  }

  // COB:        01 IRIS-CUSTOM-FUNC-FOUND        PIC X(1) VALUE 'Y'.
  public NChar irisCustomFuncFound = new NChar(1);

  // COB:            88  ACCESS-SEARCH            VALUE 'Y'.
  public boolean accessSearch() {
    return irisCustomFuncFound.equals("Y");
  }

  public void setAccessSearch(boolean value) {
    if (value) irisCustomFuncFound.setValue("Y");
  }

  // COB:            88  ACCESS-NOT-FOUND         VALUE 'N'.
  public boolean accessNotFound() {
    return irisCustomFuncFound.equals("N");
  }

  public void setAccessNotFound(boolean value) {
    if (value) irisCustomFuncFound.setValue("N");
  }

  public Pautsum0Copy pautsum0copy = new Pautsum0Copy();
  public Pautdtl1Copy pautdtl1copy = new Pautdtl1Copy();
}
