package com.aws.carddemo.batch.program;

import com.aws.carddemo.batch.program.storage.irisutil.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.esql.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;

public class IRISUTIL extends AbstractProgram {

  private final Sqlca sqlca = new Sqlca(this);
  @ProgramStorage final IrisutilWorking ws = new IrisutilWorking();

  // Linkage
  public class IrisutilLinkage extends NGroup {
    // COB:        01 IRIS-WORK-AREA.
    public IrisWorkArea irisWorkArea = new IrisWorkArea();

    public class IrisWorkArea extends NGroup {

      // COB:          03 IRIS-PROGRAM-NAME             PIC X(8)  VALUE :PROGNM:.
      public NChar irisProgramName = new NChar(8).initial("IRISUTIL");
      // COB:          03  IRIS-SEGMENT                 PIC X(8).
      public NChar irisSegment = new NChar(8);
      // COB: DC       03  IRIS-MSGNAME  REDEFINES IRIS-SEGMENT
      public NChar irisMsgname = new NChar(8).redefines(irisSegment);
      // COB: DC       03  IRIS-MODNAME  REDEFINES IRIS-SEGMENT
      public NChar irisModname = new NChar(8).redefines(irisSegment);
      // COB:          03  IRIS-CALL-ID                 PIC 9(4).
      public NZoned irisCallId = new NZoned(4, false);
      // COB:          03  IRIS-CUSTOM-FUNC-ID          PIC 9(8).
      public NZoned irisCustomFuncId = new NZoned(8, false);
      // COB:          03  IRIS-PSB-NAME                PIC X(8).
      public NChar irisPsbName = new NChar(8);
      // COB:          03  IRIS-FUNCTION                PIC X(20).
      public NChar irisFunction = new NChar(20);

      // COB:            88 SQL-SELECT-PRIMARY          VALUE 'SQL-SELECT-PRIMARY  '.
      public boolean sqlSelectPrimary() {
        return irisFunction.equals("SQL-SELECT-PRIMARY  ");
      }

      public void setSqlSelectPrimary(boolean value) {
        if (value) irisFunction.setValue("SQL-SELECT-PRIMARY  ");
      }

      // COB:            88 SQL-SELECT-PRIMARY-GE       VALUE 'SQL-SELECT-PRIMARYGE'.
      public boolean sqlSelectPrimaryGe() {
        return irisFunction.equals("SQL-SELECT-PRIMARYGE");
      }

      public void setSqlSelectPrimaryGe(boolean value) {
        if (value) irisFunction.setValue("SQL-SELECT-PRIMARYGE");
      }

      // COB:            88 SQL-SELECT-PRIMARY-GT       VALUE 'SQL-SELECT-PRIMARYGT'.
      public boolean sqlSelectPrimaryGt() {
        return irisFunction.equals("SQL-SELECT-PRIMARYGT");
      }

      public void setSqlSelectPrimaryGt(boolean value) {
        if (value) irisFunction.setValue("SQL-SELECT-PRIMARYGT");
      }

      // COB:            88 SQL-SELECT-PRIMARY-LE       VALUE 'SQL-SELECT-PRIMARYLE'.
      public boolean sqlSelectPrimaryLe() {
        return irisFunction.equals("SQL-SELECT-PRIMARYLE");
      }

      public void setSqlSelectPrimaryLe(boolean value) {
        if (value) irisFunction.setValue("SQL-SELECT-PRIMARYLE");
      }

      // COB:            88 SQL-SELECT-PRIMARY-LT       VALUE 'SQL-SELECT-PRIMARYLT'.
      public boolean sqlSelectPrimaryLt() {
        return irisFunction.equals("SQL-SELECT-PRIMARYLT");
      }

      public void setSqlSelectPrimaryLt(boolean value) {
        if (value) irisFunction.setValue("SQL-SELECT-PRIMARYLT");
      }

      // COB:            88 SQL-SELECT-PRIMARY-NE       VALUE 'SQL-SELECT-PRIMARYNE'.
      public boolean sqlSelectPrimaryNe() {
        return irisFunction.equals("SQL-SELECT-PRIMARYNE");
      }

      public void setSqlSelectPrimaryNe(boolean value) {
        if (value) irisFunction.setValue("SQL-SELECT-PRIMARYNE");
      }

      // COB:            88 SQL-SELECT-DYNAMIC          VALUE 'SQL-SELECT-DYNAMIC  '.
      public boolean sqlSelectDynamic() {
        return irisFunction.equals("SQL-SELECT-DYNAMIC  ");
      }

      public void setSqlSelectDynamic(boolean value) {
        if (value) irisFunction.setValue("SQL-SELECT-DYNAMIC  ");
      }

      // COB:            88 SQL-SELECT-STATIC           VALUE 'SQL-SELECT-STATIC   '.
      public boolean sqlSelectStatic() {
        return irisFunction.equals("SQL-SELECT-STATIC   ");
      }

      public void setSqlSelectStatic(boolean value) {
        if (value) irisFunction.setValue("SQL-SELECT-STATIC   ");
      }

      // COB:            88 SQL-SELECT-SEEK             VALUE 'SQL-SELECT-SEEK     '.
      public boolean sqlSelectSeek() {
        return irisFunction.equals("SQL-SELECT-SEEK     ");
      }

      public void setSqlSelectSeek(boolean value) {
        if (value) irisFunction.setValue("SQL-SELECT-SEEK     ");
      }

      // COB:            88 SQL-SELECT-NEXT             VALUE 'SQL-SELECT-NEXT     '.
      public boolean sqlSelectNext() {
        return irisFunction.equals("SQL-SELECT-NEXT     ");
      }

      public void setSqlSelectNext(boolean value) {
        if (value) irisFunction.setValue("SQL-SELECT-NEXT     ");
      }

      // COB:            88 SQL-SELECT-PATH             VALUE 'SQL-SELECT-PATH     '.
      public boolean sqlSelectPath() {
        return irisFunction.equals("SQL-SELECT-PATH     ");
      }

      public void setSqlSelectPath(boolean value) {
        if (value) irisFunction.setValue("SQL-SELECT-PATH     ");
      }

      // COB:            88 SQL-UPDATE                  VALUE 'SQL-UPDATE          '.
      public boolean sqlUpdate() {
        return irisFunction.equals("SQL-UPDATE          ");
      }

      public void setSqlUpdate(boolean value) {
        if (value) irisFunction.setValue("SQL-UPDATE          ");
      }

      // COB:            88 SQL-DELETE                  VALUE 'SQL-DELETE          '.
      public boolean sqlDelete() {
        return irisFunction.equals("SQL-DELETE          ");
      }

      public void setSqlDelete(boolean value) {
        if (value) irisFunction.setValue("SQL-DELETE          ");
      }

      // COB:            88 SQL-INSERT                  VALUE 'SQL-INSERT          '.
      public boolean sqlInsert() {
        return irisFunction.equals("SQL-INSERT          ");
      }

      public void setSqlInsert(boolean value) {
        if (value) irisFunction.setValue("SQL-INSERT          ");
      }

      // COB:            88 SQL-COMMIT                  VALUE 'SQL-COMMIT          '.
      public boolean sqlCommit() {
        return irisFunction.equals("SQL-COMMIT          ");
      }

      public void setSqlCommit(boolean value) {
        if (value) irisFunction.setValue("SQL-COMMIT          ");
      }

      // COB:            88 SQL-ROLLBACK                VALUE 'SQL-ROLLBACK        '.
      public boolean sqlRollback() {
        return irisFunction.equals("SQL-ROLLBACK        ");
      }

      public void setSqlRollback(boolean value) {
        if (value) irisFunction.setValue("SQL-ROLLBACK        ");
      }

      // COB:            88 SQL-SELECT-ALL-UNQ          VALUE 'SQL-SELECT-ALL-UNQ  '.
      public boolean sqlSelectAllUnq() {
        return irisFunction.equals("SQL-SELECT-ALL-UNQ  ");
      }

      public void setSqlSelectAllUnq(boolean value) {
        if (value) irisFunction.setValue("SQL-SELECT-ALL-UNQ  ");
      }

      // COB:            88 SQL-SELECT-ALL-SEEK         VALUE 'SQL-SELECT-ALL-SEEK '.
      public boolean sqlSelectAllSeek() {
        return irisFunction.equals("SQL-SELECT-ALL-SEEK ");
      }

      public void setSqlSelectAllSeek(boolean value) {
        if (value) irisFunction.setValue("SQL-SELECT-ALL-SEEK ");
      }

      // COB:            88 SQL-SELECT-ALL-NEXT         VALUE 'SQL-SELECT-ALL-NEXT '.
      public boolean sqlSelectAllNext() {
        return irisFunction.equals("SQL-SELECT-ALL-NEXT ");
      }

      public void setSqlSelectAllNext(boolean value) {
        if (value) irisFunction.setValue("SQL-SELECT-ALL-NEXT ");
      }

      // COB:            88 SQL-USER-CUSTOM             VALUE 'SQL-USER-CUSTOM     '.
      public boolean sqlUserCustom() {
        return irisFunction.equals("SQL-USER-CUSTOM     ");
      }

      public void setSqlUserCustom(boolean value) {
        if (value) irisFunction.setValue("SQL-USER-CUSTOM     ");
      }

      // COB:            88 SQL-DYNAMIC-SSA             VALUE 'SQL-DYNAMIC-SSA     '.
      public boolean sqlDynamicSsa() {
        return irisFunction.equals("SQL-DYNAMIC-SSA     ");
      }

      public void setSqlDynamicSsa(boolean value) {
        if (value) irisFunction.setValue("SQL-DYNAMIC-SSA     ");
      }

      // COB:            88 GSAM-GET-NEXT               VALUE 'GSAM-GET-NEXT       '.
      public boolean gsamGetNext() {
        return irisFunction.equals("GSAM-GET-NEXT       ");
      }

      public void setGsamGetNext(boolean value) {
        if (value) irisFunction.setValue("GSAM-GET-NEXT       ");
      }

      // COB:            88 GSAM-INSERT                 VALUE 'GSAM-INSERT         '.
      public boolean gsamInsert() {
        return irisFunction.equals("GSAM-INSERT         ");
      }

      public void setGsamInsert(boolean value) {
        if (value) irisFunction.setValue("GSAM-INSERT         ");
      }

      // COB:            88 GSAM-CLOSE                  VALUE 'GSAM-CLOSE          '.
      public boolean gsamClose() {
        return irisFunction.equals("GSAM-CLOSE          ");
      }

      public void setGsamClose(boolean value) {
        if (value) irisFunction.setValue("GSAM-CLOSE          ");
      }

      // COB:            88 GSAM-OPEN-INPUT             VALUE 'GSAM-OPEN-INPUT     '.
      public boolean gsamOpenInput() {
        return irisFunction.equals("GSAM-OPEN-INPUT     ");
      }

      public void setGsamOpenInput(boolean value) {
        if (value) irisFunction.setValue("GSAM-OPEN-INPUT     ");
      }

      // COB:            88 GSAM-OPEN-OUTPUT            VALUE 'GSAM-OPEN-OUTPUT    '.
      public boolean gsamOpenOutput() {
        return irisFunction.equals("GSAM-OPEN-OUTPUT    ");
      }

      public void setGsamOpenOutput(boolean value) {
        if (value) irisFunction.setValue("GSAM-OPEN-OUTPUT    ");
      }

      // COB:            88 CHKP-CHKP-BASIC             VALUE 'CHKP-CHKP-BASIC     '.
      public boolean chkpChkpBasic() {
        return irisFunction.equals("CHKP-CHKP-BASIC     ");
      }

      public void setChkpChkpBasic(boolean value) {
        if (value) irisFunction.setValue("CHKP-CHKP-BASIC     ");
      }

      // COB:            88 CHKP-CHKP-SYMBOLIC          VALUE 'CHKP-CHKP-BASIC     '.
      public boolean chkpChkpSymbolic() {
        return irisFunction.equals("CHKP-CHKP-BASIC     ");
      }

      public void setChkpChkpSymbolic(boolean value) {
        if (value) irisFunction.setValue("CHKP-CHKP-BASIC     ");
      }

      // COB:            88 CHKP-X-RESTART              VALUE 'CHKP-X-RESTART      '.
      public boolean chkpXRestart() {
        return irisFunction.equals("CHKP-X-RESTART      ");
      }

      public void setChkpXRestart(boolean value) {
        if (value) irisFunction.setValue("CHKP-X-RESTART      ");
      }

      // COB:            88 LOAD-DATA-DICTIONARY-SEG    VALUE 'LOAD-DICTIONARY-SEG '.
      public boolean loadDataDictionarySeg() {
        return irisFunction.equals("LOAD-DICTIONARY-SEG ");
      }

      public void setLoadDataDictionarySeg(boolean value) {
        if (value) irisFunction.setValue("LOAD-DICTIONARY-SEG ");
      }

      // COB:            88 LOAD-DATA-DICTIONARY-FLD    VALUE 'LOAD-DICTIONARY-FLD '.
      public boolean loadDataDictionaryFld() {
        return irisFunction.equals("LOAD-DICTIONARY-FLD ");
      }

      public void setLoadDataDictionaryFld(boolean value) {
        if (value) irisFunction.setValue("LOAD-DICTIONARY-FLD ");
      }

      // COB:          03 IRIS-IMS-FUNCTION             PIC X(4).
      public NChar irisImsFunction = new NChar(4);

      // COB:            88 IRIS-FUNC-GU                VALUE 'GU  '.
      public boolean irisFuncGu() {
        return irisImsFunction.equals("GU  ");
      }

      public void setIrisFuncGu(boolean value) {
        if (value) irisImsFunction.setValue("GU  ");
      }

      // COB:            88 IRIS-FUNC-GHU               VALUE 'GHU '.
      public boolean irisFuncGhu() {
        return irisImsFunction.equals("GHU ");
      }

      public void setIrisFuncGhu(boolean value) {
        if (value) irisImsFunction.setValue("GHU ");
      }

      // COB:            88 IRIS-FUNC-GN                VALUE 'GN  '.
      public boolean irisFuncGn() {
        return irisImsFunction.equals("GN  ");
      }

      public void setIrisFuncGn(boolean value) {
        if (value) irisImsFunction.setValue("GN  ");
      }

      // COB:            88 IRIS-FUNC-GHN               VALUE 'GHN '.
      public boolean irisFuncGhn() {
        return irisImsFunction.equals("GHN ");
      }

      public void setIrisFuncGhn(boolean value) {
        if (value) irisImsFunction.setValue("GHN ");
      }

      // COB:            88 IRIS-FUNC-GNP               VALUE 'GNP '.
      public boolean irisFuncGnp() {
        return irisImsFunction.equals("GNP ");
      }

      public void setIrisFuncGnp(boolean value) {
        if (value) irisImsFunction.setValue("GNP ");
      }

      // COB:            88 IRIS-FUNC-GHNP              VALUE 'GHNP'.
      public boolean irisFuncGhnp() {
        return irisImsFunction.equals("GHNP");
      }

      public void setIrisFuncGhnp(boolean value) {
        if (value) irisImsFunction.setValue("GHNP");
      }

      // COB:            88 IRIS-FUNC-ISRT              VALUE 'ISRT'.
      public boolean irisFuncIsrt() {
        return irisImsFunction.equals("ISRT");
      }

      public void setIrisFuncIsrt(boolean value) {
        if (value) irisImsFunction.setValue("ISRT");
      }

      // COB:            88 IRIS-FUNC-REPL              VALUE 'REPL'.
      public boolean irisFuncRepl() {
        return irisImsFunction.equals("REPL");
      }

      public void setIrisFuncRepl(boolean value) {
        if (value) irisImsFunction.setValue("REPL");
      }

      // COB:            88 IRIS-FUNC-DLET              VALUE 'DLET'.
      public boolean irisFuncDlet() {
        return irisImsFunction.equals("DLET");
      }

      public void setIrisFuncDlet(boolean value) {
        if (value) irisImsFunction.setValue("DLET");
      }

      // COB:            88 IRIS-FUNC-TERM              VALUE 'TERM'.
      public boolean irisFuncTerm() {
        return irisImsFunction.equals("TERM");
      }

      public void setIrisFuncTerm(boolean value) {
        if (value) irisImsFunction.setValue("TERM");
      }

      // COB:            88 IRIS-FUNC-CHKP              VALUE 'CHKP'.
      public boolean irisFuncChkp() {
        return irisImsFunction.equals("CHKP");
      }

      public void setIrisFuncChkp(boolean value) {
        if (value) irisImsFunction.setValue("CHKP");
      }

