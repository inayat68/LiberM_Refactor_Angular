package com.aws.carddemo.online.program;

import com.aws.carddemo.online.program.storage.cosgn00a.Cosgn00aConstants;
import com.aws.carddemo.online.program.storage.cosgn00a.Cosgn00aWs;
import com.aws.carddemo.online.program.storage.cosgn00a.WsVariables;
import com.nib.asm.NAsmOnlineProgram;
import com.nib.asm.exceptions.NAsmException;
import com.nib.commons.Context;
import com.aws.carddemo.common.copybook.asm.cosgn00m.*;
import com.nib.commons.storage.*;
import com.nib.supernaut.exceptions.*;
import com.nib.supernaut.values.*;
import com.nib.asm.utils.NibWrapper;

/**
 *
 * <b>Divergeek - ASM370 to JAVA.</b> <br/>
 * <br/>
 *
 * <br/>
 *
 * @version 2.2.8
 *
 * @author divergeek
 */
public class COSGN00A extends NAsmOnlineProgram {

  /*-----------------------------------------------------------------
   *    Files declaration
   *-----------------------------------------------------------------*/

  /*-----------------------------------------------------------------
   *    Working storage declaration
   *-----------------------------------------------------------------*/
  private Cosgn00aWs ws = new Cosgn00aWs();
  private NInt32 wsRespCd = new NInt32(new Memory(4), 0).initial(0);
  private NInt32 wsReasCd = new NInt32(new Memory(4), 0).initial(0);

  /**
   *
   * @throws NAsmException if there is a specific issue
   */
  public COSGN00A(Context supernaut) throws NAsmException {
    super(supernaut);
  }

