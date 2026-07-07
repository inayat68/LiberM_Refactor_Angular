      ******************************************************************
      * Author : mLogica
      * Product: IRIS-DB - v. 5.5.0
      ******************************************************************
      *
      * DESCRIPTION: DATABASE I/O ROUTINE FOR DL/I UTIILITY FUNCTIONS
      *
      ******************************************************************
      *
       IDENTIFICATION DIVISION.
      *
       PROGRAM-ID. IRISUTIL.
      *
       ENVIRONMENT DIVISION.
      *
       DATA DIVISION.
      *
       WORKING-STORAGE SECTION.
      *
      *    COMMON VARIABLES
      *
           COPY IRISCOMM.
      *
      *    DATABASE COMMON SQLCODES
      *
           COPY IRISSQLC.
      *
      *    STANDARD SQL INCLUDE
      *
           EXEC SQL INCLUDE SQLCA END-EXEC.
      *
      *    WORKING STORAGE VARIABLES
      *
       01 WS-IMS-API                  PIC X(8) VALUE SPACES.
       01 WS-JOBNAME-PTR                USAGE POINTER.
       01 WS-JOBNAME-ADDR  REDEFINES WS-JOBNAME-PTR  PIC S9(9) COMP.
       01 WS-JOBNAME-CHARS              PIC X(8).   
       01 WS-JOBNAME-JBNM               PIC X(8).
       01 WS-JULIAN-DATE.
         03 WS-JULIAN-YEAR              PIC X(4).
         03 WS-JULIAN-DAY               PIC X(3).
       01 WS-TIME.
         03 WS-TIME-HH                  PIC X(2).
         03 WS-TIME-MM                  PIC X(2).
         03 WS-TIME-SS                  PIC X(2).
         03 WS-TIME-CC                  PIC X(2).
       01 WS-CURRENT-DATETIME.
         03 WS-CURRENT-DATE             PIC X(8).
         03 WS-CURRENT-TIME             PIC X(8).
         03 WS-CURRENT-GT               PIC X(5).
      *
       01 WS-TRACE-LEVEL                PIC X.
       01 WS-SQLCODE-N                  PIC S9(5).
       01 WS-SQLCODE-E                  PIC -ZZZZ9.
       01 WS-CLEAN-UP                   PIC S9(8) COMP VALUE +1.
       01 WS-ABEND-CODE                 PIC X(4).
       01 WS-PARAM-NUM                  PIC S9(9) COMP.
       01 WS-SCHED-PARAM-NUM            PIC S9(9) COMP.
       01 WS-MESSAGE-ID-EDITED          PIC ZZZZ9.
       01 WS-MESSAGE                    PIC X(32000).
       01 WS-ERROR-DESCRIPTION          PIC X(80).
       01 WS-ERROR-DESCRIPTION-LEN      PIC S9(4)   COMP.
       01 WS-INDEX                      PIC S9(4)   COMP.
       01 WS-INDEX-C                    PIC S9(4)   COMP.
       01 WS-INDEX-2                    PIC S9(4)   COMP.
       01 WS-INDEX-Z                    PIC ZZ9.
       01 WS-ADDR-9                     PIC 9(9).
       01 WS-LAST-IORTN-SECTION         PIC X(30).
       01 WS-PTR                        POINTER.
TMPDBG 01 WS-ADDR REDEFINES WS-PTR PIC S9(9) COMP.      
       01 WS-PSB-PCBS-PTR               POINTER.
       01 WS-PSB-SENSEGS-PTR            POINTER.
       01 WS-TABLE-NAME                 PIC X(8).
       01 WS-TABLE-ACCESS               PIC X(8).
       01 WS-TSK-NR                     PIC S9(8) COMP-3 VALUE ZERO.
       01 WS-IO-RTN.
         03 WS-IO-RTN-DBDNAME           PIC X(8)       VALUE SPACES.
         03 FILLER REDEFINES WS-IO-RTN-DBDNAME.
           05 FILLER                    PIC X(6).
