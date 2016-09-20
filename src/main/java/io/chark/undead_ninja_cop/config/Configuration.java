package io.chark.undead_ninja_cop.config;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Game configuration settings.
 */
public final class Configuration {

    private static final String CONFIGURATION_DIR = "/configuration.json";
    private static Configuration instance;

    private final Gameplay gameplay = Gameplay.DEFAULTS;
    private final Settings settings = Settings.DEFAULTS;

    private Configuration() {
    }

    /**
     * Get gameplay settings.
     *
     * @return gameplay settings, never null.
     */
    public Gameplay getGameplay() {
        return gameplay;
    }

    /**
     * Get general settings.
     *
     * @return general settings, never null.
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * Get configuration instance.
     *
     * @return configuration instance.
     */
    public static synchronized Configuration getInstance() {
        if (instance == null) {
            Reader reader = new InputStreamReader(Configuration.class
                    .getResourceAsStream(CONFIGURATION_DIR));

            instance = new Gson()
                    .fromJson(reader, Configuration.class);
        }
        return instance;
    }
}