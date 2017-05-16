package me.alexander.discordbot;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {

	/**
	 * Get's the current time
	 * 
	 * @return String
	 */
	private String getTime() {
		return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	/**
	 * The different logtypes
	 *
	 */
	public enum LogType {
		INFO, WARN, CRITICAL
	}

	/**
	 * Logs to console
	 * 
	 * @param text
	 * @param type
	 */
	public void log(final String text, final LogType type) {
		System.out.println("[" + getTime() + "] [" + "SelfBot" + "/" + type.toString() + "] " + text);
	}

}
