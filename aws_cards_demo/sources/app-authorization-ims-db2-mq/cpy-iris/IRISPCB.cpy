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