  /**
   * Assembler program COSGN00A entrypoint.
   *
   * @throws NAsmException if there is a specific issue
   */
  @Override
  protected int procedure(AbstractNField dfhcommarea) throws NAsmException {
    // ASM <Code_block_id:MAIN>
    // ASM:00001          TITLE 'MLOGICA TEST COSGN00A '
    // ASM:00002
    // ASM <Code_block_id:CCDA_COMMON_MESSAGES>
    // ASM:00064 *--------------------------------------
    // ASM:00065 *
    // ASM:00066        COPY DFHAID
    // ASM:00067        COPY DFHBMSCA
    // ASM:00069 *---------------------------------------*
    // ASM:00070 *        PROCEDURE DIVISION
    // ASM:00071 *---------------------------------------*
    // ASM:00072
    // ASM:00072 COSGN00A DFHEIENT CODEREG=(R2,R3),EIBREG=R13,DATAREG=R12
    // ASM:00073 *
    // ASM:00074          MVI  WS_ERR_FLG,C'N'
    // ASM:00075          MVC  WS_MESSAGE,BLANK
    // ASM:00076          MVC  ERRMSGO,BLANK
    // ASM:00078          CLC  EIBCALEN,=Y(0)
    // ASM:00079          BNE  MAIN00
    // ASM:00080 *        XC   COSGN0AO,COSGN0AO
    // ASM:00081          LA   R6,COSGN0AO
    // ASM:00082          LA   R7,LONGMAP
    // ASM:00083          SR   R8,R8
    // ASM:00084          SR   R9,R9
    // ASM:00085          MVCL R6,R8
    // ASM:00086 *
    // ASM:00087          MVC  USERIDL,=Y(-1)
    // ASM:00088          BAL  R8,SEND_SIGNON_SCREEN
    // ASM:00089          B    RETTRANS
    //--------------------------------------
    //
    //---------------------------------------*
    //        PROCEDURE DIVISION
    //---------------------------------------*
    //[Ignored][Line Number: "72"]   [Instruction: "DFHEIENT"]   [Tokens: "[CODEREG=(R2,R3), EIBREG=R13, DATAREG=R12]"]   <<<<
    //
    ws.getWsVariables().getWsErrFlg().set("N");
    ws.getWsVariables().getWsMessage().setByteString(ws.getBlank().getByteString(0, WsVariables.Len.WS_MESSAGE));
    ws.getCosgn00m().getCosgn0as().getErrmsgi().getErrmsgo().setByteString(ws.getBlank().getByteString(0, Errmsgi.Len.ERRMSGO));
    rettrans: {
      main00: {
        if (!dfheiblk.getEibcalen().equals(0)) {
          break main00;
        }
        //        XC   COSGN0AO,COSGN0AO
        getReg6().setAddress(ws.getCosgn00m().getCosgn0ao().getAddress());
        getReg7().set(Cosgn00aConstants.LONGMAP);
        getReg8().set(0);
        getReg9().set(0);
        getReg6().setLong(getReg8());
        //
        ws.getCosgn00m().getCosgn0as().getUseridl().set(-1);
        sendSignonScreen();
        break rettrans;
        // ASM <Code_block_id:MAIN00>
        // ASM:00090 *
        // ASM:00091 MAIN00   EQU *
        // ASM:00092          CLI  EIBAID,DFHENTER
        // ASM:00093          BNE  MAIN01
        // ASM:00094          BAL  R7,PROCESS_ENTER_KEY
        // ASM:00095          B    RETTRANS
        //
      } //main00
      main01: {
        if (!dfheiblk.getEibaid().equals(Dfhaid.DFHENTER)) {
          break main01;
        }
        processEnterKey();
        break rettrans;
        // ASM <Code_block_id:MAIN01>
        // ASM:00096 *
        // ASM:00097 MAIN01   EQU *
        // ASM:00098          CLI  EIBAID,DFHPF3
        // ASM:00099          BNE  MAIN02
        // ASM:00100          MVC  WS_MESSAGE,MSG_THANK_YOU
        // ASM:00101          B    SEND_PLAIN_TEXT
        //
      } //main01
      main02: {
        if (!dfheiblk.getEibaid().equals(Dfhaid.DFHPF3)) {
          break main02;
        }
        ws.getWsVariables().getWsMessage().setByteString(ws.getCcdaCommonMessages().getMsgThankYou().getByteString(0, WsVariables.Len.WS_MESSAGE));
        sendPlainText();
        // ASM <Code_block_id:MAIN02>
        // ASM:00102 *
        // ASM:00103 MAIN02   EQU *
        // ASM:00104          MVI  WS_ERR_FLG,C'Y'
        // ASM:00105          MVC  WS_MESSAGE,MSG_INV_KEY
        // ASM:00106          BAL  R8,SEND_SIGNON_SCREEN
        //
      } //main02
      ws.getWsVariables().getWsErrFlg().set("Y");
      ws.getWsVariables().getWsMessage().setByteString(ws.getCcdaCommonMessages().getMsgInvKey().getByteString(0, WsVariables.Len.WS_MESSAGE));
      sendSignonScreen();
      // ASM <Code_block_id:RETTRANS>
      // ASM:00107 *
      // ASM:00108 RETTRANS EQU *
      // ASM:00109          EXEC CICS RETURN TRANSID(WS_TRANID) COMMAREA(CD_COMMAREA)     X
      // ASM:00110                     LENGTH(LCD_COMMAREA)
      //
    } //rettrans
    try {
      supernaut
              .returnTransid(NibWrapper.toCharField(ws.getWsVariables().getWsTranid()))
              .commarea(NibWrapper.toCharField(ws.getCdCommarea()))
              .length(ws.getLcdCommarea().getInt())
              .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    return 0;
  }

  /**
   * Method PROCESS_ENTER_KEY.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void processEnterKey() throws NAsmException {
    // ASM <Code_block_id:PROCESS_ENTER_KEY>
    // ASM:00111 *
    // ASM:00112 *----------------------------------------*
    // ASM:00113 *           PROCESS_ENTER_KEY
    // ASM:00114 *----------------------------------------*
    // ASM:00115 PROCESS_ENTER_KEY  EQU *
    // ASM:00116            ST   R7,SAVER7
    // ASM:00117            EXEC CICS RECEIVE MAP('COSGN0A') MAPSET('COSGN00')          X
    // ASM:00118                              RESP(WS_RESP_CD) RESP2(WS_REAS_CD)
    // ASM:00119            OC   USERIDI,BLANK
    // ASM:00120            CLC  USERIDI,BLANK
    // ASM:00121            BNE  PROCESS_CHECK_PASS
    // ASM:00122            MVI  WS_ERR_FLG,C'Y'
    // ASM:00123            MVC  WS_MESSAGE(30),=CL30'Please enter User ID ...'
    // ASM:00124            MVC  USERIDL,=Y(-1)
    // ASM:00125            BAL  R8,SEND_SIGNON_SCREEN
    // ASM:00126            B    PROCESS_ENTER_KEY_OK
    //
    //----------------------------------------*
    //           PROCESS_ENTER_KEY
    //----------------------------------------*
    ws.getSaver7().set(getReg7());
    supernaut
            .receiveMap("COSGN0A")
            .mapset("COSGN00")
            .into(NibWrapper.toCharField(ws.getCosgn00m().getCosgn0ai()))
            .resp(wsRespCd)
            .resp2(wsReasCd)
            .exec();
    ws.getCosgn00m().getCosgn0as().getUseridi().orAt(0, ws.getBlank().getByteArray(0, 8));
    processEnterKeyOk: {
      processCheckPass: {
        if (!ws.getCosgn00m().getCosgn0as().getUseridi().getByteString().equals(ws.getBlank().getByteString(0, 8))) {
          break processCheckPass;
        }
        ws.getWsVariables().getWsErrFlg().set("Y");
        ws.getWsVariables().getWsMessage().set("Please enter User ID ...      ");
        ws.getCosgn00m().getCosgn0as().getUseridl().set(-1);
        sendSignonScreen();
        break processEnterKeyOk;
        // ASM <Code_block_id:PROCESS_CHECK_PASS>
        // ASM:00127 *
        // ASM:00128 PROCESS_CHECK_PASS EQU *
        // ASM:00129            OC   PASSWDI,BLANK
        // ASM:00130            CLC  PASSWDI,BLANK
        // ASM:00131            BNE  PROCESS_ENTER_KEY_OK
        // ASM:00132            MVI  WS_ERR_FLG,C'Y'
        // ASM:00133            MVC  WS_MESSAGE(30),=CL30'Please enter Password ...'
        // ASM:00134            MVC  PASSWDL,=Y(-1)
        // ASM:00135            BAL  R8,SEND_SIGNON_SCREEN
        //
      } //processCheckPass
      ws.getCosgn00m().getCosgn0as().getPasswdi().orAt(0, ws.getBlank().getByteArray(0, 8));
      if (!ws.getCosgn00m().getCosgn0as().getPasswdi().getByteString().equals(ws.getBlank().getByteString(0, 8))) {
        break processEnterKeyOk;
      }
      ws.getWsVariables().getWsErrFlg().set("Y");
      ws.getWsVariables().getWsMessage().set("Please enter Password ...     ");
      ws.getCosgn00m().getCosgn0as().getPasswdl().set(-1);
      sendSignonScreen();
      // ASM <Code_block_id:PROCESS_ENTER_KEY_OK>
      // ASM:00136 *
      // ASM:00137 PROCESS_ENTER_KEY_OK EQU *
      // ASM:00138            MVC  WS_USER_ID,USERIDI
      // ASM:00139            OC   USERIDI,BLANK
      // ASM:00140            MVC  CD_USER_ID,USERIDI
      // ASM:00141            OC   USERIDI,BLANK
      // ASM:00142            MVC  WS_USER_PWD,PASSWDI
      // ASM:00143            OC   PASSWDI,BLANK
      // ASM:00144            CLI  WS_ERR_FLG,C'N'
      // ASM:00145            BNE  PROCESS_ENTER_KEY_EX
      // ASM:00146            BAL  R9,READ_USER_SEC_FILE
      //
    } //processEnterKeyOk
    ws.getWsVariables().getWsUserId().setByteString(ws.getCosgn00m().getCosgn0as().getUseridi().getByteString().toUpperCase());
    ws.getCdGeneInfo().getCdUserId().setByteString(ws.getCosgn00m().getCosgn0as().getUseridi().getByteString().toUpperCase());
    ws.getWsVariables().getWsUserPwd().setByteString(ws.getCosgn00m().getCosgn0as().getPasswdi().getByteString().toUpperCase());
    processEnterKeyEx: {
      if (!ws.getWsVariables().getWsErrFlg().equals("N")) {
        break processEnterKeyEx;
      }
      readUserSecFile();
      // ASM <Code_block_id:PROCESS_ENTER_KEY_EX>
      // ASM:00147 *
      // ASM:00148 PROCESS_ENTER_KEY_EX EQU *
      // ASM:00149            L    R7,SAVER7
      // ASM:00150            BR   R7
      //
    } //processEnterKeyEx
    getReg7().load(ws.getSaver7());
    return;
  }

  /**
   * Method SEND_SIGNON_SCREEN.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void sendSignonScreen() throws NAsmException {
    // ASM <Code_block_id:SEND_SIGNON_SCREEN>
    // ASM:00151 *
    // ASM:00152 *------------------------------------------*
    // ASM:00153 *           SEND-SIGNON-SCREEN
    // ASM:00154 *------------------------------------------*
    // ASM:00155 SEND_SIGNON_SCREEN   EQU *
    // ASM:00156         ST   R8,SAVER8
    // ASM:00157         BAL  R9,POPULATE_HEADER_INFO
    // ASM:00158         MVC  ERRMSGO,WS_MESSAGE
    // ASM:00159         EXEC CICS SEND MAP('COSGN0A') MAPSET('COSGN00') ERASE CURSOR   X
    // ASM:00160                        FROM(COSGN0AO)
    // ASM:00161         L    R8,SAVER8
    // ASM:00162         BR   R8
    //
    //------------------------------------------*
    //           SEND-SIGNON-SCREEN
    //------------------------------------------*
    ws.getSaver8().set(getReg8());
    populateHeaderInfo();
    ws.getCosgn00m().getCosgn0as().getErrmsgi().getErrmsgo().setByteString(ws.getWsVariables().getWsMessage().getByteString(0, Errmsgi.Len.ERRMSGO));
    try {
      supernaut
              .sendMap("COSGN0A")
              .mapset("COSGN00")
              .from(NibWrapper.toCharField(ws.getCosgn00m().getCosgn0ao()))
              .erase()
              .cursor()
              .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    getReg8().load(ws.getSaver8());
    return;
  }

  /**
   * Method SEND_PLAIN_TEXT.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void sendPlainText() throws NAsmException {
    // ASM <Code_block_id:SEND_PLAIN_TEXT>
    // ASM:00163 *-------------------------------------------*
    // ASM:00164 *              SEND-PLAIN-TEXT
    // ASM:00165 *-------------------------------------------*
    // ASM:00166 SEND_PLAIN_TEXT EQU *
    // ASM:00168            EXEC CICS SEND TEXT FROM(WS_MESSAGE) ERASE FREEKB
    // ASM:00169            EXEC CICS RETURN
    //-------------------------------------------*
    //              SEND-PLAIN-TEXT
    //-------------------------------------------*
    try {
      supernaut
              .sendText()
              .from(NibWrapper.toCharField(ws.getWsVariables().getWsMessage()))
              .erase()
              .freekb()
              .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    try {
      supernaut.returnToCaller();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
  }

  /**
   * Method POPULATE_HEADER_INFO.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void populateHeaderInfo() throws NAsmException {
    // ASM <Code_block_id:POPULATE_HEADER_INFO>
    // ASM:00169 *
    // ASM:00170 *------------------------------------------*
    // ASM:00171 *          POPULATE-HEADER-INFO
    // ASM:00172 *------------------------------------------*
    // ASM:00173 POPULATE_HEADER_INFO EQU *
    // ASM:00176       EXEC CICS ASKTIME ABSTIME(WS_ABSTIME) NOHANDLE
    // ASM:00176       EXEC CICS FORMATTIME ABSTIME(WS_ABSTIME)                         X
    // ASM:00177                 DATESEP('/') MMDDYY(WS_CURDATE_MMDDYY)                 X
    // ASM:00178                 TIMESEP(':') TIME(WS_CURTIME_HHMMSS)
    // ASM:00180       MVC TITLE01O,CCDA_TITLE01
    // ASM:00181       MVC TITLE02O,CCDA_TITLE02
    // ASM:00182       MVC TRNNAMEO,WS_TRANID
    // ASM:00183       MVC PGMNAMEO,WS_PGMNAME
    // ASM:00184       MVC CURDATEO,WS_CURDATE_MMDDYY
    // ASM:00185       MVC CURTIMEO,WS_CURTIME_HHMMSS
    // ASM:00187       EXEC CICS ASSIGN APPLID(APPLIDO)
    // ASM:00188       EXEC CICS ASSIGN SYSID(SYSIDO)
    // ASM:00188 *
    // ASM:00189       BR   R9
    //
    //------------------------------------------*
    //          POPULATE-HEADER-INFO
    //------------------------------------------*
    NPacked wsAbstime = new NPacked(new Memory(8), 0, 8, 0, true);
    supernaut.
            askTime()
            .abstime(wsAbstime)
            .nohandle()
            .exec();
    supernaut
            .formatTime(wsAbstime)
            .datesep("/")
            .mmddyy(NibWrapper.toCharField(ws.getWsCurdateMmddyy()))
            .timesep(":")
            .time(NibWrapper.toCharField(ws.getWsCurtimeHhmmss()))
            .exec();
    ws.getCosgn00m().getCosgn0as().getTitle01i().getTitle01o().setByteString(ws.getCcdaScreenTitle().getCcdaTitle01().getByteString());
    ws.getCosgn00m().getCosgn0as().getTitle02i().getTitle02o().setByteString(ws.getCcdaScreenTitle().getCcdaTitle02().getByteString());
    ws.getCosgn00m().getCosgn0as().getTrnnamei().getTrnnameo().setByteString(ws.getWsVariables().getWsTranid().getByteString());
    ws.getCosgn00m().getCosgn0as().getPgmnamei().getPgmnameo().setByteString(ws.getWsVariables().getWsPgmname().getByteString());
    ws.getCosgn00m().getCosgn0as().getCurdatei().getCurdateo().setByteString(ws.getWsCurdateMmddyy().getByteString());
    ws.getCosgn00m().getCosgn0as().getCurtimei().getCurtimeo().setByteString(ws.getWsCurtimeHhmmss().getByteString(0, Curtimei.Len.CURTIMEO));
    try {
      supernaut
            .assign()
            .applid(NibWrapper.toCharField(ws.getCosgn00m().getCosgn0as().getApplidi().getApplido()))
            .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    try {
      supernaut
              .assign()
              .sysid(NibWrapper.toCharField(ws.getCosgn00m().getCosgn0as().getSysidi().getSysido()))
              .exec();
    } catch (HandledConditionException e) {
      handleCommandCondition(e);
    }
    //
    return;
  }

  /**
   * Method READ_USER_SEC_FILE.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void readUserSecFile() throws NAsmException {
    // ASM <Code_block_id:READ_USER_SEC_FILE>
    // ASM:00190 *
    // ASM:00191 *-------------------------------------*
    // ASM:00192 *          READ-USER-SEC-FILE
    // ASM:00193 *-------------------------------------*
    // ASM:00194 READ_USER_SEC_FILE EQU *
    // ASM:00195        ST   R9,SAVER9B
    // ASM:00196        EXEC CICS READ DATASET(WS_USRSEC_FILE)                          X
    // ASM:00197                    INTO(SEC_USER_DATA) LENGTH(L'SEC_USER_DATA)         X
    // ASM:00198                    RIDFLD(WS_USER_ID) KEYLENGTH(L'WS_USER_ID)          X
    // ASM:00199                    RESP(WS_RESP_CD) RESP2(WS_REAS_CD)
    // ASM:00201        CLC  WS_RESP_CD,=A(0)
    // ASM:00202        BNE  RESP_NO_ZERO
    // ASM:00204        CLC  SEC_USR_PWD,WS_USER_PWD
    // ASM:00205        BNE  TEST_NE
    // ASM:00206        MVC  CD_FROM_TRAN,WS_TRANID
    // ASM:00207        MVC  CD_FROM_PROG,WS_PGMNAME
    // ASM:00208        MVC  CD_USER_ID,WS_USER_ID
    // ASM:00209        MVC  CD_USER_TYPE,SEC_USR_TYPE
    // ASM:00210        MVI  CD_PROG_CTX,C'0'
    // ASM:00211        CLI  CD_USER_TYPE,C'A'
    // ASM:00212        BNE XCTL2
    //
    //-------------------------------------*
    //          READ-USER-SEC-FILE
    //-------------------------------------*
    ws.getSaver9b().set(getReg9());
    NChar secUserData = new NChar(new Memory(ws.getSecUserData().getLength()), 0, ws.getSecUserData().getLength());
    supernaut
            .read(NibWrapper.toCharField(ws.getWsVariables().getWsUsrsecFile()).toString())
            .into(secUserData)
            .length(ws.getSecUserData().getLength())
            .ridfld(NibWrapper.toCharField(ws.getWsVariables().getWsUserId()))
            .keylength(ws.getWsVariables().getWsUserId().getLength())
            .resp(wsRespCd)
            .resp2(wsReasCd)
            .exec();
    readUserSecFileEx: {
      respNoZero: {
        if (!wsRespCd.equals(0)) {
          break respNoZero;
        }
        testNe: {
          ws.getSecUserData().setByteString(secUserData.getValue());
          if (!ws.getSecUserData().getSecUsrPwd().getByteString().equals(ws.getWsVariables().getWsUserPwd().getByteString())) {
            break testNe;
          }
          ws.getCdGeneInfo().getCdFromTran().setByteString(ws.getWsVariables().getWsTranid().getByteString());
          ws.getCdGeneInfo().getCdFromProg().setByteString(ws.getWsVariables().getWsPgmname().getByteString());
          ws.getCdGeneInfo().getCdUserId().setByteString(ws.getWsVariables().getWsUserId().getByteString());
          ws.getCdGeneInfo().getCdUserType().setByteString(ws.getSecUserData().getSecUsrType().getByteString());
          ws.getCdGeneInfo().getCdProgCtx().set("0");
          xctl2: {
            if (!ws.getCdGeneInfo().getCdUserType().equals("A")) {
              break xctl2;
            }
            // ASM <Code_block_id:XCTL1>
            // ASM:00213 *
            // ASM:00214 XCTL1 EQU *
            //
            // ASM <Code_block_id:XCTL2>
            // ASM:00216          EXEC CICS XCTL PROGRAM('COADM01C') COMMAREA(CD_COMMAREA)
            // ASM:00216 *
            // ASM:00217 XCTL2 EQU *
            try {
              supernaut
                    .xctl("COADM01C")
                    .commarea(NibWrapper.toCharField(ws.getCdCommarea()))
                    .exec();
            } catch (HandledConditionException e) {
              handleCommandCondition(e);
            }
            //
          } //xctl2
          // ASM <Code_block_id:TEST_NE>
          // ASM:00219          EXEC CICS XCTL PROGRAM('COMEN01C') COMMAREA(CD_COMMAREA)
          // ASM:00219 *
          // ASM:00220 TEST_NE  EQU *
          // ASM:00221          MVC  WS_MESSAGE(30),=CL30'Wrong Password. Try again ...'
          // ASM:00222          MVC  PASSWDL,=Y(-1)
          // ASM:00223          BAL  R8,SEND_SIGNON_SCREEN
          // ASM:00224          B    READ_USER_SEC_FILE_EX
          try {
            supernaut
                    .xctl("COMEN01C")
                    .commarea(NibWrapper.toCharField(ws.getCdCommarea()))
                    .exec();
          //
          } catch (HandledConditionException e) {
            handleCommandCondition(e);
          }
        } //testNe
        ws.getWsVariables().getWsMessage().set("Wrong Password. Try again ... ");
        ws.getCosgn00m().getCosgn0as().getPasswdl().set(-1);
        sendSignonScreen();
        break readUserSecFileEx;
        // ASM <Code_block_id:RESP_NO_ZERO>
        // ASM:00225 *
        // ASM:00226 RESP_NO_ZERO EQU *
        // ASM:00227          CLC  WS_RESP_CD,=A(13)
        // ASM:00228          BNE  TEST33
        // ASM:00229          MVI  WS_ERR_FLG,C'Y'
        // ASM:00230          MVC  WS_MESSAGE(30),=CL30'User not found. Try again ...'
        // ASM:00231          MVC  USERIDL,=Y(-1)
        // ASM:00232          BAL  R9,SEND_SIGNON_SCREEN
        // ASM:00233          B    READ_USER_SEC_FILE_EX
        //
      } //respNoZero
      test33: {
        if (!wsRespCd.equals(13)) {
          break test33;
        }
        ws.getWsVariables().getWsErrFlg().set("Y");
        ws.getWsVariables().getWsMessage().set("User not found. Try again ... ");
        ws.getCosgn00m().getCosgn0as().getUseridl().set(-1);
        sendSignonScreen();
        break readUserSecFileEx;
        // ASM <Code_block_id:TEST33>
        // ASM:00234 *
        // ASM:00235 TEST33   EQU *
        // ASM:00236          MVC  WS_ERR_FLG,C'Y'
        // ASM:00237          MVC  WS_MESSAGE(30),=CL30'Unable to verify the User ...'
        // ASM:00238          MVC  USERIDL,=Y(-1)
        // ASM:00239          BAL  R8,SEND_SIGNON_SCREEN
        //
      } //test33
      ws.getWsVariables().getWsErrFlg().set("Y");
      ws.getWsVariables().getWsMessage().set("Unable to verify the User ... ");
      ws.getCosgn00m().getCosgn0as().getUseridl().set(-1);
      sendSignonScreen();
      // ASM <Code_block_id:READ_USER_SEC_FILE_EX>
      // ASM:00240 *
      // ASM:00241 READ_USER_SEC_FILE_EX EQU *
      // ASM:00242          L    R9,SAVER9B
      // ASM:00243          BR   R9
      //
    } //readUserSecFileEx
    getReg9().load(ws.getSaver9b());
    return;
    // ASM <Code_block_id:READ_USER_SEC_FILE>
    // ASM:00270          END
  }

  private enum HandleLabel {
    Not_Defined(0);

    private Integer value = null;

    HandleLabel(Integer value) {
      this.value = value;
    }

    public int getValue() {
      return this.value;
    }

    public static COSGN00A.HandleLabel get(Integer handleId) {
      for (COSGN00A.HandleLabel hndLbl : COSGN00A.HandleLabel.values()) {
        if (hndLbl.getValue() == handleId) {
          return hndLbl;
        }
      }
      return Not_Defined;
    }
  }

  protected void handleCommandCondition(HandledConditionException e) {
    switch (COSGN00A.HandleLabel.get(e.getHandleId())) {
      default:
        throw new RuntimeException(e);
    }
  }

}