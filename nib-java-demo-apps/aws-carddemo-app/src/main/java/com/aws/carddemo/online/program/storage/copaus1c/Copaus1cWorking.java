package com.aws.carddemo.online.program.storage.copaus1c;

import com.aws.carddemo.common.copybook.*;
import com.nib.commons.storage.*;

public class Copaus1cWorking extends NGroup {
  public Iriscomm iriscomm = new Iriscomm();
  // COB:        01 IRIS-WORK-AREA.
  public IrisWorkArea irisWorkArea = new IrisWorkArea();

  public static class IrisWorkArea extends NGroup {

    // COB:          03 IRIS-PROGRAM-NAME             PIC X(8)  VALUE :PROGNM:.
    public NChar irisProgramName = new NChar(8).initial("COPAUS1C");
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
      return irisTraceLevel.equals("1") || irisTraceLevel.equals("2") || irisTraceLevel.equals("3");
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

          public IrisKeyvaluesP[] irisKeyvaluesP = AbstractNField.occurs(64, new IrisKeyvaluesP());

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

  // COB:        01 WS-VARIABLES.
  public WsVariables wsVariables = new WsVariables();

  public static class WsVariables extends NGroup {

    // COB:          05 WS-PGM-AUTH-DTL            PIC X(08) VALUE 'COPAUS1C'.
    public NChar wsPgmAuthDtl = new NChar(8).initial("COPAUS1C");
    // COB:          05 WS-PGM-AUTH-SMRY           PIC X(08) VALUE 'COPAUS0C'.
    public NChar wsPgmAuthSmry = new NChar(8).initial("COPAUS0C");
    // COB:          05 WS-PGM-AUTH-FRAUD          PIC X(08) VALUE 'COPAUS2C'.
    public NChar wsPgmAuthFraud = new NChar(8).initial("COPAUS2C");
    // COB:          05 WS-CICS-TRANID             PIC X(04) VALUE 'CPVD'.
    public NChar wsCicsTranid = new NChar(4).initial("CPVD");
    // COB:          05 WS-MESSAGE                 PIC X(80) VALUE SPACES.
    public NChar wsMessage = new NChar(80).initial(NChar.Space);
    // COB:          05 WS-ERR-FLG                 PIC X(01) VALUE 'N'.
    public NChar wsErrFlg = new NChar(1);

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

    // COB:          05 WS-AUTHS-EOF               PIC X(01) VALUE 'N'.
    public NChar wsAuthsEof = new NChar(1);

    // COB:            88 AUTHS-EOF                          VALUE 'Y'.
    public boolean authsEof() {
      return wsAuthsEof.equals("Y");
    }

    public void setAuthsEof(boolean value) {
      if (value) wsAuthsEof.setValue("Y");
    }

    // COB:            88 AUTHS-NOT-EOF                      VALUE 'N'.
    public boolean authsNotEof() {
      return wsAuthsEof.equals("N");
    }

    public void setAuthsNotEof(boolean value) {
      if (value) wsAuthsEof.setValue("N");
    }

    // COB:          05 WS-SEND-ERASE-FLG          PIC X(01) VALUE 'Y'.
    public NChar wsSendEraseFlg = new NChar(1);

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

    // COB:          05 WS-RESP-CD                 PIC S9(09) COMP VALUE ZEROS.
    public NInt32 wsRespCd = new NInt32().initial(0);
    // COB:          05 WS-REAS-CD                 PIC S9(09) COMP VALUE ZEROS.
    public NInt32 wsReasCd = new NInt32().initial(0);
    // COB:          05 WS-ACCT-ID                 PIC  9(11).
    public NZoned wsAcctId = new NZoned(11, false);
    // COB:          05 WS-AUTH-KEY                PIC  X(08).
    public NChar wsAuthKey = new NChar(8);
    // COB:          05 WS-AUTH-AMT                PIC -zzzzzzz9.99.
    public NZoned wsAuthAmt = new NZoned(12).formatAs("-#######0.00");
    // COB:          05 WS-AUTH-DATE               PIC X(08) VALUE '00/00/00'.
    public NChar wsAuthDate = new NChar(8).initial("00/00/00");
    // COB:          05 WS-AUTH-TIME               PIC X(08) VALUE '00:00:00'.
    public NChar wsAuthTime = new NChar(8).initial("00:00:00");

