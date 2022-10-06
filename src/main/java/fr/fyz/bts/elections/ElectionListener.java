package fr.fyz.bts.elections;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ElectionListener extends ListenerAdapter {

	@Override
	public void onButtonInteraction(ButtonInteractionEvent event) {
		if(event.getButton().getId().startsWith("vote_")) {
			
			int id = Integer.valueOf(event.getButton().getId().split("_")[1]);
			Candidates candidate = Candidates.getCandidateByID(id);
			
			if(System.currentTimeMillis() > 1665165600000L) {
				event.reply("Le vote est déjà terminé.").setEphemeral(true).queue();
				return;
			}
			
			if(!ElectionManager.haveSameOption(event.getMember(), candidate)) {
				event.reply("Vous ne pouvez voter que pour votre option !").setEphemeral(true).queue();
				return;
			}
			if(ElectionManager.haveVoted(event.getMember().getId())) {
				event.reply("Votre vote a déjà été comptabilisé !").setEphemeral(true).queue();
				return;
			}
			ElectionManager.vote(event.getMember().getId(), candidate);
			event.reply("A voter ! 🥳").setEphemeral(true).queue();
			
		}
	}
	
}
