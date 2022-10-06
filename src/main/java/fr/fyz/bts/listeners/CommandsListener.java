package fr.fyz.bts.listeners;

import org.jetbrains.annotations.NotNull;

import fr.fyz.bts.commands.CommandManager;
import fr.fyz.bts.commands.ITCommand;
import fr.fyz.bts.commands.list.CommandQueue;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandsListener extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
		ITCommand cmd = CommandManager.getCommandByName(event.getName());

		if (cmd != null && cmd.getRunnable() != null) {
			cmd.getRunnable().onCommand(event);
		}

	}

	@Override
	public void onButtonInteraction(ButtonInteractionEvent event) {
		try {
			int page = Integer.valueOf(event.getMessage().getEmbeds().get(0).getTitle().split(" ")[4]);
			// event.deferEdit().queue();
			if (event.getButton().getId().equals("previous")) {
				if (((page - 1) * 10) > 1) {
					event.editMessageEmbeds(CommandQueue.queueEmbed(event.getGuild(), page - 1).build()).queue();
				}
			} else if (event.getButton().getId().equals("next")) {
				if (CommandQueue.getPage(event.getGuild(), page + 1).size() > 0) {
					event.editMessageEmbeds(CommandQueue.queueEmbed(event.getGuild(), page + 1).build()).queue();
				}
			}
		} catch (Exception e) {

		}
	}

}