    public NChar wsCustfilename = new NChar(8).initial("CUSTDAT ");
    public NZoned wsRespCdDis = new NZoned(9, false);
    public NZoned wsReasCdDis = new NZoned(9, false);
  }

  // COB:        01 WS-TABLES.
  public WsTables wsTables = new WsTables();

  public static class WsTables extends NGroup {

    // COB:           05 WS-DECLINE-REASON-TABLE.
    public static class WsDeclineReasonTable extends NGroup {
      // COB:              10   PIC X(20) VALUE '0000APPROVED'.
      public NChar filler63 = new NChar(20).initial("0000APPROVED");
      // COB:              10   PIC X(20) VALUE '3100INVALID CARD'.
      public NChar filler64 = new NChar(20).initial("3100INVALID CARD");
      // COB:              10   PIC X(20) VALUE '4100INSUFFICNT FUND'.
      public NChar filler65 = new NChar(20).initial("4100INSUFFICNT FUND");
      // COB:              10   PIC X(20) VALUE '4200CARD NOT ACTIVE'.
      public NChar filler66 = new NChar(20).initial("4200CARD NOT ACTIVE");
      // COB:              10   PIC X(20) VALUE '4300ACCOUNT CLOSED'.
      public NChar filler67 = new NChar(20).initial("4300ACCOUNT CLOSED");
      // COB:              10   PIC X(20) VALUE '4400EXCED DAILY LMT'.
      public NChar filler68 = new NChar(20).initial("4400EXCED DAILY LMT");
      // COB:              10   PIC X(20) VALUE '5100CARD FRAUD'.
      public NChar filler69 = new NChar(20).initial("5100CARD FRAUD");
      // COB:              10   PIC X(20) VALUE '5200MERCHANT FRAUD'.
      public NChar filler70 = new NChar(20).initial("5200MERCHANT FRAUD");
      // COB:              10   PIC X(20) VALUE '5300LOST CARD'.
      public NChar filler71 = new NChar(20).initial("5300LOST CARD");
      // COB:              10   PIC X(20) VALUE '9000UNKNOWN'.
      public NChar filler72 = new NChar(20).initial("9000UNKNOWN");
    }

    public WsDeclineReasonTable wsDeclineReasonTable = new WsDeclineReasonTable();

    // COB:           05 FILLER REDEFINES WS-DECLINE-REASON-TABLE.
    public static class Filler73 extends NGroup {
      // COB:             07 WS-DECLINE-REASON-TAB OCCURS 10 TIMES
      public static class WsDeclineReasonTab extends NGroup {
        // COB:              10 DECL-CODE                PIC X(4).
        public NChar declCode = new NChar(4);
        // COB:              10 DECL-DESC                PIC X(16).
        public NChar declDesc = new NChar(16);
      }

      public WsDeclineReasonTab[] wsDeclineReasonTab =
          AbstractNField.occurs(10, new WsDeclineReasonTab());

      public WsDeclineReasonTab wsDeclineReasonTabAtIndex(int index) {
        return getOccursInstance(wsDeclineReasonTab, index);
      }
    }

    public Filler73 filler73 = (Filler73) new Filler73().redefines(wsDeclineReasonTable);
  }

  // COB:        01 WS-IMS-VARIABLES.
  public WsImsVariables wsImsVariables = new WsImsVariables();

  public static class WsImsVariables extends NGroup {

