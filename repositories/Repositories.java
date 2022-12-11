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

public class Repositories {
	private static Database database;
	private static String connection = "jdbc:h2:D:\\Jorge\\Code\\facultad\\Laboratorio1\\db";
	private static String username = "sa";
	private static String password = "";

	private static Database getDatabase() throws DatabaseException {
		if (Repositories.database == null) {
			Repositories.database = new H2Database(Repositories.connection, Repositories.username, Repositories.password);
		}
		
		return Repositories.database;
	}
	
	public static UserDao getUserRepository() throws UserDaoException {
		try {
			return new UserH2Dao((H2Database) Repositories.getDatabase());
		} catch (DatabaseException e) {
			throw new UserDaoException(e.getMessage());
		}
	}
	
	public static DoctorDao getDoctorRepository() throws DoctorDaoException {
		try {
			return new DoctorH2Dao((H2Database) Repositories.getDatabase());
		} catch (DatabaseException e) {
			throw new DoctorDaoException(e.getMessage());
		}
	}
}
