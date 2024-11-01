package br.ucsal.docshare.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.ucsal.docshare.util.exception.RecordNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<String> handleRecordNotFoundException(RecordNotFoundException ex) {
		
		JsonObject jsonError = new JsonObject();
		jsonError.addProperty("errorMessage", ex.getMessage());
		
		return new ResponseEntity<>(new Gson().toJson(jsonError), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
		
		JsonObject jsonError = new JsonObject();
		jsonError.addProperty("errorMessage", ex.getMessage());
		
		return new ResponseEntity<>(new Gson().toJson(jsonError), HttpStatus.BAD_REQUEST);
	}
	
}
