package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Cvcrd01y extends NGroup {

  // COB: 000100 01  CC-WORK-AREAS.                                               00010004
  public static class CcWorkAreas extends NGroup {
    // COB: 000200    05 CC-WORK-AREA.                                              00020001
    public static class CcWorkArea extends NGroup {
      // COB: 000900       10 CCARD-AID                         PIC X(5).             00090001
      public NChar ccardAid = new NChar(5);

      // COB: 001000          88  CCARD-AID-ENTER                VALUE 'ENTER'.       00100001
      public boolean ccardAidEnter() {
        return ccardAid.equals("ENTER");
      }

      public void setCcardAidEnter(boolean value) {
        if (value) ccardAid.setValue("ENTER");
      }

      // COB: 001100          88  CCARD-AID-CLEAR                VALUE 'CLEAR'.       00110001
      public boolean ccardAidClear() {
        return ccardAid.equals("CLEAR");
      }

      public void setCcardAidClear(boolean value) {
        if (value) ccardAid.setValue("CLEAR");
      }

      // COB: 001200          88  CCARD-AID-PA1                  VALUE 'PA1  '.       00120001
      public boolean ccardAidPa1() {
        return ccardAid.equals("PA1  ");
      }

      public void setCcardAidPa1(boolean value) {
        if (value) ccardAid.setValue("PA1  ");
      }

      // COB: 001300          88  CCARD-AID-PA2                  VALUE 'PA2  '.       00130001
      public boolean ccardAidPa2() {
        return ccardAid.equals("PA2  ");
      }

      public void setCcardAidPa2(boolean value) {
        if (value) ccardAid.setValue("PA2  ");
      }

      // COB: 001400          88  CCARD-AID-PFK01                VALUE 'PFK01'.       00140001
      public boolean ccardAidPfk01() {
        return ccardAid.equals("PFK01");
      }

      public void setCcardAidPfk01(boolean value) {
        if (value) ccardAid.setValue("PFK01");
      }

      // COB: 001500          88  CCARD-AID-PFK02                VALUE 'PFK02'.       00150001
      public boolean ccardAidPfk02() {
        return ccardAid.equals("PFK02");
      }

      public void setCcardAidPfk02(boolean value) {
        if (value) ccardAid.setValue("PFK02");
      }

      // COB: 001600          88  CCARD-AID-PFK03                VALUE 'PFK03'.       00160001
      public boolean ccardAidPfk03() {
        return ccardAid.equals("PFK03");
      }

      public void setCcardAidPfk03(boolean value) {
        if (value) ccardAid.setValue("PFK03");
      }

      // COB: 001700          88  CCARD-AID-PFK04                VALUE 'PFK04'.       00170001
      public boolean ccardAidPfk04() {
        return ccardAid.equals("PFK04");
      }

      public void setCcardAidPfk04(boolean value) {
        if (value) ccardAid.setValue("PFK04");
      }

      // COB: 001800          88  CCARD-AID-PFK05                VALUE 'PFK05'.       00180001
      public boolean ccardAidPfk05() {
        return ccardAid.equals("PFK05");
      }

      public void setCcardAidPfk05(boolean value) {
        if (value) ccardAid.setValue("PFK05");
      }

      // COB: 001900          88  CCARD-AID-PFK06                VALUE 'PFK06'.       00190001
      public boolean ccardAidPfk06() {
        return ccardAid.equals("PFK06");
      }

      public void setCcardAidPfk06(boolean value) {
        if (value) ccardAid.setValue("PFK06");
      }

      // COB: 002000          88  CCARD-AID-PFK07                VALUE 'PFK07'.       00200001
      public boolean ccardAidPfk07() {
        return ccardAid.equals("PFK07");
      }

      public void setCcardAidPfk07(boolean value) {
        if (value) ccardAid.setValue("PFK07");
      }

      // COB: 002100          88  CCARD-AID-PFK08                VALUE 'PFK08'.       00210001
      public boolean ccardAidPfk08() {
        return ccardAid.equals("PFK08");
      }

      public void setCcardAidPfk08(boolean value) {
        if (value) ccardAid.setValue("PFK08");
      }

      // COB: 002200          88  CCARD-AID-PFK09                VALUE 'PFK09'.       00220001
      public boolean ccardAidPfk09() {
        return ccardAid.equals("PFK09");
      }

      public void setCcardAidPfk09(boolean value) {
        if (value) ccardAid.setValue("PFK09");
      }

      // COB: 002300          88  CCARD-AID-PFK10                VALUE 'PFK10'.       00230001
      public boolean ccardAidPfk10() {
        return ccardAid.equals("PFK10");
      }

      public void setCcardAidPfk10(boolean value) {
        if (value) ccardAid.setValue("PFK10");
      }

      // COB: 002400          88  CCARD-AID-PFK11                VALUE 'PFK11'.       00240001
      public boolean ccardAidPfk11() {
        return ccardAid.equals("PFK11");
      }

      public void setCcardAidPfk11(boolean value) {
        if (value) ccardAid.setValue("PFK11");
      }

      // COB: 002500          88  CCARD-AID-PFK12                VALUE 'PFK12'.       00250001
      public boolean ccardAidPfk12() {
        return ccardAid.equals("PFK12");
      }

      public void setCcardAidPfk12(boolean value) {
        if (value) ccardAid.setValue("PFK12");
      }

      // COB: 002700       10  CCARD-NEXT-PROG                  PIC X(8).             00270001
      public NChar ccardNextProg = new NChar(8);
      // COB: 003300       10  CCARD-NEXT-MAPSET                PIC X(7).             00330001
      public NChar ccardNextMapset = new NChar(7);
      // COB: 003400       10  CCARD-NEXT-MAP                   PIC X(7).             00340001
      public NChar ccardNextMap = new NChar(7);
      // COB: 003800       10  CCARD-ERROR-MSG                  PIC X(75).            00380001
      public NChar ccardErrorMsg = new NChar(75);
      // COB: 003900       10  CCARD-RETURN-MSG                 PIC X(75).            00390001
      public NChar ccardReturnMsg = new NChar(75);

      // COB: 004000         88  CCARD-RETURN-MSG-OFF           VALUE LOW-VALUES.     00400001
      public boolean ccardReturnMsgOff() {
        return ccardReturnMsg.isLowValues();
      }

      public void setCcardReturnMsgOff(boolean value) {
        if (value) ccardReturnMsg.lowValues();
      }

      // COB: 004400       10 CC-ACCT-ID                        PIC X(11)             00440005
      // COB: 004500                                            VALUE SPACES.
      public NChar ccAcctId = new NChar(11).initial(NChar.Space);
      // COB:              10 CC-ACCT-ID-N REDEFINES CC-ACCT-ID PIC 9(11).
      public NZoned ccAcctIdN = new NZoned(11, false).redefines(ccAcctId);
      // COB: 004600       10 CC-CARD-NUM                       PIC X(16)             00460005
      // COB: 004700                                            VALUE SPACES.         00470005
      public NChar ccCardNum = new NChar(16).initial(NChar.Space);
      // COB:              10 CC-CARD-NUM-N REDEFINES CC-CARD-NUM PIC 9(16).
      public NZoned ccCardNumN = new NZoned(16, false).redefines(ccCardNum);
      // COB: 004800       10 CC-CUST-ID                        PIC X(09)             00480005
      // COB: 004900                                            VALUE SPACES.         00490005
      public NChar ccCustId = new NChar(9).initial(NChar.Space);
      // COB: 004800       10 CC-CUST-ID-N REDEFINES CC-CUST-ID PIC 9(9).             00480005
      public NZoned ccCustIdN = new NZoned(9, false).redefines(ccCustId);
    }

    public CcWorkArea ccWorkArea = new CcWorkArea();
  }

  public CcWorkAreas ccWorkAreas = new CcWorkAreas();
}
