package com.cricket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cricket.data.models.Match;
import com.cricket.data.models.MatchModel;
import com.cricket.data.models.User;

@Service
public class MatchService {
	@Autowired
	private MatchModel matchRepo;

	public boolean deleteMatchById(int id) {
		Match match = matchRepo.findById(id).orElse(null);
		if (match != null) {
			matchRepo.deleteById(id);
			return true;
		}
		return false;
	}

	public List<Match> findAllMatchByUser(User user) {
		return matchRepo.findByUser(user);
	}

	public boolean deleteMatchByMatchId(Match match) {
		List<Match> matches = findAllMatchByUser(match.getUser());
		for (int i = 0; i < matches.size(); i++) {
			if (match.getMatchID().equals(matches.get(i).getMatchID())) {
				matchRepo.delete(matches.get(i));
				return true;
			}
		}

		return false;
	}

	public Match saveMatch(Match match) {
		List<Match> matches = findAllMatchByUser(match.getUser());
		for (int i = 0; i < matches.size(); i++) {
			if (match.getMatchID().equals(matches.get(i).getMatchID())) {
				return matchRepo.save(match);

			}
		}

		return matchRepo.save(match);
	}
}
