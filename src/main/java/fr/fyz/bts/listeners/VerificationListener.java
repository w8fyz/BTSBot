package fr.fyz.bts.listeners;

import java.util.HashMap;

import fr.fyz.bts.verification.DiscordVerification;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

public class VerificationListener extends ListenerAdapter {

	private static HashMap<Member, Long> cooldown_sending = new HashMap<Member, Long>();
	
	@Override
	public void onButtonInteraction(ButtonInteractionEvent event) {
		if(event.getButton().getId().equals("verification_btn")) {
			
			
			TextInput.Builder textInput = TextInput.create("email", "Adresse mail en @efrei.net",
					TextInputStyle.SHORT);
			textInput.setPlaceholder("john.doe@efrei.net");

			Modal modal = Modal.create("email_verification", "Vérification de l'adresse mail")
					.addActionRows(ActionRow.of(textInput.build())).build();
			event.replyModal(modal).queue();
		}
	}
	
	@Override
	public void onModalInteraction(ModalInteractionEvent event) {

		if (event.getModalId().equals("email_verification")) {
			String email = event.getValue("email").getAsString();

			if (!email.contains("@efrei.net")) {
				event.reply("L'adresse mail renseignée n'est pas une adresse mail d'Efrei !").setEphemeral(true).queue();
				return;
			}

			if(cooldown_sending.containsKey(event.getMember()) && System.currentTimeMillis()-cooldown_sending.get(event.getMember()) < 180000) {
				event.reply("Un mail a été envoyé il y a moins de 3 minutes, patiente un peu avant de faire une nouvelle demande.").setEphemeral(true).queue();
				return;
			}
			
			DiscordVerification.send(event.getUser().getId(), email);
			event.reply(
					"Parfait ! Un email t'a Ã©tÃ© envoyÃ© pour confirmer ton adresse mail. **N'oublie pas de vÃ©rifier les spams !**")
					.setEphemeral(true).queue();
			cooldown_sending.put(event.getMember(), System.currentTimeMillis());
		}

	}

}
