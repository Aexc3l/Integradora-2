package exception;

public class UnvalidPriceException extends Exception{
    private String message;

    public UnvalidPriceException(String message) {
        super(message);
        this.message = message;

    }

    public String getMessage() {
        return message;

    }
}
