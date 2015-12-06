package kvverti.bnine.util;

import net.minecraftforge.fml.common.FMLLog;

import org.apache.logging.log4j.Level;

import kvverti.bnine.init.Meta;

public class Logger {

	public static void log(Level level, String message, Object... args) {

		FMLLog.log(Meta.NAME, level, message, args);
	}

	public static void info(String message, Object... args) {

		log(Level.INFO, message, args);
	}

	public static void warn(String message, Object... args) {

		log(Level.WARN, message, args);
	}

	public static void error(String message, Object... args) {

		log(Level.ERROR, message, args);
	}

	public static void error(Throwable error, String message, Object... args) {

		FMLLog.log(Meta.NAME, Level.ERROR, error, message, args);
	}

	public static void debug(String message, Object... args) {

		log(Level.DEBUG, message, args);
	}

	public static void trace(String message, Object... args) {

		log(Level.TRACE, message, args);
	}
}