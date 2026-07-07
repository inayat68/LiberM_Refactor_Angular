      ******************************************************************
      * Author : mLogica
      * Product: IRIS-DB - v. 5.5.0
      ******************************************************************
      *
      * DESCRIPTION: FORMAT AND EMIT USER MESSAGES
      *
      ******************************************************************
      *
       IDENTIFICATION DIVISION.
      *
       PROGRAM-ID. IRISTRAC.
      *
       DATA DIVISION.
      *
       WORKING-STORAGE SECTION.
      *
           COPY IRISCOMM.
      *
       01 WS-LILIAN                       PIC S9(9) COMP.
       01 WS-SECONDS                      PIC S9(18) COMP.
       01 WS-GREGORN                      PIC X(17).
       01 WS-FC.
          03 WS-CEEIGZCT-RC               PIC X.
             88 WS-CEE000                 VALUE LOW-VALUE.
          03 FILLER                       PIC X(11).
      *
      * LINE MAX LENGTH
       01 WS-MAX-LEN                      PIC S9(9) COMP.
      * LINE TO BE PRINT
       01 WS-LINE-LEN                     PIC S9(9) COMP.
       01 WS-LINE-TXT                     PIC X(4096).
      * WORK INDEXES
       01 WS-LINE-POS                     PIC S9(9) COMP.
       01 WS-LINE-PLEN                    PIC S9(9) COMP.
       01 WS-IDX                          PIC S9(9) COMP.
       01 FILLER                          PIC 9              VALUE 0.
         88 NO-SHIFT-LINE                                    VALUE 0.
         88 SHIFT-LINE                                       VALUE 1.
      * DETECT EXECUTION ENVIRONMENT
       01 WS-BIN-BIGEND                   PIC S9(4) COMP     VALUE 1.
       01 WS-BIN-LITEND REDEFINES WS-BIN-BIGEND
                                          PIC S9(4) COMP-5.
         88 IS-ENV-BIGENDIAN                                 VALUE 1.
      *
       LINKAGE SECTION.
      *
           COPY IRISGLOB REPLACING ==:PROGNM:== BY =='IRISTRAC'==.
      *
       PROCEDURE DIVISION USING IRIS-WORK-AREA.
      *
       MAIN.
      *
           PERFORM INIT-VARS THRU INIT-VARS-EX
      *
           IF IRIS-MSG-LEN > ZERO
             MOVE IRIS-MSG-LEN TO WS-LINE-LEN
             MOVE IRIS-MSG-TXT(1:WS-LINE-LEN)
                                      TO WS-LINE-TXT(1:WS-LINE-LEN)
             PERFORM PRINT-LINE THRU PRINT-LINE-EX
           ELSE
             PERFORM PRINT-LOG THRU PRINT-LOG-EX
           END-IF
      *
           GOBACK.
      *
      ******************************************************************
      *    INITIALIZE VARIABLES
      ******************************************************************
      *
       INIT-VARS SECTION.
      *
           COMPUTE WS-MAX-LEN = LENGTH OF IRIS-MSG-TXT
      *
           .
      *
       INIT-VARS-EX.
      *
           EXIT.
      *
      ******************************************************************
      *    REFORMAT AND PRINT THE INPUT MESSAGE
      ******************************************************************
      *
       PRINT-LOG SECTION.
      *
      * REFORMAT THE LINE BEFORE PRINTING
      *
           COMPUTE WS-LINE-POS = 1
           COMPUTE WS-LINE-LEN = 0
           COMPUTE IRIS-MSG-LEN = 0
      *
           SET NO-SHIFT-LINE TO TRUE
           PERFORM VARYING WS-IDX FROM 1 BY 1
           UNTIL WS-IDX >= WS-MAX-LEN
             COMPUTE WS-LINE-PLEN = WS-IDX - WS-LINE-POS + 1
      *      WHEN THE CHARACTER IS A NEW LINE MARKER SPLIT THE LINE
             IF IRIS-MSG-TXT(WS-IDX:1) = NL
             OR IRIS-MSG-TXT(WS-IDX:1) = MESSAGE-END
             OR WS-LINE-PLEN = 79
      *
               COMPUTE WS-LINE-LEN = WS-IDX - WS-LINE-POS
               IF SHIFT-LINE
                 MOVE " " TO WS-LINE-TXT(1:1)
                 MOVE IRIS-MSG-TXT(WS-LINE-POS:WS-LINE-LEN)
                                      TO WS-LINE-TXT(2:WS-LINE-LEN)
                 ADD 1 TO WS-LINE-LEN
               ELSE
                 MOVE IRIS-MSG-TXT(WS-LINE-POS:WS-LINE-LEN)
                                      TO WS-LINE-TXT(1:WS-LINE-LEN)
               END-IF
      *
               IF IRIS-MSG-TXT(WS-IDX:1) = MESSAGE-END
                 MOVE WS-MAX-LEN TO WS-IDX
               ELSE
                 IF WS-LINE-PLEN = 79
                   SET SHIFT-LINE TO TRUE
                   COMPUTE WS-LINE-POS = WS-IDX
                 ELSE
                   SET NO-SHIFT-LINE TO TRUE
                   COMPUTE WS-LINE-POS = WS-IDX + 1
                 END-IF
               END-IF
      *
             END-IF
      *
             IF WS-LINE-LEN > ZERO
               PERFORM PRINT-LINE THRU PRINT-LINE-EX
               COMPUTE WS-LINE-LEN = 0
             END-IF
      *
           END-PERFORM
      *
           .
      *
       PRINT-LOG-EX.
           EXIT.
      *
      ******************************************************************
      *    REDIRECT THE MESSAGE LINE DEPENDING ON THE EXEC ENV
      ******************************************************************
      *
       PRINT-LINE SECTION.
      *
           IF IS-ENV-BIGENDIAN
             DISPLAY WS-LINE-TXT(1:WS-LINE-LEN)
           ELSE
      *      INSPECT WS-LINE-TXT(1:WS-LINE-LEN)
      *      REPLACING ALL LOW-VALUE BY SPACE
      *      DISPLAY WS-LINE-TXT(1:WS-LINE-LEN)
           END-IF
      *
           .
      *
       PRINT-LINE-EX.
           EXIT.
