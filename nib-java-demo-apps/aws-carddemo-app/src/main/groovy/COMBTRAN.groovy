import com.nib.groovybatch.NibJob
import com.nib.motorhead.scripting.MotorheadClass
import groovy.transform.Field
import groovy.transform.BaseScript


@BaseScript NibJob COMBTRAN
@Field @MotorheadClass('A') def JOBCLASS

// COMBTRAN JOB 'COMBINE TRANSACTIONS',CLASS=A,MSGCLASS=0,                  
//   NOTIFY=&SYSUID    

job "COMBTRAN"

// DESCRIPTION = "COMBINE TRANSACTIONS"
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
// * Sort current transaction file and system generated transactions
// * *******************************************************************         
// //STEP05R  EXEC PGM=SORT                                                        
// //SORTIN   DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.TRANSACT.BKUP(0)                                
// //         DD DISP=SHR,
// //         DSN=AWS.M2.CARDDEMO.SYSTRAN(0)                                
// //SYMNAMES DD *                                                                 
// TRAN-ID,1,16,CH                                                         
// //SYSIN    DD *                                                                 
// //SYSIN    DD *                                                                 
//  SORT FIELDS=(TRAN-ID,A)                                                  
// /*                                                                              
// //SYSOUT   DD SYSOUT=*                                                          
// //SORTOUT  DD DISP=(NEW,CATLG,DELETE),                                          
// //         UNIT=SYSDA,                                                          
// //         DCB=(*.SORTIN),                                                      
// //         SPACE=(CYL,(1,1),RLSE),                                              

exec "STEP05R", pgm: "SORT", parm: "verbose", {
    dd "SORTIN", [
        [dsn: "AWS.M2.CARDDEMO.TRANSACT.BKUP", gdg: 0, disp: SHR],
        [dsn: "AWS.M2.CARDDEMO.SYSTRAN", gdg: 0, disp: SHR]
    ]
    // Instream content is expanded below
    dd "SYMNAMES", instream: [
    "TRAN-ID,1,16,CH                                                         "
    ]
    // Instream content is expanded below
    dd "SYSIN", instream: [
    " SORT FIELDS=(TRAN-ID,A)                                                "
    ]
    dd "SYSOUT", sysout: "*"
    dd "SORTOUT", dsn: "AWS.M2.CARDDEMO.TRANSACT.COMBINED", gdg: 1, disp: [NEW,CATLG,DELETE]
}

// * *******************************************************************         
// * Load combined file to transaction master
// //* *******************************************************************         
// //STEP10 EXEC PGM=IDCAMS                                                        
// //SYSPRINT DD   SYSOUT=*                                                        
// //TRANSACT DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.TRANSACT.COMBINED(+1)                            
// //TRANVSAM DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS                               
// //SYSIN    DD   *                                                               
//    REPRO INFILE(TRANSACT) OUTFILE(TRANVSAM)                                     

exec "STEP10", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    dd "TRANSACT", dsn: "AWS.M2.CARDDEMO.TRANSACT.COMBINED", gdg: 1, disp: SHR
    dd "TRANVSAM", dsn: "AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS", disp: SHR
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "   REPRO INFILE(TRANSACT) OUTFILE(TRANVSAM)                             "
    ]
}

