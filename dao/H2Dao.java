package entrega.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entrega.database.Database;
import entrega.database.H2Database;
import entrega.entities.Entity;
import entrega.exceptions.DatabaseException;

abstract public class H2Dao extends Dao {	
	public H2Dao(H2Database database) {
		this.setDatabase(database);
	}
	
	public boolean save(Entity model) throws DatabaseException {
		if (model.getIsNew()) {
			model.setId(this.getCount(model));
		}
		
		String statement = model.getIsNew()
				? this.getInsertStatement(model)
				: this.getUpdateStatement(model);
				
		boolean success = this.run(statement);

		model.setIsNew(false);
				
		return success;
	}
	
	public boolean delete(Entity model) throws DatabaseException {
		return this.run("DELETE FROM " + this.getTable() + " WHERE id=" + model.getId().toString());
	}
	
	protected boolean run(String statement) throws DatabaseException {
		Database database = this.databaseConnect();
		
		if (database == null) {
			return false;
		}
		
		boolean success = false;
		
		try {			
			database.executeUpdate(statement);
			
			success = true;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.databaseDisconnect();
		}
		
		return success;
	}
	
	protected ResultSet query(String statement) throws DatabaseException {
		Database database = this.databaseConnect();
		
		if (database == null) {
			return null;
		}
		
		ResultSet result = null;
		
		try {			
			result = database.executeQuery(statement);
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		return result;
	}
	
	private String getInsertStatement(Entity model) {
		return this.getInsertStatement(model, this.getValues(model));
	}
	
	protected String getInsertStatement(Entity model, List<String> values) {
		return "INSERT INTO " + this.getTable() + " VALUES " +
				"(" + String.join(",", values) + ")";
	}
	
	protected String getUpdateStatement(Entity model) {
		return this.getUpdateStatement(model, this.getFields(), this.getValues(model));
	}
	
	protected String getUpdateStatement(Entity model, String field, String value) {
		return this.getUpdateStatement(model, Arrays.asList(new String[] {field}), Arrays.asList(new String[] {value}));
	}
	
	protected String getUpdateStatement(Entity model, List<String> fields, List<String> values) {
		List<String> rawSQL = new ArrayList<String>();
		
		for (int i=0; i < fields.size(); i++) {
			String field = fields.get(i);
			String value = values.get(i);
			
			rawSQL.add(field + "=" + value);
		}
		
		return "UPDATE " + this.getTable() + " SET " +
				String.join(",", rawSQL) +
				" WHERE id=" + model.getId().toString();
	}
	
	@Override
	protected int getCount(Entity model) throws DatabaseException {	
		int result;
		
		try {
			ResultSet resultSet = this.query("SELECT id AS count FROM " + this.getTable() + " ORDER BY id DESC LIMIT 1");
			
			if (!resultSet.next()) {
				result = 0;
			} else {
				result = resultSet.getInt("id");
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (this.databaseIsConnected()) {
				this.databaseDisconnect();
			}
		}
		
		return result + 1;
	}

}
