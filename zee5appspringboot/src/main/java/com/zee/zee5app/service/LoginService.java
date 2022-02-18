package com.zee.zee5app.service;

import com.zee.zee5app.dto.Login;

import java.util.List;
import java.util.Optional;

import com.zee.zee5app.dto.EROLE;

public interface LoginService {
	public String addCredentials(Login login);
	public String deleteCredentials(String userName);
	public String changePassword(String userName, String password);
	public String changeRole(String userName, EROLE role);
	public String vaidateCredentials(Login login);
	public Optional<List<Login>> getAllLoginDetails();
	

}
