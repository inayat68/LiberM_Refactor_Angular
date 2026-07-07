import com.nib.groovybatch.NibJob
import com.nib.motorhead.scripting.MotorheadClass
import groovy.transform.Field
import groovy.transform.BaseScript


@BaseScript NibJob TRANFILE
@Field @MotorheadClass('A') def JOBCLASS

// TRANFILE JOB 'DEFINE TRANSACTION MASTER',CLASS=A,MSGCLASS=0,                  
//   NOTIFY=&SYSUID     

job "TRANFILE"

// DESCRIPTION = "DEFINE TRANSACTION MASTER"
// CLASS = "A"
// MSGCLASS = "0"
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
// *********************************************************************         
// * Close files in CICS region                                                  
// *********************************************************************         
// //CLCIFIL EXEC PGM=SDSF                                                         
// //ISFOUT DD SYSOUT=*                                                            
// //CMDOUT DD SYSOUT=*                                                            
// //ISFIN  DD *                                                                   
//  /F CICSAWSA,'CEMT SET FIL(TRANSACT ) CLO'                                      
//  /F CICSAWSA,'CEMT SET FIL(CXACAIX ) CLO'                                       
// /*                                                                              

exec "CLCIFIL", pgm: "SDSF", {
    dd "ISFOUT", sysout: "*"
    dd "CMDOUT", sysout: "*"
    // Instream content is expanded below
    dd "ISFIN", instream: [
    " /F CICSAWSA,'CEMT SET FIL(TRANSACT ) CLO'                              ",
    " /F CICSAWSA,'CEMT SET FIL(CXACAIX ) CLO'                               "
    ]
}

// * *******************************************************************         
// * DELETE TRANSACATION MASTER VSAM FILE IF ONE ALREADY EXISTS                  
// * *******************************************************************         
// //STEP05 EXEC PGM=IDCAMS                                                        
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   *                                                               
//    DELETE AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS -                                  
//           CLUSTER                                                               
//    IF MAXCC LE 08 THEN SET MAXCC = 0                                            
//    DELETE AWS.M2.CARDDEMO.TRANSACT.VSAM.AIX -                                   
//           ALTERNATEINDEX                                                        
//    IF MAXCC LE 08 THEN SET MAXCC = 0                                            
// /*                                                                              

exec "STEP05", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "   DELETE AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS -                          ",
    "          CLUSTER                                                       ",
    "   IF MAXCC LE 08 THEN SET MAXCC = 0                                    ",
    "   DELETE AWS.M2.CARDDEMO.TRANSACT.VSAM.AIX -                           ",
    "          ALTERNATEINDEX                                                ",
    "   IF MAXCC LE 08 THEN SET MAXCC = 0                                    "
    ]
}

// *                                                                             
// * *******************************************************************         
// * DEFINE TRANSACATION MASTER VSAM FILE                                        
// * *******************************************************************         
// //STEP10 EXEC PGM=IDCAMS                                                        
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   *                                                               
//    DEFINE CLUSTER (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS) -                   
//           CYLINDERS(1 5) -                                                      
//           VOLUMES(AWSHJ1 -                                                      
//           ) -                                                                   
//           KEYS(16 0) -                                                          
//           RECORDSIZE(350 350) -                                                 
//           SHAREOPTIONS(2 3) -                                                   
//           ERASE -                                                               
//           INDEXED -                                                             
//           ) -                                                                   
//           DATA (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS.DATA) -                 
//           ) -                                                                   
//           INDEX (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS.INDEX) -               
//           )                                                                     
// /*                                                                              

exec "STEP10", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "   DEFINE CLUSTER (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS) -           ",
    "          CYLINDERS(1 5) -                                              ",
    "          VOLUMES(AWSHJ1 -                                              ",
    "          ) -                                                           ",
    "          KEYS(16 0) -                                                  ",
    "          RECORDSIZE(350 350) -                                         ",
    "          SHAREOPTIONS(2 3) -                                           ",
    "          ERASE -                                                       ",
    "          INDEXED -                                                     ",
    "          ) -                                                           ",
    "          DATA (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS.DATA) -         ",
    "          ) -                                                           ",
    "          INDEX (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS.INDEX) -       ",
    "          )                                                             "
    ]
}

// * *******************************************************************         
// * COPY DATA FROM FLAT FILE TO VSAM FILE                                       
// * *******************************************************************         
// //STEP15 EXEC PGM=IDCAMS                                                        
// //SYSPRINT DD   SYSOUT=*                                                        
// //TRANSACT DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.DALYTRAN.PS.INIT                                 
// //TRANVSAM DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS                               
// //SYSIN    DD   *                                                               
//    REPRO INFILE(TRANSACT) OUTFILE(TRANVSAM)                                     
// /*                                                                              

