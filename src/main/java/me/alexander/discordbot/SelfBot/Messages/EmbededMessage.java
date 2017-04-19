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

public class EmbededMessage {

	private static Random rand = new Random();

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

	public static void userInfo(final Message m, final SelfBot bot) {
		m.delete();
		final EmbedBuilder emb = new EmbedBuilder();
		emb.setColor(Color.cyan);
		User user = EmbededMessage.getUser(m.getContent().replace("/user ", ""), bot);
		if (user == null) {
			user = bot.getAPI().getYourself();
		}
		String output = "Username\n" + user.getName();
		output += "\n\nUser ID\n" + user.getId();
		output += "\n\nMention Tag\n" + user.getMentionTag();
		output += "\n\nAvatar\n" + user.getAvatarUrl();
		output += "\n\nStatus\n" + user.getStatus().name() + "\n";

		emb.setTitle("Profile of " + user.getName() + ":");
		emb.setDescription(output);
		emb.setThumbnail(user.getAvatarUrl().toString());
		emb.setFooter(
				"Deft's Bot | Message sent "
						+ new SimpleDateFormat("MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()),
				"https://avatars1.githubusercontent.com/u/6422482?v=3&s=400");
		m.reply("", emb);
	}

	public static void embed(final Message message, final SelfBot bot) {
		if (message.getContent().startsWith("/user")) {
			EmbededMessage.userInfo(message, bot);
			return;
		}
		if (!message.getContent().startsWith("/embed")) {
			return;
		}

		String msg = message.getContent().replace("/embed ", "");
		message.delete();

		try {
			final EmbedBuilder emb = new EmbedBuilder();
			emb.setColor(new Color(EmbededMessage.rand.nextFloat(), EmbededMessage.rand.nextFloat(),
					EmbededMessage.rand.nextFloat()).brighter());

			emb.setFooter(
					message.getAuthor().getName() + "'s Bot | Message sent "
							+ new SimpleDateFormat("MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()),
					"https://avatars1.githubusercontent.com/u/6422482?v=3&s=400");

			for (final String url : EmbededMessage.extractUrls(msg)) {
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
