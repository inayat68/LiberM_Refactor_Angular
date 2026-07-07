      ******************************************************************
      * Author : mLogica
      * Product: IRIS-DB - v. 5.5.0
      ******************************************************************
      *                                                                *
      *   DESCRIPTION: IRIS COMMON VARIABLES                           *
      *                                                                *
      ******************************************************************
      *
      * PRODUCT NAME
      *
       01 FILLER.
         03 IRIS-WS-PRODUCT-NAME          PIC X(8)  VALUE 'IRIS-DB '.
      *
      * INTERNAL MODULES
      *
       01 IRIS-WS-RTN                     PIC X(8)  VALUE SPACE.
           88 IRISUTIL-RTN                          VALUE 'IRISUTIL'.
           88 IRISTRAC-RTN                          VALUE 'IRISTRAC'.
           88 IRISPSSA-RTN                          VALUE 'IRISPSSA'.
           88 IRISGSEG-RTN                          VALUE 'IRISGSEG'.
           88 IRISGFLD-RTN                          VALUE 'IRISGFLD'.
           88 IRISADDR-RTN                          VALUE 'IRISADDR'.
           88 IRISABND-RTN                          VALUE 'IRISABND'.
           88 IRISPOPT-RTN                          VALUE 'IRISPOPT'.
           88 IRISPSB-RTN                           VALUE 'IRISPSB'.
           88 CBLTDLI-RTN                           VALUE 'CBLTDLI '.
           88 IRISCHKP-RTN                          VALUE 'IRISCHKP'.
