import com.nib.groovybatch.NibJob
import com.nib.motorhead.scripting.MotorheadClass
import groovy.transform.Field
import groovy.transform.BaseScript


@BaseScript NibJob _INICATG
@Field @MotorheadClass('A') def JOBCLASS

// _INICATG JOB 'Delete define input SEQ files',CLASS=A,MSGCLASS=0,                 
//  NOTIFY=&SYSUID                         

job "_INICATG"

// DESCRIPTION = "Delete define input SEQ files"
// CLASS = "A"
// MSGCLASS = "0"
// NOTIFY = "${SYSUID}"

// ******************************************************************
// * N.I.B. 
// * All Rights Reserved.                                            
// *
// ******************************************************************         
def PDS = "AWS.M2.CARDDEMO.CNTL"
def pdsDir = "${System.env['DATA_ROOT']}/${PDS}"
def path = new File(pdsDir);
try {
    NibVsamCommandLine.invoke(new String[]{"delete", PDS})
} catch (Exception exc) {

}
NibVsamCommandLine.invoke(new String[]{"define", "nonvsam", "--recfmt=LSEQ", "--lrecl=80,80", "--pds", "--physical=${pdsDir}", PDS})
if (!path.exists()) {
    path.mkdir()
}
new File("${pdsDir}/REPROCT").bytes = new File("${System.env['DATA_ROOT']}/REPROCT.txt").bytes
// ******************************************************************         
// * DELETE INPUT SEQ FILES                                                      
// ******************************************************************         
// *                                                                 
// //DELEACCT  EXEC PGM=IEFBR14                                                      
// //SYSPRINT DD   SYSOUT=*                                                        
// //DD01 DD DSN=AWS.M2.CARDDEMO.ACCTDATA.PS,DISP=(MOD,DELETE,DELETE)              

exec "DELEACCT", pgm: "IEFBR14", {
    dd "SYSPRINT", sysout: "*"
    dd "DD01", dsn: "AWS.M2.CARDDEMO.ACCTDATA.PS", disp: [MOD,DELETE,DELETE]
}

// *                                                                 
// //DELECARD EXEC PGM=IEFBR14                                                      
// //SYSPRINT DD   SYSOUT=*                                                        
// //DD01 DD DSN=AWS.M2.CARDDEMO.CARDDATA.PS,DISP=(MOD,DELETE,DELETE)              

exec "DELECARD", pgm: "IEFBR14", {
    dd "SYSPRINT", sysout: "*"
    dd "DD01", dsn: "AWS.M2.CARDDEMO.CARDDATA.PS", disp: [MOD,DELETE,DELETE]
}

// *                                                                 
// //DELECUST EXEC PGM=IEFBR14                                                      
// //SYSPRINT DD   SYSOUT=*                                                        
// //DD01 DD DSN=AWS.M2.CARDDEMO.CUSTDATA.PS,DISP=(MOD,DELETE,DELETE)              

exec "DELECUST", pgm: "IEFBR14", {
    dd "SYSPRINT", sysout: "*"
    dd "DD01", dsn: "AWS.M2.CARDDEMO.CUSTDATA.PS", disp: [MOD,DELETE,DELETE]
}

// *                                                                 
// //DELEXREF EXEC PGM=IEFBR14                                                      
// //SYSPRINT DD   SYSOUT=*                                                        
// //DD01 DD DSN=AWS.M2.CARDDEMO.CARDXREF.PS,DISP=(MOD,DELETE,DELETE)              

exec "DELEXREF", pgm: "IEFBR14", {
    dd "SYSPRINT", sysout: "*"
    dd "DD01", dsn: "AWS.M2.CARDDEMO.CARDXREF.PS", disp: [MOD,DELETE,DELETE]
}

// *                                                                 
// //DELETRXI EXEC PGM=IEFBR14                                                      
// //SYSPRINT DD   SYSOUT=*                                                        
// //DD01 DD DSN=AWS.M2.CARDDEMO.DALYTRAN.PS.INIT,DISP=(MOD,DELETE,DELETE)              

exec "DELETRXI", pgm: "IEFBR14", {
    dd "SYSPRINT", sysout: "*"
    dd "DD01", dsn: "AWS.M2.CARDDEMO.DALYTRAN.PS.INIT", disp: [MOD,DELETE,DELETE]
}

