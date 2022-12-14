package entrega.dao.doctors;

import java.util.List;

import entrega.entities.Doctor;
import entrega.exceptions.DaoException;

public interface DoctorDao {
	public List<Doctor> getAll(int userId) throws DaoException;
	public Doctor getById(int id) throws DaoException;
	public boolean save(Doctor doctor) throws DaoException;
	public boolean delete(Doctor doctor) throws DaoException;
}