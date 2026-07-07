package com.aws.carddemo.batch.program;

import com.aws.carddemo.batch.program.constants.PsdittoConstants;
import com.nib.asm.NAsmBatchProgram;
import com.nib.asm.exceptions.NAsmAbendException;
import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.exceptions.NAsmExitProgramException;
import com.nib.asm.storage.INAsmStandardIO;
import com.nib.asm.storage.NAsmField;
import com.nib.asm.storage.NAsmFile;
import com.nib.asm.storage.NAsmFile.NAsmFileType;
import com.nib.asm.storage.NAsmStandardIO;
import com.nib.asm.utils.NAsmEnvironment;
import com.nib.asm.utils.NAsmStrings;

import com.aws.carddemo.batch.program.storage.psditto.Charline;
import com.aws.carddemo.batch.program.storage.psditto.Desctbl2;
import com.aws.carddemo.batch.program.storage.psditto.Dummy892;
import com.aws.carddemo.batch.program.storage.psditto.Guide;
import com.aws.carddemo.batch.program.storage.psditto.Numrline;
import com.aws.carddemo.batch.program.storage.psditto.PsdittoStorage;
import com.aws.carddemo.batch.program.storage.psditto.Pspsline;
import com.nib.commons.Context;
import com.nib.commons.annotations.ProgramStorage;
import com.nib.commons.storage.AbstractNField;

/**
 *
 * <b>Tulip - ASM370 to JAVA.</b> <br/>
 * <br/>
 *
 * <br/>
 *
 * @version 2.2.8
 *
 * @author mLogica
 */
public class PSDITTO extends NAsmBatchProgram {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /*-----------------------------------------------------------------
   *    Files declaration                    
   *-----------------------------------------------------------------*/
  // ASM:00838 INPUT       DCB  DDNAME=INPUT,DSORG=PS,MACRF=(GL),EODAD=EOF,EXLST=EXLST
  private NAsmFile input = new NAsmFile("INPUT", NAsmFileType.FLAT);
  // ASM:00839 SELECT      DCB  DDNAME=SELECT,DSORG=PS,MACRF=(GL),EODAD=DONESEL
  private NAsmFile select = new NAsmFile("SELECT", NAsmFileType.FLAT);
  // ASM:00840 LCRFLDS     DCB  DDNAME=LCRFLDS,DSORG=PS,MACRF=(GM),EODAD=EOFLCR
  private NAsmFile lcrflds = new NAsmFile("LCRFLDS", NAsmFileType.FLAT);
  // ASM:00841 OUTPUT      DCB  DDNAME=OUTPUT,DSORG=PS,MACRF=(PM)
  private NAsmFile output = new NAsmFile("OUTPUT", NAsmFileType.FLAT);

  /*-----------------------------------------------------------------
   *    Working storage declaration                    
   *-----------------------------------------------------------------*/
  @ProgramStorage
  final private PsdittoStorage storage = new PsdittoStorage();

  /*-----------------------------------------------------------------
   *    Handle NOP/TS instructions                    
   *-----------------------------------------------------------------*/
  private boolean unable = false;
  private boolean first = false;
  private boolean skiponce = false;
  private boolean beglin = false;
  private boolean askip = false;
  private boolean abeglin = false;
  private boolean lhead = false;
  private boolean lcr1 = false;
  private boolean onlyonce = false;

  /*-----------------------------------------------------------------
   *    Handle WTO messaging                    
   *-----------------------------------------------------------------*/
  private String wtof1 = "*------------------------------------------------------------------------------*";
  private String wto1 = "*                                                                              *";
  private String wto2 = "*                                                                              *";
  private String wto3 = "*                                                                              *";
  private String wto4 = "*                                                                              *";
  private String wtof2 = "*------------------------------------------------------------------------------*";

  /**
   *
   * @throws NAsmException if there is a specific issue
   */
  public PSDITTO(Context context) {
      super(context);
  }