    // COB:           05 PSB-NAME                        PIC X(8) VALUE 'PSBPAUTB'.
    public NChar psbName = new NChar(8).initial("PSBPAUTB");

    // COB:           05 PCB-OFFSET.
    public static class PcbOffset extends NGroup {
      // COB: IRISPS       10 PAUT-PCB-NUM                 PIC S9(4) COMP VALUE +2.
      public NInt16 pautPcbNum = new NInt16().initial(2);
    }

    public PcbOffset pcbOffset = new PcbOffset();
    // COB:           05 IMS-RETURN-CODE                 PIC X(02).
    public NChar imsReturnCode = new NChar(2);

    // COB:              88 STATUS-OK                    VALUE '  ', 'FW'.
    public boolean statusOk() {
      return imsReturnCode.equals("  ") || imsReturnCode.equals("FW");
    }

    public void setStatusOk(boolean value) {
      if (value) imsReturnCode.setValue("  ");
    }

    // COB:              88 SEGMENT-NOT-FOUND            VALUE 'GE'.
    public boolean segmentNotFound() {
      return imsReturnCode.equals("GE");
    }

    public void setSegmentNotFound(boolean value) {
      if (value) imsReturnCode.setValue("GE");
    }

    // COB:              88 DUPLICATE-SEGMENT-FOUND      VALUE 'II'.
    public boolean duplicateSegmentFound() {
      return imsReturnCode.equals("II");
    }

    public void setDuplicateSegmentFound(boolean value) {
      if (value) imsReturnCode.setValue("II");
    }

    // COB:              88 WRONG-PARENTAGE              VALUE 'GP'.
    public boolean wrongParentage() {
      return imsReturnCode.equals("GP");
    }

    public void setWrongParentage(boolean value) {
      if (value) imsReturnCode.setValue("GP");
    }

    // COB:              88 END-OF-DB                    VALUE 'GB'.
    public boolean endOfDb() {
      return imsReturnCode.equals("GB");
    }

    public void setEndOfDb(boolean value) {
      if (value) imsReturnCode.setValue("GB");
    }

    // COB:              88 DATABASE-UNAVAILABLE         VALUE 'BA'.
    public boolean databaseUnavailable() {
      return imsReturnCode.equals("BA");
    }

    public void setDatabaseUnavailable(boolean value) {
      if (value) imsReturnCode.setValue("BA");
    }

    // COB:              88 PSB-SCHEDULED-MORE-THAN-ONCE VALUE 'TC'.
    public boolean psbScheduledMoreThanOnce() {
      return imsReturnCode.equals("TC");
    }

    public void setPsbScheduledMoreThanOnce(boolean value) {
      if (value) imsReturnCode.setValue("TC");
    }

    // COB:              88 COULD-NOT-SCHEDULE-PSB       VALUE 'TE'.
    public boolean couldNotSchedulePsb() {
      return imsReturnCode.equals("TE");
    }

    public void setCouldNotSchedulePsb(boolean value) {
      if (value) imsReturnCode.setValue("TE");
    }

    // COB:              88 RETRY-CONDITION              VALUE 'BA', 'FH', 'TE'.
    public boolean retryCondition() {
      return imsReturnCode.equals("BA") || imsReturnCode.equals("FH") || imsReturnCode.equals("TE");
    }

    public void setRetryCondition(boolean value) {
      if (value) imsReturnCode.setValue("BA");
    }

    // COB:           05 WS-IMS-PSB-SCHD-FLG             PIC X(1).
    public NChar wsImsPsbSchdFlg = new NChar(1);

    // COB:              88  IMS-PSB-SCHD                VALUE 'Y'.
    public boolean imsPsbSchd() {
      return wsImsPsbSchdFlg.equals("Y");
    }

    public void setImsPsbSchd(boolean value) {
      if (value) wsImsPsbSchdFlg.setValue("Y");
    }

