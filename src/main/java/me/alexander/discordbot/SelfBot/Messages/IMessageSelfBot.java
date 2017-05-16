package me.alexander.discordbot.SelfBot.Messages;

import java.util.concurrent.ExecutionException;

import de.btobastian.javacord.entities.message.Message;
import me.alexander.discordbot.SelfBot.ICommandSelfBot;
import me.alexander.discordbot.SelfBot.SelfBot;

public class IMessageSelfBot {

	/**
	 * The command prefix used to trigger the bot
	 */
	private final static String commandPrefix = "/";

	private IMessageSelfBot() {}

	/**
	 * Executes the message processor
	 * 
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void execute(final Message message, final SelfBot bot)
			throws InterruptedException, ExecutionException {
		// Make sure the message is sent by the correct user
		if (message.getAuthor().equals(bot.getAPI().getYourself())) {
			// Handle command
			if (message.getContent().startsWith(commandPrefix)) {
				ICommandSelfBot.doCmd(message.getContent(), message, bot, bot.getAPI().getYourself());
			}
		}
	}

}
