package entrega.dao;

import java.sql.SQLException;
import java.util.List;

import entrega.database.Database;
import entrega.entities.Entity;
import entrega.exceptions.DatabaseException;

abstract public class Dao {
	private Database database;
	
	protected boolean databaseIsConnected() {
		return this.database.isConnected();
	}
	
	protected Database databaseConnect() throws DatabaseException {
		if (!this.databaseIsConnected()) {
			try {
				this.database.connect();
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			}
		}
		
		return this.database;
	}
	
	protected void databaseDisconnect() throws DatabaseException {		
		if (this.databaseIsConnected()) {
			try {
				this.database.disconnect();
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			}
		}
	}
	
	protected void setDatabase(Database database) {
		this.database = database;
	}
	
	protected String betweenSingleQuotes(String value) {
		return "'" + value + "'";
	}
	
	abstract protected boolean save(Entity model) throws DatabaseException;
	abstract protected boolean delete(Entity model) throws DatabaseException;

	abstract protected int getCount(Entity model) throws DatabaseException;
	
	abstract protected boolean run(String statement) throws DatabaseException;
	
	abstract protected String getInsertStatement(Entity model, List<String> values);
	abstract protected String getUpdateStatement(Entity model, List<String> fields, List<String> values);
	
	abstract public String getTable();
	abstract public List<String> getValues(Entity model);
	abstract public List<String> getFields();
}
