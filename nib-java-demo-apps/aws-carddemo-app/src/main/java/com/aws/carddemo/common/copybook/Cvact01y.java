package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cvact01y extends NGroup {

  // COB:        01  ACCOUNT-RECORD.
  public static class AccountRecord extends NGroup {
    // COB:            05  ACCT-ID                           PIC 9(11).
    public NZoned acctId = new NZoned(11, false);
    // COB:            05  ACCT-ACTIVE-STATUS                PIC X(01).
    public NChar acctActiveStatus = new NChar(1);
    // COB:            05  ACCT-CURR-BAL                     PIC S9(10)V99.
    public NZoned acctCurrBal = new NZoned(12, 2);
    // COB:            05  ACCT-CREDIT-LIMIT                 PIC S9(10)V99.
    public NZoned acctCreditLimit = new NZoned(12, 2);
    // COB:            05  ACCT-CASH-CREDIT-LIMIT            PIC S9(10)V99.
    public NZoned acctCashCreditLimit = new NZoned(12, 2);
    // COB:            05  ACCT-OPEN-DATE                    PIC X(10).
    public NChar acctOpenDate = new NChar(10);
    // COB:            05  ACCT-EXPIRAION-DATE               PIC X(10).
    public NChar acctExpiraionDate = new NChar(10);
    // COB:            05  ACCT-REISSUE-DATE                 PIC X(10).
    public NChar acctReissueDate = new NChar(10);
    // COB:            05  ACCT-CURR-CYC-CREDIT              PIC S9(10)V99.
    public NZoned acctCurrCycCredit = new NZoned(12, 2);
    // COB:            05  ACCT-CURR-CYC-DEBIT               PIC S9(10)V99.
    public NZoned acctCurrCycDebit = new NZoned(12, 2);
    // COB:            05  ACCT-ADDR-ZIP                     PIC X(10).
    public NChar acctAddrZip = new NChar(10);
    // COB:            05  ACCT-GROUP-ID                     PIC X(10).
    public NChar acctGroupId = new NChar(10);
    // COB:            05  FILLER                            PIC X(178).
    public NChar filler17 = new NChar(178);
  }

  public AccountRecord accountRecord = new AccountRecord();
}
