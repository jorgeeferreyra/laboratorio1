package entrega.database;

import entrega.contracts.Database;
import entrega.exceptions.generic.DatabaseException;

public class H2Database extends Database {
	public H2Database(String url, String user, String password) throws DatabaseException {
		super(url, user, password);
		
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