  /**
   * Assembler program PSDITTO entrypoint.
   *
   * @throws NAsmException if there is a specific issue
   */
  @Override
  protected int procedure(AbstractNField... parameters) throws NAsmException {
    // ASM <Code_block_id:MAIN>
    // ASM:00001 PSDITTO  PSBEGIN REG=2
    // ASM:00002          EJECT
    // ASM:00003          PRINT  NOGEN
    // ASM:00004 *--------------------------------------------------------------------*
    // ASM:00005 *                                                                    *
    // ASM:00006 *        MODULE  :  PSDITTO    ==> STANDALONE OR CALLABLE            *
    // ASM:00007 *        DATE    :  06/01/92                                         *
    // ASM:00008 *                                                                    *
    // ASM:00009 *        PURPOSE :  DITTO INPUT RECORDS IN EITHER STANDARD DITTO     *
    // ASM:00010 *                   FORM OR SEGMENTED FORM FOR RECORDS IN THE        *
    // ASM:00011 *                   SYSTEM UPGRADE OSCR FORMAT.                      *
    // ASM:00012 *                                                                    *
    // ASM:00013 *        PARMS   :  FORMAT IS ==> PARM='NUMRECS=00000,PS HEADER=N'   *
    // ASM:00014 *                                                                    *
    // ASM:00015 *                   'NUMRECS=00000' - NUMBER OF RECORDS TO DITTO     *
    // ASM:00016 *                                                                    *
    // ASM:00017 *                   'PS HEADER=N'   - DEFAULT. STANDARD DITTO        *
    // ASM:00018 *                                                                    *
    // ASM:00019 *                   'PS HEADER=Y'   - INDICATES THAT THE RECORD HAS  *
    // ASM:00020 *                                     AN OSCR HEADER AND THAT THE    *
    // ASM:00021 *                                     SEGMENTED DITTO FORM SHOULD    *
    // ASM:00022 *                                     BE OUTPUT.                     *
    // ASM:00023 *                                                                    *
    // ASM:00024 *        STANDALONE    :  SET UP JCL BY USING THE NAsmF ON PRO.PSMAIN  *
    // ASM:00025 *                                                                    *
    // ASM:00026 *        CALLED MODULE :                                             *
    // ASM:00027 *                                                                    *
    // ASM:00028 *           FROM THE CALLING MODULE, CALL THIS PROGRAM ONCE FOR      *
    // ASM:00029 *           EVERY RECORD WHICH YOU WISH TO DITTO. THE DCB FOR THE    *
    // ASM:00030 *           OUT FILE MUST BE DEFINED IN THE CALLING MODULE. PSDITTO  *
    // ASM:00031 *           REQUIRES INFO PASSED FROM CALLING MODULE IN THIS FORMAT. *
    // ASM:00032 *                                                                    *
    // ASM:00033 *             R1 = ADDR OF A PARMLIST AREA IN THE FOLLOWING FORMAT.  *
    // ASM:00034 *                  PARMLIST   DC  CL22'CALLED,PS HEADER=N'           *
    // ASM:00035 *                                                                    *
    // ASM:00036 *             R2 = ADDR OF A LIST OF 3 FULL WORD ADDRESSES.          *
    // ASM:00037 *                  ADDR 1 = ADDR OF JFCB AREA FOR TARGETED OUT FILE. *
    // ASM:00038 *                  ADDR 2 = ADDR OF DCB FOR THE OUT FILE.            *
    // ASM:00039 *                  ADDR 3 = ADDR OF RECORD TO DITTO TO THE OUT FILE. *
    // ASM:00040 *                                                                    *
    // ASM:00041 *        NOTE : IF MULTIPLE DITTOS ARE TO BE MADE FROM THE SAME      *
    // ASM:00042 *               CALLING MODULE, THEN FOR EACH DITTO DCB THERE MUST   *
    // ASM:00043 *               BE A UNIQUE LOAD OF THE PSDITTO ENTRY POINT ADDRESS. *
    // ASM:00044 *                                                                    *
    // ASM:00045 *        SELECT OPTION :  UNDER DD NAME SELECT, LIST ONE OR MORE     *
    // ASM:00046 *        SEGMENT IDENTIFIERS (SRC,LCR,MDB ETC.). MAX OF 1 PER LINE.  *
    // ASM:00047 *                                                                    *
    // ASM:00048 *        LCRFLDS OPTION :  UNDER DD NAME LCRFLDS,                    *
    // ASM:00049 *        HAVE THESE CARDS FIRST - '# OF FIELDS=XXX'  (NUMERIC VALUE) *
    // ASM:00050 *                               - 'OUTPUT FORM=XXXXX' (CHECK/AUDIT)  *
    // ASM:00051 *        NEXT, LIST 1 PER LINE, EACH OF THE FIELD LENGTHS IN THE     *
    // ASM:00052 *        ORDER CORRESPONDING TO THE LCR RECORD LAYOUT.               *
    // ASM:00053 *        2 DIGITS PER LENGTH. FIELDS OVER 99 MUST BE SPLIT.          *
    // ASM:00054 *                                                                    *
    // ASM:00055 *--------------------------------------------------------------------*
    // ASM:00056          SPACE 2
    // ASM:00057 *---------------------------------------------------------------------*
    // ASM:00058 *                                                                     *
    // ASM:00059 *        REGISTER USAGE                                               *
    // ASM:00060 *                                                                     *
    // ASM:00061 *        R 0 - WORK REGISTER                                          *
    // ASM:00062 *        R 1 - WORK REGISTER/ PARM COMMUNICATION WITH CALLER MODULE   *
    // ASM:00063 *        R 2 - WORK REGISTER/ PARM COMMUNICATION WITH CALLER MODULE   *
    // ASM:00064 *        R 3 - BAL  REGISTER                                          *
    // ASM:00065 *        R 4 - BAL  REGISTER                                          *
    // ASM:00066 *        R 5 - BAL  REGISTER                                          *
    // ASM:00067 *        R 6 - WORK REGISTER                                          *
    // ASM:00068 *        R 7 - RECORD LENGTH                                          *
    // ASM:00069 *        R 8 - INPUT RECORD ADDRESS                                   *
    // ASM:00070 *        R 9 - ADDR OF DISPLACEMENTS / BASE FOR SUB-PROGRAM           *
    // ASM:00071 *        R10 - WORK REGISTER                                          *
    // ASM:00072 *        R11 - WORK REGISTER                                          *
    // ASM:00073 *        R12 - BASE REGISTER TWO                                      *
    // ASM:00074 *        R13 - BASE REGISTER ONE                                      *
    // ASM:00075 *        R14 - WORK REGISTER                                          *
    // ASM:00076 *        R15 - WORK REGISTER                                          *
    // ASM:00077 *                                                                     *
    // ASM:00078 *                                                                     *
    // ASM:00079 *---------------------------------------------------------------------*
    // ASM:00080          EJECT
    // ASM:00081 *--------------------------------------------------------------------*
    // ASM:00082 *        I N I T I A L   S E T U P   R O U T I N E S                 *
    // ASM:00083 *--------------------------------------------------------------------*
    // ASM:00084          SPACE
    saveRegistersPosition(REG_14, REG_12, getReg13(), 12);
    //--------------------------------------------------------------------*
    // *
    // MODULE  :  PSDITTO    ==> STANDALONE OR CALLABLE            *
    // DATE    :  06/01/92                                         *
    // *
    // PURPOSE :  DITTO INPUT RECORDS IN EITHER STANDARD DITTO     *
    // FORM OR SEGMENTED FORM FOR RECORDS IN THE        *
    // SYSTEM UPGRADE OSCR FORMAT.                      *
    // *
    // PARMS   :  FORMAT IS ==> PARM='NUMRECS=00000,PS HEADER=N'   *
    // *
    // 'NUMRECS=00000' - NUMBER OF RECORDS TO DITTO     *
    // *
    // 'PS HEADER=N'   - DEFAULT. STANDARD DITTO        *
    // *
    // 'PS HEADER=Y'   - INDICATES THAT THE RECORD HAS  *
    // AN OSCR HEADER AND THAT THE    *
    // SEGMENTED DITTO FORM SHOULD    *
    // BE OUTPUT.                     *
    // *
    // STANDALONE    :  SET UP JCL BY USING THE NAsmF ON PRO.PSMAIN  *
    // *
    // CALLED MODULE :                                             *
    // *
    // FROM THE CALLING MODULE, CALL THIS PROGRAM ONCE FOR      *
    // EVERY RECORD WHICH YOU WISH TO DITTO. THE DCB FOR THE    *
    // OUT FILE MUST BE DEFINED IN THE CALLING MODULE. PSDITTO  *
    // REQUIRES INFO PASSED FROM CALLING MODULE IN THIS FORMAT. *
    // *
    // R1 = ADDR OF A PARMLIST AREA IN THE FOLLOWING FORMAT.  *
    // PARMLIST   DC  CL22'CALLED,PS HEADER=N'           *
    // *
    // R2 = ADDR OF A LIST OF 3 FULL WORD ADDRESSES.          *
    // ADDR 1 = ADDR OF JFCB AREA FOR TARGETED OUT FILE. *
    // ADDR 2 = ADDR OF DCB FOR THE OUT FILE.            *
    // ADDR 3 = ADDR OF RECORD TO DITTO TO THE OUT FILE. *
    // *
    // NOTE : IF MULTIPLE DITTOS ARE TO BE MADE FROM THE SAME      *
    // CALLING MODULE, THEN FOR EACH DITTO DCB THERE MUST   *
    // BE A UNIQUE LOAD OF THE PSDITTO ENTRY POINT ADDRESS. *
    // *
    // SELECT OPTION :  UNDER DD NAME SELECT, LIST ONE OR MORE     *
    // SEGMENT IDENTIFIERS (SRC,LCR,MDB ETC.). MAX OF 1 PER LINE.  *
    // *
    // LCRFLDS OPTION :  UNDER DD NAME LCRFLDS,                    *
    // HAVE THESE CARDS FIRST - '# OF FIELDS=XXX'  (NUMERIC VALUE) *
    // - 'OUTPUT FORM=XXXXX' (CHECK/AUDIT)  *
    // NEXT, LIST 1 PER LINE, EACH OF THE FIELD LENGTHS IN THE     *
    // ORDER CORRESPONDING TO THE LCR RECORD LAYOUT.               *
    // 2 DIGITS PER LENGTH. FIELDS OVER 99 MUST BE SPLIT.          *
    // *
    //--------------------------------------------------------------------*
    //---------------------------------------------------------------------*
    // *
    // REGISTER USAGE                                               *
    // *
    // R 0 - WORK REGISTER                                          *
    // R 1 - WORK REGISTER/ PARM COMMUNICATION WITH CALLER MODULE   *
    // R 2 - WORK REGISTER/ PARM COMMUNICATION WITH CALLER MODULE   *
    // R 3 - BAL  REGISTER                                          *
    // R 4 - BAL  REGISTER                                          *
    // R 5 - BAL  REGISTER                                          *
    // R 6 - WORK REGISTER                                          *
    // R 7 - RECORD LENGTH                                          *
    // R 8 - INPUT RECORD ADDRESS                                   *
    // R 9 - ADDR OF DISPLACEMENTS / BASE FOR SUB-PROGRAM           *
    // R10 - WORK REGISTER                                          *
    // R11 - WORK REGISTER                                          *
    // R12 - BASE REGISTER TWO                                      *
    // R13 - BASE REGISTER ONE                                      *
    // R14 - WORK REGISTER                                          *
    // R15 - WORK REGISTER                                          *
    // *
    // *
    //---------------------------------------------------------------------*
    //--------------------------------------------------------------------*
    // I N I T I A L   S E T U P   R O U T I N E S                 *
    //--------------------------------------------------------------------*
    // ASM <Code_block_id:INITIAL>
    // ASM:00085 INITIAL  EQU   *
    // ASM:00086          L     R1,0(R1)                ADDR OF PARMS (CALLED OR STAND)
    // ASM:00087          ST    R1,SAVER1               SAVE CALLERS PARMS
    // ASM:00088          ST    R2,SAVER2               SAVE CALLERS REGISTER
    // ASM:00089          SPACE
    // ASM:00090          CLC   19(3,R1),=C'NEW'        INITIALIZE OR RESET PSDITTO?
    // ASM:00091          BE    SETIT                   YES
    // ASM:00092          SPACE
    // ASM:00093          TM    MODE,CALLED             RE-ENTRY FROM CALLING MODULE?
    // ASM:00094          BO    GETREC                  YES
    eof: {
      // ADDR OF PARMS (CALLED OR STAND)
      getReg1().load(getReg1());
      // SAVE CALLERS PARMS
      storage.getSaver1().set(getReg1());
      // SAVE CALLERS REGISTER
      storage.getSaver2().set(getReg2());
      getrec: {
        setit: {
          // INITIALIZE OR RESET PSDITTO?
          // YES
          if (getReg1().getString(19, 3).equals("NEW")) {
            break setit;
          }
          // RE-ENTRY FROM CALLING MODULE?
          // YES
          if (storage.getMode().testUnderMask(PsdittoConstants.CALLED) == PsdittoConstants.CALLED) {
            break getrec;
          }
          // ASM <Code_block_id:SETIT>
          // ASM:00095          SPACE
          // ASM:00096 SETIT    EQU   *
          // ASM:00097          L     R15,=A(SETUP)           LOAD ADDRESS OF SETUP SUB MODULE
          // ASM:00098          BALR  R14,R15
        } //setit
        // LOAD ADDRESS OF SETUP SUB MODULE
        String breakLabel = setup();
        if (breakLabel != null && breakLabel.equals("eof")) {
          break eof;
        }
      } //getrec
      getrec: while (true) {
        checkret: {
          unable: {
            ckdisp2: {
              // ASM <Code_block_id:GETREC>
              // ASM:00099          EJECT
              // ASM:00100 *--------------------------------------------------------------------*
              // ASM:00101 *              M A I N   P R O C E S S I N G   L O O P               *
              // ASM:00102 *--------------------------------------------------------------------*
              // ASM:00103 GETREC   EQU   *
              // ASM:00104          TM    MODE,ALL                DITTO ALL RECORDS?
              // ASM:00105          BO    READ                    YES
              // ASM:00106          CP    WANT,PKZERO             DITTO LIMIT REACHED?
              // ASM:00107          BNH   EOF                     YES
              // ASM:00108          SP    WANT,PKONE              NO
              //--------------------------------------------------------------------*
              // M A I N   P R O C E S S I N G   L O O P               *
              //--------------------------------------------------------------------*
              read: {
                // DITTO ALL RECORDS?
                // YES
                if (storage.getMode().testUnderMask(PsdittoConstants.ALL) == PsdittoConstants.ALL) {
                  break read;
                }
                // DITTO LIMIT REACHED?
                if (storage.getWant().getNumeric() <= storage.getPkzero().getNumeric()) {
                  // YES
                  break eof;
                }
                // NO
                storage.getWant().set(storage.getWant().getNumeric() - storage.getPkone().getNumeric());
                // ASM <Code_block_id:READ>
                // ASM:00109          SPACE
                // ASM:00110 READ     EQU   *
                // ASM:00111          TM    MODE,CALLED             CALLED MODULE?
                // ASM:00112          BNO   READA                   NO, CONTINUE
                // ASM:00113          L     R2,SAVER2               YES, LOAD SAVE ADDRS
                // ASM:00114          L     R8,8(R2)                POINT TO INPUT RECORD
                // ASM:00115          B     READB                   CONTINUE
              } //read
              readb: {
                reada: {
                  // CALLED MODULE?
                  // NO, CONTINUE
                  if (storage.getMode().testUnderMask(PsdittoConstants.CALLED) != PsdittoConstants.CALLED) {
                    break reada;
                  }
                  // YES, LOAD SAVE ADDRS
                  getReg2().load(storage.getSaver2());
                  // POINT TO INPUT RECORD
                  getReg8().load(getReg2(), 8);
                  // CONTINUE
                  break readb;
                  // ASM <Code_block_id:READA>
                  // ASM:00116 READA    EQU   *
                  // ASM:00117          GET   INPUT                   READ RECORD
                  // ASM:00118          LR    R8,R1                   POINT R8 AT RECORD
                } //reada
                // READ RECORD
                input.next();
                if (input.isEOF()) {
                  break eof;
                }
                // POINT R8 AT RECORD
                getReg8().setRegister(getReg1());
                // ASM <Code_block_id:READB>
                // ASM:00119 READB    EQU   *
                // ASM:00120          AP    RECCT,PKONE             ADD 1 TO RECORD COUNT
                // ASM:00121          CLI   RECFM,C'V'              VARIABLE BLOCKED RECORD ?
                // ASM:00122          BE    VB                      YES GET LENGTH FROM RECORD
                // ASM:00123          LH    R7,LRECLSAV             LOAD RECORD LENGTH INTO R7
                // ASM:00124          B     RECDATA                 GO AROUND
              } //readb
              // ADD 1 TO RECORD COUNT
              storage.getRecct().set(storage.getRecct().getNumeric() + storage.getPkone().getNumeric());
              recdata: {
                vb: {
                  // VARIABLE BLOCKED RECORD ?
                  if (storage.getSubtit1().getRecfm().getString(0, 1).equals("V")) {
                    // YES GET LENGTH FROM RECORD
                    break vb;
                  }
                  // LOAD RECORD LENGTH INTO R7
                  getReg7().setByteString(_LOW(2) + storage.getLreclsav().getByteString(0, 2));
                  // GO AROUND
                  break recdata;
                  // ASM <Code_block_id:VB>
                  // ASM:00125 VB       EQU   *
                  // ASM:00126          LH    R7,0(R8)                GET RECORD LENGTH FROM RDW
                } //vb
                // GET RECORD LENGTH FROM RDW
                getReg7().setByteString(_LOW(2) + getReg8().getByteString(0, 2));
                // ASM <Code_block_id:RECDATA>
                // ASM:00127          SPACE 2
                // ASM:00128 RECDATA  EQU   *
                // ASM:00129          MVC   CHARDATA,=C'REC'        WRITE CONSTANT 'REC'
                // ASM:00130          MVC   WORK12(6),PTRN6
                // ASM:00131          ED    WORK12(6),RECCT         WRITE RECORD COUNT
                // ASM:00132          MVC   RECNUM(6),WORK12        MOVE TO CHARLINE
                // ASM:00133          SPACE
                // ASM:00134          CVD   R7,WRKDBL               WRITE CURRENT RECORD LENGTH
                // ASM:00135          ZAP   PACK4,WRKDBL+4(4)
                // ASM:00136          MVC   WORK12(8),PTRN8
                // ASM:00137          ED    WORK12(8),PACK4
                // ASM:00138          MVC   RECSIZE(8),WORK12       MOVE TO CHARLINE
                // ASM:00139          MVC   SEGNAME,BLANKS
              } //recdata
              // WRITE CONSTANT 'REC'
              storage.getCharline().getChardata().set("REC");
              storage.getWork12().setByteString(storage.getPtrn6().getByteString());
              // WRITE RECORD COUNT
              storage.getWork12().setEdited(storage.getRecct(), 0, 6);
              // MOVE TO CHARLINE
              storage.getCharline().getDummy897().getRecnum().setByteString(storage.getWork12().getByteString(0, 6));
              // WRITE CURRENT RECORD LENGTH
              storage.getWrkdbl().setAsPacked(getReg7());
              storage.getPack4().set(storage.getWrkdbl().getPackedAtRange(4, (4) + 4));
              storage.getWork12().setByteString(storage.getPtrn8().getByteString(0, 8));
              storage.getWork12().setEdited(storage.getPack4(), 0, 8);
              // MOVE TO CHARLINE
              storage.getCharline().getDummy897().getRecsize().setByteString(storage.getWork12().getByteString(0, 8));
              storage.getCharline().getDummy892().getSegname().setByteString(storage.getBlanks().getByteString(0, Dummy892.Len.SEGNAME));
              // ASM <Code_block_id:CKFORM>
              // ASM:00140          SPACE 2
              // ASM:00141 CKFORM   EQU   *
              // ASM:00142          TM    MODE,STANDARD           OSCR FORMAT?
              // ASM:00143          BNO   OSCR                    YES
              // ASM:00144          TM    MODE2,CHK_AUD           LCR CHECK/AUDIT OPTION?
              // ASM:00145          BZ    REGDITTO                NO
              // ASM:00146          L     R15,=A(LCRDITTO)
              // ASM:00147          BALR  R14,R15                 YES, DO CHECK/AUDIT LCR DITTO
              // ASM:00148          B     CHECKRET                GET NEXT RECORD
              oscr: {
                // OSCR FORMAT?
                if (storage.getMode().testUnderMask(PsdittoConstants.STANDARD) != PsdittoConstants.STANDARD) {
                  // YES
                  break oscr;
                }
                regditto: {
                  // LCR CHECK/AUDIT OPTION?
                  if (storage.getMode2().testUnderMask(PsdittoConstants.CHK_AUD) == ZERO) {
                    // NO
                    break regditto;
                  }
                  // YES, DO CHECK/AUDIT LCR DITTO
                  lcrditto();
                  break checkret;
                  // ASM <Code_block_id:REGDITTO>
                  // ASM:00149 REGDITTO EQU   *
                  // ASM:00150          BAL   R5,DITTO_RAMA           USE STANDARD DITTO FORMAT
                  // ASM:00151          B     CHECKRET                GET NEXT RECORD
                } //regditto
                // USE STANDARD DITTO FORMAT
                dittoRama();
                break checkret;
                // ASM <Code_block_id:OSCR>
                // ASM:00152          SPACE
                // ASM:00153 OSCR     EQU   *
                // ASM:00154          STH   R7,RECLEN               SAVE RECORD LENGTH
                // ASM:00155          ST    R8,RECADDR              SAVE RECORD ADDRESS
                // ASM:00156 *
                // ASM:00157 * DETERMINE IF 230 OR 280 HEADER
                // ASM:00158          TM    203(R8),X'10'
                // ASM:00159          BZ    DO230
                // ASM:00160          TM    MODE2,PRT_HDR           PRINT OSCR 230 HEADER?
                // ASM:00161          BZ    CKDISP2                 NO, SKIP HEADER
                // ASM:00162          LA    R7,280
                // ASM:00163          B     CONTDIT
              } //oscr
              // SAVE RECORD LENGTH
              storage.getReclen().setByteString(getReg7().getByteString(2, 2));
              // SAVE RECORD ADDRESS
              storage.getRecaddr().set(getReg8());
              ckdisp: {
                contdit: {
                  do230: {
                    //
                    // DETERMINE IF 230 OR 280 HEADER
                    if (getReg8().testUnderMaskAt(203, 0x10) == ZERO) {
                      break do230;
                    }
                    // PRINT OSCR 230 HEADER?
                    if (storage.getMode2().testUnderMask(PsdittoConstants.PRT_HDR) == ZERO) {
                      // NO, SKIP HEADER
                      break ckdisp2;
                    }
                    getReg7().set(280);
                    break contdit;
                    // ASM <Code_block_id:DO230>
                    // ASM:00164 DO230    EQU   *
                    // ASM:00165          TM    MODE2,PRT_HDR           PRINT OSCR 230 HEADER?
                    // ASM:00166          BZ    CKDISP                  NO, SKIP HEADER
                    // ASM:00167          LA    R7,230                  LENGTH OF HEADER
                  } //do230
                    // PRINT OSCR 230 HEADER?
                  if (storage.getMode2().testUnderMask(PsdittoConstants.PRT_HDR) == ZERO) {
                    // NO, SKIP HEADER
                    break ckdisp;
                  }
                  // LENGTH OF HEADER
                  getReg7().set(230);
                  // ASM <Code_block_id:CONTDIT>
                  // ASM:00168 CONTDIT  EQU   *
                  // ASM:00169 *
                  // ASM:00170          OI    MODE,STANDARD           DITTO HEADER IN STANDARD FORM
                  // ASM:00171          BAL   R5,DITTO_RAMA           DITTO OSCR HEADER
                  // ASM:00172          NI    MODE,OFF_80             TURN OFF STANDARD DITTO BIT
                  // ASM:00173          LH    R7,RECLEN               RESTORE RECORD LENGTH
                  // ASM:00174          L     R8,RECADDR              RESTORE RECORD ADDRESS
                  // ASM:00175          MVC   CHARDATA,BLANKS
                } //contdit
                //
                // DITTO HEADER IN STANDARD FORM
                storage.getMode().or(PsdittoConstants.STANDARD);
                // DITTO OSCR HEADER
                dittoRama();
                // TURN OFF STANDARD DITTO BIT
                storage.getMode().and(PsdittoConstants.OFF_80);
                // RESTORE RECORD LENGTH
                getReg7().setByteString(_LOW(2) + storage.getReclen().getByteString(0, 2));
                // RESTORE RECORD ADDRESS
                getReg8().load(storage.getRecaddr());
                storage.getCharline().getChardata().setByteString(storage.getBlanks().getByteString(0, Charline.Len.CHARDATA));
                // ASM <Code_block_id:CKDISP>
                // ASM:00176          SPACE
                // ASM:00177 CKDISP   EQU   *
                // ASM:00178          TM    203(R8),X'10'
                // ASM:00179          BNZ   CKDISP2
                // ASM:00180 *
                // ASM:00181          ZAP   SEGLOCST,=P'231'        INITIALIZE SEG LOCATION STORAGE
                // ASM:00182          LA    R9,180(R8)              ADDR OF DISP TO SRC SEGMENT
                // ASM:00183          LA    R6,7                    POSSIBLE # OF APPENDED SEGMENTS
              } //ckdisp
              if (getReg8().testUnderMaskAt(203, 0x10) != ZERO) {
                break ckdisp2;
              }
              //
              // INITIALIZE SEG LOCATION STORAGE
              storage.getSeglocst().set(231);
              // ADDR OF DISP TO SRC SEGMENT
              getReg9().setAddress(getReg8(), 180);
              // POSSIBLE # OF APPENDED SEGMENTS
              getReg6().set(7);
              // ASM <Code_block_id:DISPLOOP>
              // ASM:00184 DISPLOOP EQU   *
              // ASM:00185          LH    R7,RECLEN
              // ASM:00186          L     R8,RECADDR
              // ASM:00187          CLC   0(2,R9),HEXZEROS        NO SUCH APPENDED SEGMENT
              // ASM:00188          BE    BUMPDISP
              // ASM:00189          BAL   R5,VERIFY               VERIFY RECORD IS IN OSCR FORMAT
              // ASM:00190          LTR   R1,R1
              // ASM:00191          BNZ   UNABLE                  ERROR IN RECORD FORMAT
              // ASM:00192          TM    MODE2,NON_HDR           EITHER DEFAULT OR SELECT NONHDR?
              // ASM:00193          BZ    BUMPDISP                NO, SKIP NON-HDR SEGMENTS
              // ASM:00194          TM    MODE2,SEL_ON            SELECT ON FOR THIS SEGMENT?
              // ASM:00195          BM    BUMPDISP                NO, SKIP NON-HDR SEGMENT
              // ASM:00196          TM    MODE2,LCRSEG            LCR SEGMENT?
              // ASM:00197          BNO   OSCRDITO                NO
              // ASM:00198          TM    MODE2,CHK_AUD           LCR CHECK/AUDIT OPTION?
              // ASM:00199          BZ    OSCRDITO                NO
              // ASM:00200          ST    R9,SAVER9               SAVE ADDR OF SEG DISPLACEMENT
              // ASM:00201          L     R15,=A(LCRDITTO)
              // ASM:00202          BALR  R14,R15                 YES, DO CHECK/AUDIT LCR DITTO
              // ASM:00203          NI    MODE2,OFF_80            TURN LCRSEG BIT OFF
              // ASM:00204          B     BUMPDISP
              disploop: while (true) {
                getReg7().setByteString(_LOW(2) + storage.getReclen().getByteString(0, 2));
                getReg8().load(storage.getRecaddr());
                bumpdisp: {
                  // NO SUCH APPENDED SEGMENT
                  if (getReg9().getByteString(0, 2).equals(storage.getHexzeros().getByteString(0, 2))) {
                    break bumpdisp;
                  }
                  // VERIFY RECORD IS IN OSCR FORMAT
                  verify();
                  // ERROR IN RECORD FORMAT
                  if (!getReg1().isEmpty()) {
                    break unable;
                  }
                  // EITHER DEFAULT OR SELECT NONHDR?
                  if (storage.getMode2().testUnderMask(PsdittoConstants.NON_HDR) == ZERO) {
                    // NO, SKIP NON-HDR SEGMENTS
                    break bumpdisp;
                  }
                  // SELECT ON FOR THIS SEGMENT?
                  if (storage.getMode2().testUnderMask(PsdittoConstants.SEL_ON) != 0 && storage.getMode2().testUnderMask(PsdittoConstants.SEL_ON) != PsdittoConstants.SEL_ON) {
                    // NO, SKIP NON-HDR SEGMENT
                    break bumpdisp;
                  }
                  oscrdito: {
                    // LCR SEGMENT?
                    // NO
                    if (storage.getMode2().testUnderMask(PsdittoConstants.LCRSEG) != PsdittoConstants.LCRSEG) {
                      break oscrdito;
                    }
                    // LCR CHECK/AUDIT OPTION?
                    if (storage.getMode2().testUnderMask(PsdittoConstants.CHK_AUD) == ZERO) {
                      // NO
                      break oscrdito;
                    }
                    // SAVE ADDR OF SEG DISPLACEMENT
                    storage.getSaver9().set(getReg9());
                    // YES, DO CHECK/AUDIT LCR DITTO
                    lcrditto();
                    // TURN LCRSEG BIT OFF
                    storage.getMode2().and(PsdittoConstants.OFF_80);
                    break bumpdisp;
                    // ASM <Code_block_id:OSCRDITO>
                    // ASM:00205 OSCRDITO EQU   *
                    // ASM:00206          BAL   R5,DITTO_RAMA           PRODUCE OSCR DITTO
                  } //oscrdito
                  // PRODUCE OSCR DITTO
                  dittoRama();
                  // ASM <Code_block_id:BUMPDISP>
                  // ASM:00207 BUMPDISP EQU   *
                  // ASM:00208          NI    MODE2,OFF_01            TURN SEGSEL BIT OFF
                  // ASM:00209          LA    R9,2(R9)                BUMP TO NEXT SEG DISPLACEMENT
                  // ASM:00210          BCT   R6,DISPLOOP             DITTO NEXT SEGMENT
                  // ASM:00211          B     CHECKRET                GET NEXT RECORD
                } //bumpdisp
                // TURN SEGSEL BIT OFF
                storage.getMode2().and(PsdittoConstants.OFF_01);
                // BUMP TO NEXT SEG DISPLACEMENT
                getReg9().increment(2);
                getReg6().decrement(1);
                if (getReg6().getNumeric() > 0) {
                  continue disploop;
                }
                break disploop;
              } //disploop
              break checkret;
              // ASM <Code_block_id:CKDISP2>
              // ASM:00212          SPACE 3
              // ASM:00213 *********************************************************
              // ASM:00214 * POM 280 WITH NEW SEGS  (BEGIN)
              // ASM:00215 *********************************************************
              // ASM:00216 CKDISP2  EQU   *
              // ASM:00217          ZAP   SEGLOCST,=P'281'        INITIALIZE SEG LOCATION STORAGE
              // ASM:00218          LA    R2,DESCTBL2
              // ASM:00219          ST    R2,DESCTBL2_PTR
              // ASM:00220          L     R3,4(R2)
              // ASM:00221          LA    R9,0(R3,R8)
              // ASM:00222          LA    R6,DESCTBL2_#           POSSIBLE # OF APPENDED SEGMENTS
              //*******************************************************
              // POM 280 WITH NEW SEGS  (BEGIN)
              //*******************************************************
            } //ckdisp2
            // INITIALIZE SEG LOCATION STORAGE
            storage.getSeglocst().set(281);
            getReg2().setAddress(storage.getDesctbl2());
            storage.getDesctbl2Ptr().set(getReg2());
            getReg3().load(getReg2(), 4);
            getReg9().setAddress(getReg8(), getReg3().getInt());
            // POSSIBLE # OF APPENDED SEGMENTS
            getReg6().set(PsdittoConstants.DESCTBL2_X);
            // ASM <Code_block_id:DISPLOOP2>
            // ASM:00223 DISPLOOP2 EQU   *
            // ASM:00224          LH    R7,RECLEN
            // ASM:00225          L     R8,RECADDR
            // ASM:00226          CLC   0(2,R9),HEXZEROS        NO SUCH APPENDED SEGMENT
            // ASM:00227          BE    BUMPDISP2
            // ASM:00228          BAL   R5,VERIFY2              VERIFY RECORD IS IN OSCR FORMAT
            // ASM:00229          LTR   R1,R1
            // ASM:00230          BNZ   UNABLE                  ERROR IN RECORD FORMAT
            // ASM:00231          TM    MODE2,NON_HDR           EITHER DEFAULT OR SELECT NONHDR?
            // ASM:00232          BZ    BUMPDISP2               NO, SKIP NON-HDR SEGMENTS
            // ASM:00233          TM    MODE2,SEL_ON            SELECT ON FOR THIS SEGMENT?
            // ASM:00234          BM    BUMPDISP2               NO, SKIP NON-HDR SEGMENT
            // ASM:00235          TM    MODE2,LCRSEG            LCR SEGMENT?
            // ASM:00236          BNO   OSCRDITO2               NO
            // ASM:00237          TM    MODE2,CHK_AUD           LCR CHECK/AUDIT OPTION?
            // ASM:00238          BZ    OSCRDITO2               NO
            // ASM:00239          ST    R9,SAVER9               SAVE ADDR OF SEG DISPLACEMENT
            // ASM:00240          L     R15,=A(LCRDITTO)
            // ASM:00241          BALR  R14,R15                 YES, DO CHECK/AUDIT LCR DITTO
            // ASM:00242          NI    MODE2,OFF_80            TURN LCRSEG BIT OFF
            // ASM:00243          B     BUMPDISP2
            disploop2: while (true) {
              getReg7().setByteString(_LOW(2) + storage.getReclen().getByteString(0, 2));
              getReg8().load(storage.getRecaddr());
              bumpdisp2: {
                // NO SUCH APPENDED SEGMENT
                if (getReg9().getByteString(0, 2).equals(storage.getHexzeros().getByteString(0, 2))) {
                  break bumpdisp2;
                }
                // VERIFY RECORD IS IN OSCR FORMAT
                verify2();
                // ERROR IN RECORD FORMAT
                if (!getReg1().isEmpty()) {
                  break unable;
                }
                // EITHER DEFAULT OR SELECT NONHDR?
                if (storage.getMode2().testUnderMask(PsdittoConstants.NON_HDR) == ZERO) {
                  // NO, SKIP NON-HDR SEGMENTS
                  break bumpdisp2;
                }
                // SELECT ON FOR THIS SEGMENT?
                if (storage.getMode2().testUnderMask(PsdittoConstants.SEL_ON) != 0 && storage.getMode2().testUnderMask(PsdittoConstants.SEL_ON) != PsdittoConstants.SEL_ON) {
                  // NO, SKIP NON-HDR SEGMENT
                  break bumpdisp2;
                }
                oscrdito2: {
                  // LCR SEGMENT?
                  // NO
                  if (storage.getMode2().testUnderMask(PsdittoConstants.LCRSEG) != PsdittoConstants.LCRSEG) {
                    break oscrdito2;
                  }
                  // LCR CHECK/AUDIT OPTION?
                  if (storage.getMode2().testUnderMask(PsdittoConstants.CHK_AUD) == ZERO) {
                    // NO
                    break oscrdito2;
                  }
                  // SAVE ADDR OF SEG DISPLACEMENT
                  storage.getSaver9().set(getReg9());
                  // YES, DO CHECK/AUDIT LCR DITTO
                  lcrditto();
                  // TURN LCRSEG BIT OFF
                  storage.getMode2().and(PsdittoConstants.OFF_80);
                  break bumpdisp2;
                  // ASM <Code_block_id:OSCRDITO2>
                  // ASM:00244 OSCRDITO2 EQU   *
                  // ASM:00245          BAL   R5,DITTO_RAMA           PRODUCE OSCR DITTO
                } //oscrdito2
                // PRODUCE OSCR DITTO
                dittoRama();
                // ASM <Code_block_id:BUMPDISP2>
                // ASM:00246 BUMPDISP2 EQU   *
                // ASM:00247          NI    MODE2,OFF_01            TURN SEGSEL BIT OFF
                // ASM:00248          L     R2,DESCTBL2_PTR
                // ASM:00249          LA    R2,L'DESCTBL2(R2)       BUMP TO NEXT SEG DISPLACEMENT
                // ASM:00250          ST    R2,DESCTBL2_PTR
                // ASM:00251          L     R3,4(R2)
                // ASM:00252          L     R8,RECADDR
                // ASM:00253          LA    R9,0(R3,R8)
                // ASM:00254          BCT   R6,DISPLOOP2            DITTO NEXT SEGMENT
                // ASM:00255          B     CHECKRET                GET NEXT RECORD
              } //bumpdisp2
              // TURN SEGSEL BIT OFF
              storage.getMode2().and(PsdittoConstants.OFF_01);
              getReg2().load(storage.getDesctbl2Ptr());
              // BUMP TO NEXT SEG DISPLACEMENT
              getReg2().increment(Desctbl2.Len.DESCTBL2_SLOT);
              storage.getDesctbl2Ptr().set(getReg2());
              getReg3().load(getReg2(), 4);
              getReg8().load(storage.getRecaddr());
              getReg9().setAddress(getReg8(), getReg3().getInt());
              //DITTO NEXT SEGMENT
              getReg6().decrement(1);
              if (getReg6().getNumeric() > 0) {
                continue disploop2;
              }
              break disploop2;
            } //disploop2
            break checkret;
            // ASM <Code_block_id:UNABLE>
            // ASM:00256 *********************************************************
            // ASM:00257 * POM 280 WITH NEW SEGS  (END)
            // ASM:00258 *********************************************************
            // ASM:00259          SPACE 3
            // ASM:00260 UNABLE   NOP   TRYAGAIN
            // ASM:00261          MVI   UNABLE+1,X'F0'
            // ASM:00262          BAL   R5,OSCRFAIL             PRINT WTO MESSAGE
            //*******************************************************
            // POM 280 WITH NEW SEGS  (END)
            //*******************************************************
          } //unable
          tryagain: {
            if (unable) {
              break tryagain;
            }
            unable = true;
            // PRINT WTO MESSAGE
            oscrfail();
            // ASM <Code_block_id:TRYAGAIN>
            // ASM:00263 TRYAGAIN EQU   *
            // ASM:00264          MVC   CHARDATA(20),=C'REC ERR/NORMAL DITTO'
            // ASM:00265          OI    MODE,STANDARD           DITTO HEADER IN STANDARD FORM
            // ASM:00266          BAL   R5,DITTO_RAMA           ERROR! PRINT STANDARD DITTO
            // ASM:00267          NI    MODE,OFF_80             TURN OFF STANDARD DITTO BIT
            // ASM:00268          B     CHECKRET                GET NEXT RECORD
          } //tryagain
          storage.getCharline().set("REC ERR/NORMAL DITTO", Charline.Pos.CHARDATA);
          // DITTO HEADER IN STANDARD FORM
          storage.getMode().or(PsdittoConstants.STANDARD);
          // ERROR! PRINT STANDARD DITTO
          dittoRama();
          // TURN OFF STANDARD DITTO BIT
          storage.getMode().and(PsdittoConstants.OFF_80);
          // ASM <Code_block_id:CHECKRET>
          // ASM:00273          SPACE
          // ASM:00274 CHECKRET EQU   *
          // ASM:00275          TM    MODE,CALLED             CALLED MODULE?
          // ASM:00276          BNO   GETREC                  NO
          // ASM:00277          PSRETURN RC=0
          // CALLED MODULE?
        } //checkret
        if (storage.getMode().testUnderMask(PsdittoConstants.CALLED) != PsdittoConstants.CALLED) {
          continue getrec;
        }
        restoreRegistersPosition(REG_0, REG_12, getReg13(), 12);
        throw new NAsmExitProgramException(0);
      } //getrec
        // ASM <Code_block_id:EOF>
        // ASM:00269          SPACE 3
        // ASM:00270 EOF      EQU   *
        // ASM:00271          CLOSE (INPUT,,OUTPUT)         CLOSE THE FILES
        // ASM:00272          PSRETURN RC=0                 RETURN
    } //eof
    // CLOSE THE FILES
    input.close();
    output.close();
    restoreRegistersPosition(REG_0, REG_12, getReg13(), 12);
    throw new NAsmExitProgramException(0);
  }

