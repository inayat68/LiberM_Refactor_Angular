      *BL DYNAM,TRUNC(BIN),NOSQLCCSID
      ******************************************************************
      *
      * Licensed Materials - Property of mLogica, INC.
      * (C) Copyright mLogica 2023
      *
      ******************************************************************
      * IRIS-DB - v. 5.7.x
      ******************************************************************
      * Date: 2023/2024
      ******************************************************************
      *
      * Description: I/O ROUTINE FOR DBD: DBPAUTP0
      *
      ******************************************************************
      *
       IDENTIFICATION DIVISION.
      *
       PROGRAM-ID. DBPAUTP0.
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
           EXEC SQL INCLUDE SQLDA END-EXEC.
      *
      *    WORKING STORAGE VARIABLES
      *
       01 WS-IMS-API                    PIC X(8).
       01  WS-SQL-STM.
         49  WS-SQL-STM-LEN             PIC S9(4) COMP.
         49  WS-SQL-STM-TXT             PIC X(32000).
       01  SQL-ORDERBY-CLAUSE.
         49  SQL-ORDERBY-CLAUSE-LENGTH  PIC S9(4) COMP.
         49  SQL-ORDERBY-CLAUSE-TEXT    PIC X(32000).
       01 WS-DUAL-TRACE                 PIC 9 VALUE 0.
         88 DUAL-TRACE-ON               VALUE 0.
         88 DUAL-TRACE-OFF              VALUE 1.
      *
       01 WS-LOW-VALUES                 PIC X(256)     VALUE LOW-VALUE.
       01 WS-HIGH-VALUES                PIC X(256)     VALUE HIGH-VALUE.
       01 WS-IO-AREA                    PIC X(32000).
       01 WS-PATHCALL-AREA              PIC X(32000).
       01 WS-PATHCALL-LEN               PIC S9(4) COMP.
       01 WS-INIT-PATHCALL-LEN          PIC S9(4) COMP.
       01 WS-WHERE                      PIC X(4096).
       01 WS-WHERE-LEN                  PIC S9(4) COMP.
       01 WS-ORDERBY                    PIC X(1024).
       01 WS-ORDERBY-LEN                PIC S9(4) COMP.
       01 WS-SAVE-ROUTINE-SQLCODE       PIC S9(9) COMP.
       01 WS-SQLCODE-N                  PIC S9(5).
       01 WS-SQLCODE-E                  PIC -ZZZZ9.
       01 WS-SAVE-ROUTINE-SQLCA         PIC X(1024).
       01 WS-MESSAGE-ID-EDITED          PIC ZZZZ9.
       01 WS-MESSAGE                    PIC X(32000).
       01 WS-ERROR-DESCRIPTION          PIC X(80).
       01 WS-ERROR-DESCRIPTION-LEN      PIC S9(4) COMP.
       01 WS-INDEX                      PIC S9(4) COMP.
       01 WS-LEN                        PIC S9(4) COMP.
       01 WS-IDX                        PIC 9(3).
       01 WS-FLAG                       PIC 9 VALUE 0.
         88 WS-NOT-FOUND                VALUE 0.
         88 WS-FOUND                    VALUE 1.
       01 WS-PTR-IN-LK-IO-AREA          POINTER.
       01 WS-PTR-WS-IO-AREA             POINTER.
       01 WS-ZONED-FIELD-1              PIC 9.
       01 WS-ZONED-FIELD-2              PIC 9(2).
       01 WS-ZONED-FIELD-3              PIC 9(3).
       01 WS-ZONED-FIELD-4              PIC 9(4).
       01 WS-ZONED-FIELD-5              PIC 9(5).
       01 WS-ZONED-FIELD-6              PIC 9(6).
       01 WS-ZONED-FIELD-7              PIC 9(7).
       01 WS-ZONED-FIELD-8              PIC 9(8).
       01 WS-ZONED-FIELD-9              PIC 9(9).
       01 WS-ZONED-FIELD-10             PIC 9(10).
       01 WS-ZONED-FIELD-10-1           PIC 9(10).
       01 WS-ZONED-FIELD-10-2           PIC 9(10).
       01 WS-ZONED-FIELD-10-3           PIC 9(10).
       01 WS-ZONED-FIELD-10-4           PIC 9(10).
       01 WS-ZONED-FIELD-10-5           PIC 9(10).
       01 WS-ZONED-FIELD-10-6           PIC 9(10).
       01 WS-ZONED-FIELD-10-7           PIC 9(10).
       01 WS-ZONED-FIELD-10-8           PIC 9(10).
       01 WS-ZONED-FIELD-10-9           PIC 9(10).
       01 WS-ZONED-FIELD-10-10          PIC 9(10).
       01 WS-ZONED-FIELD-10-11          PIC 9(10).
       01 WS-ZONED-FIELD-10-12          PIC 9(10).
       01 WS-ZONED-FIELD-10-13          PIC 9(10).
       01 WS-ZONED-FIELD-10-14          PIC 9(10).
       01 WS-ZONED-FIELD-10-15          PIC 9(10).
       01 WS-ZONED-FIELD-10-16          PIC 9(10).
       01 WS-ZONED-FIELD-11             PIC 9(11).
       01 WS-ZONED-FIELD-12             PIC 9(12).
       01 WS-ZONED-FIELD-13             PIC 9(13).
       01 WS-ZONED-FIELD-14             PIC 9(14).
       01 WS-ZONED-FIELD-15             PIC 9(15).
       01 WS-ZONED-FIELD-16             PIC 9(16).
       01 WS-ZONED-FIELD-17             PIC 9(17).
       01 WS-ZONED-FIELD-18             PIC 9(18).
       01 WS-BINARY-FIELD               PIC S9(4) COMP.
       01 FILLER                        REDEFINES WS-BINARY-FIELD.
         03 WS-BINARY-FIELD-X           PIC X(2).
       01 WS-BINARY-BIG-END             PIC S9(4) COMP.
       01 WS-BINARY-BIG-END-RED         REDEFINES WS-BINARY-BIG-END.
         03 WS-BIG-END-BYTE-1           PIC X.
         03 WS-BIG-END-BYTE-2           PIC X.
       01 WS-BINARY-LIT-END             PIC S9(4) COMP.
       01 WS-BINARY-LIT-END-RED         REDEFINES WS-BINARY-LIT-END.
         03 WS-LIT-END-BYTE-1           PIC X.
         03 WS-LIT-END-BYTE-2           PIC X.
       01 WS-BINARY-BIG4-END            PIC S9(9) COMP.
       01 WS-BINARY-BIG4-END-RED        REDEFINES WS-BINARY-BIG4-END.
         03 WS-BIG4-END-BYTE-1          PIC X.
         03 WS-BIG4-END-BYTE-2          PIC X.
         03 WS-BIG4-END-BYTE-3          PIC X.
         03 WS-BIG4-END-BYTE-4          PIC X.
       01 WS-BINARY-LIT4-END            PIC S9(9) COMP.
       01 WS-BINARY-LIT4-END-RED        REDEFINES WS-BINARY-LIT4-END.
         03 WS-LIT4-END-BYTE-1          PIC X.
         03 WS-LIT4-END-BYTE-2          PIC X.
         03 WS-LIT4-END-BYTE-3          PIC X.
         03 WS-LIT4-END-BYTE-4          PIC X.
       01 WK-COMMAND-CODE               PIC S9(9) COMP.
         88 COMMAND-CODE-HERE           VALUE 0 1021.
         88 COMMAND-CODE-FIRST          VALUE 1020.
         88 COMMAND-CODE-LAST           VALUE 1019.
       01 WS-PATHCALLS                  PIC 9.
         88 HAS-NOT-PATHCALLS           VALUE 0.
         88 HAS-PATHCALLS               VALUE 1.
       01 WS-PATHCALLS-ERROR            PIC 9.
         88 HAS-PATHCALLS-ERROR         VALUE 0.
         88 HAS-PATHCALLS-NO-ERROR      VALUE 1.
       01 WS-PATHCALL-REVERSE           PIC 9.
         88 IS-NOT-PATHCALL-REVERSE     VALUE 0.
         88 IS-PATHCALL-REVERSE         VALUE 1.
       01 WS-PATHCALL-LEVEL             PIC S9(4) COMP.
       01 WS-FULL-SCAN                  PIC 9.
         88 FULL-SCAN-FALSE             VALUE 0.
         88 FULL-SCAN-TRUE              VALUE 1.
       01 WS-COMMON-FLD                    PIC X(4608).
       01 FILLER REDEFINES WS-COMMON-FLD.
         03 WS-ZONED-FLD     OCCURS 256    PIC S9(18).
       01 FILLER REDEFINES WS-COMMON-FLD.
         03 WS-ZONED-FLD-CHR OCCURS 256    PIC X(18).
       01 FILLER REDEFINES WS-COMMON-FLD.
         03 FILLER OCCURS 256.
           05 WS-COMP-FLD-2                PIC S9(4) COMP.
           05 FILLER                       PIC X(16).
       01 FILLER REDEFINES WS-COMMON-FLD.
         03 FILLER OCCURS 256.
           05 WS-COMP-FLD-4                PIC S9(9) COMP.
           05 FILLER                       PIC X(14).
       01 FILLER REDEFINES WS-COMMON-FLD.
         03 FILLER OCCURS 256.
           05 WS-COMP-FLD-8                PIC S9(18) COMP.
           05 FILLER                       PIC X(10).
      *
       01 WS-COMMON-FLD-PACKED             PIC X(2560)
                                           VALUE LOW-VALUE.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-18-00 OCCURS 256 PIC S9(18)         COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-17-01 OCCURS 256 PIC S9(17)V9       COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-16-02 OCCURS 256 PIC S9(16)V9(02)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-15-03 OCCURS 256 PIC S9(15)V9(03)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-14-04 OCCURS 256 PIC S9(14)V9(04)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-13-05 OCCURS 256 PIC S9(13)V9(05)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-12-06 OCCURS 256 PIC S9(12)V9(06)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-11-07 OCCURS 256 PIC S9(11)V9(07)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-10-08 OCCURS 256 PIC S9(10)V9(08)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-09-09 OCCURS 256 PIC S9(09)V9(09)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-08-10 OCCURS 256 PIC S9(08)V9(10)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-07-11 OCCURS 256 PIC S9(07)V9(11)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-06-12 OCCURS 256 PIC S9(06)V9(12)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-05-13 OCCURS 256 PIC S9(05)V9(13)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-04-14 OCCURS 256 PIC S9(04)V9(14)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-03-15 OCCURS 256 PIC S9(03)V9(15)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-02-16 OCCURS 256 PIC S9(02)V9(16)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-01-17 OCCURS 256 PIC S9(01)V9(17)   COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-00-18 OCCURS 256 PIC SV9(18)        COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-18-00 OCCURS 256 PIC 9(18)       COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-17-01 OCCURS 256 PIC 9(17)V9     COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-16-02 OCCURS 256 PIC 9(16)V9(02) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-15-03 OCCURS 256 PIC 9(15)V9(03) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-14-04 OCCURS 256 PIC 9(14)V9(04) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-13-05 OCCURS 256 PIC 9(13)V9(05) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-12-06 OCCURS 256 PIC 9(12)V9(06) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-11-07 OCCURS 256 PIC 9(11)V9(07) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-10-08 OCCURS 256 PIC 9(10)V9(08) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-09-09 OCCURS 256 PIC 9(09)V9(09) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-08-10 OCCURS 256 PIC 9(08)V9(10) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-07-11 OCCURS 256 PIC 9(07)V9(11) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-06-12 OCCURS 256 PIC 9(06)V9(12) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-05-13 OCCURS 256 PIC 9(05)V9(13) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-04-14 OCCURS 256 PIC 9(04)V9(14) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-03-15 OCCURS 256 PIC 9(03)V9(15) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-02-16 OCCURS 256 PIC 9(02)V9(16) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-01-17 OCCURS 256 PIC 9(01)V9(17) COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-NS-FLD-00-18 OCCURS 256 PIC V9(18)      COMP-3.
       01 FILLER REDEFINES WS-COMMON-FLD-PACKED.
         03 WS-PACKED-FLD-CHR      OCCURS 256         PIC X(10).
      *
       01 FILLER.
         03 WS-XZONED-18-CHR OCCURS 256               PIC X(18).
      *
       01 WS-XZONED-18                                PIC X(18).
       01 WS-NUMZONED-18-00 REDEFINES WS-XZONED-18    PIC S9(18).
       01 WS-NUMZONED-17-01 REDEFINES WS-XZONED-18    PIC S9(17)V9.
       01 WS-NUMZONED-16-02 REDEFINES WS-XZONED-18    PIC S9(16)V9(02).
       01 WS-NUMZONED-15-03 REDEFINES WS-XZONED-18    PIC S9(15)V9(03).
       01 WS-NUMZONED-14-04 REDEFINES WS-XZONED-18    PIC S9(14)V9(04).
       01 WS-NUMZONED-13-05 REDEFINES WS-XZONED-18    PIC S9(13)V9(05).
       01 WS-NUMZONED-12-06 REDEFINES WS-XZONED-18    PIC S9(12)V9(06).
       01 WS-NUMZONED-11-07 REDEFINES WS-XZONED-18    PIC S9(11)V9(07).
       01 WS-NUMZONED-10-08 REDEFINES WS-XZONED-18    PIC S9(10)V9(08).
       01 WS-NUMZONED-09-09 REDEFINES WS-XZONED-18    PIC S9(09)V9(09).
       01 WS-NUMZONED-08-10 REDEFINES WS-XZONED-18    PIC S9(08)V9(10).
       01 WS-NUMZONED-07-11 REDEFINES WS-XZONED-18    PIC S9(07)V9(11).
       01 WS-NUMZONED-06-12 REDEFINES WS-XZONED-18    PIC S9(06)V9(12).
       01 WS-NUMZONED-05-13 REDEFINES WS-XZONED-18    PIC S9(05)V9(13).
       01 WS-NUMZONED-04-14 REDEFINES WS-XZONED-18    PIC S9(04)V9(14).
       01 WS-NUMZONED-03-15 REDEFINES WS-XZONED-18    PIC S9(03)V9(15).
       01 WS-NUMZONED-02-16 REDEFINES WS-XZONED-18    PIC S9(02)V9(16).
       01 WS-NUMZONED-01-17 REDEFINES WS-XZONED-18    PIC S9(01)V9(17).
       01 WS-NUMZONED-00-18 REDEFINES WS-XZONED-18    PIC SV9(18).
       01 WS-NUMZONED-NS-18-00 REDEFINES WS-XZONED-18 PIC 9(18).
       01 WS-NUMZONED-NS-17-01 REDEFINES WS-XZONED-18 PIC 9(17)V9.
       01 WS-NUMZONED-NS-16-02 REDEFINES WS-XZONED-18 PIC 9(16)V9(02).
       01 WS-NUMZONED-NS-15-03 REDEFINES WS-XZONED-18 PIC 9(15)V9(03).
       01 WS-NUMZONED-NS-14-04 REDEFINES WS-XZONED-18 PIC 9(14)V9(04).
       01 WS-NUMZONED-NS-13-05 REDEFINES WS-XZONED-18 PIC 9(13)V9(05).
       01 WS-NUMZONED-NS-12-06 REDEFINES WS-XZONED-18 PIC 9(12)V9(06).
       01 WS-NUMZONED-NS-11-07 REDEFINES WS-XZONED-18 PIC 9(11)V9(07).
       01 WS-NUMZONED-NS-10-08 REDEFINES WS-XZONED-18 PIC 9(10)V9(08).
       01 WS-NUMZONED-NS-09-09 REDEFINES WS-XZONED-18 PIC 9(09)V9(09).
       01 WS-NUMZONED-NS-08-10 REDEFINES WS-XZONED-18 PIC 9(08)V9(10).
       01 WS-NUMZONED-NS-07-11 REDEFINES WS-XZONED-18 PIC 9(07)V9(11).
       01 WS-NUMZONED-NS-06-12 REDEFINES WS-XZONED-18 PIC 9(06)V9(12).
       01 WS-NUMZONED-NS-05-13 REDEFINES WS-XZONED-18 PIC 9(05)V9(13).
       01 WS-NUMZONED-NS-04-14 REDEFINES WS-XZONED-18 PIC 9(04)V9(14).
       01 WS-NUMZONED-NS-03-15 REDEFINES WS-XZONED-18 PIC 9(03)V9(15).
       01 WS-NUMZONED-NS-02-16 REDEFINES WS-XZONED-18 PIC 9(02)V9(16).
       01 WS-NUMZONED-NS-01-17 REDEFINES WS-XZONED-18 PIC 9(01)V9(17).
       01 WS-NUMZONED-NS-00-18 REDEFINES WS-XZONED-18 PIC V9(18).
      *
      *    SEGMENTS CONCATENATED KEYS
      *
       01 WS-CK-POS                     PIC S9(4) COMP.
       01 WS-CK-LEN                     PIC S9(4) COMP.
       01 PAUTSUM0-CONCATENATED-KEY     PIC X(4096).
       01 PAUTDTL1-CONCATENATED-KEY     PIC X(4096).
      *
      *    SEGMENTS INFO
      *
       01 PAUTSUM0-LEN                  PIC S9(9) COMP VALUE 100.
       01 PAUTDTL1-LEN                  PIC S9(9) COMP VALUE 200.
       01 PAUTSUM0-LVL                  PIC S9(4) COMP VALUE 1.
       01 PAUTDTL1-LVL                  PIC S9(4) COMP VALUE 2.
       01 WS-SEGMENTS-MAX-LVL           PIC S9(4) COMP VALUE 2.
       01 SEGMENTS-LAST-AREA.
         03 PAUTSUM0-LAST-AREA               PIC X(100).
         03 PAUTDTL1-LAST-AREA               PIC X(200).
       01 WS-SEGMENTS-AREA.
         03 PAUTSUM0-AREA               PIC X(100).
         03 PAUTDTL1-AREA               PIC X(200).
       01 WS-IMS-GU                     PIC X(4)  VALUE 'GU  '.
       01 WS-IMS-GN                     PIC X(4)  VALUE 'GN  '.
       01 WS-IMS-GNP                    PIC X(4)  VALUE 'GNP '.
       01 PAUTSUM0-SSA.
         03  FILLER                   PIC X(19)
             VALUE 'PAUTSUM0(ACCNTID ='.
         03  PAUTSUM0-KEY                PIC X(6).
         03  FILLER                   PIC X(2)  VALUE ') '.
       01 PAUTSUM0-SSA-UNQ.
         03  FILLER                   PIC X(9)
             VALUE 'PAUTSUM0 '.
       01 PAUTDTL1-SSA.
         03  FILLER                   PIC X(19)
             VALUE 'PAUTDTL1(PAUT9CTS='.
         03  PAUTDTL1-KEY                PIC X(8).
         03  FILLER                   PIC X(2)  VALUE ') '.
       01 PAUTDTL1-SSA-UNQ.
         03  FILLER                   PIC X(9)
             VALUE 'PAUTDTL1 '.
      *
      *    ACCESS INFO
      *
       01 WS-SEGMENT-NAME               PIC X(8)  VALUE SPACE.
       01 WS-SEGMENT-LEVEL              PIC 9(2)  VALUE ZERO.
       01 WS-SEGMENT-LEN                PIC S9(4) COMP VALUE ZERO.
       01 WS-SAVE-SEGM-LEN              PIC S9(4) COMP VALUE ZERO.
       01 WS-FUNCTION-HOLD-CLAUSE       PIC 9     VALUE ZERO.
         88 COMMAND-WITHOUT-HOLD        VALUE 0.
         88 COMMAND-WITH-HOLD           VALUE 1.
       01 SQL-CONDITION-CLAUSE.
         05 SQL-CONDITION-CLAUSE-LENGTH PIC S9(4) COMP.
         05 SQL-CONDITION-CLAUSE-TEXT   PIC X(32000).
       01 SQL-JOIN-CLAUSE.
         05 SQL-JOIN-CLAUSE-LENGTH      PIC S9(4) COMP.
         05 SQL-JOIN-CLAUSE-TEXT        PIC X(32000).
      *
      *    STORE LAST VALID ACCESS INFO
      *
       01 WS-FB-KEY-LENGTH              PIC S9(9) COMP.
       01 WS-FB-KEY-AREA                PIC X(32000).
       01 WS-DBD-NAME                   PIC X(8)  VALUE 'DBPAUTP0'.
       01 WS-LAST-SEGMENT-NAME          PIC X(8).
       01 WS-LAST-SEGMENT-LEVEL         PIC S9(9) COMP.
       01 WS-INIT-SEGMENT-LEVEL         PIC S9(4) COMP.
       01 WS-LAST-IORTN-SECTION         PIC X(30).
      * NEXT SEGMENT TO READ IN AN UNQUALIFIED GN
       01 WS-NEXT-SEGMENT-NAME          PIC X(8) VALUE SPACE.
       01 WS-NEXT-FUNCTION              PIC X(20) VALUE SPACE.
       01 FILLER                        PIC X VALUE SPACE.
         88 EXEC-DLI-ACCESS             VALUE '0'.
         88 SKIP-DLI-ACCESS             VALUE '1'.
       01 FILLER                        PIC X VALUE SPACE.
         88 EXEC-SEGMENT-ACCESS         VALUE '0'.
         88 SKIP-SEGMENT-ACCESS         VALUE '1'.
       01 FILLER                        PIC X VALUE SPACE.
         88 EXEC-SAVE-AREA              VALUE '0'.
         88 SKIP-SAVE-AREA              VALUE '1'.
       01 FILLER                        PIC X VALUE SPACE.
         88 EXEC-CHECK-MISMATCH         VALUE '0'.
         88 SKIP-CHECK-MISMATCH         VALUE '1'.
       01 FILLER                        PIC X VALUE SPACE.
         88 DEFAULT-DYNAMIC-ACCESS      VALUE '0'.
         88 CUSTOM-DYNAMIC-ACCESS       VALUE '1'.
       01 FILLER                        PIC X VALUE SPACE.
         88 CUSTOM-STATIC-ACCESS        VALUE '0'.
         88 CUSTOM-CURSOR-ACCESS        VALUE '1'.
       01 WS-DBD-STATUS                 PIC S9(4) COMP.
         88 WS-KEEP-AS-IS               VALUE 0.
         88 WS-REVERT-TO-SQL-ONLY       VALUE 1.
       01 WS-STATUS-CODE                PIC X(2) VALUE SPACE.
          88 WS-PCB-MISMATCH            VALUE 'XY'.
          88 WS-AREA-MISMATCH           VALUE 'XZ'.
       01 WS-SSA-ALIAS.
         03 WS-FUNC-ALIAS               PIC S9(9) COMP.
         03 FILLER                      PIC 9.
           88 IS-NOT-ALIAS              VALUE 0.
           88 IS-ALIAS                  VALUE 1.
      *
       01 IRIS-CUSTOM-FUNC-FOUND        PIC X(1) VALUE 'Y'.
           88  ACCESS-SEARCH            VALUE 'Y'.
           88  ACCESS-NOT-FOUND         VALUE 'N'.
      *
      *    SSA ACCESS DICTIONARY
      *
       01 MEM-SSA-KEYS.
         03 FILLER                      PIC S9(4) COMP VALUE 11.
         03 FILLER                      PIC S9(9) COMP VALUE 1.
         03 FILLER                      PIC S9(4) COMP VALUE 11.
         03 FILLER                      PIC X(120)     VALUE
            'G:PAUTSUM0:'.
         03 FILLER                      PIC X(480)     VALUE SPACE.
         03 FILLER                      PIC S9(9) COMP VALUE 11.
         03 FILLER                      PIC S9(4) COMP VALUE 30.
         03 FILLER                      PIC X(120)     VALUE
            'I:PAUTSUM0:ACCNTID:=:PAUTDTL1:'.
         03 FILLER                      PIC X(480)     VALUE SPACE.
         03 FILLER                      PIC S9(9) COMP VALUE 8.
         03 FILLER                      PIC S9(4) COMP VALUE 21.
         03 FILLER                      PIC X(120)     VALUE
            'G:PAUTSUM0:ACCNTID:=:'.
         03 FILLER                      PIC X(480)     VALUE SPACE.
         03 FILLER                      PIC S9(9) COMP VALUE 2.
         03 FILLER                      PIC S9(4) COMP VALUE 12.
         03 FILLER                      PIC X(120)     VALUE
            'GP:PAUTDTL1:'.
         03 FILLER                      PIC X(480)     VALUE SPACE.
         03 FILLER                      PIC S9(9) COMP VALUE 4.
         03 FILLER                      PIC S9(4) COMP VALUE 11.
         03 FILLER                      PIC X(120)     VALUE
            'D:PAUTSUM0:'.
         03 FILLER                      PIC X(480)     VALUE SPACE.
         03 FILLER                      PIC S9(9) COMP VALUE 9.
         03 FILLER                      PIC S9(4) COMP VALUE 11.
         03 FILLER                      PIC X(120)     VALUE
            'R:PAUTSUM0:'.
         03 FILLER                      PIC X(480)     VALUE SPACE.
         03 FILLER                      PIC S9(9) COMP VALUE 12.
         03 FILLER                      PIC S9(4) COMP VALUE 23.
         03 FILLER                      PIC X(120)     VALUE
            'GP:PAUTDTL1:PAUT9CTS:=:'.
         03 FILLER                      PIC X(480)     VALUE SPACE.
         03 FILLER                      PIC S9(9) COMP VALUE 16.
         03 FILLER                      PIC S9(4) COMP VALUE 11.
         03 FILLER                      PIC X(120)     VALUE
            'I:PAUTDTL1:'.
         03 FILLER                      PIC X(480)     VALUE SPACE.
         03 FILLER                      PIC S9(9) COMP VALUE 3.
         03 FILLER                      PIC S9(4) COMP VALUE 11.
         03 FILLER                      PIC X(120)     VALUE
            'D:PAUTDTL1:'.
         03 FILLER                      PIC X(480)     VALUE SPACE.
         03 FILLER                      PIC S9(9) COMP VALUE 10.
         03 FILLER                      PIC S9(4) COMP VALUE 11.
         03 FILLER                      PIC X(120)     VALUE
            'I:PAUTSUM0:'.
         03 FILLER                      PIC X(480)     VALUE SPACE.
         03 FILLER                      PIC S9(9) COMP VALUE 13.
         03 FILLER                      PIC S9(4) COMP VALUE 11.
         03 FILLER                      PIC X(120)     VALUE
            'R:PAUTDTL1:'.
         03 FILLER                      PIC X(480)     VALUE SPACE.
       01 TAB-MEM-SSA-KEYS REDEFINES MEM-SSA-KEYS.
         03 TAB-MEM-SSA-COUNT           PIC S9(4) COMP.
         03 FILLER                      OCCURS 11.
           05 TAB-MEM-SSA-FUNCID        PIC S9(9) COMP.
           05 TAB-MEM-SSA-LEN           PIC S9(4) COMP.
           05 TAB-MEM-SSA-TXT           PIC X(600).
      *
      * HOST VARIABLES
      *
       01 WS-HV-001-LEN008-X       PIC X(8).
       01 WS-HV-002-LEN006-P       PIC S9(11) COMP-3 VALUE ZERO.
       01 WS-HV-003-LEN008-X       PIC X(8).
      *
       01 IRIS-PCBS-AREA.
         03 IRIS-PCBS-TAB-IO            OCCURS 45.                      
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
           05 FILLER                    PIC X(377).
      * 33000 BYTES
           05 FILLER                    PIC X(33000).
      *
      *    SEGMENTS DCLGENs INCLUSION
      *
           EXEC SQL INCLUDE PAUTSUM0 END-EXEC.
           EXEC SQL INCLUDE PAUTDTL1 END-EXEC.
      *
       LINKAGE SECTION.
      *
      *    IRIS GLOBAL AREA
      *
           COPY IRISGLOB REPLACING ==:PROGNM:== BY =='DBPAUTP0'==.
      *
      *    IRIS PCB AREA
      *
           COPY IRISPCB.
      *
      *    PCB INFO FOR THE SEGMENT BEING ACCESSED
      *
       03 IO-RTN-USED-KEYS-PCB-AREA REDEFINES DB-RNT-CNTL-AREA.
         05 IO-RTN-USED-KEYS-AREA.
           07 IO-RTN-FB-KEY-MAX-LENGTH           PIC S9(9) COMP.
           07 IO-RTN-USED-KEY-SECONDARY          PIC X(255).
           07 IO-RTN-USED-KEY-GNUNQ-NEXT-SEG     PIC X(8).
           07 IO-RTN-USED-KEY-GNUNQ-SAVE-POS     PIC X(512).
           07 IO-RTN-USED-KEY-GNUNQ-NEXT-ACT REDEFINES
              IO-RTN-USED-KEY-GNUNQ-SAVE-POS       PIC X(20).
             88 IO-RTN-USED-KEY-GNUNQ-SEEK
                VALUE 'SQL-SELECT-SEEK     '.
             88 IO-RTN-USED-KEY-GNUNQ-NEXT
                VALUE 'SQL-SELECT-NEXT     '.
           07 RUN-DBD-STATUS                     PIC S9(4) COMP.
             88 DUAL-IMS-ONLY                    VALUE 1.
             88 DUAL-SQL-ONLY                    VALUE 2.
             88 DUAL-BOTH                        VALUE 3 6.
             88 DUAL-UPDATE-ONLY                 VALUE 4.
             88 DUAL-BOTH-IMS-PRIMARY            VALUE 5 7.
             88 DUAL-BOTH-NO-ABEND               VALUE 6.
             88 DUAL-BOTH-IMS-PR-NO-ABEND        VALUE 7.
           07 RUN-IMS-DUAL-POINTER               POINTER.
           07 RUN-PCB-INDEX                      PIC 9(3).
           07 RUN-IO-AREA-PTR                    USAGE POINTER.
           07 INIT-DBD-STATUS                    PIC S9(4) COMP.
             88 INIT-DUAL-IMS-ONLY               VALUE 1.
             88 INIT-DUAL-SQL-ONLY               VALUE 2.
             88 INIT-DUAL-BOTH                   VALUE 3 6.
             88 INIT-DUAL-UPDATE-ONLY            VALUE 4.
             88 INIT-DUAL-BOTH-IMS-PRIMARY       VALUE 5 7.
             88 INIT-DUAL-BOTH-NO-ABEND          VALUE 6.
             88 INIT-DUAL-BOTH-IMS-PR-NO-ABEND   VALUE 7.
           07 RUN-LAST-DYN-SSA-FUNCID            PIC S9(9) COMP.
           07 RUN-CHECK-MISMATCH                 PIC X.
             88 RUN-EXEC-CHECK-MISMATCH          VALUE '0'.
             88 RUN-SKIP-CHECK-MISMATCH          VALUE '1'.
           07 RUN-DLI-ACCESS                     PIC X.
             88 RUN-EXEC-DLI-ACCESS              VALUE '0'.
             88 RUN-SKIP-DLI-ACCESS              VALUE '1'.
           07 FILLER                             PIC X(200).
      *
           07 IO-RTN-USED-KEY-VALUE OCCURS 10.
             09 IO-RTN-USED-SSA-INFO             PIC X(1055).
             09 IO-RTN-USED-SSA-INPUT-KEYS       REDEFINES
                IO-RTN-USED-SSA-INFO.
               11 IO-RTN-IRIS-KEYVALUES          OCCURS 16.
                 13 IO-RTN-IRIS-KEYVALUE         PIC X(80).
