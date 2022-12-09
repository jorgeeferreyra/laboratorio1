package entrega.repositories.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entrega.contracts.H2Repository;
import entrega.database.H2Database;
import entrega.exceptions.generic.DatabaseException;
import entrega.exceptions.repositories.UserRepositoryException;
import entrega.models.User;

public class H2UserRepository extends H2Repository implements UserRepository {
	
	public H2UserRepository(H2Database database) {
		super(database);
	}

	@Override
	public List<User> getAll() throws UserRepositoryException {		
		List<User> result = new ArrayList<User>();
		
		try {
			ResultSet resultSet = this.query("SELECT * FROM " + (new User()).getTable());
			
			while(resultSet.next()) {
				result.add(new User(
					resultSet.getInt("id"),
					resultSet.getString("firstName"),
					resultSet.getString("lastName"),
					resultSet.getString("idNumber"),
					resultSet.getString("email"),
					resultSet.getString("password")
				));
			}
		} catch (SQLException | DatabaseException e) {
			throw new UserRepositoryException(e.getMessage());
		} finally {
			if (this.databaseIsConnected()) {
				try {
					this.databaseDisconnect();
				} catch (DatabaseException e) {
					throw new UserRepositoryException(e.getMessage());
				}
			}
		}
		
		return result;
	}

	@Override
	public User getById(int id) throws UserRepositoryException {
		return this.getOneFromQuery("SELECT * FROM " + (new User()).getTable() + " WHERE ID=" + String.valueOf(id));
	}

	@Override
	public User getByIdNumberAndPassword(String idNumber, String password) throws UserRepositoryException {
		return this.getOneFromQuery("SELECT * FROM " + (new User()).getTable() + " WHERE IDNUMBER='" + idNumber + "' AND PASSWORD='" + password + "'");
	}

	@Override
	public User getByIdNumber(String idNumber) throws UserRepositoryException {
		return this.getOneFromQuery("SELECT * FROM " + (new User()).getTable() + " WHERE IDNUMBER='" + idNumber + "'");
	}

	@Override
	public User getByEmail(String email) throws UserRepositoryException {
		return this.getOneFromQuery("SELECT * FROM " + (new User()).getTable() + " WHERE EMAIL='" + email + "'");
	}

	@Override
	public boolean save(User user) throws UserRepositoryException {
		try {
			return super.save(user);
		} catch (DatabaseException e) {
			throw new UserRepositoryException(e.getMessage());
		}
	}

	@Override
	public boolean delete(User user) throws UserRepositoryException {
		try {
			return super.delete(user);
		} catch (DatabaseException e) {
			throw new UserRepositoryException(e.getMessage());
		}
	}

	@Override
	public boolean checkPassword(User user, String password) {
		// TODO: encrypt password
		return user.getHashedPassword() == password;
	}

	@Override
	public boolean changePassword(User user, String newPassword) throws UserRepositoryException {
		// TODO: encrypt password
		
		boolean success;
		
		try {
			success = this.run(this.getUpdateStatement(user, "password", newPassword));
		} catch (DatabaseException e) {
			throw new UserRepositoryException(e.getMessage());
		}
		
		if (success) {
			user.setPassword(newPassword);
		}
		
		return success;
	}
	
	private User getOneFromQuery(String query) throws UserRepositoryException {
		User user = null;
		
		try {
			ResultSet resultSet = this.query(query);
			
			user = resultSet.next()
				? new User(
						resultSet.getInt("id"),
						resultSet.getString("firstName"),
						resultSet.getString("lastName"),
						resultSet.getString("idNumber"),
						resultSet.getString("email"),
						resultSet.getString("password")
					)
				: null;
		} catch (SQLException | DatabaseException e) {
			throw new UserRepositoryException(e.getMessage());
		} finally {
			if (this.databaseIsConnected()) {
				try {
					this.databaseDisconnect();
				} catch (DatabaseException e) {
					throw new UserRepositoryException(e.getMessage());
				}
			}
		}
		
		return user;
	}
}