IRISDC     88 RTN-IS-PCBIO                             VALUE 'PCB_IO',
IRISDC                                                       'ALT_IO'.
           88 RTN-IS-GSAM                              VALUE 'GSAMIO'.
           05 FILLER                    PIC X(2).
         03 FILLER REDEFINES WS-IO-RTN-DBDNAME.
      *    05 FILLER                    PIC X(4).
      *      88 RTN-IS-GSAMDBD                         VALUE 'GSAM'.
      *    05 FILLER                    PIC X(4).
           05 FILLER                    PIC X(2).
           05 FILLER                    PIC X(1).
             88 RTN-IS-GSAMDBD                         VALUE 'G'.
           05 FILLER                    PIC X(5).
       01 WS-DUMMY-PCB.
         03 WS-DUMMY-PCB-HEADER         PIC X(36).
         03 FILLER REDEFINES WS-DUMMY-PCB-HEADER.
           05 WS-DUMMY-DBD-NAME         PIC X(8).
           05 WS-DUMMY-SEG-LVL          PIC 9(2).
           05 WS-DUMMY-STS-CODE         PIC X(2).
           05 WS-DUMMY-PROC-OPT         PIC X(4).
           05 WS-DUMMY-PCB-RESERVED     PIC S9(9)  COMP.
           05 FILLER REDEFINES WS-DUMMY-PCB-RESERVED PIC X(4).
             88 WS-DUMMY-PCB-IRIS-EYE   VALUE 'IRIS'.
         03 WS-DUMMY-FB-KEY             PIC X(1587).
         03 WS-DUMMY-FB-RNTM            PIC X(377).
         03 WS-DUMMY-CNTL-FIXED         PIC X(1000).
         03 WS-DUMMY-CNTL-VAR           PIC X(32000).
      *
       01 IRIS-DLIUIB.
         03 IRIS-UIBPCBAL               POINTER.
         03 IRIS-UIBRCODE.
              05 IRIS-UIBFCTR           PIC X.
              05 IRIS-UIBDLTR           PIC X.
      *
       01 FILLER                        PIC S9(4)   COMP.
         88 WS-PSB-NOT-ALLOCATED        VALUE 0.
         88 WS-PSB-ALLOCATED            VALUE 1.
       01 FILLER                        PIC S9(4)   COMP.
         88 WS-IRISDBD-OK              VALUE 0.
         88 WS-IRISDBD-NOK             VALUE 1.
       01 WS-RUN-DBD-NAME               PIC X(8).
       01 WS-RUN-DBD-STATUS             PIC S9(4)   COMP.
         88 WS-DUAL-IMS-ONLY            VALUE 1.
         88 WS-DUAL-SQL-ONLY            VALUE 2.
         88 WS-DUAL-BOTH                VALUE 3 6.
         88 WS-DUAL-UPDATE-ONLY         VALUE 4.
         88 WS-DUAL-BOTH-IMS-PRIMARY    VALUE 5 7.
         88 WS-DUAL-BOTH-NO-ABEND       VALUE 6.
         88 WS-DUAL-BOTH-IMS-PR-NO-ABEND VALUE 7.
         88 WS-TP                       VALUE 99.

       01 WS-RUN-DBD-STATUS-DECOD.
         03 FILLER PIC X(20) VALUE 'IMS ONLY'.
         03 FILLER PIC X(20) VALUE 'SQL ONLY'.
         03 FILLER PIC X(20) VALUE 'DUAL DB2 PRIMARY'.
         03 FILLER PIC X(20) VALUE 'DUAL UPDATE ONLY'.
         03 FILLER PIC X(20) VALUE 'DUAL IMS PRIMARY'.
         03 FILLER PIC X(20) VALUE 'DUAL LIGHT DB2 PR.'.
         03 FILLER PIC X(20) VALUE 'DUAL LIGHT IMS PR.'.
       01 FILLER REDEFINES WS-RUN-DBD-STATUS-DECOD.
         03 WS-DUAL-DECOD PIC X(20) OCCURS 7.

       01 WS-DBD-LOOKUP-TABLE-COUNT     PIC S9(4)   COMP.
       01 WS-DBD-LOOKUP-AREA.
         03 WS-DBD-LOOKUP-TABLE         OCCURS 255.
           05 WS-LOOKUP-DBD             PIC X(3).
           05 WS-LOOKUP-STATUS          PIC S9(4)   COMP.
       01 WS-CMPAT-IDX                  PIC S9(4)   COMP.
       01 WS-CHKP-ID                    PIC X(14).
       01 WS-IRIS-JOB-NAME              PIC X(08).
       01 WS-IRIS-PGM-NAME              PIC X(08).
       01 WS-IRIS-PSB-NAME              PIC X(08).
       01 IRIS-IDX                      PIC S9(4) COMP.
      *
      * MAX PCBS, IF THE VALUE HAS TO CHANGE ALSO THE ARRAYS BELOW
      * IRIS-BLL-CELLS, IRIS-PCBS AND THE LINKAGE ARRAY IRIS-LK-CELLS
      * MUST BE CHANGED ACCORDINGLY
      *
       01 IRIS-MAX-PCBS                 PIC S9(4)   COMP  VALUE 45.     
       01 WS-DBD-NAME                   PIC X(3) VALUE SPACE.
      *
       01 WS-CHECKPOINT-TIME    PIC X(16).
       01 WS-SQLCODE            PIC S9(9) COMP.
      *
       01 IRIS-IO-AREAS.                                                
         03 FILLER                      OCCURS 45.                      
           05 IRIS-IO-AREA              PIC X(1000).                    
       01 IRIS-WS-BLL-POINTER           POINTER.
       01 IRIS-WS-BLL-ADDR REDEFINES IRIS-WS-BLL-POINTER PIC S9(9) COMP.
      *
       01 IRIS-BLL-CELLS.
         03 IRIS-BLL-CELL               OCCURS 45.                      
           05 IRIS-BLL-POINTER          POINTER.
      *
       01 IRIS-PCBS-AREA.
         03 IRIS-PCBS-TAB               OCCURS 45.                      
      * 36 BYTES
           05 IRIS-PCB-HEADER.
             07 IRIS-PCB-DBD-NAME       PIC X(8).
             07 IRIS-PCB-SEGMENT-LEVEL  PIC 9(2).
             07 IRIS-PCB-STATUS-CODE    PIC X(2).
             07 IRIS-PCB-PROC-OPTS      PIC X(4).
             07 IRIS-PCB-RESERVED       PIC S9(9)  COMP.
             07 IRIS-PCB-SEGMENT-NAME   PIC X(8).
             07 IRIS-PCB-FB-KEY-LENGTH  PIC S9(9)  COMP.
             07 IRIS-PCB-NUM-SENSEGS    PIC S9(9)  COMP.
      * 1587 BYTES
           05  IRIS-PCB-FEEDBACK        PIC X(1587).
      * 377 Bytes
           COPY IRISISEG REPLACING ==01 MEMORY-PCB-AREA.==
                                BY ==05 MEMORY-PCB-AREA.==.
      * 33000 BYTES
           COPY IRISRNTM REPLACING ==01 RUN-CONTROL-AREA.==
                                BY ==05 IRIS-RNT-CNTL-AREA.==.
      *
       LINKAGE SECTION.
      *
      *    IRIS GLOBAL AREA
      *
           COPY IRISGLOB REPLACING ==:PROGNM:== BY =='IRISUTIL'==.
      *
      *    IRIS PCB POINTERS
      *
      *    COPY IRISPTRS.
      *01  IRIS-PCB-PTR-1      POINTER.
	   01  IRIS-PCB-PTR-1X     PIC X(32000).
	   01  IRIS-PCB-PTR-1      REDEFINES IRIS-PCB-PTR-1X POINTER.
       01  IRIS-PCB-CHKP       REDEFINES IRIS-PCB-PTR-1X PIC X(78).
       01  IRIS-PCB-AIB        REDEFINES IRIS-PCB-PTR-1X PIC X.
       01  IRIS-LK-DIB-BLOCK   REDEFINES IRIS-PCB-PTR-1X PIC X(32).
      *01  IRIS-PCB-PTR-2      POINTER.
	   01  IRIS-PCB-PTR-2X     PIC X(32000).
	   01  IRIS-PCB-PTR-2      REDEFINES IRIS-PCB-PTR-2X POINTER.
       01  IRIS-LK-PSB-NAME    REDEFINES IRIS-PCB-PTR-2X PIC X(8).
       01  IRIS-LK-BLL-POINTER REDEFINES IRIS-PCB-PTR-2X POINTER.
       01  IRIS-LK-BLL-ADDR    REDEFINES IRIS-PCB-PTR-2X PIC S9(9) COMP.
       01  IRIS-CHKP-LENGTH    REDEFINES IRIS-PCB-PTR-2X PIC S9(9) COMP.
       01  IRIS-PCB-PTR-3      POINTER.
       01  IRIS-CHKP-ID        REDEFINES IRIS-PCB-PTR-3 PIC X(14).
       01  IRIS-PCB-PTR-4      POINTER.
       01  IRIS-CHKP-AREA1-LEN REDEFINES IRIS-PCB-PTR-4 PIC S9(9) COMP.
       01  IRIS-CHKP-AREA1-LX  REDEFINES IRIS-PCB-PTR-4 PIC X(4).
       01  IRIS-PCB-PTR-5      POINTER.
