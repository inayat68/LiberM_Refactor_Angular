package com.aws.carddemo.batch.program.storage.cbstm03a;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Costm01;
import com.aws.carddemo.common.copybook.Custrec;
import com.aws.carddemo.common.copybook.Cvact01y;
import com.aws.carddemo.common.copybook.Cvact03y;
import com.nib.commons.storage.*;

public class Cbstm03aWorking extends NGroup {
  public Costm01 costm01 = new Costm01();
  public Cvact03y cvact03y = new Cvact03y();
  public Custrec custrec = new Custrec();
  public Cvact01y cvact01y = new Cvact01y();
  // COB:        01  COMP-VARIABLES          COMP.
  public CompVariables compVariables = new CompVariables();

  public static class CompVariables extends NGroup {

    // COB:            05  CR-CNT              PIC S9(4) VALUE 0.
    public NInt16 crCnt = new NInt16().initial(0);
    // COB:            05  TR-CNT              PIC S9(4) VALUE 0.
    public NInt16 trCnt = new NInt16().initial(0);
    // COB:            05  CR-JMP              PIC S9(4) VALUE 0.
    public NInt16 crJmp = new NInt16().initial(0);
    // COB:            05  TR-JMP              PIC S9(4) VALUE 0.
    public NInt16 trJmp = new NInt16().initial(0);
  }

  // COB:        01  COMP3-VARIABLES         COMP-3.
  public Comp3Variables comp3Variables = new Comp3Variables();

  public static class Comp3Variables extends NGroup {

    // COB:            05  WS-TOTAL-AMT        PIC S9(9)V99 VALUE 0.
    public NPacked wsTotalAmt = new NPacked(6, 2).initial(0);
  }

  // COB:        01  MISC-VARIABLES.
  public MiscVariables miscVariables = new MiscVariables();

  public static class MiscVariables extends NGroup {

    // COB:            05  WS-FL-DD            PIC X(8) VALUE 'TRNXFILE'.
    public NChar wsFlDd = new NChar(8).initial("TRNXFILE");
    // COB:            05  WS-TRN-AMT          PIC S9(9)V99 VALUE 0.
    public NZoned wsTrnAmt = new NZoned(11, 2).initial(0);
    // COB:            05  WS-SAVE-CARD VALUE SPACES PIC X(16).
    public NChar wsSaveCard = new NChar(16).initial(NChar.Space);
    // COB:            05  END-OF-FILE         PIC X(01) VALUE 'N'.
    public NChar endOfFile = new NChar(1).initial("N");
  }

  // COB:        01  WS-M03B-AREA.
  public WsM03bArea wsM03bArea = new WsM03bArea();

  public static class WsM03bArea extends NGroup {

    // COB:            05  WS-M03B-DD          PIC X(08).
    public NChar wsM03bDd = new NChar(8);
    // COB:            05  WS-M03B-OPER        PIC X(01).
    public NChar wsM03bOper = new NChar(1);

    // COB:              88  M03B-OPEN       VALUE 'O'.
    public boolean m03bOpen() {
      return wsM03bOper.equals("O");
    }

    public void setM03bOpen(boolean value) {
      if (value) wsM03bOper.setValue("O");
    }

    // COB:              88  M03B-CLOSE      VALUE 'C'.
    public boolean m03bClose() {
      return wsM03bOper.equals("C");
    }

    public void setM03bClose(boolean value) {
      if (value) wsM03bOper.setValue("C");
    }

    // COB:              88  M03B-READ       VALUE 'R'.
    public boolean m03bRead() {
      return wsM03bOper.equals("R");
    }

    public void setM03bRead(boolean value) {
      if (value) wsM03bOper.setValue("R");
    }

    // COB:              88  M03B-READ-K     VALUE 'K'.
    public boolean m03bReadK() {
      return wsM03bOper.equals("K");
    }

    public void setM03bReadK(boolean value) {
      if (value) wsM03bOper.setValue("K");
    }

    // COB:              88  M03B-WRITE      VALUE 'W'.
    public boolean m03bWrite() {
      return wsM03bOper.equals("W");
    }

    public void setM03bWrite(boolean value) {
      if (value) wsM03bOper.setValue("W");
    }

    // COB:              88  M03B-REWRITE    VALUE 'Z'.
    public boolean m03bRewrite() {
      return wsM03bOper.equals("Z");
    }

