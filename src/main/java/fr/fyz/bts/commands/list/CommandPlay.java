package fr.fyz.bts.commands.list;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

import fr.fyz.bts.commands.ITCommand;
import fr.fyz.bts.enums.RAINBOW;
import fr.fyz.bts.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.managers.AudioManager;

public class CommandPlay implements ITCommand{

	@Override
	public String getName() {
		return "play";
	}

	@Override
	public String getDescription() {
		return "Permet de jouer une musique provenant de Youtube (lien OU titre)";
	}

	@Override
	public OptionData[] getOptions() {
		return new OptionData[] {new OptionData(OptionType.STRING, "nom", "Le nom ou le lien de la musique", true)};
	}

	@Override
	public CommandRunnable getRunnable() {
		return new CommandRunnable() {
			
			@Override
			public void onCommand(SlashCommandInteractionEvent event) {
				
				
				Member member = event.getMember();
				
				if(!member.getVoiceState().inAudioChannel()) {
	                EmbedBuilder eb = new EmbedBuilder();
	                eb.setColor(RAINBOW.RED.getColor());
	                eb.setTimestamp(Instant.now());
	                eb.setTitle("Erreur");
	                eb.setDescription("Il faut être dans un salon vocal pour utiliser cette commande !");
	                event.replyEmbeds(eb.build()).setEphemeral(true).queue();
					return;
				}
				
				if(!event.getGuild().getSelfMember().getVoiceState().inAudioChannel()) {
					final AudioManager audioManager = event.getGuild().getAudioManager();
					final VoiceChannel memberChannel = (VoiceChannel)member.getVoiceState().getChannel();
					
					audioManager.openAudioConnection(memberChannel);
				}
				
				String link = event.getOption("nom").getAsString();
				if(!isUrl(link)) {
					link = "ytsearch:"+link+" audio";
				}
				event.reply("**🤔 Recherche en cours...**").queue();
				PlayerManager.getInstance().loadAndPlay(event, event.getTextChannel(), link, event.getOption("nom").getAsString());
			}
			
		};
	}
	
	public static boolean isUrl(String url) {
		try {
			new URI(url);
			return true;
		}catch(URISyntaxException e) {
			return false;
		}
	}

}
