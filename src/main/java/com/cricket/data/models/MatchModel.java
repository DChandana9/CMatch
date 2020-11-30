package com.cricket.data.models;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface MatchModel extends JpaRepository<Match,Integer>{

	List<Match> findByUser(User user);

	
	
}