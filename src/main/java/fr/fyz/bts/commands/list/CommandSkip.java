package fr.fyz.bts.commands.list;

import java.time.Instant;
import java.util.ArrayList;

import fr.fyz.bts.commands.ITCommand;
import fr.fyz.bts.enums.RAINBOW;
import fr.fyz.bts.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandSkip implements ITCommand {

	private static ArrayList<Member> vote_skip = new ArrayList<>();

	@Override
	public String getName() {
		return "skip";
	}

	@Override
	public String getDescription() {
		return "Permet de skip la musique actuelle";
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

				
				if (PlayerManager.getInstance().getMusicManager(event.getGuild()).audioPlayer
						.getPlayingTrack() != null) {

					if(!event.getMember().getVoiceState().inAudioChannel() || event.getMember().getVoiceState().getChannel() != event.getGuild().getSelfMember().getVoiceState().getChannel()) {
						EmbedBuilder eb = new EmbedBuilder();
						eb.setColor(RAINBOW.RED.getColor());
						eb.setTimestamp(Instant.now());
						eb.setDescription("Vous devez �tre dans le m�me salon vocal que le bot pour effectuer cette action.");
						eb.setTitle("Erreur");
						event.replyEmbeds(eb.build()).setEphemeral(true).queue();
						return;
					}
					
					if (vote_skip.size() < getEffectiveMemberCount(event.getGuild())) {
						if(vote_skip.contains(event.getMember())) {
							EmbedBuilder eb = new EmbedBuilder();
							eb.setColor(RAINBOW.YELLOW.getColor());
							eb.setTimestamp(Instant.now());
							eb.setDescription("Ton vote a déjà été pris en compte, résultat actuel : " + vote_skip.size()
									+ "/" + getEffectiveMemberCount(event.getGuild()));
							eb.setTitle("Erreur");
							event.replyEmbeds(eb.build()).setEphemeral(true).queue();
							return;
						}
						vote_skip.add(event.getMember());
						EmbedBuilder eb = new EmbedBuilder();
						eb.setColor(RAINBOW.YELLOW.getColor());
						eb.setTimestamp(Instant.now());
						eb.setDescription("Ajout d'une demande de skip de la musique actuelle : " + vote_skip.size()
								+ "/" + getEffectiveMemberCount(event.getGuild()));
						eb.setTitle("Voteskip");
						event.replyEmbeds(eb.build()).queue();
						return;
					}

					PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.nextTrack();

					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(RAINBOW.YELLOW.getColor());
					eb.setTimestamp(Instant.now());
					eb.setDescription("La musique actuelle a été skip, lancement de la prochaine.");
					eb.setTitle("Musique suivante lancée");
					event.replyEmbeds(eb.build()).queue();
				} else {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(RAINBOW.RED.getColor());
					eb.setTimestamp(Instant.now());
					eb.setDescription("Aucune musique n'est en lecture.");
					eb.setTitle("Erreur");
					event.replyEmbeds(eb.build()).setEphemeral(true).queue();
				}

			}
		};
	}
	
	public int getEffectiveMemberCount(Guild g) {
		return (int)((g.getSelfMember().getVoiceState().getChannel().getMembers().size()-1) / 2);
	}

	public static void clearVotes() {
		vote_skip.clear();
	}

}
