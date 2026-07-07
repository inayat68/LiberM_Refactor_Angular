      ******************************************************************
      * Author : mLogica
      * Product: IRIS-DB - v. 5.5.0
      ******************************************************************
      *                                                                *
      *   DESCRIPTION: IRIS GLOBAL AREA                                *
      *                                                                *
      ******************************************************************
      *
       01 IRIS-WORK-AREA.
      *
      * CALLER PROGRAM
      *
         03 IRIS-PROGRAM-NAME             PIC X(8)  VALUE :PROGNM:.
      *
      * UNIQUE IDENTIFIER OF THE DLI/DB2 STATEMENT
      *
         03  IRIS-SEGMENT                 PIC X(8).
DC       03  IRIS-MSGNAME  REDEFINES IRIS-SEGMENT
                                          PIC X(8).
DC       03  IRIS-MODNAME  REDEFINES IRIS-SEGMENT
                                          PIC X(8).
         03  IRIS-CALL-ID                 PIC 9(4).
         03  IRIS-CUSTOM-FUNC-ID          PIC 9(8).
         03  IRIS-PSB-NAME                PIC X(8).
      *
      * FUNCTION TO BE EXECUTED
      *
         03  IRIS-FUNCTION                PIC X(20).
      * SQL FUNCTIONS
           88 SQL-SELECT-PRIMARY          VALUE 'SQL-SELECT-PRIMARY  '.
           88 SQL-SELECT-PRIMARY-GE       VALUE 'SQL-SELECT-PRIMARYGE'.
           88 SQL-SELECT-PRIMARY-GT       VALUE 'SQL-SELECT-PRIMARYGT'.
           88 SQL-SELECT-PRIMARY-LE       VALUE 'SQL-SELECT-PRIMARYLE'.
           88 SQL-SELECT-PRIMARY-LT       VALUE 'SQL-SELECT-PRIMARYLT'.
           88 SQL-SELECT-PRIMARY-NE       VALUE 'SQL-SELECT-PRIMARYNE'.
           88 SQL-SELECT-DYNAMIC          VALUE 'SQL-SELECT-DYNAMIC  '.
           88 SQL-SELECT-STATIC           VALUE 'SQL-SELECT-STATIC   '.
           88 SQL-SELECT-SEEK             VALUE 'SQL-SELECT-SEEK     '.
           88 SQL-SELECT-NEXT             VALUE 'SQL-SELECT-NEXT     '.
           88 SQL-SELECT-PATH             VALUE 'SQL-SELECT-PATH     '.
           88 SQL-UPDATE                  VALUE 'SQL-UPDATE          '.
           88 SQL-DELETE                  VALUE 'SQL-DELETE          '.
           88 SQL-INSERT                  VALUE 'SQL-INSERT          '.
           88 SQL-COMMIT                  VALUE 'SQL-COMMIT          '.
           88 SQL-ROLLBACK                VALUE 'SQL-ROLLBACK        '.
           88 SQL-SELECT-ALL-UNQ          VALUE 'SQL-SELECT-ALL-UNQ  '.
           88 SQL-SELECT-ALL-SEEK         VALUE 'SQL-SELECT-ALL-SEEK '.
           88 SQL-SELECT-ALL-NEXT         VALUE 'SQL-SELECT-ALL-NEXT '.
           88 SQL-USER-CUSTOM             VALUE 'SQL-USER-CUSTOM     '.
           88 SQL-DYNAMIC-SSA             VALUE 'SQL-DYNAMIC-SSA     '.
      * GSAM FUNCTIONS
           88 GSAM-GET-NEXT               VALUE 'GSAM-GET-NEXT       '.
           88 GSAM-INSERT                 VALUE 'GSAM-INSERT         '.
           88 GSAM-CLOSE                  VALUE 'GSAM-CLOSE          '.
           88 GSAM-OPEN-INPUT             VALUE 'GSAM-OPEN-INPUT     '.
           88 GSAM-OPEN-OUTPUT            VALUE 'GSAM-OPEN-OUTPUT    '.
      * SYSTEM FUNCTIONS
           88 CHKP-CHKP-BASIC             VALUE 'CHKP-CHKP-BASIC     '.
           88 CHKP-CHKP-SYMBOLIC          VALUE 'CHKP-CHKP-BASIC     '.
           88 CHKP-X-RESTART              VALUE 'CHKP-X-RESTART      '.
      * INTERNAL FUNCTIONS
           88 LOAD-DATA-DICTIONARY-SEG    VALUE 'LOAD-DICTIONARY-SEG '.
           88 LOAD-DATA-DICTIONARY-FLD    VALUE 'LOAD-DICTIONARY-FLD '.
      *
      * IMS FUNCTIONS
      *
         03 IRIS-IMS-FUNCTION             PIC X(4).
           88 IRIS-FUNC-GU                VALUE 'GU  '.
           88 IRIS-FUNC-GHU               VALUE 'GHU '.
           88 IRIS-FUNC-GN                VALUE 'GN  '.
           88 IRIS-FUNC-GHN               VALUE 'GHN '.
           88 IRIS-FUNC-GNP               VALUE 'GNP '.
           88 IRIS-FUNC-GHNP              VALUE 'GHNP'.
           88 IRIS-FUNC-ISRT              VALUE 'ISRT'.
           88 IRIS-FUNC-REPL              VALUE 'REPL'.
           88 IRIS-FUNC-DLET              VALUE 'DLET'.
           88 IRIS-FUNC-TERM              VALUE 'TERM'.
           88 IRIS-FUNC-CHKP              VALUE 'CHKP'.
           88 IRIS-FUNC-SYNC              VALUE 'SYNC'.
           88 IRIS-FUNC-SYMC              VALUE 'SYMC'.
           88 IRIS-FUNC-XRST              VALUE 'XRST'.
           88 IRIS-FUNC-DLCK              VALUE 'DLCK'.
           88 IRIS-FUNC-ROLL              VALUE 'ROLL'.
           88 IRIS-FUNC-ROLB              VALUE 'ROLB'.
           88 IRIS-FUNC-ROLS              VALUE 'ROLS'.
           88 IRIS-FUNC-CMD               VALUE 'CMD '.
           88 IRIS-FUNC-GCMD              VALUE 'GCMD'.
           88 IRIS-FUNC-INIT              VALUE 'INIT'.
           88 IRIS-FUNC-SCHD              VALUE 'SCHD'.
           88 IRIS-FUNC-SCHB              VALUE 'SCHB'.
           88 IRIS-FUNC-PCB               VALUE 'PCB '.
           88 IRIS-FUNC-DLIT              VALUE 'DLIT'.
           88 IRIS-FUNC-OPEN              VALUE 'OPEN'.
           88 IRIS-FUNC-CLSE              VALUE 'CLSE'.
           88 IRIS-FUNC-APSB              VALUE 'APSB'.
           88 IRIS-FUNC-DPSB              VALUE 'DPSB'.
