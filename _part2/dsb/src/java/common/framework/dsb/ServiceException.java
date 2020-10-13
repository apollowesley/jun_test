package common.framework.dsb;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(Throwable msg) {
		super(msg);
	}

}
