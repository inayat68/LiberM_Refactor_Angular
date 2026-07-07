package com.aws.carddemo.common.copybook;

import com.nib.commons.storage.*;

public class Pautsum0Copy extends NGroup {

  // COB:        01 PAUTSUM0.
  public static class Pautsum0 extends NGroup {
    // COB: 0001     03 KEY-ACCNTID                       PIC S9(11) COMP-3.
    public NPacked keyAccntid = new NPacked(6);
    // COB: 0007     03 PA-CUST-ID-N                      PIC 9(9).
    public NZoned paCustIdN = new NZoned(9, false);
    // COB: 0007     03 PA-CUST-ID                        REDEFINES
    // COB: 0007        PA-CUST-ID-N                      PIC S9(9) COMP-3.
    public NPacked paCustId = new NPacked(5).redefines(paCustIdN);
    // COB: 0016     03 PA-AUTH-STATUS                    PIC X(1).
    public NChar paAuthStatus = new NChar(1);
    // COB: 0017     03 PA-ACCOUNT-STATUS1                PIC X(2).
    public NChar paAccountStatus1 = new NChar(2);
    // COB: 0019     03 PA-ACCOUNT-STATUS2                PIC X(2).
    public NChar paAccountStatus2 = new NChar(2);
    // COB: 0021     03 PA-ACCOUNT-STATUS3                PIC X(2).
    public NChar paAccountStatus3 = new NChar(2);
    // COB: 0023     03 PA-ACCOUNT-STATUS4                PIC X(2).
    public NChar paAccountStatus4 = new NChar(2);
    // COB: 0025     03 PA-ACCOUNT-STATUS5                PIC X(2).
    public NChar paAccountStatus5 = new NChar(2);
    // COB: 0027     03 PA-CREDIT-LIMIT                   PIC S9(9)V9(2) COMP-3.
    public NPacked paCreditLimit = new NPacked(6, 2);
    // COB: 0033     03 PA-CASH-LIMIT                     PIC S9(9)V9(2) COMP-3.
    public NPacked paCashLimit = new NPacked(6, 2);
    // COB: 0039     03 PA-CREDIT-BALANCE                 PIC S9(9)V9(2) COMP-3.
    public NPacked paCreditBalance = new NPacked(6, 2);
    // COB: 0045     03 PA-CASH-BALANCE                   PIC S9(9)V9(2) COMP-3.
    public NPacked paCashBalance = new NPacked(6, 2);
    // COB: 0051     03 PA-APPROVED-AUTH-CNT              PIC S9(4) COMP.
    public NInt16 paApprovedAuthCnt = new NInt16();
    // COB: 0053     03 PA-DECLINED-AUTH-CNT              PIC S9(4) COMP.
    public NInt16 paDeclinedAuthCnt = new NInt16();
    // COB: 0055     03 PA-APPROVED-AUTH-AMT              PIC S9(9)V9(2) COMP-3.
    public NPacked paApprovedAuthAmt = new NPacked(6, 2);
    // COB: 0061     03 PA-DECLINED-AUTH-AMT              PIC S9(9)V9(2) COMP-3.
    public NPacked paDeclinedAuthAmt = new NPacked(6, 2);
    // COB: 0067     03 PAUTSUM0-FILLER                   PIC X(34).
    public NChar pautsum0Filler = new NChar(34);
  }

  public Pautsum0 pautsum0 = new Pautsum0();
}
