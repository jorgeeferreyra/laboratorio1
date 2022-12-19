package entrega.dao.healthAssurances;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entrega.dao.H2Dao;
import entrega.database.H2Database;
import entrega.entities.HealthAssurance;
import entrega.entities.Entity;
import entrega.exceptions.DatabaseException;
import entrega.exceptions.DaoException;

public class HealthAssuranceH2Dao extends H2Dao implements HealthAssuranceDao {
	public HealthAssuranceH2Dao(H2Database database) {
		super(database);
	}

	@Override
	public List<HealthAssurance> getAll(int userId) throws DaoException {
		List<HealthAssurance> result = new ArrayList<HealthAssurance>();
		
		try {
			ResultSet resultSet = this.query("SELECT * FROM " + this.getTable() + " WHERE USER_ID=" + String.valueOf(userId));
			
			while(resultSet.next()) {
				result.add(new HealthAssurance(
					resultSet.getInt("id"),
					resultSet.getInt("user_id"),
					resultSet.getString("name")
				));
			}
		} catch (SQLException | DatabaseException e) {
			throw new DaoException(e.getMessage());
		} finally {
			if (this.databaseIsConnected()) {
				try {
					this.databaseDisconnect();
				} catch (DatabaseException e) {
					throw new DaoException(e.getMessage());
				}
			}
		}
		
		return result;
	}

	@Override
	public HealthAssurance getById(int id) throws DaoException {
		return this.getOneFromQuery("SELECT * FROM " + this.getTable() + " WHERE ID=" + String.valueOf(id));
	}

	@Override
	public boolean save(HealthAssurance healthAssurance) throws DaoException {
		try {
			return super.save(healthAssurance);
		} catch (DatabaseException e) {
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public boolean delete(HealthAssurance healthAssurance) throws DaoException {
		try {
			return super.delete(healthAssurance);
		} catch (DatabaseException e) {
			throw new DaoException(e.getMessage());
		}
	}
	

	private HealthAssurance getOneFromQuery(String query) throws DaoException {
		HealthAssurance healthAssurance = null;
		
		try {
			ResultSet resultSet = this.query(query);
			
			healthAssurance = resultSet.next()
				? new HealthAssurance(
						resultSet.getInt("id"),
						resultSet.getInt("user_id"),
						resultSet.getString("name")
					)
				: null;
		} catch (SQLException | DatabaseException e) {
			throw new DaoException(e.getMessage());
		} finally {
			if (this.databaseIsConnected()) {
				try {
					this.databaseDisconnect();
				} catch (DatabaseException e) {
					throw new DaoException(e.getMessage());
				}
			}
		}
		
		return healthAssurance;
	}
	

	
	public String getTable() {
		return "health_assurances";
	}
	
	public List<String> getValues(Entity model) {
		HealthAssurance healthAssurance = (HealthAssurance) model;
		
		return Arrays.asList(new String[] {
			healthAssurance.getId().toString(),
			healthAssurance.getUserId().toString(),
			this.betweenSingleQuotes(healthAssurance.getName())
		});
	}

	public List<String> getFields() {
		return Arrays.asList(new String[] {"id", "user_id", "name"});
	}
}
