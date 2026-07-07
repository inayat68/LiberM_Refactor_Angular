package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cvcus01y extends NGroup {

  // COB:        01  CUSTOMER-RECORD.
  public static class CustomerRecord extends NGroup {
    // COB:            05  CUST-ID                                 PIC 9(09).
    public NZoned custId = new NZoned(9, false);
    // COB:            05  CUST-FIRST-NAME                         PIC X(25).
    public NChar custFirstName = new NChar(25);
    // COB:            05  CUST-MIDDLE-NAME                        PIC X(25).
    public NChar custMiddleName = new NChar(25);
    // COB:            05  CUST-LAST-NAME                          PIC X(25).
    public NChar custLastName = new NChar(25);
    // COB:            05  CUST-ADDR-LINE-1                        PIC X(50).
    public NChar custAddrLine1 = new NChar(50);
    // COB:            05  CUST-ADDR-LINE-2                        PIC X(50).
    public NChar custAddrLine2 = new NChar(50);
    // COB:            05  CUST-ADDR-LINE-3                        PIC X(50).
    public NChar custAddrLine3 = new NChar(50);
    // COB:            05  CUST-ADDR-STATE-CD                      PIC X(02).
    public NChar custAddrStateCd = new NChar(2);
    // COB:            05  CUST-ADDR-COUNTRY-CD                    PIC X(03).
    public NChar custAddrCountryCd = new NChar(3);
    // COB:            05  CUST-ADDR-ZIP                           PIC X(10).
    public NChar custAddrZip = new NChar(10);
    // COB:            05  CUST-PHONE-NUM-1                        PIC X(15).
    public NChar custPhoneNum1 = new NChar(15);
    // COB:            05  CUST-PHONE-NUM-2                        PIC X(15).
    public NChar custPhoneNum2 = new NChar(15);
    // COB:            05  CUST-SSN                                PIC 9(09).
    public NZoned custSsn = new NZoned(9, false);
    // COB:            05  CUST-GOVT-ISSUED-ID                     PIC X(20).
    public NChar custGovtIssuedId = new NChar(20);
    // COB:            05  CUST-DOB-YYYY-MM-DD                     PIC X(10).
    public NChar custDobYyyyMmDd = new NChar(10);
    // COB:            05  CUST-EFT-ACCOUNT-ID                     PIC X(10).
    public NChar custEftAccountId = new NChar(10);
    // COB:            05  CUST-PRI-CARD-HOLDER-IND                PIC X(01).
    public NChar custPriCardHolderInd = new NChar(1);
    // COB:            05  CUST-FICO-CREDIT-SCORE                  PIC 9(03).
    public NZoned custFicoCreditScore = new NZoned(3, false);
    // COB:            05  FILLER                                  PIC X(168).
    public NChar filler23 = new NChar(168);
  }

  public CustomerRecord customerRecord = new CustomerRecord();
}
