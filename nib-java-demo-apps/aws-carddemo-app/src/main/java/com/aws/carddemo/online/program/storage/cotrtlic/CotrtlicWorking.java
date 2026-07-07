package com.aws.carddemo.online.program.storage.cotrtlic;

import static com.nib.commons.storage.SpecialRegister.*;

import com.aws.carddemo.common.copybook.Cocom01y;
import com.aws.carddemo.common.copybook.Cotrtli;
import com.aws.carddemo.common.copybook.Cottl01y;
import com.aws.carddemo.common.copybook.Csdat01y;
import com.aws.carddemo.common.copybook.Csmsg01y;
import com.aws.carddemo.common.copybook.Csusr01y;
import com.aws.carddemo.common.copybook.Cvact02y;
import com.aws.carddemo.common.copybook.Cvcrd01y;
import com.aws.carddemo.common.copybook.Dcltrtyp;
import com.nib.commons.storage.*;

public class CotrtlicWorking extends NGroup {
  // COB: 004200 01 WS-CONSTANTS.
  public WsConstants wsConstants = new WsConstants();

  public static class WsConstants extends NGroup {

    // COB: 004300   05  LIT-THISPGM             PIC X(8)        VALUE 'COTRTLIC'.
    public NChar litThispgm = new NChar(8).initial("COTRTLIC");
    // COB: 004400   05  LIT-THISTRANID          PIC X(4)        VALUE 'CTLI'.
    public NChar litThistranid = new NChar(4).initial("CTLI");
    // COB: 004500   05  LIT-THISMAPSET          PIC X(7)        VALUE 'COTRTLI'.
    public NChar litThismapset = new NChar(7).initial("COTRTLI");
    // COB: 004600   05  LIT-THISMAP             PIC X(7)        VALUE 'CTRTLIA'.
    public NChar litThismap = new NChar(7).initial("CTRTLIA");
    // COB: 004700   05  LIT-ADMINPGM             PIC X(8)       VALUE 'COADM01C'.
    public NChar litAdminpgm = new NChar(8).initial("COADM01C");
    // COB: 004800   05  LIT-ADMINTRANID          PIC X(4)       VALUE 'CA00'.
    public NChar litAdmintranid = new NChar(4).initial("CA00");
    // COB: 004900   05  LIT-ADMINMAPSET          PIC X(7)       VALUE 'COADM01'.
    public NChar litAdminmapset = new NChar(7).initial("COADM01");
    // COB: 005000   05  LIT-ADDTPGM             PIC X(8)        VALUE 'COTRTUPC'.
    public NChar litAddtpgm = new NChar(8).initial("COTRTUPC");
    // COB: 005100   05  LIT-ADDTTRANID          PIC X(4)        VALUE 'CTTU'.
    public NChar litAddttranid = new NChar(4).initial("CTTU");
    // COB: 005200   05  LIT-ADDTMAPSET          PIC X(7)        VALUE 'COTRTUP'.
    public NChar litAddtmapset = new NChar(7).initial("COTRTUP");
    // COB: 005300   05  LIT-ADDTMAP             PIC X(7)        VALUE 'CTRTUPA'.
    public NChar litAddtmap = new NChar(7).initial("CTRTUPA");
    // COB: 005400   05  LIT-DSNTIAC             PIC X(7)        VALUE 'DSNTIAC'.
    public NChar litDsntiac = new NChar(7).initial("DSNTIAC");
    // COB: 005500   05  LIT-ASTERISK            PIC X(7)        VALUE '*'.
    public NChar litAsterisk = new NChar(7).initial("*");
    // COB: 005600   05  LIT-TRANTYPE-TABLE      PIC X(30)       VALUE
    // COB: 005700                                             'TRANSACTION_TYPE '.
    public NChar litTrantypeTable = new NChar(30).initial("TRANSACTION_TYPE ");
    // COB: 005800   05  LIT-DELETE-FLAG         PIC X(1)        VALUE 'D'.
    public NChar litDeleteFlag = new NChar(1).initial("D");
    // COB: 005900   05  LIT-UPDATE-FLAG         PIC X(1)        VALUE 'U'.
    public NChar litUpdateFlag = new NChar(1).initial("U");
    // COB: 006000   05  WS-MAX-SCREEN-LINES     PIC S9(4)      COMP VALUE 7.
    public NInt16 wsMaxScreenLines = new NInt16().initial(7);

    // COB: 006500    05 LIT-ALL-ALPHANUM-FROM-X.
    public static class LitAllAlphanumFromX extends NGroup {
      // COB: 006600       10 LIT-ALL-ALPHA-FROM-X.
      public static class LitAllAlphaFromX extends NGroup {
        // COB: 006700          15 LIT-UPPER                       PIC X(26)
        // COB: 006800                           VALUE 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.
        public NChar litUpper = new NChar(26).initial("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        // COB: 006900          15 LIT-LOWER                       PIC X(26)
        // COB: 007000                           VALUE 'abcdefghijklmnopqrstuvwxyz'.
        public NChar litLower = new NChar(26).initial("abcdefghijklmnopqrstuvwxyz");
      }

      public LitAllAlphaFromX litAllAlphaFromX = new LitAllAlphaFromX();
      // COB: 007100       10 LIT-NUMBERS                        PIC X(10)
      // COB: 007200                           VALUE '0123456789'.
      public NChar litNumbers = new NChar(10).initial("0123456789");
    }

    public LitAllAlphanumFromX litAllAlphanumFromX = new LitAllAlphanumFromX();
  }

  // COB: 007700 01  LIT-ALL-ALPHA-FROM     PIC X(52) VALUE SPACES.
  public NChar litAllAlphaFrom = new NChar(52).initial(NChar.Space);
  // COB: 007800 01  LIT-ALL-ALPHANUM-FROM  PIC X(62) VALUE SPACES.
  public NChar litAllAlphanumFrom = new NChar(62).initial(NChar.Space);
  // COB: 007900 01  LIT-ALL-NUM-FROM       PIC X(10) VALUE SPACES.
  public NChar litAllNumFrom = new NChar(10).initial(NChar.Space);
  // COB: 008000 77  LIT-ALPHA-SPACES-TO    PIC X(52) VALUE SPACES.
  public NChar litAlphaSpacesTo = new NChar(52).initial(NChar.Space);
  // COB: 008100 77  LIT-ALPHANUM-SPACES-TO PIC X(62) VALUE SPACES.
  public NChar litAlphanumSpacesTo = new NChar(62).initial(NChar.Space);
  // COB: 008200 77  LIT-NUM-SPACES-TO      PIC X(10) VALUE SPACES.
  public NChar litNumSpacesTo = new NChar(10).initial(NChar.Space);
  // COB: 008400 01  WS-MISC-STORAGE.
  public WsMiscStorage wsMiscStorage = new WsMiscStorage();

  public static class WsMiscStorage extends NGroup {

    // COB: 008900   05 WS-CICS-PROCESSNG-VARS.
    public static class WsCicsProcessngVars extends NGroup {
      // COB: 009000      07 WS-RESP-CD            PIC S9(9) COMP VALUE ZEROS.
      public NInt32 wsRespCd = new NInt32().initial(0);
      // COB: 009100      07 WS-REAS-CD            PIC S9(9) COMP VALUE ZEROS.
      public NInt32 wsReasCd = new NInt32().initial(0);
      // COB: 009200      07 WS-TRANID             PIC X(4)       VALUE SPACES.
      public NChar wsTranid = new NChar(4).initial(NChar.Space);
    }

    public WsCicsProcessngVars wsCicsProcessngVars = new WsCicsProcessngVars();
    // COB: 009800   05 WS-INPUT-FLAG                          PIC X(1).
    public NChar wsInputFlag = new NChar(1);

