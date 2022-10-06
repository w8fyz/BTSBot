package fr.fyz.bts.commands.list;

import java.time.Instant;

import fr.fyz.bts.commands.ITCommand;
import fr.fyz.bts.enums.RAINBOW;
import fr.fyz.bts.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandClearQueue implements ITCommand{

	@Override
	public String getName() {
		return "clear";
	}

	@Override
	public String getDescription() {
		return "Permet de supprimer la file d'attente";
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
				PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.queue.clear();
				
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(RAINBOW.YELLOW.getColor());
				eb.setTimestamp(Instant.now());
				eb.setDescription("La file d'attente a été supprimée.");
				eb.setTitle("File d'attente supprimée");
				event.replyEmbeds(eb.build()).setEphemeral(true).queue();
			}
		};
	}

}
