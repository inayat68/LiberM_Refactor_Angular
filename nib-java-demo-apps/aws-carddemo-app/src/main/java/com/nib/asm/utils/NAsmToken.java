package com.nib.asm.utils;

/**
 * Handle a program token, used internally by lexical parser only.
 * 
 * @author nib-labs.io
 */
public class NAsmToken {
	/**
	 * String of the token.
	 */
	private String word = "";

	/**
	 * true if the token if between quotes.
	 */
	private boolean literal = false;

	/**
	 * true if the token is numeric.
	 */
	private boolean numeric = false;

	/**
	 * Number of the statement of the token.
	 */
	private int line = 0;
	private int column = 0;

	/**
	 * Identification number of the include if present.
	 */
	private int includeId = 0;

	/**
	 * An applied function to the field.
	 */
	private NAsmToken appliedFunction = null;

	/**
	 * NAsmVariable reference.
	 */
	private NAsmVariable NAsmVariable = null;

	/**
	 * Reserved Word.
	 */
	private boolean reservedWord = false;

	/**
	 * Operand.
	 */
	private boolean operand = false;

	/**
	 *
	 * @param word
	 *            the word used to create the token
	 */
	public NAsmToken(String word) {
		this(word, 0, 0);
	}

	/**
	 *
	 * @param NAsmToken
	 *            the NAsmToken used to create a new instance
	 */
	public NAsmToken(NAsmToken NAsmToken) {
		this(NAsmToken.getWord(), NAsmToken.getLine(), NAsmToken.getColumn(), NAsmToken.getIncludeId());
	}

	/**
	 *
	 * @param word
	 *            the word used to create the token
	 * @param location
	 *            the token to be used as reference to assign the token location
	 */
	public NAsmToken(String word, NAsmToken location) {
		this(word, location.getLine(), location.getColumn(), location.getIncludeId());
	}

	/**
	 *
	 * @param word
	 *            the word used to create the token
	 * @param line
	 *            the token line
	 */
	public NAsmToken(String word, int line) {
		this(word, line, 0);
	}

	/**
	 * 
	 * @param word
	 *            the word used to create the token
	 * @param line
	 *            the token line
	 * @param column
	 *            the token column
	 */
	public NAsmToken(String word, int line, int column) {
		this(word, line, column, 0);
	}

	/**
	 * 
	 * @param word
	 *            the word used to create the token
	 * @param line
	 *            the token line
	 * @param column
	 *            the token column
	 * @param includeId
	 *            the include id
	 */
	public NAsmToken(String word, int line, int column, int includeId) {
		this.setWord(word);
		this.line = line;
		this.column = column;
		this.includeId = includeId;
	}

	/**
	 * Clone this token.
	 * 
	 * @return a new instance of the token
	 */
	public NAsmToken clone() {
		NAsmToken newNAsmToken = new NAsmToken(this.getWord(), this.getLine(), this.getColumn(), this.getIncludeId());
		newNAsmToken.appliedFunction = appliedFunction;
		newNAsmToken.NAsmVariable = NAsmVariable;
		newNAsmToken.reservedWord = reservedWord;
		newNAsmToken.operand = operand;
		return newNAsmToken;
	}

	/**
	 * 
	 * @param newWord
	 *            new value to be used to clone the existing instance of the
	 *            token
	 * @return a new instance of the token
	 */
	public NAsmToken clone(String newWord) {
		NAsmToken newNAsmToken = new NAsmToken(newWord, this.getLine(), this.getColumn(), this.getIncludeId());
		return newNAsmToken;
	}

	/**
	 * @return the literal
	 */
	public boolean isLiteral() {
		return this.literal;
	}

	/**
	 * @param literal
	 *            the literal to set
	 */
	private void setLiteral(boolean literal) {
		this.literal = literal;
	}

	/**
	 * @return the line
	 */
	public int getLine() {
		return this.line;
	}

	/**
	 * @param line
	 *            the statement to set
	 */
	public void setLine(int line) {
		this.line = line;
	}

	/**
	 * @return the numeric
	 */
	public boolean isNumeric() {
		return this.numeric;
	}

	/**
	 * @param numeric
	 *            the numeric to set
	 */
	private void setNumeric(boolean numeric) {
		this.numeric = numeric;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getWord();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String) {
			return this.word.equals((String) obj);
		}
		return super.equals(obj);
	}

	/**
	 * @return the includeId
	 */
	public int getIncludeId() {
		return this.includeId;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return this.column;
	}

	/**
	 * @param column
	 *            the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @return the word
	 */
	public String getWord() {
		return this.word;
	}

	/**
	 * @return the word in numeric format
	 * @throws Exception
	 *             if a problem occurs.
	 */
	public int getIntValue() throws Exception {
		return Integer.parseInt(this.word);
	}

	/**
	 * @return the flat word in case it is a literal
	 */
	public String getFlat() {
		if (this.isLiteral() && this.word.length() > 1) {
			String s = this.word.substring(1, this.word.length() - 1).trim();
			return s;
		}
		return this.word;
	}

	/**
	 * @return the word normalized to be inserted in the database
	 */
	public String getJavaWord() {
		if (this.word == null) {
			return "";
		} else if (this.isLiteral()) {
			String s = this.word.replaceAll("\\Q'\\E", "\"");
			return s;
		} else if (this.isNumeric()) {
			String s = this.word;
			while (s.startsWith("0") && s.length() > 1) {
				s = s.substring(1);
			}
			return s;
		} else if (this.word.indexOf("-") > -1) {
			String s = this.word.replaceAll("\\Q-\\E", "_");
			return s;
		}
		return this.word;
	}

	/**
	 * @param word
	 *            the word to set
	 */
	public void setWord(String word) {
		this.word = word;
		this.setLiteral(false);
		this.setNumeric(false);
		if (word != null && word.length() > 0) {
			if (word.indexOf("'") > -1 || word.indexOf("\"") > -1)
				this.setLiteral(true);
			this.setNumeric(NAsmStrings.isNumeric(word));
		}

	}

	/**
	 * @return the NAsmVariable
	 */
	public NAsmVariable getVariable() {
		return NAsmVariable;
	}

	/**
	 * @param NAsmVariable
	 *            the NAsmVariable to set
	 */
	public void setVariable(NAsmVariable NAsmVariable) {
		this.NAsmVariable = NAsmVariable;
	}

	/**
	 * @return the reservedWord
	 */
	public boolean isReservedWord() {
		return reservedWord;
	}

	/**
	 * @param reservedWord
	 *            the reservedWord to set
	 */
	public void setReservedWord(boolean reservedWord) {
		this.reservedWord = reservedWord;
	}

	/**
	 * @return the operand
	 */
	public boolean isOperand() {
		return operand;
	}

	/**
	 * @param operand
	 *            the operand to set
	 */
	public void setOperand(boolean operand) {
		this.operand = operand;
	}

}