    // COB:              88  IMS-PSB-NOT-SCHD            VALUE 'N'.
    public boolean imsPsbNotSchd() {
      return wsImsPsbSchdFlg.equals("N");
    }

    public void setImsPsbNotSchd(boolean value) {
      if (value) wsImsPsbSchdFlg.setValue("N");
    }
  }

  // COB:        01 WS-FRAUD-DATA.
  public WsFraudData wsFraudData = new WsFraudData();

  public static class WsFraudData extends NGroup {

    // COB:           02 WS-FRD-ACCT-ID                PIC 9(11).
    public NZoned wsFrdAcctId = new NZoned(11, false);
    // COB:           02 WS-FRD-CUST-ID                PIC 9(9).
    public NZoned wsFrdCustId = new NZoned(9, false);
    // COB:           02 WS-FRAUD-AUTH-RECORD          PIC X(200).
    public NChar wsFraudAuthRecord = new NChar(200);

    // COB:           02 WS-FRAUD-STATUS-RECORD.
    public static class WsFraudStatusRecord extends NGroup {
      // COB:              05 WS-FRD-ACTION              PIC X(01).
      public NChar wsFrdAction = new NChar(1);

      // COB:                 88 WS-REPORT-FRAUD         VALUE 'F'.
      public boolean wsReportFraud() {
        return wsFrdAction.equals("F");
      }

      public void setWsReportFraud(boolean value) {
        if (value) wsFrdAction.setValue("F");
      }

      // COB:                 88 WS-REMOVE-FRAUD         VALUE 'R'.
      public boolean wsRemoveFraud() {
        return wsFrdAction.equals("R");
      }

      public void setWsRemoveFraud(boolean value) {
        if (value) wsFrdAction.setValue("R");
      }

      // COB:              05 WS-FRD-UPDATE-STATUS       PIC X(01).
      public NChar wsFrdUpdateStatus = new NChar(1);

      // COB:                 88 WS-FRD-UPDT-SUCCESS     VALUE 'S'.
      public boolean wsFrdUpdtSuccess() {
        return wsFrdUpdateStatus.equals("S");
      }

      public void setWsFrdUpdtSuccess(boolean value) {
        if (value) wsFrdUpdateStatus.setValue("S");
      }

      // COB:                 88 WS-FRD-UPDT-FAILED      VALUE 'F'.
      public boolean wsFrdUpdtFailed() {
        return wsFrdUpdateStatus.equals("F");
      }

      public void setWsFrdUpdtFailed(boolean value) {
        if (value) wsFrdUpdateStatus.setValue("F");
      }

      // COB:              05 WS-FRD-ACT-MSG             PIC X(50).
      public NChar wsFrdActMsg = new NChar(50);
    }

    public WsFraudStatusRecord wsFraudStatusRecord = new WsFraudStatusRecord();
  }

  // COB:        01 CARDDEMO-COMMAREA.
  public CarddemoCommarea carddemoCommarea = new CarddemoCommarea();

  public static class CarddemoCommarea extends NGroup {

    // COB:           05 CDEMO-GENERAL-INFO.
    public static class CdemoGeneralInfo extends NGroup {
      // COB:              10 CDEMO-FROM-TRANID             PIC X(04).
      public NChar cdemoFromTranid = new NChar(4);
      // COB:              10 CDEMO-FROM-PROGRAM            PIC X(08).
      public NChar cdemoFromProgram = new NChar(8);
      // COB:              10 CDEMO-TO-TRANID               PIC X(04).
      public NChar cdemoToTranid = new NChar(4);
      // COB:              10 CDEMO-TO-PROGRAM              PIC X(08).
      public NChar cdemoToProgram = new NChar(8);
      // COB:              10 CDEMO-USER-ID                 PIC X(08).
      public NChar cdemoUserId = new NChar(8);
      // COB:              10 CDEMO-USER-TYPE               PIC X(01).
      public NChar cdemoUserType = new NChar(1);