// *                                                                 
// //DELETRXD EXEC PGM=IEFBR14                                                      
// //SYSPRINT DD   SYSOUT=*                                                        
// //DD01 DD DSN=AWS.M2.CARDDEMO.DALYTRAN.PS,DISP=(MOD,DELETE,DELETE)              

exec "DELETRXD", pgm: "IEFBR14", {
    dd "SYSPRINT", sysout: "*"
    dd "DD01", dsn: "AWS.M2.CARDDEMO.DALYTRAN.PS", disp: [MOD,DELETE,DELETE]
}

// *                                                                 
// //DELETRAN EXEC PGM=IEFBR14                                                      
// //SYSPRINT DD   SYSOUT=*                                                        
// //DD01 DD DSN=AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS,
// //            DISP=(MOD,DELETE,DELETE)              

exec "DELETRAN", pgm: "IEFBR14", {
    dd "SYSPRINT", sysout: "*"
    dd "DD01", dsn: "AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS", disp: [MOD,DELETE,DELETE]
}

// *                                                                 
// //DELEDGRP EXEC PGM=IEFBR14                                                      
// //SYSPRINT DD   SYSOUT=*                                                        
// //DD01 DD DSN=AWS.M2.CARDDEMO.DISCGRP.PS,DISP=(MOD,DELETE,DELETE)              

exec "DELEDGRP", pgm: "IEFBR14", {
    dd "SYSPRINT", sysout: "*"
    dd "DD01", dsn: "AWS.M2.CARDDEMO.DISCGRP.PS", disp: [MOD,DELETE,DELETE]
}

// *                                                                 
// //DELECATG EXEC PGM=IEFBR14                                                      
// //SYSPRINT DD   SYSOUT=*                                                        
// //DD01 DD DSN=AWS.M2.CARDDEMO.TRANCATG.PS,DISP=(MOD,DELETE,DELETE)              

exec "DELECATG", pgm: "IEFBR14", {
    dd "SYSPRINT", sysout: "*"
    dd "DD01", dsn: "AWS.M2.CARDDEMO.TRANCATG.PS", disp: [MOD,DELETE,DELETE]
}

// *                                                                 
// //DELETYPE EXEC PGM=IEFBR14                                                      
// //SYSPRINT DD   SYSOUT=*                                                        
// //DD01 DD DSN=AWS.M2.CARDDEMO.TRANTYPE.PS,DISP=(MOD,DELETE,DELETE)              

exec "DELETYPE", pgm: "IEFBR14", {
    dd "SYSPRINT", sysout: "*"
    dd "DD01", dsn: "AWS.M2.CARDDEMO.TRANTYPE.PS", disp: [MOD,DELETE,DELETE]
}

// *                                                                 
// //DELEBALF EXEC PGM=IEFBR14                                                      
// //SYSPRINT DD   SYSOUT=*                                                        
// //DD01 DD DSN=AWS.M2.CARDDEMO.TCATBALF.PS,DISP=(MOD,DELETE,DELETE)              

exec "DELEBALF", pgm: "IEFBR14", {
    dd "SYSPRINT", sysout: "*"
    dd "DD01", dsn: "AWS.M2.CARDDEMO.TCATBALF.PS", disp: [MOD,DELETE,DELETE]
}

// *                                                                             
// * *******************************************************************         
// * DEFINE AND LOAD SEQUENTIAL FILE                                             
// * *******************************************************************         
// *
// //LOADACCT EXEC PGM=IEBGENER                                                     
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   DUMMY                                                           
// //SYSUT1   DD   DSN=acctdata.txt,DISP=SHR,DCB=(LRECL=301,RECFM=FB)
// //SYSUT2   DD   DSN=AWS.M2.CARDDEMO.ACCTDATA.PS,DISP=(,KEEP),
// //                  DCB=(LRECL=300,RECFM=FB)

exec "LOADACCT", pgm: "IEBGENER", {
    dd "SYSPRINT", sysout: "*"
    dd "SYSIN", dummy: true
    dd "SYSUT1", dsn: "acctdata.txt", disp: SHR, lrecl: 301, recfm: FB
    dd "SYSUT2", dsn: "AWS.M2.CARDDEMO.ACCTDATA.PS", disp: [NEW,KEEP,KEEP], lrecl: 300, recfm: FB
}