CHECK * IT MUST BE DEPENDING ON THE AREA-LEN
       01  IRIS-CHKP-AREA1     REDEFINES IRIS-PCB-PTR-5 PIC X.
       01  IRIS-PCB-PTR-6      POINTER.
       01  IRIS-CHKP-AREA2-LEN REDEFINES IRIS-PCB-PTR-6 PIC S9(9) COMP.
       01  IRIS-CHKP-AREA2-LX  REDEFINES IRIS-PCB-PTR-6 PIC X(4).
       01  IRIS-PCB-PTR-7      POINTER.
       01  IRIS-CHKP-AREA2     REDEFINES IRIS-PCB-PTR-7 PIC X.
       01  IRIS-PCB-PTR-8      POINTER.
       01  IRIS-CHKP-AREA3-LEN REDEFINES IRIS-PCB-PTR-8 PIC S9(9) COMP.
       01  IRIS-CHKP-AREA3-LX  REDEFINES IRIS-PCB-PTR-8 PIC X(4).
       01  IRIS-PCB-PTR-9      POINTER.
       01  IRIS-CHKP-AREA3     REDEFINES IRIS-PCB-PTR-9 PIC X.
       01  IRIS-PCB-PTR-10     POINTER.
       01  IRIS-CHKP-AREA4-LEN REDEFINES IRIS-PCB-PTR-10 PIC S9(9) COMP.
       01  IRIS-CHKP-AREA4-LX  REDEFINES IRIS-PCB-PTR-10 PIC X(4).
       01  IRIS-PCB-PTR-11     POINTER.
       01  IRIS-CHKP-AREA4     REDEFINES IRIS-PCB-PTR-11 PIC X.
       01  IRIS-PCB-PTR-12     POINTER.
       01  IRIS-CHKP-AREA5-LEN REDEFINES IRIS-PCB-PTR-12 PIC S9(9) COMP.
       01  IRIS-CHKP-AREA5-LX  REDEFINES IRIS-PCB-PTR-12 PIC X(4).
       01  IRIS-PCB-PTR-13     POINTER.
       01  IRIS-CHKP-AREA5     REDEFINES IRIS-PCB-PTR-13 PIC X.
       01  IRIS-PCB-PTR-14     POINTER.
       01  IRIS-CHKP-AREA6-LEN REDEFINES IRIS-PCB-PTR-14 PIC S9(9) COMP.
       01  IRIS-CHKP-AREA6-LX  REDEFINES IRIS-PCB-PTR-14 PIC X(4).
       01  IRIS-PCB-PTR-15     POINTER.
       01  IRIS-CHKP-AREA6     REDEFINES IRIS-PCB-PTR-15 PIC X.
       01  IRIS-PCB-PTR-16     POINTER.
       01  IRIS-CHKP-AREA7-LEN REDEFINES IRIS-PCB-PTR-16 PIC S9(9) COMP.
       01  IRIS-CHKP-AREA7-LX  REDEFINES IRIS-PCB-PTR-16 PIC X(4).
       01  IRIS-PCB-PTR-17     POINTER.
       01  IRIS-CHKP-AREA7     REDEFINES IRIS-PCB-PTR-17 PIC X.
       01  IRIS-PCB-PTR-18     POINTER.
       01  IRIS-PCB-PTR-19     POINTER.
       01  IRIS-PCB-PTR-20     POINTER.
       01  IRIS-PCB-PTR-21     POINTER.
       01  IRIS-PCB-PTR-22     POINTER.
       01  IRIS-PCB-PTR-23     POINTER.
       01  IRIS-PCB-PTR-24     POINTER.
       01  IRIS-PCB-PTR-25     POINTER.
       01  IRIS-PCB-PTR-26     POINTER.
       01  IRIS-PCB-PTR-27     POINTER.
       01  IRIS-PCB-PTR-28     POINTER.
       01  IRIS-PCB-PTR-29     POINTER.
       01  IRIS-PCB-PTR-30     POINTER.
       01  IRIS-PCB-PTR-31     POINTER.
       01  IRIS-PCB-PTR-32     POINTER.
       01  IRIS-PCB-PTR-33     POINTER.
       01  IRIS-PCB-PTR-34     POINTER.
       01  IRIS-PCB-PTR-35     POINTER.
       01  IRIS-PCB-PTR-36     POINTER.
       01  IRIS-PCB-PTR-37     POINTER.
       01  IRIS-PCB-PTR-38     POINTER.
       01  IRIS-PCB-PTR-39     POINTER.
       01  IRIS-PCB-PTR-40     POINTER.
       01  IRIS-PCB-PTR-41     POINTER.
       01  IRIS-PCB-PTR-42     POINTER.
       01  IRIS-PCB-PTR-43     POINTER.
       01  IRIS-PCB-PTR-44     POINTER.
       01  IRIS-PCB-PTR-45     POINTER.
       01  IRIS-PCB-PTR-46     POINTER.
       01  IRIS-PCB-PTR-47     POINTER.
       01  IRIS-PCB-PTR-48     POINTER.
       01  IRIS-PCB-PTR-49     POINTER.
       01  IRIS-PCB-PTR-50     POINTER.
       01  IRIS-PCB-PTR-51     POINTER.
       01  IRIS-PCB-PTR-52     POINTER.
       01  IRIS-PCB-PTR-53     POINTER.
       01  IRIS-PCB-PTR-54     POINTER.
       01  IRIS-PCB-PTR-55     POINTER.
       01  IRIS-PCB-PTR-56     POINTER.
       01  IRIS-PCB-PTR-57     POINTER.
       01  IRIS-PCB-PTR-58     POINTER.
       01  IRIS-PCB-PTR-59     POINTER.
       01  IRIS-PCB-PTR-60     POINTER.
       01  IRIS-PCB-PTR-61     POINTER.
       01  IRIS-PCB-PTR-62     POINTER.
       01  IRIS-PCB-PTR-63     POINTER.
       01  IRIS-PCB-PTR-64     POINTER.
       01  IRIS-PCB-PTR-65     POINTER.
       01  IRIS-PCB-PTR-66     POINTER.
       01  IRIS-PCB-PTR-67     POINTER.
       01  IRIS-PCB-PTR-68     POINTER.
       01  IRIS-PCB-PTR-69     POINTER.
       01  IRIS-PCB-PTR-70     POINTER.
       01  IRIS-PCB-PTR-71     POINTER.
       01  IRIS-PCB-PTR-72     POINTER.
       01  IRIS-PCB-PTR-73     POINTER.
       01  IRIS-PCB-PTR-74     POINTER.
       01  IRIS-PCB-PTR-75     POINTER.
       01  IRIS-PCB-PTR-76     POINTER.
       01  IRIS-PCB-PTR-77     POINTER.
       01  IRIS-PCB-PTR-78     POINTER.
       01  IRIS-PCB-PTR-79     POINTER.
       01  IRIS-PCB-PTR-80     POINTER.
       01  IRIS-PCB-PTR-81     POINTER.
       01  IRIS-PCB-PTR-82     POINTER.
       01  IRIS-PCB-PTR-83     POINTER.
       01  IRIS-PCB-PTR-84     POINTER.
       01  IRIS-PCB-PTR-85     POINTER.
       01  IRIS-PCB-PTR-86     POINTER.
       01  IRIS-PCB-PTR-87     POINTER.
       01  IRIS-PCB-PTR-88     POINTER.
       01  IRIS-PCB-PTR-89     POINTER.
       01  IRIS-PCB-PTR-90     POINTER.
       01  IRIS-PCB-PTR-91     POINTER.
       01  IRIS-PCB-PTR-92     POINTER.
       01  IRIS-PCB-PTR-93     POINTER.
       01  IRIS-PCB-PTR-94     POINTER.
       01  IRIS-PCB-PTR-95     POINTER.
       01  IRIS-PCB-PTR-96     POINTER.
       01  IRIS-PCB-PTR-97     POINTER.
       01  IRIS-PCB-PTR-98     POINTER.
       01  IRIS-PCB-PTR-99     POINTER.
       01  IRIS-PCB-PTR-100     POINTER.
      *
      *    IRIS PCB STRUCTURE
      *
      *    COPY IRISPCB.
      ******************************************************************
      * Author : mLogica
      * Product: IRIS-DB - v. 5.5.0
      ******************************************************************
      *                                                                *
      *   DESCRIPTION: IRIS PCB AREA                                   *
      *                                                                *
      ******************************************************************
       01 IRIS-DB-PCB.
         03 DB-PCB-FIXED-PART.
           05 DB-PCB-DBD-NAME             PIC X(8).
           05 DB-PCB-SEGMENT-LEVEL        PIC 9(2).
             88 DB-PCB-IS-GSAM                       VALUE 55.
           05 DB-PCB-STATUS-CODE          PIC X(2).
             88 DB-STATUS-OK                         VALUE '  '.
             88 DB-STATUS-RESOURCE-DEADLOCK          VALUE 'FD'.
             88 DB-STATUS-HIGHER-LEVEL-CROSSED       VALUE 'GA'.
             88 DB-STATUS-END-DB-REACHED             VALUE 'GB'.
             88 DB-STATUS-SEGMENT-NOT-FOUND          VALUE 'GE'.
             88 DB-STATUS-CHANGED-SEGMENT-TYPE       VALUE 'GK'.
             88 DB-STATUS-DUPLICATED-KEY             VALUE 'II'.
             88 DB-STATUS-ISRT-RULE-VIOLATION        VALUE 'IX'.
             88 DB-STATUS-INTERNAL-NOT-HANDLED       VALUE 'XX'.
             88 DB-STATUS-DUAL-PCB-MISMATCH          VALUE 'XY'.
             88 DB-STATUS-DUAL-IOAREA-MISMATCH       VALUE 'XZ'.
             88 DB-STATUS-PSB-INIT-ERROR             VALUE 'TE'.
             88 DB-STATUS-NO-PSB-SCHEDULED           VALUE 'TG'.
             88 DB-STATUS-WRONG-PCB                  VALUE 'TP'.
             88 DB-STATUS-NO-MORE-MSG                VALUE 'QC'. 
             88 DB-STATUS-NOT-VALID-LENGTH           VALUE 'V1'. 
             88 DB-STATUS-DLET-RULE-VIOLATION        VALUE 'DX'. 
             88 DB-STATUS-REPL-RULE-VIOLATION        VALUE 'RX'. 
           05 DB-PCB-PROC-OPTS            PIC X(4).
           05 DB-PCB-RESERVED             PIC S9(9)  COMP.
           05 DB-PCB-RESERVED-X REDEFINES DB-PCB-RESERVED PIC X(4).
             88 DB-PCB-IRIS-EYE                      VALUE 'IRIS'.
           05 DB-PCB-SEGMENT-NAME         PIC X(8).
           05 DB-PCB-FB-KEY-LENGTH        PIC S9(9)  COMP.
           05 DB-PCB-NUM-SENSEGS          PIC S9(9)  COMP.
         03 DB-PCB-KEY-FB                 PIC X(1587).
      *  COPYBOOK IRISISEG
         03 DB-PCB-MEM-PCB-AREA           PIC X(377).
      *  IRIS CONTROL AREA, MUST BE IN SYNCH WITH IRIS-RNT-CNTL-AREA
      *  DECLARED IN IRISUTIL
         03 DB-RNT-CNTL-AREA.
           05 DB-RNT-CNTL-AREA-FIXED      PIC X(1000).             
           05 FILLER REDEFINES DB-RNT-CNTL-AREA-FIXED.
             07 FILLER                    PIC X(781).
             07 RUN-IMS-DUAL-POINTER-IO   POINTER.
             07 FILLER                    PIC X(215).
           05 DB-RNT-CNTL-AREA-VAR        PIC X(32000).             
       01 IRIS-MSG-PCB REDEFINES IRIS-DB-PCB.
          02 FILLER                    PIC X(8).
          02 FILLER                    PIC X.
            88 IRIS-MSG-PRINT-DETAIL   VALUE 'D'.
          02 FILLER                    PIC X.
          02 IRIS-MSG-STATUS-CODE      PIC X(2).
          02 IRIS-MSG-MESSAGE-COUNT    PIC S9(4) COMP.
          02 IRIS-MSG-TIMESTAMP        PIC X(26).
      *
      *
       01  IRIS-LK-CELLS.
           03 FILLER                    OCCURS 45.                      
              05 IRIS-LK-POINTER        POINTER.
      *
       01  IRIS-LK-PCBS-ADDR            PIC S9(9)   COMP-5.
       01  IRIS-LK-PCBS-PTR REDEFINES IRIS-LK-PCBS-ADDR POINTER.
      *
      *
       01  LK-DLIUIB.                                                   
            05 UIBPCBAL-POINTER          POINTER.                       
            05 UIBPCBAL REDEFINES UIBPCBAL-POINTER PIC S9(9) COMP-5.    
            05 UIBRCODE.                                                
               10 UIBFCTR                PIC X(01).                     
               10 UIBDLTR                PIC X(01).                     
       01 IRIS-INQY-AREA.
          05 INQY-IMS-ID                 PIC X(08).
          05 INQY-IMS-REL-LEVEL          PIC X(04).
          05 INQY-IMS-CONTROL-REGION     PIC X(08).
          05 INQY-APPL-REGION-TYPE       PIC X(08).
          05 INQY-REGION-ID              PIC X(04).
          05 INQY-APPL-PROG-NAME         PIC X(08).
          05 INQY-PSB-NAME               PIC X(08).
          05 INQY-TRAN-NAME              PIC X(08).
          05 INQY-USER-ID                PIC X(08).
          05 INQY-GROUP-NAME             PIC X(08).
          05 INQY-STATUS-GROUP-INDIC     PIC X(04).
          05 INQY-RECOVERY-TOKEN-PTR     POINTER.
          05 INQY-APPL-PARM-PTR          POINTER.
          05 FILLER                      PIC X(256).
      *
       01 IRIS-IO-PCB.
         03 IRIS-IO-LTERM                PIC X(8).
         03 IRIS-IO-FILLER               PIC X(2).
         03 IRIS-IO-STATUS-CODE          PIC X(2).
         03 IRIS-IO-CURRENT-DT           PIC S9(7) COMP-3.
         03 IRIS-IO-CURRENT-TM           PIC S9(7) COMP-3.
         03 IRIS-IO-MSG-SEQ-NO           PIC S9(5) COMP.
         03 IRIS-IO-MOD-NAME             PIC X(8).
         03 IRIS-IO-USER-ID              PIC X(8).
      *
      * 
      *    DIB BLOCK FOR EXEC DLI
      * 
       01 LK-DIB-BLOCK                  PIC X(32). 
      * 
       PROCEDURE DIVISION USING IRIS-WORK-AREA
      *                         COPY IRISPTRU.
      *                         IRIS-PCB-PTR-1
      *                         IRIS-PCB-PTR-2
                                IRIS-PCB-PTR-1X
                                IRIS-PCB-PTR-2X
                                IRIS-PCB-PTR-3
                                IRIS-PCB-PTR-4
                                IRIS-PCB-PTR-5
                                IRIS-PCB-PTR-6
                                IRIS-PCB-PTR-7
                                IRIS-PCB-PTR-8
                                IRIS-PCB-PTR-9
                                IRIS-PCB-PTR-10
                                IRIS-PCB-PTR-11
                                IRIS-PCB-PTR-12
                                IRIS-PCB-PTR-13
                                IRIS-PCB-PTR-14
                                IRIS-PCB-PTR-15
                                IRIS-PCB-PTR-16
                                IRIS-PCB-PTR-17
                                IRIS-PCB-PTR-18
                                IRIS-PCB-PTR-19
                                IRIS-PCB-PTR-20
                                IRIS-PCB-PTR-21
                                IRIS-PCB-PTR-22
                                IRIS-PCB-PTR-23
                                IRIS-PCB-PTR-24
                                IRIS-PCB-PTR-25
                                IRIS-PCB-PTR-26
                                IRIS-PCB-PTR-27
                                IRIS-PCB-PTR-28
                                IRIS-PCB-PTR-29
                                IRIS-PCB-PTR-30
                                IRIS-PCB-PTR-31
                                IRIS-PCB-PTR-32
                                IRIS-PCB-PTR-33 
                                IRIS-PCB-PTR-34 
                                IRIS-PCB-PTR-35 
                                IRIS-PCB-PTR-36 
                                IRIS-PCB-PTR-37 
                                IRIS-PCB-PTR-38 
                                IRIS-PCB-PTR-39 
                                IRIS-PCB-PTR-40 
                                IRIS-PCB-PTR-41 
                                IRIS-PCB-PTR-42 
                                IRIS-PCB-PTR-43 
                                IRIS-PCB-PTR-44 
                                IRIS-PCB-PTR-45 
                                IRIS-PCB-PTR-46 
                                IRIS-PCB-PTR-47 
                                IRIS-PCB-PTR-48 
                                IRIS-PCB-PTR-49 
                                IRIS-PCB-PTR-50 
                                IRIS-PCB-PTR-51 
                                IRIS-PCB-PTR-52 
                                IRIS-PCB-PTR-53 
                                IRIS-PCB-PTR-54 
                                IRIS-PCB-PTR-55 
                                IRIS-PCB-PTR-56 
                                IRIS-PCB-PTR-57 
                                IRIS-PCB-PTR-58 
                                IRIS-PCB-PTR-59 
                                IRIS-PCB-PTR-60 
                                IRIS-PCB-PTR-61 
                                IRIS-PCB-PTR-62 
                                IRIS-PCB-PTR-63 
                                IRIS-PCB-PTR-64 
                                IRIS-PCB-PTR-65 
                                IRIS-PCB-PTR-66 
                                IRIS-PCB-PTR-67 
                                IRIS-PCB-PTR-68 
                                IRIS-PCB-PTR-69 
                                IRIS-PCB-PTR-70 
                                IRIS-PCB-PTR-71 
                                IRIS-PCB-PTR-72 
                                IRIS-PCB-PTR-73 
                                IRIS-PCB-PTR-74 
                                IRIS-PCB-PTR-75 
                                IRIS-PCB-PTR-76 
                                IRIS-PCB-PTR-77 
                                IRIS-PCB-PTR-78 
                                IRIS-PCB-PTR-79 
                                IRIS-PCB-PTR-80 
                                IRIS-PCB-PTR-81 
                                IRIS-PCB-PTR-82 
                                IRIS-PCB-PTR-83 
                                IRIS-PCB-PTR-84 
                                IRIS-PCB-PTR-85 
                                IRIS-PCB-PTR-86 
                                IRIS-PCB-PTR-87 
                                IRIS-PCB-PTR-88 
                                IRIS-PCB-PTR-89 
                                IRIS-PCB-PTR-90 
                                IRIS-PCB-PTR-91 
                                IRIS-PCB-PTR-92 
                                IRIS-PCB-PTR-93 
                                IRIS-PCB-PTR-94 
                                IRIS-PCB-PTR-95 
                                IRIS-PCB-PTR-96 
                                IRIS-PCB-PTR-97 
                                IRIS-PCB-PTR-98 
                                IRIS-PCB-PTR-99 
                                IRIS-PCB-PTR-100.
       MAIN-PROGRAM.
      *
           IF IRIS-EXECDLI
             PERFORM EXTRACT-PCB-EXEC THRU EXTRACT-PCB-EXEC-EX
           END-IF
      *
