package com.aws.carddemo.common.copybook;

import static com.nib.commons.storage.SpecialRegister.*;

import com.nib.commons.storage.*;

public class Irissqlc extends NGroup {

  // COB:        01 IRIS-DB-SQLCODE                  PIC S9(9) COMP.
  public NInt32 irisDbSqlcode = new NInt32();

  // COB:          88 IRIS-SQL-OK                              VALUE 0.
  public boolean irisSqlOk() {
    return irisDbSqlcode.equals(0);
  }

  public void setIrisSqlOk(boolean value) {
    if (value) irisDbSqlcode.setValue(0);
  }

  // COB:          88 IRIS-SQL-UNIQ-CONSTR-VIOLATED            VALUE -803.
  public boolean irisSqlUniqConstrViolated() {
    return irisDbSqlcode.equals(-803);
  }

  public void setIrisSqlUniqConstrViolated(boolean value) {
    if (value) irisDbSqlcode.setValue(-803);
  }

  // COB:          88 IRIS-SQL-DEADLOCK                        VALUE -911.
  public boolean irisSqlDeadlock() {
    return irisDbSqlcode.equals(-911);
  }

  public void setIrisSqlDeadlock(boolean value) {
    if (value) irisDbSqlcode.setValue(-911);
  }

  // COB:          88 IRIS-SQL-INTE-CONSTR-VIOLATED            VALUE -530.
  public boolean irisSqlInteConstrViolated() {
    return irisDbSqlcode.equals(-530);
  }

  public void setIrisSqlInteConstrViolated(boolean value) {
    if (value) irisDbSqlcode.setValue(-530);
  }

  // COB:          88 IRIS-SQL-NOT-FOUND                       VALUE +100.
  public boolean irisSqlNotFound() {
    return irisDbSqlcode.equals(100);
  }

  public void setIrisSqlNotFound(boolean value) {
    if (value) irisDbSqlcode.setValue(100);
  }

  // COB:          88 IRIS-SQL-END-DB-REACHED                  VALUE +999.
  public boolean irisSqlEndDbReached() {
    return irisDbSqlcode.equals(999);
  }

  public void setIrisSqlEndDbReached(boolean value) {
    if (value) irisDbSqlcode.setValue(999);
  }

  // COB:          88 IRIS-SQL-CHG-SEG                         VALUE +998.
  public boolean irisSqlChgSeg() {
    return irisDbSqlcode.equals(998);
  }

  public void setIrisSqlChgSeg(boolean value) {
    if (value) irisDbSqlcode.setValue(998);
  }

  // COB:          88 IRIS-SQL-CHG-LEV                         VALUE +997.
  public boolean irisSqlChgLev() {
    return irisDbSqlcode.equals(997);
  }

  public void setIrisSqlChgLev(boolean value) {
    if (value) irisDbSqlcode.setValue(997);
  }

  // COB:          88 IRIS-SQL-DUAL-PCB-MISMATCH               VALUE +996.
  public boolean irisSqlDualPcbMismatch() {
    return irisDbSqlcode.equals(996);
  }

  public void setIrisSqlDualPcbMismatch(boolean value) {
    if (value) irisDbSqlcode.setValue(996);
  }

  // COB:          88 IRIS-SQL-DUAL-IOAREA-MISMATCH            VALUE +995.
  public boolean irisSqlDualIoareaMismatch() {
    return irisDbSqlcode.equals(995);
  }

  public void setIrisSqlDualIoareaMismatch(boolean value) {
    if (value) irisDbSqlcode.setValue(995);
  }

  // COB:          88 IRIS-SQL-CURSOR-ALREADY-OPEN             VALUE -502.
  public boolean irisSqlCursorAlreadyOpen() {
    return irisDbSqlcode.equals(-502);
  }

  public void setIrisSqlCursorAlreadyOpen(boolean value) {
    if (value) irisDbSqlcode.setValue(-502);
  }

  // COB:          88 IRIS-SQL-NOT-VALID-LENGTH                VALUE +994.
  public boolean irisSqlNotValidLength() {
    return irisDbSqlcode.equals(994);
  }

  public void setIrisSqlNotValidLength(boolean value) {
    if (value) irisDbSqlcode.setValue(994);
  }

  // COB:          88 IRIS-SQL-DELETE-RULE-VIOLATED            VALUE +993.
  public boolean irisSqlDeleteRuleViolated() {
    return irisDbSqlcode.equals(993);
  }

  public void setIrisSqlDeleteRuleViolated(boolean value) {
    if (value) irisDbSqlcode.setValue(993);
  }

