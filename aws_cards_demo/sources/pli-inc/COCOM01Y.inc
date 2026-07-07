 /*******************************************************************
  * Copyright Amazon,com, Inc, or its affiliates,                   *
  * All Rights Reserved,                                            *
  *                                                                 *
  * Licensed under the Apache License, Version 2,0 (the "License"), *
  * You may not use this file except in compliance with the License,*
  * You may obtain a copy of the License at                         *
  *                                                                 *
  *    http://www,apache,org/licenses/LICENSE_2,0                   *
  *                                                                 *
  * Unless required by applicable law or agreed to in writing,      *
  * software distributed under the License is distributed on an     *
  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,    *
  * either express or implied, See the License for the specific     *
  * language governing permissions and limitations under the License*
  ******************************************************************/
  DCL  1 CARDDEMO_COMMAREA,
         2 CDEMO_GENERAL_INFO,
           3 CDEMO_FROM_TRANID             CHAR(4),
           3 CDEMO_FROM_PROGRAM            CHAR(8),
           3 CDEMO_TO_TRANID               CHAR(4),
           3 CDEMO_TO_PROGRAM              CHAR(8),
           3 CDEMO_USER_ID                 CHAR(8),
           3 CDEMO_USER_TYPE               CHAR,
           3 CDEMO_PGM_CONTEXT             PIC '9',
         2 CDEMO_CUSTOMER_INFO,
           3 CDEMO_CUST_ID                 PIC '(9)9',
           3 CDEMO_CUST_FNAME              CHAR(25),
           3 CDEMO_CUST_MNAME              CHAR(25),
           3 CDEMO_CUST_LNAME              CHAR(25),
         2 CDEMO_ACCOUNT_INFO,
           3 CDEMO_ACCT_ID                 PIC '(11)9',
           3 CDEMO_ACCT_STATUS             CHAR,
         2 CDEMO_CARD_INFO,
           3 CDEMO_CARD_NUM                PIC '(16)9',
         2 CDEMO_MORE_INFO,
           3  CDEMO_LAST_MAP               CHAR(7),
           3  CDEMO_LAST_MAPSET            CHAR(7);
  DCL CDEMO_USRTYP_ADMIN                   CHAR INIT('A');
  DCL CDEMO_USRTYP_USER                    CHAR INIT('U');
  DCL CDEMO_PGM_ENTER                      PIC '9' INIT(0);
  DCL CDEMO_PGM_REENTER                    PIC '9' INIT(1);
