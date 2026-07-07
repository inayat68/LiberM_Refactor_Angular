//_INICATG JOB 'Delete define input SEQ files',CLASS=A,MSGCLASS=0,                 
// NOTIFY=&SYSUID                         
//******************************************************************
//* N.I.B. 
//* All Rights Reserved.                                            
//*
//******************************************************************         
//*@NIB_NATIVE_CODE[PWSH]$PDS="AWS.M2.CARDDEMO.CNTL"
//*@NIB_NATIVE_CODE[PWSH]$PDSDIR="$env:DATA_ROOT\$PDS"
//*@NIB_NATIVE_CODE[PWSH]nibcams delete $PDS
//*@NIB_NATIVE_CODE[PWSH]nibcams define nonvsam $PDS --lrecl 80 --recfmt lseq --pds --physical $PDSDIR
//*@NIB_NATIVE_CODE[PWSH]If(!(Test-Path -PathType Container $PDSDIR))
//*@NIB_NATIVE_CODE[PWSH]{
//*@NIB_NATIVE_CODE[PWSH]    New-Item -ItemType Directory -Path $PDSDIR
//*@NIB_NATIVE_CODE[PWSH]}
//*@NIB_NATIVE_CODE[PWSH]Copy-Item "$env:DATA_ROOT\REPROCT.txt" -Destination "$PDSDIR\REPROCT" -Force
//*@NIB_NATIVE_CODE[GROOVY]def PDS = "AWS.M2.CARDDEMO.CNTL"
//*@NIB_NATIVE_CODE[GROOVY]def pdsDir = "${System.env['DATA_ROOT']}/${PDS}"
//*@NIB_NATIVE_CODE[GROOVY]def path = new File(pdsDir);
//*@NIB_NATIVE_CODE[GROOVY]try {
//*@NIB_NATIVE_CODE[GROOVY]    NibVsamCommandLine.invoke(new String[]{"delete", PDS})
//*@NIB_NATIVE_CODE[GROOVY]} catch (Exception exc) {
//*@NIB_NATIVE_CODE[GROOVY]
//*@NIB_NATIVE_CODE[GROOVY]}
//*@NIB_NATIVE_CODE[GROOVY]NibVsamCommandLine.invoke(new String[]{"define", "nonvsam", "--recfmt=LSEQ", "--lrecl=80,80", "--pds", "--physical=${pdsDir}", PDS})
//*@NIB_NATIVE_CODE[GROOVY]if (!path.exists()) {
//*@NIB_NATIVE_CODE[GROOVY]    path.mkdir()
//*@NIB_NATIVE_CODE[GROOVY]}
//*@NIB_NATIVE_CODE[GROOVY]new File("${pdsDir}/REPROCT").bytes = new File("${System.env['DATA_ROOT']}/REPROCT.txt").bytes
//******************************************************************         
//* DELETE INPUT SEQ FILES                                                      
//******************************************************************         
//*                                                                 
//DELEACCT  EXEC PGM=IEFBR14                                                      
//SYSPRINT DD   SYSOUT=*                                                        
//DD01 DD DSN=AWS.M2.CARDDEMO.ACCTDATA.PS,DISP=(MOD,DELETE,DELETE)              
//*                                                                 
//DELECARD EXEC PGM=IEFBR14                                                      
//SYSPRINT DD   SYSOUT=*                                                        
//DD01 DD DSN=AWS.M2.CARDDEMO.CARDDATA.PS,DISP=(MOD,DELETE,DELETE)              
//*                                                                 
//DELECUST EXEC PGM=IEFBR14                                                      
//SYSPRINT DD   SYSOUT=*                                                        
//DD01 DD DSN=AWS.M2.CARDDEMO.CUSTDATA.PS,DISP=(MOD,DELETE,DELETE)              
//*                                                                 
//DELEXREF EXEC PGM=IEFBR14                                                      
//SYSPRINT DD   SYSOUT=*                                                        
//DD01 DD DSN=AWS.M2.CARDDEMO.CARDXREF.PS,DISP=(MOD,DELETE,DELETE)              
//*                                                                 
//DELETRXI EXEC PGM=IEFBR14                                                      
//SYSPRINT DD   SYSOUT=*                                                        
//DD01 DD DSN=AWS.M2.CARDDEMO.DALYTRAN.PS.INIT,DISP=(MOD,DELETE,DELETE)              
//*                                                                 
//DELETRXD EXEC PGM=IEFBR14                                                      
//SYSPRINT DD   SYSOUT=*                                                        
//DD01 DD DSN=AWS.M2.CARDDEMO.DALYTRAN.PS,DISP=(MOD,DELETE,DELETE)              
//*                                                                 
//DELETRAN EXEC PGM=IEFBR14                                                      
//SYSPRINT DD   SYSOUT=*                                                        
//DD01 DD DSN=AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS,
//            DISP=(MOD,DELETE,DELETE)              
//*                                                                 
//DELEDGRP EXEC PGM=IEFBR14                                                      
//SYSPRINT DD   SYSOUT=*                                                        
//DD01 DD DSN=AWS.M2.CARDDEMO.DISCGRP.PS,DISP=(MOD,DELETE,DELETE)              
//*                                                                 
//DELECATG EXEC PGM=IEFBR14                                                      
//SYSPRINT DD   SYSOUT=*                                                        
//DD01 DD DSN=AWS.M2.CARDDEMO.TRANCATG.PS,DISP=(MOD,DELETE,DELETE)              
//*                                                                 
//DELETYPE EXEC PGM=IEFBR14                                                      
//SYSPRINT DD   SYSOUT=*                                                        
//DD01 DD DSN=AWS.M2.CARDDEMO.TRANTYPE.PS,DISP=(MOD,DELETE,DELETE)              
//*                                                                 
//DELEBALF EXEC PGM=IEFBR14                                                      
//SYSPRINT DD   SYSOUT=*                                                        
//DD01 DD DSN=AWS.M2.CARDDEMO.TCATBALF.PS,DISP=(MOD,DELETE,DELETE)              
//*                                                                             
//* *******************************************************************         
//* DEFINE AND LOAD SEQUENTIAL FILE                                             
//* *******************************************************************         
//*
//LOADACCT EXEC PGM=IEBGENER                                                     
//SYSPRINT DD   SYSOUT=*                                                        
//SYSIN    DD   DUMMY                                                           
//SYSUT1   DD   DSN=acctdata.txt,DISP=SHR,DCB=(LRECL=301,RECFM=FB)
//SYSUT2   DD   DSN=AWS.M2.CARDDEMO.ACCTDATA.PS,DISP=(,KEEP),
//                  DCB=(LRECL=300,RECFM=FB)
//*
//LOADCARD EXEC PGM=IEBGENER                                                     
//SYSPRINT DD   SYSOUT=*                                                        
//SYSIN    DD   DUMMY                                                           
//SYSUT1   DD   DSN=carddata.txt,DISP=SHR,DCB=(LRECL=151,RECFM=FB)
//SYSUT2   DD   DSN=AWS.M2.CARDDEMO.CARDDATA.PS,DISP=(,KEEP),                   
//                  DCB=(LRECL=150,RECFM=FB)
//*
//LOADCUST EXEC PGM=IEBGENER                                                     
//SYSPRINT DD   SYSOUT=*                                                        
//SYSIN    DD   DUMMY                                                           
//SYSUT1   DD   DSN=custdata.txt,DISP=SHR,DCB=(LRECL=501,RECFM=FB)
//SYSUT2   DD   DSN=AWS.M2.CARDDEMO.CUSTDATA.PS,DISP=(,KEEP),                   
//                  DCB=(LRECL=500,RECFM=FB)
//*
//LOADXREF EXEC PGM=IEBGENER                                                     
//SYSPRINT DD   SYSOUT=*                                                        
//SYSIN    DD   DUMMY                                                           
//SYSUT1   DD   DSN=cardxref.txt,DISP=SHR,DCB=(LRECL=37,RECFM=FB)
//SYSUT2   DD   DSN=AWS.M2.CARDDEMO.CARDXREF.PS,DISP=(,KEEP),                   
//                  DCB=(LRECL=50,RECFM=FB)
//*
//LOADTRXI EXEC PGM=IEBGENER                                                     
//SYSPRINT DD   SYSOUT=*                                                        
//SYSIN    DD   DUMMY                                                           
//SYSUT1   DD   DSN=dailytran_init.txt,DISP=SHR,DCB=(LRECL=350,RECFM=FB)
//SYSUT2   DD   DSN=AWS.M2.CARDDEMO.DALYTRAN.PS.INIT,DISP=(,KEEP),                   
//                  DCB=(LRECL=350,RECFM=FB)
//*
//LOADTRXD EXEC PGM=IEBGENER                                                     
//SYSPRINT DD   SYSOUT=*                                                        
//SYSIN    DD   DUMMY                                                           
//SYSUT1   DD   DSN=dailytran.txt,DISP=SHR,DCB=(LRECL=351,RECFM=FB)
//SYSUT2   DD   DSN=AWS.M2.CARDDEMO.DALYTRAN.PS,DISP=(,KEEP),                   
//                  DCB=(LRECL=350,RECFM=FB)
//*
//DEFTRANS EXEC PGM=IDCAMS                                                     
//SYSPRINT DD   SYSOUT=*                                                        
//SYSIN    DD   *
    DEFINE CLUSTER (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS) -           
          CYLINDERS(1 5) -                                              
          VOLUMES(AWSHJ1 -                                              
          ) -                                                           
          KEYS(16 0) -                                                  
          RECORDSIZE(350 350) -                                         
          SHAREOPTIONS(2 3) -                                           
          ERASE -                                                       
          INDEXED -                                                     
          ) -                                                           
          DATA (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS.DATA) -         
          ERASE ) -                                                     
          INDEX (NAME(AWS.M2.CARDDEMO.TRANSACT.VSAM.KSDS.INDEX) -       
          )                                                             
