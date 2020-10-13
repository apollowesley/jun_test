package tom.cocook.core;

public class CocookException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public CocookException() {
		super();
	}

	public CocookException(String msg) {
		super(msg);
	}

	public CocookException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CocookException(Throwable throwable) {
		super(throwable);
	}

}
