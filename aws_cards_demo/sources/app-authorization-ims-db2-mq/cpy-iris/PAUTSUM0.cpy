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
      * Description: DCLGEN FOR TABLE: DBPAUTP0_PAUTSUM0
      *
      ******************************************************************

      ******************************************************************
      * COBOL DCLGEN FOR SEGMENT: PAUTSUM0
      ******************************************************************

       01 PAUTSUM0.
0001     03 KEY-ACCNTID                       PIC S9(11) COMP-3.
0007     03 PA-CUST-ID-N                      PIC 9(9).
0007     03 PA-CUST-ID                        REDEFINES
0007        PA-CUST-ID-N                      PIC S9(9) COMP-3.
0016     03 PA-AUTH-STATUS                    PIC X(1).
0017     03 PA-ACCOUNT-STATUS1                PIC X(2).
0019     03 PA-ACCOUNT-STATUS2                PIC X(2).
0021     03 PA-ACCOUNT-STATUS3                PIC X(2).
0023     03 PA-ACCOUNT-STATUS4                PIC X(2).
0025     03 PA-ACCOUNT-STATUS5                PIC X(2).
0027     03 PA-CREDIT-LIMIT                   PIC S9(9)V9(2) COMP-3.
0033     03 PA-CASH-LIMIT                     PIC S9(9)V9(2) COMP-3.
0039     03 PA-CREDIT-BALANCE                 PIC S9(9)V9(2) COMP-3.
0045     03 PA-CASH-BALANCE                   PIC S9(9)V9(2) COMP-3.
0051     03 PA-APPROVED-AUTH-CNT              PIC S9(4) COMP.
0053     03 PA-DECLINED-AUTH-CNT              PIC S9(4) COMP.
0055     03 PA-APPROVED-AUTH-AMT              PIC S9(9)V9(2) COMP-3.
0061     03 PA-DECLINED-AUTH-AMT              PIC S9(9)V9(2) COMP-3.
0067     03 PAUTSUM0-FILLER                   PIC X(34).