// *
// //LOADCARD EXEC PGM=IEBGENER                                                     
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   DUMMY                                                           
// //SYSUT1   DD   DSN=carddata.txt,DISP=SHR,DCB=(LRECL=151,RECFM=FB)
// //SYSUT2   DD   DSN=AWS.M2.CARDDEMO.CARDDATA.PS,DISP=(,KEEP),                   
// //                  DCB=(LRECL=150,RECFM=FB)

exec "LOADCARD", pgm: "IEBGENER", {
    dd "SYSPRINT", sysout: "*"
    dd "SYSIN", dummy: true
    dd "SYSUT1", dsn: "carddata.txt", disp: SHR, lrecl: 151, recfm: FB
    dd "SYSUT2", dsn: "AWS.M2.CARDDEMO.CARDDATA.PS", disp: [NEW,KEEP,KEEP], lrecl: 150, recfm: FB
}

// *
// //LOADCUST EXEC PGM=IEBGENER                                                     
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   DUMMY                                                           
// //SYSUT1   DD   DSN=custdata.txt,DISP=SHR,DCB=(LRECL=501,RECFM=FB)
// //SYSUT2   DD   DSN=AWS.M2.CARDDEMO.CUSTDATA.PS,DISP=(,KEEP),                   
// //                  DCB=(LRECL=500,RECFM=FB)

exec "LOADCUST", pgm: "IEBGENER", {
    dd "SYSPRINT", sysout: "*"
    dd "SYSIN", dummy: true
    dd "SYSUT1", dsn: "custdata.txt", disp: SHR, lrecl: 501, recfm: FB
    dd "SYSUT2", dsn: "AWS.M2.CARDDEMO.CUSTDATA.PS", disp: [NEW,KEEP,KEEP], lrecl: 500, recfm: FB
}

// *
// //LOADXREF EXEC PGM=IEBGENER                                                     
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   DUMMY                                                           
// //SYSUT1   DD   DSN=cardxref.txt,DISP=SHR,DCB=(LRECL=37,RECFM=FB)
// //SYSUT2   DD   DSN=AWS.M2.CARDDEMO.CARDXREF.PS,DISP=(,KEEP),                   
// //                  DCB=(LRECL=50,RECFM=FB)

exec "LOADXREF", pgm: "IEBGENER", {
    dd "SYSPRINT", sysout: "*"
    dd "SYSIN", dummy: true
    dd "SYSUT1", dsn: "cardxref.txt", disp: SHR, lrecl: 37, recfm: FB
    dd "SYSUT2", dsn: "AWS.M2.CARDDEMO.CARDXREF.PS", disp: [NEW,KEEP,KEEP], lrecl: 50, recfm: FB
}

// *
// //LOADTRXI EXEC PGM=IEBGENER                                                     
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   DUMMY                                                           
// //SYSUT1   DD   DSN=dailytran_init.txt,DISP=SHR,DCB=(LRECL=350,RECFM=FB)
// //SYSUT2   DD   DSN=AWS.M2.CARDDEMO.DALYTRAN.PS.INIT,DISP=(,KEEP),                   
// //                  DCB=(LRECL=350,RECFM=FB)

exec "LOADTRXI", pgm: "IEBGENER", {
    dd "SYSPRINT", sysout: "*"
    dd "SYSIN", dummy: true
    dd "SYSUT1", dsn: "dailytran_init.txt", disp: SHR, lrecl: 350, recfm: FB
    dd "SYSUT2", dsn: "AWS.M2.CARDDEMO.DALYTRAN.PS.INIT", disp: [NEW,KEEP,KEEP], lrecl: 350, recfm: FB
}

// *
// //LOADTRXD EXEC PGM=IEBGENER                                                     
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   DUMMY                                                           
// //SYSUT1   DD   DSN=dailytran.txt,DISP=SHR,DCB=(LRECL=351,RECFM=FB)
// //SYSUT2   DD   DSN=AWS.M2.CARDDEMO.DALYTRAN.PS,DISP=(,KEEP),                   
// //                  DCB=(LRECL=350,RECFM=FB)

exec "LOADTRXD", pgm: "IEBGENER", {
    dd "SYSPRINT", sysout: "*"
    dd "SYSIN", dummy: true
    dd "SYSUT1", dsn: "dailytran.txt", disp: SHR, lrecl: 351, recfm: FB
    dd "SYSUT2", dsn: "AWS.M2.CARDDEMO.DALYTRAN.PS", disp: [NEW,KEEP,KEEP], lrecl: 350, recfm: FB
}