  // COB:          88 IRIS-SQL-UPDATE-RULE-VIOLATED            VALUE +992.
  public boolean irisSqlUpdateRuleViolated() {
    return irisDbSqlcode.equals(992);
  }

  public void setIrisSqlUpdateRuleViolated(boolean value) {
    if (value) irisDbSqlcode.setValue(992);
  }

  // COB:        01 DATA-DICT-OBJECT-TYPE            PIC S9(4) COMP-5.
  public NInt16 dataDictObjectType = new NInt16();

  // COB:          88 DATA-DICT-DBD                            VALUE 101.
  public boolean dataDictDbd() {
    return dataDictObjectType.equals(101);
  }

  public void setDataDictDbd(boolean value) {
    if (value) dataDictObjectType.setValue(101);
  }

  // COB:          88 DATA-DICT-PSB                            VALUE 102.
  public boolean dataDictPsb() {
    return dataDictObjectType.equals(102);
  }

  public void setDataDictPsb(boolean value) {
    if (value) dataDictObjectType.setValue(102);
  }

  // COB:          88 DATA-DICT-COPYBOOK                       VALUE 103.
  public boolean dataDictCopybook() {
    return dataDictObjectType.equals(103);
  }

  public void setDataDictCopybook(boolean value) {
    if (value) dataDictObjectType.setValue(103);
  }

  // COB:          88 DATA-DICT-XML                            VALUE 104.
  public boolean dataDictXml() {
    return dataDictObjectType.equals(104);
  }

  public void setDataDictXml(boolean value) {
    if (value) dataDictObjectType.setValue(104);
  }

  // COB:          88 DATA-DICT-SQL                            VALUE 105.
  public boolean dataDictSql() {
    return dataDictObjectType.equals(105);
  }

  public void setDataDictSql(boolean value) {
    if (value) dataDictObjectType.setValue(105);
  }

  // COB:          88 DATA-DICT-BRIDGE                         VALUE 106.
  public boolean dataDictBridge() {
    return dataDictObjectType.equals(106);
  }

  public void setDataDictBridge(boolean value) {
    if (value) dataDictObjectType.setValue(106);
  }

  // COB:          88 DATA-DICT-LOG                            VALUE 107.
  public boolean dataDictLog() {
    return dataDictObjectType.equals(107);
  }

  public void setDataDictLog(boolean value) {
    if (value) dataDictObjectType.setValue(107);
  }

  // COB:          88 DATA-DICT-LOADER                         VALUE 108.
  public boolean dataDictLoader() {
    return dataDictObjectType.equals(108);
  }

  public void setDataDictLoader(boolean value) {
    if (value) dataDictObjectType.setValue(108);
  }

  // COB:        01 DATA-DICT-OBJECT-SUBTYPE         PIC S9(4) COMP-5.
  public NInt16 dataDictObjectSubtype = new NInt16();

  // COB:          88 DATA-DICT-DBD-PHYSICAL                   VALUE 201.
  public boolean dataDictDbdPhysical() {
    return dataDictObjectSubtype.equals(201);
  }

  public void setDataDictDbdPhysical(boolean value) {
    if (value) dataDictObjectSubtype.setValue(201);
  }

  // COB:          88 DATA-DICT-DBD-INDEX                      VALUE 202.
  public boolean dataDictDbdIndex() {
    return dataDictObjectSubtype.equals(202);
  }

  public void setDataDictDbdIndex(boolean value) {
    if (value) dataDictObjectSubtype.setValue(202);
  }

  // COB:          88 DATA-DICT-DBD-LOGICAL                    VALUE 203.
  public boolean dataDictDbdLogical() {
    return dataDictObjectSubtype.equals(203);
  }

  public void setDataDictDbdLogical(boolean value) {
    if (value) dataDictObjectSubtype.setValue(203);
  }

  // COB:        01 BRIDGES-INTERNAL-TYPES           PIC S9(4) COMP-5.
  public NInt16 bridgesInternalTypes = new NInt16();

  // COB:          88 FLD-TYPE-STANDARD                        VALUE 2000.
  public boolean fldTypeStandard() {
    return bridgesInternalTypes.equals(2000);
  }

  public void setFldTypeStandard(boolean value) {
    if (value) bridgesInternalTypes.setValue(2000);
  }

  // COB:          88 FLD-TYPE-PRIMARY-KEY                     VALUE 2001.
  public boolean fldTypePrimaryKey() {
    return bridgesInternalTypes.equals(2001);
  }

  public void setFldTypePrimaryKey(boolean value) {
    if (value) bridgesInternalTypes.setValue(2001);
  }

