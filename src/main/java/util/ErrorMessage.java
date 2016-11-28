package main.java.util;

/**
 * The following is a singltone class for saving the error log and 
 * displaying that to the user
 * */

public class ErrorMessage {
	private static ErrorMessage instance = null;
	public static String message;

	protected ErrorMessage() {
		message = "";
	}
	
	public static ErrorMessage getInstance() {
		if (instance == null) {
			instance = new ErrorMessage();
		}
		return instance;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		ErrorMessage.message = message;
	}	
}
