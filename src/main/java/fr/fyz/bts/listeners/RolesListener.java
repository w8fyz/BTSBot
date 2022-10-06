package fr.fyz.bts.listeners;

import java.awt.Color;
import java.util.List;

import fr.fyz.bts.Main;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;

public class RolesListener extends ListenerAdapter {

	public static List<Role> getGamesRole() {
		return Main.getJDA().getGuildById("1021817762382888991").getRoles().stream()
				.filter(r -> r.getColor() != null && r.getColor().equals(Color.decode("#e91e63"))).toList();
	}

	public static List<Role> getOptionRole() {
		return Main.getJDA().getGuildById("1021817762382888991").getRoles().stream()
				.filter(r -> r.getColor() != null && r.getColor().equals(Color.decode("#3e46e2"))).toList();
	}

	@Override
	public void onSelectMenuInteraction(SelectMenuInteractionEvent event) {
		if (event.getSelectMenu().getId().equals("games")) {
			getGamesRole().forEach(r -> {
				if (event.getMember().getRoles().contains(r)) {
					event.getGuild().removeRoleFromMember(event.getMember(), r).complete();
				}
			});

			for (SelectOption option : event.getSelectedOptions()) {
				event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(option.getValue()))
						.queue();
			}
			event.reply("Jeux actualisés ! ✨").setEphemeral(true).queue();
		} else if (event.getSelectMenu().getId().equals("options")) {
			getOptionRole().forEach(r -> {
				if (event.getMember().getRoles().contains(r)) {
					if (System.currentTimeMillis() < 1665165600000L) {
						event.reply("Erreur : Le changement d'option n'est pas autorisé pendant les élections !")
								.setEphemeral(true).queue();
						return;
					}
					event.getGuild().removeRoleFromMember(event.getMember(), r).complete();
				}
			});

			for (SelectOption option : event.getSelectedOptions()) {
				if (System.currentTimeMillis() < 1665165600000L) {
					event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(option.getValue()))
							.queue();
					return;
				}

			}
			event.reply("Option actualisée ! ✨").setEphemeral(true).queue();
		}
	}

}