  // COB:          88 FLD-TYPE-PRIMARY-KEY-SUBSET              VALUE 2002.
  public boolean fldTypePrimaryKeySubset() {
    return bridgesInternalTypes.equals(2002);
  }

  public void setFldTypePrimaryKeySubset(boolean value) {
    if (value) bridgesInternalTypes.setValue(2002);
  }

  // COB:          88 FLD-TYPE-PARENT-KEY                      VALUE 2003.
  public boolean fldTypeParentKey() {
    return bridgesInternalTypes.equals(2003);
  }

  public void setFldTypeParentKey(boolean value) {
    if (value) bridgesInternalTypes.setValue(2003);
  }

  // COB:          88 FLD-TYPE-PARENT-KEY-SUBSET               VALUE 2004.
  public boolean fldTypeParentKeySubset() {
    return bridgesInternalTypes.equals(2004);
  }

  public void setFldTypeParentKeySubset(boolean value) {
    if (value) bridgesInternalTypes.setValue(2004);
  }

  // COB:          88 FLD-TYPE-NOKEY-PROGRESSIVE               VALUE 2005.
  public boolean fldTypeNokeyProgressive() {
    return bridgesInternalTypes.equals(2005);
  }

  public void setFldTypeNokeyProgressive(boolean value) {
    if (value) bridgesInternalTypes.setValue(2005);
  }

  // COB:          88 FLD-TYPE-MULTIKEY-PROGRESSIVE            VALUE 2006.
  public boolean fldTypeMultikeyProgressive() {
    return bridgesInternalTypes.equals(2006);
  }

  public void setFldTypeMultikeyProgressive(boolean value) {
    if (value) bridgesInternalTypes.setValue(2006);
  }

  // COB:          88 FLD-TYPE-CONCAT-KEY-FIELD                VALUE 2007.
  public boolean fldTypeConcatKeyField() {
    return bridgesInternalTypes.equals(2007);
  }

  public void setFldTypeConcatKeyField(boolean value) {
    if (value) bridgesInternalTypes.setValue(2007);
  }

  // COB:          88 FLD-TYPE-SX-SYSTEM-FIELD                 VALUE 2008.
  public boolean fldTypeSxSystemField() {
    return bridgesInternalTypes.equals(2008);
  }

  public void setFldTypeSxSystemField(boolean value) {
    if (value) bridgesInternalTypes.setValue(2008);
  }

  // COB:          88 FLD-TYPE-XDFLD-KEY                       VALUE 2009.
  public boolean fldTypeXdfldKey() {
    return bridgesInternalTypes.equals(2009);
  }

  public void setFldTypeXdfldKey(boolean value) {
    if (value) bridgesInternalTypes.setValue(2009);
  }

  // COB:          88 FLD-TYPE-XDFLD-KEY-SUBSET                VALUE 2010.
  public boolean fldTypeXdfldKeySubset() {
    return bridgesInternalTypes.equals(2010);
  }

  public void setFldTypeXdfldKeySubset(boolean value) {
    if (value) bridgesInternalTypes.setValue(2010);
  }

  // COB:          88 FLD-TYPE-XDFLD-KEY-OVER-COPY             VALUE 2011.
  public boolean fldTypeXdfldKeyOverCopy() {
    return bridgesInternalTypes.equals(2011);
  }

  public void setFldTypeXdfldKeyOverCopy(boolean value) {
    if (value) bridgesInternalTypes.setValue(2011);
  }

  // COB:        01 SEGMENT-FIELD-SEQUENCE           PIC X.
  public NChar segmentFieldSequence = new NChar(1);

  // COB:          88 FIELD-SEQUENCE-NONE                      VALUE '0'.
  public boolean fieldSequenceNone() {
    return segmentFieldSequence.equals("0");
  }

  public void setFieldSequenceNone(boolean value) {
    if (value) segmentFieldSequence.setValue("0");
  }

  // COB:          88 FIELD-SEQUENCE-UNIQUE                    VALUE '1'.
  public boolean fieldSequenceUnique() {
    return segmentFieldSequence.equals("1");
  }

  public void setFieldSequenceUnique(boolean value) {
    if (value) segmentFieldSequence.setValue("1");
  }

  // COB:          88 FIELD-SEQUENCE-MULTI                     VALUE '2'.
  public boolean fieldSequenceMulti() {
    return segmentFieldSequence.equals("2");
  }

  public void setFieldSequenceMulti(boolean value) {
    if (value) segmentFieldSequence.setValue("2");
  }

  // COB:        01 WS-SQLSTMT                       PIC X(256).
  public NChar wsSqlstmt = new NChar(256);
}
