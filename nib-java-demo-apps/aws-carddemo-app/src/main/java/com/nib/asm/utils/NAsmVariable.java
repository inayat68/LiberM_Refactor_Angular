package com.nib.asm.utils;

import java.util.Vector;

/**
 * Handle a variable definition, used internally by lexical parser only.
 * 
 * @author nib-labs.io
 */
public class NAsmVariable extends NAsmToken {

	/**
	 * Self-explanatory values for File I/O type.
	 */
	public static enum FileIOType {
		UNQUALIFIED, //
		OUTPUT, //
		INPUT, //
		INPUT_OUTPUT, //
		EXTEND, //
	}

	/**
	 * Self-explanatory values for data type.
	 */
	public static enum DataType {
		NOT_DEFINED, // 0;
		ALPHANUMERIC, // 1;
		ALPHANUMERIC_VARYING, // 2;
		BIT, // 3;
		BLOB, // 4;
		BLOB_LOCATOR, // 5;
		BUILTIN, // 6;
		CHAR, // 7;
		CLOB, // 8;
		CLOB_LOCATOR, // 9;
		DATE, // 10;
		DBCLOB, // 11;
		DBCLOB_LOCATOR, // 12;
		DECIMAL, // 13;
		DOUBLE, // 14;
		EDITED, // 15;
		ENTRY, // 16;
		FLOAT, // 17;
		GRAPHIC, // 18;
		INTEGER, // 19;
		LABEL, // 20;
		LOCATOR, // 21;
		LONG, // 22;
		NCLOB, // 23;
		NUMERIC_BINARY, // 24;
		NUMERIC_COMP_1, // 25;
		NUMERIC_COMP_2, // 26;
		NUMERIC_COMP_4, // 27;
		NUMERIC_COMP_5, // 28;
		NUMERIC_COMP_6, // 29;
		NUMERIC_COMP_X, // 30;
		NUMERIC_FLOAT, // 31;
		NUMERIC_PACKED, // 32;
		NUMERIC_ZONED, // 33;
		POINTER, // 34;
		PROPRIETARY_DEFINITION, // 35;
		REAL, // 36;
		RESULT_SET_LOCATOR, // 37;
		ROWID, // 38;
		SMALLINT, // 39;
		SYSDATE, // 40;
		TIME, // 41;
		TIMESTAMP, // 42;
		UNIXTIME, // 43;
		VARBINARY, // 44;
		VARCHAR, // 45;
		VARCHAR2, // 46;
		VARGRAPHIC, // 47;
		FILE_DESCRIPTION, // 48;
		BOOLEAN, // 49;
		ENTRY_VARIABLE, // 50;
		BIGINT, // 51;
		ALPHANUMERIC_DBCS, // 52;
		ALPHANUMERIC_MIXED, // 53;
	}

	private int id = 0;
	private int fieldNameId = 0;
	private DataType dataTypeId = DataType.ALPHANUMERIC;
	private String declarativePattern = "";
	private int lengthBytes = 0;
	private int lengthLogical = 0;
	private int decimals = 0;
	private boolean decimalsDefined = false;
	private boolean isStructured = false;
	private boolean isSigned = false;
	private int recordVariableId = 0;
	private int parentVariableId = 0;
	private int basedVariableId = 0;
	private String recordVariable = "";
	private String parentVariable = "";
	private NAsmVariable actualParentNAsmVariable = null;
	private String basedVariable = "";
	private int level = 1;
	private int recordOffset = 0;
	private int parentOffset = 0;
	private int array = 0;
	private int arrayDimension = 0;
	private int arrayDynamic = 0;
	private int arrayMulti = 0;
	private int nullable = 0;
	private int procedureId = 0;
	private int fieldTypeId = 0;
	private int basedOffset = 0;
	private int basedLength = 0;
	private String procedure = null;
	private int initValueId = 0;
	private Vector<NAsmToken> initValues = new Vector<>();
	private String declarativeArea = "";
	private Vector<NAsmToken> dataMask = new Vector<>();
	private int jobId = 0;
	private NAsmVariable file = null;
	private String arrayIndex = "";
	private Vector<NAsmToken> headingValues = new Vector<>();
	private boolean reset = false;

