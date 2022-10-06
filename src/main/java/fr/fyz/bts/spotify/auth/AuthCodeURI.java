package fr.fyz.bts.spotify.auth;

import java.net.URI;

import fr.fyz.bts.Main;

public class AuthCodeURI {

	public URI get() {
		final URI uri = Main.getSpotify().authorizationCodeUri().scope("user-read-currently-playing")
				.redirect_uri(URI.create("http://" + Main.IP_ADRESS + ":8080/connect")).build().execute();
		return uri;
	}
}