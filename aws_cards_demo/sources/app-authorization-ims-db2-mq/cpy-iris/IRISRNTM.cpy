      ******************************************************************
      * Author : mLogica
      * Product: IRIS-DB - v. 5.5.0
      ******************************************************************
      *
      *   DESCRIPTION: MAIN AREA WHICH CONTAINS ALL THE POINTERS AND
      *                POSITIONS OF THE OPERATION EXECUTED AT RUNTIME,
      *                RUN-SEGMENT-DETAIL IT'S IN SINCH WITH
      *                MEMORY-PCB-SENSEG DECLARED IN COPYBOOK IRISSSEG
      ******************************************************************
      *
       01 RUN-CONTROL-AREA.
      *
         07 RUN-RUN-FIXED.
           09 RUN-FB-KEY-MAX-LENGTH              PIC S9(9) COMP.
           09 RUN-USED-KEY-SECONDARY             PIC X(255).
      *
      *   UNQUALIFIED FULL READ
      *
           09 RUN-USED-KEY-GNUNQ-NEXT-SEG        PIC X(8).
           09 RUN-USED-KEY-GNUNQ-SAVE-POS        PIC X(512).
           09 RUN-DBD-STATUS                     PIC S9(4) COMP.
             88 DUAL-IMS-ONLY                    VALUE 0.
             88 DUAL-SQL-ONLY                    VALUE 1.
             88 DUAL-BOTH                        VALUE 2.
           09 RUN-IMS-DUAL-POINTER               POINTER.
           09 RUN-PCB-INDEX                      PIC 9(3).
           09 RUN-IO-AREA-PTR                    USAGE POINTER.
           09 FILLER                             PIC X(2).
           09 RUN-LAST-DYN-SSA-FUNCID         PIC S9(9) COMP.
           09 FILLER                             PIC X(202).

     *
      *  Reducing the maximum length, this one refers to just one PCB
      *  according the the IBM documentation the maximum number of
      *  SENSEG allowed is 256, keeping it 16
         07 RUN-RUN-VAR.
           09 RUN-SEGMENT-DETAIL OCCURS 16.
             11 RUN-USED-SSA-INFO.
               13 RUN-SEQUENCE-NAME              PIC X(8).
               13 RUN-SEQUENCE-LENGTH            PIC S9(9) COMP.
      *
               13 RUN-SEQUENCE-TYPE              PIC 9.
                 88 RUN-SEQUENCE-NONE            VALUE 0.
                 88 RUN-SEQUENCE-UNIQUE          VALUE 1.
                 88 RUN-SEQUENCE-MULTI           VALUE 2.
      *
               13 RUN-SEQUENCE-DATA-TYPE         PIC S9(9) COMP.
      *
      *    DATA DICTIONARY VALUES
                 88 RUN-SEQUENCE-TYPE-NONE       VALUE 0.
                 88 RUN-SEQUENCE-TYPE-HEX        VALUE 1026.
                 88 RUN-SEQUENCE-TYPE-PACKED     VALUE 1027.
                 88 RUN-SEQUENCE-TYPE-ALPHA      VALUE 1028.
                 88 RUN-SEQUENCE-TYPE-F-WORD     VALUE 1029.
                 88 RUN-SEQUENCE-TYPE-H-WORD     VALUE 1030.
                 88 RUN-SEQUENCE-TYPE-NUM-ZONED  VALUE 1031.
      *
      *    VALUES CHANGED AT RUN-TIME
                 88 RUN-SEQUENCE-TYPE-INDEX      VALUE 91000.
                 88 RUN-SEQUENCE-TYPE-XDFLD      VALUE 91001.
                 88 RUN-SEQUENCE-TYPE-EDITED     VALUE 91002.
                 88 RUN-SEQUENCE-TYPE-EBCDIC     VALUE 91003.
      *
               13 RUN-PHYSICAL-INDEX             PIC S9(9) COMP.
               13 RUN-PARENT-SEGMENT-INDEX       PIC S9(9) COMP.
               13 RUN-PHYSICAL-PARENT-INDEX      PIC S9(9) COMP.
               13 RUN-USED-SSA-LEN               PIC S9(4) COMP.
               13 RUN-USED-SSA                   PIC X(1024).
             11 RUN-USED-KEYS-PSB.
               13 RUN-USED-KEY-STATUS            PIC X.
                 88 RUN-USED-KEY-NOT-CHANGED     VALUE '0' X'00' ' '.
                 88 RUN-USED-KEY-CHANGED         VALUE '1'.
                 88 RUN-USED-KEY-IS-INDEX        VALUE '2'.
               13 RUN-USED-KEY-VALUE.
                 15 RUN-USED-KEY-ALPHA           PIC X(256).
                 15 RUN-USED-KEY-NUMERIC         PIC S9(9) COMP.
                 15 RUN-USED-KEY-NUMERIC-PREV    PIC S9(9) COMP.
                 15 RUN-USED-KEY-NUMERIC-NEXT    PIC S9(9) COMP.
                 15 RUN-USED-KEY-PARENT-ALPHA    PIC X(256).
                 15 RUN-USED-KEY-PARENT-NUMERIC  PIC S9(9) COMP.
      *
      *   SYSTEM FIELDS STORAGE
      *
                 15 RUN-LAST-SYSTEM-FLD-AREA OCCURS 32.
                   17 RUN-LAST-SX                PIC S9(9) COMP.
              11 RUN-USED-LAST-SEGMENT           PIC X(8).
              11 RUN-USED-LAST-OPEN-CURSOR       PIC 9(8).
              11 FILLER                          PIC X(272).