  /**
   * Method VERIFY.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void verify() throws NAsmException {
    // ASM <Code_block_id:VERIFY>
    // ASM:00278          EJECT
    // ASM:00279 *--------------------------------------------------------------------*
    // ASM:00280 *                  V E R I F Y   R O U T I N E                       *
    // ASM:00281 *--------------------------------------------------------------------*
    // ASM:00282 VERIFY   EQU   *
    // ASM:00283          LH    R1,0(R9)             GET DISPLACEMENT
    // ASM:00284          A     R1,=F'5'             ALLOW FOR SEGMENT DESCRIPTOR,LENGTH
    // ASM:00285          ICM   R1,B'1100',=X'0000'  IF NOT OSCR, AVOID SOC4
    // ASM:00286          CR    R7,R1                COMP RECLEN TO LOC OF APPENDED REC
    // ASM:00287          BL    NOWAY                ERROR
    // ASM:00288          SPACE
    // ASM:00289          LR    R1,R6                APPENDED SEGMENT NUMBER (1-7)
    // ASM:00290          BCTR  R1,0
    // ASM:00291          MH    R1,=H'4'             GET OFFSET
    // ASM:00292          LA    R15,DESCTBL(R1)      INDEX INTO TABLE
    // ASM:00293          LH    R1,0(R9)             GET DISPLACEMENT
    // ASM:00294          AR    R1,R8                ADDR OF APPENDED SEGMENT
    // ASM:00295          CLC   0(3,R1),0(R15)       MATCH ON SEGMENT DESCRIPTOR?
    // ASM:00296          BNE   NOWAY                NO
    // ASM:00297          MVC   SEGNAME,0(R15)
    // ASM:00298          CLC   SEGNAME,=C'LCR'      LCR SEGMENT?
    // ASM:00299          BNE   CONTVER              NO
    // ASM:00300          OI    MODE2,LCRSEG          SET LCR SEGMENT BIT ON
    //--------------------------------------------------------------------*
    // V E R I F Y   R O U T I N E                       *
    //--------------------------------------------------------------------*
    // GET DISPLACEMENT
    getReg1().setByteString(_LOW(2) + getReg9().getByteString(0, 2));
    // ALLOW FOR SEGMENT DESCRIPTOR,LENGTH
    getReg1().addAddress(5);
    // IF NOT OSCR, AVOID SOC4
    getReg1().insertCharUnderMask(0x0C, _LOW(2));
    noway: {
      // COMP RECLEN TO LOC OF APPENDED REC
      if (getReg7().less(getReg1())) {
        // ERROR
        break noway;
      }
      // APPENDED SEGMENT NUMBER (1-7)
      getReg1().setRegister(getReg6());
      getReg1().decrement(1);
      // GET OFFSET
      getReg1().set(getReg1().getNumeric() * 4);
      // INDEX INTO TABLE
      getReg15().setAddress(storage.getDesctbl(), getReg1().getInt());
      // GET DISPLACEMENT
      getReg1().setByteString(_LOW(2) + getReg9().getByteString(0, 2));
      // ADDR OF APPENDED SEGMENT
      getReg1().increment(getReg8());
      // MATCH ON SEGMENT DESCRIPTOR?
      if (!getReg1().getByteString(0, 3).equals(getReg15().getByteString(0, 3))) {
        // NO
        break noway;
      }
      storage.getCharline().getDummy892().getSegname().setByteString(getReg15().getByteString(0, Dummy892.Len.SEGNAME));
      // LCR SEGMENT?
      contver: {
        if (!storage.getCharline().getDummy892().getSegname().equals("LCR")) {
          // NO
          break contver;
        }
        // SET LCR SEGMENT BIT ON
        storage.getMode2().or(PsdittoConstants.LCRSEG);
        // ASM <Code_block_id:CONTVER>
        // ASM:00301 CONTVER  EQU   *
        // ASM:00302          CLI   3(R15),C'Y'          SEGMENT SELECTED?
        // ASM:00303          BNE   GETSIZE              NO
        // ASM:00304          OI    MODE2,SEGSEL          SET SEGMENT SELECTED BIT ON
      } //contver
      getsize: {
        // SEGMENT SELECTED?
        if (!getReg15().getString(3, 1).equals("Y")) {
          // NO
          break getsize;
        }
        // SET SEGMENT SELECTED BIT ON
        storage.getMode2().or(PsdittoConstants.SEGSEL);
        // ASM <Code_block_id:GETSIZE>
        // ASM:00305          SPACE
        // ASM:00306 GETSIZE  EQU   *
        // ASM:00307          LH    R2,3(R1)                SEGMENT LENGTH
        // ASM:00308          S     R2,=F'5'                SUBTRACT 5 BYTE DESC/LEN
        // ASM:00309          CVD   R2,WRKDBL               CONVERT LEN TO PACK
        // ASM:00310          ZAP   PACK4,WRKDBL+4(4)
        // ASM:00311          MVC   WORK12(8),PTRN8         ED PATTERN
        // ASM:00312          ED    WORK12(8),PACK4         SEGMENT SIZE
        // ASM:00313          MVC   SEGSIZE(8),WORK12       MOVE TO CHARLINE
      } //getsize
      // SEGMENT LENGTH
      getReg2().setByteString(_LOW(2) + getReg1().getByteString(3, 2));
      // SUBTRACT 5 BYTE DESC/LEN
      getReg2().subtractAddress(5);
      // CONVERT LEN TO PACK
      storage.getWrkdbl().setAsPacked(getReg2());
      storage.getPack4().set(storage.getWrkdbl().getPackedAtRange(4, (4) + 4));
      // ED PATTERN
      storage.getWork12().setByteString(storage.getPtrn8().getByteString(0, 8));
      // SEGMENT SIZE
      storage.getWork12().setEdited(storage.getPack4(), 0, 8);
      // MOVE TO CHARLINE
      storage.getCharline().getDummy892().getSegsize().setByteString(storage.getWork12().getByteString(0, 8));
      // ASM <Code_block_id:WAY>
      // ASM:00314          SPACE
      // ASM:00315 WAY      EQU   *
      // ASM:00316          XR    R1,R1
      // ASM:00317          BR    R5
      getReg1().set(0);
      return;
      // ASM <Code_block_id:NOWAY>
      // ASM:00318          SPACE
      // ASM:00319 NOWAY    EQU   *
      // ASM:00320          LA    R1,4
      // ASM:00321          BR    R5
    } //noway
    getReg1().set(4);
    return;
  }

  /**
   * Method VERIFY2.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void verify2() throws NAsmException {
    // ASM <Code_block_id:VERIFY2>
    // ASM:00322          EJECT
    // ASM:00323 *--------------------------------------------------------------------*
    // ASM:00324 *                  V E R I F Y   R O U T I N E                       *
    // ASM:00325 *--------------------------------------------------------------------*
    // ASM:00326 VERIFY2  EQU   *
    // ASM:00327          LH    R1,0(R9)             GET DISPLACEMENT
    // ASM:00328          A     R1,=F'5'             ALLOW FOR SEGMENT DESCRIPTOR,LENGTH
    // ASM:00329          ICM   R1,B'1100',=X'0000'  IF NOT OSCR, AVOID SOC4
    // ASM:00330          CR    R7,R1                COMP RECLEN TO LOC OF APPENDED REC
    // ASM:00331          BL    NOWAY2               ERROR
    // ASM:00332          SPACE
    // ASM:00333          L     R15,DESCTBL2_PTR     APPENDED SEGMENT ENTRY
    // ASM:00334          LH    R1,0(R9)             GET DISPLACEMENT
    // ASM:00335          AR    R1,R8                ADDR OF APPENDED SEGMENT
    // ASM:00336          CLC   0(3,R1),0(R15)       MATCH ON SEGMENT DESCRIPTOR?
    // ASM:00337          BNE   NOWAY2               NO
    // ASM:00338          MVC   SEGNAME,0(R15)
    // ASM:00339          CLC   SEGNAME,=C'LCR'      LCR SEGMENT?
    // ASM:00340          BNE   CONTVER2             NO
    // ASM:00341          OI    MODE2,LCRSEG          SET LCR SEGMENT BIT ON
    //--------------------------------------------------------------------*
    // V E R I F Y   R O U T I N E                                        *
    //--------------------------------------------------------------------*
    // GET DISPLACEMENT
    getReg1().setByteString(_LOW(2) + getReg9().getByteString(0, 2));
    // ALLOW FOR SEGMENT DESCRIPTOR,LENGTH
    getReg1().addAddress(5);
    // IF NOT OSCR, AVOID SOC4
    getReg1().insertCharUnderMask(0x0C, _LOW(2));
    noway2: {
      // COMP RECLEN TO LOC OF APPENDED REC
      if (getReg7().less(getReg1())) {
        // ERROR
        break noway2;
      }
      // APPENDED SEGMENT ENTRY
      getReg15().load(storage.getDesctbl2Ptr());
      // GET DISPLACEMENT
      getReg1().setByteString(_LOW(2) + getReg9().getByteString(0, 2));
      // ADDR OF APPENDED SEGMENT
      getReg1().increment(getReg8());
      // MATCH ON SEGMENT DESCRIPTOR?
      if (!getReg1().getByteString(0, 3).equals(getReg15().getByteString(0, 3))) {
        // NO
        break noway2;
      }
      storage.getCharline().getDummy892().getSegname().setByteString(getReg15().getByteString(0, Dummy892.Len.SEGNAME));
      contver2: {
        // LCR SEGMENT?
        if (!storage.getCharline().getDummy892().getSegname().equals("LCR")) {
          // NO
          break contver2;
        }
        // SET LCR SEGMENT BIT ON
        storage.getMode2().or(PsdittoConstants.LCRSEG);
        // ASM <Code_block_id:CONTVER2>
        // ASM:00342 CONTVER2 EQU   *
        // ASM:00343          CLI   3(R15),C'Y'          SEGMENT SELECTED?
        // ASM:00344          BNE   GETSIZE2             NO
        // ASM:00345          OI    MODE2,SEGSEL          SET SEGMENT SELECTED BIT ON
      } //contver2
      getsize2: {
        // SEGMENT SELECTED?
        if (!getReg15().getString(3, 1).equals("Y")) {
          // NO
          break getsize2;
        }
        // SET SEGMENT SELECTED BIT ON
        storage.getMode2().or(PsdittoConstants.SEGSEL);
        // ASM <Code_block_id:GETSIZE2>
        // ASM:00346          SPACE
        // ASM:00347 GETSIZE2 EQU   *
        // ASM:00348          LH    R2,3(R1)                SEGMENT LENGTH
        // ASM:00349          S     R2,=F'5'                SUBTRACT 5 BYTE DESC/LEN
        // ASM:00350          CVD   R2,WRKDBL               CONVERT LEN TO PACK
        // ASM:00351          ZAP   PACK4,WRKDBL+4(4)
        // ASM:00352          MVC   WORK12(8),PTRN8         ED PATTERN
        // ASM:00353          ED    WORK12(8),PACK4         SEGMENT SIZE
        // ASM:00354          MVC   SEGSIZE(8),WORK12       MOVE TO CHARLINE
      } //getsize2
      // SEGMENT LENGTH
      getReg2().setByteString(_LOW(2) + getReg1().getByteString(3, 2));
      // SUBTRACT 5 BYTE DESC/LEN
      getReg2().subtractAddress(5);
      // CONVERT LEN TO PACK
      storage.getWrkdbl().setAsPacked(getReg2());
      storage.getPack4().set(storage.getWrkdbl().getPackedAtRange(4, (4) + 4));
      // ED PATTERN
      storage.getWork12().setByteString(storage.getPtrn8().getByteString(0, 8));
      // SEGMENT SIZE
      storage.getWork12().setEdited(storage.getPack4(), 0, 8);
      // MOVE TO CHARLINE
      storage.getCharline().getDummy892().getSegsize().setByteString(storage.getWork12().getByteString(0, 8));
      // ASM <Code_block_id:WAY2>
      // ASM:00355          SPACE
      // ASM:00356 WAY2     EQU   *
      // ASM:00357          XR    R1,R1
      // ASM:00358          BR    R5
      getReg1().set(0);
      return;
      // ASM <Code_block_id:NOWAY2>
      // ASM:00359          SPACE
      // ASM:00360 NOWAY2   EQU   *
      // ASM:00361          LA    R1,4
      // ASM:00362          BR    R5
    } //noway2
    getReg1().set(4);
    return;
  }

  /**
   * Method DITTO_RAMA.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void dittoRama() throws NAsmException {
    // ASM <Code_block_id:DITTO_RAMA>
    // ASM:00363          EJECT
    // ASM:00364 *--------------------------------------------------------------------*
    // ASM:00365 *                  D I T T O    R O U T I N E                        *
    // ASM:00366 *--------------------------------------------------------------------*
    // ASM:00367 DITTO_RAMA     EQU  *
    // ASM:00368          SPACE
    // ASM:00369          TM    MODE,STANDARD           OSCR DITTO?
    // ASM:00370          BO    GO_DITTO                NO
    // ASM:00371          TM    MODE2,SEL_OTH           SELECT NON-HDR ON?
    // ASM:00372          BO    PASTLOC                 YES, DON'T WRITE SEG DISPLACMENT
    // ASM:00373          AP    SEGLOCST,=P'4'          LOC+4 = DISPLACEMNT FOR THIS SEG
    // ASM:00374          MVC   WORK12(8),PTRN8         ED PATTERN
    // ASM:00375          ED    WORK12(8),SEGLOCST
    // ASM:00376          MVC   SEGLOC(8),WORK12        MOVE DISPLACEMENT TO GAUGE LINE
    // ASM:00377          AP    SEGLOCST,=P'1'          ADD 1 TO GET TRUE LOCATION
    //--------------------------------------------------------------------*
    // D I T T O    R O U T I N E                        *
    //--------------------------------------------------------------------*
    goDitto: {
      // OSCR DITTO?
      // NO
      if (storage.getMode().testUnderMask(PsdittoConstants.STANDARD) == PsdittoConstants.STANDARD) {
        break goDitto;
      }
      pastloc: {
        // SELECT NON-HDR ON?
        // YES, DON'T WRITE SEG DISPLACMENT
        if (storage.getMode2().testUnderMask(PsdittoConstants.SEL_OTH) == PsdittoConstants.SEL_OTH) {
          break pastloc;
        }
        // LOC+4 = DISPLACEMNT FOR THIS SEG
        storage.getSeglocst().set(storage.getSeglocst().getNumeric() + 4);
        // ED PATTERN
        storage.getWork12().setByteString(storage.getPtrn8().getByteString(0, 8));
        storage.getWork12().setEdited(storage.getSeglocst(), 0, 8);
        // MOVE DISPLACEMENT TO GAUGE LINE
        storage.getGuide().getSegloc().setByteString(storage.getWork12().getByteString(0, 8));
        // ADD 1 TO GET TRUE LOCATION
        storage.getSeglocst().set(storage.getSeglocst().getNumeric() + 1);
        // ASM <Code_block_id:PASTLOC>
        // ASM:00378          SPACE 2
        // ASM:00379 PASTLOC  EQU   *
        // ASM:00380          LH    R1,0(R9)                GET SEGMENT DISPLACEMENT
        // ASM:00381          AR    R1,R8                   ADD RECORD ADDRESS
        // ASM:00382          LR    R8,R1                   ADDR OF SEGMENT
        // ASM:00383          LH    R7,3(R8)                SEGMENT LENGTH
        // ASM:00384          S     R7,=F'5'                SKIP 5 BYTE DESC/LEN
        // ASM:00385 *
        // ASM:00386          CLC   0(3,R8),=C'P/S'
        // ASM:00387          BNE   GOONDIT
        // ASM:00388          ST    R6,SAVER6
        // ASM:00389          BAL   R6,PSPSRTNE
        // ASM:00390          L     R6,SAVER6
      } //pastloc
        // GET SEGMENT DISPLACEMENT
      getReg1().setByteString(_LOW(2) + getReg9().getByteString(0, 2));
      // ADD RECORD ADDRESS
      getReg1().increment(getReg8());
      // ADDR OF SEGMENT
      getReg8().setRegister(getReg1());
      // SEGMENT LENGTH
      getReg7().setByteString(_LOW(2) + getReg8().getByteString(3, 2));
      // SKIP 5 BYTE DESC/LEN
      getReg7().subtractAddress(5);
      //
      goondit: {
        if (!getReg8().getString(0, 3).equals("P/S")) {
          break goondit;
        }
        storage.getSaver6().set(getReg6());
        pspsrtne();
        getReg6().load(storage.getSaver6());
        // ASM <Code_block_id:GOONDIT>
        // ASM:00391 GOONDIT  EQU   *
        // ASM:00392 *
        // ASM:00393          A     R8,=F'5'                SKIP 5 BYTE DESC/LEN
        // ASM:00394          SPACE 2
        // ASM:00395          CVD   R7,WRKDBL               CONVERT LEN TO PACK
        // ASM:00396          AP    SEGLOCST,WRKDBL+4(4)    ADD SEG LEN TO SEG LOC STORAGE
      } //goondit
      //
      // SKIP 5 BYTE DESC/LEN
      getReg8().addAddress(5);
      // CONVERT LEN TO PACK
      storage.getWrkdbl().setAsPacked(getReg7());
      // ADD SEG LEN TO SEG LOC STORAGE
      storage.getSeglocst().set(storage.getSeglocst().getNumeric() + storage.getWrkdbl().getPackedAtRange(4, (4) + 4));
      // ASM <Code_block_id:GO_DITTO>
      // ASM:00397          SPACE
      // ASM:00398 GO_DITTO EQU   *
      // ASM:00399          ZAP   GUIDECTR(2),PKZERO      RESET GUIDE COUNTER
      // ASM:00400          MVI   CCON,C'-'               SKIP 2 LINES FOR NEW RECORD
      // ASM:00401          AP    LINECT,PKONE            COUNT LINE FROM PRINT CONTROL
    } //goDitto
    // RESET GUIDE COUNTER
    storage.getGuidectr().set(storage.getPkzero().getNumeric());
    // SKIP 2 LINES FOR NEW RECORD
    storage.getCharline().getCcon().set("-");
    // COUNT LINE FROM PRINT CONTROL
    storage.getLinect().set(storage.getLinect().getNumeric() + storage.getPkone().getNumeric());
    // ASM <Code_block_id:LOOP>
    // ASM:00402          SPACE
    // ASM:00403 LOOP     EQU   *
    // ASM:00404          C     R7,=F'100'              HAVE AT LEAST 100 BYTES ?
    // ASM:00405          BL    LAST                    NO  - MODIFY THE MOVES
    // ASM:00406          CP    LINECT,=P'55'           ROOM FOR POSSIBLE 5 LINES?
    // ASM:00407          BL    BIG                     YES - GO MOVE IT
    // ASM:00408          BAL   R4,HEADERS              NO  - START NEW PAGE
    last: {
      loop: while (true) {
        // HAVE AT LEAST 100 BYTES ?
        // NO - MODIFY THE MOVES
        if (getReg7().less(100)) {
          break last;
        }
        big: {
          // ROOM FOR POSSIBLE 5 LINES?
          if (storage.getLinect().getNumeric() < 55) {
            // YES - GO MOVE IT
            break big;
          }
          // NO - START NEW PAGE
          headers();
          // ASM <Code_block_id:BIG>
          // ASM:00409 BIG      EQU   *
          // ASM:00410          BAL   R11,FULLINE             PRINT 100 CHARACTERS
          // ASM:00411          B     LOOP
        } //big
        // PRINT 100 CHARACTERS
        fulline();
        continue loop;
        // ASM <Code_block_id:LAST>
        // ASM:00412          SPACE
        // ASM:00413 LAST     EQU   *
        // ASM:00414          CP    LINECT,=P'55'           ENOUGH ROOM FOR A LINE ?
        // ASM:00415          BNH   SHORT                   YES - GO PRINT IT
        // ASM:00416          BAL   R4,HEADERS              NO  - PRINT HEADERS
      } //loop
    } //last
    short_: {
      // ENOUGH ROOM FOR A LINE ?
      if (storage.getLinect().getNumeric() <= 55) {
        // YES - GO PRINT IT
        break short_;
      }
      // NO - PRINT HEADERS
      headers();
      // ASM <Code_block_id:SHORT>
      // ASM:00417 SHORT    EQU   *
      // ASM:00418          BAL   R11,SHORTLIN            PRINT LESS THAN 100 CHARACTERS
      // ASM:00419          SPACE 2
      // ASM:00420          BR    R5                      RETURN
    } //short
    // PRINT LESS THAN 100 CHARACTERS
    shortlin();
    // RETURN
    return;
  }

  /**
   * Method FULLINE.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void fulline() throws NAsmException {
    // ASM <Code_block_id:FULLINE>
    // ASM:00421          EJECT
    // ASM:00422 *--------------------------------------------------------------------*
    // ASM:00423 *                F U L L   L I N E   R O U T I N E                   *
    // ASM:00424 *--------------------------------------------------------------------*
    // ASM:00425 FULLINE  EQU   *
    // ASM:00426          MVC   CHAR100(100),0(R8)      MOVE IN THE CHARACTERS
    // ASM:00427          TR    CHAR100(100),STNDCHAR   BLANK OUT NON-STANDARD CHAR
    // ASM:00428          MVZ   ZONE100(100),0(R8)      MOVE ZONES TO OUTPUT
    // ASM:00429          TR    ZONE100(100),TRANS      MAKE THEM PRINTABLE
    // ASM:00430          MVN   NUMR100(100),0(R8)      MOVE NUM PART TO OUTPUT
    // ASM:00431          TR    NUMR100(100),TRANS      MAKE THEM PRINTABLE
    // ASM:00432          S     R7,=F'100'              DECREMENT THE LENGTH
    // ASM:00433          LA    R8,100(R8)              POINT TO NEXT 100
    // ASM:00434          BAL   R4,PRTLINES             PRINT THE LINES
    // ASM:00435          BR    R11
    //--------------------------------------------------------------------*
    // F U L L   L I N E   R O U T I N E                   *
    //--------------------------------------------------------------------*
    // MOVE IN THE CHARACTERS
    storage.getCharline().getChar100().setByteString(getReg8().getByteString(0, 100));
    // BLANK OUT NON-STANDARD CHAR
    storage.getCharline().getChar100().translate(storage.getStndchar(), 100);
    // MOVE ZONES TO OUTPUT
    storage.getZoneline().getZone100().setHighBits(getReg8().getString(0, 100));
    // MAKE THEM PRINTABLE
    storage.getZoneline().getZone100().translate(storage.getTrans(), 100);
    // MOVE NUM PART TO OUTPUT
    storage.getNumrline().getNumr100().setLowBits(getReg8().getString(0, 100));
    // MAKE THEM PRINTABLE
    storage.getNumrline().getNumr100().translate(storage.getTrans(), 100);
    // DECREMENT THE LENGTH
    getReg7().subtractAddress(100);
    // POINT TO NEXT 100
    getReg8().increment(100);
    // PRINT THE LINES
    prtlines();
    return;
  }

  /**
   * Method SHORTLIN.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void shortlin() throws NAsmException {
    // ASM <Code_block_id:SHORTLIN>
    // ASM:00436          SPACE 4
    // ASM:00437 *--------------------------------------------------------------------*
    // ASM:00438 *               S H O R T   L I N E   R O U T I N E                  *
    // ASM:00439 *--------------------------------------------------------------------*
    // ASM:00440 SHORTLIN EQU   *
    // ASM:00441          LTR   R7,R7                   END OF RECORD
    // ASM:00442          BZ    EXITSHRT                YES - GET OUT
    // ASM:00443          BCTR  R7,0                    MODIFY LENGTH FOR MACHINE INST.
    // ASM:00444          STC   R7,MOVEC+1
    // ASM:00445          STC   R7,BLNK_NS+1
    // ASM:00446          STC   R7,MOVEZ+1
    // ASM:00447          STC   R7,APNDTR+1
    // ASM:00448          STC   R7,MOVEN+1
    // ASM:00449          STC   R7,MOVEF+1
    // ASM:00450 MOVEC    MVC   CHAR100(0),0(R8)        MOVE IN THE CHARACTERS
    // ASM:00451 BLNK_NS  TR    CHAR100(0),STNDCHAR     BLANK OUT NON-STANDARD CHAR
    // ASM:00452 MOVEZ    MVZ   ZONE100(0),0(R8)        MOVE ZONES TO OUTPUT
    // ASM:00453 APNDTR   TR    ZONE100(0),TRANS        MAKE THEM PRINTABLE
    // ASM:00454 MOVEN    MVN   NUMR100(0),0(R8)        MOVE NUM PART TO OUTPUT
    // ASM:00455 MOVEF    TR    NUMR100(0),TRANS        MAKE THEM PRINTABLE
    // ASM:00456          MVI   LASTLINE,X'FF'          SIGNAL LAST LINE (SHORT LINE)
    // ASM:00457          BAL   R4,PRTLINES             PRINT THE LINES
    //--------------------------------------------------------------------*
    // S H O R T   L I N E   R O U T I N E                  *
    //--------------------------------------------------------------------*
    exitshrt: {
      // END OF RECORD
      if (getReg7().isEmpty()) {
        // YES - GET OUT
        break exitshrt;
      }
      // MODIFY LENGTH FOR MACHINE INST.
      // getReg7().decrement(1);
      // MOVE IN THE CHARACTERS
      storage.getCharline().getChar100().set(getReg8().getString(0, getReg7().getInt()));
      // BLANK OUT NON-STANDARD CHAR
      storage.getCharline().getChar100().translate(storage.getStndchar(), getReg7().getInt());
      // MOVE ZONES TO OUTPUT
      storage.getZoneline().getZone100().setHighBits(getReg8().getString(0, getReg7().getInt()));
      // MAKE THEM PRINTABLE
      storage.getZoneline().getZone100().translate(storage.getTrans(), getReg7().getInt());
      // MOVE NUM PART TO OUTPUT
      storage.getNumrline().getNumr100().setLowBits(getReg8().getString(0, getReg7().getInt()));
      // MAKE THEM PRINTABLE
      storage.getNumrline().getNumr100().translate(storage.getTrans(), getReg7().getInt());
      // SIGNAL LAST LINE (SHORT LINE)
      storage.getLastline().setByteString(_HIGH(1));
      // PRINT THE LINES
      prtlines();
      // ASM <Code_block_id:EXITSHRT>
      // ASM:00458 EXITSHRT EQU   *
      // ASM:00459          BR    R11
    } //exitshrt
    return;
  }

  /**
   * Method PSPSRTNE.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void pspsrtne() throws NAsmException {
    // ASM <Code_block_id:PSPSRTNE>
    // ASM:00460          EJECT
    // ASM:00461 *--------------------------------------------------------------------*
    // ASM:00462 *                  P R IN T   P S P S #   R O U N T I N E            *
    // ASM:00463 *--------------------------------------------------------------------*
    // ASM:00464 PSPSRTNE EQU   *
    // ASM:00465          ST    R5,SAVER5X
    // ASM:00466          CP    LINECT,=P'55'
    // ASM:00467          BL    PSPS_PRT_OK
    // ASM:00468          ST    R4,SAVER4X
    // ASM:00469          BAL   R4,HEADERS
    // ASM:00470          L     R4,SAVER4X
    //--------------------------------------------------------------------*
    // P R IN T   P S P S #   R O U N T I N E            *
    //--------------------------------------------------------------------*
    storage.getSaver5x().set(getReg5());
    pspsPrtOk: {
      if (storage.getLinect().getNumeric() < 55) {
        break pspsPrtOk;
      }
      storage.getSaver4x().set(getReg4());
      headers();
      getReg4().load(storage.getSaver4x());
      // ASM <Code_block_id:PSPS_PRT_OK>
      // ASM:00471 PSPS_PRT_OK EQU *
      // ASM:00472          ZAP   STARTPOS,PKONE
      // ASM:00473          MVI   PSPSLINE,C'-'
      // ASM:00474          AP    LINECT,PKTWO
      // ASM:00475          LR    R5,R8
      // ASM:00476          LA    R5,5(R5)
      // ASM:00477 NEXTPSPS XR    R4,R4
      // ASM:00478          LH    R4,0(R5)
      // ASM:00479          CVD   R4,DUBBLE
      // ASM:00480          UNPK  PSPS#+4(4),DUBBLE+5(3)
      // ASM:00481          OI    PSPS#+7,X'F0'
      // ASM:00482          XR    R4,R4
      // ASM:00483          LH    R4,2(R5)
      // ASM:00484          CVD   R4,DUBBLE
      // ASM:00485          AR    R5,R4
      // ASM:00486          UNPK  PSPSLEN,DUBBLE+6(2)
      // ASM:00487          OI    PSPSLEN+2,X'F0'
      // ASM:00488          ZAP   SAVEPLEN,DUBBLE
      // ASM:00489          UNPK  STARTOUT,STARTPOS
      // ASM:00490          OI    STARTOUT+3,X'F0'
      // ASM:00491          MVC   STAT(133),PSPSLINE
      // ASM:00492          BAL   R3,WRITE
      // ASM:00493          AP    LINECT,PKONE
      // ASM:00494          CP    LINECT,=P'55'
      // ASM:00495          BL    CONTPSPS
      // ASM:00496          ST    R4,SAVER4X
      // ASM:00497          BAL   R4,HEADERS
      // ASM:00498          L     R4,SAVER4X
      // ASM:00499 CONTPSPS ZAP   DUBBLE,STARTPOS
      // ASM:00500          CVB   R3,DUBBLE
      // ASM:00501          AR    R3,R4
      // ASM:00502          CR    R3,R7
      // ASM:00503          BNL   GONOW
      // ASM:00504          AP    STARTPOS,SAVEPLEN
      // ASM:00505          MVI   PSPSLINE,C' '
      // ASM:00506          B     NEXTPSPS
      // ASM:00507 GONOW    L     R5,SAVER5X
      // ASM:00508          BR    R6
    } //pspsPrtOk
    storage.getStartpos().set(storage.getPkone().getNumeric());
    storage.getPspsline().set("-");
    storage.getLinect().set(storage.getLinect().getNumeric() + storage.getPktwo().getNumeric());
    getReg5().setRegister(getReg8());
    getReg5().increment(5);
    gonow: {
      nextpsps: while (true) {
        getReg4().set(0);
        getReg4().setByteString(_LOW(2) + getReg5().getByteString(0, 2));
        storage.getDubble().setAsPacked(getReg4());
        //storage.getPspsline().getPspsX().set(Strings.formatZeroes(Strings.LeftTrim(storage.getDubble().getString().substring(5, (5) + 3), Pspsline.Len.PSPSX), Pspsline.Len.PSPSX));
        storage.getPspsline().getPspsX().set(storage.getDubble().convertPackedToZoned(5, 3, Pspsline.Len.PSPSX));
        // ASM:00481  [OI right after UNPK is ignored] [ignored]          OI    PSPS#+7,X'F0'
        getReg4().set(0);
        getReg4().setByteString(_LOW(2) + getReg5().getByteString(2, 2));
        storage.getDubble().setAsPacked(getReg4());
        getReg5().increment(getReg4());
        //storage.getPspsline().getPspslen().set(Strings.formatZeroes(Strings.LeftTrim(storage.getDubble().getString().substring(6, (6) + 2), Pspsline.Len.PSPSLEN), Pspsline.Len.PSPSLEN));
        storage.getPspsline().getPspslen().set(storage.getDubble().convertPackedToZoned(6, 2, Pspsline.Len.PSPSLEN));
        // ASM:00487  [OI right after UNPK is ignored] [ignored]          OI    PSPSLEN+2,X'F0'
        storage.getSaveplen().set(storage.getDubble());
        //storage.getPspsline().getStartout().set(Strings.formatZeroes(Strings.LeftTrim(storage.getStartpos().getString(), Pspsline.Len.STARTOUT), Pspsline.Len.STARTOUT));
        storage.getPspsline().getStartout().set(storage.getStartpos().convertPackedToZoned(Pspsline.Len.STARTOUT));
        // ASM:00490  [OI right after UNPK is ignored] [ignored]          OI    STARTOUT+3,X'F0'
        storage.getStat().setByteString(storage.getPspsline().getByteString());
        write();
        storage.getLinect().set(storage.getLinect().getNumeric() + storage.getPkone().getNumeric());
        contpsps: {
          if (storage.getLinect().getNumeric() < 55) {
            break contpsps;
          }
          storage.getSaver4x().set(getReg4());
          headers();
          getReg4().load(storage.getSaver4x());
        } //contpsps
        storage.getDubble().set(storage.getStartpos());
        getReg3().set(storage.getDubble().getNumeric());
        getReg3().increment(getReg4());
        if (!getReg3().less(getReg7())) {
          break gonow;
        }
        storage.getStartpos().set(storage.getStartpos().getNumeric() + storage.getSaveplen().getNumeric());
        storage.getPspsline().set(_SPACE(1));
        continue nextpsps;
      } //nextpsps
    } //gonow
    getReg5().load(storage.getSaver5x());
    return;
  }

  /**
   * Method HEADERS.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void headers() throws NAsmException {
    // ASM <Code_block_id:HEADERS>
    // ASM:00509          EJECT
    // ASM:00510 *--------------------------------------------------------------------*
    // ASM:00511 *                  H E A D E R   R O U T I N E                       *
    // ASM:00512 *--------------------------------------------------------------------*
    // ASM:00513 HEADERS  EQU   *
    // ASM:00514          ZAP   LINECT,=P'1'            COUNT EQFX LINE PLUS BLNK LINE
    // ASM:00515          AP    PAGECTR,PKONE           UPDATE PAGE COUNTER
    // ASM:00516          MVC   PAGECNT(4),PTRN4        MOVE EDIT PATTERN TO HEADING
    // ASM:00517          ED    PAGECNT(4),PAGECTR      EDIT THE PAGE COUNTER
    // ASM:00518          MVC   STAT(133),HEADER1       MOVE AND PRINT THE HEADINGS
    // ASM:00519          BAL   R3,WRITE
    //--------------------------------------------------------------------*
    // H E A D E R   R O U T I N E                       *
    //--------------------------------------------------------------------*
    // COUNT EQFX LINE PLUS BLNK LINE
    storage.getLinect().set(1);
    // UPDATE PAGE COUNTER
    storage.getPagectr().set(storage.getPagectr().getNumeric() + storage.getPkone().getNumeric());
    // MOVE EDIT PATTERN TO HEADING
    storage.getHeader1().getPagecnt().setByteString(storage.getPtrn4().getByteString(0, 4));
    // EDIT THE PAGE COUNTER
    storage.getHeader1().getPagecnt().setEdited(storage.getPagectr(), 0, 4);
    // MOVE AND PRINT THE HEADINGS
    storage.getStat().setByteString(storage.getHeader1().getByteString());
    write();
    // ASM <Code_block_id:FIRST>
    // ASM:00520 FIRST    NOP   BLANKHDR
    // ASM:00521          MVI   FIRST+1,X'F0'
    // ASM:00522          AP    LINECT,=P'2'            COUNT DCB/DSN LINES
    // ASM:00523          MVC   STAT(133),SUBTIT1
    // ASM:00524          BAL   R3,WRITE
    // ASM:00525          MVC   STAT(133),SUBTIT2
    // ASM:00526          BAL   R3,WRITE
    blankhdr: {
      if (first) {
        break blankhdr;
      }
      first = true;
      // COUNT DCB/DSN LINES
      storage.getLinect().set(storage.getLinect().getNumeric() + 2);
      storage.getStat().setByteString(storage.getSubtit1().getByteString());
      write();
      storage.getStat().setByteString(storage.getSubtit2().getByteString());
      write();
      // ASM <Code_block_id:BLANKHDR>
      // ASM:00527 BLANKHDR EQU   *
      // ASM:00528          BAL   R3,WRITE
      // ASM:00529          MVI   CCON,C'0'               SET PRINT CONTROL TO SPACE 2
      // ASM:00530          AP    LINECT,=P'1'             COUNT EQFX LINE PLUS BLNK LINE
    } //blankhdr
    write();
    // SET PRINT CONTROL TO SPACE 2
    storage.getCharline().getCcon().set("0");
    // COUNT EQFX LINE PLUS BLNK LINE
    storage.getLinect().set(storage.getLinect().getNumeric() + 1);
    // ASM <Code_block_id:CONTHDR>
    // ASM:00531 CONTHDR  EQU   *
    // ASM:00532          BR    R4                      RETURN
    // RETURN
    return;
  }

  /**
   * Method PRTLINES.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void prtlines() throws NAsmException {
    // ASM <Code_block_id:PRTLINES>
    // ASM:00533          EJECT
    // ASM:00534 *--------------------------------------------------------------------*
    // ASM:00535 *            P R I N T    L I N E S    R O U T I N E                 *
    // ASM:00536 *--------------------------------------------------------------------*
    // ASM:00537 PRTLINES EQU   *
    // ASM:00538          MVC   STAT(133),CHARLINE      PRINT THE LINES
    // ASM:00539          BAL   R3,WRITE
    // ASM:00540          MVC   STAT(133),ZONELINE
    // ASM:00541          BAL   R3,WRITE
    // ASM:00542          MVC   STAT(133),NUMRLINE
    // ASM:00543          BAL   R3,WRITE
    // ASM:00544 *
    // ASM:00545          TM    MODE2,LCRSEG            LCR SEGMENT?
    // ASM:00546          BNO   NONLCR                  NO
    // ASM:00547          TM    MODE2,CHK_AUD           LCR CHECK/AUDIT OPTION?
    // ASM:00548          BZ    NONLCR                  NO
    // ASM:00549          LA    R10,LGUIDE
    // ASM:00550          B     MOVGUIDE
    //--------------------------------------------------------------------*
    // P R I N T    L I N E S    R O U T I N E                 *
    //--------------------------------------------------------------------*
    prtguide: {
      moveall: {
        movguide: {
          nonlcr: {
            // PRINT THE LINES
            storage.getStat().setByteString(storage.getCharline().getByteString());
            write();
            storage.getStat().setByteString(storage.getZoneline().getByteString());
            write();
            storage.getStat().setByteString(storage.getNumrline().getByteString());
            write();
            //
            // LCR SEGMENT?
            // NO
            if (storage.getMode2().testUnderMask(PsdittoConstants.LCRSEG) != PsdittoConstants.LCRSEG) {
              break nonlcr;
            }
            // LCR CHECK/AUDIT OPTION?
            if (storage.getMode2().testUnderMask(PsdittoConstants.CHK_AUD) == ZERO) {
              // NO
              break nonlcr;
            }
            getReg10().setAddress(storage.getLguide());
            break movguide;
            // ASM <Code_block_id:NONLCR>
            // ASM:00551 *
            // ASM:00552 NONLCR   EQU   *
            // ASM:00553          MVC   EDWORK(4),PTRN4         MOVE THE EDIT PATTERN OUT
            // ASM:00554          ED    EDWORK(4),GUIDECTR      EDIT THE GUIDE COUNTER
            // ASM:00555          MVC   GUIDE#(3),EDWORK+1      MOVE IT TO THE OUTPUT
            // ASM:00556          CLI   LASTLINE,X'FF'          ARE WE ON LAST LINE ?
            // ASM:00557          BNE   MOVEALL                 NO  - MOVE THE WHOLE GUIDE
            // ASM:00558          MVI   LASTLINE,X'00'          YES - CLEAR THE FLAG
            // ASM:00559          LA    R10,GUIDE
            // ASM:00560          A     R7,=F'28'               ADD 3 TO RECORD LENGTH
            //
          } //nonlcr
            // MOVE THE EDIT PATTERN OUT
          storage.getEdwork().setByteString(storage.getPtrn4().getByteString(0, 4));
          // EDIT THE GUIDE COUNTER
          storage.getEdwork().setEdited(storage.getGuidectr(), 0, 4);
          // MOVE IT TO THE OUTPUT
          storage.getGuide().getGuideX().setByteString(storage.getEdwork().getByteString(1, 3));
          // ARE WE ON LAST LINE ?
          // NO - MOVE THE WHOLE GUIDE
          if (!storage.getLastline().isHighValue()) {
            break moveall;
          }
          // YES - CLEAR THE FLAG
          storage.getLastline().setByteString(_LOW(1));
          getReg10().setAddress(storage.getGuide());
          // ADD 3 TO RECORD LENGTH
          getReg7().addAddress(28);
          // ASM <Code_block_id:MOVGUIDE>
          // ASM:00561 MOVGUIDE EQU   *
          // ASM:00562          EX    R7,XMOVGUID             MOVE PART OF THE GUIDE
          // ASM:00563          B     PRTGUIDE                PRINT THE GUIDE LINE
          // ASM:00564 MOVEALL  MVC   STAT(133),GUIDE         MOVE THE GUIDE LINE
          // ASM:00565 PRTGUIDE BAL   R3,WRITE                GO PRINT THE GUIDE
          // ASM:00566          AP    GUIDECTR(2),PKONE       INCREMENT GUIDE COUNTER
        } //movguide
        // MOVE PART OF THE GUIDE
        storage.getStat().set(getReg10().getString(0, getReg7().getInt()));
        // PRINT THE GUIDE LINE
        break prtguide;
      } //moveall
      storage.getStat().setByteString(storage.getGuide().getByteString());
    } //prtguide
    //GO PRINT THE GUIDE
    write();
    // INCREMENT GUIDE COUNTER
    storage.getGuidectr().set(storage.getGuidectr().getNumeric() + storage.getPkone().getNumeric());
    // ASM <Code_block_id:ADDLINE>
    // ASM:00567 ADDLINE  EQU   *
    // ASM:00568          AP    LINECT,=P'5'            INCREMENT LINE COUNTER
    // ASM:00569 *
    // ASM:00570          MVI   CCON,C'0'               RESET TO SKIP 2 LINES
    // ASM:00571          MVC   CHARDATA(22),BLANKS
    // ASM:00572          MVC   CHAR100(100),BLANKS     CLEAR THE PRINT LINE
    // ASM:00573          MVC   ZONE100(100),BLANKS           TO BLANKS
    // ASM:00574          XC    NUMR100(100),NUMR100    CLEAR TO HEX ZEROS FOR TR
    // ASM:00575          MVC   SEGLOC,BLANKS
    // ASM:00576          SPACE
    // ASM:00577          BR    R4                      RETURN
    // INCREMENT LINE COUNTER
    storage.getLinect().set(storage.getLinect().getNumeric() + 5);
    //
    // RESET TO SKIP 2 LINES
    storage.getCharline().getCcon().set("0");
    storage.getCharline().setByteString(storage.getBlanks().getByteString(0, 22), Charline.Pos.CHARDATA);
    // CLEAR THE PRINT LINE
    storage.getCharline().getChar100().setByteString(storage.getBlanks().getByteString(0, 100));
    // TO BLANKS
    storage.getZoneline().getZone100().setByteString(storage.getBlanks().getByteString(0, 100));
    // CLEAR TO HEX ZEROS FOR TR
    storage.getNumrline().getNumr100().setByteString(_LOW(Numrline.Len.NUMR100));
    storage.getGuide().getSegloc().setByteString(storage.getBlanks().getByteString(0, Guide.Len.SEGLOC));
    // RETURN
    return;
  }

  /**
   * Method WRITE.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void write() throws NAsmException {
    // ASM <Code_block_id:WRITE>
    // ASM:00578          EJECT
    // ASM:00579 *--------------------------------------------------------------------*
    // ASM:00580 *                 W R I T E    R O U T I N E                         *
    // ASM:00581 *--------------------------------------------------------------------*
    // ASM:00582 WRITE    EQU   *
    // ASM:00583          LA    R2,OUTPUT
    // ASM:00584          TM    MODE,CALLED             CALLED MODULE?
    // ASM:00585          BNO   WRITE1                  NO
    // ASM:00586          L     R2,SAVER2
    // ASM:00587          L     R2,4(R2)
    //--------------------------------------------------------------------*
    // W R I T E    R O U T I N E                         *
    //--------------------------------------------------------------------*
    getReg2().setAddress(output);
    write1: {
      // CALLED MODULE?
      // NO
      if (storage.getMode().testUnderMask(PsdittoConstants.CALLED) != PsdittoConstants.CALLED) {
        break write1;
      }
      getReg2().load(storage.getSaver2());
      getReg2().load(getReg2(), 4);
      // ASM <Code_block_id:WRITE1>
      // ASM:00588 WRITE1   EQU   *
      // ASM:00589          PUT   0(R2),STAT
      // ASM:00590          MVC   STAT(133),BLANKS        CLEAR PRINT LINE
      // ASM:00591          SPACE
      // ASM:00592          BR    R3                      RETURN
    } //write1
    NAsmFile f = (NAsmFile) getReg2().getPointedAddress().getAddressedField();
    f.write(storage.getStat());
    // CLEAR PRINT LINE
    storage.getStat().setByteString(storage.getBlanks().getByteString());
    // RETURN
    return;
  }

  /**
   * Method USER1.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void user1() throws NAsmException {
    // ASM <Code_block_id:USER1>
    // ASM:00593          EJECT
    // ASM:00594 *---------------------------------------------------------------------*
    // ASM:00595 *        E R R O R   R O U T I N E S                                  *
    // ASM:00596 *---------------------------------------------------------------------*
    // ASM:00597 USER1    EQU   *
    // ASM:00598          LA    R2,USER1$
    // ASM:00599          LA    R11,0                   LOAD DISPLACEMENT
    // ASM:00600          B     ERRMODE
    //---------------------------------------------------------------------*
    // E R R O R   R O U T I N E S                                  *
    //---------------------------------------------------------------------*
    getReg2().setAddress(storage.getUser1S());
    // LOAD DISPLACEMENT
    getReg11().set(0);
    errmode();
  }

  /**
   * Method USER2.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void user2() throws NAsmException {
    // ASM <Code_block_id:USER2>
    // ASM:00601 USER2    EQU   *
    // ASM:00602          LA    R2,USER2$
    // ASM:00603          LA    R11,16                  LOAD DISPLACEMENT
    // ASM:00604          B     ERRMODE
    getReg2().setAddress(storage.getUser2S());
    // LOAD DISPLACEMENT
    getReg11().set(16);
    errmode();
  }

  /**
   * Method USER3.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void user3() throws NAsmException {
    // ASM <Code_block_id:USER3>
    // ASM:00605 USER3    EQU   *
    // ASM:00606          LA    R2,USER3$
    // ASM:00607          LA    R11,32                  LOAD DISPLACEMENT
    // ASM:00608          B     ERRMODE
    getReg2().setAddress(storage.getUser3S());
    // LOAD DISPLACEMENT
    getReg11().set(32);
    errmode();
  }

  /**
   * Method USER4.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void user4() throws NAsmException {
    // ASM <Code_block_id:USER4>
    // ASM:00609 USER4    EQU   *
    // ASM:00610          LA    R2,USER4$
    // ASM:00611          LA    R11,48                  LOAD DISPLACEMENT
    // ASM:00612          B     ERRMODE
    getReg2().setAddress(storage.getUser4S());
    // LOAD DISPLACEMENT
    getReg11().set(48);
    errmode();
  }

  /**
   * Method USER5.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void user5() throws NAsmException {
    // ASM <Code_block_id:USER5>
    // ASM:00613 USER5    EQU   *
    // ASM:00614          LA    R2,USER5$
    // ASM:00615          LA    R11,64                  LOAD DISPLACEMENT
    // ASM:00616          B     ERRMODE
    getReg2().setAddress(storage.getUser5S());
    // LOAD DISPLACEMENT
    getReg11().set(64);
    errmode();
  }

  /**
   * Method OSCRFAIL.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void oscrfail() throws NAsmException {
    // ASM <Code_block_id:OSCRFAIL>
    // ASM:00617 OSCRFAIL EQU   *
    // ASM:00618          LA    R2,FAIL$
    // ASM:00619          LA    R11,80                  LOAD DISPLACEMENT
    getReg2().setAddress(storage.getFailS());
    // LOAD DISPLACEMENT
    getReg11().set(80);
    errmode();
  }

  /**
     * Method ERRMODE.
     *
     * @throws NAsmException if there is a specific issue
     */
  private void errmode() throws NAsmException {
    // ASM <Code_block_id:ERRMODE>
    // ASM:00620 ERRMODE  EQU   *
    // ASM:00621          TM    MODE,CALLED             CALLED MODULE?
    // ASM:00622          BNO   MODESTND                NO
    // ASM:00623          MVC   WTO4+30(10),=C'CALLED    '
    // ASM:00624          B     ERRMOVE
    errmove: {
      modestnd: {
        // CALLED MODULE?
        // NO
        if (storage.getMode().testUnderMask(PsdittoConstants.CALLED) != PsdittoConstants.CALLED) {
          break modestnd;
        }
        wto4 = NAsmStrings.overlay(wto4, 22, 10, "CALLED    ");
        break errmove;
        // ASM <Code_block_id:MODESTND>
        // ASM:00625 MODESTND EQU   *
        // ASM:00626          MVC   WTO4+30(10),=C'STANDALONE'
      } //modestnd
      wto4 = NAsmStrings.overlay(wto4, 22, 10, "STANDALONE");
      // ASM <Code_block_id:ERRMOVE>
      // ASM:00627 ERRMOVE  EQU   *
      // ASM:00628          MVC   WTO1+10(75),0(R2)       MOVE MESSAGE TO WTO LINES
      // ASM:00629          MVC   WTO2+10(75),75(R2)
      // ASM:00630          MVC   WTO3+10(75),150(R2)
      // ASM:00631          MVC   WTO4+10(20),225(R2)
      // ASM:00632          BAL   R4,WTOIT                WRITE TO OPERATOR
      // ASM:00633          B     ERRABEND(R11)
    } //errmove
      // MOVE MESSAGE TO WTO LINES
    wto1 = NAsmStrings.overlay(wto1, 2, 75, getReg2().getString(0, 75));
    wto2 = NAsmStrings.overlay(wto2, 2, 75, getReg2().getString(75, 75));
    wto3 = NAsmStrings.overlay(wto3, 2, 75, getReg2().getString(150, 75));
    wto4 = NAsmStrings.overlay(wto4, 2, 20, getReg2().getString(225, 20));
    // WRITE TO OPERATOR
    wtoit();
    // ASM <Code_block_id:ERRABEND>
    // ASM:00634          SPACE
    // ASM:00635 ERRABEND EQU   *
    // ASM:00636          ABEND 3001,DUMP               USER1 - STOP PROCESSING
    // ASM:00637          ABEND 3002,DUMP               USER2
    // ASM:00638          ABEND 3003,DUMP               USER3
    // ASM:00639          ABEND 3004,DUMP               USER4
    // ASM:00640          ABEND 3005,DUMP               USER5
    // ASM:00641          BR    R5                      RETURN (LEN=4,KEEP AFTER ABENDS)
    switch (getReg11().getInt()) {
    case 0:
      // USER1 - STOP PROCESSING
      throw new NAsmAbendException(3001);
    case 16:
      // USER2
      throw new NAsmAbendException(3002);
    case 32:
      // USER3
      throw new NAsmAbendException(3003);
    case 48:
      // USER4
      throw new NAsmAbendException(3004);
    case 64:
      // USER5
      throw new NAsmAbendException(3005);
    }
    // RETURN (LEN=4,KEEP AFTER ABENDS)
    return;
  }

