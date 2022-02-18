package com.zee.zee5app.repository;


import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zee.zee5app.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	//write the custom jpa method // we will not write any definition just only signature
	//its pre-defined in jpa
	//Boolean existsByEmail(String email);
	//Boolean existsByContactNumber(BigDecimal contactNumber);
	Boolean existsByEmailAndContactNumber(String email, BigInteger contactNumber);
	Boolean existsByUserName(String userName);
	Optional<User> findByUserName(String userName);
	Boolean existsByEmail(String email);
	
}
