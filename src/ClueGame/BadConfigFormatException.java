package clueGame;
/**
 * Exception class for bad game board or legend format
 * 
 * @author Alexander Chu
 * @author Joseph O'Brien
 *
 */
public class BadConfigFormatException extends Exception{

	public BadConfigFormatException() {
		super("Error reading file");
	}

	public BadConfigFormatException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	
	
}
