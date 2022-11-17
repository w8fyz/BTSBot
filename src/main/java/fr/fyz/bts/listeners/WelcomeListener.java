package fr.fyz.bts.listeners;

import fr.fyz.bts.Main;
import fr.fyz.bts.enums.RAINBOW;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class WelcomeListener extends ListenerAdapter {

	private static String WELCOME_CHANNEL_ID = "1022110083334803507";
	

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		TextChannel bvn = Main.getJDA().getTextChannelById(WELCOME_CHANNEL_ID);

		if(!bvn.getGuild().getId().equals(event.getGuild().getId())) return;
		
		RAINBOW color_set = RAINBOW.getNextEmote(RAINBOW.getLastEmote(bvn));

		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Bienvenue ! " + color_set.getEmoji());
		eb.setColor(color_set.getColor());
		eb.setDescription("Bienvenue à toi "+event.getMember().getAsMention()+" sur le serveur ! N'hésite pas à faire un tour dans le salon <#1021838481015709847> !");
		bvn.sendMessageEmbeds(eb.build()).queue();
	}

}
