package fr.fyz.bts.utils;

public class Credentials {
	
	public String emailPassword, spotify,spotifySecret, jwtToken,discordToken;

	public Credentials(String emailPassword, String spotify, String spotifySecret, String discordToken, String jwtToken) {
		this.spotify = spotify;
		this.emailPassword = emailPassword;
		this.jwtToken = jwtToken;
		this.spotifySecret = spotifySecret;
		this.discordToken = discordToken;
	}

}
