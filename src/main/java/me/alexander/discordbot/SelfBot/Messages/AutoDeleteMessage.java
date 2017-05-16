package me.alexander.discordbot.SelfBot.Messages;

import java.util.concurrent.ExecutionException;

import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.message.Message;
import me.alexander.discordbot.Logger.LogType;
import me.alexander.discordbot.Main;

/**
 * 
 * Message that delets itself after a set time
 * 
 * @author Deftware
 *
 */
public class AutoDeleteMessage {

	protected final Message message;

	/**
	 * 
	 * Sends a message in a given channel and automatically delets the message
	 * after a set time
	 * 
	 * @param message
	 * @param time
	 * @param channel
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public AutoDeleteMessage(String message, int time, Channel channel)
			throws InterruptedException, ExecutionException {
		this.message = channel.sendMessage(message).get();
		(new Thread(() -> {
			try {
				Thread.sleep(time * 1000);
			} catch (InterruptedException e) {
				Main.getLogger().log(e.getMessage(), LogType.WARN);
			}
			try {
				this.message.delete().get();
			} catch (InterruptedException | ExecutionException e) {
				Main.getLogger().log(e.getMessage(), LogType.WARN);
			}
		})).start();
	}

}
