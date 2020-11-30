package com.cricket.data.models;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserModel extends JpaRepository<User,Integer>{

	Optional<User> findByEmail(String email);
	
}