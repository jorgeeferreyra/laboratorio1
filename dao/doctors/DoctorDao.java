package entrega.dao.doctors;

import java.util.List;

import entrega.entities.Doctor;
import entrega.exceptions.DoctorDaoException;

public interface DoctorDao {
	public List<Doctor> getAll(int userId) throws DoctorDaoException;
	public Doctor getById(int id) throws DoctorDaoException;
	public boolean save(Doctor doctor) throws DoctorDaoException;
	public boolean delete(Doctor doctor) throws DoctorDaoException;
}