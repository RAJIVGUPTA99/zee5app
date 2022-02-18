package com.learning.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.dto.Login;
import com.learning.dto.User;
import com.learning.exception.IdNotFoundException;
import com.learning.repository.LoginRepository;
import com.learning.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginRepository loginRepository;
	
	//Insert a new record in the table
	@Override
	public String addCredentials(Login login) {
		// TODO Auto-generated method stub
		Login login2 = loginRepository.save(login);
		if (login2 != null) {
			return "success";
		} else {
			return "fail";
		}
	}
    
	//Delete the record by id
	@Override
	public String deleteCredentials(String email) {
		// TODO Auto-generated method stub
		Optional<Login> optional;
		try {
			optional = loginRepository.findById(email);
			if(optional.isEmpty()) {
				throw new IdNotFoundException("record not found");
			}
			else {
				loginRepository.deleteById(email);
				return "login record deleted";
			}
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	}
    
	//Updating the existing record
	@Override
	public String changePassword(String email, String password) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Optional<Login> login = this.loginRepository.findById(email);
		if(login.isEmpty())
			throw new IdNotFoundException("invalid id");
		login.get().setPassword(password);
		return (this.loginRepository.save(login.get())!= null) ? "success":"fail";
	}

	@Override
	public String vaidateCredentials(Login login) {
		// TODO Auto-generated method stub
		Login login2 = new Login();
		User register2 = new User();
		
		if(login.getEmail()==register2.getEmail() && login.getPassword()==register2.getPassword()) {
			return "success";
		}
		else
			return "fail";
	}

}
