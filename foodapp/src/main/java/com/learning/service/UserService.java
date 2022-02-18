package com.learning.service;

import java.util.List;
import java.util.Optional;

import com.learning.dto.User;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;


public interface UserService {
	
	public User addUser(User user) throws AlreadyExistsException;
	public User updateUser(Long id, User user) throws IdNotFoundException;
	public User getUserById(Long id) throws IdNotFoundException;
	public String deleteUserById(Long id) throws IdNotFoundException;
	public Optional<List<User>> getAllUserDetails();

}