DC         88 IRISSND-RTN                           VALUE 'IRISSEND'.
DC         88 IRISRCV-RTN                           VALUE 'IRISRCVE'.
DC         88 IRISROUT-RTN                          VALUE 'IRISROUT'.
           88 IRISJBNM-RTN                          VALUE 'IRISJBNM'.
           88 IRISTSQU-RTN                          VALUE 'IRISTSQU'.
      *
      * ENDIANNESS
      *
       01 IRIS-SYSTEM-ENDIANNESS          PIC 9     VALUE 1.
           88 SYSTEM-BIG-ENDIAN                     VALUE 0.
           88 SYSTEM-LITTLE-ENDIAN                  VALUE 1.
      *
      * IMS CONDITION FOR TESTING
      *
       01 IRIS-IMS-CONDITION              PIC X(2).
           88 IRIS-COND-GREATER                     VALUE '> ' ' >'
                                                               'GT'.
           88 IRIS-COND-GREATER-EQUAL               VALUE '>=' '=>'
                                                               'GE'.
           88 IRIS-COND-EQUAL                       VALUE '= ' ' ='
                                                               'EQ'.
           88 IRIS-COND-NOT-EQUAL                   VALUE '<>' 'NE'.
           88 IRIS-COND-LESS                        VALUE '< ' ' <'
                                                               'LT'.
           88 IRIS-COND-LESS-EQUAL                  VALUE '<=' '=<'
                                                               'LE'.
      *
       01 IRIS-IMS-USER-CONDITIONS.
         03 IRIS-GT1                      PIC X(2)  VALUE 'GT'.
         03 IRIS-GT2                      PIC X(2)  VALUE '> '.
         03 IRIS-GT3                      PIC X(2)  VALUE ' >'.
         03 IRIS-GE1                      PIC X(2)  VALUE 'GE'.
         03 IRIS-GE2                      PIC X(2)  VALUE '>='.
         03 IRIS-GE3                      PIC X(2)  VALUE '=>'.
         03 IRIS-EQ1                      PIC X(2)  VALUE 'EQ'.
         03 IRIS-EQ2                      PIC X(2)  VALUE '= '.
         03 IRIS-EQ3                      PIC X(2)  VALUE ' ='.
         03 IRIS-NE1                      PIC X(2)  VALUE 'NE'.
         03 IRIS-NE2                      PIC X(2)  VALUE '¬='.
         03 IRIS-NE3                      PIC X(2)  VALUE '=¬'.
         03 IRIS-NE4                      PIC X(2)  VALUE '<>'.
         03 IRIS-LT1                      PIC X(2)  VALUE 'LT'.
         03 IRIS-LT2                      PIC X(2)  VALUE '< '.
         03 IRIS-LT3                      PIC X(2)  VALUE ' <'.
         03 IRIS-LE1                      PIC X(2)  VALUE 'LE'.
         03 IRIS-LE2                      PIC X(2)  VALUE '<='.
         03 IRIS-LE3                      PIC X(2)  VALUE '=<'.
         03 IRIS-OR1                      PIC X     VALUE '|'.
         03 IRIS-OR2                      PIC X     VALUE '+'.
         03 IRIS-AND1                     PIC X     VALUE '&'.
         03 IRIS-AND2                     PIC X     VALUE '*'.
         03 IRIS-LP                       PIC X     VALUE '('.
         03 IRIS-RP                       PIC X     VALUE ')'.
         03 IRIS-COUNT                    PIC S9(4) COMP
                                                    VALUE ZERO.
      *
      * END MESSAGE CHARACTER
      *
       01 MESSAGE-END                     PIC X VALUE X'EF'.
       01 NL                              PIC X VALUE '|'.
      *
      * DYNAMIC SSA CONSTANTS
      *
       01 KEY-FIELD-PREFIX                PIC X(3) VALUE 'KEY'.
      *
      * IRIS DIB BLOCK
      *
       01 IRIS-DIB-BLOCK.
         03  DIBVER                       PIC X(2).                             
         03  DIBSTAT                      PIC X(2).                             
         03  DIBSEGM                      PIC X(8).                             
         03  DIBFIL01                     PIC X(1).                             
         03  DIBFIL01                     PIC X(1).                             
         03  DIBSEGLV                     PIC X(2).                             
         03  DIBKFBL                      PIC S9(4) COMP.                       
         03  DIBDBDNM                     PIC X(8).                             
         03  DIBDBORG                     PIC X(8).                             
         03  DIBFIL03                     PIC X(6).                             
       01 DLZDIB REDEFINES IRIS-DIB-BLOCK.                           
         03  IRIS-DIBVER                  PIC X(2).                             
         03  IRIS-DIBSTAT                 PIC X(2).                             
         03  IRIS-DIBSEGM                 PIC X(8).                             
         03  IRIS-DIBFIL01                PIC X(1).                             
         03  IRIS-DIBFIL01                PIC X(1).                             
         03  IRIS-DIBSEGLV                PIC X(2).                             
         03  IRIS-DIBKFBL                 PIC S9(4) COMP.                       
         03  IRIS-DIBDBDNM                PIC X(8).                             
         03  IRIS-DIBDBORG                PIC X(8).                             
         03  IRIS-DIBFIL03                PIC X(6).                             
       01 IRIS-DLZDIB REDEFINES IRIS-DIB-BLOCK PIC X(40).                       
      *
       01 IRIS-DUMMY-AREA                 PIC X(8) VALUE SPACES.
      *
       01 IRIS-PSB-NAME-COMM              PIC X(8).
      *
       01 IRIS-CHKP-AREA1-LEN-COMM        PIC S9(9) COMP.
       01 IRIS-CHKP-AREA2-LEN-COMM        PIC S9(9) COMP.
       01 IRIS-CHKP-AREA3-LEN-COMM        PIC S9(9) COMP.
       01 IRIS-CHKP-AREA4-LEN-COMM        PIC S9(9) COMP.
       01 IRIS-CHKP-AREA5-LEN-COMM        PIC S9(9) COMP.
       01 IRIS-CHKP-AREA6-LEN-COMM        PIC S9(9) COMP.
       01 IRIS-CHKP-AREA7-LEN-COMM        PIC S9(9) COMP.
      * 
       01 DATE-CONSTANT.
         03 YEAR-FOR-CENTURY    PIC 9(02) VALUE 50.
         03 DAY-WHEN-MISSING    PIC 9(02) VALUE 01.
         03 MON-WHEN-MISSING    PIC 9(02) VALUE 01.    
      * 
       01 IRIS-TSNAME.
         03 IRIS-EIBTRMID                 PIC X(4).
         03 IRIS-EIBTRNID                 PIC X(4).
         03 IRIS-EIBTASKN                 PIC 9(8).
       01 IRIS-TSAREA.
         03 IRIS-PCBS-ADDRESS             PIC S9(9) COMP.
         03 IRIS-PCBS-POINTER REDEFINES IRIS-PCBS-ADDRESS POINTER.
       01 IRIS-RESPONSE                   PIC S9(8) COMP VALUE 0.
