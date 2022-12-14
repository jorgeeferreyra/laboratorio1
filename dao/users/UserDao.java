package entrega.dao.users;

import java.util.List;

import entrega.entities.User;
import entrega.exceptions.DaoException;

public interface UserDao {
	public List<User> getAll() throws DaoException;
	public User getById(int id) throws DaoException;
	public User getByIdNumberAndPassword(String idNumber, String password) throws DaoException;
	public User getByIdNumber(String idNumber) throws DaoException;
	public User getByEmail(String email) throws DaoException;
	public boolean save(User user) throws DaoException;
	public boolean delete(User user) throws DaoException;
	public boolean changePassword(User user, String newPassword) throws DaoException;
	public boolean checkPassword(User user, String password) throws DaoException;
}
