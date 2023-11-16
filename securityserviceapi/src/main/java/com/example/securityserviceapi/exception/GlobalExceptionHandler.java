package com.example.securityserviceapi.exception;

import com.example.securityserviceapi.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Qualifier("messageSource")
    @Autowired
    private MessageSource msgSource;


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handlerExistingException(NotFoundException ex) {
        LOGGER.debug("An exception occurred, which will cause a {} response", ex.getMessage() + " " + Arrays.toString(ex.getStackTrace()), ex);
        LOGGER.info("An exception occurred, which will cause a {} response", ex.getMessage() + " " + Arrays.toString(ex.getStackTrace()), ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistsException.class)
    public ResponseEntity<Object> handlerExistingException(ExistsException ex) {
        LOGGER.debug("An exception occurred, which will cause a {} response", ex.getMessage() + " " + Arrays.toString(ex.getStackTrace()), ex);
        LOGGER.info("An exception occurred, which will cause a {} response", ex.getMessage() + " " + Arrays.toString(ex.getStackTrace()), ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> processValidateError(MethodArgumentNotValidException ex) {
        LOGGER.debug("An exception occurred, which will cause a {} response", ex.getMessage() + " " + ex.getStackTrace(), ex);
        LOGGER.info("An exception occurred, which will cause a {} response", ex.getMessage() + " " + ex.getStackTrace(), ex);

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        StringBuilder messageBuilder = new StringBuilder();

        for (FieldError error : fieldErrors) {
            String temp = processFieldError(error);
            messageBuilder.append(temp).append("\n");
        }

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, messageBuilder.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    private String processFieldError(FieldError error) {
        String msg = "";
        if (error != null) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            try {
                msg = msgSource.getMessage(Objects.requireNonNull(error.getDefaultMessage()), null, currentLocale);
            } catch (Exception e) {
                msg = error.getDefaultMessage();
            }
        }
        return msg;
    }
}
