package com.learning.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.Login;
import com.learning.dto.Register;
import com.learning.exceptions.AlreadyExistsException;
import com.learning.exceptions.IdNotFoundException;
import com.learning.service.LoginService;
import com.learning.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	LoginService loginService;
	
	@PostMapping("/addUser")
	public ResponseEntity<?> addUser(@Valid @RequestBody Register register) throws AlreadyExistsException {
	
		Register result = userService.addUser(register);
		return ResponseEntity.status(201).body(result);
		
		}
	
	//retrieve single record
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") String id) throws IdNotFoundException{
		Register result = userService.getUserById(id);
		return ResponseEntity.ok(result);	
		
	}
	
	//retrieve all records
	@GetMapping("/all")
	public ResponseEntity<?> getAllUserDetails(){
		Optional<List<Register>> optional = userService.getAllUserDetails();
		if(optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		}
		return ResponseEntity.ok(optional.get());	
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable("id") String id) throws IdNotFoundException, SQLException
	{
		String result = userService.deleteUserById(id);
		Map<String, String> map = new HashMap<>();
		map.put("message", "User deleted successfully");
		return ResponseEntity.status(201).body(result);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody Register register) throws IdNotFoundException
	{
		Register result = userService.updateUser(id, register);
		loginService.changePassword(result.getEmail(), result.getPassword());
		return ResponseEntity.status(201).body(result);
	}
	
	@PostMapping("/authenticate/{id}")
	public ResponseEntity<?> authenticateUser(@RequestBody Login login){
		String result = loginService.vaidateCredentials(login);
		Map<String, String> map = new HashMap<>();
		map.put("message", "authencating");
		if(result.equals("success")) {
			return ResponseEntity.status(201).body(result);
        
		}
		return ResponseEntity.status(200).body(result);
	
	}
}