      // COB:                 88 CDEMO-USRTYP-ADMIN         VALUE 'A'.
      public boolean cdemoUsrtypAdmin() {
        return cdemoUserType.equals("A");
      }

      public void setCdemoUsrtypAdmin(boolean value) {
        if (value) cdemoUserType.setValue("A");
      }

      // COB:                 88 CDEMO-USRTYP-USER          VALUE 'U'.
      public boolean cdemoUsrtypUser() {
        return cdemoUserType.equals("U");
      }

      public void setCdemoUsrtypUser(boolean value) {
        if (value) cdemoUserType.setValue("U");
      }

      // COB:              10 CDEMO-PGM-CONTEXT             PIC 9(01).
      public NZoned cdemoPgmContext = new NZoned(1, false);

      // COB:                 88 CDEMO-PGM-ENTER            VALUE 0.
      public boolean cdemoPgmEnter() {
        return cdemoPgmContext.equals(0);
      }

      public void setCdemoPgmEnter(boolean value) {
        if (value) cdemoPgmContext.setValue(0);
      }

      // COB:                 88 CDEMO-PGM-REENTER          VALUE 1.
      public boolean cdemoPgmReenter() {
        return cdemoPgmContext.equals(1);
      }

      public void setCdemoPgmReenter(boolean value) {
        if (value) cdemoPgmContext.setValue(1);
      }
    }

    public CdemoGeneralInfo cdemoGeneralInfo = new CdemoGeneralInfo();

    // COB:           05 CDEMO-CUSTOMER-INFO.
    public static class CdemoCustomerInfo extends NGroup {
      // COB:              10 CDEMO-CUST-ID                 PIC 9(09).
      public NZoned cdemoCustId = new NZoned(9, false);
      // COB:              10 CDEMO-CUST-FNAME              PIC X(25).
      public NChar cdemoCustFname = new NChar(25);
      // COB:              10 CDEMO-CUST-MNAME              PIC X(25).
      public NChar cdemoCustMname = new NChar(25);
      // COB:              10 CDEMO-CUST-LNAME              PIC X(25).
      public NChar cdemoCustLname = new NChar(25);
    }

    public CdemoCustomerInfo cdemoCustomerInfo = new CdemoCustomerInfo();

    // COB:           05 CDEMO-ACCOUNT-INFO.
    public static class CdemoAccountInfo extends NGroup {
      // COB:              10 CDEMO-ACCT-ID                 PIC 9(11).
      public NZoned cdemoAcctId = new NZoned(11, false);
      // COB:              10 CDEMO-ACCT-STATUS             PIC X(01).
      public NChar cdemoAcctStatus = new NChar(1);
    }

    public CdemoAccountInfo cdemoAccountInfo = new CdemoAccountInfo();

    // COB:           05 CDEMO-CARD-INFO.
    public static class CdemoCardInfo extends NGroup {
      // COB:              10 CDEMO-CARD-NUM                PIC 9(16).
      public NZoned cdemoCardNum = new NZoned(16, false);
    }

    public CdemoCardInfo cdemoCardInfo = new CdemoCardInfo();

    // COB:           05 CDEMO-MORE-INFO.
    public static class CdemoMoreInfo extends NGroup {
      // COB:              10  CDEMO-LAST-MAP               PIC X(7).
      public NChar cdemoLastMap = new NChar(7);
      // COB:              10  CDEMO-LAST-MAPSET            PIC X(7).
      public NChar cdemoLastMapset = new NChar(7);
    }

    public CdemoMoreInfo cdemoMoreInfo = new CdemoMoreInfo();

