package entrega.dao.appointments;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entrega.dao.H2Dao;
import entrega.database.H2Database;
import entrega.entities.Appointment;
import entrega.entities.Entity;
import entrega.exceptions.DatabaseException;
import entrega.exceptions.DaoException;

public class AppointmentH2Dao extends H2Dao implements AppointmentDao {
	public AppointmentH2Dao(H2Database database) {
		super(database);
	}

	@Override
	public List<Appointment> getAll(int userId) throws DaoException {
		List<Appointment> result = new ArrayList<Appointment>();
		
		try {
			ResultSet resultSet = this.query("SELECT * FROM " + this.getTable() + " WHERE USER_ID=" + String.valueOf(userId));
			
			while(resultSet.next()) {
				result.add(new Appointment(
					resultSet.getInt("id"),
					resultSet.getInt("user_id"),
					resultSet.getInt("doctor_id"),
					resultSet.getInt("patient_id"),
					resultSet.getString("starts_at"),
					resultSet.getInt("duration")
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
	public Appointment getById(int id) throws DaoException {
		return this.getOneFromQuery("SELECT * FROM " + this.getTable() + " WHERE ID=" + String.valueOf(id));
	}

	@Override
	public boolean save(Appointment appointment) throws DaoException {
		try {
			return super.save(appointment);
		} catch (DatabaseException e) {
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public boolean delete(Appointment appointment) throws DaoException {
		try {
			return super.delete(appointment);
		} catch (DatabaseException e) {
			throw new DaoException(e.getMessage());
		}
	}
	

	private Appointment getOneFromQuery(String query) throws DaoException {
		Appointment appointment = null;
		
		try {
			ResultSet resultSet = this.query(query);
			
			appointment = resultSet.next()
				? new Appointment(
						resultSet.getInt("id"),
						resultSet.getInt("user_id"),
						resultSet.getInt("doctor_id"),
						resultSet.getInt("patient_id"),
						resultSet.getString("starts_at"),
						resultSet.getInt("duration")
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
		
		return appointment;
	}
	

	
	public String getTable() {
		return "appointments";
	}
	
	public List<String> getValues(Entity model) {
		Appointment appointment = (Appointment) model;
		
		return Arrays.asList(new String[] {
			appointment.getId().toString(),
			appointment.getUserId().toString(),
			appointment.getDoctorId().toString(), 
			appointment.getPatientId().toString(), 
			this.betweenSingleQuotes(appointment.getStartsAt()), 
			appointment.getDuration().toString()
		});
	}

	public List<String> getFields() {
		return Arrays.asList(new String[] {"id", "user_id", "doctor_id", "patient_id", "starts_at", "duration"});
	}
}
