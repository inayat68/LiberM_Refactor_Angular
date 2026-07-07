package com.aws.carddemo.batch.program.storage.cbstm03b;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cbstm03bWorking extends NGroup {
  // COB:        01  TRNXFILE-STATUS.
  public TrnxfileStatus trnxfileStatus = new TrnxfileStatus();

  public static class TrnxfileStatus extends NGroup {

    // COB:            05  TRNXFILE-STAT1      PIC X.
    public NChar trnxfileStat1 = new NChar(1);
    // COB:            05  TRNXFILE-STAT2      PIC X.
    public NChar trnxfileStat2 = new NChar(1);
  }

  // COB:        01  XREFFILE-STATUS.
  public XreffileStatus xreffileStatus = new XreffileStatus();

  public static class XreffileStatus extends NGroup {

    // COB:            05  XREFFILE-STAT1      PIC X.
    public NChar xreffileStat1 = new NChar(1);
    // COB:            05  XREFFILE-STAT2      PIC X.
    public NChar xreffileStat2 = new NChar(1);
  }

  // COB:        01  CUSTFILE-STATUS.
  public CustfileStatus custfileStatus = new CustfileStatus();

  public static class CustfileStatus extends NGroup {

    // COB:            05  CUSTFILE-STAT1      PIC X.
    public NChar custfileStat1 = new NChar(1);
    // COB:            05  CUSTFILE-STAT2      PIC X.
    public NChar custfileStat2 = new NChar(1);
  }

  // COB:        01  ACCTFILE-STATUS.
  public AcctfileStatus acctfileStatus = new AcctfileStatus();

  public static class AcctfileStatus extends NGroup {

    // COB:            05  ACCTFILE-STAT1      PIC X.
    public NChar acctfileStat1 = new NChar(1);
    // COB:            05  ACCTFILE-STAT2      PIC X.
    public NChar acctfileStat2 = new NChar(1);
  }
}
