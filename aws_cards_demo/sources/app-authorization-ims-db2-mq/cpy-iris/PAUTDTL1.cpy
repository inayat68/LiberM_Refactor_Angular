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
      * Description: DCLGEN FOR TABLE: DBPAUTP0_PAUTDTL1
      *
      ******************************************************************

      ******************************************************************
      * COBOL DCLGEN FOR SEGMENT: PAUTDTL1
      ******************************************************************

       01 PAUTDTL1.
0001     03 KEY-PAUT9CTS                      PIC X(8).
0009     03 PA-AUTH-ORIG-DATE                 PIC X(6).
0015     03 PA-AUTH-ORIG-TIME                 PIC X(6).
0021     03 PA-CARD-NUM                       PIC X(16).
0037     03 PA-AUTH-TYPE                      PIC X(4).
0041     03 PA-CARD-EXPIRY-DATE               PIC X(4).
0045     03 PA-MESSAGE-TYPE                   PIC X(6).
0051     03 PA-MESSAGE-SOURCE                 PIC X(6).
0057     03 PA-AUTH-ID-CODE                   PIC X(6).
0063     03 PA-AUTH-RESP-CODE                 PIC X(2).
0065     03 PA-AUTH-RESP-REASON               PIC X(4).
0069     03 PA-PROCESSING-CODE-N              PIC 9(6).
0069     03 PA-PROCESSING-CODE                REDEFINES
0069        PA-PROCESSING-CODE-N              PIC S9(6) COMP-3.
0075     03 PA-TRANSACTION-AMT                PIC S9(11)V9(2) COMP-3.
0082     03 PA-APPROVED-AMT                   PIC S9(11)V9(2) COMP-3.
0089     03 PA-MERCHANT-CATAGORY-CODE         PIC X(4).
0093     03 PA-ACQR-COUNTRY-CODE              PIC X(3).
0096     03 PA-POS-ENTRY-MODE-N               PIC 9(2).
0096     03 PA-POS-ENTRY-MODE                 REDEFINES
0096        PA-POS-ENTRY-MODE-N               PIC S9(2) COMP-3.
0098     03 PA-MERCHANT-ID                    PIC X(15).
0113     03 PA-MERCHANT-NAME                  PIC X(22).
0135     03 PA-MERCHANT-CITY                  PIC X(13).
0148     03 PA-MERCHANT-STATE                 PIC X(2).
0150     03 PA-MERCHANT-ZIP                   PIC X(9).
0159     03 PA-TRANSACTION-ID                 PIC X(15).
0174     03 PA-MATCH-STATUS                   PIC X(1).
0175     03 PA-AUTH-FRAUD                     PIC X(1).
0176     03 PA-FRAUD-RPT-DATE                 PIC X(8).
0184     03 PAUTDTL1-FILLER                   PIC X(17).
0201     03 PAUTSUM0-KEY-ACCNTID              PIC S9(11) COMP-3.