      // COB:            88 IRIS-FUNC-SYNC              VALUE 'SYNC'.
      public boolean irisFuncSync() {
        return irisImsFunction.equals("SYNC");
      }

      public void setIrisFuncSync(boolean value) {
        if (value) irisImsFunction.setValue("SYNC");
      }

      // COB:            88 IRIS-FUNC-SYMC              VALUE 'SYMC'.
      public boolean irisFuncSymc() {
        return irisImsFunction.equals("SYMC");
      }

      public void setIrisFuncSymc(boolean value) {
        if (value) irisImsFunction.setValue("SYMC");
      }

      // COB:            88 IRIS-FUNC-XRST              VALUE 'XRST'.
      public boolean irisFuncXrst() {
        return irisImsFunction.equals("XRST");
      }

      public void setIrisFuncXrst(boolean value) {
        if (value) irisImsFunction.setValue("XRST");
      }

      // COB:            88 IRIS-FUNC-DLCK              VALUE 'DLCK'.
      public boolean irisFuncDlck() {
        return irisImsFunction.equals("DLCK");
      }

      public void setIrisFuncDlck(boolean value) {
        if (value) irisImsFunction.setValue("DLCK");
      }

      // COB:            88 IRIS-FUNC-ROLL              VALUE 'ROLL'.
      public boolean irisFuncRoll() {
        return irisImsFunction.equals("ROLL");
      }

      public void setIrisFuncRoll(boolean value) {
        if (value) irisImsFunction.setValue("ROLL");
      }

      // COB:            88 IRIS-FUNC-ROLB              VALUE 'ROLB'.
      public boolean irisFuncRolb() {
        return irisImsFunction.equals("ROLB");
      }

      public void setIrisFuncRolb(boolean value) {
        if (value) irisImsFunction.setValue("ROLB");
      }

      // COB:            88 IRIS-FUNC-ROLS              VALUE 'ROLS'.
      public boolean irisFuncRols() {
        return irisImsFunction.equals("ROLS");
      }

      public void setIrisFuncRols(boolean value) {
        if (value) irisImsFunction.setValue("ROLS");
      }

      // COB:            88 IRIS-FUNC-CMD               VALUE 'CMD '.
      public boolean irisFuncCmd() {
        return irisImsFunction.equals("CMD ");
      }

      public void setIrisFuncCmd(boolean value) {
        if (value) irisImsFunction.setValue("CMD ");
      }

      // COB:            88 IRIS-FUNC-GCMD              VALUE 'GCMD'.
      public boolean irisFuncGcmd() {
        return irisImsFunction.equals("GCMD");
      }

      public void setIrisFuncGcmd(boolean value) {
        if (value) irisImsFunction.setValue("GCMD");
      }

      // COB:            88 IRIS-FUNC-INIT              VALUE 'INIT'.
      public boolean irisFuncInit() {
        return irisImsFunction.equals("INIT");
      }

      public void setIrisFuncInit(boolean value) {
        if (value) irisImsFunction.setValue("INIT");
      }

      // COB:            88 IRIS-FUNC-SCHD              VALUE 'SCHD'.
      public boolean irisFuncSchd() {
        return irisImsFunction.equals("SCHD");
      }

      public void setIrisFuncSchd(boolean value) {
        if (value) irisImsFunction.setValue("SCHD");
      }

      // COB:            88 IRIS-FUNC-SCHB              VALUE 'SCHB'.
      public boolean irisFuncSchb() {
        return irisImsFunction.equals("SCHB");
      }

      public void setIrisFuncSchb(boolean value) {
        if (value) irisImsFunction.setValue("SCHB");
      }

      // COB:            88 IRIS-FUNC-PCB               VALUE 'PCB '.
      public boolean irisFuncPcb() {
        return irisImsFunction.equals("PCB ");
      }

      public void setIrisFuncPcb(boolean value) {
        if (value) irisImsFunction.setValue("PCB ");
      }

      // COB:            88 IRIS-FUNC-DLIT              VALUE 'DLIT'.
      public boolean irisFuncDlit() {
        return irisImsFunction.equals("DLIT");
      }

      public void setIrisFuncDlit(boolean value) {
        if (value) irisImsFunction.setValue("DLIT");
      }

      // COB:            88 IRIS-FUNC-OPEN              VALUE 'OPEN'.
      public boolean irisFuncOpen() {
        return irisImsFunction.equals("OPEN");
      }

      public void setIrisFuncOpen(boolean value) {
        if (value) irisImsFunction.setValue("OPEN");
      }

      // COB:            88 IRIS-FUNC-CLSE              VALUE 'CLSE'.
      public boolean irisFuncClse() {
        return irisImsFunction.equals("CLSE");
      }

      public void setIrisFuncClse(boolean value) {
        if (value) irisImsFunction.setValue("CLSE");
      }

      // COB:            88 IRIS-FUNC-APSB              VALUE 'APSB'.
      public boolean irisFuncApsb() {
        return irisImsFunction.equals("APSB");
      }

      public void setIrisFuncApsb(boolean value) {
        if (value) irisImsFunction.setValue("APSB");
      }

      // COB:            88 IRIS-FUNC-DPSB              VALUE 'DPSB'.
      public boolean irisFuncDpsb() {
        return irisImsFunction.equals("DPSB");
      }

      public void setIrisFuncDpsb(boolean value) {
        if (value) irisImsFunction.setValue("DPSB");
      }

      // COB: DC         88 IRIS-FUNC-PURG              VALUE 'PURG'.
      public boolean irisFuncPurg() {
        return irisImsFunction.equals("PURG");
      }

      public void setIrisFuncPurg(boolean value) {
        if (value) irisImsFunction.setValue("PURG");
      }

      // COB: DC         88 IRIS-FUNC-CHNG              VALUE 'CHNG'.
      public boolean irisFuncChng() {
        return irisImsFunction.equals("CHNG");
      }

      public void setIrisFuncChng(boolean value) {
        if (value) irisImsFunction.setValue("CHNG");
      }

      // COB: DC         88 IRIS-FUNC-ENTR              VALUE 'ENTR'.
      public boolean irisFuncEntr() {
        return irisImsFunction.equals("ENTR");
      }

      public void setIrisFuncEntr(boolean value) {
        if (value) irisImsFunction.setValue("ENTR");
      }

      // COB: DC         88 IRIS-FUNC-GOBA              VALUE 'GOBA'.
      public boolean irisFuncGoba() {
        return irisImsFunction.equals("GOBA");
      }

      public void setIrisFuncGoba(boolean value) {
        if (value) irisImsFunction.setValue("GOBA");
      }

      // COB:            88 IRIS-FUNC-INQY              VALUE 'INQY'.
      public boolean irisFuncInqy() {
        return irisImsFunction.equals("INQY");
      }

      public void setIrisFuncInqy(boolean value) {
        if (value) irisImsFunction.setValue("INQY");
      }

      // COB:            88 IRIS-FUNC-POS               VALUE 'POS '.
      public boolean irisFuncPos() {
        return irisImsFunction.equals("POS ");
      }

      public void setIrisFuncPos(boolean value) {
        if (value) irisImsFunction.setValue("POS ");
      }

      // COB:            88 IRIS-FUNC-SETS              VALUE 'SETS'.
      public boolean irisFuncSets() {
        return irisImsFunction.equals("SETS");
      }

      public void setIrisFuncSets(boolean value) {
        if (value) irisImsFunction.setValue("SETS");
      }

      // COB:            88 IRIS-FUNC-READQ             VALUE 'REAQ'.
      public boolean irisFuncReadq() {
        return irisImsFunction.equals("REAQ");
      }

      public void setIrisFuncReadq(boolean value) {
        if (value) irisImsFunction.setValue("REAQ");
      }

      // COB:            88 IRIS-FUNC-WRITEQ            VALUE 'WRIQ'.
      public boolean irisFuncWriteq() {
        return irisImsFunction.equals("WRIQ");
      }

      public void setIrisFuncWriteq(boolean value) {
        if (value) irisImsFunction.setValue("WRIQ");
      }

      // COB:            88 IRIS-FUNC-DELETEQ           VALUE 'DELQ'.
      public boolean irisFuncDeleteq() {
        return irisImsFunction.equals("DELQ");
      }

      public void setIrisFuncDeleteq(boolean value) {
        if (value) irisImsFunction.setValue("DELQ");
      }

      // COB:          03  IRIS-PARAM-NUM               PIC S9(9) COMP.
      public NInt32 irisParamNum = new NInt32();
      // COB:          03  IRIS-SSAS-NUM                PIC S9(9) COMP.
      public NInt32 irisSsasNum = new NInt32();
      // COB: DC       03  IRIS-TASK-NUM REDEFINES IRIS-SSAS-NUM
      public NInt32 irisTaskNum = new NInt32().redefines(irisSsasNum);
      // COB: INQY     03  IRIS-USED-PCB REDEFINES IRIS-SSAS-NUM
      public NInt32 irisUsedPcb = new NInt32().redefines(irisSsasNum);
      // COB:          03 IRIS-TRACE-LEVEL              PIC X.
      public NChar irisTraceLevel = new NChar(1);

      // COB:            88 IRIS-LEVEL-IS-VALID                         VALUE SPACE
      public boolean irisLevelIsValid() {
        return irisTraceLevel.isSpaces()
            || irisTraceLevel.isLowValues()
            || irisTraceLevel.equals("0")
            || irisTraceLevel.equals("1")
            || irisTraceLevel.equals("2")
            || irisTraceLevel.equals("3")
            || irisTraceLevel.equals("4")
            || irisTraceLevel.equals("5");
      }

      public void setIrisLevelIsValid(boolean value) {
        if (value) irisTraceLevel.setValue(SPACE);
      }

      // COB:            88 IRIS-TRACE-NONE                             VALUE SPACE
      public boolean irisTraceNone() {
        return irisTraceLevel.isSpaces() || irisTraceLevel.equals("0");
      }

      public void setIrisTraceNone(boolean value) {
        if (value) irisTraceLevel.setValue(SPACE);
      }

      // COB:            88 IRIS-TRACE-STANDARD                         VALUE '1' '2'
      public boolean irisTraceStandard() {
        return irisTraceLevel.equals("1")
            || irisTraceLevel.equals("2")
            || irisTraceLevel.equals("3");
      }

      public void setIrisTraceStandard(boolean value) {
        if (value) irisTraceLevel.setValue("1");
      }

      // COB:            88 IRIS-TRACE-FULL                             VALUE '2' '3'.
      public boolean irisTraceFull() {
        return irisTraceLevel.equals("2") || irisTraceLevel.equals("3");
      }

      public void setIrisTraceFull(boolean value) {
        if (value) irisTraceLevel.setValue("2");
      }

      // COB:            88 IRIS-TRACE-DEBUG                            VALUE '3'.
      public boolean irisTraceDebug() {
        return irisTraceLevel.equals("3");
      }

      public void setIrisTraceDebug(boolean value) {
        if (value) irisTraceLevel.setValue("3");
      }

      // COB:            88 IRIS-TRACE-PERFORMANCE                      VALUE '4'.
      public boolean irisTracePerformance() {
        return irisTraceLevel.equals("4");
      }

      public void setIrisTracePerformance(boolean value) {
        if (value) irisTraceLevel.setValue("4");
      }

      // COB:            88 IRIS-TRACE-MINIMAL                          VALUE '5'.
      public boolean irisTraceMinimal() {
        return irisTraceLevel.equals("5");
      }

      public void setIrisTraceMinimal(boolean value) {
        if (value) irisTraceLevel.setValue("5");
      }

      // COB:          03 IRIS-MSG-DETAIL.
      public class IrisMsgDetail extends NGroup {
        // COB:            05 IRIS-MSG-LEN                          PIC S9(4) COMP.
        public NInt16 irisMsgLen = new NInt16();
        // COB:            05 IRIS-MSG-TXT                          PIC X(4096).
        public NChar irisMsgTxt = new NChar(4096);
      }

      public IrisMsgDetail irisMsgDetail = new IrisMsgDetail();
      // COB:          03 IRIS-MSG-LEVEL                          PIC 99.
      public NZoned irisMsgLevel = new NZoned(2, false);

      // COB:            88 IRIS-MSG-LEVEL-INFO                   VALUE 00.
      public boolean irisMsgLevelInfo() {
        return irisMsgLevel.equals(0);
      }

      public void setIrisMsgLevelInfo(boolean value) {
        if (value) irisMsgLevel.setValue(0);
      }

      // COB:            88 IRIS-MSG-LEVEL-WARNING                VALUE 04.
      public boolean irisMsgLevelWarning() {
        return irisMsgLevel.equals(4);
      }

      public void setIrisMsgLevelWarning(boolean value) {
        if (value) irisMsgLevel.setValue(4);
      }

      // COB:            88 IRIS-MSG-LEVEL-ERROR                  VALUE 12.
      public boolean irisMsgLevelError() {
        return irisMsgLevel.equals(12);
      }

      public void setIrisMsgLevelError(boolean value) {
        if (value) irisMsgLevel.setValue(12);
      }

      // COB:            88 IRIS-MSG-LEVEL-DEBUG                  VALUE 99.
      public boolean irisMsgLevelDebug() {
        return irisMsgLevel.equals(99);
      }

      public void setIrisMsgLevelDebug(boolean value) {
        if (value) irisMsgLevel.setValue(99);
      }

      // COB:          03 IRIS-ERROR-DESCR.
      public class IrisErrorDescr extends NGroup {
        // COB: <0001>     05 FILLER PIC X(30) VALUE '(IO-RTN) FUNCTION NOT HANDLED_'.
        public NChar filler158 = new NChar(30).initial("(IO-RTN) FUNCTION NOT HANDLED_");
        // COB: <0002>     05 FILLER PIC X(30) VALUE '(IO-RTN) SEGMENT NOT FOUND____'.
        public NChar filler159 = new NChar(30).initial("(IO-RTN) SEGMENT NOT FOUND____");
        // COB: <0003>     05 FILLER PIC X(30) VALUE '(IO-RTN) HANDLED SQL ERROR____'.
        public NChar filler160 = new NChar(30).initial("(IO-RTN) HANDLED SQL ERROR____");
        // COB: <0004>     05 FILLER PIC X(30) VALUE '(IO-RTN) UNHANDLED SQL ERROR__'.
        public NChar filler161 = new NChar(30).initial("(IO-RTN) UNHANDLED SQL ERROR__");
        // COB: <0005>     05 FILLER PIC X(30) VALUE '(IO-RTN) FUNCTION NOT FOUND___'.
        public NChar filler162 = new NChar(30).initial("(IO-RTN) FUNCTION NOT FOUND___");
        // COB: <0006>     05 FILLER PIC X(30) VALUE '(IO-RTN) WRONG BUFFER LENGTH__'.
        public NChar filler163 = new NChar(30).initial("(IO-RTN) WRONG BUFFER LENGTH__");
        // COB: <0007>     05 FILLER PIC X(30) VALUE '(IO-RTN) WRONG ALTERNATE INDEX'.
        public NChar filler164 = new NChar(30).initial("(IO-RTN) WRONG ALTERNATE INDEX");
        // COB: <0008>     05 FILLER PIC X(30) VALUE '(IO-RTN) ACCESS NOT HANDLED___'.
        public NChar filler165 = new NChar(30).initial("(IO-RTN) ACCESS NOT HANDLED___");
        // COB: <0009>     05 FILLER PIC X(30) VALUE '(IO-RTN) DYNAMIC SSA FAILURE__'.
        public NChar filler166 = new NChar(30).initial("(IO-RTN) DYNAMIC SSA FAILURE__");
        // COB:            05 FILLER PIC X(30) OCCURS 11.
        public NChar[] filler167 = AbstractNField.occurs(11, new NChar(30));

        public NChar filler167AtIndex(int index) {
          return getOccursInstance(filler167, index);
        }

