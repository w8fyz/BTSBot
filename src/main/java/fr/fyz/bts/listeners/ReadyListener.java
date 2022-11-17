package fr.fyz.bts.listeners;

import java.sql.Date;
import java.time.Instant;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import fr.fyz.bts.Main;
import fr.fyz.bts.commands.CommandManager;
import fr.fyz.bts.music.lavaplayer.MusicCheckerTask;
import fr.fyz.bts.task.MOTDTask;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {

	@Override
	public void onReady(ReadyEvent event) {
		Main.log("BTS Bot Initialisé.");
		CommandManager.init();
		new Timer().schedule(new MOTDTask(), Date.from(Instant.now()), TimeUnit.MINUTES.toMillis(2));
		new Timer().schedule(new MusicCheckerTask(), Date.from(Instant.now()), TimeUnit.MINUTES.toMillis(2));
	}

}
