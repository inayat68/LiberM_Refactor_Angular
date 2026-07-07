package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Csdat01y extends NGroup {

  // COB:        01 WS-DATE-TIME.
  public static class WsDateTime extends NGroup {
    // COB:          05 WS-CURDATE-DATA.
    public static class WsCurdateData extends NGroup {
      // COB:            10  WS-CURDATE.
      public static class WsCurdate extends NGroup {
        // COB:              15  WS-CURDATE-YEAR         PIC 9(04).
        public NZoned wsCurdateYear = new NZoned(4, false);
        // COB:              15  WS-CURDATE-MONTH        PIC 9(02).
        public NZoned wsCurdateMonth = new NZoned(2, false);
        // COB:              15  WS-CURDATE-DAY          PIC 9(02).
        public NZoned wsCurdateDay = new NZoned(2, false);
      }

      public WsCurdate wsCurdate = new WsCurdate();
      // COB:            10 WS-CURDATE-N REDEFINES WS-CURDATE PIC 9(08).
      public NZoned wsCurdateN = new NZoned(8, false).redefines(wsCurdate);

      // COB:            10  WS-CURTIME.
      public static class WsCurtime extends NGroup {
        // COB:              15  WS-CURTIME-HOURS        PIC 9(02).
        public NZoned wsCurtimeHours = new NZoned(2, false);
        // COB:              15  WS-CURTIME-MINUTE       PIC 9(02).
        public NZoned wsCurtimeMinute = new NZoned(2, false);
        // COB:              15  WS-CURTIME-SECOND       PIC 9(02).
        public NZoned wsCurtimeSecond = new NZoned(2, false);
        // COB:              15  WS-CURTIME-MILSEC       PIC 9(02).
        public NZoned wsCurtimeMilsec = new NZoned(2, false);
      }

      public WsCurtime wsCurtime = new WsCurtime();
      // COB:            10 WS-CURTIME-N REDEFINES WS-CURTIME PIC 9(08).
      public NZoned wsCurtimeN = new NZoned(8, false).redefines(wsCurtime);
    }

    public WsCurdateData wsCurdateData = new WsCurdateData();

    // COB:          05 WS-CURDATE-MM-DD-YY.
    public static class WsCurdateMmDdYy extends NGroup {
      // COB:            10  WS-CURDATE-MM             PIC 9(02).
      public NZoned wsCurdateMm = new NZoned(2, false);
      // COB:            10  FILLER                    PIC X(01) VALUE '/'.
      public NChar filler32 = new NChar(1).initial("/");
      // COB:            10  WS-CURDATE-DD             PIC 9(02).
      public NZoned wsCurdateDd = new NZoned(2, false);
      // COB:            10  FILLER                    PIC X(01) VALUE '/'.
      public NChar filler34 = new NChar(1).initial("/");
      // COB:            10  WS-CURDATE-YY             PIC 9(02).
      public NZoned wsCurdateYy = new NZoned(2, false);
    }

    public WsCurdateMmDdYy wsCurdateMmDdYy = new WsCurdateMmDdYy();

    // COB:          05 WS-CURTIME-HH-MM-SS.
    public static class WsCurtimeHhMmSs extends NGroup {
      // COB:            10  WS-CURTIME-HH             PIC 9(02).
      public NZoned wsCurtimeHh = new NZoned(2, false);
      // COB:            10  FILLER                    PIC X(01) VALUE ':'.
      public NChar filler38 = new NChar(1).initial(":");
      // COB:            10  WS-CURTIME-MM             PIC 9(02).
      public NZoned wsCurtimeMm = new NZoned(2, false);
      // COB:            10  FILLER                    PIC X(01) VALUE ':'.
      public NChar filler40 = new NChar(1).initial(":");
      // COB:            10  WS-CURTIME-SS             PIC 9(02).
      public NZoned wsCurtimeSs = new NZoned(2, false);
    }

    public WsCurtimeHhMmSs wsCurtimeHhMmSs = new WsCurtimeHhMmSs();

    // COB:          05 WS-TIMESTAMP.
    public static class WsTimestamp extends NGroup {
      // COB:            10  WS-TIMESTAMP-DT-YYYY      PIC 9(04).
      public NZoned wsTimestampDtYyyy = new NZoned(4, false);
      // COB:            10  FILLER                    PIC X(01) VALUE '-'.
      public NChar filler44 = new NChar(1).initial("-");
      // COB:            10  WS-TIMESTAMP-DT-MM        PIC 9(02).
      public NZoned wsTimestampDtMm = new NZoned(2, false);
      // COB:            10  FILLER                    PIC X(01) VALUE '-'.
      public NChar filler46 = new NChar(1).initial("-");
      // COB:            10  WS-TIMESTAMP-DT-DD        PIC 9(02).
      public NZoned wsTimestampDtDd = new NZoned(2, false);
      // COB:            10  FILLER                    PIC X(01) VALUE ' '.
      public NChar filler48 = new NChar(1).initial(" ");
      // COB:            10  WS-TIMESTAMP-TM-HH        PIC 9(02).
      public NZoned wsTimestampTmHh = new NZoned(2, false);
      // COB:            10  FILLER                    PIC X(01) VALUE ':'.
      public NChar filler50 = new NChar(1).initial(":");
      // COB:            10  WS-TIMESTAMP-TM-MM        PIC 9(02).
      public NZoned wsTimestampTmMm = new NZoned(2, false);
      // COB:            10  FILLER                    PIC X(01) VALUE ':'.
      public NChar filler52 = new NChar(1).initial(":");
      // COB:            10  WS-TIMESTAMP-TM-SS        PIC 9(02).
      public NZoned wsTimestampTmSs = new NZoned(2, false);
      // COB:            10  FILLER                    PIC X(01) VALUE '.'.
      public NChar filler54 = new NChar(1).initial(".");
      // COB:            10  WS-TIMESTAMP-TM-MS6       PIC 9(06).
      public NZoned wsTimestampTmMs6 = new NZoned(6, false);
    }

    public WsTimestamp wsTimestamp = new WsTimestamp();
  }

  public WsDateTime wsDateTime = new WsDateTime();
}
