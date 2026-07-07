package com.aws.carddemo.common.copybook;

import com.nib.commons.storage.*;

public class Pautdtl1Copy extends NGroup {

  // COB:        01 PAUTDTL1.
  public static class Pautdtl1 extends NGroup {
    // COB: 0001     03 KEY-PAUT9CTS                      PIC X(8).
    public NCharBin keyPaut9cts = new NCharBin(8);
    // COB: 0009     03 PA-AUTH-ORIG-DATE                 PIC X(6).
    public NChar paAuthOrigDate = new NChar(6);
    // COB: 0015     03 PA-AUTH-ORIG-TIME                 PIC X(6).
    public NChar paAuthOrigTime = new NChar(6);
    // COB: 0021     03 PA-CARD-NUM                       PIC X(16).
    public NChar paCardNum = new NChar(16);
    // COB: 0037     03 PA-AUTH-TYPE                      PIC X(4).
    public NChar paAuthType = new NChar(4);
    // COB: 0041     03 PA-CARD-EXPIRY-DATE               PIC X(4).
    public NChar paCardExpiryDate = new NChar(4);
    // COB: 0045     03 PA-MESSAGE-TYPE                   PIC X(6).
    public NChar paMessageType = new NChar(6);
    // COB: 0051     03 PA-MESSAGE-SOURCE                 PIC X(6).
    public NChar paMessageSource = new NChar(6);
    // COB: 0057     03 PA-AUTH-ID-CODE                   PIC X(6).
    public NChar paAuthIdCode = new NChar(6);
    // COB: 0063     03 PA-AUTH-RESP-CODE                 PIC X(2).
    public NChar paAuthRespCode = new NChar(2);
    // COB: 0065     03 PA-AUTH-RESP-REASON               PIC X(4).
    public NChar paAuthRespReason = new NChar(4);
    // COB: 0069     03 PA-PROCESSING-CODE-N              PIC 9(6).
    public NZoned paProcessingCodeN = new NZoned(6, false);
    // COB: 0069     03 PA-PROCESSING-CODE                REDEFINES
    // COB: 0069        PA-PROCESSING-CODE-N              PIC S9(6) COMP-3.
    public NPacked paProcessingCode = new NPacked(4).redefines(paProcessingCodeN);
    // COB: 0075     03 PA-TRANSACTION-AMT                PIC S9(11)V9(2) COMP-3.
    public NPacked paTransactionAmt = new NPacked(7, 2);
    // COB: 0082     03 PA-APPROVED-AMT                   PIC S9(11)V9(2) COMP-3.
    public NPacked paApprovedAmt = new NPacked(7, 2);
    // COB: 0089     03 PA-MERCHANT-CATAGORY-CODE         PIC X(4).
    public NChar paMerchantCatagoryCode = new NChar(4);
    // COB: 0093     03 PA-ACQR-COUNTRY-CODE              PIC X(3).
    public NChar paAcqrCountryCode = new NChar(3);
    // COB: 0096     03 PA-POS-ENTRY-MODE-N               PIC 9(2).
    public NZoned paPosEntryModeN = new NZoned(2, false);
    // COB: 0096     03 PA-POS-ENTRY-MODE                 REDEFINES
    // COB: 0096        PA-POS-ENTRY-MODE-N               PIC S9(2) COMP-3.
    public NPacked paPosEntryMode = new NPacked(2).redefines(paPosEntryModeN);
    // COB: 0098     03 PA-MERCHANT-ID                    PIC X(15).
    public NChar paMerchantId = new NChar(15);
    // COB: 0113     03 PA-MERCHANT-NAME                  PIC X(22).
    public NChar paMerchantName = new NChar(22);
    // COB: 0135     03 PA-MERCHANT-CITY                  PIC X(13).
    public NChar paMerchantCity = new NChar(13);
    // COB: 0148     03 PA-MERCHANT-STATE                 PIC X(2).
    public NChar paMerchantState = new NChar(2);
    // COB: 0150     03 PA-MERCHANT-ZIP                   PIC X(9).
    public NChar paMerchantZip = new NChar(9);
    // COB: 0159     03 PA-TRANSACTION-ID                 PIC X(15).
    public NChar paTransactionId = new NChar(15);
    // COB: 0174     03 PA-MATCH-STATUS                   PIC X(1).
    public NChar paMatchStatus = new NChar(1);
    // COB: 0175     03 PA-AUTH-FRAUD                     PIC X(1).
    public NChar paAuthFraud = new NChar(1);
    // COB: 0176     03 PA-FRAUD-RPT-DATE                 PIC X(8).
    public NChar paFraudRptDate = new NChar(8);
    // COB: 0184     03 PAUTDTL1-FILLER                   PIC X(17).
    public NChar pautdtl1Filler = new NChar(17);
    // COB: 0201     03 PAUTSUM0-KEY-ACCNTID              PIC S9(11) COMP-3.
    public NPacked pautsum0KeyAccntid = new NPacked(6);
  }

  public Pautdtl1 pautdtl1 = new Pautdtl1();
}
