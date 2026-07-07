package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.dbpautx0.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.esql.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;

public class DBPAUTX0 extends AbstractProgram {

  private final Sqlca sqlca = new Sqlca(this);
  private NSqlCursor pautsum0Crs;
  private NSqlCursor pautdtl1Crs;
  private NSqlCursor pautindxCrs;
  @ProgramStorage final Dbpautx0Working ws = new Dbpautx0Working();

  // Linkage
  public class Dbpautx0Linkage extends NGroup {
    // COB:        01 IRIS-WORK-AREA.
    public IrisWorkArea irisWorkArea = new IrisWorkArea();

    public class IrisWorkArea extends NGroup {

      // COB:          03 IRIS-PROGRAM-NAME             PIC X(8)  VALUE :PROGNM:.
      public NChar irisProgramName = new NChar(8).initial("DBPAUTX0");
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
        public class Filler47 extends NGroup {
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
      public class IoRtnUsedKeysPcbArea extends NGroup {
        // COB:          05 IO-RTN-USED-KEYS-AREA.
        public class IoRtnUsedKeysArea extends NGroup {
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
          public NChar filler510 = new NChar(200);

          // COB:            07 IO-RTN-USED-KEY-VALUE OCCURS 10.
          public class IoRtnUsedKeyValue extends NGroup {
            // COB:              09 IO-RTN-USED-SSA-INFO             PIC X(1055).
            public NChar ioRtnUsedSsaInfo = new NChar(1055);

            // COB:              09 IO-RTN-USED-SSA-INPUT-KEYS       REDEFINES
            // COB:                 IO-RTN-USED-SSA-INFO.
            public class IoRtnUsedSsaInputKeys extends NGroup {
              // COB:                11 IO-RTN-IRIS-KEYVALUES          OCCURS 16.
              public class IoRtnIrisKeyvalues extends NGroup {
                // COB:                  13 IO-RTN-IRIS-KEYVALUE         PIC X(80).
                public NChar ioRtnIrisKeyvalue = new NChar(80);
                // COB:                  13 IO-RTN-IRIS-KEYVALUE-PACKED  REDEFINES
                // COB:                     IO-RTN-IRIS-KEYVALUE         PIC S9(18) COMP-3.
                public NPacked ioRtnIrisKeyvaluePacked =
                    new NPacked(10).redefines(ioRtnIrisKeyvalue);
              }

              public IoRtnIrisKeyvalues[] ioRtnIrisKeyvalues =
                  AbstractNField.occurs(16, new IoRtnIrisKeyvalues());

              public IoRtnIrisKeyvalues ioRtnIrisKeyvaluesAtIndex(int index) {
                return getOccursInstance(ioRtnIrisKeyvalues, index);
              }
            }

            public IoRtnUsedSsaInputKeys ioRtnUsedSsaInputKeys =
                (IoRtnUsedSsaInputKeys) new IoRtnUsedSsaInputKeys().redefines(ioRtnUsedSsaInfo);
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
            public class IoRtnUsedSsaKeys extends NGroup {
              // COB:                11 IO-RTN-USED-KEY-ALPHA          PIC X(256).
              public NChar ioRtnUsedKeyAlpha = new NChar(256);
              // COB:                11 IO-RTN-USED-KEY-PACKED REDEFINES IO-RTN-USED-KEY-ALPHA
              public NPacked ioRtnUsedKeyPacked = new NPacked(10).redefines(ioRtnUsedKeyAlpha);
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
              public class Filler537 extends NGroup {
                // COB:                  13 IO-RTN-USED-KEY-LAST-SX      PIC S9(9) COMP.
                public NInt32 ioRtnUsedKeyLastSx = new NInt32();
              }

              public Filler537[] filler537 = AbstractNField.occurs(32, new Filler537());

              public Filler537 filler537AtIndex(int index) {
                return getOccursInstance(filler537, index);
              }

              // COB:                11 IO-RTN-USED-LAST-SEGMENT       PIC X(8).
              public NChar ioRtnUsedLastSegment = new NChar(8);
              // COB:                11 IO-RTN-LAST-OPEN-CURSOR        PIC 9(8).
              public NZoned ioRtnLastOpenCursor = new NZoned(8, false);
              // COB:                11 FILLER                         PIC X(272).
              public NChar filler541 = new NChar(272);
            }

            public IoRtnUsedSsaKeys ioRtnUsedSsaKeys = new IoRtnUsedSsaKeys();
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
        public class Filler544 extends NGroup {
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
          public class LastSsa extends NGroup {
            // COB:              09 LAST-SSA-SEGMENT                 PIC X(8).
            public NChar lastSsaSegment = new NChar(8);
            // COB:              09 LAST-SSA-DATA                    PIC X(248).
            public NChar lastSsaData = new NChar(248);
          }

          public LastSsa lastSsa = new LastSsa();
          // COB:            07 FILLER REDEFINES LAST-SSA          PIC X(256).
          public NChar filler557 = new NChar(256).redefines(lastSsa);

          // COB:              88 LAST-SSA-EMPTY                   VALUE SPACE
          public boolean lastSsaEmpty() {
            return filler557.isSpaces() || filler557.isLowValues() || filler557.isHighValues();
          }

          public void setLastSsaEmpty(boolean value) {
            if (value) filler557.setValue(SPACE);
          }

          // COB:            07 LAST-IMS-CCODE                     PIC X(240).
          public NChar lastImsCcode = new NChar(240);
        }

        public Filler544 filler544 = (Filler544) new Filler544().redefines(ioRtnCommData);
      }

      public IoRtnUsedKeysPcbArea ioRtnUsedKeysPcbArea =
          (IoRtnUsedKeysPcbArea) new IoRtnUsedKeysPcbArea().redefines(dbRntCntlArea);
    }

    // COB:        01 LK-IO-AREA                    PIC X(32000).
    public NChar lkIoArea = new NChar(32000);

    // COB:        01 LK-LOAD-DICTIONARY-AREA REDEFINES LK-IO-AREA.
    public class LkLoadDictionaryArea extends NGroup {
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

    public class LkDualPcb extends NGroup {

      // COB:          03 DUAL-PCB-FIXED-PART.
      public class DualPcbFixedPart extends NGroup {
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

    public class IrisLkCells extends NGroup {

      // COB:          03 FILLER                      OCCURS 100.
      public class Filler603 extends NGroup {
        // COB:            05 IRIS-LK-POINTER           POINTER.
        public NPointer irisLkPointer = new NPointer();
      }

      public Filler603[] filler603 = AbstractNField.occurs(100, new Filler603());

      public Filler603 filler603AtIndex(int index) {
        return getOccursInstance(filler603, index);
      }
    }

    // COB:        01 LK-DIB-BLOCK                  PIC X(32).
    public NChar lkDibBlock = new NChar(32);
    // COB:        01 LK-IO-AREA-BACKUP             PIC X(32000).
    public NChar lkIoAreaBackup = new NChar(32000);
  }

  final Dbpautx0Linkage params = new Dbpautx0Linkage();

  public DBPAUTX0(Context context) {
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
    mainProgram();
    return RETURN_CODE;
  }

  /***/
  protected void mainProgram() {
    // COB(634): IF IRIS-EXECDLI
    if (params.irisWorkArea.irisExecdli()) {
      // COB(635): PERFORM EXTRACT-PCB-EXEC
      extractPcbExec();
      // COB(636): END-IF
    }
    // COB(638): IF IRIS-TRACE-MINIMAL
    // COB(638): OR IRIS-TRACE-STANDARD
    if (params.irisWorkArea.irisTraceMinimal() || params.irisWorkArea.irisTraceStandard()) {
      // COB(640): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(641): STRING 'PGM=(' IRIS-PROGRAM-NAME ')'
      // COB(641):        ' CUSTOM FUNCTION ID=(' IRIS-CUSTOM-FUNC-ID ')'
      // COB(641):        ' CALLER ID=(' IRIS-CALL-ID ')'
      // COB(641): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
      // COB(641): POINTER IRIS-MSG-LEN
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
      // COB(646): MOVE 0 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
      // COB(647): SET IRISTRAC-RTN TO TRUE
      ws.iriscomm.setIristracRtn(true);
      // COB(648): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(649): END-IF
    }
    // COB(651): IF IRIS-TRACE-FULL
    // COB(651): OR IRIS-TRACE-PERFORMANCE
    if (params.irisWorkArea.irisTraceFull() || params.irisWorkArea.irisTracePerformance()) {
      // COB(653): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(654): STRING '<IRISTRACE> - DBPAUTX0:START' NL
      // COB(654):        ' DBD        =(DBPAUTX0) ' NL
      // COB(654):        ' CALLER PGM =(' IRIS-PROGRAM-NAME ') ' NL
      // COB(654):        ' CALLER ID  =(' IRIS-CALL-ID ') ' NL
      // COB(654): DELIMITED BY SIZE INTO IRIS-MSG-TXT
      // COB(654): POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0:START",
              ws.iriscomm.nl,
              " DBD        =(DBPAUTX0) ",
              ws.iriscomm.nl,
              " CALLER PGM =(",
              params.irisWorkArea.irisProgramName,
              ") ",
              ws.iriscomm.nl,
              " CALLER ID  =(",
              params.irisWorkArea.irisCallId,
              ") ",
              ws.iriscomm.nl));
      // COB(660): IF DB-PCB-IRIS-EYE
      if (params.irisDbPcb.dbPcbFixedPart.dbPcbIrisEye()) {
        // COB(661): STRING ' PCB INDEX  =(' RUN-PCB-INDEX ') ' NL
        // COB(661): DELIMITED BY SIZE INTO IRIS-MSG-TXT
        // COB(661): POINTER IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
            params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
                " PCB INDEX  =(",
                params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.runPcbIndex,
                ") ",
                ws.iriscomm.nl));
        // COB(664): END-IF
      }
      // COB(665): STRING ' IMS FUNC   =(' IRIS-IMS-FUNCTION ') ' NL
      // COB(665):        ' SEGMENT    =(' IRIS-SEGMENT ') ' NL
      // COB(665):        ' FUNCTION   =(' IRIS-FUNCTION ') ' NL
      // COB(665):        ' CUSTOM ID  =(' IRIS-CUSTOM-FUNC-ID ') '
      // COB(665): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
      // COB(665): POINTER IRIS-MSG-LEN
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
      // COB(671): MOVE 0 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
      // COB(672): SET IRISTRAC-RTN TO TRUE
      ws.iriscomm.setIristracRtn(true);
      // COB(673): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(674): IF NOT IRIS-EXECDLI
      if (!params.irisWorkArea.irisExecdli()) {
        // COB(675): PERFORM PRINT-SSAS
        printSsas();
        // COB(676): END-IF
      }
      // COB(677): END-IF
    }
    // COB(679): PERFORM INIT-VARIABLES
    initVariables();
    // COB(681): EVALUATE TRUE
    // COB(682): WHEN SQL-USER-CUSTOM
    if (params.irisWorkArea.sqlUserCustom()) {
      // COB(684): IF IRIS-TRACE-PERFORMANCE
      if (params.irisWorkArea.irisTracePerformance()) {
        // COB(685): MOVE 0 TO IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
        // COB(686): STRING '<IRISTRACE> - BEFORE SQL ACCESS'
        // COB(686): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - BEFORE SQL ACCESS", ws.iriscomm.messageEnd);
        // COB(688): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(689): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(690): END-IF
      }
      // COB(691): EVALUATE IRIS-CUSTOM-FUNC-ID
      switch (params.irisWorkArea.irisCustomFuncId.getInt()) {
          // COB(692): WHEN 0
        case 0:
          // COB(693): SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
          params.irisWorkArea.setIrisErrFunctionNotFound(true);
          // COB(694): WHEN OTHER
          break;
        default:
          // COB(695): SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
          params.irisWorkArea.setIrisErrFunctionNotFound(true);
          // COB(696): END-EVALUATE
      }
      // COB(697): END-EVALUATE.
    }
    skipFunction();
  }

  /***/
  protected void skipFunction() {
    // COB(701): IF IRIS-NO-ERROR
    // COB(701): AND NOT SKIP-SEGMENT-ACCESS
    if (params.irisWorkArea.irisNoError() && !ws.skipSegmentAccess()) {
      // COB(703): EVALUATE WS-SEGMENT-NAME
      // COB(704): WHEN 'PAUTSUM0'
      if (ws.wsSegmentName.getValue().equals("PAUTSUM0")) {
        // COB(706): PERFORM HANDLE-SEGMENT-PAUTSUM0
        handleSegmentPautsum0();
        // COB(708): WHEN 'PAUTDTL1'
      } else if (ws.wsSegmentName.getValue().equals("PAUTDTL1")) {
        // COB(710): PERFORM HANDLE-SEGMENT-PAUTDTL1
        handleSegmentPautdtl1();
        // COB(712): WHEN 'PAUTINDX'
      } else if (ws.wsSegmentName.getValue().equals("PAUTINDX")) {
        // COB(714): PERFORM HANDLE-SEGMENT-PAUTINDX
        handleSegmentPautindx();
        // COB(716): WHEN OTHER
      } else {
        // COB(717): SET IRIS-ERR-RTN-SEGMENT-NOT-FOUND TO TRUE
        params.irisWorkArea.setIrisErrRtnSegmentNotFound(true);
        // COB(718): END-EVALUATE
      }
      // COB(720): END-IF
    }
    // COB(721): IF IRIS-TRACE-PERFORMANCE
    if (params.irisWorkArea.irisTracePerformance()) {
      // COB(722): MOVE 0 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
      // COB(723): STRING '<IRISTRACE> - AFTER  SQL ACCESS'
      // COB(723): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
          "<IRISTRACE> - AFTER  SQL ACCESS", ws.iriscomm.messageEnd);
      // COB(725): SET IRISTRAC-RTN TO TRUE
      ws.iriscomm.setIristracRtn(true);
      // COB(726): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(727): END-IF
    }
    // COB(729): PERFORM FINALIZE-VARIABLES
    finalizeVariables();
    // COB(731): IF IRIS-TRACE-STANDARD
    if (params.irisWorkArea.irisTraceStandard()) {
      // COB(732): MOVE 80 TO WS-LEN
      ws.wsLen.setValue(80);
      // COB(733): IF WS-SEGMENT-LEN < WS-LEN
      if (ws.wsSegmentLen.lessThan(ws.wsLen)) {
        // COB(734): MOVE WS-SEGMENT-LEN TO WS-LEN
        ws.wsLen.setValue(ws.wsSegmentLen);
        // COB(735): ELSE
      } else {
        // COB(736): IF IRIS-TRACE-FULL
        if (params.irisWorkArea.irisTraceFull()) {
          // COB(737): MOVE WS-SEGMENT-LEN TO WS-LEN
          ws.wsLen.setValue(ws.wsSegmentLen);
          // COB(738): END-IF
        }
        // COB(739): END-IF
      }
      // COB(740): IF WS-LEN = ZERO
      if (ws.wsLen.equals(0)) {
        // COB(741): MOVE 1 TO WS-LEN
        ws.wsLen.setValue(1);
        // COB(742): END-IF
      }
      // COB(743): MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
      ws.irissqlc.irisDbSqlcode.setValue(params.irisWorkArea.irisSqlError.irisSqlcode);
      // COB(744): IF NOT IRIS-SQL-OK AND NOT IRIS-SQL-NOT-FOUND
      if (!ws.irissqlc.irisSqlOk() && !ws.irissqlc.irisSqlNotFound()) {
        // COB(745): IF IRIS-SQLERRML = ZERO
        if (params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrml.equals(0)) {
          // COB(746): MOVE 1 TO IRIS-SQLERRML
          params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrml.setValue(1);
          // COB(747): END-IF
        }
        // COB(748): MOVE 0 TO IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
        // COB(749): MOVE IRIS-SQLCODE TO WS-SQLCODE-N
        ws.wsSqlcodeN.setValue(params.irisWorkArea.irisSqlError.irisSqlcode);
        // COB(750): MOVE WS-SQLCODE-N TO WS-SQLCODE-E
        ws.wsSqlcodeE.setValue(ws.wsSqlcodeN);
        // COB(751): STRING '<IRISTRACE> - DBPAUTX0:SQL_RC' NL
        // COB(751):      ' SQLCODE    =(' WS-SQLCODE-E ')' NL
        // COB(751):      ' SQLERRM    =(' IRIS-SQLERRMC(1:IRIS-SQLERRML) ')'
        // COB(751): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - DBPAUTX0:SQL_RC",
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
        // COB(755): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(756): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(757): END-IF
      }
      // COB(758): MOVE 0 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
      // COB(759): IF IRIS-NO-ERROR AND IRIS-TRACE-FULL
      if (params.irisWorkArea.irisNoError() && params.irisWorkArea.irisTraceFull()) {
        // COB(760): STRING '<IRISTRACE> - DBPAUTX0:END' NL
        // COB(760):        ' DBD        =(DBPAUTX0) ' NL
        // COB(760):        ' CALLER PGM =(' IRIS-PROGRAM-NAME ') ' NL
        // COB(760):        ' CALLER ID  =(' IRIS-CALL-ID ') ' NL
        // COB(760):        ' PCB INDEX  =(' RUN-PCB-INDEX ') ' NL
        // COB(760):        ' IMS FUNC   =(' IRIS-IMS-FUNCTION ') ' NL
        // COB(760):        ' CUSTOM ID  =(' IRIS-CUSTOM-FUNC-ID ') ' NL
        // COB(760):        ' SEGMENT    =(' WS-SEGMENT-NAME ') ' NL
        // COB(760):        ' SECTION    =(' WS-LAST-IORTN-SECTION ') ' NL
        // COB(760):        ' PCB AREA   =(' DB-PCB-FIXED-PART ') ' NL
        // COB(760):        ' KFB AREA   =(' DB-PCB-KEY-FB
        // COB(760):                     (1:DB-PCB-FB-KEY-LENGTH) ') ' NL
        // COB(760):        ' I-O AREA   =(' LK-IO-AREA(1:WS-LEN)') '
        // COB(760): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - DBPAUTX0:END",
            ws.iriscomm.nl,
            " DBD        =(DBPAUTX0) ",
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
        // COB(774): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(775): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(776): ELSE
      } else {
        // COB(777): IF NOT IRIS-NO-ERROR
        if (!params.irisWorkArea.irisNoError()) {
          // COB(778): MOVE 1 TO WS-ERROR-DESCRIPTION-LEN
          ws.wsErrorDescriptionLen.setValue(1);
          // COB(779): STRING IRIS-ERROR-DESCRIPTION(IRIS-ERR-MESSAGE-ID)
          // COB(779): DELIMITED BY '_'
          // COB(779): INTO WS-ERROR-DESCRIPTION
          // COB(779): POINTER WS-ERROR-DESCRIPTION-LEN
          ws.wsErrorDescriptionLen.setValue(
              ws.wsErrorDescription.concat(
                  params
                      .irisWorkArea
                      .filler179
                      .irisErrorDescriptionAtIndex(
                          params.irisWorkArea.irisErrMessageId.getInt() - 1)
                      .substringToValue("_")));
          // COB(783): SUBTRACT 1 FROM WS-ERROR-DESCRIPTION-LEN
          ws.wsErrorDescriptionLen.subtract(1);
          // COB(784): MOVE IRIS-ERR-MESSAGE-ID TO WS-MESSAGE-ID-EDITED
          ws.wsMessageIdEdited.setValue(params.irisWorkArea.irisErrMessageId);
          // COB(785): STRING '<IRISTRACE> - DBPAUTX0:END' NL
          // COB(785):        ' DBD        =(DBPAUTX0) ' NL
          // COB(785):        ' CALLER PGM =(' IRIS-PROGRAM-NAME ') ' NL
          // COB(785):        ' CALLER ID  =(' IRIS-CALL-ID ') ' NL
          // COB(785):        ' PCB INDEX  =(' RUN-PCB-INDEX ') ' NL
          // COB(785):        ' IMS FUNC   =(' IRIS-IMS-FUNCTION ') ' NL
          // COB(785):        ' CUSTOM ID  =(' IRIS-CUSTOM-FUNC-ID ') ' NL
          // COB(785):        ' SEGMENT    =(' WS-SEGMENT-NAME ') ' NL
          // COB(785):        ' SECTION    =(' WS-LAST-IORTN-SECTION ') ' NL
          // COB(785):        ' ERROR ID   =(' WS-MESSAGE-ID-EDITED ') ' NL
          // COB(785):        ' ERROR DESCR=('
          // COB(785): WS-ERROR-DESCRIPTION(1:WS-ERROR-DESCRIPTION-LEN)') ' NL
          // COB(785):        ' PCB AREA   =(' DB-PCB-FIXED-PART ') ' NL
          // COB(785):        ' I-O AREA   =(' LK-IO-AREA(1:WS-LEN) ') '
          // COB(785): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0:END",
              ws.iriscomm.nl,
              " DBD        =(DBPAUTX0) ",
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
          // COB(800): SET IRISTRAC-RTN TO TRUE
          ws.iriscomm.setIristracRtn(true);
          // COB(801): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
          context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
          // COB(802): END-IF
        }
        // COB(803): END-IF
      }
      // COB(804): ELSE
    } else {
      // COB(806): IF DB-STATUS-INTERNAL-NOT-HANDLED
      // COB(806): OR IRIS-ERR-UNHANDLED-SQLCODE
      //       *    IN CASE OF INTERNAL ERROR EMIT ALWAYS A MESSAGE TO CONSOLE
      if (params.irisDbPcb.dbPcbFixedPart.dbStatusInternalNotHandled()
          || params.irisWorkArea.irisErrUnhandledSqlcode()) {
        // COB(808): IF IRIS-SQLERRML = ZERO
        if (params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrml.equals(0)) {
          // COB(809): MOVE 1 TO IRIS-SQLERRML
          params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrml.setValue(1);
          // COB(810): END-IF
        }
        // COB(811): MOVE 1 TO WS-ERROR-DESCRIPTION-LEN
        ws.wsErrorDescriptionLen.setValue(1);
        // COB(812): STRING IRIS-ERROR-DESCRIPTION(IRIS-ERR-MESSAGE-ID)
        // COB(812): DELIMITED BY '_'
        // COB(812): INTO WS-ERROR-DESCRIPTION
        // COB(812): POINTER WS-ERROR-DESCRIPTION-LEN
        ws.wsErrorDescriptionLen.setValue(
            ws.wsErrorDescription.concat(
                params
                    .irisWorkArea
                    .filler179
                    .irisErrorDescriptionAtIndex(params.irisWorkArea.irisErrMessageId.getInt() - 1)
                    .substringToValue("_")));
        // COB(816): SUBTRACT 1 FROM WS-ERROR-DESCRIPTION-LEN
        ws.wsErrorDescriptionLen.subtract(1);
        // COB(817): MOVE 0 TO IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
        // COB(818): MOVE IRIS-SQLCODE TO WS-SQLCODE-N
        ws.wsSqlcodeN.setValue(params.irisWorkArea.irisSqlError.irisSqlcode);
        // COB(819): MOVE WS-SQLCODE-N TO WS-SQLCODE-E
        ws.wsSqlcodeE.setValue(ws.wsSqlcodeN);
        // COB(820): MOVE IRIS-ERR-MESSAGE-ID TO WS-MESSAGE-ID-EDITED
        ws.wsMessageIdEdited.setValue(params.irisWorkArea.irisErrMessageId);
        // COB(821): STRING '<IRISTRACE> - DBPAUTX0:END' NL
        // COB(821):        ' DBD        =(DBPAUTX0) ' NL
        // COB(821):        ' CALLER PGM =(' IRIS-PROGRAM-NAME ') ' NL
        // COB(821):        ' CALLER ID  =(' IRIS-CALL-ID ') ' NL
        // COB(821):        ' PCB INDEX  =(' RUN-PCB-INDEX ') ' NL
        // COB(821):        ' IMS FUNC   =(' IRIS-IMS-FUNCTION ') ' NL
        // COB(821):        ' CUSTOM ID  =(' IRIS-CUSTOM-FUNC-ID ') ' NL
        // COB(821):        ' SEGMENT    =(' WS-SEGMENT-NAME ') ' NL
        // COB(821):        ' SECTION    =(' WS-LAST-IORTN-SECTION ') ' NL
        // COB(821):        ' ERROR ID   =(' WS-MESSAGE-ID-EDITED ') ' NL
        // COB(821):        ' ERROR DESCR=('
        // COB(821): WS-ERROR-DESCRIPTION(1:WS-ERROR-DESCRIPTION-LEN) ') ' NL
        // COB(821):      ' SQLCODE    =(' WS-SQLCODE-E ') ' NL
        // COB(821):      ' SQLERRM    =(' IRIS-SQLERRMC(1:IRIS-SQLERRML)') '
        // COB(821): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - DBPAUTX0:END",
            ws.iriscomm.nl,
            " DBD        =(DBPAUTX0) ",
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
        // COB(836): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(837): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(838): END-IF
      }
      // COB(839): END-IF
    }
    // COB(840): MOVE IRIS-IMS-FUNCTION TO LAST-IMS-FUNCTION
    params.irisDbPcb.ioRtnUsedKeysPcbArea.filler544.lastImsFunction.setValue(
        params.irisWorkArea.irisImsFunction);
    // COB(841): MOVE WS-LAST-SEGMENT-NAME TO LAST-IMS-SEGMENT-NAME
    params.irisDbPcb.ioRtnUsedKeysPcbArea.filler544.lastImsSegmentName.setValue(
        ws.wsLastSegmentName);
    // COB(842): MOVE WS-LAST-SEGMENT-LEVEL TO LAST-IMS-SEGMENT-LEVEL
    params.irisDbPcb.ioRtnUsedKeysPcbArea.filler544.lastImsSegmentLevel.setValue(
        ws.wsLastSegmentLevel);
    // COB(843): MOVE IRIS-KEYVALUE-TAB(1:240) TO LAST-IMS-CCODE
    params.irisDbPcb.ioRtnUsedKeysPcbArea.filler544.lastImsCcode.setValue(
        params.irisWorkArea.irisKeyvalueTab, 240);
    // COB(845): IF IRIS-EXECDLI
    if (params.irisWorkArea.irisExecdli()) {
      // COB(846): PERFORM SET-DIB-BLOCK
      setDibBlock();
      // COB(847): END-IF
    }
    mainProgramEx();
  }