        // COB: <0021>     05 FILLER PIC X(30) VALUE '(DYNSSA) UNSUPPORTED FUNCTION_'.
        public NChar filler168 = new NChar(30).initial("(DYNSSA) UNSUPPORTED FUNCTION_");
        // COB: <0022>     05 FILLER PIC X(30) VALUE '(DYNSSA) WRONG SENSEG_________'.
        public NChar filler169 = new NChar(30).initial("(DYNSSA) WRONG SENSEG_________");
        // COB: <0023>     05 FILLER PIC X(30) VALUE '(DYNSSA) WRONG SENSEG POINTER_'.
        public NChar filler170 = new NChar(30).initial("(DYNSSA) WRONG SENSEG POINTER_");
        // COB: <0024>     05 FILLER PIC X(30) VALUE '(DYNSSA) SEGMENT NOT FOUND____'.
        public NChar filler171 = new NChar(30).initial("(DYNSSA) SEGMENT NOT FOUND____");
        // COB: <0025>     05 FILLER PIC X(30) VALUE '(DYNSSA) FIELD NOT FOUND______'.
        public NChar filler172 = new NChar(30).initial("(DYNSSA) FIELD NOT FOUND______");
        // COB: <0026>     05 FILLER PIC X(30) VALUE '(DYNSSA) WRONG BOOL OPERATOR__'.
        public NChar filler173 = new NChar(30).initial("(DYNSSA) WRONG BOOL OPERATOR__");
        // COB: <0027>     05 FILLER PIC X(30) VALUE '(DYNSSA) ACCESS NOT HANDLED___'.
        public NChar filler174 = new NChar(30).initial("(DYNSSA) ACCESS NOT HANDLED___");
        // COB: <0028>     05 FILLER PIC X(30) VALUE '(DYNSSA) CCODE C NOT SUPPORTED'.
        public NChar filler175 = new NChar(30).initial("(DYNSSA) CCODE C NOT SUPPORTED");
        // COB: <0029>     05 FILLER PIC X(30) VALUE '(DYNSSA) PSB NOT FOUND________'.
        public NChar filler176 = new NChar(30).initial("(DYNSSA) PSB NOT FOUND________");
        // COB:            05 FILLER PIC X(30) OCCURS 11.
        public NChar[] filler177 = AbstractNField.occurs(11, new NChar(30));

        public NChar filler177AtIndex(int index) {
          return getOccursInstance(filler177, index);
        }

        // COB:            05 FILLER PIC X(30) OCCURS 59.
        public NChar[] filler178 = AbstractNField.occurs(59, new NChar(30));

        public NChar filler178AtIndex(int index) {
          return getOccursInstance(filler178, index);
        }
      }

      public IrisErrorDescr irisErrorDescr = new IrisErrorDescr();

      // COB:          03 FILLER REDEFINES IRIS-ERROR-DESCR.
      public class Filler179 extends NGroup {
        // COB:            05 IRIS-ERROR-DESCRIPTION PIC X(30) OCCURS 99.
        public NChar[] irisErrorDescription = AbstractNField.occurs(99, new NChar(30));

        public NChar irisErrorDescriptionAtIndex(int index) {
          return getOccursInstance(irisErrorDescription, index);
        }
      }

      public Filler179 filler179 = (Filler179) new Filler179().redefines(irisErrorDescr);
      // COB:          03 IRIS-ERR-MESSAGE-ID                     PIC S9(4) COMP.
      public NInt16 irisErrMessageId = new NInt16();

      // COB:            88 IRIS-NO-ERROR                         VALUE 0 2020 4040.
      public boolean irisNoError() {
        return irisErrMessageId.equals(0)
            || irisErrMessageId.equals(2020)
            || irisErrMessageId.equals(4040);
      }

      public void setIrisNoError(boolean value) {
        if (value) irisErrMessageId.setValue(0);
      }

      // COB:            88 IRIS-ERR-IMSFUNC-NOT-FOUND            VALUE 1.
      public boolean irisErrImsfuncNotFound() {
        return irisErrMessageId.equals(1);
      }

      public void setIrisErrImsfuncNotFound(boolean value) {
        if (value) irisErrMessageId.setValue(1);
      }

      // COB:            88 IRIS-ERR-RTN-SEGMENT-NOT-FOUND        VALUE 2.
      public boolean irisErrRtnSegmentNotFound() {
        return irisErrMessageId.equals(2);
      }

      public void setIrisErrRtnSegmentNotFound(boolean value) {
        if (value) irisErrMessageId.setValue(2);
      }

      // COB:            88 IRIS-ERR-HANDLED-SQLCODE              VALUE 3.
      public boolean irisErrHandledSqlcode() {
        return irisErrMessageId.equals(3);
      }

      public void setIrisErrHandledSqlcode(boolean value) {
        if (value) irisErrMessageId.setValue(3);
      }

      // COB:            88 IRIS-ERR-UNHANDLED-SQLCODE            VALUE 4.
      public boolean irisErrUnhandledSqlcode() {
        return irisErrMessageId.equals(4);
      }

      public void setIrisErrUnhandledSqlcode(boolean value) {
        if (value) irisErrMessageId.setValue(4);
      }

      // COB:            88 IRIS-ERR-FUNCTION-NOT-FOUND           VALUE 5.
      public boolean irisErrFunctionNotFound() {
        return irisErrMessageId.equals(5);
      }

      public void setIrisErrFunctionNotFound(boolean value) {
        if (value) irisErrMessageId.setValue(5);
      }

      // COB:            88 IRIS-ERR-WRONG-BUFFER-LEN             VALUE 6.
      public boolean irisErrWrongBufferLen() {
        return irisErrMessageId.equals(6);
      }

      public void setIrisErrWrongBufferLen(boolean value) {
        if (value) irisErrMessageId.setValue(6);
      }

      // COB:            88 IRIS-ERR-WRONG-ALT-INDEX              VALUE 7.
      public boolean irisErrWrongAltIndex() {
        return irisErrMessageId.equals(7);
      }

      public void setIrisErrWrongAltIndex(boolean value) {
        if (value) irisErrMessageId.setValue(7);
      }

      // COB:            88 IRIS-ERR-RTN-UNHANDLED-ACCESS         VALUE 8.
      public boolean irisErrRtnUnhandledAccess() {
        return irisErrMessageId.equals(8);
      }

      public void setIrisErrRtnUnhandledAccess(boolean value) {
        if (value) irisErrMessageId.setValue(8);
      }

      // COB:            88 IRIS-ERR-DYNAMIC-SSA-FAILURE          VALUE 9.
      public boolean irisErrDynamicSsaFailure() {
        return irisErrMessageId.equals(9);
      }

      public void setIrisErrDynamicSsaFailure(boolean value) {
        if (value) irisErrMessageId.setValue(9);
      }

      // COB:            88 IRIS-ERR-UNSUPPORTED-FUNC             VALUE 21.
      public boolean irisErrUnsupportedFunc() {
        return irisErrMessageId.equals(21);
      }

      public void setIrisErrUnsupportedFunc(boolean value) {
        if (value) irisErrMessageId.setValue(21);
      }

      // COB:            88 IRIS-ERR-WRONG-SENSEG                 VALUE 22.
      public boolean irisErrWrongSenseg() {
        return irisErrMessageId.equals(22);
      }

      public void setIrisErrWrongSenseg(boolean value) {
        if (value) irisErrMessageId.setValue(22);
      }

      // COB:            88 IRIS-ERR-WRONG-SENSEG-PTR             VALUE 23.
      public boolean irisErrWrongSensegPtr() {
        return irisErrMessageId.equals(23);
      }

      public void setIrisErrWrongSensegPtr(boolean value) {
        if (value) irisErrMessageId.setValue(23);
      }

      // COB:            88 IRIS-ERR-SSA-SEGMENT-NOT-FOUND        VALUE 24.
      public boolean irisErrSsaSegmentNotFound() {
        return irisErrMessageId.equals(24);
      }

      public void setIrisErrSsaSegmentNotFound(boolean value) {
        if (value) irisErrMessageId.setValue(24);
      }

      // COB:            88 IRIS-ERR-FIELD-NOT-FOUND              VALUE 25.
      public boolean irisErrFieldNotFound() {
        return irisErrMessageId.equals(25);
      }

      public void setIrisErrFieldNotFound(boolean value) {
        if (value) irisErrMessageId.setValue(25);
      }

      // COB:            88 IRIS-ERR-WRONG-BOOLEAN-OP             VALUE 26.
      public boolean irisErrWrongBooleanOp() {
        return irisErrMessageId.equals(26);
      }

      public void setIrisErrWrongBooleanOp(boolean value) {
        if (value) irisErrMessageId.setValue(26);
      }

      // COB:            88 IRIS-ERR-SSA-UNHANDLED-ACCESS         VALUE 27.
      public boolean irisErrSsaUnhandledAccess() {
        return irisErrMessageId.equals(27);
      }

      public void setIrisErrSsaUnhandledAccess(boolean value) {
        if (value) irisErrMessageId.setValue(27);
      }

      // COB:            88 IRIS-ERR-UNSUPPORTED-C-COMMAND        VALUE 28.
      public boolean irisErrUnsupportedCCommand() {
        return irisErrMessageId.equals(28);
      }

      public void setIrisErrUnsupportedCCommand(boolean value) {
        if (value) irisErrMessageId.setValue(28);
      }

      // COB:            88 IRIS-ERR-PSB-NOT-FOUND                VALUE 29.
      public boolean irisErrPsbNotFound() {
        return irisErrMessageId.equals(29);
      }

      public void setIrisErrPsbNotFound(boolean value) {
        if (value) irisErrMessageId.setValue(29);
      }

      // COB:          03 IRIS-SQL-ERROR.
      public class IrisSqlError extends NGroup {
        // COB:            05  IRIS-SQLCODE                         PIC S9(9) COMP.
        public NInt32 irisSqlcode = new NInt32();

        // COB:            05  IRIS-SQLERRM.
        public class IrisSqlerrm extends NGroup {
          // COB:                07 IRIS-SQLERRML                     PIC S9(4) COMP-5.
          public NInt16 irisSqlerrml = new NInt16();
          // COB:                07 IRIS-SQLERRMC                     PIC X(70).
          public NChar irisSqlerrmc = new NChar(70);
        }

        public IrisSqlerrm irisSqlerrm = new IrisSqlerrm();
      }

      public IrisSqlError irisSqlError = new IrisSqlError();
      // COB:          03 IRIS-IMS-SEGM-LVL                       PIC 99.
      public NZoned irisImsSegmLvl = new NZoned(2, false);

      // COB:            88 IRIS-IMS-SEGM-LVL-DUMMY                     VALUE 00.
      public boolean irisImsSegmLvlDummy() {
        return irisImsSegmLvl.equals(0);
      }

      public void setIrisImsSegmLvlDummy(boolean value) {
        if (value) irisImsSegmLvl.setValue(0);
      }

      // COB:            88 IRIS-IMS-SEGM-LVL-DATABASE                  VALUE 1 .
      public boolean irisImsSegmLvlDatabase() {
        return irisImsSegmLvl.equals(1);
      }

      public void setIrisImsSegmLvlDatabase(boolean value) {
        if (value) irisImsSegmLvl.setValue(1);
      }

      // COB:            88 IRIS-IMS-SEGM-LVL-GSAM                      VALUE 55.
      public boolean irisImsSegmLvlGsam() {
        return irisImsSegmLvl.equals(55);
      }

      public void setIrisImsSegmLvlGsam(boolean value) {
        if (value) irisImsSegmLvl.setValue(55);
      }

      // COB:            88 IRIS-IMS-SEGM-LVL-VIDEO                     VALUE 98.
      public boolean irisImsSegmLvlVideo() {
        return irisImsSegmLvl.equals(98);
      }

      public void setIrisImsSegmLvlVideo(boolean value) {
        if (value) irisImsSegmLvl.setValue(98);
      }

      // COB:            88 IRIS-IMS-SEGM-LVL-PRINTER                   VALUE 99.
      public boolean irisImsSegmLvlPrinter() {
        return irisImsSegmLvl.equals(99);
      }

      public void setIrisImsSegmLvlPrinter(boolean value) {
        if (value) irisImsSegmLvl.setValue(99);
      }

      // COB:          03 IRIS-CURRENT-STATUS                     PIC 9 VALUE 0.
      public NZoned irisCurrentStatus = new NZoned(1, false);

      // COB:            88 IRIS-INTERMEDIATE                           VALUE 0.
      public boolean irisIntermediate() {
        return irisCurrentStatus.equals(0);
      }

      public void setIrisIntermediate(boolean value) {
        if (value) irisCurrentStatus.setValue(0);
      }

      // COB:            88 IRIS-FINAL                                  VALUE 1.
      public boolean irisFinal() {
        return irisCurrentStatus.equals(1);
      }

      public void setIrisFinal(boolean value) {
        if (value) irisCurrentStatus.setValue(1);
      }

      // COB:          03 IRIS-KEYVALUE-TAB.
      public class IrisKeyvalueTab extends NGroup {
        // COB:            05 IRIS-CCODE-LEVELS           OCCURS 16.
        public class IrisCcodeLevels extends NGroup {
          // COB:              07 IRIS-CCODE-VALUES.
          public class IrisCcodeValues extends NGroup {
            // COB:               09 IRIS-CODE-A              PIC X.
            public NChar irisCodeA = new NChar(1);

            // COB:                 88 IRIS-CODE-POS-RESET                    VALUE 'A'.
            public boolean irisCodePosReset() {
              return irisCodeA.equals("A");
            }

            public void setIrisCodePosReset(boolean value) {
              if (value) irisCodeA.setValue("A");
            }

            // COB:               09 IRIS-CODE-C              PIC X.
            public NChar irisCodeC = new NChar(1);

            // COB:                 88 IRIS-CODE-CONCAT-KEY                   VALUE 'C'.
            public boolean irisCodeConcatKey() {
              return irisCodeC.equals("C");
            }

            public void setIrisCodeConcatKey(boolean value) {
              if (value) irisCodeC.setValue("C");
            }

            // COB:               09 IRIS-CODE-D              PIC X.
            public NChar irisCodeD = new NChar(1);

            // COB:                 88 IRIS-CODE-PATHCALL                     VALUE 'D'.
            public boolean irisCodePathcall() {
              return irisCodeD.equals("D");
            }

            public void setIrisCodePathcall(boolean value) {
              if (value) irisCodeD.setValue("D");
            }

            // COB:               09 IRIS-CODE-F              PIC X.
            public NChar irisCodeF = new NChar(1);

            // COB:                 88 IRIS-CODE-FIRST                        VALUE 'F'.
            public boolean irisCodeFirst() {
              return irisCodeF.equals("F");
            }

            public void setIrisCodeFirst(boolean value) {
              if (value) irisCodeF.setValue("F");
            }

            // COB:               09 IRIS-CODE-L              PIC X.
            public NChar irisCodeL = new NChar(1);

            // COB:                 88 IRIS-CODE-LAST                         VALUE 'L'.
            public boolean irisCodeLast() {
              return irisCodeL.equals("L");
            }

            public void setIrisCodeLast(boolean value) {
              if (value) irisCodeL.setValue("L");
            }

            // COB:               09 IRIS-CODE-M              PIC X.
            public NChar irisCodeM = new NChar(1);

            // COB:                 88 IRIS-CODE-PTRNEXT                      VALUE 'M'.
            public boolean irisCodePtrnext() {
              return irisCodeM.equals("M");
            }

            public void setIrisCodePtrnext(boolean value) {
              if (value) irisCodeM.setValue("M");
            }

            // COB:               09 IRIS-CODE-N              PIC X.
            public NChar irisCodeN = new NChar(1);

            // COB:                 88 IRIS-CODE-NOREPL                       VALUE 'N'.
            public boolean irisCodeNorepl() {
              return irisCodeN.equals("N");
            }

            public void setIrisCodeNorepl(boolean value) {
              if (value) irisCodeN.setValue("N");
            }

            // COB:               09 IRIS-CODE-P              PIC X.
            public NChar irisCodeP = new NChar(1);

            // COB:                 88 IRIS-CODE-PARENTAGE                    VALUE 'P'.
            public boolean irisCodeParentage() {
              return irisCodeP.equals("P");
            }

            public void setIrisCodeParentage(boolean value) {
              if (value) irisCodeP.setValue("P");
            }

            // COB:               09 IRIS-CODE-Q              PIC X.
            public NChar irisCodeQ = new NChar(1);

            // COB:                 88 IRIS-CODE-HOLDSEG                      VALUE 'Q'.
            public boolean irisCodeHoldseg() {
              return irisCodeQ.equals("Q");
            }

            public void setIrisCodeHoldseg(boolean value) {
              if (value) irisCodeQ.setValue("Q");
            }

            // COB:               09 IRIS-CODE-R              PIC X.
            public NChar irisCodeR = new NChar(1);

            // COB:                 88 IRIS-CODE-FIRSTSEG                     VALUE 'R'.
            public boolean irisCodeFirstseg() {
              return irisCodeR.equals("R");
            }

            public void setIrisCodeFirstseg(boolean value) {
              if (value) irisCodeR.setValue("R");
            }

