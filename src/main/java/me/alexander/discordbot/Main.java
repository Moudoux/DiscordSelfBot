package me.alexander.discordbot;

import me.alexander.discordbot.Logger.LogType;
import me.alexander.discordbot.SelfBot.SelfBot;

public class Main {

	private static SelfBot bot;
	private static Logger logger = new Logger();

	public static void main(final String[] args) {
		if (args.length == 0) {
			Main.logger.log("Please provide your Discord account token as a command line argument.", LogType.CRITICAL);
			System.exit(0);
		}
		Main.bot = new SelfBot(args[0]);
		Main.bot.setup();
		while (true) {
			;
		}
	}

	public static Logger getLogger() {
		return Main.logger;
	}

}
