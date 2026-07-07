import com.nib.groovybatch.NibJob
import com.nib.motorhead.scripting.MotorheadClass
import groovy.transform.Field
import groovy.transform.BaseScript


@BaseScript NibJob ACCTFILE
@Field @MotorheadClass('A') def JOBCLASS

// ACCTFILE JOB 'Delete define Account Data',CLASS=A,MSGCLASS=0,                 
//  NOTIFY=&SYSUID                         

job "ACCTFILE"

// DESCRIPTION = "Delete define Account Data"
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
// * *******************************************************************         
// * DELETE ACCOUNT VSAM FILE IF ONE ALREADY EXISTS                              
// * *******************************************************************         
// //STEP05 EXEC PGM=IDCAMS                                                        
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   *                                                               
//    DELETE AWS.M2.CARDDEMO.ACCTDATA.VSAM.KSDS -                                  
//           CLUSTER                                                               
//    IF MAXCC LE 08 THEN SET MAXCC = 0                                            
// /*                                                                              

exec "STEP05", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "   DELETE AWS.M2.CARDDEMO.ACCTDATA.VSAM.KSDS -                          ",
    "          CLUSTER                                                       ",
    "   IF MAXCC LE 08 THEN SET MAXCC = 0                                    "
    ]
}

// *                                                                             
// * *******************************************************************         
// * DEFINE ACCOUNT VSAM FILE                                                    
// * *******************************************************************         
// //STEP10 EXEC PGM=IDCAMS                                                        
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   *                                                               
//    DEFINE CLUSTER (NAME(AWS.M2.CARDDEMO.ACCTDATA.VSAM.KSDS) -                   
//           CYLINDERS(1 5) -                                                      
//           VOLUMES(AWSHJ1 -                                                      
//           ) -                                                                   
//           KEYS(11 0) -                                                          
//           RECORDSIZE(300 300) -                                                 
//           SHAREOPTIONS(2 3) -                                                   
//           ERASE -                                                               
//           INDEXED -                                                             
//           ) -                                                                   
//           DATA (NAME(AWS.M2.CARDDEMO.ACCTDATA.VSAM.KSDS.DATA) -                 
//           ) -                                                                   
//           INDEX (NAME(AWS.M2.CARDDEMO.ACCTDATA.VSAM.KSDS.INDEX) -               
//           )                                                                     
// /*                                                                              

exec "STEP10", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "   DEFINE CLUSTER (NAME(AWS.M2.CARDDEMO.ACCTDATA.VSAM.KSDS) -           ",
    "          CYLINDERS(1 5) -                                              ",
    "          VOLUMES(AWSHJ1 -                                              ",
    "          ) -                                                           ",
    "          KEYS(11 0) -                                                  ",
    "          RECORDSIZE(300 300) -                                         ",
    "          SHAREOPTIONS(2 3) -                                           ",
    "          ERASE -                                                       ",
    "          INDEXED -                                                     ",
    "          ) -                                                           ",
    "          DATA (NAME(AWS.M2.CARDDEMO.ACCTDATA.VSAM.KSDS.DATA) -         ",
    "          ) -                                                           ",
    "          INDEX (NAME(AWS.M2.CARDDEMO.ACCTDATA.VSAM.KSDS.INDEX) -       ",
    "          )                                                             "
    ]
}

// * *******************************************************************         
// * COPY DATA FROM FLAT FILE TO VSAM FILE                                       
// * *******************************************************************         
// //STEP15 EXEC PGM=IDCAMS                                                        
// //SYSPRINT DD   SYSOUT=*                                                        
// //ACCTDATA DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.ACCTDATA.PS                                      
// //ACCTVSAM DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.ACCTDATA.VSAM.KSDS                               
// //SYSIN    DD   *                                                               
//    REPRO INFILE(ACCTDATA) OUTFILE(ACCTVSAM)                                     
// /*                                                                              

exec "STEP15", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    dd "ACCTDATA", dsn: "AWS.M2.CARDDEMO.ACCTDATA.PS", disp: SHR
    dd "ACCTVSAM", dsn: "AWS.M2.CARDDEMO.ACCTDATA.VSAM.KSDS", disp: SHR
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "   REPRO INFILE(ACCTDATA) OUTFILE(ACCTVSAM)                             "
    ]
}

