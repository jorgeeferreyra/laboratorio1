package entrega.repositories;

import entrega.contracts.Database;
import entrega.database.H2Database;
import entrega.exceptions.generic.DatabaseException;
import entrega.exceptions.repositories.DoctorDaoException;
import entrega.exceptions.repositories.UserDaoException;
import entrega.repositories.doctors.DoctorDao;
import entrega.repositories.doctors.DoctorH2Dao;
import entrega.repositories.users.UserH2Dao;
import entrega.repositories.users.UserDao;

public class H2DaoAbstractFactory {
	private static Database database;
	private static String connection = "jdbc:h2:D:\\Jorge\\Code\\facultad\\Laboratorio1\\db";
	private static String username = "sa";
	private static String password = "";

	private static Database getDatabase() throws DatabaseException {
		if (H2DaoAbstractFactory.database == null) {
			H2DaoAbstractFactory.database = new H2Database(H2DaoAbstractFactory.connection, H2DaoAbstractFactory.username, H2DaoAbstractFactory.password);
		}
		
		return H2DaoAbstractFactory.database;
	}
	
	public static UserDao getUserRepository() throws UserDaoException {
		try {
			return new UserH2Dao((H2Database) H2DaoAbstractFactory.getDatabase());
		} catch (DatabaseException e) {
			throw new UserDaoException(e.getMessage());
		}
	}
	
	public static DoctorDao getDoctorRepository() throws DoctorDaoException {
		try {
			return new DoctorH2Dao((H2Database) H2DaoAbstractFactory.getDatabase());
		} catch (DatabaseException e) {
			throw new DoctorDaoException(e.getMessage());
		}
	}
}
