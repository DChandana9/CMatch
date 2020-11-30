package com.cricket.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cricket.data.models.User;
import com.cricket.data.models.UserModel;
@Service
public class UserService {
	@Autowired
	private UserModel userRepo;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	public User createUser(User user) {
		user.setPassword("{bcrypt}"+bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	public User findUserById(int id) {
		return userRepo.findById(id).orElse(null);
	}
	public Optional<User> findUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	
	public boolean updateUser(User user) {
		User existingUser=userRepo.findById(user.getId()).orElse(null);
		
			existingUser.setPassword("{bcrypt}"+bCryptPasswordEncoder.encode(user.getPassword()));
			userRepo.save(existingUser);
			return true;
		
	
	}
}