NODUAL     SET IRIS-FINAL TO TRUE
      *
           PERFORM INIT-VARIABLES THRU INIT-VARIABLES-EX
      *
           EVALUATE TRUE
           WHEN IRIS-FUNC-DLIT
      * INITIALIZE PCBS
             PERFORM HANDLE-BATCH-PCB THRU HANDLE-BATCH-PCB-EX
           WHEN IRIS-FUNC-SCHD
      * MANAGE THE EXEC DLI SCHED 
             PERFORM HANDLE-SCHED THRU HANDLE-SCHED-EX
           WHEN IRIS-FUNC-PCB
      * INITIALIZE PCBS FOR THE ONLINE PROGRAMS
             PERFORM HANDLE-ONLINE-PCB THRU HANDLE-ONLINE-PCB-EX
           WHEN IRIS-FUNC-TERM
      * PERFORM TERM
             PERFORM HANDLE-TERM THRU HANDLE-TERM-EX
           WHEN IRIS-FUNC-CHKP
             PERFORM HANDLE-BASIC-CHKP THRU HANDLE-BASIC-CHKP-EX
           WHEN OTHER
      * ABEND OR RETURN - MANAGED FROM THE CALLER
             SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
      *      IF IRIS-CBLTDLI
      *        SET ADDRESS OF IRIS-DB-PCB TO ADDRESS OF IRIS-PCB-AIB
      *      END-IF
             SET DB-STATUS-INTERNAL-NOT-HANDLED TO TRUE
           END-EVALUATE
      *
           PERFORM FINALIZE-VARIABLES THRU FINALIZE-VARIABLES-EX.
      *
       MAIN-PROGRAM-EX.
      *
           IF IRIS-EXECDLI
             PERFORM SET-DIB-BLOCK THRU SET-DIB-BLOCK-EX
           END-IF.
           EXIT PROGRAM.
      *
      ******************************************************************
      *    INITIALIZE VARIABLES
      ******************************************************************
      *
       INIT-VARIABLES SECTION.
      *
           MOVE ZERO TO WS-DBD-LOOKUP-TABLE-COUNT
                        WS-CMPAT-IDX
           SET WS-PSB-NOT-ALLOCATED TO TRUE.
      *
       INIT-VARIABLES-EX.
      *
           EXIT.
      *
      ******************************************************************
      *    FINALIZE VARIABLES
      ******************************************************************
      *
       FINALIZE-VARIABLES SECTION.
      *
           CONTINUE.
      *
       FINALIZE-VARIABLES-EX.
      *
           EXIT.
      *
      ******************************************************************
      *    HANDLE EXEC DLI SCHEDULE
      ******************************************************************
      *
       HANDLE-SCHED SECTION.
      *
           MOVE 'HANDLE-SCHED' TO WS-LAST-IORTN-SECTION
      *
           IF IRIS-FUNC-SCHD 
             PERFORM HANDLE-ONLINE-PCB THRU HANDLE-ONLINE-PCB-EX