SAN   *          13 IO-RTN-IRIS-KEYVALUE-PACKED  REDEFINES
SAN   *             IO-RTN-IRIS-KEYVALUE         PIC S9(18) COMP-3.
SAN          09 IO-RTN-USED-SSA-INPUT-KEYS-2     REDEFINES
SAN             IO-RTN-USED-SSA-INFO.
SAN            11 IO-RTN-IRIS-KEYVALUES-P        OCCURS 16.
SAN              13 IO-RTN-IRIS-KEYVALUE-PACKED  PIC S9(18) COMP-3.
SAN              13 FILLER                       PIC X(60).
             09 IO-RTN-USED-KEY-STATUS           PIC X.
               88 IO-RTN-USED-KEY-NOT-CHANGED    VALUE '0' X'00' ' '.
               88 IO-RTN-USED-KEY-CHANGED        VALUE '1'.
               88 IO-RTN-USED-KEY-IS-INDEX       VALUE '2'.
               88 IO-RTN-USED-KEY-DUPREC         VALUE '3'.
             09 IO-RTN-USED-SSA-KEYS.
               11 IO-RTN-USED-KEY-ALPHA          PIC X(256).
SAN   *        11 IO-RTN-USED-KEY-PACKED REDEFINES IO-RTN-USED-KEY-ALPHA
SAN   *                                          PIC S9(18) COMP-3.
               11 IO-RTN-USED-KEY-NUMERIC        PIC S9(9) COMP.
               11 IO-RTN-USED-KEY-NUMERIC-PREV   PIC S9(9) COMP.
               11 IO-RTN-USED-KEY-NUMERIC-NEXT   PIC S9(9) COMP.
               11 IO-RTN-USED-KEY-PARENT-ALPHA   PIC X(256).
               11 IO-RTN-USED-KEY-PARENT-NUMERIC PIC S9(9) COMP.
      *
      *   SYSTEM FIELDS STORAGE
      *
               11 FILLER OCCURS 32.
                 13 IO-RTN-USED-KEY-LAST-SX      PIC S9(9) COMP.
               11 IO-RTN-USED-LAST-SEGMENT       PIC X(8).
               11 IO-RTN-LAST-OPEN-CURSOR        PIC 9(8).
               11 FILLER                         PIC X(272).
