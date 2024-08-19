package persistence.exceptions;

public class InvalidPersonalDataException extends RuntimeException {
    public InvalidPersonalDataException(String message){
        super(message);
    }
    public InvalidPersonalDataException(String message, Throwable cause){
        super(message, cause);
    }
}
