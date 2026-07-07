package com.aws.carddemo.common.copybook;

import com.nib.commons.storage.*;

public class SqldaCopy extends NGroup {

  public static class Sqlda extends NGroup {

    public NChar sqldaid = new NChar(8);

    public NInt32 sqldabc = new NInt32();

    public NInt16 sqln = new NInt16();

    public NInt16 sqld = new NInt16();

    public static class Sqlvar extends NGroup {

      public NInt16 sqltype = new NInt16();

      public NInt16 sqllen = new NInt16();

      public static class Filler9 extends NGroup {

        public NChar sqlprecision = new NChar(1);

        public NChar sqlscale = new NChar(1);
      }

      public Filler9 filler9 = (Filler9) new Filler9().redefines(sqllen);

      public NChar sqlres = new NChar(12);

      public NPointer sqldata = new NPointer();

      public NPointer sqlind = new NPointer();

      public static class Sqlname extends NGroup {

        public NInt16 sqlnamel = new NInt16();

        public NChar sqlnamec = new NChar(30);
      }

      public Sqlname sqlname = new Sqlname();
    }

    public Sqlvar[] sqlvar = AbstractNField.occurs(409, new Sqlvar());

    public Sqlvar sqlvarAtIndex(int index) {
      return getOccursInstance(sqlvar, index);
    }
  }

  public Sqlda sqlda = new Sqlda();
}
