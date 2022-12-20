package entrega.dao.patients;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entrega.dao.H2Dao;
import entrega.database.H2Database;
import entrega.entities.Entity;
import entrega.entities.Patient;
import entrega.exceptions.DatabaseException;
import entrega.exceptions.DaoException;

public class PatientH2Dao extends H2Dao implements PatientDao {
	public PatientH2Dao(H2Database database) {
		super(database);
	}

	@Override
	public List<Patient> getAll(int userId) throws DaoException {
		List<Patient> result = new ArrayList<Patient>();
		
		try {
			ResultSet resultSet = this.query("SELECT * FROM " + this.getTable() + " WHERE USER_ID=" + String.valueOf(userId));
			
			while(resultSet.next()) {
				result.add(new Patient(
					resultSet.getInt("id"),
					resultSet.getInt("user_id"),
					resultSet.getInt("health_assurance_id"),
					resultSet.getString("firstName"),
					resultSet.getString("lastName"),
					resultSet.getString("phone"),
					resultSet.getString("email")
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
	public Patient getById(int id) throws DaoException {
		return this.getOneFromQuery("SELECT * FROM " + this.getTable() + " WHERE ID=" + String.valueOf(id));
	}

	@Override
	public boolean save(Patient patient) throws DaoException {
		try {
			return super.save(patient);
		} catch (DatabaseException e) {
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public boolean delete(Patient patient) throws DaoException {
		try {
			return super.delete(patient);
		} catch (DatabaseException e) {
			throw new DaoException(e.getMessage());
		}
	}
	

	private Patient getOneFromQuery(String query) throws DaoException {
		Patient patient = null;
		
		try {
			ResultSet resultSet = this.query(query);
			
			patient = resultSet.next()
				? new Patient(
						resultSet.getInt("id"),
						resultSet.getInt("user_id"),
						resultSet.getInt("health_assurance_id"),
						resultSet.getString("firstName"),
						resultSet.getString("lastName"),
						resultSet.getString("phone"),
						resultSet.getString("email")
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
		
		return patient;
	}
	

	
	public String getTable() {
		return "patients";
	}
	
	public List<String> getValues(Entity model) {
		Patient patient = (Patient) model;
		
		return Arrays.asList(new String[] {
			patient.getId().toString(),
			patient.getUserId().toString(),
			patient.getHealthAssuranceId().toString(),
			this.betweenSingleQuotes(patient.getFirstName()), 
			this.betweenSingleQuotes(patient.getLastName()), 
			this.betweenSingleQuotes(patient.getPhone()), 
			this.betweenSingleQuotes(patient.getEmail())
		});
	}

	public List<String> getFields() {
		return Arrays.asList(new String[] {"id", "user_id", "health_assurance_id", "firstName", "lastName", "phone", "email"});
	}

	@Override
	public List<Patient> getByHealthAssuranceId(int healthAssuranceId) throws DaoException {
		List<Patient> result = new ArrayList<Patient>();
		
		try {
			ResultSet resultSet = this.query("SELECT * FROM " + this.getTable() + " WHERE HEALTH_ASSURANCE_ID=" + String.valueOf(healthAssuranceId));
			
			while(resultSet.next()) {
				result.add(new Patient(
					resultSet.getInt("id"),
					resultSet.getInt("user_id"),
					resultSet.getInt("health_assurance_id"),
					resultSet.getString("firstName"),
					resultSet.getString("lastName"),
					resultSet.getString("phone"),
					resultSet.getString("email")
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

}