ALTERN*      SET IRIS-EXEC-DLI-PTR TO IRIS-LK-BLL-POINTER
             SET IRISADDR-RTN TO TRUE
      *      CALL IRIS-WS-RTN USING IRIS-WORK-AREA IRIS-BLL-CELLS
      *                             IRIS-EXEC-DLI-PTR
             SET IRIS-EXEC-DLI-PTR TO ADDRESS OF IRIS-BLL-CELLS
           ELSE                         
             PERFORM SET-LINKAGE-PCBS THRU SET-LINKAGE-PCBS-EX
             PERFORM HANDLE-BATCH-PCB THRU HANDLE-BATCH-PCB-EX
           END-IF
           SET DB-STATUS-OK TO TRUE.
     *
       HANDLE-SCHED-EX.
      *
           EXIT.
      *
      ******************************************************************
      *    HANDLE PCB SCHEDULE FOR ONLINE
      ******************************************************************
      *
       HANDLE-ONLINE-PCB SECTION.
      *
           MOVE 'HANDLE-ONLINE-PCB' TO WS-LAST-IORTN-SECTION
      *
           MOVE SPACE TO IRIS-AUDIT-FIELDS
NODUAL*    MOVE LOW-VALUE TO IRIS-PCBS-AREA
           MOVE IRIS-PARAM-NUM TO WS-SCHED-PARAM-NUM
      *
           MOVE IRIS-LK-PSB-NAME TO IRIS-PSB-NAME
           PERFORM FINAL-SCHED THRU FINAL-SCHED-EX.
      *
       HANDLE-ONLINE-PCB-EX.
      *
           EXIT.
      ******************************************************************
      *    HANDLE TERM
      ******************************************************************
      *
       HANDLE-TERM SECTION.
      *
           MOVE 'HANDLE-TERM' TO WS-LAST-IORTN-SECTION
      *
      *    INITIALIZE IRIS-USED-KEYS-PSB.
           MOVE LOW-VALUE TO IRIS-PCBS-AREA.
      * TERM REQUESTED ONLY FROM ONLINE PROGRAMS, SO NO NEED TO 
      * PERFORM A COMMIT
      *    IF IRIS-BATCH
      *      EXEC SQL COMMIT END-EXEC
      *    END-IF
      *
      *
       HANDLE-TERM-EX.
      *
           EXIT.
      ******************************************************************
      *    HANDLE BASIC CHKP
      ******************************************************************
      *
       HANDLE-BASIC-CHKP SECTION.
      *
           MOVE 'HANDLE-BASIC-CHKP' TO WS-LAST-IORTN-SECTION
