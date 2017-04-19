package me.alexander.discordbot;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {

	private String getTime() {
		return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	public enum LogType {
		INFO, WARN, CRITICAL
	}

	public void log(final String text, final LogType type) {
		System.out.println("[" + this.getTime() + "] [" + "SelfBot" + "/" + type.toString() + "] " + text);
	}

}
