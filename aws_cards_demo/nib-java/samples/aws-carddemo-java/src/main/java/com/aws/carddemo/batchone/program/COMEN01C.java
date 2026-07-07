package com.aws.carddemo.batchone.program;

import com.aws.carddemo.batchone.program.storage.comen01c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COMEN01C extends AbstractOnlineProgram {

  @ProgramStorage final Comen01cWorking ws = new Comen01cWorking();

  // Linkage
  public static class Comen01cLinkage extends NGroup {
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

  final Comen01cLinkage params = new Comen01cLinkage();

  public COMEN01C(Context context) {
    super(context);
  }

  @ProgramInit
  protected void initialize() {}

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
    // COB(79):                ERRMSGO OF COMEN1AO
    ws.wsVariables.wsMessage.spaces();
    ws.comen01.comen1ao.errmsgo.spaces();
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
        // COB(89): MOVE LOW-VALUES          TO COMEN1AO
        ws.comen01.comen1ao.lowValues();
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
      context
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
    // COB(117):         FROM LENGTH OF OPTIONI OF COMEN1AI BY -1 UNTIL
    // COB(117):         OPTIONI OF COMEN1AI(WS-IDX:1) NOT = SPACES OR
    // COB(117):         WS-IDX = 1
    ws.wsVariables.wsIdx.setValue(ws.comen01.comen1ai.optioni.length());
    for (;
        !(!ws.comen01.comen1ai.optioni.getString(ws.wsVariables.wsIdx.getInt() - 1, 1).isSpaces()
            || ws.wsVariables.wsIdx.equals(1));
        ws.wsVariables.wsIdx.add(-1)) {
      // COB(121): END-PERFORM
    }
    // COB(122): MOVE OPTIONI OF COMEN1AI(1:WS-IDX) TO WS-OPTION-X
    ws.wsVariables.wsOptionX.setValue(
        ws.comen01.comen1ai.optioni, 0, ws.wsVariables.wsIdx.getInt());
    // COB(123): INSPECT WS-OPTION-X REPLACING ALL ' ' BY '0'
    ws.wsVariables.wsOptionX.inspectAndReplaceAll(" ", "0");
    // COB(124): MOVE WS-OPTION-X              TO WS-OPTION
    ws.wsVariables.wsOption.setValue(ws.wsVariables.wsOptionX);
    // COB(125): MOVE WS-OPTION                TO OPTIONO OF COMEN1AO
    ws.comen01.comen1ao.optiono.setValue(ws.wsVariables.wsOption);
    // COB(127): IF WS-OPTION IS NOT NUMERIC OR
    // COB(127):    WS-OPTION > CDEMO-MENU-OPT-COUNT OR
    // COB(127):    WS-OPTION = ZEROS
    if (!ws.wsVariables.wsOption.isNumeric()
        || ws.wsVariables.wsOption.greaterThan(
            ws.comen02y.carddemoMainMenuOptions.cdemoMenuOptCount)
        || ws.wsVariables.wsOption.equals(0)) {
      // COB(130): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(131): MOVE 'Please enter a valid option number...' TO
      // COB(131):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Please enter a valid option number...");
      // COB(133): PERFORM SEND-MENU-SCREEN
      sendMenuScreen();
      // COB(135): SANTIX     ELSE
      //       *    END-IF
    } else {
      // COB(137): IF CDEMO-USRTYP-USER AND
      // COB(137):    CDEMO-MENU-OPT-USRTYPE(WS-OPTION) = 'A'
      if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoUsrtypUser()
          && ws.comen02y
              .carddemoMainMenuOptions
              .cdemoMenuOptions
              .cdemoMenuOptAtIndex(ws.wsVariables.wsOption.getInt() - 1)
              .cdemoMenuOptUsrtype
              .equals("A")) {
        // COB(139): SET ERR-FLG-ON          TO TRUE
        ws.wsVariables.setErrFlgOn(true);
        // COB(140): MOVE SPACES             TO WS-MESSAGE
        ws.wsVariables.wsMessage.spaces();
        // COB(141): MOVE 'No access - Admin Only option... ' TO
        // COB(141):                         WS-MESSAGE
        ws.wsVariables.wsMessage.setString("No access - Admin Only option... ");
        // COB(143): PERFORM SEND-MENU-SCREEN
        sendMenuScreen();
        // COB(144): END-IF
      }
      // COB(146): IF NOT ERR-FLG-ON
      if (!ws.wsVariables.errFlgOn()) {
        // COB(147): IF CDEMO-MENU-OPT-PGMNAME(WS-OPTION)(1:5) NOT = 'DUMMY'
        if (!ws.comen02y
            .carddemoMainMenuOptions
            .cdemoMenuOptions
            .cdemoMenuOptAtIndex(ws.wsVariables.wsOption.getInt() - 1)
            .cdemoMenuOptPgmname
            .getString(0, 5)
            .equals("DUMMY")) {
          // COB(148): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
          ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
              ws.wsVariables.wsTranid);
          // COB(149): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
          ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
              ws.wsVariables.wsPgmname);
          // COB(152): MOVE ZEROS        TO CDEMO-PGM-CONTEXT
          //       *            MOVE WS-USER-ID   TO CDEMO-USER-ID
          //       *            MOVE SEC-USR-TYPE TO CDEMO-USER-TYPE
          ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.zeros();
          // COB(153): EXEC CICS
          // COB(153):     XCTL PROGRAM(CDEMO-MENU-OPT-PGMNAME(WS-OPTION))
          // COB(153):     COMMAREA(CARDDEMO-COMMAREA)
          // COB(153): END-EXEC
          try {
            context
                .xctl(
                    ws.comen02y
                        .carddemoMainMenuOptions
                        .cdemoMenuOptions
                        .cdemoMenuOptAtIndex(ws.wsVariables.wsOption.getInt() - 1)
                        .cdemoMenuOptPgmname
                        .getValue()) //
                .commarea(ws.cocom01y.carddemoCommarea) //
                .exec();
          } catch (HandledConditionException e) {
            handleCommandCondition(e);
          }
          // COB(157): END-IF
        }
        // COB(158): MOVE SPACES             TO WS-MESSAGE
        ws.wsVariables.wsMessage.spaces();
        // COB(159): MOVE DFHGREEN           TO ERRMSGC  OF COMEN1AO
        ws.comen01.comen1ao.errmsgc.setValue(Dfhbmsca.DFHGREEN.getValue());
        // COB(160): STRING 'This option '       DELIMITED BY SIZE
        // COB(160):         CDEMO-MENU-OPT-NAME(WS-OPTION)
        // COB(160):                         DELIMITED BY SPACE
        // COB(160):         'is coming soon ...'   DELIMITED BY SIZE
        // COB(160):    INTO WS-MESSAGE
        ws.wsVariables.wsMessage.concat(
            "This option ",
            ws.comen02y
                .carddemoMainMenuOptions
                .cdemoMenuOptions
                .cdemoMenuOptAtIndex(ws.wsVariables.wsOption.getInt() - 1)
                .cdemoMenuOptName
                .substringToValue(" "),
            "is coming soon ...");
        // COB(165): PERFORM SEND-MENU-SCREEN
        sendMenuScreen();
        // COB(166): END-IF.
      }
      // COB(166): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RETURN-TO-SIGNON-SCREEN
   *----------------------------------------------------------------*/
  protected void returnToSignonScreen() {
    // COB(173): IF CDEMO-TO-PROGRAM = LOW-VALUES OR SPACES
    if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isLowValues()
        || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isSpaces()) {
      // COB(174): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(175): END-IF
    }
    // COB(176): EXEC CICS
    // COB(176):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
    // COB(176): END-EXEC.
    try {
      context
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
    // COB(185): PERFORM POPULATE-HEADER-INFO
    populateHeaderInfo();
    // COB(186): PERFORM BUILD-MENU-OPTIONS
    buildMenuOptions();
    // COB(188): MOVE WS-MESSAGE TO ERRMSGO OF COMEN1AO
    ws.comen01.comen1ao.errmsgo.setValue(ws.wsVariables.wsMessage);
    // COB(190): EXEC CICS SEND
    // COB(190):           MAP('COMEN1A')
    // COB(190):           MAPSET('COMEN01')
    // COB(190):           FROM(COMEN1AO)
    // COB(190):           ERASE
    // COB(190): END-EXEC.
    try {
      context
          .sendMap("COMEN1A") //
          .mapset("COMEN01") //
          .from(ws.comen01.comen1ao) //
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
    // COB(202): EXEC CICS RECEIVE
    // COB(202):           MAP('COMEN1A')
    // COB(202):           MAPSET('COMEN01')
    // COB(202):           INTO(COMEN1AI)
    // COB(202):           RESP(WS-RESP-CD)
    // COB(202):           RESP2(WS-REAS-CD)
    // COB(202): END-EXEC.
    context
        .receiveMap("COMEN1A") //
        .mapset("COMEN01") //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.comen01.comen1ai) //
        .exec();
  }

  /***
   *----------------------------------------------------------------*
   *                      POPULATE-HEADER-INFO
   *----------------------------------------------------------------*/
  protected void populateHeaderInfo() {
    // COB(215): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(217): MOVE CCDA-TITLE01           TO TITLE01O OF COMEN1AO
    ws.comen01.comen1ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(218): MOVE CCDA-TITLE02           TO TITLE02O OF COMEN1AO
    ws.comen01.comen1ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(219): MOVE WS-TRANID              TO TRNNAMEO OF COMEN1AO
    ws.comen01.comen1ao.trnnameo.setValue(ws.wsVariables.wsTranid);
    // COB(220): MOVE WS-PGMNAME             TO PGMNAMEO OF COMEN1AO
    ws.comen01.comen1ao.pgmnameo.setValue(ws.wsVariables.wsPgmname);
    // COB(222): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(223): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(224): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(226): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF COMEN1AO
    ws.comen01.comen1ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(228): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(229): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(230): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(232): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF COMEN1AO.
    ws.comen01.comen1ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***
   *----------------------------------------------------------------*
   *                      BUILD-MENU-OPTIONS
   *----------------------------------------------------------------*/
  protected void buildMenuOptions() {
    // COB(239): PERFORM VARYING WS-IDX FROM 1 BY 1 UNTIL
    // COB(239):                 WS-IDX > CDEMO-MENU-OPT-COUNT
    ws.wsVariables.wsIdx.setValue(1);
    for (;
        !ws.wsVariables.wsIdx.greaterThan(ws.comen02y.carddemoMainMenuOptions.cdemoMenuOptCount);
        ws.wsVariables.wsIdx.add(1)) {
      // COB(242): MOVE SPACES             TO WS-MENU-OPT-TXT
      ws.wsVariables.wsMenuOptTxt.spaces();
      // COB(243): IF CDEMO-MENU-OPT-NUM(WS-IDX) NUMERIC
      if (ws.comen02y
          .carddemoMainMenuOptions
          .cdemoMenuOptions
          .cdemoMenuOptAtIndex(ws.wsVariables.wsIdx.getInt() - 1)
          .cdemoMenuOptNum
          .isNumeric()) {
        // COB(244): DISPLAY 'IS NUMERIC!'
        sysout.display("IS NUMERIC!");
        // COB(245): END-IF
      }
      // COB(246): STRING CDEMO-MENU-OPT-NUM(WS-IDX)  DELIMITED BY SIZE
      // COB(246):        '. '                         DELIMITED BY SIZE
      // COB(246):        CDEMO-MENU-OPT-NAME(WS-IDX) DELIMITED BY SIZE
      // COB(246):   INTO WS-MENU-OPT-TXT
      ws.wsVariables.wsMenuOptTxt.concat(
          ws.comen02y.carddemoMainMenuOptions.cdemoMenuOptions.cdemoMenuOptAtIndex(
                  ws.wsVariables.wsIdx.getInt() - 1)
              .cdemoMenuOptNum,
          ". ",
          ws.comen02y.carddemoMainMenuOptions.cdemoMenuOptions.cdemoMenuOptAtIndex(
                  ws.wsVariables.wsIdx.getInt() - 1)
              .cdemoMenuOptName);
      // COB(251): EVALUATE WS-IDX
      switch (ws.wsVariables.wsIdx.getInt()) {
          // COB(252): WHEN 1
        case 1:
          // COB(253): MOVE WS-MENU-OPT-TXT TO OPTN001O
          ws.comen01.comen1ao.optn001o.setValue(ws.wsVariables.wsMenuOptTxt);
          // COB(254): WHEN 2
          break;
        case 2:
          // COB(255): MOVE WS-MENU-OPT-TXT TO OPTN002O
          ws.comen01.comen1ao.optn002o.setValue(ws.wsVariables.wsMenuOptTxt);
          // COB(256): WHEN 3
          break;
        case 3:
          // COB(257): MOVE WS-MENU-OPT-TXT TO OPTN003O
          ws.comen01.comen1ao.optn003o.setValue(ws.wsVariables.wsMenuOptTxt);
          // COB(258): WHEN 4
          break;
        case 4:
          // COB(259): MOVE WS-MENU-OPT-TXT TO OPTN004O
          ws.comen01.comen1ao.optn004o.setValue(ws.wsVariables.wsMenuOptTxt);
          // COB(260): WHEN 5
          break;
        case 5:
          // COB(261): MOVE WS-MENU-OPT-TXT TO OPTN005O
          ws.comen01.comen1ao.optn005o.setValue(ws.wsVariables.wsMenuOptTxt);
          // COB(262): WHEN 6
          break;
        case 6:
          // COB(263): MOVE WS-MENU-OPT-TXT TO OPTN006O
          ws.comen01.comen1ao.optn006o.setValue(ws.wsVariables.wsMenuOptTxt);
          // COB(264): WHEN 7
          break;
        case 7:
          // COB(265): MOVE WS-MENU-OPT-TXT TO OPTN007O
          ws.comen01.comen1ao.optn007o.setValue(ws.wsVariables.wsMenuOptTxt);
          // COB(266): WHEN 8
          break;
        case 8:
          // COB(267): MOVE WS-MENU-OPT-TXT TO OPTN008O
          ws.comen01.comen1ao.optn008o.setValue(ws.wsVariables.wsMenuOptTxt);
          // COB(268): WHEN 9
          break;
        case 9:
          // COB(269): MOVE WS-MENU-OPT-TXT TO OPTN009O
          ws.comen01.comen1ao.optn009o.setValue(ws.wsVariables.wsMenuOptTxt);
          // COB(270): WHEN 10
          break;
        case 10:
          // COB(271): MOVE WS-MENU-OPT-TXT TO OPTN010O
          ws.comen01.comen1ao.optn010o.setValue(ws.wsVariables.wsMenuOptTxt);
          // COB(272): WHEN 11
          break;
        case 11:
          // COB(273): MOVE WS-MENU-OPT-TXT TO OPTN011O
          ws.comen01.comen1ao.optn011o.setValue(ws.wsVariables.wsMenuOptTxt);
          // COB(274): WHEN 12
          break;
        case 12:
          // COB(275): MOVE WS-MENU-OPT-TXT TO OPTN012O
          ws.comen01.comen1ao.optn012o.setValue(ws.wsVariables.wsMenuOptTxt);
          // COB(276): WHEN OTHER
          break;
        default:
          // COB(277): CONTINUE
          // do nothing
          // COB(278): END-EVALUATE
      }
      // COB(280): END-PERFORM.
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