    // COB: 009900     88  INPUT-OK                            VALUES '0'
    public boolean inputOk() {
      return wsInputFlag.equals("0") || wsInputFlag.isSpaces() || wsInputFlag.isLowValues();
    }

    public void setInputOk(boolean value) {
      if (value) wsInputFlag.setValue("0");
    }

    // COB: 010200     88  INPUT-ERROR                         VALUE '1'.
    public boolean inputError() {
      return wsInputFlag.equals("1");
    }

    public void setInputError(boolean value) {
      if (value) wsInputFlag.setValue("1");
    }

    // COB: 010300   05  WS-EDIT-TYPE-FLAG                     PIC X(1).
    public NChar wsEditTypeFlag = new NChar(1);

    // COB: 010400     88  FLG-TYPEFILTER-NOT-OK               VALUE '0'.
    public boolean flgTypefilterNotOk() {
      return wsEditTypeFlag.equals("0");
    }

    public void setFlgTypefilterNotOk(boolean value) {
      if (value) wsEditTypeFlag.setValue("0");
    }

    // COB: 010500     88  FLG-TYPEFILTER-ISVALID              VALUE '1'.
    public boolean flgTypefilterIsvalid() {
      return wsEditTypeFlag.equals("1");
    }

    public void setFlgTypefilterIsvalid(boolean value) {
      if (value) wsEditTypeFlag.setValue("1");
    }

    // COB: 010600     88  FLG-TYPEFILTER-BLANK                VALUE ' '.
    public boolean flgTypefilterBlank() {
      return wsEditTypeFlag.isSpaces();
    }

    public void setFlgTypefilterBlank(boolean value) {
      if (value) wsEditTypeFlag.setValue(" ");
    }

    // COB: 010700   05  WS-EDIT-DESC-FLAG                     PIC X(1).
    public NChar wsEditDescFlag = new NChar(1);

    // COB: 010800     88  FLG-DESCFILTER-NOT-OK               VALUE '0'.
    public boolean flgDescfilterNotOk() {
      return wsEditDescFlag.equals("0");
    }

    public void setFlgDescfilterNotOk(boolean value) {
      if (value) wsEditDescFlag.setValue("0");
    }

    // COB: 010900     88  FLG-DESCFILTER-ISVALID              VALUE '1'.
    public boolean flgDescfilterIsvalid() {
      return wsEditDescFlag.equals("1");
    }

    public void setFlgDescfilterIsvalid(boolean value) {
      if (value) wsEditDescFlag.setValue("1");
    }

    // COB: 011000     88  FLG-DESCFILTER-BLANK                VALUE ' '.
    public boolean flgDescfilterBlank() {
      return wsEditDescFlag.isSpaces();
    }

    public void setFlgDescfilterBlank(boolean value) {
      if (value) wsEditDescFlag.setValue(" ");
    }

    // COB: 011100   05 WS-TYPEFILTER-CHANGED                  PIC X(1).
    public NChar wsTypefilterChanged = new NChar(1);

    // COB: 011200     88  FLG-TYPEFILTER-CHANGED-NO           VALUE LOW-VALUES.
    public boolean flgTypefilterChangedNo() {
      return wsTypefilterChanged.isLowValues();
    }

    public void setFlgTypefilterChangedNo(boolean value) {
      if (value) wsTypefilterChanged.lowValues();
    }

    // COB: 011300     88  FLG-TYPEFILTER-CHANGED-YES          VALUE 'Y'.
    public boolean flgTypefilterChangedYes() {
      return wsTypefilterChanged.equals("Y");
    }

    public void setFlgTypefilterChangedYes(boolean value) {
      if (value) wsTypefilterChanged.setValue("Y");
    }

    // COB: 011400   05 WS-DESCFILTER-CHANGED                  PIC X(1).
    public NChar wsDescfilterChanged = new NChar(1);

    // COB: 011500     88  FLG-DESCFILTER-CHANGED-NO           VALUE LOW-VALUES.
    public boolean flgDescfilterChangedNo() {
      return wsDescfilterChanged.isLowValues();
    }

    public void setFlgDescfilterChangedNo(boolean value) {
      if (value) wsDescfilterChanged.lowValues();
    }

    // COB: 011600     88  FLG-DESCFILTER-CHANGED-YES          VALUE 'Y'.
    public boolean flgDescfilterChangedYes() {
      return wsDescfilterChanged.equals("Y");
    }

    public void setFlgDescfilterChangedYes(boolean value) {
      if (value) wsDescfilterChanged.setValue("Y");
    }

    // COB: 011700   05 WS-ROW-RECORDS-CHANGED                 PIC X(01)
    public NChar[] wsRowRecordsChanged = AbstractNField.occurs(7, new NChar(1));

    public NChar wsRowRecordsChangedAtIndex(int index) {
      return getOccursInstance(wsRowRecordsChanged, index);
    }

    // COB: 011900     88  FLG-ROW-DESCR-CHANGED-NO            VALUE LOW-VALUES.
    public boolean flgRowDescrChangedNo(int index) {
      return wsRowRecordsChangedAtIndex(index).isLowValues();
    }

    public void setFlgRowDescrChangedNo(int index, boolean value) {
      if (value) wsRowRecordsChangedAtIndex(index).lowValues();
    }

    // COB: 012000     88  FLG-ROW-DESCR-CHANGED-YES           VALUE 'Y'.
    public boolean flgRowDescrChangedYes(int index) {
      return wsRowRecordsChangedAtIndex(index).equals("Y");
    }

    public void setFlgRowDescrChangedYes(int index, boolean value) {
      if (value) wsRowRecordsChangedAtIndex(index).setValue("Y");
    }

    // COB: 012100   05 WS-DELETE-STATUS                       PIC X(1).
    public NChar wsDeleteStatus = new NChar(1);

    // COB: 012200     88  FLG-DELETED-NO                      VALUE LOW-VALUES.
    public boolean flgDeletedNo() {
      return wsDeleteStatus.isLowValues();
    }

    public void setFlgDeletedNo(boolean value) {
      if (value) wsDeleteStatus.lowValues();
    }

    // COB: 012300     88  FLG-DELETED-YES                     VALUE 'Y'.
    public boolean flgDeletedYes() {
      return wsDeleteStatus.equals("Y");
    }

    public void setFlgDeletedYes(boolean value) {
      if (value) wsDeleteStatus.setValue("Y");
    }

    // COB: 012400   05 WS-UPDATE-STATUS                       PIC X(1).
    public NChar wsUpdateStatus = new NChar(1);

    // COB: 012500     88  FLG-UPDATED-NO                      VALUE LOW-VALUES.
    public boolean flgUpdatedNo() {
      return wsUpdateStatus.isLowValues();
    }

    public void setFlgUpdatedNo(boolean value) {
      if (value) wsUpdateStatus.lowValues();
    }

    // COB: 012600     88  FLG-UPDATE-COMPLETED                     VALUE 'Y'.
    public boolean flgUpdateCompleted() {
      return wsUpdateStatus.equals("Y");
    }

    public void setFlgUpdateCompleted(boolean value) {
      if (value) wsUpdateStatus.setValue("Y");
    }

    // COB: 012700   05 WS-ROW-SELECTION-CHANGED               PIC X(1).
    public NChar wsRowSelectionChanged = new NChar(1);

    // COB: 012800     88  FLG-ROW-SELECTION-CHANGED-NO        VALUE LOW-VALUES.
    public boolean flgRowSelectionChangedNo() {
      return wsRowSelectionChanged.isLowValues();
    }

    public void setFlgRowSelectionChangedNo(boolean value) {
      if (value) wsRowSelectionChanged.lowValues();
    }