DC         88 IRIS-FUNC-PURG              VALUE 'PURG'.
DC         88 IRIS-FUNC-CHNG              VALUE 'CHNG'.
DC         88 IRIS-FUNC-ENTR              VALUE 'ENTR'.
DC         88 IRIS-FUNC-GOBA              VALUE 'GOBA'.
           88 IRIS-FUNC-INQY              VALUE 'INQY'.
           88 IRIS-FUNC-POS               VALUE 'POS '.
           88 IRIS-FUNC-SETS              VALUE 'SETS'.
      * TSQ FUNCTIONS
           88 IRIS-FUNC-READQ             VALUE 'REAQ'.
           88 IRIS-FUNC-WRITEQ            VALUE 'WRIQ'.
           88 IRIS-FUNC-DELETEQ           VALUE 'DELQ'.

      *
      * NUMBER OF PARMS AND SSAS
      *
         03  IRIS-PARAM-NUM               PIC S9(9) COMP.
         03  IRIS-SSAS-NUM                PIC S9(9) COMP.
DC       03  IRIS-TASK-NUM REDEFINES IRIS-SSAS-NUM
DC                                        PIC S9(9) COMP.
INQY     03  IRIS-USED-PCB REDEFINES IRIS-SSAS-NUM
INQY                                      PIC S9(9) COMP.
      *
      * TRACE LEVEL
      *
         03 IRIS-TRACE-LEVEL              PIC X.
           88 IRIS-LEVEL-IS-VALID                         VALUE SPACE
                                                            LOW-VALUE
                                                                '0' '1'
                                                                '2' '3'
                                                                '4' '5'.
      * NO MESSAGE TRACE AT ALL
           88 IRIS-TRACE-NONE                             VALUE SPACE
                                                                '0'.
      * EMIT ONLY SEVERE ERRORS AND WARNING MESSAGES
           88 IRIS-TRACE-STANDARD                         VALUE '1' '2'
                                                                    '3'.
      * EMIT STATISTICS AND ADDITIONAL INFORMATION
           88 IRIS-TRACE-FULL                             VALUE '2' '3'.
      * EMIT DETAILED TRACE
           88 IRIS-TRACE-DEBUG                            VALUE '3'.
      * EMIT PERFORMANCE TRACE
           88 IRIS-TRACE-PERFORMANCE                      VALUE '4'.
      * EMIT MINIMAL TRACE
           88 IRIS-TRACE-MINIMAL                          VALUE '5'.
      *
      * MESSAGE DETAIL
      *
         03 IRIS-MSG-DETAIL.
           05 IRIS-MSG-LEN                          PIC S9(4) COMP.
           05 IRIS-MSG-TXT                          PIC X(4096).
      * MESSAGE LEVEL: ONE OF THE VALUES OF TABLE "COSTANTS"
         03 IRIS-MSG-LEVEL                          PIC 99.
           88 IRIS-MSG-LEVEL-INFO                   VALUE 00.
           88 IRIS-MSG-LEVEL-WARNING                VALUE 04.
           88 IRIS-MSG-LEVEL-ERROR                  VALUE 12.
           88 IRIS-MSG-LEVEL-DEBUG                  VALUE 99.
      *
      * IRIS ERRORS DESCRIPTION
      *
         03 IRIS-ERROR-DESCR.
