package entrega;

import entrega.dao.doctors.DoctorDao;
import entrega.dao.doctors.DoctorH2Dao;
import entrega.dao.users.UserDao;
import entrega.dao.users.UserH2Dao;
import entrega.database.Database;
import entrega.database.H2Database;
import entrega.exceptions.DatabaseException;
import entrega.exceptions.DaoException;

public class H2DaoFactory {
	private static Database database;
	private static String connection = "jdbc:h2:D:\\Jorge\\Code\\facultad\\Laboratorio1\\db";
	private static String username = "sa";
	private static String password = "";

	private static Database getDatabase() throws DatabaseException {
		if (H2DaoFactory.database == null) {
			H2DaoFactory.database = new H2Database(H2DaoFactory.connection, H2DaoFactory.username, H2DaoFactory.password);
		}
		
		return H2DaoFactory.database;
	}
	
	public static UserDao getUserDao() throws DaoException {
		try {
			return new UserH2Dao((H2Database) H2DaoFactory.getDatabase());
		} catch (DatabaseException e) {
			throw new DaoException(e.getMessage());
		}
	}
	
	public static DoctorDao getDoctorDao() throws DaoException {
		try {
			return new DoctorH2Dao((H2Database) H2DaoFactory.getDatabase());
		} catch (DatabaseException e) {
			throw new DaoException(e.getMessage());
		}
	}
}
