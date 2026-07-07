      ******************************************************************
      * Author : mLogica
      * Product: IRIS-DB - v. 5.5.0
      ******************************************************************
      *
      * DESCRIPTION: SEGMENT INFORMATION INITIALIZED BY IRISUTIL AT
      *              PSB SCHEDULE TIME
      *
      ******************************************************************
      *
       01 MEMORY-PCB-AREA.
         07 DT-ROOT-SEGM                     PIC S9(9) COMP.
         07 DT-PHYS-ROOT-SEGM                PIC S9(9) COMP.
         07 DT-CURR-SEGM                     PIC S9(9) COMP.
         07 DT-PARENTAGE                     PIC S9(9) COMP.
         07 DT-POSITION-STATUS               PIC 9.
           88 DT-HAS-NOT-POSITION            VALUE 0.
           88 DT-HAS-POSITION                VALUE 1.
      * THIS STRUCTURE MUST BE ALWAYS ALIGNED WITH THE STRUCTURE 
      * TABLE-PSB-PCBS OF COPYBOOK UIDDPCB WHICH REPRESENTS
      * THE TABLE PSB_PCBS  
         07 DT-PSB-PCB.
      *    PSB NAME
           09 DT-PSB-NAME                    PIC X(8).
      *    PROGRESSIVE VALUE WITHIN THE PSB
           09 DT-PCB-PROGRESSIVE             PIC S9(4) COMP.
      *    PCB NAME, CAN BE NULL
           09 DT-PCB-NAME                    PIC X(8).
      *    PCB TYPE
           09 DT-PCB-TYPE                    PIC S9(4) COMP.
             88 DT-PCB-TYPE-TP               VALUE +1050 +1051.
             88 DT-PCB-TYPE-TP-NAME          VALUE +1050.
             88 DT-PCB-TYPE-TP-LTERM         VALUE +1051.
             88 DT-PCB-TYPE-DB               VALUE +1052.
             88 DT-PCB-TYPE-GSAM             VALUE +1053.
      *    DBD ON THE TABLE COULD BE ALSO A LTERM IN CASE OF TP PCB             
           09 DT-DBD-NAME                    PIC X(8).
      *    CONCATENATED KEY LENGTH
           09 DT-PCB-KEY-LENGTH              PIC S9(4) COMP.
      *    INDICATES AT RUNTIME HOW MANY EXACT COPIES EXIST FOR THAT PCB
           09 DT-COPIES                      PIC S9(4) COMP.
      *    IS THE PARAMETER FOR THE PROCESSING OPTIONS, CAN BE NULL
           09 DT-PCB-PROC-OPTS               PIC X(4).
      *    ONLY SOME OF THE POSSIBLE VALUES
             88 DT-PROCOPT-ALL               VALUE 'A   '.
             88 DT-PROCOPT-GS                VALUE 'GS  '.
             88 DT-PROCOPT-LS                VALUE 'LS  '.
      *    SPECIFIES THE NAME OF A SECONDARY INDEX, CAN BE NULL
           09 DT-PROC-SEQ                    PIC X(8).
      *    SPECIFIES SINGLE OR MULTIPLE POSITIONING FOR THE LOGICAL DATA 
      *    STRUCTURE
           09 DT-PCB-POS                     PIC S9(4) COMP.
             88 DT-POS-SIGLE                 VALUE +1054.
             88 DT-POS-MULTIPLY              VALUE +1055.
      *    SPECIFIES WHETHER (YES) OR NOT (NO) THIS ALTERNATE PCB CAN BE 
      *    USED INSTEAD OF THE I/O PCB FOR RESPONDING TO TERMINALS IN 
      *    RESPONSE MODE, CONVERSATIONAL MODE, OR EXCLUSIVE MODE. 
      *    THE DEFAULT VALUE IS NO. ALTRESP=YES IS ONLY VALID FOR 
      *    ALTERNATE PCBS.     
           09 DT-PCB-ALTRESP                 PIC S9(4) COMP.
             88 DT-IS-ALT-RESP-NO            VALUE 0.
             88 DT-IS-ALT-RESP-YES           VALUE +1.
      *    SPECIFIES WHETHER (YES) OR NOT (NO) IMS VERIFIES THAT THE 
      *    LOGICAL TERMINAL NAMED IN THE RESPONSE ALTERNATE PCB IS 
      *    ASSIGNED TO THE SAME PHYSICAL TERMINAL AS THE LOGICAL 
      *    TERMINAL THAT ORIGINATED THE INPUT MESSAGE. 
      *    THE DEFAULT VALUE IS NO. 
           09 DT-PCB-SAMETRM                 PIC S9(4) COMP.
             88 DT-IS-SAME-TRM-NO            VALUE 0.
             88 DT-IS-SAME-TRM-YES           VALUE +1.
      *    SPECIFIES WHETHER THE ALTERNATE PCB IS MODIFIABLE (YES). 
      *    THIS FEATURE ALLOWS FOR THE DYNAMIC MODIFICATION OF THE 
      *    DESTINATION NAME ASSOCIATED WITH THIS PCB.
      *    DEFAULT VALUE IS NO.
           09 DT-PCB-MODIFY                  PIC S9(4) COMP.
             88 DT-IS-MODIFY-NO              VALUE 0.
             88 DT-IS-MODIFY-YES             VALUE +1.
      *    SPECIFIES WHETHER MESSAGES FROM THIS ALTERNATE PCB ARE TO BE 
      *    SENT (YES) OR ARE TO BE BACKED OUT (NO) IF THE APPLICATION 
      *    PROGRAM SHOULD ABEND.
           09 DT-PCB-EXPRESS                 PIC S9(4) COMP.
             88 DT-IS-EXPRESS-NO             VALUE 0.
             88 DT-IS-EXPRESS-YES            VALUE +1.
      *    SPECIFIES WHETHER THE NAMED PCB IS INCLUDED IN THE PCB LIST 
      *    PASSED TO THE APPLICATION PROGRAM AT ENTRY.
           09 DT-PCB-LIST                    PIC S9(4) COMP.
             88 DT-IS-LIST-NO                VALUE 0.
             88 DT-IS-LIST-YES               VALUE +1.
         07 DT-LAST-KEY-SEC                  PIC X(256).
         07 DT-SAVE-LEVEL                    PIC S9(9) COMP.
         07 DT-NUM-SENSEG                    PIC S9(9) COMP.
         07 DT-PHYS-NUM-SENSEG               PIC S9(9) COMP.
         07 DT-ADD-NUM-SENSEG                PIC S9(9) COMP.
         07 DT-PSB-SENSEG-PTR                USAGE POINTER.
         07 DT-DBD-SEGM-PTR                  USAGE POINTER.
         07 DT-INFO-PTR                      USAGE POINTER.
         07 DT-DC-PTR REDEFINES DT-INFO-PTR  USAGE POINTER.
         07 DT-NUM-FIELDS-OBSOLETE           PIC S9(9) COMP.
         07 DT-DBD-FIELD-PTR                 USAGE POINTER.
      *  INDEX OF FIELD KEY REFERRED TO THE FIELDS TABLE
         07 DT-SECONDARY-KEY                 PIC S9(9) COMP.
         07 DT-LAST-PRESENTED                PIC X(8).
