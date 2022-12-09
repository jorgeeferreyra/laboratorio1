package entrega.contracts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entrega.exceptions.generic.DatabaseException;

abstract public class Database {
	private Connection connection = null;
	private String url;
	private String user;
	private String password;
	private boolean autoCommit = true;

	public Database(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	public void connect() throws SQLException, DatabaseException {
		if (this.isConnected()) {
			throw DatabaseException.isAlreadyConnected(this.url);
		}
		
		this.connection = DriverManager.getConnection(this.url, this.user, this.password);
	}
	
	public void disconnect() throws SQLException, DatabaseException {
		this.requiredConnected();
		
		this.connection.close();
		this.connection = null;
	}
	
	public boolean isConnected() {
		return this.connection != null;
	}
	
	protected void requiredConnected() throws DatabaseException {
		if (!this.isConnected()) {
			throw DatabaseException.isNotConnected();
		}
	}
	
	protected Connection getConnection() {
		return this.connection;
	}
	
	public boolean transactionBegun() {
		return !this.autoCommit;
	}
	
	private void setAutoCommit(boolean autoCommit) throws SQLException, DatabaseException {
		this.requiredConnected();
		
		this.autoCommit = autoCommit;
		this.getConnection().setAutoCommit(autoCommit);
	}
	
	public void beginTransaction() throws SQLException, DatabaseException {
		if (this.transactionBegun()) {
			throw DatabaseException.transactionAlreadyBegun();
		}
		
		this.setAutoCommit(false);
	}
	
	public void commit() throws SQLException, DatabaseException {
		this.requiredConnected();
		
		if (!this.transactionBegun()) {
			throw DatabaseException.transactionWasNotStarted();
		}
		
		this.getConnection().commit();
		this.setAutoCommit(true);
	}
	
	public void rollback() throws SQLException, DatabaseException {
		this.requiredConnected();
		
		if (!this.transactionBegun()) {
			throw DatabaseException.transactionWasNotStarted();
		}
		
		this.getConnection().rollback();
		this.setAutoCommit(true);
	}
	
	public int executeUpdate(String query) throws SQLException, DatabaseException {
		this.requiredConnected();
		
		Statement statement = this.getConnection().createStatement();
		
		return statement.executeUpdate(query);
		
	}
	
	public ResultSet executeQuery(String query) throws SQLException, DatabaseException {
		this.requiredConnected();
		
		Statement statement = this.getConnection().createStatement();
		
		return statement.executeQuery(query);
	}
}