            // COB:               09 IRIS-CODE-S              PIC X.
            public NChar irisCodeS = new NChar(1);

            // COB:                 88 IRIS-CODE-PTRCURR                      VALUE 'S'.
            public boolean irisCodePtrcurr() {
              return irisCodeS.equals("S");
            }

            public void setIrisCodePtrcurr(boolean value) {
              if (value) irisCodeS.setValue("S");
            }

            // COB:               09 IRIS-CODE-U              PIC X.
            public NChar irisCodeU = new NChar(1);

            // COB:                 88 IRIS-CODE-LIMSRCH                      VALUE 'U'.
            public boolean irisCodeLimsrch() {
              return irisCodeU.equals("U");
            }

            public void setIrisCodeLimsrch(boolean value) {
              if (value) irisCodeU.setValue("U");
            }

            // COB:               09 IRIS-CODE-V              PIC X.
            public NChar irisCodeV = new NChar(1);

            // COB:                 88 IRIS-CODE-HIERQUAL                     VALUE 'V'.
            public boolean irisCodeHierqual() {
              return irisCodeV.equals("V");
            }

            public void setIrisCodeHierqual(boolean value) {
              if (value) irisCodeV.setValue("V");
            }

            // COB:               09 IRIS-CODE-W              PIC X.
            public NChar irisCodeW = new NChar(1);

            // COB:                 88 IRIS-CODE-PTRCURR2                     VALUE 'W'.
            public boolean irisCodePtrcurr2() {
              return irisCodeW.equals("W");
            }

            public void setIrisCodePtrcurr2(boolean value) {
              if (value) irisCodeW.setValue("W");
            }

            // COB:               09 IRIS-CODE-Z              PIC X.
            public NChar irisCodeZ = new NChar(1);

            // COB:                 88 IRIS-CODE-PTRRES                       VALUE 'Z'.
            public boolean irisCodePtrres() {
              return irisCodeZ.equals("Z");
            }

            public void setIrisCodePtrres(boolean value) {
              if (value) irisCodeZ.setValue("Z");
            }
          }

          public IrisCcodeValues irisCcodeValues = new IrisCcodeValues();
        }

        public IrisCcodeLevels[] irisCcodeLevels = AbstractNField.occurs(16, new IrisCcodeLevels());

        public IrisCcodeLevels irisCcodeLevelsAtIndex(int index) {
          return getOccursInstance(irisCcodeLevels, index);
        }

        // COB:            05 IRIS-KEYS-LEVELS            OCCURS 4.
        public class IrisKeysLevels extends NGroup {
          // COB: SAN          07 IRIS-KEYVALUES-TAB        PIC X(8192).
          public NChar irisKeyvaluesTab = new NChar(8192);

          // COB: SAN          07 IRIS-KEYVALUES-RED1 REDEFINES IRIS-KEYVALUES-TAB.
          public class IrisKeyvaluesRed1 extends NGroup {
            // COB: SAN            09 IRIS-KEYVALUES          OCCURS 64.
            public class IrisKeyvalues extends NGroup {
              // COB: SAN              11 IRIS-KEYVALUE         PIC X(128).
              public NChar irisKeyvalue = new NChar(128);
            }

            public IrisKeyvalues[] irisKeyvalues = AbstractNField.occurs(64, new IrisKeyvalues());

            public IrisKeyvalues irisKeyvaluesAtIndex(int index) {
              return getOccursInstance(irisKeyvalues, index);
            }
          }

          public IrisKeyvaluesRed1 irisKeyvaluesRed1 =
              (IrisKeyvaluesRed1) new IrisKeyvaluesRed1().redefines(irisKeyvaluesTab);

          // COB: SAN          07 IRIS-KEYVALUES-RED2 REDEFINES IRIS-KEYVALUES-TAB.
          public class IrisKeyvaluesRed2 extends NGroup {
            // COB: SAN            09 IRIS-KEYVALUES-P        OCCURS 64.
            public class IrisKeyvaluesP extends NGroup {
              // COB: SAN              11 IRIS-KEYVALUE-PACKED  PIC S9(18) COMP-3.
              public NPacked irisKeyvaluePacked = new NPacked(10);
              // COB: SAN              11 FILLER                PIC X(118).
              public NChar filler282 = new NChar(118);
            }

            public IrisKeyvaluesP[] irisKeyvaluesP =
                AbstractNField.occurs(64, new IrisKeyvaluesP());

            public IrisKeyvaluesP irisKeyvaluesPAtIndex(int index) {
              return getOccursInstance(irisKeyvaluesP, index);
            }
          }

          public IrisKeyvaluesRed2 irisKeyvaluesRed2 =
              (IrisKeyvaluesRed2) new IrisKeyvaluesRed2().redefines(irisKeyvaluesTab);
        }

        public IrisKeysLevels[] irisKeysLevels = AbstractNField.occurs(4, new IrisKeysLevels());

        public IrisKeysLevels irisKeysLevelsAtIndex(int index) {
          return getOccursInstance(irisKeysLevels, index);
        }
      }

      public IrisKeyvalueTab irisKeyvalueTab = new IrisKeyvalueTab();
      // COB:          03 PARENT-SEGMENT-INDEX          PIC S9(9) COMP.
      public NInt32 parentSegmentIndex = new NInt32();
      // COB:          03 LAST-SEGMENT-INDEX            PIC S9(9) COMP.
      public NInt32 lastSegmentIndex = new NInt32();
      // COB:          03 KEY-STATUS                    PIC 9.
      public NZoned keyStatus = new NZoned(1, false);

      // COB:            88 IS-NOT-LAST-SSA-KEY                         VALUE 0.
      public boolean isNotLastSsaKey() {
        return keyStatus.equals(0);
      }

      public void setIsNotLastSsaKey(boolean value) {
        if (value) keyStatus.setValue(0);
      }

      // COB:            88 IS-LAST-SSA-KEY                             VALUE 1.
      public boolean isLastSsaKey() {
        return keyStatus.equals(1);
      }

      public void setIsLastSsaKey(boolean value) {
        if (value) keyStatus.setValue(1);
      }

      // COB:          03 IRIS-IMS-API                  PIC X(8) VALUE 'CBLTDLI'.
      public NChar irisImsApi = new NChar(8);

      // COB:            88 IRIS-CBLTDLI                         VALUE 'CBLTDLI'.
      public boolean irisCbltdli() {
        return irisImsApi.equals("CBLTDLI");
      }

      public void setIrisCbltdli(boolean value) {
        if (value) irisImsApi.setValue("CBLTDLI");
      }

      // COB:            88 IRIS-AERTDLI                         VALUE 'AERTDLI'.
      public boolean irisAertdli() {
        return irisImsApi.equals("AERTDLI");
      }

      public void setIrisAertdli(boolean value) {
        if (value) irisImsApi.setValue("AERTDLI");
      }

      // COB:            88 IRIS-EXECDLI                         VALUE 'EXECDLI'.
      public boolean irisExecdli() {
        return irisImsApi.equals("EXECDLI");
      }

      public void setIrisExecdli(boolean value) {
        if (value) irisImsApi.setValue("EXECDLI");
      }

      // COB:          03 IRIS-AUDIT-FIELDS.
      public class IrisAuditFields extends NGroup {
        // COB:            05 IRIS-CREATION-USER          PIC X(25).
        public NChar irisCreationUser = new NChar(25);
        // COB:            05 IRIS-LAST-UPDATE-USER       PIC X(25).
        public NChar irisLastUpdateUser = new NChar(25);
        // COB:            05 IRIS-CREATION-TRAN          PIC X(25).
        public NChar irisCreationTran = new NChar(25);
        // COB:            05 IRIS-LAST-UPDATE-TRAN       PIC X(25).
        public NChar irisLastUpdateTran = new NChar(25);
      }

      public IrisAuditFields irisAuditFields = new IrisAuditFields();
      // COB:          03 IRIS-AIB-ADDRESS              PIC S9(9) COMP.
      public NInt32 irisAibAddress = new NInt32();
      // COB:          03 IRIS-AIB-POINTER REDEFINES IRIS-AIB-ADDRESS POINTER.
      public NPointer irisAibPointer = new NPointer().redefines(irisAibAddress);
      // COB: IMSCD    03 IRIS-DFH-POINTER REDEFINES IRIS-AIB-ADDRESS POINTER.
      public NPointer irisDfhPointer = new NPointer().redefines(irisAibAddress);
      // COB:          03 GE-PATH-CALL                  PIC X    VALUE '1'.
      public NChar gePathCall = new NChar(1);

      // COB:            88 EXEC-GE-PATH-CALL                    VALUE '0'.
      public boolean execGePathCall() {
        return gePathCall.equals("0");
      }

      public void setExecGePathCall(boolean value) {
        if (value) gePathCall.setValue("0");
      }

      // COB:            88 SKIP-GE-PATH-CALL                    VALUE '1'.
      public boolean skipGePathCall() {
        return gePathCall.equals("1");
      }

      public void setSkipGePathCall(boolean value) {
        if (value) gePathCall.setValue("1");
      }

      // COB:          03 IRIS-AIB-PCBS-COUNT           PIC S9(4) COMP VALUE 0.
      public NInt16 irisAibPcbsCount = new NInt16().initial(0);

      // COB:          03 IRIS-CHKP-FIELDS.
      public class IrisChkpFields extends NGroup {
        // COB:            05 IRIS-GSAM-JOB-NAME          PIC X(8).
        public NChar irisGsamJobName = new NChar(8);
        // COB:            05 IRIS-GSAM-PROGRAM-NAME      PIC X(8).
        public NChar irisGsamProgramName = new NChar(8);
        // COB:            05 IRIS-GSAM-PSB-NAME          PIC X(8).
        public NChar irisGsamPsbName = new NChar(8);
        // COB:            05 IRIS-GSAM-PCB-NUM           PIC S9(3) COMP-3.
        public NPacked irisGsamPcbNum = new NPacked(2);
        // COB:            05 IRIS-GSAM-CHECKPOINT-ID     PIC X(14).
        public NChar irisGsamCheckpointId = new NChar(14);
        // COB:            05 IRIS-GSAM-CHECKPOINT-TIME   PIC X(16).
        public NChar irisGsamCheckpointTime = new NChar(16);
      }

      public IrisChkpFields irisChkpFields = new IrisChkpFields();
      // COB:          03 IRIS-GSAM-LRECL               PIC 9(8).
      public NZoned irisGsamLrecl = new NZoned(8, false);
      // COB:          03 IRIS-PCB-NUM                  PIC 9(4).
      public NZoned irisPcbNum = new NZoned(4, false);
      // COB:          03 IRIS-EXEC-DLI-PTR             POINTER.
      public NPointer irisExecDliPtr = new NPointer();
      // COB:          03 IRIS-EXEC-DLI-ADDR REDEFINES IRIS-EXEC-DLI-PTR
      public NInt32 irisExecDliAddr = new NInt32().redefines(irisExecDliPtr);
      // COB:          03 FILLER                        PIC 9 VALUE 0.
      public NZoned filler348 = new NZoned(1, false);

      // COB:            88 IRIS-DYNSSA-EXECUTE               VALUE 0.
      public boolean irisDynssaExecute() {
        return filler348.equals(0);
      }

      public void setIrisDynssaExecute(boolean value) {
        if (value) filler348.setValue(0);
      }

      // COB:            88 IRIS-DYNSSA-PARSEONLY             VALUE 1.
      public boolean irisDynssaParseonly() {
        return filler348.equals(1);
      }

      public void setIrisDynssaParseonly(boolean value) {
        if (value) filler348.setValue(1);
      }

      // COB:          03 IRIS-PCB-TYPE-TABLE.
      public class IrisPcbTypeTable extends NGroup {
        // COB:            05 FILLER                      OCCURS 100.
        public class Filler355 extends NGroup {
          // COB:              07 IRIS-PCB-TYPE             PIC X.
          public NChar irisPcbType = new NChar(1);

          // COB:              88 IRIS-PCB-TYPE-TP          VALUE 'T'.
          public boolean irisPcbTypeTp() {
            return irisPcbType.equals("T");
          }

          public void setIrisPcbTypeTp(boolean value) {
            if (value) irisPcbType.setValue("T");
          }

          // COB:              88 IRIS-PCB-TYPE-DB          VALUE 'D'.
          public boolean irisPcbTypeDb() {
            return irisPcbType.equals("D");
          }

          public void setIrisPcbTypeDb(boolean value) {
            if (value) irisPcbType.setValue("D");
          }

          // COB:              88 IRIS-PCB-TYPE-GSAM        VALUE 'G'.
          public boolean irisPcbTypeGsam() {
            return irisPcbType.equals("G");
          }

          public void setIrisPcbTypeGsam(boolean value) {
            if (value) irisPcbType.setValue("G");
          }
        }

        public Filler355[] filler355 = AbstractNField.occurs(100, new Filler355());

        public Filler355 filler355AtIndex(int index) {
          return getOccursInstance(filler355, index);
        }
      }

      public IrisPcbTypeTable irisPcbTypeTable = new IrisPcbTypeTable();
      // COB:          03 IRIS-PROGRAM-TYPE             PIC X VALUE 'B'.
      public NChar irisProgramType = new NChar(1);

      // COB:            88 IRIS-BATCH                  VALUE 'B'.
      public boolean irisBatch() {
        return irisProgramType.equals("B");
      }

      public void setIrisBatch(boolean value) {
        if (value) irisProgramType.setValue("B");
      }

      // COB:            88 IRIS-ONLINE                 VALUE 'T'.
      public boolean irisOnline() {
        return irisProgramType.equals("T");
      }

      public void setIrisOnline(boolean value) {
        if (value) irisProgramType.setValue("T");
      }

      // COB:          03 IRIS-KFB                      PIC 9 VALUE 0.
      public NZoned irisKfb = new NZoned(1, false);

      // COB:            88 IRIS-KFB-NO                 VALUE 0.
      public boolean irisKfbNo() {
        return irisKfb.equals(0);
      }

      public void setIrisKfbNo(boolean value) {
        if (value) irisKfb.setValue(0);
      }

      // COB:            88 IRIS-KFB-YES                VALUE 1.
      public boolean irisKfbYes() {
        return irisKfb.equals(1);
      }

      public void setIrisKfbYes(boolean value) {
        if (value) irisKfb.setValue(1);
      }

      // COB:          03 IRIS-PAIRED-ACCESS            PIC 9 VALUE 0.
      public NZoned irisPairedAccess = new NZoned(1, false);

      // COB:            88 IRIS-PAIRED-NO              VALUE 0.
      public boolean irisPairedNo() {
        return irisPairedAccess.equals(0);
      }

      public void setIrisPairedNo(boolean value) {
        if (value) irisPairedAccess.setValue(0);
      }

      // COB:            88 IRIS-PAIRED-YES             VALUE 1.
      public boolean irisPairedYes() {
        return irisPairedAccess.equals(1);
      }

      public void setIrisPairedYes(boolean value) {
        if (value) irisPairedAccess.setValue(1);
      }

      // COB:          03 IRISGLOB-DIBSTAT              PIC X(2).
      public NChar irisglobDibstat = new NChar(2);
      // COB:          03 IRIS-KFB-LENGTH               PIC 9(8) VALUE ZERO.
      public NZoned irisKfbLength = new NZoned(8, false).initial(0);

      // COB:          03 IRIS-PCB-DBD-TABLE.
      public class IrisPcbDbdTable extends NGroup {
        // COB:            05 FILLER                      OCCURS 100.
        public class Filler390 extends NGroup {
          // COB:              07 IRIS-PCB-DBD              PIC X(8).
          public NChar irisPcbDbd = new NChar(8);
        }

        public Filler390[] filler390 = AbstractNField.occurs(100, new Filler390());

        public Filler390 filler390AtIndex(int index) {
          return getOccursInstance(filler390, index);
        }
      }

      public IrisPcbDbdTable irisPcbDbdTable = new IrisPcbDbdTable();
      // COB:          03 IRIS-MAIN-LANG                PIC X(3).
      public NChar irisMainLang = new NChar(3);

      // COB:            88 IRIS-MAIN-CBL               VALUE 'CBL'.
      public boolean irisMainCbl() {
        return irisMainLang.equals("CBL");
      }

      public void setIrisMainCbl(boolean value) {
        if (value) irisMainLang.setValue("CBL");
      }

      // COB:            88 IRIS-MAIN-PLI               VALUE 'PLI'.
      public boolean irisMainPli() {
        return irisMainLang.equals("PLI");
      }

      public void setIrisMainPli(boolean value) {
        if (value) irisMainLang.setValue("PLI");
      }

