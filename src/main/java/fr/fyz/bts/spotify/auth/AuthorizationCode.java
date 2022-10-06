package fr.fyz.bts.spotify.auth;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;

import fr.fyz.bts.Main;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

public class AuthorizationCode {

  public static void refreshWith(String code) {
    try {
      final AuthorizationCodeCredentials authorizationCodeCredentials = Main.getSpotify().authorizationCode(code)
    		    .build().execute();
      Main.getSpotify().setAccessToken(authorizationCodeCredentials.getAccessToken());
      Main.getSpotify().setRefreshToken(authorizationCodeCredentials.getRefreshToken());

      System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}