<0001>     05 FILLER PIC X(30) VALUE '(IO-RTN) FUNCTION NOT HANDLED_'.
<0002>     05 FILLER PIC X(30) VALUE '(IO-RTN) SEGMENT NOT FOUND____'.
<0003>     05 FILLER PIC X(30) VALUE '(IO-RTN) HANDLED SQL ERROR____'.
<0004>     05 FILLER PIC X(30) VALUE '(IO-RTN) UNHANDLED SQL ERROR__'.
<0005>     05 FILLER PIC X(30) VALUE '(IO-RTN) FUNCTION NOT FOUND___'.
<0006>     05 FILLER PIC X(30) VALUE '(IO-RTN) WRONG BUFFER LENGTH__'.
<0007>     05 FILLER PIC X(30) VALUE '(IO-RTN) WRONG ALTERNATE INDEX'.
<0008>     05 FILLER PIC X(30) VALUE '(IO-RTN) ACCESS NOT HANDLED___'.
<0009>     05 FILLER PIC X(30) VALUE '(IO-RTN) DYNAMIC SSA FAILURE__'.
           05 FILLER PIC X(30) OCCURS 11.
<0021>     05 FILLER PIC X(30) VALUE '(DYNSSA) UNSUPPORTED FUNCTION_'.
<0022>     05 FILLER PIC X(30) VALUE '(DYNSSA) WRONG SENSEG_________'.
<0023>     05 FILLER PIC X(30) VALUE '(DYNSSA) WRONG SENSEG POINTER_'.
<0024>     05 FILLER PIC X(30) VALUE '(DYNSSA) SEGMENT NOT FOUND____'.
<0025>     05 FILLER PIC X(30) VALUE '(DYNSSA) FIELD NOT FOUND______'.
<0026>     05 FILLER PIC X(30) VALUE '(DYNSSA) WRONG BOOL OPERATOR__'.
<0027>     05 FILLER PIC X(30) VALUE '(DYNSSA) ACCESS NOT HANDLED___'.
<0028>     05 FILLER PIC X(30) VALUE '(DYNSSA) CCODE C NOT SUPPORTED'.
<0029>     05 FILLER PIC X(30) VALUE '(DYNSSA) PSB NOT FOUND________'.
           05 FILLER PIC X(30) OCCURS 11.
           05 FILLER PIC X(30) OCCURS 59.
         03 FILLER REDEFINES IRIS-ERROR-DESCR.
           05 IRIS-ERROR-DESCRIPTION PIC X(30) OCCURS 99.
      *
      * IRIS ERRORS TABLE
      *
         03 IRIS-ERR-MESSAGE-ID                     PIC S9(4) COMP.
           88 IRIS-NO-ERROR                         VALUE 0 2020 4040.
      *
      *    I/O ROUTINE ERRORS
      *
           88 IRIS-ERR-IMSFUNC-NOT-FOUND            VALUE 1.
           88 IRIS-ERR-RTN-SEGMENT-NOT-FOUND        VALUE 2.
           88 IRIS-ERR-HANDLED-SQLCODE              VALUE 3.
           88 IRIS-ERR-UNHANDLED-SQLCODE            VALUE 4.
           88 IRIS-ERR-FUNCTION-NOT-FOUND           VALUE 5.
           88 IRIS-ERR-WRONG-BUFFER-LEN             VALUE 6.
           88 IRIS-ERR-WRONG-ALT-INDEX              VALUE 7.
           88 IRIS-ERR-RTN-UNHANDLED-ACCESS         VALUE 8.
           88 IRIS-ERR-DYNAMIC-SSA-FAILURE          VALUE 9.
      *
      *    I/O DYNAMIC SSA ERRORS
      *
           88 IRIS-ERR-UNSUPPORTED-FUNC             VALUE 21.
           88 IRIS-ERR-WRONG-SENSEG                 VALUE 22.
           88 IRIS-ERR-WRONG-SENSEG-PTR             VALUE 23.
           88 IRIS-ERR-SSA-SEGMENT-NOT-FOUND        VALUE 24.
           88 IRIS-ERR-FIELD-NOT-FOUND              VALUE 25.
           88 IRIS-ERR-WRONG-BOOLEAN-OP             VALUE 26.
           88 IRIS-ERR-SSA-UNHANDLED-ACCESS         VALUE 27.
           88 IRIS-ERR-UNSUPPORTED-C-COMMAND        VALUE 28.
           88 IRIS-ERR-PSB-NOT-FOUND                VALUE 29.
      *
      * IRIS IO/ROUTINE SQL ERROR
      *
         03 IRIS-SQL-ERROR.
           05  IRIS-SQLCODE                         PIC S9(9) COMP.
           05  IRIS-SQLERRM.
               07 IRIS-SQLERRML                     PIC S9(4) COMP-5.
               07 IRIS-SQLERRMC                     PIC X(70).
      *
      * IMS SEGMENTS SPECIAL LEVELS
      *
         03 IRIS-IMS-SEGM-LVL                       PIC 99.
           88 IRIS-IMS-SEGM-LVL-DUMMY                     VALUE 00.
           88 IRIS-IMS-SEGM-LVL-DATABASE                  VALUE 1 .
           88 IRIS-IMS-SEGM-LVL-GSAM                      VALUE 55.
           88 IRIS-IMS-SEGM-LVL-VIDEO                     VALUE 98.
           88 IRIS-IMS-SEGM-LVL-PRINTER                   VALUE 99.
      *
      *    CURRENT STATUS OF IRISUTIL: INTERMEDIATE/FINAL
      *
         03 IRIS-CURRENT-STATUS                     PIC 9 VALUE 0.
           88 IRIS-INTERMEDIATE                           VALUE 0.
           88 IRIS-FINAL                                  VALUE 1.

      *
      * I\O ROUTINES AREAS
      *
         03 IRIS-KEYVALUE-TAB.
           05 IRIS-CCODE-LEVELS           OCCURS 16.
             07 IRIS-CCODE-VALUES.
              09 IRIS-CODE-A              PIC X.
                88 IRIS-CODE-POS-RESET                    VALUE 'A'.
              09 IRIS-CODE-C              PIC X.
                88 IRIS-CODE-CONCAT-KEY                   VALUE 'C'.
              09 IRIS-CODE-D              PIC X.
                88 IRIS-CODE-PATHCALL                     VALUE 'D'.
              09 IRIS-CODE-F              PIC X.
                88 IRIS-CODE-FIRST                        VALUE 'F'.
              09 IRIS-CODE-L              PIC X.
                88 IRIS-CODE-LAST                         VALUE 'L'.
              09 IRIS-CODE-M              PIC X.
                88 IRIS-CODE-PTRNEXT                      VALUE 'M'.
              09 IRIS-CODE-N              PIC X.
                88 IRIS-CODE-NOREPL                       VALUE 'N'.
              09 IRIS-CODE-P              PIC X.
                88 IRIS-CODE-PARENTAGE                    VALUE 'P'.
              09 IRIS-CODE-Q              PIC X.
                88 IRIS-CODE-HOLDSEG                      VALUE 'Q'.
              09 IRIS-CODE-R              PIC X.
                88 IRIS-CODE-FIRSTSEG                     VALUE 'R'.
              09 IRIS-CODE-S              PIC X.
                88 IRIS-CODE-PTRCURR                      VALUE 'S'.
              09 IRIS-CODE-U              PIC X.
                88 IRIS-CODE-LIMSRCH                      VALUE 'U'.
              09 IRIS-CODE-V              PIC X.
                88 IRIS-CODE-HIERQUAL                     VALUE 'V'.
              09 IRIS-CODE-W              PIC X.
                88 IRIS-CODE-PTRCURR2                     VALUE 'W'.
              09 IRIS-CODE-Z              PIC X.
                88 IRIS-CODE-PTRRES                       VALUE 'Z'.
           05 IRIS-KEYS-LEVELS            OCCURS 4.
