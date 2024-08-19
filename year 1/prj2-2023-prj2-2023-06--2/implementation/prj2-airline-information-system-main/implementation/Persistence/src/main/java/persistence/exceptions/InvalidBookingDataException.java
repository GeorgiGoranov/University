package persistence.exceptions;

public class InvalidBookingDataException extends RuntimeException{
    public InvalidBookingDataException(String message){
        super (message);
    }
    public InvalidBookingDataException(String message, Throwable cause){
        super(message, cause);
    }
}
