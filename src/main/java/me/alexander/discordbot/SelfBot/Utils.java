package me.alexander.discordbot.SelfBot;

import java.util.concurrent.ExecutionException;

import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.permissions.PermissionState;
import de.btobastian.javacord.entities.permissions.PermissionType;
import de.btobastian.javacord.entities.permissions.Permissions;
import de.btobastian.javacord.entities.permissions.Role;

/**
 * 
 * A collection of functions used internally by the bot
 * 
 * @author Deftware
 *
 */
public class Utils {

	public Utils() {}

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

}
