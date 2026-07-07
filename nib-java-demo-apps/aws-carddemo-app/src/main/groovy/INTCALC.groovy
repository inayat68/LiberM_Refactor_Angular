import com.nib.groovybatch.NibJob
import com.nib.motorhead.scripting.MotorheadClass
import groovy.transform.Field
import groovy.transform.BaseScript


@BaseScript NibJob INTCALC
@Field @MotorheadClass('A') def JOBCLASS

// INTCALC JOB 'INTEREST CALCULATOR',CLASS=A,MSGCLASS=0,
//    NOTIFY=&SYSUID           

job "INTCALC"

// DESCRIPTION = "INTEREST CALCULATOR"
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
// * Process transaction balance file and compute interest and fees.
// * *******************************************************************         
// //STEP15 EXEC PGM=CBACT04C,PARM='2022071800'                                    
// //STEPLIB  DD DISP=SHR,                                                         
// //            DSN=AWS.M2.CARDDEMO.LOADLIB                                       
// //SYSPRINT DD SYSOUT=*                                                          
// //SYSOUT   DD SYSOUT=*       
// //TCATBALF DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.TCATBALF.VSAM.KSDS      
// //XREFFILE DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.CARDXREF.VSAM.KSDS    
// //XREFFIL1 DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.CARDXREF.VSAM.AIX.PATH    
// //ACCTFILE DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.ACCTDATA.VSAM.KSDS                               
// //DISCGRP  DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.DISCGRP.VSAM.KSDS                                
// //TRANSACT DD DISP=(NEW,CATLG,DELETE),                                          
// //         UNIT=SYSDA,                                                          
// //         DCB=(RECFM=F,LRECL=350,BLKSIZE=0),                                   
// //         SPACE=(CYL,(1,1),RLSE),                                              
// //         DSN=AWS.M2.CARDDEMO.SYSTRAN(+1)           

exec "STEP15", pgm: "CBACT04C", parm: "2022071800", {
    // dd "STEPLIB", // dsn: "AWS.M2.CARDDEMO.LOADLIB", disp: SHR
    dd "SYSPRINT", sysout: "*"
    dd "SYSOUT", sysout: "*"
    dd "TCATBALF", dsn: "AWS.M2.CARDDEMO.TCATBALF.VSAM.KSDS", disp: SHR
    dd "XREFFILE", dsn: "AWS.M2.CARDDEMO.CARDXREF.VSAM.KSDS", disp: SHR
    dd "XREFFIL1", dsn: "AWS.M2.CARDDEMO.CARDXREF.VSAM.AIX.PATH", disp: SHR
    dd "ACCTFILE", dsn: "AWS.M2.CARDDEMO.ACCTDATA.VSAM.KSDS", disp: SHR
    dd "DISCGRP", dsn: "AWS.M2.CARDDEMO.DISCGRP.VSAM.KSDS", disp: SHR
    dd "TRANSACT", dsn: "AWS.M2.CARDDEMO.SYSTRAN", gdg: 1, disp: [NEW,CATLG,DELETE], recfm: FB, lrecl: 350
}