    public void setM03bRewrite(boolean value) {
      if (value) wsM03bOper.setValue("Z");
    }

    // COB:            05  WS-M03B-RC          PIC X(02).
    public NChar wsM03bRc = new NChar(2);
    // COB:            05  WS-M03B-KEY         PIC X(25).
    public NChar wsM03bKey = new NChar(25);
    // COB:            05  WS-M03B-KEY-LN      PIC S9(4).
    public NZoned wsM03bKeyLn = new NZoned(4);
    // COB:            05  WS-M03B-FLDT        PIC X(1000).
    public NChar wsM03bFldt = new NChar(1000);
  }

  // COB:        01  STATEMENT-LINES.
  public StatementLines statementLines = new StatementLines();

  public static class StatementLines extends NGroup {

    // COB:            05  ST-LINE0.
    public static class StLine0 extends NGroup {
      // COB:                10  FILLER  VALUE ALL '*'                PIC X(31).
      public NChar filler87 = new NChar(31).initial('*');
      // COB:                10  FILLER  VALUE ALL 'START OF STATEMENT' PIC X(18).
      public NChar filler88 = new NChar(18).initial("START OF STATEMENT");
      // COB:                10  FILLER  VALUE ALL '*'                PIC X(31).
      public NChar filler89 = new NChar(31).initial('*');
    }

    public StLine0 stLine0 = new StLine0();

    // COB:            05  ST-LINE1.
    public static class StLine1 extends NGroup {
      // COB:                10  ST-NAME                              PIC X(75).
      public NChar stName = new NChar(75);
      // COB:                10  FILLER  VALUE SPACES                 PIC X(05).
      public NChar filler92 = new NChar(5).initial(NChar.Space);
    }

    public StLine1 stLine1 = new StLine1();

    // COB:            05  ST-LINE2.
    public static class StLine2 extends NGroup {
      // COB:                10  ST-ADD1                              PIC X(50).
      public NChar stAdd1 = new NChar(50);
      // COB:                10  FILLER  VALUE SPACES                 PIC X(30).
      public NChar filler95 = new NChar(30).initial(NChar.Space);
    }

    public StLine2 stLine2 = new StLine2();

    // COB:            05  ST-LINE3.
    public static class StLine3 extends NGroup {
      // COB:                10  ST-ADD2                              PIC X(50).
      public NChar stAdd2 = new NChar(50);
      // COB:                10  FILLER  VALUE SPACES                 PIC X(30).
      public NChar filler98 = new NChar(30).initial(NChar.Space);
    }

    public StLine3 stLine3 = new StLine3();

    // COB:            05  ST-LINE4.
    public static class StLine4 extends NGroup {
      // COB:                10  ST-ADD3                              PIC X(80).
      public NChar stAdd3 = new NChar(80);
    }

    public StLine4 stLine4 = new StLine4();

    // COB:            05  ST-LINE5.
    public static class StLine5 extends NGroup {
      // COB:                10  FILLER  VALUE ALL '-'                PIC X(80).
      public NChar filler102 = new NChar(80).initial('-');
    }

    public StLine5 stLine5 = new StLine5();

    // COB:            05  ST-LINE6.
    public static class StLine6 extends NGroup {
      // COB:                10  FILLER  VALUE SPACES                 PIC X(33).
      public NChar filler104 = new NChar(33).initial(NChar.Space);
      // COB:                10  FILLER  VALUE 'Basic Details'        PIC X(14).
      public NChar filler105 = new NChar(14).initial("Basic Details");
      // COB:                10  FILLER  VALUE SPACES                 PIC X(33).
      public NChar filler106 = new NChar(33).initial(NChar.Space);
    }

    public StLine6 stLine6 = new StLine6();

    // COB:            05  ST-LINE7.
    public static class StLine7 extends NGroup {
      // COB:                10  FILLER  VALUE 'Account ID         :' PIC X(20).
      public NChar filler108 = new NChar(20).initial("Account ID         :");
      // COB:                10  ST-ACCT-ID                           PIC X(20).
      public NChar stAcctId = new NChar(20);
      // COB:                10  FILLER  VALUE SPACES                 PIC X(40).
      public NChar filler110 = new NChar(40).initial(NChar.Space);
    }

    public StLine7 stLine7 = new StLine7();

