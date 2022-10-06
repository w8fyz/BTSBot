package fr.fyz.bts.enums;

import java.awt.Color;
import java.util.Arrays;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public enum RAINBOW {
	
	RED(0, ":heart:", Color.decode("#DD2E44")),
	ORANGE(1, ":orange_heart:", Color.decode("#F4900C")),
	YELLOW(2, ":yellow_heart:", Color.decode("#FDCB59")),
	GREEN(3, ":green_heart:", Color.decode("#78B258")),
	BLUE(4, ":blue_heart:", Color.decode("#5CADEC")),
	PURPLE(5, ":purple_heart:", Color.decode("#AA90D5"));
	
	private int place;
	private String emoji;
	private Color color;
	
	RAINBOW(int place, String emoji, Color color) {
		this.place = place;
		this.emoji = emoji;
		this.color = color;
	}
	
	public static RAINBOW getNextEmote(RAINBOW actual) {
		return Arrays.stream(values()).filter(i -> i.place == actual.place+1).findAny().orElse(RAINBOW.RED);
	}

	public static RAINBOW getLastEmote(TextChannel txt) {
		try {
		Message msg = txt.getHistory().retrievePast(1).complete().get(0);
		if(msg == null || msg.getEmbeds().size() == 0) {
			return RAINBOW.PURPLE;
		} else {
			String title = msg.getEmbeds().get(0).getTitle();
			System.out.println("LAST EMOTE : "+title.split(" ")[2]);
			return RAINBOW.getRainbowByEmote(title.split(" ")[2]);
		}
		}
		catch(Exception e) {
			return RAINBOW.PURPLE;
		}
	}
	
	public static RAINBOW getRainbowByEmote(String string) {
		return Arrays.stream(values()).filter(i -> i.emoji.equalsIgnoreCase(string)).findAny().orElse(RAINBOW.PURPLE);
	}

	public int getPlace() {
		return place;
	}
	
	public String getEmoji() {
		return emoji;
	}
	
	public Color getColor() {
		return color;
	}
	

}
