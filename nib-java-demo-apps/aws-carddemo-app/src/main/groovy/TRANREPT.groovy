import com.nib.groovybatch.NibJob
import com.nib.motorhead.scripting.MotorheadClass
import groovy.transform.Field
import groovy.transform.BaseScript
import procs.REPROC


@BaseScript NibJob TRANREPT
@Field @MotorheadClass('A') def JOBCLASS

// TRANREPT JOB 'TRANSACTION REPORT',                                            
//  CLASS=A,MSGCLASS=0,NOTIFY=&SYSUID              

job "TRANREPT"

// DESCRIPTION = "TRANSACTION REPORT"
// CLASS = "A"
// MSGCLASS = "0"
// NOTIFY = "${SYSUID}"

// * ********************************************************`***********        
// * Unload the processed transaction file                                       
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

// * ********************************************************`***********        
// * Unload the processed transaction file                                       
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

exec "STEP05R", pgm: "SORT", parm: "verbose", {
    dd "SORTIN", dsn: "AWS.M2.CARDDEMO.TRANSACT.BKUP", gdg: 1, disp: SHR
    // Instream content is expanded below
    dd "SYMNAMES", instream: [
    "TRAN-CARD-NUM,263,16,ZD                                                 ",
    "TRAN-PROC-DT,305,10,CH                                                  ",
    "PARM-START-DATE,C'2022-01-01'                                      //Dat",
    "PARM-END-DATE,C'2022-07-06'                                        //Dat"
    ]
    // Instream content is expanded below
    dd "SYSIN", instream: [
    " SORT FIELDS=(TRAN-CARD-NUM,A)                                          ",
    " INCLUDE COND=(TRAN-PROC-DT,GE,PARM-START-DATE,AND,                     ",
    "         TRAN-PROC-DT,LE,PARM-END-DATE)                                 "
    ]
    dd "SYSOUT", sysout: "*"
    dd "SORTOUT", dsn: "AWS.M2.CARDDEMO.TRANSACT.DALY", gdg: 1, disp: [NEW,CATLG,DELETE]
}

// * *******************************************************************         
// * Produce a formatted report for processed transactions                       
// //* *******************************************************************         
// //STEP10R EXEC PGM=CBTRN03C                                                     
// //STEPLIB  DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.LOADLIB                                          
// //SYSOUT   DD SYSOUT=*                                                          
// //SYSPRINT DD SYSOUT=*                                                          
// //* Input files                                                                 
// //TRANFILE DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.TRANSACT.DALY(+1)                                
// //CARDXREF DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.CARDXREF.VSAM.KSDS                               
// //TRANTYPE DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.TRANTYPE.VSAM.KSDS                               
// //TRANCATG DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.TRANCATG.VSAM.KSDS                               
// //DATEPARM DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.DATEPARM                                         
// //* Output files                                                                
// //TRANREPT DD DISP=(NEW,CATLG,DELETE),                                          
// //         UNIT=SYSDA,                                                          
// //         DCB=(LRECL=133,RECFM=FB,BLKSIZE=0),                                  
// //         SPACE=(CYL,(1,1),RLSE),                                              

exec "STEP10R", pgm: "CBTRN03C", {
    // dd "STEPLIB", // dsn: "AWS.M2.CARDDEMO.LOADLIB", disp: SHR
    dd "SYSOUT", sysout: "*"
    dd "SYSPRINT", sysout: "*"
    dd "TRANFILE", dsn: "AWS.M2.CARDDEMO.TRANSACT.DALY", gdg: 1, disp: SHR
    dd "CARDXREF", dsn: "AWS.M2.CARDDEMO.CARDXREF.VSAM.KSDS", disp: SHR
    dd "TRANTYPE", dsn: "AWS.M2.CARDDEMO.TRANTYPE.VSAM.KSDS", disp: SHR
    dd "TRANCATG", dsn: "AWS.M2.CARDDEMO.TRANCATG.VSAM.KSDS", disp: SHR
    dd "DATEPARM", dsn: "AWS.M2.CARDDEMO.DATEPARM", disp: SHR
    dd "TRANREPT", dsn: "AWS.M2.CARDDEMO.TRANREPT", gdg: 1, disp: [NEW,CATLG,DELETE], lrecl: 133, recfm: FB
}