    // COB:            05  ST-LINE8.
    public static class StLine8 extends NGroup {
      // COB:                10  FILLER  VALUE 'Current Balance    :' PIC X(20).
      public NChar filler112 = new NChar(20).initial("Current Balance    :");
      // COB:                10  ST-CURR-BAL                          PIC 9(9).99-.
      public NZoned stCurrBal = new NZoned(13).formatAs("000000000.00-");
      // COB:                10  FILLER  VALUE SPACES                 PIC X(07).
      public NChar filler114 = new NChar(7).initial(NChar.Space);
      // COB:                10  FILLER  VALUE SPACES                 PIC X(40).
      public NChar filler115 = new NChar(40).initial(NChar.Space);
    }

    public StLine8 stLine8 = new StLine8();

    // COB:            05  ST-LINE9.
    public static class StLine9 extends NGroup {
      // COB:                10  FILLER  VALUE 'FICO Score         :' PIC X(20).
      public NChar filler117 = new NChar(20).initial("FICO Score         :");
      // COB:                10  ST-FICO-SCORE                        PIC X(20).
      public NChar stFicoScore = new NChar(20);
      // COB:                10  FILLER  VALUE SPACES                 PIC X(40).
      public NChar filler119 = new NChar(40).initial(NChar.Space);
    }

    public StLine9 stLine9 = new StLine9();

    // COB:            05  ST-LINE10.
    public static class StLine10 extends NGroup {
      // COB:                10  FILLER  VALUE ALL '-'                PIC X(80).
      public NChar filler121 = new NChar(80).initial('-');
    }

    public StLine10 stLine10 = new StLine10();

    // COB:            05  ST-LINE11.
    public static class StLine11 extends NGroup {
      // COB:                10  FILLER  VALUE SPACES                 PIC X(30).
      public NChar filler123 = new NChar(30).initial(NChar.Space);
      // COB:                10  FILLER  VALUE 'TRANSACTION SUMMARY ' PIC X(20).
      public NChar filler124 = new NChar(20).initial("TRANSACTION SUMMARY ");
      // COB:                10  FILLER  VALUE SPACES                 PIC X(30).
      public NChar filler125 = new NChar(30).initial(NChar.Space);
    }

    public StLine11 stLine11 = new StLine11();

    // COB:            05  ST-LINE12.
    public static class StLine12 extends NGroup {
      // COB:                10  FILLER  VALUE ALL '-'                PIC X(80).
      public NChar filler127 = new NChar(80).initial('-');
    }

    public StLine12 stLine12 = new StLine12();

    // COB:            05  ST-LINE13.
    public static class StLine13 extends NGroup {
      // COB:                10  FILLER  VALUE 'Tran ID         '     PIC X(16).
      public NChar filler129 = new NChar(16).initial("Tran ID         ");
      // COB:                10  FILLER  VALUE 'Tran Details    '     PIC X(51).
      public NChar filler130 = new NChar(51).initial("Tran Details    ");
      // COB:                10  FILLER  VALUE '  Tran Amount'        PIC X(13).
      public NChar filler131 = new NChar(13).initial("  Tran Amount");
    }

    public StLine13 stLine13 = new StLine13();

    // COB:            05  ST-LINE14.
    public static class StLine14 extends NGroup {
      // COB:                10  ST-TRANID                            PIC X(16).
      public NChar stTranid = new NChar(16);
      // COB:                10  FILLER            VALUE ' '          PIC X(01).
      public NChar filler134 = new NChar(1).initial(" ");
      // COB:                10  ST-TRANDT                            PIC X(49).
      public NChar stTrandt = new NChar(49);
      // COB:                10  FILLER            VALUE '$'          PIC X(01).
      public NChar filler136 = new NChar(1).initial("$");
      // COB:                10  ST-TRANAMT                           PIC Z(9).99-.
      public NZoned stTranamt = new NZoned(13).formatAs("#########.00-");
    }

    public StLine14 stLine14 = new StLine14();

    // COB:            05  ST-LINE14A.
    public static class StLine14a extends NGroup {
      // COB:                10  FILLER            VALUE 'Total EXP:' PIC X(10).
      public NChar filler139 = new NChar(10).initial("Total EXP:");
      // COB:                10  FILLER            VALUE SPACES       PIC X(56).
      public NChar filler140 = new NChar(56).initial(NChar.Space);
      // COB:                10  FILLER            VALUE '$'          PIC X(01).
      public NChar filler141 = new NChar(1).initial("$");
      // COB:                10  ST-TOTAL-TRAMT                       PIC Z(9).99-.
      public NZoned stTotalTramt = new NZoned(13).formatAs("#########.00-");
    }