  /***/
  protected void mainProgramEx() {
    // COB(853): GOBACK.
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
    // COB(867): MOVE 'PRINT-SSAS' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PRINT-SSAS");
    // COB(869): IF IRIS-PARAM-NUM > 3
    if (params.irisWorkArea.irisParamNum.greaterThan(3)) {
      // COB(870): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(871): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(872): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(872): OR LK-SSA-01(WS-IDX:1) = '('
      // COB(872): OR LK-SSA-01(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa01.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa01.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(875): CONTINUE
        // do nothing
        // COB(876): END-PERFORM
      }
      // COB(877): IF LK-SSA-01(WS-IDX:1) = '('
      if (params.lkSsa01.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(878): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(878): UNTIL WS-IDX > 540
        // COB(878): OR LK-SSA-01(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa01.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(881): CONTINUE
          // do nothing
          // COB(882): END-PERFORM
        }
        // COB(883): END-IF
      }
      // COB(884): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(884):                       ':SSA01=(' LK-SSA-01(1:WS-IDX) ')'
      // COB(884): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA01=(",
              params.lkSsa01.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(887): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(888): END-IF
    }
    // COB(890): IF IRIS-PARAM-NUM > 4
    if (params.irisWorkArea.irisParamNum.greaterThan(4)) {
      // COB(891): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(892): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(893): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(893): OR LK-SSA-02(WS-IDX:1) = '('
      // COB(893): OR LK-SSA-02(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa02.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa02.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(896): CONTINUE
        // do nothing
        // COB(897): END-PERFORM
      }
      // COB(898): IF LK-SSA-02(WS-IDX:1) = '('
      if (params.lkSsa02.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(899): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(899): UNTIL WS-IDX > 540
        // COB(899): OR LK-SSA-02(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa02.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(902): CONTINUE
          // do nothing
          // COB(903): END-PERFORM
        }
        // COB(904): END-IF
      }
      // COB(905): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(905):                       ':SSA02=(' LK-SSA-02(1:WS-IDX) ')'
      // COB(905): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA02=(",
              params.lkSsa02.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(908): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(909): END-IF
    }
    // COB(911): IF IRIS-PARAM-NUM > 5
    if (params.irisWorkArea.irisParamNum.greaterThan(5)) {
      // COB(912): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(913): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(914): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(914): OR LK-SSA-03(WS-IDX:1) = '('
      // COB(914): OR LK-SSA-03(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa03.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa03.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(917): CONTINUE
        // do nothing
        // COB(918): END-PERFORM
      }
      // COB(919): IF LK-SSA-03(WS-IDX:1) = '('
      if (params.lkSsa03.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(920): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(920): UNTIL WS-IDX > 540
        // COB(920): OR LK-SSA-03(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa03.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(923): CONTINUE
          // do nothing
          // COB(924): END-PERFORM
        }
        // COB(925): END-IF
      }
      // COB(926): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(926):                       ':SSA03=(' LK-SSA-03(1:WS-IDX) ')'
      // COB(926): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA03=(",
              params.lkSsa03.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(929): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(930): END-IF
    }
    // COB(932): IF IRIS-PARAM-NUM > 6
    if (params.irisWorkArea.irisParamNum.greaterThan(6)) {
      // COB(933): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(934): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(935): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(935): OR LK-SSA-04(WS-IDX:1) = '('
      // COB(935): OR LK-SSA-04(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa04.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa04.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(938): CONTINUE
        // do nothing
        // COB(939): END-PERFORM
      }
      // COB(940): IF LK-SSA-04(WS-IDX:1) = '('
      if (params.lkSsa04.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(941): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(941): UNTIL WS-IDX > 540
        // COB(941): OR LK-SSA-04(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa04.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(944): CONTINUE
          // do nothing
          // COB(945): END-PERFORM
        }
        // COB(946): END-IF
      }
      // COB(947): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(947):                       ':SSA04=(' LK-SSA-04(1:WS-IDX) ')'
      // COB(947): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA04=(",
              params.lkSsa04.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(950): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(951): END-IF
    }
    // COB(953): IF IRIS-PARAM-NUM > 7
    if (params.irisWorkArea.irisParamNum.greaterThan(7)) {
      // COB(954): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(955): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(956): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(956): OR LK-SSA-05(WS-IDX:1) = '('
      // COB(956): OR LK-SSA-05(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa05.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa05.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(959): CONTINUE
        // do nothing
        // COB(960): END-PERFORM
      }
      // COB(961): IF LK-SSA-05(WS-IDX:1) = '('
      if (params.lkSsa05.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(962): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(962): UNTIL WS-IDX > 540
        // COB(962): OR LK-SSA-05(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa05.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(965): CONTINUE
          // do nothing
          // COB(966): END-PERFORM
        }
        // COB(967): END-IF
      }
      // COB(968): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(968):                       ':SSA05=(' LK-SSA-05(1:WS-IDX) ')'
      // COB(968): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA05=(",
              params.lkSsa05.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(971): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(972): END-IF
    }
    // COB(974): IF IRIS-PARAM-NUM > 8
    if (params.irisWorkArea.irisParamNum.greaterThan(8)) {
      // COB(975): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(976): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(977): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(977): OR LK-SSA-06(WS-IDX:1) = '('
      // COB(977): OR LK-SSA-06(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa06.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa06.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(980): CONTINUE
        // do nothing
        // COB(981): END-PERFORM
      }
      // COB(982): IF LK-SSA-06(WS-IDX:1) = '('
      if (params.lkSsa06.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(983): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(983): UNTIL WS-IDX > 540
        // COB(983): OR LK-SSA-06(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa06.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(986): CONTINUE
          // do nothing
          // COB(987): END-PERFORM
        }
        // COB(988): END-IF
      }
      // COB(989): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(989):                       ':SSA06=(' LK-SSA-06(1:WS-IDX) ')'
      // COB(989): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA06=(",
              params.lkSsa06.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(992): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(993): END-IF
    }
    // COB(995): IF IRIS-PARAM-NUM > 9
    if (params.irisWorkArea.irisParamNum.greaterThan(9)) {
      // COB(996): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(997): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(998): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(998): OR LK-SSA-07(WS-IDX:1) = '('
      // COB(998): OR LK-SSA-07(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa07.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa07.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1001): CONTINUE
        // do nothing
        // COB(1002): END-PERFORM
      }
      // COB(1003): IF LK-SSA-07(WS-IDX:1) = '('
      if (params.lkSsa07.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1004): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1004): UNTIL WS-IDX > 540
        // COB(1004): OR LK-SSA-07(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa07.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1007): CONTINUE
          // do nothing
          // COB(1008): END-PERFORM
        }
        // COB(1009): END-IF
      }
      // COB(1010): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(1010):                       ':SSA07=(' LK-SSA-07(1:WS-IDX) ')'
      // COB(1010): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA07=(",
              params.lkSsa07.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1013): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1014): END-IF
    }
    // COB(1016): IF IRIS-PARAM-NUM > 10
    if (params.irisWorkArea.irisParamNum.greaterThan(10)) {
      // COB(1017): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1018): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1019): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1019): OR LK-SSA-08(WS-IDX:1) = '('
      // COB(1019): OR LK-SSA-08(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa08.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa08.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1022): CONTINUE
        // do nothing
        // COB(1023): END-PERFORM
      }
      // COB(1024): IF LK-SSA-08(WS-IDX:1) = '('
      if (params.lkSsa08.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1025): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1025): UNTIL WS-IDX > 540
        // COB(1025): OR LK-SSA-08(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa08.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1028): CONTINUE
          // do nothing
          // COB(1029): END-PERFORM
        }
        // COB(1030): END-IF
      }
      // COB(1031): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(1031):                       ':SSA08=(' LK-SSA-08(1:WS-IDX) ')'
      // COB(1031): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA08=(",
              params.lkSsa08.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1034): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1035): END-IF
    }
    // COB(1037): IF IRIS-PARAM-NUM > 11
    if (params.irisWorkArea.irisParamNum.greaterThan(11)) {
      // COB(1038): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1039): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1040): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1040): OR LK-SSA-09(WS-IDX:1) = '('
      // COB(1040): OR LK-SSA-09(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa09.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa09.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1043): CONTINUE
        // do nothing
        // COB(1044): END-PERFORM
      }
      // COB(1045): IF LK-SSA-09(WS-IDX:1) = '('
      if (params.lkSsa09.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1046): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1046): UNTIL WS-IDX > 540
        // COB(1046): OR LK-SSA-09(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa09.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1049): CONTINUE
          // do nothing
          // COB(1050): END-PERFORM
        }
        // COB(1051): END-IF
      }
      // COB(1052): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(1052):                       ':SSA09=(' LK-SSA-09(1:WS-IDX) ')'
      // COB(1052): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA09=(",
              params.lkSsa09.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1055): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1056): END-IF
    }
    // COB(1058): IF IRIS-PARAM-NUM > 12
    if (params.irisWorkArea.irisParamNum.greaterThan(12)) {
      // COB(1059): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1060): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1061): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1061): OR LK-SSA-10(WS-IDX:1) = '('
      // COB(1061): OR LK-SSA-10(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa10.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa10.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1064): CONTINUE
        // do nothing
        // COB(1065): END-PERFORM
      }
      // COB(1066): IF LK-SSA-10(WS-IDX:1) = '('
      if (params.lkSsa10.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1067): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1067): UNTIL WS-IDX > 540
        // COB(1067): OR LK-SSA-10(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa10.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1070): CONTINUE
          // do nothing
          // COB(1071): END-PERFORM
        }
        // COB(1072): END-IF
      }
      // COB(1073): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(1073):                       ':SSA10=(' LK-SSA-10(1:WS-IDX) ')'
      // COB(1073): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA10=(",
              params.lkSsa10.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1076): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1077): END-IF
    }
    // COB(1079): IF IRIS-PARAM-NUM > 13
    if (params.irisWorkArea.irisParamNum.greaterThan(13)) {
      // COB(1080): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1081): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1082): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1082): OR LK-SSA-11(WS-IDX:1) = '('
      // COB(1082): OR LK-SSA-11(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa11.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa11.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1085): CONTINUE
        // do nothing
        // COB(1086): END-PERFORM
      }
      // COB(1087): IF LK-SSA-11(WS-IDX:1) = '('
      if (params.lkSsa11.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1088): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1088): UNTIL WS-IDX > 540
        // COB(1088): OR LK-SSA-11(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa11.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1091): CONTINUE
          // do nothing
          // COB(1092): END-PERFORM
        }
        // COB(1093): END-IF
      }
      // COB(1094): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(1094):                       ':SSA11=(' LK-SSA-11(1:WS-IDX) ')'
      // COB(1094): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA11=(",
              params.lkSsa11.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1097): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1098): END-IF
    }
    // COB(1100): IF IRIS-PARAM-NUM > 14
    if (params.irisWorkArea.irisParamNum.greaterThan(14)) {
      // COB(1101): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1102): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1103): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1103): OR LK-SSA-12(WS-IDX:1) = '('
      // COB(1103): OR LK-SSA-12(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa12.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa12.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1106): CONTINUE
        // do nothing
        // COB(1107): END-PERFORM
      }
      // COB(1108): IF LK-SSA-12(WS-IDX:1) = '('
      if (params.lkSsa12.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1109): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1109): UNTIL WS-IDX > 540
        // COB(1109): OR LK-SSA-12(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa12.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1112): CONTINUE
          // do nothing
          // COB(1113): END-PERFORM
        }
        // COB(1114): END-IF
      }
      // COB(1115): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(1115):                       ':SSA12=(' LK-SSA-12(1:WS-IDX) ')'
      // COB(1115): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA12=(",
              params.lkSsa12.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1118): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1119): END-IF
    }
    // COB(1121): IF IRIS-PARAM-NUM > 15
    if (params.irisWorkArea.irisParamNum.greaterThan(15)) {
      // COB(1122): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1123): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1124): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1124): OR LK-SSA-13(WS-IDX:1) = '('
      // COB(1124): OR LK-SSA-13(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa13.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa13.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1127): CONTINUE
        // do nothing
        // COB(1128): END-PERFORM
      }
      // COB(1129): IF LK-SSA-13(WS-IDX:1) = '('
      if (params.lkSsa13.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1130): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1130): UNTIL WS-IDX > 540
        // COB(1130): OR LK-SSA-13(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa13.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1133): CONTINUE
          // do nothing
          // COB(1134): END-PERFORM
        }
        // COB(1135): END-IF
      }
      // COB(1136): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(1136):                       ':SSA13=(' LK-SSA-13(1:WS-IDX) ')'
      // COB(1136): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA13=(",
              params.lkSsa13.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1139): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1140): END-IF
    }
    // COB(1142): IF IRIS-PARAM-NUM > 16
    if (params.irisWorkArea.irisParamNum.greaterThan(16)) {
      // COB(1143): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1144): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1145): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1145): OR LK-SSA-14(WS-IDX:1) = '('
      // COB(1145): OR LK-SSA-14(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa14.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa14.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1148): CONTINUE
        // do nothing
        // COB(1149): END-PERFORM
      }
      // COB(1150): IF LK-SSA-14(WS-IDX:1) = '('
      if (params.lkSsa14.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1151): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1151): UNTIL WS-IDX > 540
        // COB(1151): OR LK-SSA-14(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa14.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1154): CONTINUE
          // do nothing
          // COB(1155): END-PERFORM
        }
        // COB(1156): END-IF
      }
      // COB(1157): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(1157):                       ':SSA14=(' LK-SSA-14(1:WS-IDX) ')'
      // COB(1157): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA14=(",
              params.lkSsa14.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1160): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1161): END-IF
    }
    // COB(1163): IF IRIS-PARAM-NUM > 17
    if (params.irisWorkArea.irisParamNum.greaterThan(17)) {
      // COB(1164): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1165): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1166): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1166): OR LK-SSA-15(WS-IDX:1) = '('
      // COB(1166): OR LK-SSA-15(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa15.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa15.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1169): CONTINUE
        // do nothing
        // COB(1170): END-PERFORM
      }
      // COB(1171): IF LK-SSA-15(WS-IDX:1) = '('
      if (params.lkSsa15.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1172): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1172): UNTIL WS-IDX > 540
        // COB(1172): OR LK-SSA-15(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa15.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1175): CONTINUE
          // do nothing
          // COB(1176): END-PERFORM
        }
        // COB(1177): END-IF
      }
      // COB(1178): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(1178):                       ':SSA15=(' LK-SSA-15(1:WS-IDX) ')'
      // COB(1178): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA15=(",
              params.lkSsa15.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1181): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1182): END-IF
    }
    // COB(1184): IF IRIS-PARAM-NUM > 18
    if (params.irisWorkArea.irisParamNum.greaterThan(18)) {
      // COB(1185): MOVE 1 TO IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(1);
      // COB(1186): MOVE SPACE TO IRIS-MSG-TXT
      params.irisWorkArea.irisMsgDetail.irisMsgTxt.spaces();
      // COB(1187): PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
      // COB(1187): OR LK-SSA-16(WS-IDX:1) = '('
      // COB(1187): OR LK-SSA-16(WS-IDX:1) = ' '
      ws.wsIdx.setValue(9);
      for (;
          !(ws.wsIdx.greaterThan(540)
              || params.lkSsa16.getString(ws.wsIdx.getInt() - 1, 1).equals("(")
              || params.lkSsa16.getString(ws.wsIdx.getInt() - 1, 1).equals(" "));
          ws.wsIdx.add(1)) {
        // COB(1190): CONTINUE
        // do nothing
        // COB(1191): END-PERFORM
      }
      // COB(1192): IF LK-SSA-16(WS-IDX:1) = '('
      if (params.lkSsa16.getString(ws.wsIdx.getInt() - 1, 1).equals("(")) {
        // COB(1193): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
        // COB(1193): UNTIL WS-IDX > 540
        // COB(1193): OR LK-SSA-16(WS-IDX:1) = ')'
        ws.wsIdx.setValue(ws.wsIdx);
        for (;
            !(ws.wsIdx.greaterThan(540)
                || params.lkSsa16.getString(ws.wsIdx.getInt() - 1, 1).equals(")"));
            ws.wsIdx.add(1)) {
          // COB(1196): CONTINUE
          // do nothing
          // COB(1197): END-PERFORM
        }
        // COB(1198): END-IF
      }
      // COB(1199): STRING '<IRISTRACE> - DBPAUTX0'
      // COB(1199):                       ':SSA16=(' LK-SSA-16(1:WS-IDX) ')'
      // COB(1199): DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
      params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(
          params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
              "<IRISTRACE> - DBPAUTX0",
              ":SSA16=(",
              params.lkSsa16.getString(0, ws.wsIdx.getInt()),
              ")"));
      // COB(1202): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
      // COB(1203): END-IF
    }
  }

  /***
   ******************************************************************
   *    EXTRACT PCB WHEN EXEC DLI CASE
   ******************************************************************
   */
  protected void extractPcbExec() {
    // COB(1217): MOVE 'EXTRACT-PCB-EXEC' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("EXTRACT-PCB-EXEC");
    // COB(1219): SET WS-NOT-FOUND TO TRUE
    ws.setWsNotFound(true);
    // COB(1220): SET ADDRESS OF IRIS-LK-CELLS TO IRIS-EXEC-DLI-PTR
    params.irisLkCells.setAddress(params.irisWorkArea.irisExecDliPtr);
    // COB(1221): SET ADDRESS OF LK-DIB-BLOCK TO ADDRESS OF IRIS-DB-PCB
    params.lkDibBlock.setAddress(params.irisDbPcb.getAddress());
    // COB(1222): MOVE LK-DIB-BLOCK TO IRIS-DIB-BLOCK
    ws.iriscomm.irisDibBlock.setValue(params.lkDibBlock);
    // COB(1223): CHECK      SET ADDRESS OF IRIS-DB-PCB TO
    // COB(1223): CHECK *         IRIS-LK-POINTER(IRIS-PCB-NUM + 1)
    // COB(1223): CHECK           IRIS-LK-POINTER(IRIS-PCB-NUM)
    params.irisDbPcb.setAddress(
        params.irisLkCells.filler603AtIndex(params.irisWorkArea.irisPcbNum.getInt() - 1)
            .irisLkPointer);
  }

  /***
   ******************************************************************
   *    INITIALIZE VARIABLES
   ******************************************************************
   */
  protected void initVariables() {
    // COB(1239): MOVE 'INIT-VARIABLES' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("INIT-VARIABLES");
    // COB(1241): MOVE IRIS-SEGMENT TO WS-SEGMENT-NAME
    ws.wsSegmentName.setValue(params.irisWorkArea.irisSegment);
    // COB(1242): IF IRIS-FUNC-REPL AND NOT IRIS-EXECDLI
    if (params.irisWorkArea.irisFuncRepl() && !params.irisWorkArea.irisExecdli()) {
      // COB(1243): MOVE LAST-IMS-CCODE TO IRIS-KEYVALUE-TAB(1:240)
      params.irisWorkArea.irisKeyvalueTab.setValue(
          params.irisDbPcb.ioRtnUsedKeysPcbArea.filler544.lastImsCcode, 240);
      // COB(1244): END-IF
    }
    // COB(1245): SET DB-STATUS-OK TO TRUE
    params.irisDbPcb.dbPcbFixedPart.setDbStatusOk(true);
    // COB(1246): SET IRIS-SQL-OK TO TRUE
    ws.irissqlc.setIrisSqlOk(true);
    // COB(1247): SET IRIS-NO-ERROR TO TRUE
    params.irisWorkArea.setIrisNoError(true);
    // COB(1248): SET COMMAND-WITHOUT-HOLD TO TRUE
    ws.setCommandWithoutHold(true);
    // COB(1249): SET HAS-NOT-PATHCALLS TO TRUE
    ws.setHasNotPathcalls(true);
    // COB(1250): SET HAS-PATHCALLS-NO-ERROR TO TRUE
    ws.setHasPathcallsNoError(true);
    // COB(1251): SET IS-NOT-PATHCALL-REVERSE TO TRUE
    ws.setIsNotPathcallReverse(true);
    // COB(1252): SET EXEC-GE-PATH-CALL TO TRUE
    params.irisWorkArea.setExecGePathCall(true);
    // COB(1253): MOVE ZERO TO WS-PATHCALL-LEVEL
    ws.wsPathcallLevel.setValue(0);
    // COB(1254): SET DEFAULT-DYNAMIC-ACCESS TO TRUE
    ws.setDefaultDynamicAccess(true);
    // COB(1255): SET CUSTOM-STATIC-ACCESS TO TRUE
    ws.setCustomStaticAccess(true);
    // COB(1256): SET EXEC-SEGMENT-ACCESS TO TRUE
    ws.setExecSegmentAccess(true);
    // COB(1257): SET FULL-SCAN-FALSE TO TRUE
    ws.setFullScanFalse(true);
    // COB(1258): IF IRIS-FUNC-GHU
    // COB(1258): OR IRIS-FUNC-GHN
    // COB(1258): OR IRIS-FUNC-GHNP
    if (params.irisWorkArea.irisFuncGhu()
        || params.irisWorkArea.irisFuncGhn()
        || params.irisWorkArea.irisFuncGhnp()) {
      // COB(1261): SET COMMAND-WITH-HOLD TO TRUE
      ws.setCommandWithHold(true);
      // COB(1262): END-IF
    }
    // COB(1263): MOVE ZERO  TO IRIS-SQLCODE
    // COB(1263):               IRIS-SQLERRML
    // COB(1263):               WS-FB-KEY-LENGTH
    // COB(1263):               WS-SEGMENT-LEVEL
    // COB(1263):               WS-SEGMENT-LEN
    // COB(1263):               WS-LAST-SEGMENT-LEVEL
    // COB(1263):               WS-PATHCALL-LEN
    // COB(1263):               WS-INIT-PATHCALL-LEN
    // COB(1263):               SQL-CONDITION-CLAUSE-LENGTH
    // COB(1263):               SQL-JOIN-CLAUSE-LENGTH
    // COB(1263):               SQL-ORDERBY-CLAUSE-LENGTH
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
    // COB(1274): MOVE 1 TO WS-FB-KEY-LENGTH
    // COB(1274):           IRIS-SQLERRML
    ws.wsFbKeyLength.setValue(1);
    params.irisWorkArea.irisSqlError.irisSqlerrm.irisSqlerrml.setValue(1);
    // COB(1276): EVALUATE WS-SEGMENT-NAME
    // COB(1277): WHEN 'PAUTSUM0'
    if (ws.wsSegmentName.getValue().equals("PAUTSUM0")) {
      // COB(1278): MOVE PAUTSUM0-LVL TO WS-INIT-SEGMENT-LEVEL
      ws.wsInitSegmentLevel.setValue(ws.pautsum0Lvl);
      // COB(1279): WHEN 'PAUTDTL1'
    } else if (ws.wsSegmentName.getValue().equals("PAUTDTL1")) {
      // COB(1280): MOVE PAUTDTL1-LVL TO WS-INIT-SEGMENT-LEVEL
      ws.wsInitSegmentLevel.setValue(ws.pautdtl1Lvl);
      // COB(1281): WHEN 'PAUTINDX'
    } else if (ws.wsSegmentName.getValue().equals("PAUTINDX")) {
      // COB(1282): MOVE PAUTINDX-LVL TO WS-INIT-SEGMENT-LEVEL
      ws.wsInitSegmentLevel.setValue(ws.pautindxLvl);
      // COB(1283): END-EVALUATE
    }
    // COB(1284): MOVE SPACE TO IRIS-SQLERRMC
    // COB(1284):               WS-LAST-SEGMENT-NAME
    // COB(1284):               WS-LAST-IORTN-SECTION
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
    // COB(1299): MOVE 'FINALIZE-VARIABLES' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("FINALIZE-VARIABLES");
    // COB(1301): IF IO-RTN-FB-KEY-MAX-LENGTH > 0
    // COB(1301): AND IO-RTN-FB-KEY-MAX-LENGTH < WS-FB-KEY-LENGTH
    if (params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnFbKeyMaxLength.greaterThan(0)
        && params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnFbKeyMaxLength.lessThan(
            ws.wsFbKeyLength)) {
      // COB(1303): MOVE IO-RTN-FB-KEY-MAX-LENGTH TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(
          params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnFbKeyMaxLength);
      // COB(1304): END-IF
    }
    // COB(1308): IF IRIS-NO-ERROR
    //       *
    //       *    SETUP PCB INFO
    //       *
    if (params.irisWorkArea.irisNoError()) {
      // COB(1309): IF DB-PCB-DBD-NAME = SPACE OR LOW-VALUE
      if (params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.isSpaces()
          || params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.isLowValues()) {
        // COB(1310): MOVE WS-DBD-NAME TO DB-PCB-DBD-NAME
        params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName.setValue(ws.wsDbdName);
        // COB(1311): END-IF
      }
      // COB(1312): MOVE WS-FB-KEY-LENGTH TO DB-PCB-FB-KEY-LENGTH
      params.irisDbPcb.dbPcbFixedPart.dbPcbFbKeyLength.setValue(ws.wsFbKeyLength);
      // COB(1313): IF WS-FB-KEY-LENGTH > ZERO
      if (ws.wsFbKeyLength.greaterThan(0)) {
        // COB(1314): MOVE WS-FB-KEY-AREA(1:WS-FB-KEY-LENGTH)
        // COB(1314):                     TO DB-PCB-KEY-FB (1:WS-FB-KEY-LENGTH)
        params.irisDbPcb.dbPcbKeyFb.setValue(ws.wsFbKeyArea, ws.wsFbKeyLength.getInt());
        // COB(1316): END-IF
      }
      // COB(1317): MOVE WS-LAST-SEGMENT-NAME TO DB-PCB-SEGMENT-NAME
      params.irisDbPcb.dbPcbFixedPart.dbPcbSegmentName.setValue(ws.wsLastSegmentName);
      // COB(1318): MOVE WS-LAST-SEGMENT-LEVEL TO DB-PCB-SEGMENT-LEVEL
      params.irisDbPcb.dbPcbFixedPart.dbPcbSegmentLevel.setValue(ws.wsLastSegmentLevel);
      // COB(1319): ADD 1 TO WS-INIT-SEGMENT-LEVEL GIVING WS-IDX
      ws.wsIdx.add(1).add(ws.wsInitSegmentLevel);
      // COB(1320): PERFORM VARYING WS-IDX FROM WS-IDX BY 1
      // COB(1320):         UNTIL WS-IDX > WS-SEGMENTS-MAX-LVL
      ws.wsIdx.setValue(ws.wsIdx);
      for (; !ws.wsIdx.greaterThan(ws.wsSegmentsMaxLvl); ws.wsIdx.add(1)) {
        // COB(1322): MOVE LOW-VALUE TO IO-RTN-USED-KEY-STATUS(WS-IDX)
        // COB(1322):                   IO-RTN-USED-SSA-KEYS(WS-IDX)
        // COB(1322):                   IO-RTN-USED-SSA-INFO(WS-IDX)
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
        // COB(1325): END-PERFORM
      }
      // COB(1326): IF (IRIS-FUNC-GU OR IRIS-FUNC-GHU
      // COB(1326): OR IRIS-FUNC-ISRT)
      // COB(1326): AND WS-LAST-SEGMENT-LEVEL > ZERO
      if ((params.irisWorkArea.irisFuncGu()
              || params.irisWorkArea.irisFuncGhu()
              || params.irisWorkArea.irisFuncIsrt())
          && ws.wsLastSegmentLevel.greaterThan(0)) {
        // COB(1329): MOVE ZERO TO
        // COB(1329):   IO-RTN-LAST-OPEN-CURSOR(WS-LAST-SEGMENT-LEVEL)
        params
            .irisDbPcb
            .ioRtnUsedKeysPcbArea
            .ioRtnUsedKeysArea
            .ioRtnUsedKeyValueAtIndex(ws.wsLastSegmentLevel.getInt() - 1)
            .ioRtnUsedSsaKeys
            .ioRtnLastOpenCursor
            .setValue(0);
        // COB(1331): END-IF
      }
      // COB(1335): MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
      //       *
      //       *    SETUP STATUS
      //       *
      ws.irissqlc.irisDbSqlcode.setValue(params.irisWorkArea.irisSqlError.irisSqlcode);
      // COB(1336): EVALUATE TRUE
      // COB(1337): WHEN IRIS-SQL-OK
      if (ws.irissqlc.irisSqlOk()) {
        // COB(1338): SET DB-STATUS-OK TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusOk(true);
        // COB(1339): WHEN IRIS-SQL-UNIQ-CONSTR-VIOLATED
      } else if (ws.irissqlc.irisSqlUniqConstrViolated()) {
        // COB(1340): SET DB-STATUS-DUPLICATED-KEY TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusDuplicatedKey(true);
        // COB(1341): WHEN IRIS-SQL-DEADLOCK
      } else if (ws.irissqlc.irisSqlDeadlock()) {
        // COB(1342): SET DB-STATUS-RESOURCE-DEADLOCK TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusResourceDeadlock(true);
        // COB(1343): WHEN IRIS-SQL-INTE-CONSTR-VIOLATED
      } else if (ws.irissqlc.irisSqlInteConstrViolated()) {
        // COB(1344): SET DB-STATUS-SEGMENT-NOT-FOUND TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusSegmentNotFound(true);
        // COB(1345): WHEN IRIS-SQL-NOT-FOUND AND FULL-SCAN-FALSE
      } else if (ws.irissqlc.irisSqlNotFound() && ws.fullScanFalse()) {
        // COB(1346): SET DB-STATUS-SEGMENT-NOT-FOUND TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusSegmentNotFound(true);
        // COB(1347): WHEN IRIS-SQL-END-DB-REACHED
      } else if (ws.irissqlc.irisSqlEndDbReached()
          // COB(1348): WHEN IRIS-SQL-NOT-FOUND AND FULL-SCAN-TRUE
          || ws.irissqlc.irisSqlNotFound() && ws.fullScanTrue()) {
        // COB(1349): SET DB-STATUS-END-DB-REACHED TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusEndDbReached(true);
        // COB(1350): WHEN IRIS-SQL-CHG-SEG
      } else if (ws.irissqlc.irisSqlChgSeg()) {
        // COB(1351): SET DB-STATUS-CHANGED-SEGMENT-TYPE TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusChangedSegmentType(true);
        // COB(1352): WHEN IRIS-SQL-CHG-LEV
      } else if (ws.irissqlc.irisSqlChgLev()) {
        // COB(1353): SET DB-STATUS-HIGHER-LEVEL-CROSSED TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusHigherLevelCrossed(true);
        // COB(1354): WHEN IRIS-SQL-DUAL-PCB-MISMATCH
      } else if (ws.irissqlc.irisSqlDualPcbMismatch()) {
        // COB(1355): SET DB-STATUS-DUAL-PCB-MISMATCH TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusDualPcbMismatch(true);
        // COB(1356): WHEN IRIS-SQL-DUAL-IOAREA-MISMATCH
      } else if (ws.irissqlc.irisSqlDualIoareaMismatch()) {
        // COB(1357): SET DB-STATUS-DUAL-IOAREA-MISMATCH TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusDualIoareaMismatch(true);
        // COB(1358): WHEN OTHER
      } else {
        // COB(1359): SET DB-STATUS-INTERNAL-NOT-HANDLED TO TRUE
        params.irisDbPcb.dbPcbFixedPart.setDbStatusInternalNotHandled(true);
        // COB(1360): SET IRIS-ERR-UNHANDLED-SQLCODE TO TRUE
        params.irisWorkArea.setIrisErrUnhandledSqlcode(true);
        // COB(1361): END-EVALUATE
      }
      // COB(1362): IF IRIS-SQL-CHG-SEG
      // COB(1362): OR IRIS-SQL-CHG-LEV
      if (ws.irissqlc.irisSqlChgSeg() || ws.irissqlc.irisSqlChgLev()) {
        // COB(1364): SET IRIS-SQL-OK TO TRUE
        ws.irissqlc.setIrisSqlOk(true);
        // COB(1365): MOVE IRIS-DB-SQLCODE TO IRIS-SQLCODE
        params.irisWorkArea.irisSqlError.irisSqlcode.setValue(ws.irissqlc.irisDbSqlcode);
        // COB(1366): END-IF
      }
      // COB(1367): ELSE
    } else {
      // COB(1368): SET DB-STATUS-INTERNAL-NOT-HANDLED TO TRUE
      params.irisDbPcb.dbPcbFixedPart.setDbStatusInternalNotHandled(true);
      // COB(1369): END-IF
    }
    // COB(1370): IF EXEC-SAVE-AREA
    if (ws.execSaveArea()) {
      // COB(1371): SET ADDRESS OF LK-IO-AREA-BACKUP TO RUN-IO-AREA-PTR
      params.lkIoAreaBackup.setAddress(
          params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.runIoAreaPtr);
      // COB(1372): MOVE LK-IO-AREA(1:WS-SEGMENT-LEN) TO
      // COB(1372):      LK-IO-AREA-BACKUP(1:WS-SEGMENT-LEN)
      params.lkIoAreaBackup.setValue(params.lkIoArea, ws.wsSegmentLen.getInt());
      // COB(1374): END-IF
    }
    // COB(1375): SET SKIP-GE-PATH-CALL TO TRUE
    params.irisWorkArea.setSkipGePathCall(true);
    // COB(1377): IF WS-FB-KEY-LENGTH > 0
    // COB(1377): AND IRIS-KFB-YES
    if (ws.wsFbKeyLength.greaterThan(0) && params.irisWorkArea.irisKfbYes()) {
      // COB(1379): IF IRIS-KFB-LENGTH IS NUMERIC
      if (params.irisWorkArea.irisKfbLength.isNumeric()) {
        // COB(1380): IF IRIS-KFB-LENGTH < WS-FB-KEY-LENGTH
        // COB(1380): AND IRIS-KFB-LENGTH NOT = ZERO
        if (params.irisWorkArea.irisKfbLength.lessThan(ws.wsFbKeyLength)
            && !params.irisWorkArea.irisKfbLength.equals(0)) {
          // COB(1382): MOVE IRIS-KFB-LENGTH TO WS-FB-KEY-LENGTH
          ws.wsFbKeyLength.setValue(params.irisWorkArea.irisKfbLength);
          // COB(1383): END-IF
        }
        // COB(1384): END-IF
      }
      // COB(1385): EVALUATE IRIS-PARAM-NUM
      switch (params.irisWorkArea.irisParamNum.getInt()) {
          // COB(1386): WHEN 4
        case 4:
          // COB(1387): MOVE DB-PCB-KEY-FB(1:WS-FB-KEY-LENGTH)
          // COB(1387):   TO LK-SSA-01(1:WS-FB-KEY-LENGTH)
          params.lkSsa01.setValue(params.irisDbPcb.dbPcbKeyFb, ws.wsFbKeyLength.getInt());
          // COB(1389): WHEN 5
          break;
        case 5:
          // COB(1390): MOVE DB-PCB-KEY-FB(1:WS-FB-KEY-LENGTH)
          // COB(1390):   TO LK-SSA-02(1:WS-FB-KEY-LENGTH)
          params.lkSsa02.setValue(params.irisDbPcb.dbPcbKeyFb, ws.wsFbKeyLength.getInt());
          // COB(1392): WHEN 6
          break;
        case 6:
          // COB(1393): MOVE DB-PCB-KEY-FB(1:WS-FB-KEY-LENGTH)
          // COB(1393):   TO LK-SSA-03(1:WS-FB-KEY-LENGTH)
          params.lkSsa03.setValue(params.irisDbPcb.dbPcbKeyFb, ws.wsFbKeyLength.getInt());
          // COB(1395): WHEN 7
          break;
        default:
          break;
        case 7:
          // COB(1396): MOVE DB-PCB-KEY-FB(1:WS-FB-KEY-LENGTH)
          // COB(1396):   TO LK-SSA-04(1:WS-FB-KEY-LENGTH)
          params.lkSsa04.setValue(params.irisDbPcb.dbPcbKeyFb, ws.wsFbKeyLength.getInt());
          // COB(1398): END-EVALUATE
      }
      // COB(1399): SET IRIS-KFB-NO TO TRUE
      params.irisWorkArea.setIrisKfbNo(true);
      // COB(1400): MOVE ZERO TO IRIS-KFB-LENGTH
      params.irisWorkArea.irisKfbLength.setValue(0);
      // COB(1401): END-IF
    }
  }

  /***
   ******************************************************************
   *    HANDLE SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void handleSegmentPautsum0() {
    // COB(1414): MOVE PAUTSUM0-LVL TO WS-SEGMENT-LEVEL
    ws.wsSegmentLevel.setValue(ws.pautsum0Lvl);
    // COB(1415): MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN WS-INIT-PATHCALL-LEN
    ws.wsSegmentLen.setValue(ws.pautsum0Len);
    ws.wsInitPathcallLen.setValue(ws.pautsum0Len);
    // COB(1417): MOVE 'HANDLE-SEGMENT-PAUTSUM0' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("HANDLE-SEGMENT-PAUTSUM0");
    // COB(1419): EVALUATE TRUE
    // COB(1420): WHEN SQL-SELECT-PRIMARY
    if (params.irisWorkArea.sqlSelectPrimary()) {
      // COB(1421): PERFORM PAUTSUM0-SELECT-PRIMARY-KEY
      pautsum0SelectPrimaryKey();
      // COB(1422): WHEN SQL-SELECT-SEEK
    } else if (params.irisWorkArea.sqlSelectSeek()) {
      // COB(1423): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(1424): EVALUATE TRUE
      // COB(1425): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(1426): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(1427): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(1428): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(1429): END-EVALUATE
      }
      // COB(1431): PERFORM PAUTSUM0-SELECT-FIRST
      pautsum0SelectFirst();
      // COB(1432): WHEN SQL-SELECT-NEXT
    } else if (params.irisWorkArea.sqlSelectNext()) {
      // COB(1433): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(1434): EVALUATE TRUE
      // COB(1435): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(1436): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(1437): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(1438): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(1439): END-EVALUATE
      }
      // COB(1441): PERFORM PAUTSUM0-SELECT-NEXT
      pautsum0SelectNext();
      // COB(1442): WHEN SQL-SELECT-DYNAMIC
    } else if (params.irisWorkArea.sqlSelectDynamic()) {
      // COB(1443): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(1444): EVALUATE TRUE
      // COB(1445): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(1446): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(1447): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(1448): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(1449): END-EVALUATE
      }
      // COB(1451): PERFORM PAUTSUM0-SELECT-DYNAMIC
      pautsum0SelectDynamic();
      // COB(1452): WHEN SQL-SELECT-PATH
    } else if (params.irisWorkArea.sqlSelectPath()) {
      // COB(1453): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(1454): EVALUATE TRUE
      // COB(1455): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(1456): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(1457): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(1458): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(1459): END-EVALUATE
      }
      // COB(1461): PERFORM PAUTSUM0-SELECT-DYNAMIC
      pautsum0SelectDynamic();
      // COB(1462): WHEN SQL-INSERT
    } else if (params.irisWorkArea.sqlInsert()) {
      // COB(1463): SET COMMAND-CODE-HERE TO TRUE
      ws.setCommandCodeHere(true);
      // COB(1464): EVALUATE TRUE
      // COB(1465): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(1466): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(1467): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(1468): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(1469): END-EVALUATE
      }
      // COB(1471): PERFORM PAUTSUM0-INSERT
      pautsum0Insert();
      // COB(1472): WHEN SQL-UPDATE
    } else if (params.irisWorkArea.sqlUpdate()) {
      // COB(1473): PERFORM PAUTSUM0-UPDATE
      pautsum0Update();
      // COB(1474): WHEN SQL-DELETE
    } else if (params.irisWorkArea.sqlDelete()) {
      // COB(1475): PERFORM PAUTSUM0-DELETE
      pautsum0Delete();
      // COB(1476): WHEN OTHER
    } else {
      // COB(1477): SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
      params.irisWorkArea.setIrisErrFunctionNotFound(true);
      // COB(1478): END-EVALUATE
    }
    // COB(1480): IF IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(1481): MOVE PAUTSUM0-LVL TO WS-LAST-SEGMENT-LEVEL
      ws.wsLastSegmentLevel.setValue(ws.pautsum0Lvl);
      // COB(1482): MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
      ws.wsLastSegmentName.setString("PAUTSUM0");
      // COB(1483): END-IF.
    }
  }

  /***
   ******************************************************************
   *    SELECT ON PRIMARY KEY FOR SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0SelectPrimaryKey() {
    // COB(1495): MOVE 'PAUTSUM0-SELECT-PRIMARY-KEY' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-SELECT-PRIMARY-KEY");
    // COB(1497): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(1498): MOVE ZERO
      // COB(1498):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(1500): ELSE
    } else {
      // COB(1501): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(1501):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1503): END-IF
    }
    // COB(1504): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(1505): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(1506): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(1507): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(1507):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(1509): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(1510): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(1510):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler250
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(1512): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler174.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(1513): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(1514): END-IF
    }
    // COB(1515): MOVE WS-PACKED-FLD-18-00(1) TO KEY-ACCNTID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.keyAccntid.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(1516): EXEC SQL
    // COB(1516): SELECT
    // COB(1516):   KEY_ACCNTID
    // COB(1516):  ,PA_CUST_ID
    // COB(1516):  ,PA_AUTH_STATUS
    // COB(1516):  ,PA_ACCOUNT_STATUS1
    // COB(1516):  ,PA_ACCOUNT_STATUS2
    // COB(1516):  ,PA_ACCOUNT_STATUS3
    // COB(1516):  ,PA_ACCOUNT_STATUS4
    // COB(1516):  ,PA_ACCOUNT_STATUS5
    // COB(1516):  ,PA_CREDIT_LIMIT
    // COB(1516):  ,PA_CASH_LIMIT
    // COB(1516):  ,PA_CREDIT_BALANCE
    // COB(1516):  ,PA_CASH_BALANCE
    // COB(1516):  ,PA_APPROVED_AUTH_CNT
    // COB(1516):  ,PA_DECLINED_AUTH_CNT
    // COB(1516):  ,PA_APPROVED_AUTH_AMT
    // COB(1516):  ,PA_DECLINED_AUTH_AMT
    // COB(1516):  ,PAUTSUM0_FILLER
    // COB(1516): INTO
    // COB(1516):   :PAUTSUM0.KEY-ACCNTID
    // COB(1516):  ,:PAUTSUM0.PA-CUST-ID
    // COB(1516):  ,:PAUTSUM0.PA-AUTH-STATUS
    // COB(1516):  ,:PAUTSUM0.PA-ACCOUNT-STATUS1
    // COB(1516):  ,:PAUTSUM0.PA-ACCOUNT-STATUS2
    // COB(1516):  ,:PAUTSUM0.PA-ACCOUNT-STATUS3
    // COB(1516):  ,:PAUTSUM0.PA-ACCOUNT-STATUS4
    // COB(1516):  ,:PAUTSUM0.PA-ACCOUNT-STATUS5
    // COB(1516):  ,:PAUTSUM0.PA-CREDIT-LIMIT
    // COB(1516):  ,:PAUTSUM0.PA-CASH-LIMIT
    // COB(1516):  ,:PAUTSUM0.PA-CREDIT-BALANCE
    // COB(1516):  ,:PAUTSUM0.PA-CASH-BALANCE
    // COB(1516):  ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
    // COB(1516):  ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
    // COB(1516):  ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
    // COB(1516):  ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
    // COB(1516):  ,:PAUTSUM0.PAUTSUM0-FILLER
    // COB(1516): FROM
    // COB(1516): DBPAUTP0_PAUTSUM0
    // COB(1516):   WHERE
    // COB(1516):     KEY_ACCNTID =
    // COB(1516):   :PAUTSUM0.KEY-ACCNTID
    // COB(1516): END-EXEC
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
    // COB(1560): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(1561): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(1562): IF SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(1563): MOVE SPACE TO IO-RTN-USED-KEY-SECONDARY
      params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary.spaces();
      // COB(1564): MOVE KEY-ACCNTID OF PAUTSUM0(1:6)
      // COB(1564):                           TO IO-RTN-USED-KEY-SECONDARY
      params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary.setValue(
          ws.pautsum0copy.pautsum0.keyAccntid.getString(0, 6));
      // COB(1566): MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1567): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(1567):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(1569): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(1569):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1571): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(1571):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(1573): MOVE 6 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(6);
      // COB(1577): MOVE PA-CUST-ID OF PAUTSUM0 TO
      // COB(1577): WS-PACKED-FLD-18-00(1)
      //       *
      //       * PACKED FIELDS VS SIGNED ZONED FITTING
      //       *
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.paCustId);
      // COB(1579): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(1579): PA-CUST-ID-N OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCustIdN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
      // COB(1581): MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(ws.pautsum0Len);
      // COB(1582): PERFORM PAUTSUM0-SET-IO-AREA
      pautsum0SetIoArea();
      // COB(1583): SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(1584): MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
      // COB(1584):                 IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
      ws.wsLastSegmentName.setString("PAUTSUM0");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTSUM0");
      // COB(1586): END-IF
    }
    // COB(1588): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(1589): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    SELECT FIRST TIME FOR SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0SelectFirst() {
    // COB(1600): MOVE 'PAUTSUM0-SELECT-FIRST' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-SELECT-FIRST");
    // COB(1602): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(1603): MOVE ZERO
      // COB(1603):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(1605): ELSE
    } else {
      // COB(1606): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(1606):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1608): END-IF
    }
    // COB(1609): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(1610): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(1611): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(1612): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(1612):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(1614): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(1615): IF COMMAND-CODE-LAST
    if (ws.commandCodeLast()) {
      // COB(1616): EXEC SQL
      // COB(1616): SELECT
      // COB(1616):   KEY_ACCNTID
      // COB(1616):  ,PA_CUST_ID
      // COB(1616):  ,PA_AUTH_STATUS
      // COB(1616):  ,PA_ACCOUNT_STATUS1
      // COB(1616):  ,PA_ACCOUNT_STATUS2
      // COB(1616):  ,PA_ACCOUNT_STATUS3
      // COB(1616):  ,PA_ACCOUNT_STATUS4
      // COB(1616):  ,PA_ACCOUNT_STATUS5
      // COB(1616):  ,PA_CREDIT_LIMIT
      // COB(1616):  ,PA_CASH_LIMIT
      // COB(1616):  ,PA_CREDIT_BALANCE
      // COB(1616):  ,PA_CASH_BALANCE
      // COB(1616):  ,PA_APPROVED_AUTH_CNT
      // COB(1616):  ,PA_DECLINED_AUTH_CNT
      // COB(1616):  ,PA_APPROVED_AUTH_AMT
      // COB(1616):  ,PA_DECLINED_AUTH_AMT
      // COB(1616):  ,PAUTSUM0_FILLER
      // COB(1616): INTO
      // COB(1616):   :PAUTSUM0.KEY-ACCNTID
      // COB(1616):  ,:PAUTSUM0.PA-CUST-ID
      // COB(1616):  ,:PAUTSUM0.PA-AUTH-STATUS
      // COB(1616):  ,:PAUTSUM0.PA-ACCOUNT-STATUS1
      // COB(1616):  ,:PAUTSUM0.PA-ACCOUNT-STATUS2
      // COB(1616):  ,:PAUTSUM0.PA-ACCOUNT-STATUS3
      // COB(1616):  ,:PAUTSUM0.PA-ACCOUNT-STATUS4
      // COB(1616):  ,:PAUTSUM0.PA-ACCOUNT-STATUS5
      // COB(1616):  ,:PAUTSUM0.PA-CREDIT-LIMIT
      // COB(1616):  ,:PAUTSUM0.PA-CASH-LIMIT
      // COB(1616):  ,:PAUTSUM0.PA-CREDIT-BALANCE
      // COB(1616):  ,:PAUTSUM0.PA-CASH-BALANCE
      // COB(1616):  ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
      // COB(1616):  ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
      // COB(1616):  ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
      // COB(1616):  ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
      // COB(1616):  ,:PAUTSUM0.PAUTSUM0-FILLER
      // COB(1616): FROM
      // COB(1616): DBPAUTP0_PAUTSUM0
      // COB(1616):   WHERE
      // COB(1616):     KEY_ACCNTID = (
      // COB(1616):     SELECT
      // COB(1616):       KEY_ACCNTID
      // COB(1616):     FROM
      // COB(1616):       DBPAUTP0_PAUTSUM0
      // COB(1616):       WHERE KEY_ACCNTID IS NOT NULL
      // COB(1616):     ORDER BY 1 DESC LIMIT 1
      // COB(1616):     )
      // COB(1616): END-EXEC
      try (NSqlCommand sql = new NSqlCommand(sqlca)) {
        sql.setCommand(
            "SELECT KEY_ACCNTID, PA_CUST_ID, PA_AUTH_STATUS, PA_ACCOUNT_STATUS1,"
                + " PA_ACCOUNT_STATUS2, PA_ACCOUNT_STATUS3, PA_ACCOUNT_STATUS4, PA_ACCOUNT_STATUS5,"
                + " PA_CREDIT_LIMIT, PA_CASH_LIMIT, PA_CREDIT_BALANCE, PA_CASH_BALANCE,"
                + " PA_APPROVED_AUTH_CNT, PA_DECLINED_AUTH_CNT, PA_APPROVED_AUTH_AMT,"
                + " PA_DECLINED_AUTH_AMT, PAUTSUM0_FILLER FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID"
                + " = (SELECT KEY_ACCNTID FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID IS NOT 0 ORDER"
                + " BY 1 DESC LIMIT 1)");
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
      // COB(1665): ELSE
    } else {
      // COB(1666): EXEC SQL
      // COB(1666):   SELECT
      // COB(1666):     KEY_ACCNTID
      // COB(1666):    ,PA_CUST_ID
      // COB(1666):    ,PA_AUTH_STATUS
      // COB(1666):    ,PA_ACCOUNT_STATUS1
      // COB(1666):    ,PA_ACCOUNT_STATUS2
      // COB(1666):    ,PA_ACCOUNT_STATUS3
      // COB(1666):    ,PA_ACCOUNT_STATUS4
      // COB(1666):    ,PA_ACCOUNT_STATUS5
      // COB(1666):    ,PA_CREDIT_LIMIT
      // COB(1666):    ,PA_CASH_LIMIT
      // COB(1666):    ,PA_CREDIT_BALANCE
      // COB(1666):    ,PA_CASH_BALANCE
      // COB(1666):    ,PA_APPROVED_AUTH_CNT
      // COB(1666):    ,PA_DECLINED_AUTH_CNT
      // COB(1666):    ,PA_APPROVED_AUTH_AMT
      // COB(1666):    ,PA_DECLINED_AUTH_AMT
      // COB(1666):    ,PAUTSUM0_FILLER
      // COB(1666):   INTO
      // COB(1666):     :PAUTSUM0.KEY-ACCNTID
      // COB(1666):    ,:PAUTSUM0.PA-CUST-ID
      // COB(1666):    ,:PAUTSUM0.PA-AUTH-STATUS
      // COB(1666):    ,:PAUTSUM0.PA-ACCOUNT-STATUS1
      // COB(1666):    ,:PAUTSUM0.PA-ACCOUNT-STATUS2
      // COB(1666):    ,:PAUTSUM0.PA-ACCOUNT-STATUS3
      // COB(1666):    ,:PAUTSUM0.PA-ACCOUNT-STATUS4
      // COB(1666):    ,:PAUTSUM0.PA-ACCOUNT-STATUS5
      // COB(1666):    ,:PAUTSUM0.PA-CREDIT-LIMIT
      // COB(1666):    ,:PAUTSUM0.PA-CASH-LIMIT
      // COB(1666):    ,:PAUTSUM0.PA-CREDIT-BALANCE
      // COB(1666):    ,:PAUTSUM0.PA-CASH-BALANCE
      // COB(1666):    ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
      // COB(1666):    ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
      // COB(1666):    ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
      // COB(1666):    ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
      // COB(1666):    ,:PAUTSUM0.PAUTSUM0-FILLER
      // COB(1666):   FROM
      // COB(1666):   DBPAUTP0_PAUTSUM0
      // COB(1666):   WHERE
      // COB(1666):     KEY_ACCNTID = (
      // COB(1666):     SELECT
      // COB(1666):       KEY_ACCNTID
      // COB(1666):     FROM
      // COB(1666):       DBPAUTP0_PAUTSUM0
      // COB(1666):       WHERE KEY_ACCNTID IS NOT NULL
      // COB(1666):     ORDER BY 1 LIMIT 1
      // COB(1666):     )
      // COB(1666): END-EXEC
      try (NSqlCommand sql = new NSqlCommand(sqlca)) {
        sql.setCommand(
            "SELECT KEY_ACCNTID, PA_CUST_ID, PA_AUTH_STATUS, PA_ACCOUNT_STATUS1,"
                + " PA_ACCOUNT_STATUS2, PA_ACCOUNT_STATUS3, PA_ACCOUNT_STATUS4, PA_ACCOUNT_STATUS5,"
                + " PA_CREDIT_LIMIT, PA_CASH_LIMIT, PA_CREDIT_BALANCE, PA_CASH_BALANCE,"
                + " PA_APPROVED_AUTH_CNT, PA_DECLINED_AUTH_CNT, PA_APPROVED_AUTH_AMT,"
                + " PA_DECLINED_AUTH_AMT, PAUTSUM0_FILLER FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID"
                + " = (SELECT KEY_ACCNTID FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID IS NOT 0 ORDER"
                + " BY 1 LIMIT 1)");
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
      // COB(1715): END-IF
    }
    // COB(1717): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(1719): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(1720): EVALUATE TRUE
    // COB(1721): WHEN IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(1722): MOVE SPACE TO IO-RTN-USED-KEY-SECONDARY
      params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary.spaces();
      // COB(1723): MOVE KEY-ACCNTID OF PAUTSUM0(1:6)
      // COB(1723):                           TO IO-RTN-USED-KEY-SECONDARY
      params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary.setValue(
          ws.pautsum0copy.pautsum0.keyAccntid.getString(0, 6));
      // COB(1725): MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1726): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(1726):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(1728): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(1728):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1730): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(1730):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(1732): MOVE 6 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(6);
      // COB(1736): MOVE PA-CUST-ID OF PAUTSUM0 TO
      // COB(1736): WS-PACKED-FLD-18-00(1)
      //       *
      //       * PACKED FIELDS VS SIGNED ZONED FITTING
      //       *
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.paCustId);
      // COB(1738): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(1738): PA-CUST-ID-N OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCustIdN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
      // COB(1740): MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(ws.pautsum0Len);
      // COB(1741): PERFORM PAUTSUM0-SET-IO-AREA
      pautsum0SetIoArea();
      // COB(1742): SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(1743): MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
      // COB(1743):                 IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
      ws.wsLastSegmentName.setString("PAUTSUM0");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTSUM0");
      // COB(1745): END-EVALUATE
    }
    // COB(1747): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(1748): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    SELECT NEXT FOR SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0SelectNext() {
    // COB(1759): MOVE 'PAUTSUM0-SELECT-NEXT' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-SELECT-NEXT");
    // COB(1761): MOVE IO-RTN-USED-KEY-SECONDARY TO KEY-ACCNTID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.keyAccntid.setValue(
        params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary);
    // COB(1762): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(1763): MOVE ZERO
      // COB(1763):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(1765): ELSE
    } else {
      // COB(1766): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(1766):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1768): END-IF
    }
    // COB(1769): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(1770): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(1771): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(1772): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(1772):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(1774): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(1775): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(1775):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler250
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(1777): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler174.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(1778): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(1779): END-IF
    }
    // COB(1780): MOVE WS-PACKED-FLD-18-00(1) TO KEY-ACCNTID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.keyAccntid.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(1781): IF COMMAND-CODE-LAST
    if (ws.commandCodeLast()) {
      // COB(1782): EXEC SQL
      // COB(1782): SELECT
      // COB(1782):   KEY_ACCNTID
      // COB(1782):  ,PA_CUST_ID
      // COB(1782):  ,PA_AUTH_STATUS
      // COB(1782):  ,PA_ACCOUNT_STATUS1
      // COB(1782):  ,PA_ACCOUNT_STATUS2
      // COB(1782):  ,PA_ACCOUNT_STATUS3
      // COB(1782):  ,PA_ACCOUNT_STATUS4
      // COB(1782):  ,PA_ACCOUNT_STATUS5
      // COB(1782):  ,PA_CREDIT_LIMIT
      // COB(1782):  ,PA_CASH_LIMIT
      // COB(1782):  ,PA_CREDIT_BALANCE
      // COB(1782):  ,PA_CASH_BALANCE
      // COB(1782):  ,PA_APPROVED_AUTH_CNT
      // COB(1782):  ,PA_DECLINED_AUTH_CNT
      // COB(1782):  ,PA_APPROVED_AUTH_AMT
      // COB(1782):  ,PA_DECLINED_AUTH_AMT
      // COB(1782):  ,PAUTSUM0_FILLER
      // COB(1782): INTO
      // COB(1782):   :PAUTSUM0.KEY-ACCNTID
      // COB(1782):  ,:PAUTSUM0.PA-CUST-ID
      // COB(1782):  ,:PAUTSUM0.PA-AUTH-STATUS
      // COB(1782):  ,:PAUTSUM0.PA-ACCOUNT-STATUS1
      // COB(1782):  ,:PAUTSUM0.PA-ACCOUNT-STATUS2
      // COB(1782):  ,:PAUTSUM0.PA-ACCOUNT-STATUS3
      // COB(1782):  ,:PAUTSUM0.PA-ACCOUNT-STATUS4
      // COB(1782):  ,:PAUTSUM0.PA-ACCOUNT-STATUS5
      // COB(1782):  ,:PAUTSUM0.PA-CREDIT-LIMIT
      // COB(1782):  ,:PAUTSUM0.PA-CASH-LIMIT
      // COB(1782):  ,:PAUTSUM0.PA-CREDIT-BALANCE
      // COB(1782):  ,:PAUTSUM0.PA-CASH-BALANCE
      // COB(1782):  ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
      // COB(1782):  ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
      // COB(1782):  ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
      // COB(1782):  ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
      // COB(1782):  ,:PAUTSUM0.PAUTSUM0-FILLER
      // COB(1782): FROM
      // COB(1782): DBPAUTP0_PAUTSUM0
      // COB(1782):   WHERE
      // COB(1782):     KEY_ACCNTID = (
      // COB(1782):     SELECT
      // COB(1782):       KEY_ACCNTID
      // COB(1782):     FROM
      // COB(1782):       DBPAUTP0_PAUTSUM0
      // COB(1782):     WHERE
      // COB(1782):       KEY_ACCNTID >
      // COB(1782):       :PAUTSUM0.KEY-ACCNTID
      // COB(1782):       AND KEY_ACCNTID IS NOT NULL
      // COB(1782):     ORDER BY 1 DESC LIMIT 1
      // COB(1782):     )
      // COB(1782): END-EXEC
      try (NSqlCommand sql = new NSqlCommand(sqlca)) {
        sql.setCommand(
            "SELECT KEY_ACCNTID, PA_CUST_ID, PA_AUTH_STATUS, PA_ACCOUNT_STATUS1,"
                + " PA_ACCOUNT_STATUS2, PA_ACCOUNT_STATUS3, PA_ACCOUNT_STATUS4, PA_ACCOUNT_STATUS5,"
                + " PA_CREDIT_LIMIT, PA_CASH_LIMIT, PA_CREDIT_BALANCE, PA_CASH_BALANCE,"
                + " PA_APPROVED_AUTH_CNT, PA_DECLINED_AUTH_CNT, PA_APPROVED_AUTH_AMT,"
                + " PA_DECLINED_AUTH_AMT, PAUTSUM0_FILLER FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID"
                + " = (SELECT KEY_ACCNTID FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID > ? AND"
                + " KEY_ACCNTID IS NOT 0 ORDER BY 1 DESC LIMIT 1)");
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
      // COB(1834): ELSE
    } else {
      // COB(1835): EXEC SQL
      // COB(1835): SELECT
      // COB(1835):   KEY_ACCNTID
      // COB(1835):  ,PA_CUST_ID
      // COB(1835):  ,PA_AUTH_STATUS
      // COB(1835):  ,PA_ACCOUNT_STATUS1
      // COB(1835):  ,PA_ACCOUNT_STATUS2
      // COB(1835):  ,PA_ACCOUNT_STATUS3
      // COB(1835):  ,PA_ACCOUNT_STATUS4
      // COB(1835):  ,PA_ACCOUNT_STATUS5
      // COB(1835):  ,PA_CREDIT_LIMIT
      // COB(1835):  ,PA_CASH_LIMIT
      // COB(1835):  ,PA_CREDIT_BALANCE
      // COB(1835):  ,PA_CASH_BALANCE
      // COB(1835):  ,PA_APPROVED_AUTH_CNT
      // COB(1835):  ,PA_DECLINED_AUTH_CNT
      // COB(1835):  ,PA_APPROVED_AUTH_AMT
      // COB(1835):  ,PA_DECLINED_AUTH_AMT
      // COB(1835):  ,PAUTSUM0_FILLER
      // COB(1835): INTO
      // COB(1835):   :PAUTSUM0.KEY-ACCNTID
      // COB(1835):  ,:PAUTSUM0.PA-CUST-ID
      // COB(1835):  ,:PAUTSUM0.PA-AUTH-STATUS
      // COB(1835):  ,:PAUTSUM0.PA-ACCOUNT-STATUS1
      // COB(1835):  ,:PAUTSUM0.PA-ACCOUNT-STATUS2
      // COB(1835):  ,:PAUTSUM0.PA-ACCOUNT-STATUS3
      // COB(1835):  ,:PAUTSUM0.PA-ACCOUNT-STATUS4
      // COB(1835):  ,:PAUTSUM0.PA-ACCOUNT-STATUS5
      // COB(1835):  ,:PAUTSUM0.PA-CREDIT-LIMIT
      // COB(1835):  ,:PAUTSUM0.PA-CASH-LIMIT
      // COB(1835):  ,:PAUTSUM0.PA-CREDIT-BALANCE
      // COB(1835):  ,:PAUTSUM0.PA-CASH-BALANCE
      // COB(1835):  ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
      // COB(1835):  ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
      // COB(1835):  ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
      // COB(1835):  ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
      // COB(1835):  ,:PAUTSUM0.PAUTSUM0-FILLER
      // COB(1835): FROM
      // COB(1835): DBPAUTP0_PAUTSUM0
      // COB(1835):   WHERE
      // COB(1835):     KEY_ACCNTID = (
      // COB(1835):     SELECT
      // COB(1835):       KEY_ACCNTID
      // COB(1835):     FROM
      // COB(1835):       DBPAUTP0_PAUTSUM0
      // COB(1835):     WHERE
      // COB(1835):       KEY_ACCNTID >
      // COB(1835):       :PAUTSUM0.KEY-ACCNTID
      // COB(1835):       AND KEY_ACCNTID IS NOT NULL
      // COB(1835):     ORDER BY 1 LIMIT 1
      // COB(1835):     )
      // COB(1835): END-EXEC
      try (NSqlCommand sql = new NSqlCommand(sqlca)) {
        sql.setCommand(
            "SELECT KEY_ACCNTID, PA_CUST_ID, PA_AUTH_STATUS, PA_ACCOUNT_STATUS1,"
                + " PA_ACCOUNT_STATUS2, PA_ACCOUNT_STATUS3, PA_ACCOUNT_STATUS4, PA_ACCOUNT_STATUS5,"
                + " PA_CREDIT_LIMIT, PA_CASH_LIMIT, PA_CREDIT_BALANCE, PA_CASH_BALANCE,"
                + " PA_APPROVED_AUTH_CNT, PA_DECLINED_AUTH_CNT, PA_APPROVED_AUTH_AMT,"
                + " PA_DECLINED_AUTH_AMT, PAUTSUM0_FILLER FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID"
                + " = (SELECT KEY_ACCNTID FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID > ? AND"
                + " KEY_ACCNTID IS NOT 0 ORDER BY 1 LIMIT 1)");
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
      // COB(1887): END-IF
    }
    // COB(1889): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(1890): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(1891): EVALUATE TRUE
    // COB(1892): WHEN IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(1893): MOVE SPACE TO IO-RTN-USED-KEY-SECONDARY
      params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary.spaces();
      // COB(1894): MOVE KEY-ACCNTID OF PAUTSUM0(1:6)
      // COB(1894):                           TO IO-RTN-USED-KEY-SECONDARY
      params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary.setValue(
          ws.pautsum0copy.pautsum0.keyAccntid.getString(0, 6));
      // COB(1896): MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1897): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(1897):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(1899): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(1899):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1901): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(1901):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(1903): MOVE 6 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(6);
      // COB(1907): MOVE PA-CUST-ID OF PAUTSUM0 TO
      // COB(1907): WS-PACKED-FLD-18-00(1)
      //       *
      //       * PACKED FIELDS VS SIGNED ZONED FITTING
      //       *
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.paCustId);
      // COB(1909): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(1909): PA-CUST-ID-N OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCustIdN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
      // COB(1911): MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(ws.pautsum0Len);
      // COB(1912): PERFORM PAUTSUM0-SET-IO-AREA
      pautsum0SetIoArea();
      // COB(1913): SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(1914): MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
      // COB(1914):                 IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
      ws.wsLastSegmentName.setString("PAUTSUM0");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTSUM0");
      // COB(1916): END-EVALUATE
    }
    // COB(1918): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(1919): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    DYNAMIC SELECT FOR SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0SelectDynamic() {
    // COB(1930): MOVE 'PAUTSUM0-SELECT-DYNAMIC' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-SELECT-DYNAMIC");
    // COB(1932): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(1933): MOVE ZERO
      // COB(1933):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(1935): ELSE
    } else {
      // COB(1936): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(1936):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(1938): END-IF
    }
    // COB(1939): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(1940): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(1941): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(1942): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(1942):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(1944): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(1947): MOVE ZERO TO WS-WHERE-LEN WS-ORDERBY-LEN
    //       *    PREPARE THE WHERE CONDITION IF ANY
    //       *
    ws.wsWhereLen.setValue(0);
    ws.wsOrderbyLen.setValue(0);
    // COB(1948): IF SQL-CONDITION-CLAUSE-LENGTH > 0
    if (ws.sqlConditionClause.sqlConditionClauseLength.greaterThan(0)) {
      // COB(1949): SUBTRACT 1 FROM SQL-CONDITION-CLAUSE-LENGTH
      ws.sqlConditionClause.sqlConditionClauseLength.subtract(1);
      // COB(1950): STRING 'WHERE '
      // COB(1950): SQL-CONDITION-CLAUSE-TEXT(1:SQL-CONDITION-CLAUSE-LENGTH)
      // COB(1950): ' ' DELIMITED BY SIZE INTO WS-WHERE
      ws.wsWhere.concat(
          "WHERE ",
          ws.sqlConditionClause.sqlConditionClauseText.getString(
              0, ws.sqlConditionClause.sqlConditionClauseLength.getInt()),
          " ");
      // COB(1953): COMPUTE WS-WHERE-LEN = SQL-CONDITION-CLAUSE-LENGTH + 7
      ws.wsWhereLen.setValue(ws.sqlConditionClause.sqlConditionClauseLength.getInt() + 7);
      // COB(1954): END-IF
    }
    // COB(1956): IF SQL-ORDERBY-CLAUSE-LENGTH > 0
    if (ws.sqlOrderbyClause.sqlOrderbyClauseLength.greaterThan(0)) {
      // COB(1957): SUBTRACT 1 FROM SQL-ORDERBY-CLAUSE-LENGTH
      ws.sqlOrderbyClause.sqlOrderbyClauseLength.subtract(1);
      // COB(1958): MOVE SQL-ORDERBY-CLAUSE-TEXT(1:SQL-ORDERBY-CLAUSE-LENGTH)
      // COB(1958):                TO WS-ORDERBY(1:SQL-ORDERBY-CLAUSE-LENGTH)
      ws.wsOrderby.setValue(
          ws.sqlOrderbyClause.sqlOrderbyClauseText,
          ws.sqlOrderbyClause.sqlOrderbyClauseLength.getInt());
      // COB(1960): MOVE SQL-ORDERBY-CLAUSE-LENGTH TO WS-ORDERBY-LEN
      ws.wsOrderbyLen.setValue(ws.sqlOrderbyClause.sqlOrderbyClauseLength);
      // COB(1961): ELSE
    } else {
      // COB(1962): COMPUTE WS-ORDERBY-LEN = 1
      ws.wsOrderbyLen.setValue(1);
      // COB(1963): STRING ' ORDER BY KEY_ACCNTID '
      // COB(1963): DELIMITED BY SIZE INTO WS-ORDERBY POINTER WS-ORDERBY-LEN
      ws.wsOrderbyLen.setValue(ws.wsOrderby.concat(" ORDER BY KEY_ACCNTID "));
      // COB(1965): SUBTRACT 1 FROM WS-ORDERBY-LEN
      ws.wsOrderbyLen.subtract(1);
      // COB(1966): END-IF
    }
    // COB(1968): IF COMMAND-CODE-LAST
    if (ws.commandCodeLast()) {
      // COB(1969): STRING WS-ORDERBY(1:WS-ORDERBY-LEN)
      // COB(1969): ' DESC ' DELIMITED BY SIZE
      // COB(1969): INTO WS-ORDERBY
      ws.wsOrderby.concat(ws.wsOrderby.getString(0, ws.wsOrderbyLen.getInt()), " DESC ");
      // COB(1972): ADD 6 TO WS-ORDERBY-LEN
      ws.wsOrderbyLen.add(6);
      // COB(1973): END-IF
    }
    // COB(1978): MOVE 1 TO WS-SQL-STM-LEN
    //       *
    //       *
    //       *    PREPARE THE DYNAMIC QUERY
    //       *
    ws.wsSqlStm.wsSqlStmLen.setValue(1);
    // COB(1979): STRING
    // COB(1979): 'SELECT '
    // COB(1979):   'DBPAUTP0_PAUTSUM0.KEY_ACCNTID '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_CUST_ID '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_AUTH_STATUS '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS1 '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS2 '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS3 '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS4 '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS5 '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_CREDIT_LIMIT '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_CASH_LIMIT '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_CREDIT_BALANCE '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_CASH_BALANCE '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_APPROVED_AUTH_CNT '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_DECLINED_AUTH_CNT '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_APPROVED_AUTH_AMT '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PA_DECLINED_AUTH_AMT '
    // COB(1979):  ',DBPAUTP0_PAUTSUM0.PAUTSUM0_FILLER '
    // COB(1979): 'FROM '
    // COB(1979): 'DBPAUTP0_PAUTSUM0 '
    // COB(1979): DELIMITED BY SIZE
    // COB(1979): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
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
    // COB(2002): IF SQL-JOIN-CLAUSE-LENGTH > 0
    if (ws.sqlJoinClause.sqlJoinClauseLength.greaterThan(0)) {
      // COB(2003): SUBTRACT 1 FROM SQL-JOIN-CLAUSE-LENGTH
      ws.sqlJoinClause.sqlJoinClauseLength.subtract(1);
      // COB(2004): STRING SQL-JOIN-CLAUSE-TEXT(1:SQL-JOIN-CLAUSE-LENGTH) ' '
      // COB(2004): DELIMITED BY SIZE
      // COB(2004): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(
              ws.sqlJoinClause.sqlJoinClauseText.getString(
                  0, ws.sqlJoinClause.sqlJoinClauseLength.getInt()),
              " "));
      // COB(2007): END-IF
    }
    // COB(2008): IF WS-WHERE-LEN > 0
    if (ws.wsWhereLen.greaterThan(0)) {
      // COB(2009): STRING
      // COB(2009): WS-WHERE(1:WS-WHERE-LEN)
      // COB(2009): DELIMITED BY SIZE
      // COB(2009): ' AND KEY_ACCNTID IS NOT CL-NULL '
      // COB(2009): DELIMITED BY SIZE
      // COB(2009): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(
              ws.wsWhere.getString(0, ws.wsWhereLen.getInt()), " AND KEY_ACCNTID IS NOT CL-NULL "));
      // COB(2015): IF IRIS-TRACE-FULL
      if (params.irisWorkArea.irisTraceFull()) {
        // COB(2016): MOVE 0 TO IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
        // COB(2017): STRING '<IRISTRACE> - DBPAUTX0:SELECT-DYNAMIC' NL
        // COB(2017):      ' SECTION    =(PAUTSUM0-SELECT-DYNAMIC) ' NL
        // COB(2017):      ' CONDITION  =(' WS-WHERE(1:WS-WHERE-LEN) ')' NL
        // COB(2017):      ' ORDER BY   =(' WS-ORDERBY(1:WS-ORDERBY-LEN) ')'
        // COB(2017): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - DBPAUTX0:SELECT-DYNAMIC",
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
        // COB(2022): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(2023): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(2024): END-IF
      }
      // COB(2025): ELSE
    } else {
      // COB(2026): STRING
      // COB(2026): ' WHERE KEY_ACCNTID IS NOT CL-NULL '
      // COB(2026): DELIMITED BY SIZE
      // COB(2026): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(" WHERE KEY_ACCNTID IS NOT CL-NULL "));
      // COB(2030): IF IRIS-TRACE-FULL
      if (params.irisWorkArea.irisTraceFull()) {
        // COB(2031): MOVE 0 TO IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
        // COB(2032): STRING '<IRISTRACE> - DBPAUTX0:SELECT-DYNAMIC' NL
        // COB(2032):      ' SECTION    =(PAUTSUM0-SELECT-DYNAMIC) ' NL
        // COB(2032):      ' CONDITION  =(WHERE KEY_ACCNTID IS NOT NULL)'
        // COB(2032): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - DBPAUTX0:SELECT-DYNAMIC",
            ws.iriscomm.nl,
            " SECTION    =(PAUTSUM0-SELECT-DYNAMIC) ",
            ws.iriscomm.nl,
            " CONDITION  =(WHERE KEY_ACCNTID IS NOT NULL)",
            ws.iriscomm.messageEnd);
        // COB(2036): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(2037): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(2038): END-IF
      }
      // COB(2039): END-IF
    }
    // COB(2040): IF WS-ORDERBY-LEN > 0
    if (ws.wsOrderbyLen.greaterThan(0)) {
      // COB(2041): STRING WS-ORDERBY(1:WS-ORDERBY-LEN) DELIMITED BY SIZE
      // COB(2041): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(ws.wsOrderby.getString(0, ws.wsOrderbyLen.getInt())));
      // COB(2043): END-IF
    }
    // COB(2044): STRING ' FETCH FIRST 1 ROW ONLY '
    // COB(2044): DELIMITED BY SIZE
    // COB(2044): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
    ws.wsSqlStm.wsSqlStmLen.setValue(ws.wsSqlStm.wsSqlStmTxt.concat(" FETCH FIRST 1 ROW ONLY "));
    // COB(2047): SUBTRACT 1 FROM WS-SQL-STM-LEN
    ws.wsSqlStm.wsSqlStmLen.subtract(1);
    // COB(2051): EXEC SQL
    // COB(2051):   DECLARE PAUTSUM0_CRS CURSOR
    // COB(2051):   FOR PAUTSUM0_STATEMENT
    // COB(2051): END-EXEC
    //       *
    //       *    DECLARE THE DYNAMIC CURSOR
    //       *
    // do nothing
    // COB(2058): EXEC SQL
    // COB(2058):   DECLARE PAUTSUM0_STATEMENT STATEMENT
    // COB(2058): END-EXEC
    //       *
    //       *    DECLARE THE SQL STATEMENT
    //       *
    // do nothing
    // COB(2064): EXEC SQL
    // COB(2064):   PREPARE PAUTSUM0_STATEMENT
    // COB(2064):   INTO :SQLDA
    // COB(2064):   FROM :WS-SQL-STM
    // COB(2064): END-EXEC
    //       *
    //       *    PREPARE THE STATEMENT
    //       *
    pautsum0Crs = new NSqlCursor(this, sqlca, ws.wsSqlStm.toString());
    // COB(2072): IF SQLCODE NOT = ZERO
    //       *
    //       *    TEST THE RETURN CODE
    //       *
    if (!sqlca.sqlcode.equals(0)) {
      // COB(2073): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(2074): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(2075): GO TO PAUTSUM0-SELECT-DYNAMIC-EX
      return;
      // COB(2076): END-IF
    }
    // COB(2080): EXEC SQL
    // COB(2080):   OPEN PAUTSUM0_CRS
    // COB(2080): END-EXEC
    //       *
    //       *    OPEN THE DYNAMIC CURSOR
    //       *
    pautsum0Crs.open();
    // COB(2086): IF SQLCODE NOT = ZERO
    //       *
    //       *    TEST THE RETURN CODE
    //       *
    if (!sqlca.sqlcode.equals(0)) {
      // COB(2087): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(2088): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(2089): GO TO PAUTSUM0-SELECT-DYNAMIC-EX
      return;
      // COB(2090): END-IF
    }
    // COB(2094): EXEC SQL
    // COB(2094):   FETCH PAUTSUM0_CRS
    // COB(2094):   INTO
    // COB(2094):     :PAUTSUM0.KEY-ACCNTID
    // COB(2094):    ,:PAUTSUM0.PA-CUST-ID
    // COB(2094):    ,:PAUTSUM0.PA-AUTH-STATUS
    // COB(2094):    ,:PAUTSUM0.PA-ACCOUNT-STATUS1
    // COB(2094):    ,:PAUTSUM0.PA-ACCOUNT-STATUS2
    // COB(2094):    ,:PAUTSUM0.PA-ACCOUNT-STATUS3
    // COB(2094):    ,:PAUTSUM0.PA-ACCOUNT-STATUS4
    // COB(2094):    ,:PAUTSUM0.PA-ACCOUNT-STATUS5
    // COB(2094):    ,:PAUTSUM0.PA-CREDIT-LIMIT
    // COB(2094):    ,:PAUTSUM0.PA-CASH-LIMIT
    // COB(2094):    ,:PAUTSUM0.PA-CREDIT-BALANCE
    // COB(2094):    ,:PAUTSUM0.PA-CASH-BALANCE
    // COB(2094):    ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
    // COB(2094):    ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
    // COB(2094):    ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
    // COB(2094):    ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
    // COB(2094):    ,:PAUTSUM0.PAUTSUM0-FILLER
    // COB(2094): END-EXEC
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
    // COB(2116): IF SQLCODE NOT = ZERO
    if (!sqlca.sqlcode.equals(0)) {
      // COB(2117): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(2118): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(2122): EXEC SQL
      // COB(2122):   CLOSE PAUTSUM0_CRS
      // COB(2122): END-EXEC
      //       *
      //       *      CLOSE THE DYNAMIC CURSOR
      //       *
      pautsum0Crs.close();
      // COB(2125): GO TO PAUTSUM0-SELECT-DYNAMIC-EX
      return;
      // COB(2126): END-IF
    }
    // COB(2128): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2129): MOVE SQLERRM TO IRIS-SQLERRM
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
    // COB(2133): EXEC SQL
    // COB(2133):   CLOSE PAUTSUM0_CRS
    // COB(2133): END-EXEC
    //       *
    //       *      CLOSE THE DYNAMIC CURSOR
    //       *
    pautsum0Crs.close();
    // COB(2137): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(2139): MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(params.irisWorkArea.irisSqlError.irisSqlcode);
    // COB(2140): EVALUATE TRUE
    // COB(2141): WHEN IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(2142): MOVE SPACE TO IO-RTN-USED-KEY-SECONDARY
      params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary.spaces();
      // COB(2143): MOVE KEY-ACCNTID OF PAUTSUM0(1:6)
      // COB(2143):                           TO IO-RTN-USED-KEY-SECONDARY
      params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary.setValue(
          ws.pautsum0copy.pautsum0.keyAccntid.getString(0, 6));
      // COB(2145): MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2146): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(2146):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(2148): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(2148):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2150): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(2150):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(2152): MOVE 6 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(6);
      // COB(2153): IF SQL-SELECT-DYNAMIC
      // COB(2153): AND NOT COMMAND-WITH-HOLD
      if (params.irisWorkArea.sqlSelectDynamic() && !ws.commandWithHold()) {
        // COB(2158): MOVE PA-CUST-ID OF PAUTSUM0 TO
        // COB(2158): WS-PACKED-FLD-18-00(1)
        //       *
        //       * PACKED FIELDS VS SIGNED ZONED FITTING
        //       *
        ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.paCustId);
        // COB(2160): MOVE WS-PACKED-FLD-18-00(1) TO
        // COB(2160): PA-CUST-ID-N OF PAUTSUM0
        ws.pautsum0copy.pautsum0.paCustIdN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
        // COB(2162): MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN
        ws.wsSegmentLen.setValue(ws.pautsum0Len);
        // COB(2163): PERFORM PAUTSUM0-SET-IO-AREA
        pautsum0SetIoArea();
        // COB(2164): END-IF
      }
      // COB(2165): SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(2166): MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
      // COB(2166):                 IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
      ws.wsLastSegmentName.setString("PAUTSUM0");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTSUM0");
      // COB(2168): END-EVALUATE.
    }
  }

  /***
   ******************************************************************
   *    INSERT FOR SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0Insert() {
    // COB(2179): MOVE 'PAUTSUM0-INSERT' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-INSERT");
    // COB(2182): INITIALIZE PAUTSUM0
    //       *
    //       *
    ws.pautsum0copy.pautsum0.initialize();
    // COB(2184): MOVE LK-IO-AREA(1:PAUTSUM0-LEN) TO PAUTSUM0(1:PAUTSUM0-LEN)
    ws.pautsum0copy.pautsum0.setValue(params.lkIoArea, ws.pautsum0Len.getInt());
    // COB(2185): IF KEY-ACCNTID
    // COB(2185): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(2187): MOVE ZERO TO
      // COB(2187): KEY-ACCNTID OF PAUTSUM0
      ws.pautsum0copy.pautsum0.keyAccntid.setValue(0);
      // COB(2189): END-IF
    }
    // COB(2190): IF PA-CUST-ID-N
    // COB(2190): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCustIdN.isNumeric()) {
      // COB(2192): MOVE ZERO TO
      // COB(2192): PA-CUST-ID-N OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCustIdN.setValue(0);
      // COB(2194): END-IF
    }
    // COB(2195): IF PA-CREDIT-LIMIT
    // COB(2195): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCreditLimit.isNumeric()) {
      // COB(2197): MOVE ZERO TO
      // COB(2197): PA-CREDIT-LIMIT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCreditLimit.setValue(0);
      // COB(2199): END-IF
    }
    // COB(2200): IF PA-CASH-LIMIT
    // COB(2200): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCashLimit.isNumeric()) {
      // COB(2202): MOVE ZERO TO
      // COB(2202): PA-CASH-LIMIT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCashLimit.setValue(0);
      // COB(2204): END-IF
    }
    // COB(2205): IF PA-CREDIT-BALANCE
    // COB(2205): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCreditBalance.isNumeric()) {
      // COB(2207): MOVE ZERO TO
      // COB(2207): PA-CREDIT-BALANCE OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCreditBalance.setValue(0);
      // COB(2209): END-IF
    }
    // COB(2210): IF PA-CASH-BALANCE
    // COB(2210): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCashBalance.isNumeric()) {
      // COB(2212): MOVE ZERO TO
      // COB(2212): PA-CASH-BALANCE OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCashBalance.setValue(0);
      // COB(2214): END-IF
    }
    // COB(2215): IF PA-APPROVED-AUTH-AMT
    // COB(2215): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paApprovedAuthAmt.isNumeric()) {
      // COB(2217): MOVE ZERO TO
      // COB(2217): PA-APPROVED-AUTH-AMT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paApprovedAuthAmt.setValue(0);
      // COB(2219): END-IF
    }
    // COB(2220): IF PA-DECLINED-AUTH-AMT
    // COB(2220): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paDeclinedAuthAmt.isNumeric()) {
      // COB(2222): MOVE ZERO TO
      // COB(2222): PA-DECLINED-AUTH-AMT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paDeclinedAuthAmt.setValue(0);
      // COB(2224): END-IF
    }
    // COB(2228): MOVE PA-CUST-ID-N OF PAUTSUM0 TO
    // COB(2228): WS-PACKED-FLD-18-00(1)
    //       *
    //       * SIGNED ZONED FIELDS VS PACKED FITTING
    //       *
    ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.paCustIdN);
    // COB(2230): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(2230): PA-CUST-ID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.paCustId.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(2232): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(2233): MOVE ZERO
      // COB(2233):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(2235): ELSE
    } else {
      // COB(2236): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(2236):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2238): END-IF
    }
    // COB(2239): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(2240): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(2241): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(2242): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(2242):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(2244): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2247): EXEC SQL
    // COB(2247):   INSERT INTO
    // COB(2247):     DBPAUTP0_PAUTSUM0
    // COB(2247):     (
    // COB(2247):     KEY_ACCNTID
    // COB(2247):    ,PA_CUST_ID
    // COB(2247):    ,PA_AUTH_STATUS
    // COB(2247):    ,PA_ACCOUNT_STATUS1
    // COB(2247):    ,PA_ACCOUNT_STATUS2
    // COB(2247):    ,PA_ACCOUNT_STATUS3
    // COB(2247):    ,PA_ACCOUNT_STATUS4
    // COB(2247):    ,PA_ACCOUNT_STATUS5
    // COB(2247):    ,PA_CREDIT_LIMIT
    // COB(2247):    ,PA_CASH_LIMIT
    // COB(2247):    ,PA_CREDIT_BALANCE
    // COB(2247):    ,PA_CASH_BALANCE
    // COB(2247):    ,PA_APPROVED_AUTH_CNT
    // COB(2247):    ,PA_DECLINED_AUTH_CNT
    // COB(2247):    ,PA_APPROVED_AUTH_AMT
    // COB(2247):    ,PA_DECLINED_AUTH_AMT
    // COB(2247):    ,PAUTSUM0_FILLER
    // COB(2247):     )
    // COB(2247):   VALUES
    // COB(2247):   (
    // COB(2247):     :PAUTSUM0.KEY-ACCNTID
    // COB(2247):    ,:PAUTSUM0.PA-CUST-ID
    // COB(2247):    ,:PAUTSUM0.PA-AUTH-STATUS
    // COB(2247):    ,:PAUTSUM0.PA-ACCOUNT-STATUS1
    // COB(2247):    ,:PAUTSUM0.PA-ACCOUNT-STATUS2
    // COB(2247):    ,:PAUTSUM0.PA-ACCOUNT-STATUS3
    // COB(2247):    ,:PAUTSUM0.PA-ACCOUNT-STATUS4
    // COB(2247):    ,:PAUTSUM0.PA-ACCOUNT-STATUS5
    // COB(2247):    ,:PAUTSUM0.PA-CREDIT-LIMIT
    // COB(2247):    ,:PAUTSUM0.PA-CASH-LIMIT
    // COB(2247):    ,:PAUTSUM0.PA-CREDIT-BALANCE
    // COB(2247):    ,:PAUTSUM0.PA-CASH-BALANCE
    // COB(2247):    ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
    // COB(2247):    ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
    // COB(2247):    ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
    // COB(2247):    ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
    // COB(2247):    ,:PAUTSUM0.PAUTSUM0-FILLER
    // COB(2247):   )
    // COB(2247): END-EXEC
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
    // COB(2291): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(2292): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2293): EVALUATE TRUE
    // COB(2294): WHEN IRIS-SQL-OK
    if ((ws.irissqlc.irisSqlOk())
        // COB(2295): WHEN IRIS-SQL-UNIQ-CONSTR-VIOLATED
        || ws.irissqlc.irisSqlUniqConstrViolated()) {
      // COB(2296): MOVE SPACE TO IO-RTN-USED-KEY-SECONDARY
      params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary.spaces();
      // COB(2297): MOVE KEY-ACCNTID OF PAUTSUM0(1:6)
      // COB(2297):                           TO IO-RTN-USED-KEY-SECONDARY
      params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary.setValue(
          ws.pautsum0copy.pautsum0.keyAccntid.getString(0, 6));
      // COB(2299): MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2300): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(2300):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(2302): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(2302):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2304): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(2304):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(2306): MOVE 6 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(6);
      // COB(2307): SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(2308): IF IRIS-SQL-UNIQ-CONSTR-VIOLATED
      if (ws.irissqlc.irisSqlUniqConstrViolated()) {
        // COB(2309): SET IO-RTN-USED-KEY-DUPREC(PAUTSUM0-LVL) TO TRUE
        params
            .irisDbPcb
            .ioRtnUsedKeysPcbArea
            .ioRtnUsedKeysArea
            .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
            .setIoRtnUsedKeyDuprec(true);
        // COB(2310): END-IF
      }
      // COB(2311): MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
      // COB(2311):                 IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
      ws.wsLastSegmentName.setString("PAUTSUM0");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTSUM0");
      // COB(2313): END-EVALUATE
    }
    // COB(2315): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2316): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    UPDATE FOR SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0Update() {
    // COB(2327): MOVE 'PAUTSUM0-UPDATE' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-UPDATE");
    // COB(2330): INITIALIZE PAUTSUM0
    //       *
    //       *
    ws.pautsum0copy.pautsum0.initialize();
    // COB(2332): MOVE LK-IO-AREA(1:PAUTSUM0-LEN) TO PAUTSUM0(1:PAUTSUM0-LEN)
    ws.pautsum0copy.pautsum0.setValue(params.lkIoArea, ws.pautsum0Len.getInt());
    // COB(2333): IF KEY-ACCNTID
    // COB(2333): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(2335): MOVE ZERO TO
      // COB(2335): KEY-ACCNTID OF PAUTSUM0
      ws.pautsum0copy.pautsum0.keyAccntid.setValue(0);
      // COB(2337): END-IF
    }
    // COB(2338): IF PA-CUST-ID-N
    // COB(2338): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCustIdN.isNumeric()) {
      // COB(2340): MOVE ZERO TO
      // COB(2340): PA-CUST-ID-N OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCustIdN.setValue(0);
      // COB(2342): END-IF
    }
    // COB(2343): IF PA-CREDIT-LIMIT
    // COB(2343): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCreditLimit.isNumeric()) {
      // COB(2345): MOVE ZERO TO
      // COB(2345): PA-CREDIT-LIMIT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCreditLimit.setValue(0);
      // COB(2347): END-IF
    }
    // COB(2348): IF PA-CASH-LIMIT
    // COB(2348): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCashLimit.isNumeric()) {
      // COB(2350): MOVE ZERO TO
      // COB(2350): PA-CASH-LIMIT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCashLimit.setValue(0);
      // COB(2352): END-IF
    }
    // COB(2353): IF PA-CREDIT-BALANCE
    // COB(2353): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCreditBalance.isNumeric()) {
      // COB(2355): MOVE ZERO TO
      // COB(2355): PA-CREDIT-BALANCE OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCreditBalance.setValue(0);
      // COB(2357): END-IF
    }
    // COB(2358): IF PA-CASH-BALANCE
    // COB(2358): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paCashBalance.isNumeric()) {
      // COB(2360): MOVE ZERO TO
      // COB(2360): PA-CASH-BALANCE OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paCashBalance.setValue(0);
      // COB(2362): END-IF
    }
    // COB(2363): IF PA-APPROVED-AUTH-AMT
    // COB(2363): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paApprovedAuthAmt.isNumeric()) {
      // COB(2365): MOVE ZERO TO
      // COB(2365): PA-APPROVED-AUTH-AMT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paApprovedAuthAmt.setValue(0);
      // COB(2367): END-IF
    }
    // COB(2368): IF PA-DECLINED-AUTH-AMT
    // COB(2368): OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.paDeclinedAuthAmt.isNumeric()) {
      // COB(2370): MOVE ZERO TO
      // COB(2370): PA-DECLINED-AUTH-AMT OF PAUTSUM0
      ws.pautsum0copy.pautsum0.paDeclinedAuthAmt.setValue(0);
      // COB(2372): END-IF
    }
    // COB(2376): MOVE PA-CUST-ID-N OF PAUTSUM0 TO
    // COB(2376): WS-PACKED-FLD-18-00(1)
    //       *
    //       * SIGNED ZONED FIELDS VS PACKED FITTING
    //       *
    ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.paCustIdN);
    // COB(2378): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(2378): PA-CUST-ID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.paCustId.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(2381): MOVE IO-RTN-USED-KEY-SECONDARY TO KEY-ACCNTID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.keyAccntid.setValue(
        params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary);
    // COB(2382): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(2383): MOVE ZERO
      // COB(2383):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(2385): ELSE
    } else {
      // COB(2386): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(2386):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2388): END-IF
    }
    // COB(2389): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(2390): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(2391): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(2392): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(2392):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(2394): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2395): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(2395):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler250
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(2397): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler174.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(2398): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(2399): END-IF
    }
    // COB(2400): MOVE WS-PACKED-FLD-18-00(1) TO KEY-ACCNTID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.keyAccntid.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(2401): MOVE KEY-ACCNTID OF PAUTSUM0(1:6)
    // COB(2401):                                   TO WS-KEY-ACCNTID
    ws.wsKeyAccntid.setValue(ws.pautsum0copy.pautsum0.keyAccntid.getString(0, 6));
    // COB(2405): EXEC SQL
    // COB(2405):   UPDATE DBPAUTP0_PAUTSUM0 SET
    // COB(2405):     PA_CUST_ID = :PAUTSUM0.PA-CUST-ID,
    // COB(2405):     PA_AUTH_STATUS = :PAUTSUM0.PA-AUTH-STATUS,
    // COB(2405):     PA_ACCOUNT_STATUS1 = :PAUTSUM0.PA-ACCOUNT-STATUS1,
    // COB(2405):     PA_ACCOUNT_STATUS2 = :PAUTSUM0.PA-ACCOUNT-STATUS2,
    // COB(2405):     PA_ACCOUNT_STATUS3 = :PAUTSUM0.PA-ACCOUNT-STATUS3,
    // COB(2405):     PA_ACCOUNT_STATUS4 = :PAUTSUM0.PA-ACCOUNT-STATUS4,
    // COB(2405):     PA_ACCOUNT_STATUS5 = :PAUTSUM0.PA-ACCOUNT-STATUS5,
    // COB(2405):     PA_CREDIT_LIMIT = :PAUTSUM0.PA-CREDIT-LIMIT,
    // COB(2405):     PA_CASH_LIMIT = :PAUTSUM0.PA-CASH-LIMIT,
    // COB(2405):     PA_CREDIT_BALANCE = :PAUTSUM0.PA-CREDIT-BALANCE,
    // COB(2405):     PA_CASH_BALANCE = :PAUTSUM0.PA-CASH-BALANCE,
    // COB(2405):     PA_APPROVED_AUTH_CNT = :PAUTSUM0.PA-APPROVED-AUTH-CNT,
    // COB(2405):     PA_DECLINED_AUTH_CNT = :PAUTSUM0.PA-DECLINED-AUTH-CNT,
    // COB(2405):     PA_APPROVED_AUTH_AMT = :PAUTSUM0.PA-APPROVED-AUTH-AMT,
    // COB(2405):     PA_DECLINED_AUTH_AMT = :PAUTSUM0.PA-DECLINED-AUTH-AMT,
    // COB(2405):     PAUTSUM0_FILLER = :PAUTSUM0.PAUTSUM0-FILLER
    // COB(2405):   WHERE
    // COB(2405):     KEY_ACCNTID =
    // COB(2405):   :WS-KEY-ACCNTID
    // COB(2405): END-EXEC
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
      sql.where(ws.wsKeyAccntid);
      sql.execute();
    }
    // COB(2428): IF SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(2429): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(2429): TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2431): MOVE WS-PACKED-FLD-CHR(1)(1:6)
      // COB(2431): TO IO-RTN-USED-KEY-SECONDARY(1:6)
      params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary.setValue(
          ws.filler250.wsPackedFldChrAtIndex(0), 6);
      // COB(2433): END-IF
    }
    // COB(2437): MOVE PA-CUST-ID OF PAUTSUM0 TO
    // COB(2437): WS-PACKED-FLD-18-00(1)
    //       *
    //       * PACKED FIELDS VS SIGNED ZONED FITTING
    //       *
    ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.paCustId);
    // COB(2439): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(2439): PA-CUST-ID-N OF PAUTSUM0
    ws.pautsum0copy.pautsum0.paCustIdN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(2442): IF SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(2443): MOVE LK-IO-AREA(1:PAUTSUM0-LEN)
      // COB(2443):   TO PAUTSUM0-LAST-AREA
      ws.segmentsLastArea.pautsum0LastArea.setValue(params.lkIoArea, ws.pautsum0Len.getInt());
      // COB(2445): END-IF
    }
    // COB(2446): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2447): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    DELETE FOR SEGMENT PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0Delete() {
    // COB(2458): MOVE 'PAUTSUM0-DELETE' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-DELETE");
    // COB(2461): IF HAS-PATHCALLS
    //       *
    //       *
    if (ws.hasPathcalls()) {
      // COB(2462): SET IRIS-ERR-RTN-UNHANDLED-ACCESS TO TRUE
      params.irisWorkArea.setIrisErrRtnUnhandledAccess(true);
      // COB(2463): GO TO PAUTSUM0-DELETE-EX
      return;
      // COB(2464): END-IF
    }
    // COB(2466): MOVE IO-RTN-USED-KEY-SECONDARY TO KEY-ACCNTID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.keyAccntid.setValue(
        params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary);
    // COB(2467): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(2468): MOVE ZERO
      // COB(2468):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(2470): ELSE
    } else {
      // COB(2471): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(2471):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(2473): END-IF
    }
    // COB(2474): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(2475): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(2476): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(2477): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(2477):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(2479): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2480): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(2480):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler250
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(2482): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler174.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(2483): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(2484): END-IF
    }
    // COB(2485): MOVE WS-PACKED-FLD-18-00(1) TO KEY-ACCNTID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.keyAccntid.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(2487): EXEC SQL
    // COB(2487):   DELETE FROM DBPAUTP0_PAUTSUM0
    // COB(2487):   WHERE
    // COB(2487):     KEY_ACCNTID =
    // COB(2487):   :PAUTSUM0.KEY-ACCNTID
    // COB(2487): END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand("DELETE FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID = ?");
      sql.where(ws.pautsum0copy.pautsum0.keyAccntid);
      sql.execute();
    }
    // COB(2494): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2495): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    SET I/O AREA PAUTSUM0
   ******************************************************************
   */
  protected void pautsum0SetIoArea() {
    // COB(2506): MOVE 'PAUTSUM0-SET-IO-AREA' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTSUM0-SET-IO-AREA");
    // COB(2508): MOVE PAUTSUM0(1:WS-SEGMENT-LEN)
    // COB(2508):                TO LK-IO-AREA(1:WS-SEGMENT-LEN)
    // COB(2508):                   PAUTSUM0-LAST-AREA.
    params.lkIoArea.setValue(ws.pautsum0copy.pautsum0, ws.wsSegmentLen.getInt());
    ws.segmentsLastArea.pautsum0LastArea.setValue(
        ws.pautsum0copy.pautsum0, ws.wsSegmentLen.getInt());
  }

  /***
   ******************************************************************
   *    HANDLE SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void handleSegmentPautdtl1() {
    // COB(2521): MOVE PAUTDTL1-LVL TO WS-SEGMENT-LEVEL
    ws.wsSegmentLevel.setValue(ws.pautdtl1Lvl);
    // COB(2522): MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN WS-PATHCALL-LEN
    ws.wsSegmentLen.setValue(ws.pautdtl1Len);
    ws.wsPathcallLen.setValue(ws.pautdtl1Len);
    // COB(2524): IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
    if (params
        .irisWorkArea
        .irisKeyvalueTab
        .irisCcodeLevelsAtIndex(ws.pautsum0Lvl.getInt() - 1)
        .irisCcodeValues
        .irisCodePathcall()) {
      // COB(2525): SET HAS-PATHCALLS TO TRUE
      ws.setHasPathcalls(true);
      // COB(2526): ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
      ws.wsPathcallLen.add(ws.pautsum0Len);
      // COB(2527): END-IF
    }
    // COB(2528): MOVE WS-PATHCALL-LEN TO WS-INIT-PATHCALL-LEN
    ws.wsInitPathcallLen.setValue(ws.wsPathcallLen);
    // COB(2530): MOVE 'HANDLE-SEGMENT-PAUTDTL1' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("HANDLE-SEGMENT-PAUTDTL1");
    // COB(2532): EVALUATE TRUE
    // COB(2533): WHEN SQL-SELECT-PRIMARY
    if (params.irisWorkArea.sqlSelectPrimary()) {
      // COB(2534): PERFORM PAUTDTL1-SELECT-PRIMARY-KEY
      pautdtl1SelectPrimaryKey();
      // COB(2535): WHEN SQL-SELECT-SEEK
    } else if (params.irisWorkArea.sqlSelectSeek()) {
      // COB(2536): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(2537): EVALUATE TRUE
      // COB(2538): WHEN  IRIS-CODE-FIRST(2)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(2539): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(2540): WHEN  IRIS-CODE-LAST(2)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(2541): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(2542): END-EVALUATE
      }
      // COB(2544): PERFORM PAUTDTL1-SELECT-FIRST
      pautdtl1SelectFirst();
      // COB(2545): WHEN SQL-SELECT-NEXT
    } else if (params.irisWorkArea.sqlSelectNext()) {
      // COB(2546): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(2547): EVALUATE TRUE
      // COB(2548): WHEN  IRIS-CODE-FIRST(2)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(2549): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(2550): WHEN  IRIS-CODE-LAST(2)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(2551): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(2552): END-EVALUATE
      }
      // COB(2554): PERFORM PAUTDTL1-SELECT-NEXT
      pautdtl1SelectNext();
      // COB(2555): WHEN SQL-SELECT-DYNAMIC
    } else if (params.irisWorkArea.sqlSelectDynamic()) {
      // COB(2556): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(2557): EVALUATE TRUE
      // COB(2558): WHEN  IRIS-CODE-FIRST(2)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(2559): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(2560): WHEN  IRIS-CODE-LAST(2)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(2561): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(2562): END-EVALUATE
      }
      // COB(2564): PERFORM PAUTDTL1-SELECT-DYNAMIC
      pautdtl1SelectDynamic();
      // COB(2565): WHEN SQL-SELECT-PATH
    } else if (params.irisWorkArea.sqlSelectPath()) {
      // COB(2566): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(2567): EVALUATE TRUE
      // COB(2568): WHEN  IRIS-CODE-FIRST(2)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(2569): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(2570): WHEN  IRIS-CODE-LAST(2)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(2571): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(2572): END-EVALUATE
      }
      // COB(2574): PERFORM PAUTDTL1-SELECT-DYNAMIC
      pautdtl1SelectDynamic();
      // COB(2575): WHEN SQL-INSERT
    } else if (params.irisWorkArea.sqlInsert()) {
      // COB(2576): SET COMMAND-CODE-HERE TO TRUE
      ws.setCommandCodeHere(true);
      // COB(2577): EVALUATE TRUE
      // COB(2578): WHEN  IRIS-CODE-FIRST(2)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(2579): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(2580): WHEN  IRIS-CODE-LAST(2)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(1)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(2581): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(2582): END-EVALUATE
      }
      // COB(2584): PERFORM PAUTDTL1-INSERT
      pautdtl1Insert();
      // COB(2585): WHEN SQL-UPDATE
    } else if (params.irisWorkArea.sqlUpdate()) {
      // COB(2586): PERFORM PAUTDTL1-UPDATE
      pautdtl1Update();
      // COB(2587): WHEN SQL-DELETE
    } else if (params.irisWorkArea.sqlDelete()) {
      // COB(2588): PERFORM PAUTDTL1-DELETE
      pautdtl1Delete();
      // COB(2589): WHEN OTHER
    } else {
      // COB(2590): SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
      params.irisWorkArea.setIrisErrFunctionNotFound(true);
      // COB(2591): END-EVALUATE
    }
    // COB(2593): IF IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(2594): MOVE PAUTDTL1-LVL TO WS-LAST-SEGMENT-LEVEL
      ws.wsLastSegmentLevel.setValue(ws.pautdtl1Lvl);
      // COB(2595): MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
      ws.wsLastSegmentName.setString("PAUTDTL1");
      // COB(2596): END-IF.
    }
  }

  /***
   ******************************************************************
   *    SELECT ON PRIMARY KEY FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1SelectPrimaryKey() {
    // COB(2608): MOVE 'PAUTDTL1-SELECT-PRIMARY-KEY' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-SELECT-PRIMARY-KEY");
    // COB(2610): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(2611): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(2612): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(2612):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler250
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(2614): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(2615): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(2615):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(0), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(2617): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2618): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler174.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(2619): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(2620): END-IF
    }
    // COB(2621): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(2621):                              PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(2623): COMPUTE WS-CK-LEN = 8
    ws.wsCkLen.setValue(8);
    // COB(2624): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(2624):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, ws.wsCkPos.getInt() - 1, 8);
    // COB(2626): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2627): MOVE IO-RTN-USED-KEY-ALPHA(2)(1:8) TO
    // COB(2627):                                 KEY-PAUT9CTS OF PAUTDTL1(1:8)
    ws.pautdtl1copy.pautdtl1.keyPaut9cts.setValue(
        params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(1)
            .ioRtnUsedSsaKeys
            .ioRtnUsedKeyAlpha,
        8);
    // COB(2629): EXEC SQL
    // COB(2629): SELECT
    // COB(2629):   KEY_PAUT9CTS
    // COB(2629):  ,PA_AUTH_ORIG_DATE
    // COB(2629):  ,PA_AUTH_ORIG_TIME
    // COB(2629):  ,PA_CARD_NUM
    // COB(2629):  ,PA_AUTH_TYPE
    // COB(2629):  ,PA_CARD_EXPIRY_DATE
    // COB(2629):  ,PA_MESSAGE_TYPE
    // COB(2629):  ,PA_MESSAGE_SOURCE
    // COB(2629):  ,PA_AUTH_ID_CODE
    // COB(2629):  ,PA_AUTH_RESP_CODE
    // COB(2629):  ,PA_AUTH_RESP_REASON
    // COB(2629):  ,PA_PROCESSING_CODE
    // COB(2629):  ,PA_TRANSACTION_AMT
    // COB(2629):  ,PA_APPROVED_AMT
    // COB(2629):  ,PA_MERCHANT_CATAGORY_CODE
    // COB(2629):  ,PA_ACQR_COUNTRY_CODE
    // COB(2629):  ,PA_POS_ENTRY_MODE
    // COB(2629):  ,PA_MERCHANT_ID
    // COB(2629):  ,PA_MERCHANT_NAME
    // COB(2629):  ,PA_MERCHANT_CITY
    // COB(2629):  ,PA_MERCHANT_STATE
    // COB(2629):  ,PA_MERCHANT_ZIP
    // COB(2629):  ,PA_TRANSACTION_ID
    // COB(2629):  ,PA_MATCH_STATUS
    // COB(2629):  ,PA_AUTH_FRAUD
    // COB(2629):  ,PA_FRAUD_RPT_DATE
    // COB(2629):  ,PAUTDTL1_FILLER
    // COB(2629):  ,PAUTSUM0_KEY_ACCNTID
    // COB(2629): INTO
    // COB(2629):   :PAUTDTL1.KEY-PAUT9CTS
    // COB(2629):  ,:PAUTDTL1.PA-AUTH-ORIG-DATE
    // COB(2629):  ,:PAUTDTL1.PA-AUTH-ORIG-TIME
    // COB(2629):  ,:PAUTDTL1.PA-CARD-NUM
    // COB(2629):  ,:PAUTDTL1.PA-AUTH-TYPE
    // COB(2629):  ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
    // COB(2629):  ,:PAUTDTL1.PA-MESSAGE-TYPE
    // COB(2629):  ,:PAUTDTL1.PA-MESSAGE-SOURCE
    // COB(2629):  ,:PAUTDTL1.PA-AUTH-ID-CODE
    // COB(2629):  ,:PAUTDTL1.PA-AUTH-RESP-CODE
    // COB(2629):  ,:PAUTDTL1.PA-AUTH-RESP-REASON
    // COB(2629):  ,:PAUTDTL1.PA-PROCESSING-CODE
    // COB(2629):  ,:PAUTDTL1.PA-TRANSACTION-AMT
    // COB(2629):  ,:PAUTDTL1.PA-APPROVED-AMT
    // COB(2629):  ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
    // COB(2629):  ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
    // COB(2629):  ,:PAUTDTL1.PA-POS-ENTRY-MODE
    // COB(2629):  ,:PAUTDTL1.PA-MERCHANT-ID
    // COB(2629):  ,:PAUTDTL1.PA-MERCHANT-NAME
    // COB(2629):  ,:PAUTDTL1.PA-MERCHANT-CITY
    // COB(2629):  ,:PAUTDTL1.PA-MERCHANT-STATE
    // COB(2629):  ,:PAUTDTL1.PA-MERCHANT-ZIP
    // COB(2629):  ,:PAUTDTL1.PA-TRANSACTION-ID
    // COB(2629):  ,:PAUTDTL1.PA-MATCH-STATUS
    // COB(2629):  ,:PAUTDTL1.PA-AUTH-FRAUD
    // COB(2629):  ,:PAUTDTL1.PA-FRAUD-RPT-DATE
    // COB(2629):  ,:PAUTDTL1.PAUTDTL1-FILLER
    // COB(2629):  ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
    // COB(2629): FROM
    // COB(2629): DBPAUTP0_PAUTDTL1
    // COB(2629):   WHERE
    // COB(2629):     KEY_PAUT9CTS =
    // COB(2629):   :PAUTDTL1.KEY-PAUT9CTS
    // COB(2629):   AND
    // COB(2629):     PAUTSUM0_KEY_ACCNTID = :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
    // COB(2629): END-EXEC
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
    // COB(2697): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(2698): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2699): IF SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(2700): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
      // COB(2700):                                      WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(2702): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(2702):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(2704): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
      // COB(2704):                               IO-RTN-USED-KEY-ALPHA(2)(1:8)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 8);
      // COB(2706): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
      // COB(2706):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(2708): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(2708):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(2710): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
      // COB(2710):                                      TO WS-FB-KEY-AREA(7:8)
      ws.wsFbKeyArea.setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, 6, 8);
      // COB(2712): MOVE 14 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(14);
      // COB(2716): MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
      // COB(2716): WS-PACKED-FLD-18-00(1)
      //       *
      //       * PACKED FIELDS VS SIGNED ZONED FITTING
      //       *
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCode);
      // COB(2718): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(2718): PA-PROCESSING-CODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
      // COB(2720): MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
      // COB(2720): WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryMode);
      // COB(2722): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(2722): PA-POS-ENTRY-MODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
      // COB(2724): MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(ws.pautdtl1Len);
      // COB(2725): IF HAS-PATHCALLS AND SQL-SELECT-PRIMARY
      if (ws.hasPathcalls() && params.irisWorkArea.sqlSelectPrimary()) {
        // COB(2726): PERFORM PAUTDTL1-READ-PATHCALL
        pautdtl1ReadPathcall();
        // COB(2727): ELSE
      } else {
        // COB(2728): PERFORM PAUTDTL1-SET-IO-AREA
        pautdtl1SetIoArea();
        // COB(2729): END-IF
      }
      // COB(2730): SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(2731): MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
      // COB(2731):                 IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
      ws.wsLastSegmentName.setString("PAUTDTL1");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTDTL1");
      // COB(2733): END-IF
    }
    // COB(2735): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(2736): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    SELECT PATHCALL FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1ReadPathcall() {
    // COB(2747): MOVE 'PAUTDTL1-READ-PATHCALL' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-READ-PATHCALL");
    // COB(2749): SET HAS-NOT-PATHCALLS TO TRUE
    ws.setHasNotPathcalls(true);
    // COB(2750): SET HAS-PATHCALLS-ERROR TO TRUE
    ws.setHasPathcallsError(true);
    // COB(2751): MOVE WS-PATHCALL-LEN TO WS-INIT-PATHCALL-LEN
    ws.wsInitPathcallLen.setValue(ws.wsPathcallLen);
    // COB(2752): MOVE ZERO TO WS-PATHCALL-LEN
    ws.wsPathcallLen.setValue(0);
    // COB(2753): MOVE WS-SEGMENT-LEN TO WS-SAVE-SEGM-LEN
    ws.wsSaveSegmLen.setValue(ws.wsSegmentLen);
    // COB(2755): IF IS-PATHCALL-REVERSE
    if (ws.isPathcallReverse()) {
      // COB(2759): MOVE PAUTDTL1(1:PAUTDTL1-LEN)
      // COB(2759):   TO WS-PATHCALL-AREA(WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
      //       *
      //       *      ADD PAUTDTL1
      //       *
      ws.wsPathcallArea.setValue(
          ws.pautdtl1copy.pautdtl1, 0, ws.wsPathcallLen.getInt(), ws.pautdtl1Len.getInt());
      // COB(2761): ADD PAUTDTL1-LEN TO WS-PATHCALL-LEN
      ws.wsPathcallLen.add(ws.pautdtl1Len);
      // COB(2765): IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
      //       *
      //       *      SEGMENT: PAUTSUM0
      //       *
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .irisCcodeValues
          .irisCodePathcall()) {
        // COB(2766): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
        // COB(2766):                                    WS-PACKED-FLD-18-00(1)
        ws.filler174
            .wsPackedFld1800AtIndex(0)
            .setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
        // COB(2768): MOVE WS-PACKED-FLD-CHR(1)(5:6)
        // COB(2768):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
        params
            .irisDbPcb
            .ioRtnUsedKeysPcbArea
            .ioRtnUsedKeysArea
            .ioRtnUsedKeyValueAtIndex(0)
            .ioRtnUsedSsaKeys
            .ioRtnUsedKeyAlpha
            .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
        // COB(2771): PERFORM PAUTSUM0-SELECT-PRIMARY-KEY
        pautsum0SelectPrimaryKey();
        // COB(2773): IF IRIS-SQLCODE NOT = ZERO
        if (!params.irisWorkArea.irisSqlError.irisSqlcode.equals(0)) {
          // COB(2774): IF WS-PATHCALL-LEN > ZERO
          if (ws.wsPathcallLen.greaterThan(0)) {
            // COB(2775): MOVE WS-PATHCALL-LEN TO WS-SEGMENT-LEN
            ws.wsSegmentLen.setValue(ws.wsPathcallLen);
            // COB(2776): MOVE WS-PATHCALL-AREA(1:WS-SEGMENT-LEN)
            // COB(2776):                  TO LK-IO-AREA(1:WS-SEGMENT-LEN)
            params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsSegmentLen.getInt());
            // COB(2778): END-IF
          }
          // COB(2779): GO TO PAUTDTL1-READ-PATHCALL-EX
          return;
          // COB(2780): END-IF
        }
        // COB(2782): MOVE PAUTSUM0(1:PAUTSUM0-LEN) TO
        // COB(2782):   WS-PATHCALL-AREA(WS-PATHCALL-LEN + 1:PAUTSUM0-LEN)
        ws.wsPathcallArea.setValue(
            ws.pautsum0copy.pautsum0, 0, ws.wsPathcallLen.getInt(), ws.pautsum0Len.getInt());
        // COB(2784): ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
        ws.wsPathcallLen.add(ws.pautsum0Len);
        // COB(2786): END-IF
      }
      // COB(2788): ELSE
    } else {
      // COB(2791): IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
      //       *      SEGMENT: PAUTSUM0
      //       *
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .irisCcodeValues
          .irisCodePathcall()) {
        // COB(2792): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
        // COB(2792):                                    WS-PACKED-FLD-18-00(1)
        ws.filler174
            .wsPackedFld1800AtIndex(0)
            .setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
        // COB(2794): MOVE WS-PACKED-FLD-CHR(1)(5:6)
        // COB(2794):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
        params
            .irisDbPcb
            .ioRtnUsedKeysPcbArea
            .ioRtnUsedKeysArea
            .ioRtnUsedKeyValueAtIndex(0)
            .ioRtnUsedSsaKeys
            .ioRtnUsedKeyAlpha
            .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
        // COB(2797): PERFORM PAUTSUM0-SELECT-PRIMARY-KEY
        pautsum0SelectPrimaryKey();
        // COB(2799): IF IRIS-SQLCODE NOT = ZERO
        if (!params.irisWorkArea.irisSqlError.irisSqlcode.equals(0)) {
          // COB(2800): IF WS-PATHCALL-LEN > ZERO
          if (ws.wsPathcallLen.greaterThan(0)) {
            // COB(2801): MOVE WS-PATHCALL-LEN TO WS-SEGMENT-LEN
            ws.wsSegmentLen.setValue(ws.wsPathcallLen);
            // COB(2802): MOVE WS-PATHCALL-AREA(1:WS-SEGMENT-LEN)
            // COB(2802):                  TO LK-IO-AREA(1:WS-SEGMENT-LEN)
            params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsSegmentLen.getInt());
            // COB(2804): END-IF
          }
          // COB(2805): GO TO PAUTDTL1-READ-PATHCALL-EX
          return;
          // COB(2806): END-IF
        }
        // COB(2808): MOVE PAUTSUM0(1:PAUTSUM0-LEN) TO
        // COB(2808):   WS-PATHCALL-AREA(WS-PATHCALL-LEN + 1:PAUTSUM0-LEN)
        ws.wsPathcallArea.setValue(
            ws.pautsum0copy.pautsum0, 0, ws.wsPathcallLen.getInt(), ws.pautsum0Len.getInt());
        // COB(2810): ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
        ws.wsPathcallLen.add(ws.pautsum0Len);
        // COB(2812): END-IF
      }
      // COB(2817): MOVE PAUTDTL1(1:PAUTDTL1-LEN)
      // COB(2817):   TO WS-PATHCALL-AREA(WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
      //       *
      //       *
      //       *      ADD PAUTDTL1
      //       *
      ws.wsPathcallArea.setValue(
          ws.pautdtl1copy.pautdtl1, 0, ws.wsPathcallLen.getInt(), ws.pautdtl1Len.getInt());
      // COB(2819): ADD PAUTDTL1-LEN TO WS-PATHCALL-LEN
      ws.wsPathcallLen.add(ws.pautdtl1Len);
      // COB(2820): END-IF
    }
    // COB(2824): MOVE WS-PATHCALL-LEN TO WS-SEGMENT-LEN
    //       *
    //       *    RETURN THE PATHCALL AREA
    //       *
    ws.wsSegmentLen.setValue(ws.wsPathcallLen);
    // COB(2825): MOVE WS-PATHCALL-AREA(1:WS-SEGMENT-LEN)
    // COB(2825):                           TO LK-IO-AREA(1:WS-SEGMENT-LEN)
    params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsSegmentLen.getInt());
    // COB(2828): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
    // COB(2828):                                        WS-PACKED-FLD-18-00(1)
    ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
    // COB(2830): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(2830):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(0)
        .ioRtnUsedSsaKeys
        .ioRtnUsedKeyAlpha
        .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
    // COB(2832): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
    // COB(2832):                                 IO-RTN-USED-KEY-ALPHA(2)(1:8)
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
    // COB(2846): MOVE 'PAUTDTL1-SELECT-FIRST' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-SELECT-FIRST");
    // COB(2848): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(2849): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(2850): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(2850):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler250
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(2852): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(2853): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(2853):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(0), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(2855): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2856): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler174.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(2857): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(2858): END-IF
    }
    // COB(2859): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(2859):                              PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(2861): COMPUTE WS-CK-LEN = 8
    ws.wsCkLen.setValue(8);
    // COB(2862): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(2862):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, ws.wsCkPos.getInt() - 1, 8);
    // COB(2864): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(2865): IF COMMAND-CODE-LAST
    if (ws.commandCodeLast()) {
      // COB(2866): EXEC SQL
      // COB(2866): SELECT
      // COB(2866):   KEY_PAUT9CTS
      // COB(2866):  ,PA_AUTH_ORIG_DATE
      // COB(2866):  ,PA_AUTH_ORIG_TIME
      // COB(2866):  ,PA_CARD_NUM
      // COB(2866):  ,PA_AUTH_TYPE
      // COB(2866):  ,PA_CARD_EXPIRY_DATE
      // COB(2866):  ,PA_MESSAGE_TYPE
      // COB(2866):  ,PA_MESSAGE_SOURCE
      // COB(2866):  ,PA_AUTH_ID_CODE
      // COB(2866):  ,PA_AUTH_RESP_CODE
      // COB(2866):  ,PA_AUTH_RESP_REASON
      // COB(2866):  ,PA_PROCESSING_CODE
      // COB(2866):  ,PA_TRANSACTION_AMT
      // COB(2866):  ,PA_APPROVED_AMT
      // COB(2866):  ,PA_MERCHANT_CATAGORY_CODE
      // COB(2866):  ,PA_ACQR_COUNTRY_CODE
      // COB(2866):  ,PA_POS_ENTRY_MODE
      // COB(2866):  ,PA_MERCHANT_ID
      // COB(2866):  ,PA_MERCHANT_NAME
      // COB(2866):  ,PA_MERCHANT_CITY
      // COB(2866):  ,PA_MERCHANT_STATE
      // COB(2866):  ,PA_MERCHANT_ZIP
      // COB(2866):  ,PA_TRANSACTION_ID
      // COB(2866):  ,PA_MATCH_STATUS
      // COB(2866):  ,PA_AUTH_FRAUD
      // COB(2866):  ,PA_FRAUD_RPT_DATE
      // COB(2866):  ,PAUTDTL1_FILLER
      // COB(2866):  ,PAUTSUM0_KEY_ACCNTID
      // COB(2866): INTO
      // COB(2866):   :PAUTDTL1.KEY-PAUT9CTS
      // COB(2866):  ,:PAUTDTL1.PA-AUTH-ORIG-DATE
      // COB(2866):  ,:PAUTDTL1.PA-AUTH-ORIG-TIME
      // COB(2866):  ,:PAUTDTL1.PA-CARD-NUM
      // COB(2866):  ,:PAUTDTL1.PA-AUTH-TYPE
      // COB(2866):  ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
      // COB(2866):  ,:PAUTDTL1.PA-MESSAGE-TYPE
      // COB(2866):  ,:PAUTDTL1.PA-MESSAGE-SOURCE
      // COB(2866):  ,:PAUTDTL1.PA-AUTH-ID-CODE
      // COB(2866):  ,:PAUTDTL1.PA-AUTH-RESP-CODE
      // COB(2866):  ,:PAUTDTL1.PA-AUTH-RESP-REASON
      // COB(2866):  ,:PAUTDTL1.PA-PROCESSING-CODE
      // COB(2866):  ,:PAUTDTL1.PA-TRANSACTION-AMT
      // COB(2866):  ,:PAUTDTL1.PA-APPROVED-AMT
      // COB(2866):  ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
      // COB(2866):  ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
      // COB(2866):  ,:PAUTDTL1.PA-POS-ENTRY-MODE
      // COB(2866):  ,:PAUTDTL1.PA-MERCHANT-ID
      // COB(2866):  ,:PAUTDTL1.PA-MERCHANT-NAME
      // COB(2866):  ,:PAUTDTL1.PA-MERCHANT-CITY
      // COB(2866):  ,:PAUTDTL1.PA-MERCHANT-STATE
      // COB(2866):  ,:PAUTDTL1.PA-MERCHANT-ZIP
      // COB(2866):  ,:PAUTDTL1.PA-TRANSACTION-ID
      // COB(2866):  ,:PAUTDTL1.PA-MATCH-STATUS
      // COB(2866):  ,:PAUTDTL1.PA-AUTH-FRAUD
      // COB(2866):  ,:PAUTDTL1.PA-FRAUD-RPT-DATE
      // COB(2866):  ,:PAUTDTL1.PAUTDTL1-FILLER
      // COB(2866):  ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(2866): FROM
      // COB(2866): DBPAUTP0_PAUTDTL1 T1
      // COB(2866):   WHERE
      // COB(2866):     T1.KEY_PAUT9CTS = (
      // COB(2866):     SELECT
      // COB(2866):       T2.KEY_PAUT9CTS
      // COB(2866):     FROM
      // COB(2866):       DBPAUTP0_PAUTDTL1 T2
      // COB(2866):     WHERE
      // COB(2866):       T2.PAUTSUM0_KEY_ACCNTID =
      // COB(2866):     :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(2866):     AND
      // COB(2866):       T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID
      // COB(2866):     ORDER BY 1 DESC LIMIT 1
      // COB(2866):     )
      // COB(2866):   AND
      // COB(2866):     T1.PAUTSUM0_KEY_ACCNTID =
      // COB(2866):   :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(2866): END-EXEC
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
      // COB(2944): ELSE
    } else {
      // COB(2945): EXEC SQL
      // COB(2945):   SELECT
      // COB(2945):     KEY_PAUT9CTS
      // COB(2945):    ,PA_AUTH_ORIG_DATE
      // COB(2945):    ,PA_AUTH_ORIG_TIME
      // COB(2945):    ,PA_CARD_NUM
      // COB(2945):    ,PA_AUTH_TYPE
      // COB(2945):    ,PA_CARD_EXPIRY_DATE
      // COB(2945):    ,PA_MESSAGE_TYPE
      // COB(2945):    ,PA_MESSAGE_SOURCE
      // COB(2945):    ,PA_AUTH_ID_CODE
      // COB(2945):    ,PA_AUTH_RESP_CODE
      // COB(2945):    ,PA_AUTH_RESP_REASON
      // COB(2945):    ,PA_PROCESSING_CODE
      // COB(2945):    ,PA_TRANSACTION_AMT
      // COB(2945):    ,PA_APPROVED_AMT
      // COB(2945):    ,PA_MERCHANT_CATAGORY_CODE
      // COB(2945):    ,PA_ACQR_COUNTRY_CODE
      // COB(2945):    ,PA_POS_ENTRY_MODE
      // COB(2945):    ,PA_MERCHANT_ID
      // COB(2945):    ,PA_MERCHANT_NAME
      // COB(2945):    ,PA_MERCHANT_CITY
      // COB(2945):    ,PA_MERCHANT_STATE
      // COB(2945):    ,PA_MERCHANT_ZIP
      // COB(2945):    ,PA_TRANSACTION_ID
      // COB(2945):    ,PA_MATCH_STATUS
      // COB(2945):    ,PA_AUTH_FRAUD
      // COB(2945):    ,PA_FRAUD_RPT_DATE
      // COB(2945):    ,PAUTDTL1_FILLER
      // COB(2945):    ,PAUTSUM0_KEY_ACCNTID
      // COB(2945):   INTO
      // COB(2945):     :PAUTDTL1.KEY-PAUT9CTS
      // COB(2945):    ,:PAUTDTL1.PA-AUTH-ORIG-DATE
      // COB(2945):    ,:PAUTDTL1.PA-AUTH-ORIG-TIME
      // COB(2945):    ,:PAUTDTL1.PA-CARD-NUM
      // COB(2945):    ,:PAUTDTL1.PA-AUTH-TYPE
      // COB(2945):    ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
      // COB(2945):    ,:PAUTDTL1.PA-MESSAGE-TYPE
      // COB(2945):    ,:PAUTDTL1.PA-MESSAGE-SOURCE
      // COB(2945):    ,:PAUTDTL1.PA-AUTH-ID-CODE
      // COB(2945):    ,:PAUTDTL1.PA-AUTH-RESP-CODE
      // COB(2945):    ,:PAUTDTL1.PA-AUTH-RESP-REASON
      // COB(2945):    ,:PAUTDTL1.PA-PROCESSING-CODE
      // COB(2945):    ,:PAUTDTL1.PA-TRANSACTION-AMT
      // COB(2945):    ,:PAUTDTL1.PA-APPROVED-AMT
      // COB(2945):    ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
      // COB(2945):    ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
      // COB(2945):    ,:PAUTDTL1.PA-POS-ENTRY-MODE
      // COB(2945):    ,:PAUTDTL1.PA-MERCHANT-ID
      // COB(2945):    ,:PAUTDTL1.PA-MERCHANT-NAME
      // COB(2945):    ,:PAUTDTL1.PA-MERCHANT-CITY
      // COB(2945):    ,:PAUTDTL1.PA-MERCHANT-STATE
      // COB(2945):    ,:PAUTDTL1.PA-MERCHANT-ZIP
      // COB(2945):    ,:PAUTDTL1.PA-TRANSACTION-ID
      // COB(2945):    ,:PAUTDTL1.PA-MATCH-STATUS
      // COB(2945):    ,:PAUTDTL1.PA-AUTH-FRAUD
      // COB(2945):    ,:PAUTDTL1.PA-FRAUD-RPT-DATE
      // COB(2945):    ,:PAUTDTL1.PAUTDTL1-FILLER
      // COB(2945):    ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(2945):   FROM
      // COB(2945):   DBPAUTP0_PAUTDTL1 T1
      // COB(2945):   WHERE
      // COB(2945):     T1.KEY_PAUT9CTS = (
      // COB(2945):     SELECT
      // COB(2945):       T2.KEY_PAUT9CTS
      // COB(2945):     FROM
      // COB(2945):       DBPAUTP0_PAUTDTL1 T2
      // COB(2945):     WHERE
      // COB(2945):       T2.PAUTSUM0_KEY_ACCNTID =
      // COB(2945):     :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(2945):     AND
      // COB(2945):       T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID
      // COB(2945):     ORDER BY 1 LIMIT 1
      // COB(2945):     )
      // COB(2945):   AND
      // COB(2945):     T1.PAUTSUM0_KEY_ACCNTID =
      // COB(2945):   :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(2945): END-EXEC
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
      // COB(3023): END-IF
    }
    // COB(3025): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(3027): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(3028): EVALUATE TRUE
    // COB(3029): WHEN IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(3030): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
      // COB(3030):                                      WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(3032): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(3032):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(3034): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
      // COB(3034):                               IO-RTN-USED-KEY-ALPHA(2)(1:8)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 8);
      // COB(3036): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
      // COB(3036):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(3038): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(3038):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(3040): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
      // COB(3040):                                      TO WS-FB-KEY-AREA(7:8)
      ws.wsFbKeyArea.setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, 6, 8);
      // COB(3042): MOVE 14 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(14);
      // COB(3046): MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
      // COB(3046): WS-PACKED-FLD-18-00(1)
      //       *
      //       * PACKED FIELDS VS SIGNED ZONED FITTING
      //       *
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCode);
      // COB(3048): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(3048): PA-PROCESSING-CODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
      // COB(3050): MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
      // COB(3050): WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryMode);
      // COB(3052): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(3052): PA-POS-ENTRY-MODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
      // COB(3054): MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(ws.pautdtl1Len);
      // COB(3055): IF HAS-PATHCALLS
      if (ws.hasPathcalls()) {
        // COB(3056): PERFORM PAUTDTL1-READ-PATHCALL
        pautdtl1ReadPathcall();
        // COB(3057): ELSE
      } else {
        // COB(3058): PERFORM PAUTDTL1-SET-IO-AREA
        pautdtl1SetIoArea();
        // COB(3059): END-IF
      }
      // COB(3060): SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(3061): MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
      // COB(3061):                 IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
      ws.wsLastSegmentName.setString("PAUTDTL1");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTDTL1");
      // COB(3063): WHEN IRIS-SQL-NOT-FOUND
    } else if (ws.irissqlc.irisSqlNotFound()) {
      // COB(3064): MOVE SQLCODE TO WS-SAVE-ROUTINE-SQLCODE
      ws.wsSaveRoutineSqlcode.setValue(sqlca.sqlcode);
      // COB(3065): MOVE SQLCA TO WS-SAVE-ROUTINE-SQLCA
      ws.wsSaveRoutineSqlca.setValue(sqlca);
      // COB(3066): MOVE WS-SAVE-ROUTINE-SQLCODE TO SQLCODE
      sqlca.sqlcode.setValue(ws.wsSaveRoutineSqlcode);
      // COB(3067): MOVE WS-SAVE-ROUTINE-SQLCA TO SQLCA
      sqlca.setValue(ws.wsSaveRoutineSqlca);
      // COB(3068): END-EVALUATE
    }
    // COB(3070): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(3071): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    SELECT NEXT FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1SelectNext() {
    // COB(3082): MOVE 'PAUTDTL1-SELECT-NEXT' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-SELECT-NEXT");
    // COB(3084): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(3085): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(3086): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(3086):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler250
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(3088): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(3089): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(3089):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(0), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(3091): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(3092): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler174.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(3093): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(3094): END-IF
    }
    // COB(3095): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(3095):                              PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(3097): COMPUTE WS-CK-LEN = 8
    ws.wsCkLen.setValue(8);
    // COB(3098): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(3098):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, ws.wsCkPos.getInt() - 1, 8);
    // COB(3100): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(3101): MOVE IO-RTN-USED-KEY-ALPHA(2)(1:8) TO
    // COB(3101):                                 KEY-PAUT9CTS OF PAUTDTL1(1:8)
    ws.pautdtl1copy.pautdtl1.keyPaut9cts.setValue(
        params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(1)
            .ioRtnUsedSsaKeys
            .ioRtnUsedKeyAlpha,
        8);
    // COB(3103): IF COMMAND-CODE-LAST
    if (ws.commandCodeLast()) {
      // COB(3104): EXEC SQL
      // COB(3104): SELECT
      // COB(3104):   KEY_PAUT9CTS
      // COB(3104):  ,PA_AUTH_ORIG_DATE
      // COB(3104):  ,PA_AUTH_ORIG_TIME
      // COB(3104):  ,PA_CARD_NUM
      // COB(3104):  ,PA_AUTH_TYPE
      // COB(3104):  ,PA_CARD_EXPIRY_DATE
      // COB(3104):  ,PA_MESSAGE_TYPE
      // COB(3104):  ,PA_MESSAGE_SOURCE
      // COB(3104):  ,PA_AUTH_ID_CODE
      // COB(3104):  ,PA_AUTH_RESP_CODE
      // COB(3104):  ,PA_AUTH_RESP_REASON
      // COB(3104):  ,PA_PROCESSING_CODE
      // COB(3104):  ,PA_TRANSACTION_AMT
      // COB(3104):  ,PA_APPROVED_AMT
      // COB(3104):  ,PA_MERCHANT_CATAGORY_CODE
      // COB(3104):  ,PA_ACQR_COUNTRY_CODE
      // COB(3104):  ,PA_POS_ENTRY_MODE
      // COB(3104):  ,PA_MERCHANT_ID
      // COB(3104):  ,PA_MERCHANT_NAME
      // COB(3104):  ,PA_MERCHANT_CITY
      // COB(3104):  ,PA_MERCHANT_STATE
      // COB(3104):  ,PA_MERCHANT_ZIP
      // COB(3104):  ,PA_TRANSACTION_ID
      // COB(3104):  ,PA_MATCH_STATUS
      // COB(3104):  ,PA_AUTH_FRAUD
      // COB(3104):  ,PA_FRAUD_RPT_DATE
      // COB(3104):  ,PAUTDTL1_FILLER
      // COB(3104):  ,PAUTSUM0_KEY_ACCNTID
      // COB(3104): INTO
      // COB(3104):   :PAUTDTL1.KEY-PAUT9CTS
      // COB(3104):  ,:PAUTDTL1.PA-AUTH-ORIG-DATE
      // COB(3104):  ,:PAUTDTL1.PA-AUTH-ORIG-TIME
      // COB(3104):  ,:PAUTDTL1.PA-CARD-NUM
      // COB(3104):  ,:PAUTDTL1.PA-AUTH-TYPE
      // COB(3104):  ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
      // COB(3104):  ,:PAUTDTL1.PA-MESSAGE-TYPE
      // COB(3104):  ,:PAUTDTL1.PA-MESSAGE-SOURCE
      // COB(3104):  ,:PAUTDTL1.PA-AUTH-ID-CODE
      // COB(3104):  ,:PAUTDTL1.PA-AUTH-RESP-CODE
      // COB(3104):  ,:PAUTDTL1.PA-AUTH-RESP-REASON
      // COB(3104):  ,:PAUTDTL1.PA-PROCESSING-CODE
      // COB(3104):  ,:PAUTDTL1.PA-TRANSACTION-AMT
      // COB(3104):  ,:PAUTDTL1.PA-APPROVED-AMT
      // COB(3104):  ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
      // COB(3104):  ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
      // COB(3104):  ,:PAUTDTL1.PA-POS-ENTRY-MODE
      // COB(3104):  ,:PAUTDTL1.PA-MERCHANT-ID
      // COB(3104):  ,:PAUTDTL1.PA-MERCHANT-NAME
      // COB(3104):  ,:PAUTDTL1.PA-MERCHANT-CITY
      // COB(3104):  ,:PAUTDTL1.PA-MERCHANT-STATE
      // COB(3104):  ,:PAUTDTL1.PA-MERCHANT-ZIP
      // COB(3104):  ,:PAUTDTL1.PA-TRANSACTION-ID
      // COB(3104):  ,:PAUTDTL1.PA-MATCH-STATUS
      // COB(3104):  ,:PAUTDTL1.PA-AUTH-FRAUD
      // COB(3104):  ,:PAUTDTL1.PA-FRAUD-RPT-DATE
      // COB(3104):  ,:PAUTDTL1.PAUTDTL1-FILLER
      // COB(3104):  ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3104): FROM
      // COB(3104): DBPAUTP0_PAUTDTL1 T1
      // COB(3104):   WHERE
      // COB(3104):     T1.KEY_PAUT9CTS = (
      // COB(3104):     SELECT
      // COB(3104):       T2.KEY_PAUT9CTS
      // COB(3104):     FROM
      // COB(3104):       DBPAUTP0_PAUTDTL1 T2
      // COB(3104):     WHERE
      // COB(3104):       T2.KEY_PAUT9CTS >
      // COB(3104):       :PAUTDTL1.KEY-PAUT9CTS
      // COB(3104):     AND
      // COB(3104):       T2.PAUTSUM0_KEY_ACCNTID =
      // COB(3104):     :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3104):     AND
      // COB(3104):       T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID
      // COB(3104):     ORDER BY 1 DESC LIMIT 1
      // COB(3104):     )
      // COB(3104):   AND
      // COB(3104):     T1.PAUTSUM0_KEY_ACCNTID =
      // COB(3104):   :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3104): END-EXEC
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
      // COB(3185): ELSE
    } else {
      // COB(3186): EXEC SQL
      // COB(3186): SELECT
      // COB(3186):   KEY_PAUT9CTS
      // COB(3186):  ,PA_AUTH_ORIG_DATE
      // COB(3186):  ,PA_AUTH_ORIG_TIME
      // COB(3186):  ,PA_CARD_NUM
      // COB(3186):  ,PA_AUTH_TYPE
      // COB(3186):  ,PA_CARD_EXPIRY_DATE
      // COB(3186):  ,PA_MESSAGE_TYPE
      // COB(3186):  ,PA_MESSAGE_SOURCE
      // COB(3186):  ,PA_AUTH_ID_CODE
      // COB(3186):  ,PA_AUTH_RESP_CODE
      // COB(3186):  ,PA_AUTH_RESP_REASON
      // COB(3186):  ,PA_PROCESSING_CODE
      // COB(3186):  ,PA_TRANSACTION_AMT
      // COB(3186):  ,PA_APPROVED_AMT
      // COB(3186):  ,PA_MERCHANT_CATAGORY_CODE
      // COB(3186):  ,PA_ACQR_COUNTRY_CODE
      // COB(3186):  ,PA_POS_ENTRY_MODE
      // COB(3186):  ,PA_MERCHANT_ID
      // COB(3186):  ,PA_MERCHANT_NAME
      // COB(3186):  ,PA_MERCHANT_CITY
      // COB(3186):  ,PA_MERCHANT_STATE
      // COB(3186):  ,PA_MERCHANT_ZIP
      // COB(3186):  ,PA_TRANSACTION_ID
      // COB(3186):  ,PA_MATCH_STATUS
      // COB(3186):  ,PA_AUTH_FRAUD
      // COB(3186):  ,PA_FRAUD_RPT_DATE
      // COB(3186):  ,PAUTDTL1_FILLER
      // COB(3186):  ,PAUTSUM0_KEY_ACCNTID
      // COB(3186): INTO
      // COB(3186):   :PAUTDTL1.KEY-PAUT9CTS
      // COB(3186):  ,:PAUTDTL1.PA-AUTH-ORIG-DATE
      // COB(3186):  ,:PAUTDTL1.PA-AUTH-ORIG-TIME
      // COB(3186):  ,:PAUTDTL1.PA-CARD-NUM
      // COB(3186):  ,:PAUTDTL1.PA-AUTH-TYPE
      // COB(3186):  ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
      // COB(3186):  ,:PAUTDTL1.PA-MESSAGE-TYPE
      // COB(3186):  ,:PAUTDTL1.PA-MESSAGE-SOURCE
      // COB(3186):  ,:PAUTDTL1.PA-AUTH-ID-CODE
      // COB(3186):  ,:PAUTDTL1.PA-AUTH-RESP-CODE
      // COB(3186):  ,:PAUTDTL1.PA-AUTH-RESP-REASON
      // COB(3186):  ,:PAUTDTL1.PA-PROCESSING-CODE
      // COB(3186):  ,:PAUTDTL1.PA-TRANSACTION-AMT
      // COB(3186):  ,:PAUTDTL1.PA-APPROVED-AMT
      // COB(3186):  ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
      // COB(3186):  ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
      // COB(3186):  ,:PAUTDTL1.PA-POS-ENTRY-MODE
      // COB(3186):  ,:PAUTDTL1.PA-MERCHANT-ID
      // COB(3186):  ,:PAUTDTL1.PA-MERCHANT-NAME
      // COB(3186):  ,:PAUTDTL1.PA-MERCHANT-CITY
      // COB(3186):  ,:PAUTDTL1.PA-MERCHANT-STATE
      // COB(3186):  ,:PAUTDTL1.PA-MERCHANT-ZIP
      // COB(3186):  ,:PAUTDTL1.PA-TRANSACTION-ID
      // COB(3186):  ,:PAUTDTL1.PA-MATCH-STATUS
      // COB(3186):  ,:PAUTDTL1.PA-AUTH-FRAUD
      // COB(3186):  ,:PAUTDTL1.PA-FRAUD-RPT-DATE
      // COB(3186):  ,:PAUTDTL1.PAUTDTL1-FILLER
      // COB(3186):  ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3186): FROM
      // COB(3186): DBPAUTP0_PAUTDTL1 T1
      // COB(3186):   WHERE
      // COB(3186):     T1.KEY_PAUT9CTS = (
      // COB(3186):     SELECT
      // COB(3186):       T2.KEY_PAUT9CTS
      // COB(3186):     FROM
      // COB(3186):       DBPAUTP0_PAUTDTL1 T2
      // COB(3186):     WHERE
      // COB(3186):       T2.KEY_PAUT9CTS >
      // COB(3186):       :PAUTDTL1.KEY-PAUT9CTS
      // COB(3186):     AND
      // COB(3186):       T2.PAUTSUM0_KEY_ACCNTID =
      // COB(3186):     :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3186):     AND
      // COB(3186):       T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID
      // COB(3186):     ORDER BY 1 LIMIT 1
      // COB(3186):     )
      // COB(3186):   AND
      // COB(3186):     T1.PAUTSUM0_KEY_ACCNTID =
      // COB(3186):   :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
      // COB(3186): END-EXEC
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
      // COB(3267): END-IF
    }
    // COB(3269): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(3270): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(3271): EVALUATE TRUE
    // COB(3272): WHEN IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(3273): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
      // COB(3273):                                      WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(3275): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(3275):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(3277): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
      // COB(3277):                               IO-RTN-USED-KEY-ALPHA(2)(1:8)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 8);
      // COB(3279): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
      // COB(3279):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(3281): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(3281):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(3283): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
      // COB(3283):                                      TO WS-FB-KEY-AREA(7:8)
      ws.wsFbKeyArea.setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, 6, 8);
      // COB(3285): MOVE 14 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(14);
      // COB(3289): MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
      // COB(3289): WS-PACKED-FLD-18-00(1)
      //       *
      //       * PACKED FIELDS VS SIGNED ZONED FITTING
      //       *
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCode);
      // COB(3291): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(3291): PA-PROCESSING-CODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
      // COB(3293): MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
      // COB(3293): WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryMode);
      // COB(3295): MOVE WS-PACKED-FLD-18-00(1) TO
      // COB(3295): PA-POS-ENTRY-MODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
      // COB(3297): MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(ws.pautdtl1Len);
      // COB(3298): IF HAS-PATHCALLS
      if (ws.hasPathcalls()) {
        // COB(3299): PERFORM PAUTDTL1-READ-PATHCALL
        pautdtl1ReadPathcall();
        // COB(3300): ELSE
      } else {
        // COB(3301): PERFORM PAUTDTL1-SET-IO-AREA
        pautdtl1SetIoArea();
        // COB(3302): END-IF
      }
      // COB(3303): SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(3304): MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
      // COB(3304):                 IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
      ws.wsLastSegmentName.setString("PAUTDTL1");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTDTL1");
      // COB(3306): WHEN IRIS-SQL-NOT-FOUND
    } else if (ws.irissqlc.irisSqlNotFound()) {
      // COB(3307): MOVE SQLCODE TO WS-SAVE-ROUTINE-SQLCODE
      ws.wsSaveRoutineSqlcode.setValue(sqlca.sqlcode);
      // COB(3308): MOVE SQLCA TO WS-SAVE-ROUTINE-SQLCA
      ws.wsSaveRoutineSqlca.setValue(sqlca);
      // COB(3309): MOVE WS-SAVE-ROUTINE-SQLCODE TO SQLCODE
      sqlca.sqlcode.setValue(ws.wsSaveRoutineSqlcode);
      // COB(3310): MOVE WS-SAVE-ROUTINE-SQLCA TO SQLCA
      sqlca.setValue(ws.wsSaveRoutineSqlca);
      // COB(3311): END-EVALUATE
    }
    // COB(3313): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(3314): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    DYNAMIC SELECT FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1SelectDynamic() {
    // COB(3325): MOVE 'PAUTDTL1-SELECT-DYNAMIC' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-SELECT-DYNAMIC");
    // COB(3327): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(3328): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(3329): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(3329):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler250
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(3331): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(3332): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(3332):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(0), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(3334): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(3335): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler174.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(3336): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(3337): END-IF
    }
    // COB(3338): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(3338):                              PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(3340): COMPUTE WS-CK-LEN = 8
    ws.wsCkLen.setValue(8);
    // COB(3341): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(3341):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, ws.wsCkPos.getInt() - 1, 8);
    // COB(3343): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(3346): MOVE ZERO TO WS-WHERE-LEN WS-ORDERBY-LEN
    //       *    PREPARE THE WHERE CONDITION IF ANY
    //       *
    ws.wsWhereLen.setValue(0);
    ws.wsOrderbyLen.setValue(0);
    // COB(3347): IF SQL-CONDITION-CLAUSE-LENGTH > 0
    if (ws.sqlConditionClause.sqlConditionClauseLength.greaterThan(0)) {
      // COB(3348): SUBTRACT 1 FROM SQL-CONDITION-CLAUSE-LENGTH
      ws.sqlConditionClause.sqlConditionClauseLength.subtract(1);
      // COB(3349): STRING 'WHERE '
      // COB(3349): SQL-CONDITION-CLAUSE-TEXT(1:SQL-CONDITION-CLAUSE-LENGTH)
      // COB(3349): ' ' DELIMITED BY SIZE INTO WS-WHERE
      ws.wsWhere.concat(
          "WHERE ",
          ws.sqlConditionClause.sqlConditionClauseText.getString(
              0, ws.sqlConditionClause.sqlConditionClauseLength.getInt()),
          " ");
      // COB(3352): COMPUTE WS-WHERE-LEN = SQL-CONDITION-CLAUSE-LENGTH + 7
      ws.wsWhereLen.setValue(ws.sqlConditionClause.sqlConditionClauseLength.getInt() + 7);
      // COB(3353): END-IF
    }
    // COB(3355): IF SQL-ORDERBY-CLAUSE-LENGTH > 0
    if (ws.sqlOrderbyClause.sqlOrderbyClauseLength.greaterThan(0)) {
      // COB(3356): SUBTRACT 1 FROM SQL-ORDERBY-CLAUSE-LENGTH
      ws.sqlOrderbyClause.sqlOrderbyClauseLength.subtract(1);
      // COB(3357): MOVE SQL-ORDERBY-CLAUSE-TEXT(1:SQL-ORDERBY-CLAUSE-LENGTH)
      // COB(3357):                TO WS-ORDERBY(1:SQL-ORDERBY-CLAUSE-LENGTH)
      ws.wsOrderby.setValue(
          ws.sqlOrderbyClause.sqlOrderbyClauseText,
          ws.sqlOrderbyClause.sqlOrderbyClauseLength.getInt());
      // COB(3359): MOVE SQL-ORDERBY-CLAUSE-LENGTH TO WS-ORDERBY-LEN
      ws.wsOrderbyLen.setValue(ws.sqlOrderbyClause.sqlOrderbyClauseLength);
      // COB(3360): ELSE
    } else {
      // COB(3361): COMPUTE WS-ORDERBY-LEN = 1
      ws.wsOrderbyLen.setValue(1);
      // COB(3362): STRING ' ORDER BY '
      // COB(3362):       ' DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID, '
      // COB(3362):       ' KEY_PAUT9CTS '
      // COB(3362): DELIMITED BY SIZE INTO WS-ORDERBY POINTER WS-ORDERBY-LEN
      ws.wsOrderbyLen.setValue(
          ws.wsOrderby.concat(
              " ORDER BY ", " DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID, ", " KEY_PAUT9CTS "));
      // COB(3366): SUBTRACT 1 FROM WS-ORDERBY-LEN
      ws.wsOrderbyLen.subtract(1);
      // COB(3367): END-IF
    }
    // COB(3369): IF COMMAND-CODE-LAST
    if (ws.commandCodeLast()) {
      // COB(3370): STRING WS-ORDERBY(1:WS-ORDERBY-LEN)
      // COB(3370): ' DESC ' DELIMITED BY SIZE
      // COB(3370): INTO WS-ORDERBY
      ws.wsOrderby.concat(ws.wsOrderby.getString(0, ws.wsOrderbyLen.getInt()), " DESC ");
      // COB(3373): ADD 6 TO WS-ORDERBY-LEN
      ws.wsOrderbyLen.add(6);
      // COB(3374): END-IF
    }
    // COB(3379): MOVE 1 TO WS-SQL-STM-LEN
    //       *
    //       *
    //       *    PREPARE THE DYNAMIC QUERY
    //       *
    ws.wsSqlStm.wsSqlStmLen.setValue(1);
    // COB(3380): STRING
    // COB(3380): 'SELECT '
    // COB(3380): DELIMITED BY SIZE
    // COB(3380): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
    ws.wsSqlStm.wsSqlStmLen.setValue(ws.wsSqlStm.wsSqlStmTxt.concat("SELECT "));
    // COB(3384): IF SQL-JOIN-CLAUSE-LENGTH > 0
    if (ws.sqlJoinClause.sqlJoinClauseLength.greaterThan(0)) {
      // COB(3385): STRING 'DBPAUTP0_PAUTSUM0.KEY_ACCNTID, '
      // COB(3385): DELIMITED BY SIZE
      // COB(3385): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat("DBPAUTP0_PAUTSUM0.KEY_ACCNTID, "));
      // COB(3388): ELSE
    } else {
      // COB(3389): STRING '''NULL'', '
      // COB(3389): DELIMITED BY SIZE
      // COB(3389): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(ws.wsSqlStm.wsSqlStmTxt.concat("''NULL'', "));
      // COB(3392): END-IF
    }
    // COB(3393): STRING
    // COB(3393):   'DBPAUTP0_PAUTDTL1.KEY_PAUT9CTS '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_AUTH_ORIG_DATE '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_AUTH_ORIG_TIME '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_CARD_NUM '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_AUTH_TYPE '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_CARD_EXPIRY_DATE '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_MESSAGE_TYPE '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_MESSAGE_SOURCE '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_AUTH_ID_CODE '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_AUTH_RESP_CODE '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_AUTH_RESP_REASON '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_PROCESSING_CODE '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_TRANSACTION_AMT '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_APPROVED_AMT '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_CATAGORY_CODE '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_ACQR_COUNTRY_CODE '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_POS_ENTRY_MODE '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_ID '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_NAME '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_CITY '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_STATE '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_ZIP '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_TRANSACTION_ID '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_MATCH_STATUS '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_AUTH_FRAUD '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PA_FRAUD_RPT_DATE '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PAUTDTL1_FILLER '
    // COB(3393):  ',DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID '
    // COB(3393): 'FROM '
    // COB(3393): 'DBPAUTP0_PAUTDTL1 '
    // COB(3393): DELIMITED BY SIZE
    // COB(3393): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
    ws.wsSqlStm.wsSqlStmLen.setValue(
        ws.wsSqlStm.wsSqlStmTxt.concat(
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
    // COB(3426): IF SQL-JOIN-CLAUSE-LENGTH > 0
    if (ws.sqlJoinClause.sqlJoinClauseLength.greaterThan(0)) {
      // COB(3427): SUBTRACT 1 FROM SQL-JOIN-CLAUSE-LENGTH
      ws.sqlJoinClause.sqlJoinClauseLength.subtract(1);
      // COB(3428): STRING SQL-JOIN-CLAUSE-TEXT(1:SQL-JOIN-CLAUSE-LENGTH) ' '
      // COB(3428): DELIMITED BY SIZE
      // COB(3428): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(
              ws.sqlJoinClause.sqlJoinClauseText.getString(
                  0, ws.sqlJoinClause.sqlJoinClauseLength.getInt()),
              " "));
      // COB(3431): END-IF
    }
    // COB(3432): IF WS-WHERE-LEN > 0
    if (ws.wsWhereLen.greaterThan(0)) {
      // COB(3433): STRING
      // COB(3433): WS-WHERE(1:WS-WHERE-LEN)
      // COB(3433): DELIMITED BY SIZE
      // COB(3433): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(ws.wsWhere.getString(0, ws.wsWhereLen.getInt())));
      // COB(3437): IF IRIS-TRACE-FULL
      if (params.irisWorkArea.irisTraceFull()) {
        // COB(3438): MOVE 0 TO IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
        // COB(3439): STRING '<IRISTRACE> - DBPAUTX0:SELECT-DYNAMIC' NL
        // COB(3439):      ' SECTION    =(PAUTDTL1-SELECT-DYNAMIC) ' NL
        // COB(3439):      ' CONDITION  =(' WS-WHERE(1:WS-WHERE-LEN) ')' NL
        // COB(3439):      ' ORDER BY   =(' WS-ORDERBY(1:WS-ORDERBY-LEN) ')'
        // COB(3439): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - DBPAUTX0:SELECT-DYNAMIC",
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
        // COB(3444): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(3445): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(3446): END-IF
      }
      // COB(3447): END-IF
    }
    // COB(3448): IF WS-ORDERBY-LEN > 0
    if (ws.wsOrderbyLen.greaterThan(0)) {
      // COB(3449): STRING WS-ORDERBY(1:WS-ORDERBY-LEN) DELIMITED BY SIZE
      // COB(3449): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(ws.wsOrderby.getString(0, ws.wsOrderbyLen.getInt())));
      // COB(3451): END-IF
    }
    // COB(3452): STRING ' FETCH FIRST 1 ROW ONLY '
    // COB(3452): DELIMITED BY SIZE
    // COB(3452): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
    ws.wsSqlStm.wsSqlStmLen.setValue(ws.wsSqlStm.wsSqlStmTxt.concat(" FETCH FIRST 1 ROW ONLY "));
    // COB(3455): SUBTRACT 1 FROM WS-SQL-STM-LEN
    ws.wsSqlStm.wsSqlStmLen.subtract(1);
    // COB(3459): EXEC SQL
    // COB(3459):   DECLARE PAUTDTL1_CRS CURSOR
    // COB(3459):   FOR PAUTDTL1_STATEMENT
    // COB(3459): END-EXEC
    //       *
    //       *    DECLARE THE DYNAMIC CURSOR
    //       *
    // do nothing
    // COB(3466): EXEC SQL
    // COB(3466):   DECLARE PAUTDTL1_STATEMENT STATEMENT
    // COB(3466): END-EXEC
    //       *
    //       *    DECLARE THE SQL STATEMENT
    //       *
    // do nothing
    // COB(3472): EXEC SQL
    // COB(3472):   PREPARE PAUTDTL1_STATEMENT
    // COB(3472):   INTO :SQLDA
    // COB(3472):   FROM :WS-SQL-STM
    // COB(3472): END-EXEC
    //       *
    //       *    PREPARE THE STATEMENT
    //       *
    pautdtl1Crs = new NSqlCursor(this, sqlca, ws.wsSqlStm.toString());
    // COB(3480): IF SQLCODE NOT = ZERO
    //       *
    //       *    TEST THE RETURN CODE
    //       *
    if (!sqlca.sqlcode.equals(0)) {
      // COB(3481): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(3482): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(3483): GO TO PAUTDTL1-SELECT-DYNAMIC-EX
      return;
      // COB(3484): END-IF
    }
    // COB(3488): EXEC SQL
    // COB(3488):   OPEN PAUTDTL1_CRS
    // COB(3488): END-EXEC
    //       *
    //       *    OPEN THE DYNAMIC CURSOR
    //       *
    pautdtl1Crs.open();
    // COB(3494): IF SQLCODE NOT = ZERO
    //       *
    //       *    TEST THE RETURN CODE
    //       *
    if (!sqlca.sqlcode.equals(0)) {
      // COB(3495): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(3496): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(3497): GO TO PAUTDTL1-SELECT-DYNAMIC-EX
      return;
      // COB(3498): END-IF
    }
    // COB(3502): EXEC SQL
    // COB(3502):   FETCH PAUTDTL1_CRS
    // COB(3502):   INTO
    // COB(3502):     :PAUTSUM0.KEY-ACCNTID,
    // COB(3502):     :PAUTDTL1.KEY-PAUT9CTS
    // COB(3502):    ,:PAUTDTL1.PA-AUTH-ORIG-DATE
    // COB(3502):    ,:PAUTDTL1.PA-AUTH-ORIG-TIME
    // COB(3502):    ,:PAUTDTL1.PA-CARD-NUM
    // COB(3502):    ,:PAUTDTL1.PA-AUTH-TYPE
    // COB(3502):    ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
    // COB(3502):    ,:PAUTDTL1.PA-MESSAGE-TYPE
    // COB(3502):    ,:PAUTDTL1.PA-MESSAGE-SOURCE
    // COB(3502):    ,:PAUTDTL1.PA-AUTH-ID-CODE
    // COB(3502):    ,:PAUTDTL1.PA-AUTH-RESP-CODE
    // COB(3502):    ,:PAUTDTL1.PA-AUTH-RESP-REASON
    // COB(3502):    ,:PAUTDTL1.PA-PROCESSING-CODE
    // COB(3502):    ,:PAUTDTL1.PA-TRANSACTION-AMT
    // COB(3502):    ,:PAUTDTL1.PA-APPROVED-AMT
    // COB(3502):    ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
    // COB(3502):    ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
    // COB(3502):    ,:PAUTDTL1.PA-POS-ENTRY-MODE
    // COB(3502):    ,:PAUTDTL1.PA-MERCHANT-ID
    // COB(3502):    ,:PAUTDTL1.PA-MERCHANT-NAME
    // COB(3502):    ,:PAUTDTL1.PA-MERCHANT-CITY
    // COB(3502):    ,:PAUTDTL1.PA-MERCHANT-STATE
    // COB(3502):    ,:PAUTDTL1.PA-MERCHANT-ZIP
    // COB(3502):    ,:PAUTDTL1.PA-TRANSACTION-ID
    // COB(3502):    ,:PAUTDTL1.PA-MATCH-STATUS
    // COB(3502):    ,:PAUTDTL1.PA-AUTH-FRAUD
    // COB(3502):    ,:PAUTDTL1.PA-FRAUD-RPT-DATE
    // COB(3502):    ,:PAUTDTL1.PAUTDTL1-FILLER
    // COB(3502):    ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
    // COB(3502): END-EXEC
    //       *
    //       *    FETCH THE DYNAMIC CURSOR
    //       *
    pautdtl1Crs.fetch(
        ws.pautsum0copy.pautsum0.keyAccntid,
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
    // COB(3536): IF SQLCODE NOT = ZERO
    if (!sqlca.sqlcode.equals(0)) {
      // COB(3537): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(3538): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(3542): EXEC SQL
      // COB(3542):   CLOSE PAUTDTL1_CRS
      // COB(3542): END-EXEC
      //       *
      //       *      CLOSE THE DYNAMIC CURSOR
      //       *
      pautdtl1Crs.close();
      // COB(3546): MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
      ws.irissqlc.irisDbSqlcode.setValue(params.irisWorkArea.irisSqlError.irisSqlcode);
      // COB(3547): IF IRIS-SQL-NOT-FOUND
      // COB(3547): AND HAS-PATHCALLS
      // COB(3547): AND HAS-PATHCALLS-ERROR
      // COB(3547): AND SQL-SELECT-DYNAMIC
      if (ws.irissqlc.irisSqlNotFound()
          && ws.hasPathcalls()
          && ws.hasPathcallsError()
          && params.irisWorkArea.sqlSelectDynamic()) {
        // COB(3551): SET IRIS-ERR-RTN-UNHANDLED-ACCESS TO TRUE
        params.irisWorkArea.setIrisErrRtnUnhandledAccess(true);
        // COB(3552): MOVE 0 TO IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
        // COB(3553): STRING '<IRISTRACE> - DBPAUTX0:SELECT-DYNAMIC' NL
        // COB(3553):      ' SECTION    =(PAUTDTL1-SELECT-DYNAMIC) ' NL
        // COB(3553): ' ERROR      =(PATHCALL ACCESS NOT SUPPORTED FOR RC=GE)'
        // COB(3553): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - DBPAUTX0:SELECT-DYNAMIC",
            ws.iriscomm.nl,
            " SECTION    =(PAUTDTL1-SELECT-DYNAMIC) ",
            ws.iriscomm.nl,
            " ERROR      =(PATHCALL ACCESS NOT SUPPORTED FOR RC=GE)",
            ws.iriscomm.messageEnd);
        // COB(3557): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(3558): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(3559): MOVE -1 TO IRIS-SQLCODE
        params.irisWorkArea.irisSqlError.irisSqlcode.setValue(-1);
        // COB(3560): END-IF
      }
      // COB(3562): GO TO PAUTDTL1-SELECT-DYNAMIC-EX
      return;
      // COB(3563): END-IF
    }
    // COB(3565): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(3566): MOVE SQLERRM TO IRIS-SQLERRM
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
    // COB(3570): EXEC SQL
    // COB(3570):   CLOSE PAUTDTL1_CRS
    // COB(3570): END-EXEC
    //       *
    //       *      CLOSE THE DYNAMIC CURSOR
    //       *
    pautdtl1Crs.close();
    // COB(3574): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(3576): MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(params.irisWorkArea.irisSqlError.irisSqlcode);
    // COB(3577): EVALUATE TRUE
    // COB(3578): WHEN IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(3579): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
      // COB(3579):                                      WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(3581): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(3581):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(3583): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
      // COB(3583):                               IO-RTN-USED-KEY-ALPHA(2)(1:8)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 8);
      // COB(3586): INSPECT WS-WHERE TALLYING WS-INDEX FOR
      // COB(3586): ALL 'KEY_ACCNTID'
      //       *      STORE THE SECONDARY INDEX JUST READ
      ws.wsIndex.setValue(ws.wsWhere.countStrings("KEY_ACCNTID"));
      // COB(3588): IF WS-INDEX > 0
      if (ws.wsIndex.greaterThan(0)) {
        // COB(3589): MOVE KEY-ACCNTID OF PAUTSUM0
        // COB(3589):   TO WS-PACKED-FLD-18-00(1)
        ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
        // COB(3591): MOVE WS-PACKED-FLD-CHR(1)(1:6)
        // COB(3591):   TO IO-RTN-USED-KEY-SECONDARY(1:6)
        // COB(3591):      WS-FB-KEY-AREA(1:6)
        params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeySecondary.setValue(
            ws.filler250.wsPackedFldChrAtIndex(0), 6);
        ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 6);
        // COB(3594): MOVE 6 TO WS-FB-KEY-LENGTH
        ws.wsFbKeyLength.setValue(6);
        // COB(3595): END-IF
      }
      // COB(3596): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
      // COB(3596):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(3598): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(3598):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(3600): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
      // COB(3600):                                      TO WS-FB-KEY-AREA(7:8)
      ws.wsFbKeyArea.setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, 6, 8);
      // COB(3602): MOVE 14 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(14);
      // COB(3603): IF SQL-SELECT-DYNAMIC
      // COB(3603): AND NOT (COMMAND-WITH-HOLD
      // COB(3603): AND (NOT SQL-SELECT-DYNAMIC
      // COB(3603):  OR (SQL-SELECT-DYNAMIC
      // COB(3603):      AND NOT HAS-PATHCALLS)))
      if (params.irisWorkArea.sqlSelectDynamic()
          && !(ws.commandWithHold()
              && (!params.irisWorkArea.sqlSelectDynamic()
                  || (params.irisWorkArea.sqlSelectDynamic() && !ws.hasPathcalls())))) {
        // COB(3611): MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
        // COB(3611): WS-PACKED-FLD-18-00(1)
        //       *
        //       * PACKED FIELDS VS SIGNED ZONED FITTING
        //       *
        ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCode);
        // COB(3613): MOVE WS-PACKED-FLD-18-00(1) TO
        // COB(3613): PA-PROCESSING-CODE-N OF PAUTDTL1
        ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
        // COB(3615): MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
        // COB(3615): WS-PACKED-FLD-18-00(1)
        ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryMode);
        // COB(3617): MOVE WS-PACKED-FLD-18-00(1) TO
        // COB(3617): PA-POS-ENTRY-MODE-N OF PAUTDTL1
        ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
        // COB(3619): MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN
        ws.wsSegmentLen.setValue(ws.pautdtl1Len);
        // COB(3620): IF HAS-PATHCALLS
        if (ws.hasPathcalls()) {
          // COB(3621): PERFORM PAUTDTL1-READ-PATHCALL
          pautdtl1ReadPathcall();
          // COB(3622): ELSE
        } else {
          // COB(3623): PERFORM PAUTDTL1-SET-IO-AREA
          pautdtl1SetIoArea();
          // COB(3624): END-IF
        }
        // COB(3625): END-IF
      }
      // COB(3626): SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(3627): MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
      // COB(3627):                 IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
      ws.wsLastSegmentName.setString("PAUTDTL1");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTDTL1");
      // COB(3629): END-EVALUATE.
    }
  }

  /***
   ******************************************************************
   *    INSERT FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1Insert() {
    // COB(3640): MOVE 'PAUTDTL1-INSERT' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-INSERT");
    // COB(3642): IF HAS-PATHCALLS
    if (ws.hasPathcalls()) {
      // COB(3643): PERFORM PAUTDTL1-INSERT-PATHCALL
      pautdtl1InsertPathcall();
      // COB(3644): GO TO PAUTDTL1-INSERT-EX
      return;
      // COB(3645): END-IF
    }
    // COB(3647): INITIALIZE PAUTDTL1
    ws.pautdtl1copy.pautdtl1.initialize();
    // COB(3649): MOVE LK-IO-AREA(1:PAUTDTL1-LEN) TO PAUTDTL1(1:PAUTDTL1-LEN)
    ws.pautdtl1copy.pautdtl1.setValue(params.lkIoArea, ws.pautdtl1Len.getInt());
    // COB(3650): IF PA-PROCESSING-CODE-N
    // COB(3650): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paProcessingCodeN.isNumeric()) {
      // COB(3652): MOVE ZERO TO
      // COB(3652): PA-PROCESSING-CODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(0);
      // COB(3654): END-IF
    }
    // COB(3655): IF PA-TRANSACTION-AMT
    // COB(3655): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paTransactionAmt.isNumeric()) {
      // COB(3657): MOVE ZERO TO
      // COB(3657): PA-TRANSACTION-AMT OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paTransactionAmt.setValue(0);
      // COB(3659): END-IF
    }
    // COB(3660): IF PA-APPROVED-AMT
    // COB(3660): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paApprovedAmt.isNumeric()) {
      // COB(3662): MOVE ZERO TO
      // COB(3662): PA-APPROVED-AMT OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paApprovedAmt.setValue(0);
      // COB(3664): END-IF
    }
    // COB(3665): IF PA-POS-ENTRY-MODE-N
    // COB(3665): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paPosEntryModeN.isNumeric()) {
      // COB(3667): MOVE ZERO TO
      // COB(3667): PA-POS-ENTRY-MODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(0);
      // COB(3669): END-IF
    }
    // COB(3670): IF PAUTSUM0-KEY-ACCNTID
    // COB(3670): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.isNumeric()) {
      // COB(3672): MOVE ZERO TO
      // COB(3672): PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(0);
      // COB(3674): END-IF
    }
    // COB(3678): MOVE PA-PROCESSING-CODE-N OF PAUTDTL1 TO
    // COB(3678): WS-PACKED-FLD-18-00(1)
    //       *
    //       * SIGNED ZONED FIELDS VS PACKED FITTING
    //       *
    ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCodeN);
    // COB(3680): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(3680): PA-PROCESSING-CODE OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.paProcessingCode.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(3682): MOVE PA-POS-ENTRY-MODE-N OF PAUTDTL1 TO
    // COB(3682): WS-PACKED-FLD-18-00(1)
    ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryModeN);
    // COB(3684): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(3684): PA-POS-ENTRY-MODE OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.paPosEntryMode.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(3686): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(3687): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(3688): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(3688):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler250
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(3690): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(3691): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(3691):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(0), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(3693): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(3694): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler174.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(3695): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(3696): END-IF
    }
    // COB(3697): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(3697):                              PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(3699): COMPUTE WS-CK-LEN = 8
    ws.wsCkLen.setValue(8);
    // COB(3700): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(3700):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, ws.wsCkPos.getInt() - 1, 8);
    // COB(3702): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(3705): EXEC SQL
    // COB(3705):   INSERT INTO
    // COB(3705):     DBPAUTP0_PAUTDTL1
    // COB(3705):     (
    // COB(3705):     KEY_PAUT9CTS
    // COB(3705):    ,PA_AUTH_ORIG_DATE
    // COB(3705):    ,PA_AUTH_ORIG_TIME
    // COB(3705):    ,PA_CARD_NUM
    // COB(3705):    ,PA_AUTH_TYPE
    // COB(3705):    ,PA_CARD_EXPIRY_DATE
    // COB(3705):    ,PA_MESSAGE_TYPE
    // COB(3705):    ,PA_MESSAGE_SOURCE
    // COB(3705):    ,PA_AUTH_ID_CODE
    // COB(3705):    ,PA_AUTH_RESP_CODE
    // COB(3705):    ,PA_AUTH_RESP_REASON
    // COB(3705):    ,PA_PROCESSING_CODE
    // COB(3705):    ,PA_TRANSACTION_AMT
    // COB(3705):    ,PA_APPROVED_AMT
    // COB(3705):    ,PA_MERCHANT_CATAGORY_CODE
    // COB(3705):    ,PA_ACQR_COUNTRY_CODE
    // COB(3705):    ,PA_POS_ENTRY_MODE
    // COB(3705):    ,PA_MERCHANT_ID
    // COB(3705):    ,PA_MERCHANT_NAME
    // COB(3705):    ,PA_MERCHANT_CITY
    // COB(3705):    ,PA_MERCHANT_STATE
    // COB(3705):    ,PA_MERCHANT_ZIP
    // COB(3705):    ,PA_TRANSACTION_ID
    // COB(3705):    ,PA_MATCH_STATUS
    // COB(3705):    ,PA_AUTH_FRAUD
    // COB(3705):    ,PA_FRAUD_RPT_DATE
    // COB(3705):    ,PAUTDTL1_FILLER
    // COB(3705):    ,PAUTSUM0_KEY_ACCNTID
    // COB(3705):     )
    // COB(3705):   VALUES
    // COB(3705):   (
    // COB(3705):     :PAUTDTL1.KEY-PAUT9CTS
    // COB(3705):    ,:PAUTDTL1.PA-AUTH-ORIG-DATE
    // COB(3705):    ,:PAUTDTL1.PA-AUTH-ORIG-TIME
    // COB(3705):    ,:PAUTDTL1.PA-CARD-NUM
    // COB(3705):    ,:PAUTDTL1.PA-AUTH-TYPE
    // COB(3705):    ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
    // COB(3705):    ,:PAUTDTL1.PA-MESSAGE-TYPE
    // COB(3705):    ,:PAUTDTL1.PA-MESSAGE-SOURCE
    // COB(3705):    ,:PAUTDTL1.PA-AUTH-ID-CODE
    // COB(3705):    ,:PAUTDTL1.PA-AUTH-RESP-CODE
    // COB(3705):    ,:PAUTDTL1.PA-AUTH-RESP-REASON
    // COB(3705):    ,:PAUTDTL1.PA-PROCESSING-CODE
    // COB(3705):    ,:PAUTDTL1.PA-TRANSACTION-AMT
    // COB(3705):    ,:PAUTDTL1.PA-APPROVED-AMT
    // COB(3705):    ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
    // COB(3705):    ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
    // COB(3705):    ,:PAUTDTL1.PA-POS-ENTRY-MODE
    // COB(3705):    ,:PAUTDTL1.PA-MERCHANT-ID
    // COB(3705):    ,:PAUTDTL1.PA-MERCHANT-NAME
    // COB(3705):    ,:PAUTDTL1.PA-MERCHANT-CITY
    // COB(3705):    ,:PAUTDTL1.PA-MERCHANT-STATE
    // COB(3705):    ,:PAUTDTL1.PA-MERCHANT-ZIP
    // COB(3705):    ,:PAUTDTL1.PA-TRANSACTION-ID
    // COB(3705):    ,:PAUTDTL1.PA-MATCH-STATUS
    // COB(3705):    ,:PAUTDTL1.PA-AUTH-FRAUD
    // COB(3705):    ,:PAUTDTL1.PA-FRAUD-RPT-DATE
    // COB(3705):    ,:PAUTDTL1.PAUTDTL1-FILLER
    // COB(3705):    ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
    // COB(3705):   )
    // COB(3705): END-EXEC
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
    // COB(3771): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(3772): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(3773): EVALUATE TRUE
    // COB(3774): WHEN IRIS-SQL-OK
    if ((ws.irissqlc.irisSqlOk())
        // COB(3775): WHEN IRIS-SQL-UNIQ-CONSTR-VIOLATED
        || ws.irissqlc.irisSqlUniqConstrViolated()) {
      // COB(3776): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
      // COB(3776):                                      WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(3778): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(3778):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(3780): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
      // COB(3780):                               IO-RTN-USED-KEY-ALPHA(2)(1:8)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 8);
      // COB(3782): MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
      // COB(3782):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      // COB(3784): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(3784):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(3786): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
      // COB(3786):                                      TO WS-FB-KEY-AREA(7:8)
      ws.wsFbKeyArea.setValue(ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, 6, 8);
      // COB(3788): MOVE 14 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(14);
      // COB(3789): SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(3790): IF IRIS-SQL-UNIQ-CONSTR-VIOLATED
      if (ws.irissqlc.irisSqlUniqConstrViolated()) {
        // COB(3791): SET IO-RTN-USED-KEY-DUPREC(PAUTDTL1-LVL) TO TRUE
        params
            .irisDbPcb
            .ioRtnUsedKeysPcbArea
            .ioRtnUsedKeysArea
            .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
            .setIoRtnUsedKeyDuprec(true);
        // COB(3792): END-IF
      }
      // COB(3793): MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
      // COB(3793):                 IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
      ws.wsLastSegmentName.setString("PAUTDTL1");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTDTL1");
      // COB(3795): END-EVALUATE
    }
    // COB(3797): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(3798): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    INSERT PATHCALL FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1InsertPathcall() {
    // COB(3809): MOVE 'PAUTDTL1-INSERT-PATHCALL' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-INSERT-PATHCALL");
    // COB(3812): SET HAS-NOT-PATHCALLS TO TRUE
    //       *
    //       *
    ws.setHasNotPathcalls(true);
    // COB(3813): SET HAS-PATHCALLS-ERROR TO TRUE
    ws.setHasPathcallsError(true);
    // COB(3814): MOVE LK-IO-AREA(1:WS-PATHCALL-LEN)
    // COB(3814):            TO WS-PATHCALL-AREA(1:WS-PATHCALL-LEN)
    ws.wsPathcallArea.setValue(params.lkIoArea, ws.wsPathcallLen.getInt());
    // COB(3816): MOVE WS-PATHCALL-LEN TO WS-INIT-PATHCALL-LEN
    ws.wsInitPathcallLen.setValue(ws.wsPathcallLen);
    // COB(3817): MOVE ZERO TO WS-PATHCALL-LEN
    ws.wsPathcallLen.setValue(0);
    // COB(3821): IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
    //       *
    //       *    SEGMENT: PAUTSUM0
    //       *
    if (params
        .irisWorkArea
        .irisKeyvalueTab
        .irisCcodeLevelsAtIndex(ws.pautsum0Lvl.getInt() - 1)
        .irisCcodeValues
        .irisCodePathcall()) {
      // COB(3823): MOVE WS-PATHCALL-AREA
      // COB(3823):      (WS-PATHCALL-LEN + 1:PAUTSUM0-LEN)
      // COB(3823):                           TO LK-IO-AREA(1:PAUTSUM0-LEN)
      params.lkIoArea.setValue(
          ws.wsPathcallArea, ws.wsPathcallLen.getInt(), 0, ws.pautsum0Len.getInt());
      // COB(3827): PERFORM PAUTSUM0-INSERT
      pautsum0Insert();
      // COB(3829): IF IRIS-SQLCODE NOT = ZERO
      if (!params.irisWorkArea.irisSqlError.irisSqlcode.equals(0)) {
        // COB(3830): MOVE WS-PATHCALL-AREA(1:WS-INIT-PATHCALL-LEN)
        // COB(3830):                  TO LK-IO-AREA(1:WS-INIT-PATHCALL-LEN)
        params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsInitPathcallLen.getInt());
        // COB(3832): GO TO PAUTDTL1-INSERT-PATHCALL-EX
        return;
        // COB(3833): END-IF
      }
      // COB(3835): ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
      ws.wsPathcallLen.add(ws.pautsum0Len);
      // COB(3837): END-IF
    }
    // COB(3842): MOVE WS-PATHCALL-AREA
    // COB(3842):      (WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
    // COB(3842):                           TO LK-IO-AREA(1:PAUTDTL1-LEN)
    //       *
    //       *
    //       *    INSERT PAUTDTL1
    //       *
    params.lkIoArea.setValue(
        ws.wsPathcallArea, ws.wsPathcallLen.getInt(), 0, ws.pautdtl1Len.getInt());
    // COB(3846): PERFORM PAUTDTL1-INSERT
    pautdtl1Insert();
    // COB(3848): MOVE WS-INIT-PATHCALL-LEN TO WS-PATHCALL-LEN WS-SEGMENT-LEN
    ws.wsPathcallLen.setValue(ws.wsInitPathcallLen);
    ws.wsSegmentLen.setValue(ws.wsInitPathcallLen);
    // COB(3849): MOVE WS-PATHCALL-AREA(1:WS-PATHCALL-LEN)
    // COB(3849):                           TO LK-IO-AREA(1:WS-PATHCALL-LEN).
    params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsPathcallLen.getInt());
  }

  /***
   ******************************************************************
   *    UPDATE PATHCALL FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1UpdatePathcall() {
    // COB(3862): MOVE 'PAUTDTL1-UPDATE-PATHCALL' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-UPDATE-PATHCALL");
    // COB(3865): SET HAS-NOT-PATHCALLS TO TRUE
    //       *
    //       *
    ws.setHasNotPathcalls(true);
    // COB(3866): SET HAS-PATHCALLS-ERROR TO TRUE
    ws.setHasPathcallsError(true);
    // COB(3867): MOVE LK-IO-AREA(1:WS-PATHCALL-LEN)
    // COB(3867):            TO WS-PATHCALL-AREA(1:WS-PATHCALL-LEN)
    ws.wsPathcallArea.setValue(params.lkIoArea, ws.wsPathcallLen.getInt());
    // COB(3869): MOVE WS-PATHCALL-LEN TO WS-INIT-PATHCALL-LEN
    ws.wsInitPathcallLen.setValue(ws.wsPathcallLen);
    // COB(3870): MOVE ZERO TO WS-PATHCALL-LEN
    ws.wsPathcallLen.setValue(0);
    // COB(3872): IF IS-PATHCALL-REVERSE
    if (ws.isPathcallReverse()) {
      // COB(3876): IF IRIS-CODE-PATHCALL(PAUTDTL1-LVL)
      //       *
      //       *      SEGMENT: PAUTDTL1
      //       *
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(ws.pautdtl1Lvl.getInt() - 1)
          .irisCcodeValues
          .irisCodePathcall()) {
        // COB(3877): MOVE WS-PATHCALL-AREA
        // COB(3877):      (WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
        // COB(3877):                       TO LK-IO-AREA(1:PAUTDTL1-LEN)
        params.lkIoArea.setValue(
            ws.wsPathcallArea, ws.wsPathcallLen.getInt(), 0, ws.pautdtl1Len.getInt());
        // COB(3881): PERFORM PAUTDTL1-UPDATE
        pautdtl1Update();
        // COB(3883): IF IRIS-SQLCODE NOT = ZERO
        if (!params.irisWorkArea.irisSqlError.irisSqlcode.equals(0)) {
          // COB(3884): MOVE WS-PATHCALL-AREA(1:WS-INIT-PATHCALL-LEN)
          // COB(3884):                  TO LK-IO-AREA(1:WS-INIT-PATHCALL-LEN)
          params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsInitPathcallLen.getInt());
          // COB(3886): GO TO PAUTDTL1-UPDATE-PATHCALL-EX
          return;
          // COB(3887): END-IF
        }
        // COB(3889): ADD PAUTDTL1-LEN TO WS-PATHCALL-LEN
        ws.wsPathcallLen.add(ws.pautdtl1Len);
        // COB(3891): END-IF
      }
      // COB(3892): ELSE
    } else {
      // COB(3896): IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
      //       *
      //       *      SEGMENT: PAUTSUM0
      //       *
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(ws.pautsum0Lvl.getInt() - 1)
          .irisCcodeValues
          .irisCodePathcall()) {
        // COB(3898): MOVE WS-PATHCALL-AREA
        // COB(3898):      (WS-PATHCALL-LEN + 1:PAUTSUM0-LEN)
        // COB(3898):                         TO LK-IO-AREA(1:PAUTSUM0-LEN)
        params.lkIoArea.setValue(
            ws.wsPathcallArea, ws.wsPathcallLen.getInt(), 0, ws.pautsum0Len.getInt());
        // COB(3902): PERFORM PAUTSUM0-UPDATE
        pautsum0Update();
        // COB(3904): IF IRIS-SQLCODE NOT = ZERO
        if (!params.irisWorkArea.irisSqlError.irisSqlcode.equals(0)) {
          // COB(3905): MOVE WS-PATHCALL-AREA(1:WS-INIT-PATHCALL-LEN)
          // COB(3905):                  TO LK-IO-AREA(1:WS-INIT-PATHCALL-LEN)
          params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsInitPathcallLen.getInt());
          // COB(3907): GO TO PAUTDTL1-UPDATE-PATHCALL-EX
          return;
          // COB(3908): END-IF
        }
        // COB(3910): ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
        ws.wsPathcallLen.add(ws.pautsum0Len);
        // COB(3912): END-IF
      }
      // COB(3917): MOVE WS-PATHCALL-AREA
      // COB(3917):      (WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
      // COB(3917):                           TO LK-IO-AREA(1:PAUTDTL1-LEN)
      //       *
      //       *
      //       *      UPDATE PAUTDTL1
      //       *
      params.lkIoArea.setValue(
          ws.wsPathcallArea, ws.wsPathcallLen.getInt(), 0, ws.pautdtl1Len.getInt());
      // COB(3921): PERFORM PAUTDTL1-UPDATE
      pautdtl1Update();
      // COB(3922): END-IF
    }
    // COB(3924): MOVE WS-INIT-PATHCALL-LEN TO WS-PATHCALL-LEN WS-SEGMENT-LEN
    ws.wsPathcallLen.setValue(ws.wsInitPathcallLen);
    ws.wsSegmentLen.setValue(ws.wsInitPathcallLen);
    // COB(3925): MOVE WS-PATHCALL-AREA(1:WS-PATHCALL-LEN)
    // COB(3925):                           TO LK-IO-AREA(1:WS-PATHCALL-LEN).
    params.lkIoArea.setValue(ws.wsPathcallArea, ws.wsPathcallLen.getInt());
  }

  /***
   ******************************************************************
   *    UPDATE FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1Update() {
    // COB(3938): MOVE 'PAUTDTL1-UPDATE' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-UPDATE");
    // COB(3941): INITIALIZE PAUTDTL1
    //       *
    //       *
    ws.pautdtl1copy.pautdtl1.initialize();
    // COB(3943): IF HAS-PATHCALLS
    if (ws.hasPathcalls()) {
      // COB(3944): PERFORM PAUTDTL1-UPDATE-PATHCALL
      pautdtl1UpdatePathcall();
      // COB(3945): GO TO PAUTDTL1-UPDATE-EX
      return;
      // COB(3946): END-IF
    }
    // COB(3948): MOVE LK-IO-AREA(1:PAUTDTL1-LEN) TO PAUTDTL1(1:PAUTDTL1-LEN)
    ws.pautdtl1copy.pautdtl1.setValue(params.lkIoArea, ws.pautdtl1Len.getInt());
    // COB(3949): IF PA-PROCESSING-CODE-N
    // COB(3949): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paProcessingCodeN.isNumeric()) {
      // COB(3951): MOVE ZERO TO
      // COB(3951): PA-PROCESSING-CODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(0);
      // COB(3953): END-IF
    }
    // COB(3954): IF PA-TRANSACTION-AMT
    // COB(3954): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paTransactionAmt.isNumeric()) {
      // COB(3956): MOVE ZERO TO
      // COB(3956): PA-TRANSACTION-AMT OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paTransactionAmt.setValue(0);
      // COB(3958): END-IF
    }
    // COB(3959): IF PA-APPROVED-AMT
    // COB(3959): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paApprovedAmt.isNumeric()) {
      // COB(3961): MOVE ZERO TO
      // COB(3961): PA-APPROVED-AMT OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paApprovedAmt.setValue(0);
      // COB(3963): END-IF
    }
    // COB(3964): IF PA-POS-ENTRY-MODE-N
    // COB(3964): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.paPosEntryModeN.isNumeric()) {
      // COB(3966): MOVE ZERO TO
      // COB(3966): PA-POS-ENTRY-MODE-N OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(0);
      // COB(3968): END-IF
    }
    // COB(3969): IF PAUTSUM0-KEY-ACCNTID
    // COB(3969): OF PAUTDTL1 NOT NUMERIC
    if (!ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.isNumeric()) {
      // COB(3971): MOVE ZERO TO
      // COB(3971): PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
      ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(0);
      // COB(3973): END-IF
    }
    // COB(3977): MOVE PA-PROCESSING-CODE-N OF PAUTDTL1 TO
    // COB(3977): WS-PACKED-FLD-18-00(1)
    //       *
    //       * SIGNED ZONED FIELDS VS PACKED FITTING
    //       *
    ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCodeN);
    // COB(3979): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(3979): PA-PROCESSING-CODE OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.paProcessingCode.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(3981): MOVE PA-POS-ENTRY-MODE-N OF PAUTDTL1 TO
    // COB(3981): WS-PACKED-FLD-18-00(1)
    ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryModeN);
    // COB(3983): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(3983): PA-POS-ENTRY-MODE OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.paPosEntryMode.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(3986): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(3987): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(3988): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(3988):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler250
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(3990): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(3991): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(3991):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(0), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(3993): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(3994): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler174.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(3995): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(3996): END-IF
    }
    // COB(3997): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(3997):                              PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(3999): COMPUTE WS-CK-LEN = 8
    ws.wsCkLen.setValue(8);
    // COB(4000): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(4000):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, ws.wsCkPos.getInt() - 1, 8);
    // COB(4002): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(4003): MOVE IO-RTN-USED-KEY-ALPHA(2)(1:8) TO
    // COB(4003):                                 KEY-PAUT9CTS OF PAUTDTL1(1:8)
    ws.pautdtl1copy.pautdtl1.keyPaut9cts.setValue(
        params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(1)
            .ioRtnUsedSsaKeys
            .ioRtnUsedKeyAlpha,
        8);
    // COB(4007): EXEC SQL
    // COB(4007):   UPDATE DBPAUTP0_PAUTDTL1 SET
    // COB(4007):     PA_AUTH_ORIG_DATE = :PAUTDTL1.PA-AUTH-ORIG-DATE,
    // COB(4007):     PA_AUTH_ORIG_TIME = :PAUTDTL1.PA-AUTH-ORIG-TIME,
    // COB(4007):     PA_CARD_NUM = :PAUTDTL1.PA-CARD-NUM,
    // COB(4007):     PA_AUTH_TYPE = :PAUTDTL1.PA-AUTH-TYPE,
    // COB(4007):     PA_CARD_EXPIRY_DATE = :PAUTDTL1.PA-CARD-EXPIRY-DATE,
    // COB(4007):     PA_MESSAGE_TYPE = :PAUTDTL1.PA-MESSAGE-TYPE,
    // COB(4007):     PA_MESSAGE_SOURCE = :PAUTDTL1.PA-MESSAGE-SOURCE,
    // COB(4007):     PA_AUTH_ID_CODE = :PAUTDTL1.PA-AUTH-ID-CODE,
    // COB(4007):     PA_AUTH_RESP_CODE = :PAUTDTL1.PA-AUTH-RESP-CODE,
    // COB(4007):     PA_AUTH_RESP_REASON = :PAUTDTL1.PA-AUTH-RESP-REASON,
    // COB(4007):     PA_PROCESSING_CODE = :PAUTDTL1.PA-PROCESSING-CODE,
    // COB(4007):     PA_TRANSACTION_AMT = :PAUTDTL1.PA-TRANSACTION-AMT,
    // COB(4007):     PA_APPROVED_AMT = :PAUTDTL1.PA-APPROVED-AMT,
    // COB(4007):     PA_MERCHANT_CATAGORY_CODE =
    // COB(4007):     :PAUTDTL1.PA-MERCHANT-CATAGORY-CODE,
    // COB(4007):     PA_ACQR_COUNTRY_CODE = :PAUTDTL1.PA-ACQR-COUNTRY-CODE,
    // COB(4007):     PA_POS_ENTRY_MODE = :PAUTDTL1.PA-POS-ENTRY-MODE,
    // COB(4007):     PA_MERCHANT_ID = :PAUTDTL1.PA-MERCHANT-ID,
    // COB(4007):     PA_MERCHANT_NAME = :PAUTDTL1.PA-MERCHANT-NAME,
    // COB(4007):     PA_MERCHANT_CITY = :PAUTDTL1.PA-MERCHANT-CITY,
    // COB(4007):     PA_MERCHANT_STATE = :PAUTDTL1.PA-MERCHANT-STATE,
    // COB(4007):     PA_MERCHANT_ZIP = :PAUTDTL1.PA-MERCHANT-ZIP,
    // COB(4007):     PA_TRANSACTION_ID = :PAUTDTL1.PA-TRANSACTION-ID,
    // COB(4007):     PA_MATCH_STATUS = :PAUTDTL1.PA-MATCH-STATUS,
    // COB(4007):     PA_AUTH_FRAUD = :PAUTDTL1.PA-AUTH-FRAUD,
    // COB(4007):     PA_FRAUD_RPT_DATE = :PAUTDTL1.PA-FRAUD-RPT-DATE,
    // COB(4007):     PAUTDTL1_FILLER = :PAUTDTL1.PAUTDTL1-FILLER
    // COB(4007):   WHERE
    // COB(4007):     KEY_PAUT9CTS =
    // COB(4007):   :PAUTDTL1.KEY-PAUT9CTS
    // COB(4007):   AND
    // COB(4007):     PAUTSUM0_KEY_ACCNTID = :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
    // COB(4007): END-EXEC
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
    // COB(4045): MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
    // COB(4045): WS-PACKED-FLD-18-00(1)
    //       *
    //       * PACKED FIELDS VS SIGNED ZONED FITTING
    //       *
    ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paProcessingCode);
    // COB(4047): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(4047): PA-PROCESSING-CODE-N OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.paProcessingCodeN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(4049): MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
    // COB(4049): WS-PACKED-FLD-18-00(1)
    ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautdtl1copy.pautdtl1.paPosEntryMode);
    // COB(4051): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(4051): PA-POS-ENTRY-MODE-N OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.paPosEntryModeN.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(4054): IF SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(4055): MOVE LK-IO-AREA(1:PAUTDTL1-LEN)
      // COB(4055):   TO PAUTDTL1-LAST-AREA
      ws.segmentsLastArea.pautdtl1LastArea.setValue(params.lkIoArea, ws.pautdtl1Len.getInt());
      // COB(4057): END-IF
    }
    // COB(4058): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(4059): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    DELETE FOR SEGMENT PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1Delete() {
    // COB(4070): MOVE 'PAUTDTL1-DELETE' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-DELETE");
    // COB(4073): IF HAS-PATHCALLS
    //       *
    //       *
    if (ws.hasPathcalls()) {
      // COB(4074): SET IRIS-ERR-RTN-UNHANDLED-ACCESS TO TRUE
      params.irisWorkArea.setIrisErrRtnUnhandledAccess(true);
      // COB(4075): GO TO PAUTDTL1-DELETE-EX
      return;
      // COB(4076): END-IF
    }
    // COB(4078): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(4079): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(4080): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(4080):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler250
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(4082): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(4083): MOVE WS-PACKED-FLD-CHR(1)(5:6)
    // COB(4083):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(0), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(4085): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(4086): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler174.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(4087): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(4088): END-IF
    }
    // COB(4089): MOVE WS-PACKED-FLD-18-00(1) TO
    // COB(4089):                              PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
    ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(4091): COMPUTE WS-CK-LEN = 8
    ws.wsCkLen.setValue(8);
    // COB(4092): MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
    // COB(4092):          TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautdtl1ConcatenatedKey.setValue(
        ws.pautdtl1copy.pautdtl1.keyPaut9cts, 0, ws.wsCkPos.getInt() - 1, 8);
    // COB(4094): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(4095): MOVE IO-RTN-USED-KEY-ALPHA(2)(1:8) TO
    // COB(4095):                                 KEY-PAUT9CTS OF PAUTDTL1(1:8)
    ws.pautdtl1copy.pautdtl1.keyPaut9cts.setValue(
        params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(1)
            .ioRtnUsedSsaKeys
            .ioRtnUsedKeyAlpha,
        8);
    // COB(4098): EXEC SQL
    // COB(4098):   DELETE FROM DBPAUTP0_PAUTDTL1
    // COB(4098):   WHERE
    // COB(4098):     KEY_PAUT9CTS =
    // COB(4098):   :PAUTDTL1.KEY-PAUT9CTS
    // COB(4098):   AND
    // COB(4098):     PAUTSUM0_KEY_ACCNTID = :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
    // COB(4098): END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand(
          "DELETE FROM DBPAUTP0_PAUTDTL1 WHERE KEY_PAUT9CTS = ? AND PAUTSUM0_KEY_ACCNTID = ?");
      sql.where(ws.pautdtl1copy.pautdtl1.keyPaut9cts, ws.pautdtl1copy.pautdtl1.pautsum0KeyAccntid);
      sql.execute();
    }
    // COB(4107): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(4108): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    SET I/O AREA PAUTDTL1
   ******************************************************************
   */
  protected void pautdtl1SetIoArea() {
    // COB(4119): MOVE 'PAUTDTL1-SET-IO-AREA' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTDTL1-SET-IO-AREA");
    // COB(4121): MOVE PAUTDTL1(1:WS-SEGMENT-LEN)
    // COB(4121):                TO LK-IO-AREA(1:WS-SEGMENT-LEN)
    // COB(4121):                   PAUTDTL1-LAST-AREA.
    params.lkIoArea.setValue(ws.pautdtl1copy.pautdtl1, ws.wsSegmentLen.getInt());
    ws.segmentsLastArea.pautdtl1LastArea.setValue(
        ws.pautdtl1copy.pautdtl1, ws.wsSegmentLen.getInt());
  }

  /***
   ******************************************************************
   *    HANDLE SEGMENT PAUTINDX
   ******************************************************************
   */
  protected void handleSegmentPautindx() {
    // COB(4134): MOVE PAUTINDX-LVL TO WS-SEGMENT-LEVEL
    ws.wsSegmentLevel.setValue(ws.pautindxLvl);
    // COB(4135): MOVE PAUTINDX-LEN TO WS-SEGMENT-LEN WS-INIT-PATHCALL-LEN
    ws.wsSegmentLen.setValue(ws.pautindxLen);
    ws.wsInitPathcallLen.setValue(ws.pautindxLen);
    // COB(4137): MOVE 'HANDLE-SEGMENT-PAUTINDX' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("HANDLE-SEGMENT-PAUTINDX");
    // COB(4139): EVALUATE TRUE
    // COB(4140): WHEN SQL-SELECT-PRIMARY
    if (params.irisWorkArea.sqlSelectPrimary()) {
      // COB(4141): PERFORM PAUTINDX-SELECT-PRIMARY-KEY
      pautindxSelectPrimaryKey();
      // COB(4142): WHEN SQL-SELECT-SEEK
    } else if (params.irisWorkArea.sqlSelectSeek()) {
      // COB(4143): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(4144): EVALUATE TRUE
      // COB(4145): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(4146): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(4147): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(4148): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(4149): END-EVALUATE
      }
      // COB(4151): PERFORM PAUTINDX-SELECT-FIRST
      pautindxSelectFirst();
      // COB(4152): WHEN SQL-SELECT-NEXT
    } else if (params.irisWorkArea.sqlSelectNext()) {
      // COB(4153): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(4154): EVALUATE TRUE
      // COB(4155): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(4156): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(4157): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(4158): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(4159): END-EVALUATE
      }
      // COB(4161): PERFORM PAUTINDX-SELECT-NEXT
      pautindxSelectNext();
      // COB(4162): WHEN SQL-SELECT-DYNAMIC
    } else if (params.irisWorkArea.sqlSelectDynamic()) {
      // COB(4163): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(4164): EVALUATE TRUE
      // COB(4165): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(4166): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(4167): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(4168): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(4169): END-EVALUATE
      }
      // COB(4171): PERFORM PAUTINDX-SELECT-DYNAMIC
      pautindxSelectDynamic();
      // COB(4172): WHEN SQL-SELECT-PATH
    } else if (params.irisWorkArea.sqlSelectPath()) {
      // COB(4173): SET COMMAND-CODE-FIRST TO TRUE
      ws.setCommandCodeFirst(true);
      // COB(4174): EVALUATE TRUE
      // COB(4175): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(4176): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(4177): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(4178): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(4179): END-EVALUATE
      }
      // COB(4181): PERFORM PAUTINDX-SELECT-DYNAMIC
      pautindxSelectDynamic();
      // COB(4182): WHEN SQL-INSERT
    } else if (params.irisWorkArea.sqlInsert()) {
      // COB(4183): SET COMMAND-CODE-HERE TO TRUE
      ws.setCommandCodeHere(true);
      // COB(4184): EVALUATE TRUE
      // COB(4185): WHEN  IRIS-CODE-FIRST(1)
      if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeFirst()) {
        // COB(4186): SET COMMAND-CODE-FIRST TO TRUE
        ws.setCommandCodeFirst(true);
        // COB(4187): WHEN  IRIS-CODE-LAST(1)
      } else if (params
          .irisWorkArea
          .irisKeyvalueTab
          .irisCcodeLevelsAtIndex(0)
          .irisCcodeValues
          .irisCodeLast()) {
        // COB(4188): SET COMMAND-CODE-LAST TO TRUE
        ws.setCommandCodeLast(true);
        // COB(4189): END-EVALUATE
      }
      // COB(4191): PERFORM PAUTINDX-INSERT
      pautindxInsert();
      // COB(4192): WHEN SQL-UPDATE
    } else if (params.irisWorkArea.sqlUpdate()) {
      // COB(4193): PERFORM PAUTINDX-UPDATE
      pautindxUpdate();
      // COB(4194): WHEN SQL-DELETE
    } else if (params.irisWorkArea.sqlDelete()) {
      // COB(4195): PERFORM PAUTINDX-DELETE
      pautindxDelete();
      // COB(4196): WHEN OTHER
    } else {
      // COB(4197): SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
      params.irisWorkArea.setIrisErrFunctionNotFound(true);
      // COB(4198): END-EVALUATE
    }
    // COB(4200): IF IRIS-SQL-OK
    if (ws.irissqlc.irisSqlOk()) {
      // COB(4201): MOVE PAUTINDX-LVL TO WS-LAST-SEGMENT-LEVEL
      ws.wsLastSegmentLevel.setValue(ws.pautindxLvl);
      // COB(4202): MOVE 'PAUTINDX' TO WS-LAST-SEGMENT-NAME
      ws.wsLastSegmentName.setString("PAUTINDX");
      // COB(4203): END-IF.
    }
  }

  /***
   ******************************************************************
   *    SELECT ON PRIMARY KEY FOR SEGMENT PAUTINDX
   ******************************************************************
   */
  protected void pautindxSelectPrimaryKey() {
    // COB(4215): MOVE 'PAUTINDX-SELECT-PRIMARY-KEY' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTINDX-SELECT-PRIMARY-KEY");
    // COB(4217): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(4218): MOVE ZERO
      // COB(4218):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(4220): ELSE
    } else {
      // COB(4221): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(4221):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(4223): END-IF
    }
    // COB(4224): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(4225): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(4226): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(4227): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(4227):          TO PAUTINDX-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautindxConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(4229): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(4230): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(4230):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler250
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(4232): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler174.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(4233): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(4234): END-IF
    }
    // COB(4235): MOVE WS-PACKED-FLD-18-00(1) TO KEY-ACCNTID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.keyAccntid.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(4236): EXEC SQL
    // COB(4236): SELECT
    // COB(4236):   KEY_ACCNTID
    // COB(4236):  ,PA_CUST_ID
    // COB(4236):  ,PA_AUTH_STATUS
    // COB(4236):  ,PA_ACCOUNT_STATUS1
    // COB(4236):  ,PA_ACCOUNT_STATUS2
    // COB(4236):  ,PA_ACCOUNT_STATUS3
    // COB(4236):  ,PA_ACCOUNT_STATUS4
    // COB(4236):  ,PA_ACCOUNT_STATUS5
    // COB(4236):  ,PA_CREDIT_LIMIT
    // COB(4236):  ,PA_CASH_LIMIT
    // COB(4236):  ,PA_CREDIT_BALANCE
    // COB(4236):  ,PA_CASH_BALANCE
    // COB(4236):  ,PA_APPROVED_AUTH_CNT
    // COB(4236):  ,PA_DECLINED_AUTH_CNT
    // COB(4236):  ,PA_APPROVED_AUTH_AMT
    // COB(4236):  ,PA_DECLINED_AUTH_AMT
    // COB(4236):  ,PAUTSUM0_FILLER
    // COB(4236): INTO
    // COB(4236):   :PAUTSUM0.KEY-ACCNTID
    // COB(4236):  ,:PAUTSUM0.PA-CUST-ID
    // COB(4236):  ,:PAUTSUM0.PA-AUTH-STATUS
    // COB(4236):  ,:PAUTSUM0.PA-ACCOUNT-STATUS1
    // COB(4236):  ,:PAUTSUM0.PA-ACCOUNT-STATUS2
    // COB(4236):  ,:PAUTSUM0.PA-ACCOUNT-STATUS3
    // COB(4236):  ,:PAUTSUM0.PA-ACCOUNT-STATUS4
    // COB(4236):  ,:PAUTSUM0.PA-ACCOUNT-STATUS5
    // COB(4236):  ,:PAUTSUM0.PA-CREDIT-LIMIT
    // COB(4236):  ,:PAUTSUM0.PA-CASH-LIMIT
    // COB(4236):  ,:PAUTSUM0.PA-CREDIT-BALANCE
    // COB(4236):  ,:PAUTSUM0.PA-CASH-BALANCE
    // COB(4236):  ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
    // COB(4236):  ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
    // COB(4236):  ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
    // COB(4236):  ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
    // COB(4236):  ,:PAUTSUM0.PAUTSUM0-FILLER
    // COB(4236): FROM
    // COB(4236): DBPAUTP0_PAUTSUM0
    // COB(4236): WHERE
    // COB(4236): KEY_ACCNTID =
    // COB(4236):            :PAUTSUM0.KEY-ACCNTID
    // COB(4236):   ORDER BY KEY_ACCNTID
    // COB(4236):   FETCH FIRST 1 ROWS ONLY
    // COB(4236): END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand(
          "SELECT KEY_ACCNTID, PA_CUST_ID, PA_AUTH_STATUS, PA_ACCOUNT_STATUS1, PA_ACCOUNT_STATUS2,"
              + " PA_ACCOUNT_STATUS3, PA_ACCOUNT_STATUS4, PA_ACCOUNT_STATUS5, PA_CREDIT_LIMIT,"
              + " PA_CASH_LIMIT, PA_CREDIT_BALANCE, PA_CASH_BALANCE, PA_APPROVED_AUTH_CNT,"
              + " PA_DECLINED_AUTH_CNT, PA_APPROVED_AUTH_AMT, PA_DECLINED_AUTH_AMT, PAUTSUM0_FILLER"
              + " FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID = ? ORDER BY KEY_ACCNTID FETCH FIRST 1"
              + " ROWS ONLY");
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
    // COB(4282): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTINDX-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautindxLvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(4283): MOVE SQLCODE TO IRIS-DB-SQLCODE
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(4284): IF SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(4285): MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(4286): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(4286):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(4288): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(4288):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(4290): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(4290):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(4292): MOVE 6 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(6);
      // COB(4293): MOVE PAUTINDX-LEN TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(ws.pautindxLen);
      // COB(4294): PERFORM PAUTINDX-SET-IO-AREA
      pautindxSetIoArea();
      // COB(4295): SET IO-RTN-USED-KEY-CHANGED(PAUTINDX-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautindxLvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(4296): MOVE 'PAUTINDX' TO WS-LAST-SEGMENT-NAME
      // COB(4296):                 IO-RTN-USED-LAST-SEGMENT(PAUTINDX-LVL)
      ws.wsLastSegmentName.setString("PAUTINDX");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautindxLvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTINDX");
      // COB(4298): END-IF
    }
    // COB(4300): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(4301): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    SELECT FIRST TIME FOR SEGMENT PAUTINDX
   ******************************************************************
   */
  protected void pautindxSelectFirst() {
    // COB(4312): MOVE 'PAUTINDX-SELECT-FIRST' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTINDX-SELECT-FIRST");
    // COB(4314): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(4315): MOVE ZERO
      // COB(4315):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(4317): ELSE
    } else {
      // COB(4318): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(4318):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(4320): END-IF
    }
    // COB(4321): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(4322): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(4323): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(4324): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(4324):          TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautsum0ConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(4326): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(4327): EXEC SQL
    // COB(4327): SELECT
    // COB(4327):   KEY_ACCNTID
    // COB(4327):  ,PA_CUST_ID
    // COB(4327):  ,PA_AUTH_STATUS
    // COB(4327):  ,PA_ACCOUNT_STATUS1
    // COB(4327):  ,PA_ACCOUNT_STATUS2
    // COB(4327):  ,PA_ACCOUNT_STATUS3
    // COB(4327):  ,PA_ACCOUNT_STATUS4
    // COB(4327):  ,PA_ACCOUNT_STATUS5
    // COB(4327):  ,PA_CREDIT_LIMIT
    // COB(4327):  ,PA_CASH_LIMIT
    // COB(4327):  ,PA_CREDIT_BALANCE
    // COB(4327):  ,PA_CASH_BALANCE
    // COB(4327):  ,PA_APPROVED_AUTH_CNT
    // COB(4327):  ,PA_DECLINED_AUTH_CNT
    // COB(4327):  ,PA_APPROVED_AUTH_AMT
    // COB(4327):  ,PA_DECLINED_AUTH_AMT
    // COB(4327):  ,PAUTSUM0_FILLER
    // COB(4327): INTO
    // COB(4327):   :PAUTSUM0.KEY-ACCNTID
    // COB(4327):  ,:PAUTSUM0.PA-CUST-ID
    // COB(4327):  ,:PAUTSUM0.PA-AUTH-STATUS
    // COB(4327):  ,:PAUTSUM0.PA-ACCOUNT-STATUS1
    // COB(4327):  ,:PAUTSUM0.PA-ACCOUNT-STATUS2
    // COB(4327):  ,:PAUTSUM0.PA-ACCOUNT-STATUS3
    // COB(4327):  ,:PAUTSUM0.PA-ACCOUNT-STATUS4
    // COB(4327):  ,:PAUTSUM0.PA-ACCOUNT-STATUS5
    // COB(4327):  ,:PAUTSUM0.PA-CREDIT-LIMIT
    // COB(4327):  ,:PAUTSUM0.PA-CASH-LIMIT
    // COB(4327):  ,:PAUTSUM0.PA-CREDIT-BALANCE
    // COB(4327):  ,:PAUTSUM0.PA-CASH-BALANCE
    // COB(4327):  ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
    // COB(4327):  ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
    // COB(4327):  ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
    // COB(4327):  ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
    // COB(4327):  ,:PAUTSUM0.PAUTSUM0-FILLER
    // COB(4327): FROM
    // COB(4327): DBPAUTP0_PAUTSUM0
    // COB(4327):   WHERE
    // COB(4327):     KEY_ACCNTID = (
    // COB(4327):       SELECT
    // COB(4327):         KEY_ACCNTID
    // COB(4327):       FROM
    // COB(4327):         DBPAUTP0_PAUTSUM0
    // COB(4327):         WHERE KEY_ACCNTID IS NOT NULL
    // COB(4327):       ORDER BY 1 LIMIT 1
    // COB(4327):     )
    // COB(4327): END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand(
          "SELECT KEY_ACCNTID, PA_CUST_ID, PA_AUTH_STATUS, PA_ACCOUNT_STATUS1, PA_ACCOUNT_STATUS2,"
              + " PA_ACCOUNT_STATUS3, PA_ACCOUNT_STATUS4, PA_ACCOUNT_STATUS5, PA_CREDIT_LIMIT,"
              + " PA_CASH_LIMIT, PA_CREDIT_BALANCE, PA_CASH_BALANCE, PA_APPROVED_AUTH_CNT,"
              + " PA_DECLINED_AUTH_CNT, PA_APPROVED_AUTH_AMT, PA_DECLINED_AUTH_AMT, PAUTSUM0_FILLER"
              + " FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID = (SELECT KEY_ACCNTID FROM"
              + " DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID IS NOT 0 ORDER BY 1 LIMIT 1)");
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
    // COB(4377): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTINDX-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautindxLvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(4378): IF SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(4379): MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(4380): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(4380):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(4382): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(4382):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(4384): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(4384):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(4386): MOVE 6 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(6);
      // COB(4387): MOVE 6 TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(6);
      // COB(4388): PERFORM PAUTINDX-SET-IO-AREA
      pautindxSetIoArea();
      // COB(4389): SET IO-RTN-USED-KEY-CHANGED(PAUTINDX-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautindxLvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(4390): MOVE 'PAUTINDX' TO WS-LAST-SEGMENT-NAME
      // COB(4390):                 IO-RTN-USED-LAST-SEGMENT(PAUTINDX-LVL)
      ws.wsLastSegmentName.setString("PAUTINDX");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautindxLvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTINDX");
      // COB(4392): END-IF
    }
    // COB(4394): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(4395): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    SELECT NEXT FOR SEGMENT PAUTINDX
   ******************************************************************
   */
  protected void pautindxSelectNext() {
    // COB(4406): MOVE 'PAUTINDX-SELECT-NEXT' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTINDX-SELECT-NEXT");
    // COB(4408): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(4409): MOVE ZERO
      // COB(4409):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(4411): ELSE
    } else {
      // COB(4412): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(4412):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(4414): END-IF
    }
    // COB(4415): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(4416): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(4417): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(4418): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(4418):          TO PAUTINDX-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautindxConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(4420): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(4421): MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
    // COB(4421):                  TO WS-PACKED-FLD-CHR(1)(5:6)
    ws.filler250
        .wsPackedFldChrAtIndex(0)
        .setValue(
            params.irisDbPcb.ioRtnUsedKeysPcbArea.ioRtnUsedKeysArea.ioRtnUsedKeyValueAtIndex(0)
                .ioRtnUsedSsaKeys
                .ioRtnUsedKeyAlpha,
            0,
            4,
            6);
    // COB(4423): IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
    if (!ws.filler174.wsPackedFld1800AtIndex(0).isNumeric()) {
      // COB(4424): MOVE ZERO TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(0);
      // COB(4425): END-IF
    }
    // COB(4426): MOVE WS-PACKED-FLD-18-00(1) TO KEY-ACCNTID OF PAUTSUM0
    ws.pautsum0copy.pautsum0.keyAccntid.setValue(ws.filler174.wsPackedFld1800AtIndex(0));
    // COB(4428): EXEC SQL
    // COB(4428): SELECT
    // COB(4428):   KEY_ACCNTID
    // COB(4428):  ,PA_CUST_ID
    // COB(4428):  ,PA_AUTH_STATUS
    // COB(4428):  ,PA_ACCOUNT_STATUS1
    // COB(4428):  ,PA_ACCOUNT_STATUS2
    // COB(4428):  ,PA_ACCOUNT_STATUS3
    // COB(4428):  ,PA_ACCOUNT_STATUS4
    // COB(4428):  ,PA_ACCOUNT_STATUS5
    // COB(4428):  ,PA_CREDIT_LIMIT
    // COB(4428):  ,PA_CASH_LIMIT
    // COB(4428):  ,PA_CREDIT_BALANCE
    // COB(4428):  ,PA_CASH_BALANCE
    // COB(4428):  ,PA_APPROVED_AUTH_CNT
    // COB(4428):  ,PA_DECLINED_AUTH_CNT
    // COB(4428):  ,PA_APPROVED_AUTH_AMT
    // COB(4428):  ,PA_DECLINED_AUTH_AMT
    // COB(4428):  ,PAUTSUM0_FILLER
    // COB(4428): INTO
    // COB(4428):   :PAUTSUM0.KEY-ACCNTID
    // COB(4428):  ,:PAUTSUM0.PA-CUST-ID
    // COB(4428):  ,:PAUTSUM0.PA-AUTH-STATUS
    // COB(4428):  ,:PAUTSUM0.PA-ACCOUNT-STATUS1
    // COB(4428):  ,:PAUTSUM0.PA-ACCOUNT-STATUS2
    // COB(4428):  ,:PAUTSUM0.PA-ACCOUNT-STATUS3
    // COB(4428):  ,:PAUTSUM0.PA-ACCOUNT-STATUS4
    // COB(4428):  ,:PAUTSUM0.PA-ACCOUNT-STATUS5
    // COB(4428):  ,:PAUTSUM0.PA-CREDIT-LIMIT
    // COB(4428):  ,:PAUTSUM0.PA-CASH-LIMIT
    // COB(4428):  ,:PAUTSUM0.PA-CREDIT-BALANCE
    // COB(4428):  ,:PAUTSUM0.PA-CASH-BALANCE
    // COB(4428):  ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
    // COB(4428):  ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
    // COB(4428):  ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
    // COB(4428):  ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
    // COB(4428):  ,:PAUTSUM0.PAUTSUM0-FILLER
    // COB(4428): FROM
    // COB(4428): DBPAUTP0_PAUTSUM0
    // COB(4428):   WHERE
    // COB(4428):      KEY_ACCNTID = (
    // COB(4428):       SELECT
    // COB(4428):         KEY_ACCNTID
    // COB(4428):       FROM
    // COB(4428):         DBPAUTP0_PAUTSUM0
    // COB(4428):       WHERE
    // COB(4428):         KEY_ACCNTID >
    // COB(4428):         :PAUTSUM0.KEY-ACCNTID
    // COB(4428):         AND KEY_ACCNTID IS NOT NULL
    // COB(4428):       ORDER BY 1 LIMIT 1
    // COB(4428):    )
    // COB(4428): END-EXEC
    try (NSqlCommand sql = new NSqlCommand(sqlca)) {
      sql.setCommand(
          "SELECT KEY_ACCNTID, PA_CUST_ID, PA_AUTH_STATUS, PA_ACCOUNT_STATUS1, PA_ACCOUNT_STATUS2,"
              + " PA_ACCOUNT_STATUS3, PA_ACCOUNT_STATUS4, PA_ACCOUNT_STATUS5, PA_CREDIT_LIMIT,"
              + " PA_CASH_LIMIT, PA_CREDIT_BALANCE, PA_CASH_BALANCE, PA_APPROVED_AUTH_CNT,"
              + " PA_DECLINED_AUTH_CNT, PA_APPROVED_AUTH_AMT, PA_DECLINED_AUTH_AMT, PAUTSUM0_FILLER"
              + " FROM DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID = (SELECT KEY_ACCNTID FROM"
              + " DBPAUTP0_PAUTSUM0 WHERE KEY_ACCNTID > ? AND KEY_ACCNTID IS NOT 0 ORDER BY 1 LIMIT"
              + " 1)");
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
    // COB(4481): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTINDX-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautindxLvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(4482): IF SQLCODE = ZERO
    if (sqlca.sqlcode.equals(0)) {
      // COB(4483): MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(4484): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(4484):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(4486): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(4486):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(4488): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(4488):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(4490): MOVE 6 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(6);
      // COB(4491): MOVE 6 TO WS-SEGMENT-LEN
      ws.wsSegmentLen.setValue(6);
      // COB(4492): PERFORM PAUTINDX-SET-IO-AREA
      pautindxSetIoArea();
      // COB(4493): SET IO-RTN-USED-KEY-CHANGED(PAUTINDX-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautindxLvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(4494): MOVE 'PAUTINDX' TO WS-LAST-SEGMENT-NAME
      // COB(4494):                 IO-RTN-USED-LAST-SEGMENT(PAUTINDX-LVL)
      ws.wsLastSegmentName.setString("PAUTINDX");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautindxLvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTINDX");
      // COB(4496): END-IF
    }
    // COB(4498): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(4499): MOVE SQLERRM TO IRIS-SQLERRM.
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
  }

  /***
   ******************************************************************
   *    DYNAMIC SELECT FOR SEGMENT PAUTINDX
   ******************************************************************
   */
  protected void pautindxSelectDynamic() {
    // COB(4510): MOVE 'PAUTINDX-SELECT-DYNAMIC' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTINDX-SELECT-DYNAMIC");
    // COB(4512): IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
    if (!ws.pautsum0copy.pautsum0.keyAccntid.isNumeric()) {
      // COB(4513): MOVE ZERO
      // COB(4513):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(0);
      // COB(4515): ELSE
    } else {
      // COB(4516): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(4516):                                   TO WS-PACKED-FLD-18-00(2)
      ws.filler174.wsPackedFld1800AtIndex(1).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(4518): END-IF
    }
    // COB(4519): MOVE 1 TO WS-CK-POS
    ws.wsCkPos.setValue(1);
    // COB(4520): MOVE ZERO TO WS-CK-LEN
    ws.wsCkLen.setValue(0);
    // COB(4521): COMPUTE WS-CK-LEN = 6
    ws.wsCkLen.setValue(6);
    // COB(4522): MOVE WS-PACKED-FLD-CHR(2)(5:6)
    // COB(4522):          TO PAUTINDX-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
    ws.pautindxConcatenatedKey.setValue(
        ws.filler250.wsPackedFldChrAtIndex(1), 4, ws.wsCkPos.getInt() - 1, 6);
    // COB(4524): ADD WS-CK-LEN TO WS-CK-POS
    ws.wsCkPos.add(ws.wsCkLen);
    // COB(4527): MOVE ZERO TO WS-WHERE-LEN WS-ORDERBY-LEN
    //       *    PREPARE THE WHERE CONDITION IF ANY
    //       *
    ws.wsWhereLen.setValue(0);
    ws.wsOrderbyLen.setValue(0);
    // COB(4528): IF SQL-CONDITION-CLAUSE-LENGTH > 0
    if (ws.sqlConditionClause.sqlConditionClauseLength.greaterThan(0)) {
      // COB(4529): SUBTRACT 1 FROM SQL-CONDITION-CLAUSE-LENGTH
      ws.sqlConditionClause.sqlConditionClauseLength.subtract(1);
      // COB(4530): STRING 'WHERE '
      // COB(4530): SQL-CONDITION-CLAUSE-TEXT(1:SQL-CONDITION-CLAUSE-LENGTH)
      // COB(4530): ' ' DELIMITED BY SIZE INTO WS-WHERE
      ws.wsWhere.concat(
          "WHERE ",
          ws.sqlConditionClause.sqlConditionClauseText.getString(
              0, ws.sqlConditionClause.sqlConditionClauseLength.getInt()),
          " ");
      // COB(4533): COMPUTE WS-WHERE-LEN = SQL-CONDITION-CLAUSE-LENGTH + 7
      ws.wsWhereLen.setValue(ws.sqlConditionClause.sqlConditionClauseLength.getInt() + 7);
      // COB(4534): INSPECT
      // COB(4534):     WS-WHERE
      // COB(4534): REPLACING
      // COB(4534):   ALL '_________________'
      // COB(4534):   BY  'KEY_ACCNTID      '
      ws.wsWhere.inspectAndReplaceAll("_________________", "KEY_ACCNTID      ");
      // COB(4539): END-IF
    }
    // COB(4541): MOVE ' ORDER BY KEY_ACCNTID ' TO WS-ORDERBY
    ws.wsOrderby.setString(" ORDER BY KEY_ACCNTID ");
    // COB(4542): COMPUTE WS-ORDERBY-LEN = 22
    ws.wsOrderbyLen.setValue(22);
    // COB(4543): IF COMMAND-CODE-LAST
    if (ws.commandCodeLast()) {
      // COB(4544): STRING WS-ORDERBY(1:WS-ORDERBY-LEN)
      // COB(4544): ' DESC ' DELIMITED BY SIZE
      // COB(4544): INTO WS-ORDERBY
      ws.wsOrderby.concat(ws.wsOrderby.getString(0, ws.wsOrderbyLen.getInt()), " DESC ");
      // COB(4547): ADD 6 TO WS-ORDERBY-LEN
      ws.wsOrderbyLen.add(6);
      // COB(4548): END-IF
    }
    // COB(4553): MOVE 1 TO WS-SQL-STM-LEN
    //       *
    //       *
    //       *    PREPARE THE DYNAMIC QUERY
    //       *
    ws.wsSqlStm.wsSqlStmLen.setValue(1);
    // COB(4554): STRING
    // COB(4554): 'SELECT '
    // COB(4554):   'DBPAUTP0_PAUTSUM0.KEY_ACCNTID '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_CUST_ID '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_AUTH_STATUS '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS1 '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS2 '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS3 '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS4 '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS5 '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_CREDIT_LIMIT '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_CASH_LIMIT '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_CREDIT_BALANCE '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_CASH_BALANCE '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_APPROVED_AUTH_CNT '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_DECLINED_AUTH_CNT '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_APPROVED_AUTH_AMT '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PA_DECLINED_AUTH_AMT '
    // COB(4554):  ',DBPAUTP0_PAUTSUM0.PAUTSUM0_FILLER '
    // COB(4554): 'FROM '
    // COB(4554): 'DBPAUTP0_PAUTSUM0 '
    // COB(4554): DELIMITED BY SIZE
    // COB(4554): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
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
    // COB(4577): IF WS-WHERE-LEN > 0
    if (ws.wsWhereLen.greaterThan(0)) {
      // COB(4578): STRING
      // COB(4578): WS-WHERE(1:WS-WHERE-LEN)
      // COB(4578): DELIMITED BY SIZE
      // COB(4578): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(ws.wsWhere.getString(0, ws.wsWhereLen.getInt())));
      // COB(4582): IF IRIS-TRACE-FULL
      if (params.irisWorkArea.irisTraceFull()) {
        // COB(4583): MOVE 0 TO IRIS-MSG-LEN
        params.irisWorkArea.irisMsgDetail.irisMsgLen.setValue(0);
        // COB(4584): STRING '<IRISTRACE> - DBPAUTX0:SELECT-DYNAMIC' NL
        // COB(4584):      ' SECTION    =(PAUTINDX-SELECT-DYNAMIC) ' NL
        // COB(4584):      ' CONDITION  =(' WS-WHERE(1:WS-WHERE-LEN) ')' NL
        // COB(4584):      ' ORDER BY   =(' WS-ORDERBY(1:WS-ORDERBY-LEN) ')'
        // COB(4584): MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
        params.irisWorkArea.irisMsgDetail.irisMsgTxt.concat(
            "<IRISTRACE> - DBPAUTX0:SELECT-DYNAMIC",
            ws.iriscomm.nl,
            " SECTION    =(PAUTINDX-SELECT-DYNAMIC) ",
            ws.iriscomm.nl,
            " CONDITION  =(",
            ws.wsWhere.getString(0, ws.wsWhereLen.getInt()),
            ")",
            ws.iriscomm.nl,
            " ORDER BY   =(",
            ws.wsOrderby.getString(0, ws.wsOrderbyLen.getInt()),
            ")",
            ws.iriscomm.messageEnd);
        // COB(4589): SET IRISTRAC-RTN TO TRUE
        ws.iriscomm.setIristracRtn(true);
        // COB(4590): CALL IRIS-WS-RTN USING IRIS-WORK-AREA
        context.call(ws.iriscomm.irisWsRtn, params.irisWorkArea);
        // COB(4591): END-IF
      }
      // COB(4592): END-IF
    }
    // COB(4593): IF WS-ORDERBY-LEN > 0
    if (ws.wsOrderbyLen.greaterThan(0)) {
      // COB(4594): STRING WS-ORDERBY(1:WS-ORDERBY-LEN) DELIMITED BY SIZE
      // COB(4594): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
      ws.wsSqlStm.wsSqlStmLen.setValue(
          ws.wsSqlStm.wsSqlStmTxt.concat(ws.wsOrderby.getString(0, ws.wsOrderbyLen.getInt())));
      // COB(4596): END-IF
    }
    // COB(4597): STRING ' FETCH FIRST 1 ROW ONLY '
    // COB(4597): DELIMITED BY SIZE
    // COB(4597): INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
    ws.wsSqlStm.wsSqlStmLen.setValue(ws.wsSqlStm.wsSqlStmTxt.concat(" FETCH FIRST 1 ROW ONLY "));
    // COB(4600): SUBTRACT 1 FROM WS-SQL-STM-LEN
    ws.wsSqlStm.wsSqlStmLen.subtract(1);
    // COB(4604): EXEC SQL
    // COB(4604):   DECLARE PAUTINDX_CRS CURSOR
    // COB(4604):   FOR PAUTINDX_STATEMENT
    // COB(4604): END-EXEC
    //       *
    //       *    DECLARE THE DYNAMIC CURSOR
    //       *
    // do nothing
    // COB(4611): EXEC SQL
    // COB(4611):   DECLARE PAUTINDX_STATEMENT STATEMENT
    // COB(4611): END-EXEC
    //       *
    //       *    DECLARE THE SQL STATEMENT
    //       *
    // do nothing
    // COB(4617): EXEC SQL
    // COB(4617):   PREPARE PAUTINDX_STATEMENT
    // COB(4617):   INTO :SQLDA
    // COB(4617):   FROM :WS-SQL-STM
    // COB(4617): END-EXEC
    //       *
    //       *    PREPARE THE STATEMENT
    //       *
    pautindxCrs = new NSqlCursor(this, sqlca, ws.wsSqlStm.toString());
    // COB(4625): IF SQLCODE NOT = ZERO
    //       *
    //       *    TEST THE RETURN CODE
    //       *
    if (!sqlca.sqlcode.equals(0)) {
      // COB(4626): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(4627): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(4628): GO TO PAUTINDX-SELECT-DYNAMIC-EX
      return;
      // COB(4629): END-IF
    }
    // COB(4633): EXEC SQL
    // COB(4633):   OPEN PAUTINDX_CRS
    // COB(4633): END-EXEC
    //       *
    //       *    OPEN THE DYNAMIC CURSOR
    //       *
    pautindxCrs.open();
    // COB(4639): IF SQLCODE NOT = ZERO
    //       *
    //       *    TEST THE RETURN CODE
    //       *
    if (!sqlca.sqlcode.equals(0)) {
      // COB(4640): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(4641): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(4642): GO TO PAUTINDX-SELECT-DYNAMIC-EX
      return;
      // COB(4643): END-IF
    }
    // COB(4647): EXEC SQL
    // COB(4647):   FETCH PAUTINDX_CRS
    // COB(4647):   INTO
    // COB(4647):     :PAUTSUM0.KEY-ACCNTID
    // COB(4647):    ,:PAUTSUM0.PA-CUST-ID
    // COB(4647):    ,:PAUTSUM0.PA-AUTH-STATUS
    // COB(4647):    ,:PAUTSUM0.PA-ACCOUNT-STATUS1
    // COB(4647):    ,:PAUTSUM0.PA-ACCOUNT-STATUS2
    // COB(4647):    ,:PAUTSUM0.PA-ACCOUNT-STATUS3
    // COB(4647):    ,:PAUTSUM0.PA-ACCOUNT-STATUS4
    // COB(4647):    ,:PAUTSUM0.PA-ACCOUNT-STATUS5
    // COB(4647):    ,:PAUTSUM0.PA-CREDIT-LIMIT
    // COB(4647):    ,:PAUTSUM0.PA-CASH-LIMIT
    // COB(4647):    ,:PAUTSUM0.PA-CREDIT-BALANCE
    // COB(4647):    ,:PAUTSUM0.PA-CASH-BALANCE
    // COB(4647):    ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
    // COB(4647):    ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
    // COB(4647):    ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
    // COB(4647):    ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
    // COB(4647):    ,:PAUTSUM0.PAUTSUM0-FILLER
    // COB(4647): END-EXEC
    //       *
    //       *    FETCH THE DYNAMIC CURSOR
    //       *
    pautindxCrs.fetch(
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
    // COB(4669): IF SQLCODE NOT = ZERO
    if (!sqlca.sqlcode.equals(0)) {
      // COB(4670): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
      params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
      ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
      // COB(4671): MOVE SQLERRM TO IRIS-SQLERRM
      params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
          sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
      // COB(4675): EXEC SQL
      // COB(4675):   CLOSE PAUTINDX_CRS
      // COB(4675): END-EXEC
      //       *
      //       *      CLOSE THE DYNAMIC CURSOR
      //       *
      pautindxCrs.close();
      // COB(4678): GO TO PAUTINDX-SELECT-DYNAMIC-EX
      return;
      // COB(4679): END-IF
    }
    // COB(4681): MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
    params.irisWorkArea.irisSqlError.irisSqlcode.setValue(sqlca.sqlcode);
    ws.irissqlc.irisDbSqlcode.setValue(sqlca.sqlcode);
    // COB(4682): MOVE SQLERRM TO IRIS-SQLERRM
    params.irisWorkArea.irisSqlError.irisSqlerrm.setValue(
        sqlca.sqlerrmc.getString(0, sqlca.sqlerrml.getInt()));
    // COB(4686): EXEC SQL
    // COB(4686):   CLOSE PAUTINDX_CRS
    // COB(4686): END-EXEC
    //       *
    //       *      CLOSE THE DYNAMIC CURSOR
    //       *
    pautindxCrs.close();
    // COB(4689): SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTINDX-LVL) TO TRUE
    params
        .irisDbPcb
        .ioRtnUsedKeysPcbArea
        .ioRtnUsedKeysArea
        .ioRtnUsedKeyValueAtIndex(ws.pautindxLvl.getInt() - 1)
        .setIoRtnUsedKeyNotChanged(true);
    // COB(4690): IF IRIS-SQLCODE = ZERO
    if (params.irisWorkArea.irisSqlError.irisSqlcode.equals(0)) {
      // COB(4691): MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(4692): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(4692):                    TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(0)
          .ioRtnUsedSsaKeys
          .ioRtnUsedKeyAlpha
          .setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(4694): MOVE KEY-ACCNTID OF PAUTSUM0
      // COB(4694):                                   TO WS-PACKED-FLD-18-00(1)
      ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
      // COB(4696): MOVE WS-PACKED-FLD-CHR(1)(5:6)
      // COB(4696):                                      TO WS-FB-KEY-AREA(1:6)
      ws.wsFbKeyArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), 4, 0, 6);
      // COB(4698): MOVE 6 TO WS-FB-KEY-LENGTH
      ws.wsFbKeyLength.setValue(6);
      // COB(4699): IF SQL-SELECT-DYNAMIC
      // COB(4699): AND NOT COMMAND-WITH-HOLD
      if (params.irisWorkArea.sqlSelectDynamic() && !ws.commandWithHold()) {
        // COB(4701): MOVE 6 TO WS-SEGMENT-LEN
        ws.wsSegmentLen.setValue(6);
        // COB(4702): PERFORM PAUTINDX-SET-IO-AREA
        pautindxSetIoArea();
        // COB(4703): END-IF
      }
      // COB(4704): SET IO-RTN-USED-KEY-CHANGED(PAUTINDX-LVL) TO TRUE
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautindxLvl.getInt() - 1)
          .setIoRtnUsedKeyChanged(true);
      // COB(4705): MOVE 'PAUTINDX' TO WS-LAST-SEGMENT-NAME
      // COB(4705):                 IO-RTN-USED-LAST-SEGMENT(PAUTINDX-LVL)
      ws.wsLastSegmentName.setString("PAUTINDX");
      params
          .irisDbPcb
          .ioRtnUsedKeysPcbArea
          .ioRtnUsedKeysArea
          .ioRtnUsedKeyValueAtIndex(ws.pautindxLvl.getInt() - 1)
          .ioRtnUsedSsaKeys
          .ioRtnUsedLastSegment
          .setString("PAUTINDX");
      // COB(4707): END-IF.
    }
  }

  /***
   ******************************************************************
   *    INSERT FOR SEGMENT PAUTINDX
   ******************************************************************
   */
  protected void pautindxInsert() {
    // COB(4718): MOVE 'PAUTINDX-INSERT' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTINDX-INSERT");
    // COB(4720): SET IRIS-ERR-WRONG-ALT-INDEX TO TRUE.
    params.irisWorkArea.setIrisErrWrongAltIndex(true);
  }

  /***
   ******************************************************************
   *    UPDATE FOR SEGMENT PAUTINDX
   ******************************************************************
   */
  protected void pautindxUpdate() {
    // COB(4731): MOVE 'PAUTINDX-UPDATE' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTINDX-UPDATE");
    // COB(4733): SET IRIS-ERR-WRONG-ALT-INDEX TO TRUE.
    params.irisWorkArea.setIrisErrWrongAltIndex(true);
  }

  /***
   ******************************************************************
   *    DELETE FOR SEGMENT PAUTINDX
   ******************************************************************
   */
  protected void pautindxDelete() {
    // COB(4744): MOVE 'PAUTINDX-DELETE' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTINDX-DELETE");
    // COB(4746): SET IRIS-ERR-WRONG-ALT-INDEX TO TRUE.
    params.irisWorkArea.setIrisErrWrongAltIndex(true);
  }

  /***
   ******************************************************************
   *    SET I/O AREA PAUTINDX
   ******************************************************************
   */
  protected void pautindxSetIoArea() {
    // COB(4757): MOVE 'PAUTINDX-SET-IO-AREA' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("PAUTINDX-SET-IO-AREA");
    // COB(4759): MOVE KEY-ACCNTID OF PAUTSUM0
    // COB(4759):   TO WS-PACKED-FLD-18-00(1)
    ws.filler174.wsPackedFld1800AtIndex(0).setValue(ws.pautsum0copy.pautsum0.keyAccntid);
    // COB(4761): MOVE WS-PACKED-FLD-CHR(1)(1:WS-SEGMENT-LEN)
    // COB(4761):           TO LK-IO-AREA(1:WS-SEGMENT-LEN).
    params.lkIoArea.setValue(ws.filler250.wsPackedFldChrAtIndex(0), ws.wsSegmentLen.getInt());
  }

  /***
   *
   ******************************************************************
   *    SET DIB BLOCK
   ******************************************************************
   */
  protected void setDibBlock() {
    // COB(4774): MOVE 'SET-DIB-BLOCK' TO WS-LAST-IORTN-SECTION
    ws.wsLastIortnSection.setString("SET-DIB-BLOCK");
    // COB(4776): MOVE 'IR'                   TO IRIS-DIBVER
    ws.iriscomm.dlzdib.irisDibver.setString("IR");
    // COB(4777): MOVE DB-PCB-STATUS-CODE     TO IRIS-DIBSTAT
    ws.iriscomm.dlzdib.irisDibstat.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbStatusCode);
    // COB(4778): MOVE DB-PCB-SEGMENT-NAME    TO IRIS-DIBSEGM
    ws.iriscomm.dlzdib.irisDibsegm.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbSegmentName);
    // COB(4779): MOVE DB-PCB-SEGMENT-LEVEL   TO IRIS-DIBSEGLV
    ws.iriscomm.dlzdib.irisDibseglv.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbSegmentLevel);
    // COB(4780): MOVE DB-PCB-FB-KEY-LENGTH   TO IRIS-DIBKFBL
    ws.iriscomm.dlzdib.irisDibkfbl.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbFbKeyLength);
    // COB(4781): MOVE DB-PCB-DBD-NAME        TO IRIS-DIBDBDNM
    ws.iriscomm.dlzdib.irisDibdbdnm.setValue(params.irisDbPcb.dbPcbFixedPart.dbPcbDbdName);
    // COB(4782): MOVE 'IRIS'                 TO IRIS-DIBDBORG
    ws.iriscomm.dlzdib.irisDibdborg.setString("IRIS");
    // COB(4783): MOVE IRIS-DIB-BLOCK         TO LK-DIB-BLOCK
    params.lkDibBlock.setValue(ws.iriscomm.irisDibBlock);
    // COB(4784): SET ADDRESS OF IRIS-DB-PCB  TO ADDRESS OF LK-DIB-BLOCK.
    params.irisDbPcb.setAddress(params.lkDibBlock.getAddress());
  }
}
