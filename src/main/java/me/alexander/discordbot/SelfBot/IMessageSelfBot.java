package me.alexander.discordbot.SelfBot;

import de.btobastian.javacord.entities.message.Message;
import me.alexander.discordbot.SelfBot.Messages.EmbededMessage;

public class IMessageSelfBot {

	/**
	 * The command prefix used to trigger the bot
	 */
	private final String commandPrefix = "/";

	private final Message message;
	private final SelfBot bot;

	public IMessageSelfBot(final Message message, final SelfBot bot) {
		this.message = message;
		this.bot = bot;
	}

	/**
	 * Executes the message processor
	 */
	public void execute() {
		// Make sure the message is sent by the correct user
		if (message.getAuthor().equals(bot.getAPI().getYourself())) {
			// Handle command
			if (message.getContent().startsWith(commandPrefix)) {
				ICommandSelfBot.doCmd(message.getContent(), message, bot, bot.getAPI().getYourself());
			}
			// Handle embeds
			EmbededMessage.embed(message, bot);
		}
	}

}
