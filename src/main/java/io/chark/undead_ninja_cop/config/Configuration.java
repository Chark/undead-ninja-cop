package io.chark.undead_ninja_cop.config;

public final class Configuration {

    private static Configuration INSTANCE;

    private Configuration() {
    }

    /**
     * Get game music volume.
     *
     * @return game music volume.
     */
    public float getMusicVolume() {
        return 1f; // TODO load from configuration.json
    }

    /**
     * Get directory where music files are stored.
     *
     * @return music file directory.
     */
    public String getMusicDirectory() {
        return "music/"; // TODO load from configuration.json
    }

    /**
     * Get configuration instance.
     *
     * @return configuration instance.
     */
    public static Configuration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Configuration();
        }
        return INSTANCE;
    }
}