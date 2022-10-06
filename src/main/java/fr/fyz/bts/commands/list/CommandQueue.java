package fr.fyz.bts.commands.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import fr.fyz.bts.commands.ITCommand;
import fr.fyz.bts.enums.RAINBOW;
import fr.fyz.bts.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class CommandQueue implements ITCommand {

	@Override
	public String getName() {
		return "queue";
	}

	@Override
	public String getDescription() {
		return "Permet d'obtenir la liste des musiques qui vont être jouées";
	}

	@Override
	public OptionData[] getOptions() {
		return null;
	}
	
	public static List<AudioTrack> getPage(Guild guild, int page) {
		
		BlockingQueue<AudioTrack> queue = PlayerManager.getInstance()
		.getMusicManager(guild).scheduler.queue;
		
		ArrayList<AudioTrack> result = new ArrayList<>();
		Iterator<AudioTrack> ite = queue.iterator();
		page = page-1;
		int i = 0;
		while(ite.hasNext()) {
			if(i >= page*10 && i < (page*10+10) && (i-1 <= queue.size())) {
				result.add(ite.next());
			} else {
				ite.next();
			}
			i++;
		}
		return result;
	}

	@Override
	public CommandRunnable getRunnable() {
		return new CommandRunnable() {

			@Override
			public void onCommand(SlashCommandInteractionEvent event) {
				
				Button next = Button.primary("next", "Suivante");
				Button previous = Button.primary("previous", "Précédente");
				
				event.replyEmbeds(queueEmbed(event.getGuild(), 1).build()).addActionRow(previous, next).setEphemeral(true).queue();
			}
		};
	}
	
	public static EmbedBuilder queueEmbed(Guild guild, int page) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Liste d'attente | Page "+page);
		String songs = "";
		List<AudioTrack> queue = getPage(guild, page);
		if (queue.size() > 0) {
			Iterator<AudioTrack> ite = queue.iterator();
			int i = 0;
			while (ite.hasNext()) {
				AudioTrack track = ite.next();
				songs += ((page-1*10)+i)+" - ``" + track.getInfo().title + " - " + track.getInfo().author + " | "
						+ CommandSong.getFormatedDuration(track.getDuration()) + "``\n";
				i++;
			}
			eb.setDescription(songs);
		} else {
			eb.setDescription("Aucune musique");
		}
		eb.setFooter("Pour un total de " + PlayerManager.getInstance()
		.getMusicManager(guild).scheduler.queue.size() + " musiques.");
		eb.setColor(RAINBOW.YELLOW.getColor());
		return eb;
	}

}
