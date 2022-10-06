package fr.fyz.bts.commands.list;

import java.time.Instant;

import fr.fyz.bts.commands.CommandManager;
import fr.fyz.bts.commands.ITCommand;
import fr.fyz.bts.enums.RAINBOW;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandHelp implements ITCommand {

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "Permet de visualiser l'aide de ce bot";
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
				String help = "";
				
				for(ITCommand cmd : CommandManager.getCommands()) {
					help+="``"+cmd.getName()+" - "+cmd.getDescription()+"``\n";
				}
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(RAINBOW.YELLOW.getColor());
				eb.setTimestamp(Instant.now());
				eb.setDescription(help);
				eb.setTitle("Aide du bot");
				event.replyEmbeds(eb.build()).setEphemeral(true).queue();
			}
		};
	}

}