  /**
   * Method WTOIT.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void wtoit() throws NAsmException {
    // ASM <Code_block_id:WTOIT>
    // ASM:00642          EJECT
    // ASM:00643 *---------------------------------------------------------------------*
    // ASM:00644 *        W T O   R O U T I N E                                        *
    // ASM:00645 *---------------------------------------------------------------------*
    // ASM:00646 WTOIT    EQU   *
    // ASM:00648          WTO   '*------------------------------------------------------------------------------*'
    // ASM:00650 WTO1     WTO   '*                        *'
    // ASM:00652 WTO2     WTO   '*                        *'
    // ASM:00654 WTO3     WTO   '*                        *'
    // ASM:00656 WTO4     WTO   '*                        *'
    // ASM:00658          WTO   '*------------------------------------------------------------------------------*'
    // ASM:00659          BR    R4
    //---------------------------------------------------------------------*
    // W T O   R O U T I N E                                        *
    //---------------------------------------------------------------------*
    NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.CONSOLE, wtof1);
    NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.CONSOLE, wto1);
    NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.CONSOLE, wto2);
    NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.CONSOLE, wto3);
    NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.CONSOLE, wto4);
    NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.CONSOLE, wtof2);
    return;
  }

  /**
   * Method USER1$.
   *
   * @throws NAsmException if there is a specific issue
   */
  @SuppressWarnings("unused")
  private void user1$() throws NAsmException {
    // ASM <Code_block_id:USER1$>
    // ASM:00660          EJECT
    // ASM:00661 *---------------------------------------------------------------------*
    // ASM:00662 *        E R R O R   M E S S A G E S                                  *
    // ASM:00663 *---------------------------------------------------------------------*
    // ASM:00664          SPACE
    // ASM:00665 USER1$   EQU   *
    //---------------------------------------------------------------------*
    // E R R O R   M E S S A G E S                                  *
    //---------------------------------------------------------------------*
    // ASM <Code_block_id:USER2$>
    // ASM:00671          SPACE
    // ASM:00672 USER2$   EQU   *
    // ASM <Code_block_id:USER3$>
    // ASM:00678          SPACE
    // ASM:00679 USER3$   EQU   *
    // ASM <Code_block_id:USER4$>
    // ASM:00686          SPACE
    // ASM:00687 USER4$   EQU   *
    // ASM <Code_block_id:USER5$>
    // ASM:00692          SPACE
    // ASM:00693 USER5$   EQU   *
    // ASM <Code_block_id:FAIL$>
    // ASM:00700          SPACE
    // ASM:00701 FAIL$    EQU   *
    // ASM:00707          EJECT
    // ASM:00708 ***********************************************************************
    // ASM:00709 *                                                                     *
    // ASM:00710 *            M A I N    W O R K I N G     S T O R A G E               *
    // ASM:00711 *                                                                     *
    // ASM:00712 ***********************************************************************
    // ASM:00713          SPACE
    // ASM:00714 XMOVGUID MVC   STAT(0),0(R10)           USED BY PRTLINES ROUTINE
    // ASM:00891          SPACE
    // ASM:00892          ORG   CHARFLDS
    // ASM:00896          SPACE
    // ASM:00897          ORG   CHARFLDS
    // ASM:00984          ORG   NOTNUM+C'0'
    // ASM:00986          ORG
    //*********************************************************************
    // *
    // M A I N    W O R K I N G     S T O R A G E               *
    // *
    //*********************************************************************
    //*xmovguid:
    // USED BY PRTLINES ROUTINE
    //[converted_at_execute] [XMOVGUID MVC   STAT(0),0(R10)]
  }