    // COB:           05 CDEMO-CPVD-INFO.
    public static class CdemoCpvdInfo extends NGroup {
      // COB:              10 CDEMO-CPVD-PAU-SEL-FLG     PIC X(01).
      public NChar cdemoCpvdPauSelFlg = new NChar(1);
      // COB:              10 CDEMO-CPVD-PAU-SELECTED    PIC X(08).
      public NChar cdemoCpvdPauSelected = new NChar(8);
      // COB:              10 CDEMO-CPVD-PAUKEY-PREV-PG  PIC X(08) OCCURS 20 TIMES.
      public NChar[] cdemoCpvdPaukeyPrevPg = AbstractNField.occurs(20, new NChar(8));

      public NChar cdemoCpvdPaukeyPrevPgAtIndex(int index) {
        return getOccursInstance(cdemoCpvdPaukeyPrevPg, index);
      }

      // COB:              10 CDEMO-CPVD-PAUKEY-LAST     PIC X(08).
      public NChar cdemoCpvdPaukeyLast = new NChar(8);
      // COB:              10 CDEMO-CPVD-PAGE-NUM        PIC S9(04) COMP.
      public NInt16 cdemoCpvdPageNum = new NInt16();
      // COB:              10 CDEMO-CPVD-NEXT-PAGE-FLG   PIC X(01) VALUE 'N'.
      public NChar cdemoCpvdNextPageFlg = new NChar(1);

      // COB:                 88 NEXT-PAGE-YES                     VALUE 'Y'.
      public boolean nextPageYes() {
        return cdemoCpvdNextPageFlg.equals("Y");
      }

      public void setNextPageYes(boolean value) {
        if (value) cdemoCpvdNextPageFlg.setValue("Y");
      }

      // COB:                 88 NEXT-PAGE-NO                      VALUE 'N'.
      public boolean nextPageNo() {
        return cdemoCpvdNextPageFlg.equals("N");
      }

      public void setNextPageNo(boolean value) {
        if (value) cdemoCpvdNextPageFlg.setValue("N");
      }

      // COB:              10 CDEMO-CPVD-AUTH-KEYS       PIC X(08) OCCURS 5 TIMES.
      public NChar[] cdemoCpvdAuthKeys = AbstractNField.occurs(5, new NChar(8));

      public NChar cdemoCpvdAuthKeysAtIndex(int index) {
        return getOccursInstance(cdemoCpvdAuthKeys, index);
      }

      // COB:              10 CDEMO-CPVD-FRAUD-DATA      PIC X(100).
      public NChar cdemoCpvdFraudData = new NChar(100);
    }

    public CdemoCpvdInfo cdemoCpvdInfo = new CdemoCpvdInfo();
  }

  public Copau01 copau01 = new Copau01();
  public Cottl01y cottl01y = new Cottl01y();
  public Csdat01y csdat01y = new Csdat01y();
  public Csmsg01y csmsg01y = new Csmsg01y();
  public Csmsg02y csmsg02y = new Csmsg02y();
  public Cvcus01y cvcus01y = new Cvcus01y();
  // COB:        01 PENDING-AUTH-SUMMARY.
  public PendingAuthSummary pendingAuthSummary = new PendingAuthSummary();

  public static class PendingAuthSummary extends NGroup {

    // COB:            05  PA-ACCT-ID                   PIC S9(11) COMP-3.
    public NPacked paAcctId = new NPacked(6);
    // COB:            05  PA-CUST-ID                   PIC  9(09).
    public NZoned paCustId = new NZoned(9, false);
    // COB:            05  PA-AUTH-STATUS               PIC  X(01).
    public NChar paAuthStatus = new NChar(1);
    // COB:            05  PA-ACCOUNT-STATUS            PIC  X(02) OCCURS 5 TIMES.
    public NChar[] paAccountStatus = AbstractNField.occurs(5, new NChar(2));

    public NChar paAccountStatusAtIndex(int index) {
      return getOccursInstance(paAccountStatus, index);
    }

