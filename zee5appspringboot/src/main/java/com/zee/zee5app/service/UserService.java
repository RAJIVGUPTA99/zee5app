package com.zee.zee5app.service;

import java.util.Optional;

import com.zee.zee5app.dto.User;
import com.zee.zee5app.exception.AlreadyExistsException;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidEmailException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidNameException;
import com.zee.zee5app.exception.InvalidPasswordException;

import java.util.ArrayList;
import java.util.List;
import com.zee.zee5app.repository.UserRepository;


public interface UserService {
	public User addUser(User register) throws AlreadyExistsException;
	public User updateUser(String id, User register) throws IdNotFoundException;
	public User getUserById(String id) throws IdNotFoundException;
	public User[] getAllUsers() throws InvalidIdLengthException, InvalidNameException, InvalidEmailException, InvalidPasswordException;
	public String deleteUserById(String id) throws IdNotFoundException;
	public Optional<List<User>> getAllUserDetails();
}