/*                                                                              
//*
//LOADDGRP EXEC PGM=IEBGENER                                                     
//SYSPRINT DD   SYSOUT=*                                                        
//SYSIN    DD   DUMMY                                                           
//SYSUT1   DD   DSN=discgrp.txt,DISP=SHR,DCB=(LRECL=51,RECFM=FB)
//SYSUT2   DD   DSN=AWS.M2.CARDDEMO.DISCGRP.PS,DISP=(,KEEP),                   
//                  DCB=(LRECL=50,RECFM=FB)
//*
//LOADCATG EXEC PGM=IEBGENER                                                     
//SYSPRINT DD   SYSOUT=*                                                        
//SYSIN    DD   DUMMY                                                           
//SYSUT1   DD   DSN=trancatg.txt,DISP=SHR,DCB=(LRECL=61,RECFM=FB)
//SYSUT2   DD   DSN=AWS.M2.CARDDEMO.TRANCATG.PS,DISP=(,KEEP),                   
//                  DCB=(LRECL=60,RECFM=FB)
//*
//LOADTYPE EXEC PGM=IEBGENER                                                     
//SYSPRINT DD   SYSOUT=*                                                        
//SYSIN    DD   DUMMY                                                           
//SYSUT1   DD   DSN=trantype.txt,DISP=SHR,DCB=(LRECL=61,RECFM=FB)
//SYSUT2   DD   DSN=AWS.M2.CARDDEMO.TRANTYPE.PS,DISP=(,KEEP),                   
//                  DCB=(LRECL=60,RECFM=FB)
//*
//LOADBALF EXEC PGM=IEBGENER                                                     
//SYSPRINT DD   SYSOUT=*                                                        
//SYSIN    DD   DUMMY                                                           
//SYSUT1   DD   DSN=tcatbal.txt,DISP=SHR,DCB=(LRECL=51,RECFM=FB)
//SYSUT2   DD   DSN=AWS.M2.CARDDEMO.TCATBALF.PS,DISP=(,KEEP),                   
//                  DCB=(LRECL=50,RECFM=FB)
//*