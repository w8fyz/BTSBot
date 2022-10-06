package fr.fyz.bts.verification;

import fr.fyz.bts.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public class DiscordVerification {
	
	private static String VERIFIED_ROLE = "1021822768863510538";
	
	public static void send(String userID, String email) {
		try {
			String token = VerificationManager.createToken(userID);
			EmailManager.sendEmail(email, token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void verify(String userID) {
		User user = Main.getJDA().getUserById(userID);
		Guild guild = Main.getJDA().getGuildById("1021817762382888991");
		if(guild.isMember(user)) {
			guild.addRoleToMember(user, guild.getRoleById(VERIFIED_ROLE)).queue();
		}
	}

}
