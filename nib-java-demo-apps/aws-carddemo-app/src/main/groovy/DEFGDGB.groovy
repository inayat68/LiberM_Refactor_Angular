import com.nib.groovybatch.NibJob
import com.nib.motorhead.scripting.MotorheadClass
import groovy.transform.Field
import groovy.transform.BaseScript


@BaseScript NibJob DEFGDGB
@Field @MotorheadClass('A') def JOBCLASS

// DEFGDGB JOB 'DEF GDG BASES',CLASS=A,MSGCLASS=0,NOTIFY=&SYSUID      

job "DEFGDGB"

// DESCRIPTION = "DEF GDG BASES"
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
// *  DEFINE GDG BASES NEEDED BY CARDDEMO PROJECT                       
// * *******************************************************************
// //STEDEL EXEC PGM=IDCAMS                                            
// //SYSPRINT DD   SYSOUT=*                                            
// //SYSIN    DD   *                                                   
//    DELETE (AWS.M2.CARDDEMO.TRANSACT.BKUP) GDG FORCE                 
//    IF LASTCC=12 THEN SET MAXCC=0                                    
//    DELETE (AWS.M2.CARDDEMO.TRANSACT.DALY) GDG FORCE                 
//    IF LASTCC=12 THEN SET MAXCC=0                                    
//    DELETE (AWS.M2.CARDDEMO.TRANREPT) GDG FORCE                      
//    IF LASTCC=12 THEN SET MAXCC=0                                    
//    DELETE (AWS.M2.CARDDEMO.TCATBALF.BKUP) GDG FORCE                 
//    IF LASTCC=12 THEN SET MAXCC=0                                    
//    DELETE (AWS.M2.CARDDEMO.SYSTRAN) GDG FORCE                       
//    IF LASTCC=12 THEN SET MAXCC=0                                    
//    DELETE (AWS.M2.CARDDEMO.TRANSACT.COMBINED) GDG FORCE             
//    IF LASTCC=12 THEN SET MAXCC=0                                    
// /*                                                                  

exec "STEDEL", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "   DELETE (AWS.M2.CARDDEMO.TRANSACT.BKUP) GDG FORCE                 ",
    "   IF LASTCC=12 THEN SET MAXCC=0                                    ",
    "   DELETE (AWS.M2.CARDDEMO.TRANSACT.DALY) GDG FORCE                 ",
    "   IF LASTCC=12 THEN SET MAXCC=0                                    ",
    "   DELETE (AWS.M2.CARDDEMO.TRANREPT) GDG FORCE                      ",
    "   IF LASTCC=12 THEN SET MAXCC=0                                    ",
    "   DELETE (AWS.M2.CARDDEMO.TCATBALF.BKUP) GDG FORCE                 ",
    "   IF LASTCC=12 THEN SET MAXCC=0                                    ",
    "   DELETE (AWS.M2.CARDDEMO.SYSTRAN) GDG FORCE                       ",
    "   IF LASTCC=12 THEN SET MAXCC=0                                    ",
    "   DELETE (AWS.M2.CARDDEMO.TRANSACT.COMBINED) GDG FORCE             ",
    "   IF LASTCC=12 THEN SET MAXCC=0                                    "
    ]
}

// /*                                                                  
// //STEPCRE EXEC PGM=IDCAMS                                           
// //SYSPRINT DD   SYSOUT=*                                            
// //SYSIN    DD   *                                                   
//    DEFINE GENERATIONDATAGROUP -                                     
//    (NAME(AWS.M2.CARDDEMO.TRANSACT.BKUP) -                           
//     LIMIT(5) -                                                      
//     SCRATCH -                                                       
//    )                                                                
//    IF LASTCC=12 THEN SET MAXCC=0                                    
//    DEFINE GENERATIONDATAGROUP -                                     
//    (NAME(AWS.M2.CARDDEMO.TRANSACT.DALY) -                           
//     LIMIT(5) -                                                      
//     SCRATCH -                                                       
//    )                                                                
//    IF LASTCC=12 THEN SET MAXCC=0                                    
//    DEFINE GENERATIONDATAGROUP -                                     
//    (NAME(AWS.M2.CARDDEMO.TRANREPT) -                                
//     LIMIT(5) -                                                      
//     SCRATCH -                                                       
//    )                                                                
//    IF LASTCC=12 THEN SET MAXCC=0                                    
//    DEFINE GENERATIONDATAGROUP -                                     
//    (NAME(AWS.M2.CARDDEMO.TCATBALF.BKUP) -                           
//     LIMIT(5) -                                                      
//     SCRATCH -                                                       
//    )                                                                
//    IF LASTCC=12 THEN SET MAXCC=0                                    
//    DEFINE GENERATIONDATAGROUP -                                     
//    (NAME(AWS.M2.CARDDEMO.SYSTRAN) -                                 
//     LIMIT(5) -                                                      
//     SCRATCH -                                                       
//    )                                                                
//    IF LASTCC=12 THEN SET MAXCC=0                                    
//    DEFINE GENERATIONDATAGROUP -                                     
//    (NAME(AWS.M2.CARDDEMO.TRANSACT.COMBINED) -                       
//     LIMIT(5) -                                                      
//     SCRATCH -                                                       
//    )                                                                
//    IF LASTCC=12 THEN SET MAXCC=0                                    
// /*                                                                  

exec "STEPCRE", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "   DEFINE GENERATIONDATAGROUP -                                     ",
    "   (NAME(AWS.M2.CARDDEMO.TRANSACT.BKUP) -                           ",
    "    LIMIT(5) -                                                      ",
    "    SCRATCH -                                                       ",
    "   )                                                                ",
    "   IF LASTCC=12 THEN SET MAXCC=0                                    ",
    "   DEFINE GENERATIONDATAGROUP -                                     ",
    "   (NAME(AWS.M2.CARDDEMO.TRANSACT.DALY) -                           ",
    "    LIMIT(5) -                                                      ",
    "    SCRATCH -                                                       ",
    "   )                                                                ",
    "   IF LASTCC=12 THEN SET MAXCC=0                                    ",
    "   DEFINE GENERATIONDATAGROUP -                                     ",
    "   (NAME(AWS.M2.CARDDEMO.TRANREPT) -                                ",
    "    LIMIT(5) -                                                      ",
    "    SCRATCH -                                                       ",
    "   )                                                                ",
    "   IF LASTCC=12 THEN SET MAXCC=0                                    ",
    "   DEFINE GENERATIONDATAGROUP -                                     ",
    "   (NAME(AWS.M2.CARDDEMO.TCATBALF.BKUP) -                           ",
    "    LIMIT(5) -                                                      ",
    "    SCRATCH -                                                       ",
    "   )                                                                ",
    "   IF LASTCC=12 THEN SET MAXCC=0                                    ",
    "   DEFINE GENERATIONDATAGROUP -                                     ",
    "   (NAME(AWS.M2.CARDDEMO.SYSTRAN) -                                 ",
    "    LIMIT(5) -                                                      ",
    "    SCRATCH -                                                       ",
    "   )                                                                ",
    "   IF LASTCC=12 THEN SET MAXCC=0                                    ",
    "   DEFINE GENERATIONDATAGROUP -                                     ",
    "   (NAME(AWS.M2.CARDDEMO.TRANSACT.COMBINED) -                       ",
    "    LIMIT(5) -                                                      ",
    "    SCRATCH -                                                       ",
    "   )                                                                ",
    "   IF LASTCC=12 THEN SET MAXCC=0                                    "
    ]
}