SAN   *      07 IRIS-KEYVALUES            OCCURS 64.
SAN   *        09 IRIS-KEYVALUE           PIC X(128).
SAN   *        09 IRIS-KEYVALUE-PACKED REDEFINES
SAN   *           IRIS-KEYVALUE           PIC S9(18) COMP-3.
SAN          07 IRIS-KEYVALUES-TAB        PIC X(8192).
SAN          07 IRIS-KEYVALUES-RED1 REDEFINES IRIS-KEYVALUES-TAB.
SAN            09 IRIS-KEYVALUES          OCCURS 64.
SAN              11 IRIS-KEYVALUE         PIC X(128).
SAN          07 IRIS-KEYVALUES-RED2 REDEFINES IRIS-KEYVALUES-TAB.
SAN            09 IRIS-KEYVALUES-P        OCCURS 64.
SAN              11 IRIS-KEYVALUE-PACKED  PIC S9(18) COMP-3.
SAN              11 FILLER                PIC X(118).
      *
      * SEGMENT INDEX POINTER
      *
         03 PARENT-SEGMENT-INDEX          PIC S9(9) COMP.
         03 LAST-SEGMENT-INDEX            PIC S9(9) COMP.
      *
      * SSA KEY USED
      *
         03 KEY-STATUS                    PIC 9.
           88 IS-NOT-LAST-SSA-KEY                         VALUE 0.
           88 IS-LAST-SSA-KEY                             VALUE 1.
      *
      * DLI API USED
      *
         03 IRIS-IMS-API                  PIC X(8) VALUE 'CBLTDLI'.
           88 IRIS-CBLTDLI                         VALUE 'CBLTDLI'.
           88 IRIS-AERTDLI                         VALUE 'AERTDLI'.
           88 IRIS-EXECDLI                         VALUE 'EXECDLI'.
      *
      * AUDIT FIELDS
      *
         03 IRIS-AUDIT-FIELDS.
           05 IRIS-CREATION-USER          PIC X(25).
           05 IRIS-LAST-UPDATE-USER       PIC X(25).
           05 IRIS-CREATION-TRAN          PIC X(25).
           05 IRIS-LAST-UPDATE-TRAN       PIC X(25).
      *
      * IRIS PCB POINTERS USED WHEN AERTDLI MODE
      *
         03 IRIS-AIB-ADDRESS              PIC S9(9) COMP.
         03 IRIS-AIB-POINTER REDEFINES IRIS-AIB-ADDRESS POINTER.
      *  POINTER TO DFHIEBLK   