    // COB:            05  PA-CREDIT-LIMIT              PIC S9(09)V99 COMP-3.
    public NPacked paCreditLimit = new NPacked(6, 2);
    // COB:            05  PA-CASH-LIMIT                PIC S9(09)V99 COMP-3.
    public NPacked paCashLimit = new NPacked(6, 2);
    // COB:            05  PA-CREDIT-BALANCE            PIC S9(09)V99 COMP-3.
    public NPacked paCreditBalance = new NPacked(6, 2);
    // COB:            05  PA-CASH-BALANCE              PIC S9(09)V99 COMP-3.
    public NPacked paCashBalance = new NPacked(6, 2);
    // COB:            05  PA-APPROVED-AUTH-CNT         PIC S9(04) COMP.
    public NInt16 paApprovedAuthCnt = new NInt16();
    // COB:            05  PA-DECLINED-AUTH-CNT         PIC S9(04) COMP.
    public NInt16 paDeclinedAuthCnt = new NInt16();
    // COB:            05  PA-APPROVED-AUTH-AMT         PIC S9(09)V99 COMP-3.
    public NPacked paApprovedAuthAmt = new NPacked(6, 2);
    // COB:            05  PA-DECLINED-AUTH-AMT         PIC S9(09)V99 COMP-3.
    public NPacked paDeclinedAuthAmt = new NPacked(6, 2);
    // COB:            05  FILLER                       PIC X(34).
    public NChar filler31 = new NChar(34);
  }

  // COB:        01 PENDING-AUTH-DETAILS.
  public PendingAuthDetails pendingAuthDetails = new PendingAuthDetails();

  public static class PendingAuthDetails extends NGroup {

    // COB:            05  PA-AUTHORIZATION-KEY.
    public static class PaAuthorizationKey extends NGroup {
      // COB:                10 PA-AUTH-DATE-9C           PIC S9(05) COMP-3.
      public NPacked paAuthDate9c = new NPacked(3);
      // COB:                10 PA-AUTH-TIME-9C           PIC S9(09) COMP-3.
      public NPacked paAuthTime9c = new NPacked(5);
    }

    public PaAuthorizationKey paAuthorizationKey = new PaAuthorizationKey();
    // COB:            05  PA-AUTH-ORIG-DATE            PIC  X(06).
    public NChar paAuthOrigDate = new NChar(6);
    // COB:            05  PA-AUTH-ORIG-TIME            PIC  X(06).
    public NChar paAuthOrigTime = new NChar(6);
    // COB:            05  PA-CARD-NUM                  PIC  X(16).
    public NChar paCardNum = new NChar(16);
    // COB:            05  PA-AUTH-TYPE                 PIC  X(04).
    public NChar paAuthType = new NChar(4);
    // COB:            05  PA-CARD-EXPIRY-DATE          PIC  X(04).
    public NChar paCardExpiryDate = new NChar(4);
    // COB:            05  PA-MESSAGE-TYPE              PIC  X(06).
    public NChar paMessageType = new NChar(6);
    // COB:            05  PA-MESSAGE-SOURCE            PIC  X(06).
    public NChar paMessageSource = new NChar(6);
    // COB:            05  PA-AUTH-ID-CODE              PIC  X(06).
    public NChar paAuthIdCode = new NChar(6);
    // COB:            05  PA-AUTH-RESP-CODE            PIC  X(02).
    public NChar paAuthRespCode = new NChar(2);

    // COB:                88 PA-AUTH-APPROVED          VALUE '00'.
    public boolean paAuthApproved() {
      return paAuthRespCode.equals("00");
    }

    public void setPaAuthApproved(boolean value) {
      if (value) paAuthRespCode.setValue("00");
    }

