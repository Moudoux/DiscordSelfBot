package me.alexander.discordbot;

import me.alexander.discordbot.SelfBot.SelfBot;

public class Main {

	public static SelfBot bot;

	public static void main(final String[] args) {
		Main.bot = new SelfBot("");
		Main.bot.setup();
		while (true) {
			;
		}
	}

}