	// Files qualification
	private static String FILE_SEQUENTIAL_FB = "SEQUENTIAL_FIXED";

	// File features
	private boolean fileDeclaration = false;
	private String fileType = FILE_SEQUENTIAL_FB;
	private FileIOType fileUsage = FileIOType.UNQUALIFIED;

	/**
	 * NAsmVariable instance.
	 * 
	 * @param NAsmToken
	 *            NAsmToken used to create the variable instance
	 */
	public NAsmVariable(NAsmToken NAsmToken) {
		super(NAsmToken);
	}

	/**
	 * Clone the NAsmVariable instance.
	 * 
	 * @param NAsmVariable
	 *            NAsmVariable used to clone the NAsmVariable instance
	 * @return a new instance of the NAsmVariable
	 */
	public NAsmVariable cloneVariable(NAsmVariable NAsmVariable) {
		NAsmVariable newNAsmVariable = new NAsmVariable(NAsmVariable);
		newNAsmVariable.setId(this.getId());
		newNAsmVariable.setFieldNameId(this.getFieldNameId());
		newNAsmVariable.setDataTypeId(this.getDataTypeId());
		newNAsmVariable.setDeclarativePattern(this.getDeclarativePattern());
		newNAsmVariable.setLengthBytes(this.getLengthBytes());
		newNAsmVariable.setLengthLogical(this.getLengthLogical());
		newNAsmVariable.setDecimals(this.getDecimals());
		newNAsmVariable.setStructured(this.isStructured());
		newNAsmVariable.setSigned(this.isSigned());
		newNAsmVariable.setRecordVariableId(this.getRecordVariableId());
		newNAsmVariable.setParentVariableId(this.getParentVariableId());
		newNAsmVariable.setBasedVariableId(this.getBasedVariableId());
		newNAsmVariable.setRecordVariable(this.getRecordVariable());
		newNAsmVariable.setParentVariable(this.getParentVariable());
		newNAsmVariable.setActualParentVariable(this.getActualParentVariable());
		newNAsmVariable.setBasedVariable(this.getBasedVariable());
		newNAsmVariable.setLevel(this.getLevel());
		newNAsmVariable.setRecordOffset(this.getRecordOffset());
		newNAsmVariable.setParentOffset(this.getParentOffset());
		newNAsmVariable.setArray(this.getArray());
		newNAsmVariable.setArrayDimension(this.getArrayDimension());
		newNAsmVariable.setArrayDynamic(this.getArrayDynamic());
		newNAsmVariable.setArrayMulti(this.getArrayMulti());
		newNAsmVariable.setNullable(this.getNullable());
		newNAsmVariable.setProcedureId(this.getProcedureId());
		newNAsmVariable.setInitValueId(this.getInitValueId());
		newNAsmVariable.setFieldTypeId(this.getFieldTypeId());
		newNAsmVariable.setBasedOffset(this.getBasedOffset());
		newNAsmVariable.setBasedLength(this.getBasedLength());
		newNAsmVariable.setProcedure(this.getProcedure());
		newNAsmVariable.setFileDeclaration(this.isFileDeclaration());
		newNAsmVariable.setFileType(this.getFileType());
		newNAsmVariable.setFileUsage(this.getFileUsage());
		newNAsmVariable.setInitValues(this.initValues);
		newNAsmVariable.setDeclarativeArea(this.declarativeArea);
		newNAsmVariable.setDataMask(this.dataMask);
		newNAsmVariable.setJobId(this.jobId);
		newNAsmVariable.setFile(this.file);
		newNAsmVariable.setHeadingValues(this.headingValues);
		newNAsmVariable.setReset(this.reset);

		return newNAsmVariable;
	}

	protected int getId() {
		return id;
	}

	protected void setId(int id) {
		this.id = id;
	}

	protected int getFieldNameId() {
		return fieldNameId;
	}

	protected void setFieldNameId(int fieldNameId) {
		this.fieldNameId = fieldNameId;
	}

	protected DataType getDataTypeId() {
		return dataTypeId;
	}

