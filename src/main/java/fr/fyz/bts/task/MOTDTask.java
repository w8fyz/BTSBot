package fr.fyz.bts.task;

import java.util.Random;
import java.util.TimerTask;

import fr.fyz.bts.Main;
import net.dv8tion.jda.api.entities.Activity;

public class MOTDTask extends TimerTask {

	private static String[] presences = { "Rocket League", "Minecraft", "Regarder un live", "Réviser SISR",
			"Réviser SLAM", "Utiliser Kali", "RootMe", "un CTF", "Configurer un Pare-Feu" };

	@Override
	public void run() {
		String motd = presences[new Random().nextInt(presences.length)];
		Main.log("MOTD Updated : " + motd);
		Main.getJDA().getPresence().setActivity(Activity.playing(motd));
	}

}
