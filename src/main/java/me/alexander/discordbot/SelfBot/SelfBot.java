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

public class SelfBot {

	private SelfBot bot = this;
	private final String token;
	private DiscordAPI api = null;

	public SelfBot(String token) {
		this.token = token;
	}

	public void disconnect() {
		api.disconnect();
		System.exit(0);
	}

	public DiscordAPI getAPI() {
		return api;
	}

	public void setup() {
		api = Javacord.getApi(token, false);
		api.connect(new FutureCallback<DiscordAPI>() {
			@Override
			public void onSuccess(DiscordAPI api) {
				api.registerListener(new MessageCreateListener() {
					@Override
					public void onMessageCreate(final DiscordAPI api, final Message message) {
						new Thread() {
							@Override
							public void run() {
								IMessageSelfBot imessage = new IMessageSelfBot(api, message, bot);
								imessage.execute();
							}
						}.start();
					}
				});
				api.registerListener(new MessageEditListener() {
					@Override
					public void onMessageEdit(final DiscordAPI api, final Message message, String oldContent) {
						new Thread() {
							@Override
							public void run() {
								IMessageSelfBot imessage = new IMessageSelfBot(api, message, bot);
								imessage.execute();
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
			public void onFailure(Throwable t) {
				System.out.println("Failed to connect");
				t.printStackTrace();
			}
		});
	}

}
