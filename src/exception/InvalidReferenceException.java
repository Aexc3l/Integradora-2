package exception;

public class InvalidReferenceException extends Exception{
    private String message;

    public InvalidReferenceException(String message) {
        super(message);
        this.message = message;

    }

    public String getMessage() {
        return message;

    }
}