package hu.uni.eszterhazy.beadando.exceptions;

public class InvalidImeiException extends Throwable {
    public InvalidImeiException(String imei) {
        super(imei);
    }
}
