import com.nib.groovybatch.NibJob
import com.nib.motorhead.scripting.MotorheadClass
import groovy.transform.Field
import groovy.transform.BaseScript


@BaseScript NibJob DEFCUST
@Field @MotorheadClass('A') def JOBCLASS

// DEFCUST JOB 'Define Customer Data File',CLASS=A,MSGCLASS=0,
//  NOTIFY=&SYSUID

job "DEFCUST"

// DESCRIPTION = "Define Customer Data File"
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
// * DELETE CUSTOMER VSAM FILE IF ONE ALREADY EXISTS
// * *******************************************************************
// //STEP05 EXEC PGM=IDCAMS
// //SYSPRINT DD   SYSOUT=*
// //SYSIN    DD   *
//    DELETE AWS.CCDA.CUSTDATA.CLUSTER -
//           CLUSTER                     
// /*

exec "STEP05", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "   DELETE AWS.CCDA.CUSTDATA.CLUSTER -",
    "          CLUSTER                     "
    ]
}

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
// * DELETE CUSTOMER VSAM FILE IF ONE ALREADY EXISTS
// * *******************************************************************
// //STEP05 EXEC PGM=IDCAMS
// //SYSPRINT DD   SYSOUT=*
// //SYSIN    DD   *
//    DELETE AWS.CCDA.CUSTDATA.CLUSTER -
//           CLUSTER                     
// /*

exec "STEP05", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "   DEFINE CLUSTER (NAME(AWS.CUSTDATA.CLUSTER) - ",
    "          CYLINDERS(1 5) -                      ",
    "          KEYS(10 0) -                          ",
    "          RECORDSIZE(500 500) -                 ",
    "          SHAREOPTIONS(1 4) -                   ",
    "          ERASE -                               ",
    "          INDEXED -                             ",
    "          ) -                                        ",
    "          DATA (NAME(AWS.CUSTDATA.CLUSTER.DATA) -    ",
    "          ) -                                        ",
    "          INDEX (NAME(AWS.CUSTDATA.CLUSTER.INDEX) -  ",
    "          )                                             "
    ]
}

