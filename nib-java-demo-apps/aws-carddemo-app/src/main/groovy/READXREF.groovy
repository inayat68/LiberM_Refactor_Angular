import com.nib.groovybatch.NibJob
import com.nib.motorhead.scripting.MotorheadClass
import groovy.transform.Field
import groovy.transform.BaseScript


@BaseScript NibJob READXREF
@Field @MotorheadClass('A') def JOBCLASS

// READXREF JOB 'Read Cross Ref file',CLASS=A,MSGCLASS=0,
//  NOTIFY=&SYSUID

job "READXREF"

// DESCRIPTION = "Read Cross Ref file"
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
// * RUN THE PROGRAM THAT READS THE XREF MASTER VSAM FILE                        
// * *******************************************************************         
// //STEP05 EXEC PGM=CBACT03C                                                      
// //STEPLIB  DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.LOADLIB                                          
// //XREFFILE DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.CARDXREF.VSAM.KSDS                               
// //SYSOUT   DD SYSOUT=*                                                          
// //SYSPRINT DD SYSOUT=*                                                          

exec "STEP05", pgm: "CBACT03C", {
    // dd "STEPLIB", // dsn: "AWS.M2.CARDDEMO.LOADLIB", disp: SHR
    dd "XREFFILE", dsn: "AWS.M2.CARDDEMO.CARDXREF.VSAM.KSDS", disp: SHR
    dd "SYSOUT", sysout: "*"
    dd "SYSPRINT", sysout: "*"
}

