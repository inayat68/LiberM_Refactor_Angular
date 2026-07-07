package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Iriscomm extends NGroup {

  // COB:        01 FILLER.
  public static class Filler12 extends NGroup {
    // COB:          03 IRIS-WS-PRODUCT-NAME          PIC X(8)  VALUE 'IRIS-DB '.
    public NChar irisWsProductName = new NChar(8).initial("IRIS-DB ");
  }

  public Filler12 filler12 = new Filler12();
  // COB:        01 IRIS-WS-RTN                     PIC X(8)  VALUE SPACE.
  public NChar irisWsRtn = new NChar(8).initial(NChar.Space);

  // COB:            88 IRISUTIL-RTN                          VALUE 'IRISUTIL'.
  public boolean irisutilRtn() {
    return irisWsRtn.equals("IRISUTIL");
  }

  public void setIrisutilRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISUTIL");
  }

  // COB:            88 IRISTRAC-RTN                          VALUE 'IRISTRAC'.
  public boolean iristracRtn() {
    return irisWsRtn.equals("IRISTRAC");
  }

  public void setIristracRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISTRAC");
  }

  // COB:            88 IRISPSSA-RTN                          VALUE 'IRISPSSA'.
  public boolean irispssaRtn() {
    return irisWsRtn.equals("IRISPSSA");
  }

  public void setIrispssaRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISPSSA");
  }

  // COB:            88 IRISGSEG-RTN                          VALUE 'IRISGSEG'.
  public boolean irisgsegRtn() {
    return irisWsRtn.equals("IRISGSEG");
  }

  public void setIrisgsegRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISGSEG");
  }

  // COB:            88 IRISGFLD-RTN                          VALUE 'IRISGFLD'.
  public boolean irisgfldRtn() {
    return irisWsRtn.equals("IRISGFLD");
  }

  public void setIrisgfldRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISGFLD");
  }

  // COB:            88 IRISADDR-RTN                          VALUE 'IRISADDR'.
  public boolean irisaddrRtn() {
    return irisWsRtn.equals("IRISADDR");
  }

  public void setIrisaddrRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISADDR");
  }

  // COB:            88 IRISABND-RTN                          VALUE 'IRISABND'.
  public boolean irisabndRtn() {
    return irisWsRtn.equals("IRISABND");
  }

  public void setIrisabndRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISABND");
  }

  // COB:            88 IRISPOPT-RTN                          VALUE 'IRISPOPT'.
  public boolean irispoptRtn() {
    return irisWsRtn.equals("IRISPOPT");
  }

  public void setIrispoptRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISPOPT");
  }

  // COB:            88 IRISPSB-RTN                           VALUE 'IRISPSB'.
  public boolean irispsbRtn() {
    return irisWsRtn.equals("IRISPSB");
  }

  public void setIrispsbRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISPSB");
  }

  // COB:            88 CBLTDLI-RTN                           VALUE 'CBLTDLI '.
  public boolean cbltdliRtn() {
    return irisWsRtn.equals("CBLTDLI ");
  }

  public void setCbltdliRtn(boolean value) {
    if (value) irisWsRtn.setValue("CBLTDLI ");
  }

  // COB:            88 IRISCHKP-RTN                          VALUE 'IRISCHKP'.
  public boolean irischkpRtn() {
    return irisWsRtn.equals("IRISCHKP");
  }

  public void setIrischkpRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISCHKP");
  }

  // COB: DC         88 IRISSND-RTN                           VALUE 'IRISSEND'.
  public boolean irissndRtn() {
    return irisWsRtn.equals("IRISSEND");
  }

  public void setIrissndRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISSEND");
  }

  // COB: DC         88 IRISRCV-RTN                           VALUE 'IRISRCVE'.
  public boolean irisrcvRtn() {
    return irisWsRtn.equals("IRISRCVE");
  }

  public void setIrisrcvRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISRCVE");
  }

  // COB: DC         88 IRISROUT-RTN                          VALUE 'IRISROUT'.
  public boolean irisroutRtn() {
    return irisWsRtn.equals("IRISROUT");
  }

  public void setIrisroutRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISROUT");
  }

  // COB:            88 IRISJBNM-RTN                          VALUE 'IRISJBNM'.
  public boolean irisjbnmRtn() {
    return irisWsRtn.equals("IRISJBNM");
  }

  public void setIrisjbnmRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISJBNM");
  }

  // COB:            88 IRISTSQU-RTN                          VALUE 'IRISTSQU'.
  public boolean iristsquRtn() {
    return irisWsRtn.equals("IRISTSQU");
  }

  public void setIristsquRtn(boolean value) {
    if (value) irisWsRtn.setValue("IRISTSQU");
  }

  // COB:        01 IRIS-SYSTEM-ENDIANNESS          PIC 9     VALUE 1.
  public NZoned irisSystemEndianness = new NZoned(1, false).initial(1);

  // COB:            88 SYSTEM-BIG-ENDIAN                     VALUE 0.
  public boolean systemBigEndian() {
    return irisSystemEndianness.equals(0);
  }

  public void setSystemBigEndian(boolean value) {
    if (value) irisSystemEndianness.setValue(0);
  }

  // COB:            88 SYSTEM-LITTLE-ENDIAN                  VALUE 1.
  public boolean systemLittleEndian() {
    return irisSystemEndianness.equals(1);
  }

  public void setSystemLittleEndian(boolean value) {
    if (value) irisSystemEndianness.setValue(1);
  }

  // COB:        01 IRIS-IMS-CONDITION              PIC X(2).
  public NChar irisImsCondition = new NChar(2);

  // COB:            88 IRIS-COND-GREATER                     VALUE '> ' ' >'
  public boolean irisCondGreater() {
    return irisImsCondition.equals("> ")
        || irisImsCondition.equals(" >")
        || irisImsCondition.equals("GT");
  }

  public void setIrisCondGreater(boolean value) {
    if (value) irisImsCondition.setValue("> ");
  }

  // COB:            88 IRIS-COND-GREATER-EQUAL               VALUE '>=' '=>'
  public boolean irisCondGreaterEqual() {
    return irisImsCondition.equals(">=")
        || irisImsCondition.equals("=>")
        || irisImsCondition.equals("GE");
  }

  public void setIrisCondGreaterEqual(boolean value) {
    if (value) irisImsCondition.setValue(">=");
  }

  // COB:            88 IRIS-COND-EQUAL                       VALUE '= ' ' ='
  public boolean irisCondEqual() {
    return irisImsCondition.equals("= ")
        || irisImsCondition.equals(" =")
        || irisImsCondition.equals("EQ");
  }

  public void setIrisCondEqual(boolean value) {
    if (value) irisImsCondition.setValue("= ");
  }

  // COB:            88 IRIS-COND-NOT-EQUAL                   VALUE '<>' 'NE'.
  public boolean irisCondNotEqual() {
    return irisImsCondition.equals("<>") || irisImsCondition.equals("NE");
  }

  public void setIrisCondNotEqual(boolean value) {
    if (value) irisImsCondition.setValue("<>");
  }

  // COB:            88 IRIS-COND-LESS                        VALUE '< ' ' <'
  public boolean irisCondLess() {
    return irisImsCondition.equals("< ")
        || irisImsCondition.equals(" <")
        || irisImsCondition.equals("LT");
  }

  public void setIrisCondLess(boolean value) {
    if (value) irisImsCondition.setValue("< ");
  }

  // COB:            88 IRIS-COND-LESS-EQUAL                  VALUE '<=' '=<'
  public boolean irisCondLessEqual() {
    return irisImsCondition.equals("<=")
        || irisImsCondition.equals("=<")
        || irisImsCondition.equals("LE");
  }

  public void setIrisCondLessEqual(boolean value) {
    if (value) irisImsCondition.setValue("<=");
  }

  // COB:        01 IRIS-IMS-USER-CONDITIONS.
  public static class IrisImsUserConditions extends NGroup {
    // COB:          03 IRIS-GT1                      PIC X(2)  VALUE 'GT'.
    public NChar irisGt1 = new NChar(2).initial("GT");
    // COB:          03 IRIS-GT2                      PIC X(2)  VALUE '> '.
    public NChar irisGt2 = new NChar(2).initial("> ");
    // COB:          03 IRIS-GT3                      PIC X(2)  VALUE ' >'.
    public NChar irisGt3 = new NChar(2).initial(" >");
    // COB:          03 IRIS-GE1                      PIC X(2)  VALUE 'GE'.
    public NChar irisGe1 = new NChar(2).initial("GE");
    // COB:          03 IRIS-GE2                      PIC X(2)  VALUE '>='.
    public NChar irisGe2 = new NChar(2).initial(">=");
    // COB:          03 IRIS-GE3                      PIC X(2)  VALUE '=>'.
    public NChar irisGe3 = new NChar(2).initial("=>");
    // COB:          03 IRIS-EQ1                      PIC X(2)  VALUE 'EQ'.
    public NChar irisEq1 = new NChar(2).initial("EQ");
    // COB:          03 IRIS-EQ2                      PIC X(2)  VALUE '= '.
    public NChar irisEq2 = new NChar(2).initial("= ");
    // COB:          03 IRIS-EQ3                      PIC X(2)  VALUE ' ='.
    public NChar irisEq3 = new NChar(2).initial(" =");
    // COB:          03 IRIS-NE1                      PIC X(2)  VALUE 'NE'.
    public NChar irisNe1 = new NChar(2).initial("NE");
    // COB:          03 IRIS-NE2                      PIC X(2)  VALUE '?='.
    public NChar irisNe2 = new NChar(2).initial("¬=");
    // COB:          03 IRIS-NE3                      PIC X(2)  VALUE '=?'.
    public NChar irisNe3 = new NChar(2).initial("=¬");
    // COB:          03 IRIS-NE4                      PIC X(2)  VALUE '<>'.
    public NChar irisNe4 = new NChar(2).initial("<>");
    // COB:          03 IRIS-LT1                      PIC X(2)  VALUE 'LT'.
    public NChar irisLt1 = new NChar(2).initial("LT");
    // COB:          03 IRIS-LT2                      PIC X(2)  VALUE '< '.
    public NChar irisLt2 = new NChar(2).initial("< ");
    // COB:          03 IRIS-LT3                      PIC X(2)  VALUE ' <'.
    public NChar irisLt3 = new NChar(2).initial(" <");
    // COB:          03 IRIS-LE1                      PIC X(2)  VALUE 'LE'.
    public NChar irisLe1 = new NChar(2).initial("LE");
    // COB:          03 IRIS-LE2                      PIC X(2)  VALUE '<='.
    public NChar irisLe2 = new NChar(2).initial("<=");
    // COB:          03 IRIS-LE3                      PIC X(2)  VALUE '=<'.
    public NChar irisLe3 = new NChar(2).initial("=<");
    // COB:          03 IRIS-OR1                      PIC X     VALUE '|'.
    public NChar irisOr1 = new NChar(1).initial("|");
    // COB:          03 IRIS-OR2                      PIC X     VALUE '+'.
    public NChar irisOr2 = new NChar(1).initial("+");
    // COB:          03 IRIS-AND1                     PIC X     VALUE '&'.
    public NChar irisAnd1 = new NChar(1).initial("&");
    // COB:          03 IRIS-AND2                     PIC X     VALUE '*'.
    public NChar irisAnd2 = new NChar(1).initial("*");
    // COB:          03 IRIS-LP                       PIC X     VALUE '('.
    public NChar irisLp = new NChar(1).initial("(");
    // COB:          03 IRIS-RP                       PIC X     VALUE ')'.
    public NChar irisRp = new NChar(1).initial(")");
    // COB:          03 IRIS-COUNT                    PIC S9(4) COMP
    // COB:                                                     VALUE ZERO.
    public NInt16 irisCount = new NInt16().initial(0);
  }

  public IrisImsUserConditions irisImsUserConditions = new IrisImsUserConditions();
  // COB:        01 MESSAGE-END                     PIC X VALUE X'EF'.
  public NChar messageEnd = new NChar(1).initial("EF");
  // COB:        01 NL                              PIC X VALUE '|'.
  public NChar nl = new NChar(1).initial("|");
  // COB:        01 KEY-FIELD-PREFIX                PIC X(3) VALUE 'KEY'.
  public NChar keyFieldPrefix = new NChar(3).initial("KEY");

  // COB:        01 IRIS-DIB-BLOCK.
  public static class IrisDibBlock extends NGroup {
    // COB:          03  DIBVER                       PIC X(2).
    public NChar dibver = new NChar(2);
    // COB:          03  DIBSTAT                      PIC X(2).
    public NChar dibstat = new NChar(2);
    // COB:          03  DIBSEGM                      PIC X(8).
    public NChar dibsegm = new NChar(8);
    // COB:          03  DIBFIL01                     PIC X(1).
    public NChar dibfil01 = new NChar(1);
    // COB:          03  DIBFIL01                     PIC X(1).
    public NChar dibfil01_ = new NChar(1);
    // COB:          03  DIBSEGLV                     PIC X(2).
    public NChar dibseglv = new NChar(2);
    // COB:          03  DIBKFBL                      PIC S9(4) COMP.
    public NInt16 dibkfbl = new NInt16();
    // COB:          03  DIBDBDNM                     PIC X(8).
    public NChar dibdbdnm = new NChar(8);
    // COB:          03  DIBDBORG                     PIC X(8).
    public NChar dibdborg = new NChar(8);
    // COB:          03  DIBFIL03                     PIC X(6).
    public NChar dibfil03 = new NChar(6);
  }

  public IrisDibBlock irisDibBlock = new IrisDibBlock();

  // COB:        01 DLZDIB REDEFINES IRIS-DIB-BLOCK.
  public static class Dlzdib extends NGroup {
    // COB:          03  IRIS-DIBVER                  PIC X(2).
    public NChar irisDibver = new NChar(2);
    // COB:          03  IRIS-DIBSTAT                 PIC X(2).
    public NChar irisDibstat = new NChar(2);
    // COB:          03  IRIS-DIBSEGM                 PIC X(8).
    public NChar irisDibsegm = new NChar(8);
    // COB:          03  IRIS-DIBFIL01                PIC X(1).
    public NChar irisDibfil01 = new NChar(1);
    // COB:          03  IRIS-DIBFIL01                PIC X(1).
    public NChar irisDibfil01_ = new NChar(1);
    // COB:          03  IRIS-DIBSEGLV                PIC X(2).
    public NChar irisDibseglv = new NChar(2);
    // COB:          03  IRIS-DIBKFBL                 PIC S9(4) COMP.
    public NInt16 irisDibkfbl = new NInt16();
    // COB:          03  IRIS-DIBDBDNM                PIC X(8).
    public NChar irisDibdbdnm = new NChar(8);
    // COB:          03  IRIS-DIBDBORG                PIC X(8).
    public NChar irisDibdborg = new NChar(8);
    // COB:          03  IRIS-DIBFIL03                PIC X(6).
    public NChar irisDibfil03 = new NChar(6);
  }

  public Dlzdib dlzdib = (Dlzdib) new Dlzdib().redefines(irisDibBlock);
  // COB:        01 IRIS-DLZDIB REDEFINES IRIS-DIB-BLOCK PIC X(40).
  public NChar irisDlzdib = new NChar(40).redefines(irisDibBlock);
  // COB:        01 IRIS-DUMMY-AREA                 PIC X(8) VALUE SPACES.
  public NChar irisDummyArea = new NChar(8).initial(NChar.Space);
  // COB:        01 IRIS-PSB-NAME-COMM              PIC X(8).
  public NChar irisPsbNameComm = new NChar(8);
  // COB:        01 IRIS-CHKP-AREA1-LEN-COMM        PIC S9(9) COMP.
  public NInt32 irisChkpArea1LenComm = new NInt32();
  // COB:        01 IRIS-CHKP-AREA2-LEN-COMM        PIC S9(9) COMP.
  public NInt32 irisChkpArea2LenComm = new NInt32();
  // COB:        01 IRIS-CHKP-AREA3-LEN-COMM        PIC S9(9) COMP.
  public NInt32 irisChkpArea3LenComm = new NInt32();
  // COB:        01 IRIS-CHKP-AREA4-LEN-COMM        PIC S9(9) COMP.
  public NInt32 irisChkpArea4LenComm = new NInt32();
  // COB:        01 IRIS-CHKP-AREA5-LEN-COMM        PIC S9(9) COMP.
  public NInt32 irisChkpArea5LenComm = new NInt32();
  // COB:        01 IRIS-CHKP-AREA6-LEN-COMM        PIC S9(9) COMP.
  public NInt32 irisChkpArea6LenComm = new NInt32();
  // COB:        01 IRIS-CHKP-AREA7-LEN-COMM        PIC S9(9) COMP.
  public NInt32 irisChkpArea7LenComm = new NInt32();

  // COB:        01 DATE-CONSTANT.
  public static class DateConstant extends NGroup {
    // COB:          03 YEAR-FOR-CENTURY    PIC 9(02) VALUE 50.
    public NZoned yearForCentury = new NZoned(2, false).initial(50);
    // COB:          03 DAY-WHEN-MISSING    PIC 9(02) VALUE 01.
    public NZoned dayWhenMissing = new NZoned(2, false).initial(1);
    // COB:          03 MON-WHEN-MISSING    PIC 9(02) VALUE 01.
    public NZoned monWhenMissing = new NZoned(2, false).initial(1);
  }

  public DateConstant dateConstant = new DateConstant();

  // COB:        01 IRIS-TSNAME.
  public static class IrisTsname extends NGroup {
    // COB:          03 IRIS-EIBTRMID                 PIC X(4).
    public NChar irisEibtrmid = new NChar(4);
    // COB:          03 IRIS-EIBTRNID                 PIC X(4).
    public NChar irisEibtrnid = new NChar(4);
    // COB:          03 IRIS-EIBTASKN                 PIC 9(8).
    public NZoned irisEibtaskn = new NZoned(8, false);
  }

  public IrisTsname irisTsname = new IrisTsname();

  // COB:        01 IRIS-TSAREA.
  public static class IrisTsarea extends NGroup {
    // COB:          03 IRIS-PCBS-ADDRESS             PIC S9(9) COMP.
    public NInt32 irisPcbsAddress = new NInt32();
    // COB:          03 IRIS-PCBS-POINTER REDEFINES IRIS-PCBS-ADDRESS POINTER.
    public NPointer irisPcbsPointer = new NPointer().redefines(irisPcbsAddress);
  }

  public IrisTsarea irisTsarea = new IrisTsarea();
  // COB:        01 IRIS-RESPONSE                   PIC S9(8) COMP VALUE 0.
  public NInt32 irisResponse = new NInt32().initial(0);
}