      // COB:            88 IRIS-MAIN-EZT               VALUE 'EZT'.
      public boolean irisMainEzt() {
        return irisMainLang.equals("EZT");
      }

      public void setIrisMainEzt(boolean value) {
        if (value) irisMainLang.setValue("EZT");
      }

      // COB:            88 IRIS-MAIN-ASM               VALUE 'ASM'.
      public boolean irisMainAsm() {
        return irisMainLang.equals("ASM");
      }

      public void setIrisMainAsm(boolean value) {
        if (value) irisMainLang.setValue("ASM");
      }

      // COB:          03 FILLER                        PIC X(659).
      public NChar filler404 = new NChar(659);
    }

    // COB: 	   01  IRIS-PCB-PTR-1X     PIC X(32000).
    public NChar irisPcbPtr1x = new NChar(32000);
    // COB: 	   01  IRIS-PCB-PTR-1      REDEFINES IRIS-PCB-PTR-1X POINTER.
    public NPointer irisPcbPtr1 = new NPointer().redefines(irisPcbPtr1x);
    // COB:        01  IRIS-PCB-CHKP       REDEFINES IRIS-PCB-PTR-1X PIC X(78).
    public NChar irisPcbChkp = new NChar(78).redefines(irisPcbPtr1x);
    // COB:        01  IRIS-PCB-AIB        REDEFINES IRIS-PCB-PTR-1X PIC X.
    public NChar irisPcbAib = new NChar(1).redefines(irisPcbPtr1x);
    // COB:        01  IRIS-LK-DIB-BLOCK   REDEFINES IRIS-PCB-PTR-1X PIC X(32).
    public NChar irisLkDibBlock = new NChar(32).redefines(irisPcbPtr1x);
    // COB: 	   01  IRIS-PCB-PTR-2X     PIC X(32000).
    public NChar irisPcbPtr2x = new NChar(32000);
    // COB: 	   01  IRIS-PCB-PTR-2      REDEFINES IRIS-PCB-PTR-2X POINTER.
    public NPointer irisPcbPtr2 = new NPointer().redefines(irisPcbPtr2x);
    // COB:        01  IRIS-LK-PSB-NAME    REDEFINES IRIS-PCB-PTR-2X PIC X(8).
    public NChar irisLkPsbName = new NChar(8).redefines(irisPcbPtr2x);
    // COB:        01  IRIS-LK-BLL-POINTER REDEFINES IRIS-PCB-PTR-2X POINTER.
    public NPointer irisLkBllPointer = new NPointer().redefines(irisPcbPtr2x);
    // COB:        01  IRIS-LK-BLL-ADDR    REDEFINES IRIS-PCB-PTR-2X PIC S9(9) COMP.
    public NInt32 irisLkBllAddr = new NInt32().redefines(irisPcbPtr2x);
    // COB:        01  IRIS-CHKP-LENGTH    REDEFINES IRIS-PCB-PTR-2X PIC S9(9) COMP.
    public NInt32 irisChkpLength = new NInt32().redefines(irisPcbPtr2x);
    // COB:        01  IRIS-PCB-PTR-3      POINTER.
    public NPointer irisPcbPtr3 = new NPointer();
    // COB:        01  IRIS-CHKP-ID        REDEFINES IRIS-PCB-PTR-3 PIC X(14).
    public NChar irisChkpId = new NChar(14).redefines(irisPcbPtr3);
    // COB:        01  IRIS-PCB-PTR-4      POINTER.
    public NPointer irisPcbPtr4 = new NPointer();
    // COB:        01  IRIS-CHKP-AREA1-LEN REDEFINES IRIS-PCB-PTR-4 PIC S9(9) COMP.
    public NInt32 irisChkpArea1Len = new NInt32().redefines(irisPcbPtr4);
    // COB:        01  IRIS-CHKP-AREA1-LX  REDEFINES IRIS-PCB-PTR-4 PIC X(4).
    public NChar irisChkpArea1Lx = new NChar(4).redefines(irisPcbPtr4);
    // COB:        01  IRIS-PCB-PTR-5      POINTER.
    public NPointer irisPcbPtr5 = new NPointer();
    // COB:        01  IRIS-CHKP-AREA1     REDEFINES IRIS-PCB-PTR-5 PIC X.
    public NChar irisChkpArea1 = new NChar(1).redefines(irisPcbPtr5);
    // COB:        01  IRIS-PCB-PTR-6      POINTER.
    public NPointer irisPcbPtr6 = new NPointer();
    // COB:        01  IRIS-CHKP-AREA2-LEN REDEFINES IRIS-PCB-PTR-6 PIC S9(9) COMP.
    public NInt32 irisChkpArea2Len = new NInt32().redefines(irisPcbPtr6);
    // COB:        01  IRIS-CHKP-AREA2-LX  REDEFINES IRIS-PCB-PTR-6 PIC X(4).
    public NChar irisChkpArea2Lx = new NChar(4).redefines(irisPcbPtr6);
    // COB:        01  IRIS-PCB-PTR-7      POINTER.
    public NPointer irisPcbPtr7 = new NPointer();
    // COB:        01  IRIS-CHKP-AREA2     REDEFINES IRIS-PCB-PTR-7 PIC X.
    public NChar irisChkpArea2 = new NChar(1).redefines(irisPcbPtr7);
    // COB:        01  IRIS-PCB-PTR-8      POINTER.
    public NPointer irisPcbPtr8 = new NPointer();
    // COB:        01  IRIS-CHKP-AREA3-LEN REDEFINES IRIS-PCB-PTR-8 PIC S9(9) COMP.
    public NInt32 irisChkpArea3Len = new NInt32().redefines(irisPcbPtr8);
    // COB:        01  IRIS-CHKP-AREA3-LX  REDEFINES IRIS-PCB-PTR-8 PIC X(4).
    public NChar irisChkpArea3Lx = new NChar(4).redefines(irisPcbPtr8);
    // COB:        01  IRIS-PCB-PTR-9      POINTER.
    public NPointer irisPcbPtr9 = new NPointer();
    // COB:        01  IRIS-CHKP-AREA3     REDEFINES IRIS-PCB-PTR-9 PIC X.
    public NChar irisChkpArea3 = new NChar(1).redefines(irisPcbPtr9);
    // COB:        01  IRIS-PCB-PTR-10     POINTER.
    public NPointer irisPcbPtr10 = new NPointer();
    // COB:        01  IRIS-CHKP-AREA4-LEN REDEFINES IRIS-PCB-PTR-10 PIC S9(9) COMP.
    public NInt32 irisChkpArea4Len = new NInt32().redefines(irisPcbPtr10);
    // COB:        01  IRIS-CHKP-AREA4-LX  REDEFINES IRIS-PCB-PTR-10 PIC X(4).
    public NChar irisChkpArea4Lx = new NChar(4).redefines(irisPcbPtr10);
    // COB:        01  IRIS-PCB-PTR-11     POINTER.
    public NPointer irisPcbPtr11 = new NPointer();
    // COB:        01  IRIS-CHKP-AREA4     REDEFINES IRIS-PCB-PTR-11 PIC X.
    public NChar irisChkpArea4 = new NChar(1).redefines(irisPcbPtr11);
    // COB:        01  IRIS-PCB-PTR-12     POINTER.
    public NPointer irisPcbPtr12 = new NPointer();
    // COB:        01  IRIS-CHKP-AREA5-LEN REDEFINES IRIS-PCB-PTR-12 PIC S9(9) COMP.
    public NInt32 irisChkpArea5Len = new NInt32().redefines(irisPcbPtr12);
    // COB:        01  IRIS-CHKP-AREA5-LX  REDEFINES IRIS-PCB-PTR-12 PIC X(4).
    public NChar irisChkpArea5Lx = new NChar(4).redefines(irisPcbPtr12);
    // COB:        01  IRIS-PCB-PTR-13     POINTER.
    public NPointer irisPcbPtr13 = new NPointer();
    // COB:        01  IRIS-CHKP-AREA5     REDEFINES IRIS-PCB-PTR-13 PIC X.
    public NChar irisChkpArea5 = new NChar(1).redefines(irisPcbPtr13);
    // COB:        01  IRIS-PCB-PTR-14     POINTER.
    public NPointer irisPcbPtr14 = new NPointer();
    // COB:        01  IRIS-CHKP-AREA6-LEN REDEFINES IRIS-PCB-PTR-14 PIC S9(9) COMP.
    public NInt32 irisChkpArea6Len = new NInt32().redefines(irisPcbPtr14);
    // COB:        01  IRIS-CHKP-AREA6-LX  REDEFINES IRIS-PCB-PTR-14 PIC X(4).
    public NChar irisChkpArea6Lx = new NChar(4).redefines(irisPcbPtr14);
    // COB:        01  IRIS-PCB-PTR-15     POINTER.
    public NPointer irisPcbPtr15 = new NPointer();
    // COB:        01  IRIS-CHKP-AREA6     REDEFINES IRIS-PCB-PTR-15 PIC X.
    public NChar irisChkpArea6 = new NChar(1).redefines(irisPcbPtr15);
    // COB:        01  IRIS-PCB-PTR-16     POINTER.
    public NPointer irisPcbPtr16 = new NPointer();
    // COB:        01  IRIS-CHKP-AREA7-LEN REDEFINES IRIS-PCB-PTR-16 PIC S9(9) COMP.
    public NInt32 irisChkpArea7Len = new NInt32().redefines(irisPcbPtr16);
    // COB:        01  IRIS-CHKP-AREA7-LX  REDEFINES IRIS-PCB-PTR-16 PIC X(4).
    public NChar irisChkpArea7Lx = new NChar(4).redefines(irisPcbPtr16);
    // COB:        01  IRIS-PCB-PTR-17     POINTER.
    public NPointer irisPcbPtr17 = new NPointer();
    // COB:        01  IRIS-CHKP-AREA7     REDEFINES IRIS-PCB-PTR-17 PIC X.
    public NChar irisChkpArea7 = new NChar(1).redefines(irisPcbPtr17);
    // COB:        01  IRIS-PCB-PTR-18     POINTER.
    public NPointer irisPcbPtr18 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-19     POINTER.
    public NPointer irisPcbPtr19 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-20     POINTER.
    public NPointer irisPcbPtr20 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-21     POINTER.
    public NPointer irisPcbPtr21 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-22     POINTER.
    public NPointer irisPcbPtr22 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-23     POINTER.
    public NPointer irisPcbPtr23 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-24     POINTER.
    public NPointer irisPcbPtr24 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-25     POINTER.
    public NPointer irisPcbPtr25 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-26     POINTER.
    public NPointer irisPcbPtr26 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-27     POINTER.
    public NPointer irisPcbPtr27 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-28     POINTER.
    public NPointer irisPcbPtr28 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-29     POINTER.
    public NPointer irisPcbPtr29 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-30     POINTER.
    public NPointer irisPcbPtr30 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-31     POINTER.
    public NPointer irisPcbPtr31 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-32     POINTER.
    public NPointer irisPcbPtr32 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-33     POINTER.
    public NPointer irisPcbPtr33 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-34     POINTER.
    public NPointer irisPcbPtr34 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-35     POINTER.
    public NPointer irisPcbPtr35 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-36     POINTER.
    public NPointer irisPcbPtr36 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-37     POINTER.
    public NPointer irisPcbPtr37 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-38     POINTER.
    public NPointer irisPcbPtr38 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-39     POINTER.
    public NPointer irisPcbPtr39 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-40     POINTER.
    public NPointer irisPcbPtr40 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-41     POINTER.
    public NPointer irisPcbPtr41 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-42     POINTER.
    public NPointer irisPcbPtr42 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-43     POINTER.
    public NPointer irisPcbPtr43 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-44     POINTER.
    public NPointer irisPcbPtr44 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-45     POINTER.
    public NPointer irisPcbPtr45 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-46     POINTER.
    public NPointer irisPcbPtr46 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-47     POINTER.
    public NPointer irisPcbPtr47 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-48     POINTER.
    public NPointer irisPcbPtr48 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-49     POINTER.
    public NPointer irisPcbPtr49 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-50     POINTER.
    public NPointer irisPcbPtr50 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-51     POINTER.
    public NPointer irisPcbPtr51 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-52     POINTER.
    public NPointer irisPcbPtr52 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-53     POINTER.
    public NPointer irisPcbPtr53 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-54     POINTER.
    public NPointer irisPcbPtr54 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-55     POINTER.
    public NPointer irisPcbPtr55 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-56     POINTER.
    public NPointer irisPcbPtr56 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-57     POINTER.
    public NPointer irisPcbPtr57 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-58     POINTER.
    public NPointer irisPcbPtr58 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-59     POINTER.
    public NPointer irisPcbPtr59 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-60     POINTER.
    public NPointer irisPcbPtr60 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-61     POINTER.
    public NPointer irisPcbPtr61 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-62     POINTER.
    public NPointer irisPcbPtr62 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-63     POINTER.
    public NPointer irisPcbPtr63 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-64     POINTER.
    public NPointer irisPcbPtr64 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-65     POINTER.
    public NPointer irisPcbPtr65 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-66     POINTER.
    public NPointer irisPcbPtr66 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-67     POINTER.
    public NPointer irisPcbPtr67 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-68     POINTER.
    public NPointer irisPcbPtr68 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-69     POINTER.
    public NPointer irisPcbPtr69 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-70     POINTER.
    public NPointer irisPcbPtr70 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-71     POINTER.
    public NPointer irisPcbPtr71 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-72     POINTER.
    public NPointer irisPcbPtr72 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-73     POINTER.
    public NPointer irisPcbPtr73 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-74     POINTER.
    public NPointer irisPcbPtr74 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-75     POINTER.
    public NPointer irisPcbPtr75 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-76     POINTER.
    public NPointer irisPcbPtr76 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-77     POINTER.
    public NPointer irisPcbPtr77 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-78     POINTER.
    public NPointer irisPcbPtr78 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-79     POINTER.
    public NPointer irisPcbPtr79 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-80     POINTER.
    public NPointer irisPcbPtr80 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-81     POINTER.
    public NPointer irisPcbPtr81 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-82     POINTER.
    public NPointer irisPcbPtr82 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-83     POINTER.
    public NPointer irisPcbPtr83 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-84     POINTER.
    public NPointer irisPcbPtr84 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-85     POINTER.
    public NPointer irisPcbPtr85 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-86     POINTER.
    public NPointer irisPcbPtr86 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-87     POINTER.
    public NPointer irisPcbPtr87 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-88     POINTER.
    public NPointer irisPcbPtr88 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-89     POINTER.
    public NPointer irisPcbPtr89 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-90     POINTER.
    public NPointer irisPcbPtr90 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-91     POINTER.
    public NPointer irisPcbPtr91 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-92     POINTER.
    public NPointer irisPcbPtr92 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-93     POINTER.
    public NPointer irisPcbPtr93 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-94     POINTER.
    public NPointer irisPcbPtr94 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-95     POINTER.
    public NPointer irisPcbPtr95 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-96     POINTER.
    public NPointer irisPcbPtr96 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-97     POINTER.
    public NPointer irisPcbPtr97 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-98     POINTER.
    public NPointer irisPcbPtr98 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-99     POINTER.
    public NPointer irisPcbPtr99 = new NPointer();
    // COB:        01  IRIS-PCB-PTR-100     POINTER.
    public NPointer irisPcbPtr100 = new NPointer();
    // COB:        01 IRIS-DB-PCB.
    public IrisDbPcb irisDbPcb = new IrisDbPcb();

    public class IrisDbPcb extends NGroup {

      // COB:          03 DB-PCB-FIXED-PART.
      public class DbPcbFixedPart extends NGroup {
        // COB:            05 DB-PCB-DBD-NAME             PIC X(8).
        public NChar dbPcbDbdName = new NChar(8);
        // COB:            05 DB-PCB-SEGMENT-LEVEL        PIC 9(2).
        public NZoned dbPcbSegmentLevel = new NZoned(2, false);

        // COB:              88 DB-PCB-IS-GSAM                       VALUE 55.
        public boolean dbPcbIsGsam() {
          return dbPcbSegmentLevel.equals(55);
        }

        public void setDbPcbIsGsam(boolean value) {
          if (value) dbPcbSegmentLevel.setValue(55);
        }

        // COB:            05 DB-PCB-STATUS-CODE          PIC X(2).
        public NChar dbPcbStatusCode = new NChar(2);

        // COB:              88 DB-STATUS-OK                         VALUE '  '.
        public boolean dbStatusOk() {
          return dbPcbStatusCode.equals("  ");
        }

