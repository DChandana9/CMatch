package com.cricket.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cricket.data.models.User;
import com.cricket.data.models.UserModel;

@Service
public class LoginService implements UserDetailsService {
	@Autowired
	UserModel userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user=userRepo.findByEmail(username);
		user.orElseThrow(() -> new UsernameNotFoundException("Not found"));	
		return user.map(UserAuth::new).get();
		 
	}
}