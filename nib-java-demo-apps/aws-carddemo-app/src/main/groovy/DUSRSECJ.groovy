import com.nib.groovybatch.NibJob
import com.nib.motorhead.scripting.MotorheadClass
import groovy.transform.Field
import groovy.transform.BaseScript


@BaseScript NibJob DUSRSECJ
@Field @MotorheadClass('A') def JOBCLASS

// DUSRSECJ JOB 'DEF USRSEC FILE',REGION=8M,CLASS=A,
//       MSGCLASS=H,NOTIFY=&SYSUID

job "DUSRSECJ"

// DESCRIPTION = "DEF USRSEC FILE"
// REGION = "8M"
// CLASS = "A"
// MSGCLASS = "H"
// NOTIFY = "${SYSUID}"

// ******************************************************************
// * Copyright Amazon.com, Inc. or its affiliates.                   
// * All Rights Reserved.                                            
// *                                                                 
// * Licensed under the Apache License, Version 2.0 (the "License"). 
// * You may not use this file except in compliance with the License.
// * You may obtain a copy of the License at                         
// *                                                                 
// *    http://www.apache.org/licenses/LICENSE-2.0                   
// *                                                                 
// * Unless required by applicable law or agreed to in writing,      
// * software distributed under the License is distributed on an     
// * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,    
// * either express or implied. See the License for the specific     
// * language governing permissions and limitations under the License
// ******************************************************************
// *-------------------------------------------------------------------*
// * PRE DELETE STEP
// *-------------------------------------------------------------------*
// *
// //PREDEL  EXEC PGM=IEFBR14
// //*
// //DD01     DD DSN=AWS.M2.CARDDEMO.USRSEC.PS,
// //            DISP=(MOD,DELETE,DELETE)

exec "PREDEL", pgm: "IEFBR14", {
    dd "DD01", dsn: "AWS.M2.CARDDEMO.USRSEC.PS", disp: [MOD,DELETE,DELETE]
}

// *
// *-------------------------------------------------------------------*
// * CREATE USER SECURITY FILE (PS) FROM IN-STREAM DATA
// *-------------------------------------------------------------------*
// *
// //STEP01  EXEC PGM=IEBGENER
// //*
// //SYSUT1   DD *
// ADMIN001MARGARET            GOLD                PASSWORDA
// ADMIN002RUSSELL             RUSSELL             PASSWORDA
// ADMIN003RAYMOND             WHITMORE            PASSWORDA
// ADMIN004EMMANUEL            CASGRAIN            PASSWORDA
// ADMIN005GRANVILLE           LACHAPELLE          PASSWORDA
// USER0001LAWRENCE            THOMAS              PASSWORDU
// USER0002AJITH               KUMAR               PASSWORDU
// USER0003LAURITZ             ALME                PASSWORDU
// USER0004AVERARDO            MAZZI               PASSWORDU
// USER0005LEE                 TING                PASSWORDU
// /*
// //SYSUT2   DD DSN=AWS.M2.CARDDEMO.USRSEC.PS,
// //            DISP=(NEW,CATLG,DELETE),
// //            DCB=(LRECL=80,RECFM=FB,DSORG=PS,BLKSIZE=0),
// //            UNIT=SYSDA,SPACE=(TRK,(10,5),RLSE)
// //*
// //SYSPRINT DD SYSOUT=*
// //SYSIN    DD DUMMY

exec "STEP01", pgm: "IEBGENER", {
    // Instream content is expanded below
    dd "SYSUT1", instream: [
    "ADMIN001MARGARET            GOLD                PASSWORDA",
    "ADMIN002RUSSELL             RUSSELL             PASSWORDA",
    "ADMIN003RAYMOND             WHITMORE            PASSWORDA",
    "ADMIN004EMMANUEL            CASGRAIN            PASSWORDA",
    "ADMIN005GRANVILLE           LACHAPELLE          PASSWORDA",
    "USER0001LAWRENCE            THOMAS              PASSWORDU",
    "USER0002AJITH               KUMAR               PASSWORDU",
    "USER0003LAURITZ             ALME                PASSWORDU",
    "USER0004AVERARDO            MAZZI               PASSWORDU",
    "USER0005LEE                 TING                PASSWORDU"
    ]
    dd "SYSUT2", dsn: "AWS.M2.CARDDEMO.USRSEC.PS", disp: [NEW,CATLG,DELETE], lrecl: 80, recfm: FB
    dd "SYSPRINT", sysout: "*"
    dd "SYSIN", dummy: true
}

// *
// *-------------------------------------------------------------------*
// * DEFINE VSAM FILE FOR USER SECURITY
// *-------------------------------------------------------------------*
// *
// //STEP02  EXEC PGM=IDCAMS
// //*
// //SYSPRINT DD  SYSOUT=*
// //SYSIN    DD  *
//  DELETE                  AWS.M2.CARDDEMO.USRSEC.VSAM.KSDS
//  SET       MAXCC = 0
//  DEFINE    CLUSTER (NAME(AWS.M2.CARDDEMO.USRSEC.VSAM.KSDS)    -
//                     KEYS(8,0)                                 -
//                     RECORDSIZE(80,80)                         -
//                     REUSE                                     -
//                     INDEXED                                   -
//                     TRACKS(45,15)                             -
//                     FREESPACE(10,15)                          -
//                     CISZ(8192))                               -
//            DATA    (NAME(AWS.M2.CARDDEMO.USRSEC.VSAM.KSDS.DAT)) -
//            INDEX   (NAME(AWS.M2.CARDDEMO.USRSEC.VSAM.KSDS.IDX))
// /*

exec "STEP02", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    " DELETE                  AWS.M2.CARDDEMO.USRSEC.VSAM.KSDS",
    " SET       MAXCC = 0",
    " DEFINE    CLUSTER (NAME(AWS.M2.CARDDEMO.USRSEC.VSAM.KSDS)    -",
    "                    KEYS(8,0)                                 -",
    "                    RECORDSIZE(80,80)                         -",
    "                    REUSE                                     -",
    "                    INDEXED                                   -",
    "                    TRACKS(45,15)                             -",
    "                    FREESPACE(10,15)                          -",
    "                    CISZ(8192))                               -",
    "           DATA    (NAME(AWS.M2.CARDDEMO.USRSEC.VSAM.KSDS.DAT)) -",
    "           INDEX   (NAME(AWS.M2.CARDDEMO.USRSEC.VSAM.KSDS.IDX))"
    ]
}

// *
// *-------------------------------------------------------------------*
// * COPY USER SECURITY DATA FROM PS TO VSAM FILE
// *-------------------------------------------------------------------*
// *
// //STEP03  EXEC PGM=IDCAMS
// //*
// //IN       DD  DSN=AWS.M2.CARDDEMO.USRSEC.PS,DISP=SHR
// //OUT      DD  DSN=AWS.M2.CARDDEMO.USRSEC.VSAM.KSDS,DISP=SHR
// //SYSOUT   DD  SYSOUT=*
// //SYSPRINT DD  SYSOUT=*
// //SYSIN    DD  *
//   REPRO INFILE(IN) OUTFILE(OUT)
// /*

exec "STEP03", pgm: "IDCAMS", {
    dd "IN", dsn: "AWS.M2.CARDDEMO.USRSEC.PS", disp: SHR
    dd "OUT", dsn: "AWS.M2.CARDDEMO.USRSEC.VSAM.KSDS", disp: SHR
    dd "SYSOUT", sysout: "*"
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "  REPRO INFILE(IN) OUTFILE(OUT)"
    ]
}

