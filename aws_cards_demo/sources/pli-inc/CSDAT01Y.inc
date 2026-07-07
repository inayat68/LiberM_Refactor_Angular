 /*******************************************************************
  * Copyright Amazon.com, Inc. or its affiliates.                   *
  * All Rights Reserved.                                            *
  *                                                                 *
  * Licensed under the Apache License, Version 2.0 (the "License"). *
  * You may not use this file except in compliance with the License.*
  * You may obtain a copy of the License at                         *
  *                                                                 *
  *    http://www.apache.org/licenses/LICENSE-2.0                   *
  *                                                                 *
  * Unless required by applicable law or agreed to in writing,      *
  * software distributed under the License is distributed on an     *
  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,    *
  * either express or implied. See the License for the specific     *
  * language governing permissions and limitations under the License*
  ******************************************************************/
 DCL 1 WS_DATE_TIME,
       2 WS_CURDATE,
         3 WS_CURDATE_YEAR   PIC '9999',
         3 WS_CURDATE_MONTH  PIC '99',
         3 WS_CURDATE_DAY    PIC '99',
       2 WS_CURTIME,
         3 WS_CURTIME_HOURS  PIC '99',
         3 WS_CURTIME_MINUTE PIC '99',
         3 WS_CURTIME_SECOND PIC '99',
         3 WS_CURTIME_MILSEC PIC '999',
      2 WS_CURDATE_MM_DD_YY,
        3 WS_CURDATE_MM        PIC '99',
        3 FILLER1              CHAR (1) INIT ('/'),
        3 WS_CURDATE_DD        PIC '99',
        3 FILLER2              CHAR (1) INIT ('/'),
        3 WS_CURDATE_YY        PIC '99',
      2 WS_CURTIME_HH_MM_SS,
        3 WS_CURTIME_HH        PIC '99',
        3 FILLER3              CHAR (1) INIT (':'),
        3 WS_CURTIME_MM        PIC '99',
        3 FILLER4              CHAR (1) INIT (':'),
        3 WS_CURTIME_SS        PIC '99',
      2 WS_TIMESTAMP,
        3 WS_TIMESTAMP_DT_YYYY PIC '9999',
        3 FILLER5              CHAR (1) INIT ('-'),
        3 WS_TIMESTAMP_DT_MM   PIC '99',
        3 FILLER6              CHAR (1) INIT ('-'),
        3 WS_TIMESTAMP_DT_DD   PIC '99',
        3 FILLER7              CHAR (1) INIT (' '),
        3 WS_TIMESTAMP_TM_HH   PIC '99',
        3 FILLER8              CHAR (1) INIT (':'),
        3 WS_TIMESTAMP_TM_MM   PIC '99',
        3 FILLER9              CHAR (1) INIT (':'),
        3 WS_TIMESTAMP_TM_SS   PIC '99',
        3 FILLER10             CHAR (1) INIT ('.'),
        3 WS_TIMESTAMP_TM_MS6  PIC '999999';
 
 DCL WS_CURDATE_N BASED(ADDR(WS_CURDATE)) PIC '99999999';
 DCL WS_CURTIME_N BASED(ADDR(WS_CURTIME)) PIC '99999999';
 DCL WS_CURTIME_N_CMP BASED(ADDR(WS_CURTIME)) PIC '(8)9';
 DCL WS_CURDATE_MM_DD_YY_Z BASED(ADDR(WS_CURDATE_MM_DD_YY)) 
                                          PIC '99/99/99';
 DCL WS_CURDATE_MM_DD_YY_N BASED(ADDR(WS_CURDATE_MM_DD_YY)) 
                                          PIC '(2)9/(2)9/(2)9';
 DCL WS_CURDATE_MM_DD_YY_X BASED(ADDR(WS_CURDATE_MM_DD_YY)) 
                                          PIC '(2)9/99/(2)9';
 DCL WS_CURDATE_DATA BASED(ADDR(WS_CURDATE)) CHAR(17);