  /**
   * Method LCRDITTO.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void lcrditto() throws NAsmException {
    // ASM <Code_block_id:LCRDITTO>
    // ASM:01027          EJECT
    // ASM:01028 ***********************************************************************
    // ASM:01029 *                                                                     *
    // ASM:01030 *                        SUB-PROGRAM                                  *
    // ASM:01031 *                                                                     *
    // ASM:01032 *             SPECIAL CHECK/AUDIT DITTO FOR LCR SEGMENT               *
    // ASM:01033 *                                                                     *
    // ASM:01034 ***********************************************************************
    // ASM:01035 LCRDITTO CSECT
    // ASM:01036          STM   R2,R14,SAVE13F       SAVE REGISTERS FROM MAIN
    // ASM:01037          LR    R9,R15
    // ASM:01038          USING LCRDITTO,R9          USE R9 AS BASE FOR THIS SUB
    //*********************************************************************
    // *
    // SUB-PROGRAM                                  *
    // *
    // SPECIAL CHECK/AUDIT DITTO FOR LCR SEGMENT               *
    // *
    //*********************************************************************
    // ASM:01035 [ignored] LCRDITTO CSECT
    // SAVE REGISTERS FROM MAIN
    NAsmEnvironment.multipleStore(REG_2, REG_14, storage.getSave13f());
    getReg9().setRegister(getReg15());
    // USE R9 AS BASE FOR THIS SUB
    // ASM <Code_block_id:TRYCHECK>
    // ASM:01039          SPACE 2
    // ASM:01040 TRYCHECK EQU   *
    // ASM:01041          TM    MODE2,CHECK          CHECK OPTION?
    // ASM:01042          BNO   TRYAUDIT             NO
    // ASM:01043          BAL   R5,LDITTO            DO LCR CHECK DITTO
    tryaudit: {
      // CHECK OPTION?
      // NO
      if (storage.getMode2().testUnderMask(PsdittoConstants.CHECK) != PsdittoConstants.CHECK) {
        break tryaudit;
      }
      // DO LCR CHECK DITTO
      lditto();
      // ASM <Code_block_id:TRYAUDIT>
      // ASM:01044          SPACE 2
      // ASM:01045 TRYAUDIT EQU   *
      // ASM:01046          TM    MODE2,AUDIT          AUDIT OPTION?
      // ASM:01047          BNO   LRETRN               NO
      // ASM:01048          BAL   R5,ADITTO            DO LCR AUDIT DITTO
    } //tryaudit
    lretrn: {
      // AUDIT OPTION?
      // NO
      if (storage.getMode2().testUnderMask(PsdittoConstants.AUDIT) != PsdittoConstants.AUDIT) {
        break lretrn;
      }
      // DO LCR AUDIT DITTO
      aditto();
      // ASM <Code_block_id:LRETRN>
      // ASM:01049          SPACE 2
      // ASM:01050 LRETRN   EQU   *
      // ASM:01051          LM    R2,R14,SAVE13F       RESTORE REGISTERS FROM MAIN
      // ASM:01052          BR    R14                  RETURN TO MAIN
    } //lretrn
    // RESTORE REGISTERS FROM MAIN
    NAsmEnvironment.multipleLoad(REG_2, REG_14, storage.getSave13f());
    // RETURN TO MAIN
    return;
  }

  /**
   * Method LDITTO.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void lditto() throws NAsmException {
    // ASM <Code_block_id:LDITTO>
    // ASM:01053          EJECT
    // ASM:01054 *--------------------------------------------------------------------*
    // ASM:01055 *        L C R   C H E C K   D I T T O    R O U T I N E              *
    // ASM:01056 *--------------------------------------------------------------------*
    // ASM:01057 LDITTO   EQU  *
    // ASM:01058          ST    R5,SAVER5
    // ASM:01059          MVC   LPOSFLD(6),BLANKS       CLEAR AREA LEFT OF POS 29
    // ASM:01060          SPACE
    // ASM:01061          TM    MODE,STANDARD           OSCR DITTO?
    // ASM:01062          BO    GO_LDITT                NO
    // ASM:01063          TM    MODE2,SEL_OTH           SELECT NON_HDR ON?
    // ASM:01064          BO    PASTLOCX                YES, DON'T WRITE SEG DISPLACMENT
    // ASM:01065          AP    SEGLOCST,=P'4'          LOC+4 = DISPLACEMNT FOR THIS SEG
    // ASM:01066          MVC   WORK12(8),PTRN8         ED PATTERN
    // ASM:01067          ED    WORK12(8),SEGLOCST
    // ASM:01068          MVC   LSEGLOC(8),WORK12       MOVE DISPLACEMENT TO GAUGE LINE
    // ASM:01069          AP    SEGLOCST,=P'1'          ADD 1 TO GET TRUE LOCATION
    //--------------------------------------------------------------------*
    // L C R   C H E C K   D I T T O    R O U T I N E              *
    //--------------------------------------------------------------------*
    storage.getSaver5().set(getReg5());
    // CLEAR AREA LEFT OF POS 29
    storage.getLguide().getLposfld().setByteString(storage.getBlanks().getByteString(0, 6));
    goLditt: {
      // OSCR DITTO?
      // NO
      if (storage.getMode().testUnderMask(PsdittoConstants.STANDARD) == PsdittoConstants.STANDARD) {
        break goLditt;
      }
      pastlocx: {
        // SELECT NON_HDR ON?
        // YES, DON'T WRITE SEG DISPLACMENT
        if (storage.getMode2().testUnderMask(PsdittoConstants.SEL_OTH) == PsdittoConstants.SEL_OTH) {
          break pastlocx;
        }
        // LOC+4 = DISPLACEMNT FOR THIS SEG
        storage.getSeglocst().set(storage.getSeglocst().getNumeric() + 4);
        // ED PATTERN
        storage.getWork12().setByteString(storage.getPtrn8().getByteString(0, 8));
        storage.getWork12().setEdited(storage.getSeglocst(), 0, 8);
        // MOVE DISPLACEMENT TO GAUGE LINE
        storage.getLguide().getLsegloc().setByteString(storage.getWork12().getByteString(0, 8));
        // ADD 1 TO GET TRUE LOCATION
        storage.getSeglocst().set(storage.getSeglocst().getNumeric() + 1);
        // ASM <Code_block_id:PASTLOCX>
        // ASM:01070          SPACE 2
        // ASM:01071 PASTLOCX EQU   *
        // ASM:01072          L     R14,SAVER9              LOAD ADDR OF SEG DISPLACEMENT
        // ASM:01073          LH    R1,0(R14)               GET SEGMENT DISPLACEMENT
        // ASM:01074          AR    R1,R8                   ADD RECORD ADDRESS
        // ASM:01075          LR    R8,R1                   ADDR OF SEGMENT
        // ASM:01076          LH    R7,3(R8)                SEGMENT LENGTH
        // ASM:01077          S     R7,=F'5'                SKIP 5 BYTE DESC/LEN
        // ASM:01078          A     R8,=F'5'                SKIP 5 BYTE DESC/LEN
        // ASM:01079          SPACE 2
        // ASM:01080          CVD   R7,WRKDBL               CONVERT LEN TO PACK
        // ASM:01081          AP    SEGLOCST,WRKDBL+4(4)    ADD SEG LEN TO SEG LOC STORAGE
      } //pastlocx
      // LOAD ADDR OF SEG DISPLACEMENT
      getReg14().load(storage.getSaver9());
      // GET SEGMENT DISPLACEMENT
      getReg1().setByteString(_LOW(2) + getReg14().getByteString(0, 2));
      // ADD RECORD ADDRESS
      getReg1().increment(getReg8());
      // ADDR OF SEGMENT
      getReg8().setRegister(getReg1());
      // SEGMENT LENGTH
      getReg7().setByteString(_LOW(2) + getReg8().getByteString(3, 2));
      // SKIP 5 BYTE DESC/LEN
      getReg7().subtractAddress(5);
      // SKIP 5 BYTE DESC/LEN
      getReg8().addAddress(5);
      // CONVERT LEN TO PACK
      storage.getWrkdbl().setAsPacked(getReg7());
      // ADD SEG LEN TO SEG LOC STORAGE
      storage.getSeglocst().set(storage.getSeglocst().getNumeric() + storage.getWrkdbl().getPackedAtRange(4, (4) + 4));
      // ASM <Code_block_id:GO_LDITT>
      // ASM:01082          SPACE
      // ASM:01083 GO_LDITT EQU   *
      // ASM:01084          MVC   LCRWORK,LCRADDR         ADDR OF GETMAIN TABLE OF FLDS
      // ASM:01085          MVI   CCON,C'-'               SKIP 2 LINES FOR NEW RECORD
      // ASM:01086          AP    LINECT,PKONE            COUNT LINE FROM PRINT CONTROL
      // ASM:01087          SPACE
      // ASM:01088          XR    R6,R6                   SET CUMULATIVE LENGTH TO ZERO
      // ASM:01089          LA    R10,101                 INITIALIZE CHAR PER LINE AS HIGH
      // ASM:01090          MVI   SKIPONCE+1,X'00'
    } //goLditt
    // ADDR OF GETMAIN TABLE OF FLDS
    storage.getLcrwork().setByteString(storage.getLcraddr().getByteString());
    // SKIP 2 LINES FOR NEW RECORD
    storage.getCharline().getCcon().set("-");
    // COUNT LINE FROM PRINT CONTROL
    storage.getLinect().set(storage.getLinect().getNumeric() + storage.getPkone().getNumeric());
    // SET CUMULATIVE LENGTH TO ZERO
    getReg6().set(0);
    // INITIALIZE CHAR PER LINE AS HIGH
    getReg10().set(101);
    skiponce = false;
    // ASM <Code_block_id:LCRLOOP>
    // ASM:01091 LCRLOOP  EQU   *
    // ASM:01092          LTR   R7,R7                   END OF RECORD?
    // ASM:01093          BNP   LCREND                  YES
    // ASM:01094          SPACE
    // ASM:01095          L     R1,LCRWORK              ADDR OF LCR FIELDS TABLE
    // ASM:01096          CLI   0(R1),X'FF'             END OF TABLE?
    // ASM:01097          BE    USER3                   YES, ERROR
    // ASM:01098          PACK  WRKDBL,0(2,R1)          GET NEW FIELD
    // ASM:01099          LA    R1,80(R1)               BUMP TO NEXT ENTRY
    // ASM:01100          ST    R1,LCRWORK              STORE ADDRESS OF NEXT FIELD
    // ASM:01101          SPACE
    // ASM:01102          CP    LINECT,=P'55'           ROOM FOR POSSIBLE 5 LINES?
    // ASM:01103          BL    PERLINE                 YES
    // ASM:01104          BAL   R4,HEADERS              NO,  START NEW PAGE
    llast: {
      lcrend: {
        lcrloop: while (true) {
          // END OF RECORD?
          if (!getReg7().greater(_ZERO)) {
            // YES
            break lcrend;
          }
          // ADDR OF LCR FIELDS TABLE
          getReg1().load(storage.getLcrwork());
          // END OF TABLE?
          if (getReg1().getByteString(0, 1).equals(_HIGH(1))) {
            user3();
          }
          // GET NEW FIELD
          storage.getWrkdbl().set(getReg1().getNumericAtRange(0, 2));
          // BUMP TO NEXT ENTRY
          getReg1().increment(80);
          // STORE ADDRESS OF NEXT FIELD
          storage.getLcrwork().set(getReg1());
          perline: {
            // ROOM FOR POSSIBLE 5 LINES?
            if (storage.getLinect().getNumeric() < 55) {
              // YES
              break perline;
            }
            // NO, START NEW PAGE
            headers();
            // ASM <Code_block_id:PERLINE>
            // ASM:01105          SPACE
            // ASM:01106 PERLINE  EQU   *
            // ASM:01107          CVB   R1,WRKDBL               GET LENGTH OF NEXT FIELD
            // ASM:01108          AR    R1,R10                  ADD COUNT PER LINE
            // ASM:01109          AH    R1,=H'4'                MAX SEPERATION BETWEEN FIELDS
            // ASM:01110          CH    R1,=H'100'              REACHED CHAR PER LINE LIMIT?
            // ASM:01111          BNH   KEEPON                  NO
            // ASM:01112 SKIPONCE TS    SKIPONCE+1
            // ASM:01113          BZ    FRST_POS
            // ASM:01114          ST    R7,SAVER7
            // ASM:01115          LR    R7,R5
            // ASM:01116          BCTR  R7,0
            // ASM:01117          BAL   R4,PRTLINES             YES, PRINT LINES
            // ASM:01118          MVI   LGUIDE2,C'.'
            // ASM:01119          MVC   LGUIDE2+1(101),LGUIDE2
            // ASM:01120          L     R7,SAVER7
            // ASM:01121 FRST_POS XR    R10,R10                 RESET CHAR PER LINE COUNT
            // ASM:01122          LA    R5,27                   RESET OFFSET VALUE
            // ASM:01123          LR    R14,R6                  GET CUMULATIVE LENGTH
            // ASM:01124          AH    R14,=H'1'               ACTUAL POSITION
            // ASM:01125          LR    R1,R5                   OFFSET TO FIELD START LOCATION
            // ASM:01126          XR    R15,R15
            // ASM:01127          CH    R14,=H'10'              POSITION < 10?
            // ASM:01128          BL    STORDIG                 YES
            // ASM:01129          BCTR  R1,0                    SUBTRACT 1
            // ASM:01130          AH    R15,=H'1'
            // ASM:01131          CH    R14,=H'100'             POSITION < 100?
            // ASM:01132          BL    STORDIG                 YES
            // ASM:01133          BCTR  R1,0                    SUBTRACT 1
            // ASM:01134          AH    R15,=H'1'
            // ASM:01135          CH    R14,=H'1000'            POSITION < 1000?
            // ASM:01136          BL    STORDIG                 YES
            // ASM:01137          BCTR  R1,0                    SUBTRACT 1
            // ASM:01138          AH    R15,=H'1'
          } //perline
            // GET LENGTH OF NEXT FIELD
          getReg1().set(storage.getWrkdbl().getNumeric());
          // ADD COUNT PER LINE
          getReg1().increment(getReg10());
          // MAX SEPERATION BETWEEN FIELDS
          getReg1().addAddress(4);
          posFld: {
            keepon: {
              // REACHED CHAR PER LINE LIMIT?
              if (!getReg1().greater(100, 2, 2)) {
                // NO
                break keepon;
              }
              frstPos: {
                if (!skiponce) {
                  skiponce = true;
                  break frstPos;
                } else {
                  skiponce = true;
                }
                storage.getSaver7().set(getReg7());
                getReg7().setRegister(getReg5());
                getReg7().decrement(1);
                // YES, PRINT LINES
                prtlines();
                storage.getLguide().getLguide2AtIndex(0).set(".");
                storage.getLguide().getLguide2().set(NAsmStrings.repeatCharacter(storage.getLguide().getLguide2().getString().charAt(1), 101));
                getReg7().load(storage.getSaver7());
              } //frstPos
              // RESET CHAR PER LINE COUNT
              getReg10().set(0);
              // RESET OFFSET VALUE
              getReg5().set(27);
              // GET CUMULATIVE LENGTH
              getReg14().setRegister(getReg6());
              // ACTUAL POSITION
              getReg14().addAddress(1);
              // OFFSET TO FIELD START LOCATION
              getReg1().setRegister(getReg5());
              getReg15().set(0);
              stordig: {
                // POSITION < 10?
                if (getReg14().less(10)) {
                  // YES
                  break stordig;
                }
                // SUBTRACT 1
                getReg1().decrement(1);
                getReg15().addAddress(1);
                // POSITION < 100?
                if (getReg14().less(100)) {
                  // YES
                  break stordig;
                }
                // SUBTRACT 1
                getReg1().decrement(1);
                getReg15().addAddress(1);
                // POSITION < 1000?
                if (getReg14().less(1000, 2, 2)) {
                  // YES
                  break stordig;
                }
                // SUBTRACT 1
                getReg1().decrement(1);
                getReg15().addAddress(1);
                // ASM <Code_block_id:STORDIG>
                // ASM:01139 STORDIG  EQU   *
                // ASM:01140          STH   R15,LDIG                # OF DIGITS LEFT OF POS 29
                // ASM:01141          MVI   BEGLIN+1,X'00'          OPEN DOOR
                // ASM:01142          B     POS_FLD
              } //stordig
                // # OF DIGITS LEFT OF POS 29
              storage.getLdig().setByteString(getReg15().getByteString(2, 2));
              // OPEN DOOR
              beglin = false;
              break posFld;
              // ASM <Code_block_id:KEEPON>
              // ASM:01143          SPACE
              // ASM:01144 KEEPON   EQU   *
              // ASM:01145          LR    R1,R5
            } //keepon
            getReg1().setRegister(getReg5());
            // ASM <Code_block_id:POS_FLD>
            // ASM:01146 POS_FLD  EQU   *
            // ASM:01147          BAL   R11,POSITION            PUT POSITION TO PRINT LINE
            // ASM:01148          SPACE
            // ASM:01149          CVB   R2,WRKDBL               BINARY LENGTH OF CURRENT FIELD
            // ASM:01150          BAL   R11,FIELD               PUT FIELD TO PRINT LINE
            // ASM:01151          SPACE
            // ASM:01152          AR    R6,R2                   ADD TO CUMULATIVE LENGTH
            // ASM:01153          AR    R8,R2                   BUMP PTR TO RECORD BY FLD LEN
            // ASM:01154          SR    R7,R2                   DECREMENT RECORD LENGTH VALUE
            // ASM:01155          LH    R0,DIGITS               # OF DIGITS IN POSITION VALUE
            // ASM:01156          AR    R10,R2                  ADD LENGTH TO PER LINE COUNT
            // ASM:01157          AR    R10,R0                  ADD # DIGITS TO PER LINE COUNT
            // ASM:01158          AR    R5,R2                   BUMP OFFSET TO POS ON LINE
            // ASM:01159          AR    R5,R0                   ADD # DIGITS TO OFFSET
          } //posFld
          // PUT POSITION TO PRINT LINE
          position();
          // BINARY LENGTH OF CURRENT FIELD
          getReg2().set(storage.getWrkdbl().getNumeric());
          // PUT FIELD TO PRINT LINE
          field();
          // ADD TO CUMULATIVE LENGTH
          getReg6().increment(getReg2());
          // BUMP PTR TO RECORD BY FLD LEN
          getReg8().increment(getReg2());
          // DECREMENT RECORD LENGTH VALUE
          getReg7().decrement(getReg2());
          // # OF DIGITS IN POSITION VALUE
          getReg0().setByteString(_LOW(2) + storage.getDigits().getByteString(0, 2));
          // ADD LENGTH TO PER LINE COUNT
          getReg10().increment(getReg2());
          // ADD # DIGITS TO PER LINE COUNT
          getReg10().increment(getReg0());
          // BUMP OFFSET TO POS ON LINE
          getReg5().increment(getReg2());
          // ADD # DIGITS TO OFFSET
          getReg5().increment(getReg0());
          // ASM <Code_block_id:BEGLIN>
          // ASM:01160 BEGLIN   NOP   LCRLOOP
          // ASM:01161          MVI   BEGLIN+1,X'F0'          SHUT DOOR
          // ASM:01162          LH    R15,LDIG                # DIGITS LEFT OF 29
          // ASM:01163          SR    R5,R15
          // ASM:01164          SPACE
          // ASM:01165          B     LCRLOOP
          if (beglin) {
            // SHUT DOOR
            continue lcrloop;
          }
          beglin = true;
          // # DIGITS LEFT OF 29
          getReg15().setByteString(_LOW(2) + storage.getLdig().getByteString(0, 2));
          getReg5().decrement(getReg15());
          continue lcrloop;
          // ASM <Code_block_id:LCREND>
          // ASM:01166          SPACE 2
          // ASM:01167 LCREND   EQU   *
          // ASM:01168          CP    LINECT,=P'55'           ROOM FOR POSSIBLE 5 LINES?
          // ASM:01169          BL    LLAST                   YES
          // ASM:01170          BAL   R4,HEADERS              NO,  START NEW PAGE
        } //lcrloop
      } //lcrend
      // ROOM FOR POSSIBLE 5 LINES?
      if (storage.getLinect().getNumeric() < 55) {
        // YES
        break llast;
      }
      // NO, START NEW PAGE
      headers();
      // ASM <Code_block_id:LLAST>
      // ASM:01171          SPACE
      // ASM:01172 LLAST    EQU   *
      // ASM:01173          LR    R7,R5
      // ASM:01174          BCTR  R7,0
      // ASM:01175          BAL   R4,PRTLINES             YES, PRINT LINES
      // ASM:01176          MVI   LGUIDE2,C'.'
      // ASM:01177          MVC   LGUIDE2+1(101),LGUIDE2
      // ASM:01178          L     R5,SAVER5
      // ASM:01179          BR    R5                      RETURN
    } //llast
    getReg7().setRegister(getReg5());
    // getReg7().decrement(1);
    // YES, PRINT LINES
    prtlines();
    storage.getLguide().getLguide2AtIndex(0).set(".");
    storage.getLguide().getLguide2().set(NAsmStrings.repeatCharacter(storage.getLguide().getLguide2().getString().charAt(1), 101));
    getReg5().load(storage.getSaver5());
    // RETURN
    return;
  }

  /**
   * Method ADITTO.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void aditto() throws NAsmException {
    // ASM <Code_block_id:ADITTO>
    // ASM:01180          EJECT
    // ASM:01181 *--------------------------------------------------------------------*
    // ASM:01182 *        L C R   A U D I T   D I T T O    R O U T I N E              *
    // ASM:01183 *--------------------------------------------------------------------*
    // ASM:01184 ADITTO   EQU  *
    // ASM:01185          ST    R5,SAVER5
    // ASM:01186          SPACE
    // ASM:01187          TM    MODE,STANDARD           OSCR DITTO?
    // ASM:01188          BO    GO_ADITT                NO
    // ASM:01189          TM    MODE2,SEL_OTH           SELECT NON_HDR ON?
    // ASM:01190          BNO   USER4                   NO, ERROR
    // ASM:01191          SPACE
    // ASM:01192          L     R14,SAVER9              LOAD ADDR OF SEG DISPLACEMENT
    // ASM:01193          LH    R1,0(R14)               GET SEGMENT DISPLACEMENT
    // ASM:01194          AR    R1,R8                   ADD RECORD ADDRESS
    // ASM:01195          LR    R8,R1                   ADDR OF SEGMENT
    // ASM:01196          LH    R7,3(R8)                SEGMENT LENGTH
    // ASM:01197          S     R7,=F'5'                SKIP 5 BYTE DESC/LEN
    // ASM:01198          A     R8,=F'5'                SKIP 5 BYTE DESC/LEN
    //--------------------------------------------------------------------*
    // L C R   A U D I T   D I T T O    R O U T I N E              *
    //--------------------------------------------------------------------*
    goAditt: {
      storage.getSaver5().set(getReg5());
      // OSCR DITTO?
      // NO
      if (storage.getMode().testUnderMask(PsdittoConstants.STANDARD) == PsdittoConstants.STANDARD) {
        break goAditt;
      }
      // SELECT NON_HDR ON?
      if (storage.getMode2().testUnderMask(PsdittoConstants.SEL_OTH) != PsdittoConstants.SEL_OTH) {
        // NO, ERROR
        user4();
      }
      // LOAD ADDR OF SEG DISPLACEMENT
      getReg14().load(storage.getSaver9());
      // GET SEGMENT DISPLACEMENT
      getReg1().setByteString(_LOW(2) + getReg14().getByteString(0, 2));
      // ADD RECORD ADDRESS
      getReg1().increment(getReg8());
      // ADDR OF SEGMENT
      getReg8().setRegister(getReg1());
      // SEGMENT LENGTH
      getReg7().setByteString(_LOW(2) + getReg8().getByteString(3, 2));
      // SKIP 5 BYTE DESC/LEN
      getReg7().subtractAddress(5);
      // SKIP 5 BYTE DESC/LEN
      getReg8().addAddress(5);
      // ASM <Code_block_id:GO_ADITT>
      // ASM:01199 GO_ADITT EQU   *
      // ASM:01200          MVC   LCRWORK,LCRADDR         ADDR OF GETMAIN TABLE OF FLDS
      // ASM:01201          MVI   CCON,C'-'               SKIP 2 LINES FOR NEW RECORD
      // ASM:01202          AP    LINECT,PKONE            COUNT LINE FROM PRINT CONTROL
      // ASM:01203          SPACE
      // ASM:01204          XR    R6,R6                   SET CUMULATIVE LENGTH TO ZERO
      // ASM:01205          MVI   ASKIP+1,X'00'
    } //goAditt
    // ADDR OF GETMAIN TABLE OF FLDS
    storage.getLcrwork().setByteString(storage.getLcraddr().getByteString());
    // SKIP 2 LINES FOR NEW RECORD
    storage.getCharline().getCcon().set("-");
    // COUNT LINE FROM PRINT CONTROL
    storage.getLinect().set(storage.getLinect().getNumeric() + storage.getPkone().getNumeric());
    // SET CUMULATIVE LENGTH TO ZERO
    getReg6().set(0);
    askip = false;
    // ASM <Code_block_id:ALOOP>
    // ASM:01206 ALOOP    EQU   *
    // ASM:01207          LTR   R7,R7                   END OF RECORD?
    // ASM:01208          BNP   ALCREND                 YES
    // ASM:01209          SPACE
    // ASM:01210          L     R1,LCRWORK              ADDR OF LCR FIELDS TABLE
    // ASM:01211          CLI   0(R1),X'FF'             END OF TABLE?
    // ASM:01212          BE    USER3                   YES, ERROR
    // ASM:01213          PACK  WRKDBL,0(2,R1)          GET NEW FIELD
    // ASM:01214          SPACE
    // ASM:01215          CP    LINECT,=P'55'           ROOM FOR POSSIBLE 5 LINES?
    // ASM:01216          BL    ASKIP                   YES
    // ASM:01217          BAL   R4,HEADERS              NO,  START NEW PAGE
    // ASM:01218          SPACE
    // ASM:01219 ASKIP    TS    ASKIP+1
    // ASM:01220          BZ    AFIRST
    // ASM:01221          ST    R7,SAVER7
    // ASM:01222          LR    R7,R5
    // ASM:01223          BCTR  R7,0
    // ASM:01224          BAL   R4,PRTLINES             YES, PRINT LINES
    // ASM:01225          MVI   LGUIDE2,C'.'
    // ASM:01226          MVC   LGUIDE2+1(101),LGUIDE2
    // ASM:01227          L     R7,SAVER7
    alast: {
      alcrend: {
        aloop: while (true) {
          afirst: {
            askip: {
              // END OF RECORD?
              if (!getReg7().greater(_ZERO)) {
                // YES
                break alcrend;
              }
              // ADDR OF LCR FIELDS TABLE
              getReg1().load(storage.getLcrwork());
              // END OF TABLE?
              if (getReg1().getByteString(0, 1).equals(_HIGH(1))) {
                // YES, ERROR
                user3();
              }
              // GET NEW FIELD
              storage.getWrkdbl().set(getReg1().getNumericAtRange(0, 2));
              // ROOM FOR POSSIBLE 5 LINES?
              if (storage.getLinect().getNumeric() < 55) {
                // YES
                break askip;
              }
              // NO, START NEW PAGE
              headers();
            } //askip
            if (!askip) {
              askip = true;
              break afirst;
            } else {
              askip = true;
            }
            storage.getSaver7().set(getReg7());
            getReg7().setRegister(getReg5());
            // getReg7().decrement(1);
            // YES, PRINT LINES
            prtlines();
            storage.getLguide().getLguide2AtIndex(0).set(".");
            storage.getLguide().getLguide2().set(NAsmStrings.repeatCharacter(storage.getLguide().getLguide2().getString().charAt(1), 101));
            getReg7().load(storage.getSaver7());
            // ASM <Code_block_id:AFIRST>
            // ASM:01228 AFIRST   EQU   *
            // ASM:01229          LA    R5,27                   RESET OFFSET VALUE
            // ASM:01230          LR    R14,R6                  GET CUMULATIVE LENGTH
            // ASM:01231          AH    R14,=H'1'               ACTUAL POSITION
            // ASM:01232          LR    R1,R5                   OFFSET TO FIELD START LOCATION
            // ASM:01233          XR    R15,R15
            // ASM:01234          CH    R14,=H'10'              POSITION < 10?
            // ASM:01235          BL    ASTORDIG                 YES
            // ASM:01236          BCTR  R1,0                    SUBTRACT 1
            // ASM:01237          AH    R15,=H'1'
            // ASM:01238          CH    R14,=H'100'             POSITION < 100?
            // ASM:01239          BL    ASTORDIG                 YES
            // ASM:01240          BCTR  R1,0                    SUBTRACT 1
            // ASM:01241          AH    R15,=H'1'
            // ASM:01242          CH    R14,=H'1000'            POSITION < 1000?
            // ASM:01243          BL    ASTORDIG                 YES
            // ASM:01244          BCTR  R1,0                    SUBTRACT 1
            // ASM:01245          AH    R15,=H'1'
          } //afirst
            // RESET OFFSET VALUE
          getReg5().set(27);
          // GET CUMULATIVE LENGTH
          getReg14().setRegister(getReg6());
          // ACTUAL POSITION
          getReg14().addAddress(1);
          // OFFSET TO FIELD START LOCATION
          getReg1().setRegister(getReg5());
          getReg15().set(0);
          astordig: {
            // POSITION < 10?
            if (getReg14().less(10, 2, 2)) {
              // YES
              break astordig;
            }
            // SUBTRACT 1
            getReg1().decrement(1);
            getReg15().addAddress(1);
            // POSITION < 100?
            if (getReg14().less(100, 2, 2)) {
              // YES
              break astordig;
            }
            // SUBTRACT 1
            getReg1().decrement(1);
            getReg15().addAddress(1);
            // POSITION < 1000?
            if (getReg14().less(1000, 2, 2)) {
              // YES
              break astordig;
            }
            // SUBTRACT 1
            getReg1().decrement(1);
            getReg15().addAddress(1);
            // ASM <Code_block_id:ASTORDIG>
            // ASM:01246 ASTORDIG EQU   *
            // ASM:01247          STH   R15,LDIG                # OF DIGITS LEFT OF POS 29
            // ASM:01248          MVI   ABEGLIN+1,X'00'         OPEN DOOR
          } //astordig
          // # OF DIGITS LEFT OF POS 29
          storage.getLdig().setByteString(getReg15().getByteString(2, 2));
          // OPEN DOOR
          abeglin = false;
          // ASM <Code_block_id:A_POS_FLD>
          // ASM:01249          SPACE
          // ASM:01250 A_POS_FLD  EQU   *
          // ASM:01251          BAL   R11,POSITION            PUT POSITION TO PRINT LINE
          // ASM:01252          SPACE
          // ASM:01253          CVB   R2,WRKDBL               BINARY LENGTH OF CURRENT FIELD
          // ASM:01254          BAL   R11,FIELD               PUT FIELD TO PRINT LINE
          // PUT POSITION TO PRINT LINE
          position();
          // BINARY LENGTH OF CURRENT FIELD
          getReg2().set(storage.getWrkdbl().getNumeric());
          // PUT FIELD TO PRINT LINE
          field();
          // ASM <Code_block_id:ALITERAL>
          // ASM:01255          SPACE
          // ASM:01256 ALITERAL EQU   *
          // ASM:01257          LA    R14,CHARLINE+45         LOAD ADDRESS OF CHARLINE + SOME
          // ASM:01258          LH    R1,=H'78'               MAX LENGTH OF LITERAL
          // ASM:01259          CH    R2,=H'17'               SIZE OF BUFFER FOR FIELD
          // ASM:01260          BL    GOLIT                   FIELD NOT BIGGER THAN 17
          // ASM:01261          LR    R15,R2                  GET FLD LEN
          // ASM:01262          SH    R15,=H'15'              SUB SOME, GETTING OVERFLOW
          // ASM:01263          AR    R14,R15                 BUMP BY LENGTH OF OVERFLOW
          // ASM:01264          AH    R1,=H'17'               ADD LENGTH OF 'EXTRA SPACE'
          // ASM:01265          SR    R1,R2                   SUB FLD LEN, GETTING OVERFLOW
          // LOAD ADDRESS OF CHARLINE + SOME
          getReg14().setAddress(storage.getCharline(), 45);
          // MAX LENGTH OF LITERAL
          getReg1().set(78);
          golit: {
            // SIZE OF BUFFER FOR FIELD
            if (getReg2().less(17, 2, 2)) {
              // FIELD NOT BIGGER THAN 17
              break golit;
            }
            // GET FLD LEN
            getReg15().setRegister(getReg2());
            // SUB SOME, GETTING OVERFLOW
            getReg15().subtractAddress(15);
            // BUMP BY LENGTH OF OVERFLOW
            getReg14().increment(getReg15());
            // ADD LENGTH OF 'EXTRA SPACE'
            getReg1().addAddress(17);
            // SUB FLD LEN, GETTING OVERFLOW
            getReg1().decrement(getReg2());
            // ASM <Code_block_id:GOLIT>
            // ASM:01266 GOLIT    EQU   *
            // ASM:01267          BCTR  R1,0                    MACHINE LENGTH
            // ASM:01268          STC   R1,MOVELIT+1            STORE LENGTH
            // ASM:01269          L     R10,LCRWORK             LOAD ADDR OF LCRFLDS CARD
            // ASM:01270          LA    R10,2(R10)              BUMP TO LITERAL
            // ASM:01271 MOVELIT  MVC   0(0,R14),0(R10)         MOVE LITERAL TO CHARLINE
            // ASM:01272          LA    R10,78(R10)             BUMP TO NEXT CARD
            // ASM:01273          ST    R10,LCRWORK             SAVE ADDR OF NEXT LCRFLDS CARD
            // ASM:01274          SPACE
            // ASM:01275          AR    R6,R2                   ADD TO CUMULATIVE LENGTH
            // ASM:01276          AR    R8,R2                   BUMP PTR TO RECORD BY FLD LEN
            // ASM:01277          SR    R7,R2                   DECREMENT RECORD LENGTH VALUE
            // ASM:01278          LH    R0,DIGITS               # OF DIGITS IN POSITION VALUE
            // ASM:01279          AR    R5,R2                   BUMP OFFSET TO POS ON LINE
            // ASM:01280          AR    R5,R0                   ADD # DIGITS TO OFFSET
          } //golit
          // MACHINE LENGTH
          // getReg1().decrement(1);
          // STORE LENGTH
          // getReg14().set(getReg10().getString().substring(0, getReg1().getInt()));
          // LOAD ADDR OF LCRFLDS CARD
          getReg10().load(storage.getLcrwork());
          // BUMP TO LITERAL
          getReg10().increment(2);
          // MOVE LITERAL TO CHARLINE
          getReg14().set(getReg10().getString().substring(0, getReg1().getInt()));
          // BUMP TO NEXT CARD
          getReg10().increment(78);
          // SAVE ADDR OF NEXT LCRFLDS CARD
          storage.getLcrwork().set(getReg10());
          // ADD TO CUMULATIVE LENGTH
          getReg6().increment(getReg2());
          // BUMP PTR TO RECORD BY FLD LEN
          getReg8().increment(getReg2());
          // DECREMENT RECORD LENGTH VALUE
          getReg7().decrement(getReg2());
          // # OF DIGITS IN POSITION VALUE
          getReg0().setByteString(_LOW(2) + storage.getDigits().getByteString(0, 2));
          // BUMP OFFSET TO POS ON LINE
          getReg5().increment(getReg2());
          // ADD # DIGITS TO OFFSET
          getReg5().increment(getReg0());
          // ASM <Code_block_id:ABEGLIN>
          // ASM:01281 ABEGLIN  NOP   LCRLOOP
          // ASM:01282          MVI   ABEGLIN+1,X'F0'          SHUT DOOR
          // ASM:01283          LH    R15,LDIG                # DIGITS LEFT OF 29
          // ASM:01284          SR    R5,R15
          // ASM:01285          SPACE
          // ASM:01286          B     ALOOP
          if (abeglin) {
            //continue lcrloop;
            continue aloop;
          }
          // SHUT DOOR
          abeglin = false;
          // # DIGITS LEFT OF 29
          getReg15().setByteString(_LOW(2) + storage.getLdig().getByteString(0, 2));
          getReg5().decrement(getReg15());
          continue aloop;
          // ASM <Code_block_id:ALCREND>
          // ASM:01287          SPACE 2
          // ASM:01288 ALCREND  EQU   *
          // ASM:01289          CP    LINECT,=P'55'           ROOM FOR POSSIBLE 5 LINES?
          // ASM:01290          BL    ALAST                   YES
          // ASM:01291          BAL   R4,HEADERS              NO,  START NEW PAGE
        } //aloop
      } //alcrend
      // ROOM FOR POSSIBLE 5 LINES?
      if (storage.getLinect().getNumeric() < 55) {
        // YES
        break alast;
      }
      // NO, START NEW PAGE
      headers();
      // ASM <Code_block_id:ALAST>
      // ASM:01292          SPACE
      // ASM:01293 ALAST    EQU   *
      // ASM:01294          LR    R7,R5
      // ASM:01295          BCTR  R7,0
      // ASM:01296          BAL   R4,PRTLINES             YES, PRINT LINES
      // ASM:01297          MVI   LGUIDE2,C'.'
      // ASM:01298          MVC   LGUIDE2+1(101),LGUIDE2
      // ASM:01299          SPACE
      // ASM:01300          L     R5,SAVER5
      // ASM:01301          BR    R5                      RETURN
    } //alast
    getReg7().setRegister(getReg5());
    //getReg7().decrement(1);
    // YES, PRINT LINES
    prtlines();
    storage.getLguide().getLguide2AtIndex(0).set(".");
    storage.getLguide().getLguide2().set(NAsmStrings.repeatCharacter(storage.getLguide().getLguide2().getString().charAt(0), 101));
    getReg5().load(storage.getSaver5());
    // RETURN
  }

  /**
   * Method POSITION.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void position() throws NAsmException {
    // ASM <Code_block_id:POSITION>
    // ASM:01302          EJECT
    // ASM:01303 *--------------------------------------------------------------------*
    // ASM:01304 *                 P O S I T I O N    R O U T I N E                   *
    // ASM:01305 *--------------------------------------------------------------------*
    // ASM:01306 POSITION EQU   *
    // ASM:01307          LA    R2,LGUIDE               POINT TO LCR GUIDE LINE
    // ASM:01308          AR    R2,R1                   ADD OFFSET TO NEXT POSITION
    // ASM:01309          MVI   0(R2),C' '              PUT BLANK BETWEEN FIELDS
    // ASM:01310          LA    R2,1(R2)                BUMP 1 BYTE
    // ASM:01311          LR    R14,R6                  GET CUMULATIVE LENGTH
    // ASM:01312          AH    R14,=H'1'               ACTUAL POSITION
    // ASM:01313          CVD   R14,WRKDBL2             CONVERT POSITION TO DECIMAL
    // ASM:01314          UNPK  CHARWORK,WRKDBL2        CONVERT DECIMAL TO CHARACTER
    // ASM:01315          OI    CHARWORK+3,X'F0'
    // ASM:01316 *
    // ASM:01317          LA    R3,CHARWORK             GET ADDR TO POSITION IN CHAR
    // ASM:01318          LA    R15,3                   DIGITS=4 (MACHINE LENGTH)
    // ASM:01319          CH    R14,=H'1000'            POSITION >= 1000?
    // ASM:01320          BNL   POSMOVE                 YES
    // ASM:01321          BCTR  R15,0                   SUBTRACT 1
    // ASM:01322          LA    R3,1(R3)                BUMP PAST A ZERO
    // ASM:01323          CH    R14,=H'100'             POSITION >= 100?
    // ASM:01324          BNL   POSMOVE                 YES
    // ASM:01325          BCTR  R15,0                   SUBTRACT 1
    // ASM:01326          LA    R3,1(R3)                BUMP PAST A ZERO
    // ASM:01327          CH    R14,=H'10'              POSITION >= 10?
    // ASM:01328          BNL   POSMOVE                 YES
    // ASM:01329          BCTR  R15,0                   SUBTRACT 1
    // ASM:01330          LA    R3,1(R3)                BUMP PAST A ZERO
    //--------------------------------------------------------------------*
    // P O S I T I O N    R O U T I N E                   *
    //--------------------------------------------------------------------*
    // POINT TO LCR GUIDE LINE
    getReg2().setAddress(storage.getLguide());
    // ADD OFFSET TO NEXT POSITION
    getReg2().increment(getReg1());
    // PUT BLANK BETWEEN FIELDS
    getReg2().set(_SPACE(1));
    // BUMP 1 BYTE
    getReg2().increment(1);
    // GET CUMULATIVE LENGTH
    getReg14().setRegister(getReg6());
    // ACTUAL POSITION
    getReg14().addAddress(1);
    // CONVERT POSITION TO DECIMAL
    storage.getWrkdbl2().setAsPacked(getReg14());
    // CONVERT DECIMAL TO CHARACTER
    //storage.getCharwork().set(Strings.formatZeroes(Strings.LeftTrim(storage.getWrkdbl2().getString(), PsdittoStorage.Len.CHARWORK), PsdittoStorage.Len.CHARWORK));
    storage.getCharwork().set(storage.getWrkdbl2().convertPackedToZoned(PsdittoStorage.Len.CHARWORK));
    // ASM:01314  [OI right after UNPK is ignored] [ignored]          OI    CHARWORK+3,X'F0'
    //
    // GET ADDR TO POSITION IN CHAR
    getReg3().setAddress(storage.getCharwork());
    // DIGITS=4 (MACHINE LENGTH)
    getReg15().set(3);
    posmove: {
      // POSITION >= 1000?
      if (!getReg14().less(1000, 2, 2)) {
        // YES
        break posmove;
      }
      // SUBTRACT 1
      getReg15().decrement(1);
      // BUMP PAST A ZERO
      getReg3().increment(1);
      // POSITION >= 100?
      if (!getReg14().less(100, 2, 2)) {
        // YES
        break posmove;
      }
      // SUBTRACT 1
      getReg15().decrement(1);
      // BUMP PAST A ZERO
      getReg3().increment(1);
      // POSITION >= 10?
      if (!getReg14().less(10, 2, 2)) {
        // YES
        break posmove;
      }
      // SUBTRACT 1
      getReg15().decrement(1);
      // BUMP PAST A ZERO
      getReg3().increment(1);
      // ASM <Code_block_id:POSMOVE>
      // ASM:01331 POSMOVE  EQU   *
      // ASM:01332          EX    R15,XPOSMOVE
      // ASM:01333          AH    R15,=H'1'               CONVERT FROM MACHINE LENGTH
      // ASM:01334          STH   R15,DIGITS              # OF DIGITS IN POSITION VALUE
      // ASM:01335          AR    R1,R15
      // ASM:01336          SPACE
      // ASM:01337          BR    R11                     RETURN
    } //posmove
    getReg2().set(getReg3().getString().substring(0, getReg15().getInt() + 1));
    // CONVERT FROM MACHINE LENGTH
    getReg15().addAddress(1);
    // # OF DIGITS IN POSITION VALUE
    storage.getDigits().setByteString(getReg15().getByteString(2, 2));
    getReg1().increment(getReg15());
    // RETURN
    return;
  }

  /**
   * Method FIELD.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void field() throws NAsmException {
    // ASM <Code_block_id:FIELD>
    // ASM:01338          EJECT
    // ASM:01339 *--------------------------------------------------------------------*
    // ASM:01340 *                    F I E L D    R O U T I N E                      *
    // ASM:01341 *--------------------------------------------------------------------*
    // ASM:01342 FIELD    EQU   *
    // ASM:01343          BCTR  R2,0                    MODIFY LENGTH FOR MACHINE INST.
    // ASM:01344          STC   R2,FMOVEC+1
    // ASM:01345          STC   R2,FBLNK_NS+1
    // ASM:01346          STC   R2,FMOVEZ+1
    // ASM:01347          STC   R2,FAPNDTR+1
    // ASM:01348          STC   R2,FMOVEN+1
    // ASM:01349          STC   R2,FMOVEF+1
    // ASM:01350 *
    // ASM:01351          LR    R15,R1
    // ASM:01352          LA    R14,CHARLINE
    // ASM:01353          AR    R14,R15
    // ASM:01354 FMOVEC   MVC   0(0,R14),0(R8)           MOVE IN THE CHARACTERS
    // ASM:01355 FBLNK_NS TR    0(0,R14),STNDCHAR        BLANK OUT NON-STANDARD CHAR
    // ASM:01356          LA    R14,ZONELINE
    // ASM:01357          AR    R14,R15
    // ASM:01358 FMOVEZ   MVZ   0(0,R14),0(R8)           MOVE ZONES TO OUTPUT
    // ASM:01359 FAPNDTR  TR    0(0,R14),TRANS           MAKE THEM PRINTABLE
    // ASM:01360          LA    R14,NUMRLINE
    // ASM:01361          AR    R14,R15
    // ASM:01362 FMOVEN   MVN   0(0,R14),0(R8)           MOVE NUM PART TO OUTPUT
    // ASM:01363 FMOVEF   TR    0(0,R14),TRANS           MAKE THEM PRINTABLE
    // ASM:01364          SPACE
    // ASM:01365          LA    R2,1(R2)                UNDO MACHINE LENGTH
    //--------------------------------------------------------------------*
    // F I E L D    R O U T I N E                      *
    //--------------------------------------------------------------------*
    // MODIFY LENGTH FOR MACHINE INST.
    // getReg2().decrement(1);
    //
    getReg15().setRegister(getReg1());
    getReg14().setAddress(storage.getCharline());
    getReg14().increment(getReg15());
    // MOVE IN THE CHARACTERS
    getReg14().set(getReg8().getString().substring(0, getReg2().getInt()));
    // BLANK OUT NON-STANDARD CHAR
    getReg14().translate(storage.getStndchar(), getReg2().getInt());
    getReg14().setAddress(storage.getZoneline());
    getReg14().increment(getReg15());
    // MOVE ZONES TO OUTPUT
    getReg14().setHighBits(getReg8().getString().substring(0, getReg2().getInt()));
    // MAKE THEM PRINTABLE
    getReg14().translate(storage.getTrans(), getReg2().getInt());
    getReg14().setAddress(storage.getNumrline());
    getReg14().increment(getReg15());
    // MOVE NUM PART TO OUTPUT
    getReg14().setLowBits(getReg8().getString().substring(0, getReg2().getInt()));
    // MAKE THEM PRINTABLE
    getReg14().translate(storage.getTrans(), getReg2().getInt());
    // UNDO MACHINE LENGTH
    // getReg2().increment(1);
    // ASM <Code_block_id:FEXIT>
    // ASM:01366 FEXIT    EQU   *
    // ASM:01367          BR    R11
    return;
    // ASM <Code_block_id:FIELD>
    // ASM:01368          EJECT
    // ASM:01369 *---------------------------------------------------------------------*
    // ASM:01370 *   WORKING STORAGE FOR LCRDITTO SUB-PGM
    // ASM:01371 *---------------------------------------------------------------------*
    // ASM:01372          SPACE
    // ASM:01373 XPOSMOVE MVC   0(0,R2),0(R3)           PUT BLANK BETWEEN FIELDS
    // ASM:01382          SPACE 2
    // ASM:01383          DROP  R9
    // ASM:01383 LCRDITTO CSECT
    //---------------------------------------------------------------------*
    // WORKING STORAGE FOR LCRDITTO SUB-PGM
    //---------------------------------------------------------------------*
    //*xposmove:
    // PUT BLANK BETWEEN FIELDS
    // ASM:01383 [converted_at_execute]  [XPOSMOVE  MVC   0(0,R2),0(R3)]
    // ASM:01383 [ignored] LCRDITTO CSECT
  }

  /**
   * Method SETUP.
   *
   * @throws NAsmException if there is a specific issue
   */
  private String setup() throws NAsmException {
    // ASM <Code_block_id:SETUP>
    // ASM:01385          EJECT
    // ASM:01386 ***********************************************************************
    // ASM:01387 *                                                                     *
    // ASM:01388 *                        SUB-PROGRAM                                  *
    // ASM:01389 *                                                                     *
    // ASM:01390 *    SETUP ROUTINES FOR CALLED, STANDALONE, SELECT, AND LCR OPTIONS   *
    // ASM:01391 *                                                                     *
    // ASM:01392 ***********************************************************************
    // ASM:01393 SETUP    CSECT
    // ASM:01394          STM   R2,R14,SAVE13F          SAVE REGISTERS FROM MAIN
    // ASM:01395          LR    R9,R15
    // ASM:01396          USING SETUP,R9                USE R9 AS BASE FOR THIS SUB
    // ASM:01397          SPACE 2
    // ASM:01398          CLC   0(6,R1),=C'CALLED'      IS THIS A CALLED PROGRAM
    // ASM:01399          BNE   STAND                   NO
    //*********************************************************************
    // *
    // SUB-PROGRAM                                  *
    // *
    // SETUP ROUTINES FOR CALLED, STANDALONE, SELECT, AND LCR OPTIONS   *
    // *
    //*********************************************************************
    // ASM:01392 [ignored] SETUP    CSECT
    // SAVE REGISTERS FROM MAIN
    NAsmEnvironment.multipleStore(REG_2, REG_14, storage.getSave13f());
    getReg9().setRegister(getReg15());
    // USE R9 AS BASE FOR THIS SUB
    cklcr: {
      stand: {
        // IS THIS A CALLED PROGRAM
        if (!getReg1().getString(0, 6).equals("CALLED")) {
          // NO
          break stand;
        }
        // ASM <Code_block_id:CALL>
        // ASM:01400          SPACE
        // ASM:01401 CALL     EQU   *
        // ASM:01402          BAL   R5,BEGIN_CALL           BEGIN ROUTINE FOR CALLED MODE
        // ASM:01403          B     CKLCR
        // BEGIN ROUTINE FOR CALLED MODE
        beginCall();
        break cklcr;
        // ASM <Code_block_id:STAND>
        // ASM:01404          SPACE
        // ASM:01405 STAND    EQU   *
        // ASM:01406          BAL   R5,BEGIN_STAND          BEGIN ROUTINE FOR STANDALONE
      } //stand
      // BEGIN ROUTINE FOR STANDALONE
      beginStand();
      // ASM <Code_block_id:CKLCR>
      // ASM:01407          SPACE 2
      // ASM:01408 CKLCR    EQU   *
      // ASM:01409          OPEN  LCRFLDS                 INSTREAM DATA
      // ASM:01410          TM    LCRFLDS+48,16           IS LCRFLDS DD IN JCL?
      // ASM:01411          BNO   BANPAGE                 NO
      // ASM:01412          TM    MODE,STANDARD           STANDARD NON-OSCR?
      // ASM:01413          BNO   GOLCR                   NO
      // ASM:01414          OI    MODE2,LCRSEG            TURN LCR BIT ON
    } //cklcr
      // INSTREAM DATA
    lcrflds.openInput();
    banpage: {
      // IS LCRFLDS DD IN JCL?
      if (!lcrflds.isOpened()) {
        // NO
        break banpage;
      }
      golcr: {
        // STANDARD NON-OSCR?
        // NO
        if (storage.getMode().testUnderMask(PsdittoConstants.STANDARD) != PsdittoConstants.STANDARD) {
          break golcr;
        }
        // TURN LCR BIT ON
        storage.getMode2().or(PsdittoConstants.LCRSEG);
        // ASM <Code_block_id:GOLCR>
        // ASM:01415 GOLCR    EQU   *
        // ASM:01416          BAL   R5,SETUPLCR             LCR OPTION SETUP ROUTINE
      } //golcr
      // LCR OPTION SETUP ROUTINE
      setuplcr();
      // ASM <Code_block_id:BANPAGE>
      // ASM:01417          SPACE 2
      // ASM:01418 BANPAGE  EQU   *
      // ASM:01419          BAL   R4,BANNER               PRINT BANNER PAGE FOR STANDALONE
    } //banpage
    // PRINT BANNER PAGE FOR STANDALONE
    banner();
    // ASM <Code_block_id:SEGSELCT>
    // ASM:01420          SPACE 2
    // ASM:01421 SEGSELCT EQU   *
    // ASM:01422          OPEN  SELECT                  INSTREAM DATA
    // ASM:01423          TM    SELECT+48,16            IS SELECT DD IN JCL?
    // ASM:01424          BNO   RET002                  NO
    // ASM:01425          BAL   R5,SELCTRTN             SEGMENT SELECT ROUTINE
    // INSTREAM DATA
    select.openInput();
    ret002: {
      // IS SELECT DD IN JCL?
      if (!select.isOpened()) {
        // NO
        break ret002;
      }
      // SEGMENT SELECT ROUTINE
      String breakLabel = selctrtn();
      if (breakLabel != null)
        return breakLabel;
      // ASM <Code_block_id:RET002>
      // ASM:01426          SPACE 2
      // ASM:01427 RET002   EQU   *
      // ASM:01428          LM    R2,R14,SAVE13F          RESTORE REGISTERS FROM MAIN
      // ASM:01429          BR    R14                     RETURN TO MAIN
    } //ret002
    // RESTORE REGISTERS FROM MAIN
    NAsmEnvironment.multipleLoad(REG_2, REG_14, storage.getSave13f());
    // RETURN TO MAIN
    return null;
  }

