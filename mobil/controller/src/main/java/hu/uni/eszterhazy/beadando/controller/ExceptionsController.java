package hu.uni.eszterhazy.beadando.controller;

import hu.uni.eszterhazy.beadando.model.Manucafterer;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileAlreadyExistsException;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.io.IOException;


@ControllerAdvice
public class ExceptionsController {
    @ExceptionHandler(IOException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String serverIOError(IOException e){
        return "The server is in deep shit: "+e.getMessage();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String jsonmappingException(HttpMessageNotReadableException ex) {
        Throwable c = ex.getCause().getCause();
        return c.getClass().getSimpleName() + ": " + c.getMessage();

    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public String unsupportedMediaType(HttpMediaTypeNotSupportedException e) {
        return "Invalid media type. Please use one of the followings: " + e.getSupportedMediaTypes() + MediaType.APPLICATION_JSON_VALUE;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String invalidRequestMethod(HttpRequestMethodNotSupportedException e) {
        return "This method is not allowed: " + e.getMethod() + ", use one of these: " + e.getSupportedHttpMethods();
    }

    @ExceptionHandler(MobileAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.IM_USED)
    public String mobileAlreadyExits(MobileAlreadyExistsException e) {
        return "This imei number is registered in the database: " + e.getMessage();
    }

    @ExceptionHandler(MobileNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.IM_USED)
    public String mobileNotFound(MobileNotFoundException e) {
        return "Mobile was not found with this imei number: " + e.getMessage();
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.IM_USED)
    public String invalidManfucaterer(MethodArgumentTypeMismatchException e) {
        String errorMessage = "Sorry the typed manufacterer is invalid. Valid manufacterers are: ";
        for(Manucafterer manuf : Manucafterer.values()) errorMessage +=  manuf.toString() + ", ";
        errorMessage = errorMessage.substring(0,errorMessage.length()-2);
        return errorMessage;
    }

}