	protected void setDataTypeId(DataType dataTypeId) {
		this.dataTypeId = dataTypeId;
	}

	protected String getDeclarativePattern() {
		return declarativePattern;
	}

	protected void setDeclarativePattern(String declarativePattern) {
		this.declarativePattern = declarativePattern;
	}

	protected int getLengthBytes() {
		return lengthBytes;
	}

	protected void setLengthBytes(int lengthBytes) {
		this.lengthBytes = lengthBytes;
	}

	protected int getLengthLogical() {
		return lengthLogical;
	}

	protected void setLengthLogical(int lengthLogical) {
		this.lengthLogical = lengthLogical;
	}

	protected int getDecimals() {
		return decimals;
	}

	protected void setDecimals(int decimals) {
		this.decimals = decimals;
	}

	protected boolean isStructured() {
		return isStructured;
	}

	protected void setStructured(boolean isStructured) {
		this.isStructured = isStructured;
	}

	protected boolean isSigned() {
		return isSigned;
	}

	protected void setSigned(boolean isSigned) {
		this.isSigned = isSigned;
	}

	protected int getRecordVariableId() {
		return recordVariableId;
	}

	protected void setRecordVariableId(int recordVariableId) {
		this.recordVariableId = recordVariableId;
	}

	protected int getParentVariableId() {
		return parentVariableId;
	}

	protected void setParentVariableId(int parentVariableId) {
		this.parentVariableId = parentVariableId;
	}

	protected int getBasedVariableId() {
		return basedVariableId;
	}

	protected void setBasedVariableId(int basedVariableId) {
		this.basedVariableId = basedVariableId;
	}

	protected int getLevel() {
		return level;
	}

	protected void setLevel(int level) {
		this.level = level;
	}

	protected int getRecordOffset() {
		return recordOffset;
	}

	protected void setRecordOffset(int recordOffset) {
		this.recordOffset = recordOffset;
	}

	protected int getParentOffset() {
		return parentOffset;
	}

	protected void setParentOffset(int parentOffset) {
		this.parentOffset = parentOffset;
	}

	protected int getArray() {
		return array;
	}

	protected void setArray(int value) {
		this.array = value;
	}

	protected int getArrayDynamic() {
		return arrayDynamic;
	}

	protected void setArrayDynamic(int value) {
		this.arrayDynamic = value;
	}

	protected int getArrayMulti() {
		return arrayMulti;
	}

	protected void setArrayMulti(int value) {
		this.arrayMulti = value;
	}

	protected int getNullable() {
		return nullable;
	}

	protected void setNullable(int value) {
		this.nullable = value;
	}

	protected int getProcedureId() {
		return procedureId;
	}

	protected void setProcedureId(int procedureId) {
		this.procedureId = procedureId;
	}

	protected int getInitValueId() {
		return initValueId;
	}

	protected void setInitValueId(int initValueId) {
		this.initValueId = initValueId;
	}

	protected int getFieldTypeId() {
		return fieldTypeId;
	}

	protected void setFieldTypeId(int fieldTypeId) {
		this.fieldTypeId = fieldTypeId;
	}

	protected int getBasedOffset() {
		return basedOffset;
	}

	protected void setBasedOffset(int basedOffset) {
		this.basedOffset = basedOffset;
	}

	protected int getBasedLength() {
		return basedLength;
	}

	protected void setBasedLength(int basedLength) {
		this.basedLength = basedLength;
	}

	protected boolean isFileDeclaration() {
		return fileDeclaration;
	}

	protected void setFileDeclaration(boolean fileDeclaration) {
		this.fileDeclaration = fileDeclaration;
	}

	protected String getFileType() {
		return fileType;
	}

	protected void setFileType(String fileType) {
		this.fileType = fileType;
	}

	protected String getProcedure() {
		return procedure;
	}

	protected void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	protected int getArrayDimension() {
		return arrayDimension;
	}

	protected void setArrayDimension(int arrayDimension) {
		this.arrayDimension = arrayDimension;
	}

	protected NAsmToken getInitValue(int index) {
		if (index < 0 || index >= this.initValues.size())
			return null;
		return (NAsmToken) initValues.get(index);
	}

