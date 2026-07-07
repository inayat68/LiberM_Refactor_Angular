package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.dbpautp0.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.esql.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;

public class DBPAUTP0 extends AbstractProgram {

  private enum Flow {
    Exit,
    // MAIN-PROGRAM
    mainProgram,
    // SKIP-FUNCTION
    skipFunction
  }

  private Flow rcNext;

  private final Sqlca sqlca = new Sqlca(this);
  private NSqlCursor pautsum0Crs;
  private NSqlCursor pautdtl1Crs;
  @ProgramStorage final Dbpautp0Working ws = new Dbpautp0Working();

  // Linkage
  public static class Dbpautp0Linkage extends NGroup {
    // COB:        01 IRIS-WORK-AREA.
    public IrisWorkArea irisWorkArea = new IrisWorkArea();

    public static class IrisWorkArea extends NGroup {

      // COB:          03 IRIS-PROGRAM-NAME             PIC X(8)  VALUE :PROGNM:.
      public NChar irisProgramName = new NChar(8).initial("DBPAUTP0");
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
      public static class IrisMsgDetail extends NGroup {
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
      public static class IrisErrorDescr extends NGroup {
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
      public static class Filler179 extends NGroup {
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
      public static class IrisSqlError extends NGroup {
        // COB:            05  IRIS-SQLCODE                         PIC S9(9) COMP.
        public NInt32 irisSqlcode = new NInt32();

        // COB:            05  IRIS-SQLERRM.
        public static class IrisSqlerrm extends NGroup {
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
      public static class IrisKeyvalueTab extends NGroup {
        // COB:            05 IRIS-CCODE-LEVELS           OCCURS 16.
        public static class IrisCcodeLevels extends NGroup {
          // COB:              07 IRIS-CCODE-VALUES.
          public static class IrisCcodeValues extends NGroup {
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
        public static class IrisKeysLevels extends NGroup {
          // COB: SAN          07 IRIS-KEYVALUES-TAB        PIC X(8192).
          public NChar irisKeyvaluesTab = new NChar(8192);

          // COB: SAN          07 IRIS-KEYVALUES-RED1 REDEFINES IRIS-KEYVALUES-TAB.
          public static class IrisKeyvaluesRed1 extends NGroup {
            // COB: SAN            09 IRIS-KEYVALUES          OCCURS 64.
            public static class IrisKeyvalues extends NGroup {
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
          public static class IrisKeyvaluesRed2 extends NGroup {
            // COB: SAN            09 IRIS-KEYVALUES-P        OCCURS 64.
            public static class IrisKeyvaluesP extends NGroup {
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
      public static class IrisAuditFields extends NGroup {
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
      public static class IrisChkpFields extends NGroup {
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
      public static class IrisPcbTypeTable extends NGroup {
        // COB:            05 FILLER                      OCCURS 100.
        public static class Filler355 extends NGroup {
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
      public static class IrisPcbDbdTable extends NGroup {
        // COB:            05 FILLER                      OCCURS 100.
        public static class Filler390 extends NGroup {
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

    // COB:        01 IRIS-DB-PCB.
    public IrisDbPcb irisDbPcb = new IrisDbPcb();

    public static class IrisDbPcb extends NGroup {

      // COB:          03 DB-PCB-FIXED-PART.
      public static class DbPcbFixedPart extends NGroup {
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
      public static class DbRntCntlArea extends NGroup {
        // COB:            05 DB-RNT-CNTL-AREA-FIXED      PIC X(1000).
        public NChar dbRntCntlAreaFixed = new NChar(1000);

        // COB:            05 FILLER REDEFINES DB-RNT-CNTL-AREA-FIXED.
        public static class Filler47 extends NGroup {
          // COB:              07 FILLER                    PIC X(781).
          public NChar filler48 = new NChar(781);
          // COB:              07 RUN-IMS-DUAL-POINTER-IO   POINTER.
          public NPointer runImsDualPointerIo = new NPointer();
          // COB:              07 FILLER                    PIC X(215).
          public NChar filler50 = new NChar(215);
        }

        public Filler47 filler47 = (Filler47) new Filler47().redefines(dbRntCntlAreaFixed);
        // COB:            05 DB-RNT-CNTL-AREA-VAR        PIC X(32000).
        public NChar dbRntCntlAreaVar = new NChar(32000);
      }

      public DbRntCntlArea dbRntCntlArea = new DbRntCntlArea();

      // COB:        03 IO-RTN-USED-KEYS-PCB-AREA REDEFINES DB-RNT-CNTL-AREA.
      public static class IoRtnUsedKeysPcbArea extends NGroup {
        // COB:          05 IO-RTN-USED-KEYS-AREA.
        public static class IoRtnUsedKeysArea extends NGroup {
          // COB:            07 IO-RTN-FB-KEY-MAX-LENGTH           PIC S9(9) COMP.
          public NInt32 ioRtnFbKeyMaxLength = new NInt32();
          // COB:            07 IO-RTN-USED-KEY-SECONDARY          PIC X(255).
          public NChar ioRtnUsedKeySecondary = new NChar(255);
          // COB:            07 IO-RTN-USED-KEY-GNUNQ-NEXT-SEG     PIC X(8).
          public NChar ioRtnUsedKeyGnunqNextSeg = new NChar(8);
          // COB:            07 IO-RTN-USED-KEY-GNUNQ-SAVE-POS     PIC X(512).
          public NChar ioRtnUsedKeyGnunqSavePos = new NChar(512);
          // COB:            07 IO-RTN-USED-KEY-GNUNQ-NEXT-ACT REDEFINES
          // COB:               IO-RTN-USED-KEY-GNUNQ-SAVE-POS       PIC X(20).
          public NChar ioRtnUsedKeyGnunqNextAct = new NChar(20).redefines(ioRtnUsedKeyGnunqSavePos);

          // COB:              88 IO-RTN-USED-KEY-GNUNQ-SEEK
          public boolean ioRtnUsedKeyGnunqSeek() {
            return ioRtnUsedKeyGnunqNextAct.equals("SQL-SELECT-SEEK     ");
          }

          public void setIoRtnUsedKeyGnunqSeek(boolean value) {
            if (value) ioRtnUsedKeyGnunqNextAct.setValue("SQL-SELECT-SEEK     ");
          }

          // COB:              88 IO-RTN-USED-KEY-GNUNQ-NEXT
          public boolean ioRtnUsedKeyGnunqNext() {
            return ioRtnUsedKeyGnunqNextAct.equals("SQL-SELECT-NEXT     ");
          }

          public void setIoRtnUsedKeyGnunqNext(boolean value) {
            if (value) ioRtnUsedKeyGnunqNextAct.setValue("SQL-SELECT-NEXT     ");
          }

          // COB:            07 RUN-DBD-STATUS                     PIC S9(4) COMP.
          public NInt16 runDbdStatus = new NInt16();

          // COB:              88 DUAL-IMS-ONLY                    VALUE 1.
          public boolean dualImsOnly() {
            return runDbdStatus.equals(1);
          }

          public void setDualImsOnly(boolean value) {
            if (value) runDbdStatus.setValue(1);
          }

          // COB:              88 DUAL-SQL-ONLY                    VALUE 2.
          public boolean dualSqlOnly() {
            return runDbdStatus.equals(2);
          }

          public void setDualSqlOnly(boolean value) {
            if (value) runDbdStatus.setValue(2);
          }

          // COB:              88 DUAL-BOTH                        VALUE 3 6.
          public boolean dualBoth() {
            return runDbdStatus.equals(3) || runDbdStatus.equals(6);
          }

          public void setDualBoth(boolean value) {
            if (value) runDbdStatus.setValue(3);
          }

          // COB:              88 DUAL-UPDATE-ONLY                 VALUE 4.
          public boolean dualUpdateOnly() {
            return runDbdStatus.equals(4);
          }

          public void setDualUpdateOnly(boolean value) {
            if (value) runDbdStatus.setValue(4);
          }

          // COB:              88 DUAL-BOTH-IMS-PRIMARY            VALUE 5 7.
          public boolean dualBothImsPrimary() {
            return runDbdStatus.equals(5) || runDbdStatus.equals(7);
          }

          public void setDualBothImsPrimary(boolean value) {
            if (value) runDbdStatus.setValue(5);
          }

          // COB:              88 DUAL-BOTH-NO-ABEND               VALUE 6.
          public boolean dualBothNoAbend() {
            return runDbdStatus.equals(6);
          }

          public void setDualBothNoAbend(boolean value) {
            if (value) runDbdStatus.setValue(6);
          }

          // COB:              88 DUAL-BOTH-IMS-PR-NO-ABEND        VALUE 7.
          public boolean dualBothImsPrNoAbend() {
            return runDbdStatus.equals(7);
          }

          public void setDualBothImsPrNoAbend(boolean value) {
            if (value) runDbdStatus.setValue(7);
          }

          // COB:            07 RUN-IMS-DUAL-POINTER               POINTER.
          public NPointer runImsDualPointer = new NPointer();
          // COB:            07 RUN-PCB-INDEX                      PIC 9(3).
          public NZoned runPcbIndex = new NZoned(3, false);
          // COB:            07 RUN-IO-AREA-PTR                    USAGE POINTER.
          public NPointer runIoAreaPtr = new NPointer();
          // COB:            07 INIT-DBD-STATUS                    PIC S9(4) COMP.
          public NInt16 initDbdStatus = new NInt16();

          // COB:              88 INIT-DUAL-IMS-ONLY               VALUE 1.
          public boolean initDualImsOnly() {
            return initDbdStatus.equals(1);
          }

          public void setInitDualImsOnly(boolean value) {
            if (value) initDbdStatus.setValue(1);
          }

          // COB:              88 INIT-DUAL-SQL-ONLY               VALUE 2.
          public boolean initDualSqlOnly() {
            return initDbdStatus.equals(2);
          }

          public void setInitDualSqlOnly(boolean value) {
            if (value) initDbdStatus.setValue(2);
          }

          // COB:              88 INIT-DUAL-BOTH                   VALUE 3 6.
          public boolean initDualBoth() {
            return initDbdStatus.equals(3) || initDbdStatus.equals(6);
          }

          public void setInitDualBoth(boolean value) {
            if (value) initDbdStatus.setValue(3);
          }

          // COB:              88 INIT-DUAL-UPDATE-ONLY            VALUE 4.
          public boolean initDualUpdateOnly() {
            return initDbdStatus.equals(4);
          }

          public void setInitDualUpdateOnly(boolean value) {
            if (value) initDbdStatus.setValue(4);
          }

          // COB:              88 INIT-DUAL-BOTH-IMS-PRIMARY       VALUE 5 7.
          public boolean initDualBothImsPrimary() {
            return initDbdStatus.equals(5) || initDbdStatus.equals(7);
          }

          public void setInitDualBothImsPrimary(boolean value) {
            if (value) initDbdStatus.setValue(5);
          }

          // COB:              88 INIT-DUAL-BOTH-NO-ABEND          VALUE 6.
          public boolean initDualBothNoAbend() {
            return initDbdStatus.equals(6);
          }

          public void setInitDualBothNoAbend(boolean value) {
            if (value) initDbdStatus.setValue(6);
          }

          // COB:              88 INIT-DUAL-BOTH-IMS-PR-NO-ABEND   VALUE 7.
          public boolean initDualBothImsPrNoAbend() {
            return initDbdStatus.equals(7);
          }

          public void setInitDualBothImsPrNoAbend(boolean value) {
            if (value) initDbdStatus.setValue(7);
          }

          // COB:            07 RUN-LAST-DYN-SSA-FUNCID            PIC S9(9) COMP.
          public NInt32 runLastDynSsaFuncid = new NInt32();
          // COB:            07 RUN-CHECK-MISMATCH                 PIC X.
          public NChar runCheckMismatch = new NChar(1);

          // COB:              88 RUN-EXEC-CHECK-MISMATCH          VALUE '0'.
          public boolean runExecCheckMismatch() {
            return runCheckMismatch.equals("0");
          }

          public void setRunExecCheckMismatch(boolean value) {
            if (value) runCheckMismatch.setValue("0");
          }

          // COB:              88 RUN-SKIP-CHECK-MISMATCH          VALUE '1'.
          public boolean runSkipCheckMismatch() {
            return runCheckMismatch.equals("1");
          }

          public void setRunSkipCheckMismatch(boolean value) {
            if (value) runCheckMismatch.setValue("1");
          }

          // COB:            07 RUN-DLI-ACCESS                     PIC X.
          public NChar runDliAccess = new NChar(1);

          // COB:              88 RUN-EXEC-DLI-ACCESS              VALUE '0'.
          public boolean runExecDliAccess() {
            return runDliAccess.equals("0");
          }

          public void setRunExecDliAccess(boolean value) {
            if (value) runDliAccess.setValue("0");
          }

          // COB:              88 RUN-SKIP-DLI-ACCESS              VALUE '1'.
          public boolean runSkipDliAccess() {
            return runDliAccess.equals("1");
          }

          public void setRunSkipDliAccess(boolean value) {
            if (value) runDliAccess.setValue("1");
          }

          // COB:            07 FILLER                             PIC X(200).
          public NChar filler543 = new NChar(200);

          // COB:            07 IO-RTN-USED-KEY-VALUE OCCURS 10.
          public static class IoRtnUsedKeyValue extends NGroup {
            // COB:              09 IO-RTN-USED-SSA-INFO             PIC X(1055).
            public NChar ioRtnUsedSsaInfo = new NChar(1055);

            // COB:              09 IO-RTN-USED-SSA-INPUT-KEYS       REDEFINES
            // COB:                 IO-RTN-USED-SSA-INFO.
            public static class IoRtnUsedSsaInputKeys extends NGroup {
              // COB:                11 IO-RTN-IRIS-KEYVALUES          OCCURS 16.
              public static class IoRtnIrisKeyvalues extends NGroup {
                // COB:                  13 IO-RTN-IRIS-KEYVALUE         PIC X(80).
                public NChar ioRtnIrisKeyvalue = new NChar(80);
              }

              public IoRtnIrisKeyvalues[] ioRtnIrisKeyvalues =
                  AbstractNField.occurs(16, new IoRtnIrisKeyvalues());

              public IoRtnIrisKeyvalues ioRtnIrisKeyvaluesAtIndex(int index) {
                return getOccursInstance(ioRtnIrisKeyvalues, index);
              }
            }

            public IoRtnUsedSsaInputKeys ioRtnUsedSsaInputKeys =
                (IoRtnUsedSsaInputKeys) new IoRtnUsedSsaInputKeys().redefines(ioRtnUsedSsaInfo);

            // COB: SAN          09 IO-RTN-USED-SSA-INPUT-KEYS-2     REDEFINES
            // COB: SAN             IO-RTN-USED-SSA-INFO.
            public static class IoRtnUsedSsaInputKeys2 extends NGroup {
              // COB: SAN            11 IO-RTN-IRIS-KEYVALUES-P        OCCURS 16.
              public static class IoRtnIrisKeyvaluesP extends NGroup {
                // COB: SAN              13 IO-RTN-IRIS-KEYVALUE-PACKED  PIC S9(18) COMP-3.
                public NPacked ioRtnIrisKeyvaluePacked = new NPacked(10);
                // COB: SAN              13 FILLER                       PIC X(60).
                public NChar filler557 = new NChar(60);
              }

              public IoRtnIrisKeyvaluesP[] ioRtnIrisKeyvaluesP =
                  AbstractNField.occurs(16, new IoRtnIrisKeyvaluesP());

              public IoRtnIrisKeyvaluesP ioRtnIrisKeyvaluesPAtIndex(int index) {
                return getOccursInstance(ioRtnIrisKeyvaluesP, index);
              }
            }

            public IoRtnUsedSsaInputKeys2 ioRtnUsedSsaInputKeys2 =
                (IoRtnUsedSsaInputKeys2) new IoRtnUsedSsaInputKeys2().redefines(ioRtnUsedSsaInfo);
            // COB:              09 IO-RTN-USED-KEY-STATUS           PIC X.
            public NChar ioRtnUsedKeyStatus = new NChar(1);

            // COB:                88 IO-RTN-USED-KEY-NOT-CHANGED    VALUE '0' X'00' ' '.
            public boolean ioRtnUsedKeyNotChanged() {
              return ioRtnUsedKeyStatus.equals("0")
                  || ioRtnUsedKeyStatus.equals(hexToString("00"))
                  || ioRtnUsedKeyStatus.equals(" ");
            }

            public void setIoRtnUsedKeyNotChanged(boolean value) {
              if (value) ioRtnUsedKeyStatus.setValue("0");
            }

            // COB:                88 IO-RTN-USED-KEY-CHANGED        VALUE '1'.
            public boolean ioRtnUsedKeyChanged() {
              return ioRtnUsedKeyStatus.equals("1");
            }

            public void setIoRtnUsedKeyChanged(boolean value) {
              if (value) ioRtnUsedKeyStatus.setValue("1");
            }

            // COB:                88 IO-RTN-USED-KEY-IS-INDEX       VALUE '2'.
            public boolean ioRtnUsedKeyIsIndex() {
              return ioRtnUsedKeyStatus.equals("2");
            }

            public void setIoRtnUsedKeyIsIndex(boolean value) {
              if (value) ioRtnUsedKeyStatus.setValue("2");
            }

            // COB:                88 IO-RTN-USED-KEY-DUPREC         VALUE '3'.
            public boolean ioRtnUsedKeyDuprec() {
              return ioRtnUsedKeyStatus.equals("3");
            }

            public void setIoRtnUsedKeyDuprec(boolean value) {
              if (value) ioRtnUsedKeyStatus.setValue("3");
            }

            // COB:              09 IO-RTN-USED-SSA-KEYS.
            public static class IoRtnUsedSsaKeys extends NGroup {
              // COB:                11 IO-RTN-USED-KEY-ALPHA          PIC X(256).
              public NChar ioRtnUsedKeyAlpha = new NChar(256);
              // COB:                11 IO-RTN-USED-KEY-NUMERIC        PIC S9(9) COMP.
              public NInt32 ioRtnUsedKeyNumeric = new NInt32();
              // COB:                11 IO-RTN-USED-KEY-NUMERIC-PREV   PIC S9(9) COMP.
              public NInt32 ioRtnUsedKeyNumericPrev = new NInt32();
              // COB:                11 IO-RTN-USED-KEY-NUMERIC-NEXT   PIC S9(9) COMP.
              public NInt32 ioRtnUsedKeyNumericNext = new NInt32();
              // COB:                11 IO-RTN-USED-KEY-PARENT-ALPHA   PIC X(256).
              public NChar ioRtnUsedKeyParentAlpha = new NChar(256);
              // COB:                11 IO-RTN-USED-KEY-PARENT-NUMERIC PIC S9(9) COMP.
              public NInt32 ioRtnUsedKeyParentNumeric = new NInt32();

              // COB:                11 FILLER OCCURS 32.
              public static class Filler575 extends NGroup {
                // COB:                  13 IO-RTN-USED-KEY-LAST-SX      PIC S9(9) COMP.
                public NInt32 ioRtnUsedKeyLastSx = new NInt32();
              }

              public Filler575[] filler575 = AbstractNField.occurs(32, new Filler575());

              public Filler575 filler575AtIndex(int index) {
                return getOccursInstance(filler575, index);
              }

              // COB:                11 IO-RTN-USED-LAST-SEGMENT       PIC X(8).
              public NChar ioRtnUsedLastSegment = new NChar(8);
              // COB:                11 IO-RTN-LAST-OPEN-CURSOR        PIC 9(8).
              public NZoned ioRtnLastOpenCursor = new NZoned(8, false);
              // COB:                11 FILLER                         PIC X(272).
              public NChar filler579 = new NChar(272);
            }

            public IoRtnUsedSsaKeys ioRtnUsedSsaKeys = new IoRtnUsedSsaKeys();

            // COB: SAN          09 IO-RTN-USED-SSA-KEYS2 REDEFINES IO-RTN-USED-SSA-KEYS.
            public static class IoRtnUsedSsaKeys2 extends NGroup {
              // COB: SAN            11 IO-RTN-USED-KEY-PACKED         PIC S9(18) COMP-3.
              public NPacked ioRtnUsedKeyPacked = new NPacked(10);
            }

            public IoRtnUsedSsaKeys2 ioRtnUsedSsaKeys2 =
                (IoRtnUsedSsaKeys2) new IoRtnUsedSsaKeys2().redefines(ioRtnUsedSsaKeys);
          }

          public IoRtnUsedKeyValue[] ioRtnUsedKeyValue =
              AbstractNField.occurs(10, new IoRtnUsedKeyValue());

          public IoRtnUsedKeyValue ioRtnUsedKeyValueAtIndex(int index) {
            return getOccursInstance(ioRtnUsedKeyValue, index);
          }
        }

        public IoRtnUsedKeysArea ioRtnUsedKeysArea = new IoRtnUsedKeysArea();
        // COB:          05 IO-RTN-COMM-DATA                     PIC X(16000).
        public NChar ioRtnCommData = new NChar(16000);

        // COB:          05 FILLER REDEFINES IO-RTN-COMM-DATA.
        public static class Filler584 extends NGroup {
          // COB:            07 LAST-IMS-FUNCTION                  PIC X(4).
          public NChar lastImsFunction = new NChar(4);

          // COB:              88 LAST-IMS-FUNCTION-READ           VALUE 'GU  '
          public boolean lastImsFunctionRead() {
            return lastImsFunction.equals("GU  ")
                || lastImsFunction.equals("GN  ")
                || lastImsFunction.equals("GNP ");
          }

          public void setLastImsFunctionRead(boolean value) {
            if (value) lastImsFunction.setValue("GU  ");
          }

          // COB:              88 LAST-IMS-FUNCTION-READHLD        VALUE 'GHU '
          public boolean lastImsFunctionReadhld() {
            return lastImsFunction.equals("GHU ")
                || lastImsFunction.equals("GHN ")
                || lastImsFunction.equals("GHNP");
          }

          public void setLastImsFunctionReadhld(boolean value) {
            if (value) lastImsFunction.setValue("GHU ");
          }

          // COB:            07 LAST-IMS-SEGMENT-NAME              PIC X(8).
          public NChar lastImsSegmentName = new NChar(8);
          // COB:            07 LAST-IMS-SEGMENT-LEVEL             PIC 9(9) COMP.
          public NInt32 lastImsSegmentLevel = new NInt32();

          // COB:            07 LAST-SSA.
          public static class LastSsa extends NGroup {
            // COB:              09 LAST-SSA-SEGMENT                 PIC X(8).
            public NChar lastSsaSegment = new NChar(8);
            // COB:              09 LAST-SSA-DATA                    PIC X(248).
            public NChar lastSsaData = new NChar(248);
          }

          public LastSsa lastSsa = new LastSsa();
          // COB:            07 FILLER REDEFINES LAST-SSA          PIC X(256).
          public NChar filler597 = new NChar(256).redefines(lastSsa);

          // COB:              88 LAST-SSA-EMPTY                   VALUE SPACE
          public boolean lastSsaEmpty() {
            return filler597.isSpaces() || filler597.isLowValues() || filler597.isHighValues();
          }

          public void setLastSsaEmpty(boolean value) {
            if (value) filler597.setValue(SPACE);
          }

          // COB:            07 LAST-IMS-CCODE                     PIC X(240).
          public NChar lastImsCcode = new NChar(240);
        }

        public Filler584 filler584 = (Filler584) new Filler584().redefines(ioRtnCommData);
      }

      public IoRtnUsedKeysPcbArea ioRtnUsedKeysPcbArea =
          (IoRtnUsedKeysPcbArea) new IoRtnUsedKeysPcbArea().redefines(dbRntCntlArea);
    }

    // COB:        01 LK-IO-AREA                    PIC X(32000).
    public NChar lkIoArea = new NChar(32000);

    // COB:        01 LK-LOAD-DICTIONARY-AREA REDEFINES LK-IO-AREA.
    public static class LkLoadDictionaryArea extends NGroup {
      // COB:          03 LK-LOAD-DICTIONARY-PTR      POINTER.
      public NPointer lkLoadDictionaryPtr = new NPointer();
    }

    public LkLoadDictionaryArea lkLoadDictionaryArea =
        (LkLoadDictionaryArea) new LkLoadDictionaryArea().redefines(lkIoArea);
    // COB:        01 LK-SSA-01                     PIC X(32000).
    public NChar lkSsa01 = new NChar(32000);
    // COB:        01 LK-SSA-02                     PIC X(32000).
    public NChar lkSsa02 = new NChar(32000);
    // COB:        01 LK-SSA-03                     PIC X(32000).
    public NChar lkSsa03 = new NChar(32000);
    // COB:        01 LK-SSA-04                     PIC X(32000).
    public NChar lkSsa04 = new NChar(32000);
    // COB:        01 LK-SSA-05                     PIC X(32000).
    public NChar lkSsa05 = new NChar(32000);
    // COB:        01 LK-SSA-06                     PIC X(32000).
    public NChar lkSsa06 = new NChar(32000);
    // COB:        01 LK-SSA-07                     PIC X(32000).
    public NChar lkSsa07 = new NChar(32000);
    // COB:        01 LK-SSA-08                     PIC X(32000).
    public NChar lkSsa08 = new NChar(32000);
    // COB:        01 LK-SSA-09                     PIC X(32000).
    public NChar lkSsa09 = new NChar(32000);
    // COB:        01 LK-SSA-10                     PIC X(32000).
    public NChar lkSsa10 = new NChar(32000);
    // COB:        01 LK-SSA-11                     PIC X(32000).
    public NChar lkSsa11 = new NChar(32000);
    // COB:        01 LK-SSA-12                     PIC X(32000).
    public NChar lkSsa12 = new NChar(32000);
    // COB:        01 LK-SSA-13                     PIC X(32000).
    public NChar lkSsa13 = new NChar(32000);
    // COB:        01 LK-SSA-14                     PIC X(32000).
    public NChar lkSsa14 = new NChar(32000);
    // COB:        01 LK-SSA-15                     PIC X(32000).
    public NChar lkSsa15 = new NChar(32000);
    // COB:        01 LK-SSA-16                     PIC X(32000).
    public NChar lkSsa16 = new NChar(32000);
    // COB:        01 LK-DUAL-PCB.
    public LkDualPcb lkDualPcb = new LkDualPcb();

    public static class LkDualPcb extends NGroup {

      // COB:          03 DUAL-PCB-FIXED-PART.
      public static class DualPcbFixedPart extends NGroup {
        // COB:            05 DUAL-DBD-NAME             PIC X(8).
        public NChar dualDbdName = new NChar(8);
        // COB:            05 DUAL-SEGMENT-LEVEL        PIC 9(2).
        public NZoned dualSegmentLevel = new NZoned(2, false);
        // COB:            05 DUAL-STATUS-CODE          PIC X(2).
        public NChar dualStatusCode = new NChar(2);

        // COB:              88 DUAL-STATUS-CODE-OK     VALUE SPACE.
        public boolean dualStatusCodeOk() {
          return dualStatusCode.isSpaces();
        }

        public void setDualStatusCodeOk(boolean value) {
          if (value) dualStatusCode.setValue(SPACE);
        }

        // COB:            05 DUAL-PROC-OPTS            PIC X(4).
        public NChar dualProcOpts = new NChar(4);
        // COB:            05 DUAL-INTERNAL-INDEX       PIC S9(9)  COMP.
        public NInt32 dualInternalIndex = new NInt32();
        // COB:            05 DUAL-SEGMENT-NAME         PIC X(8).
        public NChar dualSegmentName = new NChar(8);
        // COB:            05 DUAL-FB-KEY-LENGTH        PIC S9(9)  COMP.
        public NInt32 dualFbKeyLength = new NInt32();
        // COB:            05 DUAL-NUM-SENSEGS          PIC S9(9)  COMP.
        public NInt32 dualNumSensegs = new NInt32();
      }

      public DualPcbFixedPart dualPcbFixedPart = new DualPcbFixedPart();
      // COB:          03 DUAL-KEY-FB                 PIC X.
      public NChar dualKeyFb = new NChar(1);
    }

    // COB:        01 IRIS-LK-CELLS.
    public IrisLkCells irisLkCells = new IrisLkCells();

    public static class IrisLkCells extends NGroup {

      // COB:          03 FILLER                      OCCURS 100.
      public static class Filler643 extends NGroup {
        // COB:            05 IRIS-LK-POINTER           POINTER.
        public NPointer irisLkPointer = new NPointer();
      }

      public Filler643[] filler643 = AbstractNField.occurs(100, new Filler643());

      public Filler643 filler643AtIndex(int index) {
        return getOccursInstance(filler643, index);
      }
    }

    // COB:        01 LK-DIB-BLOCK                  PIC X(32).
    public NChar lkDibBlock = new NChar(32);
    // COB:        01 LK-IO-AREA-BACKUP             PIC X(32000).
    public NChar lkIoAreaBackup = new NChar(32000);
  }

  final Dbpautp0Linkage params = new Dbpautp0Linkage();

  public DBPAUTP0(Context context) {
    super(context);
  }

  @Override
  protected int procedure(AbstractNField... args) throws ContextException {
    if (args != null) {
      params.allocate();
      if (args.length > 0) params.irisWorkArea.setAddress(args[0].getAddress());
      if (args.length > 1) params.irisDbPcb.setAddress(args[1].getAddress());
      if (args.length > 2) params.lkIoArea.setAddress(args[2].getAddress());
      if (args.length > 3) params.lkSsa01.setAddress(args[3].getAddress());
      if (args.length > 4) params.lkSsa02.setAddress(args[4].getAddress());
      if (args.length > 5) params.lkSsa03.setAddress(args[5].getAddress());
      if (args.length > 6) params.lkSsa04.setAddress(args[6].getAddress());
      if (args.length > 7) params.lkSsa05.setAddress(args[7].getAddress());
      if (args.length > 8) params.lkSsa06.setAddress(args[8].getAddress());
      if (args.length > 9) params.lkSsa07.setAddress(args[9].getAddress());
      if (args.length > 10) params.lkSsa08.setAddress(args[10].getAddress());
      if (args.length > 11) params.lkSsa09.setAddress(args[11].getAddress());
      if (args.length > 12) params.lkSsa10.setAddress(args[12].getAddress());
      if (args.length > 13) params.lkSsa11.setAddress(args[13].getAddress());
      if (args.length > 14) params.lkSsa12.setAddress(args[14].getAddress());
      if (args.length > 15) params.lkSsa13.setAddress(args[15].getAddress());
      if (args.length > 16) params.lkSsa14.setAddress(args[16].getAddress());
      if (args.length > 17) params.lkSsa15.setAddress(args[17].getAddress());
      if (args.length > 18) params.lkSsa16.setAddress(args[18].getAddress());
    }
    rcNext = Flow.mainProgram;
    while (!rcNext.equals(Flow.Exit)) {
      switch (rcNext) {
        case mainProgram:
          rcNext = mainProgram();
          break;
        case skipFunction:
          skipFunction();
          rcNext = Flow.Exit;
          break;
        default:
          throw new RuntimeException("Invalid flow option: " + rcNext);
      }
    }
    return RETURN_CODE;
  }

  /***/
  protected Flow mainProgram() {
    // COB(674): IF IRIS-EXECDLI
    if (params.irisWorkArea.irisExecdli()) {
      // COB(675): PERFORM EXTRACT-PCB-EXEC
      extractPcbExec();
      // COB(676): END-IF
    }
    // COB(678): IF IRIS-TRACE-MINIMAL
    // COB(678): OR IRIS-TRACE-STANDARD
    if (params.irisWorkArea.irisTraceMinimal() || params.irisWorkArea.irisTraceStandard()) {
      // COB(680): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(681): STRING 'PGM=(' IRIS-PROGRAM-NAME ')'
      // COB(681):        ' CUSTOM FUNCTION ID=(' IRIS-CUSTOM-FUNC-ID ')'
      // COB(681):        ' CALLER ID=(' IRIS-CALL-ID ')'
      // COB(681): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
      // COB(681): POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "PGM=(",
              params.irisWorkArea.irisProgramName,
              ")",
              " CUSTOM FUNCTION ID=(",
              params.irisWorkArea.irisCustomFuncId,
              ")",
              " CALLER ID=(",
              params.irisWorkArea.irisCallId,
              ")",
              ws.iriscomm.messageEnd));
      // COB(686): MOVE 0 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
      // COB(687): SET IRISTRAC-RTN TO TRUE
      ws.iriscomm.setIristracRtn(true);
      // COB(688): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(689): END-IF
    }
    // COB(691): IF IRIS-TRACE-FULL
    // COB(691): OR IRIS-TRACE-PERFORMANCE
    if (params.irisWorkArea.irisTraceFull() || params.irisWorkArea.irisTracePerformance()) {
      // COB(693): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(694): STRING '<IRISTRACE> - DBPAUTP0:START' NL
      // COB(694):        ' DBD        =(DBPAUTP0) ' NL
      // COB(694):        ' CALLER PGM =(' IRIS-PROGRAM-NAME ') ' NL
      // COB(694):        ' CALLER ID  =(' IRIS-CALL-ID ') ' NL
      // COB(694): DELIMITED BY SIZE INTO IRIS-MSG-TXT
      // COB(694): POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0:START",
              ws.iriscomm.nl,
              " DBD        =(DBPAUTP0) ",
              ws.iriscomm.nl,
              " CALLER PGM =(",
              params.irisWorkArea.irisProgramName,
              ") ",
              ws.iriscomm.nl,
              " CALLER ID  =(",
              params.irisWorkArea.irisCallId,
              ") ",
              ws.iriscomm.nl));
      // COB(700): IF DB-PCB-IRIS-EYE
      if (params.irisDbPcb.dbPcbFixedPart.dbPcbIrisEye()) {
        // COB(701): STRING ' PCB INDEX  =(' RUN-PCB-INDEX ') ' NL
        // COB(701): DELIMITED BY SIZE INTO IRIS-MSG-TXT
        // COB(701): POINTER IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
            params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
                " PCB INDEX  =(",
                params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.runPcbIndex,
                ") ",
                ws.iriscomm.nl));
        // COB(704): END-IF
      }
      // COB(705): STRING ' IMS FUNC   =(' IRIS-IMS-FUNCTION ') ' NL
      // COB(705):        ' SEGMENT    =(' IRIS-SEGMENT ') ' NL
      // COB(705):        ' FUNCTION   =(' IRIS-FUNCTION ') ' NL
      // COB(705):        ' CUSTOM ID  =(' IRIS-CUSTOM-FUNC-ID ') '
      // COB(705): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
      // COB(705): POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              " IMS FUNC   =(",
              params.irisWorkArea.irisImsFunction,
              ") ",
              ws.iriscomm.nl,
              " SEGMENT    =(",
              params.irisWorkArea.irisSegment,
              ") ",
              ws.iriscomm.nl,
              " FUNCTION   =(",
              params.irisWorkArea.irisFunction,
              ") ",
              ws.iriscomm.nl,
              " CUSTOM ID  =(",
              params.irisWorkArea.irisCustomFuncId,
              ") ",
              ws.iriscomm.messageEnd));
      // COB(711): MOVE 0 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
      // COB(712): SET IRISTRAC-RTN TO TRUE
      ws.iriscomm.setIristracRtn(true);
      // COB(713): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(714): IF NOT IRIS-EXECDLI
      if (!params.irisWorkArea.irisExecdli()) {
        // COB(715): PERFORM PRINT-SSAS
        printSsas();
        // COB(716): END-IF
      }
      // COB(717): END-IF
    }
    // COB(719): PERFORM INIT-VARIABLES
    initVariables();
    // COB(721): EVALUATE TRUE
    // COB(722): WHEN SQL-USER-CUSTOM
    if (params.irisWorkArea.sqlUserCustom()) {
      // COB(724): IF IRIS-TRACE-PERFORMANCE
      if (params.irisWorkArea.irisTracePerformance()) {
        // COB(725): MOVE 0 TO IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
        // COB(726): STRING '<IRISTRACE> - BEFORE SQL ACCESS'
        // COB(726): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - BEFORE SQL ACCESS", ws.iriscomm.messageEnd);
        // COB(728): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(729): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(730): END-IF
      }
      // COB(731): EVALUATE IRIS-CUSTOM-FUNC-ID
      switch (params.irisWorkArea.irisCustomFuncId.getInt()) {
          // COB(732): WHEN 0
        case 0:
          // COB(733): SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
          params.irisWorkArea.setIrisErrFunctionNotFound(true);
          // COB(735): WHEN 1
          //       * IRISDB - Segm: PAUTSUM0, Op: GN, RevId: 1
          break;
        case 1:
          // COB(736): IF IRIS-FUNC-GU OR IRIS-FUNC-GHU
          // COB(736): OR WS-SEGMENT-NAME NOT =
          // COB(736):   IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
          // COB(736): OR IRIS-CODE-FIRST(PAUTSUM0-LVL)
          if (params.irisWorkArea.irisFuncGu()
              || params.irisWorkArea.irisFuncGhu()
              || !ws.wsSegmentName.equals(
                  params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(
                          ws.pautsum0Lvl.getInt() - 1)
                      .ioRtnUsedSsaKeys
                      .ioRtnUsedLastSegment)
              || params
                  .irisWorkArea
                  .irisKeyvalueTab
                  .irisCcodeLevelsAtIndex(ws.pautsum0Lvl.getInt() - 1)
                  .irisCcodeValues
                  .irisCodeFirst()) {
            // COB(740): SET SQL-SELECT-SEEK TO TRUE
            params.irisWorkArea.setSqlSelectSeek(true);
            // COB(741): ELSE
          } else {
            // COB(742): SET SQL-SELECT-NEXT TO TRUE
            params.irisWorkArea.setSqlSelectNext(true);
            // COB(743): END-IF
          }
          // COB(744): IF IO-RTN-USED-KEY-DUPREC(PAUTSUM0-LVL)
          // COB(744): AND (IRIS-FUNC-GN OR IRIS-FUNC-GHN
          // COB(744): OR IRIS-FUNC-GNP OR IRIS-FUNC-GHNP)
          if (params
                  .irisDbPcb
                  .ioRtnUsedKeysPcbArea
                  .ioRtnUsedKeysArea
                  .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
                  .ioRtnUsedKeyDuprec()
              && (params.irisWorkArea.irisFuncGn()
                  || params.irisWorkArea.irisFuncGhn()
                  || params.irisWorkArea.irisFuncGnp()
                  || params.irisWorkArea.irisFuncGhnp())) {
            // COB(747): SET SQL-SELECT-PRIMARY TO TRUE
            params.irisWorkArea.setSqlSelectPrimary(true);
            // COB(748): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
            params
                .irisDbPcb
                .ioRtnUsedKeysPcbArea
                .ioRtnUsedKeysArea
                .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
                .setIoRtnUsedKeyNotChanged(true);
            // COB(749): MOVE ZERO TO IO-RTN-LAST-OPEN-CURSOR(PAUTSUM0-LVL)
            params
                .irisDbPcb
                .ioRtnUsedKeysPcbArea
                .ioRtnUsedKeysArea
                .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
                .ioRtnUsedSsaKeys
                .ioRtnLastOpenCursor
                .setValue(0);
            // COB(750): END-IF
          }
          // COB(752): WHEN 2
          //       * IRISDB - Segm: PAUTDTL1, Op: GNP, RevId: 2
          break;
        case 2:
          // COB(753): IF IRIS-FUNC-GU OR IRIS-FUNC-GHU
          // COB(753): OR WS-SEGMENT-NAME NOT =
          // COB(753):   IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
          // COB(753): OR IRIS-CODE-FIRST(PAUTDTL1-LVL)
          if (params.irisWorkArea.irisFuncGu()
              || params.irisWorkArea.irisFuncGhu()
              || !ws.wsSegmentName.equals(
                  params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(
                          ws.pautdtl1Lvl.getInt() - 1)
                      .ioRtnUsedSsaKeys
                      .ioRtnUsedLastSegment)
              || params
                  .irisWorkArea
                  .irisKeyvalueTab
                  .irisCcodeLevelsAtIndex(ws.pautdtl1Lvl.getInt() - 1)
                  .irisCcodeValues
                  .irisCodeFirst()) {
            // COB(757): SET SQL-SELECT-SEEK TO TRUE
            params.irisWorkArea.setSqlSelectSeek(true);
            // COB(758): ELSE
          } else {
            // COB(759): SET SQL-SELECT-NEXT TO TRUE
            params.irisWorkArea.setSqlSelectNext(true);
            // COB(760): END-IF
          }
          // COB(761): IF IO-RTN-USED-KEY-DUPREC(PAUTDTL1-LVL)
          // COB(761): AND (IRIS-FUNC-GN OR IRIS-FUNC-GHN
          // COB(761): OR IRIS-FUNC-GNP OR IRIS-FUNC-GHNP)
          if (params
                  .irisDbPcb
                  .ioRtnUsedKeysPcbArea
                  .ioRtnUsedKeysArea
                  .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
                  .ioRtnUsedKeyDuprec()
              && (params.irisWorkArea.irisFuncGn()
                  || params.irisWorkArea.irisFuncGhn()
                  || params.irisWorkArea.irisFuncGnp()
                  || params.irisWorkArea.irisFuncGhnp())) {
            // COB(764): SET SQL-SELECT-PRIMARY TO TRUE
            params.irisWorkArea.setSqlSelectPrimary(true);
            // COB(765): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
            params
                .irisDbPcb
                .ioRtnUsedKeysPcbArea
                .ioRtnUsedKeysArea
                .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
                .setIoRtnUsedKeyNotChanged(true);
            // COB(766): MOVE ZERO TO IO-RTN-LAST-OPEN-CURSOR(PAUTDTL1-LVL)
            params
                .irisDbPcb
                .ioRtnUsedKeysPcbArea
                .ioRtnUsedKeysArea
                .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
                .ioRtnUsedSsaKeys
                .ioRtnLastOpenCursor
                .setValue(0);
            // COB(767): END-IF
          }
          // COB(769): WHEN 3
          //       * IRISDB - Segm: PAUTDTL1, Op: DLET, RevId: 3
          break;
        case 3:
          // COB(770): SET SQL-DELETE TO TRUE
          params.irisWorkArea.setSqlDelete(true);
          // COB(772): WHEN 4
          //       * IRISDB - Segm: PAUTSUM0, Op: DLET, RevId: 4
          break;
        case 4:
          // COB(773): SET SQL-DELETE TO TRUE
          params.irisWorkArea.setSqlDelete(true);
          // COB(775): WHEN 8
          //       * IRISDB - Segm: PAUTSUM0, Op: GU, RevId: 8
          break;
        case 8:
          // COB(776): IF IRIS-FUNC-GU OR IRIS-FUNC-GHU
          // COB(776): OR WS-SEGMENT-NAME NOT =
          // COB(776):   IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
          // COB(776): OR IRIS-CODE-FIRST(PAUTSUM0-LVL)
          // COB(776): OR IO-RTN-USED-KEY-DUPREC(PAUTSUM0-LVL)
          // COB(776): OR IRIS-KEYVALUE(1, 1)(5:6) NOT =
          // COB(776):   IO-RTN-IRIS-KEYVALUE(1, 1)(5:6)
          if (params.irisWorkArea.irisFuncGu()
              || params.irisWorkArea.irisFuncGhu()
              || !ws.wsSegmentName.equals(
                  params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(
                          ws.pautsum0Lvl.getInt() - 1)
                      .ioRtnUsedSsaKeys
                      .ioRtnUsedLastSegment)
              || params
                  .irisWorkArea
                  .irisKeyvalueTab
                  .irisCcodeLevelsAtIndex(ws.pautsum0Lvl.getInt() - 1)
                  .irisCcodeValues
                  .irisCodeFirst()
              || params
                  .irisDbPcb
                  .ioRtnUsedKeysPcbArea
                  .ioRtnUsedKeysArea
                  .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
                  .ioRtnUsedKeyDuprec()
              || !params
                  .irisWorkArea
                  .irisKeyvalueTab
                  .irisKeysLevelsAtIndex(0)
                  .irisKeyvaluesRed1
                  .irisKeyvaluesAtIndex(0)
                  .irisKeyvalue
                  .getString(4, 6)
                  .equals(
                      params
                          .irisDbPcb
                          .ioRtnUsedKeysPcbArea
                          .ioRtnUsedKeysArea
                          .ioRtnUsedKeyValueAtIndex(0)
                          .ioRtnUsedSsaInputKeys
                          .ioRtnIrisKeyvaluesAtIndex(0)
                          .ioRtnIrisKeyvalue
                          .getString(4, 6))) {
            // COB(783): MOVE IRIS-KEYVALUE(1, 1)(5:6) TO
            // COB(783): IO-RTN-IRIS-KEYVALUE(1, 1)(5:6)
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0).ioRtnUsedSsaInputKeys.ioRtnIrisKeyvaluesAtIndex(0).ioRtnIrisKeyvalue.setValue(
                    params.irisWorkArea.irisKeyvalueTab.irisKeysLevelsAtIndex(0).irisKeyvaluesRed1.irisKeyvaluesAtIndex(0).irisKeyvalue,
                    4,
                    6);
            // COB(785): MOVE IRIS-KEYVALUE(1, 1)(5:6) TO
            // COB(785):        IO-RTN-USED-KEY-ALPHA(1)(1:6)
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0).ioRtnUsedSsaKeys.ioRtnUsedKeyAlpha.setValue(
                    params.irisWorkArea.irisKeyvalueTab.irisKeysLevelsAtIndex(0).irisKeyvaluesRed1.irisKeyvaluesAtIndex(0).irisKeyvalue,
                    4,
                    0,
                    6);
            // COB(787): SET SQL-SELECT-PRIMARY TO TRUE
            params.irisWorkArea.setSqlSelectPrimary(true);
            // COB(788): ELSE
          } else {
            // COB(789): SET SKIP-SEGMENT-ACCESS TO TRUE
            ws.setSkipSegmentAccess(true);
            // COB(790): SET IRIS-SQL-NOT-FOUND TO TRUE
            ws.irissqlc.setIrisSqlNotFound(true);
            // COB(791): MOVE IRIS-DB-SQLCODE TO IRIS-SQLCODE
            params.irisWorkArea.irisSqlError.irisSqlcode.setValue(ws.irissqlc.irisDbSqlcode);
            // COB(792): MOVE SPACE TO WS-LAST-SEGMENT-NAME
            ws.wsLastSegmentName.spaces();
            // COB(793): MOVE ZERO TO WS-LAST-SEGMENT-LEVEL
            ws.wsLastSegmentLevel.setValue(0);
            // COB(794): GO TO SKIP-FUNCTION
            return Flow.skipFunction;
            // COB(795): END-IF
          }
          // COB(797): WHEN 9
          //       * IRISDB - Segm: PAUTSUM0, Op: REPL, RevId: 9
          break;
        case 9:
          // COB(798): IF IRIS-FUNC-GU OR IRIS-FUNC-GHU
          // COB(798): OR WS-SEGMENT-NAME NOT =
          // COB(798):   IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
          // COB(798): OR IRIS-CODE-FIRST(PAUTSUM0-LVL)
          if (params.irisWorkArea.irisFuncGu()
              || params.irisWorkArea.irisFuncGhu()
              || !ws.wsSegmentName.equals(
                  params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(
                          ws.pautsum0Lvl.getInt() - 1)
                      .ioRtnUsedSsaKeys
                      .ioRtnUsedLastSegment)
              || params
                  .irisWorkArea
                  .irisKeyvalueTab
                  .irisCcodeLevelsAtIndex(ws.pautsum0Lvl.getInt() - 1)
                  .irisCcodeValues
                  .irisCodeFirst()) {
            // COB(802): SET SQL-SELECT-SEEK TO TRUE
            params.irisWorkArea.setSqlSelectSeek(true);
            // COB(803): ELSE
          } else {
            // COB(804): SET SQL-SELECT-NEXT TO TRUE
            params.irisWorkArea.setSqlSelectNext(true);
            // COB(805): END-IF
          }
          // COB(806): IF IO-RTN-USED-KEY-DUPREC(PAUTSUM0-LVL)
          // COB(806): AND (IRIS-FUNC-GN OR IRIS-FUNC-GHN
          // COB(806): OR IRIS-FUNC-GNP OR IRIS-FUNC-GHNP)
          if (params
                  .irisDbPcb
                  .ioRtnUsedKeysPcbArea
                  .ioRtnUsedKeysArea
                  .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
                  .ioRtnUsedKeyDuprec()
              && (params.irisWorkArea.irisFuncGn()
                  || params.irisWorkArea.irisFuncGhn()
                  || params.irisWorkArea.irisFuncGnp()
                  || params.irisWorkArea.irisFuncGhnp())) {
            // COB(809): SET SQL-SELECT-PRIMARY TO TRUE
            params.irisWorkArea.setSqlSelectPrimary(true);
            // COB(810): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
            params
                .irisDbPcb
                .ioRtnUsedKeysPcbArea
                .ioRtnUsedKeysArea
                .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
                .setIoRtnUsedKeyNotChanged(true);
            // COB(811): MOVE ZERO TO IO-RTN-LAST-OPEN-CURSOR(PAUTSUM0-LVL)
            params
                .irisDbPcb
                .ioRtnUsedKeysPcbArea
                .ioRtnUsedKeysArea
                .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
                .ioRtnUsedSsaKeys
                .ioRtnLastOpenCursor
                .setValue(0);
            // COB(812): END-IF
          }
          // COB(813): SET SQL-UPDATE TO TRUE
          params.irisWorkArea.setSqlUpdate(true);
          // COB(815): WHEN 10
          //       * IRISDB - Segm: PAUTSUM0, Op: ISRT, RevId: 10
          break;
        case 10:
          // COB(816): SET SQL-INSERT TO TRUE
          params.irisWorkArea.setSqlInsert(true);
          // COB(818): WHEN 11
          //       * IRISDB - Segm: PAUTDTL1, Op: ISRT, RevId: 11
          break;
        case 11:
          // COB(819): MOVE IRIS-KEYVALUE(1, 1)(5:6) TO
          // COB(819):        IO-RTN-USED-KEY-ALPHA(1)(1:6)
          params
              .irisDbPcb
              .ioRtnUsedKeysPcbArea
              .ioRtnUsedKeysArea
              .ioRtnUsedKeyValueAtIndex(0)
              .ioRtnUsedSsaKeys
              .ioRtnUsedKeyAlpha
              .setValue(
                  params
                      .irisWorkArea
                      .irisKeyvalueTab
                      .irisKeysLevelsAtIndex(0)
                      .irisKeyvaluesRed1
                      .irisKeyvaluesAtIndex(0)
                      .irisKeyvalue,
                  4,
                  0,
                  6);
          // COB(821): SET SQL-INSERT TO TRUE
          params.irisWorkArea.setSqlInsert(true);
          // COB(823): WHEN 12
          //       * IRISDB - Segm: PAUTDTL1, Op: GNP, RevId: 12
          break;
        case 12:
          // COB(824): MOVE IRIS-KEYVALUE(1, 1)(1:8) TO WS-HV-001-LEN008-X
          ws.wsHv001Len008X.setValue(
              params
                  .irisWorkArea
                  .irisKeyvalueTab
                  .irisKeysLevelsAtIndex(0)
                  .irisKeyvaluesRed1
                  .irisKeyvaluesAtIndex(0)
                  .irisKeyvalue,
              8);
          // COB(825): IF IRIS-KEYVALUE(1, 1)(1:8) NOT =
          // COB(825):   IO-RTN-IRIS-KEYVALUE(1, 1)(1:8)
          if (!params
              .irisWorkArea
              .irisKeyvalueTab
              .irisKeysLevelsAtIndex(0)
              .irisKeyvaluesRed1
              .irisKeyvaluesAtIndex(0)
              .irisKeyvalue
              .getString(0, 8)
              .equals(
                  params
                      .irisDbPcb
                      .ioRtnUsedKeysPcbArea
                      .ioRtnUsedKeysArea
                      .ioRtnUsedKeyValueAtIndex(0)
                      .ioRtnUsedSsaInputKeys
                      .ioRtnIrisKeyvaluesAtIndex(0)
                      .ioRtnIrisKeyvalue
                      .getString(0, 8))) {
            // COB(827): MOVE ZERO TO IO-RTN-LAST-OPEN-CURSOR(2)
            params
                .irisDbPcb
                .ioRtnUsedKeysPcbArea
                .ioRtnUsedKeysArea
                .ioRtnUsedKeyValueAtIndex(1)
                .ioRtnUsedSsaKeys
                .ioRtnLastOpenCursor
                .setValue(0);
            // COB(828): MOVE IRIS-KEYVALUE(1, 1)(1:8) TO
            // COB(828):        IO-RTN-IRIS-KEYVALUE(1, 1)(1:8)
            params
                .irisDbPcb
                .ioRtnUsedKeysPcbArea
                .ioRtnUsedKeysArea
                .ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaInputKeys
                .ioRtnIrisKeyvaluesAtIndex(0)
                .ioRtnIrisKeyvalue
                .setValue(
                    params
                        .irisWorkArea
                        .irisKeyvalueTab
                        .irisKeysLevelsAtIndex(0)
                        .irisKeyvaluesRed1
                        .irisKeyvaluesAtIndex(0)
                        .irisKeyvalue,
                    8);
            // COB(830): END-IF
          }
          // COB(831): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6) TO
          // COB(831):        WS-PACKED-FLD-CHR(1)(5:6)
          ws.filler249
              .wsPackedFldChrAtIndex(0)
              .setValue(
                  params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(
                          0)
                      .ioRtnUsedSsaKeys
                      .ioRtnUsedKeyAlpha,
                  0,
                  4,
                  6);
          // COB(833): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
          if (!ws.filler173.wsPackedFld1800AtIndex(0).isNumeric()) {
            // COB(834): MOVE ZERO TO WS-HV-002-LEN006-P
            ws.wsHv002Len006P.setValue(0);
            // COB(835): ELSE
          } else {
            // COB(836): MOVE WS-PACKED-FLD-18-00(1) TO WS-HV-002-LEN006-P
            ws.wsHv002Len006P.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
            // COB(837): END-IF
          }
          // COB(838): MOVE IO-RTN-USED-KEY-ALPHA(2)(1:8) TO WS-HV-003-LEN008-X
          ws.wsHv003Len008X.setValue(
              params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(1)
                  .ioRtnUsedSsaKeys
                  .ioRtnUsedKeyAlpha,
              8);
          // COB(839): SET SQL-SELECT-STATIC TO TRUE
          params.irisWorkArea.setSqlSelectStatic(true);
          // COB(840): SET CUSTOM-STATIC-ACCESS TO TRUE
          ws.setCustomStaticAccess(true);
          // COB(841): IF IO-RTN-USED-KEY-DUPREC(PAUTDTL1-LVL)
          // COB(841): AND (IRIS-FUNC-GN OR IRIS-FUNC-GHN
          // COB(841): OR IRIS-FUNC-GNP OR IRIS-FUNC-GHNP)
          if (params
                  .irisDbPcb
                  .ioRtnUsedKeysPcbArea
                  .ioRtnUsedKeysArea
                  .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
                  .ioRtnUsedKeyDuprec()
              && (params.irisWorkArea.irisFuncGn()
                  || params.irisWorkArea.irisFuncGhn()
                  || params.irisWorkArea.irisFuncGnp()
                  || params.irisWorkArea.irisFuncGhnp())) {
            // COB(844): SET SQL-SELECT-PRIMARY TO TRUE
            params.irisWorkArea.setSqlSelectPrimary(true);
            // COB(845): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
            params
                .irisDbPcb
                .ioRtnUsedKeysPcbArea
                .ioRtnUsedKeysArea
                .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
                .setIoRtnUsedKeyNotChanged(true);
            // COB(846): MOVE ZERO TO IO-RTN-LAST-OPEN-CURSOR(PAUTDTL1-LVL)
            params
                .irisDbPcb
                .ioRtnUsedKeysPcbArea
                .ioRtnUsedKeysArea
                .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
                .ioRtnUsedSsaKeys
                .ioRtnLastOpenCursor
                .setValue(0);
            // COB(847): END-IF
          }
          // COB(849): WHEN 13
          //       * IRISDB - Segm: PAUTDTL1, Op: REPL, RevId: 13
          break;
        case 13:
          // COB(850): SET SQL-UPDATE TO TRUE
          params.irisWorkArea.setSqlUpdate(true);
          // COB(852): WHEN 16
          //       * IRISDB - Segm: PAUTDTL1, Op: ISRT, RevId: 16
          break;
        case 16:
          // COB(853): SET SQL-INSERT TO TRUE
          params.irisWorkArea.setSqlInsert(true);
          // COB(854): WHEN OTHER
          break;
        default:
          // COB(855): SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
          params.irisWorkArea.setIrisErrFunctionNotFound(true);
          // COB(856): END-EVALUATE
      }
      // COB(857): END-EVALUATE.
    }
    return Flow.skipFunction;
  }

  /***/
  protected void skipFunction() {
    // COB(861): IF IRIS-NO-ERROR
    // COB(861): AND NOT SKIP-SEGMENT-ACCESS
    if (params.irisWorkArea.irisNoError() && !ws.skipSegmentAccess()) {
      // COB(863): EVALUATE WS-SEGMENT-NAME
      // COB(864): WHEN 'PAUTSUM0'
      if (ws.wsSegmentName.getValue().equals("PAUTSUM0")) {
        // COB(866): PERFORM HANDLE-SEGMENT-PAUTSUM0
        handleSegmentPautsum0();
        // COB(868): WHEN 'PAUTDTL1'
      } else if (ws.wsSegmentName.getValue().equals("PAUTDTL1")) {
        // COB(870): PERFORM HANDLE-SEGMENT-PAUTDTL1
        handleSegmentPautdtl1();
        // COB(872): WHEN OTHER
      } else {
        // COB(873): SET IRIS-ERR-RTN-SEGMENT-NOT-FOUND TO TRUE
        params.irisWorkArea.setIrisErrRtnSegmentNotFound(true);
        // COB(874): END-EVALUATE
      }
      // COB(876): END-IF
    }
    // COB(877): IF IRIS-TRACE-PERFORMANCE
    if (params.irisWorkArea.irisTracePerformance()) {
      // COB(878): MOVE 0 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
      // COB(879): STRING '<IRISTRACE> - AFTER  SQL ACCESS'
      // COB(879): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
          "<IRISTRACE> - AFTER  SQL ACCESS", ws.iriscomm.messageEnd);
      // COB(881): SET IRISTRAC-RTN TO TRUE
      ws.iriscomm.setIristracRtn(true);
      // COB(882): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(883): END-IF
    }
    // COB(885): PERFORM FINALIZE-VARIABLES
    finalizeVariables();
    // COB(887): IF IRIS-TRACE-STANDARD
    if (params.irisWorkArea.irisTraceStandard()) {
      // COB(888): MOVE 80 TO WS-LEN
      ws.wsLen.setValue(80);
      // COB(889): IF WS-SEGMENT-LEN < WS-LEN
      if (ws.wsSegmentLen.lessThan(ws.wsLen)) {
        // COB(890): MOVE WS-SEGMENT-LEN TO WS-LEN
        ws.wsLen.setValue(ws.wsSegmentLen);
        // COB(891): ELSE
      } else {
        // COB(892): IF IRIS-TRACE-FULL
        if (params.irisWorkArea.irisTraceFull()) {
          // COB(893): MOVE WS-SEGMENT-LEN TO WS-LEN
          ws.wsLen.setValue(ws.wsSegmentLen);
          // COB(894): END-IF
        }
        // COB(895): END-IF
      }
      // COB(896): IF WS-LEN = ZERO
      if (ws.wsLen.equals(0)) {
        // COB(897): MOVE 1 TO WS-LEN
        ws.wsLen.setValue(1);
        // COB(898): END-IF
      }
      // COB(899): MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
      ws.irissqlc.irisDbSqlcode.setValue(params.irisWorkArea.irisSqlError.irisSqlcode);
      // COB(900): IF NOT IRIS-SQL-OK AND NOT IRIS-SQL-NOT-FOUND
      if (!ws.irissqlc.irisSqlOk() && !ws.irissqlc.irisSqlNotFound()) {
        // COB(901): IF IRIS-SQLERRML = ZERO
        if (params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrml.equals(0)) {
          // COB(902): MOVE 1 TO IRIS-SQLERRML
          params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrml.setValue(1);
          // COB(903): END-IF
        }
        // COB(904): MOVE 0 TO IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
        // COB(905): MOVE IRIS-SQLCODE TO WS-SQLCODE-N
        ws.wsSqlcodeN.setValue(params.irisWorkArea.irisSqlError.irisSqlcode);
        // COB(906): MOVE WS-SQLCODE-N TO WS-SQLCODE-E
        ws.wsSqlcodeE.setValue(ws.wsSqlcodeN);
        // COB(907): STRING '<IRISTRACE> - DBPAUTP0:SQL_RC' NL
        // COB(907):      ' SQLCODE    =(' WS-SQLCODE-E ')' NL
        // COB(907):      ' SQLERRM    =(' IRIS-SQLERRMC(1:IRIS-SQLERRML) ')'
        // COB(907): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - DBPAUTP0:SQL_RC",
            ws.iriscomm.nl,
            " SQLCODE    =(",
            ws.wsSqlcodeE,
            ")",
            ws.iriscomm.nl,
            " SQLERRM    =(",
            params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrmc.getString(
                0, params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrml.getInt()),
            ")",
            ws.iriscomm.messageEnd);
        // COB(911): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(912): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(913): END-IF
      }
      // COB(914): MOVE 0 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
      // COB(915): IF IRIS-NO-ERROR AND IRIS-TRACE-FULL
      if (params.irisWorkArea.irisNoError() && params.irisWorkArea.irisTraceFull()) {
        // COB(916): STRING '<IRISTRACE> - DBPAUTP0:END' NL
        // COB(916):        ' DBD        =(DBPAUTP0) ' NL
        // COB(916):        ' CALLER PGM =(' IRIS-PROGRAM-NAME ') ' NL
        // COB(916):        ' CALLER ID  =(' IRIS-CALL-ID ') ' NL
        // COB(916):        ' PCB INDEX  =(' RUN-PCB-INDEX ') ' NL
        // COB(916):        ' IMS FUNC   =(' IRIS-IMS-FUNCTION ') ' NL
        // COB(916):        ' CUSTOM ID  =(' IRIS-CUSTOM-FUNC-ID ') ' NL
        // COB(916):        ' SEGMENT    =(' WS-SEGMENT-NAME ') ' NL
        // COB(916):        ' SECTION    =(' WS-LAST-IORTN-SECTION ') ' NL
        // COB(916):        ' PCB AREA   =(' DB-PCB-FIXED-PART ') ' NL
        // COB(916):        ' KFB AREA   =(' DB-PCB-KEY-FB
        // COB(916):                     (1:DB-PCB-FB-KEY-LENGTH) ') ' NL
        // COB(916):        ' I-O AREA   =(' LK-IO-AREA(1:WS-LEN)') '
        // COB(916): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - DBPAUTP0:END",
            ws.iriscomm.nl,
            " DBD        =(DBPAUTP0) ",
            ws.iriscomm.nl,
            " CALLER PGM =(",
            params.irisWorkArea.irisProgramName,
            ") ",
            ws.iriscomm.nl,
            " CALLER ID  =(",
            params.irisWorkArea.irisCallId,
            ") ",
            ws.iriscomm.nl,
            " PCB INDEX  =(",
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.runPcbIndex,
            ") ",
            ws.iriscomm.nl,
            " IMS FUNC   =(",
            params.irisWorkArea.irisImsFunction,
            ") ",
            ws.iriscomm.nl,
            " CUSTOM ID  =(",
            params.irisWorkArea.irisCustomFuncId,
            ") ",
            ws.iriscomm.nl,
            " SEGMENT    =(",
            ws.wsSegmentName,
            ") ",
            ws.iriscomm.nl,
            " SECTION    =(",
            ws.wsLastIortnSection,
            ") ",
            ws.iriscomm.nl,
            " PCB AREA   =(",
            params.irisDbPcb.dbPcbFixedPart,
            ") ",
            ws.iriscomm.nl,
            " KFB AREA   =(",
            params.irisDbPcb.dbPcbKeyFb.getString(
                0, params.irisDbPcb.dbPcbFixedPart.dbPcbFbKeyLength.getInt()),
            ") ",
            ws.iriscomm.nl,
            " I-O AREA   =(",
            params.lkIoArea.getString(0, ws.wsLen.getInt()),
            ") ",
            ws.iriscomm.messageEnd);
        // COB(930): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(931): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(932): ELSE
      } else {
        // COB(933): IF NOT IRIS-NO-ERROR
        if (!params.irisWorkArea.irisNoError()) {
          // COB(934): MOVE 1 TO WS-ERROR-DESCRIPTION-LEN
          ws.wsErrorDescriptionLen.setValue(1);
          // COB(935): STRING IRIS-ERROR-DESCRIPTION(IRIS-ERR-MESSAGE-ID)
          // COB(935): DELIMITED BY '_'
          // COB(935): INTO WS-ERROR-DESCRIPTION
          // COB(935): POINTER WS-ERROR-DESCRIPTION-LEN
          ws.wsErrorDescriptionLen.setValue(
              ws.wsErrorDescription.concat(
                  params
                      .irisWorkArea
                      .filler179
                      .irisErrorDescriptionAtIndex(
                          params.irisWorkArea.irisErrMessageId.getInt() - 1)
                      .substringToValue("_")));
          // COB(939): SUBTRACT 1 FROM WS-ERROR-DESCRIPTION-LEN
          ws.wsErrorDescriptionLen.subtract(1);
          // COB(940): MOVE IRIS-ERR-MESSAGE-ID TO WS-MESSAGE-ID-EDITED
          ws.wsMessageIdEdited.setValue(params.irisWorkArea.irisErrMessageId);
          // COB(941): STRING '<IRISTRACE> - DBPAUTP0:END' NL
          // COB(941):        ' DBD        =(DBPAUTP0) ' NL
          // COB(941):        ' CALLER PGM =(' IRIS-PROGRAM-NAME ') ' NL
          // COB(941):        ' CALLER ID  =(' IRIS-CALL-ID ') ' NL
          // COB(941):        ' PCB INDEX  =(' RUN-PCB-INDEX ') ' NL
          // COB(941):        ' IMS FUNC   =(' IRIS-IMS-FUNCTION ') ' NL
          // COB(941):        ' CUSTOM ID  =(' IRIS-CUSTOM-FUNC-ID ') ' NL
          // COB(941):        ' SEGMENT    =(' WS-SEGMENT-NAME ') ' NL
          // COB(941):        ' SECTION    =(' WS-LAST-IORTN-SECTION ') ' NL
          // COB(941):        ' ERROR ID   =(' WS-MESSAGE-ID-EDITED ') ' NL
          // COB(941):        ' ERROR DESCR=('
          // COB(941): WS-ERROR-DESCRIPTION(1:WS-ERROR-DESCRIPTION-LEN)') ' NL
          // COB(941):        ' PCB AREA   =(' DB-PCB-FIXED-PART ') ' NL
          // COB(941):        ' I-O AREA   =(' LK-IO-AREA(1:WS-LEN) ') '
          // COB(941): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0:END",
              ws.iriscomm.nl,
              " DBD        =(DBPAUTP0) ",
              ws.iriscomm.nl,
              " CALLER PGM =(",
              params.irisWorkArea.irisProgramName,
              ") ",
              ws.iriscomm.nl,
              " CALLER ID  =(",
              params.irisWorkArea.irisCallId,
              ") ",
              ws.iriscomm.nl,
              " PCB INDEX  =(",
              params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.runPcbIndex,
              ") ",
              ws.iriscomm.nl,
              " IMS FUNC   =(",
              params.irisWorkArea.irisImsFunction,
              ") ",
              ws.iriscomm.nl,
              " CUSTOM ID  =(",
              params.irisWorkArea.irisCustomFuncId,
              ") ",
              ws.iriscomm.nl,
              " SEGMENT    =(",
              ws.wsSegmentName,
              ") ",
              ws.iriscomm.nl,
              " SECTION    =(",
              ws.wsLastIortnSection,
              ") ",
              ws.iriscomm.nl,
              " ERROR ID   =(",
              ws.wsMessageIdEdited,
              ") ",
              ws.iriscomm.nl,
              " ERROR DESCR=(",
              ws.wsErrorDescription.getString(0, ws.wsErrorDescriptionLen.getInt()),
              ") ",
              ws.iriscomm.nl,
              " PCB AREA   =(",
              params.irisDbPcb.dbPcbFixedPart,
              ") ",
              ws.iriscomm.nl,
              " I-O AREA   =(",
              params.lkIoArea.getString(0, ws.wsLen.getInt()),
              ") ",
              ws.iriscomm.messageEnd);
          // COB(956): SET IRISTRAC-RTN TO TRUE
          ws.iriscomm.setIristracRtn(true);
          // COB(957): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
          context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
          // COB(958): END-IF
        }
        // COB(959): END-IF
      }
      // COB(960): ELSE
    } else {
      // COB(962): IF DB-STATUS-INTERNAL-NOT-HANDLED
      // COB(962): OR IRIS-ERR-UNHANDLED-SQLCODE
      //       *    IN CASE OF INTERNAL ERROR EMIT ALWAYS A MESSAGE TO CONSOLE
      if (params.irisDbPcb.dbPcbFixedPart.dbStatusInternalNotHandled()
          || params.irisWorkArea.irisErrUnhandledSqlcode()) {
        // COB(964): IF IRIS-SQLERRML = ZERO
        if (params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrml.equals(0)) {
          // COB(965): MOVE 1 TO IRIS-SQLERRML
          params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrml.setValue(1);
          // COB(966): END-IF
        }
        // COB(967): MOVE 1 TO WS-ERROR-DESCRIPTION-LEN
        ws.wsErrorDescriptionLen.setValue(1);
        // COB(968): STRING IRIS-ERROR-DESCRIPTION(IRIS-ERR-MESSAGE-ID)
        // COB(968): DELIMITED BY '_'
        // COB(968): INTO WS-ERROR-DESCRIPTION
        // COB(968): POINTER WS-ERROR-DESCRIPTION-LEN
        ws.wsErrorDescriptionLen.setValue(
            ws.wsErrorDescription.concat(
                params
                    .irisWorkArea
                    .filler179
                    .irisErrorDescriptionAtIndex(params.irisWorkArea.irisErrMessageId.getInt() - 1)
                    .substringToValue("_")));
        // COB(972): SUBTRACT 1 FROM WS-ERROR-DESCRIPTION-LEN
        ws.wsErrorDescriptionLen.subtract(1);
        // COB(973): MOVE 0 TO IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
        // COB(974): MOVE IRIS-SQLCODE TO WS-SQLCODE-N
        ws.wsSqlcodeN.setValue(params.irisWorkArea.irisSqlError.irisSqlcode);
        // COB(975): MOVE WS-SQLCODE-N TO WS-SQLCODE-E
        ws.wsSqlcodeE.setValue(ws.wsSqlcodeN);
        // COB(976): MOVE IRIS-ERR-MESSAGE-ID TO WS-MESSAGE-ID-EDITED
        ws.wsMessageIdEdited.setValue(params.irisWorkArea.irisErrMessageId);
        // COB(977): STRING '<IRISTRACE> - DBPAUTP0:END' NL
        // COB(977):        ' DBD        =(DBPAUTP0) ' NL
        // COB(977):        ' CALLER PGM =(' IRIS-PROGRAM-NAME ') ' NL
        // COB(977):        ' CALLER ID  =(' IRIS-CALL-ID ') ' NL
        // COB(977):        ' PCB INDEX  =(' RUN-PCB-INDEX ') ' NL
        // COB(977):        ' IMS FUNC   =(' IRIS-IMS-FUNCTION ') ' NL
        // COB(977):        ' CUSTOM ID  =(' IRIS-CUSTOM-FUNC-ID ') ' NL
        // COB(977):        ' SEGMENT    =(' WS-SEGMENT-NAME ') ' NL
        // COB(977):        ' SECTION    =(' WS-LAST-IORTN-SECTION ') ' NL
        // COB(977):        ' ERROR ID   =(' WS-MESSAGE-ID-EDITED ') ' NL
        // COB(977):        ' ERROR DESCR=('
        // COB(977): WS-ERROR-DESCRIPTION(1:WS-ERROR-DESCRIPTION-LEN) ') ' NL
        // COB(977):      ' SQLCODE    =(' WS-SQLCODE-E ') ' NL
        // COB(977):      ' SQLERRM    =(' IRIS-SQLERRMC(1:IRIS-SQLERRML)') '
        // COB(977): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - DBPAUTP0:END",
            ws.iriscomm.nl,
            " DBD        =(DBPAUTP0) ",
            ws.iriscomm.nl,
            " CALLER PGM =(",
            params.irisWorkArea.irisProgramName,
            ") ",
            ws.iriscomm.nl,
            " CALLER ID  =(",
            params.irisWorkArea.irisCallId,
            ") ",
            ws.iriscomm.nl,
            " PCB INDEX  =(",
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.runPcbIndex,
            ") ",
            ws.iriscomm.nl,
            " IMS FUNC   =(",
            params.irisWorkArea.irisImsFunction,
            ") ",
            ws.iriscomm.nl,
            " CUSTOM ID  =(",
            params.irisWorkArea.irisCustomFuncId,
            ") ",
            ws.iriscomm.nl,
            " SEGMENT    =(",
            ws.wsSegmentName,
            ") ",
            ws.iriscomm.nl,
            " SECTION    =(",
            ws.wsLastIortnSection,
            ") ",
            ws.iriscomm.nl,
            " ERROR ID   =(",
            ws.wsMessageIdEdited,
            ") ",
            ws.iriscomm.nl,
            " ERROR DESCR=(",
            ws.wsErrorDescription.getString(0, ws.wsErrorDescriptionLen.getInt()),
            ") ",
            ws.iriscomm.nl,
            " SQLCODE    =(",
            ws.wsSqlcodeE,
            ") ",
            ws.iriscomm.nl,
            " SQLERRM    =(",
            params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrmc.getString(
                0, params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrml.getInt()),
            ") ",
            ws.iriscomm.messageEnd);
        // COB(992): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(993): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(994): END-IF
      }
      // COB(995): END-IF
    }
    // COB(996): MOVE IRIS-IMS-FUNCTION TO LAST-IMS-FUNCTION
    params.irisDbPcb.ioRtnUsedKeysPcbArea.filler584.lastImsFunction.setValue(
        params.irisWorkArea.irisImsFunction);
    // COB(997): MOVE WS-LAST-SEGMENT-NAME TO LAST-IMS-SEGMENT-NAME
    params.irisDbPcb.ioRtnUsedKeysPcbArea.filler584.lastImsSegmentName.setValue(
        ws.wsLastSegmentName);
    // COB(998): MOVE WS-LAST-SEGMENT-LEVEL TO LAST-IMS-SEGMENT-LEVEL
    params.irisDbPcb.ioRtnUsedKeysPcbArea.filler584.lastImsSegmentLevel.setValue(
        ws.wsLastSegmentLevel);
    // COB(999): MOVE IRIS-KEYVALUE-TAB(1:240) TO LAST-IMS-CCODE
    params.irisDbPcb.ioRtnUsedKeysPcbArea.filler584.lastImsCcode.setValue(
        params.irisWorkArea.irisKeyvalueTab, 240);
    // COB(1001): IF IRIS-EXECDLI
    if (params.irisWorkArea.irisExecdli()) {
      // COB(1002): PERFORM SET-DIB-BLOCK
      setDibBlock();
      // COB(1003): END-IF
    }
    mainProgramEx();
  }

  /***/
  protected void mainProgramEx() {
    // COB(1009): GOBACK.
    context.goback();
  }

  /***
   ******************************************************************
   *                                                                *
   *                    P R O G R A M    E N D                      *
   *                                                                *
   ******************************************************************
   *
   ******************************************************************
   *    PRINT SSAS CONTENT
   ******************************************************************
   */
  protected void printSsas() {
    // COB(1023): MOVE 'PRINT-SSAS' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PRINT-SSAS");
    // COB(1025): IF IRIS-PARAM-NUM > 3
    if (params.irisWorkArea.irisParamNum.greaterThan(3)) {
      // COB(1026): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1027): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1028): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1028): OR LK-SSA-01(WS-IDX:1) = '('
      // COB(1028): OR LK-SSA-01(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa01.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa01.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1031): CONTINUE
        // do nothing
        // COB(1032): END-PERFORM
      }
      // COB(1033): IF LK-SSA-01(WS-IDX:1) = '('
      if (params.lkSsa01.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1034): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1034): UNTIL WS-IDX > 540
        // COB(1034): OR LK-SSA-01(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa01.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1037): CONTINUE
          // do nothing
          // COB(1038): END-PERFORM
        }
        // COB(1039): END-IF
      }
      // COB(1040): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1040):                       ':SSA01=(' LK-SSA-01(1:WS-IDX) ')'
      // COB(1040): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA01=(",
              params.lkSsa01.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1043): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1044): END-IF
    }
    // COB(1046): IF IRIS-PARAM-NUM > 4
    if (params.irisWorkArea.irisParamNum.greaterThan(4)) {
      // COB(1047): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1048): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1049): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1049): OR LK-SSA-02(WS-IDX:1) = '('
      // COB(1049): OR LK-SSA-02(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa02.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa02.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1052): CONTINUE
        // do nothing
        // COB(1053): END-PERFORM
      }
      // COB(1054): IF LK-SSA-02(WS-IDX:1) = '('
      if (params.lkSsa02.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1055): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1055): UNTIL WS-IDX > 540
        // COB(1055): OR LK-SSA-02(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa02.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1058): CONTINUE
          // do nothing
          // COB(1059): END-PERFORM
        }
        // COB(1060): END-IF
      }
      // COB(1061): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1061):                       ':SSA02=(' LK-SSA-02(1:WS-IDX) ')'
      // COB(1061): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA02=(",
              params.lkSsa02.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1064): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1065): END-IF
    }
    // COB(1067): IF IRIS-PARAM-NUM > 5
    if (params.irisWorkArea.irisParamNum.greaterThan(5)) {
      // COB(1068): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1069): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1070): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1070): OR LK-SSA-03(WS-IDX:1) = '('
      // COB(1070): OR LK-SSA-03(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa03.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa03.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1073): CONTINUE
        // do nothing
        // COB(1074): END-PERFORM
      }
      // COB(1075): IF LK-SSA-03(WS-IDX:1) = '('
      if (params.lkSsa03.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1076): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1076): UNTIL WS-IDX > 540
        // COB(1076): OR LK-SSA-03(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa03.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1079): CONTINUE
          // do nothing
          // COB(1080): END-PERFORM
        }
        // COB(1081): END-IF
      }
      // COB(1082): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1082):                       ':SSA03=(' LK-SSA-03(1:WS-IDX) ')'
      // COB(1082): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA03=(",
              params.lkSsa03.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1085): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1086): END-IF
    }
    // COB(1088): IF IRIS-PARAM-NUM > 6
    if (params.irisWorkArea.irisParamNum.greaterThan(6)) {
      // COB(1089): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1090): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1091): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1091): OR LK-SSA-04(WS-IDX:1) = '('
      // COB(1091): OR LK-SSA-04(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa04.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa04.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1094): CONTINUE
        // do nothing
        // COB(1095): END-PERFORM
      }
      // COB(1096): IF LK-SSA-04(WS-IDX:1) = '('
      if (params.lkSsa04.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1097): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1097): UNTIL WS-IDX > 540
        // COB(1097): OR LK-SSA-04(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa04.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1100): CONTINUE
          // do nothing
          // COB(1101): END-PERFORM
        }
        // COB(1102): END-IF
      }
      // COB(1103): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1103):                       ':SSA04=(' LK-SSA-04(1:WS-IDX) ')'
      // COB(1103): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA04=(",
              params.lkSsa04.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1106): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1107): END-IF
    }
    // COB(1109): IF IRIS-PARAM-NUM > 7
    if (params.irisWorkArea.irisParamNum.greaterThan(7)) {
      // COB(1110): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1111): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1112): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1112): OR LK-SSA-05(WS-IDX:1) = '('
      // COB(1112): OR LK-SSA-05(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa05.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa05.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1115): CONTINUE
        // do nothing
        // COB(1116): END-PERFORM
      }
      // COB(1117): IF LK-SSA-05(WS-IDX:1) = '('
      if (params.lkSsa05.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1118): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1118): UNTIL WS-IDX > 540
        // COB(1118): OR LK-SSA-05(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa05.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1121): CONTINUE
          // do nothing
          // COB(1122): END-PERFORM
        }
        // COB(1123): END-IF
      }
      // COB(1124): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1124):                       ':SSA05=(' LK-SSA-05(1:WS-IDX) ')'
      // COB(1124): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA05=(",
              params.lkSsa05.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1127): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1128): END-IF
    }
    // COB(1130): IF IRIS-PARAM-NUM > 8
    if (params.irisWorkArea.irisParamNum.greaterThan(8)) {
      // COB(1131): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1132): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1133): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1133): OR LK-SSA-06(WS-IDX:1) = '('
      // COB(1133): OR LK-SSA-06(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa06.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa06.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1136): CONTINUE
        // do nothing
        // COB(1137): END-PERFORM
      }
      // COB(1138): IF LK-SSA-06(WS-IDX:1) = '('
      if (params.lkSsa06.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1139): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1139): UNTIL WS-IDX > 540
        // COB(1139): OR LK-SSA-06(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa06.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1142): CONTINUE
          // do nothing
          // COB(1143): END-PERFORM
        }
        // COB(1144): END-IF
      }
      // COB(1145): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1145):                       ':SSA06=(' LK-SSA-06(1:WS-IDX) ')'
      // COB(1145): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA06=(",
              params.lkSsa06.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1148): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1149): END-IF
    }
    // COB(1151): IF IRIS-PARAM-NUM > 9
    if (params.irisWorkArea.irisParamNum.greaterThan(9)) {
      // COB(1152): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1153): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1154): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1154): OR LK-SSA-07(WS-IDX:1) = '('
      // COB(1154): OR LK-SSA-07(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa07.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa07.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1157): CONTINUE
        // do nothing
        // COB(1158): END-PERFORM
      }
      // COB(1159): IF LK-SSA-07(WS-IDX:1) = '('
      if (params.lkSsa07.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1160): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1160): UNTIL WS-IDX > 540
        // COB(1160): OR LK-SSA-07(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa07.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1163): CONTINUE
          // do nothing
          // COB(1164): END-PERFORM
        }
        // COB(1165): END-IF
      }
      // COB(1166): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1166):                       ':SSA07=(' LK-SSA-07(1:WS-IDX) ')'
      // COB(1166): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA07=(",
              params.lkSsa07.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1169): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1170): END-IF
    }
    // COB(1172): IF IRIS-PARAM-NUM > 10
    if (params.irisWorkArea.irisParamNum.greaterThan(10)) {
      // COB(1173): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1174): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1175): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1175): OR LK-SSA-08(WS-IDX:1) = '('
      // COB(1175): OR LK-SSA-08(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa08.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa08.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1178): CONTINUE
        // do nothing
        // COB(1179): END-PERFORM
      }
      // COB(1180): IF LK-SSA-08(WS-IDX:1) = '('
      if (params.lkSsa08.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1181): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1181): UNTIL WS-IDX > 540
        // COB(1181): OR LK-SSA-08(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa08.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1184): CONTINUE
          // do nothing
          // COB(1185): END-PERFORM
        }
        // COB(1186): END-IF
      }
      // COB(1187): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1187):                       ':SSA08=(' LK-SSA-08(1:WS-IDX) ')'
      // COB(1187): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA08=(",
              params.lkSsa08.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1190): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1191): END-IF
    }
    // COB(1193): IF IRIS-PARAM-NUM > 11
    if (params.irisWorkArea.irisParamNum.greaterThan(11)) {
      // COB(1194): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1195): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1196): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1196): OR LK-SSA-09(WS-IDX:1) = '('
      // COB(1196): OR LK-SSA-09(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa09.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa09.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1199): CONTINUE
        // do nothing
        // COB(1200): END-PERFORM
      }
      // COB(1201): IF LK-SSA-09(WS-IDX:1) = '('
      if (params.lkSsa09.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1202): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1202): UNTIL WS-IDX > 540
        // COB(1202): OR LK-SSA-09(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa09.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1205): CONTINUE
          // do nothing
          // COB(1206): END-PERFORM
        }
        // COB(1207): END-IF
      }
      // COB(1208): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1208):                       ':SSA09=(' LK-SSA-09(1:WS-IDX) ')'
      // COB(1208): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA09=(",
              params.lkSsa09.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1211): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1212): END-IF
    }
    // COB(1214): IF IRIS-PARAM-NUM > 12
    if (params.irisWorkArea.irisParamNum.greaterThan(12)) {
      // COB(1215): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1216): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1217): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1217): OR LK-SSA-10(WS-IDX:1) = '('
      // COB(1217): OR LK-SSA-10(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa10.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa10.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1220): CONTINUE
        // do nothing
        // COB(1221): END-PERFORM
      }
      // COB(1222): IF LK-SSA-10(WS-IDX:1) = '('
      if (params.lkSsa10.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1223): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1223): UNTIL WS-IDX > 540
        // COB(1223): OR LK-SSA-10(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa10.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1226): CONTINUE
          // do nothing
          // COB(1227): END-PERFORM
        }
        // COB(1228): END-IF
      }
      // COB(1229): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1229):                       ':SSA10=(' LK-SSA-10(1:WS-IDX) ')'
      // COB(1229): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA10=(",
              params.lkSsa10.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1232): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1233): END-IF
    }
    // COB(1235): IF IRIS-PARAM-NUM > 13
    if (params.irisWorkArea.irisParamNum.greaterThan(13)) {
      // COB(1236): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1237): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1238): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1238): OR LK-SSA-11(WS-IDX:1) = '('
      // COB(1238): OR LK-SSA-11(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa11.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa11.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1241): CONTINUE
        // do nothing
        // COB(1242): END-PERFORM
      }
      // COB(1243): IF LK-SSA-11(WS-IDX:1) = '('
      if (params.lkSsa11.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1244): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1244): UNTIL WS-IDX > 540
        // COB(1244): OR LK-SSA-11(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa11.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1247): CONTINUE
          // do nothing
          // COB(1248): END-PERFORM
        }
        // COB(1249): END-IF
      }
      // COB(1250): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1250):                       ':SSA11=(' LK-SSA-11(1:WS-IDX) ')'
      // COB(1250): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA11=(",
              params.lkSsa11.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1253): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1254): END-IF
    }
    // COB(1256): IF IRIS-PARAM-NUM > 14
    if (params.irisWorkArea.irisParamNum.greaterThan(14)) {
      // COB(1257): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1258): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1259): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1259): OR LK-SSA-12(WS-IDX:1) = '('
      // COB(1259): OR LK-SSA-12(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa12.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa12.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1262): CONTINUE
        // do nothing
        // COB(1263): END-PERFORM
      }
      // COB(1264): IF LK-SSA-12(WS-IDX:1) = '('
      if (params.lkSsa12.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1265): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1265): UNTIL WS-IDX > 540
        // COB(1265): OR LK-SSA-12(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa12.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1268): CONTINUE
          // do nothing
          // COB(1269): END-PERFORM
        }
        // COB(1270): END-IF
      }
      // COB(1271): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1271):                       ':SSA12=(' LK-SSA-12(1:WS-IDX) ')'
      // COB(1271): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA12=(",
              params.lkSsa12.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1274): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1275): END-IF
    }
    // COB(1277): IF IRIS-PARAM-NUM > 15
    if (params.irisWorkArea.irisParamNum.greaterThan(15)) {
      // COB(1278): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1279): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1280): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1280): OR LK-SSA-13(WS-IDX:1) = '('
      // COB(1280): OR LK-SSA-13(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa13.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa13.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1283): CONTINUE
        // do nothing
        // COB(1284): END-PERFORM
      }
      // COB(1285): IF LK-SSA-13(WS-IDX:1) = '('
      if (params.lkSsa13.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1286): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1286): UNTIL WS-IDX > 540
        // COB(1286): OR LK-SSA-13(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa13.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1289): CONTINUE
          // do nothing
          // COB(1290): END-PERFORM
        }
        // COB(1291): END-IF
      }
      // COB(1292): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1292):                       ':SSA13=(' LK-SSA-13(1:WS-IDX) ')'
      // COB(1292): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA13=(",
              params.lkSsa13.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1295): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1296): END-IF
    }
    // COB(1298): IF IRIS-PARAM-NUM > 16
    if (params.irisWorkArea.irisParamNum.greaterThan(16)) {
      // COB(1299): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1300): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1301): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1301): OR LK-SSA-14(WS-IDX:1) = '('
      // COB(1301): OR LK-SSA-14(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa14.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa14.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1304): CONTINUE
        // do nothing
        // COB(1305): END-PERFORM
      }
      // COB(1306): IF LK-SSA-14(WS-IDX:1) = '('
      if (params.lkSsa14.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1307): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1307): UNTIL WS-IDX > 540
        // COB(1307): OR LK-SSA-14(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa14.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1310): CONTINUE
          // do nothing
          // COB(1311): END-PERFORM
        }
        // COB(1312): END-IF
      }
      // COB(1313): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1313):                       ':SSA14=(' LK-SSA-14(1:WS-IDX) ')'
      // COB(1313): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA14=(",
              params.lkSsa14.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1316): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1317): END-IF
    }
    // COB(1319): IF IRIS-PARAM-NUM > 17
    if (params.irisWorkArea.irisParamNum.greaterThan(17)) {
      // COB(1320): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1321): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1322): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1322): OR LK-SSA-15(WS-IDX:1) = '('
      // COB(1322): OR LK-SSA-15(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa15.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa15.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1325): CONTINUE
        // do nothing
        // COB(1326): END-PERFORM
      }
      // COB(1327): IF LK-SSA-15(WS-IDX:1) = '('
      if (params.lkSsa15.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1328): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1328): UNTIL WS-IDX > 540
        // COB(1328): OR LK-SSA-15(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa15.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1331): CONTINUE
          // do nothing
          // COB(1332): END-PERFORM
        }
        // COB(1333): END-IF
      }
      // COB(1334): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1334):                       ':SSA15=(' LK-SSA-15(1:WS-IDX) ')'
      // COB(1334): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA15=(",
              params.lkSsa15.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1337): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1338): END-IF
    }
    // COB(1340): IF IRIS-PARAM-NUM > 18
    if (params.irisWorkArea.irisParamNum.greaterThan(18)) {
      // COB(1341): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1342): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1343): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1343): OR LK-SSA-16(WS-IDX:1) = '('
      // COB(1343): OR LK-SSA-16(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa16.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa16.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1346): CONTINUE
        // do nothing
        // COB(1347): END-PERFORM
      }
      // COB(1348): IF LK-SSA-16(WS-IDX:1) = '('
      if (params.lkSsa16.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1349): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1349): UNTIL WS-IDX > 540
        // COB(1349): OR LK-SSA-16(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa16.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1352): CONTINUE
          // do nothing
          // COB(1353): END-PERFORM
        }
        // COB(1354): END-IF
      }
      // COB(1355): STRING '<IRISTRACE> - DBPAUTP0'
      // COB(1355):                       ':SSA16=(' LK-SSA-16(1:WS-IDX) ')'
      // COB(1355): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTP0",
              ":SSA16=(",
              params.lkSsa16.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1358): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1359): END-IF
    }
  }

  /***
   ******************************************************************
   *    EXTRACT PCB WHEN EXEC DLI CASE
   ******************************************************************
   */
  protected void extractPcbExec() {
    // COB(1373): MOVE 'EXTRACT-PCB-EXEC' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("EXTRACT-PCB-EXEC");
    // COB(1375): SET WS-NOT-FOUND TO TRUE
    ws.setWsNotFound(true);
    // COB(1379): SET ADDRESS OF LK-DIB-BLOCK TO ADDRESS OF IRIS-DB-PCB
    //       *    IF IRIS-EXEC-DLI-PTR IS NOT NULL
    //       *      SET ADDRESS OF IRIS-LK-CELLS TO IRIS-EXEC-DLI-PTR
    //       *    END-IF
    params.lkDibBlock.setAddress(params.irisDbPcb.getAddress());
    // COB(1380): MOVE LK-DIB-BLOCK TO IRIS-DIB-BLOCK
    ws.iriscomm.irisDibBlock.setValue(params.lkDibBlock);
    // COB(1381): SET ADDRESS OF IRIS-DB-PCB TO
    // COB(1381):             ADDRESS OF IRIS-PCBS-TAB-IO(IRIS-PCB-NUM)
    params.irisDbPcb.setAddress(
        ws.irisPcbsArea
            .irisPcbsTabIoAtIndex(params.irisWorkArea.irisPcbNum.getInt() - 1)
            .getAddress());
  }

  /***
   ******************************************************************
   *    INITIALIZE VARIABLES
   ******************************************************************
   */
  protected void initVariables() {
    // COB(1395): MOVE 'INIT-VARIABLES' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("INIT-VARIABLES");
    // COB(1397): MOVE IRIS-SEGMENT TO WS-SEGMENT-NAME
    ws.wsSegmentName.setValue(params.irisWorkArea.irisSegment);
    // COB(1398): IF IRIS-FUNC-REPL AND NOT IRIS-EXECDLI
    if (params.irisWorkArea.irisFuncRepl() && !params.irisWorkArea.irisExecdli()) {
      // COB(1399): MOVE LAST-IMS-CCODE TO IRIS-KEYVALUE-TAB(1:240)
      params.irisWorkArea.irisKeyvalueTab.setValue(
          params.irisDbPcb.ioRtnUsedKeysPcbArea.filler584.lastImsCcode, 240);
      // COB(1400): END-IF
    }
    // COB(1401): SET DB-STATUS-OK TO TRUE
    params.irisDbPcb.dbPcbFixedPart.setDbStatusOk(true);
    // COB(1402): SET IRIS-SQL-OK TO TRUE
    ws.irissqlc.setIrisSqlOk(true);
    // COB(1403): SET IRIS-NO-ERROR TO TRUE
    params.irisWorkArea.setIrisNoError(true);
    // COB(1404): SET COMMAND-WITHOUT-HOLD TO TRUE
    ws.setCommandWithoutHold(true);
    // COB(1405): SET HAS-NOT-PATHCALLS TO TRUE
    ws.setHasNotPathcalls(true);
    // COB(1406): SET HAS-PATHCALLS-NO-ERROR TO TRUE
    ws.setHasPathcallsNoError(true);
    // COB(1407): SET IS-NOT-PATHCALL-REVERSE TO TRUE
    ws.setIsNotPathcallReverse(true);
    // COB(1408): SET EXEC-GE-PATH-CALL TO TRUE
    params.irisWorkArea.setExecGePathCall(true);
    // COB(1409): MOVE ZERO TO WS-PATHCALL-LEVEL
    ws.wsPathcallLevel.setValue(0);
    // COB(1410): SET DEFAULT-DYNAMIC-ACCESS TO TRUE
    ws.setDefaultDynamicAccess(true);
    // COB(1411): SET CUSTOM-STATIC-ACCESS TO TRUE
    ws.setCustomStaticAccess(true);
    // COB(1412): SET EXEC-SEGMENT-ACCESS TO TRUE
    ws.setExecSegmentAccess(true);
    // COB(1413): SET FULL-SCAN-FALSE TO TRUE
    ws.setFullScanFalse(true);
    // COB(1414): IF IRIS-FUNC-GHU
    // COB(1414): OR IRIS-FUNC-GHN
    // COB(1414): OR IRIS-FUNC-GHNP
    if (params.irisWorkArea.irisFuncGhu()
        || params.irisWorkArea.irisFuncGhn()
        || params.irisWorkArea.irisFuncGhnp()) {
      // COB(1417): SET COMMAND-WITH-HOLD TO TRUE
      ws.setCommandWithHold(true);
      // COB(1418): END-IF
    }
    // COB(1419): MOVE ZERO  TO IRIS-SQLCODE
    // COB(1419):               IRIS-SQLERRML
    // COB(1419):               WS-FB-KEY-LENGTH
    // COB(1419):               WS-SEGMENT-LEVEL
    // COB(1419):               WS-SEGMENT-LEN
    // COB(1419):               WS-LAST-SEGMENT-LEVEL
    // COB(1419):               WS-PATHCALL-LEN
    // COB(1419):               WS-INIT-PATHCALL-LEN
    // COB(1419):               SQL-CONDITION-CLAUSE-LENGTH
    // COB(1419):               SQL-JOIN-CLAUSE-LENGTH
    // COB(1419):               SQL-ORDERBY-CLAUSE-LENGTH
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(0);
    params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrml.setValue(0);
    ws.wsFbKeyLength.setValue(0);
    ws.wsSegmentLevel.setValue(0);
    ws.wsSegmentLen.setValue(0);
    ws.wsLastSegmentLevel.setValue(0);
    ws.wsPathcallLen.setValue(0);
    ws.wsInitPathcallLen.setValue(0);
    ws.sqlConditionClause.sqlConditionClauseLength.setValue(0);
    ws.sqlJoinClause.sqlJoinClauseLength.setValue(0);
    ws.sqlOrderbyClause.sqlOrderbyClauseLength.setValue(0);
    // COB(1430): MOVE 1 TO WS-FB-KEY-LENGTH
    // COB(1430):           IRIS-SQLERRML
    ws.wsFbKeyLength.setValue(1);
    params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrml.setValue(1);
    // COB(1432): EVALUATE WS-SEGMENT-NAME
    // COB(1433): WHEN 'PAUTSUM0'
    if (ws.wsSegmentName.getValue().equals("PAUTSUM0")) {
      // COB(1434): MOVE PAUTSUM0-LVL TO WS-INIT-SEGMENT-LEVEL
      ws.wsInitSegmentLevel.setValue(ws.pautsum0Lvl);
      // COB(1435): WHEN 'PAUTDTL1'
    } else if (ws.wsSegmentName.getValue().equals("PAUTDTL1")) {
      // COB(1436): MOVE PAUTDTL1-LVL TO WS-INIT-SEGMENT-LEVEL
      ws.wsInitSegmentLevel.setValue(ws.pautdtl1Lvl);
      // COB(1437): END-EVALUATE
    }
    // COB(1438): MOVE SPACE TO IRIS-SQLERRMC
    // COB(1438):               WS-LAST-SEGMENT-NAME
    // COB(1438):               WS-LAST-IORTN-SECTION
    params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrmc.spaces();
    ws.wsLastSegmentName.spaces();
    ws.wsLastIortnSection.spaces();
  }

  /***
   ******************************************************************
   *    FINALIZE VARIABLES
   ******************************************************************
   */
  protected void finalizeVariables() {
    // COB(1453): MOVE 'FINALIZE-VARIABLES' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("FINALIZE-VARIABLES");
    // COB(1455): IF IO-RTN-FB-KEY-MAX-LENGTH > 0
    // COB(1455): AND IO-RTN-FB-KEY-MAX-LENGTH < WS-FB-KEY-LENGTH
    if (params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnFbKeyMaxLength.greaterThan(0)
        && params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnFbKeyMaxLength.lessThan(
            ws.wsFbKeyLength)) {
      // COB(1457): MOVE IO-RTN-FB-KEY-MAX-LENGTH TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(
          params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnFbKeyMaxLength);
      // COB(1458): END-IF
    }
    // COB(1462): IF IRIS-NO-ERROR
    //       *
    //       *    SETUP PCB INFO
    //       *
    if (params.irisWorkArea.irisNoError()) {
      // COB(1463): IF DB-PCB-DBD-NAME = SPACE OR LOW-VALUE
      if (params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.isSpaces()
          || params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.isLowValues()) {
        // COB(1464): MOVE WS-DBD-NAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsDbdName);
        // COB(1465): END-IF
      }
      // COB(1466): MOVE WS-FB-KEY-LENGTH TO DB-PCB-FB-KEY-LENGTH
      params.irisDbPcb.dbPcbFixedPart.dbPcbFbKeyLength.setValue(ws.wsFbKeyLength);
      // COB(1467): IF WS-FB-KEY-LENGTH > ZERO
      if (ws.wsFbKeyLength.greaterThan(0)) {
        // COB(1468): MOVE WS-FB-KEY-AREA(1:WS-FB-KEY-LENGTH)
        // COB(1468):                     TO DB-PCB-KEY-FB (1:WS-FB-KEY-LENGTH)
        params.irisDbPcb.dbPcbKeyFb.setValue(ws.wsFbKeyArea, ws.wsFbKeyLength.getInt());
        // COB(1470): END-IF
      }
      // COB(1471): MOVE WS-LAST-SEGMENT-NAME TO DB-PCB-SEGMENT-NAME
      params.irisDbPcb.dbPcbFixedPart.dbPcbSegmentName.setValue(ws.wsLastSegmentName);
      // COB(1472): MOVE WS-LAST-SEGMENT-LEVEL TO DB-PCB-SEGMENT-LEVEL
      params.irisDbPcb.dbPcbFixedPart.dbPcbSegmentLevel.setValue(ws.wsLastSegmentLevel);
      // COB(1473): ADD 1 TO WS-INIT-SEGMENT-LEVEL GIVING WS-IDX
      ws.wsIdx.add(1).add(ws.wsInitSegmentLevel);
      // COB(1474): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
      // COB(1474):         UNTIL WS-IDX > WS-SEGMENTS-MAX-LVL
      ws.wsIdx.setValue(ws.wsIdx);
      for (; !ws.wsIdx.greaterThan(ws.wsSegmentsMaxLvl); ws.wsIdx.add(1)) {
        // COB(1476): MOVE LOW-VALUE TO IO-RTN-USED-KEY-STATUS(WS-IDX)
        // COB(1476):                   IO-RTN-USED-SSA-KEYS(WS-IDX)
        // COB(1476):                   IO-RTN-USED-SSA-INFO(WS-IDX)
        params
            .irisDbPcb
            .ioRtnUsedKeysPcbArea
            .ioRtnUsedKeysArea
            .ioRtnUsedKeyValueAtIndex(ws.wsIdx.getInt() - 1)
            .ioRtnUsedKeyStatus
            .lowValues();
        params
            .irisDbPcb
            .ioRtnUsedKeysPcbArea
            .ioRtnUsedKeysArea
            .ioRtnUsedKeyValueAtIndex(ws.wsIdx.getInt() - 1)
            .ioRtnUsedSsaKeys
            .lowValues();
        params
            .irisDbPcb
            .ioRtnUsedKeysPcbArea
            .ioRtnUsedKeysArea
            .ioRtnUsedKeyValueAtIndex(ws.wsIdx.getInt() - 1)
            .ioRtnUsedSsaInfo
            .lowValues();
        // COB(1479): END-PERFORM
      }
      // COB(1480): IF (IRIS-FUNC-GU OR IRIS-FUNC-GHU
      // COB(1480): OR IRIS-FUNC-ISRT)
      // COB(1480): AND WS-LAST-SEGMENT-LEVEL > ZERO
      if ((params.irisWorkArea.irisFuncGu()
              || params.irisWorkArea.irisFuncGhu()
              || params.irisWorkArea.irisFuncIsrt())
          && ws.wsLastSegmentLevel.greaterThan(0)) {
        // COB(1483): MOVE ZERO TO
        // COB(1483):   IO-RTN-LAST-OPEN-CURSOR(WS-LAST-SEGMENT-LEVEL)
        params
            .irisDbPcb
            .ioRtnUsedKeysPcbArea
            .ioRtnUsedKeysArea
            .ioRtnUsedKeyValueAtIndex(ws.wsLastSegmentLevel.getInt() - 1)
            .ioRtnUsedSsaKeys
            .ioRtnLastOpenCursor
            .setValue(0);
        // COB(1485): END-IF
      }
      // COB(1489): MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
      //       *
      //       *    SETUP STATUS
      //       *
      ws.irissqlc.irisDbSqlcode.setValue(params.irisWorkArea.irisSqlError.irisSqlcode);
      // COB(1490): EVALUATE TRUE
      // COB(1491): WHEN IRIS-SQL-OK
      if (ws.irissqlc.irisSqlOk()) {
        // COB(1492): SET DB-STATUS-OK TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusOk(true);
        // COB(1493): WHEN IRIS-SQL-UNIQ-CONSTR-VIOLATED
      } else if (ws.irissqlc.irisSqlUniqConstrViolated()) {
        // COB(1494): SET DB-STATUS-DUPLICATED-KEY TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusDuplicatedKey(true);
        // COB(1495): WHEN IRIS-SQL-DEADLOCK
      } else if (ws.irissqlc.irisSqlDeadlock()) {
        // COB(1496): SET DB-STATUS-RESOURCE-DEADLOCK TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusResourceDeadlock(true);
        // COB(1497): WHEN IRIS-SQL-INTE-CONSTR-VIOLATED
      } else if (ws.irissqlc.irisSqlInteConstrViolated()) {
        // COB(1498): SET DB-STATUS-SEGMENT-NOT-FOUND TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusSegmentNotFound(true);
        // COB(1499): WHEN IRIS-SQL-NOT-FOUND AND FULL-SCAN-FALSE
      } else if (ws.irissqlc.irisSqlNotFound() && ws.fullScanFalse()) {
        // COB(1500): SET DB-STATUS-SEGMENT-NOT-FOUND TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusSegmentNotFound(true);
        // COB(1501): WHEN IRIS-SQL-END-DB-REACHED
      } else if (ws.irissqlc.irisSqlEndDbReached()
          // COB(1502): WHEN IRIS-SQL-NOT-FOUND AND FULL-SCAN-TRUE
          || ws.irissqlc.irisSqlNotFound() && ws.fullScanTrue()) {
        // COB(1503): SET DB-STATUS-END-DB-REACHED TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusEndDbReached(true);
        // COB(1504): WHEN IRIS-SQL-CHG-SEG
      } else if (ws.irissqlc.irisSqlChgSeg()) {
        // COB(1505): SET DB-STATUS-CHANGED-SEGMENT-TYPE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusChangedSegmentType(true);
        // COB(1506): WHEN IRIS-SQL-CHG-LEV
      } else if (ws.irissqlc.irisSqlChgLev()) {
        // COB(1507): SET DB-STATUS-HIGHER-LEVEL-CROSSED TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusHigherLevelCrossed(true);
        // COB(1508): WHEN IRIS-SQL-DUAL-PCB-MISMATCH
      } else if (ws.irissqlc.irisSqlDualPcbMismatch()) {
        // COB(1509): SET DB-STATUS-DUAL-PCB-MISMATCH TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusDualPcbMismatch(true);
        // COB(1510): WHEN IRIS-SQL-DUAL-IOAREA-MISMATCH
      } else if (ws.irissqlc.irisSqlDualIoareaMismatch()) {
        // COB(1511): SET DB-STATUS-DUAL-IOAREA-MISMATCH TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusDualIoareaMismatch(true);
        // COB(1512): WHEN OTHER
      } else {
        // COB(1513): SET DB-STATUS-INTERNAL-NOT-HANDLED TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusInternalNotHandled(true);
        // COB(1514): SET IRIS-ERR-UNHANDLED-SQLCODE TO TRUE
        params.irisWorkArea.setIrisErrUnhandledSqlcode(true);
        // COB(1515): END-EVALUATE
      }
      // COB(1516): IF IRIS-SQL-CHG-SEG
      // COB(1516): OR IRIS-SQL-CHG-LEV
      if (ws.irissqlc.irisSqlChgSeg() || ws.irissqlc.irisSqlChgLev()) {
        // COB(1518): SET IRIS-SQL-OK TO TRUE
        ws.irissqlc.setIrisSqlOk(true);
        // COB(1519): MOVE IRIS-DB-SQLCODE TO IRIS-SQLCODE
        params.irisWorkArea.irisSqlError.irisSqlcode.setValue(ws.irissqlc.irisDbSqlcode);
        // COB(1520): END-IF
      }
      // COB(1521): ELSE
    } else {
      // COB(1522): SET DB-STATUS-INTERNAL-NOT-HANDLED TO TRUE
      params.irisDbPcb.dbPcbFixedPart.setDbStatusInternalNotHandled(true);
      // COB(1523): END-IF
    }
    // COB(1524): IF EXEC-SAVE-AREA
    if (ws.execSaveArea()) {
      // COB(1525): SET ADDRESS OF LK-IO-AREA-BACKUP TO RUN-IO-AREA-PTR
      params.lkIoAreaBackup.setAddress(
          params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.runIoAreaPtr);
      // COB(1526): MOVE LK-IO-AREA(1:WS-SEGMENT-LEN) TO
      // COB(1526):      LK-IO-AREA-BACKUP(1:WS-SEGMENT-LEN)
      params.lkIoAreaBackup.setValue(params.lkIoArea, ws.wsSegmentLen.getInt());
      // COB(1528): END-IF
    }
    // COB(1529): SET SKIP-GE-PATH-CALL TO TRUE
    params.irisWorkArea.setSkipGePathCall(true);
    // COB(1531): IF WS-FB-KEY-LENGTH > 0
    // COB(1531): AND IRIS-KFB-YES
    if (ws.wsFbKeyLength.greaterThan(0) && params.irisWorkArea.irisKfbYes()) {
      // COB(1533): IF IRIS-KFB-LENGTH IS NUMERIC
      if (params.irisWorkArea.irisKfbLength.isNumeric()) {
        // COB(1534): IF IRIS-KFB-LENGTH < WS-FB-KEY-LENGTH
        // COB(1534): AND IRIS-KFB-LENGTH NOT = ZERO
        if (params.irisWorkArea.irisKfbLength.lessThan(ws.wsFbKeyLength)
            && !params.irisWorkArea.irisKfbLength.equals(0)) {
          // COB(1536): MOVE IRIS-KFB-LENGTH TO WS-FB-KEY-LENGTH
          ws.wsFbKeyLength.setValue(params.irisWorkArea.irisKfbLength);
          // COB(1537): END-IF
        }
        // COB(1538): END-IF
      }
      // COB(1539): EVALUATE IRIS-PARAM-NUM
      switch (params.irisWorkArea.irisParamNum.getInt()) {
          // COB(1540): WHEN 4
        case 4:
          // COB(1541): MOVE DB-PCB-KEY-FB(1:WS-FB-KEY-LENGTH)
          // COB(1541):   TO LK-SSA-01(1:WS-FB-KEY-LENGTH)
          params.lkSsa01.setValue(params.irisDbPcb.dbPcbKeyFb, ws.wsFbKeyLength.getInt());
          // COB(1543): WHEN 5
          break;
        case 5:
          // COB(1544): MOVE DB-PCB-KEY-FB(1:WS-FB-KEY-LENGTH)
          // COB(1544):   TO LK-SSA-02(1:WS-FB-KEY-LENGTH)
          params.lkSsa02.setValue(params.irisDbPcb.dbPcbKeyFb, ws.wsFbKeyLength.getInt());
          // COB(1546): WHEN 6
          break;
        case 6:
          // COB(1547): MOVE DB-PCB-KEY-FB(1:WS-FB-KEY-LENGTH)
          // COB(1547):   TO LK-SSA-03(1:WS-FB-KEY-LENGTH)
          params.lkSsa03.setValue(params.irisDbPcb.dbPcbKeyFb, ws.wsFbKeyLength.getInt());
          // COB(1549): WHEN 7
          break;
        default:
          break;
        case 7:
          // COB(1550): MOVE DB-PCB-KEY-FB(1:WS-FB-KEY-LENGTH)
          // COB(1550):   TO LK-SSA-04(1:WS-FB-KEY-LENGTH)
          params.lkSsa04.setValue(params.irisDbPcb.dbPcbKeyFb, ws.wsFbKeyLength.getInt());
          // COB(1552): END-EVALUATE
      }
      // COB(1553): SET IRIS-KFB-NO TO TRUE
      params.irisWorkArea.setIrisKfbNo(true);
      // COB(1554): MOVE ZERO TO IRIS-KFB-LENGTH
      params.irisWorkArea.irisKfbLength.setValue(0);
      // COB(1555): END-IF
    }
  }

  /***
   ******************************************************************
   *    HANDLE SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void handleSegmentPautsum0() {
    // COB(1568): MOVE PAUTSUM0-LVL TO WS-SEGMENT-LEVEL
    ws.wsSegmentLevel.setValue(ws.pautsum0Lvl);
    // COB(1569): MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN WS-INIT-PATHCALL-LEN
    ws.wsSegmentLen.setValue(ws.pautsum0Len);
    ws.wsInitPathcallLen.setValue(ws.pautsum0Len);
    // COB(1571): MOVE 'HANDLE-SEGMENT-PAUTSUM0' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("HANDLE-SEGMENT-PAUTSUM0");
    // COB(1573): EVALUATE TRUE
    // COB(1574): WHEN SQL-SELECT-PRIMARY
    if (params.irisWorkArea.sqlSelectPrimary()) {
      // COB(1575): PERFORM PAUTSUM0-SELECT-PRIMARY-KEY
      pautsum0SelectPrimaryKey();
      // COB(1576): WHEN SQL-SELECT-SEEK
    } else if (params.irisWorkArea.sqlSelectSeek()) {
      // COB(1577): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(1578): EVALUATE TRUE
      // COB(1579): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(1580): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(1581): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(1582): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(1583): END-EVALUATE
      }
      // COB(1585): PERFORM PAUTSUM0-SELECT-FIRST
      pautsum0SelectFirst();
      // COB(1586): WHEN SQL-SELECT-NEXT
    } else if (params.irisWorkArea.sqlSelectNext()) {
      // COB(1587): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(1588): EVALUATE TRUE
      // COB(1589): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(1590): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(1591): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(1592): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(1593): END-EVALUATE
      }
      // COB(1595): PERFORM PAUTSUM0-SELECT-NEXT
      pautsum0SelectNext();
      // COB(1596): WHEN SQL-SELECT-DYNAMIC
    } else if (params.irisWorkArea.sqlSelectDynamic()) {
      // COB(1597): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(1598): EVALUATE TRUE
      // COB(1599): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(1600): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(1601): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(1602): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(1603): END-EVALUATE
      }
      // COB(1605): PERFORM PAUTSUM0-SELECT-DYNAMIC
      pautsum0SelectDynamic();
      // COB(1606): WHEN SQL-SELECT-PATH
    } else if (params.irisWorkArea.sqlSelectPath()) {
      // COB(1607): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(1608): EVALUATE TRUE
      // COB(1609): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(1610): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(1611): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(1612): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(1613): END-EVALUATE
      }
      // COB(1615): PERFORM PAUTSUM0-SELECT-DYNAMIC
      pautsum0SelectDynamic();
      // COB(1616): WHEN SQL-INSERT
    } else if (params.irisWorkArea.sqlInsert()) {
      // COB(1617): SET COMMAND-CODE-HERE TO TRUE
      ws.setCommandCodeHere(true);
      // COB(1618): EVALUATE TRUE
      // COB(1619): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(1620): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(1621): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(1622): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(1623): END-EVALUATE
      }
      // COB(1625): PERFORM PAUTSUM0-INSERT
      pautsum0Insert();
      // COB(1626): WHEN SQL-UPDATE
    } else if (params.irisWorkArea.sqlUpdate()) {
      // COB(1627): PERFORM PAUTSUM0-UPDATE
      pautsum0Update();
      // COB(1628): WHEN SQL-DELETE
    } else if (params.irisWorkArea.sqlDelete()) {
      // COB(1629): PERFORM PAUTSUM0-DELETE
      pautsum0Delete();
      // COB(1630): WHEN OTHER
    } else {
      // COB(1631): SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
      params.irisWorkArea.setIrisErrFunctionNotFound(true);
      // COB(1632): END-EVALUATE
    }
    // COB(1634): IF IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(1635): MOVE PAUTSUM0-LVL TO WS-LAST-SEGMENT-LEVEL
      ws.wsLastSegmentLevel.setValue(ws.pautsum0Lvl);
      // COB(1636): MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
      ws.wsLastSegmentName.setString("PAUTSUM0");
      // COB(1637): END-IF.
    }
  }

  /***
   ******************************************************************
   *    SELECT ON PRIMARY KEY FOR SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0SelectPrimaryKey() {
    // COB(1649): MOVE 'PAUTSUM0-SELECT-PRIMARY-KEY' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-SELECT-PRIMARY-KEY");
    // COB(1651): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(1652): MOVE ZERO
      // COB(1652):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler173.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(1654): ELSE
    } else {
      // COB(1655): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(1655):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler173.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1657): END-IF
    }
    // COB(1658): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(1659): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(1660): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(1661): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(1661):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler249.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(1663): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(1664): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(1664):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler249
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(1666): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler173.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(1667): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(1668): END-IF
    }
    // COB(1669): MOVE WS-PACKED-FLD-18-00(1) TO KEY-ACCNTID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.keyAccntid.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(1670): EXEC SQL
    // COB(1670): SELECT
    // COB(1670):   KEY_ACCNTID
    // COB(1670):  ,PA_CUST_ID
    // COB(1670):  ,PA_AUTH_STATUS
    // COB(1670):  ,PA_ACCOUNT_STATUS1
    // COB(1670):  ,PA_ACCOUNT_STATUS2
    // COB(1670):  ,PA_ACCOUNT_STATUS3
    // COB(1670):  ,PA_ACCOUNT_STATUS4
    // COB(1670):  ,PA_ACCOUNT_STATUS5
    // COB(1670):  ,PA_CREDIT_LIMIT
    // COB(1670):  ,PA_CASH_LIMIT
    // COB(1670):  ,PA_CREDIT_BALANCE
    // COB(1670):  ,PA_CASH_BALANCE
    // COB(1670):  ,PA_APPROVED_AUTH_CNT
    // COB(1670):  ,PA_DECLINED_AUTH_CNT
    // COB(1670):  ,PA_APPROVED_AUTH_AMT
    // COB(1670):  ,PA_DECLINED_AUTH_AMT
    // COB(1670):  ,PAUTSUM0_FILLER
    // COB(1670): INTO
    // COB(1670):   :PAUTSUM0.KEY-ACCNTID
    // COB(1670):  ,:PAUTSUM0.PA-CUST-ID
    // COB(1670):  ,:PAUTSUM0.PA-AUTH-STATUS
    // COB(1670):  ,:PAUTSUM0.PA-ACCOUNT-STATUS1
    // COB(1670):  ,:PAUTSUM0.PA-ACCOUNT-STATUS2
    // COB(1670):  ,:PAUTSUM0.PA-ACCOUNT-STATUS3
    // COB(1670):  ,:PAUTSUM0.PA-ACCOUNT-STATUS4
    // COB(1670):  ,:PAUTSUM0.PA-ACCOUNT-STATUS5
    // COB(1670):  ,:PAUTSUM0.PA-CREDIT-LIMIT
    // COB(1670):  ,:PAUTSUM0.PA-CASH-LIMIT
    // COB(1670):  ,:PAUTSUM0.PA-CREDIT-BALANCE
    // COB(1670):  ,:PAUTSUM0.PA-CASH-BALANCE
    // COB(1670):  ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
    // COB(1670):  ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
    // COB(1670):  ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
    // COB(1670):  ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
    // COB(1670):  ,:PAUTSUM0.PAUTSUM0-FILLER
    // COB(1670): FROM
    // COB(1670): DBPAUTP0_PAUTSUM0
    // COB(1670):   WHERE
    // COB(1670):     KEY_ACCNTID =
    // COB(1670):   :PAUTSUM0.KEY-ACCNTID
    // COB(1670): END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand(
          "SELECT KEY_ACCNTID, PA_CUST_ID, PA_AUTH_STATUS, PA_ACCOUNT_STATUS1, PA_ACCOUNT_STATUS2,"
              + " PA_ACCOUNT_STATUS3, PA_ACCOUNT_STATUS4, PA_ACCOUNT_STATUS5, PA_CREDIT_LIMIT,"
              + " PA_CASH_LIMIT, PA_CREDIT_BALANCE, PA_CASH_BALANCE, PA_APPROVED_AUTH_CNT,"
              + " PA_DECLINED_AUTH_CNT, PA_APPROVED_AUTH_AMT, PA_DECLINED_AUTH_AMT, PAUTSUM0_FILLER"
              + " FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID = ?");
      sql.into(
          ws.pautsum0copy.pautsum0.keyAccntid,
          ws.pautsum0copy.pautsum0.paCustId,
          ws.pautsum0copy.pautsum0.paAuthStatus,
          ws.pautsum0copy.pautsum0.paAccountStatus1,
          ws.pautsum0copy.pautsum0.paAccountStatus2,
          ws.pautsum0copy.pautsum0.paAccountStatus3,
          ws.pautsum0copy.pautsum0.paAccountStatus4,
          ws.pautsum0copy.pautsum0.paAccountStatus5,
          ws.pautsum0copy.pautsum0.paCreditLimit,
          ws.pautsum0copy.pautsum0.paCashLimit,
          ws.pautsum0copy.pautsum0.paCreditBalance,
          ws.pautsum0copy.pautsum0.paCashBalance,
          ws.pautsum0copy.pautsum0.paApprovedAuthCnt,
          ws.pautsum0copy.pautsum0.paDeclinedAuthCnt,
          ws.pautsum0copy.pautsum0.paApprovedAuthAmt,
          ws.pautsum0copy.pautsum0.paDeclinedAuthAmt,
          ws.pautsum0copy.pautsum0.pautsum0Filler);
      sql.where(ws.pautsum0copy.pautsum0.keyAccntid);
      sql.execute();
    }
    // COB(1714): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(1715): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(1716): IF SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(1717): MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1718): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(1718):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(1720): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(1720):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1722): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(1722):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(1724): MOVE 6 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(6);
      // COB(1728): MOVE PA-CUST-ID OF PAUTSUM0 TO
      // COB(1728): WS-PACKED-FLD-18-00(1)
      //       *
      //       * PACKED FIELDS VS SIGNED ZONED FITTING
      //       *
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.paCustId);
      // COB(1730): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(1730): PA-CUST-ID-N OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCustIdN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
      // COB(1732): MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(ws.pautsum0Len);
      // COB(1733): PERFORM PAUTSUM0-SET-IO-AREA
      pautsum0SetIoArea();
      // COB(1734): SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(1735): MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
      // COB(1735):                 IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
      ws.wsLastSegmentName.setString("PAUTSUM0");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTSUM0");
      // COB(1737): END-IF
    }
    // COB(1739): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(1740): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    SELECT FIRST TIME FOR SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0SelectFirst() {
    // COB(1751): MOVE 'PAUTSUM0-SELECT-FIRST' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-SELECT-FIRST");
    // COB(1753): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(1754): MOVE ZERO
      // COB(1754):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler173.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(1756): ELSE
    } else {
      // COB(1757): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(1757):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler173.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1759): END-IF
    }
    // COB(1760): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(1761): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(1762): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(1763): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(1763):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler249.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(1765): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(1766): IF COMMAND-CODE-LAST
    if (ws.commandCodeLast()) {
      // COB(1767): EXEC SQL
      // COB(1767): SELECT
      // COB(1767):   KEY_ACCNTID
      // COB(1767):  ,PA_CUST_ID
      // COB(1767):  ,PA_AUTH_STATUS
      // COB(1767):  ,PA_ACCOUNT_STATUS1
      // COB(1767):  ,PA_ACCOUNT_STATUS2
      // COB(1767):  ,PA_ACCOUNT_STATUS3
      // COB(1767):  ,PA_ACCOUNT_STATUS4
      // COB(1767):  ,PA_ACCOUNT_STATUS5
      // COB(1767):  ,PA_CREDIT_LIMIT
      // COB(1767):  ,PA_CASH_LIMIT
      // COB(1767):  ,PA_CREDIT_BALANCE
      // COB(1767):  ,PA_CASH_BALANCE
      // COB(1767):  ,PA_APPROVED_AUTH_CNT
      // COB(1767):  ,PA_DECLINED_AUTH_CNT
      // COB(1767):  ,PA_APPROVED_AUTH_AMT
      // COB(1767):  ,PA_DECLINED_AUTH_AMT
      // COB(1767):  ,PAUTSUM0_FILLER
      // COB(1767): INTO
      // COB(1767):   :PAUTSUM0.KEY-ACCNTID
      // COB(1767):  ,:PAUTSUM0.PA-CUST-ID
      // COB(1767):  ,:PAUTSUM0.PA-AUTH-STATUS
      // COB(1767):  ,:PAUTSUM0.PA-ACCOUNT-STATUS1
      // COB(1767):  ,:PAUTSUM0.PA-ACCOUNT-STATUS2
      // COB(1767):  ,:PAUTSUM0.PA-ACCOUNT-STATUS3
      // COB(1767):  ,:PAUTSUM0.PA-ACCOUNT-STATUS4
      // COB(1767):  ,:PAUTSUM0.PA-ACCOUNT-STATUS5
      // COB(1767):  ,:PAUTSUM0.PA-CREDIT-LIMIT
      // COB(1767):  ,:PAUTSUM0.PA-CASH-LIMIT
      // COB(1767):  ,:PAUTSUM0.PA-CREDIT-BALANCE
      // COB(1767):  ,:PAUTSUM0.PA-CASH-BALANCE
      // COB(1767):  ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
      // COB(1767):  ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
      // COB(1767):  ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
      // COB(1767):  ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
      // COB(1767):  ,:PAUTSUM0.PAUTSUM0-FILLER
      // COB(1767): FROM
      // COB(1767): DBPAUTP0_PAUTSUM0
      // COB(1767):   WHERE
      // COB(1767):     KEY_ACCNTID = (
      // COB(1767):     SELECT
      // COB(1767):       KEY_ACCNTID
      // COB(1767):     FROM
      // COB(1767):       DBPAUTP0_PAUTSUM0
      // COB(1767):     ORDER BY 1 DESC LIMIT 1
      // COB(1767):     )
      // COB(1767): END-EXEC
      try (NSqlCommand sql = new NSqlCommand(sqlca)) {
        sql.setCommand(
            "SELECT KEY_ACCNTID, PA_CUST_ID, PA_AUTH_STATUS, PA_ACCOUNT_STATUS1,"
                + " PA_ACCOUNT_STATUS2, PA_ACCOUNT_STATUS3, PA_ACCOUNT_STATUS4, PA_ACCOUNT_STATUS5,"
                + " PA_CREDIT_LIMIT, PA_CASH_LIMIT, PA_CREDIT_BALANCE, PA_CASH_BALANCE,"
                + " PA_APPROVED_AUTH_CNT, PA_DECLINED_AUTH_CNT, PA_APPROVED_AUTH_AMT,"
                + " PA_DECLINED_AUTH_AMT, PAUTSUM0_FILLER FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID"
                + " = (SELECT KEY_ACCNTID FROM DBPAUTP0_PAUTSUM0 ORDER BY 1 DESC LIMIT 1)");
        sql.into(
            ws.pautsum0copy.pautsum0.keyAccntid,
            ws.pautsum0copy.pautsum0.paCustId,
            ws.pautsum0copy.pautsum0.paAuthStatus,
            ws.pautsum0copy.pautsum0.paAccountStatus1,
            ws.pautsum0copy.pautsum0.paAccountStatus2,
            ws.pautsum0copy.pautsum0.paAccountStatus3,
            ws.pautsum0copy.pautsum0.paAccountStatus4,
            ws.pautsum0copy.pautsum0.paAccountStatus5,
            ws.pautsum0copy.pautsum0.paCreditLimit,
            ws.pautsum0copy.pautsum0.paCashLimit,
            ws.pautsum0copy.pautsum0.paCreditBalance,
            ws.pautsum0copy.pautsum0.paCashBalance,
            ws.pautsum0copy.pautsum0.paApprovedAuthCnt,
            ws.pautsum0copy.pautsum0.paDeclinedAuthCnt,
            ws.pautsum0copy.pautsum0.paApprovedAuthAmt,
            ws.pautsum0copy.pautsum0.paDeclinedAuthAmt,
            ws.pautsum0copy.pautsum0.pautsum0Filler);
        sql.where();
        sql.execute();
      }
      // COB(1815): ELSE
    } else {
      // COB(1816): EXEC SQL
      // COB(1816):   SELECT
      // COB(1816):     KEY_ACCNTID
      // COB(1816):    ,PA_CUST_ID
      // COB(1816):    ,PA_AUTH_STATUS
      // COB(1816):    ,PA_ACCOUNT_STATUS1
      // COB(1816):    ,PA_ACCOUNT_STATUS2
      // COB(1816):    ,PA_ACCOUNT_STATUS3
      // COB(1816):    ,PA_ACCOUNT_STATUS4
      // COB(1816):    ,PA_ACCOUNT_STATUS5
      // COB(1816):    ,PA_CREDIT_LIMIT
      // COB(1816):    ,PA_CASH_LIMIT
      // COB(1816):    ,PA_CREDIT_BALANCE
      // COB(1816):    ,PA_CASH_BALANCE
      // COB(1816):    ,PA_APPROVED_AUTH_CNT
      // COB(1816):    ,PA_DECLINED_AUTH_CNT
      // COB(1816):    ,PA_APPROVED_AUTH_AMT
      // COB(1816):    ,PA_DECLINED_AUTH_AMT
      // COB(1816):    ,PAUTSUM0_FILLER
      // COB(1816):   INTO
      // COB(1816):     :PAUTSUM0.KEY-ACCNTID
      // COB(1816):    ,:PAUTSUM0.PA-CUST-ID
      // COB(1816):    ,:PAUTSUM0.PA-AUTH-STATUS
      // COB(1816):    ,:PAUTSUM0.PA-ACCOUNT-STATUS1
      // COB(1816):    ,:PAUTSUM0.PA-ACCOUNT-STATUS2
      // COB(1816):    ,:PAUTSUM0.PA-ACCOUNT-STATUS3
      // COB(1816):    ,:PAUTSUM0.PA-ACCOUNT-STATUS4
      // COB(1816):    ,:PAUTSUM0.PA-ACCOUNT-STATUS5
      // COB(1816):    ,:PAUTSUM0.PA-CREDIT-LIMIT
      // COB(1816):    ,:PAUTSUM0.PA-CASH-LIMIT
      // COB(1816):    ,:PAUTSUM0.PA-CREDIT-BALANCE
      // COB(1816):    ,:PAUTSUM0.PA-CASH-BALANCE
      // COB(1816):    ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
      // COB(1816):    ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
      // COB(1816):    ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
      // COB(1816):    ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
      // COB(1816):    ,:PAUTSUM0.PAUTSUM0-FILLER
      // COB(1816):   FROM
      // COB(1816):   DBPAUTP0_PAUTSUM0
      // COB(1816):   WHERE
      // COB(1816):     KEY_ACCNTID = (
      // COB(1816):     SELECT
      // COB(1816):       KEY_ACCNTID
      // COB(1816):     FROM
      // COB(1816):       DBPAUTP0_PAUTSUM0
      // COB(1816):     ORDER BY 1 LIMIT 1
      // COB(1816):     )
      // COB(1816): END-EXEC
      try (NSqlCommand sql = new NSqlCommand(sqlca)) {
        sql.setCommand(
            "SELECT KEY_ACCNTID, PA_CUST_ID, PA_AUTH_STATUS, PA_ACCOUNT_STATUS1,"
                + " PA_ACCOUNT_STATUS2, PA_ACCOUNT_STATUS3, PA_ACCOUNT_STATUS4, PA_ACCOUNT_STATUS5,"
                + " PA_CREDIT_LIMIT, PA_CASH_LIMIT, PA_CREDIT_BALANCE, PA_CASH_BALANCE,"
                + " PA_APPROVED_AUTH_CNT, PA_DECLINED_AUTH_CNT, PA_APPROVED_AUTH_AMT,"
                + " PA_DECLINED_AUTH_AMT, PAUTSUM0_FILLER FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID"
                + " = (SELECT KEY_ACCNTID FROM DBPAUTP0_PAUTSUM0 ORDER BY 1 LIMIT 1)");
        sql.into(
            ws.pautsum0copy.pautsum0.keyAccntid,
            ws.pautsum0copy.pautsum0.paCustId,
            ws.pautsum0copy.pautsum0.paAuthStatus,
            ws.pautsum0copy.pautsum0.paAccountStatus1,
            ws.pautsum0copy.pautsum0.paAccountStatus2,
            ws.pautsum0copy.pautsum0.paAccountStatus3,
            ws.pautsum0copy.pautsum0.paAccountStatus4,
            ws.pautsum0copy.pautsum0.paAccountStatus5,
            ws.pautsum0copy.pautsum0.paCreditLimit,
            ws.pautsum0copy.pautsum0.paCashLimit,
            ws.pautsum0copy.pautsum0.paCreditBalance,
            ws.pautsum0copy.pautsum0.paCashBalance,
            ws.pautsum0copy.pautsum0.paApprovedAuthCnt,
            ws.pautsum0copy.pautsum0.paDeclinedAuthCnt,
            ws.pautsum0copy.pautsum0.paApprovedAuthAmt,
            ws.pautsum0copy.pautsum0.paDeclinedAuthAmt,
            ws.pautsum0copy.pautsum0.pautsum0Filler);
        sql.where();
        sql.execute();
      }
      // COB(1864): END-IF
    }
    // COB(1866): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(1868): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(1869): EVALUATE TRUE
    // COB(1870): WHEN IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(1871): MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1872): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(1872):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(1874): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(1874):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1876): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(1876):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(1878): MOVE 6 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(6);
      // COB(1882): MOVE PA-CUST-ID OF PAUTSUM0 TO
      // COB(1882): WS-PACKED-FLD-18-00(1)
      //       *
      //       * PACKED FIELDS VS SIGNED ZONED FITTING
      //       *
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.paCustId);
      // COB(1884): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(1884): PA-CUST-ID-N OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCustIdN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
      // COB(1886): MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(ws.pautsum0Len);
      // COB(1887): PERFORM PAUTSUM0-SET-IO-AREA
      pautsum0SetIoArea();
      // COB(1888): SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(1889): MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
      // COB(1889):                 IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
      ws.wsLastSegmentName.setString("PAUTSUM0");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTSUM0");
      // COB(1891): END-EVALUATE
    }
    // COB(1893): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(1894): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    SELECT NEXT FOR SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0SelectNext() {
    // COB(1905): MOVE 'PAUTSUM0-SELECT-NEXT' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-SELECT-NEXT");
    // COB(1907): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(1908): MOVE ZERO
      // COB(1908):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler173.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(1910): ELSE
    } else {
      // COB(1911): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(1911):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler173.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1913): END-IF
    }
    // COB(1914): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(1915): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(1916): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(1917): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(1917):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler249.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(1919): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(1920): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(1920):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler249
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(1922): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler173.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(1923): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(1924): END-IF
    }
    // COB(1925): MOVE WS-PACKED-FLD-18-00(1) TO KEY-ACCNTID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.keyAccntid.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(1926): IF COMMAND-CODE-LAST
    if (ws.commandCodeLast()) {
      // COB(1927): EXEC SQL
      // COB(1927): SELECT
      // COB(1927):   KEY_ACCNTID
      // COB(1927):  ,PA_CUST_ID
      // COB(1927):  ,PA_AUTH_STATUS
      // COB(1927):  ,PA_ACCOUNT_STATUS1
      // COB(1927):  ,PA_ACCOUNT_STATUS2
      // COB(1927):  ,PA_ACCOUNT_STATUS3
      // COB(1927):  ,PA_ACCOUNT_STATUS4
      // COB(1927):  ,PA_ACCOUNT_STATUS5
      // COB(1927):  ,PA_CREDIT_LIMIT
      // COB(1927):  ,PA_CASH_LIMIT
      // COB(1927):  ,PA_CREDIT_BALANCE
      // COB(1927):  ,PA_CASH_BALANCE
      // COB(1927):  ,PA_APPROVED_AUTH_CNT
      // COB(1927):  ,PA_DECLINED_AUTH_CNT
      // COB(1927):  ,PA_APPROVED_AUTH_AMT
      // COB(1927):  ,PA_DECLINED_AUTH_AMT
      // COB(1927):  ,PAUTSUM0_FILLER
      // COB(1927): INTO
      // COB(1927):   :PAUTSUM0.KEY-ACCNTID
      // COB(1927):  ,:PAUTSUM0.PA-CUST-ID
      // COB(1927):  ,:PAUTSUM0.PA-AUTH-STATUS
      // COB(1927):  ,:PAUTSUM0.PA-ACCOUNT-STATUS1
      // COB(1927):  ,:PAUTSUM0.PA-ACCOUNT-STATUS2
      // COB(1927):  ,:PAUTSUM0.PA-ACCOUNT-STATUS3
      // COB(1927):  ,:PAUTSUM0.PA-ACCOUNT-STATUS4
      // COB(1927):  ,:PAUTSUM0.PA-ACCOUNT-STATUS5
      // COB(1927):  ,:PAUTSUM0.PA-CREDIT-LIMIT
      // COB(1927):  ,:PAUTSUM0.PA-CASH-LIMIT
      // COB(1927):  ,:PAUTSUM0.PA-CREDIT-BALANCE
      // COB(1927):  ,:PAUTSUM0.PA-CASH-BALANCE
      // COB(1927):  ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
      // COB(1927):  ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
      // COB(1927):  ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
      // COB(1927):  ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
      // COB(1927):  ,:PAUTSUM0.PAUTSUM0-FILLER
      // COB(1927): FROM
      // COB(1927): DBPAUTP0_PAUTSUM0
      // COB(1927):   WHERE
      // COB(1927):     KEY_ACCNTID = (
      // COB(1927):     SELECT
      // COB(1927):       KEY_ACCNTID
      // COB(1927):     FROM
      // COB(1927):       DBPAUTP0_PAUTSUM0
      // COB(1927):     WHERE
      // COB(1927):       KEY_ACCNTID >
      // COB(1927):       :PAUTSUM0.KEY-ACCNTID
      // COB(1927):     ORDER BY 1 DESC LIMIT 1
      // COB(1927):     )
      // COB(1927): END-EXEC
      try (NSqlCommand sql = new NSqlCommand(sqlca)) {
        sql.setCommand(
            "SELECT KEY_ACCNTID, PA_CUST_ID, PA_AUTH_STATUS, PA_ACCOUNT_STATUS1,"
                + " PA_ACCOUNT_STATUS2, PA_ACCOUNT_STATUS3, PA_ACCOUNT_STATUS4, PA_ACCOUNT_STATUS5,"
                + " PA_CREDIT_LIMIT, PA_CASH_LIMIT, PA_CREDIT_BALANCE, PA_CASH_BALANCE,"
                + " PA_APPROVED_AUTH_CNT, PA_DECLINED_AUTH_CNT, PA_APPROVED_AUTH_AMT,"
                + " PA_DECLINED_AUTH_AMT, PAUTSUM0_FILLER FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID"
                + " = (SELECT KEY_ACCNTID FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID > ? ORDER BY 1"
                + " DESC LIMIT 1)");
        sql.into(
            ws.pautsum0copy.pautsum0.keyAccntid,
            ws.pautsum0copy.pautsum0.paCustId,
            ws.pautsum0copy.pautsum0.paAuthStatus,
            ws.pautsum0copy.pautsum0.paAccountStatus1,
            ws.pautsum0copy.pautsum0.paAccountStatus2,
            ws.pautsum0copy.pautsum0.paAccountStatus3,
            ws.pautsum0copy.pautsum0.paAccountStatus4,
            ws.pautsum0copy.pautsum0.paAccountStatus5,
            ws.pautsum0copy.pautsum0.paCreditLimit,
            ws.pautsum0copy.pautsum0.paCashLimit,
            ws.pautsum0copy.pautsum0.paCreditBalance,
            ws.pautsum0copy.pautsum0.paCashBalance,
            ws.pautsum0copy.pautsum0.paApprovedAuthCnt,
            ws.pautsum0copy.pautsum0.paDeclinedAuthCnt,
            ws.pautsum0copy.pautsum0.paApprovedAuthAmt,
            ws.pautsum0copy.pautsum0.paDeclinedAuthAmt,
            ws.pautsum0copy.pautsum0.pautsum0Filler);
        sql.where(ws.pautsum0copy.pautsum0.keyAccntid);
        sql.execute();
      }
      // COB(1978): ELSE
    } else {
      // COB(1979): EXEC SQL
      // COB(1979): SELECT
      // COB(1979):   KEY_ACCNTID
      // COB(1979):  ,PA_CUST_ID
      // COB(1979):  ,PA_AUTH_STATUS
      // COB(1979):  ,PA_ACCOUNT_STATUS1
      // COB(1979):  ,PA_ACCOUNT_STATUS2
      // COB(1979):  ,PA_ACCOUNT_STATUS3
      // COB(1979):  ,PA_ACCOUNT_STATUS4
      // COB(1979):  ,PA_ACCOUNT_STATUS5
      // COB(1979):  ,PA_CREDIT_LIMIT
      // COB(1979):  ,PA_CASH_LIMIT
      // COB(1979):  ,PA_CREDIT_BALANCE
      // COB(1979):  ,PA_CASH_BALANCE
      // COB(1979):  ,PA_APPROVED_AUTH_CNT
      // COB(1979):  ,PA_DECLINED_AUTH_CNT
      // COB(1979):  ,PA_APPROVED_AUTH_AMT
      // COB(1979):  ,PA_DECLINED_AUTH_AMT
      // COB(1979):  ,PAUTSUM0_FILLER
      // COB(1979): INTO
      // COB(1979):   :PAUTSUM0.KEY-ACCNTID
      // COB(1979):  ,:PAUTSUM0.PA-CUST-ID
      // COB(1979):  ,:PAUTSUM0.PA-AUTH-STATUS
      // COB(1979):  ,:PAUTSUM0.PA-ACCOUNT-STATUS1
      // COB(1979):  ,:PAUTSUM0.PA-ACCOUNT-STATUS2
      // COB(1979):  ,:PAUTSUM0.PA-ACCOUNT-STATUS3
      // COB(1979):  ,:PAUTSUM0.PA-ACCOUNT-STATUS4
      // COB(1979):  ,:PAUTSUM0.PA-ACCOUNT-STATUS5
      // COB(1979):  ,:PAUTSUM0.PA-CREDIT-LIMIT
      // COB(1979):  ,:PAUTSUM0.PA-CASH-LIMIT
      // COB(1979):  ,:PAUTSUM0.PA-CREDIT-BALANCE
      // COB(1979):  ,:PAUTSUM0.PA-CASH-BALANCE
      // COB(1979):  ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
      // COB(1979):  ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
      // COB(1979):  ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
      // COB(1979):  ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
      // COB(1979):  ,:PAUTSUM0.PAUTSUM0-FILLER
      // COB(1979): FROM
      // COB(1979): DBPAUTP0_PAUTSUM0
      // COB(1979):   WHERE
      // COB(1979):     KEY_ACCNTID = (
      // COB(1979):     SELECT
      // COB(1979):       KEY_ACCNTID
      // COB(1979):     FROM
      // COB(1979):       DBPAUTP0_PAUTSUM0
      // COB(1979):     WHERE
      // COB(1979):       KEY_ACCNTID >
      // COB(1979):       :PAUTSUM0.KEY-ACCNTID
      // COB(1979):     ORDER BY 1 LIMIT 1
      // COB(1979):     )
      // COB(1979): END-EXEC
      try (NSqlCommand sql = new NSqlCommand(sqlca)) {
        sql.setCommand(
            "SELECT KEY_ACCNTID, PA_CUST_ID, PA_AUTH_STATUS, PA_ACCOUNT_STATUS1,"
                + " PA_ACCOUNT_STATUS2, PA_ACCOUNT_STATUS3, PA_ACCOUNT_STATUS4, PA_ACCOUNT_STATUS5,"
                + " PA_CREDIT_LIMIT, PA_CASH_LIMIT, PA_CREDIT_BALANCE, PA_CASH_BALANCE,"
                + " PA_APPROVED_AUTH_CNT, PA_DECLINED_AUTH_CNT, PA_APPROVED_AUTH_AMT,"
                + " PA_DECLINED_AUTH_AMT, PAUTSUM0_FILLER FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID"
                + " = (SELECT KEY_ACCNTID FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID > ? ORDER BY 1"
                + " LIMIT 1)");
        sql.into(
            ws.pautsum0copy.pautsum0.keyAccntid,
            ws.pautsum0copy.pautsum0.paCustId,
            ws.pautsum0copy.pautsum0.paAuthStatus,
            ws.pautsum0copy.pautsum0.paAccountStatus1,
            ws.pautsum0copy.pautsum0.paAccountStatus2,
            ws.pautsum0copy.pautsum0.paAccountStatus3,
            ws.pautsum0copy.pautsum0.paAccountStatus4,
            ws.pautsum0copy.pautsum0.paAccountStatus5,
            ws.pautsum0copy.pautsum0.paCreditLimit,
            ws.pautsum0copy.pautsum0.paCashLimit,
            ws.pautsum0copy.pautsum0.paCreditBalance,
            ws.pautsum0copy.pautsum0.paCashBalance,
            ws.pautsum0copy.pautsum0.paApprovedAuthCnt,
            ws.pautsum0copy.pautsum0.paDeclinedAuthCnt,
            ws.pautsum0copy.pautsum0.paApprovedAuthAmt,
            ws.pautsum0copy.pautsum0.paDeclinedAuthAmt,
            ws.pautsum0copy.pautsum0.pautsum0Filler);
        sql.where(ws.pautsum0copy.pautsum0.keyAccntid);
        sql.execute();
      }
      // COB(2030): END-IF
    }
    // COB(2032): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(2033): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2034): EVALUATE TRUE
    // COB(2035): WHEN IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(2036): MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2037): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(2037):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(2039): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(2039):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2041): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(2041):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(2043): MOVE 6 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(6);
      // COB(2047): MOVE PA-CUST-ID OF PAUTSUM0 TO
      // COB(2047): WS-PACKED-FLD-18-00(1)
      //       *
      //       * PACKED FIELDS VS SIGNED ZONED FITTING
      //       *
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.paCustId);
      // COB(2049): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(2049): PA-CUST-ID-N OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCustIdN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
      // COB(2051): MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(ws.pautsum0Len);
      // COB(2052): PERFORM PAUTSUM0-SET-IO-AREA
      pautsum0SetIoArea();
      // COB(2053): SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(2054): MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
      // COB(2054):                 IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
      ws.wsLastSegmentName.setString("PAUTSUM0");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTSUM0");
      // COB(2056): END-EVALUATE
    }
    // COB(2058): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2059): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    DYNAMIC SELECT FOR SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0SelectDynamic() {
    // COB(2070): MOVE 'PAUTSUM0-SELECT-DYNAMIC' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-SELECT-DYNAMIC");
    // COB(2072): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(2073): MOVE ZERO
      // COB(2073):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler173.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(2075): ELSE
    } else {
      // COB(2076): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(2076):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler173.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2078): END-IF
    }
    // COB(2079): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(2080): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(2081): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(2082): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(2082):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler249.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(2084): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2087): MOVE ZERO TO WS-WHERE-LEN WS-ORDERBY-LEN
    //       *    PREPARE THE WHERE CONDITION IF ANY
    //       *
    ws.wsWhereLen.setValue(0);
    ws.wsOrderbyLen.setValue(0);
    // COB(2088): IF SQL-CONDITION-CLAUSE-LENGTH > 0
    if (ws.sqlConditionClause.sqlConditionClauseLength.greaterThan(0)) {
      // COB(2089): SUBTRACT 1 FROM SQL-CONDITION-CLAUSE-LENGTH
      ws.sqlConditionClause.sqlConditionClauseLength.subtract(1);
      // COB(2090): STRING 'WHERE '
      // COB(2090): SQL-CONDITION-CLAUSE-TEXT(1:SQL-CONDITION-CLAUSE-LENGTH)
      // COB(2090): ' ' DELIMITED BY SIZE INTO WS-WHERE
      ws.wsWhere.concat(
          "WHERE ",
          ws.sqlConditionClause.sqlConditionClauseText.getString(
              0, ws.sqlConditionClause.sqlConditionClauseLength.getInt()),
          " ");
      // COB(2093): COMPUTE WS-WHERE-LEN = SQL-CONDITION-CLAUSE-LENGTH + 7
      ws.wsWhereLen.setValue(ws.sqlConditionClause.sqlConditionClauseLength.getInt() + 7);
      // COB(2094): END-IF
    }
    // COB(2096): IF SQL-ORDERBY-CLAUSE-LENGTH > 0
    if (ws.sqlOrderbyClause.sqlOrderbyClauseLength.greaterThan(0)) {
      // COB(2097): SUBTRACT 1 FROM SQL-ORDERBY-CLAUSE-LENGTH
      ws.sqlOrderbyClause.sqlOrderbyClauseLength.subtract(1);
      // COB(2098): MOVE SQL-ORDERBY-CLAUSE-TEXT(1:SQL-ORDERBY-CLAUSE-LENGTH)
      // COB(2098):                TO WS-ORDERBY(1:SQL-ORDERBY-CLAUSE-LENGTH)
      ws.wsOrderby.setValue(
          ws.sqlOrderbyClause.sqlOrderbyClauseText,
          ws.sqlOrderbyClause.sqlOrderbyClauseLength.getInt());
      // COB(2100): MOVE SQL-ORDERBY-CLAUSE-LENGTH TO WS-ORDERBY-LEN
      ws.wsOrderbyLen.setValue(ws.sqlOrderbyClause.sqlOrderbyClauseLength);
      // COB(2101): ELSE
    } else {
      // COB(2102): COMPUTE WS-ORDERBY-LEN = 1
      ws.wsOrderbyLen.setValue(1);
      // COB(2103): STRING ' ORDER BY '
      // COB(2103):       ' KEY_ACCNTID '
      // COB(2103): DELIMITED BY SIZE INTO WS-ORDERBY POINTER WS-ORDERBY-LEN
      ws.wsOrderbyLen.setValue(ws.wsOrderby.concat(" ORDER BY ", " KEY_ACCNTID "));
      // COB(2106): SUBTRACT 1 FROM WS-ORDERBY-LEN
      ws.wsOrderbyLen.subtract(1);
      // COB(2107): END-IF
    }
    // COB(2109): IF COMMAND-CODE-LAST
    if (ws.commandCodeLast()) {
      // COB(2110): STRING WS-ORDERBY(1:WS-ORDERBY-LEN)
      // COB(2110): ' DESC ' DELIMITED BY SIZE
      // COB(2110): INTO WS-ORDERBY
      ws.wsOrderby.concat(ws.wsOrderby.getString(0, ws.wsOrderbyLen.getInt()), " DESC ");
      // COB(2113): ADD 6 TO WS-ORDERBY-LEN
      ws.wsOrderbyLen.add(6);
      // COB(2114): END-IF
    }
    // COB(2119): MOVE 1 TO WS-SQL-STM-LEN
    //       *
    //       *
    //       *    PREPARE THE DYNAMIC QUERY
    //       *
    ws.wsSqlStm.wsSqlStmLen.setValue(1);
    // COB(2120): STRING
    // COB(2120): 'SELECT '
    // COB(2120):   'DBPAUTP0_PAUTSUM0.KEY_ACCNTID '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_CUST_ID '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_AUTH_STATUS '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS1 '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS2 '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS3 '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS4 '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS5 '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_CREDIT_LIMIT '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_CASH_LIMIT '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_CREDIT_BALANCE '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_CASH_BALANCE '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_APPROVED_AUTH_CNT '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_DECLINED_AUTH_CNT '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_APPROVED_AUTH_AMT '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PA_DECLINED_AUTH_AMT '
    // COB(2120):  ',DBPAUTP0_PAUTSUM0.PAUTSUM0_FILLER '
    // COB(2120): 'FROM '
    // COB(2120): 'DBPAUTP0_PAUTSUM0 '
    // COB(2120): DELIMITED BY SIZE
    // COB(2120): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
    ws.wsSqlStm.wsSqlStmLen.setValue(
        ws.wsSqlStm.wsSqlStmTxt.concat(
            "SELECT ",
            "DBPAUTP0_PAUTSUM0.KEY_ACCNTID ",
            ",DBPAUTP0_PAUTSUM0.PA_CUST_ID ",
            ",DBPAUTP0_PAUTSUM0.PA_AUTH_STATUS ",
            ",DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS1 ",
            ",DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS2 ",
            ",DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS3 ",
            ",DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS4 ",
            ",DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS5 ",
            ",DBPAUTP0_PAUTSUM0.PA_CREDIT_LIMIT ",
            ",DBPAUTP0_PAUTSUM0.PA_CASH_LIMIT ",
            ",DBPAUTP0_PAUTSUM0.PA_CREDIT_BALANCE ",
            ",DBPAUTP0_PAUTSUM0.PA_CASH_BALANCE ",
            ",DBPAUTP0_PAUTSUM0.PA_APPROVED_AUTH_CNT ",
            ",DBPAUTP0_PAUTSUM0.PA_DECLINED_AUTH_CNT ",
            ",DBPAUTP0_PAUTSUM0.PA_APPROVED_AUTH_AMT ",
            ",DBPAUTP0_PAUTSUM0.PA_DECLINED_AUTH_AMT ",
            ",DBPAUTP0_PAUTSUM0.PAUTSUM0_FILLER ",
            "FROM ",
            "DBPAUTP0_PAUTSUM0 "));
    // COB(2143): IF SQL-JOIN-CLAUSE-LENGTH > 0
    if (ws.sqlJoinClause.sqlJoinClauseLength.greaterThan(0)) {
      // COB(2144): SUBTRACT 1 FROM SQL-JOIN-CLAUSE-LENGTH
      ws.sqlJoinClause.sqlJoinClauseLength.subtract(1);
      // COB(2145): STRING SQL-JOIN-CLAUSE-TEXT(1:SQL-JOIN-CLAUSE-LENGTH) ' '
      // COB(2145): DELIMITED BY SIZE
      // COB(2145): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(
              ws.sqlJoinClause.sqlJoinClauseText.getString(
                  0, ws.sqlJoinClause.sqlJoinClauseLength.getInt()),
              " "));
      // COB(2148): END-IF
    }
    // COB(2149): IF WS-WHERE-LEN > 0
    if (ws.wsWhereLen.greaterThan(0)) {
      // COB(2150): STRING
      // COB(2150): WS-WHERE(1:WS-WHERE-LEN)
      // COB(2150): DELIMITED BY SIZE
      // COB(2150): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(ws.wsWhere.getString(0, ws.wsWhereLen.getInt())));
      // COB(2154): IF IRIS-TRACE-FULL
      if (params.irisWorkArea.irisTraceFull()) {
        // COB(2155): MOVE 0 TO IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
        // COB(2156): STRING '<IRISTRACE> - DBPAUTP0:SELECT-DYNAMIC' NL
        // COB(2156):      ' SECTION    =(PAUTSUM0-SELECT-DYNAMIC) ' NL
        // COB(2156):      ' CONDITION  =(' WS-WHERE(1:WS-WHERE-LEN) ')' NL
        // COB(2156):      ' ORDER BY   =(' WS-ORDERBY(1:WS-ORDERBY-LEN) ')'
        // COB(2156): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - DBPAUTP0:SELECT-DYNAMIC",
            ws.iriscomm.nl,
            " SECTION    =(PAUTSUM0-SELECT-DYNAMIC) ",
            ws.iriscomm.nl,
            " CONDITION  =(",
            ws.wsWhere.getString(0, ws.wsWhereLen.getInt()),
            ")",
            ws.iriscomm.nl,
            " ORDER BY   =(",
            ws.wsOrderby.getString(0, ws.wsOrderbyLen.getInt()),
            ")",
            ws.iriscomm.messageEnd);
        // COB(2161): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(2162): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(2163): END-IF
      }
      // COB(2164): END-IF
    }
    // COB(2165): IF WS-ORDERBY-LEN > 0
    if (ws.wsOrderbyLen.greaterThan(0)) {
      // COB(2166): STRING WS-ORDERBY(1:WS-ORDERBY-LEN) DELIMITED BY SIZE
      // COB(2166): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(ws.wsOrderby.getString(0, ws.wsOrderbyLen.getInt())));
      // COB(2168): END-IF
    }
    // COB(2169): STRING ' FETCH FIRST 1 ROW ONLY '
    // COB(2169): DELIMITED BY SIZE
    // COB(2169): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
    ws.wsSqlStm.wsSqlStmLen.setValue(ws.wsSqlStm.wsSqlStmTxt.concat(" FETCH FIRST 1 ROW ONLY "));
    // COB(2172): SUBTRACT 1 FROM WS-SQL-STM-LEN
    ws.wsSqlStm.wsSqlStmLen.subtract(1);
    // COB(2176): EXEC SQL
    // COB(2176):   DECLARE PAUTSUM0_CRS CURSOR
    // COB(2176):   FOR PAUTSUM0_STATEMENT
    // COB(2176): END-EXEC
    //       *
    //       *    DECLARE THE DYNAMIC CURSOR
    //       *
    // do nothing
    // COB(2183): EXEC SQL
    // COB(2183):   DECLARE PAUTSUM0_STATEMENT STATEMENT
    // COB(2183): END-EXEC
    //       *
    //       *    DECLARE THE SQL STATEMENT
    //       *
    // do nothing
    // COB(2189): EXEC SQL
    // COB(2189):   PREPARE PAUTSUM0_STATEMENT
    // COB(2189):   INTO :SQLDA
    // COB(2189):   FROM :WS-SQL-STM
    // COB(2189): END-EXEC
    //       *
    //       *    PREPARE THE STATEMENT
    //       *
    pautsum0Crs = new NSqlCursor(this, sqlca, ws.wsSqlStm.toString());
    // COB(2197): IF SQLCODE NOT = ZERO
    //       *
    //       *    TEST THE RETURN CODE
    //       *
    if (!sqlca.sqlcode.equals(0)) {
      // COB(2198): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(2199): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(2200): GO TO PAUTSUM0-SELECT-DYNAMIC-EX
      return;
      // COB(2201): END-IF
    }
    // COB(2205): EXEC SQL
    // COB(2205):   OPEN PAUTSUM0_CRS
    // COB(2205): END-EXEC
    //       *
    //       *    OPEN THE DYNAMIC CURSOR
    //       *
    pautsum0Crs.open();
    // COB(2211): IF SQLCODE NOT = ZERO
    //       *
    //       *    TEST THE RETURN CODE
    //       *
    if (!sqlca.sqlcode.equals(0)) {
      // COB(2212): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(2213): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(2214): GO TO PAUTSUM0-SELECT-DYNAMIC-EX
      return;
      // COB(2215): END-IF
    }
    // COB(2219): EXEC SQL
    // COB(2219):   FETCH PAUTSUM0_CRS
    // COB(2219):   INTO
    // COB(2219):     :PAUTSUM0.KEY-ACCNTID
    // COB(2219):    ,:PAUTSUM0.PA-CUST-ID
    // COB(2219):    ,:PAUTSUM0.PA-AUTH-STATUS
    // COB(2219):    ,:PAUTSUM0.PA-ACCOUNT-STATUS1
    // COB(2219):    ,:PAUTSUM0.PA-ACCOUNT-STATUS2
    // COB(2219):    ,:PAUTSUM0.PA-ACCOUNT-STATUS3
    // COB(2219):    ,:PAUTSUM0.PA-ACCOUNT-STATUS4
    // COB(2219):    ,:PAUTSUM0.PA-ACCOUNT-STATUS5
    // COB(2219):    ,:PAUTSUM0.PA-CREDIT-LIMIT
    // COB(2219):    ,:PAUTSUM0.PA-CASH-LIMIT
    // COB(2219):    ,:PAUTSUM0.PA-CREDIT-BALANCE
    // COB(2219):    ,:PAUTSUM0.PA-CASH-BALANCE
    // COB(2219):    ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
    // COB(2219):    ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
    // COB(2219):    ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
    // COB(2219):    ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
    // COB(2219):    ,:PAUTSUM0.PAUTSUM0-FILLER
    // COB(2219): END-EXEC
    //       *
    //       *    FETCH THE DYNAMIC CURSOR
    //       *
    pautsum0Crs.fetch(
        ws.pautsum0copy.pautsum0.keyAccntid,
        ws.pautsum0copy.pautsum0.paCustId,
        ws.pautsum0copy.pautsum0.paAuthStatus,
        ws.pautsum0copy.pautsum0.paAccountStatus1,
        ws.pautsum0copy.pautsum0.paAccountStatus2,
        ws.pautsum0copy.pautsum0.paAccountStatus3,
        ws.pautsum0copy.pautsum0.paAccountStatus4,
        ws.pautsum0copy.pautsum0.paAccountStatus5,
        ws.pautsum0copy.pautsum0.paCreditLimit,
        ws.pautsum0copy.pautsum0.paCashLimit,
        ws.pautsum0copy.pautsum0.paCreditBalance,
        ws.pautsum0copy.pautsum0.paCashBalance,
        ws.pautsum0copy.pautsum0.paApprovedAuthCnt,
        ws.pautsum0copy.pautsum0.paDeclinedAuthCnt,
        ws.pautsum0copy.pautsum0.paApprovedAuthAmt,
        ws.pautsum0copy.pautsum0.paDeclinedAuthAmt,
        ws.pautsum0copy.pautsum0.pautsum0Filler);
    // COB(2241): IF SQLCODE NOT = ZERO
    if (!sqlca.sqlcode.equals(0)) {
      // COB(2242): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(2243): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(2247): EXEC SQL
      // COB(2247):   CLOSE PAUTSUM0_CRS
      // COB(2247): END-EXEC
      //       *
      //       *      CLOSE THE DYNAMIC CURSOR
      //       *
      pautsum0Crs.close();
      // COB(2250): GO TO PAUTSUM0-SELECT-DYNAMIC-EX
      return;
      // COB(2251): END-IF
    }
    // COB(2253): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2254): MOVE SQLERRM TO IRIS-SQLERRM
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
    // COB(2258): EXEC SQL
    // COB(2258):   CLOSE PAUTSUM0_CRS
    // COB(2258): END-EXEC
    //       *
    //       *      CLOSE THE DYNAMIC CURSOR
    //       *
    pautsum0Crs.close();
    // COB(2262): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(2264): MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(params.irisWorkArea.irisSqlError.irisSqlcode);
    // COB(2265): EVALUATE TRUE
    // COB(2266): WHEN IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(2267): MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2268): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(2268):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(2270): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(2270):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2272): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(2272):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(2274): MOVE 6 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(6);
      // COB(2275): IF SQL-SELECT-DYNAMIC
      // COB(2275): AND NOT COMMAND-WITH-HOLD
      if (params.irisWorkArea.sqlSelectDynamic() && !ws.commandWithHold()) {
        // COB(2280): MOVE PA-CUST-ID OF PAUTSUM0 TO
        // COB(2280): WS-PACKED-FLD-18-00(1)
        //       *
        //       * PACKED FIELDS VS SIGNED ZONED FITTING
        //       *
        ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.paCustId);
        // COB(2282): MOVE WS-PACKED-FLD-18-00(1) TO
        // COB(2282): PA-CUST-ID-N OF PAUTSUM0
        ws.pautsum0copy.pautsum0.paCustIdN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
        // COB(2284): MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN
        ws.wsSegmentLen.setValue(ws.pautsum0Len);
        // COB(2285): PERFORM PAUTSUM0-SET-IO-AREA
        pautsum0SetIoArea();
        // COB(2286): END-IF
      }
      // COB(2287): SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(2288): MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
      // COB(2288):                 IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
      ws.wsLastSegmentName.setString("PAUTSUM0");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTSUM0");
      // COB(2290): END-EVALUATE.
    }
  }

  /***
   ******************************************************************
   *    INSERT FOR SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0Insert() {
    // COB(2301): MOVE 'PAUTSUM0-INSERT' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-INSERT");
    // COB(2304): INITIALIZE PAUTSUM0
    //       *
    //       *
    ws.pautsum0copy.pautsum0.initialize();
    // COB(2306): MOVE LK-IO-AREA(1:PAUTSUM0-LEN) TO PAUTSUM0(1:PAUTSUM0-LEN)
    ws.pautsum0copy.pautsum0.setValue(params.lkIoArea, ws.pautsum0Len.getInt());
    // COB(2307): IF KEY-ACCNTID
    // COB(2307): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(2309): MOVE ZERO TO
      // COB(2309): KEY-ACCNTID OF PAUTSUM0
      ws.pautsum0copy.pautsum0.keyAccntid.setValue(0);
      // COB(2311): END-IF
    }
    // COB(2312): IF PA-CUST-ID-N
    // COB(2312): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCustIdN.isNumeric()) {
      // COB(2314): MOVE ZERO TO
      // COB(2314): PA-CUST-ID-N OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCustIdN.setValue(0);
      // COB(2316): END-IF
    }
    // COB(2317): IF PA-CREDIT-LIMIT
    // COB(2317): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCreditLimit.isNumeric()) {
      // COB(2319): MOVE ZERO TO
      // COB(2319): PA-CREDIT-LIMIT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCreditLimit.setValue(0);
      // COB(2321): END-IF
    }
    // COB(2322): IF PA-CASH-LIMIT
    // COB(2322): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCashLimit.isNumeric()) {
      // COB(2324): MOVE ZERO TO
      // COB(2324): PA-CASH-LIMIT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCashLimit.setValue(0);
      // COB(2326): END-IF
    }
    // COB(2327): IF PA-CREDIT-BALANCE
    // COB(2327): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCreditBalance.isNumeric()) {
      // COB(2329): MOVE ZERO TO
      // COB(2329): PA-CREDIT-BALANCE OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCreditBalance.setValue(0);
      // COB(2331): END-IF
    }
    // COB(2332): IF PA-CASH-BALANCE
    // COB(2332): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCashBalance.isNumeric()) {
      // COB(2334): MOVE ZERO TO
      // COB(2334): PA-CASH-BALANCE OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCashBalance.setValue(0);
      // COB(2336): END-IF
    }
    // COB(2337): IF PA-APPROVED-AUTH-AMT
    // COB(2337): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paApprovedAuthAmt.isNumeric()) {
      // COB(2339): MOVE ZERO TO
      // COB(2339): PA-APPROVED-AUTH-AMT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paApprovedAuthAmt.setValue(0);
      // COB(2341): END-IF
    }
    // COB(2342): IF PA-DECLINED-AUTH-AMT
    // COB(2342): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paDeclinedAuthAmt.isNumeric()) {
      // COB(2344): MOVE ZERO TO
      // COB(2344): PA-DECLINED-AUTH-AMT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paDeclinedAuthAmt.setValue(0);
      // COB(2346): END-IF
    }
    // COB(2350): MOVE PA-CUST-ID-N OF PAUTSUM0 TO
    // COB(2350): WS-PACKED-FLD-18-00(1)
    //       *
    //       * SIGNED ZONED FIELDS VS PACKED FITTING
    //       *
    ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.paCustIdN);
    // COB(2352): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(2352): PA-CUST-ID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.paCustId.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(2354): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(2355): MOVE ZERO
      // COB(2355):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler173.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(2357): ELSE
    } else {
      // COB(2358): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(2358):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler173.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2360): END-IF
    }
    // COB(2361): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(2362): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(2363): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(2364): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(2364):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler249.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(2366): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2369): EXEC SQL
    // COB(2369):   INSERT INTO
    // COB(2369):     DBPAUTP0_PAUTSUM0
    // COB(2369):     (
    // COB(2369):     KEY_ACCNTID
    // COB(2369):    ,PA_CUST_ID
    // COB(2369):    ,PA_AUTH_STATUS
    // COB(2369):    ,PA_ACCOUNT_STATUS1
    // COB(2369):    ,PA_ACCOUNT_STATUS2
    // COB(2369):    ,PA_ACCOUNT_STATUS3
    // COB(2369):    ,PA_ACCOUNT_STATUS4
    // COB(2369):    ,PA_ACCOUNT_STATUS5
    // COB(2369):    ,PA_CREDIT_LIMIT
    // COB(2369):    ,PA_CASH_LIMIT
    // COB(2369):    ,PA_CREDIT_BALANCE
    // COB(2369):    ,PA_CASH_BALANCE
    // COB(2369):    ,PA_APPROVED_AUTH_CNT
    // COB(2369):    ,PA_DECLINED_AUTH_CNT
    // COB(2369):    ,PA_APPROVED_AUTH_AMT
    // COB(2369):    ,PA_DECLINED_AUTH_AMT
    // COB(2369):    ,PAUTSUM0_FILLER
    // COB(2369):     )
    // COB(2369):   VALUES
    // COB(2369):   (
    // COB(2369):     :PAUTSUM0.KEY-ACCNTID
    // COB(2369):    ,:PAUTSUM0.PA-CUST-ID
    // COB(2369):    ,:PAUTSUM0.PA-AUTH-STATUS
    // COB(2369):    ,:PAUTSUM0.PA-ACCOUNT-STATUS1
    // COB(2369):    ,:PAUTSUM0.PA-ACCOUNT-STATUS2
    // COB(2369):    ,:PAUTSUM0.PA-ACCOUNT-STATUS3
    // COB(2369):    ,:PAUTSUM0.PA-ACCOUNT-STATUS4
    // COB(2369):    ,:PAUTSUM0.PA-ACCOUNT-STATUS5
    // COB(2369):    ,:PAUTSUM0.PA-CREDIT-LIMIT
    // COB(2369):    ,:PAUTSUM0.PA-CASH-LIMIT
    // COB(2369):    ,:PAUTSUM0.PA-CREDIT-BALANCE
    // COB(2369):    ,:PAUTSUM0.PA-CASH-BALANCE
    // COB(2369):    ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
    // COB(2369):    ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
    // COB(2369):    ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
    // COB(2369):    ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
    // COB(2369):    ,:PAUTSUM0.PAUTSUM0-FILLER
    // COB(2369):   )
    // COB(2369): END-EXEC
    //       *
    //       *
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand(
          "INSERT INTO DBPAUTP0_PAUTSUM0 (KEY_ACCNTID, PA_CUST_ID, PA_AUTH_STATUS,"
              + " PA_ACCOUNT_STATUS1, PA_ACCOUNT_STATUS2, PA_ACCOUNT_STATUS3, PA_ACCOUNT_STATUS4,"
              + " PA_ACCOUNT_STATUS5, PA_CREDIT_LIMIT, PA_CASH_LIMIT, PA_CREDIT_BALANCE,"
              + " PA_CASH_BALANCE, PA_APPROVED_AUTH_CNT, PA_DECLINED_AUTH_CNT,"
              + " PA_APPROVED_AUTH_AMT, PA_DECLINED_AUTH_AMT, PAUTSUM0_FILLER) VALUES (?, ?, ?, ?,"
              + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
      sql.values(
          ws.pautsum0copy.pautsum0.keyAccntid,
          ws.pautsum0copy.pautsum0.paCustId,
          ws.pautsum0copy.pautsum0.paAuthStatus,
          ws.pautsum0copy.pautsum0.paAccountStatus1,
          ws.pautsum0copy.pautsum0.paAccountStatus2,
          ws.pautsum0copy.pautsum0.paAccountStatus3,
          ws.pautsum0copy.pautsum0.paAccountStatus4,
          ws.pautsum0copy.pautsum0.paAccountStatus5,
          ws.pautsum0copy.pautsum0.paCreditLimit,
          ws.pautsum0copy.pautsum0.paCashLimit,
          ws.pautsum0copy.pautsum0.paCreditBalance,
          ws.pautsum0copy.pautsum0.paCashBalance,
          ws.pautsum0copy.pautsum0.paApprovedAuthCnt,
          ws.pautsum0copy.pautsum0.paDeclinedAuthCnt,
          ws.pautsum0copy.pautsum0.paApprovedAuthAmt,
          ws.pautsum0copy.pautsum0.paDeclinedAuthAmt,
          ws.pautsum0copy.pautsum0.pautsum0Filler);
      sql.execute();
    }
    // COB(2413): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(2414): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2415): EVALUATE TRUE
    // COB(2416): WHEN IRIS-SQL-OK
    if ((ws.irissqlc.irisSqlOk())
        // COB(2417): WHEN IRIS-SQL-UNIQ-CONSTR-VIOLATED
        || ws.irissqlc.irisSqlUniqConstrViolated()) {
      // COB(2418): MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2419): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(2419):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(2421): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(2421):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2423): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(2423):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(2425): MOVE 6 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(6);
      // COB(2426): SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(2427): IF IRIS-SQL-UNIQ-CONSTR-VIOLATED
      if (ws.irissqlc.irisSqlUniqConstrViolated()) {
        // COB(2428): SET IO-RTN-USED-KEY-DUPREC(PAUTSUM0-LVL) TO TRUE
        params
            .irisDbPcb
            .ioRtnUsedKeysPcbArea
            .ioRtnUsedKeysArea
            .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
            .setIoRtnUsedKeyDuprec(true);
        // COB(2429): END-IF
      }
      // COB(2430): MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
      // COB(2430):                 IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
      ws.wsLastSegmentName.setString("PAUTSUM0");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTSUM0");
      // COB(2432): END-EVALUATE
    }
    // COB(2434): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2435): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    UPDATE FOR SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0Update() {
    // COB(2446): MOVE 'PAUTSUM0-UPDATE' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-UPDATE");
    // COB(2449): INITIALIZE PAUTSUM0
    //       *
    //       *
    ws.pautsum0copy.pautsum0.initialize();
    // COB(2451): MOVE LK-IO-AREA(1:PAUTSUM0-LEN) TO PAUTSUM0(1:PAUTSUM0-LEN)
    ws.pautsum0copy.pautsum0.setValue(params.lkIoArea, ws.pautsum0Len.getInt());
    // COB(2452): IF KEY-ACCNTID
    // COB(2452): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(2454): MOVE ZERO TO
      // COB(2454): KEY-ACCNTID OF PAUTSUM0
      ws.pautsum0copy.pautsum0.keyAccntid.setValue(0);
      // COB(2456): END-IF
    }
    // COB(2457): IF PA-CUST-ID-N
    // COB(2457): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCustIdN.isNumeric()) {
      // COB(2459): MOVE ZERO TO
      // COB(2459): PA-CUST-ID-N OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCustIdN.setValue(0);
      // COB(2461): END-IF
    }
    // COB(2462): IF PA-CREDIT-LIMIT
    // COB(2462): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCreditLimit.isNumeric()) {
      // COB(2464): MOVE ZERO TO
      // COB(2464): PA-CREDIT-LIMIT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCreditLimit.setValue(0);
      // COB(2466): END-IF
    }
    // COB(2467): IF PA-CASH-LIMIT
    // COB(2467): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCashLimit.isNumeric()) {
      // COB(2469): MOVE ZERO TO
      // COB(2469): PA-CASH-LIMIT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCashLimit.setValue(0);
      // COB(2471): END-IF
    }
    // COB(2472): IF PA-CREDIT-BALANCE
    // COB(2472): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCreditBalance.isNumeric()) {
      // COB(2474): MOVE ZERO TO
      // COB(2474): PA-CREDIT-BALANCE OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCreditBalance.setValue(0);
      // COB(2476): END-IF
    }
    // COB(2477): IF PA-CASH-BALANCE
    // COB(2477): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCashBalance.isNumeric()) {
      // COB(2479): MOVE ZERO TO
      // COB(2479): PA-CASH-BALANCE OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCashBalance.setValue(0);
      // COB(2481): END-IF
    }
    // COB(2482): IF PA-APPROVED-AUTH-AMT
    // COB(2482): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paApprovedAuthAmt.isNumeric()) {
      // COB(2484): MOVE ZERO TO
      // COB(2484): PA-APPROVED-AUTH-AMT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paApprovedAuthAmt.setValue(0);
      // COB(2486): END-IF
    }
    // COB(2487): IF PA-DECLINED-AUTH-AMT
    // COB(2487): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paDeclinedAuthAmt.isNumeric()) {
      // COB(2489): MOVE ZERO TO
      // COB(2489): PA-DECLINED-AUTH-AMT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paDeclinedAuthAmt.setValue(0);
      // COB(2491): END-IF
    }
    // COB(2495): MOVE PA-CUST-ID-N OF PAUTSUM0 TO
    // COB(2495): WS-PACKED-FLD-18-00(1)
    //       *
    //       * SIGNED ZONED FIELDS VS PACKED FITTING
    //       *
    ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.paCustIdN);
    // COB(2497): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(2497): PA-CUST-ID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.paCustId.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(2500): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(2501): MOVE ZERO
      // COB(2501):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler173.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(2503): ELSE
    } else {
      // COB(2504): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(2504):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler173.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2506): END-IF
    }
    // COB(2507): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(2508): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(2509): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(2510): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(2510):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler249.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(2512): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2513): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(2513):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler249
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(2515): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler173.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(2516): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(2517): END-IF
    }
    // COB(2518): MOVE WS-PACKED-FLD-18-00(1) TO KEY-ACCNTID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.keyAccntid.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(2521): EXEC SQL
    // COB(2521):   UPDATE DBPAUTP0_PAUTSUM0 SET
    // COB(2521):     PA_CUST_ID = :PAUTSUM0.PA-CUST-ID,
    // COB(2521):     PA_AUTH_STATUS = :PAUTSUM0.PA-AUTH-STATUS,
    // COB(2521):     PA_ACCOUNT_STATUS1 = :PAUTSUM0.PA-ACCOUNT-STATUS1,
    // COB(2521):     PA_ACCOUNT_STATUS2 = :PAUTSUM0.PA-ACCOUNT-STATUS2,
    // COB(2521):     PA_ACCOUNT_STATUS3 = :PAUTSUM0.PA-ACCOUNT-STATUS3,
    // COB(2521):     PA_ACCOUNT_STATUS4 = :PAUTSUM0.PA-ACCOUNT-STATUS4,
    // COB(2521):     PA_ACCOUNT_STATUS5 = :PAUTSUM0.PA-ACCOUNT-STATUS5,
    // COB(2521):     PA_CREDIT_LIMIT = :PAUTSUM0.PA-CREDIT-LIMIT,
    // COB(2521):     PA_CASH_LIMIT = :PAUTSUM0.PA-CASH-LIMIT,
    // COB(2521):     PA_CREDIT_BALANCE = :PAUTSUM0.PA-CREDIT-BALANCE,
    // COB(2521):     PA_CASH_BALANCE = :PAUTSUM0.PA-CASH-BALANCE,
    // COB(2521):     PA_APPROVED_AUTH_CNT = :PAUTSUM0.PA-APPROVED-AUTH-CNT,
    // COB(2521):     PA_DECLINED_AUTH_CNT = :PAUTSUM0.PA-DECLINED-AUTH-CNT,
    // COB(2521):     PA_APPROVED_AUTH_AMT = :PAUTSUM0.PA-APPROVED-AUTH-AMT,
    // COB(2521):     PA_DECLINED_AUTH_AMT = :PAUTSUM0.PA-DECLINED-AUTH-AMT,
    // COB(2521):     PAUTSUM0_FILLER = :PAUTSUM0.PAUTSUM0-FILLER
    // COB(2521):   WHERE
    // COB(2521):     KEY_ACCNTID =
    // COB(2521):   :PAUTSUM0.KEY-ACCNTID
    // COB(2521): END-EXEC
    //       *
    //       *
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand(
          "UPDATE DBPAUTP0_PAUTSUM0 SET PA_CUST_ID = ?, PA_AUTH_STATUS = ?, PA_ACCOUNT_STATUS1 = ?,"
              + " PA_ACCOUNT_STATUS2 = ?, PA_ACCOUNT_STATUS3 = ?, PA_ACCOUNT_STATUS4 = ?,"
              + " PA_ACCOUNT_STATUS5 = ?, PA_CREDIT_LIMIT = ?, PA_CASH_LIMIT = ?, PA_CREDIT_BALANCE"
              + " = ?, PA_CASH_BALANCE = ?, PA_APPROVED_AUTH_CNT = ?, PA_DECLINED_AUTH_CNT = ?,"
              + " PA_APPROVED_AUTH_AMT = ?, PA_DECLINED_AUTH_AMT = ?, PAUTSUM0_FILLER = ? WHERE"
              + " KEY_ACCNTID = ?");
      sql.set(
          ws.pautsum0copy.pautsum0.paCustId,
          ws.pautsum0copy.pautsum0.paAuthStatus,
          ws.pautsum0copy.pautsum0.paAccountStatus1,
          ws.pautsum0copy.pautsum0.paAccountStatus2,
          ws.pautsum0copy.pautsum0.paAccountStatus3,
          ws.pautsum0copy.pautsum0.paAccountStatus4,
          ws.pautsum0copy.pautsum0.paAccountStatus5,
          ws.pautsum0copy.pautsum0.paCreditLimit,
          ws.pautsum0copy.pautsum0.paCashLimit,
          ws.pautsum0copy.pautsum0.paCreditBalance,
          ws.pautsum0copy.pautsum0.paCashBalance,
          ws.pautsum0copy.pautsum0.paApprovedAuthCnt,
          ws.pautsum0copy.pautsum0.paDeclinedAuthCnt,
          ws.pautsum0copy.pautsum0.paApprovedAuthAmt,
          ws.pautsum0copy.pautsum0.paDeclinedAuthAmt,
          ws.pautsum0copy.pautsum0.pautsum0Filler);
      sql.where(ws.pautsum0copy.pautsum0.keyAccntid);
      sql.execute();
    }
    // COB(2546): MOVE PA-CUST-ID OF PAUTSUM0 TO
    // COB(2546): WS-PACKED-FLD-18-00(1)
    //       *
    //       * PACKED FIELDS VS SIGNED ZONED FITTING
    //       *
    ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.paCustId);
    // COB(2548): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(2548): PA-CUST-ID-N OF PAUTSUM0
    ws.pautsum0copy.pautsum0.paCustIdN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(2551): IF SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(2552): MOVE LK-IO-AREA(1:PAUTSUM0-LEN)
      // COB(2552):   TO PAUTSUM0-LAST-AREA
      ws.segmentsLastArea.pautsum0LastArea.setValue(params.lkIoArea, ws.pautsum0Len.getInt());
      // COB(2554): END-IF
    }
    // COB(2555): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2556): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    DELETE FOR SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0Delete() {
    // COB(2567): MOVE 'PAUTSUM0-DELETE' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-DELETE");
    // COB(2570): IF HAS-PATHCALLS
    //       *
    //       *
    if (ws.hasPathcalls()) {
      // COB(2571): SET IRIS-ERR-RTN-UNHANDLED-ACCESS TO TRUE
      params.irisWorkArea.setIrisErrRtnUnhandledAccess(true);
      // COB(2572): GO TO PAUTSUM0-DELETE-EX
      return;
      // COB(2573): END-IF
    }
    // COB(2575): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(2576): MOVE ZERO
      // COB(2576):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler173.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(2578): ELSE
    } else {
      // COB(2579): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(2579):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler173.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2581): END-IF
    }
    // COB(2582): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(2583): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(2584): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(2585): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(2585):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler249.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(2587): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2588): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(2588):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler249
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(2590): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler173.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(2591): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(2592): END-IF
    }
    // COB(2593): MOVE WS-PACKED-FLD-18-00(1) TO KEY-ACCNTID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.keyAccntid.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(2595): EXEC SQL
    // COB(2595):   DELETE FROM DBPAUTP0_PAUTSUM0
    // COB(2595):   WHERE
    // COB(2595):     KEY_ACCNTID =
    // COB(2595):   :PAUTSUM0.KEY-ACCNTID
    // COB(2595): END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand("DELETE FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID = ?");
      sql.where(ws.pautsum0copy.pautsum0.keyAccntid);
      sql.execute();
    }
    // COB(2602): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2603): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    SET I/O AREA PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0SetIoArea() {
    // COB(2614): MOVE 'PAUTSUM0-SET-IO-AREA' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-SET-IO-AREA");
    // COB(2616): MOVE PAUTSUM0(1:WS-SEGMENT-LEN)
    // COB(2616):                TO LK-IO-AREA(1:WS-SEGMENT-LEN)
    // COB(2616):                   PAUTSUM0-LAST-AREA.
    params.lkIoArea.setValue(ws.pautsum0copy.pautsum0, 0, ws.wsSegmentLen.getInt());
    ws.segmentsLastArea.pautsum0LastArea.setValue(
        ws.pautsum0copy.pautsum0, ws.wsSegmentLen.getInt());
  }

  /***
   ******************************************************************
   *    HANDLE SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void handleSegmentPautdtl1() {
    // COB(2629): MOVE PAUTDTL1-LVL TO WS-SEGMENT-LEVEL
    ws.wsSegmentLevel.setValue(ws.pautdtl1Lvl);
    // COB(2630): MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN WS-PATHCALL-LEN
    ws.wsSegmentLen.setValue(ws.pautdtl1Len);
    ws.wsPathcallLen.setValue(ws.pautdtl1Len);
    // COB(2632): IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
    if (params
        .irisWorkArea
        .irisKeyvalueTab
        .irisCcodeLevelsAtIndex(ws.pautsum0Lvl.getInt() - 1)
        .irisCcodeValues
        .irisCodePathcall()) {
      // COB(2633): SET HAS-PATHCALLS TO TRUE
      ws.setHasPathcalls(true);
      // COB(2634): ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
      ws.wsPathcallLen.add(ws.pautsum0Len);
      // COB(2635): END-IF
    }
    // COB(2636): MOVE WS-PATHCALL-LEN TO WS-INIT-PATHCALL-LEN
    ws.wsInitPathcallLen.setValue(ws.wsPathcallLen);
    // COB(2638): MOVE 'HANDLE-SEGMENT-PAUTDTL1' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("HANDLE-SEGMENT-PAUTDTL1");
    // COB(2640): EVALUATE TRUE
    // COB(2641): WHEN SQL-SELECT-PRIMARY
    if (params.irisWorkArea.sqlSelectPrimary()) {
      // COB(2642): PERFORM PAUTDTL1-SELECT-PRIMARY-KEY
      pautdtl1SelectPrimaryKey();
      // COB(2643): WHEN SQL-SELECT-SEEK
    } else if (params.irisWorkArea.sqlSelectSeek()) {
      // COB(2644): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(2645): EVALUATE TRUE
      // COB(2646): WHEN  IRIS-CODE-FIRST(2)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(2647): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(2648): WHEN  IRIS-CODE-LAST(2)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(2649): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(2650): END-EVALUATE
      }
      // COB(2652): PERFORM PAUTDTL1-SELECT-FIRST
      pautdtl1SelectFirst();
      // COB(2653): WHEN SQL-SELECT-NEXT
    } else if (params.irisWorkArea.sqlSelectNext()) {
      // COB(2654): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(2655): EVALUATE TRUE
      // COB(2656): WHEN  IRIS-CODE-FIRST(2)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(2657): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(2658): WHEN  IRIS-CODE-LAST(2)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(2659): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(2660): END-EVALUATE
      }
      // COB(2662): PERFORM PAUTDTL1-SELECT-NEXT
      pautdtl1SelectNext();
      // COB(2663): WHEN SQL-SELECT-DYNAMIC
    } else if (params.irisWorkArea.sqlSelectDynamic()) {
      // COB(2664): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(2665): EVALUATE TRUE
      // COB(2666): WHEN  IRIS-CODE-FIRST(2)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(2667): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(2668): WHEN  IRIS-CODE-LAST(2)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(2669): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(2670): END-EVALUATE
      }
      // COB(2672): PERFORM PAUTDTL1-SELECT-DYNAMIC
      pautdtl1SelectDynamic();
      // COB(2673): WHEN SQL-SELECT-STATIC
    } else if (params.irisWorkArea.sqlSelectStatic()) {
      // COB(2674): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(2675): EVALUATE TRUE
      // COB(2676): WHEN  IRIS-CODE-FIRST(2)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(2677): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(2678): WHEN  IRIS-CODE-LAST(2)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(2679): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(2680): END-EVALUATE
      }
      // COB(2682): EVALUATE TRUE
      // COB(2683): WHEN CUSTOM-STATIC-ACCESS
      if (ws.customStaticAccess()) {
        // COB(2684): PERFORM PAUTDTL1-SELECT-STATIC-CA
        pautdtl1SelectStaticCa();
        // COB(2685): WHEN OTHER
      } else {
        // COB(2686): SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
        params.irisWorkArea.setIrisErrFunctionNotFound(true);
        // COB(2687): END-EVALUATE
      }
      // COB(2688): WHEN SQL-SELECT-PATH
    } else if (params.irisWorkArea.sqlSelectPath()) {
      // COB(2689): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(2690): EVALUATE TRUE
      // COB(2691): WHEN  IRIS-CODE-FIRST(2)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(2692): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(2693): WHEN  IRIS-CODE-LAST(2)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(2694): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(2695): END-EVALUATE
      }
      // COB(2697): PERFORM PAUTDTL1-SELECT-DYNAMIC
      pautdtl1SelectDynamic();
      // COB(2698): WHEN SQL-INSERT
    } else if (params.irisWorkArea.sqlInsert()) {
      // COB(2699): SET COMMAND-CODE-HERE TO TRUE
      ws.setCommandCodeHere(true);
      // COB(2700): EVALUATE TRUE
      // COB(2701): WHEN  IRIS-CODE-FIRST(2)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(2702): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(2703): WHEN  IRIS-CODE-LAST(2)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(2704): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(2705): END-EVALUATE
      }
      // COB(2707): PERFORM PAUTDTL1-INSERT
      pautdtl1Insert();
      // COB(2708): WHEN SQL-UPDATE
    } else if (params.irisWorkArea.sqlUpdate()) {
      // COB(2709): PERFORM PAUTDTL1-UPDATE
      pautdtl1Update();
      // COB(2710): WHEN SQL-DELETE
    } else if (params.irisWorkArea.sqlDelete()) {
      // COB(2711): PERFORM PAUTDTL1-DELETE
      pautdtl1Delete();
      // COB(2712): WHEN OTHER
    } else {
      // COB(2713): SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
      params.irisWorkArea.setIrisErrFunctionNotFound(true);
      // COB(2714): END-EVALUATE
    }
    // COB(2716): IF IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(2717): MOVE PAUTDTL1-LVL TO WS-LAST-SEGMENT-LEVEL
      ws.wsLastSegmentLevel.setValue(ws.pautdtl1Lvl);
      // COB(2718): MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
      ws.wsLastSegmentName.setString("PAUTDTL1");
      // COB(2719): END-IF.
    }
  }

  /***
   ******************************************************************
   *    SELECT ON PRIMARY KEY FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1SelectPrimaryKey() {
    // COB(2731): MOVE 'PAUTDTL1-SELECT-PRIMARY-KEY' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-SELECT-PRIMARY-KEY");
    // COB(2733): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(2734): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(2735): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(2735):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler249
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(2737): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(2738): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(2738):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.filler249.wsPackedFldChrAtIndex(0), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(2740): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2741): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler173.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(2742): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(2743): END-IF
    }
    // COB(2744): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(2744):                              PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(2746): COMPUTE WS-CK-LEN = 8
    ws.wsCkLen.setValue(8);
    // COB(2747): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(2747):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, ws.wsCkPos.getInt() - 1, 8);
    // COB(2749): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2750): MOVE IO-RTN-USED-KEY-ALPHA(2)(1:8) TO
    // COB(2750):                                 KEY-PAUT9CTS OF PAUTDTL1(1:8)
    ws.pautdtl1copy.pautdtl1.keyPaut9cts.setValue(
        params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(1)
            .ioRtnUsedSsaKeys
            .ioRtnUsedKeyAlpha,
        8);
    // COB(2752): EXEC SQL
    // COB(2752): SELECT
    // COB(2752):   KEY_PAUT9CTS
    // COB(2752):  ,PA_AUTH_ORIG_DATE
    // COB(2752):  ,PA_AUTH_ORIG_TIME
    // COB(2752):  ,PA_CARD_NUM
    // COB(2752):  ,PA_AUTH_TYPE
    // COB(2752):  ,PA_CARD_EXPIRY_DATE
    // COB(2752):  ,PA_MESSAGE_TYPE
    // COB(2752):  ,PA_MESSAGE_SOURCE
    // COB(2752):  ,PA_AUTH_ID_CODE
    // COB(2752):  ,PA_AUTH_RESP_CODE
    // COB(2752):  ,PA_AUTH_RESP_REASON
    // COB(2752):  ,PA_PROCESSING_CODE
    // COB(2752):  ,PA_TRANSACTION_AMT
    // COB(2752):  ,PA_APPROVED_AMT
    // COB(2752):  ,PA_MERCHANT_CATAGORY_CODE
    // COB(2752):  ,PA_ACQR_COUNTRY_CODE
    // COB(2752):  ,PA_POS_ENTRY_MODE
    // COB(2752):  ,PA_MERCHANT_ID
    // COB(2752):  ,PA_MERCHANT_NAME
    // COB(2752):  ,PA_MERCHANT_CITY
    // COB(2752):  ,PA_MERCHANT_STATE
    // COB(2752):  ,PA_MERCHANT_ZIP
    // COB(2752):  ,PA_TRANSACTION_ID
    // COB(2752):  ,PA_MATCH_STATUS
    // COB(2752):  ,PA_AUTH_FRAUD
    // COB(2752):  ,PA_FRAUD_RPT_DATE
    // COB(2752):  ,PAUTDTL1_FILLER
    // COB(2752):  ,PAUTSUM0_KEY_ACCNTID
    // COB(2752): INTO
    // COB(2752):   :PAUTDTL1.KEY-PAUT9CTS
    // COB(2752):  ,:PAUTDTL1.PA-AUTH-ORIG-DATE
    // COB(2752):  ,:PAUTDTL1.PA-AUTH-ORIG-TIME
    // COB(2752):  ,:PAUTDTL1.PA-CARD-NUM
    // COB(2752):  ,:PAUTDTL1.PA-AUTH-TYPE
    // COB(2752):  ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
    // COB(2752):  ,:PAUTDTL1.PA-MESSAGE-TYPE
    // COB(2752):  ,:PAUTDTL1.PA-MESSAGE-SOURCE
    // COB(2752):  ,:PAUTDTL1.PA-AUTH-ID-CODE
    // COB(2752):  ,:PAUTDTL1.PA-AUTH-RESP-CODE
    // COB(2752):  ,:PAUTDTL1.PA-AUTH-RESP-REASON
    // COB(2752):  ,:PAUTDTL1.PA-PROCESSING-CODE
    // COB(2752):  ,:PAUTDTL1.PA-TRANSACTION-AMT
    // COB(2752):  ,:PAUTDTL1.PA-APPROVED-AMT
    // COB(2752):  ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
    // COB(2752):  ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
    // COB(2752):  ,:PAUTDTL1.PA-POS-ENTRY-MODE
    // COB(2752):  ,:PAUTDTL1.PA-MERCHANT-ID
    // COB(2752):  ,:PAUTDTL1.PA-MERCHANT-NAME
    // COB(2752):  ,:PAUTDTL1.PA-MERCHANT-CITY
    // COB(2752):  ,:PAUTDTL1.PA-MERCHANT-STATE
    // COB(2752):  ,:PAUTDTL1.PA-MERCHANT-ZIP
    // COB(2752):  ,:PAUTDTL1.PA-TRANSACTION-ID
    // COB(2752):  ,:PAUTDTL1.PA-MATCH-STATUS
    // COB(2752):  ,:PAUTDTL1.PA-AUTH-FRAUD
    // COB(2752):  ,:PAUTDTL1.PA-FRAUD-RPT-DATE
    // COB(2752):  ,:PAUTDTL1.PAUTDTL1-FILLER
    // COB(2752):  ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
    // COB(2752): FROM
    // COB(2752): DBPAUTP0_PAUTDTL1
    // COB(2752):   WHERE
    // COB(2752):     KEY_PAUT9CTS =
    // COB(2752):   :PAUTDTL1.KEY-PAUT9CTS
    // COB(2752):   AND
    // COB(2752):     PAUTSUM0_KEY_ACCNTID = :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
    // COB(2752): END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand(
          "SELECT KEY_PAUT9CTS, PA_AUTH_ORIG_DATE, PA_AUTH_ORIG_TIME, PA_CARD_NUM, PA_AUTH_TYPE,"
              + " PA_CARD_EXPIRY_DATE, PA_MESSAGE_TYPE, PA_MESSAGE_SOURCE, PA_AUTH_ID_CODE,"
              + " PA_AUTH_RESP_CODE, PA_AUTH_RESP_REASON, PA_PROCESSING_CODE, PA_TRANSACTION_AMT,"
              + " PA_APPROVED_AMT, PA_MERCHANT_CATAGORY_CODE, PA_ACQR_COUNTRY_CODE,"
              + " PA_POS_ENTRY_MODE, PA_MERCHANT_ID, PA_MERCHANT_NAME, PA_MERCHANT_CITY,"
              + " PA_MERCHANT_STATE, PA_MERCHANT_ZIP, PA_TRANSACTION_ID, PA_MATCH_STATUS,"
              + " PA_AUTH_FRAUD, PA_FRAUD_RPT_DATE, PAUTDTL1_FILLER, PAUTSUM0_KEY_ACCNTID FROM"
              + " DBPAUTP0_PAUTDTL1 WHERE KEY_PAUT9CTS = ? AND PAUTSUM0_KEY_ACCNTID = ?");
      sql.into(
          ws.pautdtl1copy.pautdtl1.keyPaut9cts,
          ws.pautdtl1copy.pautdtl1.paAuthOrigDate,
          ws.pautdtl1copy.pautdtl1.paAuthOrigTime,
          ws.pautdtl1copy.pautdtl1.paCardNum,
          ws.pautdtl1copy.pautdtl1.paAuthType,
          ws.pautdtl1copy.pautdtl1.paCardExpiryDate,
          ws.pautdtl1copy.pautdtl1.paMessageType,
          ws.pautdtl1copy.pautdtl1.paMessageSource,
          ws.pautdtl1copy.pautdtl1.paAuthIdCode,
          ws.pautdtl1copy.pautdtl1.paAuthRespCode,
          ws.pautdtl1copy.pautdtl1.paAuthRespReason,
          ws.pautdtl1copy.pautdtl1.paProcessingCode,
          ws.pautdtl1copy.pautdtl1.paTransactionAmt,
          ws.pautdtl1copy.pautdtl1.paApprovedAmt,
          ws.pautdtl1copy.pautdtl1.paMerchantCatagoryCode,
          ws.pautdtl1copy.pautdtl1.paAcqrCountryCode,
          ws.pautdtl1copy.pautdtl1.paPosEntryMode,
          ws.pautdtl1copy.pautdtl1.paMerchantId,
          ws.pautdtl1copy.pautdtl1.paMerchantName,
          ws.pautdtl1copy.pautdtl1.paMerchantCity,
          ws.pautdtl1copy.pautdtl1.paMerchantState,
          ws.pautdtl1copy.pautdtl1.paMerchantZip,
          ws.pautdtl1copy.pautdtl1.paTransactionId,
          ws.pautdtl1copy.pautdtl1.paMatchStatus,
          ws.pautdtl1copy.pautdtl1.paAuthFraud,
          ws.pautdtl1copy.pautdtl1.paFraudRptDate,
          ws.pautdtl1copy.pautdtl1.pautdtl1Filler,
          ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      sql.where(ws.pautdtl1copy.pautdtl1.keyPaut9cts, ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      sql.execute();
    }
    // COB(2820): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(2821): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2822): IF SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(2823): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
      // COB(2823):                                      WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(2825): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(2825):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(2827): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
      // COB(2827):                               IO-RTN-USED-KEY-ALPHA(2)(1:8)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 8);
      // COB(2829): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
      // COB(2829):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(2831): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(2831):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(2833): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
      // COB(2833):                                      TO WS-FB-KEY-AREA(7:8)
      ws.wsFbKeyArea.setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, 6, 8);
      // COB(2835): MOVE 14 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(14);
      // COB(2839): MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
      // COB(2839): WS-PACKED-FLD-18-00(1)
      //       *
      //       * PACKED FIELDS VS SIGNED ZONED FITTING
      //       *
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCode);
      // COB(2841): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(2841): PA-PROCESSING-CODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
      // COB(2843): MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
      // COB(2843): WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryMode);
      // COB(2845): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(2845): PA-POS-ENTRY-MODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
      // COB(2847): MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(ws.pautdtl1Len);
      // COB(2848): IF HAS-PATHCALLS AND SQL-SELECT-PRIMARY
      if (ws.hasPathcalls() && params.irisWorkArea.sqlSelectPrimary()) {
        // COB(2849): PERFORM PAUTDTL1-READ-PATHCALL
        pautdtl1ReadPathcall();
        // COB(2850): ELSE
      } else {
        // COB(2851): PERFORM PAUTDTL1-SET-IO-AREA
        pautdtl1SetIoArea();
        // COB(2852): END-IF
      }
      // COB(2853): SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(2854): MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
      // COB(2854):                 IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
      ws.wsLastSegmentName.setString("PAUTDTL1");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTDTL1");
      // COB(2856): END-IF
    }
    // COB(2858): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2859): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    SELECT PATHCALL FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1ReadPathcall() {
    // COB(2870): MOVE 'PAUTDTL1-READ-PATHCALL' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-READ-PATHCALL");
    // COB(2872): SET HAS-NOT-PATHCALLS TO TRUE
    ws.setHasNotPathcalls(true);
    // COB(2873): SET HAS-PATHCALLS-ERROR TO TRUE
    ws.setHasPathcallsError(true);
    // COB(2874): MOVE WS-PATHCALL-LEN TO WS-INIT-PATHCALL-LEN
    ws.wsInitPathcallLen.setValue(ws.wsPathcallLen);
    // COB(2875): MOVE ZERO TO WS-PATHCALL-LEN
    ws.wsPathcallLen.setValue(0);
    // COB(2876): MOVE WS-SEGMENT-LEN TO WS-SAVE-SEGM-LEN
    ws.wsSaveSegmLen.setValue(ws.wsSegmentLen);
    // COB(2878): IF IS-PATHCALL-REVERSE
    if (ws.isPathcallReverse()) {
      // COB(2882): MOVE PAUTDTL1(1:PAUTDTL1-LEN)
      // COB(2882):   TO WS-PATHCALL-AREA(WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
      //       *
      //       *      ADD PAUTDTL1
      //       *
      ws.wsPathcallArea.setValue(
          ws.pautdtl1copy.pautdtl1, 0, ws.wsPathcallLen.getInt(), ws.pautdtl1Len.getInt());
      // COB(2884): ADD PAUTDTL1-LEN TO WS-PATHCALL-LEN
      ws.wsPathcallLen.add(ws.pautdtl1Len);
      // COB(2888): IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
      //       *
      //       *      SEGMENT: PAUTSUM0
      //       *
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .irisCcodeValues
          .irisCodePathcall()) {
        // COB(2889): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
        // COB(2889):                                    WS-PACKED-FLD-18-00(1)
        ws.filler173
            .wsPackedFld1800AtIndex(0)
            .setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
        // COB(2891): MOVE WS-PACKED-FLD-CHR(1)(5:6)
        // COB(2891):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
        params
            .irisDbPcb
            .ioRtnUsedKeysPcbArea
            .ioRtnUsedKeysArea
            .ioRtnUsedKeyValueAtIndex(0)
            .ioRtnUsedSsaKeys
            .ioRtnUsedKeyAlpha
            .setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
        // COB(2894): PERFORM PAUTSUM0-SELECT-PRIMARY-KEY
        pautsum0SelectPrimaryKey();
        // COB(2896): IF IRIS-SQLCODE NOT = ZERO
        if (!params.irisWorkArea.irisSqlError.irisSqlcode.equals(0)) {
          // COB(2897): IF WS-PATHCALL-LEN > ZERO
          if (ws.wsPathcallLen.greaterThan(0)) {
            // COB(2898): MOVE WS-PATHCALL-LEN TO WS-SEGMENT-LEN
            ws.wsSegmentLen.setValue(ws.wsPathcallLen);
            // COB(2899): MOVE WS-PATHCALL-AREA(1:WS-SEGMENT-LEN)
            // COB(2899):                  TO LK-IO-AREA(1:WS-SEGMENT-LEN)
            params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsSegmentLen.getInt());
            // COB(2901): END-IF
          }
          // COB(2902): GO TO PAUTDTL1-READ-PATHCALL-EX
          return;
          // COB(2903): END-IF
        }
        // COB(2905): MOVE PAUTSUM0(1:PAUTSUM0-LEN) TO
        // COB(2905):   WS-PATHCALL-AREA(WS-PATHCALL-LEN + 1:PAUTSUM0-LEN)
        ws.wsPathcallArea.setValue(
            ws.pautsum0copy.pautsum0, 0, ws.wsPathcallLen.getInt(), ws.pautsum0Len.getInt());
        // COB(2907): ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
        ws.wsPathcallLen.add(ws.pautsum0Len);
        // COB(2909): END-IF
      }
      // COB(2911): ELSE
    } else {
      // COB(2914): IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
      //       *      SEGMENT: PAUTSUM0
      //       *
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .irisCcodeValues
          .irisCodePathcall()) {
        // COB(2915): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
        // COB(2915):                                    WS-PACKED-FLD-18-00(1)
        ws.filler173
            .wsPackedFld1800AtIndex(0)
            .setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
        // COB(2917): MOVE WS-PACKED-FLD-CHR(1)(5:6)
        // COB(2917):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
        params
            .irisDbPcb
            .ioRtnUsedKeysPcbArea
            .ioRtnUsedKeysArea
            .ioRtnUsedKeyValueAtIndex(0)
            .ioRtnUsedSsaKeys
            .ioRtnUsedKeyAlpha
            .setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
        // COB(2920): PERFORM PAUTSUM0-SELECT-PRIMARY-KEY
        pautsum0SelectPrimaryKey();
        // COB(2922): IF IRIS-SQLCODE NOT = ZERO
        if (!params.irisWorkArea.irisSqlError.irisSqlcode.equals(0)) {
          // COB(2923): IF WS-PATHCALL-LEN > ZERO
          if (ws.wsPathcallLen.greaterThan(0)) {
            // COB(2924): MOVE WS-PATHCALL-LEN TO WS-SEGMENT-LEN
            ws.wsSegmentLen.setValue(ws.wsPathcallLen);
            // COB(2925): MOVE WS-PATHCALL-AREA(1:WS-SEGMENT-LEN)
            // COB(2925):                  TO LK-IO-AREA(1:WS-SEGMENT-LEN)
            params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsSegmentLen.getInt());
            // COB(2927): END-IF
          }
          // COB(2928): GO TO PAUTDTL1-READ-PATHCALL-EX
          return;
          // COB(2929): END-IF
        }
        // COB(2931): MOVE PAUTSUM0(1:PAUTSUM0-LEN) TO
        // COB(2931):   WS-PATHCALL-AREA(WS-PATHCALL-LEN + 1:PAUTSUM0-LEN)
        ws.wsPathcallArea.setValue(
            ws.pautsum0copy.pautsum0, 0, ws.wsPathcallLen.getInt(), ws.pautsum0Len.getInt());
        // COB(2933): ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
        ws.wsPathcallLen.add(ws.pautsum0Len);
        // COB(2935): END-IF
      }
      // COB(2940): MOVE PAUTDTL1(1:PAUTDTL1-LEN)
      // COB(2940):   TO WS-PATHCALL-AREA(WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
      //       *
      //       *
      //       *      ADD PAUTDTL1
      //       *
      ws.wsPathcallArea.setValue(
          ws.pautdtl1copy.pautdtl1, 0, ws.wsPathcallLen.getInt(), ws.pautdtl1Len.getInt());
      // COB(2942): ADD PAUTDTL1-LEN TO WS-PATHCALL-LEN
      ws.wsPathcallLen.add(ws.pautdtl1Len);
      // COB(2943): END-IF
    }
    // COB(2947): MOVE WS-PATHCALL-LEN TO WS-SEGMENT-LEN
    //       *
    //       *    RETURN THE PATHCALL AREA
    //       *
    ws.wsSegmentLen.setValue(ws.wsPathcallLen);
    // COB(2948): MOVE WS-PATHCALL-AREA(1:WS-SEGMENT-LEN)
    // COB(2948):                           TO LK-IO-AREA(1:WS-SEGMENT-LEN)
    params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsSegmentLen.getInt());
    // COB(2951): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
    // COB(2951):                                        WS-PACKED-FLD-18-00(1)
    ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
    // COB(2953): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(2953):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(0)
        .ioRtnUsedSsaKeys
        .ioRtnUsedKeyAlpha
        .setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
    // COB(2955): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
    // COB(2955):                                 IO-RTN-USED-KEY-ALPHA(2)(1:8)
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(1)
        .ioRtnUsedSsaKeys
        .ioRtnUsedKeyAlpha
        .setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 8);
  }

  /***
   ******************************************************************
   *    SELECT FIRST TIME FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1SelectFirst() {
    // COB(2969): MOVE 'PAUTDTL1-SELECT-FIRST' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-SELECT-FIRST");
    // COB(2971): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(2972): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(2973): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(2973):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler249
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(2975): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(2976): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(2976):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.filler249.wsPackedFldChrAtIndex(0), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(2978): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2979): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler173.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(2980): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(2981): END-IF
    }
    // COB(2982): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(2982):                              PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(2984): COMPUTE WS-CK-LEN = 8
    ws.wsCkLen.setValue(8);
    // COB(2985): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(2985):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, ws.wsCkPos.getInt() - 1, 8);
    // COB(2987): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2988): IF COMMAND-CODE-LAST
    if (ws.commandCodeLast()) {
      // COB(2989): EXEC SQL
      // COB(2989): SELECT
      // COB(2989):   KEY_PAUT9CTS
      // COB(2989):  ,PA_AUTH_ORIG_DATE
      // COB(2989):  ,PA_AUTH_ORIG_TIME
      // COB(2989):  ,PA_CARD_NUM
      // COB(2989):  ,PA_AUTH_TYPE
      // COB(2989):  ,PA_CARD_EXPIRY_DATE
      // COB(2989):  ,PA_MESSAGE_TYPE
      // COB(2989):  ,PA_MESSAGE_SOURCE
      // COB(2989):  ,PA_AUTH_ID_CODE
      // COB(2989):  ,PA_AUTH_RESP_CODE
      // COB(2989):  ,PA_AUTH_RESP_REASON
      // COB(2989):  ,PA_PROCESSING_CODE
      // COB(2989):  ,PA_TRANSACTION_AMT
      // COB(2989):  ,PA_APPROVED_AMT
      // COB(2989):  ,PA_MERCHANT_CATAGORY_CODE
      // COB(2989):  ,PA_ACQR_COUNTRY_CODE
      // COB(2989):  ,PA_POS_ENTRY_MODE
      // COB(2989):  ,PA_MERCHANT_ID
      // COB(2989):  ,PA_MERCHANT_NAME
      // COB(2989):  ,PA_MERCHANT_CITY
      // COB(2989):  ,PA_MERCHANT_STATE
      // COB(2989):  ,PA_MERCHANT_ZIP
      // COB(2989):  ,PA_TRANSACTION_ID
      // COB(2989):  ,PA_MATCH_STATUS
      // COB(2989):  ,PA_AUTH_FRAUD
      // COB(2989):  ,PA_FRAUD_RPT_DATE
      // COB(2989):  ,PAUTDTL1_FILLER
      // COB(2989):  ,PAUTSUM0_KEY_ACCNTID
      // COB(2989): INTO
      // COB(2989):   :PAUTDTL1.KEY-PAUT9CTS
      // COB(2989):  ,:PAUTDTL1.PA-AUTH-ORIG-DATE
      // COB(2989):  ,:PAUTDTL1.PA-AUTH-ORIG-TIME
      // COB(2989):  ,:PAUTDTL1.PA-CARD-NUM
      // COB(2989):  ,:PAUTDTL1.PA-AUTH-TYPE
      // COB(2989):  ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
      // COB(2989):  ,:PAUTDTL1.PA-MESSAGE-TYPE
      // COB(2989):  ,:PAUTDTL1.PA-MESSAGE-SOURCE
      // COB(2989):  ,:PAUTDTL1.PA-AUTH-ID-CODE
      // COB(2989):  ,:PAUTDTL1.PA-AUTH-RESP-CODE
      // COB(2989):  ,:PAUTDTL1.PA-AUTH-RESP-REASON
      // COB(2989):  ,:PAUTDTL1.PA-PROCESSING-CODE
      // COB(2989):  ,:PAUTDTL1.PA-TRANSACTION-AMT
      // COB(2989):  ,:PAUTDTL1.PA-APPROVED-AMT
      // COB(2989):  ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
      // COB(2989):  ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
      // COB(2989):  ,:PAUTDTL1.PA-POS-ENTRY-MODE
      // COB(2989):  ,:PAUTDTL1.PA-MERCHANT-ID
      // COB(2989):  ,:PAUTDTL1.PA-MERCHANT-NAME
      // COB(2989):  ,:PAUTDTL1.PA-MERCHANT-CITY
      // COB(2989):  ,:PAUTDTL1.PA-MERCHANT-STATE
      // COB(2989):  ,:PAUTDTL1.PA-MERCHANT-ZIP
      // COB(2989):  ,:PAUTDTL1.PA-TRANSACTION-ID
      // COB(2989):  ,:PAUTDTL1.PA-MATCH-STATUS
      // COB(2989):  ,:PAUTDTL1.PA-AUTH-FRAUD
      // COB(2989):  ,:PAUTDTL1.PA-FRAUD-RPT-DATE
      // COB(2989):  ,:PAUTDTL1.PAUTDTL1-FILLER
      // COB(2989):  ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(2989): FROM
      // COB(2989): DBPAUTP0_PAUTDTL1 T1
      // COB(2989):   WHERE
      // COB(2989):     T1.KEY_PAUT9CTS = (
      // COB(2989):     SELECT
      // COB(2989):       T2.KEY_PAUT9CTS
      // COB(2989):     FROM
      // COB(2989):       DBPAUTP0_PAUTDTL1 T2
      // COB(2989):     WHERE
      // COB(2989):       T2.PAUTSUM0_KEY_ACCNTID =
      // COB(2989):     :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(2989):     AND
      // COB(2989):       T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID
      // COB(2989):     ORDER BY 1 DESC LIMIT 1
      // COB(2989):     )
      // COB(2989):   AND
      // COB(2989):     T1.PAUTSUM0_KEY_ACCNTID =
      // COB(2989):   :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(2989): END-EXEC
      try (NSqlCommand sql = new NSqlCommand(sqlca)) {
        sql.setCommand(
            "SELECT KEY_PAUT9CTS, PA_AUTH_ORIG_DATE, PA_AUTH_ORIG_TIME, PA_CARD_NUM, PA_AUTH_TYPE,"
                + " PA_CARD_EXPIRY_DATE, PA_MESSAGE_TYPE, PA_MESSAGE_SOURCE, PA_AUTH_ID_CODE,"
                + " PA_AUTH_RESP_CODE, PA_AUTH_RESP_REASON, PA_PROCESSING_CODE, PA_TRANSACTION_AMT,"
                + " PA_APPROVED_AMT, PA_MERCHANT_CATAGORY_CODE, PA_ACQR_COUNTRY_CODE,"
                + " PA_POS_ENTRY_MODE, PA_MERCHANT_ID, PA_MERCHANT_NAME, PA_MERCHANT_CITY,"
                + " PA_MERCHANT_STATE, PA_MERCHANT_ZIP, PA_TRANSACTION_ID, PA_MATCH_STATUS,"
                + " PA_AUTH_FRAUD, PA_FRAUD_RPT_DATE, PAUTDTL1_FILLER, PAUTSUM0_KEY_ACCNTID FROM"
                + " DBPAUTP0_PAUTDTL1 T1 WHERE T1.KEY_PAUT9CTS = (SELECT T2.KEY_PAUT9CTS FROM"
                + " DBPAUTP0_PAUTDTL1 T2 WHERE T2.PAUTSUM0_KEY_ACCNTID = ? AND"
                + " T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID ORDER BY 1 DESC LIMIT 1) AND"
                + " T1.PAUTSUM0_KEY_ACCNTID = ?");
        sql.into(
            ws.pautdtl1copy.pautdtl1.keyPaut9cts,
            ws.pautdtl1copy.pautdtl1.paAuthOrigDate,
            ws.pautdtl1copy.pautdtl1.paAuthOrigTime,
            ws.pautdtl1copy.pautdtl1.paCardNum,
            ws.pautdtl1copy.pautdtl1.paAuthType,
            ws.pautdtl1copy.pautdtl1.paCardExpiryDate,
            ws.pautdtl1copy.pautdtl1.paMessageType,
            ws.pautdtl1copy.pautdtl1.paMessageSource,
            ws.pautdtl1copy.pautdtl1.paAuthIdCode,
            ws.pautdtl1copy.pautdtl1.paAuthRespCode,
            ws.pautdtl1copy.pautdtl1.paAuthRespReason,
            ws.pautdtl1copy.pautdtl1.paProcessingCode,
            ws.pautdtl1copy.pautdtl1.paTransactionAmt,
            ws.pautdtl1copy.pautdtl1.paApprovedAmt,
            ws.pautdtl1copy.pautdtl1.paMerchantCatagoryCode,
            ws.pautdtl1copy.pautdtl1.paAcqrCountryCode,
            ws.pautdtl1copy.pautdtl1.paPosEntryMode,
            ws.pautdtl1copy.pautdtl1.paMerchantId,
            ws.pautdtl1copy.pautdtl1.paMerchantName,
            ws.pautdtl1copy.pautdtl1.paMerchantCity,
            ws.pautdtl1copy.pautdtl1.paMerchantState,
            ws.pautdtl1copy.pautdtl1.paMerchantZip,
            ws.pautdtl1copy.pautdtl1.paTransactionId,
            ws.pautdtl1copy.pautdtl1.paMatchStatus,
            ws.pautdtl1copy.pautdtl1.paAuthFraud,
            ws.pautdtl1copy.pautdtl1.paFraudRptDate,
            ws.pautdtl1copy.pautdtl1.pautdtl1Filler,
            ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
        sql.where(
            ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid,
            ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
        sql.execute();
      }
      // COB(3067): ELSE
    } else {
      // COB(3068): EXEC SQL
      // COB(3068):   SELECT
      // COB(3068):     KEY_PAUT9CTS
      // COB(3068):    ,PA_AUTH_ORIG_DATE
      // COB(3068):    ,PA_AUTH_ORIG_TIME
      // COB(3068):    ,PA_CARD_NUM
      // COB(3068):    ,PA_AUTH_TYPE
      // COB(3068):    ,PA_CARD_EXPIRY_DATE
      // COB(3068):    ,PA_MESSAGE_TYPE
      // COB(3068):    ,PA_MESSAGE_SOURCE
      // COB(3068):    ,PA_AUTH_ID_CODE
      // COB(3068):    ,PA_AUTH_RESP_CODE
      // COB(3068):    ,PA_AUTH_RESP_REASON
      // COB(3068):    ,PA_PROCESSING_CODE
      // COB(3068):    ,PA_TRANSACTION_AMT
      // COB(3068):    ,PA_APPROVED_AMT
      // COB(3068):    ,PA_MERCHANT_CATAGORY_CODE
      // COB(3068):    ,PA_ACQR_COUNTRY_CODE
      // COB(3068):    ,PA_POS_ENTRY_MODE
      // COB(3068):    ,PA_MERCHANT_ID
      // COB(3068):    ,PA_MERCHANT_NAME
      // COB(3068):    ,PA_MERCHANT_CITY
      // COB(3068):    ,PA_MERCHANT_STATE
      // COB(3068):    ,PA_MERCHANT_ZIP
      // COB(3068):    ,PA_TRANSACTION_ID
      // COB(3068):    ,PA_MATCH_STATUS
      // COB(3068):    ,PA_AUTH_FRAUD
      // COB(3068):    ,PA_FRAUD_RPT_DATE
      // COB(3068):    ,PAUTDTL1_FILLER
      // COB(3068):    ,PAUTSUM0_KEY_ACCNTID
      // COB(3068):   INTO
      // COB(3068):     :PAUTDTL1.KEY-PAUT9CTS
      // COB(3068):    ,:PAUTDTL1.PA-AUTH-ORIG-DATE
      // COB(3068):    ,:PAUTDTL1.PA-AUTH-ORIG-TIME
      // COB(3068):    ,:PAUTDTL1.PA-CARD-NUM
      // COB(3068):    ,:PAUTDTL1.PA-AUTH-TYPE
      // COB(3068):    ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
      // COB(3068):    ,:PAUTDTL1.PA-MESSAGE-TYPE
      // COB(3068):    ,:PAUTDTL1.PA-MESSAGE-SOURCE
      // COB(3068):    ,:PAUTDTL1.PA-AUTH-ID-CODE
      // COB(3068):    ,:PAUTDTL1.PA-AUTH-RESP-CODE
      // COB(3068):    ,:PAUTDTL1.PA-AUTH-RESP-REASON
      // COB(3068):    ,:PAUTDTL1.PA-PROCESSING-CODE
      // COB(3068):    ,:PAUTDTL1.PA-TRANSACTION-AMT
      // COB(3068):    ,:PAUTDTL1.PA-APPROVED-AMT
      // COB(3068):    ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
      // COB(3068):    ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
      // COB(3068):    ,:PAUTDTL1.PA-POS-ENTRY-MODE
      // COB(3068):    ,:PAUTDTL1.PA-MERCHANT-ID
      // COB(3068):    ,:PAUTDTL1.PA-MERCHANT-NAME
      // COB(3068):    ,:PAUTDTL1.PA-MERCHANT-CITY
      // COB(3068):    ,:PAUTDTL1.PA-MERCHANT-STATE
      // COB(3068):    ,:PAUTDTL1.PA-MERCHANT-ZIP
      // COB(3068):    ,:PAUTDTL1.PA-TRANSACTION-ID
      // COB(3068):    ,:PAUTDTL1.PA-MATCH-STATUS
      // COB(3068):    ,:PAUTDTL1.PA-AUTH-FRAUD
      // COB(3068):    ,:PAUTDTL1.PA-FRAUD-RPT-DATE
      // COB(3068):    ,:PAUTDTL1.PAUTDTL1-FILLER
      // COB(3068):    ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3068):   FROM
      // COB(3068):   DBPAUTP0_PAUTDTL1 T1
      // COB(3068):   WHERE
      // COB(3068):     T1.KEY_PAUT9CTS = (
      // COB(3068):     SELECT
      // COB(3068):       T2.KEY_PAUT9CTS
      // COB(3068):     FROM
      // COB(3068):       DBPAUTP0_PAUTDTL1 T2
      // COB(3068):     WHERE
      // COB(3068):       T2.PAUTSUM0_KEY_ACCNTID =
      // COB(3068):     :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3068):     AND
      // COB(3068):       T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID
      // COB(3068):     ORDER BY 1 LIMIT 1
      // COB(3068):     )
      // COB(3068):   AND
      // COB(3068):     T1.PAUTSUM0_KEY_ACCNTID =
      // COB(3068):   :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3068): END-EXEC
      try (NSqlCommand sql = new NSqlCommand(sqlca)) {
        sql.setCommand(
            "SELECT KEY_PAUT9CTS, PA_AUTH_ORIG_DATE, PA_AUTH_ORIG_TIME, PA_CARD_NUM, PA_AUTH_TYPE,"
                + " PA_CARD_EXPIRY_DATE, PA_MESSAGE_TYPE, PA_MESSAGE_SOURCE, PA_AUTH_ID_CODE,"
                + " PA_AUTH_RESP_CODE, PA_AUTH_RESP_REASON, PA_PROCESSING_CODE, PA_TRANSACTION_AMT,"
                + " PA_APPROVED_AMT, PA_MERCHANT_CATAGORY_CODE, PA_ACQR_COUNTRY_CODE,"
                + " PA_POS_ENTRY_MODE, PA_MERCHANT_ID, PA_MERCHANT_NAME, PA_MERCHANT_CITY,"
                + " PA_MERCHANT_STATE, PA_MERCHANT_ZIP, PA_TRANSACTION_ID, PA_MATCH_STATUS,"
                + " PA_AUTH_FRAUD, PA_FRAUD_RPT_DATE, PAUTDTL1_FILLER, PAUTSUM0_KEY_ACCNTID FROM"
                + " DBPAUTP0_PAUTDTL1 T1 WHERE T1.KEY_PAUT9CTS = (SELECT T2.KEY_PAUT9CTS FROM"
                + " DBPAUTP0_PAUTDTL1 T2 WHERE T2.PAUTSUM0_KEY_ACCNTID = ? AND"
                + " T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID ORDER BY 1 LIMIT 1) AND"
                + " T1.PAUTSUM0_KEY_ACCNTID = ?");
        sql.into(
            ws.pautdtl1copy.pautdtl1.keyPaut9cts,
            ws.pautdtl1copy.pautdtl1.paAuthOrigDate,
            ws.pautdtl1copy.pautdtl1.paAuthOrigTime,
            ws.pautdtl1copy.pautdtl1.paCardNum,
            ws.pautdtl1copy.pautdtl1.paAuthType,
            ws.pautdtl1copy.pautdtl1.paCardExpiryDate,
            ws.pautdtl1copy.pautdtl1.paMessageType,
            ws.pautdtl1copy.pautdtl1.paMessageSource,
            ws.pautdtl1copy.pautdtl1.paAuthIdCode,
            ws.pautdtl1copy.pautdtl1.paAuthRespCode,
            ws.pautdtl1copy.pautdtl1.paAuthRespReason,
            ws.pautdtl1copy.pautdtl1.paProcessingCode,
            ws.pautdtl1copy.pautdtl1.paTransactionAmt,
            ws.pautdtl1copy.pautdtl1.paApprovedAmt,
            ws.pautdtl1copy.pautdtl1.paMerchantCatagoryCode,
            ws.pautdtl1copy.pautdtl1.paAcqrCountryCode,
            ws.pautdtl1copy.pautdtl1.paPosEntryMode,
            ws.pautdtl1copy.pautdtl1.paMerchantId,
            ws.pautdtl1copy.pautdtl1.paMerchantName,
            ws.pautdtl1copy.pautdtl1.paMerchantCity,
            ws.pautdtl1copy.pautdtl1.paMerchantState,
            ws.pautdtl1copy.pautdtl1.paMerchantZip,
            ws.pautdtl1copy.pautdtl1.paTransactionId,
            ws.pautdtl1copy.pautdtl1.paMatchStatus,
            ws.pautdtl1copy.pautdtl1.paAuthFraud,
            ws.pautdtl1copy.pautdtl1.paFraudRptDate,
            ws.pautdtl1copy.pautdtl1.pautdtl1Filler,
            ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
        sql.where(
            ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid,
            ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
        sql.execute();
      }
      // COB(3146): END-IF
    }
    // COB(3148): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(3150): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(3151): EVALUATE TRUE
    // COB(3152): WHEN IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(3153): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
      // COB(3153):                                      WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(3155): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(3155):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(3157): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
      // COB(3157):                               IO-RTN-USED-KEY-ALPHA(2)(1:8)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 8);
      // COB(3159): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
      // COB(3159):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(3161): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(3161):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(3163): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
      // COB(3163):                                      TO WS-FB-KEY-AREA(7:8)
      ws.wsFbKeyArea.setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, 6, 8);
      // COB(3165): MOVE 14 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(14);
      // COB(3169): MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
      // COB(3169): WS-PACKED-FLD-18-00(1)
      //       *
      //       * PACKED FIELDS VS SIGNED ZONED FITTING
      //       *
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCode);
      // COB(3171): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(3171): PA-PROCESSING-CODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
      // COB(3173): MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
      // COB(3173): WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryMode);
      // COB(3175): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(3175): PA-POS-ENTRY-MODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
      // COB(3177): MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(ws.pautdtl1Len);
      // COB(3178): IF HAS-PATHCALLS
      if (ws.hasPathcalls()) {
        // COB(3179): PERFORM PAUTDTL1-READ-PATHCALL
        pautdtl1ReadPathcall();
        // COB(3180): ELSE
      } else {
        // COB(3181): PERFORM PAUTDTL1-SET-IO-AREA
        pautdtl1SetIoArea();
        // COB(3182): END-IF
      }
      // COB(3183): SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(3184): MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
      // COB(3184):                 IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
      ws.wsLastSegmentName.setString("PAUTDTL1");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTDTL1");
      // COB(3186): WHEN IRIS-SQL-NOT-FOUND
    } else if (ws.irissqlc.irisSqlNotFound()) {
      // COB(3187): MOVE SQLCODE TO WS-SAVE-ROUTINE-SQLCODE
      ws.wsSaveRoutineSqlcode.setValue(sqlca.sqlcode);
      // COB(3188): MOVE SQLCA TO WS-SAVE-ROUTINE-SQLCA
      ws.wsSaveRoutineSqlca.setValue(sqlca);
      // COB(3189): MOVE WS-SAVE-ROUTINE-SQLCODE TO SQLCODE
      sqlca.sqlcode.setValue(ws.wsSaveRoutineSqlcode);
      // COB(3190): MOVE WS-SAVE-ROUTINE-SQLCA TO SQLCA
      sqlca.setValue(ws.wsSaveRoutineSqlca);
      // COB(3191): END-EVALUATE
    }
    // COB(3193): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(3194): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    SELECT NEXT FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1SelectNext() {
    // COB(3205): MOVE 'PAUTDTL1-SELECT-NEXT' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-SELECT-NEXT");
    // COB(3207): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(3208): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(3209): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(3209):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler249
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(3211): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(3212): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(3212):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.filler249.wsPackedFldChrAtIndex(0), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(3214): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(3215): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler173.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(3216): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(3217): END-IF
    }
    // COB(3218): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(3218):                              PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(3220): COMPUTE WS-CK-LEN = 8
    ws.wsCkLen.setValue(8);
    // COB(3221): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(3221):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, ws.wsCkPos.getInt() - 1, 8);
    // COB(3223): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(3224): MOVE IO-RTN-USED-KEY-ALPHA(2)(1:8) TO
    // COB(3224):                                 KEY-PAUT9CTS OF PAUTDTL1(1:8)
    ws.pautdtl1copy.pautdtl1.keyPaut9cts.setValue(
        params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(1)
            .ioRtnUsedSsaKeys
            .ioRtnUsedKeyAlpha,
        8);
    // COB(3226): IF COMMAND-CODE-LAST
    if (ws.commandCodeLast()) {
      // COB(3227): EXEC SQL
      // COB(3227): SELECT
      // COB(3227):   KEY_PAUT9CTS
      // COB(3227):  ,PA_AUTH_ORIG_DATE
      // COB(3227):  ,PA_AUTH_ORIG_TIME
      // COB(3227):  ,PA_CARD_NUM
      // COB(3227):  ,PA_AUTH_TYPE
      // COB(3227):  ,PA_CARD_EXPIRY_DATE
      // COB(3227):  ,PA_MESSAGE_TYPE
      // COB(3227):  ,PA_MESSAGE_SOURCE
      // COB(3227):  ,PA_AUTH_ID_CODE
      // COB(3227):  ,PA_AUTH_RESP_CODE
      // COB(3227):  ,PA_AUTH_RESP_REASON
      // COB(3227):  ,PA_PROCESSING_CODE
      // COB(3227):  ,PA_TRANSACTION_AMT
      // COB(3227):  ,PA_APPROVED_AMT
      // COB(3227):  ,PA_MERCHANT_CATAGORY_CODE
      // COB(3227):  ,PA_ACQR_COUNTRY_CODE
      // COB(3227):  ,PA_POS_ENTRY_MODE
      // COB(3227):  ,PA_MERCHANT_ID
      // COB(3227):  ,PA_MERCHANT_NAME
      // COB(3227):  ,PA_MERCHANT_CITY
      // COB(3227):  ,PA_MERCHANT_STATE
      // COB(3227):  ,PA_MERCHANT_ZIP
      // COB(3227):  ,PA_TRANSACTION_ID
      // COB(3227):  ,PA_MATCH_STATUS
      // COB(3227):  ,PA_AUTH_FRAUD
      // COB(3227):  ,PA_FRAUD_RPT_DATE
      // COB(3227):  ,PAUTDTL1_FILLER
      // COB(3227):  ,PAUTSUM0_KEY_ACCNTID
      // COB(3227): INTO
      // COB(3227):   :PAUTDTL1.KEY-PAUT9CTS
      // COB(3227):  ,:PAUTDTL1.PA-AUTH-ORIG-DATE
      // COB(3227):  ,:PAUTDTL1.PA-AUTH-ORIG-TIME
      // COB(3227):  ,:PAUTDTL1.PA-CARD-NUM
      // COB(3227):  ,:PAUTDTL1.PA-AUTH-TYPE
      // COB(3227):  ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
      // COB(3227):  ,:PAUTDTL1.PA-MESSAGE-TYPE
      // COB(3227):  ,:PAUTDTL1.PA-MESSAGE-SOURCE
      // COB(3227):  ,:PAUTDTL1.PA-AUTH-ID-CODE
      // COB(3227):  ,:PAUTDTL1.PA-AUTH-RESP-CODE
      // COB(3227):  ,:PAUTDTL1.PA-AUTH-RESP-REASON
      // COB(3227):  ,:PAUTDTL1.PA-PROCESSING-CODE
      // COB(3227):  ,:PAUTDTL1.PA-TRANSACTION-AMT
      // COB(3227):  ,:PAUTDTL1.PA-APPROVED-AMT
      // COB(3227):  ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
      // COB(3227):  ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
      // COB(3227):  ,:PAUTDTL1.PA-POS-ENTRY-MODE
      // COB(3227):  ,:PAUTDTL1.PA-MERCHANT-ID
      // COB(3227):  ,:PAUTDTL1.PA-MERCHANT-NAME
      // COB(3227):  ,:PAUTDTL1.PA-MERCHANT-CITY
      // COB(3227):  ,:PAUTDTL1.PA-MERCHANT-STATE
      // COB(3227):  ,:PAUTDTL1.PA-MERCHANT-ZIP
      // COB(3227):  ,:PAUTDTL1.PA-TRANSACTION-ID
      // COB(3227):  ,:PAUTDTL1.PA-MATCH-STATUS
      // COB(3227):  ,:PAUTDTL1.PA-AUTH-FRAUD
      // COB(3227):  ,:PAUTDTL1.PA-FRAUD-RPT-DATE
      // COB(3227):  ,:PAUTDTL1.PAUTDTL1-FILLER
      // COB(3227):  ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3227): FROM
      // COB(3227): DBPAUTP0_PAUTDTL1 T1
      // COB(3227):   WHERE
      // COB(3227):     T1.KEY_PAUT9CTS = (
      // COB(3227):     SELECT
      // COB(3227):       T2.KEY_PAUT9CTS
      // COB(3227):     FROM
      // COB(3227):       DBPAUTP0_PAUTDTL1 T2
      // COB(3227):     WHERE
      // COB(3227):       T2.KEY_PAUT9CTS >
      // COB(3227):       :PAUTDTL1.KEY-PAUT9CTS
      // COB(3227):     AND
      // COB(3227):       T2.PAUTSUM0_KEY_ACCNTID =
      // COB(3227):     :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3227):     AND
      // COB(3227):       T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID
      // COB(3227):     ORDER BY 1 DESC LIMIT 1
      // COB(3227):     )
      // COB(3227):   AND
      // COB(3227):     T1.PAUTSUM0_KEY_ACCNTID =
      // COB(3227):   :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3227): END-EXEC
      try (NSqlCommand sql = new NSqlCommand(sqlca)) {
        sql.setCommand(
            "SELECT KEY_PAUT9CTS, PA_AUTH_ORIG_DATE, PA_AUTH_ORIG_TIME, PA_CARD_NUM, PA_AUTH_TYPE,"
                + " PA_CARD_EXPIRY_DATE, PA_MESSAGE_TYPE, PA_MESSAGE_SOURCE, PA_AUTH_ID_CODE,"
                + " PA_AUTH_RESP_CODE, PA_AUTH_RESP_REASON, PA_PROCESSING_CODE, PA_TRANSACTION_AMT,"
                + " PA_APPROVED_AMT, PA_MERCHANT_CATAGORY_CODE, PA_ACQR_COUNTRY_CODE,"
                + " PA_POS_ENTRY_MODE, PA_MERCHANT_ID, PA_MERCHANT_NAME, PA_MERCHANT_CITY,"
                + " PA_MERCHANT_STATE, PA_MERCHANT_ZIP, PA_TRANSACTION_ID, PA_MATCH_STATUS,"
                + " PA_AUTH_FRAUD, PA_FRAUD_RPT_DATE, PAUTDTL1_FILLER, PAUTSUM0_KEY_ACCNTID FROM"
                + " DBPAUTP0_PAUTDTL1 T1 WHERE T1.KEY_PAUT9CTS = (SELECT T2.KEY_PAUT9CTS FROM"
                + " DBPAUTP0_PAUTDTL1 T2 WHERE T2.KEY_PAUT9CTS > ? AND T2.PAUTSUM0_KEY_ACCNTID = ?"
                + " AND T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID ORDER BY 1 DESC LIMIT 1)"
                + " AND T1.PAUTSUM0_KEY_ACCNTID = ?");
        sql.into(
            ws.pautdtl1copy.pautdtl1.keyPaut9cts,
            ws.pautdtl1copy.pautdtl1.paAuthOrigDate,
            ws.pautdtl1copy.pautdtl1.paAuthOrigTime,
            ws.pautdtl1copy.pautdtl1.paCardNum,
            ws.pautdtl1copy.pautdtl1.paAuthType,
            ws.pautdtl1copy.pautdtl1.paCardExpiryDate,
            ws.pautdtl1copy.pautdtl1.paMessageType,
            ws.pautdtl1copy.pautdtl1.paMessageSource,
            ws.pautdtl1copy.pautdtl1.paAuthIdCode,
            ws.pautdtl1copy.pautdtl1.paAuthRespCode,
            ws.pautdtl1copy.pautdtl1.paAuthRespReason,
            ws.pautdtl1copy.pautdtl1.paProcessingCode,
            ws.pautdtl1copy.pautdtl1.paTransactionAmt,
            ws.pautdtl1copy.pautdtl1.paApprovedAmt,
            ws.pautdtl1copy.pautdtl1.paMerchantCatagoryCode,
            ws.pautdtl1copy.pautdtl1.paAcqrCountryCode,
            ws.pautdtl1copy.pautdtl1.paPosEntryMode,
            ws.pautdtl1copy.pautdtl1.paMerchantId,
            ws.pautdtl1copy.pautdtl1.paMerchantName,
            ws.pautdtl1copy.pautdtl1.paMerchantCity,
            ws.pautdtl1copy.pautdtl1.paMerchantState,
            ws.pautdtl1copy.pautdtl1.paMerchantZip,
            ws.pautdtl1copy.pautdtl1.paTransactionId,
            ws.pautdtl1copy.pautdtl1.paMatchStatus,
            ws.pautdtl1copy.pautdtl1.paAuthFraud,
            ws.pautdtl1copy.pautdtl1.paFraudRptDate,
            ws.pautdtl1copy.pautdtl1.pautdtl1Filler,
            ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
        sql.where(
            ws.pautdtl1copy.pautdtl1.keyPaut9cts,
            ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid,
            ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
        sql.execute();
      }
      // COB(3308): ELSE
    } else {
      // COB(3309): EXEC SQL
      // COB(3309): SELECT
      // COB(3309):   KEY_PAUT9CTS
      // COB(3309):  ,PA_AUTH_ORIG_DATE
      // COB(3309):  ,PA_AUTH_ORIG_TIME
      // COB(3309):  ,PA_CARD_NUM
      // COB(3309):  ,PA_AUTH_TYPE
      // COB(3309):  ,PA_CARD_EXPIRY_DATE
      // COB(3309):  ,PA_MESSAGE_TYPE
      // COB(3309):  ,PA_MESSAGE_SOURCE
      // COB(3309):  ,PA_AUTH_ID_CODE
      // COB(3309):  ,PA_AUTH_RESP_CODE
      // COB(3309):  ,PA_AUTH_RESP_REASON
      // COB(3309):  ,PA_PROCESSING_CODE
      // COB(3309):  ,PA_TRANSACTION_AMT
      // COB(3309):  ,PA_APPROVED_AMT
      // COB(3309):  ,PA_MERCHANT_CATAGORY_CODE
      // COB(3309):  ,PA_ACQR_COUNTRY_CODE
      // COB(3309):  ,PA_POS_ENTRY_MODE
      // COB(3309):  ,PA_MERCHANT_ID
      // COB(3309):  ,PA_MERCHANT_NAME
      // COB(3309):  ,PA_MERCHANT_CITY
      // COB(3309):  ,PA_MERCHANT_STATE
      // COB(3309):  ,PA_MERCHANT_ZIP
      // COB(3309):  ,PA_TRANSACTION_ID
      // COB(3309):  ,PA_MATCH_STATUS
      // COB(3309):  ,PA_AUTH_FRAUD
      // COB(3309):  ,PA_FRAUD_RPT_DATE
      // COB(3309):  ,PAUTDTL1_FILLER
      // COB(3309):  ,PAUTSUM0_KEY_ACCNTID
      // COB(3309): INTO
      // COB(3309):   :PAUTDTL1.KEY-PAUT9CTS
      // COB(3309):  ,:PAUTDTL1.PA-AUTH-ORIG-DATE
      // COB(3309):  ,:PAUTDTL1.PA-AUTH-ORIG-TIME
      // COB(3309):  ,:PAUTDTL1.PA-CARD-NUM
      // COB(3309):  ,:PAUTDTL1.PA-AUTH-TYPE
      // COB(3309):  ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
      // COB(3309):  ,:PAUTDTL1.PA-MESSAGE-TYPE
      // COB(3309):  ,:PAUTDTL1.PA-MESSAGE-SOURCE
      // COB(3309):  ,:PAUTDTL1.PA-AUTH-ID-CODE
      // COB(3309):  ,:PAUTDTL1.PA-AUTH-RESP-CODE
      // COB(3309):  ,:PAUTDTL1.PA-AUTH-RESP-REASON
      // COB(3309):  ,:PAUTDTL1.PA-PROCESSING-CODE
      // COB(3309):  ,:PAUTDTL1.PA-TRANSACTION-AMT
      // COB(3309):  ,:PAUTDTL1.PA-APPROVED-AMT
      // COB(3309):  ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
      // COB(3309):  ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
      // COB(3309):  ,:PAUTDTL1.PA-POS-ENTRY-MODE
      // COB(3309):  ,:PAUTDTL1.PA-MERCHANT-ID
      // COB(3309):  ,:PAUTDTL1.PA-MERCHANT-NAME
      // COB(3309):  ,:PAUTDTL1.PA-MERCHANT-CITY
      // COB(3309):  ,:PAUTDTL1.PA-MERCHANT-STATE
      // COB(3309):  ,:PAUTDTL1.PA-MERCHANT-ZIP
      // COB(3309):  ,:PAUTDTL1.PA-TRANSACTION-ID
      // COB(3309):  ,:PAUTDTL1.PA-MATCH-STATUS
      // COB(3309):  ,:PAUTDTL1.PA-AUTH-FRAUD
      // COB(3309):  ,:PAUTDTL1.PA-FRAUD-RPT-DATE
      // COB(3309):  ,:PAUTDTL1.PAUTDTL1-FILLER
      // COB(3309):  ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3309): FROM
      // COB(3309): DBPAUTP0_PAUTDTL1 T1
      // COB(3309):   WHERE
      // COB(3309):     T1.KEY_PAUT9CTS = (
      // COB(3309):     SELECT
      // COB(3309):       T2.KEY_PAUT9CTS
      // COB(3309):     FROM
      // COB(3309):       DBPAUTP0_PAUTDTL1 T2
      // COB(3309):     WHERE
      // COB(3309):       T2.KEY_PAUT9CTS >
      // COB(3309):       :PAUTDTL1.KEY-PAUT9CTS
      // COB(3309):     AND
      // COB(3309):       T2.PAUTSUM0_KEY_ACCNTID =
      // COB(3309):     :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3309):     AND
      // COB(3309):       T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID
      // COB(3309):     ORDER BY 1 LIMIT 1
      // COB(3309):     )
      // COB(3309):   AND
      // COB(3309):     T1.PAUTSUM0_KEY_ACCNTID =
      // COB(3309):   :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3309): END-EXEC
      try (NSqlCommand sql = new NSqlCommand(sqlca)) {
        sql.setCommand(
            "SELECT KEY_PAUT9CTS, PA_AUTH_ORIG_DATE, PA_AUTH_ORIG_TIME, PA_CARD_NUM, PA_AUTH_TYPE,"
                + " PA_CARD_EXPIRY_DATE, PA_MESSAGE_TYPE, PA_MESSAGE_SOURCE, PA_AUTH_ID_CODE,"
                + " PA_AUTH_RESP_CODE, PA_AUTH_RESP_REASON, PA_PROCESSING_CODE, PA_TRANSACTION_AMT,"
                + " PA_APPROVED_AMT, PA_MERCHANT_CATAGORY_CODE, PA_ACQR_COUNTRY_CODE,"
                + " PA_POS_ENTRY_MODE, PA_MERCHANT_ID, PA_MERCHANT_NAME, PA_MERCHANT_CITY,"
                + " PA_MERCHANT_STATE, PA_MERCHANT_ZIP, PA_TRANSACTION_ID, PA_MATCH_STATUS,"
                + " PA_AUTH_FRAUD, PA_FRAUD_RPT_DATE, PAUTDTL1_FILLER, PAUTSUM0_KEY_ACCNTID FROM"
                + " DBPAUTP0_PAUTDTL1 T1 WHERE T1.KEY_PAUT9CTS = (SELECT T2.KEY_PAUT9CTS FROM"
                + " DBPAUTP0_PAUTDTL1 T2 WHERE T2.KEY_PAUT9CTS > ? AND T2.PAUTSUM0_KEY_ACCNTID = ?"
                + " AND T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID ORDER BY 1 LIMIT 1) AND"
                + " T1.PAUTSUM0_KEY_ACCNTID = ?");
        sql.into(
            ws.pautdtl1copy.pautdtl1.keyPaut9cts,
            ws.pautdtl1copy.pautdtl1.paAuthOrigDate,
            ws.pautdtl1copy.pautdtl1.paAuthOrigTime,
            ws.pautdtl1copy.pautdtl1.paCardNum,
            ws.pautdtl1copy.pautdtl1.paAuthType,
            ws.pautdtl1copy.pautdtl1.paCardExpiryDate,
            ws.pautdtl1copy.pautdtl1.paMessageType,
            ws.pautdtl1copy.pautdtl1.paMessageSource,
            ws.pautdtl1copy.pautdtl1.paAuthIdCode,
            ws.pautdtl1copy.pautdtl1.paAuthRespCode,
            ws.pautdtl1copy.pautdtl1.paAuthRespReason,
            ws.pautdtl1copy.pautdtl1.paProcessingCode,
            ws.pautdtl1copy.pautdtl1.paTransactionAmt,
            ws.pautdtl1copy.pautdtl1.paApprovedAmt,
            ws.pautdtl1copy.pautdtl1.paMerchantCatagoryCode,
            ws.pautdtl1copy.pautdtl1.paAcqrCountryCode,
            ws.pautdtl1copy.pautdtl1.paPosEntryMode,
            ws.pautdtl1copy.pautdtl1.paMerchantId,
            ws.pautdtl1copy.pautdtl1.paMerchantName,
            ws.pautdtl1copy.pautdtl1.paMerchantCity,
            ws.pautdtl1copy.pautdtl1.paMerchantState,
            ws.pautdtl1copy.pautdtl1.paMerchantZip,
            ws.pautdtl1copy.pautdtl1.paTransactionId,
            ws.pautdtl1copy.pautdtl1.paMatchStatus,
            ws.pautdtl1copy.pautdtl1.paAuthFraud,
            ws.pautdtl1copy.pautdtl1.paFraudRptDate,
            ws.pautdtl1copy.pautdtl1.pautdtl1Filler,
            ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
        sql.where(
            ws.pautdtl1copy.pautdtl1.keyPaut9cts,
            ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid,
            ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
        sql.execute();
      }
      // COB(3390): END-IF
    }
    // COB(3392): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(3393): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(3394): EVALUATE TRUE
    // COB(3395): WHEN IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(3396): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
      // COB(3396):                                      WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(3398): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(3398):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(3400): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
      // COB(3400):                               IO-RTN-USED-KEY-ALPHA(2)(1:8)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 8);
      // COB(3402): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
      // COB(3402):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(3404): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(3404):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(3406): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
      // COB(3406):                                      TO WS-FB-KEY-AREA(7:8)
      ws.wsFbKeyArea.setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, 6, 8);
      // COB(3408): MOVE 14 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(14);
      // COB(3412): MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
      // COB(3412): WS-PACKED-FLD-18-00(1)
      //       *
      //       * PACKED FIELDS VS SIGNED ZONED FITTING
      //       *
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCode);
      // COB(3414): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(3414): PA-PROCESSING-CODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
      // COB(3416): MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
      // COB(3416): WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryMode);
      // COB(3418): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(3418): PA-POS-ENTRY-MODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
      // COB(3420): MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(ws.pautdtl1Len);
      // COB(3421): IF HAS-PATHCALLS
      if (ws.hasPathcalls()) {
        // COB(3422): PERFORM PAUTDTL1-READ-PATHCALL
        pautdtl1ReadPathcall();
        // COB(3423): ELSE
      } else {
        // COB(3424): PERFORM PAUTDTL1-SET-IO-AREA
        pautdtl1SetIoArea();
        // COB(3425): END-IF
      }
      // COB(3426): SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(3427): MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
      // COB(3427):                 IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
      ws.wsLastSegmentName.setString("PAUTDTL1");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTDTL1");
      // COB(3429): WHEN IRIS-SQL-NOT-FOUND
    } else if (ws.irissqlc.irisSqlNotFound()) {
      // COB(3430): MOVE SQLCODE TO WS-SAVE-ROUTINE-SQLCODE
      ws.wsSaveRoutineSqlcode.setValue(sqlca.sqlcode);
      // COB(3431): MOVE SQLCA TO WS-SAVE-ROUTINE-SQLCA
      ws.wsSaveRoutineSqlca.setValue(sqlca);
      // COB(3432): MOVE WS-SAVE-ROUTINE-SQLCODE TO SQLCODE
      sqlca.sqlcode.setValue(ws.wsSaveRoutineSqlcode);
      // COB(3433): MOVE WS-SAVE-ROUTINE-SQLCA TO SQLCA
      sqlca.setValue(ws.wsSaveRoutineSqlca);
      // COB(3434): END-EVALUATE
    }
    // COB(3436): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(3437): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    DYNAMIC SELECT FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1SelectDynamic() {
    // COB(3448): MOVE 'PAUTDTL1-SELECT-DYNAMIC' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-SELECT-DYNAMIC");
    // COB(3450): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(3451): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(3452): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(3452):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler249
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(3454): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(3455): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(3455):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.filler249.wsPackedFldChrAtIndex(0), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(3457): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(3458): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler173.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(3459): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(3460): END-IF
    }
    // COB(3461): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(3461):                              PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(3463): COMPUTE WS-CK-LEN = 8
    ws.wsCkLen.setValue(8);
    // COB(3464): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(3464):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, ws.wsCkPos.getInt() - 1, 8);
    // COB(3466): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(3469): MOVE ZERO TO WS-WHERE-LEN WS-ORDERBY-LEN
    //       *    PREPARE THE WHERE CONDITION IF ANY
    //       *
    ws.wsWhereLen.setValue(0);
    ws.wsOrderbyLen.setValue(0);
    // COB(3470): IF SQL-CONDITION-CLAUSE-LENGTH > 0
    if (ws.sqlConditionClause.sqlConditionClauseLength.greaterThan(0)) {
      // COB(3471): SUBTRACT 1 FROM SQL-CONDITION-CLAUSE-LENGTH
      ws.sqlConditionClause.sqlConditionClauseLength.subtract(1);
      // COB(3472): STRING 'WHERE '
      // COB(3472): SQL-CONDITION-CLAUSE-TEXT(1:SQL-CONDITION-CLAUSE-LENGTH)
      // COB(3472): ' ' DELIMITED BY SIZE INTO WS-WHERE
      ws.wsWhere.concat(
          "WHERE ",
          ws.sqlConditionClause.sqlConditionClauseText.getString(
              0, ws.sqlConditionClause.sqlConditionClauseLength.getInt()),
          " ");
      // COB(3475): COMPUTE WS-WHERE-LEN = SQL-CONDITION-CLAUSE-LENGTH + 7
      ws.wsWhereLen.setValue(ws.sqlConditionClause.sqlConditionClauseLength.getInt() + 7);
      // COB(3476): END-IF
    }
    // COB(3478): IF SQL-ORDERBY-CLAUSE-LENGTH > 0
    if (ws.sqlOrderbyClause.sqlOrderbyClauseLength.greaterThan(0)) {
      // COB(3479): SUBTRACT 1 FROM SQL-ORDERBY-CLAUSE-LENGTH
      ws.sqlOrderbyClause.sqlOrderbyClauseLength.subtract(1);
      // COB(3480): MOVE SQL-ORDERBY-CLAUSE-TEXT(1:SQL-ORDERBY-CLAUSE-LENGTH)
      // COB(3480):                TO WS-ORDERBY(1:SQL-ORDERBY-CLAUSE-LENGTH)
      ws.wsOrderby.setValue(
          ws.sqlOrderbyClause.sqlOrderbyClauseText,
          ws.sqlOrderbyClause.sqlOrderbyClauseLength.getInt());
      // COB(3482): MOVE SQL-ORDERBY-CLAUSE-LENGTH TO WS-ORDERBY-LEN
      ws.wsOrderbyLen.setValue(ws.sqlOrderbyClause.sqlOrderbyClauseLength);
      // COB(3483): ELSE
    } else {
      // COB(3484): COMPUTE WS-ORDERBY-LEN = 1
      ws.wsOrderbyLen.setValue(1);
      // COB(3485): STRING ' ORDER BY '
      // COB(3485):       ' DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID, '
      // COB(3485):       ' KEY_PAUT9CTS '
      // COB(3485): DELIMITED BY SIZE INTO WS-ORDERBY POINTER WS-ORDERBY-LEN
      ws.wsOrderbyLen.setValue(
          ws.wsOrderby.concat(
              " ORDER BY ", " DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID, ", " KEY_PAUT9CTS "));
      // COB(3489): SUBTRACT 1 FROM WS-ORDERBY-LEN
      ws.wsOrderbyLen.subtract(1);
      // COB(3490): END-IF
    }
    // COB(3492): IF COMMAND-CODE-LAST
    if (ws.commandCodeLast()) {
      // COB(3493): STRING WS-ORDERBY(1:WS-ORDERBY-LEN)
      // COB(3493): ' DESC ' DELIMITED BY SIZE
      // COB(3493): INTO WS-ORDERBY
      ws.wsOrderby.concat(ws.wsOrderby.getString(0, ws.wsOrderbyLen.getInt()), " DESC ");
      // COB(3496): ADD 6 TO WS-ORDERBY-LEN
      ws.wsOrderbyLen.add(6);
      // COB(3497): END-IF
    }
    // COB(3502): MOVE 1 TO WS-SQL-STM-LEN
    //       *
    //       *
    //       *    PREPARE THE DYNAMIC QUERY
    //       *
    ws.wsSqlStm.wsSqlStmLen.setValue(1);
    // COB(3503): STRING
    // COB(3503): 'SELECT '
    // COB(3503):   'DBPAUTP0_PAUTDTL1.KEY_PAUT9CTS '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_AUTH_ORIG_DATE '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_AUTH_ORIG_TIME '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_CARD_NUM '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_AUTH_TYPE '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_CARD_EXPIRY_DATE '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_MESSAGE_TYPE '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_MESSAGE_SOURCE '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_AUTH_ID_CODE '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_AUTH_RESP_CODE '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_AUTH_RESP_REASON '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_PROCESSING_CODE '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_TRANSACTION_AMT '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_APPROVED_AMT '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_CATAGORY_CODE '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_ACQR_COUNTRY_CODE '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_POS_ENTRY_MODE '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_ID '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_NAME '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_CITY '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_STATE '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_ZIP '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_TRANSACTION_ID '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_MATCH_STATUS '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_AUTH_FRAUD '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PA_FRAUD_RPT_DATE '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PAUTDTL1_FILLER '
    // COB(3503):  ',DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID '
    // COB(3503): 'FROM '
    // COB(3503): 'DBPAUTP0_PAUTDTL1 '
    // COB(3503): DELIMITED BY SIZE
    // COB(3503): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
    ws.wsSqlStm.wsSqlStmLen.setValue(
        ws.wsSqlStm.wsSqlStmTxt.concat(
            "SELECT ",
            "DBPAUTP0_PAUTDTL1.KEY_PAUT9CTS ",
            ",DBPAUTP0_PAUTDTL1.PA_AUTH_ORIG_DATE ",
            ",DBPAUTP0_PAUTDTL1.PA_AUTH_ORIG_TIME ",
            ",DBPAUTP0_PAUTDTL1.PA_CARD_NUM ",
            ",DBPAUTP0_PAUTDTL1.PA_AUTH_TYPE ",
            ",DBPAUTP0_PAUTDTL1.PA_CARD_EXPIRY_DATE ",
            ",DBPAUTP0_PAUTDTL1.PA_MESSAGE_TYPE ",
            ",DBPAUTP0_PAUTDTL1.PA_MESSAGE_SOURCE ",
            ",DBPAUTP0_PAUTDTL1.PA_AUTH_ID_CODE ",
            ",DBPAUTP0_PAUTDTL1.PA_AUTH_RESP_CODE ",
            ",DBPAUTP0_PAUTDTL1.PA_AUTH_RESP_REASON ",
            ",DBPAUTP0_PAUTDTL1.PA_PROCESSING_CODE ",
            ",DBPAUTP0_PAUTDTL1.PA_TRANSACTION_AMT ",
            ",DBPAUTP0_PAUTDTL1.PA_APPROVED_AMT ",
            ",DBPAUTP0_PAUTDTL1.PA_MERCHANT_CATAGORY_CODE ",
            ",DBPAUTP0_PAUTDTL1.PA_ACQR_COUNTRY_CODE ",
            ",DBPAUTP0_PAUTDTL1.PA_POS_ENTRY_MODE ",
            ",DBPAUTP0_PAUTDTL1.PA_MERCHANT_ID ",
            ",DBPAUTP0_PAUTDTL1.PA_MERCHANT_NAME ",
            ",DBPAUTP0_PAUTDTL1.PA_MERCHANT_CITY ",
            ",DBPAUTP0_PAUTDTL1.PA_MERCHANT_STATE ",
            ",DBPAUTP0_PAUTDTL1.PA_MERCHANT_ZIP ",
            ",DBPAUTP0_PAUTDTL1.PA_TRANSACTION_ID ",
            ",DBPAUTP0_PAUTDTL1.PA_MATCH_STATUS ",
            ",DBPAUTP0_PAUTDTL1.PA_AUTH_FRAUD ",
            ",DBPAUTP0_PAUTDTL1.PA_FRAUD_RPT_DATE ",
            ",DBPAUTP0_PAUTDTL1.PAUTDTL1_FILLER ",
            ",DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID ",
            "FROM ",
            "DBPAUTP0_PAUTDTL1 "));
    // COB(3537): IF SQL-JOIN-CLAUSE-LENGTH > 0
    if (ws.sqlJoinClause.sqlJoinClauseLength.greaterThan(0)) {
      // COB(3538): SUBTRACT 1 FROM SQL-JOIN-CLAUSE-LENGTH
      ws.sqlJoinClause.sqlJoinClauseLength.subtract(1);
      // COB(3539): STRING SQL-JOIN-CLAUSE-TEXT(1:SQL-JOIN-CLAUSE-LENGTH) ' '
      // COB(3539): DELIMITED BY SIZE
      // COB(3539): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(
              ws.sqlJoinClause.sqlJoinClauseText.getString(
                  0, ws.sqlJoinClause.sqlJoinClauseLength.getInt()),
              " "));
      // COB(3542): END-IF
    }
    // COB(3543): IF WS-WHERE-LEN > 0
    if (ws.wsWhereLen.greaterThan(0)) {
      // COB(3544): STRING
      // COB(3544): WS-WHERE(1:WS-WHERE-LEN)
      // COB(3544): DELIMITED BY SIZE
      // COB(3544): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(ws.wsWhere.getString(0, ws.wsWhereLen.getInt())));
      // COB(3548): IF IRIS-TRACE-FULL
      if (params.irisWorkArea.irisTraceFull()) {
        // COB(3549): MOVE 0 TO IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
        // COB(3550): STRING '<IRISTRACE> - DBPAUTP0:SELECT-DYNAMIC' NL
        // COB(3550):      ' SECTION    =(PAUTDTL1-SELECT-DYNAMIC) ' NL
        // COB(3550):      ' CONDITION  =(' WS-WHERE(1:WS-WHERE-LEN) ')' NL
        // COB(3550):      ' ORDER BY   =(' WS-ORDERBY(1:WS-ORDERBY-LEN) ')'
        // COB(3550): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - DBPAUTP0:SELECT-DYNAMIC",
            ws.iriscomm.nl,
            " SECTION    =(PAUTDTL1-SELECT-DYNAMIC) ",
            ws.iriscomm.nl,
            " CONDITION  =(",
            ws.wsWhere.getString(0, ws.wsWhereLen.getInt()),
            ")",
            ws.iriscomm.nl,
            " ORDER BY   =(",
            ws.wsOrderby.getString(0, ws.wsOrderbyLen.getInt()),
            ")",
            ws.iriscomm.messageEnd);
        // COB(3555): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(3556): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(3557): END-IF
      }
      // COB(3558): END-IF
    }
    // COB(3559): IF WS-ORDERBY-LEN > 0
    if (ws.wsOrderbyLen.greaterThan(0)) {
      // COB(3560): STRING WS-ORDERBY(1:WS-ORDERBY-LEN) DELIMITED BY SIZE
      // COB(3560): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(ws.wsOrderby.getString(0, ws.wsOrderbyLen.getInt())));
      // COB(3562): END-IF
    }
    // COB(3563): STRING ' FETCH FIRST 1 ROW ONLY '
    // COB(3563): DELIMITED BY SIZE
    // COB(3563): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
    ws.wsSqlStm.wsSqlStmLen.setValue(ws.wsSqlStm.wsSqlStmTxt.concat(" FETCH FIRST 1 ROW ONLY "));
    // COB(3566): SUBTRACT 1 FROM WS-SQL-STM-LEN
    ws.wsSqlStm.wsSqlStmLen.subtract(1);
    // COB(3570): EXEC SQL
    // COB(3570):   DECLARE PAUTDTL1_CRS CURSOR
    // COB(3570):   FOR PAUTDTL1_STATEMENT
    // COB(3570): END-EXEC
    //       *
    //       *    DECLARE THE DYNAMIC CURSOR
    //       *
    // do nothing
    // COB(3577): EXEC SQL
    // COB(3577):   DECLARE PAUTDTL1_STATEMENT STATEMENT
    // COB(3577): END-EXEC
    //       *
    //       *    DECLARE THE SQL STATEMENT
    //       *
    // do nothing
    // COB(3583): EXEC SQL
    // COB(3583):   PREPARE PAUTDTL1_STATEMENT
    // COB(3583):   INTO :SQLDA
    // COB(3583):   FROM :WS-SQL-STM
    // COB(3583): END-EXEC
    //       *
    //       *    PREPARE THE STATEMENT
    //       *
    pautdtl1Crs = new NSqlCursor(this, sqlca, ws.wsSqlStm.toString());
    // COB(3591): IF SQLCODE NOT = ZERO
    //       *
    //       *    TEST THE RETURN CODE
    //       *
    if (!sqlca.sqlcode.equals(0)) {
      // COB(3592): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(3593): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(3594): GO TO PAUTDTL1-SELECT-DYNAMIC-EX
      return;
      // COB(3595): END-IF
    }
    // COB(3599): EXEC SQL
    // COB(3599):   OPEN PAUTDTL1_CRS
    // COB(3599): END-EXEC
    //       *
    //       *    OPEN THE DYNAMIC CURSOR
    //       *
    pautdtl1Crs.open();
    // COB(3605): IF SQLCODE NOT = ZERO
    //       *
    //       *    TEST THE RETURN CODE
    //       *
    if (!sqlca.sqlcode.equals(0)) {
      // COB(3606): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(3607): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(3608): GO TO PAUTDTL1-SELECT-DYNAMIC-EX
      return;
      // COB(3609): END-IF
    }
    // COB(3613): EXEC SQL
    // COB(3613):   FETCH PAUTDTL1_CRS
    // COB(3613):   INTO
    // COB(3613):     :PAUTDTL1.KEY-PAUT9CTS
    // COB(3613):    ,:PAUTDTL1.PA-AUTH-ORIG-DATE
    // COB(3613):    ,:PAUTDTL1.PA-AUTH-ORIG-TIME
    // COB(3613):    ,:PAUTDTL1.PA-CARD-NUM
    // COB(3613):    ,:PAUTDTL1.PA-AUTH-TYPE
    // COB(3613):    ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
    // COB(3613):    ,:PAUTDTL1.PA-MESSAGE-TYPE
    // COB(3613):    ,:PAUTDTL1.PA-MESSAGE-SOURCE
    // COB(3613):    ,:PAUTDTL1.PA-AUTH-ID-CODE
    // COB(3613):    ,:PAUTDTL1.PA-AUTH-RESP-CODE
    // COB(3613):    ,:PAUTDTL1.PA-AUTH-RESP-REASON
    // COB(3613):    ,:PAUTDTL1.PA-PROCESSING-CODE
    // COB(3613):    ,:PAUTDTL1.PA-TRANSACTION-AMT
    // COB(3613):    ,:PAUTDTL1.PA-APPROVED-AMT
    // COB(3613):    ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
    // COB(3613):    ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
    // COB(3613):    ,:PAUTDTL1.PA-POS-ENTRY-MODE
    // COB(3613):    ,:PAUTDTL1.PA-MERCHANT-ID
    // COB(3613):    ,:PAUTDTL1.PA-MERCHANT-NAME
    // COB(3613):    ,:PAUTDTL1.PA-MERCHANT-CITY
    // COB(3613):    ,:PAUTDTL1.PA-MERCHANT-STATE
    // COB(3613):    ,:PAUTDTL1.PA-MERCHANT-ZIP
    // COB(3613):    ,:PAUTDTL1.PA-TRANSACTION-ID
    // COB(3613):    ,:PAUTDTL1.PA-MATCH-STATUS
    // COB(3613):    ,:PAUTDTL1.PA-AUTH-FRAUD
    // COB(3613):    ,:PAUTDTL1.PA-FRAUD-RPT-DATE
    // COB(3613):    ,:PAUTDTL1.PAUTDTL1-FILLER
    // COB(3613):    ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
    // COB(3613): END-EXEC
    //       *
    //       *    FETCH THE DYNAMIC CURSOR
    //       *
    pautdtl1Crs.fetch(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts,
        ws.pautdtl1copy.pautdtl1.paAuthOrigDate,
        ws.pautdtl1copy.pautdtl1.paAuthOrigTime,
        ws.pautdtl1copy.pautdtl1.paCardNum,
        ws.pautdtl1copy.pautdtl1.paAuthType,
        ws.pautdtl1copy.pautdtl1.paCardExpiryDate,
        ws.pautdtl1copy.pautdtl1.paMessageType,
        ws.pautdtl1copy.pautdtl1.paMessageSource,
        ws.pautdtl1copy.pautdtl1.paAuthIdCode,
        ws.pautdtl1copy.pautdtl1.paAuthRespCode,
        ws.pautdtl1copy.pautdtl1.paAuthRespReason,
        ws.pautdtl1copy.pautdtl1.paProcessingCode,
        ws.pautdtl1copy.pautdtl1.paTransactionAmt,
        ws.pautdtl1copy.pautdtl1.paApprovedAmt,
        ws.pautdtl1copy.pautdtl1.paMerchantCatagoryCode,
        ws.pautdtl1copy.pautdtl1.paAcqrCountryCode,
        ws.pautdtl1copy.pautdtl1.paPosEntryMode,
        ws.pautdtl1copy.pautdtl1.paMerchantId,
        ws.pautdtl1copy.pautdtl1.paMerchantName,
        ws.pautdtl1copy.pautdtl1.paMerchantCity,
        ws.pautdtl1copy.pautdtl1.paMerchantState,
        ws.pautdtl1copy.pautdtl1.paMerchantZip,
        ws.pautdtl1copy.pautdtl1.paTransactionId,
        ws.pautdtl1copy.pautdtl1.paMatchStatus,
        ws.pautdtl1copy.pautdtl1.paAuthFraud,
        ws.pautdtl1copy.pautdtl1.paFraudRptDate,
        ws.pautdtl1copy.pautdtl1.pautdtl1Filler,
        ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
    // COB(3646): IF SQLCODE NOT = ZERO
    if (!sqlca.sqlcode.equals(0)) {
      // COB(3647): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(3648): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(3652): EXEC SQL
      // COB(3652):   CLOSE PAUTDTL1_CRS
      // COB(3652): END-EXEC
      //       *
      //       *      CLOSE THE DYNAMIC CURSOR
      //       *
      pautdtl1Crs.close();
      // COB(3656): MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
      ws.irissqlc.irisDbSqlcode.setValue(params.irisWorkArea.irisSqlError.irisSqlcode);
      // COB(3657): IF IRIS-SQL-NOT-FOUND
      // COB(3657): AND HAS-PATHCALLS
      // COB(3657): AND HAS-PATHCALLS-ERROR
      // COB(3657): AND SQL-SELECT-DYNAMIC
      if (ws.irissqlc.irisSqlNotFound()
          && ws.hasPathcalls()
          && ws.hasPathcallsError()
          && params.irisWorkArea.sqlSelectDynamic()) {
        // COB(3661): SET IRIS-ERR-RTN-UNHANDLED-ACCESS TO TRUE
        params.irisWorkArea.setIrisErrRtnUnhandledAccess(true);
        // COB(3662): MOVE 0 TO IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
        // COB(3663): STRING '<IRISTRACE> - DBPAUTP0:SELECT-DYNAMIC' NL
        // COB(3663):      ' SECTION    =(PAUTDTL1-SELECT-DYNAMIC) ' NL
        // COB(3663): ' ERROR      =(PATHCALL ACCESS NOT SUPPORTED FOR RC=GE)'
        // COB(3663): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - DBPAUTP0:SELECT-DYNAMIC",
            ws.iriscomm.nl,
            " SECTION    =(PAUTDTL1-SELECT-DYNAMIC) ",
            ws.iriscomm.nl,
            " ERROR      =(PATHCALL ACCESS NOT SUPPORTED FOR RC=GE)",
            ws.iriscomm.messageEnd);
        // COB(3667): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(3668): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(3669): MOVE -1 TO IRIS-SQLCODE
        params.irisWorkArea.irisSqlError.irisSqlcode.setValue(-1);
        // COB(3670): END-IF
      }
      // COB(3672): GO TO PAUTDTL1-SELECT-DYNAMIC-EX
      return;
      // COB(3673): END-IF
    }
    // COB(3675): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(3676): MOVE SQLERRM TO IRIS-SQLERRM
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
    // COB(3680): EXEC SQL
    // COB(3680):   CLOSE PAUTDTL1_CRS
    // COB(3680): END-EXEC
    //       *
    //       *      CLOSE THE DYNAMIC CURSOR
    //       *
    pautdtl1Crs.close();
    // COB(3684): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(3686): MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(params.irisWorkArea.irisSqlError.irisSqlcode);
    // COB(3687): EVALUATE TRUE
    // COB(3688): WHEN IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(3689): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
      // COB(3689):                                      WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(3691): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(3691):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(3693): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
      // COB(3693):                               IO-RTN-USED-KEY-ALPHA(2)(1:8)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 8);
      // COB(3695): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
      // COB(3695):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(3697): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(3697):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(3699): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
      // COB(3699):                                      TO WS-FB-KEY-AREA(7:8)
      ws.wsFbKeyArea.setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, 6, 8);
      // COB(3701): MOVE 14 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(14);
      // COB(3702): IF SQL-SELECT-DYNAMIC
      // COB(3702): AND NOT (COMMAND-WITH-HOLD
      // COB(3702): AND (NOT SQL-SELECT-DYNAMIC
      // COB(3702):  OR (SQL-SELECT-DYNAMIC
      // COB(3702):      AND NOT HAS-PATHCALLS)))
      if (params.irisWorkArea.sqlSelectDynamic()
          && !(ws.commandWithHold()
              && (!params.irisWorkArea.sqlSelectDynamic()
                  || (params.irisWorkArea.sqlSelectDynamic() && !ws.hasPathcalls())))) {
        // COB(3710): MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
        // COB(3710): WS-PACKED-FLD-18-00(1)
        //       *
        //       * PACKED FIELDS VS SIGNED ZONED FITTING
        //       *
        ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCode);
        // COB(3712): MOVE WS-PACKED-FLD-18-00(1) TO
        // COB(3712): PA-PROCESSING-CODE-N OF PAUTDTL1
        ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
        // COB(3714): MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
        // COB(3714): WS-PACKED-FLD-18-00(1)
        ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryMode);
        // COB(3716): MOVE WS-PACKED-FLD-18-00(1) TO
        // COB(3716): PA-POS-ENTRY-MODE-N OF PAUTDTL1
        ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
        // COB(3718): MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN
        ws.wsSegmentLen.setValue(ws.pautdtl1Len);
        // COB(3719): IF HAS-PATHCALLS
        if (ws.hasPathcalls()) {
          // COB(3720): PERFORM PAUTDTL1-READ-PATHCALL
          pautdtl1ReadPathcall();
          // COB(3721): ELSE
        } else {
          // COB(3722): PERFORM PAUTDTL1-SET-IO-AREA
          pautdtl1SetIoArea();
          // COB(3723): END-IF
        }
        // COB(3724): END-IF
      }
      // COB(3725): SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(3726): MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
      // COB(3726):                 IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
      ws.wsLastSegmentName.setString("PAUTDTL1");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTDTL1");
      // COB(3728): END-EVALUATE.
    }
  }

  /***
   ******************************************************************
   *    SELECT STATIC FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1SelectStaticCa() {
    // COB(3739): MOVE 'PAUTDTL1-SELECT-STATIC-CA' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-SELECT-STATIC-CA");
    // COB(3744): EVALUATE IRIS-CUSTOM-FUNC-ID
    //       *
    //       *
    //       *    EXECUTE THE STATIC QUERY
    //       *
    switch (params.irisWorkArea.irisCustomFuncId.getInt()) {
        // COB(3745): WHEN 0
      case 0:
        // COB(3746): SET IRIS-ERR-RTN-UNHANDLED-ACCESS TO TRUE
        params.irisWorkArea.setIrisErrRtnUnhandledAccess(true);
        // COB(3747): GO TO PAUTDTL1-SELECT-STATIC-CA-EX
        return;
        // COB(3748): WHEN 12
      case 12:
        // COB(3749): IF WS-SEGMENT-NAME NOT =
        // COB(3749):    IO-RTN-USED-LAST-SEGMENT(WS-SEGMENT-LEVEL)
        // COB(3749): OR IRIS-CODE-FIRST(WS-SEGMENT-LEVEL)
        if (!ws.wsSegmentName.equals(
                params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(
                        ws.wsSegmentLevel.getInt() - 1)
                    .ioRtnUsedSsaKeys
                    .ioRtnUsedLastSegment)
            || params
                .irisWorkArea
                .irisKeyvalueTab
                .irisCcodeLevelsAtIndex(ws.wsSegmentLevel.getInt() - 1)
                .irisCcodeValues
                .irisCodeFirst()) {
          // COB(3752): EXEC SQL
          // COB(3752):   SELECT
          // COB(3752):     KEY_PAUT9CTS
          // COB(3752):    ,PA_AUTH_ORIG_DATE
          // COB(3752):    ,PA_AUTH_ORIG_TIME
          // COB(3752):    ,PA_CARD_NUM
          // COB(3752):    ,PA_AUTH_TYPE
          // COB(3752):    ,PA_CARD_EXPIRY_DATE
          // COB(3752):    ,PA_MESSAGE_TYPE
          // COB(3752):    ,PA_MESSAGE_SOURCE
          // COB(3752):    ,PA_AUTH_ID_CODE
          // COB(3752):    ,PA_AUTH_RESP_CODE
          // COB(3752):    ,PA_AUTH_RESP_REASON
          // COB(3752):    ,PA_PROCESSING_CODE
          // COB(3752):    ,PA_TRANSACTION_AMT
          // COB(3752):    ,PA_APPROVED_AMT
          // COB(3752):    ,PA_MERCHANT_CATAGORY_CODE
          // COB(3752):    ,PA_ACQR_COUNTRY_CODE
          // COB(3752):    ,PA_POS_ENTRY_MODE
          // COB(3752):    ,PA_MERCHANT_ID
          // COB(3752):    ,PA_MERCHANT_NAME
          // COB(3752):    ,PA_MERCHANT_CITY
          // COB(3752):    ,PA_MERCHANT_STATE
          // COB(3752):    ,PA_MERCHANT_ZIP
          // COB(3752):    ,PA_TRANSACTION_ID
          // COB(3752):    ,PA_MATCH_STATUS
          // COB(3752):    ,PA_AUTH_FRAUD
          // COB(3752):    ,PA_FRAUD_RPT_DATE
          // COB(3752):    ,PAUTDTL1_FILLER
          // COB(3752):    ,PAUTSUM0_KEY_ACCNTID
          // COB(3752):   INTO
          // COB(3752):     :PAUTDTL1.KEY-PAUT9CTS
          // COB(3752):    ,:PAUTDTL1.PA-AUTH-ORIG-DATE
          // COB(3752):    ,:PAUTDTL1.PA-AUTH-ORIG-TIME
          // COB(3752):    ,:PAUTDTL1.PA-CARD-NUM
          // COB(3752):    ,:PAUTDTL1.PA-AUTH-TYPE
          // COB(3752):    ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
          // COB(3752):    ,:PAUTDTL1.PA-MESSAGE-TYPE
          // COB(3752):    ,:PAUTDTL1.PA-MESSAGE-SOURCE
          // COB(3752):    ,:PAUTDTL1.PA-AUTH-ID-CODE
          // COB(3752):    ,:PAUTDTL1.PA-AUTH-RESP-CODE
          // COB(3752):    ,:PAUTDTL1.PA-AUTH-RESP-REASON
          // COB(3752):    ,:PAUTDTL1.PA-PROCESSING-CODE
          // COB(3752):    ,:PAUTDTL1.PA-TRANSACTION-AMT
          // COB(3752):    ,:PAUTDTL1.PA-APPROVED-AMT
          // COB(3752):    ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
          // COB(3752):    ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
          // COB(3752):    ,:PAUTDTL1.PA-POS-ENTRY-MODE
          // COB(3752):    ,:PAUTDTL1.PA-MERCHANT-ID
          // COB(3752):    ,:PAUTDTL1.PA-MERCHANT-NAME
          // COB(3752):    ,:PAUTDTL1.PA-MERCHANT-CITY
          // COB(3752):    ,:PAUTDTL1.PA-MERCHANT-STATE
          // COB(3752):    ,:PAUTDTL1.PA-MERCHANT-ZIP
          // COB(3752):    ,:PAUTDTL1.PA-TRANSACTION-ID
          // COB(3752):    ,:PAUTDTL1.PA-MATCH-STATUS
          // COB(3752):    ,:PAUTDTL1.PA-AUTH-FRAUD
          // COB(3752):    ,:PAUTDTL1.PA-FRAUD-RPT-DATE
          // COB(3752):    ,:PAUTDTL1.PAUTDTL1-FILLER
          // COB(3752):    ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
          // COB(3752):   FROM
          // COB(3752):   DBPAUTP0_PAUTDTL1
          // COB(3752):   WHERE
          // COB(3752):     (KEY_PAUT9CTS = :WS-HV-001-LEN008-X)
          // COB(3752):     AND (PAUTSUM0_KEY_ACCNTID = :WS-HV-002-LEN006-P)
          // COB(3752):   ORDER BY
          // COB(3752):     DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID,
          // COB(3752):     KEY_PAUT9CTS
          // COB(3752):   FETCH FIRST 1 ROW ONLY
          // COB(3752): END-EXEC
          try (NSqlCommand sql = new NSqlCommand(sqlca)) {
            sql.setCommand(
                "SELECT KEY_PAUT9CTS, PA_AUTH_ORIG_DATE, PA_AUTH_ORIG_TIME, PA_CARD_NUM,"
                    + " PA_AUTH_TYPE, PA_CARD_EXPIRY_DATE, PA_MESSAGE_TYPE, PA_MESSAGE_SOURCE,"
                    + " PA_AUTH_ID_CODE, PA_AUTH_RESP_CODE, PA_AUTH_RESP_REASON,"
                    + " PA_PROCESSING_CODE, PA_TRANSACTION_AMT, PA_APPROVED_AMT,"
                    + " PA_MERCHANT_CATAGORY_CODE, PA_ACQR_COUNTRY_CODE, PA_POS_ENTRY_MODE,"
                    + " PA_MERCHANT_ID, PA_MERCHANT_NAME, PA_MERCHANT_CITY, PA_MERCHANT_STATE,"
                    + " PA_MERCHANT_ZIP, PA_TRANSACTION_ID, PA_MATCH_STATUS, PA_AUTH_FRAUD,"
                    + " PA_FRAUD_RPT_DATE, PAUTDTL1_FILLER, PAUTSUM0_KEY_ACCNTID FROM"
                    + " DBPAUTP0_PAUTDTL1 WHERE (KEY_PAUT9CTS = ?) AND (PAUTSUM0_KEY_ACCNTID = ?)"
                    + " ORDER BY DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID, KEY_PAUT9CTS FETCH FIRST 1"
                    + " ROW ONLY");
            sql.into(
                ws.pautdtl1copy.pautdtl1.keyPaut9cts,
                ws.pautdtl1copy.pautdtl1.paAuthOrigDate,
                ws.pautdtl1copy.pautdtl1.paAuthOrigTime,
                ws.pautdtl1copy.pautdtl1.paCardNum,
                ws.pautdtl1copy.pautdtl1.paAuthType,
                ws.pautdtl1copy.pautdtl1.paCardExpiryDate,
                ws.pautdtl1copy.pautdtl1.paMessageType,
                ws.pautdtl1copy.pautdtl1.paMessageSource,
                ws.pautdtl1copy.pautdtl1.paAuthIdCode,
                ws.pautdtl1copy.pautdtl1.paAuthRespCode,
                ws.pautdtl1copy.pautdtl1.paAuthRespReason,
                ws.pautdtl1copy.pautdtl1.paProcessingCode,
                ws.pautdtl1copy.pautdtl1.paTransactionAmt,
                ws.pautdtl1copy.pautdtl1.paApprovedAmt,
                ws.pautdtl1copy.pautdtl1.paMerchantCatagoryCode,
                ws.pautdtl1copy.pautdtl1.paAcqrCountryCode,
                ws.pautdtl1copy.pautdtl1.paPosEntryMode,
                ws.pautdtl1copy.pautdtl1.paMerchantId,
                ws.pautdtl1copy.pautdtl1.paMerchantName,
                ws.pautdtl1copy.pautdtl1.paMerchantCity,
                ws.pautdtl1copy.pautdtl1.paMerchantState,
                ws.pautdtl1copy.pautdtl1.paMerchantZip,
                ws.pautdtl1copy.pautdtl1.paTransactionId,
                ws.pautdtl1copy.pautdtl1.paMatchStatus,
                ws.pautdtl1copy.pautdtl1.paAuthFraud,
                ws.pautdtl1copy.pautdtl1.paFraudRptDate,
                ws.pautdtl1copy.pautdtl1.pautdtl1Filler,
                ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
            sql.where(ws.wsHv001Len008X, ws.wsHv002Len006P);
            sql.execute();
          }
          // COB(3821): ELSE
        } else {
          // COB(3822): EXEC SQL
          // COB(3822):   SELECT
          // COB(3822):     KEY_PAUT9CTS
          // COB(3822):    ,PA_AUTH_ORIG_DATE
          // COB(3822):    ,PA_AUTH_ORIG_TIME
          // COB(3822):    ,PA_CARD_NUM
          // COB(3822):    ,PA_AUTH_TYPE
          // COB(3822):    ,PA_CARD_EXPIRY_DATE
          // COB(3822):    ,PA_MESSAGE_TYPE
          // COB(3822):    ,PA_MESSAGE_SOURCE
          // COB(3822):    ,PA_AUTH_ID_CODE
          // COB(3822):    ,PA_AUTH_RESP_CODE
          // COB(3822):    ,PA_AUTH_RESP_REASON
          // COB(3822):    ,PA_PROCESSING_CODE
          // COB(3822):    ,PA_TRANSACTION_AMT
          // COB(3822):    ,PA_APPROVED_AMT
          // COB(3822):    ,PA_MERCHANT_CATAGORY_CODE
          // COB(3822):    ,PA_ACQR_COUNTRY_CODE
          // COB(3822):    ,PA_POS_ENTRY_MODE
          // COB(3822):    ,PA_MERCHANT_ID
          // COB(3822):    ,PA_MERCHANT_NAME
          // COB(3822):    ,PA_MERCHANT_CITY
          // COB(3822):    ,PA_MERCHANT_STATE
          // COB(3822):    ,PA_MERCHANT_ZIP
          // COB(3822):    ,PA_TRANSACTION_ID
          // COB(3822):    ,PA_MATCH_STATUS
          // COB(3822):    ,PA_AUTH_FRAUD
          // COB(3822):    ,PA_FRAUD_RPT_DATE
          // COB(3822):    ,PAUTDTL1_FILLER
          // COB(3822):    ,PAUTSUM0_KEY_ACCNTID
          // COB(3822):   INTO
          // COB(3822):     :PAUTDTL1.KEY-PAUT9CTS
          // COB(3822):    ,:PAUTDTL1.PA-AUTH-ORIG-DATE
          // COB(3822):    ,:PAUTDTL1.PA-AUTH-ORIG-TIME
          // COB(3822):    ,:PAUTDTL1.PA-CARD-NUM
          // COB(3822):    ,:PAUTDTL1.PA-AUTH-TYPE
          // COB(3822):    ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
          // COB(3822):    ,:PAUTDTL1.PA-MESSAGE-TYPE
          // COB(3822):    ,:PAUTDTL1.PA-MESSAGE-SOURCE
          // COB(3822):    ,:PAUTDTL1.PA-AUTH-ID-CODE
          // COB(3822):    ,:PAUTDTL1.PA-AUTH-RESP-CODE
          // COB(3822):    ,:PAUTDTL1.PA-AUTH-RESP-REASON
          // COB(3822):    ,:PAUTDTL1.PA-PROCESSING-CODE
          // COB(3822):    ,:PAUTDTL1.PA-TRANSACTION-AMT
          // COB(3822):    ,:PAUTDTL1.PA-APPROVED-AMT
          // COB(3822):    ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
          // COB(3822):    ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
          // COB(3822):    ,:PAUTDTL1.PA-POS-ENTRY-MODE
          // COB(3822):    ,:PAUTDTL1.PA-MERCHANT-ID
          // COB(3822):    ,:PAUTDTL1.PA-MERCHANT-NAME
          // COB(3822):    ,:PAUTDTL1.PA-MERCHANT-CITY
          // COB(3822):    ,:PAUTDTL1.PA-MERCHANT-STATE
          // COB(3822):    ,:PAUTDTL1.PA-MERCHANT-ZIP
          // COB(3822):    ,:PAUTDTL1.PA-TRANSACTION-ID
          // COB(3822):    ,:PAUTDTL1.PA-MATCH-STATUS
          // COB(3822):    ,:PAUTDTL1.PA-AUTH-FRAUD
          // COB(3822):    ,:PAUTDTL1.PA-FRAUD-RPT-DATE
          // COB(3822):    ,:PAUTDTL1.PAUTDTL1-FILLER
          // COB(3822):    ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
          // COB(3822):   FROM
          // COB(3822):   DBPAUTP0_PAUTDTL1
          // COB(3822):   WHERE
          // COB(3822):     (KEY_PAUT9CTS = :WS-HV-001-LEN008-X)
          // COB(3822):     AND (PAUTSUM0_KEY_ACCNTID = :WS-HV-002-LEN006-P)
          // COB(3822):     AND KEY_PAUT9CTS > :WS-HV-003-LEN008-X
          // COB(3822):   ORDER BY
          // COB(3822):     DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID,
          // COB(3822):     KEY_PAUT9CTS
          // COB(3822):   FETCH FIRST 1 ROW ONLY
          // COB(3822): END-EXEC
          try (NSqlCommand sql = new NSqlCommand(sqlca)) {
            sql.setCommand(
                "SELECT KEY_PAUT9CTS, PA_AUTH_ORIG_DATE, PA_AUTH_ORIG_TIME, PA_CARD_NUM,"
                    + " PA_AUTH_TYPE, PA_CARD_EXPIRY_DATE, PA_MESSAGE_TYPE, PA_MESSAGE_SOURCE,"
                    + " PA_AUTH_ID_CODE, PA_AUTH_RESP_CODE, PA_AUTH_RESP_REASON,"
                    + " PA_PROCESSING_CODE, PA_TRANSACTION_AMT, PA_APPROVED_AMT,"
                    + " PA_MERCHANT_CATAGORY_CODE, PA_ACQR_COUNTRY_CODE, PA_POS_ENTRY_MODE,"
                    + " PA_MERCHANT_ID, PA_MERCHANT_NAME, PA_MERCHANT_CITY, PA_MERCHANT_STATE,"
                    + " PA_MERCHANT_ZIP, PA_TRANSACTION_ID, PA_MATCH_STATUS, PA_AUTH_FRAUD,"
                    + " PA_FRAUD_RPT_DATE, PAUTDTL1_FILLER, PAUTSUM0_KEY_ACCNTID FROM"
                    + " DBPAUTP0_PAUTDTL1 WHERE (KEY_PAUT9CTS = ?) AND (PAUTSUM0_KEY_ACCNTID = ?)"
                    + " AND KEY_PAUT9CTS > ? ORDER BY DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID,"
                    + " KEY_PAUT9CTS FETCH FIRST 1 ROW ONLY");
            sql.into(
                ws.pautdtl1copy.pautdtl1.keyPaut9cts,
                ws.pautdtl1copy.pautdtl1.paAuthOrigDate,
                ws.pautdtl1copy.pautdtl1.paAuthOrigTime,
                ws.pautdtl1copy.pautdtl1.paCardNum,
                ws.pautdtl1copy.pautdtl1.paAuthType,
                ws.pautdtl1copy.pautdtl1.paCardExpiryDate,
                ws.pautdtl1copy.pautdtl1.paMessageType,
                ws.pautdtl1copy.pautdtl1.paMessageSource,
                ws.pautdtl1copy.pautdtl1.paAuthIdCode,
                ws.pautdtl1copy.pautdtl1.paAuthRespCode,
                ws.pautdtl1copy.pautdtl1.paAuthRespReason,
                ws.pautdtl1copy.pautdtl1.paProcessingCode,
                ws.pautdtl1copy.pautdtl1.paTransactionAmt,
                ws.pautdtl1copy.pautdtl1.paApprovedAmt,
                ws.pautdtl1copy.pautdtl1.paMerchantCatagoryCode,
                ws.pautdtl1copy.pautdtl1.paAcqrCountryCode,
                ws.pautdtl1copy.pautdtl1.paPosEntryMode,
                ws.pautdtl1copy.pautdtl1.paMerchantId,
                ws.pautdtl1copy.pautdtl1.paMerchantName,
                ws.pautdtl1copy.pautdtl1.paMerchantCity,
                ws.pautdtl1copy.pautdtl1.paMerchantState,
                ws.pautdtl1copy.pautdtl1.paMerchantZip,
                ws.pautdtl1copy.pautdtl1.paTransactionId,
                ws.pautdtl1copy.pautdtl1.paMatchStatus,
                ws.pautdtl1copy.pautdtl1.paAuthFraud,
                ws.pautdtl1copy.pautdtl1.paFraudRptDate,
                ws.pautdtl1copy.pautdtl1.pautdtl1Filler,
                ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
            sql.where(ws.wsHv001Len008X, ws.wsHv002Len006P, ws.wsHv003Len008X);
            sql.execute();
          }
          // COB(3892): END-IF
        }
        // COB(3893): WHEN OTHER
        break;
      default:
        // COB(3894): SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
        params.irisWorkArea.setIrisErrFunctionNotFound(true);
        // COB(3895): GO TO PAUTDTL1-SELECT-STATIC-CA-EX
        return;
        // COB(3896): END-EVALUATE
    }
    // COB(3898): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(3899): MOVE SQLERRM TO IRIS-SQLERRM
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
    // COB(3903): IF SQLCODE NOT = ZERO
    //       *
    //       *    TEST THE RETURN CODE
    //       *
    if (!sqlca.sqlcode.equals(0)) {
      // COB(3904): GO TO PAUTDTL1-SELECT-STATIC-CA-EX
      return;
      // COB(3905): END-IF
    }
    // COB(3907): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(3909): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
    // COB(3909):                                        WS-PACKED-FLD-18-00(1)
    ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
    // COB(3911): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(3911):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(0)
        .ioRtnUsedSsaKeys
        .ioRtnUsedKeyAlpha
        .setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
    // COB(3913): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
    // COB(3913):                                 IO-RTN-USED-KEY-ALPHA(2)(1:8)
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(1)
        .ioRtnUsedSsaKeys
        .ioRtnUsedKeyAlpha
        .setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 8);
    // COB(3915): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    // COB(3915):                                     TO WS-PACKED-FLD-18-00(1)
    ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
    // COB(3917): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(3917):                                        TO WS-FB-KEY-AREA(1:6)
    ws.wsFbKeyArea.setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
    // COB(3919): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(3919):                                        TO WS-FB-KEY-AREA(7:8)
    ws.wsFbKeyArea.setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, 6, 8);
    // COB(3921): MOVE 14 TO WS-FB-KEY-LENGTH
    ws.wsFbKeyLength.setValue(14);
    // COB(3925): MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
    // COB(3925): WS-PACKED-FLD-18-00(1)
    //       *
    //       * PACKED FIELDS VS SIGNED ZONED FITTING
    //       *
    ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCode);
    // COB(3927): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(3927): PA-PROCESSING-CODE-N OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(3929): MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
    // COB(3929): WS-PACKED-FLD-18-00(1)
    ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryMode);
    // COB(3931): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(3931): PA-POS-ENTRY-MODE-N OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(3933): MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN
    ws.wsSegmentLen.setValue(ws.pautdtl1Len);
    // COB(3934): IF HAS-PATHCALLS
    if (ws.hasPathcalls()) {
      // COB(3935): PERFORM PAUTDTL1-READ-PATHCALL
      pautdtl1ReadPathcall();
      // COB(3936): ELSE
    } else {
      // COB(3937): PERFORM PAUTDTL1-SET-IO-AREA
      pautdtl1SetIoArea();
      // COB(3938): END-IF
    }
    // COB(3940): SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
        .setIoRtnUsedKeyChanged(true);
    // COB(3941): MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
    // COB(3941):                   IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL).
    ws.wsLastSegmentName.setString("PAUTDTL1");
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
        .ioRtnUsedSsaKeys
        .ioRtnUsedLastSegment
        .setString("PAUTDTL1");
  }

  /***
   ******************************************************************
   *    INSERT FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1Insert() {
    // COB(3953): MOVE 'PAUTDTL1-INSERT' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-INSERT");
    // COB(3955): IF HAS-PATHCALLS
    if (ws.hasPathcalls()) {
      // COB(3956): PERFORM PAUTDTL1-INSERT-PATHCALL
      pautdtl1InsertPathcall();
      // COB(3957): GO TO PAUTDTL1-INSERT-EX
      return;
      // COB(3958): END-IF
    }
    // COB(3960): INITIALIZE PAUTDTL1
    ws.pautdtl1copy.pautdtl1.initialize();
    // COB(3962): MOVE LK-IO-AREA(1:PAUTDTL1-LEN) TO PAUTDTL1(1:PAUTDTL1-LEN)
    ws.pautdtl1copy.pautdtl1.setValue(params.lkIoArea, ws.pautdtl1Len.getInt());
    // COB(3963): IF PA-PROCESSING-CODE-N
    // COB(3963): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paProcessingCodeN.isNumeric()) {
      // COB(3965): MOVE ZERO TO
      // COB(3965): PA-PROCESSING-CODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(0);
      // COB(3967): END-IF
    }
    // COB(3968): IF PA-TRANSACTION-AMT
    // COB(3968): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paTransactionAmt.isNumeric()) {
      // COB(3970): MOVE ZERO TO
      // COB(3970): PA-TRANSACTION-AMT OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paTransactionAmt.setValue(0);
      // COB(3972): END-IF
    }
    // COB(3973): IF PA-APPROVED-AMT
    // COB(3973): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paApprovedAmt.isNumeric()) {
      // COB(3975): MOVE ZERO TO
      // COB(3975): PA-APPROVED-AMT OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paApprovedAmt.setValue(0);
      // COB(3977): END-IF
    }
    // COB(3978): IF PA-POS-ENTRY-MODE-N
    // COB(3978): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paPosEntryModeN.isNumeric()) {
      // COB(3980): MOVE ZERO TO
      // COB(3980): PA-POS-ENTRY-MODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(0);
      // COB(3982): END-IF
    }
    // COB(3983): IF PAUTSUM0-KEY-ACCNTID
    // COB(3983): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.isNumeric()) {
      // COB(3985): MOVE ZERO TO
      // COB(3985): PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(0);
      // COB(3987): END-IF
    }
    // COB(3991): MOVE PA-PROCESSING-CODE-N OF PAUTDTL1 TO
    // COB(3991): WS-PACKED-FLD-18-00(1)
    //       *
    //       * SIGNED ZONED FIELDS VS PACKED FITTING
    //       *
    ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCodeN);
    // COB(3993): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(3993): PA-PROCESSING-CODE OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.paProcessingCode.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(3995): MOVE PA-POS-ENTRY-MODE-N OF PAUTDTL1 TO
    // COB(3995): WS-PACKED-FLD-18-00(1)
    ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryModeN);
    // COB(3997): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(3997): PA-POS-ENTRY-MODE OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.paPosEntryMode.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(3999): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(4000): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(4001): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(4001):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler249
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(4003): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(4004): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(4004):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.filler249.wsPackedFldChrAtIndex(0), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(4006): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(4007): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler173.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(4008): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(4009): END-IF
    }
    // COB(4010): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(4010):                              PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(4012): COMPUTE WS-CK-LEN = 8
    ws.wsCkLen.setValue(8);
    // COB(4013): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(4013):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, ws.wsCkPos.getInt() - 1, 8);
    // COB(4015): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(4018): EXEC SQL
    // COB(4018):   INSERT INTO
    // COB(4018):     DBPAUTP0_PAUTDTL1
    // COB(4018):     (
    // COB(4018):     KEY_PAUT9CTS
    // COB(4018):    ,PA_AUTH_ORIG_DATE
    // COB(4018):    ,PA_AUTH_ORIG_TIME
    // COB(4018):    ,PA_CARD_NUM
    // COB(4018):    ,PA_AUTH_TYPE
    // COB(4018):    ,PA_CARD_EXPIRY_DATE
    // COB(4018):    ,PA_MESSAGE_TYPE
    // COB(4018):    ,PA_MESSAGE_SOURCE
    // COB(4018):    ,PA_AUTH_ID_CODE
    // COB(4018):    ,PA_AUTH_RESP_CODE
    // COB(4018):    ,PA_AUTH_RESP_REASON
    // COB(4018):    ,PA_PROCESSING_CODE
    // COB(4018):    ,PA_TRANSACTION_AMT
    // COB(4018):    ,PA_APPROVED_AMT
    // COB(4018):    ,PA_MERCHANT_CATAGORY_CODE
    // COB(4018):    ,PA_ACQR_COUNTRY_CODE
    // COB(4018):    ,PA_POS_ENTRY_MODE
    // COB(4018):    ,PA_MERCHANT_ID
    // COB(4018):    ,PA_MERCHANT_NAME
    // COB(4018):    ,PA_MERCHANT_CITY
    // COB(4018):    ,PA_MERCHANT_STATE
    // COB(4018):    ,PA_MERCHANT_ZIP
    // COB(4018):    ,PA_TRANSACTION_ID
    // COB(4018):    ,PA_MATCH_STATUS
    // COB(4018):    ,PA_AUTH_FRAUD
    // COB(4018):    ,PA_FRAUD_RPT_DATE
    // COB(4018):    ,PAUTDTL1_FILLER
    // COB(4018):    ,PAUTSUM0_KEY_ACCNTID
    // COB(4018):     )
    // COB(4018):   VALUES
    // COB(4018):   (
    // COB(4018):     :PAUTDTL1.KEY-PAUT9CTS
    // COB(4018):    ,:PAUTDTL1.PA-AUTH-ORIG-DATE
    // COB(4018):    ,:PAUTDTL1.PA-AUTH-ORIG-TIME
    // COB(4018):    ,:PAUTDTL1.PA-CARD-NUM
    // COB(4018):    ,:PAUTDTL1.PA-AUTH-TYPE
    // COB(4018):    ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
    // COB(4018):    ,:PAUTDTL1.PA-MESSAGE-TYPE
    // COB(4018):    ,:PAUTDTL1.PA-MESSAGE-SOURCE
    // COB(4018):    ,:PAUTDTL1.PA-AUTH-ID-CODE
    // COB(4018):    ,:PAUTDTL1.PA-AUTH-RESP-CODE
    // COB(4018):    ,:PAUTDTL1.PA-AUTH-RESP-REASON
    // COB(4018):    ,:PAUTDTL1.PA-PROCESSING-CODE
    // COB(4018):    ,:PAUTDTL1.PA-TRANSACTION-AMT
    // COB(4018):    ,:PAUTDTL1.PA-APPROVED-AMT
    // COB(4018):    ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
    // COB(4018):    ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
    // COB(4018):    ,:PAUTDTL1.PA-POS-ENTRY-MODE
    // COB(4018):    ,:PAUTDTL1.PA-MERCHANT-ID
    // COB(4018):    ,:PAUTDTL1.PA-MERCHANT-NAME
    // COB(4018):    ,:PAUTDTL1.PA-MERCHANT-CITY
    // COB(4018):    ,:PAUTDTL1.PA-MERCHANT-STATE
    // COB(4018):    ,:PAUTDTL1.PA-MERCHANT-ZIP
    // COB(4018):    ,:PAUTDTL1.PA-TRANSACTION-ID
    // COB(4018):    ,:PAUTDTL1.PA-MATCH-STATUS
    // COB(4018):    ,:PAUTDTL1.PA-AUTH-FRAUD
    // COB(4018):    ,:PAUTDTL1.PA-FRAUD-RPT-DATE
    // COB(4018):    ,:PAUTDTL1.PAUTDTL1-FILLER
    // COB(4018):    ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
    // COB(4018):   )
    // COB(4018): END-EXEC
    //       *
    //       *
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand(
          "INSERT INTO DBPAUTP0_PAUTDTL1 (KEY_PAUT9CTS, PA_AUTH_ORIG_DATE, PA_AUTH_ORIG_TIME,"
              + " PA_CARD_NUM, PA_AUTH_TYPE, PA_CARD_EXPIRY_DATE, PA_MESSAGE_TYPE,"
              + " PA_MESSAGE_SOURCE, PA_AUTH_ID_CODE, PA_AUTH_RESP_CODE, PA_AUTH_RESP_REASON,"
              + " PA_PROCESSING_CODE, PA_TRANSACTION_AMT, PA_APPROVED_AMT,"
              + " PA_MERCHANT_CATAGORY_CODE, PA_ACQR_COUNTRY_CODE, PA_POS_ENTRY_MODE,"
              + " PA_MERCHANT_ID, PA_MERCHANT_NAME, PA_MERCHANT_CITY, PA_MERCHANT_STATE,"
              + " PA_MERCHANT_ZIP, PA_TRANSACTION_ID, PA_MATCH_STATUS, PA_AUTH_FRAUD,"
              + " PA_FRAUD_RPT_DATE, PAUTDTL1_FILLER, PAUTSUM0_KEY_ACCNTID) VALUES (?, ?, ?, ?, ?,"
              + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
      sql.values(
          ws.pautdtl1copy.pautdtl1.keyPaut9cts,
          ws.pautdtl1copy.pautdtl1.paAuthOrigDate,
          ws.pautdtl1copy.pautdtl1.paAuthOrigTime,
          ws.pautdtl1copy.pautdtl1.paCardNum,
          ws.pautdtl1copy.pautdtl1.paAuthType,
          ws.pautdtl1copy.pautdtl1.paCardExpiryDate,
          ws.pautdtl1copy.pautdtl1.paMessageType,
          ws.pautdtl1copy.pautdtl1.paMessageSource,
          ws.pautdtl1copy.pautdtl1.paAuthIdCode,
          ws.pautdtl1copy.pautdtl1.paAuthRespCode,
          ws.pautdtl1copy.pautdtl1.paAuthRespReason,
          ws.pautdtl1copy.pautdtl1.paProcessingCode,
          ws.pautdtl1copy.pautdtl1.paTransactionAmt,
          ws.pautdtl1copy.pautdtl1.paApprovedAmt,
          ws.pautdtl1copy.pautdtl1.paMerchantCatagoryCode,
          ws.pautdtl1copy.pautdtl1.paAcqrCountryCode,
          ws.pautdtl1copy.pautdtl1.paPosEntryMode,
          ws.pautdtl1copy.pautdtl1.paMerchantId,
          ws.pautdtl1copy.pautdtl1.paMerchantName,
          ws.pautdtl1copy.pautdtl1.paMerchantCity,
          ws.pautdtl1copy.pautdtl1.paMerchantState,
          ws.pautdtl1copy.pautdtl1.paMerchantZip,
          ws.pautdtl1copy.pautdtl1.paTransactionId,
          ws.pautdtl1copy.pautdtl1.paMatchStatus,
          ws.pautdtl1copy.pautdtl1.paAuthFraud,
          ws.pautdtl1copy.pautdtl1.paFraudRptDate,
          ws.pautdtl1copy.pautdtl1.pautdtl1Filler,
          ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      sql.execute();
    }
    // COB(4084): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(4085): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(4086): EVALUATE TRUE
    // COB(4087): WHEN IRIS-SQL-OK
    if ((ws.irissqlc.irisSqlOk())
        // COB(4088): WHEN IRIS-SQL-UNIQ-CONSTR-VIOLATED
        || ws.irissqlc.irisSqlUniqConstrViolated()) {
      // COB(4089): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
      // COB(4089):                                      WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(4091): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(4091):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(4093): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
      // COB(4093):                               IO-RTN-USED-KEY-ALPHA(2)(1:8)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 8);
      // COB(4095): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
      // COB(4095):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(4097): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(4097):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler249.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(4099): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
      // COB(4099):                                      TO WS-FB-KEY-AREA(7:8)
      ws.wsFbKeyArea.setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, 6, 8);
      // COB(4101): MOVE 14 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(14);
      // COB(4102): SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(4103): IF IRIS-SQL-UNIQ-CONSTR-VIOLATED
      if (ws.irissqlc.irisSqlUniqConstrViolated()) {
        // COB(4104): SET IO-RTN-USED-KEY-DUPREC(PAUTDTL1-LVL) TO TRUE
        params
            .irisDbPcb
            .ioRtnUsedKeysPcbArea
            .ioRtnUsedKeysArea
            .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
            .setIoRtnUsedKeyDuprec(true);
        // COB(4105): END-IF
      }
      // COB(4106): MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
      // COB(4106):                 IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
      ws.wsLastSegmentName.setString("PAUTDTL1");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTDTL1");
      // COB(4108): END-EVALUATE
    }
    // COB(4110): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(4111): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    INSERT PATHCALL FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1InsertPathcall() {
    // COB(4122): MOVE 'PAUTDTL1-INSERT-PATHCALL' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-INSERT-PATHCALL");
    // COB(4125): SET HAS-NOT-PATHCALLS TO TRUE
    //       *
    //       *
    ws.setHasNotPathcalls(true);
    // COB(4126): SET HAS-PATHCALLS-ERROR TO TRUE
    ws.setHasPathcallsError(true);
    // COB(4127): MOVE LK-IO-AREA(1:WS-PATHCALL-LEN)
    // COB(4127):            TO WS-PATHCALL-AREA(1:WS-PATHCALL-LEN)
    ws.wsPathcallArea.setValue(params.lkIoArea, ws.wsPathcallLen.getInt());
    // COB(4129): MOVE WS-PATHCALL-LEN TO WS-INIT-PATHCALL-LEN
    ws.wsInitPathcallLen.setValue(ws.wsPathcallLen);
    // COB(4130): MOVE ZERO TO WS-PATHCALL-LEN
    ws.wsPathcallLen.setValue(0);
    // COB(4134): IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
    //       *
    //       *    SEGMENT: PAUTSUM0
    //       *
    if (params
        .irisWorkArea
        .irisKeyvalueTab
        .irisCcodeLevelsAtIndex(ws.pautsum0Lvl.getInt() - 1)
        .irisCcodeValues
        .irisCodePathcall()) {
      // COB(4136): MOVE WS-PATHCALL-AREA
      // COB(4136):      (WS-PATHCALL-LEN + 1:PAUTSUM0-LEN)
      // COB(4136):                           TO LK-IO-AREA(1:PAUTSUM0-LEN)
      params.lkIoArea.setValue(
          ws.wsPathcallArea, ws.wsPathcallLen.getInt(), 0, ws.pautsum0Len.getInt());
      // COB(4140): PERFORM PAUTSUM0-INSERT
      pautsum0Insert();
      // COB(4142): IF IRIS-SQLCODE NOT = ZERO
      if (!params.irisWorkArea.irisSqlError.irisSqlcode.equals(0)) {
        // COB(4143): MOVE WS-PATHCALL-AREA(1:WS-INIT-PATHCALL-LEN)
        // COB(4143):                  TO LK-IO-AREA(1:WS-INIT-PATHCALL-LEN)
        params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsInitPathcallLen.getInt());
        // COB(4145): GO TO PAUTDTL1-INSERT-PATHCALL-EX
        return;
        // COB(4146): END-IF
      }
      // COB(4148): ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
      ws.wsPathcallLen.add(ws.pautsum0Len);
      // COB(4150): END-IF
    }
    // COB(4155): MOVE WS-PATHCALL-AREA
    // COB(4155):      (WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
    // COB(4155):                           TO LK-IO-AREA(1:PAUTDTL1-LEN)
    //       *
    //       *
    //       *    INSERT PAUTDTL1
    //       *
    params.lkIoArea.setValue(
        ws.wsPathcallArea, ws.wsPathcallLen.getInt(), 0, ws.pautdtl1Len.getInt());
    // COB(4159): PERFORM PAUTDTL1-INSERT
    pautdtl1Insert();
    // COB(4161): MOVE WS-INIT-PATHCALL-LEN TO WS-PATHCALL-LEN WS-SEGMENT-LEN
    ws.wsPathcallLen.setValue(ws.wsInitPathcallLen);
    ws.wsSegmentLen.setValue(ws.wsInitPathcallLen);
    // COB(4162): MOVE WS-PATHCALL-AREA(1:WS-PATHCALL-LEN)
    // COB(4162):                           TO LK-IO-AREA(1:WS-PATHCALL-LEN).
    params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsPathcallLen.getInt());
  }

  /***
   ******************************************************************
   *    UPDATE PATHCALL FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1UpdatePathcall() {
    // COB(4175): MOVE 'PAUTDTL1-UPDATE-PATHCALL' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-UPDATE-PATHCALL");
    // COB(4178): SET HAS-NOT-PATHCALLS TO TRUE
    //       *
    //       *
    ws.setHasNotPathcalls(true);
    // COB(4179): SET HAS-PATHCALLS-ERROR TO TRUE
    ws.setHasPathcallsError(true);
    // COB(4180): MOVE LK-IO-AREA(1:WS-PATHCALL-LEN)
    // COB(4180):            TO WS-PATHCALL-AREA(1:WS-PATHCALL-LEN)
    ws.wsPathcallArea.setValue(params.lkIoArea, ws.wsPathcallLen.getInt());
    // COB(4182): MOVE WS-PATHCALL-LEN TO WS-INIT-PATHCALL-LEN
    ws.wsInitPathcallLen.setValue(ws.wsPathcallLen);
    // COB(4183): MOVE ZERO TO WS-PATHCALL-LEN
    ws.wsPathcallLen.setValue(0);
    // COB(4185): IF IS-PATHCALL-REVERSE
    if (ws.isPathcallReverse()) {
      // COB(4189): IF IRIS-CODE-PATHCALL(PAUTDTL1-LVL)
      //       *
      //       *      SEGMENT: PAUTDTL1
      //       *
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .irisCcodeValues
          .irisCodePathcall()) {
        // COB(4190): MOVE WS-PATHCALL-AREA
        // COB(4190):      (WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
        // COB(4190):                       TO LK-IO-AREA(1:PAUTDTL1-LEN)
        params.lkIoArea.setValue(
            ws.wsPathcallArea, ws.wsPathcallLen.getInt(), 0, ws.pautdtl1Len.getInt());
        // COB(4194): PERFORM PAUTDTL1-UPDATE
        pautdtl1Update();
        // COB(4196): IF IRIS-SQLCODE NOT = ZERO
        if (!params.irisWorkArea.irisSqlError.irisSqlcode.equals(0)) {
          // COB(4197): MOVE WS-PATHCALL-AREA(1:WS-INIT-PATHCALL-LEN)
          // COB(4197):                  TO LK-IO-AREA(1:WS-INIT-PATHCALL-LEN)
          params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsInitPathcallLen.getInt());
          // COB(4199): GO TO PAUTDTL1-UPDATE-PATHCALL-EX
          return;
          // COB(4200): END-IF
        }
        // COB(4202): ADD PAUTDTL1-LEN TO WS-PATHCALL-LEN
        ws.wsPathcallLen.add(ws.pautdtl1Len);
        // COB(4204): END-IF
      }
      // COB(4205): ELSE
    } else {
      // COB(4209): IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
      //       *
      //       *      SEGMENT: PAUTSUM0
      //       *
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .irisCcodeValues
          .irisCodePathcall()) {
        // COB(4211): MOVE WS-PATHCALL-AREA
        // COB(4211):      (WS-PATHCALL-LEN + 1:PAUTSUM0-LEN)
        // COB(4211):                         TO LK-IO-AREA(1:PAUTSUM0-LEN)
        params.lkIoArea.setValue(
            ws.wsPathcallArea, ws.wsPathcallLen.getInt(), 0, ws.pautsum0Len.getInt());
        // COB(4215): PERFORM PAUTSUM0-UPDATE
        pautsum0Update();
        // COB(4217): IF IRIS-SQLCODE NOT = ZERO
        if (!params.irisWorkArea.irisSqlError.irisSqlcode.equals(0)) {
          // COB(4218): MOVE WS-PATHCALL-AREA(1:WS-INIT-PATHCALL-LEN)
          // COB(4218):                  TO LK-IO-AREA(1:WS-INIT-PATHCALL-LEN)
          params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsInitPathcallLen.getInt());
          // COB(4220): GO TO PAUTDTL1-UPDATE-PATHCALL-EX
          return;
          // COB(4221): END-IF
        }
        // COB(4223): ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
        ws.wsPathcallLen.add(ws.pautsum0Len);
        // COB(4225): END-IF
      }
      // COB(4230): MOVE WS-PATHCALL-AREA
      // COB(4230):      (WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
      // COB(4230):                           TO LK-IO-AREA(1:PAUTDTL1-LEN)
      //       *
      //       *
      //       *      UPDATE PAUTDTL1
      //       *
      params.lkIoArea.setValue(
          ws.wsPathcallArea, ws.wsPathcallLen.getInt(), 0, ws.pautdtl1Len.getInt());
      // COB(4234): PERFORM PAUTDTL1-UPDATE
      pautdtl1Update();
      // COB(4235): END-IF
    }
    // COB(4237): MOVE WS-INIT-PATHCALL-LEN TO WS-PATHCALL-LEN WS-SEGMENT-LEN
    ws.wsPathcallLen.setValue(ws.wsInitPathcallLen);
    ws.wsSegmentLen.setValue(ws.wsInitPathcallLen);
    // COB(4238): MOVE WS-PATHCALL-AREA(1:WS-PATHCALL-LEN)
    // COB(4238):                           TO LK-IO-AREA(1:WS-PATHCALL-LEN).
    params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsPathcallLen.getInt());
  }

  /***
   ******************************************************************
   *    UPDATE FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1Update() {
    // COB(4251): MOVE 'PAUTDTL1-UPDATE' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-UPDATE");
    // COB(4254): INITIALIZE PAUTDTL1
    //       *
    //       *
    ws.pautdtl1copy.pautdtl1.initialize();
    // COB(4256): IF HAS-PATHCALLS
    if (ws.hasPathcalls()) {
      // COB(4257): PERFORM PAUTDTL1-UPDATE-PATHCALL
      pautdtl1UpdatePathcall();
      // COB(4258): GO TO PAUTDTL1-UPDATE-EX
      return;
      // COB(4259): END-IF
    }
    // COB(4261): MOVE LK-IO-AREA(1:PAUTDTL1-LEN) TO PAUTDTL1(1:PAUTDTL1-LEN)
    ws.pautdtl1copy.pautdtl1.setValue(params.lkIoArea, ws.pautdtl1Len.getInt());
    // COB(4262): IF PA-PROCESSING-CODE-N
    // COB(4262): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paProcessingCodeN.isNumeric()) {
      // COB(4264): MOVE ZERO TO
      // COB(4264): PA-PROCESSING-CODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(0);
      // COB(4266): END-IF
    }
    // COB(4267): IF PA-TRANSACTION-AMT
    // COB(4267): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paTransactionAmt.isNumeric()) {
      // COB(4269): MOVE ZERO TO
      // COB(4269): PA-TRANSACTION-AMT OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paTransactionAmt.setValue(0);
      // COB(4271): END-IF
    }
    // COB(4272): IF PA-APPROVED-AMT
    // COB(4272): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paApprovedAmt.isNumeric()) {
      // COB(4274): MOVE ZERO TO
      // COB(4274): PA-APPROVED-AMT OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paApprovedAmt.setValue(0);
      // COB(4276): END-IF
    }
    // COB(4277): IF PA-POS-ENTRY-MODE-N
    // COB(4277): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paPosEntryModeN.isNumeric()) {
      // COB(4279): MOVE ZERO TO
      // COB(4279): PA-POS-ENTRY-MODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(0);
      // COB(4281): END-IF
    }
    // COB(4282): IF PAUTSUM0-KEY-ACCNTID
    // COB(4282): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.isNumeric()) {
      // COB(4284): MOVE ZERO TO
      // COB(4284): PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(0);
      // COB(4286): END-IF
    }
    // COB(4290): MOVE PA-PROCESSING-CODE-N OF PAUTDTL1 TO
    // COB(4290): WS-PACKED-FLD-18-00(1)
    //       *
    //       * SIGNED ZONED FIELDS VS PACKED FITTING
    //       *
    ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCodeN);
    // COB(4292): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(4292): PA-PROCESSING-CODE OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.paProcessingCode.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(4294): MOVE PA-POS-ENTRY-MODE-N OF PAUTDTL1 TO
    // COB(4294): WS-PACKED-FLD-18-00(1)
    ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryModeN);
    // COB(4296): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(4296): PA-POS-ENTRY-MODE OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.paPosEntryMode.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(4299): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(4300): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(4301): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(4301):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler249
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(4303): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(4304): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(4304):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.filler249.wsPackedFldChrAtIndex(0), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(4306): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(4307): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler173.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(4308): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(4309): END-IF
    }
    // COB(4310): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(4310):                              PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(4312): COMPUTE WS-CK-LEN = 8
    ws.wsCkLen.setValue(8);
    // COB(4313): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(4313):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, ws.wsCkPos.getInt() - 1, 8);
    // COB(4315): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(4316): MOVE IO-RTN-USED-KEY-ALPHA(2)(1:8) TO
    // COB(4316):                                 KEY-PAUT9CTS OF PAUTDTL1(1:8)
    ws.pautdtl1copy.pautdtl1.keyPaut9cts.setValue(
        params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(1)
            .ioRtnUsedSsaKeys
            .ioRtnUsedKeyAlpha,
        8);
    // COB(4320): EXEC SQL
    // COB(4320):   UPDATE DBPAUTP0_PAUTDTL1 SET
    // COB(4320):     PA_AUTH_ORIG_DATE = :PAUTDTL1.PA-AUTH-ORIG-DATE,
    // COB(4320):     PA_AUTH_ORIG_TIME = :PAUTDTL1.PA-AUTH-ORIG-TIME,
    // COB(4320):     PA_CARD_NUM = :PAUTDTL1.PA-CARD-NUM,
    // COB(4320):     PA_AUTH_TYPE = :PAUTDTL1.PA-AUTH-TYPE,
    // COB(4320):     PA_CARD_EXPIRY_DATE = :PAUTDTL1.PA-CARD-EXPIRY-DATE,
    // COB(4320):     PA_MESSAGE_TYPE = :PAUTDTL1.PA-MESSAGE-TYPE,
    // COB(4320):     PA_MESSAGE_SOURCE = :PAUTDTL1.PA-MESSAGE-SOURCE,
    // COB(4320):     PA_AUTH_ID_CODE = :PAUTDTL1.PA-AUTH-ID-CODE,
    // COB(4320):     PA_AUTH_RESP_CODE = :PAUTDTL1.PA-AUTH-RESP-CODE,
    // COB(4320):     PA_AUTH_RESP_REASON = :PAUTDTL1.PA-AUTH-RESP-REASON,
    // COB(4320):     PA_PROCESSING_CODE = :PAUTDTL1.PA-PROCESSING-CODE,
    // COB(4320):     PA_TRANSACTION_AMT = :PAUTDTL1.PA-TRANSACTION-AMT,
    // COB(4320):     PA_APPROVED_AMT = :PAUTDTL1.PA-APPROVED-AMT,
    // COB(4320):     PA_MERCHANT_CATAGORY_CODE =
    // COB(4320):     :PAUTDTL1.PA-MERCHANT-CATAGORY-CODE,
    // COB(4320):     PA_ACQR_COUNTRY_CODE = :PAUTDTL1.PA-ACQR-COUNTRY-CODE,
    // COB(4320):     PA_POS_ENTRY_MODE = :PAUTDTL1.PA-POS-ENTRY-MODE,
    // COB(4320):     PA_MERCHANT_ID = :PAUTDTL1.PA-MERCHANT-ID,
    // COB(4320):     PA_MERCHANT_NAME = :PAUTDTL1.PA-MERCHANT-NAME,
    // COB(4320):     PA_MERCHANT_CITY = :PAUTDTL1.PA-MERCHANT-CITY,
    // COB(4320):     PA_MERCHANT_STATE = :PAUTDTL1.PA-MERCHANT-STATE,
    // COB(4320):     PA_MERCHANT_ZIP = :PAUTDTL1.PA-MERCHANT-ZIP,
    // COB(4320):     PA_TRANSACTION_ID = :PAUTDTL1.PA-TRANSACTION-ID,
    // COB(4320):     PA_MATCH_STATUS = :PAUTDTL1.PA-MATCH-STATUS,
    // COB(4320):     PA_AUTH_FRAUD = :PAUTDTL1.PA-AUTH-FRAUD,
    // COB(4320):     PA_FRAUD_RPT_DATE = :PAUTDTL1.PA-FRAUD-RPT-DATE,
    // COB(4320):     PAUTDTL1_FILLER = :PAUTDTL1.PAUTDTL1-FILLER
    // COB(4320):   WHERE
    // COB(4320):     KEY_PAUT9CTS =
    // COB(4320):   :PAUTDTL1.KEY-PAUT9CTS
    // COB(4320):   AND
    // COB(4320):     PAUTSUM0_KEY_ACCNTID = :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
    // COB(4320): END-EXEC
    //       *
    //       *
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand(
          "UPDATE DBPAUTP0_PAUTDTL1 SET PA_AUTH_ORIG_DATE = ?, PA_AUTH_ORIG_TIME = ?, PA_CARD_NUM ="
              + " ?, PA_AUTH_TYPE = ?, PA_CARD_EXPIRY_DATE = ?, PA_MESSAGE_TYPE = ?,"
              + " PA_MESSAGE_SOURCE = ?, PA_AUTH_ID_CODE = ?, PA_AUTH_RESP_CODE = ?,"
              + " PA_AUTH_RESP_REASON = ?, PA_PROCESSING_CODE = ?, PA_TRANSACTION_AMT = ?,"
              + " PA_APPROVED_AMT = ?, PA_MERCHANT_CATAGORY_CODE = ?, PA_ACQR_COUNTRY_CODE = ?,"
              + " PA_POS_ENTRY_MODE = ?, PA_MERCHANT_ID = ?, PA_MERCHANT_NAME = ?, PA_MERCHANT_CITY"
              + " = ?, PA_MERCHANT_STATE = ?, PA_MERCHANT_ZIP = ?, PA_TRANSACTION_ID = ?,"
              + " PA_MATCH_STATUS = ?, PA_AUTH_FRAUD = ?, PA_FRAUD_RPT_DATE = ?, PAUTDTL1_FILLER ="
              + " ? WHERE KEY_PAUT9CTS = ? AND PAUTSUM0_KEY_ACCNTID = ?");
      sql.set(
          ws.pautdtl1copy.pautdtl1.paAuthOrigDate,
          ws.pautdtl1copy.pautdtl1.paAuthOrigTime,
          ws.pautdtl1copy.pautdtl1.paCardNum,
          ws.pautdtl1copy.pautdtl1.paAuthType,
          ws.pautdtl1copy.pautdtl1.paCardExpiryDate,
          ws.pautdtl1copy.pautdtl1.paMessageType,
          ws.pautdtl1copy.pautdtl1.paMessageSource,
          ws.pautdtl1copy.pautdtl1.paAuthIdCode,
          ws.pautdtl1copy.pautdtl1.paAuthRespCode,
          ws.pautdtl1copy.pautdtl1.paAuthRespReason,
          ws.pautdtl1copy.pautdtl1.paProcessingCode,
          ws.pautdtl1copy.pautdtl1.paTransactionAmt,
          ws.pautdtl1copy.pautdtl1.paApprovedAmt,
          ws.pautdtl1copy.pautdtl1.paMerchantCatagoryCode,
          ws.pautdtl1copy.pautdtl1.paAcqrCountryCode,
          ws.pautdtl1copy.pautdtl1.paPosEntryMode,
          ws.pautdtl1copy.pautdtl1.paMerchantId,
          ws.pautdtl1copy.pautdtl1.paMerchantName,
          ws.pautdtl1copy.pautdtl1.paMerchantCity,
          ws.pautdtl1copy.pautdtl1.paMerchantState,
          ws.pautdtl1copy.pautdtl1.paMerchantZip,
          ws.pautdtl1copy.pautdtl1.paTransactionId,
          ws.pautdtl1copy.pautdtl1.paMatchStatus,
          ws.pautdtl1copy.pautdtl1.paAuthFraud,
          ws.pautdtl1copy.pautdtl1.paFraudRptDate,
          ws.pautdtl1copy.pautdtl1.pautdtl1Filler);
      sql.where(ws.pautdtl1copy.pautdtl1.keyPaut9cts, ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      sql.execute();
    }
    // COB(4358): MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
    // COB(4358): WS-PACKED-FLD-18-00(1)
    //       *
    //       * PACKED FIELDS VS SIGNED ZONED FITTING
    //       *
    ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCode);
    // COB(4360): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(4360): PA-PROCESSING-CODE-N OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(4362): MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
    // COB(4362): WS-PACKED-FLD-18-00(1)
    ws.filler173.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryMode);
    // COB(4364): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(4364): PA-POS-ENTRY-MODE-N OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(4367): IF SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(4368): MOVE LK-IO-AREA(1:PAUTDTL1-LEN)
      // COB(4368):   TO PAUTDTL1-LAST-AREA
      ws.segmentsLastArea.pautdtl1LastArea.setValue(params.lkIoArea, ws.pautdtl1Len.getInt());
      // COB(4370): END-IF
    }
    // COB(4371): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(4372): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    DELETE FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1Delete() {
    // COB(4383): MOVE 'PAUTDTL1-DELETE' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-DELETE");
    // COB(4386): IF HAS-PATHCALLS
    //       *
    //       *
    if (ws.hasPathcalls()) {
      // COB(4387): SET IRIS-ERR-RTN-UNHANDLED-ACCESS TO TRUE
      params.irisWorkArea.setIrisErrRtnUnhandledAccess(true);
      // COB(4388): GO TO PAUTDTL1-DELETE-EX
      return;
      // COB(4389): END-IF
    }
    // COB(4391): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(4392): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(4393): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(4393):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler249
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(4395): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(4396): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(4396):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.filler249.wsPackedFldChrAtIndex(0), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(4398): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(4399): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler173.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(4400): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler173.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(4401): END-IF
    }
    // COB(4402): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(4402):                              PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(ws.filler173.wsPackedFld1800AtIndex(0));
    // COB(4404): COMPUTE WS-CK-LEN = 8
    ws.wsCkLen.setValue(8);
    // COB(4405): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(4405):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, ws.wsCkPos.getInt() - 1, 8);
    // COB(4407): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(4408): MOVE IO-RTN-USED-KEY-ALPHA(2)(1:8) TO
    // COB(4408):                                 KEY-PAUT9CTS OF PAUTDTL1(1:8)
    ws.pautdtl1copy.pautdtl1.keyPaut9cts.setValue(
        params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(1)
            .ioRtnUsedSsaKeys
            .ioRtnUsedKeyAlpha,
        8);
    // COB(4411): EXEC SQL
    // COB(4411):   DELETE FROM DBPAUTP0_PAUTDTL1
    // COB(4411):   WHERE
    // COB(4411):     KEY_PAUT9CTS =
    // COB(4411):   :PAUTDTL1.KEY-PAUT9CTS
    // COB(4411):   AND
    // COB(4411):     PAUTSUM0_KEY_ACCNTID = :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
    // COB(4411): END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand(
          "DELETE FROM DBPAUTP0_PAUTDTL1 WHERE KEY_PAUT9CTS = ? AND PAUTSUM0_KEY_ACCNTID = ?");
      sql.where(ws.pautdtl1copy.pautdtl1.keyPaut9cts, ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      sql.execute();
    }
    // COB(4420): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(4421): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    SET I/O AREA PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1SetIoArea() {
    // COB(4432): MOVE 'PAUTDTL1-SET-IO-AREA' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-SET-IO-AREA");
    // COB(4434): MOVE PAUTDTL1(1:WS-SEGMENT-LEN)
    // COB(4434):                TO LK-IO-AREA(1:WS-SEGMENT-LEN)
    // COB(4434):                   PAUTDTL1-LAST-AREA.
    params.lkIoArea.setValue(ws.pautdtl1copy.pautdtl1, 0, ws.wsSegmentLen.getInt());
    ws.segmentsLastArea.pautdtl1LastArea.setValue(
        ws.pautdtl1copy.pautdtl1, ws.wsSegmentLen.getInt());
  }

  /***
   *
   ******************************************************************
   *    SET DIB BLOCK
   ******************************************************************
   */
  protected void setDibBlock() {
    // COB(4448): MOVE 'SET-DIB-BLOCK' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("SET-DIB-BLOCK");
    // COB(4450): MOVE 'IR'                   TO IRIS-DIBVER
    ws.iriscomm.dlzdib.irisDibver.setString("IR");
    // COB(4451): MOVE DB-PCB-STATUS-CODE     TO IRIS-DIBSTAT
    ws.iriscomm.dlzdib.irisDibstat.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbStatusCode);
    // COB(4452): MOVE DB-PCB-SEGMENT-NAME    TO IRIS-DIBSEGM
    ws.iriscomm.dlzdib.irisDibsegm.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbSegmentName);
    // COB(4453): MOVE DB-PCB-SEGMENT-LEVEL   TO IRIS-DIBSEGLV
    ws.iriscomm.dlzdib.irisDibseglv.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbSegmentLevel);
    // COB(4454): MOVE DB-PCB-FB-KEY-LENGTH   TO IRIS-DIBKFBL
    ws.iriscomm.dlzdib.irisDibkfbl.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbFbKeyLength);
    // COB(4455): MOVE DB-PCB-DBD-NAME        TO IRIS-DIBDBDNM
    ws.iriscomm.dlzdib.irisDibdbdnm.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName);
    // COB(4456): MOVE 'IRIS'                 TO IRIS-DIBDBORG
    ws.iriscomm.dlzdib.irisDibdborg.setString("IRIS");
    // COB(4457): MOVE IRIS-DIB-BLOCK         TO LK-DIB-BLOCK
    params.lkDibBlock.setValue(ws.iriscomm.irisDibBlock);
    // COB(4458): SET ADDRESS OF IRIS-DB-PCB  TO ADDRESS OF LK-DIB-BLOCK.
    params.irisDbPcb.setAddress(params.lkDibBlock.getAddress());
  }
}
