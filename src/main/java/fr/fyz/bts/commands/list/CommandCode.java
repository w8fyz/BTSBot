package fr.fyz.bts.commands.list;


import fr.fyz.bts.commands.ITCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandCode implements ITCommand {

	@Override
	public String getName() {
		return "code";
	}

	@Override
	public String getDescription() {
		return "Permet d'éxecuter du code et d'en récupérer le résultat";
	}

	@Override
	public OptionData[] getOptions() {
		return new OptionData[] { };
	}

	@Override
	public CommandRunnable getRunnable() {
		return new CommandRunnable() {

			@Override
			public void onCommand(SlashCommandInteractionEvent event) {

			}
		};
	}

}