// *
// //DEFTRANS EXEC PGM=IDCAMS                                                     
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   *
//     DEFINE CLUSTER (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS) -           
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
//           ERASE ) -                                                     
//           INDEX (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS.INDEX) -       
//           )                                                             
// /*                                                                              

exec "DEFTRANS", pgm: "IDCAMS", {
    dd "SYSPRINT", sysout: "*"
    // Instream content is expanded below
    dd "SYSIN", instream: [
    "    DEFINE CLUSTER (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS) -          ",
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
    "          ERASE ) -                                                     ",
    "          INDEX (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS.INDEX) -       ",
    "          )                                                             "
    ]
}

// *
// //LOADDGRP EXEC PGM=IEBGENER                                                     
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   DUMMY                                                           
// //SYSUT1   DD   DSN=discgrp.txt,DISP=SHR,DCB=(LRECL=51,RECFM=FB)
// //SYSUT2   DD   DSN=AWS.M2.CARDDEMO.DISCGRP.PS,DISP=(,KEEP),                   
// //                  DCB=(LRECL=50,RECFM=FB)

exec "LOADDGRP", pgm: "IEBGENER", {
    dd "SYSPRINT", sysout: "*"
    dd "SYSIN", dummy: true
    dd "SYSUT1", dsn: "discgrp.txt", disp: SHR, lrecl: 51, recfm: FB
    dd "SYSUT2", dsn: "AWS.M2.CARDDEMO.DISCGRP.PS", disp: [NEW,KEEP,KEEP], lrecl: 50, recfm: FB
}

// *
// //LOADCATG EXEC PGM=IEBGENER                                                     
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   DUMMY                                                           
// //SYSUT1   DD   DSN=trancatg.txt,DISP=SHR,DCB=(LRECL=61,RECFM=FB)
// //SYSUT2   DD   DSN=AWS.M2.CARDDEMO.TRANCATG.PS,DISP=(,KEEP),                   
// //                  DCB=(LRECL=60,RECFM=FB)

exec "LOADCATG", pgm: "IEBGENER", {
    dd "SYSPRINT", sysout: "*"
    dd "SYSIN", dummy: true
    dd "SYSUT1", dsn: "trancatg.txt", disp: SHR, lrecl: 61, recfm: FB
    dd "SYSUT2", dsn: "AWS.M2.CARDDEMO.TRANCATG.PS", disp: [NEW,KEEP,KEEP], lrecl: 60, recfm: FB
}

// *
// //LOADTYPE EXEC PGM=IEBGENER                                                     
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   DUMMY                                                           
// //SYSUT1   DD   DSN=trantype.txt,DISP=SHR,DCB=(LRECL=61,RECFM=FB)
// //SYSUT2   DD   DSN=AWS.M2.CARDDEMO.TRANTYPE.PS,DISP=(,KEEP),                   
// //                  DCB=(LRECL=60,RECFM=FB)

exec "LOADTYPE", pgm: "IEBGENER", {
    dd "SYSPRINT", sysout: "*"
    dd "SYSIN", dummy: true
    dd "SYSUT1", dsn: "trantype.txt", disp: SHR, lrecl: 61, recfm: FB
    dd "SYSUT2", dsn: "AWS.M2.CARDDEMO.TRANTYPE.PS", disp: [NEW,KEEP,KEEP], lrecl: 60, recfm: FB
}

// *
// //LOADBALF EXEC PGM=IEBGENER                                                     
// //SYSPRINT DD   SYSOUT=*                                                        
// //SYSIN    DD   DUMMY                                                           
// //SYSUT1   DD   DSN=tcatbal.txt,DISP=SHR,DCB=(LRECL=51,RECFM=FB)
// //SYSUT2   DD   DSN=AWS.M2.CARDDEMO.TCATBALF.PS,DISP=(,KEEP),                   
// //                  DCB=(LRECL=50,RECFM=FB)

exec "LOADBALF", pgm: "IEBGENER", {
    dd "SYSPRINT", sysout: "*"
    dd "SYSIN", dummy: true
    dd "SYSUT1", dsn: "tcatbal.txt", disp: SHR, lrecl: 51, recfm: FB
    dd "SYSUT2", dsn: "AWS.M2.CARDDEMO.TCATBALF.PS", disp: [NEW,KEEP,KEEP], lrecl: 50, recfm: FB
}

