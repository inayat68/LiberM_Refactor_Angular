package com.aws.carddemo.batchone.program;

import com.nib.commons.*;
import com.nib.commons.annotations.*;
import com.nib.commons.exceptions.*;
import com.nib.commons.storage.*;
import com.nib.commons.io.*;
import com.nib.commons.io.vsam.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.aws.carddemo.batchone.program.storage.cbact01c.*;

public class CBACT01C extends AbstractProgram {
  private KeySequentialFile acctfileFile;
  final Cbact01cRecords fs = new Cbact01cRecords();


  @ProgramStorage
  final Cbact01cWorking ws = new Cbact01cWorking();

  //Linkage
  public static class Cbact01cLinkage extends NGroup {
  }
  final Cbact01cLinkage params = new Cbact01cLinkage();

  public CBACT01C(Context context) {
    super(context);
  }

  @ProgramInit
  protected void initialize() {
    acctfileFile = new KeySequentialFile(context, "ACCTFILE") //
                   .recordKeyIs(fs.fdAcctfileRec.fdAcctId) //
                   .fileStatusIs(ws.acctfileStatus) //
                   .recordIs(fs.fdAcctfileRec);

  }

  @Override
  protected int procedure(AbstractNField... args) throws ContextException {
    if (args != null) {
      params.allocate();
    }
    procedure();
    return RETURN_CODE;
  }
  /*****************************************************************/
  protected void procedure() {
  //COB(111): DISPLAY 'START OF EXECUTION OF PROGRAM CBACT01C'.                    
    sysout.display("START OF EXECUTION OF PROGRAM CBACT01C");
  //COB(112): PERFORM 0000-ACCTFILE-OPEN.                                          
    _0000AcctfileOpen();
  //COB(114): PERFORM UNTIL END-OF-FILE = 'Y'                                      
    while (!ws.endOfFile.equals("Y")) {
  //COB(115): IF  END-OF-FILE = 'N'                                            
      if (ws.endOfFile.equals("N")) {
  //COB(116): PERFORM 1000-ACCTFILE-GET-NEXT                               
        _1000AcctfileGetNext();
  //COB(117): IF  END-OF-FILE = 'N'                                        
        if (ws.endOfFile.equals("N")) {
  //COB(118): DISPLAY ACCOUNT-RECORD                                   
          sysout.display(ws.cvact01y.accountRecord);
  //COB(119): END-IF                                                       
        }
  //COB(120): END-IF                                                           
      }
  //COB(121): END-PERFORM.                                                         
    }
  //COB(123): PERFORM 9000-ACCTFILE-CLOSE.                                         
    _9000AcctfileClose();
  //COB(125): DISPLAY 'END OF EXECUTION OF PROGRAM CBACT01C'.                      
    sysout.display("END OF EXECUTION OF PROGRAM CBACT01C");
  //COB(127): GOBACK.                                                              
    context.goback();

  }

