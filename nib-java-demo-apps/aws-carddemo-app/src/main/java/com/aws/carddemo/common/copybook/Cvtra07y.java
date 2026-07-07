package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cvtra07y extends NGroup {

  // COB:        01  REPORT-NAME-HEADER.
  public static class ReportNameHeader extends NGroup {
    // COB:            05  REPT-SHORT-NAME                  PIC X(38) VALUE
    // COB:            'DALYREPT'.
    public NChar reptShortName = new NChar(38).initial("DALYREPT");
    // COB:            05  REPT-LONG-NAME                   PIC X(41) VALUE
    // COB:            'Daily Transaction Report'.
    public NChar reptLongName = new NChar(41).initial("Daily Transaction Report");
    // COB:            05  REPT-DATE-HEADER                 PIC X(12) VALUE
    // COB:            'Date Range: '.
    public NChar reptDateHeader = new NChar(12).initial("Date Range: ");
    // COB:            05  REPT-START-DATE                  PIC X(10) VALUE SPACES.
    public NChar reptStartDate = new NChar(10).initial(NChar.Space);
    // COB:            05  FILLER                           PIC X(04) VALUE ' to '.
    public NChar filler12 = new NChar(4).initial(" to ");
    // COB:            05  REPT-END-DATE                    PIC X(10) VALUE SPACES.
    public NChar reptEndDate = new NChar(10).initial(NChar.Space);
  }

  public ReportNameHeader reportNameHeader = new ReportNameHeader();

  // COB:        01  TRANSACTION-DETAIL-REPORT.
  public static class TransactionDetailReport extends NGroup {
    // COB:            05  TRAN-REPORT-TRANS-ID             PIC X(16).
    public NChar tranReportTransId = new NChar(16);
    // COB:            05  FILLER                           PIC X(01) VALUE SPACES.
    public NChar filler17 = new NChar(1).initial(NChar.Space);
    // COB:            05  TRAN-REPORT-ACCOUNT-ID           PIC X(11).
    public NChar tranReportAccountId = new NChar(11);
    // COB:            05  FILLER                           PIC X(01) VALUE SPACES.
    public NChar filler19 = new NChar(1).initial(NChar.Space);
    // COB:            05  TRAN-REPORT-TYPE-CD              PIC X(02).
    public NChar tranReportTypeCd = new NChar(2);
    // COB:            05  FILLER                           PIC X(01) VALUE '-'.
    public NChar filler21 = new NChar(1).initial("-");
    // COB:            05  TRAN-REPORT-TYPE-DESC            PIC X(15).
    public NChar tranReportTypeDesc = new NChar(15);
    // COB:            05  FILLER                           PIC X(01) VALUE SPACES.
    public NChar filler23 = new NChar(1).initial(NChar.Space);
    // COB:            05  TRAN-REPORT-CAT-CD               PIC 9(04).
    public NZoned tranReportCatCd = new NZoned(4, false);
    // COB:            05  FILLER                           PIC X(01) VALUE '-'.
    public NChar filler25 = new NChar(1).initial("-");
    // COB:            05  TRAN-REPORT-CAT-DESC             PIC X(29).
    public NChar tranReportCatDesc = new NChar(29);
    // COB:            05  FILLER                           PIC X(01) VALUE SPACES.
    public NChar filler27 = new NChar(1).initial(NChar.Space);
    // COB:            05  TRAN-REPORT-SOURCE               PIC X(10).
    public NChar tranReportSource = new NChar(10);
    // COB:            05  FILLER                           PIC X(04) VALUE SPACES.
    public NChar filler29 = new NChar(4).initial(NChar.Space);
    // COB:            05  TRAN-REPORT-AMT                  PIC -ZZZ,ZZZ,ZZZ.ZZ.
    public NZoned tranReportAmt = new NZoned(15).formatAs("-###,###,###.##");
    // COB:            05  FILLER                           PIC X(02) VALUE SPACES.
    public NChar filler31 = new NChar(2).initial(NChar.Space);
  }

  public TransactionDetailReport transactionDetailReport = new TransactionDetailReport();

  // COB:        01  TRANSACTION-HEADER-1.
  public static class TransactionHeader1 extends NGroup {
    // COB:            05  FILLER                           PIC X(17) VALUE
    // COB:            'Transaction ID'.
    public NChar filler34 = new NChar(17).initial("Transaction ID");
    // COB:            05  FILLER                           PIC X(12) VALUE
    // COB:            'Account ID'.
    public NChar filler36 = new NChar(12).initial("Account ID");
    // COB:            05  FILLER                           PIC X(19) VALUE
    // COB:            'Transaction Type'.
    public NChar filler38 = new NChar(19).initial("Transaction Type");
    // COB:            05  FILLER                           PIC X(35) VALUE
    // COB:            'Tran Category'.
    public NChar filler40 = new NChar(35).initial("Tran Category");
    // COB:            05  FILLER                           PIC X(14) VALUE
    // COB:            'Tran Source'.
    public NChar filler42 = new NChar(14).initial("Tran Source");
    // COB:            05  FILLER                           PIC X VALUE SPACES.
    public NChar filler44 = new NChar(1).initial(NChar.Space);
    // COB:            05  FILLER                           PIC X(16) VALUE
    // COB:            '        Amount'.
    public NChar filler45 = new NChar(16).initial("        Amount");
  }

  public TransactionHeader1 transactionHeader1 = new TransactionHeader1();
  // COB:        01  TRANSACTION-HEADER-2  PIC X(133) VALUE ALL '-'.
  public NChar transactionHeader2 = new NChar(133).initial('-');

  // COB:        01  REPORT-PAGE-TOTALS.
  public static class ReportPageTotals extends NGroup {
    // COB:            05  FILLER                           PIC X(11) VALUE
    // COB:            'Page Total'.
    public NChar filler51 = new NChar(11).initial("Page Total");
    // COB:            05  FILLER                           PIC X(86) VALUE ALL '.'.
    public NChar filler53 = new NChar(86).initial('.');
    // COB:            05  REPT-PAGE-TOTAL                  PIC +ZZZ,ZZZ,ZZZ.ZZ.
    public NZoned reptPageTotal = new NZoned(15).formatAs("+###,###,###.##");
  }

  public ReportPageTotals reportPageTotals = new ReportPageTotals();

  // COB:        01  REPORT-ACCOUNT-TOTALS.
  public static class ReportAccountTotals extends NGroup {
    // COB:            05  FILLER                           PIC X(13) VALUE
    // COB:            'Account Total'.
    public NChar filler57 = new NChar(13).initial("Account Total");
    // COB:            05  FILLER                           PIC X(84) VALUE ALL '.'.
    public NChar filler59 = new NChar(84).initial('.');
    // COB:            05  REPT-ACCOUNT-TOTAL               PIC +ZZZ,ZZZ,ZZZ.ZZ.
    public NZoned reptAccountTotal = new NZoned(15).formatAs("+###,###,###.##");
  }

  public ReportAccountTotals reportAccountTotals = new ReportAccountTotals();

  // COB:        01  REPORT-GRAND-TOTALS.
  public static class ReportGrandTotals extends NGroup {
    // COB:            05  FILLER                           PIC X(11) VALUE
    // COB:            'Grand Total'.
    public NChar filler63 = new NChar(11).initial("Grand Total");
    // COB:            05  FILLER                           PIC X(86) VALUE ALL '.'.
    public NChar filler65 = new NChar(86).initial('.');
    // COB:            05  REPT-GRAND-TOTAL                 PIC +ZZZ,ZZZ,ZZZ.ZZ.
    public NZoned reptGrandTotal = new NZoned(15).formatAs("+###,###,###.##");
  }

  public ReportGrandTotals reportGrandTotals = new ReportGrandTotals();
}
