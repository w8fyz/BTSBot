package fr.fyz.bts.elections;

public class Winner {
	
	private Candidates candidate;
	private int votes;
	
	public Winner(Candidates candidate, int votes) {
		this.candidate = candidate;
		this.votes = votes;
	}
	
	public Candidates getCandidate() {
		return candidate;
	}
	
	public int getVoteCount() {
		return votes;
	}

}
