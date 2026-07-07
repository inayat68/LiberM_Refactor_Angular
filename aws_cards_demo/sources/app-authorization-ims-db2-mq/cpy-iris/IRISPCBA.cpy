      ******************************************************************
      * Author : mLogica
      * Product: IRIS-DB - v. 5.5.0
      ******************************************************************
      *                                                                *
      *   DESCRIPTION: IRIS PCBS ALLOCATION                            *
      *                                                                *
      ******************************************************************
      *
           ADD 1 TO WS-INDEX
           IF IRIS-USED-PCB = ZERO
             MOVE IRIS-PARAM-NUM TO IRIS-USED-PCB
           END-IF
           IF WS-INDEX NOT > IRIS-PARAM-NUM
           AND WS-INDEX NOT > IRIS-USED-PCB
      *
DBG   *      DISPLAY 'IRIS-PCB-PTR-' WS-INDEX '=' IRIS-PCB-PTR-:NPCB:
DBG   *      UPON CONSOLE
      *
             IF IRIS-FINAL
      * FINAL STATE: MANAGE THE PCB FOR THE CONVERTED DBDs
      *
               INITIALIZE IRIS-PCB-HEADER(WS-INDEX)
               SET IRISADDR-RTN TO TRUE
               CALL IRIS-WS-RTN USING IRIS-WORK-AREA
                                      IRIS-PCBS-TAB(WS-INDEX) WS-PTR
INQY  *        SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
INQY  *        SET ADDRESS OF IRIS-DB-PCB TO IRIS-PCB-PTR-:NPCB:
INQY           SET ADDRESS OF IRIS-DB-PCB TO WS-PTR
               SET IRIS-BLL-POINTER(WS-INDEX) TO WS-PTR
               SET DB-PCB-IRIS-EYE TO TRUE
               MOVE IRIS-PCB-DBD(WS-INDEX) TO WS-IO-RTN-DBDNAME 
               MOVE WS-IO-RTN-DBDNAME TO DB-PCB-DBD-NAME
               MOVE DB-PCB-FIXED-PART TO IRIS-PCB-HEADER(WS-INDEX)
               MOVE WS-INDEX TO RUN-PCB-INDEX(WS-INDEX)
               SET RUN-IMS-DUAL-POINTER(WS-INDEX) TO NULL
               SET WS-DUAL-SQL-ONLY TO TRUE
              MOVE WS-RUN-DBD-STATUS TO RUN-DBD-STATUS(WS-INDEX)
INQY           IF IRIS-USED-PCB > 0
INQY  *        Program use less PCBs than scheduled
INQY              IF WS-INDEX <= IRIS-USED-PCB
INQY                SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
INQY              END-IF
INQY           ELSE
INQY             SET IRIS-PCB-PTR-:NPCB: TO WS-PTR
INQY           END-IF
      * IO Area backup
               SET IRISADDR-RTN TO TRUE
               CALL IRIS-WS-RTN USING IRIS-WORK-AREA
                            IRIS-IO-AREA(WS-INDEX) WS-PTR
INQY***        SET RUN-IO-AREA-PTR (WS-INDEX) TO WS-PTR
             END-IF
           END-IF.