    // COB: 012900     88  FLG-ROW-SELECTION-CHANGED-YES       VALUE 'Y'.
    public boolean flgRowSelectionChangedYes() {
      return wsRowSelectionChanged.equals("Y");
    }

    public void setFlgRowSelectionChangedYes(boolean value) {
      if (value) wsRowSelectionChanged.setValue("Y");
    }

    // COB: 013000   05 WS-BAD-SELECTION-ACTION                PIC X(1).
    public NChar wsBadSelectionAction = new NChar(1);

    // COB: 013100     88  FLG-BAD-ACTIONS-SELECTED-NO         VALUE LOW-VALUES.
    public boolean flgBadActionsSelectedNo() {
      return wsBadSelectionAction.isLowValues();
    }

    public void setFlgBadActionsSelectedNo(boolean value) {
      if (value) wsBadSelectionAction.lowValues();
    }

    // COB: 013200     88  FLG-BAD-ACTIONS-SELECTED-YES        VALUE 'Y'.
    public boolean flgBadActionsSelectedYes() {
      return wsBadSelectionAction.equals("Y");
    }

    public void setFlgBadActionsSelectedYes(boolean value) {
      if (value) wsBadSelectionAction.setValue("Y");
    }

    // COB: 013300   05 WS-ARRAY-DESCRIPTION-FLGS              PIC X(1).
    public NChar wsArrayDescriptionFlgs = new NChar(1);

    // COB: 013400     88  FLG-ROW-DESCRIPTION-ISVALID         VALUE LOW-VALUES
    public boolean flgRowDescriptionIsvalid() {
      return wsArrayDescriptionFlgs.isLowValues() || wsArrayDescriptionFlgs.isSpaces();
    }

    public void setFlgRowDescriptionIsvalid(boolean value) {
      if (value) wsArrayDescriptionFlgs.lowValues();
    }

    // COB: 013600     88  FLG-ROW-DESCRIPTION-NOT-OK          VALUE '0'.
    public boolean flgRowDescriptionNotOk() {
      return wsArrayDescriptionFlgs.equals("0");
    }

    public void setFlgRowDescriptionNotOk(boolean value) {
      if (value) wsArrayDescriptionFlgs.setValue("0");
    }

    // COB: 013700     88  FLG-ROW-DESCRIPTION-BLANK           VALUE 'B'.
    public boolean flgRowDescriptionBlank() {
      return wsArrayDescriptionFlgs.equals("B");
    }

    public void setFlgRowDescriptionBlank(boolean value) {
      if (value) wsArrayDescriptionFlgs.setValue("B");
    }

    // COB: 013800   05  WS-DATACHANGED-FLAG                   PIC X(1).
    public NChar wsDatachangedFlag = new NChar(1);

    // COB: 013900     88  NO-CHANGES-FOUND                    VALUE '0'.
    public boolean noChangesFound() {
      return wsDatachangedFlag.equals("0");
    }

    public void setNoChangesFound(boolean value) {
      if (value) wsDatachangedFlag.setValue("0");
    }

    // COB: 014000     88  CHANGES-HAVE-OCCURRED               VALUE '1'.
    public boolean changesHaveOccurred() {
      return wsDatachangedFlag.equals("1");
    }

    public void setChangesHaveOccurred(boolean value) {
      if (value) wsDatachangedFlag.setValue("1");
    }

    // COB: 014300   05  WS-GENERIC-EDITS.
    public static class WsGenericEdits extends NGroup {
      // COB: 014400     10 WS-EDIT-VARIABLE-NAME                PIC X(25).
      public NChar wsEditVariableName = new NChar(25);
      // COB: 014600     10 WS-EDIT-ALPHANUM-ONLY                PIC X(256).
      public NChar wsEditAlphanumOnly = new NChar(256);
      // COB: 014700     10 WS-EDIT-ALPHANUM-LENGTH              PIC S9(4) COMP-3.
      public NPacked wsEditAlphanumLength = new NPacked(3);
      // COB: 014900     10 WS-EDIT-ALPHANUM-ONLY-FLAGS          PIC X(1).
      public NChar wsEditAlphanumOnlyFlags = new NChar(1);

      // COB: 015000        88  FLG-ALPHNANUM-ISVALID            VALUE LOW-VALUES.
      public boolean flgAlphnanumIsvalid() {
        return wsEditAlphanumOnlyFlags.isLowValues();
      }

      public void setFlgAlphnanumIsvalid(boolean value) {
        if (value) wsEditAlphanumOnlyFlags.lowValues();
      }

      // COB: 015100        88  FLG-ALPHNANUM-NOT-OK             VALUE '0'.
      public boolean flgAlphnanumNotOk() {
        return wsEditAlphanumOnlyFlags.equals("0");
      }

      public void setFlgAlphnanumNotOk(boolean value) {
        if (value) wsEditAlphanumOnlyFlags.setValue("0");
      }

      // COB: 015200        88  FLG-ALPHNANUM-BLANK              VALUE 'B'.
      public boolean flgAlphnanumBlank() {
        return wsEditAlphanumOnlyFlags.equals("B");
      }

      public void setFlgAlphnanumBlank(boolean value) {
        if (value) wsEditAlphanumOnlyFlags.setValue("B");
      }
    }

    public WsGenericEdits wsGenericEdits = new WsGenericEdits();

    // COB: 015400   05  WS-OTHER-EDIT-VARS.
    public static class WsOtherEditVars extends NGroup {
      // COB: 015500     10 WS-RECORDS-COUNT                     PIC S9(4) COMP-3
      // COB: 015600                                             VALUE 0.
      public NPacked wsRecordsCount = new NPacked(3).initial(0);
    }

    public WsOtherEditVars wsOtherEditVars = new WsOtherEditVars();

    // COB: 016500   05 WS-SCREEN-DATA-IN.
    public static class WsScreenDataIn extends NGroup {
      // COB: 016600      10 WS-ALL-ROWS-IN                      PIC X(364).
      public NChar wsAllRowsIn = new NChar(364);

      // COB: 016700      10 FILLER REDEFINES WS-ALL-ROWS-IN.
      public static class Filler167 extends NGroup {
        // COB: 016800         15 WS-SCREEN-ROWS-IN OCCURS  7 TIMES.
        public static class WsScreenRowsIn extends NGroup {
          // COB: 016900            20 WS-EACH-ROW-IN.
          public static class WsEachRowIn extends NGroup {
            // COB: 017000               25 WS-EACH-TTYP-IN.
            public static class WsEachTtypIn extends NGroup {
              // COB: 017100                  30 WS-ROW-TR-CODE-IN       PIC X(02).
              public NChar wsRowTrCodeIn = new NChar(2);
              // COB: 017200                  30 WS-ROW-TR-DESC-IN       PIC X(50).
              public NChar wsRowTrDescIn = new NChar(50);
            }

            public WsEachTtypIn wsEachTtypIn = new WsEachTtypIn();
          }

          public WsEachRowIn wsEachRowIn = new WsEachRowIn();
        }

        public WsScreenRowsIn[] wsScreenRowsIn = AbstractNField.occurs(7, new WsScreenRowsIn());

        public WsScreenRowsIn wsScreenRowsInAtIndex(int index) {
          return getOccursInstance(wsScreenRowsIn, index);
        }
      }

      public Filler167 filler167 = (Filler167) new Filler167().redefines(wsAllRowsIn);
    }

    public WsScreenDataIn wsScreenDataIn = new WsScreenDataIn();
    // COB: 017500   05 WS-EDIT-SELECT-COUNTER                 PIC S9(04)
    // COB: 017600                                             USAGE COMP-3
    // COB: 017700                                             VALUE 0.
    public NPacked wsEditSelectCounter = new NPacked(3).initial(0);
    // COB: 017800   05 WS-EDIT-SELECT-FLAGS                   PIC X(7)
    // COB: 017900                                             VALUE LOW-VALUES.
    public NChar wsEditSelectFlags = new NChar(7).initial(NChar.LowValue);

