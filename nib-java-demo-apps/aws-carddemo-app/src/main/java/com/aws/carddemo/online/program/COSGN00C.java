package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cosgn00c.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COSGN00C extends AbstractOnlineProgram {

  @ProgramStorage final Cosgn00cWorking ws = new Cosgn00cWorking();

  // Linkage
  public static class Cosgn00cLinkage extends NGroup {
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

  final Cosgn00cLinkage params = new Cosgn00cLinkage();

  public COSGN00C(Context supernaut) {
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
    // COB(75): SET ERR-FLG-OFF TO TRUE
    ws.wsVariables.setErrFlgOff(true);
    // COB(77): MOVE SPACES TO WS-MESSAGE
    // COB(77):                ERRMSGO OF COSGN0AO
    ws.wsVariables.wsMessage.spaces();
    ws.cosgn00.cosgn0ao.errmsgo.spaces();
    // COB(80): IF EIBCALEN = 0
    if (dfheiblk.getEibcalen() == 0) {
      // COB(81): MOVE LOW-VALUES TO COSGN0AO
      ws.cosgn00.cosgn0ao.lowValues();
      // COB(82): MOVE -1       TO USERIDL OF COSGN0AI
      ws.cosgn00.cosgn0ai.useridl.setValue(-1);
      // COB(83): PERFORM SEND-SIGNON-SCREEN
      sendSignonScreen();
      // COB(84): ELSE
    } else {
      // COB(85): EVALUATE EIBAID
      switch (dfheiblk.getEibaid()) {
          // COB(86): WHEN DFHENTER
        case DFHENTER:
          // COB(87): PERFORM PROCESS-ENTER-KEY
          processEnterKey();
          // COB(88): WHEN DFHPF3
          break;
        case DFHPF3:
          // COB(89): MOVE CCDA-MSG-THANK-YOU        TO WS-MESSAGE
          ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgThankYou);
          // COB(90): PERFORM SEND-PLAIN-TEXT
          sendPlainText();
          // COB(91): WHEN OTHER
          break;
        default:
          // COB(92): MOVE 'Y'                       TO WS-ERR-FLG
          ws.wsVariables.wsErrFlg.setString("Y");
          // COB(93): MOVE CCDA-MSG-INVALID-KEY      TO WS-MESSAGE
          ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgInvalidKey);
          // COB(94): PERFORM SEND-SIGNON-SCREEN
          sendSignonScreen();
          // COB(95): END-EVALUATE
      }
      // COB(96): END-IF.
    }
    // COB(98): EXEC CICS RETURN
    // COB(98):           TRANSID (WS-TRANID)
    // COB(98):           COMMAREA (CARDDEMO-COMMAREA)
    // COB(98):           LENGTH(LENGTH OF CARDDEMO-COMMAREA)
    // COB(98): END-EXEC.
    try {
      supernaut
          .returnTransid(ws.wsVariables.wsTranid) //
          .commarea(ws.cocom01y.carddemoCommarea) //
          .length(ws.cocom01y.carddemoCommarea.length()) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *
   *----------------------------------------------------------------*
   *                      PROCESS-ENTER-KEY
   *----------------------------------------------------------------*/
  protected void processEnterKey() {
    // COB(110): EXEC CICS RECEIVE
    // COB(110):           MAP('COSGN0A')
    // COB(110):           MAPSET('COSGN00')
    // COB(110):           RESP(WS-RESP-CD)
    // COB(110):           RESP2(WS-REAS-CD)
    // COB(110): END-EXEC.
    supernaut
        .receiveMap("COSGN0A") //
        .mapset("COSGN00") //
        .into(ws.cosgn00.cosgn0ai) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .exec();
    // COB(117): EVALUATE TRUE
    // COB(118): WHEN USERIDI OF COSGN0AI = SPACES OR LOW-VALUES
    if (ws.cosgn00.cosgn0ai.useridi.isSpaces() || ws.cosgn00.cosgn0ai.useridi.isLowValues()) {
      // COB(119): MOVE 'Y'      TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(120): MOVE 'Please enter User ID ...' TO WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Please enter User ID ...");
      // COB(121): MOVE -1       TO USERIDL OF COSGN0AI
      ws.cosgn00.cosgn0ai.useridl.setValue(-1);
      // COB(122): PERFORM SEND-SIGNON-SCREEN
      sendSignonScreen();
      // COB(123): WHEN PASSWDI OF COSGN0AI = SPACES OR LOW-VALUES
    } else if (ws.cosgn00.cosgn0ai.passwdi.isSpaces()
        || ws.cosgn00.cosgn0ai.passwdi.isLowValues()) {
      // COB(124): MOVE 'Y'      TO WS-ERR-FLG
      ws.wsVariables.wsErrFlg.setString("Y");
      // COB(125): MOVE 'Please enter Password ...' TO WS-MESSAGE
      ws.wsVariables.wsMessage.setString("Please enter Password ...");
      // COB(126): MOVE -1       TO PASSWDL OF COSGN0AI
      ws.cosgn00.cosgn0ai.passwdl.setValue(-1);
      // COB(127): PERFORM SEND-SIGNON-SCREEN
      sendSignonScreen();
      // COB(128): WHEN OTHER
    } else {
      // COB(129): CONTINUE
      // do nothing
      // COB(130): END-EVALUATE.
    }
    // COB(132): MOVE FUNCTION UPPER-CASE(USERIDI OF COSGN0AI) TO
    // COB(132):                 WS-USER-ID
    // COB(132):                 CDEMO-USER-ID
    ws.wsVariables.wsUserId.setValue(ws.cosgn00.cosgn0ai.useridi.toUpperCase());
    ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoUserId.setValue(
        ws.cosgn00.cosgn0ai.useridi.toUpperCase());
    // COB(135): MOVE FUNCTION UPPER-CASE(PASSWDI OF COSGN0AI) TO
    // COB(135):                 WS-USER-PWD
    ws.wsVariables.wsUserPwd.setValue(ws.cosgn00.cosgn0ai.passwdi.toUpperCase());
    // COB(138): IF NOT ERR-FLG-ON
    if (!ws.wsVariables.errFlgOn()) {
      // COB(139): PERFORM READ-USER-SEC-FILE
      readUserSecFile();
      // COB(140): END-IF.
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      SEND-SIGNON-SCREEN
   *----------------------------------------------------------------*/
  protected void sendSignonScreen() {
    // COB(147): PERFORM POPULATE-HEADER-INFO
    populateHeaderInfo();
    // COB(149): MOVE WS-MESSAGE TO ERRMSGO OF COSGN0AO
    ws.cosgn00.cosgn0ao.errmsgo.setValue(ws.wsVariables.wsMessage);
    // COB(151): EXEC CICS SEND
    // COB(151):           MAP('COSGN0A')
    // COB(151):           MAPSET('COSGN00')
    // COB(151):           FROM(COSGN0AO)
    // COB(151):           ERASE
    // COB(151):           CURSOR
    // COB(151): END-EXEC.
    try {
      supernaut
          .sendMap("COSGN0A") //
          .mapset("COSGN00") //
          .from(ws.cosgn00.cosgn0ao) //
          .erase() //
          .cursor() //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      SEND-PLAIN-TEXT
   *----------------------------------------------------------------*/
  protected void sendPlainText() {
    // COB(164): EXEC CICS SEND TEXT
    // COB(164):           FROM(WS-MESSAGE)
    // COB(164):           LENGTH(LENGTH OF WS-MESSAGE)
    // COB(164):           ERASE
    // COB(164):           FREEKB
    // COB(164): END-EXEC.
    try {
      supernaut
          .sendText() //
          .from(ws.wsVariables.wsMessage) //
          .length(ws.wsVariables.wsMessage.length()) //
          .freekb() //
          .erase() //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    // COB(171): EXEC CICS RETURN
    // COB(171): END-EXEC.
    try {
      supernaut.returnToCaller();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      POPULATE-HEADER-INFO
   *----------------------------------------------------------------*/
  protected void populateHeaderInfo() {
    // COB(179): MOVE FUNCTION CURRENT-DATE  TO WS-CURDATE-DATA
    ws.csdat01y.wsDateTime.wsCurdateData.setValue(environment.now().toCurrentDate());
    // COB(181): MOVE CCDA-TITLE01           TO TITLE01O OF COSGN0AO
    ws.cosgn00.cosgn0ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // COB(182): MOVE CCDA-TITLE02           TO TITLE02O OF COSGN0AO
    ws.cosgn00.cosgn0ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // COB(183): MOVE WS-TRANID              TO TRNNAMEO OF COSGN0AO
    ws.cosgn00.cosgn0ao.trnnameo.setValue(ws.wsVariables.wsTranid);
    // COB(184): MOVE WS-PGMNAME             TO PGMNAMEO OF COSGN0AO
    ws.cosgn00.cosgn0ao.pgmnameo.setValue(ws.wsVariables.wsPgmname);
    // COB(186): MOVE WS-CURDATE-MONTH       TO WS-CURDATE-MM
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateMonth);
    // COB(187): MOVE WS-CURDATE-DAY         TO WS-CURDATE-DD
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateDay);
    // COB(188): MOVE WS-CURDATE-YEAR(3:2)   TO WS-CURDATE-YY
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurdate.wsCurdateYear, 2, 0, 2);
    // COB(190): MOVE WS-CURDATE-MM-DD-YY    TO CURDATEO OF COSGN0AO
    ws.cosgn00.cosgn0ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // COB(192): MOVE WS-CURTIME-HOURS       TO WS-CURTIME-HH
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeHours);
    // COB(193): MOVE WS-CURTIME-MINUTE      TO WS-CURTIME-MM
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeMinute);
    // COB(194): MOVE WS-CURTIME-SECOND      TO WS-CURTIME-SS
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurdateData.wsCurtime.wsCurtimeSecond);
    // COB(196): MOVE WS-CURTIME-HH-MM-SS    TO CURTIMEO OF COSGN0AO
    ws.cosgn00.cosgn0ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
    // COB(198): EXEC CICS ASSIGN
    // COB(198):     APPLID(APPLIDO OF COSGN0AO)
    // COB(198): END-EXEC
    try {
      supernaut
          .assign() //
          .applid(ws.cosgn00.cosgn0ao.applido) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    // COB(202): EXEC CICS ASSIGN
    // COB(202):     SYSID(SYSIDO OF COSGN0AO)
    // COB(202): END-EXEC.
    try {
      supernaut
          .assign() //
          .sysid(ws.cosgn00.cosgn0ao.sysido) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /***
   *----------------------------------------------------------------*
   *                      READ-USER-SEC-FILE
   *----------------------------------------------------------------*/
  protected void readUserSecFile() {
    // COB(211): EXEC CICS READ
    // COB(211):      DATASET   (WS-USRSEC-FILE)
    // COB(211):      INTO      (SEC-USER-DATA)
    // COB(211):      LENGTH    (LENGTH OF SEC-USER-DATA)
    // COB(211):      RIDFLD    (WS-USER-ID)
    // COB(211):      KEYLENGTH (LENGTH OF WS-USER-ID)
    // COB(211):      RESP      (WS-RESP-CD)
    // COB(211):      RESP2     (WS-REAS-CD)
    // COB(211): END-EXEC.
    supernaut
        .read(ws.wsVariables.wsUsrsecFile.getValue()) //
        .length(ws.csusr01y.secUserData.length()) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.csusr01y.secUserData) //
        .ridfld(ws.wsVariables.wsUserId) //
        .keylength(ws.wsVariables.wsUserId.length()) //
        .exec();
    // COB(221): EVALUATE WS-RESP-CD
    switch (ws.wsVariables.wsRespCd.getInt()) {
        // COB(222): WHEN 0
      case 0:
        // COB(223): IF SEC-USR-PWD = WS-USER-PWD
        if (ws.csusr01y.secUserData.secUsrPwd.equals(ws.wsVariables.wsUserPwd)) {
          // COB(224): MOVE WS-TRANID    TO CDEMO-FROM-TRANID
          ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
              ws.wsVariables.wsTranid);
          // COB(225): MOVE WS-PGMNAME   TO CDEMO-FROM-PROGRAM
          ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
              ws.wsVariables.wsPgmname);
          // COB(226): MOVE WS-USER-ID   TO CDEMO-USER-ID
          ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoUserId.setValue(
              ws.wsVariables.wsUserId);
          // COB(227): MOVE SEC-USR-TYPE TO CDEMO-USER-TYPE
          ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoUserType.setValue(
              ws.csusr01y.secUserData.secUsrType);
          // COB(228): MOVE ZEROS        TO CDEMO-PGM-CONTEXT
          ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.zeros();
          // COB(230): IF CDEMO-USRTYP-ADMIN
          if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoUsrtypAdmin()) {
            // COB(231): EXEC CICS XCTL
            // COB(231):   PROGRAM ('COADM01C')
            // COB(231):   COMMAREA(CARDDEMO-COMMAREA)
            // COB(231): END-EXEC
            try {
              supernaut
                  .xctl("COADM01C") //
                  .commarea(ws.cocom01y.carddemoCommarea) //
                  .exec();
            } catch (HandledConditionException e) {
              handleCommandCondition(e);
            }
            // COB(235): ELSE
          } else {
            // COB(236): EXEC CICS XCTL
            // COB(236):   PROGRAM ('COMEN01C')
            // COB(236):   COMMAREA(CARDDEMO-COMMAREA)
            // COB(236): END-EXEC
            try {
              supernaut
                  .xctl("COMEN01C") //
                  .commarea(ws.cocom01y.carddemoCommarea) //
                  .exec();
            } catch (HandledConditionException e) {
              handleCommandCondition(e);
            }
            // COB(240): END-IF
          }
          // COB(241): ELSE
        } else {
          // COB(242): MOVE 'Wrong Password. Try again ...' TO
          // COB(242):                                    WS-MESSAGE
          ws.wsVariables.wsMessage.setString("Wrong Password. Try again ...");
          // COB(244): MOVE -1       TO PASSWDL OF COSGN0AI
          ws.cosgn00.cosgn0ai.passwdl.setValue(-1);
          // COB(245): PERFORM SEND-SIGNON-SCREEN
          sendSignonScreen();
          // COB(246): END-IF
        }
        // COB(247): WHEN 13
        break;
      case 13:
        // COB(248): MOVE 'Y'      TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(249): MOVE 'User not found. Try again ...' TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("User not found. Try again ...");
        // COB(250): MOVE -1       TO USERIDL OF COSGN0AI
        ws.cosgn00.cosgn0ai.useridl.setValue(-1);
        // COB(251): PERFORM SEND-SIGNON-SCREEN
        sendSignonScreen();
        // COB(252): WHEN OTHER
        break;
      default:
        // COB(253): MOVE 'Y'      TO WS-ERR-FLG
        ws.wsVariables.wsErrFlg.setString("Y");
        // COB(254): MOVE 'Unable to verify the User ...' TO WS-MESSAGE
        ws.wsVariables.wsMessage.setString("Unable to verify the User ...");
        // COB(255): MOVE -1       TO USERIDL OF COSGN0AI
        ws.cosgn00.cosgn0ai.useridl.setValue(-1);
        // COB(256): PERFORM SEND-SIGNON-SCREEN
        sendSignonScreen();
        // COB(257): END-EVALUATE.
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
