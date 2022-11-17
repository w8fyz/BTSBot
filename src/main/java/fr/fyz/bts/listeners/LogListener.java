package fr.fyz.bts.listeners;

import java.awt.Color;
import java.time.Instant;

import fr.fyz.bts.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LogListener extends ListenerAdapter{
	
	private static String MESSAGES_LOG = "1021894506867601469";
	private static String VOICE_LOG = "1022113028663152660";
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getAuthor().isBot()) {
			return;
		}
		TextChannel txt = Main.getJDA().getTextChannelById(MESSAGES_LOG);
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(event.getAuthor().getAsTag(), event.getMessage().getJumpUrl());
		eb.setDescription(event.getMessage().getContentRaw());
		eb.addField("Channel", event.getChannel().getAsMention(), false);
		if(event.getMessage().getAttachments().size() > 0) eb.addBlankField(false);
		for(Attachment attach : event.getMessage().getAttachments()) {
			eb.addField(attach.getFileName(), attach.getUrl(), false);
		}
		eb.setColor(Color.cyan);
		eb.setTimestamp(Instant.now());
		eb.setFooter("Author ID : "+event.getAuthor().getId(), event.getAuthor().getAvatarUrl());
		txt.sendMessageEmbeds(eb.build()).queue();
	}
	
	@Override
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
		TextChannel txt = Main.getJDA().getTextChannelById(VOICE_LOG);
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(event.getMember().getUser().getAsTag());
		eb.setDescription("A rejoins le channel "+event.getChannelJoined().getAsMention());
		eb.setColor(Color.green);
		eb.setTimestamp(Instant.now());
		eb.setFooter("Author ID : "+event.getMember().getId(), event.getMember().getAvatarUrl());
		txt.sendMessageEmbeds(eb.build()).queue();
	}
	
	@Override
	public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
		TextChannel txt = Main.getJDA().getTextChannelById(VOICE_LOG);
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(event.getMember().getUser().getAsTag());
		eb.setDescription("A quitté le channel "+event.getChannelLeft().getAsMention());
		eb.setColor(Color.red);
		eb.setTimestamp(Instant.now());
		eb.setFooter("Author ID : "+event.getMember().getId(), event.getMember().getAvatarUrl());
		txt.sendMessageEmbeds(eb.build()).queue();
	}
	
	@Override
	public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
		TextChannel txt = Main.getJDA().getTextChannelById(VOICE_LOG);
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(event.getMember().getUser().getAsTag());
		eb.setDescription("A été move du channel "+event.getChannelLeft().getAsMention()+" au channel "+event.getChannelJoined().getAsMention());
		eb.setColor(Color.yellow);
		eb.setTimestamp(Instant.now());
		eb.setFooter("Author ID : "+event.getMember().getId(), event.getMember().getAvatarUrl());
		txt.sendMessageEmbeds(eb.build()).queue();
	}

}
