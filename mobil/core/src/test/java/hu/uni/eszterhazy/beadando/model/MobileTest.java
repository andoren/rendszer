package hu.uni.eszterhazy.beadando.model;

import hu.uni.eszterhazy.beadando.exceptions.InvalidCameraNumberException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidImeiException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidMobileTypeException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidScreenSizeException;
import org.junit.*;

import java.time.LocalDate;

public class MobileTest {

    @Test(expected = InvalidImeiException.class)
    public void InvalidImeiTest() throws InvalidImeiException, InvalidMobileTypeException, InvalidCameraNumberException, InvalidScreenSizeException {
        Mobile mobile = new Mobile(Manucafterer.Samsung, "Galaxy S6", 2, LocalDate.now(), "888888888888888", false, 4.5,"blue");
    }
    @Test(expected = InvalidMobileTypeException.class)
    public void InvalidMobileTypeTooLongTest() throws InvalidImeiException, InvalidMobileTypeException, InvalidCameraNumberException, InvalidScreenSizeException {
        Mobile mobile = new Mobile(Manucafterer.Samsung, "dasdasdadasdasdasdasdadasdasdasdadadasdadasdasdasdasdadasdasdasdasd", 2, LocalDate.now(), "868779036670818", false, 4.5,"blue");
    }
    @Test(expected = InvalidMobileTypeException.class)
    public void InvalidMobileTypeTooShortTest() throws InvalidImeiException, InvalidMobileTypeException, InvalidCameraNumberException, InvalidScreenSizeException {
        Mobile mobile = new Mobile(Manucafterer.Samsung, "", 2, LocalDate.now(), "868779036670818", false, 4.5,"blue");
    }
    @Test(expected = InvalidCameraNumberException.class)
    public void InvalidCameraNumberExceptionTooMuchTest() throws InvalidImeiException, InvalidMobileTypeException, InvalidCameraNumberException, InvalidScreenSizeException {
        Mobile mobile = new Mobile(Manucafterer.Samsung, "Galaxy S6", 100, LocalDate.now(), "868779036670818", false, 4.5,"blue");
    }
    @Test(expected = InvalidCameraNumberException.class)
    public void InvalidCameraNumberExceptionTooLowTest() throws InvalidImeiException, InvalidMobileTypeException, InvalidCameraNumberException, InvalidScreenSizeException {
        Mobile mobile = new Mobile(Manucafterer.Samsung, "Galaxy S6", -1, LocalDate.now(), "868779036670818", false, 4.5,"blue");
    }
    @Test(expected = InvalidScreenSizeException.class)
    public void InvalidDisplaySizeExceptionTooLargeTest() throws InvalidImeiException, InvalidMobileTypeException, InvalidCameraNumberException, InvalidScreenSizeException {
        Mobile mobile = new Mobile(Manucafterer.Samsung, "Galaxy S6", 2, LocalDate.now(), "868779036670818", false, 100.5,"blue");
    }
    @Test(expected = InvalidScreenSizeException.class)
    public void InvalidDisplaySizeExceptionTooshortTest() throws InvalidImeiException, InvalidMobileTypeException, InvalidCameraNumberException, InvalidScreenSizeException {
        Mobile mobile = new Mobile(Manucafterer.Samsung, "Galaxy S6", 2, LocalDate.now(), "868779036670818", false, -1,"blue");
    }

}
