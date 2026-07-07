package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.coadm01c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COADM01C extends AbstractOnlineProgram {

  @ProgramStorage final Coadm01cWorking ws = new Coadm01cWorking();

  // Linkage
  public static class Coadm01cLinkage extends NGroup {
    // COB:        01  DFHCOMMAREA.
    public Dfhcommarea dfhcommarea = new Dfhcommarea();

    public static class Dfhcommarea extends NGroup {

      // COB:          05  LK-COMMAREA                           PIC X(01)
      // COB:              OCCURS 1 TO 32767 TIMES DEPENDING ON EIBCALEN.
      public NChar[] lkCommarea = AbstractNField.occurs(32767, new NChar(1));

      public NChar lkCommareaAtIndex(int index) {
        return getOccursInstance(lkCommarea, index);
      }
    }
  }

  final Coadm01cLinkage params = new Coadm01cLinkage();

  public COADM01C(Context supernaut) {
    super(supernaut);
  }

  @Override
  protected int procedure(AbstractNField dfhcommarea) throws ContextException {
    if (dfhcommarea != null) {
      params.dfhcommarea.setAddress(dfhcommarea.getAddress());
    } else {
      params.dfhcommarea.allocate();
    }
    mainPara();
    return 0;
  }

  protected void mainPara() {
    // COB(77): SET ERR-FLG-OFF TO TRUE
    ws.wsVariables.setErrFlgOff(true);
    // COB(79): MOVE SPACES TO WS-MESSAGE
    // COB(79):                ERRMSGO OF COADM1AO
    ws.wsVariables.wsMessage.spaces();
    ws.coadm01.coadm1ao.errmsgo.spaces();
    // COB(82): IF EIBCALEN = 0
    if (dfheiblk.getEibcalen() == 0) {
      // COB(83): MOVE 'COSGN00C' TO CDEMO-FROM-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setString("COSGN00C");
      // COB(84): PERFORM RETURN-TO-SIGNON-SCREEN
      returnToSignonScreen();
      // COB(85): ELSE
    } else {
      // COB(86): MOVE DFHCOMMAREA(1:EIBCALEN) TO CARDDEMO-COMMAREA
      ws.cocom01y.carddemoCommarea.setValue(params.dfhcommarea, 0, dfheiblk.getEibcalen());
      // COB(87): IF NOT CDEMO-PGM-REENTER
      if (!ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
        // COB(88): SET CDEMO-PGM-REENTER    TO TRUE
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
        // COB(89): MOVE LOW-VALUES          TO COADM1AO
        ws.coadm01.coadm1ao.lowValues();
        // COB(90): PERFORM SEND-MENU-SCREEN
        sendMenuScreen();
        // COB(91): ELSE
      } else {
        // COB(92): PERFORM RECEIVE-MENU-SCREEN
        receiveMenuScreen();
        // COB(93): EVALUATE EIBAID
        switch (dfheiblk.getEibaid()) {
            // COB(94): WHEN DFHENTER
          case DFHENTER:
            // COB(95): PERFORM PROCESS-ENTER-KEY
            processEnterKey();
            // COB(96): WHEN DFHPF3
            break;
          case DFHPF3:
            // COB(97): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
            // COB(98): PERFORM RETURN-TO-SIGNON-SCREEN
            returnToSignonScreen();
            // COB(99): WHEN OTHER
            break;
          default:
            // COB(100): MOVE 'Y'                       TO WS-ERR-FLG
            ws.wsVariables.wsErrFlg.setString("Y");
            // COB(101): MOVE CCDA-MSG-INVALID-KEY      TO WS-MESSAGE
            ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgInvalidKey);
            // COB(102): PERFORM SEND-MENU-SCREEN
            sendMenuScreen();
            // COB(103): END-EVALUATE
        }
        // COB(104): END-IF
      }
      // COB(105): END-IF
    }
    // COB(107): EXEC CICS RETURN
    // COB(107):           TRANSID (WS-TRANID)
    // COB(107):           COMMAREA (CARDDEMO-COMMAREA)
    // COB(107): END-EXEC.
    try {
      supernaut
          .returnTransid(ws.wsVariables.wsTranid) //
          .commarea(ws.cocom01y.carddemoCommarea) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      PROCESS-ENTER-KEY
   *----------------------------------------------------------------*/
  protected void processEnterKey() {
    // COB(117): PERFORM VARYING WS-IDX
    // COB(117):         FROM LENGTH OF OPTIONI OF COADM1AI BY -1 UNTIL
    // COB(117):         OPTIONI OF COADM1AI(WS-IDX:1) NOT = SPACES OR
    // COB(117):         WS-IDX = 1
    ws.wsVariables.wsIdx.setValue(ws.coadm01.coadm1ai.optioni.length());
    for (;
        !(!ws.coadm01.coadm1ai.optioni.getString(ws.wsVariables.wsIdx.getInt() - 1, 1).isSpaces()
            || ws.wsVariables.wsIdx.equals(1));
        ws.wsVariables.wsIdx.add(-1)) {
      // COB(121): END-PERFORM
    }
    // COB(122): MOVE OPTIONI OF COADM1AI(1:WS-IDX) TO WS-OPTION-X
    ws.wsVariables.wsOptionX.setValue(
        ws.coadm01.coadm1ai.optioni, 0, ws.wsVariables.wsIdx.getInt());
    // COB(123): INSPECT WS-OPTION-X REPLACING ALL ' ' BY '0'
    ws.wsVariables.wsOptionX.inspectAndReplaceAll(" ", "0");
    // COB(124): MOVE WS-OPTION-X              TO WS-OPTION
    ws.wsVariables.wsOption.setValue(ws.wsVariables.wsOptionX);
    // COB(125): MOVE WS-OPTION                TO OPTIONO OF COADM1AO
    ws.coadm01.coadm1ao.optiono.setValue(ws.wsVariables.wsOption);
    // COB(127): IF WS-OPTION IS NOT NUMERIC OR
    // COB(127):    WS-OPTION > CDEMO-ADMIN-OPT-COUNT OR
    // COB(127):    WS-OPTION = ZEROS
    if (!ws.wsVariables.wsOption.isNumeric()
        || ws.wsVariables.wsOption.greaterThan(
            ws.coadm02y.carddemoAdminMenuOptions.cdemoAdminOptCount)
        || ws.wsVariables.wsOption.equals(0)) {
      // COB(130): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(131): MOVE 'Please enter a valid option number...' TO
      // COB(131):                         WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Please enter a valid option number...");
      // COB(133): PERFORM SEND-MENU-SCREEN
      sendMenuScreen();
      // COB(134): END-IF
    }
    // COB(137): IF NOT ERR-FLG-ON
    //
    //
    if (!ws.wsVariables.errFlgOn()) {
      // COB(138): IF CDEMO-ADMIN-OPT-PGMNAME(WS-OPTION)(1:5) NOT = 'DUMMY'
      if (!ws.coadm02y
          .carddemoAdminMenuOptions
          .cdemoAdminOptions
          .cdemoAdminOptAtIndex(ws.wsVariables.wsOption.getInt() - 1)
          .cdemoAdminOptPgmname
          .getString(0, 5)
          .equals("DUMMY")) {
        // COB(139): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
            ws.wsVariables.wsTranid);
        // COB(140): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
            ws.wsVariables.wsPgmname);
        // COB(141): MOVE ZEROS        TO CDEMO-PGM-CONTEXT
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.zeros();
        // COB(142): EXEC CICS
        // COB(142):     XCTL PROGRAM(CDEMO-ADMIN-OPT-PGMNAME(WS-OPTION))
        // COB(142):     COMMAREA(CARDDEMO-COMMAREA)
        // COB(142): END-EXEC
        try {
          supernaut
              .xctl(
                  ws.coadm02y
                      .carddemoAdminMenuOptions
                      .cdemoAdminOptions
                      .cdemoAdminOptAtIndex(ws.wsVariables.wsOption.getInt() - 1)
                      .cdemoAdminOptPgmname
                      .getValue()) //
              .commarea(ws.cocom01y.carddemoCommarea) //
              .exec();
        } catch (HandledConditionException e) {
          handleCommandCondition(e);
        }
        // COB(146): END-IF
      }
      // COB(147): MOVE SPACES             TO WS-MESSAGE
      ws.wsVariables.wsMessage.spaces();
      // COB(148): MOVE DFHGREEN           TO ERRMSGC  OF COADM1AO
      ws.coadm01.coadm1ao.errmsgc.setValue(Dfhbmsca.DFHGREEN.getValue());
      // COB(149): STRING 'This option '       DELIMITED BY SIZE
      // COB(149):       *                CDEMO-ADMIN-OPT-NAME(WS-OPTION)
      // COB(149):       *                                DELIMITED BY SIZE
      // COB(149):         'is coming soon ...'   DELIMITED BY SIZE
      // COB(149):    INTO WS-MESSAGE
      ws.wsVariables.wsMessage.concat("This option ", "is coming soon ...");
      // COB(154): PERFORM SEND-MENU-SCREEN
      sendMenuScreen();
      // COB(155): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RETURN-TO-SIGNON-SCREEN
   *----------------------------------------------------------------*/
  protected void returnToSignonScreen() {
    // COB(162): IF CDEMO-TO-PROGRAM = LOW-VALUES OR SPACES
    if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isLowValues()
        || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isSpaces()) {
      // COB(163): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(164): END-IF
    }
    // COB(165): EXEC CICS
    // COB(165):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
    // COB(165): END-EXEC.
    try {
      supernaut
          .xctl(ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      SEND-MENU-SCREEN
   *----------------------------------------------------------------*/
  protected void sendMenuScreen() {
    // COB(174): PERFORM POPULATE-HEADER-INFO
    populateHeaderInfo();
    // COB(175): PERFORM BUILD-MENU-OPTIONS
    buildMenuOptions();
    // COB(177): MOVE WS-MESSAGE TO ERRMSGO OF COADM1AO
    ws.coadm01.coadm1ao.errmsgo.setValue(ws.wsVariables.wsMessage);
    // COB(179): EXEC CICS SEND
    // COB(179):           MAP('COADM1A')
    // COB(179):           MAPSET('COADM01')
    // COB(179):           FROM(COADM1AO)
    // COB(179):           ERASE
    // COB(179): END-EXEC.
    try {
      supernaut
          .sendMap("COADM1A") //
          .mapset("COADM01") //
          .from(ws.coadm01.coadm1ao) //
          .erase() //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RECEIVE-MENU-SCREEN
   *----------------------------------------------------------------*/
  protected void receiveMenuScreen() {
    // COB(191): EXEC CICS RECEIVE
    // COB(191):           MAP('COADM1A')
    // COB(191):           MAPSET('COADM01')
    // COB(191):           INTO(COADM1AI)
    // COB(191):           RESP(WS-RESP-CD)
    // COB(191):           RESP2(WS-REAS-CD)
    // COB(191): END-EXEC.
    supernaut
        .receiveMap("COADM1A") //
        .mapset("COADM01") //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.coadm01.coadm1ai) //
        .exec();
  }

  /***
   *----------------------------------------------------------------*
   *                      POPULATE-HEADER-INFO
   *----------------------------------------------------------------*/
  protected void populateHeaderInfo() {
    // COB(204): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(206): MOVE CCDA-TITLE01           TO TITLE01O OF COADM1AO
    ws.coadm01.coadm1ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(207): MOVE CCDA-TITLE02           TO TITLE02O OF COADM1AO
    ws.coadm01.coadm1ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(208): MOVE WS-TRANID              TO TRNNAMEO OF COADM1AO
    ws.coadm01.coadm1ao.trnnameo.setValue(ws.wsVariables.wsTranid);
    // COB(209): MOVE WS-PGMNAME             TO PGMNAMEO OF COADM1AO
    ws.coadm01.coadm1ao.pgmnameo.setValue(ws.wsVariables.wsPgmname);
    // COB(211): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(212): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(213): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(215): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF COADM1AO
    ws.coadm01.coadm1ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(217): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(218): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(219): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(221): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF COADM1AO.
    ws.coadm01.coadm1ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***
   *----------------------------------------------------------------*
   *                      BUILD-MENU-OPTIONS
   *----------------------------------------------------------------*/
  protected void buildMenuOptions() {
    // COB(228): PERFORM VARYING WS-IDX FROM 1 BY 1 UNTIL
    // COB(228):                 WS-IDX > CDEMO-ADMIN-OPT-COUNT
    ws.wsVariables.wsIdx.setValue(1);
    for (;
        !ws.wsVariables.wsIdx.greaterThan(ws.coadm02y.carddemoAdminMenuOptions.cdemoAdminOptCount);
        ws.wsVariables.wsIdx.add(1)) {
      // COB(231): MOVE SPACES             TO WS-ADMIN-OPT-TXT
      ws.wsVariables.wsAdminOptTxt.spaces();
      // COB(233): STRING CDEMO-ADMIN-OPT-NUM(WS-IDX)  DELIMITED BY SIZE
      // COB(233):        '. '                         DELIMITED BY SIZE
      // COB(233):        CDEMO-ADMIN-OPT-NAME(WS-IDX) DELIMITED BY SIZE
      // COB(233):   INTO WS-ADMIN-OPT-TXT
      ws.wsVariables.wsAdminOptTxt.concat(
          ws.coadm02y.carddemoAdminMenuOptions.cdemoAdminOptions.cdemoAdminOptAtIndex(
                  ws.wsVariables.wsIdx.getInt() - 1)
              .cdemoAdminOptNum,
          ". ",
          ws.coadm02y.carddemoAdminMenuOptions.cdemoAdminOptions.cdemoAdminOptAtIndex(
                  ws.wsVariables.wsIdx.getInt() - 1)
              .cdemoAdminOptName);
      // COB(238): EVALUATE WS-IDX
      switch (ws.wsVariables.wsIdx.getInt()) {
          // COB(239): WHEN 1
        case 1:
          // COB(240): MOVE WS-ADMIN-OPT-TXT TO OPTN001O
          ws.coadm01.coadm1ao.optn001o.setValue(ws.wsVariables.wsAdminOptTxt);
          // COB(241): WHEN 2
          break;
        case 2:
          // COB(242): MOVE WS-ADMIN-OPT-TXT TO OPTN002O
          ws.coadm01.coadm1ao.optn002o.setValue(ws.wsVariables.wsAdminOptTxt);
          // COB(243): WHEN 3
          break;
        case 3:
          // COB(244): MOVE WS-ADMIN-OPT-TXT TO OPTN003O
          ws.coadm01.coadm1ao.optn003o.setValue(ws.wsVariables.wsAdminOptTxt);
          // COB(245): WHEN 4
          break;
        case 4:
          // COB(246): MOVE WS-ADMIN-OPT-TXT TO OPTN004O
          ws.coadm01.coadm1ao.optn004o.setValue(ws.wsVariables.wsAdminOptTxt);
          // COB(247): WHEN 5
          break;
        case 5:
          // COB(248): MOVE WS-ADMIN-OPT-TXT TO OPTN005O
          ws.coadm01.coadm1ao.optn005o.setValue(ws.wsVariables.wsAdminOptTxt);
          // COB(249): WHEN 6
          break;
        case 6:
          // COB(250): MOVE WS-ADMIN-OPT-TXT TO OPTN006O
          ws.coadm01.coadm1ao.optn006o.setValue(ws.wsVariables.wsAdminOptTxt);
          // COB(251): WHEN 7
          break;
        case 7:
          // COB(252): MOVE WS-ADMIN-OPT-TXT TO OPTN007O
          ws.coadm01.coadm1ao.optn007o.setValue(ws.wsVariables.wsAdminOptTxt);
          // COB(253): WHEN 8
          break;
        case 8:
          // COB(254): MOVE WS-ADMIN-OPT-TXT TO OPTN008O
          ws.coadm01.coadm1ao.optn008o.setValue(ws.wsVariables.wsAdminOptTxt);
          // COB(255): WHEN 9
          break;
        case 9:
          // COB(256): MOVE WS-ADMIN-OPT-TXT TO OPTN009O
          ws.coadm01.coadm1ao.optn009o.setValue(ws.wsVariables.wsAdminOptTxt);
          // COB(257): WHEN 10
          break;
        case 10:
          // COB(258): MOVE WS-ADMIN-OPT-TXT TO OPTN010O
          ws.coadm01.coadm1ao.optn010o.setValue(ws.wsVariables.wsAdminOptTxt);
          // COB(259): WHEN OTHER
          break;
        default:
          // COB(260): CONTINUE
          // do nothing
          // COB(261): END-EVALUATE
      }
      // COB(263): END-PERFORM.
    }
  }

  protected enum HandleLabel {
    Not_Defined(0);

    private final Integer value;

    HandleLabel(Integer value) {
      this.value = value;
    }

    public int getValue() {
      return this.value;
    }

    public static HandleLabel get(Integer handleId) {
      for (HandleLabel hndLbl : HandleLabel.values()) {
        if (hndLbl.getValue() == handleId) {
          return hndLbl;
        }
      }
      return Not_Defined;
    }
  }

  protected void handleCommandCondition(HandledConditionException e) {
    throw new RuntimeException(e);
  }
}
