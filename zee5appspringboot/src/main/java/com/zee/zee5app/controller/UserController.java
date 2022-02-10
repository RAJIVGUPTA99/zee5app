package com.zee.zee5app.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.zee5app.payload.response.MessageResponse;
import com.zee.zee5app.dto.Login;
import com.zee.zee5app.dto.User;
import com.zee.zee5app.exception.AlreadyExistsException;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.service.LoginService;
import com.zee.zee5app.service.UserService;

@RestController //combination of @ResponseBody and @Controller
//REST API: Response wherever we have to share the response that method must be marked with @ResponseBody
//1000 methods ---> @ResponseBody 1000 times so o avoid this we use @RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	LoginService loginService;
	
	//add user info into register table
	//info will be shared by the client in terms of JSON object
	//now we need to read that JSON object
	//this JSON object is available in requestbody
	//we need to read that requestbody content
	//transform JSON obj to java object ---> this will be done by @RequestBody
	
	//we need to inform when this method should be used so we should specify the endpoint
	@PostMapping("/addUser")
	//used ? so we can return any type
	public ResponseEntity<?> addUser(@Valid @RequestBody User register) throws AlreadyExistsException {
		
		//1. It should store the received info in database
		User result = userService.addUser(register);
		System.out.println(result);
		return ResponseEntity.status(201).body(result);
		
		}
	//retrieve single record
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") String id) throws IdNotFoundException{
		User result = userService.getUserById(id);
		return ResponseEntity.ok(result);	
		
	}
	
	//retrieve all records
	@GetMapping("/all")
//	@PreAuthorize(value = true)
//	@PostAuthorize(value = false)
	public ResponseEntity<?> getAllUserDetails(){
		Optional<List<User>> optional = userService.getAllUserDetails();
		if(optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("no record found"));
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
	public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody User register) throws IdNotFoundException
	{
		User result = userService.updateUser(id, register);
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
