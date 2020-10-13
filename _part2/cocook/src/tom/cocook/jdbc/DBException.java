package tom.cocook.jdbc;

public class DBException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DBException() {
		super();
	}

	public DBException(String msg) {
		super(msg);
	}

	public DBException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DBException(Throwable throwable) {
		super(throwable);
	}

}
