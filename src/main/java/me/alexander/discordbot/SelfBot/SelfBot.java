package me.alexander.discordbot.SelfBot;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.FutureCallback;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import de.btobastian.javacord.listener.message.MessageEditListener;
import me.alexander.discordbot.SelfBot.Messages.IMessageSelfBot;

/**
 * The SelfBot bot
 *
 */
public class SelfBot {

	/**
	 * The current logger for this SelfBot
	 */
	private final Logger logger;

	/**
	 * The current SelfBot instance
	 */
	private final SelfBot bot = this;

	/**
	 * Token used
	 */
	private final String token;

	/**
	 * Call System.exit(0); on disconnect. Default: true
	 */
	private final boolean exitOnDisconnect;

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
		this(token, true);
	}

	/**
	 * SelfBot instance, provide the token to login and whether or not to exit
	 * on bot disconnect
	 * 
	 * @param token
	 * @param exitOnDisconnect
	 */
	public SelfBot(final String token, final boolean exitOnDisconnect) {
		this.token = token;
		this.exitOnDisconnect = exitOnDisconnect;
		this.logger = LoggerFactory.getLogger(SelfBot.class);
	}

	/**
	 * Disconnects the bot and will shutdown the bot
	 * 
	 */
	public void disconnect() {
		api.setAutoReconnect(false);
		api.disconnect();
		if (exitOnDisconnect) {
			System.exit(0);
		}
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
						api.getThreadPool().getExecutorService().submit(() -> {
							try {
								IMessageSelfBot.execute(message, bot);
							} catch (InterruptedException | ExecutionException e) {
								logger.debug(e.getMessage());
							}
						});
					}
				});
				api.registerListener(new MessageEditListener() {
					@Override
					public void onMessageEdit(final DiscordAPI api, final Message message, final String oldContent) {
						api.getThreadPool().getExecutorService().submit(() -> {
							try {
								IMessageSelfBot.execute(message, bot);
							} catch (InterruptedException | ExecutionException e) {
								logger.debug(e.getMessage());
							}
						});
					}
				});
				api.setAutoReconnect(true);
			}

			@Override
			/**
			 * Called when the bot is not able to connect to Discord
			 */
			public void onFailure(final Throwable t) {
				logger.debug("Failed to connect");
				t.printStackTrace();
			}
		});
	}
	
	/**
	 * Returns the logger for this SelfBot
	 * 
	 * @return logger
	 */
	public Logger getLogger() {
		return logger;
	}

}
