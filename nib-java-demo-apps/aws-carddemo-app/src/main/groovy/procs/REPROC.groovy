package procs

import com.nib.groovybatch.NibProc
import groovy.transform.BaseScript
import groovy.transform.Field

@BaseScript NibProc REPROC

// REPROC PROC                                                                   
@Field def CNTLLIB = ""

proc "REPROC"

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
// * REPRO UTILITY TO LOAD OR UNLOAD VSAM FILE                                   
// * *******************************************************************         
// //PRC001 EXEC PGM=IDCAMS                                                        
// //SYSPRINT DD SYSOUT=*                                                          
// //FILEIN  DD DISP=SHR,                                                          
// //        DSN=NULLFILE                                                          
// //FILEOUT DD DISP=SHR,                                                          
// //        DSN=NULLFILE                                                          
// //SYSIN   DD DISP=SHR,                                                          
// //        DSN=&CNTLLIB(REPROCT)                                                 

exec "PRC001", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    dd "FILEIN", dsn: "NULLFILE", disp: SHR
    dd "FILEOUT", dsn: "NULLFILE", disp: SHR
    dd "SYSIN", dsn: "${CNTLLIB}", member: "REPROCT", disp: SHR
}

