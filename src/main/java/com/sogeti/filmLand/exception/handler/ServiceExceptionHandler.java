package com.sogeti.filmLand.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.gson.Gson;

@RestControllerAdvice
public class ServiceExceptionHandler {

	@RequestMapping(produces = "application/json")
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<?> handleException(MissingRequestHeaderException ex) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.Series.CLIENT_ERROR, "Authorization Error",
				"Header value is not correct");
		return new ResponseEntity<String>(new Gson().toJson(errorMessage), HttpStatus.UNAUTHORIZED);
	}
	
}