SAN          09 IO-RTN-USED-SSA-KEYS2 REDEFINES IO-RTN-USED-SSA-KEYS.
SAN            11 IO-RTN-USED-KEY-PACKED         PIC S9(18) COMP-3.
      *  16K AVAILABLE
         05 IO-RTN-COMM-DATA                     PIC X(16000).
         05 FILLER REDEFINES IO-RTN-COMM-DATA.
           07 LAST-IMS-FUNCTION                  PIC X(4).
             88 LAST-IMS-FUNCTION-READ           VALUE 'GU  '
                                                       'GN  '
                                                       'GNP '.
             88 LAST-IMS-FUNCTION-READHLD        VALUE 'GHU '
                                                       'GHN '
                                                       'GHNP'.
           07 LAST-IMS-SEGMENT-NAME              PIC X(8).
           07 LAST-IMS-SEGMENT-LEVEL             PIC 9(9) COMP.
           07 LAST-SSA.
             09 LAST-SSA-SEGMENT                 PIC X(8).
             09 LAST-SSA-DATA                    PIC X(248).
           07 FILLER REDEFINES LAST-SSA          PIC X(256).
             88 LAST-SSA-EMPTY                   VALUE SPACE
                                                       LOW-VALUE
                                                       HIGH-VALUE.
           07 LAST-IMS-CCODE                     PIC X(240).
      *
      *    I-O USER PROGRAM AREA
       01 LK-IO-AREA                    PIC X(32000).
       01 LK-LOAD-DICTIONARY-AREA REDEFINES LK-IO-AREA.
         03 LK-LOAD-DICTIONARY-PTR      POINTER.
      *
      *    POSSIBLE SSAs
      *
       01 LK-SSA-01                     PIC X(32000).
       01 LK-SSA-02                     PIC X(32000).
       01 LK-SSA-03                     PIC X(32000).
       01 LK-SSA-04                     PIC X(32000).
       01 LK-SSA-05                     PIC X(32000).
       01 LK-SSA-06                     PIC X(32000).
       01 LK-SSA-07                     PIC X(32000).
       01 LK-SSA-08                     PIC X(32000).
       01 LK-SSA-09                     PIC X(32000).
       01 LK-SSA-10                     PIC X(32000).
       01 LK-SSA-11                     PIC X(32000).
       01 LK-SSA-12                     PIC X(32000).
       01 LK-SSA-13                     PIC X(32000).
       01 LK-SSA-14                     PIC X(32000).
       01 LK-SSA-15                     PIC X(32000).
       01 LK-SSA-16                     PIC X(32000).
      *
       01 LK-DUAL-PCB.
         03 DUAL-PCB-FIXED-PART.
           05 DUAL-DBD-NAME             PIC X(8).
           05 DUAL-SEGMENT-LEVEL        PIC 9(2).
           05 DUAL-STATUS-CODE          PIC X(2).
             88 DUAL-STATUS-CODE-OK     VALUE SPACE.
           05 DUAL-PROC-OPTS            PIC X(4).
           05 DUAL-INTERNAL-INDEX       PIC S9(9)  COMP.
           05 DUAL-SEGMENT-NAME         PIC X(8).
           05 DUAL-FB-KEY-LENGTH        PIC S9(9)  COMP.
           05 DUAL-NUM-SENSEGS          PIC S9(9)  COMP.
         03 DUAL-KEY-FB                 PIC X.
      *
      * POINTERS TO IRIS PCB
      *
       01 IRIS-LK-CELLS.
         03 FILLER                      OCCURS 100.
           05 IRIS-LK-POINTER           POINTER.
      *
      *    DIB BLOCK FOR EXEC DLI
      *
       01 LK-DIB-BLOCK                  PIC X(32).
      *
       01 LK-IO-AREA-BACKUP             PIC X(32000).
      *
       PROCEDURE DIVISION USING IRIS-WORK-AREA
                                IRIS-DB-PCB
                                LK-IO-AREA
                                LK-SSA-01
                                LK-SSA-02
                                LK-SSA-03
                                LK-SSA-04
                                LK-SSA-05
                                LK-SSA-06
                                LK-SSA-07
                                LK-SSA-08
                                LK-SSA-09
                                LK-SSA-10
                                LK-SSA-11
                                LK-SSA-12
                                LK-SSA-13
                                LK-SSA-14
                                LK-SSA-15
                                LK-SSA-16.
      *
       MAIN-PROGRAM.
      *
           IF IRIS-EXECDLI
             PERFORM EXTRACT-PCB-EXEC
           END-IF
      *
           IF IRIS-TRACE-MINIMAL
           OR IRIS-TRACE-STANDARD
             MOVE 1 TO IRIS-MSG-LEN
             STRING 'PGM=(' IRIS-PROGRAM-NAME ')'
                    ' CUSTOM FUNCTION ID=(' IRIS-CUSTOM-FUNC-ID ')'
                    ' CALLER ID=(' IRIS-CALL-ID ')'
             MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
             POINTER IRIS-MSG-LEN
             MOVE 0 TO IRIS-MSG-LEN
             SET IRISTRAC-RTN TO TRUE
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-TRACE-FULL
           OR IRIS-TRACE-PERFORMANCE
             MOVE 1 TO IRIS-MSG-LEN
             STRING '<IRISTRACE> - DBPAUTP0:START' NL
                    ' DBD        =(DBPAUTP0) ' NL
                    ' CALLER PGM =(' IRIS-PROGRAM-NAME ') ' NL
                    ' CALLER ID  =(' IRIS-CALL-ID ') ' NL
             DELIMITED BY SIZE INTO IRIS-MSG-TXT
             POINTER IRIS-MSG-LEN
             IF DB-PCB-IRIS-EYE
               STRING ' PCB INDEX  =(' RUN-PCB-INDEX ') ' NL
               DELIMITED BY SIZE INTO IRIS-MSG-TXT
               POINTER IRIS-MSG-LEN
             END-IF
             STRING ' IMS FUNC   =(' IRIS-IMS-FUNCTION ') ' NL
                    ' SEGMENT    =(' IRIS-SEGMENT ') ' NL
                    ' FUNCTION   =(' IRIS-FUNCTION ') ' NL
                    ' CUSTOM ID  =(' IRIS-CUSTOM-FUNC-ID ') '
             MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
             POINTER IRIS-MSG-LEN
             MOVE 0 TO IRIS-MSG-LEN
             SET IRISTRAC-RTN TO TRUE
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
             IF NOT IRIS-EXECDLI
               PERFORM PRINT-SSAS
             END-IF
           END-IF
      *
           PERFORM INIT-VARIABLES
      *
           EVALUATE TRUE
           WHEN SQL-USER-CUSTOM
      *
             IF IRIS-TRACE-PERFORMANCE
               MOVE 0 TO IRIS-MSG-LEN
               STRING '<IRISTRACE> - BEFORE SQL ACCESS'
               MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
               SET IRISTRAC-RTN TO TRUE
               CALL IRIS-WS-RTN USING IRIS-WORK-AREA
             END-IF
           EVALUATE IRIS-CUSTOM-FUNC-ID
           WHEN 0
             SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
      * IRISDB - Segm: PAUTSUM0, Op: GN, RevId: 1
           WHEN 1
             IF IRIS-FUNC-GU OR IRIS-FUNC-GHU
             OR WS-SEGMENT-NAME NOT =
               IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
             OR IRIS-CODE-FIRST(PAUTSUM0-LVL)
               SET SQL-SELECT-SEEK TO TRUE
             ELSE
               SET SQL-SELECT-NEXT TO TRUE
             END-IF
             IF IO-RTN-USED-KEY-DUPREC(PAUTSUM0-LVL)
             AND (IRIS-FUNC-GN OR IRIS-FUNC-GHN
             OR IRIS-FUNC-GNP OR IRIS-FUNC-GHNP)
               SET SQL-SELECT-PRIMARY TO TRUE
               SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
               MOVE ZERO TO IO-RTN-LAST-OPEN-CURSOR(PAUTSUM0-LVL)
             END-IF
      * IRISDB - Segm: PAUTDTL1, Op: GNP, RevId: 2
           WHEN 2
             IF IRIS-FUNC-GU OR IRIS-FUNC-GHU
             OR WS-SEGMENT-NAME NOT =
               IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
             OR IRIS-CODE-FIRST(PAUTDTL1-LVL)
               SET SQL-SELECT-SEEK TO TRUE
             ELSE
               SET SQL-SELECT-NEXT TO TRUE
             END-IF
             IF IO-RTN-USED-KEY-DUPREC(PAUTDTL1-LVL)
             AND (IRIS-FUNC-GN OR IRIS-FUNC-GHN
             OR IRIS-FUNC-GNP OR IRIS-FUNC-GHNP)
               SET SQL-SELECT-PRIMARY TO TRUE
               SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
               MOVE ZERO TO IO-RTN-LAST-OPEN-CURSOR(PAUTDTL1-LVL)
             END-IF
      * IRISDB - Segm: PAUTDTL1, Op: DLET, RevId: 3
           WHEN 3
             SET SQL-DELETE TO TRUE
      * IRISDB - Segm: PAUTSUM0, Op: DLET, RevId: 4
           WHEN 4
             SET SQL-DELETE TO TRUE
      * IRISDB - Segm: PAUTSUM0, Op: GU, RevId: 8
           WHEN 8
             IF IRIS-FUNC-GU OR IRIS-FUNC-GHU
             OR WS-SEGMENT-NAME NOT =
               IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
             OR IRIS-CODE-FIRST(PAUTSUM0-LVL)
             OR IO-RTN-USED-KEY-DUPREC(PAUTSUM0-LVL)
             OR IRIS-KEYVALUE(1, 1)(5:6) NOT =
               IO-RTN-IRIS-KEYVALUE(1, 1)(5:6)
               MOVE IRIS-KEYVALUE(1, 1)(5:6) TO
               IO-RTN-IRIS-KEYVALUE(1, 1)(5:6)
               MOVE IRIS-KEYVALUE(1, 1)(5:6) TO
                      IO-RTN-USED-KEY-ALPHA(1)(1:6)
               SET SQL-SELECT-PRIMARY TO TRUE
             ELSE
               SET SKIP-SEGMENT-ACCESS TO TRUE
               SET IRIS-SQL-NOT-FOUND TO TRUE
               MOVE IRIS-DB-SQLCODE TO IRIS-SQLCODE
               MOVE SPACE TO WS-LAST-SEGMENT-NAME
               MOVE ZERO TO WS-LAST-SEGMENT-LEVEL
               GO TO SKIP-FUNCTION
             END-IF
      * IRISDB - Segm: PAUTSUM0, Op: REPL, RevId: 9
           WHEN 9
             IF IRIS-FUNC-GU OR IRIS-FUNC-GHU
             OR WS-SEGMENT-NAME NOT =
               IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
             OR IRIS-CODE-FIRST(PAUTSUM0-LVL)
               SET SQL-SELECT-SEEK TO TRUE
             ELSE
               SET SQL-SELECT-NEXT TO TRUE
             END-IF
             IF IO-RTN-USED-KEY-DUPREC(PAUTSUM0-LVL)
             AND (IRIS-FUNC-GN OR IRIS-FUNC-GHN
             OR IRIS-FUNC-GNP OR IRIS-FUNC-GHNP)
               SET SQL-SELECT-PRIMARY TO TRUE
               SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
               MOVE ZERO TO IO-RTN-LAST-OPEN-CURSOR(PAUTSUM0-LVL)
             END-IF
             SET SQL-UPDATE TO TRUE
      * IRISDB - Segm: PAUTSUM0, Op: ISRT, RevId: 10
           WHEN 10
             SET SQL-INSERT TO TRUE
      * IRISDB - Segm: PAUTDTL1, Op: ISRT, RevId: 11
           WHEN 11
             MOVE IRIS-KEYVALUE(1, 1)(5:6) TO
                    IO-RTN-USED-KEY-ALPHA(1)(1:6)
             SET SQL-INSERT TO TRUE
      * IRISDB - Segm: PAUTDTL1, Op: GNP, RevId: 12
           WHEN 12
             MOVE IRIS-KEYVALUE(1, 1)(1:8) TO WS-HV-001-LEN008-X
             IF IRIS-KEYVALUE(1, 1)(1:8) NOT =
               IO-RTN-IRIS-KEYVALUE(1, 1)(1:8)
               MOVE ZERO TO IO-RTN-LAST-OPEN-CURSOR(2)
               MOVE IRIS-KEYVALUE(1, 1)(1:8) TO
                      IO-RTN-IRIS-KEYVALUE(1, 1)(1:8)
             END-IF
             MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6) TO
                    WS-PACKED-FLD-CHR(1)(5:6)
             IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
               MOVE ZERO TO WS-HV-002-LEN006-P
             ELSE
               MOVE WS-PACKED-FLD-18-00(1) TO WS-HV-002-LEN006-P
             END-IF
             MOVE IO-RTN-USED-KEY-ALPHA(2)(1:8) TO WS-HV-003-LEN008-X
             SET SQL-SELECT-STATIC TO TRUE
             SET CUSTOM-STATIC-ACCESS TO TRUE
             IF IO-RTN-USED-KEY-DUPREC(PAUTDTL1-LVL)
             AND (IRIS-FUNC-GN OR IRIS-FUNC-GHN
             OR IRIS-FUNC-GNP OR IRIS-FUNC-GHNP)
               SET SQL-SELECT-PRIMARY TO TRUE
               SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
               MOVE ZERO TO IO-RTN-LAST-OPEN-CURSOR(PAUTDTL1-LVL)
             END-IF
      * IRISDB - Segm: PAUTDTL1, Op: REPL, RevId: 13
           WHEN 13
             SET SQL-UPDATE TO TRUE
      * IRISDB - Segm: PAUTDTL1, Op: ISRT, RevId: 16
           WHEN 16
             SET SQL-INSERT TO TRUE
           WHEN OTHER
             SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
           END-EVALUATE
           END-EVALUATE.
      *
       SKIP-FUNCTION.
      *
           IF IRIS-NO-ERROR
           AND NOT SKIP-SEGMENT-ACCESS
             EVALUATE WS-SEGMENT-NAME
             WHEN 'PAUTSUM0'
      *
               PERFORM HANDLE-SEGMENT-PAUTSUM0
      *
             WHEN 'PAUTDTL1'
      *
               PERFORM HANDLE-SEGMENT-PAUTDTL1
      *
             WHEN OTHER
               SET IRIS-ERR-RTN-SEGMENT-NOT-FOUND TO TRUE
             END-EVALUATE
      *
           END-IF
           IF IRIS-TRACE-PERFORMANCE
             MOVE 0 TO IRIS-MSG-LEN
             STRING '<IRISTRACE> - AFTER  SQL ACCESS'
             MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
             SET IRISTRAC-RTN TO TRUE
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           PERFORM FINALIZE-VARIABLES
      *
           IF IRIS-TRACE-STANDARD
             MOVE 80 TO WS-LEN
             IF WS-SEGMENT-LEN < WS-LEN
               MOVE WS-SEGMENT-LEN TO WS-LEN
             ELSE
               IF IRIS-TRACE-FULL
                 MOVE WS-SEGMENT-LEN TO WS-LEN
               END-IF
             END-IF
             IF WS-LEN = ZERO
               MOVE 1 TO WS-LEN
             END-IF
             MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
             IF NOT IRIS-SQL-OK AND NOT IRIS-SQL-NOT-FOUND
               IF IRIS-SQLERRML = ZERO
                 MOVE 1 TO IRIS-SQLERRML
               END-IF
               MOVE 0 TO IRIS-MSG-LEN
               MOVE IRIS-SQLCODE TO WS-SQLCODE-N
               MOVE WS-SQLCODE-N TO WS-SQLCODE-E
               STRING '<IRISTRACE> - DBPAUTP0:SQL_RC' NL
                    ' SQLCODE    =(' WS-SQLCODE-E ')' NL
                    ' SQLERRM    =(' IRIS-SQLERRMC(1:IRIS-SQLERRML) ')'
               MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
               SET IRISTRAC-RTN TO TRUE
               CALL IRIS-WS-RTN USING IRIS-WORK-AREA
             END-IF
             MOVE 0 TO IRIS-MSG-LEN
             IF IRIS-NO-ERROR AND IRIS-TRACE-FULL
               STRING '<IRISTRACE> - DBPAUTP0:END' NL
                      ' DBD        =(DBPAUTP0) ' NL
                      ' CALLER PGM =(' IRIS-PROGRAM-NAME ') ' NL
                      ' CALLER ID  =(' IRIS-CALL-ID ') ' NL
                      ' PCB INDEX  =(' RUN-PCB-INDEX ') ' NL
                      ' IMS FUNC   =(' IRIS-IMS-FUNCTION ') ' NL
                      ' CUSTOM ID  =(' IRIS-CUSTOM-FUNC-ID ') ' NL
                      ' SEGMENT    =(' WS-SEGMENT-NAME ') ' NL
                      ' SECTION    =(' WS-LAST-IORTN-SECTION ') ' NL
                      ' PCB AREA   =(' DB-PCB-FIXED-PART ') ' NL
                      ' KFB AREA   =(' DB-PCB-KEY-FB
                                   (1:DB-PCB-FB-KEY-LENGTH) ') ' NL
                      ' I-O AREA   =(' LK-IO-AREA(1:WS-LEN)') '
               MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
               SET IRISTRAC-RTN TO TRUE
               CALL IRIS-WS-RTN USING IRIS-WORK-AREA
             ELSE
               IF NOT IRIS-NO-ERROR
                 MOVE 1 TO WS-ERROR-DESCRIPTION-LEN
                 STRING IRIS-ERROR-DESCRIPTION(IRIS-ERR-MESSAGE-ID)
                 DELIMITED BY '_'
                 INTO WS-ERROR-DESCRIPTION
                 POINTER WS-ERROR-DESCRIPTION-LEN
                 SUBTRACT 1 FROM WS-ERROR-DESCRIPTION-LEN
                 MOVE IRIS-ERR-MESSAGE-ID TO WS-MESSAGE-ID-EDITED
                 STRING '<IRISTRACE> - DBPAUTP0:END' NL
                        ' DBD        =(DBPAUTP0) ' NL
                        ' CALLER PGM =(' IRIS-PROGRAM-NAME ') ' NL
                        ' CALLER ID  =(' IRIS-CALL-ID ') ' NL
                        ' PCB INDEX  =(' RUN-PCB-INDEX ') ' NL
                        ' IMS FUNC   =(' IRIS-IMS-FUNCTION ') ' NL
                        ' CUSTOM ID  =(' IRIS-CUSTOM-FUNC-ID ') ' NL
                        ' SEGMENT    =(' WS-SEGMENT-NAME ') ' NL
                        ' SECTION    =(' WS-LAST-IORTN-SECTION ') ' NL
                        ' ERROR ID   =(' WS-MESSAGE-ID-EDITED ') ' NL
                        ' ERROR DESCR=('
                 WS-ERROR-DESCRIPTION(1:WS-ERROR-DESCRIPTION-LEN)') ' NL
                        ' PCB AREA   =(' DB-PCB-FIXED-PART ') ' NL
                        ' I-O AREA   =(' LK-IO-AREA(1:WS-LEN) ') '
                 MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
                 SET IRISTRAC-RTN TO TRUE
                 CALL IRIS-WS-RTN USING IRIS-WORK-AREA
               END-IF
             END-IF
           ELSE
      *    IN CASE OF INTERNAL ERROR EMIT ALWAYS A MESSAGE TO CONSOLE
             IF DB-STATUS-INTERNAL-NOT-HANDLED
             OR IRIS-ERR-UNHANDLED-SQLCODE
               IF IRIS-SQLERRML = ZERO
                 MOVE 1 TO IRIS-SQLERRML
               END-IF
               MOVE 1 TO WS-ERROR-DESCRIPTION-LEN
               STRING IRIS-ERROR-DESCRIPTION(IRIS-ERR-MESSAGE-ID)
               DELIMITED BY '_'
               INTO WS-ERROR-DESCRIPTION
               POINTER WS-ERROR-DESCRIPTION-LEN
               SUBTRACT 1 FROM WS-ERROR-DESCRIPTION-LEN
               MOVE 0 TO IRIS-MSG-LEN
               MOVE IRIS-SQLCODE TO WS-SQLCODE-N
               MOVE WS-SQLCODE-N TO WS-SQLCODE-E
               MOVE IRIS-ERR-MESSAGE-ID TO WS-MESSAGE-ID-EDITED
               STRING '<IRISTRACE> - DBPAUTP0:END' NL
                      ' DBD        =(DBPAUTP0) ' NL
                      ' CALLER PGM =(' IRIS-PROGRAM-NAME ') ' NL
                      ' CALLER ID  =(' IRIS-CALL-ID ') ' NL
                      ' PCB INDEX  =(' RUN-PCB-INDEX ') ' NL
                      ' IMS FUNC   =(' IRIS-IMS-FUNCTION ') ' NL
                      ' CUSTOM ID  =(' IRIS-CUSTOM-FUNC-ID ') ' NL
                      ' SEGMENT    =(' WS-SEGMENT-NAME ') ' NL
                      ' SECTION    =(' WS-LAST-IORTN-SECTION ') ' NL
                      ' ERROR ID   =(' WS-MESSAGE-ID-EDITED ') ' NL
                      ' ERROR DESCR=('
               WS-ERROR-DESCRIPTION(1:WS-ERROR-DESCRIPTION-LEN) ') ' NL
                    ' SQLCODE    =(' WS-SQLCODE-E ') ' NL
                    ' SQLERRM    =(' IRIS-SQLERRMC(1:IRIS-SQLERRML)') '
               MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
               SET IRISTRAC-RTN TO TRUE
               CALL IRIS-WS-RTN USING IRIS-WORK-AREA
             END-IF
           END-IF
           MOVE IRIS-IMS-FUNCTION TO LAST-IMS-FUNCTION
           MOVE WS-LAST-SEGMENT-NAME TO LAST-IMS-SEGMENT-NAME
           MOVE WS-LAST-SEGMENT-LEVEL TO LAST-IMS-SEGMENT-LEVEL
           MOVE IRIS-KEYVALUE-TAB(1:240) TO LAST-IMS-CCODE
      *
           IF IRIS-EXECDLI
             PERFORM SET-DIB-BLOCK
           END-IF
      *
           .
      *
       MAIN-PROGRAM-EX.
      *
           GOBACK.
      *
      ******************************************************************
      *                                                                *
      *                    P R O G R A M    E N D                      *
      *                                                                *
      ******************************************************************
      *
      ******************************************************************
      *    PRINT SSAS CONTENT
      ******************************************************************
      *
       PRINT-SSAS SECTION.
      *
           MOVE 'PRINT-SSAS' TO WS-LAST-IORTN-SECTION
      *
           IF IRIS-PARAM-NUM > 3
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-01(WS-IDX:1) = '('
             OR LK-SSA-01(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-01(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-01(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA01=(' LK-SSA-01(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 4
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-02(WS-IDX:1) = '('
             OR LK-SSA-02(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-02(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-02(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA02=(' LK-SSA-02(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 5
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-03(WS-IDX:1) = '('
             OR LK-SSA-03(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-03(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-03(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA03=(' LK-SSA-03(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 6
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-04(WS-IDX:1) = '('
             OR LK-SSA-04(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-04(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-04(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA04=(' LK-SSA-04(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 7
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-05(WS-IDX:1) = '('
             OR LK-SSA-05(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-05(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-05(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA05=(' LK-SSA-05(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 8
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-06(WS-IDX:1) = '('
             OR LK-SSA-06(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-06(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-06(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA06=(' LK-SSA-06(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 9
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-07(WS-IDX:1) = '('
             OR LK-SSA-07(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-07(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-07(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA07=(' LK-SSA-07(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 10
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-08(WS-IDX:1) = '('
             OR LK-SSA-08(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-08(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-08(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA08=(' LK-SSA-08(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 11
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-09(WS-IDX:1) = '('
             OR LK-SSA-09(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-09(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-09(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA09=(' LK-SSA-09(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 12
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-10(WS-IDX:1) = '('
             OR LK-SSA-10(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-10(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-10(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA10=(' LK-SSA-10(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 13
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-11(WS-IDX:1) = '('
             OR LK-SSA-11(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-11(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-11(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA11=(' LK-SSA-11(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 14
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-12(WS-IDX:1) = '('
             OR LK-SSA-12(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-12(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-12(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA12=(' LK-SSA-12(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 15
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-13(WS-IDX:1) = '('
             OR LK-SSA-13(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-13(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-13(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA13=(' LK-SSA-13(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 16
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-14(WS-IDX:1) = '('
             OR LK-SSA-14(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-14(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-14(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA14=(' LK-SSA-14(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 17
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-15(WS-IDX:1) = '('
             OR LK-SSA-15(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-15(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-15(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA15=(' LK-SSA-15(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           IF IRIS-PARAM-NUM > 18
             MOVE 1 TO IRIS-MSG-LEN
             MOVE SPACE TO IRIS-MSG-TXT
             PERFORM VARYING WS-IDX FROM 9 BY 1 UNTIL WS-IDX > 540
             OR LK-SSA-16(WS-IDX:1) = '('
             OR LK-SSA-16(WS-IDX:1) = ' '
               CONTINUE
             END-PERFORM
             IF LK-SSA-16(WS-IDX:1) = '('
               PERFORM VARYING WS-IDX FROM WS-IDX BY 1
               UNTIL WS-IDX > 540
               OR LK-SSA-16(WS-IDX:1) = ')'
                 CONTINUE
               END-PERFORM
             END-IF
             STRING '<IRISTRACE> - DBPAUTP0'
                                   ':SSA16=(' LK-SSA-16(1:WS-IDX) ')'
             DELIMITED BY SIZE INTO IRIS-MSG-TXT POINTER IRIS-MSG-LEN
             CALL IRIS-WS-RTN USING IRIS-WORK-AREA
           END-IF
      *
           .
      *
       PRINT-SSAS-EX.
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
           SET WS-NOT-FOUND TO TRUE
      *    IF IRIS-EXEC-DLI-PTR IS NOT NULL
      *      SET ADDRESS OF IRIS-LK-CELLS TO IRIS-EXEC-DLI-PTR
      *    END-IF
           SET ADDRESS OF LK-DIB-BLOCK TO ADDRESS OF IRIS-DB-PCB
           MOVE LK-DIB-BLOCK TO IRIS-DIB-BLOCK
           SET ADDRESS OF IRIS-DB-PCB TO
                       ADDRESS OF IRIS-PCBS-TAB-IO(IRIS-PCB-NUM)
           .
      *
       EXTRACT-PCB-EXEC-EX.
      *
           EXIT.
      *
      ******************************************************************
      *    INITIALIZE VARIABLES
      ******************************************************************
      *
       INIT-VARIABLES SECTION.
      *
           MOVE 'INIT-VARIABLES' TO WS-LAST-IORTN-SECTION
      *
           MOVE IRIS-SEGMENT TO WS-SEGMENT-NAME
           IF IRIS-FUNC-REPL AND NOT IRIS-EXECDLI
             MOVE LAST-IMS-CCODE TO IRIS-KEYVALUE-TAB(1:240)
           END-IF
           SET DB-STATUS-OK TO TRUE
           SET IRIS-SQL-OK TO TRUE
           SET IRIS-NO-ERROR TO TRUE
           SET COMMAND-WITHOUT-HOLD TO TRUE
           SET HAS-NOT-PATHCALLS TO TRUE
           SET HAS-PATHCALLS-NO-ERROR TO TRUE
           SET IS-NOT-PATHCALL-REVERSE TO TRUE
           SET EXEC-GE-PATH-CALL TO TRUE
           MOVE ZERO TO WS-PATHCALL-LEVEL
           SET DEFAULT-DYNAMIC-ACCESS TO TRUE
           SET CUSTOM-STATIC-ACCESS TO TRUE
           SET EXEC-SEGMENT-ACCESS TO TRUE
           SET FULL-SCAN-FALSE TO TRUE
           IF IRIS-FUNC-GHU
           OR IRIS-FUNC-GHN
           OR IRIS-FUNC-GHNP
             SET COMMAND-WITH-HOLD TO TRUE
           END-IF
           MOVE ZERO  TO IRIS-SQLCODE
                         IRIS-SQLERRML
                         WS-FB-KEY-LENGTH
                         WS-SEGMENT-LEVEL
                         WS-SEGMENT-LEN
                         WS-LAST-SEGMENT-LEVEL
                         WS-PATHCALL-LEN
                         WS-INIT-PATHCALL-LEN
                         SQL-CONDITION-CLAUSE-LENGTH
                         SQL-JOIN-CLAUSE-LENGTH
                         SQL-ORDERBY-CLAUSE-LENGTH
           MOVE 1 TO WS-FB-KEY-LENGTH
                     IRIS-SQLERRML
           EVALUATE WS-SEGMENT-NAME
           WHEN 'PAUTSUM0'
             MOVE PAUTSUM0-LVL TO WS-INIT-SEGMENT-LEVEL
           WHEN 'PAUTDTL1'
             MOVE PAUTDTL1-LVL TO WS-INIT-SEGMENT-LEVEL
           END-EVALUATE
           MOVE SPACE TO IRIS-SQLERRMC
                         WS-LAST-SEGMENT-NAME
                         WS-LAST-IORTN-SECTION
           .
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
           MOVE 'FINALIZE-VARIABLES' TO WS-LAST-IORTN-SECTION
      *
           IF IO-RTN-FB-KEY-MAX-LENGTH > 0
           AND IO-RTN-FB-KEY-MAX-LENGTH < WS-FB-KEY-LENGTH
             MOVE IO-RTN-FB-KEY-MAX-LENGTH TO WS-FB-KEY-LENGTH
           END-IF
      *
      *    SETUP PCB INFO
      *
           IF IRIS-NO-ERROR
             IF DB-PCB-DBD-NAME = SPACE OR LOW-VALUE
               MOVE WS-DBD-NAME TO DB-PCB-DBD-NAME
             END-IF
             MOVE WS-FB-KEY-LENGTH TO DB-PCB-FB-KEY-LENGTH
             IF WS-FB-KEY-LENGTH > ZERO
               MOVE WS-FB-KEY-AREA(1:WS-FB-KEY-LENGTH)
                                   TO DB-PCB-KEY-FB (1:WS-FB-KEY-LENGTH)
             END-IF
             MOVE WS-LAST-SEGMENT-NAME TO DB-PCB-SEGMENT-NAME
             MOVE WS-LAST-SEGMENT-LEVEL TO DB-PCB-SEGMENT-LEVEL
             ADD 1 TO WS-INIT-SEGMENT-LEVEL GIVING WS-IDX
             PERFORM VARYING WS-IDX FROM WS-IDX BY 1
                     UNTIL WS-IDX > WS-SEGMENTS-MAX-LVL
               MOVE LOW-VALUE TO IO-RTN-USED-KEY-STATUS(WS-IDX)
                                 IO-RTN-USED-SSA-KEYS(WS-IDX)
                                 IO-RTN-USED-SSA-INFO(WS-IDX)
             END-PERFORM
             IF (IRIS-FUNC-GU OR IRIS-FUNC-GHU
             OR IRIS-FUNC-ISRT)
             AND WS-LAST-SEGMENT-LEVEL > ZERO
               MOVE ZERO TO
                 IO-RTN-LAST-OPEN-CURSOR(WS-LAST-SEGMENT-LEVEL)
             END-IF
      *
      *    SETUP STATUS
      *
             MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
             EVALUATE TRUE
             WHEN IRIS-SQL-OK
               SET DB-STATUS-OK TO TRUE
             WHEN IRIS-SQL-UNIQ-CONSTR-VIOLATED
               SET DB-STATUS-DUPLICATED-KEY TO TRUE
             WHEN IRIS-SQL-DEADLOCK
               SET DB-STATUS-RESOURCE-DEADLOCK TO TRUE
             WHEN IRIS-SQL-INTE-CONSTR-VIOLATED
               SET DB-STATUS-SEGMENT-NOT-FOUND TO TRUE
             WHEN IRIS-SQL-NOT-FOUND AND FULL-SCAN-FALSE
               SET DB-STATUS-SEGMENT-NOT-FOUND TO TRUE
             WHEN IRIS-SQL-END-DB-REACHED
             WHEN IRIS-SQL-NOT-FOUND AND FULL-SCAN-TRUE
               SET DB-STATUS-END-DB-REACHED TO TRUE
             WHEN IRIS-SQL-CHG-SEG
               SET DB-STATUS-CHANGED-SEGMENT-TYPE TO TRUE
             WHEN IRIS-SQL-CHG-LEV
               SET DB-STATUS-HIGHER-LEVEL-CROSSED TO TRUE
             WHEN IRIS-SQL-DUAL-PCB-MISMATCH
               SET DB-STATUS-DUAL-PCB-MISMATCH TO TRUE
             WHEN IRIS-SQL-DUAL-IOAREA-MISMATCH
               SET DB-STATUS-DUAL-IOAREA-MISMATCH TO TRUE
             WHEN OTHER
               SET DB-STATUS-INTERNAL-NOT-HANDLED TO TRUE
               SET IRIS-ERR-UNHANDLED-SQLCODE TO TRUE
             END-EVALUATE
             IF IRIS-SQL-CHG-SEG
             OR IRIS-SQL-CHG-LEV
               SET IRIS-SQL-OK TO TRUE
               MOVE IRIS-DB-SQLCODE TO IRIS-SQLCODE
             END-IF
           ELSE
             SET DB-STATUS-INTERNAL-NOT-HANDLED TO TRUE
           END-IF
           IF EXEC-SAVE-AREA
             SET ADDRESS OF LK-IO-AREA-BACKUP TO RUN-IO-AREA-PTR
             MOVE LK-IO-AREA(1:WS-SEGMENT-LEN) TO
                  LK-IO-AREA-BACKUP(1:WS-SEGMENT-LEN)
           END-IF
           SET SKIP-GE-PATH-CALL TO TRUE
      *
           IF WS-FB-KEY-LENGTH > 0
           AND IRIS-KFB-YES
             IF IRIS-KFB-LENGTH IS NUMERIC
               IF IRIS-KFB-LENGTH < WS-FB-KEY-LENGTH
               AND IRIS-KFB-LENGTH NOT = ZERO
                 MOVE IRIS-KFB-LENGTH TO WS-FB-KEY-LENGTH
               END-IF
             END-IF
             EVALUATE IRIS-PARAM-NUM
             WHEN 4
               MOVE DB-PCB-KEY-FB(1:WS-FB-KEY-LENGTH)
                 TO LK-SSA-01(1:WS-FB-KEY-LENGTH)
             WHEN 5
               MOVE DB-PCB-KEY-FB(1:WS-FB-KEY-LENGTH)
                 TO LK-SSA-02(1:WS-FB-KEY-LENGTH)
             WHEN 6
               MOVE DB-PCB-KEY-FB(1:WS-FB-KEY-LENGTH)
                 TO LK-SSA-03(1:WS-FB-KEY-LENGTH)
             WHEN 7
               MOVE DB-PCB-KEY-FB(1:WS-FB-KEY-LENGTH)
                 TO LK-SSA-04(1:WS-FB-KEY-LENGTH)
             END-EVALUATE
             SET IRIS-KFB-NO TO TRUE
             MOVE ZERO TO IRIS-KFB-LENGTH
           END-IF
           .
      *
       FINALIZE-VARIABLES-EX.
      *
           EXIT.
      *
      ******************************************************************
      *    HANDLE SEGMENT PAUTSUM0
      ******************************************************************
      *
       HANDLE-SEGMENT-PAUTSUM0 SECTION.
      *
           MOVE PAUTSUM0-LVL TO WS-SEGMENT-LEVEL
           MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN WS-INIT-PATHCALL-LEN
      *
           MOVE 'HANDLE-SEGMENT-PAUTSUM0' TO WS-LAST-IORTN-SECTION
      *
           EVALUATE TRUE
           WHEN SQL-SELECT-PRIMARY
             PERFORM PAUTSUM0-SELECT-PRIMARY-KEY
           WHEN SQL-SELECT-SEEK
             SET COMMAND-CODE-FIRST TO TRUE
             EVALUATE TRUE
             WHEN  IRIS-CODE-FIRST(1)
               SET COMMAND-CODE-FIRST TO TRUE
             WHEN  IRIS-CODE-LAST(1)
               SET COMMAND-CODE-LAST TO TRUE
             END-EVALUATE
      *
             PERFORM PAUTSUM0-SELECT-FIRST
           WHEN SQL-SELECT-NEXT
             SET COMMAND-CODE-FIRST TO TRUE
             EVALUATE TRUE
             WHEN  IRIS-CODE-FIRST(1)
               SET COMMAND-CODE-FIRST TO TRUE
             WHEN  IRIS-CODE-LAST(1)
               SET COMMAND-CODE-LAST TO TRUE
             END-EVALUATE
      *
             PERFORM PAUTSUM0-SELECT-NEXT
           WHEN SQL-SELECT-DYNAMIC
             SET COMMAND-CODE-FIRST TO TRUE
             EVALUATE TRUE
             WHEN  IRIS-CODE-FIRST(1)
               SET COMMAND-CODE-FIRST TO TRUE
             WHEN  IRIS-CODE-LAST(1)
               SET COMMAND-CODE-LAST TO TRUE
             END-EVALUATE
      *
             PERFORM PAUTSUM0-SELECT-DYNAMIC
           WHEN SQL-SELECT-PATH
             SET COMMAND-CODE-FIRST TO TRUE
             EVALUATE TRUE
             WHEN  IRIS-CODE-FIRST(1)
               SET COMMAND-CODE-FIRST TO TRUE
             WHEN  IRIS-CODE-LAST(1)
               SET COMMAND-CODE-LAST TO TRUE
             END-EVALUATE
      *
             PERFORM PAUTSUM0-SELECT-DYNAMIC
           WHEN SQL-INSERT
             SET COMMAND-CODE-HERE TO TRUE
             EVALUATE TRUE
             WHEN  IRIS-CODE-FIRST(1)
               SET COMMAND-CODE-FIRST TO TRUE
             WHEN  IRIS-CODE-LAST(1)
               SET COMMAND-CODE-LAST TO TRUE
             END-EVALUATE
      *
             PERFORM PAUTSUM0-INSERT
           WHEN SQL-UPDATE
             PERFORM PAUTSUM0-UPDATE
           WHEN SQL-DELETE
             PERFORM PAUTSUM0-DELETE
           WHEN OTHER
             SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
           END-EVALUATE
      *
           IF IRIS-SQL-OK
             MOVE PAUTSUM0-LVL TO WS-LAST-SEGMENT-LEVEL
             MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
           END-IF.
      *
       HANDLE-SEGMENT-PAUTSUM0-EX.
      *
           EXIT.
      *
      ******************************************************************
      *    SELECT ON PRIMARY KEY FOR SEGMENT PAUTSUM0
      ******************************************************************
      *
       PAUTSUM0-SELECT-PRIMARY-KEY SECTION.
      *
           MOVE 'PAUTSUM0-SELECT-PRIMARY-KEY' TO WS-LAST-IORTN-SECTION
      *
           IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO
                                               TO WS-PACKED-FLD-18-00(2)
           ELSE
             MOVE KEY-ACCNTID OF PAUTSUM0
                                               TO WS-PACKED-FLD-18-00(2)
           END-IF
           MOVE 1 TO WS-CK-POS
           MOVE ZERO TO WS-CK-LEN
           COMPUTE WS-CK-LEN = 6
           MOVE WS-PACKED-FLD-CHR(2)(5:6)
                    TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
                            TO WS-PACKED-FLD-CHR(1)(5:6)
           IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
             MOVE ZERO TO WS-PACKED-FLD-18-00(1)
           END-IF
           MOVE WS-PACKED-FLD-18-00(1) TO KEY-ACCNTID OF PAUTSUM0
           EXEC SQL
           SELECT
             KEY_ACCNTID
            ,PA_CUST_ID
            ,PA_AUTH_STATUS
            ,PA_ACCOUNT_STATUS1
            ,PA_ACCOUNT_STATUS2
            ,PA_ACCOUNT_STATUS3
            ,PA_ACCOUNT_STATUS4
            ,PA_ACCOUNT_STATUS5
            ,PA_CREDIT_LIMIT
            ,PA_CASH_LIMIT
            ,PA_CREDIT_BALANCE
            ,PA_CASH_BALANCE
            ,PA_APPROVED_AUTH_CNT
            ,PA_DECLINED_AUTH_CNT
            ,PA_APPROVED_AUTH_AMT
            ,PA_DECLINED_AUTH_AMT
            ,PAUTSUM0_FILLER
           INTO
             :PAUTSUM0.KEY-ACCNTID
            ,:PAUTSUM0.PA-CUST-ID
            ,:PAUTSUM0.PA-AUTH-STATUS
            ,:PAUTSUM0.PA-ACCOUNT-STATUS1
            ,:PAUTSUM0.PA-ACCOUNT-STATUS2
            ,:PAUTSUM0.PA-ACCOUNT-STATUS3
            ,:PAUTSUM0.PA-ACCOUNT-STATUS4
            ,:PAUTSUM0.PA-ACCOUNT-STATUS5
            ,:PAUTSUM0.PA-CREDIT-LIMIT
            ,:PAUTSUM0.PA-CASH-LIMIT
            ,:PAUTSUM0.PA-CREDIT-BALANCE
            ,:PAUTSUM0.PA-CASH-BALANCE
            ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
            ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
            ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
            ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
            ,:PAUTSUM0.PAUTSUM0-FILLER
           FROM
           DBPAUTP0_PAUTSUM0
             WHERE
               KEY_ACCNTID = 
             :PAUTSUM0.KEY-ACCNTID
           END-EXEC
      *
           SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
           MOVE SQLCODE TO IRIS-DB-SQLCODE
           IF SQLCODE = ZERO
             MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
             MOVE KEY-ACCNTID OF PAUTSUM0
                                               TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                                  TO WS-FB-KEY-AREA(1:6)
             MOVE 6 TO WS-FB-KEY-LENGTH
      *
      * PACKED FIELDS VS SIGNED ZONED FITTING
      *
             MOVE PA-CUST-ID OF PAUTSUM0 TO
             WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-18-00(1) TO
             PA-CUST-ID-N OF PAUTSUM0
             MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN
             PERFORM PAUTSUM0-SET-IO-AREA
             SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
             MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
                             IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
           END-IF
      *
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM.
      *
       PAUTSUM0-SELECT-PRIMARY-KEY-EX.
           EXIT.
      *
      ******************************************************************
      *    SELECT FIRST TIME FOR SEGMENT PAUTSUM0
      ******************************************************************
      *
       PAUTSUM0-SELECT-FIRST SECTION.
      *
           MOVE 'PAUTSUM0-SELECT-FIRST' TO WS-LAST-IORTN-SECTION
      *
           IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO
                                               TO WS-PACKED-FLD-18-00(2)
           ELSE
             MOVE KEY-ACCNTID OF PAUTSUM0
                                               TO WS-PACKED-FLD-18-00(2)
           END-IF
           MOVE 1 TO WS-CK-POS
           MOVE ZERO TO WS-CK-LEN
           COMPUTE WS-CK-LEN = 6
           MOVE WS-PACKED-FLD-CHR(2)(5:6)
                    TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           IF COMMAND-CODE-LAST
             EXEC SQL
             SELECT
               KEY_ACCNTID
              ,PA_CUST_ID
              ,PA_AUTH_STATUS
              ,PA_ACCOUNT_STATUS1
              ,PA_ACCOUNT_STATUS2
              ,PA_ACCOUNT_STATUS3
              ,PA_ACCOUNT_STATUS4
              ,PA_ACCOUNT_STATUS5
              ,PA_CREDIT_LIMIT
              ,PA_CASH_LIMIT
              ,PA_CREDIT_BALANCE
              ,PA_CASH_BALANCE
              ,PA_APPROVED_AUTH_CNT
              ,PA_DECLINED_AUTH_CNT
              ,PA_APPROVED_AUTH_AMT
              ,PA_DECLINED_AUTH_AMT
              ,PAUTSUM0_FILLER
             INTO
               :PAUTSUM0.KEY-ACCNTID
              ,:PAUTSUM0.PA-CUST-ID
              ,:PAUTSUM0.PA-AUTH-STATUS
              ,:PAUTSUM0.PA-ACCOUNT-STATUS1
              ,:PAUTSUM0.PA-ACCOUNT-STATUS2
              ,:PAUTSUM0.PA-ACCOUNT-STATUS3
              ,:PAUTSUM0.PA-ACCOUNT-STATUS4
              ,:PAUTSUM0.PA-ACCOUNT-STATUS5
              ,:PAUTSUM0.PA-CREDIT-LIMIT
              ,:PAUTSUM0.PA-CASH-LIMIT
              ,:PAUTSUM0.PA-CREDIT-BALANCE
              ,:PAUTSUM0.PA-CASH-BALANCE
              ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
              ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
              ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
              ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
              ,:PAUTSUM0.PAUTSUM0-FILLER
             FROM
             DBPAUTP0_PAUTSUM0
               WHERE
                 KEY_ACCNTID = (
                 SELECT
                   KEY_ACCNTID
                 FROM
                   DBPAUTP0_PAUTSUM0
                 ORDER BY 1 DESC LIMIT 1 
                 )
             END-EXEC
           ELSE
             EXEC SQL
               SELECT
                 KEY_ACCNTID
                ,PA_CUST_ID
                ,PA_AUTH_STATUS
                ,PA_ACCOUNT_STATUS1
                ,PA_ACCOUNT_STATUS2
                ,PA_ACCOUNT_STATUS3
                ,PA_ACCOUNT_STATUS4
                ,PA_ACCOUNT_STATUS5
                ,PA_CREDIT_LIMIT
                ,PA_CASH_LIMIT
                ,PA_CREDIT_BALANCE
                ,PA_CASH_BALANCE
                ,PA_APPROVED_AUTH_CNT
                ,PA_DECLINED_AUTH_CNT
                ,PA_APPROVED_AUTH_AMT
                ,PA_DECLINED_AUTH_AMT
                ,PAUTSUM0_FILLER
               INTO
                 :PAUTSUM0.KEY-ACCNTID
                ,:PAUTSUM0.PA-CUST-ID
                ,:PAUTSUM0.PA-AUTH-STATUS
                ,:PAUTSUM0.PA-ACCOUNT-STATUS1
                ,:PAUTSUM0.PA-ACCOUNT-STATUS2
                ,:PAUTSUM0.PA-ACCOUNT-STATUS3
                ,:PAUTSUM0.PA-ACCOUNT-STATUS4
                ,:PAUTSUM0.PA-ACCOUNT-STATUS5
                ,:PAUTSUM0.PA-CREDIT-LIMIT
                ,:PAUTSUM0.PA-CASH-LIMIT
                ,:PAUTSUM0.PA-CREDIT-BALANCE
                ,:PAUTSUM0.PA-CASH-BALANCE
                ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
                ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
                ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
                ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
                ,:PAUTSUM0.PAUTSUM0-FILLER
               FROM
               DBPAUTP0_PAUTSUM0
               WHERE
                 KEY_ACCNTID = (
                 SELECT
                   KEY_ACCNTID
                 FROM
                   DBPAUTP0_PAUTSUM0
                 ORDER BY 1 LIMIT 1
                 )
             END-EXEC
           END-IF
      *
           SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
      *
           MOVE SQLCODE TO IRIS-DB-SQLCODE
           EVALUATE TRUE
           WHEN IRIS-SQL-OK
             MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
             MOVE KEY-ACCNTID OF PAUTSUM0
                                               TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                                  TO WS-FB-KEY-AREA(1:6)
             MOVE 6 TO WS-FB-KEY-LENGTH
      *
      * PACKED FIELDS VS SIGNED ZONED FITTING
      *
             MOVE PA-CUST-ID OF PAUTSUM0 TO
             WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-18-00(1) TO
             PA-CUST-ID-N OF PAUTSUM0
             MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN
             PERFORM PAUTSUM0-SET-IO-AREA
             SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
             MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
                             IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
           END-EVALUATE
      *
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM.
      *
       PAUTSUM0-SELECT-FIRST-EX.
           EXIT.
      *
      ******************************************************************
      *    SELECT NEXT FOR SEGMENT PAUTSUM0
      ******************************************************************
      *
       PAUTSUM0-SELECT-NEXT SECTION.
      *
           MOVE 'PAUTSUM0-SELECT-NEXT' TO WS-LAST-IORTN-SECTION
      *
           IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO
                                               TO WS-PACKED-FLD-18-00(2)
           ELSE
             MOVE KEY-ACCNTID OF PAUTSUM0
                                               TO WS-PACKED-FLD-18-00(2)
           END-IF
           MOVE 1 TO WS-CK-POS
           MOVE ZERO TO WS-CK-LEN
           COMPUTE WS-CK-LEN = 6
           MOVE WS-PACKED-FLD-CHR(2)(5:6)
                    TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
                            TO WS-PACKED-FLD-CHR(1)(5:6)
           IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
             MOVE ZERO TO WS-PACKED-FLD-18-00(1)
           END-IF
           MOVE WS-PACKED-FLD-18-00(1) TO KEY-ACCNTID OF PAUTSUM0
           IF COMMAND-CODE-LAST
             EXEC SQL
             SELECT
               KEY_ACCNTID
              ,PA_CUST_ID
              ,PA_AUTH_STATUS
              ,PA_ACCOUNT_STATUS1
              ,PA_ACCOUNT_STATUS2
              ,PA_ACCOUNT_STATUS3
              ,PA_ACCOUNT_STATUS4
              ,PA_ACCOUNT_STATUS5
              ,PA_CREDIT_LIMIT
              ,PA_CASH_LIMIT
              ,PA_CREDIT_BALANCE
              ,PA_CASH_BALANCE
              ,PA_APPROVED_AUTH_CNT
              ,PA_DECLINED_AUTH_CNT
              ,PA_APPROVED_AUTH_AMT
              ,PA_DECLINED_AUTH_AMT
              ,PAUTSUM0_FILLER
             INTO
               :PAUTSUM0.KEY-ACCNTID
              ,:PAUTSUM0.PA-CUST-ID
              ,:PAUTSUM0.PA-AUTH-STATUS
              ,:PAUTSUM0.PA-ACCOUNT-STATUS1
              ,:PAUTSUM0.PA-ACCOUNT-STATUS2
              ,:PAUTSUM0.PA-ACCOUNT-STATUS3
              ,:PAUTSUM0.PA-ACCOUNT-STATUS4
              ,:PAUTSUM0.PA-ACCOUNT-STATUS5
              ,:PAUTSUM0.PA-CREDIT-LIMIT
              ,:PAUTSUM0.PA-CASH-LIMIT
              ,:PAUTSUM0.PA-CREDIT-BALANCE
              ,:PAUTSUM0.PA-CASH-BALANCE
              ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
              ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
              ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
              ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
              ,:PAUTSUM0.PAUTSUM0-FILLER
             FROM
             DBPAUTP0_PAUTSUM0
               WHERE
                 KEY_ACCNTID = (
                 SELECT
                   KEY_ACCNTID
                 FROM
                   DBPAUTP0_PAUTSUM0
                 WHERE
                   KEY_ACCNTID >
                   :PAUTSUM0.KEY-ACCNTID
                 ORDER BY 1 DESC LIMIT 1 
                 )
             END-EXEC
           ELSE
             EXEC SQL
             SELECT
               KEY_ACCNTID
              ,PA_CUST_ID
              ,PA_AUTH_STATUS
              ,PA_ACCOUNT_STATUS1
              ,PA_ACCOUNT_STATUS2
              ,PA_ACCOUNT_STATUS3
              ,PA_ACCOUNT_STATUS4
              ,PA_ACCOUNT_STATUS5
              ,PA_CREDIT_LIMIT
              ,PA_CASH_LIMIT
              ,PA_CREDIT_BALANCE
              ,PA_CASH_BALANCE
              ,PA_APPROVED_AUTH_CNT
              ,PA_DECLINED_AUTH_CNT
              ,PA_APPROVED_AUTH_AMT
              ,PA_DECLINED_AUTH_AMT
              ,PAUTSUM0_FILLER
             INTO
               :PAUTSUM0.KEY-ACCNTID
              ,:PAUTSUM0.PA-CUST-ID
              ,:PAUTSUM0.PA-AUTH-STATUS
              ,:PAUTSUM0.PA-ACCOUNT-STATUS1
              ,:PAUTSUM0.PA-ACCOUNT-STATUS2
              ,:PAUTSUM0.PA-ACCOUNT-STATUS3
              ,:PAUTSUM0.PA-ACCOUNT-STATUS4
              ,:PAUTSUM0.PA-ACCOUNT-STATUS5
              ,:PAUTSUM0.PA-CREDIT-LIMIT
              ,:PAUTSUM0.PA-CASH-LIMIT
              ,:PAUTSUM0.PA-CREDIT-BALANCE
              ,:PAUTSUM0.PA-CASH-BALANCE
              ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
              ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
              ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
              ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
              ,:PAUTSUM0.PAUTSUM0-FILLER
             FROM
             DBPAUTP0_PAUTSUM0
               WHERE
                 KEY_ACCNTID = (
                 SELECT
                   KEY_ACCNTID
                 FROM
                   DBPAUTP0_PAUTSUM0
                 WHERE
                   KEY_ACCNTID >
                   :PAUTSUM0.KEY-ACCNTID
                 ORDER BY 1 LIMIT 1
                 )
             END-EXEC
           END-IF
      *
           SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
           MOVE SQLCODE TO IRIS-DB-SQLCODE
           EVALUATE TRUE
           WHEN IRIS-SQL-OK
             MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
             MOVE KEY-ACCNTID OF PAUTSUM0
                                               TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                                  TO WS-FB-KEY-AREA(1:6)
             MOVE 6 TO WS-FB-KEY-LENGTH
      *
      * PACKED FIELDS VS SIGNED ZONED FITTING
      *
             MOVE PA-CUST-ID OF PAUTSUM0 TO
             WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-18-00(1) TO
             PA-CUST-ID-N OF PAUTSUM0
             MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN
             PERFORM PAUTSUM0-SET-IO-AREA
             SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
             MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
                             IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
           END-EVALUATE
      *
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM.
      *
       PAUTSUM0-SELECT-NEXT-EX.
           EXIT.
      *
      ******************************************************************
      *    DYNAMIC SELECT FOR SEGMENT PAUTSUM0
      ******************************************************************
      *
       PAUTSUM0-SELECT-DYNAMIC SECTION.
      *
           MOVE 'PAUTSUM0-SELECT-DYNAMIC' TO WS-LAST-IORTN-SECTION
      *
           IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO
                                               TO WS-PACKED-FLD-18-00(2)
           ELSE
             MOVE KEY-ACCNTID OF PAUTSUM0
                                               TO WS-PACKED-FLD-18-00(2)
           END-IF
           MOVE 1 TO WS-CK-POS
           MOVE ZERO TO WS-CK-LEN
           COMPUTE WS-CK-LEN = 6
           MOVE WS-PACKED-FLD-CHR(2)(5:6)
                    TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
      *    PREPARE THE WHERE CONDITION IF ANY
      *
           MOVE ZERO TO WS-WHERE-LEN WS-ORDERBY-LEN
           IF SQL-CONDITION-CLAUSE-LENGTH > 0
             SUBTRACT 1 FROM SQL-CONDITION-CLAUSE-LENGTH
             STRING 'WHERE '
             SQL-CONDITION-CLAUSE-TEXT(1:SQL-CONDITION-CLAUSE-LENGTH)
             ' ' DELIMITED BY SIZE INTO WS-WHERE
             COMPUTE WS-WHERE-LEN = SQL-CONDITION-CLAUSE-LENGTH + 7
           END-IF
      *
           IF SQL-ORDERBY-CLAUSE-LENGTH > 0
             SUBTRACT 1 FROM SQL-ORDERBY-CLAUSE-LENGTH
             MOVE SQL-ORDERBY-CLAUSE-TEXT(1:SQL-ORDERBY-CLAUSE-LENGTH)
                            TO WS-ORDERBY(1:SQL-ORDERBY-CLAUSE-LENGTH)
             MOVE SQL-ORDERBY-CLAUSE-LENGTH TO WS-ORDERBY-LEN
           ELSE
             COMPUTE WS-ORDERBY-LEN = 1
             STRING ' ORDER BY '
                   ' KEY_ACCNTID '
             DELIMITED BY SIZE INTO WS-ORDERBY POINTER WS-ORDERBY-LEN
             SUBTRACT 1 FROM WS-ORDERBY-LEN
           END-IF
      *
           IF COMMAND-CODE-LAST
             STRING WS-ORDERBY(1:WS-ORDERBY-LEN)
             ' DESC ' DELIMITED BY SIZE
             INTO WS-ORDERBY
             ADD 6 TO WS-ORDERBY-LEN
           END-IF
      *
      *
      *    PREPARE THE DYNAMIC QUERY
      *
           MOVE 1 TO WS-SQL-STM-LEN
           STRING
           'SELECT '
             'DBPAUTP0_PAUTSUM0.KEY_ACCNTID '
            ',DBPAUTP0_PAUTSUM0.PA_CUST_ID '
            ',DBPAUTP0_PAUTSUM0.PA_AUTH_STATUS '
            ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS1 '
            ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS2 '
            ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS3 '
            ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS4 '
            ',DBPAUTP0_PAUTSUM0.PA_ACCOUNT_STATUS5 '
            ',DBPAUTP0_PAUTSUM0.PA_CREDIT_LIMIT '
            ',DBPAUTP0_PAUTSUM0.PA_CASH_LIMIT '
            ',DBPAUTP0_PAUTSUM0.PA_CREDIT_BALANCE '
            ',DBPAUTP0_PAUTSUM0.PA_CASH_BALANCE '
            ',DBPAUTP0_PAUTSUM0.PA_APPROVED_AUTH_CNT '
            ',DBPAUTP0_PAUTSUM0.PA_DECLINED_AUTH_CNT '
            ',DBPAUTP0_PAUTSUM0.PA_APPROVED_AUTH_AMT '
            ',DBPAUTP0_PAUTSUM0.PA_DECLINED_AUTH_AMT '
            ',DBPAUTP0_PAUTSUM0.PAUTSUM0_FILLER '
           'FROM '
           'DBPAUTP0_PAUTSUM0 '
           DELIMITED BY SIZE
           INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
           IF SQL-JOIN-CLAUSE-LENGTH > 0
             SUBTRACT 1 FROM SQL-JOIN-CLAUSE-LENGTH
             STRING SQL-JOIN-CLAUSE-TEXT(1:SQL-JOIN-CLAUSE-LENGTH) ' '
             DELIMITED BY SIZE
             INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
           END-IF
           IF WS-WHERE-LEN > 0
             STRING
             WS-WHERE(1:WS-WHERE-LEN)
             DELIMITED BY SIZE
             INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
             IF IRIS-TRACE-FULL
               MOVE 0 TO IRIS-MSG-LEN
               STRING '<IRISTRACE> - DBPAUTP0:SELECT-DYNAMIC' NL
                    ' SECTION    =(PAUTSUM0-SELECT-DYNAMIC) ' NL
                    ' CONDITION  =(' WS-WHERE(1:WS-WHERE-LEN) ')' NL
                    ' ORDER BY   =(' WS-ORDERBY(1:WS-ORDERBY-LEN) ')'
               MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
               SET IRISTRAC-RTN TO TRUE
               CALL IRIS-WS-RTN USING IRIS-WORK-AREA
             END-IF
           END-IF
           IF WS-ORDERBY-LEN > 0
             STRING WS-ORDERBY(1:WS-ORDERBY-LEN) DELIMITED BY SIZE
             INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
           END-IF
           STRING ' FETCH FIRST 1 ROW ONLY '
           DELIMITED BY SIZE
           INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
           SUBTRACT 1 FROM WS-SQL-STM-LEN
      *
      *    DECLARE THE DYNAMIC CURSOR
      *
           EXEC SQL
             DECLARE PAUTSUM0_CRS CURSOR
             FOR PAUTSUM0_STATEMENT
           END-EXEC
      *
      *    DECLARE THE SQL STATEMENT
      *
           EXEC SQL
             DECLARE PAUTSUM0_STATEMENT STATEMENT
           END-EXEC
      *
      *    PREPARE THE STATEMENT
      *
           EXEC SQL
             PREPARE PAUTSUM0_STATEMENT
             INTO :SQLDA
             FROM :WS-SQL-STM
           END-EXEC
      *
      *    TEST THE RETURN CODE
      *
           IF SQLCODE NOT = ZERO
             MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
             MOVE SQLERRM TO IRIS-SQLERRM
             GO TO PAUTSUM0-SELECT-DYNAMIC-EX
           END-IF
      *
      *    OPEN THE DYNAMIC CURSOR
      *
           EXEC SQL
             OPEN PAUTSUM0_CRS
           END-EXEC
      *
      *    TEST THE RETURN CODE
      *
           IF SQLCODE NOT = ZERO
             MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
             MOVE SQLERRM TO IRIS-SQLERRM
             GO TO PAUTSUM0-SELECT-DYNAMIC-EX
           END-IF
      *
      *    FETCH THE DYNAMIC CURSOR
      *
           EXEC SQL
             FETCH PAUTSUM0_CRS
             INTO
               :PAUTSUM0.KEY-ACCNTID
              ,:PAUTSUM0.PA-CUST-ID
              ,:PAUTSUM0.PA-AUTH-STATUS
              ,:PAUTSUM0.PA-ACCOUNT-STATUS1
              ,:PAUTSUM0.PA-ACCOUNT-STATUS2
              ,:PAUTSUM0.PA-ACCOUNT-STATUS3
              ,:PAUTSUM0.PA-ACCOUNT-STATUS4
              ,:PAUTSUM0.PA-ACCOUNT-STATUS5
              ,:PAUTSUM0.PA-CREDIT-LIMIT
              ,:PAUTSUM0.PA-CASH-LIMIT
              ,:PAUTSUM0.PA-CREDIT-BALANCE
              ,:PAUTSUM0.PA-CASH-BALANCE
              ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
              ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
              ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
              ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
              ,:PAUTSUM0.PAUTSUM0-FILLER
           END-EXEC
      *
           IF SQLCODE NOT = ZERO
             MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
             MOVE SQLERRM TO IRIS-SQLERRM
      *
      *      CLOSE THE DYNAMIC CURSOR
      *
             EXEC SQL
               CLOSE PAUTSUM0_CRS
             END-EXEC
             GO TO PAUTSUM0-SELECT-DYNAMIC-EX
           END-IF
      *
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM
      *
      *      CLOSE THE DYNAMIC CURSOR
      *
           EXEC SQL
             CLOSE PAUTSUM0_CRS
           END-EXEC
      *
           SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
      *
           MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
           EVALUATE TRUE
           WHEN IRIS-SQL-OK
             MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
             MOVE KEY-ACCNTID OF PAUTSUM0
                                               TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                                  TO WS-FB-KEY-AREA(1:6)
             MOVE 6 TO WS-FB-KEY-LENGTH
             IF SQL-SELECT-DYNAMIC
             AND NOT COMMAND-WITH-HOLD
      *
      * PACKED FIELDS VS SIGNED ZONED FITTING
      *
               MOVE PA-CUST-ID OF PAUTSUM0 TO
               WS-PACKED-FLD-18-00(1)
               MOVE WS-PACKED-FLD-18-00(1) TO
               PA-CUST-ID-N OF PAUTSUM0
               MOVE PAUTSUM0-LEN TO WS-SEGMENT-LEN
               PERFORM PAUTSUM0-SET-IO-AREA
             END-IF
             SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
             MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
                             IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
           END-EVALUATE.
      *
       PAUTSUM0-SELECT-DYNAMIC-EX.
           EXIT.
      *
      ******************************************************************
      *    INSERT FOR SEGMENT PAUTSUM0
      ******************************************************************
      *
       PAUTSUM0-INSERT SECTION.
      *
           MOVE 'PAUTSUM0-INSERT' TO WS-LAST-IORTN-SECTION
      *
      *
           INITIALIZE PAUTSUM0
      *
           MOVE LK-IO-AREA(1:PAUTSUM0-LEN) TO PAUTSUM0(1:PAUTSUM0-LEN)
           IF KEY-ACCNTID
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             KEY-ACCNTID OF PAUTSUM0
           END-IF
           IF PA-CUST-ID-N
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             PA-CUST-ID-N OF PAUTSUM0
           END-IF
           IF PA-CREDIT-LIMIT
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             PA-CREDIT-LIMIT OF PAUTSUM0
           END-IF
           IF PA-CASH-LIMIT
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             PA-CASH-LIMIT OF PAUTSUM0
           END-IF
           IF PA-CREDIT-BALANCE
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             PA-CREDIT-BALANCE OF PAUTSUM0
           END-IF
           IF PA-CASH-BALANCE
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             PA-CASH-BALANCE OF PAUTSUM0
           END-IF
           IF PA-APPROVED-AUTH-AMT
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             PA-APPROVED-AUTH-AMT OF PAUTSUM0
           END-IF
           IF PA-DECLINED-AUTH-AMT
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             PA-DECLINED-AUTH-AMT OF PAUTSUM0
           END-IF
      *
      * SIGNED ZONED FIELDS VS PACKED FITTING
      *
           MOVE PA-CUST-ID-N OF PAUTSUM0 TO
           WS-PACKED-FLD-18-00(1)
           MOVE WS-PACKED-FLD-18-00(1) TO
           PA-CUST-ID OF PAUTSUM0
           IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO
                                               TO WS-PACKED-FLD-18-00(2)
           ELSE
             MOVE KEY-ACCNTID OF PAUTSUM0
                                               TO WS-PACKED-FLD-18-00(2)
           END-IF
           MOVE 1 TO WS-CK-POS
           MOVE ZERO TO WS-CK-LEN
           COMPUTE WS-CK-LEN = 6
           MOVE WS-PACKED-FLD-CHR(2)(5:6)
                    TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
      *
      *
           EXEC SQL
             INSERT INTO
               DBPAUTP0_PAUTSUM0
               (
               KEY_ACCNTID
              ,PA_CUST_ID
              ,PA_AUTH_STATUS
              ,PA_ACCOUNT_STATUS1
              ,PA_ACCOUNT_STATUS2
              ,PA_ACCOUNT_STATUS3
              ,PA_ACCOUNT_STATUS4
              ,PA_ACCOUNT_STATUS5
              ,PA_CREDIT_LIMIT
              ,PA_CASH_LIMIT
              ,PA_CREDIT_BALANCE
              ,PA_CASH_BALANCE
              ,PA_APPROVED_AUTH_CNT
              ,PA_DECLINED_AUTH_CNT
              ,PA_APPROVED_AUTH_AMT
              ,PA_DECLINED_AUTH_AMT
              ,PAUTSUM0_FILLER
               )
             VALUES
             (
               :PAUTSUM0.KEY-ACCNTID
              ,:PAUTSUM0.PA-CUST-ID
              ,:PAUTSUM0.PA-AUTH-STATUS
              ,:PAUTSUM0.PA-ACCOUNT-STATUS1
              ,:PAUTSUM0.PA-ACCOUNT-STATUS2
              ,:PAUTSUM0.PA-ACCOUNT-STATUS3
              ,:PAUTSUM0.PA-ACCOUNT-STATUS4
              ,:PAUTSUM0.PA-ACCOUNT-STATUS5
              ,:PAUTSUM0.PA-CREDIT-LIMIT
              ,:PAUTSUM0.PA-CASH-LIMIT
              ,:PAUTSUM0.PA-CREDIT-BALANCE
              ,:PAUTSUM0.PA-CASH-BALANCE
              ,:PAUTSUM0.PA-APPROVED-AUTH-CNT
              ,:PAUTSUM0.PA-DECLINED-AUTH-CNT
              ,:PAUTSUM0.PA-APPROVED-AUTH-AMT
              ,:PAUTSUM0.PA-DECLINED-AUTH-AMT
              ,:PAUTSUM0.PAUTSUM0-FILLER
             )
           END-EXEC
      *
           SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTSUM0-LVL) TO TRUE
           MOVE SQLCODE TO IRIS-DB-SQLCODE
           EVALUATE TRUE
           WHEN IRIS-SQL-OK
           WHEN IRIS-SQL-UNIQ-CONSTR-VIOLATED
             MOVE KEY-ACCNTID OF PAUTSUM0 TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
             MOVE KEY-ACCNTID OF PAUTSUM0
                                               TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                                  TO WS-FB-KEY-AREA(1:6)
             MOVE 6 TO WS-FB-KEY-LENGTH
             SET IO-RTN-USED-KEY-CHANGED(PAUTSUM0-LVL) TO TRUE
             IF IRIS-SQL-UNIQ-CONSTR-VIOLATED
               SET IO-RTN-USED-KEY-DUPREC(PAUTSUM0-LVL) TO TRUE
             END-IF
             MOVE 'PAUTSUM0' TO WS-LAST-SEGMENT-NAME
                             IO-RTN-USED-LAST-SEGMENT(PAUTSUM0-LVL)
           END-EVALUATE
      *
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM.
      *
       PAUTSUM0-INSERT-EX.
           EXIT.
      *
      ******************************************************************
      *    UPDATE FOR SEGMENT PAUTSUM0
      ******************************************************************
      *
       PAUTSUM0-UPDATE SECTION.
      *
           MOVE 'PAUTSUM0-UPDATE' TO WS-LAST-IORTN-SECTION
      *
      *
           INITIALIZE PAUTSUM0
      *
           MOVE LK-IO-AREA(1:PAUTSUM0-LEN) TO PAUTSUM0(1:PAUTSUM0-LEN)
           IF KEY-ACCNTID
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             KEY-ACCNTID OF PAUTSUM0
           END-IF
           IF PA-CUST-ID-N
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             PA-CUST-ID-N OF PAUTSUM0
           END-IF
           IF PA-CREDIT-LIMIT
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             PA-CREDIT-LIMIT OF PAUTSUM0
           END-IF
           IF PA-CASH-LIMIT
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             PA-CASH-LIMIT OF PAUTSUM0
           END-IF
           IF PA-CREDIT-BALANCE
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             PA-CREDIT-BALANCE OF PAUTSUM0
           END-IF
           IF PA-CASH-BALANCE
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             PA-CASH-BALANCE OF PAUTSUM0
           END-IF
           IF PA-APPROVED-AUTH-AMT
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             PA-APPROVED-AUTH-AMT OF PAUTSUM0
           END-IF
           IF PA-DECLINED-AUTH-AMT
           OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO TO
             PA-DECLINED-AUTH-AMT OF PAUTSUM0
           END-IF
      *
      * SIGNED ZONED FIELDS VS PACKED FITTING
      *
           MOVE PA-CUST-ID-N OF PAUTSUM0 TO
           WS-PACKED-FLD-18-00(1)
           MOVE WS-PACKED-FLD-18-00(1) TO
           PA-CUST-ID OF PAUTSUM0
      *
           IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO
                                               TO WS-PACKED-FLD-18-00(2)
           ELSE
             MOVE KEY-ACCNTID OF PAUTSUM0
                                               TO WS-PACKED-FLD-18-00(2)
           END-IF
           MOVE 1 TO WS-CK-POS
           MOVE ZERO TO WS-CK-LEN
           COMPUTE WS-CK-LEN = 6
           MOVE WS-PACKED-FLD-CHR(2)(5:6)
                    TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
                            TO WS-PACKED-FLD-CHR(1)(5:6)
           IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
             MOVE ZERO TO WS-PACKED-FLD-18-00(1)
           END-IF
           MOVE WS-PACKED-FLD-18-00(1) TO KEY-ACCNTID OF PAUTSUM0
      *
      *
           EXEC SQL
             UPDATE DBPAUTP0_PAUTSUM0 SET
               PA_CUST_ID = :PAUTSUM0.PA-CUST-ID,
               PA_AUTH_STATUS = :PAUTSUM0.PA-AUTH-STATUS,
               PA_ACCOUNT_STATUS1 = :PAUTSUM0.PA-ACCOUNT-STATUS1,
               PA_ACCOUNT_STATUS2 = :PAUTSUM0.PA-ACCOUNT-STATUS2,
               PA_ACCOUNT_STATUS3 = :PAUTSUM0.PA-ACCOUNT-STATUS3,
               PA_ACCOUNT_STATUS4 = :PAUTSUM0.PA-ACCOUNT-STATUS4,
               PA_ACCOUNT_STATUS5 = :PAUTSUM0.PA-ACCOUNT-STATUS5,
               PA_CREDIT_LIMIT = :PAUTSUM0.PA-CREDIT-LIMIT,
               PA_CASH_LIMIT = :PAUTSUM0.PA-CASH-LIMIT,
               PA_CREDIT_BALANCE = :PAUTSUM0.PA-CREDIT-BALANCE,
               PA_CASH_BALANCE = :PAUTSUM0.PA-CASH-BALANCE,
               PA_APPROVED_AUTH_CNT = :PAUTSUM0.PA-APPROVED-AUTH-CNT,
               PA_DECLINED_AUTH_CNT = :PAUTSUM0.PA-DECLINED-AUTH-CNT,
               PA_APPROVED_AUTH_AMT = :PAUTSUM0.PA-APPROVED-AUTH-AMT,
               PA_DECLINED_AUTH_AMT = :PAUTSUM0.PA-DECLINED-AUTH-AMT,
               PAUTSUM0_FILLER = :PAUTSUM0.PAUTSUM0-FILLER
             WHERE
               KEY_ACCNTID =
             :PAUTSUM0.KEY-ACCNTID
           END-EXEC
      *
      * PACKED FIELDS VS SIGNED ZONED FITTING
      *
           MOVE PA-CUST-ID OF PAUTSUM0 TO
           WS-PACKED-FLD-18-00(1)
           MOVE WS-PACKED-FLD-18-00(1) TO
           PA-CUST-ID-N OF PAUTSUM0
      *
           IF SQLCODE = ZERO
             MOVE LK-IO-AREA(1:PAUTSUM0-LEN)
               TO PAUTSUM0-LAST-AREA
           END-IF
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM.
      *
       PAUTSUM0-UPDATE-EX.
           EXIT.
      *
      ******************************************************************
      *    DELETE FOR SEGMENT PAUTSUM0
      ******************************************************************
      *
       PAUTSUM0-DELETE SECTION.
      *
           MOVE 'PAUTSUM0-DELETE' TO WS-LAST-IORTN-SECTION
      *
      *
           IF HAS-PATHCALLS
             SET IRIS-ERR-RTN-UNHANDLED-ACCESS TO TRUE
             GO TO PAUTSUM0-DELETE-EX
           END-IF
      *
           IF KEY-ACCNTID OF PAUTSUM0 NOT NUMERIC
             MOVE ZERO
                                               TO WS-PACKED-FLD-18-00(2)
           ELSE
             MOVE KEY-ACCNTID OF PAUTSUM0
                                               TO WS-PACKED-FLD-18-00(2)
           END-IF
           MOVE 1 TO WS-CK-POS
           MOVE ZERO TO WS-CK-LEN
           COMPUTE WS-CK-LEN = 6
           MOVE WS-PACKED-FLD-CHR(2)(5:6)
                    TO PAUTSUM0-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
                            TO WS-PACKED-FLD-CHR(1)(5:6)
           IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
             MOVE ZERO TO WS-PACKED-FLD-18-00(1)
           END-IF
           MOVE WS-PACKED-FLD-18-00(1) TO KEY-ACCNTID OF PAUTSUM0
      *
           EXEC SQL
             DELETE FROM DBPAUTP0_PAUTSUM0
             WHERE
               KEY_ACCNTID =
             :PAUTSUM0.KEY-ACCNTID
           END-EXEC
      *
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM.
      *
       PAUTSUM0-DELETE-EX.
           EXIT.
      *
      ******************************************************************
      *    SET I/O AREA PAUTSUM0
      ******************************************************************
      *
       PAUTSUM0-SET-IO-AREA SECTION.
      *
           MOVE 'PAUTSUM0-SET-IO-AREA' TO WS-LAST-IORTN-SECTION
      *
           MOVE PAUTSUM0(1:WS-SEGMENT-LEN)
                          TO LK-IO-AREA(1:WS-SEGMENT-LEN)
                             PAUTSUM0-LAST-AREA.
      *
       PAUTSUM0-SET-IO-AREA-EX.
           EXIT.
      *
      ******************************************************************
      *    HANDLE SEGMENT PAUTDTL1
      ******************************************************************
      *
       HANDLE-SEGMENT-PAUTDTL1 SECTION.
      *
           MOVE PAUTDTL1-LVL TO WS-SEGMENT-LEVEL
           MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN WS-PATHCALL-LEN
      *
           IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
             SET HAS-PATHCALLS TO TRUE
             ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
           END-IF
           MOVE WS-PATHCALL-LEN TO WS-INIT-PATHCALL-LEN
      *
           MOVE 'HANDLE-SEGMENT-PAUTDTL1' TO WS-LAST-IORTN-SECTION
      *
           EVALUATE TRUE
           WHEN SQL-SELECT-PRIMARY
             PERFORM PAUTDTL1-SELECT-PRIMARY-KEY
           WHEN SQL-SELECT-SEEK
             SET COMMAND-CODE-FIRST TO TRUE
             EVALUATE TRUE
             WHEN  IRIS-CODE-FIRST(2)
               SET COMMAND-CODE-FIRST TO TRUE
             WHEN  IRIS-CODE-LAST(2)
               SET COMMAND-CODE-LAST TO TRUE
             END-EVALUATE
      *
             PERFORM PAUTDTL1-SELECT-FIRST
           WHEN SQL-SELECT-NEXT
             SET COMMAND-CODE-FIRST TO TRUE
             EVALUATE TRUE
             WHEN  IRIS-CODE-FIRST(2)
               SET COMMAND-CODE-FIRST TO TRUE
             WHEN  IRIS-CODE-LAST(2)
               SET COMMAND-CODE-LAST TO TRUE
             END-EVALUATE
      *
             PERFORM PAUTDTL1-SELECT-NEXT
           WHEN SQL-SELECT-DYNAMIC
             SET COMMAND-CODE-FIRST TO TRUE
             EVALUATE TRUE
             WHEN  IRIS-CODE-FIRST(2)
               SET COMMAND-CODE-FIRST TO TRUE
             WHEN  IRIS-CODE-LAST(2)
               SET COMMAND-CODE-LAST TO TRUE
             END-EVALUATE
      *
             PERFORM PAUTDTL1-SELECT-DYNAMIC
           WHEN SQL-SELECT-STATIC
             SET COMMAND-CODE-FIRST TO TRUE
             EVALUATE TRUE
             WHEN  IRIS-CODE-FIRST(2)
               SET COMMAND-CODE-FIRST TO TRUE
             WHEN  IRIS-CODE-LAST(2)
               SET COMMAND-CODE-LAST TO TRUE
             END-EVALUATE
      *
             EVALUATE TRUE
             WHEN CUSTOM-STATIC-ACCESS
               PERFORM PAUTDTL1-SELECT-STATIC-CA
             WHEN OTHER
               SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
             END-EVALUATE
           WHEN SQL-SELECT-PATH
             SET COMMAND-CODE-FIRST TO TRUE
             EVALUATE TRUE
             WHEN  IRIS-CODE-FIRST(2)
               SET COMMAND-CODE-FIRST TO TRUE
             WHEN  IRIS-CODE-LAST(2)
               SET COMMAND-CODE-LAST TO TRUE
             END-EVALUATE
      *
             PERFORM PAUTDTL1-SELECT-DYNAMIC
           WHEN SQL-INSERT
             SET COMMAND-CODE-HERE TO TRUE
             EVALUATE TRUE
             WHEN  IRIS-CODE-FIRST(2)
               SET COMMAND-CODE-FIRST TO TRUE
             WHEN  IRIS-CODE-LAST(2)
               SET COMMAND-CODE-LAST TO TRUE
             END-EVALUATE
      *
             PERFORM PAUTDTL1-INSERT
           WHEN SQL-UPDATE
             PERFORM PAUTDTL1-UPDATE
           WHEN SQL-DELETE
             PERFORM PAUTDTL1-DELETE
           WHEN OTHER
             SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
           END-EVALUATE
      *
           IF IRIS-SQL-OK
             MOVE PAUTDTL1-LVL TO WS-LAST-SEGMENT-LEVEL
             MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
           END-IF.
      *
       HANDLE-SEGMENT-PAUTDTL1-EX.
      *
           EXIT.
      *
      ******************************************************************
      *    SELECT ON PRIMARY KEY FOR SEGMENT PAUTDTL1
      ******************************************************************
      *
       PAUTDTL1-SELECT-PRIMARY-KEY SECTION.
      *
           MOVE 'PAUTDTL1-SELECT-PRIMARY-KEY' TO WS-LAST-IORTN-SECTION
      *
           MOVE 1 TO WS-CK-POS
           MOVE ZERO TO WS-CK-LEN
           MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
                            TO WS-PACKED-FLD-CHR(1)(5:6)
           COMPUTE WS-CK-LEN = 6
           MOVE WS-PACKED-FLD-CHR(1)(5:6)
                    TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
             MOVE ZERO TO WS-PACKED-FLD-18-00(1)
           END-IF
           MOVE WS-PACKED-FLD-18-00(1) TO
                                        PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
           COMPUTE WS-CK-LEN = 8
           MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
                    TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           MOVE IO-RTN-USED-KEY-ALPHA(2)(1:8) TO
                                           KEY-PAUT9CTS OF PAUTDTL1(1:8)
           EXEC SQL
           SELECT
             KEY_PAUT9CTS
            ,PA_AUTH_ORIG_DATE
            ,PA_AUTH_ORIG_TIME
            ,PA_CARD_NUM
            ,PA_AUTH_TYPE
            ,PA_CARD_EXPIRY_DATE
            ,PA_MESSAGE_TYPE
            ,PA_MESSAGE_SOURCE
            ,PA_AUTH_ID_CODE
            ,PA_AUTH_RESP_CODE
            ,PA_AUTH_RESP_REASON
            ,PA_PROCESSING_CODE
            ,PA_TRANSACTION_AMT
            ,PA_APPROVED_AMT
            ,PA_MERCHANT_CATAGORY_CODE
            ,PA_ACQR_COUNTRY_CODE
            ,PA_POS_ENTRY_MODE
            ,PA_MERCHANT_ID
            ,PA_MERCHANT_NAME
            ,PA_MERCHANT_CITY
            ,PA_MERCHANT_STATE
            ,PA_MERCHANT_ZIP
            ,PA_TRANSACTION_ID
            ,PA_MATCH_STATUS
            ,PA_AUTH_FRAUD
            ,PA_FRAUD_RPT_DATE
            ,PAUTDTL1_FILLER
            ,PAUTSUM0_KEY_ACCNTID
           INTO
             :PAUTDTL1.KEY-PAUT9CTS
            ,:PAUTDTL1.PA-AUTH-ORIG-DATE
            ,:PAUTDTL1.PA-AUTH-ORIG-TIME
            ,:PAUTDTL1.PA-CARD-NUM
            ,:PAUTDTL1.PA-AUTH-TYPE
            ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
            ,:PAUTDTL1.PA-MESSAGE-TYPE
            ,:PAUTDTL1.PA-MESSAGE-SOURCE
            ,:PAUTDTL1.PA-AUTH-ID-CODE
            ,:PAUTDTL1.PA-AUTH-RESP-CODE
            ,:PAUTDTL1.PA-AUTH-RESP-REASON
            ,:PAUTDTL1.PA-PROCESSING-CODE
            ,:PAUTDTL1.PA-TRANSACTION-AMT
            ,:PAUTDTL1.PA-APPROVED-AMT
            ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
            ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
            ,:PAUTDTL1.PA-POS-ENTRY-MODE
            ,:PAUTDTL1.PA-MERCHANT-ID
            ,:PAUTDTL1.PA-MERCHANT-NAME
            ,:PAUTDTL1.PA-MERCHANT-CITY
            ,:PAUTDTL1.PA-MERCHANT-STATE
            ,:PAUTDTL1.PA-MERCHANT-ZIP
            ,:PAUTDTL1.PA-TRANSACTION-ID
            ,:PAUTDTL1.PA-MATCH-STATUS
            ,:PAUTDTL1.PA-AUTH-FRAUD
            ,:PAUTDTL1.PA-FRAUD-RPT-DATE
            ,:PAUTDTL1.PAUTDTL1-FILLER
            ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
           FROM
           DBPAUTP0_PAUTDTL1
             WHERE
               KEY_PAUT9CTS = 
             :PAUTDTL1.KEY-PAUT9CTS
             AND
               PAUTSUM0_KEY_ACCNTID = :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
           END-EXEC
      *
           SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
           MOVE SQLCODE TO IRIS-DB-SQLCODE
           IF SQLCODE = ZERO
             MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
                                                  WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
             MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
                                           IO-RTN-USED-KEY-ALPHA(2)(1:8)
             MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
                                               TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                                  TO WS-FB-KEY-AREA(1:6)
             MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
                                                  TO WS-FB-KEY-AREA(7:8)
             MOVE 14 TO WS-FB-KEY-LENGTH
      *
      * PACKED FIELDS VS SIGNED ZONED FITTING
      *
             MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
             WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-18-00(1) TO
             PA-PROCESSING-CODE-N OF PAUTDTL1
             MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
             WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-18-00(1) TO
             PA-POS-ENTRY-MODE-N OF PAUTDTL1
             MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN
             IF HAS-PATHCALLS AND SQL-SELECT-PRIMARY
               PERFORM PAUTDTL1-READ-PATHCALL
             ELSE
               PERFORM PAUTDTL1-SET-IO-AREA
             END-IF
             SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
             MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
                             IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
           END-IF
      *
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM.
      *
       PAUTDTL1-SELECT-PRIMARY-KEY-EX.
           EXIT.
      *
      ******************************************************************
      *    SELECT PATHCALL FOR SEGMENT PAUTDTL1
      ******************************************************************
      *
       PAUTDTL1-READ-PATHCALL SECTION.
      *
           MOVE 'PAUTDTL1-READ-PATHCALL' TO WS-LAST-IORTN-SECTION
      *
           SET HAS-NOT-PATHCALLS TO TRUE
           SET HAS-PATHCALLS-ERROR TO TRUE
           MOVE WS-PATHCALL-LEN TO WS-INIT-PATHCALL-LEN
           MOVE ZERO TO WS-PATHCALL-LEN
           MOVE WS-SEGMENT-LEN TO WS-SAVE-SEGM-LEN
      *
           IF IS-PATHCALL-REVERSE
      *
      *      ADD PAUTDTL1
      *
             MOVE PAUTDTL1(1:PAUTDTL1-LEN)
               TO WS-PATHCALL-AREA(WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
             ADD PAUTDTL1-LEN TO WS-PATHCALL-LEN
      *
      *      SEGMENT: PAUTSUM0
      *
             IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
               MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
                                                  WS-PACKED-FLD-18-00(1)
               MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                  TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      *
               PERFORM PAUTSUM0-SELECT-PRIMARY-KEY
      *
               IF IRIS-SQLCODE NOT = ZERO
                 IF WS-PATHCALL-LEN > ZERO
                   MOVE WS-PATHCALL-LEN TO WS-SEGMENT-LEN
                   MOVE WS-PATHCALL-AREA(1:WS-SEGMENT-LEN)
                                    TO LK-IO-AREA(1:WS-SEGMENT-LEN)
                 END-IF
                 GO TO PAUTDTL1-READ-PATHCALL-EX
               END-IF
      *
               MOVE PAUTSUM0(1:PAUTSUM0-LEN) TO
                 WS-PATHCALL-AREA(WS-PATHCALL-LEN + 1:PAUTSUM0-LEN)
               ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
      *
             END-IF
      *
           ELSE
      *      SEGMENT: PAUTSUM0
      *
             IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
               MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
                                                  WS-PACKED-FLD-18-00(1)
               MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                  TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
      *
               PERFORM PAUTSUM0-SELECT-PRIMARY-KEY
      *
               IF IRIS-SQLCODE NOT = ZERO
                 IF WS-PATHCALL-LEN > ZERO
                   MOVE WS-PATHCALL-LEN TO WS-SEGMENT-LEN
                   MOVE WS-PATHCALL-AREA(1:WS-SEGMENT-LEN)
                                    TO LK-IO-AREA(1:WS-SEGMENT-LEN)
                 END-IF
                 GO TO PAUTDTL1-READ-PATHCALL-EX
               END-IF
      *
               MOVE PAUTSUM0(1:PAUTSUM0-LEN) TO
                 WS-PATHCALL-AREA(WS-PATHCALL-LEN + 1:PAUTSUM0-LEN)
               ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
      *
             END-IF
      *
      *
      *      ADD PAUTDTL1
      *
             MOVE PAUTDTL1(1:PAUTDTL1-LEN)
               TO WS-PATHCALL-AREA(WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
             ADD PAUTDTL1-LEN TO WS-PATHCALL-LEN
           END-IF
      *
      *    RETURN THE PATHCALL AREA
      *
           MOVE WS-PATHCALL-LEN TO WS-SEGMENT-LEN
           MOVE WS-PATHCALL-AREA(1:WS-SEGMENT-LEN)
                                     TO LK-IO-AREA(1:WS-SEGMENT-LEN)
      *
           MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
                                                  WS-PACKED-FLD-18-00(1)
           MOVE WS-PACKED-FLD-CHR(1)(5:6)
                              TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
           MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
                                           IO-RTN-USED-KEY-ALPHA(2)(1:8)
      *
           .
      *
       PAUTDTL1-READ-PATHCALL-EX.
           EXIT.
      *
      ******************************************************************
      *    SELECT FIRST TIME FOR SEGMENT PAUTDTL1
      ******************************************************************
      *
       PAUTDTL1-SELECT-FIRST SECTION.
      *
           MOVE 'PAUTDTL1-SELECT-FIRST' TO WS-LAST-IORTN-SECTION
      *
           MOVE 1 TO WS-CK-POS
           MOVE ZERO TO WS-CK-LEN
           MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
                            TO WS-PACKED-FLD-CHR(1)(5:6)
           COMPUTE WS-CK-LEN = 6
           MOVE WS-PACKED-FLD-CHR(1)(5:6)
                    TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
             MOVE ZERO TO WS-PACKED-FLD-18-00(1)
           END-IF
           MOVE WS-PACKED-FLD-18-00(1) TO
                                        PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
           COMPUTE WS-CK-LEN = 8
           MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
                    TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           IF COMMAND-CODE-LAST
             EXEC SQL
             SELECT
               KEY_PAUT9CTS
              ,PA_AUTH_ORIG_DATE
              ,PA_AUTH_ORIG_TIME
              ,PA_CARD_NUM
              ,PA_AUTH_TYPE
              ,PA_CARD_EXPIRY_DATE
              ,PA_MESSAGE_TYPE
              ,PA_MESSAGE_SOURCE
              ,PA_AUTH_ID_CODE
              ,PA_AUTH_RESP_CODE
              ,PA_AUTH_RESP_REASON
              ,PA_PROCESSING_CODE
              ,PA_TRANSACTION_AMT
              ,PA_APPROVED_AMT
              ,PA_MERCHANT_CATAGORY_CODE
              ,PA_ACQR_COUNTRY_CODE
              ,PA_POS_ENTRY_MODE
              ,PA_MERCHANT_ID
              ,PA_MERCHANT_NAME
              ,PA_MERCHANT_CITY
              ,PA_MERCHANT_STATE
              ,PA_MERCHANT_ZIP
              ,PA_TRANSACTION_ID
              ,PA_MATCH_STATUS
              ,PA_AUTH_FRAUD
              ,PA_FRAUD_RPT_DATE
              ,PAUTDTL1_FILLER
              ,PAUTSUM0_KEY_ACCNTID
             INTO
               :PAUTDTL1.KEY-PAUT9CTS
              ,:PAUTDTL1.PA-AUTH-ORIG-DATE
              ,:PAUTDTL1.PA-AUTH-ORIG-TIME
              ,:PAUTDTL1.PA-CARD-NUM
              ,:PAUTDTL1.PA-AUTH-TYPE
              ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
              ,:PAUTDTL1.PA-MESSAGE-TYPE
              ,:PAUTDTL1.PA-MESSAGE-SOURCE
              ,:PAUTDTL1.PA-AUTH-ID-CODE
              ,:PAUTDTL1.PA-AUTH-RESP-CODE
              ,:PAUTDTL1.PA-AUTH-RESP-REASON
              ,:PAUTDTL1.PA-PROCESSING-CODE
              ,:PAUTDTL1.PA-TRANSACTION-AMT
              ,:PAUTDTL1.PA-APPROVED-AMT
              ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
              ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
              ,:PAUTDTL1.PA-POS-ENTRY-MODE
              ,:PAUTDTL1.PA-MERCHANT-ID
              ,:PAUTDTL1.PA-MERCHANT-NAME
              ,:PAUTDTL1.PA-MERCHANT-CITY
              ,:PAUTDTL1.PA-MERCHANT-STATE
              ,:PAUTDTL1.PA-MERCHANT-ZIP
              ,:PAUTDTL1.PA-TRANSACTION-ID
              ,:PAUTDTL1.PA-MATCH-STATUS
              ,:PAUTDTL1.PA-AUTH-FRAUD
              ,:PAUTDTL1.PA-FRAUD-RPT-DATE
              ,:PAUTDTL1.PAUTDTL1-FILLER
              ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
             FROM
             DBPAUTP0_PAUTDTL1 T1
               WHERE
                 T1.KEY_PAUT9CTS = (
                 SELECT
                   T2.KEY_PAUT9CTS
                 FROM
                   DBPAUTP0_PAUTDTL1 T2
                 WHERE
                   T2.PAUTSUM0_KEY_ACCNTID = 
                 :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
                 AND
                   T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID
                 ORDER BY 1 DESC LIMIT 1
                 )
               AND
                 T1.PAUTSUM0_KEY_ACCNTID = 
               :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
             END-EXEC
           ELSE
             EXEC SQL
               SELECT
                 KEY_PAUT9CTS
                ,PA_AUTH_ORIG_DATE
                ,PA_AUTH_ORIG_TIME
                ,PA_CARD_NUM
                ,PA_AUTH_TYPE
                ,PA_CARD_EXPIRY_DATE
                ,PA_MESSAGE_TYPE
                ,PA_MESSAGE_SOURCE
                ,PA_AUTH_ID_CODE
                ,PA_AUTH_RESP_CODE
                ,PA_AUTH_RESP_REASON
                ,PA_PROCESSING_CODE
                ,PA_TRANSACTION_AMT
                ,PA_APPROVED_AMT
                ,PA_MERCHANT_CATAGORY_CODE
                ,PA_ACQR_COUNTRY_CODE
                ,PA_POS_ENTRY_MODE
                ,PA_MERCHANT_ID
                ,PA_MERCHANT_NAME
                ,PA_MERCHANT_CITY
                ,PA_MERCHANT_STATE
                ,PA_MERCHANT_ZIP
                ,PA_TRANSACTION_ID
                ,PA_MATCH_STATUS
                ,PA_AUTH_FRAUD
                ,PA_FRAUD_RPT_DATE
                ,PAUTDTL1_FILLER
                ,PAUTSUM0_KEY_ACCNTID
               INTO
                 :PAUTDTL1.KEY-PAUT9CTS
                ,:PAUTDTL1.PA-AUTH-ORIG-DATE
                ,:PAUTDTL1.PA-AUTH-ORIG-TIME
                ,:PAUTDTL1.PA-CARD-NUM
                ,:PAUTDTL1.PA-AUTH-TYPE
                ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
                ,:PAUTDTL1.PA-MESSAGE-TYPE
                ,:PAUTDTL1.PA-MESSAGE-SOURCE
                ,:PAUTDTL1.PA-AUTH-ID-CODE
                ,:PAUTDTL1.PA-AUTH-RESP-CODE
                ,:PAUTDTL1.PA-AUTH-RESP-REASON
                ,:PAUTDTL1.PA-PROCESSING-CODE
                ,:PAUTDTL1.PA-TRANSACTION-AMT
                ,:PAUTDTL1.PA-APPROVED-AMT
                ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
                ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
                ,:PAUTDTL1.PA-POS-ENTRY-MODE
                ,:PAUTDTL1.PA-MERCHANT-ID
                ,:PAUTDTL1.PA-MERCHANT-NAME
                ,:PAUTDTL1.PA-MERCHANT-CITY
                ,:PAUTDTL1.PA-MERCHANT-STATE
                ,:PAUTDTL1.PA-MERCHANT-ZIP
                ,:PAUTDTL1.PA-TRANSACTION-ID
                ,:PAUTDTL1.PA-MATCH-STATUS
                ,:PAUTDTL1.PA-AUTH-FRAUD
                ,:PAUTDTL1.PA-FRAUD-RPT-DATE
                ,:PAUTDTL1.PAUTDTL1-FILLER
                ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
               FROM
               DBPAUTP0_PAUTDTL1 T1
               WHERE
                 T1.KEY_PAUT9CTS = (
                 SELECT
                   T2.KEY_PAUT9CTS
                 FROM
                   DBPAUTP0_PAUTDTL1 T2
                 WHERE
                   T2.PAUTSUM0_KEY_ACCNTID = 
                 :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
                 AND
                   T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID
                 ORDER BY 1 LIMIT 1
                 )
               AND
                 T1.PAUTSUM0_KEY_ACCNTID = 
               :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
             END-EXEC
           END-IF
      *
           SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
      *
           MOVE SQLCODE TO IRIS-DB-SQLCODE
           EVALUATE TRUE
           WHEN IRIS-SQL-OK
             MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
                                                  WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
             MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
                                           IO-RTN-USED-KEY-ALPHA(2)(1:8)
             MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
                                               TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                                  TO WS-FB-KEY-AREA(1:6)
             MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
                                                  TO WS-FB-KEY-AREA(7:8)
             MOVE 14 TO WS-FB-KEY-LENGTH
      *
      * PACKED FIELDS VS SIGNED ZONED FITTING
      *
             MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
             WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-18-00(1) TO
             PA-PROCESSING-CODE-N OF PAUTDTL1
             MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
             WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-18-00(1) TO
             PA-POS-ENTRY-MODE-N OF PAUTDTL1
             MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN
             IF HAS-PATHCALLS
               PERFORM PAUTDTL1-READ-PATHCALL
             ELSE
               PERFORM PAUTDTL1-SET-IO-AREA
             END-IF
             SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
             MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
                             IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
           WHEN IRIS-SQL-NOT-FOUND
             MOVE SQLCODE TO WS-SAVE-ROUTINE-SQLCODE
             MOVE SQLCA TO WS-SAVE-ROUTINE-SQLCA
             MOVE WS-SAVE-ROUTINE-SQLCODE TO SQLCODE
             MOVE WS-SAVE-ROUTINE-SQLCA TO SQLCA
           END-EVALUATE
      *
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM.
      *
       PAUTDTL1-SELECT-FIRST-EX.
           EXIT.
      *
      ******************************************************************
      *    SELECT NEXT FOR SEGMENT PAUTDTL1
      ******************************************************************
      *
       PAUTDTL1-SELECT-NEXT SECTION.
      *
           MOVE 'PAUTDTL1-SELECT-NEXT' TO WS-LAST-IORTN-SECTION
      *
           MOVE 1 TO WS-CK-POS
           MOVE ZERO TO WS-CK-LEN
           MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
                            TO WS-PACKED-FLD-CHR(1)(5:6)
           COMPUTE WS-CK-LEN = 6
           MOVE WS-PACKED-FLD-CHR(1)(5:6)
                    TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
             MOVE ZERO TO WS-PACKED-FLD-18-00(1)
           END-IF
           MOVE WS-PACKED-FLD-18-00(1) TO
                                        PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
           COMPUTE WS-CK-LEN = 8
           MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
                    TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           MOVE IO-RTN-USED-KEY-ALPHA(2)(1:8) TO
                                           KEY-PAUT9CTS OF PAUTDTL1(1:8)
           IF COMMAND-CODE-LAST
             EXEC SQL
             SELECT
               KEY_PAUT9CTS
              ,PA_AUTH_ORIG_DATE
              ,PA_AUTH_ORIG_TIME
              ,PA_CARD_NUM
              ,PA_AUTH_TYPE
              ,PA_CARD_EXPIRY_DATE
              ,PA_MESSAGE_TYPE
              ,PA_MESSAGE_SOURCE
              ,PA_AUTH_ID_CODE
              ,PA_AUTH_RESP_CODE
              ,PA_AUTH_RESP_REASON
              ,PA_PROCESSING_CODE
              ,PA_TRANSACTION_AMT
              ,PA_APPROVED_AMT
              ,PA_MERCHANT_CATAGORY_CODE
              ,PA_ACQR_COUNTRY_CODE
              ,PA_POS_ENTRY_MODE
              ,PA_MERCHANT_ID
              ,PA_MERCHANT_NAME
              ,PA_MERCHANT_CITY
              ,PA_MERCHANT_STATE
              ,PA_MERCHANT_ZIP
              ,PA_TRANSACTION_ID
              ,PA_MATCH_STATUS
              ,PA_AUTH_FRAUD
              ,PA_FRAUD_RPT_DATE
              ,PAUTDTL1_FILLER
              ,PAUTSUM0_KEY_ACCNTID
             INTO
               :PAUTDTL1.KEY-PAUT9CTS
              ,:PAUTDTL1.PA-AUTH-ORIG-DATE
              ,:PAUTDTL1.PA-AUTH-ORIG-TIME
              ,:PAUTDTL1.PA-CARD-NUM
              ,:PAUTDTL1.PA-AUTH-TYPE
              ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
              ,:PAUTDTL1.PA-MESSAGE-TYPE
              ,:PAUTDTL1.PA-MESSAGE-SOURCE
              ,:PAUTDTL1.PA-AUTH-ID-CODE
              ,:PAUTDTL1.PA-AUTH-RESP-CODE
              ,:PAUTDTL1.PA-AUTH-RESP-REASON
              ,:PAUTDTL1.PA-PROCESSING-CODE
              ,:PAUTDTL1.PA-TRANSACTION-AMT
              ,:PAUTDTL1.PA-APPROVED-AMT
              ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
              ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
              ,:PAUTDTL1.PA-POS-ENTRY-MODE
              ,:PAUTDTL1.PA-MERCHANT-ID
              ,:PAUTDTL1.PA-MERCHANT-NAME
              ,:PAUTDTL1.PA-MERCHANT-CITY
              ,:PAUTDTL1.PA-MERCHANT-STATE
              ,:PAUTDTL1.PA-MERCHANT-ZIP
              ,:PAUTDTL1.PA-TRANSACTION-ID
              ,:PAUTDTL1.PA-MATCH-STATUS
              ,:PAUTDTL1.PA-AUTH-FRAUD
              ,:PAUTDTL1.PA-FRAUD-RPT-DATE
              ,:PAUTDTL1.PAUTDTL1-FILLER
              ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
             FROM
             DBPAUTP0_PAUTDTL1 T1
               WHERE
                 T1.KEY_PAUT9CTS = (
                 SELECT
                   T2.KEY_PAUT9CTS
                 FROM
                   DBPAUTP0_PAUTDTL1 T2
                 WHERE
                   T2.KEY_PAUT9CTS >
                   :PAUTDTL1.KEY-PAUT9CTS
                 AND
                   T2.PAUTSUM0_KEY_ACCNTID = 
                 :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
                 AND
                   T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID
                 ORDER BY 1 DESC LIMIT 1
                 )
               AND
                 T1.PAUTSUM0_KEY_ACCNTID = 
               :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
             END-EXEC
           ELSE
             EXEC SQL
             SELECT
               KEY_PAUT9CTS
              ,PA_AUTH_ORIG_DATE
              ,PA_AUTH_ORIG_TIME
              ,PA_CARD_NUM
              ,PA_AUTH_TYPE
              ,PA_CARD_EXPIRY_DATE
              ,PA_MESSAGE_TYPE
              ,PA_MESSAGE_SOURCE
              ,PA_AUTH_ID_CODE
              ,PA_AUTH_RESP_CODE
              ,PA_AUTH_RESP_REASON
              ,PA_PROCESSING_CODE
              ,PA_TRANSACTION_AMT
              ,PA_APPROVED_AMT
              ,PA_MERCHANT_CATAGORY_CODE
              ,PA_ACQR_COUNTRY_CODE
              ,PA_POS_ENTRY_MODE
              ,PA_MERCHANT_ID
              ,PA_MERCHANT_NAME
              ,PA_MERCHANT_CITY
              ,PA_MERCHANT_STATE
              ,PA_MERCHANT_ZIP
              ,PA_TRANSACTION_ID
              ,PA_MATCH_STATUS
              ,PA_AUTH_FRAUD
              ,PA_FRAUD_RPT_DATE
              ,PAUTDTL1_FILLER
              ,PAUTSUM0_KEY_ACCNTID
             INTO
               :PAUTDTL1.KEY-PAUT9CTS
              ,:PAUTDTL1.PA-AUTH-ORIG-DATE
              ,:PAUTDTL1.PA-AUTH-ORIG-TIME
              ,:PAUTDTL1.PA-CARD-NUM
              ,:PAUTDTL1.PA-AUTH-TYPE
              ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
              ,:PAUTDTL1.PA-MESSAGE-TYPE
              ,:PAUTDTL1.PA-MESSAGE-SOURCE
              ,:PAUTDTL1.PA-AUTH-ID-CODE
              ,:PAUTDTL1.PA-AUTH-RESP-CODE
              ,:PAUTDTL1.PA-AUTH-RESP-REASON
              ,:PAUTDTL1.PA-PROCESSING-CODE
              ,:PAUTDTL1.PA-TRANSACTION-AMT
              ,:PAUTDTL1.PA-APPROVED-AMT
              ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
              ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
              ,:PAUTDTL1.PA-POS-ENTRY-MODE
              ,:PAUTDTL1.PA-MERCHANT-ID
              ,:PAUTDTL1.PA-MERCHANT-NAME
              ,:PAUTDTL1.PA-MERCHANT-CITY
              ,:PAUTDTL1.PA-MERCHANT-STATE
              ,:PAUTDTL1.PA-MERCHANT-ZIP
              ,:PAUTDTL1.PA-TRANSACTION-ID
              ,:PAUTDTL1.PA-MATCH-STATUS
              ,:PAUTDTL1.PA-AUTH-FRAUD
              ,:PAUTDTL1.PA-FRAUD-RPT-DATE
              ,:PAUTDTL1.PAUTDTL1-FILLER
              ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
             FROM
             DBPAUTP0_PAUTDTL1 T1
               WHERE
                 T1.KEY_PAUT9CTS = (
                 SELECT
                   T2.KEY_PAUT9CTS
                 FROM
                   DBPAUTP0_PAUTDTL1 T2
                 WHERE
                   T2.KEY_PAUT9CTS >
                   :PAUTDTL1.KEY-PAUT9CTS
                 AND
                   T2.PAUTSUM0_KEY_ACCNTID = 
                 :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
                 AND
                   T1.PAUTSUM0_KEY_ACCNTID = T2.PAUTSUM0_KEY_ACCNTID
                 ORDER BY 1 LIMIT 1
                 )
               AND
                 T1.PAUTSUM0_KEY_ACCNTID = 
               :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
             END-EXEC
           END-IF
      *
           SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
           MOVE SQLCODE TO IRIS-DB-SQLCODE
           EVALUATE TRUE
           WHEN IRIS-SQL-OK
             MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
                                                  WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
             MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
                                           IO-RTN-USED-KEY-ALPHA(2)(1:8)
             MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
                                               TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                                  TO WS-FB-KEY-AREA(1:6)
             MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
                                                  TO WS-FB-KEY-AREA(7:8)
             MOVE 14 TO WS-FB-KEY-LENGTH
      *
      * PACKED FIELDS VS SIGNED ZONED FITTING
      *
             MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
             WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-18-00(1) TO
             PA-PROCESSING-CODE-N OF PAUTDTL1
             MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
             WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-18-00(1) TO
             PA-POS-ENTRY-MODE-N OF PAUTDTL1
             MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN
             IF HAS-PATHCALLS
               PERFORM PAUTDTL1-READ-PATHCALL
             ELSE
               PERFORM PAUTDTL1-SET-IO-AREA
             END-IF
             SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
             MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
                             IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
           WHEN IRIS-SQL-NOT-FOUND
             MOVE SQLCODE TO WS-SAVE-ROUTINE-SQLCODE
             MOVE SQLCA TO WS-SAVE-ROUTINE-SQLCA
             MOVE WS-SAVE-ROUTINE-SQLCODE TO SQLCODE
             MOVE WS-SAVE-ROUTINE-SQLCA TO SQLCA
           END-EVALUATE
      *
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM.
      *
       PAUTDTL1-SELECT-NEXT-EX.
           EXIT.
      *
      ******************************************************************
      *    DYNAMIC SELECT FOR SEGMENT PAUTDTL1
      ******************************************************************
      *
       PAUTDTL1-SELECT-DYNAMIC SECTION.
      *
           MOVE 'PAUTDTL1-SELECT-DYNAMIC' TO WS-LAST-IORTN-SECTION
      *
           MOVE 1 TO WS-CK-POS
           MOVE ZERO TO WS-CK-LEN
           MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
                            TO WS-PACKED-FLD-CHR(1)(5:6)
           COMPUTE WS-CK-LEN = 6
           MOVE WS-PACKED-FLD-CHR(1)(5:6)
                    TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
             MOVE ZERO TO WS-PACKED-FLD-18-00(1)
           END-IF
           MOVE WS-PACKED-FLD-18-00(1) TO
                                        PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
           COMPUTE WS-CK-LEN = 8
           MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
                    TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
      *    PREPARE THE WHERE CONDITION IF ANY
      *
           MOVE ZERO TO WS-WHERE-LEN WS-ORDERBY-LEN
           IF SQL-CONDITION-CLAUSE-LENGTH > 0
             SUBTRACT 1 FROM SQL-CONDITION-CLAUSE-LENGTH
             STRING 'WHERE '
             SQL-CONDITION-CLAUSE-TEXT(1:SQL-CONDITION-CLAUSE-LENGTH)
             ' ' DELIMITED BY SIZE INTO WS-WHERE
             COMPUTE WS-WHERE-LEN = SQL-CONDITION-CLAUSE-LENGTH + 7
           END-IF
      *
           IF SQL-ORDERBY-CLAUSE-LENGTH > 0
             SUBTRACT 1 FROM SQL-ORDERBY-CLAUSE-LENGTH
             MOVE SQL-ORDERBY-CLAUSE-TEXT(1:SQL-ORDERBY-CLAUSE-LENGTH)
                            TO WS-ORDERBY(1:SQL-ORDERBY-CLAUSE-LENGTH)
             MOVE SQL-ORDERBY-CLAUSE-LENGTH TO WS-ORDERBY-LEN
           ELSE
             COMPUTE WS-ORDERBY-LEN = 1
             STRING ' ORDER BY '
                   ' DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID, '
                   ' KEY_PAUT9CTS '
             DELIMITED BY SIZE INTO WS-ORDERBY POINTER WS-ORDERBY-LEN
             SUBTRACT 1 FROM WS-ORDERBY-LEN
           END-IF
      *
           IF COMMAND-CODE-LAST
             STRING WS-ORDERBY(1:WS-ORDERBY-LEN)
             ' DESC ' DELIMITED BY SIZE
             INTO WS-ORDERBY
             ADD 6 TO WS-ORDERBY-LEN
           END-IF
      *
      *
      *    PREPARE THE DYNAMIC QUERY
      *
           MOVE 1 TO WS-SQL-STM-LEN
           STRING
           'SELECT '
             'DBPAUTP0_PAUTDTL1.KEY_PAUT9CTS '
            ',DBPAUTP0_PAUTDTL1.PA_AUTH_ORIG_DATE '
            ',DBPAUTP0_PAUTDTL1.PA_AUTH_ORIG_TIME '
            ',DBPAUTP0_PAUTDTL1.PA_CARD_NUM '
            ',DBPAUTP0_PAUTDTL1.PA_AUTH_TYPE '
            ',DBPAUTP0_PAUTDTL1.PA_CARD_EXPIRY_DATE '
            ',DBPAUTP0_PAUTDTL1.PA_MESSAGE_TYPE '
            ',DBPAUTP0_PAUTDTL1.PA_MESSAGE_SOURCE '
            ',DBPAUTP0_PAUTDTL1.PA_AUTH_ID_CODE '
            ',DBPAUTP0_PAUTDTL1.PA_AUTH_RESP_CODE '
            ',DBPAUTP0_PAUTDTL1.PA_AUTH_RESP_REASON '
            ',DBPAUTP0_PAUTDTL1.PA_PROCESSING_CODE '
            ',DBPAUTP0_PAUTDTL1.PA_TRANSACTION_AMT '
            ',DBPAUTP0_PAUTDTL1.PA_APPROVED_AMT '
            ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_CATAGORY_CODE '
            ',DBPAUTP0_PAUTDTL1.PA_ACQR_COUNTRY_CODE '
            ',DBPAUTP0_PAUTDTL1.PA_POS_ENTRY_MODE '
            ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_ID '
            ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_NAME '
            ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_CITY '
            ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_STATE '
            ',DBPAUTP0_PAUTDTL1.PA_MERCHANT_ZIP '
            ',DBPAUTP0_PAUTDTL1.PA_TRANSACTION_ID '
            ',DBPAUTP0_PAUTDTL1.PA_MATCH_STATUS '
            ',DBPAUTP0_PAUTDTL1.PA_AUTH_FRAUD '
            ',DBPAUTP0_PAUTDTL1.PA_FRAUD_RPT_DATE '
            ',DBPAUTP0_PAUTDTL1.PAUTDTL1_FILLER '
            ',DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID '
           'FROM '
           'DBPAUTP0_PAUTDTL1 '
           DELIMITED BY SIZE
           INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
           IF SQL-JOIN-CLAUSE-LENGTH > 0
             SUBTRACT 1 FROM SQL-JOIN-CLAUSE-LENGTH
             STRING SQL-JOIN-CLAUSE-TEXT(1:SQL-JOIN-CLAUSE-LENGTH) ' '
             DELIMITED BY SIZE
             INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
           END-IF
           IF WS-WHERE-LEN > 0
             STRING
             WS-WHERE(1:WS-WHERE-LEN)
             DELIMITED BY SIZE
             INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
             IF IRIS-TRACE-FULL
               MOVE 0 TO IRIS-MSG-LEN
               STRING '<IRISTRACE> - DBPAUTP0:SELECT-DYNAMIC' NL
                    ' SECTION    =(PAUTDTL1-SELECT-DYNAMIC) ' NL
                    ' CONDITION  =(' WS-WHERE(1:WS-WHERE-LEN) ')' NL
                    ' ORDER BY   =(' WS-ORDERBY(1:WS-ORDERBY-LEN) ')'
               MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
               SET IRISTRAC-RTN TO TRUE
               CALL IRIS-WS-RTN USING IRIS-WORK-AREA
             END-IF
           END-IF
           IF WS-ORDERBY-LEN > 0
             STRING WS-ORDERBY(1:WS-ORDERBY-LEN) DELIMITED BY SIZE
             INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
           END-IF
           STRING ' FETCH FIRST 1 ROW ONLY '
           DELIMITED BY SIZE
           INTO WS-SQL-STM-TXT POINTER WS-SQL-STM-LEN
           SUBTRACT 1 FROM WS-SQL-STM-LEN
      *
      *    DECLARE THE DYNAMIC CURSOR
      *
           EXEC SQL
             DECLARE PAUTDTL1_CRS CURSOR
             FOR PAUTDTL1_STATEMENT
           END-EXEC
      *
      *    DECLARE THE SQL STATEMENT
      *
           EXEC SQL
             DECLARE PAUTDTL1_STATEMENT STATEMENT
           END-EXEC
      *
      *    PREPARE THE STATEMENT
      *
           EXEC SQL
             PREPARE PAUTDTL1_STATEMENT
             INTO :SQLDA
             FROM :WS-SQL-STM
           END-EXEC
      *
      *    TEST THE RETURN CODE
      *
           IF SQLCODE NOT = ZERO
             MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
             MOVE SQLERRM TO IRIS-SQLERRM
             GO TO PAUTDTL1-SELECT-DYNAMIC-EX
           END-IF
      *
      *    OPEN THE DYNAMIC CURSOR
      *
           EXEC SQL
             OPEN PAUTDTL1_CRS
           END-EXEC
      *
      *    TEST THE RETURN CODE
      *
           IF SQLCODE NOT = ZERO
             MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
             MOVE SQLERRM TO IRIS-SQLERRM
             GO TO PAUTDTL1-SELECT-DYNAMIC-EX
           END-IF
      *
      *    FETCH THE DYNAMIC CURSOR
      *
           EXEC SQL
             FETCH PAUTDTL1_CRS
             INTO
               :PAUTDTL1.KEY-PAUT9CTS
              ,:PAUTDTL1.PA-AUTH-ORIG-DATE
              ,:PAUTDTL1.PA-AUTH-ORIG-TIME
              ,:PAUTDTL1.PA-CARD-NUM
              ,:PAUTDTL1.PA-AUTH-TYPE
              ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
              ,:PAUTDTL1.PA-MESSAGE-TYPE
              ,:PAUTDTL1.PA-MESSAGE-SOURCE
              ,:PAUTDTL1.PA-AUTH-ID-CODE
              ,:PAUTDTL1.PA-AUTH-RESP-CODE
              ,:PAUTDTL1.PA-AUTH-RESP-REASON
              ,:PAUTDTL1.PA-PROCESSING-CODE
              ,:PAUTDTL1.PA-TRANSACTION-AMT
              ,:PAUTDTL1.PA-APPROVED-AMT
              ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
              ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
              ,:PAUTDTL1.PA-POS-ENTRY-MODE
              ,:PAUTDTL1.PA-MERCHANT-ID
              ,:PAUTDTL1.PA-MERCHANT-NAME
              ,:PAUTDTL1.PA-MERCHANT-CITY
              ,:PAUTDTL1.PA-MERCHANT-STATE
              ,:PAUTDTL1.PA-MERCHANT-ZIP
              ,:PAUTDTL1.PA-TRANSACTION-ID
              ,:PAUTDTL1.PA-MATCH-STATUS
              ,:PAUTDTL1.PA-AUTH-FRAUD
              ,:PAUTDTL1.PA-FRAUD-RPT-DATE
              ,:PAUTDTL1.PAUTDTL1-FILLER
              ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
           END-EXEC
      *
           IF SQLCODE NOT = ZERO
             MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
             MOVE SQLERRM TO IRIS-SQLERRM
      *
      *      CLOSE THE DYNAMIC CURSOR
      *
             EXEC SQL
               CLOSE PAUTDTL1_CRS
             END-EXEC
      *
             MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
             IF IRIS-SQL-NOT-FOUND
             AND HAS-PATHCALLS
             AND HAS-PATHCALLS-ERROR
             AND SQL-SELECT-DYNAMIC
               SET IRIS-ERR-RTN-UNHANDLED-ACCESS TO TRUE
               MOVE 0 TO IRIS-MSG-LEN
               STRING '<IRISTRACE> - DBPAUTP0:SELECT-DYNAMIC' NL
                    ' SECTION    =(PAUTDTL1-SELECT-DYNAMIC) ' NL
               ' ERROR      =(PATHCALL ACCESS NOT SUPPORTED FOR RC=GE)'
               MESSAGE-END DELIMITED BY SIZE INTO IRIS-MSG-TXT
               SET IRISTRAC-RTN TO TRUE
               CALL IRIS-WS-RTN USING IRIS-WORK-AREA
               MOVE -1 TO IRIS-SQLCODE
             END-IF
      *
             GO TO PAUTDTL1-SELECT-DYNAMIC-EX
           END-IF
      *
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM
      *
      *      CLOSE THE DYNAMIC CURSOR
      *
           EXEC SQL
             CLOSE PAUTDTL1_CRS
           END-EXEC
      *
           SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
      *
           MOVE IRIS-SQLCODE TO IRIS-DB-SQLCODE
           EVALUATE TRUE
           WHEN IRIS-SQL-OK
             MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
                                                  WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
             MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
                                           IO-RTN-USED-KEY-ALPHA(2)(1:8)
             MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
                                               TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                                  TO WS-FB-KEY-AREA(1:6)
             MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
                                                  TO WS-FB-KEY-AREA(7:8)
             MOVE 14 TO WS-FB-KEY-LENGTH
             IF SQL-SELECT-DYNAMIC
             AND NOT (COMMAND-WITH-HOLD
             AND (NOT SQL-SELECT-DYNAMIC
              OR (SQL-SELECT-DYNAMIC
                  AND NOT HAS-PATHCALLS)))
      *
      * PACKED FIELDS VS SIGNED ZONED FITTING
      *
               MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
               WS-PACKED-FLD-18-00(1)
               MOVE WS-PACKED-FLD-18-00(1) TO
               PA-PROCESSING-CODE-N OF PAUTDTL1
               MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
               WS-PACKED-FLD-18-00(1)
               MOVE WS-PACKED-FLD-18-00(1) TO
               PA-POS-ENTRY-MODE-N OF PAUTDTL1
               MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN
               IF HAS-PATHCALLS
                 PERFORM PAUTDTL1-READ-PATHCALL
               ELSE
                 PERFORM PAUTDTL1-SET-IO-AREA
               END-IF
             END-IF
             SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
             MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
                             IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
           END-EVALUATE.
      *
       PAUTDTL1-SELECT-DYNAMIC-EX.
           EXIT.
      *
      ******************************************************************
      *    SELECT STATIC FOR SEGMENT PAUTDTL1
      ******************************************************************
      *
       PAUTDTL1-SELECT-STATIC-CA SECTION.
      *
           MOVE 'PAUTDTL1-SELECT-STATIC-CA' TO WS-LAST-IORTN-SECTION
      *
      *
      *    EXECUTE THE STATIC QUERY
      *
           EVALUATE IRIS-CUSTOM-FUNC-ID
           WHEN 0
             SET IRIS-ERR-RTN-UNHANDLED-ACCESS TO TRUE
             GO TO PAUTDTL1-SELECT-STATIC-CA-EX
           WHEN 12
             IF WS-SEGMENT-NAME NOT =
                IO-RTN-USED-LAST-SEGMENT(WS-SEGMENT-LEVEL)
             OR IRIS-CODE-FIRST(WS-SEGMENT-LEVEL)
               EXEC SQL
                 SELECT
                   KEY_PAUT9CTS
                  ,PA_AUTH_ORIG_DATE
                  ,PA_AUTH_ORIG_TIME
                  ,PA_CARD_NUM
                  ,PA_AUTH_TYPE
                  ,PA_CARD_EXPIRY_DATE
                  ,PA_MESSAGE_TYPE
                  ,PA_MESSAGE_SOURCE
                  ,PA_AUTH_ID_CODE
                  ,PA_AUTH_RESP_CODE
                  ,PA_AUTH_RESP_REASON
                  ,PA_PROCESSING_CODE
                  ,PA_TRANSACTION_AMT
                  ,PA_APPROVED_AMT
                  ,PA_MERCHANT_CATAGORY_CODE
                  ,PA_ACQR_COUNTRY_CODE
                  ,PA_POS_ENTRY_MODE
                  ,PA_MERCHANT_ID
                  ,PA_MERCHANT_NAME
                  ,PA_MERCHANT_CITY
                  ,PA_MERCHANT_STATE
                  ,PA_MERCHANT_ZIP
                  ,PA_TRANSACTION_ID
                  ,PA_MATCH_STATUS
                  ,PA_AUTH_FRAUD
                  ,PA_FRAUD_RPT_DATE
                  ,PAUTDTL1_FILLER
                  ,PAUTSUM0_KEY_ACCNTID
                 INTO
                   :PAUTDTL1.KEY-PAUT9CTS
                  ,:PAUTDTL1.PA-AUTH-ORIG-DATE
                  ,:PAUTDTL1.PA-AUTH-ORIG-TIME
                  ,:PAUTDTL1.PA-CARD-NUM
                  ,:PAUTDTL1.PA-AUTH-TYPE
                  ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
                  ,:PAUTDTL1.PA-MESSAGE-TYPE
                  ,:PAUTDTL1.PA-MESSAGE-SOURCE
                  ,:PAUTDTL1.PA-AUTH-ID-CODE
                  ,:PAUTDTL1.PA-AUTH-RESP-CODE
                  ,:PAUTDTL1.PA-AUTH-RESP-REASON
                  ,:PAUTDTL1.PA-PROCESSING-CODE
                  ,:PAUTDTL1.PA-TRANSACTION-AMT
                  ,:PAUTDTL1.PA-APPROVED-AMT
                  ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
                  ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
                  ,:PAUTDTL1.PA-POS-ENTRY-MODE
                  ,:PAUTDTL1.PA-MERCHANT-ID
                  ,:PAUTDTL1.PA-MERCHANT-NAME
                  ,:PAUTDTL1.PA-MERCHANT-CITY
                  ,:PAUTDTL1.PA-MERCHANT-STATE
                  ,:PAUTDTL1.PA-MERCHANT-ZIP
                  ,:PAUTDTL1.PA-TRANSACTION-ID
                  ,:PAUTDTL1.PA-MATCH-STATUS
                  ,:PAUTDTL1.PA-AUTH-FRAUD
                  ,:PAUTDTL1.PA-FRAUD-RPT-DATE
                  ,:PAUTDTL1.PAUTDTL1-FILLER
                  ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
                 FROM
                 DBPAUTP0_PAUTDTL1
                 WHERE
                   (KEY_PAUT9CTS = :WS-HV-001-LEN008-X)
                   AND (PAUTSUM0_KEY_ACCNTID = :WS-HV-002-LEN006-P)
                 ORDER BY
                   DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID,
                   KEY_PAUT9CTS
                 FETCH FIRST 1 ROW ONLY
               END-EXEC
             ELSE
               EXEC SQL
                 SELECT
                   KEY_PAUT9CTS
                  ,PA_AUTH_ORIG_DATE
                  ,PA_AUTH_ORIG_TIME
                  ,PA_CARD_NUM
                  ,PA_AUTH_TYPE
                  ,PA_CARD_EXPIRY_DATE
                  ,PA_MESSAGE_TYPE
                  ,PA_MESSAGE_SOURCE
                  ,PA_AUTH_ID_CODE
                  ,PA_AUTH_RESP_CODE
                  ,PA_AUTH_RESP_REASON
                  ,PA_PROCESSING_CODE
                  ,PA_TRANSACTION_AMT
                  ,PA_APPROVED_AMT
                  ,PA_MERCHANT_CATAGORY_CODE
                  ,PA_ACQR_COUNTRY_CODE
                  ,PA_POS_ENTRY_MODE
                  ,PA_MERCHANT_ID
                  ,PA_MERCHANT_NAME
                  ,PA_MERCHANT_CITY
                  ,PA_MERCHANT_STATE
                  ,PA_MERCHANT_ZIP
                  ,PA_TRANSACTION_ID
                  ,PA_MATCH_STATUS
                  ,PA_AUTH_FRAUD
                  ,PA_FRAUD_RPT_DATE
                  ,PAUTDTL1_FILLER
                  ,PAUTSUM0_KEY_ACCNTID
                 INTO
                   :PAUTDTL1.KEY-PAUT9CTS
                  ,:PAUTDTL1.PA-AUTH-ORIG-DATE
                  ,:PAUTDTL1.PA-AUTH-ORIG-TIME
                  ,:PAUTDTL1.PA-CARD-NUM
                  ,:PAUTDTL1.PA-AUTH-TYPE
                  ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
                  ,:PAUTDTL1.PA-MESSAGE-TYPE
                  ,:PAUTDTL1.PA-MESSAGE-SOURCE
                  ,:PAUTDTL1.PA-AUTH-ID-CODE
                  ,:PAUTDTL1.PA-AUTH-RESP-CODE
                  ,:PAUTDTL1.PA-AUTH-RESP-REASON
                  ,:PAUTDTL1.PA-PROCESSING-CODE
                  ,:PAUTDTL1.PA-TRANSACTION-AMT
                  ,:PAUTDTL1.PA-APPROVED-AMT
                  ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
                  ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
                  ,:PAUTDTL1.PA-POS-ENTRY-MODE
                  ,:PAUTDTL1.PA-MERCHANT-ID
                  ,:PAUTDTL1.PA-MERCHANT-NAME
                  ,:PAUTDTL1.PA-MERCHANT-CITY
                  ,:PAUTDTL1.PA-MERCHANT-STATE
                  ,:PAUTDTL1.PA-MERCHANT-ZIP
                  ,:PAUTDTL1.PA-TRANSACTION-ID
                  ,:PAUTDTL1.PA-MATCH-STATUS
                  ,:PAUTDTL1.PA-AUTH-FRAUD
                  ,:PAUTDTL1.PA-FRAUD-RPT-DATE
                  ,:PAUTDTL1.PAUTDTL1-FILLER
                  ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
                 FROM
                 DBPAUTP0_PAUTDTL1
                 WHERE
                   (KEY_PAUT9CTS = :WS-HV-001-LEN008-X)
                   AND (PAUTSUM0_KEY_ACCNTID = :WS-HV-002-LEN006-P)
                   AND KEY_PAUT9CTS > :WS-HV-003-LEN008-X
                 ORDER BY
                   DBPAUTP0_PAUTDTL1.PAUTSUM0_KEY_ACCNTID,
                   KEY_PAUT9CTS
                 FETCH FIRST 1 ROW ONLY
               END-EXEC
             END-IF
           WHEN OTHER
             SET IRIS-ERR-FUNCTION-NOT-FOUND TO TRUE
             GO TO PAUTDTL1-SELECT-STATIC-CA-EX
           END-EVALUATE
      *
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM
      *
      *    TEST THE RETURN CODE
      *
           IF SQLCODE NOT = ZERO
             GO TO PAUTDTL1-SELECT-STATIC-CA-EX
           END-IF
      *
           SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
      *
           MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
                                                  WS-PACKED-FLD-18-00(1)
           MOVE WS-PACKED-FLD-CHR(1)(5:6)
                              TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
           MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
                                           IO-RTN-USED-KEY-ALPHA(2)(1:8)
           MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
                                               TO WS-PACKED-FLD-18-00(1)
           MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                                  TO WS-FB-KEY-AREA(1:6)
           MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
                                                  TO WS-FB-KEY-AREA(7:8)
           MOVE 14 TO WS-FB-KEY-LENGTH
      *
      * PACKED FIELDS VS SIGNED ZONED FITTING
      *
           MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
           WS-PACKED-FLD-18-00(1)
           MOVE WS-PACKED-FLD-18-00(1) TO
           PA-PROCESSING-CODE-N OF PAUTDTL1
           MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
           WS-PACKED-FLD-18-00(1)
           MOVE WS-PACKED-FLD-18-00(1) TO
           PA-POS-ENTRY-MODE-N OF PAUTDTL1
           MOVE PAUTDTL1-LEN TO WS-SEGMENT-LEN
           IF HAS-PATHCALLS
             PERFORM PAUTDTL1-READ-PATHCALL
           ELSE
             PERFORM PAUTDTL1-SET-IO-AREA
           END-IF
      *
           SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
           MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
                             IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL).
      *
       PAUTDTL1-SELECT-STATIC-CA-EX.
           EXIT.
      *
      ******************************************************************
      *    INSERT FOR SEGMENT PAUTDTL1
      ******************************************************************
      *
       PAUTDTL1-INSERT SECTION.
      *
           MOVE 'PAUTDTL1-INSERT' TO WS-LAST-IORTN-SECTION
      *
           IF HAS-PATHCALLS
             PERFORM PAUTDTL1-INSERT-PATHCALL
             GO TO PAUTDTL1-INSERT-EX
           END-IF
      *
           INITIALIZE PAUTDTL1
      *
           MOVE LK-IO-AREA(1:PAUTDTL1-LEN) TO PAUTDTL1(1:PAUTDTL1-LEN)
           IF PA-PROCESSING-CODE-N
           OF PAUTDTL1 NOT NUMERIC
             MOVE ZERO TO
             PA-PROCESSING-CODE-N OF PAUTDTL1
           END-IF
           IF PA-TRANSACTION-AMT
           OF PAUTDTL1 NOT NUMERIC
             MOVE ZERO TO
             PA-TRANSACTION-AMT OF PAUTDTL1
           END-IF
           IF PA-APPROVED-AMT
           OF PAUTDTL1 NOT NUMERIC
             MOVE ZERO TO
             PA-APPROVED-AMT OF PAUTDTL1
           END-IF
           IF PA-POS-ENTRY-MODE-N
           OF PAUTDTL1 NOT NUMERIC
             MOVE ZERO TO
             PA-POS-ENTRY-MODE-N OF PAUTDTL1
           END-IF
           IF PAUTSUM0-KEY-ACCNTID
           OF PAUTDTL1 NOT NUMERIC
             MOVE ZERO TO
             PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
           END-IF
      *
      * SIGNED ZONED FIELDS VS PACKED FITTING
      *
           MOVE PA-PROCESSING-CODE-N OF PAUTDTL1 TO
           WS-PACKED-FLD-18-00(1)
           MOVE WS-PACKED-FLD-18-00(1) TO
           PA-PROCESSING-CODE OF PAUTDTL1
           MOVE PA-POS-ENTRY-MODE-N OF PAUTDTL1 TO
           WS-PACKED-FLD-18-00(1)
           MOVE WS-PACKED-FLD-18-00(1) TO
           PA-POS-ENTRY-MODE OF PAUTDTL1
           MOVE 1 TO WS-CK-POS
           MOVE ZERO TO WS-CK-LEN
           MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
                            TO WS-PACKED-FLD-CHR(1)(5:6)
           COMPUTE WS-CK-LEN = 6
           MOVE WS-PACKED-FLD-CHR(1)(5:6)
                    TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
             MOVE ZERO TO WS-PACKED-FLD-18-00(1)
           END-IF
           MOVE WS-PACKED-FLD-18-00(1) TO
                                        PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
           COMPUTE WS-CK-LEN = 8
           MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
                    TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
      *
      *
           EXEC SQL
             INSERT INTO
               DBPAUTP0_PAUTDTL1
               (
               KEY_PAUT9CTS
              ,PA_AUTH_ORIG_DATE
              ,PA_AUTH_ORIG_TIME
              ,PA_CARD_NUM
              ,PA_AUTH_TYPE
              ,PA_CARD_EXPIRY_DATE
              ,PA_MESSAGE_TYPE
              ,PA_MESSAGE_SOURCE
              ,PA_AUTH_ID_CODE
              ,PA_AUTH_RESP_CODE
              ,PA_AUTH_RESP_REASON
              ,PA_PROCESSING_CODE
              ,PA_TRANSACTION_AMT
              ,PA_APPROVED_AMT
              ,PA_MERCHANT_CATAGORY_CODE
              ,PA_ACQR_COUNTRY_CODE
              ,PA_POS_ENTRY_MODE
              ,PA_MERCHANT_ID
              ,PA_MERCHANT_NAME
              ,PA_MERCHANT_CITY
              ,PA_MERCHANT_STATE
              ,PA_MERCHANT_ZIP
              ,PA_TRANSACTION_ID
              ,PA_MATCH_STATUS
              ,PA_AUTH_FRAUD
              ,PA_FRAUD_RPT_DATE
              ,PAUTDTL1_FILLER
              ,PAUTSUM0_KEY_ACCNTID
               )
             VALUES
             (
               :PAUTDTL1.KEY-PAUT9CTS
              ,:PAUTDTL1.PA-AUTH-ORIG-DATE
              ,:PAUTDTL1.PA-AUTH-ORIG-TIME
              ,:PAUTDTL1.PA-CARD-NUM
              ,:PAUTDTL1.PA-AUTH-TYPE
              ,:PAUTDTL1.PA-CARD-EXPIRY-DATE
              ,:PAUTDTL1.PA-MESSAGE-TYPE
              ,:PAUTDTL1.PA-MESSAGE-SOURCE
              ,:PAUTDTL1.PA-AUTH-ID-CODE
              ,:PAUTDTL1.PA-AUTH-RESP-CODE
              ,:PAUTDTL1.PA-AUTH-RESP-REASON
              ,:PAUTDTL1.PA-PROCESSING-CODE
              ,:PAUTDTL1.PA-TRANSACTION-AMT
              ,:PAUTDTL1.PA-APPROVED-AMT
              ,:PAUTDTL1.PA-MERCHANT-CATAGORY-CODE
              ,:PAUTDTL1.PA-ACQR-COUNTRY-CODE
              ,:PAUTDTL1.PA-POS-ENTRY-MODE
              ,:PAUTDTL1.PA-MERCHANT-ID
              ,:PAUTDTL1.PA-MERCHANT-NAME
              ,:PAUTDTL1.PA-MERCHANT-CITY
              ,:PAUTDTL1.PA-MERCHANT-STATE
              ,:PAUTDTL1.PA-MERCHANT-ZIP
              ,:PAUTDTL1.PA-TRANSACTION-ID
              ,:PAUTDTL1.PA-MATCH-STATUS
              ,:PAUTDTL1.PA-AUTH-FRAUD
              ,:PAUTDTL1.PA-FRAUD-RPT-DATE
              ,:PAUTDTL1.PAUTDTL1-FILLER
              ,:PAUTDTL1.PAUTSUM0-KEY-ACCNTID
             )
           END-EXEC
      *
           SET IO-RTN-USED-KEY-NOT-CHANGED(PAUTDTL1-LVL) TO TRUE
           MOVE SQLCODE TO IRIS-DB-SQLCODE
           EVALUATE TRUE
           WHEN IRIS-SQL-OK
           WHEN IRIS-SQL-UNIQ-CONSTR-VIOLATED
             MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1 TO
                                                  WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                TO IO-RTN-USED-KEY-ALPHA(1)(1:6)
             MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8) TO
                                           IO-RTN-USED-KEY-ALPHA(2)(1:8)
             MOVE PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
                                               TO WS-PACKED-FLD-18-00(1)
             MOVE WS-PACKED-FLD-CHR(1)(5:6)
                                                  TO WS-FB-KEY-AREA(1:6)
             MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
                                                  TO WS-FB-KEY-AREA(7:8)
             MOVE 14 TO WS-FB-KEY-LENGTH
             SET IO-RTN-USED-KEY-CHANGED(PAUTDTL1-LVL) TO TRUE
             IF IRIS-SQL-UNIQ-CONSTR-VIOLATED
               SET IO-RTN-USED-KEY-DUPREC(PAUTDTL1-LVL) TO TRUE
             END-IF
             MOVE 'PAUTDTL1' TO WS-LAST-SEGMENT-NAME
                             IO-RTN-USED-LAST-SEGMENT(PAUTDTL1-LVL)
           END-EVALUATE
      *
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM.
      *
       PAUTDTL1-INSERT-EX.
           EXIT.
      *
      ******************************************************************
      *    INSERT PATHCALL FOR SEGMENT PAUTDTL1
      ******************************************************************
      *
       PAUTDTL1-INSERT-PATHCALL SECTION.
      *
           MOVE 'PAUTDTL1-INSERT-PATHCALL' TO WS-LAST-IORTN-SECTION
      *
      *
           SET HAS-NOT-PATHCALLS TO TRUE
           SET HAS-PATHCALLS-ERROR TO TRUE
           MOVE LK-IO-AREA(1:WS-PATHCALL-LEN)
                      TO WS-PATHCALL-AREA(1:WS-PATHCALL-LEN)
           MOVE WS-PATHCALL-LEN TO WS-INIT-PATHCALL-LEN
           MOVE ZERO TO WS-PATHCALL-LEN
      *
      *    SEGMENT: PAUTSUM0
      *
           IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
      *
             MOVE WS-PATHCALL-AREA
                  (WS-PATHCALL-LEN + 1:PAUTSUM0-LEN)
                                       TO LK-IO-AREA(1:PAUTSUM0-LEN)
      *
             PERFORM PAUTSUM0-INSERT
      *
             IF IRIS-SQLCODE NOT = ZERO
               MOVE WS-PATHCALL-AREA(1:WS-INIT-PATHCALL-LEN)
                                TO LK-IO-AREA(1:WS-INIT-PATHCALL-LEN)
               GO TO PAUTDTL1-INSERT-PATHCALL-EX
             END-IF
      *
             ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
      *
           END-IF
      *
      *
      *    INSERT PAUTDTL1
      *
           MOVE WS-PATHCALL-AREA
                (WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
                                     TO LK-IO-AREA(1:PAUTDTL1-LEN)
      *
           PERFORM PAUTDTL1-INSERT
      *
           MOVE WS-INIT-PATHCALL-LEN TO WS-PATHCALL-LEN WS-SEGMENT-LEN
           MOVE WS-PATHCALL-AREA(1:WS-PATHCALL-LEN)
                                     TO LK-IO-AREA(1:WS-PATHCALL-LEN).
      *
      *
       PAUTDTL1-INSERT-PATHCALL-EX.
           EXIT.
      *
      ******************************************************************
      *    UPDATE PATHCALL FOR SEGMENT PAUTDTL1
      ******************************************************************
      *
       PAUTDTL1-UPDATE-PATHCALL SECTION.
      *
           MOVE 'PAUTDTL1-UPDATE-PATHCALL' TO WS-LAST-IORTN-SECTION
      *
      *
           SET HAS-NOT-PATHCALLS TO TRUE
           SET HAS-PATHCALLS-ERROR TO TRUE
           MOVE LK-IO-AREA(1:WS-PATHCALL-LEN)
                      TO WS-PATHCALL-AREA(1:WS-PATHCALL-LEN)
           MOVE WS-PATHCALL-LEN TO WS-INIT-PATHCALL-LEN
           MOVE ZERO TO WS-PATHCALL-LEN
      *
           IF IS-PATHCALL-REVERSE
      *
      *      SEGMENT: PAUTDTL1
      *
             IF IRIS-CODE-PATHCALL(PAUTDTL1-LVL)
               MOVE WS-PATHCALL-AREA
                    (WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
                                     TO LK-IO-AREA(1:PAUTDTL1-LEN)
      *
               PERFORM PAUTDTL1-UPDATE
      *
               IF IRIS-SQLCODE NOT = ZERO
                 MOVE WS-PATHCALL-AREA(1:WS-INIT-PATHCALL-LEN)
                                  TO LK-IO-AREA(1:WS-INIT-PATHCALL-LEN)
                 GO TO PAUTDTL1-UPDATE-PATHCALL-EX
               END-IF
      *
               ADD PAUTDTL1-LEN TO WS-PATHCALL-LEN
      *
             END-IF
           ELSE
      *
      *      SEGMENT: PAUTSUM0
      *
             IF IRIS-CODE-PATHCALL(PAUTSUM0-LVL)
      *
               MOVE WS-PATHCALL-AREA
                    (WS-PATHCALL-LEN + 1:PAUTSUM0-LEN)
                                       TO LK-IO-AREA(1:PAUTSUM0-LEN)
      *
               PERFORM PAUTSUM0-UPDATE
      *
               IF IRIS-SQLCODE NOT = ZERO
                 MOVE WS-PATHCALL-AREA(1:WS-INIT-PATHCALL-LEN)
                                  TO LK-IO-AREA(1:WS-INIT-PATHCALL-LEN)
                 GO TO PAUTDTL1-UPDATE-PATHCALL-EX
               END-IF
      *
               ADD PAUTSUM0-LEN TO WS-PATHCALL-LEN
      *
             END-IF
      *
      *
      *      UPDATE PAUTDTL1
      *
             MOVE WS-PATHCALL-AREA
                  (WS-PATHCALL-LEN + 1:PAUTDTL1-LEN)
                                       TO LK-IO-AREA(1:PAUTDTL1-LEN)
      *
             PERFORM PAUTDTL1-UPDATE
           END-IF
      *
           MOVE WS-INIT-PATHCALL-LEN TO WS-PATHCALL-LEN WS-SEGMENT-LEN
           MOVE WS-PATHCALL-AREA(1:WS-PATHCALL-LEN)
                                     TO LK-IO-AREA(1:WS-PATHCALL-LEN).
      *
      *
       PAUTDTL1-UPDATE-PATHCALL-EX.
           EXIT.
      *
      ******************************************************************
      *    UPDATE FOR SEGMENT PAUTDTL1
      ******************************************************************
      *
       PAUTDTL1-UPDATE SECTION.
      *
           MOVE 'PAUTDTL1-UPDATE' TO WS-LAST-IORTN-SECTION
      *
      *
           INITIALIZE PAUTDTL1
      *
           IF HAS-PATHCALLS
             PERFORM PAUTDTL1-UPDATE-PATHCALL
             GO TO PAUTDTL1-UPDATE-EX
           END-IF
      *
           MOVE LK-IO-AREA(1:PAUTDTL1-LEN) TO PAUTDTL1(1:PAUTDTL1-LEN)
           IF PA-PROCESSING-CODE-N
           OF PAUTDTL1 NOT NUMERIC
             MOVE ZERO TO
             PA-PROCESSING-CODE-N OF PAUTDTL1
           END-IF
           IF PA-TRANSACTION-AMT
           OF PAUTDTL1 NOT NUMERIC
             MOVE ZERO TO
             PA-TRANSACTION-AMT OF PAUTDTL1
           END-IF
           IF PA-APPROVED-AMT
           OF PAUTDTL1 NOT NUMERIC
             MOVE ZERO TO
             PA-APPROVED-AMT OF PAUTDTL1
           END-IF
           IF PA-POS-ENTRY-MODE-N
           OF PAUTDTL1 NOT NUMERIC
             MOVE ZERO TO
             PA-POS-ENTRY-MODE-N OF PAUTDTL1
           END-IF
           IF PAUTSUM0-KEY-ACCNTID
           OF PAUTDTL1 NOT NUMERIC
             MOVE ZERO TO
             PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
           END-IF
      *
      * SIGNED ZONED FIELDS VS PACKED FITTING
      *
           MOVE PA-PROCESSING-CODE-N OF PAUTDTL1 TO
           WS-PACKED-FLD-18-00(1)
           MOVE WS-PACKED-FLD-18-00(1) TO
           PA-PROCESSING-CODE OF PAUTDTL1
           MOVE PA-POS-ENTRY-MODE-N OF PAUTDTL1 TO
           WS-PACKED-FLD-18-00(1)
           MOVE WS-PACKED-FLD-18-00(1) TO
           PA-POS-ENTRY-MODE OF PAUTDTL1
      *
           MOVE 1 TO WS-CK-POS
           MOVE ZERO TO WS-CK-LEN
           MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
                            TO WS-PACKED-FLD-CHR(1)(5:6)
           COMPUTE WS-CK-LEN = 6
           MOVE WS-PACKED-FLD-CHR(1)(5:6)
                    TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
             MOVE ZERO TO WS-PACKED-FLD-18-00(1)
           END-IF
           MOVE WS-PACKED-FLD-18-00(1) TO
                                        PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
           COMPUTE WS-CK-LEN = 8
           MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
                    TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           MOVE IO-RTN-USED-KEY-ALPHA(2)(1:8) TO
                                           KEY-PAUT9CTS OF PAUTDTL1(1:8)
      *
      *
           EXEC SQL
             UPDATE DBPAUTP0_PAUTDTL1 SET
               PA_AUTH_ORIG_DATE = :PAUTDTL1.PA-AUTH-ORIG-DATE,
               PA_AUTH_ORIG_TIME = :PAUTDTL1.PA-AUTH-ORIG-TIME,
               PA_CARD_NUM = :PAUTDTL1.PA-CARD-NUM,
               PA_AUTH_TYPE = :PAUTDTL1.PA-AUTH-TYPE,
               PA_CARD_EXPIRY_DATE = :PAUTDTL1.PA-CARD-EXPIRY-DATE,
               PA_MESSAGE_TYPE = :PAUTDTL1.PA-MESSAGE-TYPE,
               PA_MESSAGE_SOURCE = :PAUTDTL1.PA-MESSAGE-SOURCE,
               PA_AUTH_ID_CODE = :PAUTDTL1.PA-AUTH-ID-CODE,
               PA_AUTH_RESP_CODE = :PAUTDTL1.PA-AUTH-RESP-CODE,
               PA_AUTH_RESP_REASON = :PAUTDTL1.PA-AUTH-RESP-REASON,
               PA_PROCESSING_CODE = :PAUTDTL1.PA-PROCESSING-CODE,
               PA_TRANSACTION_AMT = :PAUTDTL1.PA-TRANSACTION-AMT,
               PA_APPROVED_AMT = :PAUTDTL1.PA-APPROVED-AMT,
               PA_MERCHANT_CATAGORY_CODE = 
               :PAUTDTL1.PA-MERCHANT-CATAGORY-CODE,
               PA_ACQR_COUNTRY_CODE = :PAUTDTL1.PA-ACQR-COUNTRY-CODE,
               PA_POS_ENTRY_MODE = :PAUTDTL1.PA-POS-ENTRY-MODE,
               PA_MERCHANT_ID = :PAUTDTL1.PA-MERCHANT-ID,
               PA_MERCHANT_NAME = :PAUTDTL1.PA-MERCHANT-NAME,
               PA_MERCHANT_CITY = :PAUTDTL1.PA-MERCHANT-CITY,
               PA_MERCHANT_STATE = :PAUTDTL1.PA-MERCHANT-STATE,
               PA_MERCHANT_ZIP = :PAUTDTL1.PA-MERCHANT-ZIP,
               PA_TRANSACTION_ID = :PAUTDTL1.PA-TRANSACTION-ID,
               PA_MATCH_STATUS = :PAUTDTL1.PA-MATCH-STATUS,
               PA_AUTH_FRAUD = :PAUTDTL1.PA-AUTH-FRAUD,
               PA_FRAUD_RPT_DATE = :PAUTDTL1.PA-FRAUD-RPT-DATE,
               PAUTDTL1_FILLER = :PAUTDTL1.PAUTDTL1-FILLER
             WHERE
               KEY_PAUT9CTS =
             :PAUTDTL1.KEY-PAUT9CTS
             AND
               PAUTSUM0_KEY_ACCNTID = :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
           END-EXEC
      *
      * PACKED FIELDS VS SIGNED ZONED FITTING
      *
           MOVE PA-PROCESSING-CODE OF PAUTDTL1 TO
           WS-PACKED-FLD-18-00(1)
           MOVE WS-PACKED-FLD-18-00(1) TO
           PA-PROCESSING-CODE-N OF PAUTDTL1
           MOVE PA-POS-ENTRY-MODE OF PAUTDTL1 TO
           WS-PACKED-FLD-18-00(1)
           MOVE WS-PACKED-FLD-18-00(1) TO
           PA-POS-ENTRY-MODE-N OF PAUTDTL1
      *
           IF SQLCODE = ZERO
             MOVE LK-IO-AREA(1:PAUTDTL1-LEN)
               TO PAUTDTL1-LAST-AREA
           END-IF
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM.
      *
       PAUTDTL1-UPDATE-EX.
           EXIT.
      *
      ******************************************************************
      *    DELETE FOR SEGMENT PAUTDTL1
      ******************************************************************
      *
       PAUTDTL1-DELETE SECTION.
      *
           MOVE 'PAUTDTL1-DELETE' TO WS-LAST-IORTN-SECTION
      *
      *
           IF HAS-PATHCALLS
             SET IRIS-ERR-RTN-UNHANDLED-ACCESS TO TRUE
             GO TO PAUTDTL1-DELETE-EX
           END-IF
      *
           MOVE 1 TO WS-CK-POS
           MOVE ZERO TO WS-CK-LEN
           MOVE IO-RTN-USED-KEY-ALPHA(1)(1:6)
                            TO WS-PACKED-FLD-CHR(1)(5:6)
           COMPUTE WS-CK-LEN = 6
           MOVE WS-PACKED-FLD-CHR(1)(5:6)
                    TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           IF WS-PACKED-FLD-18-00(1) NOT NUMERIC
             MOVE ZERO TO WS-PACKED-FLD-18-00(1)
           END-IF
           MOVE WS-PACKED-FLD-18-00(1) TO
                                        PAUTSUM0-KEY-ACCNTID OF PAUTDTL1
           COMPUTE WS-CK-LEN = 8
           MOVE KEY-PAUT9CTS OF PAUTDTL1(1:8)
                    TO PAUTDTL1-CONCATENATED-KEY(WS-CK-POS:WS-CK-LEN)
           ADD WS-CK-LEN TO WS-CK-POS
           MOVE IO-RTN-USED-KEY-ALPHA(2)(1:8) TO
                                           KEY-PAUT9CTS OF PAUTDTL1(1:8)
      *
           EXEC SQL
             DELETE FROM DBPAUTP0_PAUTDTL1
             WHERE
               KEY_PAUT9CTS =
             :PAUTDTL1.KEY-PAUT9CTS
             AND
               PAUTSUM0_KEY_ACCNTID = :PAUTDTL1.PAUTSUM0-KEY-ACCNTID
           END-EXEC
      *
           MOVE SQLCODE TO IRIS-SQLCODE IRIS-DB-SQLCODE
           MOVE SQLERRM TO IRIS-SQLERRM.
      *
       PAUTDTL1-DELETE-EX.
           EXIT.
      *
      ******************************************************************
      *    SET I/O AREA PAUTDTL1
      ******************************************************************
      *
       PAUTDTL1-SET-IO-AREA SECTION.
      *
           MOVE 'PAUTDTL1-SET-IO-AREA' TO WS-LAST-IORTN-SECTION
      *
           MOVE PAUTDTL1(1:WS-SEGMENT-LEN)
                          TO LK-IO-AREA(1:WS-SEGMENT-LEN)
                             PAUTDTL1-LAST-AREA.
      *
       PAUTDTL1-SET-IO-AREA-EX.
           EXIT.
      *
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
           SET ADDRESS OF IRIS-DB-PCB  TO ADDRESS OF LK-DIB-BLOCK.
      *
       SET-DIB-BLOCK-EX.
      *
           EXIT.
      *
      *
