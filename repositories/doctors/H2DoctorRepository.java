package entrega.repositories.doctors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entrega.contracts.H2Repository;
import entrega.database.H2Database;
import entrega.exceptions.generic.DatabaseException;
import entrega.exceptions.repositories.DoctorRepositoryException;
import entrega.models.Doctor;

public class H2DoctorRepository extends H2Repository implements DoctorRepository {
	public H2DoctorRepository(H2Database database) {
		super(database);
	}

	@Override
	public List<Doctor> getAll(int userId) throws DoctorRepositoryException {
		List<Doctor> result = new ArrayList<Doctor>();
		
		try {
			ResultSet resultSet = this.query("SELECT * FROM " + (new Doctor()).getTable() + " WHERE USER_ID=" + String.valueOf(userId));
			
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
			throw new DoctorRepositoryException(e.getMessage());
		} finally {
			if (this.databaseIsConnected()) {
				try {
					this.databaseDisconnect();
				} catch (DatabaseException e) {
					throw new DoctorRepositoryException(e.getMessage());
				}
			}
		}
		
		return result;
	}

	@Override
	public Doctor getById(int id) throws DoctorRepositoryException {
		return this.getOneFromQuery("SELECT * FROM " + (new Doctor()).getTable() + " WHERE ID=" + String.valueOf(id));
	}

	@Override
	public boolean save(Doctor doctor) throws DoctorRepositoryException {
		try {
			return super.save(doctor);
		} catch (DatabaseException e) {
			throw new DoctorRepositoryException(e.getMessage());
		}
	}

	@Override
	public boolean delete(Doctor doctor) throws DoctorRepositoryException {
		try {
			return super.delete(doctor);
		} catch (DatabaseException e) {
			throw new DoctorRepositoryException(e.getMessage());
		}
	}
	

	private Doctor getOneFromQuery(String query) throws DoctorRepositoryException {
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
			throw new DoctorRepositoryException(e.getMessage());
		} finally {
			if (this.databaseIsConnected()) {
				try {
					this.databaseDisconnect();
				} catch (DatabaseException e) {
					throw new DoctorRepositoryException(e.getMessage());
				}
			}
		}
		
		return doctor;
	}

}
