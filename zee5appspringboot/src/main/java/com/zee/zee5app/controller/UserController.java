package com.zee.zee5app.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.zee5app.dto.Register;

@RestController //combination of @ResponseBody and @Controller
//REST API: Response wherever we have to share the response that method must be marked with @ResponseBody
//1000 methods ---> @ResponseBody 1000 times so o avoid this we use @RestController
@RequestMapping("/api/users")
public class UserController {
	
	//add user info into register table
	//info will be shared by the client in terms of JSON object
	//now we need to read that JSON object
	//this JSON object is available in requestbody
	//we need to read that requestbody content
	//transform JSON obj to java object ---> this will be done by @RequestBody
	
	//we need to inform when this method should be used so we should specify the endpoint
	@PostMapping("/addUser")
	public String addUser(@RequestBody Register register) {
		return register.toString();
	}

}
