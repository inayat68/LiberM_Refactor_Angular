package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cousr01c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COUSR01C extends AbstractOnlineProgram {

  @ProgramStorage final Cousr01cWorking ws = new Cousr01cWorking();

  // Linkage
  public static class Cousr01cLinkage extends NGroup {
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

  final Cousr01cLinkage params = new Cousr01cLinkage();

  public COUSR01C(Context supernaut) {
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
    // COB(73): SET ERR-FLG-OFF TO TRUE
    ws.wsVariables.setErrFlgOff(true);
    // COB(75): MOVE SPACES TO WS-MESSAGE
    // COB(75):                ERRMSGO OF COUSR1AO
    ws.wsVariables.wsMessage.spaces();
    ws.cousr01.cousr1ao.errmsgo.spaces();
    // COB(78): IF EIBCALEN = 0
    if (dfheiblk.getEibcalen() == 0) {
      // COB(79): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(80): PERFORM RETURN-TO-PREV-SCREEN
      returnToPrevScreen();
      // COB(81): ELSE
    } else {
      // COB(82): MOVE DFHCOMMAREA(1:EIBCALEN) TO CARDDEMO-COMMAREA
      ws.cocom01y.carddemoCommarea.setValue(params.dfhcommarea, 0, dfheiblk.getEibcalen());
      // COB(83): IF NOT CDEMO-PGM-REENTER
      if (!ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmReenter()) {
        // COB(84): SET CDEMO-PGM-REENTER    TO TRUE
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.setCdemoPgmReenter(true);
        // COB(85): MOVE LOW-VALUES          TO COUSR1AO
        ws.cousr01.cousr1ao.lowValues();
        // COB(86): MOVE -1       TO FNAMEL OF COUSR1AI
        ws.cousr01.cousr1ai.fnamel.setValue(-1);
        // COB(87): PERFORM SEND-USRADD-SCREEN
        sendUsraddScreen();
        // COB(88): ELSE
      } else {
        // COB(89): PERFORM RECEIVE-USRADD-SCREEN
        receiveUsraddScreen();
        // COB(90): EVALUATE EIBAID
        switch (dfheiblk.getEibaid()) {
            // COB(91): WHEN DFHENTER
          case DFHENTER:
            // COB(92): PERFORM PROCESS-ENTER-KEY
            processEnterKey();
            // COB(93): WHEN DFHPF3
            break;
          case DFHPF3:
            // COB(94): MOVE 'COADM01C' TO CDEMO-TO-PROGRAM
            ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COADM01C");
            // COB(95): PERFORM RETURN-TO-PREV-SCREEN
            returnToPrevScreen();
            // COB(96): WHEN DFHPF4
            break;
          case DFHPF4:
            // COB(97): PERFORM CLEAR-CURRENT-SCREEN
            clearCurrentScreen();
            // COB(98): WHEN OTHER
            break;
          default:
            // COB(99): MOVE 'Y'                       TO WS-ERR-FLG
            ws.wsVariables.wsErrFlg.setString("Y");
            // COB(100): MOVE -1       TO FNAMEL OF COUSR1AI
            ws.cousr01.cousr1ai.fnamel.setValue(-1);
            // COB(101): MOVE CCDA-MSG-INVALID-KEY      TO WS-MESSAGE
            ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgInvalidKey);
            // COB(102): PERFORM SEND-USRADD-SCREEN
            sendUsraddScreen();
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
    // COB(117): EVALUATE TRUE
    // COB(118): WHEN FNAMEI OF COUSR1AI = SPACES OR LOW-VALUES
    if (ws.cousr01.cousr1ai.fnamei.isSpaces() || ws.cousr01.cousr1ai.fnamei.isLowValues()) {
      // COB(119): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(120): MOVE 'First Name can NOT be empty...' TO
      // COB(120):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("First Name can NOT be empty...");
      // COB(122): MOVE -1       TO FNAMEL OF COUSR1AI
      ws.cousr01.cousr1ai.fnamel.setValue(-1);
      // COB(123): PERFORM SEND-USRADD-SCREEN
      sendUsraddScreen();
      // COB(124): WHEN LNAMEI OF COUSR1AI = SPACES OR LOW-VALUES
    } else if (ws.cousr01.cousr1ai.lnamei.isSpaces() || ws.cousr01.cousr1ai.lnamei.isLowValues()) {
      // COB(125): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(126): MOVE 'Last Name can NOT be empty...' TO
      // COB(126):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Last Name can NOT be empty...");
      // COB(128): MOVE -1       TO LNAMEL OF COUSR1AI
      ws.cousr01.cousr1ai.lnamel.setValue(-1);
      // COB(129): PERFORM SEND-USRADD-SCREEN
      sendUsraddScreen();
      // COB(130): WHEN USERIDI OF COUSR1AI = SPACES OR LOW-VALUES
    } else if (ws.cousr01.cousr1ai.useridi.isSpaces()
        || ws.cousr01.cousr1ai.useridi.isLowValues()) {
      // COB(131): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(132): MOVE 'User ID can NOT be empty...' TO
      // COB(132):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("User ID can NOT be empty...");
      // COB(134): MOVE -1       TO USERIDL OF COUSR1AI
      ws.cousr01.cousr1ai.useridl.setValue(-1);
      // COB(135): PERFORM SEND-USRADD-SCREEN
      sendUsraddScreen();
      // COB(136): WHEN PASSWDI OF COUSR1AI = SPACES OR LOW-VALUES
    } else if (ws.cousr01.cousr1ai.passwdi.isSpaces()
        || ws.cousr01.cousr1ai.passwdi.isLowValues()) {
      // COB(137): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(138): MOVE 'Password can NOT be empty...' TO
      // COB(138):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Password can NOT be empty...");
      // COB(140): MOVE -1       TO PASSWDL OF COUSR1AI
      ws.cousr01.cousr1ai.passwdl.setValue(-1);
      // COB(141): PERFORM SEND-USRADD-SCREEN
      sendUsraddScreen();
      // COB(142): WHEN USRTYPEI OF COUSR1AI = SPACES OR LOW-VALUES
    } else if (ws.cousr01.cousr1ai.usrtypei.isSpaces()
        || ws.cousr01.cousr1ai.usrtypei.isLowValues()) {
      // COB(143): MOVE 'Y'     TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(144): MOVE 'User Type can NOT be empty...' TO
      // COB(144):                 WS-MESSAGE
      ws.wsVariables.wsMessage.setString("User Type can NOT be empty...");
      // COB(146): MOVE -1       TO USRTYPEL OF COUSR1AI
      ws.cousr01.cousr1ai.usrtypel.setValue(-1);
      // COB(147): PERFORM SEND-USRADD-SCREEN
      sendUsraddScreen();
      // COB(148): WHEN OTHER
    } else {
      // COB(149): MOVE -1       TO FNAMEL OF COUSR1AI
      ws.cousr01.cousr1ai.fnamel.setValue(-1);
      // COB(150): CONTINUE
      // do nothing
      // COB(151): END-EVALUATE
    }
    // COB(153): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(154): MOVE USERIDI  OF COUSR1AI TO SEC-USR-ID
      ws.csusr01y.secUserData.secUsrId.setValue(ws.cousr01.cousr1ai.useridi);
      // COB(155): MOVE FNAMEI   OF COUSR1AI TO SEC-USR-FNAME
      ws.csusr01y.secUserData.secUsrFname.setValue(ws.cousr01.cousr1ai.fnamei);
      // COB(156): MOVE LNAMEI   OF COUSR1AI TO SEC-USR-LNAME
      ws.csusr01y.secUserData.secUsrLname.setValue(ws.cousr01.cousr1ai.lnamei);
      // COB(157): MOVE PASSWDI  OF COUSR1AI TO SEC-USR-PWD
      ws.csusr01y.secUserData.secUsrPwd.setValue(ws.cousr01.cousr1ai.passwdi);
      // COB(158): MOVE USRTYPEI OF COUSR1AI TO SEC-USR-TYPE
      ws.csusr01y.secUserData.secUsrType.setValue(ws.cousr01.cousr1ai.usrtypei);
      // COB(159): PERFORM WRITE-USER-SEC-FILE
      writeUserSecFile();
      // COB(160): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RETURN-TO-PREV-SCREEN
   *----------------------------------------------------------------*/
  protected void returnToPrevScreen() {
    // COB(167): IF CDEMO-TO-PROGRAM = LOW-VALUES OR SPACES
    if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isLowValues()
        || ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.isSpaces()) {
      // COB(168): MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM
      ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.setString("COSGN00C");
      // COB(169): END-IF
    }
    // COB(170): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
    ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(ws.wsVariables.wsTranid);
    // COB(171): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
    ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
        ws.wsVariables.wsPgmname);
    // COB(174): MOVE ZEROS        TO CDEMO-PGM-CONTEXT
    //     MOVE WS-USER-ID   TO CDEMO-USER-ID
    //     MOVE SEC-USR-TYPE TO CDEMO-USER-TYPE
    ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.zeros();
    // COB(175): EXEC CICS
    // COB(175):     XCTL PROGRAM(CDEMO-TO-PROGRAM)
    // COB(175):     COMMAREA(CARDDEMO-COMMAREA)
    // COB(175): END-EXEC.
    try {
      supernaut
          .xctl(ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoToProgram.getValue()) //
          .commarea(ws.cocom01y.carddemoCommarea) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *
   *----------------------------------------------------------------*
   *                      SEND-USRADD-SCREEN
   *----------------------------------------------------------------*/
  protected void sendUsraddScreen() {
    // COB(186): PERFORM POPULATE-HEADER-INFO
    populateHeaderInfo();
    // COB(188): MOVE WS-MESSAGE TO ERRMSGO OF COUSR1AO
    ws.cousr01.cousr1ao.errmsgo.setValue(ws.wsVariables.wsMessage);
    // COB(190): EXEC CICS SEND
    // COB(190):           MAP('COUSR1A')
    // COB(190):           MAPSET('COUSR01')
    // COB(190):           FROM(COUSR1AO)
    // COB(190):           ERASE
    // COB(190):           CURSOR
    // COB(190): END-EXEC.
    try {
      supernaut
          .sendMap("COUSR1A") //
          .mapset("COUSR01") //
          .from(ws.cousr01.cousr1ao) //
          .erase() //
          .cursor() //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      RECEIVE-USRADD-SCREEN
   *----------------------------------------------------------------*/
  protected void receiveUsraddScreen() {
    // COB(203): EXEC CICS RECEIVE
    // COB(203):           MAP('COUSR1A')
    // COB(203):           MAPSET('COUSR01')
    // COB(203):           INTO(COUSR1AI)
    // COB(203):           RESP(WS-RESP-CD)
    // COB(203):           RESP2(WS-REAS-CD)
    // COB(203): END-EXEC.
    supernaut
        .receiveMap("COUSR1A") //
        .mapset("COUSR01") //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.cousr01.cousr1ai) //
        .exec();
  }

  /***
   *----------------------------------------------------------------*
   *                      POPULATE-HEADER-INFO
   *----------------------------------------------------------------*/
  protected void populateHeaderInfo() {
    // COB(216): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(218): MOVE CCDA-TITLE01           TO TITLE01O OF COUSR1AO
    ws.cousr01.cousr1ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(219): MOVE CCDA-TITLE02           TO TITLE02O OF COUSR1AO
    ws.cousr01.cousr1ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(220): MOVE WS-TRANID              TO TRNNAMEO OF COUSR1AO
    ws.cousr01.cousr1ao.trnnameo.setValue(ws.wsVariables.wsTranid);
    // COB(221): MOVE WS-PGMNAME             TO PGMNAMEO OF COUSR1AO
    ws.cousr01.cousr1ao.pgmnameo.setValue(ws.wsVariables.wsPgmname);
    // COB(223): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(224): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(225): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(227): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF COUSR1AO
    ws.cousr01.cousr1ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(229): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(230): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(231): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(233): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF COUSR1AO.
    ws.cousr01.cousr1ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
  }

  /***
   *----------------------------------------------------------------*
   *                      WRITE-USER-SEC-FILE
   *----------------------------------------------------------------*/
  protected void writeUserSecFile() {
    // COB(240): EXEC CICS WRITE
    // COB(240):      DATASET   (WS-USRSEC-FILE)
    // COB(240):      FROM      (SEC-USER-DATA)
    // COB(240):      LENGTH    (LENGTH OF SEC-USER-DATA)
    // COB(240):      RIDFLD    (SEC-USR-ID)
    // COB(240):      KEYLENGTH (LENGTH OF SEC-USR-ID)
    // COB(240):      RESP      (WS-RESP-CD)
    // COB(240):      RESP2     (WS-REAS-CD)
    // COB(240): END-EXEC.
    supernaut
        .write(ws.wsVariables.wsUsrsecFile.getValue()) //
        .length(ws.csusr01y.secUserData.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .from(ws.csusr01y.secUserData) //
        .ridfld(ws.csusr01y.secUserData.secUsrId) //
        .keylength(ws.csusr01y.secUserData.secUsrId.length()) //
        .exec();
    // COB(250): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(251): WHEN DFHRESP(NORMAL)
      case Dfhresp.NORMAL:
        // COB(252): PERFORM INITIALIZE-ALL-FIELDS
        initializeAllFields();
        // COB(253): MOVE SPACES             TO WS-MESSAGE
        ws.wsVariables.wsMessage.spaces();
        // COB(254): MOVE DFHGREEN           TO ERRMSGC  OF COUSR1AO
        ws.cousr01.cousr1ao.errmsgc.setValue(Dfhbmsca.DFHGREEN.getValue());
        // COB(255): STRING 'User '     DELIMITED BY SIZE
        // COB(255):        SEC-USR-ID  DELIMITED BY SPACE
        // COB(255):        ' has been added ...' DELIMITED BY SIZE
        // COB(255):   INTO WS-MESSAGE
        ws.wsVariables.wsMessage.concat(
            "User ", ws.csusr01y.secUserData.secUsrId.substringToValue(" "), " has been added ...");
        // COB(259): PERFORM SEND-USRADD-SCREEN
        sendUsraddScreen();
        // COB(260): WHEN DFHRESP(DUPKEY)
        break;
      case Dfhresp.DUPKEY:
        // COB(261): WHEN DFHRESP(DUPREC)
      case Dfhresp.DUPREC:
        // COB(262): MOVE 'Y'     TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(263): MOVE 'User ID already exist...' TO
        // COB(263):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("User ID already exist...");
        // COB(265): MOVE -1       TO USERIDL OF COUSR1AI
        ws.cousr01.cousr1ai.useridl.setValue(-1);
        // COB(266): PERFORM SEND-USRADD-SCREEN
        sendUsraddScreen();
        // COB(267): WHEN OTHER
        break;
      default:
        // COB(269): MOVE 'Y'     TO WS-ERR-FLG
        //             DISPLAY 'RESP:' WS-RESP-CD 'REAS:' WS-REAS-CD
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(270): MOVE 'Unable to Add User...' TO
        // COB(270):                 WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to Add User...");
        // COB(272): MOVE -1       TO FNAMEL OF COUSR1AI
        ws.cousr01.cousr1ai.fnamel.setValue(-1);
        // COB(273): PERFORM SEND-USRADD-SCREEN
        sendUsraddScreen();
        // COB(274): END-EVALUATE.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      CLEAR-CURRENT-SCREEN
   *----------------------------------------------------------------*/
  protected void clearCurrentScreen() {
    // COB(281): PERFORM INITIALIZE-ALL-FIELDS.
    initializeAllFields();
    // COB(282): PERFORM SEND-USRADD-SCREEN.
    sendUsraddScreen();
  }

  /***
   *----------------------------------------------------------------*
   *                      INITIALIZE-ALL-FIELDS
   *----------------------------------------------------------------*/
  protected void initializeAllFields() {
    // COB(289): MOVE -1              TO FNAMEL OF COUSR1AI
    ws.cousr01.cousr1ai.fnamel.setValue(-1);
    // COB(290): MOVE SPACES          TO USERIDI  OF COUSR1AI
    // COB(290):                         FNAMEI   OF COUSR1AI
    // COB(290):                         LNAMEI   OF COUSR1AI
    // COB(290):                         PASSWDI  OF COUSR1AI
    // COB(290):                         USRTYPEI OF COUSR1AI
    // COB(290):                         WS-MESSAGE.
    ws.cousr01.cousr1ai.useridi.spaces();
    ws.cousr01.cousr1ai.fnamei.spaces();
    ws.cousr01.cousr1ai.lnamei.spaces();
    ws.cousr01.cousr1ai.passwdi.spaces();
    ws.cousr01.cousr1ai.usrtypei.spaces();
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