    // COB: 018000   05 FILLER  REDEFINES  WS-EDIT-SELECT-FLAGS.
    public static class Filler180 extends NGroup {
      // COB: 018100      10 WS-EDIT-SELECT                      PIC X(1)
      public NChar[] wsEditSelect = AbstractNField.occurs(7, new NChar(1));

      public NChar wsEditSelectAtIndex(int index) {
        return getOccursInstance(wsEditSelect, index);
      }

      // COB: 018300         88 SELECT-OK                        VALUES 'D', 'U'.
      public boolean selectOk(int index) {
        return wsEditSelectAtIndex(index).equals("D") || wsEditSelectAtIndex(index).equals("U");
      }

      public void setSelectOk(int index, boolean value) {
        if (value) wsEditSelectAtIndex(index).setValue("D");
      }

      // COB: 018400         88 DELETE-REQUESTED-ON              VALUE 'D'.
      public boolean deleteRequestedOn(int index) {
        return wsEditSelectAtIndex(index).equals("D");
      }

      public void setDeleteRequestedOn(int index, boolean value) {
        if (value) wsEditSelectAtIndex(index).setValue("D");
      }

      // COB: 018500         88 UPDATE-REQUESTED-ON              VALUE 'U'.
      public boolean updateRequestedOn(int index) {
        return wsEditSelectAtIndex(index).equals("U");
      }

      public void setUpdateRequestedOn(int index, boolean value) {
        if (value) wsEditSelectAtIndex(index).setValue("U");
      }

      // COB: 018600         88 SELECT-BLANK                     VALUES
      public boolean selectBlank(int index) {
        return wsEditSelectAtIndex(index).isSpaces() || wsEditSelectAtIndex(index).isLowValues();
      }

      public void setSelectBlank(int index, boolean value) {
        if (value) wsEditSelectAtIndex(index).setValue(" ");
      }
    }

    public Filler180 filler180 = (Filler180) new Filler180().redefines(wsEditSelectFlags);
    // COB: 019000   05 WS-EDIT-SELECT-ERROR-FLAGS             PIC X(7)
    // COB: 019100                                             VALUE LOW-VALUES.
    public NChar wsEditSelectErrorFlags = new NChar(7).initial(NChar.LowValue);

    // COB: 019200   05 FILLER  REDEFINES WS-EDIT-SELECT-ERROR-FLAGS.
    public static class Filler192 extends NGroup {
      // COB: 019300      10 WS-EDIT-SELECT-ERRORS               OCCURS 7 TIMES.
      public static class WsEditSelectErrors extends NGroup {
        // COB: 019400         20 WS-ROW-TRTSELECT-ERROR           PIC X(1).
        public NChar wsRowTrtselectError = new NChar(1);

        // COB: 019500            88 WS-ROW-SELECT-ERROR           VALUE '1'.
        public boolean wsRowSelectError() {
          return wsRowTrtselectError.equals("1");
        }

        public void setWsRowSelectError(boolean value) {
          if (value) wsRowTrtselectError.setValue("1");
        }
      }

      public WsEditSelectErrors[] wsEditSelectErrors =
          AbstractNField.occurs(7, new WsEditSelectErrors());

      public WsEditSelectErrors wsEditSelectErrorsAtIndex(int index) {
        return getOccursInstance(wsEditSelectErrors, index);
      }
    }

    public Filler192 filler192 = (Filler192) new Filler192().redefines(wsEditSelectErrorFlags);

    // COB: 019700   05 WS-SUBSCRIPT-VARS.
    public static class WsSubscriptVars extends NGroup {
      // COB: 019800      10 I                                  PIC S9(4) COMP
      // COB: 019900                                            VALUE 0.
      public NInt16 i = new NInt16().initial(0);
      // COB: 020000      10 I-SELECTED                         PIC S9(4) COMP
      // COB: 020100                                            VALUE 0.
      public NInt16 iSelected = new NInt16().initial(0);
    }

    public WsSubscriptVars wsSubscriptVars = new WsSubscriptVars();

    // COB: 020200   05 WS-ACTIONS-SELECTED.
    public static class WsActionsSelected extends NGroup {
      // COB: 020300      07 WS-ACTIONS-REQUESTED               PIC S9(04)
      // COB: 020400                                            USAGE COMP-3
      // COB: 020500                                            VALUE 0.
      public NPacked wsActionsRequested = new NPacked(3).initial(0);

      // COB: 020600         88 WS-ONLY-1-ACTION                VALUE 1.
      public boolean wsOnly1Action() {
        return wsActionsRequested.equals(1);
      }

      public void setWsOnly1Action(boolean value) {
        if (value) wsActionsRequested.setValue(1);
      }

      // COB: 020700         88 WS-MORETHAN1ACTION              VALUES 2 THRU 7.
      public boolean wsMorethan1action() {
        return wsActionsRequested.inRange(2, 7);
      }

      public void setWsMorethan1action(boolean value) {
        if (value) wsActionsRequested.setValue(2);
      }

      // COB: 020800      07 WS-DELETES-REQUESTED               PIC S9(04)
      // COB: 020900                                            USAGE COMP-3
      // COB: 021000                                            VALUE 0.
      public NPacked wsDeletesRequested = new NPacked(3).initial(0);
      // COB: 021100      07 WS-UPDATES-REQUESTED               PIC S9(04)
      // COB: 021200                                            USAGE COMP-3
      // COB: 021300                                            VALUE 0.
      public NPacked wsUpdatesRequested = new NPacked(3).initial(0);
      // COB: 021400      07 WS-NO-ACTIONS-SELECTED             PIC S9(04)
      // COB: 021500                                            COMP-3
      // COB: 021600                                            VALUE 0.
      public NPacked wsNoActionsSelected = new NPacked(3).initial(0);
    }

    public WsActionsSelected wsActionsSelected = new WsActionsSelected();
    // COB: 021700   05 WS-VALID-ACTIONS-SELECTED             PIC S9(04)
    // COB: 021800                                            USAGE COMP-3
    // COB: 021900                                            VALUE 0.
    public NPacked wsValidActionsSelected = new NPacked(3).initial(0);

    // COB: 022000      88 WS-ONLY-1-VALID-ACTION             VALUE 1.
    public boolean wsOnly1ValidAction() {
      return wsValidActionsSelected.equals(1);
    }

    public void setWsOnly1ValidAction(boolean value) {
      if (value) wsValidActionsSelected.setValue(1);
    }

    // COB: 022500   05 CICS-OUTPUT-EDIT-VARS.
    public static class CicsOutputEditVars extends NGroup {
      // COB: 022600     10  TRAN-TYPE-CD-X                      PIC X(02).
      public NChar tranTypeCdX = new NChar(2);
      // COB: 022700     10  TRAN-TYPE-CD-N REDEFINES TRAN-TYPE-CD-X
      public NZoned tranTypeCdN = new NZoned(2, false).redefines(tranTypeCdX);
      // COB: 022900     10  FLG-PROTECT-SELECT-ROWS             PIC X(1).
      public NChar flgProtectSelectRows = new NChar(1);

      // COB: 023000     88  FLG-PROTECT-SELECT-ROWS-NO          VALUE '0'.
      public boolean flgProtectSelectRowsNo() {
        return flgProtectSelectRows.equals("0");
      }

      public void setFlgProtectSelectRowsNo(boolean value) {
        if (value) flgProtectSelectRows.setValue("0");
      }

