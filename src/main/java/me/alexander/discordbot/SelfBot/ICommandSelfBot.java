package me.alexander.discordbot.SelfBot;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.embed.EmbedBuilder;
import de.btobastian.javacord.entities.permissions.PermissionType;
import me.alexander.discordbot.SelfBot.Messages.AutoDeleteMessage;
import me.alexander.discordbot.SelfBot.Messages.EmbeddedMessage;

/**
 * Takes input and interprets it
 *
 */
public class ICommandSelfBot {

	private ICommandSelfBot() {}

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
	public static void doCmd(final String in, final Message message, final SelfBot bot, final User u)
			throws InterruptedException, ExecutionException {
		final String cmd = in.contains(" ") ? in.split(" ")[0] : in;
		final String args = in.contains(" ") ? in.replace(in.split(" ")[0] + " ", "") : "";
		switch (cmd.toLowerCase()) {
		case "/shutdown":
			message.delete().get();
			bot.disconnect();
			break;
		case "/ban":
			if (args.equals("")) {
				new AutoDeleteMessage("Invalid argument", 2, message.getChannelReceiver());
				break;
			}
			User user = Utils.getUser(args, message.getChannelReceiver().getServer(), bot);
			if (user == null) {
				new AutoDeleteMessage("User not found", 2, message.getChannelReceiver());
				break;
			}
			// Make sure we have permission to ban in this discord server
			if (!Utils.hasPermission(bot.getAPI().getYourself(), message.getChannelReceiver().getServer(),
					PermissionType.BAN_MEMBERS)) {
				new AutoDeleteMessage("You are not authorized to ban", 2, message.getChannelReceiver());
				break;
			}
			// All checks passed, let's ban the user
			message.getChannelReceiver().getServer().banUser(user).get();
			EmbedBuilder emb = EmbeddedMessage.getEmbeddedMessage(
					":hammer: **Banned:** " + user.getMentionTag() + " (" + user.getId() + ")", "", "", "", "",
					Color.YELLOW);
			message.reply("", emb);
			break;
		case "/embed":
			message.delete();
			EmbeddedMessage.embed(message, bot);
			break;
		}
	}

}
