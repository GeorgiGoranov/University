package businesslogic.exceptions;

public class InvalidDataBusinessLogicException extends RuntimeException {
    public InvalidDataBusinessLogicException(String message){
        super (message);
    }
    public InvalidDataBusinessLogicException(String message, Throwable cause) {
        super (message, cause);
    }
}
