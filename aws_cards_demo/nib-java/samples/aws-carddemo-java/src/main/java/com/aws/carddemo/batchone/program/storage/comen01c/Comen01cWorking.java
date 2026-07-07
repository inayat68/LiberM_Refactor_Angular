package com.aws.carddemo.batchone.program.storage.comen01c;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Cocom01y;
import com.aws.carddemo.common.copybook.Comen01;
import com.aws.carddemo.common.copybook.Comen02y;
import com.aws.carddemo.common.copybook.Cottl01y;
import com.aws.carddemo.common.copybook.Csdat01y;
import com.aws.carddemo.common.copybook.Csmsg01y;
import com.aws.carddemo.common.copybook.Csusr01y;
import com.nib.commons.storage.*;

public class Comen01cWorking extends NGroup {
  // COB:        01 WS-VARIABLES.
  public WsVariables wsVariables = new WsVariables();

  public static class WsVariables extends NGroup {

    // COB:          05 WS-PGMNAME                 PIC X(08) VALUE 'COMEN01C'.
    public NChar wsPgmname = new NChar(8).initial("COMEN01C");
    // COB:          05 WS-TRANID                  PIC X(04) VALUE 'CM00'.
    public NChar wsTranid = new NChar(4).initial("CM00");
    // COB:          05 WS-MESSAGE                 PIC X(80) VALUE SPACES.
    public NChar wsMessage = new NChar(80).initial(NChar.Space);
    // COB:          05 WS-USRSEC-FILE             PIC X(08) VALUE 'USRSEC  '.
    public NChar wsUsrsecFile = new NChar(8).initial("USRSEC  ");
    // COB:          05 WS-ERR-FLG                 PIC X(01) VALUE 'N'.
    public NChar wsErrFlg = new NChar(1).initial("N");

    // COB:            88 ERR-FLG-ON                         VALUE 'Y'.
    public boolean errFlgOn() {
      return wsErrFlg.equals("Y");
    }

    public void setErrFlgOn(boolean value) {
      if (value) wsErrFlg.setValue("Y");
    }

    // COB:            88 ERR-FLG-OFF                        VALUE 'N'.
    public boolean errFlgOff() {
      return wsErrFlg.equals("N");
    }

    public void setErrFlgOff(boolean value) {
      if (value) wsErrFlg.setValue("N");
    }

    // COB:          05 WS-RESP-CD                 PIC S9(09) COMP VALUE ZEROS.
    public NInt32 wsRespCd = new NInt32().initial(0);
    // COB:          05 WS-REAS-CD                 PIC S9(09) COMP VALUE ZEROS.
    public NInt32 wsReasCd = new NInt32().initial(0);
    // COB:          05 WS-OPTION-X                PIC X(02) JUST RIGHT.
    public NChar wsOptionX = new NChar(2).justified(Justified.Right);
    // COB:          05 WS-OPTION                  PIC 9(02) VALUE 0.
    public NZoned wsOption = new NZoned(2, false).initial(0);
    // COB:          05 WS-IDX                     PIC S9(04) COMP VALUE ZEROS.
    public NInt16 wsIdx = new NInt16().initial(0);
    // COB:          05 WS-MENU-OPT-TXT            PIC X(40) VALUE SPACES.
    public NChar wsMenuOptTxt = new NChar(40).initial(NChar.Space);
  }

  public Cocom01y cocom01y = new Cocom01y();
  public Comen02y comen02y = new Comen02y();
  public Comen01 comen01 = new Comen01();
  public Cottl01y cottl01y = new Cottl01y();
  public Csdat01y csdat01y = new Csdat01y();
  public Csmsg01y csmsg01y = new Csmsg01y();
  public Csusr01y csusr01y = new Csusr01y();
}
