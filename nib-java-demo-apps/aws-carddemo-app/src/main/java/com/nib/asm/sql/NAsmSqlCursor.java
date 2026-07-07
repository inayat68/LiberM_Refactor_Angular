package com.nib.asm.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import com.nib.asm.NAsmOnlineProgram;
import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.storage.NAsmField;
import com.nib.asm.storage.NAsmField.NAsmFieldType;

/**
 * Handle an SQL cursor.
 * 
 * @author nib-labs.io
 */
public abstract class NAsmSqlCursor {

	private String name;
	private NAsmOnlineProgram main;
	private ResultSet resultSet = null;
	private Map<NAsmField, NAsmField> nullIndicators = new LinkedHashMap<>();

	protected abstract String getSQL() throws NAsmException;

	/**
	 * 
	 * @param main
	 *            the main program
	 * @param name
	 *            the cursor name
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public NAsmSqlCursor(NAsmOnlineProgram main, String name) throws NAsmException {
		this.main = main;
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Open this cursor.
	 * 
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public void open() throws NAsmException {
		Statement stm;
		this.resetSQLCODE();
		try {
			stm = main.getSQL().getConnection().createStatement();
			this.resultSet = stm.executeQuery(this.getSQL());
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				this.setSQLCODE(e.getErrorCode());
			} else {
				this.setSQLCODE(999999);
				throw new NAsmException("NAsmSqlCursor.open() - Error: " + e.getMessage());
			}
		}
	}

	/**
	 * Reset null indicators.
	 */
	public void resetNullIndicators() {
		this.nullIndicators = new LinkedHashMap<>();
	}

	/**
	 * Add a null indicator.
	 * 
	 * @param hostVariable
	 *            for which a null indicator is being added
	 * @param nullIndicator
	 *            to be added
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public void addNullIndicator(NAsmField hostVariable, NAsmField nullIndicator) throws NAsmException {
		this.nullIndicators.put(hostVariable, nullIndicator);
	}

	/**
	 * Fetch cursor record.
	 * 
	 * @param hostVariables
	 *            list of the host variables receiving the cursor data
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public void fetch(NAsmField... hostVariables) throws NAsmException {
		try {
			if (this.resultSet.next()) {
				int col = 1;
				for (NAsmField hv : hostVariables) {
					if (hv.getType().equals(NAsmFieldType.ALPHANUMERIC)) {
						int nullValue = 0;
						String value = this.resultSet.getString(col);
						if (this.resultSet.wasNull()) {
							nullValue = -1;
						} else {
							hv.set(value);
						}
						this.setNullIndicator(hv, nullValue);
					} else if (hv.getType().equals(NAsmFieldType.NUMERIC_BINARY)) {
						// hv.set(this.resultSet.getDouble(col));
						int nullValue = 0;
						double value = this.resultSet.getDouble(col);
						if (this.resultSet.wasNull()) {
							nullValue = -1;
						} else {
							hv.set(value);
						}
						this.setNullIndicator(hv, nullValue);
						col++;
					} else {
						throw new NAsmException("NAsmSqlCursor.fetch() - Unhandled data type.");
					}
				}
			} else {
				this.setSQLCODE(NAsmSql._SQLCODE_NOT_FOUND);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				this.setSQLCODE(e.getErrorCode());
			} else {
				this.setSQLCODE(999999);
				throw new NAsmException("NAsmSqlCursor.fetch() - Error: " + e.getMessage());
			}
		}
	}

	/**
	 * Close this cursor.
	 * 
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public void close() throws NAsmException {
		this.resetSQLCODE();
		try {
			if (this.resultSet != null)
				this.resultSet.close();
		} catch (SQLException e) {
			if (e.getErrorCode() != 0) {
				this.setSQLCODE(e.getErrorCode());
			} else {
				this.setSQLCODE(999999);
				throw new NAsmException("NAsmSqlCursor.close() - Error: " + e.getMessage());
			}
		}
	}

	private void setNullIndicator(NAsmField hostVariable, int nullValue) throws NAsmException {
		if (this.nullIndicators.containsKey(hostVariable)) {
			NAsmField nullIndicator = this.nullIndicators.get(hostVariable);
			nullIndicator.set(nullValue);
		}
	}

	private void resetSQLCODE() throws NAsmException {
		this.main.getSQLCODE().set(0);
	}

	private void setSQLCODE(int value) throws NAsmException {
		// TODO decode SQL value
		this.main.getSQLCODE().set(value);
	}

}
