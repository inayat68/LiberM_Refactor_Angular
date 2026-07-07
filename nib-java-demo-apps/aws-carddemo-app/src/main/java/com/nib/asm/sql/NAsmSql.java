package com.nib.asm.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.nib.asm.NAsmOnlineProgram;
import com.nib.asm.exceptions.NAsmException;
import com.nib.asm.storage.INAsmStandardIO;
import com.nib.asm.utils.NAsmEnvironment;

/**
 * Handle the SQL connection and the basic SQL operations, an instance of this
 * class and the actual SQL connection are created in NAsmOnlineProgram (see the
 * {@link NAsmOnlineProgram#getSQL() NAsmOnlineProgram#getSQL
 * method}) when a converted NAsmRuntime program executes a SQL command.
 * 
 * @author nib-labs.io
 */
public class NAsmSql {

	/**
	 * SQL connection.
	 */
	private Connection connection = null;

	/**
	 * Default DB2 constants for record not found.
	 */
	public static int _SQLCODE_NOT_FOUND = 100;

	/**
	 * Create a SQL connection using the parameters setup in NAsmEnvironment
	 * (see the {@link NAsmEnvironment
	 * NAsmEnvironment class}).
	 * 
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public NAsmSql() throws NAsmException {
		String url = NAsmEnvironment.SQL_URL();
		String driver = NAsmEnvironment.SQL_DRIVER();
		String user = NAsmEnvironment.SQL_USER();
		String password = NAsmEnvironment.SQL_PASSWORD();
		if (url != null && url.trim().length() > 0) {
			NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.INFO, "SQL connection: " + url);
			this.connect(driver, url, user, password);
		} else {
			NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.INFO, "NO SQL connection.");
		}
	}

	/**
	 * Create a SQL connection using an existing SQL connection
	 * 
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public NAsmSql(Connection sqlConnection) throws NAsmException {
		NAsmEnvironment.console(INAsmStandardIO.NAsmMessageType.INFO, "Inherited SQL connection in use.");
		this.connection = sqlConnection;
	}
	
	protected void connect(String driver, String url, String user, String password) throws NAsmException {
		if (this.connection != null)
			return;
		try {
			Class.forName(driver);
			this.connection = null;
			this.connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			NAsmException eex = new NAsmException("NAsmSql.connect() - Error: " + e.getMessage());
			eex.setJavaStack(e);
			throw eex;
		}
	}

	/**
	 * SQL connection created when the class instance is created.
	 * 
	 * @return the SQL connection
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public Connection getConnection() throws NAsmException {
		return this.connection;
	}

	/**
	 * Execute a SQL rollback command.
	 * 
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public void rollback() throws NAsmException {
		try {
			this.connection.rollback();
		} catch (SQLException e) {
			NAsmException eex = new NAsmException("NAsmSql.rollback() - Error: " + e.getMessage());
			eex.setJavaStack(e);
			throw eex;
		}
	}

	/**
	 * Execute a SQL commit command.
	 * 
	 * @throws NAsmException
	 *             if there is an NAsmRuntime specific issue
	 */
	public void commit() throws NAsmException {
		try {
			this.connection.commit();
		} catch (SQLException e) {
			NAsmException eex = new NAsmException("NAsmSql.rollback() - Error: " + e.getMessage());
			eex.setJavaStack(e);
			throw eex;
		}
	}

}
