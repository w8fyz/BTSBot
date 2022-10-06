package fr.fyz.bts.commands.list;


import java.awt.Color;

import fr.fyz.bts.commands.ITCommand;
import fr.fyz.bts.elections.Candidates;
import fr.fyz.bts.elections.ElectionManager;
import fr.fyz.bts.enums.RAINBOW;
import fr.fyz.bts.enums.Rules;
import fr.fyz.bts.listeners.RolesListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildMessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu.Builder;

public class CommandSetup implements ITCommand {

	@Override
	public String getName() {
		return "setup";
	}

	@Override
	public String getDescription() {
		return "Permet le setup des channels utilisés";
	}

	@Override
	public OptionData[] getOptions() {
		return new OptionData[] { new OptionData(OptionType.STRING, "setup", "Le setup a lancer", true),
				new OptionData(OptionType.CHANNEL, "channel", "Le channel du setup", true) };
	}

	@Override
	public CommandRunnable getRunnable() {
		return new CommandRunnable() {

			@Override
			public void onCommand(SlashCommandInteractionEvent event) {
				GuildMessageChannel txt = event.getOption("channel").getAsMessageChannel();
				String setup = event.getOption("setup").getAsString();

				if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
					event.reply("Tu n'as pas la permission").setEphemeral(true).queue();
					return;
				}

				event.reply("Lancement du setup...").setEphemeral(true).queue();
				switch (setup) {

				case "verification":
					EmbedBuilder eb0 = new EmbedBuilder();
					eb0.setTitle("👋 Bienvenue sur le discord !");
					eb0.setDescription(
							"Par mesure de sécurité, seule les personnes possédant une adresse mail @efrei.net peuvent rejoindre le discord. Vous devez donc rentrez votre email dans le champ suivant puis cliquer sur le lien de confirmation qui vous sera envoyé.");
					eb0.setColor(RAINBOW.YELLOW.getColor());


					Button btn = Button.primary("verification_btn", "Vérification");
					
					txt.sendMessageEmbeds(eb0.build()).setActionRow(btn).queue();
					break;

				case "roles":

					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Séléction de rôles - Jeux 🎮");
					eb.setDescription("Vous pouvez sélectionner à l'aide de la barre de sélection vos jeux préférés");
					eb.setColor(RAINBOW.ORANGE.getColor());

					Builder menu = SelectMenu.create("games");
					menu.setMinValues(0);
					for (Role r : RolesListener.getGamesRole()) {
						menu.addOption(r.getName(), r.getId());
					}
					menu.setMaxValues(menu.getOptions().size());
					txt.sendMessageEmbeds(eb.build()).setActionRow(menu.build()).queue();
					
					EmbedBuilder eb2 = new EmbedBuilder();
					eb2.setTitle("Séléction de rôles - Option 🎮");
					eb2.setDescription("Vous pouvez sélectionner à l'aide de la barre de sélection votre option de BTS SIO");
					eb2.setColor(RAINBOW.ORANGE.getColor());

					Builder menu2 = SelectMenu.create("options");
					menu2.setMinValues(1);
					menu2.setMaxValues(1);
					for (Role r : RolesListener.getOptionRole()) {
						menu2.addOption(r.getName(), r.getId());
					}
					txt.sendMessageEmbeds(eb2.build()).setActionRow(menu2.build()).queue();
					break;
				case "rules":
					EmbedBuilder eb3 = new EmbedBuilder();
					eb3.setTitle("Règles du Serveur");
					eb3.setDescription(
							"Bienvenue sur le discord des BTS SIO ! \nVoici les règles pour pouvoir profiter au mieux du serveur\n\n");
					for (Rules rule : Rules.values()) {
						eb3.addField(rule.geTitle(), rule.getDesc(), false);
					}
					eb3.setColor(RAINBOW.ORANGE.getColor());
					txt.sendMessageEmbeds(eb3.build()).queue();
					break;
				case "annonceGithub":
					txt.sendMessage("C'est trop la classe, merci Fyz 😎").queue();
					break;
				case "election_slam":
					EmbedBuilder eb4 = new EmbedBuilder();
					Button btn2;
					for(Candidates c : Candidates.getCandidates(false)) {
						eb4.setTitle("Candidat : "+c.getName());
						eb4.setColor(Color.blue);
						eb4.setThumbnail(c.getIMG());
						eb4.setDescription(c.getParagraph());
						btn2 = Button.primary("vote_"+c.getID(), "Voter pour cette personne");
						txt.sendMessageEmbeds(eb4.build()).setActionRow(btn2).queue();
					}
					break;
				case "election_sisr":
					EmbedBuilder eb5 = new EmbedBuilder();
					Button btn3;
					for(Candidates c : Candidates.getCandidates(true)) {
						eb5.setTitle("Candidat : "+c.getName());
						eb5.setColor(Color.blue);
						eb5.setThumbnail(c.getIMG());
						eb5.setDescription(c.getParagraph());
						btn3 = Button.primary("vote_"+c.getID(), "Voter pour cette personne");
						txt.sendMessageEmbeds(eb5.build()).setActionRow(btn3).queue();
					}
					break;
				case "election_result":
					ElectionManager.sendResults(txt);
					break;
				default:
					event.getChannel().sendMessage("Erreur, setup introuvable !").queue();
					break;
				}
			}
		};
	}

}
