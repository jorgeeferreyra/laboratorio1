package entrega.repositories.doctors;

import java.util.List;

import entrega.exceptions.repositories.DoctorRepositoryException;
import entrega.models.Doctor;

public interface DoctorRepository {
	public List<Doctor> getAll(int userId) throws DoctorRepositoryException;
	public Doctor getById(int id) throws DoctorRepositoryException;
	public boolean save(Doctor doctor) throws DoctorRepositoryException;
	public boolean delete(Doctor doctor) throws DoctorRepositoryException;
}