exec "STEP15", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    dd "TRANSACT", dsn: "AWS.M2.CARDDEMO.DALYTRAN.PS.INIT", disp: SHR
    dd "TRANVSAM", dsn: "AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS", disp: SHR
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "   REPRO INFILE(TRANSACT) OUTFILE(TRANVSAM)                             "
    ]
}

// *-------------------------------------------------------------------*         
// * CREATE ALTERNATE INDEX ON PROCESSED TIMESTAMP                               
// *-------------------------------------------------------------------*         
// //STEP20  EXEC PGM=IDCAMS                                                       
// //SYSPRINT DD  SYSOUT=*                                                         
// //SYSIN    DD  *                                                                
//    DEFINE ALTERNATEINDEX (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.AIX)-              
//    RELATE(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS)                    -              
//    KEYS(26 304)                                                  -              
//    NONUNIQUEKEY                                                  -              
//    UPGRADE                                                       -              
//    RECORDSIZE(350,350)                                           -              
//    VOLUMES(AWSHJ1)                                               -              
//    CYLINDERS(5,1))                                               -              
//    DATA (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.AIX.DATA))           -              
//    INDEX (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.AIX.INDEX))                        
// /*                                                                              

exec "STEP20", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "   DEFINE ALTERNATEINDEX (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.AIX)-      ",
    "   RELATE(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS)                    -      ",
    "   KEYS(26 304)                                                  -      ",
    "   NONUNIQUEKEY                                                  -      ",
    "   UPGRADE                                                       -      ",
    "   RECORDSIZE(350,350)                                           -      ",
    "   VOLUMES(AWSHJ1)                                               -      ",
    "   CYLINDERS(5,1))                                               -      ",
    "   DATA (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.AIX.DATA))           -      ",
    "   INDEX (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.AIX.INDEX))                "
    ]
}

// *-------------------------------------------------------------------*         
// * DEFINE PATH IS USED TO RELATE THE ALTERNATE INDEX TO BASE CLUSTER           
// *-------------------------------------------------------------------*         
// //STEP25  EXEC PGM=IDCAMS                                                       
// //SYSPRINT DD  SYSOUT=*                                                         
// //SYSIN    DD  *                                                                
//   DEFINE PATH                                           -                       
//    (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.AIX.PATH)        -                       
//     PATHENTRY(AWS.M2.CARDDEMO.TRANSACT.VSAM.AIX))                               
// /*                                                                              

exec "STEP25", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "  DEFINE PATH                                           -               ",
    "   (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.AIX.PATH)        -               ",
    "    PATHENTRY(AWS.M2.CARDDEMO.TRANSACT.VSAM.AIX))                       "
    ]
}

// *------------------------------------------------------------------           
// * BUILD ALTERNATE INDEX CLUSTER                                               
// *-------------------------------------------------------------------*         
// //STEP30  EXEC PGM=IDCAMS                                                       
// //SYSPRINT DD  SYSOUT=*                                                         
// //SYSIN    DD  *                                                                
//    BLDINDEX                                                      -              
//    INDATASET(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS)                 -              
//    OUTDATASET(AWS.M2.CARDDEMO.TRANSACT.VSAM.AIX)                                
// /*                                                                              

exec "STEP30", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "   BLDINDEX                                                      -      ",
    "   INDATASET(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS)                 -      ",
    "   OUTDATASET(AWS.M2.CARDDEMO.TRANSACT.VSAM.AIX)                        "
    ]
}

// *********************************************************************         
// * Opem files in CICS region                                                   
// *********************************************************************         
// //OPCIFIL EXEC PGM=SDSF                                                         
// //ISFOUT DD SYSOUT=*                                                            
// //CMDOUT DD SYSOUT=*                                                            
// //ISFIN  DD *                                                                   
//  /F CICSAWSA,'CEMT SET FIL(TRANSACT ) OPE'                                      
//  /F CICSAWSA,'CEMT SET FIL(CXACAIX ) OPE'                                       
// /*                                                                              

exec "OPCIFIL", pgm: "SDSF", {
    dd "ISFOUT", sysout: "*"
    dd "CMDOUT", sysout: "*"
    // Instream content is expanded below
    dd "ISFIN", instream: [
    " /F CICSAWSA,'CEMT SET FIL(TRANSACT ) OPE'                              ",
    " /F CICSAWSA,'CEMT SET FIL(CXACAIX ) OPE'                               "
    ]
}

