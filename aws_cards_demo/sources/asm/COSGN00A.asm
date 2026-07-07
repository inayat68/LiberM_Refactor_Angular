         TITLE 'AWS ASM CARDS DEMO'             
DFHEISTG DSECT                                                          
         DS    0F                      RP STORAGE RESET 
WS_VARIABLES   EQU *  
WS_PGMNAME     DC   CL8'COSGN00A'
WS_TRANID      DC   CL4'AA00'
WS_MESSAGE     DC   CL80' '
WS_USRSEC_FILE DC   CL8'USRSEC'
WS_ERR_FLG     DC   CL1'N'
WS_RESP_CD     DS   F 
WS_REAS_CD     DS   F 
WS_USER_ID     DC   CL8' '
WS_USER_PWD    DC   CL8' '
         DS    0F                      RP STORAGE RESET
*--------------------------------------
*      MAP FIELDS
*--------------------------------------
       COPY COSGN00M
LONGMAP EQU  COSGN00T-COSGN0AS
*--------------------------------------
         DS    0F                      RP STORAGE RESET 
*      COPY COCOM01Y
CD_COMMAREA     DS   0D
CD_GENE_INFO    DS   0CL34  
CD_FROM_TRAN    DS   CL4 
CD_FROM_PROG    DS   CL8 
CD_TO_TRAN      DS   CL4 
CD_TO_PROG      DS   CL8 
CD_USER_ID      DS   CL8 
CD_USER_TYPE    DS   CL1 
CD_PROG_CTX     DS   CL1 
CD_CUST_INFO    DS   CL84 
CD_ACCT_INFO    DS   CL12 
CD_CARD_INFO    DS   CL16 
CD_MORE_INFO    DS   CL14 
LCD_COMMAREA    DC   Y(*-CD_COMMAREA)           
*--------------------------------------
* RP STORAGE RESET
CCDA_SCREEN_TITLE EQU *
CCDA_TITLE01 DC   CL40'      AWS Mainframe Modernization'
CCDA_TITLE02 DC   CL40'              ASM CardDemo '
*
*--------------------------------------
         DS    0F                      RP STORAGE RESET 
WS_ABSTIME         DS   PL8    
WS_CURDATE_MMDDYY  DS   CL8
WS_CURTIME_HHMMSS  DS   CL8
*
*--------------------------------------
         DS    0F                      RP STORAGE RESET 
CCDA_COMMON_MESSAGES EQU *
MSG_THANK_YOU DC  CL50'Thank you for using CardDemo application...'
MSG_INV_KEY   DC  CL50'Invalid key pressed. Please see below...'
*
*--------------------------------------
         DS    0F                      RP STORAGE RESET 
SEC_USER_DATA   DS   0CL80        
SEC_USR_ID      DS   CL8 
SEC_USR_FNAME   DS   CL20 
SEC_USR_LNAME   DS   CL20 
SEC_USR_PWD     DS   CL8 
SEC_USR_TYPE    DS   CL1 
SEC_USR_FILLER  DS   CL23 
*--------------------------------------
*
       COPY DFHAID
       COPY DFHBMSCA

*---------------------------------------*
*        PROCEDURE DIVISION
*---------------------------------------*
MAIN_PROGRAM EQU *
COSGN00A DFHEIENT CODEREG=(R2,R3),EIBREG=R13,DATAREG=R12
*
         MVI  WS_ERR_FLG,C'N'
         MVC  WS_MESSAGE,BLANK 
         MVC  ERRMSGO,BLANK 

         CLC  EIBCALEN,=Y(0)
         BNE  MAIN00
*        XC   COSGN0AO,COSGN0AO
         LA   R6,COSGN0AO
         LA   R7,LONGMAP
         SR   R8,R8
         SR   R9,R9
         MVCL R6,R8
*
         MVC  USERIDL,=Y(-1)
         BAL  R8,SEND_SIGNON_SCREEN
         B    RETTRANS
*
MAIN00   EQU *
         CLI  EIBAID,DFHENTER   
         BNE  MAIN01
         BAL  R7,PROCESS_ENTER_KEY
         B    RETTRANS
*
MAIN01   EQU *
         CLI  EIBAID,DFHPF3
         BNE  MAIN02
         MVC  WS_MESSAGE,MSG_THANK_YOU 
         B    SEND_PLAIN_TEXT
*
MAIN02   EQU *
         MVI  WS_ERR_FLG,C'Y'
         MVC  WS_MESSAGE,MSG_INV_KEY   
         BAL  R8,SEND_SIGNON_SCREEN
*
RETTRANS EQU *
         EXEC CICS RETURN TRANSID(WS_TRANID) COMMAREA(CD_COMMAREA)     X
                    LENGTH(LCD_COMMAREA)
*
*----------------------------------------*
*           PROCESS_ENTER_KEY
*----------------------------------------*
PROCESS_ENTER_KEY  EQU *
           ST   R7,SAVER7
           EXEC CICS RECEIVE MAP('COSGN0A') MAPSET('COSGN00')          X
                             RESP(WS_RESP_CD) RESP2(WS_REAS_CD)
           OC   USERIDI,BLANK
           CLC  USERIDI,BLANK
           BNE  PROCESS_CHECK_PASS
           MVI  WS_ERR_FLG,C'Y'
           MVC  WS_MESSAGE(30),=CL30'Please enter User ID ...'
           MVC  USERIDL,=Y(-1)        
           BAL  R8,SEND_SIGNON_SCREEN
           B    PROCESS_ENTER_KEY_OK
*
PROCESS_CHECK_PASS EQU *
           OC   PASSWDI,BLANK
           CLC  PASSWDI,BLANK
           BNE  PROCESS_ENTER_KEY_OK
           MVI  WS_ERR_FLG,C'Y'
           MVC  WS_MESSAGE(30),=CL30'Please enter Password XXX ...'
           MVC  PASSWDL,=Y(-1)  
           BAL  R8,SEND_SIGNON_SCREEN