    public StLine14a stLine14a = new StLine14a();

    // COB:            05  ST-LINE15.
    public static class StLine15 extends NGroup {
      // COB:                10  FILLER  VALUE ALL '*'                PIC X(32).
      public NChar filler144 = new NChar(32).initial('*');
      // COB:                10  FILLER  VALUE ALL 'END OF STATEMENT' PIC X(16).
      public NChar filler145 = new NChar(16).initial("END OF STATEMENT");
      // COB:                10  FILLER  VALUE ALL '*'                PIC X(32).
      public NChar filler146 = new NChar(32).initial('*');
    }

    public StLine15 stLine15 = new StLine15();
  }

  // COB:        01  HTML-LINES.
  public HtmlLines htmlLines = new HtmlLines();

  public static class HtmlLines extends NGroup {

    // COB:            05  HTML-FIXED-LN        PIC X(100).
    public NChar htmlFixedLn = new NChar(100);

    // COB:              88  HTML-L01 VALUE '<!DOCTYPE html>'.
    public boolean htmlL01() {
      return htmlFixedLn.equals("<!DOCTYPE html>");
    }

    public void setHtmlL01(boolean value) {
      if (value) htmlFixedLn.setValue("<!DOCTYPE html>");
    }

    // COB:              88  HTML-L02 VALUE '<html lang="en">'.
    public boolean htmlL02() {
      return htmlFixedLn.equals("<html lang=\"en\">");
    }

    public void setHtmlL02(boolean value) {
      if (value) htmlFixedLn.setValue("<html lang=\"en\">");
    }

    // COB:              88  HTML-L03 VALUE '<head>'.
    public boolean htmlL03() {
      return htmlFixedLn.equals("<head>");
    }

    public void setHtmlL03(boolean value) {
      if (value) htmlFixedLn.setValue("<head>");
    }

    // COB:              88  HTML-L04 VALUE '<meta charset="utf-8">'.
    public boolean htmlL04() {
      return htmlFixedLn.equals("<meta charset=\"utf-8\">");
    }

    public void setHtmlL04(boolean value) {
      if (value) htmlFixedLn.setValue("<meta charset=\"utf-8\">");
    }

    // COB:              88  HTML-L05 VALUE '<title>HTML Table Layout</title>'.
    public boolean htmlL05() {
      return htmlFixedLn.equals("<title>HTML Table Layout</title>");
    }

    public void setHtmlL05(boolean value) {
      if (value) htmlFixedLn.setValue("<title>HTML Table Layout</title>");
    }

    // COB:              88  HTML-L06 VALUE '</head>'.
    public boolean htmlL06() {
      return htmlFixedLn.equals("</head>");
    }

    public void setHtmlL06(boolean value) {
      if (value) htmlFixedLn.setValue("</head>");
    }

    // COB:              88  HTML-L07 VALUE '<body style="margin:0px;">'.
    public boolean htmlL07() {
      return htmlFixedLn.equals("<body style=\"margin:0px;\">");
    }

    public void setHtmlL07(boolean value) {
      if (value) htmlFixedLn.setValue("<body style=\"margin:0px;\">");
    }

    // COB:              88  HTML-L08 VALUE '<table  align="center" frame="box" styl
    public boolean htmlL08() {
      return htmlFixedLn.equals(
          "<table  align=\"center\" frame=\"box\" style=\"width:70%; font:12px Segoe"
              + " UI,sans-serif;\">");
    }

    public void setHtmlL08(boolean value) {
      if (value)
        htmlFixedLn.setValue(
            "<table  align=\"center\" frame=\"box\" style=\"width:70%; font:12px Segoe"
                + " UI,sans-serif;\">");
    }

    // COB:              88  HTML-LTRS VALUE '<tr>'.
    public boolean htmlLtrs() {
      return htmlFixedLn.equals("<tr>");
    }

    public void setHtmlLtrs(boolean value) {
      if (value) htmlFixedLn.setValue("<tr>");
    }

    // COB:              88  HTML-LTRE VALUE '</tr>'.
    public boolean htmlLtre() {
      return htmlFixedLn.equals("</tr>");
    }

