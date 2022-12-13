package entrega.dao.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entrega.dao.H2Dao;
import entrega.database.H2Database;
import entrega.entities.Entity;
import entrega.entities.User;
import entrega.exceptions.DatabaseException;
import entrega.exceptions.UserDaoException;

public class UserH2Dao extends H2Dao implements UserDao {
	
	public UserH2Dao(H2Database database) {
		super(database);
	}

	@Override
	public List<User> getAll() throws UserDaoException {		
		List<User> result = new ArrayList<User>();
		
		try {
			ResultSet resultSet = this.query("SELECT * FROM " + this.getTable());
			
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
			throw new UserDaoException(e.getMessage());
		} finally {
			if (this.databaseIsConnected()) {
				try {
					this.databaseDisconnect();
				} catch (DatabaseException e) {
					throw new UserDaoException(e.getMessage());
				}
			}
		}
		
		return result;
	}

	@Override
	public User getById(int id) throws UserDaoException {
		return this.getOneFromQuery("SELECT * FROM " + this.getTable() + " WHERE ID=" + String.valueOf(id));
	}

	@Override
	public User getByIdNumberAndPassword(String idNumber, String password) throws UserDaoException {
		return this.getOneFromQuery("SELECT * FROM " + this.getTable() + " WHERE IDNUMBER='" + idNumber + "' AND PASSWORD='" + password + "'");
	}

	@Override
	public User getByIdNumber(String idNumber) throws UserDaoException {
		return this.getOneFromQuery("SELECT * FROM " + this.getTable() + " WHERE IDNUMBER='" + idNumber + "'");
	}

	@Override
	public User getByEmail(String email) throws UserDaoException {
		return this.getOneFromQuery("SELECT * FROM " + this.getTable() + " WHERE EMAIL='" + email + "'");
	}

	@Override
	public boolean save(User user) throws UserDaoException {
		try {
			return super.save(user);
		} catch (DatabaseException e) {
			throw new UserDaoException(e.getMessage());
		}
	}

	@Override
	public boolean delete(User user) throws UserDaoException {
		try {
			return super.delete(user);
		} catch (DatabaseException e) {
			throw new UserDaoException(e.getMessage());
		}
	}

	@Override
	public boolean checkPassword(User user, String password) {
		// TODO: encrypt password
		return user.getHashedPassword() == password;
	}

	@Override
	public boolean changePassword(User user, String newPassword) throws UserDaoException {
		// TODO: encrypt password
		
		boolean success;
		
		try {
			success = this.run(this.getUpdateStatement(user, "password", newPassword));
		} catch (DatabaseException e) {
			throw new UserDaoException(e.getMessage());
		}
		
		if (success) {
			user.setPassword(newPassword);
		}
		
		return success;
	}
	
	private User getOneFromQuery(String query) throws UserDaoException {
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
			throw new UserDaoException(e.getMessage());
		} finally {
			if (this.databaseIsConnected()) {
				try {
					this.databaseDisconnect();
				} catch (DatabaseException e) {
					throw new UserDaoException(e.getMessage());
				}
			}
		}
		
		return user;
	}
	
	public String getTable() {
		return "users";
	}
	
	public List<String> getValues(Entity model) {
		User user = (User) model;
		
		return Arrays.asList(new String[] {
			model.getId().toString(),
			this.betweenSingleQuotes(user.getFirstName()), 
			this.betweenSingleQuotes(user.getLastName()), 
			this.betweenSingleQuotes(user.getIdNumber()), 
			this.betweenSingleQuotes(user.getEmail()),
			this.betweenSingleQuotes(user.getHashedPassword())
		});
	}

	public List<String> getFields() {
		return Arrays.asList(new String[] {"id", "firstName", "lastName", "idNumber", "email", "password"});
	}
}
