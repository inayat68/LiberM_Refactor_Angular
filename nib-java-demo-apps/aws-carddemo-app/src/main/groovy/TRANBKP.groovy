import com.nib.groovybatch.NibJob
import com.nib.motorhead.scripting.MotorheadClass
import groovy.transform.Field
import groovy.transform.BaseScript
import procs.REPROC


@BaseScript NibJob TRANBKP
@Field @MotorheadClass('A') def JOBCLASS

// TRANBKP JOB 'REPRO and Delete Transaction Master',CLASS=A,MSGCLASS=0,         
//   NOTIFY=&SYSUID                    

job "TRANBKP"

// DESCRIPTION = "REPRO and Delete Transaction Master"
// CLASS = "A"
// MSGCLASS = "0"
// NOTIFY = "${SYSUID}"

// * *******************************************************************        
// * Repro the processed transaction file                                       
// * *******************************************************************         
// //STEP05R EXEC PROC=REPROC,                                                     
// // CNTLLIB=AWS.M2.CARDDEMO.CNTL                                                 
// //*                                                                             
// //PRC001.FILEIN  DD DISP=SHR,                                                   
// //        DSN=AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS                                
// //*                                                                             
// //PRC001.FILEOUT DD DISP=(NEW,CATLG,DELETE),                                    
// //        UNIT=SYSDA,                                                           
// //        DCB=(LRECL=350,RECFM=FB,BLKSIZE=0),                                   
// //        SPACE=(CYL,(1,1),RLSE),                                               
// //        DSN=AWS.M2.CARDDEMO.TRANSACT.BKUP(+1)                                 

exec "STEP05R", proc: procs.REPROC, CNTLLIB: "AWS.M2.CARDDEMO.CNTL", {
    dd "PRC001.FILEIN", dsn: "AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS", disp: SHR
    dd "PRC001.FILEOUT", dsn: "AWS.M2.CARDDEMO.TRANSACT.BKUP", gdg: 1, disp: [NEW,CATLG,DELETE], lrecl: 350, recfm: FB
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
// //STEP10 EXEC PGM=IDCAMS,COND=(4,LT)                                            
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

exec "STEP10", pgm: "IDCAMS", cond: [4,LT], {
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "   DEFINE CLUSTER (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS) -           ",
    "          CYLINDERS(1 5) -                                              ",
    "          VOLUMES(AWSHJ1 -                                              ",
    "          ) -                                                           ",
    "          KEYS(16 0) -                                                  ",
    "          RECORDSIZE(350 350) -                                         ",
    "          SHAREOPTIONS(2 3) -                                 ",
    "          ERASE -                                                       ",
    "          INDEXED -                                                     ",
    "          ) -                                                           ",
    "          DATA (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS.DATA) -         ",
    "          ) -                                                           ",
    "          INDEX (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS.INDEX) -       ",
    "          )                                                             "
    ]
}

