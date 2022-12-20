package entrega.dao.appointments;

import java.util.List;

import entrega.entities.Appointment;
import entrega.exceptions.DaoException;

public interface AppointmentDao {
	public List<Appointment> getAll(int userId) throws DaoException;
	public Appointment getById(int id) throws DaoException;
	public boolean save(Appointment appointment) throws DaoException;
	public boolean delete(Appointment appointment) throws DaoException;
}