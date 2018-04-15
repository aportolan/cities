package com.infinum.cities.exception;

public class CitiesException extends RuntimeException {

	private static final long serialVersionUID = 5158826229699901791L;

	public CitiesException() {
		super();
	}

	public CitiesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CitiesException(String message, Throwable cause) {
		super(message, cause);
	}

	public CitiesException(String message) {
		super(message);
	}

	public CitiesException(Throwable cause) {
		super(cause);
	}

}
