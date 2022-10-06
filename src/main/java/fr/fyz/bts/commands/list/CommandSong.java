package fr.fyz.bts.commands.list;

import java.awt.Color;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import fr.fyz.bts.Main;
import fr.fyz.bts.commands.ITCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;

public class CommandSong implements ITCommand{

	@Override
	public String getName() {
		return "song";
	}

	@Override
	public String getDescription() {
		return "Permet d'afficher une interface pour les musiques spotify que vous souhaitez partager";
	}

	@Override
	public OptionData[] getOptions() {
		return new OptionData[] {new OptionData(OptionType.STRING, "musique", "Nom de la musique à rechercher sur Spotify", true)};
	}

	@Override
	public CommandRunnable getRunnable() {
		return new CommandRunnable() {
			
			@Override
			public void onCommand(SlashCommandInteractionEvent event) {
				
				OptionMapping option = event.getOption("musique");
				
				EmbedBuilder eb = new EmbedBuilder();
				
				if(option == null) {
					eb.setTitle("Commande mal formulée !");
					eb.setColor(Color.decode("#00ff1a"));
					eb.setDescription("Vous devez spécifier une musique à rechercher !");
					event.replyEmbeds(eb.build()).setEphemeral(true).queue();
					return;
				}
				
				
				try {
					Track[] tracks = Main.getSpotify().searchTracks(option.getAsString()).limit(1).build().execute().getItems();
					
					if(tracks.length == 0) {
						eb.setTitle("Aucune musique trouvée !");
						eb.setColor(Color.decode("#00ff1a"));
						eb.setDescription("Essayez de spécifier votre recherche !");
						event.replyEmbeds(eb.build()).setEphemeral(true).queue();
						return;
					}
					
					Track track = tracks[0];
					eb.setTitle(track.getName()+" - "+getArtists(track.getArtists()), track.getExternalUrls().get("spotify"));
					eb.setDescription("00:00 ▬▬▬▬▬▬▬▬▬▬ "+getFormatedDuration(track.getDurationMs())+"\r\n"
							+ " :repeat::arrow_backward::pause_button::arrow_forward::loud_sound:");
					eb.setThumbnail(track.getAlbum().getImages()[0].getUrl());
					eb.setColor(Color.decode("#00ff1a"));
					
					Button spotify_btn = Button.link(track.getExternalUrls().get("spotify"), Emoji.fromUnicode("🟢").getAsMention()+" Ouvrir dans spotify");
					event.replyEmbeds(eb.build()).addActionRow(Arrays.asList(spotify_btn)).queue();
				} catch (Exception e) {
					e.printStackTrace();
					eb.setTitle("Uh oh ! :(");
					eb.setColor(Color.red);
					eb.setDescription("Une erreur a été produite, si elle doit être signalée, en voici le code : "+e.getMessage());
					event.replyEmbeds(eb.build()).setEphemeral(true).queue();
				}
			}
		};
	}
	
	public static String getFormatedDuration(long duration) {
        long minutes= TimeUnit.MILLISECONDS.toMinutes(duration);
        long seconds = (TimeUnit.MILLISECONDS.toSeconds(duration)% 60);
        
        String mn = (minutes > 9) ? minutes+"" : "0"+minutes;
        String s = (seconds > 9) ? seconds+"" : "0"+seconds;
        return mn+":"+s;
	}
	
	private String getArtists(ArtistSimplified[] artists) {
		String result = "";
		
		for(ArtistSimplified artist : artists) {
			result+= artist.getName()+", ";
		}
		
		return result.substring(0, result.length()-2);
	}

}
