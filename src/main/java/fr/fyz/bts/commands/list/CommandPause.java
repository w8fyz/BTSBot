package fr.fyz.bts.commands.list;

import java.time.Instant;

import fr.fyz.bts.commands.ITCommand;
import fr.fyz.bts.enums.RAINBOW;
import fr.fyz.bts.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandPause implements ITCommand {

	@Override
	public String getName() {
		return "pause";
	}

	@Override
	public String getDescription() {
		return "Permet de mettre en pause le lecteur de musique.";
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
				if (!event.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS)) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(RAINBOW.RED.getColor());
					eb.setTimestamp(Instant.now());
					eb.setDescription("Erreur.");
					eb.setTitle("Tu n'as pas la permission de faire ça !");
					event.replyEmbeds(eb.build()).setEphemeral(true).queue();
					return;
				}

				boolean isPaused = PlayerManager.getInstance().getMusicManager(event.getGuild()).audioPlayer.isPaused();

				if (!isPaused) {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(RAINBOW.YELLOW.getColor());
					eb.setTimestamp(Instant.now());
					eb.setDescription("Le lecteur a été mis en pause.");
					eb.setTitle("Lecteur mis en pause");
					event.replyEmbeds(eb.build()).setEphemeral(true).queue();
					PlayerManager.getInstance().getMusicManager(event.getGuild()).audioPlayer.setPaused(true);
				} else {
					EmbedBuilder eb = new EmbedBuilder();
					eb.setColor(RAINBOW.YELLOW.getColor());
					eb.setTimestamp(Instant.now());
					eb.setDescription("Le lecteur n'est plus en pause.");
					eb.setTitle("Pause du lecteur désactivée !");
					event.replyEmbeds(eb.build()).setEphemeral(true).queue();
					PlayerManager.getInstance().getMusicManager(event.getGuild()).audioPlayer.setPaused(false);
				}
			}
		};
	}

}