      // COB: 023100     88  FLG-PROTECT-SELECT-ROWS-YES         VALUE '1'.
      public boolean flgProtectSelectRowsYes() {
        return flgProtectSelectRows.equals("1");
      }

      public void setFlgProtectSelectRowsYes(boolean value) {
        if (value) flgProtectSelectRows.setValue("1");
      }
    }

    public CicsOutputEditVars cicsOutputEditVars = new CicsOutputEditVars();
    // COB: 023500   05  WS-LONG-MSG                           PIC X(800).
    public NChar wsLongMsg = new NChar(800);
    // COB: 023600   05  WS-INFO-MSG                           PIC X(45).
    public NChar wsInfoMsg = new NChar(45);

    // COB: 023700     88  WS-NO-INFO-MESSAGE                  VALUES
    public boolean wsNoInfoMessage() {
      return wsInfoMsg.isSpaces() || wsInfoMsg.isLowValues();
    }

    public void setWsNoInfoMessage(boolean value) {
      if (value) wsInfoMsg.setValue(SPACES);
    }

    // COB: 023900     88  WS-INFORM-REC-ACTIONS               VALUE
    public boolean wsInformRecActions() {
      return wsInfoMsg.equals("Type U to update, D to delete any record");
    }

    public void setWsInformRecActions(boolean value) {
      if (value) wsInfoMsg.setValue("Type U to update, D to delete any record");
    }

    // COB: 024100     88  WS-INFORM-DELETE                    VALUE
    public boolean wsInformDelete() {
      return wsInfoMsg.equals("Delete HIGHLIGHTED row ? Press F10 to confirm");
    }

    public void setWsInformDelete(boolean value) {
      if (value) wsInfoMsg.setValue("Delete HIGHLIGHTED row ? Press F10 to confirm");
    }

    // COB: 024300     88  WS-INFORM-UPDATE                    VALUE
    public boolean wsInformUpdate() {
      return wsInfoMsg.equals("Update HIGHLIGHTED row. Press F10 to save");
    }

    public void setWsInformUpdate(boolean value) {
      if (value) wsInfoMsg.setValue("Update HIGHLIGHTED row. Press F10 to save");
    }

    // COB: 024500     88  WS-INFORM-DELETE-SUCCESS            VALUE
    public boolean wsInformDeleteSuccess() {
      return wsInfoMsg.equals("HIGHLIGHTED row deleted.Hit Enter to continue");
    }

    public void setWsInformDeleteSuccess(boolean value) {
      if (value) wsInfoMsg.setValue("HIGHLIGHTED row deleted.Hit Enter to continue");
    }

    // COB: 024700     88  WS-INFORM-UPDATE-SUCCESS            VALUE
    public boolean wsInformUpdateSuccess() {
      return wsInfoMsg.equals("HIGHLIGHTED row was updated");
    }

    public void setWsInformUpdateSuccess(boolean value) {
      if (value) wsInfoMsg.setValue("HIGHLIGHTED row was updated");
    }

    // COB: 024900   05  WS-RETURN-MSG                         PIC X(75).
    public NChar wsReturnMsg = new NChar(75);

    // COB: 025000     88  WS-RETURN-MSG-OFF                   VALUE SPACES.
    public boolean wsReturnMsgOff() {
      return wsReturnMsg.isSpaces();
    }

    public void setWsReturnMsgOff(boolean value) {
      if (value) wsReturnMsg.setValue(SPACES);
    }

    // COB: 025100     88  WS-EXIT-MESSAGE                     VALUE
    public boolean wsExitMessage() {
      return wsReturnMsg.equals("PF03 pressed. Exiting");
    }

    public void setWsExitMessage(boolean value) {
      if (value) wsReturnMsg.setValue("PF03 pressed. Exiting");
    }

    // COB: 025300     88  WS-MESG-NO-RECORDS-FOUND            VALUE
    public boolean wsMesgNoRecordsFound() {
      return wsReturnMsg.equals("No records found for this search condition.");
    }

    public void setWsMesgNoRecordsFound(boolean value) {
      if (value) wsReturnMsg.setValue("No records found for this search condition.");
    }

    // COB: 025500     88  WS-MESG-NO-MORE-RECORDS             VALUE
    public boolean wsMesgNoMoreRecords() {
      return wsReturnMsg.equals("No more pages for these search conditions");
    }

    public void setWsMesgNoMoreRecords(boolean value) {
      if (value) wsReturnMsg.setValue("No more pages for these search conditions");
    }

    // COB: 025700     88  WS-MESG-MORE-THAN-1-ACTION          VALUE
    public boolean wsMesgMoreThan1Action() {
      return wsReturnMsg.equals("Please select only 1 action");
    }

    public void setWsMesgMoreThan1Action(boolean value) {
      if (value) wsReturnMsg.setValue("Please select only 1 action");
    }

    // COB: 025900     88  WS-MESG-INVALID-ACTION-CODE         VALUE
    public boolean wsMesgInvalidActionCode() {
      return wsReturnMsg.equals("Action code selected is invalid");
    }

    public void setWsMesgInvalidActionCode(boolean value) {
      if (value) wsReturnMsg.setValue("Action code selected is invalid");
    }

    // COB: 026100     88  WS-MESG-NO-CHANGES-DETECTED         VALUE
    public boolean wsMesgNoChangesDetected() {
      return wsReturnMsg.equals("No change detected with respect to database values.");
    }

    public void setWsMesgNoChangesDetected(boolean value) {
      if (value) wsReturnMsg.setValue("No change detected with respect to database values.");
    }

    // COB: 026300   05  WS-PFK-FLAG                           PIC X(1).
    public NChar wsPfkFlag = new NChar(1);

    // COB: 026400     88  PFK-VALID                           VALUE '0'.
    public boolean pfkValid() {
      return wsPfkFlag.equals("0");
    }

    public void setPfkValid(boolean value) {
      if (value) wsPfkFlag.setValue("0");
    }

    // COB: 026500     88  PFK-INVALID                         VALUE '1'.
    public boolean pfkInvalid() {
      return wsPfkFlag.equals("1");
    }

    public void setPfkInvalid(boolean value) {
      if (value) wsPfkFlag.setValue("1");
    }

    // COB: 026600   05 WS-STRING-FORMAT-VARS.
    public static class WsStringFormatVars extends NGroup {
      // COB: 026700      10 WS-STRING-MID                      PIC 9(3) VALUE 0.
      public NZoned wsStringMid = new NZoned(3, false).initial(0);
      // COB: 026800      10 WS-STRING-LEN                      PIC 9(3) VALUE 0.
      public NZoned wsStringLen = new NZoned(3, false).initial(0);
      // COB: 026900      10 WS-STRING-OUT                      PIC X(45).
      public NChar wsStringOut = new NChar(45);
    }

    public WsStringFormatVars wsStringFormatVars = new WsStringFormatVars();

    // COB: 027400   05 WS-DATA-FILTERS.
    public static class WsDataFilters extends NGroup {
      // COB: 027500      10  WS-START-KEY                      PIC X(02).
      public NChar wsStartKey = new NChar(2);
      // COB: 027600      10  WS-TYPE-CD-FILTER                 PIC X(02)
      // COB: 027700                                            VALUE SPACES.
      public NChar wsTypeCdFilter = new NChar(2).initial(NChar.Space);
      // COB: 027800      10  WS-TYPE-DESC-FILTER               PIC X(52).
      public NChar wsTypeDescFilter = new NChar(52);

      // COB: 027900      10  WS-TYPE-CD-DELETE-FILTER.
      public static class WsTypeCdDeleteFilter extends NGroup {
        // COB: 028000          15 FILLER                         PIC X(01)
        // COB: 028100                                            VALUE '('.
        public NChar filler280 = new NChar(1).initial("(");