DEMO  * DEMO EFFECT: NOT SAVING AREA AS NOT NEEDED FOR DEMOS           
           EXEC SQL COMMIT END-EXEC.
      *
       HANDLE-BASIC-CHKP-EX.
      *
           EXIT.
      *
      ******************************************************************
      *    SCHED FOR FINAL VERSION
      ******************************************************************
      *
       FINAL-SCHED SECTION.
      *
           MOVE 'FINAL-SCHED' TO WS-LAST-IORTN-SECTION
      *
      *
           PERFORM VARYING WS-INDEX FROM 1 BY 1
                   UNTIL WS-INDEX > IRIS-PARAM-NUM
      *
             IF NOT IRIS-NO-ERROR
               DISPLAY 'IRISUTIL - ALLOCATE PSB ERROR'
               GO TO FINAL-SCHED-EX
             END-IF
      *
      *      INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
      *      SET IRISADDR-RTN TO TRUE
      *      CALL IRIS-WS-RTN USING IRIS-WORK-AREA
      *                             IRIS-PCBS-TAB(WS-INDEX) WS-PTR
             SET WS-PTR TO ADDRESS OF IRIS-PCBS-TAB(WS-INDEX)
             SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
             SET ADDRESS OF IRIS-DB-PCB TO IRIS-BLL-POINTER(WS-INDEX)
             SET DB-PCB-IRIS-EYE TO TRUE
