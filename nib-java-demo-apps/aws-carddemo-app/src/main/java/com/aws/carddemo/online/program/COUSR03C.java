package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cousr03c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COUSR03C extends AbstractOnlineProgram {

  @ProgramStorage final Cousr03cWorking ws = new Cousr03cWorking();

  // Linkage
  public static class Cousr03cLinkage extends NGroup {
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

  final Cousr03cLinkage params = new Cousr03cLinkage();

  public COUSR03C(Context supernaut) {
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
    // COB(84): SET ERR-FLG-OFF     TO TRUE
    ws.wsVariables.setErrFlgOff(true);
    // COB(85): SET USR-MODIFIED-NO TO TRUE
    ws.wsVariables.setUsrModifiedNo(true);
    // COB(87): MOVE SPACES TO WS-MESSAGE
    // COB(87):                ERRMSGO OF COUSR3AO
    ws.wsVariables.wsMessage.spaces();
    ws.cousr03.cousr3ao.errmsgo.spaces();
    // COB(90): IF EIBCALEN = 0
    if (dfheiblk.getEibcalen() == 0) {
      // COB(91): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(92): PERFORM RETURN-TO-PREV-SCREEN
      returnToPrevScreen();
      // COB(93): ELSE
    } else {
      // COB(94): MOVE DFHCOMMAREA(1:EIBCALEN) TO CARDDEMO-COMMAREA
      ws.carddemoCommarea.setValue(params.dfhcommarea, 0, dfheiblk.getEibcalen());
      // COB(95): IF NOT CDEMO-PGM-REENTER
      if (!ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
        // COB(96): SET CDEMO-PGM-REENTER    TO TRUE
        ws.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
        // COB(97): MOVE LOW-VALUES          TO COUSR3AO
        ws.cousr03.cousr3ao.lowValues();
        // COB(98): MOVE -1       TO USRIDINL OF COUSR3AI
        ws.cousr03.cousr3ai.usridinl.setValue(-1);
        // COB(99): IF CDEMO-CU03-USR-SELECTED NOT =
        // COB(99):                            SPACES AND LOW-VALUES
        if (!ws.carddemoCommarea.cdemoCu03Info.cdemoCu03UsrSelected.isSpaces()
            && !ws.carddemoCommarea.cdemoCu03Info.cdemoCu03UsrSelected.isLowValues()) {
          // COB(101): MOVE CDEMO-CU03-USR-SELECTED TO
          // COB(101):      USRIDINI OF COUSR3AI
          ws.cousr03.cousr3ai.usridini.setValue(
              ws.carddemoCommarea.cdemoCu03Info.cdemoCu03UsrSelected);
          // COB(103): PERFORM PROCESS-ENTER-KEY
          processEnterKey();
          // COB(104): END-IF
        }
        // COB(105): PERFORM SEND-USRDEL-SCREEN
        sendUsrdelScreen();
        // COB(106): ELSE
      } else {
        // COB(107): PERFORM RECEIVE-USRDEL-SCREEN
        receiveUsrdelScreen();
        // COB(108): EVALUATE EIBAID
        switch (dfheiblk.getEibaid()) {
            // COB(109): WHEN DFHENTER
          case DFHENTER:
            // COB(110): PERFORM PROCESS-ENTER-KEY
            processEnterKey();
            // COB(111): WHEN DFHPF3
            break;
          case DFHPF3:
            // COB(112): IF CDEMO-FROM-PROGRAM = SPACES OR LOW-VALUES
            if (ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isSpaces()
                || ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isLowValues()) {
              // COB(113): MOVE 'COADM01C' TO CDEMO-TO-PROGRAM
              ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COADM01C");
              // COB(114): ELSE
            } else {
              // COB(115): MOVE CDEMO-FROM-PROGRAM TO
              // COB(115): CDEMO-TO-PROGRAM
              ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
                  ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram);
              // COB(117): END-IF
            }
            // COB(118): PERFORM RETURN-TO-PREV-SCREEN
            returnToPrevScreen();
            // COB(119): WHEN DFHPF4
            break;
          case DFHPF4:
            // COB(120): PERFORM CLEAR-CURRENT-SCREEN
            clearCurrentScreen();
            // COB(121): WHEN DFHPF5
            break;
          case DFHPF5:
            // COB(122): PERFORM DELETE-USER-INFO
            deleteUserInfo();
            // COB(123): WHEN DFHPF12
            break;
          case DFHPF12:
            // COB(124): MOVE 'COADM01C' TO CDEMO-TO-PROGRAM
            ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COADM01C");
            // COB(125): PERFORM RETURN-TO-PREV-SCREEN
            returnToPrevScreen();
            // COB(126): WHEN OTHER
            break;
          default:
            // COB(127): MOVE 'Y'                       TO WS-ERR-FLG
            ws.wsVariables.wsErrFlg.setString("Y");
            // COB(128): MOVE CCDA-MSG-INVALID-KEY      TO WS-MESSAGE
            ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgInvalidKey);
            // COB(129): PERFORM SEND-USRDEL-SCREEN
            sendUsrdelScreen();
            // COB(130): END-EVALUATE
        }
        // COB(131): END-IF
      }
      // COB(132): END-IF
    }
    // COB(134): EXEC CICS RETURN
    // COB(134):           TRANSID (WS-TRANID)
    // COB(134):           COMMAREA (CARDDEMO-COMMAREA)
    // COB(134): END-EXEC.
    try {
      supernaut
          .returnTransid(ws.wsVariables.wsTranid) //
          .commarea(ws.carddemoCommarea) //
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
    // COB(144): EVALUATE TRUE
    // COB(145): WHEN USRIDINI OF COUSR3AI = SPACES OR LOW-VALUES
    if (ws.cousr03.cousr3ai.usridini.isSpaces() || ws.cousr03.cousr3ai.usridini.isLowValues()) {
      // COB(146): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(147): MOVE 'User ID can NOT be empty...' TO
      // COB(147):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("User ID can NOT be empty...");
      // COB(149): MOVE -1       TO USRIDINL OF COUSR3AI
      ws.cousr03.cousr3ai.usridinl.setValue(-1);
      // COB(150): PERFORM SEND-USRDEL-SCREEN
      sendUsrdelScreen();
      // COB(151): WHEN OTHER
    } else {
      // COB(152): MOVE -1       TO USRIDINL OF COUSR3AI
      ws.cousr03.cousr3ai.usridinl.setValue(-1);
      // COB(153): CONTINUE
      // do nothing
      // COB(154): END-EVALUATE
    }
    // COB(156): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(157): MOVE SPACES      TO FNAMEI   OF COUSR3AI
      // COB(157):                     LNAMEI   OF COUSR3AI
      // COB(157):                     USRTYPEI OF COUSR3AI
      ws.cousr03.cousr3ai.fnamei.spaces();
      ws.cousr03.cousr3ai.lnamei.spaces();
      ws.cousr03.cousr3ai.usrtypei.spaces();
      // COB(160): MOVE USRIDINI  OF COUSR3AI TO SEC-USR-ID
      ws.csusr01y.secUserData.secUsrId.setValue(ws.cousr03.cousr3ai.usridini);
      // COB(161): PERFORM READ-USER-SEC-FILE
      readUserSecFile();
      // COB(162): END-IF.
    }
    // COB(164): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(165): MOVE SEC-USR-FNAME      TO FNAMEI    OF COUSR3AI
      ws.cousr03.cousr3ai.fnamei.setValue(ws.csusr01y.secUserData.secUsrFname);
      // COB(166): MOVE SEC-USR-LNAME      TO LNAMEI    OF COUSR3AI
      ws.cousr03.cousr3ai.lnamei.setValue(ws.csusr01y.secUserData.secUsrLname);
      // COB(167): MOVE SEC-USR-TYPE       TO USRTYPEI  OF COUSR3AI
      ws.cousr03.cousr3ai.usrtypei.setValue(ws.csusr01y.secUserData.secUsrType);
      // COB(168): PERFORM SEND-USRDEL-SCREEN
      sendUsrdelScreen();
      // COB(169): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      DELETE-USER-INFO
   *----------------------------------------------------------------*/
  protected void deleteUserInfo() {
    // COB(176): EVALUATE TRUE
    // COB(177): WHEN USRIDINI OF COUSR3AI = SPACES OR LOW-VALUES
    if (ws.cousr03.cousr3ai.usridini.isSpaces() || ws.cousr03.cousr3ai.usridini.isLowValues()) {
      // COB(178): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(179): MOVE 'User ID can NOT be empty...' TO
      // COB(179):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("User ID can NOT be empty...");
      // COB(181): MOVE -1       TO USRIDINL OF COUSR3AI
      ws.cousr03.cousr3ai.usridinl.setValue(-1);
      // COB(182): PERFORM SEND-USRDEL-SCREEN
      sendUsrdelScreen();
      // COB(183): WHEN OTHER
    } else {
      // COB(184): MOVE -1       TO USRIDINL OF COUSR3AI
      ws.cousr03.cousr3ai.usridinl.setValue(-1);
      // COB(185): CONTINUE
      // do nothing
      // COB(186): END-EVALUATE
    }
    // COB(188): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(189): MOVE USRIDINI  OF COUSR3AI TO SEC-USR-ID
      ws.csusr01y.secUserData.secUsrId.setValue(ws.cousr03.cousr3ai.usridini);
      // COB(190): PERFORM READ-USER-SEC-FILE
      readUserSecFile();
      // COB(191): PERFORM DELETE-USER-SEC-FILE
      deleteUserSecFile();
      // COB(192): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RETURN-TO-PREV-SCREEN
   *----------------------------------------------------------------*/
  protected void returnToPrevScreen() {
    // COB(199): IF CDEMO-TO-PROGRAM = LOW-VALUES OR SPACES
    if (ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isLowValues()
        || ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isSpaces()) {
      // COB(200): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(201): END-IF
    }
    // COB(202): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsVariables.wsTranid);
    // COB(203): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsVariables.wsPgmname);
    // COB(204): MOVE ZEROS        TO CDEMO-PGM-CONTEXT
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.zeros();
    // COB(205): EXEC CICS
    // COB(205):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
    // COB(205):     COMMAREA(CARDDEMO-COMMAREA)
    // COB(205): END-EXEC.
    try {
      supernaut
          .xctl(ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
          .commarea(ws.carddemoCommarea) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      SEND-USRDEL-SCREEN
   *----------------------------------------------------------------*/
  protected void sendUsrdelScreen() {
    // COB(215): PERFORM POPULATE-HEADER-INFO
    populateHeaderInfo();
    // COB(217): MOVE WS-MESSAGE TO ERRMSGO OF COUSR3AO
    ws.cousr03.cousr3ao.errmsgo.setValue(ws.wsVariables.wsMessage);
    // COB(219): EXEC CICS SEND
    // COB(219):           MAP('COUSR3A')
    // COB(219):           MAPSET('COUSR03')
    // COB(219):           FROM(COUSR3AO)
    // COB(219):           ERASE
    // COB(219):           CURSOR
    // COB(219): END-EXEC.
    try {
      supernaut
          .sendMap("COUSR3A") //
          .mapset("COUSR03") //
          .from(ws.cousr03.cousr3ao) //
          .erase() //
          .cursor() //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RECEIVE-USRDEL-SCREEN
   *----------------------------------------------------------------*/
  protected void receiveUsrdelScreen() {
    // COB(232): EXEC CICS RECEIVE
    // COB(232):           MAP('COUSR3A')
    // COB(232):           MAPSET('COUSR03')
    // COB(232):           INTO(COUSR3AI)
    // COB(232):           RESP(WS-RESP-CD)
    // COB(232):           RESP2(WS-REAS-CD)
    // COB(232): END-EXEC.
    supernaut
        .receiveMap("COUSR3A") //
        .mapset("COUSR03") //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cousr03.cousr3ai) //
        .exec();
  }

  /***
   *----------------------------------------------------------------*
   *                      POPULATE-HEADER-INFO
   *----------------------------------------------------------------*/
  protected void populateHeaderInfo() {
    // COB(245): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(247): MOVE CCDA-TITLE01           TO TITLE01O OF COUSR3AO
    ws.cousr03.cousr3ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(248): MOVE CCDA-TITLE02           TO TITLE02O OF COUSR3AO
    ws.cousr03.cousr3ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(249): MOVE WS-TRANID              TO TRNNAMEO OF COUSR3AO
    ws.cousr03.cousr3ao.trnnameo.setValue(ws.wsVariables.wsTranid);
    // COB(250): MOVE WS-PGMNAME             TO PGMNAMEO OF COUSR3AO
    ws.cousr03.cousr3ao.pgmnameo.setValue(ws.wsVariables.wsPgmname);
    // COB(252): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(253): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(254): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(256): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF COUSR3AO
    ws.cousr03.cousr3ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(258): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(259): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(260): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(262): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF COUSR3AO.
    ws.cousr03.cousr3ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***
   *----------------------------------------------------------------*
   *                      READ-USER-SEC-FILE
   *----------------------------------------------------------------*/
  protected void readUserSecFile() {
    // COB(269): EXEC CICS READ
    // COB(269):      DATASET   (WS-USRSEC-FILE)
    // COB(269):      INTO      (SEC-USER-DATA)
    // COB(269):      LENGTH    (LENGTH OF SEC-USER-DATA)
    // COB(269):      RIDFLD    (SEC-USR-ID)
    // COB(269):      KEYLENGTH (LENGTH OF SEC-USR-ID)
    // COB(269):      UPDATE
    // COB(269):      RESP      (WS-RESP-CD)
    // COB(269):      RESP2     (WS-REAS-CD)
    // COB(269): END-EXEC.
    supernaut
        .read(ws.wsVariables.wsUsrsecFile.getValue()) //
        .update() //
        .length(ws.csusr01y.secUserData.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.csusr01y.secUserData) //
        .ridfld(ws.csusr01y.secUserData.secUsrId) //
        .keylength(ws.csusr01y.secUserData.secUsrId.length()) //
        .exec();
    // COB(280): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(281): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(282): CONTINUE
        // do nothing
        // COB(283): MOVE 'Press PF5 key to delete this user ...' TO
        // COB(283):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Press PF5 key to delete this user ...");
        // COB(285): MOVE DFHNEUTR       TO ERRMSGC  OF COUSR3AO
        ws.cousr03.cousr3ao.errmsgc.setValue(Dfhbmsca.DFHNEUTR.getValue());
        // COB(286): PERFORM SEND-USRDEL-SCREEN
        sendUsrdelScreen();
        // COB(287): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(288): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(289): MOVE 'User ID NOT found...' TO
        // COB(289):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("User ID NOT found...");
        // COB(291): MOVE -1       TO USRIDINL OF COUSR3AI
        ws.cousr03.cousr3ai.usridinl.setValue(-1);
        // COB(292): PERFORM SEND-USRDEL-SCREEN
        sendUsrdelScreen();
        // COB(293): WHEN OTHER
        break;
      default:
        // COB(294): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(295): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(296): MOVE 'Unable to lookup User...' TO
        // COB(296):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup User...");
        // COB(298): MOVE -1       TO FNAMEL OF COUSR3AI
        ws.cousr03.cousr3ai.fnamel.setValue(-1);
        // COB(299): PERFORM SEND-USRDEL-SCREEN
        sendUsrdelScreen();
        // COB(300): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      DELETE-USER-SEC-FILE
   *----------------------------------------------------------------*/
  protected void deleteUserSecFile() {
    // COB(307): EXEC CICS DELETE
    // COB(307):      DATASET   (WS-USRSEC-FILE)
    // COB(307):      RESP      (WS-RESP-CD)
    // COB(307):      RESP2     (WS-REAS-CD)
    // COB(307): END-EXEC.
    supernaut
        .delete(ws.wsVariables.wsUsrsecFile.getValue()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .exec();
    // COB(313): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(314): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(315): PERFORM INITIALIZE-ALL-FIELDS
        initializeAllFields();
        // COB(316): MOVE SPACES             TO WS-MESSAGE
        ws.wsVariables.wsMessage.spaces();
        // COB(317): MOVE DFHGREEN           TO ERRMSGC  OF COUSR3AO
        ws.cousr03.cousr3ao.errmsgc.setValue(Dfhbmsca.DFHGREEN.getValue());
        // COB(318): STRING 'User '     DELIMITED BY SIZE
        // COB(318):        SEC-USR-ID  DELIMITED BY SPACE
        // COB(318):        ' has been deleted ...' DELIMITED BY SIZE
        // COB(318):   INTO WS-MESSAGE
        ws.wsVariables.wsMessage.concat(
            "User ",
            ws.csusr01y.secUserData.secUsrId.substringToValue(" "),
            " has been deleted ...");
        // COB(322): PERFORM SEND-USRDEL-SCREEN
        sendUsrdelScreen();
        // COB(323): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(324): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(325): MOVE 'User ID NOT found...' TO
        // COB(325):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("User ID NOT found...");
        // COB(327): MOVE -1       TO USRIDINL OF COUSR3AI
        ws.cousr03.cousr3ai.usridinl.setValue(-1);
        // COB(328): PERFORM SEND-USRDEL-SCREEN
        sendUsrdelScreen();
        // COB(329): WHEN OTHER
        break;
      default:
        // COB(330): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(331): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(332): MOVE 'Unable to Update User...' TO
        // COB(332):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to Update User...");
        // COB(334): MOVE -1       TO FNAMEL OF COUSR3AI
        ws.cousr03.cousr3ai.fnamel.setValue(-1);
        // COB(335): PERFORM SEND-USRDEL-SCREEN
        sendUsrdelScreen();
        // COB(336): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      CLEAR-CURRENT-SCREEN
   *----------------------------------------------------------------*/
  protected void clearCurrentScreen() {
    // COB(343): PERFORM INITIALIZE-ALL-FIELDS.
    initializeAllFields();
    // COB(344): PERFORM SEND-USRDEL-SCREEN.
    sendUsrdelScreen();
  }

  /***
   *----------------------------------------------------------------*
   *                      INITIALIZE-ALL-FIELDS
   *----------------------------------------------------------------*/
  protected void initializeAllFields() {
    // COB(351): MOVE -1              TO USRIDINL OF COUSR3AI
    ws.cousr03.cousr3ai.usridinl.setValue(-1);
    // COB(352): MOVE SPACES          TO USRIDINI OF COUSR3AI
    // COB(352):                         FNAMEI   OF COUSR3AI
    // COB(352):                         LNAMEI   OF COUSR3AI
    // COB(352):                         USRTYPEI OF COUSR3AI
    // COB(352):                         WS-MESSAGE.
    ws.cousr03.cousr3ai.usridini.spaces();
    ws.cousr03.cousr3ai.fnamei.spaces();
    ws.cousr03.cousr3ai.lnamei.spaces();
    ws.cousr03.cousr3ai.usrtypei.spaces();
    ws.wsVariables.wsMessage.spaces();
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
