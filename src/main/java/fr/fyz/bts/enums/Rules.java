package fr.fyz.bts.enums;

public enum Rules {

	PAS_EMBROUILLE("💢 Embrouilles",
			"Ce serveur est fait pour rencontrer des gens, partager et s'amuser, donc pas d'embrouilles s'il vous plait."),
	RESPECT("🤝 Respect",
			"Le respect de chacun est une notion importante du serveur, vous devez respectez les choix et les avis de chacun pour que la bonne humeur sois présente."),
	SUGGESTIONS("💡 Suggestions",
			"N'hésitez pas à proposer des améliorations ! J'essayerais de les intégrer avec plaisir!"),
	PRONOMS("💭 Pronoms et identitées", "Le respect de l'identitée et tout ce qui concerne une personne est à respecter, l'homophobie, la transphobie, l'enbyphobie, le racisme et tout autre sorte de discrimination sont totalement interdites."),
	GUIDELINES("📏 Règlement de Discord", "Le règlement et les guidelines de discord sont à respecter https://discord.com/guidelines"),
	OTHER("🔨 Décisions de l'équipe", "Même si cela ne rentre pas directement dans le règlement, si votre comportement semble inadapté au Discord, l'équipe se réserve le droit de vous sanctionner ou de vous rappeler à l'ordre.");
	private String title, desc;

	private Rules(String title, String desc) {
		this.title = title;
		this.desc = desc;
	}

	public String geTitle() {
		return title;
	}

	public String getDesc() {
		return desc;
	}

}
