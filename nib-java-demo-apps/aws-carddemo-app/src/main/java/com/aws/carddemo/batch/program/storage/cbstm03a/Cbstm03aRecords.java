package com.aws.carddemo.batch.program.storage.cbstm03a;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cbstm03aRecords extends NGroup {
  public NChar fdStmtfileRec = new NChar(80);
  public NChar fdHtmlfileRec = new NChar(100);
}