        public void setDbStatusOk(boolean value) {
          if (value) dbPcbStatusCode.setValue("  ");
        }

        // COB:              88 DB-STATUS-RESOURCE-DEADLOCK          VALUE 'FD'.
        public boolean dbStatusResourceDeadlock() {
          return dbPcbStatusCode.equals("FD");
        }

        public void setDbStatusResourceDeadlock(boolean value) {
          if (value) dbPcbStatusCode.setValue("FD");
        }

        // COB:              88 DB-STATUS-HIGHER-LEVEL-CROSSED       VALUE 'GA'.
        public boolean dbStatusHigherLevelCrossed() {
          return dbPcbStatusCode.equals("GA");
        }

        public void setDbStatusHigherLevelCrossed(boolean value) {
          if (value) dbPcbStatusCode.setValue("GA");
        }

        // COB:              88 DB-STATUS-END-DB-REACHED             VALUE 'GB'.
        public boolean dbStatusEndDbReached() {
          return dbPcbStatusCode.equals("GB");
        }

        public void setDbStatusEndDbReached(boolean value) {
          if (value) dbPcbStatusCode.setValue("GB");
        }

        // COB:              88 DB-STATUS-SEGMENT-NOT-FOUND          VALUE 'GE'.
        public boolean dbStatusSegmentNotFound() {
          return dbPcbStatusCode.equals("GE");
        }

        public void setDbStatusSegmentNotFound(boolean value) {
          if (value) dbPcbStatusCode.setValue("GE");
        }

        // COB:              88 DB-STATUS-CHANGED-SEGMENT-TYPE       VALUE 'GK'.
        public boolean dbStatusChangedSegmentType() {
          return dbPcbStatusCode.equals("GK");
        }

        public void setDbStatusChangedSegmentType(boolean value) {
          if (value) dbPcbStatusCode.setValue("GK");
        }

        // COB:              88 DB-STATUS-DUPLICATED-KEY             VALUE 'II'.
        public boolean dbStatusDuplicatedKey() {
          return dbPcbStatusCode.equals("II");
        }

        public void setDbStatusDuplicatedKey(boolean value) {
          if (value) dbPcbStatusCode.setValue("II");
        }

        // COB:              88 DB-STATUS-ISRT-RULE-VIOLATION        VALUE 'IX'.
        public boolean dbStatusIsrtRuleViolation() {
          return dbPcbStatusCode.equals("IX");
        }

        public void setDbStatusIsrtRuleViolation(boolean value) {
          if (value) dbPcbStatusCode.setValue("IX");
        }

        // COB:              88 DB-STATUS-INTERNAL-NOT-HANDLED       VALUE 'XX'.
        public boolean dbStatusInternalNotHandled() {
          return dbPcbStatusCode.equals("XX");
        }

        public void setDbStatusInternalNotHandled(boolean value) {
          if (value) dbPcbStatusCode.setValue("XX");
        }

        // COB:              88 DB-STATUS-DUAL-PCB-MISMATCH          VALUE 'XY'.
        public boolean dbStatusDualPcbMismatch() {
          return dbPcbStatusCode.equals("XY");
        }

        public void setDbStatusDualPcbMismatch(boolean value) {
          if (value) dbPcbStatusCode.setValue("XY");
        }

        // COB:              88 DB-STATUS-DUAL-IOAREA-MISMATCH       VALUE 'XZ'.
        public boolean dbStatusDualIoareaMismatch() {
          return dbPcbStatusCode.equals("XZ");
        }

        public void setDbStatusDualIoareaMismatch(boolean value) {
          if (value) dbPcbStatusCode.setValue("XZ");
        }

        // COB:              88 DB-STATUS-PSB-INIT-ERROR             VALUE 'TE'.
        public boolean dbStatusPsbInitError() {
          return dbPcbStatusCode.equals("TE");
        }

        public void setDbStatusPsbInitError(boolean value) {
          if (value) dbPcbStatusCode.setValue("TE");
        }

        // COB:              88 DB-STATUS-NO-PSB-SCHEDULED           VALUE 'TG'.
        public boolean dbStatusNoPsbScheduled() {
          return dbPcbStatusCode.equals("TG");
        }

        public void setDbStatusNoPsbScheduled(boolean value) {
          if (value) dbPcbStatusCode.setValue("TG");
        }

        // COB:              88 DB-STATUS-WRONG-PCB                  VALUE 'TP'.
        public boolean dbStatusWrongPcb() {
          return dbPcbStatusCode.equals("TP");
        }

        public void setDbStatusWrongPcb(boolean value) {
          if (value) dbPcbStatusCode.setValue("TP");
        }

        // COB:              88 DB-STATUS-NO-MORE-MSG                VALUE 'QC'.
        public boolean dbStatusNoMoreMsg() {
          return dbPcbStatusCode.equals("QC");
        }

        public void setDbStatusNoMoreMsg(boolean value) {
          if (value) dbPcbStatusCode.setValue("QC");
        }

        // COB:              88 DB-STATUS-NOT-VALID-LENGTH           VALUE 'V1'.
        public boolean dbStatusNotValidLength() {
          return dbPcbStatusCode.equals("V1");
        }

        public void setDbStatusNotValidLength(boolean value) {
          if (value) dbPcbStatusCode.setValue("V1");
        }

        // COB:              88 DB-STATUS-DLET-RULE-VIOLATION        VALUE 'DX'.
        public boolean dbStatusDletRuleViolation() {
          return dbPcbStatusCode.equals("DX");
        }

        public void setDbStatusDletRuleViolation(boolean value) {
          if (value) dbPcbStatusCode.setValue("DX");
        }

        // COB:              88 DB-STATUS-REPL-RULE-VIOLATION        VALUE 'RX'.
        public boolean dbStatusReplRuleViolation() {
          return dbPcbStatusCode.equals("RX");
        }

        public void setDbStatusReplRuleViolation(boolean value) {
          if (value) dbPcbStatusCode.setValue("RX");
        }

        // COB:            05 DB-PCB-PROC-OPTS            PIC X(4).
        public NChar dbPcbProcOpts = new NChar(4);
        // COB:            05 DB-PCB-RESERVED             PIC S9(9)  COMP.
        public NInt32 dbPcbReserved = new NInt32();
        // COB:            05 DB-PCB-RESERVED-X REDEFINES DB-PCB-RESERVED PIC X(4).
        public NChar dbPcbReservedX = new NChar(4).redefines(dbPcbReserved);

        // COB:              88 DB-PCB-IRIS-EYE                      VALUE 'IRIS'.
        public boolean dbPcbIrisEye() {
          return dbPcbReservedX.equals("IRIS");
        }

        public void setDbPcbIrisEye(boolean value) {
          if (value) dbPcbReservedX.setValue("IRIS");
        }

        // COB:            05 DB-PCB-SEGMENT-NAME         PIC X(8).
        public NChar dbPcbSegmentName = new NChar(8);
        // COB:            05 DB-PCB-FB-KEY-LENGTH        PIC S9(9)  COMP.
        public NInt32 dbPcbFbKeyLength = new NInt32();
        // COB:            05 DB-PCB-NUM-SENSEGS          PIC S9(9)  COMP.
        public NInt32 dbPcbNumSensegs = new NInt32();
      }

      public DbPcbFixedPart dbPcbFixedPart = new DbPcbFixedPart();
      // COB:          03 DB-PCB-KEY-FB                 PIC X(1587).
      public NChar dbPcbKeyFb = new NChar(1587);
      // COB:          03 DB-PCB-MEM-PCB-AREA           PIC X(377).
      public NChar dbPcbMemPcbArea = new NChar(377);

      // COB:          03 DB-RNT-CNTL-AREA.
      public class DbRntCntlArea extends NGroup {
        // COB:            05 DB-RNT-CNTL-AREA-FIXED      PIC X(1000).
        public NChar dbRntCntlAreaFixed = new NChar(1000);

        // COB:            05 FILLER REDEFINES DB-RNT-CNTL-AREA-FIXED.
        public class Filler387 extends NGroup {
          // COB:              07 FILLER                    PIC X(781).
          public NChar filler388 = new NChar(781);
          // COB:              07 RUN-IMS-DUAL-POINTER-IO   POINTER.
          public NPointer runImsDualPointerIo = new NPointer();
          // COB:              07 FILLER                    PIC X(215).
          public NChar filler390 = new NChar(215);
        }

        public Filler387 filler387 = (Filler387) new Filler387().redefines(dbRntCntlAreaFixed);
        // COB:            05 DB-RNT-CNTL-AREA-VAR        PIC X(32000).
        public NChar dbRntCntlAreaVar = new NChar(32000);
      }

      public DbRntCntlArea dbRntCntlArea = new DbRntCntlArea();
    }

    // COB:        01 IRIS-MSG-PCB REDEFINES IRIS-DB-PCB.
    public class IrisMsgPcb extends NGroup {
      // COB:           02 FILLER                    PIC X(8).
      public NChar filler393 = new NChar(8);
      // COB:           02 FILLER                    PIC X.
      public NChar filler394 = new NChar(1);

      // COB:             88 IRIS-MSG-PRINT-DETAIL   VALUE 'D'.
      public boolean irisMsgPrintDetail() {
        return filler394.equals("D");
      }

      public void setIrisMsgPrintDetail(boolean value) {
        if (value) filler394.setValue("D");
      }

      // COB:           02 FILLER                    PIC X.
      public NChar filler396 = new NChar(1);
      // COB:           02 IRIS-MSG-STATUS-CODE      PIC X(2).
      public NChar irisMsgStatusCode = new NChar(2);
      // COB:           02 IRIS-MSG-MESSAGE-COUNT    PIC S9(4) COMP.
      public NInt16 irisMsgMessageCount = new NInt16();
      // COB:           02 IRIS-MSG-TIMESTAMP        PIC X(26).
      public NChar irisMsgTimestamp = new NChar(26);
    }

    public IrisMsgPcb irisMsgPcb = (IrisMsgPcb) new IrisMsgPcb().redefines(irisDbPcb);
    // COB:        01  IRIS-LK-CELLS.
    public IrisLkCells irisLkCells = new IrisLkCells();

    public class IrisLkCells extends NGroup {

      // COB:            03 FILLER                    OCCURS 45.
      public class Filler403 extends NGroup {
        // COB:               05 IRIS-LK-POINTER        POINTER.
        public NPointer irisLkPointer = new NPointer();
      }

      public Filler403[] filler403 = AbstractNField.occurs(45, new Filler403());

      public Filler403 filler403AtIndex(int index) {
        return getOccursInstance(filler403, index);
      }
    }

    // COB:        01  IRIS-LK-PCBS-ADDR            PIC S9(9)   COMP-5.
    public NInt32 irisLkPcbsAddr = new NInt32();
    // COB:        01  IRIS-LK-PCBS-PTR REDEFINES IRIS-LK-PCBS-ADDR POINTER.
    public NPointer irisLkPcbsPtr = new NPointer().redefines(irisLkPcbsAddr);
    // COB:        01  LK-DLIUIB.
    public LkDliuib lkDliuib = new LkDliuib();

    public class LkDliuib extends NGroup {

      // COB:             05 UIBPCBAL-POINTER          POINTER.
      public NPointer uibpcbalPointer = new NPointer();
      // COB:             05 UIBPCBAL REDEFINES UIBPCBAL-POINTER PIC S9(9) COMP-5.
      public NInt32 uibpcbal = new NInt32().redefines(uibpcbalPointer);

      // COB:             05 UIBRCODE.
      public class Uibrcode extends NGroup {
        // COB:                10 UIBFCTR                PIC X(01).
        public NChar uibfctr = new NChar(1);
        // COB:                10 UIBDLTR                PIC X(01).
        public NChar uibdltr = new NChar(1);
      }

      public Uibrcode uibrcode = new Uibrcode();
    }

    // COB:        01 IRIS-INQY-AREA.
    public IrisInqyArea irisInqyArea = new IrisInqyArea();

    public class IrisInqyArea extends NGroup {

      // COB:           05 INQY-IMS-ID                 PIC X(08).
      public NChar inqyImsId = new NChar(8);
      // COB:           05 INQY-IMS-REL-LEVEL          PIC X(04).
      public NChar inqyImsRelLevel = new NChar(4);
      // COB:           05 INQY-IMS-CONTROL-REGION     PIC X(08).
      public NChar inqyImsControlRegion = new NChar(8);
      // COB:           05 INQY-APPL-REGION-TYPE       PIC X(08).
      public NChar inqyApplRegionType = new NChar(8);
      // COB:           05 INQY-REGION-ID              PIC X(04).
      public NChar inqyRegionId = new NChar(4);
      // COB:           05 INQY-APPL-PROG-NAME         PIC X(08).
      public NChar inqyApplProgName = new NChar(8);
      // COB:           05 INQY-PSB-NAME               PIC X(08).
      public NChar inqyPsbName = new NChar(8);
      // COB:           05 INQY-TRAN-NAME              PIC X(08).
      public NChar inqyTranName = new NChar(8);
      // COB:           05 INQY-USER-ID                PIC X(08).
      public NChar inqyUserId = new NChar(8);
      // COB:           05 INQY-GROUP-NAME             PIC X(08).
      public NChar inqyGroupName = new NChar(8);
      // COB:           05 INQY-STATUS-GROUP-INDIC     PIC X(04).
      public NChar inqyStatusGroupIndic = new NChar(4);
      // COB:           05 INQY-RECOVERY-TOKEN-PTR     POINTER.
      public NPointer inqyRecoveryTokenPtr = new NPointer();
      // COB:           05 INQY-APPL-PARM-PTR          POINTER.
      public NPointer inqyApplParmPtr = new NPointer();
      // COB:           05 FILLER                      PIC X(256).
      public NChar filler430 = new NChar(256);
    }

    // COB:        01 IRIS-IO-PCB.
    public IrisIoPcb irisIoPcb = new IrisIoPcb();

    public class IrisIoPcb extends NGroup {

      // COB:          03 IRIS-IO-LTERM                PIC X(8).
      public NChar irisIoLterm = new NChar(8);
      // COB:          03 IRIS-IO-FILLER               PIC X(2).
      public NChar irisIoFiller = new NChar(2);
      // COB:          03 IRIS-IO-STATUS-CODE          PIC X(2).
      public NChar irisIoStatusCode = new NChar(2);
      // COB:          03 IRIS-IO-CURRENT-DT           PIC S9(7) COMP-3.
      public NPacked irisIoCurrentDt = new NPacked(4);
      // COB:          03 IRIS-IO-CURRENT-TM           PIC S9(7) COMP-3.
      public NPacked irisIoCurrentTm = new NPacked(4);
      // COB:          03 IRIS-IO-MSG-SEQ-NO           PIC S9(5) COMP.
      public NInt32 irisIoMsgSeqNo = new NInt32();
      // COB:          03 IRIS-IO-MOD-NAME             PIC X(8).
      public NChar irisIoModName = new NChar(8);
      // COB:          03 IRIS-IO-USER-ID              PIC X(8).
      public NChar irisIoUserId = new NChar(8);
    }

    // COB:        01 LK-DIB-BLOCK                  PIC X(32).
    public NChar lkDibBlock = new NChar(32);
  }

  final IrisutilLinkage params = new IrisutilLinkage();

  public IRISUTIL(Context context) {
    super(context);
  }

  @ProgramInit
  protected void initialize() {}

  @Override
  protected int procedure(AbstractNField... args) throws ContextException {
    if (args != null) {
      params.allocate();
      for (int idx = 0; idx < args.length; idx++) {
        if (idx + 1 > params.getChildren().size()) {
          break;
        }
        params.getChildren().get(idx).setAddress(args[idx].getAddress());
      }
    }
    mainProgram();
    return RETURN_CODE;
  }

