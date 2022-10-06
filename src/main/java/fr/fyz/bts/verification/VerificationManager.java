package fr.fyz.bts.verification;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import fr.fyz.bts.Main;

public class VerificationManager {
	
	private static Algorithm algorithm = Algorithm.HMAC256(Main.getCrendentials().jwtToken);
	private static JWTVerifier verifier = JWT.require(algorithm).build();
	
	public static String createToken(String userID) {
		try {
		    String token = JWT.create()
		        .withClaim("userID", userID)
		        .sign(algorithm);
		    return token;
		} catch (JWTCreationException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String verifyToken(String token) {
		try {
		    DecodedJWT jwt = verifier.verify(token);
		    return jwt.getClaim("userID").asString();
		} catch (Exception exception){
			exception.printStackTrace();
		    return null;
		}
	}

	public static String getLink(String token) {
		//return "http://"+Main.IP_ADRESS+":8080/auth?code="+token;
		return "http://unsafe.fyz.center:8080/auth?code="+token;
	}

}
