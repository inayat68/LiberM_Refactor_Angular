package com.aws.carddemo.common.include;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Csdat01y extends NGroup {

  // PLI:  DCL 1 WS_DATE_TIME,
  public static class WsDateTime extends NGroup {
    // PLI:        2 WS_CURDATE,
    public static class WsCurdate extends NGroup {
      // PLI:          3 WS_CURDATE_YEAR   PIC '9999',
      public NZoned wsCurdateYear = new NZoned(4, false);
      // PLI:          3 WS_CURDATE_MONTH  PIC '99',
      public NZoned wsCurdateMonth = new NZoned(2, false);
      // PLI:          3 WS_CURDATE_DAY    PIC '99',
      public NZoned wsCurdateDay = new NZoned(2, false);
    }

    public WsCurdate wsCurdate = new WsCurdate();

    // PLI:        2 WS_CURTIME,
    public static class WsCurtime extends NGroup {
      // PLI:          3 WS_CURTIME_HOURS  PIC '99',
      public NZoned wsCurtimeHours = new NZoned(2, false);
      // PLI:          3 WS_CURTIME_MINUTE PIC '99',
      public NZoned wsCurtimeMinute = new NZoned(2, false);
      // PLI:          3 WS_CURTIME_SECOND PIC '99',
      public NZoned wsCurtimeSecond = new NZoned(2, false);
      // PLI:          3 WS_CURTIME_MILSEC PIC '999',
      public NZoned wsCurtimeMilsec = new NZoned(3, false);
    }

    public WsCurtime wsCurtime = new WsCurtime();

    // PLI:       2 WS_CURDATE_MM_DD_YY,
    public static class WsCurdateMmDdYy extends NGroup {
      // PLI:         3 WS_CURDATE_MM        PIC '99',
      public NZoned wsCurdateMm = new NZoned(2, false);
      // PLI:         3 FILLER1              CHAR (1) INIT ('/'),
      public NChar filler1 = new NChar(1).initial("/");
      // PLI:         3 WS_CURDATE_DD        PIC '99',
      public NZoned wsCurdateDd = new NZoned(2, false);
      // PLI:         3 FILLER2              CHAR (1) INIT ('/'),
      public NChar filler2 = new NChar(1).initial("/");
      // PLI:         3 WS_CURDATE_YY        PIC '99',
      public NZoned wsCurdateYy = new NZoned(2, false);
    }

    public WsCurdateMmDdYy wsCurdateMmDdYy = new WsCurdateMmDdYy();

    // PLI:       2 WS_CURTIME_HH_MM_SS,
    public static class WsCurtimeHhMmSs extends NGroup {
      // PLI:         3 WS_CURTIME_HH        PIC '99',
      public NZoned wsCurtimeHh = new NZoned(2, false);
      // PLI:         3 FILLER3              CHAR (1) INIT (':'),
      public NChar filler3 = new NChar(1).initial(":");
      // PLI:         3 WS_CURTIME_MM        PIC '99',
      public NZoned wsCurtimeMm = new NZoned(2, false);
      // PLI:         3 FILLER4              CHAR (1) INIT (':'),
      public NChar filler4 = new NChar(1).initial(":");
      // PLI:         3 WS_CURTIME_SS        PIC '99',
      public NZoned wsCurtimeSs = new NZoned(2, false);
    }

    public WsCurtimeHhMmSs wsCurtimeHhMmSs = new WsCurtimeHhMmSs();

    // PLI:       2 WS_TIMESTAMP,
    public static class WsTimestamp extends NGroup {
      // PLI:         3 WS_TIMESTAMP_DT_YYYY PIC '9999',
      public NZoned wsTimestampDtYyyy = new NZoned(4, false);
      // PLI:         3 FILLER5              CHAR (1) INIT ('-'),
      public NChar filler5 = new NChar(1).initial("-");
      // PLI:         3 WS_TIMESTAMP_DT_MM   PIC '99',
      public NZoned wsTimestampDtMm = new NZoned(2, false);
      // PLI:         3 FILLER6              CHAR (1) INIT ('-'),
      public NChar filler6 = new NChar(1).initial("-");
      // PLI:         3 WS_TIMESTAMP_DT_DD   PIC '99',
      public NZoned wsTimestampDtDd = new NZoned(2, false);
      // PLI:         3 FILLER7              CHAR (1) INIT (' '),
      public NChar filler7 = new NChar(1).initial(" ");
      // PLI:         3 WS_TIMESTAMP_TM_HH   PIC '99',
      public NZoned wsTimestampTmHh = new NZoned(2, false);
      // PLI:         3 FILLER8              CHAR (1) INIT (':'),
      public NChar filler8 = new NChar(1).initial(":");
      // PLI:         3 WS_TIMESTAMP_TM_MM   PIC '99',
      public NZoned wsTimestampTmMm = new NZoned(2, false);
      // PLI:         3 FILLER9              CHAR (1) INIT (':'),
      public NChar filler9 = new NChar(1).initial(":");
      // PLI:         3 WS_TIMESTAMP_TM_SS   PIC '99',
      public NZoned wsTimestampTmSs = new NZoned(2, false);
      // PLI:         3 FILLER10             CHAR (1) INIT ('.'),
      public NChar filler10 = new NChar(1).initial(".");
      // PLI:         3 WS_TIMESTAMP_TM_MS6  PIC '999999';
      public NZoned wsTimestampTmMs6 = new NZoned(6, false);
    }

    public WsTimestamp wsTimestamp = new WsTimestamp();
  }

  public WsDateTime wsDateTime = new WsDateTime();
  // PLI:  DCL WS_CURDATE_N BASED(ADDR(WS_CURDATE)) PIC '99999999';
  public NZoned wsCurdateN = new NZoned(8, false).redefines(wsDateTime.wsCurdate);
  // PLI:  DCL WS_CURTIME_N BASED(ADDR(WS_CURTIME)) PIC '99999999';
  public NZoned wsCurtimeN = new NZoned(8, false).redefines(wsDateTime.wsCurtime);
  // PLI:  DCL WS_CURTIME_N_CMP BASED(ADDR(WS_CURTIME)) PIC '(8)9';
  public NZoned wsCurtimeNCmp = new NZoned(8, false).redefines(wsDateTime.wsCurtime);
  // PLI:  DCL WS_CURDATE_MM_DD_YY_Z BASED(ADDR(WS_CURDATE_MM_DD_YY))
  public NZoned wsCurdateMmDdYyZ =
      new NZoned(8, false).formatAs("00/00/00").redefines(wsDateTime.wsCurdateMmDdYy);
  // PLI:  DCL WS_CURDATE_MM_DD_YY_N BASED(ADDR(WS_CURDATE_MM_DD_YY))
  public NZoned wsCurdateMmDdYyN =
      new NZoned(8, false).formatAs("00/00/00").redefines(wsDateTime.wsCurdateMmDdYy);
  // PLI:  DCL WS_CURDATE_MM_DD_YY_X BASED(ADDR(WS_CURDATE_MM_DD_YY))
  public NZoned wsCurdateMmDdYyX =
      new NZoned(8, false).formatAs("00/00/00").redefines(wsDateTime.wsCurdateMmDdYy);
  // PLI:  DCL WS_CURDATE_DATA BASED(ADDR(WS_CURDATE)) CHAR(17);
  public NChar wsCurdateData = new NChar(17).redefines(wsDateTime.wsCurdate);
}
