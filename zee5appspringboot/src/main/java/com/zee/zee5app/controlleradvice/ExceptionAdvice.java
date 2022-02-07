package com.zee.zee5app.controlleradvice;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.zee.zee5app.exception.AlreadyExistsException;
import com.zee.zee5app.exception.IdNotFoundException;

@ControllerAdvice
public class ExceptionAdvice {
	//this class should be used when user-defined exception is called throughout all the controllers
	
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<?> alreadyRecordExistsExceptionHandler(AlreadyExistsException e){
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "record already exists"+" "+e.getMessage());
		return ResponseEntity.badRequest().body(map);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exceptionHandler(Exception e){
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "unknown exception"+" "+e.getMessage());
		return ResponseEntity.badRequest().body(map);
		
	}
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<?> idNotFoundExceptionHandler(IdNotFoundException e){
		HashMap<String, String> map = new HashMap<>();
		map.put("message", e.getMessage());
		return ResponseEntity.badRequest().body(map);
	}

}
