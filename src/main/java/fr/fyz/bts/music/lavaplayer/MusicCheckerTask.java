package fr.fyz.bts.music.lavaplayer;

import java.util.TimerTask;

import fr.fyz.bts.Main;
import net.dv8tion.jda.api.entities.Guild;

public class MusicCheckerTask extends TimerTask {

	@Override
	public void run() {
		Guild g = Main.getJDA().getGuildById("1021817762382888991");
		if(PlayerManager.getInstance().getMusicManager(g).audioPlayer.getPlayingTrack() == null && PlayerManager.getInstance().getMusicManager(g).scheduler.queue.size() == 0 && g.getSelfMember().getVoiceState().inAudioChannel()) {
			g.getAudioManager().closeAudioConnection();
		}
	}

}
