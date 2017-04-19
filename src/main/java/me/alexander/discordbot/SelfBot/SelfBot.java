package me.alexander.discordbot.SelfBot;

import com.google.common.util.concurrent.FutureCallback;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.permissions.Role;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import de.btobastian.javacord.listener.message.MessageEditListener;
import de.btobastian.javacord.listener.server.ServerMemberAddListener;
import de.btobastian.javacord.listener.server.ServerMemberBanListener;
import de.btobastian.javacord.listener.server.ServerMemberRemoveListener;
import de.btobastian.javacord.listener.user.UserRoleAddListener;
import de.btobastian.javacord.listener.user.UserRoleRemoveListener;
import me.alexander.discordbot.Logger.LogType;
import me.alexander.discordbot.Main;

/**
 * The SelfBot bot
 *
 */
public class SelfBot {

	/**
	 * The current SelfBot instance
	 */
	private final SelfBot bot = this;

	/**
	 * Token used
	 */
	private final String token;

	/**
	 * The DiscordAPI instance
	 */
	private DiscordAPI api = null;

	/**
	 * SelfBot instance, provide the token to login
	 * 
	 * @param token
	 */
	public SelfBot(final String token) {
		this.token = token;
	}

	/**
	 * Disconnects the bot and will shutdown the bot
	 * 
	 */
	public void disconnect() {
		api.disconnect();
		System.exit(0);
	}

	/**
	 * Get's the current DiscordAPI instance
	 * 
	 * @return DiscordAPI
	 */
	public DiscordAPI getAPI() {
		return api;
	}

	/**
	 * Starts the bot, sets up all the hooks
	 */
	public void setup() {
		// Get the API, false = This is not a bot account
		api = Javacord.getApi(token, false);
		api.connect(new FutureCallback<DiscordAPI>() {
			@Override
			public void onSuccess(final DiscordAPI api) {
				api.registerListener(new MessageCreateListener() {
					@Override
					public void onMessageCreate(final DiscordAPI api, final Message message) {
						new Thread() {
							@Override
							public void run() {
								new IMessageSelfBot(message, bot).execute();
							}
						}.start();
					}
				});
				api.registerListener(new MessageEditListener() {
					@Override
					public void onMessageEdit(final DiscordAPI api, final Message message, final String oldContent) {
						new Thread() {
							@Override
							public void run() {
								new IMessageSelfBot(message, bot).execute();
							}
						}.start();
					}
				});
				api.registerListener(new ServerMemberAddListener() {
					@Override
					public void onServerMemberAdd(final DiscordAPI api, final User user, final Server server) {
						new Thread() {
							@Override
							public void run() {
								// Hook ?
							}
						}.start();
					}
				});
				api.registerListener(new ServerMemberRemoveListener() {
					@Override
					public void onServerMemberRemove(final DiscordAPI api, final User user, final Server server) {
						new Thread() {
							@Override
							public void run() {
								// Hook ?
							}
						}.start();
					}
				});
				api.registerListener(new ServerMemberBanListener() {
					@Override
					public void onServerMemberBan(final DiscordAPI api, final User user, final Server server) {
						new Thread() {
							@Override
							public void run() {
								// Hook ?
							}
						}.start();
					}
				});
				api.registerListener(new UserRoleAddListener() {
					@Override
					public void onUserRoleAdd(final DiscordAPI api, final User user, final Role role) {
						new Thread() {
							@Override
							public void run() {
								// Hook ?
							}
						}.start();
					}
				});
				api.registerListener(new UserRoleRemoveListener() {
					@Override
					public void onUserRoleRemove(final DiscordAPI api, final User user, final Role role) {
						new Thread() {
							@Override
							public void run() {
								// Hook ?
							}
						}.start();
					}
				});
				api.setAutoReconnect(true);
			}

			@Override
			/**
			 * Called when the bot is not able to connect to Discord
			 */
			public void onFailure(final Throwable t) {
				Main.getLogger().log("Failed to connect", LogType.CRITICAL);
				t.printStackTrace();
			}
		});
	}

}
