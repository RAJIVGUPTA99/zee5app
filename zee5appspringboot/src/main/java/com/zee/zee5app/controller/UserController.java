package com.zee.zee5app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.zee5app.dto.Register;
import com.zee.zee5app.exception.AlreadyExistsException;
import com.zee.zee5app.service.UserService;

@RestController //combination of @ResponseBody and @Controller
//REST API: Response wherever we have to share the response that method must be marked with @ResponseBody
//1000 methods ---> @ResponseBody 1000 times so o avoid this we use @RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	//add user info into register table
	//info will be shared by the client in terms of JSON object
	//now we need to read that JSON object
	//this JSON object is available in requestbody
	//we need to read that requestbody content
	//transform JSON obj to java object ---> this will be done by @RequestBody
	
	//we need to inform when this method should be used so we should specify the endpoint
	@PostMapping("/addUser")
	//used ? so we can return any type
	public ResponseEntity<?> addUser(@RequestBody Register register) {
		
		//1. It should store the received info in database
		try {
		Register result = userService.addUser(register);
		return ResponseEntity.status(201).body(result);
		} catch (AlreadyExistsException e) {
			// TODO Auto-generated catch block
			//return json obj with message: record already exists
			//status: problem
			Map<String, String> hashMap = new HashMap<>();
			hashMap.put("message", "record already exists");
			return ResponseEntity.badRequest().body(hashMap);
		}
		
		//2. validation
		//3. return the crispy info to the client
		//4. a. customization in error response
		//4. b. declaration of custom exception
	}

}
