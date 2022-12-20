package entrega.dao.patients;

import java.util.List;

import entrega.entities.Patient;
import entrega.exceptions.DaoException;

public interface PatientDao {
	public List<Patient> getAll(int userId) throws DaoException;
	public List<Patient> getByHealthAssuranceId(int healthAssuranceId) throws DaoException;
	public Patient getById(int id) throws DaoException;
	public boolean save(Patient patient) throws DaoException;
	public boolean delete(Patient patient) throws DaoException;
}