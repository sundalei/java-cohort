package com.wileyedge.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ToDoControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToDoControllerExceptionHandler.class);

    private static final String CONSTRAINT_MESSAGE = "Could not save your item. "
            + "Please ensure it is valid and try again.";

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<Error> handleSqlException() {

        LOGGER.info("ToDoControllerExceptionHandler handleSqlException");
        Error error = new Error();
        error.setMessage(CONSTRAINT_MESSAGE);
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
