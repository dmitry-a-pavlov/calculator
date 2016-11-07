package calculator;

/**
 * Represent syntax exception for the calculator language
 * 
 * @author Dmitriy Pavlov
 */
public class SytaxErrorException extends RuntimeException {

	private static final long serialVersionUID = -7933952021484380151L;

	public SytaxErrorException(String message) {
		super(message);
	}

	public SytaxErrorException(String message, Throwable e) {
		super(message, e);
	}

}
