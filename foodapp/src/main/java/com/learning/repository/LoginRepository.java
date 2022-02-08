package com.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.dto.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, String> {
	
	boolean existsByEmailAndPassword(String email, String password);
	
}
