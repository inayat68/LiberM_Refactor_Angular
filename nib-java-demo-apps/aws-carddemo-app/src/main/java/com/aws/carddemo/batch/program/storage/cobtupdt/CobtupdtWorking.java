package com.aws.carddemo.batch.program.storage.cobtupdt;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Dcltrtyp;
import com.nib.commons.storage.*;

public class CobtupdtWorking extends NGroup {
  public Dcltrtyp dcltrtyp = new Dcltrtyp();
  // COB:        01 FLAGS.
  public Flags flags = new Flags();

  public static class Flags extends NGroup {

    // COB:          05 LASTREC                                PIC X(1)
    // COB:                                                    VALUE SPACES.
    public NChar lastrec = new NChar(1).initial(NChar.Space);
  }

  // COB:        01 WORKING-VARIABLES.
  public WorkingVariables workingVariables = new WorkingVariables();

  public static class WorkingVariables extends NGroup {

    // COB:          05 WS-RETURN-MSG                          PIC X(80)
    // COB:                                                    VALUE SPACES.
    public NChar wsReturnMsg = new NChar(80).initial(NChar.Space);
  }

  // COB:        01 WS-MISC-VARS.
  public WsMiscVars wsMiscVars = new WsMiscVars();

  public static class WsMiscVars extends NGroup {

    // COB:          05 WS-VAR-SQLCODE                     PIC ----9.
    public NZoned wsVarSqlcode = new NZoned(5).formatAs("----0");
  }

  // COB:        01  WS-INF-STATUS.
  public WsInfStatus wsInfStatus = new WsInfStatus();

  public static class WsInfStatus extends NGroup {

    // COB:            05  WS-INF-STAT1       PIC X.
    public NChar wsInfStat1 = new NChar(1);
    // COB:            05  WS-INF-STAT2       PIC X.
    public NChar wsInfStat2 = new NChar(1);
  }

  // COB:        01 WS-INPUT-REC.
  public WsInputRec wsInputRec = new WsInputRec();

  public static class WsInputRec extends NGroup {

    // COB:           05 INPUT-REC-TYPE                        PIC X(1)
    // COB:                                                    VALUE SPACES.
    public NChar inputRecType = new NChar(1).initial(NChar.Space);
    // COB:           05 INPUT-REC-NUMBER                      PIC X(2)
    // COB:                                                    VALUE SPACES.
    public NChar inputRecNumber = new NChar(2).initial(NChar.Space);
    // COB:           05 INPUT-REC-DESC                        PIC X(50)
    // COB:                                                    VALUE SPACES.
    public NChar inputRecDesc = new NChar(50).initial(NChar.Space);
  }
}