  /*****************************************************************
  * I/O ROUTINES TO ACCESS A KSDS, VSAM DATA SET...               *
  *****************************************************************/
  protected void _1000AcctfileGetNext() {
  //COB(133): READ ACCTFILE-FILE INTO ACCOUNT-RECORD.                              
    acctfileFile.readNext(ws.cvact01y.accountRecord);
  //COB(134): IF  ACCTFILE-STATUS = '00'                                           
    if (ws.acctfileStatus.equals("00")) {
  //COB(135): MOVE 0 TO APPL-RESULT                                            
      ws.applResult.setValue(0);
  //COB(136): PERFORM 1100-DISPLAY-ACCT-RECORD                                 
      _1100DisplayAcctRecord();
  //COB(137): ELSE                                                                 
    } else {

  }

  /**SANTIX*        IF  ACCTFILE-STATUS = '10'*/
  protected void dog() {
    acctfileStatus();
  }

  /**SANTIX*        IF  ACCTFILE-STATUS = '10'*/
  protected void acctfileStatus() {
  //COB(140): MOVE 16 TO APPL-RESULT                                       
    ws.applResult.setValue(16);
  //COB(142): MOVE 12 TO APPL-RESULT                                       
    ws.applResult.setValue(12);
  //COB(145): IF  APPL-AOK                                                         
    if (ws.applAok()) {
  //COB(146): CONTINUE                                                         
      //do nothing
  //COB(147): ELSE                                                                 
    } else {
  //COB(148): IF  APPL-EOF                                                     
      if (ws.applEof()) {
  //COB(152): ELSE                                                             
      } else {
  //COB(153): DISPLAY 'ERROR READING ACCOUNT FILE'                         
        sysout.display("ERROR READING ACCOUNT FILE");
  //COB(154): MOVE ACCTFILE-STATUS TO IO-STATUS                            
        ws.ioStatus.setValue(ws.acctfileStatus);
  //COB(155): PERFORM 9910-DISPLAY-IO-STATUS                               
        _9910DisplayIoStatus();
  //COB(156): PERFORM 9999-ABEND-PROGRAM                                   
        _9999AbendProgram();
  //COB(157): END-IF                                                           
      }
  //COB(158): END-IF                                                               
    }
  //COB(159): EXIT.                                                                
    return;

  }

  /**XX*KHRTYU
  *---------------------------------------------------------------*/
  protected void _1100DisplayAcctRecord() {
  //COB(163): DISPLAY 'ACCT-ID                 :'   ACCT-ID                        
    sysout.display("ACCT-ID                 :", ws.cvact01y.accountRecord.acctId);
  //COB(164): DISPLAY 'ACCT-ACTIVE-STATUS      :'   ACCT-ACTIVE-STATUS             
    sysout.display("ACCT-ACTIVE-STATUS      :", ws.cvact01y.accountRecord.acctActiveStatus);
  //COB(165): DISPLAY 'ACCT-CURR-BAL           :'   ACCT-CURR-BAL                  
    sysout.display("ACCT-CURR-BAL           :", ws.cvact01y.accountRecord.acctCurrBal);
  //COB(166): DISPLAY 'ACCT-CREDIT-LIMIT       :'   ACCT-CREDIT-LIMIT              
    sysout.display("ACCT-CREDIT-LIMIT       :", ws.cvact01y.accountRecord.acctCreditLimit);
  //COB(167): DISPLAY 'ACCT-CASH-CREDIT-LIMIT  :'   ACCT-CASH-CREDIT-LIMIT         
    sysout.display("ACCT-CASH-CREDIT-LIMIT  :", ws.cvact01y.accountRecord.acctCashCreditLimit);
  //COB(168): DISPLAY 'ACCT-OPEN-DATE          :'   ACCT-OPEN-DATE                 
    sysout.display("ACCT-OPEN-DATE          :", ws.cvact01y.accountRecord.acctOpenDate);
  //COB(169): DISPLAY 'ACCT-EXPIRAION-DATE     :'   ACCT-EXPIRAION-DATE            
    sysout.display("ACCT-EXPIRAION-DATE     :", ws.cvact01y.accountRecord.acctExpiraionDate);
  //COB(170): DISPLAY 'ACCT-REISSUE-DATE       :'   ACCT-REISSUE-DATE              
    sysout.display("ACCT-REISSUE-DATE       :", ws.cvact01y.accountRecord.acctReissueDate);
  //COB(171): DISPLAY 'ACCT-CURR-CYC-CREDIT    :'   ACCT-CURR-CYC-CREDIT           
    sysout.display("ACCT-CURR-CYC-CREDIT    :", ws.cvact01y.accountRecord.acctCurrCycCredit);
  //COB(172): DISPLAY 'ACCT-CURR-CYC-DEBIT     :'   ACCT-CURR-CYC-DEBIT            
    sysout.display("ACCT-CURR-CYC-DEBIT     :", ws.cvact01y.accountRecord.acctCurrCycDebit);
  //COB(173): DISPLAY 'ACCT-GROUP-ID           :'   ACCT-GROUP-ID                  
    sysout.display("ACCT-GROUP-ID           :", ws.cvact01y.accountRecord.acctGroupId);
  //COB(174): DISPLAY '-------------------------------------------------'          
    sysout.display("-------------------------------------------------");
  //COB(175): EXIT.                                                                
    return;

  }

  /***---------------------------------------------------------------*/
  protected void _0000AcctfileOpen() {
  //COB(178): MOVE 8 TO APPL-RESULT.                                               
    ws.applResult.setValue(8);
  //COB(179): OPEN INPUT ACCTFILE-FILE                                             
    acctfileFile.open(OpenMode.Input);
  //COB(180): IF  ACCTFILE-STATUS = '00'                                           
    if (ws.acctfileStatus.equals("00")) {
  //COB(181): MOVE 0 TO APPL-RESULT                                            
      ws.applResult.setValue(0);
  //COB(182): ELSE                                                                 
    } else {
  //COB(183): MOVE 12 TO APPL-RESULT                                           
      ws.applResult.setValue(12);
  //COB(184): END-IF                                                               
    }
  //COB(185): IF  APPL-AOK                                                         
    if (ws.applAok()) {
  //COB(186): CONTINUE                                                         
      //do nothing
  //COB(187): ELSE                                                                 
    } else {
  //COB(188): DISPLAY 'ERROR OPENING ACCTFILE'                                 
      sysout.display("ERROR OPENING ACCTFILE");
  //COB(189): MOVE ACCTFILE-STATUS TO IO-STATUS                                
      ws.ioStatus.setValue(ws.acctfileStatus);
  //COB(190): PERFORM 9910-DISPLAY-IO-STATUS                                   
      _9910DisplayIoStatus();
  //COB(191): PERFORM 9999-ABEND-PROGRAM                                       
      _9999AbendProgram();
  //COB(192): END-IF                                                               
    }
  //COB(193): EXIT.                                                                
    return;

  }

  /***---------------------------------------------------------------*/
  protected void _9000AcctfileClose() {
  //COB(196): ADD 8 TO ZERO GIVING APPL-RESULT.       
    ws.applResult.add(8);
  //COB(199): DISPLAY 'MEJO ER CAZZO DELLA FREGNA'
    sysout.display("MEJO ER CAZZO DELLA FREGNA");
  //COB(201): CLOSE ACCTFILE-FILE                                                  
    acctfileFile.close();
  //COB(202): IF  ACCTFILE-STATUS = '00'                                           
    if (ws.acctfileStatus.equals("00")) {
  //COB(203): SUBTRACT APPL-RESULT FROM APPL-RESULT                            
      ws.applResult.subtract(ws.applResult);
  //COB(204): ELSE                                                                 
    } else {
  //COB(205): ADD 12 TO ZERO GIVING APPL-RESULT                                
      ws.applResult.add(12);
  //COB(206): END-IF                                                               
    }
  //COB(207): IF  APPL-AOK                                                         
    if (ws.applAok()) {
  //COB(208): CONTINUE                                                         
      //do nothing
  //COB(209): ELSE                                                                 
    } else {
  //COB(210): DISPLAY 'ERROR CLOSING ACCOUNT FILE'                             
      sysout.display("ERROR CLOSING ACCOUNT FILE");
  //COB(211): MOVE ACCTFILE-STATUS TO IO-STATUS                                
      ws.ioStatus.setValue(ws.acctfileStatus);
  //COB(212): PERFORM 9910-DISPLAY-IO-STATUS                                   
      _9910DisplayIoStatus();
  //COB(213): PERFORM 9999-ABEND-PROGRAM                                       
      _9999AbendProgram();
  //COB(214): END-IF                                                               
    }
  //COB(215): EXIT.                                                                
    return;

  }


  protected void _9999AbendProgram() {
  //COB(218): DISPLAY 'ABENDING PROGRAM'                                           
    sysout.display("ABENDING PROGRAM");
  //COB(219): MOVE 0 TO TIMING                                                     
    ws.timing.setValue(0);
  //COB(220): MOVE 999 TO ABCODE                                                   
    ws.abcode.setValue(999);
  //COB(221): CALL 'CEE3ABD'.                                                      
    throw new AbendException(ws.abcode.getInt());

  }

  /*****************************************************************/
  protected void _9910DisplayIoStatus() {
  //COB(225): IF  IO-STATUS NOT NUMERIC                                            
  //COB(225): OR  IO-STAT1 = '9'                                                   
    if (!ws.ioStatus.isNumeric()
        || ws.ioStatus.ioStat1.equals("9")) {
  //COB(227): MOVE IO-STAT1 TO IO-STATUS-04(1:1)                               
      ws.ioStatus04.setValue(ws.ioStatus.ioStat1, 0, 1);
  //COB(228): MOVE 0        TO TWO-BYTES-BINARY                                
      ws.twoBytesBinary.setValue(0);
  //COB(229): MOVE IO-STAT2 TO TWO-BYTES-RIGHT                                 
      ws.twoBytesAlpha.twoBytesRight.setValue(ws.ioStatus.ioStat2);
  //COB(230): MOVE TWO-BYTES-BINARY TO IO-STATUS-0403                          
      ws.ioStatus04.ioStatus0403.setValue(ws.twoBytesBinary);
  //COB(231): DISPLAY 'FILE STATUS IS: NNNN' IO-STATUS-04                      
      sysout.display("FILE STATUS IS: NNNN", ws.ioStatus04);
  //COB(232): ELSE                                                                 
    } else {
  //COB(233): MOVE '0000' TO IO-STATUS-04                                      
      ws.ioStatus04.setString("0000");
  //COB(234): MOVE IO-STATUS TO IO-STATUS-04(3:2)                              
      ws.ioStatus04.setValue(ws.ioStatus, 0, 2, 2);
  //COB(235): DISPLAY 'FILE STATUS IS: NNNN' IO-STATUS-04                      
      sysout.display("FILE STATUS IS: NNNN", ws.ioStatus04);
  //COB(236): END-IF                                                               
    }
  //COB(237): EXIT.               
    return;

  }



}
