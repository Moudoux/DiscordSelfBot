package me.alexander.discordbot.SelfBot;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;

public class IMessageSelfBot {

	@SuppressWarnings("unused")
	private final DiscordAPI api;
	private final Message message;
	private final SelfBot bot;

	public IMessageSelfBot(DiscordAPI api, Message message, SelfBot bot) {
		this.api = api;
		this.message = message;
		this.bot = bot;
	}

	public void execute() {
		if (message.getAuthor().equals(bot.getAPI().getYourself())) {
			ICommandSelfBot.doCmd(message.getContent(), message, bot, bot.getAPI().getYourself());
			ICommandSelfBot.embed(message);
		}
	}

}
