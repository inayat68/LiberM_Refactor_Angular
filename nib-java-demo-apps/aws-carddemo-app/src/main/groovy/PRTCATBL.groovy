import com.nib.groovybatch.NibJob
import com.nib.motorhead.scripting.MotorheadClass
import groovy.transform.Field
import groovy.transform.BaseScript
import procs.REPROC


@BaseScript NibJob PRTCATBL
@Field @MotorheadClass('A') def JOBCLASS

// PRTCATBL JOB 'Print Trasaction Category Balance File',                        
//  CLASS=A,MSGCLASS=0,NOTIFY=&SYSUID           

job "PRTCATBL"

// DESCRIPTION = "Print Trasaction Category Balance File"
// CLASS = "A"
// MSGCLASS = "0"
// NOTIFY = "${SYSUID}"

// *
// //DELDEF   EXEC PGM=IEFBR14
// //THEFILE  DD DISP=(MOD,DELETE),
// //         UNIT=SYSDA,
// //         SPACE=(TRK,(1,1),RLSE),
// //         DSN=AWS.M2.CARDDEMO.TCATBALF.REPT

exec "DELDEF", pgm: "IEFBR14", {
    dd "THEFILE", dsn: "AWS.M2.CARDDEMO.TCATBALF.REPT", disp: [MOD,DELETE]
}

// * ********************************************************`***********        
// * Unload the processed transaction category balance file                      
// * *******************************************************************         
// //STEP05R EXEC PROC=REPROC,                                                     
// // CNTLLIB=AWS.M2.CARDDEMO.CNTL                                                 
// //*                                                                             
// //PRC001.FILEIN  DD DISP=SHR,                                                   
// //        DSN=AWS.M2.CARDDEMO.TCATBALF.VSAM.KSDS                                
// //*                                                                             
// //PRC001.FILEOUT DD DISP=(NEW,CATLG,DELETE),                                    
// //        UNIT=SYSDA,                                                           
// //        DCB=(LRECL=50,RECFM=FB,BLKSIZE=0),                                   
// //        SPACE=(CYL,(1,1),RLSE),                                               
// //        DSN=AWS.M2.CARDDEMO.TCATBALF.BKUP(+1)                                 

exec "STEP05R", proc: procs.REPROC, CNTLLIB: "AWS.M2.CARDDEMO.CNTL", {
    dd "PRC001.FILEIN", dsn: "AWS.M2.CARDDEMO.TCATBALF.VSAM.KSDS", disp: SHR
    dd "PRC001.FILEOUT", dsn: "AWS.M2.CARDDEMO.TCATBALF.BKUP", gdg: 1, disp: [NEW,CATLG,DELETE], lrecl: 50, recfm: FB
}

// * *******************************************************************         
// * Filter the TCATBALFions for a the parm date and sort by card num            
// * *******************************************************************         
// //STEP10R  EXEC PGM=SORT                                                        
// //SORTIN   DD DISP=SHR,                                                         
// //         DSN=AWS.M2.CARDDEMO.TCATBALF.BKUP(+1)                                
// //SYMNAMES DD *                                                                 
// TRANCAT-ACCT-ID,1,11,ZD                                                         
// TRANCAT-TYPE-CD,12,2,CH                                                         
// TRANCAT-CD,14,4,ZD
// TRAN-CAT-BAL,18,11,ZD
// //SYSIN    DD *                                                                 
// //SYSIN    DD *                                                                 
//  SORT FIELDS=(TRANCAT-ACCT-ID,A,TRANCAT-TYPE-CD,A,TRANCAT-CD,A)                 
//  OUTREC FIELDS=(TRANCAT-ACCT-ID,X,
//      TRANCAT-TYPE-CD,X,
//      TRANCAT-CD,X,
//      TRAN-CAT-BAL,EDIT=(TTTTTTTTT.TT),9X)
// /*                                                                              
// //SYSOUT   DD SYSOUT=*                                                          
// //SORTOUT  DD DISP=(NEW,CATLG,DELETE),                                          
// //         UNIT=SYSDA,                                                          
// //         DCB=(LRECL=40,RECFM=FB,BLKSIZE=0),                                   
// //         SPACE=(CYL,(1,1),RLSE),                                              

exec "STEP10R", pgm: "SORT", parm: "verbose", {
    dd "SORTIN", dsn: "AWS.M2.CARDDEMO.TCATBALF.BKUP", gdg: 1, disp: SHR
    // Instream content is expanded below
    dd "SYMNAMES", instream: [
    "TRANCAT-ACCT-ID,1,11,ZD                                                 ",
    "TRANCAT-TYPE-CD,12,2,CH                                                 ",
    "TRANCAT-CD,14,4,ZD",
    "TRAN-CAT-BAL,18,11,ZD"
    ]
    // Instream content is expanded below
    dd "SYSIN", instream: [
    " SORT FIELDS=(TRANCAT-ACCT-ID,A,TRANCAT-TYPE-CD,A,TRANCAT-CD,A)         ",
    " OUTREC FIELDS=(TRANCAT-ACCT-ID,X,",
    "     TRANCAT-TYPE-CD,X,",
    "     TRANCAT-CD,X,",
    "     TRAN-CAT-BAL,EDIT=(TTTTTTTTT.TT),9X)"
    ]
    dd "SYSOUT", sysout: "*"
    dd "SORTOUT", dsn: "AWS.M2.CARDDEMO.TCATBALF.REPT", disp: [NEW,CATLG,DELETE], lrecl: 40, recfm: FB
}

