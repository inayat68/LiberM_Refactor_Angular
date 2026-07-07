      ******************************************************************
      * Author : mLogica
      * Product: IRIS-DB - v. 5.5.0
      ******************************************************************
      *
      * DESCRIPTION: AREA ALLOCATION API
      *
      ******************************************************************
      *
       IDENTIFICATION DIVISION.
      *
       PROGRAM-ID. IRISADDR.
      *
       DATA DIVISION.
      *
       WORKING-STORAGE SECTION.
      *
      *    COMMON VARIABLES
      *
           COPY IRISCOMM.
      *
       01 WS-ADDR-9 PIC 9(9).
       LINKAGE SECTION.
      *
      *    IRIS GLOBAL AREA
      *
           COPY IRISGLOB REPLACING ==:PROGNM:== BY =='IRISADDR'==.
      *
       01  LK-AREA   PIC X.
      *
       01  LK-PTR    POINTER.
       01  LK-ADDR   REDEFINES LK-PTR PIC S9(9) COMP.
      *
       PROCEDURE DIVISION USING IRIS-WORK-AREA
                                LK-AREA
                                LK-PTR.
       MAIN.
           SET LK-PTR TO ADDRESS OF LK-AREA.
      *
       MAIN-EX.
           GOBACK.
