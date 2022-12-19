package entrega.dao.healthAssurances;

import java.util.List;

import entrega.entities.HealthAssurance;
import entrega.exceptions.DaoException;

public interface HealthAssuranceDao {
	public List<HealthAssurance> getAll(int userId) throws DaoException;
	public HealthAssurance getById(int id) throws DaoException;
	public boolean save(HealthAssurance healthAssurance) throws DaoException;
	public boolean delete(HealthAssurance healthAssurance) throws DaoException;
}