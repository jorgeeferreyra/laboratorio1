package entrega.dao.users;

import java.util.List;

import entrega.entities.User;
import entrega.exceptions.UserDaoException;

public interface UserDao {
	public List<User> getAll() throws UserDaoException;
	public User getById(int id) throws UserDaoException;
	public User getByIdNumberAndPassword(String idNumber, String password) throws UserDaoException;
	public User getByIdNumber(String idNumber) throws UserDaoException;
	public User getByEmail(String email) throws UserDaoException;
	public boolean save(User user) throws UserDaoException;
	public boolean delete(User user) throws UserDaoException;
	public boolean changePassword(User user, String newPassword) throws UserDaoException;
	public boolean checkPassword(User user, String password) throws UserDaoException;
}
