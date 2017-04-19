package me.alexander.discordbot.SelfBot;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;
import me.alexander.discordbot.SelfBot.Messages.EmbededMessage;

public class IMessageSelfBot {

	@SuppressWarnings("unused")
	private final DiscordAPI api;
	private final Message message;
	private final SelfBot bot;

	public IMessageSelfBot(final DiscordAPI api, final Message message, final SelfBot bot) {
		this.api = api;
		this.message = message;
		this.bot = bot;
	}

	public void execute() {
		if (message.getAuthor().equals(bot.getAPI().getYourself())) {
			ICommandSelfBot.doCmd(message.getContent(), message, bot, bot.getAPI().getYourself());
			EmbededMessage.embed(message, bot);
		}
	}

}
