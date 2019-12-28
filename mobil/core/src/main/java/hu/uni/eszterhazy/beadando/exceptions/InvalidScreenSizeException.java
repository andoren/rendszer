package hu.uni.eszterhazy.beadando.exceptions;

public class InvalidScreenSizeException extends Throwable {
    public InvalidScreenSizeException(double screensize) {
        super(Double.toString(screensize));
    }
}
