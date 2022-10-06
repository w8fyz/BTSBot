package fr.fyz.bts.listeners;

import java.util.List;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CounterListener extends ListenerAdapter {

	private static String COUNT_CHANNEL_ID = "996186727817433158";

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getChannel().getType() == ChannelType.TEXT && event.getChannel().getId().equals(COUNT_CHANNEL_ID)) {

			try {
				long predicted = 0;

				List<Message> list_msg = event.getChannel().getHistory().retrievePast(2).complete();
				if (list_msg.size() > 1 && list_msg.get(1) != null) {
					predicted = Integer.valueOf(list_msg.get(1).getContentRaw());
				}
				
				if (Integer.valueOf(event.getMessage().getContentRaw()) != (predicted+1)) {
					event.getMessage().delete().queue();
				}
			} catch (Exception e) {
				event.getMessage().delete().queue();
			}
		}
	}

}