IMSCD    03 IRIS-DFH-POINTER REDEFINES IRIS-AIB-ADDRESS POINTER.
      *
      * GE MANAGEMENT IN THE PATH CALLS
      *
         03 GE-PATH-CALL                  PIC X    VALUE '1'.
           88 EXEC-GE-PATH-CALL                    VALUE '0'.
           88 SKIP-GE-PATH-CALL                    VALUE '1'.
      *
      * NUM OF PCB IN STORED PROCEDURE
      *
         03 IRIS-AIB-PCBS-COUNT           PIC S9(4) COMP VALUE 0.
      *
      * CHECKPOINT/RESTART PARAMETERS
      *
         03 IRIS-CHKP-FIELDS.
      *    05 IRIS-GSAM-REGION-ID         PIC X(8).
           05 IRIS-GSAM-JOB-NAME          PIC X(8).
           05 IRIS-GSAM-PROGRAM-NAME      PIC X(8).
           05 IRIS-GSAM-PSB-NAME          PIC X(8).
           05 IRIS-GSAM-PCB-NUM           PIC S9(3) COMP-3.
           05 IRIS-GSAM-CHECKPOINT-ID     PIC X(14).
           05 IRIS-GSAM-CHECKPOINT-TIME   PIC X(16).
         03 IRIS-GSAM-LRECL               PIC 9(8).
      *
      * EXEC DLI PARAMETERS
      *
         03 IRIS-PCB-NUM                  PIC 9(4).
         03 IRIS-EXEC-DLI-PTR             POINTER.
         03 IRIS-EXEC-DLI-ADDR REDEFINES IRIS-EXEC-DLI-PTR
                                          PIC S9(9) COMP.
      *
      * DYNAMIC SSA ONLY PARSE
      *
         03 FILLER                        PIC 9 VALUE 0.
           88 IRIS-DYNSSA-EXECUTE               VALUE 0.
           88 IRIS-DYNSSA-PARSEONLY             VALUE 1.             
      *
      * PCB TYPE
      *
         03 IRIS-PCB-TYPE-TABLE.
           05 FILLER                      OCCURS 100.
             07 IRIS-PCB-TYPE             PIC X.
             88 IRIS-PCB-TYPE-TP          VALUE 'T'.
             88 IRIS-PCB-TYPE-DB          VALUE 'D'.
             88 IRIS-PCB-TYPE-GSAM        VALUE 'G'.
      *
      * MAIN PROGRAM TYPE
      *
         03 IRIS-PROGRAM-TYPE             PIC X VALUE 'B'.
           88 IRIS-BATCH                  VALUE 'B'.
           88 IRIS-ONLINE                 VALUE 'T'.
      *
      * KEY FEEDBACK AREA REQUESTED
      *
         03 IRIS-KFB                      PIC 9 VALUE 0.
           88 IRIS-KFB-NO                 VALUE 0.
           88 IRIS-KFB-YES                VALUE 1.
      *
      * KEY FEEDBACK AREA REQUESTED
      *
         03 IRIS-PAIRED-ACCESS            PIC 9 VALUE 0.
           88 IRIS-PAIRED-NO              VALUE 0.
           88 IRIS-PAIRED-YES             VALUE 1.
      *
      * RECEIVED DIBSTAT
      *
         03 IRISGLOB-DIBSTAT              PIC X(2).
      *
      * KEY FEEDBACK AREA LENGTH
      *
         03 IRIS-KFB-LENGTH               PIC 9(8) VALUE ZERO.
      *
      * PCB TYPE
      *
         03 IRIS-PCB-DBD-TABLE.
           05 FILLER                      OCCURS 100.
             07 IRIS-PCB-DBD              PIC X(8).
      *
      * MAIN PROGRAM LANGUAGE
      * VALID VALUES: CBL, PLI, EZT, ASM
      *
         03 IRIS-MAIN-LANG                PIC X(3).
           88 IRIS-MAIN-CBL               VALUE 'CBL'.
           88 IRIS-MAIN-PLI               VALUE 'PLI'.
           88 IRIS-MAIN-EZT               VALUE 'EZT'.
           88 IRIS-MAIN-ASM               VALUE 'ASM'.
      *
      * PLACE-HOLDER - CURRENT MAX LENGTH 42000 BYTES
      *
         03 FILLER                        PIC X(659).	  
