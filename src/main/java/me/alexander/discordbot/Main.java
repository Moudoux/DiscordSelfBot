package me.alexander.discordbot;

import me.alexander.discordbot.Logger.LogType;
import me.alexander.discordbot.SelfBot.SelfBot;

public class Main {

	/**
	 * SelfBot instance
	 */
	private static SelfBot bot;

	/**
	 * Logger instance
	 */
	private static Logger logger = new Logger();

	/**
	 * Main function, provide your discord account token as a CLI argument
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(final String[] args) throws InterruptedException {
		if (args.length == 0) {
			Main.logger.log("Please provide your Discord account token as a command line argument.", LogType.CRITICAL);
			return;
		}
		Main.bot = new SelfBot(args[0]);
		Main.bot.setup();
	}

	/**
	 * Retrieves the logger
	 * 
	 * @return Logger
	 */
	public static Logger getLogger() {
		return Main.logger;
	}

}
