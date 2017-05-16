package me.alexander.discordbot.SelfBot.Messages;

import static me.alexander.discordbot.SelfBot.Utils.extractUrls;
import static me.alexander.discordbot.SelfBot.Utils.getUser;

import java.awt.Color;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Future;

import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.embed.EmbedBuilder;
import me.alexander.discordbot.SelfBot.SelfBot;

public class EmbeddedMessageUtil {

	private EmbeddedMessageUtil() {}

	/**
	 * Random to get random RGB values for the color
	 */
	private static SecureRandom rand = new SecureRandom();

	/**
	 * Builds an embedded message
	 * 
	 * @param title
	 * @param description
	 * @param footer
	 * @param image
	 * @param thumbnail
	 * @param color
	 * @return EmbedBuilder
	 */
	public static EmbedBuilder getEmbeddedMessage(final String title, final String description, final String footer,
			final String image, final String thumbnail, final Color color) {
		final EmbedBuilder emb = new EmbedBuilder();
		emb.setTitle(title);
		emb.setDescription(description);
		emb.setFooter(footer);
		emb.setImage(image);
		emb.setThumbnail(thumbnail);
		emb.setColor(color);
		return emb;
	}

	/**
	 * Sends an embedded message about a given user
	 * 
	 * @param m
	 * @param bot
	 */
	public static Future<Message> userInfo(final Message m, final SelfBot bot) {
		m.delete();

		User user = getUser(m.getContent().replace("/user ", ""), bot);
		if (user == null) {
			user = bot.getAPI().getYourself();
		}

		final EmbedBuilder emb = new EmbedBuilder();

		emb.addField("Username", user.getName(), true);
		emb.addField("User ID", user.getId(), true);
		emb.addField("Mention Tag", user.getMentionTag(), true);
		emb.addField("Avatar", user.getAvatarUrl().toString(), true);
		emb.addField("Status", user.getStatus().name(), true);

		emb.setFooter(
				m.getAuthor().getName() + "'s Bot | Message sent "
						+ new SimpleDateFormat("MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()),
				"https://avatars1.githubusercontent.com/u/6422482?v=3&s=400");

		emb.setTitle("Profile of " + user.getName() + ":");
		emb.setColor(Color.red);
		emb.setThumbnail(user.getAvatarUrl().toString());

		return m.reply("", emb);
	}

	/**
	 * The /embed message interpreter
	 * 
	 * @param message
	 * @param bot
	 */
	public static Future<Message> embed(final Message message, final SelfBot bot) {
		if (message.getContent().startsWith("/user")) {
			return EmbeddedMessageUtil.userInfo(message, bot);
		}
		if (!message.getContent().startsWith("/embed")) {
			return null;
		}

		String msg = message.getContent().replace("/embed ", "");

		return message.reply("", generateEmbed(msg, bot));
	}

	/**
	 * The /embed message interpreter
	 * 
	 * @param msg
	 * @param bot
	 * @param channel
	 * @return
	 */
	public static Future<Message> embed(String msg, final SelfBot bot, final Channel channel) {
		return channel.sendMessage("", generateEmbed(msg, bot));
	}

	public static EmbedBuilder generateEmbed(String msg, final SelfBot bot) {
		try {
			final EmbedBuilder emb = new EmbedBuilder();
			emb.setColor(new Color(EmbeddedMessageUtil.rand.nextFloat(), EmbeddedMessageUtil.rand.nextFloat(),
					EmbeddedMessageUtil.rand.nextFloat()).brighter());

			emb.setFooter(
					bot.getAPI().getYourself().getName() + "'s Bot | Message sent "
							+ new SimpleDateFormat("MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()),
					"https://avatars1.githubusercontent.com/u/6422482?v=3&s=400");

			for (final String url : extractUrls(msg)) {
				if (url.endsWith(".png") || url.endsWith(".jpeg") || url.endsWith(".jpg")) {
					emb.setImage(url);
					msg = msg.replace(url, "");
					break;
				}
			}

			if (msg.contains("**")) {
				final String title = msg.split("\\*\\*")[1].split("\\*\\*")[0];
				msg = msg.replace("**" + title + "**", "");
				emb.setTitle(title);
			} else {
				emb.setAuthor(bot.getAPI().getYourself().getName() + " says:");
			}

			if (msg.contains("--")) {
				final String footer = msg.split("\\-\\-")[1].split("\\-\\-")[0];
				msg = msg.replace("--" + footer + "--", "");
				emb.setFooter(footer);
			}

			emb.setDescription(msg);
			return emb;
		} catch (final Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
