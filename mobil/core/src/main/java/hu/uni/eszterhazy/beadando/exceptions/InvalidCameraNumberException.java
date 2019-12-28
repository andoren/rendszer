package hu.uni.eszterhazy.beadando.exceptions;

public class InvalidCameraNumberException extends Throwable {
    public InvalidCameraNumberException(int cameranumber) {
        super(Integer.toString(cameranumber));
    }
}