    public void setHtmlLtre(boolean value) {
      if (value) htmlFixedLn.setValue("</tr>");
    }

    // COB:              88  HTML-LTDS VALUE '<td>'.
    public boolean htmlLtds() {
      return htmlFixedLn.equals("<td>");
    }

    public void setHtmlLtds(boolean value) {
      if (value) htmlFixedLn.setValue("<td>");
    }

    // COB:              88  HTML-LTDE VALUE '</td>'.
    public boolean htmlLtde() {
      return htmlFixedLn.equals("</td>");
    }

    public void setHtmlLtde(boolean value) {
      if (value) htmlFixedLn.setValue("</td>");
    }

    // COB:              88  HTML-L10 VALUE '<td colspan="3" style="padding:0px 5px;
    public boolean htmlL10() {
      return htmlFixedLn.equals(
          "<td colspan=\"3\" style=\"padding:0px 5px;background-color:#1d1d96b3;\">");
    }

    public void setHtmlL10(boolean value) {
      if (value)
        htmlFixedLn.setValue(
            "<td colspan=\"3\" style=\"padding:0px 5px;background-color:#1d1d96b3;\">");
    }

    // COB:              88  HTML-L15 VALUE '<td colspan="3" style="padding:0px 5px;
    public boolean htmlL15() {
      return htmlFixedLn.equals(
          "<td colspan=\"3\" style=\"padding:0px 5px;background-color:#FFAF33;\">");
    }

    public void setHtmlL15(boolean value) {
      if (value)
        htmlFixedLn.setValue(
            "<td colspan=\"3\" style=\"padding:0px 5px;background-color:#FFAF33;\">");
    }

    // COB:              88  HTML-L16
    public boolean htmlL16() {
      return htmlFixedLn.equals("<p style=\"font-size:16px\">Bank of XYZ</p>");
    }

    public void setHtmlL16(boolean value) {
      if (value) htmlFixedLn.setValue("<p style=\"font-size:16px\">Bank of XYZ</p>");
    }

    // COB:              88  HTML-L17
    public boolean htmlL17() {
      return htmlFixedLn.equals("<p>410 Terry Ave N</p>");
    }

    public void setHtmlL17(boolean value) {
      if (value) htmlFixedLn.setValue("<p>410 Terry Ave N</p>");
    }

    // COB:              88  HTML-L18
    public boolean htmlL18() {
      return htmlFixedLn.equals("<p>Seattle WA 99999</p>");
    }

    public void setHtmlL18(boolean value) {
      if (value) htmlFixedLn.setValue("<p>Seattle WA 99999</p>");
    }

    // COB:              88  HTML-L22-35
    public boolean htmlL22_35() {
      return htmlFixedLn.equals(
          "<td colspan=\"3\" style=\"padding:0px 5px;background-color:#f2f2f2;\">");
    }

    public void setHtmlL22_35(boolean value) {
      if (value)
        htmlFixedLn.setValue(
            "<td colspan=\"3\" style=\"padding:0px 5px;background-color:#f2f2f2;\">");
    }

    // COB:              88  HTML-L30-42
    public boolean htmlL30_42() {
      return htmlFixedLn.equals(
          "<td colspan=\"3\" style=\"padding:0px 5px;background-color:#33FFD1;"
              + " text-align:center;\">");
    }

    public void setHtmlL30_42(boolean value) {
      if (value)
        htmlFixedLn.setValue(
            "<td colspan=\"3\" style=\"padding:0px 5px;background-color:#33FFD1;"
                + " text-align:center;\">");
    }

    // COB:              88  HTML-L31
    public boolean htmlL31() {
      return htmlFixedLn.equals("<p style=\"font-size:16px\">Basic Details</p>");
    }

    public void setHtmlL31(boolean value) {
      if (value) htmlFixedLn.setValue("<p style=\"font-size:16px\">Basic Details</p>");
    }

    // COB:              88  HTML-L43
    public boolean htmlL43() {
      return htmlFixedLn.equals("<p style=\"font-size:16px\">Transaction Summary</p>");
    }

    public void setHtmlL43(boolean value) {
      if (value) htmlFixedLn.setValue("<p style=\"font-size:16px\">Transaction Summary</p>");
    }

