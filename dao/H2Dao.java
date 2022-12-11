package entrega.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entrega.database.Database;
import entrega.database.H2Database;
import entrega.exceptions.DatabaseException;
import entrega.models.Model;

abstract public class H2Dao extends Dao {	
	public H2Dao(H2Database database) {
		this.setDatabase(database);
	}
	
	public boolean save(Model model) throws DatabaseException {
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
	
	public boolean delete(Model model) throws DatabaseException {
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
	
	private String getInsertStatement(Model model) {
		return this.getInsertStatement(model, this.getValues(model));
	}
	
	protected String getInsertStatement(Model model, List<String> values) {
		return "INSERT INTO " + this.getTable() + " VALUES " +
				"(" + String.join(",", values) + ")";
	}
	
	protected String getUpdateStatement(Model model) {
		return this.getUpdateStatement(model, this.getFields(), this.getValues(model));
	}
	
	protected String getUpdateStatement(Model model, String field, String value) {
		return this.getUpdateStatement(model, Arrays.asList(new String[] {field}), Arrays.asList(new String[] {value}));
	}
	
	protected String getUpdateStatement(Model model, List<String> fields, List<String> values) {
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
	protected int getCount(Model model) throws DatabaseException {	
		int result;
		
		try {
			ResultSet resultSet = this.query("SELECT COUNT(id) AS count FROM " + this.getTable());
			
			if (!resultSet.next()) {
				throw new DatabaseException("No se obtuvieron resultados para un recuento. No se pudo calcular identificador autoincremental.");
			}
			
			result = resultSet.getInt("count");
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
