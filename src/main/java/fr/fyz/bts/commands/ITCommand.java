package fr.fyz.bts.commands;

import fr.fyz.bts.commands.list.CommandRunnable;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public interface ITCommand {

	String getName();
	
	String getDescription();
	
	OptionData[] getOptions();
	
	CommandRunnable getRunnable();
	
}