    // COB:              88  HTML-L47
    public boolean htmlL47() {
      return htmlFixedLn.equals(
          "<td style=\"width:25%; padding:0px 5px; background-color:#33FF5E; text-align:left;\">");
    }

    public void setHtmlL47(boolean value) {
      if (value)
        htmlFixedLn.setValue(
            "<td style=\"width:25%; padding:0px 5px; background-color:#33FF5E;"
                + " text-align:left;\">");
    }

    // COB:              88  HTML-L48
    public boolean htmlL48() {
      return htmlFixedLn.equals("<p style=\"font-size:16px\">Tran ID</p>");
    }

    public void setHtmlL48(boolean value) {
      if (value) htmlFixedLn.setValue("<p style=\"font-size:16px\">Tran ID</p>");
    }

    // COB:              88  HTML-L50
    public boolean htmlL50() {
      return htmlFixedLn.equals(
          "<td style=\"width:55%; padding:0px 5px; background-color:#33FF5E; text-align:left;\">");
    }

    public void setHtmlL50(boolean value) {
      if (value)
        htmlFixedLn.setValue(
            "<td style=\"width:55%; padding:0px 5px; background-color:#33FF5E;"
                + " text-align:left;\">");
    }

    // COB:              88  HTML-L51
    public boolean htmlL51() {
      return htmlFixedLn.equals("<p style=\"font-size:16px\">Tran Details</p>");
    }

    public void setHtmlL51(boolean value) {
      if (value) htmlFixedLn.setValue("<p style=\"font-size:16px\">Tran Details</p>");
    }

    // COB:              88  HTML-L53
    public boolean htmlL53() {
      return htmlFixedLn.equals(
          "<td style=\"width:20%; padding:0px 5px; background-color:#33FF5E; text-align:right;\">");
    }

    public void setHtmlL53(boolean value) {
      if (value)
        htmlFixedLn.setValue(
            "<td style=\"width:20%; padding:0px 5px; background-color:#33FF5E;"
                + " text-align:right;\">");
    }

    // COB:              88  HTML-L54
    public boolean htmlL54() {
      return htmlFixedLn.equals("<p style=\"font-size:16px\">Amount</p>");
    }

    public void setHtmlL54(boolean value) {
      if (value) htmlFixedLn.setValue("<p style=\"font-size:16px\">Amount</p>");
    }

    // COB:              88  HTML-L58
    public boolean htmlL58() {
      return htmlFixedLn.equals(
          "<td style=\"width:25%; padding:0px 5px; background-color:#f2f2f2; text-align:left;\">");
    }

    public void setHtmlL58(boolean value) {
      if (value)
        htmlFixedLn.setValue(
            "<td style=\"width:25%; padding:0px 5px; background-color:#f2f2f2;"
                + " text-align:left;\">");
    }

    // COB:              88  HTML-L61
    public boolean htmlL61() {
      return htmlFixedLn.equals(
          "<td style=\"width:55%; padding:0px 5px; background-color:#f2f2f2; text-align:left;\">");
    }

    public void setHtmlL61(boolean value) {
      if (value)
        htmlFixedLn.setValue(
            "<td style=\"width:55%; padding:0px 5px; background-color:#f2f2f2;"
                + " text-align:left;\">");
    }

    // COB:              88  HTML-L64
    public boolean htmlL64() {
      return htmlFixedLn.equals(
          "<td style=\"width:20%; padding:0px 5px; background-color:#f2f2f2; text-align:right;\">");
    }

    public void setHtmlL64(boolean value) {
      if (value)
        htmlFixedLn.setValue(
            "<td style=\"width:20%; padding:0px 5px; background-color:#f2f2f2;"
                + " text-align:right;\">");
    }

    // COB:              88  HTML-L75
    public boolean htmlL75() {
      return htmlFixedLn.equals("<h3>End of Statement</h3>");
    }

    public void setHtmlL75(boolean value) {
      if (value) htmlFixedLn.setValue("<h3>End of Statement</h3>");
    }

    // COB:              88  HTML-L78 VALUE '</table>'.
    public boolean htmlL78() {
      return htmlFixedLn.equals("</table>");
    }

    public void setHtmlL78(boolean value) {
      if (value) htmlFixedLn.setValue("</table>");
    }

    // COB:              88  HTML-L79 VALUE '</body>'.
    public boolean htmlL79() {
      return htmlFixedLn.equals("</body>");
    }

