package fr.fyz.bts.elections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import fr.fyz.bts.utils.data.Data;
import net.dv8tion.jda.api.entities.GuildMessageChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class ElectionManager {
	
	public static void vote(String UUID, Candidates candidate) {
		Votes votes = (Votes) new Data(Votes.class).get("list");
		if(votes == null) {
			votes = new Votes(new HashMap<>());
		}
		votes.addVote(UUID, candidate);
		new Data(Votes.class).send("list", new Gson().toJson(votes));
	}
	
	private static Votes getVotes() {
		return (Votes) new Data(Votes.class).get("list");
	}
	
	public static boolean haveVoted(String UUID) {
		Votes votes = (Votes) new Data(Votes.class).get("list");
		if(votes != null && votes.getVoters().containsKey(UUID)) return true;
		return false;
	}
	
	public static boolean haveSameOption(Member member, Candidates candidate) {
		
		Role SISR = member.getGuild().getRoleById("1021827030842617976");
		Role SLAM = member.getGuild().getRoleById("1021828264869761044");
		
		if(member.getRoles().contains(SISR) && candidate.isSISR()) {
			return true;
		} else if(member.getRoles().contains(SLAM) && !candidate.isSISR()) {
			return true;
		}
		return false;
	}

	public static void sendResults(GuildMessageChannel txt) {
		Winner winner_sisr = ElectionManager.getWinner(true);
		Winner winner_slam = ElectionManager.getWinner(false);
		txt.sendMessage("Il est 20h, voici le résultat des élections des délégués pour cette année 2022 ! @everyone").queue();
		txt.sendMessage("Commençons donc avec les résultats en SISR...").queueAfter(3, TimeUnit.SECONDS);
		txt.sendMessage("Hmhm.. Intéressant").queueAfter(6, TimeUnit.SECONDS);
		txt.sendMessage("Je vois, je vois...").queueAfter(9, TimeUnit.SECONDS);
		txt.sendMessage("...").queueAfter(15, TimeUnit.SECONDS);
		txt.sendMessage("...").queueAfter(18, TimeUnit.SECONDS);
		txt.sendMessage("**Et le gagnant est "+winner_sisr.getCandidate().getName()+" avec "+winner_sisr.getVoteCount()+" ! Bravo !**").queueAfter(22, TimeUnit.SECONDS);
		for(Candidates candidate : Candidates.getCandidates(true)) {
			if(candidate != winner_sisr.getCandidate()) {
				txt.sendMessage("*"+candidate.getName()+" : "+ElectionManager.getVotes(candidate)+" votes*").queue();
			}
		}
		txt.sendMessage("Passons aux résultats en SLAM...").queueAfter(25, TimeUnit.SECONDS);
		txt.sendMessage("D'accord, d'accord...").queueAfter(28, TimeUnit.SECONDS);
		txt.sendMessage("Oh ! Innatendu !").queueAfter(31, TimeUnit.SECONDS);
		txt.sendMessage("...").queueAfter(37, TimeUnit.SECONDS);
		txt.sendMessage("...").queueAfter(40, TimeUnit.SECONDS);
		txt.sendMessage("**Et le gagnant est "+winner_slam.getCandidate().getName()+" avec "+winner_slam.getVoteCount()+" ! Bravo !**").queueAfter(44, TimeUnit.SECONDS);
		for(Candidates candidate : Candidates.getCandidates(false)) {
			if(candidate != winner_sisr.getCandidate()) {
				txt.sendMessage("*"+candidate.getName()+" : "+ElectionManager.getVotes(candidate)+" votes*").queue();
			}
		}
	}

	private static int getVotes(Candidates candidate) {
		return Collections.frequency(new ArrayList<Integer>(ElectionManager.getVotes().getVoters().values()), candidate.getID());
	}

	private static Winner getWinner(boolean isSISR) {
		
		Votes votes = ElectionManager.getVotes();
		Winner winner = null;
		for(Candidates candidate : Candidates.getCandidates(isSISR)) {
			int count = Collections.frequency(new ArrayList<Integer>(votes.getVoters().values()), candidate.getID());
			if(winner == null || winner.getVoteCount() < count) {
				winner = new Winner(candidate, count);
			}
		}
		return winner;
	}

}
