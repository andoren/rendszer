package hu.uni.eszterhazy.beadando.exceptions;

public class InvalidMobileTypeException extends Throwable {
    public InvalidMobileTypeException(String type) {
        super(type);
    }
}