    // COB:            05  PA-AUTH-RESP-REASON          PIC  X(04).
    public NChar paAuthRespReason = new NChar(4);
    // COB:            05  PA-PROCESSING-CODE           PIC  9(06).
    public NZoned paProcessingCode = new NZoned(6, false);
    // COB:            05  PA-TRANSACTION-AMT           PIC S9(10)V99 COMP-3.
    public NPacked paTransactionAmt = new NPacked(7, 2);
    // COB:            05  PA-APPROVED-AMT              PIC S9(10)V99 COMP-3.
    public NPacked paApprovedAmt = new NPacked(7, 2);
    // COB:            05  PA-MERCHANT-CATAGORY-CODE    PIC  X(04).
    public NChar paMerchantCatagoryCode = new NChar(4);
    // COB:            05  PA-ACQR-COUNTRY-CODE         PIC  X(03).
    public NChar paAcqrCountryCode = new NChar(3);
    // COB:            05  PA-POS-ENTRY-MODE            PIC  9(02).
    public NZoned paPosEntryMode = new NZoned(2, false);
    // COB:            05  PA-MERCHANT-ID               PIC  X(15).
    public NChar paMerchantId = new NChar(15);
    // COB:            05  PA-MERCHANT-NAME             PIC  X(22).
    public NChar paMerchantName = new NChar(22);
    // COB:            05  PA-MERCHANT-CITY             PIC  X(13).
    public NChar paMerchantCity = new NChar(13);
    // COB:            05  PA-MERCHANT-STATE            PIC  X(02).
    public NChar paMerchantState = new NChar(2);
    // COB:            05  PA-MERCHANT-ZIP              PIC  X(09).
    public NChar paMerchantZip = new NChar(9);
    // COB:            05  PA-TRANSACTION-ID            PIC  X(15).
    public NChar paTransactionId = new NChar(15);
    // COB:            05  PA-MATCH-STATUS              PIC  X(01).
    public NChar paMatchStatus = new NChar(1);

    // COB:                88 PA-MATCH-PENDING          VALUE 'P'.
    public boolean paMatchPending() {
      return paMatchStatus.equals("P");
    }

    public void setPaMatchPending(boolean value) {
      if (value) paMatchStatus.setValue("P");
    }

    // COB:                88 PA-MATCH-AUTH-DECLINED    VALUE 'D'.
    public boolean paMatchAuthDeclined() {
      return paMatchStatus.equals("D");
    }

    public void setPaMatchAuthDeclined(boolean value) {
      if (value) paMatchStatus.setValue("D");
    }

    // COB:                88 PA-MATCH-PENDING-EXPIRED  VALUE 'E'.
    public boolean paMatchPendingExpired() {
      return paMatchStatus.equals("E");
    }

    public void setPaMatchPendingExpired(boolean value) {
      if (value) paMatchStatus.setValue("E");
    }

    // COB:                88 PA-MATCHED-WITH-TRAN      VALUE 'M'.
    public boolean paMatchedWithTran() {
      return paMatchStatus.equals("M");
    }

    public void setPaMatchedWithTran(boolean value) {
      if (value) paMatchStatus.setValue("M");
    }

    // COB:            05  PA-AUTH-FRAUD                PIC  X(01).
    public NChar paAuthFraud = new NChar(1);

    // COB:                88 PA-FRAUD-CONFIRMED        VALUE 'F'.
    public boolean paFraudConfirmed() {
      return paAuthFraud.equals("F");
    }

    public void setPaFraudConfirmed(boolean value) {
      if (value) paAuthFraud.setValue("F");
    }

    // COB:                88 PA-FRAUD-REMOVED          VALUE 'R'.
    public boolean paFraudRemoved() {
      return paAuthFraud.equals("R");
    }

    public void setPaFraudRemoved(boolean value) {
      if (value) paAuthFraud.setValue("R");
    }

    // COB:            05  PA-FRAUD-RPT-DATE            PIC  X(08).
    public NChar paFraudRptDate = new NChar(8);
    // COB:            05  FILLER                       PIC  X(17).
    public NChar filler54 = new NChar(17);
  }

  public NInt32 wsDeclRsnIdx = new NInt32().initial(1);
}
