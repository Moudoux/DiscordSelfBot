package me.alexander.discordbot.SelfBot.Messages;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.message.embed.EmbedBuilder;
import me.alexander.discordbot.SelfBot.SelfBot;

public class EmbeddedMessage {

	public EmbeddedMessage() {}

	/**
	 * Random to get random RGB values for the color
	 */
	private static Random rand = new Random();

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
	 * Returns an array with all URLs in a given string
	 * 
	 * @param text
	 * @return List<String>
	 */
	public static List<String> extractUrls(final String text) {
		final List<String> containedUrls = new ArrayList<String>();
		final String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
		final Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
		final Matcher urlMatcher = pattern.matcher(text);
		while (urlMatcher.find()) {
			containedUrls.add(text.substring(urlMatcher.start(0), urlMatcher.end(0)));
		}
		return containedUrls;
	}

	/**
	 * Get's a user from their id or mentiontag
	 * 
	 * @param tag
	 * @param bot
	 * @return User
	 */
	public static User getUser(final String tag, final SelfBot bot) {
		for (final Server c : bot.getAPI().getServers()) {
			for (final User u : c.getMembers()) {
				if (u.getMentionTag().equals(tag) || u.getId().equals(tag)) {
					return u;
				}
			}
		}
		return null;
	}

	/**
	 * Sends an embedded message about a given user
	 * 
	 * @param m
	 * @param bot
	 */
	public static void userInfo(final Message m, final SelfBot bot) {
		m.delete();

		User user = EmbeddedMessage.getUser(m.getContent().replace("/user ", ""), bot);
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

		m.reply("", emb);
	}

	/**
	 * The /embed message interpreter
	 * 
	 * @param message
	 * @param bot
	 */
	public static void embed(final Message message, final SelfBot bot) {
		if (message.getContent().startsWith("/user")) {
			EmbeddedMessage.userInfo(message, bot);
			return;
		}
		if (!message.getContent().startsWith("/embed")) {
			return;
		}

		String msg = message.getContent().replace("/embed ", "");
		message.delete();

		try {
			final EmbedBuilder emb = new EmbedBuilder();
			emb.setColor(new Color(EmbeddedMessage.rand.nextFloat(), EmbeddedMessage.rand.nextFloat(),
					EmbeddedMessage.rand.nextFloat()).brighter());

			emb.setFooter(
					message.getAuthor().getName() + "'s Bot | Message sent "
							+ new SimpleDateFormat("MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()),
					"https://avatars1.githubusercontent.com/u/6422482?v=3&s=400");

			for (final String url : EmbeddedMessage.extractUrls(msg)) {
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
				emb.setAuthor(message.getAuthor().getName() + " says:");
			}

			if (msg.contains("--")) {
				final String footer = msg.split("\\-\\-")[1].split("\\-\\-")[0];
				msg = msg.replace("--" + footer + "--", "");
				emb.setFooter(footer);
			}

			emb.setDescription(msg);
			message.reply("", emb);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}

	}

}