FIX   *      MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
             MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
             SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
             SET WS-DUAL-SQL-ONLY TO TRUE
             MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
             MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
             MOVE IRIS-PSB-NAME TO DT-PSB-NAME(WS-INDEX)
           END-PERFORM.
      *
       FINAL-SCHED-EX.
      *
           EXIT.
      *
      ******************************************************************
      *    HANDLE PCB SCHEDULE FOR BATCH
      ******************************************************************
      *
       HANDLE-BATCH-PCB SECTION.
      *
           MOVE 'HANDLE-BATCH-PCB' TO WS-LAST-IORTN-SECTION
      *
           PERFORM VARYING WS-INDEX FROM 1 BY 1
             UNTIL WS-INDEX > IRIS-PARAM-NUM
                OR IRIS-PCB-TYPE-DB(WS-INDEX)
                OR IRIS-PCB-TYPE-GSAM(WS-INDEX)
           END-PERFORM
      *
           MOVE SPACE TO IRIS-AUDIT-FIELDS
           MOVE LOW-VALUE TO IRIS-PCBS-AREA
           MOVE IRIS-PARAM-NUM TO WS-SCHED-PARAM-NUM
           MOVE IRIS-PSB-NAME TO DT-PSB-NAME(1)
      *
           MOVE ZERO TO WS-INDEX
           MOVE ZERO TO WS-INDEX-C
      *
      *    IRIS PCB ALLOCATION
      *
           SET IRISADDR-RTN TO TRUE
      *
           PERFORM HANDLE-BATCH-PCB-A THRU HANDLE-BATCH-PCB-A-EX
      *
      *    SET IRISADDR-RTN TO TRUE
      *    CALL IRIS-WS-RTN USING IRIS-WORK-AREA IRIS-BLL-CELLS
      *                           IRIS-EXEC-DLI-PTR
           SET IRIS-EXEC-DLI-PTR TO ADDRESS OF IRIS-BLL-CELLS
           .
      *
       HANDLE-BATCH-PCB-EX.
      *
           EXIT.
      *
       HANDLE-BATCH-PCB-A SECTION.
      *
           PERFORM HANDLE-BATCH-PCB-A1
           PERFORM HANDLE-BATCH-PCB-A2
           PERFORM HANDLE-BATCH-PCB-A3
           PERFORM HANDLE-BATCH-PCB-A4
           PERFORM HANDLE-BATCH-PCB-A5
           .                                                            
      *                                                                 
       HANDLE-BATCH-PCB-A-EX.
      *                                                                 
           EXIT.
      *
       HANDLE-BATCH-PCB-A1 SECTION.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==1==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==2==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==3==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==4==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==5==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==6==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==7==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==8==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==9==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==10==.
       HANDLE-BATCH-PCB-A1-EX.
           EXIT.
       HANDLE-BATCH-PCB-A2 SECTION.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==11==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==12==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==13==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==14==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==15==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==16==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==17==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==18==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==19==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==20==.
       HANDLE-BATCH-PCB-A2-EX.
           EXIT.
       HANDLE-BATCH-PCB-A3 SECTION.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==21==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==22==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==23==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==24==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==25==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==26==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==27==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==28==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==29==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==30==.
       HANDLE-BATCH-PCB-A3-EX.
           EXIT.
       HANDLE-BATCH-PCB-A4 SECTION.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==31==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==32==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==33==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==34==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==35==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==36==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==37==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==38==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==39==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==40==.
       HANDLE-BATCH-PCB-A4-EX.
           EXIT.
       HANDLE-BATCH-PCB-A5 SECTION.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==41==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==42==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==43==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==44==.
           COPY IRISPCBA REPLACING ==:NPCB:== BY ==45==.
       HANDLE-BATCH-PCB-A5-EX.
           EXIT.
      *
      ******************************************************************
      *    SET DIB BLOCK
      ******************************************************************
      *
       SET-DIB-BLOCK SECTION.
      *
           MOVE 'SET-DIB-BLOCK' TO WS-LAST-IORTN-SECTION
      *
           MOVE 'IR'                   TO IRIS-DIBVER
           MOVE DB-PCB-STATUS-CODE     TO IRIS-DIBSTAT
           MOVE DB-PCB-SEGMENT-NAME    TO IRIS-DIBSEGM
           MOVE DB-PCB-SEGMENT-LEVEL   TO IRIS-DIBSEGLV
           MOVE DB-PCB-FB-KEY-LENGTH   TO IRIS-DIBKFBL
           MOVE DB-PCB-DBD-NAME        TO IRIS-DIBDBDNM
           MOVE 'IRIS'                 TO IRIS-DIBDBORG
           MOVE IRIS-DIB-BLOCK         TO LK-DIB-BLOCK
           SET ADDRESS OF IRIS-LK-DIB-BLOCK TO ADDRESS OF LK-DIB-BLOCK.
      *
       SET-DIB-BLOCK-EX.
      *
           EXIT.
      *
      ******************************************************************
      *    SET LINKAGE PCBS FOR CHALLENGING TOOL
      ******************************************************************
      *
       SET-LINKAGE-PCBS SECTION.
      *
           MOVE 'SET-LINKAGE-PCBS' TO WS-LAST-IORTN-SECTION
      *
           SET ADDRESS OF IRIS-LK-CELLS TO IRIS-EXEC-DLI-PTR
           SET ADDRESS OF IRIS-PCB-PTR-1 TO 
               ADDRESS OF IRIS-LK-POINTER(1)
           SET ADDRESS OF IRIS-PCB-PTR-2   TO 
               ADDRESS OF IRIS-LK-POINTER(2)
           SET ADDRESS OF IRIS-PCB-PTR-3   TO 
               ADDRESS OF IRIS-LK-POINTER(3)
           SET ADDRESS OF IRIS-PCB-PTR-4   TO
               ADDRESS OF IRIS-LK-POINTER(4)
           SET ADDRESS OF IRIS-PCB-PTR-5   TO
               ADDRESS OF IRIS-LK-POINTER(5)
           SET ADDRESS OF IRIS-PCB-PTR-6   TO
               ADDRESS OF IRIS-LK-POINTER(6)
           SET ADDRESS OF IRIS-PCB-PTR-7   TO
               ADDRESS OF IRIS-LK-POINTER(7)
           SET ADDRESS OF IRIS-PCB-PTR-8   TO
               ADDRESS OF IRIS-LK-POINTER(8)
           SET ADDRESS OF IRIS-PCB-PTR-9   TO
               ADDRESS OF IRIS-LK-POINTER(9)
           SET ADDRESS OF IRIS-PCB-PTR-10  TO
               ADDRESS OF IRIS-LK-POINTER(10)
           SET ADDRESS OF IRIS-PCB-PTR-11  TO
               ADDRESS OF IRIS-LK-POINTER(11)
           SET ADDRESS OF IRIS-PCB-PTR-12  TO
               ADDRESS OF IRIS-LK-POINTER(12)
           SET ADDRESS OF IRIS-PCB-PTR-13  TO
               ADDRESS OF IRIS-LK-POINTER(13)
           SET ADDRESS OF IRIS-PCB-PTR-14  TO
               ADDRESS OF IRIS-LK-POINTER(14)
           SET ADDRESS OF IRIS-PCB-PTR-15  TO
               ADDRESS OF IRIS-LK-POINTER(15)
           SET ADDRESS OF IRIS-PCB-PTR-16  TO
               ADDRESS OF IRIS-LK-POINTER(16)
           SET ADDRESS OF IRIS-PCB-PTR-17  TO
               ADDRESS OF IRIS-LK-POINTER(17)
           SET ADDRESS OF IRIS-PCB-PTR-18  TO
               ADDRESS OF IRIS-LK-POINTER(18)
           SET ADDRESS OF IRIS-PCB-PTR-19  TO
               ADDRESS OF IRIS-LK-POINTER(19)
           SET ADDRESS OF IRIS-PCB-PTR-20  TO
               ADDRESS OF IRIS-LK-POINTER(10)
           SET ADDRESS OF IRIS-PCB-PTR-21  TO
               ADDRESS OF IRIS-LK-POINTER(21)
           SET ADDRESS OF IRIS-PCB-PTR-22  TO
               ADDRESS OF IRIS-LK-POINTER(22)
           SET ADDRESS OF IRIS-PCB-PTR-23  TO
               ADDRESS OF IRIS-LK-POINTER(23)
           SET ADDRESS OF IRIS-PCB-PTR-24  TO
               ADDRESS OF IRIS-LK-POINTER(24)
           SET ADDRESS OF IRIS-PCB-PTR-25  TO
               ADDRESS OF IRIS-LK-POINTER(25)
           SET ADDRESS OF IRIS-PCB-PTR-26  TO
               ADDRESS OF IRIS-LK-POINTER(26)
           SET ADDRESS OF IRIS-PCB-PTR-27  TO
               ADDRESS OF IRIS-LK-POINTER(27)
           SET ADDRESS OF IRIS-PCB-PTR-28  TO
               ADDRESS OF IRIS-LK-POINTER(28)
           SET ADDRESS OF IRIS-PCB-PTR-29  TO
               ADDRESS OF IRIS-LK-POINTER(29)
           SET ADDRESS OF IRIS-PCB-PTR-30  TO
               ADDRESS OF IRIS-LK-POINTER(30)
           SET ADDRESS OF IRIS-PCB-PTR-31  TO
               ADDRESS OF IRIS-LK-POINTER(31)
           SET ADDRESS OF IRIS-PCB-PTR-32  TO
               ADDRESS OF IRIS-LK-POINTER(32)
           SET ADDRESS OF IRIS-PCB-PTR-33  TO
               ADDRESS OF IRIS-LK-POINTER(33)
           SET ADDRESS OF IRIS-PCB-PTR-34  TO
               ADDRESS OF IRIS-LK-POINTER(34)
           SET ADDRESS OF IRIS-PCB-PTR-35  TO
               ADDRESS OF IRIS-LK-POINTER(35)
           SET ADDRESS OF IRIS-PCB-PTR-36  TO
               ADDRESS OF IRIS-LK-POINTER(36)
           SET ADDRESS OF IRIS-PCB-PTR-37  TO
               ADDRESS OF IRIS-LK-POINTER(37)
           SET ADDRESS OF IRIS-PCB-PTR-38  TO
               ADDRESS OF IRIS-LK-POINTER(38)
           SET ADDRESS OF IRIS-PCB-PTR-39  TO
               ADDRESS OF IRIS-LK-POINTER(39)
           SET ADDRESS OF IRIS-PCB-PTR-40  TO
               ADDRESS OF IRIS-LK-POINTER(40)
           SET ADDRESS OF IRIS-PCB-PTR-41  TO
               ADDRESS OF IRIS-LK-POINTER(41)
           SET ADDRESS OF IRIS-PCB-PTR-42  TO
               ADDRESS OF IRIS-LK-POINTER(42)
           SET ADDRESS OF IRIS-PCB-PTR-43  TO
               ADDRESS OF IRIS-LK-POINTER(43)
           SET ADDRESS OF IRIS-PCB-PTR-44  TO
               ADDRESS OF IRIS-LK-POINTER(44)
           SET ADDRESS OF IRIS-PCB-PTR-45  TO
               ADDRESS OF IRIS-LK-POINTER(45)
           .
      *
       SET-LINKAGE-PCBS-EX.
      *
           EXIT.
      *
      ******************************************************************
      *    EXTRACT PCB WHEN EXEC DLI CASE
      ******************************************************************
      *
       EXTRACT-PCB-EXEC SECTION.
      *
           MOVE 'EXTRACT-PCB-EXEC' TO WS-LAST-IORTN-SECTION
      *
           SET ADDRESS OF LK-DIB-BLOCK TO ADDRESS OF IRIS-LK-DIB-BLOCK
           MOVE LK-DIB-BLOCK TO IRIS-DIB-BLOCK
           IF IRIS-BLL-POINTER(1) IS NOT NULL
             SET ADDRESS OF IRIS-DB-PCB TO IRIS-BLL-POINTER(1)
           END-IF
NODUAL*    PERFORM BUILD-SSAS THRU BUILD-SSAS-EX
           .
      *
       EXTRACT-PCB-EXEC-EX.
      *
           EXIT.
