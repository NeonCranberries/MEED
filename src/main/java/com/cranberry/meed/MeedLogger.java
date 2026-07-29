package com.cranberry.meed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// prefixes every line with [MEED] instead of the raw class/method/line stamp
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