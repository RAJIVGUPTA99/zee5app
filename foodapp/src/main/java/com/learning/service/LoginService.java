package com.learning.service;

import com.learning.dto.Login;
import com.learning.exceptions.IdNotFoundException;

public interface LoginService {
	
	public String addCredentials(Login login);
	public String deleteCredentials(String email);
	public String changePassword(String email, String password) throws IdNotFoundException;
	public String vaidateCredentials(Login login);

}
