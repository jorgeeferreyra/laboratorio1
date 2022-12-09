package entrega.contracts;

import java.sql.SQLException;
import java.util.List;

import entrega.exceptions.generic.DatabaseException;

abstract public class DaoRepository {
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
	
	abstract protected boolean save(DaoModel model) throws DatabaseException;
	abstract protected boolean delete(DaoModel model) throws DatabaseException;

	abstract protected int getCount(DaoModel model) throws DatabaseException;
	
	abstract protected boolean run(String statement) throws DatabaseException;
	
	abstract protected String getInsertStatement(DaoModel model, List<String> values);
	abstract protected String getUpdateStatement(DaoModel model, List<String> fields, List<String> values);
}
