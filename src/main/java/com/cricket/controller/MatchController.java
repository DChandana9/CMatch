package com.cricket.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cricket.auth.UserAuth;
import com.cricket.data.models.Match;
import com.cricket.data.models.User;
import com.cricket.services.MatchService;

@RestController
public class MatchController {

	@Autowired
	private MatchService matchService;

	@GetMapping("/GetMyMatches")
	public ResponseEntity getMyMatches() {
		HashMap<String, Map<Integer, String>> responseMap = new HashMap<>();

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<Integer, String> matchMap = new HashMap<>();

		if (principal instanceof UserDetails) {
			User loggedinUser = ((UserAuth) principal).getUser();
			List<Match> matches = matchService.findAllMatchByUser(loggedinUser);
			for (int i = 0; i < matches.size(); i++) {
				matchMap.put(i, matches.get(i).getMatchID());
			}
			responseMap.put("MATCHES", matchMap);
			return new ResponseEntity<>(responseMap, HttpStatus.OK);

		} else {
			HashMap<String, String> errorMap = new HashMap<>();

			errorMap.put("Error", "Cannot get user details");
			return new ResponseEntity<>(errorMap, HttpStatus.OK);

		}

	}

	@PutMapping(value = "/AddToMyMatches/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity addMatch(@PathVariable int id) {
		HashMap<String, String> responseMap = new HashMap<>();

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			User loggedinUser = ((UserAuth) principal).getUser();
			Match match = new Match();
			match.setMatchID(String.valueOf(id));
			match.setUser(loggedinUser);
			matchService.saveMatch(match);
			responseMap.put("Added", "New Match Added to your list");
		} else {

			responseMap.put("Error", "Cannot Add Match to list");

		}
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/deleteMatch/{id}")
	public ResponseEntity deleteRecommendation(@PathVariable int id) {
		HashMap<String, String> responseMap = new HashMap<>();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			User loggedinUser = ((UserAuth) principal).getUser();
			Match match=new Match();
			match.setMatchID(String.valueOf(id));
			match.setUser(loggedinUser);
			if(matchService.deleteMatchByMatchId(match))
			responseMap.put("Deleted", "Recommendation deleted");
			else
				responseMap.put("Error", "Invalid Match ID");	
		} else {
			responseMap.put("Error", "Recommendation cannot be deleted");
		}
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}


}
