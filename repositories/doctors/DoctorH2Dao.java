package entrega.repositories.doctors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entrega.contracts.H2Dao;
import entrega.contracts.Model;
import entrega.database.H2Database;
import entrega.exceptions.generic.DatabaseException;
import entrega.exceptions.repositories.DoctorDaoException;
import entrega.models.Doctor;

public class DoctorH2Dao extends H2Dao implements DoctorDao {
	public DoctorH2Dao(H2Database database) {
		super(database);
	}

	@Override
	public List<Doctor> getAll(int userId) throws DoctorDaoException {
		List<Doctor> result = new ArrayList<Doctor>();
		
		try {
			ResultSet resultSet = this.query("SELECT * FROM " + this.getTable() + " WHERE USER_ID=" + String.valueOf(userId));
			
			while(resultSet.next()) {
				result.add(new Doctor(
					resultSet.getInt("id"),
					resultSet.getInt("user_id"),
					resultSet.getString("firstName"),
					resultSet.getString("lastName"),
					resultSet.getString("phone"),
					resultSet.getString("email")
				));
			}
		} catch (SQLException | DatabaseException e) {
			throw new DoctorDaoException(e.getMessage());
		} finally {
			if (this.databaseIsConnected()) {
				try {
					this.databaseDisconnect();
				} catch (DatabaseException e) {
					throw new DoctorDaoException(e.getMessage());
				}
			}
		}
		
		return result;
	}

	@Override
	public Doctor getById(int id) throws DoctorDaoException {
		return this.getOneFromQuery("SELECT * FROM " + this.getTable() + " WHERE ID=" + String.valueOf(id));
	}

	@Override
	public boolean save(Doctor doctor) throws DoctorDaoException {
		try {
			return super.save(doctor);
		} catch (DatabaseException e) {
			throw new DoctorDaoException(e.getMessage());
		}
	}

	@Override
	public boolean delete(Doctor doctor) throws DoctorDaoException {
		try {
			return super.delete(doctor);
		} catch (DatabaseException e) {
			throw new DoctorDaoException(e.getMessage());
		}
	}
	

	private Doctor getOneFromQuery(String query) throws DoctorDaoException {
		Doctor doctor = null;
		
		try {
			ResultSet resultSet = this.query(query);
			
			doctor = resultSet.next()
				? new Doctor(
						resultSet.getInt("id"),
						resultSet.getInt("user_id"),
						resultSet.getString("firstName"),
						resultSet.getString("lastName"),
						resultSet.getString("phone"),
						resultSet.getString("email")
					)
				: null;
		} catch (SQLException | DatabaseException e) {
			throw new DoctorDaoException(e.getMessage());
		} finally {
			if (this.databaseIsConnected()) {
				try {
					this.databaseDisconnect();
				} catch (DatabaseException e) {
					throw new DoctorDaoException(e.getMessage());
				}
			}
		}
		
		return doctor;
	}
	

	
	public String getTable() {
		return "doctors";
	}
	
	public List<String> getValues(Model model) {
		Doctor doctor = (Doctor) model;
		
		return Arrays.asList(new String[] {
			doctor.getId().toString(),
			doctor.getUserId().toString(),
			this.betweenSingleQuotes(doctor.getFirstName()), 
			this.betweenSingleQuotes(doctor.getLastName()), 
			this.betweenSingleQuotes(doctor.getPhone()), 
			this.betweenSingleQuotes(doctor.getEmail())
		});
	}

	public List<String> getFields() {
		return Arrays.asList(new String[] {"id", "user_id", "firstName", "lastName", "phone", "email"});
	}

}
