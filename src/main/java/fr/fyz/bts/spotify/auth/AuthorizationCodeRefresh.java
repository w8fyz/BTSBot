package fr.fyz.bts.spotify.auth;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;

import fr.fyz.bts.Main;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;

public class AuthorizationCodeRefresh {

  private static final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = Main.getSpotify().authorizationCodeRefresh()
    .build();

  public static void authorizationCodeRefresh_Sync() {
    try {
      final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

      Main.getSpotify().setAccessToken(authorizationCodeCredentials.getAccessToken());

      Main.log("New refresh request... Expire in : "+authorizationCodeCredentials.getExpiresIn());
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

}