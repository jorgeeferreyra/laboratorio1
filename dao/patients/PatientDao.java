package entrega.dao.patients;

import java.util.List;

import entrega.entities.Doctor;
import entrega.exceptions.DoctorDaoException;

public interface PatientDao {
	public List<Doctor> getAll(int userId) throws DoctorDaoException;
	public Doctor getById(int id) throws DoctorDaoException;
	public boolean save(Doctor doctor) throws DoctorDaoException;
	public boolean delete(Doctor doctor) throws DoctorDaoException;
}