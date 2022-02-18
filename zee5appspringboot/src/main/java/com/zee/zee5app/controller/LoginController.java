package com.zee.zee5app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.zee5app.dto.Login;
import com.zee.zee5app.payload.response.MessageResponse;
import com.zee.zee5app.service.LoginService;

@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllUser() {
		Optional<List<Login>> optional = loginService.getAllLoginDetails();
		if (optional.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("No record found"));
		return ResponseEntity.ok(optional.get());
	}

}