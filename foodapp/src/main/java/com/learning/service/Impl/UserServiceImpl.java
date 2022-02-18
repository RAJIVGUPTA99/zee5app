package com.learning.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.dto.Login;
import com.learning.dto.User;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.repository.LoginRepository;
import com.learning.repository.UserRepository;
import com.learning.service.LoginService;
import com.learning.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	LoginRepository loginRepository;
    
	//Insert a new record in the table
	@Override
	@org.springframework.transaction.annotation.Transactional(rollbackFor = AlreadyExistsException.class)
	public User addUser(User register) throws AlreadyExistsException {
		// TODO Auto-generated method stub
		boolean status = userRepository.existsByEmail(register.getEmail()) ;
		if(status) {
			throw new AlreadyExistsException("this record already exists");
		}
		User register2 = userRepository.save(register);
		if (register2 != null) {
			Login login = new Login(register.getEmail(), register.getPassword(),register2);
			if(loginRepository.existsByEmailAndPassword(register.getEmail(), register.getPassword())) {
				throw new AlreadyExistsException("this record already exists");
			}
			String result = loginService.addCredentials(login);
			if(result == "success") {
					//return "record added in register and login";
				return register2;
			}
			else {
				return null;
			}
		}	
		else {
			return null;
		}
	}
    
	//Updating the existing record
	@Override
	public User updateUser(Long id, User register) throws IdNotFoundException {
		// TODO Auto-generated method stub
		if(!this.userRepository.existsById(id))
			throw new IdNotFoundException("Sorry user with " + register.getId() + " not found");
		
		return userRepository.save(register);
	}
    
	//retrive a record by id
	@Override
	public User getUserById(Long id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		User register = new User();
		Optional<User> optional =  userRepository.findById(id);
		if(optional.isEmpty()) {
			throw new IdNotFoundException("Sorry user with " + register.getId() + " not found");
		}
		else {
			return optional.get();
		}
	}
    
	//Delete the record by id
	@Override
	public String deleteUserById(Long id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		User optional;
		User register = new User();
		try {
			optional = this.getUserById(id);
			if(optional==null) {
				throw new IdNotFoundException("Sorry user with " + register.getId() + " not found");
			}
			else {
				userRepository.deleteById(id);
				return "User deleted successfully";
			}
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IdNotFoundException(e.getMessage());
		}
	}
    
	//Retrieve all details
	@Override
	public Optional<List<User>> getAllUserDetails() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(userRepository.findAll());
	}

}
