package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cousr02c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COUSR02C extends AbstractOnlineProgram {

  @ProgramStorage final Cousr02cWorking ws = new Cousr02cWorking();

  // Linkage
  public static class Cousr02cLinkage extends NGroup {
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

  final Cousr02cLinkage params = new Cousr02cLinkage();

  public COUSR02C(Context supernaut) {
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
    // COB(87):                ERRMSGO OF COUSR2AO
    ws.wsVariables.wsMessage.spaces();
    ws.cousr02.cousr2ao.errmsgo.spaces();
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
        // COB(97): MOVE LOW-VALUES          TO COUSR2AO
        ws.cousr02.cousr2ao.lowValues();
        // COB(98): MOVE -1       TO USRIDINL OF COUSR2AI
        ws.cousr02.cousr2ai.usridinl.setValue(-1);
        // COB(99): IF CDEMO-CU02-USR-SELECTED NOT =
        // COB(99):                            SPACES AND LOW-VALUES
        if (!ws.carddemoCommarea.cdemoCu02Info.cdemoCu02UsrSelected.isSpaces()
            && !ws.carddemoCommarea.cdemoCu02Info.cdemoCu02UsrSelected.isLowValues()) {
          // COB(101): MOVE CDEMO-CU02-USR-SELECTED TO
          // COB(101):      USRIDINI OF COUSR2AI
          ws.cousr02.cousr2ai.usridini.setValue(
              ws.carddemoCommarea.cdemoCu02Info.cdemoCu02UsrSelected);
          // COB(103): PERFORM PROCESS-ENTER-KEY
          processEnterKey();
          // COB(104): END-IF
        }
        // COB(105): PERFORM SEND-USRUPD-SCREEN
        sendUsrupdScreen();
        // COB(106): ELSE
      } else {
        // COB(107): PERFORM RECEIVE-USRUPD-SCREEN
        receiveUsrupdScreen();
        // COB(108): EVALUATE EIBAID
        switch (dfheiblk.getEibaid()) {
            // COB(109): WHEN DFHENTER
          case DFHENTER:
            // COB(110): PERFORM PROCESS-ENTER-KEY
            processEnterKey();
            // COB(111): WHEN DFHPF3
            break;
          case DFHPF3:
            // COB(112): PERFORM UPDATE-USER-INFO
            updateUserInfo();
            // COB(113): IF CDEMO-FROM-PROGRAM = SPACES OR LOW-VALUES
            if (ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isSpaces()
                || ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.isLowValues()) {
              // COB(114): MOVE 'COADM01C' TO CDEMO-TO-PROGRAM
              ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COADM01C");
              // COB(115): ELSE
            } else {
              // COB(116): MOVE CDEMO-FROM-PROGRAM TO
              // COB(116): CDEMO-TO-PROGRAM
              ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setValue(
                  ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram);
              // COB(118): END-IF
            }
            // COB(119): PERFORM RETURN-TO-PREV-SCREEN
            returnToPrevScreen();
            // COB(120): WHEN DFHPF4
            break;
          case DFHPF4:
            // COB(121): PERFORM CLEAR-CURRENT-SCREEN
            clearCurrentScreen();
            // COB(122): WHEN DFHPF5
            break;
          case DFHPF5:
            // COB(123): PERFORM UPDATE-USER-INFO
            updateUserInfo();
            // COB(124): WHEN DFHPF12
            break;
          case DFHPF12:
            // COB(125): MOVE 'COADM01C' TO CDEMO-TO-PROGRAM
            ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COADM01C");
            // COB(126): PERFORM RETURN-TO-PREV-SCREEN
            returnToPrevScreen();
            // COB(127): WHEN OTHER
            break;
          default:
            // COB(128): MOVE 'Y'                       TO WS-ERR-FLG
            ws.wsVariables.wsErrFlg.setString("Y");
            // COB(129): MOVE CCDA-MSG-INVALID-KEY      TO WS-MESSAGE
            ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgInvalidKey);
            // COB(130): PERFORM SEND-USRUPD-SCREEN
            sendUsrupdScreen();
            // COB(131): END-EVALUATE
        }
        // COB(132): END-IF
      }
      // COB(133): END-IF
    }
    // COB(135): EXEC CICS RETURN
    // COB(135):           TRANSID (WS-TRANID)
    // COB(135):           COMMAREA (CARDDEMO-COMMAREA)
    // COB(135): END-EXEC.
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
    // COB(145): EVALUATE TRUE
    // COB(146): WHEN USRIDINI OF COUSR2AI = SPACES OR LOW-VALUES
    if (ws.cousr02.cousr2ai.usridini.isSpaces() || ws.cousr02.cousr2ai.usridini.isLowValues()) {
      // COB(147): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(148): MOVE 'User ID can NOT be empty...' TO
      // COB(148):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("User ID can NOT be empty...");
      // COB(150): MOVE -1       TO USRIDINL OF COUSR2AI
      ws.cousr02.cousr2ai.usridinl.setValue(-1);
      // COB(151): PERFORM SEND-USRUPD-SCREEN
      sendUsrupdScreen();
      // COB(152): WHEN OTHER
    } else {
      // COB(153): MOVE -1       TO USRIDINL OF COUSR2AI
      ws.cousr02.cousr2ai.usridinl.setValue(-1);
      // COB(154): CONTINUE
      // do nothing
      // COB(155): END-EVALUATE
    }
    // COB(157): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(158): MOVE SPACES      TO FNAMEI   OF COUSR2AI
      // COB(158):                     LNAMEI   OF COUSR2AI
      // COB(158):                     PASSWDI  OF COUSR2AI
      // COB(158):                     USRTYPEI OF COUSR2AI
      ws.cousr02.cousr2ai.fnamei.spaces();
      ws.cousr02.cousr2ai.lnamei.spaces();
      ws.cousr02.cousr2ai.passwdi.spaces();
      ws.cousr02.cousr2ai.usrtypei.spaces();
      // COB(162): MOVE USRIDINI  OF COUSR2AI TO SEC-USR-ID
      ws.csusr01y.secUserData.secUsrId.setValue(ws.cousr02.cousr2ai.usridini);
      // COB(163): PERFORM READ-USER-SEC-FILE
      readUserSecFile();
      // COB(164): END-IF.
    }
    // COB(166): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(167): MOVE SEC-USR-FNAME      TO FNAMEI    OF COUSR2AI
      ws.cousr02.cousr2ai.fnamei.setValue(ws.csusr01y.secUserData.secUsrFname);
      // COB(168): MOVE SEC-USR-LNAME      TO LNAMEI    OF COUSR2AI
      ws.cousr02.cousr2ai.lnamei.setValue(ws.csusr01y.secUserData.secUsrLname);
      // COB(169): MOVE SEC-USR-PWD        TO PASSWDI   OF COUSR2AI
      ws.cousr02.cousr2ai.passwdi.setValue(ws.csusr01y.secUserData.secUsrPwd);
      // COB(170): MOVE SEC-USR-TYPE       TO USRTYPEI  OF COUSR2AI
      ws.cousr02.cousr2ai.usrtypei.setValue(ws.csusr01y.secUserData.secUsrType);
      // COB(171): PERFORM SEND-USRUPD-SCREEN
      sendUsrupdScreen();
      // COB(172): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      UPDATE-USER-INFO
   *----------------------------------------------------------------*/
  protected void updateUserInfo() {
    // COB(179): EVALUATE TRUE
    // COB(180): WHEN USRIDINI OF COUSR2AI = SPACES OR LOW-VALUES
    if (ws.cousr02.cousr2ai.usridini.isSpaces() || ws.cousr02.cousr2ai.usridini.isLowValues()) {
      // COB(181): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(182): MOVE 'User ID can NOT be empty...' TO
      // COB(182):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("User ID can NOT be empty...");
      // COB(184): MOVE -1       TO USRIDINL OF COUSR2AI
      ws.cousr02.cousr2ai.usridinl.setValue(-1);
      // COB(185): PERFORM SEND-USRUPD-SCREEN
      sendUsrupdScreen();
      // COB(186): WHEN FNAMEI OF COUSR2AI = SPACES OR LOW-VALUES
    } else if (ws.cousr02.cousr2ai.fnamei.isSpaces() || ws.cousr02.cousr2ai.fnamei.isLowValues()) {
      // COB(187): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(188): MOVE 'First Name can NOT be empty...' TO
      // COB(188):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("First Name can NOT be empty...");
      // COB(190): MOVE -1       TO FNAMEL OF COUSR2AI
      ws.cousr02.cousr2ai.fnamel.setValue(-1);
      // COB(191): PERFORM SEND-USRUPD-SCREEN
      sendUsrupdScreen();
      // COB(192): WHEN LNAMEI OF COUSR2AI = SPACES OR LOW-VALUES
    } else if (ws.cousr02.cousr2ai.lnamei.isSpaces() || ws.cousr02.cousr2ai.lnamei.isLowValues()) {
      // COB(193): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(194): MOVE 'Last Name can NOT be empty...' TO
      // COB(194):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Last Name can NOT be empty...");
      // COB(196): MOVE -1       TO LNAMEL OF COUSR2AI
      ws.cousr02.cousr2ai.lnamel.setValue(-1);
      // COB(197): PERFORM SEND-USRUPD-SCREEN
      sendUsrupdScreen();
      // COB(198): WHEN PASSWDI OF COUSR2AI = SPACES OR LOW-VALUES
    } else if (ws.cousr02.cousr2ai.passwdi.isSpaces()
        || ws.cousr02.cousr2ai.passwdi.isLowValues()) {
      // COB(199): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(200): MOVE 'Password can NOT be empty...' TO
      // COB(200):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Password can NOT be empty...");
      // COB(202): MOVE -1       TO PASSWDL OF COUSR2AI
      ws.cousr02.cousr2ai.passwdl.setValue(-1);
      // COB(203): PERFORM SEND-USRUPD-SCREEN
      sendUsrupdScreen();
      // COB(204): WHEN USRTYPEI OF COUSR2AI = SPACES OR LOW-VALUES
    } else if (ws.cousr02.cousr2ai.usrtypei.isSpaces()
        || ws.cousr02.cousr2ai.usrtypei.isLowValues()) {
      // COB(205): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(206): MOVE 'User Type can NOT be empty...' TO
      // COB(206):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("User Type can NOT be empty...");
      // COB(208): MOVE -1       TO USRTYPEL OF COUSR2AI
      ws.cousr02.cousr2ai.usrtypel.setValue(-1);
      // COB(209): PERFORM SEND-USRUPD-SCREEN
      sendUsrupdScreen();
      // COB(210): WHEN OTHER
    } else {
      // COB(211): MOVE -1       TO FNAMEL OF COUSR2AI
      ws.cousr02.cousr2ai.fnamel.setValue(-1);
      // COB(212): CONTINUE
      // do nothing
      // COB(213): END-EVALUATE
    }
    // COB(215): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(216): MOVE USRIDINI  OF COUSR2AI TO SEC-USR-ID
      ws.csusr01y.secUserData.secUsrId.setValue(ws.cousr02.cousr2ai.usridini);
      // COB(217): PERFORM READ-USER-SEC-FILE
      readUserSecFile();
      // COB(219): IF FNAMEI  OF COUSR2AI NOT = SEC-USR-FNAME
      if (!ws.cousr02.cousr2ai.fnamei.equals(ws.csusr01y.secUserData.secUsrFname)) {
        // COB(220): MOVE FNAMEI   OF COUSR2AI TO SEC-USR-FNAME
        ws.csusr01y.secUserData.secUsrFname.setValue(ws.cousr02.cousr2ai.fnamei);
        // COB(221): SET USR-MODIFIED-YES TO TRUE
        ws.wsVariables.setUsrModifiedYes(true);
        // COB(222): END-IF
      }
      // COB(223): IF LNAMEI  OF COUSR2AI NOT = SEC-USR-LNAME
      if (!ws.cousr02.cousr2ai.lnamei.equals(ws.csusr01y.secUserData.secUsrLname)) {
        // COB(224): MOVE LNAMEI   OF COUSR2AI TO SEC-USR-LNAME
        ws.csusr01y.secUserData.secUsrLname.setValue(ws.cousr02.cousr2ai.lnamei);
        // COB(225): SET USR-MODIFIED-YES TO TRUE
        ws.wsVariables.setUsrModifiedYes(true);
        // COB(226): END-IF
      }
      // COB(227): IF PASSWDI  OF COUSR2AI NOT = SEC-USR-PWD
      if (!ws.cousr02.cousr2ai.passwdi.equals(ws.csusr01y.secUserData.secUsrPwd)) {
        // COB(228): MOVE PASSWDI  OF COUSR2AI TO SEC-USR-PWD
        ws.csusr01y.secUserData.secUsrPwd.setValue(ws.cousr02.cousr2ai.passwdi);
        // COB(229): SET USR-MODIFIED-YES TO TRUE
        ws.wsVariables.setUsrModifiedYes(true);
        // COB(230): END-IF
      }
      // COB(231): IF USRTYPEI  OF COUSR2AI NOT = SEC-USR-TYPE
      if (!ws.cousr02.cousr2ai.usrtypei.equals(ws.csusr01y.secUserData.secUsrType)) {
        // COB(232): MOVE USRTYPEI OF COUSR2AI TO SEC-USR-TYPE
        ws.csusr01y.secUserData.secUsrType.setValue(ws.cousr02.cousr2ai.usrtypei);
        // COB(233): SET USR-MODIFIED-YES TO TRUE
        ws.wsVariables.setUsrModifiedYes(true);
        // COB(234): END-IF
      }
      // COB(236): IF USR-MODIFIED-YES
      if (ws.wsVariables.usrModifiedYes()) {
        // COB(237): PERFORM UPDATE-USER-SEC-FILE
        updateUserSecFile();
        // COB(238): ELSE
      } else {
        // COB(239): MOVE 'Please modify to update ...' TO
        // COB(239):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Please modify to update ...");
        // COB(241): MOVE DFHRED       TO ERRMSGC  OF COUSR2AO
        ws.cousr02.cousr2ao.errmsgc.setValue(Dfhbmsca.DFHRED.getValue());
        // COB(242): PERFORM SEND-USRUPD-SCREEN
        sendUsrupdScreen();
        // COB(243): END-IF
      }
      // COB(245): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RETURN-TO-PREV-SCREEN
   *----------------------------------------------------------------*/
  protected void returnToPrevScreen() {
    // COB(252): IF CDEMO-TO-PROGRAM = LOW-VALUES OR SPACES
    if (ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isLowValues()
        || ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isSpaces()) {
      // COB(253): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(254): END-IF
    }
    // COB(255): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsVariables.wsTranid);
    // COB(256): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(ws.wsVariables.wsPgmname);
    // COB(257): MOVE ZEROS        TO CDEMO-PGM-CONTEXT
    ws.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.zeros();
    // COB(258): EXEC CICS
    // COB(258):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
    // COB(258):     COMMAREA(CARDDEMO-COMMAREA)
    // COB(258): END-EXEC.
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
   *                      SEND-USRUPD-SCREEN
   *----------------------------------------------------------------*/
  protected void sendUsrupdScreen() {
    // COB(268): PERFORM POPULATE-HEADER-INFO
    populateHeaderInfo();
    // COB(270): MOVE WS-MESSAGE TO ERRMSGO OF COUSR2AO
    ws.cousr02.cousr2ao.errmsgo.setValue(ws.wsVariables.wsMessage);
    // COB(272): EXEC CICS SEND
    // COB(272):           MAP('COUSR2A')
    // COB(272):           MAPSET('COUSR02')
    // COB(272):           FROM(COUSR2AO)
    // COB(272):           ERASE
    // COB(272):           CURSOR
    // COB(272): END-EXEC.
    try {
      supernaut
          .sendMap("COUSR2A") //
          .mapset("COUSR02") //
          .from(ws.cousr02.cousr2ao) //
          .erase() //
          .cursor() //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RECEIVE-USRUPD-SCREEN
   *----------------------------------------------------------------*/
  protected void receiveUsrupdScreen() {
    // COB(285): EXEC CICS RECEIVE
    // COB(285):           MAP('COUSR2A')
    // COB(285):           MAPSET('COUSR02')
    // COB(285):           INTO(COUSR2AI)
    // COB(285):           RESP(WS-RESP-CD)
    // COB(285):           RESP2(WS-REAS-CD)
    // COB(285): END-EXEC.
    supernaut
        .receiveMap("COUSR2A") //
        .mapset("COUSR02") //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cousr02.cousr2ai) //
        .exec();
  }

  /***
   *----------------------------------------------------------------*
   *                      POPULATE-HEADER-INFO
   *----------------------------------------------------------------*/
  protected void populateHeaderInfo() {
    // COB(298): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(300): MOVE CCDA-TITLE01           TO TITLE01O OF COUSR2AO
    ws.cousr02.cousr2ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(301): MOVE CCDA-TITLE02           TO TITLE02O OF COUSR2AO
    ws.cousr02.cousr2ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(302): MOVE WS-TRANID              TO TRNNAMEO OF COUSR2AO
    ws.cousr02.cousr2ao.trnnameo.setValue(ws.wsVariables.wsTranid);
    // COB(303): MOVE WS-PGMNAME             TO PGMNAMEO OF COUSR2AO
    ws.cousr02.cousr2ao.pgmnameo.setValue(ws.wsVariables.wsPgmname);
    // COB(305): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(306): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(307): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(309): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF COUSR2AO
    ws.cousr02.cousr2ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(311): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(312): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(313): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(315): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF COUSR2AO.
    ws.cousr02.cousr2ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***
   *----------------------------------------------------------------*
   *                      READ-USER-SEC-FILE
   *----------------------------------------------------------------*/
  protected void readUserSecFile() {
    // COB(322): EXEC CICS READ
    // COB(322):      DATASET   (WS-USRSEC-FILE)
    // COB(322):      INTO      (SEC-USER-DATA)
    // COB(322):      LENGTH    (LENGTH OF SEC-USER-DATA)
    // COB(322):      RIDFLD    (SEC-USR-ID)
    // COB(322):      KEYLENGTH (LENGTH OF SEC-USR-ID)
    // COB(322):      UPDATE
    // COB(322):      RESP      (WS-RESP-CD)
    // COB(322):      RESP2     (WS-REAS-CD)
    // COB(322): END-EXEC.
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
    // COB(333): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(334): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(335): CONTINUE
        // do nothing
        // COB(336): MOVE 'Press PF5 key to save your updates ...' TO
        // COB(336):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Press PF5 key to save your updates ...");
        // COB(338): MOVE DFHNEUTR       TO ERRMSGC  OF COUSR2AO
        ws.cousr02.cousr2ao.errmsgc.setValue(Dfhbmsca.DFHNEUTR.getValue());
        // COB(339): PERFORM SEND-USRUPD-SCREEN
        sendUsrupdScreen();
        // COB(340): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(341): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(342): MOVE 'User ID NOT found...' TO
        // COB(342):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("User ID NOT found...");
        // COB(344): MOVE -1       TO USRIDINL OF COUSR2AI
        ws.cousr02.cousr2ai.usridinl.setValue(-1);
        // COB(345): PERFORM SEND-USRUPD-SCREEN
        sendUsrupdScreen();
        // COB(346): WHEN OTHER
        break;
      default:
        // COB(347): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(348): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(349): MOVE 'Unable to lookup User...' TO
        // COB(349):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to lookup User...");
        // COB(351): MOVE -1       TO FNAMEL OF COUSR2AI
        ws.cousr02.cousr2ai.fnamel.setValue(-1);
        // COB(352): PERFORM SEND-USRUPD-SCREEN
        sendUsrupdScreen();
        // COB(353): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      UPDATE-USER-SEC-FILE
   *----------------------------------------------------------------*/
  protected void updateUserSecFile() {
    // COB(360): EXEC CICS REWRITE
    // COB(360):      DATASET   (WS-USRSEC-FILE)
    // COB(360):      FROM      (SEC-USER-DATA)
    // COB(360):      LENGTH    (LENGTH OF SEC-USER-DATA)
    // COB(360):      RESP      (WS-RESP-CD)
    // COB(360):      RESP2     (WS-REAS-CD)
    // COB(360): END-EXEC.
    supernaut
        .rewrite(ws.wsVariables.wsUsrsecFile.getValue()) //
        .length(ws.csusr01y.secUserData.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .from(ws.csusr01y.secUserData) //
        .exec();
    // COB(368): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(369): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(370): MOVE SPACES             TO WS-MESSAGE
        ws.wsVariables.wsMessage.spaces();
        // COB(371): MOVE DFHGREEN           TO ERRMSGC  OF COUSR2AO
        ws.cousr02.cousr2ao.errmsgc.setValue(Dfhbmsca.DFHGREEN.getValue());
        // COB(372): STRING 'User '     DELIMITED BY SIZE
        // COB(372):        SEC-USR-ID  DELIMITED BY SPACE
        // COB(372):        ' has been updated ...' DELIMITED BY SIZE
        // COB(372):   INTO WS-MESSAGE
        ws.wsVariables.wsMessage.concat(
            "User ",
            ws.csusr01y.secUserData.secUsrId.substringToValue(" "),
            " has been updated ...");
        // COB(376): PERFORM SEND-USRUPD-SCREEN
        sendUsrupdScreen();
        // COB(377): WHEN DFHRESP(NOTFND)
        break;
      case Dfhresp.NOTFND:
        // COB(378): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(379): MOVE 'User ID NOT found...' TO
        // COB(379):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("User ID NOT found...");
        // COB(381): MOVE -1       TO USRIDINL OF COUSR2AI
        ws.cousr02.cousr2ai.usridinl.setValue(-1);
        // COB(382): PERFORM SEND-USRUPD-SCREEN
        sendUsrupdScreen();
        // COB(383): WHEN OTHER
        break;
      default:
        // COB(384): DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        sysout.display("RESP:", ws.wsVariables.wsRespCd, "REAS:", ws.wsVariables.wsReasCd);
        // COB(385): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(386): MOVE 'Unable to Update User...' TO
        // COB(386):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to Update User...");
        // COB(388): MOVE -1       TO FNAMEL OF COUSR2AI
        ws.cousr02.cousr2ai.fnamel.setValue(-1);
        // COB(389): PERFORM SEND-USRUPD-SCREEN
        sendUsrupdScreen();
        // COB(390): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      CLEAR-CURRENT-SCREEN
   *----------------------------------------------------------------*/
  protected void clearCurrentScreen() {
    // COB(397): PERFORM INITIALIZE-ALL-FIELDS.
    initializeAllFields();
    // COB(398): PERFORM SEND-USRUPD-SCREEN.
    sendUsrupdScreen();
  }

  /***
   *----------------------------------------------------------------*
   *                      INITIALIZE-ALL-FIELDS
   *----------------------------------------------------------------*/
  protected void initializeAllFields() {
    // COB(405): MOVE -1              TO USRIDINL OF COUSR2AI
    ws.cousr02.cousr2ai.usridinl.setValue(-1);
    // COB(406): MOVE SPACES          TO USRIDINI OF COUSR2AI
    // COB(406):                         FNAMEI   OF COUSR2AI
    // COB(406):                         LNAMEI   OF COUSR2AI
    // COB(406):                         PASSWDI  OF COUSR2AI
    // COB(406):                         USRTYPEI OF COUSR2AI
    // COB(406):                         WS-MESSAGE.
    ws.cousr02.cousr2ai.usridini.spaces();
    ws.cousr02.cousr2ai.fnamei.spaces();
    ws.cousr02.cousr2ai.lnamei.spaces();
    ws.cousr02.cousr2ai.passwdi.spaces();
    ws.cousr02.cousr2ai.usrtypei.spaces();
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
