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
 DCL 1 COSGN0AI,
       2 DFHMS1 CHAR(12),
       2 TRNNAMEL FIXED BIN(15),      /* Transaction Name Length */
       2 TRNNAMEF,                    /* Transaction Name Format */
         3 TRNNAMEA CHAR,             /*  Transaction Name Alpha */
       2 DFHMS3 CHAR(4),
       2 TRNNAMEI CHAR(4),            /* Transaction Name Identifier */
       2 TITLE01L FIXED BIN(15),      /* Title 01 Length */
       2 TITLE01F,                  /* Title 01 Format */
        3 TITLE01A CHAR,            /* Title 01 Alpha */
       2 DFHMS5 CHAR(4),
       2 TITLE01I CHAR(40),           /* Title 01 Identifier */
       2 CURDATEL FIXED BIN(15),      /* Current Date Length */
       2 CURDATEF,                    /* Current Date Format */
        3 CURDATEA CHAR,              /* Current Date Alpha */
       2 DFHMS7 CHAR(4),
       2 CURDATEI CHAR(8),            /* Current Date Identifier */
       2 PGMNAMEL FIXED BIN(15),      /* Program Name Length */
       2 PGMNAMEF,                   /* Program Name Format */
        3 PGMNAMEA CHAR,             /* Program Name Alpha */
       2 DFHMS9 CHAR(4),
       2 PGMNAMEI CHAR(8),            /* Program Name Identifier */
       2 TITLE02L FIXED BIN(15),     /* Title 02 Length */
       2 TITLE02F,                   /* Title 02 Format */
        3 TITLE02A CHAR,             /* Title 02 Alpha */
       2 DFHMS11 CHAR(4),
       2 TITLE02I CHAR(40),          /* Title 02 Identifier */
       2 CURTIMEL FIXED BIN(15),     /* Current Time Length */
       2 CURTIMEF,                   /* Current Time Format */
        3 CURTIMEA CHAR,             /* Current Time Alpha */
       2 DFHMS13 CHAR(4),
       2 CURTIMEI CHAR(9),            /* Current Time Identifier */
       2 APPLIDL FIXED BIN(15),       /* Application ID Length */
       2 APPLIDF,                     /* Application ID Format */
        3 APPLIDA CHAR,               /* Application ID Alpha */
       2 DFHMS15 CHAR(4),
       2 APPLIDI CHAR(8),             /* Application ID Identifier */
       2 SYSIDL  FIXED BIN(15),       /* System ID Length */
       2 SYSIDF,                      /* System ID Format */
        3 SYSIDA CHAR,                /* System ID Alpha */
       2 DFHMS17 CHAR(4),
       2 SYSIDI CHAR(8),              /* System ID Identifier */
       2 USERIDL FIXED BIN(15),       /* User ID Length */
       2 USERIDF,                     /* User ID Format */
        3 USERIDA CHAR,               /* User ID Alpha */
       2 DFHMS19 CHAR(4),
       2 USERIDI CHAR(8),             /* User ID Identifier */
       2 PASSWDL FIXED BIN(15),       /* Password Length */
       2 PASSWDF,                     /* Password Format */
        3 PASSWDA CHAR,               /* Password Alpha */
       2 DFHMS21 CHAR(4),
       2 PASSWDI CHAR(8),             /* Password Identifier */
       2 ERRMSGL FIXED BIN(15),       /* Error Message Length */
       2 ERRMSGF,                     /* Error Message Format */
        3 ERRMSGA CHAR,               /* Error Message Alpha */
       2 DFHMS23 CHAR(4),
       2 ERRMSGI CHAR(78);            /* Error Message Identifier */
 DCL 1 COSGN0AO BASED(ADDR(COSGN0AI)) UNALIGNED,
       2 DFHMS24 CHAR(12),
       2 DFHMS25 CHAR(3),
       2 TRNNAMEC CHAR,               /* Transaction Name Character */
       2 TRNNAMEP CHAR,               /* Transaction Name Picture */
       2 TRNNAMEH CHAR,               /* Transaction Name Highlight */
       2 TRNNAMEV CHAR,               /* Transaction Name Value */
       2 TRNNAMEO CHAR(4),            /* Transaction Name Output */
       2 DFHMS26 CHAR(3),
       2 TITLE01C CHAR,               /* Title 01 Character */
       2 TITLE01P CHAR,               /* Title 01 Picture */
       2 TITLE01H CHAR,               /* Title 01 Highlight */
       2 TITLE01V CHAR,               /* Title 01 Value */
       2 TITLE01O CHAR(40),           /* Title 01 Output */
       2 DFHMS27 CHAR(3),
       2 CURDATEC CHAR,               /* Current Date Character */
       2 CURDATEP CHAR,               /* Current Date Picture */
       2 CURDATEH CHAR,               /* Current Date Highlight */
       2 CURDATEV CHAR,               /* Current Date Value */
       2 CURDATEO CHAR(8),            /* Current Date Output */
       2 DFHMS28 CHAR(3),
       2 PGMNAMEC CHAR,               /* Program Name Character */
       2 PGMNAMEP CHAR,               /* Program Name Picture */
       2 PGMNAMEH CHAR,               /* Program Name Highlight */
       2 PGMNAMEV CHAR,               /* Program Name Value */
       2 PGMNAMEO CHAR(8),            /* Program Name Output */
       2 DFHMS29 CHAR(3),
       2 TITLE02C CHAR,               /* Title 02 Character */
       2 TITLE02P CHAR,               /* Title 02 Picture */
       2 TITLE02H CHAR,               /* Title 02 Highlight */
       2 TITLE02V CHAR,               /* Title 02 Value */
       2 TITLE02O CHAR(40),           /* Title 02 Output */
       2 DFHMS30 CHAR(3),
       2 CURTIMEC CHAR,               /* Current Time Character */
       2 CURTIMEP CHAR,               /* Current Time Picture */
       2 CURTIMEH CHAR,               /* Current Time Highlight */
       2 CURTIMEV CHAR,               /* Current Time Value */
       2 CURTIMEO CHAR(9),            /* Current Time Output */
       2 DFHMS31 CHAR(3),
       2 APPLIDC CHAR,                /* Application ID Character */
       2 APPLIDP CHAR,                /* Application ID Picture */
       2 APPLIDH CHAR,                /* Application ID Highlight */
       2 APPLIDV CHAR,                /* Application ID Value */
       2 APPLIDO CHAR(8),             /* Application ID Output */
       2 DFHMS32 CHAR(3),
       2 SYSIDC CHAR,                 /* System ID Character */
       2 SYSIDP CHAR,                 /* System ID Picture */
       2 SYSIDH CHAR,                 /* System ID Highlight */
       2 SYSIDV CHAR,                 /* System ID Value */
       2 SYSIDO CHAR(8),              /* System ID Output */
       2 DFHMS33 CHAR(3),
       2 USERIDC CHAR,                /* User ID Character */
       2 USERIDP CHAR,                /* User ID Picture */
       2 USERIDH CHAR,                /* User ID Highlight */
       2 USERIDV CHAR,                /* User ID Value */
       2 USERIDO CHAR(8),             /* User ID Output */
       2 DFHMS34 CHAR(3),
       2 PASSWDC CHAR,                /* Password Character */
       2 PASSWDP CHAR,                /* Password Picture */
       2 PASSWDH CHAR,                /* Password Highlight */
       2 PASSWDV CHAR,                /* Password Value */
       2 PASSWDO CHAR(8),             /* Password Output */
       2 DFHMS35 CHAR(3),
       2 ERRMSGC CHAR,                /* Error Message Character */
       2 ERRMSGP CHAR,                /* Error Message Picture */
       2 ERRMSGH CHAR,                /* Error Message Highlight */
       2 ERRMSGV CHAR,                /* Error Message Value */
       2 ERRMSGO CHAR(78);            /* Error Message Output */