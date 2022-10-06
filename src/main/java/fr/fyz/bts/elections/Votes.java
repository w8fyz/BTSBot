package fr.fyz.bts.elections;

import java.util.HashMap;

public class Votes {
	
	private HashMap<String, Integer> voters;
	
	public Votes(HashMap<String, Integer> voters) {
		this.voters = voters;
	}
	
	public void addVote(String UUID, Candidates candidate) {
		voters.put(UUID, candidate.getID());
	}
	
	public HashMap<String, Integer> getVoters() {
		return voters;
	}

}