	protected NAsmToken getHeadingValue(int index) {
		if (index < 0 || index >= this.headingValues.size())
			return null;
		return (NAsmToken) headingValues.get(index);
	}

	protected NAsmToken getDataMask(int index) {
		if (index < 0 || index >= this.dataMask.size())
			return null;
		return (NAsmToken) dataMask.get(index);
	}

	protected int getInitValuesCount() {
		return initValues.size();
	}

	protected int getHeadingValuesCount() {
		return headingValues.size();
	}

	protected int getDataMaskCount() {
		return dataMask.size();
	}

	protected void addInitValues(NAsmToken value) {
		this.initValues.add(this.initValues.size(), value);
	}

	protected void addHeadingValues(NAsmToken value) {
		this.headingValues.add(this.headingValues.size(), value);
	}

	protected void addDataMask(NAsmToken value) {
		this.dataMask.add(this.dataMask.size(), value);
	}

	protected void setInitValues(Vector<NAsmToken> values) {
		this.initValues = values;
	}

	protected void setHeadingValues(Vector<NAsmToken> values) {
		this.headingValues = values;
	}

	protected void setDataMask(Vector<NAsmToken> values) {
		this.dataMask = values;
	}

	protected void setInitValue(NAsmToken value) {
		Vector<NAsmToken> v = new Vector<>();
		v.add(value);
		this.setInitValues(v);
	}

	protected String getRecordVariable() {
		return recordVariable;
	}

	protected void setRecordVariable(String recordVariable) {
		this.recordVariable = recordVariable;
	}

	protected String getParentVariable() {
		return parentVariable;
	}

	protected void setParentVariable(String parentVariable) {
		this.parentVariable = parentVariable;
	}

	protected void setActualParentVariable(NAsmVariable parentNAsmVariable) {
		this.actualParentNAsmVariable = parentNAsmVariable;
		if (parentNAsmVariable == null) {
			this.setParentVariable("");
			this.setParentVariableId(0);
		} else {
			this.setParentVariable(parentNAsmVariable.getWord());
			this.setParentVariableId(parentNAsmVariable.getId());
		}
	}

	protected NAsmVariable getActualParentVariable() {
		return this.actualParentNAsmVariable;
	}

	protected String getBasedVariable() {
		return basedVariable;
	}

	protected void setBasedVariable(String basedVariable) {
		this.basedVariable = basedVariable;
	}

	protected FileIOType getFileUsage() {
		return fileUsage;
	}

	protected void setFileUsage(FileIOType fileUsage) {
		this.fileUsage = fileUsage;
	}

	protected void setDeclarativeArea(String declarativeArea) {
		this.declarativeArea = declarativeArea;
	}

	/**
	 * @return the declarativeArea
	 */
	protected String getDeclarativeArea() {
		return declarativeArea;
	}

	/**
	 * @return the decimalsDefined
	 */
	protected boolean isDecimalsDefined() {
		return decimalsDefined;
	}

	/**
	 * @param decimalsDefined
	 *            the decimalsDefined to set
	 */
	protected void setDecimalsDefined(boolean decimalsDefined) {
		this.decimalsDefined = decimalsDefined;
	}

	/**
	 * @return the jobId
	 */
	protected int getJobId() {
		return jobId;
	}

	/**
	 * @param jobId
	 *            the jobId to set
	 */
	protected void setJobId(int jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return the file
	 */
	protected NAsmVariable getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	protected void setFile(NAsmVariable file) {
		this.file = file;
	}

	/**
	 * @return the arrayIndex
	 */
	protected String getArrayIndex() {
		return arrayIndex;
	}

	/**
	 * @param arrayIndex
	 *            the arrayIndex to set
	 */
	protected void setArrayIndex(String arrayIndex) {
		this.arrayIndex = arrayIndex;
	}

	/**
	 * @return the reset
	 */
	protected boolean isReset() {
		return reset;
	}

	/**
	 * @param reset
	 *            the reset to set
	 */
	protected void setReset(boolean reset) {
		this.reset = reset;
	}

}
