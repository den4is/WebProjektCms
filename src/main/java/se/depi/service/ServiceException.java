package se.depi.service;

public final class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 9222185596580851151L;

	public ServiceException(String message) {
		super(message);
	}
	
}
