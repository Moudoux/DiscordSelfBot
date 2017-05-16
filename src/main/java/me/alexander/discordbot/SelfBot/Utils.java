package me.alexander.discordbot.SelfBot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.permissions.PermissionState;
import de.btobastian.javacord.entities.permissions.PermissionType;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.entities.permissions.Role;
import me.alexander.discordbot.Logger.LogType;
import me.alexander.discordbot.Main;

/**
 * 
 * A collection of functions used internally by the bot
 * 
 * @author Deftware
 *
 */
public class Utils {

	/**
	 * Schedule executor service (used for self-delete)
	 */
	private static final ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
	
	private Utils() {}

	/**
	 * Returns a user by a mention tag, id or name
	 * 
	 * @param id
	 * @param server
	 * @return User
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static User getUser(String id, Server server, SelfBot bot) throws InterruptedException, ExecutionException {
		User user = bot.getAPI().getUserById(id).get();
		if (user == null) {
			// Not a user id, or invalid user id
			for (User u : server.getMembers()) {
				if (u.getMentionTag().equals(id)) {
					user = u;
					break;
				} else if (u.getName().equals(id)) {
					user = u;
					break;
				} else if (u.getNickname(server).equals(id)) {
					user = u;
					break;
				} else if (u.getId().equals(id)) {
					user = u;
					break;
				}
			}
		}
		return user;
	}

	/**
	 * Checks if a given user has a permission
	 * 
	 * @param user
	 * @param permission
	 * 
	 * @return Boolean
	 */
	public static boolean hasPermission(User user, Server server, PermissionType permission) {
		for (Role r : user.getRoles(server)) {
			Permissions p = r.getPermissions();
			if (p.getState(permission).equals(PermissionState.ALLOWED)) {
				return true;
			}
		}
		return false;
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
				if (u.getMentionTag().equals(tag) || u.getId().equals(tag) || u.getName().startsWith(tag)) {
					return u;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * Sends a message in a given channel and automatically deletes the message
	 * after a set time
	 * 
	 * @param message
	 * @param time
	 * @param channel
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void sendSelfDeletingMessage(String message, int time, Channel channel)
			throws InterruptedException, ExecutionException {
		final Future<Message> messageObj = channel.sendMessage(message);
		deleteMessageLater(messageObj, time, TimeUnit.SECONDS);
	}
	
	/**
	 * Delete a message after a set amount of time
	 * 
	 * @param message
	 * @param time
	 * @param unit
	 */
	public static void deleteMessageLater(Future<Message> message, int time, TimeUnit unit) {
		exec.schedule(() -> {
			try {
				message.get().delete();
			} catch (InterruptedException | ExecutionException e) {
				Main.getLogger().log(e.getMessage(), LogType.WARN);
			}
		}, time, unit);
	}

	/**
	 * Delete a message after a set amount of time
	 * 
	 * @param message
	 * @param time
	 * @param unit
	 */
	public static void deleteMessageLater(Message message, int time, TimeUnit unit) {
		exec.schedule(() -> {
			message.delete();
		}, time, unit);
	}
	
}
