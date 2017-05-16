package me.alexander.discordbot.SelfBot;

import static me.alexander.discordbot.SelfBot.Utils.deleteMessageLater;

import java.awt.Color;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.embed.EmbedBuilder;
import de.btobastian.javacord.entities.permissions.PermissionType;
import me.alexander.discordbot.SelfBot.Messages.EmbeddedMessageUtil;

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
			message.delete();
			if (args.equals("")) {
				EmbedBuilder emb = EmbeddedMessageUtil.getEmbeddedMessage("Usage Warning", "Invalid argument", "", "",
						"", Color.RED);
				deleteMessageLater(message.reply("", emb), 2, TimeUnit.SECONDS);
				break;
			}
			User user = Utils.getUser(args, message.getChannelReceiver().getServer(), bot);
			if (user == null) {
				EmbedBuilder emb = EmbeddedMessageUtil.getEmbeddedMessage("Usage Warning", "User not found", "", "", "",
						Color.RED);
				deleteMessageLater(message.reply("", emb), 2, TimeUnit.SECONDS);
				break;
			}
			// Make sure we have permission to ban in this discord server
			if (!Utils.hasPermission(bot.getAPI().getYourself(), message.getChannelReceiver().getServer(),
					PermissionType.BAN_MEMBERS)) {
				EmbedBuilder emb = EmbeddedMessageUtil.getEmbeddedMessage("Usage Warning",
						"You are not permitted to ban others!", "", "", "", Color.RED);
				deleteMessageLater(message.reply("", emb), 2, TimeUnit.SECONDS);
				break;
			}
			// All checks passed, let's ban the user
			message.getChannelReceiver().getServer().banUser(user).get();
			EmbedBuilder emb = EmbeddedMessageUtil.getEmbeddedMessage(
					":hammer: **Banned:** " + user.getMentionTag() + " (" + user.getId() + ")", "", "", "", "",
					Color.YELLOW);
			message.reply("", emb);
			break;
		case "/user":
		case "/embed":
			message.delete();
			EmbeddedMessageUtil.embed(message, bot);
			break;
		}
	}

}
