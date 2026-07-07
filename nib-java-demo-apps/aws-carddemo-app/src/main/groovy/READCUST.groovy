import com.nib.groovybatch.NibJob
import com.nib.motorhead.scripting.MotorheadClass
import groovy.transform.Field
import groovy.transform.BaseScript


@BaseScript NibJob READCUST
@Field @MotorheadClass('A') def JOBCLASS

// READCUST JOB 'Read Customer Data file',CLASS=A,MSGCLASS=0,
//  NOTIFY=&SYUID

job "READCUST"

// DESCRIPTION = "Read Customer Data file"
// CLASS = "A"
// MSGCLASS = "0"
// NOTIFY = "${SYUID}"

// * *******************************************************************
// * RUN THE PROGRAM THAT READS THE CUSTOMER MASTER VSAM FILE
// * *******************************************************************
// //STEP05 EXEC PGM=CBCUS01C
// //STEPLIB  DD DISP=SHR,
// //         DSN=AWS.M2.CARDDEMO.LOADLIB
// //CUSTFILE DD DISP=SHR,
// //         DSN=AWS.M2.CARDDEMO.CUSTDATA.VSAM.KSDS
// //SYSOUT   DD SYSOUT=*
// //SYSPRINT DD SYSOUT=*

exec "STEP05", pgm: "CBCUS01C", {
    // dd "STEPLIB", // dsn: "AWS.M2.CARDDEMO.LOADLIB", disp: SHR
    dd "CUSTFILE", dsn: "AWS.M2.CARDDEMO.CUSTDATA.VSAM.KSDS", disp: SHR
    dd "SYSOUT", sysout: "*"
    dd "SYSPRINT", sysout: "*"
}

