package fr.fyz.bts.music.lavaplayer;

import java.util.Iterator;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class GuildMusicManager {
	
	public final AudioPlayer audioPlayer;
	public final TrackScheduler scheduler;
	private final AudioPlayerSendHandler sendhandler;
	
	public GuildMusicManager(AudioPlayerManager manager) {
		this.audioPlayer = manager.createPlayer();
		this.scheduler = new TrackScheduler(this.audioPlayer);
		this.audioPlayer.addListener(this.scheduler);
		this.sendhandler = new AudioPlayerSendHandler(this.audioPlayer);
	}
	
	public long getDurationToLastSong() {
		long duration = 0;
		Iterator<AudioTrack> ite = this.scheduler.queue.iterator();
		while(ite.hasNext()) {
			AudioTrack track = ite.next();
			duration +=track.getDuration();
		}
		return duration;
	}
	
	public AudioPlayerSendHandler getSendHandler() {
		return sendhandler;
	}

}
