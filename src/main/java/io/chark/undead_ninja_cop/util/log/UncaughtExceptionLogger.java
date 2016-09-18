package io.chark.undead_ninja_cop.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles uncaught exceptions.
 */
public final class UncaughtExceptionLogger implements Thread.UncaughtExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UncaughtExceptionLogger.class);

    private UncaughtExceptionLogger() {
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.error("Uncaught exception", e);
    }

    /**
     * Initialize uncaught exception logger.
     *
     * @return setup uncaught exception logger.
     */
    public static UncaughtExceptionLogger init() {
        UncaughtExceptionLogger logger = new UncaughtExceptionLogger();
        Thread.setDefaultUncaughtExceptionHandler(logger);
        return logger;
    }
}