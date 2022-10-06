package fr.fyz.bts.music.lavaplayer;

import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import fr.fyz.bts.commands.list.CommandPlay;
import fr.fyz.bts.commands.list.CommandSong;
import fr.fyz.bts.enums.RAINBOW;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PlayerManager {

	private static PlayerManager INSTANCE;
	private final Map<Long, GuildMusicManager> musicManagers;
	private final AudioPlayerManager audioPlayerManager;

	public PlayerManager() {
		this.musicManagers = new HashMap<>();
		this.audioPlayerManager = new DefaultAudioPlayerManager();

		AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
		AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
	}

	public GuildMusicManager getMusicManager(Guild guild) {
		return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildid) -> {
			final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
			guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
			return guildMusicManager;
		});
	}

	public void loadAndPlay(SlashCommandInteractionEvent event, TextChannel textChannel, String trackURL,
			String search) {
		final GuildMusicManager musicManager = this.getMusicManager(textChannel.getGuild());

		this.audioPlayerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {

			@Override
			public void trackLoaded(AudioTrack track) {
				musicManager.scheduler.queue(track);

				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(RAINBOW.YELLOW.getColor());
				eb.setTimestamp(Instant.now());
				eb.setThumbnail("https://img.youtube.com/vi/" + track.getInfo().uri.split("=")[1] + "/0.jpg");
				eb.setAuthor("Musique ajoutée à la file d'attente");
				eb.setTitle(track.getInfo().title, track.getInfo().uri);
				eb.addField("Auteur", track.getInfo().author, true);
				eb.addField("Durée", CommandSong.getFormatedDuration(track.getDuration()), true);
				long duration = PlayerManager.getInstance().getMusicManager(event.getGuild())
						.getDurationToLastSong();
				if (duration > 0) {
					eb.addField("Temps avant lecture", CommandSong.getFormatedDuration(duration), true);
				}
				eb.setFooter("Position : #"
						+ (PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.queue.size()
								+ 1));
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				final List<AudioTrack> tracks = playlist.getTracks();
				if (!tracks.isEmpty()) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(RAINBOW.YELLOW.getColor());
					eb.setTimestamp(Instant.now());
					eb.setThumbnail(
							"https://img.youtube.com/vi/" + tracks.get(0).getInfo().uri.split("=")[1] + "/0.jpg");
					eb.setAuthor("Musique ajoutée à la file d'attente");
					eb.setTitle(tracks.get(0).getInfo().title, tracks.get(0).getInfo().uri);
					eb.addField("Auteur", tracks.get(0).getInfo().author, true);
					eb.addField("Durée", CommandSong.getFormatedDuration(tracks.get(0).getDuration()), true);
					long duration = PlayerManager.getInstance().getMusicManager(event.getGuild())
							.getDurationToLastSong();
					if (duration > 0) {
						eb.addField("Temps avant lecture", CommandSong.getFormatedDuration(duration), true);
					}
					eb.setFooter("Position : #"
							+ (PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.queue.size()
									+ 1));
					textChannel.sendMessageEmbeds(eb.build()).queue();
					if (CommandPlay.isUrl(trackURL)) {
						if (tracks.size() > 1) {
							textChannel.sendMessage("✨ **Et " + (tracks.size() - 1) + " autres musiques...**").queue();
						}
						Iterator<AudioTrack> ite = tracks.iterator();
						while (ite.hasNext()) {
							musicManager.scheduler.queue(ite.next());
						}
					} else {
						musicManager.scheduler.queue(tracks.get(0));
					}
				} else {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(RAINBOW.RED.getColor());
					eb.setTimestamp(Instant.now());
					eb.setTitle("Erreur");
					eb.setDescription("Aucun résultat n'a été trouvé.");
					textChannel.sendMessageEmbeds(eb.build()).queue();
				}
			}

			@Override
			public void noMatches() {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(RAINBOW.RED.getColor());
				eb.setTimestamp(Instant.now());
				eb.setTitle("Erreur");
				eb.setDescription("Aucun résultat n'a été trouvé.");
				textChannel.sendMessageEmbeds(eb.build()).queue();
			}

			@Override
			public void loadFailed(FriendlyException exception) {

				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(RAINBOW.RED.getColor());
				eb.setTimestamp(Instant.now());
				eb.setTitle("Erreur");
				eb.setDescription(
						"Une erreur est survenu, si cela se reproduit, merci de signaler ce problème.\nCode d'erreur : **"
								+ exception.getMessage() + "**");

				textChannel.sendMessageEmbeds(eb.build()).queue();
			}
		});
	}

	public static PlayerManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PlayerManager();
		}
		return INSTANCE;
	}
}