  /**
   * Method BEGIN_CALL.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void beginCall() throws NAsmException {
    // ASM <Code_block_id:BEGIN_CALL>
    // ASM:01430          EJECT
    // ASM:01431 *--------------------------------------------------------------------*
    // ASM:01432 *    B E G I N    R O U T I N E    F O R    C A L L E D    M O D E   *
    // ASM:01433 *--------------------------------------------------------------------*
    // ASM:01434 BEGIN_CALL     EQU  *
    // ASM:01435          SPACE
    // ASM:01436          L     R2,SAVER2               RESTORE CALLERS REGISTER
    // ASM:01437          SPACE
    // ASM:01438          ZAP   RECCT,PKZERO            CLEAR RECORDS COUNTER
    // ASM:01439          ZAP   PAGECTR,PKZERO          CLEAR PAGE COUNTER
    // ASM:01440          ZAP   LINECT,=P'66'
    // ASM:01441          MVI   FIRST+1,X'00'
    // ASM:01442          SPACE
    // ASM:01443          OI    MODE,CALLED             SET 'CALLED MODULE' BIT ON
    // ASM:01444          OI    MODE,ALL                SET 'PRINT ALL RECORDS' BIT ON
    // ASM:01445          SPACE
    // ASM:01446          L     R14,0(R2)                LOAD CALLERS JFCB
    // ASM:01447          MVC   INJFCB(176),0(R14)       MOVE JFCB INFO
    // ASM:01448          SPACE
    // ASM:01449          L     R1,SAVER1
    // ASM:01450          CLC   7(10,R1),=C'PS HEADER=' CHECK OSCR HEADER PARM
    // ASM:01451          BNE   USER1                   NO, ABEND
    // ASM:01452          CLI   17(R1),C'Y'             PRINTING OSCR RECORDS
    // ASM:01453          BE    LOADJ                   YES
    // ASM:01454          OI    MODE,STANDARD           NO, SET 'STANDARD DITTO' BIT ON
    //--------------------------------------------------------------------*
    // B E G I N    R O U T I N E    F O R    C A L L E D    M O D E   *
    //--------------------------------------------------------------------*
    // RESTORE CALLERS REGISTER
    getReg2().load(storage.getSaver2());
    // CLEAR RECORDS COUNTER
    storage.getRecct().set(storage.getPkzero().getNumeric());
    // CLEAR PAGE COUNTER
    storage.getPagectr().set(storage.getPkzero().getNumeric());
    storage.getLinect().set(66);
    first = false;
    // SET 'CALLED MODULE' BIT ON
    storage.getMode().or(PsdittoConstants.CALLED);
    // SET 'PRINT ALL RECORDS' BIT ON
    storage.getMode().or(PsdittoConstants.ALL);
    // LOAD CALLERS JFCB
    getReg14().load(getReg2());
    // MOVE JFCB INFO
    storage.getInjfcb().setByteString(getReg14().getByteString(0, 176));
    getReg1().load(storage.getSaver1());
    // CHECK OSCR HEADER PARM
    if (!getReg1().getString(7, 10).equals("PS HEADER=")) {
      // NO, ABEND
      user1();
    }
    loadj: {
      // PRINTING OSCR RECORDS
      if (getReg1().getString(17, 1).equals("Y")) {
        // YES
        break loadj;
      }
      // NO, SET 'STANDARD DITTO' BIT ON
      storage.getMode().or(PsdittoConstants.STANDARD);
      // ASM <Code_block_id:LOADJ>
      // ASM:01455          SPACE
      // ASM:01456 LOADJ    EQU   *
      // ASM:01457          BAL   R4,LOADJFCB             LOAD INFO FROM JFCB
      // ASM:01458          SPACE
      // ASM:01459          BR    R5                      RETURN
    } //loadj
    // LOAD INFO FROM JFCB
    loadjfcb();
    // RETURN
    return;
  }

  /**
   * Method BEGIN_STAND.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void beginStand() throws NAsmException {
    // ASM <Code_block_id:BEGIN_STAND>
    // ASM:01460          EJECT
    // ASM:01461 *--------------------------------------------------------------------*
    // ASM:01462 *    B E G I N    R O U T I N E    F O R    S T A N D A L O N E      *
    // ASM:01463 *--------------------------------------------------------------------*
    // ASM:01464 BEGIN_STAND    EQU  *
    // ASM:01465          SPACE
    // ASM:01466          CLC   2(8,R1),=C'NUMRECS='    NUMBER OF RECORDS TO DITTO
    // ASM:01467          BNE   USER1
    // ASM:01468          CLC   10(3,R1),=C'ALL'
    // ASM:01469          BE    ZAPHI
    // ASM:01470          CLC   10(5,R1),=C'99999'
    // ASM:01471          BH    USER1
    // ASM:01472          CLC   10(5,R1),=C'00000'
    // ASM:01473          BL    USER1
    // ASM:01474          PACK  WANT,10(5,R1)
    // ASM:01475          B     PARM2
    //--------------------------------------------------------------------*
    // B E G I N    R O U T I N E    F O R    S T A N D A L O N E      *
    //--------------------------------------------------------------------*
    // NUMBER OF RECORDS TO DITTO
    if (!getReg1().getString(2, 8).equals("NUMRECS=")) {
      user1();
    }
    parm2: {
      zaphi: {
        if (getReg1().getString(10, 3).equals("ALL")) {
          break zaphi;
        }
        if (getReg1().getString(10, 5).compareTo("99999") > 0) {
          user1();
        }
        if (getReg1().getString(10, 5).compareTo("00000") < 0) {
          user1();
        }
        storage.getWant().set(getReg1().getNumericAtRange(10, (10) + 5));
        break parm2;
        // ASM <Code_block_id:ZAPHI>
        // ASM:01476 ZAPHI    EQU   *
        // ASM:01477          OI    MODE,ALL                SET 'PRINT ALL RECORDS' BIT ON
        // ASM:01478          SPACE 2
        // ASM:01479 PARM2    CLC   16(10,R1),=C'PS HEADER='
        // ASM:01480          BNE   USER1
        // ASM:01481          CLI   26(R1),C'Y'             OSCR DITTO?
        // ASM:01482          BE    OPENDD                  YES
        // ASM:01483          OI    MODE,STANDARD           NO, SET 'STANDARD DITTO' BIT ON
      } //zaphi
        // SET 'PRINT ALL RECORDS' BIT ON
      storage.getMode().or(PsdittoConstants.ALL);
    } //parm2
    if (!getReg1().getString(16, 10).equals("PS HEADER=")) {
      user1();
    }
    opendd: {
      // OSCR DITTO?
      if (getReg1().getString(26, 1).equals("Y")) {
        // YES
        break opendd;
      }
      // NO, SET 'STANDARD DITTO' BIT ON
      storage.getMode().or(PsdittoConstants.STANDARD);
      // ASM <Code_block_id:OPENDD>
      // ASM:01484          SPACE
      // ASM:01485 OPENDD   EQU   *
      // ASM:01486          OPEN  (INPUT,(INPUT))
      // ASM:01487          OPEN  (OUTPUT,(OUTPUT))
      // ASM:01488          SPACE
      // ASM:01489          RDJFCB (INPUT)                READ JOB FILE CONTROL BLOCK
      // ASM:01490          BAL   R4,LOADJFCB             LOAD INFO FROM JFCB
      // ASM:01491          SPACE
      // ASM:01492          BR    R5                      RETURN
    } //opendd
    input.openInput();
    output.openOutput();
    // READ JOB FILE CONTROL BLOCK
    input.setInfoRDJFCB(storage.getInjfcb());
    // LOAD INFO FROM JFCB
    loadjfcb();
    // RETURN
    return;
  }

  /**
   * Method SETUPLCR.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void setuplcr() throws NAsmException {
    // ASM <Code_block_id:SETUPLCR>
    // ASM:01493          EJECT
    // ASM:01494 *--------------------------------------------------------------------*
    // ASM:01495 *        L C R   O P T I O N   S E T U P   R O U T I N E             *
    // ASM:01496 *--------------------------------------------------------------------*
    // ASM:01497 SETUPLCR EQU   *
    // ASM:01498          LA    R2,CARDIN
    //--------------------------------------------------------------------*
    // L C R   O P T I O N   S E T U P   R O U T I N E             *
    //--------------------------------------------------------------------*
    getReg2().setAddress(storage.getCardin());
    // ASM <Code_block_id:GETLCR>
    // ASM:01499 GETLCR   EQU   *
    // ASM:01500          GET   LCRFLDS,0(R2)      ==>> GET MOVE, R2 POINTS TO AREA
    // ASM:01501          LR    R11,R2
    eoflcr: {
      getlcr: while (true) {
        // ==>> GET MOVE, R2 POINTS TO AREA
        if (lcrflds.next())
          getReg2().setByteString(lcrflds.getByteString(), false);
        if (lcrflds.isEOF()) {
          break eoflcr;
        }
        getReg11().setRegister(getReg2());
        // ASM <Code_block_id:LHEAD>
        // ASM:01502 LHEAD    NOP   ECHOCARD
        // ASM:01503          MVI   LHEAD+1,X'F0'
        // ASM:01504          MVC   STAT+1(50),LCRHEAD      PRINT HEADER FOR LCR FIELDS ECHO
        // ASM:01505          BAL   R3,WRITE
        // ASM:01506          MVI   STAT,C'-'
        echocard: {
          if (lhead) {
            break echocard;
          }
          lhead = true;
          // PRINT HEADER FOR LCR FIELDS ECHO
          storage.getStat().setByteString(storage.getLcrhead().getByteString(), 1);
          write();
          storage.getStat().set("-");
          // ASM <Code_block_id:ECHOCARD>
          // ASM:01507 ECHOCARD EQU   *
          // ASM:01508          MVC   STAT+1(80),0(R11)
          // ASM:01509          BAL   R3,WRITE
          // ASM:01510          LR    R2,R11
        } //echocard
        storage.getStat().setByteString(getReg11().getByteString(0, 80), 1);
        write();
        getReg2().setRegister(getReg11());
        // ASM <Code_block_id:LCR1>
        // ASM:01511          SPACE
        // ASM:01512 LCR1     NOP   LOADTBL
        // ASM:01513          CLC   CARDIN(12),=C'# OF FIELDS='
        // ASM:01514          BE    NUMFLDS
        // ASM:01515          CLC   CARDIN(12),=C'OUTPUT FORM='
        // ASM:01516          BE    OUTFORM
        // ASM:01517          SPACE
        // ASM:01518          TM    LCRCARDS,X'03'          REQUIRED CARDS PRESENT?
        // ASM:01519          BNO   USER2                   NO
        // ASM:01520          MVI   LCR1+1,X'F0'            SET BRANCH TO LOADTBL
        // ASM:01521          L     R6,LCRADDR              ADDR OF GETMAIN AREA
        // ASM:01522          MVC   0(80,R6),CARDIN         MOVE FIRST FLD CARD INTO GETMAIN
        numflds: {
          outform: {
            loadtbl: {
              if (lcr1) {
                break loadtbl;
              }
              if (storage.getCardin().getString(0, 12).equals("# OF FIELDS=")) {
                break numflds;
              }
              if (storage.getCardin().getString(0, 12).equals("OUTPUT FORM=")) {
                break outform;
              }
              // REQUIRED CARDS PRESENT?
              if (storage.getLcrcards().testUnderMask(0x03) != 0x03) {
                // NO
                user2();
              }
              // SET BRANCH TO LOADTBL
              lcr1 = true;
              // ADDR OF GETMAIN AREA
              getReg6().load(storage.getLcraddr());
              // MOVE FIRST FLD CARD INTO GETMAIN
              getReg6().setByteString(storage.getCardin().getByteString(), false);
              // ASM <Code_block_id:LOADTBL>
              // ASM:01523          SPACE
              // ASM:01524 LOADTBL  EQU   *
              // ASM:01525          LA    R6,80(R6)               BUMP PTR TO NEXT FILL POSITION
              // ASM:01526          LR    R2,R6
              // ASM:01527          B     GETLCR                  CONTINUE TO GET LCR FIELDS
            } //loadtbl
            // BUMP PTR TO NEXT FILL POSITION
            getReg6().increment(80);
            getReg2().setRegister(getReg6());
            continue getlcr;
            // ASM <Code_block_id:OUTFORM>
            // ASM:01528          SPACE 3
            // ASM:01529 OUTFORM  EQU   *
            // ASM:01530          CLC   CARDIN+12(5),=C'CHECK'
            // ASM:01531          BNE   CKAUDIT
            // ASM:01532          OI    MODE2,CHECK             SET LCR CHECK OPTION BIT ON
            // ASM:01533          B     SETCARD1
          } //outform
          setcard1: {
            ckaudit: {
              if (!storage.getCardin().getString(12, 5).equals("CHECK")) {
                break ckaudit;
              }
              // SET LCR CHECK OPTION BIT ON
              storage.getMode2().or(PsdittoConstants.CHECK);
              break setcard1;
              // ASM <Code_block_id:CKAUDIT>
              // ASM:01534 CKAUDIT  EQU   *
              // ASM:01535          CLC   CARDIN+12(5),=C'AUDIT'
              // ASM:01536          BNE   USER2
              // ASM:01537          OI    MODE2,AUDIT             SET LCR AUDIT OPTION BIT ON
            } //ckaudit
            if (!storage.getCardin().getString(12, 5).equals("AUDIT")) {
              user2();
            }
            // SET LCR AUDIT OPTION BIT ON
            storage.getMode2().or(PsdittoConstants.AUDIT);
            // ASM <Code_block_id:SETCARD1>
            // ASM:01538 SETCARD1 EQU   *
            // ASM:01539          OI    LCRCARDS,X'01'          SET BIT FOR OUTPUT FORM CARD
            // ASM:01540          B     GETLCR
          } //setcard1
          // SET BIT FOR OUTPUT FORM CARD
          storage.getLcrcards().or(0x01);
          continue getlcr;
          // ASM <Code_block_id:NUMFLDS>
          // ASM:01541          SPACE 3
          // ASM:01542 NUMFLDS  EQU   *
          // ASM:01543          PACK  NUMLCRF,CARDIN+12(3)   CONVERT # FLDS TO PACKED
          // ASM:01544          CP    NUMLCRF,=P'500'         NUMBER OF FLDS > 500?
          // ASM:01545          BH    USER5                   YES, ERROR
          // ASM:01546          CP    NUMLCRF,=P'300'
          // ASM:01547          BH    LCR500
          // ASM:01548          CP    NUMLCRF,=P'125'
          // ASM:01549          BH    LCR250
          // ASM:01550          CP    NUMLCRF,=P'50'
          // ASM:01551          BH    LCR125
          // ASM:01552          GETMAIN R,LV=4008             50 LCR FIELDS
          // ASM:01553          B     SAVEADDR
          // ASM:01554 LCR125   GETMAIN R,LV=10008            125 LCR FIELDS
          // ASM:01555          B     SAVEADDR
          // ASM:01556 LCR250   GETMAIN R,LV=24008            300 LCR FIELDS
          // ASM:01557          B     SAVEADDR
          // ASM:01558 LCR500   GETMAIN R,LV=40008            500 LCR FIELDS MAX
          // ASM:01559 SAVEADDR ST    R1,LCRADDR
          // ASM:01560          OI    LCRCARDS,X'02'          SET BIT FOR # LCR FIELDS CARD
          // ASM:01561          B     GETLCR
        } //numflds
        saveaddr: {
          lcr500: {
            lcr250: {
              lcr125: {
                // CONVERT # FLDS TO PACKED
                storage.getNumlcrf().set(storage.getCardin().getNumericAtRange(12, (12) + 3));
                // NUMBER OF FLDS > 500?
                if (storage.getNumlcrf().getNumeric() > 500) {
                  // YES, ERROR
                  user5();
                }
                if (storage.getNumlcrf().getNumeric() > 300) {
                  break lcr500;
                }
                if (storage.getNumlcrf().getNumeric() > 125) {
                  break lcr250;
                }
                if (storage.getNumlcrf().getNumeric() > 50) {
                  break lcr125;
                }
                // 50 LCR FIELDS
                getReg1().setAddress(getStorage(4008));
                break saveaddr;
              } //lcr125
              getReg1().setAddress(getStorage(10008));
              break saveaddr;
            } //lcr250
            getReg1().setAddress(getStorage(24008));
            break saveaddr;
          } //lcr500
          getReg1().setAddress(getStorage(40008));
        } //saveaddr
        storage.getLcraddr().set(getReg1());
        // SET BIT FOR # LCR FIELDS CARD
        storage.getLcrcards().or(0x02);
        continue getlcr;
        // ASM <Code_block_id:EOFLCR>
        // ASM:01562          SPACE 3
        // ASM:01563 EOFLCR   EQU   *
        // ASM:01564          MVI   0(R2),X'FF'
        // ASM:01565          MVI   STAT,C'1'               SKIP TO NEW PAGE ON OUTPUT
        // ASM:01566          BAL   R3,WRITE
        // ASM:01567          SPACE 2
        // ASM:01568          BR    R5                      RETURN
      } //getlcr
    } //eoflcr
    getReg2().setByteString(_HIGH(1), false);
    // SKIP TO NEW PAGE ON OUTPUT
    storage.getStat().set("1");
    write();
    // RETURN
  }

  /**
   * Method LOADJFCB.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void loadjfcb() throws NAsmException {
    // ASM <Code_block_id:LOADJFCB>
    // ASM:01569          EJECT
    // ASM:01570 *--------------------------------------------------------------------*
    // ASM:01571 *        R E A D   J O B   F I L E   C O N T R O L   B L O C K       *
    // ASM:01572 *--------------------------------------------------------------------*
    // ASM:01573 LOADJFCB EQU   *
    // ASM:01574          SPACE
    // ASM:01575          MVC   DSN(52),INJFCB
    // ASM:01576          MVC   VOLSER(6),INJFCB+118
    // ASM:01577          SPACE
    // ASM:01578          TM    MODE,CALLED             CALLED MODE?
    // ASM:01579          BNO   GOJFCB                  NO
    // ASM:01580          CLC   INJFCB(8),=C'NULLFILE'  IS DATASET TO DITTO A DUMMY?
    // ASM:01581          BNE   GOJFCB                  NO
    // ASM:01582          L     R1,SAVER1               ADDR OF PARMS
    // ASM:01583          CLC   23(3,R1),=C'DD='        CHECK FOR PRESENCE OF DD NAME
    // ASM:01584          BNE   GOJFCB
    // ASM:01585          MVC   DSN+10(11),23(R1)       MOVE DDNAME SINCE DUMMY DATA SET
    //--------------------------------------------------------------------*
    // R E A D   J O B   F I L E   C O N T R O L   B L O C K       *
    //--------------------------------------------------------------------*
    storage.getSubtit2().getDsn().setByteString(storage.getInjfcb().getByteString(0, 52));
    storage.getSubtit1().getVolser().setByteString(storage.getInjfcb().getByteString(118, 6));
    gojfcb: {
      // CALLED MODE?
      // NO
      if (storage.getMode().testUnderMask(PsdittoConstants.CALLED) != PsdittoConstants.CALLED) {
        break gojfcb;
      }
      // IS DATASET TO DITTO A DUMMY?
      if (!storage.getInjfcb().getString(0, 8).equals("NULLFILE")) {
        // NO
        break gojfcb;
      }
      // ADDR OF PARMS
      getReg1().load(storage.getSaver1());
      // CHECK FOR PRESENCE OF DD NAME
      if (!getReg1().getString(23, 3).equals("DD=")) {
        break gojfcb;
      }
      // MOVE DDNAME SINCE DUMMY DATA SET
      storage.getSubtit2().getDsn().setByteString(getReg1().getByteString(23, 11), 10);
      // ASM <Code_block_id:GOJFCB>
      // ASM:01586          SPACE
      // ASM:01587 GOJFCB   EQU   *
      // ASM:01588          LH    R2,INJFCB+104           GET LOGICAL RECORD LENGTH
      // ASM:01589          CVD   R2,WRKDBL               CONVERT LEN TO PACK
      // ASM:01590          ZAP   PACK4,WRKDBL+4(4)
      // ASM:01591          MVC   WORK12(8),PTRN8         ED PATTERN
      // ASM:01592          ED    WORK12(8),PACK4
      // ASM:01593          MVC   LRECL(8),WORK12         MOVE TO CHARLINE
      // ASM:01594          SPACE
      // ASM:01595          LH    R2,INJFCB+102           GET BLOCK SIZE
      // ASM:01596          CVD   R2,WRKDBL               CONVERT LEN TO PACK
      // ASM:01597          ZAP   PACK4,WRKDBL+4(4)
      // ASM:01598          MVC   WORK12(8),PTRN8         ED PATTERN
      // ASM:01599          ED    WORK12(8),PACK4
      // ASM:01600          MVC   BLKSIZE(8),WORK12       MOVE TO CHARLINE
      // ASM:01601          SPACE
      // ASM:01602          CLI   INJFCB+100,X'50'         VARIABLE LENGTH RECORD ?
      // ASM:01603          BNE   SETFIX                   NO GO AROUND
      // ASM:01604          MVC   RECFM,=C'VB'              YES SET SWITCH
      // ASM:01605          B     RET001                   GO AROUND
    } //gojfcb
      // GET LOGICAL RECORD LENGTH
    getReg2().setByteString(_LOW(2) + storage.getInjfcb().getByteString(104, 2));
    // CONVERT LEN TO PACK
    storage.getWrkdbl().setAsPacked(getReg2());
    storage.getPack4().set(storage.getWrkdbl().getPackedAtRange(4, (4) + 4));
    // ED PATTERN
    storage.getWork12().setByteString(storage.getPtrn8().getByteString(0, 8));
    storage.getWork12().setEdited(storage.getPack4(), 0, 8);
    // MOVE TO CHARLINE
    storage.getSubtit1().getLrecl().setByteString(storage.getWork12().getByteString(0, 8));
    // GET BLOCK SIZE
    getReg2().setByteString(_LOW(2) + storage.getInjfcb().getByteString(102, 2));
    // CONVERT LEN TO PACK
    storage.getWrkdbl().setAsPacked(getReg2());
    storage.getPack4().set(storage.getWrkdbl().getPackedAtRange(4, (4) + 4));
    // ED PATTERN
    storage.getWork12().setByteString(storage.getPtrn8().getByteString(0, 8));
    storage.getWork12().setEdited(storage.getPack4(), 0, 8);
    // MOVE TO CHARLINE
    storage.getSubtit1().getBlksize().setByteString(storage.getWork12().getByteString(0, 8));
    ret001: {
      setfix: {
        // VARIABLE LENGTH RECORD ?
        if (!storage.getInjfcb().getByteString(100, 1).equals(NAsmStrings.fromHexToString("50"))) {
          // NO GO AROUND
          break setfix;
        }
        // YES SET SWITCH
        storage.getSubtit1().getRecfm().set("VB");
        // GO AROUND
        break ret001;
        // ASM <Code_block_id:SETFIX>
        // ASM:01606          SPACE
        // ASM:01607 SETFIX   EQU   *                        FIX LENGTH RECORD
        // ASM:01608          MVC   RECFM,=C'FB'             SET SWITCH
        // ASM:01609          MVC   LRECLSAV,INJFCB+104      SAVE THE RECORD LENGTH
        // ASM:01610          SPACE
        // ASM:01611 RET001   BR    R4                       RETURN
      } //setfix
      // SET SWITCH
      storage.getSubtit1().getRecfm().set("FB");
      // SAVE THE RECORD LENGTH
      storage.getLreclsav().setByteString(storage.getInjfcb().getByteString(104, PsdittoStorage.Len.LRECLSAV));
    } //ret001
    // RETURN
    return;
  }

  /**
   * Method SELCTRTN.
   *
   * @throws NAsmException if there is a specific issue
   */
  private String selctrtn() throws NAsmException {
    // ASM <Code_block_id:SELCTRTN>
    // ASM:01612          EJECT
    // ASM:01613 *---------------------------------------------------------------------*
    // ASM:01614 *           S E G M E N T   S E L E C T   R O U T I N E               *
    // ASM:01615 *---------------------------------------------------------------------*
    // ASM:01616 SELCTRTN EQU   *
    // ASM:01617          SPACE
    // ASM:01618 READSEL  GET   SELECT                  EOF=DONESEL
    // ASM:01619          SPACE
    // ASM:01620 ONLYONCE TS    ONLYONCE+1
    // ASM:01621          BNZ   ISITHDR
    // ASM:01622          NI    MODE2,OFF_10            TURN OFF DFLT_HDR BIT
    //---------------------------------------------------------------------*
    // S E G M E N T   S E L E C T   R O U T I N E               *
    //---------------------------------------------------------------------*
    donesel: {
      readsel: while (true) {
        resetsel: {
          // EOF=DONESEL
          select.next();
          if (select.isEOF()) {
            break donesel;
          }
          isithdr: {
            if (onlyonce) {
              onlyonce = true;
              break isithdr;
            } else {
              onlyonce = true;
            }
            // TURN OFF DFLT_HDR BIT
            storage.getMode2().and(PsdittoConstants.OFF_10);
            // ASM <Code_block_id:ISITHDR>
            // ASM:01623 ISITHDR  EQU   *
            // ASM:01624          CLC   0(3,R1),=C'HDR'
            // ASM:01625          BNE   RESETSEL
            // ASM:01626          OI    MODE2,SEL_HDR           SET SELECT FOR HEADER
            // ASM:01627          B     READSEL
          } //isithdr
          if (!getReg1().getString().substring(0, 3).equals("HDR")) {
            break resetsel;
          }
          // SET SELECT FOR HEADER
          storage.getMode2().or(PsdittoConstants.SEL_HDR);
          continue readsel;
        } //resetsel
          // ASM <Code_block_id:RESETSEL>
          // ASM:01628 RESETSEL EQU   *
          // ASM:01629 * DETERMINE IF 230 OR 280 HEADER
          // ASM:01630          ST    R1,FULL
          // ASM:01631          GET   INPUT
          // ASM:01632          TM    203(R1),X'10'
          // ASM:01633          CLOSE INPUT
          // ASM:01634          OPEN  INPUT
          // ASM:01635          L     R1,FULL
          // ASM:01636          BNZ   DO_280
          // ASM:01637 *
          // ASM:01638          LA    R2,DESCTBL              SEGMENT DESCRIPTION TABLE
          // ASM:01639          LA    R7,7                    # OF ENTRIES IN DESCTBL
        do280: {
          // DETERMINE IF 230 OR 280 HEADER
          storage.getFull().set(getReg1());
          if (!input.next())
            return "eof";
          input.close();
          input.openInput();
          if (getReg1().testUnderMaskAt(203, 0x10) != ZERO) {
            getReg1().load(storage.getFull());
            break do280;
          }
          getReg1().load(storage.getFull());
          //
          // SEGMENT DESCRIPTION TABLE
          getReg2().setAddress(storage.getDesctbl());
          // # OF ENTRIES IN DESCTBL
          getReg7().set(7);
          // ASM <Code_block_id:CKSEL>
          // ASM:01640          SPACE
          // ASM:01641 CKSEL    EQU   *
          // ASM:01642          CLC   0(3,R1),0(R2)           COMPARE INPUT TO TABLE
          // ASM:01643          BNE   BUMPSEL                 NOT EQUAL
          // ASM:01644          MVI   3(R2),C'Y'              MARK TABLE ENTRY AS SELECTED
          // ASM:01645          OI    MODE2,SEL_OTH           SET SELECT FOR NON-HDR SEGMENT
          // ASM:01646          B     READSEL
          cksel: while (true) {
            bumpsel: {
              // COMPARE INPUT TO TABLE
              if (!getReg1().getByteString(0, 3).equals(getReg2().getByteString(0, 3))) {
                // NOT EQUAL
                break bumpsel;
              }
              // MARK TABLE ENTRY AS SELECTED
              getReg2().set("Y", 3);
              // SET SELECT FOR NON-HDR SEGMENT
              storage.getMode2().or(PsdittoConstants.SEL_OTH);
              continue readsel;
              // ASM <Code_block_id:BUMPSEL>
              // ASM:01647 BUMPSEL  EQU   *
              // ASM:01648          LA    R2,4(R2)
              // ASM:01649          BCT   R7,CKSEL
              // ASM:01650          B     DONESEL
            } //bumpsel
            getReg2().increment(4);
            getReg7().decrement(1);
            if (getReg7().getNumeric() > 0) {
              continue cksel;
            }
            break cksel;
          } //cksel
          break donesel;
          // ASM <Code_block_id:DO_280>
          // ASM:01651          SPACE
          // ASM:01652 DO_280   EQU   *
          // ASM:01653          LA    R2,DESCTBL2             SEGMENT DESCRIPTION TABLE
          // ASM:01654          LA    R7,DESCTBL2_#           # OF ENTRIES IN DESCTBL
        } //do280
        // SEGMENT DESCRIPTION TABLE
        getReg2().setAddress(storage.getDesctbl2());
        // # OF ENTRIES IN DESCTBL
        getReg7().set(PsdittoConstants.DESCTBL2_X);
        // ASM <Code_block_id:CKSEL_280>
        // ASM:01655          SPACE
        // ASM:01656 CKSEL_280 EQU   *
        // ASM:01657          CLC   0(3,R1),0(R2)           COMPARE INPUT TO TABLE
        // ASM:01658          BNE   BUMPSEL_280             NOT EQUAL
        // ASM:01659          MVI   3(R2),C'Y'              MARK TABLE ENTRY AS SELECTED
        // ASM:01660          OI    MODE2,SEL_OTH           SET SELECT FOR NON-HDR SEGMENT
        // ASM:01661          B     READSEL
        cksel280: while (true) {
          bumpsel280: {
            // COMPARE INPUT TO TABLE
            if (!getReg1().getByteString(0, 3).equals(getReg2().getByteString(0, 3))) {
              // NOT EQUAL
              break bumpsel280;
            }
            // MARK TABLE ENTRY AS SELECTED
            getReg2().set("Y", 3);
            // SET SELECT FOR NON-HDR SEGMENT
            storage.getMode2().or(PsdittoConstants.SEL_OTH);
            continue readsel;
            // ASM <Code_block_id:BUMPSEL_280>
            // ASM:01661 BUMPSEL_280 EQU   *
            // ASM:01662          LA    R2,L'DESCTBL2(R2)
            // ASM:01663          BCT   R7,CKSEL_280
          } //bumpsel280
          getReg2().increment(Desctbl2.Len.DESCTBL2_SLOT);
          getReg7().decrement(1);
          if (getReg7().getNumeric() > 0) {
            continue cksel280;
          }
          // ASM <Code_block_id:DONESEL>
          // ASM:01665          SPACE
          // ASM:01666 DONESEL  EQU   *
          // ASM:01667          CLOSE (SELECT)
          // ASM:01668          SPACE
          // ASM:01669          BR    R5                      RETURN
          break cksel280;
        } //cksel280
      } //readsel
    } //donesel
    select.close();
    // RETURN
    return null;
  }

