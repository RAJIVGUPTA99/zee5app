package com.zee.zee5app.service.Impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.Register;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidEmailException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidNameException;
import com.zee.zee5app.exception.InvalidPasswordException;
import com.zee.zee5app.repository.UserRepository;
import com.zee.zee5app.repository.Impl.UserRepositoryImpl;
import com.zee.zee5app.service.UserService;

public class UserServiceImpl implements UserService {

	
	private UserRepository repository = UserRepositoryImpl.getInstance() ;
	
	private UserServiceImpl() throws IOException {
		
	}
	
	private static UserService service;
	public static UserService getInstance() throws IOException{
		if(service==null)
			service = new UserServiceImpl();
		return service;
	}
	@Override
	public String addUser(Register register) {
		// TODO Auto-generated method stub
		return this.repository.addUser(register);
	}

	@Override
	public String updateUser(String id, Register register) throws IdNotFoundException {
		// TODO Auto-generated method stub
		return this.repository.updateUser(id, register);
	}

	@Override
	public Optional<Register> getUserById(String id) throws IdNotFoundException, InvalidIdLengthException, InvalidEmailException, InvalidPasswordException, InvalidNameException {
		// TODO Auto-generated method stub
		return this.repository.getUserById(id);
	}

	@Override
	public Register[] getAllUsers() {
		// TODO Auto-generated method stub
		return this.repository.getAllUsers();
	}

	@Override
	public String deleteUserById(String id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		return this.repository.deleteUserById(id);
	}
	@Override
	public List<Register> getAllUserDetails() {
		// TODO Auto-generated method stub
		return this.repository.getAllUserDetails();
	}
	
	

}
