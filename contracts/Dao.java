package entrega.contracts;

import java.sql.SQLException;
import java.util.List;

import entrega.exceptions.generic.DatabaseException;

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
	
	abstract protected boolean save(Model model) throws DatabaseException;
	abstract protected boolean delete(Model model) throws DatabaseException;

	abstract protected int getCount(Model model) throws DatabaseException;
	
	abstract protected boolean run(String statement) throws DatabaseException;
	
	abstract protected String getInsertStatement(Model model, List<String> values);
	abstract protected String getUpdateStatement(Model model, List<String> fields, List<String> values);
	
	abstract public String getTable();
	abstract public List<String> getValues(Model model);
	abstract public List<String> getFields();
}
