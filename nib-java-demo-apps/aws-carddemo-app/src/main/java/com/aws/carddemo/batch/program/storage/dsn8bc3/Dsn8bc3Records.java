package com.aws.carddemo.batch.program.storage.dsn8bc3;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Dsn8bc3Records extends NGroup {
  public NChar cardrec = new NChar(80);
  public NChar reprec = new NChar(120);
}
