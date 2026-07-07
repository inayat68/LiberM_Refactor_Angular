package  com.aws.carddemo.batch.program.constants;

/**
 * 
 * <b>RP-Asm2Java - ASM370 to JAVA.</b> <br/>
 * <br/>
 * 
 * <br/>
 * 
 * @version 2.2.8
 * 
 * @author RP - Modern Systems
 */
public class PsdittoConstants {

  // *
  // STANDARD DITTO *
  public final static int STANDARD = 0x80;
  // CALLED MODULE *
  public final static int CALLED = 0x40;
  // DITTO ALL RECORDS *
  public final static int ALL = 0x20;
  //
  // CURRENT SEGMENT IS LCR
  public final static int LCRSEG = 0x80;
  // LCR CHECK OPTION
  public final static int CHECK = 0x40;
  // LCR AUDIT OPTION
  public final static int AUDIT = 0x20;
  // DEFAULT: PRINT HDR
  public final static int DFLT_HDR = 0x10;
  // SELECT OPTION: HDR IS SELECTED
  public final static int SEL_HDR = 0x08;
  // SELECT OPTION: NON-HDR SEGMENT IS SELECTED
  public final static int SEL_OTH = 0x04;
  // EQU   B'00000010'
  // SEGMENT SELECTED
  public final static int SEGSEL = 0x01;
  // MODE2 COMBINED: EITHER CHECK OR AUDIT
  public final static int CHK_AUD = 0x60;
  // MODE2 COMBINED: PRINT HDR
  public final static int PRT_HDR = 0x18;
  // MODE2 COMBINED: PRINT NON-HDR SEG
  public final static int NON_HDR = 0x14;
  // MODE2 COMBINED: BOTH ON/OFF TO PRT OTH SEG
  public final static int SEL_ON = 0x05;
  // TURN OFF BIT 80
  public final static int OFF_80 = 0x7f;
  // TURN OFF BIT 10
  public final static int OFF_10 = 0xef;
  // TURN OFF BIT 01
  public final static int OFF_01 = 0xfe;
  public final static int DESCTBL2_X = 19;

}