        // COB: 028200          15 WS-TYPE-CD-DELETE-FILTER-X.
        public static class WsTypeCdDeleteFilterX extends NGroup {
          // COB: 028300             20 WS-TYPE-CD-DELETE-KEYS      OCCURS 7 TIMES.
          public static class WsTypeCdDeleteKeys extends NGroup {
            // COB: 028400                25 FILLER                   PIC X(01)
            // COB: 028500                                            VALUE QUOTE.
            public NChar filler284 = new NChar(1).initial("'");
            // COB: 028600                25 WS-TYPE-CD-DELETE-KEY    PIC X(02)
            // COB: 028700                                            VALUE SPACES.
            public NChar wsTypeCdDeleteKey = new NChar(2).initial(NChar.Space);
            // COB: 028800                25 FILLER                   PIC X(01)
            // COB: 028900                                            VALUE QUOTE.
            public NChar filler288 = new NChar(1).initial("'");
            // COB: 029000                25 FILLER                   PIC X(01)
            // COB: 029100                                            VALUE ','.
            public NChar filler290 = new NChar(1).initial(",");
          }

          public WsTypeCdDeleteKeys[] wsTypeCdDeleteKeys =
              AbstractNField.occurs(7, new WsTypeCdDeleteKeys());

          public WsTypeCdDeleteKeys wsTypeCdDeleteKeysAtIndex(int index) {
            return getOccursInstance(wsTypeCdDeleteKeys, index);
          }

          // COB: 029200             20 WS-DUMMY.
          public static class WsDummy extends NGroup {
            // COB: 029300                25 FILLER                   PIC X(01)
            // COB: 029400                                            VALUE QUOTE.
            public NChar filler293 = new NChar(1).initial("'");
            // COB: 029500                25 FILLER                   PIC X(01)
            // COB: 029600                                            VALUE SPACE.
            public NChar filler295 = new NChar(1).initial(NChar.Space);
            // COB: 029700                25 FILLER                   PIC X(01)
            // COB: 029800                                            VALUE QUOTE.
            public NChar filler297 = new NChar(1).initial("'");
          }

          public WsDummy wsDummy = new WsDummy();
        }

        public WsTypeCdDeleteFilterX wsTypeCdDeleteFilterX = new WsTypeCdDeleteFilterX();
        // COB: 030000          15 FILLER                         PIC X(1)
        // COB: 030100                                            VALUE ')'.
        public NChar filler300 = new NChar(1).initial(")");
      }

      public WsTypeCdDeleteFilter wsTypeCdDeleteFilter = new WsTypeCdDeleteFilter();
    }

    public WsDataFilters wsDataFilters = new WsDataFilters();

    // COB: 037101   05  WS-DB2-COMMON-VARS.
    public static class WsDb2CommonVars extends NGroup {
      // COB: 037102       10 WS-DISP-SQLCODE                    PIC ----9.
      public NZoned wsDispSqlcode = new NZoned(5).formatAs("----0");
      // COB: 037103       10 WS-DUMMY-DB2-INT                   PIC S9(4) COMP-3
      // COB: 037104                                             VALUE 0.
      public NPacked wsDummyDb2Int = new NPacked(3).initial(0);
      // COB: 037105       10 WS-DB2-PROCESSING-FLAG             PIC X(1).
      public NChar wsDb2ProcessingFlag = new NChar(1);

      // COB: 037106          88  WS-DB2-OK                      VALUE '0'.
      public boolean wsDb2Ok() {
        return wsDb2ProcessingFlag.equals("0");
      }

      public void setWsDb2Ok(boolean value) {
        if (value) wsDb2ProcessingFlag.setValue("0");
      }

      // COB: 037107          88  WS-DB2-ERROR                   VALUE '1'.
      public boolean wsDb2Error() {
        return wsDb2ProcessingFlag.equals("1");
      }

      public void setWsDb2Error(boolean value) {
        if (value) wsDb2ProcessingFlag.setValue("1");
      }

      // COB: 037108       10 WS-DB2-CURRENT-ACTION              PIC X(72)
      // COB: 037109                                             VALUE SPACES.
      public NChar wsDb2CurrentAction = new NChar(72).initial(NChar.Space);
    }

    public WsDb2CommonVars wsDb2CommonVars = new WsDb2CommonVars();

    // COB: 037200   05  WS-DSNTIAC-FORMATTED.
    public static class WsDsntiacFormatted extends NGroup {
      // COB: 037300       10  WS-DSNTIAC-MESG-LEN   PIC S9(4) USAGE COMP VALUE +720.
      public NInt16 wsDsntiacMesgLen = new NInt16().initial(720);

      // COB: 037400       10  WS-DSNTIAC-FMTD-TEXT.
      public static class WsDsntiacFmtdText extends NGroup {
        // COB: 037500           15 WS-DSNTIAC-FMTD-TEXT-LINE
        // COB: 037600                                 PIC X(72)
        // COB: 037700                                 OCCURS 10 TIMES
        // COB: 037800                                 VALUE SPACES.
        public NChar[] wsDsntiacFmtdTextLine =
            AbstractNField.occurs(10, new NChar(72), NChar.Space);

        public NChar wsDsntiacFmtdTextLineAtIndex(int index) {
          return getOccursInstance(wsDsntiacFmtdTextLine, index);
        }
      }

      public WsDsntiacFmtdText wsDsntiacFmtdText = new WsDsntiacFmtdText();
    }

    public WsDsntiacFormatted wsDsntiacFormatted = new WsDsntiacFormatted();
    // COB: 038000    05 WS-DSNTIAC-LRECL          PIC S9(4) USAGE COMP VALUE +72.
    public NInt16 wsDsntiacLrecl = new NInt16().initial(72);

    // COB: 038100    05 WS-DSNTIAC-ERROR.
    public static class WsDsntiacError extends NGroup {
      // COB: 038200       10 WS-DSNTIAC-ERR-MSG     PIC X(10) VALUE 'DSNTIAC CD'.
      public NChar wsDsntiacErrMsg = new NChar(10).initial("DSNTIAC CD");
      // COB: 038300       10 WS-DSNTIAC-ERR-CD-X    PIC X(02) VALUE SPACES.
      public NChar wsDsntiacErrCdX = new NChar(2).initial(NChar.Space);
      // COB:              10 WS-DSNTIAC-ERR-CD      REDEFINES
      // COB:                 WS-DSNTIAC-ERR-CD-X    PIC 9(02).
      public NZoned wsDsntiacErrCd = new NZoned(2, false).redefines(wsDsntiacErrCdX);
    }

    public WsDsntiacError wsDsntiacError = new WsDsntiacError();

    // COB: 030900   05 WS-SCREEN-EDIT-VARS.
    public static class WsScreenEditVars extends NGroup {
      // COB: 031000      10 WS-IN-TYPE-CD                      PIC X(02)
      // COB: 031100                                            VALUE SPACES.
      public NChar wsInTypeCd = new NChar(2).initial(NChar.Space);
      // COB: 031200      10 WS-IN-TYPE-CD-N    REDEFINES WS-IN-TYPE-CD PIC 9(02).
      public NZoned wsInTypeCdN = new NZoned(2, false).redefines(wsInTypeCd);
      // COB: 031300      10 WS-IN-TYPE-DESC                    PIC X(50).
      public NChar wsInTypeDesc = new NChar(50);
    }

    public WsScreenEditVars wsScreenEditVars = new WsScreenEditVars();
    // COB: 031800   05  WS-ROW-NUMBER               PIC S9(4) COMP VALUE 0.
    public NInt16 wsRowNumber = new NInt16().initial(0);
    // COB: 032000   05  WS-RECORDS-TO-PROCESS-FLAG            PIC X(1).
    public NChar wsRecordsToProcessFlag = new NChar(1);

