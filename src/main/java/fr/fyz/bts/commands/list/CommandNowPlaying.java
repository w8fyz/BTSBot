package fr.fyz.bts.commands.list;

import java.time.Instant;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import fr.fyz.bts.commands.ITCommand;
import fr.fyz.bts.enums.RAINBOW;
import fr.fyz.bts.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandNowPlaying implements ITCommand{

	@Override
	public String getName() {
		return "nowplaying";
	}

	@Override
	public String getDescription() {
		return "Permet de connaitre la musique actuellement entrain d'être jouée";
	}

	@Override
	public OptionData[] getOptions() {
		return null;
	}

	@Override
	public CommandRunnable getRunnable() {
		return new CommandRunnable() {
			
			@Override
			public void onCommand(SlashCommandInteractionEvent event) {
				
				AudioTrack track = PlayerManager.getInstance().getMusicManager(event.getGuild()).audioPlayer.getPlayingTrack();
				if(track == null) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(RAINBOW.RED.getColor());
					eb.setTimestamp(Instant.now());
					eb.setTitle("Erreur");
					eb.setDescription("Aucune musique n'est actuellement entrain d'être jouée.");
					event.replyEmbeds(eb.build()).setEphemeral(true).queue();
					return;
				}
				
				
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(RAINBOW.YELLOW.getColor());
				eb.setTimestamp(Instant.now());
				eb.setThumbnail("https://img.youtube.com/vi/" + track.getInfo().uri.split("=")[1] + "/0.jpg");
				eb.setAuthor("Musique en cours de lecture ⏯️");
				eb.setTitle(track.getInfo().title, track.getInfo().uri);
				eb.addField("Auteur", track.getInfo().author, true);
				eb.addField("Duréee", CommandSong.getFormatedDuration(track.getPosition())+"/"+CommandSong.getFormatedDuration(track.getDuration()), true);
				event.replyEmbeds(eb.build()).setEphemeral(true).queue();
			}
		};
	}

}