*
PROCESS_ENTER_KEY_OK EQU *
           MVC  WS_USER_ID,USERIDI
           OC   USERIDI,BLANK
           MVC  CD_USER_ID,USERIDI
           OC   USERIDI,BLANK
           MVC  WS_USER_PWD,PASSWDI
           OC   PASSWDI,BLANK
           CLI  WS_ERR_FLG,C'N'
           BNE  PROCESS_ENTER_KEY_EX
           BAL  R9,READ_USER_SEC_FILE
*
PROCESS_ENTER_KEY_EX EQU *
           L    R7,SAVER7
           BR   R7
*
*------------------------------------------*
*           SEND-SIGNON-SCREEN
*------------------------------------------*
SEND_SIGNON_SCREEN   EQU *
        ST   R8,SAVER8
        BAL  R9,POPULATE_HEADER_INFO
        MVC  ERRMSGO,WS_MESSAGE
        EXEC CICS SEND MAP('COSGN0A') MAPSET('COSGN00') ERASE CURSOR   X
                       FROM(COSGN0AO)
        L    R8,SAVER8
        BR   R8
*-------------------------------------------*
*              SEND-PLAIN-TEXT
*-------------------------------------------*
SEND_PLAIN_TEXT EQU *
           EXEC CICS SEND TEXT FROM(WS_MESSAGE) ERASE FREEKB 
           EXEC CICS RETURN
*
*------------------------------------------*
*          POPULATE-HEADER-INFO
*------------------------------------------*
POPULATE_HEADER_INFO EQU *
      EXEC CICS ASKTIME ABSTIME(WS_ABSTIME) NOHANDLE

      EXEC CICS FORMATTIME ABSTIME(WS_ABSTIME)                         X
                DATESEP('/') MMDDYY(WS_CURDATE_MMDDYY)                 X
                TIMESEP(':') TIME(WS_CURTIME_HHMMSS)

      MVC TITLE01O,CCDA_TITLE01
      MVC TITLE02O,CCDA_TITLE02
      MVC TRNNAMEO,WS_TRANID
      MVC PGMNAMEO,WS_PGMNAME
      MVC CURDATEO,WS_CURDATE_MMDDYY
      MVC CURTIMEO,WS_CURTIME_HHMMSS
      EXEC CICS ASSIGN APPLID(APPLIDO)
      EXEC CICS ASSIGN SYSID(SYSIDO)
*
      BR   R9
*
*-------------------------------------*
*          READ-USER-SEC-FILE
*-------------------------------------*
READ_USER_SEC_FILE EQU *
       ST   R9,SAVER9B
       EXEC CICS READ DATASET(WS_USRSEC_FILE)                          X
                   INTO(SEC_USER_DATA) LENGTH(L'SEC_USER_DATA)         X
                   RIDFLD(WS_USER_ID) KEYLENGTH(L'WS_USER_ID)          X
                   RESP(WS_RESP_CD) RESP2(WS_REAS_CD)

       CLC  WS_RESP_CD,=A(0)
       BNE  RESP_NO_ZERO

       CLC  SEC_USR_PWD,WS_USER_PWD
       BNE  TEST_NE
       MVC  CD_FROM_TRAN,WS_TRANID
       MVC  CD_FROM_PROG,WS_PGMNAME
       MVC  CD_USER_ID,WS_USER_ID
       MVC  CD_USER_TYPE,SEC_USR_TYPE
       MVI  CD_PROG_CTX,C'0'
       CLI  CD_USER_TYPE,C'A'
       BNE XCTL2
*
XCTL1 EQU *
         EXEC CICS XCTL PROGRAM('COADM01C') COMMAREA(CD_COMMAREA)
*
XCTL2 EQU *
         EXEC CICS XCTL PROGRAM('COMEN01C') COMMAREA(CD_COMMAREA)
*
TEST_NE  EQU *
         MVC  WS_MESSAGE(30),=CL30'Wrong Password. Try again ...'
         MVC  PASSWDL,=Y(-1)
         BAL  R8,SEND_SIGNON_SCREEN
         B    READ_USER_SEC_FILE_EX
*
RESP_NO_ZERO EQU *
         CLC  WS_RESP_CD,=A(13)
         BNE  TEST33 
         MVI  WS_ERR_FLG,C'Y'
         MVC  WS_MESSAGE(30),=CL30'User not found. Try again ...'
         MVC  USERIDL,=Y(-1) 
         BAL  R9,SEND_SIGNON_SCREEN
         B    READ_USER_SEC_FILE_EX
*
TEST33   EQU *
         MVC  WS_ERR_FLG,C'Y'
         MVC  WS_MESSAGE(30),=CL30'Unable to verify the User ...'
         MVC  USERIDL,=Y(-1) 
         BAL  R8,SEND_SIGNON_SCREEN
*
READ_USER_SEC_FILE_EX EQU *
         L    R9,SAVER9B
         BR   R9
*
         DS    0F                      RP STORAGE RESET 
*----------------------------
BLANK    DC  CL80' '
SAVER7   DS  F
SAVER8   DS  F
SAVER9   DS  F
SAVER9B  DS  F
         LTORG 
*
R0       EQU   0   
R1       EQU   1      
R2       EQU   2   
R3       EQU   3   
R4       EQU   4   
R5       EQU   5   
R6       EQU   6   
R7       EQU   7   
R8       EQU   8   
R9       EQU   9   
R10      EQU   10  
R11      EQU   11  
R12      EQU   12  
R13      EQU   13  
R14      EQU   14  
R15      EQU   15  
         END  