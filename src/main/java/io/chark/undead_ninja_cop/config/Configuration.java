package io.chark.undead_ninja_cop.config;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public final class Configuration {

    private static final Configuration INSTANCE = new Configuration();
    private Response response;

    private Configuration() {
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/configuration.json"));
        response = gson.fromJson(reader, Response.class);
    }

    /**
     * Get game music volume.
     *
     * @return game music volume.
     */
    public float getMusicVolume() {
        return response.getGeneral().getMusicVolume();
    }

    /**
     * Get directory where music files are stored.
     *
     * @return music file directory.
     */
    public String getMusicDirectory() {
        return response.getGeneral().getMusicDirectory();
    }

    /**
     * Get configuration instance.
     *
     * @return configuration instance.
     */
    public static Configuration getInstance() {
        return INSTANCE;
    }
}