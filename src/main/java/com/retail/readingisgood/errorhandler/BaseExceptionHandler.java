package com.retail.readingisgood.errorhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceExistsException.class)
    public ResponseEntity<Object> handleInvalidOrderException(final ResourceExistsException exception,
                                                              final HttpServletRequest request) {

        BaseExceptionResponse error = new BaseExceptionResponse();
        error.setErrorMessage("Resource Exists");
        error.setErrorType("BusinessFault");
        if(exception.getMessage() != null){
            List<String> details = new ArrayList<>();
            details.add(exception.getMessage());
            error.setDetails(details);
        }
        error.setTimestamp(new Date());

        return new ResponseEntity(error, HttpStatus.FOUND);
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<Object> handleInvalidOrderException(final InvalidOrderException exception,
                                                         final HttpServletRequest request) {

        BaseExceptionResponse error = new BaseExceptionResponse();
        error.setErrorMessage(exception.getMessage());
        error.setErrorType("BusinessFault");
        error.setTimestamp(new Date());

        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(final ResourceNotFoundException exception,
                                                                               final HttpServletRequest request) {

        BaseExceptionResponse error = new BaseExceptionResponse();
        error.setErrorMessage(exception.getMessage());
        error.setErrorType("BusinessFault");
        error.setTimestamp(new Date());

        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(final Exception exception,
                                          final HttpServletRequest request) {

        BaseExceptionResponse error = new BaseExceptionResponse();
        error.setErrorMessage(exception.getMessage());
        error.setErrorType("SystemFault");
        error.setTimestamp(new Date());

        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }

        BaseExceptionResponse error = new BaseExceptionResponse();
        error.setErrorMessage("Validation Failed");
        error.setErrorType("SystemFault");
        error.setTimestamp(new Date());
        error.setDetails(details);

        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

}
