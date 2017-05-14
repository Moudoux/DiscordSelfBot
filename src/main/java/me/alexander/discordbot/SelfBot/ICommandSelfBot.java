package me.alexander.discordbot.SelfBot;

import java.util.concurrent.ExecutionException;

import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import me.alexander.discordbot.SelfBot.Messages.EmbeddedMessage;

/**
 * Takes input and interprets it
 *
 */
public class ICommandSelfBot {

	private ICommandSelfBot() {
	}

	/**
	 * Executes the command processor
	 * 
	 * @param in
	 * @param message
	 * @param bot
	 * @param u
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void doCmd(final String in, final Message message, final SelfBot bot, final User u) throws InterruptedException, ExecutionException {
		final String cmd = in.contains(" ") ? in.split(" ")[0] : in;
		switch (cmd.toLowerCase()) {
		case "/shutdown":
			message.delete().get();
			bot.disconnect();
			break;
		case "/embed":
			message.delete();
			EmbeddedMessage.embed(message, bot);
			break;
		}
	}

}
