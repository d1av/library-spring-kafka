package com.kafka.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class LibraryEventControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleRequestBody(
	    MethodArgumentNotValidException ex) {
	List<FieldError> errorList = ex.getBindingResult()
		.getFieldErrors();
	String errorMessage = errorList.stream()
		.map(fieldError -> fieldError.getField() + " - "
			+ fieldError.getDefaultMessage())
		.sorted().collect(Collectors.joining(", "));
	log.info("errorMessage : {}", errorMessage);
	return new ResponseEntity<>(errorMessage,
		HttpStatus.BAD_REQUEST);
    }

}
