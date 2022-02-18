package com.zee.zee5app.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.zee5app.payload.request.LoginRequest;
import com.zee.zee5app.payload.request.SignupRequest;
import com.zee.zee5app.payload.response.JwtResponse;
import com.zee.zee5app.payload.response.MessageResponse;
import com.zee.zee5app.repository.RoleRepository;
import com.zee.zee5app.repository.UserRepository;
import com.zee.zee5app.security.jwt.JwtUtils;
import com.zee.zee5app.security.services.UserDetailsImpl;
import com.zee.zee5app.dto.EROLE;
import com.zee.zee5app.dto.Login;
import com.zee.zee5app.dto.Role;
import com.zee.zee5app.dto.User;
import com.zee.zee5app.exception.AlreadyExistsException;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.service.LoginService;
import com.zee.zee5app.service.UserService;

@CrossOrigin("*")
@RestController // combination of @ResponseBody and @Controller
//REST API: Response wherever we have to share the response that method must be marked with @ResponseBody
//1000 methods ---> @ResponseBody 1000 times so o avoid this we use @RestController
@RequestMapping("/api/auth")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	LoginService loginService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		if (userRepository.existsByUserName(loginRequest.getUserName()) == false) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username not found!"));
		}

		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateToken(authentication);
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		
		List<String> roles = userDetailsImpl.getAuthorities().stream()
				.map(i->i.getAuthority()).collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
				userDetailsImpl.getId(), 
				userDetailsImpl.getUsername(), 
				userDetailsImpl.getEmail(), 
				roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
		if (userRepository.existsByUserName(signupRequest.getUserName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));

		}

		// User's Account
		User user = new User(signupRequest.getUserName(), signupRequest.getEmail(),
				passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getFirstName(),
				signupRequest.getLastName());

		Set<String> strRoles = signupRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByRoleName(EROLE.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: role not found"));
		} else {
			strRoles.forEach(e -> {
				switch (e) {
				case "admin":
					Role adminRole = roleRepository.findByRoleName(EROLE.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: role not found"));
					roles.add(adminRole);
					break;
				case "mod":
					Role modRole = roleRepository.findByRoleName(EROLE.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: role not found"));
					roles.add(modRole);
					break;

				default:
					Role userRole = roleRepository.findByRoleName(EROLE.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: role not found"));
					roles.add(userRole);
				}
			});

		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.status(201).body(new MessageResponse("User created successfully"));
	}

//	//add user info into register table
//	//info will be shared by the client in terms of JSON object
//	//now we need to read that JSON object
//	//this JSON object is available in requestbody
//	//we need to read that requestbody content
//	//transform JSON obj to java object ---> this will be done by @RequestBody
//	
//	//we need to inform when this method should be used so we should specify the endpoint
//	@PostMapping("/addUser")
//	//used ? so we can return any type
//	public ResponseEntity<?> addUser(@Valid @RequestBody User register) throws AlreadyExistsException {
//		
//		//1. It should store the received info in database
//		User result = userService.addUser(register);
//		System.out.println(result);
//		return ResponseEntity.status(201).body(result);
//		
//		}
//	//retrieve single record
//	@GetMapping("/{id}")
//	public ResponseEntity<?> getUserById(@PathVariable("id") String id) throws IdNotFoundException{
//		User result = userService.getUserById(id);
//		return ResponseEntity.ok(result);	
//		
//	}
//	
//	//retrieve all records
//	@GetMapping("/all")
////	@PreAuthorize(value = true)
////	@PostAuthorize(value = false)
//	public ResponseEntity<?> getAllUserDetails(){
//		Optional<List<User>> optional = userService.getAllUserDetails();
//		if(optional.isEmpty()) {
//			Map<String, String> map = new HashMap<>();
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("no record found"));
//		}
//		return ResponseEntity.ok(optional.get());	
//		
//	}
//	
//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<?> deleteUserById(@PathVariable("id") String id) throws IdNotFoundException, SQLException
//	{
//		String result = userService.deleteUserById(id);
//		Map<String, String> map = new HashMap<>();
//		map.put("message", "User deleted successfully");
//		return ResponseEntity.status(201).body(result);
//	}
//	
//	@PutMapping("/update/{id}")
//	public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody User register) throws IdNotFoundException
//	{
//		User result = userService.updateUser(id, register);
//		loginService.changePassword(result.getEmail(), result.getPassword());
//		return ResponseEntity.status(201).body(result);
//	}
//	


}
