package com.zee.zee5app.service.Impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zee.zee5app.dto.Login;
import com.zee.zee5app.dto.User;
import com.zee.zee5app.exception.AlreadyExistsException;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidEmailException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidNameException;
import com.zee.zee5app.exception.InvalidPasswordException;
import com.zee.zee5app.repository.LoginRepository;
import com.zee.zee5app.repository.UserRepository;
import com.zee.zee5app.service.LoginService;
import com.zee.zee5app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	@Autowired
	private LoginService service;
	
	@Autowired
	private LoginRepository loginRepository;

	@Override
	@org.springframework.transaction.annotation.Transactional(rollbackFor = AlreadyExistsException.class)
	public User addUser(User register) throws AlreadyExistsException {
		// TODO Auto-generated method stub
		//make exception for the next line
		boolean status = repository.existsByEmailAndContactNumber(register.getEmail(), register.getContactNumber()) ;
		if(status) {
			throw new AlreadyExistsException("this record already exists");
		}
		User register2 = repository.save(register);
		if (register2 != null) {
			Login login = new Login(register.getEmail(), register.getPassword(),register2);
			if(loginRepository.existsByUserName(register.getEmail())) {
				throw new AlreadyExistsException("this record already exists");
			}
			String result = service.addCredentials(login);
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

	@Override
	public User updateUser(String id, User register) throws IdNotFoundException {
		// TODO Auto-generated method stub
		
		if(!this.repository.existsById(id))
			throw new IdNotFoundException("invalid id");
		
		return repository.save(register);
		//we dont write here coz update is automatically taken care of
	}

	@Override
	public User getUserById(String id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> optional =  repository.findById(id);
		if(optional.isEmpty()) {
			throw new IdNotFoundException("id does not exists");
		}
		else {
			return optional.get();
		}
	}

	@Override
	public User[] getAllUsers()
			throws InvalidIdLengthException, InvalidNameException, InvalidEmailException, InvalidPasswordException {
		// TODO Auto-generated method stub
		List<User> list = repository.findAll();
		User[] array = new User[list.size()];
		return list.toArray(array);
	}

	@Override
	public String deleteUserById(String id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		//cross check with findbyid
		//use optional here coz findbyid return optional type

			User optional;
			try {
				optional = this.getUserById(id);
				if(optional==null) {
					throw new IdNotFoundException("record not found");
				}
				else {
					repository.deleteById(id);
					return "register record deleted";
				}
			} catch (IdNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IdNotFoundException(e.getMessage());
			}
		
	}

	@Override
	public Optional<List<User>> getAllUserDetails() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(repository.findAll());
	}

}