  /**
   * Method BANNER.
   *
   * @throws NAsmException if there is a specific issue
   */
  private void banner() throws NAsmException {
    // ASM <Code_block_id:BANNER>
    // ASM:01670          EJECT
    // ASM:01671 *--------------------------------------------------------------------*
    // ASM:01672 *                  B A N N E R   R O U T I N E                       *
    // ASM:01673 *--------------------------------------------------------------------*
    // ASM:01674 BANNER   EQU   *
    // ASM:01675          MVI   STAT,C'1'
    // ASM:01676          LA    R7,12
    // ASM:01677          LA    R6,CMSDITTO
    //--------------------------------------------------------------------*
    // B A N N E R   R O U T I N E                       *
    //--------------------------------------------------------------------*
    storage.getStat().set("1");
    getReg7().set(12);
    getReg6().setAddress(storage.getCmsditto());
    // ASM <Code_block_id:BLOOP>
    // ASM:01678 BLOOP    EQU   *
    // ASM:01679          MVC   STAT+1(42),0(R6)
    // ASM:01680          MVC   STAT+44(43),42(R6)
    // ASM:01681          MVC   STAT+87(42),85(R6)
    // ASM:01682          BAL   R3,WRITE
    // ASM:01683          LA    R6,127(R6)
    // ASM:01684          BCT   R7,BLOOP
    // ASM:01685          SPACE
    // ASM:01686          BR    R4                      RETURN
    bloop: while (true) {
      storage.getStat().setByteString(getReg6().getByteString(0, 42), 1);
      storage.getStat().setByteString(getReg6().getByteString(42, 43), 44);
      storage.getStat().setByteString(getReg6().getByteString(85, 42), 87);
      write();
      getReg6().increment(127);
      getReg7().decrement(1);
      if (getReg7().getNumeric() > 0) {
        continue bloop;
      }
      break bloop;
    } //bloop
    // RETURN
    return;
  }
}
