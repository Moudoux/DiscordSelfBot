package me.alexander.discordbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.alexander.discordbot.SelfBot.SelfBot;

public class Main {

	/**
	 * The version of this bot
	 */
	public static final String version = "0.4";

	/**
	 * SelfBot instance
	 */
	private static SelfBot bot;

	/**
	 * Logger instance
	 */
	private static Logger logger = LoggerFactory.getLogger(Main.class);

	/**
	 * Main function, provide your discord account token as a CLI argument
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(final String[] args) throws InterruptedException {
		if (args.length == 0) {
			Main.logger.error("Please provide your Discord account token as a command line argument.");
			return;
		}
		Main.logger.info("Starting SelfBot version " + version);
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