    // COB: 032100     88  READ-LOOP-EXIT                      VALUE '0'.
    public boolean readLoopExit() {
      return wsRecordsToProcessFlag.equals("0");
    }

    public void setReadLoopExit(boolean value) {
      if (value) wsRecordsToProcessFlag.setValue("0");
    }

    // COB: 032200     88  MORE-RECORDS-TO-READ                VALUE '1'.
    public boolean moreRecordsToRead() {
      return wsRecordsToProcessFlag.equals("1");
    }

    public void setMoreRecordsToRead(boolean value) {
      if (value) wsRecordsToProcessFlag.setValue("1");
    }
  }

  public Cvcrd01y cvcrd01y = new Cvcrd01y();
  public Dcltrtyp dcltrtyp = new Dcltrtyp();
  public Cocom01y cocom01y = new Cocom01y();
  // COB: 037700 01 WS-THIS-PROGCOMMAREA.
  public WsThisProgcommarea wsThisProgcommarea = new WsThisProgcommarea();

  public static class WsThisProgcommarea extends NGroup {

    // COB: 037800      10 WS-CA-TYPE-CD                          PIC X(02)
    // COB: 037900                                                VALUE SPACES.
    public NChar wsCaTypeCd = new NChar(2).initial(NChar.Space);
    // COB: 038000      10 WS-CA-TYPE-CD-N REDEFINES WS-CA-TYPE-CD PIC 9(02).
    public NZoned wsCaTypeCdN = new NZoned(2, false).redefines(wsCaTypeCd);
    // COB: 038100      10 WS-CA-TYPE-DESC                        PIC X(50).
    public NChar wsCaTypeDesc = new NChar(50);

    // COB: 038600       10 FILLER.
    public static class Filler386 extends NGroup {
      // COB: 038700          15 WS-CA-ALL-ROWS-OUT                 PIC X(364).
      public NChar wsCaAllRowsOut = new NChar(364);

      // COB: 038800          15 FILLER REDEFINES WS-CA-ALL-ROWS-OUT.
      public static class Filler388 extends NGroup {
        // COB: 038900             20 WS-CA-SCREEN-ROWS-OUT   OCCURS  7 TIMES.
        public static class WsCaScreenRowsOut extends NGroup {
          // COB: 039000                30 WS-CA-EACH-ROW-OUT.
          public static class WsCaEachRowOut extends NGroup {
            // COB: 039100                   35 WS-CA-ROW-TR-CODE-OUT     PIC X(02).
            public NChar wsCaRowTrCodeOut = new NChar(2);
            // COB: 039200                   35 WS-CA-ROW-TR-DESC-OUT     PIC X(50).
            public NChar wsCaRowTrDescOut = new NChar(50);
          }

          public WsCaEachRowOut wsCaEachRowOut = new WsCaEachRowOut();
        }

        public WsCaScreenRowsOut[] wsCaScreenRowsOut =
            AbstractNField.occurs(7, new WsCaScreenRowsOut());

        public WsCaScreenRowsOut wsCaScreenRowsOutAtIndex(int index) {
          return getOccursInstance(wsCaScreenRowsOut, index);
        }
      }

      public Filler388 filler388 = (Filler388) new Filler388().redefines(wsCaAllRowsOut);
    }

    public Filler386 filler386 = new Filler386();
    // COB: 039500      10 WS-CA-ROW-SELECTED                     PIC S9(4) COMP
    // COB: 039600                                                VALUE 0.
    public NInt16 wsCaRowSelected = new NInt16().initial(0);

    // COB: 039700      10 WS-CA-PAGING-VARIABLES.
    public static class WsCaPagingVariables extends NGroup {
      // COB: 039800         15 WS-CA-LAST-TTYPEKEY.
      public static class WsCaLastTtypekey extends NGroup {
        // COB: 039900            20  WS-CA-LAST-TR-CODE              PIC X(02).
        public NChar wsCaLastTrCode = new NChar(2);
      }

      public WsCaLastTtypekey wsCaLastTtypekey = new WsCaLastTtypekey();

      // COB: 040000         15 WS-CA-FIRST-TTYPEKEY.
      public static class WsCaFirstTtypekey extends NGroup {
        // COB: 040100            20  WS-CA-FIRST-TR-CODE             PIC X(02).
        public NChar wsCaFirstTrCode = new NChar(2);
      }

      public WsCaFirstTtypekey wsCaFirstTtypekey = new WsCaFirstTtypekey();
      // COB: 040300         15 WS-CA-SCREEN-NUM                    PIC 9(1).
      public NZoned wsCaScreenNum = new NZoned(1, false);

      // COB: 040400            88 CA-FIRST-PAGE                    VALUE 1.
      public boolean caFirstPage() {
        return wsCaScreenNum.equals(1);
      }

      public void setCaFirstPage(boolean value) {
        if (value) wsCaScreenNum.setValue(1);
      }

      // COB: 040500         15 WS-CA-LAST-PAGE-DISPLAYED           PIC 9(1).
      public NZoned wsCaLastPageDisplayed = new NZoned(1, false);

      // COB: 040600            88 CA-LAST-PAGE-SHOWN               VALUE 0.
      public boolean caLastPageShown() {
        return wsCaLastPageDisplayed.equals(0);
      }

      public void setCaLastPageShown(boolean value) {
        if (value) wsCaLastPageDisplayed.setValue(0);
      }

      // COB: 040700            88 CA-LAST-PAGE-NOT-SHOWN           VALUE 9.
      public boolean caLastPageNotShown() {
        return wsCaLastPageDisplayed.equals(9);
      }

      public void setCaLastPageNotShown(boolean value) {
        if (value) wsCaLastPageDisplayed.setValue(9);
      }

      // COB: 040800         15 WS-CA-NEXT-PAGE-IND                 PIC X(1).
      public NChar wsCaNextPageInd = new NChar(1);

      // COB: 040900            88 CA-NEXT-PAGE-NOT-EXISTS          VALUE LOW-VALUES.
      public boolean caNextPageNotExists() {
        return wsCaNextPageInd.isLowValues();
      }

      public void setCaNextPageNotExists(boolean value) {
        if (value) wsCaNextPageInd.lowValues();
      }

      // COB: 041000            88 CA-NEXT-PAGE-EXISTS              VALUE 'Y'.
      public boolean caNextPageExists() {
        return wsCaNextPageInd.equals("Y");
      }

      public void setCaNextPageExists(boolean value) {
        if (value) wsCaNextPageInd.setValue("Y");
      }
    }

    public WsCaPagingVariables wsCaPagingVariables = new WsCaPagingVariables();
    // COB: 041100       10 WS-CA-DELETE-FLAG                     PIC X.
    public NChar wsCaDeleteFlag = new NChar(1);

    // COB: 041200            88 CA-DELETE-NOT-REQUESTED          VALUE LOW-VALUES.
    public boolean caDeleteNotRequested() {
      return wsCaDeleteFlag.isLowValues();
    }

    public void setCaDeleteNotRequested(boolean value) {
      if (value) wsCaDeleteFlag.lowValues();
    }

    // COB: 041300            88 CA-DELETE-REQUESTED              VALUE 'Y'.
    public boolean caDeleteRequested() {
      return wsCaDeleteFlag.equals("Y");
    }

    public void setCaDeleteRequested(boolean value) {
      if (value) wsCaDeleteFlag.setValue("Y");
    }

    // COB: 041400            88 CA-DELETE-SUCCEEDED              VALUE LOW-VALUES.
    public boolean caDeleteSucceeded() {
      return wsCaDeleteFlag.isLowValues();
    }

    public void setCaDeleteSucceeded(boolean value) {
      if (value) wsCaDeleteFlag.lowValues();
    }