  protected void mainProgram() {
    // COB(553): IF IRIS-EXECDLI
    if (params.irisWorkArea.irisExecdli()) {
      // COB(554): PERFORM EXTRACT-PCB-EXEC THRU EXTRACT-PCB-EXEC-EX
      extractPcbExec();
      // COB(555): END-IF
    }
    // COB(557): NODUAL     SET IRIS-FINAL TO TRUE
    params.irisWorkArea.setIrisFinal(true);
    // COB(559): PERFORM INIT-VARIABLES THRU INIT-VARIABLES-EX
    initVariables();
    // COB(561): EVALUATE TRUE
    // COB(562): WHEN IRIS-FUNC-DLIT
    if (params.irisWorkArea.irisFuncDlit()) {
      // COB(564): PERFORM HANDLE-BATCH-PCB THRU HANDLE-BATCH-PCB-EX
      handleBatchPcb();
      // COB(565): WHEN IRIS-FUNC-SCHD
    } else if (params.irisWorkArea.irisFuncSchd()) {
      // COB(567): PERFORM HANDLE-SCHED THRU HANDLE-SCHED-EX
      handleSched();
      // COB(568): WHEN IRIS-FUNC-PCB
    } else if (params.irisWorkArea.irisFuncPcb()) {
      // COB(570): PERFORM HANDLE-ONLINE-PCB THRU HANDLE-ONLINE-PCB-EX
      handleOnlinePcb();
      // COB(571): WHEN IRIS-FUNC-TERM
    } else if (params.irisWorkArea.irisFuncTerm()) {
      // COB(573): PERFORM HANDLE-TERM THRU HANDLE-TERM-EX
      handleTerm();
      // COB(574): WHEN IRIS-FUNC-CHKP
    } else if (params.irisWorkArea.irisFuncChkp()) {
      // COB(575): PERFORM HANDLE-BASIC-CHKP THRU HANDLE-BASIC-CHKP-EX
      handleBasicChkp();
      // COB(576): WHEN OTHER
    } else {
      // COB(578): SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
      params.irisWorkArea.setIrisErrFunctionNotFound(true);
      // COB(582): SET DB-STATUS-INTERNAL-NOT-HANDLED TO TRUE
      params.irisDbPcb.dbPcbFixedPart.setDbStatusInternalNotHandled(true);
      // COB(583): END-EVALUATE
    }
    // COB(585): PERFORM FINALIZE-VARIABLES THRU FINALIZE-VARIABLES-EX.
    finalizeVariables();
    mainProgramEx();
  }

  /***/
  protected void mainProgramEx() {
    // COB(589): IF IRIS-EXECDLI
    if (params.irisWorkArea.irisExecdli()) {
      // COB(590): PERFORM SET-DIB-BLOCK THRU SET-DIB-BLOCK-EX
      setDibBlock();
      // COB(591): END-IF.
    }
    // COB(592): EXIT PROGRAM.
    throw new ExitProgramException();
  }

  /***
   ******************************************************************
   *    INITIALIZE VARIABLES
   ******************************************************************
   */
  protected void initVariables() {
    // COB(600): MOVE ZERO TO WS-DBD-LOOKUP-TABLE-COUNT
    // COB(600):              WS-CMPAT-IDX
    ws.wsDbdLookupTableCount.setValue(0);
    ws.wsCmpatIdx.setValue(0);
    // COB(602): SET WS-PSB-NOT-ALLOCATED TO TRUE.
    ws.setWsPsbNotAllocated(true);
  }

  /***
   ******************************************************************
   *    FINALIZE VARIABLES
   ******************************************************************
   */
  protected void finalizeVariables() {
    // COB(614): CONTINUE.
    // do nothing

  }

  /***
   ******************************************************************
   *    HANDLE EXEC DLI SCHEDULE
   ******************************************************************
   */
  protected void handleSched() {
    // COB(626): MOVE 'HANDLE-SCHED' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("HANDLE-SCHED");
    // COB(628): IF IRIS-FUNC-SCHD
    if (params.irisWorkArea.irisFuncSchd()) {
      // COB(629): PERFORM HANDLE-ONLINE-PCB THRU HANDLE-ONLINE-PCB-EX
      handleOnlinePcb();
      // COB(631): SET IRISADDR-RTN TO TRUE
      ws.iriscomm.setIrisaddrRtn(true);
      // COB(634): SET IRIS-EXEC-DLI-PTR TO ADDRESS OF IRIS-BLL-CELLS
      params.irisWorkArea.irisExecDliPtr.setTo(ws.irisBllCells.getAddress());
      // COB(635): ELSE
    } else {
      // COB(636): PERFORM SET-LINKAGE-PCBS THRU SET-LINKAGE-PCBS-EX
      setLinkagePcbs();
      // COB(637): PERFORM HANDLE-BATCH-PCB THRU HANDLE-BATCH-PCB-EX
      handleBatchPcb();
      // COB(638): END-IF.
    }
  }

  /***
   ******************************************************************
   *    HANDLE PCB SCHEDULE FOR ONLINE
   ******************************************************************
   */
  protected void handleOnlinePcb() {
    // COB(650): MOVE 'HANDLE-ONLINE-PCB' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("HANDLE-ONLINE-PCB");
    // COB(652): MOVE SPACE TO IRIS-AUDIT-FIELDS
    params.irisWorkArea.irisAuditFields.spaces();
    // COB(654): MOVE IRIS-PARAM-NUM TO WS-SCHED-PARAM-NUM
    ws.wsSchedParamNum.setValue(params.irisWorkArea.irisParamNum);
    // COB(656): MOVE IRIS-LK-PSB-NAME TO IRIS-PSB-NAME
    params.irisWorkArea.irisPsbName.setValue(params.irisLkPsbName);
    // COB(657): PERFORM FINAL-SCHED THRU FINAL-SCHED-EX.
    finalSched();
  }

  /******************************************************************
   *    HANDLE TERM
   ******************************************************************
   */
  protected void handleTerm() {
    // COB(668): MOVE 'HANDLE-TERM' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("HANDLE-TERM");
    // COB(671): MOVE LOW-VALUE TO IRIS-PCBS-AREA.
    ws.irisPcbsArea.lowValues();
  }

  /******************************************************************
   *    HANDLE BASIC CHKP
   ******************************************************************
   */
  protected void handleBasicChkp() {
    // COB(688): MOVE 'HANDLE-BASIC-CHKP' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("HANDLE-BASIC-CHKP");
    // COB(690): EXEC SQL COMMIT END-EXEC.
    // FIXME-[NOT_CONVERTED] EXEC :690 >COB: [           EXEC SQL COMMIT END-EXEC.]

  }

