package entrega.repositories.users;

import java.util.List;

import entrega.exceptions.repositories.UserRepositoryException;
import entrega.models.User;

public interface UserRepository {
	public List<User> getAll() throws UserRepositoryException;
	public User getById(int id) throws UserRepositoryException;
	public User getByIdNumberAndPassword(String idNumber, String password) throws UserRepositoryException;
	public User getByIdNumber(String idNumber) throws UserRepositoryException;
	public User getByEmail(String email) throws UserRepositoryException;
	public boolean save(User user) throws UserRepositoryException;
	public boolean delete(User user) throws UserRepositoryException;
	public boolean changePassword(User user, String newPassword) throws UserRepositoryException;
	public boolean checkPassword(User user, String password) throws UserRepositoryException;
}
