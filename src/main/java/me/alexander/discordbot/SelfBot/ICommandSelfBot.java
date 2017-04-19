package me.alexander.discordbot.SelfBot;

import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;

public class ICommandSelfBot {

	public static void doCmd(final String in, final Message message, final SelfBot bot, final User u) {
		if (u != bot.getAPI().getYourself()) {
			return;
		}
		String cmd = in;
		if (cmd.contains(" ")) {
			cmd = cmd.split(" ")[0];
		}
		if (!cmd.startsWith("/")) {
			return;
		}
		switch (cmd.toLowerCase()) {
		case "/shutdown":
			message.delete();
			bot.disconnect();
			break;
		default:
			break;
		}
	}

}
