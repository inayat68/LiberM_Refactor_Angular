package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cosgn00p.*;
import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.AbstractOnlineProgram;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;

public class COSGN00P extends AbstractOnlineProgram {

  @ProgramStorage final Cosgn00pWorking ws = new Cosgn00pWorking();

  public COSGN00P(Context supernaut) {
    super(supernaut);
  }

  @Override
  protected int procedure(AbstractNField dfhcommarea) throws ContextException {
    if (dfhcommarea != null) {
      ws.commareaPtr.setTo(dfhcommarea.getAddress());
    } else {
      ws.commareaPtr.allocate();
    }
    main();
    return 0;
  }

  /*-
   *********************************************************************
   * Program     : COSGN00P.PLI                                        *
   * Application : CardDemo                                            *
   * Type        : CICS PL I Program                                   *
   * Function    : Signon Screen for the CardDemo Application          *
   * *******************************************************************
   * Copyright Amazon.com, Inc. or its affiliates.                     *
   * All Rights Reserved.                                              *
   *                                                                   *
   * Licensed under the Apache License, Version 2.0 (the "License").   *
   * You may not use this file except in compliance with the License.  *
   * You may obtain a copy of the License at                           *
   *                                                                   *
   *    http:  www.apache.org licenses LICENSE-2.0                     *
   *                                                                   *
   * Unless required by applicable law or agreed to in writing,        *
   * software distributed under the License is distributed on an       *
   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,      *
   * either express or implied. See the License for the specific       *
   * language governing permissions and limitations under the License  *
   *********************************************************************
   */
  protected void main() {
    // PLI(62): WS_ERR_FLG = 'N';
    ws.wsVariables.wsErrFlg.setString("N");
    // PLI(63): WS_MESSAGE = ' ';
    ws.wsVariables.wsMessage.setString(" ");
    // PLI(65): IF EIBCALEN = 0 THEN
    if (dfheiblk.getEibcalen() == 0) {
      // PLI(66): CALL SEND_SIGNON_SCREEN();
      sendSignonScreen();
      // PLI(67): ELSE DO;
    } else {
      // PLI(68): SELECT(EIBAID);
      switch (dfheiblk.getEibaid()) {
          // PLI(69): WHEN (DFHENTER)
        case DFHENTER:
          // PLI(70): CALL PROCESS_ENTER_KEY(WS_ERR_FLG);
          processEnterKey(ws.wsVariables.wsErrFlg);
          // PLI(71): WHEN (DFHPF3) DO;
          break;
        case DFHPF3:
          // PLI(72): WS_MESSAGE = CCDA_MSG_THANK_YOU;
          ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgThankYou);
          // PLI(73): CALL SEND_PLAIN_TEXT();
          sendPlainText();
          // PLI(75): OTHERWISE DO;
          break;
        default:
          // PLI(76): WS_ERR_FLG = 'Y';
          ws.wsVariables.wsErrFlg.setString("Y");
          // PLI(77): WS_MESSAGE = CCDA_MSG_INVALID_KEY;
          ws.wsVariables.wsMessage.setValue(ws.csmsg01y.ccdaCommonMessages.ccdaMsgInvalidKey);
          // PLI(78): CALL SEND_SIGNON_SCREEN();
          sendSignonScreen();
          // PLI(80): END;
      }
      // PLI(81): END;
    }
    // PLI(83): CICS_LEN = STG(CARDDEMO_COMMAREA);
    ws.cicsLen.setValue(ws.cocom01y.carddemoCommarea.length());
    // PLI(84): EXEC CICS
    // PLI(84):     RETURN
    // PLI(84):     TRANSID (WS_TRANID)
    // PLI(84):     COMMAREA (CARDDEMO_COMMAREA)
    // PLI(84):     LENGTH (CICS_LEN);
    try {
      supernaut
          .returnTransid(ws.wsVariables.wsTranid) //
          .commarea(ws.cocom01y.carddemoCommarea) //
          .length(ws.cicsLen) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /*-
    *-------------------------------------------------------------------*
                          PROCESS-ENTER-KEY
    *-------------------------------------------------------------------*
  */
  protected void processEnterKey(NChar lcErrFlg) {
    // PLI(97): EXEC CICS
    // PLI(97):     RECEIVE
    // PLI(97):     MAP ('COSGN0A')
    // PLI(97):     MAPSET ('COSGN00')
    // PLI(97):     RESP (WS_RESP_CD)
    // PLI(97):     RESP2 (WS_REAS_CD);
    supernaut
        .receiveMap("COSGN0A") //
        .mapset("COSGN00") //
        .into(ws.cosgn00.cosgn0ai) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .exec();
    // PLI(104): SELECT;
    // PLI(105): WHEN (COSGN0AI.USERIDI = ' ' | COSGN0AI.USERIDI = LOW(8))
    // PLI(105): DO;
    if (ws.cosgn00.cosgn0ai.useridi.isSpaces() || ws.cosgn00.cosgn0ai.useridi.isLowValues()) {
      // PLI(107): WS_ERR_FLG = 'Y';
      ws.wsVariables.wsErrFlg.setString("Y");
      // PLI(108): WS_MESSAGE = 'Please enter User ID ...';
      ws.wsVariables.wsMessage.setString("Please enter User ID ...");
      // PLI(109): COSGN0AI.USERIDL = -1;
      ws.cosgn00.cosgn0ai.useridl.setValue(-1);
      // PLI(110): CALL SEND_SIGNON_SCREEN();
      sendSignonScreen();
      // PLI(112): WHEN (COSGN0AI.PASSWDI = ' ' | COSGN0AI.PASSWDI = LOW(8))
      // PLI(112): DO;
    } else if (ws.cosgn00.cosgn0ai.passwdi.isSpaces()
        || ws.cosgn00.cosgn0ai.passwdi.isLowValues()) {
      // PLI(114): WS_ERR_FLG = 'Y';
      ws.wsVariables.wsErrFlg.setString("Y");
      // PLI(115): WS_MESSAGE = 'Please enter Password ...';
      ws.wsVariables.wsMessage.setString("Please enter Password ...");
      // PLI(116): COSGN0AI.PASSWDL = -1;
      ws.cosgn00.cosgn0ai.passwdl.setValue(-1);
      // PLI(117): CALL SEND_SIGNON_SCREEN();
      sendSignonScreen();
      // PLI(119): OTHERWISE;
    } else {
      // PLI(119): OTHERWISE;
      // PLI(120): END;
    }
    // PLI(122): WS_USER_ID = UPPERCASE(COSGN0AI.USERIDI); /* ADDED UPPER-CASE
    ws.wsVariables.wsUserId.setValue(ws.cosgn00.cosgn0ai.useridi.toUpperCase());
    // PLI(124): WS_USER_PWD = UPPERCASE(COSGN0AI.PASSWDI);
    ws.wsVariables.wsUserPwd.setValue(ws.cosgn00.cosgn0ai.passwdi.toUpperCase());
    // PLI(126): IF WS_ERR_FLG = 'N' THEN
    if (ws.wsVariables.wsErrFlg.equals("N")) {
      // PLI(127): CALL READ_USER_SEC_FILE();
      readUserSecFile();
      // PLI(127): CALL READ_USER_SEC_FILE();
    }
  }

  /*-
    *-------------------------------------------------------------------*
                          SEND-SIGNON-SCREEN
    *-------------------------------------------------------------------*
  */
  protected void sendSignonScreen() {
    // PLI(135): CALL POPULATE_HEADER_INFO(); /* POPULATE
    populateHeaderInfo();
    // PLI(138): COSGN0AO.ERRMSGO = SUBSTR(WS_MESSAGE, 1, LENGTH(COSGN0AO.ERRMSGO));
    ws.cosgn00.cosgn0ao.errmsgo.setValue(
        ws.wsVariables.wsMessage.getString(0, ws.cosgn00.cosgn0ao.errmsgo.length()));
    // PLI(140): EXEC CICS
    // PLI(140):     SEND
    // PLI(140):     MAP ('COSGN0A')
    // PLI(140):     MAPSET ('COSGN00')
    // PLI(140):     FROM (COSGN0AO)
    // PLI(140):     ERASE
    // PLI(140):     CURSOR;
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

  /*-
    *-------------------------------------------------------------------*
                          SEND-PLAIN-TEXT
    *-------------------------------------------------------------------*
  */
  protected void sendPlainText() {
    // PLI(154): CICS_LEN = LENGTH(WS_MESSAGE);
    ws.cicsLen.setValue(ws.wsVariables.wsMessage.length());
    // PLI(155): EXEC CICS
    // PLI(155):     SEND TEXT
    // PLI(155):     FROM (WS_MESSAGE)
    // PLI(155):     LENGTH (CICS_LEN)
    // PLI(155):     ERASE
    // PLI(155):     FREEKB;
    try {
      supernaut
          .sendText() //
          .from(ws.wsVariables.wsMessage) //
          .length(ws.cicsLen) //
          .freekb() //
          .erase() //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    // PLI(162): EXEC CICS RETURN;
    try {
      supernaut.returnToCaller();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /*-
    *-------------------------------------------------------------------*
                          POPULATE-HEADER-INFO
    *-------------------------------------------------------------------*
  */
  protected void populateHeaderInfo() {
    // PLI(170): WS_CURDATE_DATA = DATETIME('YYYYMMDDHHMISS999'); /* CURRENT DATE */
    ws.csdat01y.wsCurdateData.setValue(ProgramUtils.DATE_TIME("yyyyMMddHHmmssSSS"));
    // PLI(172): COSGN0AO.TITLE01O = CCDA_TITLE01;
    ws.cosgn00.cosgn0ao.title01o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle01);
    // PLI(173): COSGN0AO.TITLE02O = CCDA_TITLE02;
    ws.cosgn00.cosgn0ao.title02o.setValue(ws.cottl01y.ccdaScreenTitle.ccdaTitle02);
    // PLI(174): COSGN0AO.TRNNAMEO = WS_TRANID;
    ws.cosgn00.cosgn0ao.trnnameo.setValue(ws.wsVariables.wsTranid);
    // PLI(175): COSGN0AO.PGMNAMEO = WS_PGMNAME;
    ws.cosgn00.cosgn0ao.pgmnameo.setValue(ws.wsVariables.wsPgmname);
    // PLI(177): WS_CURDATE_MM = WS_CURDATE_MONTH;
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateMm.setValue(
        ws.csdat01y.wsDateTime.wsCurdate.wsCurdateMonth);
    // PLI(178): WS_CURDATE_DD = WS_CURDATE_DAY;
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateDd.setValue(
        ws.csdat01y.wsDateTime.wsCurdate.wsCurdateDay);
    // PLI(179): WS_CURDATE_YY = SUBSTR(WS_CURDATE_YEAR,3,2);
    ws.csdat01y.wsDateTime.wsCurdateMmDdYy.wsCurdateYy.setValue(
        ws.csdat01y.wsDateTime.wsCurdate.wsCurdateYear.getString(2, 2));
    // PLI(181): COSGN0AO.CURDATEO = WS_CURDATE_MM_DD_YY;
    ws.cosgn00.cosgn0ao.curdateo.setValue(ws.csdat01y.wsDateTime.wsCurdateMmDdYy);
    // PLI(184): WS_CURTIME_HH = WS_CURTIME_HOURS;    /* TAKE HOURS   */
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeHh.setValue(
        ws.csdat01y.wsDateTime.wsCurtime.wsCurtimeHours);
    // PLI(185): WS_CURTIME_MM = WS_CURTIME_MINUTE;   /* TAKE MINUTES */
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeMm.setValue(
        ws.csdat01y.wsDateTime.wsCurtime.wsCurtimeMinute);
    // PLI(186): WS_CURTIME_SS = WS_CURTIME_SECOND;   /* TAKE SECONDS */
    ws.csdat01y.wsDateTime.wsCurtimeHhMmSs.wsCurtimeSs.setValue(
        ws.csdat01y.wsDateTime.wsCurtime.wsCurtimeSecond);
    // PLI(190): COSGN0AO.CURTIMEO = WS_CURTIME_HH_MM_SS;
    ws.cosgn00.cosgn0ao.curtimeo.setValue(ws.csdat01y.wsDateTime.wsCurtimeHhMmSs);
    // PLI(193): EXEC CICS
    // PLI(193):     ASSIGN
    // PLI(193):     APPLID (COSGN0AO.APPLIDO);
    try {
      supernaut
          .assign() //
          .applid(ws.cosgn00.cosgn0ao.applido) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    // PLI(197): EXEC CICS
    // PLI(197):     ASSIGN
    // PLI(197):     SYSID (COSGN0AO.SYSIDO);
    try {
      supernaut
          .assign() //
          .sysid(ws.cosgn00.cosgn0ao.sysido) //
          .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /*-
    *-------------------------------------------------------------------*
                          READ-USER-SEC-FILE
    *-------------------------------------------------------------------*
  */
  protected void readUserSecFile() {
    // PLI(207): CICS_LEN = STG(SEC_USER_DATA);
    ws.cicsLen.setValue(ws.csusr01y.secUserData.length());
    // PLI(208): CICS_LEN2 = STG(WS_USER_ID);
    ws.cicsLen2.setValue(ws.wsVariables.wsUserId.length());
    // PLI(209): EXEC CICS
    // PLI(209):     READ
    // PLI(209):     DATASET (WS_USRSEC_FILE)
    // PLI(209):     INTO (SEC_USER_DATA)
    // PLI(209):     LENGTH (CICS_LEN)
    // PLI(209):     RIDFLD (WS_USER_ID)
    // PLI(209):     KEYLENGTH (CICS_LEN2)
    // PLI(209):     RESP (WS_RESP_CD)
    // PLI(209):     RESP2 (WS_REAS_CD);
    supernaut
        .read(ws.wsVariables.wsUsrsecFile.getValue()) //
        .length(ws.cicsLen) //
        .resp(ws.wsVariables.wsRespCd) //
        .resp2(ws.wsVariables.wsReasCd) //
        .into(ws.csusr01y.secUserData) //
        .ridfld(ws.wsVariables.wsUserId) //
        .keylength(ws.cicsLen2) //
        .exec();
    // PLI(219): SELECT;
    // PLI(220): WHEN (WS_RESP_CD = 0) DO;
    if (ws.wsVariables.wsRespCd.equals(0)) {
      // PLI(221): IF SEC_USR_PWD = WS_USER_PWD
      // PLI(221): THEN DO;
      if (ws.csusr01y.secUserData.secUsrPwd.equals(ws.wsVariables.wsUserPwd)) {
        // PLI(223): CDEMO_FROM_TRANID = WS_TRANID;
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromTranid.setValue(
            ws.wsVariables.wsTranid);
        // PLI(224): CDEMO_FROM_PROGRAM = WS_PGMNAME;
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoFromProgram.setValue(
            ws.wsVariables.wsPgmname);
        // PLI(225): CDEMO_USER_ID = WS_USER_ID;
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoUserId.setValue(ws.wsVariables.wsUserId);
        // PLI(226): CDEMO_USER_TYPE = SEC_USR_TYPE;
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoUserType.setValue(
            ws.csusr01y.secUserData.secUsrType);
        // PLI(227): CDEMO_PGM_CONTEXT = 0;
        ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoPgmContext.setValue(0);
        // PLI(229): IF (CDEMO_USER_TYPE = CDEMO_USRTYP_ADMIN)
        // PLI(229): THEN DO;
        if (ws.cocom01y.carddemoCommarea.cdemoGeneralInfo.cdemoUserType.equals(
            ws.cocom01y.cdemoUsrtypAdmin)) {
          // PLI(231): EXEC CICS
          // PLI(231):     XCTL
          // PLI(231):     PROGRAM ('COADM01C')
          // PLI(231):     COMMAREA (CARDDEMO_COMMAREA);
          try {
            supernaut
                .xctl("COADM01C") //
                .commarea(ws.cocom01y.carddemoCommarea) //
                .exec();
          } catch (HandledConditionException e) {
            handleCommandCondition(e);
          }
          // PLI(236): ELSE DO;
        } else {
          // PLI(237): EXEC CICS
          // PLI(237):     XCTL
          // PLI(237):     PROGRAM ('COMEN01C')
          // PLI(237):     COMMAREA (CARDDEMO_COMMAREA);
          try {
            supernaut
                .xctl("COMEN01C") //
                .commarea(ws.cocom01y.carddemoCommarea) //
                .exec();
          } catch (HandledConditionException e) {
            handleCommandCondition(e);
          }
          // PLI(241): END;
        }
        // PLI(243): ELSE DO;
      } else {
        // PLI(244): WS_MESSAGE = 'Wrong Password. Try again ...';
        ws.wsVariables.wsMessage.setString("Wrong Password. Try again ...");
        // PLI(245): COSGN0AI.PASSWDL = -1;
        ws.cosgn00.cosgn0ai.passwdl.setValue(-1);
        // PLI(246): CALL SEND_SIGNON_SCREEN();
        sendSignonScreen();
        // PLI(247): END;
      }
      // PLI(249): WHEN (WS_RESP_CD = 13) DO;
    } else if (ws.wsVariables.wsRespCd.equals(13)) {
      // PLI(250): WS_ERR_FLG = 'Y';
      ws.wsVariables.wsErrFlg.setString("Y");
      // PLI(251): WS_MESSAGE = 'User not found. Try again XXX';
      ws.wsVariables.wsMessage.setString("User not found. Try again XXX");
      // PLI(252): COSGN0AI.USERIDL = -1;
      ws.cosgn00.cosgn0ai.useridl.setValue(-1);
      // PLI(253): CALL SEND_SIGNON_SCREEN();
      sendSignonScreen();
      // PLI(255): OTHERWISE DO;
    } else {
      // PLI(256): WS_ERR_FLG = 'Y';
      ws.wsVariables.wsErrFlg.setString("Y");
      // PLI(257): WS_MESSAGE = 'Unable to verify the User ...';
      ws.wsVariables.wsMessage.setString("Unable to verify the User ...");
      // PLI(258): COSGN0AI.USERIDL = -1;
      ws.cosgn00.cosgn0ai.useridl.setValue(-1);
      // PLI(259): CALL SEND_SIGNON_SCREEN();
      sendSignonScreen();
      // PLI(261): END;
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
