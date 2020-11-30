package com.cricket.data.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Matches")
public class Match {
	@Id
	@GeneratedValue
	private int Id;
	private String matchID;
	@ManyToOne
    @JoinColumn(name ="uid")
    private User user;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getMatchID() {
		return matchID;
	}
	public void setMatchID(String matchID) {
		this.matchID = matchID;
	}
	
}