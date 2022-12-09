package entrega.repositories;

import entrega.contracts.Database;
import entrega.database.H2Database;
import entrega.exceptions.generic.DatabaseException;
import entrega.exceptions.repositories.DoctorRepositoryException;
import entrega.exceptions.repositories.UserRepositoryException;
import entrega.repositories.doctors.DoctorRepository;
import entrega.repositories.doctors.H2DoctorRepository;
import entrega.repositories.users.H2UserRepository;
import entrega.repositories.users.UserRepository;

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
	
	public static UserRepository getUserRepository() throws UserRepositoryException {
		try {
			return new H2UserRepository((H2Database) Repositories.getDatabase());
		} catch (DatabaseException e) {
			throw new UserRepositoryException(e.getMessage());
		}
	}
	
	public static DoctorRepository getDoctorRepository() throws DoctorRepositoryException {
		try {
			return new H2DoctorRepository((H2Database) Repositories.getDatabase());
		} catch (DatabaseException e) {
			throw new DoctorRepositoryException(e.getMessage());
		}
	}
}
