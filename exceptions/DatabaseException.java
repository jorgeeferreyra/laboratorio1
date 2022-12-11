package entrega.exceptions;

public class DatabaseException extends Exception {
	private static final long serialVersionUID = 1L;

	public DatabaseException(String message) {
		super(message);
	}

	public static DatabaseException isAlreadyConnected(String url) {
		return new DatabaseException("Ya existe una conexión a " + url);
	}

	public static DatabaseException isNotConnected() {
		return new DatabaseException("La base de datos no se encuentra conectada");
	}

	public static DatabaseException transactionAlreadyBegun() {
		return new DatabaseException("Ya existe una transaccion en curso");
	}

	public static DatabaseException transactionWasNotStarted() {
		return new DatabaseException("No hay ninguna transaccion en curso");
	}
}