    public void setHtmlL79(boolean value) {
      if (value) htmlFixedLn.setValue("</body>");
    }

    // COB:              88  HTML-L80 VALUE '</html>'.
    public boolean htmlL80() {
      return htmlFixedLn.equals("</html>");
    }

    public void setHtmlL80(boolean value) {
      if (value) htmlFixedLn.setValue("</html>");
    }

    // COB:            05  HTML-L11.
    public static class HtmlL11 extends NGroup {
      // COB:                10  FILLER   PIC X(34)
      // COB:                           VALUE '<h3>Statement for Account Number: '.
      public NChar filler213 = new NChar(34).initial("<h3>Statement for Account Number: ");
      // COB:                10  L11-ACCT PIC X(20).
      public NChar l11Acct = new NChar(20);
      // COB:                10  FILLER   PIC X(05) VALUE '</h3>'.
      public NChar filler216 = new NChar(5).initial("</h3>");
    }

    public HtmlL11 htmlL11 = new HtmlL11();

    // COB:            05  HTML-L23.
    public static class HtmlL23 extends NGroup {
      // COB:                10  FILLER   PIC X(26)
      // COB:                           VALUE '<p style="font-size:16px">'.
      public NChar filler218 = new NChar(26).initial("<p style=\"font-size:16px\">");
      // COB:                10  L23-NAME PIC X(50).
      public NChar l23Name = new NChar(50);
    }

    public HtmlL23 htmlL23 = new HtmlL23();
    // COB:            05  HTML-ADDR-LN PIC X(100).
    public NChar htmlAddrLn = new NChar(100);
    // COB:            05  HTML-BSIC-LN PIC X(100).
    public NChar htmlBsicLn = new NChar(100);
    // COB:            05  HTML-TRAN-LN PIC X(100).
    public NChar htmlTranLn = new NChar(100);
  }

  // COB:        01  WS-TRNX-TABLE.
  public WsTrnxTable wsTrnxTable = new WsTrnxTable();

  public static class WsTrnxTable extends NGroup {

    // COB:            05  WS-CARD-TBL OCCURS 51 TIMES.
    public static class WsCardTbl extends NGroup {
      // COB:                10  WS-CARD-NUM                          PIC X(16).
      public NChar wsCardNum = new NChar(16);

      // COB:                10  WS-TRAN-TBL OCCURS 10 TIMES.
      public static class WsTranTbl extends NGroup {
        // COB:                    15  WS-TRAN-NUM                      PIC X(16).
        public NChar wsTranNum = new NChar(16);
        // COB:                    15  WS-TRAN-REST                     PIC X(318).
        public NChar wsTranRest = new NChar(318);
      }

      public WsTranTbl[] wsTranTbl = AbstractNField.occurs(10, new WsTranTbl());

      public WsTranTbl wsTranTblAtIndex(int index) {
        return getOccursInstance(wsTranTbl, index);
      }
    }

    public WsCardTbl[] wsCardTbl = AbstractNField.occurs(51, new WsCardTbl());

    public WsCardTbl wsCardTblAtIndex(int index) {
      return getOccursInstance(wsCardTbl, index);
    }
  }

  // COB:        01  WS-TRN-TBL-CNTR.
  public WsTrnTblCntr wsTrnTblCntr = new WsTrnTblCntr();

  public static class WsTrnTblCntr extends NGroup {

    // COB:            05  WS-TRN-TBL-CTR OCCURS 51 TIMES.
    public static class WsTrnTblCtr extends NGroup {
      // COB:                10  WS-TRCT               PIC S9(4) COMP.
      public NInt16 wsTrct = new NInt16();
    }

    public WsTrnTblCtr[] wsTrnTblCtr = AbstractNField.occurs(51, new WsTrnTblCtr());

    public WsTrnTblCtr wsTrnTblCtrAtIndex(int index) {
      return getOccursInstance(wsTrnTblCtr, index);
    }
  }

  // COB:        01  PSAPTR                  POINTER.
  public NPointer psaptr = new NPointer();
  // COB:        01  BUMP-TIOT               PIC S9(08) BINARY VALUE ZERO.
  public NInt32 bumpTiot = new NInt32().initial(0);
  // COB:        01  TIOT-INDEX              REDEFINES BUMP-TIOT POINTER.
  public NPointer tiotIndex = new NPointer().redefines(bumpTiot);
}
