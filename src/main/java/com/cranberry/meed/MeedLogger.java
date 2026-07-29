package com.cranberry.meed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Shared logger for the whole mod. Using a real Log4j logger instead of
 * System.out.println means log lines show up as "[MEED]:" the same way
 * every other mod's logs do, instead of Forge's dev-environment log4j2
 * config stamping bare System.out calls with a
 * [full.class.Name:methodName:lineNumber]: prefix.
 */
public class MeedLogger {

    private static final Logger LOGGER = LogManager.getLogger("MEED");

    public static void info(String message) {
        LOGGER.info("[MEED] " + message);
    }

    public static void warn(String message) {
        LOGGER.warn("[MEED] " + message);
    }

    public static void error(String message) {
        LOGGER.error("[MEED] " + message);
    }

    public static void error(String message, Throwable throwable) {
        LOGGER.error("[MEED] " + message, throwable);
    }

}