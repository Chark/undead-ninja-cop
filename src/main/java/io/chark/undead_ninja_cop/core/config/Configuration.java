package io.chark.undead_ninja_cop.core.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.chark.undead_ninja_cop.core.exception.GameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Game configuration settings.
 */
public final class Configuration {

    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);
    private static final String CONFIGURATION_DIR = "/configuration.json";

    private static Configuration instance;

    private final Gameplay gameplay;
    private final Settings settings;

    @JsonCreator
    private Configuration(@JsonProperty("gameplay") Gameplay gameplay,
                          @JsonProperty("settings") Settings settings) {

        this.gameplay = gameplay;
        this.settings = settings;
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
            InputStream stream = Configuration.class
                    .getResourceAsStream(CONFIGURATION_DIR);

            try {
                instance = new ObjectMapper()
                        .readValue(stream, Configuration.class);

            } catch (IOException e) {
                LOGGER.error("Could not load config", e);

                throw new GameException("Could not load configuration");
            }
        }
        return instance;
    }
}