  /***
   ******************************************************************
   *    SCHED FOR FINAL VERSION
   ******************************************************************
   */
  protected void finalSched() {
    // COB(702): MOVE 'FINAL-SCHED' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("FINAL-SCHED");
    // COB(705): PERFORM VARYING WS-INDEX FROM 1 BY 1
    // COB(705):         UNTIL WS-INDEX > IRIS-PARAM-NUM
    ws.wsIndex.setValue(1);
    for (; !ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum); ws.wsIndex.add(1)) {
      // COB(708): IF NOT IRIS-NO-ERROR
      if (!params.irisWorkArea.irisNoError()) {
        // COB(709): DISPLAY 'IRISUTIL - ALLOCATE PSB ERROR'
        sysout.display("IRISUTIL - ALLOCATE PSB ERROR");
        // COB(710): GO TO FINAL-SCHED-EX
        return;
        // COB(711): END-IF
      }
      // COB(717): SET WS-PTR TO ADDRESS OF IRIS-PCBS-TAB(WS-INDEX)
      ws.wsPtr.setTo(ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).getAddress());
      // COB(718): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
      ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
      // COB(719): SET ADDRESS OF IRIS-DB-PCB TO IRIS-BLL-POINTER(WS-INDEX)
      params.irisDbPcb.setAddress(
          ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer);
      // COB(720): SET DB-PCB-IRIS-EYE TO TRUE
      params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
      // COB(722): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
      ws.irisPcbsArea
          .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
          .irisPcbHeader
          .setValue(params.irisDbPcb.dbPcbFixedPart);
      // COB(723): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
      ws.irisPcbsArea
          .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
          .irisRntCntlArea
          .runRunFixed
          .runImsDualPointer
          .setValue(0);
      // COB(724): SET WS-DUAL-SQL-ONLY TO TRUE
      ws.setWsDualSqlOnly(true);
      // COB(725): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
      ws.irisPcbsArea
          .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
          .irisRntCntlArea
          .runRunFixed
          .runPcbIndex
          .setValue(ws.wsIndex);
      // COB(726): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
      ws.irisPcbsArea
          .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
          .irisRntCntlArea
          .runRunFixed
          .runDbdStatus
          .setValue(ws.wsRunDbdStatus);
      // COB(727): MOVE IRIS-PSB-NAME TO DT-PSB-NAME(WS-INDEX)
      ws.irisPcbsArea
          .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
          .memoryPcbArea
          .dtPsbPcb
          .dtPsbName
          .setValue(params.irisWorkArea.irisPsbName);
      // COB(728): END-PERFORM.
    }
  }

  /***
   ******************************************************************
   *    HANDLE PCB SCHEDULE FOR BATCH
   ******************************************************************
   */
  protected void handleBatchPcb() {
    // COB(740): MOVE 'HANDLE-BATCH-PCB' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("HANDLE-BATCH-PCB");
    // COB(742): PERFORM VARYING WS-INDEX FROM 1 BY 1
    // COB(742):   UNTIL WS-INDEX > IRIS-PARAM-NUM
    // COB(742):      OR IRIS-PCB-TYPE-DB(WS-INDEX)
    // COB(742):      OR IRIS-PCB-TYPE-GSAM(WS-INDEX)
    ws.wsIndex.setValue(1);
    for (;
        !(ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
            || params
                .irisWorkArea
                .irisPcbTypeTable
                .filler355AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbTypeDb()
            || params
                .irisWorkArea
                .irisPcbTypeTable
                .filler355AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbTypeGsam());
        ws.wsIndex.add(1)) {
      // COB(746): END-PERFORM
    }
    // COB(748): MOVE SPACE TO IRIS-AUDIT-FIELDS
    params.irisWorkArea.irisAuditFields.spaces();
    // COB(749): MOVE LOW-VALUE TO IRIS-PCBS-AREA
    ws.irisPcbsArea.lowValues();
    // COB(750): MOVE IRIS-PARAM-NUM TO WS-SCHED-PARAM-NUM
    ws.wsSchedParamNum.setValue(params.irisWorkArea.irisParamNum);
    // COB(751): MOVE IRIS-PSB-NAME TO DT-PSB-NAME(1)
    ws.irisPcbsArea
        .irisPcbsTabAtIndex(0)
        .memoryPcbArea
        .dtPsbPcb
        .dtPsbName
        .setValue(params.irisWorkArea.irisPsbName);
    // COB(753): MOVE ZERO TO WS-INDEX
    ws.wsIndex.setValue(0);
    // COB(754): MOVE ZERO TO WS-INDEX-C
    ws.wsIndexC.setValue(0);
    // COB(758): SET IRISADDR-RTN TO TRUE
    ws.iriscomm.setIrisaddrRtn(true);
    // COB(760): PERFORM HANDLE-BATCH-PCB-A THRU HANDLE-BATCH-PCB-A-EX
    handleBatchPcbA();
    // COB(765): SET IRIS-EXEC-DLI-PTR TO ADDRESS OF IRIS-BLL-CELLS
    params.irisWorkArea.irisExecDliPtr.setTo(ws.irisBllCells.getAddress());
  }

  /***/
  protected void handleBatchPcbA() {
    // COB(774): PERFORM HANDLE-BATCH-PCB-A1
    handleBatchPcbA1();
    // COB(775): PERFORM HANDLE-BATCH-PCB-A2
    handleBatchPcbA2();
    // COB(776): PERFORM HANDLE-BATCH-PCB-A3
    handleBatchPcbA3();
    // COB(777): PERFORM HANDLE-BATCH-PCB-A4
    handleBatchPcbA4();
    // COB(778): PERFORM HANDLE-BATCH-PCB-A5
    handleBatchPcbA5();
  }

  /***/
  protected void handleBatchPcbA1() {
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr1.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr1.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr2.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr2.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr3.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr3.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr4.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr4.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr5.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr5.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr6.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr6.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr7.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr7.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr8.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr8.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr9.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr9.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr10.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr10.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
  }

  protected void handleBatchPcbA2() {
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr11.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr11.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr12.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr12.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr13.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr13.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr14.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr14.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr15.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr15.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr16.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr16.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr17.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr17.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr18.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr18.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr19.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr19.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr20.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr20.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
  }

  protected void handleBatchPcbA3() {
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr21.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr21.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr22.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr22.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr23.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr23.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr24.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr24.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr25.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr25.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr26.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr26.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr27.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr27.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr28.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr28.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr29.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr29.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr30.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr30.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
  }

  protected void handleBatchPcbA4() {
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr31.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr31.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr32.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr32.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr33.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr33.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr34.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr34.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr35.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr35.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr36.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr36.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr37.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr37.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr38.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr38.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr39.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr39.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr40.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr40.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
  }

  protected void handleBatchPcbA5() {
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr41.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr41.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr42.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr42.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr43.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr43.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr44.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr44.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
    // COB(10): ADD 1 TO WS-INDEX
    ws.wsIndex.add(1);
    // COB(11): IF IRIS-USED-PCB = ZERO
    if (params.irisWorkArea.irisUsedPcb.equals(0)) {
      // COB(12): MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
      params.irisWorkArea.irisUsedPcb.setValue(params.irisWorkArea.irisParamNum);
      // COB(13): END-IF
    }
    // COB(14): IF WS-INDEX NOT > IRIS-PARAM-NUM
    // COB(14): AND WS-INDEX NOT > IRIS-USED-PCB
    if (!ws.wsIndex.greaterThan(params.irisWorkArea.irisParamNum)
        && !ws.wsIndex.greaterThan(params.irisWorkArea.irisUsedPcb)) {
      // COB(20): IF IRIS-FINAL
      if (params.irisWorkArea.irisFinal()) {
        // COB(23): INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1).irisPcbHeader.initialize();
        // COB(24): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(25): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(25):                        IRIS-PCBS-TAB(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisPcbsArea.irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1),
            ws.wsPtr);
        // COB(29): INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
        params.irisDbPcb.setAddress(ws.wsPtr);
        // COB(30): SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
        ws.irisBllCells.irisBllCellAtIndex(ws.wsIndex.getInt() - 1).irisBllPointer.setTo(ws.wsPtr);
        // COB(31): SET DB-PCB-IRIS-EYE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbPcbIrisEye(true);
        // COB(32): MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME
        ws.wsIoRtn.wsIoRtnDbdname.setValue(
            params.irisWorkArea.irisPcbDbdTable.filler390AtIndex(ws.wsIndex.getInt() - 1)
                .irisPcbDbd);
        // COB(33): MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsIoRtn.wsIoRtnDbdname);
        // COB(34): MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisPcbHeader
            .setValue(params.irisDbPcb.dbPcbFixedPart);
        // COB(35): MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runPcbIndex
            .setValue(ws.wsIndex);
        // COB(36): SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runImsDualPointer
            .setValue(0);
        // COB(37): SET WS-DUAL-SQL-ONLY TO TRUE
        ws.setWsDualSqlOnly(true);
        // COB(38): MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
        ws.irisPcbsArea
            .irisPcbsTabAtIndex(ws.wsIndex.getInt() - 1)
            .irisRntCntlArea
            .runRunFixed
            .runDbdStatus
            .setValue(ws.wsRunDbdStatus);
        // COB(39): INQY           IF IRIS-USED-PCB > 0
        if (params.irisWorkArea.irisUsedPcb.greaterThan(0)) {
          // COB(41): INQY              IF WS-INDEX <= IRIS-USED-PCB
          if (ws.wsIndex.lessEqualThan(params.irisWorkArea.irisUsedPcb)) {
            // COB(42): INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
            params.irisPcbPtr45.setTo(ws.wsPtr);
            // COB(43): INQY              END-IF
          }
          // COB(44): INQY           ELSE
        } else {
          // COB(45): INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
          params.irisPcbPtr45.setTo(ws.wsPtr);
          // COB(46): INQY           END-IF
        }
        // COB(48): SET IRISADDR-RTN TO TRUE
        ws.iriscomm.setIrisaddrRtn(true);
        // COB(49): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        // COB(49):              IRIS-IO-AREA(WS-INDEX) WS-PTR
        context.call(
            ws.iriscomm.irisWsRtn,
            params.irisWorkArea,
            ws.irisIoAreas.filler164AtIndex(ws.wsIndex.getInt() - 1).irisIoArea,
            ws.wsPtr);
        // COB(52): END-IF
      }
      // COB(53): END-IF.
    }
  }

  /***
   ******************************************************************
   *    SET DIB BLOCK
   ******************************************************************
   */
  protected void setDibBlock() {
    // COB(852): MOVE 'SET-DIB-BLOCK' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("SET-DIB-BLOCK");
    // COB(854): MOVE 'IR'                   TO IRIS-DIBVER
    ws.iriscomm.dlzdib.irisDibver.setString("IR");
    // COB(855): MOVE DB-PCB-STATUS-CODE     TO IRIS-DIBSTAT
    ws.iriscomm.dlzdib.irisDibstat.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbStatusCode);
    // COB(856): MOVE DB-PCB-SEGMENT-NAME    TO IRIS-DIBSEGM
    ws.iriscomm.dlzdib.irisDibsegm.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbSegmentName);
    // COB(857): MOVE DB-PCB-SEGMENT-LEVEL   TO IRIS-DIBSEGLV
    ws.iriscomm.dlzdib.irisDibseglv.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbSegmentLevel);
    // COB(858): MOVE DB-PCB-FB-KEY-LENGTH   TO IRIS-DIBKFBL
    ws.iriscomm.dlzdib.irisDibkfbl.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbFbKeyLength);
    // COB(859): MOVE DB-PCB-DBD-NAME        TO IRIS-DIBDBDNM
    ws.iriscomm.dlzdib.irisDibdbdnm.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName);
    // COB(860): MOVE 'IRIS'                 TO IRIS-DIBDBORG
    ws.iriscomm.dlzdib.irisDibdborg.setString("IRIS");
    // COB(861): MOVE IRIS-DIB-BLOCK         TO LK-DIB-BLOCK
    params.lkDibBlock.setValue(ws.iriscomm.irisDibBlock);
    // COB(862): SET ADDRESS OF IRIS-LK-DIB-BLOCK TO ADDRESS OF LK-DIB-BLOCK.
    params.irisLkDibBlock.setAddress(params.lkDibBlock.getAddress());
  }

  /***
   ******************************************************************
   *    SET LINKAGE PCBS FOR CHALLENGING TOOL
   ******************************************************************
   */
  protected void setLinkagePcbs() {
    // COB(874): MOVE 'SET-LINKAGE-PCBS' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("SET-LINKAGE-PCBS");
    // COB(876): SET ADDRESS OF IRIS-LK-CELLS TO IRIS-EXEC-DLI-PTR
    params.irisLkCells.setAddress(params.irisWorkArea.irisExecDliPtr);
    // COB(877): SET ADDRESS OF IRIS-PCB-PTR-1 TO
    // COB(877):     ADDRESS OF IRIS-LK-POINTER(1)
    params.irisPcbPtr1.setAddress(
        params.irisLkCells.filler403AtIndex(0).irisLkPointer.getAddress());
    // COB(879): SET ADDRESS OF IRIS-PCB-PTR-2   TO
    // COB(879):     ADDRESS OF IRIS-LK-POINTER(2)
    params.irisPcbPtr2.setAddress(
        params.irisLkCells.filler403AtIndex(1).irisLkPointer.getAddress());
    // COB(881): SET ADDRESS OF IRIS-PCB-PTR-3   TO
    // COB(881):     ADDRESS OF IRIS-LK-POINTER(3)
    params.irisPcbPtr3.setAddress(
        params.irisLkCells.filler403AtIndex(2).irisLkPointer.getAddress());
    // COB(883): SET ADDRESS OF IRIS-PCB-PTR-4   TO
    // COB(883):     ADDRESS OF IRIS-LK-POINTER(4)
    params.irisPcbPtr4.setAddress(
        params.irisLkCells.filler403AtIndex(3).irisLkPointer.getAddress());
    // COB(885): SET ADDRESS OF IRIS-PCB-PTR-5   TO
    // COB(885):     ADDRESS OF IRIS-LK-POINTER(5)
    params.irisPcbPtr5.setAddress(
        params.irisLkCells.filler403AtIndex(4).irisLkPointer.getAddress());
    // COB(887): SET ADDRESS OF IRIS-PCB-PTR-6   TO
    // COB(887):     ADDRESS OF IRIS-LK-POINTER(6)
    params.irisPcbPtr6.setAddress(
        params.irisLkCells.filler403AtIndex(5).irisLkPointer.getAddress());
    // COB(889): SET ADDRESS OF IRIS-PCB-PTR-7   TO
    // COB(889):     ADDRESS OF IRIS-LK-POINTER(7)
    params.irisPcbPtr7.setAddress(
        params.irisLkCells.filler403AtIndex(6).irisLkPointer.getAddress());
    // COB(891): SET ADDRESS OF IRIS-PCB-PTR-8   TO
    // COB(891):     ADDRESS OF IRIS-LK-POINTER(8)
    params.irisPcbPtr8.setAddress(
        params.irisLkCells.filler403AtIndex(7).irisLkPointer.getAddress());
    // COB(893): SET ADDRESS OF IRIS-PCB-PTR-9   TO
    // COB(893):     ADDRESS OF IRIS-LK-POINTER(9)
    params.irisPcbPtr9.setAddress(
        params.irisLkCells.filler403AtIndex(8).irisLkPointer.getAddress());
    // COB(895): SET ADDRESS OF IRIS-PCB-PTR-10  TO
    // COB(895):     ADDRESS OF IRIS-LK-POINTER(10)
    params.irisPcbPtr10.setAddress(
        params.irisLkCells.filler403AtIndex(9).irisLkPointer.getAddress());
    // COB(897): SET ADDRESS OF IRIS-PCB-PTR-11  TO
    // COB(897):     ADDRESS OF IRIS-LK-POINTER(11)
    params.irisPcbPtr11.setAddress(
        params.irisLkCells.filler403AtIndex(10).irisLkPointer.getAddress());
    // COB(899): SET ADDRESS OF IRIS-PCB-PTR-12  TO
    // COB(899):     ADDRESS OF IRIS-LK-POINTER(12)
    params.irisPcbPtr12.setAddress(
        params.irisLkCells.filler403AtIndex(11).irisLkPointer.getAddress());
    // COB(901): SET ADDRESS OF IRIS-PCB-PTR-13  TO
    // COB(901):     ADDRESS OF IRIS-LK-POINTER(13)
    params.irisPcbPtr13.setAddress(
        params.irisLkCells.filler403AtIndex(12).irisLkPointer.getAddress());
    // COB(903): SET ADDRESS OF IRIS-PCB-PTR-14  TO
    // COB(903):     ADDRESS OF IRIS-LK-POINTER(14)
    params.irisPcbPtr14.setAddress(
        params.irisLkCells.filler403AtIndex(13).irisLkPointer.getAddress());
    // COB(905): SET ADDRESS OF IRIS-PCB-PTR-15  TO
    // COB(905):     ADDRESS OF IRIS-LK-POINTER(15)
    params.irisPcbPtr15.setAddress(
        params.irisLkCells.filler403AtIndex(14).irisLkPointer.getAddress());
    // COB(907): SET ADDRESS OF IRIS-PCB-PTR-16  TO
    // COB(907):     ADDRESS OF IRIS-LK-POINTER(16)
    params.irisPcbPtr16.setAddress(
        params.irisLkCells.filler403AtIndex(15).irisLkPointer.getAddress());
    // COB(909): SET ADDRESS OF IRIS-PCB-PTR-17  TO
    // COB(909):     ADDRESS OF IRIS-LK-POINTER(17)
    params.irisPcbPtr17.setAddress(
        params.irisLkCells.filler403AtIndex(16).irisLkPointer.getAddress());
    // COB(911): SET ADDRESS OF IRIS-PCB-PTR-18  TO
    // COB(911):     ADDRESS OF IRIS-LK-POINTER(18)
    params.irisPcbPtr18.setAddress(
        params.irisLkCells.filler403AtIndex(17).irisLkPointer.getAddress());
    // COB(913): SET ADDRESS OF IRIS-PCB-PTR-19  TO
    // COB(913):     ADDRESS OF IRIS-LK-POINTER(19)
    params.irisPcbPtr19.setAddress(
        params.irisLkCells.filler403AtIndex(18).irisLkPointer.getAddress());
    // COB(915): SET ADDRESS OF IRIS-PCB-PTR-20  TO
    // COB(915):     ADDRESS OF IRIS-LK-POINTER(10)
    params.irisPcbPtr20.setAddress(
        params.irisLkCells.filler403AtIndex(9).irisLkPointer.getAddress());
    // COB(917): SET ADDRESS OF IRIS-PCB-PTR-21  TO
    // COB(917):     ADDRESS OF IRIS-LK-POINTER(21)
    params.irisPcbPtr21.setAddress(
        params.irisLkCells.filler403AtIndex(20).irisLkPointer.getAddress());
    // COB(919): SET ADDRESS OF IRIS-PCB-PTR-22  TO
    // COB(919):     ADDRESS OF IRIS-LK-POINTER(22)
    params.irisPcbPtr22.setAddress(
        params.irisLkCells.filler403AtIndex(21).irisLkPointer.getAddress());
    // COB(921): SET ADDRESS OF IRIS-PCB-PTR-23  TO
    // COB(921):     ADDRESS OF IRIS-LK-POINTER(23)
    params.irisPcbPtr23.setAddress(
        params.irisLkCells.filler403AtIndex(22).irisLkPointer.getAddress());
    // COB(923): SET ADDRESS OF IRIS-PCB-PTR-24  TO
    // COB(923):     ADDRESS OF IRIS-LK-POINTER(24)
    params.irisPcbPtr24.setAddress(
        params.irisLkCells.filler403AtIndex(23).irisLkPointer.getAddress());
    // COB(925): SET ADDRESS OF IRIS-PCB-PTR-25  TO
    // COB(925):     ADDRESS OF IRIS-LK-POINTER(25)
    params.irisPcbPtr25.setAddress(
        params.irisLkCells.filler403AtIndex(24).irisLkPointer.getAddress());
    // COB(927): SET ADDRESS OF IRIS-PCB-PTR-26  TO
    // COB(927):     ADDRESS OF IRIS-LK-POINTER(26)
    params.irisPcbPtr26.setAddress(
        params.irisLkCells.filler403AtIndex(25).irisLkPointer.getAddress());
    // COB(929): SET ADDRESS OF IRIS-PCB-PTR-27  TO
    // COB(929):     ADDRESS OF IRIS-LK-POINTER(27)
    params.irisPcbPtr27.setAddress(
        params.irisLkCells.filler403AtIndex(26).irisLkPointer.getAddress());
    // COB(931): SET ADDRESS OF IRIS-PCB-PTR-28  TO
    // COB(931):     ADDRESS OF IRIS-LK-POINTER(28)
    params.irisPcbPtr28.setAddress(
        params.irisLkCells.filler403AtIndex(27).irisLkPointer.getAddress());
    // COB(933): SET ADDRESS OF IRIS-PCB-PTR-29  TO
    // COB(933):     ADDRESS OF IRIS-LK-POINTER(29)
    params.irisPcbPtr29.setAddress(
        params.irisLkCells.filler403AtIndex(28).irisLkPointer.getAddress());
    // COB(935): SET ADDRESS OF IRIS-PCB-PTR-30  TO
    // COB(935):     ADDRESS OF IRIS-LK-POINTER(30)
    params.irisPcbPtr30.setAddress(
        params.irisLkCells.filler403AtIndex(29).irisLkPointer.getAddress());
    // COB(937): SET ADDRESS OF IRIS-PCB-PTR-31  TO
    // COB(937):     ADDRESS OF IRIS-LK-POINTER(31)
    params.irisPcbPtr31.setAddress(
        params.irisLkCells.filler403AtIndex(30).irisLkPointer.getAddress());
    // COB(939): SET ADDRESS OF IRIS-PCB-PTR-32  TO
    // COB(939):     ADDRESS OF IRIS-LK-POINTER(32)
    params.irisPcbPtr32.setAddress(
        params.irisLkCells.filler403AtIndex(31).irisLkPointer.getAddress());
    // COB(941): SET ADDRESS OF IRIS-PCB-PTR-33  TO
    // COB(941):     ADDRESS OF IRIS-LK-POINTER(33)
    params.irisPcbPtr33.setAddress(
        params.irisLkCells.filler403AtIndex(32).irisLkPointer.getAddress());
    // COB(943): SET ADDRESS OF IRIS-PCB-PTR-34  TO
    // COB(943):     ADDRESS OF IRIS-LK-POINTER(34)
    params.irisPcbPtr34.setAddress(
        params.irisLkCells.filler403AtIndex(33).irisLkPointer.getAddress());
    // COB(945): SET ADDRESS OF IRIS-PCB-PTR-35  TO
    // COB(945):     ADDRESS OF IRIS-LK-POINTER(35)
    params.irisPcbPtr35.setAddress(
        params.irisLkCells.filler403AtIndex(34).irisLkPointer.getAddress());
    // COB(947): SET ADDRESS OF IRIS-PCB-PTR-36  TO
    // COB(947):     ADDRESS OF IRIS-LK-POINTER(36)
    params.irisPcbPtr36.setAddress(
        params.irisLkCells.filler403AtIndex(35).irisLkPointer.getAddress());
    // COB(949): SET ADDRESS OF IRIS-PCB-PTR-37  TO
    // COB(949):     ADDRESS OF IRIS-LK-POINTER(37)
    params.irisPcbPtr37.setAddress(
        params.irisLkCells.filler403AtIndex(36).irisLkPointer.getAddress());
    // COB(951): SET ADDRESS OF IRIS-PCB-PTR-38  TO
    // COB(951):     ADDRESS OF IRIS-LK-POINTER(38)
    params.irisPcbPtr38.setAddress(
        params.irisLkCells.filler403AtIndex(37).irisLkPointer.getAddress());
    // COB(953): SET ADDRESS OF IRIS-PCB-PTR-39  TO
    // COB(953):     ADDRESS OF IRIS-LK-POINTER(39)
    params.irisPcbPtr39.setAddress(
        params.irisLkCells.filler403AtIndex(38).irisLkPointer.getAddress());
    // COB(955): SET ADDRESS OF IRIS-PCB-PTR-40  TO
    // COB(955):     ADDRESS OF IRIS-LK-POINTER(40)
    params.irisPcbPtr40.setAddress(
        params.irisLkCells.filler403AtIndex(39).irisLkPointer.getAddress());
    // COB(957): SET ADDRESS OF IRIS-PCB-PTR-41  TO
    // COB(957):     ADDRESS OF IRIS-LK-POINTER(41)
    params.irisPcbPtr41.setAddress(
        params.irisLkCells.filler403AtIndex(40).irisLkPointer.getAddress());
    // COB(959): SET ADDRESS OF IRIS-PCB-PTR-42  TO
    // COB(959):     ADDRESS OF IRIS-LK-POINTER(42)
    params.irisPcbPtr42.setAddress(
        params.irisLkCells.filler403AtIndex(41).irisLkPointer.getAddress());
    // COB(961): SET ADDRESS OF IRIS-PCB-PTR-43  TO
    // COB(961):     ADDRESS OF IRIS-LK-POINTER(43)
    params.irisPcbPtr43.setAddress(
        params.irisLkCells.filler403AtIndex(42).irisLkPointer.getAddress());
    // COB(963): SET ADDRESS OF IRIS-PCB-PTR-44  TO
    // COB(963):     ADDRESS OF IRIS-LK-POINTER(44)
    params.irisPcbPtr44.setAddress(
        params.irisLkCells.filler403AtIndex(43).irisLkPointer.getAddress());
    // COB(965): SET ADDRESS OF IRIS-PCB-PTR-45  TO
    // COB(965):     ADDRESS OF IRIS-LK-POINTER(45)
    params.irisPcbPtr45.setAddress(
        params.irisLkCells.filler403AtIndex(44).irisLkPointer.getAddress());
  }

  /***
   ******************************************************************
   *    EXTRACT PCB WHEN EXEC DLI CASE
   ******************************************************************
   */
  protected void extractPcbExec() {
    // COB(979): MOVE 'EXTRACT-PCB-EXEC' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("EXTRACT-PCB-EXEC");
    // COB(981): SET ADDRESS OF LK-DIB-BLOCK TO ADDRESS OF IRIS-LK-DIB-BLOCK
    params.lkDibBlock.setAddress(params.irisLkDibBlock.getAddress());
    // COB(982): MOVE LK-DIB-BLOCK TO IRIS-DIB-BLOCK
    ws.iriscomm.irisDibBlock.setValue(params.lkDibBlock);
    // COB(983): IF IRIS-BLL-POINTER(1) IS NOT NULL
    if (!ws.irisBllCells.irisBllCellAtIndex(0).irisBllPointer.equals(0)) {
      // COB(984): SET ADDRESS OF IRIS-DB-PCB TO IRIS-BLL-POINTER(1)
      params.irisDbPcb.setAddress(ws.irisBllCells.irisBllCellAtIndex(0).irisBllPointer);
      // COB(985): END-IF
    }
  }
}
