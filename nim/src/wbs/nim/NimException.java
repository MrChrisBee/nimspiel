package wbs.nim;

public class NimException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NimException() {
		super();
	}

	public NimException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NimException(String message, Throwable cause) {
		super(message, cause);
	}

	public NimException(String message) {
		super(message);
	}

	public NimException(Throwable cause) {
		super(cause);
	}

}
