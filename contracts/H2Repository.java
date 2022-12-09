package entrega.contracts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entrega.database.H2Database;
import entrega.exceptions.generic.DatabaseException;

abstract public class H2Repository extends DaoRepository {	
	
	public H2Repository(H2Database database) {
		this.setDatabase(database);
	}
	
	public boolean save(DaoModel model) throws DatabaseException {
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
	
	public boolean delete(DaoModel model) throws DatabaseException {
		return this.run("DELETE FROM " + model.getTable() + " WHERE id=" + model.getId().toString());
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
	
	private String getInsertStatement(DaoModel model) {
		return this.getInsertStatement(model, model.getValues());
	}
	
	protected String getInsertStatement(DaoModel model, List<String> values) {
		return "INSERT INTO " + model.getTable() + " VALUES " +
				"(" + String.join(",", values) + ")";
	}
	
	protected String getUpdateStatement(DaoModel model) {
		return this.getUpdateStatement(model, model.getFields(), model.getValues());
	}
	
	protected String getUpdateStatement(DaoModel model, String field, String value) {
		return this.getUpdateStatement(model, Arrays.asList(new String[] {field}), Arrays.asList(new String[] {value}));
	}
	
	protected String getUpdateStatement(DaoModel model, List<String> fields, List<String> values) {
		List<String> rawSQL = new ArrayList<String>();
		
		for (int i=0; i < fields.size(); i++) {
			String field = fields.get(i);
			String value = values.get(i);
			
			rawSQL.add(field + "=" + value);
		}
		
		return "UPDATE " + model.getTable() + " SET " +
				String.join(",", rawSQL) +
				" WHERE id=" + model.getId().toString();
	}
	
	@Override
	protected int getCount(DaoModel model) throws DatabaseException {	
		int result;
		
		try {
			ResultSet resultSet = this.query("SELECT COUNT(id) AS count FROM " + model.getTable());
			
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
