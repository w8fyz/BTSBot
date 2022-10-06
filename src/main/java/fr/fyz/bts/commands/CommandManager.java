package fr.fyz.bts.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.fyz.bts.Main;
import fr.fyz.bts.commands.list.CommandClearQueue;
import fr.fyz.bts.commands.list.CommandForceSkip;
import fr.fyz.bts.commands.list.CommandHelp;
import fr.fyz.bts.commands.list.CommandNowPlaying;
import fr.fyz.bts.commands.list.CommandPause;
import fr.fyz.bts.commands.list.CommandPlay;
import fr.fyz.bts.commands.list.CommandQueue;
import fr.fyz.bts.commands.list.CommandSetup;
import fr.fyz.bts.commands.list.CommandSkip;
import fr.fyz.bts.commands.list.CommandSong;
import fr.fyz.bts.commands.list.CommandStop;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;

public class CommandManager {

	public static String getMagicBallChannel() {
		return "991495371991818290";
	}
	
	public static String getIDSecretChannel() {
		return "602669722668105729";
	}
	
	private static ArrayList<ITCommand> commands = new ArrayList<ITCommand>(Arrays.asList(new CommandHelp(), new CommandQueue(), new CommandClearQueue(), new CommandSkip(), new CommandForceSkip(), new CommandNowPlaying(), new CommandStop(), new CommandPause(), new CommandPlay(), /*new CommandPlural(),*/new CommandSetup(), new CommandSong()));

	public static List<ITCommand> getCommands() {
		return commands;
	}

	private static void delete(Command command) {
		//command.delete().complete();
		//System.out.println("Command "+command.getName()+" deleted");
	}
	
	public static void init() {
		Main.getJDA().retrieveCommands().complete().forEach(cm -> delete(cm));
		for (ITCommand cmd : getCommands()) {
			Main.log("Created command : " + cmd.getName());
			CommandCreateAction action = Main.getJDA().upsertCommand(cmd.getName(), cmd.getDescription());
			if (cmd.getOptions() != null) {
				action.addOptions(cmd.getOptions());
			}
			action.queue();
		}
	}

	public static ITCommand getCommandByName(String name) {
		return commands.stream().filter(cmd -> cmd.getName().equalsIgnoreCase(name)).findAny().orElse(null);
	}


}
