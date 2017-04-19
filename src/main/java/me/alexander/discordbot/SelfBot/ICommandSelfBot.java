package me.alexander.discordbot.SelfBot;

import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;

/**
 * Takes input and interpreters it
 *
 */
public class ICommandSelfBot {

	/**
	 * Executes the command processor
	 * 
	 * @param in
	 * @param message
	 * @param bot
	 * @param u
	 */
	public static void doCmd(final String in, final Message message, final SelfBot bot, final User u) {
		final String cmd = in.contains(" ") ? in.split(" ")[0] : in;
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