    // COB: 041500       10 WS-CA-UPDATE-FLAG                     PIC X.
    public NChar wsCaUpdateFlag = new NChar(1);

    // COB: 041600            88 CA-UPDATE-NOT-REQUESTED          VALUE LOW-VALUES.
    public boolean caUpdateNotRequested() {
      return wsCaUpdateFlag.isLowValues();
    }

    public void setCaUpdateNotRequested(boolean value) {
      if (value) wsCaUpdateFlag.lowValues();
    }

    // COB: 041700            88 CA-UPDATE-REQUESTED              VALUE 'Y'.
    public boolean caUpdateRequested() {
      return wsCaUpdateFlag.equals("Y");
    }

    public void setCaUpdateRequested(boolean value) {
      if (value) wsCaUpdateFlag.setValue("Y");
    }

    // COB: 041800            88 CA-UPDATE-SUCCEEDED              VALUE LOW-VALUES.
    public boolean caUpdateSucceeded() {
      return wsCaUpdateFlag.isLowValues();
    }

    public void setCaUpdateSucceeded(boolean value) {
      if (value) wsCaUpdateFlag.lowValues();
    }
  }

  // COB: 042000 01  WS-COMMAREA                                PIC X(2000).
  public NChar wsCommarea = new NChar(2000);
  public Cottl01y cottl01y = new Cottl01y();
  public Cotrtli cotrtli = new Cotrtli();

  // COB: 043400   01 FILLER REDEFINES CTRTLIAI.
  public static class Filler434 extends NGroup {
    // COB: 043500    05 FILLER                           PIC X(238).
    public NChar filler435 = new NChar(238);

    // COB: 043600    05 WS-ROW-DATAI.
    public static class WsRowDatai extends NGroup {
      // COB: 043700         06 EACH-ROWI OCCURS 7 TIMES.
      public static class EachRowi extends NGroup {
        // COB: 043800            07 TRTSELL                  PIC S9(4) COMP.
        public NInt16 trtsell = new NInt16();
        // COB: 043900            07 TRTSELF                  PIC X.
        public NChar trtself = new NChar(1);

        // COB: 044000            07 FILLER REDEFINES TRTSELF.
        public static class Filler440 extends NGroup {
          // COB: 044100               10 TRTSELA               PIC X.
          public NChar trtsela = new NChar(1);
        }

        public Filler440 filler440 = (Filler440) new Filler440().redefines(trtself);
        // COB: 044200            07 FILLER                   PIC X(4).
        public NChar filler442 = new NChar(4);
        // COB: 044300            07 TRTSELI                  PIC X(1).
        public NChar trtseli = new NChar(1);
        // COB: 044400            07 TRTTYPL                  PIC S9(4) COMP.
        public NInt16 trttypl = new NInt16();
        // COB: 044500            07 TRTTYPF                  PIC X.
        public NChar trttypf = new NChar(1);

        // COB: 044600            07 FILLER REDEFINES TRTTYPF.
        public static class Filler446 extends NGroup {
          // COB: 044700               10 TRTTYPA               PIC X.
          public NChar trttypa = new NChar(1);
        }

        public Filler446 filler446 = (Filler446) new Filler446().redefines(trttypf);
        // COB: 044800            07 FILLER                   PIC X(4).
        public NChar filler448 = new NChar(4);
        // COB: 044900            07 TRTTYPI                  PIC X(2).
        public NChar trttypi = new NChar(2);
        // COB: 045000            07 TRTYPDL                  PIC S9(4) COMP.
        public NInt16 trtypdl = new NInt16();
        // COB: 045100            07 TRTYPDF                 PIC X.
        public NChar trtypdf = new NChar(1);

        // COB: 045200            07 FILLER REDEFINES TRTYPDF.
        public static class Filler452 extends NGroup {
          // COB: 045300               10 TRTYPDA               PIC X.
          public NChar trtypda = new NChar(1);
        }

        public Filler452 filler452 = (Filler452) new Filler452().redefines(trtypdf);
        // COB: 045400            07 FILLER                   PIC X(4).
        public NChar filler454 = new NChar(4);
        // COB: 045500            07 TRTYPDI                  PIC X(50).
        public NChar trtypdi = new NChar(50);
      }

      public EachRowi[] eachRowi = AbstractNField.occurs(7, new EachRowi());

      public EachRowi eachRowiAtIndex(int index) {
        return getOccursInstance(eachRowi, index);
      }
    }

    public WsRowDatai wsRowDatai = new WsRowDatai();
    // COB: 045600    05 FILLER                           PIC X(137).
    public NChar filler456 = new NChar(137);
  }

  public Filler434 filler434 = (Filler434) new Filler434().redefines(cotrtli);

  // COB: 045700   01 FILLER REDEFINES CTRTLIAO.
  public static class Filler457 extends NGroup {
    // COB: 045800    05 FILLER                           PIC X(238).
    public NChar filler458 = new NChar(238);

    // COB: 045900    05 EACH-ROWO OCCURS 7 TIMES.
    public static class EachRowo extends NGroup {
      // COB: 046000            07 FILLER                   PIC X(3).
      public NChar filler460 = new NChar(3);
      // COB: 046100            07 TRTSELC                  PIC X.
      public NChar trtselc = new NChar(1);
      // COB: 046200            07 TRTSELP                  PIC X.
      public NChar trtselp = new NChar(1);
      // COB: 046300            07 TRTSELH                  PIC X.
      public NChar trtselh = new NChar(1);
      // COB: 046400            07 TRTSELV                  PIC X.
      public NChar trtselv = new NChar(1);
      // COB: 046500            07 TRTSELO                  PIC X(1).
      public NChar trtselo = new NChar(1);
      // COB: 046600            07 FILLER                   PIC X(3).
      public NChar filler466 = new NChar(3);
      // COB: 046700            07 TRTTYPC                  PIC X.
      public NChar trttypc = new NChar(1);
      // COB: 046800            07 TRTTYPP                  PIC X.
      public NChar trttypp = new NChar(1);
      // COB: 046900            07 TRTTYPH                  PIC X.
      public NChar trttyph = new NChar(1);
      // COB: 047000            07 TRTTYPV                  PIC X.
      public NChar trttypv = new NChar(1);
      // COB: 047100            07 TRTTYPO                  PIC X(2).
      public NChar trttypo = new NChar(2);
      // COB: 047200            07 FILLER                   PIC X(3).
      public NChar filler472 = new NChar(3);
      // COB: 047300            07 TRTYPDC                  PIC X.
      public NChar trtypdc = new NChar(1);
      // COB: 047400            07 TRTYPDP                  PIC X.
      public NChar trtypdp = new NChar(1);
      // COB: 047500            07 TRTYPDH                  PIC X.
      public NChar trtypdh = new NChar(1);
      // COB: 047600            07 TRTYPDV                  PIC X.
      public NChar trtypdv = new NChar(1);
      // COB: 047700            07 TRTYPDO                  PIC X(50).
      public NChar trtypdo = new NChar(50);
    }

    public EachRowo[] eachRowo = AbstractNField.occurs(7, new EachRowo());

    public EachRowo eachRowoAtIndex(int index) {
      return getOccursInstance(eachRowo, index);
    }

    // COB: 047800    05 FILLER                           PIC X(137).
    public NChar filler478 = new NChar(137);
  }

  public Filler457 filler457 = (Filler457) new Filler457().redefines(cotrtli.ctrtliao);
  public Csdat01y csdat01y = new Csdat01y();
  public Csmsg01y csmsg01y = new Csmsg01y();
  public Csusr01y csusr01y = new Csusr01y();
  public Cvact02y cvact02y = new Cvact02y